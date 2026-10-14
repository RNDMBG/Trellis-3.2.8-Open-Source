            package com.google.android.exoplayer.upstream;

            import java.io.IOException;
            import java.util.PriorityQueue;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public final class NetworkLock {
                public static final int DOWNLOAD_PRIORITY = 10;
                public static final int STREAMING_PRIORITY = 0;
                public static final NetworkLock instance = new NetworkLock();
                private final PriorityQueue<Integer> queue = new PriorityQueue<>();

/* 38 */        public static class PriorityTooLowException extends IOException {
/* 39 */            public PriorityTooLowException(int priority, int highestPriority) {
/* 40 */                super("Priority too low [priority=" + priority + ", highest=" + highestPriority + "]");
                    }
                }

/* 57 */        private NetworkLock() {
                }

/* 67 */        public synchronized void proceed(int priority) throws InterruptedException {
                    while (this.queue.peek().intValue() < priority) {
                        try {
/* 69 */                    wait();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 79 */        public synchronized boolean proceedNonBlocking(int priority) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.queue.peek().intValue() >= priority;
                }

/* 89 */        public synchronized void proceedOrThrow(int priority) throws PriorityTooLowException {
                    try {
                        int highestPriority = this.queue.peek().intValue();
/* 91 */                if (highestPriority < priority) {
/* 92 */                    throw new PriorityTooLowException(priority, highestPriority);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 103 */       public synchronized void add(int priority) {
                    try {
                        this.queue.add(Integer.valueOf(priority));
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 112 */       public synchronized void remove(int priority) {
                    try {
                        this.queue.remove(Integer.valueOf(priority));
/* 114 */               notifyAll();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
