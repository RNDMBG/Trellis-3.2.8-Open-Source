            package co.vine.android.recorder;

            import android.os.AsyncTask;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public abstract class BaseFinishProcessTask extends AsyncTask<Void, Integer, Void> {
                public boolean isRunning;
                protected int mCurrentMessage;
                private int mLastProgress;
                protected long mWaitStartTime;
                public final Runnable onComplete;
                public final boolean releasePreview;
                public final boolean saveSession;
                protected final String tag;


/* 20 */        public BaseFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
/* 22 */            this.tag = tag;
/* 23 */            this.onComplete = onComplete;
/* 24 */            this.releasePreview = releasePreview;
/* 25 */            this.saveSession = saveSession;
                }

                /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 29 */        protected void onPostExecute(Void aVoid) {
/* 30 */            SLog.m447d("Waited for {} ms", Long.valueOf(System.currentTimeMillis() - this.mWaitStartTime));
/* 31 */            if (this.onComplete != null) {
/* 32 */                this.onComplete.run();
                    }
/* 34 */            this.isRunning = false;
                }

                @Override // android.os.AsyncTask
/* 38 */        protected void onPreExecute() {
/* 39 */            this.mCurrentMessage = 1;
/* 40 */            this.mWaitStartTime = System.currentTimeMillis();
/* 41 */            this.isRunning = true;
                }

/* 44 */        public void publish(int value) {
/* 45 */            this.mLastProgress = value;
/* 46 */            publishProgress(Integer.valueOf(value));
                }

/* 49 */        public void publishGreater(int value) {
/* 50 */            if (value > this.mLastProgress) {
/* 51 */                publish(value);
                    }
                }
            }
