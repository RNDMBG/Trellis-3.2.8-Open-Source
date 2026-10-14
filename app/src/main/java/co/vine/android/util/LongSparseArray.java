            package co.vine.android.util;

            import java.io.Serializable;
            import java.util.Collection;
            import java.util.Iterator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class LongSparseArray<T> extends android.support.v4.util.LongSparseArray<T> implements Iterable<Long>, Serializable {
/* 14 */        public LongSparseArray() {
                }

/* 18 */        public LongSparseArray(int initialCapacity) {
/* 19 */            super(initialCapacity);
                }

/* 22 */        public boolean isEmpty() {
                    return size() == 0;
                }

/* 27 */        public T removeKey(long key) {
/* 28 */            T value = get(key);
/* 30 */            if (value != null) {
/* 31 */                super.remove(key);
                    }
/* 34 */            return value;
                }

/* 37 */        public void removeAll(Collection<Long> keysToRemove) {
                    for (Long key : keysToRemove) {
/* 39 */                delete(key.longValue());
                    }
                }

                @Override // java.lang.Iterable
/* 44 */        public Iterator<Long> iterator() {
/* 45 */            return new SparseKeyIterator();
                }

/* 48 */        public Iterator<T> valueIterator() {
/* 49 */            return new SparseValueIterator();
                }

/* 50 */        public class SparseValueIterator implements Iterator<T> {
                    public int index;

/* 51 */            public SparseValueIterator() {
                    }

                    @Override // java.util.Iterator
/* 57 */            public boolean hasNext() {
                        return this.index < LongSparseArray.this.size();
                    }

                    @Override // java.util.Iterator
/* 62 */            public T next() {
/* 63 */                this.index++;
/* 64 */                return LongSparseArray.this.valueAt(this.index - 1);
                    }

                    @Override // java.util.Iterator
/* 68 */            public void remove() {
/* 69 */                throw new UnsupportedOperationException();
                    }
                }

/* 71 */        public class SparseKeyIterator implements Iterator<Long> {
                    public int index;

/* 72 */            public SparseKeyIterator() {
                    }


                    @Override // java.util.Iterator
/* 78 */            public boolean hasNext() {
                        return this.index < LongSparseArray.this.size();
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // java.util.Iterator
/* 83 */            public Long next() {
/* 84 */                this.index++;
/* 85 */                return Long.valueOf(LongSparseArray.this.keyAt(this.index - 1));
                    }

                    @Override // java.util.Iterator
/* 89 */            public void remove() {
/* 90 */                throw new UnsupportedOperationException();
                    }
                }
            }
