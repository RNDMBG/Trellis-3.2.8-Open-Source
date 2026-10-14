            package com.crashlytics.android.internal;

            import android.bluetooth.BluetoothAdapter;
            import android.content.Context;
            import android.content.SharedPreferences;
            import android.net.wifi.WifiInfo;
            import android.net.wifi.WifiManager;
            import android.os.Build;
            import android.provider.Settings;
            import android.telephony.TelephonyManager;
            import co.vine.android.service.VineService;
            import com.crashlytics.android.Crashlytics;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.Locale;
            import java.util.Map;
            import java.util.UUID;
            import java.util.concurrent.locks.ReentrantLock;
            import java.util.regex.Pattern;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.ao */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 78 */    public final class C0694ao {

                /* JADX INFO: renamed from: a */
                private static final Pattern f385a = Pattern.compile("[^\\p{Alnum}]");

                /* JADX INFO: renamed from: b */
                private static final String f386b = Pattern.quote("/");

                /* JADX INFO: renamed from: c */
                private final ReentrantLock f387c = new ReentrantLock();

                /* JADX INFO: renamed from: d */
                private final boolean f388d;

                /* JADX INFO: renamed from: e */
                private final boolean f389e;

                /* JADX INFO: renamed from: f */
                private final Context f390f;

/* 79 */        public C0694ao(Context context) {
/* 81 */            if (context == null) {
/* 82 */                throw new IllegalArgumentException("appContext must not be null");
                    }
/* 84 */            this.f390f = context;
/* 86 */            this.f388d = C0681ab.m213a(context, "com.crashlytics.CollectDeviceIdentifiers", true);
/* 87 */            if (!this.f388d) {
/* 88 */                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Device ID collection disabled for " + context.getPackageName());
                    }
/* 92 */            this.f389e = C0681ab.m213a(context, "com.crashlytics.CollectUserIdentifiers", true);
/* 93 */            if (!this.f389e) {
/* 94 */                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "User information collection disabled for " + context.getPackageName());
                    }
                }

                /* JADX INFO: renamed from: a */
/* 99 */        public final boolean m258a() {
/* 100 */           return this.f389e;
                }

                /* JADX INFO: renamed from: a */
/* 104 */       private boolean m254a(String str) {
                    return this.f390f.checkCallingPermission(str) == 0;
                }

                /* JADX INFO: renamed from: b */
/* 115 */       private static String m255b(String str) {
/* 116 */           if (str == null) {
                        return null;
                    }
                    return f385a.matcher(str).replaceAll("").toLowerCase(Locale.US);
                }

                /* JADX INFO: renamed from: b */
/* 126 */       public final String m259b() {
/* 127 */           String strM371i = C0727v.m357a().m371i();
/* 129 */           if (strM371i == null) {
/* 130 */               SharedPreferences sharedPreferencesM200a = C0681ab.m200a();
/* 131 */               String string = sharedPreferencesM200a.getString("crashlytics.installation.id", null);
/* 133 */               if (string == null) {
/* 134 */                   return m252a(sharedPreferencesM200a);
                        }
/* 138 */               return string;
                    }
/* 138 */           return strM371i;
                }

                /* JADX INFO: renamed from: c */
/* 145 */       public final String m260c() {
                    return String.format(Locale.US, "%s/%s", m256c(Build.VERSION.RELEASE), m256c(Build.VERSION.INCREMENTAL));
                }

                /* JADX INFO: renamed from: d */
/* 153 */       public final String m261d() {
                    return String.format(Locale.US, "%s/%s", m256c(Build.MANUFACTURER), m256c(Build.MODEL));
                }

                /* JADX INFO: renamed from: c */
/* 158 */       private static String m256c(String str) {
/* 159 */           return str.replaceAll(f386b, "");
                }

                /* JADX INFO: renamed from: e */
/* 168 */       public final String m262e() {
/* 171 */           if (!this.f388d) {
/* 169 */               return "";
                    }
/* 172 */           String strM264g = m264g();
/* 174 */           if (strM264g == null) {
/* 175 */               SharedPreferences sharedPreferencesM200a = C0681ab.m200a();
/* 176 */               String string = sharedPreferencesM200a.getString("crashlytics.installation.id", null);
/* 178 */               if (string == null) {
/* 179 */                   return m252a(sharedPreferencesM200a);
                        }
/* 184 */               return string;
                    }
/* 184 */           return strM264g;
                }

                /* JADX INFO: renamed from: a */
/* 192 */       private String m252a(SharedPreferences sharedPreferences) {
/* 193 */           this.f387c.lock();
                    try {
/* 195 */               String string = sharedPreferences.getString("crashlytics.installation.id", null);
/* 197 */               if (string == null) {
/* 198 */                   string = m255b(UUID.randomUUID().toString());
/* 199 */                   sharedPreferences.edit().putString("crashlytics.installation.id", string).commit();
                        }
/* 204 */               this.f387c.unlock();
                        return string;
                    } catch (Throwable th) {
                        this.f387c.unlock();
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: f */
/* 214 */       public final Map<EnumC0695ap, String> m263f() {
                    WifiManager wifiManager;
                    WifiInfo connectionInfo;
                    TelephonyManager telephonyManager;
                    String strM255b = null;
/* 215 */           HashMap map = new HashMap();
/* 217 */           m253a(map, EnumC0695ap.ANDROID_ID, m264g());
/* 218 */           m253a(map, EnumC0695ap.ANDROID_DEVICE_ID, (this.f388d && m254a("android.permission.READ_PHONE_STATE") && (telephonyManager = (TelephonyManager) this.f390f.getSystemService(VineService.EXTRA_PHONE)) != null) ? m255b(telephonyManager.getDeviceId()) : null);
/* 219 */           m253a(map, EnumC0695ap.ANDROID_SERIAL, m257i());
/* 220 */           EnumC0695ap enumC0695ap = EnumC0695ap.WIFI_MAC_ADDRESS;
                    if (this.f388d && m254a("android.permission.ACCESS_WIFI_STATE") && (wifiManager = (WifiManager) this.f390f.getSystemService("wifi")) != null && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
                        strM255b = m255b(connectionInfo.getMacAddress());
                    }
                    m253a(map, enumC0695ap, strM255b);
/* 221 */           m253a(map, EnumC0695ap.BLUETOOTH_MAC_ADDRESS, m265h());
/* 223 */           return Collections.unmodifiableMap(map);
                }

                /* JADX INFO: renamed from: a */
/* 226 */       private static void m253a(Map<EnumC0695ap, String> map, EnumC0695ap enumC0695ap, String str) {
/* 227 */           if (str != null) {
/* 228 */               map.put(enumC0695ap, str);
                    }
                }

                /* JADX INFO: renamed from: g */
/* 232 */       public final String m264g() {
/* 235 */           if (!this.f388d) {
/* 233 */               return null;
                    }
/* 236 */           String string = Settings.Secure.getString(this.f390f.getContentResolver(), "android_id");
/* 238 */           if ("9774d56d682e549c".equals(string)) {
/* 233 */               return null;
                    }
/* 239 */           return m255b(string);
                }

                /* JADX INFO: renamed from: h */
/* 282 */       public final String m265h() {
/* 283 */           if (this.f388d && m254a("android.permission.BLUETOOTH")) {
                        try {
/* 285 */                   BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
/* 286 */                   if (defaultAdapter != null) {
/* 287 */                       m255b(defaultAdapter.getAddress());
/* 293 */                       return null;
                            }
/* 293 */                   return null;
                        } catch (Exception e) {
/* 290 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Utils#getBluetoothMacAddress failed, returning null. Requires prior call to BluetoothAdatpter.getDefaultAdapter() on thread that has called Looper.prepare()", e);
/* 293 */                   return null;
                        }
                    }
/* 293 */           return null;
                }

                /* JADX INFO: renamed from: i */
/* 297 */       private String m257i() {
/* 298 */           if (this.f388d && Build.VERSION.SDK_INT >= 9) {
                        try {
/* 301 */                   return m255b((String) Build.class.getField("SERIAL").get(null));
                        } catch (Exception e) {
/* 303 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not retrieve android.os.Build.SERIAL value", e);
                        }
                    }
                    return null;
                }
            }
