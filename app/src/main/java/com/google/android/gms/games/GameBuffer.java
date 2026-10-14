            package com.google.android.gms.games;

            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.common.data.DataBuffer;
            
            
            public final class GameBuffer extends DataBuffer<Game> {
                public GameBuffer(C0891d dataHolder) {
                    super(dataHolder);
                }

                
                @Override 
                public Game get(int position) {
                    return new C0913b(this.f948jf, position);
                }

            }
