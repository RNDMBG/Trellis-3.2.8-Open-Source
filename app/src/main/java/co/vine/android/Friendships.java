            package co.vine.android;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.HashMap;
            
            
    public class Friendships implements Parcelable {
                public static final Parcelable.Creator<Friendships> CREATOR = new C02151();
                public static final int FRIEND_FOLLOWING = 1;
                public static final int FRIEND_NONE = 0;
                public static final int FRIEND_UNDEFINED = -1;
                private HashMap<Long, Integer> mCache;

        public Friendships() {
            this.mCache = new HashMap<>();
                }

        public Friendships(Parcel in) {
            this.mCache = (HashMap) in.readSerializable();
                }

        public boolean isEmpty() {
            return this.mCache.isEmpty();
                }

        public int size() {
            return this.mCache.size();
                }

        public void clear() {
            this.mCache.clear();
                }

        public boolean contains(long userId) {
            return this.mCache.containsKey(Long.valueOf(userId));
                }

        public boolean contains(long userId, int friendship) {
            Integer state = this.mCache.get(Long.valueOf(userId));
                    return state != null && state.intValue() == friendship;
                }

        public void addFollowing(long userId) {
            Integer state = this.mCache.get(Long.valueOf(userId));
            if (state == null) {
                this.mCache.put(Long.valueOf(userId), Integer.valueOf(setFriendship(0, 1)));
                    } else {
                this.mCache.put(Long.valueOf(userId), Integer.valueOf(setFriendship(state.intValue(), 1)));
                    }
                }

        public void removeFollowing(long userId) {
            Integer state = this.mCache.get(Long.valueOf(userId));
            if (state == null) {
                this.mCache.put(Long.valueOf(userId), Integer.valueOf(unsetFriendship(0, 1)));
                    } else {
                this.mCache.put(Long.valueOf(userId), Integer.valueOf(unsetFriendship(state.intValue(), 1)));
                    }
                }

        public boolean isFollowing(long userId) {
            Integer state = this.mCache.get(Long.valueOf(userId));
                    return (state == null || (state.intValue() & 1) == 0) ? false : true;
                }

        public static int setFriendship(int state, int friendship) {
            return state | friendship;
                }

        public static int unsetFriendship(int state, int friendship) {
            return (friendship ^ (-1)) & state;
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeSerializable(this.mCache);
                }

                
        static class C02151 implements Parcelable.Creator<Friendships> {
            C02151() {
                    }



                    
                    @Override 
            public Friendships createFromParcel(Parcel in) {
                return new Friendships(in);
                    }

                    
                    @Override 
           public Friendships[] newArray(int size) {
               return new Friendships[size];
                    }
                }
            }
