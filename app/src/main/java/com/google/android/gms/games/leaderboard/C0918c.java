            package com.google.android.gms.games.leaderboard;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import com.google.android.gms.games.Player;
            import com.google.android.gms.games.PlayerEntity;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.internal.C1049eg;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.leaderboard.c */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0918c implements LeaderboardScore {

                /* JADX INFO: renamed from: nA */
                private final long f1068nA;

                /* JADX INFO: renamed from: nB */
                private final long f1069nB;

                /* JADX INFO: renamed from: nC */
                private final String f1070nC;

                /* JADX INFO: renamed from: nD */
                private final Uri f1071nD;

                /* JADX INFO: renamed from: nE */
                private final Uri f1072nE;

                /* JADX INFO: renamed from: nF */
                private final PlayerEntity f1073nF;

                /* JADX INFO: renamed from: nG */
                private final String f1074nG;

                /* JADX INFO: renamed from: nx */
                private final long f1075nx;

                /* JADX INFO: renamed from: ny */
                private final String f1076ny;

                /* JADX INFO: renamed from: nz */
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

                /* JADX INFO: renamed from: a */
                static int m1190a(LeaderboardScore leaderboardScore) {
                    return C1027dl.hashCode(Long.valueOf(leaderboardScore.getRank()), leaderboardScore.getDisplayRank(), Long.valueOf(leaderboardScore.getRawScore()), leaderboardScore.getDisplayScore(), Long.valueOf(leaderboardScore.getTimestampMillis()), leaderboardScore.getScoreHolderDisplayName(), leaderboardScore.getScoreHolderIconImageUri(), leaderboardScore.getScoreHolderHiResImageUri(), leaderboardScore.getScoreHolder());
                }

                /* JADX INFO: renamed from: a */
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

                /* JADX INFO: renamed from: b */
                static String m1192b(LeaderboardScore leaderboardScore) {
                    return C1027dl.m1599d(leaderboardScore).m1600a("Rank", Long.valueOf(leaderboardScore.getRank())).m1600a("DisplayRank", leaderboardScore.getDisplayRank()).m1600a("Score", Long.valueOf(leaderboardScore.getRawScore())).m1600a("DisplayScore", leaderboardScore.getDisplayScore()).m1600a("Timestamp", Long.valueOf(leaderboardScore.getTimestampMillis())).m1600a("DisplayName", leaderboardScore.getScoreHolderDisplayName()).m1600a("IconImageUri", leaderboardScore.getScoreHolderIconImageUri()).m1600a("HiResImageUri", leaderboardScore.getScoreHolderHiResImageUri()).m1600a("Player", leaderboardScore.getScoreHolder() == null ? null : leaderboardScore.getScoreHolder()).m1600a("ScoreTag", leaderboardScore.getScoreTag()).toString();
                }

                /* JADX INFO: renamed from: cd */
                public LeaderboardScore m1193cd() {
                    return this;
                }

                public boolean equals(Object obj) {
                    return m1191a(this, obj);
                }

                @Override // com.google.android.gms.common.data.Freezable
                public /* synthetic */ LeaderboardScore freeze() {
                    return m1193cd();
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public String getDisplayRank() {
                    return this.f1076ny;
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public void getDisplayRank(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1076ny, dataOut);
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public String getDisplayScore() {
                    return this.f1077nz;
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public void getDisplayScore(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1077nz, dataOut);
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public long getRank() {
                    return this.f1075nx;
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public long getRawScore() {
                    return this.f1068nA;
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public Player getScoreHolder() {
                    return this.f1073nF;
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public String getScoreHolderDisplayName() {
                    return this.f1073nF == null ? this.f1070nC : this.f1073nF.getDisplayName();
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public void getScoreHolderDisplayName(CharArrayBuffer dataOut) {
                    if (this.f1073nF == null) {
                        C1049eg.m1731b(this.f1070nC, dataOut);
                    } else {
                        this.f1073nF.getDisplayName(dataOut);
                    }
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public Uri getScoreHolderHiResImageUri() {
                    return this.f1073nF == null ? this.f1072nE : this.f1073nF.getHiResImageUri();
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public Uri getScoreHolderIconImageUri() {
                    return this.f1073nF == null ? this.f1071nD : this.f1073nF.getIconImageUri();
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public String getScoreTag() {
                    return this.f1074nG;
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardScore
                public long getTimestampMillis() {
                    return this.f1069nB;
                }

                public int hashCode() {
                    return m1190a(this);
                }

                @Override // com.google.android.gms.common.data.Freezable
                public boolean isDataValid() {
                    return true;
                }

                public String toString() {
                    return m1192b(this);
                }
            }
