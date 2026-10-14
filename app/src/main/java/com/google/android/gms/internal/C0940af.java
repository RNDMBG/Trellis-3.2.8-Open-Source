            package com.google.android.gms.internal;

            import java.util.Map;
            
            
            
            public final class C0940af implements InterfaceC0943ai {

                
                private final InterfaceC0941ag f1144ey;

                public C0940af(InterfaceC0941ag interfaceC0941ag) {
                    this.f1144ey = interfaceC0941ag;
                }

                @Override 
                
                public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                    String str = map.get("name");
                    if (str == null) {
                        C1002cn.m1458q("App event with no name parameter.");
                    } else {
                        this.f1144ey.mo1250a(str, map.get("info"));
                    }
                }
            }
