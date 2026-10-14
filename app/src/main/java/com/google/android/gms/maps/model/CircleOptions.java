            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.maps.internal.C1174r;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class CircleOptions implements SafeParcelable {
                public static final CircleOptionsCreator CREATOR = new CircleOptionsCreator();

                /* JADX INFO: renamed from: iM */
                private final int f2290iM;

                /* JADX INFO: renamed from: qf */
                private LatLng f2291qf;

                /* JADX INFO: renamed from: qg */
                private double f2292qg;

                /* JADX INFO: renamed from: qh */
                private float f2293qh;

                /* JADX INFO: renamed from: qi */
                private int f2294qi;

                /* JADX INFO: renamed from: qj */
                private int f2295qj;

                /* JADX INFO: renamed from: qk */
                private float f2296qk;

                /* JADX INFO: renamed from: ql */
                private boolean f2297ql;

                public CircleOptions() {
                    this.f2291qf = null;
                    this.f2292qg = 0.0d;
                    this.f2293qh = 10.0f;
                    this.f2294qi = -16777216;
                    this.f2295qj = 0;
                    this.f2296qk = 0.0f;
                    this.f2297ql = true;
                    this.f2290iM = 1;
                }

                CircleOptions(int versionCode, LatLng center, double radius, float strokeWidth, int strokeColor, int fillColor, float zIndex, boolean visible) {
                    this.f2291qf = null;
                    this.f2292qg = 0.0d;
                    this.f2293qh = 10.0f;
                    this.f2294qi = -16777216;
                    this.f2295qj = 0;
                    this.f2296qk = 0.0f;
                    this.f2297ql = true;
                    this.f2290iM = versionCode;
                    this.f2291qf = center;
                    this.f2292qg = radius;
                    this.f2293qh = strokeWidth;
                    this.f2294qi = strokeColor;
                    this.f2295qj = fillColor;
                    this.f2296qk = zIndex;
                    this.f2297ql = visible;
                }

                public CircleOptions center(LatLng center) {
                    this.f2291qf = center;
                    return this;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public CircleOptions fillColor(int color) {
                    this.f2295qj = color;
                    return this;
                }

                public LatLng getCenter() {
                    return this.f2291qf;
                }

                public int getFillColor() {
                    return this.f2295qj;
                }

                public double getRadius() {
                    return this.f2292qg;
                }

                public int getStrokeColor() {
                    return this.f2294qi;
                }

                public float getStrokeWidth() {
                    return this.f2293qh;
                }

                int getVersionCode() {
                    return this.f2290iM;
                }

                public float getZIndex() {
                    return this.f2296qk;
                }

                public boolean isVisible() {
                    return this.f2297ql;
                }

                public CircleOptions radius(double radius) {
                    this.f2292qg = radius;
                    return this;
                }

                public CircleOptions strokeColor(int color) {
                    this.f2294qi = color;
                    return this;
                }

                public CircleOptions strokeWidth(float width) {
                    this.f2293qh = width;
                    return this;
                }

                public CircleOptions visible(boolean visible) {
                    this.f2297ql = visible;
                    return this;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1178b.m2296a(this, out, flags);
                    } else {
                        CircleOptionsCreator.m2270a(this, out, flags);
                    }
                }

                public CircleOptions zIndex(float zIndex) {
                    this.f2296qk = zIndex;
                    return this;
                }
            }
