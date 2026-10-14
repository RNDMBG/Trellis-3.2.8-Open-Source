            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            
            public final class C1003co implements SafeParcelable {
                public static final C1004cp CREATOR = new C1004cp();

                
                public String f1596hP;

                
                public int f1597hQ;

                
                public int f1598hR;

                
                public boolean f1599hS;
                public final int versionCode;

                public C1003co(int i, int i2, boolean z) {
                    this(1, "afma-sdk-a-v" + i + "." + i2 + "." + (z ? "0" : "1"), i, i2, z);
                }

                C1003co(int i, String str, int i2, int i3, boolean z) {
                    this.versionCode = i;
                    this.f1596hP = str;
                    this.f1597hQ = i2;
                    this.f1598hR = i3;
                    this.f1599hS = z;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    C1004cp.m1459a(this, out, flags);
                }
            }
