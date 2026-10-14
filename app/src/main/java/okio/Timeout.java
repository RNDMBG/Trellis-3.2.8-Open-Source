            package okio;

            import java.io.IOException;
            import java.io.InterruptedIOException;
            import java.util.concurrent.TimeUnit;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class Timeout {
                public static final Timeout NONE = new C13041();
                private long deadlineNanoTime;
                private boolean hasDeadline;
                private long timeoutNanos;

                /* JADX INFO: renamed from: okio.Timeout$1 */
/* 46 */        static class C13041 extends Timeout {
/* 47 */            C13041() {
                    }

                    @Override // okio.Timeout
/* 49 */            public Timeout timeout(long timeout, TimeUnit unit) {
/* 50 */                return this;
                    }

                    @Override // okio.Timeout
/* 53 */            public Timeout deadlineNanoTime(long deadlineNanoTime) {
/* 54 */                return this;
                    }

                    @Override // okio.Timeout
/* 57 */            public void throwIfReached() throws IOException {
                    }
                }

/* 80 */        public Timeout timeout(long timeout, TimeUnit unit) {
                    if (timeout < 0) {
                        throw new IllegalArgumentException("timeout < 0: " + timeout);
                    }
/* 82 */            if (unit == null) {
                        throw new IllegalArgumentException("unit == null");
                    }
/* 83 */            this.timeoutNanos = unit.toNanos(timeout);
/* 84 */            return this;
                }

/* 88 */        public long timeoutNanos() {
/* 89 */            return this.timeoutNanos;
                }

/* 93 */        public boolean hasDeadline() {
/* 94 */            return this.hasDeadline;
                }

/* 103 */       public long deadlineNanoTime() {
/* 104 */           if (this.hasDeadline) {
/* 105 */               return this.deadlineNanoTime;
                    }
                    throw new IllegalStateException("No deadline");
                }

/* 113 */       public Timeout deadlineNanoTime(long deadlineNanoTime) {
/* 114 */           this.hasDeadline = true;
/* 115 */           this.deadlineNanoTime = deadlineNanoTime;
/* 116 */           return this;
                }

/* 120 */       public final Timeout deadline(long duration, TimeUnit unit) {
                    if (duration <= 0) {
                        throw new IllegalArgumentException("duration <= 0: " + duration);
                    }
/* 122 */           if (unit == null) {
                        throw new IllegalArgumentException("unit == null");
                    }
/* 123 */           return deadlineNanoTime(System.nanoTime() + unit.toNanos(duration));
                }

/* 127 */       public Timeout clearTimeout() {
/* 128 */           this.timeoutNanos = 0L;
/* 129 */           return this;
                }

/* 133 */       public Timeout clearDeadline() {
/* 134 */           this.hasDeadline = false;
/* 135 */           return this;
                }

/* 144 */       public void throwIfReached() throws IOException {
/* 145 */           if (Thread.interrupted()) {
/* 146 */               throw new InterruptedIOException();
                    }
/* 149 */           if (this.hasDeadline && System.nanoTime() > this.deadlineNanoTime) {
/* 150 */               throw new IOException("deadline reached");
                    }
                }
            }
