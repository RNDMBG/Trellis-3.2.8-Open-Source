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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public class VineSingleNotification extends VineNotification implements Parcelable {
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

/* 51 */        public VineSingleNotification(String avatarUrl, String username, String thumbnailUrl, String comment, long notificationId, long userId, long postId, long createdAt, boolean verified, int notificationTypeId, long conversationId, ArrayList<VineEntity> entities, long unreadMessageCount, long recipientUserId, String onboard, String url, String title) {
/* 53 */            this.avatarUrl = avatarUrl;
/* 54 */            this.username = username;
/* 55 */            this.thumbnailUrl = thumbnailUrl;
/* 56 */            this.comment = comment;
/* 57 */            this.notificationId = notificationId;
/* 58 */            this.userId = userId;
/* 59 */            this.postId = postId;
/* 60 */            this.createdAt = createdAt;
/* 61 */            this.conversationId = conversationId;
/* 62 */            this.unreadMessageCount = unreadMessageCount;
/* 63 */            this.verified = verified;
/* 64 */            this.notificationTypeId = notificationTypeId;
/* 65 */            this.entities = entities;
/* 66 */            this.recipientUserId = recipientUserId;
/* 67 */            this.onboard = onboard;
/* 68 */            this.url = url;
/* 69 */            this.title = title;
                }

/* 71 */        public VineSingleNotification(Cursor cursor) {
/* 73 */            this.avatarUrl = cursor.getString(6);
/* 74 */            this.comment = cursor.getString(3);
/* 75 */            this.notificationId = cursor.getLong(1);
/* 76 */            this.conversationRowId = cursor.getLong(5);
/* 77 */            this.notificationTypeId = cursor.getInt(2);
                }

                @Override // android.os.Parcelable
/* 81 */        public int describeContents() {
/* 82 */            return 0;
                }

/* 85 */        public VineSingleNotification(Parcel in) {
/* 87 */            this.avatarUrl = in.readString();
/* 88 */            this.username = in.readString();
/* 89 */            this.thumbnailUrl = in.readString();
/* 90 */            this.comment = in.readString();
/* 91 */            this.notificationId = in.readLong();
/* 92 */            this.userId = in.readLong();
/* 93 */            this.postId = in.readLong();
/* 94 */            this.createdAt = in.readLong();
/* 95 */            this.conversationId = in.readLong();
/* 96 */            this.unreadMessageCount = in.readLong();
/* 97 */            this.verified = in.readInt() == 1;
/* 98 */            this.notificationTypeId = in.readInt();
/* 99 */            this.entities = (ArrayList) in.readSerializable();
/* 100 */           this.followStatus = in.readInt();
/* 101 */           this.conversationRowId = in.readLong();
/* 102 */           this.messageCount = in.readInt();
/* 103 */           in.readList(this.imageKeys, ImageKey.class.getClassLoader());
/* 104 */           in.readList(this.videoKeys, VideoKey.class.getClassLoader());
/* 105 */           this.onboard = in.readString();
/* 106 */           this.url = in.readString();
/* 107 */           this.prettyComment = in.readString();
/* 108 */           this.title = in.readString();
                }

/* 111 */       public boolean isMessaging() {
                    return this.notificationTypeId == 18 || this.notificationTypeId == 25;
                }

                @Override // android.os.Parcelable
/* 117 */       public void writeToParcel(Parcel out, int flags) {
/* 118 */           out.writeString(this.avatarUrl);
/* 119 */           out.writeString(this.username);
/* 120 */           out.writeString(this.thumbnailUrl);
/* 121 */           out.writeString(this.comment);
/* 122 */           out.writeLong(this.notificationId);
/* 123 */           out.writeLong(this.userId);
/* 124 */           out.writeLong(this.postId);
/* 125 */           out.writeLong(this.createdAt);
/* 126 */           out.writeLong(this.conversationId);
/* 127 */           out.writeLong(this.unreadMessageCount);
/* 128 */           out.writeInt(this.verified ? 1 : 0);
/* 129 */           out.writeInt(this.notificationTypeId);
/* 130 */           out.writeSerializable(this.entities);
/* 131 */           out.writeInt(this.followStatus);
/* 132 */           out.writeLong(this.conversationRowId);
/* 133 */           out.writeInt(this.messageCount);
/* 134 */           out.writeList(this.imageKeys);
/* 135 */           out.writeList(this.videoKeys);
/* 136 */           out.writeString(this.onboard);
/* 137 */           out.writeString(this.url);
/* 138 */           out.writeString(this.prettyComment);
/* 139 */           out.writeString(this.title);
                }

/* 142 */       public void setFollowRequested() {
/* 143 */           this.followStatus |= 2;
                }

/* 146 */       public void setFollowing() {
/* 147 */           this.followStatus |= 1;
                }

/* 150 */       public void setPrivate() {
/* 151 */           this.followStatus |= 4;
                }

/* 161 */       public static boolean isLocked(int followStatus) {
                    return (followStatus & 4) >= 1 && ((followStatus & 2) >= 1 || (followStatus & 1) < 1);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineSingleNotification$1 */
/* 164 */       static class C03581 implements Parcelable.Creator<VineSingleNotification> {
/* 165 */           C03581() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 167 */           public VineSingleNotification createFromParcel(Parcel in) {
/* 168 */               return new VineSingleNotification(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 171 */           public VineSingleNotification[] newArray(int size) {
/* 172 */               return new VineSingleNotification[size];
                    }
                }

/* 177 */       public String toString() {
/* 178 */           if (this.notificationTypeId == 33) {
/* 179 */               return PostOptionsDialogActivity.EXTRA_FAVORITE_USER;
                    }
/* 180 */           if (!TextUtils.isEmpty(this.onboard)) {
/* 181 */               return "onboarding_" + this.onboard;
                    }
/* 182 */           if (isMessaging()) {
/* 183 */               return "message";
                    }
                    if (this.postId != 0) {
/* 185 */               return "post";
                    }
/* 187 */           return HomeTabActivity.TAG_ACTIVITY;
                }
            }
