            package com.crashlytics.android.internal;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.ai */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public enum EnumC0688ai {
                DEVELOPER(1),
                USER_SIDELOAD(2),
                TEST_DISTRIBUTION(3),
                APP_STORE(4);


                /* JADX INFO: renamed from: e */
                private final int f372e;

/* 12 */        EnumC0688ai(int i) {
/* 14 */            this.f372e = i;
                }

                /* JADX INFO: renamed from: a */
/* 17 */        public final int m242a() {
/* 18 */            return this.f372e;
                }

                @Override // java.lang.Enum
/* 22 */        public final String toString() {
/* 23 */            return Integer.toString(this.f372e);
                }

                /* JADX INFO: renamed from: a */
/* 32 */        public static EnumC0688ai m241a(String str) {
/* 33 */            if (str != null) {
/* 34 */                return APP_STORE;
                    }
/* 36 */            return DEVELOPER;
                }
            }
