            package com.squareup.okhttp.internal.spdy;

            import java.io.EOFException;
            import java.io.IOException;
            import java.io.InterruptedIOException;
            import java.util.ArrayList;
            import java.util.List;
            import okio.AsyncTimeout;
            import okio.Buffer;
            import okio.BufferedSource;
            import okio.Sink;
            import okio.Source;
            import okio.Timeout;
            
            
    public final class SpdyStream {
                static final  boolean $assertionsDisabled;
                long bytesLeftInWriteWindow;
                private final SpdyConnection connection;

                
                private final int f2533id;
                private final List<Header> requestHeaders;
                private List<Header> responseHeaders;
                final SpdyDataSink sink;
                private final SpdyDataSource source;
                long unacknowledgedBytesRead = 0;
                private long readTimeoutMillis = 0;
                private final SpdyTimeout readTimeout = new SpdyTimeout();
                private final SpdyTimeout writeTimeout = new SpdyTimeout();
                private ErrorCode errorCode = null;

                static {
            $assertionsDisabled = !SpdyStream.class.desiredAssertionStatus();
                }

        static  void access$1000(SpdyStream x0) throws IOException {
            x0.cancelStreamIfNecessary();
                }

        static  SpdyTimeout access$1100(SpdyStream x0) {
            return x0.writeTimeout;
                }

        static  void access$1200(SpdyStream x0) throws IOException {
            x0.checkOutNotClosed();
                }

        static  SpdyConnection access$500(SpdyStream x0) {
            return x0.connection;
                }

        static  int access$600(SpdyStream x0) {
            return x0.f2533id;
                }

        static  SpdyTimeout access$700(SpdyStream x0) {
            return x0.readTimeout;
                }

        static  ErrorCode access$800(SpdyStream x0) {
            return x0.errorCode;
                }

        static  void access$900(SpdyStream x0) throws InterruptedIOException {
            x0.waitForIo();
                }

        SpdyStream(int id, SpdyConnection connection, boolean outFinished, boolean inFinished, List<Header> requestHeaders) {
            if (connection == null) {
                        throw new NullPointerException("connection == null");
                    }
            if (requestHeaders == null) {
                        throw new NullPointerException("requestHeaders == null");
                    }
            this.f2533id = id;
            this.connection = connection;
            this.bytesLeftInWriteWindow = connection.peerSettings.getInitialWindowSize(65536);
            this.source = new SpdyDataSource(this, connection.okHttpSettings.getInitialWindowSize(65536), null);
            this.sink = new SpdyDataSink();
            SpdyDataSource.access$102(this.source, inFinished);
            SpdyDataSink.access$202(this.sink, outFinished);
            this.requestHeaders = requestHeaders;
                }

        public int getId() {
            return this.f2533id;
                }

       public synchronized boolean isOpen() {
                    boolean z = false;
                    synchronized (this) {
                        try {
                   if (this.errorCode == null && ((!SpdyDataSource.access$100(this.source) && !SpdyDataSource.access$300(this.source)) || ((!SpdyDataSink.access$200(this.sink) && !SpdyDataSink.access$400(this.sink)) || this.responseHeaders == null))) {
                       z = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

       public boolean isLocallyInitiated() {
           boolean streamIsClient = (this.f2533id & 1) == 1;
                    return this.connection.client == streamIsClient;
                }

       public SpdyConnection getConnection() {
           return this.connection;
                }

       public List<Header> getRequestHeaders() {
           return this.requestHeaders;
                }

       public synchronized List<Header> getResponseHeaders() throws IOException {
                    try {
                        this.readTimeout.enter();
               while (this.responseHeaders == null && this.errorCode == null) {
                            try {
                       waitForIo();
                            } catch (Throwable th) {
                                this.readTimeout.exitAndThrowIfTimedOut();
                                throw th;
                            }
                        }
               this.readTimeout.exitAndThrowIfTimedOut();
               if (this.responseHeaders == null) {
                   throw new IOException("stream was reset: " + this.errorCode);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return this.responseHeaders;
                }

       public synchronized ErrorCode getErrorCode() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.errorCode;
                }

       public void reply(List<Header> responseHeaders, boolean out) throws IOException {
           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
           boolean outFinished = false;
                    synchronized (this) {
                        try {
                   if (responseHeaders == null) {
                       throw new NullPointerException("responseHeaders == null");
                            }
                   if (this.responseHeaders != null) {
                       throw new IllegalStateException("reply already sent");
                            }
                   this.responseHeaders = responseHeaders;
                   if (!out) {
                       SpdyDataSink.access$202(this.sink, true);
                       outFinished = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           this.connection.writeSynReply(this.f2533id, outFinished, responseHeaders);
           if (outFinished) {
               this.connection.flush();
                    }
                }

       public Timeout readTimeout() {
           return this.readTimeout;
                }

       public Timeout writeTimeout() {
           return this.writeTimeout;
                }

       public Source getSource() {
           return this.source;
                }

       public Sink getSink() {
                    synchronized (this) {
                        try {
                   if (this.responseHeaders == null && !isLocallyInitiated()) {
                       throw new IllegalStateException("reply before requesting the sink");
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return this.sink;
                }

       public void close(ErrorCode rstStatusCode) throws IOException {
           if (closeInternal(rstStatusCode)) {
               this.connection.writeSynReset(this.f2533id, rstStatusCode);
                    }
                }

       public void closeLater(ErrorCode errorCode) {
           if (closeInternal(errorCode)) {
               this.connection.writeSynResetLater(this.f2533id, errorCode);
                    }
                }

       private boolean closeInternal(ErrorCode errorCode) {
           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
                    synchronized (this) {
                        try {
                   if (this.errorCode != null) {
                                return false;
                            }
                   if (SpdyDataSource.access$100(this.source) && SpdyDataSink.access$200(this.sink)) {
                                return false;
                            }
                   this.errorCode = errorCode;
                   notifyAll();
                   this.connection.removeStream(this.f2533id);
                   return true;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       void receiveHeaders(List<Header> headers, HeadersMode headersMode) {
           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
           ErrorCode errorCode = null;
           boolean open = true;
                    synchronized (this) {
                        try {
                   if (this.responseHeaders == null) {
                       if (headersMode.failIfHeadersAbsent()) {
                           errorCode = ErrorCode.PROTOCOL_ERROR;
                                } else {
                           this.responseHeaders = headers;
                           open = isOpen();
                           notifyAll();
                                }
                            } else if (headersMode.failIfHeadersPresent()) {
                       errorCode = ErrorCode.STREAM_IN_USE;
                            } else {
                       List<Header> newHeaders = new ArrayList<>();
                       newHeaders.addAll(this.responseHeaders);
                       newHeaders.addAll(headers);
                       this.responseHeaders = newHeaders;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           if (errorCode != null) {
               closeLater(errorCode);
                    } else if (!open) {
               this.connection.removeStream(this.f2533id);
                    }
                }

       void receiveData(BufferedSource in, int length) throws IOException {
           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
           this.source.receive(in, length);
                }

       void receiveFin() {
                    boolean open;
           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
                    synchronized (this) {
                        try {
                   SpdyDataSource.access$102(this.source, true);
                   open = isOpen();
                   notifyAll();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           if (!open) {
               this.connection.removeStream(this.f2533id);
                    }
                }

       synchronized void receiveRstStream(ErrorCode errorCode) {
                    try {
                        if (this.errorCode == null) {
                   this.errorCode = errorCode;
                   notifyAll();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private final class SpdyDataSource implements Source {
                    static final  boolean $assertionsDisabled;
                    private boolean closed;
                    private boolean finished;
                    private final long maxByteCount;
                    private final Buffer readBuffer;
                    private final Buffer receiveBuffer;

                    static {
               $assertionsDisabled = !SpdyStream.class.desiredAssertionStatus();
                    }

            SpdyDataSource(SpdyStream x0, long x1, Object x2) {
               this(x1);
                    }

           static  boolean access$100(SpdyDataSource x0) {
               return x0.finished;
                    }

           static  boolean access$102(SpdyDataSource x0, boolean x1) {
               x0.finished = x1;
                        return x1;
                    }

           static  boolean access$300(SpdyDataSource x0) {
               return x0.closed;
                    }

           private SpdyDataSource(long maxByteCount) {
               this.receiveBuffer = new Buffer();
               this.readBuffer = new Buffer();
               this.maxByteCount = maxByteCount;
                    }

                    @Override 
           public long read(Buffer sink, long byteCount) throws IOException {
                        long read;
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
                        synchronized (SpdyStream.this) {
                            try {
                       waitUntilReadable();
                       checkNotClosed();
                                if (this.readBuffer.size() == 0) {
                                    read = -1;
                                } else {
                           read = this.readBuffer.read(sink, Math.min(byteCount, this.readBuffer.size()));
                                    SpdyStream.this.unacknowledgedBytesRead += read;
                                    if (SpdyStream.this.unacknowledgedBytesRead >= SpdyStream.access$500(SpdyStream.this).okHttpSettings.getInitialWindowSize(65536) / 2) {
                               SpdyStream.access$500(SpdyStream.this).writeWindowUpdateLater(SpdyStream.access$600(SpdyStream.this), SpdyStream.this.unacknowledgedBytesRead);
                               SpdyStream.this.unacknowledgedBytesRead = 0L;
                                    }
                                    synchronized (SpdyStream.access$500(SpdyStream.this)) {
                                        try {
                                            SpdyStream.access$500(SpdyStream.this).unacknowledgedBytesRead += read;
                                            if (SpdyStream.access$500(SpdyStream.this).unacknowledgedBytesRead >= SpdyStream.access$500(SpdyStream.this).okHttpSettings.getInitialWindowSize(65536) / 2) {
                                       SpdyStream.access$500(SpdyStream.this).writeWindowUpdateLater(0, SpdyStream.access$500(SpdyStream.this).unacknowledgedBytesRead);
                                       SpdyStream.access$500(SpdyStream.this).unacknowledgedBytesRead = 0L;
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
               return read;
                    }

           private void waitUntilReadable() throws IOException {
               SpdyStream.access$700(SpdyStream.this).enter();
                        while (this.readBuffer.size() == 0 && !this.finished && !this.closed && SpdyStream.access$800(SpdyStream.this) == null) {
                            try {
                       SpdyStream.access$900(SpdyStream.this);
                            } catch (Throwable th) {
                                SpdyStream.access$700(SpdyStream.this).exitAndThrowIfTimedOut();
                                throw th;
                            }
                        }
                        SpdyStream.access$700(SpdyStream.this).exitAndThrowIfTimedOut();
                    }

           void receive(BufferedSource in, long byteCount) throws IOException {
                        boolean finished;
                        boolean flowControlError;
               if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                            throw new AssertionError();
                        }
                        while (byteCount > 0) {
                            synchronized (SpdyStream.this) {
                                try {
                           finished = this.finished;
                           flowControlError = this.readBuffer.size() + byteCount > this.maxByteCount;
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                   if (flowControlError) {
                       in.skip(byteCount);
                       SpdyStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                       return;
                            }
                   if (finished) {
                       in.skip(byteCount);
                       return;
                            }
                   long read = in.read(this.receiveBuffer, byteCount);
                            if (read == -1) {
                                throw new EOFException();
                            }
                   byteCount -= read;
                            synchronized (SpdyStream.this) {
                                try {
                           boolean wasEmpty = this.readBuffer.size() == 0;
                           this.readBuffer.writeAll(this.receiveBuffer);
                           if (wasEmpty) {
                               SpdyStream.this.notifyAll();
                                    }
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                        }
                    }

                    @Override 
           public Timeout timeout() {
               return SpdyStream.access$700(SpdyStream.this);
                    }

                    @Override 
           public void close() throws IOException {
                        synchronized (SpdyStream.this) {
                            try {
                       this.closed = true;
                       this.readBuffer.clear();
                       SpdyStream.this.notifyAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               SpdyStream.access$1000(SpdyStream.this);
                    }

           private void checkNotClosed() throws IOException {
               if (this.closed) {
                   throw new IOException("stream closed");
                        }
               if (SpdyStream.access$800(SpdyStream.this) != null) {
                   throw new IOException("stream was reset: " + SpdyStream.access$800(SpdyStream.this));
                        }
                    }
                }

       private void cancelStreamIfNecessary() throws IOException {
                    boolean cancel;
                    boolean open;
           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
                    synchronized (this) {
                        try {
                   cancel = !SpdyDataSource.access$100(this.source) && SpdyDataSource.access$300(this.source) && (SpdyDataSink.access$200(this.sink) || SpdyDataSink.access$400(this.sink));
                   open = isOpen();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           if (cancel) {
               close(ErrorCode.CANCEL);
                    } else if (!open) {
               this.connection.removeStream(this.f2533id);
                    }
                }

       final class SpdyDataSink implements Sink {
                    static final  boolean $assertionsDisabled;
                    private boolean closed;
                    private boolean finished;

                    static {
               $assertionsDisabled = !SpdyStream.class.desiredAssertionStatus();
                    }

           SpdyDataSink() {
                    }

           static  boolean access$200(SpdyDataSink x0) {
               return x0.finished;
                    }

           static  boolean access$202(SpdyDataSink x0, boolean x1) {
               x0.finished = x1;
                        return x1;
                    }

           static  boolean access$400(SpdyDataSink x0) {
               return x0.closed;
                    }

                    @Override 
           public void write(Buffer source, long byteCount) throws IOException {
                        long toWrite;
               if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                            throw new AssertionError();
                        }
                        while (byteCount > 0) {
                            synchronized (SpdyStream.this) {
                                try {
                           SpdyStream.access$1100(SpdyStream.this).enter();
                                    while (SpdyStream.this.bytesLeftInWriteWindow <= 0 && !this.finished && !this.closed && SpdyStream.access$800(SpdyStream.this) == null) {
                                        try {
                                   SpdyStream.access$900(SpdyStream.this);
                                        } catch (Throwable th) {
                                            SpdyStream.access$1100(SpdyStream.this).exitAndThrowIfTimedOut();
                                            throw th;
                                        }
                                    }
                           SpdyStream.access$1100(SpdyStream.this).exitAndThrowIfTimedOut();
                           SpdyStream.access$1200(SpdyStream.this);
                           toWrite = Math.min(SpdyStream.this.bytesLeftInWriteWindow, byteCount);
                                    SpdyStream.this.bytesLeftInWriteWindow -= toWrite;
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                   byteCount -= toWrite;
                   SpdyStream.access$500(SpdyStream.this).writeData(SpdyStream.access$600(SpdyStream.this), false, source, toWrite);
                        }
                    }

                    @Override 
           public void flush() throws IOException {
               if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                            throw new AssertionError();
                        }
                        synchronized (SpdyStream.this) {
                            try {
                       SpdyStream.access$1200(SpdyStream.this);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               SpdyStream.access$500(SpdyStream.this).flush();
                    }

                    @Override 
           public Timeout timeout() {
               return SpdyStream.access$1100(SpdyStream.this);
                    }

                    @Override 
           public void close() throws IOException {
               if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                            throw new AssertionError();
                        }
                        synchronized (SpdyStream.this) {
                            try {
                       if (!this.closed) {
                           if (!SpdyStream.this.sink.finished) {
                               SpdyStream.access$500(SpdyStream.this).writeData(SpdyStream.access$600(SpdyStream.this), true, null, 0L);
                                    }
                                    synchronized (SpdyStream.this) {
                                        try {
                                   this.closed = true;
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                           SpdyStream.access$500(SpdyStream.this).flush();
                           SpdyStream.access$1000(SpdyStream.this);
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
                }

       void addBytesToWriteWindow(long delta) {
           this.bytesLeftInWriteWindow += delta;
                    if (delta > 0) {
                        notifyAll();
                    }
                }

       private void checkOutNotClosed() throws IOException {
           if (SpdyDataSink.access$400(this.sink)) {
               throw new IOException("stream closed");
                    }
           if (SpdyDataSink.access$200(this.sink)) {
               throw new IOException("stream finished");
                    }
           if (this.errorCode != null) {
               throw new IOException("stream was reset: " + this.errorCode);
                    }
                }

       private void waitForIo() throws InterruptedIOException {
                    try {
               wait();
                    } catch (InterruptedException e) {
               throw new InterruptedIOException();
                    }
                }

       class SpdyTimeout extends AsyncTimeout {
           SpdyTimeout() {
                    }

                    @Override 
           protected void timedOut() {
               SpdyStream.this.closeLater(ErrorCode.CANCEL);
                    }

           public void exitAndThrowIfTimedOut() throws InterruptedIOException {
               if (exit()) {
                            throw new InterruptedIOException("timeout");
                        }
                    }
                }
            }
