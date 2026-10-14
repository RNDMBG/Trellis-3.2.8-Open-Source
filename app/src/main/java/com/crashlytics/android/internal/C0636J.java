            package com.crashlytics.android.internal;

            import java.io.File;
            import java.io.IOException;
            import java.util.List;
            import java.util.Locale;
            import java.util.concurrent.RejectedExecutionException;
            import java.util.concurrent.ScheduledExecutorService;
            import java.util.concurrent.ScheduledFuture;
            import java.util.concurrent.TimeUnit;
            
            
            
    final class C0636J implements InterfaceC0647U {

                
                private final ScheduledExecutorService f225a;

                
                private final C0637K f226b;

                
                private final C0701av f227c;

                
                private ScheduledFuture<?> f228d;

                
                private int f229e = -1;

                
                private InterfaceC0640N f230f;

        public C0636J(ScheduledExecutorService scheduledExecutorService, C0637K c0637k, C0701av c0701av) {
            this.f225a = scheduledExecutorService;
            this.f226b = c0637k;
            this.f227c = c0701av;
                }

                @Override 
                
        public final void mo110a() {
                    int size;
                    Exception e;
            if (this.f230f == null) {
                C0681ab.m221c("skipping analytics files send because we don't yet know the target endpoint");
               return;
                    }
           C0681ab.m221c("Sending all analytics files");
           List<File> listM125b = this.f226b.m125b();
                    int i = 0;
                    while (listM125b.size() > 0) {
                        try {
                            boolean zMo109a = this.f230f.mo109a(C0723r.m354a(C0630D.m103a().getContext(), false), listM125b);
                            if (zMo109a) {
                                size = listM125b.size() + i;
                                try {
                                    this.f226b.m123a(listM125b);
                                    i = size;
                                } catch (Exception e2) {
                                    e = e2;
                                    C0681ab.m223d("Crashlytics failed to send batch of analytics files to server: " + e.getMessage());
                                    i = size;
                                }
                            }
                            Locale locale = Locale.US;
                            Object[] objArr = new Object[2];
                            objArr[0] = Integer.valueOf(listM125b.size());
                            objArr[1] = zMo109a ? "succeeded" : "did not succeed";
                            C0681ab.m221c(String.format(locale, "attempt to send batch of %d analytics files %s", objArr));
                            if (!zMo109a) {
                                break;
                            } else {
                                listM125b = this.f226b.m125b();
                            }
                        } catch (Exception e3) {
                            size = i;
                            e = e3;
                        }
                    }
                    if (i == 0) {
                        this.f226b.m127d();
                    }
                }

                
       private void m116a(int i, int i2) {
                    try {
               RunnableC0651Y runnableC0651Y = new RunnableC0651Y(this.f226b, this);
               C0681ab.m221c("Scheduling time based file roll over every " + i2 + " seconds");
               this.f228d = this.f225a.scheduleAtFixedRate(runnableC0651Y, i, i2, TimeUnit.SECONDS);
                    } catch (RejectedExecutionException e) {
               C0681ab.m223d("Crashlytics failed to schedule time based analytics file roll over");
                    }
                }

                @Override 
                
       public final void mo114c() {
           if (this.f228d != null) {
               C0681ab.m221c("Cancelling time-based rollover because no events are currently being generated.");
               this.f228d.cancel(false);
               this.f228d = null;
                    }
                }

                @Override 
                
       public final void mo112a(C0664aK c0664aK, String str) {
           this.f230f = new C0634H(str, c0664aK.f305a, this.f227c);
           this.f226b.m121a(c0664aK);
           this.f229e = c0664aK.f306b;
           m116a(0, this.f229e);
                }

                @Override 
                
       public final void mo113b() {
           this.f226b.m126c();
                }

                @Override 
                
       public final void mo111a(C0648V c0648v) throws Throwable {
           C0681ab.m221c(c0648v.toString());
                    try {
               this.f226b.m120a(c0648v);
                    } catch (IOException e) {
                        C0681ab.m223d("Crashlytics failed to write session event.");
                    }
           boolean z = this.f229e != -1;
                    boolean z2 = this.f228d == null;
                    if (!z || !z2) {
               return;
                    }
                    m116a(this.f229e, this.f229e);
                }

                @Override 
                
       public final void mo115d() throws Throwable {
                    try {
               this.f226b.m124a();
                    } catch (IOException e) {
                        C0681ab.m223d("Crashlytics failed to roll analytics file over.");
                    }
                }
            }
