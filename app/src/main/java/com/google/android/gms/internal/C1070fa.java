            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.location.Geofence;
            import java.util.Locale;
            
            
            
            public class C1070fa implements SafeParcelable, Geofence {
                public static final C1071fb CREATOR = new C1071fb();

                
                private final int f1890iM;

                
                private final String f1891oA;

                
                private final int f1892oB;

                
                private final short f1893oD;

                
                private final double f1894oE;

                
                private final double f1895oF;

                
                private final float f1896oG;

                
                private final int f1897oH;

                
                private final int f1898oI;

                
                private final long f1899pc;

                public C1070fa(int i, String str, int i2, short s, double d, double d2, float f, long j, int i3, int i4) {
                    m1926R(str);
                    m1930b(f);
                    m1927a(d, d2);
                    int iM1928aa = m1928aa(i2);
                    this.f1890iM = i;
                    this.f1893oD = s;
                    this.f1891oA = str;
                    this.f1894oE = d;
                    this.f1895oF = d2;
                    this.f1896oG = f;
                    this.f1899pc = j;
                    this.f1892oB = iM1928aa;
                    this.f1897oH = i3;
                    this.f1898oI = i4;
                }

                public C1070fa(String str, int i, short s, double d, double d2, float f, long j, int i2, int i3) {
                    this(1, str, i, s, d, d2, f, j, i2, i3);
                }

                
                private static void m1926R(String str) {
                    if (str == null || str.length() > 100) {
                        throw new IllegalArgumentException("requestId is null or too long: " + str);
                    }
                }

                
                private static void m1927a(double d, double d2) {
                    if (d > 90.0d || d < -90.0d) {
                        throw new IllegalArgumentException("invalid latitude: " + d);
                    }
                    if (d2 > 180.0d || d2 < -180.0d) {
                        throw new IllegalArgumentException("invalid longitude: " + d2);
                    }
                }

                
                private static int m1928aa(int i) {
                    int i2 = i & 7;
                    if (i2 == 0) {
                        throw new IllegalArgumentException("No supported transition specified: " + i);
                    }
                    return i2;
                }

                
                private static String m1929ab(int i) {
                    switch (i) {
                        case 1:
                            return "CIRCLE";
                        default:
                            return null;
                    }
                }

                
                private static void m1930b(float f) {
                    if (f <= 0.0f) {
                        throw new IllegalArgumentException("invalid radius: " + f);
                    }
                }

                
                public static C1070fa m1931d(byte[] bArr) {
                    Parcel parcelObtain = Parcel.obtain();
                    parcelObtain.unmarshall(bArr, 0, bArr.length);
                    parcelObtain.setDataPosition(0);
                    C1070fa c1070faM1938z = CREATOR.m1938z(parcelObtain);
                    parcelObtain.recycle();
                    return c1070faM1938z;
                }

                
                public short m1932co() {
                    return this.f1893oD;
                }

                
                public float m1933cp() {
                    return this.f1896oG;
                }

                
                public int m1934cq() {
                    return this.f1892oB;
                }

                
                public int m1935cr() {
                    return this.f1898oI;
                }

                @Override 
                public int describeContents() {
                    C1071fb c1071fb = CREATOR;
                    return 0;
                }

                public boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj != null && (obj instanceof C1070fa)) {
                        C1070fa c1070fa = (C1070fa) obj;
                        return this.f1896oG == c1070fa.f1896oG && this.f1894oE == c1070fa.f1894oE && this.f1895oF == c1070fa.f1895oF && this.f1893oD == c1070fa.f1893oD;
                    }
                    return false;
                }

                public long getExpirationTime() {
                    return this.f1899pc;
                }

                public double getLatitude() {
                    return this.f1894oE;
                }

                public double getLongitude() {
                    return this.f1895oF;
                }

                public int getNotificationResponsiveness() {
                    return this.f1897oH;
                }

                @Override 
                public String getRequestId() {
                    return this.f1891oA;
                }

                public int getVersionCode() {
                    return this.f1890iM;
                }

                public int hashCode() {
                    long jDoubleToLongBits = Double.doubleToLongBits(this.f1894oE);
                    int i = ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32))) + 31;
                    long jDoubleToLongBits2 = Double.doubleToLongBits(this.f1895oF);
                    return (((((((i * 31) + ((int) (jDoubleToLongBits2 ^ (jDoubleToLongBits2 >>> 32)))) * 31) + Float.floatToIntBits(this.f1896oG)) * 31) + this.f1893oD) * 31) + this.f1892oB;
                }

                public String toString() {
                    return String.format(Locale.US, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", m1929ab(this.f1893oD), this.f1891oA, Integer.valueOf(this.f1892oB), Double.valueOf(this.f1894oE), Double.valueOf(this.f1895oF), Float.valueOf(this.f1896oG), Integer.valueOf(this.f1897oH / 1000), Integer.valueOf(this.f1898oI), Long.valueOf(this.f1899pc));
                }

                @Override 
                public void writeToParcel(Parcel parcel, int flags) {
                    C1071fb c1071fb = CREATOR;
                    C1071fb.m1936a(this, parcel, flags);
                }
            }
