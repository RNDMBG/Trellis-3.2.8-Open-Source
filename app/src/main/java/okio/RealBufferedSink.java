            package okio;

            import java.io.IOException;
            import java.io.OutputStream;
            import java.nio.charset.Charset;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    final class RealBufferedSink implements BufferedSink {
                public final Buffer buffer;
                private boolean closed;
                public final Sink sink;

/* 21 */        static /* synthetic */ boolean access$000(RealBufferedSink x0) {
/* 22 */            return x0.closed;
                }

/* 26 */        public RealBufferedSink(Sink sink, Buffer buffer) {
/* 28 */            if (sink == null) {
                        throw new IllegalArgumentException("sink == null");
                    }
/* 29 */            this.buffer = buffer;
/* 30 */            this.sink = sink;
                }

/* 33 */        public RealBufferedSink(Sink sink) {
/* 34 */            this(sink, new Buffer());
                }

                @Override // okio.BufferedSink
/* 37 */        public Buffer buffer() {
/* 38 */            return this.buffer;
                }

                @Override // okio.Sink
/* 42 */        public void write(Buffer source, long byteCount) throws IOException {
/* 43 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 44 */            this.buffer.write(source, byteCount);
/* 45 */            emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 48 */        public BufferedSink write(ByteString byteString) throws IOException {
/* 49 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 50 */            this.buffer.write(byteString);
/* 51 */            return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 54 */        public BufferedSink writeUtf8(String string) throws IOException {
/* 55 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 56 */            this.buffer.writeUtf8(string);
/* 57 */            return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 60 */        public BufferedSink writeString(String string, Charset charset) throws IOException {
/* 61 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 62 */            this.buffer.writeString(string, charset);
/* 63 */            return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 66 */        public BufferedSink write(byte[] source) throws IOException {
/* 67 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 68 */            this.buffer.write(source);
/* 69 */            return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 72 */        public BufferedSink write(byte[] source, int offset, int byteCount) throws IOException {
/* 73 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 74 */            this.buffer.write(source, offset, byteCount);
/* 75 */            return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 78 */        public long writeAll(Source source) throws IOException {
/* 79 */            if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
/* 80 */            long totalBytesRead = 0;
                    while (true) {
/* 81 */                long readCount = source.read(this.buffer, 2048L);
                        if (readCount != -1) {
/* 82 */                    totalBytesRead += readCount;
/* 83 */                    emitCompleteSegments();
                        } else {
/* 85 */                    return totalBytesRead;
                        }
                    }
                }

                @Override // okio.BufferedSink
/* 88 */        public BufferedSink writeByte(int b) throws IOException {
/* 89 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 90 */            this.buffer.writeByte(b);
/* 91 */            return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 94 */        public BufferedSink writeShort(int s) throws IOException {
/* 95 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 96 */            this.buffer.writeShort(s);
/* 97 */            return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 100 */       public BufferedSink writeShortLe(int s) throws IOException {
/* 101 */           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 102 */           this.buffer.writeShortLe(s);
/* 103 */           return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 106 */       public BufferedSink writeInt(int i) throws IOException {
/* 107 */           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 108 */           this.buffer.writeInt(i);
/* 109 */           return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 112 */       public BufferedSink writeIntLe(int i) throws IOException {
/* 113 */           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 114 */           this.buffer.writeIntLe(i);
/* 115 */           return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 118 */       public BufferedSink writeLong(long v) throws IOException {
/* 119 */           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 120 */           this.buffer.writeLong(v);
/* 121 */           return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 124 */       public BufferedSink writeLongLe(long v) throws IOException {
/* 125 */           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 126 */           this.buffer.writeLongLe(v);
/* 127 */           return emitCompleteSegments();
                }

                @Override // okio.BufferedSink
/* 130 */       public BufferedSink emitCompleteSegments() throws IOException {
/* 131 */           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
/* 132 */           long byteCount = this.buffer.completeSegmentByteCount();
                    if (byteCount > 0) {
                        this.sink.write(this.buffer, byteCount);
                    }
/* 134 */           return this;
                }

                /* JADX INFO: renamed from: okio.RealBufferedSink$1 */
/* 136 */       class C13021 extends OutputStream {
/* 137 */           C13021() {
                    }

                    @Override // java.io.OutputStream
/* 139 */           public void write(int b) throws IOException {
/* 140 */               if (RealBufferedSink.access$000(RealBufferedSink.this)) {
                            throw new IOException("closed");
                        }
/* 141 */               RealBufferedSink.this.buffer.writeByte((int) ((byte) b));
/* 142 */               RealBufferedSink.this.emitCompleteSegments();
                    }

                    @Override // java.io.OutputStream
/* 145 */           public void write(byte[] data, int offset, int byteCount) throws IOException {
/* 146 */               if (RealBufferedSink.access$000(RealBufferedSink.this)) {
                            throw new IOException("closed");
                        }
/* 147 */               RealBufferedSink.this.buffer.write(data, offset, byteCount);
/* 148 */               RealBufferedSink.this.emitCompleteSegments();
                    }

                    @Override // java.io.OutputStream, java.io.Flushable
/* 152 */           public void flush() throws IOException {
/* 153 */               if (!RealBufferedSink.access$000(RealBufferedSink.this)) {
/* 154 */                   RealBufferedSink.this.flush();
                        }
                    }

                    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 158 */           public void close() throws IOException {
/* 159 */               RealBufferedSink.this.close();
                    }

/* 162 */           public String toString() {
/* 163 */               return RealBufferedSink.this + ".outputStream()";
                    }
                }

                @Override // okio.BufferedSink
/* 137 */       public OutputStream outputStream() {
/* 138 */           return new C13021();
                }

                @Override // okio.Sink
/* 168 */       public void flush() throws IOException {
/* 169 */           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
                    if (this.buffer.size > 0) {
/* 171 */               this.sink.write(this.buffer, this.buffer.size);
                    }
/* 173 */           this.sink.flush();
                }

                @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 176 */       public void close() throws IOException {
/* 177 */           if (!this.closed) {
/* 181 */               Throwable thrown = null;
                        try {
                            if (this.buffer.size > 0) {
/* 184 */                       this.sink.write(this.buffer, this.buffer.size);
                            }
                        } catch (Throwable e) {
/* 187 */                   thrown = e;
                        }
                        try {
/* 191 */                   this.sink.close();
                        } catch (Throwable e2) {
/* 193 */                   if (thrown == null) {
                                thrown = e2;
                            }
                        }
/* 195 */               this.closed = true;
/* 197 */               if (thrown != null) {
                            if (thrown instanceof IOException) {
                                throw (IOException) thrown;
                            }
                            throw new IOException(thrown);
                        }
                    }
                }

                @Override // okio.Sink
/* 200 */       public Timeout timeout() {
/* 201 */           return this.sink.timeout();
                }

/* 204 */       public String toString() {
/* 205 */           return "buffer(" + this.sink + ")";
                }
            }
