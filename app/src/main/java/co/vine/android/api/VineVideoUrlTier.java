            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VineVideoUrlTier implements Parcelable {
                public static final Parcelable.Creator<VineVideoUrlTier> CREATOR = new C03641();
                public static final String FORMAT_DASH = "dash";
                public static final String FORMAT_H264 = "h264";
                public static final String FORMAT_H264C = "h264c";
                public static final String FORMAT_WEBM = "webm";
                public final String format;
                public final float rate;
                public final String url;

        public VineVideoUrlTier(String url, float rate, String format) {
            this.url = url;
            this.rate = rate;
            this.format = format;
                }

        public VineVideoUrlTier(Parcel in) {
            this.url = in.readString();
            this.rate = in.readFloat();
            this.format = in.readString();
                }

                
        static class C03641 implements Parcelable.Creator<VineVideoUrlTier> {
            C03641() {
                    }



                    
                    @Override 
            public VineVideoUrlTier createFromParcel(Parcel in) {
                return new VineVideoUrlTier(in);
                    }

                    
                    @Override 
            public VineVideoUrlTier[] newArray(int size) {
                return new VineVideoUrlTier[size];
                    }
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.url);
            out.writeFloat(this.rate);
            out.writeString(this.format);
                }
            }
