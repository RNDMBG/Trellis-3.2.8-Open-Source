            package com.flurry.sdk;

            import android.os.Looper;
            import java.io.Closeable;
            import java.io.DataInputStream;
            import java.io.DataOutputStream;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            import java.util.UUID;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dc */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class C0792dc {

                /* JADX INFO: renamed from: d */
                private static final String f586d = C0792dc.class.getSimpleName();

                /* JADX INFO: renamed from: a */
                String f587a;

                /* JADX INFO: renamed from: b */
                long f588b = -1;

                /* JADX INFO: renamed from: c */
                int f589c = -1;

                /* JADX INFO: renamed from: e */
                private File f590e;

/* 34 */        public C0792dc() {
/* 28 */            this.f587a = null;
/* 32 */            this.f590e = null;
/* 36 */            this.f587a = UUID.randomUUID().toString();
/* 37 */            this.f590e = C0801dl.m732a().m736b().getFileStreamPath(".flurrydatasenderblock." + this.f587a);
                }

/* 39 */        public C0792dc(String str) {
/* 28 */            this.f587a = null;
/* 32 */            this.f590e = null;
/* 41 */            this.f587a = str;
/* 42 */            this.f590e = C0801dl.m732a().m736b().getFileStreamPath(".flurrydatasenderblock." + this.f587a);
                }

                /* JADX INFO: renamed from: a */
/* 45 */        public String m593a() {
/* 46 */            return this.f587a;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.String] */
                /* JADX WARN: Type inference failed for: r2v10 */
                /* JADX WARN: Type inference failed for: r2v11 */
                /* JADX WARN: Type inference failed for: r2v3 */
                /* JADX WARN: Type inference failed for: r2v4, types: [java.io.Closeable] */
                /* JADX WARN: Type inference failed for: r2v5 */
                /* JADX WARN: Type inference failed for: r2v7 */
                /* JADX INFO: renamed from: a */
/* 57 */        public boolean m594a(byte[] bArr) throws Throwable {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
/* 59 */            C0827el.m885a(6, f586d, "setData(byte[]) running on the MAIN thread!");
                    }
/* 64 */            C0827el.m885a(4, f586d, "Writing FlurryDataSenderBlockInfo: " + this.f590e.getAbsolutePath());
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

                /* 99 */        public byte[] m595b() throws Throwable {
                    DataInputStream dataInputStream;
                    Throwable th;
                    byte[] bArr = null;
/* 100 */           if (Looper.myLooper() == Looper.getMainLooper()) {
/* 101 */               C0827el.m885a(6, f586d, "getData() running on the MAIN thread!");
                    }
/* 106 */           if (this.f590e.exists()) {
/* 107 */               C0827el.m885a(4, f586d, "Reading FlurryDataSenderBlockInfo: " + this.f590e.getAbsolutePath());
                        try {
/* 114 */                   dataInputStream = new DataInputStream(new FileInputStream(this.f590e));
                            try {
                                try {
/* 116 */                           int unsignedShort = dataInputStream.readUnsignedShort();
/* 117 */                           if (unsignedShort == 0) {
/* 131 */                               C0843fb.m962a(dataInputStream);
                                    } else {
/* 121 */                               bArr = new byte[unsignedShort];
/* 122 */                               dataInputStream.readFully(bArr);
/* 125 */                               if (dataInputStream.readUnsignedShort() == 0) {
                                        }
/* 131 */                               C0843fb.m962a(dataInputStream);
                                    }
                                } catch (Throwable th2) {
/* 128 */                           th = th2;
/* 129 */                           C0827el.m886a(6, f586d, "Error when loading persistent file", th);
/* 131 */                           C0843fb.m962a(dataInputStream);
                                }
                            } catch (Throwable th3) {
/* 131 */                       th = th3;
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
/* 134 */               C0827el.m885a(4, f586d, "Agent cache file doesn't exist.");
                    }
/* 137 */           return bArr;
                }

                /* JADX INFO: renamed from: c */
/* 152 */       public boolean m596c() {
/* 153 */           if (this.f590e.exists()) {
/* 155 */               if (this.f590e.delete()) {
/* 156 */                   C0827el.m885a(4, f586d, "Deleted persistence file");
/* 157 */                   this.f588b = -1L;
/* 158 */                   this.f589c = -1;
/* 160 */                   return true;
                        }
/* 162 */               C0827el.m885a(6, f586d, "Cannot delete persistence file");
                    }
/* 165 */           return false;
                }
            }
