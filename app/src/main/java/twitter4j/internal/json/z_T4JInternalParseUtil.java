            package twitter4j.internal.json;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.UnsupportedEncodingException;
            import java.net.URLDecoder;
            import java.text.ParseException;
            import java.text.SimpleDateFormat;
            import java.util.Date;
            import java.util.HashMap;
            import java.util.Locale;
            import java.util.Map;
            import java.util.TimeZone;
            import java.util.concurrent.LinkedBlockingQueue;
            import org.json.JSONException;
            import org.json.JSONObject;
            import twitter4j.TwitterException;
            import twitter4j.internal.http.HttpResponse;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public final class z_T4JInternalParseUtil {
                private static final Map<String, LinkedBlockingQueue<SimpleDateFormat>> formatMapQueue = new HashMap();

/* 38 */        private z_T4JInternalParseUtil() {
/* 41 */            throw new AssertionError();
                }

/* 44 */        static String getUnescapedString(String str, JSONObject json) {
/* 45 */            return HTMLEntity.unescape(getRawString(str, json));
                }

/* 49 */        public static String getRawString(String name, JSONObject json) {
                    try {
/* 50 */                if (json.isNull(name)) {
                            return null;
                        }
/* 53 */                return json.getString(name);
                    } catch (JSONException e) {
                        return null;
                    }
                }

/* 60 */        static String getURLDecodedString(String name, JSONObject json) {
/* 61 */            String returnValue = getRawString(name, json);
/* 62 */            if (returnValue != null) {
                        try {
/* 64 */                    return URLDecoder.decode(returnValue, IoUtils.UTF_8);
                        } catch (UnsupportedEncodingException e) {
/* 68 */                    return returnValue;
                        }
                    }
/* 68 */            return returnValue;
                }

/* 72 */        public static Date parseTrendsDate(String asOfStr) throws TwitterException {
                    switch (asOfStr.length()) {
                        case 10:
/* 75 */                    Date parsed = new Date(Long.parseLong(asOfStr) * 1000);
/* 83 */                    return parsed;
                        case 20:
/* 78 */                    Date parsed2 = getDate(asOfStr, "yyyy-MM-dd'T'HH:mm:ss'Z'");
/* 83 */                    return parsed2;
                        default:
/* 81 */                    Date parsed3 = getDate(asOfStr, "EEE, d MMM yyyy HH:mm:ss z");
/* 83 */                    return parsed3;
                    }
                }

/* 87 */        public static Date getDate(String name, JSONObject json) throws TwitterException {
/* 88 */            return getDate(name, json, "EEE MMM d HH:mm:ss z yyyy");
                }

/* 91 */        public static Date getDate(String name, JSONObject json, String format) throws TwitterException {
/* 92 */            String dateStr = getUnescapedString(name, json);
/* 93 */            if ("null".equals(dateStr) || dateStr == null) {
/* 94 */                return null;
                    }
                    return getDate(dateStr, format);
                }

/* 102 */       public static Date getDate(String dateString, String format) throws TwitterException {
/* 103 */           LinkedBlockingQueue<SimpleDateFormat> simpleDateFormats = formatMapQueue.get(format);
/* 104 */           if (simpleDateFormats == null) {
/* 105 */               simpleDateFormats = new LinkedBlockingQueue<>();
/* 106 */               formatMapQueue.put(format, simpleDateFormats);
                    }
/* 108 */           SimpleDateFormat sdf = simpleDateFormats.poll();
/* 109 */           if (sdf == null) {
/* 110 */               sdf = new SimpleDateFormat(format, Locale.US);
/* 111 */               sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    }
                    try {
                        try {
/* 114 */                   Date date = sdf.parse(dateString);
                            try {
/* 119 */                       simpleDateFormats.put(sdf);
                            } catch (InterruptedException e) {
                            }
/* 123 */                   return date;
                        } catch (ParseException pe) {
/* 116 */                   throw new TwitterException("Unexpected date format(" + dateString + ") returned from twitter.com", pe);
                        }
                    } catch (Throwable th) {
                        try {
/* 119 */                   simpleDateFormats.put(sdf);
                        } catch (InterruptedException e2) {
                        }
/* 123 */               throw th;
                    }
                }

/* 127 */       public static int getInt(String name, JSONObject json) {
/* 128 */           return getInt(getRawString(name, json));
                }

/* 131 */       public static int getInt(String str) {
/* 132 */           if (str == null || "".equals(str) || "null".equals(str)) {
                        return -1;
                    }
                    try {
/* 136 */               return Integer.valueOf(str).intValue();
                    } catch (NumberFormatException e) {
                        return -1;
                    }
                }

/* 144 */       public static long getLong(String name, JSONObject json) {
/* 145 */           return getLong(getRawString(name, json));
                }

/* 148 */       public static long getLong(String str) {
/* 149 */           if (str == null || "".equals(str) || "null".equals(str)) {
/* 150 */               return -1L;
                    }
/* 153 */           if (str.endsWith("+")) {
/* 155 */               return Long.valueOf(str.substring(0, str.length() - 1)).longValue() + 1;
                    }
/* 157 */           return Long.valueOf(str).longValue();
                }

/* 161 */       public static double getDouble(String name, JSONObject json) {
/* 162 */           String str2 = getRawString(name, json);
/* 163 */           if (str2 == null || "".equals(str2) || "null".equals(str2)) {
/* 164 */               return -1.0d;
                    }
                    return Double.valueOf(str2).doubleValue();
                }

/* 170 */       public static boolean getBoolean(String name, JSONObject json) {
/* 171 */           String str = getRawString(name, json);
/* 172 */           if (str == null || "null".equals(str)) {
/* 173 */               return false;
                    }
                    return Boolean.valueOf(str).booleanValue();
                }

/* 179 */       public static int toAccessLevel(HttpResponse res) {
/* 180 */           if (res == null) {
/* 181 */               return -1;
                    }
/* 183 */           String xAccessLevel = res.getResponseHeader("X-Access-Level");
/* 185 */           if (xAccessLevel == null) {
/* 186 */               return 0;
                    }
                    switch (xAccessLevel.length()) {
                        case 4:
/* 192 */                   return 1;
                        case 10:
/* 196 */                   return 2;
                        case 25:
/* 200 */                   return 3;
                        case 26:
/* 204 */                   return 3;
                        default:
/* 207 */                   return 0;
                    }
                }
            }
