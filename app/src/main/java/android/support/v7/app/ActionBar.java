            package android.support.v7.app;

            import android.content.Context;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.support.v4.app.FragmentManager;
            import android.support.v4.app.FragmentTransaction;
            import android.support.v7.appcompat.C0084R;
            import android.util.AttributeSet;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.SpinnerAdapter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 419 */   public abstract class ActionBar {
                public static final int DISPLAY_HOME_AS_UP = 4;
                public static final int DISPLAY_SHOW_CUSTOM = 16;
                public static final int DISPLAY_SHOW_HOME = 2;
                public static final int DISPLAY_SHOW_TITLE = 8;
                public static final int DISPLAY_USE_LOGO = 1;
                public static final int NAVIGATION_MODE_LIST = 1;
                public static final int NAVIGATION_MODE_STANDARD = 0;
                public static final int NAVIGATION_MODE_TABS = 2;

                interface Callback {
                    FragmentManager getSupportFragmentManager();
                }

                public interface OnMenuVisibilityListener {
                    void onMenuVisibilityChanged(boolean z);
                }

                public interface OnNavigationListener {
                    boolean onNavigationItemSelected(int i, long j);
                }

                public static abstract class Tab {
                    public static final int INVALID_POSITION = -1;

                    public abstract CharSequence getContentDescription();

                    public abstract View getCustomView();

                    public abstract Drawable getIcon();

                    public abstract int getPosition();

                    public abstract Object getTag();

                    public abstract CharSequence getText();

                    public abstract void select();

                    public abstract Tab setContentDescription(int i);

                    public abstract Tab setContentDescription(CharSequence charSequence);

                    public abstract Tab setCustomView(int i);

                    public abstract Tab setCustomView(View view);

                    public abstract Tab setIcon(int i);

                    public abstract Tab setIcon(Drawable drawable);

                    public abstract Tab setTabListener(TabListener tabListener);

                    public abstract Tab setTag(Object obj);

                    public abstract Tab setText(int i);

                    public abstract Tab setText(CharSequence charSequence);
                }

                public interface TabListener {
                    void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction);

                    void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction);

                    void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction);
                }

                public abstract void addOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener);

                public abstract void addTab(Tab tab);

                public abstract void addTab(Tab tab, int i);

                public abstract void addTab(Tab tab, int i, boolean z);

                public abstract void addTab(Tab tab, boolean z);

                public abstract View getCustomView();

                public abstract int getDisplayOptions();

                public abstract int getHeight();

                public abstract int getNavigationItemCount();

                public abstract int getNavigationMode();

                public abstract int getSelectedNavigationIndex();

                public abstract Tab getSelectedTab();

                public abstract CharSequence getSubtitle();

                public abstract Tab getTabAt(int i);

                public abstract int getTabCount();

                public abstract CharSequence getTitle();

                public abstract void hide();

                public abstract boolean isShowing();

                public abstract Tab newTab();

                public abstract void removeAllTabs();

                public abstract void removeOnMenuVisibilityListener(OnMenuVisibilityListener onMenuVisibilityListener);

                public abstract void removeTab(Tab tab);

                public abstract void removeTabAt(int i);

                public abstract void selectTab(Tab tab);

                public abstract void setBackgroundDrawable(Drawable drawable);

                public abstract void setCustomView(int i);

                public abstract void setCustomView(View view);

                public abstract void setCustomView(View view, LayoutParams layoutParams);

                public abstract void setDisplayHomeAsUpEnabled(boolean z);

                public abstract void setDisplayOptions(int i);

                public abstract void setDisplayOptions(int i, int i2);

                public abstract void setDisplayShowCustomEnabled(boolean z);

                public abstract void setDisplayShowHomeEnabled(boolean z);

                public abstract void setDisplayShowTitleEnabled(boolean z);

                public abstract void setDisplayUseLogoEnabled(boolean z);

                public abstract void setIcon(int i);

                public abstract void setIcon(Drawable drawable);

                public abstract void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, OnNavigationListener onNavigationListener);

                public abstract void setLogo(int i);

                public abstract void setLogo(Drawable drawable);

                public abstract void setNavigationMode(int i);

                public abstract void setSelectedNavigationItem(int i);

                public abstract void setSubtitle(int i);

                public abstract void setSubtitle(CharSequence charSequence);

                public abstract void setTitle(int i);

                public abstract void setTitle(CharSequence charSequence);

                public abstract void show();

/* 420 */       public void setStackedBackgroundDrawable(Drawable d) {
                }

/* 431 */       public void setSplitBackgroundDrawable(Drawable d) {
                }

/* 648 */       public void setHomeButtonEnabled(boolean enabled) {
                }

/* 660 */       public Context getThemedContext() {
/* 661 */           return null;
                }

/* 681 */       public void setHomeAsUpIndicator(Drawable indicator) {
                }

/* 702 */       public void setHomeAsUpIndicator(int resId) {
                }

/* 721 */       public void setHomeActionContentDescription(CharSequence description) {
                }

/* 741 */       public void setHomeActionContentDescription(int resId) {
                }

/* 1020 */      public static class LayoutParams extends ViewGroup.MarginLayoutParams {
                    public int gravity;

/* 1021 */          public LayoutParams(Context c, AttributeSet attrs) {
/* 1022 */              super(c, attrs);
/* 1019 */              this.gravity = -1;
/* 1024 */              TypedArray a = c.obtainStyledAttributes(attrs, C0084R.styleable.ActionBarLayout);
/* 1026 */              this.gravity = a.getInt(0, -1);
/* 1027 */              a.recycle();
                    }

/* 1030 */          public LayoutParams(int width, int height) {
/* 1031 */              super(width, height);
/* 1019 */              this.gravity = -1;
/* 1032 */              this.gravity = 19;
                    }

/* 1035 */          public LayoutParams(int width, int height, int gravity) {
/* 1036 */              super(width, height);
/* 1019 */              this.gravity = -1;
/* 1037 */              this.gravity = gravity;
                    }

/* 1040 */          public LayoutParams(int gravity) {
/* 1041 */              this(-2, -1, gravity);
                    }

/* 1044 */          public LayoutParams(LayoutParams source) {
/* 1045 */              super((ViewGroup.MarginLayoutParams) source);
/* 1019 */              this.gravity = -1;
/* 1047 */              this.gravity = source.gravity;
                    }

/* 1050 */          public LayoutParams(ViewGroup.LayoutParams source) {
/* 1051 */              super(source);
/* 1019 */              this.gravity = -1;
                    }
                }
            }
