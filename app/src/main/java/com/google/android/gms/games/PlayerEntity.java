            package com.google.android.gms.games;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.internal.AbstractC1056en;
            import com.google.android.gms.internal.C1017db;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1049eg;
            
            
            public final class PlayerEntity extends AbstractC1056en implements Player {
                public static final Parcelable.Creator<PlayerEntity> CREATOR = new C0910a();

                
                private final int f1054iM;

                
                private final String f1055mD;

                
                private final long f1056mE;

                
                private final String f1057ml;

                
                private final Uri f1058mq;

                
                private final Uri f1059mr;

                
                static final class C0910a extends C0914c {
                    C0910a() {
                    }

                    @Override 
                    public  PlayerEntity createFromParcel(Parcel x0) {
                        return mo1182u(x0);
                    }

                    @Override 
                    
                    public PlayerEntity mo1182u(Parcel parcel) {
                        if (PlayerEntity.m1180b(PlayerEntity.m1181bQ()) || PlayerEntity.m1176J(PlayerEntity.class.getCanonicalName())) {
                            return super.mo1182u(parcel);
                        }
                        String string = parcel.readString();
                        String string2 = parcel.readString();
                        String string3 = parcel.readString();
                        String string4 = parcel.readString();
                        return new PlayerEntity(1, string, string2, string3 == null ? null : Uri.parse(string3), string4 != null ? Uri.parse(string4) : null, parcel.readLong());
                    }
                }

                PlayerEntity(int versionCode, String playerId, String displayName, Uri iconImageUri, Uri hiResImageUri, long retrievedTimestamp) {
                    this.f1054iM = versionCode;
                    this.f1055mD = playerId;
                    this.f1057ml = displayName;
                    this.f1058mq = iconImageUri;
                    this.f1059mr = hiResImageUri;
                    this.f1056mE = retrievedTimestamp;
                }

                public PlayerEntity(Player player) {
                    this.f1054iM = 1;
                    this.f1055mD = player.getPlayerId();
                    this.f1057ml = player.getDisplayName();
                    this.f1058mq = player.getIconImageUri();
                    this.f1059mr = player.getHiResImageUri();
                    this.f1056mE = player.getRetrievedTimestamp();
                    C1017db.m1524c(this.f1055mD);
                    C1017db.m1524c(this.f1057ml);
                    C1017db.m1525k(this.f1056mE > 0);
                }

                
                static  boolean m1176J(String str) {
                    return m1531y(str);
                }

                
                static int m1177a(Player player) {
                    return C1027dl.hashCode(player.getPlayerId(), player.getDisplayName(), player.getIconImageUri(), player.getHiResImageUri(), Long.valueOf(player.getRetrievedTimestamp()));
                }

                
                static boolean m1178a(Player player, Object obj) {
                    if (!(obj instanceof Player)) {
                        return false;
                    }
                    if (player == obj) {
                        return true;
                    }
                    Player player2 = (Player) obj;
                    return C1027dl.equal(player2.getPlayerId(), player.getPlayerId()) && C1027dl.equal(player2.getDisplayName(), player.getDisplayName()) && C1027dl.equal(player2.getIconImageUri(), player.getIconImageUri()) && C1027dl.equal(player2.getHiResImageUri(), player.getHiResImageUri()) && C1027dl.equal(Long.valueOf(player2.getRetrievedTimestamp()), Long.valueOf(player.getRetrievedTimestamp()));
                }

                
                static String m1179b(Player player) {
                    return C1027dl.m1599d(player).m1600a("PlayerId", player.getPlayerId()).m1600a("DisplayName", player.getDisplayName()).m1600a("IconImageUri", player.getIconImageUri()).m1600a("HiResImageUri", player.getHiResImageUri()).m1600a("RetrievedTimestamp", Long.valueOf(player.getRetrievedTimestamp())).toString();
                }

                
                static  boolean m1180b(Integer num) {
                    return m1798c(num);
                }

                
                static  Integer m1181bQ() {
                    return m1530aW();
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object obj) {
                    return m1178a(this, obj);
                }

                
                @Override 
                public Player freeze() {
                    return this;
                }


                @Override 
                public String getDisplayName() {
                    return this.f1057ml;
                }

                @Override 
                public void getDisplayName(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1057ml, dataOut);
                }

                @Override 
                public Uri getHiResImageUri() {
                    return this.f1059mr;
                }

                @Override 
                public Uri getIconImageUri() {
                    return this.f1058mq;
                }

                @Override 
                public String getPlayerId() {
                    return this.f1055mD;
                }

                @Override 
                public long getRetrievedTimestamp() {
                    return this.f1056mE;
                }

                public int getVersionCode() {
                    return this.f1054iM;
                }

                @Override 
                public boolean hasHiResImage() {
                    return getHiResImageUri() != null;
                }

                @Override 
                public boolean hasIconImage() {
                    return getIconImageUri() != null;
                }

                public int hashCode() {
                    return m1177a(this);
                }

                @Override 
                public boolean isDataValid() {
                    return true;
                }

                public String toString() {
                    return m1179b(this);
                }

                @Override 
                public void writeToParcel(Parcel dest, int flags) {
                    if (!m1532aX()) {
                        C0914c.m1185a(this, dest, flags);
                        return;
                    }
                    dest.writeString(this.f1055mD);
                    dest.writeString(this.f1057ml);
                    dest.writeString(this.f1058mq == null ? null : this.f1058mq.toString());
                    dest.writeString(this.f1059mr != null ? this.f1059mr.toString() : null);
                    dest.writeLong(this.f1056mE);
                }
            }
