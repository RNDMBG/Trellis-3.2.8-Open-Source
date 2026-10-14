            package android.support.v7.app;

            import android.app.Activity;
            import android.graphics.drawable.Drawable;
            
            
    public class ActionBarImplJBMR2 extends ActionBarImplJB {
        public ActionBarImplJBMR2(Activity activity, ActionBar.Callback callback) {
            super(activity, callback);
                }

                @Override 
        public void setHomeAsUpIndicator(Drawable indicator) {
            this.mActionBar.setHomeAsUpIndicator(indicator);
                }

                @Override 
        public void setHomeAsUpIndicator(int resId) {
            this.mActionBar.setHomeAsUpIndicator(resId);
                }

                @Override 
        public void setHomeActionContentDescription(CharSequence description) {
            this.mActionBar.setHomeActionContentDescription(description);
                }

                @Override 
        public void setHomeActionContentDescription(int resId) {
            this.mActionBar.setHomeActionContentDescription(resId);
                }
            }
