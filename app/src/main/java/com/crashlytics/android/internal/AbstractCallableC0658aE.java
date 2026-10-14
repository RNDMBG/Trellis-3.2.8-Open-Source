            package com.crashlytics.android.internal;

            import java.io.IOException;
            import java.util.concurrent.Callable;
            
            
            
   public abstract class AbstractCallableC0658aE<V> implements Callable<V> {
                
                protected abstract V mo162a() throws C0657aD, IOException;

                
                protected abstract void mo160b() throws IOException;

       protected AbstractCallableC0658aE() {
                }

                @Override 
       public V call() throws Throwable {
                    boolean z = true;
                    try {
                        try {
                            try {
                       V vMo162a = mo162a();
                                try {
                           mo160b();
                           return vMo162a;
                                } catch (IOException e) {
                           throw new C0657aD(e);
                                }
                            } catch (C0657aD e2) {
                       throw e2;
                            }
                        } catch (IOException e3) {
                   throw new C0657aD(e3);
                        } catch (Throwable th) {
                   th = th;
                   z = false;
                            try {
                       mo160b();
                            } catch (IOException e4) {
                       if (!z) {
                           throw new C0657aD(e4);
                                }
                            }
                   throw th;
                        }
                    } catch (Throwable th2) {
               th = th2;
               mo160b();
               throw th;
                    }
                }
            }
