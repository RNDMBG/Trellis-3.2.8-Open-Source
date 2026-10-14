            package com.flurry.sdk;

            import org.apache.http.client.HttpClient;
            import org.apache.http.conn.scheme.PlainSocketFactory;
            import org.apache.http.conn.scheme.Scheme;
            import org.apache.http.conn.scheme.SchemeRegistry;
            import org.apache.http.conn.ssl.SSLSocketFactory;
            import org.apache.http.impl.client.DefaultHttpClient;
            import org.apache.http.impl.conn.SingleClientConnManager;
            import org.apache.http.params.HttpParams;
            
            /* JADX INFO: renamed from: com.flurry.sdk.eh */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public final class C0823eh {

                /* JADX INFO: renamed from: a */
                private static SchemeRegistry f780a;

                /* JADX INFO: renamed from: a */
/* 24 */        private static synchronized SchemeRegistry m841a() {
                    SchemeRegistry schemeRegistry;
                    try {
                        if (f780a != null) {
/* 26 */                    schemeRegistry = f780a;
                        } else {
/* 29 */                    f780a = new SchemeRegistry();
/* 30 */                    f780a.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
/* 33 */                    if (C0827el.m901d()) {
/* 34 */                        f780a.register(new Scheme("https", new C0821ef(), 443));
                            } else {
/* 36 */                        f780a.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                            }
/* 39 */                    schemeRegistry = f780a;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return schemeRegistry;
                }

                /* JADX INFO: renamed from: a */
/* 46 */        public static HttpClient m840a(HttpParams httpParams) {
/* 48 */            return new DefaultHttpClient(new SingleClientConnManager(httpParams, m841a()), httpParams);
                }
            }
