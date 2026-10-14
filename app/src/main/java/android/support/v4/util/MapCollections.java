            package android.support.v4.util;

            import java.lang.reflect.Array;
            import java.util.Collection;
            import java.util.Iterator;
            import java.util.Map;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    abstract class MapCollections<K, V> {
                MapCollections<K, V>.EntrySet mEntrySet;
                MapCollections<K, V>.KeySet mKeySet;
                MapCollections<K, V>.ValuesCollection mValues;

                protected abstract void colClear();

                protected abstract Object colGetEntry(int i, int i2);

                protected abstract Map<K, V> colGetMap();

                protected abstract int colGetSize();

                protected abstract int colIndexOfKey(Object obj);

                protected abstract int colIndexOfValue(Object obj);

                protected abstract void colPut(K k, V v);

                protected abstract void colRemoveAt(int i);

                protected abstract V colSetValue(int i, V v);

/* 29 */        MapCollections() {
                }

/* 39 */        final class ArrayIterator<T> implements Iterator<T> {
                    boolean mCanRemove = false;
                    int mIndex;
                    final int mOffset;
                    int mSize;

/* 40 */            ArrayIterator(int offset) {
/* 42 */                this.mOffset = offset;
/* 43 */                this.mSize = MapCollections.this.colGetSize();
                    }

                    @Override // java.util.Iterator
/* 47 */            public boolean hasNext() {
                        return this.mIndex < this.mSize;
                    }

                    @Override // java.util.Iterator
/* 52 */            public T next() {
/* 53 */                T t = (T) MapCollections.this.colGetEntry(this.mIndex, this.mOffset);
/* 54 */                this.mIndex++;
/* 55 */                this.mCanRemove = true;
/* 56 */                return t;
                    }

                    @Override // java.util.Iterator
/* 60 */            public void remove() {
/* 61 */                if (!this.mCanRemove) {
/* 62 */                    throw new IllegalStateException();
                        }
/* 64 */                this.mIndex--;
/* 65 */                this.mSize--;
/* 66 */                this.mCanRemove = false;
/* 67 */                MapCollections.this.colRemoveAt(this.mIndex);
                    }
                }

/* 69 */        final class MapIterator implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
                    int mEnd;
                    boolean mEntryValid = false;
                    int mIndex = -1;

                    @Override // java.util.Iterator
/* 70 */            public /* bridge */ /* synthetic */ Object next() {
/* 71 */                return next();
                    }

/* 75 */            MapIterator() {
/* 77 */                this.mEnd = MapCollections.this.colGetSize() - 1;
                    }

                    @Override // java.util.Iterator
/* 82 */            public boolean hasNext() {
                        return this.mIndex < this.mEnd;
                    }

                    @Override // java.util.Iterator
/* 87 */            public Map.Entry<K, V> next() {
/* 88 */                this.mIndex++;
/* 89 */                this.mEntryValid = true;
/* 90 */                return this;
                    }

                    @Override // java.util.Iterator
/* 94 */            public void remove() {
/* 95 */                if (!this.mEntryValid) {
/* 96 */                    throw new IllegalStateException();
                        }
/* 98 */                MapCollections.this.colRemoveAt(this.mIndex);
/* 99 */                this.mIndex--;
/* 100 */               this.mEnd--;
/* 101 */               this.mEntryValid = false;
                    }

                    @Override // java.util.Map.Entry
/* 105 */           public K getKey() {
/* 106 */               if (!this.mEntryValid) {
/* 107 */                   throw new IllegalStateException("This container does not support retaining Map.Entry objects");
                        }
/* 110 */               return (K) MapCollections.this.colGetEntry(this.mIndex, 0);
                    }

                    @Override // java.util.Map.Entry
/* 114 */           public V getValue() {
/* 115 */               if (!this.mEntryValid) {
/* 116 */                   throw new IllegalStateException("This container does not support retaining Map.Entry objects");
                        }
/* 119 */               return (V) MapCollections.this.colGetEntry(this.mIndex, 1);
                    }

                    @Override // java.util.Map.Entry
/* 123 */           public V setValue(V v) {
/* 124 */               if (!this.mEntryValid) {
/* 125 */                   throw new IllegalStateException("This container does not support retaining Map.Entry objects");
                        }
/* 128 */               return (V) MapCollections.this.colSetValue(this.mIndex, v);
                    }

                    @Override // java.util.Map.Entry
/* 132 */           public final boolean equals(Object o) {
/* 133 */               if (!this.mEntryValid) {
/* 134 */                   throw new IllegalStateException("This container does not support retaining Map.Entry objects");
                        }
/* 137 */               if (!(o instanceof Map.Entry)) {
                            return false;
                        }
/* 140 */               Map.Entry<?, ?> e = (Map.Entry) o;
/* 141 */               return ContainerHelpers.equal(e.getKey(), MapCollections.this.colGetEntry(this.mIndex, 0)) && ContainerHelpers.equal(e.getValue(), MapCollections.this.colGetEntry(this.mIndex, 1));
                    }

                    @Override // java.util.Map.Entry
/* 146 */           public final int hashCode() {
/* 147 */               if (!this.mEntryValid) {
/* 148 */                   throw new IllegalStateException("This container does not support retaining Map.Entry objects");
                        }
/* 151 */               Object key = MapCollections.this.colGetEntry(this.mIndex, 0);
/* 152 */               Object value = MapCollections.this.colGetEntry(this.mIndex, 1);
                        return (value != null ? value.hashCode() : 0) ^ (key == null ? 0 : key.hashCode());
                    }

/* 158 */           public final String toString() {
/* 159 */               return getKey() + "=" + getValue();
                    }
                }

/* 161 */       final class EntrySet implements Set<Map.Entry<K, V>> {
/* 162 */           EntrySet() {
                    }

                    @Override // java.util.Set, java.util.Collection
/* 162 */           public /* bridge */ /* synthetic */ boolean add(Object x0) {
/* 163 */               return add((Map.Entry) x0);
                    }

/* 165 */           public boolean add(Map.Entry<K, V> object) {
/* 166 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 170 */           public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
/* 171 */               int oldSize = MapCollections.this.colGetSize();
                        for (Map.Entry<K, V> entry : collection) {
/* 173 */                   MapCollections.this.colPut(entry.getKey(), entry.getValue());
                        }
                        return oldSize != MapCollections.this.colGetSize();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 179 */           public void clear() {
/* 180 */               MapCollections.this.colClear();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 184 */           public boolean contains(Object o) {
/* 185 */               if (!(o instanceof Map.Entry)) {
                            return false;
                        }
/* 187 */               Map.Entry<?, ?> e = (Map.Entry) o;
/* 188 */               int index = MapCollections.this.colIndexOfKey(e.getKey());
/* 189 */               if (index < 0) {
                            return false;
                        }
/* 192 */               Object foundVal = MapCollections.this.colGetEntry(index, 1);
/* 193 */               return ContainerHelpers.equal(foundVal, e.getValue());
                    }

                    @Override // java.util.Set, java.util.Collection
/* 197 */           public boolean containsAll(Collection<?> collection) {
/* 198 */               Iterator<?> it = collection.iterator();
/* 199 */               while (it.hasNext()) {
/* 200 */                   if (!contains(it.next())) {
/* 201 */                       return false;
                            }
                        }
                        return true;
                    }

                    @Override // java.util.Set, java.util.Collection
/* 208 */           public boolean isEmpty() {
                        return MapCollections.this.colGetSize() == 0;
                    }

                    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
/* 213 */           public Iterator<Map.Entry<K, V>> iterator() {
/* 214 */               return new MapIterator();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 218 */           public boolean remove(Object object) {
/* 219 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 223 */           public boolean removeAll(Collection<?> collection) {
/* 224 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 228 */           public boolean retainAll(Collection<?> collection) {
/* 229 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 233 */           public int size() {
/* 234 */               return MapCollections.this.colGetSize();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 238 */           public Object[] toArray() {
/* 239 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 243 */           public <T> T[] toArray(T[] array) {
/* 244 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 248 */           public boolean equals(Object object) {
/* 249 */               return MapCollections.equalsSetHelper(this, object);
                    }

                    @Override // java.util.Set, java.util.Collection
/* 253 */           public int hashCode() {
/* 254 */               int result = 0;
                        for (int i = MapCollections.this.colGetSize() - 1; i >= 0; i--) {
/* 256 */                   Object key = MapCollections.this.colGetEntry(i, 0);
/* 257 */                   Object value = MapCollections.this.colGetEntry(i, 1);
                            result += (value == null ? 0 : value.hashCode()) ^ (key == null ? 0 : key.hashCode());
                        }
/* 261 */               return result;
                    }
                }

/* 263 */       final class KeySet implements Set<K> {
/* 264 */           KeySet() {
                    }

                    @Override // java.util.Set, java.util.Collection
/* 268 */           public boolean add(K object) {
/* 269 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 273 */           public boolean addAll(Collection<? extends K> collection) {
/* 274 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 278 */           public void clear() {
/* 279 */               MapCollections.this.colClear();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 283 */           public boolean contains(Object object) {
                        return MapCollections.this.colIndexOfKey(object) >= 0;
                    }

                    @Override // java.util.Set, java.util.Collection
/* 288 */           public boolean containsAll(Collection<?> collection) {
/* 289 */               return MapCollections.containsAllHelper(MapCollections.this.colGetMap(), collection);
                    }

                    @Override // java.util.Set, java.util.Collection
/* 293 */           public boolean isEmpty() {
                        return MapCollections.this.colGetSize() == 0;
                    }

                    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
/* 298 */           public Iterator<K> iterator() {
/* 299 */               return new ArrayIterator(0);
                    }

                    @Override // java.util.Set, java.util.Collection
/* 303 */           public boolean remove(Object object) {
/* 304 */               int index = MapCollections.this.colIndexOfKey(object);
/* 305 */               if (index < 0) {
                            return false;
                        }
/* 306 */               MapCollections.this.colRemoveAt(index);
/* 307 */               return true;
                    }

                    @Override // java.util.Set, java.util.Collection
/* 313 */           public boolean removeAll(Collection<?> collection) {
/* 314 */               return MapCollections.removeAllHelper(MapCollections.this.colGetMap(), collection);
                    }

                    @Override // java.util.Set, java.util.Collection
/* 318 */           public boolean retainAll(Collection<?> collection) {
/* 319 */               return MapCollections.retainAllHelper(MapCollections.this.colGetMap(), collection);
                    }

                    @Override // java.util.Set, java.util.Collection
/* 323 */           public int size() {
/* 324 */               return MapCollections.this.colGetSize();
                    }

                    @Override // java.util.Set, java.util.Collection
/* 328 */           public Object[] toArray() {
/* 329 */               return MapCollections.this.toArrayHelper(0);
                    }

                    @Override // java.util.Set, java.util.Collection
/* 333 */           public <T> T[] toArray(T[] tArr) {
/* 334 */               return (T[]) MapCollections.this.toArrayHelper(tArr, 0);
                    }

                    @Override // java.util.Set, java.util.Collection
/* 338 */           public boolean equals(Object object) {
/* 339 */               return MapCollections.equalsSetHelper(this, object);
                    }

                    @Override // java.util.Set, java.util.Collection
/* 343 */           public int hashCode() {
/* 344 */               int result = 0;
                        for (int i = MapCollections.this.colGetSize() - 1; i >= 0; i--) {
/* 346 */                   Object obj = MapCollections.this.colGetEntry(i, 0);
/* 347 */                   result += obj == null ? 0 : obj.hashCode();
                        }
/* 349 */               return result;
                    }
                }

/* 351 */       final class ValuesCollection implements Collection<V> {
/* 352 */           ValuesCollection() {
                    }

                    @Override // java.util.Collection
/* 356 */           public boolean add(V object) {
/* 357 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.util.Collection
/* 361 */           public boolean addAll(Collection<? extends V> collection) {
/* 362 */               throw new UnsupportedOperationException();
                    }

                    @Override // java.util.Collection
/* 366 */           public void clear() {
/* 367 */               MapCollections.this.colClear();
                    }

                    @Override // java.util.Collection
/* 371 */           public boolean contains(Object object) {
                        return MapCollections.this.colIndexOfValue(object) >= 0;
                    }

                    @Override // java.util.Collection
/* 376 */           public boolean containsAll(Collection<?> collection) {
/* 377 */               Iterator<?> it = collection.iterator();
/* 378 */               while (it.hasNext()) {
/* 379 */                   if (!contains(it.next())) {
/* 380 */                       return false;
                            }
                        }
                        return true;
                    }

                    @Override // java.util.Collection
/* 387 */           public boolean isEmpty() {
                        return MapCollections.this.colGetSize() == 0;
                    }

                    @Override // java.util.Collection, java.lang.Iterable
/* 392 */           public Iterator<V> iterator() {
/* 393 */               return new ArrayIterator(1);
                    }

                    @Override // java.util.Collection
/* 397 */           public boolean remove(Object object) {
/* 398 */               int index = MapCollections.this.colIndexOfValue(object);
/* 399 */               if (index < 0) {
                            return false;
                        }
/* 400 */               MapCollections.this.colRemoveAt(index);
/* 401 */               return true;
                    }

                    @Override // java.util.Collection
/* 407 */           public boolean removeAll(Collection<?> collection) {
/* 408 */               int N = MapCollections.this.colGetSize();
/* 409 */               boolean changed = false;
/* 410 */               int i = 0;
                        while (i < N) {
/* 411 */                   Object cur = MapCollections.this.colGetEntry(i, 1);
/* 412 */                   if (collection.contains(cur)) {
/* 413 */                       MapCollections.this.colRemoveAt(i);
/* 414 */                       i--;
/* 415 */                       N--;
/* 416 */                       changed = true;
                            }
/* 410 */                   i++;
                        }
/* 419 */               return changed;
                    }

                    @Override // java.util.Collection
/* 423 */           public boolean retainAll(Collection<?> collection) {
/* 424 */               int N = MapCollections.this.colGetSize();
/* 425 */               boolean changed = false;
/* 426 */               int i = 0;
                        while (i < N) {
/* 427 */                   Object cur = MapCollections.this.colGetEntry(i, 1);
/* 428 */                   if (!collection.contains(cur)) {
/* 429 */                       MapCollections.this.colRemoveAt(i);
/* 430 */                       i--;
/* 431 */                       N--;
/* 432 */                       changed = true;
                            }
/* 426 */                   i++;
                        }
/* 435 */               return changed;
                    }

                    @Override // java.util.Collection
/* 439 */           public int size() {
/* 440 */               return MapCollections.this.colGetSize();
                    }

                    @Override // java.util.Collection
/* 444 */           public Object[] toArray() {
/* 445 */               return MapCollections.this.toArrayHelper(1);
                    }

                    @Override // java.util.Collection
/* 449 */           public <T> T[] toArray(T[] tArr) {
/* 450 */               return (T[]) MapCollections.this.toArrayHelper(tArr, 1);
                    }
                }

/* 454 */       public static <K, V> boolean containsAllHelper(Map<K, V> map, Collection<?> collection) {
/* 455 */           Iterator<?> it = collection.iterator();
/* 456 */           while (it.hasNext()) {
/* 457 */               if (!map.containsKey(it.next())) {
/* 458 */                   return false;
                        }
                    }
                    return true;
                }

/* 464 */       public static <K, V> boolean removeAllHelper(Map<K, V> map, Collection<?> collection) {
/* 465 */           int oldSize = map.size();
/* 466 */           Iterator<?> it = collection.iterator();
/* 467 */           while (it.hasNext()) {
/* 468 */               map.remove(it.next());
                    }
                    return oldSize != map.size();
                }

/* 473 */       public static <K, V> boolean retainAllHelper(Map<K, V> map, Collection<?> collection) {
/* 474 */           int oldSize = map.size();
/* 475 */           Iterator<K> it = map.keySet().iterator();
/* 476 */           while (it.hasNext()) {
/* 477 */               if (!collection.contains(it.next())) {
/* 478 */                   it.remove();
                        }
                    }
                    return oldSize != map.size();
                }

/* 485 */       public Object[] toArrayHelper(int offset) {
/* 486 */           int N = colGetSize();
/* 487 */           Object[] result = new Object[N];
                    for (int i = 0; i < N; i++) {
/* 489 */               result[i] = colGetEntry(i, offset);
                    }
/* 491 */           return result;
                }

/* 494 */       public <T> T[] toArrayHelper(T[] tArr, int i) {
/* 495 */           int iColGetSize = colGetSize();
/* 496 */           if (tArr.length < iColGetSize) {
/* 499 */               tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), iColGetSize));
                    }
                    for (int i2 = 0; i2 < iColGetSize; i2++) {
/* 502 */               tArr[i2] = colGetEntry(i2, i);
                    }
/* 504 */           if (tArr.length > iColGetSize) {
/* 505 */               tArr[iColGetSize] = null;
                    }
                    return tArr;
                }

/* 510 */       public static <T> boolean equalsSetHelper(Set<T> set, Object object) {
/* 511 */           if (set == object) {
                        return true;
                    }
/* 514 */           if (!(object instanceof Set)) {
                        return false;
                    }
/* 515 */           Set<?> s = (Set) object;
                    try {
/* 518 */               return set.size() == s.size() && set.containsAll(s);
                    } catch (ClassCastException e) {
                        return false;
                    } catch (NullPointerException e2) {
                        return false;
                    }
                }

/* 528 */       public Set<Map.Entry<K, V>> getEntrySet() {
/* 529 */           if (this.mEntrySet == null) {
/* 530 */               this.mEntrySet = new EntrySet();
                    }
/* 532 */           return this.mEntrySet;
                }

/* 535 */       public Set<K> getKeySet() {
/* 536 */           if (this.mKeySet == null) {
/* 537 */               this.mKeySet = new KeySet();
                    }
/* 539 */           return this.mKeySet;
                }

/* 542 */       public Collection<V> getValues() {
/* 543 */           if (this.mValues == null) {
/* 544 */               this.mValues = new ValuesCollection();
                    }
/* 546 */           return this.mValues;
                }
            }
