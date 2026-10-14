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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 85 */    public class ConversationFragment extends BaseCursorListFragment implements View.OnClickListener, TextWatcher, TextView.OnEditorActionListener, TypefacesEditText.KeyboardListener, RecorderPluginSupportedFragment {
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

/* 86 */        static /* synthetic */ ImageView access$1000(ConversationFragment x0) {
/* 87 */            return x0.mUploadThumbnail;
                }

/* 86 */        static /* synthetic */ Messenger access$102(ConversationFragment x0, Messenger x1) {
/* 87 */            x0.mUploadServiceMessenger = x1;
                    return x1;
                }

/* 86 */        static /* synthetic */ int access$1100(ConversationFragment x0) {
/* 87 */            return x0.mProgressViewWidth;
                }

/* 86 */        static /* synthetic */ int access$1102(ConversationFragment x0, int x1) {
/* 87 */            x0.mProgressViewWidth = x1;
                    return x1;
                }

/* 86 */        static /* synthetic */ Intent access$1200(ConversationFragment x0) {
/* 87 */            return x0.mFullRecordIntent;
                }

/* 86 */        static /* synthetic */ boolean access$1300(ConversationFragment x0) {
/* 87 */            return x0.mFetched;
                }

/* 86 */        static /* synthetic */ View access$1400(ConversationFragment x0) {
/* 87 */            return x0.mLoadMoreHeaderContent;
                }

/* 86 */        static /* synthetic */ long access$1502(ConversationFragment x0, long x1) {
/* 87 */            x0.mConversationId = x1;
                    return x1;
                }

/* 86 */        static /* synthetic */ boolean access$202(ConversationFragment x0, boolean x1) {
/* 87 */            x0.mBound = x1;
                    return x1;
                }

/* 86 */        static /* synthetic */ Handler access$300(ConversationFragment x0) {
/* 87 */            return x0.mIncomingHandler;
                }

/* 86 */        static /* synthetic */ void access$400(ConversationFragment x0, Handler x1) {
/* 87 */            x0.subscribe(x1);
                }

/* 86 */        static /* synthetic */ void access$600(ConversationFragment x0) {
/* 87 */            x0.toggleInput();
                }

/* 86 */        static /* synthetic */ long access$700(ConversationFragment x0) {
/* 87 */            return x0.mConversationRowId;
                }

/* 86 */        static /* synthetic */ ViewGroup access$800(ConversationFragment x0) {
/* 87 */            return x0.mUploadProgressContainer;
                }

/* 86 */        static /* synthetic */ ProgressView access$900(ConversationFragment x0) {
/* 87 */            return x0.mProgressView;
                }

/* 86 */        static /* synthetic */ ProgressView access$902(ConversationFragment x0, ProgressView x1) {
/* 87 */            x0.mProgressView = x1;
                    return x1;
                }


                /* JADX INFO: renamed from: co.vine.android.ConversationFragment$1 */
/* 153 */       class C01891 extends BroadcastReceiver {
/* 154 */           C01891() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 157 */           public void onReceive(Context context, Intent intent) {
/* 158 */               if (intent == null || intent.getAction() == null) {
/* 159 */                   CrashUtil.log("Something wrong has happened");
                        } else {
/* 161 */                   boolean mute = intent.getAction().equals(MuteUtil.ACTION_CHANGED_TO_MUTE);
/* 162 */                   ((ConversationAdapter) ConversationFragment.this.mCursorAdapter).toggleMute(mute);
                        }
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 168 */       public void onAttach(Activity activity) {
/* 169 */           super.onAttach(activity);
/* 170 */           this.mCallback = (ConversationActivity) activity;
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 174 */       public void onCreate(Bundle savedInstanceState) {
/* 175 */           super.onCreate(savedInstanceState);
/* 177 */           if (savedInstanceState != null && savedInstanceState.containsKey(STATE_FETCHED)) {
/* 179 */               this.mFetched = savedInstanceState.getBoolean(STATE_FETCHED);
                    }
/* 183 */           SlidingMenu m = ((BaseActionBarActivity) getActivity()).getSlidingMenu();
/* 184 */           if (m != null) {
/* 185 */               m.setTouchModeAbove(0);
                    }
/* 188 */           Bundle args = getArguments();
/* 189 */           this.mConversationRowId = args.getLong("conversation_row_id", -1L);
/* 190 */           this.mAmFollowing = args.getBoolean("am_following_recipient");
/* 192 */           setHasOptionsMenu(true);
/* 194 */           this.mVersion = (RecordSessionVersion) args.getSerializable("arg_encoder_version");
/* 195 */           if (this.mVersion == null) {
/* 196 */               this.mVersion = RecordSessionManager.getCurrentVersion(getActivity());
                    }
/* 199 */           this.mIncomingHandler = new UploadProgressHandler(this, null);
/* 200 */           this.mBound = false;
/* 201 */           this.mConnection = new ServiceConnectionC01902();
/* 221 */           if (this.mColorMap == null) {
/* 222 */               this.mColorMap = new LongSparseArray<>();
                    }
/* 225 */           long start = System.currentTimeMillis();
/* 227 */           this.mContentResolver = getActivity().getContentResolver();
/* 229 */           Cursor cursor = VineProviderHelper.queryConversationRecipientsUsersView(this.mContentResolver, this.mConversationRowId);
/* 231 */           if (cursor != null) {
/* 232 */               while (cursor.moveToNext()) {
/* 233 */                   long id = cursor.getLong(6);
/* 235 */                   int color = cursor.getInt(7);
/* 237 */                   if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
/* 238 */                       color = 16777215 & getResources().getColor(C0257R.color.vine_green);
                            }
/* 241 */                   int color2 = color | (-16777216);
/* 244 */                   if (this.mRecipientColor == 0) {
/* 245 */                       this.mRecipientColor = color2;
                            }
/* 248 */                   this.mColorMap.put(id, Integer.valueOf(color2));
/* 249 */                   this.mRecipientUsername = cursor.getString(3);
                        }
/* 251 */               cursor.close();
                    }
/* 254 */           ((ConversationActivity) getActivity()).setRecipientUsername(this.mRecipientUsername);
/* 256 */           SLog.m447d("onCreate queries took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
/* 257 */           this.mLocalUnreadCount = 0;
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationFragment$2 */
/* 199 */       class ServiceConnectionC01902 implements ServiceConnection {
/* 200 */           ServiceConnectionC01902() {
                    }

                    @Override // android.content.ServiceConnection
/* 203 */           public void onServiceConnected(ComponentName name, IBinder service) {
/* 204 */               SLog.dWithTag(ConversationFragment.TAG, "Bound to VineUploadService");
/* 205 */               ConversationFragment.access$102(ConversationFragment.this, new Messenger(service));
/* 206 */               ConversationFragment.access$202(ConversationFragment.this, true);
/* 209 */               ConversationFragment.access$400(ConversationFragment.this, ConversationFragment.access$300(ConversationFragment.this));
                    }

                    @Override // android.content.ServiceConnection
/* 213 */           public void onServiceDisconnected(ComponentName name) {
/* 214 */               SLog.dWithTag(ConversationFragment.TAG, "Connection to VineUploadService lost unexpectedly!");
/* 215 */               ConversationFragment.access$102(ConversationFragment.this, null);
/* 216 */               ConversationFragment.access$202(ConversationFragment.this, false);
                    }
                }

/* 260 */       private void subscribe(Handler replyHandler) {
/* 261 */           Messenger replyTo = new Messenger(replyHandler);
/* 262 */           Message subscribe = Message.obtain((Handler) null, 1);
/* 263 */           subscribe.replyTo = replyTo;
/* 264 */           sendMessageToUploadService(subscribe);
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 268 */       public void onSaveInstanceState(Bundle outState) {
/* 269 */           super.onSaveInstanceState(outState);
/* 270 */           outState.putBoolean(STATE_FETCHED, this.mFetched);
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 275 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 276 */           View v = createView(inflater, C0257R.layout.conversation, container);
/* 277 */           TypefacesEditText edit = (TypefacesEditText) v.findViewById(C0257R.id.edit_reply);
/* 278 */           edit.setOnEditorActionListener(this);
/* 279 */           edit.addTextChangedListener(this);
/* 280 */           edit.setKeyboardListener(this);
/* 281 */           this.mEdit = edit;
/* 282 */           this.mTextInputContainer = (RelativeLayout) v.findViewById(C0257R.id.text_input_container);
/* 283 */           this.mTextInputToggle = v.findViewById(C0257R.id.text_input_toggle);
/* 284 */           this.mTextInputToggle.setOnClickListener(this);
/* 285 */           this.mNewMessageBarContainer = v.findViewById(C0257R.id.new_message_bar_container);
/* 286 */           this.mNewMessageBarContainer.setOnClickListener(this);
/* 287 */           this.mNewMessageBarText = (TextView) this.mNewMessageBarContainer.findViewById(C0257R.id.new_message_bar);
/* 288 */           this.mRecordButton = (ImageView) v.findViewById(C0257R.id.record_button);
/* 289 */           this.mSendButton = (ImageView) v.findViewById(C0257R.id.send_reply_button);
/* 290 */           return v;
                }

                @Override // android.support.v4.app.Fragment
/* 294 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 295 */           super.onActivityCreated(savedInstanceState);
/* 296 */           ConversationActivity activity = (ConversationActivity) getActivity();
/* 297 */           SharedPreferences prefs = Util.getDefaultSharedPrefs(activity);
/* 298 */           this.mRecordingEnabled = RecordConfigUtils.isCapableOfRecording(activity);
/* 300 */           if (this.mRecordingEnabled) {
                        try {
/* 302 */                   this.mVersion.getManager(activity);
                        } catch (IOException e) {
/* 304 */                   this.mRecordingEnabled = false;
/* 305 */                   if (e instanceof NotEnoughSpaceException) {
/* 306 */                       Util.showCenteredToast(activity, C0257R.string.failed_to_start_recording_space);
                            } else {
/* 308 */                       Util.showCenteredToast(activity, C0257R.string.failed_to_start_recording_storage_not_ready);
                            }
/* 310 */                   CrashUtil.log("Exception has happened: {}", e);
                        }
                    }
/* 314 */           this.mMyUserRowId = prefs.getLong(Settings.PREF_USER_ROW_ID, 0L);
/* 315 */           if (this.mCursorAdapter == null) {
/* 316 */               this.mConversationAdapter = new ConversationAdapter(activity, this.mAppController, this.mListView, this.mMyUserRowId, 0);
/* 318 */               this.mCursorAdapter = this.mConversationAdapter;
                    }
/* 321 */           ListView listView = this.mListView;
/* 322 */           listView.setOnScrollListener(this);
/* 323 */           listView.setDivider(null);
/* 324 */           ((ConversationList) listView).deactivateRefresh(true);
/* 325 */           this.mAppSessionListener = new ConversationSessionListener(this, null);
/* 327 */           ViewGroup loadMoreHeader = (ViewGroup) LayoutInflater.from(activity).inflate(C0257R.layout.conversation_list_load_more_header, (ViewGroup) null);
/* 329 */           this.mLoadMoreHeaderContent = loadMoreHeader.findViewById(C0257R.id.header_content);
/* 330 */           this.mLoadMoreHeaderContent.setVisibility(8);
/* 331 */           this.mLoadMoreProgress = loadMoreHeader.findViewById(C0257R.id.progress);
/* 332 */           listView.addHeaderView(loadMoreHeader);
/* 333 */           this.mLoadMoreHeaderContent.setOnClickListener(this);
/* 334 */           this.mLoadMoreHeaderContent.setVisibility(0);
/* 335 */           listView.setAdapter(this.mCursorAdapter);
/* 338 */           ViewGroup uploadProgressContainer = (ViewGroup) LayoutInflater.from(activity).inflate(C0257R.layout.conversation_reply_progress, (ViewGroup) this.mListView, false);
/* 340 */           this.mUploadProgressContainer = (ViewGroup) uploadProgressContainer.findViewById(C0257R.id.progress_container);
/* 341 */           this.mProgressView = (ProgressView) uploadProgressContainer.findViewById(C0257R.id.progress_view);
/* 342 */           this.mUploadThumbnail = (ImageView) uploadProgressContainer.findViewById(C0257R.id.thumbnail);
/* 345 */           ViewGroup footer = (ViewGroup) LayoutInflater.from(activity).inflate(C0257R.layout.conversation_chat_presence, (ViewGroup) this.mListView, false);
/* 347 */           this.mChatPresenceContainer = (ViewGroup) footer.findViewById(C0257R.id.container);
/* 348 */           this.mChatPresenceEllipsis = (ImageView) footer.findViewById(C0257R.id.ellipsis);
/* 349 */           this.mChatPresenceFooter = footer;
/* 351 */           this.mListView.addFooterView(this.mChatPresenceFooter);
/* 353 */           View rootView = getView();
/* 355 */           if (this.mRecordingEnabled) {
/* 356 */               this.mRecorderManager = (InlineRecorderManager) rootView.findViewById(C0257R.id.inline_recorder_manager);
/* 357 */               this.mRecorderManager.init(this, activity, this.mVersion);
                    } else {
/* 359 */               ((InlineRecorderManager) rootView.findViewById(C0257R.id.inline_recorder_manager)).initDisabled(this);
                    }
/* 362 */           activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(this.mRecipientColor));
/* 364 */           this.mComposeContainer = rootView.findViewById(C0257R.id.compose_container);
/* 366 */           int rawColor = prefs.getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
/* 367 */           if (rawColor == Settings.DEFAULT_PROFILE_COLOR || rawColor <= 0) {
/* 368 */               rawColor = 16777215 & getResources().getColor(C0257R.color.vine_green);
                    }
/* 370 */           this.mMyColor = (-16777216) | rawColor;
/* 371 */           ((ProgressView) getView().findViewById(C0257R.id.progress_inline)).setColor(this.mMyColor);
/* 373 */           ((ProgressView) getView().findViewById(C0257R.id.finishing_progress)).setColor(this.mMyColor);
/* 375 */           ViewUtil.setBackground(this.mComposeContainer, new ColorDrawable(getMyColor()));
/* 377 */           GradientDrawable dr = (GradientDrawable) activity.getResources().getDrawable(C0257R.drawable.chat_cursor_drawable);
/* 378 */           dr.setColor((-16777216) | this.mMyColor);
/* 380 */           this.mNewMessageBarContainer.setBackgroundDrawable(new ColorDrawable(this.mRecipientColor));
/* 381 */           this.mSendButton.setOnClickListener(this.mLaunchRecorderListener);
/* 382 */           this.mSendButton.setColorFilter(new PorterDuffColorFilter(getMyColor(), PorterDuff.Mode.SRC_IN));
                }

/* 385 */       protected void prepareUpload() {
/* 386 */           InlineVineRecorder recorder = this.mRecorderManager.getRecorder();
/* 387 */           if (recorder != null && recorder.finalFile != null) {
                        try {
/* 389 */                   String uploadPath = UploadManager.addToUploadQueue(getActivity(), recorder.finalFile, recorder.finalFile.getThumbnailPath(), recorder.finalFile.folder.getName(), true, this.mConversationRowId);
/* 392 */                   sendMessage(uploadPath);
                        } catch (Exception e) {
/* 395 */                   if (BuildUtil.isLogsOn()) {
/* 396 */                       e.printStackTrace();
                            }
                        }
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, android.widget.AbsListView.OnScrollListener
/* 403 */       public void onScrollStateChanged(AbsListView view, int scrollState) {
/* 404 */           super.onScrollStateChanged(view, scrollState);
/* 405 */           if (this.mCursorAdapter != null && scrollState == 0) {
/* 406 */               ((ConversationAdapter) this.mCursorAdapter).playCurrentPosition();
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 411 */       public void onResume() {
/* 412 */           super.onResume();
/* 414 */           GradientDrawable dr = (GradientDrawable) getResources().getDrawable(C0257R.drawable.chat_cursor_drawable);
/* 415 */           dr.setColor((-16777216) | this.mMyColor);
/* 417 */           ConversationActivity activity = (ConversationActivity) getActivity();
/* 419 */           if (activity.keyBoardUpOnStart) {
/* 420 */               activity.keyBoardUpOnStart = false;
/* 421 */               this.mHandler.post(new RunnableC01913());
                    }
/* 429 */           this.mConversationAdapter.onResume();
/* 430 */           activity.registerReceiver(this.mMuteChangeReceiver, MuteUtil.MUTE_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
/* 431 */           if (this.mCursorAdapter.getCursor() == null) {
/* 432 */               initLoader();
                    }
/* 435 */           if (!this.mBound) {
/* 436 */               SLog.dWithTag(TAG, "Will bind to VineUploadService now");
/* 437 */               Intent intent = new Intent(activity, (Class<?>) VineUploadService.class);
/* 438 */               activity.bindService(intent, this.mConnection, 1);
                    }
/* 441 */           this.mUploadProgressContainer.setVisibility(8);
/* 442 */           this.mChatPresenceContainer.setVisibility(8);
/* 444 */           if (this.mRecorderManager != null) {
                        try {
/* 446 */                   this.mRecorderManager.onResume();
                        } catch (IOException e) {
/* 448 */                   this.mRecordingEnabled = false;
/* 449 */                   CrashUtil.log("Failed to create folder.");
/* 450 */                   Util.showCenteredToast(getActivity(), C0257R.string.failed_to_start_recording_storage_not_ready);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationFragment$3 */
/* 419 */       class RunnableC01913 implements Runnable {
/* 420 */           RunnableC01913() {
                    }

                    @Override // java.lang.Runnable
/* 423 */           public void run() {
/* 424 */               ConversationFragment.access$600(ConversationFragment.this);
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 456 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 457 */           if (requestCode == 2) {
/* 458 */               processPostOptionsResult(PostOptionsDialogActivity.processActivityResult(this.mAppController, getActivity(), resultCode, data));
/* 472 */               return;
                    }
/* 461 */           if (requestCode == 1991 && data != null) {
/* 462 */               SLog.dWithTag(TAG, "resultCode=" + resultCode + ", data=" + data);
/* 463 */               if (!BuildUtil.isIsHwEncodingEnabled()) {
/* 464 */                   sendMessage(data.getStringExtra("upload_path"));
/* 472 */                   return;
                        }
/* 472 */               return;
                    }
/* 466 */           if (resultCode == 100) {
/* 468 */               sendMessageToUploadService(Message.obtain((Handler) null, 2));
                    }
                }

/* 474 */       private void sendMessage(String uploadPath) {
/* 475 */           Intent intent = VineUploadService.getVMPostIntent(getActivity(), uploadPath, false, this.mConversationRowId, VineProviderHelper.getConversationRecipients(this.mContentResolver, this.mConversationRowId), null, 0);
/* 477 */           SLog.m448d("intent={}, extras={}", intent, intent.getExtras());
/* 478 */           getActivity().startService(intent);
/* 479 */           this.mIncomingHandler = new UploadProgressHandler(this, null);
/* 480 */           subscribe(this.mIncomingHandler);
                }

/* 483 */       public void onRecorderShown() {
/* 484 */           this.mTextInputToggle.setVisibility(8);
/* 485 */           ((ConversationAdapter) this.mCursorAdapter).stopCurrentPlayer();
                }

/* 488 */       public void onRecorderHidden() {
/* 489 */           this.mTextInputToggle.setVisibility(0);
/* 490 */           ((ConversationAdapter) this.mCursorAdapter).playCurrentPosition();
                }

                @Override // co.vine.android.widget.TypefacesEditText.KeyboardListener
/* 494 */       public void onKeyboardDismissed() {
/* 495 */           this.mCallback.onTypingStatusChanged(false, this.mIsSending);
/* 496 */           hideTypingContainer();
                }

                @Override // co.vine.android.widget.TypefacesEditText.KeyboardListener
/* 500 */       public boolean sendKeyEvent(KeyEvent event) {
/* 501 */           return false;
                }

/* 504 */       public int getMyColor() {
/* 505 */           return this.mMyColor;
                }

/* 508 */       public void startPostOptionsActivity(Intent intent) {
/* 509 */           startActivityForResult(intent, 2);
                }

                @Override // co.vine.android.plugin.RecorderPluginSupportedFragment
/* 513 */       public int getColor(boolean primary) {
/* 514 */           return this.mMyColor;
                }

/* 515 */       private class UploadProgressHandler extends Handler {
/* 516 */           private UploadProgressHandler() {
                    }

/* 516 */           /* synthetic */ UploadProgressHandler(ConversationFragment x0, C01891 x1) {
/* 517 */               this();
                    }

                    @Override // android.os.Handler
/* 519 */           public void handleMessage(Message msg) {
/* 520 */               int what = msg.what;
/* 521 */               Bundle data = msg.obj == null ? new Bundle() : (Bundle) msg.obj;
/* 522 */               SLog.dWithTag(ConversationFragment.TAG, "Message received, what=" + what);
                        switch (what) {
                            case 3:
/* 526 */                       boolean isActive = data.getBoolean(VineUploadService.EXTRA_IS_ACTIVE);
/* 527 */                       long conversationRowId = data.getLong("conversation_row_id");
/* 528 */                       String thumbnailPath = data.getString(VineUploadService.AG_THUMBNAIL);
/* 529 */                       SLog.dWithTag(ConversationFragment.TAG, "Result receiver was set in VineUploadService, conversationId=" + conversationRowId);
/* 531 */                       if (isActive && ConversationFragment.access$700(ConversationFragment.this) == conversationRowId) {
/* 532 */                           ConversationFragment.access$800(ConversationFragment.this).setVisibility(0);
/* 533 */                           ConversationFragment.access$902(ConversationFragment.this, (ProgressView) ConversationFragment.access$800(ConversationFragment.this).findViewById(C0257R.id.progress_view));
/* 536 */                           if (!TextUtils.isEmpty(thumbnailPath)) {
/* 537 */                               Bitmap thumbnailBitmap = BitmapFactory.decodeFile(thumbnailPath);
/* 538 */                               ConversationFragment.access$1000(ConversationFragment.this).setImageBitmap(thumbnailBitmap);
                                    } else {
/* 540 */                               ConversationFragment.access$1000(ConversationFragment.this).setVisibility(8);
                                    }
                                    break;
                                }
                                break;
                            case 4:
/* 546 */                       SLog.dWithTag(ConversationFragment.TAG, "Video transcoding started");
                                break;
                            case 5:
/* 550 */                       double p = data.getDouble(VineUploadService.EXTRA_TRANSCODE_PROGRESS);
/* 551 */                       SLog.dWithTag(ConversationFragment.TAG, "Transcode progress changed to p=" + p);
/* 552 */                       if (ConversationFragment.access$1100(ConversationFragment.this) == 0) {
/* 553 */                           ConversationFragment.access$1102(ConversationFragment.this, ConversationFragment.access$900(ConversationFragment.this).getMeasuredWidth());
                                }
/* 556 */                       ConversationFragment.access$900(ConversationFragment.this).setProgressRatio((float) ((0.65d * p) / 100.0d));
                                break;
                            case 6:
/* 560 */                       double p2 = data.getDouble(VineUploadService.EXTRA_UPLOAD_PROGRESS);
/* 561 */                       SLog.dWithTag(ConversationFragment.TAG, "Upload progress changed to p=" + p2);
/* 562 */                       if (ConversationFragment.access$1100(ConversationFragment.this) == 0) {
/* 563 */                           ConversationFragment.access$1102(ConversationFragment.this, ConversationFragment.access$900(ConversationFragment.this).getMeasuredWidth());
                                }
/* 567 */                       ConversationFragment.access$900(ConversationFragment.this).setProgressRatio((float) (0.65d + ((0.33d * p2) / 100.0d)));
                                break;
                            case 7:
/* 571 */                       boolean success = data.getBoolean(VineUploadService.EXTRA_SUCCESS);
/* 572 */                       SLog.dWithTag(ConversationFragment.TAG, "Post completed, success=" + success);
/* 573 */                       ConversationFragment.access$800(ConversationFragment.this).setVisibility(8);
/* 574 */                       sendMessage(Message.obtain((Handler) null, 2));
                                break;
                        }
                    }
                }

/* 622 */       private void sendMessageToUploadService(Message msg) {
                    try {
/* 623 */               if (this.mUploadServiceMessenger != null) {
/* 624 */                   this.mUploadServiceMessenger.send(msg);
                        }
                    } catch (RemoteException e) {
/* 627 */               SLog.m446d("Failed to send message to upload service");
/* 628 */               if (BuildUtil.isLogsOn()) {
/* 629 */                   e.printStackTrace();
                        }
                    }
                }

/* 634 */       private void scrollToBottom(boolean respectCurrentPos, boolean smooth) {
/* 635 */           ListView listView = this.mListView;
/* 636 */           int count = this.mCursorAdapter.getCount() + listView.getHeaderViewsCount();
/* 637 */           if (!respectCurrentPos || count - listView.getLastVisiblePosition() < 2) {
/* 638 */               listView.post(new RunnableC01924(smooth, listView, count));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationFragment$4 */
/* 636 */       class RunnableC01924 implements Runnable {
                    final /* synthetic */ int val$count;
                    final /* synthetic */ ListView val$listView;
                    final /* synthetic */ boolean val$smooth;

/* 637 */           RunnableC01924(boolean z, ListView listView, int i) {
                        this.val$smooth = z;
                        this.val$listView = listView;
                        this.val$count = i;
                    }

                    @Override // java.lang.Runnable
/* 640 */           public void run() {
/* 641 */               if (this.val$smooth) {
/* 642 */                   this.val$listView.smoothScrollToPosition(this.val$count - 1);
                        } else {
/* 644 */                   this.val$listView.setSelectionFromTop(this.val$count - 1, 0);
                        }
                    }
                }

/* 651 */       private void fetchContent(int fetchType) {
/* 653 */           if (this.mConversationRowId > 0 && !hasPendingRequest()) {
                        switch (fetchType) {
                            case 1:
/* 662 */                       this.mFetchWasLoadMore = true;
/* 663 */                       showProgress(1);
/* 664 */                       addRequest(this.mAppController.fetchConversation(3, true, 0L, this.mConversationRowId, false));
                                break;
                            case 3:
/* 656 */                       this.mFetched = true;
/* 657 */                       showProgress(3);
/* 658 */                       addRequest(this.mAppController.fetchConversation(1, false, 0L, this.mConversationRowId, false));
                                break;
                        }
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment
/* 673 */       public void showProgress(int type) {
                    switch (type) {
                        case 1:
/* 676 */                   this.mLoadMoreHeaderContent.setVisibility(8);
/* 677 */                   this.mLoadMoreProgress.setVisibility(0);
                            break;
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment
/* 683 */       public void hideProgress(int type) {
/* 684 */           this.mLoadMoreProgress.setVisibility(8);
                }

/* 687 */       public void showTypingIndicator(boolean show, long userId) {
/* 688 */           ViewGroup container = this.mChatPresenceContainer;
/* 689 */           container.clearAnimation();
/* 691 */           if (show) {
/* 692 */               ImageView ellipsis = this.mChatPresenceEllipsis;
/* 693 */               ((ConversationAdapter) this.mCursorAdapter).getIdAtLastPosition();
/* 694 */               Integer color = null;
/* 695 */               if (this.mColorMap != null) {
/* 696 */                   Integer color2 = this.mColorMap.get(userId);
                            color = color2;
                        }
/* 699 */               if (color == null) {
/* 700 */                   color = Integer.valueOf(this.mRecipientColor);
                        }
/* 703 */               container.setVisibility(0);
/* 704 */               container.getLayoutParams().height = getResources().getDimensionPixelSize(C0257R.dimen.chat_presence_height);
/* 707 */               ellipsis.setColorFilter(1509949440 | (color.intValue() & ViewCompat.MEASURED_SIZE_MASK));
/* 709 */               scrollToBottom(true, false);
/* 719 */               return;
                    }
/* 711 */           HeightAnimation collapse = new HeightAnimation(container, new RunnableC01935(container));
/* 717 */           container.startAnimation(collapse);
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationFragment$5 */
/* 709 */       class RunnableC01935 implements Runnable {
                    final /* synthetic */ ViewGroup val$container;

/* 710 */           RunnableC01935(ViewGroup viewGroup) {
                        this.val$container = viewGroup;
                    }

                    @Override // java.lang.Runnable
/* 713 */           public void run() {
/* 714 */               this.val$container.setVisibility(8);
                    }
                }

/* 722 */       private class HeightAnimation extends Animation {
                    private View mView;

/* 723 */           public HeightAnimation(View view, Runnable onAnimationEnd) {
/* 725 */               setFillAfter(true);
/* 726 */               setDuration(300L);
/* 727 */               this.mView = view;
/* 728 */               setAnimationListener(new AnimationAnimationListenerC01961(ConversationFragment.this, onAnimationEnd));
                    }

                    /* JADX INFO: renamed from: co.vine.android.ConversationFragment$HeightAnimation$1 */
/* 726 */           class AnimationAnimationListenerC01961 implements Animation.AnimationListener {
                        final /* synthetic */ Runnable val$onAnimationEnd;
                        final /* synthetic */ ConversationFragment val$this$0;

/* 727 */               AnimationAnimationListenerC01961(ConversationFragment conversationFragment, Runnable runnable) {
                            this.val$this$0 = conversationFragment;
                            this.val$onAnimationEnd = runnable;
                        }

                        @Override // android.view.animation.Animation.AnimationListener
/* 729 */               public void onAnimationRepeat(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
/* 732 */               public void onAnimationStart(Animation animation) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
/* 736 */               public void onAnimationEnd(Animation animation) {
/* 737 */                   this.val$onAnimationEnd.run();
                        }
                    }

                    @Override // android.view.animation.Animation
/* 743 */           protected void applyTransformation(float interpolatedTime, Transformation t) {
/* 744 */               int height = ConversationFragment.this.getResources().getDimensionPixelSize(C0257R.dimen.chat_presence_height);
/* 745 */               this.mView.getLayoutParams().height = (int) (height * (1.0f - interpolatedTime));
/* 746 */               this.mView.requestLayout();
                    }

                    @Override // android.view.animation.Animation
/* 750 */           public boolean willChangeBounds() {
/* 751 */               return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationFragment$6 */
/* 753 */       class ViewOnClickListenerC01946 implements View.OnClickListener {
/* 754 */           ViewOnClickListenerC01946() {
                    }

                    @Override // android.view.View.OnClickListener
/* 758 */           public void onClick(View v) {
/* 759 */               ConversationFragment.this.launchFullRecord();
                    }
                }

                @Override // android.text.TextWatcher
/* 764 */       public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 765 */           if (this.mEdit.getText().length() == 0) {
/* 766 */               this.mSendButton.setImageResource(C0257R.drawable.ic_vm_capture_keyboard);
/* 767 */               this.mSendButton.setOnClickListener(this.mLaunchRecorderListener);
                    } else {
/* 769 */               this.mSendButton.setImageResource(C0257R.drawable.ic_action_send);
/* 770 */               this.mSendButton.setOnClickListener(this);
                    }
                }

/* 774 */       private Bundle getMessageBundle() {
/* 775 */           Bundle extras = new Bundle();
/* 776 */           extras.putLong("conversation_row_id", this.mConversationRowId);
/* 777 */           return extras;
                }

                @Override // android.text.TextWatcher
/* 781 */       public void afterTextChanged(Editable editable) {
/* 782 */           if (!TextUtils.isEmpty(editable)) {
/* 783 */               this.mCallback.onTypingStatusChanged(true, this.mIsSending);
                    } else {
/* 785 */               this.mCallback.onTypingStatusChanged(false, this.mIsSending);
                    }
                }

                @Override // android.text.TextWatcher
/* 790 */       public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.widget.TextView.OnEditorActionListener
/* 794 */       public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/* 795 */           if (v.getId() != 2131427449) {
                        return false;
                    }
/* 796 */           if (actionId == 4) {
/* 797 */               sendTextMessage();
                    }
/* 799 */           return true;
                }

                @Override // co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 806 */       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
/* 807 */           String conversationId = String.valueOf(this.mConversationRowId);
/* 811 */           Uri contentUri = Vine.ConversationMessageUsersView.CONTENT_URI_CONVERSATION.buildUpon().appendQueryParameter("conversation_row_id", conversationId).build();
/* 814 */           return new CursorLoader(getActivity(), contentUri, VineDatabaseSQL.ConversationMessageUsersQuery.PROJECTION, null, null, Vine.Messages.SORT_ORDER_DEFAULT);
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 821 */       public void onPause() {
/* 822 */           super.onPause();
/* 823 */           this.mConversationAdapter.onPause();
/* 824 */           getActivity().unregisterReceiver(this.mMuteChangeReceiver);
/* 826 */           if (this.mBound) {
/* 827 */               SLog.dWithTag(TAG, "Will unbind from VineUploadService now");
/* 829 */               sendMessageToUploadService(Message.obtain((Handler) null, 2));
/* 831 */               getActivity().unbindService(this.mConnection);
/* 832 */               this.mBound = false;
                    }
/* 834 */           if (this.mRecorderManager != null) {
/* 835 */               this.mRecorderManager.onPaused();
                    }
                }

                @Override // co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.Fragment
/* 840 */       public void onDestroy() {
/* 841 */           super.onDestroy();
/* 842 */           if (this.mRecorderManager != null) {
/* 843 */               this.mRecorderManager.release();
                    }
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // co.vine.android.BaseCursorAdapterFragment
/* 848 */       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    switch (loader.getId()) {
                        case 0:
/* 851 */                   saveTopItemInfo();
/* 852 */                   super.onLoadFinished(loader, cursor);
/* 853 */                   hideProgress(3);
/* 854 */                   if (!this.mFetched) {
/* 855 */                       fetchContent(3);
/* 856 */                       this.mFetchWasInitial = true;
                            } else {
/* 858 */                       if (this.mCursorAdapter.isEmpty()) {
/* 859 */                           showSadface(true, false);
                                } else {
/* 861 */                           showSadface(false);
/* 862 */                           if (this.mFetchWasLoadMore) {
/* 863 */                               this.mFetchWasLoadMore = false;
/* 864 */                               this.mLoadMoreHeaderContent.setVisibility(0);
/* 865 */                               hideProgress(3);
/* 866 */                               this.mListView.setSelectionFromTop(((ConversationAdapter) this.mCursorAdapter).getPositionForId(this.mLastTopItemId) + this.mListView.getHeaderViewsCount(), this.mLastTopItemPixelOffset);
                                    } else if (this.mFetchWasInitial) {
/* 869 */                               scrollToBottom(false, false);
                                    } else if (this.mListView.getLastVisiblePosition() >= cursor.getCount() - 1) {
/* 871 */                               scrollToBottom(false, true);
                                    }
                                }
/* 874 */                       this.mFetchWasInitial = false;
/* 875 */                       if (((ConversationAdapter) this.mCursorAdapter).atLastPage()) {
/* 876 */                           this.mLoadMoreHeaderContent.setVisibility(8);
                                } else {
/* 878 */                           this.mLoadMoreHeaderContent.setVisibility(0);
                                }
/* 880 */                       if (!this.mCursorAdapter.isEmpty()) {
/* 881 */                           ((ConversationAdapter) this.mCursorAdapter).playCurrentPosition();
                                }
                            }
                            break;
                    }
                }

/* 889 */       private void saveTopItemInfo() {
/* 890 */           if (this.mCursorAdapter.getCount() != 0) {
/* 893 */               this.mLastTopItemId = this.mCursorAdapter.getItemId(this.mListView.getFirstVisiblePosition());
/* 894 */               View topChild = this.mListView.getChildAt(this.mListView.getFirstVisiblePosition() + this.mListView.getHeaderViewsCount());
/* 895 */               if (topChild != null) {
/* 896 */                   this.mLastTopItemPixelOffset = topChild.getTop();
                        }
                    }
                }

/* 900 */       private void sendTextMessage() {
/* 901 */           EditText editText = this.mEdit;
/* 902 */           if (editText.getText().length() > 420) {
/* 903 */               Util.showTopToast(getActivity(), C0257R.string.message_length_exceeded);
/* 919 */               return;
                    }
/* 905 */           if (validate(this.mEdit)) {
/* 906 */               String message = editText.getText().toString();
/* 907 */               Intent i = VineUploadService.getVMPostIntent(getActivity(), null, false, this.mConversationRowId, VineProviderHelper.getConversationRecipients(this.mContentResolver, this.mConversationRowId), message, 0);
/* 910 */               getActivity().startService(i);
/* 911 */               this.mIsSending = true;
/* 912 */               editText.getText().clear();
/* 913 */               this.mIsSending = false;
/* 919 */               return;
                    }
/* 915 */           Util.setSoftKeyboardVisibility(getActivity(), this.mEdit, false);
/* 916 */           hideTypingContainer();
                }

/* 921 */       private void hideTypingContainer() {
/* 922 */           this.mEdit.clearFocus();
/* 923 */           if (this.mLocalUnreadCount > 0) {
/* 924 */               toggleNewMessageBar(true);
                    }
/* 926 */           this.mTextInputContainer.setVisibility(8);
/* 927 */           ViewUtil.setBackground(this.mComposeContainer, new ColorDrawable(getMyColor()));
/* 929 */           this.mRecordButton.setVisibility(0);
/* 930 */           this.mTextInputToggle.setVisibility(0);
                }

/* 933 */       private boolean validate(EditText editText) {
                    return editText != null && editText.length() > 0;
                }

/* 937 */       private void toggleInput() {
/* 938 */           FragmentActivity activity = getActivity();
/* 939 */           if (activity != null) {
/* 940 */               EditText edit = this.mEdit;
/* 941 */               edit.requestFocus();
/* 942 */               Util.setSoftKeyboardVisibility(activity, edit, true);
/* 943 */               this.mTextInputContainer.setVisibility(0);
/* 944 */               ViewUtil.setBackground(this.mComposeContainer.findViewById(C0257R.id.compose_container), new ColorDrawable(activity.getResources().getColor(C0257R.color.solid_white)));
/* 946 */               this.mRecordButton.setVisibility(8);
/* 947 */               this.mTextInputToggle.setVisibility(8);
                    }
                }

                @Override // android.view.View.OnClickListener
/* 952 */       public void onClick(View view) {
/* 953 */           int id = view.getId();
/* 954 */           if (id == 2131427473) {
/* 955 */               sendTextMessage();
/* 967 */               return;
                    }
/* 956 */           if (id == 2131427470) {
/* 957 */               toggleInput();
/* 967 */               return;
                    }
/* 958 */           if (id == 2131427494) {
/* 959 */               this.mLoadMoreHeaderContent.setVisibility(8);
/* 960 */               this.mLoadMoreProgress.setVisibility(0);
/* 961 */               fetchContent(1);
                    } else if (id == 2131427489) {
/* 963 */               scrollToBottom(false, false);
/* 964 */               toggleNewMessageBar(false);
/* 965 */               this.mLocalUnreadCount = 0;
                    }
                }

/* 969 */       public void onNewMessage() {
/* 970 */           if (this.mListView.getLastVisiblePosition() < this.mCursorAdapter.getCount() && this.mTextInputContainer.getVisibility() != 0) {
/* 972 */               this.mLocalUnreadCount++;
/* 973 */               this.mNewMessageBarText.setText(getResources().getQuantityString(C0257R.plurals.new_messages, this.mLocalUnreadCount, Integer.valueOf(this.mLocalUnreadCount)));
/* 975 */               toggleNewMessageBar(true);
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 980 */       protected void onScrollLastItem(Cursor cursor) {
/* 981 */           this.mLocalUnreadCount = 0;
/* 982 */           toggleNewMessageBar(false);
                }

/* 985 */       private void toggleNewMessageBar(boolean show) {
/* 986 */           this.mNewMessageBarContainer.setVisibility(show ? 0 : 8);
                }

/* 989 */       public boolean isInTextInputMode() {
                    return this.mRecordButton.getVisibility() == 8;
                }

/* 993 */       void launchFullRecord() {
/* 994 */           this.mFullRecordIntent = AbstractRecordingActivity.getIntentForConversation(getActivity(), 0, "ConversationFullRecord", this.mConversationRowId, false, this.mRecipientUsername, this.mAmFollowing);
/* 996 */           this.mIncomingHandler = new UploadProgressHandler(this, null);
/* 997 */           subscribe(this.mIncomingHandler);
/* 998 */           this.mHandler.post(this.mLaunchFullRecordRunnable);
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationFragment$7 */
/* 999 */       class RunnableC01957 implements Runnable {
/* 1000 */          RunnableC01957() {
                    }

                    @Override // java.lang.Runnable
/* 1003 */          public void run() {
/* 1004 */              FragmentActivity activity = ConversationFragment.this.getActivity();
/* 1005 */              if (activity != null) {
/* 1006 */                  Util.startActionOnRecordingAvailable(activity, ConversationFragment.access$1200(ConversationFragment.this), 1991);
                        }
                    }
                }

/* 1009 */      private class ConversationSessionListener extends AppSessionListener {
/* 1010 */          private ConversationSessionListener() {
                    }

/* 1010 */          /* synthetic */ ConversationSessionListener(ConversationFragment x0, C01891 x1) {
/* 1011 */              this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1013 */          public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
/* 1014 */              ((ConversationAdapter) ConversationFragment.this.mCursorAdapter).onVideoPathObtained(videos);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1018 */          public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 1019 */              ((ConversationAdapter) ConversationFragment.this.mCursorAdapter).setImages(images);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1025 */          public void onGetConversationComplete(String reqId, int statusCode, String reasonPhrase, long conversationRowId, int responseCode, boolean empty) {
/* 1026 */              PendingRequest req = ConversationFragment.this.removeRequest(reqId);
/* 1027 */              if (req != null && conversationRowId == ConversationFragment.access$700(ConversationFragment.this)) {
/* 1028 */                  ConversationFragment.this.hideProgress(3);
/* 1029 */                  if (empty) {
/* 1030 */                      ConversationFragment.this.showSadface(true, false);
                            } else {
/* 1032 */                      ConversationFragment.this.showSadface(false);
                            }
/* 1035 */                  ConversationAdapter adapter = (ConversationAdapter) ConversationFragment.this.mCursorAdapter;
/* 1036 */                  if (ConversationFragment.access$1300(ConversationFragment.this) && (adapter.atLastPage() || empty || adapter.isEmpty())) {
/* 1037 */                      ConversationFragment.access$1400(ConversationFragment.this).setVisibility(8);
                            }
/* 1040 */                  if (responseCode == 4) {
/* 1041 */                      ConversationFragment.access$1400(ConversationFragment.this).setVisibility(0);
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 1047 */          public void onGetConversationRemoteIdComplete(long conversationId) {
/* 1048 */              ConversationFragment.access$1502(ConversationFragment.this, conversationId);
                        if (conversationId <= 0) {
/* 1051 */                  ConversationFragment.access$1400(ConversationFragment.this).setVisibility(8);
                        }
                    }
                }
            }
