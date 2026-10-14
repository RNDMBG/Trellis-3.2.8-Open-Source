            package com.flurry.sdk;

            import android.os.Looper;
            import java.io.Closeable;
            import java.io.DataInputStream;
            import java.io.DataOutputStream;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            import java.util.UUID;
            
            
            
    public class C0792dc {

                
                private static final String f586d = C0792dc.class.getSimpleName();

                
                String f587a;

                
                long f588b = -1;

                
                int f589c = -1;

                
                private File f590e;

        public C0792dc() {
            this.f587a = null;
            this.f590e = null;
            this.f587a = UUID.randomUUID().toString();
            this.f590e = C0801dl.m732a().m736b().getFileStreamPath(".flurrydatasenderblock." + this.f587a);
                }

        public C0792dc(String str) {
            this.f587a = null;
            this.f590e = null;
            this.f587a = str;
            this.f590e = C0801dl.m732a().m736b().getFileStreamPath(".flurrydatasenderblock." + this.f587a);
                }

                
        public String m593a() {
            return this.f587a;
                }

                
                
                
                
                
                
                
                
                
        public boolean m594a(byte[] bArr) throws Throwable {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
            C0827el.m885a(6, f586d, "setData(byte[]) running on the MAIN thread!");
                    }
            C0827el.m885a(4, f586d, "Writing FlurryDataSenderBlockInfo: " + this.f590e.getAbsolutePath());
                    if (!C0842fa.m954a(this.f590e)) {
                        return false;
                    }
                    try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(this.f590e))) {
                        int length = bArr.length;
                        dataOutputStream.writeShort(length);
                        dataOutputStream.write(bArr);
                        dataOutputStream.writeShort(0);
                        this.f589c = length;
                        this.f588b = System.currentTimeMillis();
                        return true;
                    } catch (Throwable th) {
                        C0827el.m886a(6, f586d, "", th);
                        return false;
                    }
                }

                        public byte[] m595b() throws Throwable {
                    DataInputStream dataInputStream;
                    Throwable th;
                    byte[] bArr = null;
           if (Looper.myLooper() == Looper.getMainLooper()) {
               C0827el.m885a(6, f586d, "getData() running on the MAIN thread!");
                    }
           if (this.f590e.exists()) {
               C0827el.m885a(4, f586d, "Reading FlurryDataSenderBlockInfo: " + this.f590e.getAbsolutePath());
                        try {
                   dataInputStream = new DataInputStream(new FileInputStream(this.f590e));
                            try {
                                try {
                           int unsignedShort = dataInputStream.readUnsignedShort();
                           if (unsignedShort == 0) {
                               C0843fb.m962a(dataInputStream);
                                    } else {
                               bArr = new byte[unsignedShort];
                               dataInputStream.readFully(bArr);
                               if (dataInputStream.readUnsignedShort() == 0) {
                                        }
                               C0843fb.m962a(dataInputStream);
                                    }
                                } catch (Throwable th2) {
                           th = th2;
                           C0827el.m886a(6, f586d, "Error when loading persistent file", th);
                           C0843fb.m962a(dataInputStream);
                                }
                            } catch (Throwable th3) {
                       th = th3;
                                C0843fb.m962a(dataInputStream);
                                throw th;
                            }
                        } catch (Throwable th4) {
                            dataInputStream = null;
                            th = th4;
                            C0843fb.m962a(dataInputStream);
                            throw th;
                        }
                    } else {
               C0827el.m885a(4, f586d, "Agent cache file doesn't exist.");
                    }
           return bArr;
                }

                
       public boolean m596c() {
           if (this.f590e.exists()) {
               if (this.f590e.delete()) {
                   C0827el.m885a(4, f586d, "Deleted persistence file");
                   this.f588b = -1L;
                   this.f589c = -1;
                   return true;
                        }
               C0827el.m885a(6, f586d, "Cannot delete persistence file");
                    }
           return false;
                }
            }
