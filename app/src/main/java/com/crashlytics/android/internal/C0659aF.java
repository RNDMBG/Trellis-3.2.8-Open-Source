            package com.crashlytics.android.internal;

            import java.io.BufferedOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.nio.ByteBuffer;
            import java.nio.CharBuffer;
            import java.nio.charset.Charset;
            import java.nio.charset.CharsetEncoder;
            
            
            
   public final class C0659aF extends BufferedOutputStream {

                
                private final CharsetEncoder f295a;

       public C0659aF(OutputStream outputStream, String str, int i) {
           super(outputStream, i);
           this.f295a = Charset.forName(C0704ay.m309b(str)).newEncoder();
                }

                
       public final C0659aF m163a(String str) throws IOException {
           ByteBuffer byteBufferEncode = this.f295a.encode(CharBuffer.wrap(str));
           super.write(byteBufferEncode.array(), 0, byteBufferEncode.limit());
           return this;
                }
            }
