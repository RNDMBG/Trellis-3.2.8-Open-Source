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
            
            
            
    public class C0826ek extends AbstractC0845fd {

                
                private static final String f788a = C0826ek.class.getSimpleName();

                
                private static SSLContext f789b;

                
                private static HostnameVerifier f790c;

                
                private String f791d;

                
                private a f792e;

                
                private c f797l;

                
                private HttpURLConnection f798m;

                
                private HttpClient f799n;

                
                private boolean f800o;

                
                private boolean f801p;

                
                private Exception f802q;

                
                private int f793f = 10000;

                
                private int f794i = DefaultLoadControl.DEFAULT_LOW_WATERMARK_MS;

                
                private boolean f795j = true;

                
                private final C0805dp<String, String> f796k = new C0805dp<>();

                
                private int f803r = -1;

                
                private final C0805dp<String, String> f804s = new C0805dp<>();

                
                private final Object f805t = new Object();

                
                public interface c {
                    
                    void mo853a(C0826ek c0826ek);

                    
                    void mo854a(C0826ek c0826ek, InputStream inputStream) throws Exception;

                    
                    void mo855a(C0826ek c0826ek, OutputStream outputStream) throws Exception;
                }

                
        static  HttpURLConnection m857a(C0826ek c0826ek) {
            return c0826ek.f798m;
                }

                
        static  void m858a(C0826ek c0826ek, OutputStream outputStream) throws Exception {
            c0826ek.m860a(outputStream);
                }

                
        static  HttpClient m861b(C0826ek c0826ek) {
            return c0826ek.f799n;
                }

                
        public static class b implements c {
                    @Override 
                    
            public void mo855a(C0826ek c0826ek, OutputStream outputStream) throws Exception {
                    }

                    @Override 
                    
            public void mo854a(C0826ek c0826ek, InputStream inputStream) throws Exception {
                    }

                    @Override 
                    
            public void mo853a(C0826ek c0826ek) {
                    }
                }

                
        private static synchronized SSLContext m862m() {
                    SSLContext sSLContext;
                    try {
                        if (f789b != null) {
                    sSLContext = f789b;
                        } else {
                            try {
                        TrustManager[] trustManagerArr = {new C0822eg(null)};
                        f789b = SSLContext.getInstance("TLS");
                        f789b.init(null, trustManagerArr, new SecureRandom());
                            } catch (Exception e) {
                       C0827el.m886a(3, f788a, "Exception creating SSL context", e);
                            }
                   sSLContext = f789b;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return sSLContext;
                }

                
       private static synchronized HostnameVerifier m863n() {
                    HostnameVerifier hostnameVerifier;
                    try {
                        if (f790c != null) {
                   hostnameVerifier = f790c;
                        } else {
                   f790c = new C0820ee();
                   hostnameVerifier = f790c;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return hostnameVerifier;
                }

                
       public enum a {
                    kUnknown,
                    kGet,
                    kPost,
                    kPut,
                    kDelete,
                    kHead;

                    @Override 
           public String toString() {
                        switch (this) {
                            case kPost:
                       return "POST";
                            case kPut:
                       return "PUT";
                            case kDelete:
                       return "DELETE";
                            case kHead:
                       return "HEAD";
                            case kGet:
                       return "GET";
                            default:
                       return null;
                        }
                    }

                    
           public HttpRequestBase m882a(String str) {
                        switch (this) {
                            case kPost:
                       return new HttpPost(str);
                            case kPut:
                       return new HttpPut(str);
                            case kDelete:
                       return new HttpDelete(str);
                            case kHead:
                       return new HttpHead(str);
                            case kGet:
                       return new HttpGet(str);
                            default:
                       return null;
                        }
                    }
                }

                
       public void m871a(String str) {
           this.f791d = str;
                }

                
       public void m869a(a aVar) {
           this.f792e = aVar;
                }

                
       public void m873a(boolean z) {
           this.f795j = z;
                }

                
       public void m872a(String str, String str2) {
           this.f796k.m754a(str, str2);
                }

                
       public void m870a(c cVar) {
           this.f797l = cVar;
                }

                
       public boolean m875b() {
                    boolean z;
                    synchronized (this.f805t) {
                        try {
                   z = this.f801p;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

                
       public boolean m876c() {
                    return !m879f() && m877d();
                }

                
       public boolean m877d() {
                    return this.f803r >= 200 && this.f803r < 400;
                }

                
       public int m878e() {
           return this.f803r;
                }

                
       public boolean m879f() {
                    return this.f802q != null;
                }

                
       public List<String> m874b(String str) {
           if (str == null) {
               return null;
                    }
                    return this.f804s.m751a(str);
                }

                
       public void m880g() {
                    synchronized (this.f805t) {
                        try {
                   this.f801p = true;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           m868s();
                }

                @Override 
                
       public void mo576a() {
                    try {
                        try {
                   if (this.f791d == null) {
                       m866q();
                       return;
                            }
                   if (!C0834es.m907a().m912c()) {
                       C0827el.m885a(3, f788a, "Network not available, aborting http request: " + this.f791d);
                       m866q();
                       return;
                            }
                   if (this.f792e == null || a.kUnknown.equals(this.f792e)) {
                       this.f792e = a.kGet;
                            }
                   if (Build.VERSION.SDK_INT >= 9) {
                       m864o();
                            } else {
                       m865p();
                            }
                   C0827el.m885a(4, f788a, "HTTP status: " + this.f803r + " for url: " + this.f791d);
                   m866q();
                        } catch (Exception e) {
                   C0827el.m885a(4, f788a, "HTTP status: " + this.f803r + " for url: " + this.f791d);
                   C0827el.m886a(3, f788a, "Exception during http request: " + this.f791d, e);
                   this.f802q = e;
                   m866q();
                        }
                    } catch (Throwable th) {
                        m866q();
                        throw th;
                    }
                }

                @Override 
                
       public void mo881h() {
           m880g();
                }

                
       private void m864o() throws Exception {
                    BufferedOutputStream bufferedOutputStream;
                    InputStream inputStream;
                    Closeable closeable = null;
           if (this.f801p) {
               return;
                    }
                    try {
               this.f798m = (HttpURLConnection) new URL(this.f791d).openConnection();
               this.f798m.setConnectTimeout(this.f793f);
               this.f798m.setReadTimeout(this.f794i);
               this.f798m.setRequestMethod(this.f792e.toString());
               this.f798m.setInstanceFollowRedirects(this.f795j);
               this.f798m.setDoOutput(a.kPost.equals(this.f792e));
               this.f798m.setDoInput(true);
               if (C0827el.m901d() && (this.f798m instanceof HttpsURLConnection)) {
                   HttpsURLConnection httpsURLConnection = (HttpsURLConnection) this.f798m;
                   httpsURLConnection.setHostnameVerifier(m863n());
                   httpsURLConnection.setSSLSocketFactory(m862m().getSocketFactory());
                        }
                        for (Map.Entry<String, String> entry : this.f796k.m755b()) {
                   this.f798m.addRequestProperty(entry.getKey(), entry.getValue());
                        }
               if (!a.kGet.equals(this.f792e) && !a.kPost.equals(this.f792e)) {
                   this.f798m.setRequestProperty("Accept-Encoding", "");
                        }
               if (this.f801p) {
                   m867r();
                   return;
                        }
               if (a.kPost.equals(this.f792e)) {
                            try {
                       OutputStream outputStream = this.f798m.getOutputStream();
                                try {
                           bufferedOutputStream = new BufferedOutputStream(outputStream);
                                    try {
                               m860a(bufferedOutputStream);
                               C0843fb.m962a(bufferedOutputStream);
                               C0843fb.m962a(outputStream);
                                    } catch (Throwable th) {
                                        th = th;
                                        closeable = outputStream;
                                        C0843fb.m962a(bufferedOutputStream);
                               C0843fb.m962a(closeable);
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                           th = th2;
                                    bufferedOutputStream = null;
                                    closeable = outputStream;
                                }
                            } catch (Throwable th3) {
                       th = th3;
                                bufferedOutputStream = null;
                            }
                        }
               this.f803r = this.f798m.getResponseCode();
                        for (Map.Entry<String, List<String>> entry2 : this.f798m.getHeaderFields().entrySet()) {
                   Iterator<String> it = entry2.getValue().iterator();
                            while (it.hasNext()) {
                       this.f804s.m754a(entry2.getKey(), it.next());
                            }
                        }
               if (!a.kGet.equals(this.f792e) && !a.kPost.equals(this.f792e)) {
                   m867r();
                   return;
                        }
               if (this.f801p) {
                   m867r();
                   return;
                        }
                        try {
                   InputStream inputStream2 = this.f798m.getInputStream();
                            try {
                       BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream2);
                                try {
                           m859a(bufferedInputStream);
                           C0843fb.m962a(bufferedInputStream);
                           C0843fb.m962a(inputStream2);
                           m867r();
                                } catch (Throwable th4) {
                                    th = th4;
                                    closeable = bufferedInputStream;
                                    inputStream = inputStream2;
                                    C0843fb.m962a(closeable);
                           C0843fb.m962a(inputStream);
                                    throw th;
                                }
                            } catch (Throwable th5) {
                       th = th5;
                                inputStream = inputStream2;
                            }
                        } catch (Throwable th6) {
                   th = th6;
                            inputStream = null;
                        }
                    } catch (Throwable th7) {
                        m867r();
                        throw th7;
                    }
                }

                
       private void m865p() throws Exception {
                    BufferedInputStream bufferedInputStream;
                    InputStream inputStream = null;
           if (!this.f801p) {
               HttpRequestBase httpRequestBaseM882a = this.f792e.m882a(this.f791d);
                        for (Map.Entry<String, String> entry : this.f796k.m755b()) {
                   httpRequestBaseM882a.setHeader(entry.getKey(), entry.getValue());
                        }
               if (!a.kGet.equals(this.f792e) && !a.kPost.equals(this.f792e)) {
                   httpRequestBaseM882a.removeHeaders("Accept-Encoding");
                        }
               if (a.kPost.equals(this.f792e)) {
                   ((HttpPost) httpRequestBaseM882a).setEntity(new AnonymousClass1());
                        }
                        try {
                   BasicHttpParams basicHttpParams = new BasicHttpParams();
                   HttpConnectionParams.setConnectionTimeout(basicHttpParams, this.f793f);
                   HttpConnectionParams.setSoTimeout(basicHttpParams, this.f794i);
                   basicHttpParams.setParameter("http.protocol.handle-redirects", Boolean.valueOf(this.f795j));
                   this.f799n = C0823eh.m840a(basicHttpParams);
                   HttpResponse httpResponseExecute = this.f799n.execute(httpRequestBaseM882a);
                   if (this.f801p) {
                       throw new Exception("Request cancelled");
                            }
                   if (httpResponseExecute != null) {
                       this.f803r = httpResponseExecute.getStatusLine().getStatusCode();
                       Header[] allHeaders = httpResponseExecute.getAllHeaders();
                       if (allHeaders != null) {
                                    for (Header header : allHeaders) {
                               HeaderElement[] elements = header.getElements();
                                        for (HeaderElement headerElement : elements) {
                                   this.f804s.m754a(headerElement.getName(), headerElement.getValue());
                                        }
                                    }
                                }
                       if (a.kGet.equals(this.f792e) || a.kPost.equals(this.f792e)) {
                           if (this.f801p) {
                               throw new Exception("Request cancelled");
                                    }
                           HttpEntity entity = httpResponseExecute.getEntity();
                           if (entity != null) {
                                        try {
                                   InputStream content = entity.getContent();
                                            try {
                                       bufferedInputStream = new BufferedInputStream(content);
                                                try {
                                           m859a(bufferedInputStream);
                                           C0843fb.m962a(bufferedInputStream);
                                           C0843fb.m962a(content);
                                                } catch (Throwable th) {
                                                    th = th;
                                                    inputStream = content;
                                                    C0843fb.m962a(bufferedInputStream);
                                           C0843fb.m962a(inputStream);
                                                    throw th;
                                                }
                                            } catch (Throwable th2) {
                                       th = th2;
                                                bufferedInputStream = null;
                                                inputStream = content;
                                            }
                                        } catch (Throwable th3) {
                                   th = th3;
                                            bufferedInputStream = null;
                                        }
                                    }
                                } else {
                           m867r();
                           return;
                                }
                            }
                   m867r();
                        } catch (Throwable th4) {
                            m867r();
                            throw th4;
                        }
                    }
                }

                
       class AnonymousClass1 extends AbstractHttpEntity {
           AnonymousClass1() {
                    }

                    @Override 
           public boolean isRepeatable() {
               return false;
                    }

                    @Override 
           public long getContentLength() {
               return -1L;
                    }

                    @Override 
           public boolean isStreaming() {
               return false;
                    }

                    @Override 
           public InputStream getContent() throws IOException {
               throw new UnsupportedOperationException();
                    }

                    @Override 
                    @TargetApi(9)
           public void writeTo(OutputStream outputStream) throws Throwable {
                        try {
                            try {
                       BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                                try {
                           C0826ek.m858a(C0826ek.this, bufferedOutputStream);
                           C0843fb.m962a(bufferedOutputStream);
                                } catch (IOException e) {
                                } catch (Exception e2) {
                           e = e2;
                           if (Build.VERSION.SDK_INT >= 9) {
                               throw new IOException(e);
                                    }
                           throw new IOException(e.toString());
                                }
                            } catch (Throwable th) {
                       th = th;
                       C0843fb.m962a((Closeable) null);
                                throw th;
                            }
                        } catch (IOException e3) {
                   throw e3;
                        } catch (Exception e4) {
                   e = e4;
                        } catch (Throwable th2) {
                   th = th2;
                   C0843fb.m962a((Closeable) null);
                            throw th;
                        }
                    }
                }

                
       private void m860a(OutputStream outputStream) throws Exception {
           if (this.f797l != null && !m875b() && outputStream != null) {
               this.f797l.mo855a(this, outputStream);
                    }
                }

                
       private void m859a(InputStream inputStream) throws Exception {
           if (this.f797l != null && !m875b() && inputStream != null) {
               this.f797l.mo854a(this, inputStream);
                    }
                }

                
       private void m866q() {
           if (this.f797l != null && !m875b()) {
               this.f797l.mo853a(this);
                    }
                }

                
       private void m867r() {
           if (!this.f800o) {
               this.f800o = true;
               if (this.f798m != null) {
                   this.f798m.disconnect();
                        }
               if (this.f799n != null) {
                   this.f799n.getConnectionManager().shutdown();
                        }
                    }
                }

                
       private void m868s() {
           if (!this.f800o) {
               this.f800o = true;
               if (this.f798m != null || this.f799n != null) {
                   new AnonymousClass2().start();
                        }
                    }
                }

                
       class AnonymousClass2 extends Thread {
           AnonymousClass2() {
                    }

                    @Override 
           public void run() {
               if (C0826ek.m857a(C0826ek.this) != null) {
                   C0826ek.m857a(C0826ek.this).disconnect();
                        }
               if (C0826ek.m861b(C0826ek.this) != null) {
                   C0826ek.m861b(C0826ek.this).getConnectionManager().shutdown();
                        }
                    }
                }
            }
