            package co.vine.android.service;

            import android.app.Service;
            import android.content.Intent;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Looper;
            import android.os.Message;
            import android.os.Messenger;
            import android.os.Process;
            import co.vine.android.Conversation;
            import co.vine.android.VineWebSocketClient;
            import co.vine.android.api.VineParsers;
            import co.vine.android.api.VineRTCConversation;
            import co.vine.android.client.VineAPI;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import com.codebutler.android_websockets.WebSocketClient;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.JsonParser;
            import java.io.IOException;
            import java.net.URI;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.List;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.Executors;
            import org.apache.http.message.BasicNameValuePair;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class RealTimeChatService extends Service {
                public static final int ACTION_CODE_ALERT_NEW_PRIVATE_MESSAGE = 8;
                public static final int ACTION_CODE_CONNECT = 1;
                public static final int ACTION_CODE_DISCONNECT = 5;
                public static final int ACTION_CODE_ERROR = 6;
                public static final int ACTION_CODE_NEW_WEBSOCKET_FRAME = 9;
                public static final int ACTION_CODE_RECONNECT = 2;
                public static final int ACTION_CODE_SUBSCRIBE = 3;
                public static final int ACTION_CODE_UNSUBSCRIBE = 4;
                public static final int ACTION_CODE_UPDATE_TYPING_STATE = 7;
                public static final String ACTION_WEBSOCKET_EVENT = "co.vine.android.rtc.WEBSOCKET_EVENT";
                public static final String EXTRA_ACTION_CODE = "action_code";
                public static final String EXTRA_CODE = "code";
                public static final String EXTRA_CONNECTED = "connected";
                public static final String EXTRA_CONVERSATION_ID = "conversation_id";
                public static final String EXTRA_DATA = "data";
                public static final String EXTRA_IS_TYPING = "is_typing";
                public static final String EXTRA_LAST_MESSAGE_ID = "last_message_id";
                public static final String EXTRA_PID = "pid";
                public static final String EXTRA_REASON = "reason";
                public static final String EXTRA_RESPOND = "respond";
                public static final String EXTRA_SESSION_KEY = "s_key";
                private static final long INITIAL_RETRY_DELAY = 1000;
                private static final int MAX_RETRY_DELAY = 16000;
                public static final String TAG = "RTCService";
                private WebSocketClient mClient;
                private ExecutorService mExecutor;
                private Handler mMainHandler;
                private Messenger mMessenger;
                private long mReconnectDelay;
                private String mRtcUrl;
                private WebSocketClient.Listener mWebSocketListener = new C05201();
                private final Runnable mReconnectRunnable = new RunnableC05212();

/* 34 */        static /* synthetic */ void access$000(RealTimeChatService x0) {
/* 35 */            x0.removeReconnectCallback();
                }

/* 34 */        static /* synthetic */ void access$100(RealTimeChatService x0, int x1, Bundle x2) {
/* 35 */            x0.broadcastEvent(x1, x2);
                }

/* 34 */        static /* synthetic */ long access$200(RealTimeChatService x0) {
/* 35 */            return x0.mReconnectDelay;
                }

/* 34 */        static /* synthetic */ long access$202(RealTimeChatService x0, long x1) {
/* 35 */            x0.mReconnectDelay = x1;
                    return x1;
                }

/* 34 */        static /* synthetic */ WebSocketClient access$300(RealTimeChatService x0) {
/* 35 */            return x0.mClient;
                }

/* 34 */        static /* synthetic */ boolean access$400(RealTimeChatService x0) {
/* 35 */            return x0.clientIsActive();
                }

/* 34 */        static /* synthetic */ Runnable access$500(RealTimeChatService x0) {
/* 35 */            return x0.mReconnectRunnable;
                }

/* 34 */        static /* synthetic */ Handler access$600(RealTimeChatService x0) {
/* 35 */            return x0.mMainHandler;
                }

/* 34 */        static /* synthetic */ ExecutorService access$700(RealTimeChatService x0) {
/* 35 */            return x0.mExecutor;
                }

/* 34 */        static /* synthetic */ void access$800(RealTimeChatService x0, int x1, Bundle x2) {
/* 35 */            x0.executeAction(x1, x2);
                }

                @Override // android.app.Service
/* 74 */        public void onCreate() {
/* 75 */            super.onCreate();
/* 76 */            this.mMainHandler = new Handler(Looper.getMainLooper());
/* 77 */            this.mExecutor = Executors.newCachedThreadPool();
/* 78 */            this.mMessenger = new Messenger(new VineServiceHandler());
/* 79 */            this.mRtcUrl = VineAPI.getInstance(this).getRtcUrl();
                }

                @Override // android.app.Service
/* 83 */        public int onStartCommand(Intent intent, int flags, int startId) {
/* 84 */            return 2;
                }

/* 87 */        private synchronized void disconnect() {
                    try {
                        WebSocketClient client = this.mClient;
/* 89 */                if (client != null) {
/* 90 */                    client.disconnect();
/* 91 */                    this.mClient = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.app.Service
/* 96 */        public IBinder onBind(Intent intent) {
/* 97 */            SLog.dWithTag(TAG, "rtc service was bound");
/* 98 */            return this.mMessenger.getBinder();
                }

/* 101 */       public synchronized void prepareClient(String sessionKey) {
                    try {
                        if (!clientIsActive()) {
/* 103 */                   SLog.m447d(TAG, "preparing client now");
/* 104 */                   VineAPI api = VineAPI.getInstance(this);
/* 105 */                   URI uri = URI.create(this.mRtcUrl);
/* 106 */                   List<BasicNameValuePair> extraHeaders = Arrays.asList(new BasicNameValuePair("vine-session-id", sessionKey), new BasicNameValuePair(VineAPI.X_VINE_CLIENT, api.getVineClientHeader()));
/* 110 */                   SLog.dWithTag(TAG, "Creating client: sessionKey=" + sessionKey + ", uri=" + uri.toString());
/* 111 */                   WebSocketClient client = new WebSocketClient(uri, this.mWebSocketListener, extraHeaders);
/* 112 */                   this.mClient = client;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.service.RealTimeChatService$1 */
/* 114 */       class C05201 implements WebSocketClient.Listener {
/* 115 */           C05201() {
                    }

                    @Override // com.codebutler.android_websockets.WebSocketClient.Listener
/* 118 */           public void onConnect() {
/* 119 */               SLog.dWithTag(RealTimeChatService.TAG, "Connected - now ready to subscribe to conversations.");
/* 120 */               RealTimeChatService.access$000(RealTimeChatService.this);
/* 121 */               RealTimeChatService.access$100(RealTimeChatService.this, 1, null);
                    }

                    @Override // com.codebutler.android_websockets.WebSocketClient.Listener
/* 125 */           public void onMessage(String message) {
/* 126 */               SLog.dWithTag(RealTimeChatService.TAG, String.format("Got string message: %s", message));
                        try {
/* 129 */                   JsonParser p = VineParsers.createParser(message);
/* 130 */                   ArrayList<VineRTCConversation> data = VineParsers.parseRTCEvent(p);
/* 131 */                   Bundle b = new Bundle();
/* 132 */                   b.putParcelableArrayList("data", data);
/* 133 */                   RealTimeChatService.access$100(RealTimeChatService.this, 9, b);
                        } catch (IOException e) {
/* 135 */                   SLog.m459e("Failed to parse message.", (Throwable) e);
                        }
                    }

                    @Override // com.codebutler.android_websockets.WebSocketClient.Listener
/* 140 */           public void onDisconnect(int code, String reason) {
/* 141 */               SLog.dWithTag(RealTimeChatService.TAG, String.format("Disconnected with code=%d, reason=%s, delay=%dms", Integer.valueOf(code), reason, Long.valueOf(RealTimeChatService.access$200(RealTimeChatService.this))));
/* 143 */               Bundle b = new Bundle();
/* 144 */               b.putInt("code", code);
/* 145 */               b.putString(RealTimeChatService.EXTRA_REASON, reason);
/* 146 */               RealTimeChatService.access$100(RealTimeChatService.this, 5, b);
                    }

                    @Override // com.codebutler.android_websockets.WebSocketClient.Listener
/* 150 */           public void onError(Exception error) {
/* 151 */               SLog.dWithTag(RealTimeChatService.TAG, "Error: " + error.getMessage());
                    }

                    @Override // com.codebutler.android_websockets.WebSocketClient.Listener
/* 155 */           public void onMessage(byte[] data) {
                    }
                }

/* 159 */       private synchronized void broadcastEvent(int actionCode, Bundle extras) {
                    try {
                        Intent intent = new Intent(ACTION_WEBSOCKET_EVENT);
/* 161 */               if (extras != null) {
                            intent.putExtras(extras);
                        }
/* 162 */               intent.putExtra("action_code", actionCode);
/* 163 */               sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.service.RealTimeChatService$2 */
/* 164 */       class RunnableC05212 implements Runnable {
/* 165 */           RunnableC05212() {
                    }

                    @Override // java.lang.Runnable
/* 168 */           public void run() {
/* 169 */               if (RealTimeChatService.access$300(RealTimeChatService.this) != null && !RealTimeChatService.access$400(RealTimeChatService.this)) {
/* 170 */                   RealTimeChatService.access$300(RealTimeChatService.this).connect();
/* 171 */                   RealTimeChatService.access$202(RealTimeChatService.this, RealTimeChatService.access$200(RealTimeChatService.this) < 16000 ? RealTimeChatService.access$200(RealTimeChatService.this) * 2 : RealTimeChatService.access$200(RealTimeChatService.this));
/* 173 */                   RealTimeChatService.access$600(RealTimeChatService.this).postDelayed(RealTimeChatService.access$500(RealTimeChatService.this), RealTimeChatService.access$200(RealTimeChatService.this));
/* 174 */                   SLog.dWithTag(RealTimeChatService.TAG, "Posted reconnect with delay=" + RealTimeChatService.access$200(RealTimeChatService.this) + "ms");
                        }
                    }
                }

/* 179 */       private synchronized boolean clientIsActive() {
                    WebSocketClient client;
                    try {
                        client = this.mClient;
                    } catch (Throwable th) {
                        throw th;
                    }
/* 181 */           return client != null && client.isConnected();
                }

/* 184 */       private synchronized String getPayload(Long conversationId, Conversation conversation) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return VineWebSocketClient.getPayload(conversationId, conversation);
                }

                @Override // android.app.Service
/* 189 */       public void onDestroy() {
/* 190 */           super.onDestroy();
/* 191 */           removeReconnectCallback();
/* 192 */           disconnect();
                }

/* 196 */       class VineServiceHandler extends Handler {
/* 197 */           VineServiceHandler() {
                    }

                    @Override // android.os.Handler
/* 200 */           public void handleMessage(Message msg) {
/* 201 */               msg.getData().setClassLoader(RealTimeChatService.this.getClassLoader());
/* 202 */               RealTimeChatService.access$700(RealTimeChatService.this).execute(RealTimeChatService.this.new ExecutionRunnable(msg.arg1, msg.getData(), msg.replyTo));
                    }
                }

/* 209 */       private class ExecutionRunnable implements Runnable {
                    private final int mActionCode;
                    private final Bundle mBundle;
                    private final Messenger mResponder;

/* 210 */           public ExecutionRunnable(int actionCode, Bundle bundle, Messenger messenger) {
/* 212 */               this.mActionCode = actionCode;
/* 213 */               this.mBundle = bundle;
/* 214 */               this.mResponder = messenger;
                    }

                    @Override // java.lang.Runnable
/* 217 */           public void run() {
/* 218 */               Process.setThreadPriority(10);
/* 219 */               RealTimeChatService.access$800(RealTimeChatService.this, this.mActionCode, this.mBundle);
/* 222 */               if (this.mBundle.getBoolean(RealTimeChatService.EXTRA_RESPOND, true)) {
/* 223 */                   Message msg = Message.obtain();
/* 224 */                   msg.what = this.mActionCode;
/* 225 */                   msg.setData(this.mBundle);
                            try {
/* 227 */                       this.mResponder.send(msg);
                            } catch (Exception e) {
/* 229 */                       CrashUtil.logException(e, "Error sending service response", new Object[0]);
                            }
                        }
                    }
                }

/* 236 */       private void send(String payload) {
                    try {
/* 237 */               WebSocketClient client = this.mClient;
/* 238 */               if (client != null && clientIsActive()) {
/* 239 */                   client.send(payload);
                        }
                    } catch (IllegalStateException e) {
/* 242 */               CrashUtil.log(e.getMessage());
                    }
                }

/* 246 */       private void removeReconnectCallback() {
/* 247 */           this.mMainHandler.removeCallbacks(this.mReconnectRunnable);
                }

/* 250 */       private void executeAction(int actionCode, Bundle b) {
                    switch (actionCode) {
                        case 1:
/* 253 */                   String sessionKey = b.getString("s_key");
/* 254 */                   removeReconnectCallback();
/* 255 */                   prepareClient(sessionKey);
/* 256 */                   if (!this.mClient.isConnected()) {
/* 257 */                       this.mClient.connect();
                            }
                            break;
                        case 2:
/* 262 */                   String sessionKey2 = b.getString("s_key");
/* 263 */                   prepareClient(sessionKey2);
/* 264 */                   removeReconnectCallback();
/* 266 */                   this.mReconnectDelay = 1000L;
/* 267 */                   this.mMainHandler.postDelayed(this.mReconnectRunnable, this.mReconnectDelay);
                            break;
                        case 3:
/* 271 */                   Long conversationId = Long.valueOf(b.getLong("conversation_id"));
/* 272 */                   Conversation conversation = new Conversation.Builder().setConnected().build();
/* 275 */                   send(getPayload(conversationId, conversation));
                            break;
                        case 4:
/* 279 */                   Long conversationId2 = Long.valueOf(b.getLong("conversation_id"));
/* 280 */                   Conversation conversation2 = new Conversation.Builder().build();
/* 281 */                   send(getPayload(conversationId2, conversation2));
                            break;
                        case 5:
/* 285 */                   disconnect();
                            break;
                        case 7:
/* 289 */                   boolean isTyping = b.getBoolean(EXTRA_IS_TYPING);
/* 290 */                   Long conversationId3 = Long.valueOf(b.getLong("conversation_id"));
/* 291 */                   Conversation conversation3 = new Conversation.Builder().setConnected().setTyping(isTyping).build();
/* 295 */                   send(getPayload(conversationId3, conversation3));
                            break;
                        case 8:
/* 299 */                   long conversationId4 = b.getLong("conversation_id");
/* 300 */                   long lastMessageId = b.getLong("last_message_id");
/* 301 */                   Conversation conversation4 = new Conversation.Builder().setConnected().setLastMessageId(lastMessageId).build();
/* 305 */                   send(getPayload(Long.valueOf(conversationId4), conversation4));
                            break;
                    }
                }
            }
