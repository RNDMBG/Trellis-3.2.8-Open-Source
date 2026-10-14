            package co.vine.android.embed.player;

            import android.os.SystemClock;
            
            
    public class MediaClock {
                private long mAudioPresentationTimeUs;
                private long mCurrentOperationStartMs;
                private long mLastOperationStartMs;
                private long mPositionSetTimeMs;
                private long mPositionUs;
                private long mVideoPresentationTimeUs;

        public void reset() {
            this.mCurrentOperationStartMs = 0L;
            this.mLastOperationStartMs = 0L;
            this.mAudioPresentationTimeUs = 0L;
            this.mVideoPresentationTimeUs = 0L;
            this.mPositionSetTimeMs = 0L;
            this.mPositionUs = 0L;
                }

        public void setPosition(long positionUs) {
            this.mPositionUs = positionUs;
            this.mPositionSetTimeMs = SystemClock.elapsedRealtime();
                }

        public long positionUs() {
            return this.mPositionUs;
                }

        public void operationStart() {
            this.mCurrentOperationStartMs = SystemClock.elapsedRealtime();
                }

        public void operationEnd() {
            this.mLastOperationStartMs = this.mCurrentOperationStartMs;
                }

        public long getOperationStartMs() {
            return this.mCurrentOperationStartMs;
                }

        public boolean isFirstOperation() {
                    return this.mLastOperationStartMs <= 0;
                }

        public long timeSinceLastOperationStartUs() {
            return (SystemClock.elapsedRealtime() - this.mLastOperationStartMs) * 1000;
                }

        public long timeSincePositionSetUs() {
            return (SystemClock.elapsedRealtime() - this.mPositionSetTimeMs) * 1000;
                }

        public long getPositionSetTimeMs() {
            return this.mPositionSetTimeMs;
                }

        public long getAudioPresentationTimeUs() {
            return this.mAudioPresentationTimeUs;
                }

        public void incrementAudioPresentationTimeUs(long timeUs) {
            this.mAudioPresentationTimeUs += timeUs;
                }

        public void setVideoPositionUs(long presentationTimeUs) {
            this.mVideoPresentationTimeUs = presentationTimeUs;
                }
            }
