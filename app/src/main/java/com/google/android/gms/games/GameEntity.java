            package com.google.android.gms.games;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.internal.AbstractC1056en;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1049eg;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class GameEntity extends AbstractC1056en implements Game {
                public static final Parcelable.Creator<GameEntity> CREATOR = new C0908a();

                /* JADX INFO: renamed from: iM */
                private final int f1030iM;

                /* JADX INFO: renamed from: mk */
                private final String f1031mk;

                /* JADX INFO: renamed from: ml */
                private final String f1032ml;

                /* JADX INFO: renamed from: mm */
                private final String f1033mm;

                /* JADX INFO: renamed from: mn */
                private final String f1034mn;

                /* JADX INFO: renamed from: mo */
                private final String f1035mo;

                /* JADX INFO: renamed from: mp */
                private final String f1036mp;

                /* JADX INFO: renamed from: mq */
                private final Uri f1037mq;

                /* JADX INFO: renamed from: mr */
                private final Uri f1038mr;

                /* JADX INFO: renamed from: ms */
                private final Uri f1039ms;

                /* JADX INFO: renamed from: mt */
                private final boolean f1040mt;

                /* JADX INFO: renamed from: mu */
                private final boolean f1041mu;

                /* JADX INFO: renamed from: mv */
                private final String f1042mv;

                /* JADX INFO: renamed from: mw */
                private final int f1043mw;

                /* JADX INFO: renamed from: mx */
                private final int f1044mx;

                /* JADX INFO: renamed from: my */
                private final int f1045my;

                /* JADX INFO: renamed from: com.google.android.gms.games.GameEntity$a */
                static final class C0908a extends C0911a {
                    C0908a() {
                    }

                    @Override // com.google.android.gms.games.C0911a, android.os.Parcelable.Creator
                    public /* synthetic */ GameEntity createFromParcel(Parcel x0) {
                        return mo1175t(x0);
                    }

                    @Override // com.google.android.gms.games.C0911a
                    /* JADX INFO: renamed from: t */
                    public GameEntity mo1175t(Parcel parcel) {
                        if (GameEntity.m1173b(GameEntity.m1174bQ()) || GameEntity.m1169J(GameEntity.class.getCanonicalName())) {
                            return super.mo1175t(parcel);
                        }
                        String string = parcel.readString();
                        String string2 = parcel.readString();
                        String string3 = parcel.readString();
                        String string4 = parcel.readString();
                        String string5 = parcel.readString();
                        String string6 = parcel.readString();
                        String string7 = parcel.readString();
                        Uri uri = string7 == null ? null : Uri.parse(string7);
                        String string8 = parcel.readString();
                        Uri uri2 = string8 == null ? null : Uri.parse(string8);
                        String string9 = parcel.readString();
                        return new GameEntity(1, string, string2, string3, string4, string5, string6, uri, uri2, string9 == null ? null : Uri.parse(string9), parcel.readInt() > 0, parcel.readInt() > 0, parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    }
                }

                GameEntity(int versionCode, String applicationId, String displayName, String primaryCategory, String secondaryCategory, String description, String developerName, Uri iconImageUri, Uri hiResImageUri, Uri featuredImageUri, boolean playEnabledGame, boolean instanceInstalled, String instancePackageName, int gameplayAclStatus, int achievementTotalCount, int leaderboardCount) {
                    this.f1030iM = versionCode;
                    this.f1031mk = applicationId;
                    this.f1032ml = displayName;
                    this.f1033mm = primaryCategory;
                    this.f1034mn = secondaryCategory;
                    this.f1035mo = description;
                    this.f1036mp = developerName;
                    this.f1037mq = iconImageUri;
                    this.f1038mr = hiResImageUri;
                    this.f1039ms = featuredImageUri;
                    this.f1040mt = playEnabledGame;
                    this.f1041mu = instanceInstalled;
                    this.f1042mv = instancePackageName;
                    this.f1043mw = gameplayAclStatus;
                    this.f1044mx = achievementTotalCount;
                    this.f1045my = leaderboardCount;
                }

                public GameEntity(Game game) {
                    this.f1030iM = 1;
                    this.f1031mk = game.getApplicationId();
                    this.f1033mm = game.getPrimaryCategory();
                    this.f1034mn = game.getSecondaryCategory();
                    this.f1035mo = game.getDescription();
                    this.f1036mp = game.getDeveloperName();
                    this.f1032ml = game.getDisplayName();
                    this.f1037mq = game.getIconImageUri();
                    this.f1038mr = game.getHiResImageUri();
                    this.f1039ms = game.getFeaturedImageUri();
                    this.f1040mt = game.isPlayEnabledGame();
                    this.f1041mu = game.isInstanceInstalled();
                    this.f1042mv = game.getInstancePackageName();
                    this.f1043mw = game.getGameplayAclStatus();
                    this.f1044mx = game.getAchievementTotalCount();
                    this.f1045my = game.getLeaderboardCount();
                }

                /* JADX INFO: renamed from: J */
                static /* synthetic */ boolean m1169J(String str) {
                    return m1531y(str);
                }

                /* JADX INFO: renamed from: a */
                static int m1170a(Game game) {
                    return C1027dl.hashCode(game.getApplicationId(), game.getDisplayName(), game.getPrimaryCategory(), game.getSecondaryCategory(), game.getDescription(), game.getDeveloperName(), game.getIconImageUri(), game.getHiResImageUri(), game.getFeaturedImageUri(), Boolean.valueOf(game.isPlayEnabledGame()), Boolean.valueOf(game.isInstanceInstalled()), game.getInstancePackageName(), Integer.valueOf(game.getGameplayAclStatus()), Integer.valueOf(game.getAchievementTotalCount()), Integer.valueOf(game.getLeaderboardCount()));
                }

                /* JADX INFO: renamed from: a */
                static boolean m1171a(Game game, Object obj) {
                    if (!(obj instanceof Game)) {
                        return false;
                    }
                    if (game == obj) {
                        return true;
                    }
                    Game game2 = (Game) obj;
                    return C1027dl.equal(game2.getApplicationId(), game.getApplicationId()) && C1027dl.equal(game2.getDisplayName(), game.getDisplayName()) && C1027dl.equal(game2.getPrimaryCategory(), game.getPrimaryCategory()) && C1027dl.equal(game2.getSecondaryCategory(), game.getSecondaryCategory()) && C1027dl.equal(game2.getDescription(), game.getDescription()) && C1027dl.equal(game2.getDeveloperName(), game.getDeveloperName()) && C1027dl.equal(game2.getIconImageUri(), game.getIconImageUri()) && C1027dl.equal(game2.getHiResImageUri(), game.getHiResImageUri()) && C1027dl.equal(game2.getFeaturedImageUri(), game.getFeaturedImageUri()) && C1027dl.equal(Boolean.valueOf(game2.isPlayEnabledGame()), Boolean.valueOf(game.isPlayEnabledGame())) && C1027dl.equal(Boolean.valueOf(game2.isInstanceInstalled()), Boolean.valueOf(game.isInstanceInstalled())) && C1027dl.equal(game2.getInstancePackageName(), game.getInstancePackageName()) && C1027dl.equal(Integer.valueOf(game2.getGameplayAclStatus()), Integer.valueOf(game.getGameplayAclStatus())) && C1027dl.equal(Integer.valueOf(game2.getAchievementTotalCount()), Integer.valueOf(game.getAchievementTotalCount())) && C1027dl.equal(Integer.valueOf(game2.getLeaderboardCount()), Integer.valueOf(game.getLeaderboardCount()));
                }

                /* JADX INFO: renamed from: b */
                static String m1172b(Game game) {
                    return C1027dl.m1599d(game).m1600a("ApplicationId", game.getApplicationId()).m1600a("DisplayName", game.getDisplayName()).m1600a("PrimaryCategory", game.getPrimaryCategory()).m1600a("SecondaryCategory", game.getSecondaryCategory()).m1600a("Description", game.getDescription()).m1600a("DeveloperName", game.getDeveloperName()).m1600a("IconImageUri", game.getIconImageUri()).m1600a("HiResImageUri", game.getHiResImageUri()).m1600a("FeaturedImageUri", game.getFeaturedImageUri()).m1600a("PlayEnabledGame", Boolean.valueOf(game.isPlayEnabledGame())).m1600a("InstanceInstalled", Boolean.valueOf(game.isInstanceInstalled())).m1600a("InstancePackageName", game.getInstancePackageName()).m1600a("GameplayAclStatus", Integer.valueOf(game.getGameplayAclStatus())).m1600a("AchievementTotalCount", Integer.valueOf(game.getAchievementTotalCount())).m1600a("LeaderboardCount", Integer.valueOf(game.getLeaderboardCount())).toString();
                }

                /* JADX INFO: renamed from: b */
                static /* synthetic */ boolean m1173b(Integer num) {
                    return m1798c(num);
                }

                /* JADX INFO: renamed from: bQ */
                static /* synthetic */ Integer m1174bQ() {
                    return m1530aW();
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object obj) {
                    return m1171a(this, obj);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.Freezable
                public Game freeze() {
                    return this;
                }


                @Override // com.google.android.gms.games.Game
                public int getAchievementTotalCount() {
                    return this.f1044mx;
                }

                @Override // com.google.android.gms.games.Game
                public String getApplicationId() {
                    return this.f1031mk;
                }

                @Override // com.google.android.gms.games.Game
                public String getDescription() {
                    return this.f1035mo;
                }

                @Override // com.google.android.gms.games.Game
                public void getDescription(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1035mo, dataOut);
                }

                @Override // com.google.android.gms.games.Game
                public String getDeveloperName() {
                    return this.f1036mp;
                }

                @Override // com.google.android.gms.games.Game
                public void getDeveloperName(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1036mp, dataOut);
                }

                @Override // com.google.android.gms.games.Game
                public String getDisplayName() {
                    return this.f1032ml;
                }

                @Override // com.google.android.gms.games.Game
                public void getDisplayName(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1032ml, dataOut);
                }

                @Override // com.google.android.gms.games.Game
                public Uri getFeaturedImageUri() {
                    return this.f1039ms;
                }

                @Override // com.google.android.gms.games.Game
                public int getGameplayAclStatus() {
                    return this.f1043mw;
                }

                @Override // com.google.android.gms.games.Game
                public Uri getHiResImageUri() {
                    return this.f1038mr;
                }

                @Override // com.google.android.gms.games.Game
                public Uri getIconImageUri() {
                    return this.f1037mq;
                }

                @Override // com.google.android.gms.games.Game
                public String getInstancePackageName() {
                    return this.f1042mv;
                }

                @Override // com.google.android.gms.games.Game
                public int getLeaderboardCount() {
                    return this.f1045my;
                }

                @Override // com.google.android.gms.games.Game
                public String getPrimaryCategory() {
                    return this.f1033mm;
                }

                @Override // com.google.android.gms.games.Game
                public String getSecondaryCategory() {
                    return this.f1034mn;
                }

                public int getVersionCode() {
                    return this.f1030iM;
                }

                public int hashCode() {
                    return m1170a(this);
                }

                @Override // com.google.android.gms.common.data.Freezable
                public boolean isDataValid() {
                    return true;
                }

                @Override // com.google.android.gms.games.Game
                public boolean isInstanceInstalled() {
                    return this.f1041mu;
                }

                @Override // com.google.android.gms.games.Game
                public boolean isPlayEnabledGame() {
                    return this.f1040mt;
                }

                public String toString() {
                    return m1172b(this);
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    if (!m1532aX()) {
                        C0911a.m1183a(this, dest, flags);
                        return;
                    }
                    dest.writeString(this.f1031mk);
                    dest.writeString(this.f1032ml);
                    dest.writeString(this.f1033mm);
                    dest.writeString(this.f1034mn);
                    dest.writeString(this.f1035mo);
                    dest.writeString(this.f1036mp);
                    dest.writeString(this.f1037mq == null ? null : this.f1037mq.toString());
                    dest.writeString(this.f1038mr == null ? null : this.f1038mr.toString());
                    dest.writeString(this.f1039ms != null ? this.f1039ms.toString() : null);
                    dest.writeInt(this.f1040mt ? 1 : 0);
                    dest.writeInt(this.f1041mu ? 1 : 0);
                    dest.writeString(this.f1042mv);
                    dest.writeInt(this.f1043mw);
                    dest.writeInt(this.f1044mx);
                    dest.writeInt(this.f1045my);
                }
            }
