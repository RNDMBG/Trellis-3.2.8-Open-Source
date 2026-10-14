            package com.google.android.gms.games;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import com.google.android.gms.common.data.AbstractC0889b;
            import com.google.android.gms.common.data.C0891d;
            
            
            
            public final class C0915d extends AbstractC0889b implements Player {
                public C0915d(C0891d c0891d, int i) {
                    super(c0891d, i);
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                @Override 
                public boolean equals(Object obj) {
                    return PlayerEntity.m1178a(this, obj);
                }

                
                @Override 
                public Player freeze() {
                    return new PlayerEntity(this);
                }


                @Override 
                public String getDisplayName() {
                    return getString("profile_name");
                }

                @Override 
                public void getDisplayName(CharArrayBuffer dataOut) {
                    m1029a("profile_name", dataOut);
                }

                @Override 
                public Uri getHiResImageUri() {
                    return m1030u("profile_hi_res_image_uri");
                }

                @Override 
                public Uri getIconImageUri() {
                    return m1030u("profile_icon_image_uri");
                }

                @Override 
                public String getPlayerId() {
                    return getString("external_player_id");
                }

                @Override 
                public long getRetrievedTimestamp() {
                    return getLong("last_updated");
                }

                @Override 
                public boolean hasHiResImage() {
                    return getHiResImageUri() != null;
                }

                @Override 
                public boolean hasIconImage() {
                    return getIconImageUri() != null;
                }

                @Override 
                public int hashCode() {
                    return PlayerEntity.m1177a(this);
                }

                public String toString() {
                    return PlayerEntity.m1179b(this);
                }

                @Override 
                public void writeToParcel(Parcel dest, int flags) {
                    ((PlayerEntity) freeze()).writeToParcel(dest, flags);
                }
            }
