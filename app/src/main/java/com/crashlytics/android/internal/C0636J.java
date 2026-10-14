            package com.crashlytics.android.internal;

            import java.io.File;
            import java.io.IOException;
            import java.util.List;
            import java.util.Locale;
            import java.util.concurrent.RejectedExecutionException;
            import java.util.concurrent.ScheduledExecutorService;
            import java.util.concurrent.ScheduledFuture;
            import java.util.concurrent.TimeUnit;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.J */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    final class C0636J implements InterfaceC0647U {

                /* JADX INFO: renamed from: a */
                private final ScheduledExecutorService f225a;

                /* JADX INFO: renamed from: b */
                private final C0637K f226b;

                /* JADX INFO: renamed from: c */
                private final C0701av f227c;

                /* JADX INFO: renamed from: d */
                private ScheduledFuture<?> f228d;

                /* JADX INFO: renamed from: e */
                private int f229e = -1;

                /* JADX INFO: renamed from: f */
                private InterfaceC0640N f230f;

/* 35 */        public C0636J(ScheduledExecutorService scheduledExecutorService, C0637K c0637k, C0701av c0701av) {
/* 37 */            this.f225a = scheduledExecutorService;
/* 38 */            this.f226b = c0637k;
/* 39 */            this.f227c = c0701av;
                }

                @Override // com.crashlytics.android.internal.InterfaceC0647U
                /* JADX INFO: renamed from: a */
/* 95 */        public final void mo110a() {
                    int size;
                    Exception e;
/* 96 */            if (this.f230f == null) {
/* 97 */                C0681ab.m221c("skipping analytics files send because we don't yet know the target endpoint");
/* 102 */               return;
                    }
/* 100 */           C0681ab.m221c("Sending all analytics files");
/* 101 */           List<File> listM125b = this.f226b.m125b();
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

                /* JADX INFO: renamed from: a */
/* 105 */       private void m116a(int i, int i2) {
                    try {
/* 106 */               RunnableC0651Y runnableC0651Y = new RunnableC0651Y(this.f226b, this);
/* 107 */               C0681ab.m221c("Scheduling time based file roll over every " + i2 + " seconds");
/* 108 */               this.f228d = this.f225a.scheduleAtFixedRate(runnableC0651Y, i, i2, TimeUnit.SECONDS);
                    } catch (RejectedExecutionException e) {
/* 111 */               C0681ab.m223d("Crashlytics failed to schedule time based analytics file roll over");
                    }
                }

                @Override // com.crashlytics.android.internal.InterfaceC0647U
                /* JADX INFO: renamed from: c */
/* 116 */       public final void mo114c() {
/* 117 */           if (this.f228d != null) {
/* 118 */               C0681ab.m221c("Cancelling time-based rollover because no events are currently being generated.");
/* 119 */               this.f228d.cancel(false);
/* 120 */               this.f228d = null;
                    }
                }

                @Override // com.crashlytics.android.internal.InterfaceC0647U
                /* JADX INFO: renamed from: a */
/* 125 */       public final void mo112a(C0664aK c0664aK, String str) {
/* 126 */           this.f230f = new C0634H(str, c0664aK.f305a, this.f227c);
/* 130 */           this.f226b.m121a(c0664aK);
/* 131 */           this.f229e = c0664aK.f306b;
/* 132 */           m116a(0, this.f229e);
                }

                @Override // com.crashlytics.android.internal.InterfaceC0647U
                /* JADX INFO: renamed from: b */
/* 136 */       public final void mo113b() {
/* 137 */           this.f226b.m126c();
                }

                @Override // com.crashlytics.android.internal.InterfaceC0647U
                /* JADX INFO: renamed from: a */
/* 141 */       public final void mo111a(C0648V c0648v) throws Throwable {
/* 142 */           C0681ab.m221c(c0648v.toString());
                    try {
/* 144 */               this.f226b.m120a(c0648v);
                    } catch (IOException e) {
                        C0681ab.m223d("Crashlytics failed to write session event.");
                    }
/* 148 */           boolean z = this.f229e != -1;
                    boolean z2 = this.f228d == null;
                    if (!z || !z2) {
/* 149 */               return;
                    }
                    m116a(this.f229e, this.f229e);
                }

                @Override // com.crashlytics.android.internal.InterfaceC0647U
                /* JADX INFO: renamed from: d */
/* 153 */       public final void mo115d() throws Throwable {
                    try {
/* 154 */               this.f226b.m124a();
                    } catch (IOException e) {
                        C0681ab.m223d("Crashlytics failed to roll analytics file over.");
                    }
                }
            }
