            package co.vine.android.recorder;

            import com.edisonwang.android.slog.SLog;
            
            
     public class ReusableHashAsyncTask extends HashAsyncTask {
                private final boolean mCanKeepRecording;
                private RecordingFile mComputedFile;
                private boolean mIsCompleted;
                private final BasicVineRecorder mRecorder;
                private Exception mStartingException;


        public ReusableHashAsyncTask(BasicVineRecorder recorder) {
            super(null);
            this.mIsCompleted = false;
            this.mStartingException = new Exception();
            this.mRecorder = recorder;
            this.mCanKeepRecording = this.mRecorder.canKeepRecording();
                }

                
                @Override 
        protected RecordingFile doInBackground(RecordingFile... params) {
                    return this.mCanKeepRecording ? super.doInBackground(params) : super.doInBackground(this.mRecorder.getFile());
                }

                @Override 
        public void onError(RecordingFile recordingFile, Exception e, RecordCompleteConsumer parent) {
            if (isCancelled()) {
                this.mIsCompleted = true;
                    } else {
                SLog.m452d("Pre-post processing error.", (Throwable) e);
                SLog.m452d("Pre-post was started here.", (Throwable) this.mStartingException);
                    }
                }

                @Override 
        public void onComplete(RecordingFile recordingFile, RecordCompleteConsumer parent) {
            if (!this.mCanKeepRecording) {
                this.mRecorder.setFinalFile(recordingFile);
                    }
            if (isCancelled()) {
                this.mComputedFile = recordingFile;
                    } else {
                run();
                    }
                }

                @Override 
        public void run() {
            Runnable onCompleteRunner = this.mRecorder.getOnCompleteConsumer();
            if (onCompleteRunner != null && !this.mCanKeepRecording) {
                onCompleteRunner.run();
                    } else {
                this.mIsCompleted = true;
                    }
                }

                @Override 
        public void resumeAll() {
            Runnable onCompleteRunner = this.mRecorder.getOnCompleteConsumer();
            if (onCompleteRunner != null && !this.mCanKeepRecording) {
                onCompleteRunner.run();
                    } else {
                run();
                    }
                }

                @Override 
        public void reset() {
            this.mIsCompleted = false;
                }

                @Override 
        public boolean isComplete() {
            return this.mIsCompleted;
                }

        public RecordingFile getComputedFile() {
            return this.mComputedFile;
                }
            }
