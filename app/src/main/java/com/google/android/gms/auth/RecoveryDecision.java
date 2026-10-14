            package com.google.android.gms.auth;

            import android.app.PendingIntent;
            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            public class RecoveryDecision implements SafeParcelable {
                public static final RecoveryDecisionCreator CREATOR = new RecoveryDecisionCreator();

                
                final int f927iM;
                public boolean isRecoveryInfoNeeded;
                public boolean isRecoveryInterstitialAllowed;
                public PendingIntent recoveryIntent;
                public PendingIntent recoveryIntentWithoutIntro;
                public boolean showRecoveryInterstitial;

                public RecoveryDecision() {
                    this.f927iM = 1;
                }

                RecoveryDecision(int versionCode, PendingIntent recoveryIntent, boolean showRecoveryInterstitial, boolean isRecoveryInfoNeeded, boolean isRecoveryInterstitialAllowed, PendingIntent recoveryIntentWithoutIntro) {
                    this.f927iM = versionCode;
                    this.recoveryIntent = recoveryIntent;
                    this.showRecoveryInterstitial = showRecoveryInterstitial;
                    this.isRecoveryInfoNeeded = isRecoveryInfoNeeded;
                    this.isRecoveryInterstitialAllowed = isRecoveryInterstitialAllowed;
                    this.recoveryIntentWithoutIntro = recoveryIntentWithoutIntro;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public int getVersionCode() {
                    return this.f927iM;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    RecoveryDecisionCreator.m1007a(this, out, flags);
                }
            }
