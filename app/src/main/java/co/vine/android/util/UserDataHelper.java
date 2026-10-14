            package co.vine.android.util;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.content.Context;
            import android.support.annotation.NonNull;
            import android.support.annotation.Nullable;
            
            
    public class UserDataHelper {
                private final Context mContext;

        public UserDataHelper(Context context) {
            this.mContext = context;
                }

        public boolean getBoolean(@Nullable Account account, String key, boolean defaultValue) {
                    String data;
                    return (account == null || (data = m4am().getUserData(account, key)) == null) ? defaultValue : Boolean.parseBoolean(data);
                }

        public void setBoolean(@NonNull Account account, String key, boolean value) {
            m4am().setUserData(account, key, Boolean.toString(value));
                }

        public long getLong(@Nullable Account account, String key, long defaultValue) {
                    String data;
            if (account != null && (data = m4am().getUserData(account, key)) != null) {
                return Long.parseLong(data);
                    }
            return defaultValue;
                }

        public void setLong(@NonNull Account account, String key, long value) {
            m4am().setUserData(account, key, Long.toString(value));
                }

                
        private AccountManager m4am() {
            return (AccountManager) this.mContext.getSystemService("account");
                }
            }
