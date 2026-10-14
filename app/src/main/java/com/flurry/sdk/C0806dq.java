            package com.flurry.sdk;

            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.Iterator;
            import java.util.LinkedList;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dq */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class C0806dq<T> {

                /* JADX INFO: renamed from: a */
                private final List<WeakReference<T>> f717a = new LinkedList();

                /* JADX INFO: renamed from: a */
/* 22 */        public void m760a(T t) {
/* 23 */            if (t != null) {
/* 27 */                this.f717a.add(new WeakReference<>(t));
                    }
                }

                /* JADX INFO: renamed from: b */
/* 30 */        public boolean m762b(T t) {
/* 31 */            if (t == null) {
                        return false;
                    }
/* 35 */            Iterator<WeakReference<T>> it = this.f717a.iterator();
/* 38 */            while (it.hasNext()) {
/* 41 */                T t2 = it.next().get();
/* 42 */                if (t2 == null) {
/* 43 */                    it.remove();
                        } else if (t == t2 || t.equals(t2)) {
/* 45 */                    it.remove();
/* 46 */                    return true;
                        }
                    }
                    return false;
                }

                /* JADX INFO: renamed from: a */
/* 53 */        public List<T> m759a() {
/* 54 */            ArrayList arrayList = new ArrayList(this.f717a.size());
/* 56 */            Iterator<WeakReference<T>> it = this.f717a.iterator();
/* 59 */            while (it.hasNext()) {
/* 62 */                T t = it.next().get();
/* 63 */                if (t == null) {
/* 64 */                    it.remove();
                        } else {
/* 66 */                    arrayList.add(t);
                        }
                    }
/* 70 */            return arrayList;
                }

                /* JADX INFO: renamed from: b */
/* 73 */        public int m761b() {
/* 74 */            return this.f717a.size();
                }
            }
