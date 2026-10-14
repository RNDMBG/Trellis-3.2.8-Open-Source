            package com.google.android.gms.ads.mediation.admob;

            import android.os.Bundle;
            import com.google.ads.mediation.NetworkExtras;
            
            
            public final class AdMobExtras implements NetworkExtras {

                
                private final Bundle f908im;

                public AdMobExtras(Bundle extras) {
                    this.f908im = extras != null ? new Bundle(extras) : null;
                }

                public Bundle getExtras() {
                    return this.f908im;
                }
            }
