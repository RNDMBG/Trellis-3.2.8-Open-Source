            package com.google.android.gms.games.multiplayer;

            import com.google.android.gms.common.data.DataBuffer;
            
            
            public final class ParticipantBuffer extends DataBuffer<Participant> {
                
                @Override 
                public Participant get(int position) {
                    return new C0926d(this.f948jf, position);
                }

            }
