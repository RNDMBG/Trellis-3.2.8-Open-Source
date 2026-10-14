            package com.google.android.exoplayer.dash.mpd;

            import android.net.Uri;
            import com.google.android.exoplayer.chunk.Format;
            import com.google.android.exoplayer.dash.DashSegmentIndex;
            
            
    public abstract class Representation {
                public final String contentId;
                public final Format format;
                private final RangedUri initializationUri;
                public final long periodDurationMs;
                public final long periodStartMs;
                public final long presentationTimeOffsetMs;
                public final long revisionId;

                public abstract DashSegmentIndex getIndex();

                public abstract RangedUri getIndexUri();

         Representation(long x0, long x1, String x2, long x3, Format x4, SegmentBase x5, Object x6) {
            this(x0, x1, x2, x3, x4, x5);
                }

        public static Representation newInstance(long periodStartMs, long periodDurationMs, String contentId, long revisionId, Format format, SegmentBase segmentBase) {
            if (segmentBase instanceof SegmentBase.SingleSegmentBase) {
                return new SingleSegmentRepresentation(periodStartMs, periodDurationMs, contentId, revisionId, format, (SegmentBase.SingleSegmentBase) segmentBase, -1L);
                    }
            if (segmentBase instanceof SegmentBase.MultiSegmentBase) {
                return new MultiSegmentRepresentation(periodStartMs, periodDurationMs, contentId, revisionId, format, (SegmentBase.MultiSegmentBase) segmentBase);
                    }
            throw new IllegalArgumentException("segmentBase must be of type SingleSegmentBase or MultiSegmentBase");
                }

        private Representation(long periodStartMs, long periodDurationMs, String contentId, long revisionId, Format format, SegmentBase segmentBase) {
            this.periodStartMs = periodStartMs;
            this.periodDurationMs = periodDurationMs;
           this.contentId = contentId;
           this.revisionId = revisionId;
           this.format = format;
           this.initializationUri = segmentBase.getInitialization(this);
           this.presentationTimeOffsetMs = (segmentBase.presentationTimeOffset * 1000) / segmentBase.timescale;
                }

       public RangedUri getInitializationUri() {
           return this.initializationUri;
                }

       public String getCacheKey() {
           return this.contentId + "." + this.format.f875id + "." + this.revisionId;
                }

       public static class SingleSegmentRepresentation extends Representation {
                    public final long contentLength;
                    private final RangedUri indexUri;
                    public final Uri uri;

           public static SingleSegmentRepresentation newInstance(long periodStartMs, long periodDurationMs, String contentId, long revisionId, Format format, Uri uri, long initializationStart, long initializationEnd, long indexStart, long indexEnd, long contentLength) {
               RangedUri rangedUri = new RangedUri(uri, null, initializationStart, 1 + (initializationEnd - initializationStart));
               SegmentBase.SingleSegmentBase segmentBase = new SegmentBase.SingleSegmentBase(rangedUri, 1L, 0L, uri, indexStart, (indexEnd - indexStart) + 1);
               return new SingleSegmentRepresentation(periodStartMs, periodDurationMs, contentId, revisionId, format, segmentBase, contentLength);
                    }

           public SingleSegmentRepresentation(long periodStartMs, long periodDurationMs, String contentId, long revisionId, Format format, SegmentBase.SingleSegmentBase segmentBase, long contentLength) {
               super(periodStartMs, periodDurationMs, contentId, revisionId, format, segmentBase, null);
               this.uri = segmentBase.uri;
               this.indexUri = segmentBase.getIndex();
               this.contentLength = contentLength;
                    }

                    @Override 
           public RangedUri getIndexUri() {
               return this.indexUri;
                    }

                    @Override 
           public DashSegmentIndex getIndex() {
               return null;
                    }
                }

       public static class MultiSegmentRepresentation extends Representation implements DashSegmentIndex {
                    private final SegmentBase.MultiSegmentBase segmentBase;

           public MultiSegmentRepresentation(long periodStartMs, long periodDurationMs, String contentId, long revisionId, Format format, SegmentBase.MultiSegmentBase segmentBase) {
               super(periodStartMs, periodDurationMs, contentId, revisionId, format, segmentBase, null);
               this.segmentBase = segmentBase;
                    }

                    @Override 
           public RangedUri getIndexUri() {
               return null;
                    }

                    @Override 
           public DashSegmentIndex getIndex() {
               return this;
                    }

                    @Override 
           public RangedUri getSegmentUrl(int segmentIndex) {
               return this.segmentBase.getSegmentUrl(this, segmentIndex);
                    }

                    @Override 
           public int getSegmentNum(long timeUs) {
               return this.segmentBase.getSegmentNum(timeUs);
                    }

                    @Override 
           public long getTimeUs(int segmentIndex) {
               return this.segmentBase.getSegmentTimeUs(segmentIndex);
                    }

                    @Override 
           public long getDurationUs(int segmentIndex) {
               return this.segmentBase.getSegmentDurationUs(segmentIndex);
                    }

                    @Override 
           public int getFirstSegmentNum() {
               return this.segmentBase.getFirstSegmentNum();
                    }

                    @Override 
           public int getLastSegmentNum() {
               return this.segmentBase.getLastSegmentNum();
                    }
                }
            }
