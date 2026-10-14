            package com.flurry.sdk;

            import java.util.AbstractMap;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Map;
            import java.util.Set;
            
            
            
    public class C0805dp<K, V> {

                
                private final Map<K, List<V>> f715a = new HashMap();

                
                private int f716b;

                
        public void m752a() {
            this.f715a.clear();
                }

                
        public List<V> m751a(K k) {
            if (k == null) {
                return Collections.emptyList();
                    }
            List<V> listM750a = m750a((Object) k, false);
            if (listM750a == null) {
                return Collections.emptyList();
                    }
            return listM750a;
                }

                
        public void m754a(K k, V v) {
            if (k != null) {
                m750a((Object) k, true).add(v);
                    }
                }

                
        public void m753a(C0805dp<K, V> c0805dp) {
            if (c0805dp != null) {
                        for (Map.Entry<K, List<V>> entry : c0805dp.f715a.entrySet()) {
                    this.f715a.put(entry.getKey(), entry.getValue());
                        }
                    }
                }

                
        public boolean m757b(K k, V v) {
                    List<V> listM750a;
                    boolean zRemove = false;
            if (k != null && (listM750a = m750a((Object) k, false)) != null) {
                zRemove = listM750a.remove(v);
                if (listM750a.size() == 0) {
                    this.f715a.remove(k);
                        }
                    }
            return zRemove;
                }

                
        public boolean m756b(K k) {
            if (k == null) {
                        return false;
                    }
            return this.f715a.remove(k) != null;
                }

                
       public Collection<Map.Entry<K, V>> m755b() {
           ArrayList arrayList = new ArrayList();
                    for (Map.Entry<K, List<V>> entry : this.f715a.entrySet()) {
               Iterator<V> it = entry.getValue().iterator();
                        while (it.hasNext()) {
                   arrayList.add(new AbstractMap.SimpleImmutableEntry(entry.getKey(), it.next()));
                        }
                    }
           return arrayList;
                }

                
       public Set<K> m758c() {
           return this.f715a.keySet();
                }

                
       private List<V> m750a(K k, boolean z) {
           List<V> arrayList = this.f715a.get(k);
           if (z && arrayList == null) {
               if (this.f716b > 0) {
                   arrayList = new ArrayList<>(this.f716b);
                        } else {
                   arrayList = new ArrayList<>();
                        }
               this.f715a.put(k, arrayList);
                    }
           return arrayList;
                }
            }
