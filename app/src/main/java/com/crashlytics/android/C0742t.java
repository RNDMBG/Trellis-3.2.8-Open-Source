            package com.crashlytics.android;

            import android.content.Context;
            import com.crashlytics.android.internal.AbstractRunnableC0680aa;
            import com.crashlytics.android.internal.C0727v;
            import java.util.concurrent.CountDownLatch;
            
            
            
   final class C0742t extends AbstractRunnableC0680aa {

                
                private  Context f487a;

                
                private  float f488b;

                
                private  CountDownLatch f489c;

                
                private  Crashlytics f490d;

       C0742t(Crashlytics crashlytics, Context context, float f, CountDownLatch countDownLatch) {
           this.f490d = crashlytics;
                    this.f487a = context;
                    this.f488b = f;
                    this.f489c = countDownLatch;
                }

                @Override 
                
       public final void mo38a() {
                    try {
                        try {
                   if (Crashlytics.m15a(this.f490d, this.f487a, this.f488b)) {
                       Crashlytics.m8a(this.f490d).m438e();
                            }
                   this.f489c.countDown();
                        } catch (Exception e) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Problem encountered during Crashlytics initialization.", e);
                   this.f489c.countDown();
                        }
                    } catch (Throwable th) {
                        this.f489c.countDown();
                        throw th;
                    }
                }
            }
