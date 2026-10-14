            package co.vine.android.cache;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class LikeCache extends ActionCache implements Parcelable {
                public static final Parcelable.Creator<LikeCache> CREATOR = new C03661();

/* 11 */        public LikeCache() {
                }

/* 16 */        public LikeCache(Parcel in) {
/* 17 */            super(in);
                }

/* 20 */        public void like(long id) {
/* 21 */            this.mCache.put(Long.valueOf(id), true);
                }

/* 24 */        public void unlike(long id) {
/* 25 */            this.mCache.put(Long.valueOf(id), false);
                }

/* 28 */        public boolean isLike(long id) {
/* 29 */            Boolean liked = this.mCache.get(Long.valueOf(id));
                    return liked != null && liked.booleanValue();
                }

                @Override // co.vine.android.cache.ActionCache, android.os.Parcelable
/* 34 */        public int describeContents() {
/* 35 */            return 0;
                }

                @Override // co.vine.android.cache.ActionCache, android.os.Parcelable
/* 39 */        public void writeToParcel(Parcel out, int flags) {
/* 40 */            super.writeToParcel(out, flags);
                }

                /* JADX INFO: renamed from: co.vine.android.cache.LikeCache$1 */
/* 41 */        static class C03661 implements Parcelable.Creator<LikeCache> {
/* 42 */            C03661() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 45 */            public LikeCache createFromParcel(Parcel in) {
/* 46 */                return new LikeCache(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 50 */            public LikeCache[] newArray(int size) {
/* 51 */                return new LikeCache[size];
                    }
                }
            }
