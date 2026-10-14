            package com.crashlytics.android.internal;

            import java.io.IOException;
            import java.io.InputStream;
            
            
            
   final class C0699at extends InputStream {

                
                private int f410a;

                
                private int f411b;

                
                private  C0696aq f412c;

        C0699at(C0696aq c0696aq, C0698as c0698as, byte b) {
           this(c0696aq, c0698as);
                }

       private C0699at(C0696aq c0696aq, C0698as c0698as) {
           this.f412c = c0696aq;
           this.f410a = C0696aq.m266a(c0696aq, c0698as.f408b + 4);
           this.f411b = c0698as.f409c;
                }

                @Override 
       public final int read(byte[] buffer, int offset, int length) throws IOException {
           C0696aq.m271a(buffer, "buffer");
           if ((offset | length) < 0 || length > buffer.length - offset) {
               throw new ArrayIndexOutOfBoundsException();
                    }
           if (this.f411b <= 0) {
                        return -1;
                    }
           if (length > this.f411b) {
               length = this.f411b;
                    }
           C0696aq.m274a(this.f412c, this.f410a, buffer, offset, length);
           this.f410a = C0696aq.m266a(this.f412c, this.f410a + length);
           this.f411b -= length;
                    return length;
                }

                @Override 
       public final int read() throws IOException {
           if (this.f411b == 0) {
               return -1;
                    }
           C0696aq.m269a(this.f412c).seek(this.f410a);
           int i = C0696aq.m269a(this.f412c).read();
           this.f410a = C0696aq.m266a(this.f412c, this.f410a + 1);
           this.f411b--;
           return i;
                }
            }
