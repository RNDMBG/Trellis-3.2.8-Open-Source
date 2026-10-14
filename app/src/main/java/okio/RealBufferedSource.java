            package okio;

            import com.flurry.android.Constants;
            import java.io.EOFException;
            import java.io.IOException;
            import java.io.InputStream;
            import java.nio.charset.Charset;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    final class RealBufferedSource implements BufferedSource {
                public final Buffer buffer;
                private boolean closed;
                public final Source source;

/* 24 */        static /* synthetic */ boolean access$000(RealBufferedSource x0) {
/* 25 */            return x0.closed;
                }

/* 29 */        public RealBufferedSource(Source source, Buffer buffer) {
/* 31 */            if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
/* 32 */            this.buffer = buffer;
/* 33 */            this.source = source;
                }

/* 36 */        public RealBufferedSource(Source source) {
/* 37 */            this(source, new Buffer());
                }

                @Override // okio.BufferedSource, okio.BufferedSink
/* 40 */        public Buffer buffer() {
/* 41 */            return this.buffer;
                }

                @Override // okio.Source
/* 44 */        public long read(Buffer sink, long byteCount) throws IOException {
/* 45 */            if (sink == null) {
                        throw new IllegalArgumentException("sink == null");
                    }
                    if (byteCount < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                    }
/* 47 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
                    if (this.buffer.size == 0) {
/* 50 */                long read = this.source.read(this.buffer, 2048L);
                        if (read == -1) {
                            return -1L;
                        }
                    }
/* 54 */            long toRead = Math.min(byteCount, this.buffer.size);
/* 55 */            return this.buffer.read(sink, toRead);
                }

                @Override // okio.BufferedSource
/* 58 */        public boolean exhausted() throws IOException {
/* 59 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
                    return this.buffer.exhausted() && this.source.read(this.buffer, 2048L) == -1;
                }

                @Override // okio.BufferedSource
/* 63 */        public void require(long byteCount) throws IOException {
                    if (byteCount < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                    }
/* 65 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
                    while (this.buffer.size < byteCount) {
                        if (this.source.read(this.buffer, 2048L) == -1) {
                            throw new EOFException();
                        }
                    }
                }

                @Override // okio.BufferedSource
/* 71 */        public byte readByte() throws IOException {
/* 72 */            require(1L);
/* 73 */            return this.buffer.readByte();
                }

                @Override // okio.BufferedSource
/* 76 */        public ByteString readByteString() throws IOException {
/* 77 */            this.buffer.writeAll(this.source);
/* 78 */            return this.buffer.readByteString();
                }

                @Override // okio.BufferedSource
/* 81 */        public ByteString readByteString(long byteCount) throws IOException {
/* 82 */            require(byteCount);
/* 83 */            return this.buffer.readByteString(byteCount);
                }

                @Override // okio.BufferedSource
/* 86 */        public byte[] readByteArray() throws IOException {
/* 87 */            this.buffer.writeAll(this.source);
/* 88 */            return this.buffer.readByteArray();
                }

                @Override // okio.BufferedSource
/* 91 */        public byte[] readByteArray(long byteCount) throws IOException {
/* 92 */            require(byteCount);
/* 93 */            return this.buffer.readByteArray(byteCount);
                }

                @Override // okio.BufferedSource
/* 96 */        public int read(byte[] sink) throws IOException {
/* 97 */            return read(sink, 0, sink.length);
                }

                @Override // okio.BufferedSource
/* 101 */       public void readFully(byte[] sink) throws IOException {
                    try {
/* 102 */               require(sink.length);
/* 113 */               this.buffer.readFully(sink);
                    } catch (EOFException e) {
/* 105 */               int offset = 0;
                        while (this.buffer.size > 0) {
/* 107 */                   int read = this.buffer.read(sink, offset, ((int) this.buffer.size) - offset);
/* 108 */                   if (read == -1) {
                                throw new AssertionError();
                            }
/* 109 */                   offset += read;
                        }
/* 111 */               throw e;
                    }
                }

                @Override // okio.BufferedSource
/* 116 */       public int read(byte[] sink, int offset, int byteCount) throws IOException {
/* 117 */           Util.checkOffsetAndCount(sink.length, offset, byteCount);
                    if (this.buffer.size == 0) {
/* 120 */               long read = this.source.read(this.buffer, 2048L);
                        if (read == -1) {
                            return -1;
                        }
                    }
/* 124 */           int toRead = (int) Math.min(byteCount, this.buffer.size);
/* 125 */           return this.buffer.read(sink, offset, toRead);
                }

                @Override // okio.BufferedSource
/* 129 */       public void readFully(Buffer sink, long byteCount) throws IOException {
                    try {
/* 130 */               require(byteCount);
/* 136 */               this.buffer.readFully(sink, byteCount);
                    } catch (EOFException e) {
/* 133 */               sink.writeAll(this.buffer);
/* 134 */               throw e;
                    }
                }

                @Override // okio.BufferedSource
/* 139 */       public long readAll(Sink sink) throws IOException {
/* 140 */           if (sink == null) {
                        throw new IllegalArgumentException("sink == null");
                    }
/* 142 */           long totalBytesWritten = 0;
                    while (this.source.read(this.buffer, 2048L) != -1) {
/* 144 */               long emitByteCount = this.buffer.completeSegmentByteCount();
                        if (emitByteCount > 0) {
/* 146 */                   totalBytesWritten += emitByteCount;
/* 147 */                   sink.write(this.buffer, emitByteCount);
                        }
                    }
                    if (this.buffer.size() > 0) {
/* 151 */               long totalBytesWritten2 = totalBytesWritten + this.buffer.size();
/* 152 */               sink.write(this.buffer, this.buffer.size());
/* 154 */               return totalBytesWritten2;
                    }
/* 154 */           return totalBytesWritten;
                }

                @Override // okio.BufferedSource
/* 157 */       public String readUtf8() throws IOException {
/* 158 */           this.buffer.writeAll(this.source);
/* 159 */           return this.buffer.readUtf8();
                }

                @Override // okio.BufferedSource
/* 162 */       public String readUtf8(long byteCount) throws IOException {
/* 163 */           require(byteCount);
/* 164 */           return this.buffer.readUtf8(byteCount);
                }

                @Override // okio.BufferedSource
/* 167 */       public String readString(Charset charset) throws IOException {
/* 168 */           if (charset == null) {
                        throw new IllegalArgumentException("charset == null");
                    }
/* 170 */           this.buffer.writeAll(this.source);
/* 171 */           return this.buffer.readString(charset);
                }

                @Override // okio.BufferedSource
/* 174 */       public String readString(long byteCount, Charset charset) throws IOException {
/* 175 */           require(byteCount);
/* 176 */           if (charset == null) {
                        throw new IllegalArgumentException("charset == null");
                    }
/* 177 */           return this.buffer.readString(byteCount, charset);
                }

                @Override // okio.BufferedSource
/* 180 */       public String readUtf8Line() throws IOException {
/* 181 */           long newline = indexOf((byte) 10);
                    if (newline != -1) {
/* 187 */               return this.buffer.readUtf8Line(newline);
                    }
                    if (this.buffer.size != 0) {
                        return readUtf8(this.buffer.size);
                    }
/* 184 */           return null;
                }

                @Override // okio.BufferedSource
/* 190 */       public String readUtf8LineStrict() throws IOException {
/* 191 */           long newline = indexOf((byte) 10);
                    if (newline == -1) {
                        throw new EOFException();
                    }
/* 193 */           return this.buffer.readUtf8Line(newline);
                }

                @Override // okio.BufferedSource
/* 196 */       public short readShort() throws IOException {
/* 197 */           require(2L);
/* 198 */           return this.buffer.readShort();
                }

                @Override // okio.BufferedSource
/* 201 */       public short readShortLe() throws IOException {
/* 202 */           require(2L);
/* 203 */           return this.buffer.readShortLe();
                }

                @Override // okio.BufferedSource
/* 206 */       public int readInt() throws IOException {
/* 207 */           require(4L);
/* 208 */           return this.buffer.readInt();
                }

                @Override // okio.BufferedSource
/* 211 */       public int readIntLe() throws IOException {
/* 212 */           require(4L);
/* 213 */           return this.buffer.readIntLe();
                }

                @Override // okio.BufferedSource
/* 216 */       public long readLong() throws IOException {
/* 217 */           require(8L);
/* 218 */           return this.buffer.readLong();
                }

                @Override // okio.BufferedSource
/* 221 */       public long readLongLe() throws IOException {
/* 222 */           require(8L);
/* 223 */           return this.buffer.readLongLe();
                }

                @Override // okio.BufferedSource
/* 226 */       public void skip(long byteCount) throws IOException {
/* 227 */           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
                    while (byteCount > 0) {
                        if (this.buffer.size == 0 && this.source.read(this.buffer, 2048L) == -1) {
/* 230 */                   throw new EOFException();
                        }
/* 232 */               long toSkip = Math.min(byteCount, this.buffer.size());
/* 233 */               this.buffer.skip(toSkip);
/* 234 */               byteCount -= toSkip;
                    }
                }

                @Override // okio.BufferedSource
/* 238 */       public long indexOf(byte b) throws IOException {
/* 239 */           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 240 */           long start = 0;
                    do {
/* 242 */               long index = this.buffer.indexOf(b, start);
                        if (index == -1) {
/* 243 */                   start = this.buffer.size;
                        } else {
/* 246 */                   return index;
                        }
                    } while (this.source.read(this.buffer, 2048L) != -1);
                    return -1L;
                }

                /* JADX INFO: renamed from: okio.RealBufferedSource$1 */
/* 248 */       class C13031 extends InputStream {
/* 249 */           C13031() {
                    }

                    @Override // java.io.InputStream
/* 251 */           public int read() throws IOException {
/* 252 */               if (RealBufferedSource.access$000(RealBufferedSource.this)) {
                            throw new IOException("closed");
                        }
                        if (RealBufferedSource.this.buffer.size == 0) {
/* 254 */                   long count = RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 2048L);
                            if (count == -1) {
                                return -1;
                            }
                        }
                        return RealBufferedSource.this.buffer.readByte() & Constants.UNKNOWN;
                    }

                    @Override // java.io.InputStream
/* 260 */           public int read(byte[] data, int offset, int byteCount) throws IOException {
/* 261 */               if (RealBufferedSource.access$000(RealBufferedSource.this)) {
                            throw new IOException("closed");
                        }
/* 262 */               Util.checkOffsetAndCount(data.length, offset, byteCount);
                        if (RealBufferedSource.this.buffer.size == 0) {
/* 265 */                   long count = RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 2048L);
                            if (count == -1) {
                                return -1;
                            }
                        }
                        return RealBufferedSource.this.buffer.read(data, offset, byteCount);
                    }

                    @Override // java.io.InputStream
/* 272 */           public int available() throws IOException {
/* 273 */               if (RealBufferedSource.access$000(RealBufferedSource.this)) {
                            throw new IOException("closed");
                        }
/* 274 */               return (int) Math.min(RealBufferedSource.this.buffer.size, 2147483647L);
                    }

                    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 277 */           public void close() throws IOException {
/* 278 */               RealBufferedSource.this.close();
                    }

/* 281 */           public String toString() {
/* 282 */               return RealBufferedSource.this + ".inputStream()";
                    }
                }

                @Override // okio.BufferedSource
/* 249 */       public InputStream inputStream() {
/* 250 */           return new C13031();
                }

                @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 287 */       public void close() throws IOException {
/* 288 */           if (!this.closed) {
/* 289 */               this.closed = true;
/* 290 */               this.source.close();
/* 291 */               this.buffer.clear();
                    }
                }

                @Override // okio.Source
/* 294 */       public Timeout timeout() {
/* 295 */           return this.source.timeout();
                }

/* 298 */       public String toString() {
/* 299 */           return "buffer(" + this.source + ")";
                }
            }
