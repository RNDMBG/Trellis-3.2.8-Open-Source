            package android.support.v4.util;

            import java.lang.reflect.Array;
            import java.util.Collection;
            import java.util.Iterator;
            import java.util.Map;
            import java.util.Set;
            
            
    abstract class MapCollections<K, V> {
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

        MapCollections() {
                }

        final class ArrayIterator<T> implements Iterator<T> {
                    boolean mCanRemove = false;
                    int mIndex;
                    final int mOffset;
                    int mSize;

            ArrayIterator(int offset) {
                this.mOffset = offset;
                this.mSize = MapCollections.this.colGetSize();
                    }

                    @Override 
            public boolean hasNext() {
                        return this.mIndex < this.mSize;
                    }

                    @Override 
            public T next() {
                T t = (T) MapCollections.this.colGetEntry(this.mIndex, this.mOffset);
                this.mIndex++;
                this.mCanRemove = true;
                return t;
                    }

                    @Override 
            public void remove() {
                if (!this.mCanRemove) {
                    throw new IllegalStateException();
                        }
                this.mIndex--;
                this.mSize--;
                this.mCanRemove = false;
                MapCollections.this.colRemoveAt(this.mIndex);
                    }
                }

        final class MapIterator implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
                    int mEnd;
                    boolean mEntryValid = false;
                    int mIndex = -1;

                    @Override 
            public   Object next() {
                return next();
                    }

            MapIterator() {
                this.mEnd = MapCollections.this.colGetSize() - 1;
                    }

                    @Override 
            public boolean hasNext() {
                        return this.mIndex < this.mEnd;
                    }

                    @Override 
            public Map.Entry<K, V> next() {
                this.mIndex++;
                this.mEntryValid = true;
                return this;
                    }

                    @Override 
            public void remove() {
                if (!this.mEntryValid) {
                    throw new IllegalStateException();
                        }
                MapCollections.this.colRemoveAt(this.mIndex);
                this.mIndex--;
               this.mEnd--;
               this.mEntryValid = false;
                    }

                    @Override 
           public K getKey() {
               if (!this.mEntryValid) {
                   throw new IllegalStateException("This container does not support retaining Map.Entry objects");
                        }
               return (K) MapCollections.this.colGetEntry(this.mIndex, 0);
                    }

                    @Override 
           public V getValue() {
               if (!this.mEntryValid) {
                   throw new IllegalStateException("This container does not support retaining Map.Entry objects");
                        }
               return (V) MapCollections.this.colGetEntry(this.mIndex, 1);
                    }

                    @Override 
           public V setValue(V v) {
               if (!this.mEntryValid) {
                   throw new IllegalStateException("This container does not support retaining Map.Entry objects");
                        }
               return (V) MapCollections.this.colSetValue(this.mIndex, v);
                    }

                    @Override 
           public final boolean equals(Object o) {
               if (!this.mEntryValid) {
                   throw new IllegalStateException("This container does not support retaining Map.Entry objects");
                        }
               if (!(o instanceof Map.Entry)) {
                            return false;
                        }
               Map.Entry<?, ?> e = (Map.Entry) o;
               return ContainerHelpers.equal(e.getKey(), MapCollections.this.colGetEntry(this.mIndex, 0)) && ContainerHelpers.equal(e.getValue(), MapCollections.this.colGetEntry(this.mIndex, 1));
                    }

                    @Override 
           public final int hashCode() {
               if (!this.mEntryValid) {
                   throw new IllegalStateException("This container does not support retaining Map.Entry objects");
                        }
               Object key = MapCollections.this.colGetEntry(this.mIndex, 0);
               Object value = MapCollections.this.colGetEntry(this.mIndex, 1);
                        return (value != null ? value.hashCode() : 0) ^ (key == null ? 0 : key.hashCode());
                    }

           public final String toString() {
               return getKey() + "=" + getValue();
                    }
                }

       final class EntrySet implements Set<Map.Entry<K, V>> {
           EntrySet() {
                    }

                    @Override 
           public   boolean add(Object x0) {
               return add((Map.Entry) x0);
                    }

           public boolean add(Map.Entry<K, V> object) {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
               int oldSize = MapCollections.this.colGetSize();
                        for (Map.Entry<K, V> entry : collection) {
                   MapCollections.this.colPut(entry.getKey(), entry.getValue());
                        }
                        return oldSize != MapCollections.this.colGetSize();
                    }

                    @Override 
           public void clear() {
               MapCollections.this.colClear();
                    }

                    @Override 
           public boolean contains(Object o) {
               if (!(o instanceof Map.Entry)) {
                            return false;
                        }
               Map.Entry<?, ?> e = (Map.Entry) o;
               int index = MapCollections.this.colIndexOfKey(e.getKey());
               if (index < 0) {
                            return false;
                        }
               Object foundVal = MapCollections.this.colGetEntry(index, 1);
               return ContainerHelpers.equal(foundVal, e.getValue());
                    }

                    @Override 
           public boolean containsAll(Collection<?> collection) {
               Iterator<?> it = collection.iterator();
               while (it.hasNext()) {
                   if (!contains(it.next())) {
                       return false;
                            }
                        }
                        return true;
                    }

                    @Override 
           public boolean isEmpty() {
                        return MapCollections.this.colGetSize() == 0;
                    }

                    @Override 
           public Iterator<Map.Entry<K, V>> iterator() {
               return new MapIterator();
                    }

                    @Override 
           public boolean remove(Object object) {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public boolean removeAll(Collection<?> collection) {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public boolean retainAll(Collection<?> collection) {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public int size() {
               return MapCollections.this.colGetSize();
                    }

                    @Override 
           public Object[] toArray() {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public <T> T[] toArray(T[] array) {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public boolean equals(Object object) {
               return MapCollections.equalsSetHelper(this, object);
                    }

                    @Override 
           public int hashCode() {
               int result = 0;
                        for (int i = MapCollections.this.colGetSize() - 1; i >= 0; i--) {
                   Object key = MapCollections.this.colGetEntry(i, 0);
                   Object value = MapCollections.this.colGetEntry(i, 1);
                            result += (value == null ? 0 : value.hashCode()) ^ (key == null ? 0 : key.hashCode());
                        }
               return result;
                    }
                }

       final class KeySet implements Set<K> {
           KeySet() {
                    }

                    @Override 
           public boolean add(K object) {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public boolean addAll(Collection<? extends K> collection) {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public void clear() {
               MapCollections.this.colClear();
                    }

                    @Override 
           public boolean contains(Object object) {
                        return MapCollections.this.colIndexOfKey(object) >= 0;
                    }

                    @Override 
           public boolean containsAll(Collection<?> collection) {
               return MapCollections.containsAllHelper(MapCollections.this.colGetMap(), collection);
                    }

                    @Override 
           public boolean isEmpty() {
                        return MapCollections.this.colGetSize() == 0;
                    }

                    @Override 
           public Iterator<K> iterator() {
               return new ArrayIterator(0);
                    }

                    @Override 
           public boolean remove(Object object) {
               int index = MapCollections.this.colIndexOfKey(object);
               if (index < 0) {
                            return false;
                        }
               MapCollections.this.colRemoveAt(index);
               return true;
                    }

                    @Override 
           public boolean removeAll(Collection<?> collection) {
               return MapCollections.removeAllHelper(MapCollections.this.colGetMap(), collection);
                    }

                    @Override 
           public boolean retainAll(Collection<?> collection) {
               return MapCollections.retainAllHelper(MapCollections.this.colGetMap(), collection);
                    }

                    @Override 
           public int size() {
               return MapCollections.this.colGetSize();
                    }

                    @Override 
           public Object[] toArray() {
               return MapCollections.this.toArrayHelper(0);
                    }

                    @Override 
           public <T> T[] toArray(T[] tArr) {
               return (T[]) MapCollections.this.toArrayHelper(tArr, 0);
                    }

                    @Override 
           public boolean equals(Object object) {
               return MapCollections.equalsSetHelper(this, object);
                    }

                    @Override 
           public int hashCode() {
               int result = 0;
                        for (int i = MapCollections.this.colGetSize() - 1; i >= 0; i--) {
                   Object obj = MapCollections.this.colGetEntry(i, 0);
                   result += obj == null ? 0 : obj.hashCode();
                        }
               return result;
                    }
                }

       final class ValuesCollection implements Collection<V> {
           ValuesCollection() {
                    }

                    @Override 
           public boolean add(V object) {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public boolean addAll(Collection<? extends V> collection) {
               throw new UnsupportedOperationException();
                    }

                    @Override 
           public void clear() {
               MapCollections.this.colClear();
                    }

                    @Override 
           public boolean contains(Object object) {
                        return MapCollections.this.colIndexOfValue(object) >= 0;
                    }

                    @Override 
           public boolean containsAll(Collection<?> collection) {
               Iterator<?> it = collection.iterator();
               while (it.hasNext()) {
                   if (!contains(it.next())) {
                       return false;
                            }
                        }
                        return true;
                    }

                    @Override 
           public boolean isEmpty() {
                        return MapCollections.this.colGetSize() == 0;
                    }

                    @Override 
           public Iterator<V> iterator() {
               return new ArrayIterator(1);
                    }

                    @Override 
           public boolean remove(Object object) {
               int index = MapCollections.this.colIndexOfValue(object);
               if (index < 0) {
                            return false;
                        }
               MapCollections.this.colRemoveAt(index);
               return true;
                    }

                    @Override 
           public boolean removeAll(Collection<?> collection) {
               int N = MapCollections.this.colGetSize();
               boolean changed = false;
               int i = 0;
                        while (i < N) {
                   Object cur = MapCollections.this.colGetEntry(i, 1);
                   if (collection.contains(cur)) {
                       MapCollections.this.colRemoveAt(i);
                       i--;
                       N--;
                       changed = true;
                            }
                   i++;
                        }
               return changed;
                    }

                    @Override 
           public boolean retainAll(Collection<?> collection) {
               int N = MapCollections.this.colGetSize();
               boolean changed = false;
               int i = 0;
                        while (i < N) {
                   Object cur = MapCollections.this.colGetEntry(i, 1);
                   if (!collection.contains(cur)) {
                       MapCollections.this.colRemoveAt(i);
                       i--;
                       N--;
                       changed = true;
                            }
                   i++;
                        }
               return changed;
                    }

                    @Override 
           public int size() {
               return MapCollections.this.colGetSize();
                    }

                    @Override 
           public Object[] toArray() {
               return MapCollections.this.toArrayHelper(1);
                    }

                    @Override 
           public <T> T[] toArray(T[] tArr) {
               return (T[]) MapCollections.this.toArrayHelper(tArr, 1);
                    }
                }

       public static <K, V> boolean containsAllHelper(Map<K, V> map, Collection<?> collection) {
           Iterator<?> it = collection.iterator();
           while (it.hasNext()) {
               if (!map.containsKey(it.next())) {
                   return false;
                        }
                    }
                    return true;
                }

       public static <K, V> boolean removeAllHelper(Map<K, V> map, Collection<?> collection) {
           int oldSize = map.size();
           Iterator<?> it = collection.iterator();
           while (it.hasNext()) {
               map.remove(it.next());
                    }
                    return oldSize != map.size();
                }

       public static <K, V> boolean retainAllHelper(Map<K, V> map, Collection<?> collection) {
           int oldSize = map.size();
           Iterator<K> it = map.keySet().iterator();
           while (it.hasNext()) {
               if (!collection.contains(it.next())) {
                   it.remove();
                        }
                    }
                    return oldSize != map.size();
                }

       public Object[] toArrayHelper(int offset) {
           int N = colGetSize();
           Object[] result = new Object[N];
                    for (int i = 0; i < N; i++) {
               result[i] = colGetEntry(i, offset);
                    }
           return result;
                }

       public <T> T[] toArrayHelper(T[] tArr, int i) {
           int iColGetSize = colGetSize();
           if (tArr.length < iColGetSize) {
               tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), iColGetSize));
                    }
                    for (int i2 = 0; i2 < iColGetSize; i2++) {
               tArr[i2] = colGetEntry(i2, i);
                    }
           if (tArr.length > iColGetSize) {
               tArr[iColGetSize] = null;
                    }
                    return tArr;
                }

       public static <T> boolean equalsSetHelper(Set<T> set, Object object) {
           if (set == object) {
                        return true;
                    }
           if (!(object instanceof Set)) {
                        return false;
                    }
           Set<?> s = (Set) object;
                    try {
               return set.size() == s.size() && set.containsAll(s);
                    } catch (ClassCastException e) {
                        return false;
                    } catch (NullPointerException e2) {
                        return false;
                    }
                }

       public Set<Map.Entry<K, V>> getEntrySet() {
           if (this.mEntrySet == null) {
               this.mEntrySet = new EntrySet();
                    }
           return this.mEntrySet;
                }

       public Set<K> getKeySet() {
           if (this.mKeySet == null) {
               this.mKeySet = new KeySet();
                    }
           return this.mKeySet;
                }

       public Collection<V> getValues() {
           if (this.mValues == null) {
               this.mValues = new ValuesCollection();
                    }
           return this.mValues;
                }
            }
