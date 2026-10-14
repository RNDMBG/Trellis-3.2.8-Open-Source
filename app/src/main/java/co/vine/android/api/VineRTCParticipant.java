            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VineRTCParticipant implements Parcelable {
                public static final Parcelable.Creator<VineRTCParticipant> CREATOR = new C03551();
                public boolean isConnected;
                public boolean isTyping;
                public long lastMessageId;
                public long userId;

        public VineRTCParticipant(long userId, boolean connected, boolean typing, long lastMessageId) {
            this.userId = userId;
            this.lastMessageId = lastMessageId;
            this.isConnected = connected;
            this.isTyping = typing;
                }

        public VineRTCParticipant(Parcel in) {
            this.userId = in.readLong();
            this.lastMessageId = in.readLong();
            this.isConnected = in.readInt() == 1;
            this.isTyping = in.readInt() == 1;
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.userId);
            dest.writeLong(this.lastMessageId);
            dest.writeInt(this.isConnected ? 1 : 0);
            dest.writeInt(this.isTyping ? 1 : 0);
                }

                
        static class C03551 implements Parcelable.Creator<VineRTCParticipant> {
            C03551() {
                    }



                    
                    @Override 
            public VineRTCParticipant createFromParcel(Parcel in) {
                return new VineRTCParticipant(in);
                    }

                    
                    @Override 
            public VineRTCParticipant[] newArray(int size) {
                return new VineRTCParticipant[size];
                    }
                }
            }
