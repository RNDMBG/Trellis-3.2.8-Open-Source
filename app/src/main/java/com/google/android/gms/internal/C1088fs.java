            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.plus.model.moments.ItemScope;
            import com.google.android.gms.plus.model.moments.Moment;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fs */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1088fs extends AbstractC1038dw implements SafeParcelable, Moment {
                public static final C1089ft CREATOR = new C1089ft();

                /* JADX INFO: renamed from: rH */
                private static final HashMap<String, AbstractC1038dw.a<?, ?>> f2008rH = new HashMap<>();

                /* JADX INFO: renamed from: iM */
                private final int f2009iM;

                /* JADX INFO: renamed from: rI */
                private final Set<Integer> f2010rI;

                /* JADX INFO: renamed from: sD */
                private String f2011sD;

                /* JADX INFO: renamed from: sG */
                private C1086fq f2012sG;

                /* JADX INFO: renamed from: sH */
                private C1086fq f2013sH;

                /* JADX INFO: renamed from: sm */
                private String f2014sm;

                /* JADX INFO: renamed from: sx */
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
                    return this.f2010rI.contains(Integer.valueOf(aVar.m1669bw()));
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw
                /* JADX INFO: renamed from: b */
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

                @Override // com.google.android.gms.internal.AbstractC1038dw
                /* JADX INFO: renamed from: bp */
                public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                    return f2008rH;
                }

                /* JADX INFO: renamed from: dA */
                C1086fq m2017dA() {
                    return this.f2013sH;
                }

                /* JADX INFO: renamed from: dB */
                public C1088fs m2018dB() {
                    return this;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    C1089ft c1089ft = CREATOR;
                    return 0;
                }

                /* JADX INFO: renamed from: di */
                Set<Integer> m2019di() {
                    return this.f2010rI;
                }

                /* JADX INFO: renamed from: dz */
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

                @Override // com.google.android.gms.common.data.Freezable
                public /* synthetic */ Moment freeze() {
                    return m2018dB();
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public String getId() {
                    return this.f2014sm;
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public ItemScope getResult() {
                    return this.f2012sG;
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public String getStartDate() {
                    return this.f2015sx;
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public ItemScope getTarget() {
                    return this.f2013sH;
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public String getType() {
                    return this.f2011sD;
                }

                int getVersionCode() {
                    return this.f2009iM;
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public boolean hasId() {
                    return this.f2010rI.contains(2);
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public boolean hasResult() {
                    return this.f2010rI.contains(4);
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public boolean hasStartDate() {
                    return this.f2010rI.contains(5);
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
                public boolean hasTarget() {
                    return this.f2010rI.contains(6);
                }

                @Override // com.google.android.gms.plus.model.moments.Moment
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

                @Override // com.google.android.gms.common.data.Freezable
                public boolean isDataValid() {
                    return true;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C1089ft c1089ft = CREATOR;
                    C1089ft.m2021a(this, out, flags);
                }
            }
