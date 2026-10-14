            package com.crashlytics.android;

            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.FilenameFilter;
            import java.io.IOException;
            
            /* JADX INFO: renamed from: com.crashlytics.android.f */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    final class C0623f extends FileOutputStream {

                /* JADX INFO: renamed from: a */
                public static final FilenameFilter f202a = new C0624g();

                /* JADX INFO: renamed from: b */
                private final String f203b;

                /* JADX INFO: renamed from: c */
                private File f204c;

                /* JADX INFO: renamed from: d */
                private boolean f205d;

/* 39 */        public C0623f(File file, String str) throws FileNotFoundException {
/* 40 */            super(new File(file, str + ".cls_temp"));
/* 33 */            this.f205d = false;
/* 41 */            this.f203b = file + File.separator + str;
/* 42 */            this.f204c = new File(this.f203b + ".cls_temp");
                }

                @Override // java.io.FileOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 46 */        public final synchronized void close() throws IOException {
                    try {
                        if (!this.f205d) {
/* 50 */                    this.f205d = true;
/* 51 */                    super.flush();
/* 52 */                    super.close();
/* 54 */                    File file = new File(this.f203b + ".cls");
/* 56 */                    if (this.f204c.renameTo(file)) {
/* 57 */                        this.f204c = null;
                            } else {
/* 60 */                        String str = "";
/* 61 */                        if (file.exists()) {
/* 62 */                            str = " (target already exists)";
                                } else if (!this.f204c.exists()) {
/* 64 */                            str = " (source does not exist)";
                                }
/* 66 */                        throw new IOException("Could not rename temp file: " + this.f204c + " -> " + file + str);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 77 */        public final void m68a() throws IOException {
/* 78 */            if (!this.f205d) {
/* 81 */                this.f205d = true;
/* 82 */                super.flush();
/* 83 */                super.close();
                    }
                }
            }
