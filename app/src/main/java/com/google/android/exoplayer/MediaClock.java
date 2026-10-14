            package com.google.android.exoplayer;

            import android.os.SystemClock;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class MediaClock {
                private long deltaUs;
                private boolean started;
                private long timeUs;

/* 24 */        MediaClock() {
                }

/* 43 */        public void start() {
/* 44 */            if (!this.started) {
/* 45 */                this.started = true;
/* 46 */                this.deltaUs = elapsedRealtimeMinus(this.timeUs);
                    }
                }

/* 53 */        public void stop() {
/* 54 */            if (this.started) {
/* 55 */                this.timeUs = elapsedRealtimeMinus(this.deltaUs);
/* 56 */                this.started = false;
                    }
                }

/* 63 */        public void setTimeUs(long timeUs) {
/* 64 */            this.timeUs = timeUs;
/* 65 */            this.deltaUs = elapsedRealtimeMinus(timeUs);
                }

/* 71 */        public long getTimeUs() {
                    return this.started ? elapsedRealtimeMinus(this.deltaUs) : this.timeUs;
                }

/* 75 */        private long elapsedRealtimeMinus(long microSeconds) {
/* 76 */            return (SystemClock.elapsedRealtime() * 1000) - microSeconds;
                }
            }
