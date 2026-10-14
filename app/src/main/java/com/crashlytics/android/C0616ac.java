            package com.crashlytics.android;

            import com.crashlytics.android.internal.AbstractRunnableC0680aa;
            import com.crashlytics.android.internal.C0727v;
            import java.util.Iterator;
            import java.util.List;
            
            
            
   final class C0616ac extends AbstractRunnableC0680aa {

                
                private final float f184a;

                
                private  C0614aa f185b;

       C0616ac(C0614aa c0614aa, float f) {
           this.f185b = c0614aa;
           this.f184a = f;
                }

                
                
                
                
                
                
                @Override 
                
       public final void mo38a() {
                    C0744v c0744vM30l;
                    List<C0612Z> listM56a;
                    int i;
                    Iterator<C0612Z> it;
                    try {
               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Starting report processing in " + this.f184a + " second(s)...");
                        if (this.f184a > 0.0f) {
                            try {
                                Thread.sleep((long) (this.f184a * 1000.0f));
                                Crashlytics crashlytics = Crashlytics.getInstance();
                                c0744vM30l = crashlytics.m30l();
                                listM56a = this.f185b.m56a();
                                if (!c0744vM30l.m434a()) {
                                    if (!listM56a.isEmpty() || crashlytics.m34p()) {
                                        i = 0;
                                        while (!listM56a.isEmpty() && !Crashlytics.getInstance().m30l().m434a()) {
                                            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Attempting to send " + listM56a.size() + " report(s)");
                                            it = listM56a.iterator();
                                            while (it.hasNext()) {
                                                this.f185b.m58a(it.next());
                                            }
                                            listM56a = this.f185b.m56a();
                                            if (!listM56a.isEmpty()) {
                                                int i2 = i + 1;
                                                long j = C0614aa.m55b()[Math.min(i, C0614aa.m55b().length - 1)];
                                                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Report submisson: scheduling delayed retry in " + j + " seconds");
                                                try {
                                                    Thread.sleep(j * 1000);
                                                    i = i2;
                                                } catch (InterruptedException e) {
                                                    Thread.currentThread().interrupt();
                                                }
                                            }
                                        }
                                    } else {
                                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "User declined to send. Removing " + listM56a.size() + " Report(s).");
                                        Iterator<C0612Z> it2 = listM56a.iterator();
                                        while (it2.hasNext()) {
                                            it2.next().m47a();
                                        }
                                    }
                                }
                            } catch (InterruptedException e2) {
                                Thread.currentThread().interrupt();
                            }
                        } else {
                            Crashlytics crashlytics2 = Crashlytics.getInstance();
                            c0744vM30l = crashlytics2.m30l();
                            listM56a = this.f185b.m56a();
                            if (!c0744vM30l.m434a()) {
                                if (listM56a.isEmpty()) {
                                    i = 0;
                                    while (!listM56a.isEmpty()) {
                                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Attempting to send " + listM56a.size() + " report(s)");
                                        it = listM56a.iterator();
                                        while (it.hasNext()) {
                                            this.f185b.m58a(it.next());
                                        }
                                        listM56a = this.f185b.m56a();
                                        if (!listM56a.isEmpty()) {
                                            int i3 = i + 1;
                                            long j2 = C0614aa.m55b()[Math.min(i, C0614aa.m55b().length - 1)];
                                            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Report submisson: scheduling delayed retry in " + j2 + " seconds");
                                            Thread.sleep(j2 * 1000);
                                            i = i3;
                                        }
                                    }
                                } else {
                                    i = 0;
                                    while (!listM56a.isEmpty()) {
                                        C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Attempting to send " + listM56a.size() + " report(s)");
                                        it = listM56a.iterator();
                                        while (it.hasNext()) {
                                            this.f185b.m58a(it.next());
                                        }
                                        listM56a = this.f185b.m56a();
                                        if (!listM56a.isEmpty()) {
                                            int i4 = i + 1;
                                            long j3 = C0614aa.m55b()[Math.min(i, C0614aa.m55b().length - 1)];
                                            C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Report submisson: scheduling delayed retry in " + j3 + " seconds");
                                            Thread.sleep(j3 * 1000);
                                            i = i4;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e3) {
               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "An unexpected error occurred while attempting to upload crash reports.", e3);
                    }
           C0614aa.m54a(this.f185b, null);
                }
            }
