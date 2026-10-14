            package co.vine.android;

            import android.app.Activity;
            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.SharedPreferences;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            import android.support.v4.app.Fragment;
            import android.text.TextUtils;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.View;
            import co.vine.android.api.VineParsers;
            import co.vine.android.api.VineRTCConversation;
            import co.vine.android.api.VineRTCParticipant;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.VineAPI;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSessionManager;
            import co.vine.android.recorder.RecordSessionVersion;
            import co.vine.android.recorder.RecordingActivityHelper;
            import co.vine.android.service.GCMNotificationService;
            import co.vine.android.service.RealTimeChatService;
            import co.vine.android.service.ResourceService;
            import co.vine.android.service.VineService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.IntentionalObjectCounter;
            import co.vine.android.util.MuteUtil;
            import co.vine.android.util.Util;
            import co.vine.android.widgets.PromptDialogFragment;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import com.codebutler.android_websockets.WebSocketClient;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.JsonParser;
            import com.googlecode.javacv.cpp.avformat;
            import java.io.IOException;
            import java.net.URI;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.List;
            import org.apache.http.message.BasicNameValuePair;
            
            
    public class ConversationActivity extends BaseControllerActionBarActivity {
                private static final int DIALOG_DELETE = 0;
                private static final int DIALOG_DELETE_MESSAGE = 2;
                private static final int DIALOG_IGNORE = 1;
                private static final String EVENT_SOURCE_TITLE = ConversationActivity.class.getName();
                public static final String EXTRA_AM_FOLLOWING_RECIPIENT = "am_following_recipient";
                public static final String EXTRA_CONVERSATION_ROW_ID = "conversation_row_id";
                private static final String EXTRA_IS_RECIPIENT_EXTERNAL = "is_recipient_external";
                public static final String EXTRA_KEYBOARD_UP = "keyboard_up";
                private static final String EXTRA_RECIPIENT_ID = "recipient_id";
                public static final String EXTRA_RECIPIENT_USERNAME = "recipient_username";
                private static final String FRAGMENT_TAG = "conversationFragment";
                private static final long INITIAL_RETRY_DELAY = 1000;
                private static final long INVALID_ID = -1;
                private static final int MAX_RETRY_DELAY = 16000;
                public static final int REQUEST_CONFIRMATION = 101;
                private static final String TAG = "ConvActivity;RTC";
                public boolean keyBoardUpOnStart;
                private boolean mAmFollowingRecipient;
                private RealTimeChatReceiver mChatReceiver;
                private VineWebSocketClient mClient;
                private long mConversationId;
                private long mConversationRowId;
                private long mDirectUserId;
                private boolean mIsDirectUserExternal;
                private boolean mLastIsConnected;
                private long mLastMessageId;
                private boolean mLastTyping;
                private Handler mMainHandler;
                private long mMessageIdToDelete;
                private SharedPreferences mPref;
                private String mRecipientUserName;
                private long mReconnectDelay;
                private Runnable mRetrySuccessRunnable;
                private RecordSessionVersion mVersion;
                private RecordingActivityHelper mHelper = new RecordingActivityHelper();
                private final IntentionalObjectCounter mIntentionalObjectCounter = new IntentionalObjectCounter(IntentionalObjectCounter.RECORDER_INSTANCE_COUNT, this);
                private final PromptDialogSupportFragment.OnDialogDoneListener mListener = new C01771();
                private BroadcastReceiver mConnectivityReceiver = new C01782();
                private WebSocketClient.Listener mWebSocketListener = new C01793();
                private final Runnable mReconnectRunnable = new RunnableC01804();

        static  long access$1000(ConversationActivity x0) {
            return x0.mReconnectDelay;
                }

        static  long access$1002(ConversationActivity x0, long x1) {
            x0.mReconnectDelay = x1;
                    return x1;
                }

        static  long access$1100(ConversationActivity x0) {
            return x0.mConversationId;
                }

        static  long access$1102(ConversationActivity x0, long x1) {
            x0.mConversationId = x1;
                    return x1;
                }

        static  boolean access$1200(ConversationActivity x0) {
            return x0.clientIsActive();
                }

        static  long access$1300(ConversationActivity x0) {
            return x0.mDirectUserId;
                }

        static  long access$1400(ConversationActivity x0) {
            return x0.mLastMessageId;
                }

        static  long access$1402(ConversationActivity x0, long x1) {
            x0.mLastMessageId = x1;
                    return x1;
                }

        static  VineWebSocketClient access$200(ConversationActivity x0) {
            return x0.mClient;
                }

        static  long access$300(ConversationActivity x0) {
            return x0.mConversationRowId;
                }

        static  long access$400(ConversationActivity x0) {
            return x0.mMessageIdToDelete;
                }

        static  boolean access$500(ConversationActivity x0) {
            return x0.mLastIsConnected;
                }

        static  boolean access$502(ConversationActivity x0, boolean x1) {
            x0.mLastIsConnected = x1;
                    return x1;
                }

        static  Runnable access$600(ConversationActivity x0) {
            return x0.mReconnectRunnable;
                }

        static  Handler access$700(ConversationActivity x0) {
            return x0.mMainHandler;
                }

        static  void access$800(ConversationActivity x0) {
            x0.reconnectWithBackoff();
                }

        static  void access$900(ConversationActivity x0) {
            x0.prepareClient();
                }

       public static Intent getIntent(Context context, long conversationObjectId, String recipientUsername, long recipientId, boolean isRecipientExternal, boolean followingThem, boolean keyboardUpOnStart) {
           Class conversationActivity = RecordConfigUtils.isCapableOfInline(context) ? ConversationActivity.class : ConversationActivityMain.class;
           Intent intent = new Intent(context, (Class<?>) conversationActivity);
           intent.setFlags(avformat.AVFMT_SEEK_TO_PTS);
           intent.putExtra("conversation_row_id", conversationObjectId);
           intent.putExtra("recipient_id", recipientId);
           intent.putExtra(EXTRA_IS_RECIPIENT_EXTERNAL, isRecipientExternal);
           intent.putExtra("am_following_recipient", followingThem);
           intent.putExtra(EXTRA_KEYBOARD_UP, keyboardUpOnStart);
           return intent;
                }

                @Override 
       public boolean isConversationSideMenuEnabled() {
           return true;
                }

       public void setRecipientUsername(String recipientUsername) {
           this.mRecipientUserName = recipientUsername;
           setupActionBar((Boolean) true, Boolean.valueOf(!TextUtils.isEmpty(this.mRecipientUserName)), this.mRecipientUserName, (Boolean) null, (Boolean) true);
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true, true);
           Intent intent = getIntent();
           this.mIntentionalObjectCounter.add();
           this.mConversationRowId = intent.getLongExtra("conversation_row_id", -1L);
           this.mDirectUserId = intent.getLongExtra("recipient_id", -1L);
           this.mIsDirectUserExternal = intent.getBooleanExtra(EXTRA_IS_RECIPIENT_EXTERNAL, false);
           this.mRecipientUserName = intent.getStringExtra("recipient_username");
           this.mAmFollowingRecipient = intent.getBooleanExtra("am_following_recipient", false);
           this.keyBoardUpOnStart = intent.getBooleanExtra(EXTRA_KEYBOARD_UP, false);
           this.mPref = Util.getDefaultSharedPrefs(this);
           if (StartActivity.ACTION_MESSAGE_NOTIFICATION_PRESSED.equals(intent.getAction())) {
               this.mAppController.clearPushNotifications(2);
                    }
                    if (this.mConversationRowId < 0 && this.mDirectUserId < 0) {
               restoreActivityState();
                    }
           this.mAppSessionListener = new ConversationSessionListener(this, null);
                    if (this.mConversationRowId >= 0 || this.mDirectUserId > 0) {
                    }
           this.mHelper.bindCameraService(this);
           this.mVersion = RecordSessionManager.getCurrentVersion(this);
           if (savedInstanceState == null) {
               ConversationFragment fragment = new ConversationFragment();
               Bundle b = ConversationFragment.prepareArguments(intent, false);
               b.putSerializable("arg_encoder_version", this.mVersion);
               b.putLong("conversation_row_id", this.mConversationRowId);
               fragment.setArguments(b);
               getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, FRAGMENT_TAG).commit();
                    }
           this.mMainHandler = new Handler(Looper.getMainLooper());
           this.mAppController.getConversationRemoteId(this.mConversationRowId);
           this.mChatReceiver = new RealTimeChatReceiver(this, null);
           this.mLastTyping = false;
                }

       private class RealTimeChatReceiver extends BroadcastReceiver {
           private RealTimeChatReceiver() {
                    }

            RealTimeChatReceiver(ConversationActivity x0, C01771 x1) {
               this();
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
               if (VineService.BC_ACTION_ALERT_RTC_NEW_MESSAGE.equals(intent.getAction())) {
                   long messageId = intent.getLongExtra("message_id", 0L);
                   long conversationId = intent.getLongExtra("conversation_id", 0L);
                            if (messageId > 0 && conversationId > 0 && ConversationActivity.access$200(ConversationActivity.this) != null) {
                       ConversationActivity.access$200(ConversationActivity.this).alertNewPrivateMessage(conversationId, messageId);
                            }
                        }
                    }
                }

                @Override 
       protected void onNewIntent(Intent intent) {
           super.onNewIntent(intent);
           this.mConversationRowId = intent.getLongExtra("conversation_row_id", -1L);
           this.mAmFollowingRecipient = intent.getBooleanExtra("am_following_recipient", false);
                    if (this.mConversationRowId >= 0 || this.mDirectUserId > 0) {
                    }
           this.mDirectUserId = intent.getLongExtra("recipient_id", -1L);
           this.mRecipientUserName = intent.getStringExtra("recipient_username");
           this.mAmFollowingRecipient = intent.getBooleanExtra("am_following_recipient", false);
           ConversationFragment fragment = new ConversationFragment();
           Bundle b = ConversationFragment.prepareArguments(intent, false);
           b.putSerializable("arg_encoder_version", this.mVersion);
           b.putLong("conversation_row_id", this.mConversationRowId);
           b.putBoolean("am_following_recipient", this.mAmFollowingRecipient);
           fragment.setArguments(b);
           getSupportFragmentManager().beginTransaction().replace(C0257R.id.fragment_container, fragment, FRAGMENT_TAG).commit();
                }

                @Override 
       public boolean onCreateOptionsMenu(Menu menu) {
           getMenuInflater().inflate(C0257R.menu.conversation, menu);
           if (this.mAmFollowingRecipient || this.mDirectUserId <= 0 || TextUtils.isEmpty(this.mRecipientUserName) || this.mIsDirectUserExternal) {
               menu.removeItem(C0257R.id.follow_user);
                    } else {
               MenuItem item = menu.findItem(C0257R.id.follow_user);
                        item.setTitle(String.format(getString(C0257R.string.follow_user_conversation), this.mRecipientUserName));
                    }
                    if (this.mDirectUserId > 0 && !TextUtils.isEmpty(this.mRecipientUserName) && !this.mIsDirectUserExternal) {
               MenuItem item2 = menu.findItem(C0257R.id.ignore);
                        item2.setTitle(String.format(getString(C0257R.string.ignore_user), this.mRecipientUserName));
               MenuItem item3 = menu.findItem(C0257R.id.go_to_user_profile);
                        item3.setTitle(String.format(getString(C0257R.string.go_to_profile_conversation), this.mRecipientUserName));
                    } else {
               menu.removeItem(C0257R.id.ignore);
               menu.removeItem(C0257R.id.go_to_user_profile);
                    }
           return super.onCreateOptionsMenu(menu);
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           int id = item.getItemId();
           if (id == 2131427937) {
               showIgnoreDialog();
                        return true;
                    }
           if (id == 2131427938) {
               showDeleteDialog();
                        return true;
                    }
           if (id == 16908332) {
               onBackPressed();
                        return true;
                    }
           if (id == 2131427936) {
               ProfileActivity.start(this, this.mDirectUserId, EVENT_SOURCE_TITLE);
                        return true;
                    }
           if (id == 2131427935) {
               this.mAppController.followUser(this.mAppController.getActiveSession(), this.mDirectUserId, true);
                        return true;
                    }
           return super.onOptionsItemSelected(item);
                }

       public void showDeleteDialog() {
           PromptDialogFragment p = PromptDialogFragment.newInstance(0);
           p.setListener(this.mListener);
           p.setMessage(C0257R.string.delete_conversation_prompt);
           p.setPositiveButton(C0257R.string.delete_yes);
           p.setNegativeButton(C0257R.string.cancel);
           p.show(getFragmentManager());
                }

       public void showIgnoreDialog() {
           PromptDialogFragment p = PromptDialogFragment.newInstance(1);
           p.setListener(this.mListener);
           p.setMessage(C0257R.string.ignore_conversation_prompt);
           p.setPositiveButton(C0257R.string.ignore_yes);
           p.setNegativeButton(C0257R.string.cancel);
           p.show(getFragmentManager());
                }

                
       class C01771 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C01771() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (id) {
                            case 0:
                                switch (which) {
                                    case -1:
                               ConversationActivity.this.mAppController.deleteConversation(ConversationActivity.access$300(ConversationActivity.this));
                               ConversationActivity.this.finish();
                                        break;
                                }
                                break;
                            case 1:
                                switch (which) {
                                    case -1:
                               ConversationActivity.this.mAppController.ignoreConversation(ConversationActivity.access$300(ConversationActivity.this));
                               ConversationActivity.this.finish();
                                        break;
                                }
                                break;
                            case 2:
                                switch (which) {
                                    case -1:
                               ConversationActivity.this.mAppController.deleteMessage(ConversationActivity.access$400(ConversationActivity.this));
                                        break;
                                }
                                break;
                        }
                    }
                }

                @Override 
       protected void onDestroy() {
           super.onDestroy();
                    try {
               this.mIntentionalObjectCounter.release();
               int count = this.mIntentionalObjectCounter.getCount();
               if (count == 0) {
                   CrashUtil.log("Clean up folders because we are the last one.");
                   this.mVersion.getManager(this).cleanUnusedFolders();
                        } else if (count > 1) {
                   CrashUtil.logException(new VineLoggingException("Double instance violation, but it's ok."));
                        }
                    } catch (IOException e) {
               CrashUtil.logException(e);
                    }
           this.mHelper.unBindCameraService(this);
                }

                @Override 
       public void onMenuClose() {
           if (!MuteUtil.isMuted(this)) {
               sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_UNMUTE), CrossConstants.BROADCAST_PERMISSION);
                    }
                }

                @Override 
       public void onMenuOpened() {
           if (!MuteUtil.isMuted(this)) {
               sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_MUTE), CrossConstants.BROADCAST_PERMISSION);
                    }
                }

                @Override 
       protected void restoreActivityState() {
           super.restoreActivityState();
           Intent intent = getIntent();
           if (intent != null) {
               String action = intent.getAction();
               if ("android.intent.action.MAIN".equals(action)) {
                   long[] states = getConversationActivityStates(this);
                   this.mConversationRowId = states[0];
                   this.mDirectUserId = states[1];
                        }
                    }
                }

       public static long[] getConversationActivityStates(Activity activity) {
           String data = Util.getDefaultSharedPrefs(activity).getString(Settings.PREF_SAVED_STATE_DATA, null);
           long[] state = {-1, -1};
           if (data != null) {
               int separator = data.indexOf(":");
                        try {
                   long conversationId = Long.valueOf(data.substring(0, separator)).longValue();
                            if (conversationId <= 0) {
                       conversationId = -1;
                            }
                   state[0] = conversationId;
                        } catch (NumberFormatException e) {
                   CrashUtil.logException(e, "Attempted to restore activity state with an invalid conversation id", new Object[0]);
                        }
                        try {
                   long directUserId = Long.valueOf(data.substring(separator + 1)).longValue();
                            if (directUserId <= 0) {
                       directUserId = -1;
                            }
                   state[1] = directUserId;
                        } catch (NumberFormatException e2) {
                   CrashUtil.logException(e2, "Attempted to restore activity state with an invalid direct user id", new Object[0]);
                        }
                    }
           return state;
                }

                @Override 
       protected void onStart() {
           super.onStart();
           ResourceService.isConversationActive = true;
                }

                @Override 
       protected void onResume() {
           super.onResume();
           ResourceService.lastActiveconversationRowId = this.mConversationRowId;
           IntentFilter chatFilter = new IntentFilter();
           chatFilter.addAction(RealTimeChatService.ACTION_WEBSOCKET_EVENT);
           chatFilter.addAction(VineService.BC_ACTION_ALERT_RTC_NEW_MESSAGE);
           registerReceiver(this.mChatReceiver, chatFilter, CrossConstants.BROADCAST_PERMISSION, null);
           startService(GCMNotificationService.getUpdateNotificationIntent(this, 2, this.mConversationRowId));
           IntentFilter connFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
           registerReceiver(this.mConnectivityReceiver, connFilter, CrossConstants.BROADCAST_PERMISSION, null);
           prepareClient();
           VineWebSocketClient client = this.mClient;
           if (client != null) {
               client.connect();
                    }
                }

                
       class C01782 extends BroadcastReceiver {
           C01782() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
                        try {
                   ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
                   NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                   boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
                   if (isConnected && ConversationActivity.access$500(ConversationActivity.this) != isConnected) {
                       SLog.dWithTag(ConversationActivity.TAG, "Connectivity change received, reconnecting");
                       ConversationActivity.access$700(ConversationActivity.this).removeCallbacks(ConversationActivity.access$600(ConversationActivity.this));
                       ConversationActivity.access$800(ConversationActivity.this);
                            }
                   ConversationActivity.access$502(ConversationActivity.this, isConnected);
                        } catch (SecurityException e) {
                   SLog.m459e("We are not gangsta enough to acess the connectivity state.", (Throwable) e);
                        }
                    }
                }

       public void onBackPressed(View v) {
                    try {
               onBackPressed();
                    } catch (IllegalStateException e) {
                    }
                }

                @Override 
       protected void onPause() {
           super.onPause();
                    try {
               unregisterReceiver(this.mChatReceiver);
                    } catch (IllegalArgumentException e) {
                    }
                    try {
               unregisterReceiver(this.mConnectivityReceiver);
                    } catch (IllegalArgumentException e2) {
                    }
           onTypingStatusChanged(false, false);
           disconnectClient();
                }

                @Override 
       protected void onStop() {
           super.onStop();
           ResourceService.isConversationActive = false;
                }

       public void onTypingStatusChanged(boolean isTyping, boolean isSending) {
                    if (this.mConversationId > 0 && clientIsActive() && this.mLastTyping != isTyping) {
               if (isTyping) {
                   this.mClient.updateTypingState(this.mConversationId, true);
                        } else if (!isSending) {
                   this.mClient.updateTypingState(this.mConversationId, false);
                        }
                    }
           this.mLastTyping = isTyping;
                }

       public RecordingActivityHelper getRecordingActivityHelper() {
           return this.mHelper;
                }

       public void showDeleteMessageDialog(long messageId) {
           this.mMessageIdToDelete = messageId;
           PromptDialogFragment p = PromptDialogFragment.newInstance(2);
           p.setListener(this.mListener);
           p.setMessage(C0257R.string.delete_message_prompt);
           p.setPositiveButton(C0257R.string.delete_yes);
           p.setNegativeButton(C0257R.string.cancel);
                    try {
               p.show(getFragmentManager());
                    } catch (IllegalStateException e) {
               CrashUtil.logException(e);
                    }
                }

                @Override 
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           if (requestCode == 101) {
               if (resultCode == 1527) {
                   this.mPref.edit().putBoolean(Settings.PREF_PHONE_VERIFIED, true).apply();
                   if (this.mRetrySuccessRunnable != null) {
                       this.mRetrySuccessRunnable.run();
                       return;
                            }
                   return;
                        }
               return;
                    }
           Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
           if (fragment instanceof ConversationFragment) {
               fragment.onActivityResult(requestCode - 65536, resultCode, data);
                    } else {
               super.onActivityResult(requestCode, resultCode, data);
                    }
                }

       public void startPhoneConfirmation(Runnable retrySuccessRunnable) {
           if (this.mPref.getBoolean(Settings.PREF_PHONE_VERIFIED, false)) {
               retrySuccessRunnable.run();
               return;
                    }
           this.mRetrySuccessRunnable = retrySuccessRunnable;
           String phone = this.mPref.getString(Settings.PREF_PHONE, "");
           startActivityForResult(ConfirmationFlowActivity.getIntent(this, phone, false), 101);
                }

       private synchronized boolean clientIsActive() {
                    VineWebSocketClient client;
                    try {
                        client = this.mClient;
                    } catch (Throwable th) {
                        throw th;
                    }
           return client != null && client.isConnected();
                }

       private void prepareClient() {
           VineWebSocketClient client = this.mClient;
           if (client == null || (!client.isConnecting() && !client.isConnected())) {
               SLog.m447d(TAG, "Preparing client now");
               VineAPI api = VineAPI.getInstance(this);
               URI uri = URI.create(BuildUtil.getRtcUrl(getResources()));
               String sessionKey = this.mAppController.getActiveSessionReadOnly().getSessionKey();
               List<BasicNameValuePair> extraHeaders = Arrays.asList(new BasicNameValuePair("vine-session-id", sessionKey), new BasicNameValuePair(VineAPI.X_VINE_CLIENT, api.getVineClientHeader()));
               SLog.dWithTag(TAG, "Creating client: sessionKey=" + sessionKey + ", uri=" + uri);
               this.mClient = new VineWebSocketClient(uri, this.mWebSocketListener, extraHeaders);
                    }
                }

       private void disconnectClient() {
           this.mMainHandler.removeCallbacks(this.mReconnectRunnable);
           WebSocketClient client = this.mClient;
           if (client != null) {
               client.disconnect();
               this.mClient = null;
                    }
                }

                
       class C01793 implements WebSocketClient.Listener {
           C01793() {
                    }

                    @Override 
           public void onConnect() {
               SLog.dWithTag(ConversationActivity.TAG, "Websocket connected");
               ConversationActivity.access$700(ConversationActivity.this).removeCallbacks(ConversationActivity.access$600(ConversationActivity.this));
               ConversationActivity.this.mAppSessionListener.onWebSocketConnectComplete();
                    }

                    @Override 
           public void onMessage(String message) {
               SLog.dWithTag(ConversationActivity.TAG, String.format("Got string message: %s", message));
                        try {
                   JsonParser p = VineParsers.createParser(message);
                   ArrayList<VineRTCConversation> data = VineParsers.parseRTCEvent(p);
                   if (data != null) {
                       ConversationActivity.this.mAppSessionListener.onReceiveRTCMessage(data);
                            }
                        } catch (IOException e) {
                   SLog.m459e("Failed to parse message.", (Throwable) e);
                        }
                    }

                    @Override 
           public void onMessage(byte[] data) {
                    }

                    @Override 
           public void onDisconnect(int code, String reason) {
               SLog.dWithTag(ConversationActivity.TAG, "Websocket disconnected, reason=" + reason);
               ConversationActivity.this.mAppSessionListener.onWebSocketDisconnected();
                    }

                    @Override 
           public void onError(Exception error) {
               SLog.dWithTag(ConversationActivity.TAG, "Websocket error=" + error.getMessage());
               ConversationActivity.this.mAppSessionListener.onWebSocketError();
                    }
                }

       private void reconnectWithBackoff() {
           this.mReconnectDelay = 1000L;
           this.mMainHandler.postDelayed(this.mReconnectRunnable, this.mReconnectDelay);
           SLog.dWithTag(TAG, "Posting reconnect runnable with delay=" + this.mReconnectDelay);
                }

                
       class RunnableC01804 implements Runnable {
           RunnableC01804() {
                    }

                    @Override 
           public void run() {
               if (ConversationActivity.access$200(ConversationActivity.this) == null) {
                   ConversationActivity.access$900(ConversationActivity.this);
                        }
               VineWebSocketClient client = ConversationActivity.access$200(ConversationActivity.this);
               if (client != null && !client.isConnecting() && !client.isConnected()) {
                   client.connect();
                   ConversationActivity.access$1002(ConversationActivity.this, ConversationActivity.access$1000(ConversationActivity.this) < 16000 ? ConversationActivity.access$1000(ConversationActivity.this) * 2 : ConversationActivity.access$1000(ConversationActivity.this));
                   ConversationActivity.access$700(ConversationActivity.this).postDelayed(ConversationActivity.access$600(ConversationActivity.this), ConversationActivity.access$1000(ConversationActivity.this));
                   SLog.dWithTag(ConversationActivity.TAG, "Posted reconnect with delay=" + ConversationActivity.access$1000(ConversationActivity.this) + "ms");
                        }
                    }
                }

       private class ConversationSessionListener extends AppSessionListener {
           private ConversationSessionListener() {
                    }

            ConversationSessionListener(ConversationActivity x0, C01771 x1) {
               this();
                    }

                    @Override 
           public void onGetConversationRemoteIdComplete(long conversationId) {
               ConversationActivity.access$1102(ConversationActivity.this, conversationId);
               if (ConversationActivity.access$1200(ConversationActivity.this)) {
                   SLog.dWithTag(ConversationActivity.TAG, "Subscribing to conversation=" + ConversationActivity.access$1100(ConversationActivity.this));
                   ConversationActivity.access$200(ConversationActivity.this).subscribeConversation(ConversationActivity.access$1100(ConversationActivity.this));
                        }
                    }

                    @Override 
           public void onWebSocketConnectComplete() {
                        if (ConversationActivity.access$1100(ConversationActivity.this) > 0) {
                   SLog.dWithTag(ConversationActivity.TAG, "Subscribing to conversation=" + ConversationActivity.access$1100(ConversationActivity.this));
                   ConversationActivity.access$200(ConversationActivity.this).subscribeConversation(ConversationActivity.access$1100(ConversationActivity.this));
                        }
                    }

                    @Override 
           public void onWebSocketDisconnected() {
               showTypingIndicator(false, -1L);
               ConversationActivity.access$800(ConversationActivity.this);
                    }

                    @Override 
           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
               if (statusCode == 200 && ConversationActivity.access$1300(ConversationActivity.this) == userId && ConversationActivity.this.mMenu != null) {
                   ConversationActivity.this.mMenu.removeItem(C0257R.id.follow_user);
                        }
                    }

                    @Override 
           public void onReceiveRTCMessage(ArrayList<VineRTCConversation> data) {
                        for (VineRTCConversation conversation : data) {
                            if (conversation.conversationId == ConversationActivity.access$1100(ConversationActivity.this)) {
                       ArrayList<VineRTCParticipant> participants = conversation.participants;
                                for (VineRTCParticipant participant : participants) {
                           long userId = participant.userId;
                           if (participant.isTyping) {
                               showTypingIndicator(true, userId);
                                    } else {
                               showTypingIndicator(false, userId);
                                    }
                           long lastMessageId = participant.lastMessageId;
                                    if (lastMessageId > 0) {
                                        if (lastMessageId > ConversationActivity.access$1400(ConversationActivity.this)) {
                                   ConversationActivity.this.mAppController.fetchConversation(1, false, 0L, ConversationActivity.access$300(ConversationActivity.this), false);
                                   Fragment fragment = ConversationActivity.this.getSupportFragmentManager().findFragmentByTag(ConversationActivity.FRAGMENT_TAG);
                                   if (fragment instanceof ConversationFragment) {
                                       ((ConversationFragment) fragment).onNewMessage();
                                            }
                                        }
                               ConversationActivity.access$1402(ConversationActivity.this, lastMessageId);
                                    }
                                }
                            }
                        }
                    }

                    
           class RunnableC01811 implements Runnable {
                        final  boolean val$show;
                        final  long val$userId;

               RunnableC01811(boolean z, long j) {
                            this.val$show = z;
                            this.val$userId = j;
                        }

                        @Override 
               public void run() {
                   Fragment fragment = ConversationActivity.this.getSupportFragmentManager().findFragmentByTag(ConversationActivity.FRAGMENT_TAG);
                   if (fragment instanceof ConversationFragment) {
                       ((ConversationFragment) fragment).showTypingIndicator(this.val$show, this.val$userId);
                            }
                        }
                    }

           private void showTypingIndicator(boolean show, long userId) {
               ConversationActivity.this.runOnUiThread(new RunnableC01811(show, userId));
                    }
                }
            }
