            package android.support.v4.content;

            import android.os.Handler;
            import android.os.Message;
            import android.os.Process;
            import android.util.Log;
            import java.util.concurrent.BlockingQueue;
            import java.util.concurrent.Callable;
            import java.util.concurrent.CancellationException;
            import java.util.concurrent.ExecutionException;
            import java.util.concurrent.Executor;
            import java.util.concurrent.FutureTask;
            import java.util.concurrent.LinkedBlockingQueue;
            import java.util.concurrent.ThreadFactory;
            import java.util.concurrent.ThreadPoolExecutor;
            import java.util.concurrent.TimeUnit;
            import java.util.concurrent.TimeoutException;
            import java.util.concurrent.atomic.AtomicBoolean;
            import java.util.concurrent.atomic.AtomicInteger;
            
            
    abstract class ModernAsyncTask<Params, Progress, Result> {
                private static final int CORE_POOL_SIZE = 5;
                private static final int KEEP_ALIVE = 1;
                private static final String LOG_TAG = "AsyncTask";
                private static final int MAXIMUM_POOL_SIZE = 128;
                private static final int MESSAGE_POST_PROGRESS = 2;
                private static final int MESSAGE_POST_RESULT = 1;
                private static final ThreadFactory sThreadFactory = new ThreadFactoryC00211();
                private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue(10);
                public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
                private static final InternalHandler sHandler = new InternalHandler(null);
                private static volatile Executor sDefaultExecutor = THREAD_POOL_EXECUTOR;
                private volatile Status mStatus = Status.PENDING;
                private final AtomicBoolean mTaskInvoked = new AtomicBoolean();
                private final WorkerRunnable<Params, Result> mWorker = new C00222();
                private final FutureTask<Result> mFuture = new C00233(this.mWorker);

                public enum Status {
                    PENDING,
                    RUNNING,
                    FINISHED
                }

                protected abstract Result doInBackground(Params... paramsArr);

        static  AtomicBoolean access$200(ModernAsyncTask x0) {
            return x0.mTaskInvoked;
                }

        static  Object access$300(ModernAsyncTask x0, Object x1) {
            return x0.postResult(x1);
                }

        static  void access$400(ModernAsyncTask x0, Object x1) {
            x0.postResultIfNotInvoked(x1);
                }

        static  void access$500(ModernAsyncTask x0, Object x1) {
            x0.finish(x1);
                }

                
        static class ThreadFactoryC00211 implements ThreadFactory {
                    private final AtomicInteger mCount = new AtomicInteger(1);

            ThreadFactoryC00211() {
                    }

                    @Override 
            public Thread newThread(Runnable r) {
                return new Thread(r, "ModernAsyncTask #" + this.mCount.getAndIncrement());
                    }
                }

       public static void init() {
           sHandler.getLooper();
                }

       public static void setDefaultExecutor(Executor exec) {
           sDefaultExecutor = exec;
                }

                
       class C00222 extends WorkerRunnable<Params, Result> {
           C00222() {
                        super(null);
                    }

                    @Override 
           public Result call() throws Exception {
               ModernAsyncTask.access$200(ModernAsyncTask.this).set(true);
               Process.setThreadPriority(10);
               return (Result) ModernAsyncTask.access$300(ModernAsyncTask.this, ModernAsyncTask.this.doInBackground(this.mParams));
                    }
                }

                
       class C00233 extends FutureTask<Result> {
           C00233(Callable callable) {
                        super(callable);
                    }

                    @Override 
           protected void done() {
                        try {
                   Result result = get();
                   ModernAsyncTask.access$400(ModernAsyncTask.this, result);
                        } catch (InterruptedException e) {
                   Log.w(ModernAsyncTask.LOG_TAG, e);
                        } catch (CancellationException e2) {
                   ModernAsyncTask.access$400(ModernAsyncTask.this, null);
                        } catch (ExecutionException e3) {
                   throw new RuntimeException("An error occured while executing doInBackground()", e3.getCause());
                        } catch (Throwable t) {
                   throw new RuntimeException("An error occured while executing doInBackground()", t);
                        }
                    }
                }

       private void postResultIfNotInvoked(Result result) {
           boolean wasTaskInvoked = this.mTaskInvoked.get();
           if (!wasTaskInvoked) {
               postResult(result);
                    }
                }

       private Result postResult(Result result) {
           Message message = sHandler.obtainMessage(1, new AsyncTaskResult(this, result));
           message.sendToTarget();
           return result;
                }

       public final Status getStatus() {
           return this.mStatus;
                }

       protected void onPreExecute() {
                }

       protected void onPostExecute(Result result) {
                }

       protected void onProgressUpdate(Progress... values) {
                }

       protected void onCancelled(Result result) {
           onCancelled();
                }

       protected void onCancelled() {
                }

       public final boolean isCancelled() {
           return this.mFuture.isCancelled();
                }

       public final boolean cancel(boolean mayInterruptIfRunning) {
           return this.mFuture.cancel(mayInterruptIfRunning);
                }

       public final Result get() throws ExecutionException, InterruptedException {
           return this.mFuture.get();
                }

       public final Result get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
           return this.mFuture.get(timeout, unit);
                }

       public final ModernAsyncTask<Params, Progress, Result> execute(Params... params) {
           return executeOnExecutor(sDefaultExecutor, params);
                }

       public final ModernAsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec, Params... params) {
           if (this.mStatus != Status.PENDING) {
                        switch (this.mStatus) {
                            case RUNNING:
                       throw new IllegalStateException("Cannot execute task: the task is already running.");
                            case FINISHED:
                       throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
                        }
                    }
           this.mStatus = Status.RUNNING;
           onPreExecute();
           this.mWorker.mParams = params;
           exec.execute(this.mFuture);
           return this;
                }

       public static void execute(Runnable runnable) {
           sDefaultExecutor.execute(runnable);
                }

       protected final void publishProgress(Progress... values) {
           if (!isCancelled()) {
               sHandler.obtainMessage(2, new AsyncTaskResult(this, values)).sendToTarget();
                    }
                }

       private void finish(Result result) {
           if (isCancelled()) {
               onCancelled(result);
                    } else {
               onPostExecute(result);
                    }
           this.mStatus = Status.FINISHED;
                }

       private static class InternalHandler extends Handler {
           private InternalHandler() {
                    }

            InternalHandler(ThreadFactoryC00211 x0) {
               this();
                    }

                    









                    @Override 
           public void handleMessage(Message msg) {
               AsyncTaskResult result = (AsyncTaskResult) msg.obj;
                        switch (msg.what) {
                            case 1:
                       ModernAsyncTask.access$500(result.mTask, result.mData[0]);
                                break;
                            case 2:
                       result.mTask.onProgressUpdate(result.mData);
                                break;
                        }
                    }
                }

       private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
                    Params[] mParams;

           private WorkerRunnable() {
                    }

            WorkerRunnable(ThreadFactoryC00211 x0) {
               this();
                    }
                }

       private static class AsyncTaskResult<Data> {
                    final Data[] mData;
                    final ModernAsyncTask mTask;

           AsyncTaskResult(ModernAsyncTask task, Data... data) {
               this.mTask = task;
               this.mData = data;
                    }
                }
            }
