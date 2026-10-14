            package com.crashlytics.android.internal;

            import android.app.ActivityManager;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.SharedPreferences;
            import android.content.res.Resources;
            import android.hardware.SensorManager;
            import android.os.Build;
            import android.os.Debug;
            import android.os.StatFs;
            import android.provider.Settings;
            import android.text.TextUtils;
            import co.vine.android.HomeTabActivity;
            import com.crashlytics.android.Crashlytics;
            import com.flurry.android.Constants;
            import java.io.BufferedReader;
            import java.io.Closeable;
            import java.io.File;
            import java.io.FileReader;
            import java.io.Flushable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.security.GeneralSecurityException;
            import java.security.InvalidKeyException;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Comparator;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Locale;
            import java.util.Scanner;
            import java.util.regex.Pattern;
            import javax.crypto.Cipher;
            import javax.crypto.spec.SecretKeySpec;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.ab */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 99 */    public final class C0681ab {

                /* JADX INFO: renamed from: b */
                private static Boolean f350b = null;

                /* JADX INFO: renamed from: c */
                private static final char[] f351c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

                /* JADX INFO: renamed from: d */
                private static long f352d = -1;

                /* JADX INFO: renamed from: e */
                private static Boolean f353e = null;

                /* JADX INFO: renamed from: a */
                public static final Comparator<File> f349a = new C0682ac();

                /* JADX INFO: renamed from: a */
/* 100 */       public static SharedPreferences m200a() {
/* 101 */           return C0727v.m357a().getContext().getSharedPreferences("com.crashlytics.prefs", 0);
                }

                /* JADX INFO: renamed from: a */
/* 110 */       private static String m203a(File file, String str) throws Throwable {
                    BufferedReader bufferedReader;
                    Throwable th;
                    String str2 = null;
/* 111 */           if (file.exists()) {
                        try {
/* 115 */                   bufferedReader = new BufferedReader(new FileReader(file), 1024);
                            while (true) {
                                try {
                                    try {
/* 117 */                               String line = bufferedReader.readLine();
                                        if (line == null) {
                                            break;
                                        }
/* 119 */                               String[] strArrSplit = Pattern.compile("\\s*:\\s*").split(line, 2);
/* 120 */                               if (strArrSplit.length > 1 && strArrSplit[0].equals(str)) {
/* 121 */                                   str2 = strArrSplit[1];
                                            break;
                                        }
                                    } catch (Exception e) {
/* 126 */                               e = e;
/* 127 */                               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Error parsing " + file, e);
/* 129 */                               m210a(bufferedReader, "Failed to close system file reader.");
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    m210a(bufferedReader, "Failed to close system file reader.");
                                    throw th;
                                }
                            }
/* 129 */                   m210a(bufferedReader, "Failed to close system file reader.");
                        } catch (Exception e2) {
/* 126 */                   e = e2;
                            bufferedReader = null;
                        } catch (Throwable th3) {
                            bufferedReader = null;
                            th = th3;
                            m210a(bufferedReader, "Failed to close system file reader.");
                            throw th;
                        }
                    }
/* 132 */           return str2;
                }

                /* JADX INFO: renamed from: b */
/* 139 */       public static int m215b() {
/* 140 */           return EnumC0683ad.m234a().ordinal();
                }

                /* JADX INFO: renamed from: c */
/* 191 */       public static synchronized long m220c() {
                    long jM198a;
                    try {
                        if (f352d == -1) {
/* 194 */                   String strM203a = m203a(new File("/proc/meminfo"), "MemTotal");
/* 196 */                   if (TextUtils.isEmpty(strM203a)) {
/* 193 */                       jM198a = 0;
/* 217 */                       f352d = jM198a;
                            } else {
/* 197 */                       String upperCase = strM203a.toUpperCase(Locale.US);
                                try {
/* 200 */                           if (upperCase.endsWith("KB")) {
/* 201 */                               jM198a = m198a(upperCase, "KB", 1024);
                                    } else if (upperCase.endsWith("MB")) {
/* 205 */                               jM198a = m198a(upperCase, "MB", 1048576);
                                    } else if (upperCase.endsWith("GB")) {
/* 209 */                               jM198a = m198a(upperCase, "GB", 1073741824);
                                    } else {
/* 211 */                               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Unexpected meminfo format while computing RAM: " + upperCase);
/* 193 */                               jM198a = 0;
                                    }
                                } catch (NumberFormatException e) {
/* 214 */                           C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Unexpected meminfo format while computing RAM: " + upperCase, e);
/* 193 */                           jM198a = 0;
                                }
/* 217 */                       f352d = jM198a;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 219 */           return f352d;
                }

                /* JADX INFO: renamed from: a */
/* 226 */       private static long m198a(String str, String str2, int i) {
/* 227 */           return Long.parseLong(str.split(str2)[0].trim()) * ((long) i);
                }

                /* JADX INFO: renamed from: a */
/* 231 */       public static ActivityManager.RunningAppProcessInfo m199a(String str, Context context) {
/* 233 */           List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY)).getRunningAppProcesses();
/* 236 */           if (runningAppProcesses != null) {
                        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
/* 238 */                   if (runningAppProcessInfo.processName.equals(str)) {
/* 244 */                       return runningAppProcessInfo;
                            }
                        }
                    }
/* 234 */           return null;
                }

                /* JADX INFO: renamed from: a */
/* 250 */       public static String m204a(InputStream inputStream) throws IOException {
/* 251 */           Scanner scannerUseDelimiter = new Scanner(inputStream).useDelimiter("\\A");
                    return scannerUseDelimiter.hasNext() ? scannerUseDelimiter.next() : "";
                }

                /* JADX INFO: renamed from: a */
/* 269 */       public static String m205a(String str) {
/* 270 */           return m207a(str.getBytes(), "SHA-1");
                }

                /* JADX INFO: renamed from: b */
/* 282 */       private static String m218b(InputStream inputStream) {
                    try {
/* 283 */               MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
/* 285 */               byte[] bArr = new byte[1024];
                        while (true) {
/* 289 */                   int i = inputStream.read(bArr);
                            if (i != -1) {
/* 290 */                       messageDigest.update(bArr, 0, i);
                            } else {
/* 293 */                       return m206a(messageDigest.digest());
                            }
                        }
                    } catch (Exception e) {
/* 295 */               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not calculate hash for app icon.", e);
/* 298 */               return "";
                    }
                }

                /* JADX INFO: renamed from: a */
/* 301 */       private static String m207a(byte[] bArr, String str) {
                    try {
/* 302 */               MessageDigest messageDigest = MessageDigest.getInstance(str);
/* 314 */               messageDigest.update(bArr);
/* 316 */               return m206a(messageDigest.digest());
                    } catch (NoSuchAlgorithmException e) {
/* 307 */               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not create hashing algorithm: " + str + ", returning empty string.", e);
/* 308 */               return "";
                    }
                }

                /* JADX INFO: renamed from: a */
/* 328 */       public static String m208a(String... strArr) {
/* 329 */           if (strArr == null || strArr.length == 0) {
                        return null;
                    }
/* 334 */           ArrayList arrayList = new ArrayList();
                    for (String str : strArr) {
/* 338 */               if (str != null) {
/* 340 */                   arrayList.add(str.replace("-", "").toLowerCase(Locale.US));
                        }
                    }
/* 345 */           Collections.sort(arrayList);
/* 348 */           StringBuilder sb = new StringBuilder();
/* 349 */           Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
/* 350 */               sb.append((String) it.next());
                    }
/* 353 */           String string = sb.toString();
/* 357 */           if (string.length() > 0) {
                        return m205a(string);
                    }
                    return null;
                }

                /* JADX INFO: renamed from: a */
/* 366 */       public static long m197a(Context context) {
/* 367 */           ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
/* 368 */           ((ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY)).getMemoryInfo(memoryInfo);
/* 369 */           return memoryInfo.availMem;
                }

                /* JADX INFO: renamed from: b */
/* 379 */       public static long m216b(String str) {
/* 380 */           StatFs statFs = new StatFs(str);
/* 381 */           long blockSize = statFs.getBlockSize();
/* 384 */           return (((long) statFs.getBlockCount()) * blockSize) - (((long) statFs.getAvailableBlocks()) * blockSize);
                }

                /* JADX INFO: renamed from: b */
/* 388 */       public static float m214b(Context context) {
/* 390 */           Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
/* 393 */           return intentRegisterReceiver.getIntExtra("level", -1) / intentRegisterReceiver.getIntExtra("scale", -1);
                }

                /* JADX INFO: renamed from: c */
/* 398 */       public static boolean m222c(Context context) {
                    return (m224d() || ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(8) == null) ? false : true;
                }

                /* JADX INFO: renamed from: c */
/* 414 */       public static void m221c(String str) {
/* 415 */           if (m227e(C0727v.m357a().getContext())) {
/* 416 */               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, str);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 424 */       public static void m223d(String str) {
/* 425 */           if (m227e(C0727v.m357a().getContext())) {
/* 426 */               C0727v.m357a().m365b().mo100d(Crashlytics.TAG, str);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 434 */       public static void m209a(int i, String str) {
/* 435 */           if (m227e(C0727v.m357a().getContext())) {
/* 436 */               C0727v.m357a().m365b().mo94a(4, Crashlytics.TAG, str);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 452 */       public static boolean m225d(Context context) {
/* 453 */           if (f353e == null) {
                        f353e = Boolean.valueOf(m213a(context, "com.crashlytics.SilenceCrashlyticsLogCat", false) ? false : true);
                    }
/* 458 */           return f353e.booleanValue();
                }

                /* JADX INFO: renamed from: e */
/* 464 */       public static boolean m227e(Context context) {
/* 465 */           if (f350b == null) {
/* 466 */               f350b = Boolean.valueOf(m213a(context, "com.crashlytics.Trace", false));
                    }
/* 469 */           return f350b.booleanValue();
                }

                /* JADX INFO: renamed from: a */
/* 491 */       public static boolean m213a(Context context, String str, boolean z) {
                    Resources resources;
/* 492 */           if (context != null && (resources = context.getResources()) != null) {
/* 496 */               int iM195a = m195a(context, str, "bool");
/* 498 */               if (iM195a > 0) {
/* 499 */                   return resources.getBoolean(iM195a);
                        }
/* 502 */               int iM195a2 = m195a(context, str, "string");
/* 504 */               if (iM195a2 > 0) {
/* 505 */                   return Boolean.parseBoolean(context.getString(iM195a2));
                        }
/* 510 */               return z;
                    }
/* 510 */           return z;
                }

                /* JADX INFO: renamed from: a */
/* 513 */       public static int m195a(Context context, String str, String str2) {
/* 514 */           Resources resources = context.getResources();
/* 515 */           int i = context.getApplicationContext().getApplicationInfo().icon;
                    return resources.getIdentifier(str, str2, i > 0 ? context.getResources().getResourcePackageName(i) : context.getPackageName());
                }

                /* JADX INFO: renamed from: d */
/* 523 */       public static boolean m224d() {
                    return "sdk".equals(Build.PRODUCT) || "google_sdk".equals(Build.PRODUCT) || Settings.Secure.getString(C0727v.m357a().getContext().getContentResolver(), "android_id") == null;
                }

                /* JADX INFO: renamed from: e */
/* 533 */       public static boolean m226e() {
/* 534 */           boolean zM224d = m224d();
/* 535 */           String str = Build.TAGS;
/* 536 */           if ((zM224d || str == null || !str.contains("test-keys")) && !new File("/system/app/Superuser.apk").exists()) {
                        return !zM224d && new File("/system/xbin/su").exists();
                    }
                    return true;
                }

                /* JADX INFO: renamed from: f */
/* 570 */       public static int m229f() {
/* 571 */           int i = m224d() ? 1 : 0;
/* 575 */           if (m226e()) {
/* 576 */               i |= 2;
                    }
/* 579 */           if (Debug.isDebuggerConnected() || Debug.waitingForDebugger()) {
/* 580 */               return i | 4;
                    }
/* 583 */           return i;
                }

                /* JADX INFO: renamed from: a */
/* 593 */       public static int m196a(boolean z) {
/* 594 */           float fM214b = m214b(C0727v.m357a().getContext());
/* 596 */           if (!z) {
/* 597 */               return 1;
                    }
/* 600 */           if (z && fM214b >= 99.0d) {
/* 601 */               return 3;
                    }
/* 604 */           if (z && fM214b < 99.0d) {
/* 605 */               return 2;
                    }
/* 608 */           return 0;
                }

                /* JADX INFO: renamed from: b */
/* 623 */       public static Cipher m219b(int i, String str) throws InvalidKeyException {
/* 624 */           if (str.length() < 32) {
/* 625 */               throw new InvalidKeyException("Key must be at least 32 bytes.");
                    }
/* 627 */           SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), 0, 32, "AES/ECB/PKCS7Padding");
                    try {
/* 628 */               Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
/* 637 */               cipher.init(1, secretKeySpec);
/* 638 */               return cipher;
                    } catch (GeneralSecurityException e) {
/* 632 */               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not create Cipher for AES/ECB/PKCS7Padding - should never happen.", e);
/* 635 */               throw new RuntimeException(e);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 642 */       public static String m206a(byte[] bArr) {
/* 643 */           char[] cArr = new char[bArr.length << 1];
                    for (int i = 0; i < bArr.length; i++) {
/* 646 */               int i2 = bArr[i] & Constants.UNKNOWN;
/* 647 */               cArr[i << 1] = f351c[i2 >>> 4];
/* 648 */               cArr[(i << 1) + 1] = f351c[i2 & 15];
                    }
/* 650 */           return new String(cArr);
                }

                /* JADX INFO: renamed from: f */
/* 674 */       public static boolean m230f(Context context) {
                    return (context.getApplicationInfo().flags & 2) != 0;
                }

                /* JADX INFO: renamed from: a */
/* 699 */       public static String m202a(Context context, String str) {
/* 700 */           int iM195a = m195a(context, str, "string");
                    return iM195a > 0 ? context.getString(iM195a) : "";
                }

                /* JADX INFO: renamed from: a */
/* 715 */       public static void m210a(Closeable closeable, String str) {
/* 716 */           if (closeable != null) {
                        try {
/* 718 */                   closeable.close();
                        } catch (IOException e) {
/* 720 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, str, e);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 725 */       public static void m211a(Flushable flushable, String str) {
/* 726 */           if (flushable != null) {
                        try {
/* 728 */                   flushable.flush();
                        } catch (IOException e) {
/* 730 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, str, e);
                        }
                    }
                }

                /* JADX INFO: renamed from: e */
/* 736 */       public static boolean m228e(String str) {
                    return str == null || str.length() == 0;
                }

                /* JADX INFO: renamed from: a */
/* 747 */       public static String m201a(int i) {
/* 748 */           if (i < 0) {
/* 749 */               throw new IllegalArgumentException("value must be zero or greater");
                    }
                    return String.format(Locale.US, "%1$10s", Integer.valueOf(i)).replace(' ', '0');
                }

                /* JADX INFO: renamed from: a */
/* 794 */       public static void m212a(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
                    while (true) {
/* 795 */               int i = inputStream.read(bArr);
                        if (i != -1) {
/* 796 */                   outputStream.write(bArr, 0, i);
                        } else {
/* 798 */                   return;
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 800 */       public static String m217b(int i) {
                    switch (i) {
                        case 2:
/* 811 */                   return "V";
                        case 3:
/* 805 */                   return "D";
                        case 4:
/* 809 */                   return "I";
                        case 5:
/* 813 */                   return "W";
                        case 6:
/* 807 */                   return "E";
                        case 7:
/* 803 */                   return "A";
                        default:
/* 815 */                   return "?";
                    }
                }

                /* JADX INFO: renamed from: g */
/* 826 */       public static String m231g(Context context) throws Throwable {
                    InputStream inputStreamOpenRawResource;
                    Throwable th;
                    String str = null;
                    try {
/* 827 */               inputStreamOpenRawResource = context.getResources().openRawResource(m232h(context));
                        try {
                            try {
/* 828 */                       String strM218b = m218b(inputStreamOpenRawResource);
/* 829 */                       str = m228e(strM218b) ? null : strM218b;
/* 833 */                       m210a(inputStreamOpenRawResource, "Failed to close icon input stream.");
                            } catch (Exception e) {
/* 830 */                       e = e;
/* 831 */                       C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not calculate hash for app icon.", e);
/* 833 */                       m210a(inputStreamOpenRawResource, "Failed to close icon input stream.");
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            m210a(inputStreamOpenRawResource, "Failed to close icon input stream.");
                            throw th;
                        }
                    } catch (Exception e2) {
/* 830 */               e = e2;
                        inputStreamOpenRawResource = null;
                    } catch (Throwable th3) {
                        inputStreamOpenRawResource = null;
                        th = th3;
                        m210a(inputStreamOpenRawResource, "Failed to close icon input stream.");
                        throw th;
                    }
/* 836 */           return str;
                }

                /* JADX INFO: renamed from: h */
/* 839 */       public static int m232h(Context context) {
/* 840 */           return context.getApplicationContext().getApplicationInfo().icon;
                }

                /* JADX INFO: renamed from: i */
/* 846 */       public static String m233i(Context context) {
/* 849 */           int iM195a = m195a(context, "com.crashlytics.android.build_id", "string");
/* 851 */           if (iM195a == 0) {
/* 847 */               return null;
                    }
/* 852 */           String string = context.getResources().getString(iM195a);
/* 853 */           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Build ID is: " + string);
/* 856 */           return string;
                }
            }
