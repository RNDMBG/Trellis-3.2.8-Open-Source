            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.Protocol;
            import com.squareup.okhttp.Request;
            import java.net.Proxy;
            import java.net.URL;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public final class RequestLine {
/* 9 */         private RequestLine() {
                }

/* 18 */        static String get(Request request, Proxy.Type proxyType, Protocol protocol) {
/* 19 */            StringBuilder result = new StringBuilder();
/* 20 */            result.append(request.method());
/* 21 */            result.append(' ');
/* 23 */            if (includeAuthorityInRequestLine(request, proxyType)) {
/* 24 */                result.append(request.url());
                    } else {
/* 26 */                result.append(requestPath(request.url()));
                    }
/* 29 */            result.append(' ');
/* 30 */            result.append(version(protocol));
/* 31 */            return result.toString();
                }

/* 39 */        private static boolean includeAuthorityInRequestLine(Request request, Proxy.Type proxyType) {
                    return !request.isHttps() && proxyType == Proxy.Type.HTTP;
                }

/* 47 */        public static String requestPath(URL url) {
/* 48 */            String pathAndQuery = url.getFile();
/* 49 */            if (pathAndQuery == null) {
                        return "/";
                    }
                    return !pathAndQuery.startsWith("/") ? "/" + pathAndQuery : pathAndQuery;
                }

/* 54 */        public static String version(Protocol protocol) {
                    return protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
                }
            }
