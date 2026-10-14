            package com.flurry.sdk;

            import java.io.ByteArrayOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            
            
    public class C0837ev implements InterfaceC0836eu<String> {
                @Override 
                
        public  String mo916a(InputStream inputStream) throws IOException {
            return m919b(inputStream);
                }

                @Override 
                

                
        public void a2(OutputStream outputStream, String str) throws IOException {
            if (outputStream != null && str != null) {
                byte[] bytes = str.getBytes("utf-8");
                outputStream.write(bytes, 0, bytes.length);
                    }
                }

                
        public String m919b(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
                    }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            C0843fb.m958a(inputStream, byteArrayOutputStream);
            return byteArrayOutputStream.toString();
                }
            }
