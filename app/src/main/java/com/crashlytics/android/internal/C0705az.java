            package com.crashlytics.android.internal;

            import java.io.Closeable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            
            
  final class C0705az extends AbstractC0654aA<C0704ay> {

                
                private  InputStream f432a;

                
                private  OutputStream f433b;

                
                private  C0704ay f434c;

                
      C0705az(C0704ay c0704ay, Closeable closeable, boolean z, InputStream inputStream, OutputStream outputStream) {
                    super(closeable, z);
          this.f434c = c0704ay;
                    this.f432a = inputStream;
                    this.f433b = outputStream;
                }

                @Override 
                
      public final  Object mo162a() throws C0657aD, IOException {
          byte[] bArr = new byte[C0704ay.m299a(this.f434c)];
                    while (true) {
                        int i = this.f432a.read(bArr);
                        if (i == -1) {
                            return this.f434c;
                        }
                        this.f433b.write(bArr, 0, i);
                    }
                }
            }
