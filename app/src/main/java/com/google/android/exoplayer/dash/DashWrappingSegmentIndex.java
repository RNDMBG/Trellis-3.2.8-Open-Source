            package com.google.android.exoplayer.dash;

            import android.net.Uri;
            import com.google.android.exoplayer.dash.mpd.RangedUri;
            import com.google.android.exoplayer.parser.SegmentIndex;
            import com.google.android.exoplayer.util.Util;
            
            
    public class DashWrappingSegmentIndex implements DashSegmentIndex {
                private final long indexAnchor;
                private final SegmentIndex segmentIndex;
                private final Uri uri;

        public DashWrappingSegmentIndex(SegmentIndex segmentIndex, Uri uri, long indexAnchor) {
            this.segmentIndex = segmentIndex;
            this.uri = uri;
            this.indexAnchor = indexAnchor;
                }

                @Override 
        public int getFirstSegmentNum() {
            return 0;
                }

                @Override 
        public int getLastSegmentNum() {
            return this.segmentIndex.length - 1;
                }

                @Override 
        public long getTimeUs(int segmentNum) {
            return this.segmentIndex.timesUs[segmentNum];
                }

                @Override 
        public long getDurationUs(int segmentNum) {
            return this.segmentIndex.durationsUs[segmentNum];
                }

                @Override 
        public RangedUri getSegmentUrl(int segmentNum) {
            return new RangedUri(this.uri, null, this.indexAnchor + this.segmentIndex.offsets[segmentNum], this.segmentIndex.sizes[segmentNum]);
                }

                @Override 
        public int getSegmentNum(long timeUs) {
            return Util.binarySearchFloor(this.segmentIndex.timesUs, timeUs, true, true);
                }
            }
