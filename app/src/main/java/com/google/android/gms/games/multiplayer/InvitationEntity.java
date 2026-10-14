            package com.google.android.gms.games.multiplayer;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.games.Game;
            import com.google.android.gms.games.GameEntity;
            import com.google.android.gms.internal.AbstractC1056en;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1028dm;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class InvitationEntity extends AbstractC1056en implements Invitation {
                public static final Parcelable.Creator<InvitationEntity> CREATOR = new C0921a();

                /* JADX INFO: renamed from: iM */
                private final int f1079iM;

                /* JADX INFO: renamed from: nL */
                private final GameEntity f1080nL;

                /* JADX INFO: renamed from: nM */
                private final String f1081nM;

                /* JADX INFO: renamed from: nN */
                private final long f1082nN;

                /* JADX INFO: renamed from: nO */
                private final int f1083nO;

                /* JADX INFO: renamed from: nP */
                private final ParticipantEntity f1084nP;

                /* JADX INFO: renamed from: nQ */
                private final ArrayList<ParticipantEntity> f1085nQ;

                /* JADX INFO: renamed from: nR */
                private final int f1086nR;

                /* JADX INFO: renamed from: com.google.android.gms.games.multiplayer.InvitationEntity$a */
                static final class C0921a extends C0923a {
                    C0921a() {
                    }

                    @Override // com.google.android.gms.games.multiplayer.C0923a, android.os.Parcelable.Creator
                    public /* synthetic */ InvitationEntity createFromParcel(Parcel x0) {
                        return mo1205v(x0);
                    }

                    @Override // com.google.android.gms.games.multiplayer.C0923a
                    /* JADX INFO: renamed from: v */
                    public InvitationEntity mo1205v(Parcel parcel) {
                        if (InvitationEntity.m1203b(InvitationEntity.m1204bQ()) || InvitationEntity.m1199J(InvitationEntity.class.getCanonicalName())) {
                            return super.mo1205v(parcel);
                        }
                        GameEntity gameEntityCreateFromParcel = GameEntity.CREATOR.createFromParcel(parcel);
                        String string = parcel.readString();
                        long j = parcel.readLong();
                        int i = parcel.readInt();
                        ParticipantEntity participantEntityCreateFromParcel = ParticipantEntity.CREATOR.createFromParcel(parcel);
                        int i2 = parcel.readInt();
                        ArrayList arrayList = new ArrayList(i2);
                        for (int i3 = 0; i3 < i2; i3++) {
                            arrayList.add(ParticipantEntity.CREATOR.createFromParcel(parcel));
                        }
                        return new InvitationEntity(1, gameEntityCreateFromParcel, string, j, i, participantEntityCreateFromParcel, arrayList, -1);
                    }
                }

                InvitationEntity(int versionCode, GameEntity game, String invitationId, long creationTimestamp, int invitationType, ParticipantEntity inviter, ArrayList<ParticipantEntity> participants, int variant) {
                    this.f1079iM = versionCode;
                    this.f1080nL = game;
                    this.f1081nM = invitationId;
                    this.f1082nN = creationTimestamp;
                    this.f1083nO = invitationType;
                    this.f1084nP = inviter;
                    this.f1085nQ = participants;
                    this.f1086nR = variant;
                }

                InvitationEntity(Invitation invitation) {
                    this.f1079iM = 1;
                    this.f1080nL = new GameEntity(invitation.getGame());
                    this.f1081nM = invitation.getInvitationId();
                    this.f1082nN = invitation.getCreationTimestamp();
                    this.f1083nO = invitation.mo1198ch();
                    this.f1086nR = invitation.getVariant();
                    String participantId = invitation.getInviter().getParticipantId();
                    Participant participant = null;
                    ArrayList<Participant> participants = invitation.getParticipants();
                    int size = participants.size();
                    this.f1085nQ = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        Participant participant2 = participants.get(i);
                        if (participant2.getParticipantId().equals(participantId)) {
                            participant = participant2;
                        }
                        this.f1085nQ.add((ParticipantEntity) participant2.freeze());
                    }
                    C1028dm.m1601a(participant, "Must have a valid inviter!");
                    this.f1084nP = (ParticipantEntity) participant.freeze();
                }

                /* JADX INFO: renamed from: J */
                static /* synthetic */ boolean m1199J(String str) {
                    return m1531y(str);
                }

                /* JADX INFO: renamed from: a */
                static int m1200a(Invitation invitation) {
                    return C1027dl.hashCode(invitation.getGame(), invitation.getInvitationId(), Long.valueOf(invitation.getCreationTimestamp()), Integer.valueOf(invitation.mo1198ch()), invitation.getInviter(), invitation.getParticipants(), Integer.valueOf(invitation.getVariant()));
                }

                /* JADX INFO: renamed from: a */
                static boolean m1201a(Invitation invitation, Object obj) {
                    if (!(obj instanceof Invitation)) {
                        return false;
                    }
                    if (invitation == obj) {
                        return true;
                    }
                    Invitation invitation2 = (Invitation) obj;
                    return C1027dl.equal(invitation2.getGame(), invitation.getGame()) && C1027dl.equal(invitation2.getInvitationId(), invitation.getInvitationId()) && C1027dl.equal(Long.valueOf(invitation2.getCreationTimestamp()), Long.valueOf(invitation.getCreationTimestamp())) && C1027dl.equal(Integer.valueOf(invitation2.mo1198ch()), Integer.valueOf(invitation.mo1198ch())) && C1027dl.equal(invitation2.getInviter(), invitation.getInviter()) && C1027dl.equal(invitation2.getParticipants(), invitation.getParticipants()) && C1027dl.equal(Integer.valueOf(invitation2.getVariant()), Integer.valueOf(invitation.getVariant()));
                }

                /* JADX INFO: renamed from: b */
                static String m1202b(Invitation invitation) {
                    return C1027dl.m1599d(invitation).m1600a("Game", invitation.getGame()).m1600a("InvitationId", invitation.getInvitationId()).m1600a("CreationTimestamp", Long.valueOf(invitation.getCreationTimestamp())).m1600a("InvitationType", Integer.valueOf(invitation.mo1198ch())).m1600a("Inviter", invitation.getInviter()).m1600a("Participants", invitation.getParticipants()).m1600a("Variant", Integer.valueOf(invitation.getVariant())).toString();
                }

                /* JADX INFO: renamed from: b */
                static /* synthetic */ boolean m1203b(Integer num) {
                    return m1798c(num);
                }

                /* JADX INFO: renamed from: bQ */
                static /* synthetic */ Integer m1204bQ() {
                    return m1530aW();
                }

                @Override // com.google.android.gms.games.multiplayer.Invitation
                /* JADX INFO: renamed from: ch */
                public int mo1198ch() {
                    return this.f1083nO;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object obj) {
                    return m1201a(this, obj);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.Freezable
                public Invitation freeze() {
                    return this;
                }


                @Override // com.google.android.gms.games.multiplayer.Invitation
                public long getCreationTimestamp() {
                    return this.f1082nN;
                }

                @Override // com.google.android.gms.games.multiplayer.Invitation
                public Game getGame() {
                    return this.f1080nL;
                }

                @Override // com.google.android.gms.games.multiplayer.Invitation
                public String getInvitationId() {
                    return this.f1081nM;
                }

                @Override // com.google.android.gms.games.multiplayer.Invitation
                public Participant getInviter() {
                    return this.f1084nP;
                }

                @Override // com.google.android.gms.games.multiplayer.Participatable
                public ArrayList<Participant> getParticipants() {
                    return new ArrayList<>(this.f1085nQ);
                }

                @Override // com.google.android.gms.games.multiplayer.Invitation
                public int getVariant() {
                    return this.f1086nR;
                }

                public int getVersionCode() {
                    return this.f1079iM;
                }

                public int hashCode() {
                    return m1200a(this);
                }

                @Override // com.google.android.gms.common.data.Freezable
                public boolean isDataValid() {
                    return true;
                }

                public String toString() {
                    return m1202b(this);
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    if (!m1532aX()) {
                        C0923a.m1215a(this, dest, flags);
                        return;
                    }
                    this.f1080nL.writeToParcel(dest, flags);
                    dest.writeString(this.f1081nM);
                    dest.writeLong(this.f1082nN);
                    dest.writeInt(this.f1083nO);
                    this.f1084nP.writeToParcel(dest, flags);
                    int size = this.f1085nQ.size();
                    dest.writeInt(size);
                    for (int i = 0; i < size; i++) {
                        this.f1085nQ.get(i).writeToParcel(dest, flags);
                    }
                }
            }
