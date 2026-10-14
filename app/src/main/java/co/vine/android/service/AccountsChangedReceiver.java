            package co.vine.android.service;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import co.vine.android.client.AppController;
            import co.vine.android.client.VineAccountHelper;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class AccountsChangedReceiver extends BroadcastReceiver {
                @Override // android.content.BroadcastReceiver
/* 19 */        public void onReceive(Context context, Intent intent) {
/* 20 */            AccountManager manager = AccountManager.get(context);
/* 21 */            Account[] accounts = manager.getAccountsByType(VineAccountHelper.ACCOUNT_TYPE);
/* 22 */            AppController appController = AppController.getInstance(context);
/* 23 */            if (accounts.length <= 0) {
/* 26 */                appController.logout();
                    }
                }
            }
