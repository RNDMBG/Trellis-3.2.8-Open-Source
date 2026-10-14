            package com.flurry.sdk;

            import android.os.Build;
            import com.flurry.android.FlurryAgent;
            import java.io.ByteArrayOutputStream;
            import java.io.DataOutputStream;
            import java.nio.ByteBuffer;
            import java.security.DigestOutputStream;
            import java.util.List;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.flurry.sdk.cz */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class C0789cz {

                /* JADX INFO: renamed from: a */
                private static final String f555a = C0789cz.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private byte[] f556b;

/* 37 */        public C0789cz(String str, String str2, boolean z, boolean z2, long j, long j2, List<C0794de> list, Map<EnumC0804do, ByteBuffer> map, Map<String, List<String>> map2, Map<String, List<String>> map3, Map<String, Map<String, String>> map4, long j3) throws Throwable {
                    DataOutputStream dataOutputStream;
                    byte[] byteArray;
/* 28 */            this.f556b = null;
/* 41 */            DataOutputStream dataOutputStream2 = null;
                    try {
/* 43 */                C0816ea c0816ea = new C0816ea();
/* 44 */                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 45 */                DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, c0816ea);
/* 46 */                dataOutputStream = new DataOutputStream(digestOutputStream);
                        try {
/* 48 */                    dataOutputStream.writeShort(29);
/* 51 */                    dataOutputStream.writeShort(0);
/* 54 */                    dataOutputStream.writeLong(0L);
/* 55 */                    dataOutputStream.writeShort(0);
/* 57 */                    dataOutputStream.writeShort(3);
/* 58 */                    dataOutputStream.writeShort(FlurryAgent.getAgentVersion());
/* 59 */                    dataOutputStream.writeLong(j3);
/* 60 */                    dataOutputStream.writeUTF(str);
/* 61 */                    dataOutputStream.writeUTF(str2);
/* 65 */                    dataOutputStream.writeShort(map.size());
                            for (Map.Entry<EnumC0804do, ByteBuffer> entry : map.entrySet()) {
/* 67 */                        dataOutputStream.writeShort(entry.getKey().f713d);
/* 68 */                        byte[] bArrArray = entry.getValue().array();
/* 69 */                        dataOutputStream.writeShort(bArrArray.length);
/* 70 */                        dataOutputStream.write(bArrArray);
                            }
/* 73 */                    dataOutputStream.writeByte(0);
/* 76 */                    dataOutputStream.writeBoolean(z);
/* 79 */                    dataOutputStream.writeBoolean(z2);
/* 81 */                    dataOutputStream.writeLong(j);
/* 82 */                    dataOutputStream.writeLong(j2);
/* 86 */                    dataOutputStream.writeShort(6);
/* 87 */                    dataOutputStream.writeUTF("device.model");
/* 88 */                    dataOutputStream.writeUTF(Build.MODEL);
/* 89 */                    dataOutputStream.writeByte(0);
/* 90 */                    dataOutputStream.writeUTF("build.brand");
/* 91 */                    dataOutputStream.writeUTF(Build.BRAND);
/* 92 */                    dataOutputStream.writeByte(0);
/* 93 */                    dataOutputStream.writeUTF("build.id");
/* 94 */                    dataOutputStream.writeUTF(Build.ID);
/* 95 */                    dataOutputStream.writeByte(0);
/* 96 */                    dataOutputStream.writeUTF("version.release");
/* 97 */                    dataOutputStream.writeUTF(Build.VERSION.RELEASE);
/* 98 */                    dataOutputStream.writeByte(0);
/* 99 */                    dataOutputStream.writeUTF("build.device");
/* 100 */                   dataOutputStream.writeUTF(Build.DEVICE);
/* 101 */                   dataOutputStream.writeByte(0);
/* 102 */                   dataOutputStream.writeUTF("build.product");
/* 103 */                   dataOutputStream.writeUTF(Build.PRODUCT);
/* 104 */                   dataOutputStream.writeByte(0);
/* 107 */                   dataOutputStream.writeShort(map2 != null ? map2.keySet().size() : 0);
/* 109 */                   if (map2 != null) {
/* 110 */                       C0827el.m885a(3, f555a, "sending referrer values because it exists");
                                for (Map.Entry<String, List<String>> entry2 : map2.entrySet()) {
/* 113 */                           C0827el.m885a(3, f555a, "Referrer Entry:  " + entry2.getKey() + "=" + entry2.getValue());
/* 115 */                           dataOutputStream.writeUTF(entry2.getKey());
/* 116 */                           C0827el.m885a(3, f555a, "referrer key is :" + entry2.getKey());
/* 117 */                           dataOutputStream.writeShort(entry2.getValue().size());
                                    for (String str3 : entry2.getValue()) {
/* 120 */                               dataOutputStream.writeUTF(str3);
/* 121 */                               C0827el.m885a(3, f555a, "referrer value is :" + str3);
                                    }
                                }
                            }
/* 127 */                   dataOutputStream.writeBoolean(false);
/* 130 */                   int size = map3 != null ? map3.keySet().size() : 0;
/* 132 */                   C0827el.m885a(3, f555a, "optionsMapSize is:  " + size);
/* 134 */                   dataOutputStream.writeShort(size);
/* 136 */                   if (map3 != null) {
/* 137 */                       C0827el.m885a(3, f555a, "sending launch options");
                                for (Map.Entry<String, List<String>> entry3 : map3.entrySet()) {
/* 140 */                           C0827el.m885a(3, f555a, "Launch Options Key:  " + entry3.getKey());
/* 141 */                           dataOutputStream.writeUTF(entry3.getKey());
/* 143 */                           dataOutputStream.writeShort(entry3.getValue().size());
                                    for (String str4 : entry3.getValue()) {
/* 146 */                               dataOutputStream.writeUTF(str4);
/* 147 */                               C0827el.m885a(3, f555a, "Launch Options value is :" + str4);
                                    }
                                }
                            }
/* 153 */                   int size2 = map4 != null ? map4.keySet().size() : 0;
/* 155 */                   C0827el.m885a(3, f555a, "numOriginAttributions is:  " + size);
/* 157 */                   dataOutputStream.writeShort(size2);
/* 159 */                   if (map4 != null) {
                                for (Map.Entry<String, Map<String, String>> entry4 : map4.entrySet()) {
/* 161 */                           C0827el.m885a(3, f555a, "Origin Atttribute Key:  " + entry4.getKey());
/* 162 */                           dataOutputStream.writeUTF(entry4.getKey());
/* 164 */                           dataOutputStream.writeShort(entry4.getValue().size());
/* 166 */                           C0827el.m885a(3, f555a, "Origin Attribute Map Size for " + entry4.getKey() + ":  " + entry4.getValue().size());
                                    for (Map.Entry<String, String> entry5 : entry4.getValue().entrySet()) {
/* 169 */                               C0827el.m885a(3, f555a, "Origin Atttribute for " + entry4.getKey() + ":  " + entry5.getKey() + ":" + entry5.getValue());
/* 170 */                               dataOutputStream.writeUTF(entry5.getKey() != null ? entry5.getKey() : "");
/* 171 */                               dataOutputStream.writeUTF(entry5.getValue() != null ? entry5.getValue() : "");
                                    }
                                }
                            }
/* 176 */                   int size3 = list.size();
/* 178 */                   dataOutputStream.writeShort(size3);
                            for (int i = 0; i < size3; i++) {
/* 181 */                       dataOutputStream.write(list.get(i).m610a());
                            }
/* 185 */                   dataOutputStream.writeShort(0);
/* 188 */                   digestOutputStream.on(false);
/* 189 */                   dataOutputStream.write(c0816ea.m822a());
/* 191 */                   dataOutputStream.close();
/* 192 */                   byteArray = byteArrayOutputStream.toByteArray();
/* 196 */                   C0843fb.m962a(dataOutputStream);
                        } catch (Throwable th) {
/* 193 */                   th = th;
                            dataOutputStream2 = dataOutputStream;
                            try {
/* 194 */                       C0827el.m886a(6, f555a, "Error when generating report", th);
/* 196 */                       C0843fb.m962a(dataOutputStream2);
/* 40 */                        byteArray = null;
                            } catch (Throwable th2) {
                                th = th2;
                                dataOutputStream = dataOutputStream2;
                                C0843fb.m962a(dataOutputStream);
                                throw th;
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
/* 41 */                dataOutputStream = null;
                    }
/* 199 */           this.f556b = byteArray;
                }

                /* JADX INFO: renamed from: a */
/* 202 */       public byte[] m568a() {
/* 203 */           return this.f556b;
                }
            }
