            package com.facebook;

            import android.annotation.SuppressLint;
            import android.content.Intent;
            import android.os.Bundle;
            import android.text.TextUtils;
            import com.facebook.internal.Utility;
            import com.facebook.internal.Validate;
            import java.io.InvalidObjectException;
            import java.io.ObjectInputStream;
            import java.io.Serializable;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Date;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public final class AccessToken implements Serializable {
                static final /* synthetic */ boolean $assertionsDisabled;
                static final String ACCESS_TOKEN_KEY = "access_token";
                private static final Date ALREADY_EXPIRED_EXPIRATION_TIME;
                private static final AccessTokenSource DEFAULT_ACCESS_TOKEN_SOURCE;
                private static final Date DEFAULT_EXPIRATION_TIME;
                private static final Date DEFAULT_LAST_REFRESH_TIME;
                static final String EXPIRES_IN_KEY = "expires_in";
                private static final Date MAX_DATE;
                private static final Date MIN_DATE;
                private static final long serialVersionUID = 1;
                private final Date expires;
                private final Date lastRefresh;
                private final List<String> permissions;
                private final AccessTokenSource source;
                private final String token;

                static {
/* 44 */            $assertionsDisabled = !AccessToken.class.desiredAssertionStatus();
/* 48 */            MIN_DATE = new Date(Long.MIN_VALUE);
/* 49 */            MAX_DATE = new Date(Long.MAX_VALUE);
/* 50 */            DEFAULT_EXPIRATION_TIME = MAX_DATE;
/* 51 */            DEFAULT_LAST_REFRESH_TIME = new Date();
/* 52 */            DEFAULT_ACCESS_TOKEN_SOURCE = AccessTokenSource.FACEBOOK_APPLICATION_WEB;
/* 53 */            ALREADY_EXPIRED_EXPIRATION_TIME = MIN_DATE;
                }

/* 60 */        AccessToken(String token, Date expires, List<String> permissions, AccessTokenSource source, Date lastRefresh) {
/* 63 */            permissions = permissions == null ? Collections.emptyList() : permissions;
/* 66 */            this.expires = expires;
/* 67 */            this.permissions = Collections.unmodifiableList(permissions);
/* 68 */            this.token = token;
/* 69 */            this.source = source;
/* 70 */            this.lastRefresh = lastRefresh;
                }

/* 78 */        public String getToken() {
/* 79 */            return this.token;
                }

/* 87 */        public Date getExpires() {
/* 88 */            return this.expires;
                }

/* 99 */        public List<String> getPermissions() {
/* 100 */           return this.permissions;
                }

/* 108 */       public AccessTokenSource getSource() {
/* 109 */           return this.source;
                }

/* 118 */       public Date getLastRefresh() {
/* 119 */           return this.lastRefresh;
                }

/* 140 */       public static AccessToken createFromExistingAccessToken(String accessToken, Date expirationTime, Date lastRefreshTime, AccessTokenSource accessTokenSource, List<String> permissions) {
/* 141 */           if (expirationTime == null) {
/* 142 */               expirationTime = DEFAULT_EXPIRATION_TIME;
                    }
/* 144 */           if (lastRefreshTime == null) {
/* 145 */               lastRefreshTime = DEFAULT_LAST_REFRESH_TIME;
                    }
/* 147 */           if (accessTokenSource == null) {
/* 148 */               accessTokenSource = DEFAULT_ACCESS_TOKEN_SOURCE;
                    }
/* 151 */           return new AccessToken(accessToken, expirationTime, permissions, accessTokenSource, lastRefreshTime);
                }

/* 162 */       public static AccessToken createFromNativeLinkingIntent(Intent intent) {
/* 163 */           Validate.notNull(intent, "intent");
/* 165 */           if (intent.getExtras() == null) {
                        return null;
                    }
                    return createFromBundle(null, intent.getExtras(), AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date());
                }

/* 173 */       public String toString() {
/* 174 */           StringBuilder builder = new StringBuilder();
/* 176 */           builder.append("{AccessToken");
/* 177 */           builder.append(" token:").append(tokenToString());
/* 178 */           appendPermissions(builder);
/* 179 */           builder.append("}");
/* 181 */           return builder.toString();
                }

/* 184 */       static AccessToken createEmptyToken(List<String> permissions) {
/* 185 */           return new AccessToken("", ALREADY_EXPIRED_EXPIRATION_TIME, permissions, AccessTokenSource.NONE, DEFAULT_LAST_REFRESH_TIME);
                }

/* 189 */       static AccessToken createFromString(String token, List<String> permissions, AccessTokenSource source) {
/* 190 */           return new AccessToken(token, DEFAULT_EXPIRATION_TIME, permissions, source, DEFAULT_LAST_REFRESH_TIME);
                }

/* 193 */       static AccessToken createFromNativeLogin(Bundle bundle, AccessTokenSource source) {
/* 194 */           Date expires = getBundleLongAsDate(bundle, "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH", new Date(0L));
/* 196 */           ArrayList<String> permissions = bundle.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
/* 197 */           String token = bundle.getString("com.facebook.platform.extra.ACCESS_TOKEN");
/* 199 */           return createNew(permissions, token, expires, source);
                }

/* 202 */       static AccessToken createFromWebBundle(List<String> requestedPermissions, Bundle bundle, AccessTokenSource source) {
/* 203 */           Date expires = getBundleLongAsDate(bundle, EXPIRES_IN_KEY, new Date());
/* 204 */           String token = bundle.getString("access_token");
/* 206 */           return createNew(requestedPermissions, token, expires, source);
                }

                @SuppressLint({"FieldGetter"})
/* 212 */       static AccessToken createFromRefresh(AccessToken current, Bundle bundle) {
/* 213 */           if (!$assertionsDisabled && current.source != AccessTokenSource.FACEBOOK_APPLICATION_WEB && current.source != AccessTokenSource.FACEBOOK_APPLICATION_NATIVE && current.source != AccessTokenSource.FACEBOOK_APPLICATION_SERVICE) {
                        throw new AssertionError();
                    }
/* 217 */           Date expires = getBundleLongAsDate(bundle, EXPIRES_IN_KEY, new Date(0L));
/* 218 */           String token = bundle.getString("access_token");
/* 220 */           return createNew(current.getPermissions(), token, expires, current.source);
                }

/* 223 */       static AccessToken createFromTokenWithRefreshedPermissions(AccessToken token, List<String> permissions) {
/* 224 */           return new AccessToken(token.token, token.expires, permissions, token.source, token.lastRefresh);
                }

/* 228 */       private static AccessToken createNew(List<String> requestedPermissions, String accessToken, Date expires, AccessTokenSource source) {
                    return (Utility.isNullOrEmpty(accessToken) || expires == null) ? createEmptyToken(requestedPermissions) : new AccessToken(accessToken, expires, requestedPermissions, source, new Date());
                }

/* 237 */       static AccessToken createFromCache(Bundle bundle) {
                    List<String> permissions;
/* 238 */           List<String> originalPermissions = bundle.getStringArrayList(TokenCachingStrategy.PERMISSIONS_KEY);
/* 240 */           if (originalPermissions == null) {
/* 241 */               permissions = Collections.emptyList();
                    } else {
/* 243 */               permissions = Collections.unmodifiableList(new ArrayList(originalPermissions));
                    }
/* 246 */           return new AccessToken(bundle.getString(TokenCachingStrategy.TOKEN_KEY), TokenCachingStrategy.getDate(bundle, TokenCachingStrategy.EXPIRATION_DATE_KEY), permissions, TokenCachingStrategy.getSource(bundle), TokenCachingStrategy.getDate(bundle, TokenCachingStrategy.LAST_REFRESH_DATE_KEY));
                }

/* 251 */       Bundle toCacheBundle() {
/* 252 */           Bundle bundle = new Bundle();
/* 254 */           bundle.putString(TokenCachingStrategy.TOKEN_KEY, this.token);
/* 255 */           TokenCachingStrategy.putDate(bundle, TokenCachingStrategy.EXPIRATION_DATE_KEY, this.expires);
/* 256 */           bundle.putStringArrayList(TokenCachingStrategy.PERMISSIONS_KEY, new ArrayList<>(this.permissions));
/* 257 */           bundle.putSerializable(TokenCachingStrategy.TOKEN_SOURCE_KEY, this.source);
/* 258 */           TokenCachingStrategy.putDate(bundle, TokenCachingStrategy.LAST_REFRESH_DATE_KEY, this.lastRefresh);
/* 260 */           return bundle;
                }

/* 263 */       boolean isInvalid() {
                    return Utility.isNullOrEmpty(this.token) || new Date().after(this.expires);
                }

/* 269 */       private static AccessToken createFromBundle(List<String> requestedPermissions, Bundle bundle, AccessTokenSource source, Date expirationBase) {
/* 270 */           String token = bundle.getString("access_token");
/* 271 */           Date expires = getBundleLongAsDate(bundle, EXPIRES_IN_KEY, expirationBase);
/* 273 */           if (Utility.isNullOrEmpty(token) || expires == null) {
/* 274 */               return null;
                    }
                    return new AccessToken(token, expires, requestedPermissions, source, new Date());
                }

/* 280 */       private String tokenToString() {
/* 281 */           if (this.token == null) {
/* 282 */               return "null";
                    }
/* 283 */           if (Settings.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
/* 284 */               return this.token;
                    }
/* 286 */           return "ACCESS_TOKEN_REMOVED";
                }

/* 290 */       private void appendPermissions(StringBuilder builder) {
/* 291 */           builder.append(" permissions:");
/* 292 */           if (this.permissions == null) {
/* 293 */               builder.append("null");
/* 299 */               return;
                    }
/* 295 */           builder.append("[");
/* 296 */           builder.append(TextUtils.join(", ", this.permissions));
/* 297 */           builder.append("]");
                }

/* 299 */       private static class SerializationProxyV1 implements Serializable {
                    private static final long serialVersionUID = -2488473066578201069L;
                    private final Date expires;
                    private final Date lastRefresh;
                    private final List<String> permissions;
                    private final AccessTokenSource source;
                    private final String token;

/* 300 */           /* synthetic */ SerializationProxyV1(String x0, Date x1, List x2, AccessTokenSource x3, Date x4, Object x5) {
/* 301 */               this(x0, x1, x2, x3, x4);
                    }

/* 309 */           private SerializationProxyV1(String token, Date expires, List<String> permissions, AccessTokenSource source, Date lastRefresh) {
/* 311 */               this.expires = expires;
/* 312 */               this.permissions = permissions;
/* 313 */               this.token = token;
/* 314 */               this.source = source;
/* 315 */               this.lastRefresh = lastRefresh;
                    }

/* 318 */           private Object readResolve() {
/* 319 */               return new AccessToken(this.token, this.expires, this.permissions, this.source, this.lastRefresh);
                    }
                }

/* 323 */       private Object writeReplace() {
/* 324 */           return new SerializationProxyV1(this.token, this.expires, this.permissions, this.source, this.lastRefresh, null);
                }

/* 328 */       private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 329 */           throw new InvalidObjectException("Cannot readObject, serialization proxy required");
                }

/* 333 */       private static Date getBundleLongAsDate(Bundle bundle, String key, Date dateBase) {
                    long secondsFromBase;
/* 334 */           if (bundle == null) {
                        return null;
                    }
/* 340 */           Object secondsObject = bundle.get(key);
/* 341 */           if (secondsObject instanceof Long) {
/* 342 */               secondsFromBase = ((Long) secondsObject).longValue();
                    } else {
/* 343 */               if (!(secondsObject instanceof String)) {
                            return null;
                        }
                        try {
/* 345 */                   secondsFromBase = Long.parseLong((String) secondsObject);
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    }
                    if (secondsFromBase == 0) {
/* 354 */               return new Date(Long.MAX_VALUE);
                    }
/* 356 */           return new Date(dateBase.getTime() + (1000 * secondsFromBase));
                }
            }
