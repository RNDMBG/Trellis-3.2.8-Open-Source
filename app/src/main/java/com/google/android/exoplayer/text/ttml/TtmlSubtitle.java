            package com.google.android.exoplayer.text.ttml;

            import com.google.android.exoplayer.text.Subtitle;
            import com.google.android.exoplayer.util.Util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public final class TtmlSubtitle implements Subtitle {
                private final long[] eventTimesUs;
                private final TtmlNode root;
                private final long startTimeUs;

/* 29 */        public TtmlSubtitle(TtmlNode root, long startTimeUs) {
/* 31 */            this.root = root;
/* 32 */            this.startTimeUs = startTimeUs;
/* 33 */            this.eventTimesUs = root.getEventTimesUs();
                }

                @Override // com.google.android.exoplayer.text.Subtitle
/* 37 */        public long getStartTime() {
/* 38 */            return this.startTimeUs;
                }

                @Override // com.google.android.exoplayer.text.Subtitle
/* 42 */        public int getNextEventTimeIndex(long timeUs) {
/* 43 */            int index = Util.binarySearchCeil(this.eventTimesUs, timeUs - this.startTimeUs, false, false);
/* 44 */            if (index < this.eventTimesUs.length) {
                        return index;
                    }
                    return -1;
                }

                @Override // com.google.android.exoplayer.text.Subtitle
/* 48 */        public int getEventTimeCount() {
/* 49 */            return this.eventTimesUs.length;
                }

                @Override // com.google.android.exoplayer.text.Subtitle
/* 53 */        public long getEventTime(int index) {
/* 54 */            return this.eventTimesUs[index] + this.startTimeUs;
                }

                @Override // com.google.android.exoplayer.text.Subtitle
/* 58 */        public long getLastEventTime() {
                    return (this.eventTimesUs.length == 0 ? -1L : this.eventTimesUs[this.eventTimesUs.length - 1]) + this.startTimeUs;
                }

                @Override // com.google.android.exoplayer.text.Subtitle
/* 63 */        public String getText(long timeUs) {
/* 64 */            return this.root.getText(timeUs - this.startTimeUs);
                }
            }
