            package co.vine.android.recorder;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.media.AudioRecord;
            import android.os.Build;
            import android.os.Environment;
            import android.provider.MediaStore;
            import co.vine.android.api.VineVideoUrlTier;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioArrays;
            import co.vine.android.recorder.camera.CameraManager;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacv.cpp.avcodec;
            import com.googlecode.javacv.cpp.avutil;
            import com.lifevibes.mediacoder.LVMediaCoderConfiguration;
            import com.lifevibes.mediacoder.LVMediaCoderProperties;
            import java.io.BufferedInputStream;
            import java.io.BufferedOutputStream;
            import java.io.DataInputStream;
            import java.io.DataOutputStream;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.text.SimpleDateFormat;
            import org.apache.commons.io.FileUtils;
            import org.json.JSONObject;
            
            
    public class RecordConfigUtils {
                public static final int AUDIO_BIT_RATE;
                public static final int AUDIO_BUFFER_SIZE;
                public static final int AUDIO_FORMAT = 16;
                public static final int AUDIO_RUNNABLE_SAMPLE_SIZE;
                public static final int AUDIO_SAMPLE_PER_MS_MAX = 48;
                public static final int AUDIO_SAMPLE_RATE_HZ = 44100;
                public static final double AUDIO_SAMPLE_RATE_PMS = 44.1d;
                public static final int AUDIO_WAIT_THRESHOLD_NS;
                private static String DCIM = null;
                public static final Bitmap.Config DEFAULT_BITMAP_CONFIG;
                public static final int DEFAULT_TARGET_SIZE = 480;
                public static final File FOLDER_ROOT_DIRECT_UPLOAD;
                public static final String FOLDER_ROOT_PROCESS = "process";
                private static final File FOLDER_ROOT_PROCESS_DEBUG;
                private static final String FOLDER_ROOT_SAVE;
                public static final String FOLDER_ROOT_TO_UPLOAD = "upload";
                public static final int HIGHEST_PROFILE = 4;
                public static final int IMPORT_VIDEO_BIT_RATE = 6000000;
                public static final int MAX_DRAFTS = 9;
                public static final int MIN_FRAME_COUNT_FOR_TRIM = 2;
                public static final long MIN_IMPORT_DURATION = 200;
                public static final int N_AUDIO_CHANNELS = 1;
                public static final int N_IMAGE_CHANNELS = 4;
                public static final String PREF_CAN_LOAD = "canLoad";
                public static final int PROGRESS_THRESHOLD_MESSAGING = 66;
                public static final int PROGRESS_THRESHOLD_REGULAR = 1000;
                public static final int PROGRESS_TIMER_SLEEP = 18;
                private static final String UPLOAD_IMAGE_EXTENSION = "_image";
                private static final String UPLOAD_METADATA_EXTENSION = "_meta";
                public static final int VIDEO_BIT_RATE;
                public static final String VIDEO_CONTAINER_EXT;
                public static final int VIDEO_FRAME_RATE = 30;
                public static final double VIDEO_KPS = 3.0d;
                private static LVMediaCoderConfiguration sEncoderConfig;
                private static RecordConfig sGenericConfig;
                public static final boolean HW_ENABLED = BuildConfig.ENCODING.toLowerCase().contains("hw");
                public static final boolean SW_ENABLED = BuildConfig.ENCODING.toLowerCase().contains(BuildConfig.ENCODING);

        static  File access$000() {
            return FOLDER_ROOT_PROCESS_DEBUG;
                }

                static {
           VIDEO_BIT_RATE = RecordSessionManager.DEFAULT_VERSION.willEventuallyTranscoded ? 3500000 : avutil.AV_TIME_BASE;
           AUDIO_BIT_RATE = RecordSessionManager.DEFAULT_VERSION.willEventuallyTranscoded ? LVMediaCoderProperties.BITRATE_AUDIO_128K : 64000;
           AUDIO_BUFFER_SIZE = AudioRecord.getMinBufferSize(44100, 16, 2);
           AUDIO_RUNNABLE_SAMPLE_SIZE = AUDIO_BUFFER_SIZE > 0 ? AUDIO_BUFFER_SIZE : 7680;
           AUDIO_WAIT_THRESHOLD_NS = (AUDIO_RUNNABLE_SAMPLE_SIZE * 2000000000) / 44100;
           DEFAULT_BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
           VIDEO_CONTAINER_EXT = RecordSessionManager.DEFAULT_VERSION.videoOutputExtension;
                    try {
               DCIM = Environment.DIRECTORY_DCIM;
                    } catch (Exception e) {
               DCIM = "DCIM";
                    }
           FOLDER_ROOT_SAVE = new File(Environment.getExternalStoragePublicDirectory(DCIM), "Vine").getPath();
           FOLDER_ROOT_PROCESS_DEBUG = new File(Environment.getExternalStoragePublicDirectory(DCIM), "vine_capture");
           FOLDER_ROOT_DIRECT_UPLOAD = new File(Environment.getExternalStoragePublicDirectory(DCIM), "vine_upload");
           if (SLog.sLogsOn) {
               FOLDER_ROOT_DIRECT_UPLOAD.mkdirs();
                    }
                }

        public static RecordConfig getGenericConfig(Context context) {
            if (sGenericConfig == null) {
                sGenericConfig = new RecordConfig(context);
                    }
            return sGenericConfig;
                }

        public static AudioArray createAudioArray(AudioArrays.AudioArrayType type) {
            return AudioArrays.newInstance(AUDIO_RUNNABLE_SAMPLE_SIZE, type);
                }

        public static LVMediaCoderConfiguration getEncoderConfiguration() {
            if (sEncoderConfig == null) {
                sEncoderConfig = new LVMediaCoderConfiguration();
                    }
            return sEncoderConfig;
                }

        public static boolean isFastEncoding(boolean hardwareEncoding) {
                    return hardwareEncoding && Build.VERSION.SDK_INT >= 16;
                }

        public static class RecordConfig {
                    private static final boolean CAMERA_SWITCH_ENABLED = true;
                    public static final boolean DISABLE_FACE_RECOGNITION;
                    private static final boolean FLASH_SWITCH_ENABLED = false;
                    public static final boolean FORCE_AUTO_FOCUS;
                    private static final int INITIAL_VIDEO_BUFFER_COUNT = 170;
                    private static final float INVALID_ASPECT_RATIO = 0.0f;
                    public static final boolean IS_ZERO_INDEX_BASED_ZOOM;
                    private static final String KEY_ASPECT_RATIO_OVERRIDE_bf = "aspect_ratio_or_bf";
                    private static final String KEY_ASPECT_RATIO_OVERRIDE_ff = "aspect_ratio_or_ff";
                    private static final String KEY_BUFFER_SIZE = "bufferSize";
                    private static final String KEY_CAMERA_SWITCH_ENABLED = "cameraSwitchEnabled";
                    private static final String KEY_FLASH_SWITCH_ENABLED = "flashSwitchEnabled";
                    private static final String KEY_FRAME_RATE = "frameRate";
                    private static final String KEY_MAX_DURATION = "maxDuration";
                    private static final String KEY_PREALLOCATE_BUFFER = "preAllocateBuffer";
                    private static final String KEY_PREF = "RecordConfig";
                    private static final String KEY_PREVIEW_HEIGHT = "previewHeight";
                    private static final String KEY_PREVIEW_WIDTH = "previewWidth";
                    private static final String KEY_PRE_RATIO = "pre_ratio";
                    private static final String KEY_PROCESS_ON_SD = "processOnSD";
                    private static final String KEY_RECORDING_ENABLED = "recordingEnabled";
                    private static final String KEY_SET_RECORDING_HINT_bf = "set_recording_hint_bf";
                    private static final String KEY_SET_RECORDING_HINT_ff = "set_recording_hint_ff";
                    private static final String KEY_TARGET_SIZE = "targetSize";
                    private static final String KEY_UPDATE_TIME = "updateTime";
                    private static final String KEY_ZOOM_ENABLED = "zoomEnabled";
                    private static final int PREVIEW_WIDTH = 640;
                    private static final float PRE_ALLOCATE_RATIO = 1.5f;
                    private static final boolean PROCESS_ON_SD_CARD;
                    private static final int PROGRESS_MAX = 6000;
                    private static final boolean SET_RECORDING_HINT_bf = true;
                    private static final boolean SET_RECORDING_HINT_ff = true;
                    public static final boolean SHOULD_DISABLE_BACKFACING_SET_RECORDING_HINT;
                    public static final boolean SHOULD_DISABLE_FRONTFACING_SET_RECORDING_HINT;
                    public static final boolean SHOULD_FORCE_SMOOTH_ZOOM;
                    public static final boolean SHOULD_OVERRIDED_FRONTFACING_ASPECT_RATIO = false;
                    public static final boolean SHOULD_OVERRIDE_BACKFACING_ASPECT_RATIO = false;
                    public static final boolean SHOULD_SHOW_ZOOM_SLIDER;
                    public static final boolean SHOULD_USE_MEDIAMETADATAEXTRACTOR;
                    private static final float SIX_SECONDS = 6000.0f;
                    private static final int TARGET_FRAME_RATE = 30;
                    private static final boolean ZOOM_ENABLED = true;
                    public final float backFacingAspectRatioOverride;
                    public final boolean backFacingRecordingHint;
                    public final int bufferCount;
                    public final boolean cameraSwitchEnabled;
                    public final boolean flashSwitchEnabled;
                    public final float frontFacingAspectRatioOverride;
                    public final boolean frontFacingRecordingHint;
                    public final boolean highQuality;
                    public final boolean isZoomButtonEnabled;
                    public final int maxDuration;
                    public final double memRatio;
                    public final double preAllocRatio;
                    public final boolean preAllocateBuffer;
                    public final int previewHeight;
                    public final int previewWidth;
                    public final File processDir;
                    public final boolean processOnSd;
                    public final boolean recordingEnabled;
                    public final int targetFrameRate;
                    public final int targetSize;
                    public final boolean zoomEnabled;

                    static {
                SHOULD_FORCE_SMOOTH_ZOOM = Build.MODEL.length() == "SM-C115".length() && Build.MODEL.contains("SM-C11");
                DISABLE_FACE_RECOGNITION = Build.MODEL.length() == "SM-C115".length() && Build.MODEL.contains("SM-C11");
                SHOULD_DISABLE_FRONTFACING_SET_RECORDING_HINT = Build.MODEL.contains("Nexus 5") || Build.MODEL.contains("SM-N900");
                SHOULD_DISABLE_BACKFACING_SET_RECORDING_HINT = Build.MODEL.contains("SGH-I747") || Build.MODEL.contains("SGH-T999") || Build.MODEL.contains("SGH-N064") || Build.MODEL.contains("SC-06D") || Build.MODEL.contains("SCH-J021") || Build.MODEL.contains("SCH-R530") || Build.MODEL.contains("SCH-I535") || Build.MODEL.contains("SCL21") || Build.MODEL.contains("SCH-S960L");
               SHOULD_SHOW_ZOOM_SLIDER = Build.MODEL.startsWith("SM-C11");
               IS_ZERO_INDEX_BASED_ZOOM = Build.MODEL.length() == "SM-C115".length() && Build.MODEL.contains("SM-C11");
               FORCE_AUTO_FOCUS = Build.MODEL.contains("GT-I9505") || Build.MODEL.contains("GT-I9506") || Build.MODEL.contains("GT-I9500") || Build.MODEL.contains("SGH-I337") || Build.MODEL.contains("SGH-M919") || Build.MODEL.contains("SCH-I545") || Build.MODEL.contains("SPH-L720") || Build.MODEL.contains("GT-I9508") || Build.MODEL.contains("SHV-E300") || Build.MODEL.contains("SCH-R970") || Build.MODEL.contains("SM-N900") || Build.MODEL.contains("LG-D801");
               SHOULD_USE_MEDIAMETADATAEXTRACTOR = Build.VERSION.SDK_INT < 17;
               PROCESS_ON_SD_CARD = SLog.sLogsOn;
                    }

           public RecordConfig(Context context) {
               this.memRatio = SystemUtil.getMemoryRatio(context, true);
               boolean defaultRecordingEnabled = this.memRatio >= 1.0d;
               this.highQuality = true;
               SharedPreferences sp = context.getSharedPreferences(KEY_PREF, 0);
               this.preAllocRatio = this.memRatio >= 2.0d ? sp.getFloat(KEY_PRE_RATIO, PRE_ALLOCATE_RATIO) : 0.8d;
               this.preAllocateBuffer = sp.getBoolean(KEY_PREALLOCATE_BUFFER, true);
               this.recordingEnabled = sp.getBoolean(KEY_RECORDING_ENABLED, defaultRecordingEnabled);
               this.zoomEnabled = sp.getBoolean(KEY_ZOOM_ENABLED, true);
               this.processOnSd = (PROCESS_ON_SD_CARD || sp.getBoolean(KEY_PROCESS_ON_SD, PROCESS_ON_SD_CARD)) && "mounted".equals(Environment.getExternalStorageState());
               this.processDir = this.processOnSd ? RecordConfigUtils.access$000() : context.getFilesDir();
               this.cameraSwitchEnabled = sp.getBoolean(KEY_CAMERA_SWITCH_ENABLED, true) && CameraManager.hasFrontFacingCamera() && CameraManager.hasBackFacingCamera();
               this.flashSwitchEnabled = sp.getBoolean(KEY_FLASH_SWITCH_ENABLED, false);
               this.targetFrameRate = sp.getInt(KEY_FRAME_RATE, 30);
               this.targetSize = sp.getInt(KEY_TARGET_SIZE, 480);
                        this.previewWidth = sp.getInt(KEY_PREVIEW_WIDTH, 640) / (this.highQuality ? 1 : 2);
                        this.previewHeight = sp.getInt(KEY_PREVIEW_HEIGHT, 480) / (this.highQuality ? 1 : 2);
               this.maxDuration = sp.getInt(KEY_MAX_DURATION, 6000);
               int initialBufferCount = (int) (sp.getInt(KEY_BUFFER_SIZE, 170) * (this.maxDuration / SIX_SECONDS));
               this.bufferCount = this.memRatio <= 1.0d ? (int) (((double) initialBufferCount) * 0.8d) : initialBufferCount;
               this.frontFacingAspectRatioOverride = sp.getFloat(KEY_ASPECT_RATIO_OVERRIDE_ff, 0.0f);
               this.backFacingAspectRatioOverride = sp.getFloat(KEY_ASPECT_RATIO_OVERRIDE_bf, 0.0f);
               this.frontFacingRecordingHint = SHOULD_DISABLE_FRONTFACING_SET_RECORDING_HINT ? false : sp.getBoolean(KEY_SET_RECORDING_HINT_ff, true);
               this.backFacingRecordingHint = SHOULD_DISABLE_BACKFACING_SET_RECORDING_HINT ? false : sp.getBoolean(KEY_SET_RECORDING_HINT_bf, true);
               this.isZoomButtonEnabled = SHOULD_SHOW_ZOOM_SLIDER && this.zoomEnabled;
                    }

           public static boolean needsUpdate(Context context) {
                        return System.currentTimeMillis() - context.getSharedPreferences(KEY_PREF, 0).getLong(KEY_UPDATE_TIME, 0L) > 300000;
                    }

           public static synchronized RecordConfig update(Context context, JSONObject config) {
                        try {
                            SLog.m447d("Update config: {}.", config);
                   RecordConfig oldConfig = new RecordConfig(context);
                   SharedPreferences.Editor editor = context.getSharedPreferences(KEY_PREF, 0).edit();
                   editor.putBoolean(KEY_PREALLOCATE_BUFFER, config.optBoolean(KEY_PREALLOCATE_BUFFER, oldConfig.preAllocateBuffer));
                   editor.putBoolean(KEY_RECORDING_ENABLED, config.optBoolean(KEY_RECORDING_ENABLED, oldConfig.recordingEnabled));
                   editor.putBoolean(KEY_ZOOM_ENABLED, config.optBoolean(KEY_ZOOM_ENABLED, oldConfig.zoomEnabled));
                   editor.putBoolean(KEY_CAMERA_SWITCH_ENABLED, config.optBoolean(KEY_CAMERA_SWITCH_ENABLED, oldConfig.cameraSwitchEnabled));
                   editor.putBoolean(KEY_FLASH_SWITCH_ENABLED, config.optBoolean(KEY_FLASH_SWITCH_ENABLED, oldConfig.flashSwitchEnabled));
                   editor.putBoolean(KEY_PROCESS_ON_SD, config.optBoolean(KEY_PROCESS_ON_SD, oldConfig.processOnSd));
                   editor.putBoolean(KEY_SET_RECORDING_HINT_ff, config.optBoolean(KEY_SET_RECORDING_HINT_ff, oldConfig.frontFacingRecordingHint));
                   editor.putBoolean(KEY_SET_RECORDING_HINT_bf, config.optBoolean(KEY_SET_RECORDING_HINT_bf, oldConfig.backFacingRecordingHint));
                   editor.putInt(KEY_FRAME_RATE, config.optInt(KEY_FRAME_RATE, oldConfig.targetFrameRate));
                   editor.putInt(KEY_TARGET_SIZE, config.optInt(KEY_TARGET_SIZE, oldConfig.targetSize));
                   editor.putInt(KEY_PREVIEW_WIDTH, config.optInt(KEY_PREVIEW_WIDTH, oldConfig.previewWidth));
                   editor.putInt(KEY_PREVIEW_HEIGHT, config.optInt(KEY_PREVIEW_HEIGHT, oldConfig.previewHeight));
                   editor.putInt(KEY_BUFFER_SIZE, config.optInt(KEY_BUFFER_SIZE, oldConfig.bufferCount));
                   editor.putInt(KEY_MAX_DURATION, config.optInt(KEY_MAX_DURATION, oldConfig.maxDuration));
                   editor.putFloat(KEY_PRE_RATIO, (float) config.optDouble(KEY_PRE_RATIO, oldConfig.preAllocRatio));
                   editor.putFloat(KEY_ASPECT_RATIO_OVERRIDE_ff, (float) config.optDouble(KEY_ASPECT_RATIO_OVERRIDE_ff, oldConfig.frontFacingAspectRatioOverride));
                   editor.putFloat(KEY_ASPECT_RATIO_OVERRIDE_bf, (float) config.optDouble(KEY_ASPECT_RATIO_OVERRIDE_bf, oldConfig.backFacingAspectRatioOverride));
                   editor.putLong(KEY_UPDATE_TIME, System.currentTimeMillis());
                   editor.apply();
                        } catch (Throwable th) {
                            throw th;
                        }
               return new RecordConfig(context);
                    }
                }

       public static int getMaxAudioBufferSize(RecordConfig config) {
           return getMaxAudioBufferSize(config.maxDuration);
                }

       public static int getMaxAudioBufferSize(int duration) {
           return (int) (((double) (duration * 48)) * 1.1d);
                }

       public static int getTimeStampInMsFromSampleCounted(int currentCount) {
           return (int) (((double) currentCount) / 44.1d);
                }

       public static int getVideoBufferMax(int duration) {
           return (int) (((((double) VIDEO_BIT_RATE) * 1.1d) * ((double) duration)) / 1000.0d);
                }

       public static int getVideoBufferMax(RecordConfig config) {
           return getVideoBufferMax(config.maxDuration);
                }

       public static int getGopFromKps(double kps, int fps) {
           return (int) (((double) fps) / kps);
                }

       public static SwVineFrameRecorder newVideoRecorder(String output, int frameRate, int targetSize, avcodec.AVPacket videoPacket, avcodec.AVPacket audioPacket, boolean useMp4) {
           SwVineFrameRecorder recorder = new SwVineFrameRecorder(output, targetSize, targetSize, 1, videoPacket, audioPacket);
           recorder.setAudioCodecName("libvorbis");
           recorder.setVideoCodecName("libvpx");
           recorder.setFormat(VineVideoUrlTier.FORMAT_WEBM);
           recorder.setSampleRate(44100);
           recorder.setFps(frameRate);
           return recorder;
                }

       public static HwVineFrameRecorder newVideoRecorder(Context context, String output, int frameRate) {
           return new HwVineFrameRecorder(context, output, frameRate);
                }

       public static SwVineFrameRecorder newVideoRecorder(String output, int frameRate, int targetSize, boolean useMp4) {
           return newVideoRecorder(output, frameRate, targetSize, null, null, useMp4);
                }

       public static void deletePreProcess(File root) {
           deleteNonFolders(new File(root, FOLDER_ROOT_PROCESS));
                }

       public static void deleteNonFolders(File f) {
           if (f.isDirectory()) {
               File[] arr$ = f.listFiles();
                        for (File c : arr$) {
                   deleteNonFolders(c);
                        }
                    }
                }

       public static boolean isDefaultFrontFacing() {
                    return !CameraManager.hasBackFacingCamera();
                }

       public static boolean copySilently(File from, File to) {
                    try {
               FileUtils.copyFile(from, to);
               return true;
                    } catch (Exception e) {
               SLog.m459e("Failed to copy", (Throwable) e);
               return false;
                    }
                }

       public static boolean loadWasEverSuccessful(Context context) {
                    return true;
                }

       public static void setLoadWasEverSuccessful(Context context, boolean canLoad) {
           if (context != null) {
               context.getSharedPreferences(SwVineFrameRecorder.class.getName(), 0).edit().putBoolean(PREF_CAN_LOAD, canLoad).commit();
                    }
                }

       public static String getThumbnailPath(String path) {
           return path + UPLOAD_IMAGE_EXTENSION;
                }

       public static String getMetadataPath(String path) {
           return path + UPLOAD_METADATA_EXTENSION;
                }

       public static String getFrameInfoPath(String path) {
           return path + ".info";
                }

       public static File copyForUpload(File dir, String path, String fileName) throws IOException {
           File actualFile = new File(path);
           File finalName = getUploadFile(dir, fileName);
           FileUtils.copyFile(actualFile, finalName);
           return finalName;
                }

       public static String readForUpload(File finalName) throws IOException {
           FileInputStream fileStream = new FileInputStream(finalName);
           DataInputStream stream = new DataInputStream(new BufferedInputStream(fileStream));
                    try {
               int size = stream.readInt();
               byte[] data = new byte[size];
               int r = stream.read(data);
               SLog.m465i("Data read, is there more? {}.", Boolean.valueOf(r != -1));
               return new String(data);
                    } catch (IOException e) {
               SLog.m456e("Failed to read data: {}.", finalName);
               throw e;
                    }
                }

       public static File writeForUpload(File dir, String fileName, String data) throws IOException {
           File finalName = getUploadFile(dir, fileName);
                    FileOutputStream fileStream = new FileOutputStream(finalName);
                    DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(fileStream));
                    try {
                        byte[] bytes = data.getBytes();
                        stream.writeInt(bytes.length);
                        stream.write(bytes);
                        stream.flush();
                        return finalName;
                    } catch (IOException e) {
                        SLog.m456e("Failed to write data: {}.", finalName);
                        throw e;
                    } finally {
                        stream.close();
                    }
                }

       private static synchronized File getUploadFile(File root, String fileName) {
                    File mediaStorageDir;
                    try {
                        mediaStorageDir = new File(root, "upload");
               if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                   SLog.m447d("Failed to make dirs: {},", fileName);
                   throw new RuntimeException("Failed to make directories. You will be doomed.");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
           return new File(mediaStorageDir, fileName);
                }

       public static synchronized File getCameraRollFile(Context context, long startTime, String extension) {
                    File file;
                    File mediaStorageDir = null;
                    try {
               if ("mounted".equals(Environment.getExternalStorageState())) {
                   mediaStorageDir = new File(FOLDER_ROOT_SAVE);
                        } else {
                            try {
                       String[] projection = {"_id", "_data", "datetaken"};
                       Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.INTERNAL_CONTENT_URI, projection, null, null, "datetaken DESC");
                       if (cursor != null && cursor.moveToFirst()) {
                           mediaStorageDir = new File(new File(cursor.getString(cursor.getColumnIndexOrThrow("_data"))).getParentFile(), "Vine");
                                }
                            } catch (Exception e) {
                       SLog.m459e("Failed to get internal path", (Throwable) e);
                            }
                        }
               if (mediaStorageDir == null) {
                   file = null;
                        } else if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                   SLog.m446d("Failed to make dirs.");
                   CrashUtil.logException(new RuntimeException("Failed to make directories. You will be doomed."));
                   file = null;
                        } else {
                   String fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_sss").format(Long.valueOf(startTime));
                   file = new File(mediaStorageDir.getPath() + File.separator + fileName + "." + extension);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return file;
                }

       public static Bitmap createDefaultSizeBitmap() {
           return Bitmap.createBitmap(480, 480, DEFAULT_BITMAP_CONFIG);
                }

       public static synchronized File getPreProcessFile(File dir, String folder, String id, String extension) {
                    File mediaStorageDir;
                    try {
                        mediaStorageDir = new File(new File(dir, FOLDER_ROOT_PROCESS), folder);
               if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                   SLog.m446d("Failed to make dirs.");
                   throw new RuntimeException("Failed to make directories. You will be doomed.");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
           return new File(mediaStorageDir.getPath() + File.separator + id + (extension != null ? "." + extension : ""));
                }

       public static synchronized File getPreProcessFile(RecordConfig config, String folder, String id, String extension) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return getPreProcessFile(config.processDir, folder, id, extension);
                }

       public static boolean isCapableOfRecording(Context context) {
           return true;
                }

       public static boolean isCapableOfInline(Context context) {
                    return SystemUtil.getMemoryRatio(context, true) > 1.2d;
                }
            }
