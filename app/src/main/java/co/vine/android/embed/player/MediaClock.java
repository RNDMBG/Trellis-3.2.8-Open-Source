            package co.vine.android.embed.player;

            import android.os.SystemClock;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class MediaClock {
                private long mAudioPresentationTimeUs;
                private long mCurrentOperationStartMs;
                private long mLastOperationStartMs;
                private long mPositionSetTimeMs;
                private long mPositionUs;
                private long mVideoPresentationTimeUs;

/* 17 */        public void reset() {
/* 18 */            this.mCurrentOperationStartMs = 0L;
/* 19 */            this.mLastOperationStartMs = 0L;
/* 20 */            this.mAudioPresentationTimeUs = 0L;
/* 21 */            this.mVideoPresentationTimeUs = 0L;
/* 22 */            this.mPositionSetTimeMs = 0L;
/* 23 */            this.mPositionUs = 0L;
                }

/* 26 */        public void setPosition(long positionUs) {
/* 27 */            this.mPositionUs = positionUs;
/* 28 */            this.mPositionSetTimeMs = SystemClock.elapsedRealtime();
                }

/* 31 */        public long positionUs() {
/* 32 */            return this.mPositionUs;
                }

/* 35 */        public void operationStart() {
/* 36 */            this.mCurrentOperationStartMs = SystemClock.elapsedRealtime();
                }

/* 39 */        public void operationEnd() {
/* 40 */            this.mLastOperationStartMs = this.mCurrentOperationStartMs;
                }

/* 43 */        public long getOperationStartMs() {
/* 44 */            return this.mCurrentOperationStartMs;
                }

/* 47 */        public boolean isFirstOperation() {
                    return this.mLastOperationStartMs <= 0;
                }

/* 51 */        public long timeSinceLastOperationStartUs() {
/* 52 */            return (SystemClock.elapsedRealtime() - this.mLastOperationStartMs) * 1000;
                }

/* 55 */        public long timeSincePositionSetUs() {
/* 56 */            return (SystemClock.elapsedRealtime() - this.mPositionSetTimeMs) * 1000;
                }

/* 59 */        public long getPositionSetTimeMs() {
/* 60 */            return this.mPositionSetTimeMs;
                }

/* 63 */        public long getAudioPresentationTimeUs() {
/* 64 */            return this.mAudioPresentationTimeUs;
                }

/* 67 */        public void incrementAudioPresentationTimeUs(long timeUs) {
/* 68 */            this.mAudioPresentationTimeUs += timeUs;
                }

/* 71 */        public void setVideoPositionUs(long presentationTimeUs) {
/* 72 */            this.mVideoPresentationTimeUs = presentationTimeUs;
                }
            }
