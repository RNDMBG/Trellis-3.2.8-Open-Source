            package com.google.android.gms.maps.model;

            import android.os.IBinder;
            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.internal.C1174r;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class GroundOverlayOptions implements SafeParcelable {
                public static final GroundOverlayOptionsCreator CREATOR = new GroundOverlayOptionsCreator();
                public static final float NO_DIMENSION = -1.0f;

                /* JADX INFO: renamed from: iM */
                private final int f2299iM;

                /* JADX INFO: renamed from: qd */
                private float f2300qd;

                /* JADX INFO: renamed from: qk */
                private float f2301qk;

                /* JADX INFO: renamed from: ql */
                private boolean f2302ql;

                /* JADX INFO: renamed from: qn */
                private BitmapDescriptor f2303qn;

                /* JADX INFO: renamed from: qo */
                private LatLng f2304qo;

                /* JADX INFO: renamed from: qp */
                private float f2305qp;

                /* JADX INFO: renamed from: qq */
                private float f2306qq;

                /* JADX INFO: renamed from: qr */
                private LatLngBounds f2307qr;

                /* JADX INFO: renamed from: qs */
                private float f2308qs;

                /* JADX INFO: renamed from: qt */
                private float f2309qt;

                /* JADX INFO: renamed from: qu */
                private float f2310qu;

                public GroundOverlayOptions() {
                    this.f2302ql = true;
                    this.f2308qs = 0.0f;
                    this.f2309qt = 0.5f;
                    this.f2310qu = 0.5f;
                    this.f2299iM = 1;
                }

                GroundOverlayOptions(int versionCode, IBinder wrappedImage, LatLng location, float width, float height, LatLngBounds bounds, float bearing, float zIndex, boolean visible, float transparency, float anchorU, float anchorV) {
                    this.f2302ql = true;
                    this.f2308qs = 0.0f;
                    this.f2309qt = 0.5f;
                    this.f2310qu = 0.5f;
                    this.f2299iM = versionCode;
                    this.f2303qn = new BitmapDescriptor(InterfaceC0904b.a.m1164z(wrappedImage));
                    this.f2304qo = location;
                    this.f2305qp = width;
                    this.f2306qq = height;
                    this.f2307qr = bounds;
                    this.f2300qd = bearing;
                    this.f2301qk = zIndex;
                    this.f2302ql = visible;
                    this.f2308qs = transparency;
                    this.f2309qt = anchorU;
                    this.f2310qu = anchorV;
                }

                /* JADX INFO: renamed from: a */
                private GroundOverlayOptions m2271a(LatLng latLng, float f, float f2) {
                    this.f2304qo = latLng;
                    this.f2305qp = f;
                    this.f2306qq = f2;
                    return this;
                }

                public GroundOverlayOptions anchor(float u, float v) {
                    this.f2309qt = u;
                    this.f2310qu = v;
                    return this;
                }

                public GroundOverlayOptions bearing(float bearing) {
                    this.f2300qd = ((bearing % 360.0f) + 360.0f) % 360.0f;
                    return this;
                }

                /* JADX INFO: renamed from: cM */
                IBinder m2272cM() {
                    return this.f2303qn.m2266cs().asBinder();
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public float getAnchorU() {
                    return this.f2309qt;
                }

                public float getAnchorV() {
                    return this.f2310qu;
                }

                public float getBearing() {
                    return this.f2300qd;
                }

                public LatLngBounds getBounds() {
                    return this.f2307qr;
                }

                public float getHeight() {
                    return this.f2306qq;
                }

                public BitmapDescriptor getImage() {
                    return this.f2303qn;
                }

                public LatLng getLocation() {
                    return this.f2304qo;
                }

                public float getTransparency() {
                    return this.f2308qs;
                }

                int getVersionCode() {
                    return this.f2299iM;
                }

                public float getWidth() {
                    return this.f2305qp;
                }

                public float getZIndex() {
                    return this.f2301qk;
                }

                public GroundOverlayOptions image(BitmapDescriptor image) {
                    this.f2303qn = image;
                    return this;
                }

                public boolean isVisible() {
                    return this.f2302ql;
                }

                public GroundOverlayOptions position(LatLng location, float width) {
                    C1028dm.m1602a(this.f2307qr == null, "Position has already been set using positionFromBounds");
                    C1028dm.m1604b(location != null, "Location must be specified");
                    C1028dm.m1604b(width >= 0.0f, "Width must be non-negative");
                    return m2271a(location, width, -1.0f);
                }

                public GroundOverlayOptions position(LatLng location, float width, float height) {
                    C1028dm.m1602a(this.f2307qr == null, "Position has already been set using positionFromBounds");
                    C1028dm.m1604b(location != null, "Location must be specified");
                    C1028dm.m1604b(width >= 0.0f, "Width must be non-negative");
                    C1028dm.m1604b(height >= 0.0f, "Height must be non-negative");
                    return m2271a(location, width, height);
                }

                public GroundOverlayOptions positionFromBounds(LatLngBounds bounds) {
                    C1028dm.m1602a(this.f2304qo == null, "Position has already been set using position: " + this.f2304qo);
                    this.f2307qr = bounds;
                    return this;
                }

                public GroundOverlayOptions transparency(float transparency) {
                    C1028dm.m1604b(transparency >= 0.0f && transparency <= 1.0f, "Transparency must be in the range [0..1]");
                    this.f2308qs = transparency;
                    return this;
                }

                public GroundOverlayOptions visible(boolean visible) {
                    this.f2302ql = visible;
                    return this;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1179c.m2297a(this, out, flags);
                    } else {
                        GroundOverlayOptionsCreator.m2273a(this, out, flags);
                    }
                }

                public GroundOverlayOptions zIndex(float zIndex) {
                    this.f2301qk = zIndex;
                    return this;
                }
            }
