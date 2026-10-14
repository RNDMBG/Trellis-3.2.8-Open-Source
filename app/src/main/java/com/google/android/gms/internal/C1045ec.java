            package com.google.android.gms.internal;

            import android.os.Bundle;
            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Iterator;
            import java.util.Map;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ec */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1045ec extends AbstractC1038dw implements SafeParcelable {
                public static final C1046ed CREATOR = new C1046ed();

                /* JADX INFO: renamed from: iM */
                private final int f1757iM;

                /* JADX INFO: renamed from: lH */
                private final C1041dz f1758lH;

                /* JADX INFO: renamed from: lP */
                private final Parcel f1759lP;

                /* JADX INFO: renamed from: lQ */
                private final int f1760lQ;

                /* JADX INFO: renamed from: lR */
                private int f1761lR;

                /* JADX INFO: renamed from: lS */
                private int f1762lS;
                private final String mClassName;

                C1045ec(int i, Parcel parcel, C1041dz c1041dz) {
                    this.f1757iM = i;
                    this.f1759lP = (Parcel) C1028dm.m1605e(parcel);
                    this.f1760lQ = 2;
                    this.f1758lH = c1041dz;
                    if (this.f1758lH == null) {
                        this.mClassName = null;
                    } else {
                        this.mClassName = this.f1758lH.m1687bF();
                    }
                    this.f1761lR = 2;
                }

                private C1045ec(SafeParcelable safeParcelable, C1041dz c1041dz, String str) {
                    this.f1757iM = 1;
                    this.f1759lP = Parcel.obtain();
                    safeParcelable.writeToParcel(this.f1759lP, 0);
                    this.f1760lQ = 1;
                    this.f1758lH = (C1041dz) C1028dm.m1605e(c1041dz);
                    this.mClassName = (String) C1028dm.m1605e(str);
                    this.f1761lR = 2;
                }

                /* JADX INFO: renamed from: a */
                public static <T extends AbstractC1038dw & SafeParcelable> C1045ec m1705a(T t) {
                    String canonicalName = t.getClass().getCanonicalName();
                    return new C1045ec(t, m1711b(t), canonicalName);
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: a */
                private static void m1706a(C1041dz c1041dz, AbstractC1038dw abstractC1038dw) {
                    Class<?> cls = abstractC1038dw.getClass();
                    if (c1041dz.m1683b((Class<? extends AbstractC1038dw>) cls)) {
                        return;
                    }
                    HashMap<String, AbstractC1038dw.a<?, ?>> mapMo1648bp = abstractC1038dw.mo1648bp();
                    c1041dz.m1682a(cls, abstractC1038dw.mo1648bp());
                    Iterator<String> it = mapMo1648bp.keySet().iterator();
                    while (it.hasNext()) {
                        AbstractC1038dw.a<?, ?> aVar = mapMo1648bp.get(it.next());
                        Class<? extends AbstractC1038dw> clsM1670bx = aVar.m1670bx();
                        if (clsM1670bx != null) {
                            try {
                                m1706a(c1041dz, clsM1670bx.newInstance());
                            } catch (IllegalAccessException e) {
                                throw new IllegalStateException("Could not access object of type " + aVar.m1670bx().getCanonicalName(), e);
                            } catch (InstantiationException e2) {
                                throw new IllegalStateException("Could not instantiate an object of type " + aVar.m1670bx().getCanonicalName(), e2);
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                private void m1707a(StringBuilder sb, int i, Object obj) {
                    switch (i) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            sb.append(obj);
                            return;
                        case 7:
                            sb.append("\"").append(C1051ei.m1733I(obj.toString())).append("\"");
                            return;
                        case 8:
                            sb.append("\"").append(C1048ef.m1729b((byte[]) obj)).append("\"");
                            return;
                        case 9:
                            sb.append("\"").append(C1048ef.m1730c((byte[]) obj));
                            sb.append("\"");
                            return;
                        case 10:
                            C1052ej.m1734a(sb, (HashMap) obj);
                            return;
                        case 11:
                            throw new IllegalArgumentException("Method does not accept concrete type.");
                        default:
                            throw new IllegalArgumentException("Unknown type = " + i);
                    }
                }

                /* JADX INFO: renamed from: a */
                private void m1708a(StringBuilder sb, AbstractC1038dw.a<?, ?> aVar, Parcel parcel, int i) {
                    switch (aVar.m1664bo()) {
                        case 0:
                            m1715b(sb, aVar, m1645a(aVar, Integer.valueOf(C0901a.m1105f(parcel, i))));
                            return;
                        case 1:
                            m1715b(sb, aVar, m1645a(aVar, C0901a.m1107h(parcel, i)));
                            return;
                        case 2:
                            m1715b(sb, aVar, m1645a(aVar, Long.valueOf(C0901a.m1106g(parcel, i))));
                            return;
                        case 3:
                            m1715b(sb, aVar, m1645a(aVar, Float.valueOf(C0901a.m1108i(parcel, i))));
                            return;
                        case 4:
                            m1715b(sb, aVar, m1645a(aVar, Double.valueOf(C0901a.m1110j(parcel, i))));
                            return;
                        case 5:
                            m1715b(sb, aVar, m1645a(aVar, C0901a.m1112k(parcel, i)));
                            return;
                        case 6:
                            m1715b(sb, aVar, m1645a(aVar, Boolean.valueOf(C0901a.m1102c(parcel, i))));
                            return;
                        case 7:
                            m1715b(sb, aVar, m1645a(aVar, C0901a.m1113l(parcel, i)));
                            return;
                        case 8:
                        case 9:
                            m1715b(sb, aVar, m1645a(aVar, C0901a.m1116o(parcel, i)));
                            return;
                        case 10:
                            m1715b(sb, aVar, m1645a(aVar, m1712b(C0901a.m1115n(parcel, i))));
                            return;
                        case 11:
                            throw new IllegalArgumentException("Method does not accept concrete type.");
                        default:
                            throw new IllegalArgumentException("Unknown field out type = " + aVar.m1664bo());
                    }
                }

                /* JADX INFO: renamed from: a */
                private void m1709a(StringBuilder sb, String str, AbstractC1038dw.a<?, ?> aVar, Parcel parcel, int i) {
                    sb.append("\"").append(str).append("\":");
                    if (aVar.m1672bz()) {
                        m1708a(sb, aVar, parcel, i);
                    } else {
                        m1714b(sb, aVar, parcel, i);
                    }
                }

                /* JADX INFO: renamed from: a */
                private void m1710a(StringBuilder sb, HashMap<String, AbstractC1038dw.a<?, ?>> map, Parcel parcel) {
                    HashMap<Integer, Map.Entry<String, AbstractC1038dw.a<?, ?>>> mapM1713b = m1713b(map);
                    sb.append('{');
                    int iM1111j = C0901a.m1111j(parcel);
                    boolean z = false;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        Map.Entry<String, AbstractC1038dw.a<?, ?>> entry = mapM1713b.get(Integer.valueOf(C0901a.m1126y(iM1109i)));
                        if (entry != null) {
                            if (z) {
                                sb.append(",");
                            }
                            m1709a(sb, entry.getKey(), entry.getValue(), parcel, iM1109i);
                            z = true;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    sb.append('}');
                }

                /* JADX INFO: renamed from: b */
                private static C1041dz m1711b(AbstractC1038dw abstractC1038dw) {
                    C1041dz c1041dz = new C1041dz(abstractC1038dw.getClass());
                    m1706a(c1041dz, abstractC1038dw);
                    c1041dz.m1685bD();
                    c1041dz.m1684bC();
                    return c1041dz;
                }

                /* JADX INFO: renamed from: b */
                public static HashMap<String, String> m1712b(Bundle bundle) {
                    HashMap<String, String> map = new HashMap<>();
                    for (String str : bundle.keySet()) {
                        map.put(str, bundle.getString(str));
                    }
                    return map;
                }

                /* JADX INFO: renamed from: b */
                private static HashMap<Integer, Map.Entry<String, AbstractC1038dw.a<?, ?>>> m1713b(HashMap<String, AbstractC1038dw.a<?, ?>> map) {
                    HashMap<Integer, Map.Entry<String, AbstractC1038dw.a<?, ?>>> map2 = new HashMap<>();
                    for (Map.Entry<String, AbstractC1038dw.a<?, ?>> entry : map.entrySet()) {
                        map2.put(Integer.valueOf(entry.getValue().m1669bw()), entry);
                    }
                    return map2;
                }

                /* JADX INFO: renamed from: b */
                private void m1714b(StringBuilder sb, AbstractC1038dw.a<?, ?> aVar, Parcel parcel, int i) {
                    if (aVar.m1667bu()) {
                        sb.append("[");
                        switch (aVar.m1664bo()) {
                            case 0:
                                C1047ee.m1724a(sb, C0901a.m1118q(parcel, i));
                                break;
                            case 1:
                                C1047ee.m1726a(sb, C0901a.m1120s(parcel, i));
                                break;
                            case 2:
                                C1047ee.m1725a(sb, C0901a.m1119r(parcel, i));
                                break;
                            case 3:
                                C1047ee.m1723a(sb, C0901a.m1121t(parcel, i));
                                break;
                            case 4:
                                C1047ee.m1722a(sb, C0901a.m1122u(parcel, i));
                                break;
                            case 5:
                                C1047ee.m1726a(sb, C0901a.m1123v(parcel, i));
                                break;
                            case 6:
                                C1047ee.m1728a(sb, C0901a.m1117p(parcel, i));
                                break;
                            case 7:
                                C1047ee.m1727a(sb, C0901a.m1124w(parcel, i));
                                break;
                            case 8:
                            case 9:
                            case 10:
                                throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                            case 11:
                                Parcel[] parcelArrM1128z = C0901a.m1128z(parcel, i);
                                int length = parcelArrM1128z.length;
                                for (int i2 = 0; i2 < length; i2++) {
                                    if (i2 > 0) {
                                        sb.append(",");
                                    }
                                    parcelArrM1128z[i2].setDataPosition(0);
                                    m1710a(sb, aVar.m1662bB(), parcelArrM1128z[i2]);
                                }
                                break;
                            default:
                                throw new IllegalStateException("Unknown field type out.");
                        }
                        sb.append("]");
                        return;
                    }
                    switch (aVar.m1664bo()) {
                        case 0:
                            sb.append(C0901a.m1105f(parcel, i));
                            return;
                        case 1:
                            sb.append(C0901a.m1107h(parcel, i));
                            return;
                        case 2:
                            sb.append(C0901a.m1106g(parcel, i));
                            return;
                        case 3:
                            sb.append(C0901a.m1108i(parcel, i));
                            return;
                        case 4:
                            sb.append(C0901a.m1110j(parcel, i));
                            return;
                        case 5:
                            sb.append(C0901a.m1112k(parcel, i));
                            return;
                        case 6:
                            sb.append(C0901a.m1102c(parcel, i));
                            return;
                        case 7:
                            sb.append("\"").append(C1051ei.m1733I(C0901a.m1113l(parcel, i))).append("\"");
                            return;
                        case 8:
                            sb.append("\"").append(C1048ef.m1729b(C0901a.m1116o(parcel, i))).append("\"");
                            return;
                        case 9:
                            sb.append("\"").append(C1048ef.m1730c(C0901a.m1116o(parcel, i)));
                            sb.append("\"");
                            return;
                        case 10:
                            Bundle bundleM1115n = C0901a.m1115n(parcel, i);
                            Set<String> setKeySet = bundleM1115n.keySet();
                            setKeySet.size();
                            sb.append("{");
                            boolean z = true;
                            for (String str : setKeySet) {
                                if (!z) {
                                    sb.append(",");
                                }
                                sb.append("\"").append(str).append("\"");
                                sb.append(":");
                                sb.append("\"").append(C1051ei.m1733I(bundleM1115n.getString(str))).append("\"");
                                z = false;
                            }
                            sb.append("}");
                            return;
                        case 11:
                            Parcel parcelM1127y = C0901a.m1127y(parcel, i);
                            parcelM1127y.setDataPosition(0);
                            m1710a(sb, aVar.m1662bB(), parcelM1127y);
                            return;
                        default:
                            throw new IllegalStateException("Unknown field type out");
                    }
                }

                /* JADX INFO: renamed from: b */
                private void m1715b(StringBuilder sb, AbstractC1038dw.a<?, ?> aVar, Object obj) {
                    if (aVar.m1666bt()) {
                        m1716b(sb, aVar, (ArrayList<?>) obj);
                    } else {
                        m1707a(sb, aVar.m1663bn(), obj);
                    }
                }

                /* JADX INFO: renamed from: b */
                private void m1716b(StringBuilder sb, AbstractC1038dw.a<?, ?> aVar, ArrayList<?> arrayList) {
                    sb.append("[");
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        if (i != 0) {
                            sb.append(",");
                        }
                        m1707a(sb, aVar.m1663bn(), arrayList.get(i));
                    }
                    sb.append("]");
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw
                /* JADX INFO: renamed from: D */
                protected Object mo1641D(String str) {
                    throw new UnsupportedOperationException("Converting to JSON does not require this method.");
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw
                /* JADX INFO: renamed from: E */
                protected boolean mo1642E(String str) {
                    throw new UnsupportedOperationException("Converting to JSON does not require this method.");
                }

                /* JADX INFO: renamed from: bH */
                public Parcel m1717bH() {
                    switch (this.f1761lR) {
                        case 0:
                            this.f1762lS = C0902b.m1152k(this.f1759lP);
                            C0902b.m1131C(this.f1759lP, this.f1762lS);
                            this.f1761lR = 2;
                            break;
                        case 1:
                            C0902b.m1131C(this.f1759lP, this.f1762lS);
                            this.f1761lR = 2;
                            break;
                    }
                    return this.f1759lP;
                }

                /* JADX INFO: renamed from: bI */
                C1041dz m1718bI() {
                    switch (this.f1760lQ) {
                        case 0:
                            return null;
                        case 1:
                            return this.f1758lH;
                        case 2:
                            return this.f1758lH;
                        default:
                            throw new IllegalStateException("Invalid creation type: " + this.f1760lQ);
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw
                /* JADX INFO: renamed from: bp */
                public HashMap<String, AbstractC1038dw.a<?, ?>> mo1648bp() {
                    if (this.f1758lH == null) {
                        return null;
                    }
                    return this.f1758lH.m1681H(this.mClassName);
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    C1046ed c1046ed = CREATOR;
                    return 0;
                }

                public int getVersionCode() {
                    return this.f1757iM;
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw
                public String toString() {
                    C1028dm.m1601a(this.f1758lH, "Cannot convert to JSON on client side.");
                    Parcel parcelM1717bH = m1717bH();
                    parcelM1717bH.setDataPosition(0);
                    StringBuilder sb = new StringBuilder(100);
                    m1710a(sb, this.f1758lH.m1681H(this.mClassName), parcelM1717bH);
                    return sb.toString();
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C1046ed c1046ed = CREATOR;
                    C1046ed.m1719a(this, out, flags);
                }
            }
