            package com.google.android.gms.games.leaderboard;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import com.google.android.gms.common.data.Freezable;
            import com.google.android.gms.games.Player;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface LeaderboardScore extends Freezable<LeaderboardScore> {
                String getDisplayRank();

                void getDisplayRank(CharArrayBuffer charArrayBuffer);

                String getDisplayScore();

                void getDisplayScore(CharArrayBuffer charArrayBuffer);

                long getRank();

                long getRawScore();

                Player getScoreHolder();

                String getScoreHolderDisplayName();

                void getScoreHolderDisplayName(CharArrayBuffer charArrayBuffer);

                Uri getScoreHolderHiResImageUri();

                Uri getScoreHolderIconImageUri();

                String getScoreTag();

                long getTimestampMillis();
            }
