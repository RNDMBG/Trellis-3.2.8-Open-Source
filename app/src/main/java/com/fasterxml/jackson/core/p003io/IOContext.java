            package com.fasterxml.jackson.core.p003io;

            import com.fasterxml.jackson.core.JsonEncoding;
            import com.fasterxml.jackson.core.util.BufferRecycler;
            import com.fasterxml.jackson.core.util.TextBuffer;
            
            
   public class IOContext {
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

       public IOContext(BufferRecycler bufferRecycler, Object obj, boolean z) {
           this._bufferRecycler = bufferRecycler;
           this._sourceRef = obj;
           this._managedResource = z;
                }

       public void setEncoding(JsonEncoding jsonEncoding) {
           this._encoding = jsonEncoding;
                }

       public Object getSourceReference() {
           return this._sourceRef;
                }

       public JsonEncoding getEncoding() {
           return this._encoding;
                }

       public boolean isResourceManaged() {
           return this._managedResource;
                }

       public TextBuffer constructTextBuffer() {
           return new TextBuffer(this._bufferRecycler);
                }

       public byte[] allocReadIOBuffer() {
           _verifyAlloc(this._readIOBuffer);
           byte[] bArrAllocByteBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER);
                    this._readIOBuffer = bArrAllocByteBuffer;
                    return bArrAllocByteBuffer;
                }

       public byte[] allocReadIOBuffer(int i) {
           _verifyAlloc(this._readIOBuffer);
           byte[] bArrAllocByteBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER, i);
                    this._readIOBuffer = bArrAllocByteBuffer;
                    return bArrAllocByteBuffer;
                }

       public byte[] allocWriteEncodingBuffer() {
           _verifyAlloc(this._writeEncodingBuffer);
           byte[] bArrAllocByteBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER);
                    this._writeEncodingBuffer = bArrAllocByteBuffer;
                    return bArrAllocByteBuffer;
                }

       public byte[] allocWriteEncodingBuffer(int i) {
           _verifyAlloc(this._writeEncodingBuffer);
           byte[] bArrAllocByteBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER, i);
                    this._writeEncodingBuffer = bArrAllocByteBuffer;
                    return bArrAllocByteBuffer;
                }

       public byte[] allocBase64Buffer() {
           _verifyAlloc(this._base64Buffer);
           byte[] bArrAllocByteBuffer = this._bufferRecycler.allocByteBuffer(BufferRecycler.ByteBufferType.BASE64_CODEC_BUFFER);
                    this._base64Buffer = bArrAllocByteBuffer;
                    return bArrAllocByteBuffer;
                }

       public char[] allocTokenBuffer() {
           _verifyAlloc(this._tokenCBuffer);
           char[] cArrAllocCharBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.TOKEN_BUFFER);
                    this._tokenCBuffer = cArrAllocCharBuffer;
                    return cArrAllocCharBuffer;
                }

       public char[] allocConcatBuffer() {
           _verifyAlloc(this._concatCBuffer);
           char[] cArrAllocCharBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.CONCAT_BUFFER);
                    this._concatCBuffer = cArrAllocCharBuffer;
                    return cArrAllocCharBuffer;
                }

       public char[] allocNameCopyBuffer(int i) {
           _verifyAlloc(this._nameCopyBuffer);
           char[] cArrAllocCharBuffer = this._bufferRecycler.allocCharBuffer(BufferRecycler.CharBufferType.NAME_COPY_BUFFER, i);
                    this._nameCopyBuffer = cArrAllocCharBuffer;
                    return cArrAllocCharBuffer;
                }

       public void releaseReadIOBuffer(byte[] bArr) {
           if (bArr != null) {
               _verifyRelease(bArr, this._readIOBuffer);
               this._readIOBuffer = null;
               this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.READ_IO_BUFFER, bArr);
                    }
                }

       public void releaseWriteEncodingBuffer(byte[] bArr) {
           if (bArr != null) {
               _verifyRelease(bArr, this._writeEncodingBuffer);
               this._writeEncodingBuffer = null;
               this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.WRITE_ENCODING_BUFFER, bArr);
                    }
                }

       public void releaseBase64Buffer(byte[] bArr) {
           if (bArr != null) {
               _verifyRelease(bArr, this._base64Buffer);
               this._base64Buffer = null;
               this._bufferRecycler.releaseByteBuffer(BufferRecycler.ByteBufferType.BASE64_CODEC_BUFFER, bArr);
                    }
                }

       public void releaseTokenBuffer(char[] cArr) {
           if (cArr != null) {
               _verifyRelease(cArr, this._tokenCBuffer);
               this._tokenCBuffer = null;
               this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.TOKEN_BUFFER, cArr);
                    }
                }

       public void releaseConcatBuffer(char[] cArr) {
           if (cArr != null) {
               _verifyRelease(cArr, this._concatCBuffer);
               this._concatCBuffer = null;
               this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.CONCAT_BUFFER, cArr);
                    }
                }

       public void releaseNameCopyBuffer(char[] cArr) {
           if (cArr != null) {
               _verifyRelease(cArr, this._nameCopyBuffer);
               this._nameCopyBuffer = null;
               this._bufferRecycler.releaseCharBuffer(BufferRecycler.CharBufferType.NAME_COPY_BUFFER, cArr);
                    }
                }

       protected void _verifyAlloc(Object obj) {
           if (obj != null) {
                        throw new IllegalStateException("Trying to call same allocXxx() method second time");
                    }
                }

       protected void _verifyRelease(byte[] bArr, byte[] bArr2) {
           if (bArr != bArr2 && bArr.length <= bArr2.length) {
                        throw wrongBuf();
                    }
                }

       protected void _verifyRelease(char[] cArr, char[] cArr2) {
           if (cArr != cArr2 && cArr.length <= cArr2.length) {
                        throw wrongBuf();
                    }
                }

       private IllegalArgumentException wrongBuf() {
           return new IllegalArgumentException("Trying to release buffer not owned by the context");
                }
            }
