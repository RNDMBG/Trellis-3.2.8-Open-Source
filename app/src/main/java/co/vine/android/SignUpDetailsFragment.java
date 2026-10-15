            package co.vine.android;

            import android.content.Intent;
            import android.content.res.Resources;
            import android.os.Bundle;
            import android.os.Handler;
            import android.support.v4.app.FragmentActivity;
            import android.text.Spannable;
            import android.text.Spanned;
            import android.text.TextUtils;
            import android.text.method.LinkMovementMethod;
            import android.text.style.StyleSpan;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.inputmethod.InputMethodManager;
            import android.widget.EditText;
            import android.widget.TextView;
            import co.vine.android.util.AuthenticationUtils;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.ContactsHelper;
            import co.vine.android.util.Util;
            import co.vine.android.widget.SpanClickListener;
            import co.vine.android.widget.StyledClickableSpan;
            
            
    public class SignUpDetailsFragment extends BaseFragment implements SpanClickListener, ContactsHelper.ContactHelperListener {
                private static final int SPAN_PRIVACY_POLICY = 1;
                private static final int SPAN_TOS = 2;
                private EditText mEmailAddress;
                private Handler mHandler;
                private EditText mPassword;
                private EditText mPhone;

        static  boolean access$000(SignUpDetailsFragment x0) {
            return x0.validateAndDisplayToastIfCredentialsInvalid();
                }

        static  EditText access$100(SignUpDetailsFragment x0) {
            return x0.mPhone;
                }

        static  boolean access$200(SignUpDetailsFragment x0) {
            return x0.onNextClicked();
                }

        static  EditText access$300(SignUpDetailsFragment x0) {
            return x0.mEmailAddress;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            this.mHandler = new Handler();
            ((SignUpPagerActivity) getActivity()).setBarTitle(R.string.login_sign_up);
                }

                @Override 
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            this.mEmailAddress = (EditText) view.findViewById(R.id.signup_email);
            this.mPassword = (EditText) view.findViewById(R.id.signup_password);
            this.mPhone = (EditText) view.findViewById(R.id.signup_phone);
            this.mPhone.setOnEditorActionListener(new C02861());
            TextView termsOfServiceView = (TextView) view.findViewById(R.id.tos_line_2);
            StyleSpan[] boldStyle = {new StyleSpan(1), new StyleSpan(1)};
            Spanned spanned = Util.getSpannedText(boldStyle, getString(R.string.sign_up_tos_line_2), CommonUtil.MARKER_QUOTES);
            termsOfServiceView.setText(spanned);
            termsOfServiceView.setMovementMethod(LinkMovementMethod.getInstance());
            Spannable clickSpannable = (Spannable) termsOfServiceView.getText();
            StyleSpan[] spans = (StyleSpan[]) spanned.getSpans(0, spanned.length(), StyleSpan.class);
            Resources resources = getResources();
            int start = spanned.getSpanStart(spans[0]);
            int end = spanned.getSpanEnd(spans[0]);
            StyledClickableSpan clickableSpan = new StyledClickableSpan(1, null, this);
            clickableSpan.setColor(resources.getColor(R.color.text_fineprint));
            Util.safeSetSpan(clickSpannable, clickableSpan, start, end, 33);
            int start2 = spanned.getSpanStart(spans[1]);
            int end2 = spanned.getSpanEnd(spans[1]);
            StyledClickableSpan clickableSpan2 = new StyledClickableSpan(2, null, this);
            clickableSpan2.setColor(resources.getColor(R.color.text_fineprint));
            Util.safeSetSpan(clickSpannable, clickableSpan2, start2, end2, 33);
            ContactsHelper.loadContacts(this, this);
                }

                
        class C02861 implements TextView.OnEditorActionListener {
            C02861() {
                    }

                    @Override 
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == 6 && SignUpDetailsFragment.access$000(SignUpDetailsFragment.this) && SignUpDetailsFragment.this.getActivity() != null) {
                    InputMethodManager imm = (InputMethodManager) SignUpDetailsFragment.this.getActivity().getSystemService("input_method");
                    imm.hideSoftInputFromWindow(SignUpDetailsFragment.access$100(SignUpDetailsFragment.this).getWindowToken(), 0);
                    SignUpDetailsFragment.access$200(SignUpDetailsFragment.this);
                    return true;
                        }
                return true;
                    }
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
           return inflater.inflate(R.layout.sign_up_flow_details, root, false);
                }

                @Override 
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           inflater.inflate(R.menu.done, menu);
           MenuItem doneButton = menu.findItem(R.id.done);
           doneButton.setEnabled(true);
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           if (item.getItemId() == 2131427939 && validateAndDisplayToastIfCredentialsInvalid()) {
               if (validateAndDisplayToastIfCredentialsInvalid()) {
                   return onNextClicked();
                        }
               return true;
                    }
           return super.onOptionsItemSelected(item);
                }

       private boolean onNextClicked() {
           Util.setSoftKeyboardVisibility(getActivity(), this.mEmailAddress, false);
           SignUpPagerActivity activity = (SignUpPagerActivity) getActivity();
           if (activity == null) {
                        return false;
                    }
           activity.setLogin(this.mEmailAddress.getText().toString());
           activity.setPassword(this.mPassword.getText().toString());
           activity.setPhone(this.mPhone.getText().toString());
           activity.toNextStep();
           return true;
                }

       private boolean validateAndDisplayToastIfCredentialsInvalid() {
           String emailAddress = this.mEmailAddress.getText().toString();
           if (emailAddress.isEmpty()) {
               Util.showCenteredToast(getActivity(), R.string.login_empty_email);
                        return false;
                    }
           if (!AuthenticationUtils.isEmailAddressValid(emailAddress)) {
               Util.showCenteredToast(getActivity(), R.string.login_invalid_email);
                        return false;
                    }
           AuthenticationUtils.Result passwordValidationResult = AuthenticationUtils.validatePassword(this.mPassword.getText().toString());
                    switch (passwordValidationResult) {
                        case EMPTY:
                   Util.showCenteredToast(getActivity(), R.string.login_empty_password);
                            return false;
                        case TOO_SHORT:
                   Util.showCenteredToast(getActivity(), R.string.login_password_too_short);
                            return false;
                        default:
                   return true;
                    }
                }

                @Override 
       public void onSpanClicked(View view, int type, Object tag) {
           if (getActivity() != null) {
               Intent intent = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
                        switch (type) {
                            case 1:
                       intent.putExtra("type", 1);
                                break;
                            case 2:
                       intent.putExtra("type", 2);
                                break;
                            default:
                       return;
                        }
               startActivity(intent);
                    }
                }

                @Override 
       public void onNameLoaded(String name) {
                }

                @Override 
       public void onPhoneNumberLoaded(String phoneNumber) {
           if (TextUtils.isEmpty(this.mPhone.getText()) && !TextUtils.isEmpty(phoneNumber)) {
               this.mPhone.setText(phoneNumber);
                    }
                }

                @Override 
       public void onEmailLoaded(String emailText) {
           EditText email = this.mEmailAddress;
           if (TextUtils.isEmpty(email.getText()) && !TextUtils.isEmpty(emailText)) {
               email.setText(emailText);
               email.setSelection(emailText.length());
                    }
                }

                
       class RunnableC02872 implements Runnable {
           RunnableC02872() {
                    }

                    @Override 
           public void run() {
               FragmentActivity activity = SignUpDetailsFragment.this.getActivity();
               if (activity != null) {
                   Util.setSoftKeyboardVisibility(activity, SignUpDetailsFragment.access$300(SignUpDetailsFragment.this), true);
                        }
                    }
                }

       public void onMoveTo() {
           if (this.mHandler != null) {
               this.mHandler.postDelayed(new RunnableC02872(), 300L);
                    }
                }

       public String getPhoneNumber() {
           return this.mPhone.getText().toString();
                }
            }
