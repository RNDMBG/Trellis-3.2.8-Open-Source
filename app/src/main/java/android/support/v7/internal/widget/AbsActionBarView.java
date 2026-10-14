            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.content.res.Configuration;
            import android.content.res.TypedArray;
            import android.os.Build;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.view.menu.ActionMenuPresenter;
            import android.support.v7.internal.view.menu.ActionMenuView;
            import android.util.AttributeSet;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.view.animation.AnimationUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    abstract class AbsActionBarView extends ViewGroup {
                private static final int FADE_DURATION = 200;
                protected ActionMenuPresenter mActionMenuPresenter;
                protected int mContentHeight;
                protected ActionMenuView mMenuView;
                protected boolean mSplitActionBar;
                protected ActionBarContainer mSplitView;
                protected boolean mSplitWhenNarrow;

/* 47 */        AbsActionBarView(Context context) {
/* 48 */            super(context);
                }

/* 51 */        AbsActionBarView(Context context, AttributeSet attrs) {
/* 52 */            super(context, attrs);
                }

/* 55 */        AbsActionBarView(Context context, AttributeSet attrs, int defStyle) {
/* 56 */            super(context, attrs, defStyle);
                }

                @Override // android.view.View
/* 60 */        protected void onConfigurationChanged(Configuration newConfig) {
/* 61 */            if (Build.VERSION.SDK_INT >= 8) {
/* 62 */                super.onConfigurationChanged(newConfig);
                    }
/* 67 */            TypedArray a = getContext().obtainStyledAttributes(null, C0084R.styleable.ActionBar, C0084R.attr.actionBarStyle, 0);
/* 69 */            setContentHeight(a.getLayoutDimension(0, 0));
/* 70 */            a.recycle();
/* 71 */            if (this.mSplitWhenNarrow) {
/* 72 */                setSplitActionBar(getContext().getResources().getBoolean(C0084R.bool.abc_split_action_bar_is_narrow));
                    }
/* 75 */            if (this.mActionMenuPresenter != null) {
/* 76 */                this.mActionMenuPresenter.onConfigurationChanged(newConfig);
                    }
                }

/* 85 */        public void setSplitActionBar(boolean split) {
/* 86 */            this.mSplitActionBar = split;
                }

/* 94 */        public void setSplitWhenNarrow(boolean splitWhenNarrow) {
/* 95 */            this.mSplitWhenNarrow = splitWhenNarrow;
                }

/* 98 */        public void setContentHeight(int height) {
/* 99 */            this.mContentHeight = height;
/* 100 */           requestLayout();
                }

/* 103 */       public int getContentHeight() {
/* 104 */           return this.mContentHeight;
                }

/* 107 */       public void setSplitView(ActionBarContainer splitView) {
/* 108 */           this.mSplitView = splitView;
                }

/* 114 */       public int getAnimatedVisibility() {
/* 115 */           return getVisibility();
                }

/* 118 */       public void animateToVisibility(int visibility) {
/* 119 */           clearAnimation();
/* 121 */           if (visibility != getVisibility()) {
/* 122 */               Animation anim = AnimationUtils.loadAnimation(getContext(), visibility == 0 ? C0084R.anim.abc_fade_in : C0084R.anim.abc_fade_out);
/* 125 */               startAnimation(anim);
/* 126 */               setVisibility(visibility);
/* 128 */               if (this.mSplitView != null && this.mMenuView != null) {
/* 129 */                   this.mMenuView.startAnimation(anim);
/* 130 */                   this.mMenuView.setVisibility(visibility);
                        }
                    }
                }

                @Override // android.view.View
/* 136 */       public void setVisibility(int visibility) {
/* 137 */           if (visibility != getVisibility()) {
/* 138 */               super.setVisibility(visibility);
                    }
                }

/* 142 */       public boolean showOverflowMenu() {
/* 143 */           if (this.mActionMenuPresenter != null) {
/* 144 */               return this.mActionMenuPresenter.showOverflowMenu();
                    }
                    return false;
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.AbsActionBarView$1 */
/* 148 */       class RunnableC00891 implements Runnable {
/* 149 */           RunnableC00891() {
                    }

                    @Override // java.lang.Runnable
/* 151 */           public void run() {
/* 152 */               AbsActionBarView.this.showOverflowMenu();
                    }
                }

/* 149 */       public void postShowOverflowMenu() {
/* 150 */           post(new RunnableC00891());
                }

/* 157 */       public boolean hideOverflowMenu() {
/* 158 */           if (this.mActionMenuPresenter != null) {
/* 159 */               return this.mActionMenuPresenter.hideOverflowMenu();
                    }
                    return false;
                }

/* 164 */       public boolean isOverflowMenuShowing() {
/* 165 */           if (this.mActionMenuPresenter != null) {
/* 166 */               return this.mActionMenuPresenter.isOverflowMenuShowing();
                    }
                    return false;
                }

/* 171 */       public boolean isOverflowReserved() {
                    return this.mActionMenuPresenter != null && this.mActionMenuPresenter.isOverflowReserved();
                }

/* 175 */       public void dismissPopupMenus() {
/* 176 */           if (this.mActionMenuPresenter != null) {
/* 177 */               this.mActionMenuPresenter.dismissPopupMenus();
                    }
                }

/* 182 */       protected int measureChildView(View child, int availableWidth, int childSpecHeight, int spacing) {
/* 183 */           child.measure(View.MeasureSpec.makeMeasureSpec(availableWidth, Integer.MIN_VALUE), childSpecHeight);
/* 189 */           return Math.max(0, (availableWidth - child.getMeasuredWidth()) - spacing);
                }

/* 192 */       protected int positionChild(View child, int x, int y, int contentHeight) {
/* 193 */           int childWidth = child.getMeasuredWidth();
/* 194 */           int childHeight = child.getMeasuredHeight();
/* 195 */           int childTop = y + ((contentHeight - childHeight) / 2);
/* 197 */           child.layout(x, childTop, x + childWidth, childTop + childHeight);
/* 199 */           return childWidth;
                }

/* 202 */       protected int positionChildInverse(View child, int x, int y, int contentHeight) {
/* 203 */           int childWidth = child.getMeasuredWidth();
/* 204 */           int childHeight = child.getMeasuredHeight();
/* 205 */           int childTop = y + ((contentHeight - childHeight) / 2);
/* 207 */           child.layout(x - childWidth, childTop, x, childTop + childHeight);
/* 209 */           return childWidth;
                }
            }
