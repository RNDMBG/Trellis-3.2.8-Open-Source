            package org.apache.commons.p005io.output;

            import java.io.OutputStream;
            
            
    public class CountingOutputStream extends ProxyOutputStream {
                private long count;

        public CountingOutputStream(OutputStream out) {
            super(out);
            this.count = 0L;
                }

                @Override 
        protected synchronized void beforeWrite(int n) {
                    try {
                        this.count += (long) n;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

        public int getCount() {
            long result = getByteCount();
                    if (result > 2147483647L) {
                throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
                    }
            return (int) result;
                }

        public int resetCount() {
            long result = resetByteCount();
                    if (result > 2147483647L) {
                throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
                    }
            return (int) result;
                }

       public synchronized long getByteCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.count;
                }

       public synchronized long resetByteCount() {
                    long tmp;
                    try {
                        tmp = this.count;
               this.count = 0L;
                    } catch (Throwable th) {
                        throw th;
                    }
                    return tmp;
                }
            }
