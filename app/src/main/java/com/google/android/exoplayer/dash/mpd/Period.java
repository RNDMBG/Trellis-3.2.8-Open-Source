            package com.google.android.exoplayer.dash.mpd;

            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public final class Period {
                public final List<AdaptationSet> adaptationSets;
                public final long durationMs;

                /* JADX INFO: renamed from: id */
                public final String f877id;
                public final long startMs;

/* 51 */        public Period(String id, long start, long duration, List<AdaptationSet> adaptationSets) {
/* 53 */            this.f877id = id;
/* 54 */            this.startMs = start;
/* 55 */            this.durationMs = duration;
/* 56 */            this.adaptationSets = Collections.unmodifiableList(adaptationSets);
                }
            }
