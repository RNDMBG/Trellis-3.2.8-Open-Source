            package com.google.android.gms.games.leaderboard;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.internal.C1027dl;
            import java.util.ArrayList;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.leaderboard.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0916a extends AbstractC0889b implements Leaderboard {

                /* JADX INFO: renamed from: nu */
                private final int f1066nu;

                C0916a(C0891d c0891d, int i, int i2) {
                    super(c0891d, i);
                    this.f1066nu = i2;
                }

                @Override // com.google.android.gms.games.leaderboard.Leaderboard
                public String getDisplayName() {
                    return getString("name");
                }

                @Override // com.google.android.gms.games.leaderboard.Leaderboard
                public void getDisplayName(CharArrayBuffer dataOut) {
                    m1029a("name", dataOut);
                }

                @Override // com.google.android.gms.games.leaderboard.Leaderboard
                public Uri getIconImageUri() {
                    return m1030u("board_icon_image_uri");
                }

                @Override // com.google.android.gms.games.leaderboard.Leaderboard
                public String getLeaderboardId() {
                    return getString("external_leaderboard_id");
                }

                @Override // com.google.android.gms.games.leaderboard.Leaderboard
                public int getScoreOrder() {
                    return getInteger("score_order");
                }

                @Override // com.google.android.gms.games.leaderboard.Leaderboard
                public ArrayList<LeaderboardVariant> getVariants() {
                    ArrayList<LeaderboardVariant> arrayList = new ArrayList<>(this.f1066nu);
                    for (int i = 0; i < this.f1066nu; i++) {
                        arrayList.add(new C0920e(this.f951jf, this.f952ji + i));
                    }
                    return arrayList;
                }

                public String toString() {
                    return C1027dl.m1599d(this).m1600a("ID", getLeaderboardId()).m1600a("DisplayName", getDisplayName()).m1600a("IconImageURI", getIconImageUri()).m1600a("ScoreOrder", Integer.valueOf(getScoreOrder())).toString();
                }
            }
