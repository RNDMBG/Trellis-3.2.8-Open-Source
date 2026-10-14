            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.http.HeaderParser;
            import java.util.concurrent.TimeUnit;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public final class CacheControl {
                private final boolean isPublic;
                private final int maxAgeSeconds;
                private final int maxStaleSeconds;
                private final int minFreshSeconds;
                private final boolean mustRevalidate;
                private final boolean noCache;
                private final boolean noStore;
                private final boolean noTransform;
                private final boolean onlyIfCached;
                private final int sMaxAgeSeconds;
                public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();
                public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();

/* 13 */        /* synthetic */ CacheControl(Builder x0, Object x1) {
/* 14 */            this(x0);
                }

/* 45 */        private CacheControl(boolean noCache, boolean noStore, int maxAgeSeconds, int sMaxAgeSeconds, boolean isPublic, boolean mustRevalidate, int maxStaleSeconds, int minFreshSeconds, boolean onlyIfCached, boolean noTransform) {
/* 47 */            this.noCache = noCache;
/* 48 */            this.noStore = noStore;
/* 49 */            this.maxAgeSeconds = maxAgeSeconds;
/* 50 */            this.sMaxAgeSeconds = sMaxAgeSeconds;
/* 51 */            this.isPublic = isPublic;
/* 52 */            this.mustRevalidate = mustRevalidate;
/* 53 */            this.maxStaleSeconds = maxStaleSeconds;
/* 54 */            this.minFreshSeconds = minFreshSeconds;
/* 55 */            this.onlyIfCached = onlyIfCached;
/* 56 */            this.noTransform = noTransform;
                }

/* 58 */        private CacheControl(Builder builder) {
/* 60 */            this.noCache = builder.noCache;
/* 61 */            this.noStore = builder.noStore;
/* 62 */            this.maxAgeSeconds = builder.maxAgeSeconds;
/* 63 */            this.sMaxAgeSeconds = -1;
/* 64 */            this.isPublic = false;
/* 65 */            this.mustRevalidate = false;
/* 66 */            this.maxStaleSeconds = builder.maxStaleSeconds;
/* 67 */            this.minFreshSeconds = builder.minFreshSeconds;
/* 68 */            this.onlyIfCached = builder.onlyIfCached;
/* 69 */            this.noTransform = builder.noTransform;
                }

/* 80 */        public boolean noCache() {
/* 81 */            return this.noCache;
                }

/* 85 */        public boolean noStore() {
/* 86 */            return this.noStore;
                }

/* 93 */        public int maxAgeSeconds() {
/* 94 */            return this.maxAgeSeconds;
                }

/* 102 */       public int sMaxAgeSeconds() {
/* 103 */           return this.sMaxAgeSeconds;
                }

/* 106 */       public boolean isPublic() {
/* 107 */           return this.isPublic;
                }

/* 110 */       public boolean mustRevalidate() {
/* 111 */           return this.mustRevalidate;
                }

/* 114 */       public int maxStaleSeconds() {
/* 115 */           return this.maxStaleSeconds;
                }

/* 118 */       public int minFreshSeconds() {
/* 119 */           return this.minFreshSeconds;
                }

/* 129 */       public boolean onlyIfCached() {
/* 130 */           return this.onlyIfCached;
                }

/* 133 */       public boolean noTransform() {
/* 134 */           return this.noTransform;
                }

/* 141 */       public static CacheControl parse(Headers headers) {
                    String parameter;
/* 142 */           boolean noCache = false;
/* 143 */           boolean noStore = false;
/* 144 */           int maxAgeSeconds = -1;
/* 145 */           int sMaxAgeSeconds = -1;
/* 146 */           boolean isPublic = false;
/* 147 */           boolean mustRevalidate = false;
/* 148 */           int maxStaleSeconds = -1;
/* 149 */           int minFreshSeconds = -1;
/* 150 */           boolean onlyIfCached = false;
/* 151 */           boolean noTransform = false;
                    for (int i = 0; i < headers.size(); i++) {
/* 154 */               if (headers.name(i).equalsIgnoreCase("Cache-Control") || headers.name(i).equalsIgnoreCase("Pragma")) {
/* 159 */                   String string = headers.value(i);
/* 160 */                   int pos = 0;
/* 161 */                   while (pos < string.length()) {
/* 162 */                       int tokenStart = pos;
/* 163 */                       int pos2 = HeaderParser.skipUntil(string, pos, "=,;");
/* 164 */                       String directive = string.substring(tokenStart, pos2).trim();
/* 167 */                       if (pos2 == string.length() || string.charAt(pos2) == ',' || string.charAt(pos2) == ';') {
/* 168 */                           pos = pos2 + 1;
/* 169 */                           parameter = null;
                                } else {
/* 172 */                           int pos3 = HeaderParser.skipWhitespace(string, pos2 + 1);
/* 175 */                           if (pos3 < string.length() && string.charAt(pos3) == '\"') {
/* 176 */                               int pos4 = pos3 + 1;
/* 178 */                               int pos5 = HeaderParser.skipUntil(string, pos4, "\"");
/* 179 */                               parameter = string.substring(pos4, pos5);
/* 180 */                               pos = pos5 + 1;
                                    } else {
/* 185 */                               pos = HeaderParser.skipUntil(string, pos3, ",;");
/* 186 */                               parameter = string.substring(pos3, pos).trim();
                                    }
                                }
/* 190 */                       if ("no-cache".equalsIgnoreCase(directive)) {
/* 191 */                           noCache = true;
                                } else if ("no-store".equalsIgnoreCase(directive)) {
/* 193 */                           noStore = true;
                                } else if ("max-age".equalsIgnoreCase(directive)) {
/* 195 */                           maxAgeSeconds = HeaderParser.parseSeconds(parameter, -1);
                                } else if ("s-maxage".equalsIgnoreCase(directive)) {
/* 197 */                           sMaxAgeSeconds = HeaderParser.parseSeconds(parameter, -1);
                                } else if ("public".equalsIgnoreCase(directive)) {
/* 199 */                           isPublic = true;
                                } else if ("must-revalidate".equalsIgnoreCase(directive)) {
/* 201 */                           mustRevalidate = true;
                                } else if ("max-stale".equalsIgnoreCase(directive)) {
/* 203 */                           maxStaleSeconds = HeaderParser.parseSeconds(parameter, Integer.MAX_VALUE);
                                } else if ("min-fresh".equalsIgnoreCase(directive)) {
/* 205 */                           minFreshSeconds = HeaderParser.parseSeconds(parameter, -1);
                                } else if ("only-if-cached".equalsIgnoreCase(directive)) {
/* 207 */                           onlyIfCached = true;
                                } else if ("no-transform".equalsIgnoreCase(directive)) {
/* 209 */                           noTransform = true;
                                }
                            }
                        }
                    }
/* 214 */           return new CacheControl(noCache, noStore, maxAgeSeconds, sMaxAgeSeconds, isPublic, mustRevalidate, maxStaleSeconds, minFreshSeconds, onlyIfCached, noTransform);
                }

/* 218 */       public String toString() {
/* 219 */           StringBuilder result = new StringBuilder();
/* 220 */           if (this.noCache) {
                        result.append("no-cache, ");
                    }
/* 221 */           if (this.noStore) {
                        result.append("no-store, ");
                    }
/* 222 */           if (this.maxAgeSeconds != -1) {
                        result.append("max-age=").append(this.maxAgeSeconds).append(", ");
                    }
/* 223 */           if (this.sMaxAgeSeconds != -1) {
                        result.append("s-maxage=").append(this.sMaxAgeSeconds).append(", ");
                    }
/* 224 */           if (this.isPublic) {
                        result.append("public, ");
                    }
/* 225 */           if (this.mustRevalidate) {
                        result.append("must-revalidate, ");
                    }
/* 226 */           if (this.maxStaleSeconds != -1) {
                        result.append("max-stale=").append(this.maxStaleSeconds).append(", ");
                    }
/* 227 */           if (this.minFreshSeconds != -1) {
                        result.append("min-fresh=").append(this.minFreshSeconds).append(", ");
                    }
/* 228 */           if (this.onlyIfCached) {
                        result.append("only-if-cached, ");
                    }
/* 229 */           if (this.noTransform) {
                        result.append("no-transform, ");
                    }
/* 230 */           if (result.length() == 0) {
                        return "";
                    }
/* 231 */           result.delete(result.length() - 2, result.length());
/* 232 */           return result.toString();
                }

/* 245 */       public static final class Builder {
                    int maxAgeSeconds = -1;
                    int maxStaleSeconds = -1;
                    int minFreshSeconds = -1;
                    boolean noCache;
                    boolean noStore;
                    boolean noTransform;
                    boolean onlyIfCached;

/* 246 */           public Builder noCache() {
/* 247 */               this.noCache = true;
/* 248 */               return this;
                    }

/* 252 */           public Builder noStore() {
/* 253 */               this.noStore = true;
/* 254 */               return this;
                    }

/* 265 */           public Builder maxAge(int maxAge, TimeUnit timeUnit) {
/* 266 */               if (maxAge < 0) {
                            throw new IllegalArgumentException("maxAge < 0: " + maxAge);
                        }
/* 267 */               long maxAgeSecondsLong = timeUnit.toSeconds(maxAge);
/* 268 */               this.maxAgeSeconds = maxAgeSecondsLong > 2147483647L ? Integer.MAX_VALUE : (int) maxAgeSecondsLong;
/* 271 */               return this;
                    }

/* 283 */           public Builder maxStale(int maxStale, TimeUnit timeUnit) {
/* 284 */               if (maxStale < 0) {
                            throw new IllegalArgumentException("maxStale < 0: " + maxStale);
                        }
/* 285 */               long maxStaleSecondsLong = timeUnit.toSeconds(maxStale);
/* 286 */               this.maxStaleSeconds = maxStaleSecondsLong > 2147483647L ? Integer.MAX_VALUE : (int) maxStaleSecondsLong;
/* 289 */               return this;
                    }

/* 302 */           public Builder minFresh(int minFresh, TimeUnit timeUnit) {
/* 303 */               if (minFresh < 0) {
                            throw new IllegalArgumentException("minFresh < 0: " + minFresh);
                        }
/* 304 */               long minFreshSecondsLong = timeUnit.toSeconds(minFresh);
/* 305 */               this.minFreshSeconds = minFreshSecondsLong > 2147483647L ? Integer.MAX_VALUE : (int) minFreshSecondsLong;
/* 308 */               return this;
                    }

/* 315 */           public Builder onlyIfCached() {
/* 316 */               this.onlyIfCached = true;
/* 317 */               return this;
                    }

/* 321 */           public Builder noTransform() {
/* 322 */               this.noTransform = true;
/* 323 */               return this;
                    }

/* 326 */           public CacheControl build() {
/* 327 */               return new CacheControl(this, null);
                    }
                }
            }
