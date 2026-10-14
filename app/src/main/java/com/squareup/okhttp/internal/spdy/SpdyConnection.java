            package com.squareup.okhttp.internal.spdy;

            import android.support.v4.internal.view.SupportMenu;
            import com.squareup.okhttp.Protocol;
            import com.squareup.okhttp.internal.NamedRunnable;
            import com.squareup.okhttp.internal.Util;
            import java.io.Closeable;
            import java.io.IOException;
            import java.io.InterruptedIOException;
            import java.net.InetSocketAddress;
            import java.net.Socket;
            import java.util.HashMap;
            import java.util.LinkedHashSet;
            import java.util.List;
            import java.util.Map;
            import java.util.Set;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.LinkedBlockingQueue;
            import java.util.concurrent.SynchronousQueue;
            import java.util.concurrent.ThreadPoolExecutor;
            import java.util.concurrent.TimeUnit;
            import okio.Buffer;
            import okio.BufferedSource;
            import okio.ByteString;
            import okio.Okio;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public final class SpdyConnection implements Closeable {
                static final /* synthetic */ boolean $assertionsDisabled;
                private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
                private static final ExecutorService executor;
                long bytesLeftInWriteWindow;
                final boolean client;
                private final Set<Integer> currentPushRequests;
                final FrameWriter frameWriter;
                private final IncomingStreamHandler handler;
                private final String hostName;
                private long idleStartTimeNs;
                private int lastGoodStreamId;
                private int nextPingId;
                private int nextStreamId;
                final Settings okHttpSettings;
                final Settings peerSettings;
                private Map<Integer, Ping> pings;
                final Protocol protocol;
                private final ExecutorService pushExecutor;
                private final PushObserver pushObserver;
                final Reader readerRunnable;
                private boolean receivedInitialPeerSettings;
                private boolean shutdown;
                final Socket socket;
                private final Map<Integer, SpdyStream> streams;
                long unacknowledgedBytesRead;
                final Variant variant;

                static {
/* 52 */            $assertionsDisabled = !SpdyConnection.class.desiredAssertionStatus();
/* 66 */            executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp SpdyConnection", true));
                }

/* 51 */        /* synthetic */ SpdyConnection(Builder x0, C12831 x1) throws IOException {
/* 52 */            this(x0);
                }

/* 51 */        static /* synthetic */ void access$1000(SpdyConnection x0, ErrorCode x1, ErrorCode x2) throws IOException {
/* 52 */            x0.close(x1, x2);
                }

/* 51 */        static /* synthetic */ boolean access$1100(SpdyConnection x0, int x1) {
/* 52 */            return x0.pushedStream(x1);
                }

/* 51 */        static /* synthetic */ void access$1200(SpdyConnection x0, int x1, BufferedSource x2, int x3, boolean x4) throws IOException {
/* 52 */            x0.pushDataLater(x1, x2, x3, x4);
                }

/* 51 */        static /* synthetic */ void access$1300(SpdyConnection x0, int x1, List x2, boolean x3) {
/* 52 */            x0.pushHeadersLater(x1, x2, x3);
                }

/* 51 */        static /* synthetic */ boolean access$1400(SpdyConnection x0) {
/* 52 */            return x0.shutdown;
                }

/* 51 */        static /* synthetic */ boolean access$1402(SpdyConnection x0, boolean x1) {
/* 52 */            x0.shutdown = x1;
                    return x1;
                }

/* 51 */        static /* synthetic */ int access$1500(SpdyConnection x0) {
/* 52 */            return x0.lastGoodStreamId;
                }

/* 51 */        static /* synthetic */ int access$1502(SpdyConnection x0, int x1) {
/* 52 */            x0.lastGoodStreamId = x1;
                    return x1;
                }

/* 51 */        static /* synthetic */ int access$1600(SpdyConnection x0) {
/* 52 */            return x0.nextStreamId;
                }

/* 51 */        static /* synthetic */ Map access$1700(SpdyConnection x0) {
/* 52 */            return x0.streams;
                }

/* 51 */        static /* synthetic */ IncomingStreamHandler access$1800(SpdyConnection x0) {
/* 52 */            return x0.handler;
                }

/* 51 */        static /* synthetic */ ExecutorService access$1900() {
/* 52 */            return executor;
                }

/* 51 */        static /* synthetic */ void access$2000(SpdyConnection x0, int x1, ErrorCode x2) {
/* 52 */            x0.pushResetLater(x1, x2);
                }

/* 51 */        static /* synthetic */ boolean access$2100(SpdyConnection x0) {
/* 52 */            return x0.receivedInitialPeerSettings;
                }

/* 51 */        static /* synthetic */ boolean access$2102(SpdyConnection x0, boolean x1) {
/* 52 */            x0.receivedInitialPeerSettings = x1;
                    return x1;
                }

/* 51 */        static /* synthetic */ Ping access$2200(SpdyConnection x0, int x1) {
/* 52 */            return x0.removePing(x1);
                }

/* 51 */        static /* synthetic */ void access$2300(SpdyConnection x0, boolean x1, int x2, int x3, Ping x4) {
/* 52 */            x0.writePingLater(x1, x2, x3, x4);
                }

/* 51 */        static /* synthetic */ void access$2400(SpdyConnection x0, int x1, List x2) {
/* 52 */            x0.pushRequestLater(x1, x2);
                }

/* 51 */        static /* synthetic */ PushObserver access$2500(SpdyConnection x0) {
/* 52 */            return x0.pushObserver;
                }

/* 51 */        static /* synthetic */ Set access$2600(SpdyConnection x0) {
/* 52 */            return x0.currentPushRequests;
                }

/* 51 */        static /* synthetic */ void access$700(SpdyConnection x0, boolean x1, int x2, int x3, Ping x4) throws IOException {
/* 52 */            x0.writePing(x1, x2, x3, x4);
                }

/* 51 */        static /* synthetic */ String access$900(SpdyConnection x0) {
/* 52 */            return x0.hostName;
                }

/* 128 */       private SpdyConnection(Builder builder) throws IOException {
/* 81 */            this.streams = new HashMap();
/* 86 */            this.idleStartTimeNs = System.nanoTime();
/* 102 */           this.unacknowledgedBytesRead = 0L;
/* 113 */           this.okHttpSettings = new Settings();
/* 119 */           this.peerSettings = new Settings();
/* 121 */           this.receivedInitialPeerSettings = false;
/* 791 */           this.currentPushRequests = new LinkedHashSet();
/* 130 */           this.protocol = Builder.access$000(builder);
/* 131 */           this.pushObserver = Builder.access$100(builder);
/* 132 */           this.client = Builder.access$200(builder);
/* 133 */           this.handler = Builder.access$300(builder);
/* 135 */           this.nextStreamId = Builder.access$200(builder) ? 1 : 2;
/* 136 */           if (Builder.access$200(builder) && this.protocol == Protocol.HTTP_2) {
/* 137 */               this.nextStreamId += 2;
                    }
/* 140 */           this.nextPingId = Builder.access$200(builder) ? 1 : 2;
/* 146 */           if (Builder.access$200(builder)) {
/* 147 */               this.okHttpSettings.set(7, 0, 16777216);
                    }
/* 150 */           this.hostName = Builder.access$400(builder);
/* 152 */           if (this.protocol == Protocol.HTTP_2) {
/* 153 */               this.variant = new Http20Draft14();
/* 155 */               this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(String.format("OkHttp %s Push Observer", this.hostName), true));
/* 159 */               this.peerSettings.set(7, 0, SupportMenu.USER_MASK);
/* 160 */               this.peerSettings.set(5, 0, 16384);
                    } else if (this.protocol == Protocol.SPDY_3) {
/* 162 */               this.variant = new Spdy3();
/* 163 */               this.pushExecutor = null;
                    } else {
/* 165 */               throw new AssertionError(this.protocol);
                    }
/* 167 */           this.bytesLeftInWriteWindow = this.peerSettings.getInitialWindowSize(65536);
/* 168 */           this.socket = Builder.access$500(builder);
/* 169 */           this.frameWriter = this.variant.newWriter(Okio.buffer(Okio.sink(Builder.access$500(builder))), this.client);
/* 171 */           this.readerRunnable = new Reader(this, null);
/* 172 */           new Thread(this.readerRunnable).start();
                }

/* 176 */       public Protocol getProtocol() {
/* 177 */           return this.protocol;
                }

/* 184 */       public synchronized int openStreamCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.streams.size();
                }

/* 188 */       synchronized SpdyStream getStream(int id) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.streams.get(Integer.valueOf(id));
                }

/* 192 */       synchronized SpdyStream removeStream(int streamId) {
                    SpdyStream stream;
                    try {
                        stream = this.streams.remove(Integer.valueOf(streamId));
/* 194 */               if (stream != null && this.streams.isEmpty()) {
/* 195 */                   setIdle(true);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return stream;
                }

/* 200 */       private synchronized void setIdle(boolean value) {
                    long jNanoTime;
                    if (value) {
                        try {
                            jNanoTime = System.nanoTime();
                        } catch (Throwable th) {
                            throw th;
                        }
                    } else {
/* 201 */               jNanoTime = Long.MAX_VALUE;
                    }
                    this.idleStartTimeNs = jNanoTime;
                }

/* 205 */       public synchronized boolean isIdle() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.idleStartTimeNs != Long.MAX_VALUE;
                }

/* 213 */       public synchronized long getIdleStartTimeNs() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.idleStartTimeNs;
                }

/* 226 */       public SpdyStream pushStream(int associatedStreamId, List<Header> requestHeaders, boolean out) throws IOException {
/* 227 */           if (this.client) {
                        throw new IllegalStateException("Client cannot push requests.");
                    }
/* 228 */           if (this.protocol != Protocol.HTTP_2) {
                        throw new IllegalStateException("protocol != HTTP_2");
                    }
/* 229 */           return newStream(associatedStreamId, requestHeaders, out, false);
                }

/* 241 */       public SpdyStream newStream(List<Header> requestHeaders, boolean out, boolean in) throws IOException {
/* 242 */           return newStream(0, requestHeaders, out, in);
                }

/* 246 */       private SpdyStream newStream(int associatedStreamId, List<Header> requestHeaders, boolean out, boolean in) throws IOException {
                    int streamId;
                    SpdyStream stream;
/* 247 */           boolean outFinished = !out;
/* 248 */           boolean inFinished = in ? false : true;
                    synchronized (this.frameWriter) {
                        try {
                            synchronized (this) {
                                try {
/* 254 */                           if (this.shutdown) {
/* 255 */                               throw new IOException("shutdown");
                                    }
/* 257 */                           streamId = this.nextStreamId;
/* 258 */                           this.nextStreamId += 2;
/* 259 */                           stream = new SpdyStream(streamId, this, outFinished, inFinished, requestHeaders);
/* 260 */                           if (stream.isOpen()) {
/* 261 */                               this.streams.put(Integer.valueOf(streamId), stream);
/* 262 */                               setIdle(false);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
/* 265 */                   if (associatedStreamId == 0) {
/* 266 */                       this.frameWriter.synStream(outFinished, inFinished, streamId, associatedStreamId, requestHeaders);
                            } else {
/* 268 */                       if (this.client) {
/* 269 */                           throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
                                }
/* 271 */                       this.frameWriter.pushPromise(associatedStreamId, streamId, requestHeaders);
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
/* 275 */           if (!out) {
/* 276 */               this.frameWriter.flush();
                    }
/* 279 */           return stream;
                }

/* 283 */       void writeSynReply(int streamId, boolean outFinished, List<Header> alternating) throws IOException {
/* 284 */           this.frameWriter.synReply(outFinished, streamId, alternating);
                }

/* 303 */       public void writeData(int streamId, boolean outFinished, Buffer buffer, long byteCount) throws IOException {
                    int toWrite;
                    if (byteCount == 0) {
/* 305 */               this.frameWriter.data(outFinished, streamId, buffer, 0);
/* 328 */               return;
                    }
                    while (byteCount > 0) {
                        synchronized (this) {
                            while (this.bytesLeftInWriteWindow <= 0) {
                                try {
                                    try {
/* 314 */                               wait();
                                    } catch (InterruptedException e) {
/* 317 */                               throw new InterruptedIOException();
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
/* 321 */                   toWrite = Math.min((int) Math.min(byteCount, this.bytesLeftInWriteWindow), this.frameWriter.maxDataLength());
/* 322 */                   this.bytesLeftInWriteWindow -= (long) toWrite;
                        }
/* 325 */               byteCount -= (long) toWrite;
/* 326 */               this.frameWriter.data(outFinished && byteCount == 0, streamId, buffer, toWrite);
                    }
                }

/* 334 */       void addBytesToWriteWindow(long delta) {
/* 335 */           this.bytesLeftInWriteWindow += delta;
                    if (delta > 0) {
                        notifyAll();
                    }
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.SpdyConnection$1 */
/* 338 */       class C12831 extends NamedRunnable {
                    final /* synthetic */ ErrorCode val$errorCode;
                    final /* synthetic */ int val$streamId;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 339 */           C12831(String x0, Object[] x1, int i, ErrorCode errorCode) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$errorCode = errorCode;
                    }

                    @Override // com.squareup.okhttp.internal.NamedRunnable
/* 342 */           public void execute() {
                        try {
/* 343 */                   SpdyConnection.this.writeSynReset(this.val$streamId, this.val$errorCode);
                        } catch (IOException e) {
                        }
                    }
                }

/* 339 */       void writeSynResetLater(int streamId, ErrorCode errorCode) {
/* 340 */           executor.submit(new C12831("OkHttp %s stream %d", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, errorCode));
                }

/* 350 */       void writeSynReset(int streamId, ErrorCode statusCode) throws IOException {
/* 351 */           this.frameWriter.rstStream(streamId, statusCode);
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.SpdyConnection$2 */
/* 353 */       class C12842 extends NamedRunnable {
                    final /* synthetic */ int val$streamId;
                    final /* synthetic */ long val$unacknowledgedBytesRead;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 354 */           C12842(String x0, Object[] x1, int i, long j) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$unacknowledgedBytesRead = j;
                    }

                    @Override // com.squareup.okhttp.internal.NamedRunnable
/* 357 */           public void execute() {
                        try {
/* 358 */                   SpdyConnection.this.frameWriter.windowUpdate(this.val$streamId, this.val$unacknowledgedBytesRead);
                        } catch (IOException e) {
                        }
                    }
                }

/* 354 */       void writeWindowUpdateLater(int streamId, long unacknowledgedBytesRead) {
/* 355 */           executor.submit(new C12842("OkHttp Window Update %s stream %d", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, unacknowledgedBytesRead));
                }

/* 369 */       public Ping ping() throws IOException {
                    int pingId;
/* 370 */           Ping ping = new Ping();
                    synchronized (this) {
                        try {
/* 373 */                   if (this.shutdown) {
/* 374 */                       throw new IOException("shutdown");
                            }
/* 376 */                   pingId = this.nextPingId;
/* 377 */                   this.nextPingId += 2;
/* 378 */                   if (this.pings == null) {
                                this.pings = new HashMap();
                            }
/* 379 */                   this.pings.put(Integer.valueOf(pingId), ping);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 381 */           writePing(false, pingId, 1330343787, ping);
/* 382 */           return ping;
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.SpdyConnection$3 */
/* 386 */       class C12853 extends NamedRunnable {
                    final /* synthetic */ int val$payload1;
                    final /* synthetic */ int val$payload2;
                    final /* synthetic */ Ping val$ping;
                    final /* synthetic */ boolean val$reply;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 387 */           C12853(String x0, Object[] x1, boolean z, int i, int i2, Ping ping) {
                        super(x0, x1);
                        this.val$reply = z;
                        this.val$payload1 = i;
                        this.val$payload2 = i2;
                        this.val$ping = ping;
                    }

                    @Override // com.squareup.okhttp.internal.NamedRunnable
/* 390 */           public void execute() {
                        try {
/* 391 */                   SpdyConnection.access$700(SpdyConnection.this, this.val$reply, this.val$payload1, this.val$payload2, this.val$ping);
                        } catch (IOException e) {
                        }
                    }
                }

/* 386 */       private void writePingLater(boolean reply, int payload1, int payload2, Ping ping) {
/* 387 */           executor.submit(new C12853("OkHttp %s ping %08x%08x", new Object[]{this.hostName, Integer.valueOf(payload1), Integer.valueOf(payload2)}, reply, payload1, payload2, ping));
                }

/* 398 */       private void writePing(boolean reply, int payload1, int payload2, Ping ping) throws IOException {
                    synchronized (this.frameWriter) {
/* 401 */               if (ping != null) {
                            try {
                                ping.send();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 402 */               this.frameWriter.ping(reply, payload1, payload2);
                    }
                }

/* 406 */       private synchronized Ping removePing(int id) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.pings != null ? this.pings.remove(Integer.valueOf(id)) : null;
                }

/* 410 */       public void flush() throws IOException {
/* 411 */           this.frameWriter.flush();
                }

/* 420 */       public void shutdown(ErrorCode statusCode) throws IOException {
                    synchronized (this.frameWriter) {
                        try {
                            synchronized (this) {
                                try {
/* 424 */                           if (!this.shutdown) {
/* 427 */                               this.shutdown = true;
/* 428 */                               int lastGoodStreamId = this.lastGoodStreamId;
/* 431 */                               this.frameWriter.goAway(lastGoodStreamId, statusCode, Util.EMPTY_BYTE_ARRAY);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }

                @Override // java.io.Closeable, java.lang.AutoCloseable
/* 440 */       public void close() throws IOException {
/* 441 */           close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
                }

/* 444 */       private void close(ErrorCode connectionCode, ErrorCode streamCode) throws IOException {
/* 445 */           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
/* 446 */           IOException thrown = null;
                    try {
/* 448 */               shutdown(connectionCode);
                    } catch (IOException e) {
/* 450 */               thrown = e;
                    }
/* 453 */           SpdyStream[] streamsToClose = null;
/* 454 */           Ping[] pingsToCancel = null;
                    synchronized (this) {
                        try {
/* 456 */                   if (!this.streams.isEmpty()) {
/* 457 */                       streamsToClose = (SpdyStream[]) this.streams.values().toArray(new SpdyStream[this.streams.size()]);
/* 458 */                       this.streams.clear();
/* 459 */                       setIdle(false);
                            }
/* 461 */                   if (this.pings != null) {
/* 462 */                       pingsToCancel = (Ping[]) this.pings.values().toArray(new Ping[this.pings.size()]);
/* 463 */                       this.pings = null;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 467 */           if (streamsToClose != null) {
/* 468 */               SpdyStream[] arr$ = streamsToClose;
                        for (SpdyStream stream : arr$) {
                            try {
/* 470 */                       stream.close(streamCode);
                            } catch (IOException e2) {
/* 472 */                       if (thrown != null) {
                                    thrown = e2;
                                }
                            }
                        }
                    }
/* 477 */           if (pingsToCancel != null) {
/* 478 */               Ping[] arr$2 = pingsToCancel;
                        for (Ping ping : arr$2) {
/* 479 */                   ping.cancel();
                        }
                    }
                    try {
/* 485 */               this.frameWriter.close();
                    } catch (IOException e3) {
/* 487 */               if (thrown == null) {
                            thrown = e3;
                        }
                    }
                    try {
/* 492 */               this.socket.close();
                    } catch (IOException e4) {
/* 494 */               thrown = e4;
                    }
/* 497 */           if (thrown != null) {
                        throw thrown;
                    }
                }

/* 504 */       public void sendConnectionPreface() throws IOException {
/* 505 */           this.frameWriter.connectionPreface();
/* 506 */           this.frameWriter.settings(this.okHttpSettings);
/* 507 */           int windowSize = this.okHttpSettings.getInitialWindowSize(65536);
/* 508 */           if (windowSize != 65536) {
/* 509 */               this.frameWriter.windowUpdate(0, windowSize - 65536);
                    }
                }

/* 511 */       public static class Builder {
                    private boolean client;
                    private IncomingStreamHandler handler;
                    private String hostName;
                    private Protocol protocol;
                    private PushObserver pushObserver;
                    private Socket socket;

/* 512 */           static /* synthetic */ Protocol access$000(Builder x0) {
/* 513 */               return x0.protocol;
                    }

/* 512 */           static /* synthetic */ PushObserver access$100(Builder x0) {
/* 513 */               return x0.pushObserver;
                    }

/* 512 */           static /* synthetic */ boolean access$200(Builder x0) {
/* 513 */               return x0.client;
                    }

/* 512 */           static /* synthetic */ IncomingStreamHandler access$300(Builder x0) {
/* 513 */               return x0.handler;
                    }

/* 512 */           static /* synthetic */ String access$400(Builder x0) {
/* 513 */               return x0.hostName;
                    }

/* 512 */           static /* synthetic */ Socket access$500(Builder x0) {
/* 513 */               return x0.socket;
                    }

/* 521 */           public Builder(boolean client, Socket socket) throws IOException {
/* 522 */               this(((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName(), client, socket);
                    }

/* 528 */           public Builder(String hostName, boolean client, Socket socket) throws IOException {
/* 516 */               this.handler = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
/* 517 */               this.protocol = Protocol.SPDY_3;
/* 518 */               this.pushObserver = PushObserver.CANCEL;
/* 530 */               this.hostName = hostName;
/* 531 */               this.client = client;
/* 532 */               this.socket = socket;
                    }

/* 535 */           public Builder handler(IncomingStreamHandler handler) {
/* 536 */               this.handler = handler;
/* 537 */               return this;
                    }

/* 540 */           public Builder protocol(Protocol protocol) {
/* 541 */               this.protocol = protocol;
/* 542 */               return this;
                    }

/* 545 */           public Builder pushObserver(PushObserver pushObserver) {
/* 546 */               this.pushObserver = pushObserver;
/* 547 */               return this;
                    }

/* 550 */           public SpdyConnection build() throws IOException {
/* 551 */               return new SpdyConnection(this, null);
                    }
                }

/* 557 */       class Reader extends NamedRunnable implements FrameReader.Handler {
                    FrameReader frameReader;

/* 558 */           /* synthetic */ Reader(SpdyConnection x0, C12831 x1) {
/* 559 */               this();
                    }

/* 561 */           private Reader() {
/* 563 */               super("OkHttp %s", SpdyConnection.access$900(SpdyConnection.this));
                    }

                    @Override // com.squareup.okhttp.internal.NamedRunnable
/* 566 */           protected void execute() {
/* 567 */               ErrorCode connectionErrorCode = ErrorCode.INTERNAL_ERROR;
/* 568 */               ErrorCode streamErrorCode = ErrorCode.INTERNAL_ERROR;
                        try {
                            try {
/* 570 */                       this.frameReader = SpdyConnection.this.variant.newReader(Okio.buffer(Okio.source(SpdyConnection.this.socket)), SpdyConnection.this.client);
/* 571 */                       if (!SpdyConnection.this.client) {
/* 572 */                           this.frameReader.readConnectionPreface();
                                }
/* 574 */                       while (this.frameReader.nextFrame(this)) {
                                }
/* 576 */                       connectionErrorCode = ErrorCode.NO_ERROR;
/* 577 */                       ErrorCode streamErrorCode2 = ErrorCode.CANCEL;
                                try {
/* 583 */                           SpdyConnection.access$1000(SpdyConnection.this, connectionErrorCode, streamErrorCode2);
                                } catch (IOException e) {
                                }
/* 586 */                       Util.closeQuietly(this.frameReader);
                            } catch (Throwable th) {
                                try {
/* 583 */                           SpdyConnection.access$1000(SpdyConnection.this, connectionErrorCode, streamErrorCode);
                                } catch (IOException e2) {
                                }
/* 586 */                       Util.closeQuietly(this.frameReader);
                                throw th;
                            }
                        } catch (IOException e3) {
/* 579 */                   ErrorCode connectionErrorCode2 = ErrorCode.PROTOCOL_ERROR;
/* 580 */                   ErrorCode streamErrorCode3 = ErrorCode.PROTOCOL_ERROR;
                            try {
/* 583 */                       SpdyConnection.access$1000(SpdyConnection.this, connectionErrorCode2, streamErrorCode3);
                            } catch (IOException e4) {
                            }
/* 586 */                   Util.closeQuietly(this.frameReader);
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 591 */           public void data(boolean inFinished, int streamId, BufferedSource source, int length) throws IOException {
/* 592 */               if (SpdyConnection.access$1100(SpdyConnection.this, streamId)) {
/* 593 */                   SpdyConnection.access$1200(SpdyConnection.this, streamId, source, length, inFinished);
/* 606 */                   return;
                        }
/* 596 */               SpdyStream dataStream = SpdyConnection.this.getStream(streamId);
/* 597 */               if (dataStream == null) {
/* 598 */                   SpdyConnection.this.writeSynResetLater(streamId, ErrorCode.INVALID_STREAM);
/* 599 */                   source.skip(length);
                        } else {
/* 602 */                   dataStream.receiveData(source, length);
/* 603 */                   if (inFinished) {
/* 604 */                       dataStream.receiveFin();
                            }
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 609 */           public void headers(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock, HeadersMode headersMode) {
/* 610 */               if (SpdyConnection.access$1100(SpdyConnection.this, streamId)) {
/* 611 */                   SpdyConnection.access$1300(SpdyConnection.this, streamId, headerBlock, inFinished);
/* 662 */                   return;
                        }
                        synchronized (SpdyConnection.this) {
                            try {
/* 617 */                       if (!SpdyConnection.access$1400(SpdyConnection.this)) {
/* 619 */                           SpdyStream stream = SpdyConnection.this.getStream(streamId);
/* 621 */                           if (stream == null) {
/* 623 */                               if (headersMode.failIfStreamAbsent()) {
/* 624 */                                   SpdyConnection.this.writeSynResetLater(streamId, ErrorCode.INVALID_STREAM);
                                        } else if (streamId > SpdyConnection.access$1500(SpdyConnection.this)) {
/* 632 */                                   if (streamId % 2 != SpdyConnection.access$1600(SpdyConnection.this) % 2) {
/* 635 */                                       SpdyStream newStream = new SpdyStream(streamId, SpdyConnection.this, outFinished, inFinished, headerBlock);
/* 637 */                                       SpdyConnection.access$1502(SpdyConnection.this, streamId);
/* 638 */                                       SpdyConnection.access$1700(SpdyConnection.this).put(Integer.valueOf(streamId), newStream);
/* 639 */                                       SpdyConnection.access$1900().submit(new C12901("OkHttp %s stream %d", new Object[]{SpdyConnection.access$900(SpdyConnection.this), Integer.valueOf(streamId)}, newStream));
                                            }
                                        }
                                    } else if (headersMode.failIfStreamPresent()) {
/* 654 */                               stream.closeLater(ErrorCode.PROTOCOL_ERROR);
/* 655 */                               SpdyConnection.this.removeStream(streamId);
                                    } else {
/* 660 */                               stream.receiveHeaders(headerBlock, headersMode);
/* 661 */                               if (inFinished) {
                                            stream.receiveFin();
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.SpdyConnection$Reader$1 */
/* 637 */           class C12901 extends NamedRunnable {
                        final /* synthetic */ SpdyStream val$newStream;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 638 */               C12901(String x0, Object[] x1, SpdyStream spdyStream) {
                            super(x0, x1);
                            this.val$newStream = spdyStream;
                        }

                        @Override // com.squareup.okhttp.internal.NamedRunnable
/* 641 */               public void execute() {
                            try {
/* 642 */                       SpdyConnection.access$1800(SpdyConnection.this).receive(this.val$newStream);
                            } catch (IOException e) {
/* 644 */                       throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 664 */           public void rstStream(int streamId, ErrorCode errorCode) {
/* 665 */               if (SpdyConnection.access$1100(SpdyConnection.this, streamId)) {
/* 666 */                   SpdyConnection.access$2000(SpdyConnection.this, streamId, errorCode);
/* 673 */                   return;
                        }
/* 669 */               SpdyStream rstStream = SpdyConnection.this.removeStream(streamId);
/* 670 */               if (rstStream != null) {
/* 671 */                   rstStream.receiveRstStream(errorCode);
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 675 */           public void settings(boolean clearPrevious, Settings newSettings) {
/* 676 */               long delta = 0;
/* 677 */               SpdyStream[] streamsToNotify = null;
                        synchronized (SpdyConnection.this) {
                            try {
/* 679 */                       int priorWriteWindowSize = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
/* 680 */                       if (clearPrevious) {
                                    SpdyConnection.this.peerSettings.clear();
                                }
/* 681 */                       SpdyConnection.this.peerSettings.merge(newSettings);
/* 682 */                       if (SpdyConnection.this.getProtocol() == Protocol.HTTP_2) {
/* 683 */                           ackSettingsLater(newSettings);
                                }
/* 685 */                       int peerInitialWindowSize = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
/* 686 */                       if (peerInitialWindowSize != -1 && peerInitialWindowSize != priorWriteWindowSize) {
/* 687 */                           delta = peerInitialWindowSize - priorWriteWindowSize;
/* 688 */                           if (!SpdyConnection.access$2100(SpdyConnection.this)) {
/* 689 */                               SpdyConnection.this.addBytesToWriteWindow(delta);
/* 690 */                               SpdyConnection.access$2102(SpdyConnection.this, true);
                                    }
/* 692 */                           if (!SpdyConnection.access$1700(SpdyConnection.this).isEmpty()) {
/* 693 */                               streamsToNotify = (SpdyStream[]) SpdyConnection.access$1700(SpdyConnection.this).values().toArray(new SpdyStream[SpdyConnection.access$1700(SpdyConnection.this).size()]);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 697 */               if (streamsToNotify != null && delta != 0) {
/* 698 */                   SpdyStream[] arr$ = streamsToNotify;
                            for (SpdyStream stream : arr$) {
                                synchronized (stream) {
                                    try {
/* 700 */                               stream.addBytesToWriteWindow(delta);
                                    } catch (Throwable th2) {
                                        throw th2;
                                    }
                                }
                            }
                        }
                    }

                    /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.SpdyConnection$Reader$2 */
/* 705 */           class C12912 extends NamedRunnable {
                        final /* synthetic */ Settings val$peerSettings;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 706 */               C12912(String x0, Object[] x1, Settings settings) {
                            super(x0, x1);
                            this.val$peerSettings = settings;
                        }

                        @Override // com.squareup.okhttp.internal.NamedRunnable
/* 709 */               public void execute() {
                            try {
/* 710 */                       SpdyConnection.this.frameWriter.ackSettings(this.val$peerSettings);
                            } catch (IOException e) {
                            }
                        }
                    }

/* 706 */           private void ackSettingsLater(Settings peerSettings) {
/* 707 */               SpdyConnection.access$1900().submit(new C12912("OkHttp %s ACK Settings", new Object[]{SpdyConnection.access$900(SpdyConnection.this)}, peerSettings));
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 718 */           public void ackSettings() {
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 721 */           public void ping(boolean reply, int payload1, int payload2) {
/* 722 */               if (reply) {
/* 723 */                   Ping ping = SpdyConnection.access$2200(SpdyConnection.this, payload1);
/* 724 */                   if (ping != null) {
/* 725 */                       ping.receive();
/* 731 */                       return;
                            }
/* 731 */                   return;
                        }
/* 729 */               SpdyConnection.access$2300(SpdyConnection.this, true, payload1, payload2, null);
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 733 */           public void goAway(int lastGoodStreamId, ErrorCode errorCode, ByteString debugData) {
                        SpdyStream[] streamsCopy;
/* 734 */               if (debugData.size() > 0) {
                        }
                        synchronized (SpdyConnection.this) {
                            try {
/* 740 */                       streamsCopy = (SpdyStream[]) SpdyConnection.access$1700(SpdyConnection.this).values().toArray(new SpdyStream[SpdyConnection.access$1700(SpdyConnection.this).size()]);
/* 741 */                       SpdyConnection.access$1402(SpdyConnection.this, true);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (SpdyStream spdyStream : streamsCopy) {
/* 746 */                   if (spdyStream.getId() > lastGoodStreamId && spdyStream.isLocallyInitiated()) {
/* 747 */                       spdyStream.receiveRstStream(ErrorCode.REFUSED_STREAM);
/* 748 */                       SpdyConnection.this.removeStream(spdyStream.getId());
                            }
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 753 */           public void windowUpdate(int streamId, long windowSizeIncrement) {
/* 754 */               if (streamId == 0) {
                            synchronized (SpdyConnection.this) {
                                try {
                                    SpdyConnection.this.bytesLeftInWriteWindow += windowSizeIncrement;
/* 757 */                           SpdyConnection.this.notifyAll();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
/* 767 */                   return;
                        }
/* 760 */               SpdyStream stream = SpdyConnection.this.getStream(streamId);
/* 761 */               if (stream != null) {
                            synchronized (stream) {
                                try {
/* 763 */                           stream.addBytesToWriteWindow(windowSizeIncrement);
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 771 */           public void priority(int streamId, int streamDependency, int weight, boolean exclusive) {
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 775 */           public void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) {
/* 776 */               SpdyConnection.access$2400(SpdyConnection.this, promisedStreamId, requestHeaders);
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader.Handler
/* 781 */           public void alternateService(int streamId, String origin, ByteString protocol, String host, int port, long maxAge) {
                    }
                }

/* 786 */       private boolean pushedStream(int streamId) {
                    return this.protocol == Protocol.HTTP_2 && streamId != 0 && (streamId & 1) == 0;
                }

/* 793 */       private void pushRequestLater(int streamId, List<Header> requestHeaders) {
                    synchronized (this) {
                        try {
/* 795 */                   if (this.currentPushRequests.contains(Integer.valueOf(streamId))) {
/* 796 */                       writeSynResetLater(streamId, ErrorCode.PROTOCOL_ERROR);
                            } else {
/* 799 */                       this.currentPushRequests.add(Integer.valueOf(streamId));
/* 801 */                       this.pushExecutor.submit(new C12864("OkHttp %s Push Request[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, requestHeaders));
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.SpdyConnection$4 */
/* 799 */       class C12864 extends NamedRunnable {
                    final /* synthetic */ List val$requestHeaders;
                    final /* synthetic */ int val$streamId;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 800 */           C12864(String x0, Object[] x1, int i, List list) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$requestHeaders = list;
                    }

                    @Override // com.squareup.okhttp.internal.NamedRunnable
/* 802 */           public void execute() {
/* 803 */               boolean cancel = SpdyConnection.access$2500(SpdyConnection.this).onRequest(this.val$streamId, this.val$requestHeaders);
/* 805 */               if (cancel) {
                            try {
/* 806 */                       SpdyConnection.this.frameWriter.rstStream(this.val$streamId, ErrorCode.CANCEL);
                                synchronized (SpdyConnection.this) {
                                    try {
/* 808 */                               SpdyConnection.access$2600(SpdyConnection.this).remove(Integer.valueOf(this.val$streamId));
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            } catch (IOException e) {
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.SpdyConnection$5 */
/* 817 */       class C12875 extends NamedRunnable {
                    final /* synthetic */ boolean val$inFinished;
                    final /* synthetic */ List val$requestHeaders;
                    final /* synthetic */ int val$streamId;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 818 */           C12875(String x0, Object[] x1, int i, List list, boolean z) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$requestHeaders = list;
                        this.val$inFinished = z;
                    }

                    @Override // com.squareup.okhttp.internal.NamedRunnable
/* 820 */           public void execute() {
/* 821 */               boolean cancel = SpdyConnection.access$2500(SpdyConnection.this).onHeaders(this.val$streamId, this.val$requestHeaders, this.val$inFinished);
/* 823 */               if (cancel) {
                            try {
                                SpdyConnection.this.frameWriter.rstStream(this.val$streamId, ErrorCode.CANCEL);
                            } catch (IOException e) {
/* 831 */                       return;
                            }
                        }
/* 824 */               if (cancel || this.val$inFinished) {
                            synchronized (SpdyConnection.this) {
                                try {
/* 826 */                           SpdyConnection.access$2600(SpdyConnection.this).remove(Integer.valueOf(this.val$streamId));
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }
                }

/* 818 */       private void pushHeadersLater(int streamId, List<Header> requestHeaders, boolean inFinished) {
/* 819 */           this.pushExecutor.submit(new C12875("OkHttp %s Push Headers[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, requestHeaders, inFinished));
                }

/* 840 */       private void pushDataLater(int streamId, BufferedSource source, int byteCount, boolean inFinished) throws IOException {
/* 841 */           Buffer buffer = new Buffer();
/* 842 */           source.require(byteCount);
/* 843 */           source.read(buffer, byteCount);
                    if (buffer.size() != byteCount) {
                        throw new IOException(buffer.size() + " != " + byteCount);
                    }
/* 845 */           this.pushExecutor.submit(new C12886("OkHttp %s Push Data[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, buffer, byteCount, inFinished));
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.SpdyConnection$6 */
/* 843 */       class C12886 extends NamedRunnable {
                    final /* synthetic */ Buffer val$buffer;
                    final /* synthetic */ int val$byteCount;
                    final /* synthetic */ boolean val$inFinished;
                    final /* synthetic */ int val$streamId;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 844 */           C12886(String x0, Object[] x1, int i, Buffer buffer, int i2, boolean z) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$buffer = buffer;
                        this.val$byteCount = i2;
                        this.val$inFinished = z;
                    }

                    @Override // com.squareup.okhttp.internal.NamedRunnable
/* 847 */           public void execute() {
                        try {
/* 848 */                   boolean cancel = SpdyConnection.access$2500(SpdyConnection.this).onData(this.val$streamId, this.val$buffer, this.val$byteCount, this.val$inFinished);
/* 849 */                   if (cancel) {
                                SpdyConnection.this.frameWriter.rstStream(this.val$streamId, ErrorCode.CANCEL);
                            }
/* 850 */                   if (cancel || this.val$inFinished) {
                                synchronized (SpdyConnection.this) {
                                    try {
/* 852 */                               SpdyConnection.access$2600(SpdyConnection.this).remove(Integer.valueOf(this.val$streamId));
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        } catch (IOException e) {
                        }
                    }
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.SpdyConnection$7 */
/* 860 */       class C12897 extends NamedRunnable {
                    final /* synthetic */ ErrorCode val$errorCode;
                    final /* synthetic */ int val$streamId;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 861 */           C12897(String x0, Object[] x1, int i, ErrorCode errorCode) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$errorCode = errorCode;
                    }

                    @Override // com.squareup.okhttp.internal.NamedRunnable
/* 863 */           public void execute() {
/* 864 */               SpdyConnection.access$2500(SpdyConnection.this).onReset(this.val$streamId, this.val$errorCode);
                        synchronized (SpdyConnection.this) {
                            try {
/* 866 */                       SpdyConnection.access$2600(SpdyConnection.this).remove(Integer.valueOf(this.val$streamId));
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 861 */       private void pushResetLater(int streamId, ErrorCode errorCode) {
/* 862 */           this.pushExecutor.submit(new C12897("OkHttp %s Push Reset[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, errorCode));
                }
            }
