            package com.google.android.exoplayer.upstream.cache;

            import java.io.File;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public final class CacheSpan implements Comparable<CacheSpan> {
                private static final String SUFFIX = ".v1.exo";
                private static final String SUFFIX_ESCAPED = "\\.v1\\.exo";
                private static final Pattern cacheFilePattern = Pattern.compile("^(.+)\\.(\\d+)\\.(\\d+)(\\.v1\\.exo)$");
                public final File file;
                public final boolean isCached;
                public final String key;
                public final long lastAccessTimestamp;
                public final long length;
                public final long position;


/* 59 */        public static File getCacheFileName(File cacheDir, String key, long offset, long lastAccessTimestamp) {
/* 60 */            return new File(cacheDir, key + "." + offset + "." + lastAccessTimestamp + SUFFIX);
                }

/* 63 */        public static CacheSpan createLookup(String key, long position) {
/* 64 */            return new CacheSpan(key, position, -1L, false, -1L, null);
                }

/* 67 */        public static CacheSpan createOpenHole(String key, long position) {
/* 68 */            return new CacheSpan(key, position, -1L, false, -1L, null);
                }

/* 71 */        public static CacheSpan createClosedHole(String key, long position, long length) {
/* 72 */            return new CacheSpan(key, position, length, false, -1L, null);
                }

/* 81 */        public static CacheSpan createCacheEntry(File file) {
/* 82 */            Matcher matcher = cacheFilePattern.matcher(file.getName());
/* 83 */            if (matcher.matches()) {
                        return createCacheEntry(matcher.group(1), Long.parseLong(matcher.group(2)), Long.parseLong(matcher.group(3)), file);
                    }
/* 84 */            return null;
                }

/* 91 */        private static CacheSpan createCacheEntry(String key, long position, long lastAccessTimestamp, File file) {
/* 92 */            return new CacheSpan(key, position, file.length(), true, lastAccessTimestamp, file);
                }

/* 95 */        private CacheSpan(String key, long position, long length, boolean isCached, long lastAccessTimestamp, File file) {
/* 97 */            this.key = key;
/* 98 */            this.position = position;
/* 99 */            this.length = length;
/* 100 */           this.isCached = isCached;
/* 101 */           this.file = file;
/* 102 */           this.lastAccessTimestamp = lastAccessTimestamp;
                }

/* 108 */       public boolean isOpenEnded() {
                    return this.length == -1;
                }

/* 117 */       public CacheSpan touch() {
/* 118 */           long now = System.currentTimeMillis();
/* 119 */           File newCacheFile = getCacheFileName(this.file.getParentFile(), this.key, this.position, now);
/* 120 */           this.file.renameTo(newCacheFile);
/* 121 */           return createCacheEntry(this.key, this.position, now, newCacheFile);
                }

                /* JADX INFO: renamed from: compareTo, reason: avoid collision after fix types in other method */
/* 125 */       public int compareTo(CacheSpan another) {
/* 126 */           if (!this.key.equals(another.key)) {
/* 127 */               return this.key.compareTo(another.key);
                    }
/* 129 */           long startOffsetDiff = this.position - another.position;
                    if (startOffsetDiff == 0) {
                        return 0;
                    }
                    return startOffsetDiff < 0 ? -1 : 1;
                }
            }
