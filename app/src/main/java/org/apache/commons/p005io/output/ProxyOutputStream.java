            package org.apache.commons.p005io.output;

            import java.io.FilterOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class ProxyOutputStream extends FilterOutputStream {
/* 41 */        public ProxyOutputStream(OutputStream proxy) {
/* 42 */            super(proxy);
                }

                @Override // java.io.FilterOutputStream, java.io.OutputStream
/* 53 */        public void write(int idx) throws IOException {
                    try {
/* 54 */                beforeWrite(1);
/* 55 */                this.out.write(idx);
/* 56 */                afterWrite(1);
                    } catch (IOException e) {
/* 58 */                handleIOException(e);
                    }
                }

                @Override // java.io.FilterOutputStream, java.io.OutputStream
/* 69 */        public void write(byte[] bts) throws IOException {
                    int len;
/* 70 */            if (bts == null) {
/* 70 */                len = 0;
                    } else {
                        try {
                            len = bts.length;
                        } catch (IOException e) {
/* 75 */                    handleIOException(e);
/* 77 */                    return;
                        }
                    }
/* 71 */            beforeWrite(len);
/* 72 */            this.out.write(bts);
/* 73 */            afterWrite(len);
                }

                @Override // java.io.FilterOutputStream, java.io.OutputStream
/* 88 */        public void write(byte[] bts, int st, int end) throws IOException {
                    try {
/* 89 */                beforeWrite(end);
/* 90 */                this.out.write(bts, st, end);
/* 91 */                afterWrite(end);
                    } catch (IOException e) {
/* 93 */                handleIOException(e);
                    }
                }

                @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
/* 103 */       public void flush() throws IOException {
                    try {
/* 104 */               this.out.flush();
                    } catch (IOException e) {
/* 106 */               handleIOException(e);
                    }
                }

                @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 116 */       public void close() throws IOException {
                    try {
/* 117 */               this.out.close();
                    } catch (IOException e) {
/* 119 */               handleIOException(e);
                    }
                }

/* 136 */       protected void beforeWrite(int n) throws IOException {
                }

/* 153 */       protected void afterWrite(int n) throws IOException {
                }

/* 165 */       protected void handleIOException(IOException e) throws IOException {
/* 166 */           throw e;
                }
            }
