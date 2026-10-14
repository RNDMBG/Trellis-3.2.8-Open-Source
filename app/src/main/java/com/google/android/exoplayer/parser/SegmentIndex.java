            package com.google.android.exoplayer.parser;
            
            
    public final class SegmentIndex {
                public final long[] durationsUs;
                public final int length;
                public final long[] offsets;
                public final int sizeBytes;
                public final int[] sizes;
                public final long[] timesUs;

        public SegmentIndex(int sizeBytes, int[] sizes, long[] offsets, long[] durationsUs, long[] timesUs) {
            this.sizeBytes = sizeBytes;
            this.length = sizes.length;
            this.sizes = sizes;
            this.offsets = offsets;
            this.durationsUs = durationsUs;
            this.timesUs = timesUs;
                }
            }
