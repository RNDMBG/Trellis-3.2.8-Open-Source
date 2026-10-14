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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 70 */    public class UploadManager {
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

/* 71 */        public UploadManager(Context context) {
/* 73 */            this.mMediaUtility = new MediaUtility(context);
                }

/* 76 */        public static void removeFromUploadQueue(Context context, String path) {
/* 77 */            context.startService(VineUploadService.getDiscardIntent(context, path));
                }

/* 82 */        private static String addToUploadQueue(Context context, File dir, RecordingFile file, String thumbnail, String reference, boolean isMessaging, long conversationId) throws Exception {
/* 83 */            String fileName = file.version.name() + "_" + System.currentTimeMillis();
/* 84 */            File videoFile = RecordConfigUtils.copyForUpload(dir, file.getVideoPath(), fileName);
/* 85 */            String path = videoFile.getPath();
/* 86 */            String thumbnail2 = RecordConfigUtils.copyForUpload(dir, thumbnail, RecordConfigUtils.getThumbnailPath(fileName)).getPath();
/* 87 */            RecordConfigUtils.writeForUpload(dir, RecordConfigUtils.getMetadataPath(fileName), file.getSession().getMetaData().toString());
/* 89 */            context.startService(VineUploadService.getUploadIntent(context, path, file.getHash(), thumbnail2, reference, isMessaging, conversationId));
/* 91 */            return path;
                }

/* 94 */        public static RecordSessionVersion getVersionFromPath(String path) {
/* 95 */            RecordSessionVersion version = RecordSessionVersion.SW_MP4;
/* 96 */            String fn = new File(path).getName();
/* 97 */            if (fn.startsWith(RecordSessionVersion.HW.name())) {
/* 98 */                RecordSessionVersion version2 = RecordSessionVersion.HW;
/* 102 */               return version2;
                    }
/* 99 */            if (fn.startsWith(RecordSessionVersion.SW_WEBM.name())) {
/* 100 */               RecordSessionVersion version3 = RecordSessionVersion.SW_WEBM;
/* 102 */               return version3;
                    }
/* 102 */           return version;
                }

/* 109 */       public static String addToUploadQueue(Context context, RecordingFile file, String thumbnail, String reference, boolean isMessaging, long conversationId) throws Exception {
                    File processDir;
                    try {
/* 110 */               processDir = new RecordConfigUtils.RecordConfig(context).processDir;
                    } catch (Exception e) {
/* 112 */               processDir = context.getCacheDir();
                    }
/* 114 */           return addToUploadQueue(context, processDir, file, thumbnail, reference, isMessaging, conversationId);
                }

        /** Queues an already encoded gallery video without the legacy native transcoder. */
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

/* 118 */       public static Cursor getReferenceCursor(Context context, String reference) {
/* 119 */           if (TextUtils.isEmpty(reference)) {
                        return null;
                    }
/* 122 */           long ownerId = AppController.getInstance(context).getActiveSessionReadOnly().getUserId();
/* 123 */           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_REFERENCE).appendEncodedPath(String.valueOf(ownerId)).appendQueryParameter("reference", reference).build();
/* 128 */           return context.getContentResolver().query(contentUri, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, null, null, null);
                }

/* 135 */       public static void addOrUpdateUpload(Context context, String path, String hash, String reference, boolean isPrivate, long conversationObjectId, long messageRowId) {
/* 136 */           long ownerId = AppController.getInstance(context).getActiveSessionReadOnly().getUserId();
/* 137 */           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_UPLOAD).appendEncodedPath(String.valueOf(ownerId)).appendQueryParameter("path", path).build();
/* 143 */           Cursor c = context.getContentResolver().query(contentUri, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, null, null, null);
/* 146 */           ContentValues values = new ContentValues();
/* 147 */           if (c == null || c.getCount() < 1) {
/* 148 */               String thumbnailPath = RecordConfigUtils.getThumbnailPath(path);
/* 149 */               values.put("path", path);
/* 150 */               values.put("thumbnail_path", thumbnailPath);
/* 151 */               values.put("hash", hash);
/* 152 */               values.put("status", (Integer) 0);
/* 153 */               values.put("is_private", isPrivate ? "1" : "0");
/* 154 */               values.put("reference", reference);
/* 155 */               values.put("owner_id", Long.valueOf(ownerId));
                        if (messageRowId > 0) {
/* 157 */                   values.put(VineUploads.UploadsColumns.MESSAGE_ROW_ID, Long.valueOf(messageRowId));
                        }
                        if (conversationObjectId > 0) {
/* 160 */                   values.put("conversation_row_id", Long.valueOf(conversationObjectId));
                        }
/* 163 */               Uri contentUri2 = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_NEW_UPLOAD).build();
/* 166 */               context.getContentResolver().insert(contentUri2, values);
                    } else {
/* 169 */               String[] selectionArgs = {path};
/* 170 */               if (!TextUtils.isEmpty(hash)) {
/* 171 */                   values.put("hash", hash);
                        }
/* 173 */               values.put("status", (Integer) 0);
/* 175 */               Uri contentUri3 = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_VALUES).build();
/* 178 */               context.getContentResolver().update(contentUri3, values, "path=?", selectionArgs);
                    }
/* 180 */           if (c != null) {
                        c.close();
                    }
                }

/* 183 */       public static boolean uploadListIsEmpty(Context context) {
                    boolean cursorIsEmpty = true;
/* 184 */           Cursor c = context.getContentResolver().query(VineUploads.Uploads.CONTENT_URI, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, null, null, null);
/* 186 */           if (c != null) {
/* 187 */               cursorIsEmpty = c.getCount() < 1;
/* 188 */               c.close();
                    }
/* 191 */           return cursorIsEmpty;
                }

/* 194 */       public static VineUpload getUpload(Context context, String path) {
/* 195 */           long ownerId = AppController.getInstance(context).getActiveSessionReadOnly().getUserId();
/* 196 */           VineUpload ret = null;
/* 197 */           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_UPLOAD).appendEncodedPath(String.valueOf(ownerId)).appendQueryParameter("path", path).build();
/* 203 */           Cursor c = context.getContentResolver().query(contentUri, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, null, null, null);
/* 205 */           if (c != null) {
/* 206 */               if (c.moveToFirst()) {
/* 207 */                   ret = VineUpload.fromCursor(path, c);
                        }
/* 209 */               c.close();
                    }
/* 211 */           return ret;
                }

/* 214 */       public static void deleteUploadRecord(Context context, String path) {
/* 215 */           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_DELETE_UPLOAD).build();
/* 219 */           String[] whereArgs = {path};
/* 220 */           context.getContentResolver().delete(contentUri, "path=?", whereArgs);
                }

/* 223 */       private static Uri.Builder getBaseUriBuilder() {
/* 224 */           return new Uri.Builder().scheme("content").authority(VineUploadProvider.AUTHORITY);
                }

/* 229 */       public static String generateFileName(Context context) throws PackageManager.NameNotFoundException {
/* 230 */           SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/", Locale.US);
/* 231 */           return formatter.format(new Date()) + UUID.randomUUID() + "_" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
                }

                /* JADX WARN: Code duplicated, block: B:24:0x0070 A[Catch: Exception -> 0x002c, all -> 0x00a6, TryCatch #0 {Exception -> 0x002c, blocks: (B:9:0x0018, B:11:0x0024, B:12:0x002b, B:16:0x0036, B:18:0x0042, B:20:0x0048, B:21:0x004f, B:22:0x006a, B:24:0x0070, B:25:0x0077, B:27:0x009e, B:28:0x00a5, B:32:0x00a9, B:34:0x00af, B:35:0x00b7, B:37:0x00c8, B:38:0x00cf, B:39:0x00d0), top: B:41:0x0018, outer: #1 }] */
                /* JADX WARN: Code duplicated, block: B:25:0x0077 A[Catch: Exception -> 0x002c, all -> 0x00a6, TryCatch #0 {Exception -> 0x002c, blocks: (B:9:0x0018, B:11:0x0024, B:12:0x002b, B:16:0x0036, B:18:0x0042, B:20:0x0048, B:21:0x004f, B:22:0x006a, B:24:0x0070, B:25:0x0077, B:27:0x009e, B:28:0x00a5, B:32:0x00a9, B:34:0x00af, B:35:0x00b7, B:37:0x00c8, B:38:0x00cf, B:39:0x00d0), top: B:41:0x0018, outer: #1 }] */
                /* JADX WARN: Code duplicated, block: B:27:0x009e A[Catch: Exception -> 0x002c, all -> 0x00a6, TryCatch #0 {Exception -> 0x002c, blocks: (B:9:0x0018, B:11:0x0024, B:12:0x002b, B:16:0x0036, B:18:0x0042, B:20:0x0048, B:21:0x004f, B:22:0x006a, B:24:0x0070, B:25:0x0077, B:27:0x009e, B:28:0x00a5, B:32:0x00a9, B:34:0x00af, B:35:0x00b7, B:37:0x00c8, B:38:0x00cf, B:39:0x00d0), top: B:41:0x0018, outer: #1 }] */
                /* JADX WARN: Code duplicated, block: B:32:0x00a9 A[Catch: Exception -> 0x002c, all -> 0x00a6, TRY_ENTER, TryCatch #0 {Exception -> 0x002c, blocks: (B:9:0x0018, B:11:0x0024, B:12:0x002b, B:16:0x0036, B:18:0x0042, B:20:0x0048, B:21:0x004f, B:22:0x006a, B:24:0x0070, B:25:0x0077, B:27:0x009e, B:28:0x00a5, B:32:0x00a9, B:34:0x00af, B:35:0x00b7, B:37:0x00c8, B:38:0x00cf, B:39:0x00d0), top: B:41:0x0018, outer: #1 }] */
                /* JADX WARN: Code duplicated, block: B:34:0x00af A[Catch: Exception -> 0x002c, all -> 0x00a6, TryCatch #0 {Exception -> 0x002c, blocks: (B:9:0x0018, B:11:0x0024, B:12:0x002b, B:16:0x0036, B:18:0x0042, B:20:0x0048, B:21:0x004f, B:22:0x006a, B:24:0x0070, B:25:0x0077, B:27:0x009e, B:28:0x00a5, B:32:0x00a9, B:34:0x00af, B:35:0x00b7, B:37:0x00c8, B:38:0x00cf, B:39:0x00d0), top: B:41:0x0018, outer: #1 }] */
                /* JADX WARN: Code duplicated, block: B:35:0x00b7 A[Catch: Exception -> 0x002c, all -> 0x00a6, TryCatch #0 {Exception -> 0x002c, blocks: (B:9:0x0018, B:11:0x0024, B:12:0x002b, B:16:0x0036, B:18:0x0042, B:20:0x0048, B:21:0x004f, B:22:0x006a, B:24:0x0070, B:25:0x0077, B:27:0x009e, B:28:0x00a5, B:32:0x00a9, B:34:0x00af, B:35:0x00b7, B:37:0x00c8, B:38:0x00cf, B:39:0x00d0), top: B:41:0x0018, outer: #1 }] */
                /* JADX WARN: Code duplicated, block: B:37:0x00c8 A[Catch: Exception -> 0x002c, all -> 0x00a6, TryCatch #0 {Exception -> 0x002c, blocks: (B:9:0x0018, B:11:0x0024, B:12:0x002b, B:16:0x0036, B:18:0x0042, B:20:0x0048, B:21:0x004f, B:22:0x006a, B:24:0x0070, B:25:0x0077, B:27:0x009e, B:28:0x00a5, B:32:0x00a9, B:34:0x00af, B:35:0x00b7, B:37:0x00c8, B:38:0x00cf, B:39:0x00d0), top: B:41:0x0018, outer: #1 }] */
                /* JADX WARN: Code duplicated, block: B:39:0x00d0 A[Catch: Exception -> 0x002c, all -> 0x00a6, TRY_LEAVE, TryCatch #0 {Exception -> 0x002c, blocks: (B:9:0x0018, B:11:0x0024, B:12:0x002b, B:16:0x0036, B:18:0x0042, B:20:0x0048, B:21:0x004f, B:22:0x006a, B:24:0x0070, B:25:0x0077, B:27:0x009e, B:28:0x00a5, B:32:0x00a9, B:34:0x00af, B:35:0x00b7, B:37:0x00c8, B:38:0x00cf, B:39:0x00d0), top: B:41:0x0018, outer: #1 }] */
/* 239 */       public synchronized String[] upload(VineUploadService.ServiceAsyncTask serviceAsyncTask, UploadProgressListener listener, String encoded, String thumbnail, String path, boolean isPrivate, RecordSessionVersion version) throws Exception {
                    String[] strArr;
                    String fileName;
                    String videoUri;
                    String thumbnailUrl;
                    try {
                        File file = new File(encoded);
/* 241 */               if (!file.exists()) {
/* 242 */                   SLog.m456e("Files does not exist: {}", encoded);
/* 243 */                   strArr = null;
                        } else {
                            try {
/* 246 */                       VineUpload upload = getUpload(serviceAsyncTask.getContext(), path);
/* 247 */                       if (upload == null) {
/* 248 */                           throw new Exception("Upload record was null");
                                }
/* 251 */                       String hash = HashAsyncTask.getMD5Checksum(encoded);
/* 252 */                       String expected = upload.hash;
/* 253 */                       if (!hash.equals(expected)) {
/* 254 */                           if (TextUtils.isEmpty(expected)) {
/* 255 */                               SLog.m457e("Invalid hash: actual {}, expected {}.", hash, expected);
/* 256 */                               strArr = null;
                                    } else {
/* 258 */                               CrashUtil.logException(new VineLoggingException("Expected hash was invalid: " + expected));
/* 262 */                               if (serviceAsyncTask.isCancelled()) {
/* 263 */                                   SLog.m446d("Task is already cancelled.");
/* 264 */                                   strArr = null;
                                        } else {
/* 267 */                                   Context context = serviceAsyncTask.getContext();
/* 268 */                                   fileName = generateFileName(context) + version.videoOutputExtension;
/* 270 */                                   videoUri = uploadVideo(listener, file, context, fileName, isPrivate);
/* 273 */                                   if (videoUri == null) {
/* 274 */                                       throw new Exception("Failed to upload video.");
                                            }
/* 277 */                                   if (serviceAsyncTask.isCancelled()) {
/* 278 */                                       SLog.m446d("Task is already cancelled.");
/* 279 */                                       strArr = null;
                                            } else {
/* 282 */                                       thumbnailUrl = uploadThumbnail(listener, new File(thumbnail), serviceAsyncTask.getContext(), fileName);
/* 285 */                                       if (thumbnailUrl == null) {
/* 286 */                                           throw new Exception("Failed to upload thumbnail.");
                                                }
/* 289 */                                       strArr = new String[]{videoUri, thumbnailUrl};
                                            }
                                        }
                                    }
                                } else if (serviceAsyncTask.isCancelled()) {
/* 263 */                           SLog.m446d("Task is already cancelled.");
/* 264 */                           strArr = null;
                                } else {
/* 267 */                           Context context2 = serviceAsyncTask.getContext();
/* 268 */                           fileName = generateFileName(context2) + version.videoOutputExtension;
/* 270 */                           videoUri = uploadVideo(listener, file, context2, fileName, isPrivate);
/* 273 */                           if (videoUri == null) {
/* 274 */                               throw new Exception("Failed to upload video.");
                                    }
/* 277 */                           if (serviceAsyncTask.isCancelled()) {
/* 278 */                               SLog.m446d("Task is already cancelled.");
/* 279 */                               strArr = null;
                                    } else {
/* 282 */                               thumbnailUrl = uploadThumbnail(listener, new File(thumbnail), serviceAsyncTask.getContext(), fileName);
/* 285 */                               if (thumbnailUrl == null) {
/* 286 */                                   throw new Exception("Failed to upload thumbnail.");
                                        }
/* 289 */                               strArr = new String[]{videoUri, thumbnailUrl};
                                    }
                                }
                            } catch (Exception e) {
/* 291 */                       SLog.m458e("Upload failed: {}", (Object) path, (Throwable) e);
/* 292 */                       strArr = null;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return strArr;
                }

/* 296 */       public String uploadVideo(FileNetworkEntity.ProgressListener listener, File file, Context context, String fileName, boolean isPrivate) throws Exception {
/* 297 */           return this.mMediaUtility.getVideoUri(listener, file, context, fileName, isPrivate);
                }

/* 302 */       public String uploadThumbnail(FileNetworkEntity.ProgressListener listener, File file, Context context, String fileName) throws Exception {
/* 303 */           return this.mMediaUtility.getThumbnailUri(listener, fileName, file, context);
                }

/* 310 */       public static LinkedHashMap<String, Integer> getAllPaths(Context context) {
/* 311 */           LinkedHashMap<String, Integer> paths = new LinkedHashMap<>();
/* 312 */           long ownerId = AppController.getInstance(context).getActiveSessionReadOnly().getUserId();
/* 313 */           Uri contentUri = ContentUris.withAppendedId(VineUploads.Uploads.CONTENT_URI, ownerId);
/* 314 */           Cursor c = context.getContentResolver().query(contentUri, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, null, null, "_id ASC");
/* 316 */           if (c != null) {
/* 317 */               while (c.moveToNext()) {
/* 318 */                   String path = c.getString(1);
/* 319 */                   int status = c.getInt(3);
/* 320 */                   paths.put(path, Integer.valueOf(status));
                        }
/* 322 */               c.close();
                    }
/* 324 */           return paths;
                }

/* 330 */       public static LinkedList<String> getPendingPaths(Context context) {
/* 331 */           LinkedList<String> pendingPaths = new LinkedList<>();
/* 332 */           LinkedHashMap<String, Integer> allPaths = getAllPaths(context);
                    for (String path : allPaths.keySet()) {
/* 334 */               int status = allPaths.get(path).intValue();
/* 335 */               if (status != 2 && status != -1) {
/* 336 */                   pendingPaths.add(path);
                        }
                    }
/* 339 */           return pendingPaths;
                }

/* 342 */       public static void setHash(Context context, VineUpload upload, String hash) {
/* 343 */           upload.hash = hash;
/* 344 */           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_HASH).appendQueryParameter("path", upload.path).appendQueryParameter("hash", hash).build();
/* 349 */           context.getContentResolver().update(contentUri, null, null, null);
                }

/* 352 */       public static void setPostInfo(Context context, VineUpload upload, PostInfo info) {
/* 353 */           SLog.m446d("Setting post info for path=" + upload.path + " with caption=" + info.caption + ", twitter=" + info.postToTwitter + ", facebook=" + info.postToFacebook);
/* 355 */           upload.postInfo = info.toString();
/* 356 */           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_POST_INFO).appendQueryParameter("path", upload.path).appendQueryParameter("post_info", info.toString()).build();
/* 361 */           context.getContentResolver().update(contentUri, null, null, null);
                }

/* 364 */       public static void setStatus(Context context, VineUpload upload, int status) {
/* 365 */           setStatus(context, upload, status, null);
                }

/* 368 */       public static void setStatus(Context context, VineUpload upload, int status, String captcha) {
/* 369 */           upload.status = status;
/* 370 */           Uri.Builder contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_STATUS).appendQueryParameter("path", upload.path).appendQueryParameter("status", String.valueOf(status));
/* 374 */           if (!TextUtils.isEmpty(captcha)) {
/* 375 */               contentUri.appendQueryParameter("captcha_url", captcha);
                    }
/* 377 */           context.getContentResolver().update(contentUri.build(), null, null, null);
                }

/* 380 */       public static void setUploadTime(Context context, VineUpload upload) {
/* 381 */           upload.uploadTime = System.currentTimeMillis();
/* 382 */           String currentTime = String.valueOf(System.currentTimeMillis());
/* 383 */           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_UPLOAD_TIME).appendQueryParameter("path", upload.path).appendQueryParameter("upload_time", currentTime).build();
/* 388 */           context.getContentResolver().update(contentUri, null, null, null);
                }

/* 391 */       public static void setUris(Context context, VineUpload upload, String[] uris) {
/* 392 */           upload.setUris(uris);
/* 393 */           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_URIS).appendQueryParameter("path", upload.path).appendQueryParameter("video_url", uris[0]).appendQueryParameter("thumbnail_url", uris[1]).build();
/* 399 */           context.getContentResolver().update(contentUri, null, null, null);
                }

/* 402 */       public static void clearUploadCaptchas(Context context) {
/* 403 */           Uri.Builder uri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_VALUES);
/* 404 */           ContentValues values = new ContentValues();
/* 405 */           values.put("captcha_url", "");
/* 406 */           context.getContentResolver().update(uri.build(), values, "1", null);
                }

                @Deprecated
/* 422 */       public static void initUploadPref(Context context) {
/* 423 */           mUploadPref = context.getSharedPreferences("upload", 0);
                }

                @Deprecated
/* 427 */       public static boolean isKey(String path) {
                    return !path.endsWith(".sub");
                }

                @Deprecated
/* 432 */       public static boolean isEmptySp() {
                    boolean z = true;
                    synchronized (SHARED_LOCK) {
                        try {
/* 434 */                   Map<String, ?> all = mUploadPref.getAll();
/* 435 */                   if (all != null && !all.isEmpty()) {
                                for (String key : all.keySet()) {
/* 439 */                           if (isKey(key)) {
/* 440 */                               z = false;
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 443 */           return z;
                }

                @Deprecated
/* 449 */       public static HashMap<String, String> getPendingPathsSp() {
                    HashMap<String, String> pendingPaths;
                    synchronized (SHARED_LOCK) {
                        try {
/* 452 */                   pendingPaths = new HashMap<>();
/* 453 */                   Map<String, ?> all = mUploadPref.getAll();
                            for (String key : all.keySet()) {
/* 455 */                       if (isKey(key)) {
/* 456 */                           SLog.m447d("Key: {}", key);
/* 457 */                           int status = getStatusSp(key);
/* 458 */                           if (isUploadExpiredSp(key)) {
                                        switch (status) {
                                            case 0:
/* 464 */                                       pendingPaths.put(key, VineUploadService.ACTION_NOTIFY_FAILED);
                                                break;
                                            case 1:
/* 461 */                                       pendingPaths.put(key, VineUploadService.ACTION_UPLOAD);
                                                break;
                                        }
                                    } else {
                                        switch (status) {
                                            case 0:
/* 474 */                                       pendingPaths.put(key, VineUploadService.ACTION_NOTIFY_FAILED);
                                                break;
                                            case 1:
/* 470 */                                       SLog.m446d("File does not exist any more (uploaded).");
/* 471 */                                       pendingPaths.put(key, VineUploadService.ACTION_POST);
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
/* 486 */       public static String getHashSp(String path) {
                    String string;
                    synchronized (SHARED_LOCK) {
                        try {
/* 488 */                   string = mUploadPref.getString(path + SUB_PREF_HASH, null);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return string;
                }

                @Deprecated
/* 493 */       public static long getUploadTimeSp(String path) {
                    long j;
                    synchronized (SHARED_LOCK) {
                        try {
/* 495 */                   j = mUploadPref.getLong(path + SUB_UPLOAD_TIME, 0L);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return j;
                }

                @Deprecated
/* 500 */       public static int getStatusSp(String path) {
                    int i;
                    synchronized (SHARED_LOCK) {
                        try {
/* 502 */                   i = mUploadPref.getInt(path, -1);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return i;
                }

                @Deprecated
/* 507 */       public static String getPostInfoSp(String path) {
                    String string;
                    synchronized (SHARED_LOCK) {
                        try {
/* 509 */                   string = mUploadPref.getString(path + SUB_PREF_POST, null);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return string;
                }

                @Deprecated
/* 514 */       public static String[] getUrisSp(String path) {
                    String[] strArr;
                    synchronized (SHARED_LOCK) {
                        try {
/* 516 */                   strArr = new String[]{mUploadPref.getString(path + SUB_PREF_VIDEO, null), mUploadPref.getString(path + SUB_PREF_THUMBNAIL, null)};
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return strArr;
                }

                @Deprecated
/* 522 */       public static boolean isUploadExpiredSp(String path) {
                    return System.currentTimeMillis() - mUploadPref.getLong(new StringBuilder().append(path).append(SUB_UPLOAD_TIME).toString(), 0L) > 86400000;
                }

                @Deprecated
/* 527 */       public static void clearSp() {
                    synchronized (SHARED_LOCK) {
                        try {
/* 529 */                   mUploadPref.edit().clear().apply();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 533 */       public static void setUploadMessageRowId(Context context, String uploadPath, long messageId) {
/* 534 */           Uri contentUri = getBaseUriBuilder().path(VineUploads.Uploads.PATH_PUT_MESSAGE_ROW_ID).appendQueryParameter("path", uploadPath).appendQueryParameter(VineUploads.Uploads.QUERY_MESSAGE_ROW_ID, String.valueOf(messageId)).build();
/* 539 */           context.getContentResolver().update(contentUri, null, null, null);
                }

/* 542 */       public static void prepopulateCache(Context context, String path, String videoUrl, String thumbnailUrl) {
/* 543 */           SLog.m448d("Prepopulating cache. Video url: {} | Thumbnail url: {}", videoUrl, thumbnailUrl);
/* 544 */           if (videoUrl != null) {
/* 545 */               prepopulateVideoCache(context, path, videoUrl);
                    }
/* 547 */           if (thumbnailUrl != null) {
/* 548 */               prepopulateImageCache(context, RecordConfigUtils.getThumbnailPath(path), thumbnailUrl);
                    }
/* 551 */           context.sendBroadcast(new Intent(BC_INVALIDATE_CACHE), CrossConstants.BROADCAST_PERMISSION);
                }

/* 554 */       private static void prepopulateVideoCache(Context context, String videoPath, String videoUrl) {
/* 555 */           VideoCache<VineAPI> videoCache = Util.newVideoCache(context);
                    try {
/* 558 */               FileInputStream fis = new FileInputStream(videoPath);
/* 563 */               videoCache.prepopulateVideoCacheForUrl(VineAccountHelper.getActiveSession(context, true).getUserId(), new VideoKey(videoUrl), videoUrl, fis);
                    } catch (FileNotFoundException e) {
/* 560 */               CrashUtil.logException(e, "Error prepopulating the cache", new Object[0]);
                    }
                }

/* 569 */       private static void prepopulateImageCache(Context context, String thumbnailPath, String thumbnailUrl) {
/* 570 */           Point p = SystemUtil.getDisplaySize(context);
/* 571 */           int maxImageSize = Math.max(p.x, p.y);
/* 573 */           PhotoImagesCache<VineAPI> imgCache = Util.newImageCache(context, maxImageSize, 0);
                    try {
/* 576 */               FileInputStream fis = new FileInputStream(thumbnailPath);
/* 581 */               imgCache.prepopulateThumbnailCacheForUrl(VineAccountHelper.getActiveSession(context, true).getUserId(), new ImageKey(thumbnailUrl), thumbnailUrl, fis);
                    } catch (FileNotFoundException e) {
/* 578 */               CrashUtil.logException(e, "Error prepopulating the cache", new Object[0]);
                    }
                }
            }
