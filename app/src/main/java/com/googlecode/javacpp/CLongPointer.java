            package com.googlecode.javacpp;

            import com.googlecode.javacpp.annotation.Cast;
            import com.googlecode.javacpp.annotation.Name;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Name({"long"})
/* 33 */    public class CLongPointer extends Pointer {
                private native void allocateArray(int i);

                @Cast({"long"})
                public native long get(int i);

                public native CLongPointer put(int i, long j);




/* 41 */        public CLongPointer(int size) {
                    try {
/* 44 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 46 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 49 */        public CLongPointer(Pointer p) {
/* 50 */            super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 54 */        public CLongPointer position(int position) {
/* 55 */            return (CLongPointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 58 */        public CLongPointer limit(int limit) {
/* 59 */            return (CLongPointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 62 */        public CLongPointer capacity(int capacity) {
/* 63 */            return (CLongPointer) super.capacity(capacity);
                }

/* 66 */        public long get() {
/* 67 */            return get(0);
                }

/* 70 */        public CLongPointer put(long l) {
/* 71 */            return put(0, l);
                }
            }
