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
            
            /* JADX INFO: renamed from: com.flurry.sdk.dx */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 62 */    public final class C0813dx {

                /* JADX INFO: renamed from: a */
                private static final String f749a = C0813dx.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private static final Set<String> f750b = m811i();

                /* JADX INFO: renamed from: c */
                private static String f751c;

                /* JADX INFO: renamed from: a */
/* 63 */        public static synchronized String m796a() {
                    try {
                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
/* 65 */                    throw new IllegalStateException("Must be called from a background thread!");
                        }
/* 68 */                if (TextUtils.isEmpty(f751c)) {
/* 69 */                    f751c = m809g();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 72 */            return f751c;
                }

                /* JADX INFO: renamed from: g */
/* 75 */        private static String m809g() {
/* 76 */            String strM801b = m801b();
                    return !TextUtils.isEmpty(strM801b) ? strM801b : m804c();
                }

                /* JADX INFO: renamed from: b */
/* 84 */        static String m801b() {
/* 85 */            String string = Settings.Secure.getString(C0801dl.m732a().m736b().getContentResolver(), "android_id");
/* 87 */            if (m800a(string)) {
                        return "AND" + string;
                    }
/* 88 */            return null;
                }

                /* JADX INFO: renamed from: c */
/* 94 */        static String m804c() throws Throwable {
/* 95 */            String strM807e = m807e();
/* 96 */            if (TextUtils.isEmpty(strM807e)) {
/* 98 */                strM807e = m808f();
/* 99 */                if (TextUtils.isEmpty(strM807e)) {
/* 100 */                   strM807e = m806d();
                        }
/* 102 */               m803b(strM807e);
                    }
/* 104 */           return strM807e;
                }

                /* JADX INFO: renamed from: a */
/* 107 */       static boolean m800a(String str) {
                    return (TextUtils.isEmpty(str) || m805c(str.toLowerCase(Locale.US))) ? false : true;
                }

                /* JADX INFO: renamed from: d */
/* 119 */       static String m806d() {
/* 122 */           return "ID" + Long.toString(Double.doubleToLongBits(Math.random()) + (37 * (System.nanoTime() + ((long) (C0810du.m775c(C0801dl.m732a().m736b()).hashCode() * 37)))), 16);
                }

                /* JADX INFO: renamed from: b */
/* 125 */       static void m803b(String str) throws Throwable {
/* 126 */           if (!TextUtils.isEmpty(str)) {
/* 130 */               File fileStreamPath = C0801dl.m732a().m736b().getFileStreamPath(m810h());
/* 132 */               if (C0842fa.m954a(fileStreamPath)) {
/* 136 */                   m799a(str, fileStreamPath);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 139 */       static void m799a(String str, File file) throws Throwable {
                    DataOutputStream dataOutputStream;
                    try {
                        try {
/* 142 */                   dataOutputStream = new DataOutputStream(new FileOutputStream(file));
                            try {
/* 143 */                       m798a(str, dataOutputStream);
/* 147 */                       C0843fb.m962a(dataOutputStream);
                            } catch (Throwable th) {
/* 144 */                       th = th;
/* 145 */                       C0827el.m886a(6, f749a, "Error when saving phoneId", th);
/* 147 */                       C0843fb.m962a(dataOutputStream);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            C0843fb.m962a(dataOutputStream);
                            throw th;
                        }
                    } catch (Throwable th3) {
/* 144 */               th = th3;
/* 140 */               dataOutputStream = null;
                    }
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r2v1, types: [boolean] */
                /* JADX WARN: Type inference failed for: r2v10 */
                /* JADX WARN: Type inference failed for: r2v2 */
                /* JADX WARN: Type inference failed for: r2v3, types: [java.io.Closeable] */
                /* JADX WARN: Type inference failed for: r2v4 */
                /* JADX WARN: Type inference failed for: r2v7 */
                /* JADX WARN: Type inference failed for: r2v9 */
                /* JADX INFO: renamed from: e */
/* 152 */       static String m807e() throws Throwable {
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

                /* JADX INFO: renamed from: f */
/* 171 */       static String m808f() throws Throwable {
                    String[] list;
                    DataInputStream dataInputStream;
                    Throwable th;
                    String strM802b = null;
/* 172 */           File filesDir = C0801dl.m732a().m736b().getFilesDir();
/* 173 */           if (filesDir != null && (list = filesDir.list(new AnonymousClass1())) != null && list.length != 0) {
/* 190 */               File fileStreamPath = C0801dl.m732a().m736b().getFileStreamPath(list[0]);
/* 191 */               if (fileStreamPath != null && fileStreamPath.exists()) {
                            try {
/* 198 */                       dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                                try {
                                    try {
/* 199 */                               strM802b = m802b(dataInputStream);
/* 203 */                               C0843fb.m962a(dataInputStream);
                                    } catch (Throwable th2) {
/* 200 */                               th = th2;
/* 201 */                               C0827el.m886a(6, f749a, "Error when loading phoneId", th);
/* 203 */                               C0843fb.m962a(dataInputStream);
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    C0843fb.m962a(dataInputStream);
                                    throw th;
                                }
                            } catch (Throwable th4) {
/* 200 */                       th = th4;
                                dataInputStream = null;
                            }
                        }
                    }
/* 205 */           return strM802b;
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dx$1, reason: invalid class name */
/* 175 */       static class AnonymousClass1 implements FilenameFilter {
/* 176 */           AnonymousClass1() {
                    }

                    @Override // java.io.FilenameFilter
/* 179 */           public boolean accept(File file, String str) {
/* 180 */               return str.startsWith(".flurryagent.");
                    }
                }

                /* JADX INFO: renamed from: a */
/* 208 */       private static void m798a(String str, DataOutput dataOutput) throws IOException {
/* 209 */           dataOutput.writeInt(1);
/* 210 */           dataOutput.writeUTF(str);
                }

                /* JADX INFO: renamed from: a */
/* 213 */       private static String m797a(DataInput dataInput) throws IOException {
/* 214 */           if (1 != dataInput.readInt()) {
/* 215 */               return null;
                    }
                    return dataInput.readUTF();
                }

                /* JADX INFO: renamed from: b */
/* 220 */       private static String m802b(DataInput dataInput) throws IOException {
/* 221 */           if (46586 != dataInput.readUnsignedShort() || 2 != dataInput.readUnsignedShort()) {
                        return null;
                    }
/* 229 */           dataInput.readUTF();
/* 231 */           return dataInput.readUTF();
                }

                /* JADX INFO: renamed from: h */
/* 234 */       private static String m810h() {
/* 235 */           return ".flurryb.";
                }

                /* JADX INFO: renamed from: c */
/* 238 */       private static boolean m805c(String str) {
/* 239 */           return f750b.contains(str);
                }

                /* JADX INFO: renamed from: i */
/* 242 */       private static Set<String> m811i() {
/* 243 */           HashSet hashSet = new HashSet();
/* 244 */           hashSet.add("null");
/* 245 */           hashSet.add("9774d56d682e549c");
/* 246 */           hashSet.add("dead00beef");
/* 247 */           return Collections.unmodifiableSet(hashSet);
                }
            }
