            package com.google.android.gms.internal;

            import android.content.Intent;
            import android.content.pm.PackageManager;
            import android.net.Uri;
            import co.vine.android.HomeTabActivity;
            import java.util.HashMap;
            import java.util.Map;
            
            
            
            public final class C0942ah {

                
                public static final InterfaceC0943ai f1153ez = new AnonymousClass1();

                
                public static final InterfaceC0943ai f1145eA = new AnonymousClass2();

                
                public static final InterfaceC0943ai f1146eB = new AnonymousClass3();

                
                public static final InterfaceC0943ai f1147eC = new AnonymousClass4();

                
                public static final InterfaceC0943ai f1148eD = new AnonymousClass5();

                
                public static final InterfaceC0943ai f1149eE = new AnonymousClass6();

                
                public static final InterfaceC0943ai f1150eF = new C0944aj();

                
                public static final InterfaceC0943ai f1151eG = new AnonymousClass7();

                
                public static final InterfaceC0943ai f1152eH = new C0945ak();

                
                static class AnonymousClass1 implements InterfaceC0943ai {
                    AnonymousClass1() {
                    }

                    @Override 
                    
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        String str = map.get("urls");
                        if (str == null) {
                            C1002cn.m1458q("URLs missing in canOpenURLs GMSG.");
                            return;
                        }
                        String[] strArrSplit = str.split(",");
                        HashMap map2 = new HashMap();
                        PackageManager packageManager = c1005cq.getContext().getPackageManager();
                        for (String str2 : strArrSplit) {
                            String[] strArrSplit2 = str2.split(";", 2);
                            map2.put(str2, Boolean.valueOf(packageManager.resolveActivity(new Intent(strArrSplit2.length > 1 ? strArrSplit2[1].trim() : "android.intent.action.VIEW", Uri.parse(strArrSplit2[0].trim())), 65536) != null));
                        }
                        c1005cq.m1468a("openableURLs", map2);
                    }
                }

                
                static class AnonymousClass2 implements InterfaceC0943ai {
                    AnonymousClass2() {
                    }

                    @Override 
                    
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        Uri uriM2125a;
                        String str = map.get(HomeTabActivity.PREFIX_USER_ID);
                        if (str == null) {
                            C1002cn.m1458q("URL missing from click GMSG.");
                            return;
                        }
                        Uri uri = Uri.parse(str);
                        try {
                            C1110h c1110hM1474ax = c1005cq.m1474ax();
                            uriM2125a = (c1110hM1474ax == null || !c1110hM1474ax.m2127a(uri)) ? uri : c1110hM1474ax.m2125a(uri, c1005cq.getContext());
                        } catch (C1111i e) {
                            C1002cn.m1458q("Unable to append parameter to URL: " + str);
                        }
                        new C1000cl(c1005cq.getContext(), c1005cq.m1475ay().f1596hP, uriM2125a.toString()).start();
                    }
                }

                
                static class AnonymousClass3 implements InterfaceC0943ai {
                    AnonymousClass3() {
                    }

                    @Override 
                    
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        BinderC0967bf binderC0967bfM1471au = c1005cq.m1471au();
                        if (binderC0967bfM1471au == null) {
                            C1002cn.m1458q("A GMSG tried to close something that wasn't an overlay.");
                        } else {
                            binderC0967bfM1471au.close();
                        }
                    }
                }

                
                static class AnonymousClass4 implements InterfaceC0943ai {
                    AnonymousClass4() {
                    }

                    @Override 
                    
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        BinderC0967bf binderC0967bfM1471au = c1005cq.m1471au();
                        if (binderC0967bfM1471au == null) {
                            C1002cn.m1458q("A GMSG tried to use a custom close button on something that wasn't an overlay.");
                        } else {
                            binderC0967bfM1471au.m1311d("1".equals(map.get("custom_close")));
                        }
                    }
                }

                
                static class AnonymousClass5 implements InterfaceC0943ai {
                    AnonymousClass5() {
                    }

                    @Override 
                    
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        String str = map.get(HomeTabActivity.PREFIX_USER_ID);
                        if (str == null) {
                            C1002cn.m1458q("URL missing from httpTrack GMSG.");
                        } else {
                            new C1000cl(c1005cq.getContext(), c1005cq.m1475ay().f1596hP, str).start();
                        }
                    }
                }

                
                static class AnonymousClass6 implements InterfaceC0943ai {
                    AnonymousClass6() {
                    }

                    @Override 
                    
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        C1002cn.m1456o("Received log message: " + map.get("string"));
                    }
                }

                
                static class AnonymousClass7 implements InterfaceC0943ai {
                    AnonymousClass7() {
                    }

                    @Override 
                    
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        String str = map.get("tx");
                        String str2 = map.get("ty");
                        String str3 = map.get("td");
                        try {
                            int i = Integer.parseInt(str);
                            int i2 = Integer.parseInt(str2);
                            int i3 = Integer.parseInt(str3);
                            C1110h c1110hM1474ax = c1005cq.m1474ax();
                            if (c1110hM1474ax != null) {
                                c1110hM1474ax.m2128g().mo1518a(i, i2, i3);
                            }
                        } catch (NumberFormatException e) {
                            C1002cn.m1458q("Could not parse touch parameters from gmsg.");
                        }
                    }
                }
            }
