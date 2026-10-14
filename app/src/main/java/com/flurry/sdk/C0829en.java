            package com.flurry.sdk;

            import android.os.Build;
            import android.text.TextUtils;
            
            /* JADX INFO: renamed from: com.flurry.sdk.en */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public final class C0829en extends C0831ep {

                /* JADX INFO: renamed from: a */
                private final int f822a;

/* 21 */        public C0829en(String str, int i) {
/* 22 */            super(m904a(str, i));
/* 23 */            this.f822a = i;
                }

                /* JADX INFO: renamed from: a */
/* 30 */        private static InterfaceC0832eq m904a(String str, int i) {
/* 32 */            if (!m905b(str, i)) {
/* 31 */                return null;
                    }
/* 33 */            return C0830eo.m906a(str);
                }

                /* JADX INFO: renamed from: b */
/* 39 */        private static boolean m905b(String str, int i) {
                    return !TextUtils.isEmpty(str) && Build.VERSION.SDK_INT >= i;
                }
            }
