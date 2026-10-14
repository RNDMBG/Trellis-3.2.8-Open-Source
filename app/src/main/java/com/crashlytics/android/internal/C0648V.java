            package com.crashlytics.android.internal;

            import java.util.Map;
            
            
            
    final class C0648V {

                
                public final String f259a;

                
                public final String f260b;

                
                public final String f261c;

                
                public final String f262d;

                
                public final String f263e;

                
                public final String f264f;

                
                public final String f265g;

                
                public final String f266h;

                
                public final long f267i;

                
                public final EnumC0649W f268j;

                
                public final Map<String, String> f269k;

                
                private String f270l;

                
        public static final C0648V m151a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, EnumC0649W enumC0649W, Map<String, String> map) {
            return new C0648V(str, str2, str3, str4, str5, str6, str7, str8, System.currentTimeMillis(), enumC0649W, map);
                }

        private C0648V(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, long j, EnumC0649W enumC0649W, Map<String, String> map) {
            this.f259a = str;
            this.f260b = str2;
            this.f261c = str3;
            this.f262d = str4;
            this.f263e = str5;
            this.f264f = str6;
            this.f265g = str7;
            this.f266h = str8;
            this.f267i = j;
            this.f268j = enumC0649W;
            this.f269k = map;
                }

        public final String toString() {
            if (this.f270l == null) {
               this.f270l = "[" + getClass().getSimpleName() + ": appBundleId=" + this.f259a + ", executionId=" + this.f260b + ", installationId=" + this.f261c + ", androidId=" + this.f262d + ", osVersion=" + this.f263e + ", deviceModel=" + this.f264f + ", appVersionCode=" + this.f265g + ", appVersionName=" + this.f266h + ", timestamp=" + this.f267i + ", type=" + this.f268j + ", details=" + this.f269k.toString() + "]";
                    }
           return this.f270l;
                }
            }
