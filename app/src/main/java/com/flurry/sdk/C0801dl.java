            package com.flurry.sdk;

            import android.content.Context;
            import android.content.pm.PackageManager;
            import android.os.Handler;
            import android.os.HandlerThread;
            import android.os.Looper;
            
            
            
    public class C0801dl {

                
                private static C0801dl f688a;

                
                private final Context f689b;

                
                private final Handler f690c = new Handler(Looper.getMainLooper());

                
                private final Handler f691d;

                
        public static synchronized void m733a(Context context) {
                    try {
                        if (f688a == null) {
                    if (context == null) {
                        throw new IllegalArgumentException("Context cannot be null");
                            }
                    f688a = new C0801dl(context);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public static C0801dl m732a() {
            return f688a;
                }

        private C0801dl(Context context) {
            this.f689b = context.getApplicationContext();
            HandlerThread handlerThread = new HandlerThread("FlurryAgent");
            handlerThread.start();
            this.f691d = new Handler(handlerThread.getLooper());
                }

                
        public Context m736b() {
            return this.f689b;
                }

                
        public PackageManager m739c() {
            return this.f689b.getPackageManager();
                }

                
        public void m734a(Runnable runnable) {
            if (runnable != null) {
                this.f690c.post(runnable);
                    }
                }

                
        public void m735a(Runnable runnable, long j) {
            if (runnable != null) {
                this.f690c.postDelayed(runnable, j);
                    }
                }

                
        public void m737b(Runnable runnable) {
            if (runnable != null) {
                this.f690c.removeCallbacks(runnable);
                    }
                }

                
       public void m740c(Runnable runnable) {
           if (runnable != null) {
               this.f691d.post(runnable);
                    }
                }

                
       public void m738b(Runnable runnable, long j) {
           if (runnable != null) {
               this.f691d.postDelayed(runnable, j);
                    }
                }

                
       public void m741d(Runnable runnable) {
           if (runnable != null) {
               this.f691d.removeCallbacks(runnable);
                    }
                }
            }
