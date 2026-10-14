            package com.crashlytics.android;

            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.FilenameFilter;
            import java.io.IOException;
            
            
            
    final class C0623f extends FileOutputStream {

                
                public static final FilenameFilter f202a = new C0624g();

                
                private final String f203b;

                
                private File f204c;

                
                private boolean f205d;

        public C0623f(File file, String str) throws FileNotFoundException {
            super(new File(file, str + ".cls_temp"));
            this.f205d = false;
            this.f203b = file + File.separator + str;
            this.f204c = new File(this.f203b + ".cls_temp");
                }

                @Override 
        public final synchronized void close() throws IOException {
                    try {
                        if (!this.f205d) {
                    this.f205d = true;
                    super.flush();
                    super.close();
                    File file = new File(this.f203b + ".cls");
                    if (this.f204c.renameTo(file)) {
                        this.f204c = null;
                            } else {
                        String str = "";
                        if (file.exists()) {
                            str = " (target already exists)";
                                } else if (!this.f204c.exists()) {
                            str = " (source does not exist)";
                                }
                        throw new IOException("Could not rename temp file: " + this.f204c + " -> " + file + str);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public final void m68a() throws IOException {
            if (!this.f205d) {
                this.f205d = true;
                super.flush();
                super.close();
                    }
                }
            }
