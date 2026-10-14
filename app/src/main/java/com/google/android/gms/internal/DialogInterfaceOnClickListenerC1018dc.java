            package com.google.android.gms.internal;

            import android.app.Activity;
            import android.content.ActivityNotFoundException;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.util.Log;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dc */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class DialogInterfaceOnClickListenerC1018dc implements DialogInterface.OnClickListener {

                /* JADX INFO: renamed from: fD */
                private final Activity f1670fD;

                /* JADX INFO: renamed from: ky */
                private final int f1671ky;
                private final Intent mIntent;

                public DialogInterfaceOnClickListenerC1018dc(Activity activity, Intent intent, int i) {
                    this.f1670fD = activity;
                    this.mIntent = intent;
                    this.f1671ky = i;
                }

                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (this.mIntent != null) {
                            this.f1670fD.startActivityForResult(this.mIntent, this.f1671ky);
                        }
                        dialog.dismiss();
                    } catch (ActivityNotFoundException e) {
                        Log.e("SettingsRedirect", "Can't redirect to app settings for Google Play services");
                    }
                }
            }
