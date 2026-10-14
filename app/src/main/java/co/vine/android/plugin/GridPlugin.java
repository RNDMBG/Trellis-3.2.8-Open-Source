            package co.vine.android.plugin;

            import android.app.Activity;
            import android.graphics.PorterDuff;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.AlphaAnimation;
            import android.view.animation.Animation;
            import android.widget.CompoundButton;
            import android.widget.LinearLayout;
            import android.widget.ToggleButton;
            import co.vine.android.C0257R;
            import co.vine.android.animation.SimpleAnimationListener;
            import co.vine.android.util.analytics.FlurryUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class GridPlugin extends BaseToolPlugin<ToggleButton> {
                private final AlphaAnimation mDismissGridAnimation;
                private WeakReferenceView mGrid;
                private boolean mGridOn;
                private boolean mIsMessaging;
                private final AlphaAnimation mShowGridAnimation;

/* 21 */        static /* synthetic */ WeakReferenceView access$000(GridPlugin x0) {
/* 22 */            return x0.mGrid;
                }

/* 21 */        static /* synthetic */ boolean access$102(GridPlugin x0, boolean x1) {
/* 22 */            x0.mGridOn = x1;
                    return x1;
                }

/* 21 */        static /* synthetic */ AlphaAnimation access$200(GridPlugin x0) {
/* 22 */            return x0.mShowGridAnimation;
                }

/* 21 */        static /* synthetic */ AlphaAnimation access$300(GridPlugin x0) {
/* 22 */            return x0.mDismissGridAnimation;
                }

/* 21 */        static /* synthetic */ boolean access$400(GridPlugin x0) {
/* 22 */            return x0.mIsMessaging;
                }


/* 30 */        public GridPlugin() {
/* 31 */            super("Grid");
/* 28 */            this.mIsMessaging = false;
/* 32 */            this.mShowGridAnimation = new AlphaAnimation(0.0f, 1.0f);
/* 33 */            this.mShowGridAnimation.setDuration(200L);
/* 34 */            this.mShowGridAnimation.setFillAfter(true);
/* 35 */            this.mShowGridAnimation.setAnimationListener(new C04441());
/* 52 */            this.mDismissGridAnimation = new AlphaAnimation(1.0f, 0.0f);
/* 53 */            this.mDismissGridAnimation.setDuration(200L);
/* 54 */            this.mDismissGridAnimation.setFillAfter(true);
/* 55 */            this.mDismissGridAnimation.setAnimationListener(new C04452());
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.GridPlugin$1 */
/* 34 */        class C04441 extends SimpleAnimationListener {
/* 35 */            C04441() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 38 */            public void onAnimationStart(Animation animation) {
/* 39 */                GridPlugin.access$000(GridPlugin.this).setVisibility(0);
/* 40 */                ToggleButton button = GridPlugin.this.getInflatedChild();
/* 41 */                if (button != null) {
/* 42 */                    button.setChecked(true);
                        }
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 47 */            public void onAnimationEnd(Animation animation) {
/* 48 */                GridPlugin.access$102(GridPlugin.this, true);
/* 49 */                GridPlugin.access$000(GridPlugin.this).setVisibility(0);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.GridPlugin$2 */
/* 54 */        class C04452 extends SimpleAnimationListener {
/* 55 */            C04452() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 58 */            public void onAnimationEnd(Animation animation) {
/* 59 */                GridPlugin.access$000(GridPlugin.this).setVisibility(8);
/* 60 */                ToggleButton button = GridPlugin.this.getInflatedChild();
/* 61 */                if (button != null) {
/* 62 */                    button.setChecked(false);
                        }
/* 64 */                GridPlugin.access$102(GridPlugin.this, false);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 70 */        public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
/* 71 */            ToggleButton button = getInflatedChild();
/* 72 */            if (button != null) {
/* 73 */                button.setChecked(this.mGridOn);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 78 */        public boolean onSetEditMode(boolean editing, boolean hasData) {
/* 79 */            if (this.mGridOn) {
/* 80 */                this.mGridOn = false;
/* 81 */                ToggleButton button = getInflatedChild();
/* 82 */                if (button != null) {
/* 83 */                    button.setChecked(false);
                        }
                    }
                    return false;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 90 */        public void onStartEditMode() {
/* 91 */            ToggleButton button = getInflatedChild();
/* 92 */            if (button != null) {
/* 93 */                button.setChecked(false);
                    }
                }

/* 98 */        public class GridCheckChangedListener implements CompoundButton.OnCheckedChangeListener {
                    private final int color;

/* 99 */            public GridCheckChangedListener(int color) {
/* 101 */               this.color = color;
                    }

                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 105 */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 106 */               if (GridPlugin.access$000(GridPlugin.this).get() != null) {
/* 107 */                   if (isChecked) {
/* 108 */                       GridPlugin.access$000(GridPlugin.this).startAnimation(GridPlugin.access$200(GridPlugin.this));
/* 109 */                       buttonView.getBackground().setColorFilter(this.color, PorterDuff.Mode.SRC_ATOP);
/* 110 */                       buttonView.setAlpha(1.0f);
                            } else {
/* 112 */                       GridPlugin.access$000(GridPlugin.this).startAnimation(GridPlugin.access$300(GridPlugin.this));
/* 113 */                       if (!GridPlugin.access$400(GridPlugin.this)) {
/* 114 */                           buttonView.getBackground().setColorFilter(null);
                                }
/* 116 */                       buttonView.setAlpha(0.35f);
                            }
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseToolPlugin
/* 123 */       protected ToggleButton onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
/* 124 */           ViewGroup rootLayout = (ViewGroup) fragment.getView().findViewById(C0257R.id.root_layout);
/* 125 */           View grid = inflater.inflate(C0257R.layout.plugin_grid_view, rootLayout, false);
/* 126 */           rootLayout.addView(grid);
/* 127 */           this.mGrid = new WeakReferenceView(grid);
/* 128 */           ToggleButton button = (ToggleButton) inflater.inflate(C0257R.layout.plugin_tool_toggle, (ViewGroup) view, false);
/* 129 */           button.setText((CharSequence) null);
/* 130 */           button.setTextOn(null);
/* 131 */           button.setTextOff(null);
/* 132 */           button.setBackgroundResource(C0257R.drawable.ic_grid);
/* 133 */           button.setAlpha(0.35f);
/* 134 */           button.setOnCheckedChangeListener(new GridCheckChangedListener(this.mSecondaryColor));
/* 135 */           if (this.mGrid.getVisibility() == 0) {
/* 136 */               this.mGrid.startAnimation(this.mDismissGridAnimation);
                    }
/* 138 */           return button;
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 142 */       public void onStartDrafts() {
/* 143 */           ToggleButton button = getInflatedChild();
/* 144 */           if (button != null && button.isChecked()) {
/* 146 */               button.setChecked(false);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 152 */       public void onResume(Activity activity) {
/* 153 */           super.onResume(activity);
/* 154 */           ToggleButton button = getInflatedChild();
/* 155 */           if (button != null && button.isChecked()) {
/* 156 */               button.setChecked(this.mGrid.getVisibility() != 8);
                    }
                }

                @Override // android.view.View.OnClickListener
/* 161 */       public void onClick(View v) {
/* 162 */           FlurryUtils.trackGridSwitchPressed();
/* 163 */           if (this.mGrid.getVisibility() == 8) {
/* 164 */               this.mGrid.startAnimation(this.mShowGridAnimation);
                    } else {
/* 166 */               this.mGrid.startAnimation(this.mDismissGridAnimation);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 171 */       public void setMessagingMode(boolean messagingMode) {
/* 172 */           super.setMessagingMode(messagingMode);
/* 173 */           this.mIsMessaging = messagingMode;
/* 174 */           ToggleButton button = getInflatedChild();
/* 175 */           if (messagingMode) {
/* 176 */               button.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                    } else {
/* 178 */               button.getBackground().setColorFilter(null);
                    }
                }
            }
