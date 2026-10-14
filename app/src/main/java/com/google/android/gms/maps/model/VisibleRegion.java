            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.maps.internal.C1174r;
            
            
            public final class VisibleRegion implements SafeParcelable {
                public static final VisibleRegionCreator CREATOR = new VisibleRegionCreator();
                public final LatLng farLeft;
                public final LatLng farRight;

                
                private final int f2362iM;
                public final LatLngBounds latLngBounds;
                public final LatLng nearLeft;
                public final LatLng nearRight;

                VisibleRegion(int versionCode, LatLng nearLeft, LatLng nearRight, LatLng farLeft, LatLng farRight, LatLngBounds latLngBounds) {
                    this.f2362iM = versionCode;
                    this.nearLeft = nearLeft;
                    this.nearRight = nearRight;
                    this.farLeft = farLeft;
                    this.farRight = farRight;
                    this.latLngBounds = latLngBounds;
                }

                public VisibleRegion(LatLng nearLeft, LatLng nearRight, LatLng farLeft, LatLng farRight, LatLngBounds latLngBounds) {
                    this(1, nearLeft, nearRight, farLeft, farRight, latLngBounds);
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (!(o instanceof VisibleRegion)) {
                        return false;
                    }
                    VisibleRegion visibleRegion = (VisibleRegion) o;
                    return this.nearLeft.equals(visibleRegion.nearLeft) && this.nearRight.equals(visibleRegion.nearRight) && this.farLeft.equals(visibleRegion.farLeft) && this.farRight.equals(visibleRegion.farRight) && this.latLngBounds.equals(visibleRegion.latLngBounds);
                }

                int getVersionCode() {
                    return this.f2362iM;
                }

                public int hashCode() {
                    return C1027dl.hashCode(this.nearLeft, this.nearRight, this.farLeft, this.farRight, this.latLngBounds);
                }

                public String toString() {
                    return C1027dl.m1599d(this).m1600a("nearLeft", this.nearLeft).m1600a("nearRight", this.nearRight).m1600a("farLeft", this.farLeft).m1600a("farRight", this.farRight).m1600a("latLngBounds", this.latLngBounds).toString();
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1195k.m2328a(this, out, flags);
                    } else {
                        VisibleRegionCreator.m2294a(this, out, flags);
                    }
                }
            }
