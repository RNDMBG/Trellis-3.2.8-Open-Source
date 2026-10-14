            package org.apache.commons.p005io.monitor;

            import java.util.List;
            import java.util.concurrent.CopyOnWriteArrayList;
            import java.util.concurrent.ThreadFactory;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public final class FileAlterationMonitor implements Runnable {
                private final long interval;
                private final List<FileAlterationObserver> observers;
                private volatile boolean running;
                private Thread thread;
                private ThreadFactory threadFactory;

/* 42 */        public FileAlterationMonitor() {
/* 43 */            this(10000L);
                }

/* 51 */        public FileAlterationMonitor(long interval) {
/* 34 */            this.observers = new CopyOnWriteArrayList();
/* 35 */            this.thread = null;
/* 37 */            this.running = false;
/* 53 */            this.interval = interval;
                }

/* 63 */        public FileAlterationMonitor(long interval, FileAlterationObserver... observers) {
/* 64 */            this(interval);
/* 65 */            if (observers != null) {
                        for (FileAlterationObserver observer : observers) {
/* 67 */                    addObserver(observer);
                        }
                    }
                }

/* 77 */        public long getInterval() {
/* 78 */            return this.interval;
                }

/* 86 */        public synchronized void setThreadFactory(ThreadFactory threadFactory) {
                    try {
                        this.threadFactory = threadFactory;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 95 */        public void addObserver(FileAlterationObserver observer) {
/* 96 */            if (observer != null) {
/* 97 */                this.observers.add(observer);
                    }
                }

/* 106 */       public void removeObserver(FileAlterationObserver observer) {
/* 107 */           if (observer != null) {
/* 108 */               while (this.observers.remove(observer)) {
                        }
                    }
                }

/* 119 */       public Iterable<FileAlterationObserver> getObservers() {
/* 120 */           return this.observers;
                }

/* 128 */       public synchronized void start() throws Exception {
                    try {
                        if (this.running) {
/* 130 */                   throw new IllegalStateException("Monitor is already running");
                        }
                        for (FileAlterationObserver observer : this.observers) {
/* 133 */                   observer.initialize();
                        }
/* 135 */               this.running = true;
/* 136 */               if (this.threadFactory != null) {
/* 137 */                   this.thread = this.threadFactory.newThread(this);
                        } else {
/* 139 */                   this.thread = new Thread(this);
                        }
/* 141 */               this.thread.start();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 149 */       public synchronized void stop() throws Exception {
                    try {
                        stop(this.interval);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 161 */       public synchronized void stop(long stopInterval) throws Exception {
                    try {
                        if (!this.running) {
/* 163 */                   throw new IllegalStateException("Monitor is not running");
                        }
/* 165 */               this.running = false;
                        try {
/* 167 */                   this.thread.join(stopInterval);
                        } catch (InterruptedException e) {
/* 169 */                   Thread.currentThread().interrupt();
                        }
                        for (FileAlterationObserver observer : this.observers) {
/* 172 */                   observer.destroy();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.lang.Runnable
/* 179 */       public void run() {
/* 180 */           while (this.running) {
                        for (FileAlterationObserver observer : this.observers) {
/* 182 */                   observer.checkAndNotify();
                        }
/* 184 */               if (this.running) {
                            try {
/* 188 */                       Thread.sleep(this.interval);
                            } catch (InterruptedException e) {
                            }
                        } else {
/* 192 */                   return;
                        }
                    }
                }
            }
