            package com.google.android.gms.internal;

            import com.google.android.gms.plus.PlusShare;
            import java.util.Map;
            
            
            
            public final class C0990cb {

                
                private C1005cq f1527fG;

                
                private String f1528gT;

                
                private final Object f1526eJ = new Object();

                
                private int f1531gw = -2;

                
                public final InterfaceC0943ai f1529gU = new AnonymousClass1();

                
                public final InterfaceC0943ai f1530gV = new AnonymousClass2();

                
                class AnonymousClass1 implements InterfaceC0943ai {
                    AnonymousClass1() {
                    }

                    @Override 
                    
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        synchronized (C0990cb.m1389a(C0990cb.this)) {
                            try {
                                C1002cn.m1458q("Invalid " + map.get("type") + " request error: " + map.get("errors"));
                                C0990cb.m1388a(C0990cb.this, 1);
                                C0990cb.m1389a(C0990cb.this).notify();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                
                class AnonymousClass2 implements InterfaceC0943ai {
                    AnonymousClass2() {
                    }

                    @Override 
                    
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        synchronized (C0990cb.m1389a(C0990cb.this)) {
                            try {
                                String str = map.get(PlusShare.KEY_CALL_TO_ACTION_URL);
                                if (str == null) {
                                    C1002cn.m1458q("URL missing in loadAdUrl GMSG.");
                                } else {
                                    C0990cb.m1390a(C0990cb.this, str);
                                    C0990cb.m1389a(C0990cb.this).notify();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                
                static  int m1388a(C0990cb c0990cb, int i) {
                    c0990cb.f1531gw = i;
                    return i;
                }

                
                static  Object m1389a(C0990cb c0990cb) {
                    return c0990cb.f1526eJ;
                }

                
                static  String m1390a(C0990cb c0990cb, String str) {
                    c0990cb.f1528gT = str;
                    return str;
                }

                
                public String m1391aj() {
                    String str;
                    synchronized (this.f1526eJ) {
                        while (this.f1528gT == null && this.f1531gw == -2) {
                            try {
                                try {
                                    this.f1526eJ.wait();
                                } catch (InterruptedException e) {
                                    C1002cn.m1458q("Ad request service was interrupted.");
                                    str = null;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        str = this.f1528gT;
                    }
                    return str;
                }

                
                public void m1392b(C1005cq c1005cq) {
                    synchronized (this.f1526eJ) {
                        try {
                            this.f1527fG = c1005cq;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                public int getErrorCode() {
                    int i;
                    synchronized (this.f1526eJ) {
                        try {
                            i = this.f1531gw;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return i;
                }
            }
