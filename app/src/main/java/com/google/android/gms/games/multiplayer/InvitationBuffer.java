            package com.google.android.gms.games.multiplayer;

            import com.google.android.gms.common.data.AbstractC0893f;
            import com.google.android.gms.common.data.C0891d;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class InvitationBuffer extends AbstractC0893f<Invitation> {
                public InvitationBuffer(C0891d dataHolder) {
                    super(dataHolder);
                }

                @Override // com.google.android.gms.common.data.AbstractC0893f
                /* JADX INFO: renamed from: a */
                protected /* synthetic */ Invitation mo1058a(int i, int i2) {
                    return getEntry(i, i2);
                }

                protected Invitation getEntry(int rowIndex, int numChildren) {
                    return new C0924b(this.f948jf, rowIndex, numChildren);
                }

                @Override // com.google.android.gms.common.data.AbstractC0893f
                protected String getPrimaryDataMarkerColumn() {
                    return "external_invitation_id";
                }
            }
