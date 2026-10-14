            package okio;

            import com.flurry.android.Constants;
            import java.io.EOFException;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.nio.charset.Charset;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public final class Buffer implements BufferedSource, BufferedSink, Cloneable {
                Segment head;
                long size;















/* 55 */        public long size() {
/* 56 */            return this.size;
                }

                @Override // okio.BufferedSource, okio.BufferedSink
/* 59 */        public Buffer buffer() {
/* 60 */            return this;
                }

                /* JADX INFO: renamed from: okio.Buffer$1 */
/* 62 */        class C12971 extends OutputStream {
/* 63 */            C12971() {
                    }

                    @Override // java.io.OutputStream
/* 65 */            public void write(int b) {
/* 66 */                Buffer.this.writeByte((int) ((byte) b));
                    }

                    @Override // java.io.OutputStream
/* 69 */            public void write(byte[] data, int offset, int byteCount) {
/* 70 */                Buffer.this.write(data, offset, byteCount);
                    }

                    @Override // java.io.OutputStream, java.io.Flushable
/* 73 */            public void flush() {
                    }

                    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 76 */            public void close() {
                    }

/* 79 */            public String toString() {
/* 80 */                return this + ".outputStream()";
                    }
                }

                @Override // okio.BufferedSink
/* 63 */        public OutputStream outputStream() {
/* 64 */            return new C12971();
                }

                @Override // okio.BufferedSink
/* 85 */        public Buffer emitCompleteSegments() {
/* 86 */            return this;
                }

                @Override // okio.BufferedSource
/* 89 */        public boolean exhausted() {
                    return this.size == 0;
                }

                @Override // okio.BufferedSource
/* 93 */        public void require(long byteCount) throws EOFException {
                    if (this.size < byteCount) {
                        throw new EOFException();
                    }
                }

                /* JADX INFO: renamed from: okio.Buffer$2 */
/* 96 */        class C12982 extends InputStream {
/* 97 */            C12982() {
                    }

                    @Override // java.io.InputStream
/* 99 */            public int read() {
                        if (Buffer.this.size > 0) {
                            return Buffer.this.readByte() & Constants.UNKNOWN;
                        }
                        return -1;
                    }

                    @Override // java.io.InputStream
/* 104 */           public int read(byte[] sink, int offset, int byteCount) {
/* 105 */               return Buffer.this.read(sink, offset, byteCount);
                    }

                    @Override // java.io.InputStream
/* 108 */           public int available() {
/* 109 */               return (int) Math.min(Buffer.this.size, 2147483647L);
                    }

                    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 112 */           public void close() {
                    }

/* 115 */           public String toString() {
/* 116 */               return Buffer.this + ".inputStream()";
                    }
                }

                @Override // okio.BufferedSource
/* 97 */        public InputStream inputStream() {
/* 98 */            return new C12982();
                }

/* 122 */       public Buffer copyTo(OutputStream out) throws IOException {
/* 123 */           return copyTo(out, 0L, this.size);
                }

/* 130 */       public Buffer copyTo(OutputStream out, long offset, long byteCount) throws IOException {
/* 131 */           if (out == null) {
                        throw new IllegalArgumentException("out == null");
                    }
/* 132 */           Util.checkOffsetAndCount(this.size, offset, byteCount);
                    if (byteCount != 0) {
/* 136 */               Segment s = this.head;
                        while (offset >= s.limit - s.pos) {
/* 138 */                   offset -= (long) (s.limit - s.pos);
/* 137 */                   s = s.next;
                        }
                        while (byteCount > 0) {
/* 143 */                   int pos = (int) (((long) s.pos) + offset);
/* 144 */                   int toWrite = (int) Math.min(s.limit - pos, byteCount);
/* 145 */                   out.write(s.data, pos, toWrite);
/* 146 */                   byteCount -= (long) toWrite;
/* 147 */                   offset = 0;
/* 142 */                   s = s.next;
                        }
                    }
/* 150 */           return this;
                }

/* 154 */       public Buffer writeTo(OutputStream out) throws IOException {
/* 155 */           return writeTo(out, this.size);
                }

/* 159 */       public Buffer writeTo(OutputStream out, long byteCount) throws IOException {
/* 160 */           if (out == null) {
                        throw new IllegalArgumentException("out == null");
                    }
/* 161 */           Util.checkOffsetAndCount(this.size, 0L, byteCount);
/* 163 */           Segment s = this.head;
                    while (byteCount > 0) {
/* 165 */               int toCopy = (int) Math.min(byteCount, s.limit - s.pos);
/* 166 */               out.write(s.data, s.pos, toCopy);
/* 168 */               s.pos += toCopy;
/* 169 */               this.size -= (long) toCopy;
/* 170 */               byteCount -= (long) toCopy;
/* 172 */               if (s.pos == s.limit) {
/* 173 */                   Segment toRecycle = s;
/* 174 */                   s = toRecycle.pop();
                            this.head = s;
/* 175 */                   SegmentPool.INSTANCE.recycle(toRecycle);
                        }
                    }
/* 179 */           return this;
                }

/* 183 */       public Buffer readFrom(InputStream in) throws IOException {
/* 184 */           readFrom(in, Long.MAX_VALUE, true);
/* 185 */           return this;
                }

/* 189 */       public Buffer readFrom(InputStream in, long byteCount) throws IOException {
                    if (byteCount < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                    }
/* 191 */           readFrom(in, byteCount, false);
/* 192 */           return this;
                }

/* 195 */       private void readFrom(InputStream in, long byteCount, boolean forever) throws IOException {
/* 196 */           if (in == null) {
                        throw new IllegalArgumentException("in == null");
                    }
                    while (true) {
                        if (byteCount > 0 || forever) {
/* 198 */                   Segment tail = writableSegment(1);
/* 199 */                   int maxToCopy = (int) Math.min(byteCount, 2048 - tail.limit);
/* 200 */                   int bytesRead = in.read(tail.data, tail.limit, maxToCopy);
/* 201 */                   if (bytesRead == -1) {
/* 202 */                       if (!forever) {
/* 203 */                           throw new EOFException();
                                }
/* 209 */                       return;
                            } else {
/* 205 */                       tail.limit += bytesRead;
/* 206 */                       this.size += (long) bytesRead;
/* 207 */                       byteCount -= (long) bytesRead;
                            }
                        } else {
/* 209 */                   return;
                        }
                    }
                }

/* 216 */       public long completeSegmentByteCount() {
/* 217 */           long result = this.size;
                    if (result == 0) {
                        return 0L;
                    }
/* 221 */           Segment tail = this.head.prev;
/* 222 */           if (tail.limit < 2048) {
/* 223 */               result -= (long) (tail.limit - tail.pos);
                    }
                    return result;
                }

                @Override // okio.BufferedSource
/* 229 */       public byte readByte() {
                    if (this.size == 0) {
                        throw new IllegalStateException("size == 0");
                    }
/* 232 */           Segment segment = this.head;
/* 233 */           int pos = segment.pos;
/* 234 */           int limit = segment.limit;
/* 236 */           byte[] data = segment.data;
/* 237 */           int pos2 = pos + 1;
                    byte b = data[pos];
/* 238 */           this.size--;
/* 240 */           if (pos2 == limit) {
/* 241 */               this.head = segment.pop();
/* 242 */               SegmentPool.INSTANCE.recycle(segment);
                    } else {
/* 244 */               segment.pos = pos2;
                    }
/* 247 */           return b;
                }

/* 251 */       public byte getByte(long pos) {
/* 252 */           Util.checkOffsetAndCount(this.size, pos, 1L);
/* 253 */           Segment s = this.head;
                    while (true) {
/* 254 */               int segmentByteCount = s.limit - s.pos;
                        if (pos < segmentByteCount) {
                            return s.data[s.pos + ((int) pos)];
                        }
/* 256 */               pos -= (long) segmentByteCount;
/* 253 */               s = s.next;
                    }
                }

                @Override // okio.BufferedSource
/* 260 */       public short readShort() {
                    if (this.size < 2) {
                        throw new IllegalStateException("size < 2: " + this.size);
                    }
/* 263 */           Segment segment = this.head;
/* 264 */           int pos = segment.pos;
/* 265 */           int limit = segment.limit;
/* 268 */           if (limit - pos < 2) {
/* 270 */               int s = ((readByte() & Constants.UNKNOWN) << 8) | (readByte() & Constants.UNKNOWN);
/* 271 */               return (short) s;
                    }
/* 274 */           byte[] data = segment.data;
/* 275 */           int pos2 = pos + 1;
                    int i = (data[pos] & Constants.UNKNOWN) << 8;
                    int pos3 = pos2 + 1;
                    int s2 = i | (data[pos2] & Constants.UNKNOWN);
/* 277 */           this.size -= 2;
/* 279 */           if (pos3 == limit) {
/* 280 */               this.head = segment.pop();
/* 281 */               SegmentPool.INSTANCE.recycle(segment);
                    } else {
/* 283 */               segment.pos = pos3;
                    }
/* 286 */           return (short) s2;
                }

                @Override // okio.BufferedSource
/* 289 */       public int readInt() {
                    if (this.size < 4) {
                        throw new IllegalStateException("size < 4: " + this.size);
                    }
/* 292 */           Segment segment = this.head;
/* 293 */           int pos = segment.pos;
/* 294 */           int limit = segment.limit;
/* 297 */           if (limit - pos < 4) {
/* 301 */               return ((readByte() & Constants.UNKNOWN) << 24) | ((readByte() & Constants.UNKNOWN) << 16) | ((readByte() & Constants.UNKNOWN) << 8) | (readByte() & Constants.UNKNOWN);
                    }
/* 304 */           byte[] data = segment.data;
/* 305 */           int pos2 = pos + 1;
                    int i = (data[pos] & Constants.UNKNOWN) << 24;
                    int pos3 = pos2 + 1;
                    int i2 = i | ((data[pos2] & Constants.UNKNOWN) << 16);
                    int pos4 = pos3 + 1;
                    int i3 = i2 | ((data[pos3] & Constants.UNKNOWN) << 8);
                    int pos5 = pos4 + 1;
                    int i4 = i3 | (data[pos4] & Constants.UNKNOWN);
/* 309 */           this.size -= 4;
/* 311 */           if (pos5 == limit) {
/* 312 */               this.head = segment.pop();
/* 313 */               SegmentPool.INSTANCE.recycle(segment);
/* 318 */               return i4;
                    }
/* 315 */           segment.pos = pos5;
/* 318 */           return i4;
                }

                @Override // okio.BufferedSource
/* 321 */       public long readLong() {
                    if (this.size < 8) {
                        throw new IllegalStateException("size < 8: " + this.size);
                    }
/* 324 */           Segment segment = this.head;
/* 325 */           int pos = segment.pos;
/* 326 */           int limit = segment.limit;
/* 329 */           if (limit - pos < 8) {
/* 331 */               return ((((long) readInt()) & 4294967295L) << 32) | (((long) readInt()) & 4294967295L);
                    }
/* 334 */           byte[] data = segment.data;
/* 335 */           int pos2 = pos + 1;
                    long j = (((long) data[pos]) & 255) << 56;
                    int pos3 = pos2 + 1;
                    long j2 = j | ((((long) data[pos2]) & 255) << 48);
                    int pos4 = pos3 + 1;
                    long j3 = j2 | ((((long) data[pos3]) & 255) << 40);
                    int pos5 = pos4 + 1;
                    long j4 = j3 | ((((long) data[pos4]) & 255) << 32);
                    int pos6 = pos5 + 1;
                    long j5 = j4 | ((((long) data[pos5]) & 255) << 24);
                    int pos7 = pos6 + 1;
                    long j6 = j5 | ((((long) data[pos6]) & 255) << 16);
                    int pos8 = pos7 + 1;
                    long j7 = j6 | ((((long) data[pos7]) & 255) << 8);
                    int pos9 = pos8 + 1;
                    long j8 = j7 | (((long) data[pos8]) & 255);
/* 343 */           this.size -= 8;
/* 345 */           if (pos9 == limit) {
/* 346 */               this.head = segment.pop();
/* 347 */               SegmentPool.INSTANCE.recycle(segment);
/* 352 */               return j8;
                    }
/* 349 */           segment.pos = pos9;
/* 352 */           return j8;
                }

                @Override // okio.BufferedSource
/* 355 */       public short readShortLe() {
/* 356 */           return Util.reverseBytesShort(readShort());
                }

                @Override // okio.BufferedSource
/* 359 */       public int readIntLe() {
/* 360 */           return Util.reverseBytesInt(readInt());
                }

                @Override // okio.BufferedSource
/* 363 */       public long readLongLe() {
/* 364 */           return Util.reverseBytesLong(readLong());
                }

                @Override // okio.BufferedSource
/* 367 */       public ByteString readByteString() {
/* 368 */           return new ByteString(readByteArray());
                }

                @Override // okio.BufferedSource
/* 371 */       public ByteString readByteString(long byteCount) throws EOFException {
/* 372 */           return new ByteString(readByteArray(byteCount));
                }

                @Override // okio.BufferedSource
/* 375 */       public void readFully(Buffer sink, long byteCount) throws EOFException {
                    if (this.size < byteCount) {
/* 377 */               sink.write(this, this.size);
/* 378 */               throw new EOFException();
                    }
/* 380 */           sink.write(this, byteCount);
                }

                @Override // okio.BufferedSource
/* 383 */       public long readAll(Sink sink) throws IOException {
/* 384 */           long byteCount = this.size;
                    if (byteCount > 0) {
/* 386 */               sink.write(this, byteCount);
                    }
/* 388 */           return byteCount;
                }

                @Override // okio.BufferedSource
/* 392 */       public String readUtf8() {
                    try {
/* 393 */               return readString(this.size, Util.UTF_8);
                    } catch (EOFException e) {
/* 395 */               throw new AssertionError(e);
                    }
                }

                @Override // okio.BufferedSource
/* 399 */       public String readUtf8(long byteCount) throws EOFException {
/* 400 */           return readString(byteCount, Util.UTF_8);
                }

                @Override // okio.BufferedSource
/* 404 */       public String readString(Charset charset) {
                    try {
/* 405 */               return readString(this.size, charset);
                    } catch (EOFException e) {
/* 407 */               throw new AssertionError(e);
                    }
                }

                @Override // okio.BufferedSource
/* 411 */       public String readString(long byteCount, Charset charset) throws EOFException {
/* 412 */           Util.checkOffsetAndCount(this.size, 0L, byteCount);
/* 413 */           if (charset == null) {
                        throw new IllegalArgumentException("charset == null");
                    }
                    if (byteCount > 2147483647L) {
/* 415 */               throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
                    }
                    if (byteCount == 0) {
                        return "";
                    }
/* 419 */           Segment head = this.head;
                    if (((long) head.pos) + byteCount > head.limit) {
/* 422 */               return new String(readByteArray(byteCount), charset);
                    }
/* 425 */           String str = new String(head.data, head.pos, (int) byteCount, charset);
/* 426 */           head.pos = (int) (((long) head.pos) + byteCount);
/* 427 */           this.size -= byteCount;
/* 429 */           if (head.pos == head.limit) {
/* 430 */               this.head = head.pop();
/* 431 */               SegmentPool.INSTANCE.recycle(head);
/* 434 */               return str;
                    }
/* 434 */           return str;
                }

                @Override // okio.BufferedSource
/* 437 */       public String readUtf8Line() throws EOFException {
/* 438 */           long newline = indexOf((byte) 10);
                    if (newline != -1) {
/* 444 */               return readUtf8Line(newline);
                    }
                    if (this.size != 0) {
                        return readUtf8(this.size);
                    }
/* 441 */           return null;
                }

                @Override // okio.BufferedSource
/* 447 */       public String readUtf8LineStrict() throws EOFException {
/* 448 */           long newline = indexOf((byte) 10);
                    if (newline == -1) {
                        throw new EOFException();
                    }
/* 450 */           return readUtf8Line(newline);
                }

/* 453 */       String readUtf8Line(long newline) throws EOFException {
                    if (newline > 0 && getByte(newline - 1) == 13) {
/* 456 */               String result = readUtf8(newline - 1);
/* 457 */               skip(2L);
                        return result;
                    }
/* 462 */           String result2 = readUtf8(newline);
/* 463 */           skip(1L);
                    return result2;
                }

                @Override // okio.BufferedSource
/* 469 */       public byte[] readByteArray() {
                    try {
/* 470 */               return readByteArray(this.size);
                    } catch (EOFException e) {
/* 472 */               throw new AssertionError(e);
                    }
                }

                @Override // okio.BufferedSource
/* 476 */       public byte[] readByteArray(long byteCount) throws EOFException {
/* 477 */           Util.checkOffsetAndCount(this.size, 0L, byteCount);
                    if (byteCount > 2147483647L) {
/* 479 */               throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
                    }
/* 482 */           byte[] result = new byte[(int) byteCount];
/* 483 */           readFully(result);
/* 484 */           return result;
                }

                @Override // okio.BufferedSource
/* 487 */       public int read(byte[] sink) {
/* 488 */           return read(sink, 0, sink.length);
                }

                @Override // okio.BufferedSource
/* 491 */       public void readFully(byte[] sink) throws EOFException {
/* 492 */           int offset = 0;
/* 493 */           while (offset < sink.length) {
/* 494 */               int read = read(sink, offset, sink.length - offset);
/* 495 */               if (read == -1) {
                            throw new EOFException();
                        }
/* 496 */               offset += read;
                    }
                }

                @Override // okio.BufferedSource
/* 500 */       public int read(byte[] sink, int offset, int byteCount) {
/* 501 */           Util.checkOffsetAndCount(sink.length, offset, byteCount);
/* 503 */           Segment s = this.head;
/* 504 */           if (s == null) {
                        return -1;
                    }
/* 505 */           int toCopy = Math.min(byteCount, s.limit - s.pos);
/* 506 */           System.arraycopy(s.data, s.pos, sink, offset, toCopy);
/* 508 */           s.pos += toCopy;
/* 509 */           this.size -= (long) toCopy;
/* 511 */           if (s.pos == s.limit) {
/* 512 */               this.head = s.pop();
/* 513 */               SegmentPool.INSTANCE.recycle(s);
/* 516 */               return toCopy;
                    }
/* 516 */           return toCopy;
                }

/* 524 */       public void clear() {
                    try {
/* 525 */               skip(this.size);
                    } catch (EOFException e) {
/* 527 */               throw new AssertionError(e);
                    }
                }

                @Override // okio.BufferedSource
/* 532 */       public void skip(long byteCount) throws EOFException {
                    while (byteCount > 0) {
/* 534 */               if (this.head == null) {
                            throw new EOFException();
                        }
/* 536 */               int toSkip = (int) Math.min(byteCount, this.head.limit - this.head.pos);
/* 537 */               this.size -= (long) toSkip;
/* 538 */               byteCount -= (long) toSkip;
                        this.head.pos += toSkip;
/* 541 */               if (this.head.pos == this.head.limit) {
/* 542 */                   Segment toRecycle = this.head;
/* 543 */                   this.head = toRecycle.pop();
/* 544 */                   SegmentPool.INSTANCE.recycle(toRecycle);
                        }
                    }
                }

                @Override // okio.BufferedSink
/* 549 */       public Buffer write(ByteString byteString) {
/* 550 */           if (byteString == null) {
                        throw new IllegalArgumentException("byteString == null");
                    }
/* 551 */           return write(byteString.data, 0, byteString.data.length);
                }

                @Override // okio.BufferedSink
/* 554 */       public Buffer writeUtf8(String string) {
/* 555 */           if (string == null) {
                        throw new IllegalArgumentException("string == null");
                    }
/* 557 */           return writeString(string, Util.UTF_8);
                }

                @Override // okio.BufferedSink
/* 560 */       public Buffer writeString(String string, Charset charset) {
/* 561 */           if (string == null) {
                        throw new IllegalArgumentException("string == null");
                    }
/* 562 */           if (charset == null) {
                        throw new IllegalArgumentException("charset == null");
                    }
/* 563 */           byte[] data = string.getBytes(charset);
/* 564 */           return write(data, 0, data.length);
                }

                @Override // okio.BufferedSink
/* 567 */       public Buffer write(byte[] source) {
/* 568 */           if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
/* 569 */           return write(source, 0, source.length);
                }

                @Override // okio.BufferedSink
/* 572 */       public Buffer write(byte[] source, int offset, int byteCount) {
/* 573 */           if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
/* 574 */           Util.checkOffsetAndCount(source.length, offset, byteCount);
/* 576 */           int limit = offset + byteCount;
/* 577 */           while (offset < limit) {
/* 578 */               Segment tail = writableSegment(1);
/* 580 */               int toCopy = Math.min(limit - offset, 2048 - tail.limit);
/* 581 */               System.arraycopy(source, offset, tail.data, tail.limit, toCopy);
/* 583 */               offset += toCopy;
/* 584 */               tail.limit += toCopy;
                    }
/* 587 */           this.size += (long) byteCount;
/* 588 */           return this;
                }

                @Override // okio.BufferedSink
/* 591 */       public long writeAll(Source source) throws IOException {
/* 592 */           if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
/* 593 */           long totalBytesRead = 0;
                    while (true) {
/* 594 */               long readCount = source.read(this, 2048L);
                        if (readCount != -1) {
/* 595 */                   totalBytesRead += readCount;
                        } else {
/* 597 */                   return totalBytesRead;
                        }
                    }
                }

                @Override // okio.BufferedSink
/* 600 */       public Buffer writeByte(int b) {
/* 601 */           Segment tail = writableSegment(1);
/* 602 */           byte[] bArr = tail.data;
                    int i = tail.limit;
                    tail.limit = i + 1;
                    bArr[i] = (byte) b;
/* 603 */           this.size++;
/* 604 */           return this;
                }

                @Override // okio.BufferedSink
/* 607 */       public Buffer writeShort(int s) {
/* 608 */           Segment tail = writableSegment(2);
/* 609 */           byte[] data = tail.data;
/* 610 */           int limit = tail.limit;
/* 611 */           int limit2 = limit + 1;
                    data[limit] = (byte) ((s >>> 8) & 255);
                    data[limit2] = (byte) (s & 255);
/* 613 */           tail.limit = limit2 + 1;
/* 614 */           this.size += 2;
/* 615 */           return this;
                }

                @Override // okio.BufferedSink
/* 618 */       public Buffer writeShortLe(int s) {
/* 619 */           return writeShort((int) Util.reverseBytesShort((short) s));
                }

                @Override // okio.BufferedSink
/* 622 */       public Buffer writeInt(int i) {
/* 623 */           Segment tail = writableSegment(4);
/* 624 */           byte[] data = tail.data;
/* 625 */           int limit = tail.limit;
/* 626 */           int limit2 = limit + 1;
                    data[limit] = (byte) ((i >>> 24) & 255);
/* 627 */           int limit3 = limit2 + 1;
                    data[limit2] = (byte) ((i >>> 16) & 255);
/* 628 */           int limit4 = limit3 + 1;
                    data[limit3] = (byte) ((i >>> 8) & 255);
                    data[limit4] = (byte) (i & 255);
/* 630 */           tail.limit = limit4 + 1;
/* 631 */           this.size += 4;
/* 632 */           return this;
                }

                @Override // okio.BufferedSink
/* 635 */       public Buffer writeIntLe(int i) {
/* 636 */           return writeInt(Util.reverseBytesInt(i));
                }

                @Override // okio.BufferedSink
/* 639 */       public Buffer writeLong(long v) {
/* 640 */           Segment tail = writableSegment(8);
/* 641 */           byte[] data = tail.data;
/* 642 */           int limit = tail.limit;
/* 643 */           int limit2 = limit + 1;
                    data[limit] = (byte) ((v >>> 56) & 255);
/* 644 */           int limit3 = limit2 + 1;
                    data[limit2] = (byte) ((v >>> 48) & 255);
/* 645 */           int limit4 = limit3 + 1;
                    data[limit3] = (byte) ((v >>> 40) & 255);
/* 646 */           int limit5 = limit4 + 1;
                    data[limit4] = (byte) ((v >>> 32) & 255);
/* 647 */           int limit6 = limit5 + 1;
                    data[limit5] = (byte) ((v >>> 24) & 255);
/* 648 */           int limit7 = limit6 + 1;
                    data[limit6] = (byte) ((v >>> 16) & 255);
/* 649 */           int limit8 = limit7 + 1;
                    data[limit7] = (byte) ((v >>> 8) & 255);
                    data[limit8] = (byte) (v & 255);
/* 651 */           tail.limit = limit8 + 1;
/* 652 */           this.size += 8;
/* 653 */           return this;
                }

                @Override // okio.BufferedSink
/* 656 */       public Buffer writeLongLe(long v) {
/* 657 */           return writeLong(Util.reverseBytesLong(v));
                }

/* 664 */       Segment writableSegment(int minimumCapacity) {
/* 665 */           if (minimumCapacity < 1 || minimumCapacity > 2048) {
                        throw new IllegalArgumentException();
                    }
/* 667 */           if (this.head == null) {
/* 668 */               this.head = SegmentPool.INSTANCE.take();
/* 669 */               Segment segment = this.head;
                        Segment segment2 = this.head;
                        Segment segment3 = this.head;
                        segment2.prev = segment3;
                        segment.next = segment3;
/* 676 */               return segment3;
                    }
/* 672 */           Segment tail = this.head.prev;
/* 673 */           if (tail.limit + minimumCapacity > 2048) {
/* 674 */               return tail.push(SegmentPool.INSTANCE.take());
                    }
/* 676 */           return tail;
                }

                @Override // okio.Sink
/* 729 */       public void write(Buffer source, long byteCount) {
/* 730 */           if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
/* 731 */           if (source == this) {
                        throw new IllegalArgumentException("source == this");
                    }
/* 732 */           Util.checkOffsetAndCount(source.size, 0L, byteCount);
                    while (byteCount > 0) {
                        if (byteCount < source.head.limit - source.head.pos) {
/* 737 */                   Segment tail = this.head != null ? this.head.prev : null;
/* 738 */                   if (tail == null || ((long) (tail.limit - tail.pos)) + byteCount > 2048) {
/* 741 */                       source.head = source.head.split((int) byteCount);
                            } else {
/* 744 */                       source.head.writeTo(tail, (int) byteCount);
/* 745 */                       source.size -= byteCount;
/* 746 */                       this.size += byteCount;
/* 767 */                       return;
                            }
                        }
/* 752 */               Segment segmentToMove = source.head;
/* 753 */               long movedByteCount = segmentToMove.limit - segmentToMove.pos;
/* 754 */               source.head = segmentToMove.pop();
/* 755 */               if (this.head == null) {
/* 756 */                   this.head = segmentToMove;
/* 757 */                   Segment segment = this.head;
                            Segment segment2 = this.head;
                            Segment segment3 = this.head;
                            segment2.prev = segment3;
                            segment.next = segment3;
                        } else {
/* 761 */                   this.head.prev.push(segmentToMove).compact();
                        }
/* 763 */               source.size -= movedByteCount;
/* 764 */               this.size += movedByteCount;
/* 765 */               byteCount -= movedByteCount;
                    }
                }

                @Override // okio.Source
/* 769 */       public long read(Buffer sink, long byteCount) {
/* 770 */           if (sink == null) {
                        throw new IllegalArgumentException("sink == null");
                    }
                    if (byteCount < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                    }
                    if (this.size == 0) {
                        return -1L;
                    }
                    if (byteCount > this.size) {
                        byteCount = this.size;
                    }
/* 774 */           sink.write(this, byteCount);
                    return byteCount;
                }

                @Override // okio.BufferedSource
/* 778 */       public long indexOf(byte b) {
/* 779 */           return indexOf(b, 0L);
                }

/* 786 */       public long indexOf(byte b, long fromIndex) {
                    if (fromIndex < 0) {
                        throw new IllegalArgumentException("fromIndex < 0");
                    }
/* 789 */           Segment s = this.head;
/* 790 */           if (s == null) {
                        return -1L;
                    }
/* 791 */           long offset = 0;
/* 805 */           do {
/* 793 */               int segmentByteCount = s.limit - s.pos;
                        if (fromIndex >= segmentByteCount) {
/* 795 */                   fromIndex -= (long) segmentByteCount;
                        } else {
/* 797 */                   byte[] data = s.data;
                            long limit = s.limit;
                            for (long pos = ((long) s.pos) + fromIndex; pos < limit; pos++) {
/* 799 */                       if (data[(int) pos] == b) {
                                    return (offset + pos) - ((long) s.pos);
                                }
                            }
/* 801 */                   fromIndex = 0;
                        }
/* 803 */               offset += (long) segmentByteCount;
/* 804 */               s = s.next;
/* 805 */           } while (s != this.head);
/* 806 */           return -1L;
                }

                @Override // okio.Sink
/* 809 */       public void flush() {
                }

                @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 812 */       public void close() {
                }

                @Override // okio.Source
/* 815 */       public Timeout timeout() {
/* 816 */           return Timeout.NONE;
                }

/* 820 */       List<Integer> segmentSizes() {
/* 821 */           if (this.head == null) {
                        return Collections.emptyList();
                    }
/* 822 */           List<Integer> result = new ArrayList<>();
/* 823 */           result.add(Integer.valueOf(this.head.limit - this.head.pos));
                    for (Segment s = this.head.next; s != this.head; s = s.next) {
/* 825 */               result.add(Integer.valueOf(s.limit - s.pos));
                    }
/* 827 */           return result;
                }

/* 830 */       public boolean equals(Object o) {
                    int posB;
                    int posA;
/* 831 */           if (this == o) {
                        return true;
                    }
/* 832 */           if (!(o instanceof Buffer)) {
                        return false;
                    }
/* 833 */           Buffer that = (Buffer) o;
                    if (this.size != that.size) {
                        return false;
                    }
                    if (this.size == 0) {
                        return true;
                    }
/* 837 */           Segment sa = this.head;
/* 838 */           Segment sb = that.head;
/* 839 */           int posA2 = sa.pos;
/* 840 */           int posB2 = sb.pos;
/* 842 */           long pos = 0;
                    while (pos < this.size) {
/* 843 */               long count = Math.min(sa.limit - posA2, sb.limit - posB2);
/* 845 */               int i = 0;
                        while (true) {
                            posB = posB2;
                            posA = posA2;
                            if (i < count) {
                                posA2 = posA + 1;
                                posB2 = posB + 1;
/* 846 */                       if (sa.data[posA] != sb.data[posB]) {
                                    return false;
                                }
/* 845 */                       i++;
                            } else {
                                break;
                            }
                        }
/* 849 */               if (posA == sa.limit) {
/* 850 */                   sa = sa.next;
/* 851 */                   posA2 = sa.pos;
                        } else {
                            posA2 = posA;
                        }
/* 854 */               if (posB == sb.limit) {
/* 855 */                   sb = sb.next;
/* 856 */                   posB2 = sb.pos;
                        } else {
                            posB2 = posB;
                        }
/* 842 */               pos += count;
                    }
/* 860 */           return true;
                }

/* 863 */       public int hashCode() {
/* 864 */           Segment s = this.head;
/* 865 */           if (s == null) {
                        return 0;
                    }
/* 866 */           int result = 1;
/* 872 */           do {
                        int limit = s.limit;
                        for (int pos = s.pos; pos < limit; pos++) {
/* 869 */                   result = (result * 31) + s.data[pos];
                        }
/* 871 */               s = s.next;
/* 872 */           } while (s != this.head);
/* 873 */           return result;
                }

/* 876 */       public String toString() {
                    if (this.size == 0) {
/* 878 */               return "Buffer[size=0]";
                    }
                    if (this.size <= 16) {
/* 882 */               ByteString data = m2481clone().readByteString();
/* 883 */               return String.format("Buffer[size=%s data=%s]", Long.valueOf(this.size), data.hex());
                    }
                    try {
/* 887 */               MessageDigest md5 = MessageDigest.getInstance("MD5");
/* 888 */               md5.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
                        for (Segment s = this.head.next; s != this.head; s = s.next) {
/* 890 */                   md5.update(s.data, s.pos, s.limit - s.pos);
                        }
/* 892 */               return String.format("Buffer[size=%s md5=%s]", Long.valueOf(this.size), ByteString.m2468of(md5.digest()).hex());
                    } catch (NoSuchAlgorithmException e) {
/* 895 */               throw new AssertionError();
                    }
                }

                /* JADX INFO: renamed from: clone, reason: collision with other method in class */
/* 900 */       public Buffer m2481clone() {
/* 901 */           Buffer result = new Buffer();
                    if (this.size != 0) {
/* 904 */               result.write(this.head.data, this.head.pos, this.head.limit - this.head.pos);
                        for (Segment s = this.head.next; s != this.head; s = s.next) {
/* 906 */                   result.write(s.data, s.pos, s.limit - s.pos);
                        }
                    }
/* 909 */           return result;
                }
            }
