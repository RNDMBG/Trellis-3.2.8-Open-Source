            package com.flurry.sdk;

            import java.io.ByteArrayOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: renamed from: com.flurry.sdk.et */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class C0835et implements InterfaceC0836eu<byte[]> {
                @Override // com.flurry.sdk.InterfaceC0836eu
                /* JADX INFO: renamed from: a */
/* 15 */        public /* synthetic */ byte[] mo916a(InputStream inputStream) throws IOException {
/* 16 */            return m918b(inputStream);
                }

                @Override // com.flurry.sdk.InterfaceC0836eu
                /* JADX INFO: renamed from: a */

                /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
/* 21 */        public void a2(OutputStream outputStream, byte[] bArr) throws IOException {
/* 22 */            if (outputStream != null && bArr != null) {
/* 26 */                outputStream.write(bArr, 0, bArr.length);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 30 */        public byte[] m918b(InputStream inputStream) throws IOException {
/* 31 */            if (inputStream == null) {
/* 32 */                return null;
                    }
/* 35 */            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 36 */            C0843fb.m958a(inputStream, byteArrayOutputStream);
/* 37 */            return byteArrayOutputStream.toByteArray();
                }
            }
