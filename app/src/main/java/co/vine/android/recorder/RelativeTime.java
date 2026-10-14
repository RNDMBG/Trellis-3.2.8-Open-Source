            package co.vine.android.recorder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 9 */     public class RelativeTime {
                private long end;
                public final long start;

/* 10 */        public void setEnd(long end) {
/* 11 */            this.end = end;
                }

/* 14 */        public long getEnd() {
/* 15 */            return this.end;
                }

/* 19 */        public RelativeTime(long start) {
/* 21 */            this.start = start;
                }

/* 24 */        public long getDuration(int frameRate) {
/* 25 */            int msPerFrame = 1000 / frameRate;
/* 26 */            long duration = this.end - this.start;
                    if (duration < msPerFrame) {
/* 28 */                return msPerFrame + 1;
                    }
/* 30 */            return duration;
                }
            }
