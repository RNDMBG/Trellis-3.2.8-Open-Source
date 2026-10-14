            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class VineRTCParticipant implements Parcelable {
                public static final Parcelable.Creator<VineRTCParticipant> CREATOR = new C03551();
                public boolean isConnected;
                public boolean isTyping;
                public long lastMessageId;
                public long userId;

/* 14 */        public VineRTCParticipant(long userId, boolean connected, boolean typing, long lastMessageId) {
/* 16 */            this.userId = userId;
/* 17 */            this.lastMessageId = lastMessageId;
/* 18 */            this.isConnected = connected;
/* 19 */            this.isTyping = typing;
                }

/* 21 */        public VineRTCParticipant(Parcel in) {
/* 23 */            this.userId = in.readLong();
/* 24 */            this.lastMessageId = in.readLong();
/* 25 */            this.isConnected = in.readInt() == 1;
/* 26 */            this.isTyping = in.readInt() == 1;
                }

                @Override // android.os.Parcelable
/* 30 */        public int describeContents() {
/* 31 */            return 0;
                }

                @Override // android.os.Parcelable
/* 35 */        public void writeToParcel(Parcel dest, int flags) {
/* 36 */            dest.writeLong(this.userId);
/* 37 */            dest.writeLong(this.lastMessageId);
/* 38 */            dest.writeInt(this.isConnected ? 1 : 0);
/* 39 */            dest.writeInt(this.isTyping ? 1 : 0);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineRTCParticipant$1 */
/* 40 */        static class C03551 implements Parcelable.Creator<VineRTCParticipant> {
/* 41 */            C03551() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 43 */            public VineRTCParticipant createFromParcel(Parcel in) {
/* 44 */                return new VineRTCParticipant(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 47 */            public VineRTCParticipant[] newArray(int size) {
/* 48 */                return new VineRTCParticipant[size];
                    }
                }
            }
