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
            
            
            @TargetApi(14)
    public abstract class AbstractRecordingActivity extends BaseActionBarActivity implements DraftViewPager.OnPageChangeListener, SmoothAnimator.AnimationListener, DragUpToDeleteContainer.DragUpListener, ByteBufferQueue.MemoryResponder {
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

        static  SparseArray access$000(AbstractRecordingActivity x0) {
           return x0.mDraftFragments;
                }

        static  View access$1000(AbstractRecordingActivity x0) {
           return x0.mCameraIconOverlay;
                }

        static  boolean access$102(AbstractRecordingActivity x0, boolean x1) {
           x0.mDeleteWasDrag = x1;
                    return x1;
                }

        static  ImageView access$1100(AbstractRecordingActivity x0) {
           return x0.mLargeThumbnailOverlay;
                }

        static  View access$1200(AbstractRecordingActivity x0) {
           return x0.mAnimationPreviewOverlay;
                }

        static  Runnable access$1300(AbstractRecordingActivity x0) {
           return x0.mOnResumeDraftRunnable;
                }

        static  Handler access$1400(AbstractRecordingActivity x0) {
           return x0.mHandler;
                }

        static  boolean access$1500(AbstractRecordingActivity x0) {
           return x0.isResuming();
                }

        static  View access$1600(AbstractRecordingActivity x0) {
           return x0.mCameraIconOverlayImage;
                }

        static  Animation access$1700(AbstractRecordingActivity x0) {
           return x0.mSlowFadeIn;
                }

        static  Animation access$1800(AbstractRecordingActivity x0) {
           return x0.mCameraIconFadeIn;
                }

        static  boolean access$1900(AbstractRecordingActivity x0) {
           return x0.mAnimatingIntoDrafts;
                }

        static  boolean access$1902(AbstractRecordingActivity x0, boolean x1) {
           x0.mAnimatingIntoDrafts = x1;
                    return x1;
                }

        static  View access$200(AbstractRecordingActivity x0) {
           return x0.mDraftTrashBackground;
                }

        static  View access$2000(AbstractRecordingActivity x0) {
           return x0.mDraftFullMask;
                }

        static  DraftViewPager access$2100(AbstractRecordingActivity x0) {
           return x0.mViewPager;
                }

        static  int access$2202(AbstractRecordingActivity x0, int x1) {
           x0.mPageScrollState = x1;
                    return x1;
                }

        static  View access$2300(AbstractRecordingActivity x0) {
           return x0.mDraftMaskTop;
                }

        static  View access$2400(AbstractRecordingActivity x0) {
           return x0.mDraftMaskLeft;
                }

        static  View access$2500(AbstractRecordingActivity x0) {
           return x0.mDraftMaskRight;
                }

        static  Thread access$2602(AbstractRecordingActivity x0, Thread x1) {
           x0.mAddToUploadThread = x1;
                    return x1;
                }

        static  DraftPagerImpl access$2700(AbstractRecordingActivity x0) {
           return x0.mAdapter;
                }

        static  RecordSessionManager.RecordSessionInfo access$2802(AbstractRecordingActivity x0, RecordSessionManager.RecordSessionInfo x1) {
           x0.mCurrentSession = x1;
                    return x1;
                }

        static  ArrayList access$2900(AbstractRecordingActivity x0) {
           return x0.mSessions;
                }

        static  View access$300(AbstractRecordingActivity x0) {
           return x0.mDraftTrashButton;
                }

        static  WeakReference access$3002(AbstractRecordingActivity x0, WeakReference x1) {
           x0.mCameraFragment = x1;
                    return x1;
                }

        static  boolean access$3100(AbstractRecordingActivity x0) {
           return x0.mFirstPageSet;
                }

        static  boolean access$3202(AbstractRecordingActivity x0, boolean x1) {
           x0.mIsGoingToRecord = x1;
                    return x1;
                }

        static  void access$3300(AbstractRecordingActivity x0, DraftFragment x1) {
           x0.buildAndShowFloatingView(x1);
                }

        static  void access$3400(AbstractRecordingActivity x0) {
           x0.releasePlayers();
                }

        static  Point access$3500(AbstractRecordingActivity x0) {
           return x0.mScreenSize;
                }

        static  View access$3600(AbstractRecordingActivity x0) {
           return x0.mPreviewOverlay;
                }

        static  View access$3700(AbstractRecordingActivity x0) {
           return x0.mProgressOverlay;
                }

        static  boolean access$3800(AbstractRecordingActivity x0) {
           return x0.mFirstDraftLaunch;
                }

        static  boolean access$3802(AbstractRecordingActivity x0, boolean x1) {
           x0.mFirstDraftLaunch = x1;
                    return x1;
                }

        static  boolean access$3900(AbstractRecordingActivity x0) {
           return x0.setCurrentSession();
                }

        static  View access$400(AbstractRecordingActivity x0) {
           return x0.mDraftTrashContainer;
                }

        static  void access$500(AbstractRecordingActivity x0) {
           x0.handleDelete();
                }

        static  void access$600(AbstractRecordingActivity x0) {
           x0.showCurrentFragment();
                }

        static  View access$700(AbstractRecordingActivity x0) {
           return x0.mDraftMaskParent;
                }

        static  View access$800(AbstractRecordingActivity x0) {
           return x0.mDraftRoot;
                }

        static  void access$900(AbstractRecordingActivity x0) {
           x0.hideDraftsInternal();
                }

                
       class ViewOnClickListenerC01301 implements View.OnClickListener {
           ViewOnClickListenerC01301() {
                    }

                    @Override 
           public void onClick(View v) {
                    }
                }

                
       class ViewOnTouchListenerC01372 implements View.OnTouchListener {
           ViewOnTouchListenerC01372() {
                    }

                    @Override 
           public boolean onTouch(View v, MotionEvent event) {
               return true;
                    }
                }

                @NonNull
       private RecordSessionVersion getVersion() {
           if (this.mVersion == null) {
               this.mVersion = RecordSessionManager.getCurrentVersion(this);
                    }
           return this.mVersion;
                }

       public static Intent getIntentForGeneric(Context context, int flags, String tag) {
           return getIntent(context, flags, tag, false, -1L, -1L, false, null, false);
                }

                @Override 
       public void requestForMoreMemory() {
                    try {
               AppController.getInstance(this).clearImageCacheFromMemory();
                    } catch (RuntimeException e) {
               CrashUtil.log("Failed to clear image cache memory.");
                    }
                }

       public static Intent getIntentForMessaging(Context context, int flags, String tag) {
           return getIntent(context, flags, tag, true, -1L, -1L, false, null, false);
                }

       public static Intent getIntentForConversation(Context context, int flags, String tag, long conversationRowId, boolean startConversation, String username, boolean amFollowing) {
           return getIntent(context, flags, tag, true, conversationRowId, -1L, startConversation, username, amFollowing);
                }

       private static Intent getIntent(Context context, int flags, String tag, boolean isMessaging, long conversationRowId, long directUserId, boolean startConversation, String recipientName, boolean amFollowing) {
           Intent intent = new Intent(context, (Class<?>) RecordingActivity.class);
           intent.setAction(tag);
           if (flags > 0) {
               intent.setFlags(flags);
                    }
           intent.putExtra("messaging", isMessaging);
           intent.putExtra(EXTRA_CONVERSATION_ROW_ID, conversationRowId);
           intent.putExtra(EXTRA_DIRECT_USER_ID, directUserId);
           intent.putExtra(EXTRA_START_CONVERSATION, startConversation);
           intent.putExtra("recipient_username", recipientName);
           intent.putExtra("am_following", amFollowing);
           return intent;
                }

                @Override 
       public void preSetContentView() {
           if (!CameraManager.hasCamera()) {
               Util.showNoCameraToast(this);
               finish();
                    }
                }

       public AbstractRecordingActivity() {
           if (sQueues.size() > 20) {
               sQueues.clear();
                    }
           sQueues.add(new WeakReference<>(this));
           int n = 0;
                    for (WeakReference<Object> queue : sQueues) {
               if (queue.get() != null) {
                   n++;
                        }
                    }
           CrashUtil.log("[mem] Current RecordingActivity queue count: {}.", Integer.valueOf(n));
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
                    Bundle extras;
           Intent intent = getIntent();
           String action = null;
           if (intent != null) {
               action = intent.getAction();
                    }
           CrashUtil.log("AbstractRecordingActivity {} pid: {}, action tag {}.", this, Integer.valueOf(Process.myPid()), action);
           this.mIntentionalObjectCounter.add();
           FlurryUtils.trackRecordingStart();
           super.onCreate(savedInstanceState, C0257R.layout.record_draft, false, true);
           this.mHelper.bindCameraService(this);
           this.mScreenSize = SystemUtil.getDisplaySize((Activity) this);
                    if (SystemUtil.getMemoryRatio(this, true) < 1.0d) {
               Util.showCenteredToast(this, C0257R.string.unsupported_feature_recording);
               finish();
               return;
                    }
           this.mVersion = RecordSessionManager.getCurrentVersion(this);
           if (savedInstanceState == null) {
               if (intent != null && (extras = intent.getExtras()) != null) {
                   this.mIsMessaging = extras.getBoolean("messaging");
                   this.mConversationRowId = extras.getLong(EXTRA_CONVERSATION_ROW_ID, -1L);
                   this.mTopOverlay = (Bitmap) extras.getParcelable("arg_top_overlay");
                   this.mDirectUserId = extras.getLong(EXTRA_DIRECT_USER_ID, -1L);
                   this.mStartConversation = extras.getBoolean(EXTRA_START_CONVERSATION);
                   this.mRecipientUsername = extras.getString("recipient_username");
                        }
                        try {
                   toRecord(true, false, null);
                        } catch (InvalidStateException e) {
                   throw new RuntimeException(e);
                        }
                    }
           VineAccountHelper.resetActiveSession();
           startService(VineUploadService.getClearNotificationsIntent(this));
           Resources resources = getResources();
           this.mPreviewRatio = 0.85f;
           this.mDraftRoot = findViewById(C0257R.id.drafts_root);
           this.mDraftRoot.setVisibility(8);
           this.mViewPager = (DraftViewPager) findViewById(C0257R.id.pager);
           this.mDots = (DotIndicators) findViewById(C0257R.id.dots);
           this.mPreviewDimen = (int) (this.mScreenSize.x * this.mPreviewRatio);
           this.mPreviewOverlay = findViewById(C0257R.id.preview_overlay);
           this.mProgressOverlay = findViewById(C0257R.id.progress_overlay);
           this.mDraftMaskTop = findViewById(C0257R.id.toDraftTopMask);
           this.mDraftMaskTop.setOnClickListener(this.mEmptyClicker);
           this.mDraftMaskLeft = findViewById(C0257R.id.toDraftLeftMask);
           this.mDraftMaskLeft.setOnClickListener(this.mEmptyClicker);
           this.mDraftMaskRight = findViewById(C0257R.id.toDraftRightMask);
           this.mDraftMaskRight.setOnClickListener(this.mEmptyClicker);
           this.mDraftMaskParent = findViewById(C0257R.id.toDraftParent);
           this.mDraftTrashContainer = findViewById(C0257R.id.draftsTrashContainer);
           this.mDraftTrashBackground = findViewById(C0257R.id.draftsTrashBackground);
           this.mDraftTrashButton = findViewById(C0257R.id.draftsTrashButton);
           this.mDraftTrashButton.setOnClickListener(new ViewOnClickListenerC01383());
           this.mDraftTrashButton.setOnTouchListener(new ViewOnTouchListenerC01394());
           this.mDragUpToDeleteView = (DragUpToDeleteContainer) findViewById(C0257R.id.upDragger);
           this.mDragUpToDeleteView.setVisibility(8);
           this.mDragUpToDeleteView.setInteractionListner(this);
           this.mDraftTrashContainer.setVisibility(8);
           int draftProgDimen = resources.getDimensionPixelOffset(C0257R.dimen.draft_progress_view_height);
           int progressDimen = resources.getDimensionPixelOffset(C0257R.dimen.progress_view_height);
           this.mTopMaskHeight = progressDimen + draftProgDimen;
           this.mSideMaskWidth = (this.mScreenSize.x / 2) - (this.mPreviewDimen / 2);
           View pagerLeftMask = findViewById(C0257R.id.pagerLeftMask);
           ViewGroup.LayoutParams params = pagerLeftMask.getLayoutParams();
           params.width = (int) (this.mPreviewRatio + (this.mScreenSize.x * ((1.0f - this.mPreviewRatio) / 2.0f)));
           pagerLeftMask.setLayoutParams(params);
           if (savedInstanceState != null) {
               if (savedInstanceState.containsKey(STATE_FIRST_PAGE_SET)) {
                   this.mFirstPageSet = savedInstanceState.getBoolean(STATE_FIRST_PAGE_SET);
                        }
               if (savedInstanceState.containsKey(STATE_FIRST_DRAFT_LAUNCH)) {
                   this.mFirstDraftLaunch = savedInstanceState.getBoolean(STATE_FIRST_DRAFT_LAUNCH);
                        } else {
                   this.mFirstDraftLaunch = true;
                        }
                    } else {
               this.mFirstDraftLaunch = true;
                    }
           int bottomRowDimen = resources.getDimensionPixelOffset(C0257R.dimen.draft_dots_height);
           ViewGroup.LayoutParams viewPagerLayout = this.mViewPager.getLayoutParams();
           viewPagerLayout.height = this.mScreenSize.x + progressDimen + bottomRowDimen;
           this.mViewPager.setLayoutParams(viewPagerLayout);
           int dotsHeight = resources.getDimensionPixelOffset(C0257R.dimen.draft_dots_height);
           this.mDots.setY(this.mScreenSize.x + progressDimen + (dotsHeight / 4));
           this.mLargeThumbnailOverlay = (ImageView) findViewById(C0257R.id.large_thumbnail_overlay);
           this.mAnimationPreviewOverlay = findViewById(C0257R.id.animation_preview_overlay);
           this.mCameraIconOverlay = findViewById(C0257R.id.draft_camera_icon_overlay);
           this.mCameraIconOverlay.setVisibility(8);
           this.mCameraIconOverlayImage = findViewById(C0257R.id.preview_icon);
           this.mDraftFullMask = findViewById(C0257R.id.draft_full_mask);
           this.mSlowFadeIn = AnimationUtils.loadAnimation(this, C0257R.anim.slow_fade_in);
           this.mCameraIconFadeIn = AnimationUtils.loadAnimation(this, C0257R.anim.slow_fade_in);
           if (!this.mIsMessaging) {
               SharedPreferences prefs = getSharedPreferences(BasicVineRecorder.PREFS_CAPTURE, 0);
               int launchCount = prefs.getInt(PREF_CAPTURE_LAUNCH_COUNT, 0) + 1;
               prefs.edit().putInt(PREF_CAPTURE_LAUNCH_COUNT, launchCount).apply();
                    }
                }

                
       class ViewOnClickListenerC01383 implements View.OnClickListener {
           ViewOnClickListenerC01383() {
                    }

                    @Override 
           public void onClick(View view) {
               if (AbstractRecordingActivity.this.mCurrentPage != AbstractRecordingActivity.access$000(AbstractRecordingActivity.this).size()) {
                   AbstractRecordingActivity.access$102(AbstractRecordingActivity.this, false);
                   AbstractRecordingActivity.this.showDeleteDialog();
                        }
                    }
                }

                
       class ViewOnTouchListenerC01394 implements View.OnTouchListener {
                    boolean hasMovedOut = false;

           ViewOnTouchListenerC01394() {
                    }

                    
                    @Override 
           public boolean onTouch(View view, MotionEvent event) {
                        switch (event.getAction()) {
                            case 0:
                       if (AbstractRecordingActivity.this.mCurrentPage != AbstractRecordingActivity.access$000(AbstractRecordingActivity.this).size()) {
                           AbstractRecordingActivity.access$200(AbstractRecordingActivity.this).setActivated(true);
                           this.hasMovedOut = false;
                                }
                                return false;
                            case 1:
                       AbstractRecordingActivity.access$200(AbstractRecordingActivity.this).setActivated(false);
                                return false;
                            case 2:
                       float x = event.getX();
                       float y = event.getY();
                                boolean activate = x >= 0.0f && x <= ((float) AbstractRecordingActivity.access$300(AbstractRecordingActivity.this).getWidth()) && y >= 0.0f && y <= ((float) AbstractRecordingActivity.access$300(AbstractRecordingActivity.this).getHeight());
                       if (!activate) {
                           this.hasMovedOut = true;
                                }
                       AbstractRecordingActivity.access$200(AbstractRecordingActivity.this).setActivated(activate && !this.hasMovedOut);
                       AbstractRecordingActivity.access$300(AbstractRecordingActivity.this).setActivated(activate && !this.hasMovedOut);
                                return false;
                            default:
                                return false;
                        }
                    }
                }

       private BaseRecorderPluginManager getRecorderPluginManager() {
           RecordingFragment recordingFragment = getActiveRecordingFragment();
           if (recordingFragment != null) {
               return recordingFragment.getPluginManager();
                    }
                    return null;
                }

                @Override 
       protected void onResume() {
           super.onResume();
           BaseRecorderPluginManager pm = getRecorderPluginManager();
           if (pm != null) {
               pm.onResume(this);
                    }
           this.mIsResumed = true;
                }

                @Override 
       protected void onPause() {
           super.onPause();
           BaseRecorderPluginManager pm = getRecorderPluginManager();
           if (pm != null) {
               pm.onPause();
                    }
           this.mIsResumed = false;
           if (this.mPromptDialog != null && this.mPromptDialog.isVisible()) {
               this.mPromptDialog.dismiss();
                    }
           releasePlayers();
                }

       public Point getScreenSize() {
           return this.mScreenSize;
                }

                @Override 
       protected void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putBoolean(STATE_FIRST_PAGE_SET, this.mFirstPageSet);
           outState.putBoolean(STATE_FIRST_DRAFT_LAUNCH, this.mFirstDraftLaunch);
                }

                @Override 
       public void onPageScrolled(int i, float v, int i2) {
                }

                @Override 
       public boolean prepareForPickup() {
                    WeakReference<DraftFragment> fragment;
           if (!this.mAnimatingIntoDrafts && (fragment = this.mDraftFragments.get(this.mCurrentPage)) != null) {
               DraftFragment ref = fragment.get();
               ref.mCanUnhide = false;
               ref.pausePlayer();
               ref.showImage();
                    }
                    return !this.mAnimatingIntoDrafts;
                }

                @Override 
       public void viewPickedUp() {
           hideCurrentFragment();
                }

                @Override 
       public void viewDestroyFinished() {
           this.mDraftTrashButton.setActivated(false);
           this.mDraftTrashBackground.setActivated(false);
                }

       private void hideCurrentFragment() {
                    DraftFragment ref;
           WeakReference<DraftFragment> fragment = this.mDraftFragments.get(this.mCurrentPage);
           if (fragment != null && (ref = fragment.get()) != null) {
               ref.pausePlayer();
               View toHide = ref.getView();
               if (toHide != null) {
                   toHide.setVisibility(8);
                        }
                    }
                }

       public String makeSureUploadIsReady() {
           if (this.mAddToUploadThread != null) {
                        try {
                   this.mAddToUploadThread.join();
                        } catch (InterruptedException e) {
                   throw new RuntimeException(e);
                        }
                    }
           return this.mUploadFile;
                }

                @Override 
       public boolean viewMoved(int viewTopY) {
           boolean lightTrash = viewTopY < 0;
           this.mDraftTrashBackground.setActivated(lightTrash);
           this.mDraftTrashButton.setActivated(viewTopY < this.mDraftTrashContainer.getHeight());
           return lightTrash;
                }

                @Override 
       public boolean viewDropped(int viewTopY) {
           boolean delete = viewTopY <= 0;
           if (delete && this.mCurrentPage != this.mDraftFragments.size()) {
               this.mDeleteWasDrag = true;
               showDeleteDialog();
                    } else {
               this.mDraftTrashButton.setActivated(false);
                    }
           return delete;
                }

                @Override 
       public void viewLanded() {
           showCurrentFragment();
                }

       private void showCurrentFragment() {
                    DraftFragment ref;
           WeakReference<DraftFragment> fragment = this.mDraftFragments.get(this.mCurrentPage);
           if (fragment != null && (ref = fragment.get()) != null) {
               ref.mCanUnhide = true;
               ref.resumePlayer();
               View v = ref.getView();
               if (v != null) {
                   v.setVisibility(0);
                        }
                    }
                }

       public long getConversationRowId() {
           return this.mConversationRowId;
                }

       public PromptDialogFragment createDialogForPlugins(int dialogId) {
           if (((-16777216) & dialogId) != 0) {
               throw new IllegalArgumentException("Invalid dialog id: " + dialogId);
                    }
           this.mPromptDialog = PromptDialogFragment.newInstance((-16777216) + dialogId);
           return this.mPromptDialog;
                }

                @Override 
       public boolean onCreateOptionsMenu(Menu menu) {
           super.onCreateOptionsMenu(menu);
           BaseRecorderPluginManager pm = getRecorderPluginManager();
           if (pm != null) {
               pm.onCreateOptionsMenu(menu);
               return true;
                    }
           return true;
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           BaseRecorderPluginManager pm = getRecorderPluginManager();
           if (pm == null || !pm.onOptionsItemSelected(item)) {
                        return super.onOptionsItemSelected(item);
                    }
           return true;
                }

       public void showDialog(PromptDialogFragment dialog) {
           if (this.mPromptDialog != dialog) {
               this.mPromptDialog = dialog;
                    }
                    try {
               this.mPromptDialog.show(getFragmentManager());
                    } catch (IllegalStateException e) {
                    }
                }

       public static RegularVineRecorder.DeviceIssueStringGetter getDeviceIssueStringGetter(Context context) {
           return new DeviceIssueStringGetter(context);
                }

       public Fragment getCurrentFragment() {
           return this.mCurrentFragment;
                }

       public class SetSelectedRunnable implements Runnable {
                    public final DraftFragment fragmentToSetSelectedFalse;
                    public final DraftFragment fragmentToSetSelectedTrue;

           public SetSelectedRunnable(DraftFragment fragmentToSetSelectedFalse, DraftFragment fragmentToSetSelectedTrue) {
               this.fragmentToSetSelectedFalse = fragmentToSetSelectedFalse;
               this.fragmentToSetSelectedTrue = fragmentToSetSelectedTrue;
                    }

                    @Override 
           public void run() {
               if (this.fragmentToSetSelectedFalse != null) {
                   this.fragmentToSetSelectedFalse.setSelected(false);
                        }
               if (this.fragmentToSetSelectedTrue != null) {
                   this.fragmentToSetSelectedTrue.setSelected(true);
                        }
                    }
                }

                @Override 
       public void onPageSelected(int i) {
                    DraftFragment ref;
                    View fragView;
                    View toSet;
                    WeakReference<DraftFragment> fragment;
                    DraftFragment ref2;
                    WeakReference<DraftFragment> fragment2;
                    DraftFragment ref3;
           if (this.mSessions != null) {
               DraftFragment fragmentToSetSelectedFalse = null;
               DraftFragment fragmentToSetSelectedTrue = null;
               boolean isPageStateIdle = this.mPageScrollState == 0;
               int numDrafts = this.mSessions.size();
               if (i == -1) {
                   if (this.mCurrentPage <= numDrafts && (fragment2 = this.mDraftFragments.get(this.mCurrentPage)) != null && (ref3 = fragment2.get()) != null) {
                       if (isPageStateIdle) {
                           ref3.setSelected(false);
                                } else {
                           fragmentToSetSelectedFalse = ref3;
                                }
                            }
                        } else {
                   this.mDots.setActiveDot(i);
                   if (i < numDrafts) {
                       if (this.mCurrentPage != -1 && this.mCurrentPage <= numDrafts && (fragment = this.mDraftFragments.get(this.mCurrentPage)) != null && (ref2 = fragment.get()) != null) {
                           if (isPageStateIdle) {
                               ref2.setSelected(false);
                                    } else {
                               fragmentToSetSelectedFalse = ref2;
                                    }
                                }
                       WeakReference<DraftFragment> fragment3 = this.mDraftFragments.get(i);
                       if (fragment3 != null && (ref = fragment3.get()) != null && (fragView = ref.getView()) != null && (toSet = fragView.findViewById(C0257R.id.draft_container)) != null && this.mDragUpToDeleteView != null) {
                           this.mDragUpToDeleteView.setView(toSet, this.mViewPager.getLeftOfSelectedFragment());
                           if (!this.mAnimatingIntoDrafts || i < numDrafts) {
                               this.mDraftTrashContainer.setVisibility(0);
                               this.mDraftTrashContainer.animate().alpha(1.0f).start();
                                    }
                           this.mHandler.removeCallbacks(this.mOnResumeDraftRunnable);
                           if (this.mPageScrollState == 0) {
                               ref.setSelected(true);
                                    } else {
                               fragmentToSetSelectedTrue = ref;
                                    }
                                }
                       this.mCurrentSession = this.mSessions.get(i);
                            } else {
                       if (this.mCurrentPage != -1) {
                           WeakReference<DraftFragment> fragment4 = this.mDraftFragments.get(this.mCurrentPage);
                           if (fragment4 != null) {
                               DraftFragment prev = fragment4.get();
                               if (prev != null) {
                                   if (isPageStateIdle) {
                                       prev.setSelected(false);
                                            } else {
                                       fragmentToSetSelectedFalse = prev;
                                            }
                                        }
                                    } else {
                               SLog.m448d("Fragment {} is null: size: {}.", Integer.valueOf(this.mCurrentPage), Integer.valueOf(this.mDraftFragments.size()));
                                    }
                                }
                       if (this.mCameraFragment != null && this.mCameraFragment.get() != null) {
                           this.mHandler.removeCallbacks(this.mOnResumeDraftRunnable);
                                }
                       this.mCurrentSession = null;
                       this.mDragUpToDeleteView.setView(null, this.mViewPager.getLeftOfSelectedFragment());
                       this.mDraftTrashContainer.animate().alpha(0.0f).setListener(new C01405()).start();
                            }
                   this.mCurrentPage = i;
                        }
               if (fragmentToSetSelectedFalse != null || fragmentToSetSelectedTrue != null) {
                   this.mOnPageIdleRunnable = new SetSelectedRunnable(fragmentToSetSelectedFalse, fragmentToSetSelectedTrue);
                        }
                    }
                }

                
       class C01405 extends SimpleAnimatorListener {
           C01405() {
                    }

                    @Override 
           public void onAnimationEnd(Animator a) {
               AbstractRecordingActivity.access$400(AbstractRecordingActivity.this).setVisibility(8);
               AbstractRecordingActivity.access$400(AbstractRecordingActivity.this).animate().setListener(null);
                    }
                }

                @Override 
       public void onPageScrollStateChanged(int i) {
           this.mPageScrollState = i;
           if (i == 0 && this.mOnPageIdleRunnable != null) {
               this.mOnPageIdleRunnable.run();
               this.mOnPageIdleRunnable = null;
                    }
                }

       public void initMasks(View topMask, View bottomMask) {
           topMask.setOnTouchListener(this.mOnMaskTouchListesner);
           bottomMask.setOnTouchListener(this.mOnMaskTouchListesner);
           SharedPreferences prefs = getSharedPreferences(BasicVineRecorder.PREFS_CAPTURE, 0);
           int bottomMaskHeightPx = prefs.getInt(VineRecorder.getBottomMaskHeightPref(RecordConfigUtils.isDefaultFrontFacing()), 0);
           if (bottomMaskHeightPx > 0) {
               RelativeLayout.LayoutParams bottomMaskParams = (RelativeLayout.LayoutParams) bottomMask.getLayoutParams();
               bottomMaskParams.height = bottomMaskHeightPx;
               runOnUiThread(new RunnableC01416(bottomMask, bottomMaskParams));
                    }
                }

                
       class RunnableC01416 implements Runnable {
                    final  View val$bottomMask;
                    final  RelativeLayout.LayoutParams val$bottomMaskParams;

           RunnableC01416(View view, RelativeLayout.LayoutParams layoutParams) {
                        this.val$bottomMask = view;
                        this.val$bottomMaskParams = layoutParams;
                    }

                    @Override 
           public void run() {
               this.val$bottomMask.setLayoutParams(this.val$bottomMaskParams);
                    }
                }

       protected boolean currentlyHoldsRecordingFragment() {
                    return this.mCurrentFragment != null && (this.mCurrentFragment instanceof RecordingFragment);
                }

       public void onRecorderBackPressed(View v) {
                    try {
               onBackPressed();
                    } catch (IllegalStateException e) {
                    }
                }

       public void showUnSavedChangesToSessionDialog(PromptDialogFragment.OnDialogDoneListener listener) {
           RecordingFragment fragment = getActiveRecordingFragment();
           if (fragment != null) {
               this.mPromptDialog = PromptDialogFragment.newInstance(0);
               this.mPromptDialog.setMessage(fragment.isSavedSession() ? C0257R.string.save_draft_old_confirm : C0257R.string.save_draft_new_confirm);
               this.mPromptDialog.setPositiveButton(C0257R.string.save);
               this.mPromptDialog.setNegativeButton(C0257R.string.discard_changes);
               this.mPromptDialog.setListener(listener);
                        try {
                   this.mPromptDialog.show(getFragmentManager());
                        } catch (IllegalStateException e) {
                        }
                    }
                }

                @Override 
       public void onBackPressed() {
           makeSureUploadIsReady();
                    try {
               if (this.mStep == -1) {
                   super.onBackPressed();
                        } else {
                   BaseRecorderPluginManager pm = getRecorderPluginManager();
                   if (pm == null || !pm.onBackPressed()) {
                       if (isDraftsShowing()) {
                           hideDrafts(true);
                           releasePlayers();
                           if (this.mCurrentFragment instanceof RecordingFragment) {
                               ((RecordingFragment) this.mCurrentFragment).resumeFromDraft();
                                    }
                                } else if (currentlyHoldsRecordingFragment()) {
                           RecordingFragment fragment = (RecordingFragment) this.mCurrentFragment;
                           boolean isEditing = fragment.isEditing();
                           if (!fragment.onBackPressed(isEditing)) {
                               if (!isEditing && !fragment.isSessionModified()) {
                                   fragment.setDiscardChangesOnStop();
                                   setResult(100);
                                   fragment.playStopSound();
                                   super.onBackPressed();
                                        } else if (isEditing && !fragment.isEditingDirty()) {
                                   fragment.playStopSound();
                                   fragment.discardEditing();
                                        } else if (!this.mIsMessaging) {
                                   showUnSavedChangesToSessionDialog(this.mUnsavedChangesDialogDoneListener);
                                        } else {
                                   setResult(100);
                                   super.onBackPressed();
                                        }
                                    }
                                } else if (this.mCurrentFragment instanceof RecordingPreviewFragment) {
                                    try {
                               ((RecordingPreviewFragment) this.mCurrentFragment).previewToRecord(this, false);
                                    } catch (InvalidStateException e) {
                                    }
                                }
                            }
                        }
                    } catch (IllegalStateException e2) {
                    }
                }

       public void onBackPressed(View v) {
           onBackPressed();
                }

                
       class C01427 implements PromptDialogFragment.OnDialogDoneListener {
           C01427() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -2:
                       AbstractRecordingActivity.access$600(AbstractRecordingActivity.this);
                       AbstractRecordingActivity.access$300(AbstractRecordingActivity.this).setActivated(false);
                       AbstractRecordingActivity.access$200(AbstractRecordingActivity.this).setActivated(false);
                                break;
                            case -1:
                       AbstractRecordingActivity.access$500(AbstractRecordingActivity.this);
                       AbstractRecordingActivity.access$300(AbstractRecordingActivity.this).setActivated(false);
                       AbstractRecordingActivity.access$200(AbstractRecordingActivity.this).setActivated(false);
                                break;
                        }
                    }
                }

                
       class C01438 implements PromptDialogFragment.OnDialogDoneListener {
           C01438() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -2:
                       if (AbstractRecordingActivity.this.currentlyHoldsRecordingFragment()) {
                           RecordingFragment frag = (RecordingFragment) AbstractRecordingActivity.this.mCurrentFragment;
                           frag.setDiscardChangesOnStop();
                           frag.playStopSound();
                                }
                       AbstractRecordingActivity.this.discardUpload();
                       AbstractRecordingActivity.this.setResult(100);
                       AbstractRecordingActivity.this.finish();
                                break;
                            case -1:
                       if (AbstractRecordingActivity.this.currentlyHoldsRecordingFragment()) {
                           AbstractRecordingActivity.this.setResult(100);
                           ((RecordingFragment) AbstractRecordingActivity.this.mCurrentFragment).saveSessionAndQuit();
                                }
                                break;
                        }
                    }
                }

       protected void discardUpload() {
           if (this.mUploadFile != null) {
               SLog.m446d("Upload discarded.");
               UploadManager.removeFromUploadQueue(this, this.mUploadFile);
                    }
                }

       public void hideDrafts(boolean animate) {
           CrashUtil.log("Fading away drafts.");
           AnimationSet as = new AnimationSet(true);
           if (animate) {
               as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.COLLAPSE_HEIGHT, this.mDraftMaskTop, 0));
               as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.COLLAPSE_WIDTH, this.mDraftMaskLeft, 0));
               as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.COLLAPSE_WIDTH, this.mDraftMaskRight, 0));
                    }
           as.addAnimation(AnimationUtils.loadAnimation(this, C0257R.anim.fade_out));
           as.setAnimationListener(new C01449(animate));
          as.setDuration(300L);
          this.mDragUpToDeleteView.setView(null, this.mViewPager.getLeftOfSelectedFragment());
          this.mDraftRoot.startAnimation(as);
          this.mDraftTrashContainer.animate().cancel();
          this.mDraftTrashContainer.setVisibility(8);
                }

                
       class C01449 extends SimpleAnimationListener {
                    final  boolean val$animate;

           C01449(boolean z) {
                        this.val$animate = z;
                    }

                    @Override 
           public void onAnimationStart(Animation animation) {
               if (this.val$animate) {
                   AbstractRecordingActivity.access$700(AbstractRecordingActivity.this).setVisibility(0);
                        } else {
                   AbstractRecordingActivity.access$700(AbstractRecordingActivity.this).setVisibility(8);
                   AbstractRecordingActivity.access$800(AbstractRecordingActivity.this).setVisibility(8);
                        }
                    }

                    @Override 
          public void onAnimationEnd(Animation animation) {
              AbstractRecordingActivity.access$900(AbstractRecordingActivity.this);
                    }
                }

      private void hideDraftsInternal() {
          this.mDraftRoot.setVisibility(8);
          this.mProgressOverlay.setVisibility(8);
          this.mPreviewOverlay.setVisibility(8);
          this.mProgressOverlay.setVisibility(8);
          this.mDragUpToDeleteView.setVisibility(8);
          if (this.mCurrentFragment instanceof RecordingFragment) {
              ((RecordingFragment) this.mCurrentFragment).getPluginManager().onHideDrafts();
                    }
                }

      public boolean isDraftsShowing() {
                    return (this.mDraftRoot == null || this.mDraftRoot.getVisibility() == 8) ? false : true;
                }

      public void showDrafts(String sessionId) {
                    try {
              CrashUtil.log("Started showing drafts.");
              boolean goToCamera = START_SESSION_ID_CAMERA.equals(sessionId);
              this.mAnimatingIntoDrafts = true;
              this.mStartSessionId = sessionId;
              this.mFirstPageSet = false;
              reload(RecordSessionManager.getValidSessions(this, getVersion()));
              this.mDraftFullMask.setVisibility(0);
              this.mDraftMaskParent.setVisibility(0);
              this.mCameraIconOverlayImage.setVisibility(8);
              Resources r = getResources();
              int progressDimen = r.getDimensionPixelSize(C0257R.dimen.progress_view_height);
              int draftProgressDimen = r.getDimensionPixelOffset(C0257R.dimen.draft_progress_view_height);
              int topMaskHeight = this.mTopMaskHeight;
              if (goToCamera) {
                  topMaskHeight += draftProgressDimen;
                        }
              if (!goToCamera && (this.mCurrentFragment instanceof RecordingFragment)) {
                  ImageView thumb = this.mLargeThumbnailOverlay;
                  RelativeLayout.LayoutParams thumbnailParams = (RelativeLayout.LayoutParams) thumb.getLayoutParams();
                  thumbnailParams.width = this.mScreenSize.x;
                  thumbnailParams.height = this.mScreenSize.x;
                  thumbnailParams.leftMargin = 0;
                  thumbnailParams.topMargin = progressDimen;
                  thumb.setLayoutParams(thumbnailParams);
                  String path = ((RecordingFragment) this.mCurrentFragment).getThumbnailPath();
                  BitmapDrawable drawable = new BitmapDrawable(getResources(), path);
                  ViewUtil.setBackground(thumb, drawable);
                  thumb.setVisibility(0);
                        } else if (goToCamera) {
                  this.mCameraIconOverlay.setVisibility(0);
                  RelativeLayout.LayoutParams iconParams = (RelativeLayout.LayoutParams) this.mCameraIconOverlay.getLayoutParams();
                  iconParams.width = this.mScreenSize.x;
                  iconParams.height = this.mScreenSize.x;
                  iconParams.topMargin = progressDimen;
                  iconParams.leftMargin = 0;
                  this.mCameraIconOverlay.setLayoutParams(iconParams);
                        }
              this.mViewPager.setOnTouchListener(new ViewOnTouchListenerC013110(goToCamera));
              AnimationSet as = new AnimationSet(true);
              as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.EXPAND_HEIGHT, this.mDraftMaskTop, topMaskHeight));
              as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.EXPAND_WIDTH, this.mDraftMaskLeft, this.mSideMaskWidth));
              as.addAnimation(ViewUtil.makeResizeAnimation(ViewUtil.ResizeAnimationType.EXPAND_WIDTH, this.mDraftMaskRight, this.mSideMaskWidth));
              as.setAnimationListener(new C013211(goToCamera));
              as.setDuration(300L);
              setCurrentSession();
              this.mDragUpToDeleteView.setVisibility(0);
              if (!goToCamera && (this.mCurrentFragment instanceof RecordingFragment)) {
                  View progress = ((RecordingFragment) this.mCurrentFragment).getProgressView();
                  if (progress != null) {
                      FloatingViewUtils.buildFloatingViewFor(this, progress, this.mAnimationPreviewOverlay, progress.getWidth(), 0, 0);
                            }
                  this.mAnimationPreviewOverlay.setVisibility(0);
                        }
              if (goToCamera) {
                  new MoveResizeAnimator(7, this.mCameraIconOverlay, this.mCameraIconOverlay, (int) ((this.mScreenSize.x * (1.0f - this.mPreviewRatio)) / 2.0f), progressDimen + (draftProgressDimen * 2), this.mPreviewRatio, this.mPreviewRatio, 300, this, null).start();
                        } else {
                  new MoveResizeAnimator(6, this.mAnimationPreviewOverlay, this.mAnimationPreviewOverlay, (int) ((this.mScreenSize.x * (1.0f - this.mPreviewRatio)) / 2.0f), progressDimen + draftProgressDimen, this.mPreviewRatio, 0.5d, 300, this, null).start();
                  new MoveResizeAnimator(5, this.mLargeThumbnailOverlay, this.mLargeThumbnailOverlay, (int) ((this.mScreenSize.x * (1.0f - this.mPreviewRatio)) / 2.0f), progressDimen + (draftProgressDimen * 2), this.mPreviewRatio, this.mPreviewRatio, 300, this, null).start();
                        }
              this.mDraftRoot.startAnimation(as);
                    } catch (IOException e) {
              CrashUtil.logException(e, "Error refresh reloading drafts.", new Object[0]);
                    } catch (Exception e2) {
              CrashUtil.logException(e2, "Error showing reloading drafts.", new Object[0]);
                    }
                }

                
      class ViewOnTouchListenerC013110 implements View.OnTouchListener {
                    final  boolean val$goToCamera;

          ViewOnTouchListenerC013110(boolean z) {
                        this.val$goToCamera = z;
                    }

                    @Override 
          public boolean onTouch(View v, MotionEvent event) {
              if (this.val$goToCamera) {
                  AbstractRecordingActivity.access$1000(AbstractRecordingActivity.this).setVisibility(8);
                  return false;
                        }
              AbstractRecordingActivity.access$1100(AbstractRecordingActivity.this).setVisibility(8);
              AbstractRecordingActivity.access$1200(AbstractRecordingActivity.this).setVisibility(8);
              return false;
                    }
                }

                
      class C013211 extends SimpleAnimationListener {
                    final  boolean val$goToCamera;

          C013211(boolean z) {
                        this.val$goToCamera = z;
                    }

                    @Override 
          public void onAnimationStart(Animation animation) {
              AbstractRecordingActivity.access$1400(AbstractRecordingActivity.this).removeCallbacks(AbstractRecordingActivity.access$1300(AbstractRecordingActivity.this));
              AbstractRecordingActivity.access$1400(AbstractRecordingActivity.this).postDelayed(AbstractRecordingActivity.access$1300(AbstractRecordingActivity.this), 300L);
                    }

                    @Override 
          public void onAnimationEnd(Animation animation) {
              if (AbstractRecordingActivity.this.mCurrentPage != AbstractRecordingActivity.access$000(AbstractRecordingActivity.this).size()) {
                  AbstractRecordingActivity.access$400(AbstractRecordingActivity.this).setAlpha(0.0f);
                  AbstractRecordingActivity.access$400(AbstractRecordingActivity.this).setVisibility(0);
                  if (!AbstractRecordingActivity.access$1500(AbstractRecordingActivity.this)) {
                      AbstractRecordingActivity.access$400(AbstractRecordingActivity.this).animate().alpha(1.0f).start();
                            }
                        }
              AbstractRecordingActivity.access$1700(AbstractRecordingActivity.this).setAnimationListener(new AnonymousClass1());
              AbstractRecordingActivity.access$1400(AbstractRecordingActivity.this).postDelayed(new AnonymousClass2(), 150L);
              AbstractRecordingActivity.access$2100(AbstractRecordingActivity.this).setVisibility(0);
              AbstractRecordingActivity.access$2100(AbstractRecordingActivity.this).startAnimation(AbstractRecordingActivity.access$1700(AbstractRecordingActivity.this));
              AbstractRecordingActivity.access$2202(AbstractRecordingActivity.this, 0);
              AbstractRecordingActivity.access$800(AbstractRecordingActivity.this).setVisibility(0);
              if (AbstractRecordingActivity.this.mCurrentFragment instanceof RecordingFragment) {
                  ((RecordingFragment) AbstractRecordingActivity.this.mCurrentFragment).getPluginManager().onShowDrafts();
                        }
              AbstractRecordingActivity.access$700(AbstractRecordingActivity.this).setVisibility(8);
              AbstractRecordingActivity.access$2300(AbstractRecordingActivity.this).setVisibility(8);
              AbstractRecordingActivity.access$2400(AbstractRecordingActivity.this).setVisibility(8);
              AbstractRecordingActivity.access$2500(AbstractRecordingActivity.this).setVisibility(8);
                    }

                    
          class AnonymousClass1 extends SimpleAnimationListener {
              AnonymousClass1() {
                        }

                        @Override 
              public void onAnimationStart(Animation animation) {
                  if (C013211.this.val$goToCamera) {
                      AbstractRecordingActivity.access$1600(AbstractRecordingActivity.this).setVisibility(0);
                      AbstractRecordingActivity.access$1600(AbstractRecordingActivity.this).setAlpha(0.0f);
                      AbstractRecordingActivity.access$1600(AbstractRecordingActivity.this).animate().alpha(1.0f).setDuration(AbstractRecordingActivity.access$1700(AbstractRecordingActivity.this).getDuration()).setListener(new ViewGoneAnimationListener(AbstractRecordingActivity.access$1600(AbstractRecordingActivity.this))).start();
                      AbstractRecordingActivity.access$1400(AbstractRecordingActivity.this).postDelayed(new RunnableC13121(), AbstractRecordingActivity.access$1800(AbstractRecordingActivity.this).getDuration());
                            }
                        }

                        
              class RunnableC13121 implements Runnable {
                  RunnableC13121() {
                            }

                            @Override 
                  public void run() {
                      AbstractRecordingActivity.access$1000(AbstractRecordingActivity.this).setVisibility(8);
                            }
                        }

                        
              class AnonymousClass2 implements Runnable {
                  AnonymousClass2() {
                            }

                            @Override 
                  public void run() {
                      if (!C013211.this.val$goToCamera) {
                          AbstractRecordingActivity.access$1100(AbstractRecordingActivity.this).setVisibility(8);
                          AbstractRecordingActivity.access$1200(AbstractRecordingActivity.this).setVisibility(8);
                                }
                            }
                        }

                        @Override 
              public void onAnimationEnd(Animation animation) {
                  AbstractRecordingActivity.access$1902(AbstractRecordingActivity.this, false);
                  AbstractRecordingActivity.access$1400(AbstractRecordingActivity.this).postDelayed(new AnonymousClass2(), 300L);
                        }
                    }

                    
          class AnonymousClass2 implements Runnable {
              AnonymousClass2() {
                        }

                        @Override 
              public void run() {
                  AbstractRecordingActivity.access$2000(AbstractRecordingActivity.this).setVisibility(8);
                        }
                    }
                }

      public void toRecord(boolean isNew, boolean startWithEdit, RecordingFile fileToUse) throws InvalidStateException {
          this.mStep = 0;
          this.mFirstPageSet = false;
          onPageSelected(-1);
          boolean fromPreview = fileToUse != null;
                    Object[] objArr = new Object[3];
                    objArr[0] = Boolean.valueOf(isNew);
                    objArr[1] = Boolean.valueOf(startWithEdit);
                    objArr[2] = Boolean.valueOf(fileToUse != null);
          CrashUtil.log("To recording fragment: {} {} {}", objArr);
          if (fromPreview) {
              discardUpload();
                    }
          RecordingFragment fragment = createRecordingFragment();
          Bundle args = new Bundle();
          args.putParcelable("arg_top_overlay", this.mTopOverlay);
          args.putParcelable(RecordingFragment.ARG_SCREEN_SIZE, this.mScreenSize);
          args.putBoolean(RecordingFragment.ARG_IS_MESSAGING, this.mIsMessaging);
          args.putString("recipient_username", this.mRecipientUsername);
          this.mColor = Util.getDefaultSharedPrefs(this).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
          args.putInt("color", this.mColor);
          fragment.setArguments(args);
          fragment.setFileFileToUse(fileToUse);
          fragment.setStartWithEdit(startWithEdit);
          FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
          if (isNew) {
              ft.add(C0257R.id.fragment_container, fragment);
                    } else {
              ft.replace(C0257R.id.fragment_container, fragment);
                    }
                    try {
              ft.commit();
              this.mCurrentFragment = fragment;
              this.mRegularUiMode = getWindow().getDecorView().getSystemUiVisibility();
                    } catch (IllegalStateException e) {
              throw new InvalidStateException(e);
                    }
                }

      public void swapFolder(String tag, File folder) {
          if (this.mCurrentFragment instanceof RecordingFragment) {
              RecordingFragment rf = (RecordingFragment) this.mCurrentFragment;
              if (!rf.isResuming()) {
                  rf.swapFolder(tag, folder);
                        }
                    }
                }

      public static class InvalidStateException extends Exception {
          public InvalidStateException(Exception e) {
              super(e);
                    }
                }

      public void toPreview(String source, RecordingFile finalFile, String thumbNailPath, MediaUtil.GenerateThumbnailsRunnable grabThumbnailRunnable) throws InvalidStateException {
                    RecordingPreviewFragment fragment;
          CrashUtil.log("From {} to preview fragment: {} {}", source, finalFile, thumbNailPath);
          getWindow().getDecorView().setSystemUiVisibility(this.mRegularUiMode);
                    try {
              String reference = finalFile.folder.getName();
              Cursor cursor = UploadManager.getReferenceCursor(this, reference);
              if (cursor != null && cursor.moveToFirst()) {
                  CrashUtil.log("User have edited the vine already, discard until new one comes in.");
                  startService(VineUploadService.getDiscardIntent(this, cursor.getString(1)));
                        }
              if (cursor != null) {
                  cursor.close();
                        }
                    } catch (Exception e) {
              CrashUtil.log("Failed to delete previous vines.");
                    }
          if (BuildUtil.isIsHwEncodingEnabled()) {
              this.mAddToUploadThread = new C013312(grabThumbnailRunnable, finalFile, thumbNailPath);
              this.mAddToUploadThread.start();
                    } else if (AppController.getInstance(this).isLoggedIn()) {
                        try {
                  this.mUploadFile = UploadManager.addToUploadQueue(this, finalFile, thumbNailPath, finalFile.folder.getName(), this.mIsMessaging, this.mConversationRowId);
                        } catch (Exception e2) {
                  throw new RuntimeException(e2);
                        }
                    }
          if (this.mCurrentFragment instanceof RecordingFragment) {
              this.mCurrentFragment.onPause();
                    }
          if (BuildUtil.isIsHwEncodingEnabled()) {
              fragment = createRecordingPreviewFragmentHw(finalFile, thumbNailPath);
                    } else {
              fragment = createRecordingPreviewFragmentSw(finalFile, thumbNailPath);
                    }
          fragment.setFinalFile(finalFile);
          this.mStep = 1;
          this.mHasPreviewedAlready = true;
                    try {
              getSupportFragmentManager().beginTransaction().replace(C0257R.id.fragment_container, fragment).commit();
              if (this.mCurrentFragment instanceof RecordingFragment) {
                  ((RecordingFragment) this.mCurrentFragment).release();
                        }
              this.mCurrentFragment = fragment;
                    } catch (IllegalStateException e3) {
              throw new InvalidStateException(e3);
                    }
                }

                
      class C013312 extends Thread {
                    final  RecordingFile val$finalFile;
                    final  MediaUtil.GenerateThumbnailsRunnable val$grabThumbnailRunnable;
                    final  String val$thumbNailPath;

          C013312(MediaUtil.GenerateThumbnailsRunnable generateThumbnailsRunnable, RecordingFile recordingFile, String str) {
                        this.val$grabThumbnailRunnable = generateThumbnailsRunnable;
                        this.val$finalFile = recordingFile;
                        this.val$thumbNailPath = str;
                    }

                    @Override 
          public void run() {
              if (this.val$grabThumbnailRunnable != null) {
                  this.val$grabThumbnailRunnable.run();
                        }
              if (AppController.getInstance(AbstractRecordingActivity.this.getApplicationContext()).isLoggedIn()) {
                            try {
                      AbstractRecordingActivity.this.mUploadFile = UploadManager.addToUploadQueue(AbstractRecordingActivity.this.getApplicationContext(), this.val$finalFile, this.val$thumbNailPath, this.val$finalFile.folder.getName(), AbstractRecordingActivity.this.mIsMessaging, AbstractRecordingActivity.this.mConversationRowId);
                            } catch (Exception e) {
                      throw new RuntimeException(e);
                            }
                        }
              AbstractRecordingActivity.access$2602(AbstractRecordingActivity.this, null);
                    }
                }

      public void onFinishPressed(View v) {
          if (this.mCurrentFragment instanceof RecordingFragment) {
              ((RecordingFragment) this.mCurrentFragment).onFinishPressed(v);
                    }
                }

                @Override 
      protected void onStart() {
          super.onStart();
          BaseRecorderPluginManager pm = getRecorderPluginManager();
          if (pm != null) {
              pm.onStart(this);
                    }
                }

                @Override 
      protected void onStop() {
          super.onStop();
          BaseRecorderPluginManager pm = getRecorderPluginManager();
          if (pm != null) {
              pm.onStop(this);
                    }
                }

                @Override 
      public void onActivityResult(int requestCode, int resultCode, Intent data) {
          if (this.mCurrentFragment instanceof RecordingPreviewFragment) {
              this.mCurrentFragment.onActivityResult(requestCode, resultCode, data);
              return;
                    }
          if (requestCode == 1 && currentlyHoldsRecordingFragment()) {
              this.mCurrentFragment.onActivityResult(requestCode, resultCode, data);
                    } else if (resultCode == 31 || resultCode == 32) {
              finish();
                    } else {
              super.onActivityResult(requestCode, resultCode, data);
                    }
                }

      private RecordingFragment getActiveRecordingFragment() {
          if (!isDraftsShowing() && this.mStep == 0 && (this.mCurrentFragment instanceof RecordingFragment)) {
                        return (RecordingFragment) this.mCurrentFragment;
                    }
                    return null;
                }

                @Override 
      public boolean onKeyDown(int keyCode, KeyEvent event) {
                    BaseRecorderPluginManager pluginManager;
          boolean actionCompleted = false;
          RecordingFragment recordingFragment = getActiveRecordingFragment();
          if (recordingFragment != null && (pluginManager = recordingFragment.getPluginManager()) != null) {
              actionCompleted = pluginManager.onKeyDown(keyCode, event);
                    }
          if (actionCompleted || keyCode == 80) {
                        return true;
                    }
          if (recordingFragment != null && keyCode == 27) {
              this.mHasStartedRelativeTimeFromHardwareButton = true;
              recordingFragment.startRelativeTime();
                        return true;
                    }
          return super.onKeyDown(keyCode, event);
                }

                @Override 
      public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
                    BaseRecorderPluginManager pluginManager;
          boolean actionCompleted = false;
          RecordingFragment recordingFragment = getActiveRecordingFragment();
          if (recordingFragment != null && (pluginManager = recordingFragment.getPluginManager()) != null) {
              actionCompleted = pluginManager.onKeyUp(keyCode, event);
                    }
          if (actionCompleted || keyCode == 80) {
                        return true;
                    }
          if (recordingFragment != null && keyCode == 27 && this.mHasStartedRelativeTimeFromHardwareButton) {
              recordingFragment.endRelativeTime();
                        return true;
                    }
          return super.onKeyUp(keyCode, event);
                }

      public boolean startRelativeTime() {
          if (this.mCurrentFragment instanceof RecordingFragment) {
              return ((RecordingFragment) this.mCurrentFragment).startRelativeTime();
                    }
                    return false;
                }

      public boolean hasPreviewedAlready() {
          return this.mHasPreviewedAlready;
                }

      public void deleteCurrentDraft() {
                    int nextIndex;
                    DraftFragment frag;
                    try {
              if (this.mCurrentPage == 0) {
                  nextIndex = 0;
                        } else {
                  nextIndex = this.mCurrentPage - 1;
                        }
              this.mCurrentPage = -1;
              int current = this.mViewPager.getCurrentItem();
              WeakReference<DraftFragment> fragRef = this.mDraftFragments.get(current);
              if (fragRef != null && (frag = fragRef.get()) != null) {
                  frag.setExpired(true);
                        }
              if (this.mCurrentSession != null) {
                  RecordSessionManager.deleteSession(this.mCurrentSession.folder, "deleteCurrentDraft");
                        }
              FlurryUtils.trackAbandonedStage("draft");
              this.mStartSessionId = null;
              ArrayList<RecordSessionManager.RecordSessionInfo> sessions = RecordSessionManager.getValidSessions(this, getVersion());
              reload(sessions);
              this.mSessions = sessions;
              this.mViewPager.requestLayout();
              this.mViewPager.invalidate();
              this.mAdapter.notifyDataSetChanged();
              this.mViewPager.setCurrentItem(nextIndex, false);
              onPageSelected(nextIndex);
                    } catch (IOException e) {
              SLog.m459e("Failed to delete current draft.", (Throwable) e);
                    }
                }

      private void releasePlayers() {
                    for (int i = 0; i < this.mDraftFragments.size(); i++) {
              WeakReference<DraftFragment> r = this.mDraftFragments.get(this.mDraftFragments.keyAt(i));
              DraftFragment ref = r.get();
              if (ref != null) {
                  ref.setSelected(false);
                  ref.release();
                        }
                    }
                }

      private void buildAndShowFloatingView(DraftFragment ref) {
          releasePlayers();
          ref.showImage();
          int[] previewLocation = new int[2];
          View thumbnailView = ref.getThumbnailView();
          thumbnailView.getLocationOnScreen(previewLocation);
          int yOffset = getResources().getDimensionPixelOffset(C0257R.dimen.draft_overlay_offset);
          FloatingViewUtils.buildFloatingViewFor(this, thumbnailView, this.mPreviewOverlay, thumbnailView.getWidth(), previewLocation[0], previewLocation[1] - yOffset);
          int[] progressLocation = new int[2];
          View progressView = ref.getProgressView();
          progressView.getLocationOnScreen(progressLocation);
          FloatingViewUtils.buildFloatingViewFor(this, progressView, this.mProgressOverlay, progressView.getWidth(), progressLocation[0], progressLocation[1] - yOffset);
          this.mPreviewOverlay.setVisibility(0);
          this.mProgressOverlay.setVisibility(0);
          this.mDraftTrashContainer.setVisibility(8);
          this.mViewPager.setVisibility(8);
                }

      private void reload(ArrayList<RecordSessionManager.RecordSessionInfo> sessions) {
          int size = sessions.size();
          if (size > 9) {
              this.mPromptDialog = PromptDialogFragment.newInstance(3);
              this.mPromptDialog.setMessage(C0257R.string.too_many_drafts);
              this.mPromptDialog.setPositiveButton(C0257R.string.f88ok);
              this.mPromptDialog.show(getFragmentManager());
              sessions.remove(size - 1);
                    }
          this.mSessions = sessions;
          this.mAdapter = new DraftPagerImpl(getSupportFragmentManager());
          this.mViewPager.setAdapter(this.mAdapter);
          this.mViewPager.setOnPageChangeListener(this);
          this.mViewPager.setOffscreenPageLimit(this.mAdapter.getCount() + 1);
          this.mDots.setNumberOfDots(sessions.size());
          this.mDots.invalidate();
                }

                @Override 
      public void onAnimationFinish(int id, Object tag) {
          if (this.mIsResumed) {
                        switch (id) {
                            case 1:
                      Animation fadeOut = AnimationUtils.loadAnimation(this, C0257R.anim.fade_out);
                      this.mDraftRoot.startAnimation(fadeOut);
                      hideDrafts(false);
                      swapFolder("PreviewAnimationFinish", (File) tag);
                      this.mIsGoingToRecord = false;
                                break;
                            case 3:
                      deleteCurrentDraft();
                      this.mPreviewOverlay.setVisibility(8);
                                break;
                            case 4:
                      hideDrafts(false);
                      swapFolder("CameraAnimationFinish", null);
                      this.mIsGoingToRecord = false;
                                break;
                        }
                    }
                }

      public synchronized void addPlayerToPool(SdkVideoView videoView) {
                    try {
                        this.mVideoViews.add(videoView);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

      public synchronized void releaseOtherPlayers(SdkVideoView videoView) {
                    try {
                        for (SdkVideoView v : this.mVideoViews) {
                  if (v != videoView) {
                      v.suspend();
                            }
                        }
              this.mVideoViews.clear();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                
      class RunnableC013413 implements Runnable {
          RunnableC013413() {
                    }

                    @Override 
          public void run() {
                        DraftFragment fragment;
              if (AbstractRecordingActivity.access$2700(AbstractRecordingActivity.this).getCount() != 1 && AbstractRecordingActivity.this.mCurrentPage != AbstractRecordingActivity.access$2700(AbstractRecordingActivity.this).getCount() - 1) {
                  AbstractRecordingActivity.access$2802(AbstractRecordingActivity.this, (RecordSessionManager.RecordSessionInfo) AbstractRecordingActivity.access$2900(AbstractRecordingActivity.this).get(AbstractRecordingActivity.this.mCurrentPage));
                  WeakReference<DraftFragment> f = (WeakReference) AbstractRecordingActivity.access$000(AbstractRecordingActivity.this).get(AbstractRecordingActivity.this.mCurrentPage);
                  if (f != null && (fragment = f.get()) != null) {
                      fragment.setSelected(true);
                            }
                        }
                    }
                }

      private class DraftPagerImpl extends DraftFragmentStatePagerAdapter {
          public DraftPagerImpl(FragmentManager fm) {
              super(fm);
                    }

                    @Override 
          public int getCount() {
              return AbstractRecordingActivity.access$2900(AbstractRecordingActivity.this).size() + 1;
                    }

                    @Override 
          public Fragment getItem(int i) {
              if (i == AbstractRecordingActivity.access$2900(AbstractRecordingActivity.this).size()) {
                  DraftCameraPreviewFragment fragment = new DraftCameraPreviewFragment();
                  fragment.setDimen(AbstractRecordingActivity.this.mPreviewDimen);
                  AbstractRecordingActivity.access$3002(AbstractRecordingActivity.this, new WeakReference(fragment));
                  return fragment;
                        }
              RecordSessionManager.RecordSessionInfo session = (RecordSessionManager.RecordSessionInfo) AbstractRecordingActivity.access$2900(AbstractRecordingActivity.this).get(i);
              DraftFragment fragment2 = new DraftFragment(i == 0 && !AbstractRecordingActivity.access$3100(AbstractRecordingActivity.this), i, session.video.getPath(), session.thumb.getPath(), session.folder, session.meta.getProgress(), AbstractRecordingActivity.this.mPreviewDimen);
              fragment2.setListener(new ViewOnClickListenerC01451(fragment2));
              AbstractRecordingActivity.access$000(AbstractRecordingActivity.this).put(i, new WeakReference(fragment2));
              return fragment2;
                    }

                    
          class ViewOnClickListenerC01451 implements View.OnClickListener {
                        final  DraftFragment val$fragment;

              ViewOnClickListenerC01451(DraftFragment draftFragment) {
                            this.val$fragment = draftFragment;
                        }

                        @Override 
              public void onClick(View v) {
                  if (v.getId() == 2131427531 && !AbstractRecordingActivity.access$1900(AbstractRecordingActivity.this) && !AbstractRecordingActivity.access$1500(AbstractRecordingActivity.this)) {
                      CrashUtil.log("User clicked on fragment to resume.");
                      AbstractRecordingActivity.access$3202(AbstractRecordingActivity.this, true);
                      AbstractRecordingActivity.access$3300(AbstractRecordingActivity.this, this.val$fragment);
                      AbstractRecordingActivity.access$3400(AbstractRecordingActivity.this);
                      float fullSize = AbstractRecordingActivity.access$3500(AbstractRecordingActivity.this).x;
                      float ratio = fullSize / AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this).getLayoutParams().width;
                      new MoveResizeAnimator(2, AbstractRecordingActivity.access$3700(AbstractRecordingActivity.this), 0, ratio, 2.0d, 300, null, v.getTag()).start();
                      new MoveResizeAnimator(1, AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this), AbstractRecordingActivity.access$3700(AbstractRecordingActivity.this).getLayoutParams().height * 2, ratio, ratio, 300, AbstractRecordingActivity.this, v.getTag()).start();
                            }
                        }
                    }

                    @Override 
          public void destroyItem(ViewGroup container, int position, Object object) {
                        try {
                  super.destroyItem(container, position, object);
                  if (object instanceof DraftFragment) {
                      FragmentManager m = AbstractRecordingActivity.this.getSupportFragmentManager();
                      FragmentTransaction ft = m.beginTransaction();
                      ft.remove((DraftFragment) object);
                      ft.commit();
                            }
                        } catch (IllegalStateException e) {
                  CrashUtil.log("Failed to remove draft, it's probably not there any more.");
                        }
                    }

                    @Override 
          public int getItemPosition(Object object) {
              if (object instanceof DraftFragment) {
                  DraftFragment frag = (DraftFragment) object;
                  if (frag.isExpired()) {
                      return -2;
                            }
                  return -1;
                        }
              return super.getItemPosition(object);
                    }

                    @Override 
          public void finishUpdate(ViewGroup container) {
              super.finishUpdate(container);
              if (AbstractRecordingActivity.access$3800(AbstractRecordingActivity.this)) {
                  AbstractRecordingActivity.access$3802(AbstractRecordingActivity.this, !AbstractRecordingActivity.access$3900(AbstractRecordingActivity.this));
                        }
                    }
                }

      public void showDeleteDialog() {
          PromptDialogFragment p = PromptDialogFragment.newInstance(1);
          p.setListener(this.mDeleteDialogDoneListener);
          p.setMessage(C0257R.string.delete_draft_confirm);
          p.setPositiveButton(C0257R.string.delete_yes);
          p.setNegativeButton(C0257R.string.cancel);
          p.show(getFragmentManager());
                }

      private boolean setCurrentSession() {
                    DraftFragment ref;
          int i = 0;
          if (this.mViewPager.isDrawn() && !this.mFirstPageSet) {
              if (TextUtils.isEmpty(this.mStartSessionId) || START_SESSION_ID_CAMERA.equals(this.mStartSessionId)) {
                  this.mViewPager.setCurrentItem(this.mAdapter.getCount() - 1, false);
                  this.mFirstPageSet = true;
                            return true;
                        }
                        for (RecordSessionManager.RecordSessionInfo session : this.mSessions) {
                  if (this.mStartSessionId.equals(session.folder.getName())) {
                      this.mViewPager.setCurrentItem(i, false);
                      this.mCurrentPage = i;
                      this.mFirstPageSet = true;
                      WeakReference<DraftFragment> item = this.mDraftFragments.get(this.mCurrentPage);
                      View toSet = null;
                      if (item != null && (ref = item.get()) != null) {
                          toSet = ref.getView().findViewById(C0257R.id.draft_container);
                                }
                      this.mDragUpToDeleteView.setView(toSet, this.mViewPager.getLeftOfSelectedFragment());
                                return true;
                            }
                  i++;
                        }
                    }
                    return false;
                }

                @Override 
      protected void onDestroy() {
          super.onDestroy();
          this.mHelper.unBindCameraService(this);
          BaseRecorderPluginManager pm = getRecorderPluginManager();
          if (pm != null) {
              pm.onDestroy();
                    }
                    try {
              this.mIntentionalObjectCounter.release();
              int count = this.mIntentionalObjectCounter.getCount();
              if (count == 0) {
                  CrashUtil.log("Clean up folders because we are the last one.");
                  getVersion().getManager(this).cleanUnusedFolders();
                        } else if (count > 1) {
                  CrashUtil.logException(new VineLoggingException("Double instance violation, but it's ok."));
                        }
                    } catch (IOException e) {
              CrashUtil.logException(e);
                    }
          this.mCurrentFragment = null;
          CrashUtil.log("[mem] AbstractRecordingActivity {} Destroyed.", this);
          FlurryUtils.trackRecordingDestroy();
          View root = findViewById(C0257R.id.recording_root);
                    try {
              unbindDrawables(root);
                    } catch (Exception e2) {
              SLog.m455e("Failed to remove all drawables, but FINE.");
                    }
          System.gc();
                }

      private void unbindDrawables(View view) {
          if (view.getBackground() != null) {
              view.getBackground().setCallback(null);
                    }
          if (view instanceof ViewGroup) {
                        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                  unbindDrawables(((ViewGroup) view).getChildAt(i));
                        }
              ((ViewGroup) view).removeAllViews();
                    }
                }

      private void handleDelete() {
                    DraftFragment nextFragment;
          boolean noAnimationDelete = false;
          WeakReference<DraftFragment> currentFragmentRef = this.mDraftFragments.get(this.mCurrentPage);
          if (currentFragmentRef != null) {
              DraftFragment currentFragment = currentFragmentRef.get();
              Animation fadeOut = AnimationUtils.loadAnimation(this, C0257R.anim.quick_fade_out);
              int offset = getResources().getDimensionPixelOffset(C0257R.dimen.draft_overlay_offset);
              int margin = TypedValue.complexToDimensionPixelOffset(6, getResources().getDisplayMetrics());
              if (this.mCurrentPage > 0) {
                  DraftFragment prevFragment = this.mDraftFragments.get(this.mCurrentPage - 1).get();
                  if (prevFragment != null && currentFragment != null) {
                      View prevView = prevFragment.getThumbnailView();
                      int[] previewLocation = new int[2];
                      prevView.getLocationOnScreen(previewLocation);
                      SimpleAnimationListener l = new C013514(prevView, previewLocation, offset, margin, prevFragment);
                      fadeOut.setAnimationListener(l);
                      View view = prevFragment.getView();
                      if (view != null) {
                          view.setVisibility(4);
                                }
                      View view2 = currentFragment.getView();
                      if (view2 != null && !this.mDeleteWasDrag) {
                          view2.startAnimation(fadeOut);
                                } else if (view2 != null) {
                          l.onAnimationEnd(fadeOut);
                                }
                            } else {
                      noAnimationDelete = true;
                            }
                        } else if (this.mCurrentPage == 0) {
                  if (this.mSessions.size() == 1) {
                      deleteCurrentDraft();
                      hideDrafts(true);
                      swapFolder("Delete", null);
                            } else {
                      WeakReference<DraftFragment> nextFragmentRef = this.mDraftFragments.get(this.mCurrentPage + 1);
                      if (nextFragmentRef != null && (nextFragment = nextFragmentRef.get()) != null && currentFragment != null) {
                          View nextView = nextFragment.getThumbnailView();
                          int[] nextLocation = new int[2];
                          nextView.getLocationOnScreen(nextLocation);
                                    try {
                              FloatingViewUtils.buildFloatingViewFor(this, nextView, this.mPreviewOverlay, nextView.getWidth(), nextLocation[0], nextLocation[1] - offset);
                                    } catch (Exception e) {
                              CrashUtil.logException(e);
                                    }
                          this.mPreviewOverlay.setVisibility(0);
                          MoveResizeAnimator animator = new MoveResizeAnimator(3, this.mPreviewOverlay, this.mPreviewOverlay, ((int) (this.mScreenSize.x * (1.0f - this.mPreviewRatio))) - (margin * 4), nextLocation[1] - offset, 1.0d, 1.0d, 300, this, nextFragment.getView());
                          fadeOut.setAnimationListener(new C013615(animator));
                          View view3 = nextFragment.getView();
                          if (view3 != null) {
                              view3.setVisibility(4);
                                    }
                          View view4 = currentFragment.getView();
                          if (view4 != null && !this.mDeleteWasDrag) {
                              view4.startAnimation(fadeOut);
                                    } else {
                              animator.start();
                                    }
                                } else {
                          noAnimationDelete = true;
                                }
                            }
                        }
                    } else {
              noAnimationDelete = true;
                    }
          if (noAnimationDelete) {
              deleteCurrentDraft();
                    }
                }

                
      class C013514 extends SimpleAnimationListener {
                    final  int val$margin;
                    final  int val$offset;
                    final  DraftFragment val$prevFragment;
                    final  View val$prevView;
                    final  int[] val$previewLocation;

          C013514(View view, int[] iArr, int i, int i2, DraftFragment draftFragment) {
                        this.val$prevView = view;
                        this.val$previewLocation = iArr;
                        this.val$offset = i;
                        this.val$margin = i2;
                        this.val$prevFragment = draftFragment;
                    }

                    @Override 
          public void onAnimationEnd(Animation animation) {
              FloatingViewUtils.buildFloatingViewFor(AbstractRecordingActivity.this, this.val$prevView, AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this), this.val$prevView.getWidth(), this.val$previewLocation[0], this.val$previewLocation[1] - this.val$offset);
              AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this).setVisibility(0);
              new MoveResizeAnimator(3, AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this), AbstractRecordingActivity.access$3600(AbstractRecordingActivity.this), ((int) (AbstractRecordingActivity.access$3500(AbstractRecordingActivity.this).x * (1.0f - AbstractRecordingActivity.this.mPreviewRatio))) - (this.val$margin * 5), this.val$previewLocation[1] - this.val$offset, 1.0d, 1.0d, 300, AbstractRecordingActivity.this, this.val$prevFragment.getView()).start();
                    }
                }

                
      class C013615 extends SimpleAnimationListener {
                    final  MoveResizeAnimator val$animator;

          C013615(MoveResizeAnimator moveResizeAnimator) {
                        this.val$animator = moveResizeAnimator;
                    }

                    @Override 
          public void onAnimationEnd(Animation animation) {
              this.val$animator.start();
                    }
                }

      public void cameraPreviewToRecorder(View iconView, View previewIcon) {
          if (!isResuming()) {
              this.mIsGoingToRecord = true;
              float fullSize = this.mScreenSize.x;
              float ratio = fullSize / iconView.getLayoutParams().width;
              int top = getResources().getDimensionPixelOffset(C0257R.dimen.progress_view_height);
              int topOffset = getResources().getDimensionPixelOffset(C0257R.dimen.draft_overlay_offset);
              int[] location = new int[2];
              iconView.getLocationInWindow(location);
              previewIcon.setVisibility(8);
              FloatingViewUtils.buildFloatingViewFor(this, iconView, this.mPreviewOverlay, iconView.getWidth(), location[0], location[1] - topOffset);
              this.mPreviewOverlay.setVisibility(0);
              new MoveResizeAnimator(4, this.mPreviewOverlay, this.mPreviewOverlay, 0, top, ratio, ratio, 300, this, iconView).start();
                    }
                }

      private boolean isResuming() {
          if (this.mCurrentFragment instanceof RecordingFragment) {
                        return this.mIsGoingToRecord || ((RecordingFragment) this.mCurrentFragment).isResuming();
                    }
          return this.mIsGoingToRecord;
                }

      protected RecordingPreviewFragment createRecordingPreviewFragmentSw(RecordingFile finalFile, String thumbNailPath) {
          return RecordingPreviewFragment.newInstance(finalFile.getVideoPath(), this.mUploadFile, thumbNailPath, this.mIsMessaging, this.mConversationRowId, this.mDirectUserId, isFromSony(), this.mColor, this.mRecipientUsername, this.mAmFollowingRecipient);
                }

      protected RecordingPreviewFragment createRecordingPreviewFragmentHw(RecordingFile finalFile, String thumbNailPath) {
          return RecordingPreviewFragment.newInstance(finalFile.getVideoPath(), null, thumbNailPath, this.mIsMessaging, this.mConversationRowId, this.mDirectUserId, isFromSony(), this.mColor, this.mRecipientUsername, this.mAmFollowingRecipient);
                }

      public ArrayList<RecorderPlugin<?, VineRecorder>> getPluginList() {
          ArrayList<RecorderPlugin<?, VineRecorder>> list = new ArrayList<>();
          if (this.mIsMessaging) {
              list.add(new CameraSwitcherPlugin());
              list.add(new GridPlugin());
              list.add(new FocusPlugin());
              list.add(new GhostPlugin());
                    } else {
              list.add(new SecretModePlugin());
              list.add(new IndividualEditPlugin());
              list.add(new ImportPlugin());
              list.add(new DeleteLastSegmentPlugin());
              list.add(MoreToolPlugin.newInstance());
              list.add(new CameraSwitcherPlugin());
              list.add(new DraftPlugin());
              list.add(new ImportOnboardPlugin());
              if (RecordConfigUtils.RecordConfig.SHOULD_SHOW_ZOOM_SLIDER) {
                  list.add(new ZoomPlugin());
                        }
              if (SLog.sLogsOn) {
                  list.add(new TimeLapsePlugin());
                        }
                    }
          return list;
                }

      public boolean isFromSony() {
          return false;
                }

      protected RecordingFragment createRecordingFragment() {
          return new RecordingFragment();
                }
            }
