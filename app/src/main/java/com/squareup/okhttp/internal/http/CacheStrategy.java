            package com.squareup.okhttp.internal.http;

            import com.squareup.okhttp.CacheControl;
            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Response;
            import java.util.Date;
            import java.util.concurrent.TimeUnit;
            
            
    public final class CacheStrategy {
                public final Response cacheResponse;
                public final Request networkRequest;

         CacheStrategy(Request x0, Response x1, Object x2) {
            this(x0, x1);
                }

        private CacheStrategy(Request networkRequest, Response cacheResponse) {
            this.networkRequest = networkRequest;
            this.cacheResponse = cacheResponse;
                }

        public static boolean isCacheable(Response response, Request request) {
            int responseCode = response.code();
            if (responseCode != 200 && responseCode != 203 && responseCode != 300 && responseCode != 301 && responseCode != 410 && responseCode != 308) {
                        return false;
                    }
            CacheControl responseCaching = response.cacheControl();
            CacheControl requestCaching = request.cacheControl();
                    return (responseCaching.noStore() || requestCaching.noStore()) ? false : true;
                }

        public static class Factory {
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

            public Factory(long nowMillis, Request request, Response cacheResponse) {
                this.ageSeconds = -1;
               this.nowMillis = nowMillis;
               this.request = request;
               this.cacheResponse = cacheResponse;
               if (cacheResponse != null) {
                            for (int i = 0; i < cacheResponse.headers().size(); i++) {
                       String fieldName = cacheResponse.headers().name(i);
                       String value = cacheResponse.headers().value(i);
                       if ("Date".equalsIgnoreCase(fieldName)) {
                           this.servedDate = HttpDate.parse(value);
                           this.servedDateString = value;
                                } else if ("Expires".equalsIgnoreCase(fieldName)) {
                           this.expires = HttpDate.parse(value);
                                } else if ("Last-Modified".equalsIgnoreCase(fieldName)) {
                           this.lastModified = HttpDate.parse(value);
                           this.lastModifiedString = value;
                                } else if ("ETag".equalsIgnoreCase(fieldName)) {
                           this.etag = value;
                                } else if ("Age".equalsIgnoreCase(fieldName)) {
                           this.ageSeconds = HeaderParser.parseSeconds(value, -1);
                                } else if (OkHeaders.SENT_MILLIS.equalsIgnoreCase(fieldName)) {
                           this.sentRequestMillis = Long.parseLong(value);
                                } else if (OkHeaders.RECEIVED_MILLIS.equalsIgnoreCase(fieldName)) {
                           this.receivedResponseMillis = Long.parseLong(value);
                                }
                            }
                        }
                    }

           public CacheStrategy get() {
               CacheStrategy candidate = getCandidate();
               if (candidate.networkRequest != null && this.request.cacheControl().onlyIfCached()) {
                   return new CacheStrategy(null, null, null);
                        }
               return candidate;
                    }

           private CacheStrategy getCandidate() {
               if (this.cacheResponse == null) {
                   return new CacheStrategy(this.request, null, null);
                        }
               if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
                   return new CacheStrategy(this.request, null, null);
                        }
               if (!CacheStrategy.isCacheable(this.cacheResponse, this.request)) {
                   return new CacheStrategy(this.request, null, null);
                        }
               CacheControl requestCaching = this.request.cacheControl();
               if (requestCaching.noCache() || hasConditions(this.request)) {
                   return new CacheStrategy(this.request, null, null);
                        }
               long ageMillis = cacheResponseAge();
               long freshMillis = computeFreshnessLifetime();
               if (requestCaching.maxAgeSeconds() != -1) {
                   freshMillis = Math.min(freshMillis, TimeUnit.SECONDS.toMillis(requestCaching.maxAgeSeconds()));
                        }
               long minFreshMillis = 0;
               if (requestCaching.minFreshSeconds() != -1) {
                   minFreshMillis = TimeUnit.SECONDS.toMillis(requestCaching.minFreshSeconds());
                        }
               long maxStaleMillis = 0;
               CacheControl responseCaching = this.cacheResponse.cacheControl();
               if (!responseCaching.mustRevalidate() && requestCaching.maxStaleSeconds() != -1) {
                   maxStaleMillis = TimeUnit.SECONDS.toMillis(requestCaching.maxStaleSeconds());
                        }
               if (!responseCaching.noCache() && ageMillis + minFreshMillis < freshMillis + maxStaleMillis) {
                   Response.Builder builder = this.cacheResponse.newBuilder();
                            if (ageMillis + minFreshMillis >= freshMillis) {
                       builder.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                            }
                            if (ageMillis > 86400000 && isFreshnessLifetimeHeuristic()) {
                       builder.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                            }
                   return new CacheStrategy(null, builder.build(), null);
                        }
               Request.Builder conditionalRequestBuilder = this.request.newBuilder();
               if (this.lastModified != null) {
                   conditionalRequestBuilder.header("If-Modified-Since", this.lastModifiedString);
                        } else if (this.servedDate != null) {
                   conditionalRequestBuilder.header("If-Modified-Since", this.servedDateString);
                        }
               if (this.etag != null) {
                   conditionalRequestBuilder.header("If-None-Match", this.etag);
                        }
               Request conditionalRequest = conditionalRequestBuilder.build();
                        return hasConditions(conditionalRequest) ? new CacheStrategy(conditionalRequest, this.cacheResponse, null) : new CacheStrategy(conditionalRequest, null, null);
                    }

           private long computeFreshnessLifetime() {
               CacheControl responseCaching = this.cacheResponse.cacheControl();
               if (responseCaching.maxAgeSeconds() != -1) {
                   return TimeUnit.SECONDS.toMillis(responseCaching.maxAgeSeconds());
                        }
               if (this.expires != null) {
                   long servedMillis = this.servedDate != null ? this.servedDate.getTime() : this.receivedResponseMillis;
                   long delta = this.expires.getTime() - servedMillis;
                            if (delta <= 0) {
                                delta = 0;
                            }
                            return delta;
                        }
               if (this.lastModified == null || this.cacheResponse.request().url().getQuery() != null) {
                            return 0L;
                        }
               long servedMillis2 = this.servedDate != null ? this.servedDate.getTime() : this.sentRequestMillis;
               long delta2 = servedMillis2 - this.lastModified.getTime();
                        if (delta2 > 0) {
                            return delta2 / 10;
                        }
                        return 0L;
                    }

           private long cacheResponseAge() {
               long apparentReceivedAge = this.servedDate != null ? Math.max(0L, this.receivedResponseMillis - this.servedDate.getTime()) : 0L;
               long receivedAge = this.ageSeconds != -1 ? Math.max(apparentReceivedAge, TimeUnit.SECONDS.toMillis(this.ageSeconds)) : apparentReceivedAge;
               long responseDuration = this.receivedResponseMillis - this.sentRequestMillis;
               long residentDuration = this.nowMillis - this.receivedResponseMillis;
               return receivedAge + responseDuration + residentDuration;
                    }

           private boolean isFreshnessLifetimeHeuristic() {
                        return this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
                    }

           private static boolean hasConditions(Request request) {
                        return (request.header("If-Modified-Since") == null && request.header("If-None-Match") == null) ? false : true;
                    }
                }
            }
