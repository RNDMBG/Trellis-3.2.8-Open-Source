            package com.google.android.gms.games;

            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.common.data.DataBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class PlayerBuffer extends DataBuffer<Player> {
                public PlayerBuffer(C0891d dataHolder) {
                    super(dataHolder);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.DataBuffer
                public Player get(int position) {
                    return new C0915d(this.f948jf, position);
                }

            }
