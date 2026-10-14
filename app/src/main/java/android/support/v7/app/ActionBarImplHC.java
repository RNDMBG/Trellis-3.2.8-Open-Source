            package android.support.v7.app;

            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.widget.NativeActionModeAwareLayout;
            import android.view.ActionMode;
            import android.view.Menu;
            import android.view.MenuItem;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    class ActionBarImplHC extends ActionBarImplBase implements NativeActionModeAwareLayout.OnActionModeForChildListener {
                private ActionMode mCurActionMode;
                final NativeActionModeAwareLayout mNativeActionModeAwareLayout;

/* 24 */        static /* synthetic */ ActionMode access$002(ActionBarImplHC x0, ActionMode x1) {
/* 25 */            x0.mCurActionMode = x1;
                    return x1;
                }

/* 31 */        public ActionBarImplHC(ActionBarActivity activity, ActionBar.Callback callback) {
/* 32 */            super(activity, callback);
/* 35 */            this.mNativeActionModeAwareLayout = (NativeActionModeAwareLayout) activity.findViewById(C0084R.id.action_bar_root);
/* 39 */            if (this.mNativeActionModeAwareLayout != null) {
/* 40 */                this.mNativeActionModeAwareLayout.setActionModeForChildListener(this);
                    }
                }

                @Override // android.support.v7.internal.widget.NativeActionModeAwareLayout.OnActionModeForChildListener
/* 46 */        public ActionMode.Callback onActionModeForChild(ActionMode.Callback callback) {
/* 47 */            return new CallbackWrapper(callback);
                }

                @Override // android.support.v7.app.ActionBarImplBase, android.support.v7.app.ActionBar
/* 51 */        public void show() {
/* 52 */            super.show();
/* 53 */            if (this.mCurActionMode != null) {
/* 54 */                this.mCurActionMode.finish();
                    }
                }

                @Override // android.support.v7.app.ActionBarImplBase, android.support.v7.app.ActionBar
/* 59 */        public void hide() {
/* 60 */            super.hide();
/* 61 */            if (this.mCurActionMode != null) {
/* 62 */                this.mCurActionMode.finish();
                    }
                }

                @Override // android.support.v7.app.ActionBarImplBase
/* 68 */        boolean isShowHideAnimationEnabled() {
                    return this.mCurActionMode == null && super.isShowHideAnimationEnabled();
                }

/* 73 */        private class CallbackWrapper implements ActionMode.Callback {
                    private final ActionMode.Callback mWrappedCallback;

/* 74 */            CallbackWrapper(ActionMode.Callback callback) {
/* 76 */                this.mWrappedCallback = callback;
                    }

                    @Override // android.view.ActionMode.Callback
/* 80 */            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
/* 81 */                boolean wrappedResult = this.mWrappedCallback.onCreateActionMode(mode, menu);
/* 82 */                if (wrappedResult) {
/* 84 */                    ActionBarImplHC.access$002(ActionBarImplHC.this, mode);
/* 86 */                    ActionBarImplHC.this.showForActionMode();
                        }
/* 88 */                return wrappedResult;
                    }

                    @Override // android.view.ActionMode.Callback
/* 92 */            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
/* 93 */                return this.mWrappedCallback.onPrepareActionMode(mode, menu);
                    }

                    @Override // android.view.ActionMode.Callback
/* 97 */            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
/* 98 */                return this.mWrappedCallback.onActionItemClicked(mode, item);
                    }

                    @Override // android.view.ActionMode.Callback
/* 102 */           public void onDestroyActionMode(ActionMode mode) {
/* 103 */               this.mWrappedCallback.onDestroyActionMode(mode);
/* 106 */               ActionBarImplHC.this.hideForActionMode();
/* 108 */               ActionBarImplHC.access$002(ActionBarImplHC.this, null);
                    }
                }
            }
