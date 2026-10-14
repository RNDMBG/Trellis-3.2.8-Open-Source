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
            
            
    public class DeleteLastSegmentPlugin extends BaseToolPlugin<ImageButton> implements View.OnClickListener, TooltipView.Listener {
                private Fragment mFragment;
                private boolean mHasData;
                private boolean mIsSelected;
                private ViewGroup mParent;
                private Rect mRect;
                private TooltipView mTooltip;
                private View mTouchView;

        static  ViewGroup access$000(DeleteLastSegmentPlugin x0) {
            return x0.mParent;
                }

        static  boolean access$100(DeleteLastSegmentPlugin x0) {
            return x0.mIsSelected;
                }

        static  boolean access$102(DeleteLastSegmentPlugin x0, boolean x1) {
            x0.mIsSelected = x1;
                    return x1;
                }

        static  boolean access$200(DeleteLastSegmentPlugin x0) {
            return x0.mHasData;
                }

        static  Rect access$300(DeleteLastSegmentPlugin x0) {
            return x0.mRect;
                }

        static  TooltipView access$400(DeleteLastSegmentPlugin x0) {
            return x0.mTooltip;
                }

        static  void access$500(DeleteLastSegmentPlugin x0) {
            x0.invalidateView();
                }

        static  View access$600(DeleteLastSegmentPlugin x0) {
            return x0.mTouchView;
                }

        static  void access$700(DeleteLastSegmentPlugin x0, int x1) {
            x0.showOnboardTooltip(x1);
                }


        public DeleteLastSegmentPlugin() {
            super("Delete Last Segment");
                }

                @Override 
        protected ImageButton onLayoutInflated(LinearLayout parent, LayoutInflater inflater, Fragment fragment) {
            this.mFragment = fragment;
            this.mParent = parent;
            ImageButton button = (ImageButton) inflater.inflate(C0257R.layout.plugin_option_image_button, (ViewGroup) parent, false);
            button.setOnClickListener(this);
            button.setImageResource(C0257R.drawable.ic_backspace_default);
            button.setAlpha(0.35f);
            return button;
                }

                @Override 
        public void onResume(Activity activity) {
            super.onResume(activity);
            invalidateButtonState();
                }

                @Override 
        public void onOnboardingStepFinished() {
            super.onOnboardingStepFinished();
            if (this.mTooltip != null) {
                this.mTooltip.hide();
                    }
                }

        private void showOnboardTooltip(int stringResId) {
            if (this.mFragment.getView() != null) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
                params.addRule(3, C0257R.id.recording_options);
                TooltipView tooltip = new TooltipView(this.mActivity);
                tooltip.setText(stringResId);
                tooltip.setListener(this);
                RelativeLayout bottomMask = (RelativeLayout) this.mFragment.getView().findViewById(C0257R.id.bottom_mask);
                bottomMask.addView(tooltip, params);
                bottomMask.post(new RunnableC04211(tooltip));
                this.mTooltip = tooltip;
                    }
                }

                
        class RunnableC04211 implements Runnable {
                    final  TooltipView val$tooltip;

            RunnableC04211(TooltipView tooltipView) {
                        this.val$tooltip = tooltipView;
                    }

                    @Override 
            public void run() {
                if (DeleteLastSegmentPlugin.access$000(DeleteLastSegmentPlugin.this) != null) {
                    this.val$tooltip.updatePosition(DeleteLastSegmentPlugin.access$000(DeleteLastSegmentPlugin.this));
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.val$tooltip.getLayoutParams();
                    params.topMargin = this.val$tooltip.getResources().getDimensionPixelOffset(C0257R.dimen.tooltipNegativeMargin);
                    this.val$tooltip.setLayoutParams(params);
                    this.val$tooltip.show();
                        }
                    }
                }

        private void invalidateButtonState() {
            Handler handler = getHandler();
            if (handler != null) {
                handler.post(new RunnableC04222());
                    }
                }

                
        class RunnableC04222 implements Runnable {
            RunnableC04222() {
                    }

                    @Override 
           public void run() {
                        float f;
               ImageButton view = DeleteLastSegmentPlugin.this.getInflatedChild();
               if (view != null) {
                   if (DeleteLastSegmentPlugin.access$100(DeleteLastSegmentPlugin.this)) {
                                f = 1.0f;
                            } else {
                       f = DeleteLastSegmentPlugin.access$200(DeleteLastSegmentPlugin.this) ? 0.35f : 0.175f;
                            }
                            view.setAlpha(f);
                        }
                    }
                }

                @Override 
       public boolean onBackButtonPressed(boolean isEditing) {
           return this.mIsSelected;
                }

                @Override 
       public void onClick(View v) {
           VineRecorder r = getRecorder();
           Activity activity = this.mActivity;
           if (activity != null && r != null) {
               ViewGroup rootView = (ViewGroup) activity.findViewById(C0257R.id.thumbnail_list);
               ArrayList<RecordSegment> segments = r.getEditedSegments();
               int segmentSize = segments.size();
               if (segmentSize > 0 && !this.mIsSelected) {
                   int lastIndex = 0;
                            for (int i = 0; i < segmentSize; i++) {
                       if (!segments.get(i).removed) {
                           lastIndex = i;
                                }
                            }
                   if (r.highlightSegment(lastIndex, activity.getResources().getColor(C0257R.color.plugin_delete_selected_segment))) {
                       this.mIsSelected = true;
                       if (this.mTouchView == null) {
                           this.mTouchView = new View(this.mActivity);
                           this.mTouchView.setAlpha(0.0f);
                           this.mTouchView.setOnTouchListener(new ViewOnTouchListenerC04233(r, rootView));
                                }
                       rootView.addView(this.mTouchView, new ViewGroup.LayoutParams(-1, -1));
                       invalidateView();
                       if (this.mTooltip != null) {
                           this.mTooltip.hide();
                           String lastStep = CameraOnboardHelper.getLastCompletedStep(this.mActivity);
                           if ("delete".equals(lastStep)) {
                               showOnboardTooltip(C0257R.string.camera_onboarding_undo_step_two);
                                    }
                                }
                            }
                        }
                    }
                }

                
       class ViewOnTouchListenerC04233 implements View.OnTouchListener {
                    final  VineRecorder val$r;
                    final  ViewGroup val$rootView;

           ViewOnTouchListenerC04233(VineRecorder vineRecorder, ViewGroup viewGroup) {
                        this.val$r = vineRecorder;
                        this.val$rootView = viewGroup;
                    }

                    @Override 
           public boolean onTouch(View v, MotionEvent event) {
               if (DeleteLastSegmentPlugin.access$300(DeleteLastSegmentPlugin.this) != null && event.getAction() == 1) {
                   float x = event.getX();
                   float y = event.getY();
                            if (x > DeleteLastSegmentPlugin.access$300(DeleteLastSegmentPlugin.this).left && x < DeleteLastSegmentPlugin.access$300(DeleteLastSegmentPlugin.this).right && y > DeleteLastSegmentPlugin.access$300(DeleteLastSegmentPlugin.this).top && y < DeleteLastSegmentPlugin.access$300(DeleteLastSegmentPlugin.this).bottom) {
                       this.val$r.deleteLastSegment();
                       String lastStep = CameraOnboardHelper.getLastCompletedStep(DeleteLastSegmentPlugin.this.mActivity);
                       if ("delete".equals(lastStep)) {
                           CameraOnboardHelper.setLastCompletedStep(DeleteLastSegmentPlugin.this.mActivity, CameraOnboardHelper.STEP_DELETE_2);
                                }
                       if (DeleteLastSegmentPlugin.this.mManager instanceof BaseRecorderPluginManager) {
                           ((BaseRecorderPluginManager) DeleteLastSegmentPlugin.this.mManager).onOnboardingStepFinished();
                                }
                            } else if (DeleteLastSegmentPlugin.access$400(DeleteLastSegmentPlugin.this) != null) {
                       DeleteLastSegmentPlugin.access$400(DeleteLastSegmentPlugin.this).hide();
                       CameraOnboardHelper.setLastCompletedStep(DeleteLastSegmentPlugin.this.mActivity, "delete");
                            }
                   DeleteLastSegmentPlugin.access$102(DeleteLastSegmentPlugin.this, false);
                   this.val$r.unhighlightSegments();
                   DeleteLastSegmentPlugin.access$500(DeleteLastSegmentPlugin.this);
                   if (DeleteLastSegmentPlugin.access$600(DeleteLastSegmentPlugin.this) != null) {
                       this.val$rootView.removeView(DeleteLastSegmentPlugin.access$600(DeleteLastSegmentPlugin.this));
                            }
                        }
                        return true;
                    }
                }

       private void invalidateView() {
           ImageButton view = getInflatedChild();
           if (view != null) {
               view.setImageResource(this.mIsSelected ? C0257R.drawable.ic_backspace_pressed : C0257R.drawable.ic_backspace_default);
               invalidateButtonState();
               int[] loc = new int[2];
               ((View) view.getParent()).getLocationInWindow(loc);
               this.mRect = new Rect(loc[0], loc[1], loc[0] + view.getMeasuredWidth(), loc[1] + view.getMeasuredHeight());
                    }
                }

                @Override 
       public boolean onSetEditMode(boolean editing, boolean hasData) {
           if (this.mTooltip != null) {
               this.mTooltip.hide();
               return false;
                    }
           return false;
                }

                @Override 
       public void onSegmentDataChanged(ArrayList<RecordSegment> editedSegments) {
           this.mHasData = false;
                    for (RecordSegment segment : editedSegments) {
               if (!segment.removed) {
                   this.mHasData = true;
                            break;
                        }
                    }
           invalidateButtonState();
           boolean shouldOnboard = CameraOnboardHelper.STEP_GRAB.equals(CameraOnboardHelper.getLastCompletedStep(this.mActivity)) && editedSegments.size() > 0;
           if (shouldOnboard && this.mActivity != null) {
               CameraOnboardHelper.setLastCompletedStep(this.mActivity, "delete");
               this.mActivity.runOnUiThread(new RunnableC04244());
                    }
                }

                
       class RunnableC04244 implements Runnable {
           RunnableC04244() {
                    }

                    @Override 
           public void run() {
               DeleteLastSegmentPlugin.access$700(DeleteLastSegmentPlugin.this, C0257R.string.camera_onboarding_undo_step_one);
                    }
                }

                @Override 
       public void onTooltipTapped() {
           String lastStep = CameraOnboardHelper.getLastCompletedStep(this.mActivity);
           CameraOnboardHelper.setLastCompletedStep(this.mActivity, "delete".equals(lastStep) ? CameraOnboardHelper.STEP_DELETE_2 : "delete");
                }
            }
