            package co.vine.android.recorder;

            import android.os.Handler;
            
            
    public class ProgressTimer {
                private static final int INTERVAL = 18;
                private final ProgressTimerRunnable mTask;
                private final Thread mTaskThread;

        public ProgressTimer(BasicVineRecorder controller, Handler handler, int thresholdMs) {
            this.mTask = new ProgressTimerRunnable(controller, handler, thresholdMs);
            this.mTaskThread = new Thread(this.mTask);
                }

        public void start() {
            this.mTaskThread.start();
                }

        public void release() {
            this.mTask.run = false;
            this.mTaskThread.interrupt();
                }

        public static class ProgressTimerRunnable implements Runnable {
                    private boolean hasNotifiedThreshold;
                    private final BasicVineRecorder mController;
                    private final Handler mHandler;
                    private final int mThresholdMs;
                    private int mProgress = -1;
                    public volatile boolean run = true;

            public ProgressTimerRunnable(BasicVineRecorder controller, Handler handler, int thresholdMs) {
                this.mController = controller;
                this.mHandler = handler;
                this.mThresholdMs = thresholdMs;
                    }

                    @Override 
            public void run() {
                        try {
                    exec();
                        } catch (InterruptedException e) {
                        }
                    }

            private void exec() throws InterruptedException {
                        int progress;
                BasicVineRecorder controller = this.mController;
                while (this.run) {
                    long time = System.currentTimeMillis();
                    long temp = controller.getCurrentDuration();
                            if (temp < 0) {
                        progress = (int) (temp + time);
                            } else {
                        progress = (int) temp;
                            }
                    if (progress != this.mProgress) {
                        this.mProgress = progress;
                        controller.postProgressUpdate(this.mProgress);
                            }
                    if (!this.hasNotifiedThreshold && controller.isRecordingSegment() && this.mProgress >= this.mThresholdMs) {
                        this.hasNotifiedThreshold = true;
                        this.mHandler.post(new RunnableC04771(controller));
                            }
                    RecordConfigUtils.RecordConfig config = this.mController.getConfig();
                    if (config != null && this.mProgress >= config.maxDuration) {
                        this.mHandler.post(new RunnableC04782(controller));
                        return;
                            } else {
                                while (System.currentTimeMillis() - time < 18) {
                            Thread.sleep(5L);
                                }
                            }
                        }
                    }

                    
            class RunnableC04771 implements Runnable {
                        final  BasicVineRecorder val$controller;

                RunnableC04771(BasicVineRecorder basicVineRecorder) {
                            this.val$controller = basicVineRecorder;
                        }

                        @Override 
                public void run() {
                    this.val$controller.onProgressThresholdReached();
                        }
                    }

                    
            class RunnableC04782 implements Runnable {
                        final  BasicVineRecorder val$controller;

                RunnableC04782(BasicVineRecorder basicVineRecorder) {
                            this.val$controller = basicVineRecorder;
                        }

                        @Override 
                public void run() {
                    this.val$controller.onProgressMaxReached();
                        }
                    }
                }
            }
