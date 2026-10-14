            package com.google.android.gms.games.leaderboard;

            import com.google.android.gms.common.data.AbstractC0893f;
            import com.google.android.gms.common.data.C0891d;
            
            
            public final class LeaderboardBuffer extends AbstractC0893f<Leaderboard> {
                public LeaderboardBuffer(C0891d dataHolder) {
                    super(dataHolder);
                }

                @Override 
                
                protected  Leaderboard mo1058a(int i, int i2) {
                    return getEntry(i, i2);
                }

                protected Leaderboard getEntry(int rowIndex, int numChildren) {
                    return new C0916a(this.f948jf, rowIndex, numChildren);
                }

                @Override 
                protected String getPrimaryDataMarkerColumn() {
                    return "external_leaderboard_id";
                }
            }
