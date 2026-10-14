            package com.flurry.sdk;

            import android.os.Build;
            import android.text.TextUtils;
            
            
            
    public final class C0829en extends C0831ep {

                
                private final int f822a;

        public C0829en(String str, int i) {
            super(m904a(str, i));
            this.f822a = i;
                }

                
        private static InterfaceC0832eq m904a(String str, int i) {
            if (!m905b(str, i)) {
                return null;
                    }
            return C0830eo.m906a(str);
                }

                
        private static boolean m905b(String str, int i) {
                    return !TextUtils.isEmpty(str) && Build.VERSION.SDK_INT >= i;
                }
            }
