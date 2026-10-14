            package android.support.v4.app;

            import android.R;
            import android.app.ActionBar;
            import android.app.Activity;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.util.Log;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import java.lang.reflect.Method;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    class ActionBarDrawerToggleHoneycomb {
                private static final String TAG = "ActionBarDrawerToggleHoneycomb";
                private static final int[] THEME_ATTRS = {R.attr.homeAsUpIndicator};

/* 39 */        ActionBarDrawerToggleHoneycomb() {
                }

/* 48 */        public static Object setActionBarUpIndicator(Object info, Activity activity, Drawable drawable, int contentDescRes) {
/* 49 */            if (info == null) {
/* 50 */                info = new SetIndicatorInfo(activity);
                    }
/* 52 */            SetIndicatorInfo sii = (SetIndicatorInfo) info;
/* 53 */            if (sii.setHomeAsUpIndicator != null) {
                        try {
/* 55 */                    ActionBar actionBar = activity.getActionBar();
/* 56 */                    sii.setHomeAsUpIndicator.invoke(actionBar, drawable);
/* 57 */                    sii.setHomeActionContentDescription.invoke(actionBar, Integer.valueOf(contentDescRes));
                        } catch (Exception e) {
/* 59 */                    Log.w(TAG, "Couldn't set home-as-up indicator via JB-MR2 API", e);
                        }
                    } else if (sii.upIndicatorView != null) {
/* 62 */                sii.upIndicatorView.setImageDrawable(drawable);
                    } else {
/* 64 */                Log.w(TAG, "Couldn't set home-as-up indicator");
                    }
/* 66 */            return info;
                }

/* 70 */        public static Object setActionBarDescription(Object info, Activity activity, int contentDescRes) {
/* 71 */            if (info == null) {
/* 72 */                info = new SetIndicatorInfo(activity);
                    }
/* 74 */            SetIndicatorInfo sii = (SetIndicatorInfo) info;
/* 75 */            if (sii.setHomeAsUpIndicator != null) {
                        try {
/* 77 */                    ActionBar actionBar = activity.getActionBar();
/* 78 */                    sii.setHomeActionContentDescription.invoke(actionBar, Integer.valueOf(contentDescRes));
/* 79 */                    if (Build.VERSION.SDK_INT <= 19) {
/* 82 */                        actionBar.setSubtitle(actionBar.getSubtitle());
                            }
                        } catch (Exception e) {
/* 85 */                    Log.w(TAG, "Couldn't set content description via JB-MR2 API", e);
                        }
                    }
/* 88 */            return info;
                }

/* 91 */        public static Drawable getThemeUpIndicator(Activity activity) {
/* 92 */            TypedArray a = activity.obtainStyledAttributes(THEME_ATTRS);
/* 93 */            Drawable result = a.getDrawable(0);
/* 94 */            a.recycle();
/* 95 */            return result;
                }

/* 101 */       private static class SetIndicatorInfo {
                    public Method setHomeActionContentDescription;
                    public Method setHomeAsUpIndicator;
                    public ImageView upIndicatorView;

/* 102 */           SetIndicatorInfo(Activity activity) {
                        try {
/* 105 */                   this.setHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
/* 107 */                   this.setHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
                        } catch (NoSuchMethodException e) {
/* 116 */                   View home = activity.findViewById(R.id.home);
/* 117 */                   if (home != null) {
/* 122 */                       ViewGroup parent = (ViewGroup) home.getParent();
/* 123 */                       int childCount = parent.getChildCount();
/* 124 */                       if (childCount == 2) {
/* 129 */                           View first = parent.getChildAt(0);
/* 130 */                           View second = parent.getChildAt(1);
/* 131 */                           View up = first.getId() == 16908332 ? second : first;
/* 133 */                           if (up instanceof ImageView) {
/* 135 */                               this.upIndicatorView = (ImageView) up;
                                    }
                                }
                            }
                        }
                    }
                }
            }
