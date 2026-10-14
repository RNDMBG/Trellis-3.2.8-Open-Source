            package com.google.android.gms.ads.mediation.admob;

            import android.os.Bundle;
            import com.google.ads.mediation.NetworkExtras;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class AdMobExtras implements NetworkExtras {

                /* JADX INFO: renamed from: im */
                private final Bundle f908im;

                public AdMobExtras(Bundle extras) {
                    this.f908im = extras != null ? new Bundle(extras) : null;
                }

                public Bundle getExtras() {
                    return this.f908im;
                }
            }
