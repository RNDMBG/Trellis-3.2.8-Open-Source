            package okio;

            import co.vine.android.util.AudioUtils;
            import java.io.IOException;
            import java.io.InterruptedIOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class AsyncTimeout extends Timeout {
                private static AsyncTimeout head;
                private boolean inQueue;
                private AsyncTimeout next;
                private long timeoutAt;

/* 39 */        static /* synthetic */ AsyncTimeout access$000() throws InterruptedException {
/* 40 */            return awaitTimeout();
                }

/* 61 */        public final void enter() {
/* 62 */            if (this.inQueue) {
                        throw new IllegalStateException("Unbalanced enter/exit");
                    }
/* 63 */            long timeoutNanos = timeoutNanos();
/* 64 */            boolean hasDeadline = hasDeadline();
                    if (timeoutNanos != 0 || hasDeadline) {
/* 68 */                this.inQueue = true;
/* 69 */                scheduleTimeout(this, timeoutNanos, hasDeadline);
                    }
                }

/* 74 */        private static synchronized void scheduleTimeout(AsyncTimeout node, long timeoutNanos, boolean hasDeadline) {
                    try {
                        if (head == null) {
/* 76 */                    head = new AsyncTimeout();
/* 77 */                    new Watchdog().start();
                        }
/* 80 */                long now = System.nanoTime();
                        if (timeoutNanos != 0 && hasDeadline) {
/* 84 */                    node.timeoutAt = Math.min(timeoutNanos, node.deadlineNanoTime() - now) + now;
                        } else if (timeoutNanos != 0) {
/* 86 */                    node.timeoutAt = now + timeoutNanos;
                        } else if (hasDeadline) {
/* 88 */                    node.timeoutAt = node.deadlineNanoTime();
                        } else {
/* 90 */                    throw new AssertionError();
                        }
/* 94 */                long remainingNanos = node.remainingNanos(now);
/* 95 */                AsyncTimeout prev = head;
/* 96 */                while (prev.next != null && remainingNanos >= prev.next.remainingNanos(now)) {
/* 95 */                    prev = prev.next;
                        }
/* 97 */                node.next = prev.next;
/* 98 */                prev.next = node;
/* 99 */                if (prev == head) {
/* 100 */                   AsyncTimeout.class.notify();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 108 */       public final boolean exit() {
/* 109 */           if (!this.inQueue) {
                        return false;
                    }
/* 110 */           this.inQueue = false;
/* 111 */           return cancelScheduledTimeout(this);
                }

/* 116 */       private static synchronized boolean cancelScheduledTimeout(AsyncTimeout node) {
                    boolean z;
                    try {
                        for (AsyncTimeout prev = head; prev != null; prev = prev.next) {
/* 118 */                   if (prev.next == node) {
/* 119 */                       prev.next = node.next;
/* 120 */                       node.next = null;
/* 121 */                       z = false;
                            }
                        }
/* 126 */               z = true;
                    } catch (Throwable th) {
                        throw th;
                    }
                    return z;
                }

/* 133 */       private long remainingNanos(long now) {
/* 134 */           return this.timeoutAt - now;
                }

/* 141 */       protected void timedOut() {
                }

                /* JADX INFO: renamed from: okio.AsyncTimeout$1 */
/* 148 */       class C12951 implements Sink {
                    final /* synthetic */ Sink val$sink;

/* 149 */           C12951(Sink sink) {
                        this.val$sink = sink;
                    }

                    @Override // okio.Sink
/* 151 */           public void write(Buffer source, long byteCount) throws IOException {
/* 153 */               AsyncTimeout.this.enter();
                        try {
                            try {
/* 155 */                       this.val$sink.write(source, byteCount);
/* 156 */                       AsyncTimeout.this.exit(true);
                            } catch (IOException e) {
/* 158 */                       throw AsyncTimeout.this.exit(e);
                            }
                        } catch (Throwable th) {
/* 152 */                   AsyncTimeout.this.exit(false);
                            throw th;
                        }
                    }

                    @Override // okio.Sink
/* 164 */           public void flush() throws IOException {
/* 166 */               AsyncTimeout.this.enter();
                        try {
                            try {
/* 168 */                       this.val$sink.flush();
/* 169 */                       AsyncTimeout.this.exit(true);
                            } catch (IOException e) {
/* 171 */                       throw AsyncTimeout.this.exit(e);
                            }
                        } catch (Throwable th) {
/* 165 */                   AsyncTimeout.this.exit(false);
                            throw th;
                        }
                    }

                    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 177 */           public void close() throws IOException {
/* 179 */               AsyncTimeout.this.enter();
                        try {
                            try {
/* 181 */                       this.val$sink.close();
/* 182 */                       AsyncTimeout.this.exit(true);
                            } catch (IOException e) {
/* 184 */                       throw AsyncTimeout.this.exit(e);
                            }
                        } catch (Throwable th) {
/* 178 */                   AsyncTimeout.this.exit(false);
                            throw th;
                        }
                    }

                    @Override // okio.Sink
/* 190 */           public Timeout timeout() {
/* 191 */               return AsyncTimeout.this;
                    }

/* 194 */           public String toString() {
/* 195 */               return "AsyncTimeout.sink(" + this.val$sink + ")";
                    }
                }

/* 149 */       public final Sink sink(Sink sink) {
/* 150 */           return new C12951(sink);
                }

                /* JADX INFO: renamed from: okio.AsyncTimeout$2 */
/* 204 */       class C12962 implements Source {
                    final /* synthetic */ Source val$source;

/* 205 */           C12962(Source source) {
                        this.val$source = source;
                    }

                    @Override // okio.Source
/* 207 */           public long read(Buffer sink, long byteCount) throws IOException {
/* 209 */               AsyncTimeout.this.enter();
                        try {
                            try {
/* 211 */                       long result = this.val$source.read(sink, byteCount);
/* 212 */                       AsyncTimeout.this.exit(true);
                                return result;
                            } catch (IOException e) {
/* 215 */                       throw AsyncTimeout.this.exit(e);
                            }
                        } catch (Throwable th) {
/* 208 */                   AsyncTimeout.this.exit(false);
                            throw th;
                        }
                    }

                    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 221 */           public void close() throws IOException {
                        try {
                            try {
/* 224 */                       this.val$source.close();
/* 225 */                       AsyncTimeout.this.exit(true);
                            } catch (IOException e) {
/* 227 */                       throw AsyncTimeout.this.exit(e);
                            }
                        } catch (Throwable th) {
/* 222 */                   AsyncTimeout.this.exit(false);
                            throw th;
                        }
                    }

                    @Override // okio.Source
/* 233 */           public Timeout timeout() {
/* 234 */               return AsyncTimeout.this;
                    }

/* 237 */           public String toString() {
/* 238 */               return "AsyncTimeout.source(" + this.val$source + ")";
                    }
                }

/* 205 */       public final Source source(Source source) {
/* 206 */           return new C12962(source);
                }

/* 247 */       final void exit(boolean throwOnTimeout) throws IOException {
/* 248 */           boolean timedOut = exit();
/* 249 */           if (timedOut && throwOnTimeout) {
                        throw new InterruptedIOException("timeout");
                    }
                }

/* 256 */       final IOException exit(IOException cause) throws IOException {
/* 257 */           if (exit()) {
/* 258 */               InterruptedIOException e = new InterruptedIOException("timeout");
/* 259 */               e.initCause(cause);
                        return e;
                    }
/* 260 */           return cause;
                }

/* 263 */       private static final class Watchdog extends Thread {
/* 264 */           public Watchdog() {
/* 265 */               super("Okio Watchdog");
/* 266 */               setDaemon(true);
                    }

                    /* JADX INFO: Infinite loop detected, blocks: 11, insns: 0 */
                    @Override // java.lang.Thread, java.lang.Runnable
/* 271 */           public void run() {
                        while (true) {
                            try {
/* 272 */                       AsyncTimeout timedOut = AsyncTimeout.access$000();
/* 275 */                       if (timedOut != null) {
/* 278 */                           timedOut.timedOut();
                                }
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }

/* 292 */       private static synchronized AsyncTimeout awaitTimeout() throws InterruptedException {
                    AsyncTimeout node;
                    try {
                        node = head.next;
/* 296 */               if (node == null) {
/* 297 */                   AsyncTimeout.class.wait();
                            node = null;
                        } else {
/* 301 */                   long waitNanos = node.remainingNanos(System.nanoTime());
                            if (waitNanos > 0) {
/* 307 */                       long waitMillis = waitNanos / AudioUtils.MICROS_PER_SECOND;
/* 309 */                       AsyncTimeout.class.wait(waitMillis, (int) (waitNanos - (waitMillis * AudioUtils.MICROS_PER_SECOND)));
                                node = null;
                            } else {
/* 314 */                       head.next = node.next;
/* 315 */                       node.next = null;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return node;
                }
            }
