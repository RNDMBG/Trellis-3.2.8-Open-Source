            package com.flurry.sdk;

            import android.os.Looper;
            import android.provider.Settings;
            import android.text.TextUtils;
            import java.io.Closeable;
            import java.io.DataInput;
            import java.io.DataInputStream;
            import java.io.DataOutput;
            import java.io.DataOutputStream;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            import java.io.FilenameFilter;
            import java.io.IOException;
            import java.util.Collections;
            import java.util.HashSet;
            import java.util.Locale;
            import java.util.Set;
            
            
            
    public final class C0813dx {

                
                private static final String f749a = C0813dx.class.getSimpleName();

                
                private static final Set<String> f750b = m811i();

                
                private static String f751c;

                
        public static synchronized String m796a() {
                    try {
                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                    throw new IllegalStateException("Must be called from a background thread!");
                        }
                if (TextUtils.isEmpty(f751c)) {
                    f751c = m809g();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f751c;
                }

                
        private static String m809g() {
            String strM801b = m801b();
                    return !TextUtils.isEmpty(strM801b) ? strM801b : m804c();
                }

                
        static String m801b() {
            String string = Settings.Secure.getString(C0801dl.m732a().m736b().getContentResolver(), "android_id");
            if (m800a(string)) {
                        return "AND" + string;
                    }
            return null;
                }

                
        static String m804c() throws Throwable {
            String strM807e = m807e();
            if (TextUtils.isEmpty(strM807e)) {
                strM807e = m808f();
                if (TextUtils.isEmpty(strM807e)) {
                   strM807e = m806d();
                        }
               m803b(strM807e);
                    }
           return strM807e;
                }

                
       static boolean m800a(String str) {
                    return (TextUtils.isEmpty(str) || m805c(str.toLowerCase(Locale.US))) ? false : true;
                }

                
       static String m806d() {
           return "ID" + Long.toString(Double.doubleToLongBits(Math.random()) + (37 * (System.nanoTime() + ((long) (C0810du.m775c(C0801dl.m732a().m736b()).hashCode() * 37)))), 16);
                }

                
       static void m803b(String str) throws Throwable {
           if (!TextUtils.isEmpty(str)) {
               File fileStreamPath = C0801dl.m732a().m736b().getFileStreamPath(m810h());
               if (C0842fa.m954a(fileStreamPath)) {
                   m799a(str, fileStreamPath);
                        }
                    }
                }

                
       static void m799a(String str, File file) throws Throwable {
                    DataOutputStream dataOutputStream;
                    try {
                        try {
                   dataOutputStream = new DataOutputStream(new FileOutputStream(file));
                            try {
                       m798a(str, dataOutputStream);
                       C0843fb.m962a(dataOutputStream);
                            } catch (Throwable th) {
                       th = th;
                       C0827el.m886a(6, f749a, "Error when saving phoneId", th);
                       C0843fb.m962a(dataOutputStream);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            C0843fb.m962a(dataOutputStream);
                            throw th;
                        }
                    } catch (Throwable th3) {
               th = th3;
               dataOutputStream = null;
                    }
                }

                
                
                
                
                
                
                
                
                
       static String m807e() throws Throwable {
                    File fileStreamPath = C0801dl.m732a().m736b().getFileStreamPath(m810h());
                    if (fileStreamPath == null || !fileStreamPath.exists()) {
                        return null;
                    }
                    DataInputStream dataInputStream = null;
                    try {
                        dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                        return m797a(dataInputStream);
                    } catch (Throwable th) {
                        C0827el.m886a(6, f749a, "Error when loading phoneId", th);
                        return null;
                    } finally {
                        C0843fb.m962a(dataInputStream);
                    }
                }

                
       static String m808f() throws Throwable {
                    String[] list;
                    DataInputStream dataInputStream;
                    Throwable th;
                    String strM802b = null;
           File filesDir = C0801dl.m732a().m736b().getFilesDir();
           if (filesDir != null && (list = filesDir.list(new AnonymousClass1())) != null && list.length != 0) {
               File fileStreamPath = C0801dl.m732a().m736b().getFileStreamPath(list[0]);
               if (fileStreamPath != null && fileStreamPath.exists()) {
                            try {
                       dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                                try {
                                    try {
                               strM802b = m802b(dataInputStream);
                               C0843fb.m962a(dataInputStream);
                                    } catch (Throwable th2) {
                               th = th2;
                               C0827el.m886a(6, f749a, "Error when loading phoneId", th);
                               C0843fb.m962a(dataInputStream);
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    C0843fb.m962a(dataInputStream);
                                    throw th;
                                }
                            } catch (Throwable th4) {
                       th = th4;
                                dataInputStream = null;
                            }
                        }
                    }
           return strM802b;
                }

                
       static class AnonymousClass1 implements FilenameFilter {
           AnonymousClass1() {
                    }

                    @Override 
           public boolean accept(File file, String str) {
               return str.startsWith(".flurryagent.");
                    }
                }

                
       private static void m798a(String str, DataOutput dataOutput) throws IOException {
           dataOutput.writeInt(1);
           dataOutput.writeUTF(str);
                }

                
       private static String m797a(DataInput dataInput) throws IOException {
           if (1 != dataInput.readInt()) {
               return null;
                    }
                    return dataInput.readUTF();
                }

                
       private static String m802b(DataInput dataInput) throws IOException {
           if (46586 != dataInput.readUnsignedShort() || 2 != dataInput.readUnsignedShort()) {
                        return null;
                    }
           dataInput.readUTF();
           return dataInput.readUTF();
                }

                
       private static String m810h() {
           return ".flurryb.";
                }

                
       private static boolean m805c(String str) {
           return f750b.contains(str);
                }

                
       private static Set<String> m811i() {
           HashSet hashSet = new HashSet();
           hashSet.add("null");
           hashSet.add("9774d56d682e549c");
           hashSet.add("dead00beef");
           return Collections.unmodifiableSet(hashSet);
                }
            }
