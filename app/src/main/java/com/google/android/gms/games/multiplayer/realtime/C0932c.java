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
            
            
            
            public final class C0932c extends AbstractC0889b implements Room {

                
                private final int f1130nu;

                C0932c(C0891d c0891d, int i, int i2) {
                    super(c0891d, i);
                    this.f1130nu = i2;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                @Override 
                public boolean equals(Object obj) {
                    return RoomEntity.m1223a(this, obj);
                }

                
                @Override 
                public Room freeze() {
                    return new RoomEntity(this);
                }


                @Override 
                public Bundle getAutoMatchCriteria() {
                    if (getBoolean("has_automatch_criteria")) {
                        return RoomConfig.createAutoMatchCriteria(getInteger("automatch_min_players"), getInteger("automatch_max_players"), getLong("automatch_bit_mask"));
                    }
                    return null;
                }

                @Override 
                public int getAutoMatchWaitEstimateSeconds() {
                    return getInteger("automatch_wait_estimate_sec");
                }

                @Override 
                public long getCreationTimestamp() {
                    return getLong("creation_timestamp");
                }

                @Override 
                public String getCreatorId() {
                    return getString("creator_external");
                }

                @Override 
                public String getDescription() {
                    return getString("description");
                }

                @Override 
                public void getDescription(CharArrayBuffer dataOut) {
                    m1029a("description", dataOut);
                }

                @Override 
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

                @Override 
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

                @Override 
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

                @Override 
                public ArrayList<Participant> getParticipants() {
                    ArrayList<Participant> arrayList = new ArrayList<>(this.f1130nu);
                    for (int i = 0; i < this.f1130nu; i++) {
                        arrayList.add(new C0926d(this.f951jf, this.f952ji + i));
                    }
                    return arrayList;
                }

                @Override 
                public String getRoomId() {
                    return getString("external_match_id");
                }

                @Override 
                public int getStatus() {
                    return getInteger("status");
                }

                @Override 
                public int getVariant() {
                    return getInteger("variant");
                }

                @Override 
                public int hashCode() {
                    return RoomEntity.m1222a(this);
                }

                public String toString() {
                    return RoomEntity.m1224b(this);
                }

                @Override 
                public void writeToParcel(Parcel dest, int flags) {
                    ((RoomEntity) freeze()).writeToParcel(dest, flags);
                }
            }
