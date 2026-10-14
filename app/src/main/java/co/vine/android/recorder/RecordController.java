            package co.vine.android.recorder;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.graphics.Point;
            import android.hardware.Camera;
            import android.view.View;
import co.vine.android.plugin.BaseRecorderPluginManager;
            import co.vine.android.VineNotSupportedException;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioReceiver;
            import co.vine.android.recorder.audio.AudioRecordSource;
            import co.vine.android.recorder.audio.AudioSource;
            import co.vine.android.recorder.buffered.BufferedRecordProcessor;
            import co.vine.android.recorder.camera.CameraManager;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.recorder.camera.PreviewManager;
            import co.vine.android.service.ResourceService;
            import co.vine.android.util.AudioUtils;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.MediaUtil;
            import co.vine.android.util.SystemUtil;
            import com.edisonwang.android.slog.SLog;
            import java.io.IOException;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 35 */    public class RecordController implements ParentHolder {
                public static long sMaxKnownStopTime;
                private AudioSource mAudioSource;
                /* Hardware recording does not use the legacy FFmpeg frame recorder. */
                private final boolean mHardwareEncoding;
                private final boolean mFastEncoding;
                private PreviewManager.InvalidPreviewSizeException mInvalidPreviewSizeException;
                private BasicVineRecorder mParent;
                private final BaseRecorderPluginManager mPluginManager;
                private final RecordProcessor mRecordProcessor;
                private int mRotation;
                private final SurfaceController mSurfaceController;
                private boolean mFlash = false;
                private boolean mAutoFocus = true;
                private boolean mIsFocusing = false;
                private final RecordState mState = new RecordState();
                private final RecordClock mClock = new RecordClock();
                private final CameraManager.CameraManagerController mCameraManagerController = new ControllerCameraManagerController(this, null);
                private final Camera.AutoFocusCallback mAutoFocusCallback = new ControllerAutoFocusCallback(this, null);
                private final CameraManager mCameraManager = CameraManager.getInstance();
                private final PreviewManager mPreviewManager = PreviewManager.getInstance();

                public static class RecordControllerReadyStartedException extends Exception {
                }

/* 36 */        static /* synthetic */ BasicVineRecorder access$200(RecordController x0) {
/* 37 */            return x0.mParent;
                }

/* 36 */        static /* synthetic */ boolean access$302(RecordController x0, boolean x1) {
/* 37 */            x0.mIsFocusing = x1;
                    return x1;
                }

/* 36 */        static /* synthetic */ RecordProcessor access$400(RecordController x0) {
/* 37 */            return x0.mRecordProcessor;
                }

/* 36 */        static /* synthetic */ BaseRecorderPluginManager access$500(RecordController x0) {
/* 37 */            return x0.mPluginManager;
                }

/* 62 */        public RecordController(BasicVineRecorder parent, Activity activity, boolean hardwareEncoding, boolean fastEncoding, boolean useMp4, BaseRecorderPluginManager manager) {
/* 64 */            this.mParent = parent;
/* 64 */            this.mHardwareEncoding = hardwareEncoding;
/* 65 */            this.mFastEncoding = fastEncoding;
/* 71 */            this.mPluginManager = manager;
/* 72 */            this.mRecordProcessor = new BufferedRecordProcessor(useMp4, this.mState, this.mClock, hardwareEncoding, activity.getApplicationContext(), this.mPluginManager, this);
/* 75 */            this.mPreviewManager.setManagerCallback(this.mRecordProcessor.getPreviewManagerCallback());
/* 76 */            this.mSurfaceController = new SurfaceController(this.mRecordProcessor.getSurfaceListener(this));
                }

/* 79 */        public void swapSession() {
/* 80 */            this.mRecordProcessor.swapSession(this.mParent.getFile().getSession());
/* 81 */            this.mState.setCurrentSegment(null);
/* 82 */            this.mState.setLastVideoSegment(null);
/* 83 */            this.mState.setLastAudioRecordingSegment(null);
/* 84 */            this.mState.setEnded(false);
/* 85 */            if (this.mAudioSource != null) {
/* 86 */                this.mAudioSource.stop();
/* 87 */                this.mAudioSource = null;
                    }
                }

/* 92 */        public void updateRotation(Activity activity) {
/* 93 */            this.mRotation = CameraManager.getCameraRotation(activity);
                }

/* 96 */        public void switchFlash() {
/* 97 */            this.mFlash = !this.mFlash;
/* 98 */            this.mCameraManager.changeFlashState(this.mFlash);
                }

/* 101 */       public boolean isFlashOn() {
/* 102 */           return this.mFlash;
                }

/* 105 */       public void setAutoFocus(boolean isOn) {
/* 106 */           this.mAutoFocus = isOn;
                    try {
/* 108 */               if (this.mAutoFocus) {
/* 109 */                   if (this.mPreviewManager.isPreviewing()) {
/* 110 */                       this.mCameraManager.startContinuousAutoFocus(this.mCameraManager.getParameters());
                            }
                        } else if (this.mPreviewManager.isPreviewing()) {
/* 114 */                   this.mCameraManager.stopContinuousFocus(this.mCameraManager.getParameters());
/* 115 */                   if (this.mAutoFocus && !this.mIsFocusing && this.mCameraManager.autoFocus(this.mAutoFocusCallback)) {
/* 117 */                       this.mIsFocusing = true;
                            }
                        }
                    } catch (Exception e) {
/* 123 */               CrashUtil.logException(e, "It's ok if we failed to auto focus here.", new Object[0]);
                    }
                }

/* 127 */       public void autoFocus(int x, int y) {
/* 128 */           if (!this.mIsFocusing && this.mCameraManager.setFocusAreas(x, y) && this.mCameraManager.autoFocus(this.mAutoFocusCallback)) {
/* 130 */               this.mIsFocusing = true;
/* 131 */               this.mPluginManager.onAutoFocus(x, y);
                    }
                }

/* 136 */       public void onPause() {
/* 137 */           setRecording(null);
/* 138 */           releaseCameraAndPreview("onPause");
/* 139 */           PreviewManager.releaseCallback();
                }

/* 142 */       public boolean openDefaultCamera(boolean frontFacing, boolean reOpen) {
/* 143 */           long start = System.currentTimeMillis();
                    CameraSetting cameraSetting;
                    try {
                        cameraSetting = this.mCameraManager.openDefaultCamera(this.mParent.getConfig(), frontFacing, this.mRotation, reOpen);
                    } catch (VineNotSupportedException e) {
                        CrashUtil.logException(e, "Camera configuration is not supported", new Object[0]);
                        return false;
                    }
/* 145 */           SLog.m447d("Camera Open 1 took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
/* 146 */           if (cameraSetting != null) {
/* 147 */               SLog.m446d("Received camera info.");
/* 148 */               long start2 = System.currentTimeMillis();
/* 149 */               this.mPreviewManager.setPreviewSize(cameraSetting.originalW, cameraSetting.originalH);
/* 150 */               SLog.m447d("Camera Open 2 took {}ms.", Long.valueOf(System.currentTimeMillis() - start2));
/* 151 */               long start3 = System.currentTimeMillis();
/* 152 */               this.mIsFocusing = false;
/* 153 */               this.mParent.onCameraReady(this);
/* 154 */               SLog.m447d("Camera Open 3 took {}ms.", Long.valueOf(System.currentTimeMillis() - start3));
                        return true;
                    }
/* 157 */           CrashUtil.log("Failed to open camera: {}.", Boolean.valueOf(frontFacing));
                    return false;
                }

/* 162 */       public boolean isProcessing() {
/* 163 */           return this.mRecordProcessor.wasProcessing();
                }

/* 166 */       public void finishLastIfNeeded() {
/* 167 */           this.mRecordProcessor.finishLastIfNeeded();
                }

/* 170 */       public MediaUtil.GenerateThumbnailsRunnable stop(boolean discard, boolean stopPreview) {
/* 171 */           if (!this.mState.isStarted() && SLog.sLogsOn) {
/* 173 */               throw new IllegalStateException("You can't stop before you have start it.");
                    }
/* 176 */           MediaUtil.GenerateThumbnailsRunnable r = null;
/* 177 */           Thread releaseThread = null;
                    try {
/* 179 */               long stopStart = System.currentTimeMillis();
/* 180 */               CrashUtil.log("Stop audio runnable and recording.");
/* 181 */               this.mState.setCollectMoreAudio(false);
/* 182 */               if (this.mState.isStarted()) {
/* 183 */                   SLog.m446d("Finishing recording, calling stop and release on recorder");
/* 184 */                   if (stopPreview) {
/* 185 */                       if (this.mFastEncoding) {
/* 186 */                           Thread releaseThread2 = new Thread(new RunnableC04801());
                                    try {
/* 192 */                               releaseThread2.start();
                                        releaseThread = releaseThread2;
                                    } catch (Exception e) {
/* 219 */                               e = e;
                                        releaseThread = releaseThread2;
/* 220 */                               CrashUtil.logException(e, "Stop failed", new Object[0]);
                                    }
                                } else {
/* 194 */                           releaseCameraAndPreview("Stop");
                                }
                            } else {
/* 197 */                       PreviewManager.releaseCallback();
                            }
/* 199 */                   SLog.m446d("Waiting...........");
/* 200 */                   this.mState.setStarted(false);
/* 201 */                   if (discard) {
/* 202 */                       this.mRecordProcessor.stopProcessing();
                                try {
/* 204 */                           finishLastIfNeeded();
                                } catch (Exception e2) {
/* 206 */                           CrashUtil.logException(e2, "discard failed.", new Object[0]);
                                }
                            } else {
/* 209 */                       r = this.mRecordProcessor.stop();
/* 210 */                       long stopTime = System.currentTimeMillis() - stopStart;
/* 211 */                       SLog.m465i("Stop wait time: {}.", Long.valueOf(stopTime));
                                if (stopTime > sMaxKnownStopTime) {
/* 213 */                           sMaxKnownStopTime = stopTime;
                                }
                            }
                        }
/* 218 */               this.mState.setEnded(true);
                    } catch (Exception e3) {
/* 219 */               CrashUtil.logException(e3, "Stop failed", new Object[0]);
                    }
/* 222 */           this.mState.setStarted(false);
/* 223 */           if (stopPreview) {
/* 224 */               releaseCameraAndPreview("Force stop");
                    } else {
/* 226 */               PreviewManager.releaseCallback();
                    }
/* 229 */           this.mRecordProcessor.onStopped(stopPreview);
/* 230 */           if (releaseThread != null) {
                        try {
/* 232 */                   releaseThread.join();
                        } catch (InterruptedException e4) {
/* 234 */                   SLog.m455e("Camera still not released?!");
                        }
                    }
/* 237 */           SLog.m446d("Recorder is stopped.");
/* 238 */           return r;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.RecordController$1 */
/* 184 */       class RunnableC04801 implements Runnable {
/* 185 */           RunnableC04801() {
                    }

                    @Override // java.lang.Runnable
/* 188 */           public void run() {
/* 189 */               RecordController.this.releaseCameraAndPreview("Stop on thread.");
                    }
                }

/* 241 */       public void makePreview(RecordSegment segment, boolean getLastSegmentOnlyMode, boolean ignoreTrim) {
/* 242 */           this.mRecordProcessor.makePreview(this.mParent.getFile(), segment, getLastSegmentOnlyMode, ignoreTrim);
                }

/* 245 */       public void writeToFile(ArrayList<RecordSegment> editedSegments, byte[] videoData, AudioArray audioData) throws IOException {
/* 246 */           MediaUtil.GenerateThumbnailsRunnable r = writeToFile(editedSegments, videoData, audioData, true);
/* 247 */           if (r != null) {
/* 248 */               r.run();
                    }
                }

/* 253 */       private MediaUtil.GenerateThumbnailsRunnable writeToFile(ArrayList<RecordSegment> editedSegments, byte[] videoData, AudioArray audioData, boolean preview) {
                    try {
/* 254 */               SLog.m465i("Write to file: {}", Boolean.valueOf(preview));
/* 255 */               while (this.mAudioSource != null && this.mAudioSource.isInitialized()) {
/* 256 */                   Thread.sleep(20L);
                        }
                    } catch (InterruptedException e) {
/* 259 */               SLog.m446d("No...");
                    }
/* 261 */           return this.mRecordProcessor.writeToFile(this.mParent.getFile(), editedSegments, videoData, audioData, preview);
                }

/* 264 */       public boolean isAudioReady() {
                    return this.mAudioSource != null && this.mAudioSource.isInitialized();
                }

/* 268 */       public CameraSetting getCameraSetting() {
/* 269 */           return this.mCameraManager.getCameraSetting();
                }

/* 272 */       public void logPreviewSizeExceptionIfNeeded() {
/* 273 */           if (this.mInvalidPreviewSizeException != null) {
/* 274 */               CrashUtil.logException(this.mInvalidPreviewSizeException);
                    }
                }

/* 278 */       public void setAudioTrim(boolean enabled) {
/* 279 */           this.mRecordProcessor.setAudioTrim(enabled);
                }

/* 282 */       public void waitForPreviewToStart() {
/* 283 */           this.mPreviewManager.waitForPreviewToStart();
                }

/* 286 */       public boolean canSwitchFlash() {
/* 287 */           return this.mCameraManager.canSwitchFlash();
                }

/* 290 */       public void onExternalClipAdded(int microseconds) {
/* 291 */           this.mRecordProcessor.onExternalClipAdded(microseconds);
                }

/* 294 */       public void onRecordingStarted() {
/* 295 */           PreviewManager.setPreviewCallback(this.mCameraManagerController);
                }

/* 298 */       public boolean hasCameraReceivingFrames() {
/* 299 */           return this.mPreviewManager.hasGivenBufferToCurrentCallback();
                }

/* 302 */       public void onEndRelativeTime(RecordSegment segmentToEnd) {
/* 303 */           this.mRecordProcessor.onEndRelativeTime(segmentToEnd);
                }

/* 306 */       public void releaseCallbacks() {
/* 307 */           this.mSurfaceController.releaseCallbacks();
                }

/* 310 */       public boolean isSurfaceReady() {
/* 311 */           return this.mSurfaceController.isSurfaceReady();
                }

/* 314 */       public void setPreviewSurface(View cameraView) throws IOException {
/* 315 */           this.mSurfaceController.setPreviewSurface(cameraView);
                }

/* 318 */       public void onTimestampSwap(RecordSession session) {
/* 319 */           this.mRecordProcessor.onSessionTimestampChanged(session);
                }

                @Override // co.vine.android.recorder.ParentHolder
/* 323 */       public BasicVineRecorder getParent() {
/* 324 */           return this.mParent;
                }

/* 330 */       public synchronized boolean start(Activity activity, String output, RecordSession session) throws RecordControllerReadyStartedException {
                    boolean z;
                    try {
                        if (!this.mCameraManager.isCameraReady()) {
/* 332 */                   throw new RuntimeException("You have to choose a camera via open() first.");
                        }
/* 334 */               this.mSurfaceController.assertValidSurfaces();
/* 335 */               if (this.mState.isStarted()) {
/* 336 */                   throw new RecordControllerReadyStartedException();
                        }
/* 338 */               CameraSetting cs = this.mCameraManager.getCameraSetting();
/* 339 */               if (cs == null) {
/* 340 */                   throw new RuntimeException("Camera is ready but camera setting is null, something is terribly wrong.");
                        }
/* 343 */               SLog.m447d("START Recorder, with ResourceService ready? {}.", Boolean.valueOf(ResourceService.IS_READY));
                        try {
/* 345 */                   finishLastIfNeeded();
                        } catch (Exception e) {
/* 347 */                   SLog.m452d("Finish last if needed Failed.", (Throwable) e);
                        }
/* 350 */               long start = System.currentTimeMillis();
/* 351 */               this.mState.setEnded(false);
/* 352 */               this.mState.setStarted(true);
/* 353 */               if (this.mAudioSource != null && this.mAudioSource.isInitialized()) {
/* 354 */                   this.mAudioSource.stop();
                        }
/* 357 */               AudioReceiver receiver = this.mRecordProcessor.prepareAudioReceiver(session);
/* 358 */               this.mRecordProcessor.prepareImageReceiver(session);
/* 359 */               this.mAudioSource = new AudioRecordSource(output, ResourceService.getAudioDataBuffer(receiver.getType()));
/* 360 */               this.mState.setCollectMoreAudio(true);
/* 361 */               this.mAudioSource.start(receiver);
/* 362 */               this.mClock.resetStartTime();
/* 364 */               if (!this.mHardwareEncoding && !SwVineFrameRecorder.hasEverSuccessfullyLoaded) {
                            try {
/* 366 */                       SwVineFrameRecorder.tryLoad(activity);
                            } catch (SwVineFrameRecorder.Exception e2) {
/* 368 */                       CrashUtil.logException(e2, "Failed to load native libraries.", new Object[0]);
                            }
                        }
/* 372 */               SLog.m447d("start 1 took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
/* 373 */               boolean success = this.mRecordProcessor.start(this.mParent, output, activity, cs, new C04812());
/* 374 */               if (success) {
/* 375 */                   // Install the frame callback before opening the camera preview.
/* 376 */                   onRecordingStarted();
/* 377 */                   startPreview();
/* 378 */               }
/* 380 */               if (!success) {
/* 381 */                   this.mAudioSource.stop();
/* 382 */                   z = false;
                        } else {
/* 384 */                   z = true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return z;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.RecordController$2 */
/* 372 */       class C04812 implements RecordProcessor.ProcessingErrorHandler {
/* 373 */           C04812() {
                    }

                    @Override // co.vine.android.recorder.RecordProcessor.ProcessingErrorHandler
/* 376 */           public void onNotEnoughSpaceLeft(Exception e) {
/* 377 */               RecordController.access$200(RecordController.this).onDeviceHasNotEnoughSpaceLeft(e);
                    }
                }

/* 387 */       public void startPreview(CameraSetting cs) {
/* 388 */           RecordConfigUtils.RecordConfig config = this.mParent.getConfig();
                    try {
/* 390 */               this.mPreviewManager.startPreview(this.mCameraManagerController, config, this.mSurfaceController, this.mFlash, this.mAutoFocus, cs.fps, this.mRotation, this.mClock.getAudioTimestampUs() / 1000);
                    } catch (PreviewManager.InvalidPreviewSizeException e) {
/* 394 */               cs.originalH = 240;
/* 395 */               cs.originalW = 320;
/* 396 */               CrashUtil.log("CAUGHT INVALID PREVIEW SIZE, trying to use a smaller size again: " + cs.originalW + " * " + cs.originalH + " ff: " + cs.frontFacing);
/* 397 */               if (this.mParent != null && this.mParent.mActivity != null) {
/* 398 */                   Point displaySize = SystemUtil.getDisplaySize(this.mParent.mActivity);
/* 399 */                   CrashUtil.log("Screen size: {} * {}", Integer.valueOf(displaySize.x), Integer.valueOf(displaySize.y));
                        }
/* 401 */               this.mCameraManager.printSupportedPreviewSizes();
/* 402 */               this.mInvalidPreviewSizeException = e;
/* 403 */               this.mPreviewManager.setPreviewSize(cs.originalW, cs.originalH);
                        try {
/* 405 */                   this.mPreviewManager.startPreview(this.mCameraManagerController, config, this.mSurfaceController, this.mFlash, this.mAutoFocus, cs.fps, this.mRotation, this.mClock.getAudioTimestampUs() / 1000);
                        } catch (Exception e2) {
/* 409 */                   throw new RuntimeException(e2);
                        }
                    }
                }

/* 414 */       protected void releaseCameraAndPreview(String tag) {
/* 415 */           CrashUtil.log("Release camera and preview: " + tag);
/* 416 */           this.mPreviewManager.releaseCameraAndPreview("Controller " + tag);
                }

/* 419 */       public boolean isCameraReady() {
/* 420 */           return this.mCameraManager.isCameraReady();
                }

/* 422 */       public void startPreview() {
                    CameraSetting cs;
/* 423 */           if (!this.mSurfaceController.isSurfaceReady()) {
/* 424 */               CrashUtil.log("Start Preview requested, but surface is not ready yet, let's wait.");
/* 439 */               return;
                    }
/* 427 */           if (this.mCameraManager.isCameraReady() && !this.mPreviewManager.isPreviewing() && (cs = this.mCameraManager.getCameraSetting()) != null) {
/* 430 */               cs.fps = this.mCameraManager.setFrameRate(cs.originalFrameRate);
/* 431 */               if (cs.fps > 0) {
/* 432 */                   this.mClock.setFrameTimeUs(AudioUtils.MICROS_PER_SECOND / ((long) cs.fps));
/* 433 */                   SLog.m448d("Determined frame rate: {}, frame time: {}.", Integer.valueOf(cs.fps), Long.valueOf(this.mClock.getFrameTimeUs()));
/* 434 */                   this.mFlash = false;
/* 435 */                   startPreview(cs);
                        }
                    }
                }

/* 441 */       public boolean isRecording() {
                    return this.mState.getCurrentSegment() != null;
                }

/* 445 */       public boolean isRecordingStarted() {
/* 446 */           return this.mState.isStarted();
                }

/* 449 */       public void setRecording(RecordSegment segment) {
/* 450 */           if (isRecordingStarted()) {
/* 451 */               if (segment != null) {
/* 452 */                   this.mRecordProcessor.onNewSegmentStart();
/* 453 */                   this.mClock.startSegment();
/* 454 */                   segment.setCameraSetting(this.mCameraManager.getCameraSetting());
                        }
/* 456 */               this.mState.setCurrentSegment(segment);
                    }
                }

/* 460 */       public void setFinishProcessTask(BaseFinishProcessTask finishProcessTask) {
/* 461 */           this.mRecordProcessor.setFinishProcessTask(finishProcessTask);
                }

/* 464 */       public void modifyWhiteBalance(boolean up) {
/* 465 */           this.mCameraManager.modifyWhiteBalance(up);
                }

/* 468 */       public void modifyExposure(boolean up) {
/* 469 */           this.mCameraManager.modifyExposure(up);
                }

/* 472 */       public void modifyZoom(boolean zoomIn) {
/* 473 */           this.mCameraManager.modifyZoom(zoomIn);
                }

/* 476 */       public void stopZoom() {
/* 477 */           this.mCameraManager.stopSmoothZoom();
                }

/* 480 */       public void modifySceneMode(boolean up) {
/* 481 */           this.mCameraManager.modifySceneMode(up);
                }

/* 484 */       public void modifyColorEffects(boolean up) {
/* 485 */           this.mCameraManager.modifyColorEffects(up);
                }

/* 488 */       public void modifyAntiBanding(boolean up) {
/* 489 */           this.mCameraManager.modifyAntiBanding(up);
                }

/* 492 */       public void switchImageStabilization() {
/* 493 */           this.mCameraManager.switchImageStabilization();
                }

/* 496 */       public void setRecordingAudio(boolean recordingAudio) {
/* 497 */           this.mState.setRecordingAudio(recordingAudio);
                }

/* 500 */       public void releaseCameraResources() {
/* 501 */           this.mPreviewManager.releaseResources();
                }

/* 504 */       public void releaseParent() {
/* 505 */           this.mParent = null;
                }

/* 508 */       public void releaseResources() {
/* 509 */           SLog.m446d("releaseResources");
/* 510 */           this.mState.setLastAudioRecordingSegment(null);
/* 511 */           this.mState.setLastVideoSegment(null);
/* 512 */           this.mRecordProcessor.releaseResources();
                }

/* 515 */       public boolean isPreviewing() {
/* 516 */           return this.mPreviewManager.isPreviewing();
                }

/* 519 */       public boolean canChangeFocus() {
/* 520 */           return this.mCameraManager.canChangeFocus();
                }

/* 523 */       public void setAudioTimestampUs(int timestamp) {
/* 524 */           this.mClock.setAudioTimestampUs(timestamp);
/* 525 */           if (timestamp == 0) {
/* 526 */               this.mClock.resetStartTime();
                    }
                }

/* 530 */       public void setVideoTimeStampUs(long timestamp) {
/* 531 */           this.mRecordProcessor.setVideoTimeStampUs(timestamp);
                }

/* 534 */       public BaseFinishProcessTask getFinishProcessTask() {
/* 535 */           return this.mRecordProcessor.getFinishProcessTask();
                }

/* 538 */       public long getTimestampMs() {
/* 539 */           int timestamp = this.mClock.getAudioTimestampUs();
/* 540 */           SLog.m465i("Recorder Timestamp: {}.", Integer.valueOf(timestamp));
/* 541 */           return ((long) timestamp) / 1000;
                }

/* 544 */       public boolean wasJustGrabbingDataForSegment() {
/* 545 */           return this.mClock.wasAudioJustRecorded();
                }

/* 546 */       private class ControllerAutoFocusCallback implements Camera.AutoFocusCallback {
/* 547 */           private ControllerAutoFocusCallback() {
                    }

/* 547 */           /* synthetic */ ControllerAutoFocusCallback(RecordController x0, RunnableC04801 x1) {
/* 548 */               this();
                    }

                    @Override // android.hardware.Camera.AutoFocusCallback
/* 550 */           public void onAutoFocus(boolean success, Camera camera) {
/* 551 */               RecordController.access$302(RecordController.this, false);
/* 552 */               RecordController.access$200(RecordController.this).onAutoFocusComplete(success);
                    }
                }

/* 554 */       private class ControllerCameraManagerController implements CameraManager.CameraManagerController {
/* 555 */           private ControllerCameraManagerController() {
                    }

/* 555 */           /* synthetic */ ControllerCameraManagerController(RecordController x0, RunnableC04801 x1) {
/* 556 */               this();
                    }

                    @Override // co.vine.android.recorder.camera.CameraManager.CameraManagerController
/* 559 */           public Camera.PreviewCallback getCameraPreviewCallback() {
/* 560 */               return RecordController.access$400(RecordController.this).getCameraPreviewCallback();
                    }

                    @Override // co.vine.android.recorder.camera.CameraManager.CameraManagerController
/* 563 */           public void onZoomUpdated(int zoom, boolean stopped) {
/* 564 */               if (RecordController.access$500(RecordController.this) != null) {
/* 565 */                   RecordController.access$500(RecordController.this).onZoomUpdated(zoom, stopped);
                        }
                    }

                    @Override // co.vine.android.recorder.camera.CameraManager.CameraManagerController
/* 570 */           public void onPreviewError(RuntimeException callbackException) {
/* 571 */               if (RecordController.access$200(RecordController.this) != null) {
/* 572 */                   RecordController.access$200(RecordController.this).onPreviewError(callbackException);
/* 576 */                   return;
                        }
/* 574 */               throw callbackException;
                    }
                }
            }
