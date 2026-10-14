            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.view.menu.ActionMenuPresenter;
            import android.support.v7.internal.view.menu.ActionMenuView;
            import android.support.v7.internal.view.menu.MenuBuilder;
            import android.support.v7.view.ActionMode;
            import android.text.TextUtils;
            import android.util.AttributeSet;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.LinearLayout;
            import android.widget.TextView;
            
            
    public class ActionBarContextView extends AbsActionBarView {
                private static final String TAG = "ActionBarContextView";
                private View mClose;
                private View mCustomView;
                private Drawable mSplitBackground;
                private CharSequence mSubtitle;
                private int mSubtitleStyleRes;
                private TextView mSubtitleView;
                private CharSequence mTitle;
                private LinearLayout mTitleLayout;
                private boolean mTitleOptional;
                private int mTitleStyleRes;
                private TextView mTitleView;

                @Override 
        public   void animateToVisibility(int x0) {
            super.animateToVisibility(x0);
                }

                @Override 
        public   void dismissPopupMenus() {
            super.dismissPopupMenus();
                }

                @Override 
        public   int getAnimatedVisibility() {
            return super.getAnimatedVisibility();
                }

                @Override 
        public   int getContentHeight() {
            return super.getContentHeight();
                }

                @Override 
        public   boolean isOverflowReserved() {
            return super.isOverflowReserved();
                }

                @Override 
        public   void postShowOverflowMenu() {
            super.postShowOverflowMenu();
                }

                @Override 
        public   void setSplitView(ActionBarContainer x0) {
            super.setSplitView(x0);
                }

                @Override 
        public   void setSplitWhenNarrow(boolean x0) {
            super.setSplitWhenNarrow(x0);
                }

                @Override 
        public   void setVisibility(int x0) {
            super.setVisibility(x0);
                }

        public ActionBarContextView(Context context) {
            this(context, null);
                }

        public ActionBarContextView(Context context, AttributeSet attrs) {
            this(context, attrs, C0084R.attr.actionModeStyle);
                }

        public ActionBarContextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.ActionMode, defStyle, 0);
            setBackgroundDrawable(a.getDrawable(3));
            this.mTitleStyleRes = a.getResourceId(1, 0);
            this.mSubtitleStyleRes = a.getResourceId(2, 0);
            this.mContentHeight = a.getLayoutDimension(0, 0);
            this.mSplitBackground = a.getDrawable(4);
            a.recycle();
                }

                @Override 
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (this.mActionMenuPresenter != null) {
                this.mActionMenuPresenter.hideOverflowMenu();
                this.mActionMenuPresenter.hideSubMenus();
                    }
                }

                @Override 
        public void setSplitActionBar(boolean split) {
            if (this.mSplitActionBar != split) {
                if (this.mActionMenuPresenter != null) {
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
                    if (!split) {
                       this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                       this.mMenuView.setBackgroundDrawable(null);
                       ViewGroup oldParent = (ViewGroup) this.mMenuView.getParent();
                       if (oldParent != null) {
                           oldParent.removeView(this.mMenuView);
                                }
                       addView(this.mMenuView, layoutParams);
                            } else {
                       this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                       this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
                       layoutParams.width = -1;
                       layoutParams.height = this.mContentHeight;
                       this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
                       this.mMenuView.setBackgroundDrawable(this.mSplitBackground);
                       ViewGroup oldParent2 = (ViewGroup) this.mMenuView.getParent();
                       if (oldParent2 != null) {
                           oldParent2.removeView(this.mMenuView);
                                }
                       this.mSplitView.addView(this.mMenuView, layoutParams);
                            }
                        }
               super.setSplitActionBar(split);
                    }
                }

                @Override 
       public void setContentHeight(int height) {
           this.mContentHeight = height;
                }

       public void setCustomView(View view) {
           if (this.mCustomView != null) {
               removeView(this.mCustomView);
                    }
           this.mCustomView = view;
           if (this.mTitleLayout != null) {
               removeView(this.mTitleLayout);
               this.mTitleLayout = null;
                    }
           if (view != null) {
               addView(view);
                    }
           requestLayout();
                }

       public void setTitle(CharSequence title) {
           this.mTitle = title;
           initTitle();
                }

       public void setSubtitle(CharSequence subtitle) {
           this.mSubtitle = subtitle;
           initTitle();
                }

       public CharSequence getTitle() {
           return this.mTitle;
                }

       public CharSequence getSubtitle() {
           return this.mSubtitle;
                }

       private void initTitle() {
           if (this.mTitleLayout == null) {
               LayoutInflater inflater = LayoutInflater.from(getContext());
               inflater.inflate(C0084R.layout.abc_action_bar_title_item, this);
               this.mTitleLayout = (LinearLayout) getChildAt(getChildCount() - 1);
               this.mTitleView = (TextView) this.mTitleLayout.findViewById(C0084R.id.action_bar_title);
               this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(C0084R.id.action_bar_subtitle);
               if (this.mTitleStyleRes != 0) {
                   this.mTitleView.setTextAppearance(getContext(), this.mTitleStyleRes);
                        }
               if (this.mSubtitleStyleRes != 0) {
                   this.mSubtitleView.setTextAppearance(getContext(), this.mSubtitleStyleRes);
                        }
                    }
           this.mTitleView.setText(this.mTitle);
           this.mSubtitleView.setText(this.mSubtitle);
           boolean hasTitle = !TextUtils.isEmpty(this.mTitle);
           boolean hasSubtitle = !TextUtils.isEmpty(this.mSubtitle);
           this.mSubtitleView.setVisibility(hasSubtitle ? 0 : 8);
           this.mTitleLayout.setVisibility((hasTitle || hasSubtitle) ? 0 : 8);
           if (this.mTitleLayout.getParent() == null) {
               addView(this.mTitleLayout);
                    }
                }

       public void initForMode(ActionMode mode) {
           if (this.mClose == null) {
               LayoutInflater inflater = LayoutInflater.from(getContext());
               this.mClose = inflater.inflate(C0084R.layout.abc_action_mode_close_item, (ViewGroup) this, false);
               addView(this.mClose);
                    } else if (this.mClose.getParent() == null) {
               addView(this.mClose);
                    }
           View closeButton = this.mClose.findViewById(C0084R.id.action_mode_close_button);
           closeButton.setOnClickListener(new ViewOnClickListenerC00921(mode));
           MenuBuilder menu = (MenuBuilder) mode.getMenu();
           if (this.mActionMenuPresenter != null) {
               this.mActionMenuPresenter.dismissPopupMenus();
                    }
           this.mActionMenuPresenter = new ActionMenuPresenter(getContext());
           this.mActionMenuPresenter.setReserveOverflow(true);
           ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
           if (!this.mSplitActionBar) {
               menu.addMenuPresenter(this.mActionMenuPresenter);
               this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
               this.mMenuView.setBackgroundDrawable(null);
               addView(this.mMenuView, layoutParams);
               return;
                    }
           this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
           this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
           layoutParams.width = -1;
           layoutParams.height = this.mContentHeight;
           menu.addMenuPresenter(this.mActionMenuPresenter);
           this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
           this.mMenuView.setBackgroundDrawable(this.mSplitBackground);
           this.mSplitView.addView(this.mMenuView, layoutParams);
                }

                
       class ViewOnClickListenerC00921 implements View.OnClickListener {
                    final  ActionMode val$mode;

           ViewOnClickListenerC00921(ActionMode actionMode) {
                        this.val$mode = actionMode;
                    }

                    @Override 
           public void onClick(View v) {
               this.val$mode.finish();
                    }
                }

       public void closeMode() {
           if (this.mClose == null) {
               killMode();
                    }
                }

       public void killMode() {
           removeAllViews();
           if (this.mSplitView != null) {
               this.mSplitView.removeView(this.mMenuView);
                    }
           this.mCustomView = null;
           this.mMenuView = null;
                }

                @Override 
       public boolean showOverflowMenu() {
           if (this.mActionMenuPresenter != null) {
               return this.mActionMenuPresenter.showOverflowMenu();
                    }
                    return false;
                }

                @Override 
       public boolean hideOverflowMenu() {
           if (this.mActionMenuPresenter != null) {
               return this.mActionMenuPresenter.hideOverflowMenu();
                    }
                    return false;
                }

                @Override 
       public boolean isOverflowMenuShowing() {
           if (this.mActionMenuPresenter != null) {
               return this.mActionMenuPresenter.isOverflowMenuShowing();
                    }
                    return false;
                }

                @Override 
       protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
           return new ViewGroup.MarginLayoutParams(-1, -2);
                }

                @Override 
       public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
           return new ViewGroup.MarginLayoutParams(getContext(), attrs);
                }

                @Override 
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
           if (widthMode != 1073741824) {
               throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"FILL_PARENT\" (or fill_parent)");
                    }
           int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
           if (heightMode == 0) {
               throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
                    }
           int contentWidth = View.MeasureSpec.getSize(widthMeasureSpec);
           int maxHeight = this.mContentHeight > 0 ? this.mContentHeight : View.MeasureSpec.getSize(heightMeasureSpec);
           int verticalPadding = getPaddingTop() + getPaddingBottom();
           int availableWidth = (contentWidth - getPaddingLeft()) - getPaddingRight();
           int height = maxHeight - verticalPadding;
           int childSpecHeight = View.MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE);
           if (this.mClose != null) {
               int availableWidth2 = measureChildView(this.mClose, availableWidth, childSpecHeight, 0);
               ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
               availableWidth = availableWidth2 - (lp.leftMargin + lp.rightMargin);
                    }
           if (this.mMenuView != null && this.mMenuView.getParent() == this) {
               availableWidth = measureChildView(this.mMenuView, availableWidth, childSpecHeight, 0);
                    }
           if (this.mTitleLayout != null && this.mCustomView == null) {
               if (this.mTitleOptional) {
                   int titleWidthSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                   this.mTitleLayout.measure(titleWidthSpec, childSpecHeight);
                   int titleWidth = this.mTitleLayout.getMeasuredWidth();
                   boolean titleFits = titleWidth <= availableWidth;
                   if (titleFits) {
                       availableWidth -= titleWidth;
                            }
                   this.mTitleLayout.setVisibility(titleFits ? 0 : 8);
                        } else {
                   availableWidth = measureChildView(this.mTitleLayout, availableWidth, childSpecHeight, 0);
                        }
                    }
           if (this.mCustomView != null) {
               ViewGroup.LayoutParams lp2 = this.mCustomView.getLayoutParams();
               int customWidthMode = lp2.width != -2 ? 1073741824 : Integer.MIN_VALUE;
               int customWidth = lp2.width >= 0 ? Math.min(lp2.width, availableWidth) : availableWidth;
               int customHeightMode = lp2.height != -2 ? 1073741824 : Integer.MIN_VALUE;
               int customHeight = lp2.height >= 0 ? Math.min(lp2.height, height) : height;
               this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(customWidth, customWidthMode), View.MeasureSpec.makeMeasureSpec(customHeight, customHeightMode));
                    }
           if (this.mContentHeight <= 0) {
               int measuredHeight = 0;
               int count = getChildCount();
                        for (int i = 0; i < count; i++) {
                   View v = getChildAt(i);
                   int paddedViewHeight = v.getMeasuredHeight() + verticalPadding;
                   if (paddedViewHeight > measuredHeight) {
                       measuredHeight = paddedViewHeight;
                            }
                        }
               setMeasuredDimension(contentWidth, measuredHeight);
               return;
                    }
           setMeasuredDimension(contentWidth, maxHeight);
                }

                @Override 
       protected void onLayout(boolean changed, int l, int t, int r, int b) {
           int x = getPaddingLeft();
           int y = getPaddingTop();
           int contentHeight = ((b - t) - getPaddingTop()) - getPaddingBottom();
           if (this.mClose != null && this.mClose.getVisibility() != 8) {
               ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
               int x2 = x + lp.leftMargin;
               x = x2 + positionChild(this.mClose, x2, y, contentHeight) + lp.rightMargin;
                    }
           if (this.mTitleLayout != null && this.mCustomView == null && this.mTitleLayout.getVisibility() != 8) {
               x += positionChild(this.mTitleLayout, x, y, contentHeight);
                    }
           if (this.mCustomView != null) {
               int iPositionChild = x + positionChild(this.mCustomView, x, y, contentHeight);
                    }
           int x3 = (r - l) - getPaddingRight();
           if (this.mMenuView != null) {
               int iPositionChildInverse = x3 - positionChildInverse(this.mMenuView, x3, y, contentHeight);
                    }
                }

       public void setTitleOptional(boolean titleOptional) {
           if (titleOptional != this.mTitleOptional) {
               requestLayout();
                    }
           this.mTitleOptional = titleOptional;
                }

       public boolean isTitleOptional() {
           return this.mTitleOptional;
                }
            }
