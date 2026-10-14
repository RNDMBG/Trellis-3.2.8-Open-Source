            package com.google.android.gms.common.data;

            import java.util.Iterator;
            
            
            public abstract class DataBuffer<T> implements Iterable<T> {

                
                protected final C0891d f948jf;

                protected DataBuffer(C0891d dataHolder) {
                    this.f948jf = dataHolder;
                    if (this.f948jf != null) {
                        this.f948jf.m1044b(this);
                    }
                }

                public void close() {
                    this.f948jf.close();
                }

                public int describeContents() {
                    return 0;
                }

                public abstract T get(int i);

                public int getCount() {
                    return this.f948jf.getCount();
                }

                public boolean isClosed() {
                    return this.f948jf.isClosed();
                }

                @Override 
                public Iterator<T> iterator() {
                    return new C0888a(this);
                }
            }
