            package com.google.android.gms.maps.model;

            import android.os.IBinder;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class MarkerOptionsCreator implements Parcelable.Creator<MarkerOptions> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m2284a(MarkerOptions markerOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, markerOptions.getVersionCode());
                    C0902b.m1139a(parcel, 2, (Parcelable) markerOptions.getPosition(), i, false);
                    C0902b.m1140a(parcel, 3, markerOptions.getTitle(), false);
                    C0902b.m1140a(parcel, 4, markerOptions.getSnippet(), false);
                    C0902b.m1137a(parcel, 5, markerOptions.m2283cN(), false);
                    C0902b.m1134a(parcel, 6, markerOptions.getAnchorU());
                    C0902b.m1134a(parcel, 7, markerOptions.getAnchorV());
                    C0902b.m1143a(parcel, 8, markerOptions.isDraggable());
                    C0902b.m1143a(parcel, 9, markerOptions.isVisible());
                    C0902b.m1143a(parcel, 10, markerOptions.isFlat());
                    C0902b.m1134a(parcel, 11, markerOptions.getRotation());
                    C0902b.m1134a(parcel, 12, markerOptions.getInfoWindowAnchorU());
                    C0902b.m1134a(parcel, 13, markerOptions.getInfoWindowAnchorV());
                    C0902b.m1134a(parcel, 14, markerOptions.getAlpha());
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MarkerOptions createFromParcel(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    LatLng latLng = null;
                    String strM1113l = null;
                    String strM1113l2 = null;
                    IBinder iBinderM1114m = null;
                    float fM1108i = 0.0f;
                    float fM1108i2 = 0.0f;
                    boolean zM1102c = false;
                    boolean zM1102c2 = false;
                    boolean zM1102c3 = false;
                    float fM1108i3 = 0.0f;
                    float fM1108i4 = 0.5f;
                    float fM1108i5 = 0.0f;
                    float fM1108i6 = 1.0f;
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
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 4:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 5:
                                iBinderM1114m = C0901a.m1114m(parcel, iM1109i);
                                break;
                            case 6:
                                fM1108i = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 7:
                                fM1108i2 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 8:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 9:
                                zM1102c2 = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 10:
                                zM1102c3 = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 11:
                                fM1108i3 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 12:
                                fM1108i4 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 13:
                                fM1108i5 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            case 14:
                                fM1108i6 = C0901a.m1108i(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new MarkerOptions(iM1105f, latLng, strM1113l, strM1113l2, iBinderM1114m, fM1108i, fM1108i2, zM1102c, zM1102c2, zM1102c3, fM1108i3, fM1108i4, fM1108i5, fM1108i6);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public MarkerOptions[] newArray(int size) {
                    return new MarkerOptions[size];
                }

            }
