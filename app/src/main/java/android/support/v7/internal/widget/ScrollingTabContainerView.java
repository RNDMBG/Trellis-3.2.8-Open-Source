            package android.support.v7.internal.widget;

            import android.content.Context;
            import android.content.res.Configuration;
            import android.graphics.drawable.Drawable;
            import android.support.v7.app.ActionBar;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.view.ActionBarPolicy;
            import android.text.TextUtils;
            import android.util.AttributeSet;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewParent;
            import android.widget.AbsListView;
            import android.widget.BaseAdapter;
            import android.widget.HorizontalScrollView;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.SpinnerAdapter;
            import android.widget.TextView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class ScrollingTabContainerView extends HorizontalScrollView implements AdapterViewICS.OnItemClickListener {
                private static final String TAG = "ScrollingTabContainerView";
                private boolean mAllowCollapse;
                private int mContentHeight;
                private final LayoutInflater mInflater;
                int mMaxTabWidth;
                private int mSelectedTabIndex;
                int mStackedTabMaxWidth;
                private TabClickListener mTabClickListener;
                private LinearLayout mTabLayout;
                Runnable mTabSelector;
                private SpinnerICS mTabSpinner;

/* 43 */        static /* synthetic */ LinearLayout access$200(ScrollingTabContainerView x0) {
/* 44 */            return x0.mTabLayout;
                }

/* 43 */        static /* synthetic */ TabView access$300(ScrollingTabContainerView x0, ActionBar.Tab x1, boolean x2) {
/* 44 */            return x0.createTabView(x1, x2);
                }

/* 62 */        public ScrollingTabContainerView(Context context) {
/* 63 */            super(context);
/* 64 */            this.mInflater = LayoutInflater.from(context);
/* 66 */            setHorizontalScrollBarEnabled(false);
/* 68 */            ActionBarPolicy abp = ActionBarPolicy.get(context);
/* 69 */            setContentHeight(abp.getTabContainerHeight());
/* 70 */            this.mStackedTabMaxWidth = abp.getStackedTabMaxWidth();
/* 72 */            this.mTabLayout = (LinearLayout) this.mInflater.inflate(C0084R.layout.abc_action_bar_tabbar, (ViewGroup) this, false);
/* 73 */            addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
                }

                @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
/* 78 */        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 79 */            int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
/* 80 */            boolean lockedExpanded = widthMode == 1073741824;
/* 81 */            setFillViewport(lockedExpanded);
/* 83 */            int childCount = this.mTabLayout.getChildCount();
/* 84 */            if (childCount > 1 && (widthMode == 1073741824 || widthMode == Integer.MIN_VALUE)) {
/* 86 */                if (childCount > 2) {
/* 87 */                    this.mMaxTabWidth = (int) (View.MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
                        } else {
/* 89 */                    this.mMaxTabWidth = View.MeasureSpec.getSize(widthMeasureSpec) / 2;
                        }
/* 91 */                this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
                    } else {
/* 93 */                this.mMaxTabWidth = -1;
                    }
/* 96 */            int heightMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824);
/* 98 */            boolean canCollapse = !lockedExpanded && this.mAllowCollapse;
/* 100 */           if (canCollapse) {
/* 102 */               this.mTabLayout.measure(0, heightMeasureSpec2);
/* 103 */               if (this.mTabLayout.getMeasuredWidth() > View.MeasureSpec.getSize(widthMeasureSpec)) {
/* 104 */                   performCollapse();
                        } else {
/* 106 */                   performExpand();
                        }
                    } else {
/* 109 */               performExpand();
                    }
/* 112 */           int oldWidth = getMeasuredWidth();
/* 113 */           super.onMeasure(widthMeasureSpec, heightMeasureSpec2);
/* 114 */           int newWidth = getMeasuredWidth();
/* 116 */           if (lockedExpanded && oldWidth != newWidth) {
/* 118 */               setTabSelected(this.mSelectedTabIndex);
                    }
                }

/* 127 */       private boolean isCollapsed() {
                    return this.mTabSpinner != null && this.mTabSpinner.getParent() == this;
                }

/* 131 */       public void setAllowCollapse(boolean allowCollapse) {
/* 132 */           this.mAllowCollapse = allowCollapse;
                }

/* 135 */       private void performCollapse() {
/* 136 */           if (!isCollapsed()) {
/* 140 */               if (this.mTabSpinner == null) {
/* 141 */                   this.mTabSpinner = createSpinner();
                        }
/* 143 */               removeView(this.mTabLayout);
/* 144 */               addView(this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
/* 146 */               if (this.mTabSpinner.getAdapter() == null) {
/* 147 */                   this.mTabSpinner.setAdapter((SpinnerAdapter) new TabAdapter(this, null));
                        }
/* 149 */               if (this.mTabSelector != null) {
/* 150 */                   removeCallbacks(this.mTabSelector);
/* 151 */                   this.mTabSelector = null;
                        }
/* 153 */               this.mTabSpinner.setSelection(this.mSelectedTabIndex);
                    }
                }

/* 156 */       private boolean performExpand() {
/* 157 */           if (isCollapsed()) {
/* 161 */               removeView(this.mTabSpinner);
/* 162 */               addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
/* 164 */               setTabSelected(this.mTabSpinner.getSelectedItemPosition());
                    }
                    return false;
                }

/* 168 */       public void setTabSelected(int position) {
/* 169 */           this.mSelectedTabIndex = position;
/* 170 */           int tabCount = this.mTabLayout.getChildCount();
/* 171 */           int i = 0;
                    while (i < tabCount) {
/* 172 */               View child = this.mTabLayout.getChildAt(i);
/* 173 */               boolean isSelected = i == position;
/* 174 */               child.setSelected(isSelected);
/* 175 */               if (isSelected) {
/* 176 */                   animateToTab(position);
                        }
/* 171 */               i++;
                    }
/* 179 */           if (this.mTabSpinner != null && position >= 0) {
/* 180 */               this.mTabSpinner.setSelection(position);
                    }
                }

/* 184 */       public void setContentHeight(int contentHeight) {
/* 185 */           this.mContentHeight = contentHeight;
/* 186 */           requestLayout();
                }

/* 189 */       private SpinnerICS createSpinner() {
/* 190 */           SpinnerICS spinner = new SpinnerICS(getContext(), null, C0084R.attr.actionDropDownStyle);
/* 192 */           spinner.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
/* 194 */           spinner.setOnItemClickListenerInt(this);
/* 195 */           return spinner;
                }

                @Override // android.view.View
/* 198 */       protected void onConfigurationChanged(Configuration newConfig) {
/* 199 */           ActionBarPolicy abp = ActionBarPolicy.get(getContext());
/* 202 */           setContentHeight(abp.getTabContainerHeight());
/* 203 */           this.mStackedTabMaxWidth = abp.getStackedTabMaxWidth();
                }

/* 206 */       public void animateToTab(int position) {
/* 207 */           View tabView = this.mTabLayout.getChildAt(position);
/* 208 */           if (this.mTabSelector != null) {
/* 209 */               removeCallbacks(this.mTabSelector);
                    }
/* 211 */           this.mTabSelector = new RunnableC01061(tabView);
/* 218 */           post(this.mTabSelector);
                }

                /* JADX INFO: renamed from: android.support.v7.internal.widget.ScrollingTabContainerView$1 */
/* 209 */       class RunnableC01061 implements Runnable {
                    final /* synthetic */ View val$tabView;

/* 210 */           RunnableC01061(View view) {
                        this.val$tabView = view;
                    }

                    @Override // java.lang.Runnable
/* 212 */           public void run() {
/* 213 */               int scrollPos = this.val$tabView.getLeft() - ((ScrollingTabContainerView.this.getWidth() - this.val$tabView.getWidth()) / 2);
/* 214 */               ScrollingTabContainerView.this.smoothScrollTo(scrollPos, 0);
/* 215 */               ScrollingTabContainerView.this.mTabSelector = null;
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 222 */       public void onAttachedToWindow() {
/* 223 */           super.onAttachedToWindow();
/* 224 */           if (this.mTabSelector != null) {
/* 226 */               post(this.mTabSelector);
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 231 */       public void onDetachedFromWindow() {
/* 232 */           super.onDetachedFromWindow();
/* 233 */           if (this.mTabSelector != null) {
/* 234 */               removeCallbacks(this.mTabSelector);
                    }
                }

/* 238 */       private TabView createTabView(ActionBar.Tab tab, boolean forAdapter) {
/* 239 */           TabView tabView = (TabView) this.mInflater.inflate(C0084R.layout.abc_action_bar_tab, (ViewGroup) this.mTabLayout, false);
/* 241 */           tabView.attach(this, tab, forAdapter);
/* 243 */           if (forAdapter) {
/* 244 */               tabView.setBackgroundDrawable(null);
/* 245 */               tabView.setLayoutParams(new AbsListView.LayoutParams(-1, this.mContentHeight));
                    } else {
/* 248 */               tabView.setFocusable(true);
/* 250 */               if (this.mTabClickListener == null) {
/* 251 */                   this.mTabClickListener = new TabClickListener(this, null);
                        }
/* 253 */               tabView.setOnClickListener(this.mTabClickListener);
                    }
/* 255 */           return tabView;
                }

/* 258 */       public void addTab(ActionBar.Tab tab, boolean setSelected) {
/* 259 */           TabView tabView = createTabView(tab, false);
/* 260 */           this.mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0, -1, 1.0f));
/* 262 */           if (this.mTabSpinner != null) {
/* 263 */               ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
                    }
/* 265 */           if (setSelected) {
/* 266 */               tabView.setSelected(true);
                    }
/* 268 */           if (this.mAllowCollapse) {
/* 269 */               requestLayout();
                    }
                }

/* 273 */       public void addTab(ActionBar.Tab tab, int position, boolean setSelected) {
/* 274 */           TabView tabView = createTabView(tab, false);
/* 275 */           this.mTabLayout.addView(tabView, position, new LinearLayout.LayoutParams(0, -1, 1.0f));
/* 277 */           if (this.mTabSpinner != null) {
/* 278 */               ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
                    }
/* 280 */           if (setSelected) {
/* 281 */               tabView.setSelected(true);
                    }
/* 283 */           if (this.mAllowCollapse) {
/* 284 */               requestLayout();
                    }
                }

/* 288 */       public void updateTab(int position) {
/* 289 */           ((TabView) this.mTabLayout.getChildAt(position)).update();
/* 290 */           if (this.mTabSpinner != null) {
/* 291 */               ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
                    }
/* 293 */           if (this.mAllowCollapse) {
/* 294 */               requestLayout();
                    }
                }

/* 298 */       public void removeTabAt(int position) {
/* 299 */           this.mTabLayout.removeViewAt(position);
/* 300 */           if (this.mTabSpinner != null) {
/* 301 */               ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
                    }
/* 303 */           if (this.mAllowCollapse) {
/* 304 */               requestLayout();
                    }
                }

/* 308 */       public void removeAllTabs() {
/* 309 */           this.mTabLayout.removeAllViews();
/* 310 */           if (this.mTabSpinner != null) {
/* 311 */               ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
                    }
/* 313 */           if (this.mAllowCollapse) {
/* 314 */               requestLayout();
                    }
                }

                @Override // android.support.v7.internal.widget.AdapterViewICS.OnItemClickListener
/* 319 */       public void onItemClick(AdapterViewICS<?> parent, View view, int position, long id) {
/* 320 */           TabView tabView = (TabView) view;
/* 321 */           tabView.getTab().select();
                }

/* 334 */       public static class TabView extends LinearLayout {
                    private View mCustomView;
                    private ImageView mIconView;
                    private ScrollingTabContainerView mParent;
                    private ActionBar.Tab mTab;
                    private TextView mTextView;

/* 335 */           public TabView(Context context, AttributeSet attrs) {
/* 336 */               super(context, attrs);
                    }

/* 339 */           void attach(ScrollingTabContainerView parent, ActionBar.Tab tab, boolean forList) {
/* 340 */               this.mParent = parent;
/* 341 */               this.mTab = tab;
/* 343 */               if (forList) {
/* 344 */                   setGravity(19);
                        }
/* 347 */               update();
                    }

/* 350 */           public void bindTab(ActionBar.Tab tab) {
/* 351 */               this.mTab = tab;
/* 352 */               update();
                    }

                    @Override // android.widget.LinearLayout, android.view.View
/* 356 */           public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 357 */               super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 359 */               int maxTabWidth = this.mParent != null ? this.mParent.mMaxTabWidth : 0;
/* 362 */               if (maxTabWidth > 0 && getMeasuredWidth() > maxTabWidth) {
/* 363 */                   super.onMeasure(View.MeasureSpec.makeMeasureSpec(maxTabWidth, 1073741824), heightMeasureSpec);
                        }
                    }

/* 368 */           public void update() {
/* 369 */               ActionBar.Tab tab = this.mTab;
/* 370 */               View custom = tab.getCustomView();
/* 371 */               if (custom != null) {
/* 372 */                   ViewParent customParent = custom.getParent();
/* 373 */                   if (customParent != this) {
/* 374 */                       if (customParent != null) {
/* 375 */                           ((ViewGroup) customParent).removeView(custom);
                                }
/* 377 */                       addView(custom);
                            }
/* 379 */                   this.mCustomView = custom;
/* 380 */                   if (this.mTextView != null) {
/* 381 */                       this.mTextView.setVisibility(8);
                            }
/* 383 */                   if (this.mIconView != null) {
/* 384 */                       this.mIconView.setVisibility(8);
/* 385 */                       this.mIconView.setImageDrawable(null);
/* 436 */                       return;
                            }
/* 436 */                   return;
                        }
/* 388 */               if (this.mCustomView != null) {
/* 389 */                   removeView(this.mCustomView);
/* 390 */                   this.mCustomView = null;
                        }
/* 393 */               Drawable icon = tab.getIcon();
/* 394 */               CharSequence text = tab.getText();
/* 396 */               if (icon != null) {
/* 397 */                   if (this.mIconView == null) {
/* 398 */                       ImageView iconView = new ImageView(getContext());
/* 399 */                       LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
/* 401 */                       lp.gravity = 16;
/* 402 */                       iconView.setLayoutParams(lp);
/* 403 */                       addView(iconView, 0);
/* 404 */                       this.mIconView = iconView;
                            }
/* 406 */                   this.mIconView.setImageDrawable(icon);
/* 407 */                   this.mIconView.setVisibility(0);
                        } else if (this.mIconView != null) {
/* 409 */                   this.mIconView.setVisibility(8);
/* 410 */                   this.mIconView.setImageDrawable(null);
                        }
/* 413 */               if (text != null) {
/* 414 */                   if (this.mTextView == null) {
/* 415 */                       TextView textView = new CompatTextView(getContext(), null, C0084R.attr.actionBarTabTextStyle);
/* 417 */                       textView.setEllipsize(TextUtils.TruncateAt.END);
/* 418 */                       LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(-2, -2);
/* 420 */                       lp2.gravity = 16;
/* 421 */                       textView.setLayoutParams(lp2);
/* 422 */                       addView(textView);
/* 423 */                       this.mTextView = textView;
                            }
/* 425 */                   this.mTextView.setText(text);
/* 426 */                   this.mTextView.setVisibility(0);
                        } else if (this.mTextView != null) {
/* 428 */                   this.mTextView.setVisibility(8);
/* 429 */                   this.mTextView.setText((CharSequence) null);
                        }
/* 432 */               if (this.mIconView != null) {
/* 433 */                   this.mIconView.setContentDescription(tab.getContentDescription());
                        }
                    }

/* 438 */           public ActionBar.Tab getTab() {
/* 439 */               return this.mTab;
                    }
                }

/* 441 */       private class TabAdapter extends BaseAdapter {
/* 442 */           private TabAdapter() {
                    }

/* 442 */           /* synthetic */ TabAdapter(ScrollingTabContainerView x0, RunnableC01061 x1) {
/* 443 */               this();
                    }

                    @Override // android.widget.Adapter
/* 446 */           public int getCount() {
/* 447 */               return ScrollingTabContainerView.access$200(ScrollingTabContainerView.this).getChildCount();
                    }

                    @Override // android.widget.Adapter
/* 451 */           public Object getItem(int position) {
/* 452 */               return ((TabView) ScrollingTabContainerView.access$200(ScrollingTabContainerView.this).getChildAt(position)).getTab();
                    }

                    @Override // android.widget.Adapter
/* 456 */           public long getItemId(int position) {
/* 457 */               return position;
                    }

                    @Override // android.widget.Adapter
/* 461 */           public View getView(int position, View convertView, ViewGroup parent) {
/* 462 */               if (convertView == null) {
/* 463 */                   return ScrollingTabContainerView.access$300(ScrollingTabContainerView.this, (ActionBar.Tab) getItem(position), true);
                        }
/* 465 */               ((TabView) convertView).bindTab((ActionBar.Tab) getItem(position));
/* 467 */               return convertView;
                    }
                }

/* 469 */       private class TabClickListener implements View.OnClickListener {
/* 470 */           private TabClickListener() {
                    }

/* 470 */           /* synthetic */ TabClickListener(ScrollingTabContainerView x0, RunnableC01061 x1) {
/* 471 */               this();
                    }

                    @Override // android.view.View.OnClickListener
/* 473 */           public void onClick(View view) {
/* 474 */               TabView tabView = (TabView) view;
/* 475 */               tabView.getTab().select();
/* 476 */               int tabCount = ScrollingTabContainerView.access$200(ScrollingTabContainerView.this).getChildCount();
                        for (int i = 0; i < tabCount; i++) {
/* 478 */                   View child = ScrollingTabContainerView.access$200(ScrollingTabContainerView.this).getChildAt(i);
/* 479 */                   child.setSelected(child == view);
                        }
                    }
                }
            }
