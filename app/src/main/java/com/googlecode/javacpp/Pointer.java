            package com.googlecode.javacpp;

            import java.lang.ref.PhantomReference;
            import java.lang.ref.ReferenceQueue;
            import java.lang.reflect.Constructor;
            import java.lang.reflect.Method;
            import java.lang.reflect.Modifier;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            import java.nio.ByteOrder;
            
            
    public class Pointer {
                private static final ReferenceQueue<Pointer> referenceQueue = new ReferenceQueue<>();
                protected long address;
                protected int capacity;
                private Deallocator deallocator;
                protected int limit;
                protected int position;

                
                public interface Deallocator {
                    void deallocate();
                }

                private native void allocate(Buffer buffer);

                private native ByteBuffer asDirectBuffer();

                public static native Pointer memchr(Pointer pointer, int i, long j);

                public static native int memcmp(Pointer pointer, Pointer pointer2, long j);

                public static native Pointer memcpy(Pointer pointer, Pointer pointer2, long j);

                public static native Pointer memmove(Pointer pointer, Pointer pointer2, long j);

                public static native Pointer memset(Pointer pointer, int i, long j);

        static  ReferenceQueue access$000() {
            return referenceQueue;
                }

        public Pointer() {
           this.address = 0L;
           this.position = 0;
           this.limit = 0;
           this.capacity = 0;
           this.deallocator = null;
                }

        public Pointer(Pointer p) {
           this.address = 0L;
           this.position = 0;
           this.limit = 0;
           this.capacity = 0;
           this.deallocator = null;
            if (p != null) {
                this.address = p.address;
                this.position = p.position;
                this.limit = p.limit;
                this.capacity = p.capacity;
                if (p.deallocator != null) {
                    this.deallocator = new C12251(p);
                        }
                    }
                }

                
        class C12251 implements Deallocator {
                    final  Pointer val$p;

            C12251(Pointer pointer) {
                        this.val$p = pointer;
                    }

                    @Override 
            public void deallocate() {
                this.val$p.deallocate();
                    }
                }

        public Pointer(Buffer b) {
           this.address = 0L;
           this.position = 0;
           this.limit = 0;
           this.capacity = 0;
           this.deallocator = null;
            if (b != null) {
                allocate(b);
                    }
           if (!isNull()) {
               this.position = b.position();
               this.limit = b.limit();
               this.capacity = b.capacity();
               this.deallocator = new C12262(b);
                    }
                }

                
       class C12262 implements Deallocator {

                    
                    Buffer f2532bb;
                    final  Buffer val$b;

           C12262(Buffer buffer) {
                        this.val$b = buffer;
                        this.f2532bb = this.val$b;
                    }

                    @Override 
           public void deallocate() {
               this.f2532bb = null;
                    }
                }

       void init(long allocatedAddress, int allocatedCapacity, long deallocatorAddress) {
           this.address = allocatedAddress;
           this.position = 0;
           this.limit = allocatedCapacity;
           this.capacity = allocatedCapacity;
           deallocator(new NativeDeallocator(this, deallocatorAddress));
                }

       protected static <P extends Pointer> P withDeallocator(P p) {
           return (P) p.deallocator(new CustomDeallocator(p));
                }

       protected static class CustomDeallocator extends DeallocatorReference implements Deallocator {
                    Method method;
                    Pointer pointer;

           public CustomDeallocator(Pointer p) {
               super(p, null);
               this.pointer = null;
               this.method = null;
               this.deallocator = this;
               Class<?> cls = p.getClass();
               Method[] arr$ = cls.getDeclaredMethods();
                        for (Method m : arr$) {
                   Class<?>[] parameterTypes = m.getParameterTypes();
                   if (Modifier.isStatic(m.getModifiers()) && m.getReturnType().equals(Void.TYPE) && m.getName().equals("deallocate") && parameterTypes.length == 1 && Pointer.class.isAssignableFrom(parameterTypes[0])) {
                       m.setAccessible(true);
                       this.method = m;
                                break;
                            }
                        }
               if (this.method == null) {
                   throw new RuntimeException(new NoSuchMethodException("static void " + cls.getCanonicalName() + ".deallocate(" + Pointer.class.getCanonicalName() + ")"));
                        }
                        try {
                   Constructor<?> constructor = cls.getConstructor(Pointer.class);
                   constructor.setAccessible(true);
                            this.pointer = (Pointer) constructor.newInstance(p);
                        } catch (Exception ex) {
                   throw new RuntimeException(ex);
                        }
                    }

                    @Override 
           public void deallocate() {
                        try {
                   this.method.invoke(null, this.pointer);
                   this.pointer.setNull();
                        } catch (Exception ex) {
                   throw new RuntimeException(ex);
                        }
                    }
                }

       protected static class NativeDeallocator extends DeallocatorReference implements Deallocator {
                    private long allocatedAddress;
                    private long deallocatorAddress;

                    private native void deallocate(long j, long j2);

           NativeDeallocator(Pointer p, long deallocatorAddress) {
               super(p, null);
               this.deallocator = this;
               this.allocatedAddress = p.address;
               this.deallocatorAddress = deallocatorAddress;
                    }

                    @Override 
           public void deallocate() {
                        if (this.allocatedAddress != 0 && this.deallocatorAddress != 0) {
                   deallocate(this.allocatedAddress, this.deallocatorAddress);
                   this.deallocatorAddress = 0L;
                            this.allocatedAddress = 0L;
                        }
                    }
                }

       private static class DeallocatorReference extends PhantomReference<Pointer> {
                    static DeallocatorReference head = null;
                    Deallocator deallocator;
                    DeallocatorReference next;
                    DeallocatorReference prev;

           DeallocatorReference(Pointer p, Deallocator deallocator) {
               super(p, Pointer.access$000());
               this.prev = null;
                        this.next = null;
               this.deallocator = deallocator;
                    }

           final void add() {
                        synchronized (DeallocatorReference.class) {
                            try {
                       if (head == null) {
                           head = this;
                                } else {
                           this.next = head;
                           DeallocatorReference deallocatorReference = this.next;
                                    head = this;
                                    deallocatorReference.prev = this;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

           final void remove() {
                        synchronized (DeallocatorReference.class) {
                            try {
                       if (this.prev != this || this.next != this) {
                           if (this.prev == null) {
                               head = this.next;
                                    } else {
                               this.prev.next = this.next;
                                    }
                           if (this.next != null) {
                               this.next.prev = this.prev;
                                    }
                           this.next = this;
                                    this.prev = this;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override 
           public void clear() {
               super.clear();
               this.deallocator.deallocate();
                    }
                }

       public static void deallocateReferences() {
                    while (true) {
               DeallocatorReference r = (DeallocatorReference) referenceQueue.poll();
                        if (r != null) {
                   r.clear();
                   r.remove();
                        } else {
                   return;
                        }
                    }
                }

       public boolean isNull() {
                    return this.address == 0;
                }

       public void setNull() {
           this.address = 0L;
                }

       public int position() {
           return this.position;
                }

                
       public <P extends Pointer> P position(int position) {
           this.position = position;
           return (P) this;
                }

       public int limit() {
           return this.limit;
                }

                
       public <P extends Pointer> P limit(int limit) {
           this.limit = limit;
           return (P) this;
                }

       public int capacity() {
           return this.capacity;
                }

                
       public <P extends Pointer> P capacity(int capacity) {
           this.limit = capacity;
           this.capacity = capacity;
           return (P) this;
                }

       protected Deallocator deallocator() {
           return this.deallocator;
                }

                
       protected <P extends Pointer> P deallocator(Deallocator deallocator) {
           if (this.deallocator != null) {
               this.deallocator.deallocate();
               this.deallocator = null;
                    }
           deallocateReferences();
           if (deallocator != null) {
               this.deallocator = deallocator;
               DeallocatorReference r = deallocator instanceof DeallocatorReference ? (DeallocatorReference) deallocator : new DeallocatorReference(this, deallocator);
               r.add();
                    }
           return (P) this;
                }

       public void deallocate() {
           this.deallocator.deallocate();
           this.address = 0L;
                }

       public int offsetof(String member) {
                    try {
               Class<?> cls = getClass();
               if (cls == Pointer.class) {
                   return -1;
                        }
               int offset = Loader.offsetof((Class<? extends Pointer>) cls, member);
               return offset;
                    } catch (NullPointerException e) {
               return -1;
                    }
                }

       public int sizeof() {
           Class<?> cls = getClass();
           if (cls == Pointer.class || cls == BytePointer.class) {
               return 1;
                    }
                    return offsetof("sizeof");
                }

       public ByteBuffer asByteBuffer() {
           if (isNull()) {
               return null;
                    }
           if (this.limit > 0 && this.limit < this.position) {
               throw new IllegalArgumentException("limit < position: (" + this.limit + " < " + this.position + ")");
                    }
           int valueSize = sizeof();
           int arrayPosition = this.position;
           int arrayLimit = this.limit;
           this.position = valueSize * arrayPosition;
                    this.limit = (arrayLimit <= 0 ? arrayPosition + 1 : arrayLimit) * valueSize;
           ByteBuffer byteBufferOrder = asDirectBuffer().order(ByteOrder.nativeOrder());
           this.position = arrayPosition;
           this.limit = arrayLimit;
           return byteBufferOrder;
                }

       public Buffer asBuffer() {
           return asByteBuffer();
                }

                
       public <P extends Pointer> P put(Pointer p) {
           if (p.limit > 0 && p.limit < p.position) {
               throw new IllegalArgumentException("limit < position: (" + p.limit + " < " + p.position + ")");
                    }
           int size = sizeof();
           int psize = p.sizeof();
           int length = psize * (p.limit <= 0 ? 1 : p.limit - p.position);
           this.position *= size;
           p.position *= psize;
           memcpy(this, p, length);
           this.position /= size;
           p.position /= psize;
           return (P) this;
                }

                
       public <P extends Pointer> P fill(int b) {
           if (this.limit > 0 && this.limit < this.position) {
               throw new IllegalArgumentException("limit < position: (" + this.limit + " < " + this.position + ")");
                    }
           int size = sizeof();
           int length = size * (this.limit <= 0 ? 1 : this.limit - this.position);
           this.position *= size;
           memset(this, b, length);
           this.position /= size;
           return (P) this;
                }

       public <P extends Pointer> P zero() {
           return (P) fill(0);
                }

       public boolean equals(Object obj) {
           if (obj == this) {
                        return true;
                    }
           if (obj == null) {
               return isNull();
                    }
           if (obj.getClass() != getClass()) {
                        return false;
                    }
           Pointer other = (Pointer) obj;
                    return this.address == other.address && this.position == other.position;
                }

       public int hashCode() {
           return (int) this.address;
                }

       public String toString() {
           return getClass().getName() + "[address=0x" + Long.toHexString(this.address) + ",position=" + this.position + ",limit=" + this.limit + ",capacity=" + this.capacity + ",deallocator=" + this.deallocator + "]";
                }
            }
