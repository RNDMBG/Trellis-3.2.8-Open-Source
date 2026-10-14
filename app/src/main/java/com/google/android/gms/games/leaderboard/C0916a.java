            package com.google.android.gms.games.leaderboard;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.internal.C1027dl;
            import java.util.ArrayList;
            
            
            
            public final class C0916a extends AbstractC0889b implements Leaderboard {

                
                private final int f1066nu;

                C0916a(C0891d c0891d, int i, int i2) {
                    super(c0891d, i);
                    this.f1066nu = i2;
                }

                @Override 
                public String getDisplayName() {
                    return getString("name");
                }

                @Override 
                public void getDisplayName(CharArrayBuffer dataOut) {
                    m1029a("name", dataOut);
                }

                @Override 
                public Uri getIconImageUri() {
                    return m1030u("board_icon_image_uri");
                }

                @Override 
                public String getLeaderboardId() {
                    return getString("external_leaderboard_id");
                }

                @Override 
                public int getScoreOrder() {
                    return getInteger("score_order");
                }

                @Override 
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
