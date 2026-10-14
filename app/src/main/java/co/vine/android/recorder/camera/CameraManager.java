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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 35 */    public class CameraManager implements Camera.OnZoomChangeListener {
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
/* 70 */            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
/* 72 */            int numberOfCameras = Camera.getNumberOfCameras();
/* 74 */            ArrayList<Integer> frontFacing = new ArrayList<>();
/* 75 */            ArrayList<Integer> backFacing = new ArrayList<>();
                    for (int i = 0; i < numberOfCameras; i++) {
                        try {
/* 79 */                    Camera.getCameraInfo(i, cameraInfo);
/* 80 */                    if (cameraInfo.facing == 1) {
/* 81 */                        frontFacing.add(Integer.valueOf(i));
                            } else if (cameraInfo.facing == 0) {
/* 83 */                        backFacing.add(Integer.valueOf(i));
                            }
                        } catch (Exception e) {
/* 86 */                    CrashUtil.logException(e, "Cannot get camera info for camera #{}.", Integer.valueOf(i));
                        }
                    }
/* 89 */            sFrontFacing = (Integer[]) frontFacing.toArray(new Integer[frontFacing.size()]);
/* 90 */            sBackFacing = (Integer[]) backFacing.toArray(new Integer[backFacing.size()]);
/* 93 */            sCameraInfoTable = new SparseArray<>();
/* 94 */            sCameraSettingTable = new SparseArray<>();
                }

/* 45 */        public boolean addRecordingHintIfNeeded(Camera.Parameters parameters, RecordConfigUtils.RecordConfig config) {
/* 46 */            boolean setRecordingHint = (this.mIsCurrentFront && config.frontFacingRecordingHint) || (!this.mIsCurrentFront && config.backFacingRecordingHint);
/* 47 */            if (setRecordingHint) {
/* 48 */                parameters.setRecordingHint(true);
                    }
/* 50 */            return setRecordingHint;
                }

/* 112 */       public static boolean hasCamera() {
                    return hasBackFacingCamera() || hasFrontFacingCamera();
                }

/* 116 */       public static boolean hasFrontFacingCamera() {
                    return sFrontFacing.length > 0;
                }

/* 120 */       public static boolean hasBackFacingCamera() {
                    return sBackFacing.length > 0;
                }

/* 123 */       private CameraManager() {
                }

                @NonNull
/* 131 */       public static synchronized CameraManager getInstance() {
                    try {
                        if (INSTANCE == null) {
/* 133 */                   INSTANCE = new CameraManager();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 135 */           return INSTANCE;
                }

/* 138 */       public boolean isCameraReady() {
                    return (this.mCamera == null || this.mCameraSetting == null) ? false : true;
                }

/* 142 */       public CameraSetting getCameraSetting() {
/* 143 */           return this.mCameraSetting;
                }

/* 147 */       public boolean canChangeFocus() {
                    try {
                        return this.mCamera != null && this.mCamera.getParameters().getMaxNumFocusAreas() > 0;
                    } catch (Exception e) {
                        return false;
                    }
                }

/* 155 */       public boolean setFocusAreas(int x, int y) {
                    try {
/* 156 */               initializeFocusAreas(x, y);
/* 157 */               Camera.Parameters params = this.mCamera.getParameters();
/* 158 */               params.setFocusAreas(this.mFocusArea);
/* 159 */               this.mCamera.setParameters(params);
                        return true;
                    } catch (Exception e) {
/* 162 */               CrashUtil.logException(e, "Failed to set focus areas {}, {}.", this.mMatrix, this.mFocusArea.get(0).rect);
                        return false;
                    }
                }

/* 167 */       private void initializeFocusAreas(int x, int y) {
/* 168 */           if (this.mFocusArea == null) {
/* 169 */               this.mFocusArea = new ArrayList<>();
/* 170 */               this.mFocusArea.add(new Camera.Area(new Rect(), 1));
                    }
/* 174 */           calculateTapArea(100, 100, 1.0f, x, y, this.mCameraSetting.originalW, this.mCameraSetting.originalH, this.mFocusArea.get(0).rect);
                }

/* 179 */       private void calculateTapArea(int focusWidth, int focusHeight, float areaMultiple, int x, int y, int previewWidth, int previewHeight, Rect rect) {
/* 180 */           int areaWidth = (int) (focusWidth * areaMultiple);
/* 181 */           int areaHeight = (int) (focusHeight * areaMultiple);
/* 182 */           int left = MathUtil.clamp(x - (areaWidth / 2), 0, previewWidth - areaWidth);
/* 183 */           int top = MathUtil.clamp(y - (areaHeight / 2), 0, previewHeight - areaHeight);
/* 185 */           RectF rectF = new RectF(left, top, left + areaWidth, top + areaHeight);
/* 186 */           this.mMatrix.mapRect(rectF);
/* 187 */           MathUtil.rectFToRect(rectF, rect);
                }

/* 190 */       public Camera.Parameters getParameters() {
/* 191 */           if (this.mCamera != null) {
                        return this.mCamera.getParameters();
                    }
                    return null;
                }

/* 194 */       public boolean stopContinuousFocus(Camera.Parameters params) {
/* 195 */           String supportedMode = "";
/* 197 */           List<String> focusModes = params.getSupportedFocusModes();
/* 199 */           SLog.m447d("Force auto focus: {}", Boolean.valueOf(RecordConfigUtils.RecordConfig.FORCE_AUTO_FOCUS));
/* 201 */           if (focusModes != null) {
/* 203 */               if (focusModes.contains("auto") || RecordConfigUtils.RecordConfig.FORCE_AUTO_FOCUS) {
/* 205 */                   supportedMode = "auto";
/* 206 */                   SLog.m446d("Focus Mode: FOCUS_MODE_AUTO");
                        } else if (focusModes.contains("continuous-picture")) {
/* 209 */                   supportedMode = "continuous-picture";
/* 210 */                   SLog.m446d("Focus Mode: FOCUS_MODE_CONTINUOUS_PICTURE");
                        }
/* 214 */               if (!supportedMode.equals("")) {
/* 215 */                   params.setFocusMode(supportedMode);
/* 216 */                   this.mCamera.setParameters(params);
/* 217 */                   return true;
                        }
                    }
/* 220 */           return false;
                }

/* 224 */       public boolean autoFocus(Camera.AutoFocusCallback cb) {
                    try {
/* 225 */               this.mCamera.autoFocus(cb);
/* 226 */               return true;
                    } catch (Exception e) {
/* 228 */               CrashUtil.logException(e, "Failed to autoFocus.", new Object[0]);
                        return false;
                    }
                }

/* 233 */       public boolean isZoomSupported() {
                    return this.mMaxZoom > 1;
                }

/* 237 */       public void printSupportedPreviewSizes() {
/* 238 */           if (this.mCamera != null) {
/* 239 */               Camera.Parameters param = this.mCamera.getParameters();
/* 240 */               Camera.Size preferred = param.getPreferredPreviewSizeForVideo();
/* 241 */               if (preferred != null) {
/* 242 */                   CrashUtil.log("Preferred size: {} * {}", Integer.valueOf(preferred.width), Integer.valueOf(preferred.height));
                        }
/* 244 */               List<Camera.Size> sizes = this.mCamera.getParameters().getSupportedPreviewSizes();
/* 245 */               if (sizes != null) {
                            for (Camera.Size size : sizes) {
/* 247 */                       CrashUtil.log("Supported Preview Size: {} * {}.", Integer.valueOf(size.width), Integer.valueOf(size.height));
                            }
                        }
                    }
                }

/* 254 */       public boolean isSmoothZoomingSupported() {
/* 255 */           return this.mIsSmoothZoomSupported;
                }

/* 258 */       public boolean canSwitchFlash() {
                    boolean z = false;
                    synchronized (CAMERA_LOCK) {
                        try {
/* 260 */                   if (this.mCamera != null) {
                                try {
/* 262 */                           List<String> modes = this.mCamera.getParameters().getSupportedFlashModes();
/* 263 */                           if (modes != null) {
                                        for (String mode : modes) {
/* 267 */                                   if ("torch".equals(mode)) {
/* 268 */                                       z = true;
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
/* 277 */           return z;
                }

                /* JADX WARN: Code duplicated, block: B:11:0x000e A[Catch: all -> 0x0097, TRY_LEAVE, TryCatch #1 {all -> 0x0097, blocks: (B:9:0x000a, B:13:0x001a, B:15:0x001e, B:34:0x009a, B:18:0x0054, B:20:0x0067, B:21:0x0070, B:23:0x0074, B:26:0x007b, B:28:0x0081, B:29:0x0094, B:38:0x00a4, B:41:0x00a9, B:17:0x0022, B:37:0x00a1, B:11:0x000e), top: B:46:0x000a, inners: #0 }] */
                /* JADX WARN: Code duplicated, block: B:17:0x0022 A[Catch: all -> 0x0097, Exception -> 0x00a0, TryCatch #0 {Exception -> 0x00a0, blocks: (B:13:0x001a, B:15:0x001e, B:34:0x009a, B:18:0x0054, B:20:0x0067, B:21:0x0070, B:23:0x0074, B:26:0x007b, B:28:0x0081, B:41:0x00a9, B:17:0x0022), top: B:44:0x001a, outer: #1 }] */
                /* JADX WARN: Code duplicated, block: B:20:0x0067 A[Catch: all -> 0x0097, Exception -> 0x00a0, TryCatch #0 {Exception -> 0x00a0, blocks: (B:13:0x001a, B:15:0x001e, B:34:0x009a, B:18:0x0054, B:20:0x0067, B:21:0x0070, B:23:0x0074, B:26:0x007b, B:28:0x0081, B:41:0x00a9, B:17:0x0022), top: B:44:0x001a, outer: #1 }] */
                /* JADX WARN: Code duplicated, block: B:25:0x007a  */
                /* JADX WARN: Code duplicated, block: B:28:0x0081 A[Catch: all -> 0x0097, Exception -> 0x00a0, TRY_LEAVE, TryCatch #0 {Exception -> 0x00a0, blocks: (B:13:0x001a, B:15:0x001e, B:34:0x009a, B:18:0x0054, B:20:0x0067, B:21:0x0070, B:23:0x0074, B:26:0x007b, B:28:0x0081, B:41:0x00a9, B:17:0x0022), top: B:44:0x001a, outer: #1 }] */
                /* JADX WARN: Code duplicated, block: B:40:0x00a8  */
/* 280 */       private CameraSetting safeCameraOpen(RecordConfigUtils.RecordConfig config, boolean frontFacing, int id, int rotation, boolean reOpen) throws VineNotSupportedException {
                    Camera.Parameters params;
                    boolean z;
/* 281 */           if (id == -1) {
/* 282 */               return null;
                    }
                    synchronized (CAMERA_LOCK) {
/* 286 */               if (reOpen) {
/* 287 */                   PreviewManager.getInstance().releaseCameraAndPreview("safeCameraOpen", false);
                        } else {
                            try {
                                if (this.mCurrentCamera != id) {
/* 287 */                           PreviewManager.getInstance().releaseCameraAndPreview("safeCameraOpen", false);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 290 */               if (reOpen) {
/* 291 */                   long start = System.currentTimeMillis();
/* 292 */                   this.mCamera = Camera.open(id);
/* 293 */                   MasterPreviewCallback.getInstance().onCameraOpened(this.mCamera);
/* 294 */                   this.mCamera.setZoomChangeListener(this);
/* 295 */                   SLog.m447d("Open camera took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
/* 296 */                   this.mIsSmoothZooming = false;
/* 297 */                   this.mAutoStopZoomCalled = false;
/* 298 */                   this.mIsSmoothZoomingStopCalled = false;
/* 299 */                   this.mCurrentZoomTarget = -1;
/* 303 */                   this.mCameraSetting = null;
/* 304 */                   params = this.mCamera.getParameters();
/* 305 */                   this.mMaxZoom = params.getMaxZoom();
/* 306 */                   if (RecordConfigUtils.RecordConfig.IS_ZERO_INDEX_BASED_ZOOM) {
/* 307 */                       this.mMaxZoom--;
/* 308 */                       this.mRequestedZoom = 0;
                            } else {
/* 310 */                       this.mRequestedZoom = 1;
                            }
/* 313 */                   if (RecordConfigUtils.RecordConfig.SHOULD_FORCE_SMOOTH_ZOOM) {
                                z = true;
                            } else {
                                z = true;
                            }
                            this.mIsSmoothZoomSupported = z;
/* 315 */                   if (this.mCamera != null) {
/* 316 */                       SLog.m446d("1. Open Camera.");
/* 317 */                       this.mCurrentCamera = id;
/* 318 */                       this.mIsCurrentFront = frontFacing;
/* 319 */                       this.mCameraSetting = getCameraInfo(config, this.mCurrentCamera, params, rotation, frontFacing);
/* 320 */                       return this.mCameraSetting;
                            }
                        } else {
                            try {
                                if (this.mCamera == null || this.mCurrentCamera != id) {
/* 291 */                           long start2 = System.currentTimeMillis();
/* 292 */                           this.mCamera = Camera.open(id);
/* 293 */                           MasterPreviewCallback.getInstance().onCameraOpened(this.mCamera);
/* 294 */                           this.mCamera.setZoomChangeListener(this);
/* 295 */                           SLog.m447d("Open camera took {}ms.", Long.valueOf(System.currentTimeMillis() - start2));
/* 296 */                           this.mIsSmoothZooming = false;
/* 297 */                           this.mAutoStopZoomCalled = false;
/* 298 */                           this.mIsSmoothZoomingStopCalled = false;
/* 299 */                           this.mCurrentZoomTarget = -1;
                                } else {
/* 301 */                           SLog.m446d("Camera was already opened.");
                                }
/* 303 */                       this.mCameraSetting = null;
/* 304 */                       params = this.mCamera.getParameters();
/* 305 */                       this.mMaxZoom = params.getMaxZoom();
/* 306 */                       if (RecordConfigUtils.RecordConfig.IS_ZERO_INDEX_BASED_ZOOM) {
/* 307 */                           this.mMaxZoom--;
/* 308 */                           this.mRequestedZoom = 0;
                                } else {
/* 310 */                           this.mRequestedZoom = 1;
                                }
/* 313 */                       if (RecordConfigUtils.RecordConfig.SHOULD_FORCE_SMOOTH_ZOOM || params.isSmoothZoomSupported()) {
                                    z = true;
                                } else {
/* 313 */                           z = false;
                                }
                                this.mIsSmoothZoomSupported = z;
/* 315 */                       if (this.mCamera != null) {
/* 316 */                           SLog.m446d("1. Open Camera.");
/* 317 */                           this.mCurrentCamera = id;
/* 318 */                           this.mIsCurrentFront = frontFacing;
/* 319 */                           this.mCameraSetting = getCameraInfo(config, this.mCurrentCamera, params, rotation, frontFacing);
/* 320 */                           return this.mCameraSetting;
                                }
                            } catch (Exception e) {
/* 323 */                       CrashUtil.logException(e);
                            }
                        }
/* 327 */               return null;
                    }
                }

/* 330 */       public Camera getCamera() {
/* 331 */           return this.mCamera;
                }

/* 334 */       public static int getCameraRotation(Activity activity) {
/* 335 */           int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                    switch (rotation) {
                        case 0:
                        default:
                            return 0;
                        case 1:
/* 341 */                   return 90;
                        case 2:
/* 343 */                   return 180;
                        case 3:
/* 345 */                   return 270;
                    }
                }

/* 350 */       public static CameraSetting getCameraInfo(RecordConfigUtils.RecordConfig config, int cameraId, Camera.Parameters params, int rotation, boolean isFront) throws VineNotSupportedException {
                    int videoFrameHeight;
                    int videoFrameWidth;
                    int videoFrameRate;
                    CamcorderProfile cp;
/* 351 */           int degree = getCameraDisplayOrientation(rotation, cameraId);
/* 352 */           if (isFront) {
/* 353 */               degree = -degree;
                    }
/* 355 */           CameraSetting cameraSetting = sCameraSettingTable.get(cameraId);
/* 356 */           if (cameraSetting != null) {
/* 357 */               CameraSetting cameraSetting2 = new CameraSetting(cameraSetting);
/* 358 */               cameraSetting2.degrees = degree;
/* 359 */               SLog.m464i("Using cached camera setting.");
/* 485 */               return cameraSetting2;
                    }
/* 362 */           List<Integer> previewFormats = null;
                    try {
/* 364 */               previewFormats = params.getSupportedPreviewFormats();
                    } catch (NullPointerException e) {
/* 366 */               CrashUtil.log("This device sucks, it cannot return params properly.");
                    }
/* 368 */           int selectedFormat = 17;
/* 369 */           if (previewFormats != null) {
                        for (Integer format : previewFormats) {
/* 371 */                   SLog.m447d("Supported format: {}", format);
/* 372 */                   if (format.intValue() == 4) {
/* 373 */                       selectedFormat = format.intValue();
                                break;
                            }
                        }
/* 377 */               if (selectedFormat == 17) {
                            for (Integer format2 : previewFormats) {
/* 379 */                       if (format2.intValue() == 256) {
/* 380 */                           selectedFormat = format2.intValue();
                                    break;
                                }
                            }
                        }
                    }
/* 386 */           SLog.m447d("Selected format: {}.", Integer.valueOf(selectedFormat));
                    try {
/* 390 */               if (CamcorderProfile.hasProfile(cameraId, 4)) {
/* 391 */                   cp = CamcorderProfile.get(cameraId, 4);
/* 393 */                   CrashUtil.log("Use default profile");
                        } else {
/* 395 */                   cp = CamcorderProfile.get(cameraId, 1);
                        }
/* 397 */               SLog.m447d("Preferred FrameRate: {}.", Integer.valueOf(cp.videoFrameRate));
/* 398 */               if (cp.videoFrameRate >= config.targetFrameRate) {
/* 399 */                   cp.videoFrameRate = config.targetFrameRate;
                        }
/* 401 */               CrashUtil.log("Original Width * Height: {}/{}", Integer.valueOf(cp.videoFrameWidth), Integer.valueOf(cp.videoFrameHeight));
/* 403 */               cp.videoFrameWidth = config.previewWidth;
/* 405 */               if (cp.videoFrameHeight > cp.videoFrameWidth) {
/* 406 */                   cp.videoFrameHeight = config.highQuality ? 480 : 240;
                        }
/* 409 */               SLog.m453d("Video: {}*{} codec: {} bitRate:{} frameRate:{}.", new Object[]{Integer.valueOf(cp.videoFrameWidth), Integer.valueOf(cp.videoFrameHeight), Integer.valueOf(cp.videoCodec), Integer.valueOf(cp.videoBitRate), Integer.valueOf(cp.videoFrameRate)});
/* 410 */               SLog.m453d("Audio: bitRate:{} channels: {} codec: {} sampleRate: {} ", new Object[]{Integer.valueOf(cp.audioBitRate), Integer.valueOf(cp.audioChannels), Integer.valueOf(cp.audioCodec), Integer.valueOf(cp.audioSampleRate)});
/* 411 */               videoFrameWidth = cp.videoFrameWidth;
/* 412 */               videoFrameHeight = cp.videoFrameHeight;
/* 413 */               videoFrameRate = cp.videoFrameRate;
                    } catch (Exception e2) {
/* 415 */               List<Camera.Size> sizes = params.getSupportedPreviewSizes();
/* 416 */               Set<Integer> supportedWidth = new HashSet<>();
/* 417 */               if (sizes != null) {
                            for (Camera.Size size : sizes) {
/* 419 */                       CrashUtil.log("Supported width and height: {}, {}.", Integer.valueOf(size.width), Integer.valueOf(size.height));
/* 420 */                       if (size.height == config.targetSize) {
/* 421 */                           supportedWidth.add(Integer.valueOf(size.width));
                                }
                            }
/* 424 */                   if (supportedWidth.isEmpty()) {
/* 425 */                       CrashUtil.log("Edison: Target size is not supported.");
/* 426 */                       videoFrameHeight = config.previewHeight;
/* 427 */                       videoFrameWidth = config.previewWidth;
                            } else if (supportedWidth.contains(Integer.valueOf(config.previewWidth))) {
/* 430 */                       videoFrameHeight = config.previewHeight;
/* 431 */                       videoFrameWidth = config.previewWidth;
                            } else {
/* 433 */                       CrashUtil.log("Edison: Target width is not supported.");
/* 434 */                       videoFrameHeight = config.previewHeight;
/* 435 */                       videoFrameWidth = config.previewWidth;
                            }
                        } else {
/* 439 */                   videoFrameHeight = config.previewHeight;
/* 440 */                   videoFrameWidth = config.previewWidth;
                        }
/* 442 */               videoFrameRate = config.targetFrameRate;
                    } catch (UnsatisfiedLinkError e3) {
/* 444 */               throw new VineNotSupportedException(e3);
                    }
/* 446 */           SLog.m447d("FFC OR: {}.", Float.valueOf(config.frontFacingAspectRatioOverride));
/* 447 */           SLog.m447d("BFC OR: {}.", Float.valueOf(config.backFacingAspectRatioOverride));
/* 450 */           if (videoFrameWidth == 640 && videoFrameHeight != 480) {
/* 451 */               CrashUtil.log("Force 480 with 480p.");
/* 452 */               videoFrameHeight = 480;
                    }
/* 455 */           CameraSetting cameraSetting3 = new CameraSetting(videoFrameWidth, videoFrameHeight, degree, videoFrameRate, selectedFormat, isFront, config.frontFacingAspectRatioOverride, config.backFacingAspectRatioOverride);
/* 459 */           sCameraSettingTable.put(cameraId, cameraSetting3);
/* 461 */           CameraSetting cameraSetting4 = new CameraSetting(cameraSetting3);
/* 463 */           if (SLog.sLogsOn) {
/* 464 */               Camera.Size size2 = params.getPreferredPreviewSizeForVideo();
/* 465 */               if (size2 != null) {
/* 466 */                   SLog.m448d("Preferred Width * Height: {}/{}.", Integer.valueOf(size2.width), Integer.valueOf(size2.height));
                        }
/* 468 */               List<Camera.Size> sizes2 = params.getSupportedVideoSizes();
/* 469 */               if (sizes2 != null) {
                            for (Camera.Size s : sizes2) {
/* 471 */                       SLog.m448d("Supported Video Width * Height: {}/{}.", Integer.valueOf(s.width), Integer.valueOf(s.height));
                            }
                        }
/* 474 */               List<Camera.Size> sizes3 = params.getSupportedPreviewSizes();
/* 475 */               if (sizes3 != null) {
                            for (Camera.Size s2 : sizes3) {
/* 477 */                       SLog.m448d("Supported Preview Width * Height: {}/{}.", Integer.valueOf(s2.width), Integer.valueOf(s2.height));
                            }
                        }
/* 480 */               SLog.m453d("Camera w/h/f/p/d: {}/{}/{}/{}/{}", new Object[]{Integer.valueOf(cameraSetting4.originalW), Integer.valueOf(cameraSetting4.originalH), Integer.valueOf(cameraSetting4.fps), Integer.valueOf(params.getPreviewFormat()), Integer.valueOf(cameraSetting4.degrees)});
                    }
/* 485 */           return cameraSetting4;
                }

/* 488 */       public int getDefaultCameraId(boolean frontFacing) {
/* 489 */           if (frontFacing) {
/* 490 */               if (hasFrontFacingCamera()) {
                            return sFrontFacing[0].intValue();
                        }
                        return -1;
                    }
                    if (hasBackFacingCamera()) {
                        return sBackFacing[0].intValue();
                    }
                    return -1;
                }

/* 496 */       public CameraSetting openDefaultCamera(RecordConfigUtils.RecordConfig config, boolean frontFacing, int rotation, boolean reOpen) throws VineNotSupportedException {
/* 497 */           return safeCameraOpen(config, frontFacing, getDefaultCameraId(frontFacing), rotation, reOpen);
                }

/* 500 */       public boolean startContinuousAutoFocus(Camera.Parameters params) {
/* 501 */           String supportedMode = "";
/* 503 */           List<String> focusModes = params.getSupportedFocusModes();
/* 505 */           if (focusModes == null) {
                        return false;
                    }
/* 509 */           if (RecordConfigUtils.RecordConfig.FORCE_AUTO_FOCUS) {
/* 510 */               supportedMode = "auto";
/* 511 */               SLog.m446d("Focus Mode: AUTO");
                    } else if (focusModes.contains("continuous-video")) {
/* 513 */               supportedMode = "continuous-video";
/* 514 */               SLog.m446d("Focus Mode: CONTINUOUS VIDEO");
                    } else if (focusModes.contains("infinity")) {
/* 516 */               supportedMode = "infinity";
/* 517 */               SLog.m446d("Focus Mode: INFINITY");
                    }
/* 519 */           String originalFocusMode = params.getFocusMode();
                    try {
/* 521 */               if (supportedMode.equals("")) {
                            return false;
                        }
/* 522 */               params.setFocusMode(supportedMode);
/* 523 */               this.mCamera.setParameters(params);
/* 524 */               return true;
                    } catch (Exception e) {
/* 527 */               CrashUtil.logException(e, "Continuous Auto Focus is not supported on this device.", new Object[0]);
/* 528 */               params.setFocusMode(originalFocusMode);
                        return false;
                    }
                }

/* 533 */       protected void lock() {
/* 534 */           SLog.m446d("5d. Lock Camera");
/* 535 */           this.mCamera.lock();
                }

                @TargetApi(15)
/* 539 */       public void disableVideoStab(Camera.Parameters parameters) {
/* 540 */           if (Build.VERSION.SDK_INT >= 15 && parameters.isVideoStabilizationSupported()) {
/* 541 */               parameters.setVideoStabilization(false);
                    }
                }

/* 545 */       public boolean isFlashSupported() {
/* 546 */           List<String> modes = this.mCamera.getParameters().getSupportedFlashModes();
/* 547 */           if (modes == null || modes.size() == 0) {
                        return false;
                    }
                    for (String mode : modes) {
/* 551 */               if ("on".equals(mode) || "torch".equals(mode)) {
                            return true;
                        }
                    }
                    return false;
                }

/* 560 */       public int setFrameRate(int frameRate) {
                    synchronized (CAMERA_LOCK) {
                        try {
/* 562 */                   if (this.mCamera != null) {
                                try {
/* 566 */                           Camera.Parameters parameters = this.mCamera.getParameters();
/* 567 */                           parameters.setPreviewFpsRange(frameRate * 1000, frameRate * 1000);
/* 568 */                           SLog.m446d("SET frameRate using fixed-range frameRate");
/* 569 */                           this.mCamera.setParameters(parameters);
                                    return frameRate;
                                } catch (Exception e) {
                                    try {
/* 572 */                               Camera.Parameters parameters2 = this.mCamera.getParameters();
/* 573 */                               parameters2.setPreviewFrameRate(frameRate);
/* 574 */                               this.mCamera.setParameters(parameters2);
/* 575 */                               SLog.m446d("SET frameRate using fixed frameRate");
                                        return frameRate;
                                    } catch (Exception e2) {
                                        try {
/* 581 */                                   Camera.Parameters parameters3 = this.mCamera.getParameters();
                                            try {
/* 586 */                                       List<int[]> ss = parameters3.getSupportedPreviewFpsRange();
/* 587 */                                       if (ss == null) {
/* 588 */                                           CrashUtil.log("Cannot set frame-rate");
                                                    return frameRate;
                                                }
/* 591 */                                       int frameRate2 = frameRate * 1000;
/* 592 */                                       int[] selected = null;
/* 593 */                                       int lowest = 0;
                                                int highest = 0;
                                                int reachedIndex = -1;
                                                int otherIndex = ss.size() - 1;
                                                for (int i = 0; i < ss.size(); i++) {
/* 595 */                                           int[] s = ss.get(i);
/* 596 */                                           SLog.m447d("Supported FPS: {}", s);
/* 597 */                                           if (s[0] == frameRate2) {
/* 598 */                                               selected = s;
                                                        break;
                                                    }
/* 601 */                                           if (s[1] == frameRate2) {
/* 602 */                                               if (lowest < s[0]) {
/* 603 */                                                   lowest = s[0];
/* 604 */                                                   reachedIndex = i;
                                                        }
/* 606 */                                               if (highest <= s[1]) {
/* 607 */                                                   highest = s[1];
                                                        }
                                                    } else if (lowest <= s[0]) {
/* 611 */                                               lowest = s[0];
/* 612 */                                               if (highest <= s[1]) {
/* 613 */                                                   highest = s[1];
/* 614 */                                                   otherIndex = i;
                                                        }
                                                    }
                                                }
/* 620 */                                       if (selected == null) {
/* 621 */                                           if (reachedIndex != -1) {
/* 622 */                                               int[] selected2 = ss.get(reachedIndex);
                                                        selected = selected2;
                                                    } else {
/* 624 */                                               int[] selected3 = ss.get(otherIndex);
                                                        selected = selected3;
                                                    }
                                                }
/* 628 */                                       if (selected[1] >= frameRate2) {
                                                    try {
/* 630 */                                               SLog.m448d("Try Using variable FPS: {}, {}.", Integer.valueOf(lowest), Integer.valueOf(frameRate2));
/* 631 */                                               parameters3.setPreviewFpsRange(lowest, frameRate2);
/* 632 */                                               this.mCamera.setParameters(parameters3);
/* 633 */                                               return frameRate2 / 1000;
                                                    } catch (Exception e3) {
                                                        try {
/* 636 */                                                   Camera.Parameters parameters4 = this.mCamera.getParameters();
/* 637 */                                                   SLog.m447d("Try Using fixed framerate FPS: {}", Integer.valueOf(frameRate2));
/* 638 */                                                   parameters4.setPreviewFrameRate(frameRate2 / 1000);
/* 639 */                                                   this.mCamera.setParameters(parameters4);
/* 640 */                                                   return frameRate2 / 1000;
                                                        } catch (Exception e4) {
/* 642 */                                                   Camera.Parameters parameters5 = this.mCamera.getParameters();
/* 643 */                                                   SLog.m449d("Try Using selected framerate FPS: {}, {}, {}.", Integer.valueOf(selected[0]), Integer.valueOf(selected[1]), Integer.valueOf(frameRate2));
/* 644 */                                                   parameters5.setPreviewFpsRange(selected[0], selected[1]);
/* 645 */                                                   this.mCamera.setParameters(parameters5);
/* 646 */                                                   return selected[1] / 1000;
                                                        }
                                                    }
                                                }
/* 650 */                                       SLog.m446d("Fall back, should never reach here.");
/* 651 */                                       int frameRate3 = frameRate2 / 1000;
/* 652 */                                       parameters3.setPreviewFrameRate(frameRate3);
/* 653 */                                       this.mCamera.setParameters(parameters3);
                                                return frameRate3;
                                            } catch (Exception e5) {
/* 659 */                                       return 30;
                                            }
                                        } catch (Exception e6) {
/* 584 */                                   return 30;
                                        }
                                    }
                                }
                            }
/* 663 */                   return 30;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 666 */       protected int fixOrientation(int rotation) {
                    int fixedDegrees;
                    synchronized (CAMERA_LOCK) {
/* 668 */               fixedDegrees = 0;
                        try {
/* 669 */                   if (this.mCamera != null) {
/* 670 */                       fixedDegrees = getCameraDisplayOrientation(rotation, this.mCurrentCamera);
/* 671 */                       this.mCamera.setDisplayOrientation(fixedDegrees);
/* 672 */                       SLog.m447d("Fixed orientation: {}", Integer.valueOf(fixedDegrees));
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return fixedDegrees;
                }

/* 678 */       protected void prepareFocusMatrix(int width, int height, int fixedDegrees) {
/* 679 */           if (width != 0 && height != 0) {
/* 680 */               Matrix matrix = new Matrix();
/* 681 */               MathUtil.prepareMatrix(matrix, this.mIsCurrentFront, fixedDegrees, width, height);
/* 686 */               matrix.invert(this.mMatrix);
                    }
                }

                /* JADX WARN: Code duplicated, block: B:13:0x001f  */
                /* JADX WARN: Code duplicated, block: B:7:0x000b A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:5:0x0005, B:11:0x001d, B:7:0x000b, B:10:0x0015), top: B:17:0x0005 }] */
                /* JADX WARN: Code duplicated, block: B:9:0x0013  */
/* 690 */       public void changeFlashState(boolean enabled) {
                    String str;
                    synchronized (CAMERA_LOCK) {
/* 692 */               if (!enabled) {
/* 693 */                   Camera.Parameters parameters = this.mCamera.getParameters();
/* 694 */                   if (enabled) {
                                str = "torch";
                            } else {
/* 694 */                       str = "off";
                            }
                            parameters.setFlashMode(str);
/* 695 */                   this.mCamera.setParameters(parameters);
                        } else {
                            try {
                                if (canSwitchFlash()) {
/* 693 */                           Camera.Parameters parameters2 = this.mCamera.getParameters();
/* 694 */                           if (enabled) {
                                        str = "torch";
                                    } else {
/* 694 */                               str = "off";
                                    }
                                    parameters2.setFlashMode(str);
/* 695 */                           this.mCamera.setParameters(parameters2);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 700 */       public static synchronized int getCameraDisplayOrientation(int degrees, int cameraId) {
                    int result;
                    try {
                        Camera.CameraInfo info = sCameraInfoTable.get(cameraId);
/* 702 */               if (info == null) {
/* 703 */                   info = new Camera.CameraInfo();
/* 704 */                   Camera.getCameraInfo(cameraId, info);
/* 705 */                   sCameraInfoTable.put(cameraId, info);
                        } else {
/* 707 */                   SLog.m464i("Using cached camera info.");
                        }
/* 710 */               SLog.m447d("Set rotation to {} degrees", Integer.valueOf(degrees));
/* 713 */               if (info.facing == 1) {
/* 714 */                   int result2 = (info.orientation + degrees) % 360;
/* 715 */                   result = (360 - result2) % 360;
                        } else {
/* 717 */                   result = ((info.orientation - degrees) + 360) % 360;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return result;
                }

/* 723 */       public int modifyZoom(boolean zoomIn) {
                    int newZoom;
/* 724 */           if (this.mIsSmoothZoomSupported) {
/* 725 */               newZoom = zoomIn ? this.mMaxZoom : 0;
                    } else {
/* 727 */               newZoom = zoomIn ? this.mRequestedZoom + 1 : this.mRequestedZoom - 1;
                    }
/* 729 */           if (RecordConfigUtils.RecordConfig.IS_ZERO_INDEX_BASED_ZOOM) {
/* 730 */               newZoom++;
                    }
/* 732 */           return modifyZoom(newZoom);
                }

/* 735 */       public void startSmoothZoom(boolean zoomIn) {
/* 736 */           if (!this.mIsSmoothZoomSupported) {
/* 737 */               throw new IllegalStateException("Smooth zoom not supported.");
                    }
/* 739 */           if (!this.mIsSmoothZooming) {
/* 740 */               SLog.m447d("Smooth zoom started on value {}", Integer.valueOf(this.mRequestedZoom));
/* 741 */               stopContinuousFocus(this.mCamera.getParameters());
/* 742 */               this.mIsSmoothZooming = true;
/* 743 */               this.mCurrentZoomTarget = this.mRequestedZoom;
/* 744 */               if (zoomIn) {
/* 745 */                   this.mCamera.startSmoothZoom(this.mMaxZoom);
                        } else {
/* 747 */                   this.mCamera.startSmoothZoom(0);
                        }
                    }
                }

/* 752 */       public void startInstantZoom() {
/* 753 */           SLog.m447d("Instant zoom started on value {}", Integer.valueOf(this.mRequestedZoom));
/* 754 */           this.mCurrentZoomTarget = this.mRequestedZoom;
/* 755 */           Camera.Parameters params = this.mCamera.getParameters();
/* 756 */           params.setZoom(this.mRequestedZoom);
/* 757 */           this.mCamera.setParameters(params);
/* 758 */           onZoomChange(this.mRequestedZoom, true, this.mCamera);
                }

/* 761 */       public int getMaxZoom() {
/* 762 */           return this.mMaxZoom;
                }

                @Override // android.hardware.Camera.OnZoomChangeListener
/* 766 */       public void onZoomChange(int zoomValue, boolean stopped, Camera camera) {
/* 767 */           if (stopped) {
/* 768 */               SLog.m449d("Zoom stopped on value {} (target {}, queued value: {}).", Integer.valueOf(zoomValue), Integer.valueOf(this.mCurrentZoomTarget), Integer.valueOf(this.mRequestedZoom));
/* 769 */               this.mIsSmoothZooming = false;
/* 770 */               if (this.mCamera != null) {
                            try {
/* 772 */                       startContinuousAutoFocus(this.mCamera.getParameters());
                            } catch (RuntimeException e) {
                            }
                        }
/* 777 */               if (this.mIsSmoothZoomingStopCalled) {
/* 778 */                   this.mIsSmoothZoomingStopCalled = false;
/* 779 */                   if (this.mRequestedZoom == this.mCurrentZoomTarget) {
/* 780 */                       this.mRequestedZoom = zoomValue;
                            }
                        }
/* 783 */               if (zoomValue != this.mRequestedZoom && this.mIsSmoothZoomSupported && this.mCamera != null) {
/* 784 */                   startSmoothZoom(this.mRequestedZoom >= zoomValue);
                        }
                    } else {
/* 787 */               SLog.m448d("Zoom updated on value {} (queued value: {}).", Integer.valueOf(zoomValue), Integer.valueOf(this.mRequestedZoom));
/* 788 */               if (this.mCurrentZoomTarget == zoomValue) {
/* 789 */                   SLog.m446d("Stop zoom on reaching target.");
/* 790 */                   this.mAutoStopZoomCalled = stopSmoothZoom();
                        }
                    }
/* 794 */           MasterPreviewCallback.getInstance().onZoomUpdated(RecordConfigUtils.RecordConfig.IS_ZERO_INDEX_BASED_ZOOM ? zoomValue + 1 : zoomValue, stopped && zoomValue == this.mRequestedZoom);
/* 797 */           if (stopped) {
/* 798 */               this.mAutoStopZoomCalled = false;
                    }
                }

/* 802 */       public boolean stopSmoothZoom() {
/* 803 */           if (this.mIsSmoothZooming && !this.mIsSmoothZoomingStopCalled) {
/* 804 */               this.mIsSmoothZoomingStopCalled = true;
/* 805 */               SLog.m446d("Stop smooth zoom.");
                        try {
/* 807 */                   this.mCamera.stopSmoothZoom();
                            return true;
                        } catch (Exception e) {
/* 809 */                   CrashUtil.log("Failed to stop smooth zoom.");
                            return true;
                        }
                    }
/* 813 */           return false;
                }

/* 816 */       public int modifyZoom(int zoom) {
                    synchronized (CAMERA_LOCK) {
                        try {
/* 818 */                   if (this.mCamera != null) {
/* 819 */                       int min = 0;
/* 820 */                       if (RecordConfigUtils.RecordConfig.IS_ZERO_INDEX_BASED_ZOOM) {
/* 821 */                           zoom--;
/* 822 */                           min = -1;
                                }
/* 824 */                       if (zoom != this.mRequestedZoom && zoom <= this.mMaxZoom && zoom > min) {
/* 826 */                           boolean zoomIn = zoom > this.mRequestedZoom;
/* 827 */                           this.mRequestedZoom = zoom;
                                    try {
/* 829 */                               SLog.m448d("Modifying waiting to zoom to {} using smooth? {}.", Integer.valueOf(this.mRequestedZoom), Boolean.valueOf(this.mIsSmoothZoomSupported));
/* 830 */                               if (this.mIsSmoothZoomSupported) {
/* 831 */                                   startSmoothZoom(zoomIn);
                                        } else {
/* 833 */                                   startInstantZoom();
                                        }
                                    } catch (Exception e) {
/* 836 */                               CrashUtil.logException(e, "It just didn't work.", new Object[0]);
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 842 */           return this.mRequestedZoom;
                }

/* 845 */       public void modifyExposure(boolean up) {
                    synchronized (CAMERA_LOCK) {
                        try {
/* 847 */                   if (this.mCamera != null) {
                                try {
/* 849 */                           Camera.Parameters params = this.mCamera.getParameters();
/* 850 */                           int current = params.getExposureCompensation();
/* 851 */                           int max = params.getMaxExposureCompensation();
/* 852 */                           int min = params.getMinExposureCompensation();
/* 853 */                           double step = ((double) (max - min)) / 10.0d;
/* 855 */                           if (up) {
/* 856 */                               int val = (int) Math.floor(((double) current) + step);
/* 857 */                               if (val < max) {
                                            max = val;
                                        }
                                        params.setExposureCompensation(max);
                                    } else {
/* 859 */                               int val2 = (int) Math.floor(((double) current) - step);
/* 860 */                               if (val2 > min) {
                                            min = val2;
                                        }
                                        params.setExposureCompensation(min);
                                    }
/* 862 */                           this.mCamera.setParameters(params);
                                } catch (Exception e) {
/* 864 */                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 870 */       private String getNextParamValue(boolean up, List<String> supported, String current) {
                    int i;
/* 871 */           if (supported != null) {
/* 872 */               int i2 = 0;
/* 873 */               while (i2 < supported.size() && !supported.get(i2).equals(current)) {
/* 873 */                   i2++;
                        }
/* 878 */               if (up) {
/* 879 */                   i = i2 + 1;
/* 880 */                   if (i >= supported.size()) {
                                i = supported.size() - 1;
                            }
                        } else {
/* 882 */                   i = i2 - 1;
/* 883 */                   if (i < 0) {
                                i = 0;
                            }
                        }
/* 885 */               SLog.m447d("Modify to: {}", supported.get(i));
/* 886 */               return supported.get(i);
                    }
/* 888 */           return null;
                }

/* 891 */       public void modifyWhiteBalance(boolean up) {
                    synchronized (CAMERA_LOCK) {
                        try {
/* 893 */                   if (this.mCamera != null) {
                                try {
/* 895 */                           Camera.Parameters params = this.mCamera.getParameters();
/* 896 */                           String next = getNextParamValue(up, params.getSupportedWhiteBalance(), params.getWhiteBalance());
/* 897 */                           if (next != null) {
/* 898 */                               params.setWhiteBalance(next);
/* 899 */                               this.mCamera.setParameters(params);
                                    }
                                } catch (Exception e) {
/* 902 */                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 908 */       public void modifySceneMode(boolean up) {
                    synchronized (CAMERA_LOCK) {
                        try {
/* 910 */                   if (this.mCamera != null) {
                                try {
/* 912 */                           Camera.Parameters params = this.mCamera.getParameters();
/* 913 */                           String next = getNextParamValue(up, params.getSupportedSceneModes(), params.getSceneMode());
/* 914 */                           if (next != null) {
/* 915 */                               params.setSceneMode(next);
/* 916 */                               this.mCamera.setParameters(params);
                                    }
                                } catch (Exception e) {
/* 919 */                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 925 */       public void modifyColorEffects(boolean up) {
                    synchronized (CAMERA_LOCK) {
                        try {
/* 927 */                   if (this.mCamera != null) {
                                try {
/* 929 */                           Camera.Parameters params = this.mCamera.getParameters();
/* 930 */                           String next = getNextParamValue(up, params.getSupportedColorEffects(), params.getColorEffect());
/* 931 */                           if (next != null) {
/* 932 */                               params.setColorEffect(next);
/* 933 */                               this.mCamera.setParameters(params);
                                    }
/* 935 */                           this.mCamera.setParameters(params);
                                } catch (Exception e) {
/* 937 */                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 943 */       public void modifyAntiBanding(boolean up) {
                    synchronized (CAMERA_LOCK) {
                        try {
/* 945 */                   if (this.mCamera != null) {
                                try {
/* 947 */                           Camera.Parameters params = this.mCamera.getParameters();
/* 948 */                           String next = getNextParamValue(up, params.getSupportedAntibanding(), params.getAntibanding());
/* 949 */                           if (next != null) {
/* 950 */                               params.setAntibanding(next);
/* 951 */                               this.mCamera.setParameters(params);
                                    }
/* 953 */                           this.mCamera.setParameters(params);
                                } catch (Exception e) {
/* 955 */                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @TargetApi(15)
/* 962 */       public void switchImageStabilization() {
                    synchronized (CAMERA_LOCK) {
                        try {
/* 964 */                   if (Build.VERSION.SDK_INT >= 15 && this.mCamera != null) {
                                try {
/* 966 */                           Camera.Parameters params = this.mCamera.getParameters();
/* 967 */                           if (params.isVideoStabilizationSupported()) {
/* 968 */                               params.setVideoStabilization(!params.getVideoStabilization());
                                    }
/* 970 */                           this.mCamera.setParameters(params);
                                } catch (Exception e) {
/* 972 */                           SLog.m446d("It just didn't work.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 980 */       public void releaseCamera() {
                    try {
/* 981 */               this.mCamera.release();
                    } catch (RuntimeException e) {
/* 983 */               CrashUtil.log("Failed to release camear, probably already released: " + e.getMessage());
                    }
/* 985 */           this.mCamera = null;
/* 986 */           this.mCameraSetting = null;
/* 987 */           this.mCurrentCamera = -1;
                }
            }
