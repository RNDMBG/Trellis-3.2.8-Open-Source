            package com.flurry.sdk;

            import android.annotation.TargetApi;
            import android.os.Build;
            import com.google.android.exoplayer.DefaultLoadControl;
            import java.io.BufferedInputStream;
            import java.io.BufferedOutputStream;
            import java.io.Closeable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.net.HttpURLConnection;
            import java.net.URL;
            import java.security.SecureRandom;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Map;
            import javax.net.ssl.HostnameVerifier;
            import javax.net.ssl.HttpsURLConnection;
            import javax.net.ssl.SSLContext;
            import javax.net.ssl.TrustManager;
            import org.apache.http.Header;
            import org.apache.http.HeaderElement;
            import org.apache.http.HttpEntity;
            import org.apache.http.HttpResponse;
            import org.apache.http.client.HttpClient;
            import org.apache.http.client.methods.HttpDelete;
            import org.apache.http.client.methods.HttpGet;
            import org.apache.http.client.methods.HttpHead;
            import org.apache.http.client.methods.HttpPost;
            import org.apache.http.client.methods.HttpPut;
            import org.apache.http.client.methods.HttpRequestBase;
            import org.apache.http.entity.AbstractHttpEntity;
            import org.apache.http.params.BasicHttpParams;
            import org.apache.http.params.HttpConnectionParams;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ek */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    public class C0826ek extends AbstractC0845fd {

                /* JADX INFO: renamed from: a */
                private static final String f788a = C0826ek.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private static SSLContext f789b;

                /* JADX INFO: renamed from: c */
                private static HostnameVerifier f790c;

                /* JADX INFO: renamed from: d */
                private String f791d;

                /* JADX INFO: renamed from: e */
                private a f792e;

                /* JADX INFO: renamed from: l */
                private c f797l;

                /* JADX INFO: renamed from: m */
                private HttpURLConnection f798m;

                /* JADX INFO: renamed from: n */
                private HttpClient f799n;

                /* JADX INFO: renamed from: o */
                private boolean f800o;

                /* JADX INFO: renamed from: p */
                private boolean f801p;

                /* JADX INFO: renamed from: q */
                private Exception f802q;

                /* JADX INFO: renamed from: f */
                private int f793f = 10000;

                /* JADX INFO: renamed from: i */
                private int f794i = DefaultLoadControl.DEFAULT_LOW_WATERMARK_MS;

                /* JADX INFO: renamed from: j */
                private boolean f795j = true;

                /* JADX INFO: renamed from: k */
                private final C0805dp<String, String> f796k = new C0805dp<>();

                /* JADX INFO: renamed from: r */
                private int f803r = -1;

                /* JADX INFO: renamed from: s */
                private final C0805dp<String, String> f804s = new C0805dp<>();

                /* JADX INFO: renamed from: t */
                private final Object f805t = new Object();

                /* JADX INFO: renamed from: com.flurry.sdk.ek$c */
                public interface c {
                    /* JADX INFO: renamed from: a */
                    void mo853a(C0826ek c0826ek);

                    /* JADX INFO: renamed from: a */
                    void mo854a(C0826ek c0826ek, InputStream inputStream) throws Exception;

                    /* JADX INFO: renamed from: a */
                    void mo855a(C0826ek c0826ek, OutputStream outputStream) throws Exception;
                }

                /* JADX INFO: renamed from: a */
/* 49 */        static /* synthetic */ HttpURLConnection m857a(C0826ek c0826ek) {
/* 50 */            return c0826ek.f798m;
                }

                /* JADX INFO: renamed from: a */
/* 49 */        static /* synthetic */ void m858a(C0826ek c0826ek, OutputStream outputStream) throws Exception {
/* 50 */            c0826ek.m860a(outputStream);
                }

                /* JADX INFO: renamed from: b */
/* 49 */        static /* synthetic */ HttpClient m861b(C0826ek c0826ek) {
/* 50 */            return c0826ek.f799n;
                }

                /* JADX INFO: renamed from: com.flurry.sdk.ek$b */
/* 73 */        public static class b implements c {
                    @Override // com.flurry.sdk.C0826ek.c
                    /* JADX INFO: renamed from: a */
/* 74 */            public void mo855a(C0826ek c0826ek, OutputStream outputStream) throws Exception {
                    }

                    @Override // com.flurry.sdk.C0826ek.c
                    /* JADX INFO: renamed from: a */
/* 78 */            public void mo854a(C0826ek c0826ek, InputStream inputStream) throws Exception {
                    }

                    @Override // com.flurry.sdk.C0826ek.c
                    /* JADX INFO: renamed from: a */
/* 82 */            public void mo853a(C0826ek c0826ek) {
                    }
                }

                /* JADX INFO: renamed from: m */
/* 86 */        private static synchronized SSLContext m862m() {
                    SSLContext sSLContext;
                    try {
                        if (f789b != null) {
/* 88 */                    sSLContext = f789b;
                        } else {
                            try {
/* 93 */                        TrustManager[] trustManagerArr = {new C0822eg(null)};
/* 98 */                        f789b = SSLContext.getInstance("TLS");
/* 99 */                        f789b.init(null, trustManagerArr, new SecureRandom());
                            } catch (Exception e) {
/* 101 */                       C0827el.m886a(3, f788a, "Exception creating SSL context", e);
                            }
/* 104 */                   sSLContext = f789b;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return sSLContext;
                }

                /* JADX INFO: renamed from: n */
/* 107 */       private static synchronized HostnameVerifier m863n() {
                    HostnameVerifier hostnameVerifier;
                    try {
                        if (f790c != null) {
/* 109 */                   hostnameVerifier = f790c;
                        } else {
/* 112 */                   f790c = new C0820ee();
/* 113 */                   hostnameVerifier = f790c;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return hostnameVerifier;
                }

                /* JADX INFO: renamed from: com.flurry.sdk.ek$a */
/* 123 */       public enum a {
                    kUnknown,
                    kGet,
                    kPost,
                    kPut,
                    kDelete,
                    kHead;

                    @Override // java.lang.Enum
/* 124 */           public String toString() {
                        switch (this) {
                            case kPost:
/* 127 */                       return "POST";
                            case kPut:
/* 130 */                       return "PUT";
                            case kDelete:
/* 133 */                       return "DELETE";
                            case kHead:
/* 136 */                       return "HEAD";
                            case kGet:
/* 139 */                       return "GET";
                            default:
/* 142 */                       return null;
                        }
                    }

                    /* JADX INFO: renamed from: a */
/* 146 */           public HttpRequestBase m882a(String str) {
                        switch (this) {
                            case kPost:
/* 149 */                       return new HttpPost(str);
                            case kPut:
/* 152 */                       return new HttpPut(str);
                            case kDelete:
/* 155 */                       return new HttpDelete(str);
                            case kHead:
/* 158 */                       return new HttpHead(str);
                            case kGet:
/* 161 */                       return new HttpGet(str);
                            default:
/* 164 */                       return null;
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 197 */       public void m871a(String str) {
/* 198 */           this.f791d = str;
                }

                /* JADX INFO: renamed from: a */
/* 205 */       public void m869a(a aVar) {
/* 206 */           this.f792e = aVar;
                }

                /* JADX INFO: renamed from: a */
/* 220 */       public void m873a(boolean z) {
/* 221 */           this.f795j = z;
                }

                /* JADX INFO: renamed from: a */
/* 223 */       public void m872a(String str, String str2) {
/* 224 */           this.f796k.m754a(str, str2);
                }

                /* JADX INFO: renamed from: a */
/* 227 */       public void m870a(c cVar) {
/* 228 */           this.f797l = cVar;
                }

                /* JADX INFO: renamed from: b */
/* 231 */       public boolean m875b() {
                    boolean z;
                    synchronized (this.f805t) {
                        try {
/* 233 */                   z = this.f801p;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

                /* JADX INFO: renamed from: c */
/* 237 */       public boolean m876c() {
                    return !m879f() && m877d();
                }

                /* JADX INFO: renamed from: d */
/* 241 */       public boolean m877d() {
                    return this.f803r >= 200 && this.f803r < 400;
                }

                /* JADX INFO: renamed from: e */
/* 245 */       public int m878e() {
/* 246 */           return this.f803r;
                }

                /* JADX INFO: renamed from: f */
/* 249 */       public boolean m879f() {
                    return this.f802q != null;
                }

                /* JADX INFO: renamed from: b */
/* 261 */       public List<String> m874b(String str) {
/* 262 */           if (str == null) {
/* 263 */               return null;
                    }
                    return this.f804s.m751a(str);
                }

                /* JADX INFO: renamed from: g */
/* 269 */       public void m880g() {
                    synchronized (this.f805t) {
                        try {
/* 271 */                   this.f801p = true;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 274 */           m868s();
                }

                @Override // com.flurry.sdk.AbstractRunnableC0844fc
                /* JADX INFO: renamed from: a */
/* 279 */       public void mo576a() {
                    try {
                        try {
/* 280 */                   if (this.f791d == null) {
/* 309 */                       m866q();
/* 311 */                       return;
                            }
/* 284 */                   if (!C0834es.m907a().m912c()) {
/* 285 */                       C0827el.m885a(3, f788a, "Network not available, aborting http request: " + this.f791d);
/* 309 */                       m866q();
/* 311 */                       return;
                            }
/* 290 */                   if (this.f792e == null || a.kUnknown.equals(this.f792e)) {
/* 291 */                       this.f792e = a.kGet;
                            }
/* 296 */                   if (Build.VERSION.SDK_INT >= 9) {
/* 297 */                       m864o();
                            } else {
/* 299 */                       m865p();
                            }
/* 302 */                   C0827el.m885a(4, f788a, "HTTP status: " + this.f803r + " for url: " + this.f791d);
/* 309 */                   m866q();
                        } catch (Exception e) {
/* 304 */                   C0827el.m885a(4, f788a, "HTTP status: " + this.f803r + " for url: " + this.f791d);
/* 305 */                   C0827el.m886a(3, f788a, "Exception during http request: " + this.f791d, e);
/* 306 */                   this.f802q = e;
/* 309 */                   m866q();
                        }
                    } catch (Throwable th) {
                        m866q();
                        throw th;
                    }
                }

                @Override // com.flurry.sdk.AbstractC0845fd
                /* JADX INFO: renamed from: h */
/* 314 */       public void mo881h() {
/* 315 */           m880g();
                }

                /* JADX INFO: renamed from: o */
/* 319 */       private void m864o() throws Exception {
                    BufferedOutputStream bufferedOutputStream;
                    InputStream inputStream;
                    Closeable closeable = null;
/* 320 */           if (this.f801p) {
/* 420 */               return;
                    }
                    try {
/* 328 */               this.f798m = (HttpURLConnection) new URL(this.f791d).openConnection();
/* 329 */               this.f798m.setConnectTimeout(this.f793f);
/* 330 */               this.f798m.setReadTimeout(this.f794i);
/* 331 */               this.f798m.setRequestMethod(this.f792e.toString());
/* 332 */               this.f798m.setInstanceFollowRedirects(this.f795j);
/* 333 */               this.f798m.setDoOutput(a.kPost.equals(this.f792e));
/* 334 */               this.f798m.setDoInput(true);
/* 337 */               if (C0827el.m901d() && (this.f798m instanceof HttpsURLConnection)) {
/* 338 */                   HttpsURLConnection httpsURLConnection = (HttpsURLConnection) this.f798m;
/* 342 */                   httpsURLConnection.setHostnameVerifier(m863n());
/* 343 */                   httpsURLConnection.setSSLSocketFactory(m862m().getSocketFactory());
                        }
                        for (Map.Entry<String, String> entry : this.f796k.m755b()) {
/* 349 */                   this.f798m.addRequestProperty(entry.getKey(), entry.getValue());
                        }
/* 353 */               if (!a.kGet.equals(this.f792e) && !a.kPost.equals(this.f792e)) {
/* 354 */                   this.f798m.setRequestProperty("Accept-Encoding", "");
                        }
/* 358 */               if (this.f801p) {
/* 418 */                   m867r();
/* 420 */                   return;
                        }
/* 363 */               if (a.kPost.equals(this.f792e)) {
                            try {
/* 369 */                       OutputStream outputStream = this.f798m.getOutputStream();
                                try {
/* 370 */                           bufferedOutputStream = new BufferedOutputStream(outputStream);
                                    try {
/* 373 */                               m860a(bufferedOutputStream);
/* 376 */                               C0843fb.m962a(bufferedOutputStream);
/* 377 */                               C0843fb.m962a(outputStream);
                                    } catch (Throwable th) {
                                        th = th;
                                        closeable = outputStream;
                                        C0843fb.m962a(bufferedOutputStream);
/* 377 */                               C0843fb.m962a(closeable);
                                        throw th;
                                    }
                                } catch (Throwable th2) {
/* 376 */                           th = th2;
                                    bufferedOutputStream = null;
                                    closeable = outputStream;
                                }
                            } catch (Throwable th3) {
/* 376 */                       th = th3;
                                bufferedOutputStream = null;
                            }
                        }
/* 382 */               this.f803r = this.f798m.getResponseCode();
                        for (Map.Entry<String, List<String>> entry2 : this.f798m.getHeaderFields().entrySet()) {
/* 388 */                   Iterator<String> it = entry2.getValue().iterator();
                            while (it.hasNext()) {
/* 389 */                       this.f804s.m754a(entry2.getKey(), it.next());
                            }
                        }
/* 394 */               if (!a.kGet.equals(this.f792e) && !a.kPost.equals(this.f792e)) {
/* 418 */                   m867r();
/* 420 */                   return;
                        }
/* 399 */               if (this.f801p) {
/* 418 */                   m867r();
/* 420 */                   return;
                        }
                        try {
/* 408 */                   InputStream inputStream2 = this.f798m.getInputStream();
                            try {
/* 409 */                       BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream2);
                                try {
/* 412 */                           m859a(bufferedInputStream);
/* 414 */                           C0843fb.m962a(bufferedInputStream);
/* 415 */                           C0843fb.m962a(inputStream2);
/* 418 */                           m867r();
                                } catch (Throwable th4) {
                                    th = th4;
                                    closeable = bufferedInputStream;
                                    inputStream = inputStream2;
                                    C0843fb.m962a(closeable);
/* 415 */                           C0843fb.m962a(inputStream);
                                    throw th;
                                }
                            } catch (Throwable th5) {
/* 414 */                       th = th5;
                                inputStream = inputStream2;
                            }
                        } catch (Throwable th6) {
/* 414 */                   th = th6;
                            inputStream = null;
                        }
                    } catch (Throwable th7) {
                        m867r();
                        throw th7;
                    }
                }

                /* JADX INFO: renamed from: p */
/* 423 */       private void m865p() throws Exception {
                    BufferedInputStream bufferedInputStream;
                    InputStream inputStream = null;
/* 424 */           if (!this.f801p) {
/* 429 */               HttpRequestBase httpRequestBaseM882a = this.f792e.m882a(this.f791d);
                        for (Map.Entry<String, String> entry : this.f796k.m755b()) {
/* 434 */                   httpRequestBaseM882a.setHeader(entry.getKey(), entry.getValue());
                        }
/* 438 */               if (!a.kGet.equals(this.f792e) && !a.kPost.equals(this.f792e)) {
/* 439 */                   httpRequestBaseM882a.removeHeaders("Accept-Encoding");
                        }
/* 443 */               if (a.kPost.equals(this.f792e)) {
/* 444 */                   ((HttpPost) httpRequestBaseM882a).setEntity(new AnonymousClass1());
                        }
                        try {
/* 488 */                   BasicHttpParams basicHttpParams = new BasicHttpParams();
/* 489 */                   HttpConnectionParams.setConnectionTimeout(basicHttpParams, this.f793f);
/* 490 */                   HttpConnectionParams.setSoTimeout(basicHttpParams, this.f794i);
/* 491 */                   basicHttpParams.setParameter("http.protocol.handle-redirects", Boolean.valueOf(this.f795j));
/* 494 */                   this.f799n = C0823eh.m840a(basicHttpParams);
/* 495 */                   HttpResponse httpResponseExecute = this.f799n.execute(httpRequestBaseM882a);
/* 498 */                   if (this.f801p) {
/* 499 */                       throw new Exception("Request cancelled");
                            }
/* 503 */                   if (httpResponseExecute != null) {
/* 505 */                       this.f803r = httpResponseExecute.getStatusLine().getStatusCode();
/* 508 */                       Header[] allHeaders = httpResponseExecute.getAllHeaders();
/* 509 */                       if (allHeaders != null) {
                                    for (Header header : allHeaders) {
/* 511 */                               HeaderElement[] elements = header.getElements();
                                        for (HeaderElement headerElement : elements) {
/* 512 */                                   this.f804s.m754a(headerElement.getName(), headerElement.getValue());
                                        }
                                    }
                                }
/* 518 */                       if (a.kGet.equals(this.f792e) || a.kPost.equals(this.f792e)) {
/* 523 */                           if (this.f801p) {
/* 524 */                               throw new Exception("Request cancelled");
                                    }
/* 528 */                           HttpEntity entity = httpResponseExecute.getEntity();
/* 529 */                           if (entity != null) {
                                        try {
/* 535 */                                   InputStream content = entity.getContent();
                                            try {
/* 536 */                                       bufferedInputStream = new BufferedInputStream(content);
                                                try {
/* 539 */                                           m859a(bufferedInputStream);
/* 541 */                                           C0843fb.m962a(bufferedInputStream);
/* 542 */                                           C0843fb.m962a(content);
                                                } catch (Throwable th) {
                                                    th = th;
                                                    inputStream = content;
                                                    C0843fb.m962a(bufferedInputStream);
/* 542 */                                           C0843fb.m962a(inputStream);
                                                    throw th;
                                                }
                                            } catch (Throwable th2) {
/* 541 */                                       th = th2;
                                                bufferedInputStream = null;
                                                inputStream = content;
                                            }
                                        } catch (Throwable th3) {
/* 541 */                                   th = th3;
                                            bufferedInputStream = null;
                                        }
                                    }
                                } else {
/* 547 */                           m867r();
/* 549 */                           return;
                                }
                            }
/* 547 */                   m867r();
                        } catch (Throwable th4) {
                            m867r();
                            throw th4;
                        }
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.ek$1, reason: invalid class name */
/* 442 */       class AnonymousClass1 extends AbstractHttpEntity {
/* 443 */           AnonymousClass1() {
                    }

                    @Override // org.apache.http.HttpEntity
/* 445 */           public boolean isRepeatable() {
/* 446 */               return false;
                    }

                    @Override // org.apache.http.HttpEntity
/* 449 */           public long getContentLength() {
/* 450 */               return -1L;
                    }

                    @Override // org.apache.http.HttpEntity
/* 453 */           public boolean isStreaming() {
/* 454 */               return false;
                    }

                    @Override // org.apache.http.HttpEntity
/* 457 */           public InputStream getContent() throws IOException {
/* 458 */               throw new UnsupportedOperationException();
                    }

                    @Override // org.apache.http.HttpEntity
                    @TargetApi(9)
/* 463 */           public void writeTo(OutputStream outputStream) throws Throwable {
                        try {
                            try {
/* 466 */                       BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                                try {
/* 469 */                           C0826ek.m858a(C0826ek.this, bufferedOutputStream);
/* 480 */                           C0843fb.m962a(bufferedOutputStream);
                                } catch (IOException e) {
                                } catch (Exception e2) {
/* 472 */                           e = e2;
/* 474 */                           if (Build.VERSION.SDK_INT >= 9) {
/* 475 */                               throw new IOException(e);
                                    }
/* 477 */                           throw new IOException(e.toString());
                                }
                            } catch (Throwable th) {
/* 480 */                       th = th;
/* 464 */                       C0843fb.m962a((Closeable) null);
                                throw th;
                            }
                        } catch (IOException e3) {
/* 471 */                   throw e3;
                        } catch (Exception e4) {
/* 472 */                   e = e4;
                        } catch (Throwable th2) {
/* 480 */                   th = th2;
/* 464 */                   C0843fb.m962a((Closeable) null);
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 551 */       private void m860a(OutputStream outputStream) throws Exception {
/* 552 */           if (this.f797l != null && !m875b() && outputStream != null) {
/* 564 */               this.f797l.mo855a(this, outputStream);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 567 */       private void m859a(InputStream inputStream) throws Exception {
/* 568 */           if (this.f797l != null && !m875b() && inputStream != null) {
/* 580 */               this.f797l.mo854a(this, inputStream);
                    }
                }

                /* JADX INFO: renamed from: q */
/* 583 */       private void m866q() {
/* 584 */           if (this.f797l != null && !m875b()) {
/* 592 */               this.f797l.mo853a(this);
                    }
                }

                /* JADX INFO: renamed from: r */
/* 595 */       private void m867r() {
/* 596 */           if (!this.f800o) {
/* 600 */               this.f800o = true;
/* 602 */               if (this.f798m != null) {
/* 603 */                   this.f798m.disconnect();
                        }
/* 606 */               if (this.f799n != null) {
/* 607 */                   this.f799n.getConnectionManager().shutdown();
                        }
                    }
                }

                /* JADX INFO: renamed from: s */
/* 611 */       private void m868s() {
/* 612 */           if (!this.f800o) {
/* 616 */               this.f800o = true;
/* 619 */               if (this.f798m != null || this.f799n != null) {
/* 631 */                   new AnonymousClass2().start();
                        }
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.ek$2, reason: invalid class name */
/* 618 */       class AnonymousClass2 extends Thread {
/* 619 */           AnonymousClass2() {
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 621 */           public void run() {
/* 622 */               if (C0826ek.m857a(C0826ek.this) != null) {
/* 623 */                   C0826ek.m857a(C0826ek.this).disconnect();
                        }
/* 626 */               if (C0826ek.m861b(C0826ek.this) != null) {
/* 627 */                   C0826ek.m861b(C0826ek.this).getConnectionManager().shutdown();
                        }
                    }
                }
            }
