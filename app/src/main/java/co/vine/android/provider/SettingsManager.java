            package co.vine.android.provider;

            import android.content.ContentValues;
            import android.content.Context;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import co.vine.android.Settings;
            import co.vine.android.util.Util;
            
            
    public class SettingsManager {
                private static final String CURRENT_ACCOUNT_KEY = "current_account";
                private static final String CURRENT_ACCOUNT_NAME = "current_name";
                private static final String CURRENT_USER_ID_KEY = "current_user_id";
                private static final String[] VALUE_PROJECTION = {Vine.Settings.PROP_VALUE};

        private SettingsManager() {
                }

        public static void setCurrentAccount(Context context, String emailLogin, String name, long userId) {
            setValue(context, CURRENT_ACCOUNT_KEY, emailLogin);
            setValue(context, CURRENT_USER_ID_KEY, userId);
            setValue(context, CURRENT_ACCOUNT_NAME, name);
                }

        public static String getCurrentAccount(Context context) {
            return getValue(context, CURRENT_ACCOUNT_KEY, (String) null);
                }

        public static String getCurrentAccountName(Context context) {
            return getValue(context, CURRENT_ACCOUNT_NAME, (String) null);
                }

        public static long getCurrentUserId(Context context) {
            return getValue(context, CURRENT_USER_ID_KEY, 0L);
                }

        private static synchronized long getValue(Context context, String propName, long defaultValue) {
                    long value;
                    try {
                        Cursor cursor = context.getContentResolver().query(Vine.Settings.CONTENT_URI, VALUE_PROJECTION, VineDatabaseHelper.SETTINGS_NAME_SELECTION, new String[]{propName}, null);
                value = defaultValue;
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        value = cursor.getLong(0);
                            }
                    cursor.close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return value;
                }

        private static synchronized String getValue(Context context, String propName, String defaultValue) {
                    String value;
                    try {
                        Cursor cursor = context.getContentResolver().query(Vine.Settings.CONTENT_URI, VALUE_PROJECTION, VineDatabaseHelper.SETTINGS_NAME_SELECTION, new String[]{propName}, null);
               value = defaultValue;
               if (cursor != null) {
                   if (cursor.moveToFirst()) {
                       value = cursor.getString(0);
                            }
                   cursor.close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return value;
                }

       private static void setValue(Context context, String propName, String value) {
           ContentValues values = new ContentValues();
           values.put(Vine.Settings.PROP_VALUE, value);
           VineDatabaseHelper.getDatabaseHelper(context).saveUserValue(propName, values);
                }

       private static void setValue(Context context, String propName, long value) {
           ContentValues values = new ContentValues();
           values.put(Vine.Settings.PROP_VALUE, Long.valueOf(value));
           VineDatabaseHelper.getDatabaseHelper(context).saveUserValue(propName, values);
                }

       public static String getEdition(Context context) {
           SharedPreferences pr = Util.getDefaultSharedPrefs(context);
           return pr.getString(Settings.PREF_EDITION, null);
                }
            }
