            package com.crashlytics.android;

            import com.crashlytics.android.internal.AbstractRunnableC0680aa;
            import com.crashlytics.android.internal.C0727v;
            import java.util.Iterator;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.crashlytics.android.ac */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 121 */   final class C0616ac extends AbstractRunnableC0680aa {

                /* JADX INFO: renamed from: a */
                private final float f184a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ C0614aa f185b;

/* 122 */       C0616ac(C0614aa c0614aa, float f) {
/* 123 */           this.f185b = c0614aa;
/* 124 */           this.f184a = f;
                }

                /* JADX WARN: Code duplicated, block: B:23:0x00ae  */
                /* JADX WARN: Code duplicated, block: B:26:0x00b5 A[Catch: Exception -> 0x008e, TryCatch #1 {Exception -> 0x008e, blocks: (B:2:0x0000, B:5:0x002b, B:6:0x0034, B:8:0x0048, B:10:0x004e, B:12:0x0054, B:13:0x007e, B:15:0x0084, B:24:0x00af, B:26:0x00b5, B:28:0x00c3, B:29:0x00ed, B:31:0x00f3, B:32:0x00ff, B:34:0x010b, B:36:0x0144, B:39:0x014b, B:22:0x00a6), top: B:43:0x0000, inners: #0, #2 }] */
                /* JADX WARN: Code duplicated, block: B:31:0x00f3 A[Catch: Exception -> 0x008e, LOOP:2: B:29:0x00ed->B:31:0x00f3, LOOP_END, TryCatch #1 {Exception -> 0x008e, blocks: (B:2:0x0000, B:5:0x002b, B:6:0x0034, B:8:0x0048, B:10:0x004e, B:12:0x0054, B:13:0x007e, B:15:0x0084, B:24:0x00af, B:26:0x00b5, B:28:0x00c3, B:29:0x00ed, B:31:0x00f3, B:32:0x00ff, B:34:0x010b, B:36:0x0144, B:39:0x014b, B:22:0x00a6), top: B:43:0x0000, inners: #0, #2 }] */
                /* JADX WARN: Code duplicated, block: B:47:0x010b A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:51:0x00af A[SYNTHETIC] */
                /* JADX WARN: Code duplicated, block: B:8:0x0048 A[Catch: Exception -> 0x008e, TryCatch #1 {Exception -> 0x008e, blocks: (B:2:0x0000, B:5:0x002b, B:6:0x0034, B:8:0x0048, B:10:0x004e, B:12:0x0054, B:13:0x007e, B:15:0x0084, B:24:0x00af, B:26:0x00b5, B:28:0x00c3, B:29:0x00ed, B:31:0x00f3, B:32:0x00ff, B:34:0x010b, B:36:0x0144, B:39:0x014b, B:22:0x00a6), top: B:43:0x0000, inners: #0, #2 }] */
                @Override // com.crashlytics.android.internal.AbstractRunnableC0680aa
                /* JADX INFO: renamed from: a */
/* 129 */       public final void mo38a() {
                    C0744v c0744vM30l;
                    List<C0612Z> listM56a;
                    int i;
                    Iterator<C0612Z> it;
                    try {
/* 130 */               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Starting report processing in " + this.f184a + " second(s)...");
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
/* 132 */               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "An unexpected error occurred while attempting to upload crash reports.", e3);
                    }
/* 134 */           C0614aa.m54a(this.f185b, null);
                }
            }
