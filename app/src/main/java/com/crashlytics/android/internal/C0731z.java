            package com.crashlytics.android.internal;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.content.ContextWrapper;
            import android.database.DatabaseErrorHandler;
            import android.database.sqlite.SQLiteDatabase;
            import java.io.File;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.z */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    final class C0731z extends ContextWrapper {

                /* JADX INFO: renamed from: a */
                private final String f473a;

/* 18 */        public C0731z(Context context, String str) {
/* 19 */            super(context);
/* 20 */            this.f473a = ".TwitterSdk/" + str;
                }

                @Override // android.content.ContextWrapper, android.content.Context
/* 25 */        public final File getDatabasePath(String name) {
/* 26 */            File file = new File(super.getDatabasePath(name).getParentFile(), this.f473a);
/* 28 */            file.mkdirs();
/* 30 */            return new File(file, name);
                }

                @Override // android.content.ContextWrapper, android.content.Context
/* 35 */        public final SQLiteDatabase openOrCreateDatabase(String name, int i, SQLiteDatabase.CursorFactory factory) {
/* 36 */            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
                }

                @Override // android.content.ContextWrapper, android.content.Context
                @TargetApi(11)
/* 43 */        public final SQLiteDatabase openOrCreateDatabase(String name, int i, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
/* 44 */            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name).getPath(), factory, errorHandler);
                }

                @Override // android.content.ContextWrapper, android.content.Context
/* 49 */        public final File getFilesDir() {
/* 50 */            return new File(super.getFilesDir(), this.f473a);
                }

                @Override // android.content.ContextWrapper, android.content.Context
/* 54 */        public final File getExternalFilesDir(String type) {
/* 55 */            return new File(super.getExternalFilesDir(type), this.f473a);
                }

                @Override // android.content.ContextWrapper, android.content.Context
/* 59 */        public final File getCacheDir() {
/* 60 */            return new File(super.getCacheDir(), this.f473a);
                }

                @Override // android.content.ContextWrapper, android.content.Context
/* 64 */        public final File getExternalCacheDir() {
/* 65 */            return new File(super.getExternalCacheDir(), this.f473a);
                }
            }
