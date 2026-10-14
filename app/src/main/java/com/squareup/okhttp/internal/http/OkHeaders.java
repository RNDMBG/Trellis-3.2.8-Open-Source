            package com.squareup.okhttp.internal.http;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.squareup.okhttp.Authenticator;
            import com.squareup.okhttp.Challenge;
            import com.squareup.okhttp.Headers;
            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Response;
            import com.squareup.okhttp.internal.Platform;
            import com.squareup.okhttp.internal.Util;
            import java.io.IOException;
            import java.net.Proxy;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Comparator;
            import java.util.List;
            import java.util.Map;
            import java.util.Set;
            import java.util.TreeMap;
            import java.util.TreeSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public final class OkHeaders {
                private static final Comparator<String> FIELD_NAME_COMPARATOR = new C12781();
                static final String PREFIX = Platform.get().getPrefix();
                public static final String SENT_MILLIS = PREFIX + "-Sent-Millis";
                public static final String RECEIVED_MILLIS = PREFIX + "-Received-Millis";
                public static final String SELECTED_PROTOCOL = PREFIX + "-Selected-Protocol";

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.http.OkHeaders$1 */
/* 23 */        static class C12781 implements Comparator<String> {
/* 24 */            C12781() {
                    }


                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 27 */            public int compare(String a, String b) {
/* 28 */                if (a == b) {
/* 29 */                    return 0;
                        }
/* 30 */                if (a == null) {
/* 31 */                    return -1;
                        }
/* 32 */                if (b == null) {
/* 33 */                    return 1;
                        }
/* 35 */                return String.CASE_INSENSITIVE_ORDER.compare(a, b);
                    }
                }

/* 57 */        private OkHeaders() {
                }

/* 61 */        public static long contentLength(Request request) {
/* 62 */            return contentLength(request.headers());
                }

/* 65 */        public static long contentLength(Response response) {
/* 66 */            return contentLength(response.headers());
                }

/* 69 */        public static long contentLength(Headers headers) {
/* 70 */            return stringToLong(headers.get("Content-Length"));
                }

/* 73 */        private static long stringToLong(String s) {
/* 74 */            if (s == null) {
                        return -1L;
                    }
                    try {
/* 76 */                return Long.parseLong(s);
                    } catch (NumberFormatException e) {
                        return -1L;
                    }
                }

/* 88 */        public static Map<String, List<String>> toMultimap(Headers headers, String valueForNullKey) {
/* 89 */            Map<String, List<String>> result = new TreeMap<>(FIELD_NAME_COMPARATOR);
                    for (int i = 0; i < headers.size(); i++) {
/* 91 */                String fieldName = headers.name(i);
/* 92 */                String value = headers.value(i);
/* 94 */                List<String> allValues = new ArrayList<>();
/* 95 */                List<String> otherValues = result.get(fieldName);
/* 96 */                if (otherValues != null) {
/* 97 */                    allValues.addAll(otherValues);
                        }
/* 99 */                allValues.add(value);
/* 100 */               result.put(fieldName, Collections.unmodifiableList(allValues));
                    }
/* 102 */           if (valueForNullKey != null) {
/* 103 */               result.put(null, Collections.unmodifiableList(Collections.singletonList(valueForNullKey)));
                    }
/* 105 */           return Collections.unmodifiableMap(result);
                }

/* 108 */       public static void addCookies(Request.Builder builder, Map<String, List<String>> cookieHeaders) {
                    for (Map.Entry<String, List<String>> entry : cookieHeaders.entrySet()) {
/* 110 */               String key = entry.getKey();
/* 111 */               if ("Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key)) {
                            if (!entry.getValue().isEmpty()) {
/* 113 */                       builder.addHeader(key, buildCookieHeader(entry.getValue()));
                            }
                        }
                    }
                }

/* 122 */       private static String buildCookieHeader(List<String> cookies) {
/* 123 */           if (cookies.size() == 1) {
                        return cookies.get(0);
                    }
/* 124 */           StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < cookies.size(); i++) {
/* 126 */               if (i > 0) {
                            sb.append("; ");
                        }
/* 127 */               sb.append(cookies.get(i));
                    }
/* 129 */           return sb.toString();
                }

/* 137 */       public static boolean varyMatches(Response cachedResponse, Headers cachedRequest, Request newRequest) {
                    for (String field : varyFields(cachedResponse)) {
/* 139 */               if (!Util.equal(cachedRequest.values(field), newRequest.headers(field))) {
                            return false;
                        }
                    }
                    return true;
                }

/* 148 */       public static boolean hasVaryAll(Response response) {
/* 149 */           return varyFields(response).contains("*");
                }

/* 152 */       private static Set<String> varyFields(Response response) {
/* 153 */           Set<String> result = Collections.emptySet();
/* 154 */           Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
/* 156 */               if ("Vary".equalsIgnoreCase(headers.name(i))) {
/* 158 */                   String value = headers.value(i);
/* 159 */                   if (result.isEmpty()) {
/* 160 */                       result = new TreeSet<>((Comparator<? super String>) String.CASE_INSENSITIVE_ORDER);
                            }
/* 162 */                   String[] arr$ = value.split(",");
                            for (String varyField : arr$) {
/* 163 */                       result.add(varyField.trim());
                            }
                        }
                    }
/* 166 */           return result;
                }

/* 173 */       public static Headers varyHeaders(Response response) {
/* 174 */           Set<String> varyFields = varyFields(response);
/* 175 */           if (varyFields.isEmpty()) {
                        return new Headers.Builder().build();
                    }
/* 180 */           Headers requestHeaders = response.networkResponse().request().headers();
/* 182 */           Headers.Builder result = new Headers.Builder();
                    for (int i = 0; i < requestHeaders.size(); i++) {
/* 184 */               String fieldName = requestHeaders.name(i);
/* 185 */               if (varyFields.contains(fieldName)) {
/* 186 */                   result.add(fieldName, requestHeaders.value(i));
                        }
                    }
/* 189 */           return result.build();
                }

/* 196 */       static boolean isEndToEnd(String fieldName) {
                    return ("Connection".equalsIgnoreCase(fieldName) || "Keep-Alive".equalsIgnoreCase(fieldName) || "Proxy-Authenticate".equalsIgnoreCase(fieldName) || "Proxy-Authorization".equalsIgnoreCase(fieldName) || "TE".equalsIgnoreCase(fieldName) || "Trailers".equalsIgnoreCase(fieldName) || "Transfer-Encoding".equalsIgnoreCase(fieldName) || "Upgrade".equalsIgnoreCase(fieldName)) ? false : true;
                }

/* 216 */       public static List<Challenge> parseChallenges(Headers responseHeaders, String challengeHeader) {
/* 217 */           List<Challenge> result = new ArrayList<>();
                    for (int h = 0; h < responseHeaders.size(); h++) {
/* 219 */               if (challengeHeader.equalsIgnoreCase(responseHeaders.name(h))) {
/* 222 */                   String value = responseHeaders.value(h);
/* 223 */                   int pos = 0;
/* 224 */                   while (pos < value.length()) {
/* 225 */                       int tokenStart = pos;
/* 226 */                       int pos2 = HeaderParser.skipUntil(value, pos, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 228 */                       String scheme = value.substring(tokenStart, pos2).trim();
/* 229 */                       int pos3 = HeaderParser.skipWhitespace(value, pos2);
/* 235 */                       if (!value.regionMatches(true, pos3, "realm=\"", 0, "realm=\"".length())) {
                                    break;
                                }
/* 239 */                       int pos4 = pos3 + "realm=\"".length();
/* 241 */                       int pos5 = HeaderParser.skipUntil(value, pos4, "\"");
/* 242 */                       String realm = value.substring(pos4, pos5);
/* 246 */                       pos = HeaderParser.skipWhitespace(value, HeaderParser.skipUntil(value, pos5 + 1, ",") + 1);
/* 247 */                       result.add(new Challenge(scheme, realm));
                            }
                        }
                    }
/* 250 */           return result;
                }

/* 259 */       public static Request processAuthHeader(Authenticator authenticator, Response response, Proxy proxy) throws IOException {
                    return response.code() == 407 ? authenticator.authenticateProxy(proxy, response) : authenticator.authenticate(proxy, response);
                }
            }
