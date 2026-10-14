            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VineLoopSubmissionResponse implements Parcelable {
                public static final Parcelable.Creator<VineLoopSubmissionResponse> CREATOR = new C03311();
                public int submissionInterval;

        public VineLoopSubmissionResponse(int submissionInterval) {
            this.submissionInterval = submissionInterval;
                }

        public VineLoopSubmissionResponse(Parcel in) {
            this.submissionInterval = in.readInt();
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.submissionInterval);
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                
        static class C03311 implements Parcelable.Creator<VineLoopSubmissionResponse> {
            C03311() {
                    }



                    
                    @Override 
            public VineLoopSubmissionResponse createFromParcel(Parcel in) {
                return new VineLoopSubmissionResponse(in);
                    }

                    
                    @Override 
            public VineLoopSubmissionResponse[] newArray(int size) {
                return new VineLoopSubmissionResponse[size];
                    }
                }
            }
