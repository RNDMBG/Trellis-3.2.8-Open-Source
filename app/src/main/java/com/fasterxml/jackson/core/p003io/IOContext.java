            package com.fasterxml.jackson.core.p003io;

            import com.fasterxml.jackson.core.JsonEncoding;
            import com.fasterxml.jackson.core.util.BufferRecycler;
            import com.fasterxml.jackson.core.util.TextBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 100 */   public class IOContext {
                protected final BufferRecycler _bufferRecycler;
                protected JsonEncoding _encoding;
                protected final boolean _managedResource;
                protected final Object _sourceRef;
                protected byte[] _readIOBuffer = null;
                protected byte[] _writeEncodingBuffer = null;
                protected byte[] _base64Buffer = null;
                protected char[] _tokenCBuffer = null;
                protected char[] _concatCBuffer = null;
                protected char[] _nameCopyBuffer = null;

/* 101 */       public IOContext(BufferRecycler bufferRecycler, Object obj, boolean z) {
/* 103 */           this._bufferRecycler = bufferRecycler;
/* 104 */           this._sourceRef = obj;
/* 105 */           this._managedResource = z;
                }

/* 108 */       public void setEncoding(JsonEncoding jsonEncoding) {
/* 109 */           this._encoding = jsonEncoding;
                }

/* 117 */       public Object getSourceReference() {
/* 118 */           return this._sourceRef;
                }

/* 118 */       public JsonEncoding getEncoding() {
/* 119 */           return this._encoding;
                }

/* 119 */       public boolean isResourceManaged() {
/* 120 */           return this._managedResource;
                }

/* 128 */       public TextBuffer constructTextBuffer() {
/* 129 */           return new TextBuffer(this._bufferRecycler);
                }

/* 137 */       public byte[] allocReadIOBuffer() {
/* 138 */           _verifyAlloc(this._readIOBuffer);
/* 139 */           byte[] bArrAllocByteBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER);
                    this._readIOBuffer = bArrAllocByteBuffer;
                    return bArrAllocByteBuffer;
                }

/* 145 */       public byte[] allocReadIOBuffer(int i) {
/* 146 */           _verifyAlloc(this._readIOBuffer);
/* 147 */           byte[] bArrAllocByteBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER, i);
                    this._readIOBuffer = bArrAllocByteBuffer;
                    return bArrAllocByteBuffer;
                }

/* 150 */       public byte[] allocWriteEncodingBuffer() {
/* 151 */           _verifyAlloc(this._writeEncodingBuffer);
/* 152 */           byte[] bArrAllocByteBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER);
                    this._writeEncodingBuffer = bArrAllocByteBuffer;
                    return bArrAllocByteBuffer;
                }

/* 158 */       public byte[] allocWriteEncodingBuffer(int i) {
/* 159 */           _verifyAlloc(this._writeEncodingBuffer);
/* 160 */           byte[] bArrAllocByteBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER, i);
                    this._writeEncodingBuffer = bArrAllocByteBuffer;
                    return bArrAllocByteBuffer;
                }

/* 166 */       public byte[] allocBase64Buffer() {
/* 167 */           _verifyAlloc(this._base64Buffer);
/* 168 */           byte[] bArrAllocByteBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.BASE64_CODEC_BUFFER);
                    this._base64Buffer = bArrAllocByteBuffer;
                    return bArrAllocByteBuffer;
                }

/* 171 */       public char[] allocTokenBuffer() {
/* 172 */           _verifyAlloc(this._tokenCBuffer);
/* 173 */           char[] cArrAllocCharBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.TOKEN_BUFFER);
                    this._tokenCBuffer = cArrAllocCharBuffer;
                    return cArrAllocCharBuffer;
                }

/* 176 */       public char[] allocConcatBuffer() {
/* 177 */           _verifyAlloc(this._concatCBuffer);
/* 178 */           char[] cArrAllocCharBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.CONCAT_BUFFER);
                    this._concatCBuffer = cArrAllocCharBuffer;
                    return cArrAllocCharBuffer;
                }

/* 181 */       public char[] allocNameCopyBuffer(int i) {
/* 182 */           _verifyAlloc(this._nameCopyBuffer);
/* 183 */           char[] cArrAllocCharBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.NAME_COPY_BUFFER, i);
                    this._nameCopyBuffer = cArrAllocCharBuffer;
                    return cArrAllocCharBuffer;
                }

/* 190 */       public void releaseReadIOBuffer(byte[] bArr) {
/* 191 */           if (bArr != null) {
/* 195 */               _verifyRelease(bArr, this._readIOBuffer);
/* 196 */               this._readIOBuffer = null;
/* 197 */               this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER, bArr);
                    }
                }

/* 201 */       public void releaseWriteEncodingBuffer(byte[] bArr) {
/* 202 */           if (bArr != null) {
/* 206 */               _verifyRelease(bArr, this._writeEncodingBuffer);
/* 207 */               this._writeEncodingBuffer = null;
/* 208 */               this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER, bArr);
                    }
                }

/* 212 */       public void releaseBase64Buffer(byte[] bArr) {
/* 213 */           if (bArr != null) {
/* 214 */               _verifyRelease(bArr, this._base64Buffer);
/* 215 */               this._base64Buffer = null;
/* 216 */               this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.BASE64_CODEC_BUFFER, bArr);
                    }
                }

/* 220 */       public void releaseTokenBuffer(char[] cArr) {
/* 221 */           if (cArr != null) {
/* 222 */               _verifyRelease(cArr, this._tokenCBuffer);
/* 223 */               this._tokenCBuffer = null;
/* 224 */               this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.TOKEN_BUFFER, cArr);
                    }
                }

/* 228 */       public void releaseConcatBuffer(char[] cArr) {
/* 229 */           if (cArr != null) {
/* 231 */               _verifyRelease(cArr, this._concatCBuffer);
/* 232 */               this._concatCBuffer = null;
/* 233 */               this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.CONCAT_BUFFER, cArr);
                    }
                }

/* 237 */       public void releaseNameCopyBuffer(char[] cArr) {
/* 238 */           if (cArr != null) {
/* 240 */               _verifyRelease(cArr, this._nameCopyBuffer);
/* 241 */               this._nameCopyBuffer = null;
/* 242 */               this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.NAME_COPY_BUFFER, cArr);
                    }
                }

/* 252 */       protected void _verifyAlloc(Object obj) {
/* 253 */           if (obj != null) {
                        throw new IllegalStateException("Trying to call same allocXxx() method second time");
                    }
                }

/* 256 */       protected void _verifyRelease(byte[] bArr, byte[] bArr2) {
/* 257 */           if (bArr != bArr2 && bArr.length <= bArr2.length) {
                        throw wrongBuf();
                    }
                }

/* 260 */       protected void _verifyRelease(char[] cArr, char[] cArr2) {
/* 261 */           if (cArr != cArr2 && cArr.length <= cArr2.length) {
                        throw wrongBuf();
                    }
                }

/* 263 */       private IllegalArgumentException wrongBuf() {
/* 264 */           return new IllegalArgumentException("Trying to release buffer not owned by the context");
                }
            }
