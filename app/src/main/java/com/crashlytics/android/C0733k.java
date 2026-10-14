            package com.crashlytics.android;

            import com.crashlytics.android.internal.InterfaceC0660aG;
            import java.io.InputStream;
            
            /* JADX INFO: renamed from: com.crashlytics.android.k */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 431 */   final class C0733k implements InterfaceC0660aG {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ PinningInfoProvider f476a;

/* 432 */       C0733k(PinningInfoProvider pinningInfoProvider) {
/* 433 */           this.f476a = pinningInfoProvider;
                }

                @Override // com.crashlytics.android.internal.InterfaceC0660aG
                /* JADX INFO: renamed from: a */
/* 435 */       public final InputStream mo164a() {
/* 436 */           return this.f476a.getKeyStoreStream();
                }

                @Override // com.crashlytics.android.internal.InterfaceC0660aG
                /* JADX INFO: renamed from: b */
/* 440 */       public final String mo165b() {
/* 441 */           return this.f476a.getKeyStorePassword();
                }

                @Override // com.crashlytics.android.internal.InterfaceC0660aG
                /* JADX INFO: renamed from: c */
/* 445 */       public final String[] mo166c() {
/* 446 */           return this.f476a.getPins();
                }
            }
