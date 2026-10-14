            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.SpannableStringBuilder;
            import co.vine.android.util.CommonUtil;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class VineComment implements Parcelable {
                public static final Parcelable.Creator<VineComment> CREATOR = new C03221();
                public String avatarUrl;
                public String comment;
                public long commentId;
                public SpannableStringBuilder commentSb;
                public ArrayList<VineEntity> entities;
                public boolean isLast;
                public String location;
                public long postId;
                public long timestamp;
                public ArrayList<VineEntity> transientEntities;
                public VineUser user;
                public long userId;
                public String username;
                public boolean verified;

/* 46 */        public VineComment(long commentId, String comment, String avatarUrl, VineUser user, long timestamp, String location, long userId, String username, boolean verified, long postId, ArrayList<VineEntity> entities, boolean isLast) {
/* 48 */            this.commentId = commentId;
/* 49 */            this.avatarUrl = avatarUrl;
/* 50 */            this.comment = comment;
/* 51 */            this.user = user;
/* 52 */            this.timestamp = timestamp;
/* 53 */            this.location = location;
/* 54 */            this.userId = userId;
/* 55 */            this.username = username;
/* 56 */            this.verified = verified;
/* 57 */            this.postId = postId;
/* 58 */            this.entities = entities;
/* 59 */            this.isLast = isLast;
                }

/* 71 */        public static VineComment from(Cursor c) {
/* 72 */            return new VineComment(c.getLong(34), c.getString(35), c.getString(36), null, c.getLong(38), c.getString(39), c.getLong(37), c.getString(40), c.getInt(41) == 1, c.getLong(1), (ArrayList) CommonUtil.fromByteArray(c.getBlob(43)), c.getInt(42) == 1);
                }

/* 88 */        public VineComment(Parcel in) {
/* 90 */            this.commentId = in.readLong();
/* 91 */            this.comment = in.readString();
/* 92 */            this.avatarUrl = in.readString();
/* 93 */            this.user = (VineUser) in.readParcelable(VineUser.class.getClassLoader());
/* 94 */            this.timestamp = in.readLong();
/* 95 */            this.location = in.readString();
/* 96 */            this.userId = in.readLong();
/* 97 */            this.username = in.readString();
/* 98 */            this.verified = in.readInt() == 1;
/* 99 */            this.postId = in.readLong();
/* 100 */           this.entities = (ArrayList) in.readSerializable();
/* 101 */           this.isLast = in.readInt() == 1;
                }

                @Override // android.os.Parcelable
/* 105 */       public int describeContents() {
/* 106 */           return 0;
                }

                @Override // android.os.Parcelable
/* 110 */       public void writeToParcel(Parcel dest, int flags) {
/* 111 */           dest.writeLong(this.commentId);
/* 112 */           dest.writeString(this.comment);
/* 113 */           dest.writeString(this.avatarUrl);
/* 114 */           dest.writeParcelable(this.user, flags);
/* 115 */           dest.writeLong(this.timestamp);
/* 116 */           dest.writeString(this.location);
/* 117 */           dest.writeLong(this.userId);
/* 118 */           dest.writeString(this.username);
/* 119 */           dest.writeInt(this.verified ? 1 : 0);
/* 120 */           dest.writeLong(this.postId);
/* 121 */           dest.writeSerializable(this.entities);
/* 122 */           dest.writeLong(this.isLast ? 1L : 0L);
                }

/* 126 */       public boolean equals(Object o) {
/* 127 */           if (this == o) {
                        return true;
                    }
/* 128 */           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 130 */           VineComment that = (VineComment) o;
/* 136 */           if (this.commentId == that.commentId && this.postId == that.postId && this.timestamp == that.timestamp && this.userId == that.userId && this.verified == that.verified) {
/* 137 */               if (this.avatarUrl == null ? that.avatarUrl != null : !this.avatarUrl.equals(that.avatarUrl)) {
                            return false;
                        }
/* 139 */               if (this.comment == null ? that.comment != null : !this.comment.equals(that.comment)) {
                            return false;
                        }
/* 140 */               if (this.entities == null ? that.entities != null : !this.entities.equals(that.entities)) {
                            return false;
                        }
/* 142 */               if (this.location == null ? that.location != null : !this.location.equals(that.location)) {
                            return false;
                        }
/* 144 */               if (this.user == null ? that.user != null : !this.user.equals(that.user)) {
                            return false;
                        }
/* 145 */               if (this.username == null ? that.username != null : !this.username.equals(that.username)) {
                            return false;
                        }
                        return this.isLast == that.isLast;
                    }
                    return false;
                }

/* 153 */       public int hashCode() {
/* 154 */           int result = (int) (this.commentId ^ (this.commentId >>> 32));
/* 165 */           return (((((((((((((((((((((result * 31) + (this.comment != null ? this.comment.hashCode() : 0)) * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.user != null ? this.user.hashCode() : 0)) * 31) + ((int) (this.timestamp ^ (this.timestamp >>> 32)))) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + (this.username != null ? this.username.hashCode() : 0)) * 31) + (this.verified ? 1 : 0)) * 31) + ((int) (this.postId ^ (this.postId >>> 32)))) * 31) + (this.entities != null ? this.entities.hashCode() : 0)) * 31) + (this.verified ? 1 : 0);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineComment$1 */
/* 167 */       static class C03221 implements Parcelable.Creator<VineComment> {
/* 168 */           C03221() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 170 */           public VineComment createFromParcel(Parcel in) {
/* 171 */               return new VineComment(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 174 */           public VineComment[] newArray(int size) {
/* 175 */               return new VineComment[size];
                    }
                }
            }
