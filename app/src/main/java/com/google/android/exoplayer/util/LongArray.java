            package com.google.android.exoplayer.util;

            import java.util.Arrays;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class LongArray {
                private static final int DEFAULT_INITIAL_CAPACITY = 32;
                private int size;
                private long[] values;

/* 30 */        public LongArray() {
/* 31 */            this(32);
                }

/* 36 */        public LongArray(int initialCapacity) {
/* 38 */            this.values = new long[initialCapacity];
                }

/* 46 */        public void add(long value) {
/* 47 */            if (this.size == this.values.length) {
/* 48 */                this.values = Arrays.copyOf(this.values, this.size * 2);
                    }
/* 50 */            long[] jArr = this.values;
                    int i = this.size;
                    this.size = i + 1;
                    jArr[i] = value;
                }

/* 61 */        public long get(int index) {
/* 62 */            if (index < 0 || index >= this.size) {
/* 63 */                throw new IndexOutOfBoundsException("Invalid size " + index + ", size is " + this.size);
                    }
/* 65 */            return this.values[index];
                }

/* 73 */        public int size() {
/* 74 */            return this.size;
                }
            }
