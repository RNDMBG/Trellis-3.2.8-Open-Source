            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            
            
    public class BoundedInputStream extends InputStream {

                
                private final InputStream f2538in;
                private long mark;
                private final long max;
                private long pos;
                private boolean propagateClose;

        public BoundedInputStream(InputStream in, long size) {
            this.pos = 0L;
            this.mark = -1L;
            this.propagateClose = true;
            this.max = size;
            this.f2538in = in;
                }

        public BoundedInputStream(InputStream in) {
            this(in, -1L);
                }

                @Override 
        public int read() throws IOException {
                    if (this.max >= 0 && this.pos >= this.max) {
                return -1;
                    }
            int i = this.f2538in.read();
            this.pos++;
            return i;
                }

                @Override 
       public int read(byte[] b) throws IOException {
           return read(b, 0, b.length);
                }

                @Override 
       public int read(byte[] b, int off, int len) throws IOException {
                    if (this.max >= 0 && this.pos >= this.max) {
                        return -1;
                    }
           long maxRead = this.max >= 0 ? Math.min(len, this.max - this.pos) : len;
           int bytesRead = this.f2538in.read(b, off, (int) maxRead);
           if (bytesRead == -1) {
                        return -1;
                    }
           this.pos += (long) bytesRead;
           return bytesRead;
                }

                @Override 
       public long skip(long n) throws IOException {
                    long toSkip = this.max >= 0 ? Math.min(n, this.max - this.pos) : n;
           long skippedBytes = this.f2538in.skip(toSkip);
           this.pos += skippedBytes;
           return skippedBytes;
                }

                @Override 
       public int available() throws IOException {
                    if (this.max < 0 || this.pos < this.max) {
                        return this.f2538in.available();
                    }
           return 0;
                }

       public String toString() {
           return this.f2538in.toString();
                }

                @Override 
       public void close() throws IOException {
           if (this.propagateClose) {
               this.f2538in.close();
                    }
                }

                @Override 
       public synchronized void reset() throws IOException {
                    try {
                        this.f2538in.reset();
               this.pos = this.mark;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public synchronized void mark(int readlimit) {
                    try {
                        this.f2538in.mark(readlimit);
               this.mark = this.pos;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public boolean markSupported() {
           return this.f2538in.markSupported();
                }

       public boolean isPropagateClose() {
           return this.propagateClose;
                }

       public void setPropagateClose(boolean propagateClose) {
           this.propagateClose = propagateClose;
                }
            }
