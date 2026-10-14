            package co.vine.android;

            import android.content.DialogInterface;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.support.v4.app.FragmentActivity;
            import android.text.Editable;
            import android.text.TextUtils;
            import android.text.TextWatcher;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.EditText;
            import android.widget.TextView;
            import co.vine.android.util.Util;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class ConfirmationFlowEntryFragment extends BaseControllerFragment {
                public static final String ARG_PHONE_NUMBER = "phone_number";
                public static final String STATE_PHONE_NUMBER = "phone_number";
                private MenuItem mNext;
                private boolean mNextEnabled;
                private String mPhone;
                private EditText mPhoneNumberBox;

/* 25 */        static /* synthetic */ void access$000(ConfirmationFlowEntryFragment x0, boolean x1) {
/* 26 */            x0.toggleNextAction(x1);
                }

/* 25 */        static /* synthetic */ String access$100(ConfirmationFlowEntryFragment x0) {
/* 26 */            return x0.mPhone;
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 38 */        public void onCreate(Bundle savedInstanceState) {
/* 39 */            super.onCreate(savedInstanceState);
/* 40 */            setHasOptionsMenu(true);
/* 41 */            Bundle args = getArguments();
/* 42 */            if (args != null) {
/* 43 */                this.mPhone = args.getString("phone_number");
                    } else if (savedInstanceState != null) {
/* 45 */                this.mPhone = savedInstanceState.getString("phone_number");
                    }
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 50 */        public void onPause() {
/* 51 */            super.onPause();
/* 52 */            Util.setSoftKeyboardVisibility(getActivity(), this.mPhoneNumberBox, false);
                }

                @Override // android.support.v4.app.Fragment
/* 56 */        public void onSaveInstanceState(Bundle outState) {
/* 57 */            outState.putString("phone_number", this.mPhone);
/* 58 */            super.onSaveInstanceState(outState);
                }

                @Override // android.support.v4.app.Fragment
/* 62 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 63 */            View v = inflater.inflate(C0257R.layout.confirm_phone_enter_number, container, false);
/* 64 */            EditText phoneNumberBox = (EditText) v.findViewById(C0257R.id.phone_number_box);
/* 66 */            TextView confirmation = (TextView) v.findViewById(C0257R.id.enter_number_message);
/* 67 */            confirmation.setText(getString(C0257R.string.confirm_phone_start));
/* 70 */            String phone = this.mPhone;
/* 71 */            if (!TextUtils.isEmpty(phone)) {
/* 72 */                phoneNumberBox.setText(phone);
/* 73 */                phoneNumberBox.setSelection(phone.length());
                    }
/* 76 */            phoneNumberBox.addTextChangedListener(new C01661(phoneNumberBox));
/* 94 */            this.mPhoneNumberBox = phoneNumberBox;
/* 96 */            return v;
                }

                /* JADX INFO: renamed from: co.vine.android.ConfirmationFlowEntryFragment$1 */
/* 74 */        class C01661 implements TextWatcher {
                    final /* synthetic */ EditText val$phoneNumberBox;

/* 75 */            C01661(EditText editText) {
                        this.val$phoneNumberBox = editText;
                    }

                    @Override // android.text.TextWatcher
/* 78 */            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override // android.text.TextWatcher
/* 82 */            public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 83 */                if (!TextUtils.isEmpty(this.val$phoneNumberBox.getText())) {
/* 84 */                    ConfirmationFlowEntryFragment.access$000(ConfirmationFlowEntryFragment.this, true);
                        } else {
/* 86 */                    ConfirmationFlowEntryFragment.access$000(ConfirmationFlowEntryFragment.this, false);
                        }
                    }

                    @Override // android.text.TextWatcher
/* 91 */            public void afterTextChanged(Editable s) {
                    }
                }

/* 99 */        private void toggleNextAction(boolean enabled) {
/* 100 */           this.mNextEnabled = enabled;
/* 101 */           if (this.mNext != null) {
/* 102 */               this.mNext.setEnabled(enabled);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 107 */       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 108 */           inflater.inflate(C0257R.menu.next, menu);
/* 109 */           this.mNext = menu.findItem(C0257R.id.next);
/* 110 */           this.mNext.setEnabled(this.mNextEnabled);
/* 111 */           super.onCreateOptionsMenu(menu, inflater);
                }

                @Override // co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 115 */       public void onResume() {
/* 116 */           super.onResume();
/* 117 */           if (!TextUtils.isEmpty(this.mPhone)) {
/* 118 */               toggleNextAction(true);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 123 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 124 */           if (item.getItemId() == 2131427941) {
/* 125 */               Editable e = this.mPhoneNumberBox.getText();
/* 126 */               if (e == null) {
                            return false;
                        }
/* 129 */               this.mPhone = e.toString();
/* 131 */               PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1, 3);
/* 134 */               p.setListener(new C01672());
                        p.setTitle(C0257R.string.verify_your_number).setMessage(getString(C0257R.string.is_this_your_number, this.mPhone)).setPositiveButton(C0257R.string.f88ok).setNeutralButton(C0257R.string.cancel).show(getActivity().getSupportFragmentManager());
                        return true;
                    }
/* 166 */           return super.onOptionsItemSelected(item);
                }

                /* JADX INFO: renamed from: co.vine.android.ConfirmationFlowEntryFragment$2 */
/* 132 */       class C01672 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 133 */           C01672() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 136 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
/* 139 */                       FragmentActivity activity = ConfirmationFlowEntryFragment.this.getActivity();
/* 140 */                       if (activity != null) {
/* 141 */                           Fragment frag = new ConfirmationFlowVerificationFragment();
/* 142 */                           Bundle args = new Bundle();
/* 143 */                           args.putString("phone_number", ConfirmationFlowEntryFragment.access$100(ConfirmationFlowEntryFragment.this));
/* 144 */                           args.putBoolean("from_sign_up", false);
/* 145 */                           frag.setArguments(args);
/* 147 */                           activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(C0257R.anim.slide_in_from_right, C0257R.anim.slide_out_to_left).replace(C0257R.id.fragment_container, frag).commit();
                                }
                                break;
                        }
                    }
                }
            }
