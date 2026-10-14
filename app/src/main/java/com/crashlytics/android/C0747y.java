            package com.crashlytics.android;

            import com.crashlytics.android.internal.InterfaceC0700au;
            import java.io.IOException;
            import java.io.InputStream;
            
            /* JADX INFO: renamed from: com.crashlytics.android.y */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 412 */   final class C0747y implements InterfaceC0700au {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ byte[] f521a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ int[] f522b;

/* 413 */       C0747y(C0744v c0744v, byte[] bArr, int[] iArr) {
/* 414 */           this.f521a = bArr;
                    this.f522b = iArr;
                }

                @Override // com.crashlytics.android.internal.InterfaceC0700au
                /* JADX INFO: renamed from: a */
/* 417 */       public final void mo290a(InputStream inputStream, int i) throws IOException {
                    try {
/* 418 */               inputStream.read(this.f521a, this.f522b[0], i);
/* 419 */               int[] iArr = this.f522b;
                        iArr[0] = iArr[0] + i;
/* 421 */               inputStream.close();
                    } catch (Throwable th) {
                        inputStream.close();
                        throw th;
                    }
                }
            }
