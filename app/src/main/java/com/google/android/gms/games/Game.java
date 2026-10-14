            package com.google.android.gms.games;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcelable;
            import com.google.android.gms.common.data.Freezable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Game extends Parcelable, Freezable<Game> {
                int getAchievementTotalCount();

                String getApplicationId();

                String getDescription();

                void getDescription(CharArrayBuffer charArrayBuffer);

                String getDeveloperName();

                void getDeveloperName(CharArrayBuffer charArrayBuffer);

                String getDisplayName();

                void getDisplayName(CharArrayBuffer charArrayBuffer);

                Uri getFeaturedImageUri();

                int getGameplayAclStatus();

                Uri getHiResImageUri();

                Uri getIconImageUri();

                String getInstancePackageName();

                int getLeaderboardCount();

                String getPrimaryCategory();

                String getSecondaryCategory();

                boolean isInstanceInstalled();

                boolean isPlayEnabledGame();
            }
