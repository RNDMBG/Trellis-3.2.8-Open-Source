            package co.vine.android;

            import com.codebutler.android_websockets.WebSocketClient;
            import com.edisonwang.android.slog.SLog;
            import java.net.URI;
            import java.util.List;
            import org.apache.http.message.BasicNameValuePair;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class VineWebSocketClient extends WebSocketClient {
                public static final String PARAM_CONNECTED = "connected";
                public static final String PARAM_CONVERSATIONS = "conversations";
                public static final String PARAM_LAST_MESSAGE_ID = "last_message_id";
                public static final String PARAM_TYPING = "typing";
                private static final String TAG = "VineWebSocketClient;RTC";

/* 25 */        public VineWebSocketClient(URI uri, WebSocketClient.Listener listener, List<BasicNameValuePair> extraHeaders) {
/* 26 */            super(uri, listener, extraHeaders);
                }

/* 30 */        public static synchronized String getPayload(Long conversationId, Conversation conversation) {
                    String string;
                    try {
                        try {
                            JSONObject conversationsObject = new JSONObject();
/* 32 */                    conversationsObject.put(String.valueOf(conversationId), conversation);
/* 33 */                    JSONObject event = new JSONObject();
/* 34 */                    event.put("conversations", conversationsObject);
/* 35 */                    string = event.toString();
                        } catch (JSONException e) {
/* 37 */                    SLog.dWithTag(TAG, "RTC event converstaion = " + conversation);
/* 38 */                    string = "";
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return string;
                }

/* 42 */        public void subscribeConversation(long conversationId) {
/* 43 */            Conversation conversation = new Conversation.Builder().setConnected().build();
/* 46 */            sendPayload(getPayload(Long.valueOf(conversationId), conversation));
                }

/* 49 */        public void updateTypingState(long conversationId, boolean isTyping) {
/* 50 */            Conversation conversation = new Conversation.Builder().setConnected().setTyping(isTyping).build();
/* 54 */            sendPayload(getPayload(Long.valueOf(conversationId), conversation));
                }

/* 57 */        public void alertNewPrivateMessage(long conversationId, long lastMessageId) {
/* 58 */            Conversation conversation = new Conversation.Builder().setConnected().setLastMessageId(lastMessageId).setTyping(false).build();
/* 63 */            sendPayload(getPayload(Long.valueOf(conversationId), conversation));
                }

/* 67 */        private void sendPayload(String payload) {
                    try {
/* 68 */                if (isConnected()) {
/* 69 */                    send(payload);
                        }
                    } catch (IllegalStateException e) {
                    }
                }
            }
