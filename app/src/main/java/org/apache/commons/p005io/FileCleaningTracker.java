            package org.apache.commons.p005io;

            import java.io.File;
            import java.lang.ref.PhantomReference;
            import java.lang.ref.ReferenceQueue;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.Collections;
            import java.util.HashSet;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 75 */    public class FileCleaningTracker {
                Thread reaper;

                /* JADX INFO: renamed from: q */
                ReferenceQueue<Object> f2535q = new ReferenceQueue<>();
                final Collection<Tracker> trackers = Collections.synchronizedSet(new HashSet());
                final List<String> deleteFailures = Collections.synchronizedList(new ArrayList());
                volatile boolean exitWhenFinished = false;

/* 76 */        public void track(File file, Object marker) {
/* 77 */            track(file, marker, (FileDeleteStrategy) null);
                }

/* 90 */        public void track(File file, Object marker, FileDeleteStrategy deleteStrategy) {
/* 91 */            if (file == null) {
/* 92 */                throw new NullPointerException("The file must not be null");
                    }
/* 94 */            addTracker(file.getPath(), marker, deleteStrategy);
                }

/* 106 */       public void track(String path, Object marker) {
/* 107 */           track(path, marker, (FileDeleteStrategy) null);
                }

/* 120 */       public void track(String path, Object marker, FileDeleteStrategy deleteStrategy) {
/* 121 */           if (path == null) {
/* 122 */               throw new NullPointerException("The path must not be null");
                    }
/* 124 */           addTracker(path, marker, deleteStrategy);
                }

/* 135 */       private synchronized void addTracker(String path, Object marker, FileDeleteStrategy deleteStrategy) {
                    try {
                        if (this.exitWhenFinished) {
/* 137 */                   throw new IllegalStateException("No new trackers can be added once exitWhenFinished() is called");
                        }
/* 139 */               if (this.reaper == null) {
/* 140 */                   this.reaper = new Reaper();
/* 141 */                   this.reaper.start();
                        }
/* 143 */               this.trackers.add(new Tracker(path, deleteStrategy, marker, this.f2535q));
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 153 */       public int getTrackCount() {
/* 154 */           return this.trackers.size();
                }

/* 163 */       public List<String> getDeleteFailures() {
/* 164 */           return this.deleteFailures;
                }

/* 189 */       public synchronized void exitWhenFinished() {
                    try {
                        this.exitWhenFinished = true;
/* 191 */               if (this.reaper != null) {
                            synchronized (this.reaper) {
                                try {
/* 193 */                           this.reaper.interrupt();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 202 */       private final class Reaper extends Thread {
/* 203 */           Reaper() {
/* 205 */               super("File Reaper");
/* 206 */               setPriority(10);
/* 207 */               setDaemon(true);
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 216 */           public void run() {
                        while (true) {
/* 217 */                   if (!FileCleaningTracker.this.exitWhenFinished || FileCleaningTracker.this.trackers.size() > 0) {
                                try {
/* 220 */                           Tracker tracker = (Tracker) FileCleaningTracker.this.f2535q.remove();
/* 221 */                           FileCleaningTracker.this.trackers.remove(tracker);
/* 222 */                           if (!tracker.delete()) {
/* 223 */                               FileCleaningTracker.this.deleteFailures.add(tracker.getPath());
                                    }
/* 225 */                           tracker.clear();
                                } catch (InterruptedException e) {
                                }
                            } else {
/* 230 */                       return;
                            }
                        }
                    }
                }

/* 255 */       private static final class Tracker extends PhantomReference<Object> {
                    private final FileDeleteStrategy deleteStrategy;
                    private final String path;

/* 256 */           Tracker(String path, FileDeleteStrategy deleteStrategy, Object marker, ReferenceQueue<? super Object> queue) {
/* 257 */               super(marker, queue);
/* 258 */               this.path = path;
/* 259 */               this.deleteStrategy = deleteStrategy == null ? FileDeleteStrategy.NORMAL : deleteStrategy;
                    }

/* 267 */           public String getPath() {
/* 268 */               return this.path;
                    }

/* 277 */           public boolean delete() {
/* 278 */               return this.deleteStrategy.deleteQuietly(new File(this.path));
                    }
                }
            }
