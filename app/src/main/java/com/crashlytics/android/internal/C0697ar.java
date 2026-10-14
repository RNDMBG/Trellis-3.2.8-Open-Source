            package com.crashlytics.android.internal;

            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.ar */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 543 */   final class C0697ar implements InterfaceC0700au {

                /* JADX INFO: renamed from: a */
                private boolean f405a = true;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ StringBuilder f406b;

/* 544 */       C0697ar(C0696aq c0696aq, StringBuilder sb) {
/* 545 */           this.f406b = sb;
                }

                @Override // com.crashlytics.android.internal.InterfaceC0700au
                /* JADX INFO: renamed from: a */
/* 549 */       public final void mo290a(InputStream inputStream, int i) throws IOException {
/* 550 */           if (this.f405a) {
/* 551 */               this.f405a = false;
                    } else {
/* 553 */               this.f406b.append(", ");
                    }
/* 555 */           this.f406b.append(i);
                }
            }
