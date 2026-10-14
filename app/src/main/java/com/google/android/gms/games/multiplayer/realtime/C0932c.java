            package com.google.android.gms.games.multiplayer.realtime;

            import android.database.CharArrayBuffer;
            import android.os.Bundle;
            import android.os.Parcel;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.games.Player;
            import com.google.android.gms.games.multiplayer.C0926d;
            import com.google.android.gms.games.multiplayer.Participant;
            import java.util.ArrayList;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.multiplayer.realtime.c */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0932c extends AbstractC0889b implements Room {

                /* JADX INFO: renamed from: nu */
                private final int f1130nu;

                C0932c(C0891d c0891d, int i, int i2) {
                    super(c0891d, i);
                    this.f1130nu = i2;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public boolean equals(Object obj) {
                    return RoomEntity.m1223a(this, obj);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.Freezable
                public Room freeze() {
                    return new RoomEntity(this);
                }


                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public Bundle getAutoMatchCriteria() {
                    if (getBoolean("has_automatch_criteria")) {
                        return RoomConfig.createAutoMatchCriteria(getInteger("automatch_min_players"), getInteger("automatch_max_players"), getLong("automatch_bit_mask"));
                    }
                    return null;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public int getAutoMatchWaitEstimateSeconds() {
                    return getInteger("automatch_wait_estimate_sec");
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public long getCreationTimestamp() {
                    return getLong("creation_timestamp");
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public String getCreatorId() {
                    return getString("creator_external");
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public String getDescription() {
                    return getString("description");
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public void getDescription(CharArrayBuffer dataOut) {
                    m1029a("description", dataOut);
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public String getParticipantId(String playerId) {
                    ArrayList<Participant> participants = getParticipants();
                    int size = participants.size();
                    for (int i = 0; i < size; i++) {
                        Participant participant = participants.get(i);
                        Player player = participant.getPlayer();
                        if (player != null && player.getPlayerId().equals(playerId)) {
                            return participant.getParticipantId();
                        }
                    }
                    return null;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public ArrayList<String> getParticipantIds() {
                    ArrayList<Participant> participants = getParticipants();
                    ArrayList<String> arrayList = new ArrayList<>(this.f1130nu);
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 >= this.f1130nu) {
                            return arrayList;
                        }
                        arrayList.add(participants.get(i2).getParticipantId());
                        i = i2 + 1;
                    }
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public int getParticipantStatus(String participantId) {
                    ArrayList<Participant> participants = getParticipants();
                    int size = participants.size();
                    for (int i = 0; i < size; i++) {
                        Participant participant = participants.get(i);
                        if (participant.getParticipantId().equals(participantId)) {
                            return participant.getStatus();
                        }
                    }
                    throw new IllegalStateException("Participant " + participantId + " is not in room " + getRoomId());
                }

                @Override // com.google.android.gms.games.multiplayer.Participatable
                public ArrayList<Participant> getParticipants() {
                    ArrayList<Participant> arrayList = new ArrayList<>(this.f1130nu);
                    for (int i = 0; i < this.f1130nu; i++) {
                        arrayList.add(new C0926d(this.f951jf, this.f952ji + i));
                    }
                    return arrayList;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public String getRoomId() {
                    return getString("external_match_id");
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public int getStatus() {
                    return getInteger("status");
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.Room
                public int getVariant() {
                    return getInteger("variant");
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public int hashCode() {
                    return RoomEntity.m1222a(this);
                }

                public String toString() {
                    return RoomEntity.m1224b(this);
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    ((RoomEntity) freeze()).writeToParcel(dest, flags);
                }
            }
