            package com.google.android.gms.common.data;

            import com.google.android.gms.internal.C1028dm;
            import java.util.Iterator;
            import java.util.NoSuchElementException;
            
            
            
            public final class C0888a<T> implements Iterator<T> {

                
                private final DataBuffer<T> f949jg;

                
                private int f950jh = -1;

                public C0888a(DataBuffer<T> dataBuffer) {
                    this.f949jg = (DataBuffer) C1028dm.m1605e(dataBuffer);
                }

                @Override 
                public boolean hasNext() {
                    return this.f950jh < this.f949jg.getCount() + (-1);
                }

                @Override 
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException("Cannot advance the iterator beyond " + this.f950jh);
                    }
                    DataBuffer<T> dataBuffer = this.f949jg;
                    int i = this.f950jh + 1;
                    this.f950jh = i;
                    return dataBuffer.get(i);
                }

                @Override 
                public void remove() {
                    throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
                }
            }
