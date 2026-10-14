            package com.googlecode.javacpp;

            import java.nio.Buffer;
            import java.nio.IntBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class IntPointer extends Pointer {
                private native void allocateArray(int i);

                public native int get(int i);

                public native IntPointer get(int[] iArr, int i, int i2);

                public native IntPointer put(int i, int i2);

                public native IntPointer put(int[] iArr, int i, int i2);





/* 38 */        public IntPointer(String s) {
/* 39 */            this(s.length() + 1);
/* 40 */            putString(s);
                }

/* 48 */        public IntPointer(int... array) {
/* 49 */            this(array.length);
/* 50 */            put(array);
                }

/* 59 */        public IntPointer(IntBuffer buffer) {
/* 60 */            super(buffer);
/* 61 */            if (buffer != null && buffer.hasArray()) {
/* 62 */                int[] array = buffer.array();
/* 63 */                allocateArray(array.length);
/* 64 */                put(array);
/* 65 */                position(buffer.position());
/* 66 */                limit(buffer.limit());
                    }
                }

/* 73 */        public IntPointer(int size) {
                    try {
/* 76 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 78 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 81 */        public IntPointer(Pointer p) {
/* 82 */            super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 86 */        public IntPointer position(int position) {
/* 87 */            return (IntPointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 90 */        public IntPointer limit(int limit) {
/* 91 */            return (IntPointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 94 */        public IntPointer capacity(int capacity) {
/* 95 */            return (IntPointer) super.capacity(capacity);
                }

/* 100 */       public int[] getStringCodePoints() {
/* 101 */           int[] buffer = new int[16];
/* 102 */           int i = 0;
                    int j = position();
                    while (true) {
/* 103 */               int i2 = position(j).get();
                        buffer[i] = i2;
                        if (i2 != 0) {
/* 104 */                   i++;
                            j++;
/* 105 */                   if (i >= buffer.length) {
/* 106 */                       int[] newbuffer = new int[buffer.length * 2];
/* 107 */                       System.arraycopy(buffer, 0, newbuffer, 0, buffer.length);
/* 108 */                       buffer = newbuffer;
                            }
                        } else {
/* 111 */                   int[] newbuffer2 = new int[i];
/* 112 */                   System.arraycopy(buffer, 0, newbuffer2, 0, i);
/* 113 */                   return newbuffer2;
                        }
                    }
                }

/* 116 */       public String getString() {
/* 117 */           int[] codePoints = getStringCodePoints();
/* 118 */           return new String(codePoints, 0, codePoints.length);
                }

/* 128 */       public IntPointer putString(String s) {
/* 129 */           int[] codePoints = new int[s.length()];
                    for (int i = 0; i < codePoints.length; i++) {
/* 131 */               codePoints[i] = s.codePointAt(i);
                    }
/* 133 */           return put(codePoints).put(codePoints.length, 0);
                }

/* 136 */       public int get() {
/* 137 */           return get(0);
                }

/* 140 */       public IntPointer put(int j) {
/* 141 */           return put(0, j);
                }

/* 151 */       public IntPointer get(int[] array) {
/* 152 */           return get(array, 0, array.length);
                }

/* 153 */       public IntPointer put(int[] array) {
/* 154 */           return put(array, 0, array.length);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 175 */       public final IntBuffer asBuffer() {
/* 176 */           return asByteBuffer().asIntBuffer();
                }
            }
