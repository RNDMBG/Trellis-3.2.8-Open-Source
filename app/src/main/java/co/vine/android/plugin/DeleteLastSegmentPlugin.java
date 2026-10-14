            package co.vine.android.plugin;

            import android.app.Activity;
            import android.graphics.Rect;
            import android.os.Handler;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageButton;
            import android.widget.LinearLayout;
            import android.widget.RelativeLayout;
            import co.vine.android.C0257R;
            import co.vine.android.CameraOnboardHelper;
            import co.vine.android.recorder.RecordSegment;
            import co.vine.android.recorder.VineRecorder;
            import co.vine.android.widget.TooltipView;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class DeleteLastSegmentPlugin extends BaseToolPlugin<ImageButton> implements View.OnClickListener, TooltipView.Listener {
                private Fragment mFragment;
                private boolean mHasData;
                private boolean mIsSelected;
                private ViewGroup mParent;
                private Rect mRect;
                private TooltipView mTooltip;
                private View mTouchView;

/* 25 */        static /* synthetic */ ViewGroup access$000(DeleteLastSegmentPlugin x0) {
/* 26 */            return x0.mParent;
                }

/* 25 */        static /* synthetic */ boolean access$100(DeleteLastSegmentPlugin x0) {
/* 26 */            return x0.mIsSelected;
                }

/* 25 */        static /* synthetic */ boolean access$102(DeleteLastSegmentPlugin x0, boolean x1) {
/* 26 */            x0.mIsSelected = x1;
                    return x1;
                }

/* 25 */        static /* synthetic */ boolean access$200(DeleteLastSegmentPlugin x0) {
/* 26 */            return x0.mHasData;
                }

/* 25 */        static /* synthetic */ Rect access$300(DeleteLastSegmentPlugin x0) {
/* 26 */            return x0.mRect;
                }

/* 25 */        static /* synthetic */ TooltipView access$400(DeleteLastSegmentPlugin x0) {
/* 26 */            return x0.mTooltip;
                }

/* 25 */        static /* synthetic */ void access$500(DeleteLastSegmentPlugin x0) {
/* 26 */            x0.invalidateView();
                }

/* 25 */        static /* synthetic */ View access$600(DeleteLastSegmentPlugin x0) {
/* 26 */            return x0.mTouchView;
                }

/* 25 */        static /* synthetic */ void access$700(DeleteLastSegmentPlugin x0, int x1) {
/* 26 */            x0.showOnboardTooltip(x1);
                }


/* 37 */        public DeleteLastSegmentPlugin() {
/* 38 */            super("Delete Last Segment");
                }

                @Override // co.vine.android.plugin.BaseToolPlugin
/* 42 */        protected ImageButton onLayoutInflated(LinearLayout parent, LayoutInflater inflater, Fragment fragment) {
/* 43 */            this.mFragment = fragment;
/* 44 */            this.mParent = parent;
/* 46 */            ImageButton button = (ImageButton) inflater.inflate(C0257R.layout.plugin_option_image_button, (ViewGroup) parent, false);
/* 47 */            button.setOnClickListener(this);
/* 48 */            button.setImageResource(C0257R.drawable.ic_backspace_default);
/* 49 */            button.setAlpha(0.35f);
/* 50 */            return button;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 54 */        public void onResume(Activity activity) {
/* 55 */            super.onResume(activity);
/* 56 */            invalidateButtonState();
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 60 */        public void onOnboardingStepFinished() {
/* 61 */            super.onOnboardingStepFinished();
/* 62 */            if (this.mTooltip != null) {
/* 63 */                this.mTooltip.hide();
                    }
                }

/* 67 */        private void showOnboardTooltip(int stringResId) {
/* 68 */            if (this.mFragment.getView() != null) {
/* 69 */                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
/* 71 */                params.addRule(3, C0257R.id.recording_options);
/* 72 */                TooltipView tooltip = new TooltipView(this.mActivity);
/* 73 */                tooltip.setText(stringResId);
/* 74 */                tooltip.setListener(this);
/* 76 */                RelativeLayout bottomMask = (RelativeLayout) this.mFragment.getView().findViewById(C0257R.id.bottom_mask);
/* 78 */                bottomMask.addView(tooltip, params);
/* 79 */                bottomMask.post(new RunnableC04211(tooltip));
/* 91 */                this.mTooltip = tooltip;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DeleteLastSegmentPlugin$1 */
/* 77 */        class RunnableC04211 implements Runnable {
                    final /* synthetic */ TooltipView val$tooltip;

/* 78 */            RunnableC04211(TooltipView tooltipView) {
                        this.val$tooltip = tooltipView;
                    }

                    @Override // java.lang.Runnable
/* 81 */            public void run() {
/* 82 */                if (DeleteLastSegmentPlugin.access$000(DeleteLastSegmentPlugin.this) != null) {
/* 83 */                    this.val$tooltip.updatePosition(DeleteLastSegmentPlugin.access$000(DeleteLastSegmentPlugin.this));
/* 84 */                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.val$tooltip.getLayoutParams();
/* 85 */                    params.topMargin = this.val$tooltip.getResources().getDimensionPixelOffset(C0257R.dimen.tooltipNegativeMargin);
/* 86 */                    this.val$tooltip.setLayoutParams(params);
/* 87 */                    this.val$tooltip.show();
                        }
                    }
                }

/* 96 */        private void invalidateButtonState() {
/* 97 */            Handler handler = getHandler();
/* 98 */            if (handler != null) {
/* 99 */                handler.post(new RunnableC04222());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DeleteLastSegmentPlugin$2 */
/* 97 */        class RunnableC04222 implements Runnable {
/* 98 */            RunnableC04222() {
                    }

                    @Override // java.lang.Runnable
/* 101 */           public void run() {
                        float f;
/* 102 */               ImageButton view = DeleteLastSegmentPlugin.this.getInflatedChild();
/* 103 */               if (view != null) {
/* 104 */                   if (DeleteLastSegmentPlugin.access$100(DeleteLastSegmentPlugin.this)) {
                                f = 1.0f;
                            } else {
/* 104 */                       f = DeleteLastSegmentPlugin.access$200(DeleteLastSegmentPlugin.this) ? 0.35f : 0.175f;
                            }
                            view.setAlpha(f);
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 114 */       public boolean onBackButtonPressed(boolean isEditing) {
/* 115 */           return this.mIsSelected;
                }

                @Override // android.view.View.OnClickListener
/* 119 */       public void onClick(View v) {
/* 120 */           VineRecorder r = getRecorder();
/* 121 */           Activity activity = this.mActivity;
/* 122 */           if (activity != null && r != null) {
/* 123 */               ViewGroup rootView = (ViewGroup) activity.findViewById(C0257R.id.thumbnail_list);
/* 124 */               ArrayList<RecordSegment> segments = r.getEditedSegments();
/* 125 */               int segmentSize = segments.size();
/* 126 */               if (segmentSize > 0 && !this.mIsSelected) {
/* 128 */                   int lastIndex = 0;
                            for (int i = 0; i < segmentSize; i++) {
/* 130 */                       if (!segments.get(i).removed) {
/* 131 */                           lastIndex = i;
                                }
                            }
/* 134 */                   if (r.highlightSegment(lastIndex, activity.getResources().getColor(C0257R.color.plugin_delete_selected_segment))) {
/* 135 */                       this.mIsSelected = true;
/* 136 */                       if (this.mTouchView == null) {
/* 137 */                           this.mTouchView = new View(this.mActivity);
/* 138 */                           this.mTouchView.setAlpha(0.0f);
/* 139 */                           this.mTouchView.setOnTouchListener(new ViewOnTouchListenerC04233(r, rootView));
                                }
/* 176 */                       rootView.addView(this.mTouchView, new ViewGroup.LayoutParams(-1, -1));
/* 177 */                       invalidateView();
/* 178 */                       if (this.mTooltip != null) {
/* 179 */                           this.mTooltip.hide();
/* 180 */                           String lastStep = CameraOnboardHelper.getLastCompletedStep(this.mActivity);
/* 181 */                           if ("delete".equals(lastStep)) {
/* 182 */                               showOnboardTooltip(C0257R.string.camera_onboarding_undo_step_two);
                                    }
                                }
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DeleteLastSegmentPlugin$3 */
/* 137 */       class ViewOnTouchListenerC04233 implements View.OnTouchListener {
                    final /* synthetic */ VineRecorder val$r;
                    final /* synthetic */ ViewGroup val$rootView;

/* 138 */           ViewOnTouchListenerC04233(VineRecorder vineRecorder, ViewGroup viewGroup) {
                        this.val$r = vineRecorder;
                        this.val$rootView = viewGroup;
                    }

                    @Override // android.view.View.OnTouchListener
/* 141 */           public boolean onTouch(View v, MotionEvent event) {
/* 142 */               if (DeleteLastSegmentPlugin.access$300(DeleteLastSegmentPlugin.this) != null && event.getAction() == 1) {
/* 144 */                   float x = event.getX();
/* 145 */                   float y = event.getY();
                            if (x > DeleteLastSegmentPlugin.access$300(DeleteLastSegmentPlugin.this).left && x < DeleteLastSegmentPlugin.access$300(DeleteLastSegmentPlugin.this).right && y > DeleteLastSegmentPlugin.access$300(DeleteLastSegmentPlugin.this).top && y < DeleteLastSegmentPlugin.access$300(DeleteLastSegmentPlugin.this).bottom) {
/* 148 */                       this.val$r.deleteLastSegment();
/* 149 */                       String lastStep = CameraOnboardHelper.getLastCompletedStep(DeleteLastSegmentPlugin.this.mActivity);
/* 150 */                       if ("delete".equals(lastStep)) {
/* 151 */                           CameraOnboardHelper.setLastCompletedStep(DeleteLastSegmentPlugin.this.mActivity, CameraOnboardHelper.STEP_DELETE_2);
                                }
/* 154 */                       if (DeleteLastSegmentPlugin.this.mManager instanceof BaseRecorderPluginManager) {
/* 155 */                           ((BaseRecorderPluginManager) DeleteLastSegmentPlugin.this.mManager).onOnboardingStepFinished();
                                }
                            } else if (DeleteLastSegmentPlugin.access$400(DeleteLastSegmentPlugin.this) != null) {
/* 159 */                       DeleteLastSegmentPlugin.access$400(DeleteLastSegmentPlugin.this).hide();
/* 160 */                       CameraOnboardHelper.setLastCompletedStep(DeleteLastSegmentPlugin.this.mActivity, "delete");
                            }
/* 164 */                   DeleteLastSegmentPlugin.access$102(DeleteLastSegmentPlugin.this, false);
/* 165 */                   this.val$r.unhighlightSegments();
/* 166 */                   DeleteLastSegmentPlugin.access$500(DeleteLastSegmentPlugin.this);
/* 167 */                   if (DeleteLastSegmentPlugin.access$600(DeleteLastSegmentPlugin.this) != null) {
/* 168 */                       this.val$rootView.removeView(DeleteLastSegmentPlugin.access$600(DeleteLastSegmentPlugin.this));
                            }
                        }
                        return true;
                    }
                }

/* 191 */       private void invalidateView() {
/* 192 */           ImageButton view = getInflatedChild();
/* 193 */           if (view != null) {
/* 194 */               view.setImageResource(this.mIsSelected ? C0257R.drawable.ic_backspace_pressed : C0257R.drawable.ic_backspace_default);
/* 195 */               invalidateButtonState();
/* 196 */               int[] loc = new int[2];
/* 197 */               ((View) view.getParent()).getLocationInWindow(loc);
/* 198 */               this.mRect = new Rect(loc[0], loc[1], loc[0] + view.getMeasuredWidth(), loc[1] + view.getMeasuredHeight());
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 203 */       public boolean onSetEditMode(boolean editing, boolean hasData) {
/* 204 */           if (this.mTooltip != null) {
/* 205 */               this.mTooltip.hide();
/* 207 */               return false;
                    }
/* 207 */           return false;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 211 */       public void onSegmentDataChanged(ArrayList<RecordSegment> editedSegments) {
/* 212 */           this.mHasData = false;
                    for (RecordSegment segment : editedSegments) {
/* 214 */               if (!segment.removed) {
/* 215 */                   this.mHasData = true;
                            break;
                        }
                    }
/* 219 */           invalidateButtonState();
/* 221 */           boolean shouldOnboard = CameraOnboardHelper.STEP_GRAB.equals(CameraOnboardHelper.getLastCompletedStep(this.mActivity)) && editedSegments.size() > 0;
/* 223 */           if (shouldOnboard && this.mActivity != null) {
/* 224 */               CameraOnboardHelper.setLastCompletedStep(this.mActivity, "delete");
/* 226 */               this.mActivity.runOnUiThread(new RunnableC04244());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.DeleteLastSegmentPlugin$4 */
/* 224 */       class RunnableC04244 implements Runnable {
/* 225 */           RunnableC04244() {
                    }

                    @Override // java.lang.Runnable
/* 228 */           public void run() {
/* 229 */               DeleteLastSegmentPlugin.access$700(DeleteLastSegmentPlugin.this, C0257R.string.camera_onboarding_undo_step_one);
                    }
                }

                @Override // co.vine.android.widget.TooltipView.Listener
/* 236 */       public void onTooltipTapped() {
/* 237 */           String lastStep = CameraOnboardHelper.getLastCompletedStep(this.mActivity);
/* 238 */           CameraOnboardHelper.setLastCompletedStep(this.mActivity, "delete".equals(lastStep) ? CameraOnboardHelper.STEP_DELETE_2 : "delete");
                }
            }
