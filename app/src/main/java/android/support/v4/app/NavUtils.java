            package android.support.v4.app;

            import android.app.Activity;
            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.ActivityInfo;
            import android.content.pm.PackageManager;
            import android.os.Build;
            import android.support.v4.content.IntentCompat;
            import android.util.Log;
            import com.googlecode.javacv.cpp.avformat;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public class NavUtils {
                private static final NavUtilsImpl IMPL;
                public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
                private static final String TAG = "NavUtils";

                interface NavUtilsImpl {
                    Intent getParentActivityIntent(Activity activity);

                    String getParentActivityName(Context context, ActivityInfo activityInfo);

                    void navigateUpTo(Activity activity, Intent intent);

                    boolean shouldUpRecreateTask(Activity activity, Intent intent);
                }

/* 46 */        static class NavUtilsImplBase implements NavUtilsImpl {
/* 47 */            NavUtilsImplBase() {
                    }

                    @Override // android.support.v4.app.NavUtils.NavUtilsImpl
/* 51 */            public Intent getParentActivityIntent(Activity activity) {
                        Intent parentIntent = null;
/* 52 */                String parentName = NavUtils.getParentActivityName(activity);
/* 53 */                if (parentName != null) {
/* 56 */                    ComponentName target = new ComponentName(activity, parentName);
                            try {
/* 58 */                        String grandparent = NavUtils.getParentActivityName(activity, target);
/* 59 */                        parentIntent = grandparent == null ? IntentCompat.makeMainActivity(target) : new Intent().setComponent(target);
                            } catch (PackageManager.NameNotFoundException e) {
/* 64 */                        Log.e(NavUtils.TAG, "getParentActivityIntent: bad parentActivityName '" + parentName + "' in manifest");
                            }
                        }
/* 66 */                return parentIntent;
                    }

                    @Override // android.support.v4.app.NavUtils.NavUtilsImpl
/* 71 */            public boolean shouldUpRecreateTask(Activity activity, Intent targetIntent) {
/* 72 */                String action = activity.getIntent().getAction();
                        return (action == null || action.equals("android.intent.action.MAIN")) ? false : true;
                    }

                    @Override // android.support.v4.app.NavUtils.NavUtilsImpl
/* 77 */            public void navigateUpTo(Activity activity, Intent upIntent) {
/* 78 */                upIntent.addFlags(avformat.AVFMT_SEEK_TO_PTS);
/* 79 */                activity.startActivity(upIntent);
/* 80 */                activity.finish();
                    }

                    @Override // android.support.v4.app.NavUtils.NavUtilsImpl
/* 84 */            public String getParentActivityName(Context context, ActivityInfo info) {
                        String parentActivity;
/* 85 */                if (info.metaData != null && (parentActivity = info.metaData.getString(NavUtils.PARENT_ACTIVITY)) != null) {
/* 88 */                    if (parentActivity.charAt(0) == '.') {
/* 89 */                        return context.getPackageName() + parentActivity;
                            }
/* 91 */                    return parentActivity;
                        }
                        return null;
                    }
                }

/* 93 */        static class NavUtilsImplJB extends NavUtilsImplBase {
/* 94 */            NavUtilsImplJB() {
                    }

                    @Override // android.support.v4.app.NavUtils.NavUtilsImplBase, android.support.v4.app.NavUtils.NavUtilsImpl
/* 100 */           public Intent getParentActivityIntent(Activity activity) {
/* 101 */               Intent result = NavUtilsJB.getParentActivityIntent(activity);
/* 102 */               if (result == null) {
/* 103 */                   return superGetParentActivityIntent(activity);
                        }
/* 105 */               return result;
                    }

/* 108 */           Intent superGetParentActivityIntent(Activity activity) {
/* 109 */               return super.getParentActivityIntent(activity);
                    }

                    @Override // android.support.v4.app.NavUtils.NavUtilsImplBase, android.support.v4.app.NavUtils.NavUtilsImpl
/* 113 */           public boolean shouldUpRecreateTask(Activity activity, Intent targetIntent) {
/* 114 */               return NavUtilsJB.shouldUpRecreateTask(activity, targetIntent);
                    }

                    @Override // android.support.v4.app.NavUtils.NavUtilsImplBase, android.support.v4.app.NavUtils.NavUtilsImpl
/* 118 */           public void navigateUpTo(Activity activity, Intent upIntent) {
/* 119 */               NavUtilsJB.navigateUpTo(activity, upIntent);
                    }

                    @Override // android.support.v4.app.NavUtils.NavUtilsImplBase, android.support.v4.app.NavUtils.NavUtilsImpl
/* 123 */           public String getParentActivityName(Context context, ActivityInfo info) {
/* 124 */               String result = NavUtilsJB.getParentActivityName(info);
/* 125 */               if (result == null) {
/* 126 */                   return super.getParentActivityName(context, info);
                        }
/* 128 */               return result;
                    }
                }

                static {
/* 135 */           int version = Build.VERSION.SDK_INT;
/* 136 */           if (version >= 16) {
/* 137 */               IMPL = new NavUtilsImplJB();
                    } else {
/* 139 */               IMPL = new NavUtilsImplBase();
                    }
                }

/* 157 */       public static boolean shouldUpRecreateTask(Activity sourceActivity, Intent targetIntent) {
/* 158 */           return IMPL.shouldUpRecreateTask(sourceActivity, targetIntent);
                }

/* 173 */       public static void navigateUpFromSameTask(Activity sourceActivity) {
/* 174 */           Intent upIntent = getParentActivityIntent(sourceActivity);
/* 176 */           if (upIntent == null) {
/* 177 */               throw new IllegalArgumentException("Activity " + sourceActivity.getClass().getSimpleName() + " does not have a parent activity name specified. (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data>  element in your manifest?)");
                    }
/* 184 */           navigateUpTo(sourceActivity, upIntent);
                }

/* 200 */       public static void navigateUpTo(Activity sourceActivity, Intent upIntent) {
/* 201 */           IMPL.navigateUpTo(sourceActivity, upIntent);
                }

/* 214 */       public static Intent getParentActivityIntent(Activity sourceActivity) {
/* 215 */           return IMPL.getParentActivityIntent(sourceActivity);
                }

/* 229 */       public static Intent getParentActivityIntent(Context context, Class<?> sourceActivityClass) throws PackageManager.NameNotFoundException {
/* 230 */           String parentActivity = getParentActivityName(context, new ComponentName(context, sourceActivityClass));
/* 232 */           if (parentActivity == null) {
                        return null;
                    }
/* 235 */           ComponentName target = new ComponentName(context, parentActivity);
/* 236 */           String grandparent = getParentActivityName(context, target);
                    return grandparent == null ? IntentCompat.makeMainActivity(target) : new Intent().setComponent(target);
                }

/* 254 */       public static Intent getParentActivityIntent(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
/* 255 */           String parentActivity = getParentActivityName(context, componentName);
/* 256 */           if (parentActivity == null) {
                        return null;
                    }
/* 259 */           ComponentName target = new ComponentName(componentName.getPackageName(), parentActivity);
/* 261 */           String grandparent = getParentActivityName(context, target);
                    return grandparent == null ? IntentCompat.makeMainActivity(target) : new Intent().setComponent(target);
                }

/* 278 */       public static String getParentActivityName(Activity sourceActivity) {
                    try {
/* 279 */               return getParentActivityName(sourceActivity, sourceActivity.getComponentName());
                    } catch (PackageManager.NameNotFoundException e) {
/* 282 */               throw new IllegalArgumentException(e);
                    }
                }

/* 296 */       public static String getParentActivityName(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
/* 297 */           PackageManager pm = context.getPackageManager();
/* 298 */           ActivityInfo info = pm.getActivityInfo(componentName, 128);
/* 299 */           String parentActivity = IMPL.getParentActivityName(context, info);
/* 300 */           return parentActivity;
                }

/* 303 */       private NavUtils() {
                }
            }
