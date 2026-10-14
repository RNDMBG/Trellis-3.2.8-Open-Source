            package com.flurry.sdk;

            import java.io.ByteArrayOutputStream;
            import java.io.Closeable;
            import java.io.DataOutputStream;
            import java.io.IOException;
            
            /* JADX INFO: renamed from: com.flurry.sdk.cx */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public final class C0787cx {

                /* JADX INFO: renamed from: a */
                private int f542a;

                /* JADX INFO: renamed from: b */
                private long f543b;

                /* JADX INFO: renamed from: c */
                private String f544c;

                /* JADX INFO: renamed from: d */
                private String f545d;

                /* JADX INFO: renamed from: e */
                private String f546e;

                /* JADX INFO: renamed from: f */
                private Throwable f547f;

/* 19 */        public C0787cx(int i, long j, String str, String str2, String str3, Throwable th) {
/* 21 */            this.f542a = i;
/* 22 */            this.f543b = j;
/* 23 */            this.f544c = str;
/* 24 */            this.f545d = str2;
/* 25 */            this.f546e = str3;
/* 26 */            this.f547f = th;
                }

                /* JADX INFO: renamed from: a */
/* 30 */        public int m556a() {
/* 31 */            return m557b().length;
                }

                /* JADX INFO: renamed from: b */
/* 37 */        public byte[] m557b() throws Throwable {
                    Throwable th;
                    DataOutputStream dataOutputStream;
                    byte[] byteArray;
                    try {
                        try {
/* 41 */                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 42 */                    dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                            try {
/* 44 */                        dataOutputStream.writeShort(this.f542a);
/* 45 */                        dataOutputStream.writeLong(this.f543b);
/* 46 */                        dataOutputStream.writeUTF(this.f544c);
/* 47 */                        dataOutputStream.writeUTF(this.f545d);
/* 48 */                        dataOutputStream.writeUTF(this.f546e);
/* 52 */                        if (this.f547f != null) {
/* 55 */                            if (this.f544c == "uncaught") {
/* 56 */                                dataOutputStream.writeByte(3);
                                    } else {
/* 58 */                                dataOutputStream.writeByte(2);
                                    }
/* 61 */                            dataOutputStream.writeByte(2);
/* 62 */                            StringBuilder sb = new StringBuilder("");
/* 64 */                            String property = System.getProperty("line.separator");
                                    for (StackTraceElement stackTraceElement : this.f547f.getStackTrace()) {
/* 68 */                                sb.append(stackTraceElement);
/* 69 */                                sb.append(property);
                                    }
/* 72 */                            if (this.f547f.getCause() != null) {
/* 73 */                                sb.append(property);
/* 74 */                                sb.append("Caused by: ");
                                        for (StackTraceElement stackTraceElement2 : this.f547f.getCause().getStackTrace()) {
/* 78 */                                    sb.append(stackTraceElement2);
/* 79 */                                    sb.append(property);
                                        }
                                    }
/* 83 */                            byte[] bytes = sb.toString().getBytes();
/* 85 */                            dataOutputStream.writeInt(bytes.length);
/* 86 */                            dataOutputStream.write(bytes);
                                } else {
/* 91 */                            dataOutputStream.writeByte(1);
/* 93 */                            dataOutputStream.writeByte(0);
                                }
/* 95 */                        dataOutputStream.flush();
/* 96 */                        byteArray = byteArrayOutputStream.toByteArray();
/* 105 */                       C0843fb.m962a(dataOutputStream);
                            } catch (IOException e) {
/* 101 */                       byteArray = new byte[0];
/* 105 */                       C0843fb.m962a(dataOutputStream);
                            }
                        } catch (Throwable th2) {
/* 105 */                   th = th2;
/* 38 */                    C0843fb.m962a((Closeable) null);
                            throw th;
                        }
                    } catch (IOException e2) {
/* 38 */                dataOutputStream = null;
                    } catch (Throwable th3) {
                        th = th3;
/* 38 */                C0843fb.m962a((Closeable) null);
                        throw th;
                    }
/* 108 */           return byteArray;
                }

                /* JADX INFO: renamed from: c */
/* 123 */       public String m558c() {
/* 124 */           return this.f544c;
                }
            }
