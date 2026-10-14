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
            
            
    public class RealTimeChatService extends Service {
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

        static  void access$000(RealTimeChatService x0) {
            x0.removeReconnectCallback();
                }

        static  void access$100(RealTimeChatService x0, int x1, Bundle x2) {
            x0.broadcastEvent(x1, x2);
                }

        static  long access$200(RealTimeChatService x0) {
            return x0.mReconnectDelay;
                }

        static  long access$202(RealTimeChatService x0, long x1) {
            x0.mReconnectDelay = x1;
                    return x1;
                }

        static  WebSocketClient access$300(RealTimeChatService x0) {
            return x0.mClient;
                }

        static  boolean access$400(RealTimeChatService x0) {
            return x0.clientIsActive();
                }

        static  Runnable access$500(RealTimeChatService x0) {
            return x0.mReconnectRunnable;
                }

        static  Handler access$600(RealTimeChatService x0) {
            return x0.mMainHandler;
                }

        static  ExecutorService access$700(RealTimeChatService x0) {
            return x0.mExecutor;
                }

        static  void access$800(RealTimeChatService x0, int x1, Bundle x2) {
            x0.executeAction(x1, x2);
                }

                @Override 
        public void onCreate() {
            super.onCreate();
            this.mMainHandler = new Handler(Looper.getMainLooper());
            this.mExecutor = Executors.newCachedThreadPool();
            this.mMessenger = new Messenger(new VineServiceHandler());
            this.mRtcUrl = VineAPI.getInstance(this).getRtcUrl();
                }

                @Override 
        public int onStartCommand(Intent intent, int flags, int startId) {
            return 2;
                }

        private synchronized void disconnect() {
                    try {
                        WebSocketClient client = this.mClient;
                if (client != null) {
                    client.disconnect();
                    this.mClient = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
        public IBinder onBind(Intent intent) {
            SLog.dWithTag(TAG, "rtc service was bound");
            return this.mMessenger.getBinder();
                }

       public synchronized void prepareClient(String sessionKey) {
                    try {
                        if (!clientIsActive()) {
                   SLog.m447d(TAG, "preparing client now");
                   VineAPI api = VineAPI.getInstance(this);
                   URI uri = URI.create(this.mRtcUrl);
                   List<BasicNameValuePair> extraHeaders = Arrays.asList(new BasicNameValuePair("vine-session-id", sessionKey), new BasicNameValuePair(VineAPI.X_VINE_CLIENT, api.getVineClientHeader()));
                   SLog.dWithTag(TAG, "Creating client: sessionKey=" + sessionKey + ", uri=" + uri.toString());
                   WebSocketClient client = new WebSocketClient(uri, this.mWebSocketListener, extraHeaders);
                   this.mClient = client;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       class C05201 implements WebSocketClient.Listener {
           C05201() {
                    }

                    @Override 
           public void onConnect() {
               SLog.dWithTag(RealTimeChatService.TAG, "Connected - now ready to subscribe to conversations.");
               RealTimeChatService.access$000(RealTimeChatService.this);
               RealTimeChatService.access$100(RealTimeChatService.this, 1, null);
                    }

                    @Override 
           public void onMessage(String message) {
               SLog.dWithTag(RealTimeChatService.TAG, String.format("Got string message: %s", message));
                        try {
                   JsonParser p = VineParsers.createParser(message);
                   ArrayList<VineRTCConversation> data = VineParsers.parseRTCEvent(p);
                   Bundle b = new Bundle();
                   b.putParcelableArrayList("data", data);
                   RealTimeChatService.access$100(RealTimeChatService.this, 9, b);
                        } catch (IOException e) {
                   SLog.m459e("Failed to parse message.", (Throwable) e);
                        }
                    }

                    @Override 
           public void onDisconnect(int code, String reason) {
               SLog.dWithTag(RealTimeChatService.TAG, String.format("Disconnected with code=%d, reason=%s, delay=%dms", Integer.valueOf(code), reason, Long.valueOf(RealTimeChatService.access$200(RealTimeChatService.this))));
               Bundle b = new Bundle();
               b.putInt("code", code);
               b.putString(RealTimeChatService.EXTRA_REASON, reason);
               RealTimeChatService.access$100(RealTimeChatService.this, 5, b);
                    }

                    @Override 
           public void onError(Exception error) {
               SLog.dWithTag(RealTimeChatService.TAG, "Error: " + error.getMessage());
                    }

                    @Override 
           public void onMessage(byte[] data) {
                    }
                }

       private synchronized void broadcastEvent(int actionCode, Bundle extras) {
                    try {
                        Intent intent = new Intent(ACTION_WEBSOCKET_EVENT);
               if (extras != null) {
                            intent.putExtras(extras);
                        }
               intent.putExtra("action_code", actionCode);
               sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
       class RunnableC05212 implements Runnable {
           RunnableC05212() {
                    }

                    @Override 
           public void run() {
               if (RealTimeChatService.access$300(RealTimeChatService.this) != null && !RealTimeChatService.access$400(RealTimeChatService.this)) {
                   RealTimeChatService.access$300(RealTimeChatService.this).connect();
                   RealTimeChatService.access$202(RealTimeChatService.this, RealTimeChatService.access$200(RealTimeChatService.this) < 16000 ? RealTimeChatService.access$200(RealTimeChatService.this) * 2 : RealTimeChatService.access$200(RealTimeChatService.this));
                   RealTimeChatService.access$600(RealTimeChatService.this).postDelayed(RealTimeChatService.access$500(RealTimeChatService.this), RealTimeChatService.access$200(RealTimeChatService.this));
                   SLog.dWithTag(RealTimeChatService.TAG, "Posted reconnect with delay=" + RealTimeChatService.access$200(RealTimeChatService.this) + "ms");
                        }
                    }
                }

       private synchronized boolean clientIsActive() {
                    WebSocketClient client;
                    try {
                        client = this.mClient;
                    } catch (Throwable th) {
                        throw th;
                    }
           return client != null && client.isConnected();
                }

       private synchronized String getPayload(Long conversationId, Conversation conversation) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return VineWebSocketClient.getPayload(conversationId, conversation);
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           removeReconnectCallback();
           disconnect();
                }

       class VineServiceHandler extends Handler {
           VineServiceHandler() {
                    }

                    @Override 
           public void handleMessage(Message msg) {
               msg.getData().setClassLoader(RealTimeChatService.this.getClassLoader());
               RealTimeChatService.access$700(RealTimeChatService.this).execute(RealTimeChatService.this.new ExecutionRunnable(msg.arg1, msg.getData(), msg.replyTo));
                    }
                }

       private class ExecutionRunnable implements Runnable {
                    private final int mActionCode;
                    private final Bundle mBundle;
                    private final Messenger mResponder;

           public ExecutionRunnable(int actionCode, Bundle bundle, Messenger messenger) {
               this.mActionCode = actionCode;
               this.mBundle = bundle;
               this.mResponder = messenger;
                    }

                    @Override 
           public void run() {
               Process.setThreadPriority(10);
               RealTimeChatService.access$800(RealTimeChatService.this, this.mActionCode, this.mBundle);
               if (this.mBundle.getBoolean(RealTimeChatService.EXTRA_RESPOND, true)) {
                   Message msg = Message.obtain();
                   msg.what = this.mActionCode;
                   msg.setData(this.mBundle);
                            try {
                       this.mResponder.send(msg);
                            } catch (Exception e) {
                       CrashUtil.logException(e, "Error sending service response", new Object[0]);
                            }
                        }
                    }
                }

       private void send(String payload) {
                    try {
               WebSocketClient client = this.mClient;
               if (client != null && clientIsActive()) {
                   client.send(payload);
                        }
                    } catch (IllegalStateException e) {
               CrashUtil.log(e.getMessage());
                    }
                }

       private void removeReconnectCallback() {
           this.mMainHandler.removeCallbacks(this.mReconnectRunnable);
                }

       private void executeAction(int actionCode, Bundle b) {
                    switch (actionCode) {
                        case 1:
                   String sessionKey = b.getString("s_key");
                   removeReconnectCallback();
                   prepareClient(sessionKey);
                   if (!this.mClient.isConnected()) {
                       this.mClient.connect();
                            }
                            break;
                        case 2:
                   String sessionKey2 = b.getString("s_key");
                   prepareClient(sessionKey2);
                   removeReconnectCallback();
                   this.mReconnectDelay = 1000L;
                   this.mMainHandler.postDelayed(this.mReconnectRunnable, this.mReconnectDelay);
                            break;
                        case 3:
                   Long conversationId = Long.valueOf(b.getLong("conversation_id"));
                   Conversation conversation = new Conversation.Builder().setConnected().build();
                   send(getPayload(conversationId, conversation));
                            break;
                        case 4:
                   Long conversationId2 = Long.valueOf(b.getLong("conversation_id"));
                   Conversation conversation2 = new Conversation.Builder().build();
                   send(getPayload(conversationId2, conversation2));
                            break;
                        case 5:
                   disconnect();
                            break;
                        case 7:
                   boolean isTyping = b.getBoolean(EXTRA_IS_TYPING);
                   Long conversationId3 = Long.valueOf(b.getLong("conversation_id"));
                   Conversation conversation3 = new Conversation.Builder().setConnected().setTyping(isTyping).build();
                   send(getPayload(conversationId3, conversation3));
                            break;
                        case 8:
                   long conversationId4 = b.getLong("conversation_id");
                   long lastMessageId = b.getLong("last_message_id");
                   Conversation conversation4 = new Conversation.Builder().setConnected().setLastMessageId(lastMessageId).build();
                   send(getPayload(Long.valueOf(conversationId4), conversation4));
                            break;
                    }
                }
            }
