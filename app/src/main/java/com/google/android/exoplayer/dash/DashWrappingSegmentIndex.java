            package com.google.android.exoplayer.dash;

            import android.net.Uri;
            import com.google.android.exoplayer.dash.mpd.RangedUri;
            import com.google.android.exoplayer.parser.SegmentIndex;
            import com.google.android.exoplayer.util.Util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class DashWrappingSegmentIndex implements DashSegmentIndex {
                private final long indexAnchor;
                private final SegmentIndex segmentIndex;
                private final Uri uri;

/* 39 */        public DashWrappingSegmentIndex(SegmentIndex segmentIndex, Uri uri, long indexAnchor) {
/* 41 */            this.segmentIndex = segmentIndex;
/* 42 */            this.uri = uri;
/* 43 */            this.indexAnchor = indexAnchor;
                }

                @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 47 */        public int getFirstSegmentNum() {
/* 48 */            return 0;
                }

                @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 52 */        public int getLastSegmentNum() {
/* 53 */            return this.segmentIndex.length - 1;
                }

                @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 57 */        public long getTimeUs(int segmentNum) {
/* 58 */            return this.segmentIndex.timesUs[segmentNum];
                }

                @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 62 */        public long getDurationUs(int segmentNum) {
/* 63 */            return this.segmentIndex.durationsUs[segmentNum];
                }

                @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 67 */        public RangedUri getSegmentUrl(int segmentNum) {
/* 68 */            return new RangedUri(this.uri, null, this.indexAnchor + this.segmentIndex.offsets[segmentNum], this.segmentIndex.sizes[segmentNum]);
                }

                @Override // com.google.android.exoplayer.dash.DashSegmentIndex
/* 73 */        public int getSegmentNum(long timeUs) {
/* 74 */            return Util.binarySearchFloor(this.segmentIndex.timesUs, timeUs, true, true);
                }
            }
