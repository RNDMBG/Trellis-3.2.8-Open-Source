            package android.support.v4.app;

            import android.R;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.FrameLayout;
            import android.widget.LinearLayout;
            import android.widget.TabHost;
            import android.widget.TabWidget;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 58 */    public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
                private boolean mAttached;
                private int mContainerId;
                private Context mContext;
                private FragmentManager mFragmentManager;
                private TabInfo mLastTab;
                private TabHost.OnTabChangeListener mOnTabChangeListener;
                private FrameLayout mRealTabContent;
                private final ArrayList<TabInfo> mTabs;

/* 59 */        static final class TabInfo {
                    private final Bundle args;
                    private final Class<?> clss;
                    private Fragment fragment;
                    private final String tag;

/* 60 */            static /* synthetic */ Fragment access$100(TabInfo x0) {
/* 61 */                return x0.fragment;
                    }

/* 60 */            static /* synthetic */ Fragment access$102(TabInfo x0, Fragment x1) {
/* 61 */                x0.fragment = x1;
                        return x1;
                    }

/* 60 */            static /* synthetic */ String access$200(TabInfo x0) {
/* 61 */                return x0.tag;
                    }

/* 60 */            static /* synthetic */ Class access$300(TabInfo x0) {
/* 61 */                return x0.clss;
                    }

/* 60 */            static /* synthetic */ Bundle access$400(TabInfo x0) {
/* 61 */                return x0.args;
                    }

/* 66 */            TabInfo(String _tag, Class<?> _class, Bundle _args) {
/* 68 */                this.tag = _tag;
/* 69 */                this.clss = _class;
/* 70 */                this.args = _args;
                    }
                }

/* 75 */        static class DummyTabFactory implements TabHost.TabContentFactory {
                    private final Context mContext;

/* 76 */            public DummyTabFactory(Context context) {
/* 78 */                this.mContext = context;
                    }

                    @Override // android.widget.TabHost.TabContentFactory
/* 82 */            public View createTabContent(String tag) {
/* 83 */                View v = new View(this.mContext);
/* 84 */                v.setMinimumWidth(0);
/* 85 */                v.setMinimumHeight(0);
/* 86 */                return v;
                    }
                }

/* 88 */        static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C00171();
                    String curTab;

/* 89 */            /* synthetic */ SavedState(Parcel x0, C00161 x1) {
/* 90 */                this(x0);
                    }

/* 93 */            SavedState(Parcelable superState) {
/* 94 */                super(superState);
                    }

/* 97 */            private SavedState(Parcel in) {
/* 98 */                super(in);
/* 99 */                this.curTab = in.readString();
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 103 */           public void writeToParcel(Parcel out, int flags) {
/* 104 */               super.writeToParcel(out, flags);
/* 105 */               out.writeString(this.curTab);
                    }

/* 109 */           public String toString() {
/* 110 */               return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.curTab + "}";
                    }

                    /* JADX INFO: renamed from: android.support.v4.app.FragmentTabHost$SavedState$1 */
/* 114 */           static class C00171 implements Parcelable.Creator<SavedState> {
/* 115 */               C00171() {
                        }

                        @Override // android.os.Parcelable.Creator
/* 115 */               public /* bridge */ /* synthetic */ SavedState createFromParcel(Parcel x0) {
/* 116 */                   return createFromParcel(x0);
                        }

                        @Override // android.os.Parcelable.Creator
/* 115 */               public /* bridge */ /* synthetic */ SavedState[] newArray(int x0) {
/* 116 */                   return newArray(x0);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 117 */               public SavedState createFromParcel(Parcel in) {
/* 118 */                   return new SavedState(in, null);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 121 */               public SavedState[] newArray(int size) {
/* 122 */                   return new SavedState[size];
                        }
                    }
                }

/* 129 */       public FragmentTabHost(Context context) {
/* 130 */           super(context, null);
/* 52 */            this.mTabs = new ArrayList<>();
/* 131 */           initFragmentTabHost(context, null);
                }

/* 134 */       public FragmentTabHost(Context context, AttributeSet attrs) {
/* 135 */           super(context, attrs);
/* 52 */            this.mTabs = new ArrayList<>();
/* 136 */           initFragmentTabHost(context, attrs);
                }

/* 139 */       private void initFragmentTabHost(Context context, AttributeSet attrs) {
/* 140 */           TypedArray a = context.obtainStyledAttributes(attrs, new int[]{R.attr.inflatedId}, 0, 0);
/* 142 */           this.mContainerId = a.getResourceId(0, 0);
/* 143 */           a.recycle();
/* 145 */           super.setOnTabChangedListener(this);
                }

/* 150 */       private void ensureHierarchy(Context context) {
/* 151 */           if (findViewById(R.id.tabs) == null) {
/* 152 */               LinearLayout ll = new LinearLayout(context);
/* 153 */               ll.setOrientation(1);
/* 154 */               addView(ll, new FrameLayout.LayoutParams(-1, -1));
/* 158 */               TabWidget tw = new TabWidget(context);
/* 159 */               tw.setId(R.id.tabs);
/* 160 */               tw.setOrientation(0);
/* 161 */               ll.addView(tw, new LinearLayout.LayoutParams(-1, -2, 0.0f));
/* 165 */               FrameLayout fl = new FrameLayout(context);
/* 166 */               fl.setId(R.id.tabcontent);
/* 167 */               ll.addView(fl, new LinearLayout.LayoutParams(0, 0, 0.0f));
/* 169 */               FrameLayout fl2 = new FrameLayout(context);
                        this.mRealTabContent = fl2;
/* 170 */               this.mRealTabContent.setId(this.mContainerId);
/* 171 */               ll.addView(fl2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
                    }
                }

                @Override // android.widget.TabHost
                @Deprecated
/* 182 */       public void setup() {
/* 183 */           throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
                }

/* 187 */       public void setup(Context context, FragmentManager manager) {
/* 188 */           ensureHierarchy(context);
/* 189 */           super.setup();
/* 190 */           this.mContext = context;
/* 191 */           this.mFragmentManager = manager;
/* 192 */           ensureContent();
                }

/* 195 */       public void setup(Context context, FragmentManager manager, int containerId) {
/* 196 */           ensureHierarchy(context);
/* 197 */           super.setup();
/* 198 */           this.mContext = context;
/* 199 */           this.mFragmentManager = manager;
/* 200 */           this.mContainerId = containerId;
/* 201 */           ensureContent();
/* 202 */           this.mRealTabContent.setId(containerId);
/* 206 */           if (getId() == -1) {
/* 207 */               setId(R.id.tabhost);
                    }
                }

/* 211 */       private void ensureContent() {
/* 212 */           if (this.mRealTabContent == null) {
/* 213 */               this.mRealTabContent = (FrameLayout) findViewById(this.mContainerId);
/* 214 */               if (this.mRealTabContent == null) {
/* 215 */                   throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
                        }
                    }
                }

                @Override // android.widget.TabHost
/* 222 */       public void setOnTabChangedListener(TabHost.OnTabChangeListener l) {
/* 223 */           this.mOnTabChangeListener = l;
                }

/* 226 */       public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
/* 227 */           tabSpec.setContent(new DummyTabFactory(this.mContext));
/* 228 */           String tag = tabSpec.getTag();
/* 230 */           TabInfo info = new TabInfo(tag, clss, args);
/* 232 */           if (this.mAttached) {
/* 236 */               TabInfo.access$102(info, this.mFragmentManager.findFragmentByTag(tag));
/* 237 */               if (TabInfo.access$100(info) != null && !TabInfo.access$100(info).isDetached()) {
/* 238 */                   FragmentTransaction ft = this.mFragmentManager.beginTransaction();
/* 239 */                   ft.detach(TabInfo.access$100(info));
/* 240 */                   ft.commit();
                        }
                    }
/* 244 */           this.mTabs.add(info);
/* 245 */           addTab(tabSpec);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 249 */       protected void onAttachedToWindow() {
/* 250 */           super.onAttachedToWindow();
/* 252 */           String currentTab = getCurrentTabTag();
/* 256 */           FragmentTransaction ft = null;
                    for (int i = 0; i < this.mTabs.size(); i++) {
/* 258 */               TabInfo tab = this.mTabs.get(i);
/* 259 */               TabInfo.access$102(tab, this.mFragmentManager.findFragmentByTag(TabInfo.access$200(tab)));
/* 260 */               if (TabInfo.access$100(tab) != null && !TabInfo.access$100(tab).isDetached()) {
/* 261 */                   if (TabInfo.access$200(tab).equals(currentTab)) {
/* 265 */                       this.mLastTab = tab;
                            } else {
/* 269 */                       if (ft == null) {
/* 270 */                           ft = this.mFragmentManager.beginTransaction();
                                }
/* 272 */                       ft.detach(TabInfo.access$100(tab));
                            }
                        }
                    }
/* 279 */           this.mAttached = true;
/* 280 */           FragmentTransaction ft2 = doTabChanged(currentTab, ft);
/* 281 */           if (ft2 != null) {
/* 282 */               ft2.commit();
/* 283 */               this.mFragmentManager.executePendingTransactions();
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 288 */       protected void onDetachedFromWindow() {
/* 289 */           super.onDetachedFromWindow();
/* 290 */           this.mAttached = false;
                }

                @Override // android.view.View
/* 294 */       protected Parcelable onSaveInstanceState() {
/* 295 */           Parcelable superState = super.onSaveInstanceState();
/* 296 */           SavedState ss = new SavedState(superState);
/* 297 */           ss.curTab = getCurrentTabTag();
/* 298 */           return ss;
                }

                @Override // android.view.View
/* 302 */       protected void onRestoreInstanceState(Parcelable state) {
/* 303 */           SavedState ss = (SavedState) state;
/* 304 */           super.onRestoreInstanceState(ss.getSuperState());
/* 305 */           setCurrentTabByTag(ss.curTab);
                }

                @Override // android.widget.TabHost.OnTabChangeListener
/* 309 */       public void onTabChanged(String tabId) {
                    FragmentTransaction ft;
/* 310 */           if (this.mAttached && (ft = doTabChanged(tabId, null)) != null) {
/* 313 */               ft.commit();
                    }
/* 316 */           if (this.mOnTabChangeListener != null) {
/* 317 */               this.mOnTabChangeListener.onTabChanged(tabId);
                    }
                }

/* 321 */       private FragmentTransaction doTabChanged(String tabId, FragmentTransaction ft) {
/* 322 */           TabInfo newTab = null;
                    for (int i = 0; i < this.mTabs.size(); i++) {
/* 324 */               TabInfo tab = this.mTabs.get(i);
/* 325 */               if (TabInfo.access$200(tab).equals(tabId)) {
/* 326 */                   newTab = tab;
                        }
                    }
/* 329 */           if (newTab == null) {
/* 330 */               throw new IllegalStateException("No tab known for tag " + tabId);
                    }
/* 332 */           if (this.mLastTab != newTab) {
/* 333 */               if (ft == null) {
/* 334 */                   ft = this.mFragmentManager.beginTransaction();
                        }
/* 336 */               if (this.mLastTab != null && TabInfo.access$100(this.mLastTab) != null) {
/* 338 */                   ft.detach(TabInfo.access$100(this.mLastTab));
                        }
/* 341 */               if (newTab != null) {
/* 342 */                   if (TabInfo.access$100(newTab) == null) {
/* 343 */                       TabInfo.access$102(newTab, Fragment.instantiate(this.mContext, TabInfo.access$300(newTab).getName(), TabInfo.access$400(newTab)));
/* 345 */                       ft.add(this.mContainerId, TabInfo.access$100(newTab), TabInfo.access$200(newTab));
                            } else {
/* 347 */                       ft.attach(TabInfo.access$100(newTab));
                            }
                        }
/* 351 */               this.mLastTab = newTab;
                    }
/* 353 */           return ft;
                }
            }
