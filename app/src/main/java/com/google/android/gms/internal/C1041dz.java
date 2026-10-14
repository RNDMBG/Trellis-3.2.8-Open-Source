            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Iterator;
            
            
            
            public class C1041dz implements SafeParcelable {
                public static final C1043ea CREATOR = new C1043ea();

                
                private final int f1746iM;

                
                private final HashMap<String, HashMap<String, AbstractC1038dw.a<?, ?>>> f1747lJ;

                
                private final ArrayList<a> f1748lK;

                
                private final String f1749lL;

                
                public static class a implements SafeParcelable {
                    public static final C1044eb CREATOR = new C1044eb();
                    final String className;

                    
                    final ArrayList<b> f1750lM;
                    final int versionCode;

                    a(int i, String str, ArrayList<b> arrayList) {
                        this.versionCode = i;
                        this.className = str;
                        this.f1750lM = arrayList;
                    }

                    a(String str, HashMap<String, AbstractC1038dw.a<?, ?>> map) {
                        this.versionCode = 1;
                        this.className = str;
                        this.f1750lM = m1688a(map);
                    }

                    
                    private static ArrayList<b> m1688a(HashMap<String, AbstractC1038dw.a<?, ?>> map) {
                        if (map == null) {
                            return null;
                        }
                        ArrayList<b> arrayList = new ArrayList<>();
                        for (String str : map.keySet()) {
                            arrayList.add(new b(str, map.get(str)));
                        }
                        return arrayList;
                    }

                    
                    HashMap<String, AbstractC1038dw.a<?, ?>> m1689bG() {
                        HashMap<String, AbstractC1038dw.a<?, ?>> map = new HashMap<>();
                        int size = this.f1750lM.size();
                        for (int i = 0; i < size; i++) {
                            b bVar = this.f1750lM.get(i);
                            map.put(bVar.f1751lN, bVar.f1752lO);
                        }
                        return map;
                    }

                    @Override 
                    public int describeContents() {
                        C1044eb c1044eb = CREATOR;
                        return 0;
                    }

                    @Override 
                    public void writeToParcel(Parcel out, int flags) {
                        C1044eb c1044eb = CREATOR;
                        C1044eb.m1702a(this, out, flags);
                    }
                }

                
                public static class b implements SafeParcelable {
                    public static final C1040dy CREATOR = new C1040dy();

                    
                    final String f1751lN;

                    
                    final AbstractC1038dw.a<?, ?> f1752lO;
                    final int versionCode;

                    b(int i, String str, AbstractC1038dw.a<?, ?> aVar) {
                        this.versionCode = i;
                        this.f1751lN = str;
                        this.f1752lO = aVar;
                    }

                    b(String str, AbstractC1038dw.a<?, ?> aVar) {
                        this.versionCode = 1;
                        this.f1751lN = str;
                        this.f1752lO = aVar;
                    }

                    @Override 
                    public int describeContents() {
                        C1040dy c1040dy = CREATOR;
                        return 0;
                    }

                    @Override 
                    public void writeToParcel(Parcel out, int flags) {
                        C1040dy c1040dy = CREATOR;
                        C1040dy.m1677a(this, out, flags);
                    }
                }

                C1041dz(int i, ArrayList<a> arrayList, String str) {
                    this.f1746iM = i;
                    this.f1748lK = null;
                    this.f1747lJ = m1680b(arrayList);
                    this.f1749lL = (String) C1028dm.m1605e(str);
                    m1684bC();
                }

                public C1041dz(Class<? extends AbstractC1038dw> cls) {
                    this.f1746iM = 1;
                    this.f1748lK = null;
                    this.f1747lJ = new HashMap<>();
                    this.f1749lL = cls.getCanonicalName();
                }

                
                private static HashMap<String, HashMap<String, AbstractC1038dw.a<?, ?>>> m1680b(ArrayList<a> arrayList) {
                    HashMap<String, HashMap<String, AbstractC1038dw.a<?, ?>>> map = new HashMap<>();
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        a aVar = arrayList.get(i);
                        map.put(aVar.className, aVar.m1689bG());
                    }
                    return map;
                }

                
                public HashMap<String, AbstractC1038dw.a<?, ?>> m1681H(String str) {
                    return this.f1747lJ.get(str);
                }

                
                public void m1682a(Class<? extends AbstractC1038dw> cls, HashMap<String, AbstractC1038dw.a<?, ?>> map) {
                    this.f1747lJ.put(cls.getCanonicalName(), map);
                }

                
                public boolean m1683b(Class<? extends AbstractC1038dw> cls) {
                    return this.f1747lJ.containsKey(cls.getCanonicalName());
                }

                
                public void m1684bC() {
                    Iterator<String> it = this.f1747lJ.keySet().iterator();
                    while (it.hasNext()) {
                        HashMap<String, AbstractC1038dw.a<?, ?>> map = this.f1747lJ.get(it.next());
                        Iterator<String> it2 = map.keySet().iterator();
                        while (it2.hasNext()) {
                            map.get(it2.next()).m1660a(this);
                        }
                    }
                }

                
                public void m1685bD() {
                    for (String str : this.f1747lJ.keySet()) {
                        HashMap<String, AbstractC1038dw.a<?, ?>> map = this.f1747lJ.get(str);
                        HashMap<String, AbstractC1038dw.a<?, ?>> map2 = new HashMap<>();
                        for (String str2 : map.keySet()) {
                            map2.put(str2, map.get(str2).m1665bs());
                        }
                        this.f1747lJ.put(str, map2);
                    }
                }

                
                ArrayList<a> m1686bE() {
                    ArrayList<a> arrayList = new ArrayList<>();
                    for (String str : this.f1747lJ.keySet()) {
                        arrayList.add(new a(str, this.f1747lJ.get(str)));
                    }
                    return arrayList;
                }

                
                public String m1687bF() {
                    return this.f1749lL;
                }

                @Override 
                public int describeContents() {
                    C1043ea c1043ea = CREATOR;
                    return 0;
                }

                int getVersionCode() {
                    return this.f1746iM;
                }

                public String toString() {
                    StringBuilder sb = new StringBuilder();
                    for (String str : this.f1747lJ.keySet()) {
                        sb.append(str).append(":\n");
                        HashMap<String, AbstractC1038dw.a<?, ?>> map = this.f1747lJ.get(str);
                        for (String str2 : map.keySet()) {
                            sb.append("  ").append(str2).append(": ");
                            sb.append(map.get(str2));
                        }
                    }
                    return sb.toString();
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    C1043ea c1043ea = CREATOR;
                    C1043ea.m1699a(this, out, flags);
                }
            }
