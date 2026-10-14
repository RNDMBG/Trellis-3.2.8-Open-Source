            package com.crashlytics.android.internal;

            import com.crashlytics.android.Crashlytics;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
            
    public class C0676aW {

                
                private C0679aZ f331a;

                
                private final C0678aY f332b;

                
                private final C0687ah f333c;

                
                private final C0667aN f334d;

                
                private final InterfaceC0707ba f335e;

        public C0676aW(C0679aZ c0679aZ, C0687ah c0687ah, C0678aY c0678aY, C0667aN c0667aN, InterfaceC0707ba interfaceC0707ba) {
            this.f331a = c0679aZ;
            this.f333c = c0687ah;
            this.f332b = c0678aY;
            this.f334d = c0667aN;
            this.f335e = interfaceC0707ba;
                }

                
        public C0677aX m191a() {
            return m192a(EnumC0675aV.USE_CACHE);
                }

                
        public C0677aX m192a(EnumC0675aV enumC0675aV) throws Throwable {
                    C0677aX c0677aX;
                    Exception e;
            C0677aX c0677aXM193a = null;
                    try {
                if (!C0727v.m357a().m368f()) {
                    c0677aXM193a = m190b(enumC0675aV);
                        }
                if (c0677aXM193a == null) {
                            try {
                        JSONObject jSONObjectMo179a = this.f335e.mo179a(this.f331a);
                        if (jSONObjectMo179a != null) {
                            c0677aXM193a = this.f332b.m193a(this.f333c, jSONObjectMo179a);
                            this.f334d.m178a(c0677aXM193a.f341f, jSONObjectMo179a);
                            m189a(jSONObjectMo179a, "Loaded settings: ");
                                }
                            } catch (Exception e2) {
                                c0677aX = c0677aXM193a;
                                e = e2;
                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", e);
                        return c0677aX;
                            }
                        }
                        c0677aX = c0677aXM193a;
                if (c0677aX == null) {
                            try {
                        return m190b(EnumC0675aV.IGNORE_CACHE_EXPIRATION);
                            } catch (Exception e3) {
                                e = e3;
                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", e);
                        return c0677aX;
                            }
                        }
                return c0677aX;
                    } catch (Exception e4) {
                c0677aX = null;
                        e = e4;
                    }
                }

                
        private C0677aX m190b(EnumC0675aV enumC0675aV) throws Throwable {
                    C0677aX c0677aXM193a;
                    Exception e;
                    try {
                if (!EnumC0675aV.SKIP_CACHE_LOOKUP.equals(enumC0675aV)) {
                    JSONObject jSONObjectM177a = this.f334d.m177a();
                    if (jSONObjectM177a != null) {
                        c0677aXM193a = this.f332b.m193a(this.f333c, jSONObjectM177a);
                        if (c0677aXM193a != null) {
                            m189a(jSONObjectM177a, "Loaded cached settings: ");
                            long jM240a = this.f333c.m240a();
                            if (!EnumC0675aV.IGNORE_CACHE_EXPIRATION.equals(enumC0675aV)) {
                                if (c0677aXM193a.f341f < jM240a) {
                                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Cached settings have expired.");
                                        }
                                    }
                                    try {
                                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Returning cached settings.");
                                return c0677aXM193a;
                                    } catch (Exception e2) {
                                e = e2;
                                C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to get cached settings", e);
                                return c0677aXM193a;
                                    }
                                }
                        C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Failed to transform cached settings data.", (Throwable) null);
                                return null;
                            }
                    C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "No cached settings data found.");
                        }
                        return null;
                    } catch (Exception e3) {
                        c0677aXM193a = null;
                        e = e3;
                    }
                }

                
       private void m189a(JSONObject jSONObject, String str) throws JSONException {
           if (!C0681ab.m227e(C0727v.m357a().getContext())) {
               jSONObject = this.f332b.m194a(jSONObject);
                    }
           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, str + jSONObject.toString());
                }
            }
