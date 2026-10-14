            package com.google.android.gms.games.multiplayer.realtime;

            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import com.google.android.gms.games.multiplayer.ParticipantEntity;
            import java.util.ArrayList;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.multiplayer.realtime.b */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C0931b implements Parcelable.Creator<RoomEntity> {
                /* JADX INFO: renamed from: a */
                static void m1229a(RoomEntity roomEntity, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1140a(parcel, 1, roomEntity.getRoomId(), false);
                    C0902b.m1150c(parcel, 1000, roomEntity.getVersionCode());
                    C0902b.m1140a(parcel, 2, roomEntity.getCreatorId(), false);
                    C0902b.m1135a(parcel, 3, roomEntity.getCreationTimestamp());
                    C0902b.m1150c(parcel, 4, roomEntity.getStatus());
                    C0902b.m1140a(parcel, 5, roomEntity.getDescription(), false);
                    C0902b.m1150c(parcel, 6, roomEntity.getVariant());
                    C0902b.m1136a(parcel, 7, roomEntity.getAutoMatchCriteria(), false);
                    C0902b.m1149b(parcel, 8, roomEntity.getParticipants(), false);
                    C0902b.m1150c(parcel, 9, roomEntity.getAutoMatchWaitEstimateSeconds());
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: V */
                public RoomEntity[] m1230V(int i) {
                    return new RoomEntity[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ RoomEntity createFromParcel(Parcel x0) {
                    return mo1227y(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ RoomEntity[] newArray(int x0) {
                    return m1230V(x0);
                }

                /* JADX INFO: renamed from: y */
                public RoomEntity mo1227y(Parcel parcel) {
                    int iM1105f = 0;
                    ArrayList arrayListM1101c = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    long jM1106g = 0;
                    Bundle bundleM1115n = null;
                    int iM1105f2 = 0;
                    String strM1113l = null;
                    int iM1105f3 = 0;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    int iM1105f4 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                jM1106g = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 4:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 5:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 6:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 7:
                                bundleM1115n = C0901a.m1115n(parcel, iM1109i);
                                break;
                            case 8:
                                arrayListM1101c = C0901a.m1101c(parcel, iM1109i, ParticipantEntity.CREATOR);
                                break;
                            case 9:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 1000:
                                iM1105f4 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new RoomEntity(iM1105f4, strM1113l3, strM1113l2, jM1106g, iM1105f3, strM1113l, iM1105f2, bundleM1115n, arrayListM1101c, iM1105f);
                }
            }
