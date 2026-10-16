            package co.vine.android;

            import android.app.Activity;
            import android.content.BroadcastReceiver;
            import android.content.ComponentName;
            import android.content.ContentResolver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.ServiceConnection;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.drawable.ColorDrawable;
            import android.graphics.drawable.GradientDrawable;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Message;
            import android.os.Messenger;
            import android.os.RemoteException;
            import android.support.v4.app.FragmentActivity;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.support.v4.util.LongSparseArray;
            import android.support.v4.view.ViewCompat;
            import android.text.Editable;
            import android.text.TextUtils;
            import android.text.TextWatcher;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.view.animation.Transformation;
            import android.widget.AbsListView;
            import android.widget.EditText;
            import android.widget.ImageView;
            import android.widget.ListView;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.cache.video.UrlVideo;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.plugin.RecorderPluginSupportedFragment;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.provider.VineProviderHelper;
            import co.vine.android.recorder.InlineVineRecorder;
            import co.vine.android.recorder.NotEnoughSpaceException;
            import co.vine.android.recorder.ProgressView;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSessionManager;
            import co.vine.android.recorder.RecordSessionVersion;
            import co.vine.android.service.VineUploadService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.MuteUtil;
            import co.vine.android.util.UploadManager;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.widget.ConversationList;
            import co.vine.android.widget.TypefacesEditText;
            import com.edisonwang.android.slog.SLog;
            import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
            import java.io.IOException;
            import java.util.HashMap;
            
            
    public class ConversationFragment extends BaseCursorListFragment implements View.OnClickListener, TextWatcher, TextView.OnEditorActionListener, TypefacesEditText.KeyboardListener, RecorderPluginSupportedFragment {
                public static final String ARG_AM_FOLLOWING = "am_following_recipient";
                public static final String ARG_CONVERSATION_ROW_ID = "conversation_row_id";
                public static final String ARG_ENCODER_VERSION = "arg_encoder_version";
                private static final long COLLAPSE_DURATION_MS = 300;
                private static final int MAX_CHARS = 420;
                public static final int REQUEST_POST_OPTIONS = 2;
                public static final int REQUEST_RECORD_REPLY = 1991;
                private static final int SCROLL_TO_BOTTOM_MESSAGE_COUNT_THRESHOLD = 1;
                private static final String STATE_FETCHED = "fetched";
                private static final String TAG = "ConversationFrag";
                private boolean mAmFollowing;
                private boolean mBound;
                private ConversationActivity mCallback;
                private ViewGroup mChatPresenceContainer;
                private ImageView mChatPresenceEllipsis;
                private ViewGroup mChatPresenceFooter;
                private LongSparseArray<Integer> mColorMap;
                private View mComposeContainer;
                private ServiceConnection mConnection;
                private ContentResolver mContentResolver;
                private ConversationAdapter mConversationAdapter;
                private long mConversationId;
                private long mConversationRowId;
                private TypefacesEditText mEdit;
                private Intent mFullRecordIntent;
                private Handler mIncomingHandler;
                private boolean mIsSending;
                private long mLastTopItemId;
                private int mLastTopItemPixelOffset;
                private View mLoadMoreHeaderContent;
                private View mLoadMoreProgress;
                private int mLocalUnreadCount;
                private long mMyUserRowId;
                private View mNewMessageBarContainer;
                private TextView mNewMessageBarText;
                private ProgressView mProgressView;
                private String mRecipientUsername;
                private ImageView mRecordButton;
                private InlineRecorderManager mRecorderManager;
                private boolean mRecordingEnabled;
                private ImageView mSendButton;
                private RelativeLayout mTextInputContainer;
                private View mTextInputToggle;
                private ViewGroup mUploadProgressContainer;
                private Messenger mUploadServiceMessenger;
                private ImageView mUploadThumbnail;
                private RecordSessionVersion mVersion;
                private boolean mFetched = false;
                private boolean mFetchWasLoadMore = false;
                private int mProgressViewWidth = 0;
                private int mMyColor = 0;
                private int mRecipientColor = 0;
                private boolean mFetchWasInitial = false;
                private final BroadcastReceiver mMuteChangeReceiver = new C01891();
                private final View.OnClickListener mLaunchRecorderListener = new ViewOnClickListenerC01946();
                private final Runnable mLaunchFullRecordRunnable = new RunnableC01957();

        static  ImageView access$1000(ConversationFragment x0) {
            return x0.mUploadThumbnail;
                }

        static  Messenger access$102(ConversationFragment x0, Messenger x1) {
            x0.mUploadServiceMessenger = x1;
                    return x1;
                }

        static  int access$1100(ConversationFragment x0) {
            return x0.mProgressViewWidth;
                }

        static  int access$1102(ConversationFragment x0, int x1) {
            x0.mProgressViewWidth = x1;
                    return x1;
                }

        static  Intent access$1200(ConversationFragment x0) {
            return x0.mFullRecordIntent;
                }

        static  boolean access$1300(ConversationFragment x0) {
            return x0.mFetched;
                }

        static  View access$1400(ConversationFragment x0) {
            return x0.mLoadMoreHeaderContent;
                }

        static  long access$1502(ConversationFragment x0, long x1) {
            x0.mConversationId = x1;
                    return x1;
                }

        static  boolean access$202(ConversationFragment x0, boolean x1) {
            x0.mBound = x1;
                    return x1;
                }

        static  Handler access$300(ConversationFragment x0) {
            return x0.mIncomingHandler;
                }

        static  void access$400(ConversationFragment x0, Handler x1) {
            x0.subscribe(x1);
                }

        static  void access$600(ConversationFragment x0) {
            x0.toggleInput();
                }

        static  long access$700(ConversationFragment x0) {
            return x0.mConversationRowId;
                }

        static  ViewGroup access$800(ConversationFragment x0) {
            return x0.mUploadProgressContainer;
                }

        static  ProgressView access$900(ConversationFragment x0) {
            return x0.mProgressView;
                }

        static  ProgressView access$902(ConversationFragment x0, ProgressView x1) {
            x0.mProgressView = x1;
                    return x1;
                }


                
       class C01891 extends BroadcastReceiver {
           C01891() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
               if (intent == null || intent.getAction() == null) {
                   CrashUtil.log("Something wrong has happened");
                        } else {
                   boolean mute = intent.getAction().equals(MuteUtil.ACTION_CHANGED_TO_MUTE);
                   ((ConversationAdapter) ConversationFragment.this.mCursorAdapter).toggleMute(mute);
                        }
                    }
                }

                @Override 
       public void onAttach(Activity activity) {
           super.onAttach(activity);
           this.mCallback = (ConversationActivity) activity;
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           if (savedInstanceState != null && savedInstanceState.containsKey(STATE_FETCHED)) {
               this.mFetched = savedInstanceState.getBoolean(STATE_FETCHED);
                    }
           SlidingMenu m = ((BaseActionBarActivity) getActivity()).getSlidingMenu();
           if (m != null) {
               m.setTouchModeAbove(0);
                    }
           Bundle args = getArguments();
           this.mConversationRowId = args.getLong("conversation_row_id", -1L);
           this.mAmFollowing = args.getBoolean("am_following_recipient");
           setHasOptionsMenu(true);
           this.mVersion = (RecordSessionVersion) args.getSerializable("arg_encoder_version");
           if (this.mVersion == null) {
               this.mVersion = RecordSessionManager.getCurrentVersion(getActivity());
                    }
           this.mIncomingHandler = new UploadProgressHandler(this, null);
           this.mBound = false;
           this.mConnection = new ServiceConnectionC01902();
           if (this.mColorMap == null) {
               this.mColorMap = new LongSparseArray<>();
                    }
           long start = System.currentTimeMillis();
           this.mContentResolver = getActivity().getContentResolver();
           Cursor cursor = VineProviderHelper.queryConversationRecipientsUsersView(this.mContentResolver, this.mConversationRowId);
           if (cursor != null) {
               while (cursor.moveToNext()) {
                   long id = cursor.getLong(6);
                   int color = cursor.getInt(7);
                   if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
                       color = 16777215 & getResources().getColor(R.color.vine_green);
                            }
                   int color2 = color | (-16777216);
                   if (this.mRecipientColor == 0) {
                       this.mRecipientColor = color2;
                            }
                   this.mColorMap.put(id, Integer.valueOf(color2));
                   this.mRecipientUsername = cursor.getString(3);
                        }
               cursor.close();
                    }
           ((ConversationActivity) getActivity()).setRecipientUsername(this.mRecipientUsername);
           SLog.m447d("onCreate queries took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
           this.mLocalUnreadCount = 0;
                }

                
       class ServiceConnectionC01902 implements ServiceConnection {
           ServiceConnectionC01902() {
                    }

                    @Override 
           public void onServiceConnected(ComponentName name, IBinder service) {
               SLog.dWithTag(ConversationFragment.TAG, "Bound to VineUploadService");
               ConversationFragment.access$102(ConversationFragment.this, new Messenger(service));
               ConversationFragment.access$202(ConversationFragment.this, true);
               ConversationFragment.access$400(ConversationFragment.this, ConversationFragment.access$300(ConversationFragment.this));
                    }

                    @Override 
           public void onServiceDisconnected(ComponentName name) {
               SLog.dWithTag(ConversationFragment.TAG, "Connection to VineUploadService lost unexpectedly!");
               ConversationFragment.access$102(ConversationFragment.this, null);
               ConversationFragment.access$202(ConversationFragment.this, false);
                    }
                }

       private void subscribe(Handler replyHandler) {
           Messenger replyTo = new Messenger(replyHandler);
           Message subscribe = Message.obtain((Handler) null, 1);
           subscribe.replyTo = replyTo;
           sendMessageToUploadService(subscribe);
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putBoolean(STATE_FETCHED, this.mFetched);
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View v = createView(inflater, R.layout.conversation, container);
           TypefacesEditText edit = (TypefacesEditText) v.findViewById(R.id.edit_reply);
           edit.setOnEditorActionListener(this);
           edit.addTextChangedListener(this);
           edit.setKeyboardListener(this);
           this.mEdit = edit;
           this.mTextInputContainer = (RelativeLayout) v.findViewById(R.id.text_input_container);
           this.mTextInputToggle = v.findViewById(R.id.text_input_toggle);
           this.mTextInputToggle.setOnClickListener(this);
           this.mNewMessageBarContainer = v.findViewById(R.id.new_message_bar_container);
           this.mNewMessageBarContainer.setOnClickListener(this);
           this.mNewMessageBarText = (TextView) this.mNewMessageBarContainer.findViewById(R.id.new_message_bar);
           this.mRecordButton = (ImageView) v.findViewById(R.id.record_button);
           this.mSendButton = (ImageView) v.findViewById(R.id.send_reply_button);
           return v;
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           ConversationActivity activity = (ConversationActivity) getActivity();
           SharedPreferences prefs = Util.getDefaultSharedPrefs(activity);
           this.mRecordingEnabled = RecordConfigUtils.isCapableOfRecording(activity);
           if (this.mRecordingEnabled) {
                        try {
                   this.mVersion.getManager(activity);
                        } catch (IOException e) {
                   this.mRecordingEnabled = false;
                   if (e instanceof NotEnoughSpaceException) {
                       Util.showCenteredToast(activity, R.string.failed_to_start_recording_space);
                            } else {
                       Util.showCenteredToast(activity, R.string.failed_to_start_recording_storage_not_ready);
                            }
                   CrashUtil.log("Exception has happened: {}", e);
                        }
                    }
           this.mMyUserRowId = prefs.getLong(Settings.PREF_USER_ROW_ID, 0L);
           if (this.mCursorAdapter == null) {
               this.mConversationAdapter = new ConversationAdapter(activity, this.mAppController, this.mListView, this.mMyUserRowId, 0);
               this.mCursorAdapter = this.mConversationAdapter;
                    }
           ListView listView = this.mListView;
           listView.setOnScrollListener(this);
           listView.setDivider(null);
           ((ConversationList) listView).deactivateRefresh(true);
           this.mAppSessionListener = new ConversationSessionListener(this, null);
           ViewGroup loadMoreHeader = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.conversation_list_load_more_header, (ViewGroup) null);
           this.mLoadMoreHeaderContent = loadMoreHeader.findViewById(R.id.header_content);
           this.mLoadMoreHeaderContent.setVisibility(8);
           this.mLoadMoreProgress = loadMoreHeader.findViewById(R.id.progress);
           listView.addHeaderView(loadMoreHeader);
           this.mLoadMoreHeaderContent.setOnClickListener(this);
           this.mLoadMoreHeaderContent.setVisibility(0);
           listView.setAdapter(this.mCursorAdapter);
           ViewGroup uploadProgressContainer = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.conversation_reply_progress, (ViewGroup) this.mListView, false);
           this.mUploadProgressContainer = (ViewGroup) uploadProgressContainer.findViewById(R.id.progress_container);
           this.mProgressView = (ProgressView) uploadProgressContainer.findViewById(R.id.progress_view);
           this.mUploadThumbnail = (ImageView) uploadProgressContainer.findViewById(R.id.thumbnail);
           ViewGroup footer = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.conversation_chat_presence, (ViewGroup) this.mListView, false);
           this.mChatPresenceContainer = (ViewGroup) footer.findViewById(R.id.container);
           this.mChatPresenceEllipsis = (ImageView) footer.findViewById(R.id.ellipsis);
           this.mChatPresenceFooter = footer;
           this.mListView.addFooterView(this.mChatPresenceFooter);
           View rootView = getView();
           if (this.mRecordingEnabled) {
               this.mRecorderManager = (InlineRecorderManager) rootView.findViewById(R.id.inline_recorder_manager);
               this.mRecorderManager.init(this, activity, this.mVersion);
                    } else {
               ((InlineRecorderManager) rootView.findViewById(R.id.inline_recorder_manager)).initDisabled(this);
                    }
           activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(this.mRecipientColor));
           this.mComposeContainer = rootView.findViewById(R.id.compose_container);
           int rawColor = prefs.getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
           if (rawColor == Settings.DEFAULT_PROFILE_COLOR || rawColor <= 0) {
               rawColor = 16777215 & getResources().getColor(R.color.vine_green);
                    }
           this.mMyColor = (-16777216) | rawColor;
           ((ProgressView) getView().findViewById(R.id.progress_inline)).setColor(this.mMyColor);
           ((ProgressView) getView().findViewById(R.id.finishing_progress)).setColor(this.mMyColor);
           ViewUtil.setBackground(this.mComposeContainer, new ColorDrawable(getMyColor()));
           GradientDrawable dr = (GradientDrawable) activity.getResources().getDrawable(R.drawable.chat_cursor_drawable);
           dr.setColor((-16777216) | this.mMyColor);
           this.mNewMessageBarContainer.setBackgroundDrawable(new ColorDrawable(this.mRecipientColor));
           this.mSendButton.setOnClickListener(this.mLaunchRecorderListener);
           this.mSendButton.setColorFilter(new PorterDuffColorFilter(getMyColor(), PorterDuff.Mode.SRC_IN));
                }

       protected void prepareUpload() {
           InlineVineRecorder recorder = this.mRecorderManager.getRecorder();
           if (recorder != null && recorder.finalFile != null) {
                        try {
                   String uploadPath = UploadManager.addToUploadQueue(getActivity(), recorder.finalFile, recorder.finalFile.getThumbnailPath(), recorder.finalFile.folder.getName(), true, this.mConversationRowId);
                   sendMessage(uploadPath);
                        } catch (Exception e) {
                   if (BuildUtil.isLogsOn()) {
                       e.printStackTrace();
                            }
                        }
                    }
                }

                @Override 
       public void onScrollStateChanged(AbsListView view, int scrollState) {
           super.onScrollStateChanged(view, scrollState);
           if (this.mCursorAdapter != null && scrollState == 0) {
               ((ConversationAdapter) this.mCursorAdapter).playCurrentPosition();
                    }
                }

                @Override 
       public void onResume() {
           super.onResume();
           GradientDrawable dr = (GradientDrawable) getResources().getDrawable(R.drawable.chat_cursor_drawable);
           dr.setColor((-16777216) | this.mMyColor);
           ConversationActivity activity = (ConversationActivity) getActivity();
           if (activity.keyBoardUpOnStart) {
               activity.keyBoardUpOnStart = false;
               this.mHandler.post(new RunnableC01913());
                    }
           this.mConversationAdapter.onResume();
           activity.registerReceiver(this.mMuteChangeReceiver, MuteUtil.MUTE_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
           if (this.mCursorAdapter.getCursor() == null) {
               initLoader();
                    }
           if (!this.mBound) {
               SLog.dWithTag(TAG, "Will bind to VineUploadService now");
               Intent intent = new Intent(activity, (Class<?>) VineUploadService.class);
               activity.bindService(intent, this.mConnection, 1);
                    }
           this.mUploadProgressContainer.setVisibility(8);
           this.mChatPresenceContainer.setVisibility(8);
           if (this.mRecorderManager != null) {
                        try {
                   this.mRecorderManager.onResume();
                        } catch (IOException e) {
                   this.mRecordingEnabled = false;
                   CrashUtil.log("Failed to create folder.");
                   Util.showCenteredToast(getActivity(), R.string.failed_to_start_recording_storage_not_ready);
                        }
                    }
                }

                
       class RunnableC01913 implements Runnable {
           RunnableC01913() {
                    }

                    @Override 
           public void run() {
               ConversationFragment.access$600(ConversationFragment.this);
                    }
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           if (requestCode == 2) {
               processPostOptionsResult(PostOptionsDialogActivity.processActivityResult(this.mAppController, getActivity(), resultCode, data));
               return;
                    }
           if (requestCode == 1991 && data != null) {
               SLog.dWithTag(TAG, "resultCode=" + resultCode + ", data=" + data);
               if (!BuildUtil.isIsHwEncodingEnabled()) {
                   sendMessage(data.getStringExtra("upload_path"));
                   return;
                        }
               return;
                    }
           if (resultCode == 100) {
               sendMessageToUploadService(Message.obtain((Handler) null, 2));
                    }
                }

       private void sendMessage(String uploadPath) {
           Intent intent = VineUploadService.getVMPostIntent(getActivity(), uploadPath, false, this.mConversationRowId, VineProviderHelper.getConversationRecipients(this.mContentResolver, this.mConversationRowId), null, 0);
           SLog.m448d("intent={}, extras={}", intent, intent.getExtras());
           getActivity().startService(intent);
           this.mIncomingHandler = new UploadProgressHandler(this, null);
           subscribe(this.mIncomingHandler);
                }

       public void onRecorderShown() {
           this.mTextInputToggle.setVisibility(8);
           ((ConversationAdapter) this.mCursorAdapter).stopCurrentPlayer();
                }

       public void onRecorderHidden() {
           this.mTextInputToggle.setVisibility(0);
           ((ConversationAdapter) this.mCursorAdapter).playCurrentPosition();
                }

                @Override 
       public void onKeyboardDismissed() {
           this.mCallback.onTypingStatusChanged(false, this.mIsSending);
           hideTypingContainer();
                }

                @Override 
       public boolean sendKeyEvent(KeyEvent event) {
           return false;
                }

       public int getMyColor() {
           return this.mMyColor;
                }

       public void startPostOptionsActivity(Intent intent) {
           startActivityForResult(intent, 2);
                }

                @Override 
       public int getColor(boolean primary) {
           return this.mMyColor;
                }

       private class UploadProgressHandler extends Handler {
           private UploadProgressHandler() {
                    }

            UploadProgressHandler(ConversationFragment x0, C01891 x1) {
               this();
                    }

                    @Override 
           public void handleMessage(Message msg) {
               int what = msg.what;
               Bundle data = msg.obj == null ? new Bundle() : (Bundle) msg.obj;
               SLog.dWithTag(ConversationFragment.TAG, "Message received, what=" + what);
                        switch (what) {
                            case 3:
                       boolean isActive = data.getBoolean(VineUploadService.EXTRA_IS_ACTIVE);
                       long conversationRowId = data.getLong("conversation_row_id");
                       String thumbnailPath = data.getString(VineUploadService.AG_THUMBNAIL);
                       SLog.dWithTag(ConversationFragment.TAG, "Result receiver was set in VineUploadService, conversationId=" + conversationRowId);
                       if (isActive && ConversationFragment.access$700(ConversationFragment.this) == conversationRowId) {
                           ConversationFragment.access$800(ConversationFragment.this).setVisibility(0);
                           ConversationFragment.access$902(ConversationFragment.this, (ProgressView) ConversationFragment.access$800(ConversationFragment.this).findViewById(R.id.progress_view));
                           if (!TextUtils.isEmpty(thumbnailPath)) {
                               Bitmap thumbnailBitmap = BitmapFactory.decodeFile(thumbnailPath);
                               ConversationFragment.access$1000(ConversationFragment.this).setImageBitmap(thumbnailBitmap);
                                    } else {
                               ConversationFragment.access$1000(ConversationFragment.this).setVisibility(8);
                                    }
                                    break;
                                }
                                break;
                            case 4:
                       SLog.dWithTag(ConversationFragment.TAG, "Video transcoding started");
                                break;
                            case 5:
                       double p = data.getDouble(VineUploadService.EXTRA_TRANSCODE_PROGRESS);
                       SLog.dWithTag(ConversationFragment.TAG, "Transcode progress changed to p=" + p);
                       if (ConversationFragment.access$1100(ConversationFragment.this) == 0) {
                           ConversationFragment.access$1102(ConversationFragment.this, ConversationFragment.access$900(ConversationFragment.this).getMeasuredWidth());
                                }
                       ConversationFragment.access$900(ConversationFragment.this).setProgressRatio((float) ((0.65d * p) / 100.0d));
                                break;
                            case 6:
                       double p2 = data.getDouble(VineUploadService.EXTRA_UPLOAD_PROGRESS);
                       SLog.dWithTag(ConversationFragment.TAG, "Upload progress changed to p=" + p2);
                       if (ConversationFragment.access$1100(ConversationFragment.this) == 0) {
                           ConversationFragment.access$1102(ConversationFragment.this, ConversationFragment.access$900(ConversationFragment.this).getMeasuredWidth());
                                }
                       ConversationFragment.access$900(ConversationFragment.this).setProgressRatio((float) (0.65d + ((0.33d * p2) / 100.0d)));
                                break;
                            case 7:
                       boolean success = data.getBoolean(VineUploadService.EXTRA_SUCCESS);
                       SLog.dWithTag(ConversationFragment.TAG, "Post completed, success=" + success);
                       ConversationFragment.access$800(ConversationFragment.this).setVisibility(8);
                       sendMessage(Message.obtain((Handler) null, 2));
                                break;
                        }
                    }
                }

       private void sendMessageToUploadService(Message msg) {
                    try {
               if (this.mUploadServiceMessenger != null) {
                   this.mUploadServiceMessenger.send(msg);
                        }
                    } catch (RemoteException e) {
               SLog.m446d("Failed to send message to upload service");
               if (BuildUtil.isLogsOn()) {
                   e.printStackTrace();
                        }
                    }
                }

       private void scrollToBottom(boolean respectCurrentPos, boolean smooth) {
           ListView listView = this.mListView;
           int count = this.mCursorAdapter.getCount() + listView.getHeaderViewsCount();
           if (!respectCurrentPos || count - listView.getLastVisiblePosition() < 2) {
               listView.post(new RunnableC01924(smooth, listView, count));
                    }
                }

                
       class RunnableC01924 implements Runnable {
                    final  int val$count;
                    final  ListView val$listView;
                    final  boolean val$smooth;

           RunnableC01924(boolean z, ListView listView, int i) {
                        this.val$smooth = z;
                        this.val$listView = listView;
                        this.val$count = i;
                    }

                    @Override 
           public void run() {
               if (this.val$smooth) {
                   this.val$listView.smoothScrollToPosition(this.val$count - 1);
                        } else {
                   this.val$listView.setSelectionFromTop(this.val$count - 1, 0);
                        }
                    }
                }

       private void fetchContent(int fetchType) {
           if (this.mConversationRowId > 0 && !hasPendingRequest()) {
                        switch (fetchType) {
                            case 1:
                       this.mFetchWasLoadMore = true;
                       showProgress(1);
                       addRequest(this.mAppController.fetchConversation(3, true, 0L, this.mConversationRowId, false));
                                break;
                            case 3:
                       this.mFetched = true;
                       showProgress(3);
                       addRequest(this.mAppController.fetchConversation(1, false, 0L, this.mConversationRowId, false));
                                break;
                        }
                    }
                }

                @Override 
       public void showProgress(int type) {
                    switch (type) {
                        case 1:
                   this.mLoadMoreHeaderContent.setVisibility(8);
                   this.mLoadMoreProgress.setVisibility(0);
                            break;
                    }
                }

                @Override 
       public void hideProgress(int type) {
           this.mLoadMoreProgress.setVisibility(8);
                }

       public void showTypingIndicator(boolean show, long userId) {
           ViewGroup container = this.mChatPresenceContainer;
           container.clearAnimation();
           if (show) {
               ImageView ellipsis = this.mChatPresenceEllipsis;
               ((ConversationAdapter) this.mCursorAdapter).getIdAtLastPosition();
               Integer color = null;
               if (this.mColorMap != null) {
                   Integer color2 = this.mColorMap.get(userId);
                            color = color2;
                        }
               if (color == null) {
                   color = Integer.valueOf(this.mRecipientColor);
                        }
               container.setVisibility(0);
               container.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.chat_presence_height);
               ellipsis.setColorFilter(1509949440 | (color.intValue() & ViewCompat.MEASURED_SIZE_MASK));
               scrollToBottom(true, false);
               return;
                    }
           HeightAnimation collapse = new HeightAnimation(container, new RunnableC01935(container));
           container.startAnimation(collapse);
                }

                
       class RunnableC01935 implements Runnable {
                    final  ViewGroup val$container;

           RunnableC01935(ViewGroup viewGroup) {
                        this.val$container = viewGroup;
                    }

                    @Override 
           public void run() {
               this.val$container.setVisibility(8);
                    }
                }

       private class HeightAnimation extends Animation {
                    private View mView;

           public HeightAnimation(View view, Runnable onAnimationEnd) {
               setFillAfter(true);
               setDuration(300L);
               this.mView = view;
               setAnimationListener(new AnimationAnimationListenerC01961(ConversationFragment.this, onAnimationEnd));
                    }

                    
           class AnimationAnimationListenerC01961 implements Animation.AnimationListener {
                        final  Runnable val$onAnimationEnd;
                        final  ConversationFragment val$this$0;

               AnimationAnimationListenerC01961(ConversationFragment conversationFragment, Runnable runnable) {
                            this.val$this$0 = conversationFragment;
                            this.val$onAnimationEnd = runnable;
                        }

                        @Override 
               public void onAnimationRepeat(Animation animation) {
                        }

                        @Override 
               public void onAnimationStart(Animation animation) {
                        }

                        @Override 
               public void onAnimationEnd(Animation animation) {
                   this.val$onAnimationEnd.run();
                        }
                    }

                    @Override 
           protected void applyTransformation(float interpolatedTime, Transformation t) {
               int height = ConversationFragment.this.getResources().getDimensionPixelSize(R.dimen.chat_presence_height);
               this.mView.getLayoutParams().height = (int) (height * (1.0f - interpolatedTime));
               this.mView.requestLayout();
                    }

                    @Override 
           public boolean willChangeBounds() {
               return true;
                    }
                }

                
       class ViewOnClickListenerC01946 implements View.OnClickListener {
           ViewOnClickListenerC01946() {
                    }

                    @Override 
           public void onClick(View v) {
               ConversationFragment.this.launchFullRecord();
                    }
                }

                @Override 
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           if (this.mEdit.getText().length() == 0) {
               this.mSendButton.setImageResource(R.drawable.ic_vm_capture_keyboard);
               this.mSendButton.setOnClickListener(this.mLaunchRecorderListener);
                    } else {
               this.mSendButton.setImageResource(R.drawable.ic_action_send);
               this.mSendButton.setOnClickListener(this);
                    }
                }

       private Bundle getMessageBundle() {
           Bundle extras = new Bundle();
           extras.putLong("conversation_row_id", this.mConversationRowId);
           return extras;
                }

                @Override 
       public void afterTextChanged(Editable editable) {
           if (!TextUtils.isEmpty(editable)) {
               this.mCallback.onTypingStatusChanged(true, this.mIsSending);
                    } else {
               this.mCallback.onTypingStatusChanged(false, this.mIsSending);
                    }
                }

                @Override 
       public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override 
       public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
           if (v.getId() != R.id.edit_reply) {
                        return false;
                    }
           if (actionId == 4) {
               sendTextMessage();
                    }
           return true;
                }

                @Override 
       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
           String conversationId = String.valueOf(this.mConversationRowId);
           Uri contentUri = Vine.ConversationMessageUsersView.CONTENT_URI_CONVERSATION.buildUpon().appendQueryParameter("conversation_row_id", conversationId).build();
           return new CursorLoader(getActivity(), contentUri, VineDatabaseSQL.ConversationMessageUsersQuery.PROJECTION, null, null, Vine.Messages.SORT_ORDER_DEFAULT);
                }

                @Override 
       public void onPause() {
           super.onPause();
           this.mConversationAdapter.onPause();
           getActivity().unregisterReceiver(this.mMuteChangeReceiver);
           if (this.mBound) {
               SLog.dWithTag(TAG, "Will unbind from VineUploadService now");
               sendMessageToUploadService(Message.obtain((Handler) null, 2));
               getActivity().unbindService(this.mConnection);
               this.mBound = false;
                    }
           if (this.mRecorderManager != null) {
               this.mRecorderManager.onPaused();
                    }
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           if (this.mRecorderManager != null) {
               this.mRecorderManager.release();
                    }
                }

                
                @Override 
       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    switch (loader.getId()) {
                        case 0:
                   saveTopItemInfo();
                   super.onLoadFinished(loader, cursor);
                   hideProgress(3);
                   if (!this.mFetched) {
                       fetchContent(3);
                       this.mFetchWasInitial = true;
                            } else {
                       if (this.mCursorAdapter.isEmpty()) {
                           showSadface(true, false);
                                } else {
                           showSadface(false);
                           if (this.mFetchWasLoadMore) {
                               this.mFetchWasLoadMore = false;
                               this.mLoadMoreHeaderContent.setVisibility(0);
                               hideProgress(3);
                               this.mListView.setSelectionFromTop(((ConversationAdapter) this.mCursorAdapter).getPositionForId(this.mLastTopItemId) + this.mListView.getHeaderViewsCount(), this.mLastTopItemPixelOffset);
                                    } else if (this.mFetchWasInitial) {
                               scrollToBottom(false, false);
                                    } else if (this.mListView.getLastVisiblePosition() >= cursor.getCount() - 1) {
                               scrollToBottom(false, true);
                                    }
                                }
                       this.mFetchWasInitial = false;
                       if (((ConversationAdapter) this.mCursorAdapter).atLastPage()) {
                           this.mLoadMoreHeaderContent.setVisibility(8);
                                } else {
                           this.mLoadMoreHeaderContent.setVisibility(0);
                                }
                       if (!this.mCursorAdapter.isEmpty()) {
                           ((ConversationAdapter) this.mCursorAdapter).playCurrentPosition();
                                }
                            }
                            break;
                    }
                }

       private void saveTopItemInfo() {
           if (this.mCursorAdapter.getCount() != 0) {
               this.mLastTopItemId = this.mCursorAdapter.getItemId(this.mListView.getFirstVisiblePosition());
               View topChild = this.mListView.getChildAt(this.mListView.getFirstVisiblePosition() + this.mListView.getHeaderViewsCount());
               if (topChild != null) {
                   this.mLastTopItemPixelOffset = topChild.getTop();
                        }
                    }
                }

       private void sendTextMessage() {
           EditText editText = this.mEdit;
           if (editText.getText().length() > 420) {
               Util.showTopToast(getActivity(), R.string.message_length_exceeded);
               return;
                    }
           if (validate(this.mEdit)) {
               String message = editText.getText().toString();
               Intent i = VineUploadService.getVMPostIntent(getActivity(), null, false, this.mConversationRowId, VineProviderHelper.getConversationRecipients(this.mContentResolver, this.mConversationRowId), message, 0);
               getActivity().startService(i);
               this.mIsSending = true;
               editText.getText().clear();
               this.mIsSending = false;
               return;
                    }
           Util.setSoftKeyboardVisibility(getActivity(), this.mEdit, false);
           hideTypingContainer();
                }

       private void hideTypingContainer() {
           this.mEdit.clearFocus();
           if (this.mLocalUnreadCount > 0) {
               toggleNewMessageBar(true);
                    }
           this.mTextInputContainer.setVisibility(8);
           ViewUtil.setBackground(this.mComposeContainer, new ColorDrawable(getMyColor()));
           this.mRecordButton.setVisibility(0);
           this.mTextInputToggle.setVisibility(0);
                }

       private boolean validate(EditText editText) {
                    return editText != null && editText.length() > 0;
                }

       private void toggleInput() {
           FragmentActivity activity = getActivity();
           if (activity != null) {
               EditText edit = this.mEdit;
               edit.requestFocus();
               Util.setSoftKeyboardVisibility(activity, edit, true);
               this.mTextInputContainer.setVisibility(0);
               ViewUtil.setBackground(this.mComposeContainer.findViewById(R.id.compose_container), new ColorDrawable(activity.getResources().getColor(R.color.solid_white)));
               this.mRecordButton.setVisibility(8);
               this.mTextInputToggle.setVisibility(8);
                    }
                }

                @Override 
       public void onClick(View view) {
           int id = view.getId();
           if (id == R.id.send_reply_button) {
               sendTextMessage();
               return;
                    }
           if (id == R.id.text_input_toggle) {
               toggleInput();
               return;
                    }
           if (id == R.id.header_content) {
               this.mLoadMoreHeaderContent.setVisibility(8);
               this.mLoadMoreProgress.setVisibility(0);
               fetchContent(1);
                    } else if (id == R.id.new_message_bar) {
               scrollToBottom(false, false);
               toggleNewMessageBar(false);
               this.mLocalUnreadCount = 0;
                    }
                }

       public void onNewMessage() {
           if (this.mListView.getLastVisiblePosition() < this.mCursorAdapter.getCount() && this.mTextInputContainer.getVisibility() != 0) {
               this.mLocalUnreadCount++;
               this.mNewMessageBarText.setText(getResources().getQuantityString(R.plurals.new_messages, this.mLocalUnreadCount, Integer.valueOf(this.mLocalUnreadCount)));
               toggleNewMessageBar(true);
                    }
                }

                @Override 
       protected void onScrollLastItem(Cursor cursor) {
           this.mLocalUnreadCount = 0;
           toggleNewMessageBar(false);
                }

       private void toggleNewMessageBar(boolean show) {
           this.mNewMessageBarContainer.setVisibility(show ? 0 : 8);
                }

       public boolean isInTextInputMode() {
                    return this.mRecordButton.getVisibility() == 8;
                }

       void launchFullRecord() {
           this.mFullRecordIntent = AbstractRecordingActivity.getIntentForConversation(getActivity(), 0, "ConversationFullRecord", this.mConversationRowId, false, this.mRecipientUsername, this.mAmFollowing);
           this.mIncomingHandler = new UploadProgressHandler(this, null);
           subscribe(this.mIncomingHandler);
           this.mHandler.post(this.mLaunchFullRecordRunnable);
                }

                
       class RunnableC01957 implements Runnable {
          RunnableC01957() {
                    }

                    @Override 
          public void run() {
              FragmentActivity activity = ConversationFragment.this.getActivity();
              if (activity != null) {
                  Util.startActionOnRecordingAvailable(activity, ConversationFragment.access$1200(ConversationFragment.this), 1991);
                        }
                    }
                }

      private class ConversationSessionListener extends AppSessionListener {
          private ConversationSessionListener() {
                    }

           ConversationSessionListener(ConversationFragment x0, C01891 x1) {
              this();
                    }

                    @Override 
          public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
              ((ConversationAdapter) ConversationFragment.this.mCursorAdapter).onVideoPathObtained(videos);
                    }

                    @Override 
          public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
              ((ConversationAdapter) ConversationFragment.this.mCursorAdapter).setImages(images);
                    }

                    @Override 
          public void onGetConversationComplete(String reqId, int statusCode, String reasonPhrase, long conversationRowId, int responseCode, boolean empty) {
              PendingRequest req = ConversationFragment.this.removeRequest(reqId);
              if (req != null && conversationRowId == ConversationFragment.access$700(ConversationFragment.this)) {
                  ConversationFragment.this.hideProgress(3);
                  if (empty) {
                      ConversationFragment.this.showSadface(true, false);
                            } else {
                      ConversationFragment.this.showSadface(false);
                            }
                  ConversationAdapter adapter = (ConversationAdapter) ConversationFragment.this.mCursorAdapter;
                  if (ConversationFragment.access$1300(ConversationFragment.this) && (adapter.atLastPage() || empty || adapter.isEmpty())) {
                      ConversationFragment.access$1400(ConversationFragment.this).setVisibility(8);
                            }
                  if (responseCode == 4) {
                      ConversationFragment.access$1400(ConversationFragment.this).setVisibility(0);
                            }
                        }
                    }

                    @Override 
          public void onGetConversationRemoteIdComplete(long conversationId) {
              ConversationFragment.access$1502(ConversationFragment.this, conversationId);
                        if (conversationId <= 0) {
                  ConversationFragment.access$1400(ConversationFragment.this).setVisibility(8);
                        }
                    }
                }
            }
