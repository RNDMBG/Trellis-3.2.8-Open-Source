            package com.google.android.exoplayer.dash.mpd;

            import java.util.Collections;
            import java.util.List;
            
            
    public final class Period {
                public final List<AdaptationSet> adaptationSets;
                public final long durationMs;

                
                public final String f877id;
                public final long startMs;

        public Period(String id, long start, long duration, List<AdaptationSet> adaptationSets) {
            this.f877id = id;
            this.startMs = start;
            this.durationMs = duration;
            this.adaptationSets = Collections.unmodifiableList(adaptationSets);
                }
            }
