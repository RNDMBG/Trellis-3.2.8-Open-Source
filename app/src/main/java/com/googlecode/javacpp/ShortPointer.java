            package com.googlecode.javacpp;

            import java.nio.Buffer;
            import java.nio.ShortBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class ShortPointer extends Pointer {
                private native void allocateArray(int i);

                public native ShortPointer get(short[] sArr, int i, int i2);

                public native short get(int i);

                public native ShortPointer put(int i, short s);

                public native ShortPointer put(short[] sArr, int i, int i2);





/* 38 */        public ShortPointer(short... array) {
/* 39 */            this(array.length);
/* 40 */            put(array);
                }

/* 49 */        public ShortPointer(ShortBuffer buffer) {
/* 50 */            super(buffer);
/* 51 */            if (buffer != null && buffer.hasArray()) {
/* 52 */                short[] array = buffer.array();
/* 53 */                allocateArray(array.length);
/* 54 */                put(array);
/* 55 */                position(buffer.position());
/* 56 */                limit(buffer.limit());
                    }
                }

/* 63 */        public ShortPointer(int size) {
                    try {
/* 66 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 68 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 71 */        public ShortPointer(Pointer p) {
/* 72 */            super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 76 */        public ShortPointer position(int position) {
/* 77 */            return (ShortPointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 80 */        public ShortPointer limit(int limit) {
/* 81 */            return (ShortPointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 84 */        public ShortPointer capacity(int capacity) {
/* 85 */            return (ShortPointer) super.capacity(capacity);
                }

/* 88 */        public short get() {
/* 89 */            return get(0);
                }

/* 92 */        public ShortPointer put(short s) {
/* 93 */            return put(0, s);
                }

/* 103 */       public ShortPointer get(short[] array) {
/* 104 */           return get(array, 0, array.length);
                }

/* 105 */       public ShortPointer put(short[] array) {
/* 106 */           return put(array, 0, array.length);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 127 */       public final ShortBuffer asBuffer() {
/* 128 */           return asByteBuffer().asShortBuffer();
                }
            }
