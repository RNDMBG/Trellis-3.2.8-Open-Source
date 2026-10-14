            package android.support.v4.text;

            import android.util.Log;
            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    class ICUCompatIcs {
                private static final String TAG = "ICUCompatIcs";
                private static Method sAddLikelySubtagsMethod;
                private static Method sGetScriptMethod;

/* 23 */        ICUCompatIcs() {
                }

                static {
                    try {
/* 33 */                Class<?> clazz = Class.forName("libcore.icu.ICU");
/* 34 */                if (clazz != null) {
/* 35 */                    sGetScriptMethod = clazz.getMethod("getScript", String.class);
/* 37 */                    sAddLikelySubtagsMethod = clazz.getMethod("addLikelySubtags", String.class);
                        }
                    } catch (Exception e) {
/* 42 */                Log.w(TAG, e);
                    }
                }

/* 47 */        public static String getScript(String locale) {
                    try {
/* 48 */                if (sGetScriptMethod != null) {
/* 49 */                    Object[] args = {locale};
/* 50 */                    return (String) sGetScriptMethod.invoke(null, args);
                        }
                    } catch (IllegalAccessException e) {
/* 54 */                Log.w(TAG, e);
                    } catch (InvocationTargetException e2) {
/* 58 */                Log.w(TAG, e2);
                    }
                    return null;
                }

/* 64 */        public static String addLikelySubtags(String locale) {
                    try {
/* 65 */                if (sAddLikelySubtagsMethod != null) {
/* 66 */                    Object[] args = {locale};
/* 67 */                    return (String) sAddLikelySubtagsMethod.invoke(null, args);
                        }
                    } catch (IllegalAccessException e) {
/* 71 */                Log.w(TAG, e);
                    } catch (InvocationTargetException e2) {
/* 75 */                Log.w(TAG, e2);
                    }
                    return locale;
                }
            }
