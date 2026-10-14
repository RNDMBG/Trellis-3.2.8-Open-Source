            package co.vine.android.recorder;

            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class RecordClock {
                private volatile long mAudioTimeRecorded;
                private volatile int mAudioTimestampUs;
                private long mFrameTimeUs;
                private long mLastAudioTimestamp;
                private long mStartTime;
                private volatile long mVideoTimestampUs;

/* 18 */        public long getVideoTimestampUs() {
/* 19 */            return this.mVideoTimestampUs;
                }

/* 22 */        public int getAudioTimestampUs() {
/* 23 */            return this.mAudioTimestampUs;
                }

/* 26 */        public boolean isVideoAhead() {
                    return getVideoTimestampUs() > ((long) getAudioTimestampUs());
                }

/* 30 */        public void setVideoTimestampUs(long timestampUs) {
/* 31 */            this.mVideoTimestampUs = timestampUs;
                }

/* 34 */        public void printState() {
/* 35 */            SLog.m466i("Current timestamps: {}, {}.", Long.valueOf(this.mVideoTimestampUs), Integer.valueOf(this.mAudioTimestampUs));
                }

/* 38 */        public void setAudioTimestampUs(int timestampUs) {
/* 39 */            this.mAudioTimestampUs = timestampUs;
                }

/* 42 */        public void resetStartTime() {
/* 43 */            this.mStartTime = 0L;
                }

/* 46 */        public void setFrameTimeUs(long frameTimeUs) {
/* 47 */            this.mFrameTimeUs = frameTimeUs;
                }

/* 50 */        public long getFrameTimeUs() {
/* 51 */            return this.mFrameTimeUs;
                }

/* 54 */        public void startSegment() {
/* 55 */            this.mStartTime = System.currentTimeMillis();
                }

/* 58 */        public void onFrameIncrement() {
/* 59 */            this.mVideoTimestampUs += this.mFrameTimeUs;
                }

/* 62 */        public boolean wasAudioJustRecorded() {
                    return System.nanoTime() - this.mAudioTimeRecorded < ((long) RecordConfigUtils.AUDIO_WAIT_THRESHOLD_NS);
                }

/* 66 */        public void updateAudioTimestamp(boolean resetAudioRecordTime, int newStamp) {
/* 67 */            if (this.mAudioTimestampUs != newStamp) {
/* 68 */                this.mAudioTimestampUs = newStamp;
/* 69 */                if (resetAudioRecordTime) {
/* 70 */                    this.mAudioTimeRecorded = -1L;
                        } else {
/* 72 */                    this.mAudioTimeRecorded = System.nanoTime();
                        }
                    }
                }

/* 78 */        public long onNewVideoFrame() {
                    long timestamp;
/* 79 */            int audioTimestamp = getAudioTimestampUs();
/* 80 */            if (audioTimestamp == 0) {
                        if (this.mStartTime <= 0) {
                            return 0L;
                        }
                        long timestamp2 = (System.currentTimeMillis() - this.mStartTime) * 1000;
/* 96 */                return timestamp2;
                    }
                    if (this.mLastAudioTimestamp == audioTimestamp) {
/* 84 */                long timestamp3 = ((long) audioTimestamp) + this.mFrameTimeUs;
/* 96 */                return timestamp3;
                    }
                    if (this.mAudioTimeRecorded > 0) {
/* 87 */                long offset = (System.nanoTime() - this.mAudioTimeRecorded) / 1000;
/* 88 */                timestamp = offset + ((long) audioTimestamp);
/* 89 */                SLog.m447d("Offset {}.", Long.valueOf(offset));
                    } else {
                        timestamp = audioTimestamp;
                    }
/* 93 */            this.mLastAudioTimestamp = audioTimestamp;
/* 96 */            return timestamp;
                }
            }
