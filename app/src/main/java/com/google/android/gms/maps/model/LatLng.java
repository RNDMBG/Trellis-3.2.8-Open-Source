            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.maps.internal.C1174r;
            
            
            public final class LatLng implements SafeParcelable {
                public static final LatLngCreator CREATOR = new LatLngCreator();

                
                private final int f2311iM;
                public final double latitude;
                public final double longitude;

                public LatLng(double latitude, double longitude) {
                    this(1, latitude, longitude);
                }

                LatLng(int versionCode, double latitude, double longitude) {
                    this.f2311iM = versionCode;
                    if (-180.0d > longitude || longitude >= 180.0d) {
                        this.longitude = ((((longitude - 180.0d) % 360.0d) + 360.0d) % 360.0d) - 180.0d;
                    } else {
                        this.longitude = longitude;
                    }
                    this.latitude = Math.max(-90.0d, Math.min(90.0d, latitude));
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (!(o instanceof LatLng)) {
                        return false;
                    }
                    LatLng latLng = (LatLng) o;
                    return Double.doubleToLongBits(this.latitude) == Double.doubleToLongBits(latLng.latitude) && Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(latLng.longitude);
                }

                int getVersionCode() {
                    return this.f2311iM;
                }

                public int hashCode() {
                    long jDoubleToLongBits = Double.doubleToLongBits(this.latitude);
                    int i = ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32))) + 31;
                    long jDoubleToLongBits2 = Double.doubleToLongBits(this.longitude);
                    return (i * 31) + ((int) (jDoubleToLongBits2 ^ (jDoubleToLongBits2 >>> 32)));
                }

                public String toString() {
                    return "lat/lng: (" + this.latitude + "," + this.longitude + ")";
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1181e.m2299a(this, out, flags);
                    } else {
                        LatLngCreator.m2282a(this, out, flags);
                    }
                }
            }
