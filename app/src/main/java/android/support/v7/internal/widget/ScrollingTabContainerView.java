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
            
            
    public class ScrollingTabContainerView extends HorizontalScrollView implements AdapterViewICS.OnItemClickListener {
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

        static  LinearLayout access$200(ScrollingTabContainerView x0) {
            return x0.mTabLayout;
                }

        static  TabView access$300(ScrollingTabContainerView x0, ActionBar.Tab x1, boolean x2) {
            return x0.createTabView(x1, x2);
                }

        public ScrollingTabContainerView(Context context) {
            super(context);
            this.mInflater = LayoutInflater.from(context);
            setHorizontalScrollBarEnabled(false);
            ActionBarPolicy abp = ActionBarPolicy.get(context);
            setContentHeight(abp.getTabContainerHeight());
            this.mStackedTabMaxWidth = abp.getStackedTabMaxWidth();
            this.mTabLayout = (LinearLayout) this.mInflater.inflate(C0084R.layout.abc_action_bar_tabbar, (ViewGroup) this, false);
            addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
                }

                @Override 
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
            boolean lockedExpanded = widthMode == 1073741824;
            setFillViewport(lockedExpanded);
            int childCount = this.mTabLayout.getChildCount();
            if (childCount > 1 && (widthMode == 1073741824 || widthMode == Integer.MIN_VALUE)) {
                if (childCount > 2) {
                    this.mMaxTabWidth = (int) (View.MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
                        } else {
                    this.mMaxTabWidth = View.MeasureSpec.getSize(widthMeasureSpec) / 2;
                        }
                this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
                    } else {
                this.mMaxTabWidth = -1;
                    }
            int heightMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824);
            boolean canCollapse = !lockedExpanded && this.mAllowCollapse;
           if (canCollapse) {
               this.mTabLayout.measure(0, heightMeasureSpec2);
               if (this.mTabLayout.getMeasuredWidth() > View.MeasureSpec.getSize(widthMeasureSpec)) {
                   performCollapse();
                        } else {
                   performExpand();
                        }
                    } else {
               performExpand();
                    }
           int oldWidth = getMeasuredWidth();
           super.onMeasure(widthMeasureSpec, heightMeasureSpec2);
           int newWidth = getMeasuredWidth();
           if (lockedExpanded && oldWidth != newWidth) {
               setTabSelected(this.mSelectedTabIndex);
                    }
                }

       private boolean isCollapsed() {
                    return this.mTabSpinner != null && this.mTabSpinner.getParent() == this;
                }

       public void setAllowCollapse(boolean allowCollapse) {
           this.mAllowCollapse = allowCollapse;
                }

       private void performCollapse() {
           if (!isCollapsed()) {
               if (this.mTabSpinner == null) {
                   this.mTabSpinner = createSpinner();
                        }
               removeView(this.mTabLayout);
               addView(this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
               if (this.mTabSpinner.getAdapter() == null) {
                   this.mTabSpinner.setAdapter((SpinnerAdapter) new TabAdapter(this, null));
                        }
               if (this.mTabSelector != null) {
                   removeCallbacks(this.mTabSelector);
                   this.mTabSelector = null;
                        }
               this.mTabSpinner.setSelection(this.mSelectedTabIndex);
                    }
                }

       private boolean performExpand() {
           if (isCollapsed()) {
               removeView(this.mTabSpinner);
               addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
               setTabSelected(this.mTabSpinner.getSelectedItemPosition());
                    }
                    return false;
                }

       public void setTabSelected(int position) {
           this.mSelectedTabIndex = position;
           int tabCount = this.mTabLayout.getChildCount();
           int i = 0;
                    while (i < tabCount) {
               View child = this.mTabLayout.getChildAt(i);
               boolean isSelected = i == position;
               child.setSelected(isSelected);
               if (isSelected) {
                   animateToTab(position);
                        }
               i++;
                    }
           if (this.mTabSpinner != null && position >= 0) {
               this.mTabSpinner.setSelection(position);
                    }
                }

       public void setContentHeight(int contentHeight) {
           this.mContentHeight = contentHeight;
           requestLayout();
                }

       private SpinnerICS createSpinner() {
           SpinnerICS spinner = new SpinnerICS(getContext(), null, C0084R.attr.actionDropDownStyle);
           spinner.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
           spinner.setOnItemClickListenerInt(this);
           return spinner;
                }

                @Override 
       protected void onConfigurationChanged(Configuration newConfig) {
           ActionBarPolicy abp = ActionBarPolicy.get(getContext());
           setContentHeight(abp.getTabContainerHeight());
           this.mStackedTabMaxWidth = abp.getStackedTabMaxWidth();
                }

       public void animateToTab(int position) {
           View tabView = this.mTabLayout.getChildAt(position);
           if (this.mTabSelector != null) {
               removeCallbacks(this.mTabSelector);
                    }
           this.mTabSelector = new RunnableC01061(tabView);
           post(this.mTabSelector);
                }

                
       class RunnableC01061 implements Runnable {
                    final  View val$tabView;

           RunnableC01061(View view) {
                        this.val$tabView = view;
                    }

                    @Override 
           public void run() {
               int scrollPos = this.val$tabView.getLeft() - ((ScrollingTabContainerView.this.getWidth() - this.val$tabView.getWidth()) / 2);
               ScrollingTabContainerView.this.smoothScrollTo(scrollPos, 0);
               ScrollingTabContainerView.this.mTabSelector = null;
                    }
                }

                @Override 
       public void onAttachedToWindow() {
           super.onAttachedToWindow();
           if (this.mTabSelector != null) {
               post(this.mTabSelector);
                    }
                }

                @Override 
       public void onDetachedFromWindow() {
           super.onDetachedFromWindow();
           if (this.mTabSelector != null) {
               removeCallbacks(this.mTabSelector);
                    }
                }

       private TabView createTabView(ActionBar.Tab tab, boolean forAdapter) {
           TabView tabView = (TabView) this.mInflater.inflate(C0084R.layout.abc_action_bar_tab, (ViewGroup) this.mTabLayout, false);
           tabView.attach(this, tab, forAdapter);
           if (forAdapter) {
               tabView.setBackgroundDrawable(null);
               tabView.setLayoutParams(new AbsListView.LayoutParams(-1, this.mContentHeight));
                    } else {
               tabView.setFocusable(true);
               if (this.mTabClickListener == null) {
                   this.mTabClickListener = new TabClickListener(this, null);
                        }
               tabView.setOnClickListener(this.mTabClickListener);
                    }
           return tabView;
                }

       public void addTab(ActionBar.Tab tab, boolean setSelected) {
           TabView tabView = createTabView(tab, false);
           this.mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0, -1, 1.0f));
           if (this.mTabSpinner != null) {
               ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
                    }
           if (setSelected) {
               tabView.setSelected(true);
                    }
           if (this.mAllowCollapse) {
               requestLayout();
                    }
                }

       public void addTab(ActionBar.Tab tab, int position, boolean setSelected) {
           TabView tabView = createTabView(tab, false);
           this.mTabLayout.addView(tabView, position, new LinearLayout.LayoutParams(0, -1, 1.0f));
           if (this.mTabSpinner != null) {
               ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
                    }
           if (setSelected) {
               tabView.setSelected(true);
                    }
           if (this.mAllowCollapse) {
               requestLayout();
                    }
                }

       public void updateTab(int position) {
           ((TabView) this.mTabLayout.getChildAt(position)).update();
           if (this.mTabSpinner != null) {
               ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
                    }
           if (this.mAllowCollapse) {
               requestLayout();
                    }
                }

       public void removeTabAt(int position) {
           this.mTabLayout.removeViewAt(position);
           if (this.mTabSpinner != null) {
               ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
                    }
           if (this.mAllowCollapse) {
               requestLayout();
                    }
                }

       public void removeAllTabs() {
           this.mTabLayout.removeAllViews();
           if (this.mTabSpinner != null) {
               ((TabAdapter) this.mTabSpinner.getAdapter()).notifyDataSetChanged();
                    }
           if (this.mAllowCollapse) {
               requestLayout();
                    }
                }

                @Override 
       public void onItemClick(AdapterViewICS<?> parent, View view, int position, long id) {
           TabView tabView = (TabView) view;
           tabView.getTab().select();
                }

       public static class TabView extends LinearLayout {
                    private View mCustomView;
                    private ImageView mIconView;
                    private ScrollingTabContainerView mParent;
                    private ActionBar.Tab mTab;
                    private TextView mTextView;

           public TabView(Context context, AttributeSet attrs) {
               super(context, attrs);
                    }

           void attach(ScrollingTabContainerView parent, ActionBar.Tab tab, boolean forList) {
               this.mParent = parent;
               this.mTab = tab;
               if (forList) {
                   setGravity(19);
                        }
               update();
                    }

           public void bindTab(ActionBar.Tab tab) {
               this.mTab = tab;
               update();
                    }

                    @Override 
           public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
               super.onMeasure(widthMeasureSpec, heightMeasureSpec);
               int maxTabWidth = this.mParent != null ? this.mParent.mMaxTabWidth : 0;
               if (maxTabWidth > 0 && getMeasuredWidth() > maxTabWidth) {
                   super.onMeasure(View.MeasureSpec.makeMeasureSpec(maxTabWidth, 1073741824), heightMeasureSpec);
                        }
                    }

           public void update() {
               ActionBar.Tab tab = this.mTab;
               View custom = tab.getCustomView();
               if (custom != null) {
                   ViewParent customParent = custom.getParent();
                   if (customParent != this) {
                       if (customParent != null) {
                           ((ViewGroup) customParent).removeView(custom);
                                }
                       addView(custom);
                            }
                   this.mCustomView = custom;
                   if (this.mTextView != null) {
                       this.mTextView.setVisibility(8);
                            }
                   if (this.mIconView != null) {
                       this.mIconView.setVisibility(8);
                       this.mIconView.setImageDrawable(null);
                       return;
                            }
                   return;
                        }
               if (this.mCustomView != null) {
                   removeView(this.mCustomView);
                   this.mCustomView = null;
                        }
               Drawable icon = tab.getIcon();
               CharSequence text = tab.getText();
               if (icon != null) {
                   if (this.mIconView == null) {
                       ImageView iconView = new ImageView(getContext());
                       LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
                       lp.gravity = 16;
                       iconView.setLayoutParams(lp);
                       addView(iconView, 0);
                       this.mIconView = iconView;
                            }
                   this.mIconView.setImageDrawable(icon);
                   this.mIconView.setVisibility(0);
                        } else if (this.mIconView != null) {
                   this.mIconView.setVisibility(8);
                   this.mIconView.setImageDrawable(null);
                        }
               if (text != null) {
                   if (this.mTextView == null) {
                       TextView textView = new CompatTextView(getContext(), null, C0084R.attr.actionBarTabTextStyle);
                       textView.setEllipsize(TextUtils.TruncateAt.END);
                       LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(-2, -2);
                       lp2.gravity = 16;
                       textView.setLayoutParams(lp2);
                       addView(textView);
                       this.mTextView = textView;
                            }
                   this.mTextView.setText(text);
                   this.mTextView.setVisibility(0);
                        } else if (this.mTextView != null) {
                   this.mTextView.setVisibility(8);
                   this.mTextView.setText((CharSequence) null);
                        }
               if (this.mIconView != null) {
                   this.mIconView.setContentDescription(tab.getContentDescription());
                        }
                    }

           public ActionBar.Tab getTab() {
               return this.mTab;
                    }
                }

       private class TabAdapter extends BaseAdapter {
           private TabAdapter() {
                    }

            TabAdapter(ScrollingTabContainerView x0, RunnableC01061 x1) {
               this();
                    }

                    @Override 
           public int getCount() {
               return ScrollingTabContainerView.access$200(ScrollingTabContainerView.this).getChildCount();
                    }

                    @Override 
           public Object getItem(int position) {
               return ((TabView) ScrollingTabContainerView.access$200(ScrollingTabContainerView.this).getChildAt(position)).getTab();
                    }

                    @Override 
           public long getItemId(int position) {
               return position;
                    }

                    @Override 
           public View getView(int position, View convertView, ViewGroup parent) {
               if (convertView == null) {
                   return ScrollingTabContainerView.access$300(ScrollingTabContainerView.this, (ActionBar.Tab) getItem(position), true);
                        }
               ((TabView) convertView).bindTab((ActionBar.Tab) getItem(position));
               return convertView;
                    }
                }

       private class TabClickListener implements View.OnClickListener {
           private TabClickListener() {
                    }

            TabClickListener(ScrollingTabContainerView x0, RunnableC01061 x1) {
               this();
                    }

                    @Override 
           public void onClick(View view) {
               TabView tabView = (TabView) view;
               tabView.getTab().select();
               int tabCount = ScrollingTabContainerView.access$200(ScrollingTabContainerView.this).getChildCount();
                        for (int i = 0; i < tabCount; i++) {
                   View child = ScrollingTabContainerView.access$200(ScrollingTabContainerView.this).getChildAt(i);
                   child.setSelected(child == view);
                        }
                    }
                }
            }
