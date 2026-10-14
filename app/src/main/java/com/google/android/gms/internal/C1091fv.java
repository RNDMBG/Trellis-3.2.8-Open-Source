            package com.google.android.gms.internal;

            import android.os.Parcel;
            import co.vine.android.provider.Vine;
            import co.vine.android.util.CommonUtil;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.plus.PlusShare;
            import com.google.android.gms.plus.model.people.Person;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Set;
            
            
            
            public final class C1091fv extends AbstractC1038dw implements SafeParcelable, Person {
                public static final C1092fw CREATOR = new C1092fw();

                
                private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2017rH = new HashMap<>();

                
                private int f2018dI;

                
                private String f2019hN;

                
                private final int f2020iM;

                
                private String f2021ml;

                
                private final Set<Integer> f2022rI;

                
                private String f2023sJ;

                
                private a f2024sK;

                
                private String f2025sL;

                
                private String f2026sM;

                
                private int f2027sN;

                
                private b f2028sO;

                
                private String f2029sP;

                
                private c f2030sQ;

                
                private boolean f2031sR;

                
                private String f2032sS;

                
                private d f2033sT;

                
                private String f2034sU;

                
                private int f2035sV;

                
                private List<f> f2036sW;

                
                private List<g> f2037sX;

                
                private int f2038sY;

                
                private int f2039sZ;

                
                private String f2040sm;

                
                private String f2041ta;

                
                private List<h> f2042tb;

                
                private boolean f2043tc;

                
                public static final class a extends AbstractC1038dw implements SafeParcelable, Person.AgeRange {
                    public static final C1093fx CREATOR = new C1093fx();

                    
                    private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2044rH = new HashMap<>();

                    
                    private final int f2045iM;

                    
                    private final Set<Integer> f2046rI;

                    
                    private int f2047td;

                    
                    private int f2048te;

                    static {
                        f2044rH.put("max", AbstractC1038dw.a.m1655d("max", 2));
                        f2044rH.put("min", AbstractC1038dw.a.m1655d("min", 3));
                    }

                    public a() {
                        this.f2045iM = 1;
                        this.f2046rI = new HashSet();
                    }

                    a(Set<Integer> set, int i, int i2, int i3) {
                        this.f2046rI = set;
                        this.f2045iM = i;
                        this.f2047td = i2;
                        this.f2048te = i3;
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
                        return this.f2046rI.contains(Integer.valueOf(aVar.m1669bw()));
                    }

                    @Override 
                    
                    protected Object mo1647b(AbstractC1038dw.a aVar) {
                        switch (aVar.m1669bw()) {
                            case 2:
                                return Integer.valueOf(this.f2047td);
                            case 3:
                                return Integer.valueOf(this.f2048te);
                            default:
                                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                        }
                    }

                    @Override 
                    
                    public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                        return f2044rH;
                    }

                    
                    public a m2036dL() {
                        return this;
                    }

                    @Override 
                    public int describeContents() {
                        C1093fx c1093fx = CREATOR;
                        return 0;
                    }

                    
                    Set<Integer> m2037di() {
                        return this.f2046rI;
                    }

                    public boolean equals(Object obj) {
                        if (!(obj instanceof a)) {
                            return false;
                        }
                        if (this == obj) {
                            return true;
                        }
                        a aVar = (a) obj;
                        for (AbstractC1038dw.a<?, ?> aVar2 : f2044rH.values()) {
                            if (mo1646a(aVar2)) {
                                if (aVar.mo1646a(aVar2) && mo1647b(aVar2).equals(aVar.mo1647b(aVar2))) {
                                }
                                return false;
                            }
                            if (aVar.mo1646a(aVar2)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override 
                    public  Person.AgeRange freeze() {
                        return m2036dL();
                    }

                    @Override 
                    public int getMax() {
                        return this.f2047td;
                    }

                    @Override 
                    public int getMin() {
                        return this.f2048te;
                    }

                    int getVersionCode() {
                        return this.f2045iM;
                    }

                    @Override 
                    public boolean hasMax() {
                        return this.f2046rI.contains(2);
                    }

                    @Override 
                    public boolean hasMin() {
                        return this.f2046rI.contains(3);
                    }

                    public int hashCode() {
                        int iHashCode = 0;
                        Iterator<AbstractC1038dw.a<?, ?>> it = f2044rH.values().iterator();
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
                        C1093fx c1093fx = CREATOR;
                        C1093fx.m2061a(this, out, flags);
                    }
                }

                
                public static final class b extends AbstractC1038dw implements SafeParcelable, Person.Cover {
                    public static final C1094fy CREATOR = new C1094fy();

                    
                    private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2049rH = new HashMap<>();

                    
                    private final int f2050iM;

                    
                    private final Set<Integer> f2051rI;

                    
                    private a f2052tf;

                    
                    private C1343b f2053tg;

                    
                    private int f2054th;

                    
                    public static final class a extends AbstractC1038dw implements SafeParcelable, Person.Cover.CoverInfo {
                        public static final C1095fz CREATOR = new C1095fz();

                        
                        private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2055rH = new HashMap<>();

                        
                        private final int f2056iM;

                        
                        private final Set<Integer> f2057rI;

                        
                        private int f2058ti;

                        
                        private int f2059tj;

                        static {
                            f2055rH.put("leftImageOffset", AbstractC1038dw.a.m1655d("leftImageOffset", 2));
                            f2055rH.put("topImageOffset", AbstractC1038dw.a.m1655d("topImageOffset", 3));
                        }

                        public a() {
                            this.f2056iM = 1;
                            this.f2057rI = new HashSet();
                        }

                        a(Set<Integer> set, int i, int i2, int i3) {
                            this.f2057rI = set;
                            this.f2056iM = i;
                            this.f2058ti = i2;
                            this.f2059tj = i3;
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
                            return this.f2057rI.contains(Integer.valueOf(aVar.m1669bw()));
                        }

                        @Override 
                        
                        protected Object mo1647b(AbstractC1038dw.a aVar) {
                            switch (aVar.m1669bw()) {
                                case 2:
                                    return Integer.valueOf(this.f2058ti);
                                case 3:
                                    return Integer.valueOf(this.f2059tj);
                                default:
                                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                            }
                        }

                        @Override 
                        
                        public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                            return f2055rH;
                        }

                        
                        public a m2042dP() {
                            return this;
                        }

                        @Override 
                        public int describeContents() {
                            C1095fz c1095fz = CREATOR;
                            return 0;
                        }

                        
                        Set<Integer> m2043di() {
                            return this.f2057rI;
                        }

                        public boolean equals(Object obj) {
                            if (!(obj instanceof a)) {
                                return false;
                            }
                            if (this == obj) {
                                return true;
                            }
                            a aVar = (a) obj;
                            for (AbstractC1038dw.a<?, ?> aVar2 : f2055rH.values()) {
                                if (mo1646a(aVar2)) {
                                    if (aVar.mo1646a(aVar2) && mo1647b(aVar2).equals(aVar.mo1647b(aVar2))) {
                                    }
                                    return false;
                                }
                                if (aVar.mo1646a(aVar2)) {
                                    return false;
                                }
                            }
                            return true;
                        }

                        @Override 
                        public  Person.Cover.CoverInfo freeze() {
                            return m2042dP();
                        }

                        @Override 
                        public int getLeftImageOffset() {
                            return this.f2058ti;
                        }

                        @Override 
                        public int getTopImageOffset() {
                            return this.f2059tj;
                        }

                        int getVersionCode() {
                            return this.f2056iM;
                        }

                        @Override 
                        public boolean hasLeftImageOffset() {
                            return this.f2057rI.contains(2);
                        }

                        @Override 
                        public boolean hasTopImageOffset() {
                            return this.f2057rI.contains(3);
                        }

                        public int hashCode() {
                            int iHashCode = 0;
                            Iterator<AbstractC1038dw.a<?, ?>> it = f2055rH.values().iterator();
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
                            C1095fz c1095fz = CREATOR;
                            C1095fz.m2067a(this, out, flags);
                        }
                    }

                    
                    public static final class C1343b extends AbstractC1038dw implements SafeParcelable, Person.Cover.CoverPhoto {
                        public static final C1097ga CREATOR = new C1097ga();

                        
                        private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2060rH = new HashMap<>();

                        
                        private int f2061dP;

                        
                        private int f2062dQ;

                        
                        private String f2063hN;

                        
                        private final int f2064iM;

                        
                        private final Set<Integer> f2065rI;

                        static {
                            f2060rH.put("height", AbstractC1038dw.a.m1655d("height", 2));
                            f2060rH.put(PlusShare.KEY_CALL_TO_ACTION_URL, AbstractC1038dw.a.m1658g(PlusShare.KEY_CALL_TO_ACTION_URL, 3));
                            f2060rH.put("width", AbstractC1038dw.a.m1655d("width", 4));
                        }

                        public C1343b() {
                            this.f2064iM = 1;
                            this.f2065rI = new HashSet();
                        }

                        C1343b(Set<Integer> set, int i, int i2, String str, int i3) {
                            this.f2065rI = set;
                            this.f2064iM = i;
                            this.f2062dQ = i2;
                            this.f2063hN = str;
                            this.f2061dP = i3;
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
                            return this.f2065rI.contains(Integer.valueOf(aVar.m1669bw()));
                        }

                        @Override 
                        
                        protected Object mo1647b(AbstractC1038dw.a aVar) {
                            switch (aVar.m1669bw()) {
                                case 2:
                                    return Integer.valueOf(this.f2062dQ);
                                case 3:
                                    return this.f2063hN;
                                case 4:
                                    return Integer.valueOf(this.f2061dP);
                                default:
                                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                            }
                        }

                        @Override 
                        
                        public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                            return f2060rH;
                        }

                        
                        public C1343b m2044dQ() {
                            return this;
                        }

                        @Override 
                        public int describeContents() {
                            C1097ga c1097ga = CREATOR;
                            return 0;
                        }

                        
                        Set<Integer> m2045di() {
                            return this.f2065rI;
                        }

                        public boolean equals(Object obj) {
                            if (!(obj instanceof C1343b)) {
                                return false;
                            }
                            if (this == obj) {
                                return true;
                            }
                            C1343b c1343b = (C1343b) obj;
                            for (AbstractC1038dw.a<?, ?> aVar : f2060rH.values()) {
                                if (mo1646a(aVar)) {
                                    if (c1343b.mo1646a(aVar) && mo1647b(aVar).equals(c1343b.mo1647b(aVar))) {
                                    }
                                    return false;
                                }
                                if (c1343b.mo1646a(aVar)) {
                                    return false;
                                }
                            }
                            return true;
                        }

                        @Override 
                        public  Person.Cover.CoverPhoto freeze() {
                            return m2044dQ();
                        }

                        @Override 
                        public int getHeight() {
                            return this.f2062dQ;
                        }

                        @Override 
                        public String getUrl() {
                            return this.f2063hN;
                        }

                        int getVersionCode() {
                            return this.f2064iM;
                        }

                        @Override 
                        public int getWidth() {
                            return this.f2061dP;
                        }

                        @Override 
                        public boolean hasHeight() {
                            return this.f2065rI.contains(2);
                        }

                        @Override 
                        public boolean hasUrl() {
                            return this.f2065rI.contains(3);
                        }

                        @Override 
                        public boolean hasWidth() {
                            return this.f2065rI.contains(4);
                        }

                        public int hashCode() {
                            int iHashCode = 0;
                            Iterator<AbstractC1038dw.a<?, ?>> it = f2060rH.values().iterator();
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
                            C1097ga c1097ga = CREATOR;
                            C1097ga.m2072a(this, out, flags);
                        }
                    }

                    static {
                        f2049rH.put("coverInfo", AbstractC1038dw.a.m1652a("coverInfo", 2, a.class));
                        f2049rH.put("coverPhoto", AbstractC1038dw.a.m1652a("coverPhoto", 3, C1343b.class));
                        f2049rH.put("layout", AbstractC1038dw.a.m1651a("layout", 4, new C1035dt().m1631c("banner", 0), false));
                    }

                    public b() {
                        this.f2050iM = 1;
                        this.f2051rI = new HashSet();
                    }

                    b(Set<Integer> set, int i, a aVar, C1343b c1343b, int i2) {
                        this.f2051rI = set;
                        this.f2050iM = i;
                        this.f2052tf = aVar;
                        this.f2053tg = c1343b;
                        this.f2054th = i2;
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
                        return this.f2051rI.contains(Integer.valueOf(aVar.m1669bw()));
                    }

                    @Override 
                    
                    protected Object mo1647b(AbstractC1038dw.a aVar) {
                        switch (aVar.m1669bw()) {
                            case 2:
                                return this.f2052tf;
                            case 3:
                                return this.f2053tg;
                            case 4:
                                return Integer.valueOf(this.f2054th);
                            default:
                                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                        }
                    }

                    @Override 
                    
                    public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                        return f2049rH;
                    }

                    
                    a m2038dM() {
                        return this.f2052tf;
                    }

                    
                    C1343b m2039dN() {
                        return this.f2053tg;
                    }

                    
                    public b m2040dO() {
                        return this;
                    }

                    @Override 
                    public int describeContents() {
                        C1094fy c1094fy = CREATOR;
                        return 0;
                    }

                    
                    Set<Integer> m2041di() {
                        return this.f2051rI;
                    }

                    public boolean equals(Object obj) {
                        if (!(obj instanceof b)) {
                            return false;
                        }
                        if (this == obj) {
                            return true;
                        }
                        b bVar = (b) obj;
                        for (AbstractC1038dw.a<?, ?> aVar : f2049rH.values()) {
                            if (mo1646a(aVar)) {
                                if (bVar.mo1646a(aVar) && mo1647b(aVar).equals(bVar.mo1647b(aVar))) {
                                }
                                return false;
                            }
                            if (bVar.mo1646a(aVar)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override 
                    public  Person.Cover freeze() {
                        return m2040dO();
                    }

                    @Override 
                    public Person.Cover.CoverInfo getCoverInfo() {
                        return this.f2052tf;
                    }

                    @Override 
                    public Person.Cover.CoverPhoto getCoverPhoto() {
                        return this.f2053tg;
                    }

                    @Override 
                    public int getLayout() {
                        return this.f2054th;
                    }

                    int getVersionCode() {
                        return this.f2050iM;
                    }

                    @Override 
                    public boolean hasCoverInfo() {
                        return this.f2051rI.contains(2);
                    }

                    @Override 
                    public boolean hasCoverPhoto() {
                        return this.f2051rI.contains(3);
                    }

                    @Override 
                    public boolean hasLayout() {
                        return this.f2051rI.contains(4);
                    }

                    public int hashCode() {
                        int iHashCode = 0;
                        Iterator<AbstractC1038dw.a<?, ?>> it = f2049rH.values().iterator();
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
                        C1094fy c1094fy = CREATOR;
                        C1094fy.m2064a(this, out, flags);
                    }
                }

                
                public static final class c extends AbstractC1038dw implements SafeParcelable, Person.Image {
                    public static final C1098gb CREATOR = new C1098gb();

                    
                    private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2066rH = new HashMap<>();

                    
                    private String f2067hN;

                    
                    private final int f2068iM;

                    
                    private final Set<Integer> f2069rI;

                    static {
                        f2066rH.put(PlusShare.KEY_CALL_TO_ACTION_URL, AbstractC1038dw.a.m1658g(PlusShare.KEY_CALL_TO_ACTION_URL, 2));
                    }

                    public c() {
                        this.f2068iM = 1;
                        this.f2069rI = new HashSet();
                    }

                    public c(String str) {
                        this.f2069rI = new HashSet();
                        this.f2068iM = 1;
                        this.f2067hN = str;
                        this.f2069rI.add(2);
                    }

                    c(Set<Integer> set, int i, String str) {
                        this.f2069rI = set;
                        this.f2068iM = i;
                        this.f2067hN = str;
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
                        return this.f2069rI.contains(Integer.valueOf(aVar.m1669bw()));
                    }

                    @Override 
                    
                    protected Object mo1647b(AbstractC1038dw.a aVar) {
                        switch (aVar.m1669bw()) {
                            case 2:
                                return this.f2067hN;
                            default:
                                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                        }
                    }

                    @Override 
                    
                    public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                        return f2066rH;
                    }

                    
                    public c m2046dR() {
                        return this;
                    }

                    @Override 
                    public int describeContents() {
                        C1098gb c1098gb = CREATOR;
                        return 0;
                    }

                    
                    Set<Integer> m2047di() {
                        return this.f2069rI;
                    }

                    public boolean equals(Object obj) {
                        if (!(obj instanceof c)) {
                            return false;
                        }
                        if (this == obj) {
                            return true;
                        }
                        c cVar = (c) obj;
                        for (AbstractC1038dw.a<?, ?> aVar : f2066rH.values()) {
                            if (mo1646a(aVar)) {
                                if (cVar.mo1646a(aVar) && mo1647b(aVar).equals(cVar.mo1647b(aVar))) {
                                }
                                return false;
                            }
                            if (cVar.mo1646a(aVar)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override 
                    public  Person.Image freeze() {
                        return m2046dR();
                    }

                    @Override 
                    public String getUrl() {
                        return this.f2067hN;
                    }

                    int getVersionCode() {
                        return this.f2068iM;
                    }

                    @Override 
                    public boolean hasUrl() {
                        return this.f2069rI.contains(2);
                    }

                    public int hashCode() {
                        int iHashCode = 0;
                        Iterator<AbstractC1038dw.a<?, ?>> it = f2066rH.values().iterator();
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
                        C1098gb c1098gb = CREATOR;
                        C1098gb.m2075a(this, out, flags);
                    }
                }

                
                public static final class d extends AbstractC1038dw implements SafeParcelable, Person.Name {
                    public static final C1099gc CREATOR = new C1099gc();

                    
                    private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2070rH = new HashMap<>();

                    
                    private final int f2071iM;

                    
                    private final Set<Integer> f2072rI;

                    
                    private String f2073sh;

                    
                    private String f2074sk;

                    
                    private String f2075tk;

                    
                    private String f2076tl;

                    
                    private String f2077tm;

                    
                    private String f2078tn;

                    static {
                        f2070rH.put("familyName", AbstractC1038dw.a.m1658g("familyName", 2));
                        f2070rH.put("formatted", AbstractC1038dw.a.m1658g("formatted", 3));
                        f2070rH.put("givenName", AbstractC1038dw.a.m1658g("givenName", 4));
                        f2070rH.put("honorificPrefix", AbstractC1038dw.a.m1658g("honorificPrefix", 5));
                        f2070rH.put("honorificSuffix", AbstractC1038dw.a.m1658g("honorificSuffix", 6));
                        f2070rH.put("middleName", AbstractC1038dw.a.m1658g("middleName", 7));
                    }

                    public d() {
                        this.f2071iM = 1;
                        this.f2072rI = new HashSet();
                    }

                    d(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, String str6) {
                        this.f2072rI = set;
                        this.f2071iM = i;
                        this.f2073sh = str;
                        this.f2075tk = str2;
                        this.f2074sk = str3;
                        this.f2076tl = str4;
                        this.f2077tm = str5;
                        this.f2078tn = str6;
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
                        return this.f2072rI.contains(Integer.valueOf(aVar.m1669bw()));
                    }

                    @Override 
                    
                    protected Object mo1647b(AbstractC1038dw.a aVar) {
                        switch (aVar.m1669bw()) {
                            case 2:
                                return this.f2073sh;
                            case 3:
                                return this.f2075tk;
                            case 4:
                                return this.f2074sk;
                            case 5:
                                return this.f2076tl;
                            case 6:
                                return this.f2077tm;
                            case 7:
                                return this.f2078tn;
                            default:
                                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                        }
                    }

                    @Override 
                    
                    public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                        return f2070rH;
                    }

                    
                    public d m2048dS() {
                        return this;
                    }

                    @Override 
                    public int describeContents() {
                        C1099gc c1099gc = CREATOR;
                        return 0;
                    }

                    
                    Set<Integer> m2049di() {
                        return this.f2072rI;
                    }

                    public boolean equals(Object obj) {
                        if (!(obj instanceof d)) {
                            return false;
                        }
                        if (this == obj) {
                            return true;
                        }
                        d dVar = (d) obj;
                        for (AbstractC1038dw.a<?, ?> aVar : f2070rH.values()) {
                            if (mo1646a(aVar)) {
                                if (dVar.mo1646a(aVar) && mo1647b(aVar).equals(dVar.mo1647b(aVar))) {
                                }
                                return false;
                            }
                            if (dVar.mo1646a(aVar)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override 
                    public  Person.Name freeze() {
                        return m2048dS();
                    }

                    @Override 
                    public String getFamilyName() {
                        return this.f2073sh;
                    }

                    @Override 
                    public String getFormatted() {
                        return this.f2075tk;
                    }

                    @Override 
                    public String getGivenName() {
                        return this.f2074sk;
                    }

                    @Override 
                    public String getHonorificPrefix() {
                        return this.f2076tl;
                    }

                    @Override 
                    public String getHonorificSuffix() {
                        return this.f2077tm;
                    }

                    @Override 
                    public String getMiddleName() {
                        return this.f2078tn;
                    }

                    int getVersionCode() {
                        return this.f2071iM;
                    }

                    @Override 
                    public boolean hasFamilyName() {
                        return this.f2072rI.contains(2);
                    }

                    @Override 
                    public boolean hasFormatted() {
                        return this.f2072rI.contains(3);
                    }

                    @Override 
                    public boolean hasGivenName() {
                        return this.f2072rI.contains(4);
                    }

                    @Override 
                    public boolean hasHonorificPrefix() {
                        return this.f2072rI.contains(5);
                    }

                    @Override 
                    public boolean hasHonorificSuffix() {
                        return this.f2072rI.contains(6);
                    }

                    @Override 
                    public boolean hasMiddleName() {
                        return this.f2072rI.contains(7);
                    }

                    public int hashCode() {
                        int iHashCode = 0;
                        Iterator<AbstractC1038dw.a<?, ?>> it = f2070rH.values().iterator();
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
                        C1099gc c1099gc = CREATOR;
                        C1099gc.m2078a(this, out, flags);
                    }
                }

                
                public static class e {
                    
                    public static int m2050aa(String str) {
                        if (str.equals("person")) {
                            return 0;
                        }
                        if (str.equals("page")) {
                            return 1;
                        }
                        throw new IllegalArgumentException("Unknown objectType string: " + str);
                    }
                }

                
                public static final class f extends AbstractC1038dw implements SafeParcelable, Person.Organizations {
                    public static final C1100gd CREATOR = new C1100gd();

                    
                    private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2079rH = new HashMap<>();

                    
                    private final int f2080iM;

                    
                    private int f2081jV;
                    private String mName;

                    
                    private String f2082mo;

                    
                    private String f2083qB;

                    
                    private final Set<Integer> f2084rI;

                    
                    private String f2085sg;

                    
                    private String f2086sx;

                    
                    private String f2087to;

                    
                    private String f2088tp;

                    
                    private boolean f2089tq;

                    static {
                        f2079rH.put("department", AbstractC1038dw.a.m1658g("department", 2));
                        f2079rH.put("description", AbstractC1038dw.a.m1658g("description", 3));
                        f2079rH.put("endDate", AbstractC1038dw.a.m1658g("endDate", 4));
                        f2079rH.put("location", AbstractC1038dw.a.m1658g("location", 5));
                        f2079rH.put("name", AbstractC1038dw.a.m1658g("name", 6));
                        f2079rH.put("primary", AbstractC1038dw.a.m1657f("primary", 7));
                        f2079rH.put("startDate", AbstractC1038dw.a.m1658g("startDate", 8));
                        f2079rH.put("title", AbstractC1038dw.a.m1658g("title", 9));
                        f2079rH.put("type", AbstractC1038dw.a.m1651a("type", 10, new C1035dt().m1631c(CommonUtil.FIELD_TYPE_WORK, 0).m1631c("school", 1), false));
                    }

                    public f() {
                        this.f2080iM = 1;
                        this.f2084rI = new HashSet();
                    }

                    f(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, boolean z, String str6, String str7, int i2) {
                        this.f2084rI = set;
                        this.f2080iM = i;
                        this.f2087to = str;
                        this.f2082mo = str2;
                        this.f2085sg = str3;
                        this.f2088tp = str4;
                        this.mName = str5;
                        this.f2089tq = z;
                        this.f2086sx = str6;
                        this.f2083qB = str7;
                        this.f2081jV = i2;
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
                        return this.f2084rI.contains(Integer.valueOf(aVar.m1669bw()));
                    }

                    @Override 
                    
                    protected Object mo1647b(AbstractC1038dw.a aVar) {
                        switch (aVar.m1669bw()) {
                            case 2:
                                return this.f2087to;
                            case 3:
                                return this.f2082mo;
                            case 4:
                                return this.f2085sg;
                            case 5:
                                return this.f2088tp;
                            case 6:
                                return this.mName;
                            case 7:
                                return Boolean.valueOf(this.f2089tq);
                            case 8:
                                return this.f2086sx;
                            case 9:
                                return this.f2083qB;
                            case 10:
                                return Integer.valueOf(this.f2081jV);
                            default:
                                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                        }
                    }

                    @Override 
                    
                    public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                        return f2079rH;
                    }

                    
                    public f m2051dT() {
                        return this;
                    }

                    @Override 
                    public int describeContents() {
                        C1100gd c1100gd = CREATOR;
                        return 0;
                    }

                    
                    Set<Integer> m2052di() {
                        return this.f2084rI;
                    }

                    public boolean equals(Object obj) {
                        if (!(obj instanceof f)) {
                            return false;
                        }
                        if (this == obj) {
                            return true;
                        }
                        f fVar = (f) obj;
                        for (AbstractC1038dw.a<?, ?> aVar : f2079rH.values()) {
                            if (mo1646a(aVar)) {
                                if (fVar.mo1646a(aVar) && mo1647b(aVar).equals(fVar.mo1647b(aVar))) {
                                }
                                return false;
                            }
                            if (fVar.mo1646a(aVar)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override 
                    public  Person.Organizations freeze() {
                        return m2051dT();
                    }

                    @Override 
                    public String getDepartment() {
                        return this.f2087to;
                    }

                    @Override 
                    public String getDescription() {
                        return this.f2082mo;
                    }

                    @Override 
                    public String getEndDate() {
                        return this.f2085sg;
                    }

                    @Override 
                    public String getLocation() {
                        return this.f2088tp;
                    }

                    @Override 
                    public String getName() {
                        return this.mName;
                    }

                    @Override 
                    public String getStartDate() {
                        return this.f2086sx;
                    }

                    @Override 
                    public String getTitle() {
                        return this.f2083qB;
                    }

                    @Override 
                    public int getType() {
                        return this.f2081jV;
                    }

                    int getVersionCode() {
                        return this.f2080iM;
                    }

                    @Override 
                    public boolean hasDepartment() {
                        return this.f2084rI.contains(2);
                    }

                    @Override 
                    public boolean hasDescription() {
                        return this.f2084rI.contains(3);
                    }

                    @Override 
                    public boolean hasEndDate() {
                        return this.f2084rI.contains(4);
                    }

                    @Override 
                    public boolean hasLocation() {
                        return this.f2084rI.contains(5);
                    }

                    @Override 
                    public boolean hasName() {
                        return this.f2084rI.contains(6);
                    }

                    @Override 
                    public boolean hasPrimary() {
                        return this.f2084rI.contains(7);
                    }

                    @Override 
                    public boolean hasStartDate() {
                        return this.f2084rI.contains(8);
                    }

                    @Override 
                    public boolean hasTitle() {
                        return this.f2084rI.contains(9);
                    }

                    @Override 
                    public boolean hasType() {
                        return this.f2084rI.contains(10);
                    }

                    public int hashCode() {
                        int iHashCode = 0;
                        Iterator<AbstractC1038dw.a<?, ?>> it = f2079rH.values().iterator();
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
                    public boolean isPrimary() {
                        return this.f2089tq;
                    }

                    @Override 
                    public void writeToParcel(Parcel out, int flags) {
                        C1100gd c1100gd = CREATOR;
                        C1100gd.m2081a(this, out, flags);
                    }
                }

                
                public static final class g extends AbstractC1038dw implements SafeParcelable, Person.PlacesLived {
                    public static final C1101ge CREATOR = new C1101ge();

                    
                    private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2090rH = new HashMap<>();

                    
                    private final int f2091iM;
                    private String mValue;

                    
                    private final Set<Integer> f2092rI;

                    
                    private boolean f2093tq;

                    static {
                        f2090rH.put("primary", AbstractC1038dw.a.m1657f("primary", 2));
                        f2090rH.put(Vine.Settings.PROP_VALUE, AbstractC1038dw.a.m1658g(Vine.Settings.PROP_VALUE, 3));
                    }

                    public g() {
                        this.f2091iM = 1;
                        this.f2092rI = new HashSet();
                    }

                    g(Set<Integer> set, int i, boolean z, String str) {
                        this.f2092rI = set;
                        this.f2091iM = i;
                        this.f2093tq = z;
                        this.mValue = str;
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
                        return this.f2092rI.contains(Integer.valueOf(aVar.m1669bw()));
                    }

                    @Override 
                    
                    protected Object mo1647b(AbstractC1038dw.a aVar) {
                        switch (aVar.m1669bw()) {
                            case 2:
                                return Boolean.valueOf(this.f2093tq);
                            case 3:
                                return this.mValue;
                            default:
                                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                        }
                    }

                    @Override 
                    
                    public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                        return f2090rH;
                    }

                    
                    public g m2053dU() {
                        return this;
                    }

                    @Override 
                    public int describeContents() {
                        C1101ge c1101ge = CREATOR;
                        return 0;
                    }

                    
                    Set<Integer> m2054di() {
                        return this.f2092rI;
                    }

                    public boolean equals(Object obj) {
                        if (!(obj instanceof g)) {
                            return false;
                        }
                        if (this == obj) {
                            return true;
                        }
                        g gVar = (g) obj;
                        for (AbstractC1038dw.a<?, ?> aVar : f2090rH.values()) {
                            if (mo1646a(aVar)) {
                                if (gVar.mo1646a(aVar) && mo1647b(aVar).equals(gVar.mo1647b(aVar))) {
                                }
                                return false;
                            }
                            if (gVar.mo1646a(aVar)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override 
                    public  Person.PlacesLived freeze() {
                        return m2053dU();
                    }

                    @Override 
                    public String getValue() {
                        return this.mValue;
                    }

                    int getVersionCode() {
                        return this.f2091iM;
                    }

                    @Override 
                    public boolean hasPrimary() {
                        return this.f2092rI.contains(2);
                    }

                    @Override 
                    public boolean hasValue() {
                        return this.f2092rI.contains(3);
                    }

                    public int hashCode() {
                        int iHashCode = 0;
                        Iterator<AbstractC1038dw.a<?, ?>> it = f2090rH.values().iterator();
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
                    public boolean isPrimary() {
                        return this.f2093tq;
                    }

                    @Override 
                    public void writeToParcel(Parcel out, int flags) {
                        C1101ge c1101ge = CREATOR;
                        C1101ge.m2084a(this, out, flags);
                    }
                }

                
                public static final class h extends AbstractC1038dw implements SafeParcelable, Person.Urls {
                    public static final C1102gf CREATOR = new C1102gf();

                    
                    private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2094rH = new HashMap<>();

                    
                    private final int f2095iM;

                    
                    private int f2096jV;
                    private String mValue;

                    
                    private final Set<Integer> f2097rI;

                    
                    private String f2098tr;

                    
                    private final int f2099ts;

                    static {
                        f2094rH.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, AbstractC1038dw.a.m1658g(PlusShare.KEY_CALL_TO_ACTION_LABEL, 5));
                        f2094rH.put("type", AbstractC1038dw.a.m1651a("type", 6, new C1035dt().m1631c("home", 0).m1631c(CommonUtil.FIELD_TYPE_WORK, 1).m1631c("blog", 2).m1631c("profile", 3).m1631c(CommonUtil.FIELD_TYPE_OTHER, 4).m1631c("otherProfile", 5).m1631c("contributor", 6).m1631c("website", 7), false));
                        f2094rH.put(Vine.Settings.PROP_VALUE, AbstractC1038dw.a.m1658g(Vine.Settings.PROP_VALUE, 4));
                    }

                    public h() {
                        this.f2099ts = 4;
                        this.f2095iM = 2;
                        this.f2097rI = new HashSet();
                    }

                    h(Set<Integer> set, int i, String str, int i2, String str2, int i3) {
                        this.f2099ts = 4;
                        this.f2097rI = set;
                        this.f2095iM = i;
                        this.f2098tr = str;
                        this.f2096jV = i2;
                        this.mValue = str2;
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
                        return this.f2097rI.contains(Integer.valueOf(aVar.m1669bw()));
                    }

                    @Override 
                    
                    protected Object mo1647b(AbstractC1038dw.a aVar) {
                        switch (aVar.m1669bw()) {
                            case 4:
                                return this.mValue;
                            case 5:
                                return this.f2098tr;
                            case 6:
                                return Integer.valueOf(this.f2096jV);
                            default:
                                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                        }
                    }

                    @Override 
                    
                    public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                        return f2094rH;
                    }

                    @Deprecated
                    
                    public int m2055dV() {
                        return 4;
                    }

                    
                    public h m2056dW() {
                        return this;
                    }

                    @Override 
                    public int describeContents() {
                        C1102gf c1102gf = CREATOR;
                        return 0;
                    }

                    
                    Set<Integer> m2057di() {
                        return this.f2097rI;
                    }

                    public boolean equals(Object obj) {
                        if (!(obj instanceof h)) {
                            return false;
                        }
                        if (this == obj) {
                            return true;
                        }
                        h hVar = (h) obj;
                        for (AbstractC1038dw.a<?, ?> aVar : f2094rH.values()) {
                            if (mo1646a(aVar)) {
                                if (hVar.mo1646a(aVar) && mo1647b(aVar).equals(hVar.mo1647b(aVar))) {
                                }
                                return false;
                            }
                            if (hVar.mo1646a(aVar)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override 
                    public  Person.Urls freeze() {
                        return m2056dW();
                    }

                    @Override 
                    public String getLabel() {
                        return this.f2098tr;
                    }

                    @Override 
                    public int getType() {
                        return this.f2096jV;
                    }

                    @Override 
                    public String getValue() {
                        return this.mValue;
                    }

                    int getVersionCode() {
                        return this.f2095iM;
                    }

                    @Override 
                    public boolean hasLabel() {
                        return this.f2097rI.contains(5);
                    }

                    @Override 
                    public boolean hasType() {
                        return this.f2097rI.contains(6);
                    }

                    @Override 
                    public boolean hasValue() {
                        return this.f2097rI.contains(4);
                    }

                    public int hashCode() {
                        int iHashCode = 0;
                        Iterator<AbstractC1038dw.a<?, ?>> it = f2094rH.values().iterator();
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
                        C1102gf c1102gf = CREATOR;
                        C1102gf.m2087a(this, out, flags);
                    }
                }

                static {
                    f2017rH.put("aboutMe", AbstractC1038dw.a.m1658g("aboutMe", 2));
                    f2017rH.put("ageRange", AbstractC1038dw.a.m1652a("ageRange", 3, a.class));
                    f2017rH.put("birthday", AbstractC1038dw.a.m1658g("birthday", 4));
                    f2017rH.put("braggingRights", AbstractC1038dw.a.m1658g("braggingRights", 5));
                    f2017rH.put("circledByCount", AbstractC1038dw.a.m1655d("circledByCount", 6));
                    f2017rH.put("cover", AbstractC1038dw.a.m1652a("cover", 7, b.class));
                    f2017rH.put("currentLocation", AbstractC1038dw.a.m1658g("currentLocation", 8));
                    f2017rH.put("displayName", AbstractC1038dw.a.m1658g("displayName", 9));
                    f2017rH.put("gender", AbstractC1038dw.a.m1651a("gender", 12, new C1035dt().m1631c("male", 0).m1631c("female", 1).m1631c(CommonUtil.FIELD_TYPE_OTHER, 2), false));
                    f2017rH.put("id", AbstractC1038dw.a.m1658g("id", 14));
                    f2017rH.put("image", AbstractC1038dw.a.m1652a("image", 15, c.class));
                    f2017rH.put("isPlusUser", AbstractC1038dw.a.m1657f("isPlusUser", 16));
                    f2017rH.put("language", AbstractC1038dw.a.m1658g("language", 18));
                    f2017rH.put("name", AbstractC1038dw.a.m1652a("name", 19, d.class));
                    f2017rH.put("nickname", AbstractC1038dw.a.m1658g("nickname", 20));
                    f2017rH.put("objectType", AbstractC1038dw.a.m1651a("objectType", 21, new C1035dt().m1631c("person", 0).m1631c("page", 1), false));
                    f2017rH.put("organizations", AbstractC1038dw.a.m1653b("organizations", 22, f.class));
                    f2017rH.put("placesLived", AbstractC1038dw.a.m1653b("placesLived", 23, g.class));
                    f2017rH.put("plusOneCount", AbstractC1038dw.a.m1655d("plusOneCount", 24));
                    f2017rH.put("relationshipStatus", AbstractC1038dw.a.m1651a("relationshipStatus", 25, new C1035dt().m1631c("single", 0).m1631c("in_a_relationship", 1).m1631c("engaged", 2).m1631c("married", 3).m1631c("its_complicated", 4).m1631c("open_relationship", 5).m1631c("widowed", 6).m1631c("in_domestic_partnership", 7).m1631c("in_civil_union", 8), false));
                    f2017rH.put("tagline", AbstractC1038dw.a.m1658g("tagline", 26));
                    f2017rH.put(PlusShare.KEY_CALL_TO_ACTION_URL, AbstractC1038dw.a.m1658g(PlusShare.KEY_CALL_TO_ACTION_URL, 27));
                    f2017rH.put("urls", AbstractC1038dw.a.m1653b("urls", 28, h.class));
                    f2017rH.put("verified", AbstractC1038dw.a.m1657f("verified", 29));
                }

                public C1091fv() {
                    this.f2020iM = 2;
                    this.f2022rI = new HashSet();
                }

                public C1091fv(String str, String str2, c cVar, int i, String str3) {
                    this.f2020iM = 2;
                    this.f2022rI = new HashSet();
                    this.f2021ml = str;
                    this.f2022rI.add(9);
                    this.f2040sm = str2;
                    this.f2022rI.add(14);
                    this.f2030sQ = cVar;
                    this.f2022rI.add(15);
                    this.f2035sV = i;
                    this.f2022rI.add(21);
                    this.f2019hN = str3;
                    this.f2022rI.add(27);
                }

                C1091fv(Set<Integer> set, int i, String str, a aVar, String str2, String str3, int i2, b bVar, String str4, String str5, int i3, String str6, c cVar, boolean z, String str7, d dVar, String str8, int i4, List<f> list, List<g> list2, int i5, int i6, String str9, String str10, List<h> list3, boolean z2) {
                    this.f2022rI = set;
                    this.f2020iM = i;
                    this.f2023sJ = str;
                    this.f2024sK = aVar;
                    this.f2025sL = str2;
                    this.f2026sM = str3;
                    this.f2027sN = i2;
                    this.f2028sO = bVar;
                    this.f2029sP = str4;
                    this.f2021ml = str5;
                    this.f2018dI = i3;
                    this.f2040sm = str6;
                    this.f2030sQ = cVar;
                    this.f2031sR = z;
                    this.f2032sS = str7;
                    this.f2033sT = dVar;
                    this.f2034sU = str8;
                    this.f2035sV = i4;
                    this.f2036sW = list;
                    this.f2037sX = list2;
                    this.f2038sY = i5;
                    this.f2039sZ = i6;
                    this.f2041ta = str9;
                    this.f2019hN = str10;
                    this.f2042tb = list3;
                    this.f2043tc = z2;
                }

                
                public static C1091fv m2026e(byte[] bArr) {
                    Parcel parcelObtain = Parcel.obtain();
                    parcelObtain.unmarshall(bArr, 0, bArr.length);
                    parcelObtain.setDataPosition(0);
                    C1091fv c1091fvM2059D = CREATOR.m2059D(parcelObtain);
                    parcelObtain.recycle();
                    return c1091fvM2059D;
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
                    return this.f2022rI.contains(Integer.valueOf(aVar.m1669bw()));
                }

                @Override 
                
                protected Object mo1647b(AbstractC1038dw.a aVar) {
                    switch (aVar.m1669bw()) {
                        case 2:
                            return this.f2023sJ;
                        case 3:
                            return this.f2024sK;
                        case 4:
                            return this.f2025sL;
                        case 5:
                            return this.f2026sM;
                        case 6:
                            return Integer.valueOf(this.f2027sN);
                        case 7:
                            return this.f2028sO;
                        case 8:
                            return this.f2029sP;
                        case 9:
                            return this.f2021ml;
                        case 10:
                        case 11:
                        case 13:
                        case 17:
                        default:
                            throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                        case 12:
                            return Integer.valueOf(this.f2018dI);
                        case 14:
                            return this.f2040sm;
                        case 15:
                            return this.f2030sQ;
                        case 16:
                            return Boolean.valueOf(this.f2031sR);
                        case 18:
                            return this.f2032sS;
                        case 19:
                            return this.f2033sT;
                        case 20:
                            return this.f2034sU;
                        case 21:
                            return Integer.valueOf(this.f2035sV);
                        case 22:
                            return this.f2036sW;
                        case 23:
                            return this.f2037sX;
                        case 24:
                            return Integer.valueOf(this.f2038sY);
                        case 25:
                            return Integer.valueOf(this.f2039sZ);
                        case 26:
                            return this.f2041ta;
                        case 27:
                            return this.f2019hN;
                        case 28:
                            return this.f2042tb;
                        case 29:
                            return Boolean.valueOf(this.f2043tc);
                    }
                }

                @Override 
                
                public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                    return f2017rH;
                }

                
                a m2027dD() {
                    return this.f2024sK;
                }

                
                b m2028dE() {
                    return this.f2028sO;
                }

                
                c m2029dF() {
                    return this.f2030sQ;
                }

                
                d m2030dG() {
                    return this.f2033sT;
                }

                
                List<f> m2031dH() {
                    return this.f2036sW;
                }

                
                List<g> m2032dI() {
                    return this.f2037sX;
                }

                
                List<h> m2033dJ() {
                    return this.f2042tb;
                }

                
                public C1091fv m2034dK() {
                    return this;
                }

                @Override 
                public int describeContents() {
                    C1092fw c1092fw = CREATOR;
                    return 0;
                }

                
                Set<Integer> m2035di() {
                    return this.f2022rI;
                }

                public boolean equals(Object obj) {
                    if (!(obj instanceof C1091fv)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    C1091fv c1091fv = (C1091fv) obj;
                    for (AbstractC1038dw.a<?, ?> aVar : f2017rH.values()) {
                        if (mo1646a(aVar)) {
                            if (c1091fv.mo1646a(aVar) && mo1647b(aVar).equals(c1091fv.mo1647b(aVar))) {
                            }
                            return false;
                        }
                        if (c1091fv.mo1646a(aVar)) {
                            return false;
                        }
                    }
                    return true;
                }

                @Override 
                public  Person freeze() {
                    return m2034dK();
                }

                @Override 
                public String getAboutMe() {
                    return this.f2023sJ;
                }

                @Override 
                public Person.AgeRange getAgeRange() {
                    return this.f2024sK;
                }

                @Override 
                public String getBirthday() {
                    return this.f2025sL;
                }

                @Override 
                public String getBraggingRights() {
                    return this.f2026sM;
                }

                @Override 
                public int getCircledByCount() {
                    return this.f2027sN;
                }

                @Override 
                public Person.Cover getCover() {
                    return this.f2028sO;
                }

                @Override 
                public String getCurrentLocation() {
                    return this.f2029sP;
                }

                @Override 
                public String getDisplayName() {
                    return this.f2021ml;
                }

                @Override 
                public int getGender() {
                    return this.f2018dI;
                }

                @Override 
                public String getId() {
                    return this.f2040sm;
                }

                @Override 
                public Person.Image getImage() {
                    return this.f2030sQ;
                }

                @Override 
                public String getLanguage() {
                    return this.f2032sS;
                }

                @Override 
                public Person.Name getName() {
                    return this.f2033sT;
                }

                @Override 
                public String getNickname() {
                    return this.f2034sU;
                }

                @Override 
                public int getObjectType() {
                    return this.f2035sV;
                }

                @Override 
                public List<Person.Organizations> getOrganizations() {
                    return (ArrayList) this.f2036sW;
                }

                @Override 
                public List<Person.PlacesLived> getPlacesLived() {
                    return (ArrayList) this.f2037sX;
                }

                @Override 
                public int getPlusOneCount() {
                    return this.f2038sY;
                }

                @Override 
                public int getRelationshipStatus() {
                    return this.f2039sZ;
                }

                @Override 
                public String getTagline() {
                    return this.f2041ta;
                }

                @Override 
                public String getUrl() {
                    return this.f2019hN;
                }

                @Override 
                public List<Person.Urls> getUrls() {
                    return (ArrayList) this.f2042tb;
                }

                int getVersionCode() {
                    return this.f2020iM;
                }

                @Override 
                public boolean hasAboutMe() {
                    return this.f2022rI.contains(2);
                }

                @Override 
                public boolean hasAgeRange() {
                    return this.f2022rI.contains(3);
                }

                @Override 
                public boolean hasBirthday() {
                    return this.f2022rI.contains(4);
                }

                @Override 
                public boolean hasBraggingRights() {
                    return this.f2022rI.contains(5);
                }

                @Override 
                public boolean hasCircledByCount() {
                    return this.f2022rI.contains(6);
                }

                @Override 
                public boolean hasCover() {
                    return this.f2022rI.contains(7);
                }

                @Override 
                public boolean hasCurrentLocation() {
                    return this.f2022rI.contains(8);
                }

                @Override 
                public boolean hasDisplayName() {
                    return this.f2022rI.contains(9);
                }

                @Override 
                public boolean hasGender() {
                    return this.f2022rI.contains(12);
                }

                @Override 
                public boolean hasId() {
                    return this.f2022rI.contains(14);
                }

                @Override 
                public boolean hasImage() {
                    return this.f2022rI.contains(15);
                }

                @Override 
                public boolean hasIsPlusUser() {
                    return this.f2022rI.contains(16);
                }

                @Override 
                public boolean hasLanguage() {
                    return this.f2022rI.contains(18);
                }

                @Override 
                public boolean hasName() {
                    return this.f2022rI.contains(19);
                }

                @Override 
                public boolean hasNickname() {
                    return this.f2022rI.contains(20);
                }

                @Override 
                public boolean hasObjectType() {
                    return this.f2022rI.contains(21);
                }

                @Override 
                public boolean hasOrganizations() {
                    return this.f2022rI.contains(22);
                }

                @Override 
                public boolean hasPlacesLived() {
                    return this.f2022rI.contains(23);
                }

                @Override 
                public boolean hasPlusOneCount() {
                    return this.f2022rI.contains(24);
                }

                @Override 
                public boolean hasRelationshipStatus() {
                    return this.f2022rI.contains(25);
                }

                @Override 
                public boolean hasTagline() {
                    return this.f2022rI.contains(26);
                }

                @Override 
                public boolean hasUrl() {
                    return this.f2022rI.contains(27);
                }

                @Override 
                public boolean hasUrls() {
                    return this.f2022rI.contains(28);
                }

                @Override 
                public boolean hasVerified() {
                    return this.f2022rI.contains(29);
                }

                public int hashCode() {
                    int iHashCode = 0;
                    Iterator<AbstractC1038dw.a<?, ?>> it = f2017rH.values().iterator();
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
                public boolean isPlusUser() {
                    return this.f2031sR;
                }

                @Override 
                public boolean isVerified() {
                    return this.f2043tc;
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    C1092fw c1092fw = CREATOR;
                    C1092fw.m2058a(this, out, flags);
                }
            }
