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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    abstract class ModernAsyncTask<Params, Progress, Result> {
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

/* 46 */        static /* synthetic */ AtomicBoolean access$200(ModernAsyncTask x0) {
/* 47 */            return x0.mTaskInvoked;
                }

/* 46 */        static /* synthetic */ Object access$300(ModernAsyncTask x0, Object x1) {
/* 47 */            return x0.postResult(x1);
                }

/* 46 */        static /* synthetic */ void access$400(ModernAsyncTask x0, Object x1) {
/* 47 */            x0.postResultIfNotInvoked(x1);
                }

/* 46 */        static /* synthetic */ void access$500(ModernAsyncTask x0, Object x1) {
/* 47 */            x0.finish(x1);
                }

                /* JADX INFO: renamed from: android.support.v4.content.ModernAsyncTask$1 */
/* 52 */        static class ThreadFactoryC00211 implements ThreadFactory {
                    private final AtomicInteger mCount = new AtomicInteger(1);

/* 53 */            ThreadFactoryC00211() {
                    }

                    @Override // java.util.concurrent.ThreadFactory
/* 57 */            public Thread newThread(Runnable r) {
/* 58 */                return new Thread(r, "ModernAsyncTask #" + this.mCount.getAndIncrement());
                    }
                }

/* 105 */       public static void init() {
/* 106 */           sHandler.getLooper();
                }

/* 110 */       public static void setDefaultExecutor(Executor exec) {
/* 111 */           sDefaultExecutor = exec;
                }

                /* JADX INFO: renamed from: android.support.v4.content.ModernAsyncTask$2 */
/* 116 */       class C00222 extends WorkerRunnable<Params, Result> {
/* 117 */           C00222() {
                        super(null);
                    }

                    @Override // java.util.concurrent.Callable
/* 119 */           public Result call() throws Exception {
/* 120 */               ModernAsyncTask.access$200(ModernAsyncTask.this).set(true);
/* 122 */               Process.setThreadPriority(10);
/* 123 */               return (Result) ModernAsyncTask.access$300(ModernAsyncTask.this, ModernAsyncTask.this.doInBackground(this.mParams));
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.content.ModernAsyncTask$3 */
/* 125 */       class C00233 extends FutureTask<Result> {
/* 126 */           C00233(Callable callable) {
                        super(callable);
                    }

                    @Override // java.util.concurrent.FutureTask
/* 130 */           protected void done() {
                        try {
/* 131 */                   Result result = get();
/* 133 */                   ModernAsyncTask.access$400(ModernAsyncTask.this, result);
                        } catch (InterruptedException e) {
/* 135 */                   Log.w(ModernAsyncTask.LOG_TAG, e);
                        } catch (CancellationException e2) {
/* 140 */                   ModernAsyncTask.access$400(ModernAsyncTask.this, null);
                        } catch (ExecutionException e3) {
/* 137 */                   throw new RuntimeException("An error occured while executing doInBackground()", e3.getCause());
                        } catch (Throwable t) {
/* 142 */                   throw new RuntimeException("An error occured while executing doInBackground()", t);
                        }
                    }
                }

/* 149 */       private void postResultIfNotInvoked(Result result) {
/* 150 */           boolean wasTaskInvoked = this.mTaskInvoked.get();
/* 151 */           if (!wasTaskInvoked) {
/* 152 */               postResult(result);
                    }
                }

/* 156 */       private Result postResult(Result result) {
/* 157 */           Message message = sHandler.obtainMessage(1, new AsyncTaskResult(this, result));
/* 159 */           message.sendToTarget();
/* 160 */           return result;
                }

/* 168 */       public final Status getStatus() {
/* 169 */           return this.mStatus;
                }

/* 196 */       protected void onPreExecute() {
                }

/* 212 */       protected void onPostExecute(Result result) {
                }

/* 225 */       protected void onProgressUpdate(Progress... values) {
                }

/* 243 */       protected void onCancelled(Result result) {
/* 244 */           onCancelled();
                }

/* 259 */       protected void onCancelled() {
                }

/* 272 */       public final boolean isCancelled() {
/* 273 */           return this.mFuture.isCancelled();
                }

/* 305 */       public final boolean cancel(boolean mayInterruptIfRunning) {
/* 306 */           return this.mFuture.cancel(mayInterruptIfRunning);
                }

/* 320 */       public final Result get() throws ExecutionException, InterruptedException {
/* 321 */           return this.mFuture.get();
                }

/* 340 */       public final Result get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
/* 341 */           return this.mFuture.get(timeout, unit);
                }

/* 370 */       public final ModernAsyncTask<Params, Progress, Result> execute(Params... params) {
/* 371 */           return executeOnExecutor(sDefaultExecutor, params);
                }

/* 405 */       public final ModernAsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec, Params... params) {
/* 406 */           if (this.mStatus != Status.PENDING) {
                        switch (this.mStatus) {
                            case RUNNING:
/* 409 */                       throw new IllegalStateException("Cannot execute task: the task is already running.");
                            case FINISHED:
/* 412 */                       throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
                        }
                    }
/* 418 */           this.mStatus = Status.RUNNING;
/* 420 */           onPreExecute();
/* 422 */           this.mWorker.mParams = params;
/* 423 */           exec.execute(this.mFuture);
/* 425 */           return this;
                }

/* 432 */       public static void execute(Runnable runnable) {
/* 433 */           sDefaultExecutor.execute(runnable);
                }

/* 450 */       protected final void publishProgress(Progress... values) {
/* 451 */           if (!isCancelled()) {
/* 452 */               sHandler.obtainMessage(2, new AsyncTaskResult(this, values)).sendToTarget();
                    }
                }

/* 457 */       private void finish(Result result) {
/* 458 */           if (isCancelled()) {
/* 459 */               onCancelled(result);
                    } else {
/* 461 */               onPostExecute(result);
                    }
/* 463 */           this.mStatus = Status.FINISHED;
                }

/* 464 */       private static class InternalHandler extends Handler {
/* 465 */           private InternalHandler() {
                    }

/* 465 */           /* synthetic */ InternalHandler(ThreadFactoryC00211 x0) {
/* 466 */               this();
                    }

                    /* JADX WARN: Type inference fix 'apply assigned field type' failed
                    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
                    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
                    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                     */
                    @Override // android.os.Handler
/* 469 */           public void handleMessage(Message msg) {
/* 470 */               AsyncTaskResult result = (AsyncTaskResult) msg.obj;
                        switch (msg.what) {
                            case 1:
/* 474 */                       ModernAsyncTask.access$500(result.mTask, result.mData[0]);
                                break;
                            case 2:
/* 477 */                       result.mTask.onProgressUpdate(result.mData);
                                break;
                        }
                    }
                }

/* 481 */       private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
                    Params[] mParams;

/* 482 */           private WorkerRunnable() {
                    }

/* 482 */           /* synthetic */ WorkerRunnable(ThreadFactoryC00211 x0) {
/* 483 */               this();
                    }
                }

/* 490 */       private static class AsyncTaskResult<Data> {
                    final Data[] mData;
                    final ModernAsyncTask mTask;

/* 491 */           AsyncTaskResult(ModernAsyncTask task, Data... data) {
/* 493 */               this.mTask = task;
/* 494 */               this.mData = data;
                    }
                }
            }
