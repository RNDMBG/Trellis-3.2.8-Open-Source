            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            public class LoyaltyWalletObject implements SafeParcelable {
                public static final Parcelable.Creator<LoyaltyWalletObject> CREATOR = new C1212f();

                
                private final int f2488iM;

                
                String f2489tU;

                
                String f2490tV;

                
                String f2491tW;

                
                String f2492tX;

                
                String f2493tY;

                public LoyaltyWalletObject() {
                    this.f2488iM = 2;
                }

                LoyaltyWalletObject(int versionCode, String id, String accountId, String issuerName, String programName, String accountName) {
                    this.f2488iM = versionCode;
                    this.f2489tU = id;
                    this.f2490tV = accountId;
                    this.f2491tW = issuerName;
                    this.f2492tX = programName;
                    this.f2493tY = accountName;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public String getAccountId() {
                    return this.f2490tV;
                }

                public String getAccountName() {
                    return this.f2493tY;
                }

                public String getId() {
                    return this.f2489tU;
                }

                public String getIssuerName() {
                    return this.f2491tW;
                }

                public String getProgramName() {
                    return this.f2492tX;
                }

                public int getVersionCode() {
                    return this.f2488iM;
                }

                @Override 
                public void writeToParcel(Parcel dest, int flags) {
                    C1212f.m2357a(this, dest, flags);
                }
            }
