            package android.support.v4.util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    class ContainerHelpers {
                static final int[] EMPTY_INTS = new int[0];
                static final long[] EMPTY_LONGS = new long[0];
                static final Object[] EMPTY_OBJECTS = new Object[0];

/* 18 */        ContainerHelpers() {
                }

/* 24 */        public static int idealIntArraySize(int need) {
/* 25 */            return idealByteArraySize(need * 4) / 4;
                }

/* 28 */        public static int idealLongArraySize(int need) {
/* 29 */            return idealByteArraySize(need * 8) / 8;
                }

/* 32 */        public static int idealByteArraySize(int need) {
                    for (int i = 4; i < 32; i++) {
/* 34 */                if (need <= (1 << i) - 12) {
/* 35 */                    return (1 << i) - 12;
                        }
                    }
/* 37 */            return need;
                }

/* 40 */        public static boolean equal(Object a, Object b) {
                    return a == b || (a != null && a.equals(b));
                }

/* 45 */        static int binarySearch(int[] array, int size, int value) {
/* 46 */            int lo = 0;
/* 47 */            int hi = size - 1;
/* 49 */            while (lo <= hi) {
/* 50 */                int mid = (lo + hi) >>> 1;
/* 51 */                int midVal = array[mid];
/* 53 */                if (midVal < value) {
/* 54 */                    lo = mid + 1;
                        } else {
/* 55 */                    if (midVal <= value) {
                                return mid;
                            }
/* 56 */                    hi = mid - 1;
                        }
                    }
/* 61 */            return lo ^ (-1);
                }

/* 64 */        static int binarySearch(long[] array, int size, long value) {
/* 65 */            int lo = 0;
/* 66 */            int hi = size - 1;
/* 68 */            while (lo <= hi) {
/* 69 */                int mid = (lo + hi) >>> 1;
/* 70 */                long midVal = array[mid];
                        if (midVal < value) {
/* 73 */                    lo = mid + 1;
                        } else {
                            if (midVal <= value) {
                                return mid;
                            }
/* 75 */                    hi = mid - 1;
                        }
                    }
/* 80 */            return lo ^ (-1);
                }
            }
