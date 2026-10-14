            package com.googlecode.javacpp;

            import java.nio.Buffer;
            import java.nio.FloatBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class FloatPointer extends Pointer {
                private native void allocateArray(int i);

                public native float get(int i);

                public native FloatPointer get(float[] fArr, int i, int i2);

                public native FloatPointer put(int i, float f);

                public native FloatPointer put(float[] fArr, int i, int i2);





/* 38 */        public FloatPointer(float... array) {
/* 39 */            this(array.length);
/* 40 */            put(array);
                }

/* 49 */        public FloatPointer(FloatBuffer buffer) {
/* 50 */            super(buffer);
/* 51 */            if (buffer != null && buffer.hasArray()) {
/* 52 */                float[] array = buffer.array();
/* 53 */                allocateArray(array.length);
/* 54 */                put(array);
/* 55 */                position(buffer.position());
/* 56 */                limit(buffer.limit());
                    }
                }

/* 63 */        public FloatPointer(int size) {
                    try {
/* 66 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 68 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 71 */        public FloatPointer(Pointer p) {
/* 72 */            super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 76 */        public FloatPointer position(int position) {
/* 77 */            return (FloatPointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 80 */        public FloatPointer limit(int limit) {
/* 81 */            return (FloatPointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 84 */        public FloatPointer capacity(int capacity) {
/* 85 */            return (FloatPointer) super.capacity(capacity);
                }

/* 88 */        public float get() {
/* 89 */            return get(0);
                }

/* 92 */        public FloatPointer put(float f) {
/* 93 */            return put(0, f);
                }

/* 103 */       public FloatPointer get(float[] array) {
/* 104 */           return get(array, 0, array.length);
                }

/* 105 */       public FloatPointer put(float[] array) {
/* 106 */           return put(array, 0, array.length);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 127 */       public final FloatBuffer asBuffer() {
/* 128 */           return asByteBuffer().asFloatBuffer();
                }
            }
