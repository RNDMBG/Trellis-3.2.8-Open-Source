            package co.vine.android.cache;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.HashMap;
            
            
    public class ActionCache implements Parcelable {
                public static final Parcelable.Creator<ActionCache> CREATOR = new C03651();
                protected final HashMap<Long, Boolean> mCache;

        public ActionCache() {
            this.mCache = new HashMap<>();
                }

        public ActionCache(Parcel in) {
            this.mCache = (HashMap) in.readSerializable();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeSerializable(this.mCache);
                }

                
        static class C03651 implements Parcelable.Creator<ActionCache> {
            C03651() {
                    }



                    
                    @Override 
            public ActionCache createFromParcel(Parcel in) {
                return new ActionCache(in);
                    }

                    
                    @Override 
            public ActionCache[] newArray(int size) {
                return new ActionCache[size];
                    }
                }

        public void removeEntry(long id) {
            this.mCache.remove(Long.valueOf(id));
                }

        public boolean contains(long id) {
            return this.mCache.containsKey(Long.valueOf(id));
                }

        public void clear() {
            this.mCache.clear();
                }
            }
