            package com.crashlytics.android.internal;

            import java.io.IOException;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aD */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 576 */   public final class C0657aD extends RuntimeException {
                private static final long serialVersionUID = -1170466989781746231L;

                @Override // java.lang.Throwable
/* 577 */       public final /* bridge */ /* synthetic */ Throwable getCause() {
/* 578 */           return (IOException) super.getCause();
                }

/* 585 */       protected C0657aD(IOException iOException) {
/* 586 */           super(iOException);
                }
            }
