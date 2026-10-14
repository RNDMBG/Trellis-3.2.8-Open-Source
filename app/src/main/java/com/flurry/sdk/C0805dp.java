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
            
            /* JADX INFO: renamed from: com.flurry.sdk.dp */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class C0805dp<K, V> {

                /* JADX INFO: renamed from: a */
                private final Map<K, List<V>> f715a = new HashMap();

                /* JADX INFO: renamed from: b */
                private int f716b;

                /* JADX INFO: renamed from: a */
/* 27 */        public void m752a() {
/* 28 */            this.f715a.clear();
                }

                /* JADX INFO: renamed from: a */
/* 31 */        public List<V> m751a(K k) {
/* 32 */            if (k == null) {
/* 33 */                return Collections.emptyList();
                    }
/* 36 */            List<V> listM750a = m750a((Object) k, false);
/* 39 */            if (listM750a == null) {
/* 40 */                return Collections.emptyList();
                    }
/* 43 */            return listM750a;
                }

                /* JADX INFO: renamed from: a */
/* 46 */        public void m754a(K k, V v) {
/* 47 */            if (k != null) {
/* 52 */                m750a((Object) k, true).add(v);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 55 */        public void m753a(C0805dp<K, V> c0805dp) {
/* 56 */            if (c0805dp != null) {
                        for (Map.Entry<K, List<V>> entry : c0805dp.f715a.entrySet()) {
/* 62 */                    this.f715a.put(entry.getKey(), entry.getValue());
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 66 */        public boolean m757b(K k, V v) {
                    List<V> listM750a;
                    boolean zRemove = false;
/* 67 */            if (k != null && (listM750a = m750a((Object) k, false)) != null) {
/* 73 */                zRemove = listM750a.remove(v);
/* 74 */                if (listM750a.size() == 0) {
/* 75 */                    this.f715a.remove(k);
                        }
                    }
/* 81 */            return zRemove;
                }

                /* JADX INFO: renamed from: b */
/* 84 */        public boolean m756b(K k) {
/* 85 */            if (k == null) {
                        return false;
                    }
/* 90 */            return this.f715a.remove(k) != null;
                }

                /* JADX INFO: renamed from: b */
/* 114 */       public Collection<Map.Entry<K, V>> m755b() {
/* 115 */           ArrayList arrayList = new ArrayList();
                    for (Map.Entry<K, List<V>> entry : this.f715a.entrySet()) {
/* 119 */               Iterator<V> it = entry.getValue().iterator();
                        while (it.hasNext()) {
/* 120 */                   arrayList.add(new AbstractMap.SimpleImmutableEntry(entry.getKey(), it.next()));
                        }
                    }
/* 124 */           return arrayList;
                }

                /* JADX INFO: renamed from: c */
/* 127 */       public Set<K> m758c() {
/* 128 */           return this.f715a.keySet();
                }

                /* JADX INFO: renamed from: a */
/* 157 */       private List<V> m750a(K k, boolean z) {
/* 158 */           List<V> arrayList = this.f715a.get(k);
/* 159 */           if (z && arrayList == null) {
/* 160 */               if (this.f716b > 0) {
/* 161 */                   arrayList = new ArrayList<>(this.f716b);
                        } else {
/* 163 */                   arrayList = new ArrayList<>();
                        }
/* 166 */               this.f715a.put(k, arrayList);
                    }
/* 169 */           return arrayList;
                }
            }
