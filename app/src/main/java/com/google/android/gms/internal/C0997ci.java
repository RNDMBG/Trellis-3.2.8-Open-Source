            package com.google.android.gms.internal;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.pm.PackageManager;
            import android.content.pm.ResolveInfo;
            import android.net.Uri;
            import android.os.Build;
            import android.os.Bundle;
            import android.webkit.WebSettings;
            import android.webkit.WebView;
            import com.google.android.gms.ads.AdActivity;
            import java.io.IOException;
            import java.net.HttpURLConnection;
            import java.nio.CharBuffer;
            import java.util.Arrays;
            import java.util.Collection;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Map;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
            
            public final class C0997ci {

                
                private static String f1590hK;

                
                private static final Object f1588gL = new Object();

                
                private static boolean f1589hJ = true;

                
                private static boolean f1591hL = false;

                
                static class AnonymousClass1 implements Runnable {

                    
                    final  Context f1592gO;

                    AnonymousClass1(Context context) {
                        this.f1592gO = context;
                    }

                    @Override 
                    public void run() {
                        synchronized (C0997ci.m1424ap()) {
                            try {
                                C0997ci.m1433k(C0997ci.m1432k(this.f1592gO));
                                C0997ci.m1424ap().notifyAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                
                private static final class a extends BroadcastReceiver {
                    private a() {
                    }

                     a(AnonymousClass1 anonymousClass1) {
                        this();
                    }

                    @Override 
                    public void onReceive(Context context, Intent intent) {
                        if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                            C0997ci.m1428h(true);
                        } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                            C0997ci.m1428h(false);
                        }
                    }
                }

                
                public static String m1410a(Readable readable) throws IOException {
                    StringBuilder sb = new StringBuilder();
                    CharBuffer charBufferAllocate = CharBuffer.allocate(2048);
                    while (true) {
                        int i = readable.read(charBufferAllocate);
                        if (i == -1) {
                            return sb.toString();
                        }
                        charBufferAllocate.flip();
                        sb.append((CharSequence) charBufferAllocate, 0, i);
                    }
                }

                
                private static JSONArray m1411a(Collection<?> collection) throws JSONException {
                    JSONArray jSONArray = new JSONArray();
                    Iterator<?> it = collection.iterator();
                    while (it.hasNext()) {
                        m1418a(jSONArray, it.next());
                    }
                    return jSONArray;
                }

                
                private static JSONArray m1412a(Object[] objArr) throws JSONException {
                    JSONArray jSONArray = new JSONArray();
                    for (Object obj : objArr) {
                        m1418a(jSONArray, obj);
                    }
                    return jSONArray;
                }

                
                private static JSONObject m1413a(Bundle bundle) throws JSONException {
                    JSONObject jSONObject = new JSONObject();
                    for (String str : bundle.keySet()) {
                        m1419a(jSONObject, str, bundle.get(str));
                    }
                    return jSONObject;
                }

                
                public static void m1414a(Context context, String str, WebSettings webSettings) {
                    webSettings.setUserAgentString(m1425b(context, str));
                }

                
                public static void m1415a(Context context, String str, List<String> list) {
                    Iterator<String> it = list.iterator();
                    while (it.hasNext()) {
                        new C1000cl(context, str, it.next()).start();
                    }
                }

                
                public static void m1416a(Context context, String str, boolean z, HttpURLConnection httpURLConnection) {
                    httpURLConnection.setConnectTimeout(60000);
                    httpURLConnection.setInstanceFollowRedirects(z);
                    httpURLConnection.setReadTimeout(60000);
                    httpURLConnection.setRequestProperty("User-Agent", m1425b(context, str));
                    httpURLConnection.setUseCaches(false);
                }

                
                public static void m1417a(WebView webView) {
                    if (Build.VERSION.SDK_INT >= 11) {
                        C0998cj.m1437a(webView);
                    }
                }

                
                private static void m1418a(JSONArray jSONArray, Object obj) throws JSONException {
                    if (obj instanceof Bundle) {
                        jSONArray.put(m1413a((Bundle) obj));
                        return;
                    }
                    if (obj instanceof Map) {
                        jSONArray.put(m1434l((Map) obj));
                        return;
                    }
                    if (obj instanceof Collection) {
                        jSONArray.put(m1411a((Collection<?>) obj));
                    } else if (obj instanceof Object[]) {
                        jSONArray.put(m1412a((Object[]) obj));
                    } else {
                        jSONArray.put(obj);
                    }
                }

                
                private static void m1419a(JSONObject jSONObject, String str, Object obj) throws JSONException {
                    if (obj instanceof Bundle) {
                        jSONObject.put(str, m1413a((Bundle) obj));
                        return;
                    }
                    if (obj instanceof Map) {
                        jSONObject.put(str, m1434l((Map) obj));
                        return;
                    }
                    if (obj instanceof Collection) {
                        if (str == null) {
                            str = "null";
                        }
                        jSONObject.put(str, m1411a((Collection<?>) obj));
                    } else if (obj instanceof Object[]) {
                        jSONObject.put(str, m1411a(Arrays.asList((Object[]) obj)));
                    } else {
                        jSONObject.put(str, obj);
                    }
                }

                
                public static boolean m1420a(PackageManager packageManager, String str, String str2) {
                    return packageManager.checkPermission(str2, str) == 0;
                }

                
                public static boolean m1421am() {
                    return f1589hJ;
                }

                
                public static int m1422an() {
                    return Build.VERSION.SDK_INT >= 9 ? 6 : 0;
                }

                
                public static int m1423ao() {
                    return Build.VERSION.SDK_INT >= 9 ? 7 : 1;
                }

                
                static  Object m1424ap() {
                    return f1588gL;
                }

                
                private static String m1425b(Context context, String str) {
                    String str2;
                    synchronized (f1588gL) {
                        try {
                            if (f1590hK != null) {
                                str2 = f1590hK;
                            } else {
                                if (Build.VERSION.SDK_INT >= 17) {
                                    f1590hK = C0999ck.getDefaultUserAgent(context);
                                } else if (C1001cm.m1447ar()) {
                                    f1590hK = m1430j(context);
                                } else {
                                    C1001cm.f1595hO.post(new AnonymousClass1(context));
                                    while (f1590hK == null) {
                                        try {
                                            f1588gL.wait();
                                        } catch (InterruptedException e) {
                                            str2 = f1590hK;
                                        }
                                    }
                                }
                                f1590hK += " (Mobile; " + str + ")";
                                str2 = f1590hK;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return str2;
                }

                
                public static void m1426b(WebView webView) {
                    if (Build.VERSION.SDK_INT >= 11) {
                        C0998cj.m1438b(webView);
                    }
                }

                
                public static boolean m1427h(Context context) {
                    boolean z;
                    Intent intent = new Intent();
                    intent.setClassName(context, AdActivity.CLASS_NAME);
                    ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, 65536);
                    if (resolveInfoResolveActivity == null || resolveInfoResolveActivity.activityInfo == null) {
                        C1002cn.m1458q("Could not find com.google.android.gms.ads.AdActivity, please make sure it is declared in AndroidManifest.xml.");
                        return false;
                    }
                    if ((resolveInfoResolveActivity.activityInfo.configChanges & 16) == 0) {
                        C1002cn.m1458q(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "keyboard"));
                        z = false;
                    } else {
                        z = true;
                    }
                    if ((resolveInfoResolveActivity.activityInfo.configChanges & 32) == 0) {
                        C1002cn.m1458q(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "keyboardHidden"));
                        z = false;
                    }
                    if ((resolveInfoResolveActivity.activityInfo.configChanges & 128) == 0) {
                        C1002cn.m1458q(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "orientation"));
                        z = false;
                    }
                    if ((resolveInfoResolveActivity.activityInfo.configChanges & 256) == 0) {
                        C1002cn.m1458q(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "screenLayout"));
                        z = false;
                    }
                    if ((resolveInfoResolveActivity.activityInfo.configChanges & 512) == 0) {
                        C1002cn.m1458q(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "uiMode"));
                        z = false;
                    }
                    if ((resolveInfoResolveActivity.activityInfo.configChanges & 1024) == 0) {
                        C1002cn.m1458q(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "screenSize"));
                        z = false;
                    }
                    if ((resolveInfoResolveActivity.activityInfo.configChanges & 2048) != 0) {
                        return z;
                    }
                    C1002cn.m1458q(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", "smallestScreenSize"));
                    return false;
                }

                
                static  boolean m1428h(boolean z) {
                    f1589hJ = z;
                    return z;
                }

                
                public static void m1429i(Context context) {
                    if (f1591hL) {
                        return;
                    }
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.USER_PRESENT");
                    intentFilter.addAction("android.intent.action.SCREEN_OFF");
                    context.getApplicationContext().registerReceiver(new a(null), intentFilter);
                    f1591hL = true;
                }

                
                private static String m1430j(Context context) {
                    return new WebView(context).getSettings().getUserAgentString();
                }

                
                public static String m1431j(String str) {
                    return Uri.parse(str).buildUpon().query(null).build().toString();
                }

                
                static  String m1432k(Context context) {
                    return m1430j(context);
                }

                
                static  String m1433k(String str) {
                    f1590hK = str;
                    return str;
                }

                
                public static JSONObject m1434l(Map<String, ?> map) throws JSONException {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        for (String str : map.keySet()) {
                            m1419a(jSONObject, str, map.get(str));
                        }
                        return jSONObject;
                    } catch (ClassCastException e) {
                        throw new JSONException("Could not convert map to JSON: " + e.getMessage());
                    }
                }
            }
