            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Platform;
            import com.squareup.okhttp.internal.Util;
            import com.squareup.okhttp.internal.http.HttpMethod;
            import java.io.IOException;
            import java.net.MalformedURLException;
            import java.net.URI;
            import java.net.URISyntaxException;
            import java.net.URL;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public final class Request {
                private final RequestBody body;
                private volatile CacheControl cacheControl;
                private final Headers headers;
                private final String method;
                private final Object tag;
                private volatile URI uri;
                private volatile URL url;
                private final String urlString;

/* 31 */        /* synthetic */ Request(Builder x0, Object x1) {
/* 32 */            this(x0);
                }

/* 31 */        static /* synthetic */ RequestBody access$1000(Request x0) {
/* 32 */            return x0.body;
                }

/* 31 */        static /* synthetic */ Object access$1100(Request x0) {
/* 32 */            return x0.tag;
                }

/* 31 */        static /* synthetic */ Headers access$1200(Request x0) {
/* 32 */            return x0.headers;
                }

/* 31 */        static /* synthetic */ String access$700(Request x0) {
/* 32 */            return x0.urlString;
                }

/* 31 */        static /* synthetic */ URL access$800(Request x0) {
/* 32 */            return x0.url;
                }

/* 31 */        static /* synthetic */ String access$900(Request x0) {
/* 32 */            return x0.method;
                }

/* 42 */        private Request(Builder builder) {
/* 44 */            this.urlString = Builder.access$000(builder);
/* 45 */            this.method = Builder.access$100(builder);
/* 46 */            this.headers = Builder.access$200(builder).build();
/* 47 */            this.body = Builder.access$300(builder);
/* 48 */            this.tag = Builder.access$400(builder) != null ? Builder.access$400(builder) : this;
/* 49 */            this.url = Builder.access$500(builder);
                }

/* 53 */        public URL url() {
                    try {
/* 54 */                URL result = this.url;
/* 55 */                if (result != null) {
                            return result;
                        }
                        URL result2 = new URL(this.urlString);
                        this.url = result2;
                        return result2;
                    } catch (MalformedURLException e) {
/* 57 */                throw new RuntimeException("Malformed URL: " + this.urlString, e);
                    }
                }

/* 62 */        public URI uri() throws IOException {
                    try {
/* 63 */                URI result = this.uri;
/* 64 */                if (result != null) {
                            return result;
                        }
                        URI result2 = Platform.get().toUriLenient(url());
                        this.uri = result2;
                        return result2;
                    } catch (URISyntaxException e) {
/* 66 */                throw new IOException(e.getMessage());
                    }
                }

/* 70 */        public String urlString() {
/* 71 */            return this.urlString;
                }

/* 74 */        public String method() {
/* 75 */            return this.method;
                }

/* 78 */        public Headers headers() {
/* 79 */            return this.headers;
                }

/* 82 */        public String header(String name) {
/* 83 */            return this.headers.get(name);
                }

/* 86 */        public List<String> headers(String name) {
/* 87 */            return this.headers.values(name);
                }

/* 90 */        public RequestBody body() {
/* 91 */            return this.body;
                }

/* 94 */        public Object tag() {
/* 95 */            return this.tag;
                }

/* 98 */        public Builder newBuilder() {
/* 99 */            return new Builder(this, null);
                }

/* 106 */       public CacheControl cacheControl() {
/* 107 */           CacheControl result = this.cacheControl;
/* 108 */           if (result != null) {
                        return result;
                    }
                    CacheControl result2 = CacheControl.parse(this.headers);
                    this.cacheControl = result2;
                    return result2;
                }

/* 111 */       public boolean isHttps() {
/* 112 */           return url().getProtocol().equals("https");
                }

/* 115 */       public String toString() {
                    return "Request{method=" + this.method + ", url=" + this.urlString + ", tag=" + (this.tag != this ? this.tag : null) + '}';
                }

/* 123 */       public static class Builder {
                    private RequestBody body;
                    private Headers.Builder headers;
                    private String method;
                    private Object tag;
                    private URL url;
                    private String urlString;

/* 124 */           /* synthetic */ Builder(Request x0, Object x1) {
/* 125 */               this(x0);
                    }

/* 124 */           static /* synthetic */ String access$000(Builder x0) {
/* 125 */               return x0.urlString;
                    }

/* 124 */           static /* synthetic */ String access$100(Builder x0) {
/* 125 */               return x0.method;
                    }

/* 124 */           static /* synthetic */ Headers.Builder access$200(Builder x0) {
/* 125 */               return x0.headers;
                    }

/* 124 */           static /* synthetic */ RequestBody access$300(Builder x0) {
/* 125 */               return x0.body;
                    }

/* 124 */           static /* synthetic */ Object access$400(Builder x0) {
/* 125 */               return x0.tag;
                    }

/* 124 */           static /* synthetic */ URL access$500(Builder x0) {
/* 125 */               return x0.url;
                    }

/* 132 */           public Builder() {
/* 134 */               this.method = "GET";
/* 135 */               this.headers = new Headers.Builder();
                    }

/* 137 */           private Builder(Request request) {
/* 139 */               this.urlString = Request.access$700(request);
/* 140 */               this.url = Request.access$800(request);
/* 141 */               this.method = Request.access$900(request);
/* 142 */               this.body = Request.access$1000(request);
/* 143 */               this.tag = Request.access$1100(request);
/* 144 */               this.headers = Request.access$1200(request).newBuilder();
                    }

/* 147 */           public Builder url(String url) {
/* 148 */               if (url == null) {
                            throw new IllegalArgumentException("url == null");
                        }
/* 149 */               this.urlString = url;
/* 150 */               return this;
                    }

/* 153 */           public Builder url(URL url) {
/* 154 */               if (url == null) {
                            throw new IllegalArgumentException("url == null");
                        }
/* 155 */               this.url = url;
/* 156 */               this.urlString = url.toString();
/* 157 */               return this;
                    }

/* 164 */           public Builder header(String name, String value) {
/* 165 */               this.headers.set(name, value);
/* 166 */               return this;
                    }

/* 173 */           public Builder addHeader(String name, String value) {
/* 174 */               this.headers.add(name, value);
/* 175 */               return this;
                    }

/* 178 */           public Builder removeHeader(String name) {
/* 179 */               this.headers.removeAll(name);
/* 180 */               return this;
                    }

/* 184 */           public Builder headers(Headers headers) {
/* 185 */               this.headers = headers.newBuilder();
/* 186 */               return this;
                    }

/* 194 */           public Builder cacheControl(CacheControl cacheControl) {
/* 195 */               String value = cacheControl.toString();
                        return value.isEmpty() ? removeHeader("Cache-Control") : header("Cache-Control", value);
                    }

/* 200 */           public Builder get() {
/* 201 */               return method("GET", null);
                    }

/* 204 */           public Builder head() {
/* 205 */               return method("HEAD", null);
                    }

/* 208 */           public Builder post(RequestBody body) {
/* 209 */               return method("POST", body);
                    }

/* 212 */           public Builder delete() {
/* 213 */               return method("DELETE", null);
                    }

/* 216 */           public Builder put(RequestBody body) {
/* 217 */               return method("PUT", body);
                    }

/* 220 */           public Builder patch(RequestBody body) {
/* 221 */               return method("PATCH", body);
                    }

/* 224 */           public Builder method(String method, RequestBody body) {
/* 225 */               if (method == null || method.length() == 0) {
/* 226 */                   throw new IllegalArgumentException("method == null || method.length() == 0");
                        }
/* 228 */               if (body != null && !HttpMethod.permitsRequestBody(method)) {
/* 229 */                   throw new IllegalArgumentException("method " + method + " must not have a request body.");
                        }
/* 231 */               if (body == null && HttpMethod.permitsRequestBody(method)) {
/* 232 */                   body = RequestBody.create((MediaType) null, Util.EMPTY_BYTE_ARRAY);
                        }
/* 234 */               this.method = method;
/* 235 */               this.body = body;
/* 236 */               return this;
                    }

/* 244 */           public Builder tag(Object tag) {
/* 245 */               this.tag = tag;
/* 246 */               return this;
                    }

/* 249 */           public Request build() {
/* 250 */               if (this.urlString == null) {
                            throw new IllegalStateException("url == null");
                        }
/* 251 */               return new Request(this, null);
                    }
                }
            }
