            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VineLike implements Parcelable {
                public static final Parcelable.Creator<VineLike> CREATOR = new C03291();
                public String avatarUrl;
                public long created;
                public long likeId;
                public String location;
                public long postId;
                public VineUser user;
                public long userId;
                public String username;
                public int verified;

        public VineLike() {
                }

        public VineLike(Parcel in) {
            this.postId = in.readLong();
            this.avatarUrl = in.readString();
            this.location = in.readString();
            this.username = in.readString();
            this.created = in.readLong();
            this.likeId = in.readLong();
            this.userId = in.readLong();
            this.verified = in.readInt();
            this.user = (VineUser) in.readParcelable(VineUser.class.getClassLoader());
                }

        public static VineLike from(Cursor c) {
            VineLike v = new VineLike();
            v.postId = c.getInt(1);
            v.avatarUrl = c.getString(28);
            v.location = c.getString(31);
            v.username = c.getString(32);
            v.created = c.getLong(30);
            v.likeId = c.getLong(27);
            v.userId = c.getLong(29);
            v.verified = c.getInt(33);
            return v;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeLong(this.postId);
            out.writeString(this.avatarUrl);
            out.writeString(this.location);
            out.writeString(this.username);
            out.writeLong(this.created);
            out.writeLong(this.likeId);
            out.writeLong(this.userId);
            out.writeInt(this.verified);
            out.writeParcelable(this.user, flags);
                }

                @Override 
        public int describeContents() {
            return 0;
                }

        public boolean equals(Object o) {
            if (this == o) {
                        return true;
                    }
            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
            VineLike vineLike = (VineLike) o;
            if (this.created == vineLike.created && this.likeId == vineLike.likeId && this.postId == vineLike.postId && this.userId == vineLike.userId && this.verified == vineLike.verified) {
                if (this.avatarUrl == null ? vineLike.avatarUrl != null : !this.avatarUrl.equals(vineLike.avatarUrl)) {
                            return false;
                        }
                if (this.location == null ? vineLike.location != null : !this.location.equals(vineLike.location)) {
                            return false;
                        }
                if (this.user == null ? vineLike.user != null : !this.user.equals(vineLike.user)) {
                            return false;
                        }
                if (this.username != null) {
                            if (this.username.equals(vineLike.username)) {
                                return true;
                            }
                        } else if (vineLike.username == null) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }

       public int hashCode() {
           int result = (int) (this.postId ^ (this.postId >>> 32));
           return (((((((((((((((result * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + (this.username != null ? this.username.hashCode() : 0)) * 31) + ((int) (this.created ^ (this.created >>> 32)))) * 31) + ((int) (this.likeId ^ (this.likeId >>> 32)))) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + this.verified) * 31) + (this.user != null ? this.user.hashCode() : 0);
                }

                
       static class C03291 implements Parcelable.Creator<VineLike> {
           C03291() {
                    }



                    
                    @Override 
           public VineLike createFromParcel(Parcel in) {
               return new VineLike(in);
                    }

                    
                    @Override 
           public VineLike[] newArray(int size) {
               return new VineLike[size];
                    }
                }
            }
