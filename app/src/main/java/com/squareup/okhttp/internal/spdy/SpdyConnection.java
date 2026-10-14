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
            
            
    public final class SpdyConnection implements Closeable {
                static final  boolean $assertionsDisabled;
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
            $assertionsDisabled = !SpdyConnection.class.desiredAssertionStatus();
            executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp SpdyConnection", true));
                }

         SpdyConnection(Builder x0, C12831 x1) throws IOException {
            this(x0);
                }

        static  void access$1000(SpdyConnection x0, ErrorCode x1, ErrorCode x2) throws IOException {
            x0.close(x1, x2);
                }

        static  boolean access$1100(SpdyConnection x0, int x1) {
            return x0.pushedStream(x1);
                }

        static  void access$1200(SpdyConnection x0, int x1, BufferedSource x2, int x3, boolean x4) throws IOException {
            x0.pushDataLater(x1, x2, x3, x4);
                }

        static  void access$1300(SpdyConnection x0, int x1, List x2, boolean x3) {
            x0.pushHeadersLater(x1, x2, x3);
                }

        static  boolean access$1400(SpdyConnection x0) {
            return x0.shutdown;
                }

        static  boolean access$1402(SpdyConnection x0, boolean x1) {
            x0.shutdown = x1;
                    return x1;
                }

        static  int access$1500(SpdyConnection x0) {
            return x0.lastGoodStreamId;
                }

        static  int access$1502(SpdyConnection x0, int x1) {
            x0.lastGoodStreamId = x1;
                    return x1;
                }

        static  int access$1600(SpdyConnection x0) {
            return x0.nextStreamId;
                }

        static  Map access$1700(SpdyConnection x0) {
            return x0.streams;
                }

        static  IncomingStreamHandler access$1800(SpdyConnection x0) {
            return x0.handler;
                }

        static  ExecutorService access$1900() {
            return executor;
                }

        static  void access$2000(SpdyConnection x0, int x1, ErrorCode x2) {
            x0.pushResetLater(x1, x2);
                }

        static  boolean access$2100(SpdyConnection x0) {
            return x0.receivedInitialPeerSettings;
                }

        static  boolean access$2102(SpdyConnection x0, boolean x1) {
            x0.receivedInitialPeerSettings = x1;
                    return x1;
                }

        static  Ping access$2200(SpdyConnection x0, int x1) {
            return x0.removePing(x1);
                }

        static  void access$2300(SpdyConnection x0, boolean x1, int x2, int x3, Ping x4) {
            x0.writePingLater(x1, x2, x3, x4);
                }

        static  void access$2400(SpdyConnection x0, int x1, List x2) {
            x0.pushRequestLater(x1, x2);
                }

        static  PushObserver access$2500(SpdyConnection x0) {
            return x0.pushObserver;
                }

        static  Set access$2600(SpdyConnection x0) {
            return x0.currentPushRequests;
                }

        static  void access$700(SpdyConnection x0, boolean x1, int x2, int x3, Ping x4) throws IOException {
            x0.writePing(x1, x2, x3, x4);
                }

        static  String access$900(SpdyConnection x0) {
            return x0.hostName;
                }

       private SpdyConnection(Builder builder) throws IOException {
            this.streams = new HashMap();
            this.idleStartTimeNs = System.nanoTime();
           this.unacknowledgedBytesRead = 0L;
           this.okHttpSettings = new Settings();
           this.peerSettings = new Settings();
           this.receivedInitialPeerSettings = false;
           this.currentPushRequests = new LinkedHashSet();
           this.protocol = Builder.access$000(builder);
           this.pushObserver = Builder.access$100(builder);
           this.client = Builder.access$200(builder);
           this.handler = Builder.access$300(builder);
           this.nextStreamId = Builder.access$200(builder) ? 1 : 2;
           if (Builder.access$200(builder) && this.protocol == Protocol.HTTP_2) {
               this.nextStreamId += 2;
                    }
           this.nextPingId = Builder.access$200(builder) ? 1 : 2;
           if (Builder.access$200(builder)) {
               this.okHttpSettings.set(7, 0, 16777216);
                    }
           this.hostName = Builder.access$400(builder);
           if (this.protocol == Protocol.HTTP_2) {
               this.variant = new Http20Draft14();
               this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(String.format("OkHttp %s Push Observer", this.hostName), true));
               this.peerSettings.set(7, 0, SupportMenu.USER_MASK);
               this.peerSettings.set(5, 0, 16384);
                    } else if (this.protocol == Protocol.SPDY_3) {
               this.variant = new Spdy3();
               this.pushExecutor = null;
                    } else {
               throw new AssertionError(this.protocol);
                    }
           this.bytesLeftInWriteWindow = this.peerSettings.getInitialWindowSize(65536);
           this.socket = Builder.access$500(builder);
           this.frameWriter = this.variant.newWriter(Okio.buffer(Okio.sink(Builder.access$500(builder))), this.client);
           this.readerRunnable = new Reader(this, null);
           new Thread(this.readerRunnable).start();
                }

       public Protocol getProtocol() {
           return this.protocol;
                }

       public synchronized int openStreamCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.streams.size();
                }

       synchronized SpdyStream getStream(int id) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.streams.get(Integer.valueOf(id));
                }

       synchronized SpdyStream removeStream(int streamId) {
                    SpdyStream stream;
                    try {
                        stream = this.streams.remove(Integer.valueOf(streamId));
               if (stream != null && this.streams.isEmpty()) {
                   setIdle(true);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return stream;
                }

       private synchronized void setIdle(boolean value) {
                    long jNanoTime;
                    if (value) {
                        try {
                            jNanoTime = System.nanoTime();
                        } catch (Throwable th) {
                            throw th;
                        }
                    } else {
               jNanoTime = Long.MAX_VALUE;
                    }
                    this.idleStartTimeNs = jNanoTime;
                }

       public synchronized boolean isIdle() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.idleStartTimeNs != Long.MAX_VALUE;
                }

       public synchronized long getIdleStartTimeNs() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.idleStartTimeNs;
                }

       public SpdyStream pushStream(int associatedStreamId, List<Header> requestHeaders, boolean out) throws IOException {
           if (this.client) {
                        throw new IllegalStateException("Client cannot push requests.");
                    }
           if (this.protocol != Protocol.HTTP_2) {
                        throw new IllegalStateException("protocol != HTTP_2");
                    }
           return newStream(associatedStreamId, requestHeaders, out, false);
                }

       public SpdyStream newStream(List<Header> requestHeaders, boolean out, boolean in) throws IOException {
           return newStream(0, requestHeaders, out, in);
                }

       private SpdyStream newStream(int associatedStreamId, List<Header> requestHeaders, boolean out, boolean in) throws IOException {
                    int streamId;
                    SpdyStream stream;
           boolean outFinished = !out;
           boolean inFinished = in ? false : true;
                    synchronized (this.frameWriter) {
                        try {
                            synchronized (this) {
                                try {
                           if (this.shutdown) {
                               throw new IOException("shutdown");
                                    }
                           streamId = this.nextStreamId;
                           this.nextStreamId += 2;
                           stream = new SpdyStream(streamId, this, outFinished, inFinished, requestHeaders);
                           if (stream.isOpen()) {
                               this.streams.put(Integer.valueOf(streamId), stream);
                               setIdle(false);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                   if (associatedStreamId == 0) {
                       this.frameWriter.synStream(outFinished, inFinished, streamId, associatedStreamId, requestHeaders);
                            } else {
                       if (this.client) {
                           throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
                                }
                       this.frameWriter.pushPromise(associatedStreamId, streamId, requestHeaders);
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
           if (!out) {
               this.frameWriter.flush();
                    }
           return stream;
                }

       void writeSynReply(int streamId, boolean outFinished, List<Header> alternating) throws IOException {
           this.frameWriter.synReply(outFinished, streamId, alternating);
                }

       public void writeData(int streamId, boolean outFinished, Buffer buffer, long byteCount) throws IOException {
                    int toWrite;
                    if (byteCount == 0) {
               this.frameWriter.data(outFinished, streamId, buffer, 0);
               return;
                    }
                    while (byteCount > 0) {
                        synchronized (this) {
                            while (this.bytesLeftInWriteWindow <= 0) {
                                try {
                                    try {
                               wait();
                                    } catch (InterruptedException e) {
                               throw new InterruptedIOException();
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                   toWrite = Math.min((int) Math.min(byteCount, this.bytesLeftInWriteWindow), this.frameWriter.maxDataLength());
                   this.bytesLeftInWriteWindow -= (long) toWrite;
                        }
               byteCount -= (long) toWrite;
               this.frameWriter.data(outFinished && byteCount == 0, streamId, buffer, toWrite);
                    }
                }

       void addBytesToWriteWindow(long delta) {
           this.bytesLeftInWriteWindow += delta;
                    if (delta > 0) {
                        notifyAll();
                    }
                }

                
       class C12831 extends NamedRunnable {
                    final  ErrorCode val$errorCode;
                    final  int val$streamId;

                    
           C12831(String x0, Object[] x1, int i, ErrorCode errorCode) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$errorCode = errorCode;
                    }

                    @Override 
           public void execute() {
                        try {
                   SpdyConnection.this.writeSynReset(this.val$streamId, this.val$errorCode);
                        } catch (IOException e) {
                        }
                    }
                }

       void writeSynResetLater(int streamId, ErrorCode errorCode) {
           executor.submit(new C12831("OkHttp %s stream %d", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, errorCode));
                }

       void writeSynReset(int streamId, ErrorCode statusCode) throws IOException {
           this.frameWriter.rstStream(streamId, statusCode);
                }

                
       class C12842 extends NamedRunnable {
                    final  int val$streamId;
                    final  long val$unacknowledgedBytesRead;

                    
           C12842(String x0, Object[] x1, int i, long j) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$unacknowledgedBytesRead = j;
                    }

                    @Override 
           public void execute() {
                        try {
                   SpdyConnection.this.frameWriter.windowUpdate(this.val$streamId, this.val$unacknowledgedBytesRead);
                        } catch (IOException e) {
                        }
                    }
                }

       void writeWindowUpdateLater(int streamId, long unacknowledgedBytesRead) {
           executor.submit(new C12842("OkHttp Window Update %s stream %d", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, unacknowledgedBytesRead));
                }

       public Ping ping() throws IOException {
                    int pingId;
           Ping ping = new Ping();
                    synchronized (this) {
                        try {
                   if (this.shutdown) {
                       throw new IOException("shutdown");
                            }
                   pingId = this.nextPingId;
                   this.nextPingId += 2;
                   if (this.pings == null) {
                                this.pings = new HashMap();
                            }
                   this.pings.put(Integer.valueOf(pingId), ping);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           writePing(false, pingId, 1330343787, ping);
           return ping;
                }

                
       class C12853 extends NamedRunnable {
                    final  int val$payload1;
                    final  int val$payload2;
                    final  Ping val$ping;
                    final  boolean val$reply;

                    
           C12853(String x0, Object[] x1, boolean z, int i, int i2, Ping ping) {
                        super(x0, x1);
                        this.val$reply = z;
                        this.val$payload1 = i;
                        this.val$payload2 = i2;
                        this.val$ping = ping;
                    }

                    @Override 
           public void execute() {
                        try {
                   SpdyConnection.access$700(SpdyConnection.this, this.val$reply, this.val$payload1, this.val$payload2, this.val$ping);
                        } catch (IOException e) {
                        }
                    }
                }

       private void writePingLater(boolean reply, int payload1, int payload2, Ping ping) {
           executor.submit(new C12853("OkHttp %s ping %08x%08x", new Object[]{this.hostName, Integer.valueOf(payload1), Integer.valueOf(payload2)}, reply, payload1, payload2, ping));
                }

       private void writePing(boolean reply, int payload1, int payload2, Ping ping) throws IOException {
                    synchronized (this.frameWriter) {
               if (ping != null) {
                            try {
                                ping.send();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               this.frameWriter.ping(reply, payload1, payload2);
                    }
                }

       private synchronized Ping removePing(int id) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.pings != null ? this.pings.remove(Integer.valueOf(id)) : null;
                }

       public void flush() throws IOException {
           this.frameWriter.flush();
                }

       public void shutdown(ErrorCode statusCode) throws IOException {
                    synchronized (this.frameWriter) {
                        try {
                            synchronized (this) {
                                try {
                           if (!this.shutdown) {
                               this.shutdown = true;
                               int lastGoodStreamId = this.lastGoodStreamId;
                               this.frameWriter.goAway(lastGoodStreamId, statusCode, Util.EMPTY_BYTE_ARRAY);
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

                @Override 
       public void close() throws IOException {
           close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
                }

       private void close(ErrorCode connectionCode, ErrorCode streamCode) throws IOException {
           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
           IOException thrown = null;
                    try {
               shutdown(connectionCode);
                    } catch (IOException e) {
               thrown = e;
                    }
           SpdyStream[] streamsToClose = null;
           Ping[] pingsToCancel = null;
                    synchronized (this) {
                        try {
                   if (!this.streams.isEmpty()) {
                       streamsToClose = (SpdyStream[]) this.streams.values().toArray(new SpdyStream[this.streams.size()]);
                       this.streams.clear();
                       setIdle(false);
                            }
                   if (this.pings != null) {
                       pingsToCancel = (Ping[]) this.pings.values().toArray(new Ping[this.pings.size()]);
                       this.pings = null;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           if (streamsToClose != null) {
               SpdyStream[] arr$ = streamsToClose;
                        for (SpdyStream stream : arr$) {
                            try {
                       stream.close(streamCode);
                            } catch (IOException e2) {
                       if (thrown != null) {
                                    thrown = e2;
                                }
                            }
                        }
                    }
           if (pingsToCancel != null) {
               Ping[] arr$2 = pingsToCancel;
                        for (Ping ping : arr$2) {
                   ping.cancel();
                        }
                    }
                    try {
               this.frameWriter.close();
                    } catch (IOException e3) {
               if (thrown == null) {
                            thrown = e3;
                        }
                    }
                    try {
               this.socket.close();
                    } catch (IOException e4) {
               thrown = e4;
                    }
           if (thrown != null) {
                        throw thrown;
                    }
                }

       public void sendConnectionPreface() throws IOException {
           this.frameWriter.connectionPreface();
           this.frameWriter.settings(this.okHttpSettings);
           int windowSize = this.okHttpSettings.getInitialWindowSize(65536);
           if (windowSize != 65536) {
               this.frameWriter.windowUpdate(0, windowSize - 65536);
                    }
                }

       public static class Builder {
                    private boolean client;
                    private IncomingStreamHandler handler;
                    private String hostName;
                    private Protocol protocol;
                    private PushObserver pushObserver;
                    private Socket socket;

           static  Protocol access$000(Builder x0) {
               return x0.protocol;
                    }

           static  PushObserver access$100(Builder x0) {
               return x0.pushObserver;
                    }

           static  boolean access$200(Builder x0) {
               return x0.client;
                    }

           static  IncomingStreamHandler access$300(Builder x0) {
               return x0.handler;
                    }

           static  String access$400(Builder x0) {
               return x0.hostName;
                    }

           static  Socket access$500(Builder x0) {
               return x0.socket;
                    }

           public Builder(boolean client, Socket socket) throws IOException {
               this(((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName(), client, socket);
                    }

           public Builder(String hostName, boolean client, Socket socket) throws IOException {
               this.handler = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
               this.protocol = Protocol.SPDY_3;
               this.pushObserver = PushObserver.CANCEL;
               this.hostName = hostName;
               this.client = client;
               this.socket = socket;
                    }

           public Builder handler(IncomingStreamHandler handler) {
               this.handler = handler;
               return this;
                    }

           public Builder protocol(Protocol protocol) {
               this.protocol = protocol;
               return this;
                    }

           public Builder pushObserver(PushObserver pushObserver) {
               this.pushObserver = pushObserver;
               return this;
                    }

           public SpdyConnection build() throws IOException {
               return new SpdyConnection(this, null);
                    }
                }

       class Reader extends NamedRunnable implements FrameReader.Handler {
                    FrameReader frameReader;

            Reader(SpdyConnection x0, C12831 x1) {
               this();
                    }

           private Reader() {
               super("OkHttp %s", SpdyConnection.access$900(SpdyConnection.this));
                    }

                    @Override 
           protected void execute() {
               ErrorCode connectionErrorCode = ErrorCode.INTERNAL_ERROR;
               ErrorCode streamErrorCode = ErrorCode.INTERNAL_ERROR;
                        try {
                            try {
                       this.frameReader = SpdyConnection.this.variant.newReader(Okio.buffer(Okio.source(SpdyConnection.this.socket)), SpdyConnection.this.client);
                       if (!SpdyConnection.this.client) {
                           this.frameReader.readConnectionPreface();
                                }
                       while (this.frameReader.nextFrame(this)) {
                                }
                       connectionErrorCode = ErrorCode.NO_ERROR;
                       ErrorCode streamErrorCode2 = ErrorCode.CANCEL;
                                try {
                           SpdyConnection.access$1000(SpdyConnection.this, connectionErrorCode, streamErrorCode2);
                                } catch (IOException e) {
                                }
                       Util.closeQuietly(this.frameReader);
                            } catch (Throwable th) {
                                try {
                           SpdyConnection.access$1000(SpdyConnection.this, connectionErrorCode, streamErrorCode);
                                } catch (IOException e2) {
                                }
                       Util.closeQuietly(this.frameReader);
                                throw th;
                            }
                        } catch (IOException e3) {
                   ErrorCode connectionErrorCode2 = ErrorCode.PROTOCOL_ERROR;
                   ErrorCode streamErrorCode3 = ErrorCode.PROTOCOL_ERROR;
                            try {
                       SpdyConnection.access$1000(SpdyConnection.this, connectionErrorCode2, streamErrorCode3);
                            } catch (IOException e4) {
                            }
                   Util.closeQuietly(this.frameReader);
                        }
                    }

                    @Override 
           public void data(boolean inFinished, int streamId, BufferedSource source, int length) throws IOException {
               if (SpdyConnection.access$1100(SpdyConnection.this, streamId)) {
                   SpdyConnection.access$1200(SpdyConnection.this, streamId, source, length, inFinished);
                   return;
                        }
               SpdyStream dataStream = SpdyConnection.this.getStream(streamId);
               if (dataStream == null) {
                   SpdyConnection.this.writeSynResetLater(streamId, ErrorCode.INVALID_STREAM);
                   source.skip(length);
                        } else {
                   dataStream.receiveData(source, length);
                   if (inFinished) {
                       dataStream.receiveFin();
                            }
                        }
                    }

                    @Override 
           public void headers(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock, HeadersMode headersMode) {
               if (SpdyConnection.access$1100(SpdyConnection.this, streamId)) {
                   SpdyConnection.access$1300(SpdyConnection.this, streamId, headerBlock, inFinished);
                   return;
                        }
                        synchronized (SpdyConnection.this) {
                            try {
                       if (!SpdyConnection.access$1400(SpdyConnection.this)) {
                           SpdyStream stream = SpdyConnection.this.getStream(streamId);
                           if (stream == null) {
                               if (headersMode.failIfStreamAbsent()) {
                                   SpdyConnection.this.writeSynResetLater(streamId, ErrorCode.INVALID_STREAM);
                                        } else if (streamId > SpdyConnection.access$1500(SpdyConnection.this)) {
                                   if (streamId % 2 != SpdyConnection.access$1600(SpdyConnection.this) % 2) {
                                       SpdyStream newStream = new SpdyStream(streamId, SpdyConnection.this, outFinished, inFinished, headerBlock);
                                       SpdyConnection.access$1502(SpdyConnection.this, streamId);
                                       SpdyConnection.access$1700(SpdyConnection.this).put(Integer.valueOf(streamId), newStream);
                                       SpdyConnection.access$1900().submit(new C12901("OkHttp %s stream %d", new Object[]{SpdyConnection.access$900(SpdyConnection.this), Integer.valueOf(streamId)}, newStream));
                                            }
                                        }
                                    } else if (headersMode.failIfStreamPresent()) {
                               stream.closeLater(ErrorCode.PROTOCOL_ERROR);
                               SpdyConnection.this.removeStream(streamId);
                                    } else {
                               stream.receiveHeaders(headerBlock, headersMode);
                               if (inFinished) {
                                            stream.receiveFin();
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    
           class C12901 extends NamedRunnable {
                        final  SpdyStream val$newStream;

                        
               C12901(String x0, Object[] x1, SpdyStream spdyStream) {
                            super(x0, x1);
                            this.val$newStream = spdyStream;
                        }

                        @Override 
               public void execute() {
                            try {
                       SpdyConnection.access$1800(SpdyConnection.this).receive(this.val$newStream);
                            } catch (IOException e) {
                       throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override 
           public void rstStream(int streamId, ErrorCode errorCode) {
               if (SpdyConnection.access$1100(SpdyConnection.this, streamId)) {
                   SpdyConnection.access$2000(SpdyConnection.this, streamId, errorCode);
                   return;
                        }
               SpdyStream rstStream = SpdyConnection.this.removeStream(streamId);
               if (rstStream != null) {
                   rstStream.receiveRstStream(errorCode);
                        }
                    }

                    @Override 
           public void settings(boolean clearPrevious, Settings newSettings) {
               long delta = 0;
               SpdyStream[] streamsToNotify = null;
                        synchronized (SpdyConnection.this) {
                            try {
                       int priorWriteWindowSize = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
                       if (clearPrevious) {
                                    SpdyConnection.this.peerSettings.clear();
                                }
                       SpdyConnection.this.peerSettings.merge(newSettings);
                       if (SpdyConnection.this.getProtocol() == Protocol.HTTP_2) {
                           ackSettingsLater(newSettings);
                                }
                       int peerInitialWindowSize = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
                       if (peerInitialWindowSize != -1 && peerInitialWindowSize != priorWriteWindowSize) {
                           delta = peerInitialWindowSize - priorWriteWindowSize;
                           if (!SpdyConnection.access$2100(SpdyConnection.this)) {
                               SpdyConnection.this.addBytesToWriteWindow(delta);
                               SpdyConnection.access$2102(SpdyConnection.this, true);
                                    }
                           if (!SpdyConnection.access$1700(SpdyConnection.this).isEmpty()) {
                               streamsToNotify = (SpdyStream[]) SpdyConnection.access$1700(SpdyConnection.this).values().toArray(new SpdyStream[SpdyConnection.access$1700(SpdyConnection.this).size()]);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               if (streamsToNotify != null && delta != 0) {
                   SpdyStream[] arr$ = streamsToNotify;
                            for (SpdyStream stream : arr$) {
                                synchronized (stream) {
                                    try {
                               stream.addBytesToWriteWindow(delta);
                                    } catch (Throwable th2) {
                                        throw th2;
                                    }
                                }
                            }
                        }
                    }

                    
           class C12912 extends NamedRunnable {
                        final  Settings val$peerSettings;

                        
               C12912(String x0, Object[] x1, Settings settings) {
                            super(x0, x1);
                            this.val$peerSettings = settings;
                        }

                        @Override 
               public void execute() {
                            try {
                       SpdyConnection.this.frameWriter.ackSettings(this.val$peerSettings);
                            } catch (IOException e) {
                            }
                        }
                    }

           private void ackSettingsLater(Settings peerSettings) {
               SpdyConnection.access$1900().submit(new C12912("OkHttp %s ACK Settings", new Object[]{SpdyConnection.access$900(SpdyConnection.this)}, peerSettings));
                    }

                    @Override 
           public void ackSettings() {
                    }

                    @Override 
           public void ping(boolean reply, int payload1, int payload2) {
               if (reply) {
                   Ping ping = SpdyConnection.access$2200(SpdyConnection.this, payload1);
                   if (ping != null) {
                       ping.receive();
                       return;
                            }
                   return;
                        }
               SpdyConnection.access$2300(SpdyConnection.this, true, payload1, payload2, null);
                    }

                    @Override 
           public void goAway(int lastGoodStreamId, ErrorCode errorCode, ByteString debugData) {
                        SpdyStream[] streamsCopy;
               if (debugData.size() > 0) {
                        }
                        synchronized (SpdyConnection.this) {
                            try {
                       streamsCopy = (SpdyStream[]) SpdyConnection.access$1700(SpdyConnection.this).values().toArray(new SpdyStream[SpdyConnection.access$1700(SpdyConnection.this).size()]);
                       SpdyConnection.access$1402(SpdyConnection.this, true);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (SpdyStream spdyStream : streamsCopy) {
                   if (spdyStream.getId() > lastGoodStreamId && spdyStream.isLocallyInitiated()) {
                       spdyStream.receiveRstStream(ErrorCode.REFUSED_STREAM);
                       SpdyConnection.this.removeStream(spdyStream.getId());
                            }
                        }
                    }

                    @Override 
           public void windowUpdate(int streamId, long windowSizeIncrement) {
               if (streamId == 0) {
                            synchronized (SpdyConnection.this) {
                                try {
                                    SpdyConnection.this.bytesLeftInWriteWindow += windowSizeIncrement;
                           SpdyConnection.this.notifyAll();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                   return;
                        }
               SpdyStream stream = SpdyConnection.this.getStream(streamId);
               if (stream != null) {
                            synchronized (stream) {
                                try {
                           stream.addBytesToWriteWindow(windowSizeIncrement);
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                        }
                    }

                    @Override 
           public void priority(int streamId, int streamDependency, int weight, boolean exclusive) {
                    }

                    @Override 
           public void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) {
               SpdyConnection.access$2400(SpdyConnection.this, promisedStreamId, requestHeaders);
                    }

                    @Override 
           public void alternateService(int streamId, String origin, ByteString protocol, String host, int port, long maxAge) {
                    }
                }

       private boolean pushedStream(int streamId) {
                    return this.protocol == Protocol.HTTP_2 && streamId != 0 && (streamId & 1) == 0;
                }

       private void pushRequestLater(int streamId, List<Header> requestHeaders) {
                    synchronized (this) {
                        try {
                   if (this.currentPushRequests.contains(Integer.valueOf(streamId))) {
                       writeSynResetLater(streamId, ErrorCode.PROTOCOL_ERROR);
                            } else {
                       this.currentPushRequests.add(Integer.valueOf(streamId));
                       this.pushExecutor.submit(new C12864("OkHttp %s Push Request[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, requestHeaders));
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
       class C12864 extends NamedRunnable {
                    final  List val$requestHeaders;
                    final  int val$streamId;

                    
           C12864(String x0, Object[] x1, int i, List list) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$requestHeaders = list;
                    }

                    @Override 
           public void execute() {
               boolean cancel = SpdyConnection.access$2500(SpdyConnection.this).onRequest(this.val$streamId, this.val$requestHeaders);
               if (cancel) {
                            try {
                       SpdyConnection.this.frameWriter.rstStream(this.val$streamId, ErrorCode.CANCEL);
                                synchronized (SpdyConnection.this) {
                                    try {
                               SpdyConnection.access$2600(SpdyConnection.this).remove(Integer.valueOf(this.val$streamId));
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            } catch (IOException e) {
                            }
                        }
                    }
                }

                
       class C12875 extends NamedRunnable {
                    final  boolean val$inFinished;
                    final  List val$requestHeaders;
                    final  int val$streamId;

                    
           C12875(String x0, Object[] x1, int i, List list, boolean z) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$requestHeaders = list;
                        this.val$inFinished = z;
                    }

                    @Override 
           public void execute() {
               boolean cancel = SpdyConnection.access$2500(SpdyConnection.this).onHeaders(this.val$streamId, this.val$requestHeaders, this.val$inFinished);
               if (cancel) {
                            try {
                                SpdyConnection.this.frameWriter.rstStream(this.val$streamId, ErrorCode.CANCEL);
                            } catch (IOException e) {
                       return;
                            }
                        }
               if (cancel || this.val$inFinished) {
                            synchronized (SpdyConnection.this) {
                                try {
                           SpdyConnection.access$2600(SpdyConnection.this).remove(Integer.valueOf(this.val$streamId));
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }
                }

       private void pushHeadersLater(int streamId, List<Header> requestHeaders, boolean inFinished) {
           this.pushExecutor.submit(new C12875("OkHttp %s Push Headers[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, requestHeaders, inFinished));
                }

       private void pushDataLater(int streamId, BufferedSource source, int byteCount, boolean inFinished) throws IOException {
           Buffer buffer = new Buffer();
           source.require(byteCount);
           source.read(buffer, byteCount);
                    if (buffer.size() != byteCount) {
                        throw new IOException(buffer.size() + " != " + byteCount);
                    }
           this.pushExecutor.submit(new C12886("OkHttp %s Push Data[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, buffer, byteCount, inFinished));
                }

                
       class C12886 extends NamedRunnable {
                    final  Buffer val$buffer;
                    final  int val$byteCount;
                    final  boolean val$inFinished;
                    final  int val$streamId;

                    
           C12886(String x0, Object[] x1, int i, Buffer buffer, int i2, boolean z) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$buffer = buffer;
                        this.val$byteCount = i2;
                        this.val$inFinished = z;
                    }

                    @Override 
           public void execute() {
                        try {
                   boolean cancel = SpdyConnection.access$2500(SpdyConnection.this).onData(this.val$streamId, this.val$buffer, this.val$byteCount, this.val$inFinished);
                   if (cancel) {
                                SpdyConnection.this.frameWriter.rstStream(this.val$streamId, ErrorCode.CANCEL);
                            }
                   if (cancel || this.val$inFinished) {
                                synchronized (SpdyConnection.this) {
                                    try {
                               SpdyConnection.access$2600(SpdyConnection.this).remove(Integer.valueOf(this.val$streamId));
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        } catch (IOException e) {
                        }
                    }
                }

                
       class C12897 extends NamedRunnable {
                    final  ErrorCode val$errorCode;
                    final  int val$streamId;

                    
           C12897(String x0, Object[] x1, int i, ErrorCode errorCode) {
                        super(x0, x1);
                        this.val$streamId = i;
                        this.val$errorCode = errorCode;
                    }

                    @Override 
           public void execute() {
               SpdyConnection.access$2500(SpdyConnection.this).onReset(this.val$streamId, this.val$errorCode);
                        synchronized (SpdyConnection.this) {
                            try {
                       SpdyConnection.access$2600(SpdyConnection.this).remove(Integer.valueOf(this.val$streamId));
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

       private void pushResetLater(int streamId, ErrorCode errorCode) {
           this.pushExecutor.submit(new C12897("OkHttp %s Push Reset[%s]", new Object[]{this.hostName, Integer.valueOf(streamId)}, streamId, errorCode));
                }
            }
