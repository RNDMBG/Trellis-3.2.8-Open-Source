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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 72 */    public final class SpdyTransport implements Transport {
                private final HttpEngine httpEngine;
                private final SpdyConnection spdyConnection;
                private SpdyStream stream;
                private static final List<ByteString> SPDY_3_PROHIBITED_HEADERS = Util.immutableList(ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("transfer-encoding"));
                private static final List<ByteString> HTTP_2_PROHIBITED_HEADERS = Util.immutableList(ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("te"), ByteString.encodeUtf8("transfer-encoding"), ByteString.encodeUtf8("encoding"), ByteString.encodeUtf8("upgrade"));

/* 73 */        public SpdyTransport(HttpEngine httpEngine, SpdyConnection spdyConnection) {
/* 75 */            this.httpEngine = httpEngine;
/* 76 */            this.spdyConnection = spdyConnection;
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 79 */        public Sink createRequestBody(Request request, long contentLength) throws IOException {
/* 80 */            return this.stream.getSink();
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 83 */        public void writeRequestHeaders(Request request) throws IOException {
/* 84 */            if (this.stream == null) {
/* 86 */                this.httpEngine.writingRequestHeaders();
/* 87 */                boolean permitsRequestBody = this.httpEngine.permitsRequestBody();
/* 89 */                String version = RequestLine.version(this.httpEngine.getConnection().getProtocol());
/* 88 */                this.stream = this.spdyConnection.newStream(writeNameValueBlock(request, this.spdyConnection.getProtocol(), version), permitsRequestBody, true);
/* 93 */                this.stream.readTimeout().timeout(this.httpEngine.client.getReadTimeout(), TimeUnit.MILLISECONDS);
                    }
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 96 */        public void writeRequestBody(RetryableSink requestBody) throws IOException {
/* 97 */            requestBody.writeToSocket(this.stream.getSink());
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 100 */       public void flushRequest() throws IOException {
/* 101 */           this.stream.getSink().close();
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 104 */       public Response.Builder readResponseHeaders() throws IOException {
/* 105 */           return readNameValueBlock(this.stream.getResponseHeaders(), this.spdyConnection.getProtocol());
                }

/* 114 */       public static List<Header> writeNameValueBlock(Request request, Protocol protocol, String version) {
/* 115 */           Headers headers = request.headers();
/* 116 */           List<Header> result = new ArrayList<>(headers.size() + 10);
/* 117 */           result.add(new Header(Header.TARGET_METHOD, request.method()));
/* 118 */           result.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(request.url())));
/* 119 */           String host = HttpEngine.hostHeader(request.url());
/* 120 */           if (Protocol.SPDY_3 == protocol) {
/* 121 */               result.add(new Header(Header.VERSION, version));
/* 122 */               result.add(new Header(Header.TARGET_HOST, host));
                    } else if (Protocol.HTTP_2 == protocol) {
/* 124 */               result.add(new Header(Header.TARGET_AUTHORITY, host));
                    } else {
/* 126 */               throw new AssertionError();
                    }
/* 128 */           result.add(new Header(Header.TARGET_SCHEME, request.url().getProtocol()));
/* 130 */           Set<ByteString> names = new LinkedHashSet<>();
                    for (int i = 0; i < headers.size(); i++) {
/* 133 */               ByteString name = ByteString.encodeUtf8(headers.name(i).toLowerCase(Locale.US));
/* 134 */               String value = headers.value(i);
/* 137 */               if (!isProhibitedHeader(protocol, name) && !name.equals(Header.TARGET_METHOD) && !name.equals(Header.TARGET_PATH) && !name.equals(Header.TARGET_SCHEME) && !name.equals(Header.TARGET_AUTHORITY) && !name.equals(Header.TARGET_HOST) && !name.equals(Header.VERSION)) {
/* 150 */                   if (names.add(name)) {
/* 151 */                       result.add(new Header(name, value));
                            } else {
                                for (int j = 0; j < result.size(); j++) {
/* 157 */                           if (result.get(j).name.equals(name)) {
/* 158 */                               String concatenated = joinOnNull(result.get(j).value.utf8(), value);
/* 159 */                               result.set(j, new Header(name, concatenated));
                                        break;
                                    }
                                }
                            }
                        }
                    }
/* 164 */           return result;
                }

/* 167 */       private static String joinOnNull(String first, String second) {
/* 168 */           return first + (char) 0 + second;
                }

/* 173 */       public static Response.Builder readNameValueBlock(List<Header> headerBlock, Protocol protocol) throws IOException {
/* 174 */           String status = null;
/* 175 */           String version = "HTTP/1.1";
/* 177 */           Headers.Builder headersBuilder = new Headers.Builder();
/* 178 */           headersBuilder.set(OkHeaders.SELECTED_PROTOCOL, protocol.toString());
                    for (int i = 0; i < headerBlock.size(); i++) {
/* 180 */               ByteString name = headerBlock.get(i).name;
/* 181 */               String values = headerBlock.get(i).value.utf8();
/* 182 */               int start = 0;
                        while (start < values.length()) {
/* 183 */                   int end = values.indexOf(0, start);
/* 184 */                   if (end == -1) {
/* 185 */                       end = values.length();
                            }
/* 187 */                   String value = values.substring(start, end);
/* 188 */                   if (name.equals(Header.RESPONSE_STATUS)) {
/* 189 */                       status = value;
                            } else if (name.equals(Header.VERSION)) {
/* 191 */                       version = value;
                            } else if (!isProhibitedHeader(protocol, name)) {
/* 193 */                       headersBuilder.add(name.utf8(), value);
                            }
/* 195 */                   start = end + 1;
                        }
                    }
/* 198 */           if (status == null) {
                        throw new ProtocolException("Expected ':status' header not present");
                    }
/* 199 */           if (version == null) {
                        throw new ProtocolException("Expected ':version' header not present");
                    }
/* 201 */           StatusLine statusLine = StatusLine.parse(version + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + status);
/* 202 */           return new Response.Builder().protocol(protocol).code(statusLine.code).message(statusLine.message).headers(headersBuilder.build());
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 210 */       public void emptyTransferStream() {
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 213 */       public Source getTransferStream(CacheRequest cacheRequest) throws IOException {
/* 214 */           return new SpdySource(this.stream, cacheRequest);
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 217 */       public void releaseConnectionOnIdle() {
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 220 */       public void disconnect(HttpEngine engine) throws IOException {
/* 221 */           this.stream.close(ErrorCode.CANCEL);
                }

                @Override // com.squareup.okhttp.internal.http.Transport
/* 224 */       public boolean canReuseConnection() {
/* 225 */           return true;
                }

/* 229 */       private static boolean isProhibitedHeader(Protocol protocol, ByteString name) {
/* 230 */           if (protocol == Protocol.SPDY_3) {
/* 231 */               return SPDY_3_PROHIBITED_HEADERS.contains(name);
                    }
/* 232 */           if (protocol == Protocol.HTTP_2) {
/* 233 */               return HTTP_2_PROHIBITED_HEADERS.contains(name);
                    }
/* 235 */           throw new AssertionError(protocol);
                }

/* 247 */       private static class SpdySource implements Source {
                    private final Sink cacheBody;
                    private final CacheRequest cacheRequest;
                    private boolean closed;
                    private boolean inputExhausted;
                    private final Source source;
                    private final SpdyStream stream;

/* 248 */           SpdySource(SpdyStream stream, CacheRequest cacheRequest) throws IOException {
/* 250 */               this.stream = stream;
/* 251 */               this.source = stream.getSource();
/* 254 */               Sink cacheBody = cacheRequest != null ? cacheRequest.body() : null;
/* 255 */               cacheRequest = cacheBody == null ? null : cacheRequest;
/* 259 */               this.cacheBody = cacheBody;
/* 260 */               this.cacheRequest = cacheRequest;
                    }

                    @Override // okio.Source
/* 264 */           public long read(Buffer buffer, long byteCount) throws IOException {
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
/* 266 */               if (this.closed) {
                            throw new IllegalStateException("closed");
                        }
/* 267 */               if (this.inputExhausted) {
                            return -1L;
                        }
/* 269 */               long read = this.source.read(buffer, byteCount);
                        if (read == -1) {
/* 271 */                   this.inputExhausted = true;
/* 272 */                   if (this.cacheRequest != null) {
/* 273 */                       this.cacheBody.close();
                            }
                            return -1L;
                        }
/* 278 */               if (this.cacheBody != null) {
/* 280 */                   this.cacheBody.write(buffer.m2481clone(), read);
/* 283 */                   return read;
                        }
/* 283 */               return read;
                    }

                    @Override // okio.Source
/* 286 */           public Timeout timeout() {
/* 287 */               return this.source.timeout();
                    }

                    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 290 */           public void close() throws IOException {
/* 291 */               if (!this.closed) {
/* 293 */                   if (!this.inputExhausted && this.cacheBody != null) {
/* 294 */                       discardStream();
                            }
/* 297 */                   this.closed = true;
/* 299 */                   if (!this.inputExhausted) {
/* 300 */                       this.stream.closeLater(ErrorCode.CANCEL);
/* 301 */                       if (this.cacheRequest != null) {
/* 302 */                           this.cacheRequest.abort();
                                }
                            }
                        }
                    }

/* 307 */           private boolean discardStream() {
/* 308 */               long oldTimeoutNanos = this.stream.readTimeout().timeoutNanos();
/* 309 */               this.stream.readTimeout().timeout(100L, TimeUnit.MILLISECONDS);
                        try {
/* 311 */                   Util.skipAll(this, 100);
/* 316 */                   this.stream.readTimeout().timeout(oldTimeoutNanos, TimeUnit.NANOSECONDS);
/* 312 */                   return true;
                        } catch (IOException e) {
/* 316 */                   this.stream.readTimeout().timeout(oldTimeoutNanos, TimeUnit.NANOSECONDS);
/* 314 */                   return false;
                        } catch (Throwable th) {
                            this.stream.readTimeout().timeout(oldTimeoutNanos, TimeUnit.NANOSECONDS);
                            throw th;
                        }
                    }
                }
            }
