            package com.crashlytics.android;

            import android.content.Context;
            import com.crashlytics.android.internal.AbstractRunnableC0680aa;
            import com.crashlytics.android.internal.C0727v;
            import java.util.concurrent.CountDownLatch;
            
            /* JADX INFO: renamed from: com.crashlytics.android.t */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 833 */   final class C0742t extends AbstractRunnableC0680aa {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ Context f487a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ float f488b;

                /* JADX INFO: renamed from: c */
                private /* synthetic */ CountDownLatch f489c;

                /* JADX INFO: renamed from: d */
                private /* synthetic */ Crashlytics f490d;

/* 834 */       C0742t(Crashlytics crashlytics, Context context, float f, CountDownLatch countDownLatch) {
/* 835 */           this.f490d = crashlytics;
                    this.f487a = context;
                    this.f488b = f;
                    this.f489c = countDownLatch;
                }

                @Override // com.crashlytics.android.internal.AbstractRunnableC0680aa
                /* JADX INFO: renamed from: a */
/* 838 */       public final void mo38a() {
                    try {
                        try {
/* 839 */                   if (Crashlytics.m15a(this.f490d, this.f487a, this.f488b)) {
/* 842 */                       Crashlytics.m8a(this.f490d).m438e();
                            }
/* 847 */                   this.f489c.countDown();
                        } catch (Exception e) {
/* 845 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Problem encountered during Crashlytics initialization.", e);
/* 847 */                   this.f489c.countDown();
                        }
                    } catch (Throwable th) {
                        this.f489c.countDown();
                        throw th;
                    }
                }
            }
