            package com.google.android.gms.games;

            import android.net.Uri;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            
            public class C0914c implements Parcelable.Creator<PlayerEntity> {
                
                static void m1185a(PlayerEntity playerEntity, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1140a(parcel, 1, playerEntity.getPlayerId(), false);
                    C0902b.m1150c(parcel, 1000, playerEntity.getVersionCode());
                    C0902b.m1140a(parcel, 2, playerEntity.getDisplayName(), false);
                    C0902b.m1139a(parcel, 3, (Parcelable) playerEntity.getIconImageUri(), i, false);
                    C0902b.m1139a(parcel, 4, (Parcelable) playerEntity.getHiResImageUri(), i, false);
                    C0902b.m1135a(parcel, 5, playerEntity.getRetrievedTimestamp());
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                public PlayerEntity[] m1186L(int i) {
                    return new PlayerEntity[i];
                }

                @Override 
                public  PlayerEntity createFromParcel(Parcel x0) {
                    return mo1182u(x0);
                }

                @Override 
                public  PlayerEntity[] newArray(int x0) {
                    return m1186L(x0);
                }

                
                public PlayerEntity mo1182u(Parcel parcel) {
                    Uri uri = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    long jM1106g = 0;
                    Uri uri2 = null;
                    String strM1113l = null;
                    String strM1113l2 = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                uri2 = (Uri) C0901a.m1096a(parcel, iM1109i, Uri.CREATOR);
                                break;
                            case 4:
                                uri = (Uri) C0901a.m1096a(parcel, iM1109i, Uri.CREATOR);
                                break;
                            case 5:
                                jM1106g = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 1000:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new PlayerEntity(iM1105f, strM1113l2, strM1113l, uri2, uri, jM1106g);
                }
            }
