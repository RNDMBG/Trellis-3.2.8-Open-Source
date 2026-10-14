            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            
    public class VineEverydayNotification extends VineNotification implements Parcelable {
                public static final Parcelable.Creator<VineEverydayNotification> CREATOR = new C03271();
                public static final int FOLLOWING = 1;
                public static final int FOLLOW_REQUESTED = 2;
                public static final int PRIVATE = 4;
                public static final String TYPE_ADDRESS_BOOK_FRIEND_JOINED = "address book friend joined";
                public static final String TYPE_COMMENTED = "commented";
                public static final String TYPE_COMMENT_MENTION = "mentioned comment";
                public static final String TYPE_COUNT = "count";
                public static final String TYPE_FOLLOWED = "followed";
                public static final String TYPE_FOLLOW_APPROVED = "follow approved";
                public static final String TYPE_GROUP_COMMENTED = "grouped comment";
                public static final String TYPE_LIKED = "liked";
                public static final String TYPE_MENTION = "mentioned";
                public static final String TYPE_POST_MENTION = "mentioned post";
                public static final String TYPE_REVINED = "reposted";
                public static final String TYPE_TWITTER_FRIEND_JOINED = "twitter friend joined";
                public long anchor;
                public int followStatus;
                public boolean isNew;
                public VineMilestone milestone;
                public VinePost post;
                public String type;
                public VineUser user;

        public VineEverydayNotification(String comment, long notificationId, String type, VinePost post, VineUser user, ArrayList<VineEntity> entities, VineMilestone milestone, long createdAt, boolean isNew, long anchor) {
            this.comment = comment;
            this.notificationId = notificationId;
            this.type = type;
            this.entities = entities;
            this.user = user;
            this.post = post;
            this.milestone = milestone;
            this.createdAt = createdAt;
            this.isNew = isNew;
            this.anchor = anchor;
                }

                @Override 
        public int describeContents() {
            return 0;
                }

        public VineEverydayNotification(Parcel in) {
            ClassLoader cl = getClass().getClassLoader();
            this.comment = in.readString();
            this.notificationId = in.readLong();
            this.type = in.readString();
            this.post = (VinePost) in.readParcelable(cl);
            this.user = (VineUser) in.readParcelable(cl);
            this.entities = (ArrayList) in.readSerializable();
            this.followStatus = in.readInt();
            this.milestone = (VineMilestone) in.readParcelable(cl);
            this.isNew = in.readInt() == 1;
            this.anchor = in.readLong();
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.comment);
            out.writeLong(this.notificationId);
            out.writeString(this.type);
            out.writeParcelable(this.post, flags);
            out.writeParcelable(this.user, flags);
            out.writeSerializable(this.entities);
            out.writeInt(this.followStatus);
            out.writeParcelable(this.milestone, flags);
            out.writeInt(this.isNew ? 1 : 0);
            out.writeLong(this.anchor);
                }

        public void setFollowRequested() {
            this.followStatus |= 2;
                }

        public void setFollowing() {
            this.followStatus |= 1;
                }

        public void setPrivate() {
            this.followStatus |= 4;
                }

       public static boolean isLocked(int followStatus) {
                    return (followStatus & 4) >= 1 && ((followStatus & 2) >= 1 || (followStatus & 1) < 1);
                }

                
       static class C03271 implements Parcelable.Creator<VineEverydayNotification> {
           C03271() {
                    }



                    
                    @Override 
           public VineEverydayNotification createFromParcel(Parcel in) {
               return new VineEverydayNotification(in);
                    }

                    
                    @Override 
           public VineEverydayNotification[] newArray(int size) {
               return new VineEverydayNotification[size];
                    }
                }
            }
