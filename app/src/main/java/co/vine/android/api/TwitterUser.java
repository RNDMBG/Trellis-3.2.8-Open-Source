            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class TwitterUser implements Parcelable {
                public static final Parcelable.Creator<TwitterUser> CREATOR = new C03171();
                public boolean defaultProfileImage;
                public String description;
                public String location;
                public String name;
                public String profileUrl;
                public String screenName;
                public String url;
                public long userId;

        public TwitterUser(String name, String screenName, String location, String description, String url, String profileUrl, boolean defaultProfileImage, long userId) {
            this.name = name;
            this.screenName = screenName;
            this.location = location;
            this.description = description;
            this.url = url;
            this.profileUrl = profileUrl;
            this.defaultProfileImage = defaultProfileImage;
            this.userId = userId;
                }

        public TwitterUser(Parcel in) {
            this.name = in.readString();
            this.screenName = in.readString();
            this.location = in.readString();
            this.description = in.readString();
            this.url = in.readString();
            this.profileUrl = in.readString();
            this.defaultProfileImage = in.readInt() == 1;
            this.userId = in.readLong();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.name);
            out.writeString(this.screenName);
            out.writeString(this.location);
            out.writeString(this.description);
            out.writeString(this.url);
            out.writeString(this.profileUrl);
            out.writeInt(this.defaultProfileImage ? 1 : 0);
            out.writeLong(this.userId);
                }

        public boolean equals(Object o) {
            if (this == o) {
                        return true;
                    }
            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
            TwitterUser that = (TwitterUser) o;
            if (this.defaultProfileImage == that.defaultProfileImage && this.userId == that.userId) {
                if (this.description == null ? that.description != null : !this.description.equals(that.description)) {
                            return false;
                        }
                if (this.location == null ? that.location != null : !this.location.equals(that.location)) {
                            return false;
                        }
                if (this.name == null ? that.name != null : !this.name.equals(that.name)) {
                            return false;
                        }
                if (this.profileUrl == null ? that.profileUrl != null : !this.profileUrl.equals(that.profileUrl)) {
                            return false;
                        }
                if (this.screenName == null ? that.screenName != null : !this.screenName.equals(that.screenName)) {
                            return false;
                        }
                if (this.url != null) {
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

        public int hashCode() {
            int result = this.name != null ? this.name.hashCode() : 0;
            return (((((((((((((result * 31) + (this.screenName != null ? this.screenName.hashCode() : 0)) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + (this.description != null ? this.description.hashCode() : 0)) * 31) + (this.url != null ? this.url.hashCode() : 0)) * 31) + (this.profileUrl != null ? this.profileUrl.hashCode() : 0)) * 31) + (this.defaultProfileImage ? 1 : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)));
                }

                
        static class C03171 implements Parcelable.Creator<TwitterUser> {
            C03171() {
                    }



                    
                    @Override 
            public TwitterUser createFromParcel(Parcel in) {
                return new TwitterUser(in);
                    }

                    
                    @Override 
           public TwitterUser[] newArray(int size) {
               return new TwitterUser[size];
                    }
                }
            }
