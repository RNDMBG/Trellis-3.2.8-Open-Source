            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class TileCreator implements Parcelable.Creator<Tile> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m2288a(Tile tile, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, tile.getVersionCode());
                    C0902b.m1150c(parcel, 2, tile.width);
                    C0902b.m1150c(parcel, 3, tile.height);
                    C0902b.m1144a(parcel, 4, tile.data, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Tile createFromParcel(Parcel parcel) {
                    int iM1105f = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    byte[] bArrM1116o = null;
                    int iM1105f2 = 0;
                    int iM1105f3 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 3:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 4:
                                bArrM1116o = C0901a.m1116o(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new Tile(iM1105f3, iM1105f2, iM1105f, bArrM1116o);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Tile[] newArray(int size) {
                    return new Tile[size];
                }

            }
