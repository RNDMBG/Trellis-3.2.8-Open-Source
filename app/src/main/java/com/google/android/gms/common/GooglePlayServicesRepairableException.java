            package com.google.android.gms.common;

            import android.content.Intent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class GooglePlayServicesRepairableException extends UserRecoverableException {

                /* JADX INFO: renamed from: iL */
                private final int f930iL;

                GooglePlayServicesRepairableException(int connectionStatusCode, String msg, Intent intent) {
                    super(msg, intent);
                    this.f930iL = connectionStatusCode;
                }

                public int getConnectionStatusCode() {
                    return this.f930iL;
                }
            }
