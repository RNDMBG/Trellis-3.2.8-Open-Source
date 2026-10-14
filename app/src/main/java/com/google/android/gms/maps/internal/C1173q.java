            package com.google.android.gms.maps.internal;

            import android.content.Context;
            import android.os.IBinder;
            import android.os.RemoteException;
            import android.util.Log;
            import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.model.RuntimeRemoteException;
            
            
            
            public class C1173q {

                
                private static Context f2279pW;

                
                private static InterfaceC1159c f2280pX;

                
                private static <T> T m2258a(ClassLoader classLoader, String str) {
                    try {
                        return (T) m2259c(((ClassLoader) C1028dm.m1605e(classLoader)).loadClass(str));
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException("Unable to find dynamic class " + str);
                    }
                }

                
                private static <T> T m2259c(Class<?> cls) {
                    try {
                        return (T) cls.newInstance();
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Unable to call the default constructor of " + cls.getName());
                    } catch (InstantiationException e2) {
                        throw new IllegalStateException("Unable to instantiate the dynamic class " + cls.getName());
                    }
                }

                
                private static boolean m2260cI() {
                    return m2261cJ() != null;
                }

                
                private static Class<?> m2261cJ() {
                    try {
                        return Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                }

                private static Context getRemoteContext(Context context) {
                    if (f2279pW == null) {
                        if (m2260cI()) {
                            f2279pW = context;
                        } else {
                            f2279pW = GooglePlayServicesUtil.getRemoteContext(context);
                        }
                    }
                    return f2279pW;
                }

                
                public static InterfaceC1159c m2262u(Context context) throws GooglePlayServicesNotAvailableException {
                    C1028dm.m1605e(context);
                    if (f2280pX != null) {
                        return f2280pX;
                    }
                    m2263v(context);
                    f2280pX = m2264w(context);
                    try {
                        f2280pX.mo2238a(BinderC0905c.m1166g(getRemoteContext(context).getResources()), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
                        return f2280pX;
                    } catch (RemoteException e) {
                        throw new RuntimeRemoteException(e);
                    }
                }

                
                private static void m2263v(Context context) throws GooglePlayServicesNotAvailableException {
                    int iIsGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
                    switch (iIsGooglePlayServicesAvailable) {
                        case 0:
                            return;
                        default:
                            throw new GooglePlayServicesNotAvailableException(iIsGooglePlayServicesAvailable);
                    }
                }

                
                private static InterfaceC1159c m2264w(Context context) {
                    if (!m2260cI()) {
                        return InterfaceC1159c.a.m2243J((IBinder) m2258a(getRemoteContext(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
                    }
                    Log.i(C1173q.class.getSimpleName(), "Making Creator statically");
                    return (InterfaceC1159c) m2259c(m2261cJ());
                }
            }
