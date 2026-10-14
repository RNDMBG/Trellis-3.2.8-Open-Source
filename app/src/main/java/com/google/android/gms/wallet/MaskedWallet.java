            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            public final class MaskedWallet implements SafeParcelable {
                public static final Parcelable.Creator<MaskedWallet> CREATOR = new C1213g();

                
                private final int f2494iM;

                
                String f2495tH;

                
                String f2496tI;

                
                String f2497tK;

                
                Address f2498tL;

                
                Address f2499tM;

                
                String[] f2500tN;

                
                LoyaltyWalletObject[] f2501tZ;

                
                OfferWalletObject[] f2502ua;

                public MaskedWallet() {
                    this.f2494iM = 2;
                }

                MaskedWallet(int versionCode, String googleTransactionId, String merchantTransactionId, String[] paymentDescriptions, String email, Address billingAddress, Address shippingAddress, LoyaltyWalletObject[] loyaltyWalletObjects, OfferWalletObject[] offerWalletObjects) {
                    this.f2494iM = versionCode;
                    this.f2495tH = googleTransactionId;
                    this.f2496tI = merchantTransactionId;
                    this.f2500tN = paymentDescriptions;
                    this.f2497tK = email;
                    this.f2498tL = billingAddress;
                    this.f2499tM = shippingAddress;
                    this.f2501tZ = loyaltyWalletObjects;
                    this.f2502ua = offerWalletObjects;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public Address getBillingAddress() {
                    return this.f2498tL;
                }

                public String getEmail() {
                    return this.f2497tK;
                }

                public String getGoogleTransactionId() {
                    return this.f2495tH;
                }

                public LoyaltyWalletObject[] getLoyaltyWalletObjects() {
                    return this.f2501tZ;
                }

                public String getMerchantTransactionId() {
                    return this.f2496tI;
                }

                public OfferWalletObject[] getOfferWalletObjects() {
                    return this.f2502ua;
                }

                public String[] getPaymentDescriptions() {
                    return this.f2500tN;
                }

                public Address getShippingAddress() {
                    return this.f2499tM;
                }

                public int getVersionCode() {
                    return this.f2494iM;
                }

                @Override 
                public void writeToParcel(Parcel dest, int flags) {
                    C1213g.m2360a(this, dest, flags);
                }
            }
