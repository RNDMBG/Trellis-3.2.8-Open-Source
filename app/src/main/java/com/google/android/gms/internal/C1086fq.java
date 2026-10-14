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
            
            
            
            public final class C1086fq extends AbstractC1038dw implements SafeParcelable, ItemScope {
                public static final C1087fr CREATOR = new C1087fr();

                
                private static final HashMap<String, AbstractC1038dw.a<?, ?>> f1952rH = new HashMap<>();

                
                private String f1953hN;

                
                private final int f1954iM;
                private String mName;

                
                private String f1955mo;

                
                private double f1956oE;

                
                private double f1957oF;

                
                private final Set<Integer> f1958rI;

                
                private C1086fq f1959rJ;

                
                private List<String> f1960rK;

                
                private C1086fq f1961rL;

                
                private String f1962rM;

                
                private String f1963rN;

                
                private String f1964rO;

                
                private List<C1086fq> f1965rP;

                
                private int f1966rQ;

                
                private List<C1086fq> f1967rR;

                
                private C1086fq f1968rS;

                
                private List<C1086fq> f1969rT;

                
                private String f1970rU;

                
                private String f1971rV;

                
                private C1086fq f1972rW;

                
                private String f1973rX;

                
                private String f1974rY;

                
                private String f1975rZ;

                
                private C1086fq f1976sA;

                
                private String f1977sB;

                
                private String f1978sC;

                
                private String f1979sD;

                
                private String f1980sE;

                
                private String f1981sF;

                
                private List<C1086fq> f1982sa;

                
                private String f1983sb;

                
                private String f1984sc;

                
                private String f1985sd;

                
                private String f1986se;

                
                private String f1987sf;

                
                private String f1988sg;

                
                private String f1989sh;

                
                private String f1990si;

                
                private C1086fq f1991sj;

                
                private String f1992sk;

                
                private String f1993sl;

                
                private String f1994sm;

                
                private String f1995sn;

                
                private C1086fq f1996so;

                
                private C1086fq f1997sp;

                
                private C1086fq f1998sq;

                
                private List<C1086fq> f1999sr;

                
                private String f2000ss;

                
                private String f2001st;

                
                private String f2002su;

                
                private String f2003sv;

                
                private C1086fq f2004sw;

                
                private String f2005sx;

                
                private String f2006sy;

                
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

                @Override 
                
                protected Object mo1641D(String str) {
                    return null;
                }

                @Override 
                
                protected boolean mo1642E(String str) {
                    return false;
                }

                @Override 
                
                protected boolean mo1646a(AbstractC1038dw.a aVar) {
                    return this.f1958rI.contains(Integer.valueOf(aVar.m1669bw()));
                }

                @Override 
                
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

                @Override 
                
                public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                    return f1952rH;
                }

                @Override 
                public int describeContents() {
                    C1087fr c1087fr = CREATOR;
                    return 0;
                }

                
                Set<Integer> m1997di() {
                    return this.f1958rI;
                }

                
                C1086fq m1998dj() {
                    return this.f1959rJ;
                }

                
                C1086fq m1999dk() {
                    return this.f1961rL;
                }

                
                List<C1086fq> m2000dl() {
                    return this.f1965rP;
                }

                
                List<C1086fq> m2001dm() {
                    return this.f1967rR;
                }

                
                C1086fq m2002dn() {
                    return this.f1968rS;
                }

                
                List<C1086fq> m2003do() {
                    return this.f1969rT;
                }

                
                C1086fq m2004dp() {
                    return this.f1972rW;
                }

                
                List<C1086fq> m2005dq() {
                    return this.f1982sa;
                }

                
                C1086fq m2006dr() {
                    return this.f1991sj;
                }

                
                C1086fq m2007ds() {
                    return this.f1996so;
                }

                
                C1086fq m2008dt() {
                    return this.f1997sp;
                }

                
                C1086fq m2009du() {
                    return this.f1998sq;
                }

                
                List<C1086fq> m2010dv() {
                    return this.f1999sr;
                }

                
                C1086fq m2011dw() {
                    return this.f2004sw;
                }

                
                C1086fq m2012dx() {
                    return this.f1976sA;
                }

                
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

                @Override 
                public  ItemScope freeze() {
                    return m2013dy();
                }

                @Override 
                public ItemScope getAbout() {
                    return this.f1959rJ;
                }

                @Override 
                public List<String> getAdditionalName() {
                    return this.f1960rK;
                }

                @Override 
                public ItemScope getAddress() {
                    return this.f1961rL;
                }

                @Override 
                public String getAddressCountry() {
                    return this.f1962rM;
                }

                @Override 
                public String getAddressLocality() {
                    return this.f1963rN;
                }

                @Override 
                public String getAddressRegion() {
                    return this.f1964rO;
                }

                @Override 
                public List<ItemScope> getAssociated_media() {
                    return (ArrayList) this.f1965rP;
                }

                @Override 
                public int getAttendeeCount() {
                    return this.f1966rQ;
                }

                @Override 
                public List<ItemScope> getAttendees() {
                    return (ArrayList) this.f1967rR;
                }

                @Override 
                public ItemScope getAudio() {
                    return this.f1968rS;
                }

                @Override 
                public List<ItemScope> getAuthor() {
                    return (ArrayList) this.f1969rT;
                }

                @Override 
                public String getBestRating() {
                    return this.f1970rU;
                }

                @Override 
                public String getBirthDate() {
                    return this.f1971rV;
                }

                @Override 
                public ItemScope getByArtist() {
                    return this.f1972rW;
                }

                @Override 
                public String getCaption() {
                    return this.f1973rX;
                }

                @Override 
                public String getContentSize() {
                    return this.f1974rY;
                }

                @Override 
                public String getContentUrl() {
                    return this.f1975rZ;
                }

                @Override 
                public List<ItemScope> getContributor() {
                    return (ArrayList) this.f1982sa;
                }

                @Override 
                public String getDateCreated() {
                    return this.f1983sb;
                }

                @Override 
                public String getDateModified() {
                    return this.f1984sc;
                }

                @Override 
                public String getDatePublished() {
                    return this.f1985sd;
                }

                @Override 
                public String getDescription() {
                    return this.f1955mo;
                }

                @Override 
                public String getDuration() {
                    return this.f1986se;
                }

                @Override 
                public String getEmbedUrl() {
                    return this.f1987sf;
                }

                @Override 
                public String getEndDate() {
                    return this.f1988sg;
                }

                @Override 
                public String getFamilyName() {
                    return this.f1989sh;
                }

                @Override 
                public String getGender() {
                    return this.f1990si;
                }

                @Override 
                public ItemScope getGeo() {
                    return this.f1991sj;
                }

                @Override 
                public String getGivenName() {
                    return this.f1992sk;
                }

                @Override 
                public String getHeight() {
                    return this.f1993sl;
                }

                @Override 
                public String getId() {
                    return this.f1994sm;
                }

                @Override 
                public String getImage() {
                    return this.f1995sn;
                }

                @Override 
                public ItemScope getInAlbum() {
                    return this.f1996so;
                }

                @Override 
                public double getLatitude() {
                    return this.f1956oE;
                }

                @Override 
                public ItemScope getLocation() {
                    return this.f1997sp;
                }

                @Override 
                public double getLongitude() {
                    return this.f1957oF;
                }

                @Override 
                public String getName() {
                    return this.mName;
                }

                @Override 
                public ItemScope getPartOfTVSeries() {
                    return this.f1998sq;
                }

                @Override 
                public List<ItemScope> getPerformers() {
                    return (ArrayList) this.f1999sr;
                }

                @Override 
                public String getPlayerType() {
                    return this.f2000ss;
                }

                @Override 
                public String getPostOfficeBoxNumber() {
                    return this.f2001st;
                }

                @Override 
                public String getPostalCode() {
                    return this.f2002su;
                }

                @Override 
                public String getRatingValue() {
                    return this.f2003sv;
                }

                @Override 
                public ItemScope getReviewRating() {
                    return this.f2004sw;
                }

                @Override 
                public String getStartDate() {
                    return this.f2005sx;
                }

                @Override 
                public String getStreetAddress() {
                    return this.f2006sy;
                }

                @Override 
                public String getText() {
                    return this.f2007sz;
                }

                @Override 
                public ItemScope getThumbnail() {
                    return this.f1976sA;
                }

                @Override 
                public String getThumbnailUrl() {
                    return this.f1977sB;
                }

                @Override 
                public String getTickerSymbol() {
                    return this.f1978sC;
                }

                @Override 
                public String getType() {
                    return this.f1979sD;
                }

                @Override 
                public String getUrl() {
                    return this.f1953hN;
                }

                int getVersionCode() {
                    return this.f1954iM;
                }

                @Override 
                public String getWidth() {
                    return this.f1980sE;
                }

                @Override 
                public String getWorstRating() {
                    return this.f1981sF;
                }

                @Override 
                public boolean hasAbout() {
                    return this.f1958rI.contains(2);
                }

                @Override 
                public boolean hasAdditionalName() {
                    return this.f1958rI.contains(3);
                }

                @Override 
                public boolean hasAddress() {
                    return this.f1958rI.contains(4);
                }

                @Override 
                public boolean hasAddressCountry() {
                    return this.f1958rI.contains(5);
                }

                @Override 
                public boolean hasAddressLocality() {
                    return this.f1958rI.contains(6);
                }

                @Override 
                public boolean hasAddressRegion() {
                    return this.f1958rI.contains(7);
                }

                @Override 
                public boolean hasAssociated_media() {
                    return this.f1958rI.contains(8);
                }

                @Override 
                public boolean hasAttendeeCount() {
                    return this.f1958rI.contains(9);
                }

                @Override 
                public boolean hasAttendees() {
                    return this.f1958rI.contains(10);
                }

                @Override 
                public boolean hasAudio() {
                    return this.f1958rI.contains(11);
                }

                @Override 
                public boolean hasAuthor() {
                    return this.f1958rI.contains(12);
                }

                @Override 
                public boolean hasBestRating() {
                    return this.f1958rI.contains(13);
                }

                @Override 
                public boolean hasBirthDate() {
                    return this.f1958rI.contains(14);
                }

                @Override 
                public boolean hasByArtist() {
                    return this.f1958rI.contains(15);
                }

                @Override 
                public boolean hasCaption() {
                    return this.f1958rI.contains(16);
                }

                @Override 
                public boolean hasContentSize() {
                    return this.f1958rI.contains(17);
                }

                @Override 
                public boolean hasContentUrl() {
                    return this.f1958rI.contains(18);
                }

                @Override 
                public boolean hasContributor() {
                    return this.f1958rI.contains(19);
                }

                @Override 
                public boolean hasDateCreated() {
                    return this.f1958rI.contains(20);
                }

                @Override 
                public boolean hasDateModified() {
                    return this.f1958rI.contains(21);
                }

                @Override 
                public boolean hasDatePublished() {
                    return this.f1958rI.contains(22);
                }

                @Override 
                public boolean hasDescription() {
                    return this.f1958rI.contains(23);
                }

                @Override 
                public boolean hasDuration() {
                    return this.f1958rI.contains(24);
                }

                @Override 
                public boolean hasEmbedUrl() {
                    return this.f1958rI.contains(25);
                }

                @Override 
                public boolean hasEndDate() {
                    return this.f1958rI.contains(26);
                }

                @Override 
                public boolean hasFamilyName() {
                    return this.f1958rI.contains(27);
                }

                @Override 
                public boolean hasGender() {
                    return this.f1958rI.contains(28);
                }

                @Override 
                public boolean hasGeo() {
                    return this.f1958rI.contains(29);
                }

                @Override 
                public boolean hasGivenName() {
                    return this.f1958rI.contains(30);
                }

                @Override 
                public boolean hasHeight() {
                    return this.f1958rI.contains(31);
                }

                @Override 
                public boolean hasId() {
                    return this.f1958rI.contains(32);
                }

                @Override 
                public boolean hasImage() {
                    return this.f1958rI.contains(33);
                }

                @Override 
                public boolean hasInAlbum() {
                    return this.f1958rI.contains(34);
                }

                @Override 
                public boolean hasLatitude() {
                    return this.f1958rI.contains(36);
                }

                @Override 
                public boolean hasLocation() {
                    return this.f1958rI.contains(37);
                }

                @Override 
                public boolean hasLongitude() {
                    return this.f1958rI.contains(38);
                }

                @Override 
                public boolean hasName() {
                    return this.f1958rI.contains(39);
                }

                @Override 
                public boolean hasPartOfTVSeries() {
                    return this.f1958rI.contains(40);
                }

                @Override 
                public boolean hasPerformers() {
                    return this.f1958rI.contains(41);
                }

                @Override 
                public boolean hasPlayerType() {
                    return this.f1958rI.contains(42);
                }

                @Override 
                public boolean hasPostOfficeBoxNumber() {
                    return this.f1958rI.contains(43);
                }

                @Override 
                public boolean hasPostalCode() {
                    return this.f1958rI.contains(44);
                }

                @Override 
                public boolean hasRatingValue() {
                    return this.f1958rI.contains(45);
                }

                @Override 
                public boolean hasReviewRating() {
                    return this.f1958rI.contains(46);
                }

                @Override 
                public boolean hasStartDate() {
                    return this.f1958rI.contains(47);
                }

                @Override 
                public boolean hasStreetAddress() {
                    return this.f1958rI.contains(48);
                }

                @Override 
                public boolean hasText() {
                    return this.f1958rI.contains(49);
                }

                @Override 
                public boolean hasThumbnail() {
                    return this.f1958rI.contains(50);
                }

                @Override 
                public boolean hasThumbnailUrl() {
                    return this.f1958rI.contains(51);
                }

                @Override 
                public boolean hasTickerSymbol() {
                    return this.f1958rI.contains(52);
                }

                @Override 
                public boolean hasType() {
                    return this.f1958rI.contains(53);
                }

                @Override 
                public boolean hasUrl() {
                    return this.f1958rI.contains(54);
                }

                @Override 
                public boolean hasWidth() {
                    return this.f1958rI.contains(55);
                }

                @Override 
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

                @Override 
                public boolean isDataValid() {
                    return true;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    C1087fr c1087fr = CREATOR;
                    C1087fr.m2014a(this, out, flags);
                }
            }
