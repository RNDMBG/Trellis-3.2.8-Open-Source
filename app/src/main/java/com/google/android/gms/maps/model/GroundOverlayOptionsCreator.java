            package com.google.android.gms.maps.model;

            import android.os.IBinder;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class GroundOverlayOptionsCreator implements Parcelable.Creator<GroundOverlayOptions> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m2273a(GroundOverlayOptions groundOverlayOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, groundOverlayOptions.getVersionCode());
                    C0902b.m1137a(parcel, 2, groundOverlayOptions.m2272cM(), false);
                    C0902b.m1139a(parcel, 3, (Parcelable) groundOverlayOptions.getLocation(), i, false);
                    C0902b.m1134a(parcel, 4, groundOverlayOptions.getWidth());
                    C0902b.m1134a(parcel, 5, groundOverlayOptions.getHeight());
                    C0902b.m1139a(parcel, 6, (Parcelable) groundOverlayOptions.getBounds(), i, false);
                    C0902b.m1134a(parcel, 7, groundOverlayOptions.getBearing());
                    C0902b.m1134a(parcel, 8, groundOverlayOptions.getZIndex());
                    C0902b.m1143a(parcel, 9, groundOverlayOptions.isVisible());
                    C0902b.m1134a(parcel, 10, groundOverlayOptions.getTransparency());
                    C0902b.m1134a(parcel, 11, groundOverlayOptions.getAnchorU());
                    C0902b.m1134a(parcel, 12, groundOverlayOptions.getAnchorV());
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public GroundOverlayOptions createFromParcel(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    IBinder iBinderM1114m = null;
                    LatLng latLng = null;
                    float fM1108i = 0.0f;
                    float fM1108i2 = 0.0f;
                    LatLngBounds latLngBounds = null;
                    float fM1108i3 = 0.0f;
                    float fM1108i4 = 0.0f;
                    boolean zM1102c = false;
                    float fM1108i5 = 0.0f;
                    float fM1108i6 = 0.0f;
                    float fM1108i7 = 0.0f;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                iBinderM1114m = C0901a.m1114m(parcel, iM1109i);
                                break;
                            case 3:
                                latLng = (LatLng) C0901a.m1096a(parcel, iM1109i, LatLng.CREATOR);
                                break;
                            case 4:
                                fM1108i = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 5:
                                fM1108i2 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 6:
                                latLngBounds = (LatLngBounds) C0901a.m1096a(parcel, iM1109i, LatLngBounds.CREATOR);
                                break;
                            case 7:
                                fM1108i3 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 8:
                                fM1108i4 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 9:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 10:
                                fM1108i5 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 11:
                                fM1108i6 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 12:
                                fM1108i7 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new GroundOverlayOptions(iM1105f, iBinderM1114m, latLng, fM1108i, fM1108i2, latLngBounds, fM1108i3, fM1108i4, zM1102c, fM1108i5, fM1108i6, fM1108i7);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public GroundOverlayOptions[] newArray(int size) {
                    return new GroundOverlayOptions[size];
                }

            }
