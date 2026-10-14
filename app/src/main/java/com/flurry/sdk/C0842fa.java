            package com.flurry.sdk;

            import android.content.Context;
            import android.os.Build;
            import android.os.Environment;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            
            /* JADX INFO: renamed from: com.flurry.sdk.fa */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public final class C0842fa {

                /* JADX INFO: renamed from: a */
                private static String f847a = C0842fa.class.getSimpleName();

                /* JADX INFO: renamed from: a */
/* 23 */        public static File m952a(boolean z) {
                    File externalFilesDir = null;
/* 24 */            Context contextM736b = C0801dl.m732a().m736b();
/* 29 */            if (z && "mounted".equals(Environment.getExternalStorageState()) && (Build.VERSION.SDK_INT >= 19 || contextM736b.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
/* 34 */                externalFilesDir = contextM736b.getExternalFilesDir(null);
                    }
/* 39 */            if (externalFilesDir == null) {
/* 40 */                return contextM736b.getFilesDir();
                    }
/* 43 */            return externalFilesDir;
                }

                /* JADX INFO: renamed from: b */
/* 46 */        public static File m955b(boolean z) {
/* 47 */            Context contextM736b = C0801dl.m732a().m736b();
/* 49 */            File externalCacheDir = null;
/* 52 */            if (z && "mounted".equals(Environment.getExternalStorageState()) && (Build.VERSION.SDK_INT >= 19 || contextM736b.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
/* 57 */                externalCacheDir = contextM736b.getExternalCacheDir();
                    }
/* 62 */            if (externalCacheDir == null) {
/* 63 */                return contextM736b.getCacheDir();
                    }
/* 66 */            return externalCacheDir;
                }

                /* JADX INFO: renamed from: a */
/* 74 */        public static boolean m954a(File file) {
/* 75 */            if (file == null || file.getAbsoluteFile() == null) {
                        return false;
                    }
/* 84 */            File parentFile = file.getParentFile();
/* 85 */            if (parentFile == null) {
                        return true;
                    }
/* 91 */            if (parentFile.mkdirs() || parentFile.isDirectory()) {
                        return true;
                    }
/* 92 */            C0827el.m885a(6, f847a, "Unable to create persistent dir: " + parentFile);
                    return false;
                }

                /* JADX INFO: renamed from: b */
/* 99 */        public static boolean m956b(File file) {
/* 100 */           if (file != null && file.isDirectory()) {
/* 103 */               for (String str : file.list()) {
/* 105 */                   if (!m956b(new File(file, str))) {
                                return false;
                            }
                        }
                    }
/* 112 */           return file.delete();
                }

                @Deprecated
                /* JADX INFO: renamed from: c */
/* 116 */       public static String m957c(File file) throws Throwable {
                    FileInputStream fileInputStream;
                    Throwable th;
                    StringBuilder sb;
/* 117 */           if (file == null || !file.exists()) {
/* 118 */               C0827el.m885a(4, f847a, "Persistent file doesn't exist.");
                        return null;
                    }
/* 122 */           C0827el.m885a(4, f847a, "Loading persistent data: " + file.getAbsolutePath());
                    try {
/* 128 */               fileInputStream = new FileInputStream(file);
                        try {
                            try {
/* 130 */                       sb = new StringBuilder();
/* 131 */                       byte[] bArr = new byte[1024];
                                while (true) {
/* 133 */                           int i = fileInputStream.read(bArr);
                                    if (i <= 0) {
                                        break;
                                    }
/* 134 */                           sb.append(new String(bArr, 0, i));
                                }
/* 140 */                       C0843fb.m962a(fileInputStream);
                            } catch (Throwable th2) {
/* 136 */                       th = th2;
/* 137 */                       C0827el.m886a(6, f847a, "Error when loading persistent file", th);
/* 140 */                       C0843fb.m962a(fileInputStream);
                                sb = null;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            C0843fb.m962a(fileInputStream);
                            throw th;
                        }
                    } catch (Throwable th4) {
                        fileInputStream = null;
                        th = th4;
                        C0843fb.m962a(fileInputStream);
                        throw th;
                    }
/* 143 */           if (sb != null) {
/* 144 */               return sb.toString();
                    }
                    return null;
                }

                @Deprecated
                /* JADX INFO: renamed from: a */
/* 151 */       public static void m953a(File file, String str) throws Throwable {
                    FileOutputStream fileOutputStream;
/* 152 */           if (file == null) {
/* 153 */               C0827el.m885a(4, f847a, "No persistent file specified.");
/* 174 */               return;
                    }
/* 157 */           if (str == null) {
/* 158 */               C0827el.m885a(4, f847a, "No data specified; deleting persistent file: " + file.getAbsolutePath());
/* 159 */               file.delete();
/* 174 */               return;
                    }
/* 163 */           C0827el.m885a(4, f847a, "Writing persistent data: " + file.getAbsolutePath());
                    try {
/* 167 */               fileOutputStream = new FileOutputStream(file);
                        try {
                            try {
/* 168 */                       fileOutputStream.write(str.getBytes());
/* 172 */                       C0843fb.m962a(fileOutputStream);
                            } catch (Throwable th) {
/* 169 */                       th = th;
/* 170 */                       C0827el.m886a(6, f847a, "Error writing persistent file", th);
/* 172 */                       C0843fb.m962a(fileOutputStream);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            C0843fb.m962a(fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
/* 165 */               fileOutputStream = null;
                        C0843fb.m962a(fileOutputStream);
                        throw th;
                    }
                }
            }
