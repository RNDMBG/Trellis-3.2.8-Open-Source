            package twitter4j.internal.http;

            import co.vine.android.network.apache.MultipartEntity;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.BufferedInputStream;
            import java.io.DataOutputStream;
            import java.io.FileInputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.Serializable;
            import java.net.Authenticator;
            import java.net.HttpURLConnection;
            import java.net.InetSocketAddress;
            import java.net.PasswordAuthentication;
            import java.net.Proxy;
            import java.net.URL;
            import java.util.HashMap;
            import java.util.Map;
            import org.scribe.model.OAuthConstants;
            import org.scribe.model.Request;
            import twitter4j.TwitterException;
            import twitter4j.conf.ConfigurationContext;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class HttpClientImpl extends HttpClientBase implements HttpResponseCode, Serializable {
                private static final Map<HttpClientConfiguration, HttpClient> instanceMap;
                private static final long serialVersionUID = -8819171414069621503L;

                static {
/* 48 */            if (ConfigurationContext.getInstance().isDalvik()) {
/* 51 */                System.setProperty("http.keepAlive", "false");
                    }
/* 63 */            instanceMap = new HashMap(1);
                }

/* 55 */        public HttpClientImpl() {
/* 56 */            super(ConfigurationContext.getInstance());
                }

/* 59 */        public HttpClientImpl(HttpClientConfiguration conf) {
/* 60 */            super(conf);
                }

/* 65 */        public static HttpClient getInstance(HttpClientConfiguration conf) {
/* 66 */            HttpClient client = instanceMap.get(conf);
/* 67 */            if (client == null) {
/* 68 */                HttpClient client2 = new HttpClientImpl(conf);
/* 69 */                instanceMap.put(conf, client2);
/* 71 */                return client2;
                    }
/* 71 */            return client;
                }

/* 74 */        public HttpResponse get(String url) throws TwitterException {
/* 75 */            return request(new HttpRequest(RequestMethod.GET, url, null, null, null));
                }

/* 78 */        public HttpResponse post(String url, HttpParameter[] params) throws TwitterException {
/* 79 */            return request(new HttpRequest(RequestMethod.POST, url, params, null, null));
                }

                @Override // twitter4j.internal.http.HttpClient
/* 84 */        public HttpResponse request(HttpRequest req) throws TwitterException {
/* 85 */            int retry = this.CONF.getHttpRetryCount() + 1;
/* 86 */            HttpResponse res = null;
/* 87 */            int retriedCount = 0;
                    while (true) {
                        HttpResponse res2 = res;
                        if (retriedCount >= retry) {
                            return res2;
                        }
/* 91 */                OutputStream os = null;
                        try {
/* 93 */                    HttpURLConnection con = getConnection(req.getURL());
/* 94 */                    con.setDoInput(true);
/* 95 */                    setHeaders(req, con);
/* 96 */                    con.setRequestMethod(req.getMethod().name());
/* 97 */                    if (req.getMethod() == RequestMethod.POST) {
/* 98 */                        if (HttpParameter.containsFile(req.getParameters())) {
/* 99 */                            String boundary = "----Twitter4J-upload" + System.currentTimeMillis();
/* 100 */                           con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
/* 101 */                           String boundary2 = MultipartEntity.DASH_DASH + boundary;
/* 102 */                           con.setDoOutput(true);
/* 103 */                           os = con.getOutputStream();
/* 104 */                           DataOutputStream out = new DataOutputStream(os);
/* 105 */                           HttpParameter[] arr$ = req.getParameters();
                                    for (HttpParameter param : arr$) {
/* 106 */                               if (param.isFile()) {
/* 107 */                                   write(out, boundary2 + "\r\n");
/* 108 */                                   write(out, "Content-Disposition: form-data; name=\"" + param.getName() + "\"; filename=\"" + param.getFile().getName() + "\"\r\n");
/* 109 */                                   write(out, "Content-Type: " + param.getContentType() + "\r\n\r\n");
/* 110 */                                   BufferedInputStream in = new BufferedInputStream(param.hasFileBody() ? param.getFileBody() : new FileInputStream(param.getFile()));
/* 113 */                                   byte[] buff = new byte[1024];
                                            while (true) {
/* 115 */                                       int length = in.read(buff);
                                                if (length == -1) {
                                                    break;
                                                }
/* 116 */                                       out.write(buff, 0, length);
                                            }
/* 118 */                                   write(out, "\r\n");
/* 119 */                                   in.close();
                                        } else {
/* 121 */                                   write(out, boundary2 + "\r\n");
/* 122 */                                   write(out, "Content-Disposition: form-data; name=\"" + param.getName() + "\"\r\n");
/* 123 */                                   write(out, "Content-Type: text/plain; charset=UTF-8\r\n\r\n");
/* 125 */                                   out.write(param.getValue().getBytes(IoUtils.UTF_8));
/* 126 */                                   write(out, "\r\n");
                                        }
                                    }
/* 129 */                           write(out, boundary2 + "--\r\n");
/* 130 */                           write(out, "\r\n");
                                } else {
/* 133 */                           con.setRequestProperty("Content-Type", Request.DEFAULT_CONTENT_TYPE);
/* 135 */                           String postParam = HttpParameter.encodeParameters(req.getParameters());
/* 137 */                           byte[] bytes = postParam.getBytes(IoUtils.UTF_8);
/* 138 */                           con.setRequestProperty("Content-Length", Integer.toString(bytes.length));
/* 140 */                           con.setDoOutput(true);
/* 141 */                           os = con.getOutputStream();
/* 142 */                           os.write(bytes);
                                }
/* 144 */                       os.flush();
/* 145 */                       os.close();
                            }
/* 147 */                   res = new HttpResponseImpl(con, this.CONF);
                            try {
/* 148 */                       int responseCode = con.getResponseCode();
/* 163 */                       if (responseCode < 200 || (responseCode != 302 && 300 <= responseCode)) {
/* 164 */                           if (responseCode == 420 || responseCode == 400 || responseCode < 500 || retriedCount == this.CONF.getHttpRetryCount()) {
/* 168 */                               throw new TwitterException(res.asString(), res);
                                    }
                                    try {
                                        try {
/* 176 */                                   os.close();
                                        } catch (IOException ioe) {
/* 182 */                                   if (retriedCount == this.CONF.getHttpRetryCount()) {
/* 183 */                                       throw new TwitterException(ioe.getMessage(), ioe, responseCode);
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    try {
/* 191 */                               Thread.sleep(this.CONF.getHttpRetryIntervalSeconds() * 1000);
                                    } catch (InterruptedException e2) {
                                    }
/* 87 */                            retriedCount++;
                                } else {
                                    try {
/* 176 */                               os.close();
/* 196 */                               return res;
                                    } catch (Exception e3) {
/* 196 */                               return res;
                                    }
                                }
                            } catch (Throwable th) {
/* 175 */                       th = th;
                                try {
/* 176 */                           os.close();
                                } catch (Exception e4) {
                                }
/* 178 */                       throw th;
                            }
                        } catch (Throwable th2) {
                            throw new TwitterException(th2.getMessage(), th2);
                        }
                    }
                }

/* 211 */       private void setHeaders(HttpRequest req, HttpURLConnection connection) {
                    String authorizationHeader;
/* 212 */           if (req.getAuthorization() != null && (authorizationHeader = req.getAuthorization().getAuthorizationHeader(req)) != null) {
/* 216 */               connection.addRequestProperty(OAuthConstants.HEADER, authorizationHeader);
                    }
/* 218 */           if (req.getRequestHeaders() != null) {
                        for (String key : req.getRequestHeaders().keySet()) {
/* 220 */                   connection.addRequestProperty(key, req.getRequestHeaders().get(key));
                        }
                    }
                }

/* 227 */       protected HttpURLConnection getConnection(String url) throws IOException {
                    HttpURLConnection con;
/* 228 */           if (isProxyConfigured()) {
/* 229 */               if (this.CONF.getHttpProxyUser() != null && !this.CONF.getHttpProxyUser().equals("")) {
/* 234 */                   Authenticator.setDefault(new C13101());
                        }
/* 248 */               Proxy proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(this.CONF.getHttpProxyHost(), this.CONF.getHttpProxyPort()));
/* 253 */               con = (HttpURLConnection) new URL(url).openConnection(proxy);
                    } else {
/* 255 */               con = (HttpURLConnection) new URL(url).openConnection();
                    }
/* 257 */           if (this.CONF.getHttpConnectionTimeout() > 0) {
/* 258 */               con.setConnectTimeout(this.CONF.getHttpConnectionTimeout());
                    }
/* 260 */           if (this.CONF.getHttpReadTimeout() > 0) {
/* 261 */               con.setReadTimeout(this.CONF.getHttpReadTimeout());
                    }
/* 263 */           con.setInstanceFollowRedirects(false);
/* 264 */           return con;
                }

                /* JADX INFO: renamed from: twitter4j.internal.http.HttpClientImpl$1 */
/* 232 */       class C13101 extends Authenticator {
/* 233 */           C13101() {
                    }

                    @Override // java.net.Authenticator
/* 238 */           protected PasswordAuthentication getPasswordAuthentication() {
/* 239 */               if (getRequestorType().equals(Authenticator.RequestorType.PROXY)) {
/* 240 */                   return new PasswordAuthentication(HttpClientImpl.this.CONF.getHttpProxyUser(), HttpClientImpl.this.CONF.getHttpProxyPassword().toCharArray());
                        }
                        return null;
                    }
                }
            }
