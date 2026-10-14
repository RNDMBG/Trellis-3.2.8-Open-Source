            package com.crashlytics.android.internal;

            import java.io.Closeable;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.List;
            import java.util.zip.GZIPOutputStream;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aj */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class C0689aj {

                /* JADX INFO: renamed from: a */
                private final File f373a;

                /* JADX INFO: renamed from: b */
                private final String f374b;

                /* JADX INFO: renamed from: c */
                private C0696aq f375c;

                /* JADX INFO: renamed from: d */
                private File f376d;

                /* JADX INFO: renamed from: e */
                private File f377e;

/* 28 */        public C0689aj(File file, String str, String str2) throws IOException {
/* 31 */            this.f373a = file;
/* 32 */            this.f374b = str2;
/* 34 */            this.f376d = new File(file, str);
/* 36 */            this.f375c = new C0696aq(this.f376d);
/* 38 */            this.f377e = new File(this.f373a, this.f374b);
                    if (!this.f377e.exists()) {
                        this.f377e.mkdirs();
                    }
                }

                /* JADX INFO: renamed from: a */
/* 49 */        public void m247a(byte[] bArr) throws IOException {
/* 50 */            this.f375c.m286a(bArr);
                }

                /* JADX INFO: renamed from: a */
/* 54 */        public int m243a() {
/* 55 */            return this.f375c.m284a();
                }

                /* JADX INFO: renamed from: a */
/* 59 */        public void m245a(String str) throws Throwable {
                    FileInputStream fileInputStream;
                    GZIPOutputStream gZIPOutputStream = null;
/* 60 */            this.f375c.close();
/* 62 */            File file = this.f376d;
                    File file2 = new File(this.f377e, str);
                    try {
                        fileInputStream = new FileInputStream(file);
                        try {
                            GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(new FileOutputStream(file2));
                            try {
                                C0681ab.m212a(fileInputStream, gZIPOutputStream2, new byte[1024]);
                                C0681ab.m210a(fileInputStream, "Failed to close file input stream");
                                C0681ab.m210a((Closeable) gZIPOutputStream2, "Failed to close gzip output stream");
                                file.delete();
/* 64 */                        this.f375c = new C0696aq(this.f376d);
                            } catch (Throwable th) {
                                th = th;
                                gZIPOutputStream = gZIPOutputStream2;
                                C0681ab.m210a(fileInputStream, "Failed to close file input stream");
                                C0681ab.m210a((Closeable) gZIPOutputStream, "Failed to close gzip output stream");
                                file.delete();
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Throwable th3) {
/* 62 */                th = th3;
                        fileInputStream = null;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 103 */       public List<File> m244a(int i) {
/* 104 */           ArrayList arrayList = new ArrayList();
                    for (File file : this.f377e.listFiles()) {
/* 107 */               arrayList.add(file);
/* 109 */               if (arrayList.size() > 0) {
                            break;
                        }
                    }
/* 112 */           return arrayList;
                }

                /* JADX INFO: renamed from: a */
/* 116 */       public void m246a(List<File> list) {
                    for (File file : list) {
/* 118 */               C0681ab.m221c(String.format("deleting sent analytics file %s", file.getName()));
/* 119 */               file.delete();
                    }
                }

                /* JADX INFO: renamed from: c */
/* 124 */       public List<File> m250c() {
/* 125 */           return Arrays.asList(this.f377e.listFiles());
                }

                /* JADX INFO: renamed from: d */
/* 130 */       public void m251d() {
                    try {
/* 131 */               this.f375c.close();
                    } catch (IOException e) {
                    }
/* 134 */           this.f376d.delete();
                }

                /* JADX INFO: renamed from: b */
/* 138 */       public boolean m249b() {
/* 139 */           return this.f375c.m288b();
                }

                /* JADX INFO: renamed from: a */
/* 143 */       public boolean m248a(int i, int i2) {
/* 144 */           return this.f375c.m287a(i, i2);
                }
            }
