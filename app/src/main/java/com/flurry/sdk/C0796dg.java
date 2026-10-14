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
            
            
            
    public class C0796dg implements C0799dj.b, C0803dn.a {

                
                private Location f625D;

                
                private boolean f626E;

                
                private String f627F;

                
                private byte f628G;

                
                private long f629H;

                
                private long f630I;

                
                private boolean f633L;

                
                private int f634M;

                
                private int f636O;

                
                private int f637P;

                
                private Map<String, List<String>> f639R;

                
                private C0799dj f640S;

                
                private int f641T;

                
                private final String f646k;

                
                private String f647l;

                
                private WeakReference<Context> f648m;

                
                private List<C0794de> f650o;

                
                private boolean f652q;

                
                private long f653r;

                
                private AdvertisingIdClient.Info f655t;

                
                private byte[] f656u;

                
                private String f657v;

                
                private long f658w;

                
                private long f659x;

                
                private long f660y;

                
                private long f661z;

                
                private static final String f621g = C0796dg.class.getSimpleName();

                
                static int f615a = 100;

                
                static int f616b = 10;

                
                static int f617c = 1000;

                
                static int f618d = 160000;

                
                static int f619e = 50;

                
                static int f620f = 20;

                
                private final AtomicInteger f643h = new AtomicInteger(0);

                
                private final AtomicInteger f644i = new AtomicInteger(0);

                
                private final AtomicInteger f645j = new AtomicInteger(0);

                
                private File f649n = null;

                
                private final Map<EnumC0804do, ByteBuffer> f651p = new HashMap();

                
                private List<C0794de> f654s = new ArrayList();

                
                private String f622A = "";

                
                private String f623B = "";

                
                private byte f624C = -1;

                
                private final Map<String, C0784cu.a> f631J = new HashMap();

                
                private final List<C0788cy> f632K = new ArrayList();

                
                private final List<C0787cx> f635N = new ArrayList();

                
                private final C0785cv f638Q = new C0785cv();

                
                private boolean f642U = false;

                
        static  AdvertisingIdClient.Info m654a(C0796dg c0796dg, AdvertisingIdClient.Info info) {
            c0796dg.f655t = info;
                    return info;
                }

                
        static  String m655a(C0796dg c0796dg, String str) {
            c0796dg.f657v = str;
                    return str;
                }

                
        static  void m657a(C0796dg c0796dg) {
            c0796dg.m648C();
                }

                
        static  byte[] m660a(C0796dg c0796dg, byte[] bArr) {
            c0796dg.f656u = bArr;
                    return bArr;
                }

                
        static  void m661b(C0796dg c0796dg) {
            c0796dg.m667v();
                }

                
        static  List m662c(C0796dg c0796dg) {
            return c0796dg.f654s;
                }

                
        static  void m664d(C0796dg c0796dg) {
            c0796dg.m647B();
                }

                
        static  void m665e(C0796dg c0796dg) {
            c0796dg.m670y();
                }

                
       Map<String, List<String>> m672a() {
           return this.f639R;
                }

                
       public void m674a(AbstractRunnableC0844fc abstractRunnableC0844fc) {
           C0801dl.m732a().m740c(abstractRunnableC0844fc);
                }

                
       public void m678b() {
           this.f652q = true;
                }

       public C0796dg(String str) {
           C0827el.m885a(4, f621g, "Creating new Flurry session");
           this.f646k = str;
           this.f648m = new WeakReference<>(null);
                }

                
       private void m666u() {
           C0803dn c0803dnM742a = C0802dm.m742a();
           this.f628G = ((Byte) c0803dnM742a.m746a("Gender")).byteValue();
           c0803dnM742a.m747a("Gender", (C0803dn.a) this);
           C0827el.m885a(4, f621g, "initSettings, Gender = " + ((int) this.f628G));
           this.f627F = (String) c0803dnM742a.m746a("UserId");
           c0803dnM742a.m747a("UserId", (C0803dn.a) this);
           C0827el.m885a(4, f621g, "initSettings, UserId = " + this.f627F);
           this.f626E = ((Boolean) c0803dnM742a.m746a("LogEvents")).booleanValue();
           c0803dnM742a.m747a("LogEvents", (C0803dn.a) this);
           C0827el.m885a(4, f621g, "initSettings, LogEvents = " + this.f626E);
           this.f629H = ((Long) c0803dnM742a.m746a("Age")).longValue();
           c0803dnM742a.m747a("Age", (C0803dn.a) this);
           C0827el.m885a(4, f621g, "initSettings, BirthDate = " + this.f629H);
           this.f630I = ((Long) c0803dnM742a.m746a("ContinueSessionMillis")).longValue();
           c0803dnM742a.m747a("ContinueSessionMillis", (C0803dn.a) this);
           C0827el.m885a(4, f621g, "initSettings, ContinueSessionMillis = " + this.f630I);
                }

                @Override 
                
       public void mo535a(String str, Object obj) {
           if (str.equals("Gender")) {
               this.f628G = ((Byte) obj).byteValue();
               C0827el.m885a(4, f621g, "onSettingUpdate, Gender = " + ((int) this.f628G));
               return;
                    }
           if (str.equals("UserId")) {
               this.f627F = (String) obj;
               C0827el.m885a(4, f621g, "onSettingUpdate, UserId = " + this.f627F);
               return;
                    }
           if (str.equals("LogEvents")) {
               this.f626E = ((Boolean) obj).booleanValue();
               C0827el.m885a(4, f621g, "onSettingUpdate, LogEvents = " + this.f626E);
                    } else if (str.equals("Age")) {
               this.f629H = ((Long) obj).longValue();
               C0827el.m885a(4, f621g, "onSettingUpdate, Birthdate = " + this.f629H);
                    } else if (str.equals("ContinueSessionMillis")) {
               this.f630I = ((Long) obj).longValue();
               C0827el.m885a(4, f621g, "onSettingUpdate, ContinueSessionMillis = " + this.f630I);
                    } else {
               C0827el.m885a(6, f621g, "onSettingUpdate internal error!");
                    }
                }

                
       public synchronized void m673a(Context context) {
                    try {
                        C0827el.m885a(4, f621g, "Initializing new Flurry session with context:" + context);
               this.f648m = new WeakReference<>(context);
               this.f640S = new C0799dj(this);
               this.f649n = context.getFileStreamPath(m651F());
               this.f647l = C0814dy.m812a();
               this.f660y = -1L;
               this.f636O = 0;
               this.f623B = TimeZone.getDefault().getID();
               this.f622A = Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
               this.f633L = true;
               this.f634M = 0;
               this.f637P = 0;
               this.f639R = m663d(context);
               m666u();
               long jElapsedRealtime = SystemClock.elapsedRealtime();
               this.f658w = System.currentTimeMillis();
               this.f659x = jElapsedRealtime;
               m674a(new AnonymousClass1());
               m674a(new AnonymousClass2());
               m674a(new AnonymousClass3());
               m674a(new AnonymousClass4());
               m674a(new AnonymousClass5());
               C0828em.m902a().mo529a(this, context);
               this.f642U = true;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       class AnonymousClass1 extends AbstractRunnableC0844fc {
           AnonymousClass1() {
                    }

                    @Override 
                    
           public void mo576a() {
               C0796dg.m654a(C0796dg.this, C0809dt.m770a());
                    }
                }

                
       class AnonymousClass2 extends AbstractRunnableC0844fc {
           AnonymousClass2() {
                    }

                    @Override 
                    
           public void mo576a() {
               C0796dg.m660a(C0796dg.this, C0811dv.m777a());
                    }
                }

                
       class AnonymousClass3 extends AbstractRunnableC0844fc {
           AnonymousClass3() {
                    }

                    @Override 
                    
           public void mo576a() {
               C0796dg.m655a(C0796dg.this, C0813dx.m796a());
                    }
                }

                
       class AnonymousClass4 extends AbstractRunnableC0844fc {
           AnonymousClass4() {
                    }

                    @Override 
                    
           public void mo576a() {
               C0796dg.m657a(C0796dg.this);
                    }
                }

                
       class AnonymousClass5 extends AbstractRunnableC0844fc {
           AnonymousClass5() {
                    }

                    @Override 
                    
           public void mo576a() {
               C0796dg.m661b(C0796dg.this);
                    }
                }

                
       public synchronized void m679b(Context context) {
                    try {
                        if (this.f642U) {
                   C0827el.m900d(f621g, "Start session with context: " + context + " count:" + m685g());
                   this.f648m = new WeakReference<>(context);
                   if (this.f640S.m719b()) {
                       this.f640S.m717a();
                            }
                   m649D();
                   C0812dw.m781a().m791c();
                   this.f625D = C0812dw.m781a().m794f();
                   C0828em.m902a().mo530b(this, context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       public synchronized void m681c(Context context) {
                    try {
                        if (this.f642U) {
                   C0828em.m902a().mo531c(this, context);
                   this.f625D = C0812dw.m781a().m794f();
                   m671z();
                   m650E();
                   C0827el.m900d(f621g, "End session with context: " + context + " count:" + m685g());
                   this.f660y = SystemClock.elapsedRealtime() - this.f659x;
                   m656a(this.f660y);
                   m668w();
                   if (m685g() == 0) {
                       this.f640S.m718a(this.f630I);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       public synchronized void m680c() {
                    try {
                        if (this.f642U) {
                   C0827el.m900d(f621g, "Finalize session");
                   if (this.f640S.m719b()) {
                       this.f640S.m717a();
                            }
                   if (m685g() != 0) {
                       C0827el.m885a(6, f621g, "Session with apiKey = " + m688j() + " was ended while getSessionCount() is not 0");
                            }
                   this.f641T = 0;
                   m669x();
                   C0828em.m902a().mo528a(this);
                   m674a(new AnonymousClass6());
                   C0802dm.m742a().m749b("Gender", (C0803dn.a) this);
                   C0802dm.m742a().m749b("UserId", (C0803dn.a) this);
                   C0802dm.m742a().m749b("Age", (C0803dn.a) this);
                   C0802dm.m742a().m749b("LogEvents", (C0803dn.a) this);
                   C0802dm.m742a().m749b("ContinueSessionMillis", (C0803dn.a) this);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       class AnonymousClass6 extends AbstractRunnableC0844fc {
           AnonymousClass6() {
                    }

                    @Override 
                    
           public void mo576a() {
               C0798di.m709a().m713a(C0796dg.this.m688j());
                    }
                }

                
       private Map<String, List<String>> m663d(Context context) {
                    Bundle extras;
           if (!(context instanceof Activity) || (extras = ((Activity) context).getIntent().getExtras()) == null) {
               return null;
                    }
           C0827el.m885a(3, f621g, "Launch Options Bundle is present " + extras.toString());
           HashMap map = new HashMap();
           for (String str : extras.keySet()) {
               if (str != null) {
                   Object obj = extras.get(str);
                   String string = obj != null ? obj.toString() : "null";
                   map.put(str, new ArrayList(Arrays.asList(string)));
                   C0827el.m885a(3, f621g, "Launch options Key: " + str + ". Its value: " + string);
                        }
                    }
                    return map;
                }

                
       private void m667v() {
                    try {
               if (this.f657v != null) {
                   C0827el.m885a(3, f621g, "Fetched phone id");
                   m658a(EnumC0804do.PhoneId, ByteBuffer.wrap(this.f657v.getBytes()));
                        }
               if (this.f656u != null) {
                   C0827el.m885a(3, f621g, "Fetched hashed IMEI");
                   m658a(EnumC0804do.Sha1Imei, ByteBuffer.wrap(this.f656u));
                        }
               if (this.f655t != null) {
                   C0827el.m885a(3, f621g, "Fetched advertising id");
                   m658a(EnumC0804do.AndroidAdvertisingId, ByteBuffer.wrap(this.f655t.getId().getBytes()));
                        }
               m646A();
                    } catch (Throwable th) {
               C0827el.m886a(6, f621g, "", th);
                    }
                }

                
       private synchronized void m656a(long j) {
                    try {
                        for (C0788cy c0788cy : this.f632K) {
                   if (c0788cy.m561a() && !c0788cy.m564b()) {
                       c0788cy.m559a(j);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       class AnonymousClass7 extends AbstractRunnableC0844fc {
           AnonymousClass7() {
                    }

                    @Override 
                    
           public void mo576a() {
               C0794de c0794deM682d = C0796dg.this.m682d();
               C0796dg.m662c(C0796dg.this).clear();
               C0796dg.m662c(C0796dg.this).add(c0794deM682d);
               C0796dg.m664d(C0796dg.this);
                    }
                }

                
       private void m668w() {
           m674a(new AnonymousClass7());
                }

                
       class AnonymousClass8 extends AbstractRunnableC0844fc {
           AnonymousClass8() {
                    }

                    @Override 
                    
           public void mo576a() {
               C0796dg.m665e(C0796dg.this);
                    }
                }

                
       private void m669x() {
           m674a(new AnonymousClass8());
                }

                
       private void m670y() {
                    boolean z;
                    try {
                        synchronized (this) {
                            try {
                       z = this.f654s.size() > 0;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               if (z) {
                   m646A();
                        }
                    } catch (Throwable th2) {
               C0827el.m886a(6, f621g, "", th2);
                    }
                }

                
       class AnonymousClass9 extends AbstractRunnableC0844fc {
           AnonymousClass9() {
                    }

                    @Override 
                    
           public void mo576a() {
               C0812dw.m781a().m792d();
                    }
                }

                
       private void m671z() {
           m674a(new AnonymousClass9());
                }

                
       synchronized C0794de m682d() {
                    C0794de c0794de;
                    try {
                        C0795df c0795df = new C0795df();
               c0795df.m617a(this.f647l);
               c0795df.m614a(this.f658w);
               c0795df.m623b(this.f660y);
               c0795df.m628c(this.f661z);
               c0795df.m624b(m689k());
               c0795df.m629c(m690l());
               c0795df.m613a((int) this.f624C);
               c0795df.m631d(m686h());
               c0795df.m615a(this.f625D);
               c0795df.m622b(m684f());
               c0795df.m612a(this.f628G);
               c0795df.m616a(Long.valueOf(this.f629H));
               c0795df.m619a(m698t());
               c0795df.m618a(m696r());
               c0795df.m620a(this.f633L);
               c0795df.m625b(m697s());
               c0795df.m627c(this.f636O);
                        try {
                   c0794de = new C0794de(c0795df);
                        } catch (IOException e) {
                   e.printStackTrace();
                   c0794de = null;
                        }
               if (c0794de == null) {
                   C0827el.m900d(f621g, "New session report wasn't created");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return c0794de;
                }

                
       public synchronized void m683e() {
                    try {
                        this.f637P++;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       int m684f() {
           return this.f637P;
                }

                
       public synchronized void m677a(String str, Map<String, String> map, boolean z) {
                    try {
                        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.f659x;
               String strM959a = C0843fb.m959a(str);
               if (strM959a.length() != 0) {
                   C0784cu.a aVar = this.f631J.get(strM959a);
                   if (aVar == null) {
                       if (this.f631J.size() < f615a) {
                           C0784cu.a aVar2 = new C0784cu.a();
                           aVar2.f535a = 1;
                           this.f631J.put(strM959a, aVar2);
                           C0827el.m900d(f621g, "Event count started: " + strM959a);
                                } else {
                           C0827el.m900d(f621g, "Too many different events. Event not counted: " + strM959a);
                                }
                            } else {
                       aVar.f535a++;
                       C0827el.m900d(f621g, "Event count incremented: " + strM959a);
                            }
                   if (this.f626E && this.f632K.size() < f617c && this.f634M < f618d) {
                       Map<String, String> mapEmptyMap = map == null ? Collections.emptyMap() : map;
                       if (mapEmptyMap.size() > f616b) {
                           C0827el.m900d(f621g, "MaxEventParams exceeded: " + mapEmptyMap.size());
                                } else {
                           C0788cy c0788cy = new C0788cy(m652G(), strM959a, mapEmptyMap, jElapsedRealtime, z);
                           if (c0788cy.m566d() + this.f634M <= f618d) {
                               this.f632K.add(c0788cy);
                                        this.f634M = c0788cy.m566d() + this.f634M;
                                    } else {
                               this.f634M = f618d;
                               this.f633L = false;
                               C0827el.m900d(f621g, "Event Log size exceeded. No more event details logged.");
                                    }
                                }
                            } else {
                       this.f633L = false;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       public synchronized void m676a(String str, Map<String, String> map) {
                    try {
                        for (C0788cy c0788cy : this.f632K) {
                   if (c0788cy.m562a(str)) {
                       long jElapsedRealtime = SystemClock.elapsedRealtime() - this.f659x;
                       if (map != null && map.size() > 0 && this.f634M < f618d) {
                           int iM566d = this.f634M - c0788cy.m566d();
                           HashMap map2 = new HashMap(c0788cy.m565c());
                           c0788cy.m560a(map);
                           if (c0788cy.m566d() + iM566d <= f618d) {
                               if (c0788cy.m565c().size() > f616b) {
                                   C0827el.m900d(f621g, "MaxEventParams exceeded on endEvent: " + c0788cy.m565c().size());
                                   c0788cy.m563b(map2);
                                        } else {
                                   this.f634M = iM566d + c0788cy.m566d();
                                        }
                                    } else {
                               c0788cy.m563b(map2);
                               this.f633L = false;
                               this.f634M = f618d;
                               C0827el.m900d(f621g, "Event Log size exceeded. No more event details logged.");
                                    }
                                }
                       c0788cy.m559a(jElapsedRealtime);
                                break;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
                
       public synchronized void m675a(String str, String str2, String str3, Throwable th) {
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
               this.f636O++;
               if (this.f635N.size() < f619e) {
                   C0787cx c0787cx = new C0787cx(m653H(), Long.valueOf(System.currentTimeMillis()).longValue(), str, str2, str3, th);
                   this.f635N.add(c0787cx);
                   C0827el.m900d(f621g, "Error logged: " + c0787cx.m558c());
                        } else if (z) {
                            while (true) {
                                int i2 = i;
                       if (i2 >= this.f635N.size()) {
                                    break;
                                }
                       C0787cx c0787cx2 = this.f635N.get(i2);
                       if (c0787cx2.m558c() == null || "uncaught".equals(c0787cx2.m558c())) {
                           i = i2 + 1;
                                } else {
                           this.f635N.set(i2, new C0787cx(m653H(), Long.valueOf(System.currentTimeMillis()).longValue(), str, str2, str3, th));
                                    break;
                                }
                            }
                        } else {
                   C0827el.m900d(f621g, "Max errors logged. No more errors logged.");
                        }
                    }
                }

                
       private void m646A() {
                    try {
               C0827el.m885a(3, f621g, "generating agent report");
               C0789cz c0789cz = new C0789cz(this.f646k, this.f647l, this.f652q, m693o(), this.f653r, this.f658w, this.f654s, m694p(), this.f638Q.m552a(false), m672a(), C0784cu.m532a().m543b(), System.currentTimeMillis());
               this.f650o = new ArrayList(this.f654s);
               if (c0789cz != null && c0789cz.m568a() != null) {
                   C0827el.m885a(3, f621g, "generated report of size " + c0789cz.m568a().length + " with " + this.f654s.size() + " reports.");
                   m659a(c0789cz.m568a());
                   this.f654s.removeAll(this.f650o);
                   this.f650o = null;
                   m647B();
                        } else {
                   C0827el.m900d(f621g, "Error generating report");
                        }
                    } catch (Throwable th) {
               C0827el.m886a(6, f621g, "", th);
                    }
                }

                
       private void m659a(byte[] bArr) {
           FlurryAnalyticsModule.getInstance().m527a().m584b(bArr, this.f646k, "" + C0800dk.m720a().m721b());
                }

                
       private synchronized void m647B() {
                    try {
               if (!C0842fa.m954a(this.f649n)) {
                   C0827el.m900d(f621g, "Error persisting report: could not create directory");
                        } else {
                            try {
                       DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(this.f649n));
                       C0797dh c0797dh = new C0797dh();
                       c0797dh.m704a(this.f652q);
                       c0797dh.m701a(this.f653r);
                       c0797dh.m703a(this.f654s);
                       c0797dh.m702a(dataOutputStream, this.f646k, m687i());
                            } catch (Exception e) {
                       C0827el.m894b(f621g, "Error saving persistent data", e);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       private synchronized void m648C() {
                    DataInputStream dataInputStream;
                    FileInputStream fileInputStream;
                    boolean z;
                    FileInputStream fileInputStream2 = null;
                    synchronized (this) {
                        try {
                            C0827el.m885a(4, f621g, "Loading persistent data: " + this.f649n.getAbsolutePath());
                   if (this.f649n.exists()) {
                                try {
                           fileInputStream = new FileInputStream(this.f649n);
                                    try {
                               dataInputStream = new DataInputStream(fileInputStream);
                                        try {
                                   C0797dh c0797dh = new C0797dh();
                                   c0797dh.m706a(dataInputStream, this.f646k);
                                   this.f652q = c0797dh.m705a();
                                   this.f653r = c0797dh.m708c();
                                   this.f654s = c0797dh.m707b();
                                   z = true;
                                   C0843fb.m962a(dataInputStream);
                                   C0843fb.m962a(fileInputStream);
                                        } catch (Exception e) {
                                            e = e;
                                            fileInputStream2 = fileInputStream;
                                            try {
                                       C0827el.m894b(f621g, "Error loading persistent data", e);
                                       C0843fb.m962a(dataInputStream);
                                       C0843fb.m962a(fileInputStream2);
                                                z = false;
                                            } catch (Error th) {
                                                th = th;
                                                fileInputStream = fileInputStream2;
                                                C0843fb.m962a(dataInputStream);
                                       C0843fb.m962a(fileInputStream);
                                                throw th;
                                            }
                                        } catch (Error th2) {
                                            C0843fb.m962a(dataInputStream);
                                   C0843fb.m962a(fileInputStream);
                                            throw th2;
                                        }
                                    } catch (Exception e2) {
                               C0827el.m894b(f621g, "Error loading persistent data", e2);
                                        dataInputStream = null;
                                        fileInputStream2 = fileInputStream;
                                        z = false;
                                    } catch (Error th3) {
                                        dataInputStream = null;
                                        throw th3;
                                    }
                                } catch (Exception e3) {
                           C0827el.m894b(f621g, "Error loading persistent data", e3);
                                        dataInputStream = null;
                                        z = false;
                                } catch (Error th4) {
                                        dataInputStream = null;
                                        fileInputStream = null;
                                        throw th4;
                                }
                       if (!z) {
                           C0827el.m885a(3, f621g, "Deleting agent cache file");
                           this.f649n.delete();
                                }
                            } else {
                       C0827el.m885a(4, f621g, "Agent cache file doesn't exist.");
                                z = false;
                            }
                   if (!z) {
                       this.f652q = false;
                       this.f653r = this.f658w;
                            }
                        } catch (Error th5) {
                            throw th5;
                        }
                    }
                }

                
       private void m649D() {
           this.f641T++;
                }

                
       private void m650E() {
           if (this.f641T > 0) {
               this.f641T--;
                    }
                }

                
       int m685g() {
           return this.f641T;
                }

                
       private String m651F() {
           return ".flurryagent." + Integer.toString(this.f646k.hashCode(), 16);
                }

                
       private int m652G() {
           return this.f643h.incrementAndGet();
                }

                
       private int m653H() {
           return this.f644i.incrementAndGet();
                }

                
       String m686h() {
                    return this.f627F == null ? "" : this.f627F;
                }

                
       public String m687i() {
           return this.f657v;
                }

                
       public String m688j() {
           return this.f646k;
                }

                
       public String m689k() {
           return this.f622A;
                }

                
       public String m690l() {
           return this.f623B;
                }

                
       public long m691m() {
           return this.f658w;
                }

                
       public long m692n() {
           return this.f659x;
                }

                
       public boolean m693o() {
                    return this.f655t == null || !this.f655t.isLimitAdTrackingEnabled();
                }

                
       private void m658a(EnumC0804do enumC0804do, ByteBuffer byteBuffer) {
                    synchronized (this.f651p) {
                        try {
                   this.f651p.put(enumC0804do, byteBuffer);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
       public Map<EnumC0804do, ByteBuffer> m694p() {
           return new HashMap(this.f651p);
                }

                @Override 
                
       public void mo695q() {
           m680c();
                }

                
       List<C0788cy> m696r() {
           return this.f632K;
                }

                
       List<C0787cx> m697s() {
           return this.f635N;
                }

                
       Map<String, C0784cu.a> m698t() {
           return this.f631J;
                }
            }
