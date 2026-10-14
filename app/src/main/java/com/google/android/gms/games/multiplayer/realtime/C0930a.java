            package com.google.android.gms.games.multiplayer.realtime;

            import com.google.android.gms.common.data.AbstractC0893f;
            import com.google.android.gms.common.data.C0891d;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.multiplayer.realtime.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0930a extends AbstractC0893f<Room> {
                public C0930a(C0891d c0891d) {
                    super(c0891d);
                }

                @Override // com.google.android.gms.common.data.AbstractC0893f
                /* JADX INFO: renamed from: a */
                protected /* synthetic */ Room mo1058a(int i, int i2) {
                    return m1228b(i, i2);
                }

                /* JADX INFO: renamed from: b */
                protected Room m1228b(int i, int i2) {
                    return new C0932c(this.f948jf, i, i2);
                }

                @Override // com.google.android.gms.common.data.AbstractC0893f
                protected String getPrimaryDataMarkerColumn() {
                    return "external_match_id";
                }
            }
