            package com.google.android.gms.maps;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import com.google.android.gms.maps.model.CameraPosition;
            
            
            public class GoogleMapOptionsCreator implements Parcelable.Creator<GoogleMapOptions> {
                public static final int CONTENT_DESCRIPTION = 0;

                
                static void m2215a(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, googleMapOptions.getVersionCode());
                    C0902b.m1132a(parcel, 2, googleMapOptions.m2210cv());
                    C0902b.m1132a(parcel, 3, googleMapOptions.m2211cw());
                    C0902b.m1150c(parcel, 4, googleMapOptions.getMapType());
                    C0902b.m1139a(parcel, 5, (Parcelable) googleMapOptions.getCamera(), i, false);
                    C0902b.m1132a(parcel, 6, googleMapOptions.m2212cx());
                    C0902b.m1132a(parcel, 7, googleMapOptions.m2213cy());
                    C0902b.m1132a(parcel, 8, googleMapOptions.m2214cz());
                    C0902b.m1132a(parcel, 9, googleMapOptions.m2207cA());
                    C0902b.m1132a(parcel, 10, googleMapOptions.m2208cB());
                    C0902b.m1132a(parcel, 11, googleMapOptions.m2209cC());
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                @Override 
                public GoogleMapOptions createFromParcel(Parcel parcel) {
                    byte bM1103d = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    CameraPosition cameraPosition = null;
                    byte bM1103d2 = 0;
                    byte bM1103d3 = 0;
                    byte bM1103d4 = 0;
                    byte bM1103d5 = 0;
                    byte bM1103d6 = 0;
                    int iM1105f = 0;
                    byte bM1103d7 = 0;
                    byte bM1103d8 = 0;
                    int iM1105f2 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                bM1103d8 = C0901a.m1103d(parcel, iM1109i);
                                break;
                            case 3:
                                bM1103d7 = C0901a.m1103d(parcel, iM1109i);
                                break;
                            case 4:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 5:
                                cameraPosition = (CameraPosition) C0901a.m1096a(parcel, iM1109i, CameraPosition.CREATOR);
                                break;
                            case 6:
                                bM1103d6 = C0901a.m1103d(parcel, iM1109i);
                                break;
                            case 7:
                                bM1103d5 = C0901a.m1103d(parcel, iM1109i);
                                break;
                            case 8:
                                bM1103d4 = C0901a.m1103d(parcel, iM1109i);
                                break;
                            case 9:
                                bM1103d3 = C0901a.m1103d(parcel, iM1109i);
                                break;
                            case 10:
                                bM1103d2 = C0901a.m1103d(parcel, iM1109i);
                                break;
                            case 11:
                                bM1103d = C0901a.m1103d(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new GoogleMapOptions(iM1105f2, bM1103d8, bM1103d7, iM1105f, cameraPosition, bM1103d6, bM1103d5, bM1103d4, bM1103d3, bM1103d2, bM1103d);
                }


                
                @Override 
                public GoogleMapOptions[] newArray(int size) {
                    return new GoogleMapOptions[size];
                }

            }
