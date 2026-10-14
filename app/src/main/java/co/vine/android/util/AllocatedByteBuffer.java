            package co.vine.android.util;

            import com.googlecode.javacpp.BytePointer;
            import java.nio.ByteBuffer;
            
            
    public class AllocatedByteBuffer {
                private final ByteBuffer mBuffer;
                private final BytePointer mPointer;

        public static AllocatedByteBuffer allocate(int capacity) {
            






                    try {
                        BytePointer pointer = new BytePointer(capacity);
                        ByteBuffer buffer = pointer.asByteBuffer();
                        if (buffer != null) {
                            return new AllocatedByteBuffer(pointer, buffer);
                        }
                        pointer.deallocate();
                    } catch (UnsatisfiedLinkError ignored) {
                        
                    } catch (NoClassDefFoundError ignored) {
                        
                    } catch (RuntimeException ignored) {
                        
                    }
                    return new AllocatedByteBuffer(null, ByteBuffer.allocate(capacity));
                }

        public AllocatedByteBuffer(BytePointer pointer, ByteBuffer buffer) {
            this.mPointer = pointer;
            this.mBuffer = buffer;
                }

        public ByteBuffer getBuffer() {
            return this.mBuffer;
                }

        public int capacity() {
            return this.mBuffer.capacity();
                }

        public boolean isNative() {
                    return this.mPointer != null;
                }

        public void deallocate() {
            if (this.mPointer != null && !this.mPointer.isNull()) {
                this.mPointer.deallocate();
                    }
                }
            }
