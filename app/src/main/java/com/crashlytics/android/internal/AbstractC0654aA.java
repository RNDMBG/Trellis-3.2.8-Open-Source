            package com.crashlytics.android.internal;

            import java.io.Closeable;
            import java.io.Flushable;
            import java.io.IOException;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aA */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 659 */   public abstract class AbstractC0654aA<V> extends AbstractCallableC0658aE<V> {

                /* JADX INFO: renamed from: a */
                private final Closeable f292a;

                /* JADX INFO: renamed from: b */
                private final boolean f293b;

/* 660 */       protected AbstractC0654aA(Closeable closeable, boolean z) {
/* 662 */           this.f292a = closeable;
/* 663 */           this.f293b = z;
                }

                @Override // com.crashlytics.android.internal.AbstractCallableC0658aE
                /* JADX INFO: renamed from: b */
/* 667 */       protected final void mo160b() throws IOException {
/* 668 */           if (this.f292a instanceof Flushable) {
/* 669 */               ((Flushable) this.f292a).flush();
                    }
/* 670 */           if (this.f293b) {
                        try {
/* 672 */                   this.f292a.close();
                        } catch (IOException e) {
                        }
                    } else {
/* 677 */               this.f292a.close();
                    }
                }
            }
