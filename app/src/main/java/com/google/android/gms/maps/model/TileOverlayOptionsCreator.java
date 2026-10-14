            package com.google.android.gms.maps.model;

            import android.os.IBinder;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class TileOverlayOptionsCreator implements Parcelable.Creator<TileOverlayOptions> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m2291a(TileOverlayOptions tileOverlayOptions, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, tileOverlayOptions.getVersionCode());
                    C0902b.m1137a(parcel, 2, tileOverlayOptions.m2290cP(), false);
                    C0902b.m1143a(parcel, 3, tileOverlayOptions.isVisible());
                    C0902b.m1134a(parcel, 4, tileOverlayOptions.getZIndex());
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TileOverlayOptions createFromParcel(Parcel parcel) {
                    boolean zM1102c = false;
                    int iM1111j = C0901a.m1111j(parcel);
                    IBinder iBinderM1114m = null;
                    float fM1108i = 0.0f;
                    int iM1105f = 0;
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
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 4:
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
                    return new TileOverlayOptions(iM1105f, iBinderM1114m, zM1102c, fM1108i);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TileOverlayOptions[] newArray(int size) {
                    return new TileOverlayOptions[size];
                }

            }
