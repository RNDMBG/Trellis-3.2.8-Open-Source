            package android.support.v7.app;

            import android.content.Context;
            import android.support.v7.internal.view.ActionModeWrapper;
            import android.support.v7.internal.view.ActionModeWrapperJB;
            import android.support.v7.view.ActionMode;
            
            
    class ActionBarActivityDelegateJB extends ActionBarActivityDelegateICS {
        ActionBarActivityDelegateJB(ActionBarActivity activity) {
            super(activity);
                }

                @Override 
        public ActionBar createSupportActionBar() {
            return new ActionBarImplJB(this.mActivity, this.mActivity);
                }

                @Override 
        ActionModeWrapper.CallbackWrapper createActionModeCallbackWrapper(Context context, ActionMode.Callback callback) {
            return new ActionModeWrapperJB.CallbackWrapper(context, callback);
                }

                @Override 
        ActionModeWrapper createActionModeWrapper(Context context, android.view.ActionMode frameworkMode) {
            return new ActionModeWrapperJB(context, frameworkMode);
                }
            }
