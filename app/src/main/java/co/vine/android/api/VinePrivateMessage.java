            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import co.vine.android.util.Util;
            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            import java.util.ArrayList;
            
            
    public class VinePrivateMessage implements Externalizable, Parcelable {
                public static final Parcelable.Creator<VinePrivateMessage> CREATOR = new C03511();
                public static final int NO_LOOP_LIMIT = Integer.MAX_VALUE;
                public String avatarUrl;
                public long conversationId;
                public long conversationRowId;
                public long created;
                public boolean ephemeral;
                public int errorCode;
                public String errorReason;
                public boolean hasVideo;
                public boolean isInNetwork;
                public boolean isLast;
                public long localStartTime;
                public int maxLoops;
                public String message;
                public long messageId;
                public long messageRowId;
                public int networkType;
                public VinePost post;
                public long postId;
                public String thumbnailUrl;
                public String uploadPath;
                public long userId;
                public boolean vanished;
                public long vanishedDate;
                public SpannableStringBuilder vanishedViewedSb;
                public String videoUrl;

        public VinePrivateMessage() {
            this.maxLoops = 0;
                }

        public VinePrivateMessage(long messageRowId, long conversationId, long messageId, long userId, long created, String message, String videoUrl, String thumbnailUrl, int networkType, boolean isLast, boolean vanished, int maxLoops, long vanishedDate, boolean ephmeral, long localStartTime, long postId, int errorCode, String errorReason, String uploadPath) {
            this.maxLoops = 0;
            this.messageRowId = messageRowId;
            this.conversationId = conversationId;
            this.messageId = messageId;
            this.userId = userId;
            this.created = created;
            this.message = message;
            this.videoUrl = videoUrl;
            this.thumbnailUrl = thumbnailUrl;
            this.networkType = networkType;
            this.hasVideo = !TextUtils.isEmpty(videoUrl);
            this.isInNetwork = networkType == 1;
            this.isLast = isLast;
            this.vanished = vanished;
            this.maxLoops = maxLoops;
            this.vanishedDate = vanishedDate;
            this.ephemeral = ephmeral;
            this.localStartTime = localStartTime;
            this.postId = postId;
            this.errorCode = errorCode;
            this.errorReason = errorReason;
            this.uploadPath = uploadPath;
                }

        public VinePrivateMessage(long messageRowId, long conversationId, long messageId, long userId, long created, String message, String videoUrl, String thumbnailUrl, int networkType, boolean isLast, boolean vanished, int maxLoops, long vanishedDate, boolean ephmeral, long localStartTime, long postId, int errorCode, String errorReason) {
            this(messageRowId, conversationId, messageId, userId, created, message, videoUrl, thumbnailUrl, networkType, isLast, vanished, maxLoops, vanishedDate, ephmeral, localStartTime, postId, errorCode, errorReason, null);
                }

        public VinePrivateMessage(long messageRowId, long conversationId, long messageId, long userId, long created, String message, String videoUrl, String thumbnailUrl, int networkType, boolean isLast, boolean vanished, int maxLoops, long vanishedDate, boolean ephmeral, long localStartTime, VinePost post, int errorCode, String errorReason) {
           this(messageRowId, conversationId, messageId, userId, created, message, videoUrl, thumbnailUrl, networkType, isLast, vanished, maxLoops, vanishedDate, ephmeral, localStartTime, post != null ? post.postId : 0L, errorCode, errorReason);
           this.post = post;
                }

       public VinePrivateMessage(Cursor cursor) {
            this.maxLoops = 0;
           this.messageRowId = cursor.getLong(0);
           this.conversationId = cursor.getLong(19);
           this.conversationRowId = cursor.getLong(1);
           this.messageId = cursor.getLong(2);
           this.message = cursor.getString(5);
           this.userId = cursor.getLong(3);
           this.created = cursor.getLong(4);
           this.videoUrl = cursor.getString(6);
           this.thumbnailUrl = cursor.getString(7);
           this.avatarUrl = cursor.getString(24);
           this.networkType = cursor.getInt(20);
           this.hasVideo = !TextUtils.isEmpty(this.videoUrl);
           this.isInNetwork = this.networkType == 1;
           this.isLast = cursor.getInt(8) == 1;
           this.vanished = cursor.getInt(9) == 1;
           this.maxLoops = cursor.getInt(10);
           this.vanishedDate = cursor.getLong(11);
           this.ephemeral = cursor.getInt(12) == 1;
           this.localStartTime = cursor.getLong(13);
           this.postId = cursor.getLong(15);
           this.errorCode = cursor.getInt(17);
           this.errorReason = cursor.getString(18);
           this.uploadPath = cursor.getString(16);
                    if (this.postId > 0) {
               this.post = new VinePost();
               this.post.postId = this.postId;
               this.post.userId = cursor.getLong(26);
               this.post.username = cursor.getString(27);
               this.post.avatarUrl = cursor.getString(28);
               this.post.entities = (ArrayList) Util.fromByteArray(cursor.getBlob(29));
               this.post.description = cursor.getString(30);
               this.post.shareUrl = cursor.getString(31);
                    }
                }

                @Override 
       public int describeContents() {
           return 0;
                }

       public VinePrivateMessage(Parcel in) {
            this.maxLoops = 0;
           this.messageRowId = in.readLong();
           this.conversationId = in.readLong();
           this.messageId = in.readLong();
           this.userId = in.readLong();
           this.created = in.readLong();
           this.message = in.readString();
           this.videoUrl = in.readString();
           this.avatarUrl = in.readString();
           this.thumbnailUrl = in.readString();
           this.networkType = in.readInt();
           this.hasVideo = !TextUtils.isEmpty(this.videoUrl);
           this.isInNetwork = this.networkType == 1;
           this.isLast = in.readInt() == 1;
           this.vanished = in.readInt() == 1;
           this.maxLoops = in.readInt();
           this.vanishedDate = in.readLong();
           this.ephemeral = in.readInt() == 1;
           this.localStartTime = in.readLong();
           this.postId = in.readLong();
           this.errorCode = in.readInt();
           this.errorReason = in.readString();
                }

                @Override 
       public void writeToParcel(Parcel out, int flags) {
           out.writeLong(this.messageRowId);
           out.writeLong(this.conversationId);
           out.writeLong(this.messageId);
           out.writeLong(this.userId);
           out.writeLong(this.created);
           out.writeString(this.message);
           out.writeString(this.videoUrl);
           out.writeString(this.avatarUrl);
           out.writeString(this.thumbnailUrl);
           out.writeInt(this.networkType);
           out.writeInt(this.isLast ? 1 : 0);
           out.writeInt(this.vanished ? 1 : 0);
           out.writeInt(this.maxLoops);
           out.writeLong(this.vanishedDate);
           out.writeInt(this.ephemeral ? 1 : 0);
           out.writeLong(this.localStartTime);
           out.writeLong(this.postId);
           out.writeInt(this.errorCode);
           out.writeString(this.errorReason);
                }

                @Override 
       public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
           this.messageRowId = objectInput.readLong();
           this.conversationId = objectInput.readLong();
           this.messageId = objectInput.readLong();
           this.userId = objectInput.readLong();
           this.created = objectInput.readLong();
           this.message = (String) objectInput.readObject();
           this.videoUrl = (String) objectInput.readObject();
           this.avatarUrl = (String) objectInput.readObject();
           this.thumbnailUrl = (String) objectInput.readObject();
           this.networkType = objectInput.readInt();
           this.hasVideo = !TextUtils.isEmpty(this.videoUrl);
           this.isInNetwork = this.networkType == 1;
           this.isLast = objectInput.readInt() == 1;
           this.vanished = objectInput.readInt() == 1;
           this.maxLoops = objectInput.readInt();
           this.vanishedDate = objectInput.readLong();
           this.ephemeral = objectInput.readInt() == 1;
           this.localStartTime = objectInput.readLong();
           this.postId = objectInput.readLong();
           this.errorCode = objectInput.readInt();
           this.errorReason = objectInput.readLine();
                }

                @Override 
       public void writeExternal(ObjectOutput objectOutput) throws IOException {
           objectOutput.writeLong(this.messageRowId);
           objectOutput.writeLong(this.conversationId);
           objectOutput.writeLong(this.messageId);
           objectOutput.writeLong(this.userId);
           objectOutput.writeLong(this.created);
           objectOutput.writeObject(this.message);
           objectOutput.writeObject(this.videoUrl);
           objectOutput.writeObject(this.avatarUrl);
           objectOutput.writeObject(this.thumbnailUrl);
           objectOutput.writeInt(this.networkType);
           objectOutput.writeInt(this.isLast ? 1 : 0);
           objectOutput.writeInt(this.vanished ? 1 : 0);
           objectOutput.writeInt(this.maxLoops);
           objectOutput.writeLong(this.vanishedDate);
           objectOutput.writeInt(this.ephemeral ? 1 : 0);
           objectOutput.writeLong(this.localStartTime);
           objectOutput.writeLong(this.postId);
           objectOutput.writeInt(this.errorCode);
           objectOutput.writeChars(this.errorReason);
                }

       public boolean equals(Object o) {
           if (this == o) {
                        return true;
                    }
           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
           VinePrivateMessage that = (VinePrivateMessage) o;
                    if (this.conversationId == that.conversationId && this.messageId == that.messageId && this.userId == that.userId && this.created == that.created) {
               if (this.message == null ? that.message != null : !this.message.equals(that.message)) {
                            return false;
                        }
               if (this.videoUrl == null ? that.videoUrl != null : !this.videoUrl.equals(that.videoUrl)) {
                            return false;
                        }
               if (this.avatarUrl == null ? that.avatarUrl != null : !this.avatarUrl.equals(that.avatarUrl)) {
                            return false;
                        }
               if (this.thumbnailUrl == null ? that.thumbnailUrl != null : !this.thumbnailUrl.equals(that.thumbnailUrl)) {
                            return false;
                        }
                        return this.networkType == that.networkType && this.ephemeral == that.ephemeral && this.isLast == that.isLast && this.vanished == that.vanished && this.maxLoops == that.maxLoops && this.vanishedDate == that.vanishedDate && this.localStartTime == that.localStartTime && this.postId == that.postId && this.errorCode == that.errorCode;
                    }
                    return false;
                }

       public int hashCode() {
           int result = (int) (this.conversationId ^ (this.conversationId >>> 32));
           return (((((((((((((((((((((((((((((result * 31) + ((int) (this.messageId ^ (this.messageId >>> 32)))) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + ((int) (this.created ^ (this.created >>> 32)))) * 31) + this.networkType) * 31) + (this.message != null ? this.message.hashCode() : 0)) * 31) + (this.videoUrl != null ? this.videoUrl.hashCode() : 0)) * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.thumbnailUrl != null ? this.thumbnailUrl.hashCode() : 0)) * 31) + (this.isLast ? 1 : 0)) * 31) + (this.vanished ? 1 : 0)) * 31) + this.maxLoops) * 31) + ((int) (this.vanishedDate ^ (this.vanishedDate >>> 32)))) * 31) + ((int) (this.localStartTime ^ (this.localStartTime >>> 32)))) * 31) + ((int) (this.postId ^ (this.postId >>> 32)))) * 31) + (this.errorCode ^ (this.errorCode >>> 32));
                }

                
       static class C03511 implements Parcelable.Creator<VinePrivateMessage> {
           C03511() {
                    }



                    
                    @Override 
           public VinePrivateMessage createFromParcel(Parcel in) {
               return new VinePrivateMessage(in);
                    }

                    
                    @Override 
           public VinePrivateMessage[] newArray(int size) {
               return new VinePrivateMessage[size];
                    }
                }
            }
