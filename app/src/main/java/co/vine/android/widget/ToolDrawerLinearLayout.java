            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.LinearLayout;
            import co.vine.android.animation.SmoothAnimator;
            
            
    public class ToolDrawerLinearLayout extends LinearLayout {
                private static final int DRAWER_ANIM_DURATION = 120;
                private static final float SMOOTHNESS = 0.05f;
                private Runnable mOnAnimationEndRunnable;
                private boolean mShown;
                private SmoothAnimator mSmoothDrawerHider;
                private SmoothAnimator mSmoothDrawerShower;

                
                private int f124mY;

        static  int access$000(ToolDrawerLinearLayout x0) {
            return x0.f124mY;
                }

        static  int access$002(ToolDrawerLinearLayout x0, int x1) {
            x0.f124mY = x1;
                    return x1;
                }

        static  void access$100(ToolDrawerLinearLayout x0) {
            x0.callOnStopRunnable();
                }

                
        class C05661 extends SmoothAnimator {
                    private int mDelta;

            C05661(View x0, float x1, int x2) {
                        super(x0, x1, x2);
                this.mDelta = 0;
                    }

                    @Override 
            public void onStart() {
                this.mDelta = ToolDrawerLinearLayout.access$000(ToolDrawerLinearLayout.this) - ToolDrawerLinearLayout.this.getMeasuredHeight();
                    }

                    @Override 
            public void onUpdate(float frac, float smoothFrac) {
                ToolDrawerLinearLayout.access$002(ToolDrawerLinearLayout.this, (int) (this.mDelta * smoothFrac));
                ToolDrawerLinearLayout.this.invalidate();
                    }

                    @Override 
            public void onStop() {
                ToolDrawerLinearLayout.access$100(ToolDrawerLinearLayout.this);
                    }
                }

                
        class C05672 extends SmoothAnimator {
                    private int mDelta;

            C05672(View x0, float x1, int x2) {
                        super(x0, x1, x2);
                this.mDelta = 0;
                    }

                    @Override 
            public void onStart() {
                this.mDelta = ToolDrawerLinearLayout.access$000(ToolDrawerLinearLayout.this);
                    }

                    @Override 
            public void onUpdate(float frac, float smoothFrac) {
                ToolDrawerLinearLayout.access$002(ToolDrawerLinearLayout.this, (int) (this.mDelta * (1.0f - smoothFrac)));
                if (ToolDrawerLinearLayout.access$000(ToolDrawerLinearLayout.this) > 0) {
                    ToolDrawerLinearLayout.access$002(ToolDrawerLinearLayout.this, 0);
                        }
                ToolDrawerLinearLayout.this.invalidate();
                    }

                    @Override 
            public void onStop() {
                ToolDrawerLinearLayout.access$100(ToolDrawerLinearLayout.this);
                    }
                }

        private void callOnStopRunnable() {
            if (this.mOnAnimationEndRunnable != null) {
                post(this.mOnAnimationEndRunnable);
                this.mOnAnimationEndRunnable = null;
                    }
                }

        public ToolDrawerLinearLayout(Context context) {
            super(context);
            this.f124mY = 0;
            this.mShown = false;
            this.mOnAnimationEndRunnable = null;
            this.mSmoothDrawerHider = new C05661(this, SMOOTHNESS, 120);
            this.mSmoothDrawerShower = new C05672(this, SMOOTHNESS, 120);
                }

        public ToolDrawerLinearLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.f124mY = 0;
            this.mShown = false;
            this.mOnAnimationEndRunnable = null;
            this.mSmoothDrawerHider = new C05661(this, SMOOTHNESS, 120);
            this.mSmoothDrawerShower = new C05672(this, SMOOTHNESS, 120);
                }

        public ToolDrawerLinearLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.f124mY = 0;
            this.mShown = false;
            this.mOnAnimationEndRunnable = null;
            this.mSmoothDrawerHider = new C05661(this, SMOOTHNESS, 120);
            this.mSmoothDrawerShower = new C05672(this, SMOOTHNESS, 120);
                }

                @Override 
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            this.f124mY = -getMeasuredHeight();
                }

                @Override 
        public void draw(Canvas c) {
            c.save();
            c.translate(0.0f, this.f124mY);
            super.draw(c);
            c.restore();
                }

        public void toggle(Runnable animationEndRunnable) {
            this.mShown = !this.mShown;
            this.mOnAnimationEndRunnable = animationEndRunnable;
            if (this.mShown) {
                this.mSmoothDrawerHider.cancel();
                this.mSmoothDrawerShower.start();
                    } else {
               this.mSmoothDrawerShower.cancel();
               this.mSmoothDrawerHider.start();
                    }
                }

       public boolean isOpen() {
           return this.mShown;
                }
            }
