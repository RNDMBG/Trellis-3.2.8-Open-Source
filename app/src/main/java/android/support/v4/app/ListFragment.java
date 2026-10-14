            package android.support.v4.app;

            import android.R;
            import android.content.Context;
            import android.os.Bundle;
            import android.os.Handler;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.AnimationUtils;
            import android.widget.AdapterView;
            import android.widget.FrameLayout;
            import android.widget.LinearLayout;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.ProgressBar;
            import android.widget.TextView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class ListFragment extends Fragment {
                static final int INTERNAL_EMPTY_ID = 16711681;
                static final int INTERNAL_LIST_CONTAINER_ID = 16711683;
                static final int INTERNAL_PROGRESS_CONTAINER_ID = 16711682;
                ListAdapter mAdapter;
                CharSequence mEmptyText;
                View mEmptyView;
                ListView mList;
                View mListContainer;
                boolean mListShown;
                View mProgressContainer;
                TextView mStandardEmptyView;
                private final Handler mHandler = new Handler();
                private final Runnable mRequestFocus = new RunnableC00181();
                private final AdapterView.OnItemClickListener mOnClickListener = new C00192();

                /* JADX INFO: renamed from: android.support.v4.app.ListFragment$1 */
/* 47 */        class RunnableC00181 implements Runnable {
/* 48 */            RunnableC00181() {
                    }

                    @Override // java.lang.Runnable
/* 50 */            public void run() {
/* 51 */                ListFragment.this.mList.focusableViewAvailable(ListFragment.this.mList);
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.app.ListFragment$2 */
/* 54 */        class C00192 implements AdapterView.OnItemClickListener {
/* 55 */            C00192() {
                    }

                    @Override // android.widget.AdapterView.OnItemClickListener
/* 57 */            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
/* 58 */                ListFragment.this.onListItemClick((ListView) parent, v, position, id);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 90 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 91 */            Context context = getActivity();
/* 93 */            FrameLayout frameLayout = new FrameLayout(context);
/* 97 */            LinearLayout pframe = new LinearLayout(context);
/* 98 */            pframe.setId(INTERNAL_PROGRESS_CONTAINER_ID);
/* 99 */            pframe.setOrientation(1);
/* 100 */           pframe.setVisibility(8);
/* 101 */           pframe.setGravity(17);
/* 103 */           ProgressBar progress = new ProgressBar(context, null, R.attr.progressBarStyleLarge);
/* 105 */           pframe.addView(progress, new FrameLayout.LayoutParams(-2, -2));
/* 108 */           frameLayout.addView(pframe, new FrameLayout.LayoutParams(-1, -1));
/* 113 */           FrameLayout lframe = new FrameLayout(context);
/* 114 */           lframe.setId(INTERNAL_LIST_CONTAINER_ID);
/* 116 */           TextView tv = new TextView(getActivity());
/* 117 */           tv.setId(INTERNAL_EMPTY_ID);
/* 118 */           tv.setGravity(17);
/* 119 */           lframe.addView(tv, new FrameLayout.LayoutParams(-1, -1));
/* 122 */           ListView lv = new ListView(getActivity());
/* 123 */           lv.setId(R.id.list);
/* 124 */           lv.setDrawSelectorOnTop(false);
/* 125 */           lframe.addView(lv, new FrameLayout.LayoutParams(-1, -1));
/* 128 */           frameLayout.addView(lframe, new FrameLayout.LayoutParams(-1, -1));
/* 133 */           frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
/* 136 */           return frameLayout;
                }

                @Override // android.support.v4.app.Fragment
/* 143 */       public void onViewCreated(View view, Bundle savedInstanceState) {
/* 144 */           super.onViewCreated(view, savedInstanceState);
/* 145 */           ensureList();
                }

                @Override // android.support.v4.app.Fragment
/* 152 */       public void onDestroyView() {
/* 153 */           this.mHandler.removeCallbacks(this.mRequestFocus);
/* 154 */           this.mList = null;
/* 155 */           this.mListShown = false;
/* 156 */           this.mListContainer = null;
                    this.mProgressContainer = null;
                    this.mEmptyView = null;
/* 157 */           this.mStandardEmptyView = null;
/* 158 */           super.onDestroyView();
                }

/* 172 */       public void onListItemClick(ListView l, View v, int position, long id) {
                }

/* 178 */       public void setListAdapter(ListAdapter adapter) {
/* 179 */           boolean hadAdapter = this.mAdapter != null;
/* 180 */           this.mAdapter = adapter;
/* 181 */           if (this.mList != null) {
/* 182 */               this.mList.setAdapter(adapter);
/* 183 */               if (!this.mListShown && !hadAdapter) {
/* 186 */                   setListShown(true, getView().getWindowToken() != null);
                        }
                    }
                }

/* 197 */       public void setSelection(int position) {
/* 198 */           ensureList();
/* 199 */           this.mList.setSelection(position);
                }

/* 205 */       public int getSelectedItemPosition() {
/* 206 */           ensureList();
/* 207 */           return this.mList.getSelectedItemPosition();
                }

/* 213 */       public long getSelectedItemId() {
/* 214 */           ensureList();
/* 215 */           return this.mList.getSelectedItemId();
                }

/* 221 */       public ListView getListView() {
/* 222 */           ensureList();
/* 223 */           return this.mList;
                }

/* 231 */       public void setEmptyText(CharSequence text) {
/* 232 */           ensureList();
/* 233 */           if (this.mStandardEmptyView == null) {
/* 234 */               throw new IllegalStateException("Can't be used with a custom content view");
                    }
/* 236 */           this.mStandardEmptyView.setText(text);
/* 237 */           if (this.mEmptyText == null) {
/* 238 */               this.mList.setEmptyView(this.mStandardEmptyView);
                    }
/* 240 */           this.mEmptyText = text;
                }

/* 257 */       public void setListShown(boolean shown) {
/* 258 */           setListShown(shown, true);
                }

/* 265 */       public void setListShownNoAnimation(boolean shown) {
/* 266 */           setListShown(shown, false);
                }

/* 279 */       private void setListShown(boolean shown, boolean animate) {
/* 280 */           ensureList();
/* 281 */           if (this.mProgressContainer == null) {
/* 282 */               throw new IllegalStateException("Can't be used with a custom content view");
                    }
/* 284 */           if (this.mListShown != shown) {
/* 287 */               this.mListShown = shown;
/* 288 */               if (shown) {
/* 289 */                   if (animate) {
/* 290 */                       this.mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
/* 292 */                       this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));
                            } else {
/* 295 */                       this.mProgressContainer.clearAnimation();
/* 296 */                       this.mListContainer.clearAnimation();
                            }
/* 298 */                   this.mProgressContainer.setVisibility(8);
/* 299 */                   this.mListContainer.setVisibility(0);
/* 313 */                   return;
                        }
/* 301 */               if (animate) {
/* 302 */                   this.mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in));
/* 304 */                   this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
                        } else {
/* 307 */                   this.mProgressContainer.clearAnimation();
/* 308 */                   this.mListContainer.clearAnimation();
                        }
/* 310 */               this.mProgressContainer.setVisibility(0);
/* 311 */               this.mListContainer.setVisibility(8);
                    }
                }

/* 318 */       public ListAdapter getListAdapter() {
/* 319 */           return this.mAdapter;
                }

/* 322 */       private void ensureList() {
/* 323 */           if (this.mList == null) {
/* 326 */               View root = getView();
/* 327 */               if (root == null) {
/* 328 */                   throw new IllegalStateException("Content view not yet created");
                        }
/* 330 */               if (root instanceof ListView) {
/* 331 */                   this.mList = (ListView) root;
                        } else {
/* 333 */                   this.mStandardEmptyView = (TextView) root.findViewById(INTERNAL_EMPTY_ID);
/* 334 */                   if (this.mStandardEmptyView == null) {
/* 335 */                       this.mEmptyView = root.findViewById(R.id.empty);
                            } else {
/* 337 */                       this.mStandardEmptyView.setVisibility(8);
                            }
/* 339 */                   this.mProgressContainer = root.findViewById(INTERNAL_PROGRESS_CONTAINER_ID);
/* 340 */                   this.mListContainer = root.findViewById(INTERNAL_LIST_CONTAINER_ID);
/* 341 */                   View rawListView = root.findViewById(R.id.list);
/* 342 */                   if (!(rawListView instanceof ListView)) {
/* 343 */                       if (rawListView == null) {
/* 344 */                           throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
                                }
/* 348 */                       throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
                            }
/* 352 */                   this.mList = (ListView) rawListView;
/* 353 */                   if (this.mEmptyView != null) {
/* 354 */                       this.mList.setEmptyView(this.mEmptyView);
                            } else if (this.mEmptyText != null) {
/* 356 */                       this.mStandardEmptyView.setText(this.mEmptyText);
/* 357 */                       this.mList.setEmptyView(this.mStandardEmptyView);
                            }
                        }
/* 360 */               this.mListShown = true;
/* 361 */               this.mList.setOnItemClickListener(this.mOnClickListener);
/* 362 */               if (this.mAdapter != null) {
/* 363 */                   ListAdapter adapter = this.mAdapter;
/* 364 */                   this.mAdapter = null;
/* 365 */                   setListAdapter(adapter);
                        } else if (this.mProgressContainer != null) {
/* 370 */                   setListShown(false, false);
                        }
/* 373 */               this.mHandler.post(this.mRequestFocus);
                    }
                }
            }
