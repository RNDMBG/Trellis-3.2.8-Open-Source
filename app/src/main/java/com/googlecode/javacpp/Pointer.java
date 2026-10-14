            package com.googlecode.javacpp;

            import java.lang.ref.PhantomReference;
            import java.lang.ref.ReferenceQueue;
            import java.lang.reflect.Constructor;
            import java.lang.reflect.Method;
            import java.lang.reflect.Modifier;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            import java.nio.ByteOrder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public class Pointer {
                private static final ReferenceQueue<Pointer> referenceQueue = new ReferenceQueue<>();
                protected long address;
                protected int capacity;
                private Deallocator deallocator;
                protected int limit;
                protected int position;

                /* JADX INFO: Access modifiers changed from: protected */
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

/* 55 */        static /* synthetic */ ReferenceQueue access$000() {
/* 56 */            return referenceQueue;
                }

/* 57 */        public Pointer() {
/* 277 */           this.address = 0L;
/* 279 */           this.position = 0;
/* 281 */           this.limit = 0;
/* 283 */           this.capacity = 0;
/* 285 */           this.deallocator = null;
                }

/* 78 */        public Pointer(Pointer p) {
/* 277 */           this.address = 0L;
/* 279 */           this.position = 0;
/* 281 */           this.limit = 0;
/* 283 */           this.capacity = 0;
/* 285 */           this.deallocator = null;
/* 80 */            if (p != null) {
/* 81 */                this.address = p.address;
/* 82 */                this.position = p.position;
/* 83 */                this.limit = p.limit;
/* 84 */                this.capacity = p.capacity;
/* 85 */                if (p.deallocator != null) {
/* 86 */                    this.deallocator = new C12251(p);
                        }
                    }
                }

                /* JADX INFO: renamed from: com.googlecode.javacpp.Pointer$1 */
/* 84 */        class C12251 implements Deallocator {
                    final /* synthetic */ Pointer val$p;

/* 85 */            C12251(Pointer pointer) {
                        this.val$p = pointer;
                    }

                    @Override // com.googlecode.javacpp.Pointer.Deallocator
/* 85 */            public void deallocate() {
/* 86 */                this.val$p.deallocate();
                    }
                }

/* 96 */        public Pointer(Buffer b) {
/* 277 */           this.address = 0L;
/* 279 */           this.position = 0;
/* 281 */           this.limit = 0;
/* 283 */           this.capacity = 0;
/* 285 */           this.deallocator = null;
/* 98 */            if (b != null) {
/* 99 */                allocate(b);
                    }
/* 101 */           if (!isNull()) {
/* 102 */               this.position = b.position();
/* 103 */               this.limit = b.limit();
/* 104 */               this.capacity = b.capacity();
/* 105 */               this.deallocator = new C12262(b);
                    }
                }

                /* JADX INFO: renamed from: com.googlecode.javacpp.Pointer$2 */
/* 103 */       class C12262 implements Deallocator {

                    /* JADX INFO: renamed from: bb */
                    Buffer f2532bb;
                    final /* synthetic */ Buffer val$b;

/* 104 */           C12262(Buffer buffer) {
                        this.val$b = buffer;
                        this.f2532bb = this.val$b;
                    }

                    @Override // com.googlecode.javacpp.Pointer.Deallocator
/* 104 */           public void deallocate() {
/* 105 */               this.f2532bb = null;
                    }
                }

/* 118 */       void init(long allocatedAddress, int allocatedCapacity, long deallocatorAddress) {
/* 119 */           this.address = allocatedAddress;
/* 120 */           this.position = 0;
/* 121 */           this.limit = allocatedCapacity;
/* 122 */           this.capacity = allocatedCapacity;
/* 123 */           deallocator(new NativeDeallocator(this, deallocatorAddress));
                }

/* 137 */       protected static <P extends Pointer> P withDeallocator(P p) {
/* 138 */           return (P) p.deallocator(new CustomDeallocator(p));
                }

/* 148 */       protected static class CustomDeallocator extends DeallocatorReference implements Deallocator {
                    Method method;
                    Pointer pointer;

/* 149 */           public CustomDeallocator(Pointer p) {
/* 150 */               super(p, null);
/* 176 */               this.pointer = null;
/* 177 */               this.method = null;
/* 151 */               this.deallocator = this;
/* 152 */               Class<?> cls = p.getClass();
/* 153 */               Method[] arr$ = cls.getDeclaredMethods();
                        for (Method m : arr$) {
/* 154 */                   Class<?>[] parameterTypes = m.getParameterTypes();
/* 155 */                   if (Modifier.isStatic(m.getModifiers()) && m.getReturnType().equals(Void.TYPE) && m.getName().equals("deallocate") && parameterTypes.length == 1 && Pointer.class.isAssignableFrom(parameterTypes[0])) {
/* 158 */                       m.setAccessible(true);
/* 159 */                       this.method = m;
                                break;
                            }
                        }
/* 163 */               if (this.method == null) {
/* 164 */                   throw new RuntimeException(new NoSuchMethodException("static void " + cls.getCanonicalName() + ".deallocate(" + Pointer.class.getCanonicalName() + ")"));
                        }
                        try {
/* 168 */                   Constructor<?> constructor = cls.getConstructor(Pointer.class);
/* 169 */                   constructor.setAccessible(true);
                            this.pointer = (Pointer) constructor.newInstance(p);
                        } catch (Exception ex) {
/* 172 */                   throw new RuntimeException(ex);
                        }
                    }

                    @Override // com.googlecode.javacpp.Pointer.Deallocator
/* 180 */           public void deallocate() {
                        try {
/* 181 */                   this.method.invoke(null, this.pointer);
/* 182 */                   this.pointer.setNull();
                        } catch (Exception ex) {
/* 184 */                   throw new RuntimeException(ex);
                        }
                    }
                }

/* 193 */       protected static class NativeDeallocator extends DeallocatorReference implements Deallocator {
                    private long allocatedAddress;
                    private long deallocatorAddress;

                    private native void deallocate(long j, long j2);

/* 194 */           NativeDeallocator(Pointer p, long deallocatorAddress) {
/* 195 */               super(p, null);
/* 196 */               this.deallocator = this;
/* 197 */               this.allocatedAddress = p.address;
/* 198 */               this.deallocatorAddress = deallocatorAddress;
                    }

                    @Override // com.googlecode.javacpp.Pointer.Deallocator
/* 204 */           public void deallocate() {
                        if (this.allocatedAddress != 0 && this.deallocatorAddress != 0) {
/* 208 */                   deallocate(this.allocatedAddress, this.deallocatorAddress);
/* 209 */                   this.deallocatorAddress = 0L;
                            this.allocatedAddress = 0L;
                        }
                    }
                }

/* 220 */       private static class DeallocatorReference extends PhantomReference<Pointer> {
                    static DeallocatorReference head = null;
                    Deallocator deallocator;
                    DeallocatorReference next;
                    DeallocatorReference prev;

/* 221 */           DeallocatorReference(Pointer p, Deallocator deallocator) {
/* 222 */               super(p, Pointer.access$000());
/* 227 */               this.prev = null;
                        this.next = null;
/* 223 */               this.deallocator = deallocator;
                    }

/* 230 */           final void add() {
                        synchronized (DeallocatorReference.class) {
                            try {
/* 232 */                       if (head == null) {
/* 233 */                           head = this;
                                } else {
/* 235 */                           this.next = head;
/* 236 */                           DeallocatorReference deallocatorReference = this.next;
                                    head = this;
                                    deallocatorReference.prev = this;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

/* 241 */           final void remove() {
                        synchronized (DeallocatorReference.class) {
                            try {
/* 243 */                       if (this.prev != this || this.next != this) {
/* 246 */                           if (this.prev == null) {
/* 247 */                               head = this.next;
                                    } else {
/* 249 */                               this.prev.next = this.next;
                                    }
/* 251 */                           if (this.next != null) {
/* 252 */                               this.next.prev = this.prev;
                                    }
/* 254 */                           this.next = this;
                                    this.prev = this;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // java.lang.ref.Reference
/* 258 */           public void clear() {
/* 259 */               super.clear();
/* 260 */               this.deallocator.deallocate();
                    }
                }

/* 268 */       public static void deallocateReferences() {
                    while (true) {
/* 270 */               DeallocatorReference r = (DeallocatorReference) referenceQueue.poll();
                        if (r != null) {
/* 271 */                   r.clear();
/* 272 */                   r.remove();
                        } else {
/* 274 */                   return;
                        }
                    }
                }

/* 288 */       public boolean isNull() {
                    return this.address == 0;
                }

/* 292 */       public void setNull() {
/* 293 */           this.address = 0L;
                }

/* 297 */       public int position() {
/* 298 */           return this.position;
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 307 */       public <P extends Pointer> P position(int position) {
/* 308 */           this.position = position;
/* 309 */           return (P) this;
                }

/* 313 */       public int limit() {
/* 314 */           return this.limit;
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 323 */       public <P extends Pointer> P limit(int limit) {
/* 324 */           this.limit = limit;
/* 325 */           return (P) this;
                }

/* 329 */       public int capacity() {
/* 330 */           return this.capacity;
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 339 */       public <P extends Pointer> P capacity(int capacity) {
/* 340 */           this.limit = capacity;
/* 341 */           this.capacity = capacity;
/* 342 */           return (P) this;
                }

/* 346 */       protected Deallocator deallocator() {
/* 347 */           return this.deallocator;
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 357 */       protected <P extends Pointer> P deallocator(Deallocator deallocator) {
/* 358 */           if (this.deallocator != null) {
/* 359 */               this.deallocator.deallocate();
/* 360 */               this.deallocator = null;
                    }
/* 362 */           deallocateReferences();
/* 363 */           if (deallocator != null) {
/* 364 */               this.deallocator = deallocator;
/* 365 */               DeallocatorReference r = deallocator instanceof DeallocatorReference ? (DeallocatorReference) deallocator : new DeallocatorReference(this, deallocator);
/* 368 */               r.add();
                    }
/* 370 */           return (P) this;
                }

/* 374 */       public void deallocate() {
/* 375 */           this.deallocator.deallocate();
/* 376 */           this.address = 0L;
                }

/* 380 */       public int offsetof(String member) {
                    try {
/* 383 */               Class<?> cls = getClass();
/* 384 */               if (cls == Pointer.class) {
/* 381 */                   return -1;
                        }
/* 385 */               int offset = Loader.offsetof((Class<? extends Pointer>) cls, member);
/* 388 */               return offset;
                    } catch (NullPointerException e) {
/* 381 */               return -1;
                    }
                }

/* 392 */       public int sizeof() {
/* 393 */           Class<?> cls = getClass();
/* 394 */           if (cls == Pointer.class || cls == BytePointer.class) {
/* 396 */               return 1;
                    }
                    return offsetof("sizeof");
                }

/* 410 */       public ByteBuffer asByteBuffer() {
/* 411 */           if (isNull()) {
/* 412 */               return null;
                    }
/* 414 */           if (this.limit > 0 && this.limit < this.position) {
/* 415 */               throw new IllegalArgumentException("limit < position: (" + this.limit + " < " + this.position + ")");
                    }
/* 417 */           int valueSize = sizeof();
/* 418 */           int arrayPosition = this.position;
/* 419 */           int arrayLimit = this.limit;
/* 420 */           this.position = valueSize * arrayPosition;
                    this.limit = (arrayLimit <= 0 ? arrayPosition + 1 : arrayLimit) * valueSize;
/* 422 */           ByteBuffer byteBufferOrder = asDirectBuffer().order(ByteOrder.nativeOrder());
/* 423 */           this.position = arrayPosition;
/* 424 */           this.limit = arrayLimit;
/* 425 */           return byteBufferOrder;
                }

/* 439 */       public Buffer asBuffer() {
/* 440 */           return asByteBuffer();
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 457 */       public <P extends Pointer> P put(Pointer p) {
/* 458 */           if (p.limit > 0 && p.limit < p.position) {
/* 459 */               throw new IllegalArgumentException("limit < position: (" + p.limit + " < " + p.position + ")");
                    }
/* 461 */           int size = sizeof();
/* 462 */           int psize = p.sizeof();
/* 463 */           int length = psize * (p.limit <= 0 ? 1 : p.limit - p.position);
/* 464 */           this.position *= size;
/* 465 */           p.position *= psize;
/* 466 */           memcpy(this, p, length);
/* 467 */           this.position /= size;
/* 468 */           p.position /= psize;
/* 469 */           return (P) this;
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 480 */       public <P extends Pointer> P fill(int b) {
/* 481 */           if (this.limit > 0 && this.limit < this.position) {
/* 482 */               throw new IllegalArgumentException("limit < position: (" + this.limit + " < " + this.position + ")");
                    }
/* 484 */           int size = sizeof();
/* 485 */           int length = size * (this.limit <= 0 ? 1 : this.limit - this.position);
/* 486 */           this.position *= size;
/* 487 */           memset(this, b, length);
/* 488 */           this.position /= size;
/* 489 */           return (P) this;
                }

/* 491 */       public <P extends Pointer> P zero() {
/* 492 */           return (P) fill(0);
                }

/* 502 */       public boolean equals(Object obj) {
/* 503 */           if (obj == this) {
                        return true;
                    }
/* 505 */           if (obj == null) {
/* 506 */               return isNull();
                    }
/* 507 */           if (obj.getClass() != getClass()) {
                        return false;
                    }
/* 510 */           Pointer other = (Pointer) obj;
                    return this.address == other.address && this.position == other.position;
                }

/* 516 */       public int hashCode() {
/* 517 */           return (int) this.address;
                }

/* 522 */       public String toString() {
/* 523 */           return getClass().getName() + "[address=0x" + Long.toHexString(this.address) + ",position=" + this.position + ",limit=" + this.limit + ",capacity=" + this.capacity + ",deallocator=" + this.deallocator + "]";
                }
            }
