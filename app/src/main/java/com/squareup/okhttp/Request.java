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
            
            
    public final class Request {
                private final RequestBody body;
                private volatile CacheControl cacheControl;
                private final Headers headers;
                private final String method;
                private final Object tag;
                private volatile URI uri;
                private volatile URL url;
                private final String urlString;

         Request(Builder x0, Object x1) {
            this(x0);
                }

        static  RequestBody access$1000(Request x0) {
            return x0.body;
                }

        static  Object access$1100(Request x0) {
            return x0.tag;
                }

        static  Headers access$1200(Request x0) {
            return x0.headers;
                }

        static  String access$700(Request x0) {
            return x0.urlString;
                }

        static  URL access$800(Request x0) {
            return x0.url;
                }

        static  String access$900(Request x0) {
            return x0.method;
                }

        private Request(Builder builder) {
            this.urlString = Builder.access$000(builder);
            this.method = Builder.access$100(builder);
            this.headers = Builder.access$200(builder).build();
            this.body = Builder.access$300(builder);
            this.tag = Builder.access$400(builder) != null ? Builder.access$400(builder) : this;
            this.url = Builder.access$500(builder);
                }

        public URL url() {
                    try {
                URL result = this.url;
                if (result != null) {
                            return result;
                        }
                        URL result2 = new URL(this.urlString);
                        this.url = result2;
                        return result2;
                    } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed URL: " + this.urlString, e);
                    }
                }

        public URI uri() throws IOException {
                    try {
                URI result = this.uri;
                if (result != null) {
                            return result;
                        }
                        URI result2 = Platform.get().toUriLenient(url());
                        this.uri = result2;
                        return result2;
                    } catch (URISyntaxException e) {
                throw new IOException(e.getMessage());
                    }
                }

        public String urlString() {
            return this.urlString;
                }

        public String method() {
            return this.method;
                }

        public Headers headers() {
            return this.headers;
                }

        public String header(String name) {
            return this.headers.get(name);
                }

        public List<String> headers(String name) {
            return this.headers.values(name);
                }

        public RequestBody body() {
            return this.body;
                }

        public Object tag() {
            return this.tag;
                }

        public Builder newBuilder() {
            return new Builder(this, null);
                }

       public CacheControl cacheControl() {
           CacheControl result = this.cacheControl;
           if (result != null) {
                        return result;
                    }
                    CacheControl result2 = CacheControl.parse(this.headers);
                    this.cacheControl = result2;
                    return result2;
                }

       public boolean isHttps() {
           return url().getProtocol().equals("https");
                }

       public String toString() {
                    return "Request{method=" + this.method + ", url=" + this.urlString + ", tag=" + (this.tag != this ? this.tag : null) + '}';
                }

       public static class Builder {
                    private RequestBody body;
                    private Headers.Builder headers;
                    private String method;
                    private Object tag;
                    private URL url;
                    private String urlString;

            Builder(Request x0, Object x1) {
               this(x0);
                    }

           static  String access$000(Builder x0) {
               return x0.urlString;
                    }

           static  String access$100(Builder x0) {
               return x0.method;
                    }

           static  Headers.Builder access$200(Builder x0) {
               return x0.headers;
                    }

           static  RequestBody access$300(Builder x0) {
               return x0.body;
                    }

           static  Object access$400(Builder x0) {
               return x0.tag;
                    }

           static  URL access$500(Builder x0) {
               return x0.url;
                    }

           public Builder() {
               this.method = "GET";
               this.headers = new Headers.Builder();
                    }

           private Builder(Request request) {
               this.urlString = Request.access$700(request);
               this.url = Request.access$800(request);
               this.method = Request.access$900(request);
               this.body = Request.access$1000(request);
               this.tag = Request.access$1100(request);
               this.headers = Request.access$1200(request).newBuilder();
                    }

           public Builder url(String url) {
               if (url == null) {
                            throw new IllegalArgumentException("url == null");
                        }
               this.urlString = url;
               return this;
                    }

           public Builder url(URL url) {
               if (url == null) {
                            throw new IllegalArgumentException("url == null");
                        }
               this.url = url;
               this.urlString = url.toString();
               return this;
                    }

           public Builder header(String name, String value) {
               this.headers.set(name, value);
               return this;
                    }

           public Builder addHeader(String name, String value) {
               this.headers.add(name, value);
               return this;
                    }

           public Builder removeHeader(String name) {
               this.headers.removeAll(name);
               return this;
                    }

           public Builder headers(Headers headers) {
               this.headers = headers.newBuilder();
               return this;
                    }

           public Builder cacheControl(CacheControl cacheControl) {
               String value = cacheControl.toString();
                        return value.isEmpty() ? removeHeader("Cache-Control") : header("Cache-Control", value);
                    }

           public Builder get() {
               return method("GET", null);
                    }

           public Builder head() {
               return method("HEAD", null);
                    }

           public Builder post(RequestBody body) {
               return method("POST", body);
                    }

           public Builder delete() {
               return method("DELETE", null);
                    }

           public Builder put(RequestBody body) {
               return method("PUT", body);
                    }

           public Builder patch(RequestBody body) {
               return method("PATCH", body);
                    }

           public Builder method(String method, RequestBody body) {
               if (method == null || method.length() == 0) {
                   throw new IllegalArgumentException("method == null || method.length() == 0");
                        }
               if (body != null && !HttpMethod.permitsRequestBody(method)) {
                   throw new IllegalArgumentException("method " + method + " must not have a request body.");
                        }
               if (body == null && HttpMethod.permitsRequestBody(method)) {
                   body = RequestBody.create((MediaType) null, Util.EMPTY_BYTE_ARRAY);
                        }
               this.method = method;
               this.body = body;
               return this;
                    }

           public Builder tag(Object tag) {
               this.tag = tag;
               return this;
                    }

           public Request build() {
               if (this.urlString == null) {
                            throw new IllegalStateException("url == null");
                        }
               return new Request(this, null);
                    }
                }
            }
