            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class VineEverydayNotification extends VineNotification implements Parcelable {
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

/* 41 */        public VineEverydayNotification(String comment, long notificationId, String type, VinePost post, VineUser user, ArrayList<VineEntity> entities, VineMilestone milestone, long createdAt, boolean isNew, long anchor) {
/* 43 */            this.comment = comment;
/* 44 */            this.notificationId = notificationId;
/* 45 */            this.type = type;
/* 46 */            this.entities = entities;
/* 47 */            this.user = user;
/* 48 */            this.post = post;
/* 49 */            this.milestone = milestone;
/* 50 */            this.createdAt = createdAt;
/* 51 */            this.isNew = isNew;
/* 52 */            this.anchor = anchor;
                }

                @Override // android.os.Parcelable
/* 56 */        public int describeContents() {
/* 57 */            return 0;
                }

/* 60 */        public VineEverydayNotification(Parcel in) {
/* 62 */            ClassLoader cl = getClass().getClassLoader();
/* 63 */            this.comment = in.readString();
/* 64 */            this.notificationId = in.readLong();
/* 65 */            this.type = in.readString();
/* 66 */            this.post = (VinePost) in.readParcelable(cl);
/* 67 */            this.user = (VineUser) in.readParcelable(cl);
/* 68 */            this.entities = (ArrayList) in.readSerializable();
/* 69 */            this.followStatus = in.readInt();
/* 70 */            this.milestone = (VineMilestone) in.readParcelable(cl);
/* 71 */            this.isNew = in.readInt() == 1;
/* 72 */            this.anchor = in.readLong();
                }

                @Override // android.os.Parcelable
/* 76 */        public void writeToParcel(Parcel out, int flags) {
/* 77 */            out.writeString(this.comment);
/* 78 */            out.writeLong(this.notificationId);
/* 79 */            out.writeString(this.type);
/* 80 */            out.writeParcelable(this.post, flags);
/* 81 */            out.writeParcelable(this.user, flags);
/* 82 */            out.writeSerializable(this.entities);
/* 83 */            out.writeInt(this.followStatus);
/* 84 */            out.writeParcelable(this.milestone, flags);
/* 85 */            out.writeInt(this.isNew ? 1 : 0);
/* 86 */            out.writeLong(this.anchor);
                }

/* 89 */        public void setFollowRequested() {
/* 90 */            this.followStatus |= 2;
                }

/* 93 */        public void setFollowing() {
/* 94 */            this.followStatus |= 1;
                }

/* 97 */        public void setPrivate() {
/* 98 */            this.followStatus |= 4;
                }

/* 108 */       public static boolean isLocked(int followStatus) {
                    return (followStatus & 4) >= 1 && ((followStatus & 2) >= 1 || (followStatus & 1) < 1);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineEverydayNotification$1 */
/* 111 */       static class C03271 implements Parcelable.Creator<VineEverydayNotification> {
/* 112 */           C03271() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 114 */           public VineEverydayNotification createFromParcel(Parcel in) {
/* 115 */               return new VineEverydayNotification(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 118 */           public VineEverydayNotification[] newArray(int size) {
/* 119 */               return new VineEverydayNotification[size];
                    }
                }
            }
