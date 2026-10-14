            package co.vine.android.provider;

            import android.content.ContentProvider;
            import android.content.ContentValues;
            import android.content.UriMatcher;
            import android.database.Cursor;
            import android.database.sqlite.SQLiteDatabase;
            import android.database.sqlite.SQLiteException;
            import android.database.sqlite.SQLiteQueryBuilder;
            import android.net.Uri;
            import android.text.TextUtils;
            import android.util.Log;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import java.util.Arrays;
            
            
    public class VineUploadProvider extends ContentProvider {
                private static final int ALL_UPLOADS = 1;
                public static final String AUTHORITY;
                public static final String CONTENT_AUTHORITY;
                private static final int DELETE_ALL_UPLOADS = 5;
                private static final int DELETE_UPLOAD = 4;
                private static final int GET_LAST_PATH = 3;
                private static final boolean LOGGABLE;
                private static final int PUT_HASH = 7;
                private static final int PUT_MESSAGE_ROW_ID = 14;
                private static final int PUT_NEW_UPLOAD = 6;
                private static final int PUT_POST_INFO = 10;
                private static final int PUT_STATUS = 8;
                private static final int PUT_UPLOAD_TIME = 11;
                private static final int PUT_URIS = 9;
                private static final int PUT_VALUES = 12;
                private static final int REFERENCE = 13;
                public static final String SCHEME = "content";
                private static final String TAG = "VineUploadProvider";
                private static final int UPLOAD = 2;
                private static final UriMatcher sUriMatcher;

                static {
            LOGGABLE = BuildUtil.isLogsOn() || Log.isLoggable(TAG, 3);
            AUTHORITY = BuildUtil.getAuthority(".provider.VineUploadProvider");
            CONTENT_AUTHORITY = "content://" + AUTHORITY + "/";
            sUriMatcher = new UriMatcher(-1);
            sUriMatcher.addURI(AUTHORITY, "uploads/#", 1);
            sUriMatcher.addURI(AUTHORITY, "uploads/upload/#", 2);
            sUriMatcher.addURI(AUTHORITY, "uploads/reference/#", 13);
            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_DELETE_UPLOAD, 4);
            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_NEW_UPLOAD, 6);
            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_HASH, 7);
            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_STATUS, 8);
            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_URIS, 9);
            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_POST_INFO, 10);
            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_UPLOAD_TIME, 11);
            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_VALUES, 12);
            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_MESSAGE_ROW_ID, 14);
                }

                @Override 
        public boolean onCreate() {
            return true;
                }

                @Override 
        public Cursor query(Uri uri, String[] projection, String selection, String[] selArgs, String sortOrder) {
                    SQLiteDatabase db;
                    try {
                db = VineUploadDatabaseHelper.getDatabaseHelper(getContext()).getReadableDatabase();
                    } catch (SQLiteException e) {
                CrashUtil.logException(e, "Failed to get a readable database on query.", new Object[0]);
                db = VineUploadDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
                    }
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            if (LOGGABLE) {
                Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 1:
                   String ownerId = uri.getLastPathSegment();
                   qb.setTables(VineUploads.Uploads.TABLE_NAME);
                   qb.appendWhere("owner_id=" + ownerId);
                            break;
                        case 2:
                   qb.setTables(VineUploads.Uploads.TABLE_NAME);
                   String ownerId2 = uri.getLastPathSegment();
                   String path = uri.getQueryParameter("path");
                   if (!TextUtils.isEmpty(path)) {
                       Cursor c = qb.query(db, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, "path=? AND owner_id=?", new String[]{path, ownerId2}, null, null, "_id ASC", "1");
                       return c;
                            }
                   return null;
                        case 13:
                   qb.setTables(VineUploads.Uploads.TABLE_NAME);
                   String ownerId3 = uri.getLastPathSegment();
                   String reference = uri.getQueryParameter("reference");
                   if (!TextUtils.isEmpty(reference)) {
                       Cursor c2 = qb.query(db, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, "reference=? AND owner_id=?", new String[]{reference, ownerId3}, null, null, "_id ASC");
                       return c2;
                            }
                   return null;
                        default:
                   qb.setTables(VineUploads.Uploads.TABLE_NAME);
                            break;
                    }
                    try {
                Cursor c3 = qb.query(db, projection, selection, selArgs, null, null, sortOrder);
               c3.setNotificationUri(getContext().getContentResolver(), uri);
               return c3;
                    } catch (Exception e2) {
               if (LOGGABLE) {
                   SLog.m453d("Cannot execute {} {}  {} {} {} {}", (Object[]) new String[]{db.toString(), qb.getTables(), selection, null, sortOrder});
                        }
               throw new RuntimeException(e2);
                    }
                }

                @Override 
       public String getType(Uri uri) {
           return null;
                }

                @Override 
       public Uri insert(Uri uri, ContentValues values) {
           long rowId = -1;
           SQLiteDatabase db = VineUploadDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
           if (LOGGABLE) {
               Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 6:
                   rowId = db.insert(VineUploads.Uploads.TABLE_NAME, null, values);
                   if (LOGGABLE) {
                       Log.d(TAG, "Upload inserted with rowId=" + rowId);
                            }
                            break;
                    }
                    if (rowId >= 0) {
               getContext().getContentResolver().notifyChange(VineUploads.Uploads.CONTENT_URI, null);
                    }
                    return null;
                }

                @Override 
       public int delete(Uri uri, String selection, String[] selectionArgs) {
           int rowsDeleted = 0;
           SQLiteDatabase db = VineUploadDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
           if (LOGGABLE) {
               Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 4:
                   rowsDeleted = db.delete(VineUploads.Uploads.TABLE_NAME, selection, selectionArgs);
                            break;
                        case 5:
                   rowsDeleted = db.delete(VineUploads.Uploads.TABLE_NAME, "1", null);
                            break;
                    }
           if (rowsDeleted > 0) {
               getContext().getContentResolver().notifyChange(VineUploads.Uploads.CONTENT_URI, null);
                    }
           return rowsDeleted;
                }

                @Override 
       public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
                    String tableName;
           int rowsUpdated = 0;
           if (values == null) {
               values = new ContentValues();
                    }
           SQLiteDatabase db = VineUploadDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
           if (LOGGABLE) {
               Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 7:
                   tableName = VineUploads.Uploads.TABLE_NAME;
                   String path = uri.getQueryParameter("path");
                   String hash = uri.getQueryParameter("hash");
                   SLog.m448d("PUT_HASH path={}, hash={}", path, hash);
                   selection = "path=?";
                   selectionArgs = new String[]{path};
                   values.put("hash", hash);
                            break;
                        case 8:
                   tableName = VineUploads.Uploads.TABLE_NAME;
                   String path2 = uri.getQueryParameter("path");
                   String status = uri.getQueryParameter("status");
                   String captcha = uri.getQueryParameter("captcha_url");
                   SLog.m448d("PUT_STATUS path={}, status={}", path2, status);
                   selection = "path=?";
                   selectionArgs = new String[]{path2};
                   values.put("status", status);
                   values.put("captcha_url", captcha);
                            break;
                        case 9:
                   tableName = VineUploads.Uploads.TABLE_NAME;
                   String path3 = uri.getQueryParameter("path");
                   String videoUrl = uri.getQueryParameter("video_url");
                   String thumbnailUrl = uri.getQueryParameter("thumbnail_url");
                   SLog.m449d("PUT_URIS path={}, videoUrl={}, thumbnailUrl={}", path3, videoUrl, thumbnailUrl);
                   selection = "path=?";
                   selectionArgs = new String[]{path3};
                   values.put("video_url", videoUrl);
                   values.put("thumbnail_url", thumbnailUrl);
                            break;
                        case 10:
                   tableName = VineUploads.Uploads.TABLE_NAME;
                   String path4 = uri.getQueryParameter("path");
                   String postInfo = uri.getQueryParameter("post_info");
                   SLog.m448d("PUT_POST_INFO path={}, postInfo={}", path4, postInfo);
                   selection = "path=?";
                   selectionArgs = new String[]{path4};
                   values.put("post_info", postInfo);
                            break;
                        case 11:
                   tableName = VineUploads.Uploads.TABLE_NAME;
                   String path5 = uri.getQueryParameter("path");
                   String uploadTime = uri.getQueryParameter("upload_time");
                   SLog.m448d("PUT_UPLOAD_TIME path={}, uploadTime={}", path5, uploadTime);
                   selection = "path=?";
                   selectionArgs = new String[]{path5};
                   values.put("upload_time", uploadTime);
                            break;
                        case 12:
                   SLog.m448d("PUT_VALUES selectionArgs={}, values={}", selectionArgs, values.toString());
                   tableName = VineUploads.Uploads.TABLE_NAME;
                            break;
                        case 13:
                        default:
                   tableName = VineUploads.Uploads.TABLE_NAME;
                            break;
                        case 14:
                   tableName = VineUploads.Uploads.TABLE_NAME;
                   String path6 = uri.getQueryParameter("path");
                   String messageId = uri.getQueryParameter(VineUploads.Uploads.QUERY_MESSAGE_ROW_ID);
                   SLog.m448d("PUT_MESSAGE_ROW_ID path={}, id={}", path6, messageId);
                   selection = "path=?";
                   selectionArgs = new String[]{path6};
                   values.put(VineUploads.UploadsColumns.MESSAGE_ROW_ID, messageId);
                            break;
                    }
                    try {
               if (!values.keySet().isEmpty()) {
                   rowsUpdated = db.update(tableName, values, selection, selectionArgs);
                        }
               if (rowsUpdated > 0 && String.valueOf(2).equals(uri.getQueryParameter("status"))) {
                   getContext().getContentResolver().notifyChange(VineUploads.Uploads.CONTENT_URI, null);
                        }
               return rowsUpdated;
                    } catch (SQLiteException e) {
               if (LOGGABLE) {
                   SLog.m453d("Cannot execute update with db={}, tablename={}, selection={}, selectionArgs={}", (Object[]) new String[]{db.toString(), tableName, selection, Arrays.toString(selectionArgs)});
                        }
               throw new RuntimeException(e);
                    }
                }
            }
