            package com.google.android.gms.games;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.internal.AbstractC1056en;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1049eg;
            
            
            public final class GameEntity extends AbstractC1056en implements Game {
                public static final Parcelable.Creator<GameEntity> CREATOR = new C0908a();

                
                private final int f1030iM;

                
                private final String f1031mk;

                
                private final String f1032ml;

                
                private final String f1033mm;

                
                private final String f1034mn;

                
                private final String f1035mo;

                
                private final String f1036mp;

                
                private final Uri f1037mq;

                
                private final Uri f1038mr;

                
                private final Uri f1039ms;

                
                private final boolean f1040mt;

                
                private final boolean f1041mu;

                
                private final String f1042mv;

                
                private final int f1043mw;

                
                private final int f1044mx;

                
                private final int f1045my;

                
                static final class C0908a extends C0911a {
                    C0908a() {
                    }

                    @Override 
                    public  GameEntity createFromParcel(Parcel x0) {
                        return mo1175t(x0);
                    }

                    @Override 
                    
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

                
                static  boolean m1169J(String str) {
                    return m1531y(str);
                }

                
                static int m1170a(Game game) {
                    return C1027dl.hashCode(game.getApplicationId(), game.getDisplayName(), game.getPrimaryCategory(), game.getSecondaryCategory(), game.getDescription(), game.getDeveloperName(), game.getIconImageUri(), game.getHiResImageUri(), game.getFeaturedImageUri(), Boolean.valueOf(game.isPlayEnabledGame()), Boolean.valueOf(game.isInstanceInstalled()), game.getInstancePackageName(), Integer.valueOf(game.getGameplayAclStatus()), Integer.valueOf(game.getAchievementTotalCount()), Integer.valueOf(game.getLeaderboardCount()));
                }

                
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

                
                static String m1172b(Game game) {
                    return C1027dl.m1599d(game).m1600a("ApplicationId", game.getApplicationId()).m1600a("DisplayName", game.getDisplayName()).m1600a("PrimaryCategory", game.getPrimaryCategory()).m1600a("SecondaryCategory", game.getSecondaryCategory()).m1600a("Description", game.getDescription()).m1600a("DeveloperName", game.getDeveloperName()).m1600a("IconImageUri", game.getIconImageUri()).m1600a("HiResImageUri", game.getHiResImageUri()).m1600a("FeaturedImageUri", game.getFeaturedImageUri()).m1600a("PlayEnabledGame", Boolean.valueOf(game.isPlayEnabledGame())).m1600a("InstanceInstalled", Boolean.valueOf(game.isInstanceInstalled())).m1600a("InstancePackageName", game.getInstancePackageName()).m1600a("GameplayAclStatus", Integer.valueOf(game.getGameplayAclStatus())).m1600a("AchievementTotalCount", Integer.valueOf(game.getAchievementTotalCount())).m1600a("LeaderboardCount", Integer.valueOf(game.getLeaderboardCount())).toString();
                }

                
                static  boolean m1173b(Integer num) {
                    return m1798c(num);
                }

                
                static  Integer m1174bQ() {
                    return m1530aW();
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object obj) {
                    return m1171a(this, obj);
                }

                
                @Override 
                public Game freeze() {
                    return this;
                }


                @Override 
                public int getAchievementTotalCount() {
                    return this.f1044mx;
                }

                @Override 
                public String getApplicationId() {
                    return this.f1031mk;
                }

                @Override 
                public String getDescription() {
                    return this.f1035mo;
                }

                @Override 
                public void getDescription(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1035mo, dataOut);
                }

                @Override 
                public String getDeveloperName() {
                    return this.f1036mp;
                }

                @Override 
                public void getDeveloperName(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1036mp, dataOut);
                }

                @Override 
                public String getDisplayName() {
                    return this.f1032ml;
                }

                @Override 
                public void getDisplayName(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1032ml, dataOut);
                }

                @Override 
                public Uri getFeaturedImageUri() {
                    return this.f1039ms;
                }

                @Override 
                public int getGameplayAclStatus() {
                    return this.f1043mw;
                }

                @Override 
                public Uri getHiResImageUri() {
                    return this.f1038mr;
                }

                @Override 
                public Uri getIconImageUri() {
                    return this.f1037mq;
                }

                @Override 
                public String getInstancePackageName() {
                    return this.f1042mv;
                }

                @Override 
                public int getLeaderboardCount() {
                    return this.f1045my;
                }

                @Override 
                public String getPrimaryCategory() {
                    return this.f1033mm;
                }

                @Override 
                public String getSecondaryCategory() {
                    return this.f1034mn;
                }

                public int getVersionCode() {
                    return this.f1030iM;
                }

                public int hashCode() {
                    return m1170a(this);
                }

                @Override 
                public boolean isDataValid() {
                    return true;
                }

                @Override 
                public boolean isInstanceInstalled() {
                    return this.f1041mu;
                }

                @Override 
                public boolean isPlayEnabledGame() {
                    return this.f1040mt;
                }

                public String toString() {
                    return m1172b(this);
                }

                @Override 
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
