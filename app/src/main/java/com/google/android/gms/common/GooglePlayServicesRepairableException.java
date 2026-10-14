            package com.google.android.gms.common;

            import android.content.Intent;
            
            
            public class GooglePlayServicesRepairableException extends UserRecoverableException {

                
                private final int f930iL;

                GooglePlayServicesRepairableException(int connectionStatusCode, String msg, Intent intent) {
                    super(msg, intent);
                    this.f930iL = connectionStatusCode;
                }

                public int getConnectionStatusCode() {
                    return this.f930iL;
                }
            }
