            package co.vine.android.widget;

            import android.app.Activity;
            import android.content.Context;
            import android.graphics.drawable.ColorDrawable;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.AlphaAnimation;
            import android.view.animation.Animation;
            import android.widget.RelativeLayout;
            import co.vine.android.C0257R;
            import co.vine.android.animation.SimpleAnimationListener;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class TooltipOverlayView extends RelativeLayout implements View.OnClickListener {
                public static final long ANIM_DURATION_HIDE = 300;
                public static final long ANIM_DURATION_SHOW = 500;
                private Context mContext;
                private TooltipView mTooltip;

/* 19 */        static /* synthetic */ TooltipView access$000(TooltipOverlayView x0) {
/* 20 */            return x0.mTooltip;
                }

/* 28 */        public TooltipOverlayView(Context context, String message) {
/* 29 */            super(context);
/* 30 */            setOnClickListener(this);
/* 31 */            setBackgroundDrawable(new ColorDrawable(getResources().getColor(C0257R.color.bg_tooltip_overlay)));
/* 33 */            TooltipView tooltip = new TooltipView(context);
/* 34 */            tooltip.setText(message);
/* 35 */            tooltip.setClickable(false);
/* 36 */            tooltip.setVisibility(0);
/* 37 */            addView(tooltip);
/* 39 */            this.mTooltip = tooltip;
/* 40 */            this.mContext = context;
                }

/* 43 */        public void showTooltip(View anchor) {
/* 44 */            this.mTooltip.updatePosition(anchor);
/* 45 */            startShowAnimation();
                }

/* 48 */        public void showTooltip(int top, View anchor) {
/* 49 */            ViewGroup decorView = (ViewGroup) ((Activity) this.mContext).getWindow().getDecorView();
/* 50 */            decorView.addView(this);
/* 51 */            this.mTooltip.updatePosition(anchor);
/* 52 */            this.mTooltip.setY(top);
/* 53 */            startShowAnimation();
                }

/* 56 */        private void startShowAnimation() {
/* 57 */            AlphaAnimation show = new AlphaAnimation(0.0f, 1.0f);
/* 58 */            show.setDuration(500L);
/* 59 */            show.setAnimationListener(new C05681());
/* 66 */            startAnimation(show);
                }

                /* JADX INFO: renamed from: co.vine.android.widget.TooltipOverlayView$1 */
/* 57 */        class C05681 extends SimpleAnimationListener {
/* 58 */            C05681() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 61 */            public void onAnimationEnd(Animation animation) {
/* 62 */                TooltipOverlayView.this.clearAnimation();
/* 63 */                TooltipOverlayView.access$000(TooltipOverlayView.this).setVisibility(0);
                    }
                }

                @Override // android.view.View.OnClickListener
/* 70 */        public void onClick(View view) {
/* 71 */            dismiss();
                }

/* 74 */        public void dismiss() {
/* 75 */            if (getAnimation() == null) {
/* 76 */                AlphaAnimation hide = new AlphaAnimation(1.0f, 0.0f);
/* 77 */                hide.setDuration(300L);
/* 78 */                hide.setAnimationListener(new C05692());
/* 88 */                startAnimation(hide);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.TooltipOverlayView$2 */
/* 76 */        class C05692 extends SimpleAnimationListener {
/* 77 */            C05692() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 80 */            public void onAnimationEnd(Animation animation) {
/* 81 */                TooltipOverlayView.this.setVisibility(8);
/* 82 */                ViewGroup parent = (ViewGroup) TooltipOverlayView.this.getParent();
/* 83 */                if (parent != null) {
/* 84 */                    parent.removeView(TooltipOverlayView.this);
                        }
                    }
                }

                @Override // android.widget.RelativeLayout, android.view.View
/* 93 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 94 */            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 95 */            getLayoutParams().width = -1;
/* 96 */            getLayoutParams().height = -1;
/* 97 */            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
                }
            }
