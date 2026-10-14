            package com.google.android.exoplayer.dash;

            import com.google.android.exoplayer.dash.mpd.RangedUri;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface DashSegmentIndex {
                long getDurationUs(int i);

                int getFirstSegmentNum();

                int getLastSegmentNum();

                int getSegmentNum(long j);

                RangedUri getSegmentUrl(int i);

                long getTimeUs(int i);
            }
