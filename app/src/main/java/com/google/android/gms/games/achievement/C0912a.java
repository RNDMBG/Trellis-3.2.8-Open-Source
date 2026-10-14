            package com.google.android.gms.games.achievement;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.games.C0915d;
            import com.google.android.gms.games.Player;
            import com.google.android.gms.internal.C1017db;
            import com.google.android.gms.internal.C1027dl;
            
            
            
            public final class C0912a extends AbstractC0889b implements Achievement {
                C0912a(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                @Override 
                public String getAchievementId() {
                    return getString("external_achievement_id");
                }

                @Override 
                public int getCurrentSteps() {
                    C1017db.m1525k(getType() == 1);
                    return getInteger("current_steps");
                }

                @Override 
                public String getDescription() {
                    return getString("description");
                }

                @Override 
                public void getDescription(CharArrayBuffer dataOut) {
                    m1029a("description", dataOut);
                }

                @Override 
                public String getFormattedCurrentSteps() {
                    C1017db.m1525k(getType() == 1);
                    return getString("formatted_current_steps");
                }

                @Override 
                public void getFormattedCurrentSteps(CharArrayBuffer dataOut) {
                    C1017db.m1525k(getType() == 1);
                    m1029a("formatted_current_steps", dataOut);
                }

                @Override 
                public String getFormattedTotalSteps() {
                    C1017db.m1525k(getType() == 1);
                    return getString("formatted_total_steps");
                }

                @Override 
                public void getFormattedTotalSteps(CharArrayBuffer dataOut) {
                    C1017db.m1525k(getType() == 1);
                    m1029a("formatted_total_steps", dataOut);
                }

                @Override 
                public long getLastUpdatedTimestamp() {
                    return getLong("last_updated_timestamp");
                }

                @Override 
                public String getName() {
                    return getString("name");
                }

                @Override 
                public void getName(CharArrayBuffer dataOut) {
                    m1029a("name", dataOut);
                }

                @Override 
                public Player getPlayer() {
                    return new C0915d(this.f951jf, this.f952ji);
                }

                @Override 
                public Uri getRevealedImageUri() {
                    return m1030u("revealed_icon_image_uri");
                }

                @Override 
                public int getState() {
                    return getInteger("state");
                }

                @Override 
                public int getTotalSteps() {
                    C1017db.m1525k(getType() == 1);
                    return getInteger("total_steps");
                }

                @Override 
                public int getType() {
                    return getInteger("type");
                }

                @Override 
                public Uri getUnlockedImageUri() {
                    return m1030u("unlocked_icon_image_uri");
                }

                public String toString() {
                    C1027dl.a aVarM1600a = C1027dl.m1599d(this).m1600a("id", getAchievementId()).m1600a("name", getName()).m1600a("state", Integer.valueOf(getState())).m1600a("type", Integer.valueOf(getType()));
                    if (getType() == 1) {
                        aVarM1600a.m1600a("steps", getCurrentSteps() + "/" + getTotalSteps());
                    }
                    return aVarM1600a.toString();
                }
            }
