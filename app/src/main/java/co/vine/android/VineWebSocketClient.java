            package co.vine.android;

            import com.codebutler.android_websockets.WebSocketClient;
            import com.edisonwang.android.slog.SLog;
            import java.net.URI;
            import java.util.List;
            import org.apache.http.message.BasicNameValuePair;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
    public class VineWebSocketClient extends WebSocketClient {
                public static final String PARAM_CONNECTED = "connected";
                public static final String PARAM_CONVERSATIONS = "conversations";
                public static final String PARAM_LAST_MESSAGE_ID = "last_message_id";
                public static final String PARAM_TYPING = "typing";
                private static final String TAG = "VineWebSocketClient;RTC";

        public VineWebSocketClient(URI uri, WebSocketClient.Listener listener, List<BasicNameValuePair> extraHeaders) {
            super(uri, listener, extraHeaders);
                }

        public static synchronized String getPayload(Long conversationId, Conversation conversation) {
                    String string;
                    try {
                        try {
                            JSONObject conversationsObject = new JSONObject();
                    conversationsObject.put(String.valueOf(conversationId), conversation);
                    JSONObject event = new JSONObject();
                    event.put("conversations", conversationsObject);
                    string = event.toString();
                        } catch (JSONException e) {
                    SLog.dWithTag(TAG, "RTC event converstaion = " + conversation);
                    string = "";
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return string;
                }

        public void subscribeConversation(long conversationId) {
            Conversation conversation = new Conversation.Builder().setConnected().build();
            sendPayload(getPayload(Long.valueOf(conversationId), conversation));
                }

        public void updateTypingState(long conversationId, boolean isTyping) {
            Conversation conversation = new Conversation.Builder().setConnected().setTyping(isTyping).build();
            sendPayload(getPayload(Long.valueOf(conversationId), conversation));
                }

        public void alertNewPrivateMessage(long conversationId, long lastMessageId) {
            Conversation conversation = new Conversation.Builder().setConnected().setLastMessageId(lastMessageId).setTyping(false).build();
            sendPayload(getPayload(Long.valueOf(conversationId), conversation));
                }

        private void sendPayload(String payload) {
                    try {
                if (isConnected()) {
                    send(payload);
                        }
                    } catch (IllegalStateException e) {
                    }
                }
            }
