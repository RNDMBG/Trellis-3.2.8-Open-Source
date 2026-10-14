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
            
            
   public final class Cache {
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

       static  CacheRequest access$000(Cache x0, Response x1) throws IOException {
           return x0.put(x1);
                }

       static  void access$100(Cache x0, Request x1) throws IOException {
           x0.remove(x1);
                }

       static  void access$200(Cache x0, Response x1, Response x2) {
           x0.update(x1, x2);
                }

       static  void access$300(Cache x0) {
           x0.trackConditionalCacheHit();
                }

       static  void access$400(Cache x0, CacheStrategy x1) {
           x0.trackResponse(x1);
                }

       static  int access$708(Cache x0) {
           int i = x0.writeSuccessCount;
                    x0.writeSuccessCount = i + 1;
                    return i;
                }

       static  int access$808(Cache x0) {
           int i = x0.writeAbortCount;
                    x0.writeAbortCount = i + 1;
                    return i;
                }

       static  int access$900(BufferedSource x0) throws IOException {
           return readInt(x0);
                }

                
       class C12541 implements InternalCache {
           C12541() {
                    }

                    @Override 
           public Response get(Request request) throws IOException {
               return Cache.this.get(request);
                    }

                    @Override 
           public CacheRequest put(Response response) throws IOException {
               return Cache.access$000(Cache.this, response);
                    }

                    @Override 
           public void remove(Request request) throws IOException {
               Cache.access$100(Cache.this, request);
                    }

                    @Override 
           public void update(Response cached, Response network) throws IOException {
               Cache.access$200(Cache.this, cached, network);
                    }

                    @Override 
           public void trackConditionalCacheHit() {
               Cache.access$300(Cache.this);
                    }

                    @Override 
           public void trackResponse(CacheStrategy cacheStrategy) {
               Cache.access$400(Cache.this, cacheStrategy);
                    }
                }

       public Cache(File directory, long maxSize) throws IOException {
           this.cache = DiskLruCache.open(directory, VERSION, 2, maxSize);
                }

       private static String urlToKey(Request request) {
           return Util.hash(request.urlString());
                }

       Response get(Request request) {
           String key = urlToKey(request);
                    try {
               DiskLruCache.Snapshot snapshot = this.cache.get(key);
               if (snapshot == null) {
                            return null;
                        }
                        try {
                   Entry entry = new Entry(snapshot.getSource(0));
                   Response response = entry.response(request, snapshot);
                   if (!entry.matches(request, response)) {
                       Util.closeQuietly(response.body());
                                return null;
                            }
                   return response;
                        } catch (IOException e) {
                   Util.closeQuietly(snapshot);
                            return null;
                        }
                    } catch (IOException e2) {
                        return null;
                    }
                }

       private CacheRequest put(Response response) throws IOException {
           String requestMethod = response.request().method();
           if (HttpMethod.invalidatesCache(response.request().method())) {
                        try {
                   remove(response.request());
                            return null;
                        } catch (IOException e) {
                            return null;
                        }
                    }
           if (!requestMethod.equals("GET") || OkHeaders.hasVaryAll(response)) {
                        return null;
                    }
           Entry entry = new Entry(response);
           DiskLruCache.Editor editor = null;
                    try {
               editor = this.cache.edit(urlToKey(response.request()));
               if (editor == null) {
                            return null;
                        }
               entry.writeTo(editor);
               return new CacheRequestImpl(editor);
                    } catch (IOException e2) {
               abortQuietly(editor);
                        return null;
                    }
                }

       private void remove(Request request) throws IOException {
           this.cache.remove(urlToKey(request));
                }

       private void update(Response cached, Response network) {
           Entry entry = new Entry(network);
           DiskLruCache.Snapshot snapshot = CacheResponseBody.access$500((CacheResponseBody) cached.body());
           DiskLruCache.Editor editor = null;
                    try {
               editor = snapshot.edit();
               if (editor != null) {
                   entry.writeTo(editor);
                   editor.commit();
                        }
                    } catch (IOException e) {
               abortQuietly(editor);
                    }
                }

       private void abortQuietly(DiskLruCache.Editor editor) {
           if (editor != null) {
                        try {
                   editor.abort();
                        } catch (IOException e) {
                        }
                    }
                }

       public void delete() throws IOException {
           this.cache.delete();
                }

       public void evictAll() throws IOException {
           this.cache.evictAll();
                }

       public synchronized int getWriteAbortCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.writeAbortCount;
                }

       public synchronized int getWriteSuccessCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.writeSuccessCount;
                }

       public long getSize() {
           return this.cache.size();
                }

       public long getMaxSize() {
           return this.cache.getMaxSize();
                }

       public void flush() throws IOException {
           this.cache.flush();
                }

       public void close() throws IOException {
           this.cache.close();
                }

       public File getDirectory() {
           return this.cache.getDirectory();
                }

       public boolean isClosed() {
           return this.cache.isClosed();
                }

       private synchronized void trackResponse(CacheStrategy cacheStrategy) {
                    try {
                        this.requestCount++;
               if (cacheStrategy.networkRequest != null) {
                   this.networkCount++;
                        } else if (cacheStrategy.cacheResponse != null) {
                   this.hitCount++;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private synchronized void trackConditionalCacheHit() {
                    try {
                        this.hitCount++;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       public synchronized int getNetworkCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.networkCount;
                }

       public synchronized int getHitCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.hitCount;
                }

       public synchronized int getRequestCount() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.requestCount;
                }

       private final class CacheRequestImpl implements CacheRequest {
                    private Sink body;
                    private Sink cacheOut;
                    private boolean done;
                    private final DiskLruCache.Editor editor;

           static  boolean access$600(CacheRequestImpl x0) {
               return x0.done;
                    }

           static  boolean access$602(CacheRequestImpl x0, boolean x1) {
               x0.done = x1;
                        return x1;
                    }

           public CacheRequestImpl(DiskLruCache.Editor editor) throws IOException {
               this.editor = editor;
               this.cacheOut = editor.newSink(1);
               this.body = new C12551(this.cacheOut, Cache.this, editor);
                    }

                    
           class C12551 extends ForwardingSink {
                        final  DiskLruCache.Editor val$editor;
                        final  Cache val$this$0;

                        
               C12551(Sink x0, Cache cache, DiskLruCache.Editor editor) {
                            super(x0);
                            this.val$this$0 = cache;
                            this.val$editor = editor;
                        }

                        @Override 
               public void close() throws IOException {
                            synchronized (Cache.this) {
                                try {
                           if (!CacheRequestImpl.access$600(CacheRequestImpl.this)) {
                               CacheRequestImpl.access$602(CacheRequestImpl.this, true);
                               Cache.access$708(Cache.this);
                               super.close();
                               this.val$editor.commit();
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }

                    @Override 
           public void abort() {
                        synchronized (Cache.this) {
                            try {
                       if (!this.done) {
                           this.done = true;
                           Cache.access$808(Cache.this);
                           Util.closeQuietly(this.cacheOut);
                                    try {
                               this.editor.abort();
                                    } catch (IOException e) {
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override 
           public Sink body() {
               return this.body;
                    }
                }

       private static final class Entry {
                    private final int code;
                    private final Handshake handshake;
                    private final String message;
                    private final Protocol protocol;
                    private final String requestMethod;
                    private final Headers responseHeaders;
                    private final String url;
                    private final Headers varyHeaders;

           public Entry(Source in) throws IOException {
                        try {
                   BufferedSource source = Okio.buffer(in);
                   this.url = source.readUtf8LineStrict();
                   this.requestMethod = source.readUtf8LineStrict();
                   Headers.Builder varyHeadersBuilder = new Headers.Builder();
                   int varyRequestHeaderLineCount = Cache.access$900(source);
                            for (int i = 0; i < varyRequestHeaderLineCount; i++) {
                       varyHeadersBuilder.addLine(source.readUtf8LineStrict());
                            }
                   this.varyHeaders = varyHeadersBuilder.build();
                   StatusLine statusLine = StatusLine.parse(source.readUtf8LineStrict());
                   this.protocol = statusLine.protocol;
                   this.code = statusLine.code;
                   this.message = statusLine.message;
                   Headers.Builder responseHeadersBuilder = new Headers.Builder();
                   int responseHeaderLineCount = Cache.access$900(source);
                            for (int i2 = 0; i2 < responseHeaderLineCount; i2++) {
                       responseHeadersBuilder.addLine(source.readUtf8LineStrict());
                            }
                   this.responseHeaders = responseHeadersBuilder.build();
                   if (isHttps()) {
                       String blank = source.readUtf8LineStrict();
                       if (blank.length() > 0) {
                           throw new IOException("expected \"\" but was \"" + blank + "\"");
                                }
                       String cipherSuite = source.readUtf8LineStrict();
                       List<Certificate> peerCertificates = readCertificateList(source);
                       List<Certificate> localCertificates = readCertificateList(source);
                       this.handshake = Handshake.get(cipherSuite, peerCertificates, localCertificates);
                            } else {
                       this.handshake = null;
                            }
                   in.close();
                        } catch (Throwable th) {
                            in.close();
                            throw th;
                        }
                    }

           public Entry(Response response) {
               this.url = response.request().urlString();
               this.varyHeaders = OkHeaders.varyHeaders(response);
               this.requestMethod = response.request().method();
               this.protocol = response.protocol();
               this.code = response.code();
               this.message = response.message();
               this.responseHeaders = response.headers();
               this.handshake = response.handshake();
                    }

           public void writeTo(DiskLruCache.Editor editor) throws IOException {
               BufferedSink sink = Okio.buffer(editor.newSink(0));
               sink.writeUtf8(this.url);
               sink.writeByte(10);
               sink.writeUtf8(this.requestMethod);
               sink.writeByte(10);
               sink.writeUtf8(Integer.toString(this.varyHeaders.size()));
               sink.writeByte(10);
                        for (int i = 0; i < this.varyHeaders.size(); i++) {
                   sink.writeUtf8(this.varyHeaders.name(i));
                   sink.writeUtf8(": ");
                   sink.writeUtf8(this.varyHeaders.value(i));
                   sink.writeByte(10);
                        }
               sink.writeUtf8(new StatusLine(this.protocol, this.code, this.message).toString());
               sink.writeByte(10);
               sink.writeUtf8(Integer.toString(this.responseHeaders.size()));
               sink.writeByte(10);
                        for (int i2 = 0; i2 < this.responseHeaders.size(); i2++) {
                   sink.writeUtf8(this.responseHeaders.name(i2));
                   sink.writeUtf8(": ");
                   sink.writeUtf8(this.responseHeaders.value(i2));
                   sink.writeByte(10);
                        }
               if (isHttps()) {
                   sink.writeByte(10);
                   sink.writeUtf8(this.handshake.cipherSuite());
                   sink.writeByte(10);
                   writeCertArray(sink, this.handshake.peerCertificates());
                   writeCertArray(sink, this.handshake.localCertificates());
                        }
               sink.close();
                    }

           private boolean isHttps() {
               return this.url.startsWith(VineAPI.SCHEME_HTTPS);
                    }

           private List<Certificate> readCertificateList(BufferedSource source) throws IOException {
               int length = Cache.access$900(source);
               if (length == -1) {
                            return Collections.emptyList();
                        }
                        try {
                   CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                   List<Certificate> result = new ArrayList<>(length);
                            for (int i = 0; i < length; i++) {
                       String line = source.readUtf8LineStrict();
                       byte[] bytes = ByteString.decodeBase64(line).toByteArray();
                       result.add(certificateFactory.generateCertificate(new ByteArrayInputStream(bytes)));
                            }
                   return result;
                        } catch (CertificateException e) {
                   throw new IOException(e.getMessage());
                        }
                    }

           private void writeCertArray(BufferedSink sink, List<Certificate> certificates) throws IOException {
                        try {
                   sink.writeUtf8(Integer.toString(certificates.size()));
                   sink.writeByte(10);
                            int size = certificates.size();
                            for (int i = 0; i < size; i++) {
                       byte[] bytes = certificates.get(i).getEncoded();
                       String line = ByteString.m2468of(bytes).base64();
                       sink.writeUtf8(line);
                       sink.writeByte(10);
                            }
                        } catch (CertificateEncodingException e) {
                   throw new IOException(e.getMessage());
                        }
                    }

           public boolean matches(Request request, Response response) {
                        return this.url.equals(request.urlString()) && this.requestMethod.equals(request.method()) && OkHeaders.varyMatches(response, this.varyHeaders, request);
                    }

           public Response response(Request request, DiskLruCache.Snapshot snapshot) {
               String contentType = this.responseHeaders.get("Content-Type");
               String contentLength = this.responseHeaders.get("Content-Length");
               Request cacheRequest = new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build();
               return new Response.Builder().request(cacheRequest).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new CacheResponseBody(snapshot, contentType, contentLength)).handshake(this.handshake).build();
                    }
                }

       private static int readInt(BufferedSource source) throws IOException {
           String line = source.readUtf8LineStrict();
                    try {
               return Integer.parseInt(line);
                    } catch (NumberFormatException e) {
               throw new IOException("Expected an integer but was \"" + line + "\"");
                    }
                }

       private static class CacheResponseBody extends ResponseBody {
                    private final BufferedSource bodySource;
                    private final String contentLength;
                    private final String contentType;
                    private final DiskLruCache.Snapshot snapshot;

           static  DiskLruCache.Snapshot access$500(CacheResponseBody x0) {
               return x0.snapshot;
                    }

           public CacheResponseBody(DiskLruCache.Snapshot snapshot, String contentType, String contentLength) {
               this.snapshot = snapshot;
               this.contentType = contentType;
               this.contentLength = contentLength;
               Source source = snapshot.getSource(1);
               this.bodySource = Okio.buffer(new C12561(source, snapshot));
                    }

                    
           class C12561 extends ForwardingSource {
                        final  DiskLruCache.Snapshot val$snapshot;

                        
               C12561(Source x0, DiskLruCache.Snapshot snapshot) {
                            super(x0);
                            this.val$snapshot = snapshot;
                        }

                        @Override 
               public void close() throws IOException {
                   this.val$snapshot.close();
                   super.close();
                        }
                    }

                    @Override 
           public MediaType contentType() {
               if (this.contentType != null) {
                            return MediaType.parse(this.contentType);
                        }
                        return null;
                    }

                    @Override 
           public long contentLength() {
                        try {
                   if (this.contentLength != null) {
                                return Long.parseLong(this.contentLength);
                            }
                            return -1L;
                        } catch (NumberFormatException e) {
                            return -1L;
                        }
                    }

                    @Override 
           public BufferedSource source() {
               return this.bodySource;
                    }
                }
            }
