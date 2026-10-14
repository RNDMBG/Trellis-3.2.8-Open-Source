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
            
            
            
    public final class C0727v extends AbstractC0721p {

                
                private boolean f463c;

                
                private File f464d;

                
                private Application f465e;

                
                private WeakReference<Activity> f466f;

                
                private String f467g;

                
                private AtomicReference<InterfaceC0722q> f462b = new AtomicReference<>();

                
                private int f468h = 4;

                
                private C0706b f461a = new C0628B(InterfaceC0718m.f457a);

                
                private ConcurrentHashMap<Class<? extends AbstractC0726u>, AbstractC0726u> f469i = new ConcurrentHashMap<>();

                
        static  C0727v m359a(C0727v c0727v, Activity activity) {
            return c0727v.m358a(activity);
                }

                
        public static C0727v m357a() {
            return C0730y.m373a();
                }

        C0727v() {
                }

                
        public static synchronized void m360a(Context context, AbstractC0726u... abstractC0726uArr) {
                    try {
                        if (!C0730y.m373a().isInitialized()) {
                    C0727v c0727vM373a = C0730y.m373a();
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

                
       public final InterfaceC0722q m365b() {
           InterfaceC0722q interfaceC0722q = this.f462b.get();
           if (interfaceC0722q == null) {
               C0723r c0723r = new C0723r();
               if (!this.f462b.compareAndSet(null, c0723r)) {
                   return this.f462b.get();
                        }
               return c0723r;
                    }
           return interfaceC0722q;
                }

                
       public final void m362a(InterfaceC0722q interfaceC0722q) {
           this.f462b.set(interfaceC0722q);
                }

                
       public final Application m366d() {
           return this.f465e;
                }

                
       private C0727v m358a(Activity activity) {
           this.f466f = new WeakReference<>(activity);
           return this;
                }

                
       public final Activity m367e() {
           if (this.f466f != null) {
               return this.f466f.get();
                    }
                    return null;
                }

                @Override 
                
       protected final void mo28c() {
           Context context = getContext();
           this.f464d = new File(context.getFilesDir(), "com.crashlytics.sdk.android");
                    if (!this.f464d.exists()) {
                        this.f464d.mkdirs();
                    }
           if (Build.VERSION.SDK_INT >= 14) {
               C0728w.m372a(new C0728w(this, (byte) 0), this.f465e);
                    }
           if (this.f463c && Log.isLoggable("CrashlyticsInternal", 3)) {
               StringBuilder sb = new StringBuilder();
                        for (AbstractC0726u abstractC0726u : this.f469i.values()) {
                   long jNanoTime = System.nanoTime();
                   abstractC0726u.m351a(context);
                   sb.append("sdkPerfStart.").append(abstractC0726u.getClass().getName()).append('=').append(System.nanoTime() - jNanoTime).append('\n');
                        }
               Log.d("CrashlyticsInternal", sb.toString());
               return;
                    }
           Iterator<AbstractC0726u> it = this.f469i.values().iterator();
                    while (it.hasNext()) {
               it.next().m351a(context);
                    }
                }

                @Override 
       public final String getVersion() {
           return "1.1.13.29";
                }

                
       public final <T extends AbstractC0726u> T m361a(Class<T> cls) {
           return (T) this.f469i.get(cls);
                }

                
       public final void m364a(boolean z) {
           this.f463c = z;
           this.f468h = z ? 3 : 4;
                }

                
       public final boolean m368f() {
           return this.f463c;
                }

                
       public final int m369g() {
           return this.f468h;
                }

                
       public final File m370h() {
           return this.f464d;
                }

                
       public final void m363a(String str) {
           this.f467g = str;
                }

                
       public final String m371i() {
           return this.f467g;
                }
            }
