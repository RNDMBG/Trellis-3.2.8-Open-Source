            package android.support.v4.app;

            import android.os.Bundle;
            import android.os.Parcelable;
            import android.support.v4.view.PagerAdapter;
            import android.util.Log;
            import android.view.View;
            import android.view.ViewGroup;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 73 */    public abstract class FragmentStatePagerAdapter extends PagerAdapter {
                private static final boolean DEBUG = false;
                private static final String TAG = "FragmentStatePagerAdapter";
                private final FragmentManager mFragmentManager;
                private FragmentTransaction mCurTransaction = null;
                private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<>();
                private ArrayList<Fragment> mFragments = new ArrayList<>();
                private Fragment mCurrentPrimaryItem = null;

                public abstract Fragment getItem(int i);

/* 74 */        public FragmentStatePagerAdapter(FragmentManager fm) {
/* 76 */            this.mFragmentManager = fm;
                }

                @Override // android.support.v4.view.PagerAdapter
/* 85 */        public void startUpdate(ViewGroup container) {
                }

                @Override // android.support.v4.view.PagerAdapter
/* 93 */        public Object instantiateItem(ViewGroup container, int position) {
                    Fragment.SavedState fss;
                    Fragment f;
/* 94 */            if (this.mFragments.size() <= position || (f = this.mFragments.get(position)) == null) {
/* 101 */               if (this.mCurTransaction == null) {
/* 102 */                   this.mCurTransaction = this.mFragmentManager.beginTransaction();
                        }
/* 105 */               Fragment fragment = getItem(position);
/* 107 */               if (this.mSavedState.size() > position && (fss = this.mSavedState.get(position)) != null) {
/* 110 */                   fragment.setInitialSavedState(fss);
                        }
/* 113 */               while (this.mFragments.size() <= position) {
/* 114 */                   this.mFragments.add(null);
                        }
/* 116 */               fragment.setMenuVisibility(false);
/* 117 */               fragment.setUserVisibleHint(false);
/* 118 */               this.mFragments.set(position, fragment);
/* 119 */               this.mCurTransaction.add(container.getId(), fragment);
                        return fragment;
                    }
/* 121 */           return f;
                }

                @Override // android.support.v4.view.PagerAdapter
/* 125 */       public void destroyItem(ViewGroup container, int position, Object object) {
/* 126 */           Fragment fragment = (Fragment) object;
/* 128 */           if (this.mCurTransaction == null) {
/* 129 */               this.mCurTransaction = this.mFragmentManager.beginTransaction();
                    }
/* 133 */           while (this.mSavedState.size() <= position) {
/* 134 */               this.mSavedState.add(null);
                    }
/* 136 */           this.mSavedState.set(position, this.mFragmentManager.saveFragmentInstanceState(fragment));
/* 137 */           this.mFragments.set(position, null);
/* 139 */           this.mCurTransaction.remove(fragment);
                }

                @Override // android.support.v4.view.PagerAdapter
/* 143 */       public void setPrimaryItem(ViewGroup container, int position, Object object) {
/* 144 */           Fragment fragment = (Fragment) object;
/* 145 */           if (fragment != this.mCurrentPrimaryItem) {
/* 146 */               if (this.mCurrentPrimaryItem != null) {
/* 147 */                   this.mCurrentPrimaryItem.setMenuVisibility(false);
/* 148 */                   this.mCurrentPrimaryItem.setUserVisibleHint(false);
                        }
/* 150 */               if (fragment != null) {
/* 151 */                   fragment.setMenuVisibility(true);
/* 152 */                   fragment.setUserVisibleHint(true);
                        }
/* 154 */               this.mCurrentPrimaryItem = fragment;
                    }
                }

                @Override // android.support.v4.view.PagerAdapter
/* 159 */       public void finishUpdate(ViewGroup container) {
/* 160 */           if (this.mCurTransaction != null) {
/* 161 */               this.mCurTransaction.commitAllowingStateLoss();
/* 162 */               this.mCurTransaction = null;
/* 163 */               this.mFragmentManager.executePendingTransactions();
                    }
                }

                @Override // android.support.v4.view.PagerAdapter
/* 168 */       public boolean isViewFromObject(View view, Object object) {
                    return ((Fragment) object).getView() == view;
                }

                @Override // android.support.v4.view.PagerAdapter
/* 173 */       public Parcelable saveState() {
/* 174 */           Bundle state = null;
/* 175 */           if (this.mSavedState.size() > 0) {
/* 176 */               state = new Bundle();
/* 177 */               Fragment.SavedState[] fss = new Fragment.SavedState[this.mSavedState.size()];
/* 178 */               this.mSavedState.toArray(fss);
/* 179 */               state.putParcelableArray("states", fss);
                    }
                    for (int i = 0; i < this.mFragments.size(); i++) {
/* 182 */               Fragment f = this.mFragments.get(i);
/* 183 */               if (f != null) {
/* 184 */                   if (state == null) {
/* 185 */                       state = new Bundle();
                            }
/* 187 */                   String key = "f" + i;
/* 188 */                   this.mFragmentManager.putFragment(state, key, f);
                        }
                    }
/* 191 */           return state;
                }

                @Override // android.support.v4.view.PagerAdapter
/* 195 */       public void restoreState(Parcelable state, ClassLoader loader) {
/* 196 */           if (state != null) {
/* 197 */               Bundle bundle = (Bundle) state;
/* 198 */               bundle.setClassLoader(loader);
/* 199 */               Parcelable[] fss = bundle.getParcelableArray("states");
/* 200 */               this.mSavedState.clear();
/* 201 */               this.mFragments.clear();
/* 202 */               if (fss != null) {
                            for (Parcelable parcelable : fss) {
/* 204 */                       this.mSavedState.add((Fragment.SavedState) parcelable);
                            }
                        }
/* 207 */               Iterable<String> keys = bundle.keySet();
                        for (String key : keys) {
/* 209 */                   if (key.startsWith("f")) {
/* 210 */                       int index = Integer.parseInt(key.substring(1));
/* 211 */                       Fragment f = this.mFragmentManager.getFragment(bundle, key);
/* 212 */                       if (f != null) {
/* 213 */                           while (this.mFragments.size() <= index) {
/* 214 */                               this.mFragments.add(null);
                                    }
/* 216 */                           f.setMenuVisibility(false);
/* 217 */                           this.mFragments.set(index, f);
                                } else {
/* 219 */                           Log.w(TAG, "Bad fragment at key " + key);
                                }
                            }
                        }
                    }
                }
            }
