            package com.flurry.sdk;

            import android.text.TextUtils;
            import java.util.HashMap;
            import java.util.Iterator;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dn */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class C0803dn {

                /* JADX INFO: renamed from: a */
                private Map<String, Object> f707a = new HashMap();

                /* JADX INFO: renamed from: b */
                private Map<String, List<a>> f708b = new HashMap();

                /* JADX INFO: renamed from: com.flurry.sdk.dn$a */
                public interface a {
                    /* JADX INFO: renamed from: a */
                    void mo535a(String str, Object obj);
                }

                /* JADX INFO: renamed from: a */
/* 40 */        public synchronized void m748a(String str, Object obj) {
                    try {
/* 47 */                if (!TextUtils.isEmpty(str) && !m744a(obj, this.f707a.get(str))) {
/* 49 */                    if (obj == null) {
/* 50 */                        this.f707a.remove(str);
                            } else {
/* 52 */                        this.f707a.put(str, obj);
                            }
/* 55 */                    m745b(str, obj);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 59 */        private boolean m744a(Object obj, Object obj2) {
                    return obj == obj2 || (obj != null && obj.equals(obj2));
                }

                /* JADX INFO: renamed from: b */
/* 63 */        private void m745b(String str, Object obj) {
/* 64 */            if (this.f708b.get(str) != null) {
/* 66 */                Iterator<a> it = this.f708b.get(str).iterator();
                        while (it.hasNext()) {
/* 67 */                    it.next().mo535a(str, obj);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 72 */        public synchronized Object m746a(String str) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f707a.get(str);
                }

                /* JADX INFO: renamed from: a */
/* 76 */        public synchronized void m747a(String str, a aVar) {
                    try {
/* 81 */                if (!TextUtils.isEmpty(str) && aVar != null) {
/* 85 */                    List<a> linkedList = this.f708b.get(str);
/* 86 */                    if (linkedList == null) {
/* 87 */                        linkedList = new LinkedList<>();
                            }
/* 90 */                    linkedList.add(aVar);
/* 91 */                    this.f708b.put(str, linkedList);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: b */
/* 95 */        public synchronized boolean m749b(String str, a aVar) {
                    List<a> list;
                    boolean zRemove;
                    try {
/* 109 */               zRemove = (TextUtils.isEmpty(str) || aVar == null || (list = this.f708b.get(str)) == null) ? false : list.remove(aVar);
                    } catch (Throwable th) {
                        throw th;
                    }
                    return zRemove;
                }
            }
