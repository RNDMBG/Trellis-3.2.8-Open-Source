            package com.crashlytics.android.internal;

            import java.io.Closeable;
            import java.io.Flushable;
            import java.io.IOException;
            
            
            
   public abstract class AbstractC0654aA<V> extends AbstractCallableC0658aE<V> {

                
                private final Closeable f292a;

                
                private final boolean f293b;

       protected AbstractC0654aA(Closeable closeable, boolean z) {
           this.f292a = closeable;
           this.f293b = z;
                }

                @Override 
                
       protected final void mo160b() throws IOException {
           if (this.f292a instanceof Flushable) {
               ((Flushable) this.f292a).flush();
                    }
           if (this.f293b) {
                        try {
                   this.f292a.close();
                        } catch (IOException e) {
                        }
                    } else {
               this.f292a.close();
                    }
                }
            }
