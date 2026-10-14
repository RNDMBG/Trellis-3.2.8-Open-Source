            package co.vine.android.recorder;

            import android.annotation.SuppressLint;
            import android.app.Activity;
            import android.os.AsyncTask;
            import android.os.Handler;
            import android.support.annotation.NonNull;
            import android.support.annotation.Nullable;
            import android.text.TextUtils;
            import android.view.View;
            import android.view.WindowManager;
            import android.widget.Toast;
            import co.vine.android.VineLoggingException;
            import co.vine.android.player.VideoViewInterface;
            import co.vine.android.plugin.BaseRecorderPluginManager;
            import co.vine.android.recorder.buffered.BufferedPreviewManagerCallback;
            import co.vine.android.recorder.camera.CameraManager;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.recorder.camera.PreviewManager;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.ExceptionUtil;
            import co.vine.android.util.MediaUtil;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacv.cpp.opencv_imgproc;
            import java.io.File;
            import java.io.IOException;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashSet;
            import org.apache.commons.io.FileUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public abstract class BasicVineRecorder implements VideoViewInterface.OnErrorListener {
                public static final String LOG_RECORDER_INIT_COMPLETE = "Recorder fully initialized.";
                public static final String PREFS_CAPTURE = "capture";
                private static int STRING_WAITING_ON_CAMERA = -1;
                public static long sTimeTaken;
                public boolean detectedInvalidSession;
                public volatile RecordingFile finalFile;
                public MediaUtil.GenerateThumbnailsRunnable grabThumbnailsRunnable;
                protected Activity mActivity;
                private boolean mAlreadyStoppingForUnspportedReasons;
                protected View mCameraView;
                protected boolean mCanKeepRecording;
                protected volatile long mCurrentDurationMs;
                protected int mCurrentFrameRate;
                protected RecordingFile mCurrentRecordingFile;
                protected RecordSegment mCurrentSegment;
                protected boolean mDelayDialog;
                public volatile boolean mDiscardChanges;
                protected boolean mDoNotDeleteSession;
                protected final boolean mFastEncoding;
                protected final FinishProcessRunnable mFinishProcessRunnable;
                protected boolean mFinished;
                protected boolean mFrontFacing;
                protected final boolean mHardwareEncoding;
                protected ReusableHashAsyncTask mHashTask;
                protected volatile boolean mIsSwitchingCamera;
                protected boolean mNeverResumedRecorder;
                public volatile Runnable mOnCompleteConsumer;
                protected final BaseRecorderPluginManager mPluginManager;
                protected ProgressTimer mProgressTimer;
                protected int mRecordingFileDuration;
                protected OnResumeTask mResumeTask;
                protected RecordSession mSession;
                protected ResumeCameraAsyncTask mSwitchCameraTask;
                protected final int mThresholdMs;
                private WeakReference<Toast> mToast;
                protected final boolean mUseMp4;

                @Nullable
                protected RecordController mVideoController;
                protected final ArrayList<RecordSegment> mEditedSegments = new ArrayList<>();
                protected final Handler mHandler = new Handler();
                protected final ChangeProgressRunnable mChangeProgressRunnable = new ChangeProgressRunnable(this, null);
                protected final ArrayList<RecordSegment> mAddedSegments = new ArrayList<>();
                protected boolean mAutoFocusing = true;
                protected final HashSet<RecordSegment> mToRemove = new HashSet<>();
                protected boolean mEnabled = true;
                /** Prevent camera and surface callbacks from starting two recorder workers at once. */
                private volatile boolean mRecordingStartPending;

                protected abstract void adjustBoundaries(CameraSetting cameraSetting);

                public abstract void changeProgress(long j);

                @NonNull
                protected abstract Runnable getOnDeviceIssueAction(DeviceIssue deviceIssue);

                public abstract OnResumeTask getOnResumeTask(View view, String str);

                protected abstract boolean onStop();

                protected abstract void showCameraFailedToast();

/* 38 */        static /* synthetic */ WeakReference access$100(BasicVineRecorder x0) {
/* 39 */            return x0.mToast;
                }

/* 38 */        static /* synthetic */ WeakReference access$102(BasicVineRecorder x0, WeakReference x1) {
/* 39 */            x0.mToast = x1;
                    return x1;
                }

/* 38 */        static /* synthetic */ int access$200() {
/* 39 */            return STRING_WAITING_ON_CAMERA;
                }

/* 38 */        static /* synthetic */ void access$300(BasicVineRecorder x0, Throwable x1, DeviceIssue x2, boolean x3) {
/* 39 */            x0.onDeviceIssue(x1, x2, x3);
                }

/* 99 */        public static void initErrorStrings(int waitingOnCamera) {
/* 100 */           STRING_WAITING_ON_CAMERA = waitingOnCamera;
                }

/* 103 */       public BasicVineRecorder(int thresholdMs, BaseRecorderPluginManager manager, Activity activity, boolean startWithFrontFacingCamera, boolean hardwareEncoding, RecordSessionVersion version) {
/* 105 */           if (STRING_WAITING_ON_CAMERA == -1) {
/* 106 */               throw new IllegalStateException("Error strings not initialized");
                    }
/* 108 */           this.mPluginManager = manager;
/* 109 */           this.mPluginManager.setRecorder(this);
/* 110 */           this.mThresholdMs = thresholdMs;
/* 111 */           this.mNeverResumedRecorder = true;
/* 112 */           this.mHardwareEncoding = hardwareEncoding;
/* 113 */           this.mFastEncoding = RecordConfigUtils.isFastEncoding(hardwareEncoding);
/* 114 */           this.mUseMp4 = version != RecordSessionVersion.SW_WEBM;
/* 115 */           this.mVideoController = new RecordController(this, activity, this.mHardwareEncoding, this.mFastEncoding, this.mUseMp4, manager);
/* 116 */           this.mActivity = activity;
/* 117 */           this.mFrontFacing = startWithFrontFacingCamera || RecordConfigUtils.isDefaultFrontFacing();
/* 118 */           this.mFinishProcessRunnable = getFinishProcessRunnable();
                }

/* 121 */       protected FinishProcessRunnable getFinishProcessRunnable() {
/* 122 */           return new FinishProcessRunnable();
                }

/* 129 */       public void start(String tag, boolean stopPrevious, boolean adjustBoundaries) {
/* 130 */           long start = System.currentTimeMillis();
/* 131 */           if (!this.mCanKeepRecording) {
/* 132 */               CrashUtil.logException(new IllegalStateException("You cannot start recording again if it is finished."));
/* 133 */               CrashUtil.log("Start failed.");
                } else if (this.mVideoController != null) {
/* 136 */               if (adjustBoundaries) {
/* 137 */                   adjustBoundaries(this.mVideoController.getCameraSetting());
                        }
/* 139 */               if (this.mVideoController.isRecordingStarted() && stopPrevious) {
/* 140 */                   stop("stopPrevious", new RunnableC04701(tag), true, false);
                } else {
                    synchronized (this) {
                        if (this.mRecordingStartPending || this.mVideoController.isRecordingStarted()) {
                            return;
                        }
                        this.mRecordingStartPending = true;
                    }
/* 147 */                   // Encoder and AudioRecord initialization can block on modern
/* 148 */                   // devices. Keep the recording gesture responsive while they start.
/* 149 */                   final String recordingTag = tag;
/* 150 */                   new Thread(new Runnable() {
/* 151 */                       @Override
/* 152 */                       public void run() {
                            try {
/* 153 */                           BasicVineRecorder.this.startRecording(recordingTag);
                            } finally {
                                BasicVineRecorder.this.mRecordingStartPending = false;
                            }
/* 154 */                       }
/* 155 */                   }, "VineRecorderStart").start();
                        }
                    }
/* 151 */           CrashUtil.log("Start called from {} took {}ms.", tag, Long.valueOf(System.currentTimeMillis() - start));
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.BasicVineRecorder$1 */
/* 138 */       class RunnableC04701 implements Runnable {
                    final /* synthetic */ String val$tag;

/* 139 */           RunnableC04701(String str) {
                        this.val$tag = str;
                    }

                    @Override // java.lang.Runnable
/* 142 */           public void run() {
/* 143 */               BasicVineRecorder.this.startRecording(this.val$tag);
                    }
                }

/* 154 */       public void setEnabled(boolean enabled) {
/* 155 */           this.mEnabled = enabled;
                }

/* 158 */       public Handler getHandler() {
/* 159 */           return this.mHandler;
                }

/* 162 */       public boolean canKeepRecording() {
                    return this.mCanKeepRecording && !this.mDiscardChanges;
                }

/* 166 */       public void onFinishPressed() {
/* 167 */           setHasPreviewedAlreadyIfNeeded();
/* 168 */           finish("onFinishPressed");
                }

                @Nullable
/* 175 */       public RecordConfigUtils.RecordConfig getConfig() {
/* 176 */           RecordSession session = this.mSession;
/* 177 */           if (session != null) {
/* 178 */               return session.getConfig();
                    }
                    return null;
                }

/* 184 */       public boolean isRecordingSegment() {
                    return this.mCurrentSegment != null;
                }

/* 188 */       protected synchronized void startRecording(String tag) {
                    LinkageError e;
                    try {
                        Activity activity = this.mActivity;
/* 190 */               CrashUtil.log("Start recording called from {}.", tag);
/* 191 */               if (activity != null && this.mVideoController != null) {
/* 192 */                   boolean isCameraReady = this.mVideoController.isCameraReady();
/* 193 */                   if (!isCameraReady) {
/* 194 */                       CrashUtil.log("Start recording.");
/* 195 */                       isCameraReady = this.mVideoController.openDefaultCamera(this.mFrontFacing, false);
                            }
/* 197 */                   if (isCameraReady) {
/* 198 */                       // Preview is started by RecordController after the processor
/* 199 */                       // and camera-frame callback have been prepared.
/* 201 */                       RecordSession session = this.mCurrentRecordingFile.getSession();
/* 202 */                       if (!this.mVideoController.isRecordingStarted()) {
/* 203 */                           session.setAudioDataCount(session.calculateAudioCount());
/* 204 */                           session.setVideoDataCount(session.calculateVideoCount());
                                    try {
/* 206 */                               if (!this.mHardwareEncoding) {
/* 207 */                                   opencv_imgproc.tryLoad();
                                        }
/* 209 */                               long start = System.currentTimeMillis();
/* 210 */                               this.mVideoController.start(activity, this.mCurrentRecordingFile.getVideoPath(), session);
/* 211 */                               CrashUtil.log("Start recording: {} took {}ms.", this.mCurrentRecordingFile.getVideoPath(), Long.valueOf(System.currentTimeMillis() - start));
                                    } catch (RecordController.RecordControllerReadyStartedException e2) {
/* 215 */                               CrashUtil.logException(e2);
                                    } catch (ExceptionInInitializerError e3) {
                                        e = e3;
/* 213 */                               onDeviceIssue(e, DeviceIssue.LIBS_NOT_COMPATIBLE);
                                    } catch (NoClassDefFoundError e4) {
                                        e = e4;
/* 213 */                               onDeviceIssue(e, DeviceIssue.LIBS_NOT_COMPATIBLE);
                                    } catch (UnsatisfiedLinkError e5) {
                                        e = e5;
/* 213 */                               onDeviceIssue(e, DeviceIssue.LIBS_NOT_COMPATIBLE);
                                    }
                                }
                            } else {
/* 219 */                       showCameraFailedToast();
                            }
/* 221 */                   this.mVideoController.waitForPreviewToStart();
/* 222 */                   if (!this.mVideoController.isPreviewing()) {
/* 223 */                       this.mVideoController.startPreview();
                            }
/* 225 */                   this.mVideoController.onRecordingStarted();
/* 226 */                   SLog.dWithTag("TestLog", LOG_RECORDER_INIT_COMPLETE);
                        } else {
/* 228 */                   SLog.m455e("Activity is not in the right state. ");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 232 */       public RecordingFile swapSession(String tag, RecordingFile file) {
/* 233 */           if (file != null) {
/* 234 */               CrashUtil.log("Swap new Recorder for {} from {}.", tag, file.folder.getAbsolutePath());
/* 235 */               this.mSession = file.getSession();
/* 236 */               if (SLog.sLogsOn) {
/* 237 */                   SLog.m466i("[session] Swapping file {} for {}.", Integer.valueOf(this.mCurrentRecordingFile == null ? 0 : this.mCurrentRecordingFile.hashCode()), Integer.valueOf(file.hashCode()));
                        }
/* 239 */               this.mCurrentRecordingFile = file;
/* 240 */               this.mFinished = false;
/* 241 */               this.mEditedSegments.clear();
/* 242 */               this.mEditedSegments.addAll(this.mSession.getSegments());
/* 243 */               this.mToRemove.clear();
/* 244 */               this.mAddedSegments.clear();
/* 245 */               this.mCurrentRecordingFile.editedSegments = this.mEditedSegments;
/* 246 */               swapTimestampsFromSegments(this.mSession, this.mEditedSegments);
/* 247 */               this.mCanKeepRecording = true;
/* 248 */               setHasPreviewedAlreadyIfNeeded();
/* 249 */               changeProgress(this.mCurrentDurationMs);
/* 250 */               this.mDiscardChanges = false;
/* 251 */               this.finalFile = null;
/* 252 */               this.mVideoController.swapSession();
                    }
/* 254 */           return file;
                }

/* 257 */       public boolean hasSessionFile() {
                    return this.mCurrentRecordingFile != null;
                }

/* 261 */       public RecordingFile getCurrentRecordingFile() {
/* 262 */           return this.mCurrentRecordingFile;
                }

                @Override // co.vine.android.player.VideoViewInterface.OnErrorListener
/* 268 */       public boolean onError(VideoViewInterface view, int what, int extra) {
/* 269 */           return false;
                }

/* 272 */       public boolean isCurrentlyRecording() {
                    return this.mCurrentSegment != null;
                }

/* 276 */       public ByteBufferQueue.MemoryResponder getMemoryResponder() {
/* 277 */           if (this.mActivity instanceof ByteBufferQueue.MemoryResponder) {
/* 278 */               return (ByteBufferQueue.MemoryResponder) this.mActivity;
                    }
                    return null;
                }

/* 284 */       public boolean onBackPressed(boolean isEditing) {
/* 285 */           return this.mPluginManager.onBackPressed(isEditing);
                }

/* 288 */       public RecordSession getSesion() {
/* 289 */           if (this.mSession != null) {
/* 290 */               return this.mSession;
                    }
                    return null;
                }

/* 296 */       public ArrayList<RecordSegment> getEditedSegments() {
/* 297 */           return this.mEditedSegments;
                }

/* 300 */       public boolean canChangeFocus() {
                    return this.mCurrentSegment == null && this.mVideoController.canChangeFocus();
                }

/* 304 */       public boolean isAutoFocusing() {
/* 305 */           return this.mAutoFocusing;
                }

/* 308 */       public boolean setAutoFocusing(boolean autoFocusing) {
/* 309 */           RecordController controller = this.mVideoController;
/* 310 */           if (controller == null) {
                        return false;
                    }
/* 311 */           this.mAutoFocusing = autoFocusing;
/* 312 */           this.mVideoController.setAutoFocus(this.mAutoFocusing);
/* 313 */           return this.mAutoFocusing;
                }

/* 318 */       public void switchCamera() {
/* 319 */           if (!this.mIsSwitchingCamera && this.mCanKeepRecording) {
/* 321 */               if (canSwitchCamera()) {
/* 322 */                   this.mSwitchCameraTask = getSwitchCameraTask();
/* 323 */                   this.mSwitchCameraTask.execute(new Void[0]);
                        } else {
/* 325 */                   CrashUtil.logException(new VineLoggingException("You can not switch camera after recording has started."), "Person trying to record and switch? not cool.", new Object[0]);
                        }
                    }
                }

/* 331 */       public ResumeCameraAsyncTask getSwitchCameraTask() {
/* 332 */           throw new UnsupportedOperationException();
                }

/* 335 */       public boolean hasEditedSegments() {
                    return this.mEditedSegments.size() > 0;
                }

/* 339 */       public Activity getActivity() {
/* 340 */           return this.mActivity;
                }

/* 343 */       public RecordSessionVersion getVersion() {
/* 344 */           if (this.mCurrentRecordingFile != null) {
/* 345 */               return this.mCurrentRecordingFile.version;
                    }
                    return null;
                }

/* 351 */       public boolean canSwitchFlash() {
                    return this.mVideoController != null && this.mVideoController.canSwitchFlash();
                }

/* 355 */       public CameraSetting getCurrentCameraSetting() {
/* 356 */           return this.mVideoController.getCameraSetting();
                }

/* 359 */       public void setDelayDialog(boolean delayDialog) {
/* 360 */           this.mDelayDialog = delayDialog;
                }

/* 363 */       public void postOnHandlerLoop(Runnable runnable) {
/* 364 */           this.mHandler.post(runnable);
                }

/* 367 */       public void onPreviewError(RuntimeException callbackException) {
/* 368 */           throw callbackException;
                }

/* 372 */       protected class ResumeCameraAsyncTask extends AsyncTask<Void, Void, Void> {
                    private final boolean mSwitchCamera;



/* 377 */           public ResumeCameraAsyncTask(boolean switchCamera) {
/* 379 */               this.mSwitchCamera = switchCamera;
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 383 */           protected Void doInBackground(Void... params) {
/* 384 */               if (BasicVineRecorder.this.canSwitchCamera() || (!BasicVineRecorder.this.mVideoController.isRecording() && !this.mSwitchCamera)) {
                            try {
/* 386 */                       System.gc();
/* 387 */                       if (this.mSwitchCamera) {
/* 388 */                           BasicVineRecorder.this.mFrontFacing = BasicVineRecorder.this.mFrontFacing ? false : true;
                                }
/* 390 */                       if (BasicVineRecorder.this.mVideoController.openDefaultCamera(BasicVineRecorder.this.mFrontFacing, true)) {
/* 391 */                           CameraSetting setting = BasicVineRecorder.this.mVideoController.getCameraSetting();
/* 392 */                           if (setting != null) {
/* 393 */                               BasicVineRecorder.this.mCurrentFrameRate = setting.fps;
                                    }
/* 395 */                           BasicVineRecorder.this.start("Switch camera", false, true);
/* 404 */                           return null;
                                }
/* 397 */                       BasicVineRecorder.this.showCameraFailedToast();
/* 404 */                       return null;
                            } catch (Exception e) {
/* 401 */                       SLog.m459e("Error on cancel camera switching.", (Throwable) e);
/* 404 */                       return null;
                            }
                        }
/* 404 */               return null;
                    }

                    @Override // android.os.AsyncTask
/* 408 */           protected void onPreExecute() {
/* 409 */               BasicVineRecorder.this.mIsSwitchingCamera = true;
                    }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 413 */           protected void onPostExecute(Void aVoid) {
/* 414 */               BasicVineRecorder.this.mIsSwitchingCamera = false;
/* 415 */               BasicVineRecorder.this.mPluginManager.onResumeCameraAsyncTaskPostExecute();
                    }
                }

/* 420 */       protected abstract class OnResumeTask extends AsyncTask<Void, CharSequence, RecordingFile> {
                    public boolean isRunning;
                    protected final View mClicker;
                    protected long mStartTime;
                    protected final String mTag;
                    public boolean showDialogDelayed;

                    protected abstract void publishFinishLastSegmentProgress();

                    protected abstract void publishStartProgres();



/* 431 */           public OnResumeTask(View view, String tag) {
/* 433 */               this.mClicker = view;
/* 434 */               this.mTag = tag;
                    }

/* 441 */           protected boolean onMakingSureCameraReady(RecordController controller) {
/* 442 */               return controller.openDefaultCamera(BasicVineRecorder.this.mFrontFacing, false);
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 446 */           protected RecordingFile doInBackground(Void... params) {
/* 447 */               CrashUtil.log("OnResume task started from {}.", this.mTag);
/* 448 */               RecordController controller = BasicVineRecorder.this.mVideoController;
/* 449 */               if (controller == null) {
                            return null;
                        }
/* 452 */               if (controller.isProcessing()) {
/* 453 */                   publishFinishLastSegmentProgress();
                            try {
/* 455 */                       controller.finishLastIfNeeded();
                            } catch (Exception e) {
/* 457 */                       CrashUtil.logException(e, "Failed to finish last one.", new Object[0]);
                            }
/* 459 */                   publishStartProgres();
                        }
/* 461 */               BasicVineRecorder.this.stopProgressTimer();
/* 462 */               if (BasicVineRecorder.this.canKeepRecording()) {
/* 463 */                   CrashUtil.log("Async open camera");
/* 464 */                   if (onMakingSureCameraReady(controller)) {
/* 465 */                       CameraSetting cs = controller.getCameraSetting();
/* 466 */                       if (cs != null) {
/* 467 */                           BasicVineRecorder.this.mCurrentFrameRate = cs.fps;
                                }
/* 469 */                       if (BasicVineRecorder.this.mHashTask != null) {
/* 470 */                           BasicVineRecorder.this.mHashTask.reset();
                                }
/* 472 */                       BasicVineRecorder.this.startProgressTimer();
/* 473 */                       CrashUtil.log("Open camera successful: {} fps.", Integer.valueOf(BasicVineRecorder.this.mCurrentFrameRate));
/* 474 */                       if (BasicVineRecorder.this.mSession == null) {
                                    return null;
                                }
/* 477 */                       RecordConfigUtils.RecordConfig config = BasicVineRecorder.this.mSession.getConfig();
/* 479 */                       while (controller != null && config != null && BasicVineRecorder.this.mCurrentDurationMs < config.maxDuration && !controller.isAudioReady()) {
/* 481 */                           SLog.m446d("Wait for audio to be ready. " + this);
/* 482 */                           if (isCancelled()) {
                                        return null;
                                    }
                                    try {
/* 486 */                               Thread.sleep(100L);
                                    } catch (InterruptedException e2) {
                                    }
/* 490 */                           config = BasicVineRecorder.this.mSession != null ? BasicVineRecorder.this.mSession.getConfig() : null;
/* 491 */                           controller = BasicVineRecorder.this.mVideoController;
                                }
                                return null;
                            }
/* 494 */                   if (isCancelled()) {
                                return null;
                            }
/* 495 */                   BasicVineRecorder.this.showCameraFailedToast();
                            return null;
                        }
/* 499 */               Activity activity = BasicVineRecorder.this.mActivity;
/* 500 */               if (BasicVineRecorder.this.mHashTask != null && BasicVineRecorder.this.mHashTask.isComplete() && activity != null) {
/* 501 */                   activity.finish();
                            return null;
                        }
/* 503 */               if (BasicVineRecorder.this.mHashTask != null && BasicVineRecorder.this.mHashTask.getComputedFile() != null) {
/* 504 */                   return BasicVineRecorder.this.mHashTask.getComputedFile();
                        }
/* 506 */               BasicVineRecorder.this.startHashTask(false);
                        return null;
                    }

                    @Override // android.os.AsyncTask
/* 514 */           protected void onPreExecute() {
/* 515 */               this.mStartTime = System.currentTimeMillis();
/* 516 */               if (this.mClicker != null) {
/* 517 */                   this.mClicker.setEnabled(false);
                        }
/* 519 */               if (BasicVineRecorder.this.canKeepRecording() && !BasicVineRecorder.this.mVideoController.isRecordingStarted() && !isCancelled()) {
                            try {
/* 523 */                       if (this.showDialogDelayed) {
/* 524 */                           this.isRunning = true;
                                }
                            } catch (WindowManager.BadTokenException e) {
                            }
                        }
/* 532 */               SLog.m447d("OnResume PreExecute took {}ms.", Long.valueOf(System.currentTimeMillis() - this.mStartTime));
                    }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 536 */           protected void onPostExecute(RecordingFile needHashTask) {
/* 537 */               this.isRunning = false;
/* 538 */               if (this.mClicker != null) {
/* 539 */                   this.mClicker.setEnabled(true);
                        }
/* 541 */               if (needHashTask != null) {
/* 542 */                   BasicVineRecorder.this.mHashTask.resumeAll();
                        }
/* 544 */               long timeTaken = System.currentTimeMillis() - this.mStartTime;
                        if (timeTaken > BasicVineRecorder.sTimeTaken) {
/* 546 */                   BasicVineRecorder.sTimeTaken = timeTaken;
                        }
/* 548 */               BasicVineRecorder.this.mPluginManager.onResumeAsyncTaskPostExecute();
/* 549 */               SLog.m447d("OnResumeTask took {}ms.", Long.valueOf(timeTaken));
                    }
                }

/* 553 */       protected void startHashTask(boolean detectedInvalidSession) {
/* 554 */           this.mHashTask = new ReusableHashAsyncTask(this);
/* 555 */           this.detectedInvalidSession = detectedInvalidSession;
/* 556 */           this.mHashTask.execute(new RecordingFile[]{this.mCurrentRecordingFile});
                }

/* 559 */       public boolean canSwitchCamera() {
                    return CameraManager.hasFrontFacingCamera() && CameraManager.hasBackFacingCamera() && this.mVideoController != null && !this.mVideoController.isRecording();
                }

/* 564 */       protected void stopProgressTimer() {
/* 565 */           if (this.mProgressTimer != null) {
/* 566 */               this.mProgressTimer.release();
/* 567 */               this.mProgressTimer = null;
                    }
                }

/* 571 */       protected void startProgressTimer() {
/* 572 */           this.mProgressTimer = new ProgressTimer(this, this.mHandler, this.mThresholdMs);
/* 573 */           this.mProgressTimer.start();
                }

/* 576 */       public Runnable getOnCompleteConsumer() {
/* 577 */           return this.mOnCompleteConsumer;
                }

/* 580 */       public void setFinalFile(RecordingFile recordingFile) {
/* 581 */           this.finalFile = recordingFile;
                }

/* 584 */       public void onProgressMaxReached() {
                }

/* 587 */       public long getCurrentDuration() {
/* 588 */           return this.mCurrentDurationMs;
                }

/* 591 */       public RecordingFile getFile() {
/* 592 */           return this.mCurrentRecordingFile;
                }

/* 595 */       public void onAutoFocusComplete(boolean success) {
/* 596 */           SLog.m447d("Auto focus {}.", Boolean.valueOf(success));
                }

/* 599 */       public void postProgressUpdate(int progress) {
/* 600 */           this.mChangeProgressRunnable.progress = progress;
/* 601 */           this.mHandler.post(this.mChangeProgressRunnable);
                }

/* 602 */       private class ChangeProgressRunnable implements Runnable {
                    public int progress;

/* 603 */           private ChangeProgressRunnable() {
                    }

/* 603 */           /* synthetic */ ChangeProgressRunnable(BasicVineRecorder x0, RunnableC04701 x1) {
/* 604 */               this();
                    }

                    @Override // java.lang.Runnable
/* 608 */           public void run() {
/* 609 */               BasicVineRecorder.this.changeProgress(this.progress);
                    }
                }

/* 615 */       public void setCameraView(View cameraView) {
/* 616 */           this.mCameraView = cameraView;
                }

/* 620 */       public void initPreviewSurface() {
                    try {
/* 621 */               this.mVideoController.setPreviewSurface(this.mCameraView);
                    } catch (Exception e) {
/* 623 */               CrashUtil.logException(e, "Cannot resume.", new Object[0]);
                    }
                }

/* 627 */       public View getCameraView() {
/* 628 */           return this.mCameraView;
                }

/* 631 */       public boolean isResuming() {
/* 632 */           OnResumeTask task = this.mResumeTask;
                    return task != null && task.isRunning;
                }

/* 636 */       public String getThumbnailPath() {
/* 637 */           return this.mCurrentRecordingFile.getThumbnailPath();
                }

/* 640 */       public long getTimestamp() {
/* 641 */           return this.mVideoController.getTimestampMs();
                }

/* 644 */       public void setDiscardChanges(boolean discardChanges) {
/* 645 */           this.mDiscardChanges = discardChanges;
                }

/* 648 */       public void onCameraReady(RecordController controller) {
/* 649 */           if (this.mActivity != null) {
/* 650 */               if (controller.isSurfaceReady() && !controller.isRecordingStarted()) {
/* 651 */                   // Start the complete pipeline asynchronously; start() prepares
/* 652 */                   // the callback before opening preview.
/* 653 */                   start("Camera ready", false, true);
/* 659 */                   return;
                        }
/* 653 */               SLog.m448d("Do not start recording: {} {}", Boolean.valueOf(controller.isSurfaceReady()), Boolean.valueOf(controller.isRecordingStarted()));
/* 654 */               if (controller.isRecordingStarted() && !controller.isPreviewing()) {
/* 655 */                   controller.startPreview();
                        }
                    }
                }

/* 661 */       public void onSurfaceReady(RecordController controller) {
/* 662 */           if (controller.isCameraReady()) {
/* 663 */               SLog.m446d("Start recording on surface ready.");
/* 664 */               if (!controller.isRecordingStarted()) {
/* 665 */                   // The recording worker prepares the frame pipeline before preview.
/* 666 */                   start("Surface Ready", false, true);
/* 673 */                   return;
                        } else {
/* 667 */                   adjustBoundaries(controller.getCameraSetting());
/* 668 */                   controller.startPreview();
/* 673 */                   return;
                        }
                    }
/* 671 */           SLog.m448d("Do not start recording: {} {}.", (Object) false, (Object) Boolean.valueOf(controller.isRecordingStarted()));
                }

/* 675 */       public boolean startRelativeTime() {
/* 676 */           if (this.mVideoController == null || this.mSession == null || this.mSession.getConfig() == null || !this.mVideoController.isRecordingStarted() || !this.mCanKeepRecording || !this.mVideoController.isCameraReady() || this.mCurrentDurationMs >= this.mSession.getConfig().maxDuration) {
                        return false;
                    }
/* 680 */           if (!this.mVideoController.hasCameraReceivingFrames()) {
/* 681 */               this.mHandler.post(new RunnableC04712());
                        return false;
                    }
/* 701 */           if (this.mCurrentSegment != null && !endRelativeTime()) {
                        return false;
                    }
/* 706 */           this.mCurrentDurationMs = ((long) this.mRecordingFileDuration) - System.currentTimeMillis();
/* 707 */           this.mPluginManager.onStartRelativeTime(this);
/* 708 */           this.mCurrentSegment = new RecordSegment(this.mCurrentDurationMs, this.mVideoController.getCameraSetting().fps);
                    CrashUtil.log("START RELATIVE TIME: " + this.mVideoController + " bc: " + MasterPreviewCallback.getInstance().getCallbackStateString() + ", added: " + (BufferedPreviewManagerCallback.isUsed() ? Integer.valueOf(BufferedPreviewManagerCallback.getInstance().getAddedBufferArrayCount()) : "n/a"));
/* 711 */           this.mVideoController.setRecordingAudio(true);
/* 712 */           this.mVideoController.setRecording(this.mCurrentSegment);
                    return true;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.BasicVineRecorder$2 */
/* 679 */       class RunnableC04712 implements Runnable {
/* 680 */           RunnableC04712() {
                    }

                    @Override // java.lang.Runnable
/* 683 */           public void run() {
/* 684 */               if (BasicVineRecorder.this.mActivity != null) {
/* 685 */                   Toast toast = null;
/* 686 */                   if (BasicVineRecorder.access$100(BasicVineRecorder.this) != null) {
/* 687 */                       toast = (Toast) BasicVineRecorder.access$100(BasicVineRecorder.this).get();
                            }
/* 689 */                   if (toast == null) {
/* 690 */                       toast = Toast.makeText(BasicVineRecorder.this.mActivity, BasicVineRecorder.access$200(), 0);
                            } else {
/* 692 */                       toast.setText(BasicVineRecorder.access$200());
                            }
/* 694 */                   BasicVineRecorder.access$102(BasicVineRecorder.this, new WeakReference(toast));
/* 695 */                   toast.show();
                        }
                    }
                }

/* 719 */       public synchronized boolean endRelativeTime() {
                    try {
                        if (this.mCurrentSegment != null) {
/* 721 */                   CrashUtil.log("END RELATIVE TIME.");
/* 722 */                   this.mVideoController.onEndRelativeTime(this.mCurrentSegment);
/* 723 */                   this.mRecordingFileDuration = (int) this.mVideoController.getTimestampMs();
/* 724 */                   this.mCurrentRecordingFile.getSession().add(this.mCurrentSegment);
/* 725 */                   this.mEditedSegments.add(this.mCurrentSegment);
/* 726 */                   this.mAddedSegments.add(this.mCurrentSegment);
/* 727 */                   this.mCurrentRecordingFile.isDirty = true;
/* 728 */                   this.mCurrentSegment = null;
/* 729 */                   this.mPluginManager.onEndRelativeTime(this);
/* 730 */                   this.mPluginManager.onSegmentDataChanged(this.mEditedSegments);
/* 731 */                   this.mCurrentDurationMs = this.mRecordingFileDuration;
/* 732 */                   this.mVideoController.setRecordingAudio(false);
/* 733 */                   this.mVideoController.setRecording(this.mCurrentSegment);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return true;
                }

/* 738 */       public int getEditedDurationMs() {
/* 739 */           int duration = 0;
                    for (RecordSegment segment : this.mEditedSegments) {
/* 741 */               if (!segment.removed) {
/* 742 */                   duration += segment.getDurationMs();
                        }
                    }
/* 745 */           return duration;
                }

/* 748 */       public void changeFocusTo(float x, float y) {
/* 749 */           this.mVideoController.autoFocus((int) x, (int) y);
                }

/* 752 */       public void onUiPaused() {
/* 753 */           this.mActivity = null;
/* 754 */           this.mCameraView = null;
/* 755 */           this.mOnCompleteConsumer = null;
/* 756 */           onPause();
/* 757 */           this.mPluginManager.onPause();
                }

/* 760 */       public void onProgressThresholdReached() {
                }

/* 763 */       public void onPause() {
/* 764 */           this.mNeverResumedRecorder = false;
/* 765 */           if (this.mSwitchCameraTask != null) {
/* 766 */               this.mSwitchCameraTask.cancel(true);
                    }
/* 768 */           if (this.mResumeTask != null) {
/* 769 */               SLog.m446d("Cancel resume task: " + this.mResumeTask);
/* 770 */               this.mResumeTask.cancel(true);
                    }
/* 772 */           PreviewManager.getInstance().cancelPendingPreviews();
/* 773 */           this.mFinishProcessRunnable.run("onPause", true, false);
/* 774 */           this.mVideoController.onPause();
/* 775 */           if (this.mHashTask != null) {
/* 776 */               this.mHashTask.cancel(false);
                    }
/* 778 */           if (this.mProgressTimer != null) {
/* 779 */               this.mProgressTimer.release();
/* 780 */               this.mProgressTimer = null;
                    }
                }

/* 784 */       protected void finish(String tag) {
/* 785 */           this.mFinished = true;
/* 786 */           if (this.mVideoController != null && this.mVideoController.isRecordingStarted()) {
/* 787 */               stop(tag, null, true, false);
/* 788 */               this.mCanKeepRecording = false;
                    } else if (this.mOnCompleteConsumer != null) {
/* 791 */               this.mOnCompleteConsumer.run();
                    }
                }

/* 796 */       public boolean isFinished() {
/* 797 */           return this.mFinished;
                }

/* 805 */       protected void stop(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
/* 806 */           if (onStop()) {
/* 807 */               BaseFinishProcessTask finishProcessTask = getFinishProcessTask(tag, onComplete, releasePreview, saveSession);
/* 808 */               this.mVideoController.setFinishProcessTask(finishProcessTask);
/* 809 */               finishProcessTask.execute(new Void[0]);
                    }
                }

/* 813 */       public BaseFinishProcessTask getFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
/* 814 */           return new FinishProcessTask(tag, onComplete, releasePreview, saveSession || this.mCurrentRecordingFile.isSavedSession);
                }

/* 816 */       public class FinishProcessTask extends BaseFinishProcessTask {

/* 819 */           public FinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
/* 821 */               super(tag, onComplete, releasePreview, saveSession);
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 825 */           protected Void doInBackground(Void... params) {
/* 826 */               BasicVineRecorder.this.mFinishProcessRunnable.run(this.tag, this.releasePreview, this.saveSession);
/* 827 */               return null;
                    }
                }

/* 832 */       public boolean isSavedSession() {
/* 833 */           return this.mCurrentRecordingFile.isSavedSession;
                }

/* 836 */       public void modifyWhiteBalance(boolean up) {
/* 837 */           if (this.mVideoController != null) {
/* 838 */               this.mVideoController.modifyWhiteBalance(up);
                    }
                }

/* 842 */       public void modifyExposure(boolean up) {
/* 843 */           if (this.mVideoController != null) {
/* 844 */               this.mVideoController.modifyExposure(up);
                    }
                }

/* 848 */       public boolean isSessionDirty() {
                    return this.mCurrentRecordingFile != null && this.mCurrentRecordingFile.isDirty;
                }

/* 852 */       public void modifySceneMode(boolean up) {
/* 853 */           if (this.mVideoController != null) {
/* 854 */               this.mVideoController.modifySceneMode(up);
                    }
                }

/* 858 */       public void modifyColorEffects(boolean up) {
/* 859 */           if (this.mVideoController != null) {
/* 860 */               this.mVideoController.modifyColorEffects(up);
                    }
                }

/* 864 */       public void modifyAntiBanding(boolean up) {
/* 865 */           if (this.mVideoController != null) {
/* 866 */               this.mVideoController.modifyAntiBanding(up);
                    }
                }

/* 870 */       public void switchImageStabilization() {
/* 871 */           if (this.mVideoController != null) {
/* 872 */               this.mVideoController.switchImageStabilization();
                    }
                }

/* 874 */       public class FinishProcessRunnable {
                    private boolean isResumed;

/* 875 */           public FinishProcessRunnable() {
                    }

/* 880 */           public void reset() {
/* 881 */               this.isResumed = false;
                    }

/* 884 */           public void onIncompleteSessionFinish() {
/* 885 */               if (BasicVineRecorder.this.mFinished && !BasicVineRecorder.this.canKeepRecording() && BasicVineRecorder.this.mHashTask != null && !BasicVineRecorder.this.mHashTask.isComplete() && !this.isResumed) {
/* 887 */                   this.isResumed = true;
/* 888 */                   BasicVineRecorder.this.mHashTask.resumeAll();
                        }
                    }

/* 893 */           public boolean doNotDeleteSession(boolean isFinishing) {
/* 894 */               return isFinishing;
                    }

/* 897 */           public boolean onMakeRecordingStop(String tag, boolean releasePreview, boolean saveSession) {
/* 898 */               if (BasicVineRecorder.this.mVideoController == null) {
                            return false;
                        }
/* 899 */               CrashUtil.log("Stop recording in VineRecorder from {}: ", tag);
/* 900 */               boolean wasRecordingStarted = BasicVineRecorder.this.mVideoController.isRecordingStarted();
/* 901 */               if (wasRecordingStarted) {
/* 902 */                   BasicVineRecorder.this.endRelativeTime();
/* 903 */                   BasicVineRecorder.this.grabThumbnailsRunnable = BasicVineRecorder.this.mVideoController.stop(BasicVineRecorder.this.mDiscardChanges, releasePreview);
                        }
/* 905 */               CrashUtil.log("Status: wasRecordingStarted {} releasePreview {} saveSession {} mDiscardChanges {} mDoNotDeleteSession {} mCurrentRecordingFile {}", Boolean.valueOf(wasRecordingStarted), Boolean.valueOf(releasePreview), Boolean.valueOf(saveSession), Boolean.valueOf(BasicVineRecorder.this.mDiscardChanges), Boolean.valueOf(BasicVineRecorder.this.mDoNotDeleteSession), BasicVineRecorder.this.mCurrentRecordingFile);
/* 909 */               return wasRecordingStarted;
                    }

/* 913 */           public void onCompleteSessionFinish(boolean saveSession) {
/* 914 */               RecordingFile recordingFile = BasicVineRecorder.this.mCurrentRecordingFile;
/* 915 */               if (recordingFile == null) {
/* 916 */                   CrashUtil.logException(new VineLoggingException("Failed to stop recording."));
/* 980 */                   return;
                        }
/* 919 */               RecordSession session = recordingFile.getSession();
/* 920 */               session.setAudioDataCount(session.calculateAudioCount());
/* 921 */               session.setVideoDataCount(session.calculateVideoCount());
/* 923 */               if (!BasicVineRecorder.this.mDiscardChanges) {
/* 924 */                   boolean isFinishing = BasicVineRecorder.this.mFinished && recordingFile.hasData() && !BasicVineRecorder.this.canKeepRecording();
/* 925 */                   BasicVineRecorder.this.mDoNotDeleteSession = doNotDeleteSession(isFinishing);
/* 926 */                   boolean isValidSession = BasicVineRecorder.this.mEditedSegments.size() > 0 && session.getVideoDataCount() > 0;
/* 927 */                   if (BasicVineRecorder.this.mEditedSegments.size() > 0 && !isValidSession) {
/* 928 */                       CrashUtil.log("Invalid session found.");
                            }
/* 930 */                   if (isValidSession) {
                                try {
/* 932 */                           RecordSessionManager.writeRecordingFile(recordingFile, saveSession);
                                } catch (IOException e) {
/* 934 */                           if (ExceptionUtil.isNoSpaceLeftException(e)) {
/* 935 */                               BasicVineRecorder.access$300(BasicVineRecorder.this, e, DeviceIssue.NOT_ENOUGH_SPACE, false);
/* 980 */                               return;
                                    } else {
/* 938 */                               if (saveSession) {
/* 939 */                                   BasicVineRecorder.access$300(BasicVineRecorder.this, e, DeviceIssue.STORAGE_NOT_READY, false);
/* 980 */                                   return;
                                        }
/* 942 */                               CrashUtil.log("Failed to save the files due to IOException: " + e.getMessage());
                                    }
                                }
                            } else if (!BasicVineRecorder.this.mCanKeepRecording) {
                                try {
/* 949 */                           RecordSessionManager.deleteSession(recordingFile.folder, "Invalid session");
                                } catch (IOException e2) {
/* 951 */                           CrashUtil.log("Failed to delete session: " + e2.getMessage());
                                }
                            }
/* 955 */                   if (saveSession) {
/* 956 */                       if (!recordingFile.isLastSession) {
/* 957 */                           recordingFile.isSavedSession = true;
/* 958 */                           recordingFile.isDirty = false;
                                }
/* 960 */                       if (isValidSession) {
/* 961 */                           BasicVineRecorder.this.cleanThumbnails(false);
                                }
                            }
/* 966 */                   if (isFinishing) {
/* 967 */                       BasicVineRecorder.this.startHashTask(isValidSession ? false : true);
/* 980 */                       return;
                            }
/* 980 */                   return;
                        }
/* 970 */               if (!BasicVineRecorder.this.mDoNotDeleteSession && !BasicVineRecorder.this.mCurrentRecordingFile.isSavedSession) {
                            try {
/* 972 */                       CrashUtil.log("Session {} deleted.", BasicVineRecorder.this.mCurrentRecordingFile.folder);
/* 973 */                       RecordSessionManager.deleteSession(BasicVineRecorder.this.mCurrentRecordingFile.folder, "Discard Changes.");
                            } catch (IOException e3) {
/* 975 */                       CrashUtil.logException(e3, "Failed to delete session.", new Object[0]);
                            }
                        }
/* 978 */               BasicVineRecorder.this.mToRemove.clear();
                    }

/* 982 */           public boolean isCompleteSession(boolean wasRecordingStarted) {
/* 983 */               return wasRecordingStarted;
                    }

/* 986 */           public synchronized void run(String tag, boolean releasePreview, boolean saveSession) {
                        try {
                            boolean wasRecordingStarted = onMakeRecordingStop(tag, releasePreview, saveSession);
/* 988 */                   onPreCompleteSession(wasRecordingStarted);
/* 989 */                   if (isCompleteSession(wasRecordingStarted)) {
/* 990 */                       onCompleteSessionFinish(saveSession);
                            } else {
/* 992 */                       onIncompleteSessionFinish();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

/* 997 */           protected void onPreCompleteSession(boolean wasRecordingStarted) {
                    }
                }

/* 1003 */      public void onDeviceHasNotEnoughSpaceLeft(Throwable e) {
/* 1004 */          onDeviceIssue(e, DeviceIssue.NOT_ENOUGH_SPACE);
                }

/* 1007 */      public void onDeviceIssue(Throwable e, DeviceIssue issue) {
/* 1008 */          onDeviceIssue(e, issue, true);
                }

/* 1011 */      private void onDeviceIssue(Throwable e, DeviceIssue issue, boolean stopIfNeeded) {
/* 1012 */          if (!this.mAlreadyStoppingForUnspportedReasons) {
/* 1013 */              this.mAlreadyStoppingForUnspportedReasons = true;
/* 1014 */              if (e != null) {
/* 1015 */                  CrashUtil.logException(e);
/* 1016 */                  CrashUtil.log("Device not supported happened.");
                        }
/* 1018 */              if (stopIfNeeded) {
/* 1019 */                  stopAndDiscardChanges("OnDeviceNotSupported", getOnDeviceIssueAction(issue), true);
                        } else {
/* 1022 */                  getOnDeviceIssueAction(issue).run();
                        }
                    }
                }

/* 1030 */      public void stopAndDiscardChanges(String tag, Runnable onComplete, boolean releasePreview) {
/* 1031 */          setDiscardChanges(true);
/* 1032 */          this.mFinishProcessRunnable.run(tag, releasePreview, false);
/* 1033 */          if (onComplete != null) {
/* 1034 */              onComplete.run();
                    }
                }

                @SuppressLint({"ShowToast"})
/* 1039 */      public void onUiResumed(Activity activity, Runnable onCompleteAsyncTask, boolean doNotResumeRecorder) {
/* 1040 */          this.mActivity = activity;
/* 1041 */          this.mDelayDialog = false;
/* 1042 */          this.mPluginManager.onResume(activity);
/* 1043 */          this.mVideoController.updateRotation(activity);
/* 1044 */          this.mOnCompleteConsumer = onCompleteAsyncTask;
/* 1045 */          this.mFinishProcessRunnable.reset();
                }

/* 1049 */      public boolean deleteCurrentDraftFolder(String tag) {
                    try {
/* 1050 */              RecordSessionManager.deleteSession(this.mCurrentRecordingFile.folder, tag);
/* 1051 */              return true;
                    } catch (IOException e) {
/* 1053 */              CrashUtil.logException(e);
/* 1054 */              return false;
                    }
                }

/* 1058 */      public boolean release() {
                    boolean abandoned = false;
/* 1059 */          CrashUtil.log("Recorder Released.");
/* 1060 */          this.mVideoController.releaseParent();
/* 1061 */          this.mVideoController.releaseCameraResources();
/* 1062 */          this.mVideoController.releaseResources();
/* 1063 */          this.mVideoController.releaseCallbacks();
/* 1064 */          this.mVideoController.logPreviewSizeExceptionIfNeeded();
/* 1065 */          boolean sessionDeleted = false;
/* 1066 */          if (this.mCurrentRecordingFile != null) {
/* 1067 */              File folder = this.mCurrentRecordingFile.folder;
/* 1068 */              if (!this.mDoNotDeleteSession && !RecordSessionManager.isSessionSaved(folder)) {
/* 1069 */                  sessionDeleted = deleteCurrentDraftFolder("release");
                        } else {
/* 1071 */                  File temporaryDataFile = RecordSessionManager.getDataFile(folder, false);
/* 1072 */                  if (temporaryDataFile.exists()) {
/* 1073 */                      FileUtils.deleteQuietly(temporaryDataFile);
                            }
                        }
/* 1076 */              SLog.m465i("Session deleted: {}", Boolean.valueOf(sessionDeleted));
                    }
/* 1078 */          this.mSession = null;
/* 1079 */          this.mCurrentRecordingFile = null;
/* 1080 */          this.mCurrentSegment = null;
/* 1081 */          this.mVideoController = null;
/* 1082 */          this.mAddedSegments.clear();
/* 1083 */          if (this.mEditedSegments.size() > 0 && sessionDeleted) {
                        abandoned = true;
                    }
/* 1084 */          this.mEditedSegments.clear();
/* 1085 */          return abandoned;
                }

/* 1088 */      protected void invalidateFinishButton() {
                }

/* 1091 */      protected void setHasPreviewedAlreadyIfNeeded() {
                }

/* 1094 */      protected void swapTimestampsFromSegments(RecordSession session, ArrayList<RecordSegment> segments) {
/* 1095 */          int timestamp = 0;
                    for (RecordSegment segment : segments) {
/* 1097 */              if (!segment.removed) {
/* 1098 */                  timestamp += segment.getDurationMs();
                        }
                    }
/* 1101 */          this.mCurrentDurationMs = timestamp;
/* 1102 */          int timestamp2 = timestamp * 1000;
/* 1103 */          this.mVideoController.setAudioTimestampUs(timestamp2);
/* 1104 */          this.mVideoController.setVideoTimeStampUs(timestamp2);
/* 1105 */          this.mRecordingFileDuration = (int) this.mCurrentDurationMs;
/* 1106 */          invalidateFinishButton();
/* 1107 */          changeProgress(this.mCurrentDurationMs);
/* 1108 */          this.mVideoController.onTimestampSwap(session);
/* 1109 */          this.mPluginManager.onSegmentDataChanged(this.mEditedSegments);
                }

/* 1112 */      public void cleanThumbnails(boolean discardNewOnes) {
/* 1113 */          HashSet<RecordSegment> toRemove = new HashSet<>(this.mToRemove);
/* 1114 */          this.mToRemove.clear();
/* 1115 */          if (discardNewOnes) {
/* 1116 */              toRemove.addAll(this.mAddedSegments);
                    }
                    for (RecordSegment segment : toRemove) {
/* 1119 */              String thumbnailPath = segment.getThumbnailPath();
/* 1120 */              if (!TextUtils.isEmpty(thumbnailPath)) {
/* 1121 */                  FileUtils.deleteQuietly(new File(thumbnailPath));
                        }
                    }
                }

/* 1126 */      public void onResume(String tag) {
/* 1127 */          boolean canKeepRecording = canKeepRecording();
                    Object[] objArr = new Object[4];
                    objArr[0] = Boolean.valueOf(this.mDelayDialog);
                    objArr[1] = Boolean.valueOf(canKeepRecording);
                    objArr[2] = false;
                    objArr[3] = Boolean.valueOf(this.finalFile != null);
/* 1128 */          CrashUtil.log("Resume VineRecorder: delayDialog: {} canKeepRecording: {} isEditing: {} NullFinalFile: {}.", objArr);
/* 1129 */          if (canKeepRecording) {
/* 1130 */              this.mResumeTask = getOnResumeTask(null, "OnResume_" + tag);
/* 1131 */              this.mResumeTask.showDialogDelayed = this.mDelayDialog;
/* 1132 */              this.mResumeTask.execute(new Void[0]);
                    } else if (this.finalFile != null) {
/* 1135 */              this.mOnCompleteConsumer.run();
                    }
                }

/* 1141 */      public void receivedFirstFrameAfterStartingPreview() {
                }
            }
