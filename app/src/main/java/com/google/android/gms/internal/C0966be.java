            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            
            public final class C0966be implements SafeParcelable {
                public static final C0965bd CREATOR = new C0965bd();

                
                public final String f1423fA;

                
                public final String f1424fB;

                
                public final String f1425fC;

                
                public final String f1426fy;

                
                public final String f1427fz;
                public final String mimeType;
                public final String packageName;
                public final int versionCode;

                public C0966be(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
                    this.versionCode = i;
                    this.f1426fy = str;
                    this.f1427fz = str2;
                    this.mimeType = str3;
                    this.packageName = str4;
                    this.f1423fA = str5;
                    this.f1424fB = str6;
                    this.f1425fC = str7;
                }

                public C0966be(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
                    this(1, str, str2, str3, str4, str5, str6, str7);
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    C0965bd.m1297a(this, out, flags);
                }
            }
