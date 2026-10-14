            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import java.util.ArrayList;
            import java.util.List;
            
            
            public class Cart implements SafeParcelable {
                public static final Parcelable.Creator<Cart> CREATOR = new C1208b();

                
                private final int f2463iM;

                
                String f2464tD;

                
                String f2465tE;

                
                ArrayList<LineItem> f2466tF;

                public final class Builder {
                    private Builder() {
                    }

                     Builder(Cart x0, Object x1) {
                        this();
                    }

                    public Builder addLineItem(LineItem lineItem) {
                        Cart.this.f2466tF.add(lineItem);
                        return this;
                    }

                    public Cart build() {
                        return Cart.this;
                    }

                    public Builder setCurrencyCode(String currencyCode) {
                        Cart.this.f2465tE = currencyCode;
                        return this;
                    }

                    public Builder setLineItems(List<LineItem> lineItems) {
                        Cart.this.f2466tF.clear();
                        Cart.this.f2466tF.addAll(lineItems);
                        return this;
                    }

                    public Builder setTotalPrice(String totalPrice) {
                        Cart.this.f2464tD = totalPrice;
                        return this;
                    }
                }

                public Cart() {
                    this.f2463iM = 1;
                    this.f2466tF = new ArrayList<>();
                }

                Cart(int versionCode, String totalPrice, String currencyCode, ArrayList<LineItem> lineItems) {
                    this.f2463iM = versionCode;
                    this.f2464tD = totalPrice;
                    this.f2465tE = currencyCode;
                    this.f2466tF = lineItems;
                }

                public static Builder newBuilder() {
                    Cart cart = new Cart();
                    cart.getClass();
                    return new Builder(cart, null);
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public String getCurrencyCode() {
                    return this.f2465tE;
                }

                public ArrayList<LineItem> getLineItems() {
                    return this.f2466tF;
                }

                public String getTotalPrice() {
                    return this.f2464tD;
                }

                public int getVersionCode() {
                    return this.f2463iM;
                }

                @Override 
                public void writeToParcel(Parcel dest, int flags) {
                    C1208b.m2345a(this, dest, flags);
                }
            }
