            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class OfferWalletObject implements SafeParcelable {
                public static final Parcelable.Creator<OfferWalletObject> CREATOR = new C1216j();

                /* JADX INFO: renamed from: iM */
                private final int f2519iM;

                /* JADX INFO: renamed from: tU */
                String f2520tU;

                /* JADX INFO: renamed from: ul */
                String f2521ul;

                public OfferWalletObject() {
                    this.f2519iM = 2;
                }

                OfferWalletObject(int versionCode, String id, String redemptionCode) {
                    this.f2519iM = versionCode;
                    this.f2520tU = id;
                    this.f2521ul = redemptionCode;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public String getId() {
                    return this.f2520tU;
                }

                public String getRedemptionCode() {
                    return this.f2521ul;
                }

                public int getVersionCode() {
                    return this.f2519iM;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    C1216j.m2369a(this, dest, flags);
                }
            }
