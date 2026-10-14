            package com.flurry.sdk;

            import org.apache.http.client.HttpClient;
            import org.apache.http.conn.scheme.PlainSocketFactory;
            import org.apache.http.conn.scheme.Scheme;
            import org.apache.http.conn.scheme.SchemeRegistry;
            import org.apache.http.conn.ssl.SSLSocketFactory;
            import org.apache.http.impl.client.DefaultHttpClient;
            import org.apache.http.impl.conn.SingleClientConnManager;
            import org.apache.http.params.HttpParams;
            
            
            
    public final class C0823eh {

                
                private static SchemeRegistry f780a;

                
        private static synchronized SchemeRegistry m841a() {
                    SchemeRegistry schemeRegistry;
                    try {
                        if (f780a != null) {
                    schemeRegistry = f780a;
                        } else {
                    f780a = new SchemeRegistry();
                    f780a.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                    if (C0827el.m901d()) {
                        f780a.register(new Scheme("https", new C0821ef(), 443));
                            } else {
                        f780a.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                            }
                    schemeRegistry = f780a;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return schemeRegistry;
                }

                
        public static HttpClient m840a(HttpParams httpParams) {
            return new DefaultHttpClient(new SingleClientConnManager(httpParams, m841a()), httpParams);
                }
            }
