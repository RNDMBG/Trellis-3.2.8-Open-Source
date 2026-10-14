            package com.google.android.gms.common;

            import android.app.Activity;
            import android.app.PendingIntent;
            import android.content.IntentSender;
            import com.google.android.gms.internal.C1027dl;
            
            
            public final class ConnectionResult {
                public static final int DATE_INVALID = 12;
                public static final int DEVELOPER_ERROR = 10;
                public static final int INTERNAL_ERROR = 8;
                public static final int INVALID_ACCOUNT = 5;
                public static final int LICENSE_CHECK_FAILED = 11;
                public static final int NETWORK_ERROR = 7;
                public static final int RESOLUTION_REQUIRED = 6;
                public static final int SERVICE_DISABLED = 3;
                public static final int SERVICE_INVALID = 9;
                public static final int SERVICE_MISSING = 1;
                public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
                public static final int SIGN_IN_REQUIRED = 4;
                public static final int SUCCESS = 0;

                
                public static final ConnectionResult f928iP = new ConnectionResult(0, null);

                
                private final int f929iC;
                private final PendingIntent mPendingIntent;

                public ConnectionResult(int statusCode, PendingIntent pendingIntent) {
                    this.f929iC = statusCode;
                    this.mPendingIntent = pendingIntent;
                }

                
                private String m1010aH() {
                    switch (this.f929iC) {
                        case 0:
                            return "SUCCESS";
                        case 1:
                            return "SERVICE_MISSING";
                        case 2:
                            return "SERVICE_VERSION_UPDATE_REQUIRED";
                        case 3:
                            return "SERVICE_DISABLED";
                        case 4:
                            return "SIGN_IN_REQUIRED";
                        case 5:
                            return "INVALID_ACCOUNT";
                        case 6:
                            return "RESOLUTION_REQUIRED";
                        case 7:
                            return "NETWORK_ERROR";
                        case 8:
                            return "INTERNAL_ERROR";
                        case 9:
                            return "SERVICE_INVALID";
                        case 10:
                            return "DEVELOPER_ERROR";
                        case 11:
                            return "LICENSE_CHECK_FAILED";
                        default:
                            return "unknown status code " + this.f929iC;
                    }
                }

                public int getErrorCode() {
                    return this.f929iC;
                }

                public PendingIntent getResolution() {
                    return this.mPendingIntent;
                }

                public boolean hasResolution() {
                    return (this.f929iC == 0 || this.mPendingIntent == null) ? false : true;
                }

                public boolean isSuccess() {
                    return this.f929iC == 0;
                }

                public void startResolutionForResult(Activity activity, int requestCode) throws IntentSender.SendIntentException {
                    if (hasResolution()) {
                        activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), requestCode, null, 0, 0, 0);
                    }
                }

                public String toString() {
                    return C1027dl.m1599d(this).m1600a("statusCode", m1010aH()).m1600a("resolution", this.mPendingIntent).toString();
                }
            }
