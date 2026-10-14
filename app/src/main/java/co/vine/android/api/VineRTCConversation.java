            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            
    public class VineRTCConversation implements Parcelable {
                public static final Parcelable.Creator<VineRTCConversation> CREATOR = new C03541();
                public long conversationId;
                public ArrayList<VineRTCParticipant> participants;

        public VineRTCConversation(long conversationId, ArrayList<VineRTCParticipant> participants) {
            this.participants = new ArrayList<>();
            this.conversationId = conversationId;
            this.participants = participants;
                }

        public VineRTCConversation(Parcel in) {
            this.participants = new ArrayList<>();
            this.conversationId = in.readLong();
            in.readTypedList(this.participants, VineRTCParticipant.CREATOR);
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.conversationId);
            dest.writeTypedList(this.participants);
                }

                
        static class C03541 implements Parcelable.Creator<VineRTCConversation> {
            C03541() {
                    }



                    
                    @Override 
            public VineRTCConversation createFromParcel(Parcel in) {
                return new VineRTCConversation(in);
                    }

                    
                    @Override 
            public VineRTCConversation[] newArray(int size) {
                return new VineRTCConversation[size];
                    }
                }
            }
