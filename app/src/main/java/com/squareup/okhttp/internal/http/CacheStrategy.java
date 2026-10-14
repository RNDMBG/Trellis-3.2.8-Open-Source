            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.CacheControl;
            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Response;
            import java.util.Date;
            import java.util.concurrent.TimeUnit;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public final class CacheStrategy {
                public final Response cacheResponse;
                public final Request networkRequest;

/* 23 */        /* synthetic */ CacheStrategy(Request x0, Response x1, Object x2) {
/* 24 */            this(x0, x1);
                }

/* 30 */        private CacheStrategy(Request networkRequest, Response cacheResponse) {
/* 32 */            this.networkRequest = networkRequest;
/* 33 */            this.cacheResponse = cacheResponse;
                }

/* 42 */        public static boolean isCacheable(Response response, Request request) {
/* 43 */            int responseCode = response.code();
/* 44 */            if (responseCode != 200 && responseCode != 203 && responseCode != 300 && responseCode != 301 && responseCode != 410 && responseCode != 308) {
                        return false;
                    }
/* 54 */            CacheControl responseCaching = response.cacheControl();
/* 55 */            CacheControl requestCaching = request.cacheControl();
                    return (responseCaching.noStore() || requestCaching.noStore()) ? false : true;
                }

/* 98 */        public static class Factory {
                    private int ageSeconds;
                    final Response cacheResponse;
                    private String etag;
                    private Date expires;
                    private Date lastModified;
                    private String lastModifiedString;
                    final long nowMillis;
                    private long receivedResponseMillis;
                    final Request request;
                    private long sentRequestMillis;
                    private Date servedDate;
                    private String servedDateString;

/* 99 */            public Factory(long nowMillis, Request request, Response cacheResponse) {
/* 98 */                this.ageSeconds = -1;
/* 101 */               this.nowMillis = nowMillis;
/* 102 */               this.request = request;
/* 103 */               this.cacheResponse = cacheResponse;
/* 105 */               if (cacheResponse != null) {
                            for (int i = 0; i < cacheResponse.headers().size(); i++) {
/* 107 */                       String fieldName = cacheResponse.headers().name(i);
/* 108 */                       String value = cacheResponse.headers().value(i);
/* 109 */                       if ("Date".equalsIgnoreCase(fieldName)) {
/* 110 */                           this.servedDate = HttpDate.parse(value);
/* 111 */                           this.servedDateString = value;
                                } else if ("Expires".equalsIgnoreCase(fieldName)) {
/* 113 */                           this.expires = HttpDate.parse(value);
                                } else if ("Last-Modified".equalsIgnoreCase(fieldName)) {
/* 115 */                           this.lastModified = HttpDate.parse(value);
/* 116 */                           this.lastModifiedString = value;
                                } else if ("ETag".equalsIgnoreCase(fieldName)) {
/* 118 */                           this.etag = value;
                                } else if ("Age".equalsIgnoreCase(fieldName)) {
/* 120 */                           this.ageSeconds = HeaderParser.parseSeconds(value, -1);
                                } else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(fieldName)) {
/* 122 */                           this.sentRequestMillis = Long.parseLong(value);
                                } else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(fieldName)) {
/* 124 */                           this.receivedResponseMillis = Long.parseLong(value);
                                }
                            }
                        }
                    }

/* 134 */           public CacheStrategy get() {
/* 135 */               CacheStrategy candidate = getCandidate();
/* 137 */               if (candidate.networkRequest != null && this.request.cacheControl().onlyIfCached()) {
/* 139 */                   return new CacheStrategy(null, null, null);
                        }
/* 142 */               return candidate;
                    }

/* 147 */           private CacheStrategy getCandidate() {
/* 148 */               if (this.cacheResponse == null) {
/* 149 */                   return new CacheStrategy(this.request, null, null);
                        }
/* 153 */               if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
/* 154 */                   return new CacheStrategy(this.request, null, null);
                        }
/* 160 */               if (!CacheStrategy.isCacheable(this.cacheResponse, this.request)) {
/* 161 */                   return new CacheStrategy(this.request, null, null);
                        }
/* 164 */               CacheControl requestCaching = this.request.cacheControl();
/* 165 */               if (requestCaching.noCache() || hasConditions(this.request)) {
/* 166 */                   return new CacheStrategy(this.request, null, null);
                        }
/* 169 */               long ageMillis = cacheResponseAge();
/* 170 */               long freshMillis = computeFreshnessLifetime();
/* 172 */               if (requestCaching.maxAgeSeconds() != -1) {
/* 173 */                   freshMillis = Math.min(freshMillis, TimeUnit.SECONDS.toMillis(requestCaching.maxAgeSeconds()));
                        }
/* 176 */               long minFreshMillis = 0;
/* 177 */               if (requestCaching.minFreshSeconds() != -1) {
/* 178 */                   minFreshMillis = TimeUnit.SECONDS.toMillis(requestCaching.minFreshSeconds());
                        }
/* 181 */               long maxStaleMillis = 0;
/* 182 */               CacheControl responseCaching = this.cacheResponse.cacheControl();
/* 183 */               if (!responseCaching.mustRevalidate() && requestCaching.maxStaleSeconds() != -1) {
/* 184 */                   maxStaleMillis = TimeUnit.SECONDS.toMillis(requestCaching.maxStaleSeconds());
                        }
/* 187 */               if (!responseCaching.noCache() && ageMillis + minFreshMillis < freshMillis + maxStaleMillis) {
/* 188 */                   Response.Builder builder = this.cacheResponse.newBuilder();
                            if (ageMillis + minFreshMillis >= freshMillis) {
/* 190 */                       builder.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                            }
                            if (ageMillis > 86400000 && isFreshnessLifetimeHeuristic()) {
/* 194 */                       builder.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                            }
/* 196 */                   return new CacheStrategy(null, builder.build(), null);
                        }
/* 199 */               Request.Builder conditionalRequestBuilder = this.request.newBuilder();
/* 201 */               if (this.lastModified != null) {
/* 202 */                   conditionalRequestBuilder.header("If-Modified-Since", this.lastModifiedString);
                        } else if (this.servedDate != null) {
/* 204 */                   conditionalRequestBuilder.header("If-Modified-Since", this.servedDateString);
                        }
/* 207 */               if (this.etag != null) {
/* 208 */                   conditionalRequestBuilder.header("If-None-Match", this.etag);
                        }
/* 211 */               Request conditionalRequest = conditionalRequestBuilder.build();
                        return hasConditions(conditionalRequest) ? new CacheStrategy(conditionalRequest, this.cacheResponse, null) : new CacheStrategy(conditionalRequest, null, null);
                    }

/* 221 */           private long computeFreshnessLifetime() {
/* 222 */               CacheControl responseCaching = this.cacheResponse.cacheControl();
/* 223 */               if (responseCaching.maxAgeSeconds() != -1) {
/* 224 */                   return TimeUnit.SECONDS.toMillis(responseCaching.maxAgeSeconds());
                        }
/* 225 */               if (this.expires != null) {
/* 226 */                   long servedMillis = this.servedDate != null ? this.servedDate.getTime() : this.receivedResponseMillis;
/* 229 */                   long delta = this.expires.getTime() - servedMillis;
                            if (delta <= 0) {
                                delta = 0;
                            }
                            return delta;
                        }
/* 231 */               if (this.lastModified == null || this.cacheResponse.request().url().getQuery() != null) {
                            return 0L;
                        }
/* 237 */               long servedMillis2 = this.servedDate != null ? this.servedDate.getTime() : this.sentRequestMillis;
/* 240 */               long delta2 = servedMillis2 - this.lastModified.getTime();
                        if (delta2 > 0) {
                            return delta2 / 10;
                        }
                        return 0L;
                    }

/* 250 */           private long cacheResponseAge() {
/* 251 */               long apparentReceivedAge = this.servedDate != null ? Math.max(0L, this.receivedResponseMillis - this.servedDate.getTime()) : 0L;
/* 254 */               long receivedAge = this.ageSeconds != -1 ? Math.max(apparentReceivedAge, TimeUnit.SECONDS.toMillis(this.ageSeconds)) : apparentReceivedAge;
/* 257 */               long responseDuration = this.receivedResponseMillis - this.sentRequestMillis;
/* 258 */               long residentDuration = this.nowMillis - this.receivedResponseMillis;
/* 259 */               return receivedAge + responseDuration + residentDuration;
                    }

/* 267 */           private boolean isFreshnessLifetimeHeuristic() {
                        return this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
                    }

/* 276 */           private static boolean hasConditions(Request request) {
                        return (request.header("If-Modified-Since") == null && request.header("If-None-Match") == null) ? false : true;
                    }
                }
            }
