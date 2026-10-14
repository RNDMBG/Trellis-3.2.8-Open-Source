            package co.vine.android;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            import android.text.Editable;
            import android.text.TextUtils;
            import android.text.TextWatcher;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import android.view.animation.AnimationUtils;
            import android.widget.EditText;
            import android.widget.ProgressBar;
            import android.widget.TextView;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.util.Util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class ConfirmationFlowVerificationFragment extends BaseControllerFragment implements View.OnClickListener {
                public static final String ARG_FROM_SIGNUP = "from_sign_up";
                public static final String ARG_PHONE_NUMBER = "phone_number";
                private static final long SEND_AGAIN_DELAY_MS = 10000;
                private static final long TRANSITION_DELAY_MS = 500;
                private EditText mConfirmationBox;
                private MenuItem mDone;
                private TextView mErrorMessage;
                private Animation mFadeInAnimation;
                private Animation mFadeOutAnimation;
                private View mFadingIn;
                private View mFadingOut;
                private boolean mFromSignup;
                private Handler mHandler;
                private TextView mMessage;
                private String mPhone;
                private ProgressBar mProgressBar;
                private View mSendAgain;
                private boolean mSentAgain;
                private Runnable mShowMessageRunnable = new RunnableC01692();
                private Runnable mSendAgainRunnable = new RunnableC01703();
                private Animation.AnimationListener mFadeListener = new AnimationAnimationListenerC01714();

/* 28 */        static /* synthetic */ void access$100(ConfirmationFlowVerificationFragment x0, boolean x1) {
/* 29 */            x0.toggleDoneAction(x1);
                }

/* 28 */        static /* synthetic */ TextView access$1000(ConfirmationFlowVerificationFragment x0) {
/* 29 */            return x0.mErrorMessage;
                }

/* 28 */        static /* synthetic */ void access$200(ConfirmationFlowVerificationFragment x0, boolean x1) {
/* 29 */            x0.toggleErrorMessage(x1);
                }

/* 28 */        static /* synthetic */ View access$300(ConfirmationFlowVerificationFragment x0) {
/* 29 */            return x0.mSendAgain;
                }

/* 28 */        static /* synthetic */ View access$400(ConfirmationFlowVerificationFragment x0) {
/* 29 */            return x0.mFadingIn;
                }

/* 28 */        static /* synthetic */ View access$500(ConfirmationFlowVerificationFragment x0) {
/* 29 */            return x0.mFadingOut;
                }

/* 28 */        static /* synthetic */ Runnable access$600(ConfirmationFlowVerificationFragment x0) {
/* 29 */            return x0.mSendAgainRunnable;
                }

/* 28 */        static /* synthetic */ Handler access$700(ConfirmationFlowVerificationFragment x0) {
/* 29 */            return x0.mHandler;
                }

/* 28 */        static /* synthetic */ boolean access$800(ConfirmationFlowVerificationFragment x0) {
/* 29 */            return x0.mSentAgain;
                }

/* 28 */        static /* synthetic */ void access$900(ConfirmationFlowVerificationFragment x0, boolean x1) {
/* 29 */            x0.showProgressBar(x1);
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 54 */        public void onCreate(Bundle savedInstanceState) {
/* 55 */            super.onCreate(savedInstanceState);
/* 56 */            this.mAppSessionListener = new ConfirmationSessionListener(this, null);
/* 57 */            setHasOptionsMenu(true);
/* 58 */            this.mHandler = new Handler(Looper.getMainLooper());
/* 59 */            this.mFadeOutAnimation = AnimationUtils.loadAnimation(getActivity(), C0257R.anim.fade_out_fast);
/* 60 */            this.mFadeOutAnimation.setAnimationListener(this.mFadeListener);
/* 61 */            this.mFadeInAnimation = AnimationUtils.loadAnimation(getActivity(), C0257R.anim.fade_in_fast);
/* 62 */            this.mFadeInAnimation.setAnimationListener(this.mFadeListener);
/* 64 */            Bundle args = getArguments();
/* 65 */            if (args != null) {
/* 66 */                this.mPhone = args.getString("phone_number", "");
/* 67 */                this.mFromSignup = args.getBoolean("from_sign_up", false);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 72 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 73 */            super.onActivityCreated(savedInstanceState);
/* 75 */            if (!this.mFromSignup && !TextUtils.isEmpty(this.mPhone)) {
/* 76 */                ConfirmationFlowActivity.requestPhoneVerification(this.mAppController, this.mPhone);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 81 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 82 */            View v = inflater.inflate(C0257R.layout.confirm_phone_verify, container, false);
/* 83 */            if (v == null) {
/* 84 */                return null;
                    }
/* 87 */            this.mMessage = (TextView) v.findViewById(C0257R.id.confirm_phone_message);
/* 88 */            this.mMessage.setText(getString(C0257R.string.confirm_phone_message, this.mPhone));
/* 90 */            if (this.mFromSignup) {
/* 91 */                View skip = v.findViewById(C0257R.id.skip);
/* 92 */                skip.setVisibility(0);
/* 93 */                skip.setOnClickListener(this);
                    }
/* 96 */            EditText confirmationBox = (EditText) v.findViewById(C0257R.id.phone_number_box);
/* 97 */            confirmationBox.addTextChangedListener(new C01681(confirmationBox));
/* 115 */           this.mConfirmationBox = confirmationBox;
/* 116 */           this.mErrorMessage = (TextView) v.findViewById(C0257R.id.incorrect_code_message);
/* 118 */           this.mSendAgain = v.findViewById(C0257R.id.confirm_send_again);
/* 119 */           this.mProgressBar = (ProgressBar) v.findViewById(C0257R.id.submit_code_in_progress);
/* 121 */           return v;
                }

                /* JADX INFO: renamed from: co.vine.android.ConfirmationFlowVerificationFragment$1 */
/* 95 */        class C01681 implements TextWatcher {
                    final /* synthetic */ EditText val$confirmationBox;

/* 96 */            C01681(EditText editText) {
                        this.val$confirmationBox = editText;
                    }

                    @Override // android.text.TextWatcher
/* 99 */            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
/* 103 */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 104 */               if (!TextUtils.isEmpty(this.val$confirmationBox.getText())) {
/* 105 */                   ConfirmationFlowVerificationFragment.access$100(ConfirmationFlowVerificationFragment.this, true);
                        } else {
/* 107 */                   ConfirmationFlowVerificationFragment.access$100(ConfirmationFlowVerificationFragment.this, false);
                        }
                    }

                    @Override // android.text.TextWatcher
/* 112 */           public void afterTextChanged(Editable s) {
                    }
                }

/* 124 */       private void toggleDoneAction(boolean enabled) {
/* 125 */           if (this.mDone != null) {
/* 126 */               this.mDone.setEnabled(enabled);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 131 */       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 132 */           inflater.inflate(C0257R.menu.done, menu);
/* 133 */           this.mDone = menu.findItem(C0257R.id.done);
/* 134 */           super.onCreateOptionsMenu(menu, inflater);
                }

                @Override // android.support.v4.app.Fragment
/* 138 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 139 */           if (item.getItemId() == 2131427939) {
/* 140 */               Editable e = this.mConfirmationBox.getText();
/* 141 */               if (e == null) {
                            return false;
                        }
/* 144 */               showProgressBar(true);
/* 145 */               item.setEnabled(false);
/* 146 */               this.mAppController.verifyPhoneNumber(this.mAppController.getActiveSession(), e.toString());
                        return true;
                    }
/* 149 */           return super.onOptionsItemSelected(item);
                }

/* 153 */       private void showProgressBar(boolean show) {
/* 154 */           if (this.mProgressBar != null) {
/* 155 */               this.mProgressBar.setVisibility(show ? 0 : 8);
                    }
                }

                @Override // android.view.View.OnClickListener
/* 160 */       public void onClick(View v) {
/* 161 */           int id = v.getId();
/* 162 */           if (id == 2131427455) {
/* 163 */               this.mSentAgain = true;
/* 164 */               ConfirmationFlowActivity.requestPhoneVerification(this.mAppController, this.mPhone);
/* 165 */               this.mHandler.postDelayed(this.mShowMessageRunnable, 500L);
/* 166 */               this.mSendAgain.setOnClickListener(null);
/* 170 */               return;
                    }
/* 167 */           if (id == 2131427456) {
/* 168 */               startActivity(new Intent(getActivity(), (Class<?>) ChannelsListNUXActivity.class));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ConfirmationFlowVerificationFragment$2 */
/* 170 */       class RunnableC01692 implements Runnable {
/* 171 */           RunnableC01692() {
                    }

                    @Override // java.lang.Runnable
/* 174 */           public void run() {
/* 175 */               ConfirmationFlowVerificationFragment.access$200(ConfirmationFlowVerificationFragment.this, false);
                    }
                }

/* 179 */       private void toggleErrorMessage(boolean show) {
/* 180 */           if (this.mMessage != null && this.mErrorMessage != null) {
/* 183 */               clearTextViewAnimations();
/* 184 */               if (show && this.mErrorMessage.getVisibility() != 0) {
/* 185 */                   this.mMessage.startAnimation(this.mFadeOutAnimation);
/* 186 */                   this.mErrorMessage.startAnimation(this.mFadeInAnimation);
/* 187 */                   this.mFadingIn = this.mErrorMessage;
/* 188 */                   this.mFadingOut = this.mMessage;
/* 195 */                   return;
                        }
/* 189 */               if (!show && this.mMessage.getVisibility() != 0) {
/* 190 */                   this.mMessage.startAnimation(this.mFadeInAnimation);
/* 191 */                   this.mErrorMessage.startAnimation(this.mFadeOutAnimation);
/* 192 */                   this.mFadingIn = this.mMessage;
/* 193 */                   this.mFadingOut = this.mErrorMessage;
                        }
                    }
                }

/* 197 */       private void clearTextViewAnimations() {
/* 198 */           this.mMessage.clearAnimation();
/* 199 */           this.mErrorMessage.clearAnimation();
                }

                /* JADX INFO: renamed from: co.vine.android.ConfirmationFlowVerificationFragment$3 */
/* 200 */       class RunnableC01703 implements Runnable {
/* 201 */           RunnableC01703() {
                    }

                    @Override // java.lang.Runnable
/* 204 */           public void run() {
/* 205 */               ConfirmationFlowVerificationFragment.access$300(ConfirmationFlowVerificationFragment.this).setOnClickListener(ConfirmationFlowVerificationFragment.this);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ConfirmationFlowVerificationFragment$4 */
/* 207 */       class AnimationAnimationListenerC01714 implements Animation.AnimationListener {
/* 208 */           AnimationAnimationListenerC01714() {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 211 */           public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 215 */           public void onAnimationEnd(Animation animation) {
/* 216 */               if (ConfirmationFlowVerificationFragment.access$400(ConfirmationFlowVerificationFragment.this) != null) {
/* 217 */                   ConfirmationFlowVerificationFragment.access$400(ConfirmationFlowVerificationFragment.this).setVisibility(0);
                        }
/* 219 */               if (ConfirmationFlowVerificationFragment.access$500(ConfirmationFlowVerificationFragment.this) != null) {
/* 220 */                   ConfirmationFlowVerificationFragment.access$500(ConfirmationFlowVerificationFragment.this).setVisibility(4);
                        }
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 225 */           public void onAnimationRepeat(Animation animation) {
                    }
                }

                @Override // co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 230 */       public void onResume() {
/* 231 */           super.onResume();
/* 232 */           this.mSendAgain.setOnClickListener(this);
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 236 */       public void onPause() {
/* 237 */           super.onPause();
/* 238 */           this.mHandler.removeCallbacks(this.mShowMessageRunnable);
/* 239 */           this.mHandler.removeCallbacks(this.mSendAgainRunnable);
/* 240 */           Util.setSoftKeyboardVisibility(getActivity(), this.mConfirmationBox, false);
                }

/* 241 */       private class ConfirmationSessionListener extends AppSessionListener {
/* 242 */           private ConfirmationSessionListener() {
                    }

/* 242 */           /* synthetic */ ConfirmationSessionListener(ConfirmationFlowVerificationFragment x0, C01681 x1) {
/* 243 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 246 */           public void onRequestPhoneVerificationComplete(String reqId, int statusCode, String reasonPhrase, String phone) {
/* 247 */               Activity activity = ConfirmationFlowVerificationFragment.this.getActivity();
/* 248 */               ConfirmationFlowVerificationFragment.access$700(ConfirmationFlowVerificationFragment.this).postDelayed(ConfirmationFlowVerificationFragment.access$600(ConfirmationFlowVerificationFragment.this), ConfirmationFlowVerificationFragment.SEND_AGAIN_DELAY_MS);
/* 249 */               if (statusCode != 200 || !ConfirmationFlowVerificationFragment.access$800(ConfirmationFlowVerificationFragment.this)) {
/* 252 */                   if (!TextUtils.isEmpty(reasonPhrase)) {
/* 253 */                       Util.showCenteredToast(activity, reasonPhrase);
/* 256 */                       return;
                            }
/* 256 */                   return;
                        }
                        Util.showCenteredToast(activity, ConfirmationFlowVerificationFragment.this.getString(C0257R.string.confirm_phone_again, phone));
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 259 */           public void onVerifyPhoneNumberComplete(String reqId, int statusCode, String reasonPhrase) {
/* 260 */               Activity activity = ConfirmationFlowVerificationFragment.this.getActivity();
/* 261 */               ConfirmationFlowVerificationFragment.access$900(ConfirmationFlowVerificationFragment.this, false);
/* 262 */               ConfirmationFlowVerificationFragment.access$100(ConfirmationFlowVerificationFragment.this, true);
/* 263 */               if (statusCode == 200) {
/* 264 */                   Util.showCenteredToast(activity, C0257R.string.confirm_phone_success);
/* 265 */                   ConfirmationFlowVerificationFragment.this.getActivity().setResult(ConfirmationFlowActivity.RESULT_VERIFY_PHONE_SUCCESS);
/* 266 */                   ConfirmationFlowVerificationFragment.this.getActivity().finish();
                        } else {
/* 268 */                   ConfirmationFlowVerificationFragment.access$200(ConfirmationFlowVerificationFragment.this, true);
/* 269 */                   if (!TextUtils.isEmpty(reasonPhrase)) {
/* 270 */                       ConfirmationFlowVerificationFragment.access$1000(ConfirmationFlowVerificationFragment.this).setText(reasonPhrase);
                            } else {
/* 272 */                       ConfirmationFlowVerificationFragment.access$1000(ConfirmationFlowVerificationFragment.this).setText(C0257R.string.confirm_phone_error);
                            }
                        }
                    }
                }
            }
