            package com.google.android.gms.games.multiplayer;

            import com.google.android.gms.common.data.DataBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class ParticipantBuffer extends DataBuffer<Participant> {
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.DataBuffer
                public Participant get(int position) {
                    return new C0926d(this.f948jf, position);
                }

            }
