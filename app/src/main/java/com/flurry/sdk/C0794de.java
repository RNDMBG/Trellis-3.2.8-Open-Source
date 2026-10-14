            package com.flurry.sdk;

            import java.io.ByteArrayOutputStream;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.flurry.sdk.de */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class C0794de {

                /* JADX INFO: renamed from: b */
                private static final String f595b = C0794de.class.getSimpleName();

                /* JADX INFO: renamed from: a */
                byte[] f596a;

/* 21 */        public C0794de(byte[] bArr) {
/* 23 */            this.f596a = bArr;
                }

/* 25 */        public C0794de(C0795df c0795df) throws Throwable {
                    DataOutputStream dataOutputStream;
                    int i;
/* 27 */            DataOutputStream dataOutputStream2 = null;
                    try {
/* 29 */                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 30 */                dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        try {
/* 32 */                    dataOutputStream.writeShort(3);
/* 33 */                    dataOutputStream.writeUTF(c0795df.m611a());
/* 34 */                    dataOutputStream.writeLong(c0795df.m621b());
/* 35 */                    dataOutputStream.writeLong(c0795df.m626c());
/* 36 */                    dataOutputStream.writeLong(c0795df.m630d());
/* 39 */                    Map<String, String> mapM632e = c0795df.m632e();
/* 40 */                    if (mapM632e == null) {
/* 41 */                        dataOutputStream.writeShort(0);
                            } else {
/* 43 */                        dataOutputStream.writeShort(mapM632e.size());
                                for (Map.Entry<String, String> entry : mapM632e.entrySet()) {
/* 45 */                            dataOutputStream.writeUTF(entry.getKey());
/* 46 */                            dataOutputStream.writeUTF(entry.getValue());
/* 47 */                            dataOutputStream.writeByte(0);
                                }
                            }
/* 51 */                    dataOutputStream.writeUTF(c0795df.m633f());
/* 52 */                    dataOutputStream.writeUTF(c0795df.m634g());
/* 53 */                    dataOutputStream.writeByte(c0795df.m635h());
/* 54 */                    dataOutputStream.writeUTF(c0795df.m636i());
/* 56 */                    if (c0795df.m637j() == null) {
/* 57 */                        dataOutputStream.writeBoolean(false);
                            } else {
/* 59 */                        dataOutputStream.writeBoolean(true);
/* 60 */                        dataOutputStream.writeDouble(m609a(c0795df.m637j().getLatitude()));
/* 61 */                        dataOutputStream.writeDouble(m609a(c0795df.m637j().getLongitude()));
/* 62 */                        dataOutputStream.writeFloat(c0795df.m637j().getAccuracy());
                            }
/* 65 */                    dataOutputStream.writeInt(c0795df.m638k());
/* 66 */                    dataOutputStream.writeByte(-1);
/* 67 */                    dataOutputStream.writeByte(-1);
/* 68 */                    dataOutputStream.writeByte(c0795df.m639l());
/* 69 */                    if (c0795df.m640m() == null) {
/* 70 */                        dataOutputStream.writeBoolean(false);
                            } else {
/* 72 */                        dataOutputStream.writeBoolean(true);
/* 73 */                        dataOutputStream.writeLong(c0795df.m640m().longValue());
                            }
/* 76 */                    Map<String, C0784cu.a> mapM641n = c0795df.m641n();
/* 77 */                    if (mapM641n == null) {
/* 78 */                        dataOutputStream.writeShort(0);
                            } else {
/* 80 */                        dataOutputStream.writeShort(mapM641n.size());
                                for (Map.Entry<String, C0784cu.a> entry2 : mapM641n.entrySet()) {
/* 82 */                            dataOutputStream.writeUTF(entry2.getKey());
/* 83 */                            dataOutputStream.writeInt(entry2.getValue().f535a);
                                }
                            }
/* 87 */                    List<C0788cy> listM642o = c0795df.m642o();
/* 88 */                    if (listM642o == null) {
/* 89 */                        dataOutputStream.writeShort(0);
                            } else {
/* 91 */                        dataOutputStream.writeShort(listM642o.size());
/* 92 */                        Iterator<C0788cy> it = listM642o.iterator();
                                while (it.hasNext()) {
/* 93 */                            dataOutputStream.write(it.next().m567e());
                                }
                            }
/* 96 */                    dataOutputStream.writeBoolean(c0795df.m643p());
/* 101 */                   List<C0787cx> listM645r = c0795df.m645r();
/* 102 */                   if (listM645r != null) {
                                int i2 = 0;
                                int iM556a = 0;
                                int i3 = 0;
                                while (true) {
/* 103 */                           if (i2 >= listM645r.size()) {
                                        i = i3;
                                        break;
                                    }
/* 104 */                           iM556a += listM645r.get(i2).m556a();
/* 105 */                           if (iM556a > 160000) {
/* 106 */                               C0827el.m885a(5, f595b, "Error Log size exceeded. No more event details logged.");
                                        i = i3;
                                        break;
                                    } else {
/* 110 */                               i3++;
/* 103 */                               i2++;
                                    }
                                }
                            } else {
                                i = 0;
                            }
/* 115 */                   dataOutputStream.writeInt(c0795df.m644q());
/* 116 */                   dataOutputStream.writeShort(i);
/* 117 */                   for (int i4 = 0; i4 < i; i4++) {
/* 118 */                       dataOutputStream.write(listM645r.get(i4).m557b());
                            }
/* 122 */                   dataOutputStream.writeInt(-1);
/* 123 */                   dataOutputStream.writeShort(0);
/* 126 */                   dataOutputStream.writeShort(0);
/* 129 */                   dataOutputStream.writeShort(0);
/* 131 */                   this.f596a = byteArrayOutputStream.toByteArray();
/* 136 */                   C0843fb.m962a(dataOutputStream);
                        } catch (IOException e) {
/* 132 */                   e = e;
                            dataOutputStream2 = dataOutputStream;
                            try {
/* 133 */                       C0827el.m886a(6, f595b, "", e);
/* 134 */                       throw e;
                            } catch (Throwable th) {
/* 136 */                       th = th;
                                dataOutputStream = dataOutputStream2;
                                C0843fb.m962a(dataOutputStream);
                                throw th;
                            }
                        } catch (Throwable th2) {
/* 136 */                   th = th2;
                            C0843fb.m962a(dataOutputStream);
                            throw th;
                        }
                    } catch (IOException e2) {
/* 132 */               e = e2;
                    } catch (Throwable th3) {
/* 136 */               th = th3;
/* 27 */                dataOutputStream = null;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 140 */       public byte[] m610a() {
/* 141 */           return this.f596a;
                }

                /* JADX INFO: renamed from: a */
/* 147 */       double m609a(double d) {
/* 150 */           return Math.round(d * 1000.0d) / 1000.0d;
                }
            }
