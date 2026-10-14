            package com.crashlytics.android.internal;

            import com.crashlytics.android.Crashlytics;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aW */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class C0676aW {

                /* JADX INFO: renamed from: a */
                private C0679aZ f331a;

                /* JADX INFO: renamed from: b */
                private final C0678aY f332b;

                /* JADX INFO: renamed from: c */
                private final C0687ah f333c;

                /* JADX INFO: renamed from: d */
                private final C0667aN f334d;

                /* JADX INFO: renamed from: e */
                private final InterfaceC0707ba f335e;

/* 26 */        public C0676aW(C0679aZ c0679aZ, C0687ah c0687ah, C0678aY c0678aY, C0667aN c0667aN, InterfaceC0707ba interfaceC0707ba) {
/* 28 */            this.f331a = c0679aZ;
/* 29 */            this.f333c = c0687ah;
/* 30 */            this.f332b = c0678aY;
/* 31 */            this.f334d = c0667aN;
/* 32 */            this.f335e = interfaceC0707ba;
                }

                /* JADX INFO: renamed from: a */
/* 35 */        public C0677aX m191a() {
/* 36 */            return m192a(EnumC0675aV.USE_CACHE);
                }

                /* JADX INFO: renamed from: a */
/* 39 */        public C0677aX m192a(EnumC0675aV enumC0675aV) throws Throwable {
                    C0677aX c0677aX;
                    Exception e;
/* 40 */            C0677aX c0677aXM193a = null;
                    try {
/* 43 */                if (!C0727v.m357a().m368f()) {
/* 44 */                    c0677aXM193a = m190b(enumC0675aV);
                        }
/* 47 */                if (c0677aXM193a == null) {
                            try {
/* 48 */                        JSONObject jSONObjectMo179a = this.f335e.mo179a(this.f331a);
/* 50 */                        if (jSONObjectMo179a != null) {
/* 51 */                            c0677aXM193a = this.f332b.m193a(this.f333c, jSONObjectMo179a);
/* 52 */                            this.f334d.m178a(c0677aXM193a.f341f, jSONObjectMo179a);
/* 53 */                            m189a(jSONObjectMo179a, "Loaded settings: ");
                                }
                            } catch (Exception e2) {
                                c0677aX = c0677aXM193a;
                                e = e2;
/* 62 */                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", e);
/* 64 */                        return c0677aX;
                            }
                        }
                        c0677aX = c0677aXM193a;
/* 58 */                if (c0677aX == null) {
                            try {
/* 59 */                        return m190b(EnumC0675aV.IGNORE_CACHE_EXPIRATION);
                            } catch (Exception e3) {
                                e = e3;
/* 62 */                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", e);
/* 64 */                        return c0677aX;
                            }
                        }
/* 64 */                return c0677aX;
                    } catch (Exception e4) {
/* 40 */                c0677aX = null;
                        e = e4;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 70 */        private C0677aX m190b(EnumC0675aV enumC0675aV) throws Throwable {
                    C0677aX c0677aXM193a;
                    Exception e;
                    try {
/* 71 */                if (!EnumC0675aV.SKIP_CACHE_LOOKUP.equals(enumC0675aV)) {
/* 72 */                    JSONObject jSONObjectM177a = this.f334d.m177a();
/* 74 */                    if (jSONObjectM177a != null) {
/* 75 */                        c0677aXM193a = this.f332b.m193a(this.f333c, jSONObjectM177a);
/* 77 */                        if (c0677aXM193a != null) {
/* 78 */                            m189a(jSONObjectM177a, "Loaded cached settings: ");
/* 80 */                            long jM240a = this.f333c.m240a();
/* 82 */                            if (!EnumC0675aV.IGNORE_CACHE_EXPIRATION.equals(enumC0675aV)) {
/* 82 */                                if (c0677aXM193a.f341f < jM240a) {
/* 86 */                                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Cached settings have expired.");
                                        }
                                    }
                                    try {
/* 84 */                                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Returning cached settings.");
/* 99 */                                return c0677aXM193a;
                                    } catch (Exception e2) {
/* 95 */                                e = e2;
/* 96 */                                C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to get cached settings", e);
/* 99 */                                return c0677aXM193a;
                                    }
                                }
/* 89 */                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to transform cached settings data.", (Throwable) null);
                                return null;
                            }
/* 92 */                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "No cached settings data found.");
                        }
                        return null;
                    } catch (Exception e3) {
                        c0677aXM193a = null;
                        e = e3;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 102 */       private void m189a(JSONObject jSONObject, String str) throws JSONException {
/* 103 */           if (!C0681ab.m227e(C0727v.m357a().getContext())) {
/* 104 */               jSONObject = this.f332b.m194a(jSONObject);
                    }
/* 107 */           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, str + jSONObject.toString());
                }
            }
