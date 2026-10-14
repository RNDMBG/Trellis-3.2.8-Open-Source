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
            
            
            
    public class C0793dd {

                
                String f593b;

                
                LinkedHashMap<String, List<String>> f594c;

                
                private static final String f592d = C0793dd.class.getSimpleName();

                
                static final Integer f591a = 50;

        public C0793dd(String str) {
            m603a(str);
                }

                
        void m603a(String str) {
            this.f594c = new LinkedHashMap<>();
            this.f593b = str + "Main";
            List<String> listM600e = m600e(this.f593b);
            if (listM600e != null) {
                        for (String str2 : listM600e) {
                    List<String> listM600e2 = m600e(str2);
                    if (listM600e2 != null) {
                        this.f594c.put(str2, listM600e2);
                            }
                        }
                    }
                }

                
        private synchronized void m599c() {
                    try {
                        LinkedList linkedList = new LinkedList(this.f594c.keySet());
                m605b();
                if (!linkedList.isEmpty()) {
                    m598a(this.f593b, linkedList);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
        public synchronized void m602a(C0792dc c0792dc, String str) {
                    List<String> linkedList;
                    boolean z = false;
                    synchronized (this) {
                        try {
                            C0827el.m885a(4, f592d, "addBlockInfo");
                    String strM593a = c0792dc.m593a();
                    List<String> list = this.f594c.get(str);
                    if (list == null) {
                        C0827el.m885a(4, f592d, "New Data Key");
                        linkedList = new LinkedList();
                        z = true;
                            } else {
                                linkedList = list;
                            }
                    linkedList.add(strM593a);
                    if (linkedList.size() > f591a.intValue()) {
                        m606b(linkedList.get(0));
                        linkedList.remove(0);
                            }
                    this.f594c.put(str, linkedList);
                    m598a(str, linkedList);
                   if (z) {
                       m599c();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
       boolean m606b(String str) {
           return new C0792dc(str).m596c();
                }

                
       public boolean m604a(String str, String str2) {
           List<String> list = this.f594c.get(str2);
           boolean zRemove = false;
           if (list != null) {
               m606b(str);
               zRemove = list.remove(str);
                    }
           if (list != null && !list.isEmpty()) {
               this.f594c.put(str2, list);
               m598a(str2, list);
                    } else {
               m608d(str2);
                    }
           return zRemove;
                }

                
       public List<String> m601a() {
           return new ArrayList(this.f594c.keySet());
                }

                
       public List<String> m607c(String str) {
           return this.f594c.get(str);
                }

                
       public synchronized boolean m608d(String str) {
                    boolean zM597a;
                    try {
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                   C0827el.m885a(6, f592d, "discardOutdatedBlocksForDataKey(ID) running on the MAIN thread!");
                        }
               File fileStreamPath = C0801dl.m732a().m736b().getFileStreamPath(".FlurrySenderIndex.info." + str);
               List<String> listM607c = m607c(str);
               if (listM607c != null) {
                   C0827el.m885a(4, f592d, "discardOutdatedBlocksForDataKey: notSentBlocks = " + listM607c.size());
                            for (int i = 0; i < listM607c.size(); i++) {
                       String str2 = listM607c.get(i);
                       m606b(str2);
                       C0827el.m885a(4, f592d, "discardOutdatedBlocksForDataKey: removed block = " + str2);
                            }
                        }
               this.f594c.remove(str);
               zM597a = m597a(fileStreamPath);
               m599c();
                    } catch (Throwable th) {
                        throw th;
                    }
                    return zM597a;
                }

                
       private synchronized boolean m597a(File file) {
                    boolean zDelete;
                    zDelete = false;
           if (file != null) {
                        try {
                            if (file.exists()) {
                       C0827el.m885a(4, f592d, "Trying to delete persistence file : " + file.getAbsolutePath());
                       zDelete = file.delete();
                       if (zDelete) {
                           C0827el.m885a(4, f592d, "Deleted persistence file");
                                } else {
                           C0827el.m885a(6, f592d, "Cannot delete persistence file");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return zDelete;
                }

                
       void m605b() {
           m597a(C0801dl.m732a().m736b().getFileStreamPath(".FlurrySenderIndex.info." + this.f593b));
                }

                
       private synchronized List<String> m600e(String str) {
                    DataInputStream dataInputStream;
                    ArrayList arrayList;
                    Throwable th;
                    ArrayList arrayList2 = null;
                    synchronized (this) {
                        try {
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                       C0827el.m885a(6, f592d, "readFromFile(byte[], ID) running on the MAIN thread!");
                            }
                   File fileStreamPath = C0801dl.m732a().m736b().getFileStreamPath(".FlurrySenderIndex.info." + str);
                   if (fileStreamPath.exists()) {
                                try {
                           dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                                    try {
                                        try {
                                   int unsignedShort = dataInputStream.readUnsignedShort();
                                   if (unsignedShort == 0) {
                                       C0843fb.m962a(dataInputStream);
                                            } else {
                                       arrayList = new ArrayList(unsignedShort);
                                                for (int i = 0; i < unsignedShort; i++) {
                                                    try {
                                               int unsignedShort2 = dataInputStream.readUnsignedShort();
                                               C0827el.m885a(4, f592d, "read iter " + i + " dataLength = " + unsignedShort2);
                                               byte[] bArr = new byte[unsignedShort2];
                                               dataInputStream.readFully(bArr);
                                               arrayList.add(new String(bArr));
                                                    } catch (Throwable th2) {
                                                        th = th2;
                                               C0827el.m886a(6, f592d, "Error when loading persistent file", th);
                                               C0843fb.m962a(dataInputStream);
                                                        arrayList2 = arrayList;
                                                        return arrayList2;
                                                    }
                                                }
                                       if (dataInputStream.readUnsignedShort() == 0) {
                                                }
                                       C0843fb.m962a(dataInputStream);
                                            }
                                        } catch (Throwable th3) {
                                            arrayList = null;
                                            th = th3;
                                        }
                                    } catch (Throwable th4) {
                               th = th4;
                                        C0843fb.m962a(dataInputStream);
                                        throw th;
                                    }
                                } catch (Throwable th5) {
                                    dataInputStream = null;
                                    arrayList = null;
                                    th = th5;
                                }
                            } else {
                       C0827el.m885a(5, f592d, "Agent cache file doesn't exist.");
                                arrayList = null;
                            }
                            arrayList2 = arrayList;
                        } catch (Throwable th6) {
                            throw th6;
                        }
                    }
                    return arrayList2;
                }

                
                
                
                
                
                
       private synchronized boolean m598a(String str, List<String> list) {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
              C0827el.m885a(6, f592d, "saveToFile(byte[], ID) running on the MAIN thread!");
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
