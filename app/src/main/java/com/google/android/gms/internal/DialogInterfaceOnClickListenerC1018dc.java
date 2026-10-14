            package com.google.android.gms.internal;

            import android.app.Activity;
            import android.content.ActivityNotFoundException;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.util.Log;
            
            
            
            public class DialogInterfaceOnClickListenerC1018dc implements DialogInterface.OnClickListener {

                
                private final Activity f1670fD;

                
                private final int f1671ky;
                private final Intent mIntent;

                public DialogInterfaceOnClickListenerC1018dc(Activity activity, Intent intent, int i) {
                    this.f1670fD = activity;
                    this.mIntent = intent;
                    this.f1671ky = i;
                }

                @Override 
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
