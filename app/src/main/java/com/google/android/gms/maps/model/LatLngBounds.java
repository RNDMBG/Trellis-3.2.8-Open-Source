            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.internal.C1174r;
            
            
            public final class LatLngBounds implements SafeParcelable {
                public static final LatLngBoundsCreator CREATOR = new LatLngBoundsCreator();

                
                private final int f2312iM;
                public final LatLng northeast;
                public final LatLng southwest;

                public static final class Builder {

                    
                    private double f2313qv = Double.POSITIVE_INFINITY;

                    
                    private double f2314qw = Double.NEGATIVE_INFINITY;

                    
                    private double f2315qx = Double.NaN;

                    
                    private double f2316qy = Double.NaN;

                    
                    private boolean m2280b(double d) {
                        if (this.f2315qx <= this.f2316qy) {
                            return this.f2315qx <= d && d <= this.f2316qy;
                        }
                        return this.f2315qx <= d || d <= this.f2316qy;
                    }

                    public LatLngBounds build() {
                        C1028dm.m1602a(!Double.isNaN(this.f2315qx), "no included points");
                        return new LatLngBounds(new LatLng(this.f2313qv, this.f2315qx), new LatLng(this.f2314qw, this.f2316qy));
                    }

                    public Builder include(LatLng point) {
                        this.f2313qv = Math.min(this.f2313qv, point.latitude);
                        this.f2314qw = Math.max(this.f2314qw, point.latitude);
                        double d = point.longitude;
                        if (Double.isNaN(this.f2315qx)) {
                            this.f2315qx = d;
                            this.f2316qy = d;
                        } else if (!m2280b(d)) {
                            if (LatLngBounds.m2278d(this.f2315qx, d) < LatLngBounds.m2279e(this.f2316qy, d)) {
                                this.f2315qx = d;
                            } else {
                                this.f2316qy = d;
                            }
                        }
                        return this;
                    }
                }

                LatLngBounds(int versionCode, LatLng southwest, LatLng northeast) {
                    C1028dm.m1601a(southwest, "null southwest");
                    C1028dm.m1601a(northeast, "null northeast");
                    C1028dm.m1603a(northeast.latitude >= southwest.latitude, "southern latitude exceeds northern latitude (%s > %s)", Double.valueOf(southwest.latitude), Double.valueOf(northeast.latitude));
                    this.f2312iM = versionCode;
                    this.southwest = southwest;
                    this.northeast = northeast;
                }

                public LatLngBounds(LatLng southwest, LatLng northeast) {
                    this(1, southwest, northeast);
                }

                
                private boolean m2274a(double d) {
                    return this.southwest.latitude <= d && d <= this.northeast.latitude;
                }

                
                private static double m2275b(double d, double d2) {
                    return ((d - d2) + 360.0d) % 360.0d;
                }

                
                private boolean m2276b(double d) {
                    if (this.southwest.longitude <= this.northeast.longitude) {
                        return this.southwest.longitude <= d && d <= this.northeast.longitude;
                    }
                    return this.southwest.longitude <= d || d <= this.northeast.longitude;
                }

                public static Builder builder() {
                    return new Builder();
                }

                
                private static double m2277c(double d, double d2) {
                    return ((d2 - d) + 360.0d) % 360.0d;
                }

                
                static  double m2278d(double d, double d2) {
                    return m2275b(d, d2);
                }

                
                static  double m2279e(double d, double d2) {
                    return m2277c(d, d2);
                }

                public boolean contains(LatLng point) {
                    return m2274a(point.latitude) && m2276b(point.longitude);
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (!(o instanceof LatLngBounds)) {
                        return false;
                    }
                    LatLngBounds latLngBounds = (LatLngBounds) o;
                    return this.southwest.equals(latLngBounds.southwest) && this.northeast.equals(latLngBounds.northeast);
                }

                public LatLng getCenter() {
                    double d = (this.southwest.latitude + this.northeast.latitude) / 2.0d;
                    double d2 = this.northeast.longitude;
                    double d3 = this.southwest.longitude;
                    return new LatLng(d, d3 <= d2 ? (d2 + d3) / 2.0d : ((d2 + 360.0d) + d3) / 2.0d);
                }

                int getVersionCode() {
                    return this.f2312iM;
                }

                public int hashCode() {
                    return C1027dl.hashCode(this.southwest, this.northeast);
                }

                public LatLngBounds including(LatLng point) {
                    double d;
                    double dMin = Math.min(this.southwest.latitude, point.latitude);
                    double dMax = Math.max(this.northeast.latitude, point.latitude);
                    double d2 = this.northeast.longitude;
                    double d3 = this.southwest.longitude;
                    double d4 = point.longitude;
                    if (m2276b(d4)) {
                        d4 = d3;
                        d = d2;
                    } else if (m2275b(d3, d4) < m2277c(d2, d4)) {
                        d = d2;
                    } else {
                        d = d4;
                        d4 = d3;
                    }
                    return new LatLngBounds(new LatLng(dMin, d4), new LatLng(dMax, d));
                }

                public String toString() {
                    return C1027dl.m1599d(this).m1600a("southwest", this.southwest).m1600a("northeast", this.northeast).toString();
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1180d.m2298a(this, out, flags);
                    } else {
                        LatLngBoundsCreator.m2281a(this, out, flags);
                    }
                }
            }
