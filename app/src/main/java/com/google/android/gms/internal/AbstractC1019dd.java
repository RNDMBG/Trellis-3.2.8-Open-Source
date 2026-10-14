            package com.google.android.gms.internal;

            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dd */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class AbstractC1019dd implements SafeParcelable {

                /* JADX INFO: renamed from: kC */
                private boolean f1675kC = false;

                /* JADX INFO: renamed from: kz */
                private static final Object f1674kz = new Object();

                /* JADX INFO: renamed from: kA */
                private static ClassLoader f1672kA = null;

                /* JADX INFO: renamed from: kB */
                private static Integer f1673kB = null;

                /* JADX INFO: renamed from: a */
                private static boolean m1528a(Class<?> cls) {
                    try {
                        return SafeParcelable.NULL.equals(cls.getField("NULL").get(null));
                    } catch (IllegalAccessException e) {
                        return false;
                    } catch (NoSuchFieldException e2) {
                        return false;
                    }
                }

                /* JADX INFO: renamed from: aV */
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

                /* JADX INFO: renamed from: aW */
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

                /* JADX INFO: renamed from: y */
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

                /* JADX INFO: renamed from: aX */
                protected boolean m1532aX() {
                    return this.f1675kC;
                }
            }
