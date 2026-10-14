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
            
            
    public class TooltipOverlayView extends RelativeLayout implements View.OnClickListener {
                public static final long ANIM_DURATION_HIDE = 300;
                public static final long ANIM_DURATION_SHOW = 500;
                private Context mContext;
                private TooltipView mTooltip;

        static  TooltipView access$000(TooltipOverlayView x0) {
            return x0.mTooltip;
                }

        public TooltipOverlayView(Context context, String message) {
            super(context);
            setOnClickListener(this);
            setBackgroundDrawable(new ColorDrawable(getResources().getColor(C0257R.color.bg_tooltip_overlay)));
            TooltipView tooltip = new TooltipView(context);
            tooltip.setText(message);
            tooltip.setClickable(false);
            tooltip.setVisibility(0);
            addView(tooltip);
            this.mTooltip = tooltip;
            this.mContext = context;
                }

        public void showTooltip(View anchor) {
            this.mTooltip.updatePosition(anchor);
            startShowAnimation();
                }

        public void showTooltip(int top, View anchor) {
            ViewGroup decorView = (ViewGroup) ((Activity) this.mContext).getWindow().getDecorView();
            decorView.addView(this);
            this.mTooltip.updatePosition(anchor);
            this.mTooltip.setY(top);
            startShowAnimation();
                }

        private void startShowAnimation() {
            AlphaAnimation show = new AlphaAnimation(0.0f, 1.0f);
            show.setDuration(500L);
            show.setAnimationListener(new C05681());
            startAnimation(show);
                }

                
        class C05681 extends SimpleAnimationListener {
            C05681() {
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                TooltipOverlayView.this.clearAnimation();
                TooltipOverlayView.access$000(TooltipOverlayView.this).setVisibility(0);
                    }
                }

                @Override 
        public void onClick(View view) {
            dismiss();
                }

        public void dismiss() {
            if (getAnimation() == null) {
                AlphaAnimation hide = new AlphaAnimation(1.0f, 0.0f);
                hide.setDuration(300L);
                hide.setAnimationListener(new C05692());
                startAnimation(hide);
                    }
                }

                
        class C05692 extends SimpleAnimationListener {
            C05692() {
                    }

                    @Override 
            public void onAnimationEnd(Animation animation) {
                TooltipOverlayView.this.setVisibility(8);
                ViewGroup parent = (ViewGroup) TooltipOverlayView.this.getParent();
                if (parent != null) {
                    parent.removeView(TooltipOverlayView.this);
                        }
                    }
                }

                @Override 
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            getLayoutParams().width = -1;
            getLayoutParams().height = -1;
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
                }
            }
