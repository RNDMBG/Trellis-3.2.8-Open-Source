            package com.flurry.sdk;

            import java.util.Comparator;
            
            
            
    public class C0817eb implements Comparator<Runnable> {

                
                private static final String f760a = C0817eb.class.getSimpleName();

                @Override 
        public  int compare(Runnable runnable, Runnable runnable2) {
            return m825a(runnable, runnable2);
                }

                
        public int m825a(Runnable runnable, Runnable runnable2) {
            int iM824a = m824a(runnable);
            int iM824a2 = m824a(runnable2);
            if (iM824a < iM824a2) {
                return -1;
                    }
            if (iM824a > iM824a2) {
                return 1;
                    }
            return 0;
                }

                
        private int m824a(Runnable runnable) {
                    int iM973i;
            if (runnable == null) {
                return Integer.MAX_VALUE;
                    }
            if (runnable instanceof C0818ec) {
                AbstractC0845fd abstractC0845fd = (AbstractC0845fd) ((C0818ec) runnable).m826a();
                if (abstractC0845fd == null) {
                    iM973i = Integer.MAX_VALUE;
                        } else {
                    iM973i = abstractC0845fd.m973i();
                        }
                        return iM973i;
                    }
            if (runnable instanceof AbstractC0845fd) {
                return ((AbstractC0845fd) runnable).m973i();
                    }
            C0827el.m885a(6, f760a, "Unknown runnable class: " + runnable.getClass().getName());
            return Integer.MAX_VALUE;
                }
            }
