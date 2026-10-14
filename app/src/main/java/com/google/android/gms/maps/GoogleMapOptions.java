            package com.google.android.gms.maps;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.os.Parcel;
            import android.util.AttributeSet;
            import com.google.android.gms.C0881R;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.maps.internal.C1157a;
            import com.google.android.gms.maps.internal.C1174r;
            import com.google.android.gms.maps.model.CameraPosition;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class GoogleMapOptions implements SafeParcelable {
                public static final GoogleMapOptionsCreator CREATOR = new GoogleMapOptionsCreator();

                /* JADX INFO: renamed from: iM */
                private final int f2223iM;

                /* JADX INFO: renamed from: pA */
                private CameraPosition f2224pA;

                /* JADX INFO: renamed from: pB */
                private Boolean f2225pB;

                /* JADX INFO: renamed from: pC */
                private Boolean f2226pC;

                /* JADX INFO: renamed from: pD */
                private Boolean f2227pD;

                /* JADX INFO: renamed from: pE */
                private Boolean f2228pE;

                /* JADX INFO: renamed from: pF */
                private Boolean f2229pF;

                /* JADX INFO: renamed from: pG */
                private Boolean f2230pG;

                /* JADX INFO: renamed from: px */
                private Boolean f2231px;

                /* JADX INFO: renamed from: py */
                private Boolean f2232py;

                /* JADX INFO: renamed from: pz */
                private int f2233pz;

                public GoogleMapOptions() {
                    this.f2233pz = -1;
                    this.f2223iM = 1;
                }

                GoogleMapOptions(int versionCode, byte zOrderOnTop, byte useViewLifecycleInFragment, int mapType, CameraPosition camera, byte zoomControlsEnabled, byte compassEnabled, byte scrollGesturesEnabled, byte zoomGesturesEnabled, byte tiltGesturesEnabled, byte rotateGesturesEnabled) {
                    this.f2233pz = -1;
                    this.f2223iM = versionCode;
                    this.f2231px = C1157a.m2234a(zOrderOnTop);
                    this.f2232py = C1157a.m2234a(useViewLifecycleInFragment);
                    this.f2233pz = mapType;
                    this.f2224pA = camera;
                    this.f2225pB = C1157a.m2234a(zoomControlsEnabled);
                    this.f2226pC = C1157a.m2234a(compassEnabled);
                    this.f2227pD = C1157a.m2234a(scrollGesturesEnabled);
                    this.f2228pE = C1157a.m2234a(zoomGesturesEnabled);
                    this.f2229pF = C1157a.m2234a(tiltGesturesEnabled);
                    this.f2230pG = C1157a.m2234a(rotateGesturesEnabled);
                }

                public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attrs) {
                    if (attrs == null) {
                        return null;
                    }
                    TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attrs, C0881R.styleable.MapAttrs);
                    GoogleMapOptions googleMapOptions = new GoogleMapOptions();
                    if (typedArrayObtainAttributes.hasValue(0)) {
                        googleMapOptions.mapType(typedArrayObtainAttributes.getInt(0, -1));
                    }
                    if (typedArrayObtainAttributes.hasValue(13)) {
                        googleMapOptions.zOrderOnTop(typedArrayObtainAttributes.getBoolean(13, false));
                    }
                    if (typedArrayObtainAttributes.hasValue(12)) {
                        googleMapOptions.useViewLifecycleInFragment(typedArrayObtainAttributes.getBoolean(12, false));
                    }
                    if (typedArrayObtainAttributes.hasValue(6)) {
                        googleMapOptions.compassEnabled(typedArrayObtainAttributes.getBoolean(6, true));
                    }
                    if (typedArrayObtainAttributes.hasValue(7)) {
                        googleMapOptions.rotateGesturesEnabled(typedArrayObtainAttributes.getBoolean(7, true));
                    }
                    if (typedArrayObtainAttributes.hasValue(8)) {
                        googleMapOptions.scrollGesturesEnabled(typedArrayObtainAttributes.getBoolean(8, true));
                    }
                    if (typedArrayObtainAttributes.hasValue(9)) {
                        googleMapOptions.tiltGesturesEnabled(typedArrayObtainAttributes.getBoolean(9, true));
                    }
                    if (typedArrayObtainAttributes.hasValue(11)) {
                        googleMapOptions.zoomGesturesEnabled(typedArrayObtainAttributes.getBoolean(11, true));
                    }
                    if (typedArrayObtainAttributes.hasValue(10)) {
                        googleMapOptions.zoomControlsEnabled(typedArrayObtainAttributes.getBoolean(10, true));
                    }
                    googleMapOptions.camera(CameraPosition.createFromAttributes(context, attrs));
                    typedArrayObtainAttributes.recycle();
                    return googleMapOptions;
                }

                /* JADX INFO: renamed from: cA */
                byte m2207cA() {
                    return C1157a.m2235b(this.f2228pE);
                }

                /* JADX INFO: renamed from: cB */
                byte m2208cB() {
                    return C1157a.m2235b(this.f2229pF);
                }

                /* JADX INFO: renamed from: cC */
                byte m2209cC() {
                    return C1157a.m2235b(this.f2230pG);
                }

                public GoogleMapOptions camera(CameraPosition camera) {
                    this.f2224pA = camera;
                    return this;
                }

                public GoogleMapOptions compassEnabled(boolean enabled) {
                    this.f2226pC = Boolean.valueOf(enabled);
                    return this;
                }

                /* JADX INFO: renamed from: cv */
                byte m2210cv() {
                    return C1157a.m2235b(this.f2231px);
                }

                /* JADX INFO: renamed from: cw */
                byte m2211cw() {
                    return C1157a.m2235b(this.f2232py);
                }

                /* JADX INFO: renamed from: cx */
                byte m2212cx() {
                    return C1157a.m2235b(this.f2225pB);
                }

                /* JADX INFO: renamed from: cy */
                byte m2213cy() {
                    return C1157a.m2235b(this.f2226pC);
                }

                /* JADX INFO: renamed from: cz */
                byte m2214cz() {
                    return C1157a.m2235b(this.f2227pD);
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public CameraPosition getCamera() {
                    return this.f2224pA;
                }

                public Boolean getCompassEnabled() {
                    return this.f2226pC;
                }

                public int getMapType() {
                    return this.f2233pz;
                }

                public Boolean getRotateGesturesEnabled() {
                    return this.f2230pG;
                }

                public Boolean getScrollGesturesEnabled() {
                    return this.f2227pD;
                }

                public Boolean getTiltGesturesEnabled() {
                    return this.f2229pF;
                }

                public Boolean getUseViewLifecycleInFragment() {
                    return this.f2232py;
                }

                int getVersionCode() {
                    return this.f2223iM;
                }

                public Boolean getZOrderOnTop() {
                    return this.f2231px;
                }

                public Boolean getZoomControlsEnabled() {
                    return this.f2225pB;
                }

                public Boolean getZoomGesturesEnabled() {
                    return this.f2228pE;
                }

                public GoogleMapOptions mapType(int mapType) {
                    this.f2233pz = mapType;
                    return this;
                }

                public GoogleMapOptions rotateGesturesEnabled(boolean enabled) {
                    this.f2230pG = Boolean.valueOf(enabled);
                    return this;
                }

                public GoogleMapOptions scrollGesturesEnabled(boolean enabled) {
                    this.f2227pD = Boolean.valueOf(enabled);
                    return this;
                }

                public GoogleMapOptions tiltGesturesEnabled(boolean enabled) {
                    this.f2229pF = Boolean.valueOf(enabled);
                    return this;
                }

                public GoogleMapOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
                    this.f2232py = Boolean.valueOf(useViewLifecycleInFragment);
                    return this;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1149a.m2226a(this, out, flags);
                    } else {
                        GoogleMapOptionsCreator.m2215a(this, out, flags);
                    }
                }

                public GoogleMapOptions zOrderOnTop(boolean zOrderOnTop) {
                    this.f2231px = Boolean.valueOf(zOrderOnTop);
                    return this;
                }

                public GoogleMapOptions zoomControlsEnabled(boolean enabled) {
                    this.f2225pB = Boolean.valueOf(enabled);
                    return this;
                }

                public GoogleMapOptions zoomGesturesEnabled(boolean enabled) {
                    this.f2228pE = Boolean.valueOf(enabled);
                    return this;
                }
            }
