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
            
            
            @TargetApi(14)
    public class RecordController implements ParentHolder {
                public static long sMaxKnownStopTime;
                private AudioSource mAudioSource;
                
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

        static  BasicVineRecorder access$200(RecordController x0) {
            return x0.mParent;
                }

        static  boolean access$302(RecordController x0, boolean x1) {
            x0.mIsFocusing = x1;
                    return x1;
                }

        static  RecordProcessor access$400(RecordController x0) {
            return x0.mRecordProcessor;
                }

        static  BaseRecorderPluginManager access$500(RecordController x0) {
            return x0.mPluginManager;
                }

        public RecordController(BasicVineRecorder parent, Activity activity, boolean hardwareEncoding, boolean fastEncoding, boolean useMp4, BaseRecorderPluginManager manager) {
            this.mParent = parent;
            this.mHardwareEncoding = hardwareEncoding;
            this.mFastEncoding = fastEncoding;
            this.mPluginManager = manager;
            this.mRecordProcessor = new BufferedRecordProcessor(useMp4, this.mState, this.mClock, hardwareEncoding, activity.getApplicationContext(), this.mPluginManager, this);
            this.mPreviewManager.setManagerCallback(this.mRecordProcessor.getPreviewManagerCallback());
            this.mSurfaceController = new SurfaceController(this.mRecordProcessor.getSurfaceListener(this));
                }

        public void swapSession() {
            this.mRecordProcessor.swapSession(this.mParent.getFile().getSession());
            this.mState.setCurrentSegment(null);
            this.mState.setLastVideoSegment(null);
            this.mState.setLastAudioRecordingSegment(null);
            this.mState.setEnded(false);
            if (this.mAudioSource != null) {
                this.mAudioSource.stop();
                this.mAudioSource = null;
                    }
                }

        public void updateRotation(Activity activity) {
            this.mRotation = CameraManager.getCameraRotation(activity);
                }

        public void switchFlash() {
            this.mFlash = !this.mFlash;
            this.mCameraManager.changeFlashState(this.mFlash);
                }

       public boolean isFlashOn() {
           return this.mFlash;
                }

       public void setAutoFocus(boolean isOn) {
           this.mAutoFocus = isOn;
                    try {
               if (this.mAutoFocus) {
                   if (this.mPreviewManager.isPreviewing()) {
                       this.mCameraManager.startContinuousAutoFocus(this.mCameraManager.getParameters());
                            }
                        } else if (this.mPreviewManager.isPreviewing()) {
                   this.mCameraManager.stopContinuousFocus(this.mCameraManager.getParameters());
                   if (this.mAutoFocus && !this.mIsFocusing && this.mCameraManager.autoFocus(this.mAutoFocusCallback)) {
                       this.mIsFocusing = true;
                            }
                        }
                    } catch (Exception e) {
               CrashUtil.logException(e, "It's ok if we failed to auto focus here.", new Object[0]);
                    }
                }

       public void autoFocus(int x, int y) {
           if (!this.mIsFocusing && this.mCameraManager.setFocusAreas(x, y) && this.mCameraManager.autoFocus(this.mAutoFocusCallback)) {
               this.mIsFocusing = true;
               this.mPluginManager.onAutoFocus(x, y);
                    }
                }

       public void onPause() {
           setRecording(null);
           releaseCameraAndPreview("onPause");
           PreviewManager.releaseCallback();
                }

       public boolean openDefaultCamera(boolean frontFacing, boolean reOpen) {
           long start = System.currentTimeMillis();
                    CameraSetting cameraSetting;
                    try {
                        cameraSetting = this.mCameraManager.openDefaultCamera(this.mParent.getConfig(), frontFacing, this.mRotation, reOpen);
                    } catch (VineNotSupportedException e) {
                        CrashUtil.logException(e, "Camera configuration is not supported", new Object[0]);
                        return false;
                    }
           SLog.m447d("Camera Open 1 took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
           if (cameraSetting != null) {
               SLog.m446d("Received camera info.");
               long start2 = System.currentTimeMillis();
               this.mPreviewManager.setPreviewSize(cameraSetting.originalW, cameraSetting.originalH);
               SLog.m447d("Camera Open 2 took {}ms.", Long.valueOf(System.currentTimeMillis() - start2));
               long start3 = System.currentTimeMillis();
               this.mIsFocusing = false;
               this.mParent.onCameraReady(this);
               SLog.m447d("Camera Open 3 took {}ms.", Long.valueOf(System.currentTimeMillis() - start3));
                        return true;
                    }
           CrashUtil.log("Failed to open camera: {}.", Boolean.valueOf(frontFacing));
                    return false;
                }

       public boolean isProcessing() {
           return this.mRecordProcessor.wasProcessing();
                }

       public void finishLastIfNeeded() {
           this.mRecordProcessor.finishLastIfNeeded();
                }

       public MediaUtil.GenerateThumbnailsRunnable stop(boolean discard, boolean stopPreview) {
           if (!this.mState.isStarted() && SLog.sLogsOn) {
               throw new IllegalStateException("You can't stop before you have start it.");
                    }
           MediaUtil.GenerateThumbnailsRunnable r = null;
           Thread releaseThread = null;
                    try {
               long stopStart = System.currentTimeMillis();
               CrashUtil.log("Stop audio runnable and recording.");
               this.mState.setCollectMoreAudio(false);
               if (this.mState.isStarted()) {
                   SLog.m446d("Finishing recording, calling stop and release on recorder");
                   if (stopPreview) {
                       if (this.mFastEncoding) {
                           Thread releaseThread2 = new Thread(new RunnableC04801());
                                    try {
                               releaseThread2.start();
                                        releaseThread = releaseThread2;
                                    } catch (Exception e) {
                               e = e;
                                        releaseThread = releaseThread2;
                               CrashUtil.logException(e, "Stop failed", new Object[0]);
                                    }
                                } else {
                           releaseCameraAndPreview("Stop");
                                }
                            } else {
                       PreviewManager.releaseCallback();
                            }
                   SLog.m446d("Waiting...........");
                   this.mState.setStarted(false);
                   if (discard) {
                       this.mRecordProcessor.stopProcessing();
                                try {
                           finishLastIfNeeded();
                                } catch (Exception e2) {
                           CrashUtil.logException(e2, "discard failed.", new Object[0]);
                                }
                            } else {
                       r = this.mRecordProcessor.stop();
                       long stopTime = System.currentTimeMillis() - stopStart;
                       SLog.m465i("Stop wait time: {}.", Long.valueOf(stopTime));
                                if (stopTime > sMaxKnownStopTime) {
                           sMaxKnownStopTime = stopTime;
                                }
                            }
                        }
               this.mState.setEnded(true);
                    } catch (Exception e3) {
               CrashUtil.logException(e3, "Stop failed", new Object[0]);
                    }
           this.mState.setStarted(false);
           if (stopPreview) {
               releaseCameraAndPreview("Force stop");
                    } else {
               PreviewManager.releaseCallback();
                    }
           this.mRecordProcessor.onStopped(stopPreview);
           if (releaseThread != null) {
                        try {
                   releaseThread.join();
                        } catch (InterruptedException e4) {
                   SLog.m455e("Camera still not released?!");
                        }
                    }
           SLog.m446d("Recorder is stopped.");
           return r;
                }

                
       class RunnableC04801 implements Runnable {
           RunnableC04801() {
                    }

                    @Override 
           public void run() {
               RecordController.this.releaseCameraAndPreview("Stop on thread.");
                    }
                }

       public void makePreview(RecordSegment segment, boolean getLastSegmentOnlyMode, boolean ignoreTrim) {
           this.mRecordProcessor.makePreview(this.mParent.getFile(), segment, getLastSegmentOnlyMode, ignoreTrim);
                }

       public void writeToFile(ArrayList<RecordSegment> editedSegments, byte[] videoData, AudioArray audioData) throws IOException {
           MediaUtil.GenerateThumbnailsRunnable r = writeToFile(editedSegments, videoData, audioData, true);
           if (r != null) {
               r.run();
                    }
                }

       private MediaUtil.GenerateThumbnailsRunnable writeToFile(ArrayList<RecordSegment> editedSegments, byte[] videoData, AudioArray audioData, boolean preview) {
                    try {
               SLog.m465i("Write to file: {}", Boolean.valueOf(preview));
               while (this.mAudioSource != null && this.mAudioSource.isInitialized()) {
                   Thread.sleep(20L);
                        }
                    } catch (InterruptedException e) {
               SLog.m446d("No...");
                    }
           return this.mRecordProcessor.writeToFile(this.mParent.getFile(), editedSegments, videoData, audioData, preview);
                }

       public boolean isAudioReady() {
                    return this.mAudioSource != null && this.mAudioSource.isInitialized();
                }

       public CameraSetting getCameraSetting() {
           return this.mCameraManager.getCameraSetting();
                }

       public void logPreviewSizeExceptionIfNeeded() {
           if (this.mInvalidPreviewSizeException != null) {
               CrashUtil.logException(this.mInvalidPreviewSizeException);
                    }
                }

       public void setAudioTrim(boolean enabled) {
           this.mRecordProcessor.setAudioTrim(enabled);
                }

       public void waitForPreviewToStart() {
           this.mPreviewManager.waitForPreviewToStart();
                }

       public boolean canSwitchFlash() {
           return this.mCameraManager.canSwitchFlash();
                }

       public void onExternalClipAdded(int microseconds) {
           this.mRecordProcessor.onExternalClipAdded(microseconds);
                }

       public void onRecordingStarted() {
           PreviewManager.setPreviewCallback(this.mCameraManagerController);
                }

       public boolean hasCameraReceivingFrames() {
           return this.mPreviewManager.hasGivenBufferToCurrentCallback();
                }

       public void onEndRelativeTime(RecordSegment segmentToEnd) {
           this.mRecordProcessor.onEndRelativeTime(segmentToEnd);
                }

       public void releaseCallbacks() {
           this.mSurfaceController.releaseCallbacks();
                }

       public boolean isSurfaceReady() {
           return this.mSurfaceController.isSurfaceReady();
                }

       public void setPreviewSurface(View cameraView) throws IOException {
           this.mSurfaceController.setPreviewSurface(cameraView);
                }

       public void onTimestampSwap(RecordSession session) {
           this.mRecordProcessor.onSessionTimestampChanged(session);
                }

                @Override 
       public BasicVineRecorder getParent() {
           return this.mParent;
                }

       public synchronized boolean start(Activity activity, String output, RecordSession session) throws RecordControllerReadyStartedException {
                    boolean z;
                    try {
                        if (!this.mCameraManager.isCameraReady()) {
                   throw new RuntimeException("You have to choose a camera via open() first.");
                        }
               this.mSurfaceController.assertValidSurfaces();
               if (this.mState.isStarted()) {
                   throw new RecordControllerReadyStartedException();
                        }
               CameraSetting cs = this.mCameraManager.getCameraSetting();
               if (cs == null) {
                   throw new RuntimeException("Camera is ready but camera setting is null, something is terribly wrong.");
                        }
               SLog.m447d("START Recorder, with ResourceService ready? {}.", Boolean.valueOf(ResourceService.IS_READY));
                        try {
                   finishLastIfNeeded();
                        } catch (Exception e) {
                   SLog.m452d("Finish last if needed Failed.", (Throwable) e);
                        }
               long start = System.currentTimeMillis();
               this.mState.setEnded(false);
               this.mState.setStarted(true);
               if (this.mAudioSource != null && this.mAudioSource.isInitialized()) {
                   this.mAudioSource.stop();
                        }
               AudioReceiver receiver = this.mRecordProcessor.prepareAudioReceiver(session);
               this.mRecordProcessor.prepareImageReceiver(session);
               this.mAudioSource = new AudioRecordSource(output, ResourceService.getAudioDataBuffer(receiver.getType()));
               this.mState.setCollectMoreAudio(true);
               this.mAudioSource.start(receiver);
               this.mClock.resetStartTime();
               if (!this.mHardwareEncoding && !SwVineFrameRecorder.hasEverSuccessfullyLoaded) {
                            try {
                       SwVineFrameRecorder.tryLoad(activity);
                            } catch (SwVineFrameRecorder.Exception e2) {
                       CrashUtil.logException(e2, "Failed to load native libraries.", new Object[0]);
                            }
                        }
               SLog.m447d("start 1 took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
               boolean success = this.mRecordProcessor.start(this.mParent, output, activity, cs, new C04812());
               if (success) {
                   
                   onRecordingStarted();
                   startPreview();
               }
               if (!success) {
                   this.mAudioSource.stop();
                   z = false;
                        } else {
                   z = true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return z;
                }

                
       class C04812 implements RecordProcessor.ProcessingErrorHandler {
           C04812() {
                    }

                    @Override 
           public void onNotEnoughSpaceLeft(Exception e) {
               RecordController.access$200(RecordController.this).onDeviceHasNotEnoughSpaceLeft(e);
                    }
                }

       public void startPreview(CameraSetting cs) {
           RecordConfigUtils.RecordConfig config = this.mParent.getConfig();
                    try {
               this.mPreviewManager.startPreview(this.mCameraManagerController, config, this.mSurfaceController, this.mFlash, this.mAutoFocus, cs.fps, this.mRotation, this.mClock.getAudioTimestampUs() / 1000);
                    } catch (PreviewManager.InvalidPreviewSizeException e) {
               cs.originalH = 240;
               cs.originalW = 320;
               CrashUtil.log("CAUGHT INVALID PREVIEW SIZE, trying to use a smaller size again: " + cs.originalW + " * " + cs.originalH + " ff: " + cs.frontFacing);
               if (this.mParent != null && this.mParent.mActivity != null) {
                   Point displaySize = SystemUtil.getDisplaySize(this.mParent.mActivity);
                   CrashUtil.log("Screen size: {} * {}", Integer.valueOf(displaySize.x), Integer.valueOf(displaySize.y));
                        }
               this.mCameraManager.printSupportedPreviewSizes();
               this.mInvalidPreviewSizeException = e;
               this.mPreviewManager.setPreviewSize(cs.originalW, cs.originalH);
                        try {
                   this.mPreviewManager.startPreview(this.mCameraManagerController, config, this.mSurfaceController, this.mFlash, this.mAutoFocus, cs.fps, this.mRotation, this.mClock.getAudioTimestampUs() / 1000);
                        } catch (Exception e2) {
                   throw new RuntimeException(e2);
                        }
                    }
                }

       protected void releaseCameraAndPreview(String tag) {
           CrashUtil.log("Release camera and preview: " + tag);
           this.mPreviewManager.releaseCameraAndPreview("Controller " + tag);
                }

       public boolean isCameraReady() {
           return this.mCameraManager.isCameraReady();
                }

       public void startPreview() {
                    CameraSetting cs;
           if (!this.mSurfaceController.isSurfaceReady()) {
               CrashUtil.log("Start Preview requested, but surface is not ready yet, let's wait.");
               return;
                    }
           if (this.mCameraManager.isCameraReady() && !this.mPreviewManager.isPreviewing() && (cs = this.mCameraManager.getCameraSetting()) != null) {
               cs.fps = this.mCameraManager.setFrameRate(cs.originalFrameRate);
               if (cs.fps > 0) {
                   this.mClock.setFrameTimeUs(AudioUtils.MICROS_PER_SECOND / ((long) cs.fps));
                   SLog.m448d("Determined frame rate: {}, frame time: {}.", Integer.valueOf(cs.fps), Long.valueOf(this.mClock.getFrameTimeUs()));
                   this.mFlash = false;
                   startPreview(cs);
                        }
                    }
                }

       public boolean isRecording() {
                    return this.mState.getCurrentSegment() != null;
                }

       public boolean isRecordingStarted() {
           return this.mState.isStarted();
                }

       public void setRecording(RecordSegment segment) {
           if (isRecordingStarted()) {
               if (segment != null) {
                   this.mRecordProcessor.onNewSegmentStart();
                   this.mClock.startSegment();
                   segment.setCameraSetting(this.mCameraManager.getCameraSetting());
                        }
               this.mState.setCurrentSegment(segment);
                    }
                }

       public void setFinishProcessTask(BaseFinishProcessTask finishProcessTask) {
           this.mRecordProcessor.setFinishProcessTask(finishProcessTask);
                }

       public void modifyWhiteBalance(boolean up) {
           this.mCameraManager.modifyWhiteBalance(up);
                }

       public void modifyExposure(boolean up) {
           this.mCameraManager.modifyExposure(up);
                }

       public void modifyZoom(boolean zoomIn) {
           this.mCameraManager.modifyZoom(zoomIn);
                }

       public void stopZoom() {
           this.mCameraManager.stopSmoothZoom();
                }

       public void modifySceneMode(boolean up) {
           this.mCameraManager.modifySceneMode(up);
                }

       public void modifyColorEffects(boolean up) {
           this.mCameraManager.modifyColorEffects(up);
                }

       public void modifyAntiBanding(boolean up) {
           this.mCameraManager.modifyAntiBanding(up);
                }

       public void switchImageStabilization() {
           this.mCameraManager.switchImageStabilization();
                }

       public void setRecordingAudio(boolean recordingAudio) {
           this.mState.setRecordingAudio(recordingAudio);
                }

       public void releaseCameraResources() {
           this.mPreviewManager.releaseResources();
                }

       public void releaseParent() {
           this.mParent = null;
                }

       public void releaseResources() {
           SLog.m446d("releaseResources");
           this.mState.setLastAudioRecordingSegment(null);
           this.mState.setLastVideoSegment(null);
           this.mRecordProcessor.releaseResources();
                }

       public boolean isPreviewing() {
           return this.mPreviewManager.isPreviewing();
                }

       public boolean canChangeFocus() {
           return this.mCameraManager.canChangeFocus();
                }

       public void setAudioTimestampUs(int timestamp) {
           this.mClock.setAudioTimestampUs(timestamp);
           if (timestamp == 0) {
               this.mClock.resetStartTime();
                    }
                }

       public void setVideoTimeStampUs(long timestamp) {
           this.mRecordProcessor.setVideoTimeStampUs(timestamp);
                }

       public BaseFinishProcessTask getFinishProcessTask() {
           return this.mRecordProcessor.getFinishProcessTask();
                }

       public long getTimestampMs() {
           int timestamp = this.mClock.getAudioTimestampUs();
           SLog.m465i("Recorder Timestamp: {}.", Integer.valueOf(timestamp));
           return ((long) timestamp) / 1000;
                }

       public boolean wasJustGrabbingDataForSegment() {
           return this.mClock.wasAudioJustRecorded();
                }

       private class ControllerAutoFocusCallback implements Camera.AutoFocusCallback {
           private ControllerAutoFocusCallback() {
                    }

            ControllerAutoFocusCallback(RecordController x0, RunnableC04801 x1) {
               this();
                    }

                    @Override 
           public void onAutoFocus(boolean success, Camera camera) {
               RecordController.access$302(RecordController.this, false);
               RecordController.access$200(RecordController.this).onAutoFocusComplete(success);
                    }
                }

       private class ControllerCameraManagerController implements CameraManager.CameraManagerController {
           private ControllerCameraManagerController() {
                    }

            ControllerCameraManagerController(RecordController x0, RunnableC04801 x1) {
               this();
                    }

                    @Override 
           public Camera.PreviewCallback getCameraPreviewCallback() {
               return RecordController.access$400(RecordController.this).getCameraPreviewCallback();
                    }

                    @Override 
           public void onZoomUpdated(int zoom, boolean stopped) {
               if (RecordController.access$500(RecordController.this) != null) {
                   RecordController.access$500(RecordController.this).onZoomUpdated(zoom, stopped);
                        }
                    }

                    @Override 
           public void onPreviewError(RuntimeException callbackException) {
               if (RecordController.access$200(RecordController.this) != null) {
                   RecordController.access$200(RecordController.this).onPreviewError(callbackException);
                   return;
                        }
               throw callbackException;
                    }
                }
            }
