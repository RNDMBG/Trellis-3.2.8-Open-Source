            package co.vine.android.util;

            import java.io.Serializable;
            import java.util.Collection;
            import java.util.Iterator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class SparseArray<T> extends android.util.SparseArray<T> implements Iterable<Integer>, Serializable {
                @Override // java.lang.Iterable
/* 14 */        public Iterator<Integer> iterator() {
/* 15 */            return new SparseKeyIterator();
                }

/* 18 */        public Iterator<T> valueIterator() {
/* 19 */            return new SparseValueIterator();
                }

/* 22 */        public void removeAll(Collection<Integer> keysToRemove) {
                    for (Integer key : keysToRemove) {
/* 24 */                delete(key.intValue());
                    }
                }

/* 26 */        public class SparseValueIterator implements Iterator<T> {
                    public int index;

/* 27 */            public SparseValueIterator() {
                    }

                    @Override // java.util.Iterator
/* 33 */            public boolean hasNext() {
                        return this.index < SparseArray.this.size();
                    }

                    @Override // java.util.Iterator
/* 38 */            public T next() {
/* 39 */                this.index++;
/* 40 */                return SparseArray.this.valueAt(this.index - 1);
                    }

                    @Override // java.util.Iterator
/* 44 */            public void remove() {
/* 45 */                throw new UnsupportedOperationException();
                    }
                }

/* 47 */        public class SparseKeyIterator implements Iterator<Integer> {
                    public int index;

/* 48 */            public SparseKeyIterator() {
                    }


                    @Override // java.util.Iterator
/* 54 */            public boolean hasNext() {
                        return this.index < SparseArray.this.size();
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // java.util.Iterator
/* 59 */            public Integer next() {
/* 60 */                this.index++;
/* 61 */                return Integer.valueOf(SparseArray.this.keyAt(this.index - 1));
                    }

                    @Override // java.util.Iterator
/* 65 */            public void remove() {
/* 66 */                throw new UnsupportedOperationException();
                    }
                }
            }
