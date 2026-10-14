            package com.flurry.sdk;

            import java.io.ByteArrayOutputStream;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Map;
            
            
            
    public class C0794de {

                
                private static final String f595b = C0794de.class.getSimpleName();

                
                byte[] f596a;

        public C0794de(byte[] bArr) {
            this.f596a = bArr;
                }

        public C0794de(C0795df c0795df) throws Throwable {
                    DataOutputStream dataOutputStream;
                    int i;
            DataOutputStream dataOutputStream2 = null;
                    try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        try {
                    dataOutputStream.writeShort(3);
                    dataOutputStream.writeUTF(c0795df.m611a());
                    dataOutputStream.writeLong(c0795df.m621b());
                    dataOutputStream.writeLong(c0795df.m626c());
                    dataOutputStream.writeLong(c0795df.m630d());
                    Map<String, String> mapM632e = c0795df.m632e();
                    if (mapM632e == null) {
                        dataOutputStream.writeShort(0);
                            } else {
                        dataOutputStream.writeShort(mapM632e.size());
                                for (Map.Entry<String, String> entry : mapM632e.entrySet()) {
                            dataOutputStream.writeUTF(entry.getKey());
                            dataOutputStream.writeUTF(entry.getValue());
                            dataOutputStream.writeByte(0);
                                }
                            }
                    dataOutputStream.writeUTF(c0795df.m633f());
                    dataOutputStream.writeUTF(c0795df.m634g());
                    dataOutputStream.writeByte(c0795df.m635h());
                    dataOutputStream.writeUTF(c0795df.m636i());
                    if (c0795df.m637j() == null) {
                        dataOutputStream.writeBoolean(false);
                            } else {
                        dataOutputStream.writeBoolean(true);
                        dataOutputStream.writeDouble(m609a(c0795df.m637j().getLatitude()));
                        dataOutputStream.writeDouble(m609a(c0795df.m637j().getLongitude()));
                        dataOutputStream.writeFloat(c0795df.m637j().getAccuracy());
                            }
                    dataOutputStream.writeInt(c0795df.m638k());
                    dataOutputStream.writeByte(-1);
                    dataOutputStream.writeByte(-1);
                    dataOutputStream.writeByte(c0795df.m639l());
                    if (c0795df.m640m() == null) {
                        dataOutputStream.writeBoolean(false);
                            } else {
                        dataOutputStream.writeBoolean(true);
                        dataOutputStream.writeLong(c0795df.m640m().longValue());
                            }
                    Map<String, C0784cu.a> mapM641n = c0795df.m641n();
                    if (mapM641n == null) {
                        dataOutputStream.writeShort(0);
                            } else {
                        dataOutputStream.writeShort(mapM641n.size());
                                for (Map.Entry<String, C0784cu.a> entry2 : mapM641n.entrySet()) {
                            dataOutputStream.writeUTF(entry2.getKey());
                            dataOutputStream.writeInt(entry2.getValue().f535a);
                                }
                            }
                    List<C0788cy> listM642o = c0795df.m642o();
                    if (listM642o == null) {
                        dataOutputStream.writeShort(0);
                            } else {
                        dataOutputStream.writeShort(listM642o.size());
                        Iterator<C0788cy> it = listM642o.iterator();
                                while (it.hasNext()) {
                            dataOutputStream.write(it.next().m567e());
                                }
                            }
                    dataOutputStream.writeBoolean(c0795df.m643p());
                   List<C0787cx> listM645r = c0795df.m645r();
                   if (listM645r != null) {
                                int i2 = 0;
                                int iM556a = 0;
                                int i3 = 0;
                                while (true) {
                           if (i2 >= listM645r.size()) {
                                        i = i3;
                                        break;
                                    }
                           iM556a += listM645r.get(i2).m556a();
                           if (iM556a > 160000) {
                               C0827el.m885a(5, f595b, "Error Log size exceeded. No more event details logged.");
                                        i = i3;
                                        break;
                                    } else {
                               i3++;
                               i2++;
                                    }
                                }
                            } else {
                                i = 0;
                            }
                   dataOutputStream.writeInt(c0795df.m644q());
                   dataOutputStream.writeShort(i);
                   for (int i4 = 0; i4 < i; i4++) {
                       dataOutputStream.write(listM645r.get(i4).m557b());
                            }
                   dataOutputStream.writeInt(-1);
                   dataOutputStream.writeShort(0);
                   dataOutputStream.writeShort(0);
                   dataOutputStream.writeShort(0);
                   this.f596a = byteArrayOutputStream.toByteArray();
                   C0843fb.m962a(dataOutputStream);
                        } catch (IOException e) {
                   e = e;
                            dataOutputStream2 = dataOutputStream;
                            try {
                       C0827el.m886a(6, f595b, "", e);
                       throw e;
                            } catch (Throwable th) {
                       th = th;
                                dataOutputStream = dataOutputStream2;
                                C0843fb.m962a(dataOutputStream);
                                throw th;
                            }
                        } catch (Throwable th2) {
                   th = th2;
                            C0843fb.m962a(dataOutputStream);
                            throw th;
                        }
                    } catch (IOException e2) {
               e = e2;
                    } catch (Throwable th3) {
               th = th3;
                dataOutputStream = null;
                    }
                }

                
       public byte[] m610a() {
           return this.f596a;
                }

                
       double m609a(double d) {
           return Math.round(d * 1000.0d) / 1000.0d;
                }
            }
