            package com.squareup.okhttp.internal.http;

            import java.util.Arrays;
            import java.util.LinkedHashSet;
            import java.util.Set;
            
            
    public final class HttpMethod {
                public static final Set<String> METHODS = new LinkedHashSet(Arrays.asList("OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "PATCH"));

        public static boolean invalidatesCache(String method) {
                    return method.equals("POST") || method.equals("PATCH") || method.equals("PUT") || method.equals("DELETE");
                }

        public static boolean requiresRequestBody(String method) {
                    return method.equals("POST") || method.equals("PUT") || method.equals("PATCH");
                }

        public static boolean permitsRequestBody(String method) {
                    return requiresRequestBody(method) || method.equals("DELETE");
                }

        private HttpMethod() {
                }
            }
