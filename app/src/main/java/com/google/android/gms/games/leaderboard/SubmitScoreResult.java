            package com.google.android.gms.games.leaderboard;

            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.internal.C1064ev;
            import java.util.HashMap;
            
            
            public final class SubmitScoreResult {

                
                private static final String[] f1061nI = {"leaderboardId", "playerId", "timeSpan", "hasResult", "rawScore", "formattedScore", "newBest", "scoreTag"};

                
                private int f1062iC;

                
                private String f1063mD;

                
                private String f1064nJ;

                
                private HashMap<Integer, Result> f1065nK;

                public static final class Result {
                    public final String formattedScore;
                    public final boolean newBest;
                    public final long rawScore;
                    public final String scoreTag;

                    public Result(long rawScore, String formattedScore, String scoreTag, boolean newBest) {
                        this.rawScore = rawScore;
                        this.formattedScore = formattedScore;
                        this.scoreTag = scoreTag;
                        this.newBest = newBest;
                    }

                    public String toString() {
                        return C1027dl.m1599d(this).m1600a("RawScore", Long.valueOf(this.rawScore)).m1600a("FormattedScore", this.formattedScore).m1600a("ScoreTag", this.scoreTag).m1600a("NewBest", Boolean.valueOf(this.newBest)).toString();
                    }
                }

                public SubmitScoreResult(int statusCode, String leaderboardId, String playerId) {
                    this(statusCode, leaderboardId, playerId, new HashMap());
                }

                public SubmitScoreResult(int statusCode, String leaderboardId, String playerId, HashMap<Integer, Result> results) {
                    this.f1062iC = statusCode;
                    this.f1064nJ = leaderboardId;
                    this.f1063mD = playerId;
                    this.f1065nK = results;
                }

                public SubmitScoreResult(C0891d dataHolder) {
                    this.f1062iC = dataHolder.getStatusCode();
                    this.f1065nK = new HashMap<>();
                    int count = dataHolder.getCount();
                    C1028dm.m1607m(count == 3);
                    for (int i = 0; i < count; i++) {
                        int iM1050q = dataHolder.m1050q(i);
                        if (i == 0) {
                            this.f1064nJ = dataHolder.m1045c("leaderboardId", i, iM1050q);
                            this.f1063mD = dataHolder.m1045c("playerId", i, iM1050q);
                        }
                        if (dataHolder.m1046d("hasResult", i, iM1050q)) {
                            m1188a(new Result(dataHolder.m1037a("rawScore", i, iM1050q), dataHolder.m1045c("formattedScore", i, iM1050q), dataHolder.m1045c("scoreTag", i, iM1050q), dataHolder.m1046d("newBest", i, iM1050q)), dataHolder.m1043b("timeSpan", i, iM1050q));
                        }
                    }
                }

                
                private void m1188a(Result result, int i) {
                    this.f1065nK.put(Integer.valueOf(i), result);
                }

                public String getLeaderboardId() {
                    return this.f1064nJ;
                }

                public String getPlayerId() {
                    return this.f1063mD;
                }

                public Result getScoreResult(int timeSpan) {
                    return this.f1065nK.get(Integer.valueOf(timeSpan));
                }

                public int getStatusCode() {
                    return this.f1062iC;
                }

                public String toString() {
                    C1027dl.a aVarM1600a = C1027dl.m1599d(this).m1600a("PlayerId", this.f1063mD).m1600a("StatusCode", Integer.valueOf(this.f1062iC));
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 >= 3) {
                            return aVarM1600a.toString();
                        }
                        Result result = this.f1065nK.get(Integer.valueOf(i2));
                        aVarM1600a.m1600a("TimesSpan", C1064ev.m1895R(i2));
                        aVarM1600a.m1600a("Result", result == null ? "null" : result.toString());
                        i = i2 + 1;
                    }
                }
            }
