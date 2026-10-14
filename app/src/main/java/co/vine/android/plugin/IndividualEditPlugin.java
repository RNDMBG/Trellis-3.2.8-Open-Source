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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class IndividualEditPlugin extends BaseRecorderPlugin<View, VineRecorder> implements DragSortWidget.FloatViewInteractionListener, View.OnTouchListener, View.OnClickListener, RangeSeekBar.OnRangeSeekBarChangeListener {
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

/* 46 */        static /* synthetic */ View access$000(IndividualEditPlugin x0) {
/* 47 */            return x0.mEditorDoneButton;
                }

/* 46 */        static /* synthetic */ View access$100(IndividualEditPlugin x0) {
/* 47 */            return x0.mEditorCancelButton;
                }

/* 46 */        static /* synthetic */ boolean access$1000(IndividualEditPlugin x0) {
/* 47 */            return x0.mSeekToLeft;
                }

/* 46 */        static /* synthetic */ int access$1100(IndividualEditPlugin x0) {
/* 47 */            return x0.mCurrentRangeSeekBarMin;
                }

/* 46 */        static /* synthetic */ int access$1200(IndividualEditPlugin x0) {
/* 47 */            return x0.mCurrentRangeSeekBarMax;
                }

/* 46 */        static /* synthetic */ boolean access$1300(IndividualEditPlugin x0) {
/* 47 */            return x0.mRangeSeekBarTouched;
                }

/* 46 */        static /* synthetic */ long access$1400(IndividualEditPlugin x0) {
/* 47 */            return x0.mCurrentProgressWithoutSelectedSegment;
                }

/* 46 */        static /* synthetic */ void access$1500(IndividualEditPlugin x0, RecordSegment x1) {
/* 47 */            x0.invalidateDuplicateButton(x1);
                }

/* 46 */        static /* synthetic */ long access$1600(IndividualEditPlugin x0) {
/* 47 */            return x0.mPlayerStart;
                }

/* 46 */        static /* synthetic */ long access$1700(IndividualEditPlugin x0) {
/* 47 */            return x0.mPlayerEnd;
                }

/* 46 */        static /* synthetic */ void access$1800(IndividualEditPlugin x0, long x1, long x2) {
/* 47 */            x0.onPlayerStartAndEndChanged(x1, x2);
                }

/* 46 */        static /* synthetic */ RangeSeekBar access$1900(IndividualEditPlugin x0) {
/* 47 */            return x0.mTrimmer;
                }

/* 46 */        static /* synthetic */ WeakReference access$200(IndividualEditPlugin x0) {
/* 47 */            return x0.mSegment;
                }

/* 46 */        static /* synthetic */ int access$300(IndividualEditPlugin x0) {
/* 47 */            return x0.mRangeSeekBarTouchedUnTrimmedDuration;
                }

/* 46 */        static /* synthetic */ int access$400(IndividualEditPlugin x0) {
/* 47 */            return x0.mExtraProgressDuration;
                }

/* 46 */        static /* synthetic */ float access$500(IndividualEditPlugin x0) {
/* 47 */            return x0.mMaxDuration;
                }

/* 46 */        static /* synthetic */ RegularProgressView access$600(IndividualEditPlugin x0) {
/* 47 */            return x0.mProgressView;
                }

/* 46 */        static /* synthetic */ String access$700(IndividualEditPlugin x0) {
/* 47 */            return x0.mLastUpdateProgressViewTag;
                }

/* 46 */        static /* synthetic */ DragSortWidget access$800(IndividualEditPlugin x0) {
/* 47 */            return x0.mDragSortWidget;
                }

/* 46 */        static /* synthetic */ SdkVideoView access$900(IndividualEditPlugin x0) {
/* 47 */            return x0.mVideoPlayer;
                }

/* 93 */        public IndividualEditPlugin() {
/* 94 */            super("IndividualEdit");
/* 59 */            this.mIntersectBounds = new Rect();
/* 60 */            this.mIntersectLoc = new int[2];
/* 87 */            this.mRangeSeekBarTouchedUnTrimmedDuration = -1;
/* 278 */           this.mUnsavedChangesEditedListener = new C04554();
/* 335 */           this.mUpdateProgressViewRunnable = new RunnableC04565();
/* 797 */           this.mTrimRunnable = new RunnableC04587();
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 98 */        public View onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
/* 99 */            View view = fragment.getView();
/* 100 */           Activity activity = fragment.getActivity();
/* 101 */           Resources res = activity.getResources();
/* 102 */           setEdited(false);
/* 103 */           this.mEditorButtons = view.findViewById(C0257R.id.editorButtons);
/* 104 */           this.mEditorButtons.setOnTouchListener(new ViewOnTouchListenerC04521());
/* 110 */           this.mEditorDoneButton = view.findViewById(C0257R.id.editorDoneButton);
/* 111 */           this.mEditorDoneButton.setOnClickListener(new ViewOnClickListenerC04532());
/* 120 */           this.mEditorCancelButton = view.findViewById(C0257R.id.editorCancelButton);
/* 121 */           this.mEditorCancelButton.setOnClickListener(new ViewOnClickListenerC04543());
/* 130 */           this.mIndividualEditContainer = view.findViewById(C0257R.id.individual_edit_tools_container);
/* 131 */           this.mDuplicateButton = this.mIndividualEditContainer.findViewById(C0257R.id.duplicateButton);
/* 132 */           this.mDuplicateButton.setOnClickListener(this);
/* 133 */           this.mDuplicateButton.setOnTouchListener(this);
/* 134 */           this.mTrashButton = this.mIndividualEditContainer.findViewById(C0257R.id.trashCanButtonIndividual);
/* 135 */           this.mTrashButton.setOnClickListener(this);
/* 136 */           this.mTrashButton.setOnTouchListener(this);
/* 137 */           this.mSilenceButton = this.mIndividualEditContainer.findViewById(C0257R.id.silenceButton);
/* 138 */           this.mSilenceButton.setOnClickListener(this);
/* 139 */           this.mSilenceButton.setOnTouchListener(this);
/* 141 */           Point size = SystemUtil.getDisplaySize(activity);
/* 142 */           this.mTwoThirdXOnScreen = (size.x * 2) / 3;
/* 143 */           this.mOneThirdXOnScreen = size.x / 3;
/* 145 */           this.mSize = size.x;
/* 147 */           int selectedTrimColor = res.getColor(C0257R.color.timebar_selected_color);
/* 148 */           this.mProgressView = (RegularProgressView) view.findViewById(C0257R.id.edit_progress);
/* 149 */           this.mProgressView.setSelectedColor(res.getColor(C0257R.color.trim_yellow));
/* 150 */           this.mProgressView.setColor(res.getColor(C0257R.color.vine_green));
/* 152 */           this.mDragSortWidget = (DragSortWidget) view.findViewById(C0257R.id.thumbnail_list);
/* 153 */           this.mDragSortWidget.setFloatViewInteractionListener(this);
/* 155 */           this.mAllDeletedOverlay = inflater.inflate(C0257R.layout.plugin_all_deleted_overlay, (ViewGroup) this.mDragSortWidget, false);
/* 158 */           RelativeLayout trimmerContainer = (RelativeLayout) view.findViewById(C0257R.id.above_bottom_mask_container);
/* 160 */           if (this.mTrimmer == null) {
/* 161 */               RangeSeekBar trimmer = new RangeSeekBar((NinePatchDrawable) activity.getResources().getDrawable(C0257R.drawable.scrub_left), (NinePatchDrawable) activity.getResources().getDrawable(C0257R.drawable.scrub_right), 0, 100, activity, selectedTrimColor, activity.getResources().getColor(C0257R.color.black_thirty_five_percent));
/* 167 */               trimmer.setSelectedMinValue(0);
/* 168 */               trimmer.setSelectedMaxValue(100);
/* 169 */               trimmer.setVisibility(8);
/* 170 */               trimmer.setOnRangeSeekBarChangeListener(this);
/* 172 */               int height = activity.getResources().getDimensionPixelSize(C0257R.dimen.trimmer_height);
/* 173 */               trimmer.setLayoutParams(new RelativeLayout.LayoutParams(-1, height));
/* 174 */               trimmerContainer.addView(trimmer);
/* 176 */               RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) trimmerContainer.getLayoutParams();
/* 177 */               param.bottomMargin = activity.getResources().getDimensionPixelOffset(C0257R.dimen.trimmer_margin);
/* 178 */               trimmerContainer.setLayoutParams(param);
/* 179 */               this.mTrimmer = trimmer;
                    }
/* 181 */           ViewParent trimmerParent = this.mTrimmer.getParent();
/* 182 */           if (trimmerParent != null && trimmerParent != trimmerContainer) {
/* 183 */               ((ViewGroup) trimmerParent).removeView(this.mTrimmer);
/* 186 */               return null;
                    }
/* 186 */           return null;
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.IndividualEditPlugin$1 */
/* 102 */       class ViewOnTouchListenerC04521 implements View.OnTouchListener {
/* 103 */           ViewOnTouchListenerC04521() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 106 */           public boolean onTouch(View v, MotionEvent event) {
/* 107 */               return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.IndividualEditPlugin$2 */
/* 109 */       class ViewOnClickListenerC04532 implements View.OnClickListener {
/* 110 */           ViewOnClickListenerC04532() {
                    }

                    @Override // android.view.View.OnClickListener
/* 113 */           public void onClick(View v) {
/* 114 */               VineRecorder recorder = IndividualEditPlugin.this.getRecorder();
/* 115 */               if (recorder != null) {
/* 116 */                   recorder.onEditorDone(IndividualEditPlugin.access$000(IndividualEditPlugin.this));
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.IndividualEditPlugin$3 */
/* 119 */       class ViewOnClickListenerC04543 implements View.OnClickListener {
/* 120 */           ViewOnClickListenerC04543() {
                    }

                    @Override // android.view.View.OnClickListener
/* 123 */           public void onClick(View v) {
/* 124 */               VineRecorder recorder = IndividualEditPlugin.this.getRecorder();
/* 125 */               if (recorder != null) {
/* 126 */                   recorder.onEditorCancel(IndividualEditPlugin.access$100(IndividualEditPlugin.this));
                        }
                    }
                }

/* 189 */       private void silence(RecordSegment segment) {
/* 190 */           if (segment.isSilenced()) {
/* 191 */               segment.setSilenced(false);
/* 192 */               this.mSilenceButton.setAlpha(0.35f);
                    } else {
/* 194 */               this.mSilenceButton.setAlpha(1.0f);
/* 195 */               segment.setSilenced(true);
                    }
/* 197 */           if (this.mVideoPlayer != null) {
/* 198 */               this.mVideoPlayer.setMute(segment.isSilenced());
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 203 */       public void onSessionSwapped() {
                    RecordConfigUtils.RecordConfig config;
/* 204 */           VineRecorder recorder = getRecorder();
/* 205 */           if (recorder != null && (config = recorder.getConfig()) != null) {
/* 208 */               this.mMaxDuration = config.maxDuration;
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 214 */       public void onIndividualSegmentClicked(RecordSegment segment, SdkVideoView player) {
/* 215 */           this.mVideoPlayer = player;
/* 216 */           this.mSegment = new WeakReference<>(segment);
/* 217 */           setEdited(true);
/* 218 */           Handler handler = getHandler();
/* 219 */           if (handler != null) {
/* 220 */               handler.removeCallbacks(this.mTrimRunnable);
                    }
/* 223 */           if (segment == null) {
/* 224 */               hideIndividualSegmentControls();
                    } else {
/* 226 */               showIndividualSegmentControls(segment);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 231 */       public void onAnimateEditModeControlsInUI() {
/* 232 */           if (this.mEditorButtons != null) {
/* 233 */               this.mEditorButtons.setAlpha(0.0f);
/* 234 */               this.mEditorButtons.setVisibility(0);
/* 235 */               this.mEditorButtons.animate().alpha(1.0f).setDuration(250L).start();
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 240 */       public void onAnimateEditModeControlsOutUI() {
/* 241 */           if (this.mEditorButtons != null) {
/* 242 */               this.mEditorButtons.animate().alpha(0.0f).setDuration(250L).setListener(new ViewGoneAnimationListener(this.mEditorButtons)).start();
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 248 */       public boolean onBackButtonPressed(boolean isEditing) {
/* 249 */           VineRecorder recorder = getRecorder();
/* 250 */           if (recorder != null) {
/* 251 */               if (this.mIndividualEditContainer.getVisibility() == 0) {
/* 252 */                   hideIndividualSegmentControls();
/* 253 */                   this.mDragSortWidget.setSelection(-1);
/* 254 */                   recorder.playPreview(null, true, true, "ClipBackButton");
                            return true;
                        }
/* 256 */               if (this.mEditorButtons.getVisibility() == 0) {
/* 257 */                   if (this.mActivity instanceof AbstractRecordingActivity) {
/* 258 */                       if (isEdited()) {
/* 259 */                           AbstractRecordingActivity activity = (AbstractRecordingActivity) this.mActivity;
/* 260 */                           int dialogId = this.mManager.getDialogIdForPlugin(this, 0);
/* 261 */                           PromptDialogFragment dialog = activity.createDialogForPlugins(dialogId);
/* 262 */                           dialog.setListener(this.mUnsavedChangesEditedListener);
/* 263 */                           dialog.setMessage(C0257R.string.cancel_edited_changes_confirm);
/* 264 */                           dialog.setPositiveButton(C0257R.string.save);
/* 265 */                           dialog.setNegativeButton(C0257R.string.discard_changes);
/* 266 */                           activity.showDialog(dialog);
                                } else {
/* 268 */                           recorder.setEditMode(false, true);
                                }
                            }
                            return true;
                        }
                    }
                    return false;
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.IndividualEditPlugin$4 */
/* 277 */       class C04554 implements PromptDialogFragment.OnDialogDoneListener {
/* 278 */           C04554() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 281 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
/* 282 */               VineRecorder recorder = IndividualEditPlugin.this.getRecorder();
/* 283 */               if (recorder != null && recorder.hasSessionFile()) {
                            switch (which) {
                                case -2:
/* 286 */                           recorder.setEditMode(false, true);
                                    break;
                                case -1:
/* 290 */                           recorder.setEditMode(false, false);
                                    break;
                            }
                        }
                    }
                }

/* 298 */       private void showIndividualSegmentControls(RecordSegment segment) {
/* 299 */           this.mTrimmer.setVisibility(0);
/* 300 */           disableView(this.mDuplicateButton);
/* 301 */           disableView(this.mTrashButton);
/* 302 */           disableView(this.mSilenceButton);
/* 303 */           this.mExtraProgressDuration = 0;
/* 304 */           updateProgressView("showIndividualSegmentControls");
/* 305 */           this.mEditorButtons.setVisibility(8);
/* 306 */           this.mIndividualEditContainer.setVisibility(0);
/* 307 */           if (segment.isSilenced()) {
/* 308 */               this.mSilenceButton.setAlpha(1.0f);
                    } else {
/* 310 */               this.mSilenceButton.setAlpha(0.35f);
                    }
/* 312 */           if (segment.isTrimmed()) {
/* 313 */               float total = segment.getUnTrimmedAudioDurationMs();
/* 314 */               this.mTrimmer.forceResetNormalizedValues(segment.getTrimmedVideoStartMs() / total, (segment.getTrimmedAudioEndUs() / 1000) / total);
                    } else {
/* 317 */               this.mTrimmer.forceResetNormalizedValues(0.0d, 1.0d);
                    }
/* 319 */           invalidateDuplicateButton(segment);
/* 320 */           this.mCurrentProgressWithoutSelectedSegment = getDuration() - ((long) segment.getDurationMs());
                }

/* 323 */       private void invalidateDuplicateButton(RecordSegment recordSegment) {
/* 324 */           if (canDuplicate(recordSegment)) {
/* 325 */               this.mDuplicateButton.setAlpha(0.35f);
                    } else {
/* 327 */               this.mDuplicateButton.setAlpha(0.175f);
                    }
                }

/* 331 */       private boolean canDuplicate(RecordSegment segment) {
                    return ((float) (this.mCurrentProgressWithoutSelectedSegment + ((long) (segment.getDurationMs() * 2)))) < this.mMaxDuration;
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.IndividualEditPlugin$5 */
/* 333 */       class RunnableC04565 implements Runnable {
/* 334 */           RunnableC04565() {
                    }

                    @Override // java.lang.Runnable
/* 337 */           public void run() {
                        int segmentDuration;
/* 338 */               long duration = 0;
/* 339 */               RecordSegment segment = null;
/* 340 */               if (IndividualEditPlugin.access$200(IndividualEditPlugin.this) != null) {
/* 341 */                   segment = (RecordSegment) IndividualEditPlugin.access$200(IndividualEditPlugin.this).get();
                        }
/* 344 */               if (segment != null) {
/* 345 */                   segmentDuration = segment.getDurationMs();
                        } else {
/* 347 */                   segmentDuration = 0;
                        }
/* 349 */               int start = 0;
/* 350 */               VineRecorder recorder = IndividualEditPlugin.this.getRecorder();
/* 351 */               if (recorder != null) {
/* 352 */                   SegmentEditorAdapter adapter = recorder.getEditorAdatper();
/* 353 */                   if (adapter != null) {
/* 354 */                       ArrayList<RecordSegment> data = adapter.getData();
                                for (RecordSegment s : data) {
/* 356 */                           if (segment == s) {
/* 357 */                               start = (int) duration;
/* 358 */                               if (IndividualEditPlugin.access$300(IndividualEditPlugin.this) < 0) {
/* 359 */                                   duration += (long) s.getDurationMs();
                                        }
                                    } else {
/* 362 */                               duration += (long) s.getDurationMs();
                                    }
                                }
                            } else {
/* 366 */                       duration = recorder.getCurrentDuration();
                            }
                        }
/* 369 */               SLog.m447d("Update progress ratio: {}", Float.valueOf((((long) IndividualEditPlugin.access$400(IndividualEditPlugin.this)) + duration) / IndividualEditPlugin.access$500(IndividualEditPlugin.this)));
/* 370 */               IndividualEditPlugin.access$600(IndividualEditPlugin.this).setProgressRatio((((long) IndividualEditPlugin.access$400(IndividualEditPlugin.this)) + duration) / IndividualEditPlugin.access$500(IndividualEditPlugin.this));
/* 371 */               if (IndividualEditPlugin.access$300(IndividualEditPlugin.this) >= 0) {
                            segmentDuration = 0;
                        }
                        long endAdjustment = segmentDuration;
/* 372 */               long end = ((long) (IndividualEditPlugin.access$400(IndividualEditPlugin.this) + start)) + endAdjustment;
                        if (end >= start) {
/* 374 */                   IndividualEditPlugin.access$600(IndividualEditPlugin.this).setSelectedSection(start / IndividualEditPlugin.access$500(IndividualEditPlugin.this), end / IndividualEditPlugin.access$500(IndividualEditPlugin.this));
                        } else {
/* 376 */                   CrashUtil.logException(new IllegalStateException(MessageFormatter.toStringMessage("Start {}, Extra {}, Duration {}, From {}", Integer.valueOf(start), Integer.valueOf(IndividualEditPlugin.access$400(IndividualEditPlugin.this)), Long.valueOf(endAdjustment), IndividualEditPlugin.access$700(IndividualEditPlugin.this))));
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 385 */       public void onPause() {
/* 386 */           super.onPause();
/* 387 */           if (this.mSegment != null && this.mSegment.get() != null) {
/* 388 */               hideIndividualSegmentControls();
                    }
/* 390 */           Handler handler = getHandler();
/* 391 */           if (handler != null) {
/* 392 */               handler.removeCallbacks(this.mUpdateProgressViewRunnable);
                    }
                }

/* 396 */       private void updateProgressView(String tag) {
/* 397 */           updateProgressView(tag, 10);
                }

/* 400 */       private void updateProgressView(String tag, int delay) {
/* 401 */           Handler handler = getHandler();
/* 402 */           if (handler != null) {
/* 403 */               this.mLastUpdateProgressViewTag = tag;
/* 404 */               handler.removeCallbacks(this.mUpdateProgressViewRunnable);
/* 405 */               handler.postDelayed(this.mUpdateProgressViewRunnable, delay);
                    }
                }

/* 409 */       private long getDuration() {
/* 410 */           VineRecorder recorder = getRecorder();
/* 411 */           if (recorder != null) {
/* 412 */               SegmentEditorAdapter adapter = recorder.getEditorAdatper();
/* 413 */               if (adapter != null) {
/* 414 */                   return adapter.getDuration();
                        }
/* 416 */               return recorder.getCurrentDuration();
                    }
/* 419 */           return -1L;
                }

/* 422 */       private void hideIndividualSegmentControls() {
                    ViewGroup container;
/* 423 */           long duration = getDuration();
                    if (duration >= 0) {
                        if (duration == 0) {
/* 426 */                   ViewGroup parent = (ViewGroup) this.mAllDeletedOverlay.getParent();
/* 427 */                   if (parent != null) {
/* 428 */                       parent.removeView(this.mAllDeletedOverlay);
                            }
/* 430 */                   VineRecorder recorder = getRecorder();
/* 431 */                   if (recorder != null && (container = recorder.getVideoContainer()) != null) {
/* 434 */                       container.addView(this.mAllDeletedOverlay);
/* 435 */                       ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.mAllDeletedOverlay.getLayoutParams();
/* 437 */                       params.height = this.mSize;
/* 438 */                       if (this.mAllDeletedOverlay != null) {
/* 439 */                           this.mAllDeletedOverlay.setLayoutParams(params);
/* 440 */                           this.mAllDeletedOverlay.setVisibility(0);
                                }
                            }
                        } else if (this.mAllDeletedOverlay != null) {
/* 446 */                   this.mAllDeletedOverlay.setVisibility(8);
                        }
                    }
/* 450 */           this.mEditorButtons.setVisibility(0);
/* 451 */           this.mTrimmer.setVisibility(8);
/* 452 */           this.mIndividualEditContainer.setVisibility(8);
/* 453 */           this.mExtraProgressDuration = 0;
/* 454 */           disableView(this.mTrashButton);
/* 455 */           disableView(this.mSilenceButton);
/* 456 */           disableView(this.mDuplicateButton);
/* 457 */           updateProgressView("hideIndividualSegmentControls");
                }

                @Override // co.vine.android.dragsort.DragSortWidget.FloatViewInteractionListener
/* 461 */       public boolean canPickUpFloatView() {
/* 462 */           VineRecorder recorder = getRecorder();
                    return recorder != null && recorder.canPickUpFloatView();
                }

                @Override // co.vine.android.dragsort.DragSortWidget.FloatViewInteractionListener
/* 467 */       public void floatViewPickedUp(int item) {
/* 468 */           this.mPickedUpPosition = item;
/* 469 */           VineRecorder recorder = getRecorder();
/* 470 */           if (recorder != null) {
/* 471 */               SegmentEditorAdapter editor = recorder.getEditorAdatper();
/* 472 */               if (editor != null) {
/* 473 */                   RecordSegment segment = (RecordSegment) editor.getItem(item);
/* 474 */                   recorder.showThumbnailOverlay(segment);
/* 475 */                   recorder.changePlayButtonOnClickBehavior(false, true);
/* 476 */                   onIndividualSegmentClicked(segment, this.mVideoPlayer);
                        }
/* 478 */               recorder.pausePreview(false);
                    }
                }

                @Override // co.vine.android.dragsort.DragSortWidget.FloatViewInteractionListener
/* 483 */       public void floatViewMoved() {
/* 484 */           VineRecorder recorder = getRecorder();
/* 485 */           if (recorder != null) {
                        switch (floatViewIntersectsPosition()) {
                            case 0:
/* 488 */                       enableDuplicateView(this.mDuplicateButton);
/* 489 */                       disableView(this.mTrashButton);
/* 490 */                       disableView(this.mSilenceButton);
                                break;
                            case 1:
/* 494 */                       enableTrashView(this.mTrashButton);
/* 495 */                       disableView(this.mDuplicateButton);
/* 496 */                       disableView(this.mSilenceButton);
                                break;
                            case 2:
/* 500 */                       enableSilenceView(this.mSilenceButton);
/* 501 */                       disableView(this.mTrashButton);
/* 502 */                       disableView(this.mDuplicateButton);
/* 503 */                       this.mExtraProgressDuration = 0;
/* 504 */                       updateProgressView("floatViewMovedRight");
                                break;
                            default:
/* 509 */                       disableView(this.mSilenceButton);
/* 510 */                       disableView(this.mTrashButton);
/* 511 */                       disableView(this.mDuplicateButton);
/* 512 */                       this.mExtraProgressDuration = 0;
/* 513 */                       updateProgressView("floatViewMovedFalse");
                                break;
                        }
                    }
                }

/* 518 */       private boolean floatViewIntersectsTop() {
/* 519 */           if (this.mVideoPlayer != null) {
/* 520 */               int vidPlayerMarginTop = ((RelativeLayout.LayoutParams) this.mVideoPlayer.getLayoutParams()).topMargin;
/* 522 */               Rect floatViewBounds = this.mDragSortWidget.getFloatViewBounds(this.mIntersectBounds);
/* 523 */               if (floatViewBounds != null) {
/* 524 */                   if (this.mDragSortWidget.floatViewIsFromContentView()) {
                                return floatViewBounds.top < vidPlayerMarginTop;
                            }
/* 527 */                   int videoPlayerMiddle = ((this.mVideoPlayer.getBottom() - this.mVideoPlayer.getTop()) / 2) + vidPlayerMarginTop;
/* 529 */                   return floatViewBounds.top < videoPlayerMiddle;
                        }
                    }
                    return false;
                }

/* 536 */       private int floatViewIntersectsPosition() {
                    int[] loc;
/* 537 */           if (floatViewIntersectsTop() && (loc = this.mDragSortWidget.getFloatViewPosition(this.mIntersectLoc)) != null) {
/* 540 */               if (loc[0] > this.mTwoThirdXOnScreen) {
/* 541 */                   return 2;
                        }
                        return loc[0] > this.mOneThirdXOnScreen ? 1 : 0;
                    }
/* 549 */           return -1;
                }

                @Override // co.vine.android.dragsort.DragSortWidget.FloatViewInteractionListener
/* 553 */       public boolean floatViewDropped(int to) {
/* 554 */           disableView(this.mDuplicateButton);
/* 555 */           disableView(this.mTrashButton);
/* 556 */           disableView(this.mSilenceButton);
/* 557 */           int extraProgress = this.mExtraProgressDuration;
/* 558 */           this.mExtraProgressDuration = 0;
/* 559 */           VineRecorder recorder = getRecorder();
/* 560 */           if (recorder != null) {
/* 561 */               SegmentEditorAdapter editorAdapter = recorder.getEditorAdatper();
/* 562 */               int action = floatViewIntersectsPosition();
/* 563 */               boolean shouldPlayerChange = false;
                        switch (action) {
                            case 0:
/* 566 */                       if (editorAdapter.getCount() > to) {
/* 567 */                           addNewSegmentAsDuplicate(recorder, to);
                                }
/* 569 */                       shouldPlayerChange = true;
                                break;
                            case 1:
/* 573 */                       shouldPlayerChange = true;
/* 574 */                       this.mExtraProgressDuration = extraProgress;
                                break;
                            case 2:
/* 578 */                       if (editorAdapter.getCount() > to) {
/* 579 */                           silence((RecordSegment) editorAdapter.getItem(to));
                                }
/* 581 */                       shouldPlayerChange = true;
                                break;
                        }
/* 587 */               if (this.mPickedUpPosition != to || shouldPlayerChange) {
/* 588 */                   recorder.changePlayButtonOnClickBehavior(true, true);
                        }
/* 590 */               if (!shouldPlayerChange || editorAdapter.getCount() != 1) {
/* 591 */                   recorder.animatePlayButton(true);
                        }
/* 594 */               updateProgressView("floatViewDropped");
                        switch (action) {
                            case 0:
                                return true;
                            case 1:
/* 601 */                       delete(recorder);
                                return false;
                            case 2:
                                return true;
                        }
                    }
                    return true;
                }

                @Override // co.vine.android.dragsort.DragSortWidget.FloatViewInteractionListener
/* 615 */       public void floatViewLanded(int to) {
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 619 */       public boolean onSetEditMode(boolean editing, boolean hasData) {
/* 620 */           this.mIndividualEditContainer.setVisibility(8);
/* 621 */           if (editing) {
/* 622 */               this.mProgressView.setVisibility(0);
/* 623 */               this.mSegment = new WeakReference<>(null);
/* 624 */               updateProgressView("onSetEditMode");
                    } else {
/* 626 */               this.mProgressView.setVisibility(8);
/* 627 */               this.mAllDeletedOverlay.setVisibility(8);
                    }
/* 629 */           return super.onSetEditMode(editing, hasData);
                }

/* 632 */       private void enableTrashView(View v) {
/* 633 */           if (this.mDragSortWidget.canDelete()) {
/* 634 */               RecordSegment segment = this.mSegment.get();
/* 635 */               if (segment != null) {
/* 636 */                   this.mExtraProgressDuration = -segment.getDurationMs();
/* 637 */                   updateProgressView("enableTrashView");
                        }
/* 639 */               v.setBackground(PRESSED_STATE_COLOR);
/* 640 */               v.setAlpha(1.0f);
                    }
                }

/* 644 */       private void enableDuplicateView(View v) {
/* 645 */           RecordSegment segment = this.mSegment.get();
/* 646 */           if (segment != null) {
/* 647 */               int duration = segment.getDurationMs();
                        if (((long) (duration * 2)) + this.mCurrentProgressWithoutSelectedSegment < this.mMaxDuration) {
/* 649 */                   this.mExtraProgressDuration = segment.getDurationMs();
/* 650 */                   updateProgressView("enableDuplicateView");
/* 651 */                   v.setBackground(PRESSED_STATE_COLOR);
/* 652 */                   v.setAlpha(1.0f);
                        }
                    }
                }

/* 657 */       private void enableSilenceView(View v) {
/* 658 */           v.setBackground(PRESSED_STATE_COLOR);
/* 659 */           v.setAlpha(1.0f);
                }

/* 662 */       private void disableView(View v) {
/* 663 */           v.setBackground(null);
/* 664 */           if (v == this.mSilenceButton) {
/* 665 */               RecordSegment segment = this.mSegment.get();
/* 666 */               if (segment != null) {
/* 667 */                   v.setAlpha(segment.isSilenced() ? 1.0f : 0.35f);
/* 677 */                   return;
                        } else {
/* 670 */                   v.setAlpha(0.35f);
/* 677 */                   return;
                        }
                    }
/* 673 */           if (v != this.mDuplicateButton || v.getAlpha() != 0.175f) {
/* 674 */               v.setAlpha(0.35f);
                    }
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                @Override // android.view.View.OnTouchListener
/* 680 */       public boolean onTouch(View v, MotionEvent event) {
/* 681 */           int action = event.getAction();
                    switch (action) {
                        case 0:
/* 684 */                   if (v == this.mTrashButton) {
/* 685 */                       enableTrashView(v);
                            } else if (v == this.mSilenceButton) {
/* 687 */                       enableSilenceView(v);
                            } else if (v == this.mDuplicateButton) {
/* 689 */                       enableDuplicateView(v);
                            }
                            return false;
                        case 1:
/* 694 */                   disableView(v);
/* 695 */                   this.mExtraProgressDuration = 0;
/* 696 */                   updateProgressView("OnTouchUp", 200);
                            return false;
                        default:
                            return false;
                    }
                }

                @Override // android.view.View.OnClickListener
/* 703 */       public void onClick(View v) {
/* 704 */           VineRecorder recorder = getRecorder();
/* 705 */           RecordSegment segment = this.mSegment.get();
/* 706 */           if (recorder != null && segment != null) {
/* 710 */               int id = v.getId();
/* 712 */               if (id == 2131427544) {
/* 713 */                   SegmentEditorAdapter adapter = recorder.getEditorAdatper();
/* 714 */                   int count = adapter.getCount();
/* 715 */                   int pos = -1;
                            for (int i = 0; i < count; i++) {
/* 717 */                       if (segment == adapter.getItem(i)) {
/* 718 */                           pos = i;
                                    break;
                                }
                            }
/* 722 */                   if (pos >= 0) {
/* 723 */                       addNewSegmentAsDuplicate(recorder, pos);
/* 730 */                       return;
                            }
/* 730 */                   return;
                        }
/* 725 */               if (id == 2131427546) {
/* 726 */                   silence(segment);
                        } else if (id == 2131427545) {
/* 728 */                   delete(recorder);
                        }
                    }
                }

/* 732 */       private void delete(VineRecorder recorder) {
                    int selection;
/* 733 */           this.mDragSortWidget.deleteSelection();
/* 734 */           int selection2 = this.mDragSortWidget.getSelection();
/* 735 */           SegmentEditorAdapter adapter = recorder.getEditorAdatper();
/* 738 */           if (adapter.getCount() <= 1) {
/* 739 */               selection = -1;
                    } else if (selection2 == adapter.getCount() - 1) {
/* 742 */               selection = selection2 - 1;
                    } else {
/* 745 */               selection = selection2 + 1;
                    }
/* 748 */           RecordSegment segment = null;
/* 749 */           if (selection > -1 && selection < adapter.getCount()) {
/* 750 */               segment = (RecordSegment) adapter.getItem(selection);
                    }
/* 753 */           recorder.changePlayButtonOnClickBehavior(true, true);
/* 754 */           recorder.showThumbnailOverlay(segment);
/* 755 */           recorder.pausePreview(adapter.getCount() > 1);
                }

/* 758 */       private void addNewSegmentAsDuplicate(VineRecorder recorder, int pos) {
/* 759 */           SegmentEditorAdapter adapter = recorder.getEditorAdatper();
/* 760 */           RecordSegment item = this.mSegment.get();
/* 761 */           if (item != null) {
/* 762 */               if (canDuplicate(item)) {
                            try {
/* 764 */                       recorder.saveSegmentIfNeeded(item);
                            } catch (IOException e) {
/* 766 */                       CrashUtil.logOrThrowInDebug(e);
                            }
/* 768 */                   adapter.addSegment(item, new RecordSegment(item), pos + 1, true);
/* 769 */                   Handler handler = getHandler();
/* 770 */                   if (handler != null) {
/* 771 */                       handler.post(new RunnableC04576());
                            }
/* 777 */                   this.mCurrentProgressWithoutSelectedSegment += (long) item.getDurationMs();
/* 778 */                   invalidateDuplicateButton(item);
/* 779 */                   updateProgressView("addNewSegmentAsDuplicate");
/* 791 */                   return;
                        }
/* 781 */               if (this.mActivity != null) {
/* 782 */                   if (this.mToast == null) {
/* 783 */                       this.mToast = Toast.makeText(this.mActivity, C0257R.string.error_duplicate_too_long, 0);
                            } else {
/* 785 */                       this.mToast.setText(C0257R.string.error_duplicate_too_long);
                            }
/* 787 */                   this.mToast.show();
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.IndividualEditPlugin$6 */
/* 769 */       class RunnableC04576 implements Runnable {
/* 770 */           RunnableC04576() {
                    }

                    @Override // java.lang.Runnable
/* 772 */           public void run() {
/* 773 */               IndividualEditPlugin.access$800(IndividualEditPlugin.this).forceListLayout();
                    }
                }

/* 793 */       private void unTrim() {
/* 794 */           onRangeSeekBarValuesChanged(null, 0, 100, true);
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.IndividualEditPlugin$7 */
/* 795 */       class RunnableC04587 implements Runnable {
/* 796 */           RunnableC04587() {
                    }

                    @Override // java.lang.Runnable
/* 799 */           public void run() {
                        int seekTo;
/* 800 */               RecordSegment segment = (RecordSegment) IndividualEditPlugin.access$200(IndividualEditPlugin.this).get();
/* 801 */               if (segment != null && IndividualEditPlugin.access$900(IndividualEditPlugin.this) != null) {
/* 802 */                   int durationMs = segment.getUnTrimmedAudioDurationMs();
/* 804 */                   if (IndividualEditPlugin.access$1000(IndividualEditPlugin.this)) {
/* 805 */                       seekTo = (int) ((IndividualEditPlugin.access$1100(IndividualEditPlugin.this) / 100.0f) * durationMs);
                            } else {
/* 807 */                       seekTo = (int) ((IndividualEditPlugin.access$1200(IndividualEditPlugin.this) / 100.0f) * durationMs);
                            }
/* 809 */                   SLog.m465i("Seek Video to: {}.", Integer.valueOf(seekTo));
/* 810 */                   IndividualEditPlugin.access$900(IndividualEditPlugin.this).seekTo(seekTo);
/* 811 */                   if (!IndividualEditPlugin.access$1300(IndividualEditPlugin.this)) {
                                try {
/* 813 */                           int duration = segment.getUnTrimmedAudioDurationMs();
/* 814 */                           if (duration > 0) {
/* 815 */                               int startDuration = (int) ((IndividualEditPlugin.access$1100(IndividualEditPlugin.this) / 100.0f) * duration);
/* 816 */                               long totalAllowed = (long) (IndividualEditPlugin.access$500(IndividualEditPlugin.this) - IndividualEditPlugin.access$1400(IndividualEditPlugin.this));
/* 817 */                               int endDuration = (int) ((IndividualEditPlugin.access$1200(IndividualEditPlugin.this) / 100.0f) * duration);
/* 818 */                               boolean canUntrim = totalAllowed > ((long) (endDuration - startDuration));
/* 819 */                               if (!canUntrim) {
/* 820 */                                   endDuration = (int) (Math.min(totalAllowed, endDuration - startDuration) + ((long) startDuration));
                                        }
/* 822 */                               if (canUntrim && IndividualEditPlugin.access$1100(IndividualEditPlugin.this) == 0 && IndividualEditPlugin.access$1200(IndividualEditPlugin.this) == 100) {
/* 823 */                                   segment.setTrimData(null);
                                        } else {
/* 825 */                                   segment.setTrimData(new TrimData(startDuration, endDuration));
/* 826 */                                   SLog.m449d("Segment trimmed: start={} -> end={} (untrimmed duration={}).", Long.valueOf(segment.getTrimmedVideoStartMs()), Integer.valueOf(segment.getTrimmedAudioEndUs()), Integer.valueOf(duration));
                                        }
/* 828 */                               IndividualEditPlugin.access$1500(IndividualEditPlugin.this, segment);
/* 829 */                               if (segment.isTrimmed()) {
/* 830 */                                   long newStart = segment.getTrimmedVideoStartUs();
/* 831 */                                   long newEnd = segment.getTrimmedAudioEndUs();
                                            if (newStart != IndividualEditPlugin.access$1600(IndividualEditPlugin.this) || newEnd != IndividualEditPlugin.access$1700(IndividualEditPlugin.this)) {
/* 833 */                                       SLog.m453d("Segment trimmed: {} -> {}, {} -> {}.", new Object[]{Long.valueOf(newStart), Long.valueOf(IndividualEditPlugin.access$1600(IndividualEditPlugin.this)), Long.valueOf(newEnd), Long.valueOf(IndividualEditPlugin.access$1700(IndividualEditPlugin.this))});
/* 834 */                                       IndividualEditPlugin.access$1800(IndividualEditPlugin.this, newStart, newEnd);
                                            }
/* 836 */                                   IndividualEditPlugin.access$1900(IndividualEditPlugin.this).setSelectedMinValue((int) ((IndividualEditPlugin.access$1600(IndividualEditPlugin.this) / 10) / ((long) duration)));
/* 837 */                                   IndividualEditPlugin.access$1900(IndividualEditPlugin.this).setSelectedMaxValue((int) ((IndividualEditPlugin.access$1700(IndividualEditPlugin.this) / 10) / ((long) duration)));
/* 838 */                                   IndividualEditPlugin.access$1900(IndividualEditPlugin.this).notifyListenerOfValueChange();
/* 852 */                                   return;
                                        }
/* 840 */                               SLog.m446d("Segment untrimmed.");
/* 841 */                               IndividualEditPlugin.access$1800(IndividualEditPlugin.this, -1L, -1L);
/* 842 */                               IndividualEditPlugin.access$1900(IndividualEditPlugin.this).setSelectedMinValue(0);
/* 843 */                               IndividualEditPlugin.access$1900(IndividualEditPlugin.this).setSelectedMaxValue(100);
/* 844 */                               IndividualEditPlugin.access$1900(IndividualEditPlugin.this).notifyListenerOfValueChange();
                                    }
                                } catch (TrimData.InvalidTrimException e) {
/* 848 */                           throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }

/* 855 */       private void onPlayerStartAndEndChanged(long newStart, long newEnd) {
/* 856 */           this.mPlayerStart = newStart;
/* 857 */           this.mPlayerEnd = newEnd;
/* 858 */           VineRecorder recorder = getRecorder();
/* 859 */           if (recorder != null) {
/* 860 */               recorder.setVideoTextureSeekPoints(this.mPlayerStart, this.mPlayerEnd);
                    }
                }

/* 864 */       private void invalidateTrimData() {
/* 865 */           Handler handler = getHandler();
/* 866 */           if (handler != null) {
/* 867 */               handler.removeCallbacks(this.mTrimRunnable);
/* 868 */               handler.postDelayed(this.mTrimRunnable, 20L);
                    }
                }

                @Override // co.vine.android.views.RangeSeekBar.OnRangeSeekBarChangeListener
/* 873 */       public void onRangeSeekBarTouchDown(RangeSeekBar rangeSeekBar, int minValue, int maxValue) {
/* 874 */           this.mRangeSeekBarTouched = true;
/* 875 */           this.mCurrentRangeSeekBarMin = minValue;
/* 876 */           this.mCurrentRangeSeekBarMax = maxValue;
/* 877 */           RecordSegment segment = null;
/* 878 */           if (this.mSegment != null) {
/* 879 */               RecordSegment segment2 = this.mSegment.get();
                        segment = segment2;
                    }
/* 881 */           if (segment != null) {
/* 882 */               this.mRangeSeekBarTouchedUnTrimmedDuration = segment.getUnTrimmedAudioDurationMs();
                    }
/* 884 */           this.mExtraProgressDuration = (int) (((this.mCurrentRangeSeekBarMax - this.mCurrentRangeSeekBarMin) / 100.0f) * this.mRangeSeekBarTouchedUnTrimmedDuration);
/* 885 */           this.mSeekToLeft = rangeSeekBar.seekToLeft();
/* 886 */           this.mVideoPlayer.setAutoPlayOnPrepared(false);
/* 887 */           VineRecorder recorder = getRecorder();
/* 888 */           if (recorder != null) {
/* 889 */               recorder.animatePlayButton(false);
                    }
/* 891 */           if (segment != null) {
/* 892 */               if (segment.isTrimmed()) {
/* 893 */                   onPlayerStartAndEndChanged(segment.getTrimmedVideoStartUs(), segment.getTrimmedAudioEndUs());
                        } else {
/* 895 */                   onPlayerStartAndEndChanged(-1L, -1L);
                        }
                    }
/* 898 */           invalidateTrimData();
                }

                @Override // co.vine.android.views.RangeSeekBar.OnRangeSeekBarChangeListener
/* 902 */       public void onRangeSeekBarValuesChanged(RangeSeekBar rangeSeekBar, int minValue, int maxValue, boolean byUser) {
/* 903 */           SLog.m466i("Range seek bar value changed: {} {}.", Integer.valueOf(minValue), Integer.valueOf(maxValue));
/* 904 */           this.mCurrentRangeSeekBarMin = minValue;
/* 905 */           this.mCurrentRangeSeekBarMax = maxValue;
/* 906 */           if (byUser) {
/* 907 */               this.mExtraProgressDuration = (int) (((this.mCurrentRangeSeekBarMax - this.mCurrentRangeSeekBarMin) / 100.0f) * this.mRangeSeekBarTouchedUnTrimmedDuration);
/* 908 */               invalidateTrimData();
                    } else {
/* 910 */               this.mRangeSeekBarTouchedUnTrimmedDuration = -1;
/* 911 */               this.mExtraProgressDuration = 0;
                    }
/* 913 */           updateProgressView("onRangeSeekBarValuesChanged");
                }

                @Override // co.vine.android.views.RangeSeekBar.OnRangeSeekBarChangeListener
/* 917 */       public void onRangeSeekBarTouchUp(RangeSeekBar rangeSeekBar, int minValue, int maxValue) {
/* 918 */           this.mRangeSeekBarTouched = false;
/* 919 */           onRangeSeekBarValuesChanged(rangeSeekBar, minValue, maxValue, true);
/* 920 */           this.mVideoPlayer.setAutoPlayOnPrepared(true);
                }

                @Override // co.vine.android.views.RangeSeekBar.OnRangeSeekBarChangeListener
/* 924 */       public void onRangeSeekBarLongPressHappened() {
/* 925 */           this.mRangeSeekBarTouched = false;
/* 926 */           onRangeSeekBarValuesChanged(null, 0, 100, true);
/* 927 */           this.mVideoPlayer.setAutoPlayOnPrepared(true);
                }

/* 930 */       public boolean isEdited() {
/* 931 */           return this.mEdited;
                }

/* 935 */       public void setEdited(boolean edited) {
/* 936 */           this.mEdited = edited;
                }
            }
