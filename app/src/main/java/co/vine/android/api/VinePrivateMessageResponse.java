            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class VinePrivateMessageResponse implements Parcelable {
                public static final Parcelable.Creator<VinePrivateMessageResponse> CREATOR = new C03531();
                public final long conversationId;
                public final VineError error;
                public final long messageId;
                public final int networkType;
                public final VineRecipient recipient;
                public final String shareUrl;
                public final String thumbnailUrl;
                public final String videoUrl;

/* 19 */        public VinePrivateMessageResponse(VineRecipient recipient, long conversationId, long messageId, String videoUrl, String thumbnailUrl, String shareUrl, VineError error, int networkType) {
/* 21 */            this.recipient = recipient;
/* 22 */            this.conversationId = conversationId;
/* 23 */            this.messageId = messageId;
/* 24 */            this.videoUrl = videoUrl;
/* 25 */            this.thumbnailUrl = thumbnailUrl;
/* 26 */            this.shareUrl = shareUrl;
/* 27 */            this.error = error;
/* 28 */            this.networkType = networkType;
                }

/* 31 */        public VinePrivateMessageResponse(Parcel in) {
/* 33 */            this.recipient = (VineRecipient) in.readParcelable(VineRecipient.class.getClassLoader());
/* 34 */            this.conversationId = in.readLong();
/* 35 */            this.messageId = in.readLong();
/* 36 */            this.videoUrl = in.readString();
/* 37 */            this.thumbnailUrl = in.readString();
/* 38 */            this.shareUrl = in.readString();
/* 39 */            this.error = (VineError) in.readParcelable(VineError.class.getClassLoader());
/* 40 */            this.networkType = in.readInt();
                }

                @Override // android.os.Parcelable
/* 44 */        public int describeContents() {
/* 45 */            return 0;
                }

                @Override // android.os.Parcelable
/* 49 */        public void writeToParcel(Parcel out, int flags) {
/* 50 */            out.writeParcelable(this.recipient, flags);
/* 51 */            out.writeLong(this.conversationId);
/* 52 */            out.writeLong(this.messageId);
/* 53 */            out.writeString(this.videoUrl);
/* 54 */            out.writeString(this.thumbnailUrl);
/* 55 */            out.writeString(this.shareUrl);
/* 56 */            out.writeParcelable(this.error, flags);
/* 57 */            out.writeInt(this.networkType);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VinePrivateMessageResponse$1 */
/* 58 */        static class C03531 implements Parcelable.Creator<VinePrivateMessageResponse> {
/* 59 */            C03531() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 61 */            public VinePrivateMessageResponse createFromParcel(Parcel in) {
/* 62 */                return new VinePrivateMessageResponse(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 65 */            public VinePrivateMessageResponse[] newArray(int size) {
/* 66 */                return new VinePrivateMessageResponse[size];
                    }
                }
            }
