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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    class ActionBarActivityDelegateICS extends ActionBarActivityDelegate {
                Menu mMenu;

/* 40 */        ActionBarActivityDelegateICS(ActionBarActivity activity) {
/* 41 */            super(activity);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 45 */        public ActionBar createSupportActionBar() {
/* 46 */            return new ActionBarImplICS(this.mActivity, this.mActivity);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 51 */        public void onCreate(Bundle savedInstanceState) {
/* 52 */            if ("splitActionBarWhenNarrow".equals(getUiOptionsFromMetadata())) {
/* 53 */                this.mActivity.getWindow().setUiOptions(1, 1);
                    }
/* 57 */            super.onCreate(savedInstanceState);
/* 59 */            if (this.mHasActionBar) {
/* 62 */                this.mActivity.requestWindowFeature(8);
                    }
/* 64 */            if (this.mOverlayActionBar) {
/* 65 */                this.mActivity.requestWindowFeature(9);
                    }
/* 83 */            Window w = this.mActivity.getWindow();
/* 84 */            w.setCallback(createWindowCallbackWrapper(w.getCallback()));
                }

/* 87 */        Window.Callback createWindowCallbackWrapper(Window.Callback cb) {
/* 88 */            return new WindowCallbackWrapper(cb);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 92 */        public void onConfigurationChanged(Configuration newConfig) {
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 96 */        public void onStop() {
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 100 */       public void onPostResume() {
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 104 */       public void setContentView(View v) {
/* 105 */           this.mActivity.superSetContentView(v);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 109 */       public void setContentView(int resId) {
/* 110 */           this.mActivity.superSetContentView(resId);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 114 */       public void setContentView(View v, ViewGroup.LayoutParams lp) {
/* 115 */           this.mActivity.superSetContentView(v, lp);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 119 */       public void addContentView(View v, ViewGroup.LayoutParams lp) {
/* 120 */           this.mActivity.superAddContentView(v, lp);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 125 */       public void onContentChanged() {
/* 126 */           this.mActivity.onSupportContentChanged();
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 130 */       public boolean supportRequestWindowFeature(int featureId) {
/* 131 */           return this.mActivity.requestWindowFeature(featureId);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 136 */       public View onCreatePanelView(int featureId) {
/* 137 */           return null;
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 141 */       public boolean onCreatePanelMenu(int featureId, Menu menu) {
/* 142 */           if (featureId != 0 && featureId != 8) {
                        return this.mActivity.superOnCreatePanelMenu(featureId, menu);
                    }
/* 143 */           if (this.mMenu == null) {
/* 144 */               this.mMenu = MenuWrapperFactory.createMenuWrapper(menu);
                    }
/* 146 */           return this.mActivity.superOnCreatePanelMenu(featureId, this.mMenu);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 152 */       public boolean onPreparePanel(int featureId, View view, Menu menu) {
                    return (featureId == 0 || featureId == 8) ? this.mActivity.superOnPreparePanel(featureId, view, this.mMenu) : this.mActivity.superOnPreparePanel(featureId, view, menu);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 160 */       public boolean onMenuItemSelected(int featureId, MenuItem item) {
/* 161 */           if (featureId == 0) {
/* 162 */               item = MenuWrapperFactory.createMenuItemWrapper(item);
                    }
/* 164 */           return this.mActivity.superOnMenuItemSelected(featureId, item);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 169 */       public void onTitleChanged(CharSequence title) {
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 173 */       public ActionMode startSupportActionMode(ActionMode.Callback callback) {
/* 174 */           if (callback == null) {
/* 175 */               throw new IllegalArgumentException("ActionMode callback can not be null.");
                    }
/* 178 */           Context context = getActionBarThemedContext();
/* 180 */           ActionModeWrapper.CallbackWrapper wrappedCallback = createActionModeCallbackWrapper(context, callback);
/* 184 */           android.view.ActionMode frameworkMode = this.mActivity.startActionMode(wrappedCallback);
/* 186 */           if (frameworkMode == null) {
/* 182 */               return null;
                    }
/* 187 */           ActionModeWrapper wrappedMode = createActionModeWrapper(context, frameworkMode);
/* 188 */           wrappedCallback.setLastStartedActionMode(wrappedMode);
/* 191 */           return wrappedMode;
                }

/* 194 */       public void onActionModeStarted(android.view.ActionMode mode) {
/* 195 */           this.mActivity.onSupportActionModeStarted(createActionModeWrapper(getActionBarThemedContext(), mode));
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 200 */       void setSupportProgressBarVisibility(boolean visible) {
/* 201 */           this.mActivity.setProgressBarVisibility(visible);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 205 */       void setSupportProgressBarIndeterminateVisibility(boolean visible) {
/* 206 */           this.mActivity.setProgressBarIndeterminateVisibility(visible);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 210 */       void setSupportProgressBarIndeterminate(boolean indeterminate) {
/* 211 */           this.mActivity.setProgressBarIndeterminate(indeterminate);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 215 */       void setSupportProgress(int progress) {
/* 216 */           this.mActivity.setProgress(progress);
                }

/* 219 */       public void onActionModeFinished(android.view.ActionMode mode) {
/* 220 */           this.mActivity.onSupportActionModeFinished(createActionModeWrapper(getActionBarThemedContext(), mode));
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 225 */       public void supportInvalidateOptionsMenu() {
/* 226 */           this.mMenu = null;
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 230 */       public boolean onBackPressed() {
/* 231 */           return false;
                }

                @Override // android.support.v7.app.ActionBarActivityDelegate
/* 235 */       int getHomeAsUpIndicatorAttrId() {
/* 236 */           return R.attr.homeAsUpIndicator;
                }

/* 240 */       ActionModeWrapper.CallbackWrapper createActionModeCallbackWrapper(Context context, ActionMode.Callback callback) {
/* 241 */           return new ActionModeWrapper.CallbackWrapper(context, callback);
                }

/* 245 */       ActionModeWrapper createActionModeWrapper(Context context, android.view.ActionMode frameworkMode) {
/* 246 */           return new ActionModeWrapper(context, frameworkMode);
                }

/* 250 */       class WindowCallbackWrapper implements Window.Callback {
                    final Window.Callback mWrapped;

/* 251 */           public WindowCallbackWrapper(Window.Callback wrapped) {
/* 253 */               this.mWrapped = wrapped;
                    }

                    @Override // android.view.Window.Callback
/* 257 */           public boolean dispatchKeyEvent(KeyEvent event) {
/* 258 */               return this.mWrapped.dispatchKeyEvent(event);
                    }

                    @Override // android.view.Window.Callback
/* 262 */           public boolean dispatchKeyShortcutEvent(KeyEvent event) {
/* 263 */               return this.mWrapped.dispatchKeyShortcutEvent(event);
                    }

                    @Override // android.view.Window.Callback
/* 267 */           public boolean dispatchTouchEvent(MotionEvent event) {
/* 268 */               return this.mWrapped.dispatchTouchEvent(event);
                    }

                    @Override // android.view.Window.Callback
/* 272 */           public boolean dispatchTrackballEvent(MotionEvent event) {
/* 273 */               return this.mWrapped.dispatchTrackballEvent(event);
                    }

                    @Override // android.view.Window.Callback
/* 277 */           public boolean dispatchGenericMotionEvent(MotionEvent event) {
/* 278 */               return this.mWrapped.dispatchGenericMotionEvent(event);
                    }

                    @Override // android.view.Window.Callback
/* 282 */           public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
/* 283 */               return this.mWrapped.dispatchPopulateAccessibilityEvent(event);
                    }

                    @Override // android.view.Window.Callback
/* 287 */           public View onCreatePanelView(int featureId) {
/* 288 */               return this.mWrapped.onCreatePanelView(featureId);
                    }

                    @Override // android.view.Window.Callback
/* 292 */           public boolean onCreatePanelMenu(int featureId, Menu menu) {
/* 293 */               return this.mWrapped.onCreatePanelMenu(featureId, menu);
                    }

                    @Override // android.view.Window.Callback
/* 297 */           public boolean onPreparePanel(int featureId, View view, Menu menu) {
/* 298 */               return this.mWrapped.onPreparePanel(featureId, view, menu);
                    }

                    @Override // android.view.Window.Callback
/* 302 */           public boolean onMenuOpened(int featureId, Menu menu) {
/* 303 */               return this.mWrapped.onMenuOpened(featureId, menu);
                    }

                    @Override // android.view.Window.Callback
/* 307 */           public boolean onMenuItemSelected(int featureId, MenuItem item) {
/* 308 */               return this.mWrapped.onMenuItemSelected(featureId, item);
                    }

                    @Override // android.view.Window.Callback
/* 312 */           public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
/* 313 */               this.mWrapped.onWindowAttributesChanged(attrs);
                    }

                    @Override // android.view.Window.Callback
/* 317 */           public void onContentChanged() {
/* 318 */               this.mWrapped.onContentChanged();
                    }

                    @Override // android.view.Window.Callback
/* 322 */           public void onWindowFocusChanged(boolean hasFocus) {
/* 323 */               this.mWrapped.onWindowFocusChanged(hasFocus);
                    }

                    @Override // android.view.Window.Callback
/* 327 */           public void onAttachedToWindow() {
/* 328 */               this.mWrapped.onAttachedToWindow();
                    }

                    @Override // android.view.Window.Callback
/* 332 */           public void onDetachedFromWindow() {
/* 333 */               this.mWrapped.onDetachedFromWindow();
                    }

                    @Override // android.view.Window.Callback
/* 337 */           public void onPanelClosed(int featureId, Menu menu) {
/* 338 */               this.mWrapped.onPanelClosed(featureId, menu);
                    }

                    @Override // android.view.Window.Callback
/* 342 */           public boolean onSearchRequested() {
/* 343 */               return this.mWrapped.onSearchRequested();
                    }

                    @Override // android.view.Window.Callback
/* 348 */           public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback) {
/* 349 */               return this.mWrapped.onWindowStartingActionMode(callback);
                    }

                    @Override // android.view.Window.Callback
/* 357 */           public void onActionModeStarted(android.view.ActionMode mode) {
/* 358 */               this.mWrapped.onActionModeStarted(mode);
/* 359 */               ActionBarActivityDelegateICS.this.onActionModeStarted(mode);
                    }

                    @Override // android.view.Window.Callback
/* 363 */           public void onActionModeFinished(android.view.ActionMode mode) {
/* 364 */               this.mWrapped.onActionModeFinished(mode);
/* 365 */               ActionBarActivityDelegateICS.this.onActionModeFinished(mode);
                    }
                }
            }
