            package com.google.android.gms.internal;

            import java.util.Map;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.af */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0940af implements InterfaceC0943ai {

                /* JADX INFO: renamed from: ey */
                private final InterfaceC0941ag f1144ey;

                public C0940af(InterfaceC0941ag interfaceC0941ag) {
                    this.f1144ey = interfaceC0941ag;
                }

                @Override // com.google.android.gms.internal.InterfaceC0943ai
                /* JADX INFO: renamed from: a */
                public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                    String str = map.get("name");
                    if (str == null) {
                        C1002cn.m1458q("App event with no name parameter.");
                    } else {
                        this.f1144ey.mo1250a(str, map.get("info"));
                    }
                }
            }
