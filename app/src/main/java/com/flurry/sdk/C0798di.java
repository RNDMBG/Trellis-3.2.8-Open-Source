            package com.flurry.sdk;

            import android.content.Context;
            import android.text.TextUtils;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Iterator;
            import java.util.Map;
            import java.util.WeakHashMap;
            
            /* JADX INFO: renamed from: com.flurry.sdk.di */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class C0798di {

                /* JADX INFO: renamed from: a */
                private static final String f675a = C0798di.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private static C0798di f676b;

                /* JADX INFO: renamed from: c */
                private final Map<String, C0796dg> f677c = new HashMap();

                /* JADX INFO: renamed from: d */
                private final Map<Context, C0796dg> f678d = new WeakHashMap();

                /* JADX INFO: renamed from: e */
                private final Object f679e = new Object();

                /* JADX INFO: renamed from: f */
                private C0796dg f680f;

                /* JADX INFO: renamed from: a */
/* 26 */        public static synchronized C0798di m709a() {
                    try {
                        if (f676b == null) {
/* 28 */                    f676b = new C0798di();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 31 */            return f676b;
                }

/* 39 */        private C0798di() {
                }

                /* JADX INFO: renamed from: b */
/* 49 */        public synchronized int m714b() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f678d.size();
                }

                /* JADX INFO: renamed from: c */
/* 52 */        public C0796dg m715c() {
                    C0796dg c0796dg;
                    synchronized (this.f679e) {
                        try {
/* 54 */                    c0796dg = this.f680f;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return c0796dg;
                }

                /* JADX INFO: renamed from: a */
/* 59 */        public void m712a(C0796dg c0796dg) {
                    synchronized (this.f679e) {
                        try {
/* 61 */                    this.f680f = c0796dg;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 65 */        public synchronized void m711a(Context context, String str) {
                    C0796dg c0796dg;
                    try {
                        C0801dl.m733a(context);
/* 68 */                C0834es.m907a().m911b();
/* 69 */                C0812dw.m781a().m790b();
/* 72 */                C0796dg c0796dg2 = this.f678d.get(context);
/* 73 */                if (c0796dg2 != null) {
/* 74 */                    C0827el.m900d(f675a, "Session already started with context: " + context + " count:" + c0796dg2.m685g());
                        } else {
/* 79 */                    if (this.f677c.containsKey(str)) {
/* 80 */                        c0796dg = this.f677c.get(str);
                            } else {
/* 82 */                        c0796dg = new C0796dg(str);
/* 83 */                        this.f677c.put(str, c0796dg);
/* 85 */                        c0796dg.m673a(context);
                            }
/* 89 */                    this.f678d.put(context, c0796dg);
/* 91 */                    m712a(c0796dg);
/* 92 */                    c0796dg.m679b(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 96 */        public synchronized void m710a(Context context) {
                    try {
                        C0796dg c0796dgRemove = this.f678d.remove(context);
/* 98 */                if (c0796dgRemove == null) {
/* 99 */                    C0827el.m900d(f675a, "Session cannot be ended, session not found for context: " + context);
                        } else {
/* 101 */                   c0796dgRemove.m681c(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 105 */       public synchronized void m713a(String str) {
                    try {
/* 107 */               if (!this.f677c.containsKey(str)) {
/* 109 */                   C0827el.m885a(6, f675a, "Ended session is not in the session map! Maybe it was already destroyed.");
                        } else {
/* 113 */                   C0796dg c0796dgM715c = m715c();
/* 114 */                   if (c0796dgM715c != null && TextUtils.equals(c0796dgM715c.m688j(), str)) {
/* 117 */                       m712a((C0796dg) null);
                            }
/* 121 */                   this.f677c.remove(str);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: d */
/* 126 */       public synchronized void m716d() {
                    try {
                        for (Map.Entry<Context, C0796dg> entry : this.f678d.entrySet()) {
/* 129 */                   entry.getValue().m681c(entry.getKey());
                        }
/* 131 */               this.f678d.clear();
/* 135 */               Iterator it = new ArrayList(this.f677c.values()).iterator();
                        while (it.hasNext()) {
/* 136 */                   ((C0796dg) it.next()).m680c();
                        }
/* 138 */               this.f677c.clear();
/* 140 */               m712a((C0796dg) null);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
