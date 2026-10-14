            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            
            
    public class AutoCloseInputStream extends ProxyInputStream {
        public AutoCloseInputStream(InputStream in) {
            super(in);
                }

                @Override 
        public void close() throws IOException {
            this.in.close();
            this.in = new ClosedInputStream();
                }

                @Override 
        protected void afterRead(int n) throws IOException {
            if (n == -1) {
                close();
                    }
                }

        protected void finalize() throws Throwable {
            close();
            super.finalize();
                }
            }
