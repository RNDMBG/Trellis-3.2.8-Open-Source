            package co.vine.android;

            import android.app.Activity;
            import android.app.Dialog;
            import android.app.ProgressDialog;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.ColorFilter;
            import android.net.Uri;
            import android.os.Bundle;
            import android.support.v4.app.FragmentActivity;
            import android.text.Editable;
            import android.text.Spannable;
            import android.text.Spanned;
            import android.text.TextUtils;
            import android.text.TextWatcher;
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
            import android.widget.CheckBox;
            import android.widget.EditText;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.TextView;
            import co.vine.android.api.TwitterUser;
            import co.vine.android.api.VineLogin;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.util.AuthenticationUtils;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.ContactsHelper;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.ImageUtils;
            import co.vine.android.util.Util;
            import co.vine.android.widget.SpanClickListener;
            import co.vine.android.widget.StyledClickableSpan;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import java.util.HashMap;
            
            
    public class SignUpNameAvatarFragment extends BaseControllerFragment implements TextWatcher, View.OnClickListener, PromptDialogSupportFragment.OnDialogDoneListener, SetThumbnailTask.SetThumbnailListener, ImagePicker.Listener, ContactsHelper.ContactHelperListener, SpanClickListener {
                public static final String ARG_LOGIN = "user";
                private static final int DIALOG_REMOVE_TAKE_OR_CHOOSE_PHOTO = 3;
                private static final int DIALOG_SET_PHOTO_OR_SKIP = 1;
                private static final int DIALOG_TAKE_OR_CHOOSE_PHOTO = 2;
                public static final int REQUEST_CODE_CHOOSE_PHOTO = 3;
                public static final int REQUEST_CODE_CROP = 2;
                public static final int REQUEST_CODE_TAKE_PHOTO = 1;
                private static final int SPAN_PRIVACY_POLICY = 1;
                private static final int SPAN_TOS = 2;
                private static final String STATE_PROFILE_URI = "state_pi";
                private CheckBox mFollowVineOnTwitterCheckBox;
                private boolean mHasPromptedForPhoto;
                private ImagePicker mImagePicker;
                private VineLogin mLogin;
                private EditText mName;
                private MenuItem mNextButton;
                private EditText mPhoneNumber;
                private ViewGroup mPhoneNumberContainer;
                private boolean mPhotoAttached;
                private ImageView mProfileImage;
                private ImageView mProfileImageAction;
                private Uri mProfileUri;
                private Dialog mProgress;
                private TwitterUser mTwitterUser;

        static  EditText access$000(SignUpNameAvatarFragment x0) {
            return x0.mName;
                }

        static  boolean access$100(SignUpNameAvatarFragment x0, int x1, View x2) {
            return x0.onEditorActionClicked(x1, x2);
                }

        static  Dialog access$1000(SignUpNameAvatarFragment x0) {
            return x0.mProgress;
                }

        static  TwitterUser access$1102(SignUpNameAvatarFragment x0, TwitterUser x1) {
            x0.mTwitterUser = x1;
                    return x1;
                }

        static  EditText access$200(SignUpNameAvatarFragment x0) {
            return x0.mPhoneNumber;
                }

        static  CheckBox access$300(SignUpNameAvatarFragment x0) {
            return x0.mFollowVineOnTwitterCheckBox;
                }

        static  ViewGroup access$400(SignUpNameAvatarFragment x0) {
            return x0.mPhoneNumberContainer;
                }

        static  Uri access$502(SignUpNameAvatarFragment x0, Uri x1) {
            x0.mProfileUri = x1;
                    return x1;
                }

        static  void access$600(SignUpNameAvatarFragment x0, Bitmap x1) {
            x0.setAvatar(x1);
                }

        static  boolean access$700(SignUpNameAvatarFragment x0) {
            return x0.mPhotoAttached;
                }

        static  ImageView access$800(SignUpNameAvatarFragment x0) {
            return x0.mProfileImage;
                }

        static  ImagePicker access$900(SignUpNameAvatarFragment x0) {
            return x0.mImagePicker;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
           this.mAppController = AppController.getInstance(getActivity());
           if (savedInstanceState != null && savedInstanceState.containsKey(STATE_PROFILE_URI)) {
               this.mProfileUri = (Uri) savedInstanceState.getParcelable(STATE_PROFILE_URI);
               if (this.mProfileUri != null) {
                   new SetThumbnailTask(this).execute(this.mProfileUri);
                        }
                    }
           setHasOptionsMenu(true);
           ((SignUpPagerActivity) getActivity()).setBarTitle(R.string.login_sign_up);
           Bundle bundle = getArguments();
           if (bundle != null && bundle.containsKey("user")) {
               this.mLogin = (VineLogin) bundle.getParcelable("user");
                    }
           this.mAppSessionListener = new SignUpListener();
           this.mHasPromptedForPhoto = false;
           this.mImagePicker = new ImagePicker(getActivity(), this, this.mAppController.getActiveId());
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
           View view = inflater.inflate(R.layout.sign_up_flow_name, root, false);
           TextView mTos = (TextView) view.findViewById(R.id.tos_line_2);
           StyleSpan[] boldStyle = {new StyleSpan(1), new StyleSpan(1)};
           Spanned spanned = Util.getSpannedText(boldStyle, getString(R.string.sign_up_tos_line_2), CommonUtil.MARKER_QUOTES);
           mTos.setText(spanned);
           mTos.setMovementMethod(LinkMovementMethod.getInstance());
           Spannable clickSpannable = (Spannable) mTos.getText();
           StyleSpan[] spans = (StyleSpan[]) spanned.getSpans(0, spanned.length(), StyleSpan.class);
           Resources res = getResources();
           int start = spanned.getSpanStart(spans[0]);
           int end = spanned.getSpanEnd(spans[0]);
           StyledClickableSpan clickableSpan = new StyledClickableSpan(1, null, this);
           clickableSpan.setColor(res.getColor(R.color.text_fineprint));
           Util.safeSetSpan(clickSpannable, clickableSpan, start, end, 33);
           int start2 = spanned.getSpanStart(spans[1]);
           int end2 = spanned.getSpanEnd(spans[1]);
           StyledClickableSpan clickableSpan2 = new StyledClickableSpan(2, null, this);
           clickableSpan2.setColor(res.getColor(R.color.text_fineprint));
           Util.safeSetSpan(clickSpannable, clickableSpan2, start2, end2, 33);
           return view;
                }

       private boolean onEditorActionClicked(int actionId, View v) {
           FragmentActivity activity = getActivity();
           if (activity != null && actionId == 6 && validate()) {
               InputMethodManager imm = (InputMethodManager) activity.getSystemService("input_method");
               imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
               onNextClicked(activity);
               return true;
                    }
           return true;
                }

                @Override 
       public void onViewCreated(View view, Bundle savedInstanceState) {
           super.onViewCreated(view, savedInstanceState);
           this.mName = (EditText) view.findViewById(R.id.sign_up_username);
           this.mName.addTextChangedListener(this);
           this.mName.setOnEditorActionListener(new C02891());
           this.mPhoneNumberContainer = (ViewGroup) view.findViewById(R.id.phone_tos_container);
           this.mPhoneNumber = (EditText) view.findViewById(R.id.signup_name_phone);
           this.mPhoneNumber.addTextChangedListener(this);
           this.mPhoneNumber.setOnEditorActionListener(new C02902());
           View userImageRoot = view.findViewById(R.id.user_image_container);
           this.mProfileImageAction = (ImageView) userImageRoot.findViewById(R.id.user_image_action);
           this.mProfileImageAction.setVisibility(0);
           userImageRoot.setOnClickListener(this);
           this.mProfileImage = (ImageView) userImageRoot.findViewById(R.id.user_image);
           this.mFollowVineOnTwitterCheckBox = (CheckBox) view.findViewById(R.id.twitter_vine_follow_enabled);
           this.mFollowVineOnTwitterCheckBox.setChecked(true);
           this.mFollowVineOnTwitterCheckBox.setEnabled(true);
           LinearLayout followVineLayout = (LinearLayout) view.findViewById(R.id.follow_vine_on_twitter_selection);
           followVineLayout.setOnClickListener(new ViewOnClickListenerC02913());
           if (this.mLogin != null) {
               ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.ProgressDialogTheme);
               dialog.setMessage(getString(R.string.loading));
               dialog.setProgress(0);
               dialog.show();
               this.mProgress = dialog;
               this.mAppController.fetchTwitterUser(this.mLogin);
                    }
           ContactsHelper.loadContacts(this, this);
                }

                
       class C02891 implements TextView.OnEditorActionListener {
           C02891() {
                    }

                    @Override 
           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               return SignUpNameAvatarFragment.access$100(SignUpNameAvatarFragment.this, actionId, SignUpNameAvatarFragment.access$000(SignUpNameAvatarFragment.this));
                    }
                }

                
       class C02902 implements TextView.OnEditorActionListener {
           C02902() {
                    }

                    @Override 
           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               return SignUpNameAvatarFragment.access$100(SignUpNameAvatarFragment.this, actionId, SignUpNameAvatarFragment.access$200(SignUpNameAvatarFragment.this));
                    }
                }

                
       class ViewOnClickListenerC02913 implements View.OnClickListener {
           ViewOnClickListenerC02913() {
                    }

                    @Override 
           public void onClick(View v) {
               SignUpNameAvatarFragment.access$300(SignUpNameAvatarFragment.this).toggle();
                    }
                }

                @Override 
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           inflater.inflate(R.menu.next, menu);
           this.mNextButton = menu.findItem(R.id.next);
           if (this.mNextButton != null) {
               this.mNextButton.setEnabled(validate());
                    }
                }

       private boolean showProfilePhotoPrompt() {
           if (this.mProfileUri != null || this.mHasPromptedForPhoto || this.mPhotoAttached) {
                        return false;
                    }
           PromptDialogSupportFragment promptDialogSupportFragment = PromptDialogSupportFragment.newInstance(1).setMessage(R.string.profile_photo_prompt).setPositiveButton(R.string.set_photo).setNegativeButton(R.string.skip);
           promptDialogSupportFragment.setListener(this);
                    try {
               promptDialogSupportFragment.show(getActivity().getSupportFragmentManager());
               this.mHasPromptedForPhoto = true;
                        return true;
                    } catch (IllegalStateException e) {
                        return false;
                    }
                }

       private void nextClicked() {
           SignUpPagerActivity activity = (SignUpPagerActivity) getActivity();
           activity.setName(this.mName.getText().toString());
           activity.setPhone(this.mPhoneNumber.getText().toString());
           if (this.mPhotoAttached) {
               activity.setProfile(this.mProfileUri);
                    }
           if (this.mTwitterUser != null) {
               this.mTwitterUser.name = this.mName.getText().toString();
               activity.setTwitterUser(this.mTwitterUser);
               boolean followVineOnTwitter = this.mFollowVineOnTwitterCheckBox.isChecked();
               activity.setFollowVineOnTwitter(followVineOnTwitter);
                    }
           activity.toNextStep();
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
                    return item.getItemId() == R.id.next ? onNextClicked(getActivity()) : super.onOptionsItemSelected(item);
                }

       private boolean onNextClicked(Activity a) {
           if (!showProfilePhotoPrompt()) {
               SignUpPagerActivity activity = (SignUpPagerActivity) a;
               activity.setName(this.mName.getText().toString());
               activity.setPhone(this.mPhoneNumber.getText().toString());
               activity.setProfile(this.mImagePicker.getSavedImageUri());
               if (this.mTwitterUser != null) {
                   this.mTwitterUser.name = this.mName.getText().toString();
                   activity.setTwitterUser(this.mTwitterUser);
                        }
               nextClicked();
                    }
           Util.setSoftKeyboardVisibility(getActivity(), this.mName, false);
           return true;
                }

                @Override 
       public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override 
       public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override 
       public void afterTextChanged(Editable editable) {
           if (this.mNextButton != null) {
               this.mNextButton.setEnabled(validate());
                    }
                }

                @Override 
       public void onNameLoaded(String nameText) {
           EditText name = this.mName;
           if (TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(nameText)) {
               name.setText(nameText);
               name.setSelection(nameText.length());
                    }
                }

                @Override 
       public void onPhoneNumberLoaded(String phoneNumber) {
           if (this.mPhoneNumber.getVisibility() == 0 && TextUtils.isEmpty(this.mPhoneNumber.getText()) && !TextUtils.isEmpty(phoneNumber)) {
               this.mPhoneNumber.setText(phoneNumber);
                    }
                }

                @Override 
       public void onEmailLoaded(String email) {
                }

       private boolean validate() {
           return AuthenticationUtils.isUsernameValid(this.mName.getText().toString());
                }

       private void addPhoto() {
           if (this.mPhotoAttached) {
               PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(3);
               p.setTargetFragment(this, 0);
               p.setTitle(R.string.sign_up_profile_photo).setPositiveButton(R.string.remove_photo).setNeutralButton(R.string.take_photo).setNegativeButton(R.string.choose_photo).show(getActivity().getSupportFragmentManager());
                    } else {
               PromptDialogSupportFragment p2 = PromptDialogSupportFragment.newInstance(2);
               p2.setTargetFragment(this, 0);
                        try {
                   p2.setTitle(R.string.sign_up_profile_photo).setPositiveButton(R.string.take_photo).setNeutralButton(R.string.choose_photo).show(getActivity().getSupportFragmentManager());
                        } catch (IllegalStateException e) {
                   CrashUtil.logException(e);
                        }
                    }
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           setAvatar(null);
                }

       private void setAvatar(Bitmap bitmap) {
           if (bitmap != null) {
               this.mProfileImage.setImageDrawable(new RecyclableBitmapDrawable(this.mProfileImage.getResources(), bitmap));
               this.mProfileImage.setColorFilter((ColorFilter) null);
               this.mProfileImageAction.setImageResource(R.drawable.avatar_edit);
               this.mPhotoAttached = true;
               return;
                    }
           this.mProfileImage.setImageResource(R.drawable.blank_avatar_nux);
           this.mProfileImageAction.setImageResource(R.drawable.avatar_add);
           this.mPhotoAttached = false;
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent extras) {
                    Uri uri;
           super.onActivityResult(requestCode, resultCode, extras);
                    switch (requestCode) {
                        case 1:
                        case 3:
                   if (resultCode == -1) {
                       if (requestCode == 1) {
                           uri = this.mProfileUri;
                                } else {
                           uri = extras.getData();
                                }
                       Intent intent = new Intent(getActivity(), (Class<?>) EditProfileCropActivity.class).putExtra("uri", uri);
                       getActivity().startActivityForResult(intent, 2);
                            } else {
                       this.mProfileUri = null;
                            }
                            break;
                        case 2:
                   if (resultCode == -1 && extras != null) {
                       Uri croppedUri = (Uri) extras.getParcelableExtra("uri");
                       if (croppedUri != null) {
                           ImageUtils.deleteTempPic(this.mProfileUri);
                           this.mProfileUri = croppedUri;
                           new SetThumbnailTask(this).execute(croppedUri);
                                }
                            } else {
                       this.mProfileUri = null;
                            }
                            break;
                    }
                }

                @Override 
       public void setAvatarUrl(Uri url) {
           this.mProfileUri = url;
                }

                @Override 
       public void onClick(View view) {
           if (view.getId() == R.id.user_image_container) {
               addPhoto();
                    }
                }

                @Override 
       public void onDialogDone(DialogInterface dialog, int id, int which) {
                    switch (id) {
                        case 1:
                            switch (which) {
                                case -2:
                           nextClicked();
                                    break;
                                case -1:
                           addPhoto();
                                    break;
                            }
                            break;
                        case 2:
                            switch (which) {
                                case -3:
                           this.mImagePicker.chooseImage(3);
                                    break;
                                case -1:
                           this.mImagePicker.captureImage(1);
                                    break;
                            }
                            break;
                        case 3:
                            switch (which) {
                                case -3:
                           this.mImagePicker.captureImage(1);
                                    break;
                                case -2:
                           this.mImagePicker.chooseImage(3);
                                    break;
                                case -1:
                           setThumbnailImage(null);
                                    break;
                            }
                            break;
                    }
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putParcelable(STATE_PROFILE_URI, this.mProfileUri);
                }

                @Override 
       public void setThumbnailImage(Bitmap bm) {
           setAvatar(bm);
           if (this.mPhotoAttached) {
               this.mHasPromptedForPhoto = true;
                    }
                }

       class SignUpListener extends AppSessionListener {
           SignUpListener() {
                    }

                    @Override 
           public void onGetTwitterUserComplete(String reqId, int statusCode, String reasonPhrase, TwitterUser user, VineLogin login) {
               if (statusCode != 200 && user != null) {
                   SignUpNameAvatarFragment.access$000(SignUpNameAvatarFragment.this).setText(user.name);
                   SignUpNameAvatarFragment.access$400(SignUpNameAvatarFragment.this).setVisibility(0);
                   SignUpNameAvatarFragment.access$502(SignUpNameAvatarFragment.this, null);
                   if (!user.defaultProfileImage) {
                       int w = SignUpNameAvatarFragment.this.getResources().getDimensionPixelSize(R.dimen.user_image_size_large);
                       ImageKey key = new ImageKey(user.profileUrl, w, w, true);
                       Bitmap bm = SignUpNameAvatarFragment.this.mAppController.getPhotoBitmap(key);
                       SignUpNameAvatarFragment.access$600(SignUpNameAvatarFragment.this, bm);
                       if (SignUpNameAvatarFragment.access$700(SignUpNameAvatarFragment.this)) {
                           SignUpNameAvatarFragment.access$800(SignUpNameAvatarFragment.this).setTag(null);
                           SignUpNameAvatarFragment.access$900(SignUpNameAvatarFragment.this).saveProfileImage(bm);
                           if (SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this) != null) {
                               SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this).dismiss();
                                    }
                                } else {
                           SignUpNameAvatarFragment.access$800(SignUpNameAvatarFragment.this).setTag(key);
                                }
                            } else if (SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this) != null) {
                       SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this).dismiss();
                            }
                   SignUpNameAvatarFragment.access$1102(SignUpNameAvatarFragment.this, user);
                   return;
                        }
               if (SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this) != null) {
                   SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this).dismiss();
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               ImageKey key = (ImageKey) SignUpNameAvatarFragment.access$800(SignUpNameAvatarFragment.this).getTag();
               UrlImage image = images.get(key);
               if (image != null && image.isValid()) {
                   SignUpNameAvatarFragment.access$600(SignUpNameAvatarFragment.this, image.bitmap);
                   SignUpNameAvatarFragment.access$900(SignUpNameAvatarFragment.this).saveProfileImage(image.bitmap);
                   if (SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this) != null) {
                       SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this).dismiss();
                            }
                        }
                    }
                }

                @Override 
       public void onSpanClicked(View view, int type, Object tag) {
           if (getActivity() != null) {
               Intent i = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
                        switch (type) {
                            case 1:
                       i.putExtra("type", 1);
                                break;
                            case 2:
                       i.putExtra("type", 2);
                                break;
                            default:
                       return;
                        }
               startActivity(i);
                    }
                }
            }
