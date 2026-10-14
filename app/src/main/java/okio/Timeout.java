            package okio;

            import java.io.IOException;
            import java.io.InterruptedIOException;
            import java.util.concurrent.TimeUnit;
            
            
    public class Timeout {
                public static final Timeout NONE = new C13041();
                private long deadlineNanoTime;
                private boolean hasDeadline;
                private long timeoutNanos;

                
        static class C13041 extends Timeout {
            C13041() {
                    }

                    @Override 
            public Timeout timeout(long timeout, TimeUnit unit) {
                return this;
                    }

                    @Override 
            public Timeout deadlineNanoTime(long deadlineNanoTime) {
                return this;
                    }

                    @Override 
            public void throwIfReached() throws IOException {
                    }
                }

        public Timeout timeout(long timeout, TimeUnit unit) {
                    if (timeout < 0) {
                        throw new IllegalArgumentException("timeout < 0: " + timeout);
                    }
            if (unit == null) {
                        throw new IllegalArgumentException("unit == null");
                    }
            this.timeoutNanos = unit.toNanos(timeout);
            return this;
                }

        public long timeoutNanos() {
            return this.timeoutNanos;
                }

        public boolean hasDeadline() {
            return this.hasDeadline;
                }

       public long deadlineNanoTime() {
           if (this.hasDeadline) {
               return this.deadlineNanoTime;
                    }
                    throw new IllegalStateException("No deadline");
                }

       public Timeout deadlineNanoTime(long deadlineNanoTime) {
           this.hasDeadline = true;
           this.deadlineNanoTime = deadlineNanoTime;
           return this;
                }

       public final Timeout deadline(long duration, TimeUnit unit) {
                    if (duration <= 0) {
                        throw new IllegalArgumentException("duration <= 0: " + duration);
                    }
           if (unit == null) {
                        throw new IllegalArgumentException("unit == null");
                    }
           return deadlineNanoTime(System.nanoTime() + unit.toNanos(duration));
                }

       public Timeout clearTimeout() {
           this.timeoutNanos = 0L;
           return this;
                }

       public Timeout clearDeadline() {
           this.hasDeadline = false;
           return this;
                }

       public void throwIfReached() throws IOException {
           if (Thread.interrupted()) {
               throw new InterruptedIOException();
                    }
           if (this.hasDeadline && System.nanoTime() > this.deadlineNanoTime) {
               throw new IOException("deadline reached");
                    }
                }
            }
