            package co.vine.android;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class Friendships implements Parcelable {
                public static final Parcelable.Creator<Friendships> CREATOR = new C02151();
                public static final int FRIEND_FOLLOWING = 1;
                public static final int FRIEND_NONE = 0;
                public static final int FRIEND_UNDEFINED = -1;
                private HashMap<Long, Integer> mCache;

/* 19 */        public Friendships() {
/* 21 */            this.mCache = new HashMap<>();
                }

/* 24 */        public Friendships(Parcel in) {
/* 26 */            this.mCache = (HashMap) in.readSerializable();
                }

/* 29 */        public boolean isEmpty() {
/* 30 */            return this.mCache.isEmpty();
                }

/* 33 */        public int size() {
/* 34 */            return this.mCache.size();
                }

/* 37 */        public void clear() {
/* 38 */            this.mCache.clear();
                }

/* 41 */        public boolean contains(long userId) {
/* 42 */            return this.mCache.containsKey(Long.valueOf(userId));
                }

/* 45 */        public boolean contains(long userId, int friendship) {
/* 46 */            Integer state = this.mCache.get(Long.valueOf(userId));
                    return state != null && state.intValue() == friendship;
                }

/* 50 */        public void addFollowing(long userId) {
/* 51 */            Integer state = this.mCache.get(Long.valueOf(userId));
/* 52 */            if (state == null) {
/* 53 */                this.mCache.put(Long.valueOf(userId), Integer.valueOf(setFriendship(0, 1)));
                    } else {
/* 55 */                this.mCache.put(Long.valueOf(userId), Integer.valueOf(setFriendship(state.intValue(), 1)));
                    }
                }

/* 59 */        public void removeFollowing(long userId) {
/* 60 */            Integer state = this.mCache.get(Long.valueOf(userId));
/* 61 */            if (state == null) {
/* 62 */                this.mCache.put(Long.valueOf(userId), Integer.valueOf(unsetFriendship(0, 1)));
                    } else {
/* 64 */                this.mCache.put(Long.valueOf(userId), Integer.valueOf(unsetFriendship(state.intValue(), 1)));
                    }
                }

/* 68 */        public boolean isFollowing(long userId) {
/* 69 */            Integer state = this.mCache.get(Long.valueOf(userId));
                    return (state == null || (state.intValue() & 1) == 0) ? false : true;
                }

/* 74 */        public static int setFriendship(int state, int friendship) {
/* 75 */            return state | friendship;
                }

/* 78 */        public static int unsetFriendship(int state, int friendship) {
/* 79 */            return (friendship ^ (-1)) & state;
                }

                @Override // android.os.Parcelable
/* 83 */        public int describeContents() {
/* 84 */            return 0;
                }

                @Override // android.os.Parcelable
/* 88 */        public void writeToParcel(Parcel out, int flags) {
/* 89 */            out.writeSerializable(this.mCache);
                }

                /* JADX INFO: renamed from: co.vine.android.Friendships$1 */
/* 90 */        static class C02151 implements Parcelable.Creator<Friendships> {
/* 91 */            C02151() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 95 */            public Friendships createFromParcel(Parcel in) {
/* 96 */                return new Friendships(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 100 */           public Friendships[] newArray(int size) {
/* 101 */               return new Friendships[size];
                    }
                }
            }
