            package com.crashlytics.android.internal;

            import android.app.Activity;
            import android.app.Application;
            import android.content.Context;
            import android.os.Build;
            import android.util.Log;
            import java.io.File;
            import java.lang.ref.WeakReference;
            import java.util.Iterator;
            import java.util.concurrent.ConcurrentHashMap;
            import java.util.concurrent.atomic.AtomicReference;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.v */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public final class C0727v extends AbstractC0721p {

                /* JADX INFO: renamed from: c */
                private boolean f463c;

                /* JADX INFO: renamed from: d */
                private File f464d;

                /* JADX INFO: renamed from: e */
                private Application f465e;

                /* JADX INFO: renamed from: f */
                private WeakReference<Activity> f466f;

                /* JADX INFO: renamed from: g */
                private String f467g;

                /* JADX INFO: renamed from: b */
                private AtomicReference<InterfaceC0722q> f462b = new AtomicReference<>();

                /* JADX INFO: renamed from: h */
                private int f468h = 4;

                /* JADX INFO: renamed from: a */
                private C0706b f461a = new C0628B(InterfaceC0718m.f457a);

                /* JADX INFO: renamed from: i */
                private ConcurrentHashMap<Class<? extends AbstractC0726u>, AbstractC0726u> f469i = new ConcurrentHashMap<>();

                /* JADX INFO: renamed from: a */
/* 21 */        static /* synthetic */ C0727v m359a(C0727v c0727v, Activity activity) {
/* 22 */            return c0727v.m358a(activity);
                }

                /* JADX INFO: renamed from: a */
/* 61 */        public static C0727v m357a() {
/* 62 */            return C0730y.m373a();
                }

/* 73 */        C0727v() {
                }

                /* JADX INFO: renamed from: a */
/* 88 */        public static synchronized void m360a(Context context, AbstractC0726u... abstractC0726uArr) {
                    try {
                        if (!C0730y.m373a().isInitialized()) {
/* 93 */                    C0727v c0727vM373a = C0730y.m373a();
                            c0727vM373a.f465e = C0723r.m355b(context);
                            C0727v c0727vM358a = c0727vM373a.m358a(C0723r.m353a(context));
                            for (AbstractC0726u abstractC0726u : abstractC0726uArr) {
                                if (!c0727vM358a.f469i.containsKey(abstractC0726uArr)) {
                                    c0727vM358a.f469i.putIfAbsent((Class<? extends AbstractC0726u>) abstractC0726u.getClass(), abstractC0726u);
                                }
                            }
                            c0727vM358a.m351a(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 105 */       public final InterfaceC0722q m365b() {
/* 106 */           InterfaceC0722q interfaceC0722q = this.f462b.get();
/* 108 */           if (interfaceC0722q == null) {
/* 109 */               C0723r c0723r = new C0723r();
/* 111 */               if (!this.f462b.compareAndSet(null, c0723r)) {
/* 112 */                   return this.f462b.get();
                        }
/* 115 */               return c0723r;
                    }
/* 115 */           return interfaceC0722q;
                }

                /* JADX INFO: renamed from: a */
/* 123 */       public final void m362a(InterfaceC0722q interfaceC0722q) {
/* 124 */           this.f462b.set(interfaceC0722q);
                }

                /* JADX INFO: renamed from: d */
/* 130 */       public final Application m366d() {
/* 131 */           return this.f465e;
                }

                /* JADX INFO: renamed from: a */
/* 139 */       private C0727v m358a(Activity activity) {
/* 140 */           this.f466f = new WeakReference<>(activity);
/* 141 */           return this;
                }

                /* JADX INFO: renamed from: e */
/* 148 */       public final Activity m367e() {
/* 149 */           if (this.f466f != null) {
/* 150 */               return this.f466f.get();
                    }
                    return null;
                }

                @Override // com.crashlytics.android.internal.AbstractC0721p
                /* JADX INFO: renamed from: c */
/* 156 */       protected final void mo28c() {
/* 157 */           Context context = getContext();
/* 158 */           this.f464d = new File(context.getFilesDir(), "com.crashlytics.sdk.android");
                    if (!this.f464d.exists()) {
                        this.f464d.mkdirs();
                    }
/* 160 */           if (Build.VERSION.SDK_INT >= 14) {
/* 161 */               C0728w.m372a(new C0728w(this, (byte) 0), this.f465e);
                    }
/* 165 */           if (this.f463c && Log.isLoggable("CrashlyticsInternal", 3)) {
/* 166 */               StringBuilder sb = new StringBuilder();
                        for (AbstractC0726u abstractC0726u : this.f469i.values()) {
/* 168 */                   long jNanoTime = System.nanoTime();
/* 169 */                   abstractC0726u.m351a(context);
/* 172 */                   sb.append("sdkPerfStart.").append(abstractC0726u.getClass().getName()).append('=').append(System.nanoTime() - jNanoTime).append('\n');
                        }
/* 178 */               Log.d("CrashlyticsInternal", sb.toString());
/* 185 */               return;
                    }
/* 181 */           Iterator<AbstractC0726u> it = this.f469i.values().iterator();
                    while (it.hasNext()) {
/* 182 */               it.next().m351a(context);
                    }
                }

                @Override // com.crashlytics.android.internal.AbstractC0721p
/* 191 */       public final String getVersion() {
/* 192 */           return "1.1.13.29";
                }

                /* JADX INFO: renamed from: a */
/* 212 */       public final <T extends AbstractC0726u> T m361a(Class<T> cls) {
/* 213 */           return (T) this.f469i.get(cls);
                }

                /* JADX INFO: renamed from: a */
/* 232 */       public final void m364a(boolean z) {
/* 233 */           this.f463c = z;
/* 236 */           this.f468h = z ? 3 : 4;
                }

                /* JADX INFO: renamed from: f */
/* 244 */       public final boolean m368f() {
/* 245 */           return this.f463c;
                }

                /* JADX INFO: renamed from: g */
/* 248 */       public final int m369g() {
/* 249 */           return this.f468h;
                }

                /* JADX INFO: renamed from: h */
/* 260 */       public final File m370h() {
/* 261 */           return this.f464d;
                }

                /* JADX INFO: renamed from: a */
/* 268 */       public final void m363a(String str) {
/* 269 */           this.f467g = str;
                }

                /* JADX INFO: renamed from: i */
/* 272 */       public final String m371i() {
/* 273 */           return this.f467g;
                }
            }
