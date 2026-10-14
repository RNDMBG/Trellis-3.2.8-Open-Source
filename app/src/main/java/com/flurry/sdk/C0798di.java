            package com.flurry.sdk;

            import android.content.Context;
            import android.text.TextUtils;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Iterator;
            import java.util.Map;
            import java.util.WeakHashMap;
            
            
            
    public class C0798di {

                
                private static final String f675a = C0798di.class.getSimpleName();

                
                private static C0798di f676b;

                
                private final Map<String, C0796dg> f677c = new HashMap();

                
                private final Map<Context, C0796dg> f678d = new WeakHashMap();

                
                private final Object f679e = new Object();

                
                private C0796dg f680f;

                
        public static synchronized C0798di m709a() {
                    try {
                        if (f676b == null) {
                    f676b = new C0798di();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f676b;
                }

        private C0798di() {
                }

                
        public synchronized int m714b() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f678d.size();
                }

                
        public C0796dg m715c() {
                    C0796dg c0796dg;
                    synchronized (this.f679e) {
                        try {
                    c0796dg = this.f680f;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return c0796dg;
                }

                
        public void m712a(C0796dg c0796dg) {
                    synchronized (this.f679e) {
                        try {
                    this.f680f = c0796dg;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
        public synchronized void m711a(Context context, String str) {
                    C0796dg c0796dg;
                    try {
                        C0801dl.m733a(context);
                C0834es.m907a().m911b();
                C0812dw.m781a().m790b();
                C0796dg c0796dg2 = this.f678d.get(context);
                if (c0796dg2 != null) {
                    C0827el.m900d(f675a, "Session already started with context: " + context + " count:" + c0796dg2.m685g());
                        } else {
                    if (this.f677c.containsKey(str)) {
                        c0796dg = this.f677c.get(str);
                            } else {
                        c0796dg = new C0796dg(str);
                        this.f677c.put(str, c0796dg);
                        c0796dg.m673a(context);
                            }
                    this.f678d.put(context, c0796dg);
                    m712a(c0796dg);
                    c0796dg.m679b(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized void m710a(Context context) {
                    try {
                        C0796dg c0796dgRemove = this.f678d.remove(context);
                if (c0796dgRemove == null) {
                    C0827el.m900d(f675a, "Session cannot be ended, session not found for context: " + context);
                        } else {
                   c0796dgRemove.m681c(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       public synchronized void m713a(String str) {
                    try {
               if (!this.f677c.containsKey(str)) {
                   C0827el.m885a(6, f675a, "Ended session is not in the session map! Maybe it was already destroyed.");
                        } else {
                   C0796dg c0796dgM715c = m715c();
                   if (c0796dgM715c != null && TextUtils.equals(c0796dgM715c.m688j(), str)) {
                       m712a((C0796dg) null);
                            }
                   this.f677c.remove(str);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       public synchronized void m716d() {
                    try {
                        for (Map.Entry<Context, C0796dg> entry : this.f678d.entrySet()) {
                   entry.getValue().m681c(entry.getKey());
                        }
               this.f678d.clear();
               Iterator it = new ArrayList(this.f677c.values()).iterator();
                        while (it.hasNext()) {
                   ((C0796dg) it.next()).m680c();
                        }
               this.f677c.clear();
               m712a((C0796dg) null);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
