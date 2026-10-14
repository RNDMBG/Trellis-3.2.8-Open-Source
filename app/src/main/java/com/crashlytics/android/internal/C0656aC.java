            package com.crashlytics.android.internal;

            import java.io.IOException;
            import java.net.HttpURLConnection;
            import java.net.URL;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aC */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 347 */   final class C0656aC implements InterfaceC0655aB {
/* 348 */       C0656aC() {
                }

                @Override // com.crashlytics.android.internal.InterfaceC0655aB
                /* JADX INFO: renamed from: a */
/* 350 */       public final HttpURLConnection mo161a(URL url) throws IOException {
/* 351 */           return (HttpURLConnection) url.openConnection();
                }
            }
