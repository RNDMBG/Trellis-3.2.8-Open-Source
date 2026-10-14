            package com.facebook;

            import android.os.Bundle;
            import co.vine.android.provider.Vine;
            import com.facebook.internal.Validate;
            import java.util.ArrayList;
            import java.util.Date;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 121 */   public abstract class TokenCachingStrategy {
                public static final String EXPIRATION_DATE_KEY = "com.facebook.TokenCachingStrategy.ExpirationDate";
                private static final long INVALID_BUNDLE_MILLISECONDS = Long.MIN_VALUE;
                private static final String IS_SSO_KEY = "com.facebook.TokenCachingStrategy.IsSSO";
                public static final String LAST_REFRESH_DATE_KEY = "com.facebook.TokenCachingStrategy.LastRefreshDate";
                public static final String PERMISSIONS_KEY = "com.facebook.TokenCachingStrategy.Permissions";
                public static final String TOKEN_KEY = "com.facebook.TokenCachingStrategy.Token";
                public static final String TOKEN_SOURCE_KEY = "com.facebook.TokenCachingStrategy.AccessTokenSource";
                public static final String USER_FBID_KEY = "com.facebook.TokenCachingStrategy.UserFBID";

                public abstract void clear();

                public abstract Bundle load();

                public abstract void save(Bundle bundle);

/* 122 */       public static boolean hasTokenInformation(Bundle bundle) {
                    String token;
/* 123 */           if (bundle == null || (token = bundle.getString(TOKEN_KEY)) == null || token.length() == 0) {
                        return false;
                    }
/* 132 */           long expiresMilliseconds = bundle.getLong(EXPIRATION_DATE_KEY, 0L);
                    return expiresMilliseconds != 0;
                }

/* 149 */       public static String getToken(Bundle bundle) {
/* 150 */           Validate.notNull(bundle, "bundle");
/* 151 */           return bundle.getString(TOKEN_KEY);
                }

/* 164 */       public static void putToken(Bundle bundle, String value) {
/* 165 */           Validate.notNull(bundle, "bundle");
/* 166 */           Validate.notNull(value, Vine.Settings.PROP_VALUE);
/* 167 */           bundle.putString(TOKEN_KEY, value);
                }

/* 179 */       public static Date getExpirationDate(Bundle bundle) {
/* 180 */           Validate.notNull(bundle, "bundle");
/* 181 */           return getDate(bundle, EXPIRATION_DATE_KEY);
                }

/* 194 */       public static void putExpirationDate(Bundle bundle, Date value) {
/* 195 */           Validate.notNull(bundle, "bundle");
/* 196 */           Validate.notNull(value, Vine.Settings.PROP_VALUE);
/* 197 */           putDate(bundle, EXPIRATION_DATE_KEY, value);
                }

/* 210 */       public static long getExpirationMilliseconds(Bundle bundle) {
/* 211 */           Validate.notNull(bundle, "bundle");
/* 212 */           return bundle.getLong(EXPIRATION_DATE_KEY);
                }

/* 226 */       public static void putExpirationMilliseconds(Bundle bundle, long value) {
/* 227 */           Validate.notNull(bundle, "bundle");
/* 228 */           bundle.putLong(EXPIRATION_DATE_KEY, value);
                }

/* 240 */       public static List<String> getPermissions(Bundle bundle) {
/* 241 */           Validate.notNull(bundle, "bundle");
/* 242 */           return bundle.getStringArrayList(PERMISSIONS_KEY);
                }

/* 256 */       public static void putPermissions(Bundle bundle, List<String> value) {
                    ArrayList<String> arrayList;
/* 257 */           Validate.notNull(bundle, "bundle");
/* 258 */           Validate.notNull(value, Vine.Settings.PROP_VALUE);
/* 261 */           if (value instanceof ArrayList) {
/* 262 */               arrayList = (ArrayList) value;
                    } else {
/* 264 */               arrayList = new ArrayList<>(value);
                    }
/* 266 */           bundle.putStringArrayList(PERMISSIONS_KEY, arrayList);
                }

/* 278 */       public static AccessTokenSource getSource(Bundle bundle) {
/* 279 */           Validate.notNull(bundle, "bundle");
/* 280 */           if (bundle.containsKey(TOKEN_SOURCE_KEY)) {
/* 281 */               return (AccessTokenSource) bundle.getSerializable(TOKEN_SOURCE_KEY);
                    }
/* 283 */           boolean isSSO = bundle.getBoolean(IS_SSO_KEY);
                    return isSSO ? AccessTokenSource.FACEBOOK_APPLICATION_WEB : AccessTokenSource.WEB_VIEW;
                }

/* 297 */       public static void putSource(Bundle bundle, AccessTokenSource value) {
/* 298 */           Validate.notNull(bundle, "bundle");
/* 299 */           bundle.putSerializable(TOKEN_SOURCE_KEY, value);
                }

/* 311 */       public static Date getLastRefreshDate(Bundle bundle) {
/* 312 */           Validate.notNull(bundle, "bundle");
/* 313 */           return getDate(bundle, LAST_REFRESH_DATE_KEY);
                }

/* 326 */       public static void putLastRefreshDate(Bundle bundle, Date value) {
/* 327 */           Validate.notNull(bundle, "bundle");
/* 328 */           Validate.notNull(value, Vine.Settings.PROP_VALUE);
/* 329 */           putDate(bundle, LAST_REFRESH_DATE_KEY, value);
                }

/* 341 */       public static long getLastRefreshMilliseconds(Bundle bundle) {
/* 342 */           Validate.notNull(bundle, "bundle");
/* 343 */           return bundle.getLong(LAST_REFRESH_DATE_KEY);
                }

/* 357 */       public static void putLastRefreshMilliseconds(Bundle bundle, long value) {
/* 358 */           Validate.notNull(bundle, "bundle");
/* 359 */           bundle.putLong(LAST_REFRESH_DATE_KEY, value);
                }

/* 362 */       static Date getDate(Bundle bundle, String key) {
/* 363 */           if (bundle == null) {
                        return null;
                    }
/* 367 */           long n = bundle.getLong(key, Long.MIN_VALUE);
                    if (n != Long.MIN_VALUE) {
/* 372 */               return new Date(n);
                    }
                    return null;
                }

/* 375 */       static void putDate(Bundle bundle, String key, Date date) {
/* 376 */           bundle.putLong(key, date.getTime());
                }
            }
