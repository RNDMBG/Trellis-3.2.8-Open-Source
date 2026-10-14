            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import co.vine.android.client.AppController;
            import co.vine.android.util.analytics.FlurryUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class ConfirmationFlowActivity extends BaseActionBarActivity {
                public static final int DIALOG_VERIFY_PHONE = 1;
                public static final String EXTRA_FROM_SIGN_UP = "from_sign_up";
                public static final String EXTRA_PHONE_NUMBER = "phone_number";
                public static final int RESULT_VERIFY_PHONE_FAILURE = 1528;
                public static final int RESULT_VERIFY_PHONE_SUCCESS = 1527;
                private static final String TAG = "confirmationFlow";

/* 26 */        public static Intent getIntent(Context c, String phone, boolean fromSignup) {
/* 27 */            Intent i = new Intent(c, (Class<?>) ConfirmationFlowActivity.class);
/* 28 */            i.putExtra("phone_number", phone);
/* 29 */            i.putExtra("from_sign_up", fromSignup);
/* 30 */            return i;
                }

/* 36 */        public static void requestPhoneVerification(AppController appController, String phone) {
/* 37 */            appController.requestPhoneVerification(appController.getActiveSession(), phone, appController.getActiveId());
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 42 */        public void onCreate(Bundle savedInstanceState) {
                    Fragment frag;
/* 43 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_full_layout, true, true);
/* 45 */            Intent intent = getIntent();
/* 46 */            boolean fromSignup = intent.getBooleanExtra("from_sign_up", false);
/* 47 */            FlurryUtils.trackNuxScreenDisplayed("verify_phone");
/* 49 */            if (fromSignup) {
/* 50 */                setupActionBar((Boolean) false, (Boolean) true, C0257R.string.login_sign_up, (Boolean) false, (Boolean) true);
                    } else {
/* 52 */                setupActionBar((Boolean) true, (Boolean) true, C0257R.string.confirm_phone_title, (Boolean) true, (Boolean) true);
                    }
/* 55 */            if (savedInstanceState == null) {
/* 57 */                if (fromSignup) {
/* 58 */                    frag = new ConfirmationFlowVerificationFragment();
                        } else {
/* 60 */                    frag = new ConfirmationFlowEntryFragment();
                        }
/* 62 */                Bundle args = BaseFragment.prepareArguments(intent);
/* 63 */                args.putString("phone_number", intent.getStringExtra("phone_number"));
/* 65 */                frag.setArguments(args);
/* 67 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, frag, TAG).commit();
                    }
                }
            }
