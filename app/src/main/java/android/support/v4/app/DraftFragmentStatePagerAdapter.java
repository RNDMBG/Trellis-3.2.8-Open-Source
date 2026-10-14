            package android.support.v4.app;

            import android.os.Bundle;
            import android.os.Parcelable;
            import android.support.v4.view.DraftPagerAdapter;
            import android.view.View;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public abstract class DraftFragmentStatePagerAdapter extends DraftPagerAdapter {
                private final FragmentManager mFragmentManager;
                private FragmentTransaction mCurTransaction = null;
                private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<>();
                private ArrayList<Fragment> mFragments = new ArrayList<>();
                private Fragment mCurrentPrimaryItem = null;

                public abstract Fragment getItem(int i);

/* 35 */        public DraftFragmentStatePagerAdapter(FragmentManager fm) {
/* 37 */            this.mFragmentManager = fm;
                }

                @Override // android.support.v4.view.DraftPagerAdapter
/* 46 */        public void startUpdate(View container) {
                }

                @Override // android.support.v4.view.DraftPagerAdapter
/* 54 */        public Object instantiateItem(View container, int position) {
                    Fragment.SavedState fss;
                    Fragment f;
/* 55 */            if (this.mFragments.size() <= position || (f = this.mFragments.get(position)) == null) {
/* 62 */                if (this.mCurTransaction == null) {
/* 63 */                    this.mCurTransaction = this.mFragmentManager.beginTransaction();
                        }
/* 66 */                Fragment fragment = getItem(position);
/* 67 */                if (this.mSavedState.size() > position && (fss = this.mSavedState.get(position)) != null) {
/* 70 */                    fragment.setInitialSavedState(fss);
                        }
/* 73 */                while (this.mFragments.size() <= position) {
/* 74 */                    this.mFragments.add(null);
                        }
/* 76 */                fragment.setMenuVisibility(false);
/* 77 */                this.mFragments.set(position, fragment);
/* 78 */                this.mCurTransaction.add(container.getId(), fragment);
                        return fragment;
                    }
/* 80 */            return f;
                }

                @Override // android.support.v4.view.DraftPagerAdapter
/* 84 */        public void destroyItem(View container, int position, Object object) {
/* 85 */            Fragment fragment = (Fragment) object;
/* 87 */            if (this.mCurTransaction == null) {
/* 88 */                this.mCurTransaction = this.mFragmentManager.beginTransaction();
                    }
/* 90 */            while (this.mSavedState.size() <= position) {
/* 91 */                this.mSavedState.add(null);
                    }
/* 93 */            this.mSavedState.set(position, this.mFragmentManager.saveFragmentInstanceState(fragment));
/* 94 */            this.mFragments.set(position, null);
/* 96 */            this.mCurTransaction.remove(fragment);
                }

                @Override // android.support.v4.view.DraftPagerAdapter
/* 100 */       public void setPrimaryItem(View container, int position, Object object) {
/* 101 */           Fragment fragment = (Fragment) object;
/* 102 */           if (fragment != this.mCurrentPrimaryItem) {
/* 103 */               if (this.mCurrentPrimaryItem != null) {
/* 104 */                   this.mCurrentPrimaryItem.setMenuVisibility(false);
                        }
/* 106 */               if (fragment != null) {
/* 107 */                   fragment.setMenuVisibility(true);
                        }
/* 109 */               this.mCurrentPrimaryItem = fragment;
                    }
                }

                @Override // android.support.v4.view.DraftPagerAdapter
/* 114 */       public void finishUpdate(View container) {
/* 115 */           if (this.mCurTransaction != null) {
/* 116 */               this.mCurTransaction.commitAllowingStateLoss();
/* 117 */               this.mCurTransaction = null;
/* 118 */               this.mFragmentManager.executePendingTransactions();
                    }
                }

                @Override // android.support.v4.view.DraftPagerAdapter
/* 123 */       public boolean isViewFromObject(View view, Object object) {
                    return ((Fragment) object).getView() == view;
                }

                @Override // android.support.v4.view.DraftPagerAdapter
/* 128 */       public Parcelable saveState() {
/* 129 */           Bundle state = null;
/* 130 */           if (this.mSavedState.size() > 0) {
/* 131 */               state = new Bundle();
/* 132 */               Fragment.SavedState[] fss = new Fragment.SavedState[this.mSavedState.size()];
/* 133 */               this.mSavedState.toArray(fss);
/* 134 */               state.putParcelableArray("states", fss);
                    }
                    for (int i = 0; i < this.mFragments.size(); i++) {
/* 137 */               Fragment f = this.mFragments.get(i);
/* 138 */               if (f != null) {
/* 139 */                   if (state == null) {
/* 140 */                       state = new Bundle();
                            }
/* 142 */                   String key = "f" + i;
/* 143 */                   this.mFragmentManager.putFragment(state, key, f);
                        }
                    }
/* 146 */           return state;
                }

                @Override // android.support.v4.view.DraftPagerAdapter
/* 150 */       public void restoreState(Parcelable state, ClassLoader loader) {
/* 151 */           if (state != null) {
/* 152 */               Bundle bundle = (Bundle) state;
/* 153 */               bundle.setClassLoader(loader);
/* 154 */               Parcelable[] fss = bundle.getParcelableArray("states");
/* 155 */               this.mSavedState.clear();
/* 156 */               this.mFragments.clear();
/* 157 */               if (fss != null) {
                            for (Parcelable fs : fss) {
/* 159 */                       this.mSavedState.add((Fragment.SavedState) fs);
                            }
                        }
/* 162 */               Iterable<String> keys = bundle.keySet();
                        for (String key : keys) {
/* 164 */                   if (key.startsWith("f")) {
/* 165 */                       int index = Integer.parseInt(key.substring(1));
/* 166 */                       Fragment f = this.mFragmentManager.getFragment(bundle, key);
/* 167 */                       if (f != null) {
/* 168 */                           while (this.mFragments.size() <= index) {
/* 169 */                               this.mFragments.add(null);
                                    }
/* 171 */                           f.setMenuVisibility(false);
/* 172 */                           this.mFragments.set(index, f);
                                }
                            }
                        }
                    }
                }
            }
