            package com.google.android.gms.internal;

            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.List;
            
            
            
            public final class C1027dl {

                
                public static final class a {

                    
                    private final List<String> f1717lj;

                    
                    private final Object f1718lk;

                    private a(Object obj) {
                        this.f1718lk = C1028dm.m1605e(obj);
                        this.f1717lj = new ArrayList();
                    }

                     a(Object obj, Object anonymousClass1) {
                        this(obj);
                    }

                    
                    public a m1600a(String str, Object obj) {
                        this.f1717lj.add(((String) C1028dm.m1605e(str)) + "=" + String.valueOf(obj));
                        return this;
                    }

                    public String toString() {
                        StringBuilder sbAppend = new StringBuilder(100).append(this.f1718lk.getClass().getSimpleName()).append('{');
                        int size = this.f1717lj.size();
                        for (int i = 0; i < size; i++) {
                            sbAppend.append(this.f1717lj.get(i));
                            if (i < size - 1) {
                                sbAppend.append(", ");
                            }
                        }
                        return sbAppend.append('}').toString();
                    }
                }

                
                public static a m1599d(Object obj) {
                    return new a(obj, null);
                }

                public static boolean equal(Object a2, Object b) {
                    return a2 == b || (a2 != null && a2.equals(b));
                }

                public static int hashCode(Object... objects) {
                    return Arrays.hashCode(objects);
                }
            }
