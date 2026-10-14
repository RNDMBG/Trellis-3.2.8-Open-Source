            package com.google.android.gms.maps.model;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.os.Parcel;
            import android.util.AttributeSet;
            import com.google.android.gms.C0881R;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.maps.internal.C1174r;
            
            
            public final class CameraPosition implements SafeParcelable {
                public static final CameraPositionCreator CREATOR = new CameraPositionCreator();
                public final float bearing;

                
                private final int f2284iM;
                public final LatLng target;
                public final float tilt;
                public final float zoom;

                public static final class Builder {

                    
                    private LatLng f2285qa;

                    
                    private float f2286qb;

                    
                    private float f2287qc;

                    
                    private float f2288qd;

                    public Builder() {
                    }

                    public Builder(CameraPosition previous) {
                        this.f2285qa = previous.target;
                        this.f2286qb = previous.zoom;
                        this.f2287qc = previous.tilt;
                        this.f2288qd = previous.bearing;
                    }

                    public Builder bearing(float bearing) {
                        this.f2288qd = bearing;
                        return this;
                    }

                    public CameraPosition build() {
                        return new CameraPosition(this.f2285qa, this.f2286qb, this.f2287qc, this.f2288qd);
                    }

                    public Builder target(LatLng location) {
                        this.f2285qa = location;
                        return this;
                    }

                    public Builder tilt(float tilt) {
                        this.f2287qc = tilt;
                        return this;
                    }

                    public Builder zoom(float zoom) {
                        this.f2286qb = zoom;
                        return this;
                    }
                }

                CameraPosition(int versionCode, LatLng target, float zoom, float tilt, float bearing) {
                    C1028dm.m1601a(target, "null camera target");
                    C1028dm.m1604b(0.0f <= tilt && tilt <= 90.0f, "Tilt needs to be between 0 and 90 inclusive");
                    this.f2284iM = versionCode;
                    this.target = target;
                    this.zoom = zoom;
                    this.tilt = tilt + 0.0f;
                    this.bearing = (((double) bearing) <= 0.0d ? (bearing % 360.0f) + 360.0f : bearing) % 360.0f;
                }

                public CameraPosition(LatLng target, float zoom, float tilt, float bearing) {
                    this(1, target, zoom, tilt, bearing);
                }

                public static Builder builder() {
                    return new Builder();
                }

                public static Builder builder(CameraPosition camera) {
                    return new Builder(camera);
                }

                public static CameraPosition createFromAttributes(Context context, AttributeSet attrs) {
                    if (attrs == null) {
                        return null;
                    }
                    TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attrs, C0881R.styleable.MapAttrs);
                    LatLng latLng = new LatLng(typedArrayObtainAttributes.hasValue(2) ? typedArrayObtainAttributes.getFloat(2, 0.0f) : 0.0f, typedArrayObtainAttributes.hasValue(3) ? typedArrayObtainAttributes.getFloat(3, 0.0f) : 0.0f);
                    Builder builder = builder();
                    builder.target(latLng);
                    if (typedArrayObtainAttributes.hasValue(5)) {
                        builder.zoom(typedArrayObtainAttributes.getFloat(5, 0.0f));
                    }
                    if (typedArrayObtainAttributes.hasValue(1)) {
                        builder.bearing(typedArrayObtainAttributes.getFloat(1, 0.0f));
                    }
                    if (typedArrayObtainAttributes.hasValue(4)) {
                        builder.tilt(typedArrayObtainAttributes.getFloat(4, 0.0f));
                    }
                    return builder.build();
                }

                public static final CameraPosition fromLatLngZoom(LatLng target, float zoom) {
                    return new CameraPosition(target, zoom, 0.0f, 0.0f);
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public boolean equals(Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (!(o instanceof CameraPosition)) {
                        return false;
                    }
                    CameraPosition cameraPosition = (CameraPosition) o;
                    return this.target.equals(cameraPosition.target) && Float.floatToIntBits(this.zoom) == Float.floatToIntBits(cameraPosition.zoom) && Float.floatToIntBits(this.tilt) == Float.floatToIntBits(cameraPosition.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(cameraPosition.bearing);
                }

                int getVersionCode() {
                    return this.f2284iM;
                }

                public int hashCode() {
                    return C1027dl.hashCode(this.target, Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing));
                }

                public String toString() {
                    return C1027dl.m1599d(this).m1600a("target", this.target).m1600a("zoom", Float.valueOf(this.zoom)).m1600a("tilt", Float.valueOf(this.tilt)).m1600a("bearing", Float.valueOf(this.bearing)).toString();
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1177a.m2295a(this, out, flags);
                    } else {
                        CameraPositionCreator.m2269a(this, out, flags);
                    }
                }
            }
