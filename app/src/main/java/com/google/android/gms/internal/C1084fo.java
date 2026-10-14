            package com.google.android.gms.internal;

            import android.content.Context;
            import com.google.android.gms.common.Scopes;
            import java.util.ArrayList;
            import java.util.Arrays;
            
            
            
            public class C1084fo {

                
                private String f1944it;

                
                private String[] f1945rA;

                
                private String f1946rB;

                
                private String f1947rC;

                
                private String f1948rD;

                
                private String f1949rE;

                
                private ArrayList<String> f1950rF = new ArrayList<>();

                
                private String[] f1951rG;

                public C1084fo(Context context) {
                    this.f1947rC = context.getPackageName();
                    this.f1946rB = context.getPackageName();
                    this.f1950rF.add(Scopes.PLUS_LOGIN);
                }

                
                public C1084fo m1989Z(String str) {
                    this.f1944it = str;
                    return this;
                }

                
                public C1084fo m1990d(String... strArr) {
                    this.f1950rF.clear();
                    this.f1950rF.addAll(Arrays.asList(strArr));
                    return this;
                }

                
                public C1084fo m1991dg() {
                    this.f1950rF.clear();
                    return this;
                }

                
                public C1083fn m1992dh() {
                    if (this.f1944it == null) {
                        this.f1944it = "<<default account>>";
                    }
                    return new C1083fn(this.f1944it, (String[]) this.f1950rF.toArray(new String[this.f1950rF.size()]), this.f1951rG, this.f1945rA, this.f1946rB, this.f1947rC, this.f1948rD, this.f1949rE);
                }

                
                public C1084fo m1993e(String... strArr) {
                    this.f1951rG = strArr;
                    return this;
                }
            }
