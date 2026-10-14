            package com.flurry.sdk;

            import java.io.ByteArrayOutputStream;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.flurry.sdk.cy */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public final class C0788cy {

                /* JADX INFO: renamed from: a */
                private int f548a;

                /* JADX INFO: renamed from: b */
                private String f549b;

                /* JADX INFO: renamed from: c */
                private Map<String, String> f550c;

                /* JADX INFO: renamed from: d */
                private long f551d;

                /* JADX INFO: renamed from: e */
                private boolean f552e;

                /* JADX INFO: renamed from: f */
                private boolean f553f;

                /* JADX INFO: renamed from: g */
                private long f554g;

/* 21 */        public C0788cy(int i, String str, Map<String, String> map, long j, boolean z) {
/* 23 */            this.f548a = i;
/* 24 */            this.f549b = str;
/* 25 */            this.f550c = map;
/* 26 */            this.f551d = j;
/* 27 */            this.f552e = z;
/* 29 */            if (this.f552e) {
/* 30 */                this.f553f = false;
                    } else {
/* 32 */                this.f553f = true;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 37 */        public boolean m561a() {
/* 38 */            return this.f552e;
                }

                /* JADX INFO: renamed from: b */
/* 41 */        public boolean m564b() {
/* 42 */            return this.f553f;
                }

                /* JADX INFO: renamed from: a */
/* 46 */        public boolean m562a(String str) {
                    return this.f552e && this.f554g == 0 && this.f549b.equals(str);
                }

                /* JADX INFO: renamed from: a */
/* 51 */        public void m559a(long j) {
/* 52 */            this.f553f = true;
/* 53 */            this.f554g = j - this.f551d;
/* 54 */            C0827el.m885a(3, "FlurryAgent", "Ended event '" + this.f549b + "' (" + this.f551d + ") after " + this.f554g + "ms");
                }

                /* JADX INFO: renamed from: a */
/* 58 */        public void m560a(Map<String, String> map) {
/* 59 */            if (this.f550c == null || this.f550c.size() == 0) {
/* 61 */                this.f550c = map;
/* 79 */                return;
                    }
                    for (Map.Entry<String, String> entry : map.entrySet()) {
/* 68 */                if (this.f550c.containsKey(entry.getKey())) {
/* 70 */                    this.f550c.remove(entry.getKey());
/* 71 */                    this.f550c.put(entry.getKey(), entry.getValue());
                        } else {
/* 75 */                    this.f550c.put(entry.getKey(), entry.getValue());
                        }
                    }
                }

                /* JADX INFO: renamed from: c */
/* 82 */        public Map<String, String> m565c() {
/* 83 */            return this.f550c;
                }

                /* JADX INFO: renamed from: b */
/* 87 */        public void m563b(Map<String, String> map) {
/* 88 */            this.f550c = map;
                }

                /* JADX INFO: renamed from: d */
/* 92 */        public int m566d() {
/* 93 */            return m567e().length;
                }

                /* JADX INFO: renamed from: e */
/* 107 */       public byte[] m567e() throws Throwable {
                    DataOutputStream dataOutputStream;
                    Throwable th;
                    DataOutputStream dataOutputStream2;
                    byte[] byteArray;
                    try {
/* 111 */               ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 112 */               dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        try {
/* 114 */                   dataOutputStream.writeShort(this.f548a);
/* 115 */                   dataOutputStream.writeUTF(this.f549b);
/* 116 */                   if (this.f550c == null) {
/* 118 */                       dataOutputStream.writeShort(0);
                            } else {
/* 122 */                       dataOutputStream.writeShort(this.f550c.size());
                                for (Map.Entry<String, String> entry : this.f550c.entrySet()) {
/* 125 */                           dataOutputStream.writeUTF(C0843fb.m959a(entry.getKey()));
/* 126 */                           dataOutputStream.writeUTF(C0843fb.m959a(entry.getValue()));
                                }
                            }
/* 129 */                   dataOutputStream.writeLong(this.f551d);
/* 130 */                   dataOutputStream.writeLong(this.f554g);
/* 132 */                   dataOutputStream.flush();
/* 134 */                   byteArray = byteArrayOutputStream.toByteArray();
/* 143 */                   C0843fb.m962a(dataOutputStream);
                        } catch (IOException e) {
                            dataOutputStream2 = dataOutputStream;
                            try {
/* 139 */                       byteArray = new byte[0];
/* 143 */                       C0843fb.m962a(dataOutputStream2);
                            } catch (Throwable th2) {
                                th = th2;
                                dataOutputStream = dataOutputStream2;
                                C0843fb.m962a(dataOutputStream);
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            C0843fb.m962a(dataOutputStream);
                            throw th;
                        }
                    } catch (IOException e2) {
/* 108 */               dataOutputStream2 = null;
                    } catch (Throwable th4) {
/* 108 */               dataOutputStream = null;
                        th = th4;
                    }
/* 146 */           return byteArray;
                }
            }
