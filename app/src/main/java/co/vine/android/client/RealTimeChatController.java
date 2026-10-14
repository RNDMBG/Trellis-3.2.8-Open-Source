            package co.vine.android.client;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.os.Process;
            import co.vine.android.api.VineRTCConversation;
            import co.vine.android.service.RealTimeChatService;
            import co.vine.android.service.VineService;
            import co.vine.android.service.VineServiceConnection;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import java.util.ArrayList;
            
            
    public class RealTimeChatController implements VineServiceConnection.ServiceResponseHandler {
                private static final String TAG = "RTCController";
                private static RealTimeChatController sInstance;
                private final Context mContext;
                private ArrayList<AppSessionListener> mListeners;
                private final VineServiceConnection mServiceConnection;

        public static RealTimeChatController getInstance(Context context) {
            if (sInstance == null) {
                sInstance = new RealTimeChatController(context.getApplicationContext());
                    }
            return sInstance;
                }

        private RealTimeChatController(Context context) {
            this.mServiceConnection = new VineServiceConnection(context, this);
            context.bindService(new Intent(context, (Class<?>) RealTimeChatService.class), this.mServiceConnection, 1);
            this.mListeners = new ArrayList<>();
            this.mContext = context;
                }

        public String connectChatService() {
            Bundle b = createServiceBundle();
            b.putBoolean(RealTimeChatService.EXTRA_RESPOND, false);
            return executeServiceAction(1, b);
                }

        public String reconnectChatService() {
            Bundle b = createServiceBundle();
            return executeServiceAction(2, b);
                }

        public void disconnect() {
            SLog.dWithTag(TAG, "RTCController disconnect() hit");
            Bundle b = createServiceBundle();
            b.putBoolean(RealTimeChatService.EXTRA_RESPOND, false);
            executeServiceAction(5, b);
                }

        public String subscribeConversation(long conversationRemoteId) {
            Bundle b = new Bundle();
            b.putLong("conversation_id", conversationRemoteId);
            return executeServiceAction(3, b);
                }

        public String updateTypingState(long conversationId, boolean isTyping) {
            Bundle b = new Bundle();
            b.putLong("conversation_id", conversationId);
            b.putBoolean(RealTimeChatService.EXTRA_IS_TYPING, isTyping);
            return executeServiceAction(7, b);
                }

        public String notifyNewPrivateMessage(long conversationId, long lastMessageId) {
            Bundle b = new Bundle();
            b.putLong("conversation_id", conversationId);
            b.putLong("last_message_id", lastMessageId);
            return executeServiceAction(8, b);
                }

        private Bundle createServiceBundle() {
            Bundle b = new Bundle();
                    try {
                return injectServiceBundle(b, getActiveSession(true));
                    } catch (Exception e) {
                CrashUtil.log("Failed to get active session.");
                return b;
                    }
                }

        private Bundle injectServiceBundle(Bundle bundle, Session session) {
            bundle.putString("s_key", session.getSessionKey());
            bundle.putLong(VineService.EXTRA_SESSION_OWNER_ID, session.getUserId());
            bundle.putString(VineService.EXTRA_ACCOUNT_NAME, session.getName());
            bundle.putString("email", session.getLoginEmail());
            return bundle;
                }

       private Session getActiveSession(boolean readOnly) {
           return VineAccountHelper.getActiveSession(this.mContext, readOnly);
                }

       public void addListener(AppSessionListener listener) {
           if (!this.mListeners.contains(listener)) {
               this.mListeners.add(listener);
                    }
                }

       public void removeListener(AppSessionListener listener) {
           this.mListeners.remove(listener);
                }

                @Override 
       public void handleServiceResponse(int actionCode, int responseCode, String reasonPhrase, Bundle b) {
           String reqId = b.getString(VineService.EXTRA_REQUEST_ID);
           this.mServiceConnection.remove(reqId);
           notifyListeners(actionCode, b);
                }

       public void notifyListeners(int actionCode, Bundle b) {
                    switch (actionCode) {
                        case 1:
                            for (AppSessionListener listener : this.mListeners) {
                       listener.onWebSocketConnectComplete();
                            }
                            break;
                        case 3:
                            for (AppSessionListener listener2 : this.mListeners) {
                       listener2.onSubscribeConversationComplete();
                            }
                            break;
                        case 5:
                            for (AppSessionListener listener3 : this.mListeners) {
                       listener3.onWebSocketDisconnected();
                            }
                            break;
                        case 6:
                            for (AppSessionListener listener4 : this.mListeners) {
                       listener4.onWebSocketError();
                            }
                            break;
                        case 7:
                   boolean isTyping = b.getBoolean(RealTimeChatService.EXTRA_IS_TYPING);
                            for (AppSessionListener listener5 : this.mListeners) {
                       listener5.onUpdateTypingStateComplete(isTyping);
                            }
                            break;
                        case 9:
                   ArrayList<VineRTCConversation> data = b.getParcelableArrayList("data");
                            for (AppSessionListener listener6 : this.mListeners) {
                       if (data != null) {
                           listener6.onReceiveRTCMessage(data);
                                }
                            }
                            break;
                    }
                }

       private String executeServiceAction(int actionCode, Bundle bundle) {
           bundle.putInt(RealTimeChatService.EXTRA_PID, Process.myPid());
           return this.mServiceConnection.queueAndExecute(actionCode, bundle);
                }
            }
