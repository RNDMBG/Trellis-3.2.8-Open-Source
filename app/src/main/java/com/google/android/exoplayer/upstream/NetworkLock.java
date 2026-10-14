            package com.google.android.exoplayer.upstream;

            import java.io.IOException;
            import java.util.PriorityQueue;
            
            
    public final class NetworkLock {
                public static final int DOWNLOAD_PRIORITY = 10;
                public static final int STREAMING_PRIORITY = 0;
                public static final NetworkLock instance = new NetworkLock();
                private final PriorityQueue<Integer> queue = new PriorityQueue<>();

        public static class PriorityTooLowException extends IOException {
            public PriorityTooLowException(int priority, int highestPriority) {
                super("Priority too low [priority=" + priority + ", highest=" + highestPriority + "]");
                    }
                }

        private NetworkLock() {
                }

        public synchronized void proceed(int priority) throws InterruptedException {
                    while (this.queue.peek().intValue() < priority) {
                        try {
                    wait();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        public synchronized boolean proceedNonBlocking(int priority) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.queue.peek().intValue() >= priority;
                }

        public synchronized void proceedOrThrow(int priority) throws PriorityTooLowException {
                    try {
                        int highestPriority = this.queue.peek().intValue();
                if (highestPriority < priority) {
                    throw new PriorityTooLowException(priority, highestPriority);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized void add(int priority) {
                    try {
                        this.queue.add(Integer.valueOf(priority));
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized void remove(int priority) {
                    try {
                        this.queue.remove(Integer.valueOf(priority));
               notifyAll();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
