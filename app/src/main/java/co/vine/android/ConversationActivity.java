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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    public class ConversationActivity extends BaseControllerActionBarActivity {
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

/* 58 */        static /* synthetic */ long access$1000(ConversationActivity x0) {
/* 59 */            return x0.mReconnectDelay;
                }

/* 58 */        static /* synthetic */ long access$1002(ConversationActivity x0, long x1) {
/* 59 */            x0.mReconnectDelay = x1;
                    return x1;
                }

/* 58 */        static /* synthetic */ long access$1100(ConversationActivity x0) {
/* 59 */            return x0.mConversationId;
                }

/* 58 */        static /* synthetic */ long access$1102(ConversationActivity x0, long x1) {
/* 59 */            x0.mConversationId = x1;
                    return x1;
                }

/* 58 */        static /* synthetic */ boolean access$1200(ConversationActivity x0) {
/* 59 */            return x0.clientIsActive();
                }

/* 58 */        static /* synthetic */ long access$1300(ConversationActivity x0) {
/* 59 */            return x0.mDirectUserId;
                }

/* 58 */        static /* synthetic */ long access$1400(ConversationActivity x0) {
/* 59 */            return x0.mLastMessageId;
                }

/* 58 */        static /* synthetic */ long access$1402(ConversationActivity x0, long x1) {
/* 59 */            x0.mLastMessageId = x1;
                    return x1;
                }

/* 58 */        static /* synthetic */ VineWebSocketClient access$200(ConversationActivity x0) {
/* 59 */            return x0.mClient;
                }

/* 58 */        static /* synthetic */ long access$300(ConversationActivity x0) {
/* 59 */            return x0.mConversationRowId;
                }

/* 58 */        static /* synthetic */ long access$400(ConversationActivity x0) {
/* 59 */            return x0.mMessageIdToDelete;
                }

/* 58 */        static /* synthetic */ boolean access$500(ConversationActivity x0) {
/* 59 */            return x0.mLastIsConnected;
                }

/* 58 */        static /* synthetic */ boolean access$502(ConversationActivity x0, boolean x1) {
/* 59 */            x0.mLastIsConnected = x1;
                    return x1;
                }

/* 58 */        static /* synthetic */ Runnable access$600(ConversationActivity x0) {
/* 59 */            return x0.mReconnectRunnable;
                }

/* 58 */        static /* synthetic */ Handler access$700(ConversationActivity x0) {
/* 59 */            return x0.mMainHandler;
                }

/* 58 */        static /* synthetic */ void access$800(ConversationActivity x0) {
/* 59 */            x0.reconnectWithBackoff();
                }

/* 58 */        static /* synthetic */ void access$900(ConversationActivity x0) {
/* 59 */            x0.prepareClient();
                }

/* 107 */       public static Intent getIntent(Context context, long conversationObjectId, String recipientUsername, long recipientId, boolean isRecipientExternal, boolean followingThem, boolean keyboardUpOnStart) {
/* 108 */           Class conversationActivity = RecordConfigUtils.isCapableOfInline(context) ? ConversationActivity.class : ConversationActivityMain.class;
/* 109 */           Intent intent = new Intent(context, (Class<?>) conversationActivity);
/* 110 */           intent.setFlags(avformat.AVFMT_SEEK_TO_PTS);
/* 111 */           intent.putExtra("conversation_row_id", conversationObjectId);
/* 112 */           intent.putExtra("recipient_id", recipientId);
/* 113 */           intent.putExtra(EXTRA_IS_RECIPIENT_EXTERNAL, isRecipientExternal);
/* 114 */           intent.putExtra("am_following_recipient", followingThem);
/* 115 */           intent.putExtra(EXTRA_KEYBOARD_UP, keyboardUpOnStart);
/* 116 */           return intent;
                }

                @Override // co.vine.android.BaseControllerActionBarActivity
/* 120 */       public boolean isConversationSideMenuEnabled() {
/* 121 */           return true;
                }

/* 124 */       public void setRecipientUsername(String recipientUsername) {
/* 125 */           this.mRecipientUserName = recipientUsername;
/* 126 */           setupActionBar((Boolean) true, Boolean.valueOf(!TextUtils.isEmpty(this.mRecipientUserName)), this.mRecipientUserName, (Boolean) null, (Boolean) true);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 130 */       public void onCreate(Bundle savedInstanceState) {
/* 131 */           super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true, true);
/* 132 */           Intent intent = getIntent();
/* 133 */           this.mIntentionalObjectCounter.add();
/* 134 */           this.mConversationRowId = intent.getLongExtra("conversation_row_id", -1L);
/* 135 */           this.mDirectUserId = intent.getLongExtra("recipient_id", -1L);
/* 136 */           this.mIsDirectUserExternal = intent.getBooleanExtra(EXTRA_IS_RECIPIENT_EXTERNAL, false);
/* 138 */           this.mRecipientUserName = intent.getStringExtra("recipient_username");
/* 139 */           this.mAmFollowingRecipient = intent.getBooleanExtra("am_following_recipient", false);
/* 140 */           this.keyBoardUpOnStart = intent.getBooleanExtra(EXTRA_KEYBOARD_UP, false);
/* 141 */           this.mPref = Util.getDefaultSharedPrefs(this);
/* 143 */           if (StartActivity.ACTION_MESSAGE_NOTIFICATION_PRESSED.equals(intent.getAction())) {
/* 144 */               this.mAppController.clearPushNotifications(2);
                    }
                    if (this.mConversationRowId < 0 && this.mDirectUserId < 0) {
/* 148 */               restoreActivityState();
                    }
/* 151 */           this.mAppSessionListener = new ConversationSessionListener(this, null);
                    if (this.mConversationRowId >= 0 || this.mDirectUserId > 0) {
                    }
/* 157 */           this.mHelper.bindCameraService(this);
/* 159 */           this.mVersion = RecordSessionManager.getCurrentVersion(this);
/* 161 */           if (savedInstanceState == null) {
/* 162 */               ConversationFragment fragment = new ConversationFragment();
/* 163 */               Bundle b = ConversationFragment.prepareArguments(intent, false);
/* 164 */               b.putSerializable("arg_encoder_version", this.mVersion);
/* 165 */               b.putLong("conversation_row_id", this.mConversationRowId);
/* 166 */               fragment.setArguments(b);
/* 167 */               getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, FRAGMENT_TAG).commit();
                    }
/* 172 */           this.mMainHandler = new Handler(Looper.getMainLooper());
/* 173 */           this.mAppController.getConversationRemoteId(this.mConversationRowId);
/* 174 */           this.mChatReceiver = new RealTimeChatReceiver(this, null);
/* 175 */           this.mLastTyping = false;
                }

/* 176 */       private class RealTimeChatReceiver extends BroadcastReceiver {
/* 177 */           private RealTimeChatReceiver() {
                    }

/* 177 */           /* synthetic */ RealTimeChatReceiver(ConversationActivity x0, C01771 x1) {
/* 178 */               this();
                    }

                    @Override // android.content.BroadcastReceiver
/* 180 */           public void onReceive(Context context, Intent intent) {
/* 181 */               if (VineService.BC_ACTION_ALERT_RTC_NEW_MESSAGE.equals(intent.getAction())) {
/* 182 */                   long messageId = intent.getLongExtra("message_id", 0L);
/* 183 */                   long conversationId = intent.getLongExtra("conversation_id", 0L);
                            if (messageId > 0 && conversationId > 0 && ConversationActivity.access$200(ConversationActivity.this) != null) {
/* 185 */                       ConversationActivity.access$200(ConversationActivity.this).alertNewPrivateMessage(conversationId, messageId);
                            }
                        }
                    }
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 192 */       protected void onNewIntent(Intent intent) {
/* 193 */           super.onNewIntent(intent);
/* 194 */           this.mConversationRowId = intent.getLongExtra("conversation_row_id", -1L);
/* 195 */           this.mAmFollowingRecipient = intent.getBooleanExtra("am_following_recipient", false);
                    if (this.mConversationRowId >= 0 || this.mDirectUserId > 0) {
                    }
/* 200 */           this.mDirectUserId = intent.getLongExtra("recipient_id", -1L);
/* 201 */           this.mRecipientUserName = intent.getStringExtra("recipient_username");
/* 202 */           this.mAmFollowingRecipient = intent.getBooleanExtra("am_following_recipient", false);
/* 204 */           ConversationFragment fragment = new ConversationFragment();
/* 205 */           Bundle b = ConversationFragment.prepareArguments(intent, false);
/* 206 */           b.putSerializable("arg_encoder_version", this.mVersion);
/* 207 */           b.putLong("conversation_row_id", this.mConversationRowId);
/* 208 */           b.putBoolean("am_following_recipient", this.mAmFollowingRecipient);
/* 209 */           fragment.setArguments(b);
/* 210 */           getSupportFragmentManager().beginTransaction().replace(C0257R.id.fragment_container, fragment, FRAGMENT_TAG).commit();
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 217 */       public boolean onCreateOptionsMenu(Menu menu) {
/* 218 */           getMenuInflater().inflate(C0257R.menu.conversation, menu);
/* 220 */           if (this.mAmFollowingRecipient || this.mDirectUserId <= 0 || TextUtils.isEmpty(this.mRecipientUserName) || this.mIsDirectUserExternal) {
/* 221 */               menu.removeItem(C0257R.id.follow_user);
                    } else {
/* 223 */               MenuItem item = menu.findItem(C0257R.id.follow_user);
                        item.setTitle(String.format(getString(C0257R.string.follow_user_conversation), this.mRecipientUserName));
                    }
                    if (this.mDirectUserId > 0 && !TextUtils.isEmpty(this.mRecipientUserName) && !this.mIsDirectUserExternal) {
/* 228 */               MenuItem item2 = menu.findItem(C0257R.id.ignore);
                        item2.setTitle(String.format(getString(C0257R.string.ignore_user), this.mRecipientUserName));
/* 231 */               MenuItem item3 = menu.findItem(C0257R.id.go_to_user_profile);
                        item3.setTitle(String.format(getString(C0257R.string.go_to_profile_conversation), this.mRecipientUserName));
                    } else {
/* 234 */               menu.removeItem(C0257R.id.ignore);
/* 235 */               menu.removeItem(C0257R.id.go_to_user_profile);
                    }
/* 238 */           return super.onCreateOptionsMenu(menu);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 242 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 243 */           int id = item.getItemId();
/* 244 */           if (id == 2131427937) {
/* 245 */               showIgnoreDialog();
                        return true;
                    }
/* 247 */           if (id == 2131427938) {
/* 248 */               showDeleteDialog();
                        return true;
                    }
/* 250 */           if (id == 16908332) {
/* 251 */               onBackPressed();
                        return true;
                    }
/* 253 */           if (id == 2131427936) {
/* 254 */               ProfileActivity.start(this, this.mDirectUserId, EVENT_SOURCE_TITLE);
                        return true;
                    }
/* 256 */           if (id == 2131427935) {
/* 257 */               this.mAppController.followUser(this.mAppController.getActiveSession(), this.mDirectUserId, true);
                        return true;
                    }
/* 260 */           return super.onOptionsItemSelected(item);
                }

/* 264 */       public void showDeleteDialog() {
/* 265 */           PromptDialogFragment p = PromptDialogFragment.newInstance(0);
/* 267 */           p.setListener(this.mListener);
/* 268 */           p.setMessage(C0257R.string.delete_conversation_prompt);
/* 269 */           p.setPositiveButton(C0257R.string.delete_yes);
/* 270 */           p.setNegativeButton(C0257R.string.cancel);
/* 271 */           p.show(getFragmentManager());
                }

/* 274 */       public void showIgnoreDialog() {
/* 275 */           PromptDialogFragment p = PromptDialogFragment.newInstance(1);
/* 277 */           p.setListener(this.mListener);
/* 278 */           p.setMessage(C0257R.string.ignore_conversation_prompt);
/* 279 */           p.setPositiveButton(C0257R.string.ignore_yes);
/* 280 */           p.setNegativeButton(C0257R.string.cancel);
/* 281 */           p.show(getFragmentManager());
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationActivity$1 */
/* 282 */       class C01771 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 283 */           C01771() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 287 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (id) {
                            case 0:
                                switch (which) {
                                    case -1:
/* 292 */                               ConversationActivity.this.mAppController.deleteConversation(ConversationActivity.access$300(ConversationActivity.this));
/* 293 */                               ConversationActivity.this.finish();
                                        break;
                                }
                                break;
                            case 1:
                                switch (which) {
                                    case -1:
/* 302 */                               ConversationActivity.this.mAppController.ignoreConversation(ConversationActivity.access$300(ConversationActivity.this));
/* 303 */                               ConversationActivity.this.finish();
                                        break;
                                }
                                break;
                            case 2:
                                switch (which) {
                                    case -1:
/* 312 */                               ConversationActivity.this.mAppController.deleteMessage(ConversationActivity.access$400(ConversationActivity.this));
                                        break;
                                }
                                break;
                        }
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 323 */       protected void onDestroy() {
/* 324 */           super.onDestroy();
                    try {
/* 326 */               this.mIntentionalObjectCounter.release();
/* 327 */               int count = this.mIntentionalObjectCounter.getCount();
/* 328 */               if (count == 0) {
/* 329 */                   CrashUtil.log("Clean up folders because we are the last one.");
/* 330 */                   this.mVersion.getManager(this).cleanUnusedFolders();
                        } else if (count > 1) {
/* 333 */                   CrashUtil.logException(new VineLoggingException("Double instance violation, but it's ok."));
                        }
                    } catch (IOException e) {
/* 337 */               CrashUtil.logException(e);
                    }
/* 339 */           this.mHelper.unBindCameraService(this);
                }

                @Override // co.vine.android.BaseActionBarActivity, com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper.MenuStateHandler.MenuStateListener
/* 343 */       public void onMenuClose() {
/* 344 */           if (!MuteUtil.isMuted(this)) {
/* 345 */               sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_UNMUTE), CrossConstants.BROADCAST_PERMISSION);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper.MenuStateHandler.MenuStateListener
/* 350 */       public void onMenuOpened() {
/* 351 */           if (!MuteUtil.isMuted(this)) {
/* 352 */               sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_MUTE), CrossConstants.BROADCAST_PERMISSION);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity
/* 358 */       protected void restoreActivityState() {
/* 359 */           super.restoreActivityState();
/* 360 */           Intent intent = getIntent();
/* 361 */           if (intent != null) {
/* 362 */               String action = intent.getAction();
/* 363 */               if ("android.intent.action.MAIN".equals(action)) {
/* 364 */                   long[] states = getConversationActivityStates(this);
/* 365 */                   this.mConversationRowId = states[0];
/* 366 */                   this.mDirectUserId = states[1];
                        }
                    }
                }

/* 371 */       public static long[] getConversationActivityStates(Activity activity) {
/* 372 */           String data = Util.getDefaultSharedPrefs(activity).getString(Settings.PREF_SAVED_STATE_DATA, null);
/* 373 */           long[] state = {-1, -1};
/* 374 */           if (data != null) {
/* 375 */               int separator = data.indexOf(":");
                        try {
/* 377 */                   long conversationId = Long.valueOf(data.substring(0, separator)).longValue();
                            if (conversationId <= 0) {
/* 378 */                       conversationId = -1;
                            }
/* 378 */                   state[0] = conversationId;
                        } catch (NumberFormatException e) {
/* 380 */                   CrashUtil.logException(e, "Attempted to restore activity state with an invalid conversation id", new Object[0]);
                        }
                        try {
/* 384 */                   long directUserId = Long.valueOf(data.substring(separator + 1)).longValue();
                            if (directUserId <= 0) {
/* 385 */                       directUserId = -1;
                            }
/* 385 */                   state[1] = directUserId;
                        } catch (NumberFormatException e2) {
/* 387 */                   CrashUtil.logException(e2, "Attempted to restore activity state with an invalid direct user id", new Object[0]);
                        }
                    }
/* 391 */           return state;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 395 */       protected void onStart() {
/* 396 */           super.onStart();
/* 397 */           ResourceService.isConversationActive = true;
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 401 */       protected void onResume() {
/* 402 */           super.onResume();
/* 403 */           ResourceService.lastActiveconversationRowId = this.mConversationRowId;
/* 405 */           IntentFilter chatFilter = new IntentFilter();
/* 406 */           chatFilter.addAction(RealTimeChatService.ACTION_WEBSOCKET_EVENT);
/* 407 */           chatFilter.addAction(VineService.BC_ACTION_ALERT_RTC_NEW_MESSAGE);
/* 408 */           registerReceiver(this.mChatReceiver, chatFilter, CrossConstants.BROADCAST_PERMISSION, null);
/* 411 */           startService(GCMNotificationService.getUpdateNotificationIntent(this, 2, this.mConversationRowId));
/* 413 */           IntentFilter connFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
/* 414 */           registerReceiver(this.mConnectivityReceiver, connFilter, CrossConstants.BROADCAST_PERMISSION, null);
/* 415 */           prepareClient();
/* 416 */           VineWebSocketClient client = this.mClient;
/* 417 */           if (client != null) {
/* 418 */               client.connect();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationActivity$2 */
/* 420 */       class C01782 extends BroadcastReceiver {
/* 421 */           C01782() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 425 */           public void onReceive(Context context, Intent intent) {
                        try {
/* 426 */                   ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
/* 428 */                   NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
/* 429 */                   boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
/* 430 */                   if (isConnected && ConversationActivity.access$500(ConversationActivity.this) != isConnected) {
/* 431 */                       SLog.dWithTag(ConversationActivity.TAG, "Connectivity change received, reconnecting");
/* 432 */                       ConversationActivity.access$700(ConversationActivity.this).removeCallbacks(ConversationActivity.access$600(ConversationActivity.this));
/* 433 */                       ConversationActivity.access$800(ConversationActivity.this);
                            }
/* 435 */                   ConversationActivity.access$502(ConversationActivity.this, isConnected);
                        } catch (SecurityException e) {
/* 437 */                   SLog.m459e("We are not gangsta enough to acess the connectivity state.", (Throwable) e);
                        }
                    }
                }

/* 444 */       public void onBackPressed(View v) {
                    try {
/* 445 */               onBackPressed();
                    } catch (IllegalStateException e) {
                    }
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 452 */       protected void onPause() {
/* 453 */           super.onPause();
                    try {
/* 455 */               unregisterReceiver(this.mChatReceiver);
                    } catch (IllegalArgumentException e) {
                    }
                    try {
/* 460 */               unregisterReceiver(this.mConnectivityReceiver);
                    } catch (IllegalArgumentException e2) {
                    }
/* 464 */           onTypingStatusChanged(false, false);
/* 465 */           disconnectClient();
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 469 */       protected void onStop() {
/* 470 */           super.onStop();
/* 471 */           ResourceService.isConversationActive = false;
                }

/* 474 */       public void onTypingStatusChanged(boolean isTyping, boolean isSending) {
                    if (this.mConversationId > 0 && clientIsActive() && this.mLastTyping != isTyping) {
/* 476 */               if (isTyping) {
/* 477 */                   this.mClient.updateTypingState(this.mConversationId, true);
                        } else if (!isSending) {
/* 479 */                   this.mClient.updateTypingState(this.mConversationId, false);
                        }
                    }
/* 482 */           this.mLastTyping = isTyping;
                }

/* 485 */       public RecordingActivityHelper getRecordingActivityHelper() {
/* 486 */           return this.mHelper;
                }

/* 489 */       public void showDeleteMessageDialog(long messageId) {
/* 490 */           this.mMessageIdToDelete = messageId;
/* 491 */           PromptDialogFragment p = PromptDialogFragment.newInstance(2);
/* 493 */           p.setListener(this.mListener);
/* 494 */           p.setMessage(C0257R.string.delete_message_prompt);
/* 495 */           p.setPositiveButton(C0257R.string.delete_yes);
/* 496 */           p.setNegativeButton(C0257R.string.cancel);
                    try {
/* 498 */               p.show(getFragmentManager());
                    } catch (IllegalStateException e) {
/* 500 */               CrashUtil.logException(e);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 505 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 506 */           if (requestCode == 101) {
/* 507 */               if (resultCode == 1527) {
/* 508 */                   this.mPref.edit().putBoolean(Settings.PREF_PHONE_VERIFIED, true).apply();
/* 509 */                   if (this.mRetrySuccessRunnable != null) {
/* 510 */                       this.mRetrySuccessRunnable.run();
/* 521 */                       return;
                            }
/* 521 */                   return;
                        }
/* 521 */               return;
                    }
/* 514 */           Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
/* 515 */           if (fragment instanceof ConversationFragment) {
/* 516 */               fragment.onActivityResult(requestCode - 65536, resultCode, data);
                    } else {
/* 518 */               super.onActivityResult(requestCode, resultCode, data);
                    }
                }

/* 523 */       public void startPhoneConfirmation(Runnable retrySuccessRunnable) {
/* 524 */           if (this.mPref.getBoolean(Settings.PREF_PHONE_VERIFIED, false)) {
/* 525 */               retrySuccessRunnable.run();
/* 531 */               return;
                    }
/* 527 */           this.mRetrySuccessRunnable = retrySuccessRunnable;
/* 528 */           String phone = this.mPref.getString(Settings.PREF_PHONE, "");
/* 529 */           startActivityForResult(ConfirmationFlowActivity.getIntent(this, phone, false), 101);
                }

/* 533 */       private synchronized boolean clientIsActive() {
                    VineWebSocketClient client;
                    try {
                        client = this.mClient;
                    } catch (Throwable th) {
                        throw th;
                    }
/* 535 */           return client != null && client.isConnected();
                }

/* 538 */       private void prepareClient() {
/* 539 */           VineWebSocketClient client = this.mClient;
/* 540 */           if (client == null || (!client.isConnecting() && !client.isConnected())) {
/* 541 */               SLog.m447d(TAG, "Preparing client now");
/* 542 */               VineAPI api = VineAPI.getInstance(this);
/* 543 */               URI uri = URI.create(BuildUtil.getRtcUrl(getResources()));
/* 544 */               String sessionKey = this.mAppController.getActiveSessionReadOnly().getSessionKey();
/* 545 */               List<BasicNameValuePair> extraHeaders = Arrays.asList(new BasicNameValuePair("vine-session-id", sessionKey), new BasicNameValuePair(VineAPI.X_VINE_CLIENT, api.getVineClientHeader()));
/* 549 */               SLog.dWithTag(TAG, "Creating client: sessionKey=" + sessionKey + ", uri=" + uri);
/* 550 */               this.mClient = new VineWebSocketClient(uri, this.mWebSocketListener, extraHeaders);
                    }
                }

/* 554 */       private void disconnectClient() {
/* 555 */           this.mMainHandler.removeCallbacks(this.mReconnectRunnable);
/* 556 */           WebSocketClient client = this.mClient;
/* 557 */           if (client != null) {
/* 558 */               client.disconnect();
/* 559 */               this.mClient = null;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationActivity$3 */
/* 561 */       class C01793 implements WebSocketClient.Listener {
/* 562 */           C01793() {
                    }

                    @Override // com.codebutler.android_websockets.WebSocketClient.Listener
/* 565 */           public void onConnect() {
/* 566 */               SLog.dWithTag(ConversationActivity.TAG, "Websocket connected");
/* 567 */               ConversationActivity.access$700(ConversationActivity.this).removeCallbacks(ConversationActivity.access$600(ConversationActivity.this));
/* 568 */               ConversationActivity.this.mAppSessionListener.onWebSocketConnectComplete();
                    }

                    @Override // com.codebutler.android_websockets.WebSocketClient.Listener
/* 572 */           public void onMessage(String message) {
/* 573 */               SLog.dWithTag(ConversationActivity.TAG, String.format("Got string message: %s", message));
                        try {
/* 576 */                   JsonParser p = VineParsers.createParser(message);
/* 577 */                   ArrayList<VineRTCConversation> data = VineParsers.parseRTCEvent(p);
/* 578 */                   if (data != null) {
/* 579 */                       ConversationActivity.this.mAppSessionListener.onReceiveRTCMessage(data);
                            }
                        } catch (IOException e) {
/* 582 */                   SLog.m459e("Failed to parse message.", (Throwable) e);
                        }
                    }

                    @Override // com.codebutler.android_websockets.WebSocketClient.Listener
/* 587 */           public void onMessage(byte[] data) {
                    }

                    @Override // com.codebutler.android_websockets.WebSocketClient.Listener
/* 591 */           public void onDisconnect(int code, String reason) {
/* 592 */               SLog.dWithTag(ConversationActivity.TAG, "Websocket disconnected, reason=" + reason);
/* 593 */               ConversationActivity.this.mAppSessionListener.onWebSocketDisconnected();
                    }

                    @Override // com.codebutler.android_websockets.WebSocketClient.Listener
/* 597 */           public void onError(Exception error) {
/* 598 */               SLog.dWithTag(ConversationActivity.TAG, "Websocket error=" + error.getMessage());
/* 599 */               ConversationActivity.this.mAppSessionListener.onWebSocketError();
                    }
                }

/* 603 */       private void reconnectWithBackoff() {
/* 604 */           this.mReconnectDelay = 1000L;
/* 605 */           this.mMainHandler.postDelayed(this.mReconnectRunnable, this.mReconnectDelay);
/* 606 */           SLog.dWithTag(TAG, "Posting reconnect runnable with delay=" + this.mReconnectDelay);
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationActivity$4 */
/* 607 */       class RunnableC01804 implements Runnable {
/* 608 */           RunnableC01804() {
                    }

                    @Override // java.lang.Runnable
/* 611 */           public void run() {
/* 612 */               if (ConversationActivity.access$200(ConversationActivity.this) == null) {
/* 613 */                   ConversationActivity.access$900(ConversationActivity.this);
                        }
/* 615 */               VineWebSocketClient client = ConversationActivity.access$200(ConversationActivity.this);
/* 616 */               if (client != null && !client.isConnecting() && !client.isConnected()) {
/* 617 */                   client.connect();
/* 618 */                   ConversationActivity.access$1002(ConversationActivity.this, ConversationActivity.access$1000(ConversationActivity.this) < 16000 ? ConversationActivity.access$1000(ConversationActivity.this) * 2 : ConversationActivity.access$1000(ConversationActivity.this));
/* 620 */                   ConversationActivity.access$700(ConversationActivity.this).postDelayed(ConversationActivity.access$600(ConversationActivity.this), ConversationActivity.access$1000(ConversationActivity.this));
/* 621 */                   SLog.dWithTag(ConversationActivity.TAG, "Posted reconnect with delay=" + ConversationActivity.access$1000(ConversationActivity.this) + "ms");
                        }
                    }
                }

/* 624 */       private class ConversationSessionListener extends AppSessionListener {
/* 625 */           private ConversationSessionListener() {
                    }

/* 625 */           /* synthetic */ ConversationSessionListener(ConversationActivity x0, C01771 x1) {
/* 626 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 628 */           public void onGetConversationRemoteIdComplete(long conversationId) {
/* 629 */               ConversationActivity.access$1102(ConversationActivity.this, conversationId);
/* 630 */               if (ConversationActivity.access$1200(ConversationActivity.this)) {
/* 631 */                   SLog.dWithTag(ConversationActivity.TAG, "Subscribing to conversation=" + ConversationActivity.access$1100(ConversationActivity.this));
/* 632 */                   ConversationActivity.access$200(ConversationActivity.this).subscribeConversation(ConversationActivity.access$1100(ConversationActivity.this));
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 637 */           public void onWebSocketConnectComplete() {
                        if (ConversationActivity.access$1100(ConversationActivity.this) > 0) {
/* 639 */                   SLog.dWithTag(ConversationActivity.TAG, "Subscribing to conversation=" + ConversationActivity.access$1100(ConversationActivity.this));
/* 640 */                   ConversationActivity.access$200(ConversationActivity.this).subscribeConversation(ConversationActivity.access$1100(ConversationActivity.this));
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 645 */           public void onWebSocketDisconnected() {
/* 646 */               showTypingIndicator(false, -1L);
/* 647 */               ConversationActivity.access$800(ConversationActivity.this);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 651 */           public void onFollowComplete(String reqId, int statusCode, String reasonPhrase, long userId) {
/* 652 */               if (statusCode == 200 && ConversationActivity.access$1300(ConversationActivity.this) == userId && ConversationActivity.this.mMenu != null) {
/* 654 */                   ConversationActivity.this.mMenu.removeItem(C0257R.id.follow_user);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 660 */           public void onReceiveRTCMessage(ArrayList<VineRTCConversation> data) {
                        for (VineRTCConversation conversation : data) {
                            if (conversation.conversationId == ConversationActivity.access$1100(ConversationActivity.this)) {
/* 663 */                       ArrayList<VineRTCParticipant> participants = conversation.participants;
                                for (VineRTCParticipant participant : participants) {
/* 665 */                           long userId = participant.userId;
/* 666 */                           if (participant.isTyping) {
/* 667 */                               showTypingIndicator(true, userId);
                                    } else {
/* 669 */                               showTypingIndicator(false, userId);
                                    }
/* 672 */                           long lastMessageId = participant.lastMessageId;
                                    if (lastMessageId > 0) {
                                        if (lastMessageId > ConversationActivity.access$1400(ConversationActivity.this)) {
/* 675 */                                   ConversationActivity.this.mAppController.fetchConversation(1, false, 0L, ConversationActivity.access$300(ConversationActivity.this), false);
/* 677 */                                   Fragment fragment = ConversationActivity.this.getSupportFragmentManager().findFragmentByTag(ConversationActivity.FRAGMENT_TAG);
/* 678 */                                   if (fragment instanceof ConversationFragment) {
/* 679 */                                       ((ConversationFragment) fragment).onNewMessage();
                                            }
                                        }
/* 682 */                               ConversationActivity.access$1402(ConversationActivity.this, lastMessageId);
                                    }
                                }
                            }
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.ConversationActivity$ConversationSessionListener$1 */
/* 688 */           class RunnableC01811 implements Runnable {
                        final /* synthetic */ boolean val$show;
                        final /* synthetic */ long val$userId;

/* 689 */               RunnableC01811(boolean z, long j) {
                            this.val$show = z;
                            this.val$userId = j;
                        }

                        @Override // java.lang.Runnable
/* 692 */               public void run() {
/* 693 */                   Fragment fragment = ConversationActivity.this.getSupportFragmentManager().findFragmentByTag(ConversationActivity.FRAGMENT_TAG);
/* 694 */                   if (fragment instanceof ConversationFragment) {
/* 695 */                       ((ConversationFragment) fragment).showTypingIndicator(this.val$show, this.val$userId);
                            }
                        }
                    }

/* 689 */           private void showTypingIndicator(boolean show, long userId) {
/* 690 */               ConversationActivity.this.runOnUiThread(new RunnableC01811(show, userId));
                    }
                }
            }
