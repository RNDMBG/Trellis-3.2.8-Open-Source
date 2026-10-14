            package com.google.android.gms.games.multiplayer;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.games.C0915d;
            import com.google.android.gms.games.Player;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.multiplayer.d */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0926d extends AbstractC0889b implements Participant {

                /* JADX INFO: renamed from: nZ */
                private final C0915d f1100nZ;

                public C0926d(C0891d c0891d, int i) {
                    super(c0891d, i);
                    this.f1100nZ = new C0915d(c0891d, i);
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                /* JADX INFO: renamed from: ci */
                public String mo1206ci() {
                    return getString("client_address");
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public boolean equals(Object obj) {
                    return ParticipantEntity.m1209a(this, obj);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.Freezable
                public Participant freeze() {
                    return new ParticipantEntity(this);
                }


                @Override // com.google.android.gms.games.multiplayer.Participant
                public int getCapabilities() {
                    return getInteger("capabilities");
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public String getDisplayName() {
                    return m1031v("external_player_id") ? getString("default_display_name") : this.f1100nZ.getDisplayName();
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public void getDisplayName(CharArrayBuffer dataOut) {
                    if (m1031v("external_player_id")) {
                        m1029a("default_display_name", dataOut);
                    } else {
                        this.f1100nZ.getDisplayName(dataOut);
                    }
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public Uri getHiResImageUri() {
                    if (m1031v("external_player_id")) {
                        return null;
                    }
                    return this.f1100nZ.getHiResImageUri();
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public Uri getIconImageUri() {
                    return m1031v("external_player_id") ? m1030u("default_display_image_uri") : this.f1100nZ.getIconImageUri();
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public String getParticipantId() {
                    return getString("external_participant_id");
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public Player getPlayer() {
                    if (m1031v("external_player_id")) {
                        return null;
                    }
                    return this.f1100nZ;
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public int getStatus() {
                    return getInteger("player_status");
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public int hashCode() {
                    return ParticipantEntity.m1208a(this);
                }

                @Override // com.google.android.gms.games.multiplayer.Participant
                public boolean isConnectedToRoom() {
                    return getInteger("connected") > 0;
                }

                public String toString() {
                    return ParticipantEntity.m1210b(this);
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    ((ParticipantEntity) freeze()).writeToParcel(dest, flags);
                }
            }
