            package com.google.android.gms.games.multiplayer;

            import android.net.Uri;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import com.google.android.gms.games.PlayerEntity;
            
            
            
            public class C0925c implements Parcelable.Creator<ParticipantEntity> {
                
                static void m1217a(ParticipantEntity participantEntity, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1140a(parcel, 1, participantEntity.getParticipantId(), false);
                    C0902b.m1150c(parcel, 1000, participantEntity.getVersionCode());
                    C0902b.m1140a(parcel, 2, participantEntity.getDisplayName(), false);
                    C0902b.m1139a(parcel, 3, (Parcelable) participantEntity.getIconImageUri(), i, false);
                    C0902b.m1139a(parcel, 4, (Parcelable) participantEntity.getHiResImageUri(), i, false);
                    C0902b.m1150c(parcel, 5, participantEntity.getStatus());
                    C0902b.m1140a(parcel, 6, participantEntity.mo1206ci(), false);
                    C0902b.m1143a(parcel, 7, participantEntity.isConnectedToRoom());
                    C0902b.m1139a(parcel, 8, (Parcelable) participantEntity.getPlayer(), i, false);
                    C0902b.m1150c(parcel, 9, participantEntity.getCapabilities());
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public ParticipantEntity[] m1218T(int i) {
                    return new ParticipantEntity[i];
                }

                @Override 
                public  ParticipantEntity createFromParcel(Parcel x0) {
                    return mo1213w(x0);
                }

                @Override 
                public  ParticipantEntity[] newArray(int x0) {
                    return m1218T(x0);
                }

                
                public ParticipantEntity mo1213w(Parcel parcel) {
                    int iM1105f = 0;
                    PlayerEntity playerEntity = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    boolean zM1102c = false;
                    String strM1113l = null;
                    int iM1105f2 = 0;
                    Uri uri = null;
                    Uri uri2 = null;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    int iM1105f3 = 0;
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
                                uri2 = (Uri) C0901a.m1096a(parcel, iM1109i, Uri.CREATOR);
                                break;
                            case 4:
                                uri = (Uri) C0901a.m1096a(parcel, iM1109i, Uri.CREATOR);
                                break;
                            case 5:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 6:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 7:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 8:
                                playerEntity = (PlayerEntity) C0901a.m1096a(parcel, iM1109i, PlayerEntity.CREATOR);
                                break;
                            case 9:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 1000:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new ParticipantEntity(iM1105f3, strM1113l3, strM1113l2, uri2, uri, iM1105f2, strM1113l, zM1102c, playerEntity, iM1105f);
                }
            }
