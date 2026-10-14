            package co.vine.android.provider;

            import android.content.Context;
            import android.database.sqlite.SQLiteDatabase;
            import android.database.sqlite.SQLiteException;
            import android.database.sqlite.SQLiteOpenHelper;
            import co.vine.android.client.AppController;
            import co.vine.android.util.CrashUtil;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class VineUploadDatabaseHelper extends SQLiteOpenHelper {
                public static final int DATABASE_VERSION = 4;
                private static final String DB_NAME = "upload";
                public static final int SCHEMA_VERSION = 1;
                private static final String SEPARATOR = "-";
                private static final HashMap<String, VineUploadDatabaseHelper> sHelperMap = new HashMap<>();
                private final Context mAppContext;

/* 32 */        public static synchronized VineUploadDatabaseHelper getDatabaseHelper(Context context) {
                    VineUploadDatabaseHelper helper;
                    try {
                        String databaseName = getDatabaseName(1);
/* 34 */                helper = sHelperMap.get(databaseName);
/* 35 */                if (helper == null) {
/* 36 */                    helper = new VineUploadDatabaseHelper(context.getApplicationContext(), databaseName);
/* 37 */                    sHelperMap.put(databaseName, helper);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return helper;
                }

/* 42 */        private VineUploadDatabaseHelper(Context context, String name) {
/* 43 */            super(context, name, (SQLiteDatabase.CursorFactory) null, 4);
/* 44 */            this.mAppContext = context.getApplicationContext();
                }

                @Override // android.database.sqlite.SQLiteOpenHelper
/* 48 */        public void onCreate(SQLiteDatabase db) {
/* 49 */            createTables(db);
                }

                @Override // android.database.sqlite.SQLiteOpenHelper
/* 53 */        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/* 54 */            int version = oldVersion;
/* 56 */            if (version == 1) {
                        try {
/* 57 */                    db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_REFERENCE_COLUMN);
/* 58 */                    db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_OWNER_ID_COLUMN);
/* 59 */                    db.execSQL("UPDATE uploads SET status=2 WHERE (status=0 OR status=1);");
/* 61 */                    long userId = AppController.getInstance(this.mAppContext).getActiveSessionReadOnly().getUserId();
/* 63 */                    db.execSQL("UPDATE uploads SET owner_id=" + userId + ";");
/* 65 */                    version = 2;
                        } catch (SQLiteException e) {
/* 81 */                    CrashUtil.logException(e);
/* 82 */                    db.execSQL("DROP TABLE IF EXISTS uploads;");
/* 83 */                    db.execSQL(VineUploadsDatabaseSQL.UPLOADS_TABLE_SQL);
/* 85 */                    return;
                        }
                    }
/* 68 */            if (version == 2) {
/* 69 */                db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_CAPTCHA_URL_COLUMN);
/* 70 */                version = 3;
                    }
/* 73 */            if (version == 3) {
/* 74 */                db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_PRIVATE_COLUMN);
/* 75 */                db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_CONVERSATION_ID_COLUMN);
/* 76 */                db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_MAX_LOOPS_COLUMN);
/* 77 */                db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_MESSAGE_ROW_ID_COLUMN);
                    }
                }

/* 87 */        protected void createTables(SQLiteDatabase db) {
/* 88 */            db.execSQL(VineUploadsDatabaseSQL.UPLOADS_TABLE_SQL);
                }

/* 91 */        public static String getDatabaseName(int schemaVersion) {
/* 92 */            return "upload-" + schemaVersion;
                }
            }
