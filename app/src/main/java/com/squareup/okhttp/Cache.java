            package com.squareup.okhttp;

            import co.vine.android.client.VineAPI;
            import com.squareup.okhttp.internal.DiskLruCache;
            import com.squareup.okhttp.internal.InternalCache;
            import com.squareup.okhttp.internal.Util;
            import com.squareup.okhttp.internal.http.CacheRequest;
            import com.squareup.okhttp.internal.http.CacheStrategy;
            import com.squareup.okhttp.internal.http.HttpMethod;
            import com.squareup.okhttp.internal.http.OkHeaders;
            import com.squareup.okhttp.internal.http.StatusLine;
            import java.io.ByteArrayInputStream;
            import java.io.File;
            import java.io.IOException;
            import java.security.cert.Certificate;
            import java.security.cert.CertificateEncodingException;
            import java.security.cert.CertificateException;
            import java.security.cert.CertificateFactory;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            import okio.BufferedSink;
            import okio.BufferedSource;
            import okio.ByteString;
            import okio.ForwardingSink;
            import okio.ForwardingSource;
            import okio.Okio;
            import okio.Sink;
            import okio.Source;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 101 */   public final class Cache {
                private static final int ENTRY_BODY = 1;
                private static final int ENTRY_COUNT = 2;
                private static final int ENTRY_METADATA = 0;
                private static final int VERSION = 201105;
                private final DiskLruCache cache;
                private int hitCount;
                final InternalCache internalCache = new C12541();
                private int networkCount;
                private int requestCount;
                private int writeAbortCount;
                private int writeSuccessCount;

/* 102 */       static /* synthetic */ CacheRequest access$000(Cache x0, Response x1) throws IOException {
/* 103 */           return x0.put(x1);
                }

/* 102 */       static /* synthetic */ void access$100(Cache x0, Request x1) throws IOException {
/* 103 */           x0.remove(x1);
                }

/* 102 */       static /* synthetic */ void access$200(Cache x0, Response x1, Response x2) {
/* 103 */           x0.update(x1, x2);
                }

/* 102 */       static /* synthetic */ void access$300(Cache x0) {
/* 103 */           x0.trackConditionalCacheHit();
                }

/* 102 */       static /* synthetic */ void access$400(Cache x0, CacheStrategy x1) {
/* 103 */           x0.trackResponse(x1);
                }

/* 102 */       static /* synthetic */ int access$708(Cache x0) {
/* 103 */           int i = x0.writeSuccessCount;
                    x0.writeSuccessCount = i + 1;
                    return i;
                }

/* 102 */       static /* synthetic */ int access$808(Cache x0) {
/* 103 */           int i = x0.writeAbortCount;
                    x0.writeAbortCount = i + 1;
                    return i;
                }

/* 102 */       static /* synthetic */ int access$900(BufferedSource x0) throws IOException {
/* 103 */           return readInt(x0);
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.Cache$1 */
/* 108 */       class C12541 implements InternalCache {
/* 109 */           C12541() {
                    }

                    @Override // com.squareup.okhttp.internal.InternalCache
/* 111 */           public Response get(Request request) throws IOException {
/* 112 */               return Cache.this.get(request);
                    }

                    @Override // com.squareup.okhttp.internal.InternalCache
/* 114 */           public CacheRequest put(Response response) throws IOException {
/* 115 */               return Cache.access$000(Cache.this, response);
                    }

                    @Override // com.squareup.okhttp.internal.InternalCache
/* 117 */           public void remove(Request request) throws IOException {
/* 118 */               Cache.access$100(Cache.this, request);
                    }

                    @Override // com.squareup.okhttp.internal.InternalCache
/* 120 */           public void update(Response cached, Response network) throws IOException {
/* 121 */               Cache.access$200(Cache.this, cached, network);
                    }

                    @Override // com.squareup.okhttp.internal.InternalCache
/* 123 */           public void trackConditionalCacheHit() {
/* 124 */               Cache.access$300(Cache.this);
                    }

                    @Override // com.squareup.okhttp.internal.InternalCache
/* 126 */           public void trackResponse(CacheStrategy cacheStrategy) {
/* 127 */               Cache.access$400(Cache.this, cacheStrategy);
                    }
                }

/* 139 */       public Cache(File directory, long maxSize) throws IOException {
/* 141 */           this.cache = DiskLruCache.open(directory, VERSION, 2, maxSize);
                }

/* 144 */       private static String urlToKey(Request request) {
/* 145 */           return Util.hash(request.urlString());
                }

/* 148 */       Response get(Request request) {
/* 149 */           String key = urlToKey(request);
                    try {
/* 153 */               DiskLruCache.Snapshot snapshot = this.cache.get(key);
/* 154 */               if (snapshot == null) {
                            return null;
                        }
                        try {
/* 163 */                   Entry entry = new Entry(snapshot.getSource(0));
/* 169 */                   Response response = entry.response(request, snapshot);
/* 171 */                   if (!entry.matches(request, response)) {
/* 172 */                       Util.closeQuietly(response.body());
                                return null;
                            }
/* 176 */                   return response;
                        } catch (IOException e) {
/* 165 */                   Util.closeQuietly(snapshot);
                            return null;
                        }
                    } catch (IOException e2) {
                        return null;
                    }
                }

/* 179 */       private CacheRequest put(Response response) throws IOException {
/* 180 */           String requestMethod = response.request().method();
/* 182 */           if (HttpMethod.invalidatesCache(response.request().method())) {
                        try {
/* 184 */                   remove(response.request());
                            return null;
                        } catch (IOException e) {
                            return null;
                        }
                    }
/* 190 */           if (!requestMethod.equals("GET") || OkHeaders.hasVaryAll(response)) {
                        return null;
                    }
/* 201 */           Entry entry = new Entry(response);
/* 202 */           DiskLruCache.Editor editor = null;
                    try {
/* 204 */               editor = this.cache.edit(urlToKey(response.request()));
/* 205 */               if (editor == null) {
                            return null;
                        }
/* 208 */               entry.writeTo(editor);
/* 209 */               return new CacheRequestImpl(editor);
                    } catch (IOException e2) {
/* 211 */               abortQuietly(editor);
                        return null;
                    }
                }

/* 216 */       private void remove(Request request) throws IOException {
/* 217 */           this.cache.remove(urlToKey(request));
                }

/* 220 */       private void update(Response cached, Response network) {
/* 221 */           Entry entry = new Entry(network);
/* 222 */           DiskLruCache.Snapshot snapshot = CacheResponseBody.access$500((CacheResponseBody) cached.body());
/* 223 */           DiskLruCache.Editor editor = null;
                    try {
/* 225 */               editor = snapshot.edit();
/* 226 */               if (editor != null) {
/* 227 */                   entry.writeTo(editor);
/* 228 */                   editor.commit();
                        }
                    } catch (IOException e) {
/* 231 */               abortQuietly(editor);
                    }
                }

/* 237 */       private void abortQuietly(DiskLruCache.Editor editor) {
/* 238 */           if (editor != null) {
                        try {
/* 239 */                   editor.abort();
                        } catch (IOException e) {
                        }
                    }
                }

/* 250 */       public void delete() throws IOException {
/* 251 */           this.cache.delete();
                }

/* 258 */       public void evictAll() throws IOException {
/* 259 */           this.cache.evictAll();
                }

/* 262 */       public synchronized int getWriteAbortCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.writeAbortCount;
                }

/* 266 */       public synchronized int getWriteSuccessCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.writeSuccessCount;
                }

/* 270 */       public long getSize() {
/* 271 */           return this.cache.size();
                }

/* 274 */       public long getMaxSize() {
/* 275 */           return this.cache.getMaxSize();
                }

/* 278 */       public void flush() throws IOException {
/* 279 */           this.cache.flush();
                }

/* 282 */       public void close() throws IOException {
/* 283 */           this.cache.close();
                }

/* 286 */       public File getDirectory() {
/* 287 */           return this.cache.getDirectory();
                }

/* 290 */       public boolean isClosed() {
/* 291 */           return this.cache.isClosed();
                }

/* 294 */       private synchronized void trackResponse(CacheStrategy cacheStrategy) {
                    try {
                        this.requestCount++;
/* 297 */               if (cacheStrategy.networkRequest != null) {
/* 299 */                   this.networkCount++;
                        } else if (cacheStrategy.cacheResponse != null) {
/* 303 */                   this.hitCount++;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 307 */       private synchronized void trackConditionalCacheHit() {
                    try {
                        this.hitCount++;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 311 */       public synchronized int getNetworkCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.networkCount;
                }

/* 315 */       public synchronized int getHitCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.hitCount;
                }

/* 319 */       public synchronized int getRequestCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.requestCount;
                }

/* 321 */       private final class CacheRequestImpl implements CacheRequest {
                    private Sink body;
                    private Sink cacheOut;
                    private boolean done;
                    private final DiskLruCache.Editor editor;

/* 322 */           static /* synthetic */ boolean access$600(CacheRequestImpl x0) {
/* 323 */               return x0.done;
                    }

/* 322 */           static /* synthetic */ boolean access$602(CacheRequestImpl x0, boolean x1) {
/* 323 */               x0.done = x1;
                        return x1;
                    }

/* 328 */           public CacheRequestImpl(DiskLruCache.Editor editor) throws IOException {
/* 330 */               this.editor = editor;
/* 331 */               this.cacheOut = editor.newSink(1);
/* 332 */               this.body = new C12551(this.cacheOut, Cache.this, editor);
                    }

                    /* JADX INFO: renamed from: com.squareup.okhttp.Cache$CacheRequestImpl$1 */
/* 330 */           class C12551 extends ForwardingSink {
                        final /* synthetic */ DiskLruCache.Editor val$editor;
                        final /* synthetic */ Cache val$this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 331 */               C12551(Sink x0, Cache cache, DiskLruCache.Editor editor) {
                            super(x0);
                            this.val$this$0 = cache;
                            this.val$editor = editor;
                        }

                        @Override // okio.ForwardingSink, okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 333 */               public void close() throws IOException {
                            synchronized (Cache.this) {
                                try {
/* 335 */                           if (!CacheRequestImpl.access$600(CacheRequestImpl.this)) {
/* 338 */                               CacheRequestImpl.access$602(CacheRequestImpl.this, true);
/* 339 */                               Cache.access$708(Cache.this);
/* 341 */                               super.close();
/* 342 */                               this.val$editor.commit();
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override // com.squareup.okhttp.internal.http.CacheRequest
/* 347 */           public void abort() {
                        synchronized (Cache.this) {
                            try {
/* 349 */                       if (!this.done) {
/* 352 */                           this.done = true;
/* 353 */                           Cache.access$808(Cache.this);
/* 355 */                           Util.closeQuietly(this.cacheOut);
                                    try {
/* 357 */                               this.editor.abort();
                                    } catch (IOException e) {
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // com.squareup.okhttp.internal.http.CacheRequest
/* 362 */           public Sink body() {
/* 363 */               return this.body;
                    }
                }

/* 424 */       private static final class Entry {
                    private final int code;
                    private final Handshake handshake;
                    private final String message;
                    private final Protocol protocol;
                    private final String requestMethod;
                    private final Headers responseHeaders;
                    private final String url;
                    private final Headers varyHeaders;

/* 425 */           public Entry(Source in) throws IOException {
                        try {
/* 428 */                   BufferedSource source = Okio.buffer(in);
/* 429 */                   this.url = source.readUtf8LineStrict();
/* 430 */                   this.requestMethod = source.readUtf8LineStrict();
/* 431 */                   Headers.Builder varyHeadersBuilder = new Headers.Builder();
/* 432 */                   int varyRequestHeaderLineCount = Cache.access$900(source);
                            for (int i = 0; i < varyRequestHeaderLineCount; i++) {
/* 434 */                       varyHeadersBuilder.addLine(source.readUtf8LineStrict());
                            }
/* 436 */                   this.varyHeaders = varyHeadersBuilder.build();
/* 438 */                   StatusLine statusLine = StatusLine.parse(source.readUtf8LineStrict());
/* 439 */                   this.protocol = statusLine.protocol;
/* 440 */                   this.code = statusLine.code;
/* 441 */                   this.message = statusLine.message;
/* 442 */                   Headers.Builder responseHeadersBuilder = new Headers.Builder();
/* 443 */                   int responseHeaderLineCount = Cache.access$900(source);
                            for (int i2 = 0; i2 < responseHeaderLineCount; i2++) {
/* 445 */                       responseHeadersBuilder.addLine(source.readUtf8LineStrict());
                            }
/* 447 */                   this.responseHeaders = responseHeadersBuilder.build();
/* 449 */                   if (isHttps()) {
/* 450 */                       String blank = source.readUtf8LineStrict();
/* 451 */                       if (blank.length() > 0) {
/* 452 */                           throw new IOException("expected \"\" but was \"" + blank + "\"");
                                }
/* 454 */                       String cipherSuite = source.readUtf8LineStrict();
/* 455 */                       List<Certificate> peerCertificates = readCertificateList(source);
/* 456 */                       List<Certificate> localCertificates = readCertificateList(source);
/* 457 */                       this.handshake = Handshake.get(cipherSuite, peerCertificates, localCertificates);
                            } else {
/* 459 */                       this.handshake = null;
                            }
/* 462 */                   in.close();
                        } catch (Throwable th) {
                            in.close();
                            throw th;
                        }
                    }

/* 465 */           public Entry(Response response) {
/* 467 */               this.url = response.request().urlString();
/* 468 */               this.varyHeaders = OkHeaders.varyHeaders(response);
/* 469 */               this.requestMethod = response.request().method();
/* 470 */               this.protocol = response.protocol();
/* 471 */               this.code = response.code();
/* 472 */               this.message = response.message();
/* 473 */               this.responseHeaders = response.headers();
/* 474 */               this.handshake = response.handshake();
                    }

/* 477 */           public void writeTo(DiskLruCache.Editor editor) throws IOException {
/* 478 */               BufferedSink sink = Okio.buffer(editor.newSink(0));
/* 480 */               sink.writeUtf8(this.url);
/* 481 */               sink.writeByte(10);
/* 482 */               sink.writeUtf8(this.requestMethod);
/* 483 */               sink.writeByte(10);
/* 484 */               sink.writeUtf8(Integer.toString(this.varyHeaders.size()));
/* 485 */               sink.writeByte(10);
                        for (int i = 0; i < this.varyHeaders.size(); i++) {
/* 487 */                   sink.writeUtf8(this.varyHeaders.name(i));
/* 488 */                   sink.writeUtf8(": ");
/* 489 */                   sink.writeUtf8(this.varyHeaders.value(i));
/* 490 */                   sink.writeByte(10);
                        }
/* 493 */               sink.writeUtf8(new StatusLine(this.protocol, this.code, this.message).toString());
/* 494 */               sink.writeByte(10);
/* 495 */               sink.writeUtf8(Integer.toString(this.responseHeaders.size()));
/* 496 */               sink.writeByte(10);
                        for (int i2 = 0; i2 < this.responseHeaders.size(); i2++) {
/* 498 */                   sink.writeUtf8(this.responseHeaders.name(i2));
/* 499 */                   sink.writeUtf8(": ");
/* 500 */                   sink.writeUtf8(this.responseHeaders.value(i2));
/* 501 */                   sink.writeByte(10);
                        }
/* 504 */               if (isHttps()) {
/* 505 */                   sink.writeByte(10);
/* 506 */                   sink.writeUtf8(this.handshake.cipherSuite());
/* 507 */                   sink.writeByte(10);
/* 508 */                   writeCertArray(sink, this.handshake.peerCertificates());
/* 509 */                   writeCertArray(sink, this.handshake.localCertificates());
                        }
/* 511 */               sink.close();
                    }

/* 514 */           private boolean isHttps() {
/* 515 */               return this.url.startsWith(VineAPI.SCHEME_HTTPS);
                    }

/* 518 */           private List<Certificate> readCertificateList(BufferedSource source) throws IOException {
/* 519 */               int length = Cache.access$900(source);
/* 520 */               if (length == -1) {
                            return Collections.emptyList();
                        }
                        try {
/* 523 */                   CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
/* 524 */                   List<Certificate> result = new ArrayList<>(length);
                            for (int i = 0; i < length; i++) {
/* 526 */                       String line = source.readUtf8LineStrict();
/* 527 */                       byte[] bytes = ByteString.decodeBase64(line).toByteArray();
/* 528 */                       result.add(certificateFactory.generateCertificate(new ByteArrayInputStream(bytes)));
                            }
/* 530 */                   return result;
                        } catch (CertificateException e) {
/* 532 */                   throw new IOException(e.getMessage());
                        }
                    }

/* 538 */           private void writeCertArray(BufferedSink sink, List<Certificate> certificates) throws IOException {
                        try {
/* 539 */                   sink.writeUtf8(Integer.toString(certificates.size()));
/* 540 */                   sink.writeByte(10);
                            int size = certificates.size();
                            for (int i = 0; i < size; i++) {
/* 542 */                       byte[] bytes = certificates.get(i).getEncoded();
/* 543 */                       String line = ByteString.m2468of(bytes).base64();
/* 544 */                       sink.writeUtf8(line);
/* 545 */                       sink.writeByte(10);
                            }
                        } catch (CertificateEncodingException e) {
/* 548 */                   throw new IOException(e.getMessage());
                        }
                    }

/* 552 */           public boolean matches(Request request, Response response) {
                        return this.url.equals(request.urlString()) && this.requestMethod.equals(request.method()) && OkHeaders.varyMatches(response, this.varyHeaders, request);
                    }

/* 558 */           public Response response(Request request, DiskLruCache.Snapshot snapshot) {
/* 559 */               String contentType = this.responseHeaders.get("Content-Type");
/* 560 */               String contentLength = this.responseHeaders.get("Content-Length");
/* 561 */               Request cacheRequest = new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build();
/* 566 */               return new Response.Builder().request(cacheRequest).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new CacheResponseBody(snapshot, contentType, contentLength)).handshake(this.handshake).build();
                    }
                }

/* 578 */       private static int readInt(BufferedSource source) throws IOException {
/* 579 */           String line = source.readUtf8LineStrict();
                    try {
/* 581 */               return Integer.parseInt(line);
                    } catch (NumberFormatException e) {
/* 583 */               throw new IOException("Expected an integer but was \"" + line + "\"");
                    }
                }

/* 585 */       private static class CacheResponseBody extends ResponseBody {
                    private final BufferedSource bodySource;
                    private final String contentLength;
                    private final String contentType;
                    private final DiskLruCache.Snapshot snapshot;

/* 586 */           static /* synthetic */ DiskLruCache.Snapshot access$500(CacheResponseBody x0) {
/* 587 */               return x0.snapshot;
                    }

/* 593 */           public CacheResponseBody(DiskLruCache.Snapshot snapshot, String contentType, String contentLength) {
/* 595 */               this.snapshot = snapshot;
/* 596 */               this.contentType = contentType;
/* 597 */               this.contentLength = contentLength;
/* 599 */               Source source = snapshot.getSource(1);
/* 600 */               this.bodySource = Okio.buffer(new C12561(source, snapshot));
                    }

                    /* JADX INFO: renamed from: com.squareup.okhttp.Cache$CacheResponseBody$1 */
/* 598 */           class C12561 extends ForwardingSource {
                        final /* synthetic */ DiskLruCache.Snapshot val$snapshot;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 599 */               C12561(Source x0, DiskLruCache.Snapshot snapshot) {
                            super(x0);
                            this.val$snapshot = snapshot;
                        }

                        @Override // okio.ForwardingSource, okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 601 */               public void close() throws IOException {
/* 602 */                   this.val$snapshot.close();
/* 603 */                   super.close();
                        }
                    }

                    @Override // com.squareup.okhttp.ResponseBody
/* 608 */           public MediaType contentType() {
/* 609 */               if (this.contentType != null) {
                            return MediaType.parse(this.contentType);
                        }
                        return null;
                    }

                    @Override // com.squareup.okhttp.ResponseBody
/* 613 */           public long contentLength() {
                        try {
/* 614 */                   if (this.contentLength != null) {
                                return Long.parseLong(this.contentLength);
                            }
                            return -1L;
                        } catch (NumberFormatException e) {
                            return -1L;
                        }
                    }

                    @Override // com.squareup.okhttp.ResponseBody
/* 620 */           public BufferedSource source() {
/* 621 */               return this.bodySource;
                    }
                }
            }
