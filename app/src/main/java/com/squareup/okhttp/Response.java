            package com.squareup.okhttp;

            import com.googlecode.javacv.cpp.avutil;
            import com.squareup.okhttp.internal.http.OkHeaders;
            import java.util.Collections;
            import java.util.List;
            
            
    public final class Response {
                private final ResponseBody body;
                private volatile CacheControl cacheControl;
                private Response cacheResponse;
                private final int code;
                private final Handshake handshake;
                private final Headers headers;
                private final String message;
                private Response networkResponse;
                private final Response priorResponse;
                private final Protocol protocol;
                private final Request request;

         Response(Builder x0, Object x1) {
            this(x0);
                }

        static  Request access$1100(Response x0) {
            return x0.request;
                }

        static  Protocol access$1200(Response x0) {
            return x0.protocol;
                }

        static  int access$1300(Response x0) {
            return x0.code;
                }

        static  String access$1400(Response x0) {
            return x0.message;
                }

        static  Handshake access$1500(Response x0) {
            return x0.handshake;
                }

        static  Headers access$1600(Response x0) {
            return x0.headers;
                }

        static  ResponseBody access$1700(Response x0) {
            return x0.body;
                }

        static  Response access$1800(Response x0) {
            return x0.networkResponse;
                }

        static  Response access$1900(Response x0) {
            return x0.cacheResponse;
                }

        static  Response access$2000(Response x0) {
            return x0.priorResponse;
                }

        private Response(Builder builder) {
            this.request = Builder.access$000(builder);
            this.protocol = Builder.access$100(builder);
            this.code = Builder.access$200(builder);
            this.message = Builder.access$300(builder);
            this.handshake = Builder.access$400(builder);
            this.headers = Builder.access$500(builder).build();
            this.body = Builder.access$600(builder);
            this.networkResponse = Builder.access$700(builder);
            this.cacheResponse = Builder.access$800(builder);
            this.priorResponse = Builder.access$900(builder);
                }

        public Request request() {
            return this.request;
                }

        public Protocol protocol() {
            return this.protocol;
                }

        public int code() {
            return this.code;
                }

        public boolean isSuccessful() {
                    return this.code >= 200 && this.code < 300;
                }

       public String message() {
           return this.message;
                }

       public Handshake handshake() {
           return this.handshake;
                }

       public List<String> headers(String name) {
           return this.headers.values(name);
                }

       public String header(String name) {
           return header(name, null);
                }

       public String header(String name, String defaultValue) {
           String result = this.headers.get(name);
                    return result != null ? result : defaultValue;
                }

       public Headers headers() {
           return this.headers;
                }

       public ResponseBody body() {
           return this.body;
                }

       public Builder newBuilder() {
           return new Builder(this, null);
                }

       public boolean isRedirect() {
                    switch (this.code) {
                        case 300:
                        case avutil.AV_PIX_FMT_YUV420P12BE :
                        case 302:
                        case avutil.AV_PIX_FMT_YUV420P14BE :
                        case 307:
                        case 308:
                   return true;
                        case 304:
                        case avutil.AV_PIX_FMT_YUV422P12BE :
                        case avutil.AV_PIX_FMT_YUV422P12LE :
                        default:
                   return false;
                    }
                }

       public Response networkResponse() {
           return this.networkResponse;
                }

       public Response cacheResponse() {
           return this.cacheResponse;
                }

       public Response priorResponse() {
           return this.priorResponse;
                }

       public List<Challenge> challenges() {
                    String responseField;
           if (this.code == 401) {
               responseField = "WWW-Authenticate";
                    } else if (this.code == 407) {
               responseField = "Proxy-Authenticate";
                    } else {
               return Collections.emptyList();
                    }
           return OkHeaders.parseChallenges(headers(), responseField);
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

       public String toString() {
           return "Response{protocol=" + this.protocol + ", code=" + this.code + ", message=" + this.message + ", url=" + this.request.urlString() + '}';
                }

       public static class Builder {
                    private ResponseBody body;
                    private Response cacheResponse;
                    private int code;
                    private Handshake handshake;
                    private Headers.Builder headers;
                    private String message;
                    private Response networkResponse;
                    private Response priorResponse;
                    private Protocol protocol;
                    private Request request;

            Builder(Response x0, Object x1) {
               this(x0);
                    }

           static  Request access$000(Builder x0) {
               return x0.request;
                    }

           static  Protocol access$100(Builder x0) {
               return x0.protocol;
                    }

           static  int access$200(Builder x0) {
               return x0.code;
                    }

           static  String access$300(Builder x0) {
               return x0.message;
                    }

           static  Handshake access$400(Builder x0) {
               return x0.handshake;
                    }

           static  Headers.Builder access$500(Builder x0) {
               return x0.headers;
                    }

           static  ResponseBody access$600(Builder x0) {
               return x0.body;
                    }

           static  Response access$700(Builder x0) {
               return x0.networkResponse;
                    }

           static  Response access$800(Builder x0) {
               return x0.cacheResponse;
                    }

           static  Response access$900(Builder x0) {
               return x0.priorResponse;
                    }

           public Builder() {
               this.code = -1;
               this.headers = new Headers.Builder();
                    }

           private Builder(Response response) {
               this.code = -1;
               this.request = Response.access$1100(response);
               this.protocol = Response.access$1200(response);
               this.code = Response.access$1300(response);
               this.message = Response.access$1400(response);
               this.handshake = Response.access$1500(response);
               this.headers = Response.access$1600(response).newBuilder();
               this.body = Response.access$1700(response);
               this.networkResponse = Response.access$1800(response);
               this.cacheResponse = Response.access$1900(response);
               this.priorResponse = Response.access$2000(response);
                    }

           public Builder request(Request request) {
               this.request = request;
               return this;
                    }

           public Builder protocol(Protocol protocol) {
               this.protocol = protocol;
               return this;
                    }

           public Builder code(int code) {
               this.code = code;
               return this;
                    }

           public Builder message(String message) {
               this.message = message;
               return this;
                    }

           public Builder handshake(Handshake handshake) {
               this.handshake = handshake;
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

           public Builder body(ResponseBody body) {
               this.body = body;
               return this;
                    }

           public Builder networkResponse(Response networkResponse) {
               if (networkResponse != null) {
                            checkSupportResponse("networkResponse", networkResponse);
                        }
               this.networkResponse = networkResponse;
               return this;
                    }

           public Builder cacheResponse(Response cacheResponse) {
               if (cacheResponse != null) {
                            checkSupportResponse("cacheResponse", cacheResponse);
                        }
               this.cacheResponse = cacheResponse;
               return this;
                    }

           private void checkSupportResponse(String name, Response response) {
               if (Response.access$1700(response) != null) {
                   throw new IllegalArgumentException(name + ".body != null");
                        }
               if (Response.access$1800(response) != null) {
                   throw new IllegalArgumentException(name + ".networkResponse != null");
                        }
               if (Response.access$1900(response) != null) {
                   throw new IllegalArgumentException(name + ".cacheResponse != null");
                        }
               if (Response.access$2000(response) != null) {
                   throw new IllegalArgumentException(name + ".priorResponse != null");
                        }
                    }

           public Builder priorResponse(Response priorResponse) {
               if (priorResponse != null) {
                            checkPriorResponse(priorResponse);
                        }
               this.priorResponse = priorResponse;
               return this;
                    }

           private void checkPriorResponse(Response response) {
               if (Response.access$1700(response) != null) {
                   throw new IllegalArgumentException("priorResponse.body != null");
                        }
                    }

           public Response build() {
               if (this.request == null) {
                            throw new IllegalStateException("request == null");
                        }
               if (this.protocol == null) {
                            throw new IllegalStateException("protocol == null");
                        }
               if (this.code < 0) {
                            throw new IllegalStateException("code < 0: " + this.code);
                        }
               return new Response(this, null);
                    }
                }
            }
