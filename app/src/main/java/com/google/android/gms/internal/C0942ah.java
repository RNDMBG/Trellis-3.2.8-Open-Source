            package com.google.android.gms.internal;

            import android.content.Intent;
            import android.content.pm.PackageManager;
            import android.net.Uri;
            import co.vine.android.HomeTabActivity;
            import java.util.HashMap;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ah */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0942ah {

                /* JADX INFO: renamed from: ez */
                public static final InterfaceC0943ai f1153ez = new AnonymousClass1();

                /* JADX INFO: renamed from: eA */
                public static final InterfaceC0943ai f1145eA = new AnonymousClass2();

                /* JADX INFO: renamed from: eB */
                public static final InterfaceC0943ai f1146eB = new AnonymousClass3();

                /* JADX INFO: renamed from: eC */
                public static final InterfaceC0943ai f1147eC = new AnonymousClass4();

                /* JADX INFO: renamed from: eD */
                public static final InterfaceC0943ai f1148eD = new AnonymousClass5();

                /* JADX INFO: renamed from: eE */
                public static final InterfaceC0943ai f1149eE = new AnonymousClass6();

                /* JADX INFO: renamed from: eF */
                public static final InterfaceC0943ai f1150eF = new C0944aj();

                /* JADX INFO: renamed from: eG */
                public static final InterfaceC0943ai f1151eG = new AnonymousClass7();

                /* JADX INFO: renamed from: eH */
                public static final InterfaceC0943ai f1152eH = new C0945ak();

                /* JADX INFO: renamed from: com.google.android.gms.internal.ah$1, reason: invalid class name */
                static class AnonymousClass1 implements InterfaceC0943ai {
                    AnonymousClass1() {
                    }

                    @Override // com.google.android.gms.internal.InterfaceC0943ai
                    /* JADX INFO: renamed from: a */
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

                /* JADX INFO: renamed from: com.google.android.gms.internal.ah$2, reason: invalid class name */
                static class AnonymousClass2 implements InterfaceC0943ai {
                    AnonymousClass2() {
                    }

                    @Override // com.google.android.gms.internal.InterfaceC0943ai
                    /* JADX INFO: renamed from: a */
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

                /* JADX INFO: renamed from: com.google.android.gms.internal.ah$3, reason: invalid class name */
                static class AnonymousClass3 implements InterfaceC0943ai {
                    AnonymousClass3() {
                    }

                    @Override // com.google.android.gms.internal.InterfaceC0943ai
                    /* JADX INFO: renamed from: a */
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        BinderC0967bf binderC0967bfM1471au = c1005cq.m1471au();
                        if (binderC0967bfM1471au == null) {
                            C1002cn.m1458q("A GMSG tried to close something that wasn't an overlay.");
                        } else {
                            binderC0967bfM1471au.close();
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ah$4, reason: invalid class name */
                static class AnonymousClass4 implements InterfaceC0943ai {
                    AnonymousClass4() {
                    }

                    @Override // com.google.android.gms.internal.InterfaceC0943ai
                    /* JADX INFO: renamed from: a */
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        BinderC0967bf binderC0967bfM1471au = c1005cq.m1471au();
                        if (binderC0967bfM1471au == null) {
                            C1002cn.m1458q("A GMSG tried to use a custom close button on something that wasn't an overlay.");
                        } else {
                            binderC0967bfM1471au.m1311d("1".equals(map.get("custom_close")));
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ah$5, reason: invalid class name */
                static class AnonymousClass5 implements InterfaceC0943ai {
                    AnonymousClass5() {
                    }

                    @Override // com.google.android.gms.internal.InterfaceC0943ai
                    /* JADX INFO: renamed from: a */
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        String str = map.get(HomeTabActivity.PREFIX_USER_ID);
                        if (str == null) {
                            C1002cn.m1458q("URL missing from httpTrack GMSG.");
                        } else {
                            new C1000cl(c1005cq.getContext(), c1005cq.m1475ay().f1596hP, str).start();
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ah$6, reason: invalid class name */
                static class AnonymousClass6 implements InterfaceC0943ai {
                    AnonymousClass6() {
                    }

                    @Override // com.google.android.gms.internal.InterfaceC0943ai
                    /* JADX INFO: renamed from: a */
                    public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                        C1002cn.m1456o("Received log message: " + map.get("string"));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.ah$7, reason: invalid class name */
                static class AnonymousClass7 implements InterfaceC0943ai {
                    AnonymousClass7() {
                    }

                    @Override // com.google.android.gms.internal.InterfaceC0943ai
                    /* JADX INFO: renamed from: a */
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
