            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            public class CameraPositionCreator implements Parcelable.Creator<CameraPosition> {
                public static final int CONTENT_DESCRIPTION = 0;

                
                static void m2269a(CameraPosition cameraPosition, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, cameraPosition.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) cameraPosition.target, i, false);
                    C0902b.m1134a(parcel, 3, cameraPosition.zoom);
                    C0902b.m1134a(parcel, 4, cameraPosition.tilt);
                    C0902b.m1134a(parcel, 5, cameraPosition.bearing);
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                @Override 
                public CameraPosition createFromParcel(Parcel parcel) {
                    float fM1108i = 0.0f;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    LatLng latLng = null;
                    float fM1108i2 = 0.0f;
                    float fM1108i3 = 0.0f;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                latLng = (LatLng) C0901a.m1096a(parcel, iM1109i, LatLng.CREATOR);
                                break;
                            case 3:
                                fM1108i3 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 4:
                                fM1108i2 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 5:
                                fM1108i = C0901a.m1108i(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new CameraPosition(iM1105f, latLng, fM1108i3, fM1108i2, fM1108i);
                }


                
                @Override 
                public CameraPosition[] newArray(int size) {
                    return new CameraPosition[size];
                }

            }
