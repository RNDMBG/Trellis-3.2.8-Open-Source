            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
            
            public abstract class AbstractC1038dw {

                
                public static class a<I, O> implements SafeParcelable {
                    public static final C1039dx CREATOR = new C1039dx();

                    
                    private final int f1735iM;

                    
                    protected final boolean f1736lA;

                    
                    protected final int f1737lB;

                    
                    protected final boolean f1738lC;

                    
                    protected final String f1739lD;

                    
                    protected final int f1740lE;

                    
                    protected final Class<? extends AbstractC1038dw> f1741lF;

                    
                    protected final String f1742lG;

                    
                    private C1041dz f1743lH;

                    
                    private b<I, O> f1744lI;

                    
                    protected final int f1745lz;

                    a(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, C1033dr c1033dr) {
                        this.f1735iM = i;
                        this.f1745lz = i2;
                        this.f1736lA = z;
                        this.f1737lB = i3;
                        this.f1738lC = z2;
                        this.f1739lD = str;
                        this.f1740lE = i4;
                        if (str2 == null) {
                            this.f1741lF = null;
                            this.f1742lG = null;
                        } else {
                            this.f1741lF = C1045ec.class;
                            this.f1742lG = str2;
                        }
                        if (c1033dr == null) {
                            this.f1744lI = null;
                        } else {
                            this.f1744lI = (b<I, O>) c1033dr.m1622bl();
                        }
                    }

                    protected a(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends AbstractC1038dw> cls, b<I, O> bVar) {
                        this.f1735iM = 1;
                        this.f1745lz = i;
                        this.f1736lA = z;
                        this.f1737lB = i2;
                        this.f1738lC = z2;
                        this.f1739lD = str;
                        this.f1740lE = i3;
                        this.f1741lF = cls;
                        if (cls == null) {
                            this.f1742lG = null;
                        } else {
                            this.f1742lG = cls.getCanonicalName();
                        }
                        this.f1744lI = bVar;
                    }

                    
                    public static a m1651a(String str, int i, b<?, ?> bVar, boolean z) {
                        return new a(bVar.mo1629bn(), z, bVar.mo1630bo(), false, str, i, null, bVar);
                    }

                    
                    public static <T extends AbstractC1038dw> a<T, T> m1652a(String str, int i, Class<T> cls) {
                        return new a<>(11, false, 11, false, str, i, cls, null);
                    }

                    
                    public static <T extends AbstractC1038dw> a<ArrayList<T>, ArrayList<T>> m1653b(String str, int i, Class<T> cls) {
                        return new a<>(11, true, 11, true, str, i, cls, null);
                    }

                    
                    static  b m1654c(a aVar) {
                        return aVar.f1744lI;
                    }

                    
                    public static a<Integer, Integer> m1655d(String str, int i) {
                        return new a<>(0, false, 0, false, str, i, null, null);
                    }

                    
                    public static a<Double, Double> m1656e(String str, int i) {
                        return new a<>(4, false, 4, false, str, i, null, null);
                    }

                    
                    public static a<Boolean, Boolean> m1657f(String str, int i) {
                        return new a<>(6, false, 6, false, str, i, null, null);
                    }

                    
                    public static a<String, String> m1658g(String str, int i) {
                        return new a<>(7, false, 7, false, str, i, null, null);
                    }

                    
                    public static a<ArrayList<String>, ArrayList<String>> m1659h(String str, int i) {
                        return new a<>(7, true, 7, true, str, i, null, null);
                    }

                    
                    public void m1660a(C1041dz c1041dz) {
                        this.f1743lH = c1041dz;
                    }

                    
                    C1033dr m1661bA() {
                        if (this.f1744lI == null) {
                            return null;
                        }
                        return C1033dr.m1620a(this.f1744lI);
                    }

                    
                    public HashMap<String, a<?, ?>> m1662bB() {
                        C1028dm.m1605e(this.f1742lG);
                        C1028dm.m1605e(this.f1743lH);
                        return this.f1743lH.m1681H(this.f1742lG);
                    }

                    
                    public int m1663bn() {
                        return this.f1745lz;
                    }

                    
                    public int m1664bo() {
                        return this.f1737lB;
                    }

                    
                    public a<I, O> m1665bs() {
                        return new a<>(this.f1735iM, this.f1745lz, this.f1736lA, this.f1737lB, this.f1738lC, this.f1739lD, this.f1740lE, this.f1742lG, m1661bA());
                    }

                    
                    public boolean m1666bt() {
                        return this.f1736lA;
                    }

                    
                    public boolean m1667bu() {
                        return this.f1738lC;
                    }

                    
                    public String m1668bv() {
                        return this.f1739lD;
                    }

                    
                    public int m1669bw() {
                        return this.f1740lE;
                    }

                    
                    public Class<? extends AbstractC1038dw> m1670bx() {
                        return this.f1741lF;
                    }

                    
                    String m1671by() {
                        if (this.f1742lG == null) {
                            return null;
                        }
                        return this.f1742lG;
                    }

                    
                    public boolean m1672bz() {
                        return this.f1744lI != null;
                    }

                    @Override 
                    public int describeContents() {
                        C1039dx c1039dx = CREATOR;
                        return 0;
                    }

                    
                    public I m1673f(O o) {
                        return this.f1744lI.mo1632f(o);
                    }

                    public int getVersionCode() {
                        return this.f1735iM;
                    }

                    public String toString() {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Field\n");
                        sb.append("            versionCode=").append(this.f1735iM).append('\n');
                        sb.append("                 typeIn=").append(this.f1745lz).append('\n');
                        sb.append("            typeInArray=").append(this.f1736lA).append('\n');
                        sb.append("                typeOut=").append(this.f1737lB).append('\n');
                        sb.append("           typeOutArray=").append(this.f1738lC).append('\n');
                        sb.append("        outputFieldName=").append(this.f1739lD).append('\n');
                        sb.append("      safeParcelFieldId=").append(this.f1740lE).append('\n');
                        sb.append("       concreteTypeName=").append(m1671by()).append('\n');
                        if (m1670bx() != null) {
                            sb.append("     concreteType.class=").append(m1670bx().getCanonicalName()).append('\n');
                        }
                        sb.append("          converterName=").append(this.f1744lI == null ? "null" : this.f1744lI.getClass().getCanonicalName()).append('\n');
                        return sb.toString();
                    }

                    @Override 
                    public void writeToParcel(Parcel out, int flags) {
                        C1039dx c1039dx = CREATOR;
                        C1039dx.m1674a(this, out, flags);
                    }
                }

                
                public interface b<I, O> {
                    
                    int mo1629bn();

                    
                    int mo1630bo();

                    
                    I mo1632f(O o);
                }

                
                private void m1639a(StringBuilder sb, a aVar, Object obj) {
                    if (aVar.m1663bn() == 11) {
                        sb.append(aVar.m1670bx().cast(obj).toString());
                    } else {
                        if (aVar.m1663bn() != 7) {
                            sb.append(obj);
                            return;
                        }
                        sb.append("\"");
                        sb.append(C1051ei.m1733I((String) obj));
                        sb.append("\"");
                    }
                }

                
                private void m1640a(StringBuilder sb, a aVar, ArrayList<Object> arrayList) {
                    sb.append("[");
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        if (i > 0) {
                            sb.append(",");
                        }
                        Object obj = arrayList.get(i);
                        if (obj != null) {
                            m1639a(sb, aVar, obj);
                        }
                    }
                    sb.append("]");
                }

                
                protected abstract Object mo1641D(String str);

                
                protected abstract boolean mo1642E(String str);

                
                protected boolean m1643F(String str) {
                    throw new UnsupportedOperationException("Concrete types not supported");
                }

                
                protected boolean m1644G(String str) {
                    throw new UnsupportedOperationException("Concrete type arrays not supported");
                }

                
                
                protected <O, I> I m1645a(a<I, O> aVar, Object obj) {
                    return a.m1654c(aVar) != null ? aVar.m1673f(obj) : obj;
                }

                
                protected boolean mo1646a(a aVar) {
                    if (aVar.m1664bo() == 11) {
                        return aVar.m1667bu() ? m1644G(aVar.m1668bv()) : m1643F(aVar.m1668bv());
                    }
                    return mo1642E(aVar.m1668bv());
                }

                
                protected Object mo1647b(a aVar) {
                    String strM1668bv = aVar.m1668bv();
                    if (aVar.m1670bx() == null) {
                        return mo1641D(aVar.m1668bv());
                    }
                    C1028dm.m1602a(mo1641D(aVar.m1668bv()) == null, "Concrete field shouldn't be value object: " + aVar.m1668bv());
                    HashMap<String, Object> mapM1650br = aVar.m1667bu() ? m1650br() : m1649bq();
                    if (mapM1650br != null) {
                        return mapM1650br.get(strM1668bv);
                    }
                    try {
                        return getClass().getMethod("get" + Character.toUpperCase(strM1668bv.charAt(0)) + strM1668bv.substring(1), new Class[0]).invoke(this, new Object[0]);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                
                public abstract HashMap<String, a<?, ?>> mo1648bp();

                
                public HashMap<String, Object> m1649bq() {
                    return null;
                }

                
                public HashMap<String, Object> m1650br() {
                    return null;
                }

                public String toString() {
                    HashMap<String, a<?, ?>> mapMo1648bp = mo1648bp();
                    StringBuilder sb = new StringBuilder(100);
                    for (String str : mapMo1648bp.keySet()) {
                        a<?, ?> aVar = mapMo1648bp.get(str);
                        if (mo1646a(aVar)) {
                            Object objM1645a = m1645a(aVar, mo1647b(aVar));
                            if (sb.length() == 0) {
                                sb.append("{");
                            } else {
                                sb.append(",");
                            }
                            sb.append("\"").append(str).append("\":");
                            if (objM1645a != null) {
                                switch (aVar.m1664bo()) {
                                    case 8:
                                        sb.append("\"").append(C1048ef.m1729b((byte[]) objM1645a)).append("\"");
                                        break;
                                    case 9:
                                        sb.append("\"").append(C1048ef.m1730c((byte[]) objM1645a)).append("\"");
                                        break;
                                    case 10:
                                        C1052ej.m1734a(sb, (HashMap) objM1645a);
                                        break;
                                    default:
                                        if (aVar.m1666bt()) {
                                            m1640a(sb, (a) aVar, (ArrayList<Object>) objM1645a);
                                        } else {
                                            m1639a(sb, aVar, objM1645a);
                                        }
                                        break;
                                }
                            } else {
                                sb.append("null");
                            }
                        }
                    }
                    if (sb.length() > 0) {
                        sb.append("}");
                    } else {
                        sb.append("{}");
                    }
                    return sb.toString();
                }
            }
