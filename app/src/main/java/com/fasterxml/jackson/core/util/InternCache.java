            package com.fasterxml.jackson.core.util;

            import java.util.concurrent.ConcurrentHashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public final class InternCache extends ConcurrentHashMap<String, String> {
                private static final int MAX_ENTRIES = 180;
                public static final InternCache instance = new InternCache();
                private static final Object _flushLock = new Object();

/* 37 */        private InternCache() {
/* 38 */            super(MAX_ENTRIES, 0.8f, 4);
                }

/* 42 */        public String intern(String str) {
/* 43 */            String str2 = get(str);
/* 44 */            if (str2 == null) {
/* 53 */                if (size() >= MAX_ENTRIES) {
                            synchronized (_flushLock) {
                                try {
/* 59 */                            if (size() >= MAX_ENTRIES) {
/* 60 */                                clear();
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
/* 64 */                String strIntern = str.intern();
/* 65 */                put(strIntern, strIntern);
/* 66 */                return strIntern;
                    }
/* 66 */            return str2;
                }
            }
