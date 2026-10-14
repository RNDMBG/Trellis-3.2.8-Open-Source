            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.maps.internal.C1174r;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Iterator;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class PolygonOptions implements SafeParcelable {
                public static final PolygonOptionsCreator CREATOR = new PolygonOptionsCreator();

                /* JADX INFO: renamed from: iM */
                private final int f2332iM;

                /* JADX INFO: renamed from: qK */
                private final List<LatLng> f2333qK;

                /* JADX INFO: renamed from: qL */
                private final List<List<LatLng>> f2334qL;

                /* JADX INFO: renamed from: qM */
                private boolean f2335qM;

                /* JADX INFO: renamed from: qh */
                private float f2336qh;

                /* JADX INFO: renamed from: qi */
                private int f2337qi;

                /* JADX INFO: renamed from: qj */
                private int f2338qj;

                /* JADX INFO: renamed from: qk */
                private float f2339qk;

                /* JADX INFO: renamed from: ql */
                private boolean f2340ql;

                public PolygonOptions() {
                    this.f2336qh = 10.0f;
                    this.f2337qi = -16777216;
                    this.f2338qj = 0;
                    this.f2339qk = 0.0f;
                    this.f2340ql = true;
                    this.f2335qM = false;
                    this.f2332iM = 1;
                    this.f2333qK = new ArrayList();
                    this.f2334qL = new ArrayList();
                }

                PolygonOptions(int versionCode, List<LatLng> points, List holes, float strokeWidth, int strokeColor, int fillColor, float zIndex, boolean visible, boolean geodesic) {
                    this.f2336qh = 10.0f;
                    this.f2337qi = -16777216;
                    this.f2338qj = 0;
                    this.f2339qk = 0.0f;
                    this.f2340ql = true;
                    this.f2335qM = false;
                    this.f2332iM = versionCode;
                    this.f2333qK = points;
                    this.f2334qL = holes;
                    this.f2336qh = strokeWidth;
                    this.f2337qi = strokeColor;
                    this.f2338qj = fillColor;
                    this.f2339qk = zIndex;
                    this.f2340ql = visible;
                    this.f2335qM = geodesic;
                }

                public PolygonOptions add(LatLng point) {
                    this.f2333qK.add(point);
                    return this;
                }

                public PolygonOptions add(LatLng... points) {
                    this.f2333qK.addAll(Arrays.asList(points));
                    return this;
                }

                public PolygonOptions addAll(Iterable<LatLng> points) {
                    Iterator<LatLng> it = points.iterator();
                    while (it.hasNext()) {
                        this.f2333qK.add(it.next());
                    }
                    return this;
                }

                public PolygonOptions addHole(Iterable<LatLng> points) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<LatLng> it = points.iterator();
                    while (it.hasNext()) {
                        arrayList.add(it.next());
                    }
                    this.f2334qL.add(arrayList);
                    return this;
                }

                /* JADX INFO: renamed from: cO */
                List m2285cO() {
                    return this.f2334qL;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public PolygonOptions fillColor(int color) {
                    this.f2338qj = color;
                    return this;
                }

                public PolygonOptions geodesic(boolean geodesic) {
                    this.f2335qM = geodesic;
                    return this;
                }

                public int getFillColor() {
                    return this.f2338qj;
                }

                public List<List<LatLng>> getHoles() {
                    return this.f2334qL;
                }

                public List<LatLng> getPoints() {
                    return this.f2333qK;
                }

                public int getStrokeColor() {
                    return this.f2337qi;
                }

                public float getStrokeWidth() {
                    return this.f2336qh;
                }

                int getVersionCode() {
                    return this.f2332iM;
                }

                public float getZIndex() {
                    return this.f2339qk;
                }

                public boolean isGeodesic() {
                    return this.f2335qM;
                }

                public boolean isVisible() {
                    return this.f2340ql;
                }

                public PolygonOptions strokeColor(int color) {
                    this.f2337qi = color;
                    return this;
                }

                public PolygonOptions strokeWidth(float width) {
                    this.f2336qh = width;
                    return this;
                }

                public PolygonOptions visible(boolean visible) {
                    this.f2340ql = visible;
                    return this;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1183g.m2301a(this, out, flags);
                    } else {
                        PolygonOptionsCreator.m2286a(this, out, flags);
                    }
                }

                public PolygonOptions zIndex(float zIndex) {
                    this.f2339qk = zIndex;
                    return this;
                }
            }
