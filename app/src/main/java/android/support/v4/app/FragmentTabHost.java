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
            
            
    public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
                private boolean mAttached;
                private int mContainerId;
                private Context mContext;
                private FragmentManager mFragmentManager;
                private TabInfo mLastTab;
                private TabHost.OnTabChangeListener mOnTabChangeListener;
                private FrameLayout mRealTabContent;
                private final ArrayList<TabInfo> mTabs;

        static final class TabInfo {
                    private final Bundle args;
                    private final Class<?> clss;
                    private Fragment fragment;
                    private final String tag;

            static  Fragment access$100(TabInfo x0) {
                return x0.fragment;
                    }

            static  Fragment access$102(TabInfo x0, Fragment x1) {
                x0.fragment = x1;
                        return x1;
                    }

            static  String access$200(TabInfo x0) {
                return x0.tag;
                    }

            static  Class access$300(TabInfo x0) {
                return x0.clss;
                    }

            static  Bundle access$400(TabInfo x0) {
                return x0.args;
                    }

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                this.tag = _tag;
                this.clss = _class;
                this.args = _args;
                    }
                }

        static class DummyTabFactory implements TabHost.TabContentFactory {
                    private final Context mContext;

            public DummyTabFactory(Context context) {
                this.mContext = context;
                    }

                    @Override 
            public View createTabContent(String tag) {
                View v = new View(this.mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
                    }
                }

        static class SavedState extends View.BaseSavedState {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C00171();
                    String curTab;

             SavedState(Parcel x0, C00161 x1) {
                this(x0);
                    }

            SavedState(Parcelable superState) {
                super(superState);
                    }

            private SavedState(Parcel in) {
                super(in);
                this.curTab = in.readString();
                    }

                    @Override 
           public void writeToParcel(Parcel out, int flags) {
               super.writeToParcel(out, flags);
               out.writeString(this.curTab);
                    }

           public String toString() {
               return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.curTab + "}";
                    }

                    
           static class C00171 implements Parcelable.Creator<SavedState> {
               C00171() {
                        }

                        @Override 
               public   SavedState createFromParcel(Parcel x0) {
                   return createFromParcel(x0);
                        }

                        @Override 
               public   SavedState[] newArray(int x0) {
                   return newArray(x0);
                        }

                        
                        @Override 
               public SavedState createFromParcel(Parcel in) {
                   return new SavedState(in, null);
                        }

                        
                        @Override 
               public SavedState[] newArray(int size) {
                   return new SavedState[size];
                        }
                    }
                }

       public FragmentTabHost(Context context) {
           super(context, null);
            this.mTabs = new ArrayList<>();
           initFragmentTabHost(context, null);
                }

       public FragmentTabHost(Context context, AttributeSet attrs) {
           super(context, attrs);
            this.mTabs = new ArrayList<>();
           initFragmentTabHost(context, attrs);
                }

       private void initFragmentTabHost(Context context, AttributeSet attrs) {
           TypedArray a = context.obtainStyledAttributes(attrs, new int[]{R.attr.inflatedId}, 0, 0);
           this.mContainerId = a.getResourceId(0, 0);
           a.recycle();
           super.setOnTabChangedListener(this);
                }

       private void ensureHierarchy(Context context) {
           if (findViewById(R.id.tabs) == null) {
               LinearLayout ll = new LinearLayout(context);
               ll.setOrientation(1);
               addView(ll, new FrameLayout.LayoutParams(-1, -1));
               TabWidget tw = new TabWidget(context);
               tw.setId(R.id.tabs);
               tw.setOrientation(0);
               ll.addView(tw, new LinearLayout.LayoutParams(-1, -2, 0.0f));
               FrameLayout fl = new FrameLayout(context);
               fl.setId(R.id.tabcontent);
               ll.addView(fl, new LinearLayout.LayoutParams(0, 0, 0.0f));
               FrameLayout fl2 = new FrameLayout(context);
                        this.mRealTabContent = fl2;
               this.mRealTabContent.setId(this.mContainerId);
               ll.addView(fl2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
                    }
                }

                @Override 
                @Deprecated
       public void setup() {
           throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
                }

       public void setup(Context context, FragmentManager manager) {
           ensureHierarchy(context);
           super.setup();
           this.mContext = context;
           this.mFragmentManager = manager;
           ensureContent();
                }

       public void setup(Context context, FragmentManager manager, int containerId) {
           ensureHierarchy(context);
           super.setup();
           this.mContext = context;
           this.mFragmentManager = manager;
           this.mContainerId = containerId;
           ensureContent();
           this.mRealTabContent.setId(containerId);
           if (getId() == -1) {
               setId(R.id.tabhost);
                    }
                }

       private void ensureContent() {
           if (this.mRealTabContent == null) {
               this.mRealTabContent = (FrameLayout) findViewById(this.mContainerId);
               if (this.mRealTabContent == null) {
                   throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
                        }
                    }
                }

                @Override 
       public void setOnTabChangedListener(TabHost.OnTabChangeListener l) {
           this.mOnTabChangeListener = l;
                }

       public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
           tabSpec.setContent(new DummyTabFactory(this.mContext));
           String tag = tabSpec.getTag();
           TabInfo info = new TabInfo(tag, clss, args);
           if (this.mAttached) {
               TabInfo.access$102(info, this.mFragmentManager.findFragmentByTag(tag));
               if (TabInfo.access$100(info) != null && !TabInfo.access$100(info).isDetached()) {
                   FragmentTransaction ft = this.mFragmentManager.beginTransaction();
                   ft.detach(TabInfo.access$100(info));
                   ft.commit();
                        }
                    }
           this.mTabs.add(info);
           addTab(tabSpec);
                }

                @Override 
       protected void onAttachedToWindow() {
           super.onAttachedToWindow();
           String currentTab = getCurrentTabTag();
           FragmentTransaction ft = null;
                    for (int i = 0; i < this.mTabs.size(); i++) {
               TabInfo tab = this.mTabs.get(i);
               TabInfo.access$102(tab, this.mFragmentManager.findFragmentByTag(TabInfo.access$200(tab)));
               if (TabInfo.access$100(tab) != null && !TabInfo.access$100(tab).isDetached()) {
                   if (TabInfo.access$200(tab).equals(currentTab)) {
                       this.mLastTab = tab;
                            } else {
                       if (ft == null) {
                           ft = this.mFragmentManager.beginTransaction();
                                }
                       ft.detach(TabInfo.access$100(tab));
                            }
                        }
                    }
           this.mAttached = true;
           FragmentTransaction ft2 = doTabChanged(currentTab, ft);
           if (ft2 != null) {
               ft2.commit();
               this.mFragmentManager.executePendingTransactions();
                    }
                }

                @Override 
       protected void onDetachedFromWindow() {
           super.onDetachedFromWindow();
           this.mAttached = false;
                }

                @Override 
       protected Parcelable onSaveInstanceState() {
           Parcelable superState = super.onSaveInstanceState();
           SavedState ss = new SavedState(superState);
           ss.curTab = getCurrentTabTag();
           return ss;
                }

                @Override 
       protected void onRestoreInstanceState(Parcelable state) {
           SavedState ss = (SavedState) state;
           super.onRestoreInstanceState(ss.getSuperState());
           setCurrentTabByTag(ss.curTab);
                }

                @Override 
       public void onTabChanged(String tabId) {
                    FragmentTransaction ft;
           if (this.mAttached && (ft = doTabChanged(tabId, null)) != null) {
               ft.commit();
                    }
           if (this.mOnTabChangeListener != null) {
               this.mOnTabChangeListener.onTabChanged(tabId);
                    }
                }

       private FragmentTransaction doTabChanged(String tabId, FragmentTransaction ft) {
           TabInfo newTab = null;
                    for (int i = 0; i < this.mTabs.size(); i++) {
               TabInfo tab = this.mTabs.get(i);
               if (TabInfo.access$200(tab).equals(tabId)) {
                   newTab = tab;
                        }
                    }
           if (newTab == null) {
               throw new IllegalStateException("No tab known for tag " + tabId);
                    }
           if (this.mLastTab != newTab) {
               if (ft == null) {
                   ft = this.mFragmentManager.beginTransaction();
                        }
               if (this.mLastTab != null && TabInfo.access$100(this.mLastTab) != null) {
                   ft.detach(TabInfo.access$100(this.mLastTab));
                        }
               if (newTab != null) {
                   if (TabInfo.access$100(newTab) == null) {
                       TabInfo.access$102(newTab, Fragment.instantiate(this.mContext, TabInfo.access$300(newTab).getName(), TabInfo.access$400(newTab)));
                       ft.add(this.mContainerId, TabInfo.access$100(newTab), TabInfo.access$200(newTab));
                            } else {
                       ft.attach(TabInfo.access$100(newTab));
                            }
                        }
               this.mLastTab = newTab;
                    }
           return ft;
                }
            }
