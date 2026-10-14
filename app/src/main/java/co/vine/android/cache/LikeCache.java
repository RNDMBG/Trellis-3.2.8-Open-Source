            package co.vine.android.cache;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class LikeCache extends ActionCache implements Parcelable {
                public static final Parcelable.Creator<LikeCache> CREATOR = new C03661();

        public LikeCache() {
                }

        public LikeCache(Parcel in) {
            super(in);
                }

        public void like(long id) {
            this.mCache.put(Long.valueOf(id), true);
                }

        public void unlike(long id) {
            this.mCache.put(Long.valueOf(id), false);
                }

        public boolean isLike(long id) {
            Boolean liked = this.mCache.get(Long.valueOf(id));
                    return liked != null && liked.booleanValue();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
                }

                
        static class C03661 implements Parcelable.Creator<LikeCache> {
            C03661() {
                    }



                    
                    @Override 
            public LikeCache createFromParcel(Parcel in) {
                return new LikeCache(in);
                    }

                    
                    @Override 
            public LikeCache[] newArray(int size) {
                return new LikeCache[size];
                    }
                }
            }
