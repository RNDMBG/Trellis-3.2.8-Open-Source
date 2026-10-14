            package com.google.android.gms.internal;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ee */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1047ee {
                /* JADX INFO: renamed from: a */
                public static void m1722a(StringBuilder sb, double[] dArr) {
                    int length = dArr.length;
                    for (int i = 0; i < length; i++) {
                        if (i != 0) {
                            sb.append(",");
                        }
                        sb.append(Double.toString(dArr[i]));
                    }
                }

                /* JADX INFO: renamed from: a */
                public static void m1723a(StringBuilder sb, float[] fArr) {
                    int length = fArr.length;
                    for (int i = 0; i < length; i++) {
                        if (i != 0) {
                            sb.append(",");
                        }
                        sb.append(Float.toString(fArr[i]));
                    }
                }

                /* JADX INFO: renamed from: a */
                public static void m1724a(StringBuilder sb, int[] iArr) {
                    int length = iArr.length;
                    for (int i = 0; i < length; i++) {
                        if (i != 0) {
                            sb.append(",");
                        }
                        sb.append(Integer.toString(iArr[i]));
                    }
                }

                /* JADX INFO: renamed from: a */
                public static void m1725a(StringBuilder sb, long[] jArr) {
                    int length = jArr.length;
                    for (int i = 0; i < length; i++) {
                        if (i != 0) {
                            sb.append(",");
                        }
                        sb.append(Long.toString(jArr[i]));
                    }
                }

                /* JADX INFO: renamed from: a */
                public static <T> void m1726a(StringBuilder sb, T[] tArr) {
                    int length = tArr.length;
                    for (int i = 0; i < length; i++) {
                        if (i != 0) {
                            sb.append(",");
                        }
                        sb.append(tArr[i].toString());
                    }
                }

                /* JADX INFO: renamed from: a */
                public static void m1727a(StringBuilder sb, String[] strArr) {
                    int length = strArr.length;
                    for (int i = 0; i < length; i++) {
                        if (i != 0) {
                            sb.append(",");
                        }
                        sb.append("\"").append(strArr[i]).append("\"");
                    }
                }

                /* JADX INFO: renamed from: a */
                public static void m1728a(StringBuilder sb, boolean[] zArr) {
                    int length = zArr.length;
                    for (int i = 0; i < length; i++) {
                        if (i != 0) {
                            sb.append(",");
                        }
                        sb.append(Boolean.toString(zArr[i]));
                    }
                }
            }
