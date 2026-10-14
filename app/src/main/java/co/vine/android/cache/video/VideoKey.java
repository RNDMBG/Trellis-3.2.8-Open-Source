            package co.vine.android.cache.video;

            import android.os.Parcel;
            import android.os.Parcelable;
            import co.vine.android.cache.CacheKey;
            import org.apache.commons.io.IOUtils;
            
            
    public class VideoKey extends CacheKey implements Parcelable {
                public static final Parcelable.Creator<VideoKey> CREATOR = new C03691();
                public final String url;

        public VideoKey(String url) {
            this.url = url;
                }

        public boolean equals(Object o) {
            if (this == o) {
                        return true;
                    }
            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
            if (this.url != null) {
                return this.url.equals(((VideoKey) o).url);
                    }
                    return ((VideoKey) o).url == null;
                }

                @Override 
        public String toString() {
            return super.toString() + IOUtils.LINE_SEPARATOR_UNIX + this.url;
                }

        public int hashCode() {
            if (this.url != null) {
                        return this.url.hashCode() * 31;
                    }
                    return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.url);
                }

        public VideoKey(Parcel in) {
            this.url = in.readString();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                
        static class C03691 implements Parcelable.Creator<VideoKey> {
            C03691() {
                    }



                    
                    @Override 
            public VideoKey createFromParcel(Parcel in) {
                return new VideoKey(in);
                    }

                    
                    @Override 
            public VideoKey[] newArray(int size) {
                return new VideoKey[size];
                    }
                }
            }
