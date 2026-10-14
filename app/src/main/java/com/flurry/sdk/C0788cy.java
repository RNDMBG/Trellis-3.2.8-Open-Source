            package com.flurry.sdk;

            import java.io.ByteArrayOutputStream;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.util.Map;
            
            
            
    public final class C0788cy {

                
                private int f548a;

                
                private String f549b;

                
                private Map<String, String> f550c;

                
                private long f551d;

                
                private boolean f552e;

                
                private boolean f553f;

                
                private long f554g;

        public C0788cy(int i, String str, Map<String, String> map, long j, boolean z) {
            this.f548a = i;
            this.f549b = str;
            this.f550c = map;
            this.f551d = j;
            this.f552e = z;
            if (this.f552e) {
                this.f553f = false;
                    } else {
                this.f553f = true;
                    }
                }

                
        public boolean m561a() {
            return this.f552e;
                }

                
        public boolean m564b() {
            return this.f553f;
                }

                
        public boolean m562a(String str) {
                    return this.f552e && this.f554g == 0 && this.f549b.equals(str);
                }

                
        public void m559a(long j) {
            this.f553f = true;
            this.f554g = j - this.f551d;
            C0827el.m885a(3, "FlurryAgent", "Ended event '" + this.f549b + "' (" + this.f551d + ") after " + this.f554g + "ms");
                }

                
        public void m560a(Map<String, String> map) {
            if (this.f550c == null || this.f550c.size() == 0) {
                this.f550c = map;
                return;
                    }
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                if (this.f550c.containsKey(entry.getKey())) {
                    this.f550c.remove(entry.getKey());
                    this.f550c.put(entry.getKey(), entry.getValue());
                        } else {
                    this.f550c.put(entry.getKey(), entry.getValue());
                        }
                    }
                }

                
        public Map<String, String> m565c() {
            return this.f550c;
                }

                
        public void m563b(Map<String, String> map) {
            this.f550c = map;
                }

                
        public int m566d() {
            return m567e().length;
                }

                
       public byte[] m567e() throws Throwable {
                    DataOutputStream dataOutputStream;
                    Throwable th;
                    DataOutputStream dataOutputStream2;
                    byte[] byteArray;
                    try {
               ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
               dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        try {
                   dataOutputStream.writeShort(this.f548a);
                   dataOutputStream.writeUTF(this.f549b);
                   if (this.f550c == null) {
                       dataOutputStream.writeShort(0);
                            } else {
                       dataOutputStream.writeShort(this.f550c.size());
                                for (Map.Entry<String, String> entry : this.f550c.entrySet()) {
                           dataOutputStream.writeUTF(C0843fb.m959a(entry.getKey()));
                           dataOutputStream.writeUTF(C0843fb.m959a(entry.getValue()));
                                }
                            }
                   dataOutputStream.writeLong(this.f551d);
                   dataOutputStream.writeLong(this.f554g);
                   dataOutputStream.flush();
                   byteArray = byteArrayOutputStream.toByteArray();
                   C0843fb.m962a(dataOutputStream);
                        } catch (IOException e) {
                            dataOutputStream2 = dataOutputStream;
                            try {
                       byteArray = new byte[0];
                       C0843fb.m962a(dataOutputStream2);
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
               dataOutputStream2 = null;
                    } catch (Throwable th4) {
               dataOutputStream = null;
                        th = th4;
                    }
           return byteArray;
                }
            }
