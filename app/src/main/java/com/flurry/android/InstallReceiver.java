            package com.flurry.android;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import com.flurry.sdk.C0785cv;
            import com.flurry.sdk.C0827el;
            import com.flurry.sdk.C0843fb;
            
            
    public final class InstallReceiver extends BroadcastReceiver {

                
                static final String f528a = InstallReceiver.class.getSimpleName();

                @Override 
        public void onReceive(Context context, Intent intent) {
            C0827el.m885a(4, f528a, "Received an Install nofication of " + intent.getAction());
            String string = intent.getExtras().getString("referrer");
            C0827el.m885a(4, f528a, "Received an Install referrer of " + string);
            if (string == null || !"com.android.vending.INSTALL_REFERRER".equals(intent.getAction())) {
                C0827el.m885a(5, f528a, "referrer is null");
                return;
                    }
            if (!string.contains("=")) {
                C0827el.m885a(4, f528a, "referrer is before decoding: " + string);
                string = C0843fb.m967c(string);
                C0827el.m885a(4, f528a, "referrer is: " + string);
                    }
            new C0785cv(context).m554a(string);
                }
            }
