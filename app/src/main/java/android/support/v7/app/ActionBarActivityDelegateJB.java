            package android.support.v7.app;

            import android.content.Context;
            import android.support.v7.internal.view.ActionModeWrapper;
            import android.support.v7.internal.view.ActionModeWrapperJB;
            import android.support.v7.view.ActionMode;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    class ActionBarActivityDelegateJB extends ActionBarActivityDelegateICS {
/* 26 */        ActionBarActivityDelegateJB(ActionBarActivity activity) {
/* 27 */            super(activity);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegateICS, android.support.v7.app.ActionBarActivityDelegate
/* 31 */        public ActionBar createSupportActionBar() {
/* 32 */            return new ActionBarImplJB(this.mActivity, this.mActivity);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegateICS
/* 37 */        ActionModeWrapper.CallbackWrapper createActionModeCallbackWrapper(Context context, ActionMode.Callback callback) {
/* 38 */            return new ActionModeWrapperJB.CallbackWrapper(context, callback);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegateICS
/* 42 */        ActionModeWrapper createActionModeWrapper(Context context, android.view.ActionMode frameworkMode) {
/* 43 */            return new ActionModeWrapperJB(context, frameworkMode);
                }
            }
