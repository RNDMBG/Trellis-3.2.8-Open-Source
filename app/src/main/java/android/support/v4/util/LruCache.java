            package android.support.v4.util;

            import java.util.LinkedHashMap;
            import java.util.Map;
            
            
    public class LruCache<K, V> {
                private int createCount;
                private int evictionCount;
                private int hitCount;
                private final LinkedHashMap<K, V> map;
                private int maxSize;
                private int missCount;
                private int putCount;
                private int size;

        public LruCache(int maxSize) {
            if (maxSize <= 0) {
                throw new IllegalArgumentException("maxSize <= 0");
                    }
            this.maxSize = maxSize;
            this.map = new LinkedHashMap<>(0, 0.75f, true);
                }

        public final V get(K k) {
                    V vPut;
            if (k == null) {
                throw new NullPointerException("key == null");
                    }
                    synchronized (this) {
                        try {
                    V v = this.map.get(k);
                    if (v != null) {
                        this.hitCount++;
                                return v;
                            }
                    this.missCount++;
                    V vCreate = create(k);
                    if (vCreate == null) {
                        return null;
                            }
                            synchronized (this) {
                                try {
                            this.createCount++;
                            vPut = this.map.put(k, vCreate);
                            if (vPut != null) {
                                this.map.put(k, vPut);
                                    } else {
                                this.size += safeSizeOf(k, vCreate);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                   if (vPut != null) {
                       entryRemoved(false, k, vCreate, vPut);
                                return vPut;
                            }
                   trimToSize(this.maxSize);
                   return vCreate;
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }

       public final V put(K key, V value) {
                    V previous;
           if (key == null || value == null) {
               throw new NullPointerException("key == null || value == null");
                    }
                    synchronized (this) {
                        try {
                   this.putCount++;
                   this.size += safeSizeOf(key, value);
                   previous = this.map.put(key, value);
                   if (previous != null) {
                       this.size -= safeSizeOf(key, previous);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           if (previous != null) {
               entryRemoved(false, key, previous, value);
                    }
           trimToSize(this.maxSize);
           return previous;
                }

       public void trimToSize(int maxSize) {
                    K key;
                    V value;
                    while (true) {
                        synchronized (this) {
                            try {
                       if (this.size < 0 || (this.map.isEmpty() && this.size != 0)) {
                                    break;
                                }
                       if (this.size > maxSize && !this.map.isEmpty()) {
                           Map.Entry<K, V> toEvict = this.map.entrySet().iterator().next();
                           key = toEvict.getKey();
                           value = toEvict.getValue();
                           this.map.remove(key);
                           this.size -= safeSizeOf(key, value);
                           this.evictionCount++;
                                } else {
                           return;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               entryRemoved(true, key, value, null);
                    }
           throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }

       public final V remove(K key) {
                    V previous;
           if (key == null) {
               throw new NullPointerException("key == null");
                    }
                    synchronized (this) {
                        try {
                   previous = this.map.remove(key);
                   if (previous != null) {
                       this.size -= safeSizeOf(key, previous);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           if (previous != null) {
               entryRemoved(false, key, previous, null);
                    }
           return previous;
                }

       protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
                }

       protected V create(K key) {
           return null;
                }

       private int safeSizeOf(K key, V value) {
           int result = sizeOf(key, value);
           if (result < 0) {
               throw new IllegalStateException("Negative size: " + key + "=" + value);
                    }
           return result;
                }

       protected int sizeOf(K key, V value) {
           return 1;
                }

       public final void evictAll() {
           trimToSize(-1);
                }

       public final synchronized int size() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.size;
                }

       public final synchronized int maxSize() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.maxSize;
                }

       public final synchronized int hitCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.hitCount;
                }

       public final synchronized int missCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.missCount;
                }

       public final synchronized int createCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.createCount;
                }

       public final synchronized int putCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.putCount;
                }

       public final synchronized int evictionCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.evictionCount;
                }

       public final synchronized Map<K, V> snapshot() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return new LinkedHashMap(this.map);
                }

       public final synchronized String toString() {
                    String str;
                    synchronized (this) {
                        try {
                            int accesses = this.hitCount + this.missCount;
                   int hitPercent = accesses != 0 ? (this.hitCount * 100) / accesses : 0;
                   str = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(hitPercent));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return str;
                }
            }
