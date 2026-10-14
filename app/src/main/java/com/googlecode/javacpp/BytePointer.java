            package com.googlecode.javacpp;

            import java.io.UnsupportedEncodingException;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class BytePointer extends Pointer {
                private native void allocateArray(int i);

                public native byte get(int i);

                public native BytePointer get(byte[] bArr, int i, int i2);

                public native BytePointer put(int i, byte b);

                public native BytePointer put(byte[] bArr, int i, int i2);





/* 43 */        public BytePointer(String s, String charsetName) throws UnsupportedEncodingException {
/* 44 */            this(s.getBytes(charsetName).length + 1);
/* 45 */            putString(s, charsetName);
                }

/* 54 */        public BytePointer(String s) {
/* 55 */            this(s.getBytes().length + 1);
/* 56 */            putString(s);
                }

/* 64 */        public BytePointer(byte... array) {
/* 65 */            this(array.length);
/* 66 */            put(array);
                }

/* 75 */        public BytePointer(ByteBuffer buffer) {
/* 76 */            super(buffer);
/* 77 */            if (buffer != null && buffer.hasArray()) {
/* 78 */                byte[] array = buffer.array();
/* 79 */                allocateArray(array.length);
/* 80 */                put(array);
/* 81 */                position(buffer.position());
/* 82 */                limit(buffer.limit());
                    }
                }

/* 89 */        public BytePointer(int size) {
                    try {
/* 92 */                allocateArray(size);
                    } catch (UnsatisfiedLinkError e) {
/* 94 */                throw new RuntimeException("No native JavaCPP library in memory. (Has Loader.load() been called?)", e);
                    }
                }

/* 97 */        public BytePointer(Pointer p) {
/* 98 */            super(p);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 102 */       public BytePointer position(int position) {
/* 103 */           return (BytePointer) super.position(position);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 106 */       public BytePointer limit(int limit) {
/* 107 */           return (BytePointer) super.limit(limit);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 110 */       public BytePointer capacity(int capacity) {
/* 111 */           return (BytePointer) super.capacity(capacity);
                }

/* 116 */       public byte[] getStringBytes() {
/* 117 */           byte[] buffer = new byte[16];
/* 118 */           int i = 0;
                    int j = position();
                    while (true) {
/* 119 */               byte b = position(j).get();
                        buffer[i] = b;
                        if (b != 0) {
/* 120 */                   i++;
                            j++;
/* 121 */                   if (i >= buffer.length) {
/* 122 */                       byte[] newbuffer = new byte[buffer.length * 2];
/* 123 */                       System.arraycopy(buffer, 0, newbuffer, 0, buffer.length);
/* 124 */                       buffer = newbuffer;
                            }
                        } else {
/* 127 */                   byte[] newbuffer2 = new byte[i];
/* 128 */                   System.arraycopy(buffer, 0, newbuffer2, 0, i);
/* 129 */                   return newbuffer2;
                        }
                    }
                }

/* 138 */       public String getString(String charsetName) throws UnsupportedEncodingException {
/* 139 */           return new String(getStringBytes(), charsetName);
                }

/* 145 */       public String getString() {
/* 146 */           return new String(getStringBytes());
                }

/* 161 */       public BytePointer putString(String s, String charsetName) throws UnsupportedEncodingException {
/* 162 */           byte[] bytes = s.getBytes(charsetName);
/* 164 */           put(bytes).put(bytes.length, (byte) 0);
/* 165 */           return this;
                }

/* 176 */       public BytePointer putString(String s) {
/* 177 */           byte[] bytes = s.getBytes();
/* 178 */           return put(bytes).put(bytes.length, (byte) 0);
                }

/* 181 */       public byte get() {
/* 182 */           return get(0);
                }

/* 185 */       public BytePointer put(byte b) {
/* 186 */           return put(0, b);
                }

/* 196 */       public BytePointer get(byte[] array) {
/* 197 */           return get(array, 0, array.length);
                }

/* 198 */       public BytePointer put(byte[] array) {
/* 199 */           return put(array, 0, array.length);
                }

                @Override // com.googlecode.javacpp.Pointer
/* 220 */       public final ByteBuffer asBuffer() {
/* 221 */           return asByteBuffer();
                }
            }
