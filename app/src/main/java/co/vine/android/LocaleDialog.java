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
            
            
    public class LocaleDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
                private boolean mChanges;
                private int mLastSelected;
                private RadioGroup mLocaleOptions;
                private Spinner mLocales;
                private ArrayAdapter<String> mLocalesAdapter;
                private ArrayList<Locale> mLocalesCodes;

        static  Spinner access$100(LocaleDialog x0) {
            return x0.mLocales;
                }

        static  boolean access$202(LocaleDialog x0, boolean x1) {
            x0.mChanges = x1;
                    return x1;
                }

        static LocaleDialog newInstance() {
            return new LocaleDialog();
                }

        private static Boolean isValidLocale(Locale locale) {
                    return Boolean.valueOf((TextUtils.isEmpty(locale.getLanguage()) || TextUtils.isEmpty(locale.getCountry()) || !TextUtils.isEmpty(locale.getVariant())) ? false : true);
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ArrayList<String> localesNames = new ArrayList<>();
            ArrayList<Locale> localesCodes = new ArrayList<>();
            Locale[] availableLocales = Locale.getAvailableLocales();
            if (availableLocales != null) {
                Arrays.sort(availableLocales, new LocaleComparator(this, null));
                        for (Locale l : availableLocales) {
                    if (isValidLocale(l).booleanValue()) {
                        String code = l.getDisplayName(Locale.ENGLISH);
                        localesCodes.add(l);
                        localesNames.add(l.getLanguage() + " - " + code);
                            }
                        }
                    }
            Locale dummy = new Locale("en", "ss");
            localesNames.add("DEBUG - " + dummy.getLanguage() + " - " + dummy.getDisplayName(Locale.ENGLISH));
            localesCodes.add(dummy);
            this.mLocalesCodes = localesCodes;
            ArrayAdapter<String> localesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, localesNames);
            localesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.mLocalesAdapter = localesAdapter;
                }

                @Override 
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(C0257R.layout.locale_preference, container, false);
            RadioButton system = (RadioButton) v.findViewById(C0257R.id.system_locale);
            RadioButton custom = (RadioButton) v.findViewById(C0257R.id.custom_locale);
            SharedPreferences prefs = Util.getDefaultSharedPrefs(getActivity());
            boolean customEnabled = prefs.getBoolean(Settings.PREF_CUSTOM_LOCALE_ENABLED, false);
            system.setChecked(!customEnabled);
            custom.setChecked(customEnabled);
            Spinner locales = (Spinner) v.findViewById(C0257R.id.locales_spinner);
            locales.setAdapter((SpinnerAdapter) this.mLocalesAdapter);
            if (prefs.contains(Settings.PREF_CUSTOM_LOCALE)) {
                String customLocaleCode = prefs.getString(Settings.PREF_CUSTOM_LOCALE, "");
                String customCountry = prefs.getString(Settings.PREF_CUSTOME_LOCALE_COUNTRY, "");
                if (!TextUtils.isEmpty(customLocaleCode)) {
                    int index = 0;
                            for (Locale l : this.mLocalesCodes) {
                        if (customLocaleCode.equals(l.getLanguage()) && customCountry.equals(l.getCountry())) {
                           locales.setSelection(index);
                           this.mLastSelected = index;
                                    break;
                                }
                       index++;
                            }
                        }
                    }
           RadioGroup localeOptions = (RadioGroup) v.findViewById(C0257R.id.locale_group);
           if (customEnabled) {
               localeOptions.check(C0257R.id.custom_locale);
                    } else {
               localeOptions.check(C0257R.id.system_locale);
                    }
           localeOptions.setOnCheckedChangeListener(new C02381());
           locales.setEnabled(customEnabled);
           locales.setOnItemSelectedListener(this);
           this.mLocales = locales;
           this.mLocaleOptions = localeOptions;
           getDialog().setTitle("Custom locales");
           return v;
                }

                
       class C02381 implements RadioGroup.OnCheckedChangeListener {
           C02381() {
                    }

                    @Override 
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               LocaleDialog.access$100(LocaleDialog.this).setEnabled(checkedId == 2131427619);
               LocaleDialog.access$202(LocaleDialog.this, true);
                    }
                }

                @Override 
       public void onCancel(DialogInterface dialog) {
           super.onCancel(dialog);
           getDialog().dismiss();
                }

                @Override 
       public void onDismiss(DialogInterface dialog) {
           super.onDismiss(dialog);
           if (this.mChanges) {
               boolean customLocale = this.mLocaleOptions.getCheckedRadioButtonId() == 2131427619;
               int position = this.mLocales.getSelectedItemPosition();
               int index = 0;
               Locale selectedLocale = null;
                        for (Locale l : this.mLocalesCodes) {
                   if (index == position) {
                       selectedLocale = l;
                                break;
                            }
                   index++;
                        }
               if (selectedLocale != null) {
                   String customLocaleCode = selectedLocale.getLanguage();
                   String customCountry = selectedLocale.getCountry();
                   SharedPreferences prefs = Util.getDefaultSharedPrefs(getActivity());
                   prefs.edit().putBoolean(Settings.PREF_CUSTOM_LOCALE_ENABLED, customLocale).putString(Settings.PREF_CUSTOM_LOCALE, customLocaleCode).putString(Settings.PREF_CUSTOME_LOCALE_COUNTRY, customCountry).apply();
                   if (index != this.mLastSelected) {
                       Util.restartApp(getActivity());
                            }
                        }
                    }
                }

                @Override 
       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           this.mChanges = true;
                }

                @Override 
       public void onNothingSelected(AdapterView<?> parent) {
                }

       private class LocaleComparator implements Comparator<Locale> {
           private LocaleComparator() {
                    }

            LocaleComparator(LocaleDialog x0, C02381 x1) {
               this();
                    }


           public int compare(Locale l1, Locale l2) {
               String s1 = l1.getLanguage() + "-" + l1.getCountry();
               String s2 = l2.getLanguage() + "-" + l2.getCountry();
               return s1.compareTo(s2);
                    }
                }
            }
