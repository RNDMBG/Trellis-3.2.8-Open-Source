            package co.vine.android.cache;

            import com.edisonwang.android.slog.MessageFormatter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class CacheKey {
                private int mCacheLoadTimeMs;
                private CacheState mCacheState = CacheState.UNKNOWN;
                private boolean mDownloadOnly;
                private boolean mLastLoadFromMemory;
                private long mLoadStartTime;

                public enum CacheState {
                    UNKNOWN,
                    NOT_CACHED,
                    DISK,
                    NETWORK,
                    EXPIRED
                }

/* 28 */        public CacheState getCacheState() {
/* 29 */            return this.mCacheState;
                }

/* 32 */        public void setCacheState(CacheState cacheState) {
/* 33 */            this.mCacheState = cacheState;
                }

/* 36 */        public int getCacheLoadTimeMs() {
/* 37 */            return this.mCacheLoadTimeMs;
                }

/* 40 */        public void setLoadTime(int timeMs) {
/* 41 */            this.mCacheLoadTimeMs = timeMs;
                }

/* 44 */        public long getLoadStartTime() {
/* 45 */            return this.mLoadStartTime;
                }

/* 48 */        public void setLoadStartTime(long startTime) {
/* 49 */            this.mLoadStartTime = startTime;
                }

/* 52 */        public boolean wasLastLoadFromMemory() {
/* 53 */            return this.mLastLoadFromMemory;
                }

/* 56 */        public void setLoadedFromMemory() {
/* 57 */            this.mLastLoadFromMemory = true;
                }

/* 60 */        public boolean isDownloadOnly() {
/* 61 */            return this.mDownloadOnly;
                }

/* 64 */        public void setDownloadOnly(boolean downloadOnly) {
/* 65 */            this.mDownloadOnly = downloadOnly;
                }

/* 69 */        public String toString() {
/* 70 */            return MessageFormatter.toStringMessage("Cache State: {}, Load took {}ms, loaded {}s ago, memory: {}", this.mCacheState.name(), Integer.valueOf(this.mCacheLoadTimeMs), Long.valueOf((System.currentTimeMillis() - this.mLoadStartTime) / 1000), Boolean.valueOf(this.mLastLoadFromMemory));
                }
            }
