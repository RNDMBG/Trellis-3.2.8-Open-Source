            package com.google.android.exoplayer.upstream.cache;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class NoOpCacheEvictor implements CacheEvictor {
                @Override // com.google.android.exoplayer.upstream.cache.CacheEvictor
/* 29 */        public void onStartFile(Cache cache, String key, long position, long length) {
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache.Listener
/* 34 */        public void onSpanAdded(Cache cache, CacheSpan span) {
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache.Listener
/* 39 */        public void onSpanRemoved(Cache cache, CacheSpan span) {
                }

                @Override // com.google.android.exoplayer.upstream.cache.Cache.Listener
/* 44 */        public void onSpanTouched(Cache cache, CacheSpan oldSpan, CacheSpan newSpan) {
                }
            }
