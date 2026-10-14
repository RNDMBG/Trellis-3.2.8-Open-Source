            package com.flurry.sdk;

            import android.os.Build;
            import com.flurry.android.FlurryAgent;
            import java.io.ByteArrayOutputStream;
            import java.io.DataOutputStream;
            import java.nio.ByteBuffer;
            import java.security.DigestOutputStream;
            import java.util.List;
            import java.util.Map;
            
            
            
    public class C0789cz {

                
                private static final String f555a = C0789cz.class.getSimpleName();

                
                private byte[] f556b;

        public C0789cz(String str, String str2, boolean z, boolean z2, long j, long j2, List<C0794de> list, Map<EnumC0804do, ByteBuffer> map, Map<String, List<String>> map2, Map<String, List<String>> map3, Map<String, Map<String, String>> map4, long j3) throws Throwable {
                    DataOutputStream dataOutputStream;
                    byte[] byteArray;
            this.f556b = null;
            DataOutputStream dataOutputStream2 = null;
                    try {
                C0816ea c0816ea = new C0816ea();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, c0816ea);
                dataOutputStream = new DataOutputStream(digestOutputStream);
                        try {
                    dataOutputStream.writeShort(29);
                    dataOutputStream.writeShort(0);
                    dataOutputStream.writeLong(0L);
                    dataOutputStream.writeShort(0);
                    dataOutputStream.writeShort(3);
                    dataOutputStream.writeShort(FlurryAgent.getAgentVersion());
                    dataOutputStream.writeLong(j3);
                    dataOutputStream.writeUTF(str);
                    dataOutputStream.writeUTF(str2);
                    dataOutputStream.writeShort(map.size());
                            for (Map.Entry<EnumC0804do, ByteBuffer> entry : map.entrySet()) {
                        dataOutputStream.writeShort(entry.getKey().f713d);
                        byte[] bArrArray = entry.getValue().array();
                        dataOutputStream.writeShort(bArrArray.length);
                        dataOutputStream.write(bArrArray);
                            }
                    dataOutputStream.writeByte(0);
                    dataOutputStream.writeBoolean(z);
                    dataOutputStream.writeBoolean(z2);
                    dataOutputStream.writeLong(j);
                    dataOutputStream.writeLong(j2);
                    dataOutputStream.writeShort(6);
                    dataOutputStream.writeUTF("device.model");
                    dataOutputStream.writeUTF(Build.MODEL);
                    dataOutputStream.writeByte(0);
                    dataOutputStream.writeUTF("build.brand");
                    dataOutputStream.writeUTF(Build.BRAND);
                    dataOutputStream.writeByte(0);
                    dataOutputStream.writeUTF("build.id");
                    dataOutputStream.writeUTF(Build.ID);
                    dataOutputStream.writeByte(0);
                    dataOutputStream.writeUTF("version.release");
                    dataOutputStream.writeUTF(Build.VERSION.RELEASE);
                    dataOutputStream.writeByte(0);
                    dataOutputStream.writeUTF("build.device");
                   dataOutputStream.writeUTF(Build.DEVICE);
                   dataOutputStream.writeByte(0);
                   dataOutputStream.writeUTF("build.product");
                   dataOutputStream.writeUTF(Build.PRODUCT);
                   dataOutputStream.writeByte(0);
                   dataOutputStream.writeShort(map2 != null ? map2.keySet().size() : 0);
                   if (map2 != null) {
                       C0827el.m885a(3, f555a, "sending referrer values because it exists");
                                for (Map.Entry<String, List<String>> entry2 : map2.entrySet()) {
                           C0827el.m885a(3, f555a, "Referrer Entry:  " + entry2.getKey() + "=" + entry2.getValue());
                           dataOutputStream.writeUTF(entry2.getKey());
                           C0827el.m885a(3, f555a, "referrer key is :" + entry2.getKey());
                           dataOutputStream.writeShort(entry2.getValue().size());
                                    for (String str3 : entry2.getValue()) {
                               dataOutputStream.writeUTF(str3);
                               C0827el.m885a(3, f555a, "referrer value is :" + str3);
                                    }
                                }
                            }
                   dataOutputStream.writeBoolean(false);
                   int size = map3 != null ? map3.keySet().size() : 0;
                   C0827el.m885a(3, f555a, "optionsMapSize is:  " + size);
                   dataOutputStream.writeShort(size);
                   if (map3 != null) {
                       C0827el.m885a(3, f555a, "sending launch options");
                                for (Map.Entry<String, List<String>> entry3 : map3.entrySet()) {
                           C0827el.m885a(3, f555a, "Launch Options Key:  " + entry3.getKey());
                           dataOutputStream.writeUTF(entry3.getKey());
                           dataOutputStream.writeShort(entry3.getValue().size());
                                    for (String str4 : entry3.getValue()) {
                               dataOutputStream.writeUTF(str4);
                               C0827el.m885a(3, f555a, "Launch Options value is :" + str4);
                                    }
                                }
                            }
                   int size2 = map4 != null ? map4.keySet().size() : 0;
                   C0827el.m885a(3, f555a, "numOriginAttributions is:  " + size);
                   dataOutputStream.writeShort(size2);
                   if (map4 != null) {
                                for (Map.Entry<String, Map<String, String>> entry4 : map4.entrySet()) {
                           C0827el.m885a(3, f555a, "Origin Atttribute Key:  " + entry4.getKey());
                           dataOutputStream.writeUTF(entry4.getKey());
                           dataOutputStream.writeShort(entry4.getValue().size());
                           C0827el.m885a(3, f555a, "Origin Attribute Map Size for " + entry4.getKey() + ":  " + entry4.getValue().size());
                                    for (Map.Entry<String, String> entry5 : entry4.getValue().entrySet()) {
                               C0827el.m885a(3, f555a, "Origin Atttribute for " + entry4.getKey() + ":  " + entry5.getKey() + ":" + entry5.getValue());
                               dataOutputStream.writeUTF(entry5.getKey() != null ? entry5.getKey() : "");
                               dataOutputStream.writeUTF(entry5.getValue() != null ? entry5.getValue() : "");
                                    }
                                }
                            }
                   int size3 = list.size();
                   dataOutputStream.writeShort(size3);
                            for (int i = 0; i < size3; i++) {
                       dataOutputStream.write(list.get(i).m610a());
                            }
                   dataOutputStream.writeShort(0);
                   digestOutputStream.on(false);
                   dataOutputStream.write(c0816ea.m822a());
                   dataOutputStream.close();
                   byteArray = byteArrayOutputStream.toByteArray();
                   C0843fb.m962a(dataOutputStream);
                        } catch (Throwable th) {
                   th = th;
                            dataOutputStream2 = dataOutputStream;
                            try {
                       C0827el.m886a(6, f555a, "Error when generating report", th);
                       C0843fb.m962a(dataOutputStream2);
                        byteArray = null;
                            } catch (Throwable th2) {
                                th = th2;
                                dataOutputStream = dataOutputStream2;
                                C0843fb.m962a(dataOutputStream);
                                throw th;
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                dataOutputStream = null;
                    }
           this.f556b = byteArray;
                }

                
       public byte[] m568a() {
           return this.f556b;
                }
            }
