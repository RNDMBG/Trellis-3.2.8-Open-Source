            package com.google.android.exoplayer.text.ttml;

            import com.google.android.exoplayer.text.Subtitle;
            import com.google.android.exoplayer.util.Util;
            
            
    public final class TtmlSubtitle implements Subtitle {
                private final long[] eventTimesUs;
                private final TtmlNode root;
                private final long startTimeUs;

        public TtmlSubtitle(TtmlNode root, long startTimeUs) {
            this.root = root;
            this.startTimeUs = startTimeUs;
            this.eventTimesUs = root.getEventTimesUs();
                }

                @Override 
        public long getStartTime() {
            return this.startTimeUs;
                }

                @Override 
        public int getNextEventTimeIndex(long timeUs) {
            int index = Util.binarySearchCeil(this.eventTimesUs, timeUs - this.startTimeUs, false, false);
            if (index < this.eventTimesUs.length) {
                        return index;
                    }
                    return -1;
                }

                @Override 
        public int getEventTimeCount() {
            return this.eventTimesUs.length;
                }

                @Override 
        public long getEventTime(int index) {
            return this.eventTimesUs[index] + this.startTimeUs;
                }

                @Override 
        public long getLastEventTime() {
                    return (this.eventTimesUs.length == 0 ? -1L : this.eventTimesUs[this.eventTimesUs.length - 1]) + this.startTimeUs;
                }

                @Override 
        public String getText(long timeUs) {
            return this.root.getText(timeUs - this.startTimeUs);
                }
            }
