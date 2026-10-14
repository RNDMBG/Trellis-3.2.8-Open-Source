            package okio;

            import co.vine.android.util.AudioUtils;
            import java.io.IOException;
            import java.io.InterruptedIOException;
            
            
    public class AsyncTimeout extends Timeout {
                private static AsyncTimeout head;
                private boolean inQueue;
                private AsyncTimeout next;
                private long timeoutAt;

        static  AsyncTimeout access$000() throws InterruptedException {
            return awaitTimeout();
                }

        public final void enter() {
            if (this.inQueue) {
                        throw new IllegalStateException("Unbalanced enter/exit");
                    }
            long timeoutNanos = timeoutNanos();
            boolean hasDeadline = hasDeadline();
                    if (timeoutNanos != 0 || hasDeadline) {
                this.inQueue = true;
                scheduleTimeout(this, timeoutNanos, hasDeadline);
                    }
                }

        private static synchronized void scheduleTimeout(AsyncTimeout node, long timeoutNanos, boolean hasDeadline) {
                    try {
                        if (head == null) {
                    head = new AsyncTimeout();
                    new Watchdog().start();
                        }
                long now = System.nanoTime();
                        if (timeoutNanos != 0 && hasDeadline) {
                    node.timeoutAt = Math.min(timeoutNanos, node.deadlineNanoTime() - now) + now;
                        } else if (timeoutNanos != 0) {
                    node.timeoutAt = now + timeoutNanos;
                        } else if (hasDeadline) {
                    node.timeoutAt = node.deadlineNanoTime();
                        } else {
                    throw new AssertionError();
                        }
                long remainingNanos = node.remainingNanos(now);
                AsyncTimeout prev = head;
                while (prev.next != null && remainingNanos >= prev.next.remainingNanos(now)) {
                    prev = prev.next;
                        }
                node.next = prev.next;
                prev.next = node;
                if (prev == head) {
                   AsyncTimeout.class.notify();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public final boolean exit() {
           if (!this.inQueue) {
                        return false;
                    }
           this.inQueue = false;
           return cancelScheduledTimeout(this);
                }

       private static synchronized boolean cancelScheduledTimeout(AsyncTimeout node) {
                    boolean z;
                    try {
                        for (AsyncTimeout prev = head; prev != null; prev = prev.next) {
                   if (prev.next == node) {
                       prev.next = node.next;
                       node.next = null;
                       z = false;
                            }
                        }
               z = true;
                    } catch (Throwable th) {
                        throw th;
                    }
                    return z;
                }

       private long remainingNanos(long now) {
           return this.timeoutAt - now;
                }

       protected void timedOut() {
                }

                
       class C12951 implements Sink {
                    final  Sink val$sink;

           C12951(Sink sink) {
                        this.val$sink = sink;
                    }

                    @Override 
           public void write(Buffer source, long byteCount) throws IOException {
               AsyncTimeout.this.enter();
                        try {
                            try {
                       this.val$sink.write(source, byteCount);
                       AsyncTimeout.this.exit(true);
                            } catch (IOException e) {
                       throw AsyncTimeout.this.exit(e);
                            }
                        } catch (Throwable th) {
                   AsyncTimeout.this.exit(false);
                            throw th;
                        }
                    }

                    @Override 
           public void flush() throws IOException {
               AsyncTimeout.this.enter();
                        try {
                            try {
                       this.val$sink.flush();
                       AsyncTimeout.this.exit(true);
                            } catch (IOException e) {
                       throw AsyncTimeout.this.exit(e);
                            }
                        } catch (Throwable th) {
                   AsyncTimeout.this.exit(false);
                            throw th;
                        }
                    }

                    @Override 
           public void close() throws IOException {
               AsyncTimeout.this.enter();
                        try {
                            try {
                       this.val$sink.close();
                       AsyncTimeout.this.exit(true);
                            } catch (IOException e) {
                       throw AsyncTimeout.this.exit(e);
                            }
                        } catch (Throwable th) {
                   AsyncTimeout.this.exit(false);
                            throw th;
                        }
                    }

                    @Override 
           public Timeout timeout() {
               return AsyncTimeout.this;
                    }

           public String toString() {
               return "AsyncTimeout.sink(" + this.val$sink + ")";
                    }
                }

       public final Sink sink(Sink sink) {
           return new C12951(sink);
                }

                
       class C12962 implements Source {
                    final  Source val$source;

           C12962(Source source) {
                        this.val$source = source;
                    }

                    @Override 
           public long read(Buffer sink, long byteCount) throws IOException {
               AsyncTimeout.this.enter();
                        try {
                            try {
                       long result = this.val$source.read(sink, byteCount);
                       AsyncTimeout.this.exit(true);
                                return result;
                            } catch (IOException e) {
                       throw AsyncTimeout.this.exit(e);
                            }
                        } catch (Throwable th) {
                   AsyncTimeout.this.exit(false);
                            throw th;
                        }
                    }

                    @Override 
           public void close() throws IOException {
                        try {
                            try {
                       this.val$source.close();
                       AsyncTimeout.this.exit(true);
                            } catch (IOException e) {
                       throw AsyncTimeout.this.exit(e);
                            }
                        } catch (Throwable th) {
                   AsyncTimeout.this.exit(false);
                            throw th;
                        }
                    }

                    @Override 
           public Timeout timeout() {
               return AsyncTimeout.this;
                    }

           public String toString() {
               return "AsyncTimeout.source(" + this.val$source + ")";
                    }
                }

       public final Source source(Source source) {
           return new C12962(source);
                }

       final void exit(boolean throwOnTimeout) throws IOException {
           boolean timedOut = exit();
           if (timedOut && throwOnTimeout) {
                        throw new InterruptedIOException("timeout");
                    }
                }

       final IOException exit(IOException cause) throws IOException {
           if (exit()) {
               InterruptedIOException e = new InterruptedIOException("timeout");
               e.initCause(cause);
                        return e;
                    }
           return cause;
                }

       private static final class Watchdog extends Thread {
           public Watchdog() {
               super("Okio Watchdog");
               setDaemon(true);
                    }

                    
                    @Override 
           public void run() {
                        while (true) {
                            try {
                       AsyncTimeout timedOut = AsyncTimeout.access$000();
                       if (timedOut != null) {
                           timedOut.timedOut();
                                }
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }

       private static synchronized AsyncTimeout awaitTimeout() throws InterruptedException {
                    AsyncTimeout node;
                    try {
                        node = head.next;
               if (node == null) {
                   AsyncTimeout.class.wait();
                            node = null;
                        } else {
                   long waitNanos = node.remainingNanos(System.nanoTime());
                            if (waitNanos > 0) {
                       long waitMillis = waitNanos / AudioUtils.MICROS_PER_SECOND;
                       AsyncTimeout.class.wait(waitMillis, (int) (waitNanos - (waitMillis * AudioUtils.MICROS_PER_SECOND)));
                                node = null;
                            } else {
                       head.next = node.next;
                       node.next = null;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return node;
                }
            }
