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
            
            
    public final class Buffer implements BufferedSource, BufferedSink, Cloneable {
                Segment head;
                long size;















        public long size() {
            return this.size;
                }

                @Override 
        public Buffer buffer() {
            return this;
                }

                
        class C12971 extends OutputStream {
            C12971() {
                    }

                    @Override 
            public void write(int b) {
                Buffer.this.writeByte((int) ((byte) b));
                    }

                    @Override 
            public void write(byte[] data, int offset, int byteCount) {
                Buffer.this.write(data, offset, byteCount);
                    }

                    @Override 
            public void flush() {
                    }

                    @Override 
            public void close() {
                    }

            public String toString() {
                return this + ".outputStream()";
                    }
                }

                @Override 
        public OutputStream outputStream() {
            return new C12971();
                }

                @Override 
        public Buffer emitCompleteSegments() {
            return this;
                }

                @Override 
        public boolean exhausted() {
                    return this.size == 0;
                }

                @Override 
        public void require(long byteCount) throws EOFException {
                    if (this.size < byteCount) {
                        throw new EOFException();
                    }
                }

                
        class C12982 extends InputStream {
            C12982() {
                    }

                    @Override 
            public int read() {
                        if (Buffer.this.size > 0) {
                            return Buffer.this.readByte() & Constants.UNKNOWN;
                        }
                        return -1;
                    }

                    @Override 
           public int read(byte[] sink, int offset, int byteCount) {
               return Buffer.this.read(sink, offset, byteCount);
                    }

                    @Override 
           public int available() {
               return (int) Math.min(Buffer.this.size, 2147483647L);
                    }

                    @Override 
           public void close() {
                    }

           public String toString() {
               return Buffer.this + ".inputStream()";
                    }
                }

                @Override 
        public InputStream inputStream() {
            return new C12982();
                }

       public Buffer copyTo(OutputStream out) throws IOException {
           return copyTo(out, 0L, this.size);
                }

       public Buffer copyTo(OutputStream out, long offset, long byteCount) throws IOException {
           if (out == null) {
                        throw new IllegalArgumentException("out == null");
                    }
           Util.checkOffsetAndCount(this.size, offset, byteCount);
                    if (byteCount != 0) {
               Segment s = this.head;
                        while (offset >= s.limit - s.pos) {
                   offset -= (long) (s.limit - s.pos);
                   s = s.next;
                        }
                        while (byteCount > 0) {
                   int pos = (int) (((long) s.pos) + offset);
                   int toWrite = (int) Math.min(s.limit - pos, byteCount);
                   out.write(s.data, pos, toWrite);
                   byteCount -= (long) toWrite;
                   offset = 0;
                   s = s.next;
                        }
                    }
           return this;
                }

       public Buffer writeTo(OutputStream out) throws IOException {
           return writeTo(out, this.size);
                }

       public Buffer writeTo(OutputStream out, long byteCount) throws IOException {
           if (out == null) {
                        throw new IllegalArgumentException("out == null");
                    }
           Util.checkOffsetAndCount(this.size, 0L, byteCount);
           Segment s = this.head;
                    while (byteCount > 0) {
               int toCopy = (int) Math.min(byteCount, s.limit - s.pos);
               out.write(s.data, s.pos, toCopy);
               s.pos += toCopy;
               this.size -= (long) toCopy;
               byteCount -= (long) toCopy;
               if (s.pos == s.limit) {
                   Segment toRecycle = s;
                   s = toRecycle.pop();
                            this.head = s;
                   SegmentPool.INSTANCE.recycle(toRecycle);
                        }
                    }
           return this;
                }

       public Buffer readFrom(InputStream in) throws IOException {
           readFrom(in, Long.MAX_VALUE, true);
           return this;
                }

       public Buffer readFrom(InputStream in, long byteCount) throws IOException {
                    if (byteCount < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                    }
           readFrom(in, byteCount, false);
           return this;
                }

       private void readFrom(InputStream in, long byteCount, boolean forever) throws IOException {
           if (in == null) {
                        throw new IllegalArgumentException("in == null");
                    }
                    while (true) {
                        if (byteCount > 0 || forever) {
                   Segment tail = writableSegment(1);
                   int maxToCopy = (int) Math.min(byteCount, 2048 - tail.limit);
                   int bytesRead = in.read(tail.data, tail.limit, maxToCopy);
                   if (bytesRead == -1) {
                       if (!forever) {
                           throw new EOFException();
                                }
                       return;
                            } else {
                       tail.limit += bytesRead;
                       this.size += (long) bytesRead;
                       byteCount -= (long) bytesRead;
                            }
                        } else {
                   return;
                        }
                    }
                }

       public long completeSegmentByteCount() {
           long result = this.size;
                    if (result == 0) {
                        return 0L;
                    }
           Segment tail = this.head.prev;
           if (tail.limit < 2048) {
               result -= (long) (tail.limit - tail.pos);
                    }
                    return result;
                }

                @Override 
       public byte readByte() {
                    if (this.size == 0) {
                        throw new IllegalStateException("size == 0");
                    }
           Segment segment = this.head;
           int pos = segment.pos;
           int limit = segment.limit;
           byte[] data = segment.data;
           int pos2 = pos + 1;
                    byte b = data[pos];
           this.size--;
           if (pos2 == limit) {
               this.head = segment.pop();
               SegmentPool.INSTANCE.recycle(segment);
                    } else {
               segment.pos = pos2;
                    }
           return b;
                }

       public byte getByte(long pos) {
           Util.checkOffsetAndCount(this.size, pos, 1L);
           Segment s = this.head;
                    while (true) {
               int segmentByteCount = s.limit - s.pos;
                        if (pos < segmentByteCount) {
                            return s.data[s.pos + ((int) pos)];
                        }
               pos -= (long) segmentByteCount;
               s = s.next;
                    }
                }

                @Override 
       public short readShort() {
                    if (this.size < 2) {
                        throw new IllegalStateException("size < 2: " + this.size);
                    }
           Segment segment = this.head;
           int pos = segment.pos;
           int limit = segment.limit;
           if (limit - pos < 2) {
               int s = ((readByte() & Constants.UNKNOWN) << 8) | (readByte() & Constants.UNKNOWN);
               return (short) s;
                    }
           byte[] data = segment.data;
           int pos2 = pos + 1;
                    int i = (data[pos] & Constants.UNKNOWN) << 8;
                    int pos3 = pos2 + 1;
                    int s2 = i | (data[pos2] & Constants.UNKNOWN);
           this.size -= 2;
           if (pos3 == limit) {
               this.head = segment.pop();
               SegmentPool.INSTANCE.recycle(segment);
                    } else {
               segment.pos = pos3;
                    }
           return (short) s2;
                }

                @Override 
       public int readInt() {
                    if (this.size < 4) {
                        throw new IllegalStateException("size < 4: " + this.size);
                    }
           Segment segment = this.head;
           int pos = segment.pos;
           int limit = segment.limit;
           if (limit - pos < 4) {
               return ((readByte() & Constants.UNKNOWN) << 24) | ((readByte() & Constants.UNKNOWN) << 16) | ((readByte() & Constants.UNKNOWN) << 8) | (readByte() & Constants.UNKNOWN);
                    }
           byte[] data = segment.data;
           int pos2 = pos + 1;
                    int i = (data[pos] & Constants.UNKNOWN) << 24;
                    int pos3 = pos2 + 1;
                    int i2 = i | ((data[pos2] & Constants.UNKNOWN) << 16);
                    int pos4 = pos3 + 1;
                    int i3 = i2 | ((data[pos3] & Constants.UNKNOWN) << 8);
                    int pos5 = pos4 + 1;
                    int i4 = i3 | (data[pos4] & Constants.UNKNOWN);
           this.size -= 4;
           if (pos5 == limit) {
               this.head = segment.pop();
               SegmentPool.INSTANCE.recycle(segment);
               return i4;
                    }
           segment.pos = pos5;
           return i4;
                }

                @Override 
       public long readLong() {
                    if (this.size < 8) {
                        throw new IllegalStateException("size < 8: " + this.size);
                    }
           Segment segment = this.head;
           int pos = segment.pos;
           int limit = segment.limit;
           if (limit - pos < 8) {
               return ((((long) readInt()) & 4294967295L) << 32) | (((long) readInt()) & 4294967295L);
                    }
           byte[] data = segment.data;
           int pos2 = pos + 1;
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
           this.size -= 8;
           if (pos9 == limit) {
               this.head = segment.pop();
               SegmentPool.INSTANCE.recycle(segment);
               return j8;
                    }
           segment.pos = pos9;
           return j8;
                }

                @Override 
       public short readShortLe() {
           return Util.reverseBytesShort(readShort());
                }

                @Override 
       public int readIntLe() {
           return Util.reverseBytesInt(readInt());
                }

                @Override 
       public long readLongLe() {
           return Util.reverseBytesLong(readLong());
                }

                @Override 
       public ByteString readByteString() {
           return new ByteString(readByteArray());
                }

                @Override 
       public ByteString readByteString(long byteCount) throws EOFException {
           return new ByteString(readByteArray(byteCount));
                }

                @Override 
       public void readFully(Buffer sink, long byteCount) throws EOFException {
                    if (this.size < byteCount) {
               sink.write(this, this.size);
               throw new EOFException();
                    }
           sink.write(this, byteCount);
                }

                @Override 
       public long readAll(Sink sink) throws IOException {
           long byteCount = this.size;
                    if (byteCount > 0) {
               sink.write(this, byteCount);
                    }
           return byteCount;
                }

                @Override 
       public String readUtf8() {
                    try {
               return readString(this.size, Util.UTF_8);
                    } catch (EOFException e) {
               throw new AssertionError(e);
                    }
                }

                @Override 
       public String readUtf8(long byteCount) throws EOFException {
           return readString(byteCount, Util.UTF_8);
                }

                @Override 
       public String readString(Charset charset) {
                    try {
               return readString(this.size, charset);
                    } catch (EOFException e) {
               throw new AssertionError(e);
                    }
                }

                @Override 
       public String readString(long byteCount, Charset charset) throws EOFException {
           Util.checkOffsetAndCount(this.size, 0L, byteCount);
           if (charset == null) {
                        throw new IllegalArgumentException("charset == null");
                    }
                    if (byteCount > 2147483647L) {
               throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
                    }
                    if (byteCount == 0) {
                        return "";
                    }
           Segment head = this.head;
                    if (((long) head.pos) + byteCount > head.limit) {
               return new String(readByteArray(byteCount), charset);
                    }
           String str = new String(head.data, head.pos, (int) byteCount, charset);
           head.pos = (int) (((long) head.pos) + byteCount);
           this.size -= byteCount;
           if (head.pos == head.limit) {
               this.head = head.pop();
               SegmentPool.INSTANCE.recycle(head);
               return str;
                    }
           return str;
                }

                @Override 
       public String readUtf8Line() throws EOFException {
           long newline = indexOf((byte) 10);
                    if (newline != -1) {
               return readUtf8Line(newline);
                    }
                    if (this.size != 0) {
                        return readUtf8(this.size);
                    }
           return null;
                }

                @Override 
       public String readUtf8LineStrict() throws EOFException {
           long newline = indexOf((byte) 10);
                    if (newline == -1) {
                        throw new EOFException();
                    }
           return readUtf8Line(newline);
                }

       String readUtf8Line(long newline) throws EOFException {
                    if (newline > 0 && getByte(newline - 1) == 13) {
               String result = readUtf8(newline - 1);
               skip(2L);
                        return result;
                    }
           String result2 = readUtf8(newline);
           skip(1L);
                    return result2;
                }

                @Override 
       public byte[] readByteArray() {
                    try {
               return readByteArray(this.size);
                    } catch (EOFException e) {
               throw new AssertionError(e);
                    }
                }

                @Override 
       public byte[] readByteArray(long byteCount) throws EOFException {
           Util.checkOffsetAndCount(this.size, 0L, byteCount);
                    if (byteCount > 2147483647L) {
               throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
                    }
           byte[] result = new byte[(int) byteCount];
           readFully(result);
           return result;
                }

                @Override 
       public int read(byte[] sink) {
           return read(sink, 0, sink.length);
                }

                @Override 
       public void readFully(byte[] sink) throws EOFException {
           int offset = 0;
           while (offset < sink.length) {
               int read = read(sink, offset, sink.length - offset);
               if (read == -1) {
                            throw new EOFException();
                        }
               offset += read;
                    }
                }

                @Override 
       public int read(byte[] sink, int offset, int byteCount) {
           Util.checkOffsetAndCount(sink.length, offset, byteCount);
           Segment s = this.head;
           if (s == null) {
                        return -1;
                    }
           int toCopy = Math.min(byteCount, s.limit - s.pos);
           System.arraycopy(s.data, s.pos, sink, offset, toCopy);
           s.pos += toCopy;
           this.size -= (long) toCopy;
           if (s.pos == s.limit) {
               this.head = s.pop();
               SegmentPool.INSTANCE.recycle(s);
               return toCopy;
                    }
           return toCopy;
                }

       public void clear() {
                    try {
               skip(this.size);
                    } catch (EOFException e) {
               throw new AssertionError(e);
                    }
                }

                @Override 
       public void skip(long byteCount) throws EOFException {
                    while (byteCount > 0) {
               if (this.head == null) {
                            throw new EOFException();
                        }
               int toSkip = (int) Math.min(byteCount, this.head.limit - this.head.pos);
               this.size -= (long) toSkip;
               byteCount -= (long) toSkip;
                        this.head.pos += toSkip;
               if (this.head.pos == this.head.limit) {
                   Segment toRecycle = this.head;
                   this.head = toRecycle.pop();
                   SegmentPool.INSTANCE.recycle(toRecycle);
                        }
                    }
                }

                @Override 
       public Buffer write(ByteString byteString) {
           if (byteString == null) {
                        throw new IllegalArgumentException("byteString == null");
                    }
           return write(byteString.data, 0, byteString.data.length);
                }

                @Override 
       public Buffer writeUtf8(String string) {
           if (string == null) {
                        throw new IllegalArgumentException("string == null");
                    }
           return writeString(string, Util.UTF_8);
                }

                @Override 
       public Buffer writeString(String string, Charset charset) {
           if (string == null) {
                        throw new IllegalArgumentException("string == null");
                    }
           if (charset == null) {
                        throw new IllegalArgumentException("charset == null");
                    }
           byte[] data = string.getBytes(charset);
           return write(data, 0, data.length);
                }

                @Override 
       public Buffer write(byte[] source) {
           if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
           return write(source, 0, source.length);
                }

                @Override 
       public Buffer write(byte[] source, int offset, int byteCount) {
           if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
           Util.checkOffsetAndCount(source.length, offset, byteCount);
           int limit = offset + byteCount;
           while (offset < limit) {
               Segment tail = writableSegment(1);
               int toCopy = Math.min(limit - offset, 2048 - tail.limit);
               System.arraycopy(source, offset, tail.data, tail.limit, toCopy);
               offset += toCopy;
               tail.limit += toCopy;
                    }
           this.size += (long) byteCount;
           return this;
                }

                @Override 
       public long writeAll(Source source) throws IOException {
           if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
           long totalBytesRead = 0;
                    while (true) {
               long readCount = source.read(this, 2048L);
                        if (readCount != -1) {
                   totalBytesRead += readCount;
                        } else {
                   return totalBytesRead;
                        }
                    }
                }

                @Override 
       public Buffer writeByte(int b) {
           Segment tail = writableSegment(1);
           byte[] bArr = tail.data;
                    int i = tail.limit;
                    tail.limit = i + 1;
                    bArr[i] = (byte) b;
           this.size++;
           return this;
                }

                @Override 
       public Buffer writeShort(int s) {
           Segment tail = writableSegment(2);
           byte[] data = tail.data;
           int limit = tail.limit;
           int limit2 = limit + 1;
                    data[limit] = (byte) ((s >>> 8) & 255);
                    data[limit2] = (byte) (s & 255);
           tail.limit = limit2 + 1;
           this.size += 2;
           return this;
                }

                @Override 
       public Buffer writeShortLe(int s) {
           return writeShort((int) Util.reverseBytesShort((short) s));
                }

                @Override 
       public Buffer writeInt(int i) {
           Segment tail = writableSegment(4);
           byte[] data = tail.data;
           int limit = tail.limit;
           int limit2 = limit + 1;
                    data[limit] = (byte) ((i >>> 24) & 255);
           int limit3 = limit2 + 1;
                    data[limit2] = (byte) ((i >>> 16) & 255);
           int limit4 = limit3 + 1;
                    data[limit3] = (byte) ((i >>> 8) & 255);
                    data[limit4] = (byte) (i & 255);
           tail.limit = limit4 + 1;
           this.size += 4;
           return this;
                }

                @Override 
       public Buffer writeIntLe(int i) {
           return writeInt(Util.reverseBytesInt(i));
                }

                @Override 
       public Buffer writeLong(long v) {
           Segment tail = writableSegment(8);
           byte[] data = tail.data;
           int limit = tail.limit;
           int limit2 = limit + 1;
                    data[limit] = (byte) ((v >>> 56) & 255);
           int limit3 = limit2 + 1;
                    data[limit2] = (byte) ((v >>> 48) & 255);
           int limit4 = limit3 + 1;
                    data[limit3] = (byte) ((v >>> 40) & 255);
           int limit5 = limit4 + 1;
                    data[limit4] = (byte) ((v >>> 32) & 255);
           int limit6 = limit5 + 1;
                    data[limit5] = (byte) ((v >>> 24) & 255);
           int limit7 = limit6 + 1;
                    data[limit6] = (byte) ((v >>> 16) & 255);
           int limit8 = limit7 + 1;
                    data[limit7] = (byte) ((v >>> 8) & 255);
                    data[limit8] = (byte) (v & 255);
           tail.limit = limit8 + 1;
           this.size += 8;
           return this;
                }

                @Override 
       public Buffer writeLongLe(long v) {
           return writeLong(Util.reverseBytesLong(v));
                }

       Segment writableSegment(int minimumCapacity) {
           if (minimumCapacity < 1 || minimumCapacity > 2048) {
                        throw new IllegalArgumentException();
                    }
           if (this.head == null) {
               this.head = SegmentPool.INSTANCE.take();
               Segment segment = this.head;
                        Segment segment2 = this.head;
                        Segment segment3 = this.head;
                        segment2.prev = segment3;
                        segment.next = segment3;
               return segment3;
                    }
           Segment tail = this.head.prev;
           if (tail.limit + minimumCapacity > 2048) {
               return tail.push(SegmentPool.INSTANCE.take());
                    }
           return tail;
                }

                @Override 
       public void write(Buffer source, long byteCount) {
           if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
           if (source == this) {
                        throw new IllegalArgumentException("source == this");
                    }
           Util.checkOffsetAndCount(source.size, 0L, byteCount);
                    while (byteCount > 0) {
                        if (byteCount < source.head.limit - source.head.pos) {
                   Segment tail = this.head != null ? this.head.prev : null;
                   if (tail == null || ((long) (tail.limit - tail.pos)) + byteCount > 2048) {
                       source.head = source.head.split((int) byteCount);
                            } else {
                       source.head.writeTo(tail, (int) byteCount);
                       source.size -= byteCount;
                       this.size += byteCount;
                       return;
                            }
                        }
               Segment segmentToMove = source.head;
               long movedByteCount = segmentToMove.limit - segmentToMove.pos;
               source.head = segmentToMove.pop();
               if (this.head == null) {
                   this.head = segmentToMove;
                   Segment segment = this.head;
                            Segment segment2 = this.head;
                            Segment segment3 = this.head;
                            segment2.prev = segment3;
                            segment.next = segment3;
                        } else {
                   this.head.prev.push(segmentToMove).compact();
                        }
               source.size -= movedByteCount;
               this.size += movedByteCount;
               byteCount -= movedByteCount;
                    }
                }

                @Override 
       public long read(Buffer sink, long byteCount) {
           if (sink == null) {
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
           sink.write(this, byteCount);
                    return byteCount;
                }

                @Override 
       public long indexOf(byte b) {
           return indexOf(b, 0L);
                }

       public long indexOf(byte b, long fromIndex) {
                    if (fromIndex < 0) {
                        throw new IllegalArgumentException("fromIndex < 0");
                    }
           Segment s = this.head;
           if (s == null) {
                        return -1L;
                    }
           long offset = 0;
           do {
               int segmentByteCount = s.limit - s.pos;
                        if (fromIndex >= segmentByteCount) {
                   fromIndex -= (long) segmentByteCount;
                        } else {
                   byte[] data = s.data;
                            long limit = s.limit;
                            for (long pos = ((long) s.pos) + fromIndex; pos < limit; pos++) {
                       if (data[(int) pos] == b) {
                                    return (offset + pos) - ((long) s.pos);
                                }
                            }
                   fromIndex = 0;
                        }
               offset += (long) segmentByteCount;
               s = s.next;
           } while (s != this.head);
           return -1L;
                }

                @Override 
       public void flush() {
                }

                @Override 
       public void close() {
                }

                @Override 
       public Timeout timeout() {
           return Timeout.NONE;
                }

       List<Integer> segmentSizes() {
           if (this.head == null) {
                        return Collections.emptyList();
                    }
           List<Integer> result = new ArrayList<>();
           result.add(Integer.valueOf(this.head.limit - this.head.pos));
                    for (Segment s = this.head.next; s != this.head; s = s.next) {
               result.add(Integer.valueOf(s.limit - s.pos));
                    }
           return result;
                }

       public boolean equals(Object o) {
                    int posB;
                    int posA;
           if (this == o) {
                        return true;
                    }
           if (!(o instanceof Buffer)) {
                        return false;
                    }
           Buffer that = (Buffer) o;
                    if (this.size != that.size) {
                        return false;
                    }
                    if (this.size == 0) {
                        return true;
                    }
           Segment sa = this.head;
           Segment sb = that.head;
           int posA2 = sa.pos;
           int posB2 = sb.pos;
           long pos = 0;
                    while (pos < this.size) {
               long count = Math.min(sa.limit - posA2, sb.limit - posB2);
               int i = 0;
                        while (true) {
                            posB = posB2;
                            posA = posA2;
                            if (i < count) {
                                posA2 = posA + 1;
                                posB2 = posB + 1;
                       if (sa.data[posA] != sb.data[posB]) {
                                    return false;
                                }
                       i++;
                            } else {
                                break;
                            }
                        }
               if (posA == sa.limit) {
                   sa = sa.next;
                   posA2 = sa.pos;
                        } else {
                            posA2 = posA;
                        }
               if (posB == sb.limit) {
                   sb = sb.next;
                   posB2 = sb.pos;
                        } else {
                            posB2 = posB;
                        }
               pos += count;
                    }
           return true;
                }

       public int hashCode() {
           Segment s = this.head;
           if (s == null) {
                        return 0;
                    }
           int result = 1;
           do {
                        int limit = s.limit;
                        for (int pos = s.pos; pos < limit; pos++) {
                   result = (result * 31) + s.data[pos];
                        }
               s = s.next;
           } while (s != this.head);
           return result;
                }

       public String toString() {
                    if (this.size == 0) {
               return "Buffer[size=0]";
                    }
                    if (this.size <= 16) {
               ByteString data = m2481clone().readByteString();
               return String.format("Buffer[size=%s data=%s]", Long.valueOf(this.size), data.hex());
                    }
                    try {
               MessageDigest md5 = MessageDigest.getInstance("MD5");
               md5.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
                        for (Segment s = this.head.next; s != this.head; s = s.next) {
                   md5.update(s.data, s.pos, s.limit - s.pos);
                        }
               return String.format("Buffer[size=%s md5=%s]", Long.valueOf(this.size), ByteString.m2468of(md5.digest()).hex());
                    } catch (NoSuchAlgorithmException e) {
               throw new AssertionError();
                    }
                }

                
       public Buffer m2481clone() {
           Buffer result = new Buffer();
                    if (this.size != 0) {
               result.write(this.head.data, this.head.pos, this.head.limit - this.head.pos);
                        for (Segment s = this.head.next; s != this.head; s = s.next) {
                   result.write(s.data, s.pos, s.limit - s.pos);
                        }
                    }
           return result;
                }
            }
