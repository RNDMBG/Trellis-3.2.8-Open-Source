            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.maps.internal.C1174r;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Iterator;
            import java.util.List;
            
            
            public final class PolylineOptions implements SafeParcelable {
                public static final PolylineOptionsCreator CREATOR = new PolylineOptionsCreator();

                
                private final int f2342iM;

                
                private int f2343jc;

                
                private final List<LatLng> f2344qK;

                
                private boolean f2345qM;

                
                private float f2346qk;

                
                private boolean f2347ql;

                
                private float f2348qp;

                public PolylineOptions() {
                    this.f2348qp = 10.0f;
                    this.f2343jc = -16777216;
                    this.f2346qk = 0.0f;
                    this.f2347ql = true;
                    this.f2345qM = false;
                    this.f2342iM = 1;
                    this.f2344qK = new ArrayList();
                }

                PolylineOptions(int versionCode, List points, float width, int color, float zIndex, boolean visible, boolean geodesic) {
                    this.f2348qp = 10.0f;
                    this.f2343jc = -16777216;
                    this.f2346qk = 0.0f;
                    this.f2347ql = true;
                    this.f2345qM = false;
                    this.f2342iM = versionCode;
                    this.f2344qK = points;
                    this.f2348qp = width;
                    this.f2343jc = color;
                    this.f2346qk = zIndex;
                    this.f2347ql = visible;
                    this.f2345qM = geodesic;
                }

                public PolylineOptions add(LatLng point) {
                    this.f2344qK.add(point);
                    return this;
                }

                public PolylineOptions add(LatLng... points) {
                    this.f2344qK.addAll(Arrays.asList(points));
                    return this;
                }

                public PolylineOptions addAll(Iterable<LatLng> points) {
                    Iterator<LatLng> it = points.iterator();
                    while (it.hasNext()) {
                        this.f2344qK.add(it.next());
                    }
                    return this;
                }

                public PolylineOptions color(int color) {
                    this.f2343jc = color;
                    return this;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public PolylineOptions geodesic(boolean geodesic) {
                    this.f2345qM = geodesic;
                    return this;
                }

                public int getColor() {
                    return this.f2343jc;
                }

                public List<LatLng> getPoints() {
                    return this.f2344qK;
                }

                int getVersionCode() {
                    return this.f2342iM;
                }

                public float getWidth() {
                    return this.f2348qp;
                }

                public float getZIndex() {
                    return this.f2346qk;
                }

                public boolean isGeodesic() {
                    return this.f2345qM;
                }

                public boolean isVisible() {
                    return this.f2347ql;
                }

                public PolylineOptions visible(boolean visible) {
                    this.f2347ql = visible;
                    return this;
                }

                public PolylineOptions width(float width) {
                    this.f2348qp = width;
                    return this;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1184h.m2302a(this, out, flags);
                    } else {
                        PolylineOptionsCreator.m2287a(this, out, flags);
                    }
                }

                public PolylineOptions zIndex(float zIndex) {
                    this.f2346qk = zIndex;
                    return this;
                }
            }
