            package co.vine.android.recorder.camera;

            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.graphics.Matrix;
            import android.graphics.Rect;
            import android.graphics.RectF;
            import android.hardware.Camera;
            import android.media.CamcorderProfile;
            import android.os.Build;
            import android.support.annotation.NonNull;
            import android.util.SparseArray;
            import co.vine.android.VineNotSupportedException;
            import co.vine.android.recorder.MasterPreviewCallback;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.MathUtil;
            import com.edisonwang.android.slog.SLog;
            import java.util.ArrayList;
            import java.util.HashSet;
            import java.util.List;
            import java.util.Set;
            
            
            @TargetApi(14)
    public class CameraManager implements Camera.OnZoomChangeListener {
                public static final int[] CAMERA_LOCK = new int[0];
                private static CameraManager INSTANCE;
                private static final Integer[] sBackFacing;
                private static final SparseArray<Camera.CameraInfo> sCameraInfoTable;
                private static final SparseArray<CameraSetting> sCameraSettingTable;
                private static final Integer[] sFrontFacing;
                private boolean mAutoStopZoomCalled;
                private Camera mCamera;
                private CameraSetting mCameraSetting;
                private int mCurrentZoomTarget;
                private ArrayList<Camera.Area> mFocusArea;
                private boolean mIsSmoothZoomSupported;
                private boolean mIsSmoothZooming;
                private boolean mIsSmoothZoomingStopCalled;
                private int mMaxZoom;
                private int mRequestedZoom;
                private boolean mIsCurrentFront = false;
                private int mCurrentCamera = -1;
                private final Matrix mMatrix = new Matrix();

                public interface CameraManagerController {
                    Camera.PreviewCallback getCameraPreviewCallback();

                    void onPreviewError(RuntimeException runtimeException);

                    void onZoomUpdated(int i, boolean z);
                }

                static {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            int numberOfCameras = Camera.getNumberOfCameras();
            ArrayList<Integer> frontFacing = new ArrayList<>();
            ArrayList<Integer> backFacing = new ArrayList<>();
                    for (int i = 0; i < numberOfCameras; i++) {
                        try {
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraInfo.facing == 1) {
                        frontFacing.add(Integer.valueOf(i));
                            } else if (cameraInfo.facing == 0) {
                        backFacing.add(Integer.valueOf(i));
                            }
                        } catch (Exception e) {
                    CrashUtil.logException(e, "Cannot get camera info for camera #{}.", Integer.valueOf(i));
                        }
                    }
            sFrontFacing = (Integer[]) frontFacing.toArray(new Integer[frontFacing.size()]);
            sBackFacing = (Integer[]) backFacing.toArray(new Integer[backFacing.size()]);
            sCameraInfoTable = new SparseArray<>();
            sCameraSettingTable = new SparseArray<>();
                }

        public boolean addRecordingHintIfNeeded(Camera.Parameters parameters, RecordConfigUtils.RecordConfig config) {
            boolean setRecordingHint = (this.mIsCurrentFront && config.frontFacingRecordingHint) || (!this.mIsCurrentFront && config.backFacingRecordingHint);
            if (setRecordingHint) {
                parameters.setRecordingHint(true);
                    }
            return setRecordingHint;
                }

       public static boolean hasCamera() {
                    return hasBackFacingCamera() || hasFrontFacingCamera();
                }

       public static boolean hasFrontFacingCamera() {
                    return sFrontFacing.length > 0;
                }

       public static boolean hasBackFacingCamera() {
                    return sBackFacing.length > 0;
                }

       private CameraManager() {
                }

                @NonNull
       public static synchronized CameraManager getInstance() {
                    try {
                        if (INSTANCE == null) {
                   INSTANCE = new CameraManager();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
           return INSTANCE;
                }

       public boolean isCameraReady() {
                    return (this.mCamera == null || this.mCameraSetting == null) ? false : true;
                }

       public CameraSetting getCameraSetting() {
           return this.mCameraSetting;
                }

       public boolean canChangeFocus() {
                    try {
                        return this.mCamera != null && this.mCamera.getParameters().getMaxNumFocusAreas() > 0;
                    } catch (Exception e) {
                        return false;
                    }
                }

       public boolean setFocusAreas(int x, int y) {
                    try {
               initializeFocusAreas(x, y);
               Camera.Parameters params = this.mCamera.getParameters();
               params.setFocusAreas(this.mFocusArea);
               this.mCamera.setParameters(params);
                        return true;
                    } catch (Exception e) {
               CrashUtil.logException(e, "Failed to set focus areas {}, {}.", this.mMatrix, this.mFocusArea.get(0).rect);
                        return false;
                    }
                }

       private void initializeFocusAreas(int x, int y) {
           if (this.mFocusArea == null) {
               this.mFocusArea = new ArrayList<>();
               this.mFocusArea.add(new Camera.Area(new Rect(), 1));
                    }
           calculateTapArea(100, 100, 1.0f, x, y, this.mCameraSetting.originalW, this.mCameraSetting.originalH, this.mFocusArea.get(0).rect);
                }

       private void calculateTapArea(int focusWidth, int focusHeight, float areaMultiple, int x, int y, int previewWidth, int previewHeight, Rect rect) {
           int areaWidth = (int) (focusWidth * areaMultiple);
           int areaHeight = (int) (focusHeight * areaMultiple);
           int left = MathUtil.clamp(x - (areaWidth / 2), 0, previewWidth - areaWidth);
           int top = MathUtil.clamp(y - (areaHeight / 2), 0, previewHeight - areaHeight);
           RectF rectF = new RectF(left, top, left + areaWidth, top + areaHeight);
           this.mMatrix.mapRect(rectF);
           MathUtil.rectFToRect(rectF, rect);
                }

       public Camera.Parameters getParameters() {
           if (this.mCamera != null) {
                        return this.mCamera.getParameters();
                    }
                    return null;
                }

       public boolean stopContinuousFocus(Camera.Parameters params) {
           String supportedMode = "";
           List<String> focusModes = params.getSupportedFocusModes();
           SLog.m447d("Force auto focus: {}", Boolean.valueOf(RecordConfigUtils.RecordConfig.FORCE_AUTO_FOCUS));
           if (focusModes != null) {
               if (focusModes.contains("auto") || RecordConfigUtils.RecordConfig.FORCE_AUTO_FOCUS) {
                   supportedMode = "auto";
                   SLog.m446d("Focus Mode: FOCUS_MODE_AUTO");
                        } else if (focusModes.contains("continuous-picture")) {
                   supportedMode = "continuous-picture";
                   SLog.m446d("Focus Mode: FOCUS_MODE_CONTINUOUS_PICTURE");
                        }
               if (!supportedMode.equals("")) {
                   params.setFocusMode(supportedMode);
                   this.mCamera.setParameters(params);
                   return true;
                        }
                    }
           return false;
                }

       public boolean autoFocus(Camera.AutoFocusCallback cb) {
                    try {
               this.mCamera.autoFocus(cb);
               return true;
                    } catch (Exception e) {
               CrashUtil.logException(e, "Failed to autoFocus.", new Object[0]);
                        return false;
                    }
                }

       public boolean isZoomSupported() {
                    return this.mMaxZoom > 1;
                }

       public void printSupportedPreviewSizes() {
           if (this.mCamera != null) {
               Camera.Parameters param = this.mCamera.getParameters();
               Camera.Size preferred = param.getPreferredPreviewSizeForVideo();
               if (preferred != null) {
                   CrashUtil.log("Preferred size: {} * {}", Integer.valueOf(preferred.width), Integer.valueOf(preferred.height));
                        }
               List<Camera.Size> sizes = this.mCamera.getParameters().getSupportedPreviewSizes();
               if (sizes != null) {
                            for (Camera.Size size : sizes) {
                       CrashUtil.log("Supported Preview Size: {} * {}.", Integer.valueOf(size.width), Integer.valueOf(size.height));
                            }
                        }
                    }
                }

       public boolean isSmoothZoomingSupported() {
           return this.mIsSmoothZoomSupported;
                }

       public boolean canSwitchFlash() {
                    boolean z = false;
                    synchronized (CAMERA_LOCK) {
                        try {
                   if (this.mCamera != null) {
                                try {
                           List<String> modes = this.mCamera.getParameters().getSupportedFlashModes();
                           if (modes != null) {
                                        for (String mode : modes) {
                                   if ("torch".equals(mode)) {
                                       z = true;
                                            }
                                        }
                                    }
                                } catch (RuntimeException e) {
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return z;
                }

                
                
                
                
                
                
       private CameraSetting safeCameraOpen(RecordConfigUtils.RecordConfig config, boolean frontFacing, int id, int rotation, boolean reOpen) throws VineNotSupportedException {
                    Camera.Parameters params;
                    boolean z;
           if (id == -1) {
               return null;
                    }
                    synchronized (CAMERA_LOCK) {
               if (reOpen) {
                   PreviewManager.getInstance().releaseCameraAndPreview("safeCameraOpen", false);
                        } else {
                            try {
                                if (this.mCurrentCamera != id) {
                           PreviewManager.getInstance().releaseCameraAndPreview("safeCameraOpen", false);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               if (reOpen) {
                   long start = System.currentTimeMillis();
                   this.mCamera = Camera.open(id);
                   MasterPreviewCallback.getInstance().onCameraOpened(this.mCamera);
                   this.mCamera.setZoomChangeListener(this);
                   SLog.m447d("Open camera took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                   this.mIsSmoothZooming = false;
                   this.mAutoStopZoomCalled = false;
                   this.mIsSmoothZoomingStopCalled = false;
                   this.mCurrentZoomTarget = -1;
                   this.mCameraSetting = null;
                   params = this.mCamera.getParameters();
                   this.mMaxZoom = params.getMaxZoom();
                   if (RecordConfigUtils.RecordConfig.IS_ZERO_INDEX_BASED_ZOOM) {
                       this.mMaxZoom--;
                       this.mRequestedZoom = 0;
                            } else {
                       this.mRequestedZoom = 1;
                            }
                   if (RecordConfigUtils.RecordConfig.SHOULD_FORCE_SMOOTH_ZOOM) {
                                z = true;
                            } else {
                                z = true;
                            }
                            this.mIsSmoothZoomSupported = z;
                   if (this.mCamera != null) {
                       SLog.m446d("1. Open Camera.");
                       this.mCurrentCamera = id;
                       this.mIsCurrentFront = frontFacing;
                       this.mCameraSetting = getCameraInfo(config, this.mCurrentCamera, params, rotation, frontFacing);
                       return this.mCameraSetting;
                            }
                        } else {
                            try {
                                if (this.mCamera == null || this.mCurrentCamera != id) {
                           long start2 = System.currentTimeMillis();
                           this.mCamera = Camera.open(id);
                           MasterPreviewCallback.getInstance().onCameraOpened(this.mCamera);
                           this.mCamera.setZoomChangeListener(this);
                           SLog.m447d("Open camera took {}ms.", Long.valueOf(System.currentTimeMillis() - start2));
                           this.mIsSmoothZooming = false;
                           this.mAutoStopZoomCalled = false;
                           this.mIsSmoothZoomingStopCalled = false;
                           this.mCurrentZoomTarget = -1;
                                } else {
                           SLog.m446d("Camera was already opened.");
                                }
                       this.mCameraSetting = null;
                       params = this.mCamera.getParameters();
                       this.mMaxZoom = params.getMaxZoom();
                       if (RecordConfigUtils.RecordConfig.IS_ZERO_INDEX_BASED_ZOOM) {
                           this.mMaxZoom--;
                           this.mRequestedZoom = 0;
                                } else {
                           this.mRequestedZoom = 1;
                                }
                       if (RecordConfigUtils.RecordConfig.SHOULD_FORCE_SMOOTH_ZOOM || params.isSmoothZoomSupported()) {
                                    z = true;
                                } else {
                           z = false;
                                }
                                this.mIsSmoothZoomSupported = z;
                       if (this.mCamera != null) {
                           SLog.m446d("1. Open Camera.");
                           this.mCurrentCamera = id;
                           this.mIsCurrentFront = frontFacing;
                           this.mCameraSetting = getCameraInfo(config, this.mCurrentCamera, params, rotation, frontFacing);
                           return this.mCameraSetting;
                                }
                            } catch (Exception e) {
                       CrashUtil.logException(e);
                            }
                        }
               return null;
                    }
                }

       public Camera getCamera() {
           return this.mCamera;
                }

       public static int getCameraRotation(Activity activity) {
           int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                    switch (rotation) {
                        case 0:
                        default:
                            return 0;
                        case 1:
                   return 90;
                        case 2:
                   return 180;
                        case 3:
                   return 270;
                    }
                }

       public static CameraSetting getCameraInfo(RecordConfigUtils.RecordConfig config, int cameraId, Camera.Parameters params, int rotation, boolean isFront) throws VineNotSupportedException {
                    int videoFrameHeight;
                    int videoFrameWidth;
                    int videoFrameRate;
                    CamcorderProfile cp;
           int degree = getCameraDisplayOrientation(rotation, cameraId);
           if (isFront) {
               degree = -degree;
                    }
           CameraSetting cameraSetting = sCameraSettingTable.get(cameraId);
           if (cameraSetting != null) {
               CameraSetting cameraSetting2 = new CameraSetting(cameraSetting);
               cameraSetting2.degrees = degree;
               SLog.m464i("Using cached camera setting.");
               return cameraSetting2;
                    }
           List<Integer> previewFormats = null;
                    try {
               previewFormats = params.getSupportedPreviewFormats();
                    } catch (NullPointerException e) {
               CrashUtil.log("This device sucks, it cannot return params properly.");
                    }
           int selectedFormat = 17;
           if (previewFormats != null) {
                        for (Integer format : previewFormats) {
                   SLog.m447d("Supported format: {}", format);
                   if (format.intValue() == 4) {
                       selectedFormat = format.intValue();
                                break;
                            }
                        }
               if (selectedFormat == 17) {
                            for (Integer format2 : previewFormats) {
                       if (format2.intValue() == 256) {
                           selectedFormat = format2.intValue();
                                    break;
                                }
                            }
                        }
                    }
           SLog.m447d("Selected format: {}.", Integer.valueOf(selectedFormat));
                    try {
               if (CamcorderProfile.hasProfile(cameraId, 4)) {
                   cp = CamcorderProfile.get(cameraId, 4);
                   CrashUtil.log("Use default profile");
                        } else {
                   cp = CamcorderProfile.get(cameraId, 1);
                        }
               SLog.m447d("Preferred FrameRate: {}.", Integer.valueOf(cp.videoFrameRate));
               if (cp.videoFrameRate >= config.targetFrameRate) {
                   cp.videoFrameRate = config.targetFrameRate;
                        }
               CrashUtil.log("Original Width * Height: {}/{}", Integer.valueOf(cp.videoFrameWidth), Integer.valueOf(cp.videoFrameHeight));
               cp.videoFrameWidth = config.previewWidth;
               if (cp.videoFrameHeight > cp.videoFrameWidth) {
                   cp.videoFrameHeight = config.highQuality ? 480 : 240;
                        }
               SLog.m453d("Video: {}*{} codec: {} bitRate:{} frameRate:{}.", new Object[]{Integer.valueOf(cp.videoFrameWidth), Integer.valueOf(cp.videoFrameHeight), Integer.valueOf(cp.videoCodec), Integer.valueOf(cp.videoBitRate), Integer.valueOf(cp.videoFrameRate)});
               SLog.m453d("Audio: bitRate:{} channels: {} codec: {} sampleRate: {} ", new Object[]{Integer.valueOf(cp.audioBitRate), Integer.valueOf(cp.audioChannels), Integer.valueOf(cp.audioCodec), Integer.valueOf(cp.audioSampleRate)});
               videoFrameWidth = cp.videoFrameWidth;
               videoFrameHeight = cp.videoFrameHeight;
               videoFrameRate = cp.videoFrameRate;
                    } catch (Exception e2) {
               List<Camera.Size> sizes = params.getSupportedPreviewSizes();
               Set<Integer> supportedWidth = new HashSet<>();
               if (sizes != null) {
                            for (Camera.Size size : sizes) {
                       CrashUtil.log("Supported width and height: {}, {}.", Integer.valueOf(size.width), Integer.valueOf(size.height));
                       if (size.height == config.targetSize) {
                           supportedWidth.add(Integer.valueOf(size.width));
                                }
                            }
                   if (supportedWidth.isEmpty()) {
                       CrashUtil.log("Edison: Target size is not supported.");
                       videoFrameHeight = config.previewHeight;
                       videoFrameWidth = config.previewWidth;
                            } else if (supportedWidth.contains(Integer.valueOf(config.previewWidth))) {
                       videoFrameHeight = config.previewHeight;
                       videoFrameWidth = config.previewWidth;
                            } else {
                       CrashUtil.log("Edison: Target width is not supported.");
                       videoFrameHeight = config.previewHeight;
                       videoFrameWidth = config.previewWidth;
                            }
                        } else {
                   videoFrameHeight = config.previewHeight;
                   videoFrameWidth = config.previewWidth;
                        }
               videoFrameRate = config.targetFrameRate;
                    } catch (UnsatisfiedLinkError e3) {
               throw new VineNotSupportedException(e3);
                    }
           SLog.m447d("FFC OR: {}.", Float.valueOf(config.frontFacingAspectRatioOverride));
           SLog.m447d("BFC OR: {}.", Float.valueOf(config.backFacingAspectRatioOverride));
           if (videoFrameWidth == 640 && videoFrameHeight != 480) {
               CrashUtil.log("Force 480 with 480p.");
               videoFrameHeight = 480;
                    }
           CameraSetting cameraSetting3 = new CameraSetting(videoFrameWidth, videoFrameHeight, degree, videoFrameRate, selectedFormat, isFront, config.frontFacingAspectRatioOverride, config.backFacingAspectRatioOverride);
           sCameraSettingTable.put(cameraId, cameraSetting3);
           CameraSetting cameraSetting4 = new CameraSetting(cameraSetting3);
           if (SLog.sLogsOn) {
               Camera.Size size2 = params.getPreferredPreviewSizeForVideo();
               if (size2 != null) {
                   SLog.m448d("Preferred Width * Height: {}/{}.", Integer.valueOf(size2.width), Integer.valueOf(size2.height));
                        }
               List<Camera.Size> sizes2 = params.getSupportedVideoSizes();
               if (sizes2 != null) {
                            for (Camera.Size s : sizes2) {
                       SLog.m448d("Supported Video Width * Height: {}/{}.", Integer.valueOf(s.width), Integer.valueOf(s.height));
                            }
                        }
               List<Camera.Size> sizes3 = params.getSupportedPreviewSizes();
               if (sizes3 != null) {
                            for (Camera.Size s2 : sizes3) {
                       SLog.m448d("Supported Preview Width * Height: {}/{}.", Integer.valueOf(s2.width), Integer.valueOf(s2.height));
                            }
                        }
               SLog.m453d("Camera w/h/f/p/d: {}/{}/{}/{}/{}", new Object[]{Integer.valueOf(cameraSetting4.originalW), Integer.valueOf(cameraSetting4.originalH), Integer.valueOf(cameraSetting4.fps), Integer.valueOf(params.getPreviewFormat()), Integer.valueOf(cameraSetting4.degrees)});
                    }
           return cameraSetting4;
                }

       public int getDefaultCameraId(boolean frontFacing) {
           if (frontFacing) {
               if (hasFrontFacingCamera()) {
                            return sFrontFacing[0].intValue();
                        }
                        return -1;
                    }
                    if (hasBackFacingCamera()) {
                        return sBackFacing[0].intValue();
                    }
                    return -1;
                }

       public CameraSetting openDefaultCamera(RecordConfigUtils.RecordConfig config, boolean frontFacing, int rotation, boolean reOpen) throws VineNotSupportedException {
           return safeCameraOpen(config, frontFacing, getDefaultCameraId(frontFacing), rotation, reOpen);
                }

       public boolean startContinuousAutoFocus(Camera.Parameters params) {
           String supportedMode = "";
           List<String> focusModes = params.getSupportedFocusModes();
           if (focusModes == null) {
                        return false;
                    }
           if (RecordConfigUtils.RecordConfig.FORCE_AUTO_FOCUS) {
               supportedMode = "auto";
               SLog.m446d("Focus Mode: AUTO");
                    } else if (focusModes.contains("continuous-video")) {
               supportedMode = "continuous-video";
               SLog.m446d("Focus Mode: CONTINUOUS VIDEO");
                    } else if (focusModes.contains("infinity")) {
               supportedMode = "infinity";
               SLog.m446d("Focus Mode: INFINITY");
                    }
           String originalFocusMode = params.getFocusMode();
                    try {
               if (supportedMode.equals("")) {
                            return false;
                        }
               params.setFocusMode(supportedMode);
               this.mCamera.setParameters(params);
               return true;
                    } catch (Exception e) {
               CrashUtil.logException(e, "Continuous Auto Focus is not supported on this device.", new Object[0]);
               params.setFocusMode(originalFocusMode);
                        return false;
                    }
                }

       protected void lock() {
           SLog.m446d("5d. Lock Camera");
           this.mCamera.lock();
                }

                @TargetApi(15)
       public void disableVideoStab(Camera.Parameters parameters) {
           if (Build.VERSION.SDK_INT >= 15 && parameters.isVideoStabilizationSupported()) {
               parameters.setVideoStabilization(false);
                    }
                }

       public boolean isFlashSupported() {
           List<String> modes = this.mCamera.getParameters().getSupportedFlashModes();
           if (modes == null || modes.size() == 0) {
                        return false;
                    }
                    for (String mode : modes) {
               if ("on".equals(mode) || "torch".equals(mode)) {
                            return true;
                        }
                    }
                    return false;
                }

       public int setFrameRate(int frameRate) {
                    synchronized (CAMERA_LOCK) {
                        try {
                   if (this.mCamera != null) {
                                try {
                           Camera.Parameters parameters = this.mCamera.getParameters();
                           parameters.setPreviewFpsRange(frameRate * 1000, frameRate * 1000);
                           SLog.m446d("SET frameRate using fixed-range frameRate");
                           this.mCamera.setParameters(parameters);
                                    return frameRate;
                                } catch (Exception e) {
                                    try {
                               Camera.Parameters parameters2 = this.mCamera.getParameters();
                               parameters2.setPreviewFrameRate(frameRate);
                               this.mCamera.setParameters(parameters2);
                               SLog.m446d("SET frameRate using fixed frameRate");
                                        return frameRate;
                                    } catch (Exception e2) {
                                        try {
                                   Camera.Parameters parameters3 = this.mCamera.getParameters();
                                            try {
                                       List<int[]> ss = parameters3.getSupportedPreviewFpsRange();
                                       if (ss == null) {
                                           CrashUtil.log("Cannot set frame-rate");
                                                    return frameRate;
                                                }
                                       int frameRate2 = frameRate * 1000;
                                       int[] selected = null;
                                       int lowest = 0;
                                                int highest = 0;
                                                int reachedIndex = -1;
                                                int otherIndex = ss.size() - 1;
                                                for (int i = 0; i < ss.size(); i++) {
                                           int[] s = ss.get(i);
                                           SLog.m447d("Supported FPS: {}", s);
                                           if (s[0] == frameRate2) {
                                               selected = s;
                                                        break;
                                                    }
                                           if (s[1] == frameRate2) {
                                               if (lowest < s[0]) {
                                                   lowest = s[0];
                                                   reachedIndex = i;
                                                        }
                                               if (highest <= s[1]) {
                                                   highest = s[1];
                                                        }
                                                    } else if (lowest <= s[0]) {
                                               lowest = s[0];
                                               if (highest <= s[1]) {
                                                   highest = s[1];
                                                   otherIndex = i;
                                                        }
                                                    }
                                                }
                                       if (selected == null) {
                                           if (reachedIndex != -1) {
                                               int[] selected2 = ss.get(reachedIndex);
                                                        selected = selected2;
                                                    } else {
                                               int[] selected3 = ss.get(otherIndex);
                                                        selected = selected3;
                                                    }
                                                }
                                       if (selected[1] >= frameRate2) {
                                                    try {
                                               SLog.m448d("Try Using variable FPS: {}, {}.", Integer.valueOf(lowest), Integer.valueOf(frameRate2));
                                               parameters3.setPreviewFpsRange(lowest, frameRate2);
                                               this.mCamera.setParameters(parameters3);
                                               return frameRate2 / 1000;
                                                    } catch (Exception e3) {
                                                        try {
                                                   Camera.Parameters parameters4 = this.mCamera.getParameters();
                                                   SLog.m447d("Try Using fixed framerate FPS: {}", Integer.valueOf(frameRate2));
                                                   parameters4.setPreviewFrameRate(frameRate2 / 1000);
                                                   this.mCamera.setParameters(parameters4);
                                                   return frameRate2 / 1000;
                                                        } catch (Exception e4) {
                                                   Camera.Parameters parameters5 = this.mCamera.getParameters();
                                                   SLog.m449d("Try Using selected framerate FPS: {}, {}, {}.", Integer.valueOf(selected[0]), Integer.valueOf(selected[1]), Integer.valueOf(frameRate2));
                                                   parameters5.setPreviewFpsRange(selected[0], selected[1]);
                                                   this.mCamera.setParameters(parameters5);
                                                   return selected[1] / 1000;
                                                        }
                                                    }
                                                }
                                       SLog.m446d("Fall back, should never reach here.");
                                       int frameRate3 = frameRate2 / 1000;
                                       parameters3.setPreviewFrameRate(frameRate3);
                                       this.mCamera.setParameters(parameters3);
                                                return frameRate3;
                                            } catch (Exception e5) {
                                       return 30;
                                            }
                                        } catch (Exception e6) {
                                   return 30;
                                        }
                                    }
                                }
                            }
                   return 30;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       protected int fixOrientation(int rotation) {
                    int fixedDegrees;
                    synchronized (CAMERA_LOCK) {
               fixedDegrees = 0;
                        try {
                   if (this.mCamera != null) {
                       fixedDegrees = getCameraDisplayOrientation(rotation, this.mCurrentCamera);
                       this.mCamera.setDisplayOrientation(fixedDegrees);
                       SLog.m447d("Fixed orientation: {}", Integer.valueOf(fixedDegrees));
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return fixedDegrees;
                }

       protected void prepareFocusMatrix(int width, int height, int fixedDegrees) {
           if (width != 0 && height != 0) {
               Matrix matrix = new Matrix();
               MathUtil.prepareMatrix(matrix, this.mIsCurrentFront, fixedDegrees, width, height);
               matrix.invert(this.mMatrix);
                    }
                }

                
                
                
       public void changeFlashState(boolean enabled) {
                    String str;
                    synchronized (CAMERA_LOCK) {
               if (!enabled) {
                   Camera.Parameters parameters = this.mCamera.getParameters();
                   if (enabled) {
                                str = "torch";
                            } else {
                       str = "off";
                            }
                            parameters.setFlashMode(str);
                   this.mCamera.setParameters(parameters);
                        } else {
                            try {
                                if (canSwitchFlash()) {
                           Camera.Parameters parameters2 = this.mCamera.getParameters();
                           if (enabled) {
                                        str = "torch";
                                    } else {
                               str = "off";
                                    }
                                    parameters2.setFlashMode(str);
                           this.mCamera.setParameters(parameters2);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

       public static synchronized int getCameraDisplayOrientation(int degrees, int cameraId) {
                    int result;
                    try {
                        Camera.CameraInfo info = sCameraInfoTable.get(cameraId);
               if (info == null) {
                   info = new Camera.CameraInfo();
                   Camera.getCameraInfo(cameraId, info);
                   sCameraInfoTable.put(cameraId, info);
                        } else {
                   SLog.m464i("Using cached camera info.");
                        }
               SLog.m447d("Set rotation to {} degrees", Integer.valueOf(degrees));
               if (info.facing == 1) {
                   int result2 = (info.orientation + degrees) % 360;
                   result = (360 - result2) % 360;
                        } else {
                   result = ((info.orientation - degrees) + 360) % 360;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return result;
                }

       public int modifyZoom(boolean zoomIn) {
                    int newZoom;
           if (this.mIsSmoothZoomSupported) {
               newZoom = zoomIn ? this.mMaxZoom : 0;
                    } else {
               newZoom = zoomIn ? this.mRequestedZoom + 1 : this.mRequestedZoom - 1;
                    }
           if (RecordConfigUtils.RecordConfig.IS_ZERO_INDEX_BASED_ZOOM) {
               newZoom++;
                    }
           return modifyZoom(newZoom);
                }

       public void startSmoothZoom(boolean zoomIn) {
           if (!this.mIsSmoothZoomSupported) {
               throw new IllegalStateException("Smooth zoom not supported.");
                    }
           if (!this.mIsSmoothZooming) {
               SLog.m447d("Smooth zoom started on value {}", Integer.valueOf(this.mRequestedZoom));
               stopContinuousFocus(this.mCamera.getParameters());
               this.mIsSmoothZooming = true;
               this.mCurrentZoomTarget = this.mRequestedZoom;
               if (zoomIn) {
                   this.mCamera.startSmoothZoom(this.mMaxZoom);
                        } else {
                   this.mCamera.startSmoothZoom(0);
                        }
                    }
                }

       public void startInstantZoom() {
           SLog.m447d("Instant zoom started on value {}", Integer.valueOf(this.mRequestedZoom));
           this.mCurrentZoomTarget = this.mRequestedZoom;
           Camera.Parameters params = this.mCamera.getParameters();
           params.setZoom(this.mRequestedZoom);
           this.mCamera.setParameters(params);
           onZoomChange(this.mRequestedZoom, true, this.mCamera);
                }

       public int getMaxZoom() {
           return this.mMaxZoom;
                }

                @Override 
       public void onZoomChange(int zoomValue, boolean stopped, Camera camera) {
           if (stopped) {
               SLog.m449d("Zoom stopped on value {} (target {}, queued value: {}).", Integer.valueOf(zoomValue), Integer.valueOf(this.mCurrentZoomTarget), Integer.valueOf(this.mRequestedZoom));
               this.mIsSmoothZooming = false;
               if (this.mCamera != null) {
                            try {
                       startContinuousAutoFocus(this.mCamera.getParameters());
                            } catch (RuntimeException e) {
                            }
                        }
               if (this.mIsSmoothZoomingStopCalled) {
                   this.mIsSmoothZoomingStopCalled = false;
                   if (this.mRequestedZoom == this.mCurrentZoomTarget) {
                       this.mRequestedZoom = zoomValue;
                            }
                        }
               if (zoomValue != this.mRequestedZoom && this.mIsSmoothZoomSupported && this.mCamera != null) {
                   startSmoothZoom(this.mRequestedZoom >= zoomValue);
                        }
                    } else {
               SLog.m448d("Zoom updated on value {} (queued value: {}).", Integer.valueOf(zoomValue), Integer.valueOf(this.mRequestedZoom));
               if (this.mCurrentZoomTarget == zoomValue) {
                   SLog.m446d("Stop zoom on reaching target.");
                   this.mAutoStopZoomCalled = stopSmoothZoom();
                        }
                    }
           MasterPreviewCallback.getInstance().onZoomUpdated(RecordConfigUtils.RecordConfig.IS_ZERO_INDEX_BASED_ZOOM ? zoomValue + 1 : zoomValue, stopped && zoomValue == this.mRequestedZoom);
           if (stopped) {
               this.mAutoStopZoomCalled = false;
                    }
                }

       public boolean stopSmoothZoom() {
           if (this.mIsSmoothZooming && !this.mIsSmoothZoomingStopCalled) {
               this.mIsSmoothZoomingStopCalled = true;
               SLog.m446d("Stop smooth zoom.");
                        try {
                   this.mCamera.stopSmoothZoom();
                            return true;
                        } catch (Exception e) {
                   CrashUtil.log("Failed to stop smooth zoom.");
                            return true;
                        }
                    }
           return false;
                }

       public int modifyZoom(int zoom) {
                    synchronized (CAMERA_LOCK) {
                        try {
                   if (this.mCamera != null) {
                       int min = 0;
                       if (RecordConfigUtils.RecordConfig.IS_ZERO_INDEX_BASED_ZOOM) {
                           zoom--;
                           min = -1;
                                }
                       if (zoom != this.mRequestedZoom && zoom <= this.mMaxZoom && zoom > min) {
                           boolean zoomIn = zoom > this.mRequestedZoom;
                           this.mRequestedZoom = zoom;
                                    try {
                               SLog.m448d("Modifying waiting to zoom to {} using smooth? {}.", Integer.valueOf(this.mRequestedZoom), Boolean.valueOf(this.mIsSmoothZoomSupported));
                               if (this.mIsSmoothZoomSupported) {
                                   startSmoothZoom(zoomIn);
                                        } else {
                                   startInstantZoom();
                                        }
                                    } catch (Exception e) {
                               CrashUtil.logException(e, "It just didn't work.", new Object[0]);
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return this.mRequestedZoom;
                }

       public void modifyExposure(boolean up) {
                    synchronized (CAMERA_LOCK) {
                        try {
                   if (this.mCamera != null) {
                                try {
                           Camera.Parameters params = this.mCamera.getParameters();
                           int current = params.getExposureCompensation();
                           int max = params.getMaxExposureCompensation();
                           int min = params.getMinExposureCompensation();
                           double step = ((double) (max - min)) / 10.0d;
                           if (up) {
                               int val = (int) Math.floor(((double) current) + step);
                               if (val < max) {
                                            max = val;
                                        }
                                        params.setExposureCompensation(max);
                                    } else {
                               int val2 = (int) Math.floor(((double) current) - step);
                               if (val2 > min) {
                                            min = val2;
                                        }
                                        params.setExposureCompensation(min);
                                    }
                           this.mCamera.setParameters(params);
                                } catch (Exception e) {
                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private String getNextParamValue(boolean up, List<String> supported, String current) {
                    int i;
           if (supported != null) {
               int i2 = 0;
               while (i2 < supported.size() && !supported.get(i2).equals(current)) {
                   i2++;
                        }
               if (up) {
                   i = i2 + 1;
                   if (i >= supported.size()) {
                                i = supported.size() - 1;
                            }
                        } else {
                   i = i2 - 1;
                   if (i < 0) {
                                i = 0;
                            }
                        }
               SLog.m447d("Modify to: {}", supported.get(i));
               return supported.get(i);
                    }
           return null;
                }

       public void modifyWhiteBalance(boolean up) {
                    synchronized (CAMERA_LOCK) {
                        try {
                   if (this.mCamera != null) {
                                try {
                           Camera.Parameters params = this.mCamera.getParameters();
                           String next = getNextParamValue(up, params.getSupportedWhiteBalance(), params.getWhiteBalance());
                           if (next != null) {
                               params.setWhiteBalance(next);
                               this.mCamera.setParameters(params);
                                    }
                                } catch (Exception e) {
                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void modifySceneMode(boolean up) {
                    synchronized (CAMERA_LOCK) {
                        try {
                   if (this.mCamera != null) {
                                try {
                           Camera.Parameters params = this.mCamera.getParameters();
                           String next = getNextParamValue(up, params.getSupportedSceneModes(), params.getSceneMode());
                           if (next != null) {
                               params.setSceneMode(next);
                               this.mCamera.setParameters(params);
                                    }
                                } catch (Exception e) {
                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void modifyColorEffects(boolean up) {
                    synchronized (CAMERA_LOCK) {
                        try {
                   if (this.mCamera != null) {
                                try {
                           Camera.Parameters params = this.mCamera.getParameters();
                           String next = getNextParamValue(up, params.getSupportedColorEffects(), params.getColorEffect());
                           if (next != null) {
                               params.setColorEffect(next);
                               this.mCamera.setParameters(params);
                                    }
                           this.mCamera.setParameters(params);
                                } catch (Exception e) {
                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void modifyAntiBanding(boolean up) {
                    synchronized (CAMERA_LOCK) {
                        try {
                   if (this.mCamera != null) {
                                try {
                           Camera.Parameters params = this.mCamera.getParameters();
                           String next = getNextParamValue(up, params.getSupportedAntibanding(), params.getAntibanding());
                           if (next != null) {
                               params.setAntibanding(next);
                               this.mCamera.setParameters(params);
                                    }
                           this.mCamera.setParameters(params);
                                } catch (Exception e) {
                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @TargetApi(15)
       public void switchImageStabilization() {
                    synchronized (CAMERA_LOCK) {
                        try {
                   if (Build.VERSION.SDK_INT >= 15 && this.mCamera != null) {
                                try {
                           Camera.Parameters params = this.mCamera.getParameters();
                           if (params.isVideoStabilizationSupported()) {
                               params.setVideoStabilization(!params.getVideoStabilization());
                                    }
                           this.mCamera.setParameters(params);
                                } catch (Exception e) {
                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void releaseCamera() {
                    try {
               this.mCamera.release();
                    } catch (RuntimeException e) {
               CrashUtil.log("Failed to release camear, probably already released: " + e.getMessage());
                    }
           this.mCamera = null;
           this.mCameraSetting = null;
           this.mCurrentCamera = -1;
                }
            }
