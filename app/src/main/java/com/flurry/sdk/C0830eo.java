            package com.flurry.sdk;

            import android.text.TextUtils;
            
            /* JADX INFO: renamed from: com.flurry.sdk.eo */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public final class C0830eo {

                /* JADX INFO: renamed from: a */
                private static final String f823a = C0830eo.class.getSimpleName();

                /* JADX INFO: renamed from: a */
/* 31 */        public static InterfaceC0832eq m906a(String str) {
                    InterfaceC0832eq interfaceC0832eq;
/* 32 */            if (TextUtils.isEmpty(str)) {
                        return null;
                    }
                    try {
/* 39 */                interfaceC0832eq = (InterfaceC0832eq) Class.forName(str).getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                    } catch (Exception e) {
/* 41 */                C0827el.m886a(5, f823a, "FlurryModule " + str + " is not available:", e);
                        interfaceC0832eq = null;
                    }
                    return interfaceC0832eq;
                }
            }
