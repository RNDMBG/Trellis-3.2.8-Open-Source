            package com.google.android.gms.games.leaderboard;

            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1063eu;
            import com.google.android.gms.internal.C1064ev;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.leaderboard.e */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0920e extends AbstractC0889b implements LeaderboardVariant {
                C0920e(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                /* JADX INFO: renamed from: ce */
                public String m1195ce() {
                    return getString("top_page_token_next");
                }

                /* JADX INFO: renamed from: cf */
                public String m1196cf() {
                    return getString("window_page_token_prev");
                }

                /* JADX INFO: renamed from: cg */
                public String m1197cg() {
                    return getString("window_page_token_next");
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardVariant
                public int getCollection() {
                    return getInteger("collection");
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardVariant
                public String getDisplayPlayerRank() {
                    return getString("player_display_rank");
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardVariant
                public String getDisplayPlayerScore() {
                    return getString("player_display_score");
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardVariant
                public long getNumScores() {
                    if (m1031v("total_scores")) {
                        return -1L;
                    }
                    return getLong("total_scores");
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardVariant
                public long getPlayerRank() {
                    if (m1031v("player_rank")) {
                        return -1L;
                    }
                    return getLong("player_rank");
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardVariant
                public String getPlayerScoreTag() {
                    return getString("player_score_tag");
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardVariant
                public long getRawPlayerScore() {
                    if (m1031v("player_raw_score")) {
                        return -1L;
                    }
                    return getLong("player_raw_score");
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardVariant
                public int getTimeSpan() {
                    return getInteger("timespan");
                }

                @Override // com.google.android.gms.games.leaderboard.LeaderboardVariant
                public boolean hasPlayerInfo() {
                    return !m1031v("player_raw_score");
                }

                public String toString() {
                    return C1027dl.m1599d(this).m1600a("TimeSpan", C1064ev.m1895R(getTimeSpan())).m1600a("Collection", C1063eu.m1894R(getCollection())).m1600a("RawPlayerScore", hasPlayerInfo() ? Long.valueOf(getRawPlayerScore()) : "none").m1600a("DisplayPlayerScore", hasPlayerInfo() ? getDisplayPlayerScore() : "none").m1600a("PlayerRank", hasPlayerInfo() ? Long.valueOf(getPlayerRank()) : "none").m1600a("DisplayPlayerRank", hasPlayerInfo() ? getDisplayPlayerRank() : "none").m1600a("NumScores", Long.valueOf(getNumScores())).m1600a("TopPageNextToken", m1195ce()).m1600a("WindowPageNextToken", m1197cg()).m1600a("WindowPagePrevToken", m1196cf()).toString();
                }
            }
