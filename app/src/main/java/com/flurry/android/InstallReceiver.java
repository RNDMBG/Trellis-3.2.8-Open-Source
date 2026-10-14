            package com.flurry.android;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import com.flurry.sdk.C0785cv;
            import com.flurry.sdk.C0827el;
            import com.flurry.sdk.C0843fb;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public final class InstallReceiver extends BroadcastReceiver {

                /* JADX INFO: renamed from: a */
                static final String f528a = InstallReceiver.class.getSimpleName();

                @Override // android.content.BroadcastReceiver
/* 28 */        public void onReceive(Context context, Intent intent) {
/* 29 */            C0827el.m885a(4, f528a, "Received an Install nofication of " + intent.getAction());
/* 32 */            String string = intent.getExtras().getString("referrer");
/* 33 */            C0827el.m885a(4, f528a, "Received an Install referrer of " + string);
/* 35 */            if (string == null || !"com.android.vending.INSTALL_REFERRER".equals(intent.getAction())) {
/* 36 */                C0827el.m885a(5, f528a, "referrer is null");
/* 50 */                return;
                    }
/* 42 */            if (!string.contains("=")) {
/* 43 */                C0827el.m885a(4, f528a, "referrer is before decoding: " + string);
/* 44 */                string = C0843fb.m967c(string);
/* 45 */                C0827el.m885a(4, f528a, "referrer is: " + string);
                    }
/* 49 */            new C0785cv(context).m554a(string);
                }
            }
