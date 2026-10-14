            package com.fasterxml.jackson.core.util;

            import java.util.concurrent.ConcurrentHashMap;
            
            
    public final class InternCache extends ConcurrentHashMap<String, String> {
                private static final int MAX_ENTRIES = 180;
                public static final InternCache instance = new InternCache();
                private static final Object _flushLock = new Object();

        private InternCache() {
            super(MAX_ENTRIES, 0.8f, 4);
                }

        public String intern(String str) {
            String str2 = get(str);
            if (str2 == null) {
                if (size() >= MAX_ENTRIES) {
                            synchronized (_flushLock) {
                                try {
                            if (size() >= MAX_ENTRIES) {
                                clear();
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                String strIntern = str.intern();
                put(strIntern, strIntern);
                return strIntern;
                    }
            return str2;
                }
            }
