            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import java.util.Collections;
            import java.util.List;
            
            
            
            public final class C0984bw implements SafeParcelable {
                public static final C0985bx CREATOR = new C0985bx();

                
                public final List<String> f1508eW;

                
                public final List<String> f1509eX;
                public final int errorCode;

                
                public final String f1510fW;

                
                public final long f1511fa;

                
                public final String f1512gG;

                
                public final long f1513gH;

                
                public final boolean f1514gI;

                
                public final long f1515gJ;

                
                public final List<String> f1516gK;
                public final int orientation;
                public final int versionCode;

                public C0984bw(int i) {
                    this(1, null, null, null, i, null, -1L, false, -1L, null, -1L, -1);
                }

                C0984bw(int i, String str, String str2, List<String> list, int i2, List<String> list2, long j, boolean z, long j2, List<String> list3, long j3, int i3) {
                    this.versionCode = i;
                    this.f1510fW = str;
                    this.f1512gG = str2;
                    this.f1508eW = list != null ? Collections.unmodifiableList(list) : null;
                    this.errorCode = i2;
                    this.f1509eX = list2 != null ? Collections.unmodifiableList(list2) : null;
                    this.f1513gH = j;
                    this.f1514gI = z;
                    this.f1515gJ = j2;
                    this.f1516gK = list3 != null ? Collections.unmodifiableList(list3) : null;
                    this.f1511fa = j3;
                    this.orientation = i3;
                }

                public C0984bw(String str, String str2, List<String> list, List<String> list2, long j, boolean z, long j2, List<String> list3, long j3, int i) {
                    this(1, str, str2, list, -2, list2, j, z, j2, list3, j3, i);
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    C0985bx.m1374a(this, out, flags);
                }
            }
