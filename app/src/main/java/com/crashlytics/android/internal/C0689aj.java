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
            
            
            
    public class C0689aj {

                
                private final File f373a;

                
                private final String f374b;

                
                private C0696aq f375c;

                
                private File f376d;

                
                private File f377e;

        public C0689aj(File file, String str, String str2) throws IOException {
            this.f373a = file;
            this.f374b = str2;
            this.f376d = new File(file, str);
            this.f375c = new C0696aq(this.f376d);
            this.f377e = new File(this.f373a, this.f374b);
                    if (!this.f377e.exists()) {
                        this.f377e.mkdirs();
                    }
                }

                
        public void m247a(byte[] bArr) throws IOException {
            this.f375c.m286a(bArr);
                }

                
        public int m243a() {
            return this.f375c.m284a();
                }

                
        public void m245a(String str) throws Throwable {
                    FileInputStream fileInputStream;
                    GZIPOutputStream gZIPOutputStream = null;
            this.f375c.close();
            File file = this.f376d;
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
                        this.f375c = new C0696aq(this.f376d);
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
                th = th3;
                        fileInputStream = null;
                    }
                }

                
       public List<File> m244a(int i) {
           ArrayList arrayList = new ArrayList();
                    for (File file : this.f377e.listFiles()) {
               arrayList.add(file);
               if (arrayList.size() > 0) {
                            break;
                        }
                    }
           return arrayList;
                }

                
       public void m246a(List<File> list) {
                    for (File file : list) {
               C0681ab.m221c(String.format("deleting sent analytics file %s", file.getName()));
               file.delete();
                    }
                }

                
       public List<File> m250c() {
           return Arrays.asList(this.f377e.listFiles());
                }

                
       public void m251d() {
                    try {
               this.f375c.close();
                    } catch (IOException e) {
                    }
           this.f376d.delete();
                }

                
       public boolean m249b() {
           return this.f375c.m288b();
                }

                
       public boolean m248a(int i, int i2) {
           return this.f375c.m287a(i, i2);
                }
            }
