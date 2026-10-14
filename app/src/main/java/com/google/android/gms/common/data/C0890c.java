            package com.google.android.gms.common.data;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            /* JADX INFO: renamed from: com.google.android.gms.common.data.c */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C0890c<T extends SafeParcelable> extends DataBuffer<T> {

                /* JADX INFO: renamed from: jk */
                private static final String[] f954jk = {"data"};

                /* JADX INFO: renamed from: jl */
                private final Parcelable.Creator<T> f955jl;

                public C0890c(C0891d c0891d, Parcelable.Creator<T> creator) {
                    super(c0891d);
                    this.f955jl = creator;
                }

                @Override // com.google.android.gms.common.data.DataBuffer
                public /* synthetic */ Object get(int x0) {
                    return m1032p(x0);
                }

                /* JADX INFO: renamed from: p */
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
