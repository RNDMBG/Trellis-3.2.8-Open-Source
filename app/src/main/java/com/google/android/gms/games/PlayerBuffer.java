            package com.google.android.gms.games;

            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.common.data.DataBuffer;
            
            
            public final class PlayerBuffer extends DataBuffer<Player> {
                public PlayerBuffer(C0891d dataHolder) {
                    super(dataHolder);
                }

                
                @Override 
                public Player get(int position) {
                    return new C0915d(this.f948jf, position);
                }

            }
