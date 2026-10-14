            package com.fasterxml.jackson.core.util;

            import java.lang.reflect.Array;
            
            
    public class ArraysCompat {
        public static char[] copyOf(char[] cArr, int i) {
            return copyOfRange(cArr, 0, i);
                }

        public static int[] copyOf(int[] iArr, int i) {
            return copyOfRange(iArr, 0, i);
                }

        public static <T> T[] copyOf(T[] tArr, int i) {
            return (T[]) copyOfRange(tArr, 0, i);
                }

        public static char[] copyOfRange(char[] cArr, int i, int i2) {
            int i3 = i2 - i;
            int iMin = Math.min(i3, cArr.length - i);
            char[] cArr2 = new char[i3];
            System.arraycopy(cArr, i, cArr2, 0, iMin);
            return cArr2;
                }

        public static int[] copyOfRange(int[] iArr, int i, int i2) {
            int i3 = i2 - i;
            int iMin = Math.min(i3, iArr.length - i);
            int[] iArr2 = new int[i3];
            System.arraycopy(iArr, i, iArr2, 0, iMin);
            return iArr2;
                }

       public static <T> T[] copyOfRange(T[] tArr, int i, int i2) {
           int i3 = i2 - i;
           int iMin = Math.min(i3, tArr.length - i);
           T[] tArr2 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i3));
           System.arraycopy(tArr, i, tArr2, 0, iMin);
           return tArr2;
                }
            }
