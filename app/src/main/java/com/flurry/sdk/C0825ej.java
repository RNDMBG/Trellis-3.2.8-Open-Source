            package com.flurry.sdk;

            import java.util.concurrent.PriorityBlockingQueue;
            import java.util.concurrent.TimeUnit;
            
            
            
    public final class C0825ej extends C0819ed<C0826ek> {

                
                private static C0825ej f787a = null;

                
        public static synchronized C0825ej m856a() {
                    try {
                        if (f787a == null) {
                    f787a = new C0825ej();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f787a;
                }

        protected C0825ej() {
            super(C0825ej.class.getName(), 0, 5, 5000L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(11, new C0817eb()));
                }
            }
