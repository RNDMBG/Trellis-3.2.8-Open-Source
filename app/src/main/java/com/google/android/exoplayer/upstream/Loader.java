            package com.google.android.exoplayer.upstream;

            import android.annotation.SuppressLint;
            import android.os.Handler;
            import android.os.Message;
            import android.util.Log;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.Util;
            import java.io.IOException;
            import java.util.concurrent.ExecutorService;
            
            
    public final class Loader {
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

        static  Listener access$000(Loader x0) {
            return x0.listener;
                }

        static  boolean access$102(Loader x0, boolean x1) {
            x0.loading = x1;
                    return x1;
                }

        static  LoadTask access$202(Loader x0, LoadTask x1) {
            x0.currentTask = x1;
                    return x1;
                }

        public static final class UnexpectedLoaderException extends IOException {
            public UnexpectedLoaderException(Exception cause) {
                super("Unexpected " + cause.getClass().getSimpleName() + ": " + cause.getMessage(), cause);
                    }
                }

       public Loader(String threadName, Listener listener) {
           this.downloadExecutorService = Util.newSingleThreadExecutor(threadName);
           this.listener = listener;
                }

       public void startLoading(Loadable loadable) {
           Assertions.checkState(!this.loading);
           this.loading = true;
           this.currentTask = new LoadTask(loadable);
           this.downloadExecutorService.submit(this.currentTask);
                }

       public boolean isLoading() {
           return this.loading;
                }

       public void cancelLoading() {
           Assertions.checkState(this.loading);
           this.currentTask.quit();
                }

       public void release() {
           if (this.loading) {
               cancelLoading();
                    }
           this.downloadExecutorService.shutdown();
                }

                @SuppressLint({"HandlerLeak"})
       private final class LoadTask extends Handler implements Runnable {
                    private static final String TAG = "LoadTask";
                    private volatile Thread executorThread;
                    private final Loadable loadable;

           public LoadTask(Loadable loadable) {
               this.loadable = loadable;
                    }

           public void quit() {
               this.loadable.cancelLoad();
               if (this.executorThread != null) {
                   this.executorThread.interrupt();
                        }
                    }

                    @Override 
           public void run() {
                        try {
                   this.executorThread = Thread.currentThread();
                   if (!this.loadable.isLoadCanceled()) {
                       this.loadable.load();
                            }
                   sendEmptyMessage(0);
                        } catch (IOException e) {
                   obtainMessage(1, e).sendToTarget();
                        } catch (InterruptedException e2) {
                   Assertions.checkState(this.loadable.isLoadCanceled());
                   sendEmptyMessage(0);
                        } catch (Exception e3) {
                   Log.e(TAG, "Unexpected error loading stream", e3);
                   obtainMessage(1, new UnexpectedLoaderException(e3)).sendToTarget();
                        }
                    }

                    @Override 
           public void handleMessage(Message msg) {
               onFinished();
               if (this.loadable.isLoadCanceled()) {
                   Loader.access$000(Loader.this).onCanceled();
                        }
                        switch (msg.what) {
                            case 0:
                       Loader.access$000(Loader.this).onLoaded();
                                break;
                            case 1:
                       Loader.access$000(Loader.this).onError((IOException) msg.obj);
                                break;
                        }
                    }

           private void onFinished() {
               Loader.access$102(Loader.this, false);
               Loader.access$202(Loader.this, null);
                    }
                }
            }
