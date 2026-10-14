            package co.vine.android;

            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 9 */     public class Conversation extends JSONObject {
/* 10 */        public Conversation(Builder b) throws JSONException {
/* 12 */            put("connected", Builder.access$000(b));
/* 13 */            put(VineWebSocketClient.PARAM_TYPING, Builder.access$100(b));
                    if (Builder.access$200(b) > 0) {
/* 15 */                put("last_message_id", Builder.access$200(b));
                    }
                }

/* 17 */        public static class Builder {
                    private boolean connected = false;
                    private boolean typing = false;
                    private long lastMessageId = 0;

/* 18 */            static /* synthetic */ boolean access$000(Builder x0) {
/* 19 */                return x0.connected;
                    }

/* 18 */            static /* synthetic */ boolean access$100(Builder x0) {
/* 19 */                return x0.typing;
                    }

/* 18 */            static /* synthetic */ long access$200(Builder x0) {
/* 19 */                return x0.lastMessageId;
                    }

/* 30 */            public Builder setConnected() {
/* 31 */                this.connected = true;
/* 32 */                return this;
                    }

/* 35 */            public Builder setTyping(boolean isTyping) {
/* 36 */                this.typing = isTyping;
/* 37 */                return this;
                    }

/* 40 */            public Builder setLastMessageId(long lastMessageId) {
/* 41 */                this.lastMessageId = lastMessageId;
/* 42 */                return this;
                    }

/* 46 */            public Conversation build() {
                        try {
/* 47 */                    return new Conversation(this);
                        } catch (JSONException e) {
/* 49 */                    throw new RuntimeException(e);
                        }
                    }
                }
            }
