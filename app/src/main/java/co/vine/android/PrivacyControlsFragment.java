            package co.vine.android;

            import android.app.Activity;
            import android.content.SharedPreferences;
            import android.os.Bundle;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.CompoundButton;
            import android.widget.TextView;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.util.Util;
            import co.vine.android.views.SwitchInterface;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class PrivacyControlsFragment extends BaseControllerFragment {
                private static final String STATE_ACCEPT_OON = "state_accept_oon";
                private static final String STATE_ALLOW_ADDRESS_BOOK = "state_address_book";
                private static final String STATE_EMAIL_DISCOVERABLE = "state_email_discoverable";
                private static final String STATE_PHONE_DISCOVERABLE = "state_phone_discoverable";
                private static final String STATE_TWITTER_DISCOVERABLE = "state_twitter_discoverable";
                private static final int TYPE_ACCEPT_OON = 3;
                private static final int TYPE_ADDRESS_BOOK = 4;
                public static final int TYPE_EMAIL = 0;
                public static final int TYPE_PHONE = 1;
                public static final int TYPE_TWITTER = 2;
                private boolean mAcceptOon;
                private boolean mAllowAddressBook;
                private boolean mEmailDiscoverable;
                private boolean mPhoneDiscoverable;
                private boolean mTwitterDiscoverable;

/* 19 */        static /* synthetic */ boolean access$100(PrivacyControlsFragment x0) {
/* 20 */            return x0.mAcceptOon;
                }

/* 19 */        static /* synthetic */ boolean access$102(PrivacyControlsFragment x0, boolean x1) {
/* 20 */            x0.mAcceptOon = x1;
                    return x1;
                }

/* 19 */        static /* synthetic */ boolean access$200(PrivacyControlsFragment x0) {
/* 20 */            return x0.mAllowAddressBook;
                }

/* 19 */        static /* synthetic */ boolean access$202(PrivacyControlsFragment x0, boolean x1) {
/* 20 */            x0.mAllowAddressBook = x1;
                    return x1;
                }

/* 19 */        static /* synthetic */ boolean access$300(PrivacyControlsFragment x0) {
/* 20 */            return x0.mEmailDiscoverable;
                }

/* 19 */        static /* synthetic */ boolean access$302(PrivacyControlsFragment x0, boolean x1) {
/* 20 */            x0.mEmailDiscoverable = x1;
                    return x1;
                }

/* 19 */        static /* synthetic */ boolean access$400(PrivacyControlsFragment x0) {
/* 20 */            return x0.mPhoneDiscoverable;
                }

/* 19 */        static /* synthetic */ boolean access$402(PrivacyControlsFragment x0, boolean x1) {
/* 20 */            x0.mPhoneDiscoverable = x1;
                    return x1;
                }

/* 19 */        static /* synthetic */ boolean access$500(PrivacyControlsFragment x0) {
/* 20 */            return x0.mTwitterDiscoverable;
                }

/* 19 */        static /* synthetic */ boolean access$502(PrivacyControlsFragment x0, boolean x1) {
/* 20 */            x0.mTwitterDiscoverable = x1;
                    return x1;
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 41 */        public void onCreate(Bundle savedInstanceState) {
/* 42 */            super.onCreate(savedInstanceState);
/* 43 */            this.mAppSessionListener = new ContentControlsListener();
/* 45 */            if (savedInstanceState != null) {
/* 46 */                this.mAcceptOon = savedInstanceState.getBoolean(STATE_ACCEPT_OON, false);
/* 47 */                this.mAllowAddressBook = savedInstanceState.getBoolean(STATE_ALLOW_ADDRESS_BOOK, false);
/* 48 */                this.mEmailDiscoverable = savedInstanceState.getBoolean(STATE_EMAIL_DISCOVERABLE, true);
/* 49 */                this.mPhoneDiscoverable = savedInstanceState.getBoolean(STATE_PHONE_DISCOVERABLE, true);
/* 50 */                this.mTwitterDiscoverable = savedInstanceState.getBoolean(STATE_TWITTER_DISCOVERABLE, true);
/* 59 */                return;
                    }
/* 52 */            SharedPreferences prefs = Util.getDefaultSharedPrefs(getActivity());
/* 53 */            this.mAcceptOon = prefs.getBoolean(Settings.PREF_ACCEPT_OON, true);
/* 54 */            this.mAllowAddressBook = prefs.getBoolean(Settings.PREF_ENABLE_ADDRESS_BOOK, false);
/* 55 */            this.mEmailDiscoverable = prefs.getBoolean(Settings.PREF_EMAIL_DISCOVERABLE, true);
/* 56 */            this.mPhoneDiscoverable = prefs.getBoolean(Settings.PREF_PHONE_DISCOVERABLE, true);
/* 57 */            this.mTwitterDiscoverable = prefs.getBoolean(Settings.PREF_TWITTER_DISCOVERABLE, true);
                }

                @Override // android.support.v4.app.Fragment
/* 62 */        public void onSaveInstanceState(Bundle outState) {
/* 63 */            super.onSaveInstanceState(outState);
/* 64 */            outState.putBoolean(STATE_ACCEPT_OON, this.mAcceptOon);
/* 65 */            outState.putBoolean(STATE_ALLOW_ADDRESS_BOOK, this.mAllowAddressBook);
/* 66 */            outState.putBoolean(STATE_EMAIL_DISCOVERABLE, this.mEmailDiscoverable);
/* 67 */            outState.putBoolean(STATE_PHONE_DISCOVERABLE, this.mPhoneDiscoverable);
/* 68 */            outState.putBoolean(STATE_TWITTER_DISCOVERABLE, this.mTwitterDiscoverable);
                }

                @Override // android.support.v4.app.Fragment
/* 73 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 74 */            ViewGroup vg = (ViewGroup) inflater.inflate(C0257R.layout.settings_sub, container, false);
/* 75 */            addSwitcher(inflater, vg, C0257R.string.settings_accepts_oon, C0257R.string.settings_accepts_oon_desc, this.mAcceptOon, 3);
/* 77 */            addSwitcher(inflater, vg, C0257R.string.settings_address_book, C0257R.string.settings_address_book_desc, this.mAllowAddressBook, 4);
/* 79 */            addSwitcher(inflater, vg, C0257R.string.email_discoverability, C0257R.string.email_discoverability_desc, this.mEmailDiscoverable, 0);
/* 81 */            addSwitcher(inflater, vg, C0257R.string.phone_discoverability, C0257R.string.phone_discoverability_desc, this.mPhoneDiscoverable, 1);
/* 83 */            addSwitcher(inflater, vg, C0257R.string.twitter_discoverability, C0257R.string.twitter_discoverability_desc, this.mTwitterDiscoverable, 2);
/* 85 */            return vg;
                }

/* 89 */        private void addSwitcher(LayoutInflater inflater, ViewGroup parent, int labelString, int descString, boolean initialCondition, int type) {
/* 90 */            View view = inflater.inflate(C0257R.layout.settings_switch_row, parent, false);
/* 91 */            SwitchInterface switcher = (SwitchInterface) view.findViewById(C0257R.id.switcher);
/* 92 */            if (initialCondition) {
/* 93 */                switcher.setChecked(true);
                    }
/* 95 */            switcher.setOnCheckedChangeListener(new PrivacyControlOnCheckChangedListener(this, type, null));
/* 96 */            ((TextView) view.findViewById(C0257R.id.label)).setText(labelString);
/* 97 */            ((TextView) view.findViewById(C0257R.id.description)).setText(descString);
/* 98 */            parent.addView(view);
/* 99 */            inflater.inflate(C0257R.layout.settings_sub_divider, parent, true);
                }

/* 100 */       class PrivacyControlOnCheckChangedListener implements CompoundButton.OnCheckedChangeListener {
                    private final int mActionCode;

/* 101 */           /* synthetic */ PrivacyControlOnCheckChangedListener(PrivacyControlsFragment x0, int x1, Object x2) {
/* 102 */               this(x1);
                    }

/* 105 */           private PrivacyControlOnCheckChangedListener(int actionCode) {
/* 107 */               this.mActionCode = actionCode;
                    }

                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 111 */           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/* 112 */               if (this.mActionCode == 3) {
/* 113 */                   PrivacyControlsFragment.access$102(PrivacyControlsFragment.this, isChecked);
/* 114 */                   PrivacyControlsFragment.this.mAppController.updateAcceptOon(PrivacyControlsFragment.access$100(PrivacyControlsFragment.this));
/* 128 */                   return;
                        }
/* 115 */               if (this.mActionCode == 4) {
/* 116 */                   PrivacyControlsFragment.access$202(PrivacyControlsFragment.this, isChecked);
/* 117 */                   PrivacyControlsFragment.this.mAppController.updateEnableAddressBook(PrivacyControlsFragment.access$200(PrivacyControlsFragment.this));
/* 128 */                   return;
                        }
/* 118 */               if (this.mActionCode == 0) {
/* 119 */                   PrivacyControlsFragment.access$302(PrivacyControlsFragment.this, isChecked);
/* 120 */                   PrivacyControlsFragment.this.mAppController.updateDiscoverability(0, PrivacyControlsFragment.access$300(PrivacyControlsFragment.this));
                        } else if (this.mActionCode == 1) {
/* 122 */                   PrivacyControlsFragment.access$402(PrivacyControlsFragment.this, isChecked);
/* 123 */                   PrivacyControlsFragment.this.mAppController.updateDiscoverability(1, PrivacyControlsFragment.access$400(PrivacyControlsFragment.this));
                        } else if (this.mActionCode == 2) {
/* 125 */                   PrivacyControlsFragment.access$502(PrivacyControlsFragment.this, isChecked);
/* 126 */                   PrivacyControlsFragment.this.mAppController.updateDiscoverability(2, PrivacyControlsFragment.access$500(PrivacyControlsFragment.this));
                        }
                    }
                }

/* 129 */       class ContentControlsListener extends AppSessionListener {
/* 130 */           ContentControlsListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 134 */           public void onUpdateAcceptOonComplete(String reqId, int statusCode, String reasonPhrase, boolean acceptOon) {
/* 135 */               Activity activity = PrivacyControlsFragment.this.getActivity();
/* 136 */               if (statusCode == 200) {
/* 137 */                   SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(activity).edit();
/* 138 */                   editor.putBoolean(Settings.PREF_ACCEPT_OON, acceptOon);
/* 139 */                   editor.apply();
/* 143 */                   return;
                        }
/* 141 */               Util.showCenteredToast(activity, C0257R.string.privacy_controls_update_fail);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 147 */           public void onUpdateEnableAddressBookComplete(String reqId, int statusCode, String reasonPhrase, boolean enableAddressBook) {
/* 148 */               Activity activity = PrivacyControlsFragment.this.getActivity();
/* 149 */               if (statusCode == 200) {
/* 150 */                   SharedPreferences.Editor editor = Util.getDefaultSharedPrefs(PrivacyControlsFragment.this.getActivity()).edit();
/* 151 */                   editor.putBoolean(Settings.PREF_ENABLE_ADDRESS_BOOK, enableAddressBook);
/* 152 */                   editor.apply();
/* 153 */                   if (enableAddressBook) {
/* 154 */                       PrivacyControlsFragment.this.mAppController.sendAddressBook();
/* 159 */                       return;
                            }
/* 159 */                   return;
                        }
/* 157 */               Util.showCenteredToast(activity, C0257R.string.privacy_controls_update_fail);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 163 */           public void onUpdateDiscoverability(String reqId, int statusCode, String reasonPhrase, int type, boolean enable) {
                        String pref;
/* 164 */               Activity activity = PrivacyControlsFragment.this.getActivity();
/* 165 */               if (statusCode == 200) {
/* 167 */                   if (type == 0) {
/* 168 */                       pref = Settings.PREF_EMAIL_DISCOVERABLE;
                            } else if (type == 1) {
/* 170 */                       pref = Settings.PREF_PHONE_DISCOVERABLE;
                            } else {
/* 172 */                       pref = Settings.PREF_TWITTER_DISCOVERABLE;
                            }
/* 175 */                   Util.getDefaultSharedPrefs(PrivacyControlsFragment.this.getActivity()).edit().putBoolean(pref, enable).apply();
/* 181 */                   return;
                        }
/* 179 */               Util.showCenteredToast(activity, C0257R.string.privacy_controls_update_fail);
                    }
                }
            }
