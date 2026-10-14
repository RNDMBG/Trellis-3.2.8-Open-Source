            package com.google.android.exoplayer.dash.mpd;

            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public final class AdaptationSet {
                public static final int TYPE_AUDIO = 1;
                public static final int TYPE_TEXT = 2;
                public static final int TYPE_UNKNOWN = -1;
                public static final int TYPE_VIDEO = 0;
                public final List<ContentProtection> contentProtections;

                /* JADX INFO: renamed from: id */
                public final int f876id;
                public final List<Representation> representations;
                public final int type;

/* 38 */        public AdaptationSet(int id, int type, List<Representation> representations, List<ContentProtection> contentProtections) {
/* 40 */            this.f876id = id;
/* 41 */            this.type = type;
/* 42 */            this.representations = Collections.unmodifiableList(representations);
/* 43 */            if (contentProtections == null) {
/* 44 */                this.contentProtections = Collections.emptyList();
                    } else {
/* 46 */                this.contentProtections = Collections.unmodifiableList(contentProtections);
                    }
                }

/* 50 */        public AdaptationSet(int id, int type, List<Representation> representations) {
/* 51 */            this(id, type, representations, null);
                }

/* 54 */        public boolean hasContentProtection() {
                    return !this.contentProtections.isEmpty();
                }
            }
