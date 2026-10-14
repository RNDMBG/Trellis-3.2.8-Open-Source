            package okio;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    final class Segment {
                static final int SIZE = 2048;
                final byte[] data = new byte[2048];
                int limit;
                Segment next;
                int pos;
                Segment prev;

/* 26 */        Segment() {
                }

/* 52 */        public Segment pop() {
/* 53 */            Segment result = this.next != this ? this.next : null;
/* 54 */            this.prev.next = this.next;
/* 55 */            this.next.prev = this.prev;
/* 56 */            this.next = null;
/* 57 */            this.prev = null;
/* 58 */            return result;
                }

/* 65 */        public Segment push(Segment segment) {
/* 66 */            segment.prev = this;
/* 67 */            segment.next = this.next;
/* 68 */            this.next.prev = segment;
/* 69 */            this.next = segment;
/* 70 */            return segment;
                }

/* 81 */        public Segment split(int byteCount) {
/* 83 */            int bSize = (this.limit - this.pos) - byteCount;
/* 84 */            if (byteCount <= 0 || bSize <= 0) {
                        throw new IllegalArgumentException();
                    }
/* 87 */            if (byteCount < bSize) {
/* 89 */                Segment before = SegmentPool.INSTANCE.take();
/* 82 */                System.arraycopy(this.data, this.pos, before.data, before.pos, byteCount);
/* 82 */                this.pos += byteCount;
/* 82 */                before.limit += byteCount;
/* 93 */                this.prev.push(before);
/* 102 */               return before;
                    }
/* 97 */            Segment after = SegmentPool.INSTANCE.take();
/* 82 */            System.arraycopy(this.data, this.pos + byteCount, after.data, after.pos, bSize);
/* 99 */            this.limit -= bSize;
/* 100 */           after.limit += bSize;
/* 101 */           push(after);
                    return this;
                }

/* 110 */       public void compact() {
/* 111 */           if (this.prev == this) {
                        throw new IllegalStateException();
                    }
/* 112 */           if ((this.prev.limit - this.prev.pos) + (this.limit - this.pos) <= 2048) {
/* 113 */               writeTo(this.prev, this.limit - this.pos);
/* 114 */               pop();
/* 115 */               SegmentPool.INSTANCE.recycle(this);
                    }
                }

/* 121 */       public void writeTo(Segment sink, int byteCount) {
/* 122 */           if ((sink.limit - sink.pos) + byteCount > 2048) {
                        throw new IllegalArgumentException();
                    }
/* 124 */           if (sink.limit + byteCount > 2048) {
/* 126 */               System.arraycopy(sink.data, sink.pos, sink.data, 0, sink.limit - sink.pos);
/* 127 */               sink.limit -= sink.pos;
/* 128 */               sink.pos = 0;
                    }
/* 131 */           System.arraycopy(this.data, this.pos, sink.data, sink.limit, byteCount);
/* 132 */           sink.limit += byteCount;
/* 133 */           this.pos += byteCount;
                }
            }
