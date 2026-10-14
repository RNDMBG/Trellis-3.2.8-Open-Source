            package com.google.android.exoplayer.parser;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 59 */    public final class SegmentIndex {
                public final long[] durationsUs;
                public final int length;
                public final long[] offsets;
                public final int sizeBytes;
                public final int[] sizes;
                public final long[] timesUs;

/* 60 */        public SegmentIndex(int sizeBytes, int[] sizes, long[] offsets, long[] durationsUs, long[] timesUs) {
/* 62 */            this.sizeBytes = sizeBytes;
/* 63 */            this.length = sizes.length;
/* 64 */            this.sizes = sizes;
/* 65 */            this.offsets = offsets;
/* 66 */            this.durationsUs = durationsUs;
/* 67 */            this.timesUs = timesUs;
                }
            }
