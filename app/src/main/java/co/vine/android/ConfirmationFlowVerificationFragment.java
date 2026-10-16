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
            
            
    public class ConfirmationFlowVerificationFragment extends BaseControllerFragment implements View.OnClickListener {
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

        static  void access$100(ConfirmationFlowVerificationFragment x0, boolean x1) {
            x0.toggleDoneAction(x1);
                }

        static  TextView access$1000(ConfirmationFlowVerificationFragment x0) {
            return x0.mErrorMessage;
                }

        static  void access$200(ConfirmationFlowVerificationFragment x0, boolean x1) {
            x0.toggleErrorMessage(x1);
                }

        static  View access$300(ConfirmationFlowVerificationFragment x0) {
            return x0.mSendAgain;
                }

        static  View access$400(ConfirmationFlowVerificationFragment x0) {
            return x0.mFadingIn;
                }

        static  View access$500(ConfirmationFlowVerificationFragment x0) {
            return x0.mFadingOut;
                }

        static  Runnable access$600(ConfirmationFlowVerificationFragment x0) {
            return x0.mSendAgainRunnable;
                }

        static  Handler access$700(ConfirmationFlowVerificationFragment x0) {
            return x0.mHandler;
                }

        static  boolean access$800(ConfirmationFlowVerificationFragment x0) {
            return x0.mSentAgain;
                }

        static  void access$900(ConfirmationFlowVerificationFragment x0, boolean x1) {
            x0.showProgressBar(x1);
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mAppSessionListener = new ConfirmationSessionListener(this, null);
            setHasOptionsMenu(true);
            this.mHandler = new Handler(Looper.getMainLooper());
            this.mFadeOutAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out_fast);
            this.mFadeOutAnimation.setAnimationListener(this.mFadeListener);
            this.mFadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_fast);
            this.mFadeInAnimation.setAnimationListener(this.mFadeListener);
            Bundle args = getArguments();
            if (args != null) {
                this.mPhone = args.getString("phone_number", "");
                this.mFromSignup = args.getBoolean("from_sign_up", false);
                    }
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (!this.mFromSignup && !TextUtils.isEmpty(this.mPhone)) {
                ConfirmationFlowActivity.requestPhoneVerification(this.mAppController, this.mPhone);
                    }
                }

                @Override 
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.confirm_phone_verify, container, false);
            if (v == null) {
                return null;
                    }
            this.mMessage = (TextView) v.findViewById(R.id.confirm_phone_message);
            this.mMessage.setText(getString(R.string.confirm_phone_message, this.mPhone));
            if (this.mFromSignup) {
                View skip = v.findViewById(R.id.skip);
                skip.setVisibility(0);
                skip.setOnClickListener(this);
                    }
            EditText confirmationBox = (EditText) v.findViewById(R.id.phone_number_box);
            confirmationBox.addTextChangedListener(new C01681(confirmationBox));
           this.mConfirmationBox = confirmationBox;
           this.mErrorMessage = (TextView) v.findViewById(R.id.incorrect_code_message);
           this.mSendAgain = v.findViewById(R.id.confirm_send_again);
           this.mProgressBar = (ProgressBar) v.findViewById(R.id.submit_code_in_progress);
           return v;
                }

                
        class C01681 implements TextWatcher {
                    final  EditText val$confirmationBox;

            C01681(EditText editText) {
                        this.val$confirmationBox = editText;
                    }

                    @Override 
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override 
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (!TextUtils.isEmpty(this.val$confirmationBox.getText())) {
                   ConfirmationFlowVerificationFragment.access$100(ConfirmationFlowVerificationFragment.this, true);
                        } else {
                   ConfirmationFlowVerificationFragment.access$100(ConfirmationFlowVerificationFragment.this, false);
                        }
                    }

                    @Override 
           public void afterTextChanged(Editable s) {
                    }
                }

       private void toggleDoneAction(boolean enabled) {
           if (this.mDone != null) {
               this.mDone.setEnabled(enabled);
                    }
                }

                @Override 
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           inflater.inflate(R.menu.done, menu);
           this.mDone = menu.findItem(R.id.done);
           super.onCreateOptionsMenu(menu, inflater);
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           if (item.getItemId() == R.id.done) {
               Editable e = this.mConfirmationBox.getText();
               if (e == null) {
                            return false;
                        }
               showProgressBar(true);
               item.setEnabled(false);
               this.mAppController.verifyPhoneNumber(this.mAppController.getActiveSession(), e.toString());
                        return true;
                    }
           return super.onOptionsItemSelected(item);
                }

       private void showProgressBar(boolean show) {
           if (this.mProgressBar != null) {
               this.mProgressBar.setVisibility(show ? 0 : 8);
                    }
                }

                @Override 
       public void onClick(View v) {
           int id = v.getId();
           if (id == R.id.confirm_send_again) {
               this.mSentAgain = true;
               ConfirmationFlowActivity.requestPhoneVerification(this.mAppController, this.mPhone);
               this.mHandler.postDelayed(this.mShowMessageRunnable, 500L);
               this.mSendAgain.setOnClickListener(null);
               return;
                    }
           if (id == R.id.skip) {
               startActivity(new Intent(getActivity(), (Class<?>) ChannelsListNUXActivity.class));
                    }
                }

                
       class RunnableC01692 implements Runnable {
           RunnableC01692() {
                    }

                    @Override 
           public void run() {
               ConfirmationFlowVerificationFragment.access$200(ConfirmationFlowVerificationFragment.this, false);
                    }
                }

       private void toggleErrorMessage(boolean show) {
           if (this.mMessage != null && this.mErrorMessage != null) {
               clearTextViewAnimations();
               if (show && this.mErrorMessage.getVisibility() != 0) {
                   this.mMessage.startAnimation(this.mFadeOutAnimation);
                   this.mErrorMessage.startAnimation(this.mFadeInAnimation);
                   this.mFadingIn = this.mErrorMessage;
                   this.mFadingOut = this.mMessage;
                   return;
                        }
               if (!show && this.mMessage.getVisibility() != 0) {
                   this.mMessage.startAnimation(this.mFadeInAnimation);
                   this.mErrorMessage.startAnimation(this.mFadeOutAnimation);
                   this.mFadingIn = this.mMessage;
                   this.mFadingOut = this.mErrorMessage;
                        }
                    }
                }

       private void clearTextViewAnimations() {
           this.mMessage.clearAnimation();
           this.mErrorMessage.clearAnimation();
                }

                
       class RunnableC01703 implements Runnable {
           RunnableC01703() {
                    }

                    @Override 
           public void run() {
               ConfirmationFlowVerificationFragment.access$300(ConfirmationFlowVerificationFragment.this).setOnClickListener(ConfirmationFlowVerificationFragment.this);
                    }
                }

                
       class AnimationAnimationListenerC01714 implements Animation.AnimationListener {
           AnimationAnimationListenerC01714() {
                    }

                    @Override 
           public void onAnimationStart(Animation animation) {
                    }

                    @Override 
           public void onAnimationEnd(Animation animation) {
               if (ConfirmationFlowVerificationFragment.access$400(ConfirmationFlowVerificationFragment.this) != null) {
                   ConfirmationFlowVerificationFragment.access$400(ConfirmationFlowVerificationFragment.this).setVisibility(0);
                        }
               if (ConfirmationFlowVerificationFragment.access$500(ConfirmationFlowVerificationFragment.this) != null) {
                   ConfirmationFlowVerificationFragment.access$500(ConfirmationFlowVerificationFragment.this).setVisibility(4);
                        }
                    }

                    @Override 
           public void onAnimationRepeat(Animation animation) {
                    }
                }

                @Override 
       public void onResume() {
           super.onResume();
           this.mSendAgain.setOnClickListener(this);
                }

                @Override 
       public void onPause() {
           super.onPause();
           this.mHandler.removeCallbacks(this.mShowMessageRunnable);
           this.mHandler.removeCallbacks(this.mSendAgainRunnable);
           Util.setSoftKeyboardVisibility(getActivity(), this.mConfirmationBox, false);
                }

       private class ConfirmationSessionListener extends AppSessionListener {
           private ConfirmationSessionListener() {
                    }

            ConfirmationSessionListener(ConfirmationFlowVerificationFragment x0, C01681 x1) {
               this();
                    }

                    @Override 
           public void onRequestPhoneVerificationComplete(String reqId, int statusCode, String reasonPhrase, String phone) {
               Activity activity = ConfirmationFlowVerificationFragment.this.getActivity();
               ConfirmationFlowVerificationFragment.access$700(ConfirmationFlowVerificationFragment.this).postDelayed(ConfirmationFlowVerificationFragment.access$600(ConfirmationFlowVerificationFragment.this), ConfirmationFlowVerificationFragment.SEND_AGAIN_DELAY_MS);
               if (statusCode != 200 || !ConfirmationFlowVerificationFragment.access$800(ConfirmationFlowVerificationFragment.this)) {
                   if (!TextUtils.isEmpty(reasonPhrase)) {
                       Util.showCenteredToast(activity, reasonPhrase);
                       return;
                            }
                   return;
                        }
                        Util.showCenteredToast(activity, ConfirmationFlowVerificationFragment.this.getString(R.string.confirm_phone_again, phone));
                    }

                    @Override 
           public void onVerifyPhoneNumberComplete(String reqId, int statusCode, String reasonPhrase) {
               Activity activity = ConfirmationFlowVerificationFragment.this.getActivity();
               ConfirmationFlowVerificationFragment.access$900(ConfirmationFlowVerificationFragment.this, false);
               ConfirmationFlowVerificationFragment.access$100(ConfirmationFlowVerificationFragment.this, true);
               if (statusCode == 200) {
                   Util.showCenteredToast(activity, R.string.confirm_phone_success);
                   ConfirmationFlowVerificationFragment.this.getActivity().setResult(ConfirmationFlowActivity.RESULT_VERIFY_PHONE_SUCCESS);
                   ConfirmationFlowVerificationFragment.this.getActivity().finish();
                        } else {
                   ConfirmationFlowVerificationFragment.access$200(ConfirmationFlowVerificationFragment.this, true);
                   if (!TextUtils.isEmpty(reasonPhrase)) {
                       ConfirmationFlowVerificationFragment.access$1000(ConfirmationFlowVerificationFragment.this).setText(reasonPhrase);
                            } else {
                       ConfirmationFlowVerificationFragment.access$1000(ConfirmationFlowVerificationFragment.this).setText(R.string.confirm_phone_error);
                            }
                        }
                    }
                }
            }
