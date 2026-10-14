            package com.crashlytics.android.internal;

            import android.content.Context;
            import com.crashlytics.android.Crashlytics;
            import java.security.GeneralSecurityException;
            import java.util.Locale;
            import java.util.Map;
            import java.util.concurrent.atomic.AtomicReference;
            import javax.crypto.Cipher;
            import org.json.JSONObject;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aS */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public final class C0672aS {

                /* JADX INFO: renamed from: a */
                private final AtomicReference<C0677aX> f323a;

                /* JADX INFO: renamed from: b */
                private C0676aW f324b;

                /* JADX INFO: renamed from: c */
                private boolean f325c;

/* 23 */        /* synthetic */ C0672aS(byte b) {
/* 24 */            this();
                }

                /* JADX INFO: renamed from: a */
/* 41 */        public static C0672aS m180a() {
/* 42 */            return C0673aT.m187a();
                }

/* 45 */        private C0672aS() {
/* 29 */            this.f323a = new AtomicReference<>();
/* 31 */            this.f325c = false;
                }

                /* JADX INFO: renamed from: a */
/* 58 */        public final synchronized C0672aS m182a(Context context, C0701av c0701av, String str, String str2, String str3) {
                    try {
                        if (!this.f325c) {
/* 63 */                    if (this.f324b == null) {
/* 64 */                        String strM354a = C0723r.m354a(context, false);
/* 65 */                        String packageName = context.getPackageName();
/* 67 */                        String installerPackageName = context.getPackageManager().getInstallerPackageName(packageName);
/* 84 */                        this.f324b = new C0676aW(new C0679aZ(strM354a, m181a(strM354a, packageName, context), C0681ab.m208a(C0681ab.m233i(context)), str2, str, EnumC0688ai.m241a(installerPackageName).m242a(), C0681ab.m231g(context)), new C0687ah(), new C0678aY(), new C0667aN(), new C0668aO(str3, String.format(Locale.US, "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings", packageName), c0701av));
                            }
/* 89 */                    this.f325c = true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this;
                }

                /* JADX INFO: renamed from: b */
/* 93 */        public final C0677aX m184b() {
/* 94 */            return this.f323a.get();
                }

                /* JADX INFO: renamed from: a */
/* 115 */       public final <T> T m183a(InterfaceC0674aU<T> interfaceC0674aU, T t) {
/* 116 */           C0677aX c0677aX = this.f323a.get();
                    return c0677aX == null ? t : interfaceC0674aU.mo188a(c0677aX);
                }

                /* JADX INFO: renamed from: c */
/* 124 */       public final synchronized boolean m185c() {
                    C0677aX c0677aXM191a;
                    try {
                        c0677aXM191a = this.f324b.m191a();
/* 126 */               this.f323a.set(c0677aXM191a);
                    } catch (Throwable th) {
                        throw th;
                    }
/* 127 */           return c0677aXM191a != null;
                }

                /* JADX INFO: renamed from: d */
/* 134 */       public final synchronized boolean m186d() {
                    C0677aX c0677aXM192a;
                    try {
                        c0677aXM192a = this.f324b.m192a(EnumC0675aV.SKIP_CACHE_LOOKUP);
/* 136 */               this.f323a.set(c0677aXM192a);
/* 138 */               if (c0677aXM192a == null) {
/* 139 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to force reload of settings from Crashlytics.", (Throwable) null);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 142 */           return c0677aXM192a != null;
                }

                /* JADX INFO: renamed from: a */
/* 157 */       private static String m181a(String str, String str2, Context context) {
                    try {
/* 164 */               Cipher cipherM219b = C0681ab.m219b(1, C0681ab.m205a(str + str2.replaceAll("\\.", new StringBuffer("slc").reverse().toString())));
/* 170 */               JSONObject jSONObject = new JSONObject();
/* 171 */               C0694ao c0694ao = new C0694ao(context);
                        try {
/* 173 */                   jSONObject.put("APPLICATION_INSTALLATION_UUID".toLowerCase(Locale.US), c0694ao.m259b());
                        } catch (Exception e) {
/* 175 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not write application id to JSON", e);
                        }
                        for (Map.Entry<EnumC0695ap, String> entry : c0694ao.m263f().entrySet()) {
                            try {
/* 181 */                       jSONObject.put(entry.getKey().name().toLowerCase(Locale.US), entry.getValue());
                            } catch (Exception e2) {
/* 183 */                       C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not write value to JSON: " + entry.getKey().name(), e2);
                            }
                        }
                        try {
/* 189 */                   jSONObject.put("os_version", c0694ao.m260c());
                        } catch (Exception e3) {
/* 191 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not write OS version to JSON", e3);
                        }
                        try {
/* 196 */                   jSONObject.put("model", c0694ao.m261d());
                        } catch (Exception e4) {
/* 198 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not write model to JSON", e4);
                        }
/* 203 */               if (jSONObject.length() <= 0) {
/* 202 */                   return "";
                        }
                        try {
/* 205 */                   return C0681ab.m206a(cipherM219b.doFinal(jSONObject.toString().getBytes()));
                        } catch (GeneralSecurityException e5) {
/* 207 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not encrypt IDs", e5);
/* 202 */                   return "";
                        }
                    } catch (GeneralSecurityException e6) {
/* 167 */               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not create cipher to encrypt headers.", e6);
/* 168 */               return "";
                    }
                }
            }
