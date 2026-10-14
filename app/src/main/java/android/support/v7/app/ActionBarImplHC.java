            package android.support.v7.app;

            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.widget.NativeActionModeAwareLayout;
            import android.view.ActionMode;
            import android.view.Menu;
            import android.view.MenuItem;
            
            
    class ActionBarImplHC extends ActionBarImplBase implements NativeActionModeAwareLayout.OnActionModeForChildListener {
                private ActionMode mCurActionMode;
                final NativeActionModeAwareLayout mNativeActionModeAwareLayout;

        static  ActionMode access$002(ActionBarImplHC x0, ActionMode x1) {
            x0.mCurActionMode = x1;
                    return x1;
                }

        public ActionBarImplHC(ActionBarActivity activity, ActionBar.Callback callback) {
            super(activity, callback);
            this.mNativeActionModeAwareLayout = (NativeActionModeAwareLayout) activity.findViewById(C0084R.id.action_bar_root);
            if (this.mNativeActionModeAwareLayout != null) {
                this.mNativeActionModeAwareLayout.setActionModeForChildListener(this);
                    }
                }

                @Override 
        public ActionMode.Callback onActionModeForChild(ActionMode.Callback callback) {
            return new CallbackWrapper(callback);
                }

                @Override 
        public void show() {
            super.show();
            if (this.mCurActionMode != null) {
                this.mCurActionMode.finish();
                    }
                }

                @Override 
        public void hide() {
            super.hide();
            if (this.mCurActionMode != null) {
                this.mCurActionMode.finish();
                    }
                }

                @Override 
        boolean isShowHideAnimationEnabled() {
                    return this.mCurActionMode == null && super.isShowHideAnimationEnabled();
                }

        private class CallbackWrapper implements ActionMode.Callback {
                    private final ActionMode.Callback mWrappedCallback;

            CallbackWrapper(ActionMode.Callback callback) {
                this.mWrappedCallback = callback;
                    }

                    @Override 
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                boolean wrappedResult = this.mWrappedCallback.onCreateActionMode(mode, menu);
                if (wrappedResult) {
                    ActionBarImplHC.access$002(ActionBarImplHC.this, mode);
                    ActionBarImplHC.this.showForActionMode();
                        }
                return wrappedResult;
                    }

                    @Override 
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return this.mWrappedCallback.onPrepareActionMode(mode, menu);
                    }

                    @Override 
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return this.mWrappedCallback.onActionItemClicked(mode, item);
                    }

                    @Override 
           public void onDestroyActionMode(ActionMode mode) {
               this.mWrappedCallback.onDestroyActionMode(mode);
               ActionBarImplHC.this.hideForActionMode();
               ActionBarImplHC.access$002(ActionBarImplHC.this, null);
                    }
                }
            }
