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
            
            
            @TargetApi(14)
    public class VineRecorder extends RegularVineRecorder implements View.OnTouchListener, DragSortWidget.SelectionChangedListener, VideoViewInterface.OnPreparedListener, DragSortWidget.CurrentlyPlayingProvider, View.OnKeyListener {
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

        static  boolean access$000(VineRecorder x0) {
            return x0.mInitiallyStartedWithEditMode;
                }

        static  RecordSegment access$100(VineRecorder x0) {
            return x0.mLastPlayingSegment;
                }

        static  void access$1000(VineRecorder x0) {
            x0.adjustEditBoundaries();
                }

        static  RecordSegment access$102(VineRecorder x0, RecordSegment x1) {
            x0.mLastPlayingSegment = x1;
                    return x1;
                }

        static  DragSortWidget access$1100(VineRecorder x0) {
            return x0.mDragSortWidget;
                }

        static  void access$1200(VineRecorder x0, String x1, long x2, long x3) {
            x0.preparePlayer(x1, x2, x3);
                }

        static  RefreshPreviewTask access$1300(VineRecorder x0) {
            return x0.mRefreshPreviewTask;
                }

        static  RefreshPreviewTask access$1302(VineRecorder x0, RefreshPreviewTask x1) {
            x0.mRefreshPreviewTask = x1;
                    return x1;
                }

        static  void access$1400(VineRecorder x0, boolean x1) {
            x0.animatePreviewSpinner(x1);
                }

        static  long access$1500(VineRecorder x0) {
            return x0.mVideoTextureSeekEndUs;
                }

        static  long access$1600(VineRecorder x0) {
            return x0.mVideoTextureSeekStartUs;
                }

        static  PlayButtonOnClickListener access$1700(VineRecorder x0) {
            return x0.mPlayButtonOnClickListener;
                }

        static  View access$1800(VineRecorder x0) {
            return x0.mFinishLoadingOverlay;
                }

        static  void access$1900(VineRecorder x0, View x1, boolean x2) {
            x0.animateSpinner(x1, x2);
                }

        static  View access$200(VineRecorder x0) {
            return x0.mRecordingOptions;
                }

        static  boolean access$2000(VineRecorder x0) {
            return x0.mReturnToPreview;
                }

        static  int access$2100(VineRecorder x0) {
            return x0.getDurationFromSegments();
                }

        static  boolean access$2200(VineRecorder x0) {
            return x0.mEditing;
                }

        static  void access$2300(VineRecorder x0, boolean x1) {
            x0.returnToPreview(x1);
                }

        static  void access$2400(VineRecorder x0, boolean x1) {
            x0.animateEditModeControlsOut(x1);
                }

        static  Runnable access$2500(VineRecorder x0) {
            return x0.mVideoPreviewTrimPositionChecker;
                }

        static  EditorDoneAsyncTask access$2602(VineRecorder x0, EditorDoneAsyncTask x1) {
            x0.mEditorDoneAsyncTask = x1;
                    return x1;
                }

        static  View access$2700(VineRecorder x0) {
            return x0.mPlayButtonContainer;
                }

        static  boolean access$2800(VineRecorder x0) {
            return x0.mIsMessaging;
                }

        static  void access$2900(VineRecorder x0) {
            x0.adjustStaticBoundaries();
                }

        static  int access$300(VineRecorder x0) {
            return x0.mEditorButtonsHeight;
                }

        static  int access$3000(VineRecorder x0) {
            return x0.mLastSelectedPosition;
                }

        static  int access$3002(VineRecorder x0, int x1) {
            x0.mLastSelectedPosition = x1;
                    return x1;
                }

        static  View access$3100(VineRecorder x0) {
            return x0.mProgressOverlay;
                }

        static  void access$400(VineRecorder x0) {
            x0.ensureLowProfileStatusBar();
                }

        static  View access$500(VineRecorder x0) {
            return x0.mThumbnailList;
                }

        static  SdkVideoView access$600(VineRecorder x0) {
            return x0.mVideoTexture;
                }

        static  RelativeLayout.LayoutParams access$700(VineRecorder x0) {
            return x0.mVideoViewParams;
                }

        static  void access$800(VineRecorder x0) {
            x0.refreshFullPreview();
                }

        static  SegmentEditorAdapter access$900(VineRecorder x0) {
            return x0.mEditorAdapter;
                }

                
        class RunnableC04901 implements Runnable {
            RunnableC04901() {
                    }

                    @Override 
            public void run() {
                VineRecorder.this.mPluginManager.onCameraReady(VineRecorder.this.mFrontFacing, VineRecorder.this.setAutoFocusing(true));
                    }
                }

       public VineRecorder(RecordSessionVersion version, BaseRecorderPluginManager manager, boolean startWithEditMode, Point screenSize, boolean returnToPreview, boolean hasPreviewedAlready, RecordingFile file, Activity activity, ViewGroup videoViewContainer, int recordingOptionsRowId, int dragSortWidgetId, int progressViewResourceId, int cameraViewResourceId, int finishButtonId, int topMaskId, int bottomMaskId, int lastSegmentString, int openCameraString, int playButtonContainerId, int thumbnailOverlayId, int rootLayoutId, int editorPadding, int editorButtonsHeight, int finishLoadingOverlayId, int previewLoadingOverlayId, int progressOverlay, Drawable progressDrawable, RegularVineRecorder.DeviceIssueStringGetter deviceNotSupportedString, boolean startWithFrontFacingCamera, boolean isMessaging, MediaActionSound mediaActionSound, CharSequence importingString, CharSequence... messages) {
           super(isMessaging ? 66 : 1000, manager, screenSize, activity, progressViewResourceId, cameraViewResourceId, topMaskId, bottomMaskId, openCameraString, rootLayoutId, progressDrawable, deviceNotSupportedString, startWithFrontFacingCamera, version, importingString, messages);
            this.mLastPlayingSegment = new RecordSegment(0L, 30);
            this.onCameraReadyRunnable = new RunnableC04901();
           this.mPlaySound = false;
           this.mFinishClicker = new ViewOnClickListenerC05053();
           this.mAnimateEditModeControlsInRunnable = new RunnableC05075();
           this.mSegmentHighlightRunnable = new SegmentHighlightRunnable();
          this.mVideoPreviewTrimPositionChecker = new RunnableC049312();
          this.mChangeProgressOverlayVisibilityRunnable = new RunnableC050422();
           this.mReturnToPreview = returnToPreview;
           this.mHasPreviewedAlready = hasPreviewedAlready;
           this.mStartWithEditMode = startWithEditMode;
           this.mInitiallyStartedWithEditMode = startWithEditMode;
           this.mEditorButtonsHeight = editorButtonsHeight;
           this.mVideoViewContainer = videoViewContainer;
           this.mShortAnimTime = this.mActivity.getResources().getInteger(android.R.integer.config_shortAnimTime);
           this.mThumbnailPadding = editorPadding;
           this.mFinishButtonId = finishButtonId;
           this.mFinishLastSegmentString = activity.getText(lastSegmentString);
           this.mThumbnailOverlayId = thumbnailOverlayId;
           this.mFinishLoadingOverlayId = finishLoadingOverlayId;
           this.mPlayButtonContainerId = playButtonContainerId;
           this.mPreviewLoadingOverlayId = previewLoadingOverlayId;
           this.mRecordingOptionsRowId = recordingOptionsRowId;
           this.mDragSortWidgetId = dragSortWidgetId;
           this.mProgressOverlayId = progressOverlay;
           this.mMediaActionSound = mediaActionSound;
           Locale locale = Locale.getDefault();
           this.mPlaySound = (locale != null && JAPANESE_LANG.equals(locale.getLanguage())) || this.mActivity.getResources().getConfiguration().mcc == JAPANESE_MCC;
           swapSession("Init", file);
           this.mIsMessaging = isMessaging;
                }

                
       class C05012 extends BasicVineRecorder.FinishProcessRunnable {
           C05012() {
                        super();
                    }

                    @Override 
           public boolean doNotDeleteSession(boolean isFinishing) {
                        return isFinishing || VineRecorder.access$000(VineRecorder.this);
                    }

                    @Override 
           public boolean isCompleteSession(boolean wasRecordingStarted) {
                        return wasRecordingStarted || VineRecorder.access$000(VineRecorder.this);
                    }

                    @Override 
           protected void onPreCompleteSession(boolean wasRecordingStarted) {
               if (wasRecordingStarted) {
                   VineRecorder.access$102(VineRecorder.this, new RecordSegment(VineRecorder.this.mCurrentDurationMs, 30));
                        }
                    }
                }

                @Override 
       protected BasicVineRecorder.FinishProcessRunnable getFinishProcessRunnable() {
           return new C05012();
                }

                @Override 
       public RecordingFile swapSession(String tag, RecordingFile file) {
           if (file == null) {
               return null;
                    }
           RecordingFile ret = super.swapSession(tag, file);
           this.mPluginManager.onSessionSwapped();
           this.mPluginManager.onSegmentDataChanged(this.mEditedSegments);
           return ret;
                }

                
       class ViewOnClickListenerC05053 implements View.OnClickListener {
           ViewOnClickListenerC05053() {
                    }

                    @Override 
           public void onClick(View v) {
               VineRecorder.this.setHasPreviewedAlreadyIfNeeded();
               VineRecorder.this.finish("finishClicker");
                    }
                }

       public void switchFlash() {
           this.mVideoController.switchFlash();
                }

       public void modifyZoom(boolean zoomIn) {
           if (this.mVideoController.isRecordingStarted()) {
               this.mVideoController.modifyZoom(zoomIn);
                    }
                }

       public void stopZoom(Runnable updateRunnable) {
           if (this.mVideoController.isRecordingStarted()) {
               this.mVideoController.stopZoom();
               this.mHandler.removeCallbacks(updateRunnable);
                    }
                }

                @Override 
       public boolean release() {
           this.mLastPlayingSegment = null;
           return super.release();
                }

                @Nullable
       public ViewGroup getVideoContainer() {
           if (this.mVideoTexture != null) {
               return (ViewGroup) this.mVideoTexture.getParent();
                    }
                    return null;
                }

                
       class RunnableC05064 implements Runnable {
           RunnableC05064() {
                    }

                    @Override 
           public void run() {
               if (VineRecorder.access$200(VineRecorder.this) != null) {
                   VineRecorder.access$200(VineRecorder.this).setAlpha(0.0f);
                   VineRecorder.access$200(VineRecorder.this).setVisibility(0);
                   VineRecorder.access$200(VineRecorder.this).animate().alpha(1.0f).setDuration(250L).start();
                        }
               if (VineRecorder.this.mTopMaskView != null) {
                   VineRecorder.this.mTopMaskView.setAlpha(0.0f);
                   VineRecorder.this.mTopMaskView.setVisibility(0);
                   VineRecorder.this.mTopMaskView.animate().alpha(1.0f).setDuration(250L).start();
                        }
               if (VineRecorder.this.mProgressView != null) {
                   VineRecorder.this.mProgressView.setTranslationY(VineRecorder.access$300(VineRecorder.this));
                   VineRecorder.this.mProgressView.setScaleY(0.0f);
                   VineRecorder.this.mProgressView.setVisibility(0);
                   VineRecorder.this.mProgressView.animate().scaleY(1.0f).translationY(0.0f).setDuration(250L).start();
                        }
                    }
                }

       private void animateCaptureControlsIn() {
           this.mHandler.postDelayed(new RunnableC05064(), 250L);
           if (this.mCameraView != null) {
               this.mCameraView.setVisibility(0);
                    }
                }

       private void animateCaptureControlsOut() {
                    ViewPropertyAnimator am;
                    ViewPropertyAnimator am2;
                    ViewPropertyAnimator am3;
           View v = this.mProgressView;
           if (v != null && (am3 = v.animate()) != null) {
               am3.scaleY(0.0f).translationY(this.mEditorButtonsHeight).setDuration(250L).setListener(new ViewGoneAnimationListener(v)).start();
                    }
           View v2 = this.mTopMaskView;
           if (v2 != null && (am2 = v2.animate()) != null) {
               am2.alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(v2)).start();
                    }
           View v3 = this.mRecordingOptions;
           if (v3 != null && (am = v3.animate()) != null) {
               am.alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(v3)).start();
                    }
           if (this.mCameraView != null) {
               this.mCameraView.setVisibility(4);
                    }
                }

       private void hideCaptureControls() {
           if (this.mProgressView != null) {
               this.mProgressView.setVisibility(4);
                    }
           if (this.mCameraView != null) {
               this.mCameraView.setVisibility(4);
                    }
           if (this.mTopMaskView != null) {
               this.mTopMaskView.setVisibility(4);
                    }
           if (this.mRecordingOptions != null) {
               this.mRecordingOptions.setVisibility(4);
                    }
                }

       public void higlightProgressView(float start, float end, int color) {
           if (this.mProgressView != null) {
               this.mProgressView.setSelectedColor(color);
               this.mProgressView.setSelectedSection(start, end);
                    }
                }

                
       class RunnableC05075 implements Runnable {
           RunnableC05075() {
                    }

                    @Override 
           public void run() {
               VineRecorder.this.mPluginManager.onAnimateEditModeControlsInUI();
               VineRecorder.access$400(VineRecorder.this);
               if (VineRecorder.access$500(VineRecorder.this) != null) {
                   int thumbnailListHeight = VineRecorder.access$500(VineRecorder.this).getHeight();
                   VineRecorder.access$500(VineRecorder.this).setTranslationY((-thumbnailListHeight) / 2);
                   VineRecorder.access$500(VineRecorder.this).setScaleY(0.0f);
                   VineRecorder.access$500(VineRecorder.this).setVisibility(0);
                   VineRecorder.access$500(VineRecorder.this).animate().scaleY(1.0f).translationY(0.0f).setDuration(250L).start();
                        }
                    }
                }

       private void ensureLowProfileStatusBar() {
           Activity activity = getActivity();
           if (activity != null) {
               ViewUtil.setLowProfileSystemUiMode(activity);
                    }
                }

       private void animateEditModeControlsIn() {
           this.mHandler.postDelayed(this.mAnimateEditModeControlsInRunnable, 250L);
           if (this.mPreviewLoadingOverlay != null) {
               this.mPreviewLoadingOverlay.setVisibility(4);
               this.mPreviewLoadingOverlay.setAlpha(0.0f);
                    }
           if (this.mPlayButtonContainer != null) {
               this.mPlayButtonContainer.setVisibility(4);
               this.mPlayButtonContainer.setAlpha(0.0f);
                    }
           if (this.mThumbnailOverlay != null) {
               this.mThumbnailOverlay.setVisibility(4);
                    }
                }

       private void animateEditModeControlsOut(boolean hideVideo) {
           this.mHandler.removeCallbacks(this.mAnimateEditModeControlsInRunnable);
           if (this.mEditorAdapter != null && this.mThumbnailOverlay != null && this.mPlayButtonContainer != null && this.mPreviewLoadingOverlay != null && this.mThumbnailList != null && this.mPlayButton != null) {
               this.mPluginManager.onAnimateEditModeControlsOutUI();
               this.mThumbnailOverlay.animate().alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(this.mThumbnailOverlay)).start();
               this.mPlayButtonContainer.animate().alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(this.mPlayButtonContainer)).start();
               this.mPreviewLoadingOverlay.animate().alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(this.mPreviewLoadingOverlay)).start();
               this.mThumbnailList.animate().scaleY(0.0f).translationY((-this.mThumbnailList.getHeight()) / 2).setDuration(250L).setListener(new ViewGoneAnimationListener(this.mThumbnailList)).start();
               this.mPlayButtonContainer.animate().alpha(0.0f).setDuration(250L).start();
               this.mDragSortWidget.setSelection(-1);
                    }
           if (hideVideo && this.mVideoTexture != null) {
               this.mVideoTexture.setVisibility(4);
                    }
                }

       public void startEditMode(boolean wasRecordingAlready) {
           adjustEditBoundaries();
           if (!this.mReturnToPreview) {
               animateCaptureControlsOut();
                    } else {
               hideCaptureControls();
                    }
           animateEditModeControlsIn();
           this.mLastSelectedPosition = -1;
           this.mLastPlayingSegment = null;
           if (!wasRecordingAlready) {
               this.mEditorAdapter = new SegmentEditorAdapter(this.mEditedSegments, this.mActivity, this.mDragSortWidget, this.density);
                    } else {
               this.mEditorAdapter = new SegmentEditorAdapter(this.mEditorAdapter);
                    }
           if (this.mDragSortWidget != null) {
               this.mDragSortWidget.setAdapter(this.mEditorAdapter);
               this.mEditorAdapter.notifyDataSetChanged();
               this.mDragSortWidget.setSelection(-1);
               this.mDragSortWidget.setFocused(0);
                    }
           this.mPluginManager.onStartEditMode();
           refreshVideoView(null, true, true, false, "startEditMode");
                }

       public class SegmentHighlightRunnable implements Runnable {
                    public WeakReference<RecordSegment> target;

           public SegmentHighlightRunnable() {
                    }

                    @Override 
           public void run() {
               if (this.target != null) {
                   RecordSegment t = this.target.get();
                   if (t != null) {
                       VineRecorder.this.playPreview(t, true, false, "segmentHighlight");
                       return;
                            }
                   return;
                        }
               if (!VineRecorder.this.mVideoController.isPreviewing()) {
                   VineRecorder.this.mVideoController.openDefaultCamera(VineRecorder.this.mFrontFacing, true);
                        }
                    }
                }

       public boolean highlightSegment(int index, int color) {
                    RecordSession session;
           if (!isCurrentlyRecording() && (session = getSesion()) != null) {
               int duration = 0;
               ArrayList<RecordSegment> segments = this.mEditedSegments;
               RecordSegment target = segments.get(index);
               long start = 0;
               long end = 0;
                        for (RecordSegment segment : segments) {
                   if (!segment.removed) {
                       if (target == segment) {
                                    start = duration;
                           end = start + ((long) segment.getDurationMs());
                                }
                       duration += segment.getDurationMs();
                            }
                        }
               SLog.m467i("Segment {} highlighted: {} {} ", Integer.valueOf(index), Long.valueOf(start), Long.valueOf(end));
               float max = session.getConfig().maxDuration;
               if (this.mProgressView != null) {
                   if (duration > 0 && Math.floor(end / ((long) duration)) == 1.0d && end < this.mProgressView.getProgressRatio()) {
                       higlightProgressView(start / max, this.mProgressView.getProgressRatio(), color);
                            } else {
                       higlightProgressView(start / max, end / max, color);
                            }
                        }
               this.mHandler.removeCallbacks(this.mSegmentHighlightRunnable);
               this.mSegmentHighlightRunnable.target = new WeakReference<>(target);
               this.mHandler.postDelayed(this.mSegmentHighlightRunnable, 500L);
               return true;
                    }
           return false;
                }

       public void unhighlightSegments() {
           if (this.mProgressView != null) {
               this.mProgressView.setSelectedSection(0.0f, 0.0f);
                    }
           if (this.mCameraView != null) {
               this.mCameraView.setVisibility(0);
                    }
           pausePreview(false);
           if (this.mVideoTexture != null) {
               this.mVideoTexture.setVisibility(4);
                    }
           this.mHandler.removeCallbacks(this.mSegmentHighlightRunnable);
           this.mSegmentHighlightRunnable.target = null;
           this.mHandler.postDelayed(this.mSegmentHighlightRunnable, 250L);
                }

       public void deleteLastSegment() {
           if (this.mEditedSegments.size() > 0) {
               stopProgressTimer();
               int lastIndex = 0;
                        for (int i = 0; i < this.mEditedSegments.size(); i++) {
                   if (!this.mEditedSegments.get(i).removed) {
                       lastIndex = i;
                            }
                        }
               this.mEditedSegments.get(lastIndex).removed = true;
               RecordSession session = this.mCurrentRecordingFile.getSession();
               while (this.mVideoController.wasJustGrabbingDataForSegment()) {
                   SLog.m464i("Waiting for audio recorder to settle down.");
                            try {
                       Thread.sleep(RecordConfigUtils.AUDIO_WAIT_THRESHOLD_NS / 2);
                            } catch (InterruptedException e) {
                            }
                        }
                        try {
                   this.mToRemove.addAll(RecordSegment.applyEditedChanges(this.mCurrentRecordingFile.folder, session, this.mEditedSegments));
                   swapTimestampsFromSegments(session, this.mEditedSegments);
                   this.mCurrentRecordingFile.isDirty = this.mCurrentRecordingFile.isSavedSession || this.mEditedSegments.size() > 0;
                   startProgressTimer();
                   SLog.m465i("Segment {} deleted.", Integer.valueOf(lastIndex));
                        } catch (IOException e2) {
                   throw new RecordSegmentIOException(e2);
                        }
                    }
                }

       public void setEditMode(View view, boolean on, boolean discard) {
                    int size;
                    Object[] objArr = new Object[2];
           objArr[0] = on ? "on" : "off";
                    objArr[1] = discard ? VineUploadService.ACTION_DISCARD : "save";
           CrashUtil.log("Set edit mode {} {}.", objArr);
           boolean hasData = this.mCurrentRecordingFile != null && this.mCurrentRecordingFile.hasData();
           if (!this.mPluginManager.onSetEditMode(on, hasData)) {
               boolean wasEditingAlready = this.mEditing;
               this.mEditing = on;
               if (hasData) {
                   if (this.mEditing) {
                       this.mStartWithEditMode = false;
                       this.mHandler.post(this.mVideoPreviewTrimPositionChecker);
                       if (canKeepRecording()) {
                           if (this.mVideoController != null && this.mVideoController.isRecordingStarted()) {
                               stop("editMode", new RunnableC05086(wasEditingAlready), true, false);
                               return;
                                    } else {
                               startEditMode(wasEditingAlready);
                               return;
                                    }
                                }
                       this.mEditing = false;
                       return;
                            }
                   this.mHandler.removeCallbacks(this.mVideoPreviewTrimPositionChecker);
                   releaseSegmentChangeDetector();
                   if (this.mRefreshPreviewTask != null) {
                       this.mRefreshPreviewTask.cancel(true);
                            }
                   if (this.mVideoTexture != null) {
                       this.mVideoTexture.stopPlayback();
                       this.mVideoTexture.setAutoPlayOnPrepared(false);
                            }
                   if (this.mDragSortWidget != null) {
                       this.mDragSortWidget.cleanUp();
                            }
                   if (!discard) {
                       if (commitChanges() && (size = this.mEditedSegments.size()) > 0 && this.mVideoController != null) {
                           this.mVideoController.makePreview(this.mEditedSegments.get(size - 1), true, false);
                                }
                       if (this.mCurrentRecordingFile != null) {
                           this.mCurrentRecordingFile.invalidateGhostThumbnail();
                                }
                            }
                   animateEditModeControlsOut(true);
                   animateCaptureControlsIn();
                   invalidateFinishButton();
                   setHasPreviewedAlreadyIfNeeded();
                   this.mReturnToPreview = false;
                   this.mResumeTask = new RegularVineRecorder.OnResumeTask(view, "Set EditMode.");
                   this.mResumeTask.execute(new Void[0]);
                   this.mEditorAdapter = null;
                   return;
                        }
               this.mEditing = false;
                    }
                }

                
       class RunnableC05086 implements Runnable {
                    final  boolean val$wasEditingAlready;

           RunnableC05086(boolean z) {
                        this.val$wasEditingAlready = z;
                    }

                    @Override 
           public void run() {
               VineRecorder.this.startEditMode(this.val$wasEditingAlready);
                    }
                }

       public void setEditMode(boolean on, boolean discard) {
           setEditMode(null, on, discard);
                }

                @Override 
       protected void setHasPreviewedAlreadyIfNeeded() {
           this.mHasPreviewedAlready = this.mCurrentDurationMs >= ((long) this.mSession.getConfig().maxDuration);
                }

       public void addExtraSegment(RecordSegment segment, Bitmap lastBitmap) {
           this.mCurrentDurationMs += (long) segment.getDurationMs();
           long durationUs = this.mCurrentDurationMs * 1000;
           this.mVideoController.setAudioTimestampUs((int) durationUs);
           this.mVideoController.setVideoTimeStampUs((int) durationUs);
           this.mVideoController.onExternalClipAdded(segment.getDurationMs() * 1000);
           this.mRecordingFileDuration = (int) this.mCurrentDurationMs;
           segment.setCameraSetting(this.mVideoController.getCameraSetting());
           changeProgress(this.mCurrentDurationMs);
           this.mSession.add(segment);
           this.mAddedSegments.add(segment);
           this.mEditedSegments.add(segment);
           this.mCurrentRecordingFile.isDirty = true;
           this.mPluginManager.onOfferLastFrame(lastBitmap, getCurrentCameraSetting());
           this.mPluginManager.onSegmentDataChanged(this.mEditedSegments);
           this.mHandler.post(new RunnableC05097());
                }

                
       class RunnableC05097 implements Runnable {
           RunnableC05097() {
                    }

                    @Override 
           public void run() {
               VineRecorder.this.invalidateFinishButton();
                    }
                }

       private boolean commitChanges() {
                    boolean lastChanged = false;
           SegmentEditorAdapter adapter = this.mEditorAdapter;
           if (adapter != null) {
               adapter.commitDelete();
               RecordSessionManager.cleanUpSegmentData(this.mCurrentRecordingFile.folder, adapter.cleanUpForDeletion());
               int size = this.mEditedSegments.size();
               RecordSegment lastSegment = null;
               if (size > 0) {
                   RecordSegment lastSegment2 = this.mEditedSegments.get(size - 1);
                            lastSegment = lastSegment2;
                        }
               int modifiedSize = adapter.getData().size();
               if (modifiedSize > 0) {
                   if (lastSegment != adapter.getData().get(modifiedSize - 1)) {
                                lastChanged = true;
                            }
                        } else if (lastSegment != null) {
                            lastChanged = true;
                        }
               this.mEditedSegments.clear();
               this.mEditedSegments.addAll(adapter.getData());
               this.mEditedSegments.addAll(adapter.getDeleted());
                        for (RecordSegment segment : adapter.getDeleted()) {
                   segment.removed = true;
                        }
               RecordSession session = this.mCurrentRecordingFile.getSession();
                        try {
                   this.mToRemove.addAll(RecordSegment.applyEditedChanges(this.mCurrentRecordingFile.folder, session, this.mEditedSegments));
                   swapTimestampsFromSegments(session, this.mEditedSegments);
                   this.mCurrentRecordingFile.isDirty = true;
                   SLog.m447d("Changes commited, last segment has changed? {}.", Boolean.valueOf(lastChanged));
                        } catch (IOException e) {
                   throw new RecordSegmentIOException(e);
                        }
                    }
           return lastChanged;
                }

       private void adjustEditBoundaries() {
                    RelativeLayout.LayoutParams thumbListParams;
           if (this.mVideoTexture != null) {
               this.mVideoViewParams = (RelativeLayout.LayoutParams) this.mVideoTexture.getLayoutParams();
               this.mVideoViewParams.width = this.mSize.x;
               this.mVideoViewParams.height = this.mSize.x;
                    }
           if (this.mPlayButtonContainer != null) {
               this.mPlayButtonContainer.setLayoutParams(this.mVideoViewParams);
                    }
           if (this.mThumbnailOverlay != null) {
               this.mThumbnailOverlay.setLayoutParams(this.mVideoViewParams);
                    }
           if (this.mPreviewLoadingOverlay != null) {
               this.mPreviewLoadingOverlay.setLayoutParams(this.mVideoViewParams);
                    }
           if (this.mThumbnailList != null) {
               thumbListParams = (RelativeLayout.LayoutParams) this.mThumbnailList.getLayoutParams();
               thumbListParams.setMargins(0, this.mEditorButtonsHeight + this.mSize.x + this.mThumbnailPadding, 0, 0);
                    } else {
               thumbListParams = null;
                    }
           if (this.mActivity != null) {
               this.mActivity.runOnUiThread(new RunnableC05108(thumbListParams));
                    }
                }

                
       class RunnableC05108 implements Runnable {
                    final  RelativeLayout.LayoutParams val$thumbListParams;

           RunnableC05108(RelativeLayout.LayoutParams layoutParams) {
                        this.val$thumbListParams = layoutParams;
                    }

                    @Override 
           public void run() {
               View view = VineRecorder.access$600(VineRecorder.this);
               if (view != null) {
                   view.setLayoutParams(VineRecorder.access$700(VineRecorder.this));
                        }
               View view2 = VineRecorder.access$500(VineRecorder.this);
               if (view2 != null) {
                   view2.setLayoutParams(this.val$thumbListParams);
                        }
                    }
                }

       public boolean isEditing() {
                    return this.mEditing && this.mEditorAdapter != null;
                }

       public void saveSession(Runnable onSaveSessionCompleteRunnable, boolean releaseCamera) {
           this.mCanKeepRecording = false;
           stop("saveSession", onSaveSessionCompleteRunnable, releaseCamera, true);
                }

                @Override 
       public void onSelectionChanged(int position, boolean listViewClick) {
           this.mLastSelectedPosition = position;
           SegmentEditorAdapter editor = this.mEditorAdapter;
           if (editor != null) {
               if (position != -1 && position < editor.getCount()) {
                   RecordSegment toPlay = (RecordSegment) editor.getItem(position);
                   animateTopButtons(0, toPlay);
                   if (listViewClick) {
                       pausePreview(false);
                       playPreview(toPlay, true, true, "SelectionChanged");
                            } else {
                       pausePreview(true);
                            }
                   editor.commitDelete();
                   this.mPluginManager.onIndividualSegmentClicked(toPlay, this.mVideoTexture);
                   return;
                        }
               if (editor.getCount() > 0) {
                   animateTopButtons(2, null);
                   pausePreview(true);
                   if (this.mDragSortWidget != null) {
                       this.mDragSortWidget.setFocused(0);
                            }
                   this.mPluginManager.onIndividualSegmentClicked(null, this.mVideoTexture);
                   showThumbnailOverlay(null);
                   return;
                        }
               animatePlayButton(false);
               animatePreviewSpinner(false);
               hideThumbnailOverlay();
               animateTopButtons(2, null);
               this.mVideoTexture.setVisibility(8);
               this.mPluginManager.onIndividualSegmentClicked(null, this.mVideoTexture);
                    }
                }

       public void showThumbnailOverlay(RecordSegment segment) {
           if (this.mThumbnailOverlay != null && (this.mThumbnailOverlay.getVisibility() != 0 || segment != this.mThumbnailSegment)) {
               this.mThumbnailOverlay.setVisibility(0);
               this.mThumbnailOverlay.setAlpha(1.0f);
               if (segment == null && this.mEditorAdapter.getCount() > 0) {
                   segment = (RecordSegment) this.mEditorAdapter.getItem(0);
                        }
               if (segment != null) {
                   Drawable thumbDrawable = segment.getDrawableCopy(this.mActivity);
                   this.mThumbnailOverlay.setImageDrawable(thumbDrawable);
                        }
                    }
           this.mThumbnailSegment = segment;
                }

                
       class RunnableC05119 implements Runnable {
           RunnableC05119() {
                    }

                    @Override 
           public void run() {
               VineRecorder.this.hideThumbnailOverlay();
                    }
                }

       public void hideThumbnailOverlayDelayed() {
           getHandler().postDelayed(new RunnableC05119(), REMOVE_THUMBNAIL_MILLIS);
                }

       public void hideThumbnailOverlay() {
           if (this.mThumbnailOverlay != null && this.mThumbnailOverlay.getVisibility() != 4) {
               this.mThumbnailOverlay.animate().setDuration(this.mShortAnimTime).alpha(0.0f).setListener(new ViewGoneAnimationListener(this.mThumbnailOverlay)).start();
               this.mThumbnailOverlay.setImageDrawable(null);
                    }
                }

                @Override 
       public void onPrepared(VideoViewInterface view) {
           if (this.mDragSortWidget != null) {
               if (this.mDragSortWidget.hasFloatView()) {
                   pausePreview(false);
                        }
               hideThumbnailOverlayDelayed();
               animatePreviewSpinner(false);
                    }
                }

       public boolean canPickUpFloatView() {
                    return (this.mPreviewLoadingOverlay == null || this.mPreviewLoadingOverlay.getVisibility() == 0) ? false : true;
                }

       public void pausePreview(boolean showPlayButton) {
           animatePlayButton(showPlayButton);
           if (this.mRefreshPreviewTask != null) {
               this.mRefreshPreviewTask.cancel(true);
               this.mRefreshPreviewTask = null;
                    }
           if (this.mVideoTexture != null) {
               this.mVideoTexture.pause();
               this.mVideoTexture.setAutoPlayOnPrepared(false);
                    }
                }

       public void playPreview(RecordSegment toPlay, boolean forceRefresh, boolean editMode, String tag) {
           animatePlayButton(false);
           refreshVideoView(toPlay, forceRefresh, editMode, toPlay != null, tag);
                }

       public boolean isEditingDirty() {
                    return isEditing() && !this.mEditorAdapter.compareTo(this.mEditedSegments);
                }

       public void animateTopButtons(int animation, RecordSegment segment) {
                    switch (animation) {
                        case 0:
                   this.mPluginManager.onIndividualSegmentClicked(segment, this.mVideoTexture);
                            break;
                        case 2:
                   this.mPluginManager.onIndividualSegmentClicked(null, this.mVideoTexture);
                            break;
                    }
                }

       public void animatePlayButton(boolean visible) {
           if (this.mPlayButton != null && this.mPlayRefreshButton != null && this.mPlayButtonContainer != null) {
               this.mPlayButtonContainer.animate().cancel();
               if (!visible) {
                   this.mPlayButtonContainer.animate().alpha(0.0f).setDuration(this.mShortAnimTime).setListener(new ViewGoneAnimationListener(this.mPlayButtonContainer)).start();
                   return;
                        }
               if (this.mDragSortWidget.getSelection() != -1) {
                   this.mPlayButton.setVisibility(8);
                   this.mPlayRefreshButton.setVisibility(0);
                        } else {
                   this.mPlayButton.setVisibility(0);
                   this.mPlayRefreshButton.setVisibility(8);
                        }
               this.mPlayButtonContainer.setVisibility(0);
               this.mPlayButtonContainer.animate().alpha(1.0f).setDuration(this.mShortAnimTime).start();
                    }
                }

       private void animatePreviewSpinner(boolean visible) {
           animateSpinner(this.mPreviewLoadingOverlay, visible);
                }

       private void animateSpinner(View view, boolean visible) {
           if (view != null) {
               view.animate().cancel();
               if (!visible) {
                   view.animate().alpha(0.0f).setDuration(this.mShortAnimTime).setListener(new ViewGoneAnimationListener(view)).start();
                   return;
                        }
               view.setVisibility(0);
               view.setAlpha(0.0f);
               view.animate().alpha(1.0f).setDuration(this.mShortAnimTime).start();
                    }
                }

       public void reverseFrames() {
           if (isEditing()) {
               this.mEditorAdapter.reverse();
                    }
                }

                @Override 
       public int getCurrentlyPlayingPosition() {
           if (this.mLastSelectedPosition == -1 && this.mSegmentChangeDetector != null) {
               if (this.mSegmentChangeDetector.lastFirstItem >= 0 && this.mEditorAdapter != null && this.mSegmentChangeDetector.lastFirstItem < this.mEditorAdapter.getCount()) {
                   return this.mSegmentChangeDetector.lastFirstItem;
                        }
               return 0;
                    }
           return this.mLastSelectedPosition;
                }

       public void doOneFrame() {
           if (this.mCurrentRecordingFile != null && !isEditing() && this.mAutoFocusing && startRelativeTime()) {
               this.mHandler.postDelayed(new RunnableC049110(), 25L);
                    }
                }

                
       class RunnableC049110 implements Runnable {
           RunnableC049110() {
                    }

                    @Override 
           public void run() {
               VineRecorder.this.endRelativeTime();
                    }
                }

                @Override 
       protected void onResumeTaskComplete() {
           ensureLowProfileStatusBar();
                }

       public void doTime(long time) {
           if (this.mCurrentRecordingFile != null && !isEditing() && this.mAutoFocusing && startRelativeTime()) {
               this.mHandler.postDelayed(new RunnableC049211(), time);
                    }
                }

                
       class RunnableC049211 implements Runnable {
           RunnableC049211() {
                    }

                    @Override 
           public void run() {
               VineRecorder.this.endRelativeTime();
                    }
                }

                @Override 
       public void onResume(String tag) {
                    boolean z = true;
           boolean canKeepRecording = canKeepRecording();
                    Object[] objArr = new Object[4];
                    objArr[0] = Boolean.valueOf(this.mDelayDialog);
                    objArr[1] = Boolean.valueOf(canKeepRecording);
                    objArr[2] = Boolean.valueOf(isEditing());
                    objArr[3] = Boolean.valueOf(this.finalFile != null);
           CrashUtil.log("Resume VineRecorder: delayDialog: {} canKeepRecording: {} isEditing: {} NullFinalFile: {}.", objArr);
           StaticSizeVideoView videoTexture = new StaticSizeVideoView(this.mActivity);
           videoTexture.setUseVinePlayer(false);
           videoTexture.setSize(this.mSize.x, this.mSize.x);
           this.mVideoViewContainer.addView(videoTexture, 0);
           this.mVideoTexture = videoTexture;
           if (canKeepRecording) {
               if (!isEditing()) {
                   initPreviewSurface();
                   if (this.mStartWithEditMode) {
                       setEditMode(true, false);
                       return;
                            }
                   this.mResumeTask = getOnResumeTask(null, "OnResume_" + tag);
                   BasicVineRecorder.OnResumeTask onResumeTask = this.mResumeTask;
                            if (!this.mDelayDialog && (!this.mCurrentRecordingFile.isSavedSession || !this.mNeverResumedRecorder)) {
                                z = false;
                            }
                            onResumeTask.showDialogDelayed = z;
                   this.mResumeTask.execute(new Void[0]);
                   return;
                        }
               setEditMode(true, false);
               return;
                    }
           if (this.finalFile != null) {
               this.mOnCompleteConsumer.run();
               return;
                    }
           if (this.mStartProgressDialog != null) {
               this.mStartProgressDialog.setMessage(this.mFinishLastSegmentString);
               if (!this.mDelayDialog) {
                            try {
                       this.mStartProgressDialog.show();
                            } catch (WindowManager.BadTokenException e) {
                            }
                        }
                    }
                }

                @Override 
       public void setDiscardChanges(boolean discardChanges) {
           this.mDiscardChanges = discardChanges;
                }

      public void refreshVideoView(RecordSegment segment, boolean forceRefresh, boolean editMode, boolean ignoreTrim, String tag) {
          releaseSegmentChangeDetector();
          if (editMode) {
              this.mSegmentChangeDetector = new SegmentChangeDetector();
              this.mSegmentChangeThread = new Thread(this.mSegmentChangeDetector);
              this.mSegmentChangeThread.start();
                    }
          if (this.mPlayButtonOnClickListener != null) {
              this.mPlayButtonOnClickListener.editMode = editMode;
                    }
          if (editMode && this.mEditorAdapter.getCount() == 0) {
              hideThumbnailOverlay();
              if (this.mPlayButton != null) {
                  this.mPlayButton.setVisibility(8);
                        }
              if (this.mVideoTexture != null) {
                  this.mVideoTexture.setVisibility(8);
                  animatePreviewSpinner(false);
                  return;
                        }
              return;
                    }
          if (this.mLastPlayingSegment != segment || forceRefresh) {
              if (!editMode || this.mEditorAdapter.getCount() > 0) {
                  showThumbnailOverlay(segment);
                        }
              if (!editMode) {
                  if (this.mCameraView != null) {
                      this.mCameraView.setVisibility(4);
                            }
                  if (this.mVideoController != null) {
                      this.mVideoController.releaseCameraAndPreview(MessageFormatter.toStringMessage("refreshVideo view {}", tag));
                            } else {
                      CrashUtil.log("WARNING: it looks like the controller is already released, but we may still have camera and preview in use.");
                            }
                        }
              if (segment == null && editMode && this.mDragSortWidget != null) {
                  this.mDragSortWidget.setFocused(0);
                        }
              if (this.mRefreshPreviewTask != null) {
                  this.mRefreshPreviewTask.cancel(true);
                        }
              if (segment != null && segment.removed) {
                  SLog.m464i("Do not start refresh task because segment is removed.");
                  return;
                        } else {
                  if (this.mVideoTexture == null) {
                      SLog.m464i("Do not start refresh task because onPause probably happened.");
                      return;
                            }
                  animatePreviewSpinner(true);
                  this.mRefreshPreviewTask = new RefreshPreviewTask(segment, editMode, ignoreTrim);
                  this.mRefreshPreviewTask.execute(new Void[0]);
                  return;
                        }
                    }
          if (this.mVideoTexture != null) {
              this.mVideoTexture.setAutoPlayOnPrepared(true);
              if (this.mVideoTexture.isPaused()) {
                  this.mVideoTexture.resume();
                        }
                    }
          hideThumbnailOverlayDelayed();
          animatePreviewSpinner(false);
                }

      public boolean isMessaging() {
          return this.mIsMessaging;
                }

      public boolean isFlashOn() {
          return this.mVideoController.isFlashOn();
                }

      public boolean isWithinDurationPercentageOf(double percentage) {
                    return (this.mSession == null || this.mSession.getConfig() == null || ((double) this.mCurrentDurationMs) >= ((double) this.mSession.getConfig().maxDuration) * percentage) ? false : true;
                }

      public boolean setAudioTrim(boolean trim) {
          if (this.mVideoController == null) {
                        return false;
                    }
          this.mVideoController.setAudioTrim(trim);
          return true;
                }

      public SegmentEditorAdapter getEditorAdatper() {
          return this.mEditorAdapter;
                }

      public void changePlayButtonOnClickBehavior(boolean forceRefresh, boolean editMode) {
          this.mPlayButtonOnClickListener.forceRefresh = forceRefresh;
          this.mPlayButtonOnClickListener.editMode = editMode;
                }

      public void saveSegmentIfNeeded(RecordSegment segment) throws IOException {
          segment.saveDataToDiskIfNeeded(this.mCurrentRecordingFile.folder, this.mSession.getVideoData(), this.mSession.getAudioData(), segment);
                }

      private class RefreshPreviewTask extends AsyncTask<Void, Void, Void> {
                    private final boolean mEditMode;
                    private final boolean mIgnoreTrim;
                    private final RecordSegment mSegment;



          public RefreshPreviewTask(RecordSegment segment, boolean editMode, boolean ignoreTrim) {
              this.mSegment = segment;
              this.mEditMode = editMode;
              this.mIgnoreTrim = ignoreTrim;
                    }

                    
          protected Void doInBackground(Void... params) {
                        int percentage;
              if (!isCancelled()) {
                  if (this.mSegment != VineRecorder.access$100(VineRecorder.this)) {
                      if (this.mSegment == null) {
                          VineRecorder.access$800(VineRecorder.this);
                          return null;
                                }
                      long start = System.currentTimeMillis();
                      if (!this.mEditMode) {
                          while (VineRecorder.this.mVideoController.wasJustGrabbingDataForSegment()) {
                              SLog.m464i("Waiting for audio recorder to settle down.");
                                        try {
                                  Thread.sleep(RecordConfigUtils.AUDIO_WAIT_THRESHOLD_NS / 2);
                                        } catch (InterruptedException e) {
                                        }
                                    }
                          if (this.mSegment.getVideoData().size() == 0) {
                              SLog.m464i("Waiting for camera frame to come in one more time.");
                                        try {
                                  Thread.sleep(33L);
                                        } catch (InterruptedException e2) {
                                        }
                                    }
                          do {
                              percentage = this.mSegment.getVideoDataEncodedPercentage();
                              SLog.m465i("Segment video encoded percentage: {}.", Integer.valueOf(percentage));
                              if (percentage < 100) {
                                            try {
                                      Thread.sleep(200L);
                                            } catch (InterruptedException e3) {
                                      throw new RuntimeException(e3);
                                            }
                                        }
                          } while (percentage < 100);
                          if (this.mSegment.getVideoData().size() == 0) {
                              CrashUtil.log("Something wrong is happening, 0 video frames by the end of settling phase.");
                                    }
                                }
                      if (!isCancelled()) {
                          VineRecorder.this.mVideoController.makePreview(this.mSegment, false, this.mIgnoreTrim);
                                }
                      SLog.m465i("Preview waited for {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                      return null;
                            }
                  if (this.mSegment == null && VineRecorder.access$900(VineRecorder.this).hasInitialized) {
                      VineRecorder.access$800(VineRecorder.this);
                      return null;
                            }
                  return null;
                        }
              return null;
                    }

                    @Override 
          protected void onPreExecute() {
              super.onPreExecute();
              VineRecorder.access$1000(VineRecorder.this);
                    }

                    
          protected void onPostExecute(Void aVoid) {
                        String videoPath;
              SegmentEditorAdapter adapter = VineRecorder.access$900(VineRecorder.this);
              boolean segmentAdapterIsValid = adapter != null && adapter.getCount() > 0;
              SdkVideoView videoSurface = VineRecorder.access$600(VineRecorder.this);
              if (videoSurface != null) {
                  if (!isCancelled() && (!this.mEditMode || segmentAdapterIsValid)) {
                      ViewGroup group = (ViewGroup) videoSurface.getParent();
                      videoSurface.setVisibility(0);
                      if (group != null) {
                          group.setOnClickListener(VineRecorder.this.new PreviewVideoClickListener(this.mEditMode));
                                }
                      if (VineRecorder.access$1100(VineRecorder.this) != null) {
                          VineRecorder.access$1100(VineRecorder.this).setContentView(group, new Rect(0, VineRecorder.access$300(VineRecorder.this), VineRecorder.this.mSize.x, VineRecorder.access$300(VineRecorder.this) + VineRecorder.this.mSize.x));
                                }
                      VineRecorder.access$1000(VineRecorder.this);
                      VineRecorder.access$102(VineRecorder.this, this.mSegment);
                      if (this.mSegment == null) {
                          videoPath = VineRecorder.this.mCurrentRecordingFile != null ? VineRecorder.this.mCurrentRecordingFile.getPreviewVideoPath() : null;
                                } else {
                                    videoPath = this.mSegment.videoPath;
                                }
                      if (this.mSegment != null && this.mSegment.isTrimmed()) {
                          VineRecorder.access$1200(VineRecorder.this, videoPath, this.mSegment.getTrimmedVideoStartUs(), this.mSegment.getTrimmedAudioEndUs());
                                } else {
                          VineRecorder.access$1200(VineRecorder.this, videoPath, -1L, -1L);
                                }
                      VineRecorder.access$600(VineRecorder.this).setMute(this.mSegment != null && this.mSegment.isSilenced());
                            } else {
                      videoSurface.setVisibility(8);
                            }
                        }
              VineRecorder.access$1302(VineRecorder.this, null);
                    }

                    @Override 
          public void onCancelled() {
              VineRecorder.access$1400(VineRecorder.this, false);
                    }
                }

      public void setVideoTextureSeekPoints(long startUs, long endUs) {
          this.mVideoTextureSeekStartUs = startUs;
          this.mVideoTextureSeekEndUs = endUs;
                }

                
      class RunnableC049312 implements Runnable {
          RunnableC049312() {
                    }

                    @Override 
          public void run() {
                        int position;
                        int trimEndTimeMs;
              Handler handler = VineRecorder.this.getHandler();
              if (handler != null) {
                  if (VineRecorder.access$600(VineRecorder.this) != null) {
                                if (VineRecorder.access$1500(VineRecorder.this) >= 0 && VineRecorder.access$1600(VineRecorder.this) >= 0) {
                          trimEndTimeMs = ((int) VineRecorder.access$1500(VineRecorder.this)) / 1000;
                                } else {
                          trimEndTimeMs = -1;
                                }
                      position = VineRecorder.access$600(VineRecorder.this).getCurrentPosition();
                      if (position >= trimEndTimeMs && trimEndTimeMs > 0) {
                          VineRecorder.access$600(VineRecorder.this).seekTo((int) (VineRecorder.access$1600(VineRecorder.this) / 1000));
                          VineRecorder.access$600(VineRecorder.this).setAutoPlayOnPrepared(true);
                                }
                            } else {
                      position = -1;
                            }
                  handler.postDelayed(this, Math.max(66, 200 - (position % 200)));
                        }
                    }
                }

      private void preparePlayer(String path, long startStartUs, long startEndUs) {
          setVideoTextureSeekPoints(startStartUs, startEndUs);
          SdkVideoView videoTexture = this.mVideoTexture;
          videoTexture.setOnPreparedListener(new C049413(videoTexture));
          videoTexture.setOnCompletionListener(new C049514(videoTexture));
          if (path != null) {
              videoTexture.setVideoPath(path);
              videoTexture.setAutoPlayOnPrepared(true);
              videoTexture.start();
                    }
                }

                
      class C049413 implements VideoViewInterface.OnPreparedListener {
                    final  SdkVideoView val$videoTexture;

          C049413(SdkVideoView sdkVideoView) {
                        this.val$videoTexture = sdkVideoView;
                    }

                    @Override 
          public void onPrepared(VideoViewInterface view) {
              VineRecorder.access$600(VineRecorder.this).setVisibility(0);
                        if (VineRecorder.access$1600(VineRecorder.this) > 0) {
                  this.val$videoTexture.seekTo((int) (VineRecorder.access$1600(VineRecorder.this) / 1000));
                        }
              VineRecorder.access$1400(VineRecorder.this, false);
              VineRecorder.this.hideThumbnailOverlayDelayed();
                    }
                }

                
      class C049514 implements VideoViewInterface.OnCompletionListener {
                    final  SdkVideoView val$videoTexture;

          C049514(SdkVideoView sdkVideoView) {
                        this.val$videoTexture = sdkVideoView;
                    }

                    
          class AnonymousClass1 implements Runnable {
              AnonymousClass1() {
                        }

                        @Override 
              public void run() {
                  C049514.this.val$videoTexture.seekTo(VineRecorder.access$1600(VineRecorder.this) > 0 ? (int) (VineRecorder.access$1600(VineRecorder.this) / 1000) : 0);
                  C049514.this.val$videoTexture.setAutoPlayOnPrepared(true);
                  C049514.this.val$videoTexture.start();
                        }
                    }

                    @Override 
          public void onCompletion(VideoViewInterface view) {
              SLog.m446d("mjama  playback ended!");
              this.val$videoTexture.postDelayed(new AnonymousClass1(), 20L);
                    }
                }

      public class PreviewVideoClickListener implements View.OnClickListener {
                    private final boolean mEditMode;

          public PreviewVideoClickListener(boolean editMode) {
              this.mEditMode = editMode;
                    }

                    @Override 
          public void onClick(View view) {
              if (VineRecorder.access$600(VineRecorder.this) != null && VineRecorder.access$600(VineRecorder.this).isPlaying()) {
                  VineRecorder.access$1700(VineRecorder.this).forceRefresh = false;
                  VineRecorder.access$1700(VineRecorder.this).editMode = this.mEditMode;
                  VineRecorder.this.pausePreview(true);
                        }
                    }
                }

      private void refreshFullPreview() {
          SLog.m464i("Refresh full preview.");
          RecordingFile file = getFile();
          RecordSession session = null;
          if (file != null) {
              session = file.getSession();
                    }
          if (session != null) {
              ArrayList<RecordSegment> data = this.mEditorAdapter.getData();
              byte[] videoData = new byte[session.getVideoData().length];
              AudioArray audioData = AudioArrays.newInstance(session.getAudioData().getLength(), session.getVersion().getAudioArrayType());
                        try {
                  this.mVideoController.writeToFile(RecordSegment.applyForEditedChanges(file.folder, session, videoData, audioData, data), videoData, audioData);
                  long lastTime = 0;
                            for (int i = 0; i < data.size(); i++) {
                      RecordSegment segment = data.get(i);
                      segment.index = i;
                      segment.startTimestamp = lastTime;
                      lastTime += (long) segment.getAudioDurationMs();
                      SLog.m465i("Timestamp modified to: {}.", Long.valueOf(segment.startTimestamp));
                            }
                        } catch (IOException e) {
                  throw new RecordSegmentIOException(e);
                        }
                    }
                }

                
      class AsyncTaskC049615 extends RegularVineRecorder.FinishProcessTask {
          AsyncTaskC049615(String x0, Runnable x1, boolean x2, boolean x3) {
                        super(x0, x1, x2, x3);
                    }


                    @Override 
                    
          protected void onPostExecute(Void aVoid) {
              super.onPostExecute(aVoid);
              VineRecorder.access$1900(VineRecorder.this, VineRecorder.access$1800(VineRecorder.this), false);
                    }

                    @Override 
          protected void onPreExecute() {
              super.onPreExecute();
              VineRecorder.access$1900(VineRecorder.this, VineRecorder.access$1800(VineRecorder.this), true);
                    }
                }

                @Override 
      public BaseFinishProcessTask getFinishProcessTask(String tag, Runnable onComplete, boolean releasePreview, boolean saveSession) {
                    return this.mFastEncoding ? new AsyncTaskC049615(tag, onComplete, releasePreview, saveSession) : super.getFinishProcessTask(tag, onComplete, releasePreview, saveSession);
                }

      public void writePreviewToFile() {
                    try {
              int frameRate = RecordSegment.getFrameRate(this.mEditedSegments);
              SwVineFrameRecorder videoRecorder = RecordConfigUtils.newVideoRecorder(this.mCurrentRecordingFile.getVideoPath() + ".video" + RecordConfigUtils.VIDEO_CONTAINER_EXT, frameRate, 480, this.mUseMp4);
              videoRecorder.start();
              RecordSession recordSession = getFile().getSession();
              SwCombiningRunnable combiningRunnable = SwCombiningRunnable.newInstance(getFile(), false, recordSession.getAudioData(), recordSession.getVideoData(), this.mEditedSegments, videoRecorder, this.mVideoController.getFinishProcessTask());
              combiningRunnable.combineVideos();
                    } catch (SwVineFrameRecorder.Exception e) {
              CrashUtil.logException(e, "Cannot start audio/video recorder. ", new Object[0]);
                    }
                }

                @Override 
      protected void invalidateFinishButton() {
                    if (this.mCurrentDurationMs < this.mThresholdMs) {
              View view = this.mFinishButton;
              if (view != null) {
                  view.setVisibility(8);
                  return;
                        }
              return;
                    }
          onProgressThresholdReached();
                }

                @Override 
      public synchronized boolean onTouch(View v, MotionEvent event) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return !isEditing() && super.onTouch(v, event);
                }

                @Override 
      public synchronized boolean onKey(View v, int keyCode, KeyEvent event) {
                    boolean z;
                    switch (keyCode) {
                        case 27:
                            try {
                                switch (event.getAction()) {
                                    case 0:
                              startRelativeTime();
                                        break;
                                    case 1:
                              endRelativeTime();
                                        break;
                                }
                      z = true;
                            } catch (Throwable th) {
                                throw th;
                            }
                            break;
                        default:
                  z = false;
                            break;
                    }
                    return z;
                }

                @Override 
      public BasicVineRecorder.ResumeCameraAsyncTask getSwitchCameraTask() {
          return new RegularVineRecorder.ResumeCameraAsyncTask(true);
                }

      public void onEditorDone(View button) {
          if (this.mEditorDoneAsyncTask == null) {
              this.mEditorDoneAsyncTask = new EditorDoneAsyncTask(button);
              this.mEditorDoneAsyncTask.execute(new Void[0]);
                    }
                }

      public void onEditorCancel(View button) {
          if (this.mReturnToPreview) {
              returnToPreview(true);
                    } else {
              setEditMode(button, !this.mEditing, true);
                    }
                }

      public class EditorDoneAsyncTask extends AsyncTask<Void, Void, Void> {
                    private final View mEditorDoneButton;
                    private final boolean mReturnToPreviewNow;
                    private boolean mWasIndeterminate;



          public EditorDoneAsyncTask(View editorDoneButton) {
              this.mReturnToPreviewNow = VineRecorder.access$2000(VineRecorder.this) && VineRecorder.access$2100(VineRecorder.this) >= VineRecorder.this.mThresholdMs;
              this.mEditorDoneButton = editorDoneButton;
                    }

                    @Override 
          protected void onPreExecute() {
              if (VineRecorder.this.mCurrentRecordingFile != null) {
                  if (this.mReturnToPreviewNow) {
                      if (VineRecorder.this.mStartProgressDialog != null) {
                          VineRecorder.this.mStartProgressDialog.setMessage(VineRecorder.this.mFinishProgressDialogMessage[VineRecorder.this.mFinishProgressDialogMessage.length - 1]);
                          this.mWasIndeterminate = VineRecorder.this.mStartProgressDialog.isIndeterminate();
                          if (!this.mWasIndeterminate) {
                              VineRecorder.this.mStartProgressDialog.setIndeterminate(true);
                                    }
                          VineRecorder.this.mStartProgressDialog.show();
                                }
                      if (VineRecorder.access$1300(VineRecorder.this) != null) {
                          VineRecorder.access$1300(VineRecorder.this).cancel(false);
                          return;
                                }
                      return;
                            }
                  if (!isCancelled()) {
                      VineRecorder.this.setEditMode(this.mEditorDoneButton, VineRecorder.access$2200(VineRecorder.this) ? false : true, false);
                            }
                        }
                    }

                    
          protected Void doInBackground(Void... params) {
              if (this.mReturnToPreviewNow) {
                  VineRecorder.access$2300(VineRecorder.this, false);
                  return null;
                        }
              return null;
                    }

                    
          protected void onPostExecute(Void aVoid) {
              if (VineRecorder.this.mCurrentRecordingFile != null) {
                  if (this.mReturnToPreviewNow) {
                      VineRecorder.access$2400(VineRecorder.this, true);
                      VineRecorder.this.mPluginManager.onSetEditMode(false, VineRecorder.this.mCurrentRecordingFile != null && VineRecorder.this.mCurrentRecordingFile.hasData());
                      VineRecorder.this.mHandler.removeCallbacks(VineRecorder.access$2500(VineRecorder.this));
                            }
                  if (VineRecorder.this.mStartProgressDialog != null) {
                      VineRecorder.this.mStartProgressDialog.dismiss();
                      if (!this.mWasIndeterminate) {
                          VineRecorder.this.mStartProgressDialog.setIndeterminate(false);
                                }
                            }
                        }
              VineRecorder.access$2602(VineRecorder.this, null);
                    }
                }

                @Override 
                @SuppressLint({"ShowToast"})
      public void onUiResumed(Activity activity, Runnable onCompleteAsyncTask, boolean doNotResumeRecorder) {
          super.onUiResumed(activity, onCompleteAsyncTask, doNotResumeRecorder);
          this.mRecordingOptions = activity.findViewById(this.mRecordingOptionsRowId);
          this.mThumbnailList = activity.findViewById(android.R.id.list);
          this.mThumbnailOverlay = (ImageView) activity.findViewById(this.mThumbnailOverlayId);
          this.mFinishLoadingOverlay = activity.findViewById(this.mFinishLoadingOverlayId);
          this.mPlayButtonContainer = activity.findViewById(this.mPlayButtonContainerId);
          this.mPlayButtonOnClickListener = new PlayButtonOnClickListener();
          this.mPlayButtonContainer.setOnClickListener(this.mPlayButtonOnClickListener);
          this.mPlayButtonContainer.setOnTouchListener(new ViewOnTouchListenerC049716());
          this.mFinishButton = activity.findViewById(this.mFinishButtonId);
          this.mFinishButton.setOnClickListener(this.mFinishClicker);
          this.mPlayButton = ((ViewGroup) this.mPlayButtonContainer).getChildAt(0);
          this.mPlayRefreshButton = ((ViewGroup) this.mPlayButtonContainer).getChildAt(1);
          this.mDragSortWidget = (DragSortWidget) activity.findViewById(this.mDragSortWidgetId);
          this.mDragSortWidget.setSelectionChangedListener(this);
          this.mDragSortWidget.setCurrentlyPlayingProvider(this);
          this.mPreviewLoadingOverlay = activity.findViewById(this.mPreviewLoadingOverlayId);
          if (!isEditing() && this.mCurrentDurationMs >= this.mThresholdMs) {
              onProgressThresholdReached();
                    }
          this.mProgressView.setOnClickListener(new ViewOnClickListenerC049817());
          this.mIsSwitchingCamera = false;
          this.mProgressOverlay = activity.findViewById(this.mProgressOverlayId);
          ((OnMeasureNotifyingRelativeLayout) this.mRootLayoutView).setOnMeasureListener(new C049918());
          if (!doNotResumeRecorder) {
              onResume("UI Resume");
                    }
                }

                
      class ViewOnTouchListenerC049716 implements View.OnTouchListener {
          ViewOnTouchListenerC049716() {
                    }

                    @Override 
          public boolean onTouch(View view, MotionEvent event) {
              if (VineRecorder.access$1100(VineRecorder.this) != null) {
                  return VineRecorder.access$1100(VineRecorder.this).onTouch((View) VineRecorder.access$600(VineRecorder.this).getParent(), event, VineRecorder.access$2700(VineRecorder.this).getLeft(), VineRecorder.access$2700(VineRecorder.this).getTop());
                        }
                        return false;
                    }
                }

                
      class ViewOnClickListenerC049817 implements View.OnClickListener {
          ViewOnClickListenerC049817() {
                    }

                    @Override 
          public void onClick(View v) {
              if (!VineRecorder.access$2800(VineRecorder.this)) {
                  if (VineRecorder.this.mPluginManager.canEdit()) {
                      VineRecorder.this.setEditMode(VineRecorder.this.mProgressView, !VineRecorder.access$2200(VineRecorder.this), false);
                            } else {
                      SLog.m464i("Plugin has a lock on edit right now.");
                            }
                        }
                    }
                }

                
      class C049918 implements OnMeasureNotifyingRelativeLayout.OnMeasureListener {
          C049918() {
                    }

                    @Override 
          public void onMeasure() {
              VineRecorder.access$2900(VineRecorder.this);
                    }
                }

      private void returnToPreview(boolean discard) {
          if (this.mVideoController != null && this.mCurrentRecordingFile != null) {
              releaseSegmentChangeDetector();
              if (!discard) {
                  commitChanges();
                        }
              this.mCanKeepRecording = false;
              writePreviewToFile();
              startHashTask(false);
                    }
                }

      private int getDurationFromSegments() {
          int duration = 0;
                    for (RecordSegment segment : this.mEditorAdapter.getData()) {
              if (!segment.removed) {
                  duration += segment.getDurationMs();
                        }
                    }
          return duration;
                }

                @Override 
      public void onProgressThresholdReached() {
          View view = this.mFinishButton;
          if (view != null && view.getVisibility() != 0) {
              view.setVisibility(0);
                    }
                }

                @Override 
                @TargetApi(16)
      public void onPause() {
          super.onPause();
          if (Build.VERSION.SDK_INT >= 16 && this.mMediaActionSound != null) {
              this.mMediaActionSound.release();
                    }
          EditorDoneAsyncTask task = this.mEditorDoneAsyncTask;
          if (task != null) {
              task.cancel(false);
                    }
          this.mVideoViewContainer.removeView(this.mVideoTexture);
          if (this.mVideoTexture != null) {
              this.mVideoTexture.suspend();
              this.mVideoTexture = null;
                    }
                }

      public class PlayButtonOnClickListener implements View.OnClickListener {
                    boolean forceRefresh = false;
                    boolean editMode = false;

          public PlayButtonOnClickListener() {
                    }

                    @Override 
          public void onClick(View v) {
              if (VineRecorder.access$900(VineRecorder.this) != null) {
                  int selection = VineRecorder.access$1100(VineRecorder.this).getSelection();
                  RecordSegment toPlay = null;
                  if (selection != -1) {
                      toPlay = (RecordSegment) VineRecorder.access$900(VineRecorder.this).getItem(VineRecorder.access$1100(VineRecorder.this).getSelection());
                            }
                  if (toPlay == null) {
                      VineRecorder.access$3002(VineRecorder.this, -1);
                      if (VineRecorder.access$1100(VineRecorder.this) != null) {
                          VineRecorder.access$1100(VineRecorder.this).setSelection(-1);
                                }
                      VineRecorder.this.animateTopButtons(2, null);
                            }
                  VineRecorder.this.playPreview(toPlay, this.forceRefresh, this.editMode, "playButton");
                        }
                    }
                }

                @Override 
      public void onUiPaused() {
          releaseSegmentChangeDetector();
          if (this.mDragSortWidget != null) {
              this.mDragSortWidget.releaseReferences();
                    }
                    try {
              if (this.mRefreshPreviewTask != null) {
                  this.mRefreshPreviewTask.cancel(false);
                        }
                    } catch (Exception e) {
              SLog.m459e("It's probably detached already.", (Throwable) e);
                    }
          if (this.mVideoTexture != null) {
              this.mVideoTexture.setOnErrorListener(null);
              this.mVideoTexture.setOnPreparedListener(null);
                    }
          this.mRefreshPreviewTask = null;
          this.mRecordingOptions = null;
          this.mPreviewLoadingOverlay = null;
          this.mFinishLoadingOverlay = null;
          this.mThumbnailList = null;
          if (this.mPlayButtonContainer != null) {
              this.mPlayButtonContainer.setOnTouchListener(null);
              this.mPlayButtonContainer.setOnClickListener(null);
                    }
          this.mPlayButtonContainer = null;
          this.mThumbnailSegment = null;
          this.mThumbnailOverlay = null;
          if (this.mDragSortWidget != null) {
              this.mDragSortWidget.setSelectionChangedListener(null);
              this.mDragSortWidget.setCurrentlyPlayingProvider(null);
                    }
          this.mDragSortWidget = null;
          if (this.mFinishButton != null) {
              this.mFinishButton.setOnClickListener(null);
                    }
          this.mFinishButton = null;
          super.onUiPaused();
                }

                @Override 
      protected void adjustBoundaries(CameraSetting cs) {
          Activity activity = this.mActivity;
          if (activity != null && cs != null) {
              float previewWidth = cs.originalW;
              if (cs.frontFacing && cs.frontFacingAspectRatio > 0.0f) {
                  previewWidth = cs.originalH * cs.frontFacingAspectRatio;
                        } else if (cs.backFacingAspectRatio > 0.0f) {
                  previewWidth = cs.originalH * cs.backFacingAspectRatio;
                        }
                        if (cs.frontFacingAspectRatio > 0.0f || cs.backFacingAspectRatio > 0.0f) {
                  CrashUtil.logException(new VineLoggingException("Adjusting ratio originalW = " + cs.originalW + " originalH = " + cs.originalH + " hardware Hardware Version: " + Build.DEVICE + " - " + Build.MODEL + " (" + Build.CPU_ABI + "." + Build.HARDWARE + "." + Build.BRAND + "." + Build.PRODUCT + "." + Build.MANUFACTURER + "." + Build.CPU_ABI2 + ")"));
                        }
              if (this.mFinishLoadingOverlay != null && this.mVideoTexture != null) {
                  ViewGroup.LayoutParams params = this.mVideoTexture.getLayoutParams();
                  params.width = this.mSize.x;
                  params.height = this.mSize.x;
                  this.mFinishLoadingOverlay.post(new RunnableC050019(params));
                        }
              float previewHeight = cs.originalH;
              float aspectRatio = previewWidth / previewHeight;
              int surfaceViewHeightPx = (int) (this.mSize.x * aspectRatio);
              if (this.mTopMaskView != null && this.mBottomMaskView != null) {
                  int topMaskHeightPx = this.mTopMaskView.getLayoutParams().height;
                  int surfaceViewMidpoint = surfaceViewHeightPx / 2;
                  int midpointYOffset = (this.mSize.x / 2) + topMaskHeightPx;
                  int surfaceViewYPos = midpointYOffset - surfaceViewMidpoint;
                  RelativeLayout.LayoutParams cameraViewParams = (RelativeLayout.LayoutParams) this.mCameraView.getLayoutParams();
                  cameraViewParams.width = this.mSize.x;
                  cameraViewParams.height = surfaceViewHeightPx;
                  cameraViewParams.setMargins(0, surfaceViewYPos, 0, 0);
                  activity.runOnUiThread(new RunnableC050220(cameraViewParams, topMaskHeightPx));
                        }
                    }
                }

                
      class RunnableC050019 implements Runnable {
                    final  ViewGroup.LayoutParams val$params;

          RunnableC050019(ViewGroup.LayoutParams layoutParams) {
                        this.val$params = layoutParams;
                    }

                    @Override 
          public void run() {
              if (VineRecorder.access$1800(VineRecorder.this) != null) {
                  VineRecorder.access$1800(VineRecorder.this).setLayoutParams(this.val$params);
                        }
                    }
                }

                
      class RunnableC050220 implements Runnable {
                    final  RelativeLayout.LayoutParams val$cameraViewParams;
                    final  int val$topMaskHeightPx;

          RunnableC050220(RelativeLayout.LayoutParams layoutParams, int i) {
                        this.val$cameraViewParams = layoutParams;
                        this.val$topMaskHeightPx = i;
                    }

                    @Override 
          public void run() {
              View view = VineRecorder.this.mCameraView;
              if (view != null) {
                  SLog.m448d("Setting preview sizes to {} {}.", Integer.valueOf(this.val$cameraViewParams.width), Integer.valueOf(this.val$cameraViewParams.height));
                  view.setLayoutParams(this.val$cameraViewParams);
                        }
              VineRecorder.this.mPluginManager.onAdjustingLayoutParamsComplete(this.val$topMaskHeightPx, VineRecorder.this.mSize.x);
                    }
                }

      private void adjustStaticBoundaries() {
          Activity activity = this.mActivity;
          if (activity != null) {
              int topMaskHeightPx = this.mTopMaskView.getLayoutParams().height;
              int rootHeight = this.mRootLayoutView.getMeasuredHeight();
              int bottomMaskHeightPx = (rootHeight - topMaskHeightPx) - this.mSize.x;
              RelativeLayout.LayoutParams bottomMaskParams = (RelativeLayout.LayoutParams) this.mBottomMaskView.getLayoutParams();
              bottomMaskParams.height = bottomMaskHeightPx;
              activity.runOnUiThread(new RunnableC050321(bottomMaskParams));
                    }
                }

                
      class RunnableC050321 implements Runnable {
                    final  RelativeLayout.LayoutParams val$bottomMaskParams;

          RunnableC050321(RelativeLayout.LayoutParams layoutParams) {
                        this.val$bottomMaskParams = layoutParams;
                    }

                    @Override 
          public void run() {
              View view = VineRecorder.this.mBottomMaskView;
              if (view != null) {
                  view.setLayoutParams(this.val$bottomMaskParams);
                        }
                    }
                }

      private void releaseSegmentChangeDetector() {
          if (this.mSegmentChangeThread != null) {
              this.mSegmentChangeDetector.runThread = false;
                        try {
                  this.mSegmentChangeThread.join();
                        } catch (InterruptedException e) {
                        }
              this.mSegmentChangeThread = null;
                    }
                }

                @Override 
      public void onProgressMaxReached() {
          if (!this.mHasPreviewedAlready) {
              finish("progressMaxReached");
                    }
                }

      public View getProgressView() {
          return this.mProgressView;
                }

                @Override 
      public void onCameraReady(RecordController controller) {
          if (this.mActivity != null) {
              this.mHandler.post(this.onCameraReadyRunnable);
              super.onCameraReady(controller);
                    }
                }

                
      class RunnableC050422 implements Runnable {
          RunnableC050422() {
                    }

                    @Override 
          public void run() {
              if (VineRecorder.access$3100(VineRecorder.this).getVisibility() != 8) {
                  VineRecorder.access$3100(VineRecorder.this).setVisibility(8);
                        }
                    }
                }

                @Override 
      public void changeProgress(long progress) {
          super.changeProgress(progress);
          if (this.mProgressOverlay != null) {
              if (SystemUtil.isOnMainThread()) {
                  this.mChangeProgressOverlayVisibilityRunnable.run();
                        } else {
                  this.mHandler.post(this.mChangeProgressOverlayVisibilityRunnable);
                        }
                    }
                }

      public class SegmentChangeDetector implements Runnable {
                    private static final int SLEEP_MILLIS = 40;
                    public int lastFirstItem = 0;
                    public boolean runThread = true;

          public SegmentChangeDetector() {
                    }

                    @Override 
          public void run() {
                        int position;
                        ArrayList<RecordSegment> segments;
                        int size;
              while (this.runThread) {
                            try {
                      if (VineRecorder.access$3000(VineRecorder.this) == -1 && VineRecorder.access$900(VineRecorder.this) != null && VineRecorder.access$600(VineRecorder.this) != null && VineRecorder.access$600(VineRecorder.this).isPlaying() && (position = VineRecorder.access$600(VineRecorder.this).getCurrentPosition() + 20) > 0 && (size = (segments = VineRecorder.access$900(VineRecorder.this).getData()).size()) > 0) {
                                    for (int i = size - 1; i >= 0; i--) {
                                        if (position >= segments.get(i).startTimestamp) {
                                  if (this.lastFirstItem != i) {
                                      this.lastFirstItem = i;
                                      Activity activity = VineRecorder.this.mActivity;
                                      if (activity == null) {
                                                    break;
                                                }
                                      activity.runOnUiThread(new RunnableC05121());
                                                break;
                                            }
                                            break;
                                        }
                                    }
                                }
                      Thread.sleep(40L);
                            } catch (InterruptedException e) {
                      return;
                            }
                        }
                    }

                    
          class RunnableC05121 implements Runnable {
              RunnableC05121() {
                        }

                        @Override 
              public void run() {
                            DragSortWidget widget;
                  if (VineRecorder.access$3000(VineRecorder.this) == -1 && (widget = VineRecorder.access$1100(VineRecorder.this)) != null) {
                      widget.setFocused(SegmentChangeDetector.this.lastFirstItem);
                            }
                        }
                    }
                }

                @Override 
      public Point getSize() {
          return this.mSize;
                }

                @TargetApi(16)
      public void playStopRecordingSound() {
                    try {
              if (Build.VERSION.SDK_INT >= 16 && this.mMediaActionSound != null && this.mPlaySound) {
                  this.mMediaActionSound.play(3);
                        }
                    } catch (Exception e) {
                    }
                }

                @TargetApi(16)
      public void playStartRecordingSound() {
                    try {
              if (Build.VERSION.SDK_INT >= 16 && this.mMediaActionSound != null && this.mPlaySound) {
                  this.mMediaActionSound.play(2);
                        }
                    } catch (Exception e) {
                    }
                }
            }
