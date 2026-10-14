            package co.vine.android;

            import android.annotation.SuppressLint;
            import android.content.ContentUris;
            import android.content.Context;
            import android.database.Cursor;
            import android.net.Uri;
            import android.os.Build;
            import android.os.Environment;
            import android.provider.DocumentsContract;
            import android.provider.MediaStore;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.util.CommonUtil;
            import com.google.android.exoplayer.util.MimeTypes;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class SdkFileUtils {
                @SuppressLint({"NewApi"})
/* 47 */        public static String getPath(Context context, Uri uri) {
/* 48 */            boolean isKitKat = Build.VERSION.SDK_INT >= 19;
/* 51 */            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
/* 53 */                if (isExternalStorageDocument(uri)) {
/* 54 */                    String docId = DocumentsContract.getDocumentId(uri);
/* 55 */                    String[] split = docId.split(":");
/* 58 */                    if ("primary".equalsIgnoreCase(split[0])) {
/* 59 */                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                            }
                            return null;
                        }
/* 63 */                if (isDownloadsDocument(uri)) {
/* 65 */                    String id = DocumentsContract.getDocumentId(uri);
/* 66 */                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id).longValue());
/* 69 */                    return getDataColumn(context, contentUri, null, null);
                        }
/* 72 */                if (!isMediaDocument(uri)) {
                            return null;
                        }
/* 73 */                String docId2 = DocumentsContract.getDocumentId(uri);
/* 74 */                String[] split2 = docId2.split(":");
/* 75 */                String type = split2[0];
/* 77 */                Uri contentUri2 = null;
/* 78 */                if ("image".equals(type)) {
/* 79 */                    contentUri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if (MimeTypes.BASE_TYPE_VIDEO.equals(type)) {
/* 81 */                    contentUri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if (MimeTypes.BASE_TYPE_AUDIO.equals(type)) {
/* 83 */                    contentUri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
/* 87 */                String[] selectionArgs = {split2[1]};
/* 91 */                return getDataColumn(context, contentUri2, VineDatabaseSQL.SELECTION_ROW_ID, selectionArgs);
                    }
/* 95 */            if ("content".equalsIgnoreCase(uri.getScheme())) {
/* 96 */                return getDataColumn(context, uri, null, null);
                    }
/* 100 */           if (CommonUtil.FILE.equalsIgnoreCase(uri.getScheme())) {
/* 101 */               return uri.getPath();
                    }
                    return null;
                }

/* 119 */       public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
/* 120 */           Cursor cursor = null;
/* 122 */           String[] projection = {"_data"};
                    try {
/* 127 */               cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
/* 129 */               if (cursor != null && cursor.moveToFirst()) {
/* 130 */                   int column_index = cursor.getColumnIndexOrThrow("_data");
/* 131 */                   String string = cursor.getString(column_index);
/* 134 */                   if (cursor != null) {
/* 135 */                       cursor.close();
/* 137 */                       return string;
                            }
/* 137 */                   return string;
                        }
/* 134 */               if (cursor != null) {
/* 135 */                   cursor.close();
                        }
                        return null;
                    } catch (Throwable th) {
                        if (cursor != null) {
/* 135 */                   cursor.close();
                        }
                        throw th;
                    }
                }

/* 145 */       public static boolean isExternalStorageDocument(Uri uri) {
/* 146 */           return "com.android.externalstorage.documents".equals(uri.getAuthority());
                }

/* 153 */       public static boolean isDownloadsDocument(Uri uri) {
/* 154 */           return "com.android.providers.downloads.documents".equals(uri.getAuthority());
                }

/* 161 */       public static boolean isMediaDocument(Uri uri) {
/* 162 */           return "com.android.providers.media.documents".equals(uri.getAuthority());
                }
            }
