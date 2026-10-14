            package com.google.android.gms.games.achievement;

            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.common.data.DataBuffer;
            
            
            public final class AchievementBuffer extends DataBuffer<Achievement> {
                public AchievementBuffer(C0891d dataHolder) {
                    super(dataHolder);
                }

                
                @Override 
                public Achievement get(int position) {
                    return new C0912a(this.f948jf, position);
                }

            }
