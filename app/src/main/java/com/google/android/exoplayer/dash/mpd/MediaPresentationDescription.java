            package com.google.android.exoplayer.dash.mpd;

            import java.util.Collections;
            import java.util.List;
            
            
    public final class MediaPresentationDescription {
                public final long availabilityStartTime;
                public final long duration;
                public final boolean dynamic;
                public final long minBufferTime;
                public final long minUpdatePeriod;
                public final List<Period> periods;
                public final long timeShiftBufferDepth;
                public final UtcTimingElement utcTiming;

        public MediaPresentationDescription(long availabilityStartTime, long duration, long minBufferTime, boolean dynamic, long minUpdatePeriod, long timeShiftBufferDepth, UtcTimingElement utcTiming, List<Period> periods) {
            this.availabilityStartTime = availabilityStartTime;
            this.duration = duration;
            this.minBufferTime = minBufferTime;
            this.dynamic = dynamic;
            this.minUpdatePeriod = minUpdatePeriod;
            this.timeShiftBufferDepth = timeShiftBufferDepth;
            this.utcTiming = utcTiming;
            this.periods = Collections.unmodifiableList(periods);
                }
            }
