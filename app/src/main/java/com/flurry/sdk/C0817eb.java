            package com.flurry.sdk;

            import java.util.Comparator;
            
            /* JADX INFO: renamed from: com.flurry.sdk.eb */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class C0817eb implements Comparator<Runnable> {

                /* JADX INFO: renamed from: a */
                private static final String f760a = C0817eb.class.getSimpleName();

                @Override // java.util.Comparator
/* 12 */        public /* synthetic */ int compare(Runnable runnable, Runnable runnable2) {
/* 13 */            return m825a(runnable, runnable2);
                }

                /* JADX INFO: renamed from: a */
/* 20 */        public int m825a(Runnable runnable, Runnable runnable2) {
/* 21 */            int iM824a = m824a(runnable);
/* 22 */            int iM824a2 = m824a(runnable2);
/* 24 */            if (iM824a < iM824a2) {
/* 25 */                return -1;
                    }
/* 26 */            if (iM824a > iM824a2) {
/* 27 */                return 1;
                    }
/* 29 */            return 0;
                }

                /* JADX INFO: renamed from: a */
/* 33 */        private int m824a(Runnable runnable) {
                    int iM973i;
/* 36 */            if (runnable == null) {
/* 34 */                return Integer.MAX_VALUE;
                    }
/* 37 */            if (runnable instanceof C0818ec) {
/* 39 */                AbstractC0845fd abstractC0845fd = (AbstractC0845fd) ((C0818ec) runnable).m826a();
/* 40 */                if (abstractC0845fd == null) {
/* 34 */                    iM973i = Integer.MAX_VALUE;
                        } else {
/* 41 */                    iM973i = abstractC0845fd.m973i();
                        }
                        return iM973i;
                    }
/* 43 */            if (runnable instanceof AbstractC0845fd) {
/* 44 */                return ((AbstractC0845fd) runnable).m973i();
                    }
/* 46 */            C0827el.m885a(6, f760a, "Unknown runnable class: " + runnable.getClass().getName());
/* 34 */            return Integer.MAX_VALUE;
                }
            }
