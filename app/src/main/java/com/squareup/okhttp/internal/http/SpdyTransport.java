            package com.squareup.okhttp.internal.http;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.squareup.okhttp.Headers;
            import com.squareup.okhttp.Protocol;
            import com.squareup.okhttp.Request;
            import com.squareup.okhttp.Response;
            import com.squareup.okhttp.internal.Util;
            import com.squareup.okhttp.internal.spdy.ErrorCode;
            import com.squareup.okhttp.internal.spdy.Header;
            import com.squareup.okhttp.internal.spdy.SpdyConnection;
            import com.squareup.okhttp.internal.spdy.SpdyStream;
            import java.io.IOException;
            import java.net.ProtocolException;
            import java.util.ArrayList;
            import java.util.LinkedHashSet;
            import java.util.List;
            import java.util.Locale;
            import java.util.Set;
            import java.util.concurrent.TimeUnit;
            import okio.Buffer;
            import okio.ByteString;
            import okio.Sink;
            import okio.Source;
            import okio.Timeout;
            
            
    public final class SpdyTransport implements Transport {
                private final HttpEngine httpEngine;
                private final SpdyConnection spdyConnection;
                private SpdyStream stream;
                private static final List<ByteString> SPDY_3_PROHIBITED_HEADERS = Util.immutableList(ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("transfer-encoding"));
                private static final List<ByteString> HTTP_2_PROHIBITED_HEADERS = Util.immutableList(ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("te"), ByteString.encodeUtf8("transfer-encoding"), ByteString.encodeUtf8("encoding"), ByteString.encodeUtf8("upgrade"));

        public SpdyTransport(HttpEngine httpEngine, SpdyConnection spdyConnection) {
            this.httpEngine = httpEngine;
            this.spdyConnection = spdyConnection;
                }

                @Override 
        public Sink createRequestBody(Request request, long contentLength) throws IOException {
            return this.stream.getSink();
                }

                @Override 
        public void writeRequestHeaders(Request request) throws IOException {
            if (this.stream == null) {
                this.httpEngine.writingRequestHeaders();
                boolean permitsRequestBody = this.httpEngine.permitsRequestBody();
                String version = RequestLine.version(this.httpEngine.getConnection().getProtocol());
                this.stream = this.spdyConnection.newStream(writeNameValueBlock(request, this.spdyConnection.getProtocol(), version), permitsRequestBody, true);
                this.stream.readTimeout().timeout(this.httpEngine.client.getReadTimeout(), TimeUnit.MILLISECONDS);
                    }
                }

                @Override 
        public void writeRequestBody(RetryableSink requestBody) throws IOException {
            requestBody.writeToSocket(this.stream.getSink());
                }

                @Override 
       public void flushRequest() throws IOException {
           this.stream.getSink().close();
                }

                @Override 
       public Response.Builder readResponseHeaders() throws IOException {
           return readNameValueBlock(this.stream.getResponseHeaders(), this.spdyConnection.getProtocol());
                }

       public static List<Header> writeNameValueBlock(Request request, Protocol protocol, String version) {
           Headers headers = request.headers();
           List<Header> result = new ArrayList<>(headers.size() + 10);
           result.add(new Header(Header.TARGET_METHOD, request.method()));
           result.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(request.url())));
           String host = HttpEngine.hostHeader(request.url());
           if (Protocol.SPDY_3 == protocol) {
               result.add(new Header(Header.VERSION, version));
               result.add(new Header(Header.TARGET_HOST, host));
                    } else if (Protocol.HTTP_2 == protocol) {
               result.add(new Header(Header.TARGET_AUTHORITY, host));
                    } else {
               throw new AssertionError();
                    }
           result.add(new Header(Header.TARGET_SCHEME, request.url().getProtocol()));
           Set<ByteString> names = new LinkedHashSet<>();
                    for (int i = 0; i < headers.size(); i++) {
               ByteString name = ByteString.encodeUtf8(headers.name(i).toLowerCase(Locale.US));
               String value = headers.value(i);
               if (!isProhibitedHeader(protocol, name) && !name.equals(Header.TARGET_METHOD) && !name.equals(Header.TARGET_PATH) && !name.equals(Header.TARGET_SCHEME) && !name.equals(Header.TARGET_AUTHORITY) && !name.equals(Header.TARGET_HOST) && !name.equals(Header.VERSION)) {
                   if (names.add(name)) {
                       result.add(new Header(name, value));
                            } else {
                                for (int j = 0; j < result.size(); j++) {
                           if (result.get(j).name.equals(name)) {
                               String concatenated = joinOnNull(result.get(j).value.utf8(), value);
                               result.set(j, new Header(name, concatenated));
                                        break;
                                    }
                                }
                            }
                        }
                    }
           return result;
                }

       private static String joinOnNull(String first, String second) {
           return first + (char) 0 + second;
                }

       public static Response.Builder readNameValueBlock(List<Header> headerBlock, Protocol protocol) throws IOException {
           String status = null;
           String version = "HTTP/1.1";
           Headers.Builder headersBuilder = new Headers.Builder();
           headersBuilder.set(OkHeaders.SELECTED_PROTOCOL, protocol.toString());
                    for (int i = 0; i < headerBlock.size(); i++) {
               ByteString name = headerBlock.get(i).name;
               String values = headerBlock.get(i).value.utf8();
               int start = 0;
                        while (start < values.length()) {
                   int end = values.indexOf(0, start);
                   if (end == -1) {
                       end = values.length();
                            }
                   String value = values.substring(start, end);
                   if (name.equals(Header.RESPONSE_STATUS)) {
                       status = value;
                            } else if (name.equals(Header.VERSION)) {
                       version = value;
                            } else if (!isProhibitedHeader(protocol, name)) {
                       headersBuilder.add(name.utf8(), value);
                            }
                   start = end + 1;
                        }
                    }
           if (status == null) {
                        throw new ProtocolException("Expected ':status' header not present");
                    }
           if (version == null) {
                        throw new ProtocolException("Expected ':version' header not present");
                    }
           StatusLine statusLine = StatusLine.parse(version + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + status);
           return new Response.Builder().protocol(protocol).code(statusLine.code).message(statusLine.message).headers(headersBuilder.build());
                }

                @Override 
       public void emptyTransferStream() {
                }

                @Override 
       public Source getTransferStream(CacheRequest cacheRequest) throws IOException {
           return new SpdySource(this.stream, cacheRequest);
                }

                @Override 
       public void releaseConnectionOnIdle() {
                }

                @Override 
       public void disconnect(HttpEngine engine) throws IOException {
           this.stream.close(ErrorCode.CANCEL);
                }

                @Override 
       public boolean canReuseConnection() {
           return true;
                }

       private static boolean isProhibitedHeader(Protocol protocol, ByteString name) {
           if (protocol == Protocol.SPDY_3) {
               return SPDY_3_PROHIBITED_HEADERS.contains(name);
                    }
           if (protocol == Protocol.HTTP_2) {
               return HTTP_2_PROHIBITED_HEADERS.contains(name);
                    }
           throw new AssertionError(protocol);
                }

       private static class SpdySource implements Source {
                    private final Sink cacheBody;
                    private final CacheRequest cacheRequest;
                    private boolean closed;
                    private boolean inputExhausted;
                    private final Source source;
                    private final SpdyStream stream;

           SpdySource(SpdyStream stream, CacheRequest cacheRequest) throws IOException {
               this.stream = stream;
               this.source = stream.getSource();
               Sink cacheBody = cacheRequest != null ? cacheRequest.body() : null;
               cacheRequest = cacheBody == null ? null : cacheRequest;
               this.cacheBody = cacheBody;
               this.cacheRequest = cacheRequest;
                    }

                    @Override 
           public long read(Buffer buffer, long byteCount) throws IOException {
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
               if (this.inputExhausted) {
                            return -1L;
                        }
               long read = this.source.read(buffer, byteCount);
                        if (read == -1) {
                   this.inputExhausted = true;
                   if (this.cacheRequest != null) {
                       this.cacheBody.close();
                            }
                            return -1L;
                        }
               if (this.cacheBody != null) {
                   this.cacheBody.write(buffer.m2481clone(), read);
                   return read;
                        }
               return read;
                    }

                    @Override 
           public Timeout timeout() {
               return this.source.timeout();
                    }

                    @Override 
           public void close() throws IOException {
               if (!this.closed) {
                   if (!this.inputExhausted && this.cacheBody != null) {
                       discardStream();
                            }
                   this.closed = true;
                   if (!this.inputExhausted) {
                       this.stream.closeLater(ErrorCode.CANCEL);
                       if (this.cacheRequest != null) {
                           this.cacheRequest.abort();
                                }
                            }
                        }
                    }

           private boolean discardStream() {
               long oldTimeoutNanos = this.stream.readTimeout().timeoutNanos();
               this.stream.readTimeout().timeout(100L, TimeUnit.MILLISECONDS);
                        try {
                   Util.skipAll(this, 100);
                   this.stream.readTimeout().timeout(oldTimeoutNanos, TimeUnit.NANOSECONDS);
                   return true;
                        } catch (IOException e) {
                   this.stream.readTimeout().timeout(oldTimeoutNanos, TimeUnit.NANOSECONDS);
                   return false;
                        } catch (Throwable th) {
                            this.stream.readTimeout().timeout(oldTimeoutNanos, TimeUnit.NANOSECONDS);
                            throw th;
                        }
                    }
                }
            }
