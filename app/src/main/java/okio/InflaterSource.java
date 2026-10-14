            package okio;

            import java.io.EOFException;
            import java.io.IOException;
            import java.util.zip.DataFormatException;
            import java.util.zip.Inflater;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public final class InflaterSource implements Source {
                private int bufferBytesHeldByInflater;
                private boolean closed;
                private final Inflater inflater;
                private final BufferedSource source;

/* 39 */        public InflaterSource(Source source, Inflater inflater) {
/* 40 */            this(Okio.buffer(source), inflater);
                }

/* 47 */        InflaterSource(BufferedSource source, Inflater inflater) {
/* 49 */            if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
/* 50 */            if (inflater == null) {
                        throw new IllegalArgumentException("inflater == null");
                    }
/* 51 */            this.source = source;
/* 52 */            this.inflater = inflater;
                }

                @Override // okio.Source
/* 56 */        public long read(Buffer sink, long byteCount) throws IOException {
                    boolean sourceExhausted;
                    if (byteCount < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                    }
/* 58 */            if (this.closed) {
                        throw new IllegalStateException("closed");
                    }
                    if (byteCount == 0) {
                        return 0L;
                    }
/* 77 */            do {
/* 62 */                sourceExhausted = refill();
                        try {
/* 66 */                    Segment tail = sink.writableSegment(1);
/* 67 */                    int bytesInflated = this.inflater.inflate(tail.data, tail.limit, 2048 - tail.limit);
/* 68 */                    if (bytesInflated > 0) {
/* 69 */                        tail.limit += bytesInflated;
/* 70 */                        sink.size += (long) bytesInflated;
/* 71 */                        return bytesInflated;
                            }
/* 73 */                    if (this.inflater.finished() || this.inflater.needsDictionary()) {
/* 74 */                        releaseInflatedBytes();
/* 75 */                        return -1L;
                            }
                        } catch (DataFormatException e) {
/* 79 */                    throw new IOException(e);
                        }
/* 77 */            } while (!sourceExhausted);
                    throw new EOFException("source exhausted prematurely");
                }

/* 89 */        public boolean refill() throws IOException {
/* 90 */            if (!this.inflater.needsInput()) {
                        return false;
                    }
/* 92 */            releaseInflatedBytes();
/* 93 */            if (this.inflater.getRemaining() != 0) {
                        throw new IllegalStateException("?");
                    }
/* 96 */            if (this.source.exhausted()) {
                        return true;
                    }
/* 99 */            Segment head = this.source.buffer().head;
/* 100 */           this.bufferBytesHeldByInflater = head.limit - head.pos;
/* 101 */           this.inflater.setInput(head.data, head.pos, this.bufferBytesHeldByInflater);
                    return false;
                }

/* 106 */       private void releaseInflatedBytes() throws IOException {
/* 107 */           if (this.bufferBytesHeldByInflater != 0) {
/* 108 */               int toRelease = this.bufferBytesHeldByInflater - this.inflater.getRemaining();
/* 109 */               this.bufferBytesHeldByInflater -= toRelease;
/* 110 */               this.source.skip(toRelease);
                    }
                }

                @Override // okio.Source
/* 113 */       public Timeout timeout() {
/* 114 */           return this.source.timeout();
                }

                @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 117 */       public void close() throws IOException {
/* 118 */           if (!this.closed) {
/* 119 */               this.inflater.end();
/* 120 */               this.closed = true;
/* 121 */               this.source.close();
                    }
                }
            }
