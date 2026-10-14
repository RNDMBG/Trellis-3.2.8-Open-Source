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
            
            
            
    public final class C0681ab {

                
                private static Boolean f350b = null;

                
                private static final char[] f351c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

                
                private static long f352d = -1;

                
                private static Boolean f353e = null;

                
                public static final Comparator<File> f349a = new C0682ac();

                
       public static SharedPreferences m200a() {
           return C0727v.m357a().getContext().getSharedPreferences("com.crashlytics.prefs", 0);
                }

                
       private static String m203a(File file, String str) throws Throwable {
                    BufferedReader bufferedReader;
                    Throwable th;
                    String str2 = null;
           if (file.exists()) {
                        try {
                   bufferedReader = new BufferedReader(new FileReader(file), 1024);
                            while (true) {
                                try {
                                    try {
                               String line = bufferedReader.readLine();
                                        if (line == null) {
                                            break;
                                        }
                               String[] strArrSplit = Pattern.compile("\\s*:\\s*").split(line, 2);
                               if (strArrSplit.length > 1 && strArrSplit[0].equals(str)) {
                                   str2 = strArrSplit[1];
                                            break;
                                        }
                                    } catch (Exception e) {
                               e = e;
                               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Error parsing " + file, e);
                               m210a(bufferedReader, "Failed to close system file reader.");
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    m210a(bufferedReader, "Failed to close system file reader.");
                                    throw th;
                                }
                            }
                   m210a(bufferedReader, "Failed to close system file reader.");
                        } catch (Exception e2) {
                   e = e2;
                            bufferedReader = null;
                        } catch (Throwable th3) {
                            bufferedReader = null;
                            th = th3;
                            m210a(bufferedReader, "Failed to close system file reader.");
                            throw th;
                        }
                    }
           return str2;
                }

                
       public static int m215b() {
           return EnumC0683ad.m234a().ordinal();
                }

                
       public static synchronized long m220c() {
                    long jM198a;
                    try {
                        if (f352d == -1) {
                   String strM203a = m203a(new File("/proc/meminfo"), "MemTotal");
                   if (TextUtils.isEmpty(strM203a)) {
                       jM198a = 0;
                       f352d = jM198a;
                            } else {
                       String upperCase = strM203a.toUpperCase(Locale.US);
                                try {
                           if (upperCase.endsWith("KB")) {
                               jM198a = m198a(upperCase, "KB", 1024);
                                    } else if (upperCase.endsWith("MB")) {
                               jM198a = m198a(upperCase, "MB", 1048576);
                                    } else if (upperCase.endsWith("GB")) {
                               jM198a = m198a(upperCase, "GB", 1073741824);
                                    } else {
                               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Unexpected meminfo format while computing RAM: " + upperCase);
                               jM198a = 0;
                                    }
                                } catch (NumberFormatException e) {
                           C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Unexpected meminfo format while computing RAM: " + upperCase, e);
                           jM198a = 0;
                                }
                       f352d = jM198a;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
           return f352d;
                }

                
       private static long m198a(String str, String str2, int i) {
           return Long.parseLong(str.split(str2)[0].trim()) * ((long) i);
                }

                
       public static ActivityManager.RunningAppProcessInfo m199a(String str, Context context) {
           List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY)).getRunningAppProcesses();
           if (runningAppProcesses != null) {
                        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                   if (runningAppProcessInfo.processName.equals(str)) {
                       return runningAppProcessInfo;
                            }
                        }
                    }
           return null;
                }

                
       public static String m204a(InputStream inputStream) throws IOException {
           Scanner scannerUseDelimiter = new Scanner(inputStream).useDelimiter("\\A");
                    return scannerUseDelimiter.hasNext() ? scannerUseDelimiter.next() : "";
                }

                
       public static String m205a(String str) {
           return m207a(str.getBytes(), "SHA-1");
                }

                
       private static String m218b(InputStream inputStream) {
                    try {
               MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
               byte[] bArr = new byte[1024];
                        while (true) {
                   int i = inputStream.read(bArr);
                            if (i != -1) {
                       messageDigest.update(bArr, 0, i);
                            } else {
                       return m206a(messageDigest.digest());
                            }
                        }
                    } catch (Exception e) {
               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not calculate hash for app icon.", e);
               return "";
                    }
                }

                
       private static String m207a(byte[] bArr, String str) {
                    try {
               MessageDigest messageDigest = MessageDigest.getInstance(str);
               messageDigest.update(bArr);
               return m206a(messageDigest.digest());
                    } catch (NoSuchAlgorithmException e) {
               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not create hashing algorithm: " + str + ", returning empty string.", e);
               return "";
                    }
                }

                
       public static String m208a(String... strArr) {
           if (strArr == null || strArr.length == 0) {
                        return null;
                    }
           ArrayList arrayList = new ArrayList();
                    for (String str : strArr) {
               if (str != null) {
                   arrayList.add(str.replace("-", "").toLowerCase(Locale.US));
                        }
                    }
           Collections.sort(arrayList);
           StringBuilder sb = new StringBuilder();
           Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
               sb.append((String) it.next());
                    }
           String string = sb.toString();
           if (string.length() > 0) {
                        return m205a(string);
                    }
                    return null;
                }

                
       public static long m197a(Context context) {
           ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
           ((ActivityManager) context.getSystemService(HomeTabActivity.TAG_ACTIVITY)).getMemoryInfo(memoryInfo);
           return memoryInfo.availMem;
                }

                
       public static long m216b(String str) {
           StatFs statFs = new StatFs(str);
           long blockSize = statFs.getBlockSize();
           return (((long) statFs.getBlockCount()) * blockSize) - (((long) statFs.getAvailableBlocks()) * blockSize);
                }

                
       public static float m214b(Context context) {
           Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
           return intentRegisterReceiver.getIntExtra("level", -1) / intentRegisterReceiver.getIntExtra("scale", -1);
                }

                
       public static boolean m222c(Context context) {
                    return (m224d() || ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(8) == null) ? false : true;
                }

                
       public static void m221c(String str) {
           if (m227e(C0727v.m357a().getContext())) {
               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, str);
                    }
                }

                
       public static void m223d(String str) {
           if (m227e(C0727v.m357a().getContext())) {
               C0727v.m357a().m365b().mo100d(Crashlytics.TAG, str);
                    }
                }

                
       public static void m209a(int i, String str) {
           if (m227e(C0727v.m357a().getContext())) {
               C0727v.m357a().m365b().mo94a(4, Crashlytics.TAG, str);
                    }
                }

                
       public static boolean m225d(Context context) {
           if (f353e == null) {
                        f353e = Boolean.valueOf(m213a(context, "com.crashlytics.SilenceCrashlyticsLogCat", false) ? false : true);
                    }
           return f353e.booleanValue();
                }

                
       public static boolean m227e(Context context) {
           if (f350b == null) {
               f350b = Boolean.valueOf(m213a(context, "com.crashlytics.Trace", false));
                    }
           return f350b.booleanValue();
                }

                
       public static boolean m213a(Context context, String str, boolean z) {
                    Resources resources;
           if (context != null && (resources = context.getResources()) != null) {
               int iM195a = m195a(context, str, "bool");
               if (iM195a > 0) {
                   return resources.getBoolean(iM195a);
                        }
               int iM195a2 = m195a(context, str, "string");
               if (iM195a2 > 0) {
                   return Boolean.parseBoolean(context.getString(iM195a2));
                        }
               return z;
                    }
           return z;
                }

                
       public static int m195a(Context context, String str, String str2) {
           Resources resources = context.getResources();
           int i = context.getApplicationContext().getApplicationInfo().icon;
                    return resources.getIdentifier(str, str2, i > 0 ? context.getResources().getResourcePackageName(i) : context.getPackageName());
                }

                
       public static boolean m224d() {
                    return "sdk".equals(Build.PRODUCT) || "google_sdk".equals(Build.PRODUCT) || Settings.Secure.getString(C0727v.m357a().getContext().getContentResolver(), "android_id") == null;
                }

                
       public static boolean m226e() {
           boolean zM224d = m224d();
           String str = Build.TAGS;
           if ((zM224d || str == null || !str.contains("test-keys")) && !new File("/system/app/Superuser.apk").exists()) {
                        return !zM224d && new File("/system/xbin/su").exists();
                    }
                    return true;
                }

                
       public static int m229f() {
           int i = m224d() ? 1 : 0;
           if (m226e()) {
               i |= 2;
                    }
           if (Debug.isDebuggerConnected() || Debug.waitingForDebugger()) {
               return i | 4;
                    }
           return i;
                }

                
       public static int m196a(boolean z) {
           float fM214b = m214b(C0727v.m357a().getContext());
           if (!z) {
               return 1;
                    }
           if (z && fM214b >= 99.0d) {
               return 3;
                    }
           if (z && fM214b < 99.0d) {
               return 2;
                    }
           return 0;
                }

                
       public static Cipher m219b(int i, String str) throws InvalidKeyException {
           if (str.length() < 32) {
               throw new InvalidKeyException("Key must be at least 32 bytes.");
                    }
           SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), 0, 32, "AES/ECB/PKCS7Padding");
                    try {
               Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
               cipher.init(1, secretKeySpec);
               return cipher;
                    } catch (GeneralSecurityException e) {
               C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not create Cipher for AES/ECB/PKCS7Padding - should never happen.", e);
               throw new RuntimeException(e);
                    }
                }

                
       public static String m206a(byte[] bArr) {
           char[] cArr = new char[bArr.length << 1];
                    for (int i = 0; i < bArr.length; i++) {
               int i2 = bArr[i] & Constants.UNKNOWN;
               cArr[i << 1] = f351c[i2 >>> 4];
               cArr[(i << 1) + 1] = f351c[i2 & 15];
                    }
           return new String(cArr);
                }

                
       public static boolean m230f(Context context) {
                    return (context.getApplicationInfo().flags & 2) != 0;
                }

                
       public static String m202a(Context context, String str) {
           int iM195a = m195a(context, str, "string");
                    return iM195a > 0 ? context.getString(iM195a) : "";
                }

                
       public static void m210a(Closeable closeable, String str) {
           if (closeable != null) {
                        try {
                   closeable.close();
                        } catch (IOException e) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, str, e);
                        }
                    }
                }

                
       public static void m211a(Flushable flushable, String str) {
           if (flushable != null) {
                        try {
                   flushable.flush();
                        } catch (IOException e) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, str, e);
                        }
                    }
                }

                
       public static boolean m228e(String str) {
                    return str == null || str.length() == 0;
                }

                
       public static String m201a(int i) {
           if (i < 0) {
               throw new IllegalArgumentException("value must be zero or greater");
                    }
                    return String.format(Locale.US, "%1$10s", Integer.valueOf(i)).replace(' ', '0');
                }

                
       public static void m212a(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
                    while (true) {
               int i = inputStream.read(bArr);
                        if (i != -1) {
                   outputStream.write(bArr, 0, i);
                        } else {
                   return;
                        }
                    }
                }

                
       public static String m217b(int i) {
                    switch (i) {
                        case 2:
                   return "V";
                        case 3:
                   return "D";
                        case 4:
                   return "I";
                        case 5:
                   return "W";
                        case 6:
                   return "E";
                        case 7:
                   return "A";
                        default:
                   return "?";
                    }
                }

                
       public static String m231g(Context context) throws Throwable {
                    InputStream inputStreamOpenRawResource;
                    Throwable th;
                    String str = null;
                    try {
               inputStreamOpenRawResource = context.getResources().openRawResource(m232h(context));
                        try {
                            try {
                       String strM218b = m218b(inputStreamOpenRawResource);
                       str = m228e(strM218b) ? null : strM218b;
                       m210a(inputStreamOpenRawResource, "Failed to close icon input stream.");
                            } catch (Exception e) {
                       e = e;
                       C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not calculate hash for app icon.", e);
                       m210a(inputStreamOpenRawResource, "Failed to close icon input stream.");
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            m210a(inputStreamOpenRawResource, "Failed to close icon input stream.");
                            throw th;
                        }
                    } catch (Exception e2) {
               e = e2;
                        inputStreamOpenRawResource = null;
                    } catch (Throwable th3) {
                        inputStreamOpenRawResource = null;
                        th = th3;
                        m210a(inputStreamOpenRawResource, "Failed to close icon input stream.");
                        throw th;
                    }
           return str;
                }

                
       public static int m232h(Context context) {
           return context.getApplicationContext().getApplicationInfo().icon;
                }

                
       public static String m233i(Context context) {
           int iM195a = m195a(context, "com.crashlytics.android.build_id", "string");
           if (iM195a == 0) {
               return null;
                    }
           String string = context.getResources().getString(iM195a);
           C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Build ID is: " + string);
           return string;
                }
            }
