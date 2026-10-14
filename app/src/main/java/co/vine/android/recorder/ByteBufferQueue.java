            package co.vine.android.recorder;

            import android.support.annotation.Nullable;
            import co.vine.android.util.AllocatedByteBuffer;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import java.lang.ref.WeakReference;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class ByteBufferQueue {
                private static final ArrayList<WeakReference<ByteBufferQueue>> sQueues = new ArrayList<>();
                private final int[] LOCK = new int[0];
                private AllocatedByteBuffer mAllocatedByteBuffer;
                private byte[] mGetBuffer;
                private int mInitialCount;
                private boolean mPutHasEnded;
                private int mSpaceLeft;
                private int mStartIndex;
                private ArrayList<Integer> mStarts;
                private ArrayList<WeakReference<Object>> mTags;

                public interface MemoryResponder {
                    void requestForMoreMemory();
                }

/* 34 */        public ByteBufferQueue(int count, int singleCapacity, MemoryResponder memoryResponder) {
/* 36 */            if (sQueues.size() > 20) {
/* 37 */                sQueues.clear();
                    }
/* 39 */            sQueues.add(new WeakReference<>(this));
/* 40 */            int n = 0;
                    for (WeakReference<ByteBufferQueue> queue : sQueues) {
/* 42 */                if (queue.get() != null) {
/* 43 */                    n++;
                        }
                    }
/* 46 */            CrashUtil.log("[mem] Current byte buffer queue count: {}.", Integer.valueOf(n));
/* 48 */            if (SLog.sLogsOn) {
/* 49 */                Runtime runtime = Runtime.getRuntime();
/* 50 */                long usedMem = (runtime.totalMemory() - runtime.freeMemory()) / 1048576;
/* 51 */                long maxHeap = runtime.maxMemory() / 1048576;
/* 53 */                SLog.m447d("[mem] Allocating: {}.", Long.valueOf(((long) (singleCapacity * count)) / 1048576));
/* 54 */                SLog.m447d("[mem] Free: {}.", Long.valueOf(runtime.freeMemory() / 1048576));
/* 55 */                SLog.m447d("[mem] Used mem: {}.", Long.valueOf(usedMem));
/* 56 */                SLog.m447d("[mem] Max mem: {}.", Long.valueOf(maxHeap));
                    }
                    try {
/* 59 */                reset(count, singleCapacity);
                    } catch (OutOfMemoryError e) {
/* 61 */                if (memoryResponder != null) {
/* 62 */                    memoryResponder.requestForMoreMemory();
                        }
/* 64 */                System.gc();
/* 65 */                reset(count, singleCapacity);
                    }
                }

/* 69 */        public void reset(int count, int capacity) {
                    synchronized (this.LOCK) {
                        try {
/* 71 */                    this.mSpaceLeft = count;
/* 72 */                    this.mInitialCount = count;
/* 73 */                    if (this.mAllocatedByteBuffer == null || this.mAllocatedByteBuffer.capacity() < capacity * count) {
/* 74 */                        release();
/* 75 */                        this.mAllocatedByteBuffer = AllocatedByteBuffer.allocate(capacity * count);
                            }
/* 77 */                    if (this.mGetBuffer == null || this.mGetBuffer.length != capacity) {
/* 78 */                        this.mGetBuffer = new byte[capacity];
                            }
/* 80 */                    this.mAllocatedByteBuffer.getBuffer().position(0);
/* 81 */                    this.mStarts = new ArrayList<>();
/* 82 */                    this.mTags = new ArrayList<>();
/* 83 */                    this.mPutHasEnded = false;
/* 84 */                    this.mStartIndex = 0;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 89 */        protected void finalize() throws Throwable {
/* 90 */            super.finalize();
/* 91 */            release();
                }

/* 94 */        public void release() {
/* 95 */            if (this.mAllocatedByteBuffer != null) {
/* 96 */                this.mAllocatedByteBuffer.deallocate();
/* 97 */                this.mAllocatedByteBuffer = null;
                    }
                }

/* 101 */       public boolean put(QueueItem item) {
                    boolean z = false;
                    synchronized (this.LOCK) {
                        try {
/* 103 */                   this.mSpaceLeft--;
/* 104 */                   if (this.mSpaceLeft >= 0) {
/* 107 */                       ByteBuffer buffer = this.mAllocatedByteBuffer.getBuffer();
/* 108 */                       this.mStarts.add(Integer.valueOf(buffer.position()));
/* 109 */                       this.mTags.add(new WeakReference<>(item.tag));
/* 110 */                       buffer.put(item.bytes);
/* 111 */                       if (buffer.position() == buffer.limit()) {
/* 112 */                           buffer.position(0);
                                }
/* 114 */                       z = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 114 */           return z;
                }

/* 120 */       public void endOfPut() {
/* 121 */           this.mPutHasEnded = true;
                }

/* 124 */       public boolean isEndOfPut() {
/* 125 */           return this.mPutHasEnded;
                }

/* 132 */       public static class QueueItem {
                    final byte[] bytes;

                    @Nullable
                    final Object tag;

/* 133 */           public QueueItem(byte[] bytes, Object tag) {
/* 135 */               this.bytes = bytes;
/* 136 */               this.tag = tag;
                    }
                }

/* 140 */       public QueueItem get() {
                    QueueItem queueItem;
                    synchronized (this.LOCK) {
                        try {
/* 142 */                   if (this.mStartIndex == this.mStarts.size()) {
/* 143 */                       queueItem = null;
                            } else {
/* 145 */                       Integer next = this.mStarts.get(this.mStartIndex);
/* 146 */                       Object tag = this.mTags.get(this.mStartIndex).get();
/* 147 */                       this.mStartIndex++;
/* 148 */                       this.mSpaceLeft++;
/* 149 */                       ByteBuffer buffer = this.mAllocatedByteBuffer.getBuffer();
/* 150 */                       int originalPosition = buffer.position();
/* 151 */                       buffer.position(next.intValue());
/* 152 */                       buffer.get(this.mGetBuffer);
/* 153 */                       buffer.position(originalPosition);
/* 154 */                       queueItem = new QueueItem(this.mGetBuffer, tag);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 154 */           return queueItem;
                }

/* 159 */       public int size() {
/* 160 */           return this.mInitialCount - this.mSpaceLeft;
                }

/* 163 */       public boolean isFresh() {
                    return this.mStarts.size() == 0;
                }
            }
