            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            public class CircleOptionsCreator implements Parcelable.Creator<CircleOptions> {
                public static final int CONTENT_DESCRIPTION = 0;

                
                static void m2270a(CircleOptions circleOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, circleOptions.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) circleOptions.getCenter(), i, false);
                    C0902b.m1133a(parcel, 3, circleOptions.getRadius());
                    C0902b.m1134a(parcel, 4, circleOptions.getStrokeWidth());
                    C0902b.m1150c(parcel, 5, circleOptions.getStrokeColor());
                    C0902b.m1150c(parcel, 6, circleOptions.getFillColor());
                    C0902b.m1134a(parcel, 7, circleOptions.getZIndex());
                    C0902b.m1143a(parcel, 8, circleOptions.isVisible());
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                @Override 
                public CircleOptions createFromParcel(Parcel parcel) {
                    float fM1108i = 0.0f;
                    boolean zM1102c = false;
                    int iM1111j = C0901a.m1111j(parcel);
                    LatLng latLng = null;
                    double dM1110j = 0.0d;
                    int iM1105f = 0;
                    int iM1105f2 = 0;
                    float fM1108i2 = 0.0f;
                    int iM1105f3 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                latLng = (LatLng) C0901a.m1096a(parcel, iM1109i, LatLng.CREATOR);
                                break;
                            case 3:
                                dM1110j = C0901a.m1110j(parcel, iM1109i);
                                break;
                            case 4:
                                fM1108i2 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 5:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 6:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 7:
                                fM1108i = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 8:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new CircleOptions(iM1105f3, latLng, dM1110j, fM1108i2, iM1105f2, iM1105f, fM1108i, zM1102c);
                }


                
                @Override 
                public CircleOptions[] newArray(int size) {
                    return new CircleOptions[size];
                }

            }
