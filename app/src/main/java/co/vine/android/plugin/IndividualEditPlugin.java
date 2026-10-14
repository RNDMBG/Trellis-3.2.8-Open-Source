            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.DialogInterface;
            import android.content.res.Resources;
            import android.graphics.Point;
            import android.graphics.Rect;
            import android.graphics.drawable.ColorDrawable;
            import android.graphics.drawable.NinePatchDrawable;
            import android.os.Handler;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewParent;
            import android.widget.RelativeLayout;
            import android.widget.Toast;
            import co.vine.android.AbstractRecordingActivity;
            import co.vine.android.C0257R;
            import co.vine.android.dragsort.DragSortWidget;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSegment;
            import co.vine.android.recorder.RegularProgressView;
            import co.vine.android.recorder.SegmentEditorAdapter;
            import co.vine.android.recorder.TrimData;
            import co.vine.android.recorder.ViewGoneAnimationListener;
            import co.vine.android.recorder.VineRecorder;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.views.RangeSeekBar;
            import co.vine.android.widgets.PromptDialogFragment;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import java.io.IOException;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            
            
    public class IndividualEditPlugin extends BaseRecorderPlugin<View, VineRecorder> implements DragSortWidget.FloatViewInteractionListener, View.OnTouchListener, View.OnClickListener, RangeSeekBar.OnRangeSeekBarChangeListener {
                private static final int DIALOG_UNSAVED_EDITED_CHANGES = 0;
                private static final int INTERSECT_FALSE = -1;
                private static final int INTERSECT_LEFT = 0;
                private static final int INTERSECT_MIDDLE = 1;
                private static final int INTERSECT_RIGHT = 2;
                private static final ColorDrawable PRESSED_STATE_COLOR = new ColorDrawable(-13092808);
                private View mAllDeletedOverlay;
                private long mCurrentProgressWithoutSelectedSegment;
                private int mCurrentRangeSeekBarMax;
                private int mCurrentRangeSeekBarMin;
                private DragSortWidget mDragSortWidget;
                private View mDuplicateButton;
                private boolean mEdited;
                private View mEditorButtons;
                private View mEditorCancelButton;
                private View mEditorDoneButton;
                private int mExtraProgressDuration;
                private View mIndividualEditContainer;
                private final Rect mIntersectBounds;
                private final int[] mIntersectLoc;
                private String mLastUpdateProgressViewTag;
                private float mMaxDuration;
                private int mOneThirdXOnScreen;
                private int mPickedUpPosition;
                private long mPlayerEnd;
                private long mPlayerStart;
                private RegularProgressView mProgressView;
                private boolean mRangeSeekBarTouched;
                private int mRangeSeekBarTouchedUnTrimmedDuration;
                private boolean mSeekToLeft;
                private WeakReference<RecordSegment> mSegment;
                private View mSilenceButton;
                private int mSize;
                private Toast mToast;
                private View mTrashButton;
                private final Runnable mTrimRunnable;
                private RangeSeekBar mTrimmer;
                private int mTwoThirdXOnScreen;
                private final PromptDialogFragment.OnDialogDoneListener mUnsavedChangesEditedListener;
                private final Runnable mUpdateProgressViewRunnable;
                private SdkVideoView mVideoPlayer;

        static  View access$000(IndividualEditPlugin x0) {
            return x0.mEditorDoneButton;
                }

        static  View access$100(IndividualEditPlugin x0) {
            return x0.mEditorCancelButton;
                }

        static  boolean access$1000(IndividualEditPlugin x0) {
            return x0.mSeekToLeft;
                }

        static  int access$1100(IndividualEditPlugin x0) {
            return x0.mCurrentRangeSeekBarMin;
                }

        static  int access$1200(IndividualEditPlugin x0) {
            return x0.mCurrentRangeSeekBarMax;
                }

        static  boolean access$1300(IndividualEditPlugin x0) {
            return x0.mRangeSeekBarTouched;
                }

        static  long access$1400(IndividualEditPlugin x0) {
            return x0.mCurrentProgressWithoutSelectedSegment;
                }

        static  void access$1500(IndividualEditPlugin x0, RecordSegment x1) {
            x0.invalidateDuplicateButton(x1);
                }

        static  long access$1600(IndividualEditPlugin x0) {
            return x0.mPlayerStart;
                }

        static  long access$1700(IndividualEditPlugin x0) {
            return x0.mPlayerEnd;
                }

        static  void access$1800(IndividualEditPlugin x0, long x1, long x2) {
            x0.onPlayerStartAndEndChanged(x1, x2);
                }

        static  RangeSeekBar access$1900(IndividualEditPlugin x0) {
            return x0.mTrimmer;
                }

        static  WeakReference access$200(IndividualEditPlugin x0) {
            return x0.mSegment;
                }

        static  int access$300(IndividualEditPlugin x0) {
            return x0.mRangeSeekBarTouchedUnTrimmedDuration;
                }

        static  int access$400(IndividualEditPlugin x0) {
            return x0.mExtraProgressDuration;
                }

        static  float access$500(IndividualEditPlugin x0) {
            return x0.mMaxDuration;
                }

        static  RegularProgressView access$600(IndividualEditPlugin x0) {
            return x0.mProgressView;
                }

        static  String access$700(IndividualEditPlugin x0) {
            return x0.mLastUpdateProgressViewTag;
                }

        static  DragSortWidget access$800(IndividualEditPlugin x0) {
            return x0.mDragSortWidget;
                }

        static  SdkVideoView access$900(IndividualEditPlugin x0) {
            return x0.mVideoPlayer;
                }

        public IndividualEditPlugin() {
            super("IndividualEdit");
            this.mIntersectBounds = new Rect();
            this.mIntersectLoc = new int[2];
            this.mRangeSeekBarTouchedUnTrimmedDuration = -1;
           this.mUnsavedChangesEditedListener = new C04554();
           this.mUpdateProgressViewRunnable = new RunnableC04565();
           this.mTrimRunnable = new RunnableC04587();
                }

                @Override 
        public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
            View view = fragment.getView();
           Activity activity = fragment.getActivity();
           Resources res = activity.getResources();
           setEdited(false);
           this.mEditorButtons = view.findViewById(C0257R.id.editorButtons);
           this.mEditorButtons.setOnTouchListener(new ViewOnTouchListenerC04521());
           this.mEditorDoneButton = view.findViewById(C0257R.id.editorDoneButton);
           this.mEditorDoneButton.setOnClickListener(new ViewOnClickListenerC04532());
           this.mEditorCancelButton = view.findViewById(C0257R.id.editorCancelButton);
           this.mEditorCancelButton.setOnClickListener(new ViewOnClickListenerC04543());
           this.mIndividualEditContainer = view.findViewById(C0257R.id.individual_edit_tools_container);
           this.mDuplicateButton = this.mIndividualEditContainer.findViewById(C0257R.id.duplicateButton);
           this.mDuplicateButton.setOnClickListener(this);
           this.mDuplicateButton.setOnTouchListener(this);
           this.mTrashButton = this.mIndividualEditContainer.findViewById(C0257R.id.trashCanButtonIndividual);
           this.mTrashButton.setOnClickListener(this);
           this.mTrashButton.setOnTouchListener(this);
           this.mSilenceButton = this.mIndividualEditContainer.findViewById(C0257R.id.silenceButton);
           this.mSilenceButton.setOnClickListener(this);
           this.mSilenceButton.setOnTouchListener(this);
           Point size = SystemUtil.getDisplaySize(activity);
           this.mTwoThirdXOnScreen = (size.x * 2) / 3;
           this.mOneThirdXOnScreen = size.x / 3;
           this.mSize = size.x;
           int selectedTrimColor = res.getColor(C0257R.color.timebar_selected_color);
           this.mProgressView = (RegularProgressView) view.findViewById(C0257R.id.edit_progress);
           this.mProgressView.setSelectedColor(res.getColor(C0257R.color.trim_yellow));
           this.mProgressView.setColor(res.getColor(C0257R.color.vine_green));
           this.mDragSortWidget = (DragSortWidget) view.findViewById(C0257R.id.thumbnail_list);
           this.mDragSortWidget.setFloatViewInteractionListener(this);
           this.mAllDeletedOverlay = inflater.inflate(C0257R.layout.plugin_all_deleted_overlay, (ViewGroup) this.mDragSortWidget, false);
           RelativeLayout trimmerContainer = (RelativeLayout) view.findViewById(C0257R.id.above_bottom_mask_container);
           if (this.mTrimmer == null) {
               RangeSeekBar trimmer = new RangeSeekBar((NinePatchDrawable) activity.getResources().getDrawable(C0257R.drawable.scrub_left), (NinePatchDrawable) activity.getResources().getDrawable(C0257R.drawable.scrub_right), 0, 100, activity, selectedTrimColor, activity.getResources().getColor(C0257R.color.black_thirty_five_percent));
               trimmer.setSelectedMinValue(0);
               trimmer.setSelectedMaxValue(100);
               trimmer.setVisibility(8);
               trimmer.setOnRangeSeekBarChangeListener(this);
               int height = activity.getResources().getDimensionPixelSize(C0257R.dimen.trimmer_height);
               trimmer.setLayoutParams(new RelativeLayout.LayoutParams(-1, height));
               trimmerContainer.addView(trimmer);
               RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) trimmerContainer.getLayoutParams();
               param.bottomMargin = activity.getResources().getDimensionPixelOffset(C0257R.dimen.trimmer_margin);
               trimmerContainer.setLayoutParams(param);
               this.mTrimmer = trimmer;
                    }
           ViewParent trimmerParent = this.mTrimmer.getParent();
           if (trimmerParent != null && trimmerParent != trimmerContainer) {
               ((ViewGroup) trimmerParent).removeView(this.mTrimmer);
               return null;
                    }
           return null;
                }

                
       class ViewOnTouchListenerC04521 implements View.OnTouchListener {
           ViewOnTouchListenerC04521() {
                    }

                    @Override 
           public boolean onTouch(View v, MotionEvent event) {
               return true;
                    }
                }

                
       class ViewOnClickListenerC04532 implements View.OnClickListener {
           ViewOnClickListenerC04532() {
                    }

                    @Override 
           public void onClick(View v) {
               VineRecorder recorder = IndividualEditPlugin.this.getRecorder();
               if (recorder != null) {
                   recorder.onEditorDone(IndividualEditPlugin.access$000(IndividualEditPlugin.this));
                        }
                    }
                }

                
       class ViewOnClickListenerC04543 implements View.OnClickListener {
           ViewOnClickListenerC04543() {
                    }

                    @Override 
           public void onClick(View v) {
               VineRecorder recorder = IndividualEditPlugin.this.getRecorder();
               if (recorder != null) {
                   recorder.onEditorCancel(IndividualEditPlugin.access$100(IndividualEditPlugin.this));
                        }
                    }
                }

       private void silence(RecordSegment segment) {
           if (segment.isSilenced()) {
               segment.setSilenced(false);
               this.mSilenceButton.setAlpha(0.35f);
                    } else {
               this.mSilenceButton.setAlpha(1.0f);
               segment.setSilenced(true);
                    }
           if (this.mVideoPlayer != null) {
               this.mVideoPlayer.setMute(segment.isSilenced());
                    }
                }

                @Override 
       public void onSessionSwapped() {
                    RecordConfigUtils.RecordConfig config;
           VineRecorder recorder = getRecorder();
           if (recorder != null && (config = recorder.getConfig()) != null) {
               this.mMaxDuration = config.maxDuration;
                    }
                }

                @Override 
       public void onIndividualSegmentClicked(RecordSegment segment, SdkVideoView player) {
           this.mVideoPlayer = player;
           this.mSegment = new WeakReference<>(segment);
           setEdited(true);
           Handler handler = getHandler();
           if (handler != null) {
               handler.removeCallbacks(this.mTrimRunnable);
                    }
           if (segment == null) {
               hideIndividualSegmentControls();
                    } else {
               showIndividualSegmentControls(segment);
                    }
                }

                @Override 
       public void onAnimateEditModeControlsInUI() {
           if (this.mEditorButtons != null) {
               this.mEditorButtons.setAlpha(0.0f);
               this.mEditorButtons.setVisibility(0);
               this.mEditorButtons.animate().alpha(1.0f).setDuration(250L).start();
                    }
                }

                @Override 
       public void onAnimateEditModeControlsOutUI() {
           if (this.mEditorButtons != null) {
               this.mEditorButtons.animate().alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(this.mEditorButtons)).start();
                    }
                }

                @Override 
       public boolean onBackButtonPressed(boolean isEditing) {
           VineRecorder recorder = getRecorder();
           if (recorder != null) {
               if (this.mIndividualEditContainer.getVisibility() == 0) {
                   hideIndividualSegmentControls();
                   this.mDragSortWidget.setSelection(-1);
                   recorder.playPreview(null, true, true, "ClipBackButton");
                            return true;
                        }
               if (this.mEditorButtons.getVisibility() == 0) {
                   if (this.mActivity instanceof AbstractRecordingActivity) {
                       if (isEdited()) {
                           AbstractRecordingActivity activity = (AbstractRecordingActivity) this.mActivity;
                           int dialogId = this.mManager.getDialogIdForPlugin(this, 0);
                           PromptDialogFragment dialog = activity.createDialogForPlugins(dialogId);
                           dialog.setListener(this.mUnsavedChangesEditedListener);
                           dialog.setMessage(C0257R.string.cancel_edited_changes_confirm);
                           dialog.setPositiveButton(C0257R.string.save);
                           dialog.setNegativeButton(C0257R.string.discard_changes);
                           activity.showDialog(dialog);
                                } else {
                           recorder.setEditMode(false, true);
                                }
                            }
                            return true;
                        }
                    }
                    return false;
                }

                
       class C04554 implements PromptDialogFragment.OnDialogDoneListener {
           C04554() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
               VineRecorder recorder = IndividualEditPlugin.this.getRecorder();
               if (recorder != null && recorder.hasSessionFile()) {
                            switch (which) {
                                case -2:
                           recorder.setEditMode(false, true);
                                    break;
                                case -1:
                           recorder.setEditMode(false, false);
                                    break;
                            }
                        }
                    }
                }

       private void showIndividualSegmentControls(RecordSegment segment) {
           this.mTrimmer.setVisibility(0);
           disableView(this.mDuplicateButton);
           disableView(this.mTrashButton);
           disableView(this.mSilenceButton);
           this.mExtraProgressDuration = 0;
           updateProgressView("showIndividualSegmentControls");
           this.mEditorButtons.setVisibility(8);
           this.mIndividualEditContainer.setVisibility(0);
           if (segment.isSilenced()) {
               this.mSilenceButton.setAlpha(1.0f);
                    } else {
               this.mSilenceButton.setAlpha(0.35f);
                    }
           if (segment.isTrimmed()) {
               float total = segment.getUnTrimmedAudioDurationMs();
               this.mTrimmer.forceResetNormalizedValues(segment.getTrimmedVideoStartMs() / total, (segment.getTrimmedAudioEndUs() / 1000) / total);
                    } else {
               this.mTrimmer.forceResetNormalizedValues(0.0d, 1.0d);
                    }
           invalidateDuplicateButton(segment);
           this.mCurrentProgressWithoutSelectedSegment = getDuration() - ((long) segment.getDurationMs());
                }

       private void invalidateDuplicateButton(RecordSegment recordSegment) {
           if (canDuplicate(recordSegment)) {
               this.mDuplicateButton.setAlpha(0.35f);
                    } else {
               this.mDuplicateButton.setAlpha(0.175f);
                    }
                }

       private boolean canDuplicate(RecordSegment segment) {
                    return ((float) (this.mCurrentProgressWithoutSelectedSegment + ((long) (segment.getDurationMs() * 2)))) < this.mMaxDuration;
                }

                
       class RunnableC04565 implements Runnable {
           RunnableC04565() {
                    }

                    @Override 
           public void run() {
                        int segmentDuration;
               long duration = 0;
               RecordSegment segment = null;
               if (IndividualEditPlugin.access$200(IndividualEditPlugin.this) != null) {
                   segment = (RecordSegment) IndividualEditPlugin.access$200(IndividualEditPlugin.this).get();
                        }
               if (segment != null) {
                   segmentDuration = segment.getDurationMs();
                        } else {
                   segmentDuration = 0;
                        }
               int start = 0;
               VineRecorder recorder = IndividualEditPlugin.this.getRecorder();
               if (recorder != null) {
                   SegmentEditorAdapter adapter = recorder.getEditorAdatper();
                   if (adapter != null) {
                       ArrayList<RecordSegment> data = adapter.getData();
                                for (RecordSegment s : data) {
                           if (segment == s) {
                               start = (int) duration;
                               if (IndividualEditPlugin.access$300(IndividualEditPlugin.this) < 0) {
                                   duration += (long) s.getDurationMs();
                                        }
                                    } else {
                               duration += (long) s.getDurationMs();
                                    }
                                }
                            } else {
                       duration = recorder.getCurrentDuration();
                            }
                        }
               SLog.m447d("Update progress ratio: {}", Float.valueOf((((long) IndividualEditPlugin.access$400(IndividualEditPlugin.this)) + duration) / IndividualEditPlugin.access$500(IndividualEditPlugin.this)));
               IndividualEditPlugin.access$600(IndividualEditPlugin.this).setProgressRatio((((long) IndividualEditPlugin.access$400(IndividualEditPlugin.this)) + duration) / IndividualEditPlugin.access$500(IndividualEditPlugin.this));
               if (IndividualEditPlugin.access$300(IndividualEditPlugin.this) >= 0) {
                            segmentDuration = 0;
                        }
                        long endAdjustment = segmentDuration;
               long end = ((long) (IndividualEditPlugin.access$400(IndividualEditPlugin.this) + start)) + endAdjustment;
                        if (end >= start) {
                   IndividualEditPlugin.access$600(IndividualEditPlugin.this).setSelectedSection(start / IndividualEditPlugin.access$500(IndividualEditPlugin.this), end / IndividualEditPlugin.access$500(IndividualEditPlugin.this));
                        } else {
                   CrashUtil.logException(new IllegalStateException(MessageFormatter.toStringMessage("Start {}, Extra {}, Duration {}, From {}", Integer.valueOf(start), Integer.valueOf(IndividualEditPlugin.access$400(IndividualEditPlugin.this)), Long.valueOf(endAdjustment), IndividualEditPlugin.access$700(IndividualEditPlugin.this))));
                        }
                    }
                }

                @Override 
       public void onPause() {
           super.onPause();
           if (this.mSegment != null && this.mSegment.get() != null) {
               hideIndividualSegmentControls();
                    }
           Handler handler = getHandler();
           if (handler != null) {
               handler.removeCallbacks(this.mUpdateProgressViewRunnable);
                    }
                }

       private void updateProgressView(String tag) {
           updateProgressView(tag, 10);
                }

       private void updateProgressView(String tag, int delay) {
           Handler handler = getHandler();
           if (handler != null) {
               this.mLastUpdateProgressViewTag = tag;
               handler.removeCallbacks(this.mUpdateProgressViewRunnable);
               handler.postDelayed(this.mUpdateProgressViewRunnable, delay);
                    }
                }

       private long getDuration() {
           VineRecorder recorder = getRecorder();
           if (recorder != null) {
               SegmentEditorAdapter adapter = recorder.getEditorAdatper();
               if (adapter != null) {
                   return adapter.getDuration();
                        }
               return recorder.getCurrentDuration();
                    }
           return -1L;
                }

       private void hideIndividualSegmentControls() {
                    ViewGroup container;
           long duration = getDuration();
                    if (duration >= 0) {
                        if (duration == 0) {
                   ViewGroup parent = (ViewGroup) this.mAllDeletedOverlay.getParent();
                   if (parent != null) {
                       parent.removeView(this.mAllDeletedOverlay);
                            }
                   VineRecorder recorder = getRecorder();
                   if (recorder != null && (container = recorder.getVideoContainer()) != null) {
                       container.addView(this.mAllDeletedOverlay);
                       ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.mAllDeletedOverlay.getLayoutParams();
                       params.height = this.mSize;
                       if (this.mAllDeletedOverlay != null) {
                           this.mAllDeletedOverlay.setLayoutParams(params);
                           this.mAllDeletedOverlay.setVisibility(0);
                                }
                            }
                        } else if (this.mAllDeletedOverlay != null) {
                   this.mAllDeletedOverlay.setVisibility(8);
                        }
                    }
           this.mEditorButtons.setVisibility(0);
           this.mTrimmer.setVisibility(8);
           this.mIndividualEditContainer.setVisibility(8);
           this.mExtraProgressDuration = 0;
           disableView(this.mTrashButton);
           disableView(this.mSilenceButton);
           disableView(this.mDuplicateButton);
           updateProgressView("hideIndividualSegmentControls");
                }

                @Override 
       public boolean canPickUpFloatView() {
           VineRecorder recorder = getRecorder();
                    return recorder != null && recorder.canPickUpFloatView();
                }

                @Override 
       public void floatViewPickedUp(int item) {
           this.mPickedUpPosition = item;
           VineRecorder recorder = getRecorder();
           if (recorder != null) {
               SegmentEditorAdapter editor = recorder.getEditorAdatper();
               if (editor != null) {
                   RecordSegment segment = (RecordSegment) editor.getItem(item);
                   recorder.showThumbnailOverlay(segment);
                   recorder.changePlayButtonOnClickBehavior(false, true);
                   onIndividualSegmentClicked(segment, this.mVideoPlayer);
                        }
               recorder.pausePreview(false);
                    }
                }

                @Override 
       public void floatViewMoved() {
           VineRecorder recorder = getRecorder();
           if (recorder != null) {
                        switch (floatViewIntersectsPosition()) {
                            case 0:
                       enableDuplicateView(this.mDuplicateButton);
                       disableView(this.mTrashButton);
                       disableView(this.mSilenceButton);
                                break;
                            case 1:
                       enableTrashView(this.mTrashButton);
                       disableView(this.mDuplicateButton);
                       disableView(this.mSilenceButton);
                                break;
                            case 2:
                       enableSilenceView(this.mSilenceButton);
                       disableView(this.mTrashButton);
                       disableView(this.mDuplicateButton);
                       this.mExtraProgressDuration = 0;
                       updateProgressView("floatViewMovedRight");
                                break;
                            default:
                       disableView(this.mSilenceButton);
                       disableView(this.mTrashButton);
                       disableView(this.mDuplicateButton);
                       this.mExtraProgressDuration = 0;
                       updateProgressView("floatViewMovedFalse");
                                break;
                        }
                    }
                }

       private boolean floatViewIntersectsTop() {
           if (this.mVideoPlayer != null) {
               int vidPlayerMarginTop = ((RelativeLayout.LayoutParams) this.mVideoPlayer.getLayoutParams()).topMargin;
               Rect floatViewBounds = this.mDragSortWidget.getFloatViewBounds(this.mIntersectBounds);
               if (floatViewBounds != null) {
                   if (this.mDragSortWidget.floatViewIsFromContentView()) {
                                return floatViewBounds.top < vidPlayerMarginTop;
                            }
                   int videoPlayerMiddle = ((this.mVideoPlayer.getBottom() - this.mVideoPlayer.getTop()) / 2) + vidPlayerMarginTop;
                   return floatViewBounds.top < videoPlayerMiddle;
                        }
                    }
                    return false;
                }

       private int floatViewIntersectsPosition() {
                    int[] loc;
           if (floatViewIntersectsTop() && (loc = this.mDragSortWidget.getFloatViewPosition(this.mIntersectLoc)) != null) {
               if (loc[0] > this.mTwoThirdXOnScreen) {
                   return 2;
                        }
                        return loc[0] > this.mOneThirdXOnScreen ? 1 : 0;
                    }
           return -1;
                }

                @Override 
       public boolean floatViewDropped(int to) {
           disableView(this.mDuplicateButton);
           disableView(this.mTrashButton);
           disableView(this.mSilenceButton);
           int extraProgress = this.mExtraProgressDuration;
           this.mExtraProgressDuration = 0;
           VineRecorder recorder = getRecorder();
           if (recorder != null) {
               SegmentEditorAdapter editorAdapter = recorder.getEditorAdatper();
               int action = floatViewIntersectsPosition();
               boolean shouldPlayerChange = false;
                        switch (action) {
                            case 0:
                       if (editorAdapter.getCount() > to) {
                           addNewSegmentAsDuplicate(recorder, to);
                                }
                       shouldPlayerChange = true;
                                break;
                            case 1:
                       shouldPlayerChange = true;
                       this.mExtraProgressDuration = extraProgress;
                                break;
                            case 2:
                       if (editorAdapter.getCount() > to) {
                           silence((RecordSegment) editorAdapter.getItem(to));
                                }
                       shouldPlayerChange = true;
                                break;
                        }
               if (this.mPickedUpPosition != to || shouldPlayerChange) {
                   recorder.changePlayButtonOnClickBehavior(true, true);
                        }
               if (!shouldPlayerChange || editorAdapter.getCount() != 1) {
                   recorder.animatePlayButton(true);
                        }
               updateProgressView("floatViewDropped");
                        switch (action) {
                            case 0:
                                return true;
                            case 1:
                       delete(recorder);
                                return false;
                            case 2:
                                return true;
                        }
                    }
                    return true;
                }

                @Override 
       public void floatViewLanded(int to) {
                }

                @Override 
       public boolean onSetEditMode(boolean editing, boolean hasData) {
           this.mIndividualEditContainer.setVisibility(8);
           if (editing) {
               this.mProgressView.setVisibility(0);
               this.mSegment = new WeakReference<>(null);
               updateProgressView("onSetEditMode");
                    } else {
               this.mProgressView.setVisibility(8);
               this.mAllDeletedOverlay.setVisibility(8);
                    }
           return super.onSetEditMode(editing, hasData);
                }

       private void enableTrashView(View v) {
           if (this.mDragSortWidget.canDelete()) {
               RecordSegment segment = this.mSegment.get();
               if (segment != null) {
                   this.mExtraProgressDuration = -segment.getDurationMs();
                   updateProgressView("enableTrashView");
                        }
               v.setBackground(PRESSED_STATE_COLOR);
               v.setAlpha(1.0f);
                    }
                }

       private void enableDuplicateView(View v) {
           RecordSegment segment = this.mSegment.get();
           if (segment != null) {
               int duration = segment.getDurationMs();
                        if (((long) (duration * 2)) + this.mCurrentProgressWithoutSelectedSegment < this.mMaxDuration) {
                   this.mExtraProgressDuration = segment.getDurationMs();
                   updateProgressView("enableDuplicateView");
                   v.setBackground(PRESSED_STATE_COLOR);
                   v.setAlpha(1.0f);
                        }
                    }
                }

       private void enableSilenceView(View v) {
           v.setBackground(PRESSED_STATE_COLOR);
           v.setAlpha(1.0f);
                }

       private void disableView(View v) {
           v.setBackground(null);
           if (v == this.mSilenceButton) {
               RecordSegment segment = this.mSegment.get();
               if (segment != null) {
                   v.setAlpha(segment.isSilenced() ? 1.0f : 0.35f);
                   return;
                        } else {
                   v.setAlpha(0.35f);
                   return;
                        }
                    }
           if (v != this.mDuplicateButton || v.getAlpha() != 0.175f) {
               v.setAlpha(0.35f);
                    }
                }

                
                @Override 
       public boolean onTouch(View v, MotionEvent event) {
           int action = event.getAction();
                    switch (action) {
                        case 0:
                   if (v == this.mTrashButton) {
                       enableTrashView(v);
                            } else if (v == this.mSilenceButton) {
                       enableSilenceView(v);
                            } else if (v == this.mDuplicateButton) {
                       enableDuplicateView(v);
                            }
                            return false;
                        case 1:
                   disableView(v);
                   this.mExtraProgressDuration = 0;
                   updateProgressView("OnTouchUp", 200);
                            return false;
                        default:
                            return false;
                    }
                }

                @Override 
       public void onClick(View v) {
           VineRecorder recorder = getRecorder();
           RecordSegment segment = this.mSegment.get();
           if (recorder != null && segment != null) {
               int id = v.getId();
               if (id == 2131427544) {
                   SegmentEditorAdapter adapter = recorder.getEditorAdatper();
                   int count = adapter.getCount();
                   int pos = -1;
                            for (int i = 0; i < count; i++) {
                       if (segment == adapter.getItem(i)) {
                           pos = i;
                                    break;
                                }
                            }
                   if (pos >= 0) {
                       addNewSegmentAsDuplicate(recorder, pos);
                       return;
                            }
                   return;
                        }
               if (id == 2131427546) {
                   silence(segment);
                        } else if (id == 2131427545) {
                   delete(recorder);
                        }
                    }
                }

       private void delete(VineRecorder recorder) {
                    int selection;
           this.mDragSortWidget.deleteSelection();
           int selection2 = this.mDragSortWidget.getSelection();
           SegmentEditorAdapter adapter = recorder.getEditorAdatper();
           if (adapter.getCount() <= 1) {
               selection = -1;
                    } else if (selection2 == adapter.getCount() - 1) {
               selection = selection2 - 1;
                    } else {
               selection = selection2 + 1;
                    }
           RecordSegment segment = null;
           if (selection > -1 && selection < adapter.getCount()) {
               segment = (RecordSegment) adapter.getItem(selection);
                    }
           recorder.changePlayButtonOnClickBehavior(true, true);
           recorder.showThumbnailOverlay(segment);
           recorder.pausePreview(adapter.getCount() > 1);
                }

       private void addNewSegmentAsDuplicate(VineRecorder recorder, int pos) {
           SegmentEditorAdapter adapter = recorder.getEditorAdatper();
           RecordSegment item = this.mSegment.get();
           if (item != null) {
               if (canDuplicate(item)) {
                            try {
                       recorder.saveSegmentIfNeeded(item);
                            } catch (IOException e) {
                       CrashUtil.logOrThrowInDebug(e);
                            }
                   adapter.addSegment(item, new RecordSegment(item), pos + 1, true);
                   Handler handler = getHandler();
                   if (handler != null) {
                       handler.post(new RunnableC04576());
                            }
                   this.mCurrentProgressWithoutSelectedSegment += (long) item.getDurationMs();
                   invalidateDuplicateButton(item);
                   updateProgressView("addNewSegmentAsDuplicate");
                   return;
                        }
               if (this.mActivity != null) {
                   if (this.mToast == null) {
                       this.mToast = Toast.makeText(this.mActivity, C0257R.string.error_duplicate_too_long, 0);
                            } else {
                       this.mToast.setText(C0257R.string.error_duplicate_too_long);
                            }
                   this.mToast.show();
                        }
                    }
                }

                
       class RunnableC04576 implements Runnable {
           RunnableC04576() {
                    }

                    @Override 
           public void run() {
               IndividualEditPlugin.access$800(IndividualEditPlugin.this).forceListLayout();
                    }
                }

       private void unTrim() {
           onRangeSeekBarValuesChanged(null, 0, 100, true);
                }

                
       class RunnableC04587 implements Runnable {
           RunnableC04587() {
                    }

                    @Override 
           public void run() {
                        int seekTo;
               RecordSegment segment = (RecordSegment) IndividualEditPlugin.access$200(IndividualEditPlugin.this).get();
               if (segment != null && IndividualEditPlugin.access$900(IndividualEditPlugin.this) != null) {
                   int durationMs = segment.getUnTrimmedAudioDurationMs();
                   if (IndividualEditPlugin.access$1000(IndividualEditPlugin.this)) {
                       seekTo = (int) ((IndividualEditPlugin.access$1100(IndividualEditPlugin.this) / 100.0f) * durationMs);
                            } else {
                       seekTo = (int) ((IndividualEditPlugin.access$1200(IndividualEditPlugin.this) / 100.0f) * durationMs);
                            }
                   SLog.m465i("Seek Video to: {}.", Integer.valueOf(seekTo));
                   IndividualEditPlugin.access$900(IndividualEditPlugin.this).seekTo(seekTo);
                   if (!IndividualEditPlugin.access$1300(IndividualEditPlugin.this)) {
                                try {
                           int duration = segment.getUnTrimmedAudioDurationMs();
                           if (duration > 0) {
                               int startDuration = (int) ((IndividualEditPlugin.access$1100(IndividualEditPlugin.this) / 100.0f) * duration);
                               long totalAllowed = (long) (IndividualEditPlugin.access$500(IndividualEditPlugin.this) - IndividualEditPlugin.access$1400(IndividualEditPlugin.this));
                               int endDuration = (int) ((IndividualEditPlugin.access$1200(IndividualEditPlugin.this) / 100.0f) * duration);
                               boolean canUntrim = totalAllowed > ((long) (endDuration - startDuration));
                               if (!canUntrim) {
                                   endDuration = (int) (Math.min(totalAllowed, endDuration - startDuration) + ((long) startDuration));
                                        }
                               if (canUntrim && IndividualEditPlugin.access$1100(IndividualEditPlugin.this) == 0 && IndividualEditPlugin.access$1200(IndividualEditPlugin.this) == 100) {
                                   segment.setTrimData(null);
                                        } else {
                                   segment.setTrimData(new TrimData(startDuration, endDuration));
                                   SLog.m449d("Segment trimmed: start={} -> end={} (untrimmed duration={}).", Long.valueOf(segment.getTrimmedVideoStartMs()), Integer.valueOf(segment.getTrimmedAudioEndUs()), Integer.valueOf(duration));
                                        }
                               IndividualEditPlugin.access$1500(IndividualEditPlugin.this, segment);
                               if (segment.isTrimmed()) {
                                   long newStart = segment.getTrimmedVideoStartUs();
                                   long newEnd = segment.getTrimmedAudioEndUs();
                                            if (newStart != IndividualEditPlugin.access$1600(IndividualEditPlugin.this) || newEnd != IndividualEditPlugin.access$1700(IndividualEditPlugin.this)) {
                                       SLog.m453d("Segment trimmed: {} -> {}, {} -> {}.", new Object[]{Long.valueOf(newStart), Long.valueOf(IndividualEditPlugin.access$1600(IndividualEditPlugin.this)), Long.valueOf(newEnd), Long.valueOf(IndividualEditPlugin.access$1700(IndividualEditPlugin.this))});
                                       IndividualEditPlugin.access$1800(IndividualEditPlugin.this, newStart, newEnd);
                                            }
                                   IndividualEditPlugin.access$1900(IndividualEditPlugin.this).setSelectedMinValue((int) ((IndividualEditPlugin.access$1600(IndividualEditPlugin.this) / 10) / ((long) duration)));
                                   IndividualEditPlugin.access$1900(IndividualEditPlugin.this).setSelectedMaxValue((int) ((IndividualEditPlugin.access$1700(IndividualEditPlugin.this) / 10) / ((long) duration)));
                                   IndividualEditPlugin.access$1900(IndividualEditPlugin.this).notifyListenerOfValueChange();
                                   return;
                                        }
                               SLog.m446d("Segment untrimmed.");
                               IndividualEditPlugin.access$1800(IndividualEditPlugin.this, -1L, -1L);
                               IndividualEditPlugin.access$1900(IndividualEditPlugin.this).setSelectedMinValue(0);
                               IndividualEditPlugin.access$1900(IndividualEditPlugin.this).setSelectedMaxValue(100);
                               IndividualEditPlugin.access$1900(IndividualEditPlugin.this).notifyListenerOfValueChange();
                                    }
                                } catch (TrimData.InvalidTrimException e) {
                           throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }

       private void onPlayerStartAndEndChanged(long newStart, long newEnd) {
           this.mPlayerStart = newStart;
           this.mPlayerEnd = newEnd;
           VineRecorder recorder = getRecorder();
           if (recorder != null) {
               recorder.setVideoTextureSeekPoints(this.mPlayerStart, this.mPlayerEnd);
                    }
                }

       private void invalidateTrimData() {
           Handler handler = getHandler();
           if (handler != null) {
               handler.removeCallbacks(this.mTrimRunnable);
               handler.postDelayed(this.mTrimRunnable, 20L);
                    }
                }

                @Override 
       public void onRangeSeekBarTouchDown(RangeSeekBar rangeSeekBar, int minValue, int maxValue) {
           this.mRangeSeekBarTouched = true;
           this.mCurrentRangeSeekBarMin = minValue;
           this.mCurrentRangeSeekBarMax = maxValue;
           RecordSegment segment = null;
           if (this.mSegment != null) {
               RecordSegment segment2 = this.mSegment.get();
                        segment = segment2;
                    }
           if (segment != null) {
               this.mRangeSeekBarTouchedUnTrimmedDuration = segment.getUnTrimmedAudioDurationMs();
                    }
           this.mExtraProgressDuration = (int) (((this.mCurrentRangeSeekBarMax - this.mCurrentRangeSeekBarMin) / 100.0f) * this.mRangeSeekBarTouchedUnTrimmedDuration);
           this.mSeekToLeft = rangeSeekBar.seekToLeft();
           this.mVideoPlayer.setAutoPlayOnPrepared(false);
           VineRecorder recorder = getRecorder();
           if (recorder != null) {
               recorder.animatePlayButton(false);
                    }
           if (segment != null) {
               if (segment.isTrimmed()) {
                   onPlayerStartAndEndChanged(segment.getTrimmedVideoStartUs(), segment.getTrimmedAudioEndUs());
                        } else {
                   onPlayerStartAndEndChanged(-1L, -1L);
                        }
                    }
           invalidateTrimData();
                }

                @Override 
       public void onRangeSeekBarValuesChanged(RangeSeekBar rangeSeekBar, int minValue, int maxValue, boolean byUser) {
           SLog.m466i("Range seek bar value changed: {} {}.", Integer.valueOf(minValue), Integer.valueOf(maxValue));
           this.mCurrentRangeSeekBarMin = minValue;
           this.mCurrentRangeSeekBarMax = maxValue;
           if (byUser) {
               this.mExtraProgressDuration = (int) (((this.mCurrentRangeSeekBarMax - this.mCurrentRangeSeekBarMin) / 100.0f) * this.mRangeSeekBarTouchedUnTrimmedDuration);
               invalidateTrimData();
                    } else {
               this.mRangeSeekBarTouchedUnTrimmedDuration = -1;
               this.mExtraProgressDuration = 0;
                    }
           updateProgressView("onRangeSeekBarValuesChanged");
                }

                @Override 
       public void onRangeSeekBarTouchUp(RangeSeekBar rangeSeekBar, int minValue, int maxValue) {
           this.mRangeSeekBarTouched = false;
           onRangeSeekBarValuesChanged(rangeSeekBar, minValue, maxValue, true);
           this.mVideoPlayer.setAutoPlayOnPrepared(true);
                }

                @Override 
       public void onRangeSeekBarLongPressHappened() {
           this.mRangeSeekBarTouched = false;
           onRangeSeekBarValuesChanged(null, 0, 100, true);
           this.mVideoPlayer.setAutoPlayOnPrepared(true);
                }

       public boolean isEdited() {
           return this.mEdited;
                }

       public void setEdited(boolean edited) {
           this.mEdited = edited;
                }
            }
