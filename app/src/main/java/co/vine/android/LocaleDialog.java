            package co.vine.android;

            import android.content.DialogInterface;
            import android.content.SharedPreferences;
            import android.os.Bundle;
            import android.support.v4.app.DialogFragment;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.ArrayAdapter;
            import android.widget.RadioButton;
            import android.widget.RadioGroup;
            import android.widget.Spinner;
            import android.widget.SpinnerAdapter;
            import co.vine.android.util.Util;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Comparator;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class LocaleDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
                private boolean mChanges;
                private int mLastSelected;
                private RadioGroup mLocaleOptions;
                private Spinner mLocales;
                private ArrayAdapter<String> mLocalesAdapter;
                private ArrayList<Locale> mLocalesCodes;

/* 26 */        static /* synthetic */ Spinner access$100(LocaleDialog x0) {
/* 27 */            return x0.mLocales;
                }

/* 26 */        static /* synthetic */ boolean access$202(LocaleDialog x0, boolean x1) {
/* 27 */            x0.mChanges = x1;
                    return x1;
                }

/* 36 */        static LocaleDialog newInstance() {
/* 37 */            return new LocaleDialog();
                }

/* 40 */        private static Boolean isValidLocale(Locale locale) {
                    return Boolean.valueOf((TextUtils.isEmpty(locale.getLanguage()) || TextUtils.isEmpty(locale.getCountry()) || !TextUtils.isEmpty(locale.getVariant())) ? false : true);
                }

                @Override // android.support.v4.app.DialogFragment, android.support.v4.app.Fragment
/* 47 */        public void onCreate(Bundle savedInstanceState) {
/* 48 */            super.onCreate(savedInstanceState);
/* 49 */            ArrayList<String> localesNames = new ArrayList<>();
/* 50 */            ArrayList<Locale> localesCodes = new ArrayList<>();
/* 52 */            Locale[] availableLocales = Locale.getAvailableLocales();
/* 53 */            if (availableLocales != null) {
/* 54 */                Arrays.sort(availableLocales, new LocaleComparator(this, null));
                        for (Locale l : availableLocales) {
/* 56 */                    if (isValidLocale(l).booleanValue()) {
/* 57 */                        String code = l.getDisplayName(Locale.ENGLISH);
/* 58 */                        localesCodes.add(l);
/* 59 */                        localesNames.add(l.getLanguage() + " - " + code);
                            }
                        }
                    }
/* 65 */            Locale dummy = new Locale("en", "ss");
/* 66 */            localesNames.add("DEBUG - " + dummy.getLanguage() + " - " + dummy.getDisplayName(Locale.ENGLISH));
/* 67 */            localesCodes.add(dummy);
/* 69 */            this.mLocalesCodes = localesCodes;
/* 71 */            ArrayAdapter<String> localesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, localesNames);
/* 73 */            localesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
/* 74 */            this.mLocalesAdapter = localesAdapter;
                }

                @Override // android.support.v4.app.Fragment
/* 79 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 80 */            View v = inflater.inflate(C0257R.layout.locale_preference, container, false);
/* 82 */            RadioButton system = (RadioButton) v.findViewById(C0257R.id.system_locale);
/* 83 */            RadioButton custom = (RadioButton) v.findViewById(C0257R.id.custom_locale);
/* 85 */            SharedPreferences prefs = Util.getDefaultSharedPrefs(getActivity());
/* 87 */            boolean customEnabled = prefs.getBoolean(Settings.PREF_CUSTOM_LOCALE_ENABLED, false);
/* 88 */            system.setChecked(!customEnabled);
/* 89 */            custom.setChecked(customEnabled);
/* 91 */            Spinner locales = (Spinner) v.findViewById(C0257R.id.locales_spinner);
/* 92 */            locales.setAdapter((SpinnerAdapter) this.mLocalesAdapter);
/* 93 */            if (prefs.contains(Settings.PREF_CUSTOM_LOCALE)) {
/* 94 */                String customLocaleCode = prefs.getString(Settings.PREF_CUSTOM_LOCALE, "");
/* 95 */                String customCountry = prefs.getString(Settings.PREF_CUSTOME_LOCALE_COUNTRY, "");
/* 96 */                if (!TextUtils.isEmpty(customLocaleCode)) {
/* 97 */                    int index = 0;
                            for (Locale l : this.mLocalesCodes) {
/* 99 */                        if (customLocaleCode.equals(l.getLanguage()) && customCountry.equals(l.getCountry())) {
/* 101 */                           locales.setSelection(index);
/* 102 */                           this.mLastSelected = index;
                                    break;
                                }
/* 105 */                       index++;
                            }
                        }
                    }
/* 110 */           RadioGroup localeOptions = (RadioGroup) v.findViewById(C0257R.id.locale_group);
/* 111 */           if (customEnabled) {
/* 112 */               localeOptions.check(C0257R.id.custom_locale);
                    } else {
/* 114 */               localeOptions.check(C0257R.id.system_locale);
                    }
/* 117 */           localeOptions.setOnCheckedChangeListener(new C02381());
/* 125 */           locales.setEnabled(customEnabled);
/* 127 */           locales.setOnItemSelectedListener(this);
/* 129 */           this.mLocales = locales;
/* 130 */           this.mLocaleOptions = localeOptions;
/* 133 */           getDialog().setTitle("Custom locales");
/* 135 */           return v;
                }

                /* JADX INFO: renamed from: co.vine.android.LocaleDialog$1 */
/* 115 */       class C02381 implements RadioGroup.OnCheckedChangeListener {
/* 116 */           C02381() {
                    }

                    @Override // android.widget.RadioGroup.OnCheckedChangeListener
/* 119 */           public void onCheckedChanged(RadioGroup group, int checkedId) {
/* 120 */               LocaleDialog.access$100(LocaleDialog.this).setEnabled(checkedId == 2131427619);
/* 121 */               LocaleDialog.access$202(LocaleDialog.this, true);
                    }
                }

                @Override // android.support.v4.app.DialogFragment, android.content.DialogInterface.OnCancelListener
/* 139 */       public void onCancel(DialogInterface dialog) {
/* 140 */           super.onCancel(dialog);
/* 141 */           getDialog().dismiss();
                }

                @Override // android.support.v4.app.DialogFragment, android.content.DialogInterface.OnDismissListener
/* 145 */       public void onDismiss(DialogInterface dialog) {
/* 146 */           super.onDismiss(dialog);
/* 147 */           if (this.mChanges) {
/* 148 */               boolean customLocale = this.mLocaleOptions.getCheckedRadioButtonId() == 2131427619;
/* 150 */               int position = this.mLocales.getSelectedItemPosition();
/* 151 */               int index = 0;
/* 152 */               Locale selectedLocale = null;
                        for (Locale l : this.mLocalesCodes) {
/* 154 */                   if (index == position) {
/* 155 */                       selectedLocale = l;
                                break;
                            }
/* 158 */                   index++;
                        }
/* 160 */               if (selectedLocale != null) {
/* 164 */                   String customLocaleCode = selectedLocale.getLanguage();
/* 165 */                   String customCountry = selectedLocale.getCountry();
/* 166 */                   SharedPreferences prefs = Util.getDefaultSharedPrefs(getActivity());
/* 168 */                   prefs.edit().putBoolean(Settings.PREF_CUSTOM_LOCALE_ENABLED, customLocale).putString(Settings.PREF_CUSTOM_LOCALE, customLocaleCode).putString(Settings.PREF_CUSTOME_LOCALE_COUNTRY, customCountry).apply();
/* 172 */                   if (index != this.mLastSelected) {
/* 173 */                       Util.restartApp(getActivity());
                            }
                        }
                    }
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
/* 179 */       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/* 180 */           this.mChanges = true;
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
/* 185 */       public void onNothingSelected(AdapterView<?> parent) {
                }

/* 186 */       private class LocaleComparator implements Comparator<Locale> {
/* 187 */           private LocaleComparator() {
                    }

/* 187 */           /* synthetic */ LocaleComparator(LocaleDialog x0, C02381 x1) {
/* 188 */               this();
                    }


/* 190 */           public int compare(Locale l1, Locale l2) {
/* 191 */               String s1 = l1.getLanguage() + "-" + l1.getCountry();
/* 192 */               String s2 = l2.getLanguage() + "-" + l2.getCountry();
/* 194 */               return s1.compareTo(s2);
                    }
                }
            }
