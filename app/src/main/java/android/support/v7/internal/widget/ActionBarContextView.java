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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class ActionBarContextView extends AbsActionBarView {
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

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 36 */        public /* bridge */ /* synthetic */ void animateToVisibility(int x0) {
/* 37 */            super.animateToVisibility(x0);
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 36 */        public /* bridge */ /* synthetic */ void dismissPopupMenus() {
/* 37 */            super.dismissPopupMenus();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 36 */        public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
/* 37 */            return super.getAnimatedVisibility();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 36 */        public /* bridge */ /* synthetic */ int getContentHeight() {
/* 37 */            return super.getContentHeight();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 36 */        public /* bridge */ /* synthetic */ boolean isOverflowReserved() {
/* 37 */            return super.isOverflowReserved();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 36 */        public /* bridge */ /* synthetic */ void postShowOverflowMenu() {
/* 37 */            super.postShowOverflowMenu();
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 36 */        public /* bridge */ /* synthetic */ void setSplitView(ActionBarContainer x0) {
/* 37 */            super.setSplitView(x0);
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 36 */        public /* bridge */ /* synthetic */ void setSplitWhenNarrow(boolean x0) {
/* 37 */            super.setSplitWhenNarrow(x0);
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView, android.view.View
/* 36 */        public /* bridge */ /* synthetic */ void setVisibility(int x0) {
/* 37 */            super.setVisibility(x0);
                }

/* 54 */        public ActionBarContextView(Context context) {
/* 55 */            this(context, null);
                }

/* 58 */        public ActionBarContextView(Context context, AttributeSet attrs) {
/* 59 */            this(context, attrs, C0084R.attr.actionModeStyle);
                }

/* 62 */        public ActionBarContextView(Context context, AttributeSet attrs, int defStyle) {
/* 63 */            super(context, attrs, defStyle);
/* 65 */            TypedArray a = context.obtainStyledAttributes(attrs, C0084R.styleable.ActionMode, defStyle, 0);
/* 66 */            setBackgroundDrawable(a.getDrawable(3));
/* 68 */            this.mTitleStyleRes = a.getResourceId(1, 0);
/* 70 */            this.mSubtitleStyleRes = a.getResourceId(2, 0);
/* 73 */            this.mContentHeight = a.getLayoutDimension(0, 0);
/* 76 */            this.mSplitBackground = a.getDrawable(4);
/* 79 */            a.recycle();
                }

                @Override // android.view.ViewGroup, android.view.View
/* 83 */        public void onDetachedFromWindow() {
/* 84 */            super.onDetachedFromWindow();
/* 85 */            if (this.mActionMenuPresenter != null) {
/* 86 */                this.mActionMenuPresenter.hideOverflowMenu();
/* 87 */                this.mActionMenuPresenter.hideSubMenus();
                    }
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 92 */        public void setSplitActionBar(boolean split) {
/* 93 */            if (this.mSplitActionBar != split) {
/* 94 */                if (this.mActionMenuPresenter != null) {
/* 96 */                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
/* 99 */                    if (!split) {
/* 100 */                       this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
/* 101 */                       this.mMenuView.setBackgroundDrawable(null);
/* 102 */                       ViewGroup oldParent = (ViewGroup) this.mMenuView.getParent();
/* 103 */                       if (oldParent != null) {
/* 104 */                           oldParent.removeView(this.mMenuView);
                                }
/* 106 */                       addView(this.mMenuView, layoutParams);
                            } else {
/* 109 */                       this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
/* 112 */                       this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
/* 114 */                       layoutParams.width = -1;
/* 115 */                       layoutParams.height = this.mContentHeight;
/* 116 */                       this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
/* 117 */                       this.mMenuView.setBackgroundDrawable(this.mSplitBackground);
/* 118 */                       ViewGroup oldParent2 = (ViewGroup) this.mMenuView.getParent();
/* 119 */                       if (oldParent2 != null) {
/* 120 */                           oldParent2.removeView(this.mMenuView);
                                }
/* 122 */                       this.mSplitView.addView(this.mMenuView, layoutParams);
                            }
                        }
/* 125 */               super.setSplitActionBar(split);
                    }
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 129 */       public void setContentHeight(int height) {
/* 130 */           this.mContentHeight = height;
                }

/* 133 */       public void setCustomView(View view) {
/* 134 */           if (this.mCustomView != null) {
/* 135 */               removeView(this.mCustomView);
                    }
/* 137 */           this.mCustomView = view;
/* 138 */           if (this.mTitleLayout != null) {
/* 139 */               removeView(this.mTitleLayout);
/* 140 */               this.mTitleLayout = null;
                    }
/* 142 */           if (view != null) {
/* 143 */               addView(view);
                    }
/* 145 */           requestLayout();
                }

/* 148 */       public void setTitle(CharSequence title) {
/* 149 */           this.mTitle = title;
/* 150 */           initTitle();
                }

/* 153 */       public void setSubtitle(CharSequence subtitle) {
/* 154 */           this.mSubtitle = subtitle;
/* 155 */           initTitle();
                }

/* 158 */       public CharSequence getTitle() {
/* 159 */           return this.mTitle;
                }

/* 162 */       public CharSequence getSubtitle() {
/* 163 */           return this.mSubtitle;
                }

/* 166 */       private void initTitle() {
/* 167 */           if (this.mTitleLayout == null) {
/* 168 */               LayoutInflater inflater = LayoutInflater.from(getContext());
/* 169 */               inflater.inflate(C0084R.layout.abc_action_bar_title_item, this);
/* 170 */               this.mTitleLayout = (LinearLayout) getChildAt(getChildCount() - 1);
/* 171 */               this.mTitleView = (TextView) this.mTitleLayout.findViewById(C0084R.id.action_bar_title);
/* 172 */               this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(C0084R.id.action_bar_subtitle);
/* 173 */               if (this.mTitleStyleRes != 0) {
/* 174 */                   this.mTitleView.setTextAppearance(getContext(), this.mTitleStyleRes);
                        }
/* 176 */               if (this.mSubtitleStyleRes != 0) {
/* 177 */                   this.mSubtitleView.setTextAppearance(getContext(), this.mSubtitleStyleRes);
                        }
                    }
/* 181 */           this.mTitleView.setText(this.mTitle);
/* 182 */           this.mSubtitleView.setText(this.mSubtitle);
/* 184 */           boolean hasTitle = !TextUtils.isEmpty(this.mTitle);
/* 185 */           boolean hasSubtitle = !TextUtils.isEmpty(this.mSubtitle);
/* 186 */           this.mSubtitleView.setVisibility(hasSubtitle ? 0 : 8);
/* 187 */           this.mTitleLayout.setVisibility((hasTitle || hasSubtitle) ? 0 : 8);
/* 188 */           if (this.mTitleLayout.getParent() == null) {
/* 189 */               addView(this.mTitleLayout);
                    }
                }

/* 193 */       public void initForMode(ActionMode mode) {
/* 194 */           if (this.mClose == null) {
/* 195 */               LayoutInflater inflater = LayoutInflater.from(getContext());
/* 196 */               this.mClose = inflater.inflate(C0084R.layout.abc_action_mode_close_item, (ViewGroup) this, false);
/* 197 */               addView(this.mClose);
                    } else if (this.mClose.getParent() == null) {
/* 199 */               addView(this.mClose);
                    }
/* 202 */           View closeButton = this.mClose.findViewById(C0084R.id.action_mode_close_button);
/* 203 */           closeButton.setOnClickListener(new ViewOnClickListenerC00921(mode));
/* 209 */           MenuBuilder menu = (MenuBuilder) mode.getMenu();
/* 210 */           if (this.mActionMenuPresenter != null) {
/* 211 */               this.mActionMenuPresenter.dismissPopupMenus();
                    }
/* 213 */           this.mActionMenuPresenter = new ActionMenuPresenter(getContext());
/* 214 */           this.mActionMenuPresenter.setReserveOverflow(true);
/* 216 */           ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
/* 219 */           if (!this.mSplitActionBar) {
/* 220 */               menu.addMenuPresenter(this.mActionMenuPresenter);
/* 221 */               this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
/* 222 */               this.mMenuView.setBackgroundDrawable(null);
/* 223 */               addView(this.mMenuView, layoutParams);
/* 238 */               return;
                    }
/* 226 */           this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
/* 229 */           this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
/* 231 */           layoutParams.width = -1;
/* 232 */           layoutParams.height = this.mContentHeight;
/* 233 */           menu.addMenuPresenter(this.mActionMenuPresenter);
/* 234 */           this.mMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
/* 235 */           this.mMenuView.setBackgroundDrawable(this.mSplitBackground);
/* 236 */           this.mSplitView.addView(this.mMenuView, layoutParams);
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.ActionBarContextView$1 */
/* 201 */       class ViewOnClickListenerC00921 implements View.OnClickListener {
                    final /* synthetic */ ActionMode val$mode;

/* 202 */           ViewOnClickListenerC00921(ActionMode actionMode) {
                        this.val$mode = actionMode;
                    }

                    @Override // android.view.View.OnClickListener
/* 204 */           public void onClick(View v) {
/* 205 */               this.val$mode.finish();
                    }
                }

/* 240 */       public void closeMode() {
/* 241 */           if (this.mClose == null) {
/* 242 */               killMode();
                    }
                }

/* 247 */       public void killMode() {
/* 248 */           removeAllViews();
/* 249 */           if (this.mSplitView != null) {
/* 250 */               this.mSplitView.removeView(this.mMenuView);
                    }
/* 252 */           this.mCustomView = null;
/* 253 */           this.mMenuView = null;
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 257 */       public boolean showOverflowMenu() {
/* 258 */           if (this.mActionMenuPresenter != null) {
/* 259 */               return this.mActionMenuPresenter.showOverflowMenu();
                    }
                    return false;
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 265 */       public boolean hideOverflowMenu() {
/* 266 */           if (this.mActionMenuPresenter != null) {
/* 267 */               return this.mActionMenuPresenter.hideOverflowMenu();
                    }
                    return false;
                }

                @Override // android.support.v7.internal.widget.AbsActionBarView
/* 273 */       public boolean isOverflowMenuShowing() {
/* 274 */           if (this.mActionMenuPresenter != null) {
/* 275 */               return this.mActionMenuPresenter.isOverflowMenuShowing();
                    }
                    return false;
                }

                @Override // android.view.ViewGroup
/* 283 */       protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
/* 284 */           return new ViewGroup.MarginLayoutParams(-1, -2);
                }

                @Override // android.view.ViewGroup
/* 289 */       public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
/* 290 */           return new ViewGroup.MarginLayoutParams(getContext(), attrs);
                }

                @Override // android.view.View
/* 294 */       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 295 */           int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 296 */           if (widthMode != 1073741824) {
/* 297 */               throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"FILL_PARENT\" (or fill_parent)");
                    }
/* 301 */           int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
/* 302 */           if (heightMode == 0) {
/* 303 */               throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
                    }
/* 307 */           int contentWidth = View.MeasureSpec.getSize(widthMeasureSpec);
/* 309 */           int maxHeight = this.mContentHeight > 0 ? this.mContentHeight : View.MeasureSpec.getSize(heightMeasureSpec);
/* 312 */           int verticalPadding = getPaddingTop() + getPaddingBottom();
/* 313 */           int availableWidth = (contentWidth - getPaddingLeft()) - getPaddingRight();
/* 314 */           int height = maxHeight - verticalPadding;
/* 315 */           int childSpecHeight = View.MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE);
/* 318 */           if (this.mClose != null) {
/* 319 */               int availableWidth2 = measureChildView(this.mClose, availableWidth, childSpecHeight, 0);
/* 320 */               ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
/* 322 */               availableWidth = availableWidth2 - (lp.leftMargin + lp.rightMargin);
                    }
/* 325 */           if (this.mMenuView != null && this.mMenuView.getParent() == this) {
/* 326 */               availableWidth = measureChildView(this.mMenuView, availableWidth, childSpecHeight, 0);
                    }
/* 330 */           if (this.mTitleLayout != null && this.mCustomView == null) {
/* 331 */               if (this.mTitleOptional) {
/* 332 */                   int titleWidthSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 334 */                   this.mTitleLayout.measure(titleWidthSpec, childSpecHeight);
/* 335 */                   int titleWidth = this.mTitleLayout.getMeasuredWidth();
/* 336 */                   boolean titleFits = titleWidth <= availableWidth;
/* 337 */                   if (titleFits) {
/* 338 */                       availableWidth -= titleWidth;
                            }
/* 340 */                   this.mTitleLayout.setVisibility(titleFits ? 0 : 8);
                        } else {
/* 342 */                   availableWidth = measureChildView(this.mTitleLayout, availableWidth, childSpecHeight, 0);
                        }
                    }
/* 346 */           if (this.mCustomView != null) {
/* 347 */               ViewGroup.LayoutParams lp2 = this.mCustomView.getLayoutParams();
/* 348 */               int customWidthMode = lp2.width != -2 ? 1073741824 : Integer.MIN_VALUE;
/* 350 */               int customWidth = lp2.width >= 0 ? Math.min(lp2.width, availableWidth) : availableWidth;
/* 352 */               int customHeightMode = lp2.height != -2 ? 1073741824 : Integer.MIN_VALUE;
/* 354 */               int customHeight = lp2.height >= 0 ? Math.min(lp2.height, height) : height;
/* 356 */               this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(customWidth, customWidthMode), View.MeasureSpec.makeMeasureSpec(customHeight, customHeightMode));
                    }
/* 360 */           if (this.mContentHeight <= 0) {
/* 361 */               int measuredHeight = 0;
/* 362 */               int count = getChildCount();
                        for (int i = 0; i < count; i++) {
/* 364 */                   View v = getChildAt(i);
/* 365 */                   int paddedViewHeight = v.getMeasuredHeight() + verticalPadding;
/* 366 */                   if (paddedViewHeight > measuredHeight) {
/* 367 */                       measuredHeight = paddedViewHeight;
                            }
                        }
/* 370 */               setMeasuredDimension(contentWidth, measuredHeight);
/* 374 */               return;
                    }
/* 372 */           setMeasuredDimension(contentWidth, maxHeight);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 377 */       protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 378 */           int x = getPaddingLeft();
/* 379 */           int y = getPaddingTop();
/* 380 */           int contentHeight = ((b - t) - getPaddingTop()) - getPaddingBottom();
/* 382 */           if (this.mClose != null && this.mClose.getVisibility() != 8) {
/* 383 */               ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
/* 385 */               int x2 = x + lp.leftMargin;
/* 387 */               x = x2 + positionChild(this.mClose, x2, y, contentHeight) + lp.rightMargin;
                    }
/* 391 */           if (this.mTitleLayout != null && this.mCustomView == null && this.mTitleLayout.getVisibility() != 8) {
/* 392 */               x += positionChild(this.mTitleLayout, x, y, contentHeight);
                    }
/* 395 */           if (this.mCustomView != null) {
/* 396 */               int iPositionChild = x + positionChild(this.mCustomView, x, y, contentHeight);
                    }
/* 399 */           int x3 = (r - l) - getPaddingRight();
/* 401 */           if (this.mMenuView != null) {
/* 402 */               int iPositionChildInverse = x3 - positionChildInverse(this.mMenuView, x3, y, contentHeight);
                    }
                }

/* 406 */       public void setTitleOptional(boolean titleOptional) {
/* 407 */           if (titleOptional != this.mTitleOptional) {
/* 408 */               requestLayout();
                    }
/* 410 */           this.mTitleOptional = titleOptional;
                }

/* 413 */       public boolean isTitleOptional() {
/* 414 */           return this.mTitleOptional;
                }
            }
