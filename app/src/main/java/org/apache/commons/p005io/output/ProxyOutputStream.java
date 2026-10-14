            package org.apache.commons.p005io.output;

            import java.io.FilterOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            
            
    public class ProxyOutputStream extends FilterOutputStream {
        public ProxyOutputStream(OutputStream proxy) {
            super(proxy);
                }

                @Override 
        public void write(int idx) throws IOException {
                    try {
                beforeWrite(1);
                this.out.write(idx);
                afterWrite(1);
                    } catch (IOException e) {
                handleIOException(e);
                    }
                }

                @Override 
        public void write(byte[] bts) throws IOException {
                    int len;
            if (bts == null) {
                len = 0;
                    } else {
                        try {
                            len = bts.length;
                        } catch (IOException e) {
                    handleIOException(e);
                    return;
                        }
                    }
            beforeWrite(len);
            this.out.write(bts);
            afterWrite(len);
                }

                @Override 
        public void write(byte[] bts, int st, int end) throws IOException {
                    try {
                beforeWrite(end);
                this.out.write(bts, st, end);
                afterWrite(end);
                    } catch (IOException e) {
                handleIOException(e);
                    }
                }

                @Override 
       public void flush() throws IOException {
                    try {
               this.out.flush();
                    } catch (IOException e) {
               handleIOException(e);
                    }
                }

                @Override 
       public void close() throws IOException {
                    try {
               this.out.close();
                    } catch (IOException e) {
               handleIOException(e);
                    }
                }

       protected void beforeWrite(int n) throws IOException {
                }

       protected void afterWrite(int n) throws IOException {
                }

       protected void handleIOException(IOException e) throws IOException {
           throw e;
                }
            }
