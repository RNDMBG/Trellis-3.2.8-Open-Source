            package com.google.android.gms.common.data;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            
            public class C0890c<T extends SafeParcelable> extends DataBuffer<T> {

                
                private static final String[] f954jk = {"data"};

                
                private final Parcelable.Creator<T> f955jl;

                public C0890c(C0891d c0891d, Parcelable.Creator<T> creator) {
                    super(c0891d);
                    this.f955jl = creator;
                }

                @Override 
                public  Object get(int x0) {
                    return m1032p(x0);
                }

                
                public T m1032p(int i) {
                    byte[] bArrM1047e = this.f948jf.m1047e("data", i, 0);
                    Parcel parcelObtain = Parcel.obtain();
                    parcelObtain.unmarshall(bArrM1047e, 0, bArrM1047e.length);
                    parcelObtain.setDataPosition(0);
                    T tCreateFromParcel = this.f955jl.createFromParcel(parcelObtain);
                    parcelObtain.recycle();
                    return tCreateFromParcel;
                }
            }
