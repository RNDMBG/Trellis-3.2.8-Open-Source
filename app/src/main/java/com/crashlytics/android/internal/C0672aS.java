            package com.crashlytics.android.internal;

            import android.content.Context;
            import com.crashlytics.android.Crashlytics;
            import java.security.GeneralSecurityException;
            import java.util.Locale;
            import java.util.Map;
            import java.util.concurrent.atomic.AtomicReference;
            import javax.crypto.Cipher;
            import org.json.JSONObject;
            
            
            
    public final class C0672aS {

                
                private final AtomicReference<C0677aX> f323a;

                
                private C0676aW f324b;

                
                private boolean f325c;

         C0672aS(byte b) {
            this();
                }

                
        public static C0672aS m180a() {
            return C0673aT.m187a();
                }

        private C0672aS() {
            this.f323a = new AtomicReference<>();
            this.f325c = false;
                }

                
        public final synchronized C0672aS m182a(Context context, C0701av c0701av, String str, String str2, String str3) {
                    try {
                        if (!this.f325c) {
                    if (this.f324b == null) {
                        String strM354a = C0723r.m354a(context, false);
                        String packageName = context.getPackageName();
                        String installerPackageName = context.getPackageManager().getInstallerPackageName(packageName);
                        this.f324b = new C0676aW(new C0679aZ(strM354a, m181a(strM354a, packageName, context), C0681ab.m208a(C0681ab.m233i(context)), str2, str, EnumC0688ai.m241a(installerPackageName).m242a(), C0681ab.m231g(context)), new C0687ah(), new C0678aY(), new C0667aN(), new C0668aO(str3, String.format(Locale.US, "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings", packageName), c0701av));
                            }
                    this.f325c = true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this;
                }

                
        public final C0677aX m184b() {
            return this.f323a.get();
                }

                
       public final <T> T m183a(InterfaceC0674aU<T> interfaceC0674aU, T t) {
           C0677aX c0677aX = this.f323a.get();
                    return c0677aX == null ? t : interfaceC0674aU.mo188a(c0677aX);
                }

                
       public final synchronized boolean m185c() {
                    C0677aX c0677aXM191a;
                    try {
                        c0677aXM191a = this.f324b.m191a();
               this.f323a.set(c0677aXM191a);
                    } catch (Throwable th) {
                        throw th;
                    }
           return c0677aXM191a != null;
                }

                
       public final synchronized boolean m186d() {
                    C0677aX c0677aXM192a;
                    try {
                        c0677aXM192a = this.f324b.m192a(EnumC0675aV.SKIP_CACHE_LOOKUP);
               this.f323a.set(c0677aXM192a);
               if (c0677aXM192a == null) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to force reload of settings from Crashlytics.", (Throwable) null);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
           return c0677aXM192a != null;
                }

                
       private static String m181a(String str, String str2, Context context) {
                    try {
               Cipher cipherM219b = C0681ab.m219b(1, C0681ab.m205a(str + str2.replaceAll("\\.", new StringBuffer("slc").reverse().toString())));
               JSONObject jSONObject = new JSONObject();
               C0694ao c0694ao = new C0694ao(context);
                        try {
                   jSONObject.put("APPLICATION_INSTALLATION_UUID".toLowerCase(Locale.US), c0694ao.m259b());
                        } catch (Exception e) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not write application id to JSON", e);
                        }
                        for (Map.Entry<EnumC0695ap, String> entry : c0694ao.m263f().entrySet()) {
                            try {
                       jSONObject.put(entry.getKey().name().toLowerCase(Locale.US), entry.getValue());
                            } catch (Exception e2) {
                       C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not write value to JSON: " + entry.getKey().name(), e2);
                            }
                        }
                        try {
                   jSONObject.put("os_version", c0694ao.m260c());
                        } catch (Exception e3) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not write OS version to JSON", e3);
                        }
                        try {
                   jSONObject.put("model", c0694ao.m261d());
                        } catch (Exception e4) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not write model to JSON", e4);
                        }
               if (jSONObject.length() <= 0) {
                   return "";
                        }
                        try {
                   return C0681ab.m206a(cipherM219b.doFinal(jSONObject.toString().getBytes()));
                        } catch (GeneralSecurityException e5) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not encrypt IDs", e5);
                   return "";
                        }
                    } catch (GeneralSecurityException e6) {
               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not create cipher to encrypt headers.", e6);
               return "";
                    }
                }
            }
