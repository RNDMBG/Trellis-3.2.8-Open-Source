            package com.google.android.gms.games.leaderboard;

            import com.google.android.gms.common.data.AbstractC0893f;
            import com.google.android.gms.common.data.C0891d;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class LeaderboardBuffer extends AbstractC0893f<Leaderboard> {
                public LeaderboardBuffer(C0891d dataHolder) {
                    super(dataHolder);
                }

                @Override // com.google.android.gms.common.data.AbstractC0893f
                /* JADX INFO: renamed from: a */
                protected /* synthetic */ Leaderboard mo1058a(int i, int i2) {
                    return getEntry(i, i2);
                }

                protected Leaderboard getEntry(int rowIndex, int numChildren) {
                    return new C0916a(this.f948jf, rowIndex, numChildren);
                }

                @Override // com.google.android.gms.common.data.AbstractC0893f
                protected String getPrimaryDataMarkerColumn() {
                    return "external_leaderboard_id";
                }
            }
