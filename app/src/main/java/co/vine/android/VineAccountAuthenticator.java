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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class VineAccountAuthenticator extends AbstractAccountAuthenticator {
                private final Context mContext;

/* 36 */        public VineAccountAuthenticator(Context context) {
/* 37 */            super(context);
/* 38 */            this.mContext = context;
                }

                @Override // android.accounts.AbstractAccountAuthenticator
/* 46 */        public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
/* 47 */            Bundle b = new Bundle();
/* 48 */            b.putParcelable("intent", new Intent(this.mContext, (Class<?>) LoginActivity.class).putExtra("accountAuthenticatorResponse", response));
/* 50 */            return b;
                }

                @Override // android.accounts.AbstractAccountAuthenticator
/* 54 */        public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
/* 55 */            throw new UnsupportedOperationException();
                }

                @Override // android.accounts.AbstractAccountAuthenticator
/* 61 */        public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
/* 62 */            Intent intent = new Intent(this.mContext, (Class<?>) AuthenticatorActivity.class);
/* 63 */            intent.putExtra("username", account.name);
/* 64 */            intent.putExtra(AuthenticatorActivity.PARAM_CONFIRM_CREDENTIALS, true);
/* 65 */            intent.putExtra("accountAuthenticatorResponse", response);
/* 67 */            Bundle bundle = new Bundle();
/* 68 */            bundle.putParcelable("intent", intent);
/* 69 */            return bundle;
                }

                @Override // android.accounts.AbstractAccountAuthenticator
/* 74 */        public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle loginOptions) {
/* 75 */            Intent intent = new Intent(this.mContext, (Class<?>) AuthenticatorActivity.class);
/* 76 */            intent.putExtra("username", account.name);
/* 77 */            intent.putExtra(AuthenticatorActivity.PARAM_AUTH_TOKEN_TYPE, authTokenType);
/* 78 */            intent.putExtra(AuthenticatorActivity.PARAM_CONFIRM_CREDENTIALS, false);
/* 79 */            intent.putExtra("accountAuthenticatorResponse", response);
/* 81 */            Bundle bundle = new Bundle();
/* 82 */            bundle.putParcelable("intent", intent);
/* 83 */            return bundle;
                }

                @Override // android.accounts.AbstractAccountAuthenticator
/* 89 */        public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle loginOptions) throws NetworkErrorException {
/* 90 */            if (!authTokenType.equals(VineAccountHelper.SESSION_TOKEN_KEY)) {
/* 91 */                Bundle result = new Bundle();
/* 92 */                result.putString("errorMessage", "invalid authTokenType");
/* 115 */               return result;
                    }
/* 96 */            AccountManager am = AccountManager.get(this.mContext);
/* 97 */            String token = am.peekAuthToken(account, authTokenType);
/* 98 */            if (token != null) {
/* 99 */                Bundle result2 = new Bundle();
/* 100 */               result2.putString("authAccount", account.name);
/* 101 */               result2.putString("accountType", VineAccountHelper.ACCOUNT_TYPE);
/* 102 */               result2.putString("authtoken", token);
/* 115 */               return result2;
                    }
/* 108 */           Intent intent = new Intent(this.mContext, (Class<?>) AuthenticatorActivity.class);
/* 109 */           intent.putExtra("accountAuthenticatorResponse", response);
/* 110 */           intent.putExtra("username", account.name);
/* 111 */           intent.putExtra(AuthenticatorActivity.PARAM_AUTH_TOKEN_TYPE, authTokenType);
/* 113 */           Bundle bundle = new Bundle();
/* 114 */           bundle.putParcelable("intent", intent);
                    return bundle;
                }

                @Override // android.accounts.AbstractAccountAuthenticator
/* 119 */       public String getAuthTokenLabel(String authTokenType) {
/* 120 */           return this.mContext.getString(C0257R.string.app_name);
                }

                @Override // android.accounts.AbstractAccountAuthenticator
/* 125 */       public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
/* 126 */           Bundle result = new Bundle();
/* 127 */           result.putBoolean("booleanResult", false);
/* 128 */           return result;
                }
            }
