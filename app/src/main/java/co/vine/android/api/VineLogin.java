            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class VineLogin implements Parcelable {
                public static final Parcelable.Creator<VineLogin> CREATOR = new C03301();
                public static final int LOGIN_TYPE_EMAIL = 1;
                public static final int LOGIN_TYPE_TWITTER = 2;
                public String edition;
                public String key;
                public int loginType;
                public String name;
                public String twitterSecret;
                public String twitterToken;
                public long twitterUserId;
                public String twitterUsername;
                public long userId;
                public String username;

/* 24 */        public VineLogin(String key, String username, long userId, String edition) {
/* 26 */            this.key = key;
/* 27 */            this.username = username;
/* 28 */            this.userId = userId;
/* 29 */            this.loginType = 1;
/* 30 */            this.edition = edition;
                }

/* 33 */        public VineLogin(String key, String twitterUsername, String twitterToken, String twitterSecret, long twitterUserId, String edition) {
/* 35 */            this.key = key;
/* 36 */            this.twitterUsername = twitterUsername;
/* 37 */            this.twitterToken = twitterToken;
/* 38 */            this.twitterSecret = twitterSecret;
/* 39 */            this.twitterUserId = twitterUserId;
/* 40 */            this.loginType = 2;
/* 41 */            this.edition = edition;
                }

/* 43 */        public VineLogin(Parcel in) {
/* 45 */            this.key = in.readString();
/* 46 */            this.username = in.readString();
/* 47 */            this.name = in.readString();
/* 48 */            this.twitterUsername = in.readString();
/* 49 */            this.twitterToken = in.readString();
/* 50 */            this.twitterSecret = in.readString();
/* 51 */            this.userId = in.readLong();
/* 52 */            this.twitterUserId = in.readLong();
/* 53 */            this.loginType = in.readInt();
/* 54 */            this.edition = in.readString();
                }

/* 58 */        public boolean equals(Object o) {
/* 59 */            if (this == o) {
                        return true;
                    }
/* 60 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 62 */            VineLogin vineLogin = (VineLogin) o;
/* 64 */            if (this.loginType == vineLogin.loginType && this.twitterUserId == vineLogin.twitterUserId && this.userId == vineLogin.userId) {
/* 67 */                if (this.key == null ? vineLogin.key != null : !this.key.equals(vineLogin.key)) {
                            return false;
                        }
/* 68 */                if (this.name == null ? vineLogin.name != null : !this.name.equals(vineLogin.name)) {
                            return false;
                        }
/* 69 */                if (this.twitterSecret == null ? vineLogin.twitterSecret != null : !this.twitterSecret.equals(vineLogin.twitterSecret)) {
                            return false;
                        }
/* 72 */                if (this.twitterToken == null ? vineLogin.twitterToken != null : !this.twitterToken.equals(vineLogin.twitterToken)) {
                            return false;
                        }
/* 75 */                if (this.twitterUsername == null ? vineLogin.twitterUsername != null : !this.twitterUsername.equals(vineLogin.twitterUsername)) {
                            return false;
                        }
/* 78 */                if (this.username == null ? vineLogin.username != null : !this.username.equals(vineLogin.username)) {
                            return false;
                        }
/* 80 */                if (this.edition != null) {
                            if (this.edition.equals(vineLogin.edition)) {
                                return true;
                            }
                        } else if (vineLogin.edition == null) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }

/* 87 */        public int hashCode() {
/* 88 */            int result = this.key != null ? this.key.hashCode() : 0;
/* 97 */            return (((((((((((((((((result * 31) + (this.username != null ? this.username.hashCode() : 0)) * 31) + (this.name != null ? this.name.hashCode() : 0)) * 31) + (this.twitterToken != null ? this.twitterToken.hashCode() : 0)) * 31) + (this.twitterSecret != null ? this.twitterSecret.hashCode() : 0)) * 31) + (this.twitterUsername != null ? this.twitterUsername.hashCode() : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + ((int) (this.twitterUserId ^ (this.twitterUserId >>> 32)))) * 31) + this.loginType) * 31) + (this.edition != null ? this.edition.hashCode() : 0);
                }

                @Override // android.os.Parcelable
/* 102 */       public int describeContents() {
/* 103 */           return 0;
                }

                @Override // android.os.Parcelable
/* 107 */       public void writeToParcel(Parcel out, int i) {
/* 108 */           out.writeString(this.key);
/* 109 */           out.writeString(this.username);
/* 110 */           out.writeString(this.name);
/* 111 */           out.writeString(this.twitterUsername);
/* 112 */           out.writeString(this.twitterToken);
/* 113 */           out.writeString(this.twitterSecret);
/* 114 */           out.writeLong(this.userId);
/* 115 */           out.writeLong(this.twitterUserId);
/* 116 */           out.writeInt(this.loginType);
/* 117 */           out.writeString(this.edition);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineLogin$1 */
/* 119 */       static class C03301 implements Parcelable.Creator<VineLogin> {
/* 120 */           C03301() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 122 */           public VineLogin createFromParcel(Parcel in) {
/* 123 */               return new VineLogin(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 126 */           public VineLogin[] newArray(int size) {
/* 127 */               return new VineLogin[size];
                    }
                }
            }
