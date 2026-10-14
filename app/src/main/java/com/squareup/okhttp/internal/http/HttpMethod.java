            package com.squareup.okhttp.internal.http;

            import java.util.Arrays;
            import java.util.LinkedHashSet;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public final class HttpMethod {
                public static final Set<String> METHODS = new LinkedHashSet(Arrays.asList("OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "PATCH"));

/* 26 */        public static boolean invalidatesCache(String method) {
                    return method.equals("POST") || method.equals("PATCH") || method.equals("PUT") || method.equals("DELETE");
                }

/* 33 */        public static boolean requiresRequestBody(String method) {
                    return method.equals("POST") || method.equals("PUT") || method.equals("PATCH");
                }

/* 39 */        public static boolean permitsRequestBody(String method) {
                    return requiresRequestBody(method) || method.equals("DELETE");
                }

/* 43 */        private HttpMethod() {
                }
            }
