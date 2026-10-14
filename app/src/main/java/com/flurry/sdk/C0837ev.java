            package com.flurry.sdk;

            import java.io.ByteArrayOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ev */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class C0837ev implements InterfaceC0836eu<String> {
                @Override // com.flurry.sdk.InterfaceC0836eu
                /* JADX INFO: renamed from: a */
/* 15 */        public /* synthetic */ String mo916a(InputStream inputStream) throws IOException {
/* 16 */            return m919b(inputStream);
                }

                @Override // com.flurry.sdk.InterfaceC0836eu
                /* JADX INFO: renamed from: a */

                /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
/* 21 */        public void a2(OutputStream outputStream, String str) throws IOException {
/* 22 */            if (outputStream != null && str != null) {
/* 26 */                byte[] bytes = str.getBytes("utf-8");
/* 27 */                outputStream.write(bytes, 0, bytes.length);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 31 */        public String m919b(InputStream inputStream) throws IOException {
/* 32 */            if (inputStream == null) {
/* 33 */                return null;
                    }
/* 36 */            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 37 */            C0843fb.m958a(inputStream, byteArrayOutputStream);
/* 38 */            return byteArrayOutputStream.toString();
                }
            }
