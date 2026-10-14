            package co.vine.android.provider;

            import android.content.ContentValues;
            import android.content.Context;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import co.vine.android.Settings;
            import co.vine.android.util.Util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class SettingsManager {
                private static final String CURRENT_ACCOUNT_KEY = "current_account";
                private static final String CURRENT_ACCOUNT_NAME = "current_name";
                private static final String CURRENT_USER_ID_KEY = "current_user_id";
                private static final String[] VALUE_PROJECTION = {Vine.Settings.PROP_VALUE};

/* 41 */        private SettingsManager() {
                }

/* 54 */        public static void setCurrentAccount(Context context, String emailLogin, String name, long userId) {
/* 55 */            setValue(context, CURRENT_ACCOUNT_KEY, emailLogin);
/* 56 */            setValue(context, CURRENT_USER_ID_KEY, userId);
/* 57 */            setValue(context, CURRENT_ACCOUNT_NAME, name);
                }

/* 64 */        public static String getCurrentAccount(Context context) {
/* 65 */            return getValue(context, CURRENT_ACCOUNT_KEY, (String) null);
                }

/* 68 */        public static String getCurrentAccountName(Context context) {
/* 69 */            return getValue(context, CURRENT_ACCOUNT_NAME, (String) null);
                }

/* 72 */        public static long getCurrentUserId(Context context) {
/* 73 */            return getValue(context, CURRENT_USER_ID_KEY, 0L);
                }

/* 76 */        private static synchronized long getValue(Context context, String propName, long defaultValue) {
                    long value;
                    try {
                        Cursor cursor = context.getContentResolver().query(Vine.Settings.CONTENT_URI, VALUE_PROJECTION, VineDatabaseHelper.SETTINGS_NAME_SELECTION, new String[]{propName}, null);
/* 81 */                value = defaultValue;
/* 82 */                if (cursor != null) {
/* 83 */                    if (cursor.moveToFirst()) {
/* 84 */                        value = cursor.getLong(0);
                            }
/* 86 */                    cursor.close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return value;
                }

/* 99 */        private static synchronized String getValue(Context context, String propName, String defaultValue) {
                    String value;
                    try {
                        Cursor cursor = context.getContentResolver().query(Vine.Settings.CONTENT_URI, VALUE_PROJECTION, VineDatabaseHelper.SETTINGS_NAME_SELECTION, new String[]{propName}, null);
/* 103 */               value = defaultValue;
/* 104 */               if (cursor != null) {
/* 105 */                   if (cursor.moveToFirst()) {
/* 106 */                       value = cursor.getString(0);
                            }
/* 108 */                   cursor.close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return value;
                }

/* 120 */       private static void setValue(Context context, String propName, String value) {
/* 121 */           ContentValues values = new ContentValues();
/* 122 */           values.put(Vine.Settings.PROP_VALUE, value);
/* 123 */           VineDatabaseHelper.getDatabaseHelper(context).saveUserValue(propName, values);
                }

/* 126 */       private static void setValue(Context context, String propName, long value) {
/* 127 */           ContentValues values = new ContentValues();
/* 128 */           values.put(Vine.Settings.PROP_VALUE, Long.valueOf(value));
/* 129 */           VineDatabaseHelper.getDatabaseHelper(context).saveUserValue(propName, values);
                }

/* 132 */       public static String getEdition(Context context) {
/* 133 */           SharedPreferences pr = Util.getDefaultSharedPrefs(context);
/* 134 */           return pr.getString(Settings.PREF_EDITION, null);
                }
            }
