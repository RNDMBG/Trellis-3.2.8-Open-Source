            package com.google.android.gms.games.leaderboard;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.games.C0915d;
            import com.google.android.gms.games.Player;
            
            
            
            public final class C0919d extends AbstractC0889b implements LeaderboardScore {

                
                private final C0915d f1078nH;

                C0919d(C0891d c0891d, int i) {
                    super(c0891d, i);
                    this.f1078nH = new C0915d(c0891d, i);
                }

                
                public LeaderboardScore m1194cd() {
                    return new C0918c(this);
                }

                @Override 
                public boolean equals(Object obj) {
                    return C0918c.m1191a(this, obj);
                }

                @Override 
                public  LeaderboardScore freeze() {
                    return m1194cd();
                }

                @Override 
                public String getDisplayRank() {
                    return getString("display_rank");
                }

                @Override 
                public void getDisplayRank(CharArrayBuffer dataOut) {
                    m1029a("display_rank", dataOut);
                }

                @Override 
                public String getDisplayScore() {
                    return getString("display_score");
                }

                @Override 
                public void getDisplayScore(CharArrayBuffer dataOut) {
                    m1029a("display_score", dataOut);
                }

                @Override 
                public long getRank() {
                    return getLong("rank");
                }

                @Override 
                public long getRawScore() {
                    return getLong("raw_score");
                }

                @Override 
                public Player getScoreHolder() {
                    if (m1031v("external_player_id")) {
                        return null;
                    }
                    return this.f1078nH;
                }

                @Override 
                public String getScoreHolderDisplayName() {
                    return m1031v("external_player_id") ? getString("default_display_name") : this.f1078nH.getDisplayName();
                }

                @Override 
                public void getScoreHolderDisplayName(CharArrayBuffer dataOut) {
                    if (m1031v("external_player_id")) {
                        m1029a("default_display_name", dataOut);
                    } else {
                        this.f1078nH.getDisplayName(dataOut);
                    }
                }

                @Override 
                public Uri getScoreHolderHiResImageUri() {
                    if (m1031v("external_player_id")) {
                        return null;
                    }
                    return this.f1078nH.getHiResImageUri();
                }

                @Override 
                public Uri getScoreHolderIconImageUri() {
                    return m1031v("external_player_id") ? m1030u("default_display_image_uri") : this.f1078nH.getIconImageUri();
                }

                @Override 
                public String getScoreTag() {
                    return getString("score_tag");
                }

                @Override 
                public long getTimestampMillis() {
                    return getLong("achieved_timestamp");
                }

                @Override 
                public int hashCode() {
                    return C0918c.m1190a(this);
                }

                public String toString() {
                    return C0918c.m1192b(this);
                }
            }
