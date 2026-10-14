            package com.crashlytics.android.internal;

            import android.os.Build;
            import android.text.TextUtils;
            import com.crashlytics.android.Crashlytics;
            import java.util.HashMap;
            import java.util.Locale;
            import java.util.Map;
            
            
            
   enum EnumC0683ad {
                X86_32,
                X86_64,
                ARM_UNKNOWN,
                PPC,
                PPC64,
                ARMV6,
                ARMV7,
                UNKNOWN,
                ARMV7S,
                ARM64;


                
                private static final Map<String, EnumC0683ad> f364k;

                static {
           HashMap map = new HashMap(4);
           f364k = map;
                    map.put("armeabi-v7a", ARMV7);
           f364k.put("armeabi", ARMV6);
           f364k.put("x86", X86_32);
                }

                
       static EnumC0683ad m234a() {
           String str = Build.CPU_ABI;
           if (TextUtils.isEmpty(str)) {
               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Architecture#getValue()::Build.CPU_ABI returned null or empty");
               return UNKNOWN;
                    }
           EnumC0683ad enumC0683ad = f364k.get(str.toLowerCase(Locale.US));
           if (enumC0683ad == null) {
               return UNKNOWN;
                    }
           return enumC0683ad;
                }
            }
