            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.OutputStream;
            
            
    public class TeeOutputStream extends ProxyOutputStream {
                protected OutputStream branch;

        public TeeOutputStream(OutputStream out, OutputStream branch) {
            super(out);
            this.branch = branch;
                }

                @Override 
        public synchronized void write(byte[] b) throws IOException {
                    try {
                        super.write(b);
                this.branch.write(b);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
        public synchronized void write(byte[] b, int off, int len) throws IOException {
                    try {
                        super.write(b, off, len);
                this.branch.write(b, off, len);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
        public synchronized void write(int b) throws IOException {
                    try {
                        super.write(b);
                this.branch.write(b);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
        public void flush() throws IOException {
            super.flush();
            this.branch.flush();
                }

                @Override 
       public void close() throws IOException {
                    try {
               super.close();
               this.branch.close();
                    } catch (Throwable th) {
                        this.branch.close();
                        throw th;
                    }
                }
            }
