            package co.vine.android.plugin;

            import android.graphics.PorterDuff;
            import android.graphics.drawable.Drawable;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.AlphaAnimation;
            import android.view.animation.Animation;
            import android.view.animation.AnimationSet;
            import android.view.animation.ScaleAnimation;
            import android.widget.Button;
            import android.widget.LinearLayout;
            import co.vine.android.R;
            import co.vine.android.animation.SimpleAnimationListener;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.util.ViewUtil;
            
            
    public class FocusPlugin extends BaseToolPlugin<Button> {
                private AnimationSet mFocusAnimationSet;
                private final AlphaAnimation mFocusDismissAnimation;
                private WeakReferenceView mFocusIndicator;
                private boolean mIsMessaging;

        static  WeakReferenceView access$000(FocusPlugin x0) {
            return x0.mFocusIndicator;
                }

        static  AlphaAnimation access$100(FocusPlugin x0) {
            return x0.mFocusDismissAnimation;
                }


        public FocusPlugin() {
            super("Focus");
            this.mIsMessaging = false;
            this.mFocusDismissAnimation = new AlphaAnimation(1.0f, 0.0f);
            this.mFocusDismissAnimation.setDuration(300L);
            this.mFocusDismissAnimation.setFillAfter(true);
            this.mFocusDismissAnimation.setAnimationListener(new C04401());
                }

                
        class C04401 extends SimpleAnimationListener {
            C04401() {
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                if (FocusPlugin.access$000(FocusPlugin.this) != null) {
                    FocusPlugin.access$000(FocusPlugin.this).setVisibility(4);
                        }
                    }
                }

                @Override 
        protected Button onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
            this.mFocusIndicator = new WeakReferenceView(fragment.getView().findViewById(R.id.focus_indicator));
            Button button = (Button) inflater.inflate(R.layout.plugin_tool_regular_button, (ViewGroup) view, false);
            button.setBackgroundResource(R.drawable.ic_focus);
            button.setAlpha(0.35f);
            ViewGroup.LayoutParams indicatorParams = this.mFocusIndicator.getLayoutParams();
            if (indicatorParams == null) {
                throw new RuntimeException("XML is missing layout params for focus indicator. Wrong view?");
                    }
            ScaleAnimation focusResizeAnimation = new ScaleAnimation(1.0f, 0.85f, 1.0f, 0.85f, indicatorParams.width / 2, indicatorParams.height / 2);
            focusResizeAnimation.setRepeatMode(2);
            focusResizeAnimation.setRepeatCount(1);
            focusResizeAnimation.setDuration(80L);
            focusResizeAnimation.setFillAfter(true);
            focusResizeAnimation.setAnimationListener(new C04412());
            this.mFocusAnimationSet = new AnimationSet(true);
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            this.mFocusAnimationSet.addAnimation(animation);
            Animation animation2 = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, indicatorParams.width / 2, indicatorParams.height / 2);
            this.mFocusAnimationSet.addAnimation(animation2);
            this.mFocusAnimationSet.setFillAfter(true);
            this.mFocusAnimationSet.setDuration(300L);
            this.mFocusAnimationSet.setAnimationListener(new C04423(focusResizeAnimation));
            return button;
                }

                
        class C04412 extends SimpleAnimationListener {
            C04412() {
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                FocusPlugin.access$000(FocusPlugin.this).startAnimation(FocusPlugin.access$100(FocusPlugin.this));
                    }
                }

                
        class C04423 extends SimpleAnimationListener {
                    final  ScaleAnimation val$focusResizeAnimation;

            C04423(ScaleAnimation scaleAnimation) {
                        this.val$focusResizeAnimation = scaleAnimation;
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                FocusPlugin.access$000(FocusPlugin.this).startAnimation(this.val$focusResizeAnimation);
                    }
                }

                @Override 
        public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
            Button focusView = getInflatedChild();
            BasicVineRecorder recorder = getRecorder();
            if (focusView != null && recorder != null && this.mActivity != null) {
                boolean canChangeFocus = recorder.canChangeFocus();
                Drawable d = canChangeFocus ? this.mActivity.getResources().getDrawable(R.drawable.ic_focus) : this.mActivity.getResources().getDrawable(R.drawable.ic_focus_disabled);
                focusView.setAlpha(canChangeFocus ? 0.35f : 0.175f);
                if (this.mIsMessaging) {
                    d.setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                        } else {
                   d.setColorFilter(null);
                        }
               ViewUtil.setBackground(focusView, d);
                    }
           if (autoFocusSet) {
               onAutoFocusSet();
                    }
                }

                @Override 
       public void onClick(View v) {
                    Button child;
                    BasicVineRecorder recorder;
           if (this.mActivity != null && (child = getInflatedChild()) != null && (recorder = getRecorder()) != null && recorder.canChangeFocus()) {
               if (recorder.isAutoFocusing()) {
                   child.setAlpha(1.0f);
                   child.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                        } else {
                   if (!this.mIsMessaging) {
                       child.getBackground().setColorFilter(null);
                            }
                   child.setAlpha(0.35f);
                        }
               boolean isSet = recorder.setAutoFocusing(!recorder.isAutoFocusing());
               if (isSet) {
                   onAutoFocusSet();
                        }
                    }
                }

                @Override 
       public void onAutoFocus(int x, int y) {
           View indicator = this.mFocusIndicator != null ? (View) this.mFocusIndicator.get() : null;
           if (indicator != null) {
               int width = indicator.getMeasuredWidth();
               int height = indicator.getMeasuredHeight();
               indicator.layout(x - (width / 2), y - (height / 2), (width / 2) + x, (height / 2) + y);
               indicator.setVisibility(0);
               indicator.startAnimation(this.mFocusAnimationSet);
                    }
                }

       private void onAutoFocusSet() {
           if (this.mFocusIndicator != null && this.mFocusIndicator.getVisibility() == 0) {
               this.mFocusIndicator.startAnimation(this.mFocusDismissAnimation);
                    }
                }

                @Override 
       public void setMessagingMode(boolean messagingMode) {
           super.setMessagingMode(messagingMode);
           this.mIsMessaging = messagingMode;
           Button button = getInflatedChild();
           if (messagingMode) {
               button.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                    } else {
               button.getBackground().setColorFilter(null);
                    }
                }
            }
