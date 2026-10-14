            package co.vine.android.util;

            import android.content.ContentUris;
            import android.content.ContentValues;
            import android.content.Context;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.content.pm.PackageManager;
            import android.database.Cursor;
            import android.graphics.Point;
            import android.net.Uri;
            import android.text.TextUtils;
            import co.vine.android.VineLoggingException;
            import co.vine.android.api.PostInfo;
            import co.vine.android.api.VineUpload;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.PhotoImagesCache;
            import co.vine.android.cache.video.VideoCache;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.client.AppController;
            import co.vine.android.client.VineAPI;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.network.FileNetworkEntity;
            import co.vine.android.provider.VineUploadProvider;
            import co.vine.android.provider.VineUploads;
            import co.vine.android.provider.VineUploadsDatabaseSQL;
            import co.vine.android.recorder.HashAsyncTask;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSessionVersion;
            import co.vine.android.recorder.RecordingFile;
            import co.vine.android.service.UploadProgressListener;
            import co.vine.android.service.VineUploadService;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.text.SimpleDateFormat;
            import java.util.Date;
            import java.util.HashMap;
            import java.util.LinkedHashMap;
            import java.util.LinkedList;
            import java.util.Locale;
            import java.util.Map;
            import java.util.UUID;
            
            
    public class UploadManager {
                public static final String BC_INVALIDATE_CACHE = "co.vine.android.invalidateCache";
                public static final long EXPIRE_TIME = 86400000;
                private static final int[] SHARED_LOCK = new int[0];
                public static final int STATUS_FAILED = 2;
                public static final int STATUS_INVALID = -1;
                public static final int STATUS_NEED_POST = 1;
                public static final int STATUS_NEED_UPLOAD = 0;
                private static final String SUB_PREF_HASH = "_hash.sub";
                private static final String SUB_PREF_POST = "_post.sub";
                private static final String SUB_PREF_THUMBNAIL = "_thumbnail_url.sub";
                private static final String SUB_PREF_VIDEO = "_video_url.sub";
                private static final String SUB_UPLOAD_TIME = "_upload_time.sub";
                public static final String TAG = "UploadManager";
                private static SharedPreferences mUploadPref;
                private final MediaUtility mMediaUtility;

        public UploadManager(Context context) {
            this.mMediaUtility = new MediaUtility(context);
                }

        public static void removeFromUploadQueue(Context context, String path) {
            context.startService(VineUploadService.getDiscardIntent(context, path));
                }

        private static String addToUploadQueue(Context context, File dir, RecordingFile file, String thumbnail, String reference, boolean isMessaging, long conversationId) throws Exception {
            String fileName = file.version.name() + "_" + System.currentTimeMillis();
            File videoFile = RecordConfigUtils.copyForUpload(dir, file.getVideoPath(), fileName);
            String path = videoFile.getPath();
            String thumbnail2 = RecordConfigUtils.copyForUpload(dir, thumbnail, RecordConfigUtils.getThumbnailPath(fileName)).getPath();
            RecordConfigUtils.writeForUpload(dir, RecordConfigUtils.getMetadataPath(fileName), file.getSession().getMetaData().toString());
            context.startService(VineUploadService.getUploadIntent(context, path, file.getHash(), thumbnail2, reference, isMessaging, conversationId));
            return path;
                }

        public static RecordSessionVersion getVersionFromPath(String path) {
            RecordSessionVersion version = RecordSessionVersion.SW_MP4;
            String fn = new File(path).getName();
            if (fn.startsWith(RecordSessionVersion.HW.name())) {
                RecordSessionVersion version2 = RecordSessionVersion.HW;
               return version2;
                    }
            if (fn.startsWith(RecordSessionVersion.SW_WEBM.name())) {
               RecordSessionVersion version3 = RecordSessionVersion.SW_WEBM;
               return version3;
                    }
           return version;
                }

       public static String addToUploadQueue(Context context, RecordingFile file, String thumbnail, String reference, boolean isMessaging, long conversationId) throws Exception {
                    File processDir;
                    try {
               processDir = new RecordConfigUtils.RecordConfig(context).processDir;
                    } catch (Exception e) {
               processDir = context.getCacheDir();
                    }
           return addToUploadQueue(context, processDir, file, thumbnail, reference, isMessaging, conversationId);
                }

        
        public static String addGalleryUpload(Context context, String sourcePath, String thumbnail, String reference) throws Exception {
            File processDir;
            try {
                processDir = new RecordConfigUtils.RecordConfig(context).processDir;
            } catch (Exception e) {
                processDir = context.getCacheDir();
            }
            String fileName = RecordSessionVersion.SW_MP4.name() + "_" + System.currentTimeMillis();
            File videoFile = RecordConfigUtils.copyForUpload(processDir, sourcePath, fileName);
            File thumbnailFile = RecordConfigUtils.copyForUpload(processDir, thumbnail, RecordConfigUtils.getThumbnailPath(fileName));
            String hash = HashAsyncTask.getMD5Checksum(videoFile.getPath());
            RecordConfigUtils.writeForUpload(processDir, RecordConfigUtils.getMetadataPath(fileName), "[]");
            addOrUpdateUpload(context, videoFile.getPath(), hash, reference, false, 0L, -1L);
            context.startService(VineUploadService.getUploadIntent(context, videoFile.getPath(), hash, thumbnailFile.getPath(), reference, false, 0L));
            return videoFile.getPath();
        }

       public static Cursor getReferenceCursor(Context context, String reference) {
           if (TextUtils.isEmpty(reference)) {
                        return null;
                    }
           long ownerId = AppController.getInstance(context).getActiveSessionReadOnly().getUserId();
           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_REFERENCE).appendEncodedPath(String.valueOf(ownerId)).appendQueryParameter("reference", reference).build();
           return context.getContentResolver().query(contentUri, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, null, null, null);
                }

       public static void addOrUpdateUpload(Context context, String path, String hash, String reference, boolean isPrivate, long conversationObjectId, long messageRowId) {
           long ownerId = AppController.getInstance(context).getActiveSessionReadOnly().getUserId();
           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_UPLOAD).appendEncodedPath(String.valueOf(ownerId)).appendQueryParameter("path", path).build();
           Cursor c = context.getContentResolver().query(contentUri, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, null, null, null);
           ContentValues values = new ContentValues();
           if (c == null || c.getCount() < 1) {
               String thumbnailPath = RecordConfigUtils.getThumbnailPath(path);
               values.put("path", path);
               values.put("thumbnail_path", thumbnailPath);
               values.put("hash", hash);
               values.put("status", (Integer) 0);
               values.put("is_private", isPrivate ? "1" : "0");
               values.put("reference", reference);
               values.put("owner_id", Long.valueOf(ownerId));
                        if (messageRowId > 0) {
                   values.put(VineUploads.UploadsColumns.MESSAGE_ROW_ID, Long.valueOf(messageRowId));
                        }
                        if (conversationObjectId > 0) {
                   values.put("conversation_row_id", Long.valueOf(conversationObjectId));
                        }
               Uri contentUri2 = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_NEW_UPLOAD).build();
               context.getContentResolver().insert(contentUri2, values);
                    } else {
               String[] selectionArgs = {path};
               if (!TextUtils.isEmpty(hash)) {
                   values.put("hash", hash);
                        }
               values.put("status", (Integer) 0);
               Uri contentUri3 = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_VALUES).build();
               context.getContentResolver().update(contentUri3, values, "path=?", selectionArgs);
                    }
           if (c != null) {
                        c.close();
                    }
                }

       public static boolean uploadListIsEmpty(Context context) {
                    boolean cursorIsEmpty = true;
           Cursor c = context.getContentResolver().query(VineUploads.Uploads.CONTENT_URI, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, null, null, null);
           if (c != null) {
               cursorIsEmpty = c.getCount() < 1;
               c.close();
                    }
           return cursorIsEmpty;
                }

       public static VineUpload getUpload(Context context, String path) {
           long ownerId = AppController.getInstance(context).getActiveSessionReadOnly().getUserId();
           VineUpload ret = null;
           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_UPLOAD).appendEncodedPath(String.valueOf(ownerId)).appendQueryParameter("path", path).build();
           Cursor c = context.getContentResolver().query(contentUri, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, null, null, null);
           if (c != null) {
               if (c.moveToFirst()) {
                   ret = VineUpload.fromCursor(path, c);
                        }
               c.close();
                    }
           return ret;
                }

       public static void deleteUploadRecord(Context context, String path) {
           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_DELETE_UPLOAD).build();
           String[] whereArgs = {path};
           context.getContentResolver().delete(contentUri, "path=?", whereArgs);
                }

       private static Uri.Builder getBaseUriBuilder() {
           return new Uri.Builder().scheme("content").authority(VineUploadProvider.AUTHORITY);
                }

       public static String generateFileName(Context context) throws PackageManager.NameNotFoundException {
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/", Locale.US);
           return formatter.format(new Date()) + UUID.randomUUID() + "_" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
                }

                
                
                
                
                
                
                
                
       public synchronized String[] upload(VineUploadService.ServiceAsyncTask serviceAsyncTask, UploadProgressListener listener, String encoded, String thumbnail, String path, boolean isPrivate, RecordSessionVersion version) throws Exception {
                    String[] strArr;
                    String fileName;
                    String videoUri;
                    String thumbnailUrl;
                    try {
                        File file = new File(encoded);
               if (!file.exists()) {
                   SLog.m456e("Files does not exist: {}", encoded);
                   strArr = null;
                        } else {
                            try {
                       VineUpload upload = getUpload(serviceAsyncTask.getContext(), path);
                       if (upload == null) {
                           throw new Exception("Upload record was null");
                                }
                       String hash = HashAsyncTask.getMD5Checksum(encoded);
                       String expected = upload.hash;
                       if (!hash.equals(expected)) {
                           if (TextUtils.isEmpty(expected)) {
                               SLog.m457e("Invalid hash: actual {}, expected {}.", hash, expected);
                               strArr = null;
                                    } else {
                               CrashUtil.logException(new VineLoggingException("Expected hash was invalid: " + expected));
                               if (serviceAsyncTask.isCancelled()) {
                                   SLog.m446d("Task is already cancelled.");
                                   strArr = null;
                                        } else {
                                   Context context = serviceAsyncTask.getContext();
                                   fileName = generateFileName(context) + version.videoOutputExtension;
                                   videoUri = uploadVideo(listener, file, context, fileName, isPrivate);
                                   if (videoUri == null) {
                                       throw new Exception("Failed to upload video.");
                                            }
                                   if (serviceAsyncTask.isCancelled()) {
                                       SLog.m446d("Task is already cancelled.");
                                       strArr = null;
                                            } else {
                                       thumbnailUrl = uploadThumbnail(listener, new File(thumbnail), serviceAsyncTask.getContext(), fileName);
                                       if (thumbnailUrl == null) {
                                           throw new Exception("Failed to upload thumbnail.");
                                                }
                                       strArr = new String[]{videoUri, thumbnailUrl};
                                            }
                                        }
                                    }
                                } else if (serviceAsyncTask.isCancelled()) {
                           SLog.m446d("Task is already cancelled.");
                           strArr = null;
                                } else {
                           Context context2 = serviceAsyncTask.getContext();
                           fileName = generateFileName(context2) + version.videoOutputExtension;
                           videoUri = uploadVideo(listener, file, context2, fileName, isPrivate);
                           if (videoUri == null) {
                               throw new Exception("Failed to upload video.");
                                    }
                           if (serviceAsyncTask.isCancelled()) {
                               SLog.m446d("Task is already cancelled.");
                               strArr = null;
                                    } else {
                               thumbnailUrl = uploadThumbnail(listener, new File(thumbnail), serviceAsyncTask.getContext(), fileName);
                               if (thumbnailUrl == null) {
                                   throw new Exception("Failed to upload thumbnail.");
                                        }
                               strArr = new String[]{videoUri, thumbnailUrl};
                                    }
                                }
                            } catch (Exception e) {
                       SLog.m458e("Upload failed: {}", (Object) path, (Throwable) e);
                       strArr = null;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return strArr;
                }

       public String uploadVideo(FileNetworkEntity.ProgressListener listener, File file, Context context, String fileName, boolean isPrivate) throws Exception {
           return this.mMediaUtility.getVideoUri(listener, file, context, fileName, isPrivate);
                }

       public String uploadThumbnail(FileNetworkEntity.ProgressListener listener, File file, Context context, String fileName) throws Exception {
           return this.mMediaUtility.getThumbnailUri(listener, fileName, file, context);
                }

       public static LinkedHashMap<String, Integer> getAllPaths(Context context) {
           LinkedHashMap<String, Integer> paths = new LinkedHashMap<>();
           long ownerId = AppController.getInstance(context).getActiveSessionReadOnly().getUserId();
           Uri contentUri = ContentUris.withAppendedId(VineUploads.Uploads.CONTENT_URI, ownerId);
           Cursor c = context.getContentResolver().query(contentUri, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, null, null, "_id ASC");
           if (c != null) {
               while (c.moveToNext()) {
                   String path = c.getString(1);
                   int status = c.getInt(3);
                   paths.put(path, Integer.valueOf(status));
                        }
               c.close();
                    }
           return paths;
                }

       public static LinkedList<String> getPendingPaths(Context context) {
           LinkedList<String> pendingPaths = new LinkedList<>();
           LinkedHashMap<String, Integer> allPaths = getAllPaths(context);
                    for (String path : allPaths.keySet()) {
               int status = allPaths.get(path).intValue();
               if (status != 2 && status != -1) {
                   pendingPaths.add(path);
                        }
                    }
           return pendingPaths;
                }

       public static void setHash(Context context, VineUpload upload, String hash) {
           upload.hash = hash;
           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_HASH).appendQueryParameter("path", upload.path).appendQueryParameter("hash", hash).build();
           context.getContentResolver().update(contentUri, null, null, null);
                }

       public static void setPostInfo(Context context, VineUpload upload, PostInfo info) {
           SLog.m446d("Setting post info for path=" + upload.path + " with caption=" + info.caption + ", twitter=" + info.postToTwitter + ", facebook=" + info.postToFacebook);
           upload.postInfo = info.toString();
           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_POST_INFO).appendQueryParameter("path", upload.path).appendQueryParameter("post_info", info.toString()).build();
           context.getContentResolver().update(contentUri, null, null, null);
                }

       public static void setStatus(Context context, VineUpload upload, int status) {
           setStatus(context, upload, status, null);
                }

       public static void setStatus(Context context, VineUpload upload, int status, String captcha) {
           upload.status = status;
           Uri.Builder contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_STATUS).appendQueryParameter("path", upload.path).appendQueryParameter("status", String.valueOf(status));
           if (!TextUtils.isEmpty(captcha)) {
               contentUri.appendQueryParameter("captcha_url", captcha);
                    }
           context.getContentResolver().update(contentUri.build(), null, null, null);
                }

       public static void setUploadTime(Context context, VineUpload upload) {
           upload.uploadTime = System.currentTimeMillis();
           String currentTime = String.valueOf(System.currentTimeMillis());
           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_UPLOAD_TIME).appendQueryParameter("path", upload.path).appendQueryParameter("upload_time", currentTime).build();
           context.getContentResolver().update(contentUri, null, null, null);
                }

       public static void setUris(Context context, VineUpload upload, String[] uris) {
           upload.setUris(uris);
           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_URIS).appendQueryParameter("path", upload.path).appendQueryParameter("video_url", uris[0]).appendQueryParameter("thumbnail_url", uris[1]).build();
           context.getContentResolver().update(contentUri, null, null, null);
                }

       public static void clearUploadCaptchas(Context context) {
           Uri.Builder uri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_VALUES);
           ContentValues values = new ContentValues();
           values.put("captcha_url", "");
           context.getContentResolver().update(uri.build(), values, "1", null);
                }

                @Deprecated
       public static void initUploadPref(Context context) {
           mUploadPref = context.getSharedPreferences("upload", 0);
                }

                @Deprecated
       public static boolean isKey(String path) {
                    return !path.endsWith(".sub");
                }

                @Deprecated
       public static boolean isEmptySp() {
                    boolean z = true;
                    synchronized (SHARED_LOCK) {
                        try {
                   Map<String, ?> all = mUploadPref.getAll();
                   if (all != null && !all.isEmpty()) {
                                for (String key : all.keySet()) {
                           if (isKey(key)) {
                               z = false;
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return z;
                }

                @Deprecated
       public static HashMap<String, String> getPendingPathsSp() {
                    HashMap<String, String> pendingPaths;
                    synchronized (SHARED_LOCK) {
                        try {
                   pendingPaths = new HashMap<>();
                   Map<String, ?> all = mUploadPref.getAll();
                            for (String key : all.keySet()) {
                       if (isKey(key)) {
                           SLog.m447d("Key: {}", key);
                           int status = getStatusSp(key);
                           if (isUploadExpiredSp(key)) {
                                        switch (status) {
                                            case 0:
                                       pendingPaths.put(key, VineUploadService.ACTION_NOTIFY_FAILED);
                                                break;
                                            case 1:
                                       pendingPaths.put(key, VineUploadService.ACTION_UPLOAD);
                                                break;
                                        }
                                    } else {
                                        switch (status) {
                                            case 0:
                                       pendingPaths.put(key, VineUploadService.ACTION_NOTIFY_FAILED);
                                                break;
                                            case 1:
                                       SLog.m446d("File does not exist any more (uploaded).");
                                       pendingPaths.put(key, VineUploadService.ACTION_POST);
                                                break;
                                        }
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return pendingPaths;
                }

                @Deprecated
       public static String getHashSp(String path) {
                    String string;
                    synchronized (SHARED_LOCK) {
                        try {
                   string = mUploadPref.getString(path + SUB_PREF_HASH, null);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return string;
                }

                @Deprecated
       public static long getUploadTimeSp(String path) {
                    long j;
                    synchronized (SHARED_LOCK) {
                        try {
                   j = mUploadPref.getLong(path + SUB_UPLOAD_TIME, 0L);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return j;
                }

                @Deprecated
       public static int getStatusSp(String path) {
                    int i;
                    synchronized (SHARED_LOCK) {
                        try {
                   i = mUploadPref.getInt(path, -1);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return i;
                }

                @Deprecated
       public static String getPostInfoSp(String path) {
                    String string;
                    synchronized (SHARED_LOCK) {
                        try {
                   string = mUploadPref.getString(path + SUB_PREF_POST, null);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return string;
                }

                @Deprecated
       public static String[] getUrisSp(String path) {
                    String[] strArr;
                    synchronized (SHARED_LOCK) {
                        try {
                   strArr = new String[]{mUploadPref.getString(path + SUB_PREF_VIDEO, null), mUploadPref.getString(path + SUB_PREF_THUMBNAIL, null)};
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return strArr;
                }

                @Deprecated
       public static boolean isUploadExpiredSp(String path) {
                    return System.currentTimeMillis() - mUploadPref.getLong(new StringBuilder().append(path).append(SUB_UPLOAD_TIME).toString(), 0L) > 86400000;
                }

                @Deprecated
       public static void clearSp() {
                    synchronized (SHARED_LOCK) {
                        try {
                   mUploadPref.edit().clear().apply();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public static void setUploadMessageRowId(Context context, String uploadPath, long messageId) {
           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_MESSAGE_ROW_ID).appendQueryParameter("path", uploadPath).appendQueryParameter(VineUploads.Uploads.QUERY_MESSAGE_ROW_ID, String.valueOf(messageId)).build();
           context.getContentResolver().update(contentUri, null, null, null);
                }

       public static void prepopulateCache(Context context, String path, String videoUrl, String thumbnailUrl) {
           SLog.m448d("Prepopulating cache. Video url: {} | Thumbnail url: {}", videoUrl, thumbnailUrl);
           if (videoUrl != null) {
               prepopulateVideoCache(context, path, videoUrl);
                    }
           if (thumbnailUrl != null) {
               prepopulateImageCache(context, RecordConfigUtils.getThumbnailPath(path), thumbnailUrl);
                    }
           context.sendBroadcast(new Intent(BC_INVALIDATE_CACHE), CrossConstants.BROADCAST_PERMISSION);
                }

       private static void prepopulateVideoCache(Context context, String videoPath, String videoUrl) {
           VideoCache<VineAPI> videoCache = Util.newVideoCache(context);
                    try {
               FileInputStream fis = new FileInputStream(videoPath);
               videoCache.prepopulateVideoCacheForUrl(VineAccountHelper.getActiveSession(context, true).getUserId(), new VideoKey(videoUrl), videoUrl, fis);
                    } catch (FileNotFoundException e) {
               CrashUtil.logException(e, "Error prepopulating the cache", new Object[0]);
                    }
                }

       private static void prepopulateImageCache(Context context, String thumbnailPath, String thumbnailUrl) {
           Point p = SystemUtil.getDisplaySize(context);
           int maxImageSize = Math.max(p.x, p.y);
           PhotoImagesCache<VineAPI> imgCache = Util.newImageCache(context, maxImageSize, 0);
                    try {
               FileInputStream fis = new FileInputStream(thumbnailPath);
               imgCache.prepopulateThumbnailCacheForUrl(VineAccountHelper.getActiveSession(context, true).getUserId(), new ImageKey(thumbnailUrl), thumbnailUrl, fis);
                    } catch (FileNotFoundException e) {
               CrashUtil.logException(e, "Error prepopulating the cache", new Object[0]);
                    }
                }
            }
