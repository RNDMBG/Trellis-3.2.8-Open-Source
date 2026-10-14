            package co.vine.android.recorder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class RecordState {
                private volatile RecordSegment mCurrentSegment;
                private boolean mIsRecordingEnded;
                private RecordSegment mLastAudioRecordingSegment;
                private RecordSegment mLastVideoSegment;
                private volatile boolean mRecordingAudio;
                private volatile boolean mCollectMoreAudio = true;
                private volatile boolean isRecordingStarted = false;

/* 15 */        public boolean collectMoreAudio() {
/* 16 */            return this.mCollectMoreAudio;
                }

/* 19 */        public boolean recordingAudio() {
/* 20 */            return this.mRecordingAudio;
                }

/* 23 */        public boolean isEnded() {
/* 24 */            return this.mIsRecordingEnded;
                }

/* 27 */        public void setRecordingAudio(boolean recordingAudio) {
/* 28 */            this.mRecordingAudio = recordingAudio;
                }

/* 31 */        public RecordSegment getCurrentSegment() {
/* 32 */            return this.mCurrentSegment;
                }

/* 35 */        public void setCurrentSegment(RecordSegment currentSegment) {
/* 36 */            this.mCurrentSegment = currentSegment;
                }

/* 39 */        public RecordSegment getLastVideoSegment() {
/* 40 */            return this.mLastVideoSegment;
                }

/* 43 */        public void setLastVideoSegment(RecordSegment mLastVideoSegment) {
/* 44 */            this.mLastVideoSegment = mLastVideoSegment;
                }

/* 47 */        public void setEnded(boolean isRecordingEnded) {
/* 48 */            this.mIsRecordingEnded = isRecordingEnded;
                }

/* 51 */        public RecordSegment getLastAudioRecordingSegment() {
/* 52 */            return this.mLastAudioRecordingSegment;
                }

/* 55 */        public void setLastAudioRecordingSegment(RecordSegment lastAudioRecordingSegment) {
/* 56 */            this.mLastAudioRecordingSegment = lastAudioRecordingSegment;
                }

/* 59 */        public void setCollectMoreAudio(boolean collectMoreAudio) {
/* 60 */            this.mCollectMoreAudio = collectMoreAudio;
                }

/* 63 */        public boolean isStarted() {
/* 64 */            return this.isRecordingStarted;
                }

/* 67 */        public void setStarted(boolean isStarted) {
/* 68 */            this.isRecordingStarted = isStarted;
                }
            }
