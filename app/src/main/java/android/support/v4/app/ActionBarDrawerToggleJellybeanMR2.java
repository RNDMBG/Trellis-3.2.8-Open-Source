            package android.support.v4.app;

            import android.R;
            import android.app.ActionBar;
            import android.app.Activity;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    class ActionBarDrawerToggleJellybeanMR2 {
                private static final String TAG = "ActionBarDrawerToggleImplJellybeanMR2";
                private static final int[] THEME_ATTRS = {R.attr.homeAsUpIndicator};

/* 26 */        ActionBarDrawerToggleJellybeanMR2() {
                }

/* 35 */        public static Object setActionBarUpIndicator(Object info, Activity activity, Drawable drawable, int contentDescRes) {
/* 36 */            ActionBar actionBar = activity.getActionBar();
/* 37 */            if (actionBar != null) {
/* 38 */                actionBar.setHomeAsUpIndicator(drawable);
/* 39 */                actionBar.setHomeActionContentDescription(contentDescRes);
                    }
/* 41 */            return info;
                }

/* 45 */        public static Object setActionBarDescription(Object info, Activity activity, int contentDescRes) {
/* 46 */            ActionBar actionBar = activity.getActionBar();
/* 47 */            if (actionBar != null) {
/* 48 */                actionBar.setHomeActionContentDescription(contentDescRes);
                    }
/* 50 */            return info;
                }

/* 53 */        public static Drawable getThemeUpIndicator(Activity activity) {
/* 54 */            TypedArray a = activity.obtainStyledAttributes(THEME_ATTRS);
/* 55 */            Drawable result = a.getDrawable(0);
/* 56 */            a.recycle();
/* 57 */            return result;
                }
            }
