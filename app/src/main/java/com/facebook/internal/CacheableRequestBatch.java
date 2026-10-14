            package com.facebook.internal;

            import com.facebook.Request;
            import com.facebook.RequestBatch;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class CacheableRequestBatch extends RequestBatch {
                private String cacheKey;
                private boolean forceRoundTrip;

/* 30 */        public CacheableRequestBatch() {
                }

/* 34 */        public CacheableRequestBatch(Request... requests) {
/* 35 */            super(requests);
                }

/* 38 */        public final String getCacheKeyOverride() {
/* 39 */            return this.cacheKey;
                }

/* 45 */        public final void setCacheKeyOverride(String cacheKey) {
/* 46 */            this.cacheKey = cacheKey;
                }

/* 49 */        public final boolean getForceRoundTrip() {
/* 50 */            return this.forceRoundTrip;
                }

/* 53 */        public final void setForceRoundTrip(boolean forceRoundTrip) {
/* 54 */            this.forceRoundTrip = forceRoundTrip;
                }
            }
