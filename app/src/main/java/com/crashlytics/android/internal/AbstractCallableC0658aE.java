            package com.crashlytics.android.internal;

            import java.io.IOException;
            import java.util.concurrent.Callable;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aE */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 603 */   public abstract class AbstractCallableC0658aE<V> implements Callable<V> {
                /* JADX INFO: renamed from: a */
                protected abstract V mo162a() throws C0657aD, IOException;

                /* JADX INFO: renamed from: b */
                protected abstract void mo160b() throws IOException;

/* 604 */       protected AbstractCallableC0658aE() {
                }

                @Override // java.util.concurrent.Callable
/* 623 */       public V call() throws Throwable {
                    boolean z = true;
                    try {
                        try {
                            try {
/* 626 */                       V vMo162a = mo162a();
                                try {
/* 635 */                           mo160b();
/* 639 */                           return vMo162a;
                                } catch (IOException e) {
/* 637 */                           throw new C0657aD(e);
                                }
                            } catch (C0657aD e2) {
/* 629 */                       throw e2;
                            }
                        } catch (IOException e3) {
/* 632 */                   throw new C0657aD(e3);
                        } catch (Throwable th) {
/* 634 */                   th = th;
/* 624 */                   z = false;
                            try {
/* 635 */                       mo160b();
                            } catch (IOException e4) {
/* 637 */                       if (!z) {
/* 638 */                           throw new C0657aD(e4);
                                }
                            }
/* 639 */                   throw th;
                        }
                    } catch (Throwable th2) {
/* 634 */               th = th2;
/* 635 */               mo160b();
/* 639 */               throw th;
                    }
                }
            }
