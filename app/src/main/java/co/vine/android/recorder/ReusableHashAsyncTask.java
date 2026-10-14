            package co.vine.android.recorder;

            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 6 */     public class ReusableHashAsyncTask extends HashAsyncTask {
                private final boolean mCanKeepRecording;
                private RecordingFile mComputedFile;
                private boolean mIsCompleted;
                private final BasicVineRecorder mRecorder;
                private Exception mStartingException;


/* 18 */        public ReusableHashAsyncTask(BasicVineRecorder recorder) {
/* 19 */            super(null);
/* 12 */            this.mIsCompleted = false;
/* 20 */            this.mStartingException = new Exception();
/* 21 */            this.mRecorder = recorder;
/* 22 */            this.mCanKeepRecording = this.mRecorder.canKeepRecording();
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // co.vine.android.recorder.HashAsyncTask
/* 26 */        protected RecordingFile doInBackground(RecordingFile... params) {
                    return this.mCanKeepRecording ? super.doInBackground(params) : super.doInBackground(this.mRecorder.getFile());
                }

                @Override // co.vine.android.recorder.HashAsyncTask, co.vine.android.recorder.RecordCompleteConsumer
/* 35 */        public void onError(RecordingFile recordingFile, Exception e, RecordCompleteConsumer parent) {
/* 36 */            if (isCancelled()) {
/* 37 */                this.mIsCompleted = true;
                    } else {
/* 40 */                SLog.m452d("Pre-post processing error.", (Throwable) e);
/* 41 */                SLog.m452d("Pre-post was started here.", (Throwable) this.mStartingException);
                    }
                }

                @Override // co.vine.android.recorder.HashAsyncTask, co.vine.android.recorder.RecordCompleteConsumer
/* 45 */        public void onComplete(RecordingFile recordingFile, RecordCompleteConsumer parent) {
/* 46 */            if (!this.mCanKeepRecording) {
/* 47 */                this.mRecorder.setFinalFile(recordingFile);
                    }
/* 50 */            if (isCancelled()) {
/* 51 */                this.mComputedFile = recordingFile;
                    } else {
/* 53 */                run();
                    }
                }

                @Override // co.vine.android.recorder.RecordCompleteConsumer, java.lang.Runnable
/* 58 */        public void run() {
/* 59 */            Runnable onCompleteRunner = this.mRecorder.getOnCompleteConsumer();
/* 60 */            if (onCompleteRunner != null && !this.mCanKeepRecording) {
/* 61 */                onCompleteRunner.run();
                    } else {
/* 63 */                this.mIsCompleted = true;
                    }
                }

                @Override // co.vine.android.recorder.RecordCompleteConsumer
/* 68 */        public void resumeAll() {
/* 69 */            Runnable onCompleteRunner = this.mRecorder.getOnCompleteConsumer();
/* 70 */            if (onCompleteRunner != null && !this.mCanKeepRecording) {
/* 71 */                onCompleteRunner.run();
                    } else {
/* 73 */                run();
                    }
                }

                @Override // co.vine.android.recorder.RecordCompleteConsumer
/* 78 */        public void reset() {
/* 79 */            this.mIsCompleted = false;
                }

                @Override // co.vine.android.recorder.RecordCompleteConsumer
/* 83 */        public boolean isComplete() {
/* 84 */            return this.mIsCompleted;
                }

/* 87 */        public RecordingFile getComputedFile() {
/* 88 */            return this.mComputedFile;
                }
            }
