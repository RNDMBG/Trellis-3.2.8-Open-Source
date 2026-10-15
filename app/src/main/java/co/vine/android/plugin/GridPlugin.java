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
            import co.vine.android.R;
            import co.vine.android.animation.SimpleAnimationListener;
            import co.vine.android.util.analytics.FlurryUtils;
            
            
    public class GridPlugin extends BaseToolPlugin<ToggleButton> {
                private final AlphaAnimation mDismissGridAnimation;
                private WeakReferenceView mGrid;
                private boolean mGridOn;
                private boolean mIsMessaging;
                private final AlphaAnimation mShowGridAnimation;

        static  WeakReferenceView access$000(GridPlugin x0) {
            return x0.mGrid;
                }

        static  boolean access$102(GridPlugin x0, boolean x1) {
            x0.mGridOn = x1;
                    return x1;
                }

        static  AlphaAnimation access$200(GridPlugin x0) {
            return x0.mShowGridAnimation;
                }

        static  AlphaAnimation access$300(GridPlugin x0) {
            return x0.mDismissGridAnimation;
                }

        static  boolean access$400(GridPlugin x0) {
            return x0.mIsMessaging;
                }


        public GridPlugin() {
            super("Grid");
            this.mIsMessaging = false;
            this.mShowGridAnimation = new AlphaAnimation(0.0f, 1.0f);
            this.mShowGridAnimation.setDuration(200L);
            this.mShowGridAnimation.setFillAfter(true);
            this.mShowGridAnimation.setAnimationListener(new C04441());
            this.mDismissGridAnimation = new AlphaAnimation(1.0f, 0.0f);
            this.mDismissGridAnimation.setDuration(200L);
            this.mDismissGridAnimation.setFillAfter(true);
            this.mDismissGridAnimation.setAnimationListener(new C04452());
                }

                
        class C04441 extends SimpleAnimationListener {
            C04441() {
                    }

                    @Override 
            public void onAnimationStart(Animation animation) {
                GridPlugin.access$000(GridPlugin.this).setVisibility(0);
                ToggleButton button = GridPlugin.this.getInflatedChild();
                if (button != null) {
                    button.setChecked(true);
                        }
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                GridPlugin.access$102(GridPlugin.this, true);
                GridPlugin.access$000(GridPlugin.this).setVisibility(0);
                    }
                }

                
        class C04452 extends SimpleAnimationListener {
            C04452() {
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                GridPlugin.access$000(GridPlugin.this).setVisibility(8);
                ToggleButton button = GridPlugin.this.getInflatedChild();
                if (button != null) {
                    button.setChecked(false);
                        }
                GridPlugin.access$102(GridPlugin.this, false);
                    }
                }

                @Override 
        public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
            ToggleButton button = getInflatedChild();
            if (button != null) {
                button.setChecked(this.mGridOn);
                    }
                }

                @Override 
        public boolean onSetEditMode(boolean editing, boolean hasData) {
            if (this.mGridOn) {
                this.mGridOn = false;
                ToggleButton button = getInflatedChild();
                if (button != null) {
                    button.setChecked(false);
                        }
                    }
                    return false;
                }

                @Override 
        public void onStartEditMode() {
            ToggleButton button = getInflatedChild();
            if (button != null) {
                button.setChecked(false);
                    }
                }

        public class GridCheckChangedListener implements CompoundButton.OnCheckedChangeListener {
                    private final int color;

            public GridCheckChangedListener(int color) {
               this.color = color;
                    }

                    @Override 
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (GridPlugin.access$000(GridPlugin.this).get() != null) {
                   if (isChecked) {
                       GridPlugin.access$000(GridPlugin.this).startAnimation(GridPlugin.access$200(GridPlugin.this));
                       buttonView.getBackground().setColorFilter(this.color, PorterDuff.Mode.SRC_ATOP);
                       buttonView.setAlpha(1.0f);
                            } else {
                       GridPlugin.access$000(GridPlugin.this).startAnimation(GridPlugin.access$300(GridPlugin.this));
                       if (!GridPlugin.access$400(GridPlugin.this)) {
                           buttonView.getBackground().setColorFilter(null);
                                }
                       buttonView.setAlpha(0.35f);
                            }
                        }
                    }
                }

                @Override 
       protected ToggleButton onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
           ViewGroup rootLayout = (ViewGroup) fragment.getView().findViewById(R.id.root_layout);
           View grid = inflater.inflate(R.layout.plugin_grid_view, rootLayout, false);
           rootLayout.addView(grid);
           this.mGrid = new WeakReferenceView(grid);
           ToggleButton button = (ToggleButton) inflater.inflate(R.layout.plugin_tool_toggle, (ViewGroup) view, false);
           button.setText((CharSequence) null);
           button.setTextOn(null);
           button.setTextOff(null);
           button.setBackgroundResource(R.drawable.ic_grid);
           button.setAlpha(0.35f);
           button.setOnCheckedChangeListener(new GridCheckChangedListener(this.mSecondaryColor));
           if (this.mGrid.getVisibility() == 0) {
               this.mGrid.startAnimation(this.mDismissGridAnimation);
                    }
           return button;
                }

                @Override 
       public void onStartDrafts() {
           ToggleButton button = getInflatedChild();
           if (button != null && button.isChecked()) {
               button.setChecked(false);
                    }
                }

                @Override 
       public void onResume(Activity activity) {
           super.onResume(activity);
           ToggleButton button = getInflatedChild();
           if (button != null && button.isChecked()) {
               button.setChecked(this.mGrid.getVisibility() != 8);
                    }
                }

                @Override 
       public void onClick(View v) {
           FlurryUtils.trackGridSwitchPressed();
           if (this.mGrid.getVisibility() == 8) {
               this.mGrid.startAnimation(this.mShowGridAnimation);
                    } else {
               this.mGrid.startAnimation(this.mDismissGridAnimation);
                    }
                }

                @Override 
       public void setMessagingMode(boolean messagingMode) {
           super.setMessagingMode(messagingMode);
           this.mIsMessaging = messagingMode;
           ToggleButton button = getInflatedChild();
           if (messagingMode) {
               button.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                    } else {
               button.getBackground().setColorFilter(null);
                    }
                }
            }
