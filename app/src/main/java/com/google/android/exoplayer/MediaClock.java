            package com.google.android.exoplayer;

            import android.os.SystemClock;
            
            
    class MediaClock {
                private long deltaUs;
                private boolean started;
                private long timeUs;

        MediaClock() {
                }

        public void start() {
            if (!this.started) {
                this.started = true;
                this.deltaUs = elapsedRealtimeMinus(this.timeUs);
                    }
                }

        public void stop() {
            if (this.started) {
                this.timeUs = elapsedRealtimeMinus(this.deltaUs);
                this.started = false;
                    }
                }

        public void setTimeUs(long timeUs) {
            this.timeUs = timeUs;
            this.deltaUs = elapsedRealtimeMinus(timeUs);
                }

        public long getTimeUs() {
                    return this.started ? elapsedRealtimeMinus(this.deltaUs) : this.timeUs;
                }

        private long elapsedRealtimeMinus(long microSeconds) {
            return (SystemClock.elapsedRealtime() * 1000) - microSeconds;
                }
            }
