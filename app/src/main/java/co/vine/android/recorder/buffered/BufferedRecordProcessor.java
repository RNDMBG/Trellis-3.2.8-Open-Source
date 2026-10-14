            package co.vine.android.recorder.buffered;

            import android.app.Activity;
            import android.app.ActivityManager;
            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.hardware.Camera;
            import android.os.Looper;
            import android.support.v8.renderscript.RenderScript;
            import co.vine.android.HomeTabActivity;
            import co.vine.android.VineLoggingException;
            import co.vine.android.VineNotSupportedException;
            import co.vine.android.plugin.BaseRecorderPluginManager;
            import co.vine.android.recorder.AbstractCombiningRunnable;
            import co.vine.android.recorder.AbstractEncodingRunnable;
            import co.vine.android.recorder.AudioDataReceiver;
            import co.vine.android.recorder.BaseFinishProcessTask;
            import co.vine.android.recorder.BaseSurfaceListener;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.ByteBufferQueue;
            import co.vine.android.recorder.DeviceIssue;
            import co.vine.android.recorder.HwCombiningRunnable;
            import co.vine.android.recorder.HwEncodingRunnable;
            import co.vine.android.recorder.HwVineFrameRecorder;
            import co.vine.android.recorder.ParentHolder;
            import co.vine.android.recorder.PictureConverter;
            import co.vine.android.recorder.RecordClock;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordController;
            import co.vine.android.recorder.RecordProcessor;
            import co.vine.android.recorder.RecordSegment;
            import co.vine.android.recorder.RecordSession;
            import co.vine.android.recorder.RecordState;
            import co.vine.android.recorder.RecordingFile;
            import co.vine.android.recorder.SurfaceController;
            import co.vine.android.recorder.SwCombiningRunnable;
            import co.vine.android.recorder.SwEncodingRunnable;
            import co.vine.android.recorder.SwVineFrameRecorder;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioArrays;
            import co.vine.android.recorder.audio.AudioReceiver;
            import co.vine.android.recorder.camera.CameraManager;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.recorder.camera.PreviewManagerCallback;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.service.ResourceService;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.MediaUtil;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacv.cpp.opencv_core;
            import java.io.File;
            import java.lang.ref.WeakReference;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.concurrent.ConcurrentLinkedQueue;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 62 */    public class BufferedRecordProcessor implements RecordProcessor {
                private static WeakReference<ByteBufferQueue> sByteBufferQueue;
                private static ByteBufferQueue sByteBufferQueueReal;
                private AudioArray<?, ?> mAudioDataBufferMax;
                private AudioDataReceiver mAudioReceiver;
                private final RecordClock mClock;
                private final Context mContext;
                private AbstractEncodingRunnable mEncodingRunnable;
                private boolean mEncodingThreadWasRunning;
                private BaseFinishProcessTask mFinishProcessTask;
                private final boolean mHardwareEncoding;
                private final BufferedCameraPreviewReceiver mImageReceiver;
                private String mOutput;
                private final ParentHolder mParentHolder;
                private final BaseRecorderPluginManager mPluginManager;
                private Thread mProcessThread;
                private final RecordState mState;
                private final boolean mUseMp4;
                private byte[] mVideoDataBufferMax;
                private int mVideoDataStartCount;
                private final ConcurrentLinkedQueue<VideoData> mDataQueue = new ConcurrentLinkedQueue<>();
                private final BufferedPreviewManagerCallback mPreviewManagerCallback = BufferedPreviewManagerCallback.getInstance();

/* 63 */        static /* synthetic */ ParentHolder access$000(BufferedRecordProcessor x0) {
/* 64 */            return x0.mParentHolder;
                }

/* 63 */        static /* synthetic */ RecordState access$100(BufferedRecordProcessor x0) {
/* 64 */            return x0.mState;
                }

/* 92 */        public BufferedRecordProcessor(boolean useMp4, RecordState state, RecordClock clock, boolean hardwareEncoding, Context applicationContext, BaseRecorderPluginManager pluginManager, ParentHolder parentHolder) {
/* 94 */            this.mUseMp4 = useMp4;
/* 95 */            this.mState = state;
/* 96 */            this.mClock = clock;
/* 97 */            this.mParentHolder = parentHolder;
/* 98 */            this.mHardwareEncoding = hardwareEncoding;
/* 99 */            this.mContext = applicationContext;
/* 101 */           this.mPluginManager = pluginManager;
/* 102 */           this.mImageReceiver = new BufferedCameraPreviewReceiver(this.mState, this.mClock, this.mDataQueue, this.mPluginManager, this.mPreviewManagerCallback);
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 108 */       public boolean start(BasicVineRecorder parent, String output, Activity activity, CameraSetting cs, RecordProcessor.ProcessingErrorHandler errorHandler) {
/* 109 */           this.mImageReceiver.clearLastFrames();
/* 110 */           long start = System.currentTimeMillis();
/* 111 */           this.mOutput = output + ".video" + RecordConfigUtils.VIDEO_CONTAINER_EXT;
/* 112 */           RecordConfigUtils.RecordConfig config = parent.getConfig();
/* 113 */           if (config == null) {
/* 114 */               return false;
                    }
/* 116 */           int frameRate = cs.fps;
/* 123 */           Bitmap matrixBitmap = getPreviewBitmap();
/* 125 */           Bitmap thumbnailBitmap = ResourceService.getThumbnailBitmap(activity);
/* 126 */           RenderScript rs = ResourceService.getRenderScript(activity);
/* 127 */           Matrix thumbnailMatrix = ResourceService.getThumbnailMatrix(activity);
/* 128 */           Canvas thumbnailCanvas = ResourceService.getThumbnailCanvas(activity);
/* 129 */           Canvas previewCanvas = ResourceService.getPreviewCanvas();
/* 131 */           SLog.m447d("start 4 took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
/* 132 */           long start2 = System.currentTimeMillis();
/* 134 */           PictureConverter pictureConverter = ResourceService.getPictureConverter();
/* 135 */           if (pictureConverter != null) {
/* 136 */               pictureConverter.updateSettingsIfNeeded(cs);
                    } else {
                        try {
/* 139 */                   pictureConverter = new PictureConverter(rs, 480, cs);
/* 145 */                   ResourceService.setPictureConverter(pictureConverter);
                        } catch (VineNotSupportedException e) {
/* 141 */                   parent.onDeviceIssue(new VineLoggingException("Device does not support Rs"), DeviceIssue.RS_NOT_SUPPORTED);
/* 143 */                   return false;
                        }
                    }
/* 148 */           SLog.m447d("start 5 took {}ms.", Long.valueOf(System.currentTimeMillis() - start2));
/* 149 */           long start3 = System.currentTimeMillis();
/* 151 */           if (SLog.sLogsOn) {
/* 152 */               ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
/* 153 */               ActivityManager activityManager = (ActivityManager) parent.getActivity().getSystemService(HomeTabActivity.TAG_ACTIVITY);
/* 154 */               activityManager.getMemoryInfo(mi);
/* 155 */               SLog.m447d("[mem] Avaliable bytes: {}.", Long.valueOf(mi.availMem));
                    }
/* 158 */           CameraManager cameraManager = CameraManager.getInstance();
/* 159 */           boolean useRealQueue = parent.getConfig().memRatio == 1.0d;
/* 160 */           int frameSize = this.mPreviewManagerCallback.getFrameSize();
/* 161 */           ByteBufferQueue queue = null;
/* 162 */           if (sByteBufferQueue == null && sByteBufferQueueReal == null) {
/* 163 */               queue = new ByteBufferQueue(parent.getConfig().bufferCount, frameSize, parent.getMemoryResponder());
/* 164 */               if (useRealQueue) {
/* 165 */                   sByteBufferQueueReal = queue;
                        } else {
/* 167 */                   sByteBufferQueue = new WeakReference<>(queue);
                        }
                    } else {
/* 170 */               if (useRealQueue) {
/* 171 */                   queue = sByteBufferQueueReal;
                        } else if (sByteBufferQueue != null) {
/* 174 */                   ByteBufferQueue queue2 = sByteBufferQueue.get();
                            queue = queue2;
                        }
/* 177 */               if (queue == null) {
/* 178 */                   queue = new ByteBufferQueue(parent.getConfig().bufferCount, frameSize, parent.getMemoryResponder());
/* 179 */                   sByteBufferQueue = new WeakReference<>(queue);
                        } else {
/* 181 */                   queue.reset(parent.getConfig().bufferCount, frameSize);
                        }
                    }
/* 185 */           SLog.m447d("start 6 took {}ms.", Long.valueOf(System.currentTimeMillis() - start3));
/* 186 */           long start4 = System.currentTimeMillis();
/* 188 */           if (!cameraManager.isCameraReady()) {
/* 189 */               CrashUtil.logException(new VineLoggingException("Camera was released already. "));
/* 190 */               return false;
                    }
/* 192 */           if (this.mHardwareEncoding) {
/* 193 */               ByteBuffer frameBuffer = ResourceService.getFrameBuffer();
/* 194 */               this.mEncodingRunnable = new HwEncodingRunnable(this.mContext, this.mDataQueue, this.mVideoDataBufferMax, this.mState, output, frameRate, frameBuffer, matrixBitmap, thumbnailBitmap, pictureConverter, this.mVideoDataStartCount, this.mPreviewManagerCallback, queue, thumbnailMatrix, thumbnailCanvas, previewCanvas);
                    } else {
/* 199 */               this.mEncodingRunnable = new SwEncodingRunnable(this.mDataQueue, this.mVideoDataBufferMax, this.mState, output, frameRate, getFrameBuffer(), matrixBitmap, thumbnailBitmap, pictureConverter, this.mVideoDataStartCount, this.mPreviewManagerCallback, errorHandler, queue, thumbnailMatrix, thumbnailCanvas, previewCanvas, this.mUseMp4);
                        for (Object plugin : this.mPluginManager.getEnabledPlugins()) {
/* 204 */                   if (plugin instanceof AbstractEncodingRunnable.Drawer) {
/* 205 */                       this.mEncodingRunnable.addAdditionalDrawer((AbstractEncodingRunnable.Drawer) plugin);
                            }
                        }
                    }
/* 209 */           this.mProcessThread = new Thread(this.mEncodingRunnable, "EncodingRunnable");
/* 210 */           this.mProcessThread.start();
/* 211 */           SLog.m447d("start 7 took {}ms.", Long.valueOf(System.currentTimeMillis() - start4));
/* 212 */           return true;
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 217 */       public MediaUtil.GenerateThumbnailsRunnable stop() {
/* 218 */           if (Looper.myLooper() == Looper.getMainLooper()) {
/* 219 */               this.mEncodingThreadWasRunning = true;
                    } else {
/* 221 */               BasicVineRecorder parent = this.mParentHolder.getParent();
/* 222 */               if (!parent.canKeepRecording()) {
/* 223 */                   this.mEncodingThreadWasRunning = true;
                        }
/* 225 */               finishLastIfNeeded();
/* 226 */               if (!parent.canKeepRecording()) {
/* 227 */                   System.gc();
/* 228 */                   RecordingFile file = parent.getFile();
/* 229 */                   RecordSession session = file.getSession();
/* 230 */                   return writeToFile(file, session.getSegments(), session.getVideoData(), session.getAudioData(), false);
                        }
                    }
/* 233 */           return null;
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 237 */       public void onNewSegmentStart() {
/* 238 */           this.mImageReceiver.onNewSegmentStart();
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 242 */       public void onEndRelativeTime(RecordSegment segmentToEnd) {
/* 243 */           this.mImageReceiver.offerLastFrame(segmentToEnd, null);
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 247 */       public SurfaceController.SurfaceListener getSurfaceListener(RecordController recordController) {
/* 248 */           return new BaseSurfaceListener(this.mParentHolder, recordController, this.mState);
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 252 */       public AudioReceiver prepareAudioReceiver(RecordSession session) {
/* 253 */           int currentAudioSampleCount = session.calculateMemoryBackedAudioCount();
/* 254 */           AudioArrays.AudioArrayType type = this.mHardwareEncoding ? AudioArrays.AudioArrayType.BYTE : AudioArrays.AudioArrayType.SHORT;
/* 256 */           SLog.m465i("Audio recorder initialized with count {}.", Integer.valueOf(currentAudioSampleCount));
/* 257 */           this.mAudioReceiver = new AudioDataReceiver(this.mClock, this.mState, this.mAudioDataBufferMax, currentAudioSampleCount, session.getDurationMs(), type);
/* 259 */           this.mAudioReceiver.setValidator(new C05131());
/* 274 */           this.mClock.printState();
/* 275 */           return this.mAudioReceiver;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.buffered.BufferedRecordProcessor$1 */
/* 257 */       class C05131 implements AudioDataReceiver.AudioLengthValidator {
/* 258 */           C05131() {
                    }

                    @Override // co.vine.android.recorder.AudioDataReceiver.AudioLengthValidator
/* 261 */           public boolean isAudioLengthResonable(long audioTimestampUs) {
                        RecordConfigUtils.RecordConfig config;
/* 262 */               BasicVineRecorder parent = BufferedRecordProcessor.access$000(BufferedRecordProcessor.this).getParent();
                        return (parent == null || (config = parent.getConfig()) == null || audioTimestampUs >= ((long) (config.maxDuration * 1000))) ? false : true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.buffered.BufferedRecordProcessor$2 */
/* 280 */       class C05142 implements BufferedCameraPreviewReceiver.PreviewStatusValidator {
/* 281 */           C05142() {
                    }

                    @Override // co.vine.android.recorder.buffered.BufferedCameraPreviewReceiver.PreviewStatusValidator
/* 284 */           public boolean validateOnFrameReceived(boolean emptyDataReceived) {
/* 285 */               BasicVineRecorder parent = BufferedRecordProcessor.access$000(BufferedRecordProcessor.this).getParent();
/* 286 */               if (!BufferedRecordProcessor.access$100(BufferedRecordProcessor.this).recordingAudio() && parent != null) {
/* 288 */                   parent.receivedFirstFrameAfterStartingPreview();
                        }
/* 291 */               if (!emptyDataReceived || parent == null) {
                            return true;
                        }
/* 292 */               parent.onDeviceIssue(new VineLoggingException("Camera does not return proper frames."), DeviceIssue.CAMERA_NULL_FRAMES);
/* 294 */               return false;
                    }
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 280 */       public void prepareImageReceiver(RecordSession session) {
/* 281 */           this.mVideoDataStartCount = session.getVideoDataCount();
/* 282 */           this.mImageReceiver.setValidator(new C05142());
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 303 */       public void setAudioTrim(boolean enabled) {
/* 304 */           if (this.mAudioReceiver != null) {
/* 305 */               this.mAudioReceiver.setAudioTrim(enabled);
                    }
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 310 */       public Camera.PreviewCallback getCameraPreviewCallback() {
/* 311 */           return this.mImageReceiver;
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 315 */       public void onSessionTimestampChanged(RecordSession session) {
/* 316 */           if (this.mAudioReceiver != null) {
/* 317 */               this.mAudioReceiver.onSessionTimestampChanged(session);
/* 318 */               if (this.mOutput != null) {
/* 319 */                   this.mClock.updateAudioTimestamp(true, this.mAudioReceiver.getCurrentTimestampUs());
                        }
                    }
/* 322 */           this.mImageReceiver.updateLastFrameTimestampUs();
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 326 */       public PreviewManagerCallback getPreviewManagerCallback() {
/* 327 */           return this.mPreviewManagerCallback;
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 331 */       public void swapSession(RecordSession session) {
/* 332 */           this.mVideoDataBufferMax = session.getVideoData();
/* 333 */           this.mAudioDataBufferMax = session.getAudioData();
/* 334 */           this.mOutput = null;
/* 335 */           this.mImageReceiver.onSessionSwapped();
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 340 */       public void makePreview(RecordingFile file, RecordSegment segment, boolean getLastSegmentOnlyMode, boolean ignoreTrim) {
                    AbstractCombiningRunnable combiningRunnable;
/* 341 */           if (!getLastSegmentOnlyMode) {
                        try {
                            if (segment.videoPath != null && new File(segment.videoPath).exists()) {
/* 373 */                       return;
                            }
                        } catch (Exception e) {
/* 371 */                   CrashUtil.logException(e, "Cannot make previews", new Object[0]);
/* 373 */                   return;
                        }
                    }
/* 342 */           String output = file.getPreviewVideoPath() + ".temp_video" + RecordConfigUtils.VIDEO_CONTAINER_EXT;
/* 344 */           if (segment.removed) {
/* 345 */               SLog.m464i("Do not make preview as this is already removed.");
/* 373 */               return;
                    }
/* 348 */           if (this.mHardwareEncoding) {
/* 349 */               int frameRate = segment.getCameraSetting().fps;
/* 351 */               combiningRunnable = new HwCombiningRunnable(file, segment, RecordConfigUtils.newVideoRecorder(this.mContext, output, frameRate), this.mFinishProcessTask, getLastSegmentOnlyMode);
                    } else {
/* 353 */               CameraSetting setting = segment.getCameraSetting();
/* 354 */               SwVineFrameRecorder videoRecorder = RecordConfigUtils.newVideoRecorder(output, setting == null ? 30 : setting.fps, 480, this.mUseMp4);
                    try {
/* 357 */                   videoRecorder.start();
                    } catch (Exception e) {
                        CrashUtil.logException(e, "Cannot start preview recorder", new Object[0]);
                        return;
                    }
/* 358 */               combiningRunnable = SwCombiningRunnable.newSinglePreview(file, segment, videoRecorder, this.mFinishProcessTask, getLastSegmentOnlyMode, ignoreTrim);
                    }
/* 361 */           if (segment.removed) {
/* 362 */               SLog.m464i("Do not make preview as this is already removed.");
/* 373 */               return;
                    }
/* 365 */           MediaUtil.GenerateThumbnailsRunnable r = combiningRunnable.combineVideos();
/* 366 */           if (r != null) {
/* 367 */               r.run();
                    }
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 377 */       public MediaUtil.GenerateThumbnailsRunnable writeToFile(RecordingFile file, ArrayList<RecordSegment> editedSegments, byte[] videoData, AudioArray audioData, boolean preview) {
                    SwVineFrameRecorder videoRecorder;
                    HwVineFrameRecorder videoRecorder2;
                    try {
/* 378 */               int frameRate = RecordSegment.getFrameRate(editedSegments);
/* 379 */               if (this.mHardwareEncoding) {
/* 381 */                   if (preview) {
/* 382 */                       videoRecorder2 = RecordConfigUtils.newVideoRecorder(this.mContext, file.getPreviewVideoPath() + ".temp_video.mp4", frameRate);
                            } else {
/* 386 */                       videoRecorder2 = RecordConfigUtils.newVideoRecorder(this.mContext, this.mOutput + ".video.mp4", frameRate);
                            }
/* 388 */                   HwCombiningRunnable combiningRunnable = new HwCombiningRunnable(file, preview, audioData, videoData, editedSegments, videoRecorder2, this.mFinishProcessTask);
/* 391 */                   return combiningRunnable.combineVideos();
                        }
/* 394 */               if (preview) {
/* 395 */                   videoRecorder = RecordConfigUtils.newVideoRecorder(file.getPreviewVideoPath() + ".temp_video" + RecordConfigUtils.VIDEO_CONTAINER_EXT, frameRate, 480, this.mUseMp4);
                        } else {
/* 399 */                   videoRecorder = RecordConfigUtils.newVideoRecorder(this.mOutput, frameRate, 480, this.mUseMp4);
                        }
/* 402 */               videoRecorder.start();
/* 403 */               AbstractCombiningRunnable combiningRunnable2 = SwCombiningRunnable.newInstance(file, preview, audioData, videoData, editedSegments, videoRecorder, this.mFinishProcessTask);
/* 406 */               return combiningRunnable2.combineVideos();
                    } catch (Exception e) {
/* 409 */               CrashUtil.logException(e, "failed to write to file. ", new Object[0]);
/* 411 */               return null;
                    }
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 415 */       public void onExternalClipAdded(int microseconds) {
/* 416 */           if (this.mAudioReceiver != null) {
/* 417 */               this.mAudioReceiver.onExternalClipAdded(microseconds);
                    }
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 422 */       public void stopProcessing() {
/* 423 */           if (this.mEncodingRunnable != null) {
/* 424 */               this.mEncodingRunnable.terminate();
                    }
/* 426 */           if (this.mProcessThread != null) {
/* 427 */               this.mProcessThread.interrupt();
                    }
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 433 */       public void finishLastIfNeeded() {
                    try {
/* 434 */               if (this.mProcessThread != null) {
/* 435 */                   this.mProcessThread.join();
                        }
                    } catch (InterruptedException e) {
                    }
/* 440 */           this.mEncodingThreadWasRunning = false;
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 444 */       public boolean wasProcessing() {
/* 445 */           return this.mEncodingThreadWasRunning;
                }

/* 448 */       private Bitmap getPreviewBitmap() {
/* 449 */           return ResourceService.getPreviewBitmap();
                }

/* 452 */       private opencv_core.IplImage getFrameBuffer() {
/* 453 */           return ResourceService.getFrameImage();
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 457 */       public void releaseResources() {
/* 458 */           this.mDataQueue.clear();
/* 459 */           this.mEncodingRunnable = null;
/* 460 */           System.gc();
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 464 */       public BaseFinishProcessTask getFinishProcessTask() {
/* 465 */           return this.mFinishProcessTask;
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 469 */       public void setFinishProcessTask(BaseFinishProcessTask finishProcessTask) {
/* 470 */           this.mFinishProcessTask = finishProcessTask;
/* 471 */           if (this.mEncodingRunnable != null) {
/* 472 */               this.mEncodingRunnable.setAsyncTask(this.mFinishProcessTask);
                    }
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 477 */       public void onStopped(boolean stopPreview) {
/* 478 */           if (this.mFinishProcessTask != null) {
/* 479 */               this.mFinishProcessTask.publish(100);
                    }
/* 481 */           if (!stopPreview) {
/* 482 */               this.mImageReceiver.clearLastFrames();
                    }
                }

                @Override // co.vine.android.recorder.RecordProcessor
/* 487 */       public void setVideoTimeStampUs(long timestamp) {
/* 488 */           this.mImageReceiver.updateVideoTimestampUs(timestamp);
                }
            }
