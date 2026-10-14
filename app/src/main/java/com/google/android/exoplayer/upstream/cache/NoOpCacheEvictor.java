            package com.google.android.exoplayer.upstream.cache;
            
            
    public class NoOpCacheEvictor implements CacheEvictor {
                @Override 
        public void onStartFile(Cache cache, String key, long position, long length) {
                }

                @Override 
        public void onSpanAdded(Cache cache, CacheSpan span) {
                }

                @Override 
        public void onSpanRemoved(Cache cache, CacheSpan span) {
                }

                @Override 
        public void onSpanTouched(Cache cache, CacheSpan oldSpan, CacheSpan newSpan) {
                }
            }
