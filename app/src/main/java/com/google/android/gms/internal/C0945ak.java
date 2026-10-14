            package com.google.android.gms.internal;

            import android.os.SystemClock;
            import android.util.DisplayMetrics;
            import android.view.MotionEvent;
            import co.vine.android.BaseFragment;
            import co.vine.android.service.VineService;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ak */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0945ak implements InterfaceC0943ai {
                /* JADX INFO: renamed from: a */
                private static int m1253a(DisplayMetrics displayMetrics, Map<String, String> map, String str, int i) {
                    String str2 = map.get(str);
                    if (str2 == null) {
                        return i;
                    }
                    try {
                        return C1001cm.m1443a(displayMetrics, Integer.parseInt(str2));
                    } catch (NumberFormatException e) {
                        C1002cn.m1458q("Could not parse " + str + " in a video GMSG: " + str2);
                        return i;
                    }
                }

                @Override // com.google.android.gms.internal.InterfaceC0943ai
                /* JADX INFO: renamed from: a */
                public void mo1249a(C1005cq c1005cq, Map<String, String> map) {
                    String str = map.get(BaseFragment.ARG_ACTION);
                    if (str == null) {
                        C1002cn.m1458q("Action missing from video GMSG.");
                        return;
                    }
                    BinderC0967bf binderC0967bfM1471au = c1005cq.m1471au();
                    if (binderC0967bfM1471au == null) {
                        C1002cn.m1458q("Could not get ad overlay for a video GMSG.");
                        return;
                    }
                    boolean zEqualsIgnoreCase = "new".equalsIgnoreCase(str);
                    boolean zEqualsIgnoreCase2 = "position".equalsIgnoreCase(str);
                    if (zEqualsIgnoreCase || zEqualsIgnoreCase2) {
                        DisplayMetrics displayMetrics = c1005cq.getContext().getResources().getDisplayMetrics();
                        int iM1253a = m1253a(displayMetrics, map, "x", 0);
                        int iM1253a2 = m1253a(displayMetrics, map, "y", 0);
                        int iM1253a3 = m1253a(displayMetrics, map, "w", -1);
                        int iM1253a4 = m1253a(displayMetrics, map, "h", -1);
                        if (zEqualsIgnoreCase && binderC0967bfM1471au.m1304Q() == null) {
                            binderC0967bfM1471au.m1309c(iM1253a, iM1253a2, iM1253a3, iM1253a4);
                            return;
                        } else {
                            binderC0967bfM1471au.m1308b(iM1253a, iM1253a2, iM1253a3, iM1253a4);
                            return;
                        }
                    }
                    C0971bj c0971bjM1304Q = binderC0967bfM1471au.m1304Q();
                    if (c0971bjM1304Q == null) {
                        C0971bj.m1326a(c1005cq, "no_video_view", (String) null);
                        return;
                    }
                    if ("click".equalsIgnoreCase(str)) {
                        DisplayMetrics displayMetrics2 = c1005cq.getContext().getResources().getDisplayMetrics();
                        int iM1253a5 = m1253a(displayMetrics2, map, "x", 0);
                        int iM1253a6 = m1253a(displayMetrics2, map, "y", 0);
                        long jUptimeMillis = SystemClock.uptimeMillis();
                        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, iM1253a5, iM1253a6, 0);
                        c0971bjM1304Q.m1331b(motionEventObtain);
                        motionEventObtain.recycle();
                        return;
                    }
                    if ("controls".equalsIgnoreCase(str)) {
                        String str2 = map.get("enabled");
                        if (str2 == null) {
                            C1002cn.m1458q("Enabled parameter missing from controls video GMSG.");
                            return;
                        } else {
                            c0971bjM1304Q.m1332f(Boolean.parseBoolean(str2));
                            return;
                        }
                    }
                    if ("currentTime".equalsIgnoreCase(str)) {
                        String str3 = map.get(VineService.EXTRA_ANCHOR);
                        if (str3 == null) {
                            C1002cn.m1458q("Time parameter missing from currentTime video GMSG.");
                            return;
                        }
                        try {
                            c0971bjM1304Q.seekTo((int) (Float.parseFloat(str3) * 1000.0f));
                            return;
                        } catch (NumberFormatException e) {
                            C1002cn.m1458q("Could not parse time parameter from currentTime video GMSG: " + str3);
                            return;
                        }
                    }
                    if ("hide".equalsIgnoreCase(str)) {
                        c0971bjM1304Q.setVisibility(4);
                        return;
                    }
                    if ("load".equalsIgnoreCase(str)) {
                        c0971bjM1304Q.m1329Z();
                        return;
                    }
                    if ("pause".equalsIgnoreCase(str)) {
                        c0971bjM1304Q.pause();
                        return;
                    }
                    if ("play".equalsIgnoreCase(str)) {
                        c0971bjM1304Q.play();
                        return;
                    }
                    if ("show".equalsIgnoreCase(str)) {
                        c0971bjM1304Q.setVisibility(0);
                    } else if ("src".equalsIgnoreCase(str)) {
                        c0971bjM1304Q.m1333i(map.get("src"));
                    } else {
                        C1002cn.m1458q("Unknown video action: " + str);
                    }
                }
            }
