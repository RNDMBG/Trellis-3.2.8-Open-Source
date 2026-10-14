            package okio;

            import java.io.EOFException;
            import java.io.IOException;
            import java.util.zip.CRC32;
            import java.util.zip.Inflater;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 58 */    public final class GzipSource implements Source {
                private static final byte FCOMMENT = 4;
                private static final byte FEXTRA = 2;
                private static final byte FHCRC = 1;
                private static final byte FNAME = 3;
                private static final byte SECTION_BODY = 1;
                private static final byte SECTION_DONE = 3;
                private static final byte SECTION_HEADER = 0;
                private static final byte SECTION_TRAILER = 2;
                private final Inflater inflater;
                private final InflaterSource inflaterSource;
                private final BufferedSource source;
                private int section = 0;
                private final CRC32 crc = new CRC32();

/* 59 */        public GzipSource(Source source) {
/* 61 */            if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
/* 62 */            this.inflater = new Inflater(true);
/* 63 */            this.source = Okio.buffer(source);
/* 64 */            this.inflaterSource = new InflaterSource(this.source, this.inflater);
                }

                @Override // okio.Source
/* 67 */        public long read(Buffer sink, long byteCount) throws IOException {
                    if (byteCount < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                    }
                    if (byteCount == 0) {
                        return 0L;
                    }
/* 72 */            if (this.section == 0) {
/* 73 */                consumeHeader();
/* 74 */                this.section = 1;
                    }
/* 78 */            if (this.section == 1) {
/* 79 */                long offset = sink.size;
/* 80 */                long result = this.inflaterSource.read(sink, byteCount);
                        if (result != -1) {
/* 82 */                    updateCrc(sink, offset, result);
/* 104 */                   return result;
                        }
/* 85 */                this.section = 2;
                    }
/* 91 */            if (this.section == 2) {
/* 92 */                consumeTrailer();
/* 93 */                this.section = 3;
/* 99 */                if (!this.source.exhausted()) {
/* 100 */                   throw new IOException("gzip finished without exhausting source");
                        }
                    }
                    return -1L;
                }

/* 113 */       private void consumeHeader() throws IOException {
/* 114 */           this.source.require(10L);
/* 115 */           byte flags = this.source.buffer().getByte(3L);
/* 116 */           boolean fhcrc = ((flags >> 1) & 1) == 1;
/* 117 */           if (fhcrc) {
                        updateCrc(this.source.buffer(), 0L, 10L);
                    }
/* 119 */           short id1id2 = this.source.readShort();
/* 120 */           checkEqual("ID1ID2", 8075, id1id2);
/* 121 */           this.source.skip(8L);
/* 127 */           if (((flags >> 2) & 1) == 1) {
/* 128 */               this.source.require(2L);
/* 129 */               if (fhcrc) {
                            updateCrc(this.source.buffer(), 0L, 2L);
                        }
/* 130 */               int xlen = this.source.buffer().readShortLe();
/* 131 */               this.source.require(xlen);
/* 132 */               if (fhcrc) {
                            updateCrc(this.source.buffer(), 0L, xlen);
                        }
/* 133 */               this.source.skip(xlen);
                    }
/* 140 */           if (((flags >> 3) & 1) == 1) {
/* 141 */               long index = this.source.indexOf((byte) 0);
                        if (index == -1) {
                            throw new EOFException();
                        }
/* 143 */               if (fhcrc) {
                            updateCrc(this.source.buffer(), 0L, 1 + index);
                        }
/* 144 */               this.source.skip(1 + index);
                    }
/* 151 */           if (((flags >> FCOMMENT) & 1) == 1) {
/* 152 */               long index2 = this.source.indexOf((byte) 0);
                        if (index2 == -1) {
                            throw new EOFException();
                        }
/* 154 */               if (fhcrc) {
                            updateCrc(this.source.buffer(), 0L, 1 + index2);
                        }
/* 155 */               this.source.skip(1 + index2);
                    }
/* 162 */           if (fhcrc) {
/* 163 */               checkEqual("FHCRC", this.source.readShortLe(), (short) this.crc.getValue());
/* 164 */               this.crc.reset();
                    }
                }

/* 172 */       private void consumeTrailer() throws IOException {
/* 173 */           checkEqual("CRC", this.source.readIntLe(), (int) this.crc.getValue());
/* 174 */           checkEqual("ISIZE", this.source.readIntLe(), this.inflater.getTotalOut());
                }

                @Override // okio.Source
/* 177 */       public Timeout timeout() {
/* 178 */           return this.source.timeout();
                }

                @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 181 */       public void close() throws IOException {
/* 182 */           this.inflaterSource.close();
                }

/* 187 */       private void updateCrc(Buffer buffer, long offset, long byteCount) {
/* 188 */           Segment s = buffer.head;
                    while (offset >= s.limit - s.pos) {
/* 190 */               offset -= (long) (s.limit - s.pos);
/* 189 */               s = s.next;
                    }
                    while (byteCount > 0) {
/* 195 */               int pos = (int) (((long) s.pos) + offset);
/* 196 */               int toUpdate = (int) Math.min(s.limit - pos, byteCount);
/* 197 */               this.crc.update(s.data, pos, toUpdate);
/* 198 */               byteCount -= (long) toUpdate;
/* 199 */               offset = 0;
/* 194 */               s = s.next;
                    }
                }

/* 203 */       private void checkEqual(String name, int expected, int actual) throws IOException {
/* 204 */           if (actual != expected) {
/* 205 */               throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", name, Integer.valueOf(actual), Integer.valueOf(expected)));
                    }
                }
            }
