            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class MaskedWalletRequest implements SafeParcelable {
                public static final Parcelable.Creator<MaskedWalletRequest> CREATOR = new C1214h();

                /* JADX INFO: renamed from: iM */
                private final int f2503iM;

                /* JADX INFO: renamed from: tE */
                String f2504tE;

                /* JADX INFO: renamed from: tI */
                String f2505tI;

                /* JADX INFO: renamed from: tO */
                Cart f2506tO;

                /* JADX INFO: renamed from: ub */
                boolean f2507ub;

                /* JADX INFO: renamed from: uc */
                boolean f2508uc;

                /* JADX INFO: renamed from: ud */
                boolean f2509ud;

                /* JADX INFO: renamed from: ue */
                String f2510ue;

                /* JADX INFO: renamed from: uf */
                String f2511uf;

                /* JADX INFO: renamed from: ug */
                boolean f2512ug;

                /* JADX INFO: renamed from: uh */
                boolean f2513uh;

                public final class Builder {
                    private Builder() {
                    }

                    /* synthetic */ Builder(MaskedWalletRequest x0, Object x1) {
                        this();
                    }

                    public MaskedWalletRequest build() {
                        return MaskedWalletRequest.this;
                    }

                    public Builder setCart(Cart cart) {
                        MaskedWalletRequest.this.f2506tO = cart;
                        return this;
                    }

                    public Builder setCurrencyCode(String currencyCode) {
                        MaskedWalletRequest.this.f2504tE = currencyCode;
                        return this;
                    }

                    public Builder setEstimatedTotalPrice(String estimatedTotalPrice) {
                        MaskedWalletRequest.this.f2510ue = estimatedTotalPrice;
                        return this;
                    }

                    public Builder setIsBillingAgreement(boolean isBillingAgreement) {
                        MaskedWalletRequest.this.f2513uh = isBillingAgreement;
                        return this;
                    }

                    public Builder setMerchantName(String merchantName) {
                        MaskedWalletRequest.this.f2511uf = merchantName;
                        return this;
                    }

                    public Builder setMerchantTransactionId(String merchantTransactionId) {
                        MaskedWalletRequest.this.f2505tI = merchantTransactionId;
                        return this;
                    }

                    public Builder setPhoneNumberRequired(boolean phoneNumberRequired) {
                        MaskedWalletRequest.this.f2507ub = phoneNumberRequired;
                        return this;
                    }

                    public Builder setShippingAddressRequired(boolean shippingAddressRequired) {
                        MaskedWalletRequest.this.f2508uc = shippingAddressRequired;
                        return this;
                    }

                    public Builder setShouldRetrieveWalletObjects(boolean shouldRetrieveWalletObjects) {
                        MaskedWalletRequest.this.f2512ug = shouldRetrieveWalletObjects;
                        return this;
                    }

                    public Builder setUseMinimalBillingAddress(boolean useMinimalBillingAddress) {
                        MaskedWalletRequest.this.f2509ud = useMinimalBillingAddress;
                        return this;
                    }
                }

                public MaskedWalletRequest() {
                    this.f2503iM = 2;
                }

                MaskedWalletRequest(int versionCode, String merchantTransactionId, boolean phoneNumberRequired, boolean shippingAddressRequired, boolean useMinimalBillingAddress, String estimatedTotalPrice, String currencyCode, String merchantName, Cart cart, boolean shouldRetrieveWalletObjects, boolean isBillingAgreement) {
                    this.f2503iM = versionCode;
                    this.f2505tI = merchantTransactionId;
                    this.f2507ub = phoneNumberRequired;
                    this.f2508uc = shippingAddressRequired;
                    this.f2509ud = useMinimalBillingAddress;
                    this.f2510ue = estimatedTotalPrice;
                    this.f2504tE = currencyCode;
                    this.f2511uf = merchantName;
                    this.f2506tO = cart;
                    this.f2512ug = shouldRetrieveWalletObjects;
                    this.f2513uh = isBillingAgreement;
                }

                public static Builder newBuilder() {
                    MaskedWalletRequest maskedWalletRequest = new MaskedWalletRequest();
                    maskedWalletRequest.getClass();
                    return new Builder(maskedWalletRequest, null);
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public Cart getCart() {
                    return this.f2506tO;
                }

                public String getCurrencyCode() {
                    return this.f2504tE;
                }

                public String getEstimatedTotalPrice() {
                    return this.f2510ue;
                }

                public String getMerchantName() {
                    return this.f2511uf;
                }

                public String getMerchantTransactionId() {
                    return this.f2505tI;
                }

                public int getVersionCode() {
                    return this.f2503iM;
                }

                public boolean isBillingAgreement() {
                    return this.f2513uh;
                }

                public boolean isPhoneNumberRequired() {
                    return this.f2507ub;
                }

                public boolean isShippingAddressRequired() {
                    return this.f2508uc;
                }

                public boolean shouldRetrieveWalletObjects() {
                    return this.f2512ug;
                }

                public boolean useMinimalBillingAddress() {
                    return this.f2509ud;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    C1214h.m2363a(this, dest, flags);
                }
            }
