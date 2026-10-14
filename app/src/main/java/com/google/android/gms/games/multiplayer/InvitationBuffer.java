            package com.google.android.gms.games.multiplayer;

            import com.google.android.gms.common.data.AbstractC0893f;
            import com.google.android.gms.common.data.C0891d;
            
            
            public final class InvitationBuffer extends AbstractC0893f<Invitation> {
                public InvitationBuffer(C0891d dataHolder) {
                    super(dataHolder);
                }

                @Override 
                
                protected  Invitation mo1058a(int i, int i2) {
                    return getEntry(i, i2);
                }

                protected Invitation getEntry(int rowIndex, int numChildren) {
                    return new C0924b(this.f948jf, rowIndex, numChildren);
                }

                @Override 
                protected String getPrimaryDataMarkerColumn() {
                    return "external_invitation_id";
                }
            }
