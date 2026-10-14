            package co.vine.android.recorder;

            import android.os.Handler;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class ProgressTimer {
                private static final int INTERVAL = 18;
                private final ProgressTimerRunnable mTask;
                private final Thread mTaskThread;

/* 19 */        public ProgressTimer(BasicVineRecorder controller, Handler handler, int thresholdMs) {
/* 21 */            this.mTask = new ProgressTimerRunnable(controller, handler, thresholdMs);
/* 22 */            this.mTaskThread = new Thread(this.mTask);
                }

/* 25 */        public void start() {
/* 26 */            this.mTaskThread.start();
                }

/* 29 */        public void release() {
/* 30 */            this.mTask.run = false;
/* 31 */            this.mTaskThread.interrupt();
                }

/* 41 */        public static class ProgressTimerRunnable implements Runnable {
                    private boolean hasNotifiedThreshold;
                    private final BasicVineRecorder mController;
                    private final Handler mHandler;
                    private final int mThresholdMs;
                    private int mProgress = -1;
                    public volatile boolean run = true;

/* 42 */            public ProgressTimerRunnable(BasicVineRecorder controller, Handler handler, int thresholdMs) {
/* 44 */                this.mController = controller;
/* 45 */                this.mHandler = handler;
/* 46 */                this.mThresholdMs = thresholdMs;
                    }

                    @Override // java.lang.Runnable
/* 51 */            public void run() {
                        try {
/* 52 */                    exec();
                        } catch (InterruptedException e) {
                        }
                    }

/* 60 */            private void exec() throws InterruptedException {
                        int progress;
/* 61 */                BasicVineRecorder controller = this.mController;
/* 62 */                while (this.run) {
/* 63 */                    long time = System.currentTimeMillis();
/* 64 */                    long temp = controller.getCurrentDuration();
                            if (temp < 0) {
/* 66 */                        progress = (int) (temp + time);
                            } else {
/* 68 */                        progress = (int) temp;
                            }
/* 70 */                    if (progress != this.mProgress) {
/* 71 */                        this.mProgress = progress;
/* 72 */                        controller.postProgressUpdate(this.mProgress);
                            }
/* 74 */                    if (!this.hasNotifiedThreshold && controller.isRecordingSegment() && this.mProgress >= this.mThresholdMs) {
/* 75 */                        this.hasNotifiedThreshold = true;
/* 76 */                        this.mHandler.post(new RunnableC04771(controller));
                            }
/* 83 */                    RecordConfigUtils.RecordConfig config = this.mController.getConfig();
/* 84 */                    if (config != null && this.mProgress >= config.maxDuration) {
/* 85 */                        this.mHandler.post(new RunnableC04782(controller));
/* 97 */                        return;
                            } else {
                                while (System.currentTimeMillis() - time < 18) {
/* 94 */                            Thread.sleep(5L);
                                }
                            }
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.recorder.ProgressTimer$ProgressTimerRunnable$1 */
/* 74 */            class RunnableC04771 implements Runnable {
                        final /* synthetic */ BasicVineRecorder val$controller;

/* 75 */                RunnableC04771(BasicVineRecorder basicVineRecorder) {
                            this.val$controller = basicVineRecorder;
                        }

                        @Override // java.lang.Runnable
/* 78 */                public void run() {
/* 79 */                    this.val$controller.onProgressThresholdReached();
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.recorder.ProgressTimer$ProgressTimerRunnable$2 */
/* 83 */            class RunnableC04782 implements Runnable {
                        final /* synthetic */ BasicVineRecorder val$controller;

/* 84 */                RunnableC04782(BasicVineRecorder basicVineRecorder) {
                            this.val$controller = basicVineRecorder;
                        }

                        @Override // java.lang.Runnable
/* 87 */                public void run() {
/* 88 */                    this.val$controller.onProgressMaxReached();
                        }
                    }
                }
            }
