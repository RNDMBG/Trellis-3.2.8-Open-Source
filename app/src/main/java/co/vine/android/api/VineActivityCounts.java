            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VineActivityCounts implements Parcelable {
                public static final Parcelable.Creator<VineActivityCounts> CREATOR = new C03181();
                public int messages;
                public int notifications;

        public VineActivityCounts(int messages, int notifications) {
            this.messages = messages;
            this.notifications = notifications;
                }

        public VineActivityCounts(Parcel in) {
            this.messages = in.readInt();
            this.notifications = in.readInt();
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.messages);
            out.writeInt(this.notifications);
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                
        static class C03181 implements Parcelable.Creator<VineActivityCounts> {
            C03181() {
                    }



                    
                    @Override 
            public VineActivityCounts createFromParcel(Parcel in) {
                return new VineActivityCounts(in);
                    }

                    
                    @Override 
            public VineActivityCounts[] newArray(int size) {
                return new VineActivityCounts[size];
                    }
                }
            }
