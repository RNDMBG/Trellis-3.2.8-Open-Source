            package com.flurry.sdk;

            import android.app.Activity;
            import android.content.Context;
            import android.location.Location;
            import android.os.Bundle;
            import android.os.SystemClock;
            import com.flurry.android.impl.analytics.FlurryAnalyticsModule;
            import com.google.android.gms.ads.identifier.AdvertisingIdClient;
            import java.io.DataInputStream;
            import java.io.DataOutputStream;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.lang.ref.WeakReference;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.List;
            import java.util.Locale;
            import java.util.Map;
            import java.util.TimeZone;
            import java.util.concurrent.atomic.AtomicInteger;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dg */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 64 */    public class C0796dg implements C0799dj.b, C0803dn.a {

                /* JADX INFO: renamed from: D */
                private Location f625D;

                /* JADX INFO: renamed from: E */
                private boolean f626E;

                /* JADX INFO: renamed from: F */
                private String f627F;

                /* JADX INFO: renamed from: G */
                private byte f628G;

                /* JADX INFO: renamed from: H */
                private long f629H;

                /* JADX INFO: renamed from: I */
                private long f630I;

                /* JADX INFO: renamed from: L */
                private boolean f633L;

                /* JADX INFO: renamed from: M */
                private int f634M;

                /* JADX INFO: renamed from: O */
                private int f636O;

                /* JADX INFO: renamed from: P */
                private int f637P;

                /* JADX INFO: renamed from: R */
                private Map<String, List<String>> f639R;

                /* JADX INFO: renamed from: S */
                private C0799dj f640S;

                /* JADX INFO: renamed from: T */
                private int f641T;

                /* JADX INFO: renamed from: k */
                private final String f646k;

                /* JADX INFO: renamed from: l */
                private String f647l;

                /* JADX INFO: renamed from: m */
                private WeakReference<Context> f648m;

                /* JADX INFO: renamed from: o */
                private List<C0794de> f650o;

                /* JADX INFO: renamed from: q */
                private boolean f652q;

                /* JADX INFO: renamed from: r */
                private long f653r;

                /* JADX INFO: renamed from: t */
                private AdvertisingIdClient.Info f655t;

                /* JADX INFO: renamed from: u */
                private byte[] f656u;

                /* JADX INFO: renamed from: v */
                private String f657v;

                /* JADX INFO: renamed from: w */
                private long f658w;

                /* JADX INFO: renamed from: x */
                private long f659x;

                /* JADX INFO: renamed from: y */
                private long f660y;

                /* JADX INFO: renamed from: z */
                private long f661z;

                /* JADX INFO: renamed from: g */
                private static final String f621g = C0796dg.class.getSimpleName();

                /* JADX INFO: renamed from: a */
                static int f615a = 100;

                /* JADX INFO: renamed from: b */
                static int f616b = 10;

                /* JADX INFO: renamed from: c */
                static int f617c = 1000;

                /* JADX INFO: renamed from: d */
                static int f618d = 160000;

                /* JADX INFO: renamed from: e */
                static int f619e = 50;

                /* JADX INFO: renamed from: f */
                static int f620f = 20;

                /* JADX INFO: renamed from: h */
                private final AtomicInteger f643h = new AtomicInteger(0);

                /* JADX INFO: renamed from: i */
                private final AtomicInteger f644i = new AtomicInteger(0);

                /* JADX INFO: renamed from: j */
                private final AtomicInteger f645j = new AtomicInteger(0);

                /* JADX INFO: renamed from: n */
                private File f649n = null;

                /* JADX INFO: renamed from: p */
                private final Map<EnumC0804do, ByteBuffer> f651p = new HashMap();

                /* JADX INFO: renamed from: s */
                private List<C0794de> f654s = new ArrayList();

                /* JADX INFO: renamed from: A */
                private String f622A = "";

                /* JADX INFO: renamed from: B */
                private String f623B = "";

                /* JADX INFO: renamed from: C */
                private byte f624C = -1;

                /* JADX INFO: renamed from: J */
                private final Map<String, C0784cu.a> f631J = new HashMap();

                /* JADX INFO: renamed from: K */
                private final List<C0788cy> f632K = new ArrayList();

                /* JADX INFO: renamed from: N */
                private final List<C0787cx> f635N = new ArrayList();

                /* JADX INFO: renamed from: Q */
                private final C0785cv f638Q = new C0785cv();

                /* JADX INFO: renamed from: U */
                private boolean f642U = false;

                /* JADX INFO: renamed from: a */
/* 65 */        static /* synthetic */ AdvertisingIdClient.Info m654a(C0796dg c0796dg, AdvertisingIdClient.Info info) {
/* 66 */            c0796dg.f655t = info;
                    return info;
                }

                /* JADX INFO: renamed from: a */
/* 65 */        static /* synthetic */ String m655a(C0796dg c0796dg, String str) {
/* 66 */            c0796dg.f657v = str;
                    return str;
                }

                /* JADX INFO: renamed from: a */
/* 65 */        static /* synthetic */ void m657a(C0796dg c0796dg) {
/* 66 */            c0796dg.m648C();
                }

                /* JADX INFO: renamed from: a */
/* 65 */        static /* synthetic */ byte[] m660a(C0796dg c0796dg, byte[] bArr) {
/* 66 */            c0796dg.f656u = bArr;
                    return bArr;
                }

                /* JADX INFO: renamed from: b */
/* 65 */        static /* synthetic */ void m661b(C0796dg c0796dg) {
/* 66 */            c0796dg.m667v();
                }

                /* JADX INFO: renamed from: c */
/* 65 */        static /* synthetic */ List m662c(C0796dg c0796dg) {
/* 66 */            return c0796dg.f654s;
                }

                /* JADX INFO: renamed from: d */
/* 65 */        static /* synthetic */ void m664d(C0796dg c0796dg) {
/* 66 */            c0796dg.m647B();
                }

                /* JADX INFO: renamed from: e */
/* 65 */        static /* synthetic */ void m665e(C0796dg c0796dg) {
/* 66 */            c0796dg.m670y();
                }

                /* JADX INFO: renamed from: a */
/* 142 */       Map<String, List<String>> m672a() {
/* 143 */           return this.f639R;
                }

                /* JADX INFO: renamed from: a */
/* 146 */       public void m674a(AbstractRunnableC0844fc abstractRunnableC0844fc) {
/* 147 */           C0801dl.m732a().m740c(abstractRunnableC0844fc);
                }

                /* JADX INFO: renamed from: b */
/* 150 */       public void m678b() {
/* 151 */           this.f652q = true;
                }

/* 157 */       public C0796dg(String str) {
/* 159 */           C0827el.m885a(4, f621g, "Creating new Flurry session");
/* 161 */           this.f646k = str;
/* 162 */           this.f648m = new WeakReference<>(null);
                }

                /* JADX INFO: renamed from: u */
/* 165 */       private void m666u() {
/* 166 */           C0803dn c0803dnM742a = C0802dm.m742a();
/* 168 */           this.f628G = ((Byte) c0803dnM742a.m746a("Gender")).byteValue();
/* 169 */           c0803dnM742a.m747a("Gender", (C0803dn.a) this);
/* 170 */           C0827el.m885a(4, f621g, "initSettings, Gender = " + ((int) this.f628G));
/* 172 */           this.f627F = (String) c0803dnM742a.m746a("UserId");
/* 173 */           c0803dnM742a.m747a("UserId", (C0803dn.a) this);
/* 174 */           C0827el.m885a(4, f621g, "initSettings, UserId = " + this.f627F);
/* 176 */           this.f626E = ((Boolean) c0803dnM742a.m746a("LogEvents")).booleanValue();
/* 177 */           c0803dnM742a.m747a("LogEvents", (C0803dn.a) this);
/* 178 */           C0827el.m885a(4, f621g, "initSettings, LogEvents = " + this.f626E);
/* 180 */           this.f629H = ((Long) c0803dnM742a.m746a("Age")).longValue();
/* 181 */           c0803dnM742a.m747a("Age", (C0803dn.a) this);
/* 182 */           C0827el.m885a(4, f621g, "initSettings, BirthDate = " + this.f629H);
/* 184 */           this.f630I = ((Long) c0803dnM742a.m746a("ContinueSessionMillis")).longValue();
/* 186 */           c0803dnM742a.m747a("ContinueSessionMillis", (C0803dn.a) this);
/* 187 */           C0827el.m885a(4, f621g, "initSettings, ContinueSessionMillis = " + this.f630I);
                }

                @Override // com.flurry.sdk.C0803dn.a
                /* JADX INFO: renamed from: a */
/* 193 */       public void mo535a(String str, Object obj) {
/* 194 */           if (str.equals("Gender")) {
/* 195 */               this.f628G = ((Byte) obj).byteValue();
/* 196 */               C0827el.m885a(4, f621g, "onSettingUpdate, Gender = " + ((int) this.f628G));
/* 213 */               return;
                    }
/* 197 */           if (str.equals("UserId")) {
/* 198 */               this.f627F = (String) obj;
/* 199 */               C0827el.m885a(4, f621g, "onSettingUpdate, UserId = " + this.f627F);
/* 213 */               return;
                    }
/* 200 */           if (str.equals("LogEvents")) {
/* 201 */               this.f626E = ((Boolean) obj).booleanValue();
/* 202 */               C0827el.m885a(4, f621g, "onSettingUpdate, LogEvents = " + this.f626E);
                    } else if (str.equals("Age")) {
/* 204 */               this.f629H = ((Long) obj).longValue();
/* 205 */               C0827el.m885a(4, f621g, "onSettingUpdate, Birthdate = " + this.f629H);
                    } else if (str.equals("ContinueSessionMillis")) {
/* 207 */               this.f630I = ((Long) obj).longValue();
/* 208 */               C0827el.m885a(4, f621g, "onSettingUpdate, ContinueSessionMillis = " + this.f630I);
                    } else {
/* 211 */               C0827el.m885a(6, f621g, "onSettingUpdate internal error!");
                    }
                }

                /* JADX INFO: renamed from: a */
/* 215 */       public synchronized void m673a(Context context) {
                    try {
                        C0827el.m885a(4, f621g, "Initializing new Flurry session with context:" + context);
/* 218 */               this.f648m = new WeakReference<>(context);
/* 220 */               this.f640S = new C0799dj(this);
/* 222 */               this.f649n = context.getFileStreamPath(m651F());
/* 224 */               this.f647l = C0814dy.m812a();
/* 227 */               this.f660y = -1L;
/* 228 */               this.f636O = 0;
/* 229 */               this.f623B = TimeZone.getDefault().getID();
/* 230 */               this.f622A = Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
/* 231 */               this.f633L = true;
/* 232 */               this.f634M = 0;
/* 233 */               this.f637P = 0;
/* 235 */               this.f639R = m663d(context);
/* 237 */               m666u();
/* 239 */               long jElapsedRealtime = SystemClock.elapsedRealtime();
/* 240 */               this.f658w = System.currentTimeMillis();
/* 241 */               this.f659x = jElapsedRealtime;
/* 244 */               m674a(new AnonymousClass1());
/* 251 */               m674a(new AnonymousClass2());
/* 258 */               m674a(new AnonymousClass3());
/* 265 */               m674a(new AnonymousClass4());
/* 272 */               m674a(new AnonymousClass5());
/* 279 */               C0828em.m902a().mo529a(this, context);
/* 281 */               this.f642U = true;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dg$1, reason: invalid class name */
/* 242 */       class AnonymousClass1 extends AbstractRunnableC0844fc {
/* 243 */           AnonymousClass1() {
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 246 */           public void mo576a() {
/* 247 */               C0796dg.m654a(C0796dg.this, C0809dt.m770a());
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dg$2, reason: invalid class name */
/* 249 */       class AnonymousClass2 extends AbstractRunnableC0844fc {
/* 250 */           AnonymousClass2() {
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 253 */           public void mo576a() {
/* 254 */               C0796dg.m660a(C0796dg.this, C0811dv.m777a());
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dg$3, reason: invalid class name */
/* 256 */       class AnonymousClass3 extends AbstractRunnableC0844fc {
/* 257 */           AnonymousClass3() {
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 260 */           public void mo576a() {
/* 261 */               C0796dg.m655a(C0796dg.this, C0813dx.m796a());
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dg$4, reason: invalid class name */
/* 263 */       class AnonymousClass4 extends AbstractRunnableC0844fc {
/* 264 */           AnonymousClass4() {
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 267 */           public void mo576a() {
/* 268 */               C0796dg.m657a(C0796dg.this);
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dg$5, reason: invalid class name */
/* 270 */       class AnonymousClass5 extends AbstractRunnableC0844fc {
/* 271 */           AnonymousClass5() {
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 274 */           public void mo576a() {
/* 275 */               C0796dg.m661b(C0796dg.this);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 284 */       public synchronized void m679b(Context context) {
                    try {
                        if (this.f642U) {
/* 289 */                   C0827el.m900d(f621g, "Start session with context: " + context + " count:" + m685g());
/* 291 */                   this.f648m = new WeakReference<>(context);
/* 293 */                   if (this.f640S.m719b()) {
/* 294 */                       this.f640S.m717a();
                            }
/* 297 */                   m649D();
/* 299 */                   C0812dw.m781a().m791c();
/* 300 */                   this.f625D = C0812dw.m781a().m794f();
/* 302 */                   C0828em.m902a().mo530b(this, context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: c */
/* 305 */       public synchronized void m681c(Context context) {
                    try {
                        if (this.f642U) {
/* 310 */                   C0828em.m902a().mo531c(this, context);
/* 312 */                   this.f625D = C0812dw.m781a().m794f();
/* 313 */                   m671z();
/* 315 */                   m650E();
/* 317 */                   C0827el.m900d(f621g, "End session with context: " + context + " count:" + m685g());
/* 321 */                   this.f660y = SystemClock.elapsedRealtime() - this.f659x;
/* 323 */                   m656a(this.f660y);
/* 325 */                   m668w();
/* 327 */                   if (m685g() == 0) {
/* 328 */                       this.f640S.m718a(this.f630I);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: c */
/* 332 */       public synchronized void m680c() {
                    try {
                        if (this.f642U) {
/* 337 */                   C0827el.m900d(f621g, "Finalize session");
/* 339 */                   if (this.f640S.m719b()) {
/* 340 */                       this.f640S.m717a();
                            }
/* 343 */                   if (m685g() != 0) {
/* 344 */                       C0827el.m885a(6, f621g, "Session with apiKey = " + m688j() + " was ended while getSessionCount() is not 0");
                            }
/* 349 */                   this.f641T = 0;
/* 352 */                   m669x();
/* 354 */                   C0828em.m902a().mo528a(this);
/* 357 */                   m674a(new AnonymousClass6());
/* 364 */                   C0802dm.m742a().m749b("Gender", (C0803dn.a) this);
/* 366 */                   C0802dm.m742a().m749b("UserId", (C0803dn.a) this);
/* 368 */                   C0802dm.m742a().m749b("Age", (C0803dn.a) this);
/* 369 */                   C0802dm.m742a().m749b("LogEvents", (C0803dn.a) this);
/* 371 */                   C0802dm.m742a().m749b("ContinueSessionMillis", (C0803dn.a) this);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dg$6, reason: invalid class name */
/* 355 */       class AnonymousClass6 extends AbstractRunnableC0844fc {
/* 356 */           AnonymousClass6() {
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 359 */           public void mo576a() {
/* 360 */               C0798di.m709a().m713a(C0796dg.this.m688j());
                    }
                }

                /* JADX INFO: renamed from: d */
/* 375 */       private Map<String, List<String>> m663d(Context context) {
                    Bundle extras;
/* 379 */           if (!(context instanceof Activity) || (extras = ((Activity) context).getIntent().getExtras()) == null) {
/* 376 */               return null;
                    }
/* 383 */           C0827el.m885a(3, f621g, "Launch Options Bundle is present " + extras.toString());
/* 385 */           HashMap map = new HashMap();
/* 388 */           for (String str : extras.keySet()) {
/* 392 */               if (str != null) {
/* 396 */                   Object obj = extras.get(str);
/* 397 */                   String string = obj != null ? obj.toString() : "null";
/* 399 */                   map.put(str, new ArrayList(Arrays.asList(string)));
/* 400 */                   C0827el.m885a(3, f621g, "Launch options Key: " + str + ". Its value: " + string);
                        }
                    }
                    return map;
                }

                /* JADX INFO: renamed from: v */
/* 411 */       private void m667v() {
                    try {
/* 412 */               if (this.f657v != null) {
/* 413 */                   C0827el.m885a(3, f621g, "Fetched phone id");
/* 414 */                   m658a(EnumC0804do.PhoneId, ByteBuffer.wrap(this.f657v.getBytes()));
                        }
/* 416 */               if (this.f656u != null) {
/* 417 */                   C0827el.m885a(3, f621g, "Fetched hashed IMEI");
/* 418 */                   m658a(EnumC0804do.Sha1Imei, ByteBuffer.wrap(this.f656u));
                        }
/* 420 */               if (this.f655t != null) {
/* 421 */                   C0827el.m885a(3, f621g, "Fetched advertising id");
/* 422 */                   m658a(EnumC0804do.AndroidAdvertisingId, ByteBuffer.wrap(this.f655t.getId().getBytes()));
                        }
/* 425 */               m646A();
                    } catch (Throwable th) {
/* 427 */               C0827el.m886a(6, f621g, "", th);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 431 */       private synchronized void m656a(long j) {
                    try {
                        for (C0788cy c0788cy : this.f632K) {
/* 433 */                   if (c0788cy.m561a() && !c0788cy.m564b()) {
/* 434 */                       c0788cy.m559a(j);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dg$7, reason: invalid class name */
/* 438 */       class AnonymousClass7 extends AbstractRunnableC0844fc {
/* 439 */           AnonymousClass7() {
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 442 */           public void mo576a() {
/* 443 */               C0794de c0794deM682d = C0796dg.this.m682d();
/* 444 */               C0796dg.m662c(C0796dg.this).clear();
/* 445 */               C0796dg.m662c(C0796dg.this).add(c0794deM682d);
/* 447 */               C0796dg.m664d(C0796dg.this);
                    }
                }

                /* JADX INFO: renamed from: w */
/* 439 */       private void m668w() {
/* 440 */           m674a(new AnonymousClass7());
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dg$8, reason: invalid class name */
/* 451 */       class AnonymousClass8 extends AbstractRunnableC0844fc {
/* 452 */           AnonymousClass8() {
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 455 */           public void mo576a() {
/* 456 */               C0796dg.m665e(C0796dg.this);
                    }
                }

                /* JADX INFO: renamed from: x */
/* 452 */       private void m669x() {
/* 453 */           m674a(new AnonymousClass8());
                }

                /* JADX INFO: renamed from: y */
/* 464 */       private void m670y() {
                    boolean z;
                    try {
                        synchronized (this) {
                            try {
/* 466 */                       z = this.f654s.size() > 0;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 468 */               if (z) {
/* 469 */                   m646A();
                        }
                    } catch (Throwable th2) {
/* 472 */               C0827el.m886a(6, f621g, "", th2);
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.dg$9, reason: invalid class name */
/* 475 */       class AnonymousClass9 extends AbstractRunnableC0844fc {
/* 476 */           AnonymousClass9() {
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 479 */           public void mo576a() {
/* 480 */               C0812dw.m781a().m792d();
                    }
                }

                /* JADX INFO: renamed from: z */
/* 476 */       private void m671z() {
/* 477 */           m674a(new AnonymousClass9());
                }

                /* JADX INFO: renamed from: d */
/* 488 */       synchronized C0794de m682d() {
                    C0794de c0794de;
                    try {
                        C0795df c0795df = new C0795df();
/* 491 */               c0795df.m617a(this.f647l);
/* 492 */               c0795df.m614a(this.f658w);
/* 493 */               c0795df.m623b(this.f660y);
/* 494 */               c0795df.m628c(this.f661z);
/* 495 */               c0795df.m624b(m689k());
/* 496 */               c0795df.m629c(m690l());
/* 497 */               c0795df.m613a((int) this.f624C);
/* 498 */               c0795df.m631d(m686h());
/* 500 */               c0795df.m615a(this.f625D);
/* 502 */               c0795df.m622b(m684f());
/* 503 */               c0795df.m612a(this.f628G);
/* 504 */               c0795df.m616a(Long.valueOf(this.f629H));
/* 506 */               c0795df.m619a(m698t());
/* 507 */               c0795df.m618a(m696r());
/* 508 */               c0795df.m620a(this.f633L);
/* 510 */               c0795df.m625b(m697s());
/* 511 */               c0795df.m627c(this.f636O);
                        try {
/* 515 */                   c0794de = new C0794de(c0795df);
                        } catch (IOException e) {
/* 517 */                   e.printStackTrace();
/* 513 */                   c0794de = null;
                        }
/* 520 */               if (c0794de == null) {
/* 521 */                   C0827el.m900d(f621g, "New session report wasn't created");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return c0794de;
                }

                /* JADX INFO: renamed from: e */
/* 527 */       public synchronized void m683e() {
                    try {
                        this.f637P++;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: f */
/* 531 */       int m684f() {
/* 532 */           return this.f637P;
                }

                /* JADX INFO: renamed from: a */
/* 535 */       public synchronized void m677a(String str, Map<String, String> map, boolean z) {
                    try {
                        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.f659x;
/* 538 */               String strM959a = C0843fb.m959a(str);
/* 539 */               if (strM959a.length() != 0) {
/* 542 */                   C0784cu.a aVar = this.f631J.get(strM959a);
/* 543 */                   if (aVar == null) {
/* 544 */                       if (this.f631J.size() < f615a) {
/* 545 */                           C0784cu.a aVar2 = new C0784cu.a();
/* 546 */                           aVar2.f535a = 1;
/* 547 */                           this.f631J.put(strM959a, aVar2);
/* 549 */                           C0827el.m900d(f621g, "Event count started: " + strM959a);
                                } else {
/* 551 */                           C0827el.m900d(f621g, "Too many different events. Event not counted: " + strM959a);
                                }
                            } else {
/* 555 */                       aVar.f535a++;
/* 556 */                       C0827el.m900d(f621g, "Event count incremented: " + strM959a);
                            }
/* 559 */                   if (this.f626E && this.f632K.size() < f617c && this.f634M < f618d) {
/* 561 */                       Map<String, String> mapEmptyMap = map == null ? Collections.emptyMap() : map;
/* 563 */                       if (mapEmptyMap.size() > f616b) {
/* 564 */                           C0827el.m900d(f621g, "MaxEventParams exceeded: " + mapEmptyMap.size());
                                } else {
/* 566 */                           C0788cy c0788cy = new C0788cy(m652G(), strM959a, mapEmptyMap, jElapsedRealtime, z);
/* 569 */                           if (c0788cy.m566d() + this.f634M <= f618d) {
/* 570 */                               this.f632K.add(c0788cy);
                                        this.f634M = c0788cy.m566d() + this.f634M;
                                    } else {
/* 575 */                               this.f634M = f618d;
/* 576 */                               this.f633L = false;
/* 578 */                               C0827el.m900d(f621g, "Event Log size exceeded. No more event details logged.");
                                    }
                                }
                            } else {
/* 583 */                       this.f633L = false;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 588 */       public synchronized void m676a(String str, Map<String, String> map) {
                    try {
                        for (C0788cy c0788cy : this.f632K) {
/* 590 */                   if (c0788cy.m562a(str)) {
/* 591 */                       long jElapsedRealtime = SystemClock.elapsedRealtime() - this.f659x;
/* 593 */                       if (map != null && map.size() > 0 && this.f634M < f618d) {
/* 595 */                           int iM566d = this.f634M - c0788cy.m566d();
/* 597 */                           HashMap map2 = new HashMap(c0788cy.m565c());
/* 599 */                           c0788cy.m560a(map);
/* 601 */                           if (c0788cy.m566d() + iM566d <= f618d) {
/* 602 */                               if (c0788cy.m565c().size() > f616b) {
/* 603 */                                   C0827el.m900d(f621g, "MaxEventParams exceeded on endEvent: " + c0788cy.m565c().size());
/* 605 */                                   c0788cy.m563b(map2);
                                        } else {
/* 607 */                                   this.f634M = iM566d + c0788cy.m566d();
                                        }
                                    } else {
/* 611 */                               c0788cy.m563b(map2);
/* 612 */                               this.f633L = false;
/* 613 */                               this.f634M = f618d;
/* 615 */                               C0827el.m900d(f621g, "Event Log size exceeded. No more event details logged.");
                                    }
                                }
/* 620 */                       c0788cy.m559a(jElapsedRealtime);
                                break;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX WARN: Code duplicated, block: B:13:0x0059  */
                /* JADX INFO: renamed from: a */
/* 628 */       public synchronized void m675a(String str, String str2, String str3, Throwable th) {
                    boolean z;
                    int i = 0;
                    synchronized (this) {
                        if (str != null) {
                            try {
                                if ("uncaught".equals(str)) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        } else {
                            z = false;
                        }
/* 632 */               this.f636O++;
/* 633 */               if (this.f635N.size() < f619e) {
/* 635 */                   C0787cx c0787cx = new C0787cx(m653H(), Long.valueOf(System.currentTimeMillis()).longValue(), str, str2, str3, th);
/* 637 */                   this.f635N.add(c0787cx);
/* 639 */                   C0827el.m900d(f621g, "Error logged: " + c0787cx.m558c());
                        } else if (z) {
                            while (true) {
                                int i2 = i;
/* 643 */                       if (i2 >= this.f635N.size()) {
                                    break;
                                }
/* 644 */                       C0787cx c0787cx2 = this.f635N.get(i2);
/* 645 */                       if (c0787cx2.m558c() == null || "uncaught".equals(c0787cx2.m558c())) {
/* 643 */                           i = i2 + 1;
                                } else {
/* 650 */                           this.f635N.set(i2, new C0787cx(m653H(), Long.valueOf(System.currentTimeMillis()).longValue(), str, str2, str3, th));
                                    break;
                                }
                            }
                        } else {
/* 655 */                   C0827el.m900d(f621g, "Max errors logged. No more errors logged.");
                        }
                    }
                }

                /* JADX INFO: renamed from: A */
/* 661 */       private void m646A() {
                    try {
/* 662 */               C0827el.m885a(3, f621g, "generating agent report");
/* 664 */               C0789cz c0789cz = new C0789cz(this.f646k, this.f647l, this.f652q, m693o(), this.f653r, this.f658w, this.f654s, m694p(), this.f638Q.m552a(false), m672a(), C0784cu.m532a().m543b(), System.currentTimeMillis());
/* 670 */               this.f650o = new ArrayList(this.f654s);
/* 672 */               if (c0789cz != null && c0789cz.m568a() != null) {
/* 673 */                   C0827el.m885a(3, f621g, "generated report of size " + c0789cz.m568a().length + " with " + this.f654s.size() + " reports.");
/* 677 */                   m659a(c0789cz.m568a());
/* 679 */                   this.f654s.removeAll(this.f650o);
/* 680 */                   this.f650o = null;
/* 683 */                   m647B();
                        } else {
/* 685 */                   C0827el.m900d(f621g, "Error generating report");
                        }
                    } catch (Throwable th) {
/* 688 */               C0827el.m886a(6, f621g, "", th);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 692 */       private void m659a(byte[] bArr) {
/* 695 */           FlurryAnalyticsModule.getInstance().m527a().m584b(bArr, this.f646k, "" + C0800dk.m720a().m721b());
                }

                /* JADX INFO: renamed from: B */
/* 698 */       private synchronized void m647B() {
                    try {
/* 701 */               if (!C0842fa.m954a(this.f649n)) {
/* 702 */                   C0827el.m900d(f621g, "Error persisting report: could not create directory");
                        } else {
                            try {
/* 708 */                       DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(this.f649n));
/* 710 */                       C0797dh c0797dh = new C0797dh();
/* 712 */                       c0797dh.m704a(this.f652q);
/* 713 */                       c0797dh.m701a(this.f653r);
/* 714 */                       c0797dh.m703a(this.f654s);
/* 716 */                       c0797dh.m702a(dataOutputStream, this.f646k, m687i());
                            } catch (Exception e) {
/* 718 */                       C0827el.m894b(f621g, "Error saving persistent data", e);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: C */
/* 725 */       private synchronized void m648C() {
                    DataInputStream dataInputStream;
                    FileInputStream fileInputStream;
                    boolean z;
                    FileInputStream fileInputStream2 = null;
                    synchronized (this) {
                        try {
                            C0827el.m885a(4, f621g, "Loading persistent data: " + this.f649n.getAbsolutePath());
/* 729 */                   if (this.f649n.exists()) {
                                try {
/* 734 */                           fileInputStream = new FileInputStream(this.f649n);
                                    try {
/* 735 */                               dataInputStream = new DataInputStream(fileInputStream);
                                        try {
/* 737 */                                   C0797dh c0797dh = new C0797dh();
/* 738 */                                   c0797dh.m706a(dataInputStream, this.f646k);
/* 740 */                                   this.f652q = c0797dh.m705a();
/* 741 */                                   this.f653r = c0797dh.m708c();
/* 742 */                                   this.f654s = c0797dh.m707b();
/* 744 */                                   z = true;
/* 748 */                                   C0843fb.m962a(dataInputStream);
/* 749 */                                   C0843fb.m962a(fileInputStream);
                                        } catch (Exception e) {
                                            e = e;
                                            fileInputStream2 = fileInputStream;
                                            try {
/* 746 */                                       C0827el.m894b(f621g, "Error loading persistent data", e);
/* 748 */                                       C0843fb.m962a(dataInputStream);
/* 749 */                                       C0843fb.m962a(fileInputStream2);
                                                z = false;
                                            } catch (Error th) {
                                                th = th;
                                                fileInputStream = fileInputStream2;
                                                C0843fb.m962a(dataInputStream);
/* 749 */                                       C0843fb.m962a(fileInputStream);
                                                throw th;
                                            }
                                        } catch (Error th2) {
                                            C0843fb.m962a(dataInputStream);
/* 749 */                                   C0843fb.m962a(fileInputStream);
                                            throw th2;
                                        }
                                    } catch (Exception e2) {
/* 745 */                               C0827el.m894b(f621g, "Error loading persistent data", e2);
                                        dataInputStream = null;
                                        fileInputStream2 = fileInputStream;
                                        z = false;
                                    } catch (Error th3) {
                                        dataInputStream = null;
                                        throw th3;
                                    }
                                } catch (Exception e3) {
/* 745 */                           C0827el.m894b(f621g, "Error loading persistent data", e3);
                                        dataInputStream = null;
                                        z = false;
                                } catch (Error th4) {
                                        dataInputStream = null;
                                        fileInputStream = null;
                                        throw th4;
                                }
/* 752 */                       if (!z) {
/* 753 */                           C0827el.m885a(3, f621g, "Deleting agent cache file");
/* 754 */                           this.f649n.delete();
                                }
                            } else {
/* 757 */                       C0827el.m885a(4, f621g, "Agent cache file doesn't exist.");
                                z = false;
                            }
/* 760 */                   if (!z) {
/* 761 */                       this.f652q = false;
/* 762 */                       this.f653r = this.f658w;
                            }
                        } catch (Error th5) {
                            throw th5;
                        }
                    }
                }

                /* JADX INFO: renamed from: D */
/* 766 */       private void m649D() {
/* 767 */           this.f641T++;
                }

                /* JADX INFO: renamed from: E */
/* 770 */       private void m650E() {
/* 771 */           if (this.f641T > 0) {
/* 772 */               this.f641T--;
                    }
                }

                /* JADX INFO: renamed from: g */
/* 776 */       int m685g() {
/* 777 */           return this.f641T;
                }

                /* JADX INFO: renamed from: F */
/* 780 */       private String m651F() {
/* 781 */           return ".flurryagent." + Integer.toString(this.f646k.hashCode(), 16);
                }

                /* JADX INFO: renamed from: G */
/* 784 */       private int m652G() {
/* 785 */           return this.f643h.incrementAndGet();
                }

                /* JADX INFO: renamed from: H */
/* 788 */       private int m653H() {
/* 789 */           return this.f644i.incrementAndGet();
                }

                /* JADX INFO: renamed from: h */
/* 796 */       String m686h() {
                    return this.f627F == null ? "" : this.f627F;
                }

                /* JADX INFO: renamed from: i */
/* 800 */       public String m687i() {
/* 801 */           return this.f657v;
                }

                /* JADX INFO: renamed from: j */
/* 808 */       public String m688j() {
/* 809 */           return this.f646k;
                }

                /* JADX INFO: renamed from: k */
/* 812 */       public String m689k() {
/* 813 */           return this.f622A;
                }

                /* JADX INFO: renamed from: l */
/* 816 */       public String m690l() {
/* 817 */           return this.f623B;
                }

                /* JADX INFO: renamed from: m */
/* 820 */       public long m691m() {
/* 821 */           return this.f658w;
                }

                /* JADX INFO: renamed from: n */
/* 824 */       public long m692n() {
/* 825 */           return this.f659x;
                }

                /* JADX INFO: renamed from: o */
/* 828 */       public boolean m693o() {
                    return this.f655t == null || !this.f655t.isLimitAdTrackingEnabled();
                }

                /* JADX INFO: renamed from: a */
/* 836 */       private void m658a(EnumC0804do enumC0804do, ByteBuffer byteBuffer) {
                    synchronized (this.f651p) {
                        try {
/* 838 */                   this.f651p.put(enumC0804do, byteBuffer);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: p */
/* 842 */       public Map<EnumC0804do, ByteBuffer> m694p() {
/* 843 */           return new HashMap(this.f651p);
                }

                @Override // com.flurry.sdk.C0799dj.b
                /* JADX INFO: renamed from: q */
/* 863 */       public void mo695q() {
/* 864 */           m680c();
                }

                /* JADX INFO: renamed from: r */
/* 867 */       List<C0788cy> m696r() {
/* 868 */           return this.f632K;
                }

                /* JADX INFO: renamed from: s */
/* 871 */       List<C0787cx> m697s() {
/* 872 */           return this.f635N;
                }

                /* JADX INFO: renamed from: t */
/* 875 */       Map<String, C0784cu.a> m698t() {
/* 876 */           return this.f631J;
                }
            }
