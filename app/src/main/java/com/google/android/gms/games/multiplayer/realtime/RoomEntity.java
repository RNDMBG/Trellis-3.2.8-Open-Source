            package com.google.android.gms.games.multiplayer.realtime;

            import android.database.CharArrayBuffer;
            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.games.Player;
            import com.google.android.gms.games.multiplayer.Participant;
            import com.google.android.gms.games.multiplayer.ParticipantEntity;
            import com.google.android.gms.internal.AbstractC1056en;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1049eg;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class RoomEntity extends AbstractC1056en implements Room {
                public static final Parcelable.Creator<RoomEntity> CREATOR = new C0929a();

                /* JADX INFO: renamed from: iM */
                private final int f1120iM;

                /* JADX INFO: renamed from: mo */
                private final String f1121mo;

                /* JADX INFO: renamed from: nN */
                private final long f1122nN;

                /* JADX INFO: renamed from: nQ */
                private final ArrayList<ParticipantEntity> f1123nQ;

                /* JADX INFO: renamed from: nR */
                private final int f1124nR;

                /* JADX INFO: renamed from: nb */
                private final String f1125nb;

                /* JADX INFO: renamed from: oh */
                private final Bundle f1126oh;

                /* JADX INFO: renamed from: ol */
                private final String f1127ol;

                /* JADX INFO: renamed from: om */
                private final int f1128om;

                /* JADX INFO: renamed from: on */
                private final int f1129on;

                /* JADX INFO: renamed from: com.google.android.gms.games.multiplayer.realtime.RoomEntity$a */
                static final class C0929a extends C0931b {
                    C0929a() {
                    }

                    @Override // com.google.android.gms.games.multiplayer.realtime.C0931b, android.os.Parcelable.Creator
                    public /* synthetic */ RoomEntity createFromParcel(Parcel x0) {
                        return mo1227y(x0);
                    }

                    @Override // com.google.android.gms.games.multiplayer.realtime.C0931b
                    /* JADX INFO: renamed from: y */
                    public RoomEntity mo1227y(Parcel parcel) {
                        if (RoomEntity.m1225b(RoomEntity.m1226bQ()) || RoomEntity.m1221J(RoomEntity.class.getCanonicalName())) {
                            return super.mo1227y(parcel);
                        }
                        String string = parcel.readString();
                        String string2 = parcel.readString();
                        long j = parcel.readLong();
                        int i = parcel.readInt();
                        String string3 = parcel.readString();
                        int i2 = parcel.readInt();
                        Bundle bundle = parcel.readBundle();
                        int i3 = parcel.readInt();
                        ArrayList arrayList = new ArrayList(i3);
                        for (int i4 = 0; i4 < i3; i4++) {
                            arrayList.add(ParticipantEntity.CREATOR.createFromParcel(parcel));
                        }
                        return new RoomEntity(2, string, string2, j, i, string3, i2, bundle, arrayList, -1);
                    }
                }

                RoomEntity(int versionCode, String roomId, String creatorId, long creationTimestamp, int roomStatus, String description, int variant, Bundle autoMatchCriteria, ArrayList<ParticipantEntity> participants, int autoMatchWaitEstimateSeconds) {
                    this.f1120iM = versionCode;
                    this.f1125nb = roomId;
                    this.f1127ol = creatorId;
                    this.f1122nN = creationTimestamp;
                    this.f1128om = roomStatus;
                    this.f1121mo = description;
                    this.f1124nR = variant;
                    this.f1126oh = autoMatchCriteria;
                    this.f1123nQ = participants;
                    this.f1129on = autoMatchWaitEstimateSeconds;
                }

                public RoomEntity(Room room) {
                    this.f1120iM = 2;
                    this.f1125nb = room.getRoomId();
                    this.f1127ol = room.getCreatorId();
                    this.f1122nN = room.getCreationTimestamp();
                    this.f1128om = room.getStatus();
                    this.f1121mo = room.getDescription();
                    this.f1124nR = room.getVariant();
                    this.f1126oh = room.getAutoMatchCriteria();
                    ArrayList<Participant> participants = room.getParticipants();
                    int size = participants.size();
                    this.f1123nQ = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        this.f1123nQ.add((ParticipantEntity) participants.get(i).freeze());
                    }
                    this.f1129on = room.getAutoMatchWaitEstimateSeconds();
                }

                /* JADX INFO: renamed from: J */
                static /* synthetic */ boolean m1221J(String str) {
                    return m1531y(str);
                }

                /* JADX INFO: renamed from: a */
                static int m1222a(Room room) {
                    return C1027dl.hashCode(room.getRoomId(), room.getCreatorId(), Long.valueOf(room.getCreationTimestamp()), Integer.valueOf(room.getStatus()), room.getDescription(), Integer.valueOf(room.getVariant()), room.getAutoMatchCriteria(), room.getParticipants(), Integer.valueOf(room.getAutoMatchWaitEstimateSeconds()));
                }

                /* JADX INFO: renamed from: a */
                static boolean m1223a(Room room, Object obj) {
                    if (!(obj instanceof Room)) {
                        return false;
                    }
                    if (room == obj) {
                        return true;
                    }
                    Room room2 = (Room) obj;
                    return C1027dl.equal(room2.getRoomId(), room.getRoomId()) && C1027dl.equal(room2.getCreatorId(), room.getCreatorId()) && C1027dl.equal(Long.valueOf(room2.getCreationTimestamp()), Long.valueOf(room.getCreationTimestamp())) && C1027dl.equal(Integer.valueOf(room2.getStatus()), Integer.valueOf(room.getStatus())) && C1027dl.equal(room2.getDescription(), room.getDescription()) && C1027dl.equal(Integer.valueOf(room2.getVariant()), Integer.valueOf(room.getVariant())) && C1027dl.equal(room2.getAutoMatchCriteria(), room.getAutoMatchCriteria()) && C1027dl.equal(room2.getParticipants(), room.getParticipants()) && C1027dl.equal(Integer.valueOf(room2.getAutoMatchWaitEstimateSeconds()), Integer.valueOf(room.getAutoMatchWaitEstimateSeconds()));
                }

                /* JADX INFO: renamed from: b */
                static String m1224b(Room room) {
                    return C1027dl.m1599d(room).m1600a("RoomId", room.getRoomId()).m1600a("CreatorId", room.getCreatorId()).m1600a("CreationTimestamp", Long.valueOf(room.getCreationTimestamp())).m1600a("RoomStatus", Integer.valueOf(room.getStatus())).m1600a("Description", room.getDescription()).m1600a("Variant", Integer.valueOf(room.getVariant())).m1600a("AutoMatchCriteria", room.getAutoMatchCriteria()).m1600a("Participants", room.getParticipants()).m1600a("AutoMatchWaitEstimateSeconds", Integer.valueOf(room.getAutoMatchWaitEstimateSeconds())).toString();
                }

                /* JADX INFO: renamed from: b */
                static /* synthetic */ boolean m1225b(Integer num) {
                    return m1798c(num);
                }

                /* JADX INFO: renamed from: bQ */
                static /* synthetic */ Integer m1226bQ() {
                    return m1530aW();
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object obj) {
                    return m1223a(this, obj);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.Freezable
                public Room freeze() {
                    return this;
                }


                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public Bundle getAutoMatchCriteria() {
                    return this.f1126oh;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public int getAutoMatchWaitEstimateSeconds() {
                    return this.f1129on;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public long getCreationTimestamp() {
                    return this.f1122nN;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public String getCreatorId() {
                    return this.f1127ol;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public String getDescription() {
                    return this.f1121mo;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public void getDescription(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1121mo, dataOut);
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public String getParticipantId(String playerId) {
                    int size = this.f1123nQ.size();
                    for (int i = 0; i < size; i++) {
                        ParticipantEntity participantEntity = this.f1123nQ.get(i);
                        Player player = participantEntity.getPlayer();
                        if (player != null && player.getPlayerId().equals(playerId)) {
                            return participantEntity.getParticipantId();
                        }
                    }
                    return null;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public ArrayList<String> getParticipantIds() {
                    int size = this.f1123nQ.size();
                    ArrayList<String> arrayList = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        arrayList.add(this.f1123nQ.get(i).getParticipantId());
                    }
                    return arrayList;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public int getParticipantStatus(String participantId) {
                    int size = this.f1123nQ.size();
                    for (int i = 0; i < size; i++) {
                        ParticipantEntity participantEntity = this.f1123nQ.get(i);
                        if (participantEntity.getParticipantId().equals(participantId)) {
                            return participantEntity.getStatus();
                        }
                    }
                    throw new IllegalStateException("Participant " + participantId + " is not in room " + getRoomId());
                }

                @Override // com.google.android.gms.games.multiplayer.Participatable
                public ArrayList<Participant> getParticipants() {
                    return new ArrayList<>(this.f1123nQ);
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public String getRoomId() {
                    return this.f1125nb;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public int getStatus() {
                    return this.f1128om;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public int getVariant() {
                    return this.f1124nR;
                }

                public int getVersionCode() {
                    return this.f1120iM;
                }

                public int hashCode() {
                    return m1222a(this);
                }

                @Override // com.google.android.gms.common.data.Freezable
                public boolean isDataValid() {
                    return true;
                }

                public String toString() {
                    return m1224b(this);
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    if (!m1532aX()) {
                        C0931b.m1229a(this, dest, flags);
                        return;
                    }
                    dest.writeString(this.f1125nb);
                    dest.writeString(this.f1127ol);
                    dest.writeLong(this.f1122nN);
                    dest.writeInt(this.f1128om);
                    dest.writeString(this.f1121mo);
                    dest.writeInt(this.f1124nR);
                    dest.writeBundle(this.f1126oh);
                    int size = this.f1123nQ.size();
                    dest.writeInt(size);
                    for (int i = 0; i < size; i++) {
                        this.f1123nQ.get(i).writeToParcel(dest, flags);
                    }
                }
            }
