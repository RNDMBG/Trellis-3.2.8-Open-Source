            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class TwitterUser implements Parcelable {
                public static final Parcelable.Creator<TwitterUser> CREATOR = new C03171();
                public boolean defaultProfileImage;
                public String description;
                public String location;
                public String name;
                public String profileUrl;
                public String screenName;
                public String url;
                public long userId;

/* 20 */        public TwitterUser(String name, String screenName, String location, String description, String url, String profileUrl, boolean defaultProfileImage, long userId) {
/* 22 */            this.name = name;
/* 23 */            this.screenName = screenName;
/* 24 */            this.location = location;
/* 25 */            this.description = description;
/* 26 */            this.url = url;
/* 27 */            this.profileUrl = profileUrl;
/* 28 */            this.defaultProfileImage = defaultProfileImage;
/* 29 */            this.userId = userId;
                }

/* 31 */        public TwitterUser(Parcel in) {
/* 33 */            this.name = in.readString();
/* 34 */            this.screenName = in.readString();
/* 35 */            this.location = in.readString();
/* 36 */            this.description = in.readString();
/* 37 */            this.url = in.readString();
/* 38 */            this.profileUrl = in.readString();
/* 39 */            this.defaultProfileImage = in.readInt() == 1;
/* 40 */            this.userId = in.readLong();
                }

                @Override // android.os.Parcelable
/* 44 */        public int describeContents() {
/* 45 */            return 0;
                }

                @Override // android.os.Parcelable
/* 49 */        public void writeToParcel(Parcel out, int flags) {
/* 50 */            out.writeString(this.name);
/* 51 */            out.writeString(this.screenName);
/* 52 */            out.writeString(this.location);
/* 53 */            out.writeString(this.description);
/* 54 */            out.writeString(this.url);
/* 55 */            out.writeString(this.profileUrl);
/* 56 */            out.writeInt(this.defaultProfileImage ? 1 : 0);
/* 57 */            out.writeLong(this.userId);
                }

/* 61 */        public boolean equals(Object o) {
/* 62 */            if (this == o) {
                        return true;
                    }
/* 63 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 65 */            TwitterUser that = (TwitterUser) o;
/* 67 */            if (this.defaultProfileImage == that.defaultProfileImage && this.userId == that.userId) {
/* 69 */                if (this.description == null ? that.description != null : !this.description.equals(that.description)) {
                            return false;
                        }
/* 71 */                if (this.location == null ? that.location != null : !this.location.equals(that.location)) {
                            return false;
                        }
/* 73 */                if (this.name == null ? that.name != null : !this.name.equals(that.name)) {
                            return false;
                        }
/* 74 */                if (this.profileUrl == null ? that.profileUrl != null : !this.profileUrl.equals(that.profileUrl)) {
                            return false;
                        }
/* 76 */                if (this.screenName == null ? that.screenName != null : !this.screenName.equals(that.screenName)) {
                            return false;
                        }
/* 78 */                if (this.url != null) {
                            if (this.url.equals(that.url)) {
                                return true;
                            }
                        } else if (that.url == null) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }

/* 84 */        public int hashCode() {
/* 85 */            int result = this.name != null ? this.name.hashCode() : 0;
/* 92 */            return (((((((((((((result * 31) + (this.screenName != null ? this.screenName.hashCode() : 0)) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + (this.description != null ? this.description.hashCode() : 0)) * 31) + (this.url != null ? this.url.hashCode() : 0)) * 31) + (this.profileUrl != null ? this.profileUrl.hashCode() : 0)) * 31) + (this.defaultProfileImage ? 1 : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)));
                }

                /* JADX INFO: renamed from: co.vine.android.api.TwitterUser$1 */
/* 94 */        static class C03171 implements Parcelable.Creator<TwitterUser> {
/* 95 */            C03171() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 97 */            public TwitterUser createFromParcel(Parcel in) {
/* 98 */                return new TwitterUser(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 101 */           public TwitterUser[] newArray(int size) {
/* 102 */               return new TwitterUser[size];
                    }
                }
            }
