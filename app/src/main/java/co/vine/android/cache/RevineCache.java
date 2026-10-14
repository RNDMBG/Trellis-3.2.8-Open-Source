            package co.vine.android.cache;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class RevineCache extends ActionCache implements Parcelable {
                public static final Parcelable.Creator<RevineCache> CREATOR = new C03671();
                HashMap<Long, Long> mRepostIdMap;

/* 14 */        public RevineCache() {
/* 16 */            this.mRepostIdMap = new HashMap<>();
                }

/* 20 */        public RevineCache(Parcel in) {
/* 21 */            super(in);
/* 22 */            this.mRepostIdMap = (HashMap) in.readSerializable();
                }

/* 25 */        public void revine(long id) {
/* 26 */            this.mCache.put(Long.valueOf(id), true);
                }

/* 29 */        public void unRevine(long id) {
/* 30 */            this.mCache.put(Long.valueOf(id), false);
                }

/* 33 */        public boolean isRevined(long id) {
/* 34 */            Boolean revined = this.mCache.get(Long.valueOf(id));
/* 35 */            if (revined == null) {
/* 36 */                return false;
                    }
                    return revined.booleanValue();
                }

/* 42 */        public void putMyRepostId(long postId, long myRepostId) {
/* 43 */            this.mRepostIdMap.put(Long.valueOf(postId), Long.valueOf(myRepostId));
                }

/* 46 */        public void removeMyRepostId(long postId) {
/* 47 */            this.mRepostIdMap.remove(Long.valueOf(postId));
                }

/* 50 */        public long getMyRepostId(long postId) {
/* 51 */            if (this.mRepostIdMap.containsKey(Long.valueOf(postId))) {
/* 52 */                return this.mRepostIdMap.get(Long.valueOf(postId)).longValue();
                    }
                    return 0L;
                }

                @Override // co.vine.android.cache.ActionCache, android.os.Parcelable
/* 59 */        public int describeContents() {
/* 60 */            return 0;
                }

                @Override // co.vine.android.cache.ActionCache, android.os.Parcelable
/* 64 */        public void writeToParcel(Parcel out, int flags) {
/* 65 */            super.writeToParcel(out, flags);
/* 66 */            out.writeSerializable(this.mRepostIdMap);
                }

                /* JADX INFO: renamed from: co.vine.android.cache.RevineCache$1 */
/* 67 */        static class C03671 implements Parcelable.Creator<RevineCache> {
/* 68 */            C03671() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 71 */            public RevineCache createFromParcel(Parcel in) {
/* 72 */                return new RevineCache(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 76 */            public RevineCache[] newArray(int size) {
/* 77 */                return new RevineCache[size];
                    }
                }
            }
