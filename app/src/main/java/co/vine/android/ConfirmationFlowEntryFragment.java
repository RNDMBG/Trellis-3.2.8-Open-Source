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
            
            
    public class ConfirmationFlowEntryFragment extends BaseControllerFragment {
                public static final String ARG_PHONE_NUMBER = "phone_number";
                public static final String STATE_PHONE_NUMBER = "phone_number";
                private MenuItem mNext;
                private boolean mNextEnabled;
                private String mPhone;
                private EditText mPhoneNumberBox;

        static  void access$000(ConfirmationFlowEntryFragment x0, boolean x1) {
            x0.toggleNextAction(x1);
                }

        static  String access$100(ConfirmationFlowEntryFragment x0) {
            return x0.mPhone;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            Bundle args = getArguments();
            if (args != null) {
                this.mPhone = args.getString("phone_number");
                    } else if (savedInstanceState != null) {
                this.mPhone = savedInstanceState.getString("phone_number");
                    }
                }

                @Override 
        public void onPause() {
            super.onPause();
            Util.setSoftKeyboardVisibility(getActivity(), this.mPhoneNumberBox, false);
                }

                @Override 
        public void onSaveInstanceState(Bundle outState) {
            outState.putString("phone_number", this.mPhone);
            super.onSaveInstanceState(outState);
                }

                @Override 
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.confirm_phone_enter_number, container, false);
            EditText phoneNumberBox = (EditText) v.findViewById(R.id.phone_number_box);
            TextView confirmation = (TextView) v.findViewById(R.id.enter_number_message);
            confirmation.setText(getString(R.string.confirm_phone_start));
            String phone = this.mPhone;
            if (!TextUtils.isEmpty(phone)) {
                phoneNumberBox.setText(phone);
                phoneNumberBox.setSelection(phone.length());
                    }
            phoneNumberBox.addTextChangedListener(new C01661(phoneNumberBox));
            this.mPhoneNumberBox = phoneNumberBox;
            return v;
                }

                
        class C01661 implements TextWatcher {
                    final  EditText val$phoneNumberBox;

            C01661(EditText editText) {
                        this.val$phoneNumberBox = editText;
                    }

                    @Override 
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override 
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(this.val$phoneNumberBox.getText())) {
                    ConfirmationFlowEntryFragment.access$000(ConfirmationFlowEntryFragment.this, true);
                        } else {
                    ConfirmationFlowEntryFragment.access$000(ConfirmationFlowEntryFragment.this, false);
                        }
                    }

                    @Override 
            public void afterTextChanged(Editable s) {
                    }
                }

        private void toggleNextAction(boolean enabled) {
           this.mNextEnabled = enabled;
           if (this.mNext != null) {
               this.mNext.setEnabled(enabled);
                    }
                }

                @Override 
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           inflater.inflate(R.menu.next, menu);
           this.mNext = menu.findItem(R.id.next);
           this.mNext.setEnabled(this.mNextEnabled);
           super.onCreateOptionsMenu(menu, inflater);
                }

                @Override 
       public void onResume() {
           super.onResume();
           if (!TextUtils.isEmpty(this.mPhone)) {
               toggleNextAction(true);
                    }
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           if (item.getItemId() == 2131427941) {
               Editable e = this.mPhoneNumberBox.getText();
               if (e == null) {
                            return false;
                        }
               this.mPhone = e.toString();
               PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1, 3);
               p.setListener(new C01672());
                        p.setTitle(R.string.verify_your_number).setMessage(getString(R.string.is_this_your_number, this.mPhone)).setPositiveButton(R.string.ok).setNeutralButton(R.string.cancel).show(getActivity().getSupportFragmentManager());
                        return true;
                    }
           return super.onOptionsItemSelected(item);
                }

                
       class C01672 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C01672() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
                       FragmentActivity activity = ConfirmationFlowEntryFragment.this.getActivity();
                       if (activity != null) {
                           Fragment frag = new ConfirmationFlowVerificationFragment();
                           Bundle args = new Bundle();
                           args.putString("phone_number", ConfirmationFlowEntryFragment.access$100(ConfirmationFlowEntryFragment.this));
                           args.putBoolean("from_sign_up", false);
                           frag.setArguments(args);
                           activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).replace(R.id.fragment_container, frag).commit();
                                }
                                break;
                        }
                    }
                }
            }
