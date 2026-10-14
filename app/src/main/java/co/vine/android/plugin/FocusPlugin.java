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
            import co.vine.android.C0257R;
            import co.vine.android.animation.SimpleAnimationListener;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.util.ViewUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class FocusPlugin extends BaseToolPlugin<Button> {
                private AnimationSet mFocusAnimationSet;
                private final AlphaAnimation mFocusDismissAnimation;
                private WeakReferenceView mFocusIndicator;
                private boolean mIsMessaging;

/* 23 */        static /* synthetic */ WeakReferenceView access$000(FocusPlugin x0) {
/* 24 */            return x0.mFocusIndicator;
                }

/* 23 */        static /* synthetic */ AlphaAnimation access$100(FocusPlugin x0) {
/* 24 */            return x0.mFocusDismissAnimation;
                }


/* 31 */        public FocusPlugin() {
/* 32 */            super("Focus");
/* 29 */            this.mIsMessaging = false;
/* 33 */            this.mFocusDismissAnimation = new AlphaAnimation(1.0f, 0.0f);
/* 34 */            this.mFocusDismissAnimation.setDuration(300L);
/* 35 */            this.mFocusDismissAnimation.setFillAfter(true);
/* 36 */            this.mFocusDismissAnimation.setAnimationListener(new C04401());
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.FocusPlugin$1 */
/* 34 */        class C04401 extends SimpleAnimationListener {
/* 35 */            C04401() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 39 */            public void onAnimationEnd(Animation animation) {
/* 40 */                if (FocusPlugin.access$000(FocusPlugin.this) != null) {
/* 41 */                    FocusPlugin.access$000(FocusPlugin.this).setVisibility(4);
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseToolPlugin
/* 48 */        protected Button onLayoutInflated(LinearLayout view, LayoutInflater inflater, Fragment fragment) {
/* 49 */            this.mFocusIndicator = new WeakReferenceView(fragment.getView().findViewById(C0257R.id.focus_indicator));
/* 50 */            Button button = (Button) inflater.inflate(C0257R.layout.plugin_tool_regular_button, (ViewGroup) view, false);
/* 51 */            button.setBackgroundResource(C0257R.drawable.ic_focus);
/* 52 */            button.setAlpha(0.35f);
/* 53 */            ViewGroup.LayoutParams indicatorParams = this.mFocusIndicator.getLayoutParams();
/* 54 */            if (indicatorParams == null) {
/* 55 */                throw new RuntimeException("XML is missing layout params for focus indicator. Wrong view?");
                    }
/* 57 */            ScaleAnimation focusResizeAnimation = new ScaleAnimation(1.0f, 0.85f, 1.0f, 0.85f, indicatorParams.width / 2, indicatorParams.height / 2);
/* 58 */            focusResizeAnimation.setRepeatMode(2);
/* 59 */            focusResizeAnimation.setRepeatCount(1);
/* 60 */            focusResizeAnimation.setDuration(80L);
/* 61 */            focusResizeAnimation.setFillAfter(true);
/* 62 */            focusResizeAnimation.setAnimationListener(new C04412());
/* 71 */            this.mFocusAnimationSet = new AnimationSet(true);
/* 72 */            Animation animation = new AlphaAnimation(0.0f, 1.0f);
/* 73 */            this.mFocusAnimationSet.addAnimation(animation);
/* 74 */            Animation animation2 = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, indicatorParams.width / 2, indicatorParams.height / 2);
/* 75 */            this.mFocusAnimationSet.addAnimation(animation2);
/* 76 */            this.mFocusAnimationSet.setFillAfter(true);
/* 77 */            this.mFocusAnimationSet.setDuration(300L);
/* 78 */            this.mFocusAnimationSet.setAnimationListener(new C04423(focusResizeAnimation));
/* 85 */            return button;
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.FocusPlugin$2 */
/* 60 */        class C04412 extends SimpleAnimationListener {
/* 61 */            C04412() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 65 */            public void onAnimationEnd(Animation animation) {
/* 66 */                FocusPlugin.access$000(FocusPlugin.this).startAnimation(FocusPlugin.access$100(FocusPlugin.this));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.plugin.FocusPlugin$3 */
/* 76 */        class C04423 extends SimpleAnimationListener {
                    final /* synthetic */ ScaleAnimation val$focusResizeAnimation;

/* 77 */            C04423(ScaleAnimation scaleAnimation) {
                        this.val$focusResizeAnimation = scaleAnimation;
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 81 */            public void onAnimationEnd(Animation animation) {
/* 82 */                FocusPlugin.access$000(FocusPlugin.this).startAnimation(this.val$focusResizeAnimation);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 89 */        public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
/* 90 */            Button focusView = getInflatedChild();
/* 91 */            BasicVineRecorder recorder = getRecorder();
/* 92 */            if (focusView != null && recorder != null && this.mActivity != null) {
/* 93 */                boolean canChangeFocus = recorder.canChangeFocus();
/* 94 */                Drawable d = canChangeFocus ? this.mActivity.getResources().getDrawable(C0257R.drawable.ic_focus) : this.mActivity.getResources().getDrawable(C0257R.drawable.ic_focus_disabled);
/* 97 */                focusView.setAlpha(canChangeFocus ? 0.35f : 0.175f);
/* 98 */                if (this.mIsMessaging) {
/* 99 */                    d.setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                        } else {
/* 101 */                   d.setColorFilter(null);
                        }
/* 103 */               ViewUtil.setBackground(focusView, d);
                    }
/* 105 */           if (autoFocusSet) {
/* 106 */               onAutoFocusSet();
                    }
                }

                @Override // android.view.View.OnClickListener
/* 111 */       public void onClick(View v) {
                    Button child;
                    BasicVineRecorder recorder;
/* 112 */           if (this.mActivity != null && (child = getInflatedChild()) != null && (recorder = getRecorder()) != null && recorder.canChangeFocus()) {
/* 118 */               if (recorder.isAutoFocusing()) {
/* 119 */                   child.setAlpha(1.0f);
/* 120 */                   child.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                        } else {
/* 122 */                   if (!this.mIsMessaging) {
/* 123 */                       child.getBackground().setColorFilter(null);
                            }
/* 125 */                   child.setAlpha(0.35f);
                        }
/* 127 */               boolean isSet = recorder.setAutoFocusing(!recorder.isAutoFocusing());
/* 128 */               if (isSet) {
/* 129 */                   onAutoFocusSet();
                        }
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 138 */       public void onAutoFocus(int x, int y) {
/* 139 */           View indicator = this.mFocusIndicator != null ? (View) this.mFocusIndicator.get() : null;
/* 140 */           if (indicator != null) {
/* 141 */               int width = indicator.getMeasuredWidth();
/* 142 */               int height = indicator.getMeasuredHeight();
/* 143 */               indicator.layout(x - (width / 2), y - (height / 2), (width / 2) + x, (height / 2) + y);
/* 144 */               indicator.setVisibility(0);
/* 145 */               indicator.startAnimation(this.mFocusAnimationSet);
                    }
                }

/* 149 */       private void onAutoFocusSet() {
/* 150 */           if (this.mFocusIndicator != null && this.mFocusIndicator.getVisibility() == 0) {
/* 151 */               this.mFocusIndicator.startAnimation(this.mFocusDismissAnimation);
                    }
                }

                @Override // co.vine.android.plugin.BaseRecorderPlugin, co.vine.android.plugin.RecorderPlugin
/* 156 */       public void setMessagingMode(boolean messagingMode) {
/* 157 */           super.setMessagingMode(messagingMode);
/* 158 */           this.mIsMessaging = messagingMode;
/* 159 */           Button button = getInflatedChild();
/* 160 */           if (messagingMode) {
/* 161 */               button.getBackground().setColorFilter(this.mSecondaryColor, PorterDuff.Mode.SRC_ATOP);
                    } else {
/* 163 */               button.getBackground().setColorFilter(null);
                    }
                }
            }
