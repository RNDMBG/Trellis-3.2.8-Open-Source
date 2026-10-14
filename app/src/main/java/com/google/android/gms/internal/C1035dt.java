            package com.google.android.gms.internal;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dt */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1035dt implements SafeParcelable, AbstractC1038dw.b<String, Integer> {
                public static final C1036du CREATOR = new C1036du();

                /* JADX INFO: renamed from: iM */
                private final int f1729iM;

                /* JADX INFO: renamed from: lu */
                private final HashMap<String, Integer> f1730lu;

                /* JADX INFO: renamed from: lv */
                private final HashMap<Integer, String> f1731lv;

                /* JADX INFO: renamed from: lw */
                private final ArrayList<a> f1732lw;

                /* JADX INFO: renamed from: com.google.android.gms.internal.dt$a */
                public static final class a implements SafeParcelable {
                    public static final C1037dv CREATOR = new C1037dv();

                    /* JADX INFO: renamed from: lx */
                    final String f1733lx;

                    /* JADX INFO: renamed from: ly */
                    final int f1734ly;
                    final int versionCode;

                    a(int i, String str, int i2) {
                        this.versionCode = i;
                        this.f1733lx = str;
                        this.f1734ly = i2;
                    }

                    a(String str, int i) {
                        this.versionCode = 1;
                        this.f1733lx = str;
                        this.f1734ly = i;
                    }

                    @Override // android.os.Parcelable
                    public int describeContents() {
                        C1037dv c1037dv = CREATOR;
                        return 0;
                    }

                    @Override // android.os.Parcelable
                    public void writeToParcel(Parcel out, int flags) {
                        C1037dv c1037dv = CREATOR;
                        C1037dv.m1636a(this, out, flags);
                    }
                }

                public C1035dt() {
                    this.f1729iM = 1;
                    this.f1730lu = new HashMap<>();
                    this.f1731lv = new HashMap<>();
                    this.f1732lw = null;
                }

                C1035dt(int i, ArrayList<a> arrayList) {
                    this.f1729iM = i;
                    this.f1730lu = new HashMap<>();
                    this.f1731lv = new HashMap<>();
                    this.f1732lw = null;
                    m1626a(arrayList);
                }

                /* JADX INFO: renamed from: a */
                private void m1626a(ArrayList<a> arrayList) {
                    for (a aVar : arrayList) {
                        m1631c(aVar.f1733lx, aVar.f1734ly);
                    }
                }

                /* JADX INFO: renamed from: a */
                public String m1627a(Integer num) {
                    String str = this.f1731lv.get(num);
                    return (str == null && this.f1730lu.containsKey("gms_unknown")) ? "gms_unknown" : str;
                }

                /* JADX INFO: renamed from: bm */
                ArrayList<a> m1628bm() {
                    ArrayList<a> arrayList = new ArrayList<>();
                    for (String str : this.f1730lu.keySet()) {
                        arrayList.add(new a(str, this.f1730lu.get(str).intValue()));
                    }
                    return arrayList;
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw.b
                /* JADX INFO: renamed from: bn */
                public int mo1629bn() {
                    return 7;
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw.b
                /* JADX INFO: renamed from: bo */
                public int mo1630bo() {
                    return 0;
                }

                /* JADX INFO: renamed from: c */
                public C1035dt m1631c(String str, int i) {
                    this.f1730lu.put(str, Integer.valueOf(i));
                    this.f1731lv.put(Integer.valueOf(i), str);
                    return this;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    C1036du c1036du = CREATOR;
                    return 0;
                }

                @Override // com.google.android.gms.internal.AbstractC1038dw.b
                /* JADX INFO: renamed from: f */
                public /* synthetic */ String mo1632f(Integer num) {
                    return m1627a(num);
                }

                int getVersionCode() {
                    return this.f1729iM;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C1036du c1036du = CREATOR;
                    C1036du.m1633a(this, out, flags);
                }
            }
