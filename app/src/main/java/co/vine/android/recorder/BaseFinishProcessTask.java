            package co.vine.android.recorder;

            import android.os.AsyncTask;
            import com.edisonwang.android.slog.SLog;
            
            
     public abstract class BaseFinishProcessTask extends AsyncTask<Void, Integer, Void> {
                public boolean isRunning;
                protected int mCurrentMessage;
                private int mLastProgress;
                protected long mWaitStartTime;
                public final Runnable onComplete;
                public final boolean releasePreview;
                public final boolean saveSession;
                protected final String tag;


        public BaseFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
            this.tag = tag;
            this.onComplete = onComplete;
            this.releasePreview = releasePreview;
            this.saveSession = saveSession;
                }

                
        protected void onPostExecute(Void aVoid) {
            SLog.m447d("Waited for {} ms", Long.valueOf(System.currentTimeMillis() - this.mWaitStartTime));
            if (this.onComplete != null) {
                this.onComplete.run();
                    }
            this.isRunning = false;
                }

                @Override 
        protected void onPreExecute() {
            this.mCurrentMessage = 1;
            this.mWaitStartTime = System.currentTimeMillis();
            this.isRunning = true;
                }

        public void publish(int value) {
            this.mLastProgress = value;
            publishProgress(Integer.valueOf(value));
                }

        public void publishGreater(int value) {
            if (value > this.mLastProgress) {
                publish(value);
                    }
                }
            }
