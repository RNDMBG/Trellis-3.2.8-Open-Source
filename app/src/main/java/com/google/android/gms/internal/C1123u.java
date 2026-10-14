            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.IBinder;
            import android.os.RemoteException;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.dynamic.AbstractC0907e;
            import com.google.android.gms.dynamic.BinderC0905c;
            
            
            
            public final class C1123u extends AbstractC0907e<InterfaceC0938ad> {

                
                private static final C1123u f2158er = new C1123u();

                private C1123u() {
                    super("com.google.android.gms.ads.AdManagerCreatorImpl");
                }

                
                public static InterfaceC0937ac m2172a(Context context, C1126x c1126x, String str, BinderC0956av binderC0956av) {
                    InterfaceC0937ac interfaceC0937acM2173b;
                    if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0 && (interfaceC0937acM2173b = f2158er.m2173b(context, c1126x, str, binderC0956av)) != null) {
                        return interfaceC0937acM2173b;
                    }
                    C1002cn.m1454m("Using AdManager from the client jar.");
                    return new BinderC1120r(context, c1126x, str, binderC0956av, new C1003co(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, true));
                }

                
                private InterfaceC0937ac m2173b(Context context, C1126x c1126x, String str, BinderC0956av binderC0956av) {
                    try {
                        return InterfaceC0937ac.a.m1244f(m1168t(context).mo1245a(BinderC0905c.m1166g(context), c1126x, str, binderC0956av, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE));
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not create remote AdManager.", e);
                        return null;
                    } catch (AbstractC0907e.a e2) {
                        C1002cn.m1452b("Could not create remote AdManager.", e2);
                        return null;
                    }
                }

                
                protected InterfaceC0938ad m2174c(IBinder iBinder) {
                    return InterfaceC0938ad.a.m1246g(iBinder);
                }

                @Override 
                
                protected  InterfaceC0938ad mo1167d(IBinder iBinder) {
                    return m2174c(iBinder);
                }
            }
