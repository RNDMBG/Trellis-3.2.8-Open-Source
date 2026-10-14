            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            
    public class VineInbox implements Parcelable {
                public static final Parcelable.Creator<VineInbox> CREATOR = new C03281();
                public ArrayList<VineConversation> conversations;
                public ArrayList<VineUser> participants;

        public VineInbox() {
                }

        public VineInbox(ArrayList<VineConversation> conversations, ArrayList<VineUser> participants) {
            this.conversations = conversations;
            this.participants = participants;
                }

        public VineInbox(Parcel in) {
            ClassLoader cl = getClass().getClassLoader();
            this.conversations = in.readArrayList(cl);
            this.participants = in.readArrayList(cl);
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeSerializable(this.conversations);
            out.writeSerializable(this.participants);
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                
        static class C03281 implements Parcelable.Creator<VineInbox> {
            C03281() {
                    }



                    
                    @Override 
            public VineInbox createFromParcel(Parcel in) {
                return new VineInbox(in);
                    }

                    
                    @Override 
            public VineInbox[] newArray(int size) {
                return new VineInbox[size];
                    }
                }

        public boolean equals(Object o) {
            if (this == o) {
                        return true;
                    }
            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
            VineInbox that = (VineInbox) o;
            if (this.conversations == null ? that.conversations != null : !this.conversations.equals(that.conversations)) {
                        return false;
                    }
            if (this.participants != null) {
                        if (this.participants.equals(that.participants)) {
                            return true;
                        }
                    } else if (that.participants == null) {
                        return true;
                    }
                    return false;
                }

        public int hashCode() {
            int result = this.conversations != null ? this.conversations.hashCode() : 0;
            return (result * 31) + (this.participants != null ? this.participants.hashCode() : 0);
                }
            }
