            package com.flurry.sdk;

            import java.io.ByteArrayOutputStream;
            import java.io.Closeable;
            import java.io.DataOutputStream;
            import java.io.IOException;
            
            
            
    public final class C0787cx {

                
                private int f542a;

                
                private long f543b;

                
                private String f544c;

                
                private String f545d;

                
                private String f546e;

                
                private Throwable f547f;

        public C0787cx(int i, long j, String str, String str2, String str3, Throwable th) {
            this.f542a = i;
            this.f543b = j;
            this.f544c = str;
            this.f545d = str2;
            this.f546e = str3;
            this.f547f = th;
                }

                
        public int m556a() {
            return m557b().length;
                }

                
        public byte[] m557b() throws Throwable {
                    Throwable th;
                    DataOutputStream dataOutputStream;
                    byte[] byteArray;
                    try {
                        try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                            try {
                        dataOutputStream.writeShort(this.f542a);
                        dataOutputStream.writeLong(this.f543b);
                        dataOutputStream.writeUTF(this.f544c);
                        dataOutputStream.writeUTF(this.f545d);
                        dataOutputStream.writeUTF(this.f546e);
                        if (this.f547f != null) {
                            if (this.f544c == "uncaught") {
                                dataOutputStream.writeByte(3);
                                    } else {
                                dataOutputStream.writeByte(2);
                                    }
                            dataOutputStream.writeByte(2);
                            StringBuilder sb = new StringBuilder("");
                            String property = System.getProperty("line.separator");
                                    for (StackTraceElement stackTraceElement : this.f547f.getStackTrace()) {
                                sb.append(stackTraceElement);
                                sb.append(property);
                                    }
                            if (this.f547f.getCause() != null) {
                                sb.append(property);
                                sb.append("Caused by: ");
                                        for (StackTraceElement stackTraceElement2 : this.f547f.getCause().getStackTrace()) {
                                    sb.append(stackTraceElement2);
                                    sb.append(property);
                                        }
                                    }
                            byte[] bytes = sb.toString().getBytes();
                            dataOutputStream.writeInt(bytes.length);
                            dataOutputStream.write(bytes);
                                } else {
                            dataOutputStream.writeByte(1);
                            dataOutputStream.writeByte(0);
                                }
                        dataOutputStream.flush();
                        byteArray = byteArrayOutputStream.toByteArray();
                       C0843fb.m962a(dataOutputStream);
                            } catch (IOException e) {
                       byteArray = new byte[0];
                       C0843fb.m962a(dataOutputStream);
                            }
                        } catch (Throwable th2) {
                   th = th2;
                    C0843fb.m962a((Closeable) null);
                            throw th;
                        }
                    } catch (IOException e2) {
                dataOutputStream = null;
                    } catch (Throwable th3) {
                        th = th3;
                C0843fb.m962a((Closeable) null);
                        throw th;
                    }
           return byteArray;
                }

                
       public String m558c() {
           return this.f544c;
                }
            }
