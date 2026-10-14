            package co.vine.android.widget.tabs;

            import android.R;
            import android.content.Context;
            import android.content.res.TypedArray;
            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.support.annotation.NonNull;
            import android.support.v4.app.Fragment;
            import android.support.v4.app.FragmentManager;
            import android.support.v4.app.FragmentTransaction;
            import android.text.TextUtils;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.FrameLayout;
            import android.widget.LinearLayout;
            import android.widget.TabHost;
            import android.widget.TabWidget;
            import co.vine.android.util.CrashUtil;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 66 */    public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
                private boolean mAttached;
                private int mContainerId;
                private Context mContext;
                private FragmentManager mFragmentManager;
                private TabInfo mLastTab;
                private TabHost.OnTabChangeListener mOnTabChangeListener;
                private FrameLayout mRealTabContent;
                private final ArrayList<TabInfo> mTabs;

/* 67 */        static class TabInfo extends co.vine.android.widget.tabs.TabInfo {
                    private Fragment fragment;

/* 68 */            static /* synthetic */ Fragment access$100(TabInfo x0) {
/* 69 */                return x0.fragment;
                    }

/* 68 */            static /* synthetic */ Fragment access$102(TabInfo x0, Fragment x1) {
/* 69 */                x0.fragment = x1;
                        return x1;
                    }

/* 73 */            TabInfo(String tag, Class<?> clss, Bundle args) {
/* 74 */                super(clss, args, tag);
                    }
                }

/* 76 */        static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C05791();
                    String curTab;

/* 77 */            /* synthetic */ SavedState(Parcel x0, Object x1) {
/* 78 */                this(x0);
                    }

/* 81 */            SavedState(Parcelable superState) {
/* 82 */                super(superState);
                    }

/* 85 */            private SavedState(Parcel in) {
/* 86 */                super(in);
/* 87 */                this.curTab = in.readString();
                    }

                    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
/* 91 */            public void writeToParcel(@NonNull Parcel out, int flags) {
/* 92 */                super.writeToParcel(out, flags);
/* 93 */                out.writeString(this.curTab);
                    }

/* 97 */            public String toString() {
/* 98 */                return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.curTab + "}";
                    }

                    /* JADX INFO: renamed from: co.vine.android.widget.tabs.FragmentTabHost$SavedState$1 */
/* 102 */           static class C05791 implements Parcelable.Creator<SavedState> {
/* 103 */               C05791() {
                        }



                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 105 */               public SavedState createFromParcel(Parcel in) {
/* 106 */                   return new SavedState(in, null);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 109 */               public SavedState[] newArray(int size) {
/* 110 */                   return new SavedState[size];
                        }
                    }
                }

/* 117 */       public FragmentTabHost(Context context) {
/* 118 */           super(context, null);
/* 60 */            this.mTabs = new ArrayList<>();
/* 119 */           initFragmentTabHost(context, null);
                }

/* 122 */       public FragmentTabHost(Context context, AttributeSet attrs) {
/* 123 */           super(context, attrs);
/* 60 */            this.mTabs = new ArrayList<>();
/* 124 */           initFragmentTabHost(context, attrs);
                }

/* 127 */       private void initFragmentTabHost(Context context, AttributeSet attrs) {
/* 128 */           TypedArray a = context.obtainStyledAttributes(attrs, new int[]{R.attr.inflatedId}, 0, 0);
/* 130 */           if (a != null) {
/* 131 */               this.mContainerId = a.getResourceId(0, 0);
/* 132 */               a.recycle();
                    }
/* 135 */           super.setOnTabChangedListener(this);
/* 139 */           if (findViewById(R.id.tabs) == null) {
/* 140 */               LinearLayout ll = new LinearLayout(context);
/* 141 */               ll.setOrientation(1);
/* 142 */               addView(ll, new FrameLayout.LayoutParams(-1, -1));
/* 146 */               TabWidget tw = new TabWidget(context);
/* 147 */               tw.setId(R.id.tabs);
/* 148 */               tw.setOrientation(0);
/* 149 */               ll.addView(tw, new LinearLayout.LayoutParams(-1, -2, 0.0f));
/* 153 */               FrameLayout fl = new FrameLayout(context);
/* 154 */               fl.setId(R.id.tabcontent);
/* 155 */               ll.addView(fl, new LinearLayout.LayoutParams(0, 0, 0.0f));
/* 157 */               FrameLayout fl2 = new FrameLayout(context);
                        this.mRealTabContent = fl2;
/* 158 */               this.mRealTabContent.setId(this.mContainerId);
/* 159 */               ll.addView(fl2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
                    }
                }

                @Override // android.widget.TabHost
                @Deprecated
/* 171 */       public void setup() {
/* 172 */           throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
                }

/* 176 */       public void setup(Context context, FragmentManager manager) {
/* 177 */           super.setup();
/* 178 */           this.mContext = context;
/* 179 */           this.mFragmentManager = manager;
/* 180 */           ensureContent();
                }

/* 183 */       public void setup(Context context, FragmentManager manager, int containerId) {
/* 184 */           super.setup();
/* 185 */           this.mContext = context;
/* 186 */           this.mFragmentManager = manager;
/* 187 */           this.mContainerId = containerId;
/* 188 */           ensureContent();
/* 189 */           this.mRealTabContent.setId(containerId);
/* 193 */           if (getId() == -1) {
/* 194 */               setId(R.id.tabhost);
                    }
                }

/* 198 */       private void ensureContent() {
/* 199 */           if (this.mRealTabContent == null) {
/* 200 */               this.mRealTabContent = (FrameLayout) findViewById(this.mContainerId);
/* 201 */               if (this.mRealTabContent == null) {
/* 202 */                   throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
                        }
                    }
                }

                @Override // android.widget.TabHost
/* 209 */       public void setOnTabChangedListener(TabHost.OnTabChangeListener l) {
/* 210 */           this.mOnTabChangeListener = l;
                }

/* 213 */       public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
/* 214 */           tabSpec.setContent(new DummyTabFactory(this.mContext));
/* 215 */           String tag = tabSpec.getTag();
/* 217 */           TabInfo info = new TabInfo(tag, clss, args);
/* 219 */           if (this.mAttached) {
/* 223 */               TabInfo.access$102(info, this.mFragmentManager.findFragmentByTag(tag));
/* 224 */               if (TabInfo.access$100(info) != null && !TabInfo.access$100(info).isDetached()) {
/* 225 */                   FragmentTransaction ft = this.mFragmentManager.beginTransaction();
/* 226 */                   ft.detach(TabInfo.access$100(info));
/* 227 */                   ft.commit();
                        }
                    }
/* 231 */           this.mTabs.add(info);
/* 232 */           addTab(tabSpec);
                }

                @Override // android.view.ViewGroup, android.view.View
/* 236 */       protected void onAttachedToWindow() {
/* 237 */           super.onAttachedToWindow();
/* 239 */           String currentTab = getCurrentTabTag();
/* 243 */           FragmentTransaction ft = null;
                    for (TabInfo tab : this.mTabs) {
/* 245 */               TabInfo.access$102(tab, this.mFragmentManager.findFragmentByTag(tab.mTag));
/* 246 */               if (TabInfo.access$100(tab) != null && !TabInfo.access$100(tab).isDetached()) {
/* 247 */                   if (tab.mTag.equals(currentTab)) {
/* 251 */                       this.mLastTab = tab;
                            } else {
/* 255 */                       if (ft == null) {
/* 256 */                           ft = this.mFragmentManager.beginTransaction();
                                }
/* 258 */                       ft.detach(TabInfo.access$100(tab));
                            }
                        }
                    }
/* 265 */           this.mAttached = true;
/* 266 */           FragmentTransaction ft2 = doTabChanged(currentTab, ft);
/* 267 */           if (ft2 != null) {
/* 268 */               ft2.commitAllowingStateLoss();
/* 269 */               this.mFragmentManager.executePendingTransactions();
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
/* 274 */       protected void onDetachedFromWindow() {
/* 275 */           super.onDetachedFromWindow();
/* 276 */           this.mAttached = false;
                }

                @Override // android.view.View
/* 280 */       protected Parcelable onSaveInstanceState() {
/* 281 */           Parcelable superState = super.onSaveInstanceState();
/* 282 */           SavedState ss = new SavedState(superState);
/* 283 */           ss.curTab = getCurrentTabTag();
/* 284 */           return ss;
                }

                @Override // android.view.View
/* 288 */       protected void onRestoreInstanceState(Parcelable state) {
/* 289 */           SavedState ss = (SavedState) state;
/* 290 */           super.onRestoreInstanceState(ss.getSuperState());
/* 291 */           setCurrentTabByTag(ss.curTab);
                }

                @Override // android.widget.TabHost.OnTabChangeListener
/* 295 */       public void onTabChanged(String tabId) {
                    FragmentTransaction ft;
/* 296 */           if (this.mAttached && (ft = doTabChanged(tabId, null)) != null) {
/* 299 */               ft.commitAllowingStateLoss();
                    }
/* 302 */           if (this.mOnTabChangeListener != null) {
/* 303 */               this.mOnTabChangeListener.onTabChanged(tabId);
                    }
                }

/* 311 */       private FragmentTransaction doTabChanged(String tabId, FragmentTransaction ft) {
/* 312 */           if (TextUtils.isEmpty(tabId)) {
/* 313 */               tabId = "users";
                    }
/* 315 */           TabInfo newTab = null;
                    for (TabInfo tab : this.mTabs) {
/* 317 */               if (tab.mTag.equals(tabId)) {
/* 318 */                   newTab = tab;
                        }
                    }
/* 321 */           if (newTab == null) {
/* 322 */               CrashUtil.logException(new IllegalStateException("No tab known for tag " + tabId));
                        return ft;
                    }
/* 325 */           if (this.mLastTab != newTab) {
/* 326 */               if (ft == null) {
/* 327 */                   ft = this.mFragmentManager.beginTransaction();
                        }
/* 329 */               if (this.mLastTab != null && TabInfo.access$100(this.mLastTab) != null) {
/* 331 */                   ft.detach(TabInfo.access$100(this.mLastTab));
                        }
/* 334 */               if (TabInfo.access$100(newTab) == null) {
/* 335 */                   TabInfo.access$102(newTab, Fragment.instantiate(this.mContext, newTab.clss.getName(), newTab.bundle));
/* 337 */                   ft.add(this.mContainerId, TabInfo.access$100(newTab), newTab.mTag);
                        } else {
/* 339 */                   ft.attach(TabInfo.access$100(newTab));
                        }
/* 342 */               this.mLastTab = newTab;
                    }
                    return ft;
                }
            }
