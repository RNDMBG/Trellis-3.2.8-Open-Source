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
            
            
    public final class AccessToken implements Serializable {
                static final  boolean $assertionsDisabled;
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
            $assertionsDisabled = !AccessToken.class.desiredAssertionStatus();
            MIN_DATE = new Date(Long.MIN_VALUE);
            MAX_DATE = new Date(Long.MAX_VALUE);
            DEFAULT_EXPIRATION_TIME = MAX_DATE;
            DEFAULT_LAST_REFRESH_TIME = new Date();
            DEFAULT_ACCESS_TOKEN_SOURCE = AccessTokenSource.FACEBOOK_APPLICATION_WEB;
            ALREADY_EXPIRED_EXPIRATION_TIME = MIN_DATE;
                }

        AccessToken(String token, Date expires, List<String> permissions, AccessTokenSource source, Date lastRefresh) {
            permissions = permissions == null ? Collections.emptyList() : permissions;
            this.expires = expires;
            this.permissions = Collections.unmodifiableList(permissions);
            this.token = token;
            this.source = source;
            this.lastRefresh = lastRefresh;
                }

        public String getToken() {
            return this.token;
                }

        public Date getExpires() {
            return this.expires;
                }

        public List<String> getPermissions() {
           return this.permissions;
                }

       public AccessTokenSource getSource() {
           return this.source;
                }

       public Date getLastRefresh() {
           return this.lastRefresh;
                }

       public static AccessToken createFromExistingAccessToken(String accessToken, Date expirationTime, Date lastRefreshTime, AccessTokenSource accessTokenSource, List<String> permissions) {
           if (expirationTime == null) {
               expirationTime = DEFAULT_EXPIRATION_TIME;
                    }
           if (lastRefreshTime == null) {
               lastRefreshTime = DEFAULT_LAST_REFRESH_TIME;
                    }
           if (accessTokenSource == null) {
               accessTokenSource = DEFAULT_ACCESS_TOKEN_SOURCE;
                    }
           return new AccessToken(accessToken, expirationTime, permissions, accessTokenSource, lastRefreshTime);
                }

       public static AccessToken createFromNativeLinkingIntent(Intent intent) {
           Validate.notNull(intent, "intent");
           if (intent.getExtras() == null) {
                        return null;
                    }
                    return createFromBundle(null, intent.getExtras(), AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date());
                }

       public String toString() {
           StringBuilder builder = new StringBuilder();
           builder.append("{AccessToken");
           builder.append(" token:").append(tokenToString());
           appendPermissions(builder);
           builder.append("}");
           return builder.toString();
                }

       static AccessToken createEmptyToken(List<String> permissions) {
           return new AccessToken("", ALREADY_EXPIRED_EXPIRATION_TIME, permissions, AccessTokenSource.NONE, DEFAULT_LAST_REFRESH_TIME);
                }

       static AccessToken createFromString(String token, List<String> permissions, AccessTokenSource source) {
           return new AccessToken(token, DEFAULT_EXPIRATION_TIME, permissions, source, DEFAULT_LAST_REFRESH_TIME);
                }

       static AccessToken createFromNativeLogin(Bundle bundle, AccessTokenSource source) {
           Date expires = getBundleLongAsDate(bundle, "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH", new Date(0L));
           ArrayList<String> permissions = bundle.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
           String token = bundle.getString("com.facebook.platform.extra.ACCESS_TOKEN");
           return createNew(permissions, token, expires, source);
                }

       static AccessToken createFromWebBundle(List<String> requestedPermissions, Bundle bundle, AccessTokenSource source) {
           Date expires = getBundleLongAsDate(bundle, EXPIRES_IN_KEY, new Date());
           String token = bundle.getString("access_token");
           return createNew(requestedPermissions, token, expires, source);
                }

                @SuppressLint({"FieldGetter"})
       static AccessToken createFromRefresh(AccessToken current, Bundle bundle) {
           if (!$assertionsDisabled && current.source != AccessTokenSource.FACEBOOK_APPLICATION_WEB && current.source != AccessTokenSource.FACEBOOK_APPLICATION_NATIVE && current.source != AccessTokenSource.FACEBOOK_APPLICATION_SERVICE) {
                        throw new AssertionError();
                    }
           Date expires = getBundleLongAsDate(bundle, EXPIRES_IN_KEY, new Date(0L));
           String token = bundle.getString("access_token");
           return createNew(current.getPermissions(), token, expires, current.source);
                }

       static AccessToken createFromTokenWithRefreshedPermissions(AccessToken token, List<String> permissions) {
           return new AccessToken(token.token, token.expires, permissions, token.source, token.lastRefresh);
                }

       private static AccessToken createNew(List<String> requestedPermissions, String accessToken, Date expires, AccessTokenSource source) {
                    return (Utility.isNullOrEmpty(accessToken) || expires == null) ? createEmptyToken(requestedPermissions) : new AccessToken(accessToken, expires, requestedPermissions, source, new Date());
                }

       static AccessToken createFromCache(Bundle bundle) {
                    List<String> permissions;
           List<String> originalPermissions = bundle.getStringArrayList(TokenCachingStrategy.PERMISSIONS_KEY);
           if (originalPermissions == null) {
               permissions = Collections.emptyList();
                    } else {
               permissions = Collections.unmodifiableList(new ArrayList(originalPermissions));
                    }
           return new AccessToken(bundle.getString(TokenCachingStrategy.TOKEN_KEY), TokenCachingStrategy.getDate(bundle, TokenCachingStrategy.EXPIRATION_DATE_KEY), permissions, TokenCachingStrategy.getSource(bundle), TokenCachingStrategy.getDate(bundle, TokenCachingStrategy.LAST_REFRESH_DATE_KEY));
                }

       Bundle toCacheBundle() {
           Bundle bundle = new Bundle();
           bundle.putString(TokenCachingStrategy.TOKEN_KEY, this.token);
           TokenCachingStrategy.putDate(bundle, TokenCachingStrategy.EXPIRATION_DATE_KEY, this.expires);
           bundle.putStringArrayList(TokenCachingStrategy.PERMISSIONS_KEY, new ArrayList<>(this.permissions));
           bundle.putSerializable(TokenCachingStrategy.TOKEN_SOURCE_KEY, this.source);
           TokenCachingStrategy.putDate(bundle, TokenCachingStrategy.LAST_REFRESH_DATE_KEY, this.lastRefresh);
           return bundle;
                }

       boolean isInvalid() {
                    return Utility.isNullOrEmpty(this.token) || new Date().after(this.expires);
                }

       private static AccessToken createFromBundle(List<String> requestedPermissions, Bundle bundle, AccessTokenSource source, Date expirationBase) {
           String token = bundle.getString("access_token");
           Date expires = getBundleLongAsDate(bundle, EXPIRES_IN_KEY, expirationBase);
           if (Utility.isNullOrEmpty(token) || expires == null) {
               return null;
                    }
                    return new AccessToken(token, expires, requestedPermissions, source, new Date());
                }

       private String tokenToString() {
           if (this.token == null) {
               return "null";
                    }
           if (Settings.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
               return this.token;
                    }
           return "ACCESS_TOKEN_REMOVED";
                }

       private void appendPermissions(StringBuilder builder) {
           builder.append(" permissions:");
           if (this.permissions == null) {
               builder.append("null");
               return;
                    }
           builder.append("[");
           builder.append(TextUtils.join(", ", this.permissions));
           builder.append("]");
                }

       private static class SerializationProxyV1 implements Serializable {
                    private static final long serialVersionUID = -2488473066578201069L;
                    private final Date expires;
                    private final Date lastRefresh;
                    private final List<String> permissions;
                    private final AccessTokenSource source;
                    private final String token;

            SerializationProxyV1(String x0, Date x1, List x2, AccessTokenSource x3, Date x4, Object x5) {
               this(x0, x1, x2, x3, x4);
                    }

           private SerializationProxyV1(String token, Date expires, List<String> permissions, AccessTokenSource source, Date lastRefresh) {
               this.expires = expires;
               this.permissions = permissions;
               this.token = token;
               this.source = source;
               this.lastRefresh = lastRefresh;
                    }

           private Object readResolve() {
               return new AccessToken(this.token, this.expires, this.permissions, this.source, this.lastRefresh);
                    }
                }

       private Object writeReplace() {
           return new SerializationProxyV1(this.token, this.expires, this.permissions, this.source, this.lastRefresh, null);
                }

       private void readObject(ObjectInputStream stream) throws InvalidObjectException {
           throw new InvalidObjectException("Cannot readObject, serialization proxy required");
                }

       private static Date getBundleLongAsDate(Bundle bundle, String key, Date dateBase) {
                    long secondsFromBase;
           if (bundle == null) {
                        return null;
                    }
           Object secondsObject = bundle.get(key);
           if (secondsObject instanceof Long) {
               secondsFromBase = ((Long) secondsObject).longValue();
                    } else {
               if (!(secondsObject instanceof String)) {
                            return null;
                        }
                        try {
                   secondsFromBase = Long.parseLong((String) secondsObject);
                        } catch (NumberFormatException e) {
                            return null;
                        }
                    }
                    if (secondsFromBase == 0) {
               return new Date(Long.MAX_VALUE);
                    }
           return new Date(dateBase.getTime() + (1000 * secondsFromBase));
                }
            }
