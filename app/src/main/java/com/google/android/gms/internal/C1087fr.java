            package com.google.android.gms.internal;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.ArrayList;
            import java.util.HashSet;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fr */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1087fr implements Parcelable.Creator<C1086fq> {
                /* JADX INFO: renamed from: a */
                static void m2014a(C1086fq c1086fq, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    Set<Integer> setM1997di = c1086fq.m1997di();
                    if (setM1997di.contains(1)) {
                        C0902b.m1150c(parcel, 1, c1086fq.getVersionCode());
                    }
                    if (setM1997di.contains(2)) {
                        C0902b.m1139a(parcel, 2, (Parcelable) c1086fq.m1998dj(), i, true);
                    }
                    if (setM1997di.contains(3)) {
                        C0902b.m1141a(parcel, 3, c1086fq.getAdditionalName(), true);
                    }
                    if (setM1997di.contains(4)) {
                        C0902b.m1139a(parcel, 4, (Parcelable) c1086fq.m1999dk(), i, true);
                    }
                    if (setM1997di.contains(5)) {
                        C0902b.m1140a(parcel, 5, c1086fq.getAddressCountry(), true);
                    }
                    if (setM1997di.contains(6)) {
                        C0902b.m1140a(parcel, 6, c1086fq.getAddressLocality(), true);
                    }
                    if (setM1997di.contains(7)) {
                        C0902b.m1140a(parcel, 7, c1086fq.getAddressRegion(), true);
                    }
                    if (setM1997di.contains(8)) {
                        C0902b.m1149b(parcel, 8, c1086fq.m2000dl(), true);
                    }
                    if (setM1997di.contains(9)) {
                        C0902b.m1150c(parcel, 9, c1086fq.getAttendeeCount());
                    }
                    if (setM1997di.contains(10)) {
                        C0902b.m1149b(parcel, 10, c1086fq.m2001dm(), true);
                    }
                    if (setM1997di.contains(11)) {
                        C0902b.m1139a(parcel, 11, (Parcelable) c1086fq.m2002dn(), i, true);
                    }
                    if (setM1997di.contains(12)) {
                        C0902b.m1149b(parcel, 12, c1086fq.m2003do(), true);
                    }
                    if (setM1997di.contains(13)) {
                        C0902b.m1140a(parcel, 13, c1086fq.getBestRating(), true);
                    }
                    if (setM1997di.contains(14)) {
                        C0902b.m1140a(parcel, 14, c1086fq.getBirthDate(), true);
                    }
                    if (setM1997di.contains(15)) {
                        C0902b.m1139a(parcel, 15, (Parcelable) c1086fq.m2004dp(), i, true);
                    }
                    if (setM1997di.contains(17)) {
                        C0902b.m1140a(parcel, 17, c1086fq.getContentSize(), true);
                    }
                    if (setM1997di.contains(16)) {
                        C0902b.m1140a(parcel, 16, c1086fq.getCaption(), true);
                    }
                    if (setM1997di.contains(19)) {
                        C0902b.m1149b(parcel, 19, c1086fq.m2005dq(), true);
                    }
                    if (setM1997di.contains(18)) {
                        C0902b.m1140a(parcel, 18, c1086fq.getContentUrl(), true);
                    }
                    if (setM1997di.contains(21)) {
                        C0902b.m1140a(parcel, 21, c1086fq.getDateModified(), true);
                    }
                    if (setM1997di.contains(20)) {
                        C0902b.m1140a(parcel, 20, c1086fq.getDateCreated(), true);
                    }
                    if (setM1997di.contains(23)) {
                        C0902b.m1140a(parcel, 23, c1086fq.getDescription(), true);
                    }
                    if (setM1997di.contains(22)) {
                        C0902b.m1140a(parcel, 22, c1086fq.getDatePublished(), true);
                    }
                    if (setM1997di.contains(25)) {
                        C0902b.m1140a(parcel, 25, c1086fq.getEmbedUrl(), true);
                    }
                    if (setM1997di.contains(24)) {
                        C0902b.m1140a(parcel, 24, c1086fq.getDuration(), true);
                    }
                    if (setM1997di.contains(27)) {
                        C0902b.m1140a(parcel, 27, c1086fq.getFamilyName(), true);
                    }
                    if (setM1997di.contains(26)) {
                        C0902b.m1140a(parcel, 26, c1086fq.getEndDate(), true);
                    }
                    if (setM1997di.contains(29)) {
                        C0902b.m1139a(parcel, 29, (Parcelable) c1086fq.m2006dr(), i, true);
                    }
                    if (setM1997di.contains(28)) {
                        C0902b.m1140a(parcel, 28, c1086fq.getGender(), true);
                    }
                    if (setM1997di.contains(31)) {
                        C0902b.m1140a(parcel, 31, c1086fq.getHeight(), true);
                    }
                    if (setM1997di.contains(30)) {
                        C0902b.m1140a(parcel, 30, c1086fq.getGivenName(), true);
                    }
                    if (setM1997di.contains(34)) {
                        C0902b.m1139a(parcel, 34, (Parcelable) c1086fq.m2007ds(), i, true);
                    }
                    if (setM1997di.contains(32)) {
                        C0902b.m1140a(parcel, 32, c1086fq.getId(), true);
                    }
                    if (setM1997di.contains(33)) {
                        C0902b.m1140a(parcel, 33, c1086fq.getImage(), true);
                    }
                    if (setM1997di.contains(38)) {
                        C0902b.m1133a(parcel, 38, c1086fq.getLongitude());
                    }
                    if (setM1997di.contains(39)) {
                        C0902b.m1140a(parcel, 39, c1086fq.getName(), true);
                    }
                    if (setM1997di.contains(36)) {
                        C0902b.m1133a(parcel, 36, c1086fq.getLatitude());
                    }
                    if (setM1997di.contains(37)) {
                        C0902b.m1139a(parcel, 37, (Parcelable) c1086fq.m2008dt(), i, true);
                    }
                    if (setM1997di.contains(42)) {
                        C0902b.m1140a(parcel, 42, c1086fq.getPlayerType(), true);
                    }
                    if (setM1997di.contains(43)) {
                        C0902b.m1140a(parcel, 43, c1086fq.getPostOfficeBoxNumber(), true);
                    }
                    if (setM1997di.contains(40)) {
                        C0902b.m1139a(parcel, 40, (Parcelable) c1086fq.m2009du(), i, true);
                    }
                    if (setM1997di.contains(41)) {
                        C0902b.m1149b(parcel, 41, c1086fq.m2010dv(), true);
                    }
                    if (setM1997di.contains(46)) {
                        C0902b.m1139a(parcel, 46, (Parcelable) c1086fq.m2011dw(), i, true);
                    }
                    if (setM1997di.contains(47)) {
                        C0902b.m1140a(parcel, 47, c1086fq.getStartDate(), true);
                    }
                    if (setM1997di.contains(44)) {
                        C0902b.m1140a(parcel, 44, c1086fq.getPostalCode(), true);
                    }
                    if (setM1997di.contains(45)) {
                        C0902b.m1140a(parcel, 45, c1086fq.getRatingValue(), true);
                    }
                    if (setM1997di.contains(51)) {
                        C0902b.m1140a(parcel, 51, c1086fq.getThumbnailUrl(), true);
                    }
                    if (setM1997di.contains(50)) {
                        C0902b.m1139a(parcel, 50, (Parcelable) c1086fq.m2012dx(), i, true);
                    }
                    if (setM1997di.contains(49)) {
                        C0902b.m1140a(parcel, 49, c1086fq.getText(), true);
                    }
                    if (setM1997di.contains(48)) {
                        C0902b.m1140a(parcel, 48, c1086fq.getStreetAddress(), true);
                    }
                    if (setM1997di.contains(55)) {
                        C0902b.m1140a(parcel, 55, c1086fq.getWidth(), true);
                    }
                    if (setM1997di.contains(54)) {
                        C0902b.m1140a(parcel, 54, c1086fq.getUrl(), true);
                    }
                    if (setM1997di.contains(53)) {
                        C0902b.m1140a(parcel, 53, c1086fq.getType(), true);
                    }
                    if (setM1997di.contains(52)) {
                        C0902b.m1140a(parcel, 52, c1086fq.getTickerSymbol(), true);
                    }
                    if (setM1997di.contains(56)) {
                        C0902b.m1140a(parcel, 56, c1086fq.getWorstRating(), true);
                    }
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX INFO: renamed from: B */
                public C1086fq m2015B(Parcel parcel) {
                    int iM1111j = C0901a.m1111j(parcel);
                    HashSet hashSet = new HashSet();
                    int iM1105f = 0;
                    C1086fq c1086fq = null;
                    ArrayList<String> arrayListM1125x = null;
                    C1086fq c1086fq2 = null;
                    String strM1113l = null;
                    String strM1113l2 = null;
                    String strM1113l3 = null;
                    ArrayList arrayListM1101c = null;
                    int iM1105f2 = 0;
                    ArrayList arrayListM1101c2 = null;
                    C1086fq c1086fq3 = null;
                    ArrayList arrayListM1101c3 = null;
                    String strM1113l4 = null;
                    String strM1113l5 = null;
                    C1086fq c1086fq4 = null;
                    String strM1113l6 = null;
                    String strM1113l7 = null;
                    String strM1113l8 = null;
                    ArrayList arrayListM1101c4 = null;
                    String strM1113l9 = null;
                    String strM1113l10 = null;
                    String strM1113l11 = null;
                    String strM1113l12 = null;
                    String strM1113l13 = null;
                    String strM1113l14 = null;
                    String strM1113l15 = null;
                    String strM1113l16 = null;
                    String strM1113l17 = null;
                    C1086fq c1086fq5 = null;
                    String strM1113l18 = null;
                    String strM1113l19 = null;
                    String strM1113l20 = null;
                    String strM1113l21 = null;
                    C1086fq c1086fq6 = null;
                    double dM1110j = 0.0d;
                    C1086fq c1086fq7 = null;
                    double dM1110j2 = 0.0d;
                    String strM1113l22 = null;
                    C1086fq c1086fq8 = null;
                    ArrayList arrayListM1101c5 = null;
                    String strM1113l23 = null;
                    String strM1113l24 = null;
                    String strM1113l25 = null;
                    String strM1113l26 = null;
                    C1086fq c1086fq9 = null;
                    String strM1113l27 = null;
                    String strM1113l28 = null;
                    String strM1113l29 = null;
                    C1086fq c1086fq10 = null;
                    String strM1113l30 = null;
                    String strM1113l31 = null;
                    String strM1113l32 = null;
                    String strM1113l33 = null;
                    String strM1113l34 = null;
                    String strM1113l35 = null;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(1);
                                break;
                            case 2:
                                C1086fq c1086fq11 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(2);
                                c1086fq = c1086fq11;
                                break;
                            case 3:
                                arrayListM1125x = C0901a.m1125x(parcel, iM1109i);
                                hashSet.add(3);
                                break;
                            case 4:
                                C1086fq c1086fq12 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(4);
                                c1086fq2 = c1086fq12;
                                break;
                            case 5:
                                strM1113l = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(5);
                                break;
                            case 6:
                                strM1113l2 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(6);
                                break;
                            case 7:
                                strM1113l3 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(7);
                                break;
                            case 8:
                                arrayListM1101c = C0901a.m1101c(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(8);
                                break;
                            case 9:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                hashSet.add(9);
                                break;
                            case 10:
                                arrayListM1101c2 = C0901a.m1101c(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(10);
                                break;
                            case 11:
                                C1086fq c1086fq13 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(11);
                                c1086fq3 = c1086fq13;
                                break;
                            case 12:
                                arrayListM1101c3 = C0901a.m1101c(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(12);
                                break;
                            case 13:
                                strM1113l4 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(13);
                                break;
                            case 14:
                                strM1113l5 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(14);
                                break;
                            case 15:
                                C1086fq c1086fq14 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(15);
                                c1086fq4 = c1086fq14;
                                break;
                            case 16:
                                strM1113l6 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(16);
                                break;
                            case 17:
                                strM1113l7 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(17);
                                break;
                            case 18:
                                strM1113l8 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(18);
                                break;
                            case 19:
                                arrayListM1101c4 = C0901a.m1101c(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(19);
                                break;
                            case 20:
                                strM1113l9 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(20);
                                break;
                            case 21:
                                strM1113l10 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(21);
                                break;
                            case 22:
                                strM1113l11 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(22);
                                break;
                            case 23:
                                strM1113l12 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(23);
                                break;
                            case 24:
                                strM1113l13 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(24);
                                break;
                            case 25:
                                strM1113l14 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(25);
                                break;
                            case 26:
                                strM1113l15 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(26);
                                break;
                            case 27:
                                strM1113l16 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(27);
                                break;
                            case 28:
                                strM1113l17 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(28);
                                break;
                            case 29:
                                C1086fq c1086fq15 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(29);
                                c1086fq5 = c1086fq15;
                                break;
                            case 30:
                                strM1113l18 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(30);
                                break;
                            case 31:
                                strM1113l19 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(31);
                                break;
                            case 32:
                                strM1113l20 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(32);
                                break;
                            case 33:
                                strM1113l21 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(33);
                                break;
                            case 34:
                                C1086fq c1086fq16 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(34);
                                c1086fq6 = c1086fq16;
                                break;
                            case 35:
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                            case 36:
                                dM1110j = C0901a.m1110j(parcel, iM1109i);
                                hashSet.add(36);
                                break;
                            case 37:
                                C1086fq c1086fq17 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(37);
                                c1086fq7 = c1086fq17;
                                break;
                            case 38:
                                dM1110j2 = C0901a.m1110j(parcel, iM1109i);
                                hashSet.add(38);
                                break;
                            case 39:
                                strM1113l22 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(39);
                                break;
                            case 40:
                                C1086fq c1086fq18 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(40);
                                c1086fq8 = c1086fq18;
                                break;
                            case 41:
                                arrayListM1101c5 = C0901a.m1101c(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(41);
                                break;
                            case 42:
                                strM1113l23 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(42);
                                break;
                            case 43:
                                strM1113l24 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(43);
                                break;
                            case 44:
                                strM1113l25 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(44);
                                break;
                            case 45:
                                strM1113l26 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(45);
                                break;
                            case 46:
                                C1086fq c1086fq19 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(46);
                                c1086fq9 = c1086fq19;
                                break;
                            case 47:
                                strM1113l27 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(47);
                                break;
                            case 48:
                                strM1113l28 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(48);
                                break;
                            case 49:
                                strM1113l29 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(49);
                                break;
                            case 50:
                                C1086fq c1086fq20 = (C1086fq) C0901a.m1096a(parcel, iM1109i, C1086fq.CREATOR);
                                hashSet.add(50);
                                c1086fq10 = c1086fq20;
                                break;
                            case 51:
                                strM1113l30 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(51);
                                break;
                            case 52:
                                strM1113l31 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(52);
                                break;
                            case 53:
                                strM1113l32 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(53);
                                break;
                            case 54:
                                strM1113l33 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(54);
                                break;
                            case 55:
                                strM1113l34 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(55);
                                break;
                            case 56:
                                strM1113l35 = C0901a.m1113l(parcel, iM1109i);
                                hashSet.add(56);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new C1086fq(hashSet, iM1105f, c1086fq, arrayListM1125x, c1086fq2, strM1113l, strM1113l2, strM1113l3, arrayListM1101c, iM1105f2, arrayListM1101c2, c1086fq3, arrayListM1101c3, strM1113l4, strM1113l5, c1086fq4, strM1113l6, strM1113l7, strM1113l8, arrayListM1101c4, strM1113l9, strM1113l10, strM1113l11, strM1113l12, strM1113l13, strM1113l14, strM1113l15, strM1113l16, strM1113l17, c1086fq5, strM1113l18, strM1113l19, strM1113l20, strM1113l21, c1086fq6, dM1110j, c1086fq7, dM1110j2, strM1113l22, c1086fq8, arrayListM1101c5, strM1113l23, strM1113l24, strM1113l25, strM1113l26, c1086fq9, strM1113l27, strM1113l28, strM1113l29, c1086fq10, strM1113l30, strM1113l31, strM1113l32, strM1113l33, strM1113l34, strM1113l35);
                }

                /* JADX INFO: renamed from: ag */
                public C1086fq[] m2016ag(int i) {
                    return new C1086fq[i];
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1086fq createFromParcel(Parcel x0) {
                    return m2015B(x0);
                }

                @Override // android.os.Parcelable.Creator
                public /* synthetic */ C1086fq[] newArray(int x0) {
                    return m2016ag(x0);
                }
            }
