            package co.vine.android;

            import android.accounts.AbstractAccountAuthenticator;
            import android.accounts.Account;
            import android.accounts.AccountAuthenticatorResponse;
            import android.accounts.AccountManager;
            import android.accounts.NetworkErrorException;
            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import co.vine.android.client.VineAccountHelper;
            
            
    public class VineAccountAuthenticator extends AbstractAccountAuthenticator {
                private final Context mContext;

        public VineAccountAuthenticator(Context context) {
            super(context);
            this.mContext = context;
                }

                @Override 
        public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
            Bundle b = new Bundle();
            b.putParcelable("intent", new Intent(this.mContext, (Class<?>) LoginActivity.class).putExtra("accountAuthenticatorResponse", response));
            return b;
                }

                @Override 
        public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
            throw new UnsupportedOperationException();
                }

                @Override 
        public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
            Intent intent = new Intent(this.mContext, (Class<?>) AuthenticatorActivity.class);
            intent.putExtra("username", account.name);
            intent.putExtra(AuthenticatorActivity.PARAM_CONFIRM_CREDENTIALS, true);
            intent.putExtra("accountAuthenticatorResponse", response);
            Bundle bundle = new Bundle();
            bundle.putParcelable("intent", intent);
            return bundle;
                }

                @Override 
        public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle loginOptions) {
            Intent intent = new Intent(this.mContext, (Class<?>) AuthenticatorActivity.class);
            intent.putExtra("username", account.name);
            intent.putExtra(AuthenticatorActivity.PARAM_AUTH_TOKEN_TYPE, authTokenType);
            intent.putExtra(AuthenticatorActivity.PARAM_CONFIRM_CREDENTIALS, false);
            intent.putExtra("accountAuthenticatorResponse", response);
            Bundle bundle = new Bundle();
            bundle.putParcelable("intent", intent);
            return bundle;
                }

                @Override 
        public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle loginOptions) throws NetworkErrorException {
            if (!authTokenType.equals(VineAccountHelper.SESSION_TOKEN_KEY)) {
                Bundle result = new Bundle();
                result.putString("errorMessage", "invalid authTokenType");
               return result;
                    }
            AccountManager am = AccountManager.get(this.mContext);
            String token = am.peekAuthToken(account, authTokenType);
            if (token != null) {
                Bundle result2 = new Bundle();
               result2.putString("authAccount", account.name);
               result2.putString("accountType", VineAccountHelper.ACCOUNT_TYPE);
               result2.putString("authtoken", token);
               return result2;
                    }
           Intent intent = new Intent(this.mContext, (Class<?>) AuthenticatorActivity.class);
           intent.putExtra("accountAuthenticatorResponse", response);
           intent.putExtra("username", account.name);
           intent.putExtra(AuthenticatorActivity.PARAM_AUTH_TOKEN_TYPE, authTokenType);
           Bundle bundle = new Bundle();
           bundle.putParcelable("intent", intent);
                    return bundle;
                }

                @Override 
       public String getAuthTokenLabel(String authTokenType) {
           return this.mContext.getString(C0257R.string.app_name);
                }

                @Override 
       public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
           Bundle result = new Bundle();
           result.putBoolean("booleanResult", false);
           return result;
                }
            }
