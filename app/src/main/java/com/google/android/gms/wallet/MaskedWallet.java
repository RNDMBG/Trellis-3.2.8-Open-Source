            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class MaskedWallet implements SafeParcelable {
                public static final Parcelable.Creator<MaskedWallet> CREATOR = new C1213g();

                /* JADX INFO: renamed from: iM */
                private final int f2494iM;

                /* JADX INFO: renamed from: tH */
                String f2495tH;

                /* JADX INFO: renamed from: tI */
                String f2496tI;

                /* JADX INFO: renamed from: tK */
                String f2497tK;

                /* JADX INFO: renamed from: tL */
                Address f2498tL;

                /* JADX INFO: renamed from: tM */
                Address f2499tM;

                /* JADX INFO: renamed from: tN */
                String[] f2500tN;

                /* JADX INFO: renamed from: tZ */
                LoyaltyWalletObject[] f2501tZ;

                /* JADX INFO: renamed from: ua */
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

                @Override // android.os.Parcelable
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

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    C1213g.m2360a(this, dest, flags);
                }
            }
