            package android.support.v4.util;

            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 60 */    public class SimpleArrayMap<K, V> {
                private static final int BASE_SIZE = 4;
                private static final int CACHE_SIZE = 10;
                private static final boolean DEBUG = false;
                private static final String TAG = "ArrayMap";
                static Object[] mBaseCache;
                static int mBaseCacheSize;
                static Object[] mTwiceBaseCache;
                static int mTwiceBaseCacheSize;
                Object[] mArray;
                int[] mHashes;
                int mSize;

/* 61 */        int indexOf(Object key, int hash) {
/* 62 */            int N = this.mSize;
/* 65 */            if (N == 0) {
/* 66 */                return -1;
                    }
/* 69 */            int index = ContainerHelpers.binarySearch(this.mHashes, N, hash);
/* 72 */            if (index >= 0 && !key.equals(this.mArray[index << 1])) {
/* 83 */                int end = index + 1;
                        while (end < N && this.mHashes[end] == hash) {
/* 84 */                    if (key.equals(this.mArray[end << 1])) {
                                return end;
                            }
/* 83 */                    end++;
                        }
                        for (int i = index - 1; i >= 0 && this.mHashes[i] == hash; i--) {
/* 89 */                    if (key.equals(this.mArray[i << 1])) {
                                return i;
                            }
                        }
/* 96 */                return end ^ (-1);
                    }
/* 96 */            return index;
                }

/* 99 */        int indexOfNull() {
/* 100 */           int N = this.mSize;
/* 103 */           if (N == 0) {
/* 104 */               return -1;
                    }
/* 107 */           int index = ContainerHelpers.binarySearch(this.mHashes, N, 0);
/* 110 */           if (index >= 0 && this.mArray[index << 1] != null) {
/* 121 */               int end = index + 1;
                        while (end < N && this.mHashes[end] == 0) {
/* 122 */                   if (this.mArray[end << 1] == null) {
                                return end;
                            }
/* 121 */                   end++;
                        }
                        for (int i = index - 1; i >= 0 && this.mHashes[i] == 0; i--) {
/* 127 */                   if (this.mArray[i << 1] == null) {
                                return i;
                            }
                        }
/* 134 */               return end ^ (-1);
                    }
/* 134 */           return index;
                }

/* 137 */       private void allocArrays(int size) {
/* 138 */           if (size == 8) {
                        synchronized (ArrayMap.class) {
                            try {
/* 140 */                       if (mTwiceBaseCache != null) {
/* 141 */                           Object[] array = mTwiceBaseCache;
/* 142 */                           this.mArray = array;
/* 143 */                           mTwiceBaseCache = (Object[]) array[0];
/* 144 */                           this.mHashes = (int[]) array[1];
                                    array[1] = null;
/* 145 */                           array[0] = null;
/* 146 */                           mTwiceBaseCacheSize--;
/* 170 */                           return;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    } else if (size == 4) {
                        synchronized (ArrayMap.class) {
                            try {
/* 154 */                       if (mBaseCache != null) {
/* 155 */                           Object[] array2 = mBaseCache;
/* 156 */                           this.mArray = array2;
/* 157 */                           mBaseCache = (Object[]) array2[0];
/* 158 */                           this.mHashes = (int[]) array2[1];
                                    array2[1] = null;
/* 159 */                           array2[0] = null;
/* 160 */                           mBaseCacheSize--;
/* 170 */                           return;
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
/* 168 */           this.mHashes = new int[size];
/* 169 */           this.mArray = new Object[size << 1];
                }

/* 172 */       private static void freeArrays(int[] hashes, Object[] array, int size) {
/* 173 */           if (hashes.length == 8) {
                        synchronized (ArrayMap.class) {
                            try {
/* 175 */                       if (mTwiceBaseCacheSize < 10) {
/* 176 */                           array[0] = mTwiceBaseCache;
/* 177 */                           array[1] = hashes;
                                    for (int i = (size << 1) - 1; i >= 2; i--) {
/* 179 */                               array[i] = null;
                                    }
/* 181 */                           mTwiceBaseCache = array;
/* 182 */                           mTwiceBaseCacheSize++;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 202 */               return;
                    }
/* 187 */           if (hashes.length == 4) {
                        synchronized (ArrayMap.class) {
                            try {
/* 189 */                       if (mBaseCacheSize < 10) {
/* 190 */                           array[0] = mBaseCache;
/* 191 */                           array[1] = hashes;
                                    for (int i2 = (size << 1) - 1; i2 >= 2; i2--) {
/* 193 */                               array[i2] = null;
                                    }
/* 195 */                           mBaseCache = array;
/* 196 */                           mBaseCacheSize++;
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
                }

/* 207 */       public SimpleArrayMap() {
/* 209 */           this.mHashes = ContainerHelpers.EMPTY_INTS;
/* 210 */           this.mArray = ContainerHelpers.EMPTY_OBJECTS;
/* 211 */           this.mSize = 0;
                }

/* 216 */       public SimpleArrayMap(int capacity) {
/* 218 */           if (capacity == 0) {
/* 219 */               this.mHashes = ContainerHelpers.EMPTY_INTS;
/* 220 */               this.mArray = ContainerHelpers.EMPTY_OBJECTS;
                    } else {
/* 222 */               allocArrays(capacity);
                    }
/* 224 */           this.mSize = 0;
                }

/* 230 */       public SimpleArrayMap(SimpleArrayMap map) {
/* 231 */           this();
/* 232 */           if (map != null) {
/* 233 */               putAll(map);
                    }
                }

/* 240 */       public void clear() {
/* 241 */           if (this.mSize != 0) {
/* 242 */               freeArrays(this.mHashes, this.mArray, this.mSize);
/* 243 */               this.mHashes = ContainerHelpers.EMPTY_INTS;
/* 244 */               this.mArray = ContainerHelpers.EMPTY_OBJECTS;
/* 245 */               this.mSize = 0;
                    }
                }

/* 253 */       public void ensureCapacity(int minimumCapacity) {
/* 254 */           if (this.mHashes.length < minimumCapacity) {
/* 255 */               int[] ohashes = this.mHashes;
/* 256 */               Object[] oarray = this.mArray;
/* 257 */               allocArrays(minimumCapacity);
/* 258 */               if (this.mSize > 0) {
/* 259 */                   System.arraycopy(ohashes, 0, this.mHashes, 0, this.mSize);
/* 260 */                   System.arraycopy(oarray, 0, this.mArray, 0, this.mSize << 1);
                        }
/* 262 */               freeArrays(ohashes, oarray, this.mSize);
                    }
                }

/* 272 */       public boolean containsKey(Object key) {
/* 273 */           if (key == null) {
                        return indexOfNull() >= 0;
                    }
                    return indexOf(key, key.hashCode()) >= 0;
                }

/* 276 */       int indexOfValue(Object value) {
/* 277 */           int N = this.mSize * 2;
/* 278 */           Object[] array = this.mArray;
/* 279 */           if (value == null) {
                        for (int i = 1; i < N; i += 2) {
/* 281 */                   if (array[i] == null) {
/* 282 */                       return i >> 1;
                            }
                        }
                    } else {
                        for (int i2 = 1; i2 < N; i2 += 2) {
/* 287 */                   if (value.equals(array[i2])) {
/* 288 */                       return i2 >> 1;
                            }
                        }
                    }
/* 292 */           return -1;
                }

/* 302 */       public boolean containsValue(Object value) {
                    return indexOfValue(value) >= 0;
                }

/* 312 */       public V get(Object obj) {
/* 313 */           int iIndexOfNull = obj == null ? indexOfNull() : indexOf(obj, obj.hashCode());
/* 314 */           if (iIndexOfNull >= 0) {
                        return (V) this.mArray[(iIndexOfNull << 1) + 1];
                    }
/* 314 */           return null;
                }

/* 322 */       public K keyAt(int i) {
/* 323 */           return (K) this.mArray[i << 1];
                }

/* 331 */       public V valueAt(int i) {
/* 332 */           return (V) this.mArray[(i << 1) + 1];
                }

/* 341 */       public V setValueAt(int i, V v) {
/* 342 */           int i2 = (i << 1) + 1;
/* 343 */           V v2 = (V) this.mArray[i2];
/* 344 */           this.mArray[i2] = v;
/* 345 */           return v2;
                }

/* 351 */       public boolean isEmpty() {
                    return this.mSize <= 0;
                }

/* 365 */       public V put(K k, V v) {
                    int iHashCode;
                    int iIndexOf;
                    int i = 8;
/* 366 */           if (k == null) {
/* 367 */               iHashCode = 0;
/* 368 */               iIndexOf = indexOfNull();
                    } else {
/* 370 */               iHashCode = k.hashCode();
/* 371 */               iIndexOf = indexOf(k, iHashCode);
                    }
/* 373 */           if (iIndexOf >= 0) {
/* 374 */               int i2 = (iIndexOf << 1) + 1;
/* 375 */               V v2 = (V) this.mArray[i2];
/* 376 */               this.mArray[i2] = v;
/* 411 */               return v2;
                    }
/* 380 */           int i3 = iIndexOf ^ (-1);
/* 381 */           if (this.mSize >= this.mHashes.length) {
/* 382 */               if (this.mSize >= 8) {
                            i = this.mSize + (this.mSize >> 1);
                        } else if (this.mSize < 4) {
                            i = 4;
                        }
/* 387 */               int[] iArr = this.mHashes;
/* 388 */               Object[] objArr = this.mArray;
/* 389 */               allocArrays(i);
/* 391 */               if (this.mHashes.length > 0) {
/* 393 */                   System.arraycopy(iArr, 0, this.mHashes, 0, iArr.length);
/* 394 */                   System.arraycopy(objArr, 0, this.mArray, 0, objArr.length);
                        }
/* 397 */               freeArrays(iArr, objArr, this.mSize);
                    }
/* 400 */           if (i3 < this.mSize) {
/* 403 */               System.arraycopy(this.mHashes, i3, this.mHashes, i3 + 1, this.mSize - i3);
/* 404 */               System.arraycopy(this.mArray, i3 << 1, this.mArray, (i3 + 1) << 1, (this.mSize - i3) << 1);
                    }
/* 407 */           this.mHashes[i3] = iHashCode;
/* 408 */           this.mArray[i3 << 1] = k;
/* 409 */           this.mArray[(i3 << 1) + 1] = v;
/* 410 */           this.mSize++;
/* 411 */           return null;
                }

/* 418 */       public void putAll(SimpleArrayMap<? extends K, ? extends V> array) {
/* 419 */           int N = array.mSize;
/* 420 */           ensureCapacity(this.mSize + N);
/* 421 */           if (this.mSize == 0) {
/* 422 */               if (N > 0) {
/* 423 */                   System.arraycopy(array.mHashes, 0, this.mHashes, 0, N);
/* 424 */                   System.arraycopy(array.mArray, 0, this.mArray, 0, N << 1);
/* 425 */                   this.mSize = N;
/* 432 */                   return;
                        }
/* 432 */               return;
                    }
                    for (int i = 0; i < N; i++) {
/* 429 */               put(array.keyAt(i), array.valueAt(i));
                    }
                }

/* 440 */       public V remove(Object key) {
/* 441 */           int index = key == null ? indexOfNull() : indexOf(key, key.hashCode());
/* 442 */           if (index >= 0) {
/* 443 */               return removeAt(index);
                    }
/* 446 */           return null;
                }

/* 454 */       public V removeAt(int i) {
/* 455 */           V v = (V) this.mArray[(i << 1) + 1];
/* 456 */           if (this.mSize <= 1) {
/* 459 */               freeArrays(this.mHashes, this.mArray, this.mSize);
/* 460 */               this.mHashes = ContainerHelpers.EMPTY_INTS;
/* 461 */               this.mArray = ContainerHelpers.EMPTY_OBJECTS;
/* 462 */               this.mSize = 0;
                    } else if (this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3) {
/* 468 */               int i2 = this.mSize > 8 ? this.mSize + (this.mSize >> 1) : 8;
/* 472 */               int[] iArr = this.mHashes;
/* 473 */               Object[] objArr = this.mArray;
/* 474 */               allocArrays(i2);
/* 476 */               this.mSize--;
/* 477 */               if (i > 0) {
/* 479 */                   System.arraycopy(iArr, 0, this.mHashes, 0, i);
/* 480 */                   System.arraycopy(objArr, 0, this.mArray, 0, i << 1);
                        }
/* 482 */               if (i < this.mSize) {
/* 485 */                   System.arraycopy(iArr, i + 1, this.mHashes, i, this.mSize - i);
/* 486 */                   System.arraycopy(objArr, (i + 1) << 1, this.mArray, i << 1, (this.mSize - i) << 1);
                        }
                    } else {
/* 490 */               this.mSize--;
/* 491 */               if (i < this.mSize) {
/* 494 */                   System.arraycopy(this.mHashes, i + 1, this.mHashes, i, this.mSize - i);
/* 495 */                   System.arraycopy(this.mArray, (i + 1) << 1, this.mArray, i << 1, (this.mSize - i) << 1);
                        }
/* 498 */               this.mArray[this.mSize << 1] = null;
/* 499 */               this.mArray[(this.mSize << 1) + 1] = null;
                    }
/* 502 */           return v;
                }

/* 508 */       public int size() {
/* 509 */           return this.mSize;
                }

/* 521 */       public boolean equals(Object object) {
/* 522 */           if (this == object) {
                        return true;
                    }
/* 525 */           if (!(object instanceof Map)) {
                        return false;
                    }
/* 526 */           Map<?, ?> map = (Map) object;
/* 527 */           if (size() != map.size()) {
                        return false;
                    }
                    for (int i = 0; i < this.mSize; i++) {
                        try {
/* 533 */                   K key = keyAt(i);
/* 534 */                   V mine = valueAt(i);
/* 535 */                   Object theirs = map.get(key);
/* 536 */                   if (mine == null) {
/* 537 */                       if (theirs != null || !map.containsKey(key)) {
                                    return false;
                                }
                            } else if (!mine.equals(theirs)) {
                                return false;
                            }
                        } catch (ClassCastException e) {
                            return false;
                        } catch (NullPointerException e2) {
                            return false;
                        }
                    }
                    return true;
                }

/* 558 */       public int hashCode() {
/* 559 */           int[] hashes = this.mHashes;
/* 560 */           Object[] array = this.mArray;
/* 561 */           int result = 0;
/* 562 */           int i = 0;
                    int v = 1;
                    int s = this.mSize;
                    while (i < s) {
/* 563 */               Object value = array[v];
                        result += (value == null ? 0 : value.hashCode()) ^ hashes[i];
/* 562 */               i++;
                        v += 2;
                    }
/* 566 */           return result;
                }

/* 577 */       public String toString() {
/* 578 */           if (isEmpty()) {
/* 579 */               return "{}";
                    }
/* 582 */           StringBuilder buffer = new StringBuilder(this.mSize * 28);
/* 583 */           buffer.append('{');
                    for (int i = 0; i < this.mSize; i++) {
/* 585 */               if (i > 0) {
/* 586 */                   buffer.append(", ");
                        }
/* 588 */               Object key = keyAt(i);
/* 589 */               if (key != this) {
/* 590 */                   buffer.append(key);
                        } else {
/* 592 */                   buffer.append("(this Map)");
                        }
/* 594 */               buffer.append('=');
/* 595 */               Object value = valueAt(i);
/* 596 */               if (value != this) {
/* 597 */                   buffer.append(value);
                        } else {
/* 599 */                   buffer.append("(this Map)");
                        }
                    }
/* 602 */           buffer.append('}');
/* 603 */           return buffer.toString();
                }
            }
