            package okio;

            import java.io.IOException;
            import java.io.OutputStream;
            import java.nio.charset.Charset;
            
            
    final class RealBufferedSink implements BufferedSink {
                public final Buffer buffer;
                private boolean closed;
                public final Sink sink;

        static  boolean access$000(RealBufferedSink x0) {
            return x0.closed;
                }

        public RealBufferedSink(Sink sink, Buffer buffer) {
            if (sink == null) {
                        throw new IllegalArgumentException("sink == null");
                    }
            this.buffer = buffer;
            this.sink = sink;
                }

        public RealBufferedSink(Sink sink) {
            this(sink, new Buffer());
                }

                @Override 
        public Buffer buffer() {
            return this.buffer;
                }

                @Override 
        public void write(Buffer source, long byteCount) throws IOException {
            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
            this.buffer.write(source, byteCount);
            emitCompleteSegments();
                }

                @Override 
        public BufferedSink write(ByteString byteString) throws IOException {
            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
            this.buffer.write(byteString);
            return emitCompleteSegments();
                }

                @Override 
        public BufferedSink writeUtf8(String string) throws IOException {
            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
            this.buffer.writeUtf8(string);
            return emitCompleteSegments();
                }

                @Override 
        public BufferedSink writeString(String string, Charset charset) throws IOException {
            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
            this.buffer.writeString(string, charset);
            return emitCompleteSegments();
                }

                @Override 
        public BufferedSink write(byte[] source) throws IOException {
            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
            this.buffer.write(source);
            return emitCompleteSegments();
                }

                @Override 
        public BufferedSink write(byte[] source, int offset, int byteCount) throws IOException {
            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
            this.buffer.write(source, offset, byteCount);
            return emitCompleteSegments();
                }

                @Override 
        public long writeAll(Source source) throws IOException {
            if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
            long totalBytesRead = 0;
                    while (true) {
                long readCount = source.read(this.buffer, 2048L);
                        if (readCount != -1) {
                    totalBytesRead += readCount;
                    emitCompleteSegments();
                        } else {
                    return totalBytesRead;
                        }
                    }
                }

                @Override 
        public BufferedSink writeByte(int b) throws IOException {
            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
            this.buffer.writeByte(b);
            return emitCompleteSegments();
                }

                @Override 
        public BufferedSink writeShort(int s) throws IOException {
            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
            this.buffer.writeShort(s);
            return emitCompleteSegments();
                }

                @Override 
       public BufferedSink writeShortLe(int s) throws IOException {
           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
           this.buffer.writeShortLe(s);
           return emitCompleteSegments();
                }

                @Override 
       public BufferedSink writeInt(int i) throws IOException {
           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
           this.buffer.writeInt(i);
           return emitCompleteSegments();
                }

                @Override 
       public BufferedSink writeIntLe(int i) throws IOException {
           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
           this.buffer.writeIntLe(i);
           return emitCompleteSegments();
                }

                @Override 
       public BufferedSink writeLong(long v) throws IOException {
           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
           this.buffer.writeLong(v);
           return emitCompleteSegments();
                }

                @Override 
       public BufferedSink writeLongLe(long v) throws IOException {
           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
           this.buffer.writeLongLe(v);
           return emitCompleteSegments();
                }

                @Override 
       public BufferedSink emitCompleteSegments() throws IOException {
           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
           long byteCount = this.buffer.completeSegmentByteCount();
                    if (byteCount > 0) {
                        this.sink.write(this.buffer, byteCount);
                    }
           return this;
                }

                
       class C13021 extends OutputStream {
           C13021() {
                    }

                    @Override 
           public void write(int b) throws IOException {
               if (RealBufferedSink.access$000(RealBufferedSink.this)) {
                            throw new IOException("closed");
                        }
               RealBufferedSink.this.buffer.writeByte((int) ((byte) b));
               RealBufferedSink.this.emitCompleteSegments();
                    }

                    @Override 
           public void write(byte[] data, int offset, int byteCount) throws IOException {
               if (RealBufferedSink.access$000(RealBufferedSink.this)) {
                            throw new IOException("closed");
                        }
               RealBufferedSink.this.buffer.write(data, offset, byteCount);
               RealBufferedSink.this.emitCompleteSegments();
                    }

                    @Override 
           public void flush() throws IOException {
               if (!RealBufferedSink.access$000(RealBufferedSink.this)) {
                   RealBufferedSink.this.flush();
                        }
                    }

                    @Override 
           public void close() throws IOException {
               RealBufferedSink.this.close();
                    }

           public String toString() {
               return RealBufferedSink.this + ".outputStream()";
                    }
                }

                @Override 
       public OutputStream outputStream() {
           return new C13021();
                }

                @Override 
       public void flush() throws IOException {
           if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
                    if (this.buffer.size > 0) {
               this.sink.write(this.buffer, this.buffer.size);
                    }
           this.sink.flush();
                }

                @Override 
       public void close() throws IOException {
           if (!this.closed) {
               Throwable thrown = null;
                        try {
                            if (this.buffer.size > 0) {
                       this.sink.write(this.buffer, this.buffer.size);
                            }
                        } catch (Throwable e) {
                   thrown = e;
                        }
                        try {
                   this.sink.close();
                        } catch (Throwable e2) {
                   if (thrown == null) {
                                thrown = e2;
                            }
                        }
               this.closed = true;
               if (thrown != null) {
                            if (thrown instanceof IOException) {
                                throw (IOException) thrown;
                            }
                            throw new IOException(thrown);
                        }
                    }
                }

                @Override 
       public Timeout timeout() {
           return this.sink.timeout();
                }

       public String toString() {
           return "buffer(" + this.sink + ")";
                }
            }
