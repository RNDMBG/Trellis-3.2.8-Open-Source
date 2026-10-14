            package com.googlecode.javacpp;

            import com.googlecode.javacpp.annotation.Cast;
            import com.googlecode.javacpp.annotation.Name;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Name({"bool"})
/* 32 */    public class BoolPointer extends Pointer {
                private native void allocateArray(int i);

                @Cast({"bool"})
                public native boolean get(int i);

                public native BoolPointer put(int i, boolean z);




/* 40 */        public BoolPointer(int size) {
                    try {
/* 43 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 45 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 48 */        public BoolPointer(Pointer p) {
/* 49 */            super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 53 */        public BoolPointer position(int position) {
/* 54 */            return (BoolPointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 57 */        public BoolPointer limit(int limit) {
/* 58 */            return (BoolPointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 61 */        public BoolPointer capacity(int capacity) {
/* 62 */            return (BoolPointer) super.capacity(capacity);
                }

/* 65 */        public boolean get() {
/* 66 */            return get(0);
                }

/* 69 */        public BoolPointer put(boolean b) {
/* 70 */            return put(0, b);
                }
            }
