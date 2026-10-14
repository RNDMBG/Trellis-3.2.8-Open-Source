            package co.vine.android.util;

            import android.content.Context;
            import android.net.Uri;
            import android.text.TextUtils;
            import co.vine.android.api.VineParserReader;
            import co.vine.android.client.VineAPI;
            import co.vine.android.network.FileNetworkEntity;
            import co.vine.android.network.NetworkOperation;
            import co.vine.android.network.NetworkOperationFactory;
            import co.vine.android.network.VineNetworkUtils;
            import co.vine.android.recorder.RecordSessionVersion;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.util.UUID;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class MediaUtility {
                private final Context mContext;
                private final String mMediaUrl;
                private final NetworkOperationFactory mNetworkFactory = VineNetworkUtils.getDefaultNetworkOperationFactory();

/* 28 */        public MediaUtility(Context context) {
/* 30 */            this.mContext = context;
/* 31 */            this.mMediaUrl = VineAPI.getInstance(context).getMediaUrl();
                }

/* 36 */        public FileInfo upload(FileNetworkEntity.ProgressListener listener, String key, File fileToUpload, String type) throws Exception {
/* 37 */            return upload(listener, key, fileToUpload, type, false);
                }

/* 41 */        public FileInfo upload(FileNetworkEntity.ProgressListener listener, String key, File fileToUpload, String type, boolean isPrivate) throws Exception {
/* 42 */            SLog.m448d("Upload started for {}, key: {}.", fileToUpload, key);
/* 43 */            RecordSessionVersion version = UploadManager.getVersionFromPath(fileToUpload.getPath());
/* 44 */            if (key.endsWith(version.videoOutputExtension)) {
/* 45 */                StringBuilder url = VineAPI.buildUponUrl(this.mMediaUrl, "upload", "videos", fileToUpload.getName() + version.videoOutputExtension);
/* 49 */                if (isPrivate) {
/* 50 */                    VineAPI.addParam(url, "private", "1");
                        }
/* 52 */                MeasureOutputStream entity = new MeasureOutputStream(fileToUpload, version.mimeType, listener);
/* 53 */                VineParserReader reader = VineParserReader.createParserReader(16);
/* 55 */                NetworkOperation op = this.mNetworkFactory.createMediaPutRequest(this.mContext, url, reader, entity, VineAPI.getInstance(this.mContext)).execute();
/* 57 */                if (op.isOK()) {
/* 58 */                    SLog.m447d("Upload {} successful.", op.uploadKey);
/* 59 */                    return new FileInfo(fileToUpload.getName(), fileToUpload, op.uploadKey);
                        }
/* 61 */                SLog.m449d("{} {} {}", url, Integer.valueOf(op.statusCode), op.statusPhrase);
                    } else if (key.endsWith("jpg")) {
/* 64 */                SLog.m446d("Upload image through media.");
/* 65 */                StringBuilder url2 = VineAPI.buildUponUrl(this.mMediaUrl, "upload", type, fileToUpload.getName() + ".jpg");
/* 69 */                if (isPrivate) {
/* 70 */                    VineAPI.addParam(url2, "private", "1");
                        }
/* 72 */                MeasureOutputStream entity2 = new MeasureOutputStream(fileToUpload, "image/jpeg", listener);
/* 73 */                VineParserReader reader2 = VineParserReader.createParserReader(16);
/* 75 */                NetworkOperation op2 = this.mNetworkFactory.createMediaPutRequest(this.mContext, url2, reader2, entity2, VineAPI.getInstance(this.mContext)).execute();
/* 77 */                if (op2.isOK()) {
/* 78 */                    SLog.m447d("Upload {} successful.", op2.uploadKey);
/* 79 */                    return new FileInfo(fileToUpload.getName(), fileToUpload, op2.uploadKey);
                        }
/* 81 */                SLog.m460e("{} {} {}", new Object[]{url2, Integer.valueOf(op2.statusCode), op2.statusPhrase});
                    }
/* 84 */            return null;
                }

/* 88 */        public String getThumbnailUri(FileNetworkEntity.ProgressListener listener, String filename, File thumbnail, Context context) throws Exception {
/* 90 */            FileInfo info = upload(listener, "thumbs/" + filename + ".jpg", thumbnail, "thumbs");
/* 91 */            if (info != null) {
/* 92 */                return info.uploadKey;
                    }
                    return null;
                }

/* 100 */       public String getVideoUri(FileNetworkEntity.ProgressListener listener, File file, Context context, String filename, boolean isPrivate) throws Exception {
/* 102 */           FileInfo info = upload(listener, "videos/" + filename, file, "videos", isPrivate);
/* 103 */           if (info != null) {
/* 104 */               return info.uploadKey;
                    }
                    return null;
                }

/* 113 */       public String getPhotoUri(Uri avatarFileUri, Context context) throws Exception {
/* 114 */           String filename = "avatars/" + UUID.randomUUID() + ".jpg";
/* 116 */           if (avatarFileUri == null || TextUtils.isEmpty(avatarFileUri.toString())) {
                        return null;
                    }
/* 117 */           String realFileUrl = ImageUtils.getRealPathFromImageUri(context, avatarFileUri);
/* 119 */           FileInfo info = upload(null, filename, new File(realFileUrl), "avatars");
/* 121 */           if (info != null) {
/* 122 */               return info.uploadKey;
                    }
                    return null;
                }

/* 134 */       private static class FileInfo {
                    public File file;
                    public String filename;
                    public String uploadKey;

/* 135 */           public FileInfo(String filename, File file, String uploadKey) {
/* 137 */               this.filename = filename;
/* 138 */               this.file = file;
/* 139 */               this.uploadKey = uploadKey;
                    }
                }
            }
