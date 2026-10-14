            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 58 */    public class TeeInputStream extends ProxyInputStream {
                private final OutputStream branch;
                private final boolean closeBranch;

/* 59 */        public TeeInputStream(InputStream input, OutputStream branch) {
/* 60 */            this(input, branch, false);
                }

/* 75 */        public TeeInputStream(InputStream input, OutputStream branch, boolean closeBranch) {
/* 76 */            super(input);
/* 77 */            this.branch = branch;
/* 78 */            this.closeBranch = closeBranch;
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 90 */        public void close() throws IOException {
                    try {
/* 91 */                super.close();
/* 93 */                if (this.closeBranch) {
/* 94 */                    this.branch.close();
                        }
                    } catch (Throwable th) {
                        if (this.closeBranch) {
/* 94 */                    this.branch.close();
                        }
                        throw th;
                    }
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
/* 107 */       public int read() throws IOException {
/* 108 */           int ch = super.read();
/* 109 */           if (ch != -1) {
/* 110 */               this.branch.write(ch);
                    }
/* 112 */           return ch;
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
/* 126 */       public int read(byte[] bts, int st, int end) throws IOException {
/* 127 */           int n = super.read(bts, st, end);
/* 128 */           if (n != -1) {
/* 129 */               this.branch.write(bts, st, n);
                    }
/* 131 */           return n;
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
/* 143 */       public int read(byte[] bts) throws IOException {
/* 144 */           int n = super.read(bts);
/* 145 */           if (n != -1) {
/* 146 */               this.branch.write(bts, 0, n);
                    }
/* 148 */           return n;
                }
            }
