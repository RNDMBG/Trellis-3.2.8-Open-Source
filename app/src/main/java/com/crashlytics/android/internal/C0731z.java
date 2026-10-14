            package com.crashlytics.android.internal;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.content.ContextWrapper;
            import android.database.DatabaseErrorHandler;
            import android.database.sqlite.SQLiteDatabase;
            import java.io.File;
            
            
            
    final class C0731z extends ContextWrapper {

                
                private final String f473a;

        public C0731z(Context context, String str) {
            super(context);
            this.f473a = ".TwitterSdk/" + str;
                }

                @Override 
        public final File getDatabasePath(String name) {
            File file = new File(super.getDatabasePath(name).getParentFile(), this.f473a);
            file.mkdirs();
            return new File(file, name);
                }

                @Override 
        public final SQLiteDatabase openOrCreateDatabase(String name, int i, SQLiteDatabase.CursorFactory factory) {
            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
                }

                @Override 
                @TargetApi(11)
        public final SQLiteDatabase openOrCreateDatabase(String name, int i, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name).getPath(), factory, errorHandler);
                }

                @Override 
        public final File getFilesDir() {
            return new File(super.getFilesDir(), this.f473a);
                }

                @Override 
        public final File getExternalFilesDir(String type) {
            return new File(super.getExternalFilesDir(type), this.f473a);
                }

                @Override 
        public final File getCacheDir() {
            return new File(super.getCacheDir(), this.f473a);
                }

                @Override 
        public final File getExternalCacheDir() {
            return new File(super.getExternalCacheDir(), this.f473a);
                }
            }
