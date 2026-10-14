            package co.vine.android;

            import org.json.JSONException;
            import org.json.JSONObject;
            
            
     public class Conversation extends JSONObject {
        public Conversation(Builder b) throws JSONException {
            put("connected", Builder.access$000(b));
            put(VineWebSocketClient.PARAM_TYPING, Builder.access$100(b));
                    if (Builder.access$200(b) > 0) {
                put("last_message_id", Builder.access$200(b));
                    }
                }

        public static class Builder {
                    private boolean connected = false;
                    private boolean typing = false;
                    private long lastMessageId = 0;

            static  boolean access$000(Builder x0) {
                return x0.connected;
                    }

            static  boolean access$100(Builder x0) {
                return x0.typing;
                    }

            static  long access$200(Builder x0) {
                return x0.lastMessageId;
                    }

            public Builder setConnected() {
                this.connected = true;
                return this;
                    }

            public Builder setTyping(boolean isTyping) {
                this.typing = isTyping;
                return this;
                    }

            public Builder setLastMessageId(long lastMessageId) {
                this.lastMessageId = lastMessageId;
                return this;
                    }

            public Conversation build() {
                        try {
                    return new Conversation(this);
                        } catch (JSONException e) {
                    throw new RuntimeException(e);
                        }
                    }
                }
            }
