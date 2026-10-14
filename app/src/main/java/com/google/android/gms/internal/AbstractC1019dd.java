            package com.google.android.gms.internal;

            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            
            public abstract class AbstractC1019dd implements SafeParcelable {

                
                private boolean f1675kC = false;

                
                private static final Object f1674kz = new Object();

                
                private static ClassLoader f1672kA = null;

                
                private static Integer f1673kB = null;

                
                private static boolean m1528a(Class<?> cls) {
                    try {
                        return SafeParcelable.NULL.equals(cls.getField("NULL").get(null));
                    } catch (IllegalAccessException e) {
                        return false;
                    } catch (NoSuchFieldException e2) {
                        return false;
                    }
                }

                
                protected static ClassLoader m1529aV() {
                    ClassLoader classLoader;
                    synchronized (f1674kz) {
                        try {
                            classLoader = f1672kA;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return classLoader;
                }

                
                protected static Integer m1530aW() {
                    Integer num;
                    synchronized (f1674kz) {
                        try {
                            num = f1673kB;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return num;
                }

                
                protected static boolean m1531y(String str) {
                    ClassLoader classLoaderM1529aV = m1529aV();
                    if (classLoaderM1529aV == null) {
                        return true;
                    }
                    try {
                        return m1528a(classLoaderM1529aV.loadClass(str));
                    } catch (Exception e) {
                        return false;
                    }
                }

                
                protected boolean m1532aX() {
                    return this.f1675kC;
                }
            }
