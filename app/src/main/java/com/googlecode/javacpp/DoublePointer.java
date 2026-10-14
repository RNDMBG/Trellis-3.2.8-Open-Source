            package com.googlecode.javacpp;

            import java.nio.Buffer;
            import java.nio.DoubleBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class DoublePointer extends Pointer {
                private native void allocateArray(int i);

                public native double get(int i);

                public native DoublePointer get(double[] dArr, int i, int i2);

                public native DoublePointer put(int i, double d);

                public native DoublePointer put(double[] dArr, int i, int i2);





/* 38 */        public DoublePointer(double... array) {
/* 39 */            this(array.length);
/* 40 */            put(array);
                }

/* 49 */        public DoublePointer(DoubleBuffer buffer) {
/* 50 */            super(buffer);
/* 51 */            if (buffer != null && buffer.hasArray()) {
/* 52 */                double[] array = buffer.array();
/* 53 */                allocateArray(array.length);
/* 54 */                put(array);
/* 55 */                position(buffer.position());
/* 56 */                limit(buffer.limit());
                    }
                }

/* 63 */        public DoublePointer(int size) {
                    try {
/* 66 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 68 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 71 */        public DoublePointer(Pointer p) {
/* 72 */            super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 76 */        public DoublePointer position(int position) {
/* 77 */            return (DoublePointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 80 */        public DoublePointer limit(int limit) {
/* 81 */            return (DoublePointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 84 */        public DoublePointer capacity(int capacity) {
/* 85 */            return (DoublePointer) super.capacity(capacity);
                }

/* 88 */        public double get() {
/* 89 */            return get(0);
                }

/* 92 */        public DoublePointer put(double d) {
/* 93 */            return put(0, d);
                }

/* 103 */       public DoublePointer get(double[] array) {
/* 104 */           return get(array, 0, array.length);
                }

/* 105 */       public DoublePointer put(double[] array) {
/* 106 */           return put(array, 0, array.length);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 127 */       public final DoubleBuffer asBuffer() {
/* 128 */           return asByteBuffer().asDoubleBuffer();
                }
            }
