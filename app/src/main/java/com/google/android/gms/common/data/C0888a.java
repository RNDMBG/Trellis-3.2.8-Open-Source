            package com.google.android.gms.common.data;

            import com.google.android.gms.internal.C1028dm;
            import java.util.Iterator;
            import java.util.NoSuchElementException;
            
            /* JADX INFO: renamed from: com.google.android.gms.common.data.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0888a<T> implements Iterator<T> {

                /* JADX INFO: renamed from: jg */
                private final DataBuffer<T> f949jg;

                /* JADX INFO: renamed from: jh */
                private int f950jh = -1;

                public C0888a(DataBuffer<T> dataBuffer) {
                    this.f949jg = (DataBuffer) C1028dm.m1605e(dataBuffer);
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.f950jh < this.f949jg.getCount() + (-1);
                }

                @Override // java.util.Iterator
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException("Cannot advance the iterator beyond " + this.f950jh);
                    }
                    DataBuffer<T> dataBuffer = this.f949jg;
                    int i = this.f950jh + 1;
                    this.f950jh = i;
                    return dataBuffer.get(i);
                }

                @Override // java.util.Iterator
                public void remove() {
                    throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
                }
            }
