            package com.twitter.android.widget;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface RefreshListener {
                ItemPosition getFirstItemPosition();

                int getPositionForItemId(long j);

                void onRefreshCancelled();

                void onRefreshFinished();

                void onRefreshFinishedNewData();

                void onRefreshFinishedNoChange();

                void onRefreshPulled();

                void onRefreshReleased(boolean z);
            }
