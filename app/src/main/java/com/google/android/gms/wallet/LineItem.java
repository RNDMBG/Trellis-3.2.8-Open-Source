            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class LineItem implements SafeParcelable {
                public static final Parcelable.Creator<LineItem> CREATOR = new C1211e();
                String description;

                /* JADX INFO: renamed from: iM */
                private final int f2481iM;

                /* JADX INFO: renamed from: tD */
                String f2482tD;

                /* JADX INFO: renamed from: tE */
                String f2483tE;

                /* JADX INFO: renamed from: tQ */
                String f2484tQ;

                /* JADX INFO: renamed from: tR */
                String f2485tR;

                /* JADX INFO: renamed from: tS */
                int f2486tS;

                public final class Builder {
                    private Builder() {
                    }

                    /* synthetic */ Builder(LineItem x0, Object x1) {
                        this();
                    }

                    public LineItem build() {
                        return LineItem.this;
                    }

                    public Builder setCurrencyCode(String currencyCode) {
                        LineItem.this.f2483tE = currencyCode;
                        return this;
                    }

                    public Builder setDescription(String description) {
                        LineItem.this.description = description;
                        return this;
                    }

                    public Builder setQuantity(String quantity) {
                        LineItem.this.f2484tQ = quantity;
                        return this;
                    }

                    public Builder setRole(int role) {
                        LineItem.this.f2486tS = role;
                        return this;
                    }

                    public Builder setTotalPrice(String totalPrice) {
                        LineItem.this.f2482tD = totalPrice;
                        return this;
                    }

                    public Builder setUnitPrice(String unitPrice) {
                        LineItem.this.f2485tR = unitPrice;
                        return this;
                    }
                }

                public interface Role {
                    public static final int REGULAR = 0;
                    public static final int SHIPPING = 2;
                    public static final int TAX = 1;
                }

                public LineItem() {
                    this.f2486tS = 0;
                    this.f2481iM = 1;
                }

                LineItem(int versionCode, String description, String quantity, String unitPrice, String totalPrice, int role, String currencyCode) {
                    this.f2486tS = 0;
                    this.f2481iM = versionCode;
                    this.description = description;
                    this.f2484tQ = quantity;
                    this.f2485tR = unitPrice;
                    this.f2482tD = totalPrice;
                    this.f2486tS = role;
                    this.f2483tE = currencyCode;
                }

                public static Builder newBuilder() {
                    LineItem lineItem = new LineItem();
                    lineItem.getClass();
                    return new Builder(lineItem, null);
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public String getCurrencyCode() {
                    return this.f2483tE;
                }

                public String getDescription() {
                    return this.description;
                }

                public String getQuantity() {
                    return this.f2484tQ;
                }

                public int getRole() {
                    return this.f2486tS;
                }

                public String getTotalPrice() {
                    return this.f2482tD;
                }

                public String getUnitPrice() {
                    return this.f2485tR;
                }

                public int getVersionCode() {
                    return this.f2481iM;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    C1211e.m2354a(this, dest, flags);
                }
            }
