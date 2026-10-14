            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.IBinder;
            import android.view.View;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.dynamic.BinderC0905c;
            import com.google.android.gms.plus.PlusOneDummyView;
            
            
            
            public final class C1082fm {

                
                private static Context f1933pW;

                
                private static InterfaceC1078fi f1934rx;

                
                public static class a extends Exception {
                    public a(String str) {
                        super(str);
                    }
                }

                
                public static View m1979a(Context context, int i, int i2, String str, int i3) {
                    try {
                        if (str == null) {
                            throw new NullPointerException();
                        }
                        return (View) BinderC0905c.m1165b(m1981x(context).mo1955a(BinderC0905c.m1166g(context), i, i2, str, i3));
                    } catch (Exception e) {
                        return new PlusOneDummyView(context, i);
                    }
                }

                
                public static View m1980a(Context context, int i, int i2, String str, String str2) {
                    try {
                        if (str == null) {
                            throw new NullPointerException();
                        }
                        return (View) BinderC0905c.m1165b(m1981x(context).mo1956a(BinderC0905c.m1166g(context), i, i2, str, str2));
                    } catch (Exception e) {
                        return new PlusOneDummyView(context, i);
                    }
                }

                
                private static InterfaceC1078fi m1981x(Context context) throws a {
                    C1028dm.m1605e(context);
                    if (f1934rx == null) {
                        if (f1933pW == null) {
                            f1933pW = GooglePlayServicesUtil.getRemoteContext(context);
                            if (f1933pW == null) {
                                throw new a("Could not get remote context.");
                            }
                        }
                        try {
                            f1934rx = InterfaceC1078fi.a.m1957ao((IBinder) f1933pW.getClassLoader().loadClass("com.google.android.gms.plus.plusone.PlusOneButtonCreatorImpl").newInstance());
                        } catch (ClassNotFoundException e) {
                            throw new a("Could not load creator class.");
                        } catch (IllegalAccessException e2) {
                            throw new a("Could not access creator.");
                        } catch (InstantiationException e3) {
                            throw new a("Could not instantiate creator.");
                        }
                    }
                    return f1934rx;
                }
            }
