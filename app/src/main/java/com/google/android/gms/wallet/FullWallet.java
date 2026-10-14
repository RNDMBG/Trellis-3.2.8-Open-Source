            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            public final class FullWallet implements SafeParcelable {
                public static final Parcelable.Creator<FullWallet> CREATOR = new C1209c();

                
                private final int f2468iM;

                
                String f2469tH;

                
                String f2470tI;

                
                ProxyCard f2471tJ;

                
                String f2472tK;

                
                Address f2473tL;

                
                Address f2474tM;

                
                String[] f2475tN;

                public FullWallet() {
                    this.f2468iM = 1;
                }

                FullWallet(int versionCode, String googleTransactionId, String merchantTransactionId, ProxyCard proxyCard, String email, Address billingAddress, Address shippingAddress, String[] paymentDescriptions) {
                    this.f2468iM = versionCode;
                    this.f2469tH = googleTransactionId;
                    this.f2470tI = merchantTransactionId;
                    this.f2471tJ = proxyCard;
                    this.f2472tK = email;
                    this.f2473tL = billingAddress;
                    this.f2474tM = shippingAddress;
                    this.f2475tN = paymentDescriptions;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public Address getBillingAddress() {
                    return this.f2473tL;
                }

                public String getEmail() {
                    return this.f2472tK;
                }

                public String getGoogleTransactionId() {
                    return this.f2469tH;
                }

                public String getMerchantTransactionId() {
                    return this.f2470tI;
                }

                public String[] getPaymentDescriptions() {
                    return this.f2475tN;
                }

                public ProxyCard getProxyCard() {
                    return this.f2471tJ;
                }

                public Address getShippingAddress() {
                    return this.f2474tM;
                }

                public int getVersionCode() {
                    return this.f2468iM;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    C1209c.m2348a(this, out, flags);
                }
            }
