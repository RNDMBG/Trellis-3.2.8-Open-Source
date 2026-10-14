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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 88 */    public class SettingsFragment extends BaseControllerFragment implements SetThumbnailTask.SetThumbnailListener, PromptDialogSupportFragment.OnDialogDoneListener, View.OnClickListener, ImagePicker.Listener, CompoundButton.OnCheckedChangeListener, LoaderManager.LoaderCallbacks<Cursor>, TypingEditText.TypingListener, ColorPicker.ColorClickListener {
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

/* 89 */        static /* synthetic */ TextView access$000(SettingsFragment x0) {
/* 90 */            return x0.mClearCacheValue;
                }

/* 89 */        static /* synthetic */ String access$100(SettingsFragment x0) {
/* 90 */            return x0.mName;
                }

/* 89 */        static /* synthetic */ boolean access$1002(SettingsFragment x0, boolean x1) {
/* 90 */            x0.mIsPhoneVerified = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ String access$102(SettingsFragment x0, String x1) {
/* 90 */            x0.mName = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ boolean access$1100(SettingsFragment x0) {
/* 90 */            return x0.mOriginalPhoneVerified;
                }

/* 89 */        static /* synthetic */ String access$1200(SettingsFragment x0) {
/* 90 */            return x0.mEdition;
                }

/* 89 */        static /* synthetic */ String access$1202(SettingsFragment x0, String x1) {
/* 90 */            x0.mEdition = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ EditionsSpinnerAdapter access$1300(SettingsFragment x0) {
/* 90 */            return x0.mSpinnerAdapter;
                }

/* 89 */        static /* synthetic */ int access$1400(SettingsFragment x0) {
/* 90 */            return x0.mBackgroundColor;
                }

/* 89 */        static /* synthetic */ View access$1500(SettingsFragment x0) {
/* 90 */            return x0.mProfileBackground;
                }

/* 89 */        static /* synthetic */ ImageKey access$1600(SettingsFragment x0) {
/* 90 */            return x0.mAvatarKey;
                }

/* 89 */        static /* synthetic */ ImageView access$1700(SettingsFragment x0) {
/* 90 */            return x0.mAvatarImageView;
                }

/* 89 */        static /* synthetic */ ProgressDialog access$1800(SettingsFragment x0) {
/* 90 */            return x0.mProgress;
                }

/* 89 */        static /* synthetic */ TextView access$1900(SettingsFragment x0) {
/* 90 */            return x0.mFacebookValue;
                }

/* 89 */        static /* synthetic */ String access$200(SettingsFragment x0) {
/* 90 */            return x0.mDescription;
                }

/* 89 */        static /* synthetic */ void access$2000(SettingsFragment x0) {
/* 90 */            x0.invalidateFacebookSessionUI();
                }

/* 89 */        static /* synthetic */ String access$202(SettingsFragment x0, String x1) {
/* 90 */            x0.mDescription = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ SharedPreferences.Editor access$2100(SettingsFragment x0) {
/* 90 */            return x0.mEditor;
                }

/* 89 */        static /* synthetic */ int access$2200(SettingsFragment x0) {
/* 90 */            return x0.mColorIndex;
                }

/* 89 */        static /* synthetic */ int access$2202(SettingsFragment x0, int x1) {
/* 90 */            x0.mColorIndex = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ String access$2302(SettingsFragment x0, String x1) {
/* 90 */            x0.mOriginalEdition = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ AppController access$2400(SettingsFragment x0) {
/* 90 */            return x0.mAppController;
                }

/* 89 */        static /* synthetic */ boolean access$2502(SettingsFragment x0, boolean x1) {
/* 90 */            x0.mTwitterLogin = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ SharedPreferences access$2600(SettingsFragment x0) {
/* 90 */            return x0.mPrefs;
                }

/* 89 */        static /* synthetic */ void access$2700(SettingsFragment x0) {
/* 90 */            x0.updateVerificationIcons();
                }

/* 89 */        static /* synthetic */ boolean access$2802(SettingsFragment x0, boolean x1) {
/* 90 */            x0.mEditionsFetched = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ Spinner access$2900(SettingsFragment x0) {
/* 90 */            return x0.mEditionsSpinner;
                }

/* 89 */        static /* synthetic */ String access$300(SettingsFragment x0) {
/* 90 */            return x0.mLocation;
                }

/* 89 */        static /* synthetic */ String access$302(SettingsFragment x0, String x1) {
/* 90 */            x0.mLocation = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ String access$400(SettingsFragment x0) {
/* 90 */            return x0.mEmail;
                }

/* 89 */        static /* synthetic */ String access$402(SettingsFragment x0, String x1) {
/* 90 */            x0.mEmail = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ boolean access$502(SettingsFragment x0, boolean x1) {
/* 90 */            x0.mIsEmailVerified = x1;
                    return x1;
                }

/* 89 */        static /* synthetic */ String access$600(SettingsFragment x0) {
/* 90 */            return x0.mOriginalEmail;
                }

/* 89 */        static /* synthetic */ String access$700(SettingsFragment x0) {
/* 90 */            return x0.mOriginalPhone;
                }

/* 89 */        static /* synthetic */ boolean access$800(SettingsFragment x0) {
/* 90 */            return x0.mOriginalEmailVerified;
                }

/* 89 */        static /* synthetic */ String access$900(SettingsFragment x0) {
/* 90 */            return x0.mPhone;
                }

/* 89 */        static /* synthetic */ String access$902(SettingsFragment x0, String x1) {
/* 90 */            x0.mPhone = x1;
                    return x1;
                }


/* 194 */       private void resetPrefs() {
/* 195 */           SharedPreferences.Editor editor = this.mEditor;
/* 196 */           editor.putString(Settings.PREF_NAME, this.mOriginalName);
/* 197 */           editor.putString(Settings.PREF_DESCRIPTION, this.mOriginalDescription);
/* 198 */           editor.putString(Settings.PREF_LOCATION, this.mOriginalLocation);
/* 199 */           editor.putString(Settings.PREF_EMAIL, this.mOriginalEmail);
/* 200 */           editor.putString(Settings.PREF_PHONE, this.mOriginalPhone);
/* 201 */           editor.putString(Settings.PREF_AVATAR_URL, this.mOriginalAvatarUrl);
/* 202 */           editor.putString(Settings.PREF_EDITION, this.mOriginalEdition);
/* 203 */           editor.putInt(Settings.PREF_COLOR_INDEX, this.mOriginalColorIndex);
/* 204 */           editor.putInt("profile_background", this.mOriginalBackgroundColor);
/* 205 */           editor.commit();
                }

/* 208 */       private boolean isDirty() {
                    return (this.mOriginalName.equals(this.mName) && this.mOriginalEmail.equals(this.mEmail) && this.mOriginalDescription.equals(this.mDescription) && this.mOriginalLocation.equals(this.mLocation) && this.mOriginalPhone.equals(this.mPhone) && this.mOriginalEdition.equals(this.mEdition) && this.mOriginalColorIndex == this.mColorIndex && !this.mPhotoChanged) ? false : true;
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 221 */       public void onPause() {
/* 222 */           super.onPause();
/* 223 */           if (this.mProgress != null) {
/* 224 */               this.mProgress.dismiss();
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 229 */       public void onSaveInstanceState(Bundle outState) {
/* 230 */           super.onSaveInstanceState(outState);
/* 231 */           outState.putString(STATE_NAME, this.mName);
/* 232 */           outState.putString(STATE_DESCRIPTION, this.mDescription);
/* 233 */           outState.putString(STATE_LOCATION, this.mLocation);
/* 234 */           outState.putString(STATE_PHONE, this.mPhone);
/* 235 */           outState.putString(STATE_EMAIL, this.mEmail);
/* 236 */           outState.putString(STATE_EDITION, this.mEdition);
/* 237 */           outState.putBoolean(STATE_EDITIONS_FETCHED, this.mEditionsFetched);
/* 238 */           outState.putParcelable(STATE_AVATAR_URI, this.mAvatarUri);
/* 239 */           outState.putInt(STATE_BACKGROUND_COLOR, this.mBackgroundColor);
/* 240 */           outState.putInt(STATE_COLOR_INDEX, this.mColorIndex);
                }

/* 243 */       private void setOriginalPreferenceValues() {
/* 244 */           SharedPreferences prefs = this.mPrefs;
/* 245 */           this.mOriginalName = prefs.getString(Settings.PREF_NAME, "");
/* 246 */           this.mName = this.mOriginalName;
/* 247 */           this.mOriginalDescription = prefs.getString(Settings.PREF_DESCRIPTION, "");
/* 248 */           this.mDescription = this.mOriginalDescription;
/* 249 */           this.mOriginalLocation = prefs.getString(Settings.PREF_LOCATION, "");
/* 250 */           this.mLocation = this.mOriginalLocation;
/* 251 */           this.mOriginalEmail = prefs.getString(Settings.PREF_EMAIL, "");
/* 252 */           this.mEmail = this.mOriginalEmail;
/* 253 */           this.mOriginalPhone = prefs.getString(Settings.PREF_PHONE, "");
/* 254 */           this.mPhone = this.mOriginalPhone;
/* 255 */           this.mOriginalEdition = prefs.getString(Settings.PREF_EDITION, "");
/* 256 */           this.mEdition = this.mOriginalEdition;
/* 257 */           this.mTwitterConnected = prefs.getBoolean(Settings.PREF_TWITTER_CONNECTED, false);
/* 260 */           this.mAvatarUri = Uri.parse(prefs.getString(Settings.PREF_AVATAR_URL, ""));
/* 261 */           this.mOriginalAvatarUrl = this.mAvatarUri.toString();
/* 262 */           this.mBackgroundColor = prefs.getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
/* 264 */           this.mOriginalBackgroundColor = this.mBackgroundColor;
                    for (int i = 0; i < Settings.PROFILE_BACKGROUND_COLORS.length; i++) {
/* 266 */               if (Settings.PROFILE_BACKGROUND_COLORS[i] == this.mBackgroundColor) {
/* 267 */                   this.mOriginalColorIndex = i;
                            break;
                        }
                    }
/* 271 */           this.mColorIndex = this.mOriginalColorIndex;
/* 272 */           this.mOriginalEmailVerified = prefs.getBoolean(Settings.PREF_EMAIL_VERIFIED, false);
/* 273 */           this.mIsEmailVerified = this.mOriginalEmailVerified;
/* 274 */           this.mOriginalPhoneVerified = prefs.getBoolean(Settings.PREF_PHONE_VERIFIED, false);
/* 275 */           this.mIsPhoneVerified = this.mOriginalPhoneVerified;
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 279 */       public void onCreate(Bundle savedInstanceState) {
/* 280 */           super.onCreate(savedInstanceState);
/* 281 */           setHasOptionsMenu(true);
/* 282 */           this.mPrefs = Util.getDefaultSharedPrefs(getActivity());
/* 283 */           this.mAppSessionListener = new SettingsListener();
/* 284 */           this.mAppController = AppController.getInstance(getActivity());
/* 285 */           this.mImagePicker = new ImagePicker(getActivity(), this, this.mAppController.getActiveId());
/* 286 */           this.mEditor = this.mPrefs.edit();
                }

                @Override // co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 290 */       public void onResume() {
/* 291 */           super.onResume();
/* 292 */           this.mAppController.fetchUsersMe(this.mAppController.getActiveId(), UrlCachePolicy.FORCE_REFRESH);
/* 293 */           if (this.mSpinnerAdapter.getCursor() == null) {
/* 294 */               getLoaderManager().initLoader(0, null, this);
                    } else if (this.mSpinnerAdapter.isEmpty() && !this.mEditionsFetched) {
/* 296 */               this.mEditionsFetched = true;
/* 297 */               this.mAppController.getEditions();
                    }
/* 299 */           if (!ClientFlagsHelper.prefetchEnabled(getActivity())) {
/* 300 */               View view = getView();
/* 301 */               view.findViewById(C0257R.id.sync).setVisibility(8);
/* 302 */               view.findViewById(C0257R.id.sync_divider).setVisibility(8);
                    }
                }

/* 304 */       public class GetCacheSizeAsyncTask extends AsyncTask<Void, Void, String> {
/* 305 */           public GetCacheSizeAsyncTask() {
                    }



                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 309 */           protected void onPostExecute(String size) {
/* 310 */               if (size != null) {
/* 311 */                   SettingsFragment.access$000(SettingsFragment.this).setText(size);
                        }
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 317 */           protected String doInBackground(Void... params) {
                        try {
/* 318 */                   FragmentActivity activity = SettingsFragment.this.getActivity();
/* 319 */                   if (activity != null) {
/* 320 */                       return Util.formatFileSize(SettingsFragment.this.getResources(), Util.getCacheSize(activity));
                            }
                        } catch (Exception e) {
/* 324 */                   CrashUtil.logException(e);
                        }
/* 326 */               return null;
                    }
                }

                @Override // android.support.v4.app.Fragment
                @SuppressLint({"NewApi"})
/* 333 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 334 */           View v = inflater.inflate(C0257R.layout.settings, container, false);
/* 336 */           Resources res = getResources();
/* 338 */           setOriginalPreferenceValues();
/* 340 */           if (savedInstanceState != null) {
/* 341 */               this.mName = savedInstanceState.getString(STATE_NAME);
/* 342 */               this.mDescription = savedInstanceState.getString(STATE_DESCRIPTION);
/* 343 */               this.mLocation = savedInstanceState.getString(STATE_LOCATION);
/* 344 */               this.mPhone = savedInstanceState.getString(STATE_PHONE);
/* 345 */               this.mEmail = savedInstanceState.getString(STATE_EMAIL);
/* 346 */               this.mEditionsFetched = savedInstanceState.getBoolean(STATE_EDITIONS_FETCHED);
/* 347 */               this.mEdition = savedInstanceState.getString(STATE_EDITION);
/* 348 */               this.mAvatarUri = (Uri) savedInstanceState.getParcelable(STATE_AVATAR_URI);
/* 349 */               this.mBackgroundColor = savedInstanceState.getInt(STATE_BACKGROUND_COLOR);
/* 350 */               this.mColorIndex = savedInstanceState.getInt(STATE_COLOR_INDEX);
                    }
/* 353 */           this.mScrollView = (ScrollView) v.findViewById(C0257R.id.scrollview);
/* 355 */           this.mEmailVerified = (ImageView) v.findViewById(C0257R.id.email_verified);
/* 356 */           this.mEmailVerified.setOnClickListener(this);
/* 358 */           this.mPhoneVerified = (ImageView) v.findViewById(C0257R.id.phone_verified);
/* 359 */           this.mPhoneVerified.setOnClickListener(this);
/* 361 */           ColorPicker colorPicker = (ColorPicker) v.findViewById(C0257R.id.color_picker);
/* 362 */           colorPicker.setOnColorClickListener(this);
/* 363 */           colorPicker.setColorIndex(this.mColorIndex);
/* 365 */           ViewGroup profileBackground = (ViewGroup) v.findViewById(C0257R.id.user_image_container);
/* 366 */           profileBackground.setBackgroundColor(this.mBackgroundColor | (-16777216));
/* 368 */           this.mProfileBackground = profileBackground;
/* 370 */           setActionBarColor(this.mBackgroundColor);
/* 372 */           TextView privacyControls = (TextView) v.findViewById(C0257R.id.privacy_controls);
/* 373 */           privacyControls.setOnClickListener(this);
/* 375 */           TextView syncControls = (TextView) v.findViewById(C0257R.id.sync);
/* 376 */           syncControls.setOnClickListener(this);
           TextView updatesRow = (TextView) v.findViewById(C0257R.id.updates);
           updatesRow.setOnClickListener(this);
/* 378 */           this.mAvatarImageView = (ImageView) v.findViewById(C0257R.id.user_image);
/* 379 */           this.mAvatarActionView = (ImageView) v.findViewById(C0257R.id.user_image_action);
/* 380 */           this.mAvatarActionView.setVisibility(0);
/* 381 */           String avatarUrl = this.mAvatarUri == null ? "" : this.mAvatarUri.toString();
/* 382 */           this.mAvatarImageView.setOnClickListener(this);
/* 383 */           if (this.mThumbnail != null) {
/* 384 */               setImage(this.mThumbnail, false);
                    } else if (TextUtils.isEmpty(avatarUrl) || Util.isDefaultAvatarUrl(avatarUrl)) {
/* 387 */               setImage(null, false);
                    } else {
/* 389 */               int dimen = res.getDimensionPixelOffset(C0257R.dimen.user_image_size_large);
/* 390 */               this.mAvatarKey = new ImageKey(avatarUrl, dimen, dimen, true);
/* 391 */               Bitmap bmp = this.mAppController.getPhotoBitmap(this.mAvatarKey);
/* 392 */               setImage(bmp, false);
                    }
/* 398 */           EditText editTextName = (EditText) v.findViewById(C0257R.id.name);
/* 399 */           editTextName.setText(this.mName);
/* 400 */           editTextName.addTextChangedListener(new C02751());
/* 408 */           EditText editTextDescription = (EditText) v.findViewById(C0257R.id.description);
/* 409 */           editTextDescription.setText(this.mDescription);
/* 410 */           editTextDescription.setHint(C0257R.string.settings_description);
/* 411 */           editTextDescription.addTextChangedListener(new C02772());
/* 419 */           EditText editTextLocation = (EditText) v.findViewById(C0257R.id.location);
/* 420 */           editTextLocation.setText(this.mLocation);
/* 421 */           editTextLocation.setHint(C0257R.string.settings_location);
/* 422 */           editTextLocation.addTextChangedListener(new C02783());
/* 431 */           TypingEditText editTextEmail = (TypingEditText) v.findViewById(C0257R.id.settings_email);
/* 432 */           editTextEmail.setText(this.mEmail);
/* 433 */           editTextEmail.setInputType(32);
/* 434 */           editTextEmail.setTypingListener(this);
/* 435 */           this.mEditTextEmail = editTextEmail;
/* 437 */           editTextEmail.addTextChangedListener(new C02794());
/* 446 */           TypingEditText editTextPhone = (TypingEditText) v.findViewById(C0257R.id.settings_phone);
/* 447 */           editTextPhone.setText(this.mPhone);
/* 448 */           editTextPhone.setInputType(3);
/* 449 */           editTextPhone.addTextChangedListener(new C02805());
/* 457 */           editTextPhone.setTypingListener(this);
/* 458 */           this.mEditTextPhone = editTextPhone;
/* 460 */           this.mEditionsSpinner = (Spinner) v.findViewById(C0257R.id.edition);
/* 461 */           this.mEditionsSpinner.setFocusable(false);
/* 462 */           this.mEditionsSpinner.setOnItemSelectedListener(new C02816());
/* 474 */           v.findViewById(C0257R.id.edition_container).setOnClickListener(this);
/* 475 */           v.findViewById(C0257R.id.edition_label).setOnClickListener(this);
/* 477 */           this.mSpinnerAdapter = new EditionsSpinnerAdapter();
/* 479 */           this.mEditionsSpinner.setAdapter((SpinnerAdapter) this.mSpinnerAdapter);
/* 481 */           TextView password = (TextView) v.findViewById(C0257R.id.settings_password);
/* 482 */           password.setOnClickListener(this);
/* 484 */           TextView content = (TextView) v.findViewById(C0257R.id.content_controls);
/* 485 */           content.setOnClickListener(this);
/* 487 */           this.mNotifications = (ViewGroup) v.findViewById(C0257R.id.notification_settings);
/* 488 */           if (BuildUtil.isGoogle()) {
/* 489 */               this.mNotifications.setVisibility(0);
/* 490 */               this.mAlertIcon = (ImageView) this.mNotifications.findViewById(C0257R.id.alert_icon);
/* 491 */               this.mErrorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
/* 492 */               if (this.mErrorCode != 0) {
/* 493 */                   this.mAlertIcon.setVisibility(0);
/* 494 */                   this.mAlertIcon.setOnClickListener(this);
                        } else {
/* 496 */                   this.mAlertIcon.setVisibility(8);
                        }
/* 498 */               this.mNotifications.setOnClickListener(this);
                    } else {
/* 501 */               this.mNotifications.setVisibility(8);
                    }
/* 510 */           TextView findFriends = (TextView) v.findViewById(C0257R.id.find_friends);
/* 511 */           findFriends.setOnClickListener(this);
/* 513 */           TextView inviteViaText = (TextView) v.findViewById(C0257R.id.invite_via_text);
/* 514 */           inviteViaText.setOnClickListener(this);
/* 516 */           TextView inviteViaEmail = (TextView) v.findViewById(C0257R.id.invite_via_email);
/* 517 */           inviteViaEmail.setOnClickListener(this);
/* 519 */           RelativeLayout twitterConnect = (RelativeLayout) v.findViewById(C0257R.id.twitter_connect);
/* 520 */           twitterConnect.setBackgroundResource(C0257R.drawable.bg_selectable_item);
/* 521 */           TextView twitterConnectedTextView = (TextView) twitterConnect.findViewById(C0257R.id.twitter_connect_value);
/* 524 */           AccountManager am = AccountManager.get(getActivity());
/* 525 */           Account acc = VineAccountHelper.getAccount(getActivity(), this.mAppController.getActiveSession().getLoginEmail());
/* 527 */           if (acc != null && VineAccountHelper.getLoginType(am, acc).intValue() == 2) {
/* 529 */               twitterConnectedTextView.setText('@' + VineAccountHelper.getTwitterUsername(am, acc));
/* 530 */               this.mTwitterLogin = true;
                    } else if (this.mTwitterConnected) {
/* 533 */               twitterConnectedTextView.setText("");
/* 534 */               this.mTwitterLogin = true;
                    } else {
/* 536 */               twitterConnectedTextView.setText(C0257R.string.settings_connect_to_twitter_summary);
/* 537 */               this.mTwitterLogin = false;
                    }
/* 539 */           twitterConnect.setOnClickListener(this);
/* 541 */           RelativeLayout facebookConnect = (RelativeLayout) v.findViewById(C0257R.id.facebook_connect);
/* 542 */           facebookConnect.setOnClickListener(this);
/* 543 */           TextView facebookLabel = (TextView) v.findViewById(C0257R.id.facebook_label);
/* 544 */           facebookLabel.setText(C0257R.string.facebook);
/* 545 */           this.mFacebookValue = (TextView) v.findViewById(C0257R.id.facebook_value);
/* 546 */           invalidateFacebookSessionUI();
/* 547 */           facebookConnect.setOnClickListener(this);
/* 549 */           RelativeLayout clearCache = (RelativeLayout) v.findViewById(C0257R.id.clear_cache);
/* 550 */           clearCache.setOnClickListener(this);
/* 552 */           TextView clearCacheLabel = (TextView) v.findViewById(C0257R.id.clear_cache_label);
/* 553 */           clearCacheLabel.setText(getString(C0257R.string.settings_cache_size));
/* 555 */           this.mClearCacheValue = (TextView) v.findViewById(C0257R.id.clear_cache_value);
/* 556 */           new GetCacheSizeAsyncTask().execute(new Void[0]);
/* 558 */           TextView termsOfService = (TextView) v.findViewById(C0257R.id.terms_of_service);
/* 559 */           termsOfService.setOnClickListener(this);
/* 561 */           TextView privacyPolicy = (TextView) v.findViewById(C0257R.id.privacy_policy);
/* 562 */           privacyPolicy.setOnClickListener(this);
/* 564 */           TextView attribution = (TextView) v.findViewById(C0257R.id.attribution);
/* 565 */           attribution.setOnClickListener(this);
/* 567 */           TextView deactivateAccount = (TextView) v.findViewById(C0257R.id.deactivate_account);
/* 568 */           deactivateAccount.setOnClickListener(this);
/* 570 */           Button logout = (Button) v.findViewById(C0257R.id.logout);
/* 571 */           logout.setOnClickListener(this);
/* 573 */           LinearLayout version = (LinearLayout) v.findViewById(C0257R.id.version);
/* 574 */           TextView tv = (TextView) v.findViewById(C0257R.id.version_number);
                    try {
/* 577 */               PackageInfo pi = getActivity().getPackageManager().getPackageInfo("co.vine.android", 0);
/* 578 */               tv.setText(getString(C0257R.string.settings_about_summary, pi.versionName));
                    } catch (PackageManager.NameNotFoundException e) {
/* 581 */               Log.d(TAG, "Error retrieving package info:", e);
                    }
/* 583 */           version.setOnClickListener(this);
/* 585 */           if (BuildUtil.isI18nOn()) {
/* 586 */               TextView locale = (TextView) v.findViewById(C0257R.id.locale);
/* 587 */               locale.setVisibility(0);
/* 588 */               locale.setOnClickListener(this);
                    }
/* 591 */           TextView favoritePeople = (TextView) v.findViewById(C0257R.id.favorite_people);
/* 592 */           favoritePeople.setOnClickListener(new ViewOnClickListenerC02827());
/* 599 */           return v;
                }

                /* JADX INFO: renamed from: co.vine.android.SettingsFragment$1 */
/* 398 */       class C02751 extends SimpleTextWatcher {
/* 399 */           C02751() {
                    }

                    @Override // co.vine.android.views.SimpleTextWatcher, android.text.TextWatcher
/* 403 */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 404 */               SettingsFragment.access$102(SettingsFragment.this, s.toString());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SettingsFragment$2 */
/* 409 */       class C02772 extends SimpleTextWatcher {
/* 410 */           C02772() {
                    }

                    @Override // co.vine.android.views.SimpleTextWatcher, android.text.TextWatcher
/* 414 */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 415 */               SettingsFragment.access$202(SettingsFragment.this, s.toString());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SettingsFragment$3 */
/* 420 */       class C02783 extends SimpleTextWatcher {
/* 421 */           C02783() {
                    }

                    @Override // co.vine.android.views.SimpleTextWatcher, android.text.TextWatcher
/* 425 */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 426 */               SettingsFragment.access$302(SettingsFragment.this, s.toString());
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SettingsFragment$4 */
/* 435 */       class C02794 extends SimpleTextWatcher {
/* 436 */           C02794() {
                    }

                    @Override // co.vine.android.views.SimpleTextWatcher, android.text.TextWatcher
/* 440 */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 441 */               SettingsFragment.access$402(SettingsFragment.this, s.toString());
/* 442 */               SettingsFragment.access$502(SettingsFragment.this, (SettingsFragment.access$600(SettingsFragment.this) == null || SettingsFragment.access$700(SettingsFragment.this).equals(SettingsFragment.access$400(SettingsFragment.this))) && SettingsFragment.access$800(SettingsFragment.this));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SettingsFragment$5 */
/* 447 */       class C02805 extends SimpleTextWatcher {
/* 448 */           C02805() {
                    }

                    @Override // co.vine.android.views.SimpleTextWatcher, android.text.TextWatcher
/* 452 */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 453 */               SettingsFragment.access$902(SettingsFragment.this, s.toString());
/* 454 */               SettingsFragment.access$1002(SettingsFragment.this, (SettingsFragment.access$700(SettingsFragment.this) == null || SettingsFragment.access$700(SettingsFragment.this).equals(SettingsFragment.access$900(SettingsFragment.this))) && SettingsFragment.access$1100(SettingsFragment.this));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SettingsFragment$6 */
/* 460 */       class C02816 implements AdapterView.OnItemSelectedListener {
/* 461 */           C02816() {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
/* 465 */           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
/* 466 */               SettingsFragment.access$1202(SettingsFragment.this, SettingsFragment.access$1300(SettingsFragment.this).getEditionCode(i));
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
/* 470 */           public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SettingsFragment$7 */
/* 590 */       class ViewOnClickListenerC02827 implements View.OnClickListener {
/* 591 */           ViewOnClickListenerC02827() {
                    }

                    @Override // android.view.View.OnClickListener
/* 594 */           public void onClick(View view) {
/* 595 */               FavoritePeopleActivity.start(SettingsFragment.this.getActivity(), SettingsFragment.access$1400(SettingsFragment.this));
                    }
                }

/* 602 */       private void invalidateFacebookSessionUI() {
/* 603 */           this.mFbSession = this.mAppController.getValidFacebookSession(getActivity(), true);
/* 604 */           if (this.mFbSession != null) {
/* 605 */               this.mFacebookValue.setText(C0257R.string.settings_connected);
                    } else {
/* 607 */               this.mFacebookValue.setText(C0257R.string.settings_not_connected);
                    }
                }

                @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 613 */       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                }

/* 619 */       public void onBackPressed() {
/* 620 */           FragmentActivity activity = getActivity();
/* 621 */           if (isDirty() && activity != null) {
/* 622 */               PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(3);
/* 624 */               p.setMessage(C0257R.string.settings_unsaved_changes);
/* 625 */               p.setNeutralButton(C0257R.string.settings_continue_editing);
/* 626 */               p.setPositiveButton(C0257R.string.settings_discard);
/* 627 */               p.setTargetFragment(this, 0);
/* 628 */               p.show(activity.getSupportFragmentManager());
/* 637 */               return;
                    }
/* 630 */           if (this.mProgress != null) {
/* 631 */               this.mProgress.dismiss();
                    }
/* 633 */           if (activity != null) {
/* 634 */               activity.finish();
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 640 */       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 641 */           inflater.inflate(C0257R.menu.save, menu);
/* 642 */           MenuItem menuItem = menu.findItem(C0257R.id.done);
/* 643 */           menuItem.setEnabled(true);
/* 644 */           menu.findItem(C0257R.id.done).setEnabled(true);
                }

                @Override // android.support.v4.app.LoaderManager.LoaderCallbacks
/* 648 */       public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
/* 649 */           return new CursorLoader(getActivity(), Vine.Editions.CONTENT_URI, VineDatabaseSQL.EditionsQuery.PROJECTION, null, null, null);
                }

/* 656 */       public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
/* 657 */           if (!this.mEditionsFetched) {
/* 658 */               this.mAppController.getEditions();
/* 659 */               this.mEditionsFetched = true;
                    }
/* 661 */           this.mSpinnerAdapter.changeCursor(cursor);
/* 662 */           this.mSpinnerAdapter.setSelectionToUserEdition();
                }

                @Override // android.support.v4.app.LoaderManager.LoaderCallbacks
/* 665 */       public void onLoaderReset(Loader<Cursor> cursorLoader) {
                }

                @Override // co.vine.android.widget.TypingEditText.TypingListener
/* 669 */       public void onTypingTimeout(View v) {
/* 670 */           int id = v.getId();
/* 671 */           if (id == C0257R.id.settings_email || id == C0257R.id.settings_phone) {
/* 672 */               updateVerificationIcons();
                    }
                }

                @Override // co.vine.android.widget.ColorPicker.ColorClickListener
                @TargetApi(11)
/* 679 */       public void onColorClick(int colorIndex) {
                    int origColor;
/* 680 */           if (this.mColorIndex >= 0) {
/* 681 */               origColor = Settings.PROFILE_BACKGROUND_COLORS[this.mColorIndex];
                    } else {
/* 683 */               origColor = Settings.DEFAULT_PROFILE_COLOR;
                    }
/* 685 */           this.mColorIndex = colorIndex;
/* 686 */           this.mBackgroundColor = Settings.PROFILE_BACKGROUND_COLORS[this.mColorIndex];
/* 688 */           ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(origColor | (-16777216)), Integer.valueOf(this.mBackgroundColor | (-16777216)));
/* 690 */           animator.addUpdateListener(new C02838(animator));
/* 697 */           animator.setDuration(100L);
/* 698 */           animator.start();
/* 700 */           Intent intent = new Intent(VineService.ACTION_PROFILE_COLOR_CHANGED);
/* 701 */           getActivity().sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                }

                /* JADX INFO: renamed from: co.vine.android.SettingsFragment$8 */
/* 688 */       class C02838 implements ValueAnimator.AnimatorUpdateListener {
                    final /* synthetic */ ValueAnimator val$animator;

/* 689 */           C02838(ValueAnimator valueAnimator) {
                        this.val$animator = valueAnimator;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
/* 692 */           public void onAnimationUpdate(ValueAnimator animation) {
/* 693 */               SettingsFragment.access$1500(SettingsFragment.this).setBackgroundColor(((Integer) this.val$animator.getAnimatedValue()).intValue());
/* 694 */               SettingsFragment.this.setActionBarColor(((Integer) this.val$animator.getAnimatedValue()).intValue());
                    }
                }

/* 702 */       class SettingsListener extends AppSessionListener {
/* 703 */           SettingsListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 707 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 708 */               UrlImage urlImage = images.get(SettingsFragment.access$1600(SettingsFragment.this));
/* 709 */               if (urlImage != null && urlImage.isValid()) {
/* 710 */                   SettingsFragment.access$1700(SettingsFragment.this).setImageDrawable(new RecyclableBitmapDrawable(SettingsFragment.access$1700(SettingsFragment.this).getResources(), urlImage.bitmap));
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 716 */           public void onConnectFacebookComplete(String reqId, int statusCode, String reasonPhrase) {
/* 717 */               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
/* 718 */                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
/* 720 */               if (statusCode != 200) {
/* 721 */                   Util.showCenteredToast(SettingsFragment.this.getActivity(), C0257R.string.error_facebook_send_token);
/* 723 */                   SettingsFragment.access$1900(SettingsFragment.this).setText(C0257R.string.settings_not_connected);
                        } else {
/* 725 */                   SettingsFragment.access$2000(SettingsFragment.this);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 731 */           public void onUpdateProfileComplete(String reqId, int statusCode, String reasonPhrase, String avatarUrl) {
/* 732 */               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
/* 733 */                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
/* 735 */               FragmentActivity activity = SettingsFragment.this.getActivity();
/* 736 */               if (statusCode == 200) {
/* 737 */                   SharedPreferences.Editor editor = SettingsFragment.access$2100(SettingsFragment.this);
/* 738 */                   if (!TextUtils.isEmpty(avatarUrl)) {
/* 739 */                       SettingsFragment.access$2100(SettingsFragment.this).putString(Settings.PREF_AVATAR_URL, avatarUrl);
                            }
/* 741 */                   editor.putString(Settings.PREF_NAME, SettingsFragment.access$100(SettingsFragment.this));
/* 742 */                   editor.putString(Settings.PREF_DESCRIPTION, SettingsFragment.access$200(SettingsFragment.this));
/* 743 */                   editor.putString(Settings.PREF_LOCATION, SettingsFragment.access$300(SettingsFragment.this));
/* 744 */                   editor.putString(Settings.PREF_EMAIL, SettingsFragment.access$400(SettingsFragment.this));
/* 745 */                   editor.putString(Settings.PREF_PHONE, SettingsFragment.access$900(SettingsFragment.this));
/* 747 */                   if (SettingsFragment.access$2200(SettingsFragment.this) < 0 || SettingsFragment.access$2200(SettingsFragment.this) >= Settings.PROFILE_BACKGROUND_COLORS.length) {
/* 748 */                       SettingsFragment.access$2202(SettingsFragment.this, 0);
                            }
/* 751 */                   editor.putInt("profile_background", Settings.PROFILE_BACKGROUND_COLORS[SettingsFragment.access$2200(SettingsFragment.this)]);
/* 753 */                   editor.putInt(Settings.PREF_COLOR_INDEX, SettingsFragment.access$2200(SettingsFragment.this));
/* 755 */                   editor.apply();
/* 756 */                   if (activity != null) {
/* 757 */                       activity.sendBroadcast(new Intent(VineService.ACTION_PROFILE_COLOR_CHANGED), CrossConstants.BROADCAST_PERMISSION);
/* 758 */                       activity.finish();
/* 767 */                       return;
                            }
/* 767 */                   return;
                        }
/* 761 */               if (TextUtils.isEmpty(reasonPhrase)) {
/* 762 */                   Util.showCenteredToast(activity, C0257R.string.update_profile_error);
                        } else {
/* 764 */                   Util.showCenteredToast(activity, reasonPhrase);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 770 */           public void onUpdateEditionComplete(String reqId, int statusCode, String reasonPhrase, String edition) {
/* 771 */               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
/* 772 */                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
/* 774 */               if (statusCode == 200) {
/* 775 */                   SettingsFragment.access$1202(SettingsFragment.this, edition);
/* 776 */                   SettingsFragment.access$2302(SettingsFragment.this, edition);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 802 */           public void onClearCacheComplete(String reqId, int statusCode, String reasonPhrase) {
/* 803 */               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
/* 804 */                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
                        try {
/* 807 */                   SettingsFragment.access$000(SettingsFragment.this).setText(Util.formatFileSize(SettingsFragment.this.getResources(), Util.getCacheSize(SettingsFragment.this.getActivity())));
                        } catch (VineLoggingException e) {
/* 810 */                   CrashUtil.logException(e);
                        }
/* 812 */               SettingsFragment.this.getActivity().setResult(1);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 818 */           public void onConnectTwitterComplete(String reqId, int statusCode, String reasonPhrase, String username, String token, String secret, long userId) {
/* 819 */               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
/* 820 */                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
/* 822 */               TextView valueTextView = (TextView) SettingsFragment.this.getView().findViewById(C0257R.id.twitter_connect_value);
/* 824 */               TextView label = (TextView) SettingsFragment.this.getView().findViewById(C0257R.id.twitter_connect_label);
/* 826 */               if (statusCode == 200 && userId > 0 && !TextUtils.isEmpty(token) && !TextUtils.isEmpty(secret)) {
/* 828 */                   label.setText(C0257R.string.settings_connected_to_twitter);
/* 829 */                   valueTextView.setText('@' + username);
/* 830 */                   VineAccountHelper.saveTwitterInfo(SettingsFragment.this.getActivity(), SettingsFragment.access$2400(SettingsFragment.this).getActiveSession().getLoginEmail(), username, token, secret, userId);
/* 833 */                   SettingsFragment.access$2502(SettingsFragment.this, true);
/* 834 */                   SettingsFragment.access$2600(SettingsFragment.this).edit().putBoolean(Settings.PREF_TWITTER_CONNECTED, true).apply();
/* 845 */                   return;
                        }
/* 836 */               label.setText(C0257R.string.settings_connect_to_twitter);
/* 837 */               valueTextView.setText(C0257R.string.settings_connect_to_twitter_summary);
/* 838 */               SettingsFragment.access$2502(SettingsFragment.this, false);
/* 839 */               if (TextUtils.isEmpty(reasonPhrase)) {
/* 840 */                   reasonPhrase = SettingsFragment.this.getResources().getString(C0257R.string.settings_error_connect_to_twitter);
                        }
/* 843 */               Util.showCenteredToast(SettingsFragment.this.getActivity(), reasonPhrase);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 848 */           public void onDisconnectTwitterComplete(String reqId, int statusCode, String reasonPhrase) {
/* 849 */               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
/* 850 */                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
/* 852 */               TextView valueTextView = (TextView) SettingsFragment.this.getView().findViewById(C0257R.id.twitter_connect_value);
/* 854 */               TextView label = (TextView) SettingsFragment.this.getView().findViewById(C0257R.id.twitter_connect_label);
/* 856 */               if (statusCode == 200) {
/* 857 */                   label.setText(C0257R.string.settings_connect_to_twitter);
/* 858 */                   valueTextView.setText(C0257R.string.settings_connect_to_twitter_summary);
/* 859 */                   VineAccountHelper.removeTwitterInfo(SettingsFragment.this.getActivity(), SettingsFragment.access$2400(SettingsFragment.this).getActiveSession().getLoginEmail());
/* 861 */                   SettingsFragment.access$2502(SettingsFragment.this, false);
/* 862 */                   SettingsFragment.access$2100(SettingsFragment.this).putBoolean(Settings.PREF_TWITTER_CONNECTED, false).apply();
/* 870 */                   return;
                        }
/* 864 */               if (TextUtils.isEmpty(reasonPhrase)) {
/* 865 */                   reasonPhrase = SettingsFragment.this.getResources().getString(C0257R.string.settings_error_disconnect_from_twitter);
                        }
/* 868 */               Util.showCenteredToast(SettingsFragment.this.getActivity(), reasonPhrase);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 873 */           public void onLogoutComplete(String reqId) {
/* 874 */               if (SettingsFragment.this.getActivity() != null && !SettingsFragment.this.getActivity().isFinishing()) {
/* 877 */                   if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
/* 878 */                       SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                            }
/* 880 */                   StartActivity.toStart(SettingsFragment.this.getActivity());
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 885 */           public void onDeactivateAccountComplete(String reqId, int statusCode, String reasonPhrase, boolean success) {
                        int messageId;
/* 886 */               if (SettingsFragment.access$1800(SettingsFragment.this) != null) {
/* 887 */                   SettingsFragment.access$1800(SettingsFragment.this).dismiss();
                        }
/* 891 */               if (success) {
/* 892 */                   SettingsFragment.access$2400(SettingsFragment.this).logout();
/* 893 */                   messageId = C0257R.string.settings_account_deactivation_success;
/* 894 */                   SLog.m446d("User account successfully deactivated");
                        } else {
/* 896 */                   messageId = C0257R.string.settings_account_deactivation_fail;
/* 897 */                   SLog.m446d("User account deactivation failed");
                        }
/* 899 */               Util.showCenteredToast(SettingsFragment.this.getActivity(), messageId);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 904 */           public void onGetUsersMeComplete(String reqId, int statusCode, String reasonPhrase, long sessionOwnerId, VineUser meUser, UrlCachePolicy policy) {
/* 905 */               if (meUser != null) {
/* 906 */                   SettingsFragment.access$502(SettingsFragment.this, meUser.isEmailVerified());
/* 907 */                   SettingsFragment.access$1002(SettingsFragment.this, meUser.isPhoneVerified());
/* 908 */                   SettingsFragment.access$2700(SettingsFragment.this);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 913 */           public void onGetEditionsComplete(int statusCode, ArrayList<Pair<String, String>> editions) {
/* 914 */               if (statusCode == 200) {
/* 915 */                   SettingsFragment.access$2802(SettingsFragment.this, true);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 921 */           public void onRequestEmailVerificationComplete(String reqId, int statusCode, String reasonPhrase, String email) {
/* 922 */               if (statusCode == 200) {
/* 923 */                   PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(10);
/* 925 */                   p.setTargetFragment(SettingsFragment.this, 0);
/* 926 */                   p.setNeutralButton(android.R.string.ok).setMessage(C0257R.string.confirm_email_sent).show(SettingsFragment.this.getActivity().getSupportFragmentManager());
                        } else {
/* 930 */                   String reason = reasonPhrase != null ? reasonPhrase : SettingsFragment.this.getString(C0257R.string.generic_error);
/* 932 */                   Util.showCenteredToast(SettingsFragment.this.getActivity(), reason);
                        }
                    }
                }

                @Override // co.vine.android.SetThumbnailTask.SetThumbnailListener
/* 938 */       public void setThumbnailImage(Bitmap bitmap) {
/* 939 */           setImage(bitmap, true);
                }

/* 942 */       private void setImage(Bitmap bmp, boolean edited) {
/* 943 */           this.mThumbnail = bmp;
/* 944 */           if (bmp == null) {
/* 945 */               this.mAvatarImageView.setImageResource(C0257R.drawable.avatar_large);
/* 946 */               this.mAvatarActionView.setImageResource(C0257R.drawable.avatar_add);
                    } else {
/* 948 */               this.mAvatarActionView.setImageResource(edited ? C0257R.drawable.avatar_check : C0257R.drawable.avatar_edit);
/* 949 */               this.mAvatarImageView.setImageDrawable(new RecyclableBitmapDrawable(this.mAvatarImageView.getResources(), bmp));
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 954 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 955 */           super.onActivityCreated(savedInstanceState);
/* 956 */           Bundle args = getArguments();
/* 957 */           if (args != null) {
/* 958 */               String action = args.getString(BaseFragment.ARG_ACTION);
/* 960 */               if (HomeTabActivity.ACTION_VERIFICATION_COMPLETE.equals(action)) {
/* 961 */                   ScrollView scrollView = this.mScrollView;
/* 962 */                   View verified = scrollView.findViewById(C0257R.id.settings_phone_label);
/* 963 */                   scrollView.post(new RunnableC02849(scrollView, verified));
                        }
                    }
/* 971 */           this.mInviteSmsIntent = new Intent("android.intent.action.VIEW");
/* 972 */           this.mInviteSmsIntent.setType("vnd.android-dir/mms-sms");
/* 973 */           this.mInviteSmsIntent.putExtra("sms_body", ShareUtils.getSmsMessage(getActivity()));
/* 974 */           if (this.mInviteSmsIntent.resolveActivity(getActivity().getPackageManager()) == null) {
/* 975 */               this.mScrollView.findViewById(C0257R.id.invite_via_text).setVisibility(8);
/* 976 */               this.mScrollView.findViewById(C0257R.id.invite_via_text_divider).setVisibility(8);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SettingsFragment$9 */
/* 961 */       class RunnableC02849 implements Runnable {
                    final /* synthetic */ ScrollView val$scrollView;
                    final /* synthetic */ View val$verified;

/* 962 */           RunnableC02849(ScrollView scrollView, View view) {
                        this.val$scrollView = scrollView;
                        this.val$verified = view;
                    }

                    @Override // java.lang.Runnable
/* 965 */           public void run() {
/* 966 */               this.val$scrollView.smoothScrollTo(0, this.val$verified.getBottom());
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 982 */       public void onActivityResult(int requestCode, int resultCode, Intent extras) {
                    Uri uri;
/* 983 */           super.onActivityResult(requestCode, resultCode, extras);
                    switch (requestCode) {
                        case 1:
/* 1013 */                  if (resultCode == -1) {
/* 1014 */                      String username = extras.getStringExtra("screen_name");
/* 1015 */                      String token = extras.getStringExtra(Twitter.EXTRA_TOKEN);
/* 1016 */                      String secret = extras.getStringExtra(Twitter.EXTRA_TOKEN_SECRET);
/* 1017 */                      long userId = extras.getLongExtra("user_id", 0L);
/* 1018 */                      ProgressDialog d = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 1020 */                      d.setMessage(getString(C0257R.string.sign_up_authorizing));
/* 1021 */                      d.setProgress(0);
/* 1022 */                      d.show();
/* 1023 */                      this.mProgress = d;
/* 1024 */                      this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username, token, secret, userId);
                            } else {
/* 1027 */                      Util.showCenteredToast(getActivity(), C0257R.string.error_twitter_sdk);
                            }
                            break;
                        case 2:
/* 1032 */                  if (resultCode == -1) {
/* 1033 */                      String username2 = extras.getStringExtra("screen_name");
/* 1035 */                      String token2 = extras.getStringExtra("token");
/* 1036 */                      String secret2 = extras.getStringExtra("secret");
/* 1037 */                      long userId2 = extras.getLongExtra("user_id", 0L);
/* 1038 */                      ProgressDialog d2 = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 1040 */                      d2.setMessage(getString(C0257R.string.sign_up_authorizing));
/* 1041 */                      d2.setProgress(0);
/* 1042 */                      d2.show();
/* 1043 */                      this.mProgress = d2;
/* 1044 */                      this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username2, token2, secret2, userId2);
                            } else if (resultCode != 0) {
/* 1048 */                      Util.showCenteredToast(getActivity(), C0257R.string.error_xauth);
                            }
                            break;
                        case 3:
                        case 5:
/* 987 */                   if (resultCode == -1) {
/* 989 */                       if (requestCode == 3) {
/* 990 */                           uri = this.mAvatarUri;
                                } else {
/* 992 */                           uri = extras.getData();
                                }
/* 994 */                       Intent intent = new Intent(getActivity(), (Class<?>) EditProfileCropActivity.class).putExtra("uri", uri);
/* 996 */                       startActivityForResult(intent, 4);
                            }
                            break;
                        case 4:
/* 1001 */                  if (resultCode == -1 && extras != null) {
/* 1002 */                      Uri croppedUri = (Uri) extras.getParcelableExtra("uri");
/* 1003 */                      if (croppedUri != null) {
/* 1004 */                          ImageUtils.deleteTempPic(this.mAvatarUri);
/* 1005 */                          this.mAvatarUri = croppedUri;
/* 1006 */                          new SetThumbnailTask(this).execute(croppedUri);
                                }
/* 1008 */                      this.mPhotoChanged = true;
                                break;
                            }
                            break;
                        case 6:
/* 1053 */                  Activity activity = getActivity();
/* 1054 */                  int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
/* 1055 */                  if (errorCode == 0) {
/* 1056 */                      if (this.mNotifications != null) {
/* 1057 */                          this.mNotifications.setOnClickListener(this);
                                }
/* 1059 */                      if (this.mAlertIcon != null) {
/* 1060 */                          this.mAlertIcon.setVisibility(8);
                                }
/* 1063 */                      getActivity().startService(GCMRegistrationService.getRegisterIntent(activity, this.mAppController.getActiveId()));
                            } else {
/* 1066 */                      Util.showCenteredToast(getActivity(), C0257R.string.toast_gms_resolution_failed);
                            }
                            break;
                        case 7:
                            switch (resultCode) {
                                case ConfirmationFlowActivity.RESULT_VERIFY_PHONE_SUCCESS /* 1527 */:
/* 1073 */                          this.mPrefs.edit().putBoolean(Settings.PREF_PHONE_VERIFIED, true).apply();
/* 1074 */                          this.mIsPhoneVerified = true;
/* 1075 */                          updateVerificationIcons();
                                    break;
                                case ConfirmationFlowActivity.RESULT_VERIFY_PHONE_FAILURE /* 1528 */:
/* 1079 */                          this.mIsPhoneVerified = false;
/* 1080 */                          this.mPrefs.edit().putBoolean(Settings.PREF_PHONE_VERIFIED, false).apply();
/* 1081 */                          updateVerificationIcons();
                                    break;
                            }
                            break;
                        default:
/* 1088 */                  SLog.m447d("Facebook auth came back: {}", Integer.valueOf(requestCode));
/* 1089 */                  if (resultCode == -1) {
/* 1090 */                      Session session = Session.getActiveSession();
/* 1091 */                      if (session != null) {
/* 1092 */                          if (session.onActivityResult(getActivity(), requestCode, resultCode, extras)) {
/* 1094 */                              if (session.getPermissions().contains(AppController.PUBLISH_ACTIONS)) {
/* 1095 */                                  AppController.setPendingFacebookToken(getActivity(), session.getAccessToken());
/* 1097 */                                  ProgressDialog d3 = new ProgressDialog(getActivity(), 2);
/* 1099 */                                  d3.setMessage(getString(C0257R.string.login_logging_in));
/* 1100 */                                  d3.setProgress(0);
/* 1101 */                                  d3.show();
/* 1102 */                                  this.mProgress = d3;
/* 1103 */                                  this.mAppController.sendFacebookToken();
                                        } else {
/* 1105 */                                  getActivity().runOnUiThread(new RunnableC027610());
/* 1111 */                                  this.mAppController.connectToFacebookPublish(this);
                                        }
                                    } else {
/* 1114 */                              invalidateFacebookSessionUI();
                                    }
                                } else {
/* 1117 */                          invalidateFacebookSessionUI();
/* 1118 */                          super.onActivityResult(requestCode, resultCode, extras);
                                }
                            } else {
/* 1121 */                      invalidateFacebookSessionUI();
                            }
                            break;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SettingsFragment$10 */
/* 1103 */      class RunnableC027610 implements Runnable {
/* 1104 */          RunnableC027610() {
                    }

                    @Override // java.lang.Runnable
/* 1107 */          public void run() {
/* 1108 */              Util.showCenteredToast(SettingsFragment.this.getActivity(), C0257R.string.publish_to_facebook_toast);
                    }
                }

                @Override // co.vine.android.ImagePicker.Listener
/* 1129 */      public void setAvatarUrl(Uri url) {
/* 1130 */          this.mAvatarUri = url;
                }

                @Override // android.support.v4.app.Fragment
/* 1140 */      public boolean onOptionsItemSelected(MenuItem item) {
/* 1141 */          if (item.getItemId() == C0257R.id.done) {
/* 1142 */              if (isDirty()) {
/* 1143 */                  updateProfile(this.mAvatarUri);
                        } else {
/* 1145 */                  if (this.mProgress != null) {
/* 1146 */                      this.mProgress.dismiss();
                            }
/* 1148 */                  if (getActivity() != null) {
/* 1149 */                      getActivity().finish();
                            }
                        }
/* 1152 */              return true;
                    }
/* 1154 */          return super.onOptionsItemSelected(item);
                }

                @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 1159 */      public void onDialogDone(DialogInterface dialog, int id, int which) {
                    switch (id) {
                        case 1:
                            switch (which) {
                                case -3:
/* 1168 */                          this.mImagePicker.chooseImage(5);
                                    break;
                                case -1:
/* 1164 */                          this.mImagePicker.captureImage(3);
                                    break;
                            }
                            break;
                        case 2:
                            switch (which) {
                                case -3:
/* 1195 */                          this.mImagePicker.captureImage(3);
                                    break;
                                case -2:
/* 1199 */                          this.mImagePicker.chooseImage(5);
                                    break;
                                case -1:
/* 1189 */                          setThumbnailImage(null);
/* 1190 */                          this.mPhotoChanged = true;
/* 1191 */                          this.mAvatarUri = Uri.parse("");
                                    break;
                            }
                            break;
                        case 3:
                            switch (which) {
                                case -1:
/* 1208 */                          resetPrefs();
/* 1209 */                          FragmentActivity activity = getActivity();
/* 1210 */                          if (activity != null) {
/* 1211 */                              activity.finish();
                                    }
                                    break;
                            }
                            break;
                        case 4:
                            switch (which) {
                                case -1:
/* 1224 */                          FlurryUtils.trackLogout();
/* 1225 */                          if (this.mFbSession != null) {
/* 1226 */                              this.mFbSession.closeAndClearTokenInformation();
                                    }
/* 1228 */                          invalidateFacebookSessionUI();
/* 1229 */                          ProgressDialog d = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 1231 */                          d.setMessage(getString(C0257R.string.logging_out));
/* 1232 */                          d.setProgress(0);
/* 1233 */                          d.show();
/* 1234 */                          this.mProgress = d;
/* 1235 */                          this.mAppController.logout();
                                    break;
                            }
                            break;
                        case 5:
                            switch (which) {
                                case -1:
/* 1247 */                          ProgressDialog d2 = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 1249 */                          d2.setMessage(getString(C0257R.string.settings_disconnecting_twitter));
/* 1250 */                          d2.setProgress(0);
/* 1251 */                          d2.show();
/* 1252 */                          this.mProgress = d2;
/* 1253 */                          this.mAppController.disconnectTwitter(this.mAppController.getActiveSession());
                                    break;
                            }
                            break;
                        case 6:
                            switch (which) {
                                case -1:
/* 1265 */                          ProgressDialog d3 = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 1267 */                          d3.setMessage(getString(C0257R.string.settings_clearing_cache));
/* 1268 */                          d3.setProgress(0);
/* 1269 */                          d3.show();
/* 1270 */                          this.mProgress = d3;
/* 1271 */                          this.mAppController.clearDbCache(true);
                                    break;
                            }
                            break;
                        case 7:
                            switch (which) {
                                case -1:
/* 1283 */                          ProgressDialog d4 = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 1285 */                          d4.setMessage(getString(C0257R.string.settings_deactivating_account));
/* 1286 */                          d4.setProgress(0);
/* 1287 */                          d4.show();
/* 1288 */                          this.mProgress = d4;
/* 1289 */                          this.mAppController.deactivateAccount();
                                    break;
                            }
                            break;
                        case 8:
                            switch (which) {
                                case -1:
/* 1318 */                          CharSequence email = this.mEditTextEmail.getText();
/* 1319 */                          if (!TextUtils.isEmpty(email)) {
/* 1320 */                              this.mAppController.requestEmailVerification(this.mAppController.getActiveSession(), email.toString(), this.mAppController.getActiveId());
                                    }
                                    break;
                            }
                            break;
                        case 9:
                            switch (which) {
                                case -1:
/* 1301 */                          CharSequence phone = this.mEditTextPhone.getText();
/* 1302 */                          if (!TextUtils.isEmpty(phone)) {
/* 1303 */                              this.mAppController.requestPhoneVerification(this.mAppController.getActiveSession(), phone.toString(), this.mAppController.getActiveId());
                                    }
                                    break;
                            }
                            break;
                        case 11:
                            switch (which) {
                                case -1:
/* 1177 */                          if (this.mFbSession != null) {
/* 1178 */                              this.mFbSession.closeAndClearTokenInformation();
/* 1179 */                              this.mFbSession = null;
                                    }
/* 1181 */                          invalidateFacebookSessionUI();
                                    break;
                            }
                            break;
                    }
                }

                @Override // android.view.View.OnClickListener
/* 1338 */      public void onClick(View view) {
/* 1339 */          int id = view.getId();
/* 1340 */          if (id == C0257R.id.facebook_connect) {
/* 1341 */              if (this.mFbSession != null) {
/* 1342 */                  PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(11);
/* 1344 */                  p.setTargetFragment(this, 0);
/* 1345 */                  p.setTitle(C0257R.string.settings_disconnect_dialog).setPositiveButton(C0257R.string.yes).setNegativeButton(C0257R.string.cancel);
                            try {
/* 1349 */                      p.show(getActivity().getSupportFragmentManager());
/* 1546 */                      return;
                            } catch (Exception e) {
/* 1351 */                      CrashUtil.logException(e);
/* 1546 */                      return;
                            }
                        }
/* 1354 */              Session session = this.mAppController.getValidFacebookSession(getActivity(), false);
/* 1356 */              if (session == null) {
/* 1357 */                  this.mAppController.connectToFacebookInitialize(this);
/* 1546 */                  return;
                        } else {
/* 1359 */                  if (this.mAppController.getPendingFacebookToken() != null) {
/* 1360 */                      this.mAppController.sendFacebookToken();
/* 1546 */                      return;
                            }
/* 1546 */                  return;
                        }
                    }
/* 1364 */          if (id == C0257R.id.user_image) {
/* 1365 */              if (this.mAvatarUri != null && !Util.isDefaultAvatarUrl(this.mAvatarUri.toString())) {
/* 1366 */                  PromptDialogSupportFragment p2 = PromptDialogSupportFragment.newInstance(2);
/* 1368 */                  p2.setTargetFragment(this, 0);
/* 1369 */                  p2.setTitle(C0257R.string.sign_up_profile_photo).setPositiveButton(C0257R.string.remove_photo).setNeutralButton(C0257R.string.take_photo).setNegativeButton(C0257R.string.choose_photo);
                            try {
/* 1374 */                      p2.show(getActivity().getSupportFragmentManager());
/* 1546 */                      return;
                            } catch (Exception e2) {
/* 1376 */                      CrashUtil.logException(e2);
/* 1546 */                      return;
                            }
                        }
/* 1379 */              PromptDialogSupportFragment p3 = PromptDialogSupportFragment.newInstance(1);
/* 1381 */              p3.setTargetFragment(this, 0);
/* 1382 */              p3.setTitle(C0257R.string.sign_up_profile_photo).setPositiveButton(C0257R.string.take_photo).setNeutralButton(C0257R.string.choose_photo);
                        try {
/* 1386 */                  p3.show(getActivity().getSupportFragmentManager());
/* 1546 */                  return;
                        } catch (Exception e3) {
/* 1388 */                  CrashUtil.logException(e3);
/* 1546 */                  return;
                        }
                    }
/* 1391 */          if (id == C0257R.id.twitter_connect) {
/* 1392 */              if (this.mTwitterLogin) {
/* 1393 */                  PromptDialogSupportFragment p4 = PromptDialogSupportFragment.newInstance(5);
/* 1395 */                  p4.setMessage(C0257R.string.settings_disconnect_twitter);
/* 1396 */                  p4.setPositiveButton(C0257R.string.settings_disconnect_dialog);
/* 1397 */                  p4.setNegativeButton(C0257R.string.cancel);
/* 1398 */                  p4.setTargetFragment(this, 0);
                            try {
/* 1400 */                      p4.show(getActivity().getSupportFragmentManager());
/* 1546 */                      return;
                            } catch (Exception e4) {
/* 1402 */                      CrashUtil.logException(e4);
/* 1546 */                      return;
                            }
                        }
/* 1405 */              Twitter twitter = this.mAppController.getTwitter();
/* 1406 */              Activity activity = getActivity();
/* 1407 */              AppController.startTwitterAuthWithFinish(twitter, activity);
/* 1546 */              return;
                    }
/* 1409 */          if (id == C0257R.id.logout) {
/* 1410 */              PromptDialogSupportFragment p5 = PromptDialogSupportFragment.newInstance(4);
/* 1412 */              p5.setMessage(C0257R.string.logout_confirm);
/* 1413 */              p5.setPositiveButton(C0257R.string.logout);
/* 1414 */              p5.setNegativeButton(C0257R.string.cancel);
/* 1415 */              p5.setTargetFragment(this, 0);
                        try {
/* 1417 */                  p5.show(getActivity().getSupportFragmentManager());
/* 1546 */                  return;
                        } catch (Exception e5) {
/* 1419 */                  CrashUtil.logException(e5);
/* 1546 */                  return;
                        }
                    }
/* 1421 */          if (id == C0257R.id.settings_password) {
/* 1422 */              Intent i = new Intent(getActivity(), (Class<?>) ResetPasswordActivity.class);
/* 1423 */              if (!TextUtils.isEmpty(this.mEmail)) {
/* 1424 */                  i.putExtra("email", this.mEmail);
/* 1425 */                  i.putExtra("color", (-16777216) | this.mBackgroundColor);
                        }
/* 1427 */              startActivity(i);
/* 1546 */              return;
                    }
/* 1428 */          if (id == C0257R.id.content_controls) {
/* 1429 */              FlurryUtils.trackContentControls();
/* 1430 */              Intent intent = new Intent(getActivity(), (Class<?>) ContentControlsActivity.class);
/* 1431 */              intent.putExtra("color", this.mBackgroundColor);
/* 1432 */              startActivity(intent);
/* 1546 */              return;
                    }
/* 1433 */          if (id == C0257R.id.privacy_controls) {
/* 1434 */              Intent intent2 = new Intent(getActivity(), (Class<?>) PrivacyControlsActivity.class);
/* 1435 */              intent2.putExtra("color", this.mBackgroundColor);
/* 1436 */              startActivity(intent2);
/* 1546 */              return;
                    }
/* 1437 */          if (id == C0257R.id.sync) {
/* 1438 */              Intent intent3 = new Intent(getActivity(), (Class<?>) SyncControlsActivity.class);
/* 1439 */              intent3.putExtra("color", this.mBackgroundColor);
/* 1440 */              startActivity(intent3);
/* 1546 */              return;
                    }
/* 1441 */                              if (id == C0257R.id.updates) {
                        startActivity(new Intent(getActivity(), (Class<?>) UpdatesActivity.class));
                        return;
                    }
                    if (id == C0257R.id.find_friends) {
/* 1442 */              FlurryUtils.trackVisitFindFriends("Settings");
/* 1443 */              startActivity(new Intent(getActivity(), (Class<?>) FindFriendsActivity.class));
/* 1546 */              return;
                    }
/* 1444 */          if (id == C0257R.id.invite_via_email) {
/* 1445 */              FlurryUtils.trackInvite("email", "Settings");
/* 1446 */              Intent intent4 = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", "", null));
/* 1448 */              intent4.putExtra("android.intent.extra.SUBJECT", getString(C0257R.string.find_friends_invite_email_subject));
/* 1450 */              intent4.putExtra("android.intent.extra.TEXT", ShareUtils.getEmailMessage(getActivity()));
/* 1451 */              startActivity(Intent.createChooser(intent4, getString(C0257R.string.find_friends_email_choose_title)));
/* 1546 */              return;
                    }
/* 1453 */          if (id == C0257R.id.invite_via_text) {
/* 1454 */              FlurryUtils.trackInvite("sms", "Settings");
/* 1455 */              startActivity(Intent.createChooser(this.mInviteSmsIntent, getString(C0257R.string.send)));
/* 1546 */              return;
                    }
/* 1457 */          if (id == C0257R.id.clear_cache) {
/* 1458 */              PromptDialogSupportFragment p6 = PromptDialogSupportFragment.newInstance(6);
/* 1460 */              p6.setMessage(C0257R.string.settings_clear_cache_confirm_dialog);
/* 1461 */              p6.setNegativeButton(C0257R.string.cancel);
/* 1462 */              p6.setPositiveButton(C0257R.string.settings_clear_cache_confirm_button);
/* 1463 */              p6.setTargetFragment(this, 0);
/* 1464 */              p6.show(getActivity().getSupportFragmentManager());
/* 1546 */              return;
                    }
/* 1465 */          if (id == C0257R.id.privacy_policy) {
/* 1466 */              FlurryUtils.trackPrivacyPolicy();
/* 1467 */              Intent i2 = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
/* 1468 */              i2.putExtra("type", 1);
/* 1470 */              startActivity(i2);
/* 1546 */              return;
                    }
/* 1471 */          if (id == C0257R.id.terms_of_service) {
/* 1472 */              FlurryUtils.trackTos();
/* 1473 */              Intent i3 = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
/* 1474 */              i3.putExtra("type", 2);
/* 1475 */              startActivity(i3);
/* 1546 */              return;
                    }
/* 1476 */          if (id == C0257R.id.attribution) {
/* 1477 */              FlurryUtils.trackAttribution();
/* 1478 */              Intent i4 = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
/* 1479 */              i4.putExtra("type", 5);
/* 1480 */              startActivity(i4);
/* 1546 */              return;
                    }
/* 1481 */          if (id == C0257R.id.deactivate_account) {
/* 1482 */              FlurryUtils.trackDeactivateAccount();
/* 1483 */              PromptDialogSupportFragment p7 = PromptDialogSupportFragment.newInstance(7);
/* 1485 */              p7.setMessage(C0257R.string.settings_deactivate_account_dialog);
/* 1486 */              p7.setTitle(C0257R.string.settings_deactivate_account_title);
/* 1487 */              p7.setNegativeButton(C0257R.string.cancel);
/* 1488 */              p7.setPositiveButton(C0257R.string.settings_deactivate_account_confirm);
/* 1489 */              p7.setTargetFragment(this, 0);
                        try {
/* 1491 */                  p7.show(getActivity().getSupportFragmentManager());
/* 1546 */                  return;
                        } catch (Exception e6) {
/* 1493 */                  CrashUtil.logException(e6);
/* 1546 */                  return;
                        }
                    }
/* 1495 */          if (id == C0257R.id.version) {
/* 1496 */              this.mVersionTapCount++;
/* 1497 */              if (this.mVersionTapCount >= 6) {
/* 1498 */                  this.mVersionTapCount = 0;
/* 1499 */                  startActivity(new Intent(getActivity(), (Class<?>) DebugHomeActivity.class));
/* 1546 */                  return;
                        } else {
/* 1500 */                  if (this.mVersionTapCount == 3) {
                                try {
/* 1502 */                          PackageInfo pi = getActivity().getPackageManager().getPackageInfo("co.vine.android", 0);
/* 1503 */                          Util.showShortCenteredToast(getActivity(), "Version Code: " + pi.versionCode);
/* 1546 */                          return;
                                } catch (Exception e7) {
/* 1505 */                          SLog.m459e("Failed to show version code.", (Throwable) e7);
/* 1546 */                          return;
                                }
                            }
/* 1546 */                  return;
                        }
                    }
/* 1508 */          if (id == C0257R.id.locale) {
/* 1509 */              LocaleDialog ldf = LocaleDialog.newInstance();
/* 1510 */              ldf.show(getActivity().getSupportFragmentManager(), "locale");
/* 1546 */              return;
                    }
/* 1511 */          if (id == C0257R.id.edition_container || id == C0257R.id.edition_label) {
/* 1512 */              this.mEditionsSpinner.performClick();
/* 1546 */              return;
                    }
/* 1513 */          if (id == C0257R.id.notification_settings || id == C0257R.id.alert_icon) {
/* 1514 */              if (this.mErrorCode != 0) {
/* 1515 */                  Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(this.mErrorCode, getActivity(), 6);
/* 1517 */                  errorDialog.show();
/* 1546 */                  return;
                        } else {
/* 1519 */                  FlurryUtils.trackNotificationSettings();
/* 1520 */                  Intent intent5 = new Intent(getActivity(), (Class<?>) NotificationSettingsActivity.class);
/* 1521 */                  intent5.putExtra("color", this.mBackgroundColor);
/* 1522 */                  startActivity(intent5);
/* 1546 */                  return;
                        }
                    }
/* 1524 */          if (id == C0257R.id.email_verified) {
/* 1525 */              if (!this.mIsEmailVerified) {
/* 1528 */                  PromptDialogSupportFragment p8 = PromptDialogSupportFragment.newInstance(8);
/* 1530 */                  p8.setTargetFragment(this, 0);
                            p8.setPositiveButton(C0257R.string.confirm_email_yes).setMessage(getString(C0257R.string.confirm_email_prompt, this.mEmail)).setNeutralButton(android.R.string.cancel);
                            try {
/* 1535 */                      p8.show(getActivity().getSupportFragmentManager());
/* 1546 */                      return;
                            } catch (Exception e8) {
/* 1537 */                      CrashUtil.logException(e8);
/* 1546 */                      return;
                            }
                        }
/* 1546 */              return;
                    }
/* 1539 */          if (id == C0257R.id.phone_verified && !this.mIsPhoneVerified) {
/* 1544 */              getActivity().startActivityForResult(ConfirmationFlowActivity.getIntent(getActivity(), this.mPhone, false), 7);
                    }
                }

/* 1548 */      private boolean validate() {
/* 1549 */          FragmentActivity activity = getActivity();
/* 1550 */          if (this.mName.isEmpty()) {
/* 1551 */              if (activity == null) {
                            return false;
                        }
/* 1552 */              Util.showCenteredToast(activity, C0257R.string.name_length_toast);
                        return false;
                    }
/* 1556 */          if (this.mLocation.length() > 32) {
/* 1557 */              if (activity == null) {
                            return false;
                        }
/* 1558 */              Util.showCenteredToast(activity, C0257R.string.API_ERROR_LOCATION_INVALID_LENGTH);
                        return false;
                    }
/* 1563 */          if (this.mDescription.length() > 140) {
/* 1564 */              if (activity == null) {
                            return false;
                        }
/* 1565 */              Util.showCenteredToast(activity, C0257R.string.API_ERROR_DESCRIPTION_INVALID_LENGTH);
                        return false;
                    }
/* 1570 */          if (activity != null) {
/* 1571 */              ProgressDialog progressDialog = new ProgressDialog(activity, C0257R.style.ProgressDialogTheme);
/* 1573 */              progressDialog.setMessage(getString(C0257R.string.settings_updating));
/* 1574 */              progressDialog.show();
/* 1575 */              this.mProgress = progressDialog;
                    }
/* 1577 */          return true;
                }

/* 1580 */      private void updateVerificationIcons() {
/* 1581 */          Resources res = null;
                    try {
/* 1583 */              res = getResources();
                    } catch (Exception e) {
/* 1586 */              CrashUtil.logException(e);
                    }
/* 1589 */          if (this.mEditTextEmail != null && TextUtils.isEmpty(this.mEditTextEmail.getText().toString())) {
/* 1590 */              this.mEmailVerified.setVisibility(8);
                    } else if (res != null) {
/* 1593 */              this.mEmailVerified.setVisibility(0);
/* 1594 */              if (this.mIsEmailVerified) {
/* 1595 */                  this.mEmailVerified.setImageDrawable(res.getDrawable(C0257R.drawable.ic_settings_check));
                        } else {
/* 1597 */                  this.mEmailVerified.setImageDrawable(res.getDrawable(C0257R.drawable.ic_settings_alert));
                        }
                    }
/* 1602 */          if (this.mEditTextPhone != null && TextUtils.isEmpty(this.mEditTextPhone.getText().toString())) {
/* 1603 */              this.mPhoneVerified.setVisibility(8);
/* 1614 */              return;
                    }
/* 1605 */          this.mPhoneVerified.setVisibility(0);
/* 1606 */          if (res != null) {
/* 1607 */              if (this.mIsPhoneVerified) {
/* 1608 */                  this.mPhoneVerified.setImageDrawable(res.getDrawable(C0257R.drawable.ic_settings_check));
                        } else {
/* 1610 */                  this.mPhoneVerified.setImageDrawable(res.getDrawable(C0257R.drawable.ic_settings_alert));
                        }
                    }
                }

/* 1621 */      private void updateProfile(Uri avatarUri) {
/* 1622 */          if (validate()) {
/* 1623 */              if (!this.mOriginalName.equals(this.mName)) {
                            FlurryUtils.trackChangedName();
                        }
/* 1624 */              if (!this.mOriginalDescription.equals(this.mDescription)) {
                            FlurryUtils.trackChangedDescription();
                        }
/* 1625 */              if (!this.mOriginalLocation.equals(this.mLocation)) {
                            FlurryUtils.trackChangedLocation();
                        }
/* 1626 */              if (!this.mOriginalEmail.equals(this.mEmail)) {
                            FlurryUtils.trackChangedEmail();
                        }
/* 1627 */              if (!this.mOriginalEdition.equals(this.mEdition)) {
                            FlurryUtils.trackChangedEdition();
                        }
/* 1629 */              this.mAppController.updateProfile(this.mAppController.getActiveSession(), this.mName, this.mDescription, this.mLocation, this.mEmail, this.mPhone, this.mPhotoChanged ? avatarUri : null, this.mBackgroundColor);
/* 1632 */              this.mAppController.updateEdition(this.mAppController.getActiveSession(), this.mEdition);
                    }
                }

/* 1634 */      class EditionsSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
                    private Cursor mCursor;

/* 1635 */          EditionsSpinnerAdapter() {
                    }

                    @Override // android.widget.Adapter
/* 1640 */          public int getCount() {
/* 1641 */              if (this.mCursor != null) {
/* 1642 */                  return this.mCursor.getCount();
                        }
                        return 0;
                    }

/* 1647 */          public void changeCursor(Cursor c) {
/* 1648 */              this.mCursor = c;
/* 1649 */              notifyDataSetChanged();
                    }

                    @Override // android.widget.Adapter
/* 1653 */          public Object getItem(int i) {
/* 1654 */              return null;
                    }

                    @Override // android.widget.Adapter
/* 1658 */          public long getItemId(int i) {
/* 1659 */              return i;
                    }

                    @Override // android.widget.Adapter
/* 1663 */          public View getView(int i, View view, ViewGroup viewGroup) {
                        View res;
/* 1664 */              if (this.mCursor == null || i >= this.mCursor.getCount()) {
                            return view;
                        }
/* 1666 */              if (view != null) {
/* 1667 */                  res = view;
                        } else {
/* 1669 */                  res = LayoutInflater.from(SettingsFragment.this.getActivity()).inflate(C0257R.layout.settings_spinner_row_item, (ViewGroup) null);
                        }
/* 1671 */              TextView t = (TextView) res.findViewById(2131427387);
/* 1672 */              t.setText(getEditionName(i));
/* 1673 */              t.setSingleLine(true);
/* 1674 */              t.setEllipsize(TextUtils.TruncateAt.END);
/* 1677 */              return res;
                    }

                    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
/* 1681 */          public View getDropDownView(int i, View view, ViewGroup viewGroup) {
                        View res;
/* 1682 */              if (this.mCursor == null || i >= this.mCursor.getCount()) {
                            return view;
                        }
/* 1684 */              if (view != null) {
/* 1685 */                  res = view;
                        } else {
/* 1687 */                  res = LayoutInflater.from(SettingsFragment.this.getActivity()).inflate(C0257R.layout.settings_spinner_row_item, (ViewGroup) null);
                        }
/* 1689 */              TextView t = (TextView) res.findViewById(2131427387);
/* 1690 */              t.setText(getEditionName(i));
/* 1691 */              t.setSingleLine(false);
/* 1692 */              res.invalidate();
/* 1695 */              return res;
                    }

/* 1698 */          public void setSelectionToUserEdition() {
/* 1699 */              if (this.mCursor != null) {
/* 1700 */                  this.mCursor.moveToPosition(-1);
/* 1701 */                  while (this.mCursor.moveToNext()) {
/* 1702 */                      String editionCode = this.mCursor.getString(1);
/* 1703 */                      if (editionCode.equals(SettingsFragment.access$1200(SettingsFragment.this))) {
/* 1704 */                          SettingsFragment.access$2900(SettingsFragment.this).setSelection(this.mCursor.getPosition());
/* 1709 */                          return;
                                }
                            }
                        }
                    }

/* 1711 */          public String getEditionName(int index) {
/* 1712 */              if (this.mCursor == null || index >= this.mCursor.getCount()) {
                            return "";
                        }
/* 1713 */              this.mCursor.moveToPosition(index);
/* 1714 */              return this.mCursor.getString(2);
                    }

/* 1719 */          public String getEditionCode(int index) {
/* 1720 */              if (this.mCursor == null || index >= this.mCursor.getCount()) {
                            return "";
                        }
/* 1721 */              this.mCursor.moveToPosition(index);
/* 1722 */              return this.mCursor.getString(1);
                    }

/* 1727 */          public Object getCursor() {
/* 1728 */              return this.mCursor;
                    }
                }
            }
