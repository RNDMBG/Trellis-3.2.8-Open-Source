            package com.googlecode.javacpp;

            import java.nio.Buffer;
            import java.nio.LongBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class LongPointer extends Pointer {
                private native void allocateArray(int i);

                public native long get(int i);

                public native LongPointer get(long[] jArr, int i, int i2);

                public native LongPointer put(int i, long j);

                public native LongPointer put(long[] jArr, int i, int i2);





/* 38 */        public LongPointer(long... array) {
/* 39 */            this(array.length);
/* 40 */            put(array);
                }

/* 49 */        public LongPointer(LongBuffer buffer) {
/* 50 */            super(buffer);
/* 51 */            if (buffer != null && buffer.hasArray()) {
/* 52 */                long[] array = buffer.array();
/* 53 */                allocateArray(array.length);
/* 54 */                put(array);
/* 55 */                position(buffer.position());
/* 56 */                limit(buffer.limit());
                    }
                }

/* 63 */        public LongPointer(int size) {
                    try {
/* 66 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 68 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 71 */        public LongPointer(Pointer p) {
/* 72 */            super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 76 */        public LongPointer position(int position) {
/* 77 */            return (LongPointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 80 */        public LongPointer limit(int limit) {
/* 81 */            return (LongPointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 84 */        public LongPointer capacity(int capacity) {
/* 85 */            return (LongPointer) super.capacity(capacity);
                }

/* 88 */        public long get() {
/* 89 */            return get(0);
                }

/* 92 */        public LongPointer put(long l) {
/* 93 */            return put(0, l);
                }

/* 103 */       public LongPointer get(long[] array) {
/* 104 */           return get(array, 0, array.length);
                }

/* 105 */       public LongPointer put(long[] array) {
/* 106 */           return put(array, 0, array.length);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 127 */       public final LongBuffer asBuffer() {
/* 128 */           return asByteBuffer().asLongBuffer();
                }
            }
