            package com.googlecode.javacpp;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class PointerPointer extends Pointer {
                private Pointer[] pointerArray;

                private native void allocateArray(int i);

                public native Pointer get(int i);

                public native PointerPointer put(int i, Pointer pointer);





/* 38 */        public PointerPointer(Pointer... array) {
/* 39 */            this(array.length);
                    put(array);
                }

/* 45 */        public PointerPointer(byte[]... array) {
/* 46 */            this(array.length);
                    put(array);
                }

/* 52 */        public PointerPointer(short[]... array) {
/* 53 */            this(array.length);
                    put(array);
                }

/* 59 */        public PointerPointer(int[]... array) {
/* 60 */            this(array.length);
                    put(array);
                }

/* 66 */        public PointerPointer(long[]... array) {
/* 67 */            this(array.length);
                    put(array);
                }

/* 73 */        public PointerPointer(float[]... array) {
/* 74 */            this(array.length);
                    put(array);
                }

/* 80 */        public PointerPointer(double[]... array) {
/* 81 */            this(array.length);
                    put(array);
                }

/* 87 */        public PointerPointer(char[]... array) {
/* 88 */            this(array.length);
                    put(array);
                }

/* 93 */        public PointerPointer(int size) {
                    try {
/* 96 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 98 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 101 */       public PointerPointer(Pointer p) {
/* 102 */           super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 109 */       public PointerPointer position(int position) {
/* 110 */           return (PointerPointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 113 */       public PointerPointer limit(int limit) {
/* 114 */           return (PointerPointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 117 */       public PointerPointer capacity(int capacity) {
/* 118 */           return (PointerPointer) super.capacity(capacity);
                }

/* 127 */       public PointerPointer put(Pointer... array) {
                    for (int i = 0; i < array.length; i++) {
/* 129 */               put(i, array[i]);
                    }
/* 131 */           return this;
                }

/* 140 */       public PointerPointer put(byte[]... array) {
/* 141 */           this.pointerArray = new Pointer[array.length];
                    for (int i = 0; i < array.length; i++) {
/* 143 */               this.pointerArray[i] = new BytePointer(array[i]);
                    }
/* 145 */           return put(this.pointerArray);
                }

/* 154 */       public PointerPointer put(short[]... array) {
/* 155 */           this.pointerArray = new Pointer[array.length];
                    for (int i = 0; i < array.length; i++) {
/* 157 */               this.pointerArray[i] = new ShortPointer(array[i]);
                    }
/* 159 */           return put(this.pointerArray);
                }

/* 168 */       public PointerPointer put(int[]... array) {
/* 169 */           this.pointerArray = new Pointer[array.length];
                    for (int i = 0; i < array.length; i++) {
/* 171 */               this.pointerArray[i] = new IntPointer(array[i]);
                    }
/* 173 */           return put(this.pointerArray);
                }

/* 182 */       public PointerPointer put(long[]... array) {
/* 183 */           this.pointerArray = new Pointer[array.length];
                    for (int i = 0; i < array.length; i++) {
/* 185 */               this.pointerArray[i] = new LongPointer(array[i]);
                    }
/* 187 */           return put(this.pointerArray);
                }

/* 196 */       public PointerPointer put(float[]... array) {
/* 197 */           this.pointerArray = new Pointer[array.length];
                    for (int i = 0; i < array.length; i++) {
/* 199 */               this.pointerArray[i] = new FloatPointer(array[i]);
                    }
/* 201 */           return put(this.pointerArray);
                }

/* 210 */       public PointerPointer put(double[]... array) {
/* 211 */           this.pointerArray = new Pointer[array.length];
                    for (int i = 0; i < array.length; i++) {
/* 213 */               this.pointerArray[i] = new DoublePointer(array[i]);
                    }
/* 215 */           return put(this.pointerArray);
                }

/* 224 */       public PointerPointer put(char[]... array) {
/* 225 */           this.pointerArray = new Pointer[array.length];
                    for (int i = 0; i < array.length; i++) {
/* 227 */               this.pointerArray[i] = new CharPointer(array[i]);
                    }
/* 229 */           return put(this.pointerArray);
                }

/* 232 */       public Pointer get() {
/* 233 */           return get(0);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 236 */       public PointerPointer put(Pointer p) {
/* 237 */           return put(0, p);
                }
            }
