            package com.google.android.exoplayer.text;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Subtitle {
                long getEventTime(int i);

                int getEventTimeCount();

                long getLastEventTime();

                int getNextEventTimeIndex(long j);

                long getStartTime();

                String getText(long j);
            }
