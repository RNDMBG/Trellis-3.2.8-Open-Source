            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.animation.ArgbEvaluator;
            import android.animation.ValueAnimator;
            import android.annotation.SuppressLint;
            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.app.Dialog;
            import android.app.ProgressDialog;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.content.pm.PackageInfo;
            import android.content.pm.PackageManager;
            import android.content.res.Resources;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.net.Uri;
            import android.os.AsyncTask;
            import android.os.Bundle;
            import android.support.v4.app.FragmentActivity;
            import android.support.v4.app.LoaderManager;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.text.TextUtils;
            import android.util.Log;
            import android.util.Pair;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.BaseAdapter;
            import android.widget.Button;
            import android.widget.CompoundButton;
            import android.widget.EditText;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.RelativeLayout;
            import android.widget.ScrollView;
            import android.widget.Spinner;
            import android.widget.SpinnerAdapter;
            import android.widget.TextView;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.service.GCMRegistrationService;
            import co.vine.android.service.VineService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.ClientFlagsHelper;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.ImageUtils;
            import co.vine.android.util.ShareUtils;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.views.SimpleTextWatcher;
            import co.vine.android.widget.ColorPicker;
            import co.vine.android.widget.TypingEditText;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import com.edisonwang.android.slog.SLog;
            import com.facebook.Session;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.twitter.android.sdk.Twitter;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class SettingsFragment extends BaseControllerFragment implements SetThumbnailTask.SetThumbnailListener, PromptDialogSupportFragment.OnDialogDoneListener, View.OnClickListener, ImagePicker.Listener, CompoundButton.OnCheckedChangeListener, LoaderManager.LoaderCallbacks<Cursor>, TypingEditText.TypingListener, ColorPicker.ColorClickListener {
                private static final int DEBUG_TAP_THRESHOLD = 6;
                private static final int DIALOG_CLEAR_CACHE = 6;
                private static final int DIALOG_DEACTIVATE_ACCOUNT = 7;
                private static final int DIALOG_DISCONNECT_TWITTER = 5;
                private static final int DIALOG_FACEBOOK_DISCONNECT = 11;
                private static final int DIALOG_LOGOUT = 4;
                private static final int DIALOG_REMOVE_TAKE_OR_CHOOSE_PHOTO = 2;
                private static final int DIALOG_TAKE_OR_CHOOSE_PHOTO = 1;
                private static final int DIALOG_UNSAVED_CHANGES = 3;
                private static final int DIALOG_VERIFY_EMAIL = 8;
                private static final int DIALOG_VERIFY_EMAIL_SENT = 10;
                private static final int DIALOG_VERIFY_PHONE = 9;
                public static final String EXTRA_COLOR = "color";
                private static final int LOADER_ID_DEFAULT = 0;
                private static final int REQUEST_CODE_CHOOSE_PHOTO = 5;
                private static final int REQUEST_CODE_CONFIRM_PHONE = 7;
                private static final int REQUEST_CODE_CROP = 4;
                private static final int REQUEST_CODE_TAKE_PHOTO = 3;
                private static final int REQUEST_GMS_RESOLUTION = 6;
                public static final int RESULT_CACHE_CLEARED = 1;
                private static final String STATE_AVATAR_URI = "state_avatar_url";
                private static final String STATE_BACKGROUND_COLOR = "state_background_color";
                private static final String STATE_COLOR_INDEX = "state_color_index";
                private static final String STATE_DESCRIPTION = "state_desc";
                private static final String STATE_EDITION = "state_edition";
                private static final String STATE_EDITIONS_FETCHED = "state_editions_fetched";
                private static final String STATE_EMAIL = "state_email";
                private static final String STATE_LOCATION = "state_loc";
                private static final String STATE_NAME = "state_name";
                private static final String STATE_PHONE = "state_phone";
                private static final String TAG = "SettingsFragment";
                private ImageView mAlertIcon;
                private AppController mAppController;
                private ImageView mAvatarActionView;
                private ImageView mAvatarImageView;
                private ImageKey mAvatarKey;
                private Uri mAvatarUri;
                private int mBackgroundColor;
                private TextView mClearCacheValue;
                private int mColorIndex;
                private String mDescription;
                private TextView mEditTextEmail;
                private TextView mEditTextPhone;
                private String mEdition;
                private boolean mEditionsFetched = false;
                private Spinner mEditionsSpinner;
                private SharedPreferences.Editor mEditor;
                private String mEmail;
                private ImageView mEmailVerified;
                private int mErrorCode;
                private TextView mFacebookValue;
                private Session mFbSession;
                private ImagePicker mImagePicker;
                private Intent mInviteSmsIntent;
                private boolean mIsEmailVerified;
                private boolean mIsPhoneVerified;
                private String mLocation;
                private String mName;
                private ViewGroup mNotifications;
                private String mOriginalAvatarUrl;
                private int mOriginalBackgroundColor;
                private int mOriginalColor;
                private int mOriginalColorIndex;
                private String mOriginalDescription;
                private String mOriginalEdition;
                private String mOriginalEmail;
                private boolean mOriginalEmailVerified;
                private String mOriginalLocation;
                private String mOriginalName;
                private String mOriginalPhone;
                private boolean mOriginalPhoneVerified;
                private String mPhone;
                private ImageView mPhoneVerified;
                private boolean mPhotoChanged;
                private SharedPreferences mPrefs;
                private View mProfileBackground;
                private ProgressDialog mProgress;
                private ScrollView mScrollView;
                private EditionsSpinnerAdapter mSpinnerAdapter;
                private Bitmap mThumbnail;
                private boolean mTwitterConnected;
                private boolean mTwitterLogin;
                private int mVersionTapCount;

        static  TextView access$000(SettingsFragment x0) {
            return x0.mClearCacheValue;
                }

        static  String access$100(SettingsFragment x0) {
            return x0.mName;
                }

        static  boolean access$1002(SettingsFragment x0, boolean x1) {
            x0.mIsPhoneVerified = x1;
                    return x1;
                }

        static  String access$102(SettingsFragment x0, String x1) {
            x0.mName = x1;
                    return x1;
                }

        static  boolean access$1100(SettingsFragment x0) {
            return x0.mOriginalPhoneVerified;
                }

        static  String access$1200(SettingsFragment x0) {
            return x0.mEdition;
                }

        static  String access$1202(SettingsFragment x0, String x1) {
            x0.mEdition = x1;
                    return x1;
                }

        static  EditionsSpinnerAdapter access$1300(SettingsFragment x0) {
            return x0.mSpinnerAdapter;
                }

        static  int access$1400(SettingsFragment x0) {
            return x0.mBackgroundColor;
                }

        static  View access$1500(SettingsFragment x0) {
            return x0.mProfileBackground;
                }

        static  ImageKey access$1600(SettingsFragment x0) {
            return x0.mAvatarKey;
                }

        static  ImageView access$1700(SettingsFragment x0) {
            return x0.mAvatarImageView;
                }

        static  ProgressDialog access$1800(SettingsFragment x0) {
            return x0.mProgress;
                }

        static  TextView access$1900(SettingsFragment x0) {
            return x0.mFacebookValue;
                }

        static  String access$200(SettingsFragment x0) {
            return x0.mDescription;
                }

        static  void access$2000(SettingsFragment x0) {
            x0.invalidateFacebookSessionUI();
                }

        static  String access$202(SettingsFragment x0, String x1) {
            x0.mDescription = x1;
                    return x1;
                }

        static  SharedPreferences.Editor access$2100(SettingsFragment x0) {
            return x0.mEditor;
                }

        static  int access$2200(SettingsFragment x0) {
            return x0.mColorIndex;
                }

        static  int access$2202(SettingsFragment x0, int x1) {
            x0.mColorIndex = x1;
                    return x1;
                }

        static  String access$2302(SettingsFragment x0, String x1) {
            x0.mOriginalEdition = x1;
                    return x1;
                }

        static  AppController access$2400(SettingsFragment x0) {
            return x0.mAppController;
                }

        static  boolean access$2502(SettingsFragment x0, boolean x1) {
            x0.mTwitterLogin = x1;
                    return x1;
                }

        static  SharedPreferences access$2600(SettingsFragment x0) {
            return x0.mPrefs;
                }

        static  void access$2700(SettingsFragment x0) {
            x0.updateVerificationIcons();
                }

        static  boolean access$2802(SettingsFragment x0, boolean x1) {
            x0.mEditionsFetched = x1;
                    return x1;
                }

        static  Spinner access$2900(SettingsFragment x0) {
            return x0.mEditionsSpinner;
                }

        static  String access$300(SettingsFragment x0) {
            return x0.mLocation;
                }

        static  String access$302(SettingsFragment x0, String x1) {
            x0.mLocation = x1;
                    return x1;
                }

        static  String access$400(SettingsFragment x0) {
            return x0.mEmail;
                }

        static  String access$402(SettingsFragment x0, String x1) {
            x0.mEmail = x1;
                    return x1;
                }

        static  boolean access$502(SettingsFragment x0, boolean x1) {
            x0.mIsEmailVerified = x1;
                    return x1;
                }

        static  String access$600(SettingsFragment x0) {
            return x0.mOriginalEmail;
                }

        static  String access$700(SettingsFragment x0) {
            return x0.mOriginalPhone;
                }

        static  boolean access$800(SettingsFragment x0) {
            return x0.mOriginalEmailVerified;
                }

        static  String access$900(SettingsFragment x0) {
            return x0.mPhone;
                }

        static  String access$902(SettingsFragment x0, String x1) {
            x0.mPhone = x1;
                    return x1;
                }


       private void resetPrefs() {
           SharedPreferences.Editor editor = this.mEditor;
           editor.putString(Settings.PREF_NAME, this.mOriginalName);
           editor.putString(Settings.PREF_DESCRIPTION, this.mOriginalDescription);
           editor.putString(Settings.PREF_LOCATION, this.mOriginalLocation);
           editor.putString(Settings.PREF_EMAIL, this.mOriginalEmail);
           editor.putString(Settings.PREF_PHONE, this.mOriginalPhone);
           editor.putString(Settings.PREF_AVATAR_URL, this.mOriginalAvatarUrl);
           editor.putString(Settings.PREF_EDITION, this.mOriginalEdition);
           editor.putInt(Settings.PREF_COLOR_INDEX, this.mOriginalColorIndex);
           editor.putInt("profile_background", this.mOriginalBackgroundColor);
           editor.commit();
                }

       private boolean isDirty() {
                    return (this.mOriginalName.equals(this.mName) && this.mOriginalEmail.equals(this.mEmail) && this.mOriginalDescription.equals(this.mDescription) && this.mOriginalLocation.equals(this.mLocation) && this.mOriginalPhone.equals(this.mPhone) && this.mOriginalEdition.equals(this.mEdition) && this.mOriginalColorIndex == this.mColorIndex && !this.mPhotoChanged) ? false : true;
                }

                @Override 
       public void onPause() {
           super.onPause();
           if (this.mProgress != null) {
               this.mProgress.dismiss();
                    }
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putString(STATE_NAME, this.mName);
           outState.putString(STATE_DESCRIPTION, this.mDescription);
           outState.putString(STATE_LOCATION, this.mLocation);
           outState.putString(STATE_PHONE, this.mPhone);
           outState.putString(STATE_EMAIL, this.mEmail);
           outState.putString(STATE_EDITION, this.mEdition);
           outState.putBoolean(STATE_EDITIONS_FETCHED, this.mEditionsFetched);
           outState.putParcelable(STATE_AVATAR_URI, this.mAvatarUri);
           outState.putInt(STATE_BACKGROUND_COLOR, this.mBackgroundColor);
           outState.putInt(STATE_COLOR_INDEX, this.mColorIndex);
                }

       private void setOriginalPreferenceValues() {
           SharedPreferences prefs = this.mPrefs;
           this.mOriginalName = prefs.getString(Settings.PREF_NAME, "");
           this.mName = this.mOriginalName;
           this.mOriginalDescription = prefs.getString(Settings.PREF_DESCRIPTION, "");
           this.mDescription = this.mOriginalDescription;
           this.mOriginalLocation = prefs.getString(Settings.PREF_LOCATION, "");
           this.mLocation = this.mOriginalLocation;
           this.mOriginalEmail = prefs.getString(Settings.PREF_EMAIL, "");
           this.mEmail = this.mOriginalEmail;
           this.mOriginalPhone = prefs.getString(Settings.PREF_PHONE, "");
           this.mPhone = this.mOriginalPhone;
           this.mOriginalEdition = prefs.getString(Settings.PREF_EDITION, "");
           this.mEdition = this.mOriginalEdition;
           this.mTwitterConnected = prefs.getBoolean(Settings.PREF_TWITTER_CONNECTED, false);
           this.mAvatarUri = Uri.parse(prefs.getString(Settings.PREF_AVATAR_URL, ""));
           this.mOriginalAvatarUrl = this.mAvatarUri.toString();
           this.mBackgroundColor = prefs.getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
           this.mOriginalBackgroundColor = this.mBackgroundColor;
                    for (int i = 0; i < Settings.PROFILE_BACKGROUND_COLORS.length; i++) {
               if (Settings.PROFILE_BACKGROUND_COLORS[i] == this.mBackgroundColor) {
                   this.mOriginalColorIndex = i;
                            break;
                        }
                    }
           this.mColorIndex = this.mOriginalColorIndex;
           this.mOriginalEmailVerified = prefs.getBoolean(Settings.PREF_EMAIL_VERIFIED, false);
           this.mIsEmailVerified = this.mOriginalEmailVerified;
           this.mOriginalPhoneVerified = prefs.getBoolean(Settings.PREF_PHONE_VERIFIED, false);
           this.mIsPhoneVerified = this.mOriginalPhoneVerified;
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setHasOptionsMenu(true);
           this.mPrefs = Util.getDefaultSharedPrefs(getActivity());
           this.mAppSessionListener = new SettingsListener();
           this.mAppController = AppController.getInstance(getActivity());
           this.mImagePicker = new ImagePicker(getActivity(), this, this.mAppController.getActiveId());
           this.mEditor = this.mPrefs.edit();
                }

                @Override 
       public void onResume() {
           super.onResume();
           this.mAppController.fetchUsersMe(this.mAppController.getActiveId(), UrlCachePolicy.FORCE_REFRESH);
           if (this.mSpinnerAdapter.getCursor() == null) {
               getLoaderManager().initLoader(0, null, this);
                    } else if (this.mSpinnerAdapter.isEmpty() && !this.mEditionsFetched) {
               this.mEditionsFetched = true;
               this.mAppController.getEditions();
                    }
           if (!ClientFlagsHelper.prefetchEnabled(getActivity())) {
               View view = getView();
               view.findViewById(C0257R.id.sync).setVisibility(8);
               view.findViewById(C0257R.id.sync_divider).setVisibility(8);
                    }
                }

       public class GetCacheSizeAsyncTask extends AsyncTask<Void, Void, String> {
           public GetCacheSizeAsyncTask() {
                    }



                    
           protected void onPostExecute(String size) {
               if (size != null) {
                   SettingsFragment.access$000(SettingsFragment.this).setText(size);
                        }
                    }

                    
           protected String doInBackground(Void... params) {
                        try {
                   FragmentActivity activity = SettingsFragment.this.getActivity();
                   if (activity != null) {
                       return Util.formatFileSize(SettingsFragment.this.getResources(), Util.getCacheSize(activity));
                            }
                        } catch (Exception e) {
                   CrashUtil.logException(e);
                        }
               return null;
                    }
                }

                @Override 
                @SuppressLint({"NewApi"})
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View v = inflater.inflate(C0257R.layout.settings, container, false);
           Resources res = getResources();
           setOriginalPreferenceValues();
           if (savedInstanceState != null) {
               this.mName = savedInstanceState.getString(STATE_NAME);
               this.mDescription = savedInstanceState.getString(STATE_DESCRIPTION);
               this.mLocation = savedInstanceState.getString(STATE_LOCATION);
               this.mPhone = savedInstanceState.getString(STATE_PHONE);
               this.mEmail = savedInstanceState.getString(STATE_EMAIL);
               this.mEditionsFetched = savedInstanceState.getBoolean(STATE_EDITIONS_FETCHED);
               this.mEdition = savedInstanceState.getString(STATE_EDITION);
               this.mAvatarUri = (Uri) savedInstanceState.getParcelable(STATE_AVATAR_URI);
               this.mBackgroundColor = savedInstanceState.getInt(STATE_BACKGROUND_COLOR);
               this.mColorIndex = savedInstanceState.getInt(STATE_COLOR_INDEX);
                    }
           this.mScrollView = (ScrollView) v.findViewById(C0257R.id.scrollview);
           this.mEmailVerified = (ImageView) v.findViewById(C0257R.id.email_verified);
           this.mEmailVerified.setOnClickListener(this);
           this.mPhoneVerified = (ImageView) v.findViewById(C0257R.id.phone_verified);
           this.mPhoneVerified.setOnClickListener(this);
           ColorPicker colorPicker = (ColorPicker) v.findViewById(C0257R.id.color_picker);
           colorPicker.setOnColorClickListener(this);
           colorPicker.setColorIndex(this.mColorIndex);
           ViewGroup profileBackground = (ViewGroup) v.findViewById(C0257R.id.user_image_container);
           profileBackground.setBackgroundColor(this.mBackgroundColor | (-16777216));
           this.mProfileBackground = profileBackground;
           setActionBarColor(this.mBackgroundColor);
           TextView privacyControls = (TextView) v.findViewById(C0257R.id.privacy_controls);
           privacyControls.setOnClickListener(this);
           TextView syncControls = (TextView) v.findViewById(C0257R.id.sync);
           syncControls.setOnClickListener(this);
           TextView updatesRow = (TextView) v.findViewById(C0257R.id.updates);
           updatesRow.setOnClickListener(this);
           this.mAvatarImageView = (ImageView) v.findViewById(C0257R.id.user_image);
           this.mAvatarActionView = (ImageView) v.findViewById(C0257R.id.user_image_action);
           this.mAvatarActionView.setVisibility(0);
           String avatarUrl = this.mAvatarUri == null ? "" : this.mAvatarUri.toString();
           this.mAvatarImageView.setOnClickListener(this);
           if (this.mThumbnail != null) {
               setImage(this.mThumbnail, false);
                    } else if (TextUtils.isEmpty(avatarUrl) || Util.isDefaultAvatarUrl(avatarUrl)) {
               setImage(null, false);
                    } else {
               int dimen = res.getDimensionPixelOffset(C0257R.dimen.user_image_size_large);
               this.mAvatarKey = new ImageKey(avatarUrl, dimen, dimen, true);
               Bitmap bmp = this.mAppController.getPhotoBitmap(this.mAvatarKey);
               setImage(bmp, false);
                    }
           EditText editTextName = (EditText) v.findViewById(C0257R.id.name);
           editTextName.setText(this.mName);
           editTextName.addTextChangedListener(new C02751());
           EditText editTextDescription = (EditText) v.findViewById(C0257R.id.description);
           editTextDescription.setText(this.mDescription);
           editTextDescription.setHint(C0257R.string.settings_description);
           editTextDescription.addTextChangedListener(new C02772());
           EditText editTextLocation = (EditText) v.findViewById(C0257R.id.location);
           editTextLocation.setText(this.mLocation);
           editTextLocation.setHint(C0257R.string.settings_location);
           editTextLocation.addTextChangedListener(new C02783());
           TypingEditText editTextEmail = (TypingEditText) v.findViewById(C0257R.id.settings_email);
           editTextEmail.setText(this.mEmail);
           editTextEmail.setInputType(32);
           editTextEmail.setTypingListener(this);
           this.mEditTextEmail = editTextEmail;
           editTextEmail.addTextChangedListener(new C02794());
           TypingEditText editTextPhone = (TypingEditText) v.findViewById(C0257R.id.settings_phone);
           editTextPhone.setText(this.mPhone);
           editTextPhone.setInputType(3);
           editTextPhone.addTextChangedListener(new C02805());
           editTextPhone.setTypingListener(this);
           this.mEditTextPhone = editTextPhone;
           this.mEditionsSpinner = (Spinner) v.findViewById(C0257R.id.edition);
           this.mEditionsSpinner.setFocusable(false);
           this.mEditionsSpinner.setOnItemSelectedListener(new C02816());
           v.findViewById(C0257R.id.edition_container).setOnClickListener(this);
           v.findViewById(C0257R.id.edition_label).setOnClickListener(this);
           this.mSpinnerAdapter = new EditionsSpinnerAdapter();
           this.mEditionsSpinner.setAdapter((SpinnerAdapter) this.mSpinnerAdapter);
           TextView password = (TextView) v.findViewById(C0257R.id.settings_password);
           password.setOnClickListener(this);
           TextView content = (TextView) v.findViewById(C0257R.id.content_controls);
           content.setOnClickListener(this);
           this.mNotifications = (ViewGroup) v.findViewById(C0257R.id.notification_settings);
           if (BuildUtil.isGoogle()) {
               this.mNotifications.setVisibility(0);
               this.mAlertIcon = (ImageView) this.mNotifications.findViewById(C0257R.id.alert_icon);
               this.mErrorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
               if (this.mErrorCode != 0) {
                   this.mAlertIcon.setVisibility(0);
                   this.mAlertIcon.setOnClickListener(this);
                        } else {
                   this.mAlertIcon.setVisibility(8);
                        }
               this.mNotifications.setOnClickListener(this);
                    } else {
               this.mNotifications.setVisibility(8);
                    }
           TextView findFriends = (TextView) v.findViewById(C0257R.id.find_friends);
           findFriends.setOnClickListener(this);
           TextView inviteViaText = (TextView) v.findViewById(C0257R.id.invite_via_text);
           inviteViaText.setOnClickListener(this);
           TextView inviteViaEmail = (TextView) v.findViewById(C0257R.id.invite_via_email);
           inviteViaEmail.setOnClickListener(this);
           RelativeLayout twitterConnect = (RelativeLayout) v.findViewById(C0257R.id.twitter_connect);
           twitterConnect.setBackgroundResource(C0257R.drawable.bg_selectable_item);
           TextView twitterConnectedTextView = (TextView) twitterConnect.findViewById(C0257R.id.twitter_connect_value);
           AccountManager am = AccountManager.get(getActivity());
           Account acc = VineAccountHelper.getAccount(getActivity(), this.mAppController.getActiveSession().getLoginEmail());
           if (acc != null && VineAccountHelper.getLoginType(am, acc).intValue() == 2) {
               twitterConnectedTextView.setText('@' + VineAccountHelper.getTwitterUsername(am, acc));
               this.mTwitterLogin = true;
                    } else if (this.mTwitterConnected) {
               twitterConnectedTextView.setText("");
               this.mTwitterLogin = true;
                    } else {
               twitterConnectedTextView.setText(C0257R.string.settings_connect_to_twitter_summary);
               this.mTwitterLogin = false;
                    }
           twitterConnect.setOnClickListener(this);
           RelativeLayout facebookConnect = (RelativeLayout) v.findViewById(C0257R.id.facebook_connect);
           facebookConnect.setOnClickListener(this);
           TextView facebookLabel = (TextView) v.findViewById(C0257R.id.facebook_label);
           facebookLabel.setText(C0257R.string.facebook);
           this.mFacebookValue = (TextView) v.findViewById(C0257R.id.facebook_value);
           invalidateFacebookSessionUI();
           facebookConnect.setOnClickListener(this);
           RelativeLayout clearCache = (RelativeLayout) v.findViewById(C0257R.id.clear_cache);
           clearCache.setOnClickListener(this);
           TextView clearCacheLabel = (TextView) v.findViewById(C0257R.id.clear_cache_label);
           clearCacheLabel.setText(getString(C0257R.string.settings_cache_size));
           this.mClearCacheValue = (TextView) v.findViewById(C0257R.id.clear_cache_value);
           new GetCacheSizeAsyncTask().execute(new Void[0]);
           TextView termsOfService = (TextView) v.findViewById(C0257R.id.terms_of_service);
           termsOfService.setOnClickListener(this);
           TextView privacyPolicy = (TextView) v.findViewById(C0257R.id.privacy_policy);
           privacyPolicy.setOnClickListener(this);
           TextView attribution = (TextView) v.findViewById(C0257R.id.attribution);
           attribution.setOnClickListener(this);
           TextView deactivateAccount = (TextView) v.findViewById(C0257R.id.deactivate_account);
           deactivateAccount.setOnClickListener(this);
           Button logout = (Button) v.findViewById(C0257R.id.logout);
           logout.setOnClickListener(this);
           LinearLayout version = (LinearLayout) v.findViewById(C0257R.id.version);
           TextView tv = (TextView) v.findViewById(C0257R.id.version_number);
                    try {
               PackageInfo pi = getActivity().getPackageManager().getPackageInfo("co.vine.android", 0);
               tv.setText(getString(C0257R.string.settings_about_summary, pi.versionName));
                    } catch (PackageManager.NameNotFoundException e) {
               Log.d(TAG, "Error retrieving package info:", e);
                    }
           version.setOnClickListener(this);
           if (BuildUtil.isI18nOn()) {
               TextView locale = (TextView) v.findViewById(C0257R.id.locale);
               locale.setVisibility(0);
               locale.setOnClickListener(this);
                    }
           TextView favoritePeople = (TextView) v.findViewById(C0257R.id.favorite_people);
           favoritePeople.setOnClickListener(new ViewOnClickListenerC02827());
           return v;
                }

                
       class C02751 extends SimpleTextWatcher {
           C02751() {
                    }

                    @Override 
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               SettingsFragment.access$102(SettingsFragment.this, s.toString());
                    }
                }

                
       class C02772 extends SimpleTextWatcher {
           C02772() {
                    }

                    @Override 
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               SettingsFragment.access$202(SettingsFragment.this, s.toString());
                    }
                }

                
       class C02783 extends SimpleTextWatcher {
           C02783() {
                    }

                    @Override 
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               SettingsFragment.access$302(SettingsFragment.this, s.toString());
                    }
                }

                
       class C02794 extends SimpleTextWatcher {
           C02794() {
                    }

                    @Override 
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               SettingsFragment.access$402(SettingsFragment.this, s.toString());
               SettingsFragment.access$502(SettingsFragment.this, (SettingsFragment.access$600(SettingsFragment.this) == null || SettingsFragment.access$700(SettingsFragment.this).equals(SettingsFragment.access$400(SettingsFragment.this))) && SettingsFragment.access$800(SettingsFragment.this));
                    }
                }

                
       class C02805 extends SimpleTextWatcher {
           C02805() {
                    }

                    @Override 
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               SettingsFragment.access$902(SettingsFragment.this, s.toString());
               SettingsFragment.access$1002(SettingsFragment.this, (SettingsFragment.access$700(SettingsFragment.this) == null || SettingsFragment.access$700(SettingsFragment.this).equals(SettingsFragment.access$900(SettingsFragment.this))) && SettingsFragment.access$1100(SettingsFragment.this));
                    }
                }

                
       class C02816 implements AdapterView.OnItemSelectedListener {
           C02816() {
                    }

                    @Override 
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               SettingsFragment.access$1202(SettingsFragment.this, SettingsFragment.access$1300(SettingsFragment.this).getEditionCode(i));
                    }

                    @Override 
           public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                }

                
       class ViewOnClickListenerC02827 implements View.OnClickListener {
           ViewOnClickListenerC02827() {
                    }

                    @Override 
           public void onClick(View view) {
               FavoritePeopleActivity.start(SettingsFragment.this.getActivity(), SettingsFragment.access$1400(SettingsFragment.this));
                    }
                }

       private void invalidateFacebookSessionUI() {
           this.mFbSession = this.mAppController.getValidFacebookSession(getActivity(), true);
           if (this.mFbSession != null) {
               this.mFacebookValue.setText(C0257R.string.settings_connected);
                    } else {
               this.mFacebookValue.setText(C0257R.string.settings_not_connected);
                    }
                }

                @Override 
       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                }

       public void onBackPressed() {
           FragmentActivity activity = getActivity();
           if (isDirty() && activity != null) {
               PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(3);
               p.setMessage(C0257R.string.settings_unsaved_changes);
               p.setNeutralButton(C0257R.string.settings_continue_editing);
               p.setPositiveButton(C0257R.string.settings_discard);
               p.setTargetFragment(this, 0);
               p.show(activity.getSupportFragmentManager());
               return;
                    }
           if (this.mProgress != null) {
               this.mProgress.dismiss();
                    }
           if (activity != null) {
               activity.finish();
                    }
                }

                @Override 
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           inflater.inflate(C0257R.menu.save, menu);
           MenuItem menuItem = menu.findItem(C0257R.id.done);
           menuItem.setEnabled(true);
           menu.findItem(C0257R.id.done).setEnabled(true);
                }

                @Override 
       public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
           return new CursorLoader(getActivity(), Vine.Editions.CONTENT_URI, VineDatabaseSQL.EditionsQuery.PROJECTION, null, null, null);
                }

       public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
           if (!this.mEditionsFetched) {
               this.mAppController.getEditions();
               this.mEditionsFetched = true;
                    }
           this.mSpinnerAdapter.changeCursor(cursor);
           this.mSpinnerAdapter.setSelectionToUserEdition();
                }

                @Override 
       public void onLoaderReset(Loader<Cursor> cursorLoader) {
                }

                @Override 
       public void onTypingTimeout(View v) {
           int id = v.getId();
           if (id == C0257R.id.settings_email || id == C0257R.id.settings_phone) {
               updateVerificationIcons();
                    }
                }

                @Override 
                @TargetApi(11)
       public void onColorClick(int colorIndex) {
                    int origColor;
           if (this.mColorIndex >= 0) {
               origColor = Settings.PROFILE_BACKGROUND_COLORS[this.mColorIndex];
                    } else {
               origColor = Settings.DEFAULT_PROFILE_COLOR;
                    }
           this.mColorIndex = colorIndex;
           this.mBackgroundColor = Settings.PROFILE_BACKGROUND_COLORS[this.mColorIndex];
           ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(origColor | (-16777216)), Integer.valueOf(this.mBackgroundColor | (-16777216)));
           animator.addUpdateListener(new C02838(animator));
           animator.setDuration(100L);
           animator.start();
           Intent intent = new Intent(VineService.ACTION_PROFILE_COLOR_CHANGED);
           getActivity().sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                }

                
       class C02838 implements ValueAnimator.AnimatorUpdateListener {
                    final  ValueAnimator val$animator;

           C02838(ValueAnimator valueAnimator) {
                        this.val$animator = valueAnimator;
                    }

                    @Override 
           public void onAnimationUpdate(ValueAnimator animation) {
               SettingsFragment.access$1500(SettingsFragment.this).setBackgroundColor(((Integer) this.val$animator.getAnimatedValue()).intValue());
               SettingsFragment.this.setActionBarColor(((Integer) this.val$animator.getAnimatedValue()).intValue());
                    }
                }

       class SettingsListener extends AppSessionListener {
           SettingsListener() {
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               UrlImage urlImage = images.get(SettingsFragment.access$1600(SettingsFragment.this));
               if (urlImage != null && urlImage.isValid()) {
                   SettingsFragment.access$1700(SettingsFragment.this).setImageDrawable(new RecyclableBitmapDrawable(SettingsFragment.access$1700(SettingsFragment.this).getResources(), urlImage.bitmap));
                        }
                    }

                    @Override 
           public void onConnectFacebookComplete(String reqId, int statusCode, String reasonPhrase) {
               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
               if (statusCode != 200) {
                   Util.showCenteredToast(SettingsFragment.this.getActivity(), C0257R.string.error_facebook_send_token);
                   SettingsFragment.access$1900(SettingsFragment.this).setText(C0257R.string.settings_not_connected);
                        } else {
                   SettingsFragment.access$2000(SettingsFragment.this);
                        }
                    }

                    @Override 
           public void onUpdateProfileComplete(String reqId, int statusCode, String reasonPhrase, String avatarUrl) {
               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
               FragmentActivity activity = SettingsFragment.this.getActivity();
               if (statusCode == 200) {
                   SharedPreferences.Editor editor = SettingsFragment.access$2100(SettingsFragment.this);
                   if (!TextUtils.isEmpty(avatarUrl)) {
                       SettingsFragment.access$2100(SettingsFragment.this).putString(Settings.PREF_AVATAR_URL, avatarUrl);
                            }
                   editor.putString(Settings.PREF_NAME, SettingsFragment.access$100(SettingsFragment.this));
                   editor.putString(Settings.PREF_DESCRIPTION, SettingsFragment.access$200(SettingsFragment.this));
                   editor.putString(Settings.PREF_LOCATION, SettingsFragment.access$300(SettingsFragment.this));
                   editor.putString(Settings.PREF_EMAIL, SettingsFragment.access$400(SettingsFragment.this));
                   editor.putString(Settings.PREF_PHONE, SettingsFragment.access$900(SettingsFragment.this));
                   if (SettingsFragment.access$2200(SettingsFragment.this) < 0 || SettingsFragment.access$2200(SettingsFragment.this) >= Settings.PROFILE_BACKGROUND_COLORS.length) {
                       SettingsFragment.access$2202(SettingsFragment.this, 0);
                            }
                   editor.putInt("profile_background", Settings.PROFILE_BACKGROUND_COLORS[SettingsFragment.access$2200(SettingsFragment.this)]);
                   editor.putInt(Settings.PREF_COLOR_INDEX, SettingsFragment.access$2200(SettingsFragment.this));
                   editor.apply();
                   if (activity != null) {
                       activity.sendBroadcast(new Intent(VineService.ACTION_PROFILE_COLOR_CHANGED), CrossConstants.BROADCAST_PERMISSION);
                       activity.finish();
                       return;
                            }
                   return;
                        }
               if (TextUtils.isEmpty(reasonPhrase)) {
                   Util.showCenteredToast(activity, C0257R.string.update_profile_error);
                        } else {
                   Util.showCenteredToast(activity, reasonPhrase);
                        }
                    }

                    @Override 
           public void onUpdateEditionComplete(String reqId, int statusCode, String reasonPhrase, String edition) {
               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
               if (statusCode == 200) {
                   SettingsFragment.access$1202(SettingsFragment.this, edition);
                   SettingsFragment.access$2302(SettingsFragment.this, edition);
                        }
                    }

                    @Override 
           public void onClearCacheComplete(String reqId, int statusCode, String reasonPhrase) {
               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
                        try {
                   SettingsFragment.access$000(SettingsFragment.this).setText(Util.formatFileSize(SettingsFragment.this.getResources(), Util.getCacheSize(SettingsFragment.this.getActivity())));
                        } catch (VineLoggingException e) {
                   CrashUtil.logException(e);
                        }
               SettingsFragment.this.getActivity().setResult(1);
                    }

                    @Override 
           public void onConnectTwitterComplete(String reqId, int statusCode, String reasonPhrase, String username, String token, String secret, long userId) {
               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
               TextView valueTextView = (TextView) SettingsFragment.this.getView().findViewById(C0257R.id.twitter_connect_value);
               TextView label = (TextView) SettingsFragment.this.getView().findViewById(C0257R.id.twitter_connect_label);
               if (statusCode == 200 && userId > 0 && !TextUtils.isEmpty(token) && !TextUtils.isEmpty(secret)) {
                   label.setText(C0257R.string.settings_connected_to_twitter);
                   valueTextView.setText('@' + username);
                   VineAccountHelper.saveTwitterInfo(SettingsFragment.this.getActivity(), SettingsFragment.access$2400(SettingsFragment.this).getActiveSession().getLoginEmail(), username, token, secret, userId);
                   SettingsFragment.access$2502(SettingsFragment.this, true);
                   SettingsFragment.access$2600(SettingsFragment.this).edit().putBoolean(Settings.PREF_TWITTER_CONNECTED, true).apply();
                   return;
                        }
               label.setText(C0257R.string.settings_connect_to_twitter);
               valueTextView.setText(C0257R.string.settings_connect_to_twitter_summary);
               SettingsFragment.access$2502(SettingsFragment.this, false);
               if (TextUtils.isEmpty(reasonPhrase)) {
                   reasonPhrase = SettingsFragment.this.getResources().getString(C0257R.string.settings_error_connect_to_twitter);
                        }
               Util.showCenteredToast(SettingsFragment.this.getActivity(), reasonPhrase);
                    }

                    @Override 
           public void onDisconnectTwitterComplete(String reqId, int statusCode, String reasonPhrase) {
               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
               TextView valueTextView = (TextView) SettingsFragment.this.getView().findViewById(C0257R.id.twitter_connect_value);
               TextView label = (TextView) SettingsFragment.this.getView().findViewById(C0257R.id.twitter_connect_label);
               if (statusCode == 200) {
                   label.setText(C0257R.string.settings_connect_to_twitter);
                   valueTextView.setText(C0257R.string.settings_connect_to_twitter_summary);
                   VineAccountHelper.removeTwitterInfo(SettingsFragment.this.getActivity(), SettingsFragment.access$2400(SettingsFragment.this).getActiveSession().getLoginEmail());
                   SettingsFragment.access$2502(SettingsFragment.this, false);
                   SettingsFragment.access$2100(SettingsFragment.this).putBoolean(Settings.PREF_TWITTER_CONNECTED, false).apply();
                   return;
                        }
               if (TextUtils.isEmpty(reasonPhrase)) {
                   reasonPhrase = SettingsFragment.this.getResources().getString(C0257R.string.settings_error_disconnect_from_twitter);
                        }
               Util.showCenteredToast(SettingsFragment.this.getActivity(), reasonPhrase);
                    }

                    @Override 
           public void onLogoutComplete(String reqId) {
               if (SettingsFragment.this.getActivity() != null && !SettingsFragment.this.getActivity().isFinishing()) {
                   if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
                       SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                            }
                   StartActivity.toStart(SettingsFragment.this.getActivity());
                        }
                    }

                    @Override 
           public void onDeactivateAccountComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
                        int messageId;
               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
               if (success) {
                   SettingsFragment.access$2400(SettingsFragment.this).logout();
                   messageId = C0257R.string.settings_account_deactivation_success;
                   SLog.m446d("User account successfully deactivated");
                        } else {
                   messageId = C0257R.string.settings_account_deactivation_fail;
                   SLog.m446d("User account deactivation failed");
                        }
               Util.showCenteredToast(SettingsFragment.this.getActivity(), messageId);
                    }

                    @Override 
           public void onGetUsersMeComplete(String reqId, int statusCode, String reasonPhrase, long sessionOwnerId, VineUser meUser, UrlCachePolicy policy) {
               if (meUser != null) {
                   SettingsFragment.access$502(SettingsFragment.this, meUser.isEmailVerified());
                   SettingsFragment.access$1002(SettingsFragment.this, meUser.isPhoneVerified());
                   SettingsFragment.access$2700(SettingsFragment.this);
                        }
                    }

                    @Override 
           public void onGetEditionsComplete(int statusCode, ArrayList<Pair<String, String>> editions) {
               if (statusCode == 200) {
                   SettingsFragment.access$2802(SettingsFragment.this, true);
                        }
                    }

                    @Override 
           public void onRequestEmailVerificationComplete(String reqId, int statusCode, String reasonPhrase, String email) {
               if (statusCode == 200) {
                   PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(10);
                   p.setTargetFragment(SettingsFragment.this, 0);
                   p.setNeutralButton(android.R.string.ok).setMessage(C0257R.string.confirm_email_sent).show(SettingsFragment.this.getActivity().getSupportFragmentManager());
                        } else {
                   String reason = reasonPhrase != null ? reasonPhrase : SettingsFragment.this.getString(C0257R.string.generic_error);
                   Util.showCenteredToast(SettingsFragment.this.getActivity(), reason);
                        }
                    }
                }

                @Override 
       public void setThumbnailImage(Bitmap bitmap) {
           setImage(bitmap, true);
                }

       private void setImage(Bitmap bmp, boolean edited) {
           this.mThumbnail = bmp;
           if (bmp == null) {
               this.mAvatarImageView.setImageResource(C0257R.drawable.avatar_large);
               this.mAvatarActionView.setImageResource(C0257R.drawable.avatar_add);
                    } else {
               this.mAvatarActionView.setImageResource(edited ? C0257R.drawable.avatar_check : C0257R.drawable.avatar_edit);
               this.mAvatarImageView.setImageDrawable(new RecyclableBitmapDrawable(this.mAvatarImageView.getResources(), bmp));
                    }
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           Bundle args = getArguments();
           if (args != null) {
               String action = args.getString(BaseFragment.ARG_ACTION);
               if (HomeTabActivity.ACTION_VERIFICATION_COMPLETE.equals(action)) {
                   ScrollView scrollView = this.mScrollView;
                   View verified = scrollView.findViewById(C0257R.id.settings_phone_label);
                   scrollView.post(new RunnableC02849(scrollView, verified));
                        }
                    }
           this.mInviteSmsIntent = new Intent("android.intent.action.VIEW");
           this.mInviteSmsIntent.setType("vnd.android-dir/mms-sms");
           this.mInviteSmsIntent.putExtra("sms_body", ShareUtils.getSmsMessage(getActivity()));
           if (this.mInviteSmsIntent.resolveActivity(getActivity().getPackageManager()) == null) {
               this.mScrollView.findViewById(C0257R.id.invite_via_text).setVisibility(8);
               this.mScrollView.findViewById(C0257R.id.invite_via_text_divider).setVisibility(8);
                    }
                }

                
       class RunnableC02849 implements Runnable {
                    final  ScrollView val$scrollView;
                    final  View val$verified;

           RunnableC02849(ScrollView scrollView, View view) {
                        this.val$scrollView = scrollView;
                        this.val$verified = view;
                    }

                    @Override 
           public void run() {
               this.val$scrollView.smoothScrollTo(0, this.val$verified.getBottom());
                    }
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent extras) {
                    Uri uri;
           super.onActivityResult(requestCode, resultCode, extras);
                    switch (requestCode) {
                        case 1:
                  if (resultCode == -1) {
                      String username = extras.getStringExtra("screen_name");
                      String token = extras.getStringExtra(Twitter.EXTRA_TOKEN);
                      String secret = extras.getStringExtra(Twitter.EXTRA_TOKEN_SECRET);
                      long userId = extras.getLongExtra("user_id", 0L);
                      ProgressDialog d = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
                      d.setMessage(getString(C0257R.string.sign_up_authorizing));
                      d.setProgress(0);
                      d.show();
                      this.mProgress = d;
                      this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username, token, secret, userId);
                            } else {
                      Util.showCenteredToast(getActivity(), C0257R.string.error_twitter_sdk);
                            }
                            break;
                        case 2:
                  if (resultCode == -1) {
                      String username2 = extras.getStringExtra("screen_name");
                      String token2 = extras.getStringExtra("token");
                      String secret2 = extras.getStringExtra("secret");
                      long userId2 = extras.getLongExtra("user_id", 0L);
                      ProgressDialog d2 = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
                      d2.setMessage(getString(C0257R.string.sign_up_authorizing));
                      d2.setProgress(0);
                      d2.show();
                      this.mProgress = d2;
                      this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username2, token2, secret2, userId2);
                            } else if (resultCode != 0) {
                      Util.showCenteredToast(getActivity(), C0257R.string.error_xauth);
                            }
                            break;
                        case 3:
                        case 5:
                   if (resultCode == -1) {
                       if (requestCode == 3) {
                           uri = this.mAvatarUri;
                                } else {
                           uri = extras.getData();
                                }
                       Intent intent = new Intent(getActivity(), (Class<?>) EditProfileCropActivity.class).putExtra("uri", uri);
                       startActivityForResult(intent, 4);
                            }
                            break;
                        case 4:
                  if (resultCode == -1 && extras != null) {
                      Uri croppedUri = (Uri) extras.getParcelableExtra("uri");
                      if (croppedUri != null) {
                          ImageUtils.deleteTempPic(this.mAvatarUri);
                          this.mAvatarUri = croppedUri;
                          new SetThumbnailTask(this).execute(croppedUri);
                                }
                      this.mPhotoChanged = true;
                                break;
                            }
                            break;
                        case 6:
                  Activity activity = getActivity();
                  int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
                  if (errorCode == 0) {
                      if (this.mNotifications != null) {
                          this.mNotifications.setOnClickListener(this);
                                }
                      if (this.mAlertIcon != null) {
                          this.mAlertIcon.setVisibility(8);
                                }
                      getActivity().startService(GCMRegistrationService.getRegisterIntent(activity, this.mAppController.getActiveId()));
                            } else {
                      Util.showCenteredToast(getActivity(), C0257R.string.toast_gms_resolution_failed);
                            }
                            break;
                        case 7:
                            switch (resultCode) {
                                case ConfirmationFlowActivity.RESULT_VERIFY_PHONE_SUCCESS :
                          this.mPrefs.edit().putBoolean(Settings.PREF_PHONE_VERIFIED, true).apply();
                          this.mIsPhoneVerified = true;
                          updateVerificationIcons();
                                    break;
                                case ConfirmationFlowActivity.RESULT_VERIFY_PHONE_FAILURE :
                          this.mIsPhoneVerified = false;
                          this.mPrefs.edit().putBoolean(Settings.PREF_PHONE_VERIFIED, false).apply();
                          updateVerificationIcons();
                                    break;
                            }
                            break;
                        default:
                  SLog.m447d("Facebook auth came back: {}", Integer.valueOf(requestCode));
                  if (resultCode == -1) {
                      Session session = Session.getActiveSession();
                      if (session != null) {
                          if (session.onActivityResult(getActivity(), requestCode, resultCode, extras)) {
                              if (session.getPermissions().contains(AppController.PUBLISH_ACTIONS)) {
                                  AppController.setPendingFacebookToken(getActivity(), session.getAccessToken());
                                  ProgressDialog d3 = new ProgressDialog(getActivity(), 2);
                                  d3.setMessage(getString(C0257R.string.login_logging_in));
                                  d3.setProgress(0);
                                  d3.show();
                                  this.mProgress = d3;
                                  this.mAppController.sendFacebookToken();
                                        } else {
                                  getActivity().runOnUiThread(new RunnableC027610());
                                  this.mAppController.connectToFacebookPublish(this);
                                        }
                                    } else {
                              invalidateFacebookSessionUI();
                                    }
                                } else {
                          invalidateFacebookSessionUI();
                          super.onActivityResult(requestCode, resultCode, extras);
                                }
                            } else {
                      invalidateFacebookSessionUI();
                            }
                            break;
                    }
                }

                
      class RunnableC027610 implements Runnable {
          RunnableC027610() {
                    }

                    @Override 
          public void run() {
              Util.showCenteredToast(SettingsFragment.this.getActivity(), C0257R.string.publish_to_facebook_toast);
                    }
                }

                @Override 
      public void setAvatarUrl(Uri url) {
          this.mAvatarUri = url;
                }

                @Override 
      public boolean onOptionsItemSelected(MenuItem item) {
          if (item.getItemId() == C0257R.id.done) {
              if (isDirty()) {
                  updateProfile(this.mAvatarUri);
                        } else {
                  if (this.mProgress != null) {
                      this.mProgress.dismiss();
                            }
                  if (getActivity() != null) {
                      getActivity().finish();
                            }
                        }
              return true;
                    }
          return super.onOptionsItemSelected(item);
                }

                @Override 
      public void onDialogDone(DialogInterface dialog, int id, int which) {
                    switch (id) {
                        case 1:
                            switch (which) {
                                case -3:
                          this.mImagePicker.chooseImage(5);
                                    break;
                                case -1:
                          this.mImagePicker.captureImage(3);
                                    break;
                            }
                            break;
                        case 2:
                            switch (which) {
                                case -3:
                          this.mImagePicker.captureImage(3);
                                    break;
                                case -2:
                          this.mImagePicker.chooseImage(5);
                                    break;
                                case -1:
                          setThumbnailImage(null);
                          this.mPhotoChanged = true;
                          this.mAvatarUri = Uri.parse("");
                                    break;
                            }
                            break;
                        case 3:
                            switch (which) {
                                case -1:
                          resetPrefs();
                          FragmentActivity activity = getActivity();
                          if (activity != null) {
                              activity.finish();
                                    }
                                    break;
                            }
                            break;
                        case 4:
                            switch (which) {
                                case -1:
                          FlurryUtils.trackLogout();
                          if (this.mFbSession != null) {
                              this.mFbSession.closeAndClearTokenInformation();
                                    }
                          invalidateFacebookSessionUI();
                          ProgressDialog d = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
                          d.setMessage(getString(C0257R.string.logging_out));
                          d.setProgress(0);
                          d.show();
                          this.mProgress = d;
                          this.mAppController.logout();
                                    break;
                            }
                            break;
                        case 5:
                            switch (which) {
                                case -1:
                          ProgressDialog d2 = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
                          d2.setMessage(getString(C0257R.string.settings_disconnecting_twitter));
                          d2.setProgress(0);
                          d2.show();
                          this.mProgress = d2;
                          this.mAppController.disconnectTwitter(this.mAppController.getActiveSession());
                                    break;
                            }
                            break;
                        case 6:
                            switch (which) {
                                case -1:
                          ProgressDialog d3 = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
                          d3.setMessage(getString(C0257R.string.settings_clearing_cache));
                          d3.setProgress(0);
                          d3.show();
                          this.mProgress = d3;
                          this.mAppController.clearDbCache(true);
                                    break;
                            }
                            break;
                        case 7:
                            switch (which) {
                                case -1:
                          ProgressDialog d4 = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
                          d4.setMessage(getString(C0257R.string.settings_deactivating_account));
                          d4.setProgress(0);
                          d4.show();
                          this.mProgress = d4;
                          this.mAppController.deactivateAccount();
                                    break;
                            }
                            break;
                        case 8:
                            switch (which) {
                                case -1:
                          CharSequence email = this.mEditTextEmail.getText();
                          if (!TextUtils.isEmpty(email)) {
                              this.mAppController.requestEmailVerification(this.mAppController.getActiveSession(), email.toString(), this.mAppController.getActiveId());
                                    }
                                    break;
                            }
                            break;
                        case 9:
                            switch (which) {
                                case -1:
                          CharSequence phone = this.mEditTextPhone.getText();
                          if (!TextUtils.isEmpty(phone)) {
                              this.mAppController.requestPhoneVerification(this.mAppController.getActiveSession(), phone.toString(), this.mAppController.getActiveId());
                                    }
                                    break;
                            }
                            break;
                        case 11:
                            switch (which) {
                                case -1:
                          if (this.mFbSession != null) {
                              this.mFbSession.closeAndClearTokenInformation();
                              this.mFbSession = null;
                                    }
                          invalidateFacebookSessionUI();
                                    break;
                            }
                            break;
                    }
                }

                @Override 
      public void onClick(View view) {
          int id = view.getId();
          if (id == C0257R.id.facebook_connect) {
              if (this.mFbSession != null) {
                  PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(11);
                  p.setTargetFragment(this, 0);
                  p.setTitle(C0257R.string.settings_disconnect_dialog).setPositiveButton(C0257R.string.yes).setNegativeButton(C0257R.string.cancel);
                            try {
                      p.show(getActivity().getSupportFragmentManager());
                      return;
                            } catch (Exception e) {
                      CrashUtil.logException(e);
                      return;
                            }
                        }
              Session session = this.mAppController.getValidFacebookSession(getActivity(), false);
              if (session == null) {
                  this.mAppController.connectToFacebookInitialize(this);
                  return;
                        } else {
                  if (this.mAppController.getPendingFacebookToken() != null) {
                      this.mAppController.sendFacebookToken();
                      return;
                            }
                  return;
                        }
                    }
          if (id == C0257R.id.user_image) {
              if (this.mAvatarUri != null && !Util.isDefaultAvatarUrl(this.mAvatarUri.toString())) {
                  PromptDialogSupportFragment p2 = PromptDialogSupportFragment.newInstance(2);
                  p2.setTargetFragment(this, 0);
                  p2.setTitle(C0257R.string.sign_up_profile_photo).setPositiveButton(C0257R.string.remove_photo).setNeutralButton(C0257R.string.take_photo).setNegativeButton(C0257R.string.choose_photo);
                            try {
                      p2.show(getActivity().getSupportFragmentManager());
                      return;
                            } catch (Exception e2) {
                      CrashUtil.logException(e2);
                      return;
                            }
                        }
              PromptDialogSupportFragment p3 = PromptDialogSupportFragment.newInstance(1);
              p3.setTargetFragment(this, 0);
              p3.setTitle(C0257R.string.sign_up_profile_photo).setPositiveButton(C0257R.string.take_photo).setNeutralButton(C0257R.string.choose_photo);
                        try {
                  p3.show(getActivity().getSupportFragmentManager());
                  return;
                        } catch (Exception e3) {
                  CrashUtil.logException(e3);
                  return;
                        }
                    }
          if (id == C0257R.id.twitter_connect) {
              if (this.mTwitterLogin) {
                  PromptDialogSupportFragment p4 = PromptDialogSupportFragment.newInstance(5);
                  p4.setMessage(C0257R.string.settings_disconnect_twitter);
                  p4.setPositiveButton(C0257R.string.settings_disconnect_dialog);
                  p4.setNegativeButton(C0257R.string.cancel);
                  p4.setTargetFragment(this, 0);
                            try {
                      p4.show(getActivity().getSupportFragmentManager());
                      return;
                            } catch (Exception e4) {
                      CrashUtil.logException(e4);
                      return;
                            }
                        }
              Twitter twitter = this.mAppController.getTwitter();
              Activity activity = getActivity();
              AppController.startTwitterAuthWithFinish(twitter, activity);
              return;
                    }
          if (id == C0257R.id.logout) {
              PromptDialogSupportFragment p5 = PromptDialogSupportFragment.newInstance(4);
              p5.setMessage(C0257R.string.logout_confirm);
              p5.setPositiveButton(C0257R.string.logout);
              p5.setNegativeButton(C0257R.string.cancel);
              p5.setTargetFragment(this, 0);
                        try {
                  p5.show(getActivity().getSupportFragmentManager());
                  return;
                        } catch (Exception e5) {
                  CrashUtil.logException(e5);
                  return;
                        }
                    }
          if (id == C0257R.id.settings_password) {
              Intent i = new Intent(getActivity(), (Class<?>) ResetPasswordActivity.class);
              if (!TextUtils.isEmpty(this.mEmail)) {
                  i.putExtra("email", this.mEmail);
                  i.putExtra("color", (-16777216) | this.mBackgroundColor);
                        }
              startActivity(i);
              return;
                    }
          if (id == C0257R.id.content_controls) {
              FlurryUtils.trackContentControls();
              Intent intent = new Intent(getActivity(), (Class<?>) ContentControlsActivity.class);
              intent.putExtra("color", this.mBackgroundColor);
              startActivity(intent);
              return;
                    }
          if (id == C0257R.id.privacy_controls) {
              Intent intent2 = new Intent(getActivity(), (Class<?>) PrivacyControlsActivity.class);
              intent2.putExtra("color", this.mBackgroundColor);
              startActivity(intent2);
              return;
                    }
          if (id == C0257R.id.sync) {
              Intent intent3 = new Intent(getActivity(), (Class<?>) SyncControlsActivity.class);
              intent3.putExtra("color", this.mBackgroundColor);
              startActivity(intent3);
              return;
                    }
                              if (id == C0257R.id.updates) {
                        startActivity(new Intent(getActivity(), (Class<?>) UpdatesActivity.class));
                        return;
                    }
                    if (id == C0257R.id.find_friends) {
              FlurryUtils.trackVisitFindFriends("Settings");
              startActivity(new Intent(getActivity(), (Class<?>) FindFriendsActivity.class));
              return;
                    }
          if (id == C0257R.id.invite_via_email) {
              FlurryUtils.trackInvite("email", "Settings");
              Intent intent4 = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", "", null));
              intent4.putExtra("android.intent.extra.SUBJECT", getString(C0257R.string.find_friends_invite_email_subject));
              intent4.putExtra("android.intent.extra.TEXT", ShareUtils.getEmailMessage(getActivity()));
              startActivity(Intent.createChooser(intent4, getString(C0257R.string.find_friends_email_choose_title)));
              return;
                    }
          if (id == C0257R.id.invite_via_text) {
              FlurryUtils.trackInvite("sms", "Settings");
              startActivity(Intent.createChooser(this.mInviteSmsIntent, getString(C0257R.string.send)));
              return;
                    }
          if (id == C0257R.id.clear_cache) {
              PromptDialogSupportFragment p6 = PromptDialogSupportFragment.newInstance(6);
              p6.setMessage(C0257R.string.settings_clear_cache_confirm_dialog);
              p6.setNegativeButton(C0257R.string.cancel);
              p6.setPositiveButton(C0257R.string.settings_clear_cache_confirm_button);
              p6.setTargetFragment(this, 0);
              p6.show(getActivity().getSupportFragmentManager());
              return;
                    }
          if (id == C0257R.id.privacy_policy) {
              FlurryUtils.trackPrivacyPolicy();
              Intent i2 = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
              i2.putExtra("type", 1);
              startActivity(i2);
              return;
                    }
          if (id == C0257R.id.terms_of_service) {
              FlurryUtils.trackTos();
              Intent i3 = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
              i3.putExtra("type", 2);
              startActivity(i3);
              return;
                    }
          if (id == C0257R.id.attribution) {
              FlurryUtils.trackAttribution();
              Intent i4 = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
              i4.putExtra("type", 5);
              startActivity(i4);
              return;
                    }
          if (id == C0257R.id.deactivate_account) {
              FlurryUtils.trackDeactivateAccount();
              PromptDialogSupportFragment p7 = PromptDialogSupportFragment.newInstance(7);
              p7.setMessage(C0257R.string.settings_deactivate_account_dialog);
              p7.setTitle(C0257R.string.settings_deactivate_account_title);
              p7.setNegativeButton(C0257R.string.cancel);
              p7.setPositiveButton(C0257R.string.settings_deactivate_account_confirm);
              p7.setTargetFragment(this, 0);
                        try {
                  p7.show(getActivity().getSupportFragmentManager());
                  return;
                        } catch (Exception e6) {
                  CrashUtil.logException(e6);
                  return;
                        }
                    }
          if (id == C0257R.id.version) {
              this.mVersionTapCount++;
              if (this.mVersionTapCount >= 6) {
                  this.mVersionTapCount = 0;
                  startActivity(new Intent(getActivity(), (Class<?>) DebugHomeActivity.class));
                  return;
                        } else {
                  if (this.mVersionTapCount == 3) {
                                try {
                          PackageInfo pi = getActivity().getPackageManager().getPackageInfo("co.vine.android", 0);
                          Util.showShortCenteredToast(getActivity(), "Version Code: " + pi.versionCode);
                          return;
                                } catch (Exception e7) {
                          SLog.m459e("Failed to show version code.", (Throwable) e7);
                          return;
                                }
                            }
                  return;
                        }
                    }
          if (id == C0257R.id.locale) {
              LocaleDialog ldf = LocaleDialog.newInstance();
              ldf.show(getActivity().getSupportFragmentManager(), "locale");
              return;
                    }
          if (id == C0257R.id.edition_container || id == C0257R.id.edition_label) {
              this.mEditionsSpinner.performClick();
              return;
                    }
          if (id == C0257R.id.notification_settings || id == C0257R.id.alert_icon) {
              if (this.mErrorCode != 0) {
                  Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(this.mErrorCode, getActivity(), 6);
                  errorDialog.show();
                  return;
                        } else {
                  FlurryUtils.trackNotificationSettings();
                  Intent intent5 = new Intent(getActivity(), (Class<?>) NotificationSettingsActivity.class);
                  intent5.putExtra("color", this.mBackgroundColor);
                  startActivity(intent5);
                  return;
                        }
                    }
          if (id == C0257R.id.email_verified) {
              if (!this.mIsEmailVerified) {
                  PromptDialogSupportFragment p8 = PromptDialogSupportFragment.newInstance(8);
                  p8.setTargetFragment(this, 0);
                            p8.setPositiveButton(C0257R.string.confirm_email_yes).setMessage(getString(C0257R.string.confirm_email_prompt, this.mEmail)).setNeutralButton(android.R.string.cancel);
                            try {
                      p8.show(getActivity().getSupportFragmentManager());
                      return;
                            } catch (Exception e8) {
                      CrashUtil.logException(e8);
                      return;
                            }
                        }
              return;
                    }
          if (id == C0257R.id.phone_verified && !this.mIsPhoneVerified) {
              getActivity().startActivityForResult(ConfirmationFlowActivity.getIntent(getActivity(), this.mPhone, false), 7);
                    }
                }

      private boolean validate() {
          FragmentActivity activity = getActivity();
          if (this.mName.isEmpty()) {
              if (activity == null) {
                            return false;
                        }
              Util.showCenteredToast(activity, C0257R.string.name_length_toast);
                        return false;
                    }
          if (this.mLocation.length() > 32) {
              if (activity == null) {
                            return false;
                        }
              Util.showCenteredToast(activity, C0257R.string.API_ERROR_LOCATION_INVALID_LENGTH);
                        return false;
                    }
          if (this.mDescription.length() > 140) {
              if (activity == null) {
                            return false;
                        }
              Util.showCenteredToast(activity, C0257R.string.API_ERROR_DESCRIPTION_INVALID_LENGTH);
                        return false;
                    }
          if (activity != null) {
              ProgressDialog progressDialog = new ProgressDialog(activity, C0257R.style.ProgressDialogTheme);
              progressDialog.setMessage(getString(C0257R.string.settings_updating));
              progressDialog.show();
              this.mProgress = progressDialog;
                    }
          return true;
                }

      private void updateVerificationIcons() {
          Resources res = null;
                    try {
              res = getResources();
                    } catch (Exception e) {
              CrashUtil.logException(e);
                    }
          if (this.mEditTextEmail != null && TextUtils.isEmpty(this.mEditTextEmail.getText().toString())) {
              this.mEmailVerified.setVisibility(8);
                    } else if (res != null) {
              this.mEmailVerified.setVisibility(0);
              if (this.mIsEmailVerified) {
                  this.mEmailVerified.setImageDrawable(res.getDrawable(C0257R.drawable.ic_settings_check));
                        } else {
                  this.mEmailVerified.setImageDrawable(res.getDrawable(C0257R.drawable.ic_settings_alert));
                        }
                    }
          if (this.mEditTextPhone != null && TextUtils.isEmpty(this.mEditTextPhone.getText().toString())) {
              this.mPhoneVerified.setVisibility(8);
              return;
                    }
          this.mPhoneVerified.setVisibility(0);
          if (res != null) {
              if (this.mIsPhoneVerified) {
                  this.mPhoneVerified.setImageDrawable(res.getDrawable(C0257R.drawable.ic_settings_check));
                        } else {
                  this.mPhoneVerified.setImageDrawable(res.getDrawable(C0257R.drawable.ic_settings_alert));
                        }
                    }
                }

      private void updateProfile(Uri avatarUri) {
          if (validate()) {
              if (!this.mOriginalName.equals(this.mName)) {
                            FlurryUtils.trackChangedName();
                        }
              if (!this.mOriginalDescription.equals(this.mDescription)) {
                            FlurryUtils.trackChangedDescription();
                        }
              if (!this.mOriginalLocation.equals(this.mLocation)) {
                            FlurryUtils.trackChangedLocation();
                        }
              if (!this.mOriginalEmail.equals(this.mEmail)) {
                            FlurryUtils.trackChangedEmail();
                        }
              if (!this.mOriginalEdition.equals(this.mEdition)) {
                            FlurryUtils.trackChangedEdition();
                        }
              this.mAppController.updateProfile(this.mAppController.getActiveSession(), this.mName, this.mDescription, this.mLocation, this.mEmail, this.mPhone, this.mPhotoChanged ? avatarUri : null, this.mBackgroundColor);
              this.mAppController.updateEdition(this.mAppController.getActiveSession(), this.mEdition);
                    }
                }

      class EditionsSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
                    private Cursor mCursor;

          EditionsSpinnerAdapter() {
                    }

                    @Override 
          public int getCount() {
              if (this.mCursor != null) {
                  return this.mCursor.getCount();
                        }
                        return 0;
                    }

          public void changeCursor(Cursor c) {
              this.mCursor = c;
              notifyDataSetChanged();
                    }

                    @Override 
          public Object getItem(int i) {
              return null;
                    }

                    @Override 
          public long getItemId(int i) {
              return i;
                    }

                    @Override 
          public View getView(int i, View view, ViewGroup viewGroup) {
                        View res;
              if (this.mCursor == null || i >= this.mCursor.getCount()) {
                            return view;
                        }
              if (view != null) {
                  res = view;
                        } else {
                  res = LayoutInflater.from(SettingsFragment.this.getActivity()).inflate(C0257R.layout.settings_spinner_row_item, (ViewGroup) null);
                        }
              TextView t = (TextView) res.findViewById(2131427387);
              t.setText(getEditionName(i));
              t.setSingleLine(true);
              t.setEllipsize(TextUtils.TruncateAt.END);
              return res;
                    }

                    @Override 
          public View getDropDownView(int i, View view, ViewGroup viewGroup) {
                        View res;
              if (this.mCursor == null || i >= this.mCursor.getCount()) {
                            return view;
                        }
              if (view != null) {
                  res = view;
                        } else {
                  res = LayoutInflater.from(SettingsFragment.this.getActivity()).inflate(C0257R.layout.settings_spinner_row_item, (ViewGroup) null);
                        }
              TextView t = (TextView) res.findViewById(2131427387);
              t.setText(getEditionName(i));
              t.setSingleLine(false);
              res.invalidate();
              return res;
                    }

          public void setSelectionToUserEdition() {
              if (this.mCursor != null) {
                  this.mCursor.moveToPosition(-1);
                  while (this.mCursor.moveToNext()) {
                      String editionCode = this.mCursor.getString(1);
                      if (editionCode.equals(SettingsFragment.access$1200(SettingsFragment.this))) {
                          SettingsFragment.access$2900(SettingsFragment.this).setSelection(this.mCursor.getPosition());
                          return;
                                }
                            }
                        }
                    }

          public String getEditionName(int index) {
              if (this.mCursor == null || index >= this.mCursor.getCount()) {
                            return "";
                        }
              this.mCursor.moveToPosition(index);
              return this.mCursor.getString(2);
                    }

          public String getEditionCode(int index) {
              if (this.mCursor == null || index >= this.mCursor.getCount()) {
                            return "";
                        }
              this.mCursor.moveToPosition(index);
              return this.mCursor.getString(1);
                    }

          public Object getCursor() {
              return this.mCursor;
                    }
                }
            }
