            package android.support.v7.internal.widget;

            import android.R;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.Rect;
            import android.support.v7.app.ActionBar;
            import android.support.v7.appcompat.C0084R;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.FrameLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    public class ActionBarOverlayLayout extends FrameLayout {
                static final int[] mActionBarSizeAttr = {C0084R.attr.actionBarSize};
                private ActionBar mActionBar;
                private View mActionBarBottom;
                private int mActionBarHeight;
                private View mActionBarTop;
                private ActionBarView mActionView;
                private ActionBarContainer mContainerView;
                private View mContent;
                private final Rect mZeroRect;

/* 49 */        public ActionBarOverlayLayout(Context context) {
/* 50 */            super(context);
/* 43 */            this.mZeroRect = new Rect(0, 0, 0, 0);
/* 51 */            init(context);
                }

/* 54 */        public ActionBarOverlayLayout(Context context, AttributeSet attrs) {
/* 55 */            super(context, attrs);
/* 43 */            this.mZeroRect = new Rect(0, 0, 0, 0);
/* 56 */            init(context);
                }

/* 59 */        private void init(Context context) {
/* 60 */            TypedArray ta = getContext().getTheme().obtainStyledAttributes(mActionBarSizeAttr);
/* 61 */            this.mActionBarHeight = ta.getDimensionPixelSize(0, 0);
/* 62 */            ta.recycle();
                }

/* 65 */        public void setActionBar(ActionBar impl) {
/* 66 */            this.mActionBar = impl;
                }

/* 70 */        private boolean applyInsets(View view, Rect insets, boolean left, boolean top, boolean bottom, boolean right) {
/* 71 */            boolean changed = false;
/* 72 */            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
/* 73 */            if (left && lp.leftMargin != insets.left) {
/* 74 */                changed = true;
/* 75 */                lp.leftMargin = insets.left;
                    }
/* 77 */            if (top && lp.topMargin != insets.top) {
/* 78 */                changed = true;
/* 79 */                lp.topMargin = insets.top;
                    }
/* 81 */            if (right && lp.rightMargin != insets.right) {
/* 82 */                changed = true;
/* 83 */                lp.rightMargin = insets.right;
                    }
/* 85 */            if (bottom && lp.bottomMargin != insets.bottom) {
/* 87 */                lp.bottomMargin = insets.bottom;
/* 86 */                return true;
                    }
/* 89 */            return changed;
                }

/* 92 */        void pullChildren() {
/* 93 */            if (this.mContent == null) {
/* 94 */                this.mContent = findViewById(C0084R.id.action_bar_activity_content);
/* 95 */                if (this.mContent == null) {
/* 96 */                    this.mContent = findViewById(R.id.content);
                        }
/* 98 */                this.mActionBarTop = findViewById(C0084R.id.top_action_bar);
/* 99 */                this.mContainerView = (ActionBarContainer) findViewById(C0084R.id.action_bar_container);
/* 100 */               this.mActionView = (ActionBarView) findViewById(C0084R.id.action_bar);
/* 101 */               this.mActionBarBottom = findViewById(C0084R.id.split_action_bar);
                    }
                }
            }
