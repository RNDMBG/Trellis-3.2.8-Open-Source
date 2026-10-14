            package com.crashlytics.android.internal;

            import java.util.Map;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.V */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 65 */    final class C0648V {

                /* JADX INFO: renamed from: a */
                public final String f259a;

                /* JADX INFO: renamed from: b */
                public final String f260b;

                /* JADX INFO: renamed from: c */
                public final String f261c;

                /* JADX INFO: renamed from: d */
                public final String f262d;

                /* JADX INFO: renamed from: e */
                public final String f263e;

                /* JADX INFO: renamed from: f */
                public final String f264f;

                /* JADX INFO: renamed from: g */
                public final String f265g;

                /* JADX INFO: renamed from: h */
                public final String f266h;

                /* JADX INFO: renamed from: i */
                public final long f267i;

                /* JADX INFO: renamed from: j */
                public final EnumC0649W f268j;

                /* JADX INFO: renamed from: k */
                public final Map<String, String> f269k;

                /* JADX INFO: renamed from: l */
                private String f270l;

                /* JADX INFO: renamed from: a */
/* 66 */        public static final C0648V m151a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, EnumC0649W enumC0649W, Map<String, String> map) {
/* 67 */            return new C0648V(str, str2, str3, str4, str5, str6, str7, str8, System.currentTimeMillis(), enumC0649W, map);
                }

/* 72 */        private C0648V(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, long j, EnumC0649W enumC0649W, Map<String, String> map) {
/* 74 */            this.f259a = str;
/* 75 */            this.f260b = str2;
/* 76 */            this.f261c = str3;
/* 77 */            this.f262d = str4;
/* 78 */            this.f263e = str5;
/* 79 */            this.f264f = str6;
/* 80 */            this.f265g = str7;
/* 81 */            this.f266h = str8;
/* 82 */            this.f267i = j;
/* 83 */            this.f268j = enumC0649W;
/* 84 */            this.f269k = map;
                }

/* 93 */        public final String toString() {
/* 94 */            if (this.f270l == null) {
/* 123 */               this.f270l = "[" + getClass().getSimpleName() + ": appBundleId=" + this.f259a + ", executionId=" + this.f260b + ", installationId=" + this.f261c + ", androidId=" + this.f262d + ", osVersion=" + this.f263e + ", deviceModel=" + this.f264f + ", appVersionCode=" + this.f265g + ", appVersionName=" + this.f266h + ", timestamp=" + this.f267i + ", type=" + this.f268j + ", details=" + this.f269k.toString() + "]";
                    }
/* 125 */           return this.f270l;
                }
            }
