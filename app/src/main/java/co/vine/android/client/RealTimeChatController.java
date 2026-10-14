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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public class RealTimeChatController implements VineServiceConnection.ServiceResponseHandler {
                private static final String TAG = "RTCController";
                private static RealTimeChatController sInstance;
                private final Context mContext;
                private ArrayList<AppSessionListener> mListeners;
                private final VineServiceConnection mServiceConnection;

/* 30 */        public static RealTimeChatController getInstance(Context context) {
/* 31 */            if (sInstance == null) {
/* 32 */                sInstance = new RealTimeChatController(context.getApplicationContext());
                    }
/* 34 */            return sInstance;
                }

/* 36 */        private RealTimeChatController(Context context) {
/* 38 */            this.mServiceConnection = new VineServiceConnection(context, this);
/* 39 */            context.bindService(new Intent(context, (Class<?>) RealTimeChatService.class), this.mServiceConnection, 1);
/* 41 */            this.mListeners = new ArrayList<>();
/* 42 */            this.mContext = context;
                }

/* 45 */        public String connectChatService() {
/* 46 */            Bundle b = createServiceBundle();
/* 48 */            b.putBoolean(RealTimeChatService.EXTRA_RESPOND, false);
/* 49 */            return executeServiceAction(1, b);
                }

/* 52 */        public String reconnectChatService() {
/* 53 */            Bundle b = createServiceBundle();
/* 54 */            return executeServiceAction(2, b);
                }

/* 57 */        public void disconnect() {
/* 58 */            SLog.dWithTag(TAG, "RTCController disconnect() hit");
/* 59 */            Bundle b = createServiceBundle();
/* 60 */            b.putBoolean(RealTimeChatService.EXTRA_RESPOND, false);
/* 61 */            executeServiceAction(5, b);
                }

/* 64 */        public String subscribeConversation(long conversationRemoteId) {
/* 65 */            Bundle b = new Bundle();
/* 66 */            b.putLong("conversation_id", conversationRemoteId);
/* 67 */            return executeServiceAction(3, b);
                }

/* 70 */        public String updateTypingState(long conversationId, boolean isTyping) {
/* 71 */            Bundle b = new Bundle();
/* 72 */            b.putLong("conversation_id", conversationId);
/* 73 */            b.putBoolean(RealTimeChatService.EXTRA_IS_TYPING, isTyping);
/* 74 */            return executeServiceAction(7, b);
                }

/* 77 */        public String notifyNewPrivateMessage(long conversationId, long lastMessageId) {
/* 78 */            Bundle b = new Bundle();
/* 79 */            b.putLong("conversation_id", conversationId);
/* 80 */            b.putLong("last_message_id", lastMessageId);
/* 81 */            return executeServiceAction(8, b);
                }

/* 84 */        private Bundle createServiceBundle() {
/* 85 */            Bundle b = new Bundle();
                    try {
/* 87 */                return injectServiceBundle(b, getActiveSession(true));
                    } catch (Exception e) {
/* 89 */                CrashUtil.log("Failed to get active session.");
/* 90 */                return b;
                    }
                }

/* 94 */        private Bundle injectServiceBundle(Bundle bundle, Session session) {
/* 95 */            bundle.putString("s_key", session.getSessionKey());
/* 96 */            bundle.putLong(VineService.EXTRA_SESSION_OWNER_ID, session.getUserId());
/* 97 */            bundle.putString(VineService.EXTRA_ACCOUNT_NAME, session.getName());
/* 98 */            bundle.putString("email", session.getLoginEmail());
/* 99 */            return bundle;
                }

/* 102 */       private Session getActiveSession(boolean readOnly) {
/* 103 */           return VineAccountHelper.getActiveSession(this.mContext, readOnly);
                }

/* 106 */       public void addListener(AppSessionListener listener) {
/* 107 */           if (!this.mListeners.contains(listener)) {
/* 110 */               this.mListeners.add(listener);
                    }
                }

/* 113 */       public void removeListener(AppSessionListener listener) {
/* 114 */           this.mListeners.remove(listener);
                }

                @Override // co.vine.android.service.VineServiceConnection.ServiceResponseHandler
/* 118 */       public void handleServiceResponse(int actionCode, int responseCode, String reasonPhrase, Bundle b) {
/* 119 */           String reqId = b.getString(VineService.EXTRA_REQUEST_ID);
/* 120 */           this.mServiceConnection.remove(reqId);
/* 121 */           notifyListeners(actionCode, b);
                }

/* 124 */       public void notifyListeners(int actionCode, Bundle b) {
                    switch (actionCode) {
                        case 1:
                            for (AppSessionListener listener : this.mListeners) {
/* 128 */                       listener.onWebSocketConnectComplete();
                            }
                            break;
                        case 3:
                            for (AppSessionListener listener2 : this.mListeners) {
/* 134 */                       listener2.onSubscribeConversationComplete();
                            }
                            break;
                        case 5:
                            for (AppSessionListener listener3 : this.mListeners) {
/* 147 */                       listener3.onWebSocketDisconnected();
                            }
                            break;
                        case 6:
                            for (AppSessionListener listener4 : this.mListeners) {
/* 153 */                       listener4.onWebSocketError();
                            }
                            break;
                        case 7:
/* 139 */                   boolean isTyping = b.getBoolean(RealTimeChatService.EXTRA_IS_TYPING);
                            for (AppSessionListener listener5 : this.mListeners) {
/* 141 */                       listener5.onUpdateTypingStateComplete(isTyping);
                            }
                            break;
                        case 9:
/* 158 */                   ArrayList<VineRTCConversation> data = b.getParcelableArrayList("data");
                            for (AppSessionListener listener6 : this.mListeners) {
/* 160 */                       if (data != null) {
/* 161 */                           listener6.onReceiveRTCMessage(data);
                                }
                            }
                            break;
                    }
                }

/* 169 */       private String executeServiceAction(int actionCode, Bundle bundle) {
/* 170 */           bundle.putInt(RealTimeChatService.EXTRA_PID, Process.myPid());
/* 171 */           return this.mServiceConnection.queueAndExecute(actionCode, bundle);
                }
            }
