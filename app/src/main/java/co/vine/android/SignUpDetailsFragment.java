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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class SignUpDetailsFragment extends BaseFragment implements SpanClickListener, ContactsHelper.ContactHelperListener {
                private static final int SPAN_PRIVACY_POLICY = 1;
                private static final int SPAN_TOS = 2;
                private EditText mEmailAddress;
                private Handler mHandler;
                private EditText mPassword;
                private EditText mPhone;

/* 36 */        static /* synthetic */ boolean access$000(SignUpDetailsFragment x0) {
/* 37 */            return x0.validateAndDisplayToastIfCredentialsInvalid();
                }

/* 36 */        static /* synthetic */ EditText access$100(SignUpDetailsFragment x0) {
/* 37 */            return x0.mPhone;
                }

/* 36 */        static /* synthetic */ boolean access$200(SignUpDetailsFragment x0) {
/* 37 */            return x0.onNextClicked();
                }

/* 36 */        static /* synthetic */ EditText access$300(SignUpDetailsFragment x0) {
/* 37 */            return x0.mEmailAddress;
                }

                @Override // android.support.v4.app.Fragment
/* 47 */        public void onCreate(Bundle savedInstanceState) {
/* 48 */            super.onCreate(savedInstanceState);
/* 49 */            setHasOptionsMenu(true);
/* 50 */            this.mHandler = new Handler();
/* 51 */            ((SignUpPagerActivity) getActivity()).setBarTitle(C0257R.string.login_sign_up);
                }

                @Override // android.support.v4.app.Fragment
/* 55 */        public void onViewCreated(View view, Bundle savedInstanceState) {
/* 56 */            super.onViewCreated(view, savedInstanceState);
/* 57 */            this.mEmailAddress = (EditText) view.findViewById(C0257R.id.signup_email);
/* 58 */            this.mPassword = (EditText) view.findViewById(C0257R.id.signup_password);
/* 59 */            this.mPhone = (EditText) view.findViewById(C0257R.id.signup_phone);
/* 60 */            this.mPhone.setOnEditorActionListener(new C02861());
/* 73 */            TextView termsOfServiceView = (TextView) view.findViewById(C0257R.id.tos_line_2);
/* 74 */            StyleSpan[] boldStyle = {new StyleSpan(1), new StyleSpan(1)};
/* 75 */            Spanned spanned = Util.getSpannedText(boldStyle, getString(C0257R.string.sign_up_tos_line_2), CommonUtil.MARKER_QUOTES);
/* 76 */            termsOfServiceView.setText(spanned);
/* 77 */            termsOfServiceView.setMovementMethod(LinkMovementMethod.getInstance());
/* 79 */            Spannable clickSpannable = (Spannable) termsOfServiceView.getText();
/* 80 */            StyleSpan[] spans = (StyleSpan[]) spanned.getSpans(0, spanned.length(), StyleSpan.class);
/* 81 */            Resources resources = getResources();
/* 84 */            int start = spanned.getSpanStart(spans[0]);
/* 85 */            int end = spanned.getSpanEnd(spans[0]);
/* 86 */            StyledClickableSpan clickableSpan = new StyledClickableSpan(1, null, this);
/* 87 */            clickableSpan.setColor(resources.getColor(C0257R.color.text_fineprint));
/* 88 */            Util.safeSetSpan(clickSpannable, clickableSpan, start, end, 33);
/* 91 */            int start2 = spanned.getSpanStart(spans[1]);
/* 92 */            int end2 = spanned.getSpanEnd(spans[1]);
/* 93 */            StyledClickableSpan clickableSpan2 = new StyledClickableSpan(2, null, this);
/* 94 */            clickableSpan2.setColor(resources.getColor(C0257R.color.text_fineprint));
/* 95 */            Util.safeSetSpan(clickSpannable, clickableSpan2, start2, end2, 33);
/* 97 */            ContactsHelper.loadContacts(this, this);
                }

                /* JADX INFO: renamed from: co.vine.android.SignUpDetailsFragment$1 */
/* 58 */        class C02861 implements TextView.OnEditorActionListener {
/* 59 */            C02861() {
                    }

                    @Override // android.widget.TextView.OnEditorActionListener
/* 61 */            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
/* 62 */                if (actionId == 6 && SignUpDetailsFragment.access$000(SignUpDetailsFragment.this) && SignUpDetailsFragment.this.getActivity() != null) {
/* 64 */                    InputMethodManager imm = (InputMethodManager) SignUpDetailsFragment.this.getActivity().getSystemService("input_method");
/* 65 */                    imm.hideSoftInputFromWindow(SignUpDetailsFragment.access$100(SignUpDetailsFragment.this).getWindowToken(), 0);
/* 66 */                    SignUpDetailsFragment.access$200(SignUpDetailsFragment.this);
/* 69 */                    return true;
                        }
/* 69 */                return true;
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 101 */       public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
/* 102 */           return inflater.inflate(C0257R.layout.sign_up_flow_details, root, false);
                }

                @Override // android.support.v4.app.Fragment
/* 106 */       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 107 */           inflater.inflate(C0257R.menu.done, menu);
/* 108 */           MenuItem doneButton = menu.findItem(C0257R.id.done);
/* 109 */           doneButton.setEnabled(true);
                }

                @Override // android.support.v4.app.Fragment
/* 113 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 114 */           if (item.getItemId() == 2131427939 && validateAndDisplayToastIfCredentialsInvalid()) {
/* 115 */               if (validateAndDisplayToastIfCredentialsInvalid()) {
/* 116 */                   return onNextClicked();
                        }
/* 118 */               return true;
                    }
/* 120 */           return super.onOptionsItemSelected(item);
                }

/* 124 */       private boolean onNextClicked() {
/* 125 */           Util.setSoftKeyboardVisibility(getActivity(), this.mEmailAddress, false);
/* 126 */           SignUpPagerActivity activity = (SignUpPagerActivity) getActivity();
/* 127 */           if (activity == null) {
                        return false;
                    }
/* 128 */           activity.setLogin(this.mEmailAddress.getText().toString());
/* 129 */           activity.setPassword(this.mPassword.getText().toString());
/* 130 */           activity.setPhone(this.mPhone.getText().toString());
/* 131 */           activity.toNextStep();
/* 132 */           return true;
                }

/* 138 */       private boolean validateAndDisplayToastIfCredentialsInvalid() {
/* 139 */           String emailAddress = this.mEmailAddress.getText().toString();
/* 140 */           if (emailAddress.isEmpty()) {
/* 141 */               Util.showCenteredToast(getActivity(), C0257R.string.login_empty_email);
                        return false;
                    }
/* 143 */           if (!AuthenticationUtils.isEmailAddressValid(emailAddress)) {
/* 144 */               Util.showCenteredToast(getActivity(), C0257R.string.login_invalid_email);
                        return false;
                    }
/* 148 */           AuthenticationUtils.Result passwordValidationResult = AuthenticationUtils.validatePassword(this.mPassword.getText().toString());
                    switch (passwordValidationResult) {
                        case EMPTY:
/* 151 */                   Util.showCenteredToast(getActivity(), C0257R.string.login_empty_password);
                            return false;
                        case TOO_SHORT:
/* 154 */                   Util.showCenteredToast(getActivity(), C0257R.string.login_password_too_short);
                            return false;
                        default:
/* 157 */                   return true;
                    }
                }

                @Override // co.vine.android.widget.SpanClickListener
/* 161 */       public void onSpanClicked(View view, int type, Object tag) {
/* 162 */           if (getActivity() != null) {
/* 165 */               Intent intent = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
                        switch (type) {
                            case 1:
/* 168 */                       intent.putExtra("type", 1);
                                break;
                            case 2:
/* 171 */                       intent.putExtra("type", 2);
                                break;
                            default:
/* 177 */                       return;
                        }
/* 176 */               startActivity(intent);
                    }
                }

                @Override // co.vine.android.util.ContactsHelper.ContactHelperListener
/* 179 */       public void onNameLoaded(String name) {
                }

                @Override // co.vine.android.util.ContactsHelper.ContactHelperListener
/* 183 */       public void onPhoneNumberLoaded(String phoneNumber) {
/* 184 */           if (TextUtils.isEmpty(this.mPhone.getText()) && !TextUtils.isEmpty(phoneNumber)) {
/* 185 */               this.mPhone.setText(phoneNumber);
                    }
                }

                @Override // co.vine.android.util.ContactsHelper.ContactHelperListener
/* 190 */       public void onEmailLoaded(String emailText) {
/* 191 */           EditText email = this.mEmailAddress;
/* 192 */           if (TextUtils.isEmpty(email.getText()) && !TextUtils.isEmpty(emailText)) {
/* 193 */               email.setText(emailText);
/* 194 */               email.setSelection(emailText.length());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SignUpDetailsFragment$2 */
/* 198 */       class RunnableC02872 implements Runnable {
/* 199 */           RunnableC02872() {
                    }

                    @Override // java.lang.Runnable
/* 202 */           public void run() {
/* 203 */               FragmentActivity activity = SignUpDetailsFragment.this.getActivity();
/* 204 */               if (activity != null) {
/* 205 */                   Util.setSoftKeyboardVisibility(activity, SignUpDetailsFragment.access$300(SignUpDetailsFragment.this), true);
                        }
                    }
                }

/* 198 */       public void onMoveTo() {
/* 199 */           if (this.mHandler != null) {
/* 200 */               this.mHandler.postDelayed(new RunnableC02872(), 300L);
                    }
                }

/* 212 */       public String getPhoneNumber() {
/* 213 */           return this.mPhone.getText().toString();
                }
            }
