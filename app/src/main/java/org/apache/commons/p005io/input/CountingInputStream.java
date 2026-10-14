            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            
            
    public class CountingInputStream extends ProxyInputStream {
                private long count;

        public CountingInputStream(InputStream in) {
            super(in);
                }

                @Override 
        public synchronized long skip(long length) throws IOException {
                    long skip;
                    try {
                        skip = super.skip(length);
                this.count += skip;
                    } catch (Throwable th) {
                        throw th;
                    }
                    return skip;
                }

                @Override 
        protected synchronized void afterRead(int n) {
                    if (n != -1) {
                        try {
                    this.count += (long) n;
                        } catch (Throwable th) {
                            throw th;
                        }
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
