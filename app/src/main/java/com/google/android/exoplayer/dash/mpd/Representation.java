            package com.google.android.exoplayer.dash.mpd;

            import android.net.Uri;
            import com.google.android.exoplayer.chunk.Format;
            import com.google.android.exoplayer.dash.DashSegmentIndex;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public abstract class Representation {
                public final String contentId;
                public final Format format;
                private final RangedUri initializationUri;
                public final long periodDurationMs;
                public final long periodStartMs;
                public final long presentationTimeOffsetMs;
                public final long revisionId;

                public abstract DashSegmentIndex getIndex();

                public abstract RangedUri getIndexUri();

/* 27 */        /* synthetic */ Representation(long x0, long x1, String x2, long x3, Format x4, SegmentBase x5, Object x6) {
/* 28 */            this(x0, x1, x2, x3, x4, x5);
                }

/* 83 */        public static Representation newInstance(long periodStartMs, long periodDurationMs, String contentId, long revisionId, Format format, SegmentBase segmentBase) {
/* 84 */            if (segmentBase instanceof SegmentBase.SingleSegmentBase) {
/* 85 */                return new SingleSegmentRepresentation(periodStartMs, periodDurationMs, contentId, revisionId, format, (SegmentBase.SingleSegmentBase) segmentBase, -1L);
                    }
/* 87 */            if (segmentBase instanceof SegmentBase.MultiSegmentBase) {
/* 88 */                return new MultiSegmentRepresentation(periodStartMs, periodDurationMs, contentId, revisionId, format, (SegmentBase.MultiSegmentBase) segmentBase);
                    }
/* 91 */            throw new IllegalArgumentException("segmentBase must be of type SingleSegmentBase or MultiSegmentBase");
                }

/* 96 */        private Representation(long periodStartMs, long periodDurationMs, String contentId, long revisionId, Format format, SegmentBase segmentBase) {
/* 98 */            this.periodStartMs = periodStartMs;
/* 99 */            this.periodDurationMs = periodDurationMs;
/* 100 */           this.contentId = contentId;
/* 101 */           this.revisionId = revisionId;
/* 102 */           this.format = format;
/* 103 */           this.initializationUri = segmentBase.getInitialization(this);
/* 104 */           this.presentationTimeOffsetMs = (segmentBase.presentationTimeOffset * 1000) / segmentBase.timescale;
                }

/* 113 */       public RangedUri getInitializationUri() {
/* 114 */           return this.initializationUri;
                }

/* 139 */       public String getCacheKey() {
/* 140 */           return this.contentId + "." + this.format.f875id + "." + this.revisionId;
                }

/* 175 */       public static class SingleSegmentRepresentation extends Representation {
                    public final long contentLength;
                    private final RangedUri indexUri;
                    public final Uri uri;

/* 176 */           public static SingleSegmentRepresentation newInstance(long periodStartMs, long periodDurationMs, String contentId, long revisionId, Format format, Uri uri, long initializationStart, long initializationEnd, long indexStart, long indexEnd, long contentLength) {
/* 177 */               RangedUri rangedUri = new RangedUri(uri, null, initializationStart, 1 + (initializationEnd - initializationStart));
/* 179 */               SegmentBase.SingleSegmentBase segmentBase = new SegmentBase.SingleSegmentBase(rangedUri, 1L, 0L, uri, indexStart, (indexEnd - indexStart) + 1);
/* 181 */               return new SingleSegmentRepresentation(periodStartMs, periodDurationMs, contentId, revisionId, format, segmentBase, contentLength);
                    }

/* 196 */           public SingleSegmentRepresentation(long periodStartMs, long periodDurationMs, String contentId, long revisionId, Format format, SegmentBase.SingleSegmentBase segmentBase, long contentLength) {
/* 197 */               super(periodStartMs, periodDurationMs, contentId, revisionId, format, segmentBase, null);
/* 198 */               this.uri = segmentBase.uri;
/* 199 */               this.indexUri = segmentBase.getIndex();
/* 200 */               this.contentLength = contentLength;
                    }

                    @Override // com.google.android.exoplayer.dash.mpd.Representation
/* 204 */           public RangedUri getIndexUri() {
/* 205 */               return this.indexUri;
                    }

                    @Override // com.google.android.exoplayer.dash.mpd.Representation
/* 209 */           public DashSegmentIndex getIndex() {
/* 210 */               return null;
                    }
                }

/* 232 */       public static class MultiSegmentRepresentation extends Representation implements DashSegmentIndex {
                    private final SegmentBase.MultiSegmentBase segmentBase;

/* 233 */           public MultiSegmentRepresentation(long periodStartMs, long periodDurationMs, String contentId, long revisionId, Format format, SegmentBase.MultiSegmentBase segmentBase) {
/* 234 */               super(periodStartMs, periodDurationMs, contentId, revisionId, format, segmentBase, null);
/* 235 */               this.segmentBase = segmentBase;
                    }

                    @Override // com.google.android.exoplayer.dash.mpd.Representation
/* 239 */           public RangedUri getIndexUri() {
/* 240 */               return null;
                    }

                    @Override // com.google.android.exoplayer.dash.mpd.Representation
/* 244 */           public DashSegmentIndex getIndex() {
/* 245 */               return this;
                    }

                    @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 251 */           public RangedUri getSegmentUrl(int segmentIndex) {
/* 252 */               return this.segmentBase.getSegmentUrl(this, segmentIndex);
                    }

                    @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 256 */           public int getSegmentNum(long timeUs) {
/* 257 */               return this.segmentBase.getSegmentNum(timeUs);
                    }

                    @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 261 */           public long getTimeUs(int segmentIndex) {
/* 262 */               return this.segmentBase.getSegmentTimeUs(segmentIndex);
                    }

                    @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 266 */           public long getDurationUs(int segmentIndex) {
/* 267 */               return this.segmentBase.getSegmentDurationUs(segmentIndex);
                    }

                    @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 271 */           public int getFirstSegmentNum() {
/* 272 */               return this.segmentBase.getFirstSegmentNum();
                    }

                    @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 276 */           public int getLastSegmentNum() {
/* 277 */               return this.segmentBase.getLastSegmentNum();
                    }
                }
            }
