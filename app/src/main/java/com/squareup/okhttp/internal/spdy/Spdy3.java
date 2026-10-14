            package com.squareup.okhttp.internal.spdy;

            import android.support.v4.internal.view.SupportMenu;
            import android.support.v4.view.ViewCompat;
            import com.squareup.okhttp.Protocol;
            import com.squareup.okhttp.internal.Util;
            import java.io.IOException;
            import java.io.UnsupportedEncodingException;
            import java.net.ProtocolException;
            import java.util.List;
            import java.util.zip.Deflater;
            import okio.Buffer;
            import okio.BufferedSink;
            import okio.BufferedSource;
            import okio.ByteString;
            import okio.DeflaterSink;
            import okio.Okio;
            import okio.Sink;
            
            
    public final class Spdy3 implements Variant {
                static final byte[] DICTIONARY;
                static final int FLAG_FIN = 1;
                static final int FLAG_UNIDIRECTIONAL = 2;
                static final int TYPE_DATA = 0;
                static final int TYPE_GOAWAY = 7;
                static final int TYPE_HEADERS = 8;
                static final int TYPE_PING = 6;
                static final int TYPE_RST_STREAM = 3;
                static final int TYPE_SETTINGS = 4;
                static final int TYPE_SYN_REPLY = 2;
                static final int TYPE_SYN_STREAM = 1;
                static final int TYPE_WINDOW_UPDATE = 9;
                static final int VERSION = 3;

                @Override 
        public Protocol getProtocol() {
            return Protocol.SPDY_3;
                }

                static {
                    try {
                DICTIONARY = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.".getBytes(Util.UTF_8.name());
                    } catch (UnsupportedEncodingException e) {
                throw new AssertionError();
                    }
                }

                @Override 
        public FrameReader newReader(BufferedSource source, boolean client) {
            return new Reader(source, client);
                }

                @Override 
       public FrameWriter newWriter(BufferedSink sink, boolean client) {
           return new Writer(sink, client);
                }

       static final class Reader implements FrameReader {
                    private final boolean client;
                    private final NameValueBlockReader headerBlockReader;
                    private final BufferedSource source;

           Reader(BufferedSource source, boolean client) {
               this.source = source;
               this.headerBlockReader = new NameValueBlockReader(this.source);
               this.client = client;
                    }

                    @Override 
           public void readConnectionPreface() {
                    }

                    @Override 
           public boolean nextFrame(FrameReader.Handler handler) throws IOException {
                        try {
                   int w1 = this.source.readInt();
                   int w2 = this.source.readInt();
                   boolean control = (Integer.MIN_VALUE & w1) != 0;
                   int flags = ((-16777216) & w2) >>> 24;
                   int length = w2 & ViewCompat.MEASURED_SIZE_MASK;
                   if (control) {
                       int version = (2147418112 & w1) >>> 16;
                       int type = w1 & SupportMenu.USER_MASK;
                       if (version != 3) {
                           throw new ProtocolException("version != 3: " + version);
                                }
                                switch (type) {
                                    case 1:
                               readSynStream(handler, flags, length);
                                        return true;
                                    case 2:
                               readSynReply(handler, flags, length);
                                        return true;
                                    case 3:
                               readRstStream(handler, flags, length);
                                        return true;
                                    case 4:
                               readSettings(handler, flags, length);
                                        return true;
                                    case 5:
                                    default:
                               this.source.skip(length);
                                        return true;
                                    case 6:
                               readPing(handler, flags, length);
                                        return true;
                                    case 7:
                               readGoAway(handler, flags, length);
                                        return true;
                                    case 8:
                               readHeaders(handler, flags, length);
                                        return true;
                                    case 9:
                               readWindowUpdate(handler, flags, length);
                                        return true;
                                }
                            }
                   int streamId = w1 & Integer.MAX_VALUE;
                   boolean inFinished = (flags & 1) != 0;
                   handler.data(inFinished, streamId, this.source, length);
                            return true;
                        } catch (IOException e) {
                            return false;
                        }
                    }

           private void readSynStream(FrameReader.Handler handler, int flags, int length) throws IOException {
               int w1 = this.source.readInt();
               int w2 = this.source.readInt();
               int streamId = w1 & Integer.MAX_VALUE;
               int associatedStreamId = w2 & Integer.MAX_VALUE;
               this.source.readShort();
               List<Header> headerBlock = this.headerBlockReader.readNameValueBlock(length - 10);
               boolean inFinished = (flags & 1) != 0;
               boolean outFinished = (flags & 2) != 0;
               handler.headers(outFinished, inFinished, streamId, associatedStreamId, headerBlock, HeadersMode.SPDY_SYN_STREAM);
                    }

           private void readSynReply(FrameReader.Handler handler, int flags, int length) throws IOException {
               int w1 = this.source.readInt();
               int streamId = w1 & Integer.MAX_VALUE;
               List<Header> headerBlock = this.headerBlockReader.readNameValueBlock(length - 4);
               boolean inFinished = (flags & 1) != 0;
               handler.headers(false, inFinished, streamId, -1, headerBlock, HeadersMode.SPDY_REPLY);
                    }

           private void readRstStream(FrameReader.Handler handler, int flags, int length) throws IOException {
               if (length != 8) {
                            throw ioException("TYPE_RST_STREAM length: %d != 8", Integer.valueOf(length));
                        }
               int streamId = this.source.readInt() & Integer.MAX_VALUE;
               int errorCodeInt = this.source.readInt();
               ErrorCode errorCode = ErrorCode.fromSpdy3Rst(errorCodeInt);
               if (errorCode == null) {
                   throw ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(errorCodeInt));
                        }
               handler.rstStream(streamId, errorCode);
                    }

           private void readHeaders(FrameReader.Handler handler, int flags, int length) throws IOException {
               int w1 = this.source.readInt();
               int streamId = w1 & Integer.MAX_VALUE;
               List<Header> headerBlock = this.headerBlockReader.readNameValueBlock(length - 4);
               handler.headers(false, false, streamId, -1, headerBlock, HeadersMode.SPDY_HEADERS);
                    }

           private void readWindowUpdate(FrameReader.Handler handler, int flags, int length) throws IOException {
               if (length != 8) {
                            throw ioException("TYPE_WINDOW_UPDATE length: %d != 8", Integer.valueOf(length));
                        }
               int w1 = this.source.readInt();
               int w2 = this.source.readInt();
               int streamId = w1 & Integer.MAX_VALUE;
               long increment = w2 & Integer.MAX_VALUE;
                        if (increment == 0) {
                            throw ioException("windowSizeIncrement was 0", Long.valueOf(increment));
                        }
               handler.windowUpdate(streamId, increment);
                    }

           private void readPing(FrameReader.Handler handler, int flags, int length) throws IOException {
               if (length != 4) {
                            throw ioException("TYPE_PING length: %d != 4", Integer.valueOf(length));
                        }
               int id = this.source.readInt();
                        boolean ack = this.client == ((id & 1) == 1);
               handler.ping(ack, id, 0);
                    }

           private void readGoAway(FrameReader.Handler handler, int flags, int length) throws IOException {
               if (length != 8) {
                            throw ioException("TYPE_GOAWAY length: %d != 8", Integer.valueOf(length));
                        }
               int lastGoodStreamId = this.source.readInt() & Integer.MAX_VALUE;
               int errorCodeInt = this.source.readInt();
               ErrorCode errorCode = ErrorCode.fromSpdyGoAway(errorCodeInt);
               if (errorCode == null) {
                   throw ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(errorCodeInt));
                        }
               handler.goAway(lastGoodStreamId, errorCode, ByteString.EMPTY);
                    }

           private void readSettings(FrameReader.Handler handler, int flags, int length) throws IOException {
               int numberOfEntries = this.source.readInt();
               if (length != (numberOfEntries * 8) + 4) {
                   throw ioException("TYPE_SETTINGS length: %d != 4 + 8 * %d", Integer.valueOf(length), Integer.valueOf(numberOfEntries));
                        }
               Settings settings = new Settings();
                        for (int i = 0; i < numberOfEntries; i++) {
                   int w1 = this.source.readInt();
                   int value = this.source.readInt();
                   int idFlags = ((-16777216) & w1) >>> 24;
                   int id = w1 & ViewCompat.MEASURED_SIZE_MASK;
                   settings.set(id, idFlags, value);
                        }
               boolean clearPrevious = (flags & 1) != 0;
               handler.settings(clearPrevious, settings);
                    }

           private static IOException ioException(String message, Object... args) throws IOException {
               throw new IOException(String.format(message, args));
                    }

                    @Override 
           public void close() throws IOException {
               this.headerBlockReader.close();
                    }
                }

       static final class Writer implements FrameWriter {
                    private final boolean client;
                    private boolean closed;
                    private final Buffer headerBlockBuffer;
                    private final BufferedSink headerBlockOut;
                    private final BufferedSink sink;

           Writer(BufferedSink sink, boolean client) {
               this.sink = sink;
               this.client = client;
               Deflater deflater = new Deflater();
               deflater.setDictionary(Spdy3.DICTIONARY);
               this.headerBlockBuffer = new Buffer();
               this.headerBlockOut = Okio.buffer(new DeflaterSink((Sink) this.headerBlockBuffer, deflater));
                    }

                    @Override 
           public void ackSettings(Settings peerSettings) {
                    }

                    @Override 
           public void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) throws IOException {
                    }

                    @Override 
           public synchronized void connectionPreface() {
                    }

                    @Override 
           public synchronized void flush() throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
                   this.sink.flush();
                        } catch (Throwable th) {
                            if (th instanceof IOException) {
                                throw (IOException) th;
                            }
                            throw new IOException(th);
                        }
                    }

                    @Override 
           public synchronized void synStream(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
                   writeNameValueBlockToBuffer(headerBlock);
                   int length = (int) (10 + this.headerBlockBuffer.size());
                   int flags = (outFinished ? 1 : 0) | (inFinished ? 2 : 0);
                   this.sink.writeInt(-2147287039);
                   this.sink.writeInt(((flags & 255) << 24) | (16777215 & length));
                   this.sink.writeInt(Integer.MAX_VALUE & streamId);
                   this.sink.writeInt(Integer.MAX_VALUE & associatedStreamId);
                   this.sink.writeShort(0);
                   this.sink.writeAll(this.headerBlockBuffer);
                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override 
           public synchronized void synReply(boolean outFinished, int streamId, List<Header> headerBlock) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
                   writeNameValueBlockToBuffer(headerBlock);
                   int flags = outFinished ? 1 : 0;
                   int length = (int) (this.headerBlockBuffer.size() + 4);
                   this.sink.writeInt(-2147287038);
                   this.sink.writeInt(((flags & 255) << 24) | (16777215 & length));
                   this.sink.writeInt(Integer.MAX_VALUE & streamId);
                   this.sink.writeAll(this.headerBlockBuffer);
                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override 
           public synchronized void headers(int streamId, List<Header> headerBlock) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
                   writeNameValueBlockToBuffer(headerBlock);
                   int length = (int) (this.headerBlockBuffer.size() + 4);
                   this.sink.writeInt(-2147287032);
                   this.sink.writeInt((16777215 & length) | 0);
                   this.sink.writeInt(Integer.MAX_VALUE & streamId);
                   this.sink.writeAll(this.headerBlockBuffer);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override 
           public synchronized void rstStream(int streamId, ErrorCode errorCode) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
                   if (errorCode.spdyRstCode == -1) {
                                throw new IllegalArgumentException();
                            }
                   this.sink.writeInt(-2147287037);
                   this.sink.writeInt(8);
                   this.sink.writeInt(Integer.MAX_VALUE & streamId);
                   this.sink.writeInt(errorCode.spdyRstCode);
                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override 
           public int maxDataLength() {
               return 16383;
                    }

                    @Override 
           public synchronized void data(boolean outFinished, int streamId, Buffer source, int byteCount) throws IOException {
               int flags = outFinished ? 1 : 0;
                        try {
                   sendDataFrame(streamId, flags, source, byteCount);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

           void sendDataFrame(int streamId, int flags, Buffer buffer, int byteCount) throws IOException {
               if (this.closed) {
                            throw new IOException("closed");
                        }
                        if (byteCount > 16777215) {
                   throw new IllegalArgumentException("FRAME_TOO_LARGE max size is 16Mib: " + byteCount);
                        }
               this.sink.writeInt(Integer.MAX_VALUE & streamId);
               this.sink.writeInt(((flags & 255) << 24) | (16777215 & byteCount));
               if (byteCount > 0) {
                   this.sink.write(buffer, byteCount);
                        }
                    }

           private void writeNameValueBlockToBuffer(List<Header> headerBlock) throws IOException {
                        if (this.headerBlockBuffer.size() != 0) {
                            throw new IllegalStateException();
                        }
               this.headerBlockOut.writeInt(headerBlock.size());
                        int size = headerBlock.size();
                        for (int i = 0; i < size; i++) {
                   ByteString name = headerBlock.get(i).name;
                   this.headerBlockOut.writeInt(name.size());
                   this.headerBlockOut.write(name);
                   ByteString value = headerBlock.get(i).value;
                   this.headerBlockOut.writeInt(value.size());
                   this.headerBlockOut.write(value);
                        }
               this.headerBlockOut.flush();
                    }

                    @Override 
           public synchronized void settings(Settings settings) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
                   int size = settings.size();
                   int length = (size * 8) + 4;
                   this.sink.writeInt(-2147287036);
                   this.sink.writeInt((length & ViewCompat.MEASURED_SIZE_MASK) | 0);
                   this.sink.writeInt(size);
                            for (int i = 0; i <= 10; i++) {
                       if (settings.isSet(i)) {
                           int settingsFlags = settings.flags(i);
                           this.sink.writeInt(((settingsFlags & 255) << 24) | (i & ViewCompat.MEASURED_SIZE_MASK));
                           this.sink.writeInt(settings.get(i));
                                }
                            }
                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override 
           public synchronized void ping(boolean reply, int payload1, int payload2) throws IOException {
                        synchronized (this) {
                            try {
                                if (this.closed) {
                                    throw new IOException("closed");
                                }
                                boolean payloadIsReply = this.client != ((payload1 & 1) == 1);
                       if (reply != payloadIsReply) {
                                    throw new IllegalArgumentException("payload != reply");
                                }
                       this.sink.writeInt(-2147287034);
                       this.sink.writeInt(4);
                       this.sink.writeInt(payload1);
                       this.sink.flush();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override 
           public synchronized void goAway(int lastGoodStreamId, ErrorCode errorCode, byte[] ignored) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
                   if (errorCode.spdyGoAwayCode == -1) {
                       throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
                            }
                   this.sink.writeInt(-2147287033);
                   this.sink.writeInt(8);
                   this.sink.writeInt(lastGoodStreamId);
                   this.sink.writeInt(errorCode.spdyGoAwayCode);
                   this.sink.flush();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

                    @Override 
           public synchronized void windowUpdate(int streamId, long increment) throws IOException {
                        try {
                            if (this.closed) {
                                throw new IOException("closed");
                            }
                            if (increment == 0 || increment > 2147483647L) {
                       throw new IllegalArgumentException("windowSizeIncrement must be between 1 and 0x7fffffff: " + increment);
                            }
                   this.sink.writeInt(-2147287031);
                   this.sink.writeInt(8);
                   this.sink.writeInt(streamId);
                   this.sink.writeInt((int) increment);
                   this.sink.flush();
                        } catch (IOException e) {
                            throw e;
                        }
                    }

                    @Override 
           public synchronized void close() throws IOException {
                        try {
                            this.closed = true;
                   Util.closeAll(this.sink, this.headerBlockOut);
                        } catch (Throwable th) {
                            if (th instanceof IOException) {
                                throw (IOException) th;
                            }
                            throw new IOException(th);
                        }
                    }
                }
            }
