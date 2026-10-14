            package com.google.android.gms.common;

            import android.accounts.Account;
            import android.content.Intent;
            import android.os.Bundle;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class AccountPicker {
                private AccountPicker() {
                }

                /* JADX INFO: renamed from: a */
                public static Intent m1008a(Account account, ArrayList<Account> arrayList, String[] strArr, boolean z, String str, String str2, String[] strArr2, Bundle bundle, boolean z2) {
                    return m1009a(account, arrayList, strArr, z, str, str2, strArr2, bundle, z2, 0);
                }

                /* JADX INFO: renamed from: a */
                public static Intent m1009a(Account account, ArrayList<Account> arrayList, String[] strArr, boolean z, String str, String str2, String[] strArr2, Bundle bundle, boolean z2, int i) {
                    Intent intent = new Intent();
                    intent.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
                    intent.putExtra("allowableAccounts", arrayList);
                    intent.putExtra("allowableAccountTypes", strArr);
                    intent.putExtra("addAccountOptions", bundle);
                    intent.putExtra("selectedAccount", account);
                    intent.putExtra("alwaysPromptForAccount", z);
                    intent.putExtra("descriptionTextOverride", str);
                    intent.putExtra("authTokenType", str2);
                    intent.putExtra("addAccountRequiredFeatures", strArr2);
                    intent.putExtra("setGmsCoreAccount", z2);
                    intent.putExtra("overrideTheme", i);
                    return intent;
                }

                public static Intent newChooseAccountIntent(Account selectedAccount, ArrayList<Account> allowableAccounts, String[] allowableAccountTypes, boolean alwaysPromptForAccount, String descriptionOverrideText, String addAccountAuthTokenType, String[] addAccountRequiredFeatures, Bundle addAccountOptions) {
                    return m1008a(selectedAccount, allowableAccounts, allowableAccountTypes, alwaysPromptForAccount, descriptionOverrideText, addAccountAuthTokenType, addAccountRequiredFeatures, addAccountOptions, false);
                }
            }
