            package com.squareup.okhttp;

            import com.googlecode.javacv.cpp.avutil;
            import com.squareup.okhttp.internal.http.OkHeaders;
            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public final class Response {
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

/* 35 */        /* synthetic */ Response(Builder x0, Object x1) {
/* 36 */            this(x0);
                }

/* 35 */        static /* synthetic */ Request access$1100(Response x0) {
/* 36 */            return x0.request;
                }

/* 35 */        static /* synthetic */ Protocol access$1200(Response x0) {
/* 36 */            return x0.protocol;
                }

/* 35 */        static /* synthetic */ int access$1300(Response x0) {
/* 36 */            return x0.code;
                }

/* 35 */        static /* synthetic */ String access$1400(Response x0) {
/* 36 */            return x0.message;
                }

/* 35 */        static /* synthetic */ Handshake access$1500(Response x0) {
/* 36 */            return x0.handshake;
                }

/* 35 */        static /* synthetic */ Headers access$1600(Response x0) {
/* 36 */            return x0.headers;
                }

/* 35 */        static /* synthetic */ ResponseBody access$1700(Response x0) {
/* 36 */            return x0.body;
                }

/* 35 */        static /* synthetic */ Response access$1800(Response x0) {
/* 36 */            return x0.networkResponse;
                }

/* 35 */        static /* synthetic */ Response access$1900(Response x0) {
/* 36 */            return x0.cacheResponse;
                }

/* 35 */        static /* synthetic */ Response access$2000(Response x0) {
/* 36 */            return x0.priorResponse;
                }

/* 49 */        private Response(Builder builder) {
/* 51 */            this.request = Builder.access$000(builder);
/* 52 */            this.protocol = Builder.access$100(builder);
/* 53 */            this.code = Builder.access$200(builder);
/* 54 */            this.message = Builder.access$300(builder);
/* 55 */            this.handshake = Builder.access$400(builder);
/* 56 */            this.headers = Builder.access$500(builder).build();
/* 57 */            this.body = Builder.access$600(builder);
/* 58 */            this.networkResponse = Builder.access$700(builder);
/* 59 */            this.cacheResponse = Builder.access$800(builder);
/* 60 */            this.priorResponse = Builder.access$900(builder);
                }

/* 74 */        public Request request() {
/* 75 */            return this.request;
                }

/* 82 */        public Protocol protocol() {
/* 83 */            return this.protocol;
                }

/* 87 */        public int code() {
/* 88 */            return this.code;
                }

/* 95 */        public boolean isSuccessful() {
                    return this.code >= 200 && this.code < 300;
                }

/* 100 */       public String message() {
/* 101 */           return this.message;
                }

/* 108 */       public Handshake handshake() {
/* 109 */           return this.handshake;
                }

/* 112 */       public List<String> headers(String name) {
/* 113 */           return this.headers.values(name);
                }

/* 116 */       public String header(String name) {
/* 117 */           return header(name, null);
                }

/* 120 */       public String header(String name, String defaultValue) {
/* 121 */           String result = this.headers.get(name);
                    return result != null ? result : defaultValue;
                }

/* 125 */       public Headers headers() {
/* 126 */           return this.headers;
                }

/* 129 */       public ResponseBody body() {
/* 130 */           return this.body;
                }

/* 133 */       public Builder newBuilder() {
/* 134 */           return new Builder(this, null);
                }

/* 138 */       public boolean isRedirect() {
                    switch (this.code) {
                        case 300:
                        case avutil.AV_PIX_FMT_YUV420P12BE /* 301 */:
                        case 302:
                        case avutil.AV_PIX_FMT_YUV420P14BE /* 303 */:
                        case 307:
                        case 308:
/* 146 */                   return true;
                        case 304:
                        case avutil.AV_PIX_FMT_YUV422P12BE /* 305 */:
                        case avutil.AV_PIX_FMT_YUV422P12LE /* 306 */:
                        default:
/* 148 */                   return false;
                    }
                }

/* 157 */       public Response networkResponse() {
/* 158 */           return this.networkResponse;
                }

/* 167 */       public Response cacheResponse() {
/* 168 */           return this.cacheResponse;
                }

/* 177 */       public Response priorResponse() {
/* 178 */           return this.priorResponse;
                }

/* 189 */       public List<Challenge> challenges() {
                    String responseField;
/* 190 */           if (this.code == 401) {
/* 191 */               responseField = "WWW-Authenticate";
                    } else if (this.code == 407) {
/* 193 */               responseField = "Proxy-Authenticate";
                    } else {
/* 195 */               return Collections.emptyList();
                    }
/* 197 */           return OkHeaders.parseChallenges(headers(), responseField);
                }

/* 204 */       public CacheControl cacheControl() {
/* 205 */           CacheControl result = this.cacheControl;
/* 206 */           if (result != null) {
                        return result;
                    }
                    CacheControl result2 = CacheControl.parse(this.headers);
                    this.cacheControl = result2;
                    return result2;
                }

/* 209 */       public String toString() {
/* 210 */           return "Response{protocol=" + this.protocol + ", code=" + this.code + ", message=" + this.message + ", url=" + this.request.urlString() + '}';
                }

/* 219 */       public static class Builder {
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

/* 220 */           /* synthetic */ Builder(Response x0, Object x1) {
/* 221 */               this(x0);
                    }

/* 220 */           static /* synthetic */ Request access$000(Builder x0) {
/* 221 */               return x0.request;
                    }

/* 220 */           static /* synthetic */ Protocol access$100(Builder x0) {
/* 221 */               return x0.protocol;
                    }

/* 220 */           static /* synthetic */ int access$200(Builder x0) {
/* 221 */               return x0.code;
                    }

/* 220 */           static /* synthetic */ String access$300(Builder x0) {
/* 221 */               return x0.message;
                    }

/* 220 */           static /* synthetic */ Handshake access$400(Builder x0) {
/* 221 */               return x0.handshake;
                    }

/* 220 */           static /* synthetic */ Headers.Builder access$500(Builder x0) {
/* 221 */               return x0.headers;
                    }

/* 220 */           static /* synthetic */ ResponseBody access$600(Builder x0) {
/* 221 */               return x0.body;
                    }

/* 220 */           static /* synthetic */ Response access$700(Builder x0) {
/* 221 */               return x0.networkResponse;
                    }

/* 220 */           static /* synthetic */ Response access$800(Builder x0) {
/* 221 */               return x0.cacheResponse;
                    }

/* 220 */           static /* synthetic */ Response access$900(Builder x0) {
/* 221 */               return x0.priorResponse;
                    }

/* 232 */           public Builder() {
/* 224 */               this.code = -1;
/* 234 */               this.headers = new Headers.Builder();
                    }

/* 236 */           private Builder(Response response) {
/* 224 */               this.code = -1;
/* 238 */               this.request = Response.access$1100(response);
/* 239 */               this.protocol = Response.access$1200(response);
/* 240 */               this.code = Response.access$1300(response);
/* 241 */               this.message = Response.access$1400(response);
/* 242 */               this.handshake = Response.access$1500(response);
/* 243 */               this.headers = Response.access$1600(response).newBuilder();
/* 244 */               this.body = Response.access$1700(response);
/* 245 */               this.networkResponse = Response.access$1800(response);
/* 246 */               this.cacheResponse = Response.access$1900(response);
/* 247 */               this.priorResponse = Response.access$2000(response);
                    }

/* 250 */           public Builder request(Request request) {
/* 251 */               this.request = request;
/* 252 */               return this;
                    }

/* 255 */           public Builder protocol(Protocol protocol) {
/* 256 */               this.protocol = protocol;
/* 257 */               return this;
                    }

/* 260 */           public Builder code(int code) {
/* 261 */               this.code = code;
/* 262 */               return this;
                    }

/* 265 */           public Builder message(String message) {
/* 266 */               this.message = message;
/* 267 */               return this;
                    }

/* 270 */           public Builder handshake(Handshake handshake) {
/* 271 */               this.handshake = handshake;
/* 272 */               return this;
                    }

/* 279 */           public Builder header(String name, String value) {
/* 280 */               this.headers.set(name, value);
/* 281 */               return this;
                    }

/* 288 */           public Builder addHeader(String name, String value) {
/* 289 */               this.headers.add(name, value);
/* 290 */               return this;
                    }

/* 293 */           public Builder removeHeader(String name) {
/* 294 */               this.headers.removeAll(name);
/* 295 */               return this;
                    }

/* 299 */           public Builder headers(Headers headers) {
/* 300 */               this.headers = headers.newBuilder();
/* 301 */               return this;
                    }

/* 304 */           public Builder body(ResponseBody body) {
/* 305 */               this.body = body;
/* 306 */               return this;
                    }

/* 309 */           public Builder networkResponse(Response networkResponse) {
/* 310 */               if (networkResponse != null) {
                            checkSupportResponse("networkResponse", networkResponse);
                        }
/* 311 */               this.networkResponse = networkResponse;
/* 312 */               return this;
                    }

/* 315 */           public Builder cacheResponse(Response cacheResponse) {
/* 316 */               if (cacheResponse != null) {
                            checkSupportResponse("cacheResponse", cacheResponse);
                        }
/* 317 */               this.cacheResponse = cacheResponse;
/* 318 */               return this;
                    }

/* 321 */           private void checkSupportResponse(String name, Response response) {
/* 322 */               if (Response.access$1700(response) != null) {
/* 323 */                   throw new IllegalArgumentException(name + ".body != null");
                        }
/* 324 */               if (Response.access$1800(response) != null) {
/* 325 */                   throw new IllegalArgumentException(name + ".networkResponse != null");
                        }
/* 326 */               if (Response.access$1900(response) != null) {
/* 327 */                   throw new IllegalArgumentException(name + ".cacheResponse != null");
                        }
/* 328 */               if (Response.access$2000(response) != null) {
/* 329 */                   throw new IllegalArgumentException(name + ".priorResponse != null");
                        }
                    }

/* 333 */           public Builder priorResponse(Response priorResponse) {
/* 334 */               if (priorResponse != null) {
                            checkPriorResponse(priorResponse);
                        }
/* 335 */               this.priorResponse = priorResponse;
/* 336 */               return this;
                    }

/* 339 */           private void checkPriorResponse(Response response) {
/* 340 */               if (Response.access$1700(response) != null) {
/* 341 */                   throw new IllegalArgumentException("priorResponse.body != null");
                        }
                    }

/* 345 */           public Response build() {
/* 346 */               if (this.request == null) {
                            throw new IllegalStateException("request == null");
                        }
/* 347 */               if (this.protocol == null) {
                            throw new IllegalStateException("protocol == null");
                        }
/* 348 */               if (this.code < 0) {
                            throw new IllegalStateException("code < 0: " + this.code);
                        }
/* 349 */               return new Response(this, null);
                    }
                }
            }
