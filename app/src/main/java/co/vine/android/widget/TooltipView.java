            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.AlphaAnimation;
            import android.view.animation.Animation;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            import co.vine.android.animation.SimpleAnimationListener;
            
            
    public class TooltipView extends LinearLayout {
                private int mLeftMargin;
                private Listener mListener;
                private ImageView mNib;
                private int mScreenPadding;
                private Rect mTextBounds;
                private int mTextViewPadding;
                private TextView mTooltip;

                public interface Listener {
                    void onTooltipTapped();
                }

        static  Listener access$000(TooltipView x0) {
            return x0.mListener;
                }

        public TooltipView(Context context) {
            super(context);
            setVisibility(4);
            setOrientation(1);
            this.mTextBounds = new Rect();
            ImageView nib = new ImageView(context);
            nib.setImageResource(C0257R.drawable.nib_up);
            nib.setScaleType(ImageView.ScaleType.CENTER);
            int padding = getResources().getDimensionPixelSize(C0257R.dimen.spacing_small);
            int paddingLeftRight = getResources().getDimensionPixelSize(C0257R.dimen.tooltip_left_right_padding);
            TextView tooltip = new TextView(context);
            tooltip.setBackgroundResource(C0257R.drawable.rounded_corner_rect_5);
            tooltip.setGravity(17);
            tooltip.setPadding(paddingLeftRight, padding, paddingLeftRight, padding);
            tooltip.setTextColor(getResources().getColor(C0257R.color.solid_black));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
            params.gravity = 3;
            addView(nib, params);
            addView(tooltip);
            setOnClickListener(new ViewOnClickListenerC05701());
            this.mNib = nib;
            this.mTooltip = tooltip;
            this.mScreenPadding = getResources().getDimensionPixelSize(C0257R.dimen.spacing_small) * 2;
            this.mTextViewPadding = getResources().getDimensionPixelSize(C0257R.dimen.tooltip_left_right_padding) * 2;
                }

                
        class ViewOnClickListenerC05701 implements View.OnClickListener {
            ViewOnClickListenerC05701() {
                    }

                    @Override 
            public void onClick(View view) {
                TooltipView.this.hide();
                if (TooltipView.access$000(TooltipView.this) != null) {
                    TooltipView.access$000(TooltipView.this).onTooltipTapped();
                        }
                    }
                }

        public void setText(int textResId) {
            setText(getResources().getString(textResId));
                }

        public void setText(String text) {
            this.mTooltip.setText(text);
            Paint p = this.mTooltip.getPaint();
            this.mTextBounds = new Rect();
            p.getTextBounds(text, 0, text.length(), this.mTextBounds);
                }

                @Override 
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int width = Math.min(screenWidth - this.mScreenPadding, this.mTextBounds.width() + this.mTextViewPadding + this.mScreenPadding);
            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, 1073741824);
            int heightSpec = this.mTextBounds.height();
            super.onMeasure(widthSpec, heightSpec);
            measureChildren(widthSpec, heightSpec);
                }

        public void updatePosition(View anchor, int leftMargin) {
            this.mLeftMargin = leftMargin;
           updatePosition(anchor);
                }

       public void updatePosition(View anchor) {
                    int newXPos;
           int screenWidth = getResources().getDisplayMetrics().widthPixels;
           int[] loc = new int[2];
           anchor.getLocationOnScreen(loc);
           int xPos = loc[0] + this.mLeftMargin;
           int nibOffset = ((anchor.getWidth() / 2) - (this.mNib.getDrawable().getIntrinsicWidth() / 2)) - this.mLeftMargin;
           int tooltipFullWidth = this.mTextBounds.width() + this.mScreenPadding + this.mTextViewPadding;
           if (xPos + tooltipFullWidth > screenWidth) {
               int newXPos2 = Math.max(this.mScreenPadding / 2, screenWidth - tooltipFullWidth);
               nibOffset += xPos - newXPos2;
               xPos = newXPos2;
                    }
           if (xPos > this.mScreenPadding / 2 && (newXPos = (screenWidth - tooltipFullWidth) / 2) < loc[0] - this.mNib.getDrawable().getIntrinsicWidth()) {
               nibOffset += xPos - newXPos;
               xPos = newXPos;
                    }
           ((RelativeLayout.LayoutParams) getLayoutParams()).setMargins(xPos, 0, 0, 0);
           ((LinearLayout.LayoutParams) this.mNib.getLayoutParams()).setMargins(nibOffset, 0, 0, 0);
           requestLayout();
                }

                
       class RunnableC05712 implements Runnable {
           RunnableC05712() {
                    }

                    @Override 
           public void run() {
               TooltipView.this.show();
                    }
                }

       public void show(int delay) {
           postDelayed(new RunnableC05712(), delay);
                }

       public void show() {
           AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
           anim.setDuration(400L);
           anim.setFillAfter(true);
           anim.setAnimationListener(new C05723());
           startAnimation(anim);
                }

                
       class C05723 extends SimpleAnimationListener {
           C05723() {
                    }

                    @Override 
           public void onAnimationEnd(Animation animation) {
               TooltipView.this.clearAnimation();
               TooltipView.this.setVisibility(0);
                    }
                }

       public void hide() {
           AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
           anim.setDuration(200L);
           anim.setAnimationListener(new C05734());
           startAnimation(anim);
                }

                
       class C05734 extends SimpleAnimationListener {
           C05734() {
                    }

                    @Override 
           public void onAnimationEnd(Animation animation) {
               TooltipView.this.clearAnimation();
               TooltipView.this.setVisibility(8);
               ViewGroup parent = (ViewGroup) TooltipView.this.getParent();
               if (parent != null) {
                   parent.removeView(TooltipView.this);
                        }
                    }
                }

       public boolean hasBeenHidden() {
                    return getVisibility() == 8 && getParent() == null;
                }

       public void setListener(Listener listener) {
           this.mListener = listener;
                }
            }
