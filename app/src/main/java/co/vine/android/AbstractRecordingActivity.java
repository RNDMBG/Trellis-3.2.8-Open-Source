            package co.vine.android;

            import android.animation.Animator;
            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.content.res.Resources;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.Point;
            import android.graphics.drawable.BitmapDrawable;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Process;
            import android.support.annotation.NonNull;
            import android.support.v4.app.DraftFragmentStatePagerAdapter;
            import android.support.v4.app.Fragment;
            import android.support.v4.app.FragmentManager;
            import android.support.v4.app.FragmentTransaction;
            import android.support.v4.view.DraftViewPager;
            import android.text.TextUtils;
            import android.util.SparseArray;
            import android.util.TypedValue;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.view.animation.AnimationSet;
            import android.view.animation.AnimationUtils;
            import android.widget.ImageView;
            import android.widget.RelativeLayout;
            import co.vine.android.animation.MoveResizeAnimator;
            import co.vine.android.animation.SimpleAnimationListener;
            import co.vine.android.animation.SimpleAnimatorListener;
            import co.vine.android.animation.SmoothAnimator;
            import co.vine.android.client.AppController;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.plugin.BaseRecorderPluginManager;
            import co.vine.android.plugin.CameraSwitcherPlugin;
            import co.vine.android.plugin.DeleteLastSegmentPlugin;
            import co.vine.android.plugin.DraftPlugin;
            import co.vine.android.plugin.FocusPlugin;
            import co.vine.android.plugin.GhostPlugin;
            import co.vine.android.plugin.GridPlugin;
            import co.vine.android.plugin.ImportOnboardPlugin;
            import co.vine.android.plugin.ImportPlugin;
            import co.vine.android.plugin.IndividualEditPlugin;
            import co.vine.android.plugin.MoreToolPlugin;
            import co.vine.android.plugin.RecorderPlugin;
            import co.vine.android.plugin.SecretModePlugin;
            import co.vine.android.plugin.TimeLapsePlugin;
            import co.vine.android.plugin.ZoomPlugin;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.ByteBufferQueue;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSessionManager;
            import co.vine.android.recorder.RecordSessionVersion;
            import co.vine.android.recorder.RecordingActivityHelper;
            import co.vine.android.recorder.RecordingFile;
            import co.vine.android.recorder.RegularVineRecorder;
            import co.vine.android.recorder.ViewGoneAnimationListener;
            import co.vine.android.recorder.VineRecorder;
            import co.vine.android.recorder.camera.CameraManager;
            import co.vine.android.service.VineUploadService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.FloatingViewUtils;
            import co.vine.android.util.IntentionalObjectCounter;
            import co.vine.android.util.MediaUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.UploadManager;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.DotIndicators;
            import co.vine.android.widget.DragUpToDeleteContainer;
            import co.vine.android.widgets.PromptDialogFragment;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.IOException;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 98 */    public abstract class AbstractRecordingActivity extends BaseActionBarActivity implements DraftViewPager.OnPageChangeListener, SmoothAnimator.AnimationListener, DragUpToDeleteContainer.DragUpListener, ByteBufferQueue.MemoryResponder {
                private static final int ANIMATION_CAMERA_ICON = 4;
                private static final int ANIMATION_DELETE = 3;
                private static final int ANIMATION_PREVIEW = 1;
                private static final int ANIMATION_PROGRESS = 2;
                private static final int ANIMATION_PROGRESS_TO_DRAFTS = 6;
                private static final int ANIMATION_TO_DRAFTS = 5;
                private static final int ANIMATION_TO_DRAFTS_CAMERA = 7;
                private static final int ANIM_DURATION = 300;
                public static final int DIALOG_DELETE = 1;
                public static final int DIALOG_PLUGIN = -16777216;
                public static final int DIALOG_TOO_MANY_DRAFTS = 3;
                public static final int DIALOG_UNSAVED_CHANGES = 0;
                protected static final String EXTRA_AM_FOLLOWING = "am_following";
                protected static final String EXTRA_CONVERSATION_ROW_ID = "conv_row_id";
                protected static final String EXTRA_DIRECT_USER_ID = "direct_id";
                protected static final String EXTRA_MESSAGING = "messaging";
                protected static final String EXTRA_RECIPIENT_USERNAME = "recipient_username";
                protected static final String EXTRA_START_CONVERSATION = "start_conversation";
                private static final String EXTRA_TOP_OVERLAY = "arg_top_overlay";
                private static final int PAGE_INVALID = -1;
                public static final String PREF_CAPTURE_LAUNCH_COUNT = "recorder_launch_count";
                public static final int REQUEST_IMPORT_PICK_VIDEO = 1;
                public static final int RESULT_RECORDING_CANCELLED = 100;
                public static final String START_SESSION_ID_CAMERA = "camera_preview";
                private static final String STATE_FIRST_DRAFT_LAUNCH = "first_launch";
                private static final String STATE_FIRST_PAGE_SET = "state_fp";
                private static final int STEP_NONE = -1;
                private static final int STEP_PREVIEW = 1;
                private static final int STEP_RECORD = 0;
                private static final ArrayList<WeakReference<Object>> sQueues = new ArrayList<>();
                private DraftPagerImpl mAdapter;
                private Thread mAddToUploadThread;
                private View mAnimationPreviewOverlay;
                private WeakReference<DraftCameraPreviewFragment> mCameraFragment;
                private Animation mCameraIconFadeIn;
                private View mCameraIconOverlay;
                private View mCameraIconOverlayImage;
                protected int mColor;
                protected long mConversationRowId;
                public long mCurrentDuration;
                protected Fragment mCurrentFragment;
                int mCurrentPage;
                private RecordSessionManager.RecordSessionInfo mCurrentSession;
                protected long mDirectUserId;
                private DotIndicators mDots;
                private View mDraftFullMask;
                private View mDraftMaskLeft;
                private View mDraftMaskParent;
                private View mDraftMaskRight;
                private View mDraftMaskTop;
                private View mDraftRoot;
                private View mDraftTrashBackground;
                private View mDraftTrashButton;
                private View mDraftTrashContainer;
                private DragUpToDeleteContainer mDragUpToDeleteView;
                private boolean mFirstDraftLaunch;
                private boolean mFirstPageSet;
                private boolean mHasPreviewedAlready;
                private boolean mHasStartedRelativeTimeFromHardwareButton;
                private boolean mIsGoingToRecord;
                protected boolean mIsMessaging;
                private boolean mIsResumed;
                private ImageView mLargeThumbnailOverlay;
                private SetSelectedRunnable mOnPageIdleRunnable;
                private int mPageScrollState;
                int mPreviewDimen;
                private View mPreviewOverlay;
                float mPreviewRatio;
                public int mProgressContainerWidth;
                private View mProgressOverlay;
                private PromptDialogFragment mPromptDialog;
                private String mRecipientUsername;
                private int mRegularUiMode;
                private Point mScreenSize;
                private ArrayList<RecordSessionManager.RecordSessionInfo> mSessions;
                private int mSideMaskWidth;
                private Animation mSlowFadeIn;
                private boolean mStartConversation;
                private String mStartSessionId;
                private int mTopMaskHeight;
                private Bitmap mTopOverlay;
                protected String mUploadFile;
                private RecordSessionVersion mVersion;
                private DraftViewPager mViewPager;
                private final IntentionalObjectCounter mIntentionalObjectCounter = new IntentionalObjectCounter(IntentionalObjectCounter.RECORDER_INSTANCE_COUNT, this);
                private final Handler mHandler = new Handler();
                private final SparseArray<WeakReference<DraftFragment>> mDraftFragments = new SparseArray<>();
                private ArrayList<SdkVideoView> mVideoViews = new ArrayList<>();
                private int mStep = -1;
                private boolean mDeleteWasDrag = false;
                private final View.OnClickListener mEmptyClicker = new ViewOnClickListenerC01301();
                private boolean mAnimatingIntoDrafts = false;
                final View.OnTouchListener mOnMaskTouchListesner = new ViewOnTouchListenerC01372();
                private final RecordingActivityHelper mHelper = new RecordingActivityHelper();
                private boolean mAmFollowingRecipient = false;
                private final PromptDialogFragment.OnDialogDoneListener mDeleteDialogDoneListener = new C01427();
                private final PromptDialogFragment.OnDialogDoneListener mUnsavedChangesDialogDoneListener = new C01438();
                private final Runnable mOnResumeDraftRunnable = new RunnableC013413();

/* 99 */        static /* synthetic */ SparseArray access$000(AbstractRecordingActivity x0) {
/* 100 */           return x0.mDraftFragments;
                }

/* 99 */        static /* synthetic */ View access$1000(AbstractRecordingActivity x0) {
/* 100 */           return x0.mCameraIconOverlay;
                }

/* 99 */        static /* synthetic */ boolean access$102(AbstractRecordingActivity x0, boolean x1) {
/* 100 */           x0.mDeleteWasDrag = x1;
                    return x1;
                }

/* 99 */        static /* synthetic */ ImageView access$1100(AbstractRecordingActivity x0) {
/* 100 */           return x0.mLargeThumbnailOverlay;
                }

/* 99 */        static /* synthetic */ View access$1200(AbstractRecordingActivity x0) {
/* 100 */           return x0.mAnimationPreviewOverlay;
                }

/* 99 */        static /* synthetic */ Runnable access$1300(AbstractRecordingActivity x0) {
/* 100 */           return x0.mOnResumeDraftRunnable;
                }

/* 99 */        static /* synthetic */ Handler access$1400(AbstractRecordingActivity x0) {
/* 100 */           return x0.mHandler;
                }

/* 99 */        static /* synthetic */ boolean access$1500(AbstractRecordingActivity x0) {
/* 100 */           return x0.isResuming();
                }

/* 99 */        static /* synthetic */ View access$1600(AbstractRecordingActivity x0) {
/* 100 */           return x0.mCameraIconOverlayImage;
                }

/* 99 */        static /* synthetic */ Animation access$1700(AbstractRecordingActivity x0) {
/* 100 */           return x0.mSlowFadeIn;
                }

/* 99 */        static /* synthetic */ Animation access$1800(AbstractRecordingActivity x0) {
/* 100 */           return x0.mCameraIconFadeIn;
                }

/* 99 */        static /* synthetic */ boolean access$1900(AbstractRecordingActivity x0) {
/* 100 */           return x0.mAnimatingIntoDrafts;
                }

/* 99 */        static /* synthetic */ boolean access$1902(AbstractRecordingActivity x0, boolean x1) {
/* 100 */           x0.mAnimatingIntoDrafts = x1;
                    return x1;
                }

/* 99 */        static /* synthetic */ View access$200(AbstractRecordingActivity x0) {
/* 100 */           return x0.mDraftTrashBackground;
                }

/* 99 */        static /* synthetic */ View access$2000(AbstractRecordingActivity x0) {
/* 100 */           return x0.mDraftFullMask;
                }

/* 99 */        static /* synthetic */ DraftViewPager access$2100(AbstractRecordingActivity x0) {
/* 100 */           return x0.mViewPager;
                }

/* 99 */        static /* synthetic */ int access$2202(AbstractRecordingActivity x0, int x1) {
/* 100 */           x0.mPageScrollState = x1;
                    return x1;
                }

/* 99 */        static /* synthetic */ View access$2300(AbstractRecordingActivity x0) {
/* 100 */           return x0.mDraftMaskTop;
                }

/* 99 */        static /* synthetic */ View access$2400(AbstractRecordingActivity x0) {
/* 100 */           return x0.mDraftMaskLeft;
                }

/* 99 */        static /* synthetic */ View access$2500(AbstractRecordingActivity x0) {
/* 100 */           return x0.mDraftMaskRight;
                }

/* 99 */        static /* synthetic */ Thread access$2602(AbstractRecordingActivity x0, Thread x1) {
/* 100 */           x0.mAddToUploadThread = x1;
                    return x1;
                }

/* 99 */        static /* synthetic */ DraftPagerImpl access$2700(AbstractRecordingActivity x0) {
/* 100 */           return x0.mAdapter;
                }

/* 99 */        static /* synthetic */ RecordSessionManager.RecordSessionInfo access$2802(AbstractRecordingActivity x0, RecordSessionManager.RecordSessionInfo x1) {
/* 100 */           x0.mCurrentSession = x1;
                    return x1;
                }

/* 99 */        static /* synthetic */ ArrayList access$2900(AbstractRecordingActivity x0) {
/* 100 */           return x0.mSessions;
                }

/* 99 */        static /* synthetic */ View access$300(AbstractRecordingActivity x0) {
/* 100 */           return x0.mDraftTrashButton;
                }

/* 99 */        static /* synthetic */ WeakReference access$3002(AbstractRecordingActivity x0, WeakReference x1) {
/* 100 */           x0.mCameraFragment = x1;
                    return x1;
                }

/* 99 */        static /* synthetic */ boolean access$3100(AbstractRecordingActivity x0) {
/* 100 */           return x0.mFirstPageSet;
                }

/* 99 */        static /* synthetic */ boolean access$3202(AbstractRecordingActivity x0, boolean x1) {
/* 100 */           x0.mIsGoingToRecord = x1;
                    return x1;
                }

/* 99 */        static /* synthetic */ void access$3300(AbstractRecordingActivity x0, DraftFragment x1) {
/* 100 */           x0.buildAndShowFloatingView(x1);
                }

/* 99 */        static /* synthetic */ void access$3400(AbstractRecordingActivity x0) {
/* 100 */           x0.releasePlayers();
                }

/* 99 */        static /* synthetic */ Point access$3500(AbstractRecordingActivity x0) {
/* 100 */           return x0.mScreenSize;
                }

/* 99 */        static /* synthetic */ View access$3600(AbstractRecordingActivity x0) {
/* 100 */           return x0.mPreviewOverlay;
                }

/* 99 */        static /* synthetic */ View access$3700(AbstractRecordingActivity x0) {
/* 100 */           return x0.mProgressOverlay;
                }

/* 99 */        static /* synthetic */ boolean access$3800(AbstractRecordingActivity x0) {
/* 100 */           return x0.mFirstDraftLaunch;
                }

/* 99 */        static /* synthetic */ boolean access$3802(AbstractRecordingActivity x0, boolean x1) {
/* 100 */           x0.mFirstDraftLaunch = x1;
                    return x1;
                }

/* 99 */        static /* synthetic */ boolean access$3900(AbstractRecordingActivity x0) {
/* 100 */           return x0.setCurrentSession();
                }

/* 99 */        static /* synthetic */ View access$400(AbstractRecordingActivity x0) {
/* 100 */           return x0.mDraftTrashContainer;
                }

/* 99 */        static /* synthetic */ void access$500(AbstractRecordingActivity x0) {
/* 100 */           x0.handleDelete();
                }

/* 99 */        static /* synthetic */ void access$600(AbstractRecordingActivity x0) {
/* 100 */           x0.showCurrentFragment();
                }

/* 99 */        static /* synthetic */ View access$700(AbstractRecordingActivity x0) {
/* 100 */           return x0.mDraftMaskParent;
                }

/* 99 */        static /* synthetic */ View access$800(AbstractRecordingActivity x0) {
/* 100 */           return x0.mDraftRoot;
                }

/* 99 */        static /* synthetic */ void access$900(AbstractRecordingActivity x0) {
/* 100 */           x0.hideDraftsInternal();
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$1 */
/* 207 */       class ViewOnClickListenerC01301 implements View.OnClickListener {
/* 208 */           ViewOnClickListenerC01301() {
                    }

                    @Override // android.view.View.OnClickListener
/* 212 */           public void onClick(View v) {
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$2 */
/* 227 */       class ViewOnTouchListenerC01372 implements View.OnTouchListener {
/* 228 */           ViewOnTouchListenerC01372() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 230 */           public boolean onTouch(View v, MotionEvent event) {
/* 231 */               return true;
                    }
                }

                @NonNull
/* 246 */       private RecordSessionVersion getVersion() {
/* 247 */           if (this.mVersion == null) {
/* 248 */               this.mVersion = RecordSessionManager.getCurrentVersion(this);
                    }
/* 250 */           return this.mVersion;
                }

/* 254 */       public static Intent getIntentForGeneric(Context context, int flags, String tag) {
/* 255 */           return getIntent(context, flags, tag, false, -1L, -1L, false, null, false);
                }

                @Override // co.vine.android.recorder.ByteBufferQueue.MemoryResponder
/* 260 */       public void requestForMoreMemory() {
                    try {
/* 261 */               AppController.getInstance(this).clearImageCacheFromMemory();
                    } catch (RuntimeException e) {
/* 263 */               CrashUtil.log("Failed to clear image cache memory.");
                    }
                }

/* 268 */       public static Intent getIntentForMessaging(Context context, int flags, String tag) {
/* 269 */           return getIntent(context, flags, tag, true, -1L, -1L, false, null, false);
                }

/* 274 */       public static Intent getIntentForConversation(Context context, int flags, String tag, long conversationRowId, boolean startConversation, String username, boolean amFollowing) {
/* 275 */           return getIntent(context, flags, tag, true, conversationRowId, -1L, startConversation, username, amFollowing);
                }

/* 282 */       private static Intent getIntent(Context context, int flags, String tag, boolean isMessaging, long conversationRowId, long directUserId, boolean startConversation, String recipientName, boolean amFollowing) {
/* 283 */           Intent intent = new Intent(context, (Class<?>) RecordingActivity.class);
/* 284 */           intent.setAction(tag);
/* 285 */           if (flags > 0) {
/* 286 */               intent.setFlags(flags);
                    }
/* 288 */           intent.putExtra("messaging", isMessaging);
/* 289 */           intent.putExtra(EXTRA_CONVERSATION_ROW_ID, conversationRowId);
/* 290 */           intent.putExtra(EXTRA_DIRECT_USER_ID, directUserId);
/* 291 */           intent.putExtra(EXTRA_START_CONVERSATION, startConversation);
/* 292 */           intent.putExtra("recipient_username", recipientName);
/* 293 */           intent.putExtra("am_following", amFollowing);
/* 294 */           return intent;
                }

                @Override // co.vine.android.BaseActionBarActivity
/* 298 */       public void preSetContentView() {
/* 299 */           if (!CameraManager.hasCamera()) {
/* 300 */               Util.showNoCameraToast(this);
/* 301 */               finish();
                    }
                }

/* 306 */       public AbstractRecordingActivity() {
/* 308 */           if (sQueues.size() > 20) {
/* 309 */               sQueues.clear();
                    }
/* 311 */           sQueues.add(new WeakReference<>(this));
/* 312 */           int n = 0;
                    for (WeakReference<Object> queue : sQueues) {
/* 314 */               if (queue.get() != null) {
/* 315 */                   n++;
                        }
                    }
/* 318 */           CrashUtil.log("[mem] Current RecordingActivity queue count: {}.", Integer.valueOf(n));
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 322 */       public void onCreate(Bundle savedInstanceState) {
                    Bundle extras;
/* 323 */           Intent intent = getIntent();
/* 324 */           String action = null;
/* 325 */           if (intent != null) {
/* 326 */               action = intent.getAction();
                    }
/* 328 */           CrashUtil.log("AbstractRecordingActivity {} pid: {}, action tag {}.", this, Integer.valueOf(Process.myPid()), action);
/* 330 */           this.mIntentionalObjectCounter.add();
/* 331 */           FlurryUtils.trackRecordingStart();
/* 334 */           super.onCreate(savedInstanceState, C0257R.layout.record_draft, false, true);
/* 335 */           this.mHelper.bindCameraService(this);
/* 336 */           this.mScreenSize = SystemUtil.getDisplaySize((Activity) this);
                    if (SystemUtil.getMemoryRatio(this, true) < 1.0d) {
/* 339 */               Util.showCenteredToast(this, C0257R.string.unsupported_feature_recording);
/* 340 */               finish();
/* 482 */               return;
                    }
/* 344 */           this.mVersion = RecordSessionManager.getCurrentVersion(this);
/* 346 */           if (savedInstanceState == null) {
/* 347 */               if (intent != null && (extras = intent.getExtras()) != null) {
/* 350 */                   this.mIsMessaging = extras.getBoolean("messaging");
/* 351 */                   this.mConversationRowId = extras.getLong(EXTRA_CONVERSATION_ROW_ID, -1L);
/* 352 */                   this.mTopOverlay = (Bitmap) extras.getParcelable("arg_top_overlay");
/* 353 */                   this.mDirectUserId = extras.getLong(EXTRA_DIRECT_USER_ID, -1L);
/* 354 */                   this.mStartConversation = extras.getBoolean(EXTRA_START_CONVERSATION);
/* 355 */                   this.mRecipientUsername = extras.getString("recipient_username");
                        }
                        try {
/* 359 */                   toRecord(true, false, null);
                        } catch (InvalidStateException e) {
/* 361 */                   throw new RuntimeException(e);
                        }
                    }
/* 364 */           VineAccountHelper.resetActiveSession();
/* 365 */           startService(VineUploadService.getClearNotificationsIntent(this));
/* 367 */           Resources resources = getResources();
/* 368 */           this.mPreviewRatio = 0.85f;
/* 369 */           this.mDraftRoot = findViewById(C0257R.id.drafts_root);
/* 370 */           this.mDraftRoot.setVisibility(8);
/* 371 */           this.mViewPager = (DraftViewPager) findViewById(C0257R.id.pager);
/* 372 */           this.mDots = (DotIndicators) findViewById(C0257R.id.dots);
/* 373 */           this.mPreviewDimen = (int) (this.mScreenSize.x * this.mPreviewRatio);
/* 374 */           this.mPreviewOverlay = findViewById(C0257R.id.preview_overlay);
/* 375 */           this.mProgressOverlay = findViewById(C0257R.id.progress_overlay);
/* 377 */           this.mDraftMaskTop = findViewById(C0257R.id.toDraftTopMask);
/* 378 */           this.mDraftMaskTop.setOnClickListener(this.mEmptyClicker);
/* 379 */           this.mDraftMaskLeft = findViewById(C0257R.id.toDraftLeftMask);
/* 380 */           this.mDraftMaskLeft.setOnClickListener(this.mEmptyClicker);
/* 381 */           this.mDraftMaskRight = findViewById(C0257R.id.toDraftRightMask);
/* 382 */           this.mDraftMaskRight.setOnClickListener(this.mEmptyClicker);
/* 383 */           this.mDraftMaskParent = findViewById(C0257R.id.toDraftParent);
/* 384 */           this.mDraftTrashContainer = findViewById(C0257R.id.draftsTrashContainer);
/* 385 */           this.mDraftTrashBackground = findViewById(C0257R.id.draftsTrashBackground);
/* 386 */           this.mDraftTrashButton = findViewById(C0257R.id.draftsTrashButton);
/* 387 */           this.mDraftTrashButton.setOnClickListener(new ViewOnClickListenerC01383());
/* 396 */           this.mDraftTrashButton.setOnTouchListener(new ViewOnTouchListenerC01394());
/* 426 */           this.mDragUpToDeleteView = (DragUpToDeleteContainer) findViewById(C0257R.id.upDragger);
/* 427 */           this.mDragUpToDeleteView.setVisibility(8);
/* 428 */           this.mDragUpToDeleteView.setInteractionListner(this);
/* 429 */           this.mDraftTrashContainer.setVisibility(8);
/* 431 */           int draftProgDimen = resources.getDimensionPixelOffset(C0257R.dimen.draft_progress_view_height);
/* 432 */           int progressDimen = resources.getDimensionPixelOffset(C0257R.dimen.progress_view_height);
/* 434 */           this.mTopMaskHeight = progressDimen + draftProgDimen;
/* 435 */           this.mSideMaskWidth = (this.mScreenSize.x / 2) - (this.mPreviewDimen / 2);
/* 437 */           View pagerLeftMask = findViewById(C0257R.id.pagerLeftMask);
/* 438 */           ViewGroup.LayoutParams params = pagerLeftMask.getLayoutParams();
/* 439 */           params.width = (int) (this.mPreviewRatio + (this.mScreenSize.x * ((1.0f - this.mPreviewRatio) / 2.0f)));
/* 440 */           pagerLeftMask.setLayoutParams(params);
/* 442 */           if (savedInstanceState != null) {
/* 443 */               if (savedInstanceState.containsKey(STATE_FIRST_PAGE_SET)) {
/* 444 */                   this.mFirstPageSet = savedInstanceState.getBoolean(STATE_FIRST_PAGE_SET);
                        }
/* 446 */               if (savedInstanceState.containsKey(STATE_FIRST_DRAFT_LAUNCH)) {
/* 447 */                   this.mFirstDraftLaunch = savedInstanceState.getBoolean(STATE_FIRST_DRAFT_LAUNCH);
                        } else {
/* 449 */                   this.mFirstDraftLaunch = true;
                        }
                    } else {
/* 452 */               this.mFirstDraftLaunch = true;
                    }
/* 455 */           int bottomRowDimen = resources.getDimensionPixelOffset(C0257R.dimen.draft_dots_height);
/* 456 */           ViewGroup.LayoutParams viewPagerLayout = this.mViewPager.getLayoutParams();
/* 457 */           viewPagerLayout.height = this.mScreenSize.x + progressDimen + bottomRowDimen;
/* 458 */           this.mViewPager.setLayoutParams(viewPagerLayout);
/* 460 */           int dotsHeight = resources.getDimensionPixelOffset(C0257R.dimen.draft_dots_height);
/* 461 */           this.mDots.setY(this.mScreenSize.x + progressDimen + (dotsHeight / 4));
/* 463 */           this.mLargeThumbnailOverlay = (ImageView) findViewById(C0257R.id.large_thumbnail_overlay);
/* 464 */           this.mAnimationPreviewOverlay = findViewById(C0257R.id.animation_preview_overlay);
/* 466 */           this.mCameraIconOverlay = findViewById(C0257R.id.draft_camera_icon_overlay);
/* 467 */           this.mCameraIconOverlay.setVisibility(8);
/* 469 */           this.mCameraIconOverlayImage = findViewById(C0257R.id.preview_icon);
/* 471 */           this.mDraftFullMask = findViewById(C0257R.id.draft_full_mask);
/* 473 */           this.mSlowFadeIn = AnimationUtils.loadAnimation(this, C0257R.anim.slow_fade_in);
/* 474 */           this.mCameraIconFadeIn = AnimationUtils.loadAnimation(this, C0257R.anim.slow_fade_in);
/* 476 */           if (!this.mIsMessaging) {
/* 477 */               SharedPreferences prefs = getSharedPreferences(BasicVineRecorder.PREFS_CAPTURE, 0);
/* 479 */               int launchCount = prefs.getInt(PREF_CAPTURE_LAUNCH_COUNT, 0) + 1;
/* 480 */               prefs.edit().putInt(PREF_CAPTURE_LAUNCH_COUNT, launchCount).apply();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$3 */
/* 385 */       class ViewOnClickListenerC01383 implements View.OnClickListener {
/* 386 */           ViewOnClickListenerC01383() {
                    }

                    @Override // android.view.View.OnClickListener
/* 389 */           public void onClick(View view) {
/* 390 */               if (AbstractRecordingActivity.this.mCurrentPage != AbstractRecordingActivity.access$000(AbstractRecordingActivity.this).size()) {
/* 391 */                   AbstractRecordingActivity.access$102(AbstractRecordingActivity.this, false);
/* 392 */                   AbstractRecordingActivity.this.showDeleteDialog();
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$4 */
/* 394 */       class ViewOnTouchListenerC01394 implements View.OnTouchListener {
                    boolean hasMovedOut = false;

/* 395 */           ViewOnTouchListenerC01394() {
                    }

                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    @Override // android.view.View.OnTouchListener
/* 400 */           public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getAction()) {
                            case 0:
/* 403 */                       if (AbstractRecordingActivity.this.mCurrentPage != AbstractRecordingActivity.access$000(AbstractRecordingActivity.this).size()) {
/* 404 */                           AbstractRecordingActivity.access$200(AbstractRecordingActivity.this).setActivated(true);
/* 405 */                           this.hasMovedOut = false;
                                }
                                return false;
                            case 1:
/* 409 */                       AbstractRecordingActivity.access$200(AbstractRecordingActivity.this).setActivated(false);
                                return false;
                            case 2:
/* 412 */                       float x = event.getX();
/* 413 */                       float y = event.getY();
                                boolean activate = x >= 0.0f && x <= ((float) AbstractRecordingActivity.access$300(AbstractRecordingActivity.this).getWidth()) && y >= 0.0f && y <= ((float) AbstractRecordingActivity.access$300(AbstractRecordingActivity.this).getHeight());
/* 416 */                       if (!activate) {
/* 417 */                           this.hasMovedOut = true;
                                }
/* 419 */                       AbstractRecordingActivity.access$200(AbstractRecordingActivity.this).setActivated(activate && !this.hasMovedOut);
/* 420 */                       AbstractRecordingActivity.access$300(AbstractRecordingActivity.this).setActivated(activate && !this.hasMovedOut);
                                return false;
                            default:
                                return false;
                        }
                    }
                }

/* 484 */       private BaseRecorderPluginManager getRecorderPluginManager() {
/* 485 */           RecordingFragment recordingFragment = getActiveRecordingFragment();
/* 486 */           if (recordingFragment != null) {
/* 487 */               return recordingFragment.getPluginManager();
                    }
                    return null;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 493 */       protected void onResume() {
/* 494 */           super.onResume();
/* 495 */           BaseRecorderPluginManager pm = getRecorderPluginManager();
/* 496 */           if (pm != null) {
/* 497 */               pm.onResume(this);
                    }
/* 499 */           this.mIsResumed = true;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 503 */       protected void onPause() {
/* 504 */           super.onPause();
/* 505 */           BaseRecorderPluginManager pm = getRecorderPluginManager();
/* 506 */           if (pm != null) {
/* 507 */               pm.onPause();
                    }
/* 509 */           this.mIsResumed = false;
/* 510 */           if (this.mPromptDialog != null && this.mPromptDialog.isVisible()) {
/* 511 */               this.mPromptDialog.dismiss();
                    }
/* 513 */           releasePlayers();
                }

/* 516 */       public Point getScreenSize() {
/* 517 */           return this.mScreenSize;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 521 */       protected void onSaveInstanceState(Bundle outState) {
/* 522 */           super.onSaveInstanceState(outState);
/* 523 */           outState.putBoolean(STATE_FIRST_PAGE_SET, this.mFirstPageSet);
/* 524 */           outState.putBoolean(STATE_FIRST_DRAFT_LAUNCH, this.mFirstDraftLaunch);
                }

                @Override // android.support.v4.view.DraftViewPager.OnPageChangeListener
/* 528 */       public void onPageScrolled(int i, float v, int i2) {
                }

                @Override // co.vine.android.widget.DragUpToDeleteContainer.DragUpListener
/* 532 */       public boolean prepareForPickup() {
                    WeakReference<DraftFragment> fragment;
/* 533 */           if (!this.mAnimatingIntoDrafts && (fragment = this.mDraftFragments.get(this.mCurrentPage)) != null) {
/* 536 */               DraftFragment ref = fragment.get();
/* 537 */               ref.mCanUnhide = false;
/* 538 */               ref.pausePlayer();
/* 539 */               ref.showImage();
                    }
                    return !this.mAnimatingIntoDrafts;
                }

                @Override // co.vine.android.widget.DragUpToDeleteContainer.DragUpListener
/* 546 */       public void viewPickedUp() {
/* 547 */           hideCurrentFragment();
                }

                @Override // co.vine.android.widget.DragUpToDeleteContainer.DragUpListener
/* 551 */       public void viewDestroyFinished() {
/* 552 */           this.mDraftTrashButton.setActivated(false);
/* 553 */           this.mDraftTrashBackground.setActivated(false);
                }

/* 556 */       private void hideCurrentFragment() {
                    DraftFragment ref;
/* 557 */           WeakReference<DraftFragment> fragment = this.mDraftFragments.get(this.mCurrentPage);
/* 558 */           if (fragment != null && (ref = fragment.get()) != null) {
/* 561 */               ref.pausePlayer();
/* 562 */               View toHide = ref.getView();
/* 563 */               if (toHide != null) {
/* 564 */                   toHide.setVisibility(8);
                        }
                    }
                }

/* 570 */       public String makeSureUploadIsReady() {
/* 571 */           if (this.mAddToUploadThread != null) {
                        try {
/* 573 */                   this.mAddToUploadThread.join();
                        } catch (InterruptedException e) {
/* 575 */                   throw new RuntimeException(e);
                        }
                    }
/* 578 */           return this.mUploadFile;
                }

                @Override // co.vine.android.widget.DragUpToDeleteContainer.DragUpListener
/* 582 */       public boolean viewMoved(int viewTopY) {
/* 583 */           boolean lightTrash = viewTopY < 0;
/* 584 */           this.mDraftTrashBackground.setActivated(lightTrash);
/* 585 */           this.mDraftTrashButton.setActivated(viewTopY < this.mDraftTrashContainer.getHeight());
/* 586 */           return lightTrash;
                }

                @Override // co.vine.android.widget.DragUpToDeleteContainer.DragUpListener
/* 590 */       public boolean viewDropped(int viewTopY) {
/* 591 */           boolean delete = viewTopY <= 0;
/* 592 */           if (delete && this.mCurrentPage != this.mDraftFragments.size()) {
/* 593 */               this.mDeleteWasDrag = true;
/* 594 */               showDeleteDialog();
                    } else {
/* 596 */               this.mDraftTrashButton.setActivated(false);
                    }
/* 598 */           return delete;
                }

                @Override // co.vine.android.widget.DragUpToDeleteContainer.DragUpListener
/* 602 */       public void viewLanded() {
/* 603 */           showCurrentFragment();
                }

/* 606 */       private void showCurrentFragment() {
                    DraftFragment ref;
/* 607 */           WeakReference<DraftFragment> fragment = this.mDraftFragments.get(this.mCurrentPage);
/* 608 */           if (fragment != null && (ref = fragment.get()) != null) {
/* 611 */               ref.mCanUnhide = true;
/* 612 */               ref.resumePlayer();
/* 613 */               View v = ref.getView();
/* 614 */               if (v != null) {
/* 615 */                   v.setVisibility(0);
                        }
                    }
                }

/* 621 */       public long getConversationRowId() {
/* 622 */           return this.mConversationRowId;
                }

/* 625 */       public PromptDialogFragment createDialogForPlugins(int dialogId) {
/* 626 */           if (((-16777216) & dialogId) != 0) {
/* 627 */               throw new IllegalArgumentException("Invalid dialog id: " + dialogId);
                    }
/* 629 */           this.mPromptDialog = PromptDialogFragment.newInstance((-16777216) + dialogId);
/* 630 */           return this.mPromptDialog;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 634 */       public boolean onCreateOptionsMenu(Menu menu) {
/* 635 */           super.onCreateOptionsMenu(menu);
/* 636 */           BaseRecorderPluginManager pm = getRecorderPluginManager();
/* 637 */           if (pm != null) {
/* 638 */               pm.onCreateOptionsMenu(menu);
/* 640 */               return true;
                    }
/* 640 */           return true;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 644 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 645 */           BaseRecorderPluginManager pm = getRecorderPluginManager();
/* 646 */           if (pm == null || !pm.onOptionsItemSelected(item)) {
                        return super.onOptionsItemSelected(item);
                    }
/* 648 */           return true;
                }

/* 654 */       public void showDialog(PromptDialogFragment dialog) {
/* 655 */           if (this.mPromptDialog != dialog) {
/* 656 */               this.mPromptDialog = dialog;
                    }
                    try {
/* 659 */               this.mPromptDialog.show(getFragmentManager());
                    } catch (IllegalStateException e) {
                    }
                }

/* 665 */       public static RegularVineRecorder.DeviceIssueStringGetter getDeviceIssueStringGetter(Context context) {
/* 666 */           return new DeviceIssueStringGetter(context);
                }

/* 669 */       public Fragment getCurrentFragment() {
/* 670 */           return this.mCurrentFragment;
                }

/* 675 */       public class SetSelectedRunnable implements Runnable {
                    public final DraftFragment fragmentToSetSelectedFalse;
                    public final DraftFragment fragmentToSetSelectedTrue;

/* 676 */           public SetSelectedRunnable(DraftFragment fragmentToSetSelectedFalse, DraftFragment fragmentToSetSelectedTrue) {
/* 678 */               this.fragmentToSetSelectedFalse = fragmentToSetSelectedFalse;
/* 679 */               this.fragmentToSetSelectedTrue = fragmentToSetSelectedTrue;
                    }

                    @Override // java.lang.Runnable
/* 683 */           public void run() {
/* 684 */               if (this.fragmentToSetSelectedFalse != null) {
/* 685 */                   this.fragmentToSetSelectedFalse.setSelected(false);
                        }
/* 687 */               if (this.fragmentToSetSelectedTrue != null) {
/* 688 */                   this.fragmentToSetSelectedTrue.setSelected(true);
                        }
                    }
                }

                @Override // android.support.v4.view.DraftViewPager.OnPageChangeListener
/* 694 */       public void onPageSelected(int i) {
                    DraftFragment ref;
                    View fragView;
                    View toSet;
                    WeakReference<DraftFragment> fragment;
                    DraftFragment ref2;
                    WeakReference<DraftFragment> fragment2;
                    DraftFragment ref3;
/* 695 */           if (this.mSessions != null) {
/* 696 */               DraftFragment fragmentToSetSelectedFalse = null;
/* 697 */               DraftFragment fragmentToSetSelectedTrue = null;
/* 698 */               boolean isPageStateIdle = this.mPageScrollState == 0;
/* 699 */               int numDrafts = this.mSessions.size();
/* 700 */               if (i == -1) {
/* 704 */                   if (this.mCurrentPage <= numDrafts && (fragment2 = this.mDraftFragments.get(this.mCurrentPage)) != null && (ref3 = fragment2.get()) != null) {
/* 709 */                       if (isPageStateIdle) {
/* 710 */                           ref3.setSelected(false);
                                } else {
/* 712 */                           fragmentToSetSelectedFalse = ref3;
                                }
                            }
                        } else {
/* 718 */                   this.mDots.setActiveDot(i);
/* 719 */                   if (i < numDrafts) {
/* 720 */                       if (this.mCurrentPage != -1 && this.mCurrentPage <= numDrafts && (fragment = this.mDraftFragments.get(this.mCurrentPage)) != null && (ref2 = fragment.get()) != null) {
/* 726 */                           if (isPageStateIdle) {
/* 727 */                               ref2.setSelected(false);
                                    } else {
/* 729 */                               fragmentToSetSelectedFalse = ref2;
                                    }
                                }
/* 735 */                       WeakReference<DraftFragment> fragment3 = this.mDraftFragments.get(i);
/* 736 */                       if (fragment3 != null && (ref = fragment3.get()) != null && (fragView = ref.getView()) != null && (toSet = fragView.findViewById(C0257R.id.draft_container)) != null && this.mDragUpToDeleteView != null) {
/* 743 */                           this.mDragUpToDeleteView.setView(toSet, this.mViewPager.getLeftOfSelectedFragment());
/* 744 */                           if (!this.mAnimatingIntoDrafts || i < numDrafts) {
/* 745 */                               this.mDraftTrashContainer.setVisibility(0);
/* 746 */                               this.mDraftTrashContainer.animate().alpha(1.0f).start();
                                    }
/* 748 */                           this.mHandler.removeCallbacks(this.mOnResumeDraftRunnable);
/* 749 */                           if (this.mPageScrollState == 0) {
/* 750 */                               ref.setSelected(true);
                                    } else {
/* 752 */                               fragmentToSetSelectedTrue = ref;
                                    }
                                }
/* 758 */                       this.mCurrentSession = this.mSessions.get(i);
                            } else {
/* 760 */                       if (this.mCurrentPage != -1) {
/* 761 */                           WeakReference<DraftFragment> fragment4 = this.mDraftFragments.get(this.mCurrentPage);
/* 762 */                           if (fragment4 != null) {
/* 763 */                               DraftFragment prev = fragment4.get();
/* 764 */                               if (prev != null) {
/* 765 */                                   if (isPageStateIdle) {
/* 766 */                                       prev.setSelected(false);
                                            } else {
/* 768 */                                       fragmentToSetSelectedFalse = prev;
                                            }
                                        }
                                    } else {
/* 773 */                               SLog.m448d("Fragment {} is null: size: {}.", Integer.valueOf(this.mCurrentPage), Integer.valueOf(this.mDraftFragments.size()));
                                    }
                                }
/* 776 */                       if (this.mCameraFragment != null && this.mCameraFragment.get() != null) {
/* 779 */                           this.mHandler.removeCallbacks(this.mOnResumeDraftRunnable);
                                }
/* 782 */                       this.mCurrentSession = null;
/* 783 */                       this.mDragUpToDeleteView.setView(null, this.mViewPager.getLeftOfSelectedFragment());
/* 784 */                       this.mDraftTrashContainer.animate().alpha(0.0f).setListener(new C01405()).start();
                            }
/* 792 */                   this.mCurrentPage = i;
                        }
/* 794 */               if (fragmentToSetSelectedFalse != null || fragmentToSetSelectedTrue != null) {
/* 795 */                   this.mOnPageIdleRunnable = new SetSelectedRunnable(fragmentToSetSelectedFalse, fragmentToSetSelectedTrue);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$5 */
/* 782 */       class C01405 extends SimpleAnimatorListener {
/* 783 */           C01405() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
/* 786 */           public void onAnimationEnd(Animator a) {
/* 787 */               AbstractRecordingActivity.access$400(AbstractRecordingActivity.this).setVisibility(8);
/* 788 */               AbstractRecordingActivity.access$400(AbstractRecordingActivity.this).animate().setListener(null);
                    }
                }

                @Override // android.support.v4.view.DraftViewPager.OnPageChangeListener
/* 801 */       public void onPageScrollStateChanged(int i) {
/* 802 */           this.mPageScrollState = i;
/* 803 */           if (i == 0 && this.mOnPageIdleRunnable != null) {
/* 804 */               this.mOnPageIdleRunnable.run();
/* 805 */               this.mOnPageIdleRunnable = null;
                    }
                }

/* 810 */       public void initMasks(View topMask, View bottomMask) {
/* 811 */           topMask.setOnTouchListener(this.mOnMaskTouchListesner);
/* 812 */           bottomMask.setOnTouchListener(this.mOnMaskTouchListesner);
/* 816 */           SharedPreferences prefs = getSharedPreferences(BasicVineRecorder.PREFS_CAPTURE, 0);
/* 818 */           int bottomMaskHeightPx = prefs.getInt(VineRecorder.getBottomMaskHeightPref(RecordConfigUtils.isDefaultFrontFacing()), 0);
/* 821 */           if (bottomMaskHeightPx > 0) {
/* 822 */               RelativeLayout.LayoutParams bottomMaskParams = (RelativeLayout.LayoutParams) bottomMask.getLayoutParams();
/* 824 */               bottomMaskParams.height = bottomMaskHeightPx;
/* 826 */               runOnUiThread(new RunnableC01416(bottomMask, bottomMaskParams));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$6 */
/* 824 */       class RunnableC01416 implements Runnable {
                    final /* synthetic */ View val$bottomMask;
                    final /* synthetic */ RelativeLayout.LayoutParams val$bottomMaskParams;

/* 825 */           RunnableC01416(View view, RelativeLayout.LayoutParams layoutParams) {
                        this.val$bottomMask = view;
                        this.val$bottomMaskParams = layoutParams;
                    }

                    @Override // java.lang.Runnable
/* 828 */           public void run() {
/* 829 */               this.val$bottomMask.setLayoutParams(this.val$bottomMaskParams);
                    }
                }

/* 835 */       protected boolean currentlyHoldsRecordingFragment() {
                    return this.mCurrentFragment != null && (this.mCurrentFragment instanceof RecordingFragment);
                }

/* 841 */       public void onRecorderBackPressed(View v) {
                    try {
/* 842 */               onBackPressed();
                    } catch (IllegalStateException e) {
                    }
                }

/* 848 */       public void showUnSavedChangesToSessionDialog(PromptDialogFragment.OnDialogDoneListener listener) {
/* 849 */           RecordingFragment fragment = getActiveRecordingFragment();
/* 850 */           if (fragment != null) {
/* 851 */               this.mPromptDialog = PromptDialogFragment.newInstance(0);
/* 852 */               this.mPromptDialog.setMessage(fragment.isSavedSession() ? C0257R.string.save_draft_old_confirm : C0257R.string.save_draft_new_confirm);
/* 854 */               this.mPromptDialog.setPositiveButton(C0257R.string.save);
/* 855 */               this.mPromptDialog.setNegativeButton(C0257R.string.discard_changes);
/* 856 */               this.mPromptDialog.setListener(listener);
                        try {
/* 858 */                   this.mPromptDialog.show(getFragmentManager());
                        } catch (IllegalStateException e) {
                        }
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 866 */       public void onBackPressed() {
/* 867 */           makeSureUploadIsReady();
                    try {
/* 869 */               if (this.mStep == -1) {
/* 870 */                   super.onBackPressed();
                        } else {
/* 872 */                   BaseRecorderPluginManager pm = getRecorderPluginManager();
/* 873 */                   if (pm == null || !pm.onBackPressed()) {
/* 876 */                       if (isDraftsShowing()) {
/* 877 */                           hideDrafts(true);
/* 878 */                           releasePlayers();
/* 879 */                           if (this.mCurrentFragment instanceof RecordingFragment) {
/* 881 */                               ((RecordingFragment) this.mCurrentFragment).resumeFromDraft();
                                    }
                                } else if (currentlyHoldsRecordingFragment()) {
/* 885 */                           RecordingFragment fragment = (RecordingFragment) this.mCurrentFragment;
/* 886 */                           boolean isEditing = fragment.isEditing();
/* 888 */                           if (!fragment.onBackPressed(isEditing)) {
/* 889 */                               if (!isEditing && !fragment.isSessionModified()) {
/* 890 */                                   fragment.setDiscardChangesOnStop();
/* 891 */                                   setResult(100);
/* 892 */                                   fragment.playStopSound();
/* 893 */                                   super.onBackPressed();
                                        } else if (isEditing && !fragment.isEditingDirty()) {
/* 896 */                                   fragment.playStopSound();
/* 897 */                                   fragment.discardEditing();
                                        } else if (!this.mIsMessaging) {
/* 899 */                                   showUnSavedChangesToSessionDialog(this.mUnsavedChangesDialogDoneListener);
                                        } else {
/* 901 */                                   setResult(100);
/* 902 */                                   super.onBackPressed();
                                        }
                                    }
                                } else if (this.mCurrentFragment instanceof RecordingPreviewFragment) {
                                    try {
/* 909 */                               ((RecordingPreviewFragment) this.mCurrentFragment).previewToRecord(this, false);
                                    } catch (InvalidStateException e) {
                                    }
                                }
                            }
                        }
                    } catch (IllegalStateException e2) {
                    }
                }

/* 922 */       public void onBackPressed(View v) {
/* 923 */           onBackPressed();
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$7 */
/* 924 */       class C01427 implements PromptDialogFragment.OnDialogDoneListener {
/* 925 */           C01427() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 928 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -2:
/* 937 */                       AbstractRecordingActivity.access$600(AbstractRecordingActivity.this);
/* 938 */                       AbstractRecordingActivity.access$300(AbstractRecordingActivity.this).setActivated(false);
/* 939 */                       AbstractRecordingActivity.access$200(AbstractRecordingActivity.this).setActivated(false);
                                break;
                            case -1:
/* 931 */                       AbstractRecordingActivity.access$500(AbstractRecordingActivity.this);
/* 932 */                       AbstractRecordingActivity.access$300(AbstractRecordingActivity.this).setActivated(false);
/* 933 */                       AbstractRecordingActivity.access$200(AbstractRecordingActivity.this).setActivated(false);
                                break;
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$8 */
/* 943 */       class C01438 implements PromptDialogFragment.OnDialogDoneListener {
/* 944 */           C01438() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 947 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -2:
/* 950 */                       if (AbstractRecordingActivity.this.currentlyHoldsRecordingFragment()) {
/* 951 */                           RecordingFragment frag = (RecordingFragment) AbstractRecordingActivity.this.mCurrentFragment;
/* 952 */                           frag.setDiscardChangesOnStop();
/* 953 */                           frag.playStopSound();
                                }
/* 955 */                       AbstractRecordingActivity.this.discardUpload();
/* 956 */                       AbstractRecordingActivity.this.setResult(100);
/* 957 */                       AbstractRecordingActivity.this.finish();
                                break;
                            case -1:
/* 961 */                       if (AbstractRecordingActivity.this.currentlyHoldsRecordingFragment()) {
/* 962 */                           AbstractRecordingActivity.this.setResult(100);
/* 963 */                           ((RecordingFragment) AbstractRecordingActivity.this.mCurrentFragment).saveSessionAndQuit();
                                }
                                break;
                        }
                    }
                }

/* 971 */       protected void discardUpload() {
/* 972 */           if (this.mUploadFile != null) {
/* 973 */               SLog.m446d("Upload discarded.");
/* 974 */               UploadManager.removeFromUploadQueue(this, this.mUploadFile);
                    }
                }

/* 978 */       public void hideDrafts(boolean animate) {
/* 979 */           CrashUtil.log("Fading away drafts.");
/* 981 */           AnimationSet as = new AnimationSet(true);
/* 982 */           if (animate) {
/* 983 */               as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.COLLAPSE_HEIGHT, this.mDraftMaskTop, 0));
/* 985 */               as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.COLLAPSE_WIDTH, this.mDraftMaskLeft, 0));
/* 987 */               as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.COLLAPSE_WIDTH, this.mDraftMaskRight, 0));
                    }
/* 990 */           as.addAnimation(AnimationUtils.loadAnimation(this, C0257R.anim.fade_out));
/* 991 */           as.setAnimationListener(new C01449(animate));
/* 1007 */          as.setDuration(300L);
/* 1008 */          this.mDragUpToDeleteView.setView(null, this.mViewPager.getLeftOfSelectedFragment());
/* 1009 */          this.mDraftRoot.startAnimation(as);
/* 1010 */          this.mDraftTrashContainer.animate().cancel();
/* 1011 */          this.mDraftTrashContainer.setVisibility(8);
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$9 */
/* 989 */       class C01449 extends SimpleAnimationListener {
                    final /* synthetic */ boolean val$animate;

/* 990 */           C01449(boolean z) {
                        this.val$animate = z;
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 993 */           public void onAnimationStart(Animation animation) {
/* 994 */               if (this.val$animate) {
/* 995 */                   AbstractRecordingActivity.access$700(AbstractRecordingActivity.this).setVisibility(0);
                        } else {
/* 997 */                   AbstractRecordingActivity.access$700(AbstractRecordingActivity.this).setVisibility(8);
/* 998 */                   AbstractRecordingActivity.access$800(AbstractRecordingActivity.this).setVisibility(8);
                        }
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 1003 */          public void onAnimationEnd(Animation animation) {
/* 1004 */              AbstractRecordingActivity.access$900(AbstractRecordingActivity.this);
                    }
                }

/* 1014 */      private void hideDraftsInternal() {
/* 1015 */          this.mDraftRoot.setVisibility(8);
/* 1016 */          this.mProgressOverlay.setVisibility(8);
/* 1017 */          this.mPreviewOverlay.setVisibility(8);
/* 1018 */          this.mProgressOverlay.setVisibility(8);
/* 1019 */          this.mDragUpToDeleteView.setVisibility(8);
/* 1020 */          if (this.mCurrentFragment instanceof RecordingFragment) {
/* 1021 */              ((RecordingFragment) this.mCurrentFragment).getPluginManager().onHideDrafts();
                    }
                }

/* 1025 */      public boolean isDraftsShowing() {
                    return (this.mDraftRoot == null || this.mDraftRoot.getVisibility() == 8) ? false : true;
                }

/* 1030 */      public void showDrafts(String sessionId) {
                    try {
/* 1031 */              CrashUtil.log("Started showing drafts.");
/* 1032 */              boolean goToCamera = START_SESSION_ID_CAMERA.equals(sessionId);
/* 1033 */              this.mAnimatingIntoDrafts = true;
/* 1034 */              this.mStartSessionId = sessionId;
/* 1035 */              this.mFirstPageSet = false;
/* 1036 */              reload(RecordSessionManager.getValidSessions(this, getVersion()));
/* 1037 */              this.mDraftFullMask.setVisibility(0);
/* 1038 */              this.mDraftMaskParent.setVisibility(0);
/* 1039 */              this.mCameraIconOverlayImage.setVisibility(8);
/* 1041 */              Resources r = getResources();
/* 1042 */              int progressDimen = r.getDimensionPixelSize(C0257R.dimen.progress_view_height);
/* 1043 */              int draftProgressDimen = r.getDimensionPixelOffset(C0257R.dimen.draft_progress_view_height);
/* 1046 */              int topMaskHeight = this.mTopMaskHeight;
/* 1047 */              if (goToCamera) {
/* 1048 */                  topMaskHeight += draftProgressDimen;
                        }
/* 1051 */              if (!goToCamera && (this.mCurrentFragment instanceof RecordingFragment)) {
/* 1052 */                  ImageView thumb = this.mLargeThumbnailOverlay;
/* 1054 */                  RelativeLayout.LayoutParams thumbnailParams = (RelativeLayout.LayoutParams) thumb.getLayoutParams();
/* 1056 */                  thumbnailParams.width = this.mScreenSize.x;
/* 1057 */                  thumbnailParams.height = this.mScreenSize.x;
/* 1058 */                  thumbnailParams.leftMargin = 0;
/* 1059 */                  thumbnailParams.topMargin = progressDimen;
/* 1060 */                  thumb.setLayoutParams(thumbnailParams);
/* 1062 */                  String path = ((RecordingFragment) this.mCurrentFragment).getThumbnailPath();
/* 1063 */                  BitmapDrawable drawable = new BitmapDrawable(getResources(), path);
/* 1064 */                  ViewUtil.setBackground(thumb, drawable);
/* 1065 */                  thumb.setVisibility(0);
                        } else if (goToCamera) {
/* 1067 */                  this.mCameraIconOverlay.setVisibility(0);
/* 1068 */                  RelativeLayout.LayoutParams iconParams = (RelativeLayout.LayoutParams) this.mCameraIconOverlay.getLayoutParams();
/* 1070 */                  iconParams.width = this.mScreenSize.x;
/* 1071 */                  iconParams.height = this.mScreenSize.x;
/* 1072 */                  iconParams.topMargin = progressDimen;
/* 1073 */                  iconParams.leftMargin = 0;
/* 1074 */                  this.mCameraIconOverlay.setLayoutParams(iconParams);
                        }
/* 1078 */              this.mViewPager.setOnTouchListener(new ViewOnTouchListenerC013110(goToCamera));
/* 1091 */              AnimationSet as = new AnimationSet(true);
/* 1092 */              as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.EXPAND_HEIGHT, this.mDraftMaskTop, topMaskHeight));
/* 1094 */              as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.EXPAND_WIDTH, this.mDraftMaskLeft, this.mSideMaskWidth));
/* 1096 */              as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.EXPAND_WIDTH, this.mDraftMaskRight, this.mSideMaskWidth));
/* 1099 */              as.setAnimationListener(new C013211(goToCamera));
/* 1174 */              as.setDuration(300L);
/* 1175 */              setCurrentSession();
/* 1176 */              this.mDragUpToDeleteView.setVisibility(0);
/* 1178 */              if (!goToCamera && (this.mCurrentFragment instanceof RecordingFragment)) {
/* 1179 */                  View progress = ((RecordingFragment) this.mCurrentFragment).getProgressView();
/* 1180 */                  if (progress != null) {
/* 1181 */                      FloatingViewUtils.buildFloatingViewFor(this, progress, this.mAnimationPreviewOverlay, progress.getWidth(), 0, 0);
                            }
/* 1184 */                  this.mAnimationPreviewOverlay.setVisibility(0);
                        }
/* 1187 */              if (goToCamera) {
/* 1188 */                  new MoveResizeAnimator(7, this.mCameraIconOverlay, this.mCameraIconOverlay, (int) ((this.mScreenSize.x * (1.0f - this.mPreviewRatio)) / 2.0f), progressDimen + (draftProgressDimen * 2), this.mPreviewRatio, this.mPreviewRatio, 300, this, null).start();
                        } else {
/* 1195 */                  new MoveResizeAnimator(6, this.mAnimationPreviewOverlay, this.mAnimationPreviewOverlay, (int) ((this.mScreenSize.x * (1.0f - this.mPreviewRatio)) / 2.0f), progressDimen + draftProgressDimen, this.mPreviewRatio, 0.5d, 300, this, null).start();
/* 1201 */                  new MoveResizeAnimator(5, this.mLargeThumbnailOverlay, this.mLargeThumbnailOverlay, (int) ((this.mScreenSize.x * (1.0f - this.mPreviewRatio)) / 2.0f), progressDimen + (draftProgressDimen * 2), this.mPreviewRatio, this.mPreviewRatio, 300, this, null).start();
                        }
/* 1208 */              this.mDraftRoot.startAnimation(as);
                    } catch (IOException e) {
/* 1210 */              CrashUtil.logException(e, "Error refresh reloading drafts.", new Object[0]);
                    } catch (Exception e2) {
/* 1212 */              CrashUtil.logException(e2, "Error showing reloading drafts.", new Object[0]);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$10 */
/* 1076 */      class ViewOnTouchListenerC013110 implements View.OnTouchListener {
                    final /* synthetic */ boolean val$goToCamera;

/* 1077 */          ViewOnTouchListenerC013110(boolean z) {
                        this.val$goToCamera = z;
                    }

                    @Override // android.view.View.OnTouchListener
/* 1080 */          public boolean onTouch(View v, MotionEvent event) {
/* 1081 */              if (this.val$goToCamera) {
/* 1082 */                  AbstractRecordingActivity.access$1000(AbstractRecordingActivity.this).setVisibility(8);
/* 1087 */                  return false;
                        }
/* 1084 */              AbstractRecordingActivity.access$1100(AbstractRecordingActivity.this).setVisibility(8);
/* 1085 */              AbstractRecordingActivity.access$1200(AbstractRecordingActivity.this).setVisibility(8);
/* 1087 */              return false;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$11 */
/* 1097 */      class C013211 extends SimpleAnimationListener {
                    final /* synthetic */ boolean val$goToCamera;

/* 1098 */          C013211(boolean z) {
                        this.val$goToCamera = z;
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 1101 */          public void onAnimationStart(Animation animation) {
/* 1102 */              AbstractRecordingActivity.access$1400(AbstractRecordingActivity.this).removeCallbacks(AbstractRecordingActivity.access$1300(AbstractRecordingActivity.this));
/* 1103 */              AbstractRecordingActivity.access$1400(AbstractRecordingActivity.this).postDelayed(AbstractRecordingActivity.access$1300(AbstractRecordingActivity.this), 300L);
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 1107 */          public void onAnimationEnd(Animation animation) {
/* 1108 */              if (AbstractRecordingActivity.this.mCurrentPage != AbstractRecordingActivity.access$000(AbstractRecordingActivity.this).size()) {
/* 1109 */                  AbstractRecordingActivity.access$400(AbstractRecordingActivity.this).setAlpha(0.0f);
/* 1110 */                  AbstractRecordingActivity.access$400(AbstractRecordingActivity.this).setVisibility(0);
/* 1111 */                  if (!AbstractRecordingActivity.access$1500(AbstractRecordingActivity.this)) {
/* 1112 */                      AbstractRecordingActivity.access$400(AbstractRecordingActivity.this).animate().alpha(1.0f).start();
                            }
                        }
/* 1116 */              AbstractRecordingActivity.access$1700(AbstractRecordingActivity.this).setAnimationListener(new AnonymousClass1());
/* 1152 */              AbstractRecordingActivity.access$1400(AbstractRecordingActivity.this).postDelayed(new AnonymousClass2(), 150L);
/* 1159 */              AbstractRecordingActivity.access$2100(AbstractRecordingActivity.this).setVisibility(0);
/* 1160 */              AbstractRecordingActivity.access$2100(AbstractRecordingActivity.this).startAnimation(AbstractRecordingActivity.access$1700(AbstractRecordingActivity.this));
/* 1161 */              AbstractRecordingActivity.access$2202(AbstractRecordingActivity.this, 0);
/* 1162 */              AbstractRecordingActivity.access$800(AbstractRecordingActivity.this).setVisibility(0);
/* 1164 */              if (AbstractRecordingActivity.this.mCurrentFragment instanceof RecordingFragment) {
/* 1165 */                  ((RecordingFragment) AbstractRecordingActivity.this.mCurrentFragment).getPluginManager().onShowDrafts();
                        }
/* 1168 */              AbstractRecordingActivity.access$700(AbstractRecordingActivity.this).setVisibility(8);
/* 1169 */              AbstractRecordingActivity.access$2300(AbstractRecordingActivity.this).setVisibility(8);
/* 1170 */              AbstractRecordingActivity.access$2400(AbstractRecordingActivity.this).setVisibility(8);
/* 1171 */              AbstractRecordingActivity.access$2500(AbstractRecordingActivity.this).setVisibility(8);
                    }

                    /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$11$1, reason: invalid class name */
/* 1114 */          class AnonymousClass1 extends SimpleAnimationListener {
/* 1115 */              AnonymousClass1() {
                        }

                        @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 1120 */              public void onAnimationStart(Animation animation) {
/* 1121 */                  if (C013211.this.val$goToCamera) {
/* 1122 */                      AbstractRecordingActivity.access$1600(AbstractRecordingActivity.this).setVisibility(0);
/* 1123 */                      AbstractRecordingActivity.access$1600(AbstractRecordingActivity.this).setAlpha(0.0f);
/* 1124 */                      AbstractRecordingActivity.access$1600(AbstractRecordingActivity.this).animate().alpha(1.0f).setDuration(AbstractRecordingActivity.access$1700(AbstractRecordingActivity.this).getDuration()).setListener(new ViewGoneAnimationListener(AbstractRecordingActivity.access$1600(AbstractRecordingActivity.this))).start();
/* 1128 */                      AbstractRecordingActivity.access$1400(AbstractRecordingActivity.this).postDelayed(new RunnableC13121(), AbstractRecordingActivity.access$1800(AbstractRecordingActivity.this).getDuration());
                            }
                        }

                        /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$11$1$1, reason: invalid class name and collision with other inner class name */
/* 1126 */              class RunnableC13121 implements Runnable {
/* 1127 */                  RunnableC13121() {
                            }

                            @Override // java.lang.Runnable
/* 1130 */                  public void run() {
/* 1131 */                      AbstractRecordingActivity.access$1000(AbstractRecordingActivity.this).setVisibility(8);
                            }
                        }

                        /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$11$1$2, reason: invalid class name */
/* 1138 */              class AnonymousClass2 implements Runnable {
/* 1139 */                  AnonymousClass2() {
                            }

                            @Override // java.lang.Runnable
/* 1142 */                  public void run() {
/* 1143 */                      if (!C013211.this.val$goToCamera) {
/* 1144 */                          AbstractRecordingActivity.access$1100(AbstractRecordingActivity.this).setVisibility(8);
/* 1145 */                          AbstractRecordingActivity.access$1200(AbstractRecordingActivity.this).setVisibility(8);
                                }
                            }
                        }

                        @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 1138 */              public void onAnimationEnd(Animation animation) {
/* 1139 */                  AbstractRecordingActivity.access$1902(AbstractRecordingActivity.this, false);
/* 1140 */                  AbstractRecordingActivity.access$1400(AbstractRecordingActivity.this).postDelayed(new AnonymousClass2(), 300L);
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$11$2, reason: invalid class name */
/* 1150 */          class AnonymousClass2 implements Runnable {
/* 1151 */              AnonymousClass2() {
                        }

                        @Override // java.lang.Runnable
/* 1154 */              public void run() {
/* 1155 */                  AbstractRecordingActivity.access$2000(AbstractRecordingActivity.this).setVisibility(8);
                        }
                    }
                }

/* 1216 */      public void toRecord(boolean isNew, boolean startWithEdit, RecordingFile fileToUse) throws InvalidStateException {
/* 1217 */          this.mStep = 0;
/* 1218 */          this.mFirstPageSet = false;
/* 1219 */          onPageSelected(-1);
/* 1220 */          boolean fromPreview = fileToUse != null;
                    Object[] objArr = new Object[3];
                    objArr[0] = Boolean.valueOf(isNew);
                    objArr[1] = Boolean.valueOf(startWithEdit);
                    objArr[2] = Boolean.valueOf(fileToUse != null);
/* 1221 */          CrashUtil.log("To recording fragment: {} {} {}", objArr);
/* 1222 */          if (fromPreview) {
/* 1223 */              discardUpload();
                    }
/* 1225 */          RecordingFragment fragment = createRecordingFragment();
/* 1226 */          Bundle args = new Bundle();
/* 1227 */          args.putParcelable("arg_top_overlay", this.mTopOverlay);
/* 1228 */          args.putParcelable(RecordingFragment.ARG_SCREEN_SIZE, this.mScreenSize);
/* 1229 */          args.putBoolean(RecordingFragment.ARG_IS_MESSAGING, this.mIsMessaging);
/* 1230 */          args.putString("recipient_username", this.mRecipientUsername);
/* 1233 */          this.mColor = Util.getDefaultSharedPrefs(this).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
/* 1236 */          args.putInt("color", this.mColor);
/* 1237 */          fragment.setArguments(args);
/* 1238 */          fragment.setFileFileToUse(fileToUse);
/* 1239 */          fragment.setStartWithEdit(startWithEdit);
/* 1240 */          FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
/* 1241 */          if (isNew) {
/* 1242 */              ft.add(C0257R.id.fragment_container, fragment);
                    } else {
/* 1244 */              ft.replace(C0257R.id.fragment_container, fragment);
                    }
                    try {
/* 1247 */              ft.commit();
/* 1251 */              this.mCurrentFragment = fragment;
/* 1252 */              this.mRegularUiMode = getWindow().getDecorView().getSystemUiVisibility();
                    } catch (IllegalStateException e) {
/* 1249 */              throw new InvalidStateException(e);
                    }
                }

/* 1255 */      public void swapFolder(String tag, File folder) {
/* 1256 */          if (this.mCurrentFragment instanceof RecordingFragment) {
/* 1257 */              RecordingFragment rf = (RecordingFragment) this.mCurrentFragment;
/* 1258 */              if (!rf.isResuming()) {
/* 1259 */                  rf.swapFolder(tag, folder);
                        }
                    }
                }

/* 1265 */      public static class InvalidStateException extends Exception {
/* 1266 */          public InvalidStateException(Exception e) {
/* 1267 */              super(e);
                    }
                }

/* 1271 */      public void toPreview(String source, RecordingFile finalFile, String thumbNailPath, MediaUtil.GenerateThumbnailsRunnable grabThumbnailRunnable) throws InvalidStateException {
                    RecordingPreviewFragment fragment;
/* 1272 */          CrashUtil.log("From {} to preview fragment: {} {}", source, finalFile, thumbNailPath);
/* 1273 */          getWindow().getDecorView().setSystemUiVisibility(this.mRegularUiMode);
                    try {
/* 1275 */              String reference = finalFile.folder.getName();
/* 1276 */              Cursor cursor = UploadManager.getReferenceCursor(this, reference);
/* 1277 */              if (cursor != null && cursor.moveToFirst()) {
/* 1278 */                  CrashUtil.log("User have edited the vine already, discard until new one comes in.");
/* 1279 */                  startService(VineUploadService.getDiscardIntent(this, cursor.getString(1)));
                        }
/* 1282 */              if (cursor != null) {
/* 1283 */                  cursor.close();
                        }
                    } catch (Exception e) {
/* 1286 */              CrashUtil.log("Failed to delete previous vines.");
                    }
/* 1288 */          if (BuildUtil.isIsHwEncodingEnabled()) {
/* 1289 */              this.mAddToUploadThread = new C013312(grabThumbnailRunnable, finalFile, thumbNailPath);
/* 1307 */              this.mAddToUploadThread.start();
                    } else if (AppController.getInstance(this).isLoggedIn()) {
                        try {
/* 1311 */                  this.mUploadFile = UploadManager.addToUploadQueue(this, finalFile, thumbNailPath, finalFile.folder.getName(), this.mIsMessaging, this.mConversationRowId);
                        } catch (Exception e2) {
/* 1315 */                  throw new RuntimeException(e2);
                        }
                    }
/* 1319 */          if (this.mCurrentFragment instanceof RecordingFragment) {
/* 1320 */              this.mCurrentFragment.onPause();
                    }
/* 1324 */          if (BuildUtil.isIsHwEncodingEnabled()) {
/* 1325 */              fragment = createRecordingPreviewFragmentHw(finalFile, thumbNailPath);
                    } else {
/* 1327 */              fragment = createRecordingPreviewFragmentSw(finalFile, thumbNailPath);
                    }
/* 1330 */          fragment.setFinalFile(finalFile);
/* 1331 */          this.mStep = 1;
/* 1332 */          this.mHasPreviewedAlready = true;
                    try {
/* 1334 */              getSupportFragmentManager().beginTransaction().replace(C0257R.id.fragment_container, fragment).commit();
/* 1341 */              if (this.mCurrentFragment instanceof RecordingFragment) {
/* 1342 */                  ((RecordingFragment) this.mCurrentFragment).release();
                        }
/* 1344 */              this.mCurrentFragment = fragment;
                    } catch (IllegalStateException e3) {
/* 1339 */              throw new InvalidStateException(e3);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$12 */
/* 1287 */      class C013312 extends Thread {
                    final /* synthetic */ RecordingFile val$finalFile;
                    final /* synthetic */ MediaUtil.GenerateThumbnailsRunnable val$grabThumbnailRunnable;
                    final /* synthetic */ String val$thumbNailPath;

/* 1288 */          C013312(MediaUtil.GenerateThumbnailsRunnable generateThumbnailsRunnable, RecordingFile recordingFile, String str) {
                        this.val$grabThumbnailRunnable = generateThumbnailsRunnable;
                        this.val$finalFile = recordingFile;
                        this.val$thumbNailPath = str;
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 1291 */          public void run() {
/* 1292 */              if (this.val$grabThumbnailRunnable != null) {
/* 1293 */                  this.val$grabThumbnailRunnable.run();
                        }
/* 1295 */              if (AppController.getInstance(AbstractRecordingActivity.this.getApplicationContext()).isLoggedIn()) {
                            try {
/* 1297 */                      AbstractRecordingActivity.this.mUploadFile = UploadManager.addToUploadQueue(AbstractRecordingActivity.this.getApplicationContext(), this.val$finalFile, this.val$thumbNailPath, this.val$finalFile.folder.getName(), AbstractRecordingActivity.this.mIsMessaging, AbstractRecordingActivity.this.mConversationRowId);
                            } catch (Exception e) {
/* 1301 */                      throw new RuntimeException(e);
                            }
                        }
/* 1304 */              AbstractRecordingActivity.access$2602(AbstractRecordingActivity.this, null);
                    }
                }

/* 1347 */      public void onFinishPressed(View v) {
/* 1348 */          if (this.mCurrentFragment instanceof RecordingFragment) {
/* 1349 */              ((RecordingFragment) this.mCurrentFragment).onFinishPressed(v);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 1354 */      protected void onStart() {
/* 1355 */          super.onStart();
/* 1356 */          BaseRecorderPluginManager pm = getRecorderPluginManager();
/* 1357 */          if (pm != null) {
/* 1358 */              pm.onStart(this);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 1363 */      protected void onStop() {
/* 1364 */          super.onStop();
/* 1365 */          BaseRecorderPluginManager pm = getRecorderPluginManager();
/* 1366 */          if (pm != null) {
/* 1367 */              pm.onStop(this);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 1372 */      public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 1373 */          if (this.mCurrentFragment instanceof RecordingPreviewFragment) {
/* 1374 */              this.mCurrentFragment.onActivityResult(requestCode, resultCode, data);
/* 1386 */              return;
                    }
/* 1377 */          if (requestCode == 1 && currentlyHoldsRecordingFragment()) {
/* 1378 */              this.mCurrentFragment.onActivityResult(requestCode, resultCode, data);
                    } else if (resultCode == 31 || resultCode == 32) {
/* 1382 */              finish();
                    } else {
/* 1384 */              super.onActivityResult(requestCode, resultCode, data);
                    }
                }

/* 1388 */      private RecordingFragment getActiveRecordingFragment() {
/* 1389 */          if (!isDraftsShowing() && this.mStep == 0 && (this.mCurrentFragment instanceof RecordingFragment)) {
                        return (RecordingFragment) this.mCurrentFragment;
                    }
                    return null;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity, android.view.KeyEvent.Callback
/* 1395 */      public boolean onKeyDown(int keyCode, KeyEvent event) {
                    BaseRecorderPluginManager pluginManager;
/* 1396 */          boolean actionCompleted = false;
/* 1397 */          RecordingFragment recordingFragment = getActiveRecordingFragment();
/* 1398 */          if (recordingFragment != null && (pluginManager = recordingFragment.getPluginManager()) != null) {
/* 1401 */              actionCompleted = pluginManager.onKeyDown(keyCode, event);
                    }
/* 1404 */          if (actionCompleted || keyCode == 80) {
                        return true;
                    }
/* 1407 */          if (recordingFragment != null && keyCode == 27) {
/* 1408 */              this.mHasStartedRelativeTimeFromHardwareButton = true;
/* 1409 */              recordingFragment.startRelativeTime();
                        return true;
                    }
/* 1413 */          return super.onKeyDown(keyCode, event);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity, android.view.KeyEvent.Callback
/* 1417 */      public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
                    BaseRecorderPluginManager pluginManager;
/* 1418 */          boolean actionCompleted = false;
/* 1419 */          RecordingFragment recordingFragment = getActiveRecordingFragment();
/* 1420 */          if (recordingFragment != null && (pluginManager = recordingFragment.getPluginManager()) != null) {
/* 1423 */              actionCompleted = pluginManager.onKeyUp(keyCode, event);
                    }
/* 1426 */          if (actionCompleted || keyCode == 80) {
                        return true;
                    }
/* 1429 */          if (recordingFragment != null && keyCode == 27 && this.mHasStartedRelativeTimeFromHardwareButton) {
/* 1430 */              recordingFragment.endRelativeTime();
                        return true;
                    }
/* 1434 */          return super.onKeyUp(keyCode, event);
                }

/* 1438 */      public boolean startRelativeTime() {
/* 1439 */          if (this.mCurrentFragment instanceof RecordingFragment) {
/* 1440 */              return ((RecordingFragment) this.mCurrentFragment).startRelativeTime();
                    }
                    return false;
                }

/* 1446 */      public boolean hasPreviewedAlready() {
/* 1447 */          return this.mHasPreviewedAlready;
                }

/* 1452 */      public void deleteCurrentDraft() {
                    int nextIndex;
                    DraftFragment frag;
                    try {
/* 1453 */              if (this.mCurrentPage == 0) {
/* 1454 */                  nextIndex = 0;
                        } else {
/* 1456 */                  nextIndex = this.mCurrentPage - 1;
                        }
/* 1458 */              this.mCurrentPage = -1;
/* 1460 */              int current = this.mViewPager.getCurrentItem();
/* 1461 */              WeakReference<DraftFragment> fragRef = this.mDraftFragments.get(current);
/* 1462 */              if (fragRef != null && (frag = fragRef.get()) != null) {
/* 1465 */                  frag.setExpired(true);
                        }
/* 1469 */              if (this.mCurrentSession != null) {
/* 1470 */                  RecordSessionManager.deleteSession(this.mCurrentSession.folder, "deleteCurrentDraft");
                        }
/* 1473 */              FlurryUtils.trackAbandonedStage("draft");
/* 1475 */              this.mStartSessionId = null;
/* 1477 */              ArrayList<RecordSessionManager.RecordSessionInfo> sessions = RecordSessionManager.getValidSessions(this, getVersion());
/* 1479 */              reload(sessions);
/* 1480 */              this.mSessions = sessions;
/* 1482 */              this.mViewPager.requestLayout();
/* 1483 */              this.mViewPager.invalidate();
/* 1484 */              this.mAdapter.notifyDataSetChanged();
/* 1485 */              this.mViewPager.setCurrentItem(nextIndex, false);
/* 1486 */              onPageSelected(nextIndex);
                    } catch (IOException e) {
/* 1488 */              SLog.m459e("Failed to delete current draft.", (Throwable) e);
                    }
                }

/* 1492 */      private void releasePlayers() {
                    for (int i = 0; i < this.mDraftFragments.size(); i++) {
/* 1494 */              WeakReference<DraftFragment> r = this.mDraftFragments.get(this.mDraftFragments.keyAt(i));
/* 1495 */              DraftFragment ref = r.get();
/* 1496 */              if (ref != null) {
/* 1497 */                  ref.setSelected(false);
/* 1498 */                  ref.release();
                        }
                    }
                }

/* 1503 */      private void buildAndShowFloatingView(DraftFragment ref) {
/* 1504 */          releasePlayers();
/* 1505 */          ref.showImage();
/* 1506 */          int[] previewLocation = new int[2];
/* 1507 */          View thumbnailView = ref.getThumbnailView();
/* 1508 */          thumbnailView.getLocationOnScreen(previewLocation);
/* 1510 */          int yOffset = getResources().getDimensionPixelOffset(C0257R.dimen.draft_overlay_offset);
/* 1512 */          FloatingViewUtils.buildFloatingViewFor(this, thumbnailView, this.mPreviewOverlay, thumbnailView.getWidth(), previewLocation[0], previewLocation[1] - yOffset);
/* 1515 */          int[] progressLocation = new int[2];
/* 1516 */          View progressView = ref.getProgressView();
/* 1517 */          progressView.getLocationOnScreen(progressLocation);
/* 1519 */          FloatingViewUtils.buildFloatingViewFor(this, progressView, this.mProgressOverlay, progressView.getWidth(), progressLocation[0], progressLocation[1] - yOffset);
/* 1521 */          this.mPreviewOverlay.setVisibility(0);
/* 1522 */          this.mProgressOverlay.setVisibility(0);
/* 1523 */          this.mDraftTrashContainer.setVisibility(8);
/* 1524 */          this.mViewPager.setVisibility(8);
                }

/* 1527 */      private void reload(ArrayList<RecordSessionManager.RecordSessionInfo> sessions) {
/* 1528 */          int size = sessions.size();
/* 1529 */          if (size > 9) {
/* 1530 */              this.mPromptDialog = PromptDialogFragment.newInstance(3);
/* 1531 */              this.mPromptDialog.setMessage(C0257R.string.too_many_drafts);
/* 1532 */              this.mPromptDialog.setPositiveButton(C0257R.string.f88ok);
/* 1533 */              this.mPromptDialog.show(getFragmentManager());
/* 1534 */              sessions.remove(size - 1);
                    }
/* 1536 */          this.mSessions = sessions;
/* 1537 */          this.mAdapter = new DraftPagerImpl(getSupportFragmentManager());
/* 1538 */          this.mViewPager.setAdapter(this.mAdapter);
/* 1539 */          this.mViewPager.setOnPageChangeListener(this);
/* 1540 */          this.mViewPager.setOffscreenPageLimit(this.mAdapter.getCount() + 1);
/* 1541 */          this.mDots.setNumberOfDots(sessions.size());
/* 1542 */          this.mDots.invalidate();
                }

                @Override // co.vine.android.animation.SmoothAnimator.AnimationListener
/* 1546 */      public void onAnimationFinish(int id, Object tag) {
/* 1547 */          if (this.mIsResumed) {
                        switch (id) {
                            case 1:
/* 1550 */                      Animation fadeOut = AnimationUtils.loadAnimation(this, C0257R.anim.fade_out);
/* 1552 */                      this.mDraftRoot.startAnimation(fadeOut);
/* 1553 */                      hideDrafts(false);
/* 1554 */                      swapFolder("PreviewAnimationFinish", (File) tag);
/* 1555 */                      this.mIsGoingToRecord = false;
                                break;
                            case 3:
/* 1559 */                      deleteCurrentDraft();
/* 1560 */                      this.mPreviewOverlay.setVisibility(8);
                                break;
                            case 4:
/* 1564 */                      hideDrafts(false);
/* 1565 */                      swapFolder("CameraAnimationFinish", null);
/* 1566 */                      this.mIsGoingToRecord = false;
                                break;
                        }
                    }
                }

/* 1573 */      public synchronized void addPlayerToPool(SdkVideoView videoView) {
                    try {
                        this.mVideoViews.add(videoView);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 1577 */      public synchronized void releaseOtherPlayers(SdkVideoView videoView) {
                    try {
                        for (SdkVideoView v : this.mVideoViews) {
/* 1579 */                  if (v != videoView) {
/* 1580 */                      v.suspend();
                            }
                        }
/* 1583 */              this.mVideoViews.clear();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$13 */
/* 1584 */      class RunnableC013413 implements Runnable {
/* 1585 */          RunnableC013413() {
                    }

                    @Override // java.lang.Runnable
/* 1588 */          public void run() {
                        DraftFragment fragment;
/* 1589 */              if (AbstractRecordingActivity.access$2700(AbstractRecordingActivity.this).getCount() != 1 && AbstractRecordingActivity.this.mCurrentPage != AbstractRecordingActivity.access$2700(AbstractRecordingActivity.this).getCount() - 1) {
/* 1590 */                  AbstractRecordingActivity.access$2802(AbstractRecordingActivity.this, (RecordSessionManager.RecordSessionInfo) AbstractRecordingActivity.access$2900(AbstractRecordingActivity.this).get(AbstractRecordingActivity.this.mCurrentPage));
/* 1591 */                  WeakReference<DraftFragment> f = (WeakReference) AbstractRecordingActivity.access$000(AbstractRecordingActivity.this).get(AbstractRecordingActivity.this.mCurrentPage);
/* 1592 */                  if (f != null && (fragment = f.get()) != null) {
/* 1595 */                      fragment.setSelected(true);
                            }
                        }
                    }
                }

/* 1602 */      private class DraftPagerImpl extends DraftFragmentStatePagerAdapter {
/* 1603 */          public DraftPagerImpl(FragmentManager fm) {
/* 1605 */              super(fm);
                    }

                    @Override // android.support.v4.view.DraftPagerAdapter
/* 1608 */          public int getCount() {
/* 1609 */              return AbstractRecordingActivity.access$2900(AbstractRecordingActivity.this).size() + 1;
                    }

                    @Override // android.support.v4.app.DraftFragmentStatePagerAdapter
/* 1613 */          public Fragment getItem(int i) {
/* 1614 */              if (i == AbstractRecordingActivity.access$2900(AbstractRecordingActivity.this).size()) {
/* 1615 */                  DraftCameraPreviewFragment fragment = new DraftCameraPreviewFragment();
/* 1616 */                  fragment.setDimen(AbstractRecordingActivity.this.mPreviewDimen);
/* 1617 */                  AbstractRecordingActivity.access$3002(AbstractRecordingActivity.this, new WeakReference(fragment));
/* 1654 */                  return fragment;
                        }
/* 1620 */              RecordSessionManager.RecordSessionInfo session = (RecordSessionManager.RecordSessionInfo) AbstractRecordingActivity.access$2900(AbstractRecordingActivity.this).get(i);
/* 1621 */              DraftFragment fragment2 = new DraftFragment(i == 0 && !AbstractRecordingActivity.access$3100(AbstractRecordingActivity.this), i, session.video.getPath(), session.thumb.getPath(), session.folder, session.meta.getProgress(), AbstractRecordingActivity.this.mPreviewDimen);
/* 1625 */              fragment2.setListener(new ViewOnClickListenerC01451(fragment2));
/* 1653 */              AbstractRecordingActivity.access$000(AbstractRecordingActivity.this).put(i, new WeakReference(fragment2));
/* 1654 */              return fragment2;
                    }

                    /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$DraftPagerImpl$1 */
/* 1624 */          class ViewOnClickListenerC01451 implements View.OnClickListener {
                        final /* synthetic */ DraftFragment val$fragment;

/* 1625 */              ViewOnClickListenerC01451(DraftFragment draftFragment) {
                            this.val$fragment = draftFragment;
                        }

                        @Override // android.view.View.OnClickListener
/* 1628 */              public void onClick(View v) {
/* 1629 */                  if (v.getId() == 2131427531 && !AbstractRecordingActivity.access$1900(AbstractRecordingActivity.this) && !AbstractRecordingActivity.access$1500(AbstractRecordingActivity.this)) {
/* 1634 */                      CrashUtil.log("User clicked on fragment to resume.");
/* 1635 */                      AbstractRecordingActivity.access$3202(AbstractRecordingActivity.this, true);
/* 1636 */                      AbstractRecordingActivity.access$3300(AbstractRecordingActivity.this, this.val$fragment);
/* 1637 */                      AbstractRecordingActivity.access$3400(AbstractRecordingActivity.this);
/* 1638 */                      float fullSize = AbstractRecordingActivity.access$3500(AbstractRecordingActivity.this).x;
/* 1639 */                      float ratio = fullSize / AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this).getLayoutParams().width;
/* 1641 */                      new MoveResizeAnimator(2, AbstractRecordingActivity.access$3700(AbstractRecordingActivity.this), 0, ratio, 2.0d, 300, null, v.getTag()).start();
/* 1644 */                      new MoveResizeAnimator(1, AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this), AbstractRecordingActivity.access$3700(AbstractRecordingActivity.this).getLayoutParams().height * 2, ratio, ratio, 300, AbstractRecordingActivity.this, v.getTag()).start();
                            }
                        }
                    }

                    @Override // android.support.v4.view.DraftPagerAdapter
/* 1660 */          public void destroyItem(ViewGroup container, int position, Object object) {
                        try {
/* 1661 */                  super.destroyItem(container, position, object);
/* 1662 */                  if (object instanceof DraftFragment) {
/* 1663 */                      FragmentManager m = AbstractRecordingActivity.this.getSupportFragmentManager();
/* 1664 */                      FragmentTransaction ft = m.beginTransaction();
/* 1665 */                      ft.remove((DraftFragment) object);
/* 1666 */                      ft.commit();
                            }
                        } catch (IllegalStateException e) {
/* 1669 */                  CrashUtil.log("Failed to remove draft, it's probably not there any more.");
                        }
                    }

                    @Override // android.support.v4.view.DraftPagerAdapter
/* 1674 */          public int getItemPosition(Object object) {
/* 1675 */              if (object instanceof DraftFragment) {
/* 1676 */                  DraftFragment frag = (DraftFragment) object;
/* 1677 */                  if (frag.isExpired()) {
/* 1678 */                      return -2;
                            }
/* 1680 */                  return -1;
                        }
/* 1683 */              return super.getItemPosition(object);
                    }

                    @Override // android.support.v4.view.DraftPagerAdapter
/* 1687 */          public void finishUpdate(ViewGroup container) {
/* 1688 */              super.finishUpdate(container);
/* 1693 */              if (AbstractRecordingActivity.access$3800(AbstractRecordingActivity.this)) {
/* 1694 */                  AbstractRecordingActivity.access$3802(AbstractRecordingActivity.this, !AbstractRecordingActivity.access$3900(AbstractRecordingActivity.this));
                        }
                    }
                }

/* 1699 */      public void showDeleteDialog() {
/* 1700 */          PromptDialogFragment p = PromptDialogFragment.newInstance(1);
/* 1702 */          p.setListener(this.mDeleteDialogDoneListener);
/* 1703 */          p.setMessage(C0257R.string.delete_draft_confirm);
/* 1704 */          p.setPositiveButton(C0257R.string.delete_yes);
/* 1705 */          p.setNegativeButton(C0257R.string.cancel);
/* 1706 */          p.show(getFragmentManager());
                }

/* 1715 */      private boolean setCurrentSession() {
                    DraftFragment ref;
/* 1716 */          int i = 0;
/* 1717 */          if (this.mViewPager.isDrawn() && !this.mFirstPageSet) {
/* 1718 */              if (TextUtils.isEmpty(this.mStartSessionId) || START_SESSION_ID_CAMERA.equals(this.mStartSessionId)) {
/* 1719 */                  this.mViewPager.setCurrentItem(this.mAdapter.getCount() - 1, false);
/* 1720 */                  this.mFirstPageSet = true;
                            return true;
                        }
                        for (RecordSessionManager.RecordSessionInfo session : this.mSessions) {
/* 1724 */                  if (this.mStartSessionId.equals(session.folder.getName())) {
/* 1725 */                      this.mViewPager.setCurrentItem(i, false);
/* 1726 */                      this.mCurrentPage = i;
/* 1727 */                      this.mFirstPageSet = true;
/* 1728 */                      WeakReference<DraftFragment> item = this.mDraftFragments.get(this.mCurrentPage);
/* 1729 */                      View toSet = null;
/* 1730 */                      if (item != null && (ref = item.get()) != null) {
/* 1733 */                          toSet = ref.getView().findViewById(C0257R.id.draft_container);
                                }
/* 1736 */                      this.mDragUpToDeleteView.setView(toSet, this.mViewPager.getLeftOfSelectedFragment());
                                return true;
                            }
/* 1739 */                  i++;
                        }
                    }
                    return false;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 1747 */      protected void onDestroy() {
/* 1748 */          super.onDestroy();
/* 1749 */          this.mHelper.unBindCameraService(this);
/* 1750 */          BaseRecorderPluginManager pm = getRecorderPluginManager();
/* 1751 */          if (pm != null) {
/* 1752 */              pm.onDestroy();
                    }
                    try {
/* 1755 */              this.mIntentionalObjectCounter.release();
/* 1756 */              int count = this.mIntentionalObjectCounter.getCount();
/* 1757 */              if (count == 0) {
/* 1758 */                  CrashUtil.log("Clean up folders because we are the last one.");
/* 1759 */                  getVersion().getManager(this).cleanUnusedFolders();
                        } else if (count > 1) {
/* 1762 */                  CrashUtil.logException(new VineLoggingException("Double instance violation, but it's ok."));
                        }
                    } catch (IOException e) {
/* 1766 */              CrashUtil.logException(e);
                    }
/* 1768 */          this.mCurrentFragment = null;
/* 1769 */          CrashUtil.log("[mem] AbstractRecordingActivity {} Destroyed.", this);
/* 1770 */          FlurryUtils.trackRecordingDestroy();
/* 1772 */          View root = findViewById(C0257R.id.recording_root);
                    try {
/* 1774 */              unbindDrawables(root);
                    } catch (Exception e2) {
/* 1776 */              SLog.m455e("Failed to remove all drawables, but FINE.");
                    }
/* 1778 */          System.gc();
                }

/* 1781 */      private void unbindDrawables(View view) {
/* 1782 */          if (view.getBackground() != null) {
/* 1783 */              view.getBackground().setCallback(null);
                    }
/* 1785 */          if (view instanceof ViewGroup) {
                        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
/* 1787 */                  unbindDrawables(((ViewGroup) view).getChildAt(i));
                        }
/* 1789 */              ((ViewGroup) view).removeAllViews();
                    }
                }

/* 1793 */      private void handleDelete() {
                    DraftFragment nextFragment;
/* 1794 */          boolean noAnimationDelete = false;
/* 1795 */          WeakReference<DraftFragment> currentFragmentRef = this.mDraftFragments.get(this.mCurrentPage);
/* 1796 */          if (currentFragmentRef != null) {
/* 1797 */              DraftFragment currentFragment = currentFragmentRef.get();
/* 1798 */              Animation fadeOut = AnimationUtils.loadAnimation(this, C0257R.anim.quick_fade_out);
/* 1799 */              int offset = getResources().getDimensionPixelOffset(C0257R.dimen.draft_overlay_offset);
/* 1800 */              int margin = TypedValue.complexToDimensionPixelOffset(6, getResources().getDisplayMetrics());
/* 1802 */              if (this.mCurrentPage > 0) {
/* 1803 */                  DraftFragment prevFragment = this.mDraftFragments.get(this.mCurrentPage - 1).get();
/* 1804 */                  if (prevFragment != null && currentFragment != null) {
/* 1805 */                      View prevView = prevFragment.getThumbnailView();
/* 1806 */                      int[] previewLocation = new int[2];
/* 1807 */                      prevView.getLocationOnScreen(previewLocation);
/* 1809 */                      SimpleAnimationListener l = new C013514(prevView, previewLocation, offset, margin, prevFragment);
/* 1825 */                      fadeOut.setAnimationListener(l);
/* 1826 */                      View view = prevFragment.getView();
/* 1827 */                      if (view != null) {
/* 1828 */                          view.setVisibility(4);
                                }
/* 1830 */                      View view2 = currentFragment.getView();
/* 1831 */                      if (view2 != null && !this.mDeleteWasDrag) {
/* 1832 */                          view2.startAnimation(fadeOut);
                                } else if (view2 != null) {
/* 1834 */                          l.onAnimationEnd(fadeOut);
                                }
                            } else {
/* 1837 */                      noAnimationDelete = true;
                            }
                        } else if (this.mCurrentPage == 0) {
/* 1840 */                  if (this.mSessions.size() == 1) {
/* 1842 */                      deleteCurrentDraft();
/* 1843 */                      hideDrafts(true);
/* 1844 */                      swapFolder("Delete", null);
                            } else {
/* 1847 */                      WeakReference<DraftFragment> nextFragmentRef = this.mDraftFragments.get(this.mCurrentPage + 1);
/* 1849 */                      if (nextFragmentRef != null && (nextFragment = nextFragmentRef.get()) != null && currentFragment != null) {
/* 1852 */                          View nextView = nextFragment.getThumbnailView();
/* 1853 */                          int[] nextLocation = new int[2];
/* 1854 */                          nextView.getLocationOnScreen(nextLocation);
                                    try {
/* 1856 */                              FloatingViewUtils.buildFloatingViewFor(this, nextView, this.mPreviewOverlay, nextView.getWidth(), nextLocation[0], nextLocation[1] - offset);
                                    } catch (Exception e) {
/* 1861 */                              CrashUtil.logException(e);
                                    }
/* 1863 */                          this.mPreviewOverlay.setVisibility(0);
/* 1865 */                          MoveResizeAnimator animator = new MoveResizeAnimator(3, this.mPreviewOverlay, this.mPreviewOverlay, ((int) (this.mScreenSize.x * (1.0f - this.mPreviewRatio))) - (margin * 4), nextLocation[1] - offset, 1.0d, 1.0d, 300, this, nextFragment.getView());
/* 1871 */                          fadeOut.setAnimationListener(new C013615(animator));
/* 1877 */                          View view3 = nextFragment.getView();
/* 1878 */                          if (view3 != null) {
/* 1879 */                              view3.setVisibility(4);
                                    }
/* 1881 */                          View view4 = currentFragment.getView();
/* 1882 */                          if (view4 != null && !this.mDeleteWasDrag) {
/* 1883 */                              view4.startAnimation(fadeOut);
                                    } else {
/* 1885 */                              animator.start();
                                    }
                                } else {
/* 1888 */                          noAnimationDelete = true;
                                }
                            }
                        }
                    } else {
/* 1896 */              noAnimationDelete = true;
                    }
/* 1898 */          if (noAnimationDelete) {
/* 1901 */              deleteCurrentDraft();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$14 */
/* 1807 */      class C013514 extends SimpleAnimationListener {
                    final /* synthetic */ int val$margin;
                    final /* synthetic */ int val$offset;
                    final /* synthetic */ DraftFragment val$prevFragment;
                    final /* synthetic */ View val$prevView;
                    final /* synthetic */ int[] val$previewLocation;

/* 1808 */          C013514(View view, int[] iArr, int i, int i2, DraftFragment draftFragment) {
                        this.val$prevView = view;
                        this.val$previewLocation = iArr;
                        this.val$offset = i;
                        this.val$margin = i2;
                        this.val$prevFragment = draftFragment;
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 1811 */          public void onAnimationEnd(Animation animation) {
/* 1812 */              FloatingViewUtils.buildFloatingViewFor(AbstractRecordingActivity.this, this.val$prevView, AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this), this.val$prevView.getWidth(), this.val$previewLocation[0], this.val$previewLocation[1] - this.val$offset);
/* 1816 */              AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this).setVisibility(0);
/* 1818 */              new MoveResizeAnimator(3, AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this), AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this), ((int) (AbstractRecordingActivity.access$3500(AbstractRecordingActivity.this).x * (1.0f - AbstractRecordingActivity.this.mPreviewRatio))) - (this.val$margin * 5), this.val$previewLocation[1] - this.val$offset, 1.0d, 1.0d, 300, AbstractRecordingActivity.this, this.val$prevFragment.getView()).start();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.AbstractRecordingActivity$15 */
/* 1869 */      class C013615 extends SimpleAnimationListener {
                    final /* synthetic */ MoveResizeAnimator val$animator;

/* 1870 */          C013615(MoveResizeAnimator moveResizeAnimator) {
                        this.val$animator = moveResizeAnimator;
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 1873 */          public void onAnimationEnd(Animation animation) {
/* 1874 */              this.val$animator.start();
                    }
                }

/* 1905 */      public void cameraPreviewToRecorder(View iconView, View previewIcon) {
/* 1906 */          if (!isResuming()) {
/* 1909 */              this.mIsGoingToRecord = true;
/* 1910 */              float fullSize = this.mScreenSize.x;
/* 1911 */              float ratio = fullSize / iconView.getLayoutParams().width;
/* 1912 */              int top = getResources().getDimensionPixelOffset(C0257R.dimen.progress_view_height);
/* 1914 */              int topOffset = getResources().getDimensionPixelOffset(C0257R.dimen.draft_overlay_offset);
/* 1916 */              int[] location = new int[2];
/* 1917 */              iconView.getLocationInWindow(location);
/* 1918 */              previewIcon.setVisibility(8);
/* 1920 */              FloatingViewUtils.buildFloatingViewFor(this, iconView, this.mPreviewOverlay, iconView.getWidth(), location[0], location[1] - topOffset);
/* 1922 */              this.mPreviewOverlay.setVisibility(0);
/* 1923 */              new MoveResizeAnimator(4, this.mPreviewOverlay, this.mPreviewOverlay, 0, top, ratio, ratio, 300, this, iconView).start();
                    }
                }

/* 1927 */      private boolean isResuming() {
/* 1928 */          if (this.mCurrentFragment instanceof RecordingFragment) {
                        return this.mIsGoingToRecord || ((RecordingFragment) this.mCurrentFragment).isResuming();
                    }
/* 1931 */          return this.mIsGoingToRecord;
                }

/* 1935 */      protected RecordingPreviewFragment createRecordingPreviewFragmentSw(RecordingFile finalFile, String thumbNailPath) {
/* 1936 */          return RecordingPreviewFragment.newInstance(finalFile.getVideoPath(), this.mUploadFile, thumbNailPath, this.mIsMessaging, this.mConversationRowId, this.mDirectUserId, isFromSony(), this.mColor, this.mRecipientUsername, this.mAmFollowingRecipient);
                }

/* 1943 */      protected RecordingPreviewFragment createRecordingPreviewFragmentHw(RecordingFile finalFile, String thumbNailPath) {
/* 1944 */          return RecordingPreviewFragment.newInstance(finalFile.getVideoPath(), null, thumbNailPath, this.mIsMessaging, this.mConversationRowId, this.mDirectUserId, isFromSony(), this.mColor, this.mRecipientUsername, this.mAmFollowingRecipient);
                }

/* 1949 */      public ArrayList<RecorderPlugin<?, VineRecorder>> getPluginList() {
/* 1950 */          ArrayList<RecorderPlugin<?, VineRecorder>> list = new ArrayList<>();
/* 1951 */          if (this.mIsMessaging) {
/* 1952 */              list.add(new CameraSwitcherPlugin());
/* 1953 */              list.add(new GridPlugin());
/* 1954 */              list.add(new FocusPlugin());
/* 1955 */              list.add(new GhostPlugin());
                    } else {
/* 1957 */              list.add(new SecretModePlugin());
/* 1958 */              list.add(new IndividualEditPlugin());
/* 1959 */              list.add(new ImportPlugin());
/* 1960 */              list.add(new DeleteLastSegmentPlugin());
/* 1961 */              list.add(MoreToolPlugin.newInstance());
/* 1962 */              list.add(new CameraSwitcherPlugin());
/* 1963 */              list.add(new DraftPlugin());
/* 1964 */              list.add(new ImportOnboardPlugin());
/* 1965 */              if (RecordConfigUtils.RecordConfig.SHOULD_SHOW_ZOOM_SLIDER) {
/* 1966 */                  list.add(new ZoomPlugin());
                        }
/* 1968 */              if (SLog.sLogsOn) {
/* 1969 */                  list.add(new TimeLapsePlugin());
                        }
                    }
/* 1973 */          return list;
                }

/* 1977 */      public boolean isFromSony() {
/* 1978 */          return false;
                }

/* 1981 */      protected RecordingFragment createRecordingFragment() {
/* 1982 */          return new RecordingFragment();
                }
            }
