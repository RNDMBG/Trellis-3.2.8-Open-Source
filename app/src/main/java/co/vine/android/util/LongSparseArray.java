            package co.vine.android.util;

            import java.io.Serializable;
            import java.util.Collection;
            import java.util.Iterator;
            
            
    public class LongSparseArray<T> extends android.support.v4.util.LongSparseArray<T> implements Iterable<Long>, Serializable {
        public LongSparseArray() {
                }

        public LongSparseArray(int initialCapacity) {
            super(initialCapacity);
                }

        public boolean isEmpty() {
                    return size() == 0;
                }

        public T removeKey(long key) {
            T value = get(key);
            if (value != null) {
                super.remove(key);
                    }
            return value;
                }

        public void removeAll(Collection<Long> keysToRemove) {
                    for (Long key : keysToRemove) {
                delete(key.longValue());
                    }
                }

                @Override 
        public Iterator<Long> iterator() {
            return new SparseKeyIterator();
                }

        public Iterator<T> valueIterator() {
            return new SparseValueIterator();
                }

        public class SparseValueIterator implements Iterator<T> {
                    public int index;

            public SparseValueIterator() {
                    }

                    @Override 
            public boolean hasNext() {
                        return this.index < LongSparseArray.this.size();
                    }

                    @Override 
            public T next() {
                this.index++;
                return LongSparseArray.this.valueAt(this.index - 1);
                    }

                    @Override 
            public void remove() {
                throw new UnsupportedOperationException();
                    }
                }

        public class SparseKeyIterator implements Iterator<Long> {
                    public int index;

            public SparseKeyIterator() {
                    }


                    @Override 
            public boolean hasNext() {
                        return this.index < LongSparseArray.this.size();
                    }

                    
                    @Override 
            public Long next() {
                this.index++;
                return Long.valueOf(LongSparseArray.this.keyAt(this.index - 1));
                    }

                    @Override 
            public void remove() {
                throw new UnsupportedOperationException();
                    }
                }
            }
