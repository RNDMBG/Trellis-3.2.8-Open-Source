            package com.google.android.gms.games;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.internal.AbstractC1056en;
            import com.google.android.gms.internal.C1017db;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1049eg;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class PlayerEntity extends AbstractC1056en implements Player {
                public static final Parcelable.Creator<PlayerEntity> CREATOR = new C0910a();

                /* JADX INFO: renamed from: iM */
                private final int f1054iM;

                /* JADX INFO: renamed from: mD */
                private final String f1055mD;

                /* JADX INFO: renamed from: mE */
                private final long f1056mE;

                /* JADX INFO: renamed from: ml */
                private final String f1057ml;

                /* JADX INFO: renamed from: mq */
                private final Uri f1058mq;

                /* JADX INFO: renamed from: mr */
                private final Uri f1059mr;

                /* JADX INFO: renamed from: com.google.android.gms.games.PlayerEntity$a */
                static final class C0910a extends C0914c {
                    C0910a() {
                    }

                    @Override // com.google.android.gms.games.C0914c, android.os.Parcelable.Creator
                    public /* synthetic */ PlayerEntity createFromParcel(Parcel x0) {
                        return mo1182u(x0);
                    }

                    @Override // com.google.android.gms.games.C0914c
                    /* JADX INFO: renamed from: u */
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

                /* JADX INFO: renamed from: J */
                static /* synthetic */ boolean m1176J(String str) {
                    return m1531y(str);
                }

                /* JADX INFO: renamed from: a */
                static int m1177a(Player player) {
                    return C1027dl.hashCode(player.getPlayerId(), player.getDisplayName(), player.getIconImageUri(), player.getHiResImageUri(), Long.valueOf(player.getRetrievedTimestamp()));
                }

                /* JADX INFO: renamed from: a */
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

                /* JADX INFO: renamed from: b */
                static String m1179b(Player player) {
                    return C1027dl.m1599d(player).m1600a("PlayerId", player.getPlayerId()).m1600a("DisplayName", player.getDisplayName()).m1600a("IconImageUri", player.getIconImageUri()).m1600a("HiResImageUri", player.getHiResImageUri()).m1600a("RetrievedTimestamp", Long.valueOf(player.getRetrievedTimestamp())).toString();
                }

                /* JADX INFO: renamed from: b */
                static /* synthetic */ boolean m1180b(Integer num) {
                    return m1798c(num);
                }

                /* JADX INFO: renamed from: bQ */
                static /* synthetic */ Integer m1181bQ() {
                    return m1530aW();
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object obj) {
                    return m1178a(this, obj);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.google.android.gms.common.data.Freezable
                public Player freeze() {
                    return this;
                }


                @Override // com.google.android.gms.games.Player
                public String getDisplayName() {
                    return this.f1057ml;
                }

                @Override // com.google.android.gms.games.Player
                public void getDisplayName(CharArrayBuffer dataOut) {
                    C1049eg.m1731b(this.f1057ml, dataOut);
                }

                @Override // com.google.android.gms.games.Player
                public Uri getHiResImageUri() {
                    return this.f1059mr;
                }

                @Override // com.google.android.gms.games.Player
                public Uri getIconImageUri() {
                    return this.f1058mq;
                }

                @Override // com.google.android.gms.games.Player
                public String getPlayerId() {
                    return this.f1055mD;
                }

                @Override // com.google.android.gms.games.Player
                public long getRetrievedTimestamp() {
                    return this.f1056mE;
                }

                public int getVersionCode() {
                    return this.f1054iM;
                }

                @Override // com.google.android.gms.games.Player
                public boolean hasHiResImage() {
                    return getHiResImageUri() != null;
                }

                @Override // com.google.android.gms.games.Player
                public boolean hasIconImage() {
                    return getIconImageUri() != null;
                }

                public int hashCode() {
                    return m1177a(this);
                }

                @Override // com.google.android.gms.common.data.Freezable
                public boolean isDataValid() {
                    return true;
                }

                public String toString() {
                    return m1179b(this);
                }

                @Override // android.os.Parcelable
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
