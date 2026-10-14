            package co.vine.android.recorder;

            import android.annotation.SuppressLint;
            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.graphics.Bitmap;
            import android.graphics.Point;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.media.MediaActionSound;
            import android.os.AsyncTask;
            import android.os.Build;
            import android.os.Handler;
            import android.support.annotation.Nullable;
            import android.view.KeyEvent;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewPropertyAnimator;
            import android.view.WindowManager;
            import android.widget.ImageView;
            import android.widget.RelativeLayout;
            import co.vine.android.VineLoggingException;
            import co.vine.android.dragsort.DragSortWidget;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.player.StaticSizeVideoView;
            import co.vine.android.player.VideoViewInterface;
            import co.vine.android.plugin.BaseRecorderPluginManager;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioArrays;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.service.VineUploadService;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.views.OnMeasureNotifyingRelativeLayout;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import java.io.IOException;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(14)
/* 52 */    public class VineRecorder extends RegularVineRecorder implements View.OnTouchListener, DragSortWidget.SelectionChangedListener, VideoViewInterface.OnPreparedListener, DragSortWidget.CurrentlyPlayingProvider, View.OnKeyListener {
                private static final String JAPANESE_LANG = "ja";
                private static final int JAPANESE_MCC = 440;
                private static final long REMOVE_THUMBNAIL_MILLIS = 100;
                public static final long TRANSITION_DURATION = 250;
                public static final int TRASH_INVISIBLE = 2;
                private static final int TRASH_VISIBLE = 0;
                public boolean detectedInvalidSession;
                private final Runnable mAnimateEditModeControlsInRunnable;
                private final Runnable mChangeProgressOverlayVisibilityRunnable;
                private DragSortWidget mDragSortWidget;
                private final int mDragSortWidgetId;
                private boolean mEditing;
                private SegmentEditorAdapter mEditorAdapter;
                private int mEditorButtonsHeight;
                private EditorDoneAsyncTask mEditorDoneAsyncTask;
                private View mFinishButton;
                private final int mFinishButtonId;
                private final View.OnClickListener mFinishClicker;
                private View mFinishLoadingOverlay;
                private final int mFinishLoadingOverlayId;
                private boolean mHasPreviewedAlready;
                private final boolean mInitiallyStartedWithEditMode;
                private final boolean mIsMessaging;
                private RecordSegment mLastPlayingSegment;
                private int mLastSelectedPosition;
                private MediaActionSound mMediaActionSound;

                @Nullable
                private View mPlayButton;
                private View mPlayButtonContainer;
                private final int mPlayButtonContainerId;
                private PlayButtonOnClickListener mPlayButtonOnClickListener;
                private View mPlayRefreshButton;
                private boolean mPlaySound;
                private View mPreviewLoadingOverlay;
                private final int mPreviewLoadingOverlayId;
                private View mProgressOverlay;
                private final int mProgressOverlayId;
                private View mRecordingOptions;
                private final int mRecordingOptionsRowId;
                private RefreshPreviewTask mRefreshPreviewTask;
                private boolean mReturnToPreview;
                private SegmentChangeDetector mSegmentChangeDetector;
                private Thread mSegmentChangeThread;
                private final SegmentHighlightRunnable mSegmentHighlightRunnable;
                private int mShortAnimTime;
                private boolean mStartWithEditMode;
                private View mThumbnailList;
                private ImageView mThumbnailOverlay;
                private final int mThumbnailOverlayId;
                private int mThumbnailPadding;
                private RecordSegment mThumbnailSegment;
                private Runnable mVideoPreviewTrimPositionChecker;

                @Nullable
                private SdkVideoView mVideoTexture;
                private long mVideoTextureSeekEndUs;
                private long mVideoTextureSeekStartUs;
                private final ViewGroup mVideoViewContainer;
                private RelativeLayout.LayoutParams mVideoViewParams;
                private final Runnable onCameraReadyRunnable;

/* 53 */        static /* synthetic */ boolean access$000(VineRecorder x0) {
/* 54 */            return x0.mInitiallyStartedWithEditMode;
                }

/* 53 */        static /* synthetic */ RecordSegment access$100(VineRecorder x0) {
/* 54 */            return x0.mLastPlayingSegment;
                }

/* 53 */        static /* synthetic */ void access$1000(VineRecorder x0) {
/* 54 */            x0.adjustEditBoundaries();
                }

/* 53 */        static /* synthetic */ RecordSegment access$102(VineRecorder x0, RecordSegment x1) {
/* 54 */            x0.mLastPlayingSegment = x1;
                    return x1;
                }

/* 53 */        static /* synthetic */ DragSortWidget access$1100(VineRecorder x0) {
/* 54 */            return x0.mDragSortWidget;
                }

/* 53 */        static /* synthetic */ void access$1200(VineRecorder x0, String x1, long x2, long x3) {
/* 54 */            x0.preparePlayer(x1, x2, x3);
                }

/* 53 */        static /* synthetic */ RefreshPreviewTask access$1300(VineRecorder x0) {
/* 54 */            return x0.mRefreshPreviewTask;
                }

/* 53 */        static /* synthetic */ RefreshPreviewTask access$1302(VineRecorder x0, RefreshPreviewTask x1) {
/* 54 */            x0.mRefreshPreviewTask = x1;
                    return x1;
                }

/* 53 */        static /* synthetic */ void access$1400(VineRecorder x0, boolean x1) {
/* 54 */            x0.animatePreviewSpinner(x1);
                }

/* 53 */        static /* synthetic */ long access$1500(VineRecorder x0) {
/* 54 */            return x0.mVideoTextureSeekEndUs;
                }

/* 53 */        static /* synthetic */ long access$1600(VineRecorder x0) {
/* 54 */            return x0.mVideoTextureSeekStartUs;
                }

/* 53 */        static /* synthetic */ PlayButtonOnClickListener access$1700(VineRecorder x0) {
/* 54 */            return x0.mPlayButtonOnClickListener;
                }

/* 53 */        static /* synthetic */ View access$1800(VineRecorder x0) {
/* 54 */            return x0.mFinishLoadingOverlay;
                }

/* 53 */        static /* synthetic */ void access$1900(VineRecorder x0, View x1, boolean x2) {
/* 54 */            x0.animateSpinner(x1, x2);
                }

/* 53 */        static /* synthetic */ View access$200(VineRecorder x0) {
/* 54 */            return x0.mRecordingOptions;
                }

/* 53 */        static /* synthetic */ boolean access$2000(VineRecorder x0) {
/* 54 */            return x0.mReturnToPreview;
                }

/* 53 */        static /* synthetic */ int access$2100(VineRecorder x0) {
/* 54 */            return x0.getDurationFromSegments();
                }

/* 53 */        static /* synthetic */ boolean access$2200(VineRecorder x0) {
/* 54 */            return x0.mEditing;
                }

/* 53 */        static /* synthetic */ void access$2300(VineRecorder x0, boolean x1) {
/* 54 */            x0.returnToPreview(x1);
                }

/* 53 */        static /* synthetic */ void access$2400(VineRecorder x0, boolean x1) {
/* 54 */            x0.animateEditModeControlsOut(x1);
                }

/* 53 */        static /* synthetic */ Runnable access$2500(VineRecorder x0) {
/* 54 */            return x0.mVideoPreviewTrimPositionChecker;
                }

/* 53 */        static /* synthetic */ EditorDoneAsyncTask access$2602(VineRecorder x0, EditorDoneAsyncTask x1) {
/* 54 */            x0.mEditorDoneAsyncTask = x1;
                    return x1;
                }

/* 53 */        static /* synthetic */ View access$2700(VineRecorder x0) {
/* 54 */            return x0.mPlayButtonContainer;
                }

/* 53 */        static /* synthetic */ boolean access$2800(VineRecorder x0) {
/* 54 */            return x0.mIsMessaging;
                }

/* 53 */        static /* synthetic */ void access$2900(VineRecorder x0) {
/* 54 */            x0.adjustStaticBoundaries();
                }

/* 53 */        static /* synthetic */ int access$300(VineRecorder x0) {
/* 54 */            return x0.mEditorButtonsHeight;
                }

/* 53 */        static /* synthetic */ int access$3000(VineRecorder x0) {
/* 54 */            return x0.mLastSelectedPosition;
                }

/* 53 */        static /* synthetic */ int access$3002(VineRecorder x0, int x1) {
/* 54 */            x0.mLastSelectedPosition = x1;
                    return x1;
                }

/* 53 */        static /* synthetic */ View access$3100(VineRecorder x0) {
/* 54 */            return x0.mProgressOverlay;
                }

/* 53 */        static /* synthetic */ void access$400(VineRecorder x0) {
/* 54 */            x0.ensureLowProfileStatusBar();
                }

/* 53 */        static /* synthetic */ View access$500(VineRecorder x0) {
/* 54 */            return x0.mThumbnailList;
                }

/* 53 */        static /* synthetic */ SdkVideoView access$600(VineRecorder x0) {
/* 54 */            return x0.mVideoTexture;
                }

/* 53 */        static /* synthetic */ RelativeLayout.LayoutParams access$700(VineRecorder x0) {
/* 54 */            return x0.mVideoViewParams;
                }

/* 53 */        static /* synthetic */ void access$800(VineRecorder x0) {
/* 54 */            x0.refreshFullPreview();
                }

/* 53 */        static /* synthetic */ SegmentEditorAdapter access$900(VineRecorder x0) {
/* 54 */            return x0.mEditorAdapter;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$1 */
/* 94 */        class RunnableC04901 implements Runnable {
/* 95 */            RunnableC04901() {
                    }

                    @Override // java.lang.Runnable
/* 98 */            public void run() {
/* 99 */                VineRecorder.this.mPluginManager.onCameraReady(VineRecorder.this.mFrontFacing, VineRecorder.this.setAutoFocusing(true));
                    }
                }

/* 136 */       public VineRecorder(RecordSessionVersion version, BaseRecorderPluginManager manager, boolean startWithEditMode, Point screenSize, boolean returnToPreview, boolean hasPreviewedAlready, RecordingFile file, Activity activity, ViewGroup videoViewContainer, int recordingOptionsRowId, int dragSortWidgetId, int progressViewResourceId, int cameraViewResourceId, int finishButtonId, int topMaskId, int bottomMaskId, int lastSegmentString, int openCameraString, int playButtonContainerId, int thumbnailOverlayId, int rootLayoutId, int editorPadding, int editorButtonsHeight, int finishLoadingOverlayId, int previewLoadingOverlayId, int progressOverlay, Drawable progressDrawable, RegularVineRecorder.DeviceIssueStringGetter deviceNotSupportedString, boolean startWithFrontFacingCamera, boolean isMessaging, MediaActionSound mediaActionSound, CharSequence importingString, CharSequence... messages) {
/* 137 */           super(isMessaging ? 66 : 1000, manager, screenSize, activity, progressViewResourceId, cameraViewResourceId, topMaskId, bottomMaskId, openCameraString, rootLayoutId, progressDrawable, deviceNotSupportedString, startWithFrontFacingCamera, version, importingString, messages);
/* 93 */            this.mLastPlayingSegment = new RecordSegment(0L, 30);
/* 96 */            this.onCameraReadyRunnable = new RunnableC04901();
/* 120 */           this.mPlaySound = false;
/* 205 */           this.mFinishClicker = new ViewOnClickListenerC05053();
/* 324 */           this.mAnimateEditModeControlsInRunnable = new RunnableC05075();
/* 442 */           this.mSegmentHighlightRunnable = new SegmentHighlightRunnable();
/* 1224 */          this.mVideoPreviewTrimPositionChecker = new RunnableC049312();
/* 1787 */          this.mChangeProgressOverlayVisibilityRunnable = new RunnableC050422();
/* 141 */           this.mReturnToPreview = returnToPreview;
/* 142 */           this.mHasPreviewedAlready = hasPreviewedAlready;
/* 143 */           this.mStartWithEditMode = startWithEditMode;
/* 144 */           this.mInitiallyStartedWithEditMode = startWithEditMode;
/* 145 */           this.mEditorButtonsHeight = editorButtonsHeight;
/* 147 */           this.mVideoViewContainer = videoViewContainer;
/* 149 */           this.mShortAnimTime = this.mActivity.getResources().getInteger(android.R.integer.config_shortAnimTime);
/* 150 */           this.mThumbnailPadding = editorPadding;
/* 151 */           this.mFinishButtonId = finishButtonId;
/* 152 */           this.mFinishLastSegmentString = activity.getText(lastSegmentString);
/* 153 */           this.mThumbnailOverlayId = thumbnailOverlayId;
/* 154 */           this.mFinishLoadingOverlayId = finishLoadingOverlayId;
/* 155 */           this.mPlayButtonContainerId = playButtonContainerId;
/* 156 */           this.mPreviewLoadingOverlayId = previewLoadingOverlayId;
/* 157 */           this.mRecordingOptionsRowId = recordingOptionsRowId;
/* 158 */           this.mDragSortWidgetId = dragSortWidgetId;
/* 159 */           this.mProgressOverlayId = progressOverlay;
/* 160 */           this.mMediaActionSound = mediaActionSound;
/* 162 */           Locale locale = Locale.getDefault();
/* 166 */           this.mPlaySound = (locale != null && JAPANESE_LANG.equals(locale.getLanguage())) || this.mActivity.getResources().getConfiguration().mcc == JAPANESE_MCC;
/* 168 */           swapSession("Init", file);
/* 169 */           this.mIsMessaging = isMessaging;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$2 */
/* 172 */       class C05012 extends BasicVineRecorder.FinishProcessRunnable {
/* 173 */           C05012() {
                        super();
                    }

                    @Override // co.vine.android.recorder.BasicVineRecorder.FinishProcessRunnable
/* 177 */           public boolean doNotDeleteSession(boolean isFinishing) {
                        return isFinishing || VineRecorder.access$000(VineRecorder.this);
                    }

                    @Override // co.vine.android.recorder.BasicVineRecorder.FinishProcessRunnable
/* 182 */           public boolean isCompleteSession(boolean wasRecordingStarted) {
                        return wasRecordingStarted || VineRecorder.access$000(VineRecorder.this);
                    }

                    @Override // co.vine.android.recorder.BasicVineRecorder.FinishProcessRunnable
/* 187 */           protected void onPreCompleteSession(boolean wasRecordingStarted) {
/* 188 */               if (wasRecordingStarted) {
/* 189 */                   VineRecorder.access$102(VineRecorder.this, new RecordSegment(VineRecorder.this.mCurrentDurationMs, 30));
                        }
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 173 */       protected BasicVineRecorder.FinishProcessRunnable getFinishProcessRunnable() {
/* 174 */           return new C05012();
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 195 */       public RecordingFile swapSession(String tag, RecordingFile file) {
/* 197 */           if (file == null) {
/* 196 */               return null;
                    }
/* 198 */           RecordingFile ret = super.swapSession(tag, file);
/* 199 */           this.mPluginManager.onSessionSwapped();
/* 200 */           this.mPluginManager.onSegmentDataChanged(this.mEditedSegments);
/* 202 */           return ret;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$3 */
/* 203 */       class ViewOnClickListenerC05053 implements View.OnClickListener {
/* 204 */           ViewOnClickListenerC05053() {
                    }

                    @Override // android.view.View.OnClickListener
/* 207 */           public void onClick(View v) {
/* 208 */               VineRecorder.this.setHasPreviewedAlreadyIfNeeded();
/* 209 */               VineRecorder.this.finish("finishClicker");
                    }
                }

/* 213 */       public void switchFlash() {
/* 214 */           this.mVideoController.switchFlash();
                }

/* 217 */       public void modifyZoom(boolean zoomIn) {
/* 218 */           if (this.mVideoController.isRecordingStarted()) {
/* 219 */               this.mVideoController.modifyZoom(zoomIn);
                    }
                }

/* 223 */       public void stopZoom(Runnable updateRunnable) {
/* 224 */           if (this.mVideoController.isRecordingStarted()) {
/* 225 */               this.mVideoController.stopZoom();
/* 226 */               this.mHandler.removeCallbacks(updateRunnable);
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 230 */       public boolean release() {
/* 231 */           this.mLastPlayingSegment = null;
/* 232 */           return super.release();
                }

                @Nullable
/* 236 */       public ViewGroup getVideoContainer() {
/* 237 */           if (this.mVideoTexture != null) {
/* 238 */               return (ViewGroup) this.mVideoTexture.getParent();
                    }
                    return null;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$4 */
/* 243 */       class RunnableC05064 implements Runnable {
/* 244 */           RunnableC05064() {
                    }

                    @Override // java.lang.Runnable
/* 247 */           public void run() {
/* 248 */               if (VineRecorder.access$200(VineRecorder.this) != null) {
/* 249 */                   VineRecorder.access$200(VineRecorder.this).setAlpha(0.0f);
/* 250 */                   VineRecorder.access$200(VineRecorder.this).setVisibility(0);
/* 251 */                   VineRecorder.access$200(VineRecorder.this).animate().alpha(1.0f).setDuration(250L).start();
                        }
/* 253 */               if (VineRecorder.this.mTopMaskView != null) {
/* 254 */                   VineRecorder.this.mTopMaskView.setAlpha(0.0f);
/* 255 */                   VineRecorder.this.mTopMaskView.setVisibility(0);
/* 256 */                   VineRecorder.this.mTopMaskView.animate().alpha(1.0f).setDuration(250L).start();
                        }
/* 258 */               if (VineRecorder.this.mProgressView != null) {
/* 259 */                   VineRecorder.this.mProgressView.setTranslationY(VineRecorder.access$300(VineRecorder.this));
/* 260 */                   VineRecorder.this.mProgressView.setScaleY(0.0f);
/* 261 */                   VineRecorder.this.mProgressView.setVisibility(0);
/* 262 */                   VineRecorder.this.mProgressView.animate().scaleY(1.0f).translationY(0.0f).setDuration(250L).start();
                        }
                    }
                }

/* 244 */       private void animateCaptureControlsIn() {
/* 245 */           this.mHandler.postDelayed(new RunnableC05064(), 250L);
/* 267 */           if (this.mCameraView != null) {
/* 268 */               this.mCameraView.setVisibility(0);
                    }
                }

/* 272 */       private void animateCaptureControlsOut() {
                    ViewPropertyAnimator am;
                    ViewPropertyAnimator am2;
                    ViewPropertyAnimator am3;
/* 273 */           View v = this.mProgressView;
/* 274 */           if (v != null && (am3 = v.animate()) != null) {
/* 277 */               am3.scaleY(0.0f).translationY(this.mEditorButtonsHeight).setDuration(250L).setListener(new ViewGoneAnimationListener(v)).start();
                    }
/* 281 */           View v2 = this.mTopMaskView;
/* 282 */           if (v2 != null && (am2 = v2.animate()) != null) {
/* 285 */               am2.alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(v2)).start();
                    }
/* 289 */           View v3 = this.mRecordingOptions;
/* 290 */           if (v3 != null && (am = v3.animate()) != null) {
/* 293 */               am.alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(v3)).start();
                    }
/* 297 */           if (this.mCameraView != null) {
/* 298 */               this.mCameraView.setVisibility(4);
                    }
                }

/* 302 */       private void hideCaptureControls() {
/* 303 */           if (this.mProgressView != null) {
/* 304 */               this.mProgressView.setVisibility(4);
                    }
/* 306 */           if (this.mCameraView != null) {
/* 307 */               this.mCameraView.setVisibility(4);
                    }
/* 309 */           if (this.mTopMaskView != null) {
/* 310 */               this.mTopMaskView.setVisibility(4);
                    }
/* 312 */           if (this.mRecordingOptions != null) {
/* 313 */               this.mRecordingOptions.setVisibility(4);
                    }
                }

/* 317 */       public void higlightProgressView(float start, float end, int color) {
/* 318 */           if (this.mProgressView != null) {
/* 319 */               this.mProgressView.setSelectedColor(color);
/* 320 */               this.mProgressView.setSelectedSection(start, end);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$5 */
/* 322 */       class RunnableC05075 implements Runnable {
/* 323 */           RunnableC05075() {
                    }

                    @Override // java.lang.Runnable
/* 326 */           public void run() {
/* 327 */               VineRecorder.this.mPluginManager.onAnimateEditModeControlsInUI();
/* 328 */               VineRecorder.access$400(VineRecorder.this);
/* 329 */               if (VineRecorder.access$500(VineRecorder.this) != null) {
/* 330 */                   int thumbnailListHeight = VineRecorder.access$500(VineRecorder.this).getHeight();
/* 331 */                   VineRecorder.access$500(VineRecorder.this).setTranslationY((-thumbnailListHeight) / 2);
/* 332 */                   VineRecorder.access$500(VineRecorder.this).setScaleY(0.0f);
/* 333 */                   VineRecorder.access$500(VineRecorder.this).setVisibility(0);
/* 334 */                   VineRecorder.access$500(VineRecorder.this).animate().scaleY(1.0f).translationY(0.0f).setDuration(250L).start();
                        }
                    }
                }

/* 340 */       private void ensureLowProfileStatusBar() {
/* 341 */           Activity activity = getActivity();
/* 342 */           if (activity != null) {
/* 343 */               ViewUtil.setLowProfileSystemUiMode(activity);
                    }
                }

/* 347 */       private void animateEditModeControlsIn() {
/* 348 */           this.mHandler.postDelayed(this.mAnimateEditModeControlsInRunnable, 250L);
/* 350 */           if (this.mPreviewLoadingOverlay != null) {
/* 352 */               this.mPreviewLoadingOverlay.setVisibility(4);
/* 353 */               this.mPreviewLoadingOverlay.setAlpha(0.0f);
                    }
/* 356 */           if (this.mPlayButtonContainer != null) {
/* 357 */               this.mPlayButtonContainer.setVisibility(4);
/* 358 */               this.mPlayButtonContainer.setAlpha(0.0f);
                    }
/* 361 */           if (this.mThumbnailOverlay != null) {
/* 362 */               this.mThumbnailOverlay.setVisibility(4);
                    }
                }

/* 366 */       private void animateEditModeControlsOut(boolean hideVideo) {
/* 367 */           this.mHandler.removeCallbacks(this.mAnimateEditModeControlsInRunnable);
/* 368 */           if (this.mEditorAdapter != null && this.mThumbnailOverlay != null && this.mPlayButtonContainer != null && this.mPreviewLoadingOverlay != null && this.mThumbnailList != null && this.mPlayButton != null) {
/* 370 */               this.mPluginManager.onAnimateEditModeControlsOutUI();
/* 371 */               this.mThumbnailOverlay.animate().alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(this.mThumbnailOverlay)).start();
/* 373 */               this.mPlayButtonContainer.animate().alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(this.mPlayButtonContainer)).start();
/* 375 */               this.mPreviewLoadingOverlay.animate().alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(this.mPreviewLoadingOverlay)).start();
/* 377 */               this.mThumbnailList.animate().scaleY(0.0f).translationY((-this.mThumbnailList.getHeight()) / 2).setDuration(250L).setListener(new ViewGoneAnimationListener(this.mThumbnailList)).start();
/* 381 */               this.mPlayButtonContainer.animate().alpha(0.0f).setDuration(250L).start();
/* 382 */               this.mDragSortWidget.setSelection(-1);
                    }
/* 384 */           if (hideVideo && this.mVideoTexture != null) {
/* 385 */               this.mVideoTexture.setVisibility(4);
                    }
                }

/* 389 */       public void startEditMode(boolean wasRecordingAlready) {
/* 390 */           adjustEditBoundaries();
/* 391 */           if (!this.mReturnToPreview) {
/* 392 */               animateCaptureControlsOut();
                    } else {
/* 394 */               hideCaptureControls();
                    }
/* 396 */           animateEditModeControlsIn();
/* 398 */           this.mLastSelectedPosition = -1;
/* 400 */           this.mLastPlayingSegment = null;
/* 402 */           if (!wasRecordingAlready) {
/* 403 */               this.mEditorAdapter = new SegmentEditorAdapter(this.mEditedSegments, this.mActivity, this.mDragSortWidget, this.density);
                    } else {
/* 406 */               this.mEditorAdapter = new SegmentEditorAdapter(this.mEditorAdapter);
                    }
/* 409 */           if (this.mDragSortWidget != null) {
/* 410 */               this.mDragSortWidget.setAdapter(this.mEditorAdapter);
/* 412 */               this.mEditorAdapter.notifyDataSetChanged();
/* 414 */               this.mDragSortWidget.setSelection(-1);
/* 415 */               this.mDragSortWidget.setFocused(0);
                    }
/* 418 */           this.mPluginManager.onStartEditMode();
/* 420 */           refreshVideoView(null, true, true, false, "startEditMode");
                }

/* 421 */       public class SegmentHighlightRunnable implements Runnable {
                    public WeakReference<RecordSegment> target;

/* 422 */           public SegmentHighlightRunnable() {
                    }

                    @Override // java.lang.Runnable
/* 428 */           public void run() {
/* 429 */               if (this.target != null) {
/* 430 */                   RecordSegment t = this.target.get();
/* 431 */                   if (t != null) {
/* 432 */                       VineRecorder.this.playPreview(t, true, false, "segmentHighlight");
/* 439 */                       return;
                            }
/* 439 */                   return;
                        }
/* 435 */               if (!VineRecorder.this.mVideoController.isPreviewing()) {
/* 436 */                   VineRecorder.this.mVideoController.openDefaultCamera(VineRecorder.this.mFrontFacing, true);
                        }
                    }
                }

/* 444 */       public boolean highlightSegment(int index, int color) {
                    RecordSession session;
/* 445 */           if (!isCurrentlyRecording() && (session = getSesion()) != null) {
/* 448 */               int duration = 0;
/* 449 */               ArrayList<RecordSegment> segments = this.mEditedSegments;
/* 450 */               RecordSegment target = segments.get(index);
/* 451 */               long start = 0;
/* 452 */               long end = 0;
                        for (RecordSegment segment : segments) {
/* 454 */                   if (!segment.removed) {
/* 455 */                       if (target == segment) {
                                    start = duration;
/* 457 */                           end = start + ((long) segment.getDurationMs());
                                }
/* 459 */                       duration += segment.getDurationMs();
                            }
                        }
/* 462 */               SLog.m467i("Segment {} highlighted: {} {} ", Integer.valueOf(index), Long.valueOf(start), Long.valueOf(end));
/* 463 */               float max = session.getConfig().maxDuration;
/* 465 */               if (this.mProgressView != null) {
/* 466 */                   if (duration > 0 && Math.floor(end / ((long) duration)) == 1.0d && end < this.mProgressView.getProgressRatio()) {
/* 467 */                       higlightProgressView(start / max, this.mProgressView.getProgressRatio(), color);
                            } else {
/* 469 */                       higlightProgressView(start / max, end / max, color);
                            }
                        }
/* 472 */               this.mHandler.removeCallbacks(this.mSegmentHighlightRunnable);
/* 473 */               this.mSegmentHighlightRunnable.target = new WeakReference<>(target);
/* 474 */               this.mHandler.postDelayed(this.mSegmentHighlightRunnable, 500L);
/* 475 */               return true;
                    }
/* 478 */           return false;
                }

/* 481 */       public void unhighlightSegments() {
/* 482 */           if (this.mProgressView != null) {
/* 483 */               this.mProgressView.setSelectedSection(0.0f, 0.0f);
                    }
/* 485 */           if (this.mCameraView != null) {
/* 486 */               this.mCameraView.setVisibility(0);
                    }
/* 488 */           pausePreview(false);
/* 489 */           if (this.mVideoTexture != null) {
/* 490 */               this.mVideoTexture.setVisibility(4);
                    }
/* 492 */           this.mHandler.removeCallbacks(this.mSegmentHighlightRunnable);
/* 493 */           this.mSegmentHighlightRunnable.target = null;
/* 494 */           this.mHandler.postDelayed(this.mSegmentHighlightRunnable, 250L);
                }

/* 497 */       public void deleteLastSegment() {
/* 498 */           if (this.mEditedSegments.size() > 0) {
/* 499 */               stopProgressTimer();
/* 500 */               int lastIndex = 0;
                        for (int i = 0; i < this.mEditedSegments.size(); i++) {
/* 502 */                   if (!this.mEditedSegments.get(i).removed) {
/* 503 */                       lastIndex = i;
                            }
                        }
/* 506 */               this.mEditedSegments.get(lastIndex).removed = true;
/* 507 */               RecordSession session = this.mCurrentRecordingFile.getSession();
/* 508 */               while (this.mVideoController.wasJustGrabbingDataForSegment()) {
/* 509 */                   SLog.m464i("Waiting for audio recorder to settle down.");
                            try {
/* 511 */                       Thread.sleep(RecordConfigUtils.AUDIO_WAIT_THRESHOLD_NS / 2);
                            } catch (InterruptedException e) {
                            }
                        }
                        try {
/* 517 */                   this.mToRemove.addAll(RecordSegment.applyEditedChanges(this.mCurrentRecordingFile.folder, session, this.mEditedSegments));
/* 521 */                   swapTimestampsFromSegments(session, this.mEditedSegments);
/* 522 */                   this.mCurrentRecordingFile.isDirty = this.mCurrentRecordingFile.isSavedSession || this.mEditedSegments.size() > 0;
/* 523 */                   startProgressTimer();
/* 524 */                   SLog.m465i("Segment {} deleted.", Integer.valueOf(lastIndex));
                        } catch (IOException e2) {
/* 519 */                   throw new RecordSegmentIOException(e2);
                        }
                    }
                }

/* 529 */       public void setEditMode(View view, boolean on, boolean discard) {
                    int size;
                    Object[] objArr = new Object[2];
/* 530 */           objArr[0] = on ? "on" : "off";
                    objArr[1] = discard ? VineUploadService.ACTION_DISCARD : "save";
/* 530 */           CrashUtil.log("Set edit mode {} {}.", objArr);
/* 533 */           boolean hasData = this.mCurrentRecordingFile != null && this.mCurrentRecordingFile.hasData();
/* 534 */           if (!this.mPluginManager.onSetEditMode(on, hasData)) {
/* 537 */               boolean wasEditingAlready = this.mEditing;
/* 538 */               this.mEditing = on;
/* 539 */               if (hasData) {
/* 540 */                   if (this.mEditing) {
/* 541 */                       this.mStartWithEditMode = false;
/* 542 */                       this.mHandler.post(this.mVideoPreviewTrimPositionChecker);
/* 543 */                       if (canKeepRecording()) {
/* 544 */                           if (this.mVideoController != null && this.mVideoController.isRecordingStarted()) {
/* 545 */                               stop("editMode", new RunnableC05086(wasEditingAlready), true, false);
/* 593 */                               return;
                                    } else {
/* 552 */                               startEditMode(wasEditingAlready);
/* 593 */                               return;
                                    }
                                }
/* 555 */                       this.mEditing = false;
/* 593 */                       return;
                            }
/* 558 */                   this.mHandler.removeCallbacks(this.mVideoPreviewTrimPositionChecker);
/* 559 */                   releaseSegmentChangeDetector();
/* 560 */                   if (this.mRefreshPreviewTask != null) {
/* 561 */                       this.mRefreshPreviewTask.cancel(true);
                            }
/* 563 */                   if (this.mVideoTexture != null) {
/* 564 */                       this.mVideoTexture.stopPlayback();
/* 565 */                       this.mVideoTexture.setAutoPlayOnPrepared(false);
                            }
/* 567 */                   if (this.mDragSortWidget != null) {
/* 568 */                       this.mDragSortWidget.cleanUp();
                            }
/* 570 */                   if (!discard) {
/* 571 */                       if (commitChanges() && (size = this.mEditedSegments.size()) > 0 && this.mVideoController != null) {
/* 574 */                           this.mVideoController.makePreview(this.mEditedSegments.get(size - 1), true, false);
                                }
/* 577 */                       if (this.mCurrentRecordingFile != null) {
/* 578 */                           this.mCurrentRecordingFile.invalidateGhostThumbnail();
                                }
                            }
/* 581 */                   animateEditModeControlsOut(true);
/* 582 */                   animateCaptureControlsIn();
/* 583 */                   invalidateFinishButton();
/* 584 */                   setHasPreviewedAlreadyIfNeeded();
/* 585 */                   this.mReturnToPreview = false;
/* 586 */                   this.mResumeTask = new RegularVineRecorder.OnResumeTask(view, "Set EditMode.");
/* 587 */                   this.mResumeTask.execute(new Void[0]);
/* 588 */                   this.mEditorAdapter = null;
/* 593 */                   return;
                        }
/* 591 */               this.mEditing = false;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$6 */
/* 543 */       class RunnableC05086 implements Runnable {
                    final /* synthetic */ boolean val$wasEditingAlready;

/* 544 */           RunnableC05086(boolean z) {
                        this.val$wasEditingAlready = z;
                    }

                    @Override // java.lang.Runnable
/* 547 */           public void run() {
/* 548 */               VineRecorder.this.startEditMode(this.val$wasEditingAlready);
                    }
                }

/* 595 */       public void setEditMode(boolean on, boolean discard) {
/* 596 */           setEditMode(null, on, discard);
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 599 */       protected void setHasPreviewedAlreadyIfNeeded() {
/* 600 */           this.mHasPreviewedAlready = this.mCurrentDurationMs >= ((long) this.mSession.getConfig().maxDuration);
                }

/* 604 */       public void addExtraSegment(RecordSegment segment, Bitmap lastBitmap) {
/* 605 */           this.mCurrentDurationMs += (long) segment.getDurationMs();
/* 606 */           long durationUs = this.mCurrentDurationMs * 1000;
/* 607 */           this.mVideoController.setAudioTimestampUs((int) durationUs);
/* 608 */           this.mVideoController.setVideoTimeStampUs((int) durationUs);
/* 609 */           this.mVideoController.onExternalClipAdded(segment.getDurationMs() * 1000);
/* 610 */           this.mRecordingFileDuration = (int) this.mCurrentDurationMs;
/* 611 */           segment.setCameraSetting(this.mVideoController.getCameraSetting());
/* 612 */           changeProgress(this.mCurrentDurationMs);
/* 613 */           this.mSession.add(segment);
/* 614 */           this.mAddedSegments.add(segment);
/* 615 */           this.mEditedSegments.add(segment);
/* 616 */           this.mCurrentRecordingFile.isDirty = true;
/* 617 */           this.mPluginManager.onOfferLastFrame(lastBitmap, getCurrentCameraSetting());
/* 618 */           this.mPluginManager.onSegmentDataChanged(this.mEditedSegments);
/* 619 */           this.mHandler.post(new RunnableC05097());
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$7 */
/* 617 */       class RunnableC05097 implements Runnable {
/* 618 */           RunnableC05097() {
                    }

                    @Override // java.lang.Runnable
/* 621 */           public void run() {
/* 622 */               VineRecorder.this.invalidateFinishButton();
                    }
                }

/* 630 */       private boolean commitChanges() {
                    boolean lastChanged = false;
/* 631 */           SegmentEditorAdapter adapter = this.mEditorAdapter;
/* 632 */           if (adapter != null) {
/* 633 */               adapter.commitDelete();
/* 634 */               RecordSessionManager.cleanUpSegmentData(this.mCurrentRecordingFile.folder, adapter.cleanUpForDeletion());
/* 636 */               int size = this.mEditedSegments.size();
/* 637 */               RecordSegment lastSegment = null;
/* 639 */               if (size > 0) {
/* 640 */                   RecordSegment lastSegment2 = this.mEditedSegments.get(size - 1);
                            lastSegment = lastSegment2;
                        }
/* 643 */               int modifiedSize = adapter.getData().size();
/* 647 */               if (modifiedSize > 0) {
/* 648 */                   if (lastSegment != adapter.getData().get(modifiedSize - 1)) {
                                lastChanged = true;
                            }
                        } else if (lastSegment != null) {
                            lastChanged = true;
                        }
/* 653 */               this.mEditedSegments.clear();
/* 654 */               this.mEditedSegments.addAll(adapter.getData());
/* 655 */               this.mEditedSegments.addAll(adapter.getDeleted());
                        for (RecordSegment segment : adapter.getDeleted()) {
/* 657 */                   segment.removed = true;
                        }
/* 659 */               RecordSession session = this.mCurrentRecordingFile.getSession();
                        try {
/* 661 */                   this.mToRemove.addAll(RecordSegment.applyEditedChanges(this.mCurrentRecordingFile.folder, session, this.mEditedSegments));
/* 665 */                   swapTimestampsFromSegments(session, this.mEditedSegments);
/* 667 */                   this.mCurrentRecordingFile.isDirty = true;
/* 669 */                   SLog.m447d("Changes commited, last segment has changed? {}.", Boolean.valueOf(lastChanged));
                        } catch (IOException e) {
/* 663 */                   throw new RecordSegmentIOException(e);
                        }
                    }
/* 673 */           return lastChanged;
                }

/* 678 */       private void adjustEditBoundaries() {
                    RelativeLayout.LayoutParams thumbListParams;
/* 679 */           if (this.mVideoTexture != null) {
/* 680 */               this.mVideoViewParams = (RelativeLayout.LayoutParams) this.mVideoTexture.getLayoutParams();
/* 681 */               this.mVideoViewParams.width = this.mSize.x;
/* 682 */               this.mVideoViewParams.height = this.mSize.x;
                    }
/* 684 */           if (this.mPlayButtonContainer != null) {
/* 685 */               this.mPlayButtonContainer.setLayoutParams(this.mVideoViewParams);
                    }
/* 687 */           if (this.mThumbnailOverlay != null) {
/* 688 */               this.mThumbnailOverlay.setLayoutParams(this.mVideoViewParams);
                    }
/* 690 */           if (this.mPreviewLoadingOverlay != null) {
/* 691 */               this.mPreviewLoadingOverlay.setLayoutParams(this.mVideoViewParams);
                    }
/* 695 */           if (this.mThumbnailList != null) {
/* 696 */               thumbListParams = (RelativeLayout.LayoutParams) this.mThumbnailList.getLayoutParams();
/* 699 */               thumbListParams.setMargins(0, this.mEditorButtonsHeight + this.mSize.x + this.mThumbnailPadding, 0, 0);
                    } else {
/* 702 */               thumbListParams = null;
                    }
/* 705 */           if (this.mActivity != null) {
/* 706 */               this.mActivity.runOnUiThread(new RunnableC05108(thumbListParams));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$8 */
/* 704 */       class RunnableC05108 implements Runnable {
                    final /* synthetic */ RelativeLayout.LayoutParams val$thumbListParams;

/* 705 */           RunnableC05108(RelativeLayout.LayoutParams layoutParams) {
                        this.val$thumbListParams = layoutParams;
                    }

                    @Override // java.lang.Runnable
/* 708 */           public void run() {
/* 709 */               View view = VineRecorder.access$600(VineRecorder.this);
/* 710 */               if (view != null) {
/* 711 */                   view.setLayoutParams(VineRecorder.access$700(VineRecorder.this));
                        }
/* 713 */               View view2 = VineRecorder.access$500(VineRecorder.this);
/* 714 */               if (view2 != null) {
/* 715 */                   view2.setLayoutParams(this.val$thumbListParams);
                        }
                    }
                }

/* 722 */       public boolean isEditing() {
                    return this.mEditing && this.mEditorAdapter != null;
                }

/* 733 */       public void saveSession(Runnable onSaveSessionCompleteRunnable, boolean releaseCamera) {
/* 734 */           this.mCanKeepRecording = false;
/* 735 */           stop("saveSession", onSaveSessionCompleteRunnable, releaseCamera, true);
                }

                @Override // co.vine.android.dragsort.DragSortWidget.SelectionChangedListener
/* 739 */       public void onSelectionChanged(int position, boolean listViewClick) {
/* 740 */           this.mLastSelectedPosition = position;
/* 741 */           SegmentEditorAdapter editor = this.mEditorAdapter;
/* 742 */           if (editor != null) {
/* 743 */               if (position != -1 && position < editor.getCount()) {
/* 744 */                   RecordSegment toPlay = (RecordSegment) editor.getItem(position);
/* 745 */                   animateTopButtons(0, toPlay);
/* 746 */                   if (listViewClick) {
/* 747 */                       pausePreview(false);
/* 748 */                       playPreview(toPlay, true, true, "SelectionChanged");
                            } else {
/* 750 */                       pausePreview(true);
                            }
/* 752 */                   editor.commitDelete();
/* 753 */                   this.mPluginManager.onIndividualSegmentClicked(toPlay, this.mVideoTexture);
/* 771 */                   return;
                        }
/* 754 */               if (editor.getCount() > 0) {
/* 755 */                   animateTopButtons(2, null);
/* 756 */                   pausePreview(true);
/* 757 */                   if (this.mDragSortWidget != null) {
/* 758 */                       this.mDragSortWidget.setFocused(0);
                            }
/* 760 */                   this.mPluginManager.onIndividualSegmentClicked(null, this.mVideoTexture);
/* 761 */                   showThumbnailOverlay(null);
/* 771 */                   return;
                        }
/* 763 */               animatePlayButton(false);
/* 764 */               animatePreviewSpinner(false);
/* 765 */               hideThumbnailOverlay();
/* 766 */               animateTopButtons(2, null);
/* 767 */               this.mVideoTexture.setVisibility(8);
/* 768 */               this.mPluginManager.onIndividualSegmentClicked(null, this.mVideoTexture);
                    }
                }

/* 773 */       public void showThumbnailOverlay(RecordSegment segment) {
/* 774 */           if (this.mThumbnailOverlay != null && (this.mThumbnailOverlay.getVisibility() != 0 || segment != this.mThumbnailSegment)) {
/* 775 */               this.mThumbnailOverlay.setVisibility(0);
/* 776 */               this.mThumbnailOverlay.setAlpha(1.0f);
/* 777 */               if (segment == null && this.mEditorAdapter.getCount() > 0) {
/* 778 */                   segment = (RecordSegment) this.mEditorAdapter.getItem(0);
                        }
/* 780 */               if (segment != null) {
/* 781 */                   Drawable thumbDrawable = segment.getDrawableCopy(this.mActivity);
/* 782 */                   this.mThumbnailOverlay.setImageDrawable(thumbDrawable);
                        }
                    }
/* 785 */           this.mThumbnailSegment = segment;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$9 */
/* 787 */       class RunnableC05119 implements Runnable {
/* 788 */           RunnableC05119() {
                    }

                    @Override // java.lang.Runnable
/* 791 */           public void run() {
/* 792 */               VineRecorder.this.hideThumbnailOverlay();
                    }
                }

/* 788 */       public void hideThumbnailOverlayDelayed() {
/* 789 */           getHandler().postDelayed(new RunnableC05119(), REMOVE_THUMBNAIL_MILLIS);
                }

/* 798 */       public void hideThumbnailOverlay() {
/* 799 */           if (this.mThumbnailOverlay != null && this.mThumbnailOverlay.getVisibility() != 4) {
/* 801 */               this.mThumbnailOverlay.animate().setDuration(this.mShortAnimTime).alpha(0.0f).setListener(new ViewGoneAnimationListener(this.mThumbnailOverlay)).start();
/* 803 */               this.mThumbnailOverlay.setImageDrawable(null);
                    }
                }

                @Override // co.vine.android.player.VideoViewInterface.OnPreparedListener
/* 809 */       public void onPrepared(VideoViewInterface view) {
/* 810 */           if (this.mDragSortWidget != null) {
/* 811 */               if (this.mDragSortWidget.hasFloatView()) {
/* 812 */                   pausePreview(false);
                        }
/* 814 */               hideThumbnailOverlayDelayed();
/* 815 */               animatePreviewSpinner(false);
                    }
                }

/* 819 */       public boolean canPickUpFloatView() {
                    return (this.mPreviewLoadingOverlay == null || this.mPreviewLoadingOverlay.getVisibility() == 0) ? false : true;
                }

/* 823 */       public void pausePreview(boolean showPlayButton) {
/* 824 */           animatePlayButton(showPlayButton);
/* 825 */           if (this.mRefreshPreviewTask != null) {
/* 826 */               this.mRefreshPreviewTask.cancel(true);
/* 827 */               this.mRefreshPreviewTask = null;
                    }
/* 829 */           if (this.mVideoTexture != null) {
/* 830 */               this.mVideoTexture.pause();
/* 831 */               this.mVideoTexture.setAutoPlayOnPrepared(false);
                    }
                }

/* 836 */       public void playPreview(RecordSegment toPlay, boolean forceRefresh, boolean editMode, String tag) {
/* 837 */           animatePlayButton(false);
/* 838 */           refreshVideoView(toPlay, forceRefresh, editMode, toPlay != null, tag);
                }

/* 841 */       public boolean isEditingDirty() {
                    return isEditing() && !this.mEditorAdapter.compareTo(this.mEditedSegments);
                }

/* 845 */       public void animateTopButtons(int animation, RecordSegment segment) {
                    switch (animation) {
                        case 0:
/* 848 */                   this.mPluginManager.onIndividualSegmentClicked(segment, this.mVideoTexture);
                            break;
                        case 2:
/* 851 */                   this.mPluginManager.onIndividualSegmentClicked(null, this.mVideoTexture);
                            break;
                    }
                }

/* 856 */       public void animatePlayButton(boolean visible) {
/* 857 */           if (this.mPlayButton != null && this.mPlayRefreshButton != null && this.mPlayButtonContainer != null) {
/* 858 */               this.mPlayButtonContainer.animate().cancel();
/* 859 */               if (!visible) {
/* 860 */                   this.mPlayButtonContainer.animate().alpha(0.0f).setDuration(this.mShortAnimTime).setListener(new ViewGoneAnimationListener(this.mPlayButtonContainer)).start();
/* 880 */                   return;
                        }
/* 866 */               if (this.mDragSortWidget.getSelection() != -1) {
/* 867 */                   this.mPlayButton.setVisibility(8);
/* 868 */                   this.mPlayRefreshButton.setVisibility(0);
                        } else {
/* 870 */                   this.mPlayButton.setVisibility(0);
/* 871 */                   this.mPlayRefreshButton.setVisibility(8);
                        }
/* 873 */               this.mPlayButtonContainer.setVisibility(0);
/* 874 */               this.mPlayButtonContainer.animate().alpha(1.0f).setDuration(this.mShortAnimTime).start();
                    }
                }

/* 882 */       private void animatePreviewSpinner(boolean visible) {
/* 883 */           animateSpinner(this.mPreviewLoadingOverlay, visible);
                }

/* 886 */       private void animateSpinner(View view, boolean visible) {
/* 887 */           if (view != null) {
/* 888 */               view.animate().cancel();
/* 889 */               if (!visible) {
/* 890 */                   view.animate().alpha(0.0f).setDuration(this.mShortAnimTime).setListener(new ViewGoneAnimationListener(view)).start();
/* 904 */                   return;
                        }
/* 896 */               view.setVisibility(0);
/* 897 */               view.setAlpha(0.0f);
/* 898 */               view.animate().alpha(1.0f).setDuration(this.mShortAnimTime).start();
                    }
                }

/* 906 */       public void reverseFrames() {
/* 907 */           if (isEditing()) {
/* 908 */               this.mEditorAdapter.reverse();
                    }
                }

                @Override // co.vine.android.dragsort.DragSortWidget.CurrentlyPlayingProvider
/* 913 */       public int getCurrentlyPlayingPosition() {
/* 914 */           if (this.mLastSelectedPosition == -1 && this.mSegmentChangeDetector != null) {
/* 916 */               if (this.mSegmentChangeDetector.lastFirstItem >= 0 && this.mEditorAdapter != null && this.mSegmentChangeDetector.lastFirstItem < this.mEditorAdapter.getCount()) {
/* 919 */                   return this.mSegmentChangeDetector.lastFirstItem;
                        }
/* 921 */               return 0;
                    }
/* 923 */           return this.mLastSelectedPosition;
                }

/* 927 */       public void doOneFrame() {
/* 928 */           if (this.mCurrentRecordingFile != null && !isEditing() && this.mAutoFocusing && startRelativeTime()) {
/* 930 */               this.mHandler.postDelayed(new RunnableC049110(), 25L);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$10 */
/* 928 */       class RunnableC049110 implements Runnable {
/* 929 */           RunnableC049110() {
                    }

                    @Override // java.lang.Runnable
/* 932 */           public void run() {
/* 933 */               VineRecorder.this.endRelativeTime();
                    }
                }

                @Override // co.vine.android.recorder.RegularVineRecorder
/* 941 */       protected void onResumeTaskComplete() {
/* 942 */           ensureLowProfileStatusBar();
                }

/* 945 */       public void doTime(long time) {
/* 946 */           if (this.mCurrentRecordingFile != null && !isEditing() && this.mAutoFocusing && startRelativeTime()) {
/* 948 */               this.mHandler.postDelayed(new RunnableC049211(), time);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$11 */
/* 946 */       class RunnableC049211 implements Runnable {
/* 947 */           RunnableC049211() {
                    }

                    @Override // java.lang.Runnable
/* 950 */           public void run() {
/* 951 */               VineRecorder.this.endRelativeTime();
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 958 */       public void onResume(String tag) {
                    boolean z = true;
/* 959 */           boolean canKeepRecording = canKeepRecording();
                    Object[] objArr = new Object[4];
                    objArr[0] = Boolean.valueOf(this.mDelayDialog);
                    objArr[1] = Boolean.valueOf(canKeepRecording);
                    objArr[2] = Boolean.valueOf(isEditing());
                    objArr[3] = Boolean.valueOf(this.finalFile != null);
/* 960 */           CrashUtil.log("Resume VineRecorder: delayDialog: {} canKeepRecording: {} isEditing: {} NullFinalFile: {}.", objArr);
/* 961 */           StaticSizeVideoView videoTexture = new StaticSizeVideoView(this.mActivity);
/* 962 */           videoTexture.setUseVinePlayer(false);
/* 963 */           videoTexture.setSize(this.mSize.x, this.mSize.x);
/* 964 */           this.mVideoViewContainer.addView(videoTexture, 0);
/* 965 */           this.mVideoTexture = videoTexture;
/* 967 */           if (canKeepRecording) {
/* 968 */               if (!isEditing()) {
/* 969 */                   initPreviewSurface();
/* 970 */                   if (this.mStartWithEditMode) {
/* 971 */                       setEditMode(true, false);
/* 996 */                       return;
                            }
/* 973 */                   this.mResumeTask = getOnResumeTask(null, "OnResume_" + tag);
/* 974 */                   BasicVineRecorder.OnResumeTask onResumeTask = this.mResumeTask;
                            if (!this.mDelayDialog && (!this.mCurrentRecordingFile.isSavedSession || !this.mNeverResumedRecorder)) {
                                z = false;
                            }
                            onResumeTask.showDialogDelayed = z;
/* 975 */                   this.mResumeTask.execute(new Void[0]);
/* 996 */                   return;
                        }
/* 978 */               setEditMode(true, false);
/* 996 */               return;
                    }
/* 981 */           if (this.finalFile != null) {
/* 982 */               this.mOnCompleteConsumer.run();
/* 996 */               return;
                    }
/* 984 */           if (this.mStartProgressDialog != null) {
/* 985 */               this.mStartProgressDialog.setMessage(this.mFinishLastSegmentString);
/* 986 */               if (!this.mDelayDialog) {
                            try {
/* 988 */                       this.mStartProgressDialog.show();
                            } catch (WindowManager.BadTokenException e) {
                            }
                        }
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 998 */       public void setDiscardChanges(boolean discardChanges) {
/* 999 */           this.mDiscardChanges = discardChanges;
                }

/* 1003 */      public void refreshVideoView(RecordSegment segment, boolean forceRefresh, boolean editMode, boolean ignoreTrim, String tag) {
/* 1004 */          releaseSegmentChangeDetector();
/* 1005 */          if (editMode) {
/* 1006 */              this.mSegmentChangeDetector = new SegmentChangeDetector();
/* 1007 */              this.mSegmentChangeThread = new Thread(this.mSegmentChangeDetector);
/* 1008 */              this.mSegmentChangeThread.start();
                    }
/* 1010 */          if (this.mPlayButtonOnClickListener != null) {
/* 1011 */              this.mPlayButtonOnClickListener.editMode = editMode;
                    }
/* 1013 */          if (editMode && this.mEditorAdapter.getCount() == 0) {
/* 1014 */              hideThumbnailOverlay();
/* 1015 */              if (this.mPlayButton != null) {
/* 1016 */                  this.mPlayButton.setVisibility(8);
                        }
/* 1018 */              if (this.mVideoTexture != null) {
/* 1019 */                  this.mVideoTexture.setVisibility(8);
/* 1020 */                  animatePreviewSpinner(false);
/* 1065 */                  return;
                        }
/* 1065 */              return;
                    }
/* 1022 */          if (this.mLastPlayingSegment != segment || forceRefresh) {
/* 1023 */              if (!editMode || this.mEditorAdapter.getCount() > 0) {
/* 1024 */                  showThumbnailOverlay(segment);
                        }
/* 1026 */              if (!editMode) {
/* 1027 */                  if (this.mCameraView != null) {
/* 1028 */                      this.mCameraView.setVisibility(4);
                            }
/* 1030 */                  if (this.mVideoController != null) {
/* 1031 */                      this.mVideoController.releaseCameraAndPreview(MessageFormatter.toStringMessage("refreshVideo view {}", tag));
                            } else {
/* 1033 */                      CrashUtil.log("WARNING: it looks like the controller is already released, but we may still have camera and preview in use.");
                            }
                        }
/* 1036 */              if (segment == null && editMode && this.mDragSortWidget != null) {
/* 1038 */                  this.mDragSortWidget.setFocused(0);
                        }
/* 1041 */              if (this.mRefreshPreviewTask != null) {
/* 1042 */                  this.mRefreshPreviewTask.cancel(true);
                        }
/* 1044 */              if (segment != null && segment.removed) {
/* 1045 */                  SLog.m464i("Do not start refresh task because segment is removed.");
/* 1065 */                  return;
                        } else {
/* 1048 */                  if (this.mVideoTexture == null) {
/* 1049 */                      SLog.m464i("Do not start refresh task because onPause probably happened.");
/* 1065 */                      return;
                            }
/* 1052 */                  animatePreviewSpinner(true);
/* 1053 */                  this.mRefreshPreviewTask = new RefreshPreviewTask(segment, editMode, ignoreTrim);
/* 1054 */                  this.mRefreshPreviewTask.execute(new Void[0]);
/* 1065 */                  return;
                        }
                    }
/* 1056 */          if (this.mVideoTexture != null) {
/* 1057 */              this.mVideoTexture.setAutoPlayOnPrepared(true);
/* 1058 */              if (this.mVideoTexture.isPaused()) {
/* 1059 */                  this.mVideoTexture.resume();
                        }
                    }
/* 1062 */          hideThumbnailOverlayDelayed();
/* 1063 */          animatePreviewSpinner(false);
                }

/* 1067 */      public boolean isMessaging() {
/* 1068 */          return this.mIsMessaging;
                }

/* 1071 */      public boolean isFlashOn() {
/* 1072 */          return this.mVideoController.isFlashOn();
                }

/* 1075 */      public boolean isWithinDurationPercentageOf(double percentage) {
                    return (this.mSession == null || this.mSession.getConfig() == null || ((double) this.mCurrentDurationMs) >= ((double) this.mSession.getConfig().maxDuration) * percentage) ? false : true;
                }

/* 1080 */      public boolean setAudioTrim(boolean trim) {
/* 1081 */          if (this.mVideoController == null) {
                        return false;
                    }
/* 1082 */          this.mVideoController.setAudioTrim(trim);
/* 1083 */          return true;
                }

/* 1089 */      public SegmentEditorAdapter getEditorAdatper() {
/* 1090 */          return this.mEditorAdapter;
                }

/* 1093 */      public void changePlayButtonOnClickBehavior(boolean forceRefresh, boolean editMode) {
/* 1094 */          this.mPlayButtonOnClickListener.forceRefresh = forceRefresh;
/* 1095 */          this.mPlayButtonOnClickListener.editMode = editMode;
                }

/* 1098 */      public void saveSegmentIfNeeded(RecordSegment segment) throws IOException {
/* 1099 */          segment.saveDataToDiskIfNeeded(this.mCurrentRecordingFile.folder, this.mSession.getVideoData(), this.mSession.getAudioData(), segment);
                }

/* 1100 */      private class RefreshPreviewTask extends AsyncTask<Void, Void, Void> {
                    private final boolean mEditMode;
                    private final boolean mIgnoreTrim;
                    private final RecordSegment mSegment;



/* 1107 */          public RefreshPreviewTask(RecordSegment segment, boolean editMode, boolean ignoreTrim) {
/* 1109 */              this.mSegment = segment;
/* 1110 */              this.mEditMode = editMode;
/* 1111 */              this.mIgnoreTrim = ignoreTrim;
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 1115 */          protected Void doInBackground(Void... params) {
                        int percentage;
/* 1116 */              if (!isCancelled()) {
/* 1117 */                  if (this.mSegment != VineRecorder.access$100(VineRecorder.this)) {
/* 1118 */                      if (this.mSegment == null) {
/* 1119 */                          VineRecorder.access$800(VineRecorder.this);
/* 1165 */                          return null;
                                }
/* 1121 */                      long start = System.currentTimeMillis();
/* 1122 */                      if (!this.mEditMode) {
/* 1123 */                          while (VineRecorder.this.mVideoController.wasJustGrabbingDataForSegment()) {
/* 1124 */                              SLog.m464i("Waiting for audio recorder to settle down.");
                                        try {
/* 1126 */                                  Thread.sleep(RecordConfigUtils.AUDIO_WAIT_THRESHOLD_NS / 2);
                                        } catch (InterruptedException e) {
                                        }
                                    }
/* 1131 */                          if (this.mSegment.getVideoData().size() == 0) {
/* 1132 */                              SLog.m464i("Waiting for camera frame to come in one more time.");
                                        try {
/* 1134 */                                  Thread.sleep(33L);
                                        } catch (InterruptedException e2) {
                                        }
                                    }
/* 1150 */                          do {
/* 1141 */                              percentage = this.mSegment.getVideoDataEncodedPercentage();
/* 1142 */                              SLog.m465i("Segment video encoded percentage: {}.", Integer.valueOf(percentage));
/* 1143 */                              if (percentage < 100) {
                                            try {
/* 1145 */                                      Thread.sleep(200L);
                                            } catch (InterruptedException e3) {
/* 1147 */                                      throw new RuntimeException(e3);
                                            }
                                        }
/* 1150 */                          } while (percentage < 100);
/* 1151 */                          if (this.mSegment.getVideoData().size() == 0) {
/* 1152 */                              CrashUtil.log("Something wrong is happening, 0 video frames by the end of settling phase.");
                                    }
                                }
/* 1156 */                      if (!isCancelled()) {
/* 1157 */                          VineRecorder.this.mVideoController.makePreview(this.mSegment, false, this.mIgnoreTrim);
                                }
/* 1159 */                      SLog.m465i("Preview waited for {}ms.", Long.valueOf(System.currentTimeMillis() - start));
/* 1165 */                      return null;
                            }
/* 1161 */                  if (this.mSegment == null && VineRecorder.access$900(VineRecorder.this).hasInitialized) {
/* 1162 */                      VineRecorder.access$800(VineRecorder.this);
/* 1165 */                      return null;
                            }
/* 1165 */                  return null;
                        }
/* 1165 */              return null;
                    }

                    @Override // android.os.AsyncTask
/* 1169 */          protected void onPreExecute() {
/* 1170 */              super.onPreExecute();
/* 1171 */              VineRecorder.access$1000(VineRecorder.this);
                    }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 1175 */          protected void onPostExecute(Void aVoid) {
                        String videoPath;
/* 1176 */              SegmentEditorAdapter adapter = VineRecorder.access$900(VineRecorder.this);
/* 1177 */              boolean segmentAdapterIsValid = adapter != null && adapter.getCount() > 0;
/* 1178 */              SdkVideoView videoSurface = VineRecorder.access$600(VineRecorder.this);
/* 1179 */              if (videoSurface != null) {
/* 1180 */                  if (!isCancelled() && (!this.mEditMode || segmentAdapterIsValid)) {
/* 1181 */                      ViewGroup group = (ViewGroup) videoSurface.getParent();
/* 1182 */                      videoSurface.setVisibility(0);
/* 1183 */                      if (group != null) {
/* 1184 */                          group.setOnClickListener(VineRecorder.this.new PreviewVideoClickListener(this.mEditMode));
                                }
/* 1186 */                      if (VineRecorder.access$1100(VineRecorder.this) != null) {
/* 1187 */                          VineRecorder.access$1100(VineRecorder.this).setContentView(group, new Rect(0, VineRecorder.access$300(VineRecorder.this), VineRecorder.this.mSize.x, VineRecorder.access$300(VineRecorder.this) + VineRecorder.this.mSize.x));
                                }
/* 1190 */                      VineRecorder.access$1000(VineRecorder.this);
/* 1191 */                      VineRecorder.access$102(VineRecorder.this, this.mSegment);
/* 1192 */                      if (this.mSegment == null) {
/* 1192 */                          videoPath = VineRecorder.this.mCurrentRecordingFile != null ? VineRecorder.this.mCurrentRecordingFile.getPreviewVideoPath() : null;
                                } else {
                                    videoPath = this.mSegment.videoPath;
                                }
/* 1195 */                      if (this.mSegment != null && this.mSegment.isTrimmed()) {
/* 1196 */                          VineRecorder.access$1200(VineRecorder.this, videoPath, this.mSegment.getTrimmedVideoStartUs(), this.mSegment.getTrimmedAudioEndUs());
                                } else {
/* 1200 */                          VineRecorder.access$1200(VineRecorder.this, videoPath, -1L, -1L);
                                }
/* 1204 */                      VineRecorder.access$600(VineRecorder.this).setMute(this.mSegment != null && this.mSegment.isSilenced());
                            } else {
/* 1206 */                      videoSurface.setVisibility(8);
                            }
                        }
/* 1209 */              VineRecorder.access$1302(VineRecorder.this, null);
                    }

                    @Override // android.os.AsyncTask
/* 1213 */          public void onCancelled() {
/* 1214 */              VineRecorder.access$1400(VineRecorder.this, false);
                    }
                }

/* 1219 */      public void setVideoTextureSeekPoints(long startUs, long endUs) {
/* 1220 */          this.mVideoTextureSeekStartUs = startUs;
/* 1221 */          this.mVideoTextureSeekEndUs = endUs;
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$12 */
/* 1222 */      class RunnableC049312 implements Runnable {
/* 1223 */          RunnableC049312() {
                    }

                    @Override // java.lang.Runnable
/* 1226 */          public void run() {
                        int position;
                        int trimEndTimeMs;
/* 1227 */              Handler handler = VineRecorder.this.getHandler();
/* 1228 */              if (handler != null) {
/* 1230 */                  if (VineRecorder.access$600(VineRecorder.this) != null) {
                                if (VineRecorder.access$1500(VineRecorder.this) >= 0 && VineRecorder.access$1600(VineRecorder.this) >= 0) {
/* 1233 */                          trimEndTimeMs = ((int) VineRecorder.access$1500(VineRecorder.this)) / 1000;
                                } else {
/* 1235 */                          trimEndTimeMs = -1;
                                }
/* 1237 */                      position = VineRecorder.access$600(VineRecorder.this).getCurrentPosition();
/* 1238 */                      if (position >= trimEndTimeMs && trimEndTimeMs > 0) {
/* 1239 */                          VineRecorder.access$600(VineRecorder.this).seekTo((int) (VineRecorder.access$1600(VineRecorder.this) / 1000));
/* 1240 */                          VineRecorder.access$600(VineRecorder.this).setAutoPlayOnPrepared(true);
                                }
                            } else {
/* 1243 */                      position = -1;
                            }
/* 1245 */                  handler.postDelayed(this, Math.max(66, 200 - (position % 200)));
                        }
                    }
                }

/* 1250 */      private void preparePlayer(String path, long startStartUs, long startEndUs) {
/* 1251 */          setVideoTextureSeekPoints(startStartUs, startEndUs);
/* 1252 */          SdkVideoView videoTexture = this.mVideoTexture;
/* 1253 */          videoTexture.setOnPreparedListener(new C049413(videoTexture));
/* 1264 */          videoTexture.setOnCompletionListener(new C049514(videoTexture));
/* 1278 */          if (path != null) {
/* 1279 */              videoTexture.setVideoPath(path);
/* 1280 */              videoTexture.setAutoPlayOnPrepared(true);
/* 1281 */              videoTexture.start();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$13 */
/* 1251 */      class C049413 implements VideoViewInterface.OnPreparedListener {
                    final /* synthetic */ SdkVideoView val$videoTexture;

/* 1252 */          C049413(SdkVideoView sdkVideoView) {
                        this.val$videoTexture = sdkVideoView;
                    }

                    @Override // co.vine.android.player.VideoViewInterface.OnPreparedListener
/* 1255 */          public void onPrepared(VideoViewInterface view) {
/* 1256 */              VineRecorder.access$600(VineRecorder.this).setVisibility(0);
                        if (VineRecorder.access$1600(VineRecorder.this) > 0) {
/* 1258 */                  this.val$videoTexture.seekTo((int) (VineRecorder.access$1600(VineRecorder.this) / 1000));
                        }
/* 1260 */              VineRecorder.access$1400(VineRecorder.this, false);
/* 1261 */              VineRecorder.this.hideThumbnailOverlayDelayed();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$14 */
/* 1262 */      class C049514 implements VideoViewInterface.OnCompletionListener {
                    final /* synthetic */ SdkVideoView val$videoTexture;

/* 1263 */          C049514(SdkVideoView sdkVideoView) {
                        this.val$videoTexture = sdkVideoView;
                    }

                    /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$14$1, reason: invalid class name */
/* 1266 */          class AnonymousClass1 implements Runnable {
/* 1267 */              AnonymousClass1() {
                        }

                        @Override // java.lang.Runnable
/* 1270 */              public void run() {
/* 1271 */                  C049514.this.val$videoTexture.seekTo(VineRecorder.access$1600(VineRecorder.this) > 0 ? (int) (VineRecorder.access$1600(VineRecorder.this) / 1000) : 0);
/* 1272 */                  C049514.this.val$videoTexture.setAutoPlayOnPrepared(true);
/* 1273 */                  C049514.this.val$videoTexture.start();
                        }
                    }

                    @Override // co.vine.android.player.VideoViewInterface.OnCompletionListener
/* 1266 */          public void onCompletion(VideoViewInterface view) {
/* 1267 */              SLog.m446d("mjama  playback ended!");
/* 1268 */              this.val$videoTexture.postDelayed(new AnonymousClass1(), 20L);
                    }
                }

/* 1287 */      public class PreviewVideoClickListener implements View.OnClickListener {
                    private final boolean mEditMode;

/* 1288 */          public PreviewVideoClickListener(boolean editMode) {
/* 1290 */              this.mEditMode = editMode;
                    }

                    @Override // android.view.View.OnClickListener
/* 1294 */          public void onClick(View view) {
/* 1295 */              if (VineRecorder.access$600(VineRecorder.this) != null && VineRecorder.access$600(VineRecorder.this).isPlaying()) {
/* 1297 */                  VineRecorder.access$1700(VineRecorder.this).forceRefresh = false;
/* 1298 */                  VineRecorder.access$1700(VineRecorder.this).editMode = this.mEditMode;
/* 1299 */                  VineRecorder.this.pausePreview(true);
                        }
                    }
                }

/* 1304 */      private void refreshFullPreview() {
/* 1305 */          SLog.m464i("Refresh full preview.");
/* 1306 */          RecordingFile file = getFile();
/* 1307 */          RecordSession session = null;
/* 1308 */          if (file != null) {
/* 1309 */              session = file.getSession();
                    }
/* 1311 */          if (session != null) {
/* 1312 */              ArrayList<RecordSegment> data = this.mEditorAdapter.getData();
/* 1313 */              byte[] videoData = new byte[session.getVideoData().length];
/* 1314 */              AudioArray audioData = AudioArrays.newInstance(session.getAudioData().getLength(), session.getVersion().getAudioArrayType());
                        try {
/* 1316 */                  this.mVideoController.writeToFile(RecordSegment.applyForEditedChanges(file.folder, session, videoData, audioData, data), videoData, audioData);
/* 1321 */                  long lastTime = 0;
                            for (int i = 0; i < data.size(); i++) {
/* 1323 */                      RecordSegment segment = data.get(i);
/* 1324 */                      segment.index = i;
/* 1325 */                      segment.startTimestamp = lastTime;
/* 1326 */                      lastTime += (long) segment.getAudioDurationMs();
/* 1327 */                      SLog.m465i("Timestamp modified to: {}.", Long.valueOf(segment.startTimestamp));
                            }
                        } catch (IOException e) {
/* 1319 */                  throw new RecordSegmentIOException(e);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$15 */
/* 1333 */      class AsyncTaskC049615 extends RegularVineRecorder.FinishProcessTask {
/* 1334 */          AsyncTaskC049615(String x0, Runnable x1, boolean x2, boolean x3) {
                        super(x0, x1, x2, x3);
                    }


                    @Override // co.vine.android.recorder.RegularVineRecorder.FinishProcessTask, co.vine.android.recorder.BaseFinishProcessTask
                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 1337 */          protected void onPostExecute(Void aVoid) {
/* 1338 */              super.onPostExecute(aVoid);
/* 1339 */              VineRecorder.access$1900(VineRecorder.this, VineRecorder.access$1800(VineRecorder.this), false);
                    }

                    @Override // co.vine.android.recorder.BaseFinishProcessTask, android.os.AsyncTask
/* 1343 */          protected void onPreExecute() {
/* 1344 */              super.onPreExecute();
/* 1345 */              VineRecorder.access$1900(VineRecorder.this, VineRecorder.access$1800(VineRecorder.this), true);
                    }
                }

                @Override // co.vine.android.recorder.RegularVineRecorder, co.vine.android.recorder.BasicVineRecorder
/* 1333 */      public BaseFinishProcessTask getFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
                    return this.mFastEncoding ? new AsyncTaskC049615(tag, onComplete, releasePreview, saveSession) : super.getFinishProcessTask(tag, onComplete, releasePreview, saveSession);
                }

/* 1354 */      public void writePreviewToFile() {
                    try {
/* 1355 */              int frameRate = RecordSegment.getFrameRate(this.mEditedSegments);
/* 1357 */              SwVineFrameRecorder videoRecorder = RecordConfigUtils.newVideoRecorder(this.mCurrentRecordingFile.getVideoPath() + ".video" + RecordConfigUtils.VIDEO_CONTAINER_EXT, frameRate, 480, this.mUseMp4);
/* 1359 */              videoRecorder.start();
/* 1360 */              RecordSession recordSession = getFile().getSession();
/* 1361 */              SwCombiningRunnable combiningRunnable = SwCombiningRunnable.newInstance(getFile(), false, recordSession.getAudioData(), recordSession.getVideoData(), this.mEditedSegments, videoRecorder, this.mVideoController.getFinishProcessTask());
/* 1364 */              combiningRunnable.combineVideos();
                    } catch (SwVineFrameRecorder.Exception e) {
/* 1366 */              CrashUtil.logException(e, "Cannot start audio/video recorder. ", new Object[0]);
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 1371 */      protected void invalidateFinishButton() {
                    if (this.mCurrentDurationMs < this.mThresholdMs) {
/* 1373 */              View view = this.mFinishButton;
/* 1374 */              if (view != null) {
/* 1375 */                  view.setVisibility(8);
/* 1380 */                  return;
                        }
/* 1380 */              return;
                    }
/* 1378 */          onProgressThresholdReached();
                }

                @Override // co.vine.android.recorder.RegularVineRecorder, android.view.View.OnTouchListener
/* 1383 */      public synchronized boolean onTouch(View v, MotionEvent event) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return !isEditing() && super.onTouch(v, event);
                }

                @Override // android.view.View.OnKeyListener
/* 1389 */      public synchronized boolean onKey(View v, int keyCode, KeyEvent event) {
                    boolean z;
                    switch (keyCode) {
                        case 27:
                            try {
                                switch (event.getAction()) {
                                    case 0:
/* 1394 */                              startRelativeTime();
                                        break;
                                    case 1:
/* 1398 */                              endRelativeTime();
                                        break;
                                }
/* 1402 */                      z = true;
                            } catch (Throwable th) {
                                throw th;
                            }
                            break;
                        default:
/* 1405 */                  z = false;
                            break;
                    }
                    return z;
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 1411 */      public BasicVineRecorder.ResumeCameraAsyncTask getSwitchCameraTask() {
/* 1412 */          return new RegularVineRecorder.ResumeCameraAsyncTask(true);
                }

/* 1415 */      public void onEditorDone(View button) {
/* 1416 */          if (this.mEditorDoneAsyncTask == null) {
/* 1417 */              this.mEditorDoneAsyncTask = new EditorDoneAsyncTask(button);
/* 1418 */              this.mEditorDoneAsyncTask.execute(new Void[0]);
                    }
                }

/* 1422 */      public void onEditorCancel(View button) {
/* 1423 */          if (this.mReturnToPreview) {
/* 1424 */              returnToPreview(true);
                    } else {
/* 1426 */              setEditMode(button, !this.mEditing, true);
                    }
                }

/* 1428 */      public class EditorDoneAsyncTask extends AsyncTask<Void, Void, Void> {
                    private final View mEditorDoneButton;
                    private final boolean mReturnToPreviewNow;
                    private boolean mWasIndeterminate;



/* 1435 */          public EditorDoneAsyncTask(View editorDoneButton) {
/* 1437 */              this.mReturnToPreviewNow = VineRecorder.access$2000(VineRecorder.this) && VineRecorder.access$2100(VineRecorder.this) >= VineRecorder.this.mThresholdMs;
/* 1438 */              this.mEditorDoneButton = editorDoneButton;
                    }

                    @Override // android.os.AsyncTask
/* 1442 */          protected void onPreExecute() {
/* 1443 */              if (VineRecorder.this.mCurrentRecordingFile != null) {
/* 1444 */                  if (this.mReturnToPreviewNow) {
/* 1445 */                      if (VineRecorder.this.mStartProgressDialog != null) {
/* 1446 */                          VineRecorder.this.mStartProgressDialog.setMessage(VineRecorder.this.mFinishProgressDialogMessage[VineRecorder.this.mFinishProgressDialogMessage.length - 1]);
/* 1447 */                          this.mWasIndeterminate = VineRecorder.this.mStartProgressDialog.isIndeterminate();
/* 1448 */                          if (!this.mWasIndeterminate) {
/* 1449 */                              VineRecorder.this.mStartProgressDialog.setIndeterminate(true);
                                    }
/* 1451 */                          VineRecorder.this.mStartProgressDialog.show();
                                }
/* 1453 */                      if (VineRecorder.access$1300(VineRecorder.this) != null) {
/* 1454 */                          VineRecorder.access$1300(VineRecorder.this).cancel(false);
/* 1462 */                          return;
                                }
/* 1462 */                      return;
                            }
/* 1457 */                  if (!isCancelled()) {
/* 1458 */                      VineRecorder.this.setEditMode(this.mEditorDoneButton, VineRecorder.access$2200(VineRecorder.this) ? false : true, false);
                            }
                        }
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 1465 */          protected Void doInBackground(Void... params) {
/* 1466 */              if (this.mReturnToPreviewNow) {
/* 1467 */                  VineRecorder.access$2300(VineRecorder.this, false);
/* 1469 */                  return null;
                        }
/* 1469 */              return null;
                    }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 1473 */          protected void onPostExecute(Void aVoid) {
/* 1474 */              if (VineRecorder.this.mCurrentRecordingFile != null) {
/* 1475 */                  if (this.mReturnToPreviewNow) {
/* 1476 */                      VineRecorder.access$2400(VineRecorder.this, true);
/* 1477 */                      VineRecorder.this.mPluginManager.onSetEditMode(false, VineRecorder.this.mCurrentRecordingFile != null && VineRecorder.this.mCurrentRecordingFile.hasData());
/* 1478 */                      VineRecorder.this.mHandler.removeCallbacks(VineRecorder.access$2500(VineRecorder.this));
                            }
/* 1480 */                  if (VineRecorder.this.mStartProgressDialog != null) {
/* 1481 */                      VineRecorder.this.mStartProgressDialog.dismiss();
/* 1482 */                      if (!this.mWasIndeterminate) {
/* 1483 */                          VineRecorder.this.mStartProgressDialog.setIndeterminate(false);
                                }
                            }
                        }
/* 1487 */              VineRecorder.access$2602(VineRecorder.this, null);
                    }
                }

                @Override // co.vine.android.recorder.RegularVineRecorder, co.vine.android.recorder.BasicVineRecorder
                @SuppressLint({"ShowToast"})
/* 1494 */      public void onUiResumed(Activity activity, Runnable onCompleteAsyncTask, boolean doNotResumeRecorder) {
/* 1495 */          super.onUiResumed(activity, onCompleteAsyncTask, doNotResumeRecorder);
/* 1496 */          this.mRecordingOptions = activity.findViewById(this.mRecordingOptionsRowId);
/* 1497 */          this.mThumbnailList = activity.findViewById(android.R.id.list);
/* 1498 */          this.mThumbnailOverlay = (ImageView) activity.findViewById(this.mThumbnailOverlayId);
/* 1499 */          this.mFinishLoadingOverlay = activity.findViewById(this.mFinishLoadingOverlayId);
/* 1500 */          this.mPlayButtonContainer = activity.findViewById(this.mPlayButtonContainerId);
/* 1501 */          this.mPlayButtonOnClickListener = new PlayButtonOnClickListener();
/* 1502 */          this.mPlayButtonContainer.setOnClickListener(this.mPlayButtonOnClickListener);
/* 1503 */          this.mPlayButtonContainer.setOnTouchListener(new ViewOnTouchListenerC049716());
/* 1514 */          this.mFinishButton = activity.findViewById(this.mFinishButtonId);
/* 1515 */          this.mFinishButton.setOnClickListener(this.mFinishClicker);
/* 1516 */          this.mPlayButton = ((ViewGroup) this.mPlayButtonContainer).getChildAt(0);
/* 1517 */          this.mPlayRefreshButton = ((ViewGroup) this.mPlayButtonContainer).getChildAt(1);
/* 1518 */          this.mDragSortWidget = (DragSortWidget) activity.findViewById(this.mDragSortWidgetId);
/* 1519 */          this.mDragSortWidget.setSelectionChangedListener(this);
/* 1520 */          this.mDragSortWidget.setCurrentlyPlayingProvider(this);
/* 1521 */          this.mPreviewLoadingOverlay = activity.findViewById(this.mPreviewLoadingOverlayId);
/* 1522 */          if (!isEditing() && this.mCurrentDurationMs >= this.mThresholdMs) {
/* 1523 */              onProgressThresholdReached();
                    }
/* 1525 */          this.mProgressView.setOnClickListener(new ViewOnClickListenerC049817());
/* 1538 */          this.mIsSwitchingCamera = false;
/* 1540 */          this.mProgressOverlay = activity.findViewById(this.mProgressOverlayId);
/* 1542 */          ((OnMeasureNotifyingRelativeLayout) this.mRootLayoutView).setOnMeasureListener(new C049918());
/* 1548 */          if (!doNotResumeRecorder) {
/* 1549 */              onResume("UI Resume");
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$16 */
/* 1501 */      class ViewOnTouchListenerC049716 implements View.OnTouchListener {
/* 1502 */          ViewOnTouchListenerC049716() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 1505 */          public boolean onTouch(View view, MotionEvent event) {
/* 1506 */              if (VineRecorder.access$1100(VineRecorder.this) != null) {
/* 1507 */                  return VineRecorder.access$1100(VineRecorder.this).onTouch((View) VineRecorder.access$600(VineRecorder.this).getParent(), event, VineRecorder.access$2700(VineRecorder.this).getLeft(), VineRecorder.access$2700(VineRecorder.this).getTop());
                        }
                        return false;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$17 */
/* 1523 */      class ViewOnClickListenerC049817 implements View.OnClickListener {
/* 1524 */          ViewOnClickListenerC049817() {
                    }

                    @Override // android.view.View.OnClickListener
/* 1527 */          public void onClick(View v) {
/* 1528 */              if (!VineRecorder.access$2800(VineRecorder.this)) {
/* 1529 */                  if (VineRecorder.this.mPluginManager.canEdit()) {
/* 1530 */                      VineRecorder.this.setEditMode(VineRecorder.this.mProgressView, !VineRecorder.access$2200(VineRecorder.this), false);
                            } else {
/* 1532 */                      SLog.m464i("Plugin has a lock on edit right now.");
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$18 */
/* 1540 */      class C049918 implements OnMeasureNotifyingRelativeLayout.OnMeasureListener {
/* 1541 */          C049918() {
                    }

                    @Override // co.vine.android.views.OnMeasureNotifyingRelativeLayout.OnMeasureListener
/* 1544 */          public void onMeasure() {
/* 1545 */              VineRecorder.access$2900(VineRecorder.this);
                    }
                }

/* 1553 */      private void returnToPreview(boolean discard) {
/* 1554 */          if (this.mVideoController != null && this.mCurrentRecordingFile != null) {
/* 1555 */              releaseSegmentChangeDetector();
/* 1556 */              if (!discard) {
/* 1557 */                  commitChanges();
                        }
/* 1559 */              this.mCanKeepRecording = false;
/* 1560 */              writePreviewToFile();
/* 1561 */              startHashTask(false);
                    }
                }

/* 1565 */      private int getDurationFromSegments() {
/* 1566 */          int duration = 0;
                    for (RecordSegment segment : this.mEditorAdapter.getData()) {
/* 1568 */              if (!segment.removed) {
/* 1569 */                  duration += segment.getDurationMs();
                        }
                    }
/* 1572 */          return duration;
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 1576 */      public void onProgressThresholdReached() {
/* 1577 */          View view = this.mFinishButton;
/* 1578 */          if (view != null && view.getVisibility() != 0) {
/* 1579 */              view.setVisibility(0);
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
                @TargetApi(16)
/* 1585 */      public void onPause() {
/* 1586 */          super.onPause();
/* 1587 */          if (Build.VERSION.SDK_INT >= 16 && this.mMediaActionSound != null) {
/* 1588 */              this.mMediaActionSound.release();
                    }
/* 1590 */          EditorDoneAsyncTask task = this.mEditorDoneAsyncTask;
/* 1591 */          if (task != null) {
/* 1592 */              task.cancel(false);
                    }
/* 1594 */          this.mVideoViewContainer.removeView(this.mVideoTexture);
/* 1595 */          if (this.mVideoTexture != null) {
/* 1596 */              this.mVideoTexture.suspend();
/* 1597 */              this.mVideoTexture = null;
                    }
                }

/* 1599 */      public class PlayButtonOnClickListener implements View.OnClickListener {
                    boolean forceRefresh = false;
                    boolean editMode = false;

/* 1600 */          public PlayButtonOnClickListener() {
                    }

                    @Override // android.view.View.OnClickListener
/* 1606 */          public void onClick(View v) {
/* 1607 */              if (VineRecorder.access$900(VineRecorder.this) != null) {
/* 1608 */                  int selection = VineRecorder.access$1100(VineRecorder.this).getSelection();
/* 1609 */                  RecordSegment toPlay = null;
/* 1610 */                  if (selection != -1) {
/* 1611 */                      toPlay = (RecordSegment) VineRecorder.access$900(VineRecorder.this).getItem(VineRecorder.access$1100(VineRecorder.this).getSelection());
                            }
/* 1613 */                  if (toPlay == null) {
/* 1614 */                      VineRecorder.access$3002(VineRecorder.this, -1);
/* 1615 */                      if (VineRecorder.access$1100(VineRecorder.this) != null) {
/* 1616 */                          VineRecorder.access$1100(VineRecorder.this).setSelection(-1);
                                }
/* 1618 */                      VineRecorder.this.animateTopButtons(2, null);
                            }
/* 1620 */                  VineRecorder.this.playPreview(toPlay, this.forceRefresh, this.editMode, "playButton");
                        }
                    }
                }

                @Override // co.vine.android.recorder.RegularVineRecorder, co.vine.android.recorder.BasicVineRecorder
/* 1625 */      public void onUiPaused() {
/* 1626 */          releaseSegmentChangeDetector();
/* 1627 */          if (this.mDragSortWidget != null) {
/* 1628 */              this.mDragSortWidget.releaseReferences();
                    }
                    try {
/* 1631 */              if (this.mRefreshPreviewTask != null) {
/* 1632 */                  this.mRefreshPreviewTask.cancel(false);
                        }
                    } catch (Exception e) {
/* 1635 */              SLog.m459e("It's probably detached already.", (Throwable) e);
                    }
/* 1637 */          if (this.mVideoTexture != null) {
/* 1638 */              this.mVideoTexture.setOnErrorListener(null);
/* 1639 */              this.mVideoTexture.setOnPreparedListener(null);
                    }
/* 1641 */          this.mRefreshPreviewTask = null;
/* 1642 */          this.mRecordingOptions = null;
/* 1643 */          this.mPreviewLoadingOverlay = null;
/* 1644 */          this.mFinishLoadingOverlay = null;
/* 1645 */          this.mThumbnailList = null;
/* 1646 */          if (this.mPlayButtonContainer != null) {
/* 1647 */              this.mPlayButtonContainer.setOnTouchListener(null);
/* 1648 */              this.mPlayButtonContainer.setOnClickListener(null);
                    }
/* 1650 */          this.mPlayButtonContainer = null;
/* 1651 */          this.mThumbnailSegment = null;
/* 1652 */          this.mThumbnailOverlay = null;
/* 1653 */          if (this.mDragSortWidget != null) {
/* 1654 */              this.mDragSortWidget.setSelectionChangedListener(null);
/* 1655 */              this.mDragSortWidget.setCurrentlyPlayingProvider(null);
                    }
/* 1657 */          this.mDragSortWidget = null;
/* 1658 */          if (this.mFinishButton != null) {
/* 1659 */              this.mFinishButton.setOnClickListener(null);
                    }
/* 1661 */          this.mFinishButton = null;
/* 1662 */          super.onUiPaused();
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 1665 */      protected void adjustBoundaries(CameraSetting cs) {
/* 1666 */          Activity activity = this.mActivity;
/* 1667 */          if (activity != null && cs != null) {
/* 1670 */              float previewWidth = cs.originalW;
/* 1671 */              if (cs.frontFacing && cs.frontFacingAspectRatio > 0.0f) {
/* 1672 */                  previewWidth = cs.originalH * cs.frontFacingAspectRatio;
                        } else if (cs.backFacingAspectRatio > 0.0f) {
/* 1674 */                  previewWidth = cs.originalH * cs.backFacingAspectRatio;
                        }
                        if (cs.frontFacingAspectRatio > 0.0f || cs.backFacingAspectRatio > 0.0f) {
/* 1677 */                  CrashUtil.logException(new VineLoggingException("Adjusting ratio originalW = " + cs.originalW + " originalH = " + cs.originalH + " hardware Hardware Version: " + Build.DEVICE + " - " + Build.MODEL + " (" + Build.CPU_ABI + "." + Build.HARDWARE + "." + Build.BRAND + "." + Build.PRODUCT + "." + Build.MANUFACTURER + "." + Build.CPU_ABI2 + ")"));
                        }
/* 1683 */              if (this.mFinishLoadingOverlay != null && this.mVideoTexture != null) {
/* 1685 */                  ViewGroup.LayoutParams params = this.mVideoTexture.getLayoutParams();
/* 1686 */                  params.width = this.mSize.x;
/* 1687 */                  params.height = this.mSize.x;
/* 1688 */                  this.mFinishLoadingOverlay.post(new RunnableC050019(params));
                        }
/* 1699 */              float previewHeight = cs.originalH;
/* 1700 */              float aspectRatio = previewWidth / previewHeight;
/* 1702 */              int surfaceViewHeightPx = (int) (this.mSize.x * aspectRatio);
/* 1705 */              if (this.mTopMaskView != null && this.mBottomMaskView != null) {
/* 1706 */                  int topMaskHeightPx = this.mTopMaskView.getLayoutParams().height;
/* 1707 */                  int surfaceViewMidpoint = surfaceViewHeightPx / 2;
/* 1708 */                  int midpointYOffset = (this.mSize.x / 2) + topMaskHeightPx;
/* 1709 */                  int surfaceViewYPos = midpointYOffset - surfaceViewMidpoint;
/* 1711 */                  RelativeLayout.LayoutParams cameraViewParams = (RelativeLayout.LayoutParams) this.mCameraView.getLayoutParams();
/* 1713 */                  cameraViewParams.width = this.mSize.x;
/* 1714 */                  cameraViewParams.height = surfaceViewHeightPx;
/* 1715 */                  cameraViewParams.setMargins(0, surfaceViewYPos, 0, 0);
/* 1717 */                  activity.runOnUiThread(new RunnableC050220(cameraViewParams, topMaskHeightPx));
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$19 */
/* 1686 */      class RunnableC050019 implements Runnable {
                    final /* synthetic */ ViewGroup.LayoutParams val$params;

/* 1687 */          RunnableC050019(ViewGroup.LayoutParams layoutParams) {
                        this.val$params = layoutParams;
                    }

                    @Override // java.lang.Runnable
/* 1690 */          public void run() {
/* 1691 */              if (VineRecorder.access$1800(VineRecorder.this) != null) {
/* 1692 */                  VineRecorder.access$1800(VineRecorder.this).setLayoutParams(this.val$params);
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$20 */
/* 1715 */      class RunnableC050220 implements Runnable {
                    final /* synthetic */ RelativeLayout.LayoutParams val$cameraViewParams;
                    final /* synthetic */ int val$topMaskHeightPx;

/* 1716 */          RunnableC050220(RelativeLayout.LayoutParams layoutParams, int i) {
                        this.val$cameraViewParams = layoutParams;
                        this.val$topMaskHeightPx = i;
                    }

                    @Override // java.lang.Runnable
/* 1719 */          public void run() {
/* 1720 */              View view = VineRecorder.this.mCameraView;
/* 1721 */              if (view != null) {
/* 1722 */                  SLog.m448d("Setting preview sizes to {} {}.", Integer.valueOf(this.val$cameraViewParams.width), Integer.valueOf(this.val$cameraViewParams.height));
/* 1723 */                  view.setLayoutParams(this.val$cameraViewParams);
                        }
/* 1725 */              VineRecorder.this.mPluginManager.onAdjustingLayoutParamsComplete(this.val$topMaskHeightPx, VineRecorder.this.mSize.x);
                    }
                }

/* 1733 */      private void adjustStaticBoundaries() {
/* 1734 */          Activity activity = this.mActivity;
/* 1735 */          if (activity != null) {
/* 1736 */              int topMaskHeightPx = this.mTopMaskView.getLayoutParams().height;
/* 1739 */              int rootHeight = this.mRootLayoutView.getMeasuredHeight();
/* 1740 */              int bottomMaskHeightPx = (rootHeight - topMaskHeightPx) - this.mSize.x;
/* 1742 */              RelativeLayout.LayoutParams bottomMaskParams = (RelativeLayout.LayoutParams) this.mBottomMaskView.getLayoutParams();
/* 1744 */              bottomMaskParams.height = bottomMaskHeightPx;
/* 1746 */              activity.runOnUiThread(new RunnableC050321(bottomMaskParams));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$21 */
/* 1744 */      class RunnableC050321 implements Runnable {
                    final /* synthetic */ RelativeLayout.LayoutParams val$bottomMaskParams;

/* 1745 */          RunnableC050321(RelativeLayout.LayoutParams layoutParams) {
                        this.val$bottomMaskParams = layoutParams;
                    }

                    @Override // java.lang.Runnable
/* 1748 */          public void run() {
/* 1749 */              View view = VineRecorder.this.mBottomMaskView;
/* 1750 */              if (view != null) {
/* 1751 */                  view.setLayoutParams(this.val$bottomMaskParams);
                        }
                    }
                }

/* 1758 */      private void releaseSegmentChangeDetector() {
/* 1759 */          if (this.mSegmentChangeThread != null) {
/* 1760 */              this.mSegmentChangeDetector.runThread = false;
                        try {
/* 1762 */                  this.mSegmentChangeThread.join();
                        } catch (InterruptedException e) {
                        }
/* 1765 */              this.mSegmentChangeThread = null;
                    }
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 1769 */      public void onProgressMaxReached() {
/* 1770 */          if (!this.mHasPreviewedAlready) {
/* 1771 */              finish("progressMaxReached");
                    }
                }

/* 1775 */      public View getProgressView() {
/* 1776 */          return this.mProgressView;
                }

                @Override // co.vine.android.recorder.BasicVineRecorder
/* 1780 */      public void onCameraReady(RecordController controller) {
/* 1781 */          if (this.mActivity != null) {
/* 1782 */              this.mHandler.post(this.onCameraReadyRunnable);
/* 1783 */              super.onCameraReady(controller);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$22 */
/* 1785 */      class RunnableC050422 implements Runnable {
/* 1786 */          RunnableC050422() {
                    }

                    @Override // java.lang.Runnable
/* 1789 */          public void run() {
/* 1790 */              if (VineRecorder.access$3100(VineRecorder.this).getVisibility() != 8) {
/* 1791 */                  VineRecorder.access$3100(VineRecorder.this).setVisibility(8);
                        }
                    }
                }

                @Override // co.vine.android.recorder.RegularVineRecorder, co.vine.android.recorder.BasicVineRecorder
/* 1796 */      public void changeProgress(long progress) {
/* 1797 */          super.changeProgress(progress);
/* 1798 */          if (this.mProgressOverlay != null) {
/* 1799 */              if (SystemUtil.isOnMainThread()) {
/* 1800 */                  this.mChangeProgressOverlayVisibilityRunnable.run();
                        } else {
/* 1802 */                  this.mHandler.post(this.mChangeProgressOverlayVisibilityRunnable);
                        }
                    }
                }

/* 1812 */      public class SegmentChangeDetector implements Runnable {
                    private static final int SLEEP_MILLIS = 40;
                    public int lastFirstItem = 0;
                    public boolean runThread = true;

/* 1813 */          public SegmentChangeDetector() {
                    }

                    @Override // java.lang.Runnable
/* 1819 */          public void run() {
                        int position;
                        ArrayList<RecordSegment> segments;
                        int size;
/* 1820 */              while (this.runThread) {
                            try {
/* 1822 */                      if (VineRecorder.access$3000(VineRecorder.this) == -1 && VineRecorder.access$900(VineRecorder.this) != null && VineRecorder.access$600(VineRecorder.this) != null && VineRecorder.access$600(VineRecorder.this).isPlaying() && (position = VineRecorder.access$600(VineRecorder.this).getCurrentPosition() + 20) > 0 && (size = (segments = VineRecorder.access$900(VineRecorder.this).getData()).size()) > 0) {
                                    for (int i = size - 1; i >= 0; i--) {
                                        if (position >= segments.get(i).startTimestamp) {
/* 1832 */                                  if (this.lastFirstItem != i) {
/* 1835 */                                      this.lastFirstItem = i;
/* 1836 */                                      Activity activity = VineRecorder.this.mActivity;
/* 1837 */                                      if (activity == null) {
                                                    break;
                                                }
/* 1838 */                                      activity.runOnUiThread(new RunnableC05121());
                                                break;
                                            }
                                            break;
                                        }
                                    }
                                }
/* 1857 */                      Thread.sleep(40L);
                            } catch (InterruptedException e) {
/* 1862 */                      return;
                            }
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.recorder.VineRecorder$SegmentChangeDetector$1 */
/* 1836 */          class RunnableC05121 implements Runnable {
/* 1837 */              RunnableC05121() {
                        }

                        @Override // java.lang.Runnable
/* 1840 */              public void run() {
                            DragSortWidget widget;
/* 1841 */                  if (VineRecorder.access$3000(VineRecorder.this) == -1 && (widget = VineRecorder.access$1100(VineRecorder.this)) != null) {
/* 1844 */                      widget.setFocused(SegmentChangeDetector.this.lastFirstItem);
                            }
                        }
                    }
                }

                @Override // co.vine.android.recorder.RegularVineRecorder
/* 1865 */      public Point getSize() {
/* 1866 */          return this.mSize;
                }

                @TargetApi(16)
/* 1871 */      public void playStopRecordingSound() {
                    try {
/* 1872 */              if (Build.VERSION.SDK_INT >= 16 && this.mMediaActionSound != null && this.mPlaySound) {
/* 1873 */                  this.mMediaActionSound.play(3);
                        }
                    } catch (Exception e) {
                    }
                }

                @TargetApi(16)
/* 1884 */      public void playStartRecordingSound() {
                    try {
/* 1885 */              if (Build.VERSION.SDK_INT >= 16 && this.mMediaActionSound != null && this.mPlaySound) {
/* 1886 */                  this.mMediaActionSound.play(2);
                        }
                    } catch (Exception e) {
                    }
                }
            }
