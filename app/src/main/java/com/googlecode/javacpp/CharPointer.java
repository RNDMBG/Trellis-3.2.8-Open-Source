            package com.googlecode.javacpp;

            import java.nio.Buffer;
            import java.nio.CharBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class CharPointer extends Pointer {
                private native void allocateArray(int i);

                public native char get(int i);

                public native CharPointer get(char[] cArr, int i, int i2);

                public native CharPointer put(int i, char c);

                public native CharPointer put(char[] cArr, int i, int i2);





/* 38 */        public CharPointer(String s) {
/* 39 */            this(s.toCharArray().length + 1);
/* 40 */            putString(s);
                }

/* 48 */        public CharPointer(char... array) {
/* 49 */            this(array.length);
/* 50 */            put(array);
                }

/* 59 */        public CharPointer(CharBuffer buffer) {
/* 60 */            super(buffer);
/* 61 */            if (buffer != null && buffer.hasArray()) {
/* 62 */                char[] array = buffer.array();
/* 63 */                allocateArray(array.length);
/* 64 */                put(array);
/* 65 */                position(buffer.position());
/* 66 */                limit(buffer.limit());
                    }
                }

/* 73 */        public CharPointer(int size) {
                    try {
/* 76 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 78 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 81 */        public CharPointer(Pointer p) {
/* 82 */            super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 86 */        public CharPointer position(int position) {
/* 87 */            return (CharPointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 90 */        public CharPointer limit(int limit) {
/* 91 */            return (CharPointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 94 */        public CharPointer capacity(int capacity) {
/* 95 */            return (CharPointer) super.capacity(capacity);
                }

/* 100 */       public char[] getStringChars() {
/* 101 */           char[] buffer = new char[16];
/* 102 */           int i = 0;
                    int j = position();
                    while (true) {
/* 103 */               char c = position(j).get();
                        buffer[i] = c;
                        if (c != 0) {
/* 104 */                   i++;
                            j++;
/* 105 */                   if (i >= buffer.length) {
/* 106 */                       char[] newbuffer = new char[buffer.length * 2];
/* 107 */                       System.arraycopy(buffer, 0, newbuffer, 0, buffer.length);
/* 108 */                       buffer = newbuffer;
                            }
                        } else {
/* 111 */                   char[] newbuffer2 = new char[i];
/* 112 */                   System.arraycopy(buffer, 0, newbuffer2, 0, i);
/* 113 */                   return newbuffer2;
                        }
                    }
                }

/* 116 */       public String getString() {
/* 117 */           return new String(getStringChars());
                }

/* 127 */       public CharPointer putString(String s) {
/* 128 */           char[] chars = s.toCharArray();
/* 129 */           return put(chars).put(chars.length, (char) 0);
                }

/* 132 */       public char get() {
/* 133 */           return get(0);
                }

/* 136 */       public CharPointer put(char c) {
/* 137 */           return put(0, c);
                }

/* 147 */       public CharPointer get(char[] array) {
/* 148 */           return get(array, 0, array.length);
                }

/* 149 */       public CharPointer put(char[] array) {
/* 150 */           return put(array, 0, array.length);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 171 */       public final CharBuffer asBuffer() {
/* 172 */           return asByteBuffer().asCharBuffer();
                }
            }
