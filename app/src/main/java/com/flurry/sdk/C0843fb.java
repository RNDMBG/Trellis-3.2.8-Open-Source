            package com.flurry.sdk;

            import android.content.Intent;
            import android.text.TextUtils;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.Closeable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.io.UnsupportedEncodingException;
            import java.net.URLDecoder;
            import java.net.URLEncoder;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.util.HashMap;
            import java.util.Map;
            
            
            
    public final class C0843fb {

                
                private static final String f848a = C0843fb.class.getSimpleName();

                
        public static String m959a(String str) {
            return m960a(str, 255);
                }

                
        public static String m960a(String str, int i) {
            if (str == null) {
                return "";
                    }
                    return str.length() > i ? str.substring(0, i) : str;
                }

                
        public static String m965b(String str) {
                    try {
                return URLEncoder.encode(str, IoUtils.UTF_8);
                    } catch (UnsupportedEncodingException e) {
                C0827el.m885a(5, f848a, "Cannot encode '" + str + "'");
                return "";
                    }
                }

                
        public static String m967c(String str) {
                    try {
                return URLDecoder.decode(str, IoUtils.UTF_8);
                    } catch (UnsupportedEncodingException e) {
                C0827el.m885a(5, f848a, "Cannot decode '" + str + "'");
                return "";
                    }
                }

                
        public static void m962a(Closeable closeable) {
            if (closeable != null) {
                        try {
                    closeable.close();
                        } catch (Throwable th) {
                        }
                    }
                }

                
       public static byte[] m968d(String str) {
                    try {
               MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
               messageDigest.update(str.getBytes(), 0, str.length());
               return messageDigest.digest();
                    } catch (NoSuchAlgorithmException e) {
               C0827el.m885a(6, f848a, "Unsupported SHA1: " + e.getMessage());
               return null;
                    }
                }

                
       public static String m961a(byte[] bArr) {
           StringBuilder sb = new StringBuilder(bArr.length * 2);
           char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                    for (byte b : bArr) {
               sb.append(cArr[(byte) ((b & 240) >> 4)]);
               sb.append(cArr[(byte) (b & 15)]);
                    }
           return sb.toString();
                }

                
       public static boolean m963a(long j) {
                    if (j != 0 && System.currentTimeMillis() > j) {
               return false;
                    }
           return true;
                }

                
       public static boolean m964a(Intent intent) {
                    return C0801dl.m732a().m739c().queryIntentActivities(intent, 65536).size() > 0;
                }

                
       public static boolean m966b(Intent intent) {
           if (intent == null) {
               return false;
                    }
           return C0801dl.m732a().m736b().getPackageName().equals(intent.resolveActivity(C0801dl.m732a().m739c()).getPackageName());
                }

                
       public static String m969e(String str) {
           return str.replace("'", "\\'").replace("\\n", "").replace("\\r", "").replace("\\t", "");
                }

                
       public static Map<String, String> m970f(String str) {
           HashMap map = new HashMap();
           if (!TextUtils.isEmpty(str)) {
                        for (String str2 : str.split("&")) {
                   String[] strArrSplit = str2.split("=");
                   if (!strArrSplit[0].equals("event")) {
                       map.put(m967c(strArrSplit[0]), m967c(strArrSplit[1]));
                            }
                        }
                    }
           return map;
                }

                
       public static long m971g(String str) {
           if (str == null) {
               return 0L;
                    }
           int length = str.length();
           long j = 1125899906842597L;
           int i = 0;
                    while (i < length) {
               long jCharAt = ((long) str.charAt(i)) + (j * 31);
               i++;
                        j = jCharAt;
                    }
           return j;
                }

                
       public static long m958a(InputStream inputStream, OutputStream outputStream) throws IOException {
           byte[] bArr = new byte[1024];
           long j = 0;
                    while (true) {
               int i = inputStream.read(bArr);
               if (i >= 0) {
                   outputStream.write(bArr, 0, i);
                   j += (long) i;
                        } else {
                   return j;
                        }
                    }
                }
            }
