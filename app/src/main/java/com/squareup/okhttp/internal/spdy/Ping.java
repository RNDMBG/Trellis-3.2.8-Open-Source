            package com.squareup.okhttp.internal.spdy;

            import java.util.concurrent.CountDownLatch;
            import java.util.concurrent.TimeUnit;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public final class Ping {
                private final CountDownLatch latch = new CountDownLatch(1);
                private long sent = -1;
                private long received = -1;

/* 28 */        Ping() {
                }

/* 32 */        void send() {
                    if (this.sent != -1) {
                        throw new IllegalStateException();
                    }
/* 34 */            this.sent = System.nanoTime();
                }

/* 37 */        void receive() {
                    if (this.received != -1 || this.sent == -1) {
                        throw new IllegalStateException();
                    }
/* 39 */            this.received = System.nanoTime();
/* 40 */            this.latch.countDown();
                }

/* 43 */        void cancel() {
                    if (this.received != -1 || this.sent == -1) {
                        throw new IllegalStateException();
                    }
/* 45 */            this.received = this.sent - 1;
/* 46 */            this.latch.countDown();
                }

/* 54 */        public long roundTripTime() throws InterruptedException {
/* 55 */            this.latch.await();
/* 56 */            return this.received - this.sent;
                }

/* 64 */        public long roundTripTime(long timeout, TimeUnit unit) throws InterruptedException {
/* 65 */            if (this.latch.await(timeout, unit)) {
/* 66 */                return this.received - this.sent;
                    }
                    return -2L;
                }
            }
