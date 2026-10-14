            package com.google.android.gms.auth;

            import android.content.Intent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class GooglePlayServicesAvailabilityException extends UserRecoverableAuthException {

                /* JADX INFO: renamed from: iL */
                private final int f926iL;

                GooglePlayServicesAvailabilityException(int connectionStatusCode, String msg, Intent intent) {
                    super(msg, intent);
                    this.f926iL = connectionStatusCode;
                }

                public int getConnectionStatusCode() {
                    return this.f926iL;
                }
            }
