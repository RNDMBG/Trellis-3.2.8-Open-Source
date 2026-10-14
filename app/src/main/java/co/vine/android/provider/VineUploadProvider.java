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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class VineUploadProvider extends ContentProvider {
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
/* 31 */            LOGGABLE = BuildUtil.isLogsOn() || Log.isLoggable(TAG, 3);
/* 34 */            AUTHORITY = BuildUtil.getAuthority(".provider.VineUploadProvider");
/* 35 */            CONTENT_AUTHORITY = "content://" + AUTHORITY + "/";
/* 52 */            sUriMatcher = new UriMatcher(-1);
/* 56 */            sUriMatcher.addURI(AUTHORITY, "uploads/#", 1);
/* 57 */            sUriMatcher.addURI(AUTHORITY, "uploads/upload/#", 2);
/* 58 */            sUriMatcher.addURI(AUTHORITY, "uploads/reference/#", 13);
/* 61 */            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_DELETE_UPLOAD, 4);
/* 64 */            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_NEW_UPLOAD, 6);
/* 65 */            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_HASH, 7);
/* 66 */            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_STATUS, 8);
/* 67 */            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_URIS, 9);
/* 68 */            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_POST_INFO, 10);
/* 69 */            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_UPLOAD_TIME, 11);
/* 70 */            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_VALUES, 12);
/* 71 */            sUriMatcher.addURI(AUTHORITY, VineUploads.Uploads.PATH_PUT_MESSAGE_ROW_ID, 14);
                }

                @Override // android.content.ContentProvider
/* 75 */        public boolean onCreate() {
/* 76 */            return true;
                }

                @Override // android.content.ContentProvider
/* 82 */        public Cursor query(Uri uri, String[] projection, String selection, String[] selArgs, String sortOrder) {
                    SQLiteDatabase db;
                    try {
/* 87 */                db = VineUploadDatabaseHelper.getDatabaseHelper(getContext()).getReadableDatabase();
                    } catch (SQLiteException e) {
/* 90 */                CrashUtil.logException(e, "Failed to get a readable database on query.", new Object[0]);
/* 91 */                db = VineUploadDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
                    }
/* 95 */            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
/* 96 */            if (LOGGABLE) {
/* 97 */                Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
/* 100 */           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 1:
/* 103 */                   String ownerId = uri.getLastPathSegment();
/* 104 */                   qb.setTables(VineUploads.Uploads.TABLE_NAME);
/* 105 */                   qb.appendWhere("owner_id=" + ownerId);
                            break;
                        case 2:
/* 109 */                   qb.setTables(VineUploads.Uploads.TABLE_NAME);
/* 110 */                   String ownerId2 = uri.getLastPathSegment();
/* 111 */                   String path = uri.getQueryParameter("path");
/* 112 */                   if (!TextUtils.isEmpty(path)) {
/* 113 */                       Cursor c = qb.query(db, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, "path=? AND owner_id=?", new String[]{path, ownerId2}, null, null, "_id ASC", "1");
/* 144 */                       return c;
                            }
/* 118 */                   return null;
                        case 13:
/* 121 */                   qb.setTables(VineUploads.Uploads.TABLE_NAME);
/* 122 */                   String ownerId3 = uri.getLastPathSegment();
/* 123 */                   String reference = uri.getQueryParameter("reference");
/* 124 */                   if (!TextUtils.isEmpty(reference)) {
/* 125 */                       Cursor c2 = qb.query(db, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, "reference=? AND owner_id=?", new String[]{reference, ownerId3}, null, null, "_id ASC");
/* 144 */                       return c2;
                            }
/* 131 */                   return null;
                        default:
/* 134 */                   qb.setTables(VineUploads.Uploads.TABLE_NAME);
                            break;
                    }
                    try {
/* 83 */                Cursor c3 = qb.query(db, projection, selection, selArgs, null, null, sortOrder);
/* 143 */               c3.setNotificationUri(getContext().getContentResolver(), uri);
/* 144 */               return c3;
                    } catch (Exception e2) {
/* 146 */               if (LOGGABLE) {
/* 147 */                   SLog.m453d("Cannot execute {} {}  {} {} {} {}", (Object[]) new String[]{db.toString(), qb.getTables(), selection, null, sortOrder});
                        }
/* 150 */               throw new RuntimeException(e2);
                    }
                }

                @Override // android.content.ContentProvider
/* 155 */       public String getType(Uri uri) {
/* 156 */           return null;
                }

                @Override // android.content.ContentProvider
/* 160 */       public Uri insert(Uri uri, ContentValues values) {
/* 161 */           long rowId = -1;
/* 162 */           SQLiteDatabase db = VineUploadDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
/* 165 */           if (LOGGABLE) {
/* 166 */               Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
/* 169 */           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 6:
/* 172 */                   rowId = db.insert(VineUploads.Uploads.TABLE_NAME, null, values);
/* 173 */                   if (LOGGABLE) {
/* 174 */                       Log.d(TAG, "Upload inserted with rowId=" + rowId);
                            }
                            break;
                    }
                    if (rowId >= 0) {
/* 184 */               getContext().getContentResolver().notifyChange(VineUploads.Uploads.CONTENT_URI, null);
                    }
                    return null;
                }

                @Override // android.content.ContentProvider
/* 190 */       public int delete(Uri uri, String selection, String[] selectionArgs) {
/* 191 */           int rowsDeleted = 0;
/* 192 */           SQLiteDatabase db = VineUploadDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
/* 195 */           if (LOGGABLE) {
/* 196 */               Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
/* 199 */           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 4:
/* 202 */                   rowsDeleted = db.delete(VineUploads.Uploads.TABLE_NAME, selection, selectionArgs);
                            break;
                        case 5:
/* 206 */                   rowsDeleted = db.delete(VineUploads.Uploads.TABLE_NAME, "1", null);
                            break;
                    }
/* 214 */           if (rowsDeleted > 0) {
/* 215 */               getContext().getContentResolver().notifyChange(VineUploads.Uploads.CONTENT_URI, null);
                    }
/* 217 */           return rowsDeleted;
                }

                @Override // android.content.ContentProvider
/* 221 */       public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
                    String tableName;
/* 222 */           int rowsUpdated = 0;
/* 224 */           if (values == null) {
/* 225 */               values = new ContentValues();
                    }
/* 228 */           SQLiteDatabase db = VineUploadDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
/* 231 */           if (LOGGABLE) {
/* 232 */               Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
/* 235 */           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 7:
/* 238 */                   tableName = VineUploads.Uploads.TABLE_NAME;
/* 239 */                   String path = uri.getQueryParameter("path");
/* 240 */                   String hash = uri.getQueryParameter("hash");
/* 241 */                   SLog.m448d("PUT_HASH path={}, hash={}", path, hash);
/* 242 */                   selection = "path=?";
/* 243 */                   selectionArgs = new String[]{path};
/* 244 */                   values.put("hash", hash);
                            break;
                        case 8:
/* 248 */                   tableName = VineUploads.Uploads.TABLE_NAME;
/* 249 */                   String path2 = uri.getQueryParameter("path");
/* 250 */                   String status = uri.getQueryParameter("status");
/* 251 */                   String captcha = uri.getQueryParameter("captcha_url");
/* 252 */                   SLog.m448d("PUT_STATUS path={}, status={}", path2, status);
/* 253 */                   selection = "path=?";
/* 254 */                   selectionArgs = new String[]{path2};
/* 255 */                   values.put("status", status);
/* 256 */                   values.put("captcha_url", captcha);
                            break;
                        case 9:
/* 260 */                   tableName = VineUploads.Uploads.TABLE_NAME;
/* 261 */                   String path3 = uri.getQueryParameter("path");
/* 262 */                   String videoUrl = uri.getQueryParameter("video_url");
/* 263 */                   String thumbnailUrl = uri.getQueryParameter("thumbnail_url");
/* 264 */                   SLog.m449d("PUT_URIS path={}, videoUrl={}, thumbnailUrl={}", path3, videoUrl, thumbnailUrl);
/* 265 */                   selection = "path=?";
/* 266 */                   selectionArgs = new String[]{path3};
/* 267 */                   values.put("video_url", videoUrl);
/* 268 */                   values.put("thumbnail_url", thumbnailUrl);
                            break;
                        case 10:
/* 272 */                   tableName = VineUploads.Uploads.TABLE_NAME;
/* 273 */                   String path4 = uri.getQueryParameter("path");
/* 274 */                   String postInfo = uri.getQueryParameter("post_info");
/* 275 */                   SLog.m448d("PUT_POST_INFO path={}, postInfo={}", path4, postInfo);
/* 276 */                   selection = "path=?";
/* 277 */                   selectionArgs = new String[]{path4};
/* 278 */                   values.put("post_info", postInfo);
                            break;
                        case 11:
/* 282 */                   tableName = VineUploads.Uploads.TABLE_NAME;
/* 283 */                   String path5 = uri.getQueryParameter("path");
/* 284 */                   String uploadTime = uri.getQueryParameter("upload_time");
/* 285 */                   SLog.m448d("PUT_UPLOAD_TIME path={}, uploadTime={}", path5, uploadTime);
/* 286 */                   selection = "path=?";
/* 287 */                   selectionArgs = new String[]{path5};
/* 288 */                   values.put("upload_time", uploadTime);
                            break;
                        case 12:
/* 292 */                   SLog.m448d("PUT_VALUES selectionArgs={}, values={}", selectionArgs, values.toString());
/* 293 */                   tableName = VineUploads.Uploads.TABLE_NAME;
                            break;
                        case 13:
                        default:
/* 307 */                   tableName = VineUploads.Uploads.TABLE_NAME;
                            break;
                        case 14:
/* 297 */                   tableName = VineUploads.Uploads.TABLE_NAME;
/* 298 */                   String path6 = uri.getQueryParameter("path");
/* 299 */                   String messageId = uri.getQueryParameter(VineUploads.Uploads.QUERY_MESSAGE_ROW_ID);
/* 300 */                   SLog.m448d("PUT_MESSAGE_ROW_ID path={}, id={}", path6, messageId);
/* 301 */                   selection = "path=?";
/* 302 */                   selectionArgs = new String[]{path6};
/* 303 */                   values.put(VineUploads.UploadsColumns.MESSAGE_ROW_ID, messageId);
                            break;
                    }
                    try {
/* 313 */               if (!values.keySet().isEmpty()) {
/* 314 */                   rowsUpdated = db.update(tableName, values, selection, selectionArgs);
                        }
/* 324 */               if (rowsUpdated > 0 && String.valueOf(2).equals(uri.getQueryParameter("status"))) {
/* 327 */                   getContext().getContentResolver().notifyChange(VineUploads.Uploads.CONTENT_URI, null);
                        }
/* 331 */               return rowsUpdated;
                    } catch (SQLiteException e) {
/* 317 */               if (LOGGABLE) {
/* 318 */                   SLog.m453d("Cannot execute update with db={}, tablename={}, selection={}, selectionArgs={}", (Object[]) new String[]{db.toString(), tableName, selection, Arrays.toString(selectionArgs)});
                        }
/* 321 */               throw new RuntimeException(e);
                    }
                }
            }
