            package com.google.android.gms.games;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            
            
            
            public final class C0913b extends AbstractC0889b implements Game {
                public C0913b(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                @Override 
                public boolean equals(Object obj) {
                    return GameEntity.m1171a(this, obj);
                }

                
                @Override 
                public Game freeze() {
                    return new GameEntity(this);
                }


                @Override 
                public int getAchievementTotalCount() {
                    return getInteger("achievement_total_count");
                }

                @Override 
                public String getApplicationId() {
                    return getString("external_game_id");
                }

                @Override 
                public String getDescription() {
                    return getString("game_description");
                }

                @Override 
                public void getDescription(CharArrayBuffer dataOut) {
                    m1029a("game_description", dataOut);
                }

                @Override 
                public String getDeveloperName() {
                    return getString("developer_name");
                }

                @Override 
                public void getDeveloperName(CharArrayBuffer dataOut) {
                    m1029a("developer_name", dataOut);
                }

                @Override 
                public String getDisplayName() {
                    return getString("display_name");
                }

                @Override 
                public void getDisplayName(CharArrayBuffer dataOut) {
                    m1029a("display_name", dataOut);
                }

                @Override 
                public Uri getFeaturedImageUri() {
                    return m1030u("featured_image_uri");
                }

                @Override 
                public int getGameplayAclStatus() {
                    return getInteger("gameplay_acl_status");
                }

                @Override 
                public Uri getHiResImageUri() {
                    return m1030u("game_hi_res_image_uri");
                }

                @Override 
                public Uri getIconImageUri() {
                    return m1030u("game_icon_image_uri");
                }

                @Override 
                public String getInstancePackageName() {
                    return getString("package_name");
                }

                @Override 
                public int getLeaderboardCount() {
                    return getInteger("leaderboard_count");
                }

                @Override 
                public String getPrimaryCategory() {
                    return getString("primary_category");
                }

                @Override 
                public String getSecondaryCategory() {
                    return getString("secondary_category");
                }

                @Override 
                public int hashCode() {
                    return GameEntity.m1170a(this);
                }

                @Override 
                public boolean isInstanceInstalled() {
                    return getInteger("installed") > 0;
                }

                @Override 
                public boolean isPlayEnabledGame() {
                    return getBoolean("play_enabled_game");
                }

                public String toString() {
                    return GameEntity.m1172b(this);
                }

                @Override 
                public void writeToParcel(Parcel dest, int flags) {
                    ((GameEntity) freeze()).writeToParcel(dest, flags);
                }
            }
