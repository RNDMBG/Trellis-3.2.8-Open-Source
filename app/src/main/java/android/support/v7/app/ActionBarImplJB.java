            package android.support.v7.app;

            import android.app.Activity;
            import android.content.Context;
            import android.graphics.drawable.Drawable;
            import android.view.View;
            import android.widget.SpinnerAdapter;
            
            
    public class ActionBarImplJB extends ActionBarImplICS {
                @Override 
        public   void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener x0) {
            super.addOnMenuVisibilityListener(x0);
                }

                @Override 
        public   void addTab(ActionBar.Tab x0) {
            super.addTab(x0);
                }

                @Override 
        public   void addTab(ActionBar.Tab x0, int x1) {
            super.addTab(x0, x1);
                }

                @Override 
        public   void addTab(ActionBar.Tab x0, int x1, boolean x2) {
            super.addTab(x0, x1, x2);
                }

                @Override 
        public   void addTab(ActionBar.Tab x0, boolean x1) {
            super.addTab(x0, x1);
                }

                @Override 
        public   View getCustomView() {
            return super.getCustomView();
                }

                @Override 
        public   int getDisplayOptions() {
            return super.getDisplayOptions();
                }

                @Override 
        public   int getHeight() {
            return super.getHeight();
                }

                @Override 
        public   int getNavigationItemCount() {
            return super.getNavigationItemCount();
                }

                @Override 
        public   int getNavigationMode() {
            return super.getNavigationMode();
                }

                @Override 
        public   int getSelectedNavigationIndex() {
            return super.getSelectedNavigationIndex();
                }

                @Override 
        public   ActionBar.Tab getSelectedTab() {
            return super.getSelectedTab();
                }

                @Override 
        public   CharSequence getSubtitle() {
            return super.getSubtitle();
                }

                @Override 
        public   ActionBar.Tab getTabAt(int x0) {
            return super.getTabAt(x0);
                }

                @Override 
        public   int getTabCount() {
            return super.getTabCount();
                }

                @Override 
        public   Context getThemedContext() {
            return super.getThemedContext();
                }

                @Override 
        public   CharSequence getTitle() {
            return super.getTitle();
                }

                @Override 
        public   void hide() {
            super.hide();
                }

                @Override 
        public   boolean isShowing() {
            return super.isShowing();
                }

                @Override 
        public   ActionBar.Tab newTab() {
            return super.newTab();
                }

                @Override 
        public   void removeAllTabs() {
            super.removeAllTabs();
                }

                @Override 
        public   void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener x0) {
            super.removeOnMenuVisibilityListener(x0);
                }

                @Override 
        public   void removeTab(ActionBar.Tab x0) {
            super.removeTab(x0);
                }

                @Override 
        public   void removeTabAt(int x0) {
            super.removeTabAt(x0);
                }

                @Override 
        public   void selectTab(ActionBar.Tab x0) {
            super.selectTab(x0);
                }

                @Override 
        public   void setBackgroundDrawable(Drawable x0) {
            super.setBackgroundDrawable(x0);
                }

                @Override 
        public   void setCustomView(int x0) {
            super.setCustomView(x0);
                }

                @Override 
        public   void setCustomView(View x0) {
            super.setCustomView(x0);
                }

                @Override 
        public   void setCustomView(View x0, ActionBar.LayoutParams x1) {
            super.setCustomView(x0, x1);
                }

                @Override 
        public   void setDisplayHomeAsUpEnabled(boolean x0) {
            super.setDisplayHomeAsUpEnabled(x0);
                }

                @Override 
        public   void setDisplayOptions(int x0) {
            super.setDisplayOptions(x0);
                }

                @Override 
        public   void setDisplayOptions(int x0, int x1) {
            super.setDisplayOptions(x0, x1);
                }

                @Override 
        public   void setDisplayShowCustomEnabled(boolean x0) {
            super.setDisplayShowCustomEnabled(x0);
                }

                @Override 
        public   void setDisplayShowHomeEnabled(boolean x0) {
            super.setDisplayShowHomeEnabled(x0);
                }

                @Override 
        public   void setDisplayShowTitleEnabled(boolean x0) {
            super.setDisplayShowTitleEnabled(x0);
                }

                @Override 
        public   void setDisplayUseLogoEnabled(boolean x0) {
            super.setDisplayUseLogoEnabled(x0);
                }

                @Override 
        public   void setHomeAsUpIndicator(int x0) {
            super.setHomeAsUpIndicator(x0);
                }

                @Override 
        public   void setHomeAsUpIndicator(Drawable x0) {
            super.setHomeAsUpIndicator(x0);
                }

                @Override 
        public   void setHomeButtonEnabled(boolean x0) {
            super.setHomeButtonEnabled(x0);
                }

                @Override 
        public   void setIcon(int x0) {
            super.setIcon(x0);
                }

                @Override 
        public   void setIcon(Drawable x0) {
            super.setIcon(x0);
                }

                @Override 
        public   void setListNavigationCallbacks(SpinnerAdapter x0, ActionBar.OnNavigationListener x1) {
            super.setListNavigationCallbacks(x0, x1);
                }

                @Override 
        public   void setLogo(int x0) {
            super.setLogo(x0);
                }

                @Override 
        public   void setLogo(Drawable x0) {
            super.setLogo(x0);
                }

                @Override 
        public   void setNavigationMode(int x0) {
            super.setNavigationMode(x0);
                }

                @Override 
        public   void setSelectedNavigationItem(int x0) {
            super.setSelectedNavigationItem(x0);
                }

                @Override 
        public   void setSplitBackgroundDrawable(Drawable x0) {
            super.setSplitBackgroundDrawable(x0);
                }

                @Override 
        public   void setStackedBackgroundDrawable(Drawable x0) {
            super.setStackedBackgroundDrawable(x0);
                }

                @Override 
        public   void setSubtitle(int x0) {
            super.setSubtitle(x0);
                }

                @Override 
        public   void setSubtitle(CharSequence x0) {
            super.setSubtitle(x0);
                }

                @Override 
        public   void setTitle(int x0) {
            super.setTitle(x0);
                }

                @Override 
        public   void setTitle(CharSequence x0) {
            super.setTitle(x0);
                }

                @Override 
        public   void show() {
            super.show();
                }

        public ActionBarImplJB(Activity activity, ActionBar.Callback callback) {
            super(activity, callback, false);
                }
            }
