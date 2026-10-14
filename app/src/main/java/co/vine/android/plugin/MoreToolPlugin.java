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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class MoreToolPlugin extends RecorderPluginSubManager<LinearLayout, VineRecorder> implements View.OnClickListener, TooltipView.Listener {
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

/* 23 */        static /* synthetic */ View access$000(MoreToolPlugin x0) {
/* 24 */            return x0.mNib;
                }

/* 23 */        static /* synthetic */ ToolDrawerLinearLayout access$100(MoreToolPlugin x0) {
/* 24 */            return x0.mDrawer;
                }

/* 23 */        static /* synthetic */ ViewGroup access$200(MoreToolPlugin x0) {
/* 24 */            return x0.mParent;
                }

/* 23 */        static /* synthetic */ TooltipView access$300(MoreToolPlugin x0) {
/* 24 */            return x0.mTooltip;
                }

/* 23 */        static /* synthetic */ boolean access$402(MoreToolPlugin x0, boolean x1) {
/* 24 */            x0.mWasToolTipHidden = x1;
                    return x1;
                }

/* 23 */        static /* synthetic */ ImageButton access$500(MoreToolPlugin x0) {
/* 24 */            return x0.mButton;
                }


/* 38 */        public static MoreToolPlugin newInstance() {
/* 39 */            ArrayList<RecorderPlugin<?, VineRecorder>> plugins = new ArrayList<>();
/* 40 */            plugins.add(new GridPlugin());
/* 41 */            plugins.add(new FocusPlugin());
/* 42 */            plugins.add(new GhostPlugin());
/* 43 */            plugins.add(new FlashPlugin());
/* 44 */            return new MoreToolPlugin(plugins);
                }

/* 47 */        private MoreToolPlugin(ArrayList<RecorderPlugin<?, VineRecorder>> plugins) {
/* 48 */            super("More tools", plugins);
/* 51 */            this.mDrawerClosedFinishedRunnable = new RunnableC04591();
/* 189 */           this.mShowToolTipRunnable = new RunnableC04602();
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.MoreToolPlugin$1 */
/* 49 */        class RunnableC04591 implements Runnable {
/* 50 */            RunnableC04591() {
                    }

                    @Override // java.lang.Runnable
/* 52 */            public void run() {
/* 53 */                MoreToolPlugin.access$000(MoreToolPlugin.this).setVisibility(8);
/* 54 */                MoreToolPlugin.access$100(MoreToolPlugin.this).setVisibility(8);
                    }
                }

                @Override // co.vine.android.plugin.RecorderPluginSubManager, co.vine.android.plugin.RecorderPlugin
/* 59 */        public LinearLayout onLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
/* 60 */            LinearLayout view = (LinearLayout) inflater.inflate(C0257R.layout.plugin_base_tool, parent, false);
/* 61 */            view.setOnClickListener(this);
/* 62 */            ViewGroup layout = (ViewGroup) inflater.inflate(C0257R.layout.plugin_option_image_button_with_nib, parent, false);
/* 63 */            this.mNib = layout.findViewById(C0257R.id.nib);
/* 64 */            ImageButton button = (ImageButton) layout.findViewById(C0257R.id.option_image_button);
/* 65 */            button.setOnClickListener(this);
/* 66 */            button.setImageResource(C0257R.drawable.ic_tools);
/* 67 */            button.setAlpha(0.35f);
/* 68 */            view.addView(layout);
/* 69 */            ViewGroup viewGroup = (ViewGroup) fragment.getView().findViewById(C0257R.id.recording_sub_options_bar);
                    for (RecorderPlugin<?, ?> plugin : this.mPlugins) {
/* 71 */                plugin.createLayout(viewGroup, inflater, fragment);
                    }
/* 73 */            this.mDrawer = (ToolDrawerLinearLayout) fragment.getView().findViewById(C0257R.id.recording_sub_options);
/* 74 */            this.mButton = button;
/* 75 */            this.mFragment = fragment;
/* 76 */            this.mParent = parent;
/* 77 */            return view;
                }

                @Override // co.vine.android.plugin.BaseRecorderPluginManager, co.vine.android.plugin.RecorderPlugin
/* 81 */        public boolean onSetEditMode(boolean editing, boolean hasData) {
/* 82 */            closeDrawer();
/* 83 */            if (editing) {
/* 84 */                if (hideToolTip()) {
/* 85 */                    this.mWasToolTipHidden = true;
/* 92 */                    return false;
                        }
/* 92 */                return false;
                    }
/* 88 */            if (this.mWasToolTipHidden) {
/* 89 */                showToolTip();
/* 92 */                return false;
                    }
/* 92 */            return false;
                }

                @Override // co.vine.android.plugin.BasePluginManager, co.vine.android.plugin.Plugin
/* 96 */        public void onActivityCreated(Activity activity) {
/* 97 */            this.mActivity = activity;
/* 98 */            super.onActivityCreated(activity);
                }

                @Override // co.vine.android.plugin.BasePluginManager, co.vine.android.plugin.Plugin
/* 102 */       public void onResume(Activity activity) {
/* 103 */           this.mActivity = activity;
/* 104 */           super.onResume(activity);
                }

                @Override // co.vine.android.plugin.BasePluginManager, co.vine.android.plugin.Plugin
/* 108 */       public void onPause() {
/* 109 */           this.mActivity = null;
/* 110 */           super.onPause();
                }

/* 113 */       public void setOnClickListener(View.OnClickListener listener) {
/* 114 */           this.mOnClickListener = listener;
                }

                @Override // android.view.View.OnClickListener
/* 118 */       public void onClick(View v) {
/* 119 */           if (this.mOnClickListener != null) {
/* 120 */               this.mOnClickListener.onClick(v);
                    }
/* 122 */           if (this.mActivity != null) {
/* 123 */               if (this.mViewSpec == 0) {
/* 124 */                   ViewGroup parent = (ViewGroup) this.mDrawer.getParent();
/* 125 */                   this.mViewSpec = View.MeasureSpec.makeMeasureSpec(parent.getMeasuredHeight() - parent.findViewById(C0257R.id.recording_options).getMeasuredHeight(), 0);
/* 128 */                   int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 129 */                   DisplayMetrics metrics = new DisplayMetrics();
/* 130 */                   this.mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
/* 131 */                   int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(metrics.widthPixels, 1073741824);
/* 132 */                   this.mDrawer.measure(widthMeasureSpec, heightMeasureSpec);
/* 133 */                   this.mViewSpec = this.mDrawer.getMeasuredHeight();
                        }
/* 135 */               if (!this.mDrawer.isOpen()) {
/* 136 */                   this.mButton.setAlpha(1.0f);
/* 137 */                   this.mDrawer.setVisibility(0);
/* 138 */                   this.mNib.setVisibility(0);
/* 139 */                   this.mDrawer.toggle(null);
                        } else {
/* 141 */                   this.mButton.setAlpha(0.35f);
/* 142 */                   this.mDrawer.toggle(this.mDrawerClosedFinishedRunnable);
                        }
                    }
/* 145 */           if (hideToolTip()) {
/* 146 */               CameraOnboardHelper.setLastCompletedStep(this.mActivity, CameraOnboardHelper.STEP_MORE_TOOLS);
                    }
                }

/* 150 */       private boolean hideToolTip() {
/* 151 */           if (this.mTooltip == null || this.mTooltip.hasBeenHidden()) {
                        return false;
                    }
/* 152 */           this.mTooltip.hide();
/* 153 */           return true;
                }

                @Override // co.vine.android.plugin.BaseRecorderPluginManager, co.vine.android.plugin.RecorderPlugin
/* 159 */       public void onOnboardingStepFinished() {
/* 160 */           if (this.mActivity != null) {
/* 161 */               String lastStep = CameraOnboardHelper.getLastCompletedStep(this.mActivity);
/* 162 */               boolean shouldOnboard = "delete".equals(lastStep) || CameraOnboardHelper.STEP_DELETE_2.equals(lastStep);
/* 164 */               if (shouldOnboard && this.mFragment.getView() != null) {
/* 165 */                   RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
/* 167 */                   params.addRule(3, C0257R.id.recording_options);
/* 168 */                   if (this.mTooltip == null) {
/* 169 */                       TooltipView tooltip = new TooltipView(this.mActivity);
/* 170 */                       tooltip.setText(C0257R.string.camera_onboarding_more_camera_tools);
/* 171 */                       tooltip.setListener(this);
/* 172 */                       this.mTooltip = tooltip;
/* 173 */                       RelativeLayout bottomMask = (RelativeLayout) this.mFragment.getView().findViewById(C0257R.id.bottom_mask);
/* 175 */                       bottomMask.addView(this.mTooltip, params);
                            }
/* 177 */                   showToolTip();
                        }
                    }
                }

/* 182 */       private void showToolTip() {
/* 183 */           if (this.mParent != null) {
/* 184 */               this.mParent.removeCallbacks(this.mShowToolTipRunnable);
/* 185 */               this.mParent.postDelayed(this.mShowToolTipRunnable, 500L);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.MoreToolPlugin$2 */
/* 187 */       class RunnableC04602 implements Runnable {
/* 188 */           RunnableC04602() {
                    }

                    @Override // java.lang.Runnable
/* 191 */           public void run() {
/* 192 */               if (MoreToolPlugin.access$200(MoreToolPlugin.this) != null && MoreToolPlugin.access$300(MoreToolPlugin.this) != null) {
/* 193 */                   MoreToolPlugin.access$402(MoreToolPlugin.this, false);
/* 194 */                   MoreToolPlugin.access$300(MoreToolPlugin.this).updatePosition(MoreToolPlugin.access$500(MoreToolPlugin.this));
/* 195 */                   MoreToolPlugin.access$300(MoreToolPlugin.this).show();
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPluginManager, co.vine.android.plugin.RecorderPlugin
/* 201 */       public void onShowDrafts() {
/* 202 */           closeDrawer();
                }

/* 205 */       public void closeDrawer() {
/* 206 */           if (this.mDrawer.isOpen()) {
/* 207 */               this.mButton.setAlpha(0.35f);
/* 208 */               this.mDrawer.toggle(this.mDrawerClosedFinishedRunnable);
                    }
                }

                @Override // co.vine.android.widget.TooltipView.Listener
/* 213 */       public void onTooltipTapped() {
/* 214 */           CameraOnboardHelper.setLastCompletedStep(this.mActivity, CameraOnboardHelper.STEP_MORE_TOOLS);
                }
            }
