            package com.googlecode.javacpp;

            import java.nio.Buffer;
            import java.nio.FloatBuffer;
            
            
    public class FloatPointer extends Pointer {
                private native void allocateArray(int i);

                public native float get(int i);

                public native FloatPointer get(float[] fArr, int i, int i2);

                public native FloatPointer put(int i, float f);

                public native FloatPointer put(float[] fArr, int i, int i2);





        public FloatPointer(float... array) {
            this(array.length);
            put(array);
                }

        public FloatPointer(FloatBuffer buffer) {
            super(buffer);
            if (buffer != null && buffer.hasArray()) {
                float[] array = buffer.array();
                allocateArray(array.length);
                put(array);
                position(buffer.position());
                limit(buffer.limit());
                    }
                }

        public FloatPointer(int size) {
                    try {
                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

        public FloatPointer(Pointer p) {
            super(p);
                }

                @Override 
        public FloatPointer position(int position) {
            return (FloatPointer) super.position(position);
                }

                @Override 
        public FloatPointer limit(int limit) {
            return (FloatPointer) super.limit(limit);
                }

                @Override 
        public FloatPointer capacity(int capacity) {
            return (FloatPointer) super.capacity(capacity);
                }

        public float get() {
            return get(0);
                }

        public FloatPointer put(float f) {
            return put(0, f);
                }

       public FloatPointer get(float[] array) {
           return get(array, 0, array.length);
                }

       public FloatPointer put(float[] array) {
           return put(array, 0, array.length);
                }

                @Override 
       public final FloatBuffer asBuffer() {
           return asByteBuffer().asFloatBuffer();
                }
            }
