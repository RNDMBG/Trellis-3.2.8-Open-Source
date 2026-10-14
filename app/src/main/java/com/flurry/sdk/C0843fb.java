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
            
            /* JADX INFO: renamed from: com.flurry.sdk.fb */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public final class C0843fb {

                /* JADX INFO: renamed from: a */
                private static final String f848a = C0843fb.class.getSimpleName();

                /* JADX INFO: renamed from: a */
/* 44 */        public static String m959a(String str) {
/* 45 */            return m960a(str, 255);
                }

                /* JADX INFO: renamed from: a */
/* 49 */        public static String m960a(String str, int i) {
/* 50 */            if (str == null) {
/* 52 */                return "";
                    }
                    return str.length() > i ? str.substring(0, i) : str;
                }

                /* JADX INFO: renamed from: b */
/* 67 */        public static String m965b(String str) {
                    try {
/* 68 */                return URLEncoder.encode(str, IoUtils.UTF_8);
                    } catch (UnsupportedEncodingException e) {
/* 72 */                C0827el.m885a(5, f848a, "Cannot encode '" + str + "'");
/* 73 */                return "";
                    }
                }

                /* JADX INFO: renamed from: c */
/* 80 */        public static String m967c(String str) {
                    try {
/* 81 */                return URLDecoder.decode(str, IoUtils.UTF_8);
                    } catch (UnsupportedEncodingException e) {
/* 85 */                C0827el.m885a(5, f848a, "Cannot decode '" + str + "'");
/* 86 */                return "";
                    }
                }

                /* JADX INFO: renamed from: a */
/* 93 */        public static void m962a(Closeable closeable) {
/* 94 */            if (closeable != null) {
                        try {
/* 96 */                    closeable.close();
                        } catch (Throwable th) {
                        }
                    }
                }

                /* JADX INFO: renamed from: d */
/* 112 */       public static byte[] m968d(String str) {
                    try {
/* 113 */               MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
/* 114 */               messageDigest.update(str.getBytes(), 0, str.length());
/* 115 */               return messageDigest.digest();
                    } catch (NoSuchAlgorithmException e) {
/* 119 */               C0827el.m885a(6, f848a, "Unsupported SHA1: " + e.getMessage());
/* 120 */               return null;
                    }
                }

                /* JADX INFO: renamed from: a */
/* 125 */       public static String m961a(byte[] bArr) {
/* 126 */           StringBuilder sb = new StringBuilder(bArr.length * 2);
/* 127 */           char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                    for (byte b : bArr) {
/* 132 */               sb.append(cArr[(byte) ((b & 240) >> 4)]);
/* 133 */               sb.append(cArr[(byte) (b & 15)]);
                    }
/* 135 */           return sb.toString();
                }

                /* JADX INFO: renamed from: a */
/* 140 */       public static boolean m963a(long j) {
                    if (j != 0 && System.currentTimeMillis() > j) {
/* 141 */               return false;
                    }
/* 145 */           return true;
                }

                /* JADX INFO: renamed from: a */
/* 192 */       public static boolean m964a(Intent intent) {
                    return C0801dl.m732a().m739c().queryIntentActivities(intent, 65536).size() > 0;
                }

                /* JADX INFO: renamed from: b */
/* 198 */       public static boolean m966b(Intent intent) {
/* 200 */           if (intent == null) {
/* 199 */               return false;
                    }
/* 202 */           return C0801dl.m732a().m736b().getPackageName().equals(intent.resolveActivity(C0801dl.m732a().m739c()).getPackageName());
                }

                /* JADX INFO: renamed from: e */
/* 208 */       public static String m969e(String str) {
/* 209 */           return str.replace("'", "\\'").replace("\\n", "").replace("\\r", "").replace("\\t", "");
                }

                /* JADX INFO: renamed from: f */
/* 213 */       public static Map<String, String> m970f(String str) {
/* 214 */           HashMap map = new HashMap();
/* 215 */           if (!TextUtils.isEmpty(str)) {
                        for (String str2 : str.split("&")) {
/* 219 */                   String[] strArrSplit = str2.split("=");
/* 220 */                   if (!strArrSplit[0].equals("event")) {
/* 222 */                       map.put(m967c(strArrSplit[0]), m967c(strArrSplit[1]));
                            }
                        }
                    }
/* 226 */           return map;
                }

                /* JADX INFO: renamed from: g */
/* 229 */       public static long m971g(String str) {
/* 230 */           if (str == null) {
/* 231 */               return 0L;
                    }
/* 235 */           int length = str.length();
/* 234 */           long j = 1125899906842597L;
/* 237 */           int i = 0;
                    while (i < length) {
/* 238 */               long jCharAt = ((long) str.charAt(i)) + (j * 31);
/* 237 */               i++;
                        j = jCharAt;
                    }
/* 241 */           return j;
                }

                /* JADX INFO: renamed from: a */
/* 244 */       public static long m958a(InputStream inputStream, OutputStream outputStream) throws IOException {
/* 245 */           byte[] bArr = new byte[1024];
/* 247 */           long j = 0;
                    while (true) {
/* 249 */               int i = inputStream.read(bArr);
/* 250 */               if (i >= 0) {
/* 254 */                   outputStream.write(bArr, 0, i);
/* 255 */                   j += (long) i;
                        } else {
/* 258 */                   return j;
                        }
                    }
                }
            }
