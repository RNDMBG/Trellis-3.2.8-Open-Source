            package com.google.android.gms.games.multiplayer;

            import android.os.Parcel;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.games.C0913b;
            import com.google.android.gms.games.Game;
            import com.google.android.gms.internal.C1028dm;
            import java.util.ArrayList;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.multiplayer.b */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0924b extends AbstractC0889b implements Invitation {

                /* JADX INFO: renamed from: nQ */
                private final ArrayList<Participant> f1097nQ;

                /* JADX INFO: renamed from: nS */
                private final Game f1098nS;

                /* JADX INFO: renamed from: nT */
                private final C0926d f1099nT;

                C0924b(C0891d c0891d, int i, int i2) {
                    super(c0891d, i);
                    this.f1098nS = new C0913b(c0891d, i);
                    this.f1097nQ = new ArrayList<>(i2);
                    String string = getString("external_inviter_id");
                    C0926d c0926d = null;
                    for (int i3 = 0; i3 < i2; i3++) {
                        C0926d c0926d2 = new C0926d(this.f951jf, this.f952ji + i3);
                        if (c0926d2.getParticipantId().equals(string)) {
                            c0926d = c0926d2;
                        }
                        this.f1097nQ.add(c0926d2);
                    }
                    this.f1099nT = (C0926d) C1028dm.m1601a(c0926d, "Must have a valid inviter!");
                }

                @Override // com.google.android.gms.games.multiplayer.Invitation
                /* JADX INFO: renamed from: ch */
                public int mo1198ch() {
                    return getInteger("type");
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public boolean equals(Object obj) {
                    return InvitationEntity.m1201a(this, obj);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.Freezable
                public Invitation freeze() {
                    return new InvitationEntity(this);
                }


                @Override // com.google.android.gms.games.multiplayer.Invitation
                public long getCreationTimestamp() {
                    return getLong("creation_timestamp");
                }

                @Override // com.google.android.gms.games.multiplayer.Invitation
                public Game getGame() {
                    return this.f1098nS;
                }

                @Override // com.google.android.gms.games.multiplayer.Invitation
                public String getInvitationId() {
                    return getString("external_invitation_id");
                }

                @Override // com.google.android.gms.games.multiplayer.Invitation
                public Participant getInviter() {
                    return this.f1099nT;
                }

                @Override // com.google.android.gms.games.multiplayer.Participatable
                public ArrayList<Participant> getParticipants() {
                    return this.f1097nQ;
                }

                @Override // com.google.android.gms.games.multiplayer.Invitation
                public int getVariant() {
                    return getInteger("variant");
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public int hashCode() {
                    return InvitationEntity.m1200a(this);
                }

                public String toString() {
                    return InvitationEntity.m1202b(this);
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    ((InvitationEntity) freeze()).writeToParcel(dest, flags);
                }
            }
