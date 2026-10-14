            package com.flurry.sdk;

            import android.content.Context;
            import android.os.Build;
            import android.os.Environment;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            
            
            
    public final class C0842fa {

                
                private static String f847a = C0842fa.class.getSimpleName();

                
        public static File m952a(boolean z) {
                    File externalFilesDir = null;
            Context contextM736b = C0801dl.m732a().m736b();
            if (z && "mounted".equals(Environment.getExternalStorageState()) && (Build.VERSION.SDK_INT >= 19 || contextM736b.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
                externalFilesDir = contextM736b.getExternalFilesDir(null);
                    }
            if (externalFilesDir == null) {
                return contextM736b.getFilesDir();
                    }
            return externalFilesDir;
                }

                
        public static File m955b(boolean z) {
            Context contextM736b = C0801dl.m732a().m736b();
            File externalCacheDir = null;
            if (z && "mounted".equals(Environment.getExternalStorageState()) && (Build.VERSION.SDK_INT >= 19 || contextM736b.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
                externalCacheDir = contextM736b.getExternalCacheDir();
                    }
            if (externalCacheDir == null) {
                return contextM736b.getCacheDir();
                    }
            return externalCacheDir;
                }

                
        public static boolean m954a(File file) {
            if (file == null || file.getAbsoluteFile() == null) {
                        return false;
                    }
            File parentFile = file.getParentFile();
            if (parentFile == null) {
                        return true;
                    }
            if (parentFile.mkdirs() || parentFile.isDirectory()) {
                        return true;
                    }
            C0827el.m885a(6, f847a, "Unable to create persistent dir: " + parentFile);
                    return false;
                }

                
        public static boolean m956b(File file) {
           if (file != null && file.isDirectory()) {
               for (String str : file.list()) {
                   if (!m956b(new File(file, str))) {
                                return false;
                            }
                        }
                    }
           return file.delete();
                }

                @Deprecated
                
       public static String m957c(File file) throws Throwable {
                    FileInputStream fileInputStream;
                    Throwable th;
                    StringBuilder sb;
           if (file == null || !file.exists()) {
               C0827el.m885a(4, f847a, "Persistent file doesn't exist.");
                        return null;
                    }
           C0827el.m885a(4, f847a, "Loading persistent data: " + file.getAbsolutePath());
                    try {
               fileInputStream = new FileInputStream(file);
                        try {
                            try {
                       sb = new StringBuilder();
                       byte[] bArr = new byte[1024];
                                while (true) {
                           int i = fileInputStream.read(bArr);
                                    if (i <= 0) {
                                        break;
                                    }
                           sb.append(new String(bArr, 0, i));
                                }
                       C0843fb.m962a(fileInputStream);
                            } catch (Throwable th2) {
                       th = th2;
                       C0827el.m886a(6, f847a, "Error when loading persistent file", th);
                       C0843fb.m962a(fileInputStream);
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
           if (sb != null) {
               return sb.toString();
                    }
                    return null;
                }

                @Deprecated
                
       public static void m953a(File file, String str) throws Throwable {
                    FileOutputStream fileOutputStream;
           if (file == null) {
               C0827el.m885a(4, f847a, "No persistent file specified.");
               return;
                    }
           if (str == null) {
               C0827el.m885a(4, f847a, "No data specified; deleting persistent file: " + file.getAbsolutePath());
               file.delete();
               return;
                    }
           C0827el.m885a(4, f847a, "Writing persistent data: " + file.getAbsolutePath());
                    try {
               fileOutputStream = new FileOutputStream(file);
                        try {
                            try {
                       fileOutputStream.write(str.getBytes());
                       C0843fb.m962a(fileOutputStream);
                            } catch (Throwable th) {
                       th = th;
                       C0827el.m886a(6, f847a, "Error writing persistent file", th);
                       C0843fb.m962a(fileOutputStream);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            C0843fb.m962a(fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
               fileOutputStream = null;
                        C0843fb.m962a(fileOutputStream);
                        throw th;
                    }
                }
            }
