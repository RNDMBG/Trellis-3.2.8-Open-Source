            package com.google.android.gms.games;

            import android.net.Uri;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C0911a implements Parcelable.Creator<GameEntity> {
                /* JADX INFO: renamed from: a */
                static void m1183a(GameEntity gameEntity, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1140a(parcel, 1, gameEntity.getApplicationId(), false);
                    C0902b.m1140a(parcel, 2, gameEntity.getDisplayName(), false);
                    C0902b.m1140a(parcel, 3, gameEntity.getPrimaryCategory(), false);
                    C0902b.m1140a(parcel, 4, gameEntity.getSecondaryCategory(), false);
                    C0902b.m1140a(parcel, 5, gameEntity.getDescription(), false);
                    C0902b.m1140a(parcel, 6, gameEntity.getDeveloperName(), false);
                    C0902b.m1139a(parcel, 7, (Parcelable) gameEntity.getIconImageUri(), i, false);
                    C0902b.m1139a(parcel, 8, (Parcelable) gameEntity.getHiResImageUri(), i, false);
                    C0902b.m1139a(parcel, 9, (Parcelable) gameEntity.getFeaturedImageUri(), i, false);
                    C0902b.m1143a(parcel, 10, gameEntity.isPlayEnabledGame());
                    C0902b.m1143a(parcel, 11, gameEntity.isInstanceInstalled());
                    C0902b.m1140a(parcel, 12, gameEntity.getInstancePackageName(), false);
                    C0902b.m1150c(parcel, 13, gameEntity.getGameplayAclStatus());
                    C0902b.m1150c(parcel, 14, gameEntity.getAchievementTotalCount());
                    C0902b.m1150c(parcel, 15, gameEntity.getLeaderboardCount());
                    C0902b.m1150c(parcel, 1000, gameEntity.getVersionCode());
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: K */
                public GameEntity[] m1184K(int i) {
                    return new GameEntity[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ GameEntity createFromParcel(Parcel x0) {
                    return mo1175t(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ GameEntity[] newArray(int x0) {
                    return m1184K(x0);
                }

                /* JADX INFO: renamed from: t */
                public GameEntity mo1175t(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    String strM1113l = null;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    String strM1113l4 = null;
                    String strM1113l5 = null;
                    String strM1113l6 = null;
                    Uri uri = null;
                    Uri uri2 = null;
                    Uri uri3 = null;
                    boolean zM1102c = false;
                    boolean zM1102c2 = false;
                    String strM1113l7 = null;
                    int iM1105f2 = 0;
                    int iM1105f3 = 0;
                    int iM1105f4 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 2:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 4:
                                strM1113l4 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 5:
                                strM1113l5 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 6:
                                strM1113l6 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 7:
                                uri = (Uri) C0901a.m1096a(parcel, iM1109i, Uri.CREATOR);
                                break;
                            case 8:
                                uri2 = (Uri) C0901a.m1096a(parcel, iM1109i, Uri.CREATOR);
                                break;
                            case 9:
                                uri3 = (Uri) C0901a.m1096a(parcel, iM1109i, Uri.CREATOR);
                                break;
                            case 10:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 11:
                                zM1102c2 = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 12:
                                strM1113l7 = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 13:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 14:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 15:
                                iM1105f4 = C0901a.m1105f(parcel, iM1109i);
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
                    return new GameEntity(iM1105f, strM1113l, strM1113l2, strM1113l3, strM1113l4, strM1113l5, strM1113l6, uri, uri2, uri3, zM1102c, zM1102c2, strM1113l7, iM1105f2, iM1105f3, iM1105f4);
                }
            }
