            package co.vine.android.util;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.content.Context;
            import android.support.annotation.NonNull;
            import android.support.annotation.Nullable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class UserDataHelper {
                private final Context mContext;

/* 15 */        public UserDataHelper(Context context) {
/* 17 */            this.mContext = context;
                }

/* 21 */        public boolean getBoolean(@Nullable Account account, String key, boolean defaultValue) {
                    String data;
                    return (account == null || (data = m4am().getUserData(account, key)) == null) ? defaultValue : Boolean.parseBoolean(data);
                }

/* 33 */        public void setBoolean(@NonNull Account account, String key, boolean value) {
/* 34 */            m4am().setUserData(account, key, Boolean.toString(value));
                }

/* 38 */        public long getLong(@Nullable Account account, String key, long defaultValue) {
                    String data;
/* 39 */            if (account != null && (data = m4am().getUserData(account, key)) != null) {
/* 44 */                return Long.parseLong(data);
                    }
/* 46 */            return defaultValue;
                }

/* 51 */        public void setLong(@NonNull Account account, String key, long value) {
/* 52 */            m4am().setUserData(account, key, Long.toString(value));
                }

                /* JADX INFO: renamed from: am */
/* 55 */        private AccountManager m4am() {
/* 56 */            return (AccountManager) this.mContext.getSystemService("account");
                }
            }
