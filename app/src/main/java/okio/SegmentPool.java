            package okio;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    final class SegmentPool {
                static final SegmentPool INSTANCE = new SegmentPool();
                static final long MAX_SIZE = 65536;
                long byteCount;
                private Segment next;

/* 34 */        private SegmentPool() {
                }

/* 38 */        Segment take() {
                    synchronized (this) {
                        try {
/* 40 */                    if (this.next != null) {
/* 41 */                        Segment result = this.next;
/* 42 */                        this.next = result.next;
/* 43 */                        result.next = null;
/* 44 */                        this.byteCount -= 2048;
/* 48 */                        return result;
                            }
/* 48 */                    return new Segment();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 51 */        void recycle(Segment segment) {
/* 52 */            if (segment.next != null || segment.prev != null) {
                        throw new IllegalArgumentException();
                    }
                    synchronized (this) {
                        try {
                            if (this.byteCount + 2048 <= MAX_SIZE) {
/* 55 */                        this.byteCount += 2048;
/* 56 */                        segment.next = this.next;
/* 57 */                        segment.limit = 0;
/* 57 */                        segment.pos = 0;
/* 58 */                        this.next = segment;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
