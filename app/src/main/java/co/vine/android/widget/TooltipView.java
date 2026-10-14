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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class TooltipView extends LinearLayout {
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

/* 21 */        static /* synthetic */ Listener access$000(TooltipView x0) {
/* 22 */            return x0.mListener;
                }

/* 32 */        public TooltipView(Context context) {
/* 33 */            super(context);
/* 35 */            setVisibility(4);
/* 36 */            setOrientation(1);
/* 37 */            this.mTextBounds = new Rect();
/* 39 */            ImageView nib = new ImageView(context);
/* 40 */            nib.setImageResource(C0257R.drawable.nib_up);
/* 41 */            nib.setScaleType(ImageView.ScaleType.CENTER);
/* 43 */            int padding = getResources().getDimensionPixelSize(C0257R.dimen.spacing_small);
/* 44 */            int paddingLeftRight = getResources().getDimensionPixelSize(C0257R.dimen.tooltip_left_right_padding);
/* 45 */            TextView tooltip = new TextView(context);
/* 46 */            tooltip.setBackgroundResource(C0257R.drawable.rounded_corner_rect_5);
/* 47 */            tooltip.setGravity(17);
/* 48 */            tooltip.setPadding(paddingLeftRight, padding, paddingLeftRight, padding);
/* 49 */            tooltip.setTextColor(getResources().getColor(C0257R.color.solid_black));
/* 51 */            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
/* 53 */            params.gravity = 3;
/* 54 */            addView(nib, params);
/* 55 */            addView(tooltip);
/* 57 */            setOnClickListener(new ViewOnClickListenerC05701());
/* 67 */            this.mNib = nib;
/* 68 */            this.mTooltip = tooltip;
/* 70 */            this.mScreenPadding = getResources().getDimensionPixelSize(C0257R.dimen.spacing_small) * 2;
/* 71 */            this.mTextViewPadding = getResources().getDimensionPixelSize(C0257R.dimen.tooltip_left_right_padding) * 2;
                }

                /* JADX INFO: renamed from: co.vine.android.widget.TooltipView$1 */
/* 55 */        class ViewOnClickListenerC05701 implements View.OnClickListener {
/* 56 */            ViewOnClickListenerC05701() {
                    }

                    @Override // android.view.View.OnClickListener
/* 59 */            public void onClick(View view) {
/* 60 */                TooltipView.this.hide();
/* 61 */                if (TooltipView.access$000(TooltipView.this) != null) {
/* 62 */                    TooltipView.access$000(TooltipView.this).onTooltipTapped();
                        }
                    }
                }

/* 74 */        public void setText(int textResId) {
/* 75 */            setText(getResources().getString(textResId));
                }

/* 78 */        public void setText(String text) {
/* 79 */            this.mTooltip.setText(text);
/* 80 */            Paint p = this.mTooltip.getPaint();
/* 81 */            this.mTextBounds = new Rect();
/* 82 */            p.getTextBounds(text, 0, text.length(), this.mTextBounds);
                }

                @Override // android.widget.LinearLayout, android.view.View
/* 86 */        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 87 */            int screenWidth = getResources().getDisplayMetrics().widthPixels;
/* 88 */            int width = Math.min(screenWidth - this.mScreenPadding, this.mTextBounds.width() + this.mTextViewPadding + this.mScreenPadding);
/* 91 */            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, 1073741824);
/* 92 */            int heightSpec = this.mTextBounds.height();
/* 94 */            super.onMeasure(widthSpec, heightSpec);
/* 95 */            measureChildren(widthSpec, heightSpec);
                }

/* 98 */        public void updatePosition(View anchor, int leftMargin) {
/* 99 */            this.mLeftMargin = leftMargin;
/* 100 */           updatePosition(anchor);
                }

/* 103 */       public void updatePosition(View anchor) {
                    int newXPos;
/* 104 */           int screenWidth = getResources().getDisplayMetrics().widthPixels;
/* 105 */           int[] loc = new int[2];
/* 106 */           anchor.getLocationOnScreen(loc);
/* 107 */           int xPos = loc[0] + this.mLeftMargin;
/* 108 */           int nibOffset = ((anchor.getWidth() / 2) - (this.mNib.getDrawable().getIntrinsicWidth() / 2)) - this.mLeftMargin;
/* 110 */           int tooltipFullWidth = this.mTextBounds.width() + this.mScreenPadding + this.mTextViewPadding;
/* 111 */           if (xPos + tooltipFullWidth > screenWidth) {
/* 112 */               int newXPos2 = Math.max(this.mScreenPadding / 2, screenWidth - tooltipFullWidth);
/* 113 */               nibOffset += xPos - newXPos2;
/* 114 */               xPos = newXPos2;
                    }
/* 118 */           if (xPos > this.mScreenPadding / 2 && (newXPos = (screenWidth - tooltipFullWidth) / 2) < loc[0] - this.mNib.getDrawable().getIntrinsicWidth()) {
/* 121 */               nibOffset += xPos - newXPos;
/* 122 */               xPos = newXPos;
                    }
/* 126 */           ((RelativeLayout.LayoutParams) getLayoutParams()).setMargins(xPos, 0, 0, 0);
/* 127 */           ((LinearLayout.LayoutParams) this.mNib.getLayoutParams()).setMargins(nibOffset, 0, 0, 0);
/* 128 */           requestLayout();
                }

                /* JADX INFO: renamed from: co.vine.android.widget.TooltipView$2 */
/* 130 */       class RunnableC05712 implements Runnable {
/* 131 */           RunnableC05712() {
                    }

                    @Override // java.lang.Runnable
/* 134 */           public void run() {
/* 135 */               TooltipView.this.show();
                    }
                }

/* 131 */       public void show(int delay) {
/* 132 */           postDelayed(new RunnableC05712(), delay);
                }

/* 140 */       public void show() {
/* 141 */           AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
/* 142 */           anim.setDuration(400L);
/* 143 */           anim.setFillAfter(true);
/* 144 */           anim.setAnimationListener(new C05723());
/* 151 */           startAnimation(anim);
                }

                /* JADX INFO: renamed from: co.vine.android.widget.TooltipView$3 */
/* 142 */       class C05723 extends SimpleAnimationListener {
/* 143 */           C05723() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 146 */           public void onAnimationEnd(Animation animation) {
/* 147 */               TooltipView.this.clearAnimation();
/* 148 */               TooltipView.this.setVisibility(0);
                    }
                }

/* 154 */       public void hide() {
/* 155 */           AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
/* 156 */           anim.setDuration(200L);
/* 157 */           anim.setAnimationListener(new C05734());
/* 168 */           startAnimation(anim);
                }

                /* JADX INFO: renamed from: co.vine.android.widget.TooltipView$4 */
/* 155 */       class C05734 extends SimpleAnimationListener {
/* 156 */           C05734() {
                    }

                    @Override // co.vine.android.animation.SimpleAnimationListener, android.view.animation.Animation.AnimationListener
/* 159 */           public void onAnimationEnd(Animation animation) {
/* 160 */               TooltipView.this.clearAnimation();
/* 161 */               TooltipView.this.setVisibility(8);
/* 162 */               ViewGroup parent = (ViewGroup) TooltipView.this.getParent();
/* 163 */               if (parent != null) {
/* 164 */                   parent.removeView(TooltipView.this);
                        }
                    }
                }

/* 171 */       public boolean hasBeenHidden() {
                    return getVisibility() == 8 && getParent() == null;
                }

/* 175 */       public void setListener(Listener listener) {
/* 176 */           this.mListener = listener;
                }
            }
