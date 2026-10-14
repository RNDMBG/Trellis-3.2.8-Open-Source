            package co.vine.android.plugin;

            import android.app.Activity;
            import android.support.v4.app.Fragment;
            import android.util.DisplayMetrics;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageButton;
            import android.widget.LinearLayout;
            import android.widget.RelativeLayout;
            import co.vine.android.C0257R;
            import co.vine.android.CameraOnboardHelper;
            import co.vine.android.recorder.VineRecorder;
            import co.vine.android.widget.ToolDrawerLinearLayout;
            import co.vine.android.widget.TooltipView;
            import java.util.ArrayList;
            
            
    public class MoreToolPlugin extends RecorderPluginSubManager<LinearLayout, VineRecorder> implements View.OnClickListener, TooltipView.Listener {
                protected Activity mActivity;
                private ImageButton mButton;
                private ToolDrawerLinearLayout mDrawer;
                private Runnable mDrawerClosedFinishedRunnable;
                private Fragment mFragment;
                private View mNib;
                private View.OnClickListener mOnClickListener;
                private ViewGroup mParent;
                private final Runnable mShowToolTipRunnable;
                private TooltipView mTooltip;
                private int mViewSpec;
                private boolean mWasToolTipHidden;

        static  View access$000(MoreToolPlugin x0) {
            return x0.mNib;
                }

        static  ToolDrawerLinearLayout access$100(MoreToolPlugin x0) {
            return x0.mDrawer;
                }

        static  ViewGroup access$200(MoreToolPlugin x0) {
            return x0.mParent;
                }

        static  TooltipView access$300(MoreToolPlugin x0) {
            return x0.mTooltip;
                }

        static  boolean access$402(MoreToolPlugin x0, boolean x1) {
            x0.mWasToolTipHidden = x1;
                    return x1;
                }

        static  ImageButton access$500(MoreToolPlugin x0) {
            return x0.mButton;
                }


        public static MoreToolPlugin newInstance() {
            ArrayList<RecorderPlugin<?, VineRecorder>> plugins = new ArrayList<>();
            plugins.add(new GridPlugin());
            plugins.add(new FocusPlugin());
            plugins.add(new GhostPlugin());
            plugins.add(new FlashPlugin());
            return new MoreToolPlugin(plugins);
                }

        private MoreToolPlugin(ArrayList<RecorderPlugin<?, VineRecorder>> plugins) {
            super("More tools", plugins);
            this.mDrawerClosedFinishedRunnable = new RunnableC04591();
           this.mShowToolTipRunnable = new RunnableC04602();
                }

                
        class RunnableC04591 implements Runnable {
            RunnableC04591() {
                    }

                    @Override 
            public void run() {
                MoreToolPlugin.access$000(MoreToolPlugin.this).setVisibility(8);
                MoreToolPlugin.access$100(MoreToolPlugin.this).setVisibility(8);
                    }
                }

                @Override 
        public LinearLayout onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
            LinearLayout view = (LinearLayout) inflater.inflate(C0257R.layout.plugin_base_tool, parent, false);
            view.setOnClickListener(this);
            ViewGroup layout = (ViewGroup) inflater.inflate(C0257R.layout.plugin_option_image_button_with_nib, parent, false);
            this.mNib = layout.findViewById(C0257R.id.nib);
            ImageButton button = (ImageButton) layout.findViewById(C0257R.id.option_image_button);
            button.setOnClickListener(this);
            button.setImageResource(C0257R.drawable.ic_tools);
            button.setAlpha(0.35f);
            view.addView(layout);
            ViewGroup viewGroup = (ViewGroup) fragment.getView().findViewById(C0257R.id.recording_sub_options_bar);
                    for (RecorderPlugin<?, ?> plugin : this.mPlugins) {
                plugin.createLayout(viewGroup, inflater, fragment);
                    }
            this.mDrawer = (ToolDrawerLinearLayout) fragment.getView().findViewById(C0257R.id.recording_sub_options);
            this.mButton = button;
            this.mFragment = fragment;
            this.mParent = parent;
            return view;
                }

                @Override 
        public boolean onSetEditMode(boolean editing, boolean hasData) {
            closeDrawer();
            if (editing) {
                if (hideToolTip()) {
                    this.mWasToolTipHidden = true;
                    return false;
                        }
                return false;
                    }
            if (this.mWasToolTipHidden) {
                showToolTip();
                return false;
                    }
            return false;
                }

                @Override 
        public void onActivityCreated(Activity activity) {
            this.mActivity = activity;
            super.onActivityCreated(activity);
                }

                @Override 
       public void onResume(Activity activity) {
           this.mActivity = activity;
           super.onResume(activity);
                }

                @Override 
       public void onPause() {
           this.mActivity = null;
           super.onPause();
                }

       public void setOnClickListener(View.OnClickListener listener) {
           this.mOnClickListener = listener;
                }

                @Override 
       public void onClick(View v) {
           if (this.mOnClickListener != null) {
               this.mOnClickListener.onClick(v);
                    }
           if (this.mActivity != null) {
               if (this.mViewSpec == 0) {
                   ViewGroup parent = (ViewGroup) this.mDrawer.getParent();
                   this.mViewSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredHeight() - parent.findViewById(C0257R.id.recording_options).getMeasuredHeight(), 0);
                   int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                   DisplayMetrics metrics = new DisplayMetrics();
                   this.mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                   int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(metrics.widthPixels, 1073741824);
                   this.mDrawer.measure(widthMeasureSpec, heightMeasureSpec);
                   this.mViewSpec = this.mDrawer.getMeasuredHeight();
                        }
               if (!this.mDrawer.isOpen()) {
                   this.mButton.setAlpha(1.0f);
                   this.mDrawer.setVisibility(0);
                   this.mNib.setVisibility(0);
                   this.mDrawer.toggle(null);
                        } else {
                   this.mButton.setAlpha(0.35f);
                   this.mDrawer.toggle(this.mDrawerClosedFinishedRunnable);
                        }
                    }
           if (hideToolTip()) {
               CameraOnboardHelper.setLastCompletedStep(this.mActivity, CameraOnboardHelper.STEP_MORE_TOOLS);
                    }
                }

       private boolean hideToolTip() {
           if (this.mTooltip == null || this.mTooltip.hasBeenHidden()) {
                        return false;
                    }
           this.mTooltip.hide();
           return true;
                }

                @Override 
       public void onOnboardingStepFinished() {
           if (this.mActivity != null) {
               String lastStep = CameraOnboardHelper.getLastCompletedStep(this.mActivity);
               boolean shouldOnboard = "delete".equals(lastStep) || CameraOnboardHelper.STEP_DELETE_2.equals(lastStep);
               if (shouldOnboard && this.mFragment.getView() != null) {
                   RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
                   params.addRule(3, C0257R.id.recording_options);
                   if (this.mTooltip == null) {
                       TooltipView tooltip = new TooltipView(this.mActivity);
                       tooltip.setText(C0257R.string.camera_onboarding_more_camera_tools);
                       tooltip.setListener(this);
                       this.mTooltip = tooltip;
                       RelativeLayout bottomMask = (RelativeLayout) this.mFragment.getView().findViewById(C0257R.id.bottom_mask);
                       bottomMask.addView(this.mTooltip, params);
                            }
                   showToolTip();
                        }
                    }
                }

       private void showToolTip() {
           if (this.mParent != null) {
               this.mParent.removeCallbacks(this.mShowToolTipRunnable);
               this.mParent.postDelayed(this.mShowToolTipRunnable, 500L);
                    }
                }

                
       class RunnableC04602 implements Runnable {
           RunnableC04602() {
                    }

                    @Override 
           public void run() {
               if (MoreToolPlugin.access$200(MoreToolPlugin.this) != null && MoreToolPlugin.access$300(MoreToolPlugin.this) != null) {
                   MoreToolPlugin.access$402(MoreToolPlugin.this, false);
                   MoreToolPlugin.access$300(MoreToolPlugin.this).updatePosition(MoreToolPlugin.access$500(MoreToolPlugin.this));
                   MoreToolPlugin.access$300(MoreToolPlugin.this).show();
                        }
                    }
                }

                @Override 
       public void onShowDrafts() {
           closeDrawer();
                }

       public void closeDrawer() {
           if (this.mDrawer.isOpen()) {
               this.mButton.setAlpha(0.35f);
               this.mDrawer.toggle(this.mDrawerClosedFinishedRunnable);
                    }
                }

                @Override 
       public void onTooltipTapped() {
           CameraOnboardHelper.setLastCompletedStep(this.mActivity, CameraOnboardHelper.STEP_MORE_TOOLS);
                }
            }
