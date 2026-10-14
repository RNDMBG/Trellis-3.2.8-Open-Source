            package co.vine.android.network;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 7 */     public class UrlCachePolicy implements Parcelable {
                public final boolean mCacheTakesPriority;
                public final boolean mCachedResponseAllowed;
                public final long mIfReachableFetchMaxStaleTimeMs;
                public final boolean mNetworkDataAllowed;
                public final boolean mShouldCacheResponse;
                public final boolean mUseExpiredDataAllowedIfNetworkIsDown;
                public static final UrlCachePolicy CACHE_THEN_NETWORK = new UrlCachePolicy(true, true, true, true, -1, true);
                public static final UrlCachePolicy NETWORK_THEN_CACHE = new UrlCachePolicy(true, true, false, true, -1, true);
                public static final UrlCachePolicy NOT_CACHABLE = new UrlCachePolicy(false, false, false, false, -1, true);
                public static final UrlCachePolicy NETWORK_ONLY = new UrlCachePolicy(true, false, false, false, -1, true);
                public static final UrlCachePolicy FORCE_REFRESH = new UrlCachePolicy(true, true, false, false, -1, true);
                public static final UrlCachePolicy CACHE_ONLY = new UrlCachePolicy(true, true, true, true, -1, false);
                public static final Parcelable.Creator<UrlCachePolicy> CREATOR = new C03881();

/* 8 */         /* synthetic */ UrlCachePolicy(Parcel x0, C03881 x1) {
/* 9 */             this(x0);
                }

/* 22 */        private UrlCachePolicy(Parcel in) {
/* 24 */            this.mShouldCacheResponse = in.readInt() != 0;
/* 25 */            this.mCacheTakesPriority = in.readInt() != 0;
/* 26 */            this.mCachedResponseAllowed = in.readInt() != 0;
/* 27 */            this.mUseExpiredDataAllowedIfNetworkIsDown = in.readInt() != 0;
/* 28 */            this.mNetworkDataAllowed = in.readInt() != 0;
/* 29 */            this.mIfReachableFetchMaxStaleTimeMs = in.readLong();
                }

/* 44 */        private UrlCachePolicy(boolean shouldCacheResponse, boolean cachedResponseAllowed, boolean cacheTakesPriority, boolean useExpiredDataAllowedIfNetworkIsDown, long ifReachableFetchMaxStaleTimeMs, boolean networkDataAllowed) {
/* 46 */            if (cacheTakesPriority && !cachedResponseAllowed) {
/* 47 */                throw new IllegalArgumentException("Cache can't take priority if cache reponses are not allowed.");
                    }
/* 49 */            this.mNetworkDataAllowed = networkDataAllowed;
/* 50 */            this.mShouldCacheResponse = shouldCacheResponse;
/* 51 */            this.mCachedResponseAllowed = cachedResponseAllowed;
/* 52 */            this.mUseExpiredDataAllowedIfNetworkIsDown = useExpiredDataAllowedIfNetworkIsDown;
/* 53 */            this.mIfReachableFetchMaxStaleTimeMs = ifReachableFetchMaxStaleTimeMs;
/* 54 */            this.mCacheTakesPriority = cacheTakesPriority;
                }

/* 103 */       public static UrlCachePolicy cacheAllowedPolicy(boolean expiredDataAllowedIfNetworkIsDown, long ifReachableFetchMaxStaleTimeMs) {
/* 104 */           return new UrlCachePolicy(true, true, true, expiredDataAllowedIfNetworkIsDown, ifReachableFetchMaxStaleTimeMs, true);
                }

                @Override // android.os.Parcelable
/* 108 */       public int describeContents() {
/* 109 */           return 0;
                }

                @Override // android.os.Parcelable
/* 113 */       public void writeToParcel(Parcel dest, int flags) {
/* 114 */           dest.writeInt(this.mShouldCacheResponse ? 1 : 0);
/* 115 */           dest.writeInt(this.mCacheTakesPriority ? 1 : 0);
/* 116 */           dest.writeInt(this.mCachedResponseAllowed ? 1 : 0);
/* 117 */           dest.writeInt(this.mUseExpiredDataAllowedIfNetworkIsDown ? 1 : 0);
/* 118 */           dest.writeInt(this.mNetworkDataAllowed ? 1 : 0);
/* 119 */           dest.writeLong(this.mIfReachableFetchMaxStaleTimeMs);
                }

/* 123 */       public boolean equals(Object o) {
/* 124 */           if (!(o instanceof UrlCachePolicy)) {
                        return false;
                    }
/* 127 */           UrlCachePolicy other = (UrlCachePolicy) o;
                    return this.mShouldCacheResponse == other.mShouldCacheResponse && this.mCacheTakesPriority == other.mCacheTakesPriority && this.mIfReachableFetchMaxStaleTimeMs == other.mIfReachableFetchMaxStaleTimeMs && this.mNetworkDataAllowed == other.mNetworkDataAllowed && this.mCachedResponseAllowed == other.mCachedResponseAllowed && this.mUseExpiredDataAllowedIfNetworkIsDown == other.mUseExpiredDataAllowedIfNetworkIsDown;
                }

/* 137 */       public int hashCode() {
/* 138 */           int r = (this.mShouldCacheResponse ? 1 : 0) * 31;
                    int r2 = r + ((this.mCacheTakesPriority ? 1 : 0) * 31) + r;
                    int r3 = r2 + ((this.mNetworkDataAllowed ? 1 : 0) * 31) + r2;
                    int r4 = r3 + ((this.mCachedResponseAllowed ? 1 : 0) * 31) + r3;
                    int r5 = r4 + ((this.mUseExpiredDataAllowedIfNetworkIsDown ? 1 : 0) * 31) + r4;
/* 143 */           return r5 + (((int) ((this.mIfReachableFetchMaxStaleTimeMs >> 32) ^ this.mIfReachableFetchMaxStaleTimeMs)) * 31) + r5;
                }

                /* JADX INFO: renamed from: co.vine.android.network.UrlCachePolicy$1 */
/* 145 */       static class C03881 implements Parcelable.Creator<UrlCachePolicy> {
/* 146 */           C03881() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 148 */           public UrlCachePolicy createFromParcel(Parcel in) {
/* 149 */               return new UrlCachePolicy(in, null);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 152 */           public UrlCachePolicy[] newArray(int size) {
/* 153 */               return new UrlCachePolicy[size];
                    }
                }
            }
