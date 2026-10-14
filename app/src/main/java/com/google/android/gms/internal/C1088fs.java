            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.plus.model.moments.ItemScope;
            import com.google.android.gms.plus.model.moments.Moment;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.Set;
            
            
            
            public final class C1088fs extends AbstractC1038dw implements SafeParcelable, Moment {
                public static final C1089ft CREATOR = new C1089ft();

                
                private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2008rH = new HashMap<>();

                
                private final int f2009iM;

                
                private final Set<Integer> f2010rI;

                
                private String f2011sD;

                
                private C1086fq f2012sG;

                
                private C1086fq f2013sH;

                
                private String f2014sm;

                
                private String f2015sx;

                static {
                    f2008rH.put("id", AbstractC1038dw.a.m1658g("id", 2));
                    f2008rH.put("result", AbstractC1038dw.a.m1652a("result", 4, C1086fq.class));
                    f2008rH.put("startDate", AbstractC1038dw.a.m1658g("startDate", 5));
                    f2008rH.put("target", AbstractC1038dw.a.m1652a("target", 6, C1086fq.class));
                    f2008rH.put("type", AbstractC1038dw.a.m1658g("type", 7));
                }

                public C1088fs() {
                    this.f2009iM = 1;
                    this.f2010rI = new HashSet();
                }

                C1088fs(Set<Integer> set, int i, String str, C1086fq c1086fq, String str2, C1086fq c1086fq2, String str3) {
                    this.f2010rI = set;
                    this.f2009iM = i;
                    this.f2014sm = str;
                    this.f2012sG = c1086fq;
                    this.f2015sx = str2;
                    this.f2013sH = c1086fq2;
                    this.f2011sD = str3;
                }

                public C1088fs(Set<Integer> set, String str, C1086fq c1086fq, String str2, C1086fq c1086fq2, String str3) {
                    this.f2010rI = set;
                    this.f2009iM = 1;
                    this.f2014sm = str;
                    this.f2012sG = c1086fq;
                    this.f2015sx = str2;
                    this.f2013sH = c1086fq2;
                    this.f2011sD = str3;
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
                    return this.f2010rI.contains(Integer.valueOf(aVar.m1669bw()));
                }

                @Override 
                
                protected Object mo1647b(AbstractC1038dw.a aVar) {
                    switch (aVar.m1669bw()) {
                        case 2:
                            return this.f2014sm;
                        case 3:
                        default:
                            throw new IllegalStateException("Unknown safe parcelable id=" + aVar.m1669bw());
                        case 4:
                            return this.f2012sG;
                        case 5:
                            return this.f2015sx;
                        case 6:
                            return this.f2013sH;
                        case 7:
                            return this.f2011sD;
                    }
                }

                @Override 
                
                public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                    return f2008rH;
                }

                
                C1086fq m2017dA() {
                    return this.f2013sH;
                }

                
                public C1088fs m2018dB() {
                    return this;
                }

                @Override 
                public int describeContents() {
                    C1089ft c1089ft = CREATOR;
                    return 0;
                }

                
                Set<Integer> m2019di() {
                    return this.f2010rI;
                }

                
                C1086fq m2020dz() {
                    return this.f2012sG;
                }

                public boolean equals(Object obj) {
                    if (!(obj instanceof C1088fs)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    C1088fs c1088fs = (C1088fs) obj;
                    for (AbstractC1038dw.a<?, ?> aVar : f2008rH.values()) {
                        if (mo1646a(aVar)) {
                            if (c1088fs.mo1646a(aVar) && mo1647b(aVar).equals(c1088fs.mo1647b(aVar))) {
                            }
                            return false;
                        }
                        if (c1088fs.mo1646a(aVar)) {
                            return false;
                        }
                    }
                    return true;
                }

                @Override 
                public  Moment freeze() {
                    return m2018dB();
                }

                @Override 
                public String getId() {
                    return this.f2014sm;
                }

                @Override 
                public ItemScope getResult() {
                    return this.f2012sG;
                }

                @Override 
                public String getStartDate() {
                    return this.f2015sx;
                }

                @Override 
                public ItemScope getTarget() {
                    return this.f2013sH;
                }

                @Override 
                public String getType() {
                    return this.f2011sD;
                }

                int getVersionCode() {
                    return this.f2009iM;
                }

                @Override 
                public boolean hasId() {
                    return this.f2010rI.contains(2);
                }

                @Override 
                public boolean hasResult() {
                    return this.f2010rI.contains(4);
                }

                @Override 
                public boolean hasStartDate() {
                    return this.f2010rI.contains(5);
                }

                @Override 
                public boolean hasTarget() {
                    return this.f2010rI.contains(6);
                }

                @Override 
                public boolean hasType() {
                    return this.f2010rI.contains(7);
                }

                public int hashCode() {
                    int iHashCode = 0;
                    Iterator<AbstractC1038dw.a<?, ?>> it = f2008rH.values().iterator();
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
                    C1089ft c1089ft = CREATOR;
                    C1089ft.m2021a(this, out, flags);
                }
            }
