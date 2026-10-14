            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.LinearLayout;
            import co.vine.android.animation.SmoothAnimator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class ToolDrawerLinearLayout extends LinearLayout {
                private static final int DRAWER_ANIM_DURATION = 120;
                private static final float SMOOTHNESS = 0.05f;
                private Runnable mOnAnimationEndRunnable;
                private boolean mShown;
                private SmoothAnimator mSmoothDrawerHider;
                private SmoothAnimator mSmoothDrawerShower;

                /* JADX INFO: renamed from: mY */
                private int f124mY;

/* 12 */        static /* synthetic */ int access$000(ToolDrawerLinearLayout x0) {
/* 13 */            return x0.f124mY;
                }

/* 12 */        static /* synthetic */ int access$002(ToolDrawerLinearLayout x0, int x1) {
/* 13 */            x0.f124mY = x1;
                    return x1;
                }

/* 12 */        static /* synthetic */ void access$100(ToolDrawerLinearLayout x0) {
/* 13 */            x0.callOnStopRunnable();
                }

                /* JADX INFO: renamed from: co.vine.android.widget.ToolDrawerLinearLayout$1 */
/* 19 */        class C05661 extends SmoothAnimator {
                    private int mDelta;

/* 20 */            C05661(View x0, float x1, int x2) {
                        super(x0, x1, x2);
/* 22 */                this.mDelta = 0;
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 24 */            public void onStart() {
/* 25 */                this.mDelta = ToolDrawerLinearLayout.access$000(ToolDrawerLinearLayout.this) - ToolDrawerLinearLayout.this.getMeasuredHeight();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 29 */            public void onUpdate(float frac, float smoothFrac) {
/* 30 */                ToolDrawerLinearLayout.access$002(ToolDrawerLinearLayout.this, (int) (this.mDelta * smoothFrac));
/* 31 */                ToolDrawerLinearLayout.this.invalidate();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 35 */            public void onStop() {
/* 36 */                ToolDrawerLinearLayout.access$100(ToolDrawerLinearLayout.this);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.ToolDrawerLinearLayout$2 */
/* 38 */        class C05672 extends SmoothAnimator {
                    private int mDelta;

/* 39 */            C05672(View x0, float x1, int x2) {
                        super(x0, x1, x2);
/* 41 */                this.mDelta = 0;
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 43 */            public void onStart() {
/* 44 */                this.mDelta = ToolDrawerLinearLayout.access$000(ToolDrawerLinearLayout.this);
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 48 */            public void onUpdate(float frac, float smoothFrac) {
/* 49 */                ToolDrawerLinearLayout.access$002(ToolDrawerLinearLayout.this, (int) (this.mDelta * (1.0f - smoothFrac)));
/* 50 */                if (ToolDrawerLinearLayout.access$000(ToolDrawerLinearLayout.this) > 0) {
/* 51 */                    ToolDrawerLinearLayout.access$002(ToolDrawerLinearLayout.this, 0);
                        }
/* 53 */                ToolDrawerLinearLayout.this.invalidate();
                    }

                    @Override // co.vine.android.animation.SmoothAnimator
/* 57 */            public void onStop() {
/* 58 */                ToolDrawerLinearLayout.access$100(ToolDrawerLinearLayout.this);
                    }
                }

/* 62 */        private void callOnStopRunnable() {
/* 63 */            if (this.mOnAnimationEndRunnable != null) {
/* 64 */                post(this.mOnAnimationEndRunnable);
/* 65 */                this.mOnAnimationEndRunnable = null;
                    }
                }

/* 69 */        public ToolDrawerLinearLayout(Context context) {
/* 70 */            super(context);
/* 17 */            this.f124mY = 0;
/* 18 */            this.mShown = false;
/* 19 */            this.mOnAnimationEndRunnable = null;
/* 21 */            this.mSmoothDrawerHider = new C05661(this, SMOOTHNESS, 120);
/* 40 */            this.mSmoothDrawerShower = new C05672(this, SMOOTHNESS, 120);
                }

/* 73 */        public ToolDrawerLinearLayout(Context context, AttributeSet attrs) {
/* 74 */            super(context, attrs);
/* 17 */            this.f124mY = 0;
/* 18 */            this.mShown = false;
/* 19 */            this.mOnAnimationEndRunnable = null;
/* 21 */            this.mSmoothDrawerHider = new C05661(this, SMOOTHNESS, 120);
/* 40 */            this.mSmoothDrawerShower = new C05672(this, SMOOTHNESS, 120);
                }

/* 77 */        public ToolDrawerLinearLayout(Context context, AttributeSet attrs, int defStyle) {
/* 78 */            super(context, attrs, defStyle);
/* 17 */            this.f124mY = 0;
/* 18 */            this.mShown = false;
/* 19 */            this.mOnAnimationEndRunnable = null;
/* 21 */            this.mSmoothDrawerHider = new C05661(this, SMOOTHNESS, 120);
/* 40 */            this.mSmoothDrawerShower = new C05672(this, SMOOTHNESS, 120);
                }

                @Override // android.widget.LinearLayout, android.view.View
/* 81 */        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 82 */            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 83 */            this.f124mY = -getMeasuredHeight();
                }

                @Override // android.view.View
/* 86 */        public void draw(Canvas c) {
/* 87 */            c.save();
/* 88 */            c.translate(0.0f, this.f124mY);
/* 89 */            super.draw(c);
/* 90 */            c.restore();
                }

/* 93 */        public void toggle(Runnable animationEndRunnable) {
/* 94 */            this.mShown = !this.mShown;
/* 95 */            this.mOnAnimationEndRunnable = animationEndRunnable;
/* 96 */            if (this.mShown) {
/* 97 */                this.mSmoothDrawerHider.cancel();
/* 98 */                this.mSmoothDrawerShower.start();
                    } else {
/* 100 */               this.mSmoothDrawerShower.cancel();
/* 101 */               this.mSmoothDrawerHider.start();
                    }
                }

/* 105 */       public boolean isOpen() {
/* 106 */           return this.mShown;
                }
            }
