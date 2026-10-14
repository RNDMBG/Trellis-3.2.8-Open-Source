            package com.google.android.gms.games.leaderboard;

            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.common.data.DataBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class LeaderboardScoreBuffer extends DataBuffer<LeaderboardScore> {

                /* JADX INFO: renamed from: nv */
                private final C0917b f1060nv;

                public LeaderboardScoreBuffer(C0891d dataHolder) {
                    super(dataHolder);
                    this.f1060nv = new C0917b(dataHolder.m1042aM());
                }

                /* JADX INFO: renamed from: cb */
                public C0917b m1187cb() {
                    return this.f1060nv;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.DataBuffer
                public LeaderboardScore get(int position) {
                    return new C0919d(this.f948jf, position);
                }

            }
