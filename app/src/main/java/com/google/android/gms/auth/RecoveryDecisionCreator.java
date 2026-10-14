            package com.google.android.gms.auth;

            import android.app.PendingIntent;
            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class RecoveryDecisionCreator implements Parcelable.Creator<RecoveryDecision> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m1007a(RecoveryDecision recoveryDecision, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, recoveryDecision.f927iM);
                    C0902b.m1139a(parcel, 2, (Parcelable) recoveryDecision.recoveryIntent, i, false);
                    C0902b.m1143a(parcel, 3, recoveryDecision.showRecoveryInterstitial);
                    C0902b.m1143a(parcel, 4, recoveryDecision.isRecoveryInfoNeeded);
                    C0902b.m1143a(parcel, 5, recoveryDecision.isRecoveryInterstitialAllowed);
                    C0902b.m1139a(parcel, 6, (Parcelable) recoveryDecision.recoveryIntentWithoutIntro, i, false);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public RecoveryDecision createFromParcel(Parcel parcel) {
                    PendingIntent pendingIntent = null;
                    boolean zM1102c = false;
                    int iM1111j = C0901a.m1111j(parcel);
                    boolean zM1102c2 = false;
                    boolean zM1102c3 = false;
                    PendingIntent pendingIntent2 = null;
                    int iM1105f = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                pendingIntent2 = (PendingIntent) C0901a.m1096a(parcel, iM1109i, PendingIntent.CREATOR);
                                break;
                            case 3:
                                zM1102c3 = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 4:
                                zM1102c2 = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 5:
                                zM1102c = C0901a.m1102c(parcel, iM1109i);
                                break;
                            case 6:
                                pendingIntent = (PendingIntent) C0901a.m1096a(parcel, iM1109i, PendingIntent.CREATOR);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new RecoveryDecision(iM1105f, pendingIntent2, zM1102c3, zM1102c2, zM1102c, pendingIntent);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public RecoveryDecision[] newArray(int size) {
                    return new RecoveryDecision[size];
                }

            }
