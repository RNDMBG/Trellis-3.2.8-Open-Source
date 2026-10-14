            package co.vine.android.recorder;
            
            
     public class RelativeTime {
                private long end;
                public final long start;

        public void setEnd(long end) {
            this.end = end;
                }

        public long getEnd() {
            return this.end;
                }

        public RelativeTime(long start) {
            this.start = start;
                }

        public long getDuration(int frameRate) {
            int msPerFrame = 1000 / frameRate;
            long duration = this.end - this.start;
                    if (duration < msPerFrame) {
                return msPerFrame + 1;
                    }
            return duration;
                }
            }
