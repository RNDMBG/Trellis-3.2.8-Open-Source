            package co.vine.android.provider;

            import android.content.Context;
            import android.database.sqlite.SQLiteDatabase;
            import android.database.sqlite.SQLiteException;
            import android.database.sqlite.SQLiteOpenHelper;
            import co.vine.android.client.AppController;
            import co.vine.android.util.CrashUtil;
            import java.util.HashMap;
            
            
    public class VineUploadDatabaseHelper extends SQLiteOpenHelper {
                public static final int DATABASE_VERSION = 4;
                private static final String DB_NAME = "upload";
                public static final int SCHEMA_VERSION = 1;
                private static final String SEPARATOR = "-";
                private static final HashMap<String, VineUploadDatabaseHelper> sHelperMap = new HashMap<>();
                private final Context mAppContext;

        public static synchronized VineUploadDatabaseHelper getDatabaseHelper(Context context) {
                    VineUploadDatabaseHelper helper;
                    try {
                        String databaseName = getDatabaseName(1);
                helper = sHelperMap.get(databaseName);
                if (helper == null) {
                    helper = new VineUploadDatabaseHelper(context.getApplicationContext(), databaseName);
                    sHelperMap.put(databaseName, helper);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return helper;
                }

        private VineUploadDatabaseHelper(Context context, String name) {
            super(context, name, (SQLiteDatabase.CursorFactory) null, 4);
            this.mAppContext = context.getApplicationContext();
                }

                @Override 
        public void onCreate(SQLiteDatabase db) {
            createTables(db);
                }

                @Override 
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            int version = oldVersion;
            if (version == 1) {
                        try {
                    db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_REFERENCE_COLUMN);
                    db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_OWNER_ID_COLUMN);
                    db.execSQL("UPDATE uploads SET status=2 WHERE (status=0 OR status=1);");
                    long userId = AppController.getInstance(this.mAppContext).getActiveSessionReadOnly().getUserId();
                    db.execSQL("UPDATE uploads SET owner_id=" + userId + ";");
                    version = 2;
                        } catch (SQLiteException e) {
                    CrashUtil.logException(e);
                    db.execSQL("DROP TABLE IF EXISTS uploads;");
                    db.execSQL(VineUploadsDatabaseSQL.UPLOADS_TABLE_SQL);
                    return;
                        }
                    }
            if (version == 2) {
                db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_CAPTCHA_URL_COLUMN);
                version = 3;
                    }
            if (version == 3) {
                db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_PRIVATE_COLUMN);
                db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_CONVERSATION_ID_COLUMN);
                db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_MAX_LOOPS_COLUMN);
                db.execSQL(VineUploadsDatabaseSQL.UPLOADS_ADD_MESSAGE_ROW_ID_COLUMN);
                    }
                }

        protected void createTables(SQLiteDatabase db) {
            db.execSQL(VineUploadsDatabaseSQL.UPLOADS_TABLE_SQL);
                }

        public static String getDatabaseName(int schemaVersion) {
            return "upload-" + schemaVersion;
                }
            }
