            package com.google.android.gms.location;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            
            
            public class DetectedActivity implements SafeParcelable {
                public static final DetectedActivityCreator CREATOR = new DetectedActivityCreator();
                public static final int IN_VEHICLE = 0;
                public static final int ON_BICYCLE = 1;
                public static final int ON_FOOT = 2;
                public static final int STILL = 3;
                public static final int TILTING = 5;
                public static final int UNKNOWN = 4;

                
                private final int f2171iM;

                
                int f2172oy;

                
                int f2173oz;

                public DetectedActivity(int activityType, int confidence) {
                    this.f2171iM = 1;
                    this.f2172oy = activityType;
                    this.f2173oz = confidence;
                }

                public DetectedActivity(int versionCode, int activityType, int confidence) {
                    this.f2171iM = versionCode;
                    this.f2172oy = activityType;
                    this.f2173oz = confidence;
                }

                
                private int m2185W(int i) {
                    if (i > 6) {
                        return 4;
                    }
                    return i;
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public int getConfidence() {
                    return this.f2173oz;
                }

                public int getType() {
                    return m2185W(this.f2172oy);
                }

                public int getVersionCode() {
                    return this.f2171iM;
                }

                public String toString() {
                    return "DetectedActivity [type=" + getType() + ", confidence=" + this.f2173oz + "]";
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    DetectedActivityCreator.m2186a(this, out, flags);
                }
            }
