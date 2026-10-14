            package com.fasterxml.jackson.core.util;

            import java.lang.reflect.Array;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 19 */    public class ArraysCompat {
/* 20 */        public static char[] copyOf(char[] cArr, int i) {
/* 21 */            return copyOfRange(cArr, 0, i);
                }

/* 35 */        public static int[] copyOf(int[] iArr, int i) {
/* 36 */            return copyOfRange(iArr, 0, i);
                }

/* 50 */        public static <T> T[] copyOf(T[] tArr, int i) {
/* 51 */            return (T[]) copyOfRange(tArr, 0, i);
                }

/* 68 */        public static char[] copyOfRange(char[] cArr, int i, int i2) {
/* 70 */            int i3 = i2 - i;
/* 71 */            int iMin = Math.min(i3, cArr.length - i);
/* 72 */            char[] cArr2 = new char[i3];
/* 73 */            System.arraycopy(cArr, i, cArr2, 0, iMin);
/* 74 */            return cArr2;
                }

/* 91 */        public static int[] copyOfRange(int[] iArr, int i, int i2) {
/* 93 */            int i3 = i2 - i;
/* 94 */            int iMin = Math.min(i3, iArr.length - i);
/* 95 */            int[] iArr2 = new int[i3];
/* 96 */            System.arraycopy(iArr, i, iArr2, 0, iMin);
/* 97 */            return iArr2;
                }

/* 115 */       public static <T> T[] copyOfRange(T[] tArr, int i, int i2) {
/* 117 */           int i3 = i2 - i;
/* 118 */           int iMin = Math.min(i3, tArr.length - i);
/* 119 */           T[] tArr2 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i3));
/* 120 */           System.arraycopy(tArr, i, tArr2, 0, iMin);
/* 121 */           return tArr2;
                }
            }
