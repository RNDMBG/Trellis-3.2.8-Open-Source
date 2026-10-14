            package com.google.android.gms.games.multiplayer.realtime;

            import com.google.android.gms.common.data.AbstractC0893f;
            import com.google.android.gms.common.data.C0891d;
            
            
            
            public final class C0930a extends AbstractC0893f<Room> {
                public C0930a(C0891d c0891d) {
                    super(c0891d);
                }

                @Override 
                
                protected  Room mo1058a(int i, int i2) {
                    return m1228b(i, i2);
                }

                
                protected Room m1228b(int i, int i2) {
                    return new C0932c(this.f948jf, i, i2);
                }

                @Override 
                protected String getPrimaryDataMarkerColumn() {
                    return "external_match_id";
                }
            }
