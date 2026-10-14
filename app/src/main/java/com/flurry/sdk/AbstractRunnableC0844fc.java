            package com.flurry.sdk;

            import java.io.PrintStream;
            import java.io.PrintWriter;
            
            
            
    public abstract class AbstractRunnableC0844fc implements Runnable {

                
                private static final String f849a = AbstractRunnableC0844fc.class.getSimpleName();

                
                PrintStream f850g;

                
                PrintWriter f851h;

                
                public abstract void mo576a();

                @Override 
        public final void run() {
                    try {
                mo576a();
                    } catch (Throwable th) {
                if (this.f850g != null) {
                    th.printStackTrace(this.f850g);
                        } else if (this.f851h != null) {
                    th.printStackTrace(this.f851h);
                        } else {
                    th.printStackTrace();
                        }
                C0827el.m886a(6, f849a, "", th);
                    }
                }
            }
