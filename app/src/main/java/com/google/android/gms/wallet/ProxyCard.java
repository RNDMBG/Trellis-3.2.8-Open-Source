            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            public final class ProxyCard implements SafeParcelable {
                public static final Parcelable.Creator<ProxyCard> CREATOR = new C1217k();

                
                private final int f2522iM;

                
                String f2523um;

                
                String f2524un;

                
                int f2525uo;

                
                int f2526up;

                ProxyCard(int versionCode, String pan, String cvn, int expirationMonth, int expirationYear) {
                    this.f2522iM = versionCode;
                    this.f2523um = pan;
                    this.f2524un = cvn;
                    this.f2525uo = expirationMonth;
                    this.f2526up = expirationYear;
                }

                public ProxyCard(String pan, String cvn, int expirationMonth, int expirationYear) {
                    this.f2522iM = 1;
                    this.f2523um = pan;
                    this.f2524un = cvn;
                    this.f2525uo = expirationMonth;
                    this.f2526up = expirationYear;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public String getCvn() {
                    return this.f2524un;
                }

                public int getExpirationMonth() {
                    return this.f2525uo;
                }

                public int getExpirationYear() {
                    return this.f2526up;
                }

                public String getPan() {
                    return this.f2523um;
                }

                public int getVersionCode() {
                    return this.f2522iM;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    C1217k.m2372a(this, out, flags);
                }
            }
