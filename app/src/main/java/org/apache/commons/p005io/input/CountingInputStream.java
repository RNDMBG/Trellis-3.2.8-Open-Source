            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class CountingInputStream extends ProxyInputStream {
                private long count;

/* 41 */        public CountingInputStream(InputStream in) {
/* 42 */            super(in);
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
/* 57 */        public synchronized long skip(long length) throws IOException {
                    long skip;
                    try {
                        skip = super.skip(length);
/* 59 */                this.count += skip;
                    } catch (Throwable th) {
                        throw th;
                    }
                    return skip;
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream
/* 70 */        protected synchronized void afterRead(int n) {
                    if (n != -1) {
                        try {
/* 72 */                    this.count += (long) n;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 87 */        public int getCount() {
/* 88 */            long result = getByteCount();
                    if (result > 2147483647L) {
/* 90 */                throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
                    }
/* 92 */            return (int) result;
                }

/* 105 */       public int resetCount() {
/* 106 */           long result = resetByteCount();
                    if (result > 2147483647L) {
/* 108 */               throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
                    }
/* 110 */           return (int) result;
                }

/* 123 */       public synchronized long getByteCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.count;
                }

/* 137 */       public synchronized long resetByteCount() {
                    long tmp;
                    try {
                        tmp = this.count;
/* 139 */               this.count = 0L;
                    } catch (Throwable th) {
                        throw th;
                    }
                    return tmp;
                }
            }
