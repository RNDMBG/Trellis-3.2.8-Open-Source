            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VineTypeAhead implements Parcelable {
                public static final Parcelable.Creator<VineTypeAhead> CREATOR = new C03601();

                
                public long f97id;
                public final String token;
                public final String type;

        public VineTypeAhead(String type, String token, long id) {
            this.f97id = id;
            this.token = token;
            this.type = type;
                }

        public static String getPlainTag(String tagWithHash) {
            return tagWithHash.substring(1, tagWithHash.length());
                }

        public VineTypeAhead(Parcel in) {
            this.f97id = in.readLong();
            this.token = in.readString();
            this.type = in.readString();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeLong(this.f97id);
            out.writeString(this.token);
            out.writeString(this.type);
                }

                
        static class C03601 implements Parcelable.Creator<VineTypeAhead> {
            C03601() {
                    }



                    
                    @Override 
            public VineTypeAhead createFromParcel(Parcel in) {
                return new VineTypeAhead(in);
                    }

                    
                    @Override 
            public VineTypeAhead[] newArray(int size) {
                return new VineTypeAhead[size];
                    }
                }
            }
