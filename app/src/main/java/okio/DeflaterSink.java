            package okio;

            import java.io.IOException;
            import java.util.zip.Deflater;
            
            
    public final class DeflaterSink implements Sink {
                private boolean closed;
                private final Deflater deflater;
                private final BufferedSink sink;

        public DeflaterSink(Sink sink, Deflater deflater) {
            this(Okio.buffer(sink), deflater);
                }

        DeflaterSink(BufferedSink sink, Deflater deflater) {
            if (sink == null) {
                        throw new IllegalArgumentException("source == null");
                    }
            if (deflater == null) {
                        throw new IllegalArgumentException("inflater == null");
                    }
            this.sink = sink;
            this.deflater = deflater;
                }

                @Override 
        public void write(Buffer source, long byteCount) throws IOException {
            Util.checkOffsetAndCount(source.size, 0L, byteCount);
                    while (byteCount > 0) {
                Segment head = source.head;
                int toDeflate = (int) Math.min(byteCount, head.limit - head.pos);
                this.deflater.setInput(head.data, head.pos, toDeflate);
                deflate(false);
                source.size -= (long) toDeflate;
                head.pos += toDeflate;
                if (head.pos == head.limit) {
                    source.head = head.pop();
                    SegmentPool.INSTANCE.recycle(head);
                        }
                byteCount -= (long) toDeflate;
                    }
                }

        private void deflate(boolean syncFlush) throws IOException {
                    int deflated;
            Buffer buffer = this.sink.buffer();
                    while (true) {
                Segment s = buffer.writableSegment(1);
                if (syncFlush) {
                    deflated = this.deflater.deflate(s.data, s.limit, 2048 - s.limit, 2);
                        } else {
                    deflated = this.deflater.deflate(s.data, s.limit, 2048 - s.limit);
                        }
                if (deflated > 0) {
                    s.limit += deflated;
                    buffer.size += (long) deflated;
                   this.sink.emitCompleteSegments();
                        } else if (this.deflater.needsInput()) {
                   return;
                        }
                    }
                }

                @Override 
       public void flush() throws IOException {
           deflate(true);
           this.sink.flush();
                }

       void finishDeflate() throws IOException {
           this.deflater.finish();
           deflate(false);
                }

                @Override 
       public void close() throws IOException {
           if (!this.closed) {
               Throwable thrown = null;
                        try {
                   finishDeflate();
                        } catch (Throwable e) {
                   thrown = e;
                        }
                        try {
                   this.deflater.end();
                        } catch (Throwable e2) {
                   if (thrown == null) {
                                thrown = e2;
                            }
                        }
                        try {
                   this.sink.close();
                        } catch (Throwable e3) {
                   if (thrown == null) {
                                thrown = e3;
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
           return "DeflaterSink(" + this.sink + ")";
                }
            }
