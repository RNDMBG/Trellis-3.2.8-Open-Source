            package com.google.android.gms.games.multiplayer;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import com.google.android.gms.games.GameEntity;
            import java.util.ArrayList;
            
            /* JADX INFO: renamed from: com.google.android.gms.games.multiplayer.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C0923a implements Parcelable.Creator<InvitationEntity> {
                /* JADX INFO: renamed from: a */
                static void m1215a(InvitationEntity invitationEntity, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1139a(parcel, 1, (Parcelable) invitationEntity.getGame(), i, false);
                    C0902b.m1150c(parcel, 1000, invitationEntity.getVersionCode());
                    C0902b.m1140a(parcel, 2, invitationEntity.getInvitationId(), false);
                    C0902b.m1135a(parcel, 3, invitationEntity.getCreationTimestamp());
                    C0902b.m1150c(parcel, 4, invitationEntity.mo1198ch());
                    C0902b.m1139a(parcel, 5, (Parcelable) invitationEntity.getInviter(), i, false);
                    C0902b.m1149b(parcel, 6, invitationEntity.getParticipants(), false);
                    C0902b.m1150c(parcel, 7, invitationEntity.getVariant());
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: S */
                public InvitationEntity[] m1216S(int i) {
                    return new InvitationEntity[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ InvitationEntity createFromParcel(Parcel x0) {
                    return mo1205v(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ InvitationEntity[] newArray(int x0) {
                    return m1216S(x0);
                }

                /* JADX INFO: renamed from: v */
                public InvitationEntity mo1205v(Parcel parcel) {
                    int iM1105f = 0;
                    ArrayList arrayListM1101c = null;
                    int iM1111j = C0901a.m1111j(parcel);
                    long jM1106g = 0;
                    ParticipantEntity participantEntity = null;
                    int iM1105f2 = 0;
                    String strM1113l = null;
                    GameEntity gameEntity = null;
                    int iM1105f3 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                gameEntity = (GameEntity) C0901a.m1096a(parcel, iM1109i, GameEntity.CREATOR);
                                break;
                            case 2:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                break;
                            case 3:
                                jM1106g = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 4:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 5:
                                participantEntity = (ParticipantEntity) C0901a.m1096a(parcel, iM1109i, ParticipantEntity.CREATOR);
                                break;
                            case 6:
                                arrayListM1101c = C0901a.m1101c(parcel, iM1109i, ParticipantEntity.CREATOR);
                                break;
                            case 7:
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
                    return new InvitationEntity(iM1105f3, gameEntity, strM1113l, jM1106g, iM1105f2, participantEntity, arrayListM1101c, iM1105f);
                }
            }
