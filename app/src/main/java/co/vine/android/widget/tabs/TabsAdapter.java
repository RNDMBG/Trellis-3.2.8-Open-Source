            package co.vine.android.widget.tabs;

            import android.content.ComponentCallbacks;
            import android.os.Bundle;
            import android.os.Parcelable;
            import android.support.v4.app.Fragment;
            import android.support.v4.app.FragmentPagerAdapter;
            import android.support.v4.view.ViewPager;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.TabHost;
            import android.widget.TabWidget;
            import co.vine.android.BaseArrayListFragment;
            import co.vine.android.BaseControllerActionBarActivity;
            import co.vine.android.BaseFragment;
            import co.vine.android.widget.OnTabChangedListener;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public class TabsAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
                private final BaseControllerActionBarActivity mActivity;
                private final ViewPagerScrollBar mScrollBar;
                private boolean mSetActionBarColorOnPageSelectedEnabled;
                private final DummyTabFactory mTabFactory;
                private final TabHost mTabHost;
                private final ArrayList<TabInfo> mTabs;
                private final ViewPager mViewPager;
                public int previousTab;

/* 42 */        public ArrayList<TabInfo> getTabs() {
/* 43 */            return this.mTabs;
                }

/* 46 */        public ArrayList<TabInfo> tabs() {
/* 47 */            return this.mTabs;
                }

/* 50 */        public TabInfo getTab(int pos) {
/* 51 */            return this.mTabs.get(pos);
                }

/* 55 */        public TabsAdapter(BaseControllerActionBarActivity activity, TabHost tabHost, ViewPager pager, ViewPagerScrollBar scrollBar) {
/* 56 */            super(activity.getSupportFragmentManager());
/* 37 */            this.mTabs = new ArrayList<>();
/* 40 */            this.mSetActionBarColorOnPageSelectedEnabled = true;
/* 57 */            this.mActivity = activity;
/* 58 */            this.mTabHost = tabHost;
/* 59 */            this.mViewPager = pager;
/* 60 */            this.mViewPager.setAdapter(this);
/* 61 */            this.mViewPager.setOnPageChangeListener(this);
/* 62 */            this.mScrollBar = scrollBar;
/* 63 */            this.mTabFactory = new DummyTabFactory(this.mActivity);
                }

/* 66 */        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
/* 67 */            TabInfo info = new TabInfo(clss, args, tabSpec.getTag());
/* 68 */            tabSpec.setContent(this.mTabFactory);
/* 69 */            this.mTabs.add(info);
/* 70 */            this.mTabHost.addTab(tabSpec);
/* 71 */            notifyDataSetChanged();
                }

                @Override // android.support.v4.view.PagerAdapter
/* 75 */        public int getCount() {
/* 76 */            return this.mTabs.size();
                }

                @Override // android.support.v4.view.PagerAdapter
/* 80 */        public int getItemPosition(Object object) {
/* 81 */            boolean found = false;
                    for (TabInfo info : this.mTabs) {
/* 83 */                if (info.fragment() == object) {
/* 84 */                    found = true;
                            break;
                        }
                    }
                    return found ? -1 : -2;
                }

/* 95 */        public Fragment getCurrentFragment() {
/* 96 */            TabInfo tab = getTab(this.mTabHost.getCurrentTab());
/* 97 */            if (tab != null) {
/* 98 */                return tab.fragment();
                    }
                    return null;
                }

                @Override // android.support.v4.app.FragmentPagerAdapter, android.support.v4.view.PagerAdapter
/* 105 */       public Parcelable saveState() {
/* 106 */           return new TabSavedState(this.mTabs);
                }

                @Override // android.support.v4.app.FragmentPagerAdapter, android.support.v4.view.PagerAdapter
/* 110 */       public void restoreState(Parcelable state, ClassLoader loader) {
/* 111 */           TabSavedState savedState = (TabSavedState) state;
/* 112 */           String[] tags = savedState.tags;
                    for (int i = 0; i < tags.length; i++) {
/* 114 */               String tag = tags[i];
/* 115 */               Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentByTag(tag);
/* 117 */               if (fragment != null) {
/* 118 */                   this.mTabs.get(i).setFragment(fragment);
/* 119 */                   if (fragment instanceof BaseArrayListFragment) {
/* 120 */                       ((BaseArrayListFragment) fragment).setScrollListener(new TabScrollListener(this.mActivity, i));
                            }
                        }
                    }
                }

                @Override // android.support.v4.app.FragmentPagerAdapter
/* 128 */       public Fragment getItem(int position) {
/* 129 */           return Fragment.instantiate(this.mActivity, this.mTabs.get(position).clss.getName(), this.mTabs.get(position).bundle);
                }

                @Override // android.support.v4.app.FragmentPagerAdapter, android.support.v4.view.PagerAdapter
/* 134 */       public Object instantiateItem(ViewGroup container, int position) {
/* 135 */           Fragment blf = (Fragment) super.instantiateItem(container, position);
/* 137 */           this.mTabs.get(position).setFragment(blf);
/* 139 */           if (blf instanceof BaseArrayListFragment) {
/* 140 */               ((BaseArrayListFragment) blf).setScrollListener(new TabScrollListener(this.mActivity, position));
                    }
/* 144 */           return blf;
                }

                @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 147 */       public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
/* 148 */           if (this.mViewPager.getCurrentItem() < position && this.mScrollBar != null) {
/* 149 */               this.mScrollBar.scroll(position, -positionOffsetPixels);
                    } else if (this.mScrollBar != null) {
/* 151 */               this.mScrollBar.scroll(position, positionOffsetPixels);
                    }
                }

/* 159 */       public void onPageSelected(int position) {
/* 160 */           TabWidget widget = this.mTabHost.getTabWidget();
/* 161 */           int descendantFocusability = widget.getDescendantFocusability();
/* 162 */           widget.setDescendantFocusability(393216);
/* 163 */           if (position != this.previousTab) {
/* 164 */               ((IconTabHost) this.mTabHost).setTabExplicit(position);
/* 165 */               ComponentCallbacks componentCallbacksFragment = this.mTabs.get(this.previousTab).fragment();
/* 166 */               if (componentCallbacksFragment != null && (componentCallbacksFragment instanceof OnTabChangedListener)) {
/* 167 */                   ((OnTabChangedListener) componentCallbacksFragment).onMoveAway(position);
                        }
/* 169 */               ComponentCallbacks componentCallbacksFragment2 = this.mTabs.get(position).fragment();
/* 170 */               if (componentCallbacksFragment2 != null && (componentCallbacksFragment2 instanceof OnTabChangedListener)) {
/* 171 */                   ((OnTabChangedListener) componentCallbacksFragment2).onMoveTo(this.previousTab);
                        }
/* 174 */               if (componentCallbacksFragment2 != null && this.mSetActionBarColorOnPageSelectedEnabled) {
/* 175 */                   ((BaseFragment) componentCallbacksFragment2).setActionBarColor();
                        }
                    }
/* 178 */           this.previousTab = this.mTabHost.getCurrentTab();
/* 179 */           if (this.mScrollBar != null) {
/* 180 */               this.mScrollBar.setPageColor(position);
                    }
/* 182 */           widget.setDescendantFocusability(descendantFocusability);
                }

                @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 185 */       public void onPageScrollStateChanged(int state) {
                }

/* 188 */       public TabIndicator getIndicator(int tabIndex) {
/* 189 */           TabWidget widget = this.mTabHost.getTabWidget();
/* 190 */           if (widget != null) {
                        return (TabIndicator) widget.getChildTabViewAt(tabIndex);
                    }
                    return null;
                }

/* 193 */       public void setNew(int tabIndex, boolean isNew) {
/* 194 */           TabIndicator indicator = getIndicator(tabIndex);
/* 195 */           if (indicator != null) {
/* 196 */               indicator.setNew(isNew);
                    }
                }

/* 200 */       public ImageView getNewIndicatorForTab(int tabIndex) {
/* 201 */           TabIndicator indicator = getIndicator(tabIndex);
/* 202 */           if (indicator != null) {
                        return indicator.getNewIndicator();
                    }
                    return null;
                }

/* 205 */       public void setSetActionBarColorOnPageSelectedEnabled(boolean setActionBarColorOnPageSelectedEnabled) {
/* 206 */           this.mSetActionBarColorOnPageSelectedEnabled = setActionBarColorOnPageSelectedEnabled;
                }
            }
