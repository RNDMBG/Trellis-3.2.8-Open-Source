            package com.google.android.gms.maps.model;

            import android.os.IBinder;
            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.maps.internal.C1174r;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class MarkerOptions implements SafeParcelable {
                public static final MarkerOptionsCreator CREATOR = new MarkerOptionsCreator();

                /* JADX INFO: renamed from: iM */
                private final int f2318iM;
                private float mAlpha;

                /* JADX INFO: renamed from: qA */
                private LatLng f2319qA;

                /* JADX INFO: renamed from: qB */
                private String f2320qB;

                /* JADX INFO: renamed from: qC */
                private String f2321qC;

                /* JADX INFO: renamed from: qD */
                private BitmapDescriptor f2322qD;

                /* JADX INFO: renamed from: qE */
                private boolean f2323qE;

                /* JADX INFO: renamed from: qF */
                private boolean f2324qF;

                /* JADX INFO: renamed from: qG */
                private float f2325qG;

                /* JADX INFO: renamed from: qH */
                private float f2326qH;

                /* JADX INFO: renamed from: qI */
                private float f2327qI;

                /* JADX INFO: renamed from: ql */
                private boolean f2328ql;

                /* JADX INFO: renamed from: qt */
                private float f2329qt;

                /* JADX INFO: renamed from: qu */
                private float f2330qu;

                public MarkerOptions() {
                    this.f2329qt = 0.5f;
                    this.f2330qu = 1.0f;
                    this.f2328ql = true;
                    this.f2324qF = false;
                    this.f2325qG = 0.0f;
                    this.f2326qH = 0.5f;
                    this.f2327qI = 0.0f;
                    this.mAlpha = 1.0f;
                    this.f2318iM = 1;
                }

                MarkerOptions(int versionCode, LatLng position, String title, String snippet, IBinder wrappedIcon, float anchorU, float anchorV, boolean draggable, boolean visible, boolean flat, float rotation, float infoWindowAnchorU, float infoWindowAnchorV, float alpha) {
                    this.f2329qt = 0.5f;
                    this.f2330qu = 1.0f;
                    this.f2328ql = true;
                    this.f2324qF = false;
                    this.f2325qG = 0.0f;
                    this.f2326qH = 0.5f;
                    this.f2327qI = 0.0f;
                    this.mAlpha = 1.0f;
                    this.f2318iM = versionCode;
                    this.f2319qA = position;
                    this.f2320qB = title;
                    this.f2321qC = snippet;
                    this.f2322qD = wrappedIcon == null ? null : new BitmapDescriptor(InterfaceC0904b.a.m1164z(wrappedIcon));
                    this.f2329qt = anchorU;
                    this.f2330qu = anchorV;
                    this.f2323qE = draggable;
                    this.f2328ql = visible;
                    this.f2324qF = flat;
                    this.f2325qG = rotation;
                    this.f2326qH = infoWindowAnchorU;
                    this.f2327qI = infoWindowAnchorV;
                    this.mAlpha = alpha;
                }

                public MarkerOptions alpha(float alpha) {
                    this.mAlpha = alpha;
                    return this;
                }

                public MarkerOptions anchor(float u, float v) {
                    this.f2329qt = u;
                    this.f2330qu = v;
                    return this;
                }

                /* JADX INFO: renamed from: cN */
                IBinder m2283cN() {
                    if (this.f2322qD == null) {
                        return null;
                    }
                    return this.f2322qD.m2266cs().asBinder();
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public MarkerOptions draggable(boolean draggable) {
                    this.f2323qE = draggable;
                    return this;
                }

                public MarkerOptions flat(boolean flat) {
                    this.f2324qF = flat;
                    return this;
                }

                public float getAlpha() {
                    return this.mAlpha;
                }

                public float getAnchorU() {
                    return this.f2329qt;
                }

                public float getAnchorV() {
                    return this.f2330qu;
                }

                public BitmapDescriptor getIcon() {
                    return this.f2322qD;
                }

                public float getInfoWindowAnchorU() {
                    return this.f2326qH;
                }

                public float getInfoWindowAnchorV() {
                    return this.f2327qI;
                }

                public LatLng getPosition() {
                    return this.f2319qA;
                }

                public float getRotation() {
                    return this.f2325qG;
                }

                public String getSnippet() {
                    return this.f2321qC;
                }

                public String getTitle() {
                    return this.f2320qB;
                }

                int getVersionCode() {
                    return this.f2318iM;
                }

                public MarkerOptions icon(BitmapDescriptor icon) {
                    this.f2322qD = icon;
                    return this;
                }

                public MarkerOptions infoWindowAnchor(float u, float v) {
                    this.f2326qH = u;
                    this.f2327qI = v;
                    return this;
                }

                public boolean isDraggable() {
                    return this.f2323qE;
                }

                public boolean isFlat() {
                    return this.f2324qF;
                }

                public boolean isVisible() {
                    return this.f2328ql;
                }

                public MarkerOptions position(LatLng position) {
                    this.f2319qA = position;
                    return this;
                }

                public MarkerOptions rotation(float rotation) {
                    this.f2325qG = rotation;
                    return this;
                }

                public MarkerOptions snippet(String snippet) {
                    this.f2321qC = snippet;
                    return this;
                }

                public MarkerOptions title(String title) {
                    this.f2320qB = title;
                    return this;
                }

                public MarkerOptions visible(boolean visible) {
                    this.f2328ql = visible;
                    return this;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1182f.m2300a(this, out, flags);
                    } else {
                        MarkerOptionsCreator.m2284a(this, out, flags);
                    }
                }
            }
