            package android.support.v7.app;

            import android.R;
            import android.content.Context;
            import android.content.res.Configuration;
            import android.os.Bundle;
            import android.support.v7.internal.view.ActionModeWrapper;
            import android.support.v7.internal.view.menu.MenuWrapperFactory;
            import android.support.v7.view.ActionMode;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.Window;
            import android.view.WindowManager;
            import android.view.accessibility.AccessibilityEvent;
            
            
    class ActionBarActivityDelegateICS extends ActionBarActivityDelegate {
                Menu mMenu;

        ActionBarActivityDelegateICS(ActionBarActivity activity) {
            super(activity);
                }

                @Override 
        public ActionBar createSupportActionBar() {
            return new ActionBarImplICS(this.mActivity, this.mActivity);
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            if ("splitActionBarWhenNarrow".equals(getUiOptionsFromMetadata())) {
                this.mActivity.getWindow().setUiOptions(1, 1);
                    }
            super.onCreate(savedInstanceState);
            if (this.mHasActionBar) {
                this.mActivity.requestWindowFeature(8);
                    }
            if (this.mOverlayActionBar) {
                this.mActivity.requestWindowFeature(9);
                    }
            Window w = this.mActivity.getWindow();
            w.setCallback(createWindowCallbackWrapper(w.getCallback()));
                }

        Window.Callback createWindowCallbackWrapper(Window.Callback cb) {
            return new WindowCallbackWrapper(cb);
                }

                @Override 
        public void onConfigurationChanged(Configuration newConfig) {
                }

                @Override 
        public void onStop() {
                }

                @Override 
       public void onPostResume() {
                }

                @Override 
       public void setContentView(View v) {
           this.mActivity.superSetContentView(v);
                }

                @Override 
       public void setContentView(int resId) {
           this.mActivity.superSetContentView(resId);
                }

                @Override 
       public void setContentView(View v, ViewGroup.LayoutParams lp) {
           this.mActivity.superSetContentView(v, lp);
                }

                @Override 
       public void addContentView(View v, ViewGroup.LayoutParams lp) {
           this.mActivity.superAddContentView(v, lp);
                }

                @Override 
       public void onContentChanged() {
           this.mActivity.onSupportContentChanged();
                }

                @Override 
       public boolean supportRequestWindowFeature(int featureId) {
           return this.mActivity.requestWindowFeature(featureId);
                }

                @Override 
       public View onCreatePanelView(int featureId) {
           return null;
                }

                @Override 
       public boolean onCreatePanelMenu(int featureId, Menu menu) {
           if (featureId != 0 && featureId != 8) {
                        return this.mActivity.superOnCreatePanelMenu(featureId, menu);
                    }
           if (this.mMenu == null) {
               this.mMenu = MenuWrapperFactory.createMenuWrapper(menu);
                    }
           return this.mActivity.superOnCreatePanelMenu(featureId, this.mMenu);
                }

                @Override 
       public boolean onPreparePanel(int featureId, View view, Menu menu) {
                    return (featureId == 0 || featureId == 8) ? this.mActivity.superOnPreparePanel(featureId, view, this.mMenu) : this.mActivity.superOnPreparePanel(featureId, view, menu);
                }

                @Override 
       public boolean onMenuItemSelected(int featureId, MenuItem item) {
           if (featureId == 0) {
               item = MenuWrapperFactory.createMenuItemWrapper(item);
                    }
           return this.mActivity.superOnMenuItemSelected(featureId, item);
                }

                @Override 
       public void onTitleChanged(CharSequence title) {
                }

                @Override 
       public ActionMode startSupportActionMode(ActionMode.Callback callback) {
           if (callback == null) {
               throw new IllegalArgumentException("ActionMode callback can not be null.");
                    }
           Context context = getActionBarThemedContext();
           ActionModeWrapper.CallbackWrapper wrappedCallback = createActionModeCallbackWrapper(context, callback);
           android.view.ActionMode frameworkMode = this.mActivity.startActionMode(wrappedCallback);
           if (frameworkMode == null) {
               return null;
                    }
           ActionModeWrapper wrappedMode = createActionModeWrapper(context, frameworkMode);
           wrappedCallback.setLastStartedActionMode(wrappedMode);
           return wrappedMode;
                }

       public void onActionModeStarted(android.view.ActionMode mode) {
           this.mActivity.onSupportActionModeStarted(createActionModeWrapper(getActionBarThemedContext(), mode));
                }

                @Override 
       void setSupportProgressBarVisibility(boolean visible) {
           this.mActivity.setProgressBarVisibility(visible);
                }

                @Override 
       void setSupportProgressBarIndeterminateVisibility(boolean visible) {
           this.mActivity.setProgressBarIndeterminateVisibility(visible);
                }

                @Override 
       void setSupportProgressBarIndeterminate(boolean indeterminate) {
           this.mActivity.setProgressBarIndeterminate(indeterminate);
                }

                @Override 
       void setSupportProgress(int progress) {
           this.mActivity.setProgress(progress);
                }

       public void onActionModeFinished(android.view.ActionMode mode) {
           this.mActivity.onSupportActionModeFinished(createActionModeWrapper(getActionBarThemedContext(), mode));
                }

                @Override 
       public void supportInvalidateOptionsMenu() {
           this.mMenu = null;
                }

                @Override 
       public boolean onBackPressed() {
           return false;
                }

                @Override 
       int getHomeAsUpIndicatorAttrId() {
           return R.attr.homeAsUpIndicator;
                }

       ActionModeWrapper.CallbackWrapper createActionModeCallbackWrapper(Context context, ActionMode.Callback callback) {
           return new ActionModeWrapper.CallbackWrapper(context, callback);
                }

       ActionModeWrapper createActionModeWrapper(Context context, android.view.ActionMode frameworkMode) {
           return new ActionModeWrapper(context, frameworkMode);
                }

       class WindowCallbackWrapper implements Window.Callback {
                    final Window.Callback mWrapped;

           public WindowCallbackWrapper(Window.Callback wrapped) {
               this.mWrapped = wrapped;
                    }

                    @Override 
           public boolean dispatchKeyEvent(KeyEvent event) {
               return this.mWrapped.dispatchKeyEvent(event);
                    }

                    @Override 
           public boolean dispatchKeyShortcutEvent(KeyEvent event) {
               return this.mWrapped.dispatchKeyShortcutEvent(event);
                    }

                    @Override 
           public boolean dispatchTouchEvent(MotionEvent event) {
               return this.mWrapped.dispatchTouchEvent(event);
                    }

                    @Override 
           public boolean dispatchTrackballEvent(MotionEvent event) {
               return this.mWrapped.dispatchTrackballEvent(event);
                    }

                    @Override 
           public boolean dispatchGenericMotionEvent(MotionEvent event) {
               return this.mWrapped.dispatchGenericMotionEvent(event);
                    }

                    @Override 
           public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
               return this.mWrapped.dispatchPopulateAccessibilityEvent(event);
                    }

                    @Override 
           public View onCreatePanelView(int featureId) {
               return this.mWrapped.onCreatePanelView(featureId);
                    }

                    @Override 
           public boolean onCreatePanelMenu(int featureId, Menu menu) {
               return this.mWrapped.onCreatePanelMenu(featureId, menu);
                    }

                    @Override 
           public boolean onPreparePanel(int featureId, View view, Menu menu) {
               return this.mWrapped.onPreparePanel(featureId, view, menu);
                    }

                    @Override 
           public boolean onMenuOpened(int featureId, Menu menu) {
               return this.mWrapped.onMenuOpened(featureId, menu);
                    }

                    @Override 
           public boolean onMenuItemSelected(int featureId, MenuItem item) {
               return this.mWrapped.onMenuItemSelected(featureId, item);
                    }

                    @Override 
           public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
               this.mWrapped.onWindowAttributesChanged(attrs);
                    }

                    @Override 
           public void onContentChanged() {
               this.mWrapped.onContentChanged();
                    }

                    @Override 
           public void onWindowFocusChanged(boolean hasFocus) {
               this.mWrapped.onWindowFocusChanged(hasFocus);
                    }

                    @Override 
           public void onAttachedToWindow() {
               this.mWrapped.onAttachedToWindow();
                    }

                    @Override 
           public void onDetachedFromWindow() {
               this.mWrapped.onDetachedFromWindow();
                    }

                    @Override 
           public void onPanelClosed(int featureId, Menu menu) {
               this.mWrapped.onPanelClosed(featureId, menu);
                    }

                    @Override 
           public boolean onSearchRequested() {
               return this.mWrapped.onSearchRequested();
                    }

                    @Override 
           public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback) {
               return this.mWrapped.onWindowStartingActionMode(callback);
                    }

                    @Override 
           public void onActionModeStarted(android.view.ActionMode mode) {
               this.mWrapped.onActionModeStarted(mode);
               ActionBarActivityDelegateICS.this.onActionModeStarted(mode);
                    }

                    @Override 
           public void onActionModeFinished(android.view.ActionMode mode) {
               this.mWrapped.onActionModeFinished(mode);
               ActionBarActivityDelegateICS.this.onActionModeFinished(mode);
                    }
                }
            }
