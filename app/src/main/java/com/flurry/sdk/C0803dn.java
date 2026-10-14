            package com.flurry.sdk;

            import android.text.TextUtils;
            import java.util.HashMap;
            import java.util.Iterator;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.Map;
            
            
            
    public class C0803dn {

                
                private Map<String, Object> f707a = new HashMap();

                
                private Map<String, List<a>> f708b = new HashMap();

                
                public interface a {
                    
                    void mo535a(String str, Object obj);
                }

                
        public synchronized void m748a(String str, Object obj) {
                    try {
                if (!TextUtils.isEmpty(str) && !m744a(obj, this.f707a.get(str))) {
                    if (obj == null) {
                        this.f707a.remove(str);
                            } else {
                        this.f707a.put(str, obj);
                            }
                    m745b(str, obj);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        private boolean m744a(Object obj, Object obj2) {
                    return obj == obj2 || (obj != null && obj.equals(obj2));
                }

                
        private void m745b(String str, Object obj) {
            if (this.f708b.get(str) != null) {
                Iterator<a> it = this.f708b.get(str).iterator();
                        while (it.hasNext()) {
                    it.next().mo535a(str, obj);
                        }
                    }
                }

                
        public synchronized Object m746a(String str) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.f707a.get(str);
                }

                
        public synchronized void m747a(String str, a aVar) {
                    try {
                if (!TextUtils.isEmpty(str) && aVar != null) {
                    List<a> linkedList = this.f708b.get(str);
                    if (linkedList == null) {
                        linkedList = new LinkedList<>();
                            }
                    linkedList.add(aVar);
                    this.f708b.put(str, linkedList);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized boolean m749b(String str, a aVar) {
                    List<a> list;
                    boolean zRemove;
                    try {
               zRemove = (TextUtils.isEmpty(str) || aVar == null || (list = this.f708b.get(str)) == null) ? false : list.remove(aVar);
                    } catch (Throwable th) {
                        throw th;
                    }
                    return zRemove;
                }
            }
