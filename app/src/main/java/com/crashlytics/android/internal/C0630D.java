            package com.crashlytics.android.internal;

            import android.app.Application;
            import android.content.Context;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.os.Build;
            import co.vine.android.util.CommonUtil;
            import com.crashlytics.android.Crashlytics;
            import java.io.File;
            
            
            
    public class C0630D extends AbstractC0726u {

                
                private String f213a;

                
                private String f214b;

                
                private String f215c;

                
                private C0694ao f216d;

                
                private C0663aJ f217e;

                
                private long f218f;

                
                private C0701av f219g;

                
                private C0641O f220h;

                
                
        static  void m104a(C0630D c0630d) {
                    boolean z;
            Context context = c0630d.getContext();
                    try {
                        C0637K c0637k = new C0637K(new C0650X(), new C0687ah(), new C0689aj(C0727v.m357a().m370h(), "session_analytics.tap", "session_analytics_to_send"));
                        String strM259b = c0630d.f216d.m259b();
                        String strM264g = c0630d.f216d.m264g();
                        String strM260c = c0630d.f216d.m260c();
                        String strM261d = c0630d.f216d.m261d();
                        Application applicationM366d = C0727v.m357a().m366d();
                        if (applicationM366d == null || Build.VERSION.SDK_INT < 14) {
                            c0630d.f220h = new C0641O(context.getPackageName(), strM259b, strM264g, strM260c, strM261d, c0630d.f214b, c0630d.f215c, c0637k, c0630d.f219g);
                        } else {
                            c0630d.f220h = new C0632F(applicationM366d, context.getPackageName(), strM259b, strM264g, strM260c, strM261d, c0630d.f214b, c0630d.f215c, c0637k, c0630d.f219g);
                        }
                        long j = c0630d.f218f;
                        if (c0630d.f217e.m174a().getBoolean("analytics_launched", false)) {
                            z = false;
                        } else {
                            if (System.currentTimeMillis() - j < CommonUtil.HOUR_MILLIS) {
                                z = true;
                            } else {
                                z = false;
                            }
                        }
                        if (z) {
                            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "First launch");
                            if (c0630d.f220h != null) {
                                c0630d.f220h.m142b();
                                c0630d.f217e.m175a(c0630d.f217e.m176b().putBoolean("analytics_launched", true));
                            }
                        }
                    } catch (Exception e) {
                        C0681ab.m223d("Crashlytics failed to initialize session analytics.");
                    }
                    try {
                        C0672aS.m180a().m182a(context, c0630d.f219g, c0630d.f214b, c0630d.f215c, c0630d.m105b()).m185c();
                        C0677aX c0677aXM184b = C0672aS.m180a().m184b();
                        if (c0677aXM184b != null) {
                            if (c0677aXM184b.f339d.f314c) {
                                c0630d.f220h.m140a(c0677aXM184b.f340e, c0630d.m105b());
                            } else {
                                C0681ab.m221c("Disabling analytics collection based on settings flag value.");
                                c0630d.f220h.mo108a();
                            }
                        }
                    } catch (Exception e2) {
                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Error dealing with settings", e2);
                    }
                }

                
        public static C0630D m103a() {
            return (C0630D) C0727v.m357a().m361a(C0630D.class);
                }

                @Override 
                
        protected final void mo28c() {
                    try {
                this.f219g = new C0701av(C0727v.m357a().m365b());
                this.f217e = new C0663aJ(C0727v.m357a().m361a(C0630D.class));
                Context context = getContext();
                PackageManager packageManager = context.getPackageManager();
                this.f216d = new C0694ao(context);
                this.f213a = context.getPackageName();
                PackageInfo packageInfo = packageManager.getPackageInfo(this.f213a, 0);
                this.f214b = Integer.toString(packageInfo.versionCode);
                this.f215c = packageInfo.versionName == null ? "0.0" : packageInfo.versionName;
                if (Build.VERSION.SDK_INT >= 9) {
                    this.f218f = packageInfo.firstInstallTime;
                        } else {
                    this.f218f = new File(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir).lastModified();
                        }
                    } catch (Exception e) {
                C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Error setting up app properties", e);
                    }
            new Thread(new C0631E(this), "Crashlytics Initializer").start();
                }

                @Override 
       public String getVersion() {
           return C0727v.m357a().getVersion();
                }

                
       private String m105b() {
           return C0681ab.m202a(getContext(), "com.crashlytics.ApiEndpoint");
                }

                
       public final void m107a(C0686ag c0686ag) {
           if (this.f220h != null) {
               this.f220h.m144b(c0686ag.m235a());
                    }
                }

                
       public final void m106a(C0685af c0685af) {
           if (this.f220h != null) {
               this.f220h.m141a(c0685af.m235a());
                    }
                }
            }
