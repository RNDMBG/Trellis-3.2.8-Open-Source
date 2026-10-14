            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class VineLike implements Parcelable {
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

/* 24 */        public VineLike() {
                }

/* 27 */        public VineLike(Parcel in) {
/* 29 */            this.postId = in.readLong();
/* 30 */            this.avatarUrl = in.readString();
/* 31 */            this.location = in.readString();
/* 32 */            this.username = in.readString();
/* 33 */            this.created = in.readLong();
/* 34 */            this.likeId = in.readLong();
/* 35 */            this.userId = in.readLong();
/* 36 */            this.verified = in.readInt();
/* 37 */            this.user = (VineUser) in.readParcelable(VineUser.class.getClassLoader());
                }

/* 48 */        public static VineLike from(Cursor c) {
/* 49 */            VineLike v = new VineLike();
/* 50 */            v.postId = c.getInt(1);
/* 51 */            v.avatarUrl = c.getString(28);
/* 52 */            v.location = c.getString(31);
/* 53 */            v.username = c.getString(32);
/* 54 */            v.created = c.getLong(30);
/* 55 */            v.likeId = c.getLong(27);
/* 56 */            v.userId = c.getLong(29);
/* 57 */            v.verified = c.getInt(33);
/* 58 */            return v;
                }

                @Override // android.os.Parcelable
/* 62 */        public void writeToParcel(Parcel out, int flags) {
/* 63 */            out.writeLong(this.postId);
/* 64 */            out.writeString(this.avatarUrl);
/* 65 */            out.writeString(this.location);
/* 66 */            out.writeString(this.username);
/* 67 */            out.writeLong(this.created);
/* 68 */            out.writeLong(this.likeId);
/* 69 */            out.writeLong(this.userId);
/* 70 */            out.writeInt(this.verified);
/* 71 */            out.writeParcelable(this.user, flags);
                }

                @Override // android.os.Parcelable
/* 75 */        public int describeContents() {
/* 76 */            return 0;
                }

/* 80 */        public boolean equals(Object o) {
/* 81 */            if (this == o) {
                        return true;
                    }
/* 82 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 84 */            VineLike vineLike = (VineLike) o;
/* 90 */            if (this.created == vineLike.created && this.likeId == vineLike.likeId && this.postId == vineLike.postId && this.userId == vineLike.userId && this.verified == vineLike.verified) {
/* 91 */                if (this.avatarUrl == null ? vineLike.avatarUrl != null : !this.avatarUrl.equals(vineLike.avatarUrl)) {
                            return false;
                        }
/* 93 */                if (this.location == null ? vineLike.location != null : !this.location.equals(vineLike.location)) {
                            return false;
                        }
/* 95 */                if (this.user == null ? vineLike.user != null : !this.user.equals(vineLike.user)) {
                            return false;
                        }
/* 96 */                if (this.username != null) {
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

/* 103 */       public int hashCode() {
/* 104 */           int result = (int) (this.postId ^ (this.postId >>> 32));
/* 112 */           return (((((((((((((((result * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + (this.username != null ? this.username.hashCode() : 0)) * 31) + ((int) (this.created ^ (this.created >>> 32)))) * 31) + ((int) (this.likeId ^ (this.likeId >>> 32)))) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + this.verified) * 31) + (this.user != null ? this.user.hashCode() : 0);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineLike$1 */
/* 114 */       static class C03291 implements Parcelable.Creator<VineLike> {
/* 115 */           C03291() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 118 */           public VineLike createFromParcel(Parcel in) {
/* 119 */               return new VineLike(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 123 */           public VineLike[] newArray(int size) {
/* 124 */               return new VineLike[size];
                    }
                }
            }
