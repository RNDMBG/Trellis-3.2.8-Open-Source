            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class FullWalletRequest implements SafeParcelable {
                public static final Parcelable.Creator<FullWalletRequest> CREATOR = new C1210d();

                /* JADX INFO: renamed from: iM */
                private final int f2476iM;

                /* JADX INFO: renamed from: tH */
                String f2477tH;

                /* JADX INFO: renamed from: tI */
                String f2478tI;

                /* JADX INFO: renamed from: tO */
                Cart f2479tO;

                public final class Builder {
                    private Builder() {
                    }

                    /* synthetic */ Builder(FullWalletRequest x0, Object x1) {
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

                @Override // android.os.Parcelable
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

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    C1210d.m2351a(this, dest, flags);
                }
            }
