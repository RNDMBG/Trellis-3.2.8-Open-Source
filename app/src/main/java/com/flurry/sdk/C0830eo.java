            package com.flurry.sdk;

            import android.text.TextUtils;
            
            
            
    public final class C0830eo {

                
                private static final String f823a = C0830eo.class.getSimpleName();

                
        public static InterfaceC0832eq m906a(String str) {
                    InterfaceC0832eq interfaceC0832eq;
            if (TextUtils.isEmpty(str)) {
                        return null;
                    }
                    try {
                interfaceC0832eq = (InterfaceC0832eq) Class.forName(str).getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                    } catch (Exception e) {
                C0827el.m886a(5, f823a, "FlurryModule " + str + " is not available:", e);
                        interfaceC0832eq = null;
                    }
                    return interfaceC0832eq;
                }
            }
