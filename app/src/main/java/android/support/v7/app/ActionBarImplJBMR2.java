            package android.support.v7.app;

            import android.app.Activity;
            import android.graphics.drawable.Drawable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class ActionBarImplJBMR2 extends ActionBarImplJB {
/* 23 */        public ActionBarImplJBMR2(Activity activity, ActionBar.Callback callback) {
/* 24 */            super(activity, callback);
                }

                @Override // android.support.v7.app.ActionBarImplJB, android.support.v7.app.ActionBarImplICS, android.support.v7.app.ActionBar
/* 28 */        public void setHomeAsUpIndicator(Drawable indicator) {
/* 29 */            this.mActionBar.setHomeAsUpIndicator(indicator);
                }

                @Override // android.support.v7.app.ActionBarImplJB, android.support.v7.app.ActionBarImplICS, android.support.v7.app.ActionBar
/* 33 */        public void setHomeAsUpIndicator(int resId) {
/* 34 */            this.mActionBar.setHomeAsUpIndicator(resId);
                }

                @Override // android.support.v7.app.ActionBar
/* 38 */        public void setHomeActionContentDescription(CharSequence description) {
/* 39 */            this.mActionBar.setHomeActionContentDescription(description);
                }

                @Override // android.support.v7.app.ActionBar
/* 43 */        public void setHomeActionContentDescription(int resId) {
/* 44 */            this.mActionBar.setHomeActionContentDescription(resId);
                }
            }
