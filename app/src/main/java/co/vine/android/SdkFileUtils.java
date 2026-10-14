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
            
            
    public class SdkFileUtils {
                @SuppressLint({"NewApi"})
        public static String getPath(Context context, Uri uri) {
            boolean isKitKat = Build.VERSION.SDK_INT >= 19;
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    String docId = DocumentsContract.getDocumentId(uri);
                    String[] split = docId.split(":");
                    if ("primary".equalsIgnoreCase(split[0])) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                            }
                            return null;
                        }
                if (isDownloadsDocument(uri)) {
                    String id = DocumentsContract.getDocumentId(uri);
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id).longValue());
                    return getDataColumn(context, contentUri, null, null);
                        }
                if (!isMediaDocument(uri)) {
                            return null;
                        }
                String docId2 = DocumentsContract.getDocumentId(uri);
                String[] split2 = docId2.split(":");
                String type = split2[0];
                Uri contentUri2 = null;
                if ("image".equals(type)) {
                    contentUri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if (MimeTypes.BASE_TYPE_VIDEO.equals(type)) {
                    contentUri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if (MimeTypes.BASE_TYPE_AUDIO.equals(type)) {
                    contentUri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                String[] selectionArgs = {split2[1]};
                return getDataColumn(context, contentUri2, VineDatabaseSQL.SELECTION_ROW_ID, selectionArgs);
                    }
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
                    }
           if (CommonUtil.FILE.equalsIgnoreCase(uri.getScheme())) {
               return uri.getPath();
                    }
                    return null;
                }

       public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
           Cursor cursor = null;
           String[] projection = {"_data"};
                    try {
               cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
               if (cursor != null && cursor.moveToFirst()) {
                   int column_index = cursor.getColumnIndexOrThrow("_data");
                   String string = cursor.getString(column_index);
                   if (cursor != null) {
                       cursor.close();
                       return string;
                            }
                   return string;
                        }
               if (cursor != null) {
                   cursor.close();
                        }
                        return null;
                    } catch (Throwable th) {
                        if (cursor != null) {
                   cursor.close();
                        }
                        throw th;
                    }
                }

       public static boolean isExternalStorageDocument(Uri uri) {
           return "com.android.externalstorage.documents".equals(uri.getAuthority());
                }

       public static boolean isDownloadsDocument(Uri uri) {
           return "com.android.providers.downloads.documents".equals(uri.getAuthority());
                }

       public static boolean isMediaDocument(Uri uri) {
           return "com.android.providers.media.documents".equals(uri.getAuthority());
                }
            }
