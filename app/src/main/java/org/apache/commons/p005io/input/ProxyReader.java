            package org.apache.commons.p005io.input;

            import java.io.FilterReader;
            import java.io.IOException;
            import java.io.Reader;
            import java.nio.CharBuffer;
            
            
    public abstract class ProxyReader extends FilterReader {
        public ProxyReader(Reader proxy) {
            super(proxy);
                }

                @Override 
        public int read() throws IOException {
                    try {
                beforeRead(1);
                int c = this.in.read();
                afterRead(c == -1 ? -1 : 1);
                return c;
                    } catch (IOException e) {
                handleIOException(e);
                        return -1;
                    }
                }

                @Override 
        public int read(char[] chr) throws IOException {
                    int length;
            if (chr == null) {
                length = 0;
                    } else {
                        try {
                            length = chr.length;
                        } catch (IOException e) {
                    handleIOException(e);
                    return -1;
                        }
                    }
                    beforeRead(length);
            int n = this.in.read(chr);
            afterRead(n);
            return n;
                }

                @Override 
        public int read(char[] chr, int st, int len) throws IOException {
                    try {
                beforeRead(len);
                int n = this.in.read(chr, st, len);
                afterRead(n);
               return n;
                    } catch (IOException e) {
               handleIOException(e);
               return -1;
                    }
                }

                @Override 
       public int read(CharBuffer target) throws IOException {
                    int length;
           if (target == null) {
               length = 0;
                    } else {
                        try {
                            length = target.length();
                        } catch (IOException e) {
                   handleIOException(e);
                   return -1;
                        }
                    }
                    beforeRead(length);
           int n = this.in.read(target);
           afterRead(n);
           return n;
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
       public boolean ready() throws IOException {
                    try {
               return this.in.ready();
                    } catch (IOException e) {
               handleIOException(e);
               return false;
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
       public synchronized void mark(int idx) throws IOException {
                    try {
                        try {
                            this.in.mark(idx);
                        } catch (IOException e) {
                   handleIOException(e);
                        }
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
