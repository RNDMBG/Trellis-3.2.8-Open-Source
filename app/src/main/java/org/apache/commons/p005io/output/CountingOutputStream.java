            package org.apache.commons.p005io.output;

            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class CountingOutputStream extends ProxyOutputStream {
                private long count;

/* 40 */        public CountingOutputStream(OutputStream out) {
/* 41 */            super(out);
/* 33 */            this.count = 0L;
                }

                @Override // org.apache.commons.p005io.output.ProxyOutputStream
/* 53 */        protected synchronized void beforeWrite(int n) {
                    try {
                        this.count += (long) n;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 68 */        public int getCount() {
/* 69 */            long result = getByteCount();
                    if (result > 2147483647L) {
/* 71 */                throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
                    }
/* 73 */            return (int) result;
                }

/* 86 */        public int resetCount() {
/* 87 */            long result = resetByteCount();
                    if (result > 2147483647L) {
/* 89 */                throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
                    }
/* 91 */            return (int) result;
                }

/* 104 */       public synchronized long getByteCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.count;
                }

/* 118 */       public synchronized long resetByteCount() {
                    long tmp;
                    try {
                        tmp = this.count;
/* 120 */               this.count = 0L;
                    } catch (Throwable th) {
                        throw th;
                    }
                    return tmp;
                }
            }
