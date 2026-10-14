            package com.google.android.exoplayer.upstream;
            
            
            public interface BandwidthMeter {
                public static final long NO_ESTIMATE = -1;

                long getBitrateEstimate();
            }
