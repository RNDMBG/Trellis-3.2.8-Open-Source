            package com.crashlytics.android.internal;

            import java.io.BufferedOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.nio.ByteBuffer;
            import java.nio.CharBuffer;
            import java.nio.charset.Charset;
            import java.nio.charset.CharsetEncoder;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aF */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 718 */   public final class C0659aF extends BufferedOutputStream {

                /* JADX INFO: renamed from: a */
                private final CharsetEncoder f295a;

/* 719 */       public C0659aF(OutputStream outputStream, String str, int i) {
/* 720 */           super(outputStream, i);
/* 722 */           this.f295a = Charset.forName(C0704ay.m309b(str)).newEncoder();
                }

                /* JADX INFO: renamed from: a */
/* 732 */       public final C0659aF m163a(String str) throws IOException {
/* 733 */           ByteBuffer byteBufferEncode = this.f295a.encode(CharBuffer.wrap(str));
/* 735 */           super.write(byteBufferEncode.array(), 0, byteBufferEncode.limit());
/* 737 */           return this;
                }
            }
