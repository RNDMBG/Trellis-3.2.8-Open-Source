            package com.google.android.exoplayer.dash.mpd;

            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public final class MediaPresentationDescription {
                public final long availabilityStartTime;
                public final long duration;
                public final boolean dynamic;
                public final long minBufferTime;
                public final long minUpdatePeriod;
                public final List<Period> periods;
                public final long timeShiftBufferDepth;
                public final UtcTimingElement utcTiming;

/* 43 */        public MediaPresentationDescription(long availabilityStartTime, long duration, long minBufferTime, boolean dynamic, long minUpdatePeriod, long timeShiftBufferDepth, UtcTimingElement utcTiming, List<Period> periods) {
/* 45 */            this.availabilityStartTime = availabilityStartTime;
/* 46 */            this.duration = duration;
/* 47 */            this.minBufferTime = minBufferTime;
/* 48 */            this.dynamic = dynamic;
/* 49 */            this.minUpdatePeriod = minUpdatePeriod;
/* 50 */            this.timeShiftBufferDepth = timeShiftBufferDepth;
/* 51 */            this.utcTiming = utcTiming;
/* 52 */            this.periods = Collections.unmodifiableList(periods);
                }
            }
