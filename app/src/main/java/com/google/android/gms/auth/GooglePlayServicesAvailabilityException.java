            package com.google.android.gms.auth;

            import android.content.Intent;
            
            
            public class GooglePlayServicesAvailabilityException extends UserRecoverableAuthException {

                
                private final int f926iL;

                GooglePlayServicesAvailabilityException(int connectionStatusCode, String msg, Intent intent) {
                    super(msg, intent);
                    this.f926iL = connectionStatusCode;
                }

                public int getConnectionStatusCode() {
                    return this.f926iL;
                }
            }
