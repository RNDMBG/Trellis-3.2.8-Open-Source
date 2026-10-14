            package com.flurry.sdk;

            import java.io.DataInputStream;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.List;
            
            
            
    public class C0797dh {

                
                private static final String f671a = C0797dh.class.getSimpleName();

                
                private boolean f672b;

                
                private List<C0794de> f673c;

                
                private long f674d = -1;

                
        public void m704a(boolean z) {
            this.f672b = z;
                }

                
        public boolean m705a() {
            return this.f672b;
                }

                
        public void m703a(List<C0794de> list) {
            this.f673c = list;
                }

                
        public List<C0794de> m707b() {
            return this.f673c;
                }

                
        public void m701a(long j) {
            this.f674d = j;
                }

                
        public long m708c() {
            return this.f674d;
                }

                
        public void m702a(DataOutputStream dataOutputStream, String str, String str2) throws IOException {
                    try {
                        try {
                    dataOutputStream.writeShort(46586);
                    dataOutputStream.writeShort(2);
                    dataOutputStream.writeUTF(str);
                    dataOutputStream.writeUTF(str2);
                    dataOutputStream.writeBoolean(this.f672b);
                    dataOutputStream.writeLong(this.f674d);
                            for (int size = this.f673c.size() - 1; size >= 0; size--) {
                        byte[] bArrM610a = this.f673c.get(size).m610a();
                        int length = bArrM610a.length;
                        if (length + 2 + dataOutputStream.size() > 50000) {
                            C0827el.m885a(6, f671a, "discarded sessions: " + size);
                                    break;
                                } else {
                            dataOutputStream.writeShort(length);
                            dataOutputStream.write(bArrM610a);
                                }
                            }
                    dataOutputStream.writeShort(0);
                    C0843fb.m962a(dataOutputStream);
                        } catch (Throwable th) {
                    C0827el.m886a(6, f671a, "", th);
                    throw new IOException(th.getMessage());
                        }
                    } catch (Throwable th2) {
                        C0843fb.m962a(dataOutputStream);
                        throw th2;
                    }
                }

                
        public boolean m706a(DataInputStream dataInputStream, String str) throws IOException {
            boolean zM700a = false;
                    try {
                        try {
                    int unsignedShort = dataInputStream.readUnsignedShort();
                    C0827el.m885a(4, f671a, "Magic: " + unsignedShort);
                    if (unsignedShort == 46586) {
                        zM700a = m700a(str, dataInputStream);
                            } else {
                        C0827el.m885a(3, f671a, "Unexpected file type");
                            }
                   C0843fb.m962a(dataInputStream);
                   return zM700a;
                        } catch (Throwable th) {
                   C0827el.m886a(6, f671a, "Error when loading persistent file", th);
                   throw new IOException(th.getMessage());
                        }
                    } catch (Throwable th2) {
                        C0843fb.m962a(dataInputStream);
                        throw th2;
                    }
                }

                
       private boolean m700a(String str, DataInputStream dataInputStream) throws IOException {
           int unsignedShort = dataInputStream.readUnsignedShort();
           C0827el.m885a(3, f671a, "File version: " + unsignedShort);
           if (unsignedShort > 2) {
               C0827el.m885a(6, f671a, "Unknown agent file version: " + unsignedShort);
               throw new IOException("Unknown agent file version: " + unsignedShort);
                    }
           if (unsignedShort >= 2) {
               String utf = dataInputStream.readUTF();
               C0827el.m885a(3, f671a, "Loading API key: " + m699a(str));
               if (utf.equals(str)) {
                   ArrayList arrayList = new ArrayList();
                   dataInputStream.readUTF();
                   boolean z = dataInputStream.readBoolean();
                   long j = dataInputStream.readLong();
                   C0827el.m885a(3, f671a, "Loading session reports");
                            int i = 0;
                            while (true) {
                       int unsignedShort2 = dataInputStream.readUnsignedShort();
                       if (unsignedShort2 != 0) {
                           byte[] bArr = new byte[unsignedShort2];
                           dataInputStream.readFully(bArr);
                           arrayList.add(0, new C0794de(bArr));
                                    i++;
                           C0827el.m885a(3, f671a, "Session report added: " + i);
                                } else {
                           C0827el.m885a(3, f671a, "Persistent file loaded");
                           m704a(z);
                           m701a(j);
                           m703a(arrayList);
                           return true;
                                }
                            }
                        } else {
                   C0827el.m885a(3, f671a, "Api keys do not match, old: " + m699a(str) + ", new: " + m699a(utf));
                            return false;
                        }
                    } else {
               C0827el.m885a(5, f671a, "Deleting old file version: " + unsignedShort);
                        return false;
                    }
                }

                
       private static String m699a(String str) {
           if (str != null && str.length() > 4) {
               StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < str.length() - 4; i++) {
                   sb.append('*');
                        }
               sb.append(str.substring(str.length() - 4));
               return sb.toString();
                    }
           return str;
                }
            }
