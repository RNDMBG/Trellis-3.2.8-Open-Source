            package com.google.android.exoplayer.upstream.cache;

            import java.util.Comparator;
            import java.util.TreeSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class LeastRecentlyUsedCacheEvictor implements CacheEvictor, Comparator<CacheSpan> {
                private long currentSize;
                private final TreeSet<CacheSpan> leastRecentlyUsed = new TreeSet<>(this);
                private final long maxBytes;


/* 30 */        public LeastRecentlyUsedCacheEvictor(long maxBytes) {
/* 32 */            this.maxBytes = maxBytes;
                }

                @Override // com.google.android.exoplayer.upstream.cache.CacheEvictor
/* 37 */        public void onStartFile(Cache cache, String key, long position, long length) {
/* 38 */            evictCache(cache, length);
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache.Listener
/* 42 */        public void onSpanAdded(Cache cache, CacheSpan span) {
/* 43 */            this.leastRecentlyUsed.add(span);
/* 44 */            this.currentSize += span.length;
/* 45 */            evictCache(cache, 0L);
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache.Listener
/* 49 */        public void onSpanRemoved(Cache cache, CacheSpan span) {
/* 50 */            this.leastRecentlyUsed.remove(span);
/* 51 */            this.currentSize -= span.length;
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache.Listener
/* 55 */        public void onSpanTouched(Cache cache, CacheSpan oldSpan, CacheSpan newSpan) {
/* 56 */            onSpanRemoved(cache, oldSpan);
/* 57 */            onSpanAdded(cache, newSpan);
                }

                /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 61 */        public int compare(CacheSpan lhs, CacheSpan rhs) {
/* 62 */            long lastAccessTimestampDelta = lhs.lastAccessTimestamp - rhs.lastAccessTimestamp;
                    if (lastAccessTimestampDelta == 0) {
/* 65 */                return lhs.compareTo(rhs);
                    }
                    return lhs.lastAccessTimestamp < rhs.lastAccessTimestamp ? -1 : 1;
                }

/* 70 */        private void evictCache(Cache cache, long requiredSpace) {
                    while (this.currentSize + requiredSpace > this.maxBytes) {
/* 72 */                cache.removeSpan(this.leastRecentlyUsed.first());
                    }
                }
            }
