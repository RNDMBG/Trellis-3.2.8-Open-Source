            package com.google.android.gms.games;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.d */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0915d extends AbstractC0889b implements Player {
                public C0915d(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public boolean equals(Object obj) {
                    return PlayerEntity.m1178a(this, obj);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.Freezable
                public Player freeze() {
                    return new PlayerEntity(this);
                }


                @Override // com.google.android.gms.games.Player
                public String getDisplayName() {
                    return getString("profile_name");
                }

                @Override // com.google.android.gms.games.Player
                public void getDisplayName(CharArrayBuffer dataOut) {
                    m1029a("profile_name", dataOut);
                }

                @Override // com.google.android.gms.games.Player
                public Uri getHiResImageUri() {
                    return m1030u("profile_hi_res_image_uri");
                }

                @Override // com.google.android.gms.games.Player
                public Uri getIconImageUri() {
                    return m1030u("profile_icon_image_uri");
                }

                @Override // com.google.android.gms.games.Player
                public String getPlayerId() {
                    return getString("external_player_id");
                }

                @Override // com.google.android.gms.games.Player
                public long getRetrievedTimestamp() {
                    return getLong("last_updated");
                }

                @Override // com.google.android.gms.games.Player
                public boolean hasHiResImage() {
                    return getHiResImageUri() != null;
                }

                @Override // com.google.android.gms.games.Player
                public boolean hasIconImage() {
                    return getIconImageUri() != null;
                }

                @Override // com.google.android.gms.common.data.AbstractC0889b
                public int hashCode() {
                    return PlayerEntity.m1177a(this);
                }

                public String toString() {
                    return PlayerEntity.m1179b(this);
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    ((PlayerEntity) freeze()).writeToParcel(dest, flags);
                }
            }
