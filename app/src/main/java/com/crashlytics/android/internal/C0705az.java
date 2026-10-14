            package com.crashlytics.android.internal;

            import java.io.Closeable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.az */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 2399 */  final class C0705az extends AbstractC0654aA<C0704ay> {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ InputStream f432a;

                /* JADX INFO: renamed from: b */
                private /* synthetic */ OutputStream f433b;

                /* JADX INFO: renamed from: c */
                private /* synthetic */ C0704ay f434c;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 2400 */      C0705az(C0704ay c0704ay, Closeable closeable, boolean z, InputStream inputStream, OutputStream outputStream) {
                    super(closeable, z);
/* 2401 */          this.f434c = c0704ay;
                    this.f432a = inputStream;
                    this.f433b = outputStream;
                }

                @Override // com.crashlytics.android.internal.AbstractCallableC0658aE
                /* JADX INFO: renamed from: a */
/* 2400 */      public final /* synthetic */ Object mo162a() throws C0657aD, IOException {
/* 2401 */          byte[] bArr = new byte[C0704ay.m299a(this.f434c)];
                    while (true) {
                        int i = this.f432a.read(bArr);
                        if (i == -1) {
                            return this.f434c;
                        }
                        this.f433b.write(bArr, 0, i);
                    }
                }
            }
