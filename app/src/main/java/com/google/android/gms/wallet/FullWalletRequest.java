            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            public class FullWalletRequest implements SafeParcelable {
                public static final Parcelable.Creator<FullWalletRequest> CREATOR = new C1210d();

                
                private final int f2476iM;

                
                String f2477tH;

                
                String f2478tI;

                
                Cart f2479tO;

                public final class Builder {
                    private Builder() {
                    }

                     Builder(FullWalletRequest x0, Object x1) {
                        this();
                    }

                    public FullWalletRequest build() {
                        return FullWalletRequest.this;
                    }

                    public Builder setCart(Cart cart) {
                        FullWalletRequest.this.f2479tO = cart;
                        return this;
                    }

                    public Builder setGoogleTransactionId(String googleTransactionId) {
                        FullWalletRequest.this.f2477tH = googleTransactionId;
                        return this;
                    }

                    public Builder setMerchantTransactionId(String merchantTransactionId) {
                        FullWalletRequest.this.f2478tI = merchantTransactionId;
                        return this;
                    }
                }

                public FullWalletRequest() {
                    this.f2476iM = 1;
                }

                FullWalletRequest(int versionCode, String googleTransactionId, String merchantTransactionId, Cart cart) {
                    this.f2476iM = versionCode;
                    this.f2477tH = googleTransactionId;
                    this.f2478tI = merchantTransactionId;
                    this.f2479tO = cart;
                }

                public static Builder newBuilder() {
                    FullWalletRequest fullWalletRequest = new FullWalletRequest();
                    fullWalletRequest.getClass();
                    return new Builder(fullWalletRequest, null);
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public Cart getCart() {
                    return this.f2479tO;
                }

                public String getGoogleTransactionId() {
                    return this.f2477tH;
                }

                public String getMerchantTransactionId() {
                    return this.f2478tI;
                }

                public int getVersionCode() {
                    return this.f2476iM;
                }

                @Override 
                public void writeToParcel(Parcel dest, int flags) {
                    C1210d.m2351a(this, dest, flags);
                }
            }
