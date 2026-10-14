            package com.google.android.gms.games.leaderboard;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Leaderboard {
                public static final int SCORE_ORDER_LARGER_IS_BETTER = 1;
                public static final int SCORE_ORDER_SMALLER_IS_BETTER = 0;

                String getDisplayName();

                void getDisplayName(CharArrayBuffer charArrayBuffer);

                Uri getIconImageUri();

                String getLeaderboardId();

                int getScoreOrder();

                ArrayList<LeaderboardVariant> getVariants();
            }
