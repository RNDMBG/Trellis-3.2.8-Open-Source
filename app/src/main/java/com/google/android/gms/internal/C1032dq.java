            package com.google.android.gms.internal;

            import java.util.LinkedHashMap;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dq */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1032dq<K, V> {

                /* JADX INFO: renamed from: lm */
                private final LinkedHashMap<K, V> f1720lm;

                /* JADX INFO: renamed from: ln */
                private int f1721ln;

                /* JADX INFO: renamed from: lo */
                private int f1722lo;

                /* JADX INFO: renamed from: lp */
                private int f1723lp;

                /* JADX INFO: renamed from: lq */
                private int f1724lq;

                /* JADX INFO: renamed from: lr */
                private int f1725lr;

                /* JADX INFO: renamed from: ls */
                private int f1726ls;
                private int size;

                public C1032dq(int i) {
                    if (i <= 0) {
                        throw new IllegalArgumentException("maxSize <= 0");
                    }
                    this.f1721ln = i;
                    this.f1720lm = new LinkedHashMap<>(0, 0.75f, true);
                }

                /* JADX INFO: renamed from: b */
                private int m1619b(K k, V v) {
                    int iSizeOf = sizeOf(k, v);
                    if (iSizeOf < 0) {
                        throw new IllegalStateException("Negative size: " + k + "=" + v);
                    }
                    return iSizeOf;
                }

                protected V create(K key) {
                    return null;
                }

                protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
                }

                public final void evictAll() {
                    trimToSize(-1);
                }

                public final V get(K k) {
                    V vPut;
                    if (k == null) {
                        throw new NullPointerException("key == null");
                    }
                    synchronized (this) {
                        try {
                            V v = this.f1720lm.get(k);
                            if (v != null) {
                                this.f1725lr++;
                                return v;
                            }
                            this.f1726ls++;
                            V vCreate = create(k);
                            if (vCreate == null) {
                                return null;
                            }
                            synchronized (this) {
                                try {
                                    this.f1723lp++;
                                    vPut = this.f1720lm.put(k, vCreate);
                                    if (vPut != null) {
                                        this.f1720lm.put(k, vPut);
                                    } else {
                                        this.size += m1619b(k, vCreate);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            if (vPut != null) {
                                entryRemoved(false, k, vCreate, vPut);
                                return vPut;
                            }
                            trimToSize(this.f1721ln);
                            return vCreate;
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }

                public final V put(K key, V value) {
                    V vPut;
                    if (key == null || value == null) {
                        throw new NullPointerException("key == null || value == null");
                    }
                    synchronized (this) {
                        try {
                            this.f1722lo++;
                            this.size += m1619b(key, value);
                            vPut = this.f1720lm.put(key, value);
                            if (vPut != null) {
                                this.size -= m1619b(key, vPut);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (vPut != null) {
                        entryRemoved(false, key, vPut, value);
                    }
                    trimToSize(this.f1721ln);
                    return vPut;
                }

                public final synchronized int size() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.size;
                }

                protected int sizeOf(K key, V value) {
                    return 1;
                }

                public final synchronized String toString() {
                    String str;
                    synchronized (this) {
                        try {
                            int i = this.f1725lr + this.f1726ls;
                            str = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.f1721ln), Integer.valueOf(this.f1725lr), Integer.valueOf(this.f1726ls), Integer.valueOf(i != 0 ? (this.f1725lr * 100) / i : 0));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return str;
                }

                public void trimToSize(int maxSize) {
                    K key;
                    V value;
                    while (true) {
                        synchronized (this) {
                            try {
                                if (this.size < 0 || (this.f1720lm.isEmpty() && this.size != 0)) {
                                    break;
                                }
                                if (this.size <= maxSize || this.f1720lm.isEmpty()) {
                                    return;
                                }
                                Map.Entry<K, V> next = this.f1720lm.entrySet().iterator().next();
                                key = next.getKey();
                                value = next.getValue();
                                this.f1720lm.remove(key);
                                this.size -= m1619b(key, value);
                                this.f1724lq++;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        entryRemoved(true, key, value, null);
                    }
                    throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }
            }
