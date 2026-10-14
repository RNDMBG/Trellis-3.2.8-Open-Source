            package com.google.android.gms.internal;

            import android.content.Context;
            import android.webkit.WebResourceResponse;
            import android.webkit.WebView;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.ByteArrayInputStream;
            import java.io.File;
            import java.io.IOException;
            import java.io.InputStreamReader;
            import java.net.HttpURLConnection;
            import java.net.URL;
            
            
            
            public final class C1008ct extends C1006cr {
                public C1008ct(C1005cq c1005cq, boolean z) {
                    super(c1005cq, z);
                }

                
                private static WebResourceResponse m1495b(Context context, String str, String str2) throws IOException {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
                    try {
                        C0997ci.m1416a(context, str, true, httpURLConnection);
                        httpURLConnection.connect();
                        WebResourceResponse webResourceResponse = new WebResourceResponse("application/javascript", IoUtils.UTF_8, new ByteArrayInputStream(C0997ci.m1410a(new InputStreamReader(httpURLConnection.getInputStream())).getBytes(IoUtils.UTF_8)));
                        httpURLConnection.disconnect();
                        return webResourceResponse;
                    } catch (Throwable th) {
                        httpURLConnection.disconnect();
                        throw th;
                    }
                }

                @Override 
                public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
                    WebResourceResponse webResourceResponseM1495b;
                    try {
                        if (!"mraid.js".equalsIgnoreCase(new File(url).getName())) {
                            webResourceResponseM1495b = super.shouldInterceptRequest(webView, url);
                        } else if (webView instanceof C1005cq) {
                            C1005cq c1005cq = (C1005cq) webView;
                            c1005cq.m1473aw().m1481S();
                            if (c1005cq.m1472av().f2164ex) {
                                C1002cn.m1457p("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_interstitial.js)");
                                webResourceResponseM1495b = m1495b(c1005cq.getContext(), this.f1611fG.m1475ay().f1596hP, "http://media.admob.com/mraid/v1/mraid_app_interstitial.js");
                            } else if (c1005cq.m1476az()) {
                                C1002cn.m1457p("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_expanded_banner.js)");
                                webResourceResponseM1495b = m1495b(c1005cq.getContext(), this.f1611fG.m1475ay().f1596hP, "http://media.admob.com/mraid/v1/mraid_app_expanded_banner.js");
                            } else {
                                C1002cn.m1457p("shouldInterceptRequest(http://media.admob.com/mraid/v1/mraid_app_banner.js)");
                                webResourceResponseM1495b = m1495b(c1005cq.getContext(), this.f1611fG.m1475ay().f1596hP, "http://media.admob.com/mraid/v1/mraid_app_banner.js");
                            }
                        } else {
                            C1002cn.m1458q("Tried to intercept request from a WebView that wasn't an AdWebView.");
                            webResourceResponseM1495b = super.shouldInterceptRequest(webView, url);
                        }
                        return webResourceResponseM1495b;
                    } catch (IOException e) {
                        C1002cn.m1458q("Could not fetching MRAID JS. " + e.getMessage());
                        return super.shouldInterceptRequest(webView, url);
                    }
                }
            }
