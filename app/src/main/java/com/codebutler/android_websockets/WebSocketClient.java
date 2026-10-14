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
            
            
    public class WebSocketClient {
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

        static  int access$002(WebSocketClient x0, int x1) {
            x0.mConnectionFlag = x1;
                    return x1;
                }

        static  String access$100(WebSocketClient x0) {
            return x0.createSecret();
                }

        static  Listener access$1000(WebSocketClient x0) {
            return x0.mListener;
                }

        static  HybiParser access$1100(WebSocketClient x0) {
            return x0.mParser;
                }

        static  Object access$1200(WebSocketClient x0) {
            return x0.mSendLock;
                }

        static  URI access$200(WebSocketClient x0) {
            return x0.mURI;
                }

        static  SSLSocketFactory access$300(WebSocketClient x0) throws NoSuchAlgorithmException, KeyManagementException {
            return x0.getSSLSocketFactory();
                }

        static  Socket access$400(WebSocketClient x0) {
            return x0.mSocket;
                }

        static  Socket access$402(WebSocketClient x0, Socket x1) {
            x0.mSocket = x1;
                    return x1;
                }

        static  List<BasicNameValuePair> access$500(WebSocketClient x0) {
            return x0.mExtraHeaders;
                }

        static  String access$600(WebSocketClient x0, HybiParser.HappyDataInputStream x1) throws IOException {
            return x0.readLine(x1);
                }

        static  StatusLine access$700(WebSocketClient x0, String x1) {
            return x0.parseStatusLine(x1);
                }

        static  Header access$800(WebSocketClient x0, String x1) {
            return x0.parseHeader(x1);
                }

        static  String access$900(WebSocketClient x0, String x1) {
            return x0.createSecretValidation(x1);
                }

        public static void setTrustManagers(TrustManager[] tm) {
            sTrustManagers = tm;
                }

        public WebSocketClient(URI uri, Listener listener, List<BasicNameValuePair> extraHeaders) {
            this.mURI = uri;
            this.mListener = listener;
            this.mExtraHeaders = extraHeaders;
            this.mHandlerThread.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper());
            this.mConnectionFlag = 0;
                }

        public boolean isConnected() {
                    return (this.mSocket == null || (this.mConnectionFlag & 2) == 0) ? false : true;
                }

        public boolean isConnecting() {
                    return (this.mSocket == null || (this.mConnectionFlag & 1) == 0) ? false : true;
                }

        public Listener getListener() {
            return this.mListener;
                }

        public void connect() {
            if (this.mThread == null || !this.mThread.isAlive()) {
                this.mThread = new Thread(new RunnableC05841());
               this.mThread.start();
                    }
                }

                
        class RunnableC05841 implements Runnable {
            RunnableC05841() {
                    }

                    @Override 
            public void run() {
                        try {
                    WebSocketClient.access$002(WebSocketClient.this, 1);
                    String secret = WebSocketClient.access$100(WebSocketClient.this);
                    String path = TextUtils.isEmpty(WebSocketClient.access$200(WebSocketClient.this).getPath()) ? "/" : WebSocketClient.access$200(WebSocketClient.this).getPath();
                    if (!TextUtils.isEmpty(WebSocketClient.access$200(WebSocketClient.this).getQuery())) {
                        path = path + "?" + WebSocketClient.access$200(WebSocketClient.this).getQuery();
                            }
                    URI origin = new URI("https", "//" + WebSocketClient.access$200(WebSocketClient.this).getHost(), null);
                    SocketFactory factory = WebSocketClient.access$300(WebSocketClient.this);
                    WebSocketClient.access$402(WebSocketClient.this, factory.createSocket(WebSocketClient.access$200(WebSocketClient.this).getHost(), 443));
                   PrintWriter out = new PrintWriter(WebSocketClient.access$400(WebSocketClient.this).getOutputStream());
                   out.print("GET " + path + " HTTP/1.1\r\n");
                   out.print("Upgrade: websocket\r\n");
                   out.print("Connection: Upgrade\r\n");
                   out.print("Host: " + WebSocketClient.access$200(WebSocketClient.this).getHost() + "\r\n");
                   out.print("Origin: " + origin.toString() + "\r\n");
                   out.print("Sec-WebSocket-Key: " + secret + "\r\n");
                   out.print("Sec-WebSocket-Version: 13\r\n");
                   if (WebSocketClient.access$500(WebSocketClient.this) != null) {
                                for (NameValuePair pair : WebSocketClient.access$500(WebSocketClient.this)) {
                           out.print(String.format("%s: %s\r\n", pair.getName(), pair.getValue()));
                                }
                            }
                   out.print("\r\n");
                   out.flush();
                   HybiParser.HappyDataInputStream stream = new HybiParser.HappyDataInputStream(WebSocketClient.access$400(WebSocketClient.this).getInputStream());
                   StatusLine statusLine = WebSocketClient.access$700(WebSocketClient.this, WebSocketClient.access$600(WebSocketClient.this, stream));
                   if (statusLine == null) {
                       throw new HttpException("Received no reply from server.");
                            }
                   if (statusLine.getStatusCode() != 101) {
                       throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                            }
                   boolean validated = false;
                            while (true) {
                       String line = WebSocketClient.access$600(WebSocketClient.this, stream);
                                if (!TextUtils.isEmpty(line)) {
                           Header header = WebSocketClient.access$800(WebSocketClient.this, line);
                           if (header.getName().equals("Sec-WebSocket-Accept")) {
                               String expected = WebSocketClient.access$900(WebSocketClient.this, secret);
                               String actual = header.getValue().trim();
                               if (!expected.equals(actual)) {
                                   throw new HttpException("Bad Sec-WebSocket-Accept header value.");
                                        }
                               validated = true;
                                    }
                                } else {
                           if (!validated) {
                               throw new HttpException("No Sec-WebSocket-Accept header.");
                                    }
                           WebSocketClient.access$002(WebSocketClient.this, 2);
                           WebSocketClient.access$1000(WebSocketClient.this).onConnect();
                           WebSocketClient.access$1100(WebSocketClient.this).start(stream);
                           return;
                                }
                            }
                        } catch (EOFException ex) {
                   Log.d(WebSocketClient.TAG, "WebSocket EOF!", ex);
                   WebSocketClient.access$1000(WebSocketClient.this).onDisconnect(0, "EOF");
                   WebSocketClient.access$002(WebSocketClient.this, 0);
                        } catch (SSLException ex2) {
                   Log.d(WebSocketClient.TAG, "Websocket SSL error!", ex2);
                   WebSocketClient.access$1000(WebSocketClient.this).onDisconnect(0, "SSL");
                   WebSocketClient.access$002(WebSocketClient.this, 0);
                        } catch (Exception ex3) {
                   WebSocketClient.access$1000(WebSocketClient.this).onError(ex3);
                   WebSocketClient.access$002(WebSocketClient.this, 0);
                        }
                    }
                }

       public void disconnect() {
           this.mConnectionFlag = 0;
           if (this.mSocket != null) {
               this.mHandler.post(new RunnableC05852());
                    }
                }

                
       class RunnableC05852 implements Runnable {
           RunnableC05852() {
                    }

                    @Override 
           public void run() {
                        try {
                   WebSocketClient.access$400(WebSocketClient.this).close();
                   Log.d("RTC", "WebSocket disconnected");
                   WebSocketClient.access$402(WebSocketClient.this, null);
                        } catch (IOException ex) {
                   Log.d(WebSocketClient.TAG, "Error while disconnecting", ex);
                   WebSocketClient.access$1000(WebSocketClient.this).onError(ex);
                        }
                    }
                }

       public void send(String data) {
           sendFrame(this.mParser.frame(data));
                }

       public void send(byte[] data) {
           sendFrame(this.mParser.frame(data));
                }

       private StatusLine parseStatusLine(String line) {
           if (TextUtils.isEmpty(line)) {
               return null;
                    }
                    return BasicLineParser.parseStatusLine(line, new BasicLineParser());
                }

       private Header parseHeader(String line) {
           return BasicLineParser.parseHeader(line, new BasicLineParser());
                }

       private String readLine(HybiParser.HappyDataInputStream reader) throws IOException {
           int readChar = reader.read();
           if (readChar == -1) {
                        return null;
                    }
           StringBuilder string = new StringBuilder("");
           while (readChar != 10) {
               if (readChar != 13) {
                   string.append((char) readChar);
                        }
               readChar = reader.read();
               if (readChar == -1) {
                            return null;
                        }
                    }
           return string.toString();
                }

       private String createSecret() {
           byte[] nonce = new byte[16];
                    for (int i = 0; i < 16; i++) {
               nonce[i] = (byte) (Math.random() * 256.0d);
                    }
           return Base64.encodeToString(nonce, 0).trim();
                }

       private String createSecretValidation(String secret) {
                    try {
               MessageDigest md = MessageDigest.getInstance("SHA-1");
               md.update((secret + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes());
               return Base64.encodeToString(md.digest(), 0).trim();
                    } catch (NoSuchAlgorithmException e) {
               throw new RuntimeException(e);
                    }
                }

                
       class RunnableC05863 implements Runnable {
                    final  byte[] val$frame;

           RunnableC05863(byte[] bArr) {
                        this.val$frame = bArr;
                    }

                    @Override 
           public void run() {
                        try {
                            synchronized (WebSocketClient.access$1200(WebSocketClient.this)) {
                                try {
                           if (WebSocketClient.access$400(WebSocketClient.this) == null) {
                               throw new IOException("Socket not connected while trying to write: " + Arrays.toString(this.val$frame));
                                    }
                           OutputStream outputStream = WebSocketClient.access$400(WebSocketClient.this).getOutputStream();
                           outputStream.write(this.val$frame);
                           outputStream.flush();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } catch (IOException e) {
                   WebSocketClient.access$1000(WebSocketClient.this).onError(e);
                        }
                    }
                }

       void sendFrame(byte[] frame) {
           this.mHandler.post(new RunnableC05863(frame));
                }

       private SSLSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
           SSLContext context = SSLContext.getInstance("TLS");
           context.init(null, sTrustManagers, null);
           return context.getSocketFactory();
                }
            }
