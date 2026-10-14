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
            
            
    public final class Utility {
                private static final String APPLICATION_FIELDS = "fields";
                public static final int DEFAULT_STREAM_BUFFER_SIZE = 8192;
                private static final String HASH_ALGORITHM_MD5 = "MD5";
                private static final String SUPPORTS_ATTRIBUTION = "supports_attribution";
                private static final String URL_SCHEME = "https";
                private static final Object LOCK = new Object();
                private static volatile boolean attributionAllowedForLastAppChecked = false;
                private static volatile String lastAppCheckedForAttributionStatus = "";

        public static <T> boolean isSubset(Collection<T> subset, Collection<T> superset) {
            if (superset == null || superset.size() == 0) {
                        return subset == null || subset.size() == 0;
                    }
            HashSet<T> hash = new HashSet<>((Collection<? extends T>) superset);
                    for (T t : subset) {
                if (!hash.contains(t)) {
                            return false;
                        }
                    }
                    return true;
                }

        public static <T> boolean isNullOrEmpty(Collection<T> c) {
                    return c == null || c.size() == 0;
                }

        public static boolean isNullOrEmpty(String s) {
                    return s == null || s.length() == 0;
                }

        public static <T> Collection<T> unmodifiableCollection(T... ts) {
            return Collections.unmodifiableCollection(Arrays.asList(ts));
                }

        public static <T> ArrayList<T> arrayList(T... ts) {
            ArrayList<T> arrayList = new ArrayList<>(ts.length);
            Collections.addAll(arrayList, ts);
            return arrayList;
                }

        static String md5hash(String key) {
                    try {
               MessageDigest hash = MessageDigest.getInstance(HASH_ALGORITHM_MD5);
               hash.update(key.getBytes());
               byte[] digest = hash.digest();
               StringBuilder builder = new StringBuilder();
                        for (int b : digest) {
                   builder.append(Integer.toHexString((b >> 4) & 15));
                   builder.append(Integer.toHexString((b >> 0) & 15));
                        }
                        return builder.toString();
                    } catch (NoSuchAlgorithmException e) {
               return null;
                    }
                }

       public static Uri buildUri(String authority, String path, Bundle parameters) {
           Uri.Builder builder = new Uri.Builder();
           builder.scheme(URL_SCHEME);
           builder.authority(authority);
           builder.path(path);
                    for (String key : parameters.keySet()) {
               Object parameter = parameters.get(key);
               if (parameter instanceof String) {
                   builder.appendQueryParameter(key, (String) parameter);
                        }
                    }
           return builder.build();
                }

       public static void putObjectInBundle(Bundle bundle, String key, Object value) {
           if (value instanceof String) {
               bundle.putString(key, (String) value);
                    } else if (value instanceof Parcelable) {
               bundle.putParcelable(key, (Parcelable) value);
                    } else {
               if (value instanceof byte[]) {
                   bundle.putByteArray(key, (byte[]) value);
                   return;
                        }
               throw new FacebookException("attempted to add unsupported type to Bundle");
                    }
                }

       public static void closeQuietly(Closeable closeable) {
           if (closeable != null) {
                        try {
                   closeable.close();
                        } catch (IOException e) {
                        }
                    }
                }

       public static void disconnectQuietly(URLConnection connection) {
           if (connection instanceof HttpURLConnection) {
               ((HttpURLConnection) connection).disconnect();
                    }
                }

       static Map<String, Object> convertJSONObjectToHashMap(JSONObject jsonObject) {
           HashMap<String, Object> map = new HashMap<>();
           JSONArray keys = jsonObject.names();
                    for (int i = 0; i < keys.length(); i++) {
                        try {
                   String key = keys.getString(i);
                   Object value = jsonObject.get(key);
                   if (value instanceof JSONObject) {
                       value = convertJSONObjectToHashMap((JSONObject) value);
                            }
                   map.put(key, value);
                        } catch (JSONException e) {
                        }
                    }
           return map;
                }

       public static Object getStringPropertyAsJSON(JSONObject jsonObject, String key, String nonJSONPropertyKey) throws JSONException {
           Object value = jsonObject.opt(key);
           if (value != null && (value instanceof String)) {
               JSONTokener tokener = new JSONTokener((String) value);
               value = tokener.nextValue();
                    }
           if (value == null || (value instanceof JSONObject) || (value instanceof JSONArray)) {
                        return value;
                    }
           if (nonJSONPropertyKey != null) {
               JSONObject jsonObject2 = new JSONObject();
               jsonObject2.putOpt(nonJSONPropertyKey, value);
               return jsonObject2;
                    }
           throw new FacebookException("Got an unexpected non-JSON object.");
                }

       public static String readStreamToString(InputStream inputStream) throws IOException {
           BufferedInputStream bufferedInputStream = null;
           InputStreamReader reader = null;
                    try {
               BufferedInputStream bufferedInputStream2 = new BufferedInputStream(inputStream);
                        try {
                   InputStreamReader reader2 = new InputStreamReader(bufferedInputStream2);
                            try {
                       StringBuilder stringBuilder = new StringBuilder();
                       char[] buffer = new char[2048];
                                while (true) {
                           int n = reader2.read(buffer);
                                    if (n != -1) {
                               stringBuilder.append(buffer, 0, n);
                                    } else {
                               String string = stringBuilder.toString();
                               closeQuietly(bufferedInputStream2);
                               closeQuietly(reader2);
                                        return string;
                                    }
                                }
                            } catch (IOException ex) {
                                reader = reader2;
                                bufferedInputStream = bufferedInputStream2;
                                closeQuietly(bufferedInputStream);
                       closeQuietly(reader);
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

       public static boolean stringsEqualOrEmpty(String a, String b) {
           boolean aEmpty = TextUtils.isEmpty(a);
           boolean bEmpty = TextUtils.isEmpty(b);
           if (aEmpty && bEmpty) {
               return true;
                    }
           if (!aEmpty && !bEmpty) {
               return a.equals(b);
                    }
           return false;
                }

       private static void clearCookiesForDomain(Context context, String domain) {
           CookieSyncManager syncManager = CookieSyncManager.createInstance(context);
           syncManager.sync();
           CookieManager cookieManager = CookieManager.getInstance();
           String cookies = null;
                    try {
               cookies = cookieManager.getCookie(domain);
                    } catch (IllegalArgumentException e) {
               if (!CommonUtil.isDoingRobolectricTest()) {
                   throw e;
                        }
                    }
           if (cookies != null) {
               String[] splitCookies = cookies.split(";");
                        for (String cookie : splitCookies) {
                   String[] cookieParts = cookie.split("=");
                   if (cookieParts.length > 0) {
                       String newCookie = cookieParts[0].trim() + "=;expires=Sat, 1 Jan 2000 00:00:01 UTC;";
                       cookieManager.setCookie(domain, newCookie);
                            }
                        }
               cookieManager.removeExpiredCookie();
                    }
                }

       public static void clearFacebookCookies(Context context) {
           clearCookiesForDomain(context, "facebook.com");
           clearCookiesForDomain(context, ".facebook.com");
           clearCookiesForDomain(context, "https://facebook.com");
           clearCookiesForDomain(context, "https://.facebook.com");
                }

       public static void logd(String tag, Exception e) {
                }

       public static void logd(String tag, String msg) {
                }

       public static boolean queryAppAttributionSupportAndWait(String applicationId) {
                    boolean z;
                    synchronized (LOCK) {
                        try {
                   if (applicationId.equals(lastAppCheckedForAttributionStatus)) {
                       z = attributionAllowedForLastAppChecked;
                            } else {
                       Bundle supportsAttributionParams = new Bundle();
                       supportsAttributionParams.putString(APPLICATION_FIELDS, SUPPORTS_ATTRIBUTION);
                       Request pingRequest = Request.newGraphPathRequest(null, applicationId, null);
                       pingRequest.setParameters(supportsAttributionParams);
                       GraphObject supportResponse = pingRequest.executeAndWait().getGraphObject();
                       Object property = false;
                       if (supportResponse != null) {
                           property = supportResponse.getProperty(SUPPORTS_ATTRIBUTION);
                                }
                       Object obj = !(property instanceof Boolean) ? false : property;
                       lastAppCheckedForAttributionStatus = applicationId;
                       attributionAllowedForLastAppChecked = ((Boolean) obj).booleanValue();
                       z = attributionAllowedForLastAppChecked;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return z;
                }
            }
