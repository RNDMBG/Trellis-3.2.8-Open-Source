            package com.codebutler.android_websockets;

            import android.os.Handler;
            import android.os.HandlerThread;
            import android.text.TextUtils;
            import android.util.Base64;
            import android.util.Log;
            import java.io.EOFException;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.PrintWriter;
            import java.net.Socket;
            import java.net.URI;
            import java.security.KeyManagementException;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.util.Arrays;
            import java.util.List;
            import javax.net.SocketFactory;
            import javax.net.ssl.SSLContext;
            import javax.net.ssl.SSLException;
            import javax.net.ssl.SSLSocketFactory;
            import javax.net.ssl.TrustManager;
            import org.apache.http.Header;
            import org.apache.http.HttpException;
            import org.apache.http.NameValuePair;
            import org.apache.http.StatusLine;
            import org.apache.http.client.HttpResponseException;
            import org.apache.http.message.BasicLineParser;
            import org.apache.http.message.BasicNameValuePair;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class WebSocketClient {
                public static final int FLAG_CONNECTED = 2;
                public static final int FLAG_CONNECTING = 1;
                private static final String TAG = "WebSocketClient";
                private static TrustManager[] sTrustManagers;
                private volatile int mConnectionFlag;
                private List<BasicNameValuePair> mExtraHeaders;
                private Handler mHandler;
                private Listener mListener;
                private Socket mSocket;
                private Thread mThread;
                private URI mURI;
                private final Object mSendLock = new Object();
                private HybiParser mParser = new HybiParser(this);
                private HandlerThread mHandlerThread = new HandlerThread("websocket-thread");

                public interface Listener {
                    void onConnect();

                    void onDisconnect(int i, String str);

                    void onError(Exception exc);

                    void onMessage(String str);

                    void onMessage(byte[] bArr);
                }

/* 29 */        static /* synthetic */ int access$002(WebSocketClient x0, int x1) {
/* 30 */            x0.mConnectionFlag = x1;
                    return x1;
                }

/* 29 */        static /* synthetic */ String access$100(WebSocketClient x0) {
/* 30 */            return x0.createSecret();
                }

/* 29 */        static /* synthetic */ Listener access$1000(WebSocketClient x0) {
/* 30 */            return x0.mListener;
                }

/* 29 */        static /* synthetic */ HybiParser access$1100(WebSocketClient x0) {
/* 30 */            return x0.mParser;
                }

/* 29 */        static /* synthetic */ Object access$1200(WebSocketClient x0) {
/* 30 */            return x0.mSendLock;
                }

/* 29 */        static /* synthetic */ URI access$200(WebSocketClient x0) {
/* 30 */            return x0.mURI;
                }

/* 29 */        static /* synthetic */ SSLSocketFactory access$300(WebSocketClient x0) throws NoSuchAlgorithmException, KeyManagementException {
/* 30 */            return x0.getSSLSocketFactory();
                }

/* 29 */        static /* synthetic */ Socket access$400(WebSocketClient x0) {
/* 30 */            return x0.mSocket;
                }

/* 29 */        static /* synthetic */ Socket access$402(WebSocketClient x0, Socket x1) {
/* 30 */            x0.mSocket = x1;
                    return x1;
                }

/* 29 */        static /* synthetic */ List<BasicNameValuePair> access$500(WebSocketClient x0) {
/* 30 */            return x0.mExtraHeaders;
                }

/* 29 */        static /* synthetic */ String access$600(WebSocketClient x0, HybiParser.HappyDataInputStream x1) throws IOException {
/* 30 */            return x0.readLine(x1);
                }

/* 29 */        static /* synthetic */ StatusLine access$700(WebSocketClient x0, String x1) {
/* 30 */            return x0.parseStatusLine(x1);
                }

/* 29 */        static /* synthetic */ Header access$800(WebSocketClient x0, String x1) {
/* 30 */            return x0.parseHeader(x1);
                }

/* 29 */        static /* synthetic */ String access$900(WebSocketClient x0, String x1) {
/* 30 */            return x0.createSecretValidation(x1);
                }

/* 50 */        public static void setTrustManagers(TrustManager[] tm) {
/* 51 */            sTrustManagers = tm;
                }

/* 53 */        public WebSocketClient(URI uri, Listener listener, List<BasicNameValuePair> extraHeaders) {
/* 55 */            this.mURI = uri;
/* 56 */            this.mListener = listener;
/* 57 */            this.mExtraHeaders = extraHeaders;
/* 61 */            this.mHandlerThread.start();
/* 62 */            this.mHandler = new Handler(this.mHandlerThread.getLooper());
/* 63 */            this.mConnectionFlag = 0;
                }

/* 66 */        public boolean isConnected() {
                    return (this.mSocket == null || (this.mConnectionFlag & 2) == 0) ? false : true;
                }

/* 70 */        public boolean isConnecting() {
                    return (this.mSocket == null || (this.mConnectionFlag & 1) == 0) ? false : true;
                }

/* 74 */        public Listener getListener() {
/* 75 */            return this.mListener;
                }

/* 78 */        public void connect() {
/* 79 */            if (this.mThread == null || !this.mThread.isAlive()) {
/* 83 */                this.mThread = new Thread(new RunnableC05841());
/* 171 */               this.mThread.start();
                    }
                }

                /* JADX INFO: renamed from: com.codebutler.android_websockets.WebSocketClient$1 */
/* 81 */        class RunnableC05841 implements Runnable {
/* 82 */            RunnableC05841() {
                    }

                    @Override // java.lang.Runnable
/* 86 */            public void run() {
                        try {
/* 87 */                    WebSocketClient.access$002(WebSocketClient.this, 1);
/* 88 */                    String secret = WebSocketClient.access$100(WebSocketClient.this);
/* 90 */                    String path = TextUtils.isEmpty(WebSocketClient.access$200(WebSocketClient.this).getPath()) ? "/" : WebSocketClient.access$200(WebSocketClient.this).getPath();
/* 91 */                    if (!TextUtils.isEmpty(WebSocketClient.access$200(WebSocketClient.this).getQuery())) {
/* 92 */                        path = path + "?" + WebSocketClient.access$200(WebSocketClient.this).getQuery();
                            }
/* 95 */                    URI origin = new URI("https", "//" + WebSocketClient.access$200(WebSocketClient.this).getHost(), null);
/* 97 */                    SocketFactory factory = WebSocketClient.access$300(WebSocketClient.this);
/* 98 */                    WebSocketClient.access$402(WebSocketClient.this, factory.createSocket(WebSocketClient.access$200(WebSocketClient.this).getHost(), 443));
/* 100 */                   PrintWriter out = new PrintWriter(WebSocketClient.access$400(WebSocketClient.this).getOutputStream());
/* 101 */                   out.print("GET " + path + " HTTP/1.1\r\n");
/* 102 */                   out.print("Upgrade: websocket\r\n");
/* 103 */                   out.print("Connection: Upgrade\r\n");
/* 104 */                   out.print("Host: " + WebSocketClient.access$200(WebSocketClient.this).getHost() + "\r\n");
/* 105 */                   out.print("Origin: " + origin.toString() + "\r\n");
/* 106 */                   out.print("Sec-WebSocket-Key: " + secret + "\r\n");
/* 107 */                   out.print("Sec-WebSocket-Version: 13\r\n");
/* 108 */                   if (WebSocketClient.access$500(WebSocketClient.this) != null) {
                                for (NameValuePair pair : WebSocketClient.access$500(WebSocketClient.this)) {
/* 110 */                           out.print(String.format("%s: %s\r\n", pair.getName(), pair.getValue()));
                                }
                            }
/* 113 */                   out.print("\r\n");
/* 114 */                   out.flush();
/* 116 */                   HybiParser.HappyDataInputStream stream = new HybiParser.HappyDataInputStream(WebSocketClient.access$400(WebSocketClient.this).getInputStream());
/* 119 */                   StatusLine statusLine = WebSocketClient.access$700(WebSocketClient.this, WebSocketClient.access$600(WebSocketClient.this, stream));
/* 120 */                   if (statusLine == null) {
/* 121 */                       throw new HttpException("Received no reply from server.");
                            }
/* 122 */                   if (statusLine.getStatusCode() != 101) {
/* 123 */                       throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                            }
/* 128 */                   boolean validated = false;
                            while (true) {
/* 130 */                       String line = WebSocketClient.access$600(WebSocketClient.this, stream);
                                if (!TextUtils.isEmpty(line)) {
/* 131 */                           Header header = WebSocketClient.access$800(WebSocketClient.this, line);
/* 132 */                           if (header.getName().equals("Sec-WebSocket-Accept")) {
/* 133 */                               String expected = WebSocketClient.access$900(WebSocketClient.this, secret);
/* 134 */                               String actual = header.getValue().trim();
/* 136 */                               if (!expected.equals(actual)) {
/* 137 */                                   throw new HttpException("Bad Sec-WebSocket-Accept header value.");
                                        }
/* 140 */                               validated = true;
                                    }
                                } else {
/* 144 */                           if (!validated) {
/* 145 */                               throw new HttpException("No Sec-WebSocket-Accept header.");
                                    }
/* 148 */                           WebSocketClient.access$002(WebSocketClient.this, 2);
/* 149 */                           WebSocketClient.access$1000(WebSocketClient.this).onConnect();
/* 152 */                           WebSocketClient.access$1100(WebSocketClient.this).start(stream);
/* 169 */                           return;
                                }
                            }
                        } catch (EOFException ex) {
/* 155 */                   Log.d(WebSocketClient.TAG, "WebSocket EOF!", ex);
/* 156 */                   WebSocketClient.access$1000(WebSocketClient.this).onDisconnect(0, "EOF");
/* 157 */                   WebSocketClient.access$002(WebSocketClient.this, 0);
                        } catch (SSLException ex2) {
/* 161 */                   Log.d(WebSocketClient.TAG, "Websocket SSL error!", ex2);
/* 162 */                   WebSocketClient.access$1000(WebSocketClient.this).onDisconnect(0, "SSL");
/* 163 */                   WebSocketClient.access$002(WebSocketClient.this, 0);
                        } catch (Exception ex3) {
/* 166 */                   WebSocketClient.access$1000(WebSocketClient.this).onError(ex3);
/* 167 */                   WebSocketClient.access$002(WebSocketClient.this, 0);
                        }
                    }
                }

/* 174 */       public void disconnect() {
/* 175 */           this.mConnectionFlag = 0;
/* 176 */           if (this.mSocket != null) {
/* 177 */               this.mHandler.post(new RunnableC05852());
                    }
                }

                /* JADX INFO: renamed from: com.codebutler.android_websockets.WebSocketClient$2 */
/* 175 */       class RunnableC05852 implements Runnable {
/* 176 */           RunnableC05852() {
                    }

                    @Override // java.lang.Runnable
/* 180 */           public void run() {
                        try {
/* 181 */                   WebSocketClient.access$400(WebSocketClient.this).close();
/* 182 */                   Log.d("RTC", "WebSocket disconnected");
/* 183 */                   WebSocketClient.access$402(WebSocketClient.this, null);
                        } catch (IOException ex) {
/* 185 */                   Log.d(WebSocketClient.TAG, "Error while disconnecting", ex);
/* 186 */                   WebSocketClient.access$1000(WebSocketClient.this).onError(ex);
                        }
                    }
                }

/* 193 */       public void send(String data) {
/* 194 */           sendFrame(this.mParser.frame(data));
                }

/* 197 */       public void send(byte[] data) {
/* 198 */           sendFrame(this.mParser.frame(data));
                }

/* 201 */       private StatusLine parseStatusLine(String line) {
/* 202 */           if (TextUtils.isEmpty(line)) {
/* 203 */               return null;
                    }
                    return BasicLineParser.parseStatusLine(line, new BasicLineParser());
                }

/* 208 */       private Header parseHeader(String line) {
/* 209 */           return BasicLineParser.parseHeader(line, new BasicLineParser());
                }

/* 213 */       private String readLine(HybiParser.HappyDataInputStream reader) throws IOException {
/* 214 */           int readChar = reader.read();
/* 215 */           if (readChar == -1) {
                        return null;
                    }
/* 218 */           StringBuilder string = new StringBuilder("");
/* 219 */           while (readChar != 10) {
/* 220 */               if (readChar != 13) {
/* 221 */                   string.append((char) readChar);
                        }
/* 224 */               readChar = reader.read();
/* 225 */               if (readChar == -1) {
                            return null;
                        }
                    }
/* 229 */           return string.toString();
                }

/* 232 */       private String createSecret() {
/* 233 */           byte[] nonce = new byte[16];
                    for (int i = 0; i < 16; i++) {
/* 235 */               nonce[i] = (byte) (Math.random() * 256.0d);
                    }
/* 237 */           return Base64.encodeToString(nonce, 0).trim();
                }

/* 241 */       private String createSecretValidation(String secret) {
                    try {
/* 242 */               MessageDigest md = MessageDigest.getInstance("SHA-1");
/* 243 */               md.update((secret + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes());
/* 244 */               return Base64.encodeToString(md.digest(), 0).trim();
                    } catch (NoSuchAlgorithmException e) {
/* 246 */               throw new RuntimeException(e);
                    }
                }

                /* JADX INFO: renamed from: com.codebutler.android_websockets.WebSocketClient$3 */
/* 249 */       class RunnableC05863 implements Runnable {
                    final /* synthetic */ byte[] val$frame;

/* 250 */           RunnableC05863(byte[] bArr) {
                        this.val$frame = bArr;
                    }

                    @Override // java.lang.Runnable
/* 254 */           public void run() {
                        try {
                            synchronized (WebSocketClient.access$1200(WebSocketClient.this)) {
                                try {
/* 256 */                           if (WebSocketClient.access$400(WebSocketClient.this) == null) {
/* 257 */                               throw new IOException("Socket not connected while trying to write: " + Arrays.toString(this.val$frame));
                                    }
/* 261 */                           OutputStream outputStream = WebSocketClient.access$400(WebSocketClient.this).getOutputStream();
/* 262 */                           outputStream.write(this.val$frame);
/* 263 */                           outputStream.flush();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } catch (IOException e) {
/* 267 */                   WebSocketClient.access$1000(WebSocketClient.this).onError(e);
                        }
                    }
                }

/* 250 */       void sendFrame(byte[] frame) {
/* 251 */           this.mHandler.post(new RunnableC05863(frame));
                }

/* 281 */       private SSLSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
/* 282 */           SSLContext context = SSLContext.getInstance("TLS");
/* 283 */           context.init(null, sTrustManagers, null);
/* 284 */           return context.getSocketFactory();
                }
            }
