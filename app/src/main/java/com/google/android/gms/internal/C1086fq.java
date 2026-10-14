            package com.google.android.gms.internal;

            import android.os.Parcel;
            import co.vine.android.FindFriendsBaseActivity;
            import co.vine.android.Settings;
            import co.vine.android.service.VineUploadService;
            import com.google.android.exoplayer.util.MimeTypes;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.plus.PlusShare;
            import com.google.android.gms.plus.model.moments.ItemScope;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fq */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1086fq extends AbstractC1038dw implements SafeParcelable, ItemScope {
                public static final C1087fr CREATOR = new C1087fr();

                /* JADX INFO: renamed from: rH */
                private static final HashMap<String, AbstractC1038dw.a<?, ?>> f1952rH = new HashMap<>();

                /* JADX INFO: renamed from: hN */
                private String f1953hN;

                /* JADX INFO: renamed from: iM */
                private final int f1954iM;
                private String mName;

                /* JADX INFO: renamed from: mo */
                private String f1955mo;

                /* JADX INFO: renamed from: oE */
                private double f1956oE;

                /* JADX INFO: renamed from: oF */
                private double f1957oF;

                /* JADX INFO: renamed from: rI */
                private final Set<Integer> f1958rI;

                /* JADX INFO: renamed from: rJ */
                private C1086fq f1959rJ;

                /* JADX INFO: renamed from: rK */
                private List<String> f1960rK;

                /* JADX INFO: renamed from: rL */
                private C1086fq f1961rL;

                /* JADX INFO: renamed from: rM */
                private String f1962rM;

                /* JADX INFO: renamed from: rN */
                private String f1963rN;

                /* JADX INFO: renamed from: rO */
                private String f1964rO;

                /* JADX INFO: renamed from: rP */
                private List<C1086fq> f1965rP;

                /* JADX INFO: renamed from: rQ */
                private int f1966rQ;

                /* JADX INFO: renamed from: rR */
                private List<C1086fq> f1967rR;

                /* JADX INFO: renamed from: rS */
                private C1086fq f1968rS;

                /* JADX INFO: renamed from: rT */
                private List<C1086fq> f1969rT;

                /* JADX INFO: renamed from: rU */
                private String f1970rU;

                /* JADX INFO: renamed from: rV */
                private String f1971rV;

                /* JADX INFO: renamed from: rW */
                private C1086fq f1972rW;

                /* JADX INFO: renamed from: rX */
                private String f1973rX;

                /* JADX INFO: renamed from: rY */
                private String f1974rY;

                /* JADX INFO: renamed from: rZ */
                private String f1975rZ;

                /* JADX INFO: renamed from: sA */
                private C1086fq f1976sA;

                /* JADX INFO: renamed from: sB */
                private String f1977sB;

                /* JADX INFO: renamed from: sC */
                private String f1978sC;

                /* JADX INFO: renamed from: sD */
                private String f1979sD;

                /* JADX INFO: renamed from: sE */
                private String f1980sE;

                /* JADX INFO: renamed from: sF */
                private String f1981sF;

                /* JADX INFO: renamed from: sa */
                private List<C1086fq> f1982sa;

                /* JADX INFO: renamed from: sb */
                private String f1983sb;

                /* JADX INFO: renamed from: sc */
                private String f1984sc;

                /* JADX INFO: renamed from: sd */
                private String f1985sd;

                /* JADX INFO: renamed from: se */
                private String f1986se;

                /* JADX INFO: renamed from: sf */
                private String f1987sf;

                /* JADX INFO: renamed from: sg */
                private String f1988sg;

                /* JADX INFO: renamed from: sh */
                private String f1989sh;

                /* JADX INFO: renamed from: si */
                private String f1990si;

                /* JADX INFO: renamed from: sj */
                private C1086fq f1991sj;

                /* JADX INFO: renamed from: sk */
                private String f1992sk;

                /* JADX INFO: renamed from: sl */
                private String f1993sl;

                /* JADX INFO: renamed from: sm */
                private String f1994sm;

                /* JADX INFO: renamed from: sn */
                private String f1995sn;

                /* JADX INFO: renamed from: so */
                private C1086fq f1996so;

                /* JADX INFO: renamed from: sp */
                private C1086fq f1997sp;

                /* JADX INFO: renamed from: sq */
                private C1086fq f1998sq;

                /* JADX INFO: renamed from: sr */
                private List<C1086fq> f1999sr;

                /* JADX INFO: renamed from: ss */
                private String f2000ss;

                /* JADX INFO: renamed from: st */
                private String f2001st;

                /* JADX INFO: renamed from: su */
                private String f2002su;

                /* JADX INFO: renamed from: sv */
                private String f2003sv;

                /* JADX INFO: renamed from: sw */
                private C1086fq f2004sw;

                /* JADX INFO: renamed from: sx */
                private String f2005sx;

                /* JADX INFO: renamed from: sy */
                private String f2006sy;

                /* JADX INFO: renamed from: sz */
                private String f2007sz;

                static {
                    f1952rH.put(Settings.ABOUT, AbstractC1038dw.a.m1652a(Settings.ABOUT, 2, C1086fq.class));
                    f1952rH.put("additionalName", AbstractC1038dw.a.m1659h("additionalName", 3));
                    f1952rH.put(FindFriendsBaseActivity.TAG_ADDRESS_BOOK, AbstractC1038dw.a.m1652a(FindFriendsBaseActivity.TAG_ADDRESS_BOOK, 4, C1086fq.class));
                    f1952rH.put("addressCountry", AbstractC1038dw.a.m1658g("addressCountry", 5));
                    f1952rH.put("addressLocality", AbstractC1038dw.a.m1658g("addressLocality", 6));
                    f1952rH.put("addressRegion", AbstractC1038dw.a.m1658g("addressRegion", 7));
                    f1952rH.put("associated_media", AbstractC1038dw.a.m1653b("associated_media", 8, C1086fq.class));
                    f1952rH.put("attendeeCount", AbstractC1038dw.a.m1655d("attendeeCount", 9));
                    f1952rH.put("attendees", AbstractC1038dw.a.m1653b("attendees", 10, C1086fq.class));
                    f1952rH.put(MimeTypes.BASE_TYPE_AUDIO, AbstractC1038dw.a.m1652a(MimeTypes.BASE_TYPE_AUDIO, 11, C1086fq.class));
                    f1952rH.put("author", AbstractC1038dw.a.m1653b("author", 12, C1086fq.class));
                    f1952rH.put("bestRating", AbstractC1038dw.a.m1658g("bestRating", 13));
                    f1952rH.put("birthDate", AbstractC1038dw.a.m1658g("birthDate", 14));
                    f1952rH.put("byArtist", AbstractC1038dw.a.m1652a("byArtist", 15, C1086fq.class));
                    f1952rH.put("caption", AbstractC1038dw.a.m1658g("caption", 16));
                    f1952rH.put("contentSize", AbstractC1038dw.a.m1658g("contentSize", 17));
                    f1952rH.put("contentUrl", AbstractC1038dw.a.m1658g("contentUrl", 18));
                    f1952rH.put("contributor", AbstractC1038dw.a.m1653b("contributor", 19, C1086fq.class));
                    f1952rH.put("dateCreated", AbstractC1038dw.a.m1658g("dateCreated", 20));
                    f1952rH.put("dateModified", AbstractC1038dw.a.m1658g("dateModified", 21));
                    f1952rH.put("datePublished", AbstractC1038dw.a.m1658g("datePublished", 22));
                    f1952rH.put("description", AbstractC1038dw.a.m1658g("description", 23));
                    f1952rH.put("duration", AbstractC1038dw.a.m1658g("duration", 24));
                    f1952rH.put("embedUrl", AbstractC1038dw.a.m1658g("embedUrl", 25));
                    f1952rH.put("endDate", AbstractC1038dw.a.m1658g("endDate", 26));
                    f1952rH.put("familyName", AbstractC1038dw.a.m1658g("familyName", 27));
                    f1952rH.put("gender", AbstractC1038dw.a.m1658g("gender", 28));
                    f1952rH.put("geo", AbstractC1038dw.a.m1652a("geo", 29, C1086fq.class));
                    f1952rH.put("givenName", AbstractC1038dw.a.m1658g("givenName", 30));
                    f1952rH.put("height", AbstractC1038dw.a.m1658g("height", 31));
                    f1952rH.put("id", AbstractC1038dw.a.m1658g("id", 32));
                    f1952rH.put("image", AbstractC1038dw.a.m1658g("image", 33));
                    f1952rH.put("inAlbum", AbstractC1038dw.a.m1652a("inAlbum", 34, C1086fq.class));
                    f1952rH.put("latitude", AbstractC1038dw.a.m1656e("latitude", 36));
                    f1952rH.put("location", AbstractC1038dw.a.m1652a("location", 37, C1086fq.class));
                    f1952rH.put("longitude", AbstractC1038dw.a.m1656e("longitude", 38));
                    f1952rH.put("name", AbstractC1038dw.a.m1658g("name", 39));
                    f1952rH.put("partOfTVSeries", AbstractC1038dw.a.m1652a("partOfTVSeries", 40, C1086fq.class));
                    f1952rH.put("performers", AbstractC1038dw.a.m1653b("performers", 41, C1086fq.class));
                    f1952rH.put("playerType", AbstractC1038dw.a.m1658g("playerType", 42));
                    f1952rH.put("postOfficeBoxNumber", AbstractC1038dw.a.m1658g("postOfficeBoxNumber", 43));
                    f1952rH.put("postalCode", AbstractC1038dw.a.m1658g("postalCode", 44));
                    f1952rH.put("ratingValue", AbstractC1038dw.a.m1658g("ratingValue", 45));
                    f1952rH.put("reviewRating", AbstractC1038dw.a.m1652a("reviewRating", 46, C1086fq.class));
                    f1952rH.put("startDate", AbstractC1038dw.a.m1658g("startDate", 47));
                    f1952rH.put("streetAddress", AbstractC1038dw.a.m1658g("streetAddress", 48));
                    f1952rH.put(MimeTypes.BASE_TYPE_TEXT, AbstractC1038dw.a.m1658g(MimeTypes.BASE_TYPE_TEXT, 49));
                    f1952rH.put(VineUploadService.AG_THUMBNAIL, AbstractC1038dw.a.m1652a(VineUploadService.AG_THUMBNAIL, 50, C1086fq.class));
                    f1952rH.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, AbstractC1038dw.a.m1658g(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, 51));
                    f1952rH.put("tickerSymbol", AbstractC1038dw.a.m1658g("tickerSymbol", 52));
                    f1952rH.put("type", AbstractC1038dw.a.m1658g("type", 53));
                    f1952rH.put(PlusShare.KEY_CALL_TO_ACTION_URL, AbstractC1038dw.a.m1658g(PlusShare.KEY_CALL_TO_ACTION_URL, 54));
                    f1952rH.put("width", AbstractC1038dw.a.m1658g("width", 55));
                    f1952rH.put("worstRating", AbstractC1038dw.a.m1658g("worstRating", 56));
                }

                public C1086fq() {
                    this.f1954iM = 1;
                    this.f1958rI = new HashSet();
                }

                C1086fq(Set<Integer> set, int i, C1086fq c1086fq, List<String> list, C1086fq c1086fq2, String str, String str2, String str3, List<C1086fq> list2, int i2, List<C1086fq> list3, C1086fq c1086fq3, List<C1086fq> list4, String str4, String str5, C1086fq c1086fq4, String str6, String str7, String str8, List<C1086fq> list5, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, C1086fq c1086fq5, String str18, String str19, String str20, String str21, C1086fq c1086fq6, double d, C1086fq c1086fq7, double d2, String str22, C1086fq c1086fq8, List<C1086fq> list6, String str23, String str24, String str25, String str26, C1086fq c1086fq9, String str27, String str28, String str29, C1086fq c1086fq10, String str30, String str31, String str32, String str33, String str34, String str35) {
                    this.f1958rI = set;
                    this.f1954iM = i;
                    this.f1959rJ = c1086fq;
                    this.f1960rK = list;
                    this.f1961rL = c1086fq2;
                    this.f1962rM = str;
                    this.f1963rN = str2;
                    this.f1964rO = str3;
                    this.f1965rP = list2;
                    this.f1966rQ = i2;
                    this.f1967rR = list3;
                    this.f1968rS = c1086fq3;
                    this.f1969rT = list4;
                    this.f1970rU = str4;
                    this.f1971rV = str5;
                    this.f1972rW = c1086fq4;
                    this.f1973rX = str6;
                    this.f1974rY = str7;
                    this.f1975rZ = str8;
                    this.f1982sa = list5;
                    this.f1983sb = str9;
                    this.f1984sc = str10;
                    this.f1985sd = str11;
                    this.f1955mo = str12;
                    this.f1986se = str13;
                    this.f1987sf = str14;
                    this.f1988sg = str15;
                    this.f1989sh = str16;
                    this.f1990si = str17;
                    this.f1991sj = c1086fq5;
                    this.f1992sk = str18;
                    this.f1993sl = str19;
                    this.f1994sm = str20;
                    this.f1995sn = str21;
                    this.f1996so = c1086fq6;
                    this.f1956oE = d;
                    this.f1997sp = c1086fq7;
                    this.f1957oF = d2;
                    this.mName = str22;
                    this.f1998sq = c1086fq8;
                    this.f1999sr = list6;
                    this.f2000ss = str23;
                    this.f2001st = str24;
                    this.f2002su = str25;
                    this.f2003sv = str26;
                    this.f2004sw = c1086fq9;
                    this.f2005sx = str27;
                    this.f2006sy = str28;
                    this.f2007sz = str29;
                    this.f1976sA = c1086fq10;
                    this.f1977sB = str30;
                    this.f1978sC = str31;
                    this.f1979sD = str32;
                    this.f1953hN = str33;
                    this.f1980sE = str34;
                    this.f1981sF = str35;
                }

                public C1086fq(Set<Integer> set, C1086fq c1086fq, List<String> list, C1086fq c1086fq2, String str, String str2, String str3, List<C1086fq> list2, int i, List<C1086fq> list3, C1086fq c1086fq3, List<C1086fq> list4, String str4, String str5, C1086fq c1086fq4, String str6, String str7, String str8, List<C1086fq> list5, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, C1086fq c1086fq5, String str18, String str19, String str20, String str21, C1086fq c1086fq6, double d, C1086fq c1086fq7, double d2, String str22, C1086fq c1086fq8, List<C1086fq> list6, String str23, String str24, String str25, String str26, C1086fq c1086fq9, String str27, String str28, String str29, C1086fq c1086fq10, String str30, String str31, String str32, String str33, String str34, String str35) {
                    this.f1958rI = set;
                    this.f1954iM = 1;
                    this.f1959rJ = c1086fq;
                    this.f1960rK = list;
                    this.f1961rL = c1086fq2;
                    this.f1962rM = str;
                    this.f1963rN = str2;
                    this.f1964rO = str3;
                    this.f1965rP = list2;
                    this.f1966rQ = i;
                    this.f1967rR = list3;
                    this.f1968rS = c1086fq3;
                    this.f1969rT = list4;
                    this.f1970rU = str4;
                    this.f1971rV = str5;
                    this.f1972rW = c1086fq4;
                    this.f1973rX = str6;
                    this.f1974rY = str7;
                    this.f1975rZ = str8;
                    this.f1982sa = list5;
                    this.f1983sb = str9;
                    this.f1984sc = str10;
                    this.f1985sd = str11;
                    this.f1955mo = str12;
                    this.f1986se = str13;
                    this.f1987sf = str14;
                    this.f1988sg = str15;
                    this.f1989sh = str16;
                    this.f1990si = str17;
                    this.f1991sj = c1086fq5;
                    this.f1992sk = str18;
                    this.f1993sl = str19;
                    this.f1994sm = str20;
                    this.f1995sn = str21;
                    this.f1996so = c1086fq6;
                    this.f1956oE = d;
                    this.f1997sp = c1086fq7;
                    this.f1957oF = d2;
                    this.mName = str22;
                    this.f1998sq = c1086fq8;
                    this.f1999sr = list6;
                    this.f2000ss = str23;
                    this.f2001st = str24;
                    this.f2002su = str25;
                    this.f2003sv = str26;
                    this.f2004sw = c1086fq9;
                    this.f2005sx = str27;
                    this.f2006sy = str28;
                    this.f2007sz = str29;
                    this.f1976sA = c1086fq10;
                    this.f1977sB = str30;
                    this.f1978sC = str31;
                    this.f1979sD = str32;
                    this.f1953hN = str33;
                    this.f1980sE = str34;
                    this.f1981sF = str35;
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw
                /* JADX INFO: renamed from: D */
                protected Object mo1641D(String str) {
                    return null;
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw
                /* JADX INFO: renamed from: E */
                protected boolean mo1642E(String str) {
                    return false;
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw
                /* JADX INFO: renamed from: a */
                protected boolean mo1646a(AbstractC1038dw.a aVar) {
                    return this.f1958rI.contains(Integer.valueOf(aVar.m1669bw()));
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw
                /* JADX INFO: renamed from: b */
                protected Object mo1647b(AbstractC1038dw.a aVar) {
                    switch (aVar.m1669bw()) {
                        case 2:
                            return this.f1959rJ;
                        case 3:
                            return this.f1960rK;
                        case 4:
                            return this.f1961rL;
                        case 5:
                            return this.f1962rM;
                        case 6:
                            return this.f1963rN;
                        case 7:
                            return this.f1964rO;
                        case 8:
                            return this.f1965rP;
                        case 9:
                            return Integer.valueOf(this.f1966rQ);
                        case 10:
                            return this.f1967rR;
                        case 11:
                            return this.f1968rS;
                        case 12:
                            return this.f1969rT;
                        case 13:
                            return this.f1970rU;
                        case 14:
                            return this.f1971rV;
                        case 15:
                            return this.f1972rW;
                        case 16:
                            return this.f1973rX;
                        case 17:
                            return this.f1974rY;
                        case 18:
                            return this.f1975rZ;
                        case 19:
                            return this.f1982sa;
                        case 20:
                            return this.f1983sb;
                        case 21:
                            return this.f1984sc;
                        case 22:
                            return this.f1985sd;
                        case 23:
                            return this.f1955mo;
                        case 24:
                            return this.f1986se;
                        case 25:
                            return this.f1987sf;
                        case 26:
                            return this.f1988sg;
                        case 27:
                            return this.f1989sh;
                        case 28:
                            return this.f1990si;
                        case 29:
                            return this.f1991sj;
                        case 30:
                            return this.f1992sk;
                        case 31:
                            return this.f1993sl;
                        case 32:
                            return this.f1994sm;
                        case 33:
                            return this.f1995sn;
                        case 34:
                            return this.f1996so;
                        case 35:
                        default:
                            throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                        case 36:
                            return Double.valueOf(this.f1956oE);
                        case 37:
                            return this.f1997sp;
                        case 38:
                            return Double.valueOf(this.f1957oF);
                        case 39:
                            return this.mName;
                        case 40:
                            return this.f1998sq;
                        case 41:
                            return this.f1999sr;
                        case 42:
                            return this.f2000ss;
                        case 43:
                            return this.f2001st;
                        case 44:
                            return this.f2002su;
                        case 45:
                            return this.f2003sv;
                        case 46:
                            return this.f2004sw;
                        case 47:
                            return this.f2005sx;
                        case 48:
                            return this.f2006sy;
                        case 49:
                            return this.f2007sz;
                        case 50:
                            return this.f1976sA;
                        case 51:
                            return this.f1977sB;
                        case 52:
                            return this.f1978sC;
                        case 53:
                            return this.f1979sD;
                        case 54:
                            return this.f1953hN;
                        case 55:
                            return this.f1980sE;
                        case 56:
                            return this.f1981sF;
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw
                /* JADX INFO: renamed from: bp */
                public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                    return f1952rH;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    C1087fr c1087fr = CREATOR;
                    return 0;
                }

                /* JADX INFO: renamed from: di */
                Set<Integer> m1997di() {
                    return this.f1958rI;
                }

                /* JADX INFO: renamed from: dj */
                C1086fq m1998dj() {
                    return this.f1959rJ;
                }

                /* JADX INFO: renamed from: dk */
                C1086fq m1999dk() {
                    return this.f1961rL;
                }

                /* JADX INFO: renamed from: dl */
                List<C1086fq> m2000dl() {
                    return this.f1965rP;
                }

                /* JADX INFO: renamed from: dm */
                List<C1086fq> m2001dm() {
                    return this.f1967rR;
                }

                /* JADX INFO: renamed from: dn */
                C1086fq m2002dn() {
                    return this.f1968rS;
                }

                /* JADX INFO: renamed from: do */
                List<C1086fq> m2003do() {
                    return this.f1969rT;
                }

                /* JADX INFO: renamed from: dp */
                C1086fq m2004dp() {
                    return this.f1972rW;
                }

                /* JADX INFO: renamed from: dq */
                List<C1086fq> m2005dq() {
                    return this.f1982sa;
                }

                /* JADX INFO: renamed from: dr */
                C1086fq m2006dr() {
                    return this.f1991sj;
                }

                /* JADX INFO: renamed from: ds */
                C1086fq m2007ds() {
                    return this.f1996so;
                }

                /* JADX INFO: renamed from: dt */
                C1086fq m2008dt() {
                    return this.f1997sp;
                }

                /* JADX INFO: renamed from: du */
                C1086fq m2009du() {
                    return this.f1998sq;
                }

                /* JADX INFO: renamed from: dv */
                List<C1086fq> m2010dv() {
                    return this.f1999sr;
                }

                /* JADX INFO: renamed from: dw */
                C1086fq m2011dw() {
                    return this.f2004sw;
                }

                /* JADX INFO: renamed from: dx */
                C1086fq m2012dx() {
                    return this.f1976sA;
                }

                /* JADX INFO: renamed from: dy */
                public C1086fq m2013dy() {
                    return this;
                }

                public boolean equals(Object obj) {
                    if (!(obj instanceof C1086fq)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    C1086fq c1086fq = (C1086fq) obj;
                    for (AbstractC1038dw.a<?, ?> aVar : f1952rH.values()) {
                        if (mo1646a(aVar)) {
                            if (c1086fq.mo1646a(aVar) && mo1647b(aVar).equals(c1086fq.mo1647b(aVar))) {
                            }
                            return false;
                        }
                        if (c1086fq.mo1646a(aVar)) {
                            return false;
                        }
                    }
                    return true;
                }

                @Override // com.google.android.gms.common.data.Freezable
                public /* synthetic */ ItemScope freeze() {
                    return m2013dy();
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public ItemScope getAbout() {
                    return this.f1959rJ;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public List<String> getAdditionalName() {
                    return this.f1960rK;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public ItemScope getAddress() {
                    return this.f1961rL;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getAddressCountry() {
                    return this.f1962rM;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getAddressLocality() {
                    return this.f1963rN;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getAddressRegion() {
                    return this.f1964rO;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public List<ItemScope> getAssociated_media() {
                    return (ArrayList) this.f1965rP;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public int getAttendeeCount() {
                    return this.f1966rQ;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public List<ItemScope> getAttendees() {
                    return (ArrayList) this.f1967rR;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public ItemScope getAudio() {
                    return this.f1968rS;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public List<ItemScope> getAuthor() {
                    return (ArrayList) this.f1969rT;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getBestRating() {
                    return this.f1970rU;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getBirthDate() {
                    return this.f1971rV;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public ItemScope getByArtist() {
                    return this.f1972rW;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getCaption() {
                    return this.f1973rX;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getContentSize() {
                    return this.f1974rY;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getContentUrl() {
                    return this.f1975rZ;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public List<ItemScope> getContributor() {
                    return (ArrayList) this.f1982sa;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getDateCreated() {
                    return this.f1983sb;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getDateModified() {
                    return this.f1984sc;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getDatePublished() {
                    return this.f1985sd;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getDescription() {
                    return this.f1955mo;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getDuration() {
                    return this.f1986se;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getEmbedUrl() {
                    return this.f1987sf;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getEndDate() {
                    return this.f1988sg;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getFamilyName() {
                    return this.f1989sh;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getGender() {
                    return this.f1990si;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public ItemScope getGeo() {
                    return this.f1991sj;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getGivenName() {
                    return this.f1992sk;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getHeight() {
                    return this.f1993sl;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getId() {
                    return this.f1994sm;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getImage() {
                    return this.f1995sn;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public ItemScope getInAlbum() {
                    return this.f1996so;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public double getLatitude() {
                    return this.f1956oE;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public ItemScope getLocation() {
                    return this.f1997sp;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public double getLongitude() {
                    return this.f1957oF;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getName() {
                    return this.mName;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public ItemScope getPartOfTVSeries() {
                    return this.f1998sq;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public List<ItemScope> getPerformers() {
                    return (ArrayList) this.f1999sr;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getPlayerType() {
                    return this.f2000ss;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getPostOfficeBoxNumber() {
                    return this.f2001st;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getPostalCode() {
                    return this.f2002su;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getRatingValue() {
                    return this.f2003sv;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public ItemScope getReviewRating() {
                    return this.f2004sw;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getStartDate() {
                    return this.f2005sx;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getStreetAddress() {
                    return this.f2006sy;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getText() {
                    return this.f2007sz;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public ItemScope getThumbnail() {
                    return this.f1976sA;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getThumbnailUrl() {
                    return this.f1977sB;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getTickerSymbol() {
                    return this.f1978sC;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getType() {
                    return this.f1979sD;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getUrl() {
                    return this.f1953hN;
                }

                int getVersionCode() {
                    return this.f1954iM;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getWidth() {
                    return this.f1980sE;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public String getWorstRating() {
                    return this.f1981sF;
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAbout() {
                    return this.f1958rI.contains(2);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAdditionalName() {
                    return this.f1958rI.contains(3);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAddress() {
                    return this.f1958rI.contains(4);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAddressCountry() {
                    return this.f1958rI.contains(5);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAddressLocality() {
                    return this.f1958rI.contains(6);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAddressRegion() {
                    return this.f1958rI.contains(7);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAssociated_media() {
                    return this.f1958rI.contains(8);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAttendeeCount() {
                    return this.f1958rI.contains(9);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAttendees() {
                    return this.f1958rI.contains(10);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAudio() {
                    return this.f1958rI.contains(11);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasAuthor() {
                    return this.f1958rI.contains(12);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasBestRating() {
                    return this.f1958rI.contains(13);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasBirthDate() {
                    return this.f1958rI.contains(14);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasByArtist() {
                    return this.f1958rI.contains(15);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasCaption() {
                    return this.f1958rI.contains(16);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasContentSize() {
                    return this.f1958rI.contains(17);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasContentUrl() {
                    return this.f1958rI.contains(18);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasContributor() {
                    return this.f1958rI.contains(19);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasDateCreated() {
                    return this.f1958rI.contains(20);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasDateModified() {
                    return this.f1958rI.contains(21);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasDatePublished() {
                    return this.f1958rI.contains(22);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasDescription() {
                    return this.f1958rI.contains(23);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasDuration() {
                    return this.f1958rI.contains(24);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasEmbedUrl() {
                    return this.f1958rI.contains(25);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasEndDate() {
                    return this.f1958rI.contains(26);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasFamilyName() {
                    return this.f1958rI.contains(27);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasGender() {
                    return this.f1958rI.contains(28);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasGeo() {
                    return this.f1958rI.contains(29);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasGivenName() {
                    return this.f1958rI.contains(30);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasHeight() {
                    return this.f1958rI.contains(31);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasId() {
                    return this.f1958rI.contains(32);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasImage() {
                    return this.f1958rI.contains(33);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasInAlbum() {
                    return this.f1958rI.contains(34);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasLatitude() {
                    return this.f1958rI.contains(36);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasLocation() {
                    return this.f1958rI.contains(37);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasLongitude() {
                    return this.f1958rI.contains(38);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasName() {
                    return this.f1958rI.contains(39);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasPartOfTVSeries() {
                    return this.f1958rI.contains(40);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasPerformers() {
                    return this.f1958rI.contains(41);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasPlayerType() {
                    return this.f1958rI.contains(42);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasPostOfficeBoxNumber() {
                    return this.f1958rI.contains(43);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasPostalCode() {
                    return this.f1958rI.contains(44);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasRatingValue() {
                    return this.f1958rI.contains(45);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasReviewRating() {
                    return this.f1958rI.contains(46);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasStartDate() {
                    return this.f1958rI.contains(47);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasStreetAddress() {
                    return this.f1958rI.contains(48);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasText() {
                    return this.f1958rI.contains(49);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasThumbnail() {
                    return this.f1958rI.contains(50);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasThumbnailUrl() {
                    return this.f1958rI.contains(51);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasTickerSymbol() {
                    return this.f1958rI.contains(52);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasType() {
                    return this.f1958rI.contains(53);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasUrl() {
                    return this.f1958rI.contains(54);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasWidth() {
                    return this.f1958rI.contains(55);
                }

                @Override // com.google.android.gms.plus.model.moments.ItemScope
                public boolean hasWorstRating() {
                    return this.f1958rI.contains(56);
                }

                public int hashCode() {
                    int iHashCode = 0;
                    Iterator<AbstractC1038dw.a<?, ?>> it = f1952rH.values().iterator();
                    while (true) {
                        int i = iHashCode;
                        if (!it.hasNext()) {
                            return i;
                        }
                        AbstractC1038dw.a<?, ?> next = it.next();
                        if (mo1646a(next)) {
                            iHashCode = mo1647b(next).hashCode() + i + next.m1669bw();
                        } else {
                            iHashCode = i;
                        }
                    }
                }

                @Override // com.google.android.gms.common.data.Freezable
                public boolean isDataValid() {
                    return true;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C1087fr c1087fr = CREATOR;
                    C1087fr.m2014a(this, out, flags);
                }
            }
