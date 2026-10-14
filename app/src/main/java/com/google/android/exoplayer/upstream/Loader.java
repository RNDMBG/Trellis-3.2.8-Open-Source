            package com.google.android.exoplayer.upstream;

            import android.annotation.SuppressLint;
            import android.os.Handler;
            import android.os.Message;
            import android.util.Log;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.Util;
            import java.io.IOException;
            import java.util.concurrent.ExecutorService;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public final class Loader {
                private static final int MSG_END_OF_SOURCE = 0;
                private static final int MSG_ERROR = 1;
                private LoadTask currentTask;
                private final ExecutorService downloadExecutorService;
                private final Listener listener;
                private boolean loading;

                public interface Listener {
                    void onCanceled();

                    void onError(IOException iOException);

                    void onLoaded();
                }

                public interface Loadable {
                    void cancelLoad();

                    boolean isLoadCanceled();

                    void load() throws InterruptedException, IOException;
                }

/* 31 */        static /* synthetic */ Listener access$000(Loader x0) {
/* 32 */            return x0.listener;
                }

/* 31 */        static /* synthetic */ boolean access$102(Loader x0, boolean x1) {
/* 32 */            x0.loading = x1;
                    return x1;
                }

/* 31 */        static /* synthetic */ LoadTask access$202(Loader x0, LoadTask x1) {
/* 32 */            x0.currentTask = x1;
                    return x1;
                }

/* 38 */        public static final class UnexpectedLoaderException extends IOException {
/* 39 */            public UnexpectedLoaderException(Exception cause) {
/* 40 */                super("Unexpected " + cause.getClass().getSimpleName() + ": " + cause.getMessage(), cause);
                    }
                }

/* 106 */       public Loader(String threadName, Listener listener) {
/* 108 */           this.downloadExecutorService = Util.newSingleThreadExecutor(threadName);
/* 109 */           this.listener = listener;
                }

/* 120 */       public void startLoading(Loadable loadable) {
/* 121 */           Assertions.checkState(!this.loading);
/* 122 */           this.loading = true;
/* 123 */           this.currentTask = new LoadTask(loadable);
/* 124 */           this.downloadExecutorService.submit(this.currentTask);
                }

/* 132 */       public boolean isLoading() {
/* 133 */           return this.loading;
                }

/* 141 */       public void cancelLoading() {
/* 142 */           Assertions.checkState(this.loading);
/* 143 */           this.currentTask.quit();
                }

/* 151 */       public void release() {
/* 152 */           if (this.loading) {
/* 153 */               cancelLoading();
                    }
/* 155 */           this.downloadExecutorService.shutdown();
                }

                @SuppressLint({"HandlerLeak"})
/* 165 */       private final class LoadTask extends Handler implements Runnable {
                    private static final String TAG = "LoadTask";
                    private volatile Thread executorThread;
                    private final Loadable loadable;

/* 166 */           public LoadTask(Loadable loadable) {
/* 168 */               this.loadable = loadable;
                    }

/* 171 */           public void quit() {
/* 172 */               this.loadable.cancelLoad();
/* 173 */               if (this.executorThread != null) {
/* 174 */                   this.executorThread.interrupt();
                        }
                    }

                    @Override // java.lang.Runnable
/* 180 */           public void run() {
                        try {
/* 181 */                   this.executorThread = Thread.currentThread();
/* 182 */                   if (!this.loadable.isLoadCanceled()) {
/* 183 */                       this.loadable.load();
                            }
/* 185 */                   sendEmptyMessage(0);
                        } catch (IOException e) {
/* 187 */                   obtainMessage(1, e).sendToTarget();
                        } catch (InterruptedException e2) {
/* 190 */                   Assertions.checkState(this.loadable.isLoadCanceled());
/* 191 */                   sendEmptyMessage(0);
                        } catch (Exception e3) {
/* 194 */                   Log.e(TAG, "Unexpected error loading stream", e3);
/* 195 */                   obtainMessage(1, new UnexpectedLoaderException(e3)).sendToTarget();
                        }
                    }

                    @Override // android.os.Handler
/* 200 */           public void handleMessage(Message msg) {
/* 201 */               onFinished();
/* 202 */               if (this.loadable.isLoadCanceled()) {
/* 203 */                   Loader.access$000(Loader.this).onCanceled();
                        }
                        switch (msg.what) {
                            case 0:
/* 208 */                       Loader.access$000(Loader.this).onLoaded();
                                break;
                            case 1:
/* 211 */                       Loader.access$000(Loader.this).onError((IOException) msg.obj);
                                break;
                        }
                    }

/* 216 */           private void onFinished() {
/* 217 */               Loader.access$102(Loader.this, false);
/* 218 */               Loader.access$202(Loader.this, null);
                    }
                }
            }
