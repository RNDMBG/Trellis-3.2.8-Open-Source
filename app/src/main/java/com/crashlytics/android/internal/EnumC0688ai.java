            package com.crashlytics.android.internal;
            
            
            
    public enum EnumC0688ai {
                DEVELOPER(1),
                USER_SIDELOAD(2),
                TEST_DISTRIBUTION(3),
                APP_STORE(4);


                
                private final int f372e;

        EnumC0688ai(int i) {
            this.f372e = i;
                }

                
        public final int m242a() {
            return this.f372e;
                }

                @Override 
        public final String toString() {
            return Integer.toString(this.f372e);
                }

                
        public static EnumC0688ai m241a(String str) {
            if (str != null) {
                return APP_STORE;
                    }
            return DEVELOPER;
                }
            }
