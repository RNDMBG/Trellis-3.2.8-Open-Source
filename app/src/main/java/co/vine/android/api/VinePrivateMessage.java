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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    public class VinePrivateMessage implements Externalizable, Parcelable {
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

/* 58 */        public VinePrivateMessage() {
/* 39 */            this.maxLoops = 0;
                }

/* 63 */        public VinePrivateMessage(long messageRowId, long conversationId, long messageId, long userId, long created, String message, String videoUrl, String thumbnailUrl, int networkType, boolean isLast, boolean vanished, int maxLoops, long vanishedDate, boolean ephmeral, long localStartTime, long postId, int errorCode, String errorReason, String uploadPath) {
/* 39 */            this.maxLoops = 0;
/* 65 */            this.messageRowId = messageRowId;
/* 66 */            this.conversationId = conversationId;
/* 67 */            this.messageId = messageId;
/* 68 */            this.userId = userId;
/* 69 */            this.created = created;
/* 70 */            this.message = message;
/* 71 */            this.videoUrl = videoUrl;
/* 72 */            this.thumbnailUrl = thumbnailUrl;
/* 73 */            this.networkType = networkType;
/* 74 */            this.hasVideo = !TextUtils.isEmpty(videoUrl);
/* 75 */            this.isInNetwork = networkType == 1;
/* 76 */            this.isLast = isLast;
/* 77 */            this.vanished = vanished;
/* 78 */            this.maxLoops = maxLoops;
/* 79 */            this.vanishedDate = vanishedDate;
/* 80 */            this.ephemeral = ephmeral;
/* 81 */            this.localStartTime = localStartTime;
/* 82 */            this.postId = postId;
/* 83 */            this.errorCode = errorCode;
/* 84 */            this.errorReason = errorReason;
/* 85 */            this.uploadPath = uploadPath;
                }

/* 91 */        public VinePrivateMessage(long messageRowId, long conversationId, long messageId, long userId, long created, String message, String videoUrl, String thumbnailUrl, int networkType, boolean isLast, boolean vanished, int maxLoops, long vanishedDate, boolean ephmeral, long localStartTime, long postId, int errorCode, String errorReason) {
/* 92 */            this(messageRowId, conversationId, messageId, userId, created, message, videoUrl, thumbnailUrl, networkType, isLast, vanished, maxLoops, vanishedDate, ephmeral, localStartTime, postId, errorCode, errorReason, null);
                }

/* 99 */        public VinePrivateMessage(long messageRowId, long conversationId, long messageId, long userId, long created, String message, String videoUrl, String thumbnailUrl, int networkType, boolean isLast, boolean vanished, int maxLoops, long vanishedDate, boolean ephmeral, long localStartTime, VinePost post, int errorCode, String errorReason) {
/* 100 */           this(messageRowId, conversationId, messageId, userId, created, message, videoUrl, thumbnailUrl, networkType, isLast, vanished, maxLoops, vanishedDate, ephmeral, localStartTime, post != null ? post.postId : 0L, errorCode, errorReason);
/* 102 */           this.post = post;
                }

/* 104 */       public VinePrivateMessage(Cursor cursor) {
/* 39 */            this.maxLoops = 0;
/* 106 */           this.messageRowId = cursor.getLong(0);
/* 107 */           this.conversationId = cursor.getLong(19);
/* 108 */           this.conversationRowId = cursor.getLong(1);
/* 109 */           this.messageId = cursor.getLong(2);
/* 110 */           this.message = cursor.getString(5);
/* 111 */           this.userId = cursor.getLong(3);
/* 112 */           this.created = cursor.getLong(4);
/* 113 */           this.videoUrl = cursor.getString(6);
/* 114 */           this.thumbnailUrl = cursor.getString(7);
/* 115 */           this.avatarUrl = cursor.getString(24);
/* 116 */           this.networkType = cursor.getInt(20);
/* 117 */           this.hasVideo = !TextUtils.isEmpty(this.videoUrl);
/* 118 */           this.isInNetwork = this.networkType == 1;
/* 119 */           this.isLast = cursor.getInt(8) == 1;
/* 120 */           this.vanished = cursor.getInt(9) == 1;
/* 121 */           this.maxLoops = cursor.getInt(10);
/* 122 */           this.vanishedDate = cursor.getLong(11);
/* 123 */           this.ephemeral = cursor.getInt(12) == 1;
/* 124 */           this.localStartTime = cursor.getLong(13);
/* 125 */           this.postId = cursor.getLong(15);
/* 126 */           this.errorCode = cursor.getInt(17);
/* 127 */           this.errorReason = cursor.getString(18);
/* 128 */           this.uploadPath = cursor.getString(16);
                    if (this.postId > 0) {
/* 131 */               this.post = new VinePost();
/* 132 */               this.post.postId = this.postId;
/* 133 */               this.post.userId = cursor.getLong(26);
/* 134 */               this.post.username = cursor.getString(27);
/* 135 */               this.post.avatarUrl = cursor.getString(28);
/* 136 */               this.post.entities = (ArrayList) Util.fromByteArray(cursor.getBlob(29));
/* 137 */               this.post.description = cursor.getString(30);
/* 138 */               this.post.shareUrl = cursor.getString(31);
                    }
                }

                @Override // android.os.Parcelable
/* 143 */       public int describeContents() {
/* 144 */           return 0;
                }

/* 147 */       public VinePrivateMessage(Parcel in) {
/* 39 */            this.maxLoops = 0;
/* 149 */           this.messageRowId = in.readLong();
/* 150 */           this.conversationId = in.readLong();
/* 151 */           this.messageId = in.readLong();
/* 152 */           this.userId = in.readLong();
/* 153 */           this.created = in.readLong();
/* 154 */           this.message = in.readString();
/* 155 */           this.videoUrl = in.readString();
/* 156 */           this.avatarUrl = in.readString();
/* 157 */           this.thumbnailUrl = in.readString();
/* 158 */           this.networkType = in.readInt();
/* 159 */           this.hasVideo = !TextUtils.isEmpty(this.videoUrl);
/* 160 */           this.isInNetwork = this.networkType == 1;
/* 161 */           this.isLast = in.readInt() == 1;
/* 162 */           this.vanished = in.readInt() == 1;
/* 163 */           this.maxLoops = in.readInt();
/* 164 */           this.vanishedDate = in.readLong();
/* 165 */           this.ephemeral = in.readInt() == 1;
/* 166 */           this.localStartTime = in.readLong();
/* 167 */           this.postId = in.readLong();
/* 168 */           this.errorCode = in.readInt();
/* 169 */           this.errorReason = in.readString();
                }

                @Override // android.os.Parcelable
/* 173 */       public void writeToParcel(Parcel out, int flags) {
/* 174 */           out.writeLong(this.messageRowId);
/* 175 */           out.writeLong(this.conversationId);
/* 176 */           out.writeLong(this.messageId);
/* 177 */           out.writeLong(this.userId);
/* 178 */           out.writeLong(this.created);
/* 179 */           out.writeString(this.message);
/* 180 */           out.writeString(this.videoUrl);
/* 181 */           out.writeString(this.avatarUrl);
/* 182 */           out.writeString(this.thumbnailUrl);
/* 183 */           out.writeInt(this.networkType);
/* 184 */           out.writeInt(this.isLast ? 1 : 0);
/* 185 */           out.writeInt(this.vanished ? 1 : 0);
/* 186 */           out.writeInt(this.maxLoops);
/* 187 */           out.writeLong(this.vanishedDate);
/* 188 */           out.writeInt(this.ephemeral ? 1 : 0);
/* 189 */           out.writeLong(this.localStartTime);
/* 190 */           out.writeLong(this.postId);
/* 191 */           out.writeInt(this.errorCode);
/* 192 */           out.writeString(this.errorReason);
                }

                @Override // java.io.Externalizable
/* 196 */       public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
/* 197 */           this.messageRowId = objectInput.readLong();
/* 198 */           this.conversationId = objectInput.readLong();
/* 199 */           this.messageId = objectInput.readLong();
/* 200 */           this.userId = objectInput.readLong();
/* 201 */           this.created = objectInput.readLong();
/* 202 */           this.message = (String) objectInput.readObject();
/* 203 */           this.videoUrl = (String) objectInput.readObject();
/* 204 */           this.avatarUrl = (String) objectInput.readObject();
/* 205 */           this.thumbnailUrl = (String) objectInput.readObject();
/* 206 */           this.networkType = objectInput.readInt();
/* 207 */           this.hasVideo = !TextUtils.isEmpty(this.videoUrl);
/* 208 */           this.isInNetwork = this.networkType == 1;
/* 209 */           this.isLast = objectInput.readInt() == 1;
/* 210 */           this.vanished = objectInput.readInt() == 1;
/* 211 */           this.maxLoops = objectInput.readInt();
/* 212 */           this.vanishedDate = objectInput.readLong();
/* 213 */           this.ephemeral = objectInput.readInt() == 1;
/* 214 */           this.localStartTime = objectInput.readLong();
/* 215 */           this.postId = objectInput.readLong();
/* 216 */           this.errorCode = objectInput.readInt();
/* 217 */           this.errorReason = objectInput.readLine();
                }

                @Override // java.io.Externalizable
/* 221 */       public void writeExternal(ObjectOutput objectOutput) throws IOException {
/* 222 */           objectOutput.writeLong(this.messageRowId);
/* 223 */           objectOutput.writeLong(this.conversationId);
/* 224 */           objectOutput.writeLong(this.messageId);
/* 225 */           objectOutput.writeLong(this.userId);
/* 226 */           objectOutput.writeLong(this.created);
/* 227 */           objectOutput.writeObject(this.message);
/* 228 */           objectOutput.writeObject(this.videoUrl);
/* 229 */           objectOutput.writeObject(this.avatarUrl);
/* 230 */           objectOutput.writeObject(this.thumbnailUrl);
/* 231 */           objectOutput.writeInt(this.networkType);
/* 232 */           objectOutput.writeInt(this.isLast ? 1 : 0);
/* 233 */           objectOutput.writeInt(this.vanished ? 1 : 0);
/* 234 */           objectOutput.writeInt(this.maxLoops);
/* 235 */           objectOutput.writeLong(this.vanishedDate);
/* 236 */           objectOutput.writeInt(this.ephemeral ? 1 : 0);
/* 237 */           objectOutput.writeLong(this.localStartTime);
/* 238 */           objectOutput.writeLong(this.postId);
/* 239 */           objectOutput.writeInt(this.errorCode);
/* 240 */           objectOutput.writeChars(this.errorReason);
                }

/* 244 */       public boolean equals(Object o) {
/* 245 */           if (this == o) {
                        return true;
                    }
/* 246 */           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 248 */           VinePrivateMessage that = (VinePrivateMessage) o;
                    if (this.conversationId == that.conversationId && this.messageId == that.messageId && this.userId == that.userId && this.created == that.created) {
/* 254 */               if (this.message == null ? that.message != null : !this.message.equals(that.message)) {
                            return false;
                        }
/* 256 */               if (this.videoUrl == null ? that.videoUrl != null : !this.videoUrl.equals(that.videoUrl)) {
                            return false;
                        }
/* 258 */               if (this.avatarUrl == null ? that.avatarUrl != null : !this.avatarUrl.equals(that.avatarUrl)) {
                            return false;
                        }
/* 260 */               if (this.thumbnailUrl == null ? that.thumbnailUrl != null : !this.thumbnailUrl.equals(that.thumbnailUrl)) {
                            return false;
                        }
                        return this.networkType == that.networkType && this.ephemeral == that.ephemeral && this.isLast == that.isLast && this.vanished == that.vanished && this.maxLoops == that.maxLoops && this.vanishedDate == that.vanishedDate && this.localStartTime == that.localStartTime && this.postId == that.postId && this.errorCode == that.errorCode;
                    }
                    return false;
                }

/* 286 */       public int hashCode() {
/* 287 */           int result = (int) (this.conversationId ^ (this.conversationId >>> 32));
/* 302 */           return (((((((((((((((((((((((((((((result * 31) + ((int) (this.messageId ^ (this.messageId >>> 32)))) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + ((int) (this.created ^ (this.created >>> 32)))) * 31) + this.networkType) * 31) + (this.message != null ? this.message.hashCode() : 0)) * 31) + (this.videoUrl != null ? this.videoUrl.hashCode() : 0)) * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.thumbnailUrl != null ? this.thumbnailUrl.hashCode() : 0)) * 31) + (this.isLast ? 1 : 0)) * 31) + (this.vanished ? 1 : 0)) * 31) + this.maxLoops) * 31) + ((int) (this.vanishedDate ^ (this.vanishedDate >>> 32)))) * 31) + ((int) (this.localStartTime ^ (this.localStartTime >>> 32)))) * 31) + ((int) (this.postId ^ (this.postId >>> 32)))) * 31) + (this.errorCode ^ (this.errorCode >>> 32));
                }

                /* JADX INFO: renamed from: co.vine.android.api.VinePrivateMessage$1 */
/* 305 */       static class C03511 implements Parcelable.Creator<VinePrivateMessage> {
/* 306 */           C03511() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 309 */           public VinePrivateMessage createFromParcel(Parcel in) {
/* 310 */               return new VinePrivateMessage(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 314 */           public VinePrivateMessage[] newArray(int size) {
/* 315 */               return new VinePrivateMessage[size];
                    }
                }
            }
