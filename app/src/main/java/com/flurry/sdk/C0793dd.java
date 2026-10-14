            package com.flurry.sdk;

            import android.os.Looper;
            import java.io.Closeable;
            import java.io.DataInputStream;
            import java.io.DataOutputStream;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileOutputStream;
            import java.util.ArrayList;
            import java.util.LinkedHashMap;
            import java.util.LinkedList;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.flurry.sdk.dd */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class C0793dd {

                /* JADX INFO: renamed from: b */
                String f593b;

                /* JADX INFO: renamed from: c */
                LinkedHashMap<String, List<String>> f594c;

                /* JADX INFO: renamed from: d */
                private static final String f592d = C0793dd.class.getSimpleName();

                /* JADX INFO: renamed from: a */
                static final Integer f591a = 50;

/* 32 */        public C0793dd(String str) {
/* 34 */            m603a(str);
                }

                /* JADX INFO: renamed from: a */
/* 39 */        void m603a(String str) {
/* 40 */            this.f594c = new LinkedHashMap<>();
/* 41 */            this.f593b = str + "Main";
/* 42 */            List<String> listM600e = m600e(this.f593b);
/* 44 */            if (listM600e != null) {
                        for (String str2 : listM600e) {
/* 49 */                    List<String> listM600e2 = m600e(str2);
/* 51 */                    if (listM600e2 != null) {
/* 52 */                        this.f594c.put(str2, listM600e2);
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: c */
/* 57 */        private synchronized void m599c() {
                    try {
                        LinkedList linkedList = new LinkedList(this.f594c.keySet());
/* 60 */                m605b();
/* 62 */                if (!linkedList.isEmpty()) {
/* 63 */                    m598a(this.f593b, linkedList);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 68 */        public synchronized void m602a(C0792dc c0792dc, String str) {
                    List<String> linkedList;
                    boolean z = false;
                    synchronized (this) {
                        try {
                            C0827el.m885a(4, f592d, "addBlockInfo");
/* 70 */                    String strM593a = c0792dc.m593a();
/* 72 */                    List<String> list = this.f594c.get(str);
/* 75 */                    if (list == null) {
/* 76 */                        C0827el.m885a(4, f592d, "New Data Key");
/* 77 */                        linkedList = new LinkedList();
/* 78 */                        z = true;
                            } else {
                                linkedList = list;
                            }
/* 81 */                    linkedList.add(strM593a);
/* 85 */                    if (linkedList.size() > f591a.intValue()) {
/* 88 */                        m606b(linkedList.get(0));
/* 91 */                        linkedList.remove(0);
                            }
/* 94 */                    this.f594c.put(str, linkedList);
/* 97 */                    m598a(str, linkedList);
/* 109 */                   if (z) {
/* 110 */                       m599c();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 114 */       boolean m606b(String str) {
/* 116 */           return new C0792dc(str).m596c();
                }

                /* JADX INFO: renamed from: a */
/* 122 */       public boolean m604a(String str, String str2) {
/* 123 */           List<String> list = this.f594c.get(str2);
/* 125 */           boolean zRemove = false;
/* 126 */           if (list != null) {
/* 127 */               m606b(str);
/* 128 */               zRemove = list.remove(str);
                    }
/* 131 */           if (list != null && !list.isEmpty()) {
/* 132 */               this.f594c.put(str2, list);
/* 133 */               m598a(str2, list);
                    } else {
/* 135 */               m608d(str2);
                    }
/* 138 */           return zRemove;
                }

                /* JADX INFO: renamed from: a */
/* 141 */       public List<String> m601a() {
/* 142 */           return new ArrayList(this.f594c.keySet());
                }

                /* JADX INFO: renamed from: c */
/* 147 */       public List<String> m607c(String str) {
/* 148 */           return this.f594c.get(str);
                }

                /* JADX INFO: renamed from: d */
/* 154 */       public synchronized boolean m608d(String str) {
                    boolean zM597a;
                    try {
                        if (Looper.myLooper() == Looper.getMainLooper()) {
/* 156 */                   C0827el.m885a(6, f592d, "discardOutdatedBlocksForDataKey(ID) running on the MAIN thread!");
                        }
/* 160 */               File fileStreamPath = C0801dl.m732a().m736b().getFileStreamPath(".FlurrySenderIndex.info." + str);
/* 164 */               List<String> listM607c = m607c(str);
/* 165 */               if (listM607c != null) {
/* 166 */                   C0827el.m885a(4, f592d, "discardOutdatedBlocksForDataKey: notSentBlocks = " + listM607c.size());
                            for (int i = 0; i < listM607c.size(); i++) {
/* 169 */                       String str2 = listM607c.get(i);
/* 171 */                       m606b(str2);
/* 172 */                       C0827el.m885a(4, f592d, "discardOutdatedBlocksForDataKey: removed block = " + str2);
                            }
                        }
/* 177 */               this.f594c.remove(str);
/* 180 */               zM597a = m597a(fileStreamPath);
/* 182 */               m599c();
                    } catch (Throwable th) {
                        throw th;
                    }
                    return zM597a;
                }

                /* JADX INFO: renamed from: a */
/* 187 */       private synchronized boolean m597a(File file) {
                    boolean zDelete;
                    zDelete = false;
/* 189 */           if (file != null) {
                        try {
                            if (file.exists()) {
/* 190 */                       C0827el.m885a(4, f592d, "Trying to delete persistence file : " + file.getAbsolutePath());
/* 192 */                       zDelete = file.delete();
/* 193 */                       if (zDelete) {
/* 194 */                           C0827el.m885a(4, f592d, "Deleted persistence file");
                                } else {
/* 196 */                           C0827el.m885a(6, f592d, "Cannot delete persistence file");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zDelete;
                }

                /* JADX INFO: renamed from: b */
/* 203 */       void m605b() {
/* 206 */           m597a(C0801dl.m732a().m736b().getFileStreamPath(".FlurrySenderIndex.info." + this.f593b));
                }

                /* JADX INFO: renamed from: e */
/* 210 */       private synchronized List<String> m600e(String str) {
                    DataInputStream dataInputStream;
                    ArrayList arrayList;
                    Throwable th;
                    ArrayList arrayList2 = null;
                    synchronized (this) {
                        try {
                            if (Looper.myLooper() == Looper.getMainLooper()) {
/* 212 */                       C0827el.m885a(6, f592d, "readFromFile(byte[], ID) running on the MAIN thread!");
                            }
/* 216 */                   File fileStreamPath = C0801dl.m732a().m736b().getFileStreamPath(".FlurrySenderIndex.info." + str);
/* 218 */                   if (fileStreamPath.exists()) {
                                try {
/* 223 */                           dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                                    try {
                                        try {
/* 225 */                                   int unsignedShort = dataInputStream.readUnsignedShort();
/* 226 */                                   if (unsignedShort == 0) {
/* 251 */                                       C0843fb.m962a(dataInputStream);
                                            } else {
/* 230 */                                       arrayList = new ArrayList(unsignedShort);
                                                for (int i = 0; i < unsignedShort; i++) {
                                                    try {
/* 235 */                                               int unsignedShort2 = dataInputStream.readUnsignedShort();
/* 236 */                                               C0827el.m885a(4, f592d, "read iter " + i + " dataLength = " + unsignedShort2);
/* 237 */                                               byte[] bArr = new byte[unsignedShort2];
/* 238 */                                               dataInputStream.readFully(bArr);
/* 241 */                                               arrayList.add(new String(bArr));
                                                    } catch (Throwable th2) {
                                                        th = th2;
/* 249 */                                               C0827el.m886a(6, f592d, "Error when loading persistent file", th);
/* 251 */                                               C0843fb.m962a(dataInputStream);
                                                        arrayList2 = arrayList;
                                                        return arrayList2;
                                                    }
                                                }
/* 245 */                                       if (dataInputStream.readUnsignedShort() == 0) {
                                                }
/* 251 */                                       C0843fb.m962a(dataInputStream);
                                            }
                                        } catch (Throwable th3) {
                                            arrayList = null;
                                            th = th3;
                                        }
                                    } catch (Throwable th4) {
/* 251 */                               th = th4;
                                        C0843fb.m962a(dataInputStream);
                                        throw th;
                                    }
                                } catch (Throwable th5) {
                                    dataInputStream = null;
                                    arrayList = null;
                                    th = th5;
                                }
                            } else {
/* 254 */                       C0827el.m885a(5, f592d, "Agent cache file doesn't exist.");
                                arrayList = null;
                            }
                            arrayList2 = arrayList;
                        } catch (Throwable th6) {
                            throw th6;
                        }
                    }
                    return arrayList2;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r0v3, types: [android.content.Context] */
                /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.String] */
                /* JADX WARN: Type inference failed for: r2v6 */
                /* JADX WARN: Type inference failed for: r2v7, types: [java.io.Closeable] */
                /* JADX INFO: renamed from: a */
/* 261 */       private synchronized boolean m598a(String str, List<String> list) {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
/* 263 */              C0827el.m885a(6, f592d, "saveToFile(byte[], ID) running on the MAIN thread!");
                    }
                    File fileStreamPath = C0801dl.m732a().m736b()
                            .getFileStreamPath(".FlurrySenderIndex.info." + str);
                    if (!C0842fa.m954a(fileStreamPath)) {
                        return false;
                    }
                    try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(fileStreamPath))) {
                        dataOutputStream.writeShort(list.size());
                        for (int i = 0; i < list.size(); i++) {
                            byte[] bytes = list.get(i).getBytes();
                            C0827el.m885a(4, f592d, "write iter " + i + " dataLength = " + bytes.length);
                            dataOutputStream.writeShort(bytes.length);
                            dataOutputStream.write(bytes);
                        }
                        dataOutputStream.writeShort(0);
                        return true;
                    } catch (Throwable th) {
                        C0827el.m886a(6, f592d, "", th);
                        return false;
                    }
                }
            }
