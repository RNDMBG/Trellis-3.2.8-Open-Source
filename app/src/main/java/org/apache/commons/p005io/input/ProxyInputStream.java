            package org.apache.commons.p005io.input;

            import java.io.FilterInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            
            
    public abstract class ProxyInputStream extends FilterInputStream {
        public ProxyInputStream(InputStream proxy) {
            super(proxy);
                }

                @Override 
        public int read() throws IOException {
                    try {
                beforeRead(1);
                int b = this.in.read();
                afterRead(b == -1 ? -1 : 1);
                return b;
                    } catch (IOException e) {
                handleIOException(e);
                        return -1;
                    }
                }

                @Override 
        public int read(byte[] bts) throws IOException {
                    int length;
            if (bts == null) {
                length = 0;
                    } else {
                        try {
                            length = bts.length;
                        } catch (IOException e) {
                    handleIOException(e);
                    return -1;
                        }
                    }
                    beforeRead(length);
            int n = this.in.read(bts);
            afterRead(n);
            return n;
                }

                @Override 
        public int read(byte[] bts, int off, int len) throws IOException {
                    try {
                beforeRead(len);
                int n = this.in.read(bts, off, len);
                afterRead(n);
               return n;
                    } catch (IOException e) {
               handleIOException(e);
               return -1;
                    }
                }

                @Override 
       public long skip(long ln) throws IOException {
                    try {
               return this.in.skip(ln);
                    } catch (IOException e) {
               handleIOException(e);
               return 0L;
                    }
                }

                @Override 
       public int available() throws IOException {
                    try {
               return super.available();
                    } catch (IOException e) {
               handleIOException(e);
               return 0;
                    }
                }

                @Override 
       public void close() throws IOException {
                    try {
               this.in.close();
                    } catch (IOException e) {
               handleIOException(e);
                    }
                }

                @Override 
       public synchronized void mark(int readlimit) {
                    try {
                        this.in.mark(readlimit);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public synchronized void reset() throws IOException {
                    try {
                        try {
                            this.in.reset();
                        } catch (IOException e) {
                   handleIOException(e);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public boolean markSupported() {
           return this.in.markSupported();
                }

       protected void beforeRead(int n) throws IOException {
                }

       protected void afterRead(int n) throws IOException {
                }

       protected void handleIOException(IOException e) throws IOException {
           throw e;
                }
            }
