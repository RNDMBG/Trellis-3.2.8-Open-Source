            package co.vine.android.cache;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.HashMap;
            
            
    public class RevineCache extends ActionCache implements Parcelable {
                public static final Parcelable.Creator<RevineCache> CREATOR = new C03671();
                HashMap<Long, Long> mRepostIdMap;

        public RevineCache() {
            this.mRepostIdMap = new HashMap<>();
                }

        public RevineCache(Parcel in) {
            super(in);
            this.mRepostIdMap = (HashMap) in.readSerializable();
                }

        public void revine(long id) {
            this.mCache.put(Long.valueOf(id), true);
                }

        public void unRevine(long id) {
            this.mCache.put(Long.valueOf(id), false);
                }

        public boolean isRevined(long id) {
            Boolean revined = this.mCache.get(Long.valueOf(id));
            if (revined == null) {
                return false;
                    }
                    return revined.booleanValue();
                }

        public void putMyRepostId(long postId, long myRepostId) {
            this.mRepostIdMap.put(Long.valueOf(postId), Long.valueOf(myRepostId));
                }

        public void removeMyRepostId(long postId) {
            this.mRepostIdMap.remove(Long.valueOf(postId));
                }

        public long getMyRepostId(long postId) {
            if (this.mRepostIdMap.containsKey(Long.valueOf(postId))) {
                return this.mRepostIdMap.get(Long.valueOf(postId)).longValue();
                    }
                    return 0L;
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeSerializable(this.mRepostIdMap);
                }

                
        static class C03671 implements Parcelable.Creator<RevineCache> {
            C03671() {
                    }



                    
                    @Override 
            public RevineCache createFromParcel(Parcel in) {
                return new RevineCache(in);
                    }

                    
                    @Override 
            public RevineCache[] newArray(int size) {
                return new RevineCache[size];
                    }
                }
            }
