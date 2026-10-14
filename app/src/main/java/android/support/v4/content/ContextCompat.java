            package android.support.v4.content;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.Environment;
            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 65 */    public class ContextCompat {
                private static final String DIR_ANDROID = "Android";
                private static final String DIR_CACHE = "cache";
                private static final String DIR_DATA = "data";
                private static final String DIR_FILES = "files";
                private static final String DIR_OBB = "obb";

/* 66 */        public static boolean startActivities(Context context, Intent[] intents) {
/* 67 */            return startActivities(context, intents, null);
                }

/* 98 */        public static boolean startActivities(Context context, Intent[] intents, Bundle options) {
/* 99 */            int version = Build.VERSION.SDK_INT;
/* 100 */           if (version >= 16) {
/* 101 */               ContextCompatJellybean.startActivities(context, intents, options);
                        return true;
                    }
/* 103 */           if (version >= 11) {
/* 104 */               ContextCompatHoneycomb.startActivities(context, intents);
                        return true;
                    }
/* 107 */           return false;
                }

/* 153 */       public static File[] getObbDirs(Context context) {
                    File single;
/* 154 */           int version = Build.VERSION.SDK_INT;
/* 155 */           if (version >= 19) {
/* 156 */               return ContextCompatKitKat.getObbDirs(context);
                    }
/* 159 */           if (version >= 11) {
/* 160 */               single = ContextCompatHoneycomb.getObbDir(context);
                    } else {
/* 162 */               single = buildPath(Environment.getExternalStorageDirectory(), DIR_ANDROID, DIR_OBB, context.getPackageName());
                    }
/* 165 */           return new File[]{single};
                }

/* 213 */       public static File[] getExternalFilesDirs(Context context, String type) {
                    File single;
/* 214 */           int version = Build.VERSION.SDK_INT;
/* 215 */           if (version >= 19) {
/* 216 */               return ContextCompatKitKat.getExternalFilesDirs(context, type);
                    }
/* 219 */           if (version >= 8) {
/* 220 */               single = ContextCompatFroyo.getExternalFilesDir(context, type);
                    } else {
/* 222 */               single = buildPath(Environment.getExternalStorageDirectory(), DIR_ANDROID, "data", context.getPackageName(), DIR_FILES, type);
                    }
/* 225 */           return new File[]{single};
                }

/* 273 */       public static File[] getExternalCacheDirs(Context context) {
                    File single;
/* 274 */           int version = Build.VERSION.SDK_INT;
/* 275 */           if (version >= 19) {
/* 276 */               return ContextCompatKitKat.getExternalCacheDirs(context);
                    }
/* 279 */           if (version >= 8) {
/* 280 */               single = ContextCompatFroyo.getExternalCacheDir(context);
                    } else {
/* 282 */               single = buildPath(Environment.getExternalStorageDirectory(), DIR_ANDROID, "data", context.getPackageName(), "cache");
                    }
/* 285 */           return new File[]{single};
                }

/* 289 */       private static File buildPath(File base, String... segments) {
                    File cur;
/* 291 */           int len$ = segments.length;
                    int i$ = 0;
/* 290 */           File cur2 = base;
                    while (i$ < len$) {
/* 291 */               String segment = segments[i$];
/* 292 */               if (cur2 == null) {
/* 293 */                   cur = new File(segment);
                        } else {
/* 295 */                   cur = segment != null ? new File(cur2, segment) : cur2;
                        }
/* 291 */               i$++;
                        cur2 = cur;
                    }
/* 298 */           return cur2;
                }
            }
