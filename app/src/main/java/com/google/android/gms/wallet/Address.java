            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            public final class Address implements SafeParcelable {
                public static final Parcelable.Creator<Address> CREATOR = new C1207a();

                
                String f2452hl;

                
                private final int f2453iM;
                String name;

                
                String f2454tA;

                
                boolean f2455tB;

                
                String f2456tC;

                
                String f2457tu;

                
                String f2458tv;

                
                String f2459tw;

                
                String f2460tx;

                
                String f2461ty;

                
                String f2462tz;

                public Address() {
                    this.f2453iM = 1;
                }

                Address(int versionCode, String name, String address1, String address2, String address3, String countryCode, String city, String state, String postalCode, String phoneNumber, boolean isPostBox, String companyName) {
                    this.f2453iM = versionCode;
                    this.name = name;
                    this.f2457tu = address1;
                    this.f2458tv = address2;
                    this.f2459tw = address3;
                    this.f2452hl = countryCode;
                    this.f2460tx = city;
                    this.f2461ty = state;
                    this.f2462tz = postalCode;
                    this.f2454tA = phoneNumber;
                    this.f2455tB = isPostBox;
                    this.f2456tC = companyName;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public String getAddress1() {
                    return this.f2457tu;
                }

                public String getAddress2() {
                    return this.f2458tv;
                }

                public String getAddress3() {
                    return this.f2459tw;
                }

                public String getCity() {
                    return this.f2460tx;
                }

                public String getCompanyName() {
                    return this.f2456tC;
                }

                public String getCountryCode() {
                    return this.f2452hl;
                }

                public String getName() {
                    return this.name;
                }

                public String getPhoneNumber() {
                    return this.f2454tA;
                }

                public String getPostalCode() {
                    return this.f2462tz;
                }

                public String getState() {
                    return this.f2461ty;
                }

                public int getVersionCode() {
                    return this.f2453iM;
                }

                public boolean isPostBox() {
                    return this.f2455tB;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    C1207a.m2342a(this, out, flags);
                }
            }
