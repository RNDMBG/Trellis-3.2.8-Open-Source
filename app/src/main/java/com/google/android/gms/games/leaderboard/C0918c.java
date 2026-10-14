            package com.google.android.gms.games.leaderboard;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import com.google.android.gms.games.Player;
            import com.google.android.gms.games.PlayerEntity;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.internal.C1049eg;
            
            
            
            public final class C0918c implements LeaderboardScore {

                
                private final long f1068nA;

                
                private final long f1069nB;

                
                private final String f1070nC;

                
                private final Uri f1071nD;

                
                private final Uri f1072nE;

                
                private final PlayerEntity f1073nF;

                
                private final String f1074nG;

                
                private final long f1075nx;

                
                private final String f1076ny;

                
                private final String f1077nz;

                public C0918c(LeaderboardScore leaderboardScore) {
                    this.f1075nx = leaderboardScore.getRank();
                    this.f1076ny = (String) C1028dm.m1605e(leaderboardScore.getDisplayRank());
                    this.f1077nz = (String) C1028dm.m1605e(leaderboardScore.getDisplayScore());
                    this.f1068nA = leaderboardScore.getRawScore();
                    this.f1069nB = leaderboardScore.getTimestampMillis();
                    this.f1070nC = leaderboardScore.getScoreHolderDisplayName();
                    this.f1071nD = leaderboardScore.getScoreHolderIconImageUri();
                    this.f1072nE = leaderboardScore.getScoreHolderHiResImageUri();
                    Player scoreHolder = leaderboardScore.getScoreHolder();
                    this.f1073nF = scoreHolder == null ? null : (PlayerEntity) scoreHolder.freeze();
                    this.f1074nG = leaderboardScore.getScoreTag();
                }

                
                static int m1190a(LeaderboardScore leaderboardScore) {
                    return C1027dl.hashCode(Long.valueOf(leaderboardScore.getRank()), leaderboardScore.getDisplayRank(), Long.valueOf(leaderboardScore.getRawScore()), leaderboardScore.getDisplayScore(), Long.valueOf(leaderboardScore.getTimestampMillis()), leaderboardScore.getScoreHolderDisplayName(), leaderboardScore.getScoreHolderIconImageUri(), leaderboardScore.getScoreHolderHiResImageUri(), leaderboardScore.getScoreHolder());
                }

                
                static boolean m1191a(LeaderboardScore leaderboardScore, Object obj) {
                    if (!(obj instanceof LeaderboardScore)) {
                        return false;
                    }
                    if (leaderboardScore == obj) {
                        return true;
                    }
                    LeaderboardScore leaderboardScore2 = (LeaderboardScore) obj;
                    return C1027dl.equal(Long.valueOf(leaderboardScore2.getRank()), Long.valueOf(leaderboardScore.getRank())) && C1027dl.equal(leaderboardScore2.getDisplayRank(), leaderboardScore.getDisplayRank()) && C1027dl.equal(Long.valueOf(leaderboardScore2.getRawScore()), Long.valueOf(leaderboardScore.getRawScore())) && C1027dl.equal(leaderboardScore2.getDisplayScore(), leaderboardScore.getDisplayScore()) && C1027dl.equal(Long.valueOf(leaderboardScore2.getTimestampMillis()), Long.valueOf(leaderboardScore.getTimestampMillis())) && C1027dl.equal(leaderboardScore2.getScoreHolderDisplayName(), leaderboardScore.getScoreHolderDisplayName()) && C1027dl.equal(leaderboardScore2.getScoreHolderIconImageUri(), leaderboardScore.getScoreHolderIconImageUri()) && C1027dl.equal(leaderboardScore2.getScoreHolderHiResImageUri(), leaderboardScore.getScoreHolderHiResImageUri()) && C1027dl.equal(leaderboardScore2.getScoreHolder(), leaderboardScore.getScoreHolder()) && C1027dl.equal(leaderboardScore2.getScoreTag(), leaderboardScore.getScoreTag());
                }

                
                static String m1192b(LeaderboardScore leaderboardScore) {
                    return C1027dl.m1599d(leaderboardScore).m1600a("Rank", Long.valueOf(leaderboardScore.getRank())).m1600a("DisplayRank", leaderboardScore.getDisplayRank()).m1600a("Score", Long.valueOf(leaderboardScore.getRawScore())).m1600a("DisplayScore", leaderboardScore.getDisplayScore()).m1600a("Timestamp", Long.valueOf(leaderboardScore.getTimestampMillis())).m1600a("DisplayName", leaderboardScore.getScoreHolderDisplayName()).m1600a("IconImageUri", leaderboardScore.getScoreHolderIconImageUri()).m1600a("HiResImageUri", leaderboardScore.getScoreHolderHiResImageUri()).m1600a("Player", leaderboardScore.getScoreHolder() == null ? null : leaderboardScore.getScoreHolder()).m1600a("ScoreTag", leaderboardScore.getScoreTag()).toString();
                }

                
                public LeaderboardScore m1193cd() {
                    return this;
                }

                public boolean equals(Object obj) {
                    return m1191a(this, obj);
                }

                @Override 
                public  LeaderboardScore freeze() {
                    return m1193cd();
                }

                @Override 
                public String getDisplayRank() {
                    return this.f1076ny;
                }

                @Override 
                public void getDisplayRank(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1076ny, dataOut);
                }

                @Override 
                public String getDisplayScore() {
                    return this.f1077nz;
                }

                @Override 
                public void getDisplayScore(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1077nz, dataOut);
                }

                @Override 
                public long getRank() {
                    return this.f1075nx;
                }

                @Override 
                public long getRawScore() {
                    return this.f1068nA;
                }

                @Override 
                public Player getScoreHolder() {
                    return this.f1073nF;
                }

                @Override 
                public String getScoreHolderDisplayName() {
                    return this.f1073nF == null ? this.f1070nC : this.f1073nF.getDisplayName();
                }

                @Override 
                public void getScoreHolderDisplayName(CharArrayBuffer dataOut) {
                    if (this.f1073nF == null) {
                        C1049eg.m1731b(this.f1070nC, dataOut);
                    } else {
                        this.f1073nF.getDisplayName(dataOut);
                    }
                }

                @Override 
                public Uri getScoreHolderHiResImageUri() {
                    return this.f1073nF == null ? this.f1072nE : this.f1073nF.getHiResImageUri();
                }

                @Override 
                public Uri getScoreHolderIconImageUri() {
                    return this.f1073nF == null ? this.f1071nD : this.f1073nF.getIconImageUri();
                }

                @Override 
                public String getScoreTag() {
                    return this.f1074nG;
                }

                @Override 
                public long getTimestampMillis() {
                    return this.f1069nB;
                }

                public int hashCode() {
                    return m1190a(this);
                }

                @Override 
                public boolean isDataValid() {
                    return true;
                }

                public String toString() {
                    return m1192b(this);
                }
            }
