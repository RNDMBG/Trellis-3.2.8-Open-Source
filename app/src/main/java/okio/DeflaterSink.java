            package okio;

            import java.io.IOException;
            import java.util.zip.Deflater;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public final class DeflaterSink implements Sink {
                private boolean closed;
                private final Deflater deflater;
                private final BufferedSink sink;

/* 43 */        public DeflaterSink(Sink sink, Deflater deflater) {
/* 44 */            this(Okio.buffer(sink), deflater);
                }

/* 51 */        DeflaterSink(BufferedSink sink, Deflater deflater) {
/* 53 */            if (sink == null) {
                        throw new IllegalArgumentException("source == null");
                    }
/* 54 */            if (deflater == null) {
                        throw new IllegalArgumentException("inflater == null");
                    }
/* 55 */            this.sink = sink;
/* 56 */            this.deflater = deflater;
                }

                @Override // okio.Sink
/* 60 */        public void write(Buffer source, long byteCount) throws IOException {
/* 61 */            Util.checkOffsetAndCount(source.size, 0L, byteCount);
                    while (byteCount > 0) {
/* 64 */                Segment head = source.head;
/* 65 */                int toDeflate = (int) Math.min(byteCount, head.limit - head.pos);
/* 66 */                this.deflater.setInput(head.data, head.pos, toDeflate);
/* 69 */                deflate(false);
/* 72 */                source.size -= (long) toDeflate;
/* 73 */                head.pos += toDeflate;
/* 74 */                if (head.pos == head.limit) {
/* 75 */                    source.head = head.pop();
/* 76 */                    SegmentPool.INSTANCE.recycle(head);
                        }
/* 79 */                byteCount -= (long) toDeflate;
                    }
                }

/* 84 */        private void deflate(boolean syncFlush) throws IOException {
                    int deflated;
/* 85 */            Buffer buffer = this.sink.buffer();
                    while (true) {
/* 87 */                Segment s = buffer.writableSegment(1);
/* 93 */                if (syncFlush) {
/* 94 */                    deflated = this.deflater.deflate(s.data, s.limit, 2048 - s.limit, 2);
                        } else {
/* 95 */                    deflated = this.deflater.deflate(s.data, s.limit, 2048 - s.limit);
                        }
/* 97 */                if (deflated > 0) {
/* 98 */                    s.limit += deflated;
/* 99 */                    buffer.size += (long) deflated;
/* 100 */                   this.sink.emitCompleteSegments();
                        } else if (this.deflater.needsInput()) {
/* 102 */                   return;
                        }
                    }
                }

                @Override // okio.Sink
/* 107 */       public void flush() throws IOException {
/* 108 */           deflate(true);
/* 109 */           this.sink.flush();
                }

/* 112 */       void finishDeflate() throws IOException {
/* 113 */           this.deflater.finish();
/* 114 */           deflate(false);
                }

                @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 117 */       public void close() throws IOException {
/* 118 */           if (!this.closed) {
/* 122 */               Throwable thrown = null;
                        try {
/* 124 */                   finishDeflate();
                        } catch (Throwable e) {
/* 126 */                   thrown = e;
                        }
                        try {
/* 130 */                   this.deflater.end();
                        } catch (Throwable e2) {
/* 132 */                   if (thrown == null) {
                                thrown = e2;
                            }
                        }
                        try {
/* 136 */                   this.sink.close();
                        } catch (Throwable e3) {
/* 138 */                   if (thrown == null) {
                                thrown = e3;
                            }
                        }
/* 140 */               this.closed = true;
/* 142 */               if (thrown != null) {
                            if (thrown instanceof IOException) {
                                throw (IOException) thrown;
                            }
                            throw new IOException(thrown);
                        }
                    }
                }

                @Override // okio.Sink
/* 145 */       public Timeout timeout() {
/* 146 */           return this.sink.timeout();
                }

/* 149 */       public String toString() {
/* 150 */           return "DeflaterSink(" + this.sink + ")";
                }
            }
