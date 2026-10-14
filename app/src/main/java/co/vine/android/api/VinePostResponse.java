            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VinePostResponse implements Parcelable {
                public static final Parcelable.Creator<VinePostResponse> CREATOR = new C03501();
                public final long createdAt;
                public final long postId;

        public VinePostResponse(long postId, long createdAt) {
            this.postId = postId;
            this.createdAt = createdAt;
                }

        public VinePostResponse(Parcel in) {
            this.postId = in.readLong();
            this.createdAt = in.readLong();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeLong(this.postId);
            out.writeLong(this.createdAt);
                }

                
        static class C03501 implements Parcelable.Creator<VinePostResponse> {
            C03501() {
                    }



                    
                    @Override 
            public VinePostResponse createFromParcel(Parcel in) {
                return new VinePostResponse(in);
                    }

                    
                    @Override 
            public VinePostResponse[] newArray(int size) {
                return new VinePostResponse[size];
                    }
                }
            }
