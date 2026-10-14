            package org.apache.commons.p005io;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    class ThreadMonitor implements Runnable {
                private final Thread thread;
                private final long timeout;

/* 54 */        public static Thread start(long timeout) {
/* 55 */            return start(Thread.currentThread(), timeout);
                }

/* 67 */        public static Thread start(Thread thread, long timeout) {
                    if (timeout <= 0) {
/* 68 */                return null;
                    }
/* 70 */            ThreadMonitor timout = new ThreadMonitor(thread, timeout);
/* 71 */            Thread monitor = new Thread(timout, ThreadMonitor.class.getSimpleName());
/* 72 */            monitor.setDaemon(true);
/* 73 */            monitor.start();
/* 75 */            return monitor;
                }

/* 83 */        public static void stop(Thread thread) {
/* 84 */            if (thread != null) {
/* 85 */                thread.interrupt();
                    }
                }

/* 94 */        private ThreadMonitor(Thread thread, long timeout) {
/* 96 */            this.thread = thread;
/* 97 */            this.timeout = timeout;
                }

                @Override // java.lang.Runnable
/* 107 */       public void run() {
                    try {
/* 108 */               Thread.sleep(this.timeout);
/* 109 */               this.thread.interrupt();
                    } catch (InterruptedException e) {
                    }
                }
            }
