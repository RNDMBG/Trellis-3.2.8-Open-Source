            package android.support.v4.app;

            import android.os.Parcelable;
            import android.support.v4.view.PagerAdapter;
            import android.view.View;
            import android.view.ViewGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 67 */    public abstract class FragmentPagerAdapter extends PagerAdapter {
                private static final boolean DEBUG = false;
                private static final String TAG = "FragmentPagerAdapter";
                private FragmentTransaction mCurTransaction = null;
                private Fragment mCurrentPrimaryItem = null;
                private final FragmentManager mFragmentManager;

                public abstract Fragment getItem(int i);

/* 68 */        public FragmentPagerAdapter(FragmentManager fm) {
/* 70 */            this.mFragmentManager = fm;
                }

                @Override // android.support.v4.view.PagerAdapter
/* 79 */        public void startUpdate(ViewGroup container) {
                }

                @Override // android.support.v4.view.PagerAdapter
/* 83 */        public Object instantiateItem(ViewGroup container, int position) {
/* 84 */            if (this.mCurTransaction == null) {
/* 85 */                this.mCurTransaction = this.mFragmentManager.beginTransaction();
                    }
/* 88 */            long itemId = getItemId(position);
/* 91 */            String name = makeFragmentName(container.getId(), itemId);
/* 92 */            Fragment fragment = this.mFragmentManager.findFragmentByTag(name);
/* 93 */            if (fragment != null) {
/* 95 */                this.mCurTransaction.attach(fragment);
                    } else {
/* 97 */                fragment = getItem(position);
/* 99 */                this.mCurTransaction.add(container.getId(), fragment, makeFragmentName(container.getId(), itemId));
                    }
/* 102 */           if (fragment != this.mCurrentPrimaryItem) {
/* 103 */               fragment.setMenuVisibility(false);
/* 104 */               fragment.setUserVisibleHint(false);
                    }
/* 107 */           return fragment;
                }

                @Override // android.support.v4.view.PagerAdapter
/* 111 */       public void destroyItem(ViewGroup container, int position, Object object) {
/* 112 */           if (this.mCurTransaction == null) {
/* 113 */               this.mCurTransaction = this.mFragmentManager.beginTransaction();
                    }
/* 117 */           this.mCurTransaction.detach((Fragment) object);
                }

                @Override // android.support.v4.view.PagerAdapter
/* 121 */       public void setPrimaryItem(ViewGroup container, int position, Object object) {
/* 122 */           Fragment fragment = (Fragment) object;
/* 123 */           if (fragment != this.mCurrentPrimaryItem) {
/* 124 */               if (this.mCurrentPrimaryItem != null) {
/* 125 */                   this.mCurrentPrimaryItem.setMenuVisibility(false);
/* 126 */                   this.mCurrentPrimaryItem.setUserVisibleHint(false);
                        }
/* 128 */               if (fragment != null) {
/* 129 */                   fragment.setMenuVisibility(true);
/* 130 */                   fragment.setUserVisibleHint(true);
                        }
/* 132 */               this.mCurrentPrimaryItem = fragment;
                    }
                }

                @Override // android.support.v4.view.PagerAdapter
/* 137 */       public void finishUpdate(ViewGroup container) {
/* 138 */           if (this.mCurTransaction != null) {
/* 139 */               this.mCurTransaction.commitAllowingStateLoss();
/* 140 */               this.mCurTransaction = null;
/* 141 */               this.mFragmentManager.executePendingTransactions();
                    }
                }

                @Override // android.support.v4.view.PagerAdapter
/* 146 */       public boolean isViewFromObject(View view, Object object) {
                    return ((Fragment) object).getView() == view;
                }

                @Override // android.support.v4.view.PagerAdapter
/* 151 */       public Parcelable saveState() {
/* 152 */           return null;
                }

                @Override // android.support.v4.view.PagerAdapter
/* 156 */       public void restoreState(Parcelable state, ClassLoader loader) {
                }

/* 168 */       public long getItemId(int position) {
/* 169 */           return position;
                }

/* 172 */       private static String makeFragmentName(int viewId, long id) {
/* 173 */           return "android:switcher:" + viewId + ":" + id;
                }
            }
