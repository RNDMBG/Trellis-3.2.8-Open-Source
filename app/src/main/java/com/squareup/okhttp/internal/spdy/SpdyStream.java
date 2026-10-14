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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class SpdyStream {
                static final /* synthetic */ boolean $assertionsDisabled;
                long bytesLeftInWriteWindow;
                private final SpdyConnection connection;

                /* JADX INFO: renamed from: id */
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
/* 34 */            $assertionsDisabled = !SpdyStream.class.desiredAssertionStatus();
                }

/* 33 */        static /* synthetic */ void access$1000(SpdyStream x0) throws IOException {
/* 34 */            x0.cancelStreamIfNecessary();
                }

/* 33 */        static /* synthetic */ SpdyTimeout access$1100(SpdyStream x0) {
/* 34 */            return x0.writeTimeout;
                }

/* 33 */        static /* synthetic */ void access$1200(SpdyStream x0) throws IOException {
/* 34 */            x0.checkOutNotClosed();
                }

/* 33 */        static /* synthetic */ SpdyConnection access$500(SpdyStream x0) {
/* 34 */            return x0.connection;
                }

/* 33 */        static /* synthetic */ int access$600(SpdyStream x0) {
/* 34 */            return x0.f2533id;
                }

/* 33 */        static /* synthetic */ SpdyTimeout access$700(SpdyStream x0) {
/* 34 */            return x0.readTimeout;
                }

/* 33 */        static /* synthetic */ ErrorCode access$800(SpdyStream x0) {
/* 34 */            return x0.errorCode;
                }

/* 33 */        static /* synthetic */ void access$900(SpdyStream x0) throws InterruptedIOException {
/* 34 */            x0.waitForIo();
                }

/* 76 */        SpdyStream(int id, SpdyConnection connection, boolean outFinished, boolean inFinished, List<Header> requestHeaders) {
/* 78 */            if (connection == null) {
                        throw new NullPointerException("connection == null");
                    }
/* 79 */            if (requestHeaders == null) {
                        throw new NullPointerException("requestHeaders == null");
                    }
/* 80 */            this.f2533id = id;
/* 81 */            this.connection = connection;
/* 82 */            this.bytesLeftInWriteWindow = connection.peerSettings.getInitialWindowSize(65536);
/* 84 */            this.source = new SpdyDataSource(this, connection.okHttpSettings.getInitialWindowSize(65536), null);
/* 86 */            this.sink = new SpdyDataSink();
/* 87 */            SpdyDataSource.access$102(this.source, inFinished);
/* 88 */            SpdyDataSink.access$202(this.sink, outFinished);
/* 89 */            this.requestHeaders = requestHeaders;
                }

/* 92 */        public int getId() {
/* 93 */            return this.f2533id;
                }

/* 106 */       public synchronized boolean isOpen() {
                    boolean z = false;
                    synchronized (this) {
                        try {
/* 110 */                   if (this.errorCode == null && ((!SpdyDataSource.access$100(this.source) && !SpdyDataSource.access$300(this.source)) || ((!SpdyDataSink.access$200(this.sink) && !SpdyDataSink.access$400(this.sink)) || this.responseHeaders == null))) {
/* 115 */                       z = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

/* 119 */       public boolean isLocallyInitiated() {
/* 120 */           boolean streamIsClient = (this.f2533id & 1) == 1;
                    return this.connection.client == streamIsClient;
                }

/* 124 */       public SpdyConnection getConnection() {
/* 125 */           return this.connection;
                }

/* 128 */       public List<Header> getRequestHeaders() {
/* 129 */           return this.requestHeaders;
                }

/* 136 */       public synchronized List<Header> getResponseHeaders() throws IOException {
                    try {
                        this.readTimeout.enter();
/* 139 */               while (this.responseHeaders == null && this.errorCode == null) {
                            try {
/* 140 */                       waitForIo();
                            } catch (Throwable th) {
                                this.readTimeout.exitAndThrowIfTimedOut();
                                throw th;
                            }
                        }
/* 143 */               this.readTimeout.exitAndThrowIfTimedOut();
/* 145 */               if (this.responseHeaders == null) {
/* 146 */                   throw new IOException("stream was reset: " + this.errorCode);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return this.responseHeaders;
                }

/* 153 */       public synchronized ErrorCode getErrorCode() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.errorCode;
                }

/* 163 */       public void reply(List<Header> responseHeaders, boolean out) throws IOException {
/* 164 */           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
/* 165 */           boolean outFinished = false;
                    synchronized (this) {
                        try {
/* 167 */                   if (responseHeaders == null) {
/* 168 */                       throw new NullPointerException("responseHeaders == null");
                            }
/* 170 */                   if (this.responseHeaders != null) {
/* 171 */                       throw new IllegalStateException("reply already sent");
                            }
/* 173 */                   this.responseHeaders = responseHeaders;
/* 174 */                   if (!out) {
/* 175 */                       SpdyDataSink.access$202(this.sink, true);
/* 176 */                       outFinished = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 179 */           this.connection.writeSynReply(this.f2533id, outFinished, responseHeaders);
/* 181 */           if (outFinished) {
/* 182 */               this.connection.flush();
                    }
                }

/* 186 */       public Timeout readTimeout() {
/* 187 */           return this.readTimeout;
                }

/* 190 */       public Timeout writeTimeout() {
/* 191 */           return this.writeTimeout;
                }

/* 195 */       public Source getSource() {
/* 196 */           return this.source;
                }

/* 205 */       public Sink getSink() {
                    synchronized (this) {
                        try {
/* 207 */                   if (this.responseHeaders == null && !isLocallyInitiated()) {
/* 208 */                       throw new IllegalStateException("reply before requesting the sink");
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 211 */           return this.sink;
                }

/* 218 */       public void close(ErrorCode rstStatusCode) throws IOException {
/* 219 */           if (closeInternal(rstStatusCode)) {
/* 222 */               this.connection.writeSynReset(this.f2533id, rstStatusCode);
                    }
                }

/* 229 */       public void closeLater(ErrorCode errorCode) {
/* 230 */           if (closeInternal(errorCode)) {
/* 233 */               this.connection.writeSynResetLater(this.f2533id, errorCode);
                    }
                }

/* 237 */       private boolean closeInternal(ErrorCode errorCode) {
/* 238 */           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
                    synchronized (this) {
                        try {
/* 240 */                   if (this.errorCode != null) {
                                return false;
                            }
/* 243 */                   if (SpdyDataSource.access$100(this.source) && SpdyDataSink.access$200(this.sink)) {
                                return false;
                            }
/* 246 */                   this.errorCode = errorCode;
/* 247 */                   notifyAll();
/* 249 */                   this.connection.removeStream(this.f2533id);
/* 250 */                   return true;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 253 */       void receiveHeaders(List<Header> headers, HeadersMode headersMode) {
/* 254 */           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
/* 255 */           ErrorCode errorCode = null;
/* 256 */           boolean open = true;
                    synchronized (this) {
                        try {
/* 258 */                   if (this.responseHeaders == null) {
/* 259 */                       if (headersMode.failIfHeadersAbsent()) {
/* 260 */                           errorCode = ErrorCode.PROTOCOL_ERROR;
                                } else {
/* 262 */                           this.responseHeaders = headers;
/* 263 */                           open = isOpen();
/* 264 */                           notifyAll();
                                }
                            } else if (headersMode.failIfHeadersPresent()) {
/* 268 */                       errorCode = ErrorCode.STREAM_IN_USE;
                            } else {
/* 270 */                       List<Header> newHeaders = new ArrayList<>();
/* 271 */                       newHeaders.addAll(this.responseHeaders);
/* 272 */                       newHeaders.addAll(headers);
/* 273 */                       this.responseHeaders = newHeaders;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 277 */           if (errorCode != null) {
/* 278 */               closeLater(errorCode);
                    } else if (!open) {
/* 280 */               this.connection.removeStream(this.f2533id);
                    }
                }

/* 284 */       void receiveData(BufferedSource in, int length) throws IOException {
/* 285 */           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
/* 286 */           this.source.receive(in, length);
                }

/* 289 */       void receiveFin() {
                    boolean open;
/* 290 */           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
                    synchronized (this) {
                        try {
/* 293 */                   SpdyDataSource.access$102(this.source, true);
/* 294 */                   open = isOpen();
/* 295 */                   notifyAll();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 297 */           if (!open) {
/* 298 */               this.connection.removeStream(this.f2533id);
                    }
                }

/* 302 */       synchronized void receiveRstStream(ErrorCode errorCode) {
                    try {
                        if (this.errorCode == null) {
/* 304 */                   this.errorCode = errorCode;
/* 305 */                   notifyAll();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 312 */       private final class SpdyDataSource implements Source {
                    static final /* synthetic */ boolean $assertionsDisabled;
                    private boolean closed;
                    private boolean finished;
                    private final long maxByteCount;
                    private final Buffer readBuffer;
                    private final Buffer receiveBuffer;

                    static {
/* 314 */               $assertionsDisabled = !SpdyStream.class.desiredAssertionStatus();
                    }

/* 313 */           /* synthetic */ SpdyDataSource(SpdyStream x0, long x1, Object x2) {
/* 314 */               this(x1);
                    }

/* 313 */           static /* synthetic */ boolean access$100(SpdyDataSource x0) {
/* 314 */               return x0.finished;
                    }

/* 313 */           static /* synthetic */ boolean access$102(SpdyDataSource x0, boolean x1) {
/* 314 */               x0.finished = x1;
                        return x1;
                    }

/* 313 */           static /* synthetic */ boolean access$300(SpdyDataSource x0) {
/* 314 */               return x0.closed;
                    }

/* 332 */           private SpdyDataSource(long maxByteCount) {
/* 316 */               this.receiveBuffer = new Buffer();
/* 319 */               this.readBuffer = new Buffer();
/* 334 */               this.maxByteCount = maxByteCount;
                    }

                    @Override // okio.Source
/* 338 */           public long read(Buffer sink, long byteCount) throws IOException {
                        long read;
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
                        synchronized (SpdyStream.this) {
                            try {
/* 343 */                       waitUntilReadable();
/* 344 */                       checkNotClosed();
                                if (this.readBuffer.size() == 0) {
                                    read = -1;
                                } else {
/* 348 */                           read = this.readBuffer.read(sink, Math.min(byteCount, this.readBuffer.size()));
                                    SpdyStream.this.unacknowledgedBytesRead += read;
                                    if (SpdyStream.this.unacknowledgedBytesRead >= SpdyStream.access$500(SpdyStream.this).okHttpSettings.getInitialWindowSize(65536) / 2) {
/* 354 */                               SpdyStream.access$500(SpdyStream.this).writeWindowUpdateLater(SpdyStream.access$600(SpdyStream.this), SpdyStream.this.unacknowledgedBytesRead);
/* 355 */                               SpdyStream.this.unacknowledgedBytesRead = 0L;
                                    }
                                    synchronized (SpdyStream.access$500(SpdyStream.this)) {
                                        try {
                                            SpdyStream.access$500(SpdyStream.this).unacknowledgedBytesRead += read;
                                            if (SpdyStream.access$500(SpdyStream.this).unacknowledgedBytesRead >= SpdyStream.access$500(SpdyStream.this).okHttpSettings.getInitialWindowSize(65536) / 2) {
/* 364 */                                       SpdyStream.access$500(SpdyStream.this).writeWindowUpdateLater(0, SpdyStream.access$500(SpdyStream.this).unacknowledgedBytesRead);
/* 365 */                                       SpdyStream.access$500(SpdyStream.this).unacknowledgedBytesRead = 0L;
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
/* 369 */               return read;
                    }

/* 373 */           private void waitUntilReadable() throws IOException {
/* 374 */               SpdyStream.access$700(SpdyStream.this).enter();
                        while (this.readBuffer.size() == 0 && !this.finished && !this.closed && SpdyStream.access$800(SpdyStream.this) == null) {
                            try {
/* 377 */                       SpdyStream.access$900(SpdyStream.this);
                            } catch (Throwable th) {
                                SpdyStream.access$700(SpdyStream.this).exitAndThrowIfTimedOut();
                                throw th;
                            }
                        }
                        SpdyStream.access$700(SpdyStream.this).exitAndThrowIfTimedOut();
                    }

/* 384 */           void receive(BufferedSource in, long byteCount) throws IOException {
                        boolean finished;
                        boolean flowControlError;
/* 385 */               if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                            throw new AssertionError();
                        }
                        while (byteCount > 0) {
                            synchronized (SpdyStream.this) {
                                try {
/* 391 */                           finished = this.finished;
/* 392 */                           flowControlError = this.readBuffer.size() + byteCount > this.maxByteCount;
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
/* 396 */                   if (flowControlError) {
/* 397 */                       in.skip(byteCount);
/* 398 */                       SpdyStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
/* 422 */                       return;
                            }
/* 403 */                   if (finished) {
/* 404 */                       in.skip(byteCount);
/* 422 */                       return;
                            }
/* 409 */                   long read = in.read(this.receiveBuffer, byteCount);
                            if (read == -1) {
                                throw new EOFException();
                            }
/* 411 */                   byteCount -= read;
                            synchronized (SpdyStream.this) {
                                try {
/* 415 */                           boolean wasEmpty = this.readBuffer.size() == 0;
/* 416 */                           this.readBuffer.writeAll(this.receiveBuffer);
/* 417 */                           if (wasEmpty) {
/* 418 */                               SpdyStream.this.notifyAll();
                                    }
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
                        }
                    }

                    @Override // okio.Source
/* 424 */           public Timeout timeout() {
/* 425 */               return SpdyStream.access$700(SpdyStream.this);
                    }

                    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 428 */           public void close() throws IOException {
                        synchronized (SpdyStream.this) {
                            try {
/* 430 */                       this.closed = true;
/* 431 */                       this.readBuffer.clear();
/* 432 */                       SpdyStream.this.notifyAll();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 434 */               SpdyStream.access$1000(SpdyStream.this);
                    }

/* 437 */           private void checkNotClosed() throws IOException {
/* 438 */               if (this.closed) {
/* 439 */                   throw new IOException("stream closed");
                        }
/* 441 */               if (SpdyStream.access$800(SpdyStream.this) != null) {
/* 442 */                   throw new IOException("stream was reset: " + SpdyStream.access$800(SpdyStream.this));
                        }
                    }
                }

/* 447 */       private void cancelStreamIfNecessary() throws IOException {
                    boolean cancel;
                    boolean open;
/* 448 */           if (!$assertionsDisabled && Thread.holdsLock(this)) {
                        throw new AssertionError();
                    }
                    synchronized (this) {
                        try {
/* 452 */                   cancel = !SpdyDataSource.access$100(this.source) && SpdyDataSource.access$300(this.source) && (SpdyDataSink.access$200(this.sink) || SpdyDataSink.access$400(this.sink));
/* 453 */                   open = isOpen();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 455 */           if (cancel) {
/* 460 */               close(ErrorCode.CANCEL);
                    } else if (!open) {
/* 462 */               this.connection.removeStream(this.f2533id);
                    }
                }

/* 468 */       final class SpdyDataSink implements Sink {
                    static final /* synthetic */ boolean $assertionsDisabled;
                    private boolean closed;
                    private boolean finished;

                    static {
/* 470 */               $assertionsDisabled = !SpdyStream.class.desiredAssertionStatus();
                    }

/* 469 */           SpdyDataSink() {
                    }

/* 469 */           static /* synthetic */ boolean access$200(SpdyDataSink x0) {
/* 470 */               return x0.finished;
                    }

/* 469 */           static /* synthetic */ boolean access$202(SpdyDataSink x0, boolean x1) {
/* 470 */               x0.finished = x1;
                        return x1;
                    }

/* 469 */           static /* synthetic */ boolean access$400(SpdyDataSink x0) {
/* 470 */               return x0.closed;
                    }

                    @Override // okio.Sink
/* 479 */           public void write(Buffer source, long byteCount) throws IOException {
                        long toWrite;
/* 480 */               if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                            throw new AssertionError();
                        }
                        while (byteCount > 0) {
                            synchronized (SpdyStream.this) {
                                try {
/* 484 */                           SpdyStream.access$1100(SpdyStream.this).enter();
                                    while (SpdyStream.this.bytesLeftInWriteWindow <= 0 && !this.finished && !this.closed && SpdyStream.access$800(SpdyStream.this) == null) {
                                        try {
/* 487 */                                   SpdyStream.access$900(SpdyStream.this);
                                        } catch (Throwable th) {
                                            SpdyStream.access$1100(SpdyStream.this).exitAndThrowIfTimedOut();
                                            throw th;
                                        }
                                    }
/* 490 */                           SpdyStream.access$1100(SpdyStream.this).exitAndThrowIfTimedOut();
/* 493 */                           SpdyStream.access$1200(SpdyStream.this);
/* 494 */                           toWrite = Math.min(SpdyStream.this.bytesLeftInWriteWindow, byteCount);
                                    SpdyStream.this.bytesLeftInWriteWindow -= toWrite;
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
/* 498 */                   byteCount -= toWrite;
/* 499 */                   SpdyStream.access$500(SpdyStream.this).writeData(SpdyStream.access$600(SpdyStream.this), false, source, toWrite);
                        }
                    }

                    @Override // okio.Sink
/* 503 */           public void flush() throws IOException {
/* 504 */               if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                            throw new AssertionError();
                        }
                        synchronized (SpdyStream.this) {
                            try {
/* 506 */                       SpdyStream.access$1200(SpdyStream.this);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 508 */               SpdyStream.access$500(SpdyStream.this).flush();
                    }

                    @Override // okio.Sink
/* 511 */           public Timeout timeout() {
/* 512 */               return SpdyStream.access$1100(SpdyStream.this);
                    }

                    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 515 */           public void close() throws IOException {
/* 516 */               if (!$assertionsDisabled && Thread.holdsLock(SpdyStream.this)) {
                            throw new AssertionError();
                        }
                        synchronized (SpdyStream.this) {
                            try {
/* 518 */                       if (!this.closed) {
/* 520 */                           if (!SpdyStream.this.sink.finished) {
/* 521 */                               SpdyStream.access$500(SpdyStream.this).writeData(SpdyStream.access$600(SpdyStream.this), true, null, 0L);
                                    }
                                    synchronized (SpdyStream.this) {
                                        try {
/* 524 */                                   this.closed = true;
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
/* 526 */                           SpdyStream.access$500(SpdyStream.this).flush();
/* 527 */                           SpdyStream.access$1000(SpdyStream.this);
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
                }

/* 535 */       void addBytesToWriteWindow(long delta) {
/* 536 */           this.bytesLeftInWriteWindow += delta;
                    if (delta > 0) {
                        notifyAll();
                    }
                }

/* 540 */       private void checkOutNotClosed() throws IOException {
/* 541 */           if (SpdyDataSink.access$400(this.sink)) {
/* 542 */               throw new IOException("stream closed");
                    }
/* 543 */           if (SpdyDataSink.access$200(this.sink)) {
/* 544 */               throw new IOException("stream finished");
                    }
/* 545 */           if (this.errorCode != null) {
/* 546 */               throw new IOException("stream was reset: " + this.errorCode);
                    }
                }

/* 555 */       private void waitForIo() throws InterruptedIOException {
                    try {
/* 556 */               wait();
                    } catch (InterruptedException e) {
/* 558 */               throw new InterruptedIOException();
                    }
                }

/* 565 */       class SpdyTimeout extends AsyncTimeout {
/* 566 */           SpdyTimeout() {
                    }

                    @Override // okio.AsyncTimeout
/* 568 */           protected void timedOut() {
/* 569 */               SpdyStream.this.closeLater(ErrorCode.CANCEL);
                    }

/* 572 */           public void exitAndThrowIfTimedOut() throws InterruptedIOException {
/* 573 */               if (exit()) {
                            throw new InterruptedIOException("timeout");
                        }
                    }
                }
            }
