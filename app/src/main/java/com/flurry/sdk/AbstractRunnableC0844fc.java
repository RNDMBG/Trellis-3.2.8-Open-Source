            package com.flurry.sdk;

            import java.io.PrintStream;
            import java.io.PrintWriter;
            
            /* JADX INFO: renamed from: com.flurry.sdk.fc */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 47 */    public abstract class AbstractRunnableC0844fc implements Runnable {

                /* JADX INFO: renamed from: a */
                private static final String f849a = AbstractRunnableC0844fc.class.getSimpleName();

                /* JADX INFO: renamed from: g */
                PrintStream f850g;

                /* JADX INFO: renamed from: h */
                PrintWriter f851h;

                /* JADX INFO: renamed from: a */
                public abstract void mo576a();

                @Override // java.lang.Runnable
/* 48 */        public final void run() {
                    try {
/* 49 */                mo576a();
                    } catch (Throwable th) {
/* 53 */                if (this.f850g != null) {
/* 55 */                    th.printStackTrace(this.f850g);
                        } else if (this.f851h != null) {
/* 59 */                    th.printStackTrace(this.f851h);
                        } else {
/* 63 */                    th.printStackTrace();
                        }
/* 65 */                C0827el.m886a(6, f849a, "", th);
                    }
                }
            }
