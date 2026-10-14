            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class PolylineOptionsCreator implements Parcelable.Creator<PolylineOptions> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m2287a(PolylineOptions polylineOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, polylineOptions.getVersionCode());
                    C0902b.m1149b(parcel, 2, polylineOptions.getPoints(), false);
                    C0902b.m1134a(parcel, 3, polylineOptions.getWidth());
                    C0902b.m1150c(parcel, 4, polylineOptions.getColor());
                    C0902b.m1134a(parcel, 5, polylineOptions.getZIndex());
                    C0902b.m1143a(parcel, 6, polylineOptions.isVisible());
                    C0902b.m1143a(parcel, 7, polylineOptions.isGeodesic());
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PolylineOptions createFromParcel(Parcel parcel) {
                    float fM1108i = 0.0f;
                    boolean zM1102c = false;
                    int iM1111j = C0901a.m1111j(parcel);
                    ArrayList arrayListM1101c = null;
                    boolean zM1102c2 = false;
                    int iM1105f = 0;
                    float fM1108i2 = 0.0f;
                    int iM1105f2 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                arrayListM1101c = C0901a.m1101c(parcel, iM1109i, LatLng.CREATOR);
                                break;
                            case 3:
                                fM1108i2 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 4:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 5:
                                fM1108i = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 6:
                                zM1102c2 = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 7:
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
                    return new PolylineOptions(iM1105f2, arrayListM1101c, fM1108i2, iM1105f, fM1108i, zM1102c2, zM1102c);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PolylineOptions[] newArray(int size) {
                    return new PolylineOptions[size];
                }

            }
