            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            
    public class VinePrivateMessagePostResponseWithUsers implements Parcelable {
                public static final Parcelable.Creator<VinePrivateMessagePostResponseWithUsers> CREATOR = new C03521();
                public final VinePost post;
                public final ArrayList<VineRecipient> recipients;
                public final ArrayList<VinePrivateMessageResponse> responses;
                public final ArrayList<VineUser> users;

        public VinePrivateMessagePostResponseWithUsers(ArrayList<VinePrivateMessageResponse> responses, ArrayList<VineRecipient> recipients, ArrayList<VineUser> users, VinePost post) {
            this.responses = responses;
            this.recipients = recipients;
            this.users = users;
            this.post = post;
                }

        public VinePrivateMessagePostResponseWithUsers(Parcel in) {
            this.responses = (ArrayList) in.readSerializable();
            this.recipients = (ArrayList) in.readSerializable();
            this.users = (ArrayList) in.readSerializable();
            this.post = (VinePost) in.readParcelable(VinePost.class.getClassLoader());
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeSerializable(this.responses);
            out.writeSerializable(this.recipients);
            out.writeSerializable(this.users);
            out.writeParcelable(this.post, flags);
                }

                
        static class C03521 implements Parcelable.Creator<VinePrivateMessagePostResponseWithUsers> {
            C03521() {
                    }



                    
                    @Override 
            public VinePrivateMessagePostResponseWithUsers createFromParcel(Parcel in) {
                return new VinePrivateMessagePostResponseWithUsers(in);
                    }

                    
                    @Override 
            public VinePrivateMessagePostResponseWithUsers[] newArray(int size) {
                return new VinePrivateMessagePostResponseWithUsers[size];
                    }
                }
            }
