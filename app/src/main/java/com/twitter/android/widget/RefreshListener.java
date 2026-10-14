            package com.twitter.android.widget;
            
            
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
