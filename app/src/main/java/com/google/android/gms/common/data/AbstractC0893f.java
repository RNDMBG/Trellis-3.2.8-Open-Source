            package com.google.android.gms.common.data;

            import java.util.ArrayList;
            
            
            
            public abstract class AbstractC0893f<T> extends DataBuffer<T> {

                
                private boolean f973jA;

                
                private ArrayList<Integer> f974jB;

                protected AbstractC0893f(C0891d c0891d) {
                    super(c0891d);
                    this.f973jA = false;
                }

                
                private void m1056aN() {
                    synchronized (this) {
                        try {
                            if (!this.f973jA) {
                                int count = this.f948jf.getCount();
                                this.f974jB = new ArrayList<>();
                                if (count > 0) {
                                    this.f974jB.add(0);
                                    String primaryDataMarkerColumn = getPrimaryDataMarkerColumn();
                                    String strM1045c = this.f948jf.m1045c(primaryDataMarkerColumn, 0, this.f948jf.m1050q(0));
                                    int i = 1;
                                    while (i < count) {
                                        String strM1045c2 = this.f948jf.m1045c(primaryDataMarkerColumn, i, this.f948jf.m1050q(i));
                                        if (strM1045c2.equals(strM1045c)) {
                                            strM1045c2 = strM1045c;
                                        } else {
                                            this.f974jB.add(Integer.valueOf(i));
                                        }
                                        i++;
                                        strM1045c = strM1045c2;
                                    }
                                }
                                this.f973jA = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
                private int m1057u(int i) {
                    if (i < 0 || i == this.f974jB.size()) {
                        return 0;
                    }
                    return i == this.f974jB.size() + (-1) ? this.f948jf.getCount() - this.f974jB.get(i).intValue() : this.f974jB.get(i + 1).intValue() - this.f974jB.get(i).intValue();
                }

                
                protected abstract T mo1058a(int i, int i2);

                @Override 
                public final T get(int position) {
                    m1056aN();
                    return mo1058a(m1059t(position), m1057u(position));
                }

                @Override 
                public int getCount() {
                    m1056aN();
                    return this.f974jB.size();
                }

                protected abstract String getPrimaryDataMarkerColumn();

                
                int m1059t(int i) {
                    if (i < 0 || i >= this.f974jB.size()) {
                        throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
                    }
                    return this.f974jB.get(i).intValue();
                }
            }
