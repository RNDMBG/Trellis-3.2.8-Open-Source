            package android.support.v4.database;

            import android.text.TextUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class DatabaseUtilsCompat {
/* 26 */        private DatabaseUtilsCompat() {
                }

/* 34 */        public static String concatenateWhere(String a, String b) {
/* 35 */            if (TextUtils.isEmpty(a)) {
/* 42 */                return b;
                    }
                    return TextUtils.isEmpty(b) ? a : "(" + a + ") AND (" + b + ")";
                }

/* 49 */        public static String[] appendSelectionArgs(String[] originalValues, String[] newValues) {
/* 50 */            if (originalValues == null || originalValues.length == 0) {
                        return newValues;
                    }
/* 53 */            String[] result = new String[originalValues.length + newValues.length];
/* 54 */            System.arraycopy(originalValues, 0, result, 0, originalValues.length);
/* 55 */            System.arraycopy(newValues, 0, result, originalValues.length, newValues.length);
/* 56 */            return result;
                }
            }
