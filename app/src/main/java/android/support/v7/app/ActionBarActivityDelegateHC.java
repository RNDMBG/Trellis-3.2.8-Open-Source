            package android.support.v7.app;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    class ActionBarActivityDelegateHC extends ActionBarActivityDelegateBase {
/* 21 */        ActionBarActivityDelegateHC(ActionBarActivity activity) {
/* 22 */            super(activity);
                }

                @Override // android.support.v7.app.ActionBarActivityDelegateBase, android.support.v7.app.ActionBarActivityDelegate
/* 26 */        public ActionBar createSupportActionBar() {
/* 27 */            ensureSubDecor();
/* 28 */            return new ActionBarImplHC(this.mActivity, this.mActivity);
                }
            }
