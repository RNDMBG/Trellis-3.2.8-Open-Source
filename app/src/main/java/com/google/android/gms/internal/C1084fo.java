            package com.google.android.gms.internal;

            import android.content.Context;
            import com.google.android.gms.common.Scopes;
            import java.util.ArrayList;
            import java.util.Arrays;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fo */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class C1084fo {

                /* JADX INFO: renamed from: it */
                private String f1944it;

                /* JADX INFO: renamed from: rA */
                private String[] f1945rA;

                /* JADX INFO: renamed from: rB */
                private String f1946rB;

                /* JADX INFO: renamed from: rC */
                private String f1947rC;

                /* JADX INFO: renamed from: rD */
                private String f1948rD;

                /* JADX INFO: renamed from: rE */
                private String f1949rE;

                /* JADX INFO: renamed from: rF */
                private ArrayList<String> f1950rF = new ArrayList<>();

                /* JADX INFO: renamed from: rG */
                private String[] f1951rG;

                public C1084fo(Context context) {
                    this.f1947rC = context.getPackageName();
                    this.f1946rB = context.getPackageName();
                    this.f1950rF.add(Scopes.PLUS_LOGIN);
                }

                /* JADX INFO: renamed from: Z */
                public C1084fo m1989Z(String str) {
                    this.f1944it = str;
                    return this;
                }

                /* JADX INFO: renamed from: d */
                public C1084fo m1990d(String... strArr) {
                    this.f1950rF.clear();
                    this.f1950rF.addAll(Arrays.asList(strArr));
                    return this;
                }

                /* JADX INFO: renamed from: dg */
                public C1084fo m1991dg() {
                    this.f1950rF.clear();
                    return this;
                }

                /* JADX INFO: renamed from: dh */
                public C1083fn m1992dh() {
                    if (this.f1944it == null) {
                        this.f1944it = "<<default account>>";
                    }
                    return new C1083fn(this.f1944it, (String[]) this.f1950rF.toArray(new String[this.f1950rF.size()]), this.f1951rG, this.f1945rA, this.f1946rB, this.f1947rC, this.f1948rD, this.f1949rE);
                }

                /* JADX INFO: renamed from: e */
                public C1084fo m1993e(String... strArr) {
                    this.f1951rG = strArr;
                    return this;
                }
            }
