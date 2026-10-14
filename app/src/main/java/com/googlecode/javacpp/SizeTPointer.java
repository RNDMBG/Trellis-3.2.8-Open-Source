            package com.googlecode.javacpp;

            import com.googlecode.javacpp.annotation.Cast;
            import com.googlecode.javacpp.annotation.Name;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Name({"size_t"})
/* 32 */    public class SizeTPointer extends Pointer {
                private native void allocateArray(int i);

                @Cast({"size_t"})
                public native long get(int i);

                public native SizeTPointer put(int i, long j);




/* 40 */        public SizeTPointer(int size) {
                    try {
/* 43 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 45 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 48 */        public SizeTPointer(Pointer p) {
/* 49 */            super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 53 */        public SizeTPointer position(int position) {
/* 54 */            return (SizeTPointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 57 */        public SizeTPointer limit(int limit) {
/* 58 */            return (SizeTPointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 61 */        public SizeTPointer capacity(int capacity) {
/* 62 */            return (SizeTPointer) super.capacity(capacity);
                }

/* 65 */        public long get() {
/* 66 */            return get(0);
                }

/* 69 */        public SizeTPointer put(long s) {
/* 70 */            return put(0, s);
                }
            }
