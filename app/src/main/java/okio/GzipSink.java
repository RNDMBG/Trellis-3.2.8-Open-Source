            package okio;

            import java.io.IOException;
            import java.util.zip.CRC32;
            import java.util.zip.Deflater;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public final class GzipSink implements Sink {
                private boolean closed;
                private final CRC32 crc = new CRC32();
                private final Deflater deflater;
                private final DeflaterSink deflaterSink;
                private final BufferedSink sink;

/* 55 */        public GzipSink(Sink sink) {
/* 57 */            if (sink == null) {
                        throw new IllegalArgumentException("sink == null");
                    }
/* 58 */            this.deflater = new Deflater(-1, true);
/* 59 */            this.sink = Okio.buffer(sink);
/* 60 */            this.deflaterSink = new DeflaterSink(this.sink, this.deflater);
/* 62 */            writeHeader();
                }

                @Override // okio.Sink
/* 65 */        public void write(Buffer source, long byteCount) throws IOException {
                    if (byteCount < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                    }
                    if (byteCount != 0) {
/* 69 */                updateCrc(source, byteCount);
/* 70 */                this.deflaterSink.write(source, byteCount);
                    }
                }

                @Override // okio.Sink
/* 73 */        public void flush() throws IOException {
/* 74 */            this.deflaterSink.flush();
                }

                @Override // okio.Sink
/* 77 */        public Timeout timeout() {
/* 78 */            return this.sink.timeout();
                }

                @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 81 */        public void close() throws IOException {
/* 82 */            if (!this.closed) {
/* 89 */                Throwable thrown = null;
                        try {
/* 91 */                    this.deflaterSink.finishDeflate();
/* 92 */                    writeFooter();
                        } catch (Throwable e) {
/* 94 */                    thrown = e;
                        }
                        try {
/* 98 */                    this.deflater.end();
                        } catch (Throwable e2) {
/* 100 */                   if (thrown == null) {
                                thrown = e2;
                            }
                        }
                        try {
/* 104 */                   this.sink.close();
                        } catch (Throwable e3) {
/* 106 */                   if (thrown == null) {
                                thrown = e3;
                            }
                        }
/* 108 */               this.closed = true;
/* 110 */               if (thrown != null) {
                            if (thrown instanceof IOException) {
                                throw (IOException) thrown;
                            }
                            throw new IOException(thrown);
                        }
                    }
                }

/* 114 */       private void writeHeader() {
/* 115 */           Buffer buffer = this.sink.buffer();
/* 116 */           buffer.writeShort(8075);
/* 117 */           buffer.writeByte(8);
/* 118 */           buffer.writeByte(0);
/* 119 */           buffer.writeInt(0);
/* 120 */           buffer.writeByte(0);
/* 121 */           buffer.writeByte(0);
                }

/* 124 */       private void writeFooter() throws IOException {
/* 125 */           this.sink.writeIntLe((int) this.crc.getValue());
/* 126 */           this.sink.writeIntLe(this.deflater.getTotalIn());
                }

/* 130 */       private void updateCrc(Buffer buffer, long byteCount) {
/* 131 */           Segment head = buffer.head;
                    while (byteCount > 0) {
/* 132 */               int segmentLength = (int) Math.min(byteCount, head.limit - head.pos);
/* 133 */               this.crc.update(head.data, head.pos, segmentLength);
/* 134 */               byteCount -= (long) segmentLength;
/* 131 */               head = head.next;
                    }
                }
            }
