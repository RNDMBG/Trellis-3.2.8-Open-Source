            package co.vine.android.cache;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class ActionCache implements Parcelable {
                public static final Parcelable.Creator<ActionCache> CREATOR = new C03651();
                protected final HashMap<Long, Boolean> mCache;

/* 14 */        public ActionCache() {
/* 16 */            this.mCache = new HashMap<>();
                }

/* 19 */        public ActionCache(Parcel in) {
/* 21 */            this.mCache = (HashMap) in.readSerializable();
                }

                @Override // android.os.Parcelable
/* 25 */        public int describeContents() {
/* 26 */            return 0;
                }

                @Override // android.os.Parcelable
/* 30 */        public void writeToParcel(Parcel out, int flags) {
/* 31 */            out.writeSerializable(this.mCache);
                }

                /* JADX INFO: renamed from: co.vine.android.cache.ActionCache$1 */
/* 32 */        static class C03651 implements Parcelable.Creator<ActionCache> {
/* 33 */            C03651() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 36 */            public ActionCache createFromParcel(Parcel in) {
/* 37 */                return new ActionCache(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 41 */            public ActionCache[] newArray(int size) {
/* 42 */                return new ActionCache[size];
                    }
                }

/* 46 */        public void removeEntry(long id) {
/* 47 */            this.mCache.remove(Long.valueOf(id));
                }

/* 50 */        public boolean contains(long id) {
/* 51 */            return this.mCache.containsKey(Long.valueOf(id));
                }

/* 54 */        public void clear() {
/* 55 */            this.mCache.clear();
                }
            }
