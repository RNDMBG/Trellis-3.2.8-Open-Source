            package com.google.android.gms.games.leaderboard;

            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.common.data.DataBuffer;
            
            
            public final class LeaderboardScoreBuffer extends DataBuffer<LeaderboardScore> {

                
                private final C0917b f1060nv;

                public LeaderboardScoreBuffer(C0891d dataHolder) {
                    super(dataHolder);
                    this.f1060nv = new C0917b(dataHolder.m1042aM());
                }

                
                public C0917b m1187cb() {
                    return this.f1060nv;
                }

                
                @Override 
                public LeaderboardScore get(int position) {
                    return new C0919d(this.f948jf, position);
                }

            }
