            package com.google.android.gms.internal;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.IBinder;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.AbstractC0907e;
            import com.google.android.gms.dynamic.BinderC0905c;
            
            
            
            public final class C0974bm extends AbstractC0907e<InterfaceC0976bo> {

                
                private static final C0974bm f1467gl = new C0974bm();

                
                private static final class a extends Exception {
                    public a(String str) {
                        super(str);
                    }
                }

                private C0974bm() {
                    super("com.google.android.gms.ads.AdOverlayCreatorImpl");
                }

                
                public static InterfaceC0975bn m1338a(Activity activity) {
                    InterfaceC0975bn interfaceC0975bnM1340c;
                    try {
                        if (m1339b(activity)) {
                            C1002cn.m1454m("Using AdOverlay from the client jar.");
                            interfaceC0975bnM1340c = new BinderC0967bf(activity);
                        } else {
                            interfaceC0975bnM1340c = f1467gl.m1340c(activity);
                        }
                        return interfaceC0975bnM1340c;
                    } catch (a e) {
                        C1002cn.m1458q(e.getMessage());
                        return null;
                    }
                }

                
                private static boolean m1339b(Activity activity) throws a {
                    Intent intent = activity.getIntent();
                    if (intent.hasExtra("com.google.android.gms.ads.internal.overlay.useClientJar")) {
                        return intent.getBooleanExtra("com.google.android.gms.ads.internal.overlay.useClientJar", false);
                    }
                    throw new a("Ad overlay requires the useClientJar flag in intent extras.");
                }

                
                private InterfaceC0975bn m1340c(Activity activity) {
                    try {
                        return InterfaceC0975bn.a.m1342m(m1168t(activity).mo1343a(BinderC0905c.m1166g(activity)));
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not create remote AdOverlay.", e);
                        return null;
                    } catch (AbstractC0907e.a e2) {
                        C1002cn.m1452b("Could not create remote AdOverlay.", e2);
                        return null;
                    }
                }

                @Override 
                
                protected  InterfaceC0976bo mo1167d(IBinder iBinder) {
                    return m1341l(iBinder);
                }

                
                protected InterfaceC0976bo m1341l(IBinder iBinder) {
                    return InterfaceC0976bo.a.m1344n(iBinder);
                }
            }
