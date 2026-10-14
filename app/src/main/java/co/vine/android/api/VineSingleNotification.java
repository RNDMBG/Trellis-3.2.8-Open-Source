            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.TextUtils;
            import co.vine.android.HomeTabActivity;
            import co.vine.android.PostOptionsDialogActivity;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.video.VideoKey;
            import java.util.ArrayList;
            
            
    public class VineSingleNotification extends VineNotification implements Parcelable {
                public static final int BaseConversationMessageNotification = 18;
                public static final Parcelable.Creator<VineSingleNotification> CREATOR = new C03581();
                public static final int ConversationMessageTextNotification = 25;
                public static final int FOLLOWING = 1;
                public static final int FOLLOW_REQUESTED = 2;
                public static final int PRIVATE = 4;
                public String avatarUrl;
                public long conversationId;
                public long conversationRowId;
                public int followStatus;
                public int messageCount;
                public int notificationTypeId;
                public String onboard;
                public long postId;
                public String prettyComment;
                public long recipientUserId;
                public String thumbnailUrl;
                public String title;
                public long unreadMessageCount;
                public String url;
                public long userId;
                public String username;
                public boolean verified;
                public final ArrayList<ImageKey> imageKeys = new ArrayList<>();
                public final ArrayList<VideoKey> videoKeys = new ArrayList<>();

        public VineSingleNotification(String avatarUrl, String username, String thumbnailUrl, String comment, long notificationId, long userId, long postId, long createdAt, boolean verified, int notificationTypeId, long conversationId, ArrayList<VineEntity> entities, long unreadMessageCount, long recipientUserId, String onboard, String url, String title) {
            this.avatarUrl = avatarUrl;
            this.username = username;
            this.thumbnailUrl = thumbnailUrl;
            this.comment = comment;
            this.notificationId = notificationId;
            this.userId = userId;
            this.postId = postId;
            this.createdAt = createdAt;
            this.conversationId = conversationId;
            this.unreadMessageCount = unreadMessageCount;
            this.verified = verified;
            this.notificationTypeId = notificationTypeId;
            this.entities = entities;
            this.recipientUserId = recipientUserId;
            this.onboard = onboard;
            this.url = url;
            this.title = title;
                }

        public VineSingleNotification(Cursor cursor) {
            this.avatarUrl = cursor.getString(6);
            this.comment = cursor.getString(3);
            this.notificationId = cursor.getLong(1);
            this.conversationRowId = cursor.getLong(5);
            this.notificationTypeId = cursor.getInt(2);
                }

                @Override 
        public int describeContents() {
            return 0;
                }

        public VineSingleNotification(Parcel in) {
            this.avatarUrl = in.readString();
            this.username = in.readString();
            this.thumbnailUrl = in.readString();
            this.comment = in.readString();
            this.notificationId = in.readLong();
            this.userId = in.readLong();
            this.postId = in.readLong();
            this.createdAt = in.readLong();
            this.conversationId = in.readLong();
            this.unreadMessageCount = in.readLong();
            this.verified = in.readInt() == 1;
            this.notificationTypeId = in.readInt();
            this.entities = (ArrayList) in.readSerializable();
           this.followStatus = in.readInt();
           this.conversationRowId = in.readLong();
           this.messageCount = in.readInt();
           in.readList(this.imageKeys, ImageKey.class.getClassLoader());
           in.readList(this.videoKeys, VideoKey.class.getClassLoader());
           this.onboard = in.readString();
           this.url = in.readString();
           this.prettyComment = in.readString();
           this.title = in.readString();
                }

       public boolean isMessaging() {
                    return this.notificationTypeId == 18 || this.notificationTypeId == 25;
                }

                @Override 
       public void writeToParcel(Parcel out, int flags) {
           out.writeString(this.avatarUrl);
           out.writeString(this.username);
           out.writeString(this.thumbnailUrl);
           out.writeString(this.comment);
           out.writeLong(this.notificationId);
           out.writeLong(this.userId);
           out.writeLong(this.postId);
           out.writeLong(this.createdAt);
           out.writeLong(this.conversationId);
           out.writeLong(this.unreadMessageCount);
           out.writeInt(this.verified ? 1 : 0);
           out.writeInt(this.notificationTypeId);
           out.writeSerializable(this.entities);
           out.writeInt(this.followStatus);
           out.writeLong(this.conversationRowId);
           out.writeInt(this.messageCount);
           out.writeList(this.imageKeys);
           out.writeList(this.videoKeys);
           out.writeString(this.onboard);
           out.writeString(this.url);
           out.writeString(this.prettyComment);
           out.writeString(this.title);
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

                
       static class C03581 implements Parcelable.Creator<VineSingleNotification> {
           C03581() {
                    }



                    
                    @Override 
           public VineSingleNotification createFromParcel(Parcel in) {
               return new VineSingleNotification(in);
                    }

                    
                    @Override 
           public VineSingleNotification[] newArray(int size) {
               return new VineSingleNotification[size];
                    }
                }

       public String toString() {
           if (this.notificationTypeId == 33) {
               return PostOptionsDialogActivity.EXTRA_FAVORITE_USER;
                    }
           if (!TextUtils.isEmpty(this.onboard)) {
               return "onboarding_" + this.onboard;
                    }
           if (isMessaging()) {
               return "message";
                    }
                    if (this.postId != 0) {
               return "post";
                    }
           return HomeTabActivity.TAG_ACTIVITY;
                }
            }
