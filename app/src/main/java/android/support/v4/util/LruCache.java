            package android.support.v4.util;

            import java.util.LinkedHashMap;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class LruCache<K, V> {
                private int createCount;
                private int evictionCount;
                private int hitCount;
                private final LinkedHashMap<K, V> map;
                private int maxSize;
                private int missCount;
                private int putCount;
                private int size;

/* 46 */        public LruCache(int maxSize) {
/* 48 */            if (maxSize <= 0) {
/* 49 */                throw new IllegalArgumentException("maxSize <= 0");
                    }
/* 51 */            this.maxSize = maxSize;
/* 52 */            this.map = new LinkedHashMap<>(0, 0.75f, true);
                }

/* 61 */        public final V get(K k) {
                    V vPut;
/* 62 */            if (k == null) {
/* 63 */                throw new NullPointerException("key == null");
                    }
                    synchronized (this) {
                        try {
/* 68 */                    V v = this.map.get(k);
/* 69 */                    if (v != null) {
/* 70 */                        this.hitCount++;
                                return v;
                            }
/* 73 */                    this.missCount++;
/* 83 */                    V vCreate = create(k);
/* 84 */                    if (vCreate == null) {
/* 85 */                        return null;
                            }
                            synchronized (this) {
                                try {
/* 89 */                            this.createCount++;
/* 90 */                            vPut = this.map.put(k, vCreate);
/* 92 */                            if (vPut != null) {
/* 94 */                                this.map.put(k, vPut);
                                    } else {
/* 96 */                                this.size += safeSizeOf(k, vCreate);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
/* 100 */                   if (vPut != null) {
/* 101 */                       entryRemoved(false, k, vCreate, vPut);
                                return vPut;
                            }
/* 104 */                   trimToSize(this.maxSize);
/* 105 */                   return vCreate;
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }

/* 115 */       public final V put(K key, V value) {
                    V previous;
/* 116 */           if (key == null || value == null) {
/* 117 */               throw new NullPointerException("key == null || value == null");
                    }
                    synchronized (this) {
                        try {
/* 122 */                   this.putCount++;
/* 123 */                   this.size += safeSizeOf(key, value);
/* 124 */                   previous = this.map.put(key, value);
/* 125 */                   if (previous != null) {
/* 126 */                       this.size -= safeSizeOf(key, previous);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 130 */           if (previous != null) {
/* 131 */               entryRemoved(false, key, previous, value);
                    }
/* 134 */           trimToSize(this.maxSize);
/* 135 */           return previous;
                }

/* 148 */       public void trimToSize(int maxSize) {
                    K key;
                    V value;
                    while (true) {
                        synchronized (this) {
                            try {
/* 150 */                       if (this.size < 0 || (this.map.isEmpty() && this.size != 0)) {
                                    break;
                                }
/* 155 */                       if (this.size > maxSize && !this.map.isEmpty()) {
/* 159 */                           Map.Entry<K, V> toEvict = this.map.entrySet().iterator().next();
/* 160 */                           key = toEvict.getKey();
/* 161 */                           value = toEvict.getValue();
/* 162 */                           this.map.remove(key);
/* 163 */                           this.size -= safeSizeOf(key, value);
/* 164 */                           this.evictionCount++;
                                } else {
/* 169 */                           return;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 167 */               entryRemoved(true, key, value, null);
                    }
/* 151 */           throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }

/* 176 */       public final V remove(K key) {
                    V previous;
/* 177 */           if (key == null) {
/* 178 */               throw new NullPointerException("key == null");
                    }
                    synchronized (this) {
                        try {
/* 183 */                   previous = this.map.remove(key);
/* 184 */                   if (previous != null) {
/* 185 */                       this.size -= safeSizeOf(key, previous);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 189 */           if (previous != null) {
/* 190 */               entryRemoved(false, key, previous, null);
                    }
/* 193 */           return previous;
                }

/* 210 */       protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
                }

/* 228 */       protected V create(K key) {
/* 229 */           return null;
                }

/* 232 */       private int safeSizeOf(K key, V value) {
/* 233 */           int result = sizeOf(key, value);
/* 234 */           if (result < 0) {
/* 235 */               throw new IllegalStateException("Negative size: " + key + "=" + value);
                    }
/* 237 */           return result;
                }

/* 247 */       protected int sizeOf(K key, V value) {
/* 248 */           return 1;
                }

/* 254 */       public final void evictAll() {
/* 255 */           trimToSize(-1);
                }

/* 263 */       public final synchronized int size() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.size;
                }

/* 272 */       public final synchronized int maxSize() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.maxSize;
                }

/* 279 */       public final synchronized int hitCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.hitCount;
                }

/* 287 */       public final synchronized int missCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.missCount;
                }

/* 294 */       public final synchronized int createCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.createCount;
                }

/* 301 */       public final synchronized int putCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.putCount;
                }

/* 308 */       public final synchronized int evictionCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.evictionCount;
                }

/* 316 */       public final synchronized Map<K, V> snapshot() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return new LinkedHashMap(this.map);
                }

/* 320 */       public final synchronized String toString() {
                    String str;
                    synchronized (this) {
                        try {
                            int accesses = this.hitCount + this.missCount;
/* 322 */                   int hitPercent = accesses != 0 ? (this.hitCount * 100) / accesses : 0;
/* 323 */                   str = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(hitPercent));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return str;
                }
            }
