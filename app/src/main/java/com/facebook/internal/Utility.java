            package com.facebook.internal;

            import android.content.Context;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.Parcelable;
            import android.text.TextUtils;
            import android.webkit.CookieManager;
            import android.webkit.CookieSyncManager;
            import co.vine.android.util.CommonUtil;
            import com.facebook.FacebookException;
            import com.facebook.Request;
            import com.facebook.model.GraphObject;
            import java.io.BufferedInputStream;
            import java.io.Closeable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.net.HttpURLConnection;
            import java.net.URLConnection;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collection;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Map;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            import org.json.JSONTokener;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 66 */    public final class Utility {
                private static final String APPLICATION_FIELDS = "fields";
                public static final int DEFAULT_STREAM_BUFFER_SIZE = 8192;
                private static final String HASH_ALGORITHM_MD5 = "MD5";
                private static final String SUPPORTS_ATTRIBUTION = "supports_attribution";
                private static final String URL_SCHEME = "https";
                private static final Object LOCK = new Object();
                private static volatile boolean attributionAllowedForLastAppChecked = false;
                private static volatile String lastAppCheckedForAttributionStatus = "";

/* 67 */        public static <T> boolean isSubset(Collection<T> subset, Collection<T> superset) {
/* 68 */            if (superset == null || superset.size() == 0) {
                        return subset == null || subset.size() == 0;
                    }
/* 72 */            HashSet<T> hash = new HashSet<>((Collection<? extends T>) superset);
                    for (T t : subset) {
/* 74 */                if (!hash.contains(t)) {
                            return false;
                        }
                    }
                    return true;
                }

/* 81 */        public static <T> boolean isNullOrEmpty(Collection<T> c) {
                    return c == null || c.size() == 0;
                }

/* 85 */        public static boolean isNullOrEmpty(String s) {
                    return s == null || s.length() == 0;
                }

/* 89 */        public static <T> Collection<T> unmodifiableCollection(T... ts) {
/* 90 */            return Collections.unmodifiableCollection(Arrays.asList(ts));
                }

/* 93 */        public static <T> ArrayList<T> arrayList(T... ts) {
/* 94 */            ArrayList<T> arrayList = new ArrayList<>(ts.length);
/* 95 */            Collections.addAll(arrayList, ts);
/* 96 */            return arrayList;
                }

/* 99 */        static String md5hash(String key) {
                    try {
/* 102 */               MessageDigest hash = MessageDigest.getInstance(HASH_ALGORITHM_MD5);
/* 107 */               hash.update(key.getBytes());
/* 108 */               byte[] digest = hash.digest();
/* 109 */               StringBuilder builder = new StringBuilder();
                        for (int b : digest) {
/* 111 */                   builder.append(Integer.toHexString((b >> 4) & 15));
/* 112 */                   builder.append(Integer.toHexString((b >> 0) & 15));
                        }
                        return builder.toString();
                    } catch (NoSuchAlgorithmException e) {
/* 104 */               return null;
                    }
                }

/* 117 */       public static Uri buildUri(String authority, String path, Bundle parameters) {
/* 118 */           Uri.Builder builder = new Uri.Builder();
/* 119 */           builder.scheme(URL_SCHEME);
/* 120 */           builder.authority(authority);
/* 121 */           builder.path(path);
                    for (String key : parameters.keySet()) {
/* 123 */               Object parameter = parameters.get(key);
/* 124 */               if (parameter instanceof String) {
/* 125 */                   builder.appendQueryParameter(key, (String) parameter);
                        }
                    }
/* 128 */           return builder.build();
                }

/* 131 */       public static void putObjectInBundle(Bundle bundle, String key, Object value) {
/* 132 */           if (value instanceof String) {
/* 133 */               bundle.putString(key, (String) value);
                    } else if (value instanceof Parcelable) {
/* 135 */               bundle.putParcelable(key, (Parcelable) value);
                    } else {
/* 136 */               if (value instanceof byte[]) {
/* 137 */                   bundle.putByteArray(key, (byte[]) value);
/* 141 */                   return;
                        }
/* 139 */               throw new FacebookException("attempted to add unsupported type to Bundle");
                    }
                }

/* 144 */       public static void closeQuietly(Closeable closeable) {
/* 145 */           if (closeable != null) {
                        try {
/* 146 */                   closeable.close();
                        } catch (IOException e) {
                        }
                    }
                }

/* 153 */       public static void disconnectQuietly(URLConnection connection) {
/* 154 */           if (connection instanceof HttpURLConnection) {
/* 155 */               ((HttpURLConnection) connection).disconnect();
                    }
                }

/* 159 */       static Map<String, Object> convertJSONObjectToHashMap(JSONObject jsonObject) {
/* 160 */           HashMap<String, Object> map = new HashMap<>();
/* 161 */           JSONArray keys = jsonObject.names();
                    for (int i = 0; i < keys.length(); i++) {
                        try {
/* 165 */                   String key = keys.getString(i);
/* 166 */                   Object value = jsonObject.get(key);
/* 167 */                   if (value instanceof JSONObject) {
/* 168 */                       value = convertJSONObjectToHashMap((JSONObject) value);
                            }
/* 170 */                   map.put(key, value);
                        } catch (JSONException e) {
                        }
                    }
/* 174 */           return map;
                }

/* 179 */       public static Object getStringPropertyAsJSON(JSONObject jsonObject, String key, String nonJSONPropertyKey) throws JSONException {
/* 180 */           Object value = jsonObject.opt(key);
/* 181 */           if (value != null && (value instanceof String)) {
/* 182 */               JSONTokener tokener = new JSONTokener((String) value);
/* 183 */               value = tokener.nextValue();
                    }
/* 186 */           if (value == null || (value instanceof JSONObject) || (value instanceof JSONArray)) {
                        return value;
                    }
/* 187 */           if (nonJSONPropertyKey != null) {
/* 193 */               JSONObject jsonObject2 = new JSONObject();
/* 194 */               jsonObject2.putOpt(nonJSONPropertyKey, value);
/* 201 */               return jsonObject2;
                    }
/* 197 */           throw new FacebookException("Got an unexpected non-JSON object.");
                }

/* 205 */       public static String readStreamToString(InputStream inputStream) throws IOException {
/* 206 */           BufferedInputStream bufferedInputStream = null;
/* 207 */           InputStreamReader reader = null;
                    try {
/* 209 */               BufferedInputStream bufferedInputStream2 = new BufferedInputStream(inputStream);
                        try {
/* 210 */                   InputStreamReader reader2 = new InputStreamReader(bufferedInputStream2);
                            try {
/* 211 */                       StringBuilder stringBuilder = new StringBuilder();
/* 214 */                       char[] buffer = new char[2048];
                                while (true) {
/* 216 */                           int n = reader2.read(buffer);
                                    if (n != -1) {
/* 217 */                               stringBuilder.append(buffer, 0, n);
                                    } else {
/* 220 */                               String string = stringBuilder.toString();
/* 222 */                               closeQuietly(bufferedInputStream2);
/* 223 */                               closeQuietly(reader2);
                                        return string;
                                    }
                                }
                            } catch (IOException ex) {
                                reader = reader2;
                                bufferedInputStream = bufferedInputStream2;
                                closeQuietly(bufferedInputStream);
/* 223 */                       closeQuietly(reader);
                                throw ex;
                            }
                        } catch (IOException ex) {
                            closeQuietly(bufferedInputStream2);
                            throw ex;
                        }
                    } catch (RuntimeException ex) {
                        throw ex;
                    }
                }

/* 227 */       public static boolean stringsEqualOrEmpty(String a, String b) {
/* 228 */           boolean aEmpty = TextUtils.isEmpty(a);
/* 229 */           boolean bEmpty = TextUtils.isEmpty(b);
/* 231 */           if (aEmpty && bEmpty) {
/* 233 */               return true;
                    }
/* 235 */           if (!aEmpty && !bEmpty) {
/* 237 */               return a.equals(b);
                    }
/* 240 */           return false;
                }

/* 245 */       private static void clearCookiesForDomain(Context context, String domain) {
/* 246 */           CookieSyncManager syncManager = CookieSyncManager.createInstance(context);
/* 247 */           syncManager.sync();
/* 249 */           CookieManager cookieManager = CookieManager.getInstance();
/* 251 */           String cookies = null;
                    try {
/* 254 */               cookies = cookieManager.getCookie(domain);
                    } catch (IllegalArgumentException e) {
/* 256 */               if (!CommonUtil.isDoingRobolectricTest()) {
/* 257 */                   throw e;
                        }
                    }
/* 260 */           if (cookies != null) {
/* 264 */               String[] splitCookies = cookies.split(";");
                        for (String cookie : splitCookies) {
/* 266 */                   String[] cookieParts = cookie.split("=");
/* 267 */                   if (cookieParts.length > 0) {
/* 268 */                       String newCookie = cookieParts[0].trim() + "=;expires=Sat, 1 Jan 2000 00:00:01 UTC;";
/* 269 */                       cookieManager.setCookie(domain, newCookie);
                            }
                        }
/* 272 */               cookieManager.removeExpiredCookie();
                    }
                }

/* 277 */       public static void clearFacebookCookies(Context context) {
/* 278 */           clearCookiesForDomain(context, "facebook.com");
/* 279 */           clearCookiesForDomain(context, ".facebook.com");
/* 280 */           clearCookiesForDomain(context, "https://facebook.com");
/* 281 */           clearCookiesForDomain(context, "https://.facebook.com");
                }

/* 287 */       public static void logd(String tag, Exception e) {
                }

/* 293 */       public static void logd(String tag, String msg) {
                }

/* 297 */       public static boolean queryAppAttributionSupportAndWait(String applicationId) {
                    boolean z;
                    synchronized (LOCK) {
                        try {
/* 301 */                   if (applicationId.equals(lastAppCheckedForAttributionStatus)) {
/* 302 */                       z = attributionAllowedForLastAppChecked;
                            } else {
/* 305 */                       Bundle supportsAttributionParams = new Bundle();
/* 306 */                       supportsAttributionParams.putString(APPLICATION_FIELDS, SUPPORTS_ATTRIBUTION);
/* 307 */                       Request pingRequest = Request.newGraphPathRequest(null, applicationId, null);
/* 308 */                       pingRequest.setParameters(supportsAttributionParams);
/* 310 */                       GraphObject supportResponse = pingRequest.executeAndWait().getGraphObject();
/* 312 */                       Object property = false;
/* 313 */                       if (supportResponse != null) {
/* 314 */                           property = supportResponse.getProperty(SUPPORTS_ATTRIBUTION);
                                }
/* 319 */                       Object obj = !(property instanceof Boolean) ? false : property;
/* 322 */                       lastAppCheckedForAttributionStatus = applicationId;
/* 323 */                       attributionAllowedForLastAppChecked = ((Boolean) obj).booleanValue();
/* 324 */                       z = attributionAllowedForLastAppChecked;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 324 */           return z;
                }
            }
