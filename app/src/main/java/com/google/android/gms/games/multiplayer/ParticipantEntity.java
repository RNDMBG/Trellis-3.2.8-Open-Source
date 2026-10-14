            package com.google.android.gms.games.multiplayer;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.games.Player;
            import com.google.android.gms.games.PlayerEntity;
            import com.google.android.gms.internal.AbstractC1056en;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1049eg;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class ParticipantEntity extends AbstractC1056en implements Participant {
                public static final Parcelable.Creator<ParticipantEntity> CREATOR = new C0922a();

                /* JADX INFO: renamed from: iM */
                private final int f1087iM;

                /* JADX INFO: renamed from: ml */
                private final String f1088ml;

                /* JADX INFO: renamed from: mq */
                private final Uri f1089mq;

                /* JADX INFO: renamed from: mr */
                private final Uri f1090mr;

                /* JADX INFO: renamed from: nU */
                private final int f1091nU;

                /* JADX INFO: renamed from: nV */
                private final String f1092nV;

                /* JADX INFO: renamed from: nW */
                private final boolean f1093nW;

                /* JADX INFO: renamed from: nX */
                private final PlayerEntity f1094nX;

                /* JADX INFO: renamed from: nY */
                private final int f1095nY;

                /* JADX INFO: renamed from: nd */
                private final String f1096nd;

                /* JADX INFO: renamed from: com.google.android.gms.games.multiplayer.ParticipantEntity$a */
                static final class C0922a extends C0925c {
                    C0922a() {
                    }

                    @Override // com.google.android.gms.games.multiplayer.C0925c, android.os.Parcelable.Creator
                    public /* synthetic */ ParticipantEntity createFromParcel(Parcel x0) {
                        return mo1213w(x0);
                    }

                    @Override // com.google.android.gms.games.multiplayer.C0925c
                    /* JADX INFO: renamed from: w */
                    public ParticipantEntity mo1213w(Parcel parcel) {
                        if (ParticipantEntity.m1211b(ParticipantEntity.m1212bQ()) || ParticipantEntity.m1207J(ParticipantEntity.class.getCanonicalName())) {
                            return super.mo1213w(parcel);
                        }
                        String string = parcel.readString();
                        String string2 = parcel.readString();
                        String string3 = parcel.readString();
                        Uri uri = string3 == null ? null : Uri.parse(string3);
                        String string4 = parcel.readString();
                        return new ParticipantEntity(1, string, string2, uri, string4 == null ? null : Uri.parse(string4), parcel.readInt(), parcel.readString(), parcel.readInt() > 0, parcel.readInt() > 0 ? PlayerEntity.CREATOR.createFromParcel(parcel) : null, 7);
                    }
                }

                ParticipantEntity(int versionCode, String participantId, String displayName, Uri iconImageUri, Uri hiResImageUri, int status, String clientAddress, boolean connectedToRoom, PlayerEntity player, int capabilities) {
                    this.f1087iM = versionCode;
                    this.f1096nd = participantId;
                    this.f1088ml = displayName;
                    this.f1089mq = iconImageUri;
                    this.f1090mr = hiResImageUri;
                    this.f1091nU = status;
                    this.f1092nV = clientAddress;
                    this.f1093nW = connectedToRoom;
                    this.f1094nX = player;
                    this.f1095nY = capabilities;
                }

                public ParticipantEntity(Participant participant) {
                    this.f1087iM = 1;
                    this.f1096nd = participant.getParticipantId();
                    this.f1088ml = participant.getDisplayName();
                    this.f1089mq = participant.getIconImageUri();
                    this.f1090mr = participant.getHiResImageUri();
                    this.f1091nU = participant.getStatus();
                    this.f1092nV = participant.mo1206ci();
                    this.f1093nW = participant.isConnectedToRoom();
                    Player player = participant.getPlayer();
                    this.f1094nX = player == null ? null : new PlayerEntity(player);
                    this.f1095nY = participant.getCapabilities();
                }

                /* JADX INFO: renamed from: J */
                static /* synthetic */ boolean m1207J(String str) {
                    return m1531y(str);
                }

                /* JADX INFO: renamed from: a */
                static int m1208a(Participant participant) {
                    return C1027dl.hashCode(participant.getPlayer(), Integer.valueOf(participant.getStatus()), participant.mo1206ci(), Boolean.valueOf(participant.isConnectedToRoom()), participant.getDisplayName(), participant.getIconImageUri(), participant.getHiResImageUri(), Integer.valueOf(participant.getCapabilities()));
                }

                /* JADX INFO: renamed from: a */
                static boolean m1209a(Participant participant, Object obj) {
                    if (!(obj instanceof Participant)) {
                        return false;
                    }
                    if (participant == obj) {
                        return true;
                    }
                    Participant participant2 = (Participant) obj;
                    return C1027dl.equal(participant2.getPlayer(), participant.getPlayer()) && C1027dl.equal(Integer.valueOf(participant2.getStatus()), Integer.valueOf(participant.getStatus())) && C1027dl.equal(participant2.mo1206ci(), participant.mo1206ci()) && C1027dl.equal(Boolean.valueOf(participant2.isConnectedToRoom()), Boolean.valueOf(participant.isConnectedToRoom())) && C1027dl.equal(participant2.getDisplayName(), participant.getDisplayName()) && C1027dl.equal(participant2.getIconImageUri(), participant.getIconImageUri()) && C1027dl.equal(participant2.getHiResImageUri(), participant.getHiResImageUri()) && C1027dl.equal(Integer.valueOf(participant2.getCapabilities()), Integer.valueOf(participant.getCapabilities()));
                }

                /* JADX INFO: renamed from: b */
                static String m1210b(Participant participant) {
                    return C1027dl.m1599d(participant).m1600a("Player", participant.getPlayer()).m1600a("Status", Integer.valueOf(participant.getStatus())).m1600a("ClientAddress", participant.mo1206ci()).m1600a("ConnectedToRoom", Boolean.valueOf(participant.isConnectedToRoom())).m1600a("DisplayName", participant.getDisplayName()).m1600a("IconImage", participant.getIconImageUri()).m1600a("HiResImage", participant.getHiResImageUri()).m1600a("Capabilities", Integer.valueOf(participant.getCapabilities())).toString();
                }

                /* JADX INFO: renamed from: b */
                static /* synthetic */ boolean m1211b(Integer num) {
                    return m1798c(num);
                }

                /* JADX INFO: renamed from: bQ */
                static /* synthetic */ Integer m1212bQ() {
                    return m1530aW();
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                /* JADX INFO: renamed from: ci */
                public String mo1206ci() {
                    return this.f1092nV;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object obj) {
                    return m1209a(this, obj);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.Freezable
                public Participant freeze() {
                    return this;
                }


                @Override // com.google.android.gms.games.multiplayer.Participant
                public int getCapabilities() {
                    return this.f1095nY;
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public String getDisplayName() {
                    return this.f1094nX == null ? this.f1088ml : this.f1094nX.getDisplayName();
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public void getDisplayName(CharArrayBuffer dataOut) {
                    if (this.f1094nX == null) {
                        C1049eg.m1731b(this.f1088ml, dataOut);
                    } else {
                        this.f1094nX.getDisplayName(dataOut);
                    }
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public Uri getHiResImageUri() {
                    return this.f1094nX == null ? this.f1090mr : this.f1094nX.getHiResImageUri();
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public Uri getIconImageUri() {
                    return this.f1094nX == null ? this.f1089mq : this.f1094nX.getIconImageUri();
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public String getParticipantId() {
                    return this.f1096nd;
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public Player getPlayer() {
                    return this.f1094nX;
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public int getStatus() {
                    return this.f1091nU;
                }

                public int getVersionCode() {
                    return this.f1087iM;
                }

                public int hashCode() {
                    return m1208a(this);
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public boolean isConnectedToRoom() {
                    return this.f1093nW;
                }

                @Override // com.google.android.gms.common.data.Freezable
                public boolean isDataValid() {
                    return true;
                }

                public String toString() {
                    return m1210b(this);
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    if (!m1532aX()) {
                        C0925c.m1217a(this, dest, flags);
                        return;
                    }
                    dest.writeString(this.f1096nd);
                    dest.writeString(this.f1088ml);
                    dest.writeString(this.f1089mq == null ? null : this.f1089mq.toString());
                    dest.writeString(this.f1090mr != null ? this.f1090mr.toString() : null);
                    dest.writeInt(this.f1091nU);
                    dest.writeString(this.f1092nV);
                    dest.writeInt(this.f1093nW ? 1 : 0);
                    dest.writeInt(this.f1094nX != null ? 1 : 0);
                    if (this.f1094nX != null) {
                        this.f1094nX.writeToParcel(dest, flags);
                    }
                }
            }
