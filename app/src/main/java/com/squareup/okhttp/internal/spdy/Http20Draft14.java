            package com.squareup.okhttp.internal.spdy;

            import com.flurry.android.Constants;
            import com.squareup.okhttp.Protocol;
            import com.squareup.okhttp.internal.Internal;
            import java.io.IOException;
            import java.util.List;
            import java.util.logging.Level;
            import okio.Buffer;
            import okio.BufferedSink;
            import okio.BufferedSource;
            import okio.ByteString;
            import okio.Source;
            import okio.Timeout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public final class Http20Draft14 implements Variant {
                private static final ByteString CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
                static final byte FLAG_ACK = 1;
                static final byte FLAG_COMPRESSED = 32;
                static final byte FLAG_END_HEADERS = 4;
                static final byte FLAG_END_PUSH_PROMISE = 4;
                static final byte FLAG_END_STREAM = 1;
                static final byte FLAG_NONE = 0;
                static final byte FLAG_PADDED = 8;
                static final byte FLAG_PRIORITY = 32;
                static final int INITIAL_MAX_FRAME_SIZE = 16384;
                static final byte TYPE_CONTINUATION = 9;
                static final byte TYPE_DATA = 0;
                static final byte TYPE_GOAWAY = 7;
                static final byte TYPE_HEADERS = 1;
                static final byte TYPE_PING = 6;
                static final byte TYPE_PRIORITY = 2;
                static final byte TYPE_PUSH_PROMISE = 5;
                static final byte TYPE_RST_STREAM = 3;
                static final byte TYPE_SETTINGS = 4;
                static final byte TYPE_WINDOW_UPDATE = 8;

/* 41 */        static /* synthetic */ ByteString access$000() {
/* 42 */            return CONNECTION_PREFACE;
                }

/* 41 */        static /* synthetic */ IOException access$100(String x0, Object[] x1) throws IOException {
/* 42 */            return ioException(x0, x1);
                }

/* 41 */        static /* synthetic */ int access$200(BufferedSource x0) throws IOException {
/* 42 */            return readMedium(x0);
                }

/* 41 */        static /* synthetic */ int access$300(int x0, byte x1, short x2) throws IOException {
/* 42 */            return lengthWithoutPadding(x0, x1, x2);
                }

/* 41 */        static /* synthetic */ IllegalArgumentException access$400(String x0, Object[] x1) {
/* 42 */            return illegalArgument(x0, x1);
                }

/* 41 */        static /* synthetic */ void access$500(BufferedSink x0, int x1) throws IOException {
/* 42 */            writeMedium(x0, x1);
                }

                @Override // com.squareup.okhttp.internal.spdy.Variant
/* 43 */        public Protocol getProtocol() {
/* 44 */            return Protocol.HTTP_2;
                }

                @Override // com.squareup.okhttp.internal.spdy.Variant
/* 77 */        public FrameReader newReader(BufferedSource source, boolean client) {
/* 78 */            return new Reader(source, 4096, client);
                }

                @Override // com.squareup.okhttp.internal.spdy.Variant
/* 81 */        public FrameWriter newWriter(BufferedSink sink, boolean client) {
/* 82 */            return new Writer(sink, client);
                }

/* 91 */        static final class Reader implements FrameReader {
                    private final boolean client;
                    private final ContinuationSource continuation;
                    final HpackDraft09.Reader hpackReader;
                    private final BufferedSource source;

/* 92 */            Reader(BufferedSource source, int headerTableSize, boolean client) {
/* 94 */                this.source = source;
/* 95 */                this.client = client;
/* 96 */                this.continuation = new ContinuationSource(this.source);
/* 97 */                this.hpackReader = new HpackDraft09.Reader(headerTableSize, this.continuation);
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader
/* 100 */           public void readConnectionPreface() throws IOException {
/* 101 */               if (!this.client) {
/* 102 */                   ByteString connectionPreface = this.source.readByteString(Http20Draft14.access$000().size());
/* 103 */                   if (Internal.logger.isLoggable(Level.FINE)) {
                                Internal.logger.fine(String.format("<< CONNECTION %s", connectionPreface.hex()));
                            }
/* 104 */                   if (!Http20Draft14.access$000().equals(connectionPreface)) {
/* 105 */                       throw Http20Draft14.access$100("Expected a connection header but was %s", new Object[]{connectionPreface.utf8()});
                            }
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameReader
/* 110 */           public boolean nextFrame(FrameReader.Handler handler) throws IOException {
                        try {
/* 111 */                   this.source.require(9L);
/* 128 */                   int length = Http20Draft14.access$200(this.source);
/* 129 */                   if (length < 0 || length > 16384) {
/* 130 */                       throw Http20Draft14.access$100("FRAME_SIZE_ERROR: %s", new Object[]{Integer.valueOf(length)});
                            }
/* 132 */                   byte type = (byte) (this.source.readByte() & Constants.UNKNOWN);
/* 133 */                   byte flags = (byte) (this.source.readByte() & Constants.UNKNOWN);
/* 134 */                   int streamId = this.source.readInt() & Integer.MAX_VALUE;
/* 135 */                   if (Internal.logger.isLoggable(Level.FINE)) {
                                Internal.logger.fine(FrameLogger.formatHeader(true, streamId, length, type, flags));
                            }
                            switch (type) {
                                case 0:
/* 139 */                           readData(handler, length, flags, streamId);
                                    return true;
                                case 1:
/* 143 */                           readHeaders(handler, length, flags, streamId);
                                    return true;
                                case 2:
/* 147 */                           readPriority(handler, length, flags, streamId);
                                    return true;
                                case 3:
/* 151 */                           readRstStream(handler, length, flags, streamId);
                                    return true;
                                case 4:
/* 155 */                           readSettings(handler, length, flags, streamId);
                                    return true;
                                case 5:
/* 159 */                           readPushPromise(handler, length, flags, streamId);
                                    return true;
                                case 6:
/* 163 */                           readPing(handler, length, flags, streamId);
                                    return true;
                                case 7:
/* 167 */                           readGoAway(handler, length, flags, streamId);
                                    return true;
                                case 8:
/* 171 */                           readWindowUpdate(handler, length, flags, streamId);
                                    return true;
                                default:
/* 176 */                           this.source.skip(length);
                                    return true;
                            }
                        } catch (IOException e) {
                            return false;
                        }
                    }

/* 182 */           private void readHeaders(FrameReader.Handler handler, int length, byte flags, int streamId) throws IOException {
/* 183 */               if (streamId == 0) {
                            throw Http20Draft14.access$100("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
                        }
/* 185 */               boolean endStream = (flags & 1) != 0;
/* 187 */               short padding = (flags & 8) != 0 ? (short) (this.source.readByte() & Constants.UNKNOWN) : (short) 0;
/* 189 */               if ((flags & 32) != 0) {
/* 190 */                   readPriority(handler, streamId);
/* 191 */                   length -= 5;
                        }
/* 196 */               List<Header> headerBlock = readHeaderBlock(Http20Draft14.access$300(length, flags, padding), padding, flags, streamId);
/* 198 */               handler.headers(false, endStream, streamId, -1, headerBlock, HeadersMode.HTTP_20_HEADERS);
                    }

/* 202 */           private List<Header> readHeaderBlock(int length, short padding, byte flags, int streamId) throws IOException {
/* 203 */               ContinuationSource continuationSource = this.continuation;
                        this.continuation.left = length;
                        continuationSource.length = length;
/* 204 */               this.continuation.padding = padding;
/* 205 */               this.continuation.flags = flags;
/* 206 */               this.continuation.streamId = streamId;
/* 210 */               this.hpackReader.readHeaders();
/* 211 */               return this.hpackReader.getAndResetHeaderList();
                    }

/* 216 */           private void readData(FrameReader.Handler handler, int length, byte flags, int streamId) throws IOException {
/* 217 */               boolean inFinished = (flags & 1) != 0;
/* 218 */               boolean gzipped = (flags & 32) != 0;
/* 219 */               if (gzipped) {
/* 220 */                   throw Http20Draft14.access$100("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
                        }
/* 223 */               short padding = (flags & 8) != 0 ? (short) (this.source.readByte() & Constants.UNKNOWN) : (short) 0;
/* 226 */               handler.data(inFinished, streamId, this.source, Http20Draft14.access$300(length, flags, padding));
/* 227 */               this.source.skip(padding);
                    }

/* 231 */           private void readPriority(FrameReader.Handler handler, int length, byte flags, int streamId) throws IOException {
/* 232 */               if (length != 5) {
                            throw Http20Draft14.access$100("TYPE_PRIORITY length: %d != 5", new Object[]{Integer.valueOf(length)});
                        }
/* 233 */               if (streamId == 0) {
                            throw Http20Draft14.access$100("TYPE_PRIORITY streamId == 0", new Object[0]);
                        }
/* 234 */               readPriority(handler, streamId);
                    }

/* 237 */           private void readPriority(FrameReader.Handler handler, int streamId) throws IOException {
/* 238 */               int w1 = this.source.readInt();
/* 239 */               boolean exclusive = (Integer.MIN_VALUE & w1) != 0;
/* 240 */               int streamDependency = w1 & Integer.MAX_VALUE;
/* 241 */               int weight = (this.source.readByte() & Constants.UNKNOWN) + 1;
/* 242 */               handler.priority(streamId, streamDependency, weight, exclusive);
                    }

/* 246 */           private void readRstStream(FrameReader.Handler handler, int length, byte flags, int streamId) throws IOException {
/* 247 */               if (length != 4) {
                            throw Http20Draft14.access$100("TYPE_RST_STREAM length: %d != 4", new Object[]{Integer.valueOf(length)});
                        }
/* 248 */               if (streamId == 0) {
                            throw Http20Draft14.access$100("TYPE_RST_STREAM streamId == 0", new Object[0]);
                        }
/* 249 */               int errorCodeInt = this.source.readInt();
/* 250 */               ErrorCode errorCode = ErrorCode.fromHttp2(errorCodeInt);
/* 251 */               if (errorCode == null) {
/* 252 */                   throw Http20Draft14.access$100("TYPE_RST_STREAM unexpected error code: %d", new Object[]{Integer.valueOf(errorCodeInt)});
                        }
/* 254 */               handler.rstStream(streamId, errorCode);
                    }

/* 258 */           private void readSettings(FrameReader.Handler handler, int length, byte flags, int streamId) throws IOException {
/* 259 */               if (streamId != 0) {
                            throw Http20Draft14.access$100("TYPE_SETTINGS streamId != 0", new Object[0]);
                        }
/* 260 */               if ((flags & 1) != 0) {
/* 261 */                   if (length != 0) {
                                throw Http20Draft14.access$100("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                            }
/* 262 */                   handler.ackSettings();
/* 305 */                   return;
                        }
/* 266 */               if (length % 6 != 0) {
                            throw Http20Draft14.access$100("TYPE_SETTINGS length %% 6 != 0: %s", new Object[]{Integer.valueOf(length)});
                        }
/* 267 */               Settings settings = new Settings();
                        for (int i = 0; i < length; i += 6) {
/* 269 */                   short id = this.source.readShort();
/* 270 */                   int value = this.source.readInt();
                            switch (id) {
                                case 1:
                                case 6:
                                    break;
                                case 2:
/* 276 */                           if (value != 0 && value != 1) {
/* 277 */                               throw Http20Draft14.access$100("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                                    }
                                    break;
                                case 3:
/* 281 */                           id = 4;
                                    break;
                                case 4:
/* 284 */                           id = 7;
/* 285 */                           if (value < 0) {
/* 286 */                               throw Http20Draft14.access$100("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                                    }
                                    break;
                                case 5:
/* 290 */                           if (value < 16384 || value > 16777215) {
/* 291 */                               throw Http20Draft14.access$100("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", new Object[]{Integer.valueOf(value)});
                                    }
                                    break;
                                default:
/* 297 */                           throw Http20Draft14.access$100("PROTOCOL_ERROR invalid settings id: %s", new Object[]{Short.valueOf(id)});
                            }
/* 299 */                   settings.set(id, 0, value);
                        }
/* 301 */               handler.settings(false, settings);
/* 302 */               if (settings.getHeaderTableSize() >= 0) {
/* 303 */                   this.hpackReader.maxHeaderTableByteCountSetting(settings.getHeaderTableSize());
                        }
                    }

/* 308 */           private void readPushPromise(FrameReader.Handler handler, int length, byte flags, int streamId) throws IOException {
/* 309 */               if (streamId == 0) {
/* 310 */                   throw Http20Draft14.access$100("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
                        }
/* 312 */               short padding = (flags & 8) != 0 ? (short) (this.source.readByte() & Constants.UNKNOWN) : (short) 0;
/* 313 */               int promisedStreamId = this.source.readInt() & Integer.MAX_VALUE;
/* 316 */               List<Header> headerBlock = readHeaderBlock(Http20Draft14.access$300(length - 4, flags, padding), padding, flags, streamId);
/* 317 */               handler.pushPromise(streamId, promisedStreamId, headerBlock);
                    }

/* 321 */           private void readPing(FrameReader.Handler handler, int length, byte flags, int streamId) throws IOException {
/* 322 */               if (length != 8) {
                            throw Http20Draft14.access$100("TYPE_PING length != 8: %s", new Object[]{Integer.valueOf(length)});
                        }
/* 323 */               if (streamId != 0) {
                            throw Http20Draft14.access$100("TYPE_PING streamId != 0", new Object[0]);
                        }
/* 324 */               int payload1 = this.source.readInt();
/* 325 */               int payload2 = this.source.readInt();
/* 326 */               boolean ack = (flags & 1) != 0;
/* 327 */               handler.ping(ack, payload1, payload2);
                    }

/* 331 */           private void readGoAway(FrameReader.Handler handler, int length, byte flags, int streamId) throws IOException {
/* 332 */               if (length < 8) {
                            throw Http20Draft14.access$100("TYPE_GOAWAY length < 8: %s", new Object[]{Integer.valueOf(length)});
                        }
/* 333 */               if (streamId != 0) {
                            throw Http20Draft14.access$100("TYPE_GOAWAY streamId != 0", new Object[0]);
                        }
/* 334 */               int lastStreamId = this.source.readInt();
/* 335 */               int errorCodeInt = this.source.readInt();
/* 336 */               int opaqueDataLength = length - 8;
/* 337 */               ErrorCode errorCode = ErrorCode.fromHttp2(errorCodeInt);
/* 338 */               if (errorCode == null) {
/* 339 */                   throw Http20Draft14.access$100("TYPE_GOAWAY unexpected error code: %d", new Object[]{Integer.valueOf(errorCodeInt)});
                        }
/* 341 */               ByteString debugData = ByteString.EMPTY;
/* 342 */               if (opaqueDataLength > 0) {
/* 343 */                   debugData = this.source.readByteString(opaqueDataLength);
                        }
/* 345 */               handler.goAway(lastStreamId, errorCode, debugData);
                    }

/* 349 */           private void readWindowUpdate(FrameReader.Handler handler, int length, byte flags, int streamId) throws IOException {
/* 350 */               if (length != 4) {
                            throw Http20Draft14.access$100("TYPE_WINDOW_UPDATE length !=4: %s", new Object[]{Integer.valueOf(length)});
                        }
/* 351 */               long increment = ((long) this.source.readInt()) & 2147483647L;
                        if (increment == 0) {
                            throw Http20Draft14.access$100("windowSizeIncrement was 0", new Object[]{Long.valueOf(increment)});
                        }
/* 353 */               handler.windowUpdate(streamId, increment);
                    }

                    @Override // java.io.Closeable, java.lang.AutoCloseable
/* 356 */           public void close() throws IOException {
/* 357 */               this.source.close();
                    }
                }

/* 367 */       static final class Writer implements FrameWriter {
                    private final boolean client;
                    private boolean closed;
                    private final Buffer hpackBuffer = new Buffer();
                    private final HpackDraft09.Writer hpackWriter = new HpackDraft09.Writer(this.hpackBuffer);
                    private int maxFrameSize = 16384;
                    private final BufferedSink sink;

/* 368 */           Writer(BufferedSink sink, boolean client) {
/* 370 */               this.sink = sink;
/* 371 */               this.client = client;
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 377 */           public synchronized void flush() throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
/* 379 */                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 382 */           public synchronized void ackSettings(Settings peerSettings) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
/* 384 */                   this.maxFrameSize = peerSettings.getMaxFrameSize(this.maxFrameSize);
/* 389 */                   frameHeader(0, 0, (byte) 4, (byte) 1);
/* 390 */                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 393 */           public synchronized void connectionPreface() throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
/* 395 */                   if (this.client) {
/* 396 */                       if (Internal.logger.isLoggable(Level.FINE)) {
/* 397 */                           Internal.logger.fine(String.format(">> CONNECTION %s", Http20Draft14.access$000().hex()));
                                }
/* 399 */                       this.sink.write(Http20Draft14.access$000().toByteArray());
/* 400 */                       this.sink.flush();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 405 */           public synchronized void synStream(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock) throws IOException {
                        try {
                            if (inFinished) {
                                throw new UnsupportedOperationException();
                            }
/* 407 */                   if (this.closed) {
                                throw new IOException("closed");
                            }
/* 408 */                   headers(outFinished, streamId, headerBlock);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 412 */           public synchronized void synReply(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
/* 414 */                   headers(outFinished, streamId, headerBlock);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 418 */           public synchronized void headers(int streamId, List<Header> headerBlock) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
/* 420 */                   headers(false, streamId, headerBlock);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 424 */           public synchronized void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
                            if (this.hpackBuffer.size() != 0) {
                                throw new IllegalStateException();
                            }
/* 427 */                   this.hpackWriter.writeHeaders(requestHeaders);
/* 429 */                   long byteCount = this.hpackBuffer.size();
/* 430 */                   int length = (int) Math.min(this.maxFrameSize - 4, byteCount);
/* 432 */                   byte flags = byteCount == ((long) length) ? (byte) 4 : (byte) 0;
/* 431 */                   frameHeader(streamId, length + 4, Http20Draft14.TYPE_PUSH_PROMISE, flags);
/* 434 */                   this.sink.writeInt(Integer.MAX_VALUE & promisedStreamId);
/* 435 */                   this.sink.write(this.hpackBuffer, length);
                            if (byteCount > length) {
                                writeContinuationFrames(streamId, byteCount - ((long) length));
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

/* 440 */           void headers(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
/* 441 */               if (this.closed) {
                            throw new IOException("closed");
                        }
                        if (this.hpackBuffer.size() != 0) {
                            throw new IllegalStateException();
                        }
/* 443 */               this.hpackWriter.writeHeaders(headerBlock);
/* 445 */               long byteCount = this.hpackBuffer.size();
/* 446 */               int length = (int) Math.min(this.maxFrameSize, byteCount);
/* 448 */               byte flags = byteCount == ((long) length) ? (byte) 4 : (byte) 0;
/* 449 */               if (outFinished) {
                            flags = (byte) (flags | 1);
                        }
/* 450 */               frameHeader(streamId, length, (byte) 1, flags);
/* 451 */               this.sink.write(this.hpackBuffer, length);
                        if (byteCount > length) {
                            writeContinuationFrames(streamId, byteCount - ((long) length));
                        }
                    }

/* 456 */           private void writeContinuationFrames(int streamId, long byteCount) throws IOException {
                        while (byteCount > 0) {
/* 458 */                   int length = (int) Math.min(this.maxFrameSize, byteCount);
/* 459 */                   byteCount -= (long) length;
/* 460 */                   frameHeader(streamId, length, Http20Draft14.TYPE_CONTINUATION, byteCount == 0 ? (byte) 4 : (byte) 0);
/* 461 */                   this.sink.write(this.hpackBuffer, length);
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 466 */           public synchronized void rstStream(int streamId, ErrorCode errorCode) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
/* 468 */                   if (errorCode.spdyRstCode == -1) {
                                throw new IllegalArgumentException();
                            }
/* 473 */                   frameHeader(streamId, 4, Http20Draft14.TYPE_RST_STREAM, (byte) 0);
/* 474 */                   this.sink.writeInt(errorCode.httpCode);
/* 475 */                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 478 */           public int maxDataLength() {
/* 479 */               return this.maxFrameSize;
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 483 */           public synchronized void data(boolean outFinished, int streamId, Buffer source, int byteCount) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
/* 486 */                   byte flags = outFinished ? (byte) 1 : (byte) 0;
/* 487 */                   dataFrame(streamId, flags, source, byteCount);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

/* 490 */           void dataFrame(int streamId, byte flags, Buffer buffer, int byteCount) throws IOException {
/* 492 */               frameHeader(streamId, byteCount, (byte) 0, flags);
/* 493 */               if (byteCount > 0) {
/* 494 */                   this.sink.write(buffer, byteCount);
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 498 */           public synchronized void settings(Settings settings) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
/* 500 */                   int length = settings.size() * 6;
/* 504 */                   frameHeader(0, length, (byte) 4, (byte) 0);
                            for (int i = 0; i < 10; i++) {
/* 506 */                       if (settings.isSet(i)) {
/* 507 */                           int id = i;
/* 508 */                           if (id == 4) {
                                        id = 3;
                                    } else if (id == 7) {
                                        id = 4;
                                    }
/* 510 */                           this.sink.writeShort(id);
/* 511 */                           this.sink.writeInt(settings.get(i));
                                }
                            }
/* 513 */                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 517 */           public synchronized void ping(boolean ack, int payload1, int payload2) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
/* 521 */                   byte flags = ack ? (byte) 1 : (byte) 0;
/* 523 */                   frameHeader(0, 8, Http20Draft14.TYPE_PING, flags);
/* 524 */                   this.sink.writeInt(payload1);
/* 525 */                   this.sink.writeInt(payload2);
/* 526 */                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 530 */           public synchronized void goAway(int lastGoodStreamId, ErrorCode errorCode, byte[] debugData) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
/* 532 */                   if (errorCode.httpCode == -1) {
                                throw Http20Draft14.access$400("errorCode.httpCode == -1", new Object[0]);
                            }
/* 533 */                   int length = debugData.length + 8;
/* 537 */                   frameHeader(0, length, Http20Draft14.TYPE_GOAWAY, (byte) 0);
/* 538 */                   this.sink.writeInt(lastGoodStreamId);
/* 539 */                   this.sink.writeInt(errorCode.httpCode);
/* 540 */                   if (debugData.length > 0) {
/* 541 */                       this.sink.write(debugData);
                            }
/* 543 */                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.spdy.FrameWriter
/* 547 */           public synchronized void windowUpdate(int streamId, long windowSizeIncrement) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
                            if (windowSizeIncrement == 0 || windowSizeIncrement > 2147483647L) {
/* 550 */                       throw Http20Draft14.access$400("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", new Object[]{Long.valueOf(windowSizeIncrement)});
                            }
/* 556 */                   frameHeader(streamId, 4, (byte) 8, (byte) 0);
/* 557 */                   this.sink.writeInt((int) windowSizeIncrement);
/* 558 */                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override // java.io.Closeable, java.lang.AutoCloseable
/* 561 */           public synchronized void close() throws IOException {
                        try {
                            this.closed = true;
/* 563 */                   this.sink.close();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

/* 566 */           void frameHeader(int streamId, int length, byte type, byte flags) throws IOException {
/* 567 */               if (Internal.logger.isLoggable(Level.FINE)) {
                            Internal.logger.fine(FrameLogger.formatHeader(false, streamId, length, type, flags));
                        }
/* 568 */               if (length > this.maxFrameSize) {
/* 569 */                   throw Http20Draft14.access$400("FRAME_SIZE_ERROR length > %d: %d", new Object[]{Integer.valueOf(this.maxFrameSize), Integer.valueOf(length)});
                        }
/* 571 */               if ((Integer.MIN_VALUE & streamId) != 0) {
                            throw Http20Draft14.access$400("reserved bit set: %s", new Object[]{Integer.valueOf(streamId)});
                        }
/* 572 */               Http20Draft14.access$500(this.sink, length);
/* 573 */               this.sink.writeByte(type & Constants.UNKNOWN);
/* 574 */               this.sink.writeByte(flags & Constants.UNKNOWN);
/* 575 */               this.sink.writeInt(Integer.MAX_VALUE & streamId);
                    }
                }

/* 579 */       private static IllegalArgumentException illegalArgument(String message, Object... args) {
/* 580 */           throw new IllegalArgumentException(String.format(message, args));
                }

/* 583 */       private static IOException ioException(String message, Object... args) throws IOException {
/* 584 */           throw new IOException(String.format(message, args));
                }

/* 600 */       static final class ContinuationSource implements Source {
                    byte flags;
                    int left;
                    int length;
                    short padding;
                    private final BufferedSource source;
                    int streamId;

/* 601 */           public ContinuationSource(BufferedSource source) {
/* 603 */               this.source = source;
                    }

                    @Override // okio.Source
/* 606 */           public long read(Buffer sink, long byteCount) throws IOException {
/* 607 */               while (this.left == 0) {
/* 608 */                   this.source.skip(this.padding);
/* 609 */                   this.padding = (short) 0;
/* 610 */                   if ((this.flags & 4) != 0) {
                                return -1L;
                            }
/* 611 */                   readContinuationHeader();
                        }
/* 615 */               long read = this.source.read(sink, Math.min(byteCount, this.left));
                        if (read == -1) {
                            return -1L;
                        }
/* 617 */               this.left = (int) (((long) this.left) - read);
/* 618 */               return read;
                    }

                    @Override // okio.Source
/* 621 */           public Timeout timeout() {
/* 622 */               return this.source.timeout();
                    }

                    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 625 */           public void close() throws IOException {
                    }

/* 628 */           private void readContinuationHeader() throws IOException {
/* 629 */               int previousStreamId = this.streamId;
/* 631 */               int iAccess$200 = Http20Draft14.access$200(this.source);
                        this.left = iAccess$200;
                        this.length = iAccess$200;
/* 632 */               byte type = (byte) (this.source.readByte() & Constants.UNKNOWN);
/* 633 */               this.flags = (byte) (this.source.readByte() & Constants.UNKNOWN);
/* 634 */               if (Internal.logger.isLoggable(Level.FINE)) {
                            Internal.logger.fine(FrameLogger.formatHeader(true, this.streamId, this.length, type, this.flags));
                        }
/* 635 */               this.streamId = this.source.readInt() & Integer.MAX_VALUE;
/* 636 */               if (type != 9) {
                            throw Http20Draft14.access$100("%s != TYPE_CONTINUATION", new Object[]{Byte.valueOf(type)});
                        }
/* 637 */               if (this.streamId != previousStreamId) {
                            throw Http20Draft14.access$100("TYPE_CONTINUATION streamId changed", new Object[0]);
                        }
                    }
                }

/* 642 */       private static int lengthWithoutPadding(int length, byte flags, short padding) throws IOException {
/* 643 */           if ((flags & 8) != 0) {
                        length--;
                    }
/* 644 */           if (padding > length) {
/* 645 */               throw ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(padding), Integer.valueOf(length));
                    }
/* 647 */           return (short) (length - padding);
                }

/* 666 */       static final class FrameLogger {
                    private static final String[] TYPES = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
                    private static final String[] FLAGS = new String[64];
                    private static final String[] BINARY = new String[256];

/* 667 */           FrameLogger() {
                    }

/* 670 */           static String formatHeader(boolean inbound, int streamId, int length, byte type, byte flags) {
/* 671 */               String formattedType = type < TYPES.length ? TYPES[type] : String.format("0x%02x", Byte.valueOf(type));
/* 672 */               String formattedFlags = formatFlags(type, flags);
                        Object[] objArr = new Object[5];
/* 673 */               objArr[0] = inbound ? "<<" : ">>";
                        objArr[1] = Integer.valueOf(streamId);
                        objArr[2] = Integer.valueOf(length);
                        objArr[3] = formattedType;
                        objArr[4] = formattedFlags;
/* 673 */               return String.format("%s 0x%08x %5d %-13s %s", objArr);
                    }

/* 682 */           static String formatFlags(byte type, byte flags) {
/* 683 */               if (flags == 0) {
                            return "";
                        }
                        switch (type) {
                            case 2:
                            case 3:
                            case 7:
                            case 8:
/* 692 */                       return BINARY[flags];
                            case 4:
                            case 6:
                                return flags == 1 ? "ACK" : BINARY[flags];
                            case 5:
                            default:
/* 694 */                       String result = flags < FLAGS.length ? FLAGS[flags] : BINARY[flags];
/* 696 */                       if (type != 5 || (flags & 4) == 0) {
                                    return (type != 0 || (flags & 32) == 0) ? result : result.replace("PRIORITY", "COMPRESSED");
                                }
/* 697 */                       return result.replace("HEADERS", "PUSH_PROMISE");
                        }
                    }

                    static {
                        for (int i = 0; i < BINARY.length; i++) {
/* 727 */                   BINARY[i] = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
                        }
/* 730 */               FLAGS[0] = "";
/* 731 */               FLAGS[1] = "END_STREAM";
/* 733 */               int[] prefixFlags = {1};
/* 735 */               FLAGS[8] = "PADDED";
                        for (int prefixFlag : prefixFlags) {
/* 737 */                   FLAGS[prefixFlag | 8] = FLAGS[prefixFlag] + "|PADDED";
                        }
/* 740 */               FLAGS[4] = "END_HEADERS";
/* 741 */               FLAGS[32] = "PRIORITY";
/* 742 */               FLAGS[36] = "END_HEADERS|PRIORITY";
/* 743 */               int[] frameFlags = {4, 32, 36};
                        for (int frameFlag : frameFlags) {
                            for (int prefixFlag2 : prefixFlags) {
/* 748 */                       FLAGS[prefixFlag2 | frameFlag] = FLAGS[prefixFlag2] + '|' + FLAGS[frameFlag];
/* 749 */                       FLAGS[prefixFlag2 | frameFlag | 8] = FLAGS[prefixFlag2] + '|' + FLAGS[frameFlag] + "|PADDED";
                            }
                        }
                        for (int i2 = 0; i2 < FLAGS.length; i2++) {
/* 755 */                   if (FLAGS[i2] == null) {
                                FLAGS[i2] = BINARY[i2];
                            }
                        }
                    }
                }

/* 760 */       private static int readMedium(BufferedSource source) throws IOException {
/* 761 */           return ((source.readByte() & Constants.UNKNOWN) << 16) | ((source.readByte() & Constants.UNKNOWN) << 8) | (source.readByte() & Constants.UNKNOWN);
                }

/* 766 */       private static void writeMedium(BufferedSink sink, int i) throws IOException {
/* 767 */           sink.writeByte((i >>> 16) & 255);
/* 768 */           sink.writeByte((i >>> 8) & 255);
/* 769 */           sink.writeByte(i & 255);
                }
            }
