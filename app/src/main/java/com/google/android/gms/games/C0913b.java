            package com.google.android.gms.games;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.b */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0913b extends AbstractC0889b implements Game {
                public C0913b(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public boolean equals(Object obj) {
                    return GameEntity.m1171a(this, obj);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.Freezable
                public Game freeze() {
                    return new GameEntity(this);
                }


                @Override // com.google.android.gms.games.Game
                public int getAchievementTotalCount() {
                    return getInteger("achievement_total_count");
                }

                @Override // com.google.android.gms.games.Game
                public String getApplicationId() {
                    return getString("external_game_id");
                }

                @Override // com.google.android.gms.games.Game
                public String getDescription() {
                    return getString("game_description");
                }

                @Override // com.google.android.gms.games.Game
                public void getDescription(CharArrayBuffer dataOut) {
                    m1029a("game_description", dataOut);
                }

                @Override // com.google.android.gms.games.Game
                public String getDeveloperName() {
                    return getString("developer_name");
                }

                @Override // com.google.android.gms.games.Game
                public void getDeveloperName(CharArrayBuffer dataOut) {
                    m1029a("developer_name", dataOut);
                }

                @Override // com.google.android.gms.games.Game
                public String getDisplayName() {
                    return getString("display_name");
                }

                @Override // com.google.android.gms.games.Game
                public void getDisplayName(CharArrayBuffer dataOut) {
                    m1029a("display_name", dataOut);
                }

                @Override // com.google.android.gms.games.Game
                public Uri getFeaturedImageUri() {
                    return m1030u("featured_image_uri");
                }

                @Override // com.google.android.gms.games.Game
                public int getGameplayAclStatus() {
                    return getInteger("gameplay_acl_status");
                }

                @Override // com.google.android.gms.games.Game
                public Uri getHiResImageUri() {
                    return m1030u("game_hi_res_image_uri");
                }

                @Override // com.google.android.gms.games.Game
                public Uri getIconImageUri() {
                    return m1030u("game_icon_image_uri");
                }

                @Override // com.google.android.gms.games.Game
                public String getInstancePackageName() {
                    return getString("package_name");
                }

                @Override // com.google.android.gms.games.Game
                public int getLeaderboardCount() {
                    return getInteger("leaderboard_count");
                }

                @Override // com.google.android.gms.games.Game
                public String getPrimaryCategory() {
                    return getString("primary_category");
                }

                @Override // com.google.android.gms.games.Game
                public String getSecondaryCategory() {
                    return getString("secondary_category");
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public int hashCode() {
                    return GameEntity.m1170a(this);
                }

                @Override // com.google.android.gms.games.Game
                public boolean isInstanceInstalled() {
                    return getInteger("installed") > 0;
                }

                @Override // com.google.android.gms.games.Game
                public boolean isPlayEnabledGame() {
                    return getBoolean("play_enabled_game");
                }

                public String toString() {
                    return GameEntity.m1172b(this);
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    ((GameEntity) freeze()).writeToParcel(dest, flags);
                }
            }
