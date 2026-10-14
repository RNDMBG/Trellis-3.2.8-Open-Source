            package co.vine.android.util;

            import com.googlecode.javacpp.BytePointer;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class AllocatedByteBuffer {
                private final ByteBuffer mBuffer;
                private final BytePointer mPointer;

/* 15 */        public static AllocatedByteBuffer allocate(int capacity) {
/* 16 */            /*
                 * The original recorder preferred a JavaCPP-backed buffer, but that
                 * native allocator is not present in every supported ABI.  In that
                 * case BytePointer throws UnsatisfiedLinkError before recording can
                 * even open.  The queue only requires a ByteBuffer, so keep the
                 * native path when available and use a regular heap buffer otherwise.
                 */
                    try {
                        BytePointer pointer = new BytePointer(capacity);
                        ByteBuffer buffer = pointer.asByteBuffer();
                        if (buffer != null) {
                            return new AllocatedByteBuffer(pointer, buffer);
                        }
                        pointer.deallocate();
                    } catch (UnsatisfiedLinkError ignored) {
                        // Java fallback below for ABIs without the JavaCPP library.
                    } catch (NoClassDefFoundError ignored) {
                        // Java fallback below when the optional native binding is absent.
                    } catch (RuntimeException ignored) {
                        // JavaCPP wraps a missing JNI implementation in RuntimeException.
                    }
                    return new AllocatedByteBuffer(null, ByteBuffer.allocate(capacity));
                }

/* 25 */        public AllocatedByteBuffer(BytePointer pointer, ByteBuffer buffer) {
/* 27 */            this.mPointer = pointer;
/* 28 */            this.mBuffer = buffer;
                }

/* 31 */        public ByteBuffer getBuffer() {
/* 32 */            return this.mBuffer;
                }

/* 35 */        public int capacity() {
/* 36 */            return this.mBuffer.capacity();
                }

/* 39 */        public boolean isNative() {
                    return this.mPointer != null;
                }

/* 43 */        public void deallocate() {
/* 44 */            if (this.mPointer != null && !this.mPointer.isNull()) {
/* 46 */                this.mPointer.deallocate();
                    }
                }
            }
