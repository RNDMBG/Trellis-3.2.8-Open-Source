            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.SpannableStringBuilder;
            import co.vine.android.util.CommonUtil;
            import java.util.ArrayList;
            
            
    public class VineComment implements Parcelable {
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

        public VineComment(long commentId, String comment, String avatarUrl, VineUser user, long timestamp, String location, long userId, String username, boolean verified, long postId, ArrayList<VineEntity> entities, boolean isLast) {
            this.commentId = commentId;
            this.avatarUrl = avatarUrl;
            this.comment = comment;
            this.user = user;
            this.timestamp = timestamp;
            this.location = location;
            this.userId = userId;
            this.username = username;
            this.verified = verified;
            this.postId = postId;
            this.entities = entities;
            this.isLast = isLast;
                }

        public static VineComment from(Cursor c) {
            return new VineComment(c.getLong(34), c.getString(35), c.getString(36), null, c.getLong(38), c.getString(39), c.getLong(37), c.getString(40), c.getInt(41) == 1, c.getLong(1), (ArrayList) CommonUtil.fromByteArray(c.getBlob(43)), c.getInt(42) == 1);
                }

        public VineComment(Parcel in) {
            this.commentId = in.readLong();
            this.comment = in.readString();
            this.avatarUrl = in.readString();
            this.user = (VineUser) in.readParcelable(VineUser.class.getClassLoader());
            this.timestamp = in.readLong();
            this.location = in.readString();
            this.userId = in.readLong();
            this.username = in.readString();
            this.verified = in.readInt() == 1;
            this.postId = in.readLong();
           this.entities = (ArrayList) in.readSerializable();
           this.isLast = in.readInt() == 1;
                }

                @Override 
       public int describeContents() {
           return 0;
                }

                @Override 
       public void writeToParcel(Parcel dest, int flags) {
           dest.writeLong(this.commentId);
           dest.writeString(this.comment);
           dest.writeString(this.avatarUrl);
           dest.writeParcelable(this.user, flags);
           dest.writeLong(this.timestamp);
           dest.writeString(this.location);
           dest.writeLong(this.userId);
           dest.writeString(this.username);
           dest.writeInt(this.verified ? 1 : 0);
           dest.writeLong(this.postId);
           dest.writeSerializable(this.entities);
           dest.writeLong(this.isLast ? 1L : 0L);
                }

       public boolean equals(Object o) {
           if (this == o) {
                        return true;
                    }
           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
           VineComment that = (VineComment) o;
           if (this.commentId == that.commentId && this.postId == that.postId && this.timestamp == that.timestamp && this.userId == that.userId && this.verified == that.verified) {
               if (this.avatarUrl == null ? that.avatarUrl != null : !this.avatarUrl.equals(that.avatarUrl)) {
                            return false;
                        }
               if (this.comment == null ? that.comment != null : !this.comment.equals(that.comment)) {
                            return false;
                        }
               if (this.entities == null ? that.entities != null : !this.entities.equals(that.entities)) {
                            return false;
                        }
               if (this.location == null ? that.location != null : !this.location.equals(that.location)) {
                            return false;
                        }
               if (this.user == null ? that.user != null : !this.user.equals(that.user)) {
                            return false;
                        }
               if (this.username == null ? that.username != null : !this.username.equals(that.username)) {
                            return false;
                        }
                        return this.isLast == that.isLast;
                    }
                    return false;
                }

       public int hashCode() {
           int result = (int) (this.commentId ^ (this.commentId >>> 32));
           return (((((((((((((((((((((result * 31) + (this.comment != null ? this.comment.hashCode() : 0)) * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.user != null ? this.user.hashCode() : 0)) * 31) + ((int) (this.timestamp ^ (this.timestamp >>> 32)))) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + (this.username != null ? this.username.hashCode() : 0)) * 31) + (this.verified ? 1 : 0)) * 31) + ((int) (this.postId ^ (this.postId >>> 32)))) * 31) + (this.entities != null ? this.entities.hashCode() : 0)) * 31) + (this.verified ? 1 : 0);
                }

                
       static class C03221 implements Parcelable.Creator<VineComment> {
           C03221() {
                    }



                    
                    @Override 
           public VineComment createFromParcel(Parcel in) {
               return new VineComment(in);
                    }

                    
                    @Override 
           public VineComment[] newArray(int size) {
               return new VineComment[size];
                    }
                }
            }
