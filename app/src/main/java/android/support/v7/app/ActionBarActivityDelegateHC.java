            package android.support.v7.app;
            
            
    class ActionBarActivityDelegateHC extends ActionBarActivityDelegateBase {
        ActionBarActivityDelegateHC(ActionBarActivity activity) {
            super(activity);
                }

                @Override 
        public ActionBar createSupportActionBar() {
            ensureSubDecor();
            return new ActionBarImplHC(this.mActivity, this.mActivity);
                }
            }
