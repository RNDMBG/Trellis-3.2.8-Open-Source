            package com.flurry.sdk;

            import java.io.ByteArrayOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            
            
    public class C0835et implements InterfaceC0836eu<byte[]> {
                @Override 
                
        public  byte[] mo916a(InputStream inputStream) throws IOException {
            return m918b(inputStream);
                }

                @Override 
                

                
        public void a2(OutputStream outputStream, byte[] bArr) throws IOException {
            if (outputStream != null && bArr != null) {
                outputStream.write(bArr, 0, bArr.length);
                    }
                }

                
        public byte[] m918b(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
                    }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            C0843fb.m958a(inputStream, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
                }
            }
