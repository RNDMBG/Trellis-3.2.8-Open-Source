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
            
            
            
    public final class C0694ao {

                
                private static final Pattern f385a = Pattern.compile("[^\\p{Alnum}]");

                
                private static final String f386b = Pattern.quote("/");

                
                private final ReentrantLock f387c = new ReentrantLock();

                
                private final boolean f388d;

                
                private final boolean f389e;

                
                private final Context f390f;

        public C0694ao(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("appContext must not be null");
                    }
            this.f390f = context;
            this.f388d = C0681ab.m213a(context, "com.crashlytics.CollectDeviceIdentifiers", true);
            if (!this.f388d) {
                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Device ID collection disabled for " + context.getPackageName());
                    }
            this.f389e = C0681ab.m213a(context, "com.crashlytics.CollectUserIdentifiers", true);
            if (!this.f389e) {
                C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "User information collection disabled for " + context.getPackageName());
                    }
                }

                
        public final boolean m258a() {
           return this.f389e;
                }

                
       private boolean m254a(String str) {
                    return this.f390f.checkCallingPermission(str) == 0;
                }

                
       private static String m255b(String str) {
           if (str == null) {
                        return null;
                    }
                    return f385a.matcher(str).replaceAll("").toLowerCase(Locale.US);
                }

                
       public final String m259b() {
           String strM371i = C0727v.m357a().m371i();
           if (strM371i == null) {
               SharedPreferences sharedPreferencesM200a = C0681ab.m200a();
               String string = sharedPreferencesM200a.getString("crashlytics.installation.id", null);
               if (string == null) {
                   return m252a(sharedPreferencesM200a);
                        }
               return string;
                    }
           return strM371i;
                }

                
       public final String m260c() {
                    return String.format(Locale.US, "%s/%s", m256c(Build.VERSION.RELEASE), m256c(Build.VERSION.INCREMENTAL));
                }

                
       public final String m261d() {
                    return String.format(Locale.US, "%s/%s", m256c(Build.MANUFACTURER), m256c(Build.MODEL));
                }

                
       private static String m256c(String str) {
           return str.replaceAll(f386b, "");
                }

                
       public final String m262e() {
           if (!this.f388d) {
               return "";
                    }
           String strM264g = m264g();
           if (strM264g == null) {
               SharedPreferences sharedPreferencesM200a = C0681ab.m200a();
               String string = sharedPreferencesM200a.getString("crashlytics.installation.id", null);
               if (string == null) {
                   return m252a(sharedPreferencesM200a);
                        }
               return string;
                    }
           return strM264g;
                }

                
       private String m252a(SharedPreferences sharedPreferences) {
           this.f387c.lock();
                    try {
               String string = sharedPreferences.getString("crashlytics.installation.id", null);
               if (string == null) {
                   string = m255b(UUID.randomUUID().toString());
                   sharedPreferences.edit().putString("crashlytics.installation.id", string).commit();
                        }
               this.f387c.unlock();
                        return string;
                    } catch (Throwable th) {
                        this.f387c.unlock();
                        throw th;
                    }
                }

                
       public final Map<EnumC0695ap, String> m263f() {
                    WifiManager wifiManager;
                    WifiInfo connectionInfo;
                    TelephonyManager telephonyManager;
                    String strM255b = null;
           HashMap map = new HashMap();
           m253a(map, EnumC0695ap.ANDROID_ID, m264g());
           m253a(map, EnumC0695ap.ANDROID_DEVICE_ID, (this.f388d && m254a("android.permission.READ_PHONE_STATE") && (telephonyManager = (TelephonyManager) this.f390f.getSystemService(VineService.EXTRA_PHONE)) != null) ? m255b(telephonyManager.getDeviceId()) : null);
           m253a(map, EnumC0695ap.ANDROID_SERIAL, m257i());
           EnumC0695ap enumC0695ap = EnumC0695ap.WIFI_MAC_ADDRESS;
                    if (this.f388d && m254a("android.permission.ACCESS_WIFI_STATE") && (wifiManager = (WifiManager) this.f390f.getSystemService("wifi")) != null && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
                        strM255b = m255b(connectionInfo.getMacAddress());
                    }
                    m253a(map, enumC0695ap, strM255b);
           m253a(map, EnumC0695ap.BLUETOOTH_MAC_ADDRESS, m265h());
           return Collections.unmodifiableMap(map);
                }

                
       private static void m253a(Map<EnumC0695ap, String> map, EnumC0695ap enumC0695ap, String str) {
           if (str != null) {
               map.put(enumC0695ap, str);
                    }
                }

                
       public final String m264g() {
           if (!this.f388d) {
               return null;
                    }
           String string = Settings.Secure.getString(this.f390f.getContentResolver(), "android_id");
           if ("9774d56d682e549c".equals(string)) {
               return null;
                    }
           return m255b(string);
                }

                
       public final String m265h() {
           if (this.f388d && m254a("android.permission.BLUETOOTH")) {
                        try {
                   BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                   if (defaultAdapter != null) {
                       m255b(defaultAdapter.getAddress());
                       return null;
                            }
                   return null;
                        } catch (Exception e) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Utils#getBluetoothMacAddress failed, returning null. Requires prior call to BluetoothAdatpter.getDefaultAdapter() on thread that has called Looper.prepare()", e);
                   return null;
                        }
                    }
           return null;
                }

                
       private String m257i() {
           if (this.f388d && Build.VERSION.SDK_INT >= 9) {
                        try {
                   return m255b((String) Build.class.getField("SERIAL").get(null));
                        } catch (Exception e) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "Could not retrieve android.os.Build.SERIAL value", e);
                        }
                    }
                    return null;
                }
            }
