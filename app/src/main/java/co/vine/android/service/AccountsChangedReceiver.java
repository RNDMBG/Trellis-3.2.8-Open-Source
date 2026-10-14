            package co.vine.android.service;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import co.vine.android.client.AppController;
            import co.vine.android.client.VineAccountHelper;
            
            
    public class AccountsChangedReceiver extends BroadcastReceiver {
                @Override 
        public void onReceive(Context context, Intent intent) {
            AccountManager manager = AccountManager.get(context);
            Account[] accounts = manager.getAccountsByType(VineAccountHelper.ACCOUNT_TYPE);
            AppController appController = AppController.getInstance(context);
            if (accounts.length <= 0) {
                appController.logout();
                    }
                }
            }
