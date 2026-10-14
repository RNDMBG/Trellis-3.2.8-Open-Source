            package android.support.v4.content;

            import android.content.Context;
            import android.os.Handler;
            import android.os.SystemClock;
            import android.support.v4.util.TimeUtils;
            import java.io.FileDescriptor;
            import java.io.PrintWriter;
            import java.util.concurrent.CountDownLatch;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public abstract class AsyncTaskLoader<D> extends Loader<D> {
                static final boolean DEBUG = false;
                static final String TAG = "AsyncTaskLoader";
                volatile AsyncTaskLoader<D>.LoadTask mCancellingTask;
                Handler mHandler;
                long mLastLoadCompleteTime;
                volatile AsyncTaskLoader<D>.LoadTask mTask;
                long mUpdateThrottle;

                public abstract D loadInBackground();

/* 38 */        final class LoadTask extends ModernAsyncTask<Void, Void, D> implements Runnable {
                    private CountDownLatch done = new CountDownLatch(1);
                    D result;
                    boolean waiting;

/* 39 */            LoadTask() {
                    }

/* 39 */            static /* synthetic */ CountDownLatch access$000(LoadTask x0) {
/* 40 */                return x0.done;
                    }

                    @Override // android.support.v4.content.ModernAsyncTask
/* 39 */            protected /* bridge */ /* synthetic */ Object doInBackground(Void[] voidArr) {
/* 40 */                return doInBackground2(voidArr);
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 50 */            protected D doInBackground2(Void... voidArr) {
/* 51 */                this.result = (D) AsyncTaskLoader.this.onLoadInBackground();
/* 53 */                return this.result;
                    }

                    @Override // android.support.v4.content.ModernAsyncTask
/* 60 */            protected void onPostExecute(D data) {
                        try {
/* 61 */                    AsyncTaskLoader.this.dispatchOnLoadComplete(this, data);
/* 63 */                    this.done.countDown();
                        } catch (Throwable th) {
                            this.done.countDown();
                            throw th;
                        }
                    }

                    @Override // android.support.v4.content.ModernAsyncTask
/* 70 */            protected void onCancelled() {
                        try {
/* 71 */                    AsyncTaskLoader.this.dispatchOnCancelled(this, this.result);
/* 73 */                    this.done.countDown();
                        } catch (Throwable th) {
                            this.done.countDown();
                            throw th;
                        }
                    }

                    @Override // java.lang.Runnable
/* 78 */            public void run() {
/* 79 */                this.waiting = false;
/* 80 */                AsyncTaskLoader.this.executePendingTask();
                    }
                }

/* 91 */        public AsyncTaskLoader(Context context) {
/* 92 */            super(context);
/* 88 */            this.mLastLoadCompleteTime = -10000L;
                }

/* 102 */       public void setUpdateThrottle(long delayMS) {
/* 103 */           this.mUpdateThrottle = delayMS;
                    if (delayMS != 0) {
/* 105 */               this.mHandler = new Handler();
                    }
                }

                @Override // android.support.v4.content.Loader
/* 110 */       protected void onForceLoad() {
/* 111 */           super.onForceLoad();
/* 112 */           cancelLoad();
/* 113 */           this.mTask = new LoadTask();
/* 115 */           executePendingTask();
                }

/* 135 */       public boolean cancelLoad() {
                    boolean cancelled = false;
/* 136 */           if (this.mTask != null) {
/* 137 */               if (this.mCancellingTask != null) {
/* 142 */                   if (this.mTask.waiting) {
/* 143 */                       this.mTask.waiting = false;
/* 144 */                       this.mHandler.removeCallbacks(this.mTask);
                            }
/* 146 */                   this.mTask = null;
                        } else if (this.mTask.waiting) {
/* 152 */                   this.mTask.waiting = false;
/* 153 */                   this.mHandler.removeCallbacks(this.mTask);
/* 154 */                   this.mTask = null;
                        } else {
/* 157 */                   cancelled = this.mTask.cancel(false);
/* 159 */                   if (cancelled) {
/* 160 */                       this.mCancellingTask = this.mTask;
                            }
/* 162 */                   this.mTask = null;
                        }
                    }
/* 166 */           return cancelled;
                }

/* 173 */       public void onCanceled(D data) {
                }

/* 176 */       void executePendingTask() {
/* 177 */           if (this.mCancellingTask == null && this.mTask != null) {
/* 178 */               if (this.mTask.waiting) {
/* 179 */                   this.mTask.waiting = false;
/* 180 */                   this.mHandler.removeCallbacks(this.mTask);
                        }
                        if (this.mUpdateThrottle > 0) {
/* 183 */                   long now = SystemClock.uptimeMillis();
                            if (now < this.mLastLoadCompleteTime + this.mUpdateThrottle) {
/* 189 */                       this.mTask.waiting = true;
/* 190 */                       this.mHandler.postAtTime(this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle);
/* 197 */                       return;
                            }
                        }
/* 195 */               this.mTask.executeOnExecutor(ModernAsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
                    }
                }

/* 199 */       void dispatchOnCancelled(AsyncTaskLoader<D>.LoadTask task, D data) {
/* 200 */           onCanceled(data);
/* 201 */           if (this.mCancellingTask == task) {
/* 203 */               rollbackContentChanged();
/* 204 */               this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
/* 205 */               this.mCancellingTask = null;
/* 206 */               executePendingTask();
                    }
                }

/* 210 */       void dispatchOnLoadComplete(AsyncTaskLoader<D>.LoadTask task, D data) {
/* 211 */           if (this.mTask != task) {
/* 213 */               dispatchOnCancelled(task, data);
/* 226 */               return;
                    }
/* 215 */           if (isAbandoned()) {
/* 217 */               onCanceled(data);
/* 226 */               return;
                    }
/* 219 */           commitContentChanged();
/* 220 */           this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
/* 221 */           this.mTask = null;
/* 223 */           deliverResult(data);
                }

/* 241 */       protected D onLoadInBackground() {
/* 242 */           return loadInBackground();
                }

/* 255 */       public void waitForLoader() {
/* 256 */           AsyncTaskLoader<D>.LoadTask task = this.mTask;
/* 257 */           if (task != null) {
                        try {
/* 259 */                   LoadTask.access$000(task).await();
                        } catch (InterruptedException e) {
                        }
                    }
                }

                @Override // android.support.v4.content.Loader
/* 267 */       public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
/* 268 */           super.dump(prefix, fd, writer, args);
/* 269 */           if (this.mTask != null) {
/* 270 */               writer.print(prefix);
                        writer.print("mTask=");
                        writer.print(this.mTask);
/* 271 */               writer.print(" waiting=");
                        writer.println(this.mTask.waiting);
                    }
/* 273 */           if (this.mCancellingTask != null) {
/* 274 */               writer.print(prefix);
                        writer.print("mCancellingTask=");
                        writer.print(this.mCancellingTask);
/* 275 */               writer.print(" waiting=");
                        writer.println(this.mCancellingTask.waiting);
                    }
                    if (this.mUpdateThrottle != 0) {
/* 278 */               writer.print(prefix);
                        writer.print("mUpdateThrottle=");
/* 279 */               TimeUtils.formatDuration(this.mUpdateThrottle, writer);
/* 280 */               writer.print(" mLastLoadCompleteTime=");
/* 281 */               TimeUtils.formatDuration(this.mLastLoadCompleteTime, SystemClock.uptimeMillis(), writer);
/* 283 */               writer.println();
                    }
                }
            }
