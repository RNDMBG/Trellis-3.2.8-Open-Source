            package com.google.android.gms.games.multiplayer;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.games.C0915d;
            import com.google.android.gms.games.Player;
            
            
            
            public final class C0926d extends AbstractC0889b implements Participant {

                
                private final C0915d f1100nZ;

                public C0926d(C0891d c0891d, int i) {
                    super(c0891d, i);
                    this.f1100nZ = new C0915d(c0891d, i);
                }

                @Override 
                
                public String mo1206ci() {
                    return getString("client_address");
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                @Override 
                public boolean equals(Object obj) {
                    return ParticipantEntity.m1209a(this, obj);
                }

                
                @Override 
                public Participant freeze() {
                    return new ParticipantEntity(this);
                }


                @Override 
                public int getCapabilities() {
                    return getInteger("capabilities");
                }

                @Override 
                public String getDisplayName() {
                    return m1031v("external_player_id") ? getString("default_display_name") : this.f1100nZ.getDisplayName();
                }

                @Override 
                public void getDisplayName(CharArrayBuffer dataOut) {
                    if (m1031v("external_player_id")) {
                        m1029a("default_display_name", dataOut);
                    } else {
                        this.f1100nZ.getDisplayName(dataOut);
                    }
                }

                @Override 
                public Uri getHiResImageUri() {
                    if (m1031v("external_player_id")) {
                        return null;
                    }
                    return this.f1100nZ.getHiResImageUri();
                }

                @Override 
                public Uri getIconImageUri() {
                    return m1031v("external_player_id") ? m1030u("default_display_image_uri") : this.f1100nZ.getIconImageUri();
                }

                @Override 
                public String getParticipantId() {
                    return getString("external_participant_id");
                }

                @Override 
                public Player getPlayer() {
                    if (m1031v("external_player_id")) {
                        return null;
                    }
                    return this.f1100nZ;
                }

                @Override 
                public int getStatus() {
                    return getInteger("player_status");
                }

                @Override 
                public int hashCode() {
                    return ParticipantEntity.m1208a(this);
                }

                @Override 
                public boolean isConnectedToRoom() {
                    return getInteger("connected") > 0;
                }

                public String toString() {
                    return ParticipantEntity.m1210b(this);
                }

                @Override 
                public void writeToParcel(Parcel dest, int flags) {
                    ((ParticipantEntity) freeze()).writeToParcel(dest, flags);
                }
            }
