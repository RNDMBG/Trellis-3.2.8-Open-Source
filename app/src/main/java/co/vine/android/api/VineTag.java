            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.io.Serializable;
            
            
    public class VineTag implements Parcelable, Serializable {
                public static final Parcelable.Creator<VineTag> CREATOR = new C03591();
                public long tagId;
                public String tagName;

        public VineTag() {
                }

        public VineTag(String tagName, long tagId) {
            this.tagName = tagName;
            this.tagId = tagId;
                }

        public VineTag(Parcel in) {
            this.tagName = in.readString();
            this.tagId = in.readLong();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.tagName);
            dest.writeLong(this.tagId);
                }

                
        static class C03591 implements Parcelable.Creator<VineTag> {
            C03591() {
                    }



                    
                    @Override 
            public VineTag createFromParcel(Parcel in) {
                return new VineTag(in);
                    }

                    
                    @Override 
            public VineTag[] newArray(int size) {
                return new VineTag[size];
                    }
                }
            }
