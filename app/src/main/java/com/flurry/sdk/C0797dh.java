            package com.flurry.sdk;

            import java.io.DataInputStream;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dh */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class C0797dh {

                /* JADX INFO: renamed from: a */
                private static final String f671a = C0797dh.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private boolean f672b;

                /* JADX INFO: renamed from: c */
                private List<C0794de> f673c;

                /* JADX INFO: renamed from: d */
                private long f674d = -1;

                /* JADX INFO: renamed from: a */
/* 27 */        public void m704a(boolean z) {
/* 28 */            this.f672b = z;
                }

                /* JADX INFO: renamed from: a */
/* 31 */        public boolean m705a() {
/* 32 */            return this.f672b;
                }

                /* JADX INFO: renamed from: a */
/* 35 */        public void m703a(List<C0794de> list) {
/* 36 */            this.f673c = list;
                }

                /* JADX INFO: renamed from: b */
/* 39 */        public List<C0794de> m707b() {
/* 40 */            return this.f673c;
                }

                /* JADX INFO: renamed from: a */
/* 43 */        public void m701a(long j) {
/* 44 */            this.f674d = j;
                }

                /* JADX INFO: renamed from: c */
/* 47 */        public long m708c() {
/* 48 */            return this.f674d;
                }

                /* JADX INFO: renamed from: a */
/* 53 */        public void m702a(DataOutputStream dataOutputStream, String str, String str2) throws IOException {
                    try {
                        try {
/* 54 */                    dataOutputStream.writeShort(46586);
/* 55 */                    dataOutputStream.writeShort(2);
/* 57 */                    dataOutputStream.writeUTF(str);
/* 58 */                    dataOutputStream.writeUTF(str2);
/* 60 */                    dataOutputStream.writeBoolean(this.f672b);
/* 61 */                    dataOutputStream.writeLong(this.f674d);
                            for (int size = this.f673c.size() - 1; size >= 0; size--) {
/* 66 */                        byte[] bArrM610a = this.f673c.get(size).m610a();
/* 67 */                        int length = bArrM610a.length;
/* 68 */                        if (length + 2 + dataOutputStream.size() > 50000) {
/* 72 */                            C0827el.m885a(6, f671a, "discarded sessions: " + size);
                                    break;
                                } else {
/* 75 */                            dataOutputStream.writeShort(length);
/* 76 */                            dataOutputStream.write(bArrM610a);
                                }
                            }
/* 78 */                    dataOutputStream.writeShort(0);
/* 83 */                    C0843fb.m962a(dataOutputStream);
                        } catch (Throwable th) {
/* 80 */                    C0827el.m886a(6, f671a, "", th);
/* 81 */                    throw new IOException(th.getMessage());
                        }
                    } catch (Throwable th2) {
                        C0843fb.m962a(dataOutputStream);
                        throw th2;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 88 */        public boolean m706a(DataInputStream dataInputStream, String str) throws IOException {
/* 89 */            boolean zM700a = false;
                    try {
                        try {
/* 91 */                    int unsignedShort = dataInputStream.readUnsignedShort();
/* 92 */                    C0827el.m885a(4, f671a, "Magic: " + unsignedShort);
/* 94 */                    if (unsignedShort == 46586) {
/* 95 */                        zM700a = m700a(str, dataInputStream);
                            } else {
/* 97 */                        C0827el.m885a(3, f671a, "Unexpected file type");
                            }
/* 104 */                   C0843fb.m962a(dataInputStream);
/* 107 */                   return zM700a;
                        } catch (Throwable th) {
/* 101 */                   C0827el.m886a(6, f671a, "Error when loading persistent file", th);
/* 102 */                   throw new IOException(th.getMessage());
                        }
                    } catch (Throwable th2) {
                        C0843fb.m962a(dataInputStream);
                        throw th2;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 112 */       private boolean m700a(String str, DataInputStream dataInputStream) throws IOException {
/* 113 */           int unsignedShort = dataInputStream.readUnsignedShort();
/* 115 */           C0827el.m885a(3, f671a, "File version: " + unsignedShort);
/* 117 */           if (unsignedShort > 2) {
/* 118 */               C0827el.m885a(6, f671a, "Unknown agent file version: " + unsignedShort);
/* 119 */               throw new IOException("Unknown agent file version: " + unsignedShort);
                    }
/* 122 */           if (unsignedShort >= 2) {
/* 123 */               String utf = dataInputStream.readUTF();
/* 124 */               C0827el.m885a(3, f671a, "Loading API key: " + m699a(str));
/* 126 */               if (utf.equals(str)) {
/* 127 */                   ArrayList arrayList = new ArrayList();
/* 129 */                   dataInputStream.readUTF();
/* 130 */                   boolean z = dataInputStream.readBoolean();
/* 131 */                   long j = dataInputStream.readLong();
/* 133 */                   C0827el.m885a(3, f671a, "Loading session reports");
                            int i = 0;
                            while (true) {
/* 138 */                       int unsignedShort2 = dataInputStream.readUnsignedShort();
/* 139 */                       if (unsignedShort2 != 0) {
/* 142 */                           byte[] bArr = new byte[unsignedShort2];
/* 143 */                           dataInputStream.readFully(bArr);
/* 145 */                           arrayList.add(0, new C0794de(bArr));
                                    i++;
/* 147 */                           C0827el.m885a(3, f671a, "Session report added: " + i);
                                } else {
/* 150 */                           C0827el.m885a(3, f671a, "Persistent file loaded");
/* 153 */                           m704a(z);
/* 154 */                           m701a(j);
/* 155 */                           m703a(arrayList);
/* 152 */                           return true;
                                }
                            }
                        } else {
/* 157 */                   C0827el.m885a(3, f671a, "Api keys do not match, old: " + m699a(str) + ", new: " + m699a(utf));
                            return false;
                        }
                    } else {
/* 161 */               C0827el.m885a(5, f671a, "Deleting old file version: " + unsignedShort);
                        return false;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 170 */       private static String m699a(String str) {
/* 171 */           if (str != null && str.length() > 4) {
/* 172 */               StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < str.length() - 4; i++) {
/* 174 */                   sb.append('*');
                        }
/* 176 */               sb.append(str.substring(str.length() - 4));
/* 177 */               return sb.toString();
                    }
/* 179 */           return str;
                }
            }
