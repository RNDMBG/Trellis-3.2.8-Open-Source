            package com.crashlytics.android.internal;

            import android.os.Build;
            import android.text.TextUtils;
            import com.crashlytics.android.Crashlytics;
            import java.util.HashMap;
            import java.util.Locale;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.ad */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 146 */   enum EnumC0683ad {
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


                /* JADX INFO: renamed from: k */
                private static final Map<String, EnumC0683ad> f364k;

                static {
/* 159 */           HashMap map = new HashMap(4);
/* 161 */           f364k = map;
                    map.put("armeabi-v7a", ARMV7);
/* 162 */           f364k.put("armeabi", ARMV6);
/* 163 */           f364k.put("x86", X86_32);
                }

                /* JADX INFO: renamed from: a */
/* 170 */       static EnumC0683ad m234a() {
/* 171 */           String str = Build.CPU_ABI;
/* 173 */           if (TextUtils.isEmpty(str)) {
/* 174 */               C0727v.m357a().m365b().mo96a(Crashlytics.TAG, "Architecture#getValue()::Build.CPU_ABI returned null or empty");
/* 175 */               return UNKNOWN;
                    }
/* 179 */           EnumC0683ad enumC0683ad = f364k.get(str.toLowerCase(Locale.US));
/* 180 */           if (enumC0683ad == null) {
/* 181 */               return UNKNOWN;
                    }
/* 183 */           return enumC0683ad;
                }
            }
