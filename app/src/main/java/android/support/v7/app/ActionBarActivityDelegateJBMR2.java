            package android.support.v7.app;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    class ActionBarActivityDelegateJBMR2 extends ActionBarActivityDelegateJB {
/* 21 */        ActionBarActivityDelegateJBMR2(ActionBarActivity activity) {
/* 22 */            super(activity);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegateJB, android.support.v7.app.ActionBarActivityDelegateICS, android.support.v7.app.ActionBarActivityDelegate
/* 26 */        public ActionBar createSupportActionBar() {
/* 27 */            return new ActionBarImplJBMR2(this.mActivity, this.mActivity);
                }
            }
