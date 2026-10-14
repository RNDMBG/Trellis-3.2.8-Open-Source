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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    public class SignUpNameAvatarFragment extends BaseControllerFragment implements TextWatcher, View.OnClickListener, PromptDialogSupportFragment.OnDialogDoneListener, SetThumbnailTask.SetThumbnailListener, ImagePicker.Listener, ContactsHelper.ContactHelperListener, SpanClickListener {
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

/* 58 */        static /* synthetic */ EditText access$000(SignUpNameAvatarFragment x0) {
/* 59 */            return x0.mName;
                }

/* 58 */        static /* synthetic */ boolean access$100(SignUpNameAvatarFragment x0, int x1, View x2) {
/* 59 */            return x0.onEditorActionClicked(x1, x2);
                }

/* 58 */        static /* synthetic */ Dialog access$1000(SignUpNameAvatarFragment x0) {
/* 59 */            return x0.mProgress;
                }

/* 58 */        static /* synthetic */ TwitterUser access$1102(SignUpNameAvatarFragment x0, TwitterUser x1) {
/* 59 */            x0.mTwitterUser = x1;
                    return x1;
                }

/* 58 */        static /* synthetic */ EditText access$200(SignUpNameAvatarFragment x0) {
/* 59 */            return x0.mPhoneNumber;
                }

/* 58 */        static /* synthetic */ CheckBox access$300(SignUpNameAvatarFragment x0) {
/* 59 */            return x0.mFollowVineOnTwitterCheckBox;
                }

/* 58 */        static /* synthetic */ ViewGroup access$400(SignUpNameAvatarFragment x0) {
/* 59 */            return x0.mPhoneNumberContainer;
                }

/* 58 */        static /* synthetic */ Uri access$502(SignUpNameAvatarFragment x0, Uri x1) {
/* 59 */            x0.mProfileUri = x1;
                    return x1;
                }

/* 58 */        static /* synthetic */ void access$600(SignUpNameAvatarFragment x0, Bitmap x1) {
/* 59 */            x0.setAvatar(x1);
                }

/* 58 */        static /* synthetic */ boolean access$700(SignUpNameAvatarFragment x0) {
/* 59 */            return x0.mPhotoAttached;
                }

/* 58 */        static /* synthetic */ ImageView access$800(SignUpNameAvatarFragment x0) {
/* 59 */            return x0.mProfileImage;
                }

/* 58 */        static /* synthetic */ ImagePicker access$900(SignUpNameAvatarFragment x0) {
/* 59 */            return x0.mImagePicker;
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 98 */        public void onCreate(Bundle savedInstanceState) {
/* 99 */            super.onCreate(savedInstanceState);
/* 100 */           this.mAppController = AppController.getInstance(getActivity());
/* 102 */           if (savedInstanceState != null && savedInstanceState.containsKey(STATE_PROFILE_URI)) {
/* 103 */               this.mProfileUri = (Uri) savedInstanceState.getParcelable(STATE_PROFILE_URI);
/* 104 */               if (this.mProfileUri != null) {
/* 105 */                   new SetThumbnailTask(this).execute(this.mProfileUri);
                        }
                    }
/* 109 */           setHasOptionsMenu(true);
/* 110 */           ((SignUpPagerActivity) getActivity()).setBarTitle(C0257R.string.login_sign_up);
/* 112 */           Bundle bundle = getArguments();
/* 113 */           if (bundle != null && bundle.containsKey("user")) {
/* 114 */               this.mLogin = (VineLogin) bundle.getParcelable("user");
                    }
/* 116 */           this.mAppSessionListener = new SignUpListener();
/* 117 */           this.mHasPromptedForPhoto = false;
/* 119 */           this.mImagePicker = new ImagePicker(getActivity(), this, this.mAppController.getActiveId());
                }

                @Override // android.support.v4.app.Fragment
/* 124 */       public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
/* 125 */           View view = inflater.inflate(C0257R.layout.sign_up_flow_name, root, false);
/* 126 */           TextView mTos = (TextView) view.findViewById(C0257R.id.tos_line_2);
/* 127 */           StyleSpan[] boldStyle = {new StyleSpan(1), new StyleSpan(1)};
/* 129 */           Spanned spanned = Util.getSpannedText(boldStyle, getString(C0257R.string.sign_up_tos_line_2), CommonUtil.MARKER_QUOTES);
/* 132 */           mTos.setText(spanned);
/* 133 */           mTos.setMovementMethod(LinkMovementMethod.getInstance());
/* 135 */           Spannable clickSpannable = (Spannable) mTos.getText();
/* 136 */           StyleSpan[] spans = (StyleSpan[]) spanned.getSpans(0, spanned.length(), StyleSpan.class);
/* 137 */           Resources res = getResources();
/* 140 */           int start = spanned.getSpanStart(spans[0]);
/* 141 */           int end = spanned.getSpanEnd(spans[0]);
/* 142 */           StyledClickableSpan clickableSpan = new StyledClickableSpan(1, null, this);
/* 143 */           clickableSpan.setColor(res.getColor(C0257R.color.text_fineprint));
/* 144 */           Util.safeSetSpan(clickSpannable, clickableSpan, start, end, 33);
/* 148 */           int start2 = spanned.getSpanStart(spans[1]);
/* 149 */           int end2 = spanned.getSpanEnd(spans[1]);
/* 150 */           StyledClickableSpan clickableSpan2 = new StyledClickableSpan(2, null, this);
/* 151 */           clickableSpan2.setColor(res.getColor(C0257R.color.text_fineprint));
/* 152 */           Util.safeSetSpan(clickSpannable, clickableSpan2, start2, end2, 33);
/* 155 */           return view;
                }

/* 158 */       private boolean onEditorActionClicked(int actionId, View v) {
/* 159 */           FragmentActivity activity = getActivity();
/* 160 */           if (activity != null && actionId == 6 && validate()) {
/* 161 */               InputMethodManager imm = (InputMethodManager) activity.getSystemService("input_method");
/* 163 */               imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
/* 164 */               onNextClicked(activity);
/* 166 */               return true;
                    }
/* 166 */           return true;
                }

                @Override // android.support.v4.app.Fragment
/* 170 */       public void onViewCreated(View view, Bundle savedInstanceState) {
/* 171 */           super.onViewCreated(view, savedInstanceState);
/* 172 */           this.mName = (EditText) view.findViewById(C0257R.id.sign_up_username);
/* 173 */           this.mName.addTextChangedListener(this);
/* 174 */           this.mName.setOnEditorActionListener(new C02891());
/* 181 */           this.mPhoneNumberContainer = (ViewGroup) view.findViewById(C0257R.id.phone_tos_container);
/* 183 */           this.mPhoneNumber = (EditText) view.findViewById(C0257R.id.signup_name_phone);
/* 184 */           this.mPhoneNumber.addTextChangedListener(this);
/* 185 */           this.mPhoneNumber.setOnEditorActionListener(new C02902());
/* 192 */           View userImageRoot = view.findViewById(C0257R.id.user_image_container);
/* 194 */           this.mProfileImageAction = (ImageView) userImageRoot.findViewById(C0257R.id.user_image_action);
/* 195 */           this.mProfileImageAction.setVisibility(0);
/* 196 */           userImageRoot.setOnClickListener(this);
/* 198 */           this.mProfileImage = (ImageView) userImageRoot.findViewById(C0257R.id.user_image);
/* 200 */           this.mFollowVineOnTwitterCheckBox = (CheckBox) view.findViewById(C0257R.id.twitter_vine_follow_enabled);
/* 201 */           this.mFollowVineOnTwitterCheckBox.setChecked(true);
/* 202 */           this.mFollowVineOnTwitterCheckBox.setEnabled(true);
/* 205 */           LinearLayout followVineLayout = (LinearLayout) view.findViewById(C0257R.id.follow_vine_on_twitter_selection);
/* 207 */           followVineLayout.setOnClickListener(new ViewOnClickListenerC02913());
/* 215 */           if (this.mLogin != null) {
/* 216 */               ProgressDialog dialog = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 217 */               dialog.setMessage(getString(C0257R.string.loading));
/* 218 */               dialog.setProgress(0);
/* 219 */               dialog.show();
/* 220 */               this.mProgress = dialog;
/* 221 */               this.mAppController.fetchTwitterUser(this.mLogin);
                    }
/* 224 */           ContactsHelper.loadContacts(this, this);
                }

                /* JADX INFO: renamed from: co.vine.android.SignUpNameAvatarFragment$1 */
/* 172 */       class C02891 implements TextView.OnEditorActionListener {
/* 173 */           C02891() {
                    }

                    @Override // android.widget.TextView.OnEditorActionListener
/* 176 */           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/* 177 */               return SignUpNameAvatarFragment.access$100(SignUpNameAvatarFragment.this, actionId, SignUpNameAvatarFragment.access$000(SignUpNameAvatarFragment.this));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SignUpNameAvatarFragment$2 */
/* 183 */       class C02902 implements TextView.OnEditorActionListener {
/* 184 */           C02902() {
                    }

                    @Override // android.widget.TextView.OnEditorActionListener
/* 187 */           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/* 188 */               return SignUpNameAvatarFragment.access$100(SignUpNameAvatarFragment.this, actionId, SignUpNameAvatarFragment.access$200(SignUpNameAvatarFragment.this));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SignUpNameAvatarFragment$3 */
/* 205 */       class ViewOnClickListenerC02913 implements View.OnClickListener {
/* 206 */           ViewOnClickListenerC02913() {
                    }

                    @Override // android.view.View.OnClickListener
/* 209 */           public void onClick(View v) {
/* 210 */               SignUpNameAvatarFragment.access$300(SignUpNameAvatarFragment.this).toggle();
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 228 */       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 229 */           inflater.inflate(C0257R.menu.next, menu);
/* 230 */           this.mNextButton = menu.findItem(C0257R.id.next);
/* 231 */           if (this.mNextButton != null) {
/* 232 */               this.mNextButton.setEnabled(validate());
                    }
                }

/* 236 */       private boolean showProfilePhotoPrompt() {
/* 237 */           if (this.mProfileUri != null || this.mHasPromptedForPhoto || this.mPhotoAttached) {
                        return false;
                    }
/* 238 */           PromptDialogSupportFragment promptDialogSupportFragment = PromptDialogSupportFragment.newInstance(1).setMessage(C0257R.string.profile_photo_prompt).setPositiveButton(C0257R.string.set_photo).setNegativeButton(C0257R.string.skip);
/* 243 */           promptDialogSupportFragment.setListener(this);
                    try {
/* 245 */               promptDialogSupportFragment.show(getActivity().getSupportFragmentManager());
/* 249 */               this.mHasPromptedForPhoto = true;
                        return true;
                    } catch (IllegalStateException e) {
                        return false;
                    }
                }

/* 255 */       private void nextClicked() {
/* 256 */           SignUpPagerActivity activity = (SignUpPagerActivity) getActivity();
/* 257 */           activity.setName(this.mName.getText().toString());
/* 258 */           activity.setPhone(this.mPhoneNumber.getText().toString());
/* 259 */           if (this.mPhotoAttached) {
/* 260 */               activity.setProfile(this.mProfileUri);
                    }
/* 262 */           if (this.mTwitterUser != null) {
/* 263 */               this.mTwitterUser.name = this.mName.getText().toString();
/* 264 */               activity.setTwitterUser(this.mTwitterUser);
/* 265 */               boolean followVineOnTwitter = this.mFollowVineOnTwitterCheckBox.isChecked();
/* 266 */               activity.setFollowVineOnTwitter(followVineOnTwitter);
                    }
/* 269 */           activity.toNextStep();
                }

                @Override // android.support.v4.app.Fragment
/* 273 */       public boolean onOptionsItemSelected(MenuItem item) {
                    return item.getItemId() == 2131427941 ? onNextClicked(getActivity()) : super.onOptionsItemSelected(item);
                }

/* 281 */       private boolean onNextClicked(Activity a) {
/* 282 */           if (!showProfilePhotoPrompt()) {
/* 283 */               SignUpPagerActivity activity = (SignUpPagerActivity) a;
/* 284 */               activity.setName(this.mName.getText().toString());
/* 285 */               activity.setPhone(this.mPhoneNumber.getText().toString());
/* 286 */               activity.setProfile(this.mImagePicker.getSavedImageUri());
/* 287 */               if (this.mTwitterUser != null) {
/* 288 */                   this.mTwitterUser.name = this.mName.getText().toString();
/* 289 */                   activity.setTwitterUser(this.mTwitterUser);
                        }
/* 291 */               nextClicked();
                    }
/* 293 */           Util.setSoftKeyboardVisibility(getActivity(), this.mName, false);
/* 294 */           return true;
                }

                @Override // android.text.TextWatcher
/* 298 */       public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
/* 302 */       public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
/* 306 */       public void afterTextChanged(Editable editable) {
/* 307 */           if (this.mNextButton != null) {
/* 308 */               this.mNextButton.setEnabled(validate());
                    }
                }

                @Override // co.vine.android.util.ContactsHelper.ContactHelperListener
/* 314 */       public void onNameLoaded(String nameText) {
/* 315 */           EditText name = this.mName;
/* 316 */           if (TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(nameText)) {
/* 317 */               name.setText(nameText);
/* 318 */               name.setSelection(nameText.length());
                    }
                }

                @Override // co.vine.android.util.ContactsHelper.ContactHelperListener
/* 323 */       public void onPhoneNumberLoaded(String phoneNumber) {
/* 324 */           if (this.mPhoneNumber.getVisibility() == 0 && TextUtils.isEmpty(this.mPhoneNumber.getText()) && !TextUtils.isEmpty(phoneNumber)) {
/* 326 */               this.mPhoneNumber.setText(phoneNumber);
                    }
                }

                @Override // co.vine.android.util.ContactsHelper.ContactHelperListener
/* 331 */       public void onEmailLoaded(String email) {
                }

/* 334 */       private boolean validate() {
/* 335 */           return AuthenticationUtils.isUsernameValid(this.mName.getText().toString());
                }

/* 338 */       private void addPhoto() {
/* 339 */           if (this.mPhotoAttached) {
/* 340 */               PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(3);
/* 342 */               p.setTargetFragment(this, 0);
/* 343 */               p.setTitle(C0257R.string.sign_up_profile_photo).setPositiveButton(C0257R.string.remove_photo).setNeutralButton(C0257R.string.take_photo).setNegativeButton(C0257R.string.choose_photo).show(getActivity().getSupportFragmentManager());
                    } else {
/* 349 */               PromptDialogSupportFragment p2 = PromptDialogSupportFragment.newInstance(2);
/* 351 */               p2.setTargetFragment(this, 0);
                        try {
/* 353 */                   p2.setTitle(C0257R.string.sign_up_profile_photo).setPositiveButton(C0257R.string.take_photo).setNeutralButton(C0257R.string.choose_photo).show(getActivity().getSupportFragmentManager());
                        } catch (IllegalStateException e) {
/* 358 */                   CrashUtil.logException(e);
                        }
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 364 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 365 */           super.onActivityCreated(savedInstanceState);
/* 366 */           setAvatar(null);
                }

/* 369 */       private void setAvatar(Bitmap bitmap) {
/* 370 */           if (bitmap != null) {
/* 371 */               this.mProfileImage.setImageDrawable(new RecyclableBitmapDrawable(this.mProfileImage.getResources(), bitmap));
/* 372 */               this.mProfileImage.setColorFilter((ColorFilter) null);
/* 373 */               this.mProfileImageAction.setImageResource(C0257R.drawable.avatar_edit);
/* 374 */               this.mPhotoAttached = true;
/* 380 */               return;
                    }
/* 376 */           this.mProfileImage.setImageResource(C0257R.drawable.blank_avatar_nux);
/* 377 */           this.mProfileImageAction.setImageResource(C0257R.drawable.avatar_add);
/* 378 */           this.mPhotoAttached = false;
                }

                @Override // android.support.v4.app.Fragment
/* 383 */       public void onActivityResult(int requestCode, int resultCode, Intent extras) {
                    Uri uri;
/* 384 */           super.onActivityResult(requestCode, resultCode, extras);
                    switch (requestCode) {
                        case 1:
                        case 3:
/* 388 */                   if (resultCode == -1) {
/* 390 */                       if (requestCode == 1) {
/* 391 */                           uri = this.mProfileUri;
                                } else {
/* 393 */                           uri = extras.getData();
                                }
/* 395 */                       Intent intent = new Intent(getActivity(), (Class<?>) EditProfileCropActivity.class).putExtra("uri", uri);
/* 397 */                       getActivity().startActivityForResult(intent, 2);
                            } else {
/* 399 */                       this.mProfileUri = null;
                            }
                            break;
                        case 2:
/* 404 */                   if (resultCode == -1 && extras != null) {
/* 405 */                       Uri croppedUri = (Uri) extras.getParcelableExtra("uri");
/* 406 */                       if (croppedUri != null) {
/* 407 */                           ImageUtils.deleteTempPic(this.mProfileUri);
/* 408 */                           this.mProfileUri = croppedUri;
/* 409 */                           new SetThumbnailTask(this).execute(croppedUri);
                                }
                            } else {
/* 412 */                       this.mProfileUri = null;
                            }
                            break;
                    }
                }

                @Override // co.vine.android.ImagePicker.Listener
/* 420 */       public void setAvatarUrl(Uri url) {
/* 421 */           this.mProfileUri = url;
                }

                @Override // android.view.View.OnClickListener
/* 425 */       public void onClick(View view) {
/* 426 */           if (view.getId() == 2131427595) {
/* 427 */               addPhoto();
                    }
                }

                @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 432 */       public void onDialogDone(DialogInterface dialog, int id, int which) {
                    switch (id) {
                        case 1:
                            switch (which) {
                                case -2:
/* 441 */                           nextClicked();
                                    break;
                                case -1:
/* 437 */                           addPhoto();
                                    break;
                            }
                            break;
                        case 2:
                            switch (which) {
                                case -3:
/* 454 */                           this.mImagePicker.chooseImage(3);
                                    break;
                                case -1:
/* 450 */                           this.mImagePicker.captureImage(1);
                                    break;
                            }
                            break;
                        case 3:
                            switch (which) {
                                case -3:
/* 467 */                           this.mImagePicker.captureImage(1);
                                    break;
                                case -2:
/* 471 */                           this.mImagePicker.chooseImage(3);
                                    break;
                                case -1:
/* 463 */                           setThumbnailImage(null);
                                    break;
                            }
                            break;
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 483 */       public void onSaveInstanceState(Bundle outState) {
/* 484 */           super.onSaveInstanceState(outState);
/* 485 */           outState.putParcelable(STATE_PROFILE_URI, this.mProfileUri);
                }

                @Override // co.vine.android.SetThumbnailTask.SetThumbnailListener
/* 489 */       public void setThumbnailImage(Bitmap bm) {
/* 490 */           setAvatar(bm);
/* 491 */           if (this.mPhotoAttached) {
/* 492 */               this.mHasPromptedForPhoto = true;
                    }
                }

/* 494 */       class SignUpListener extends AppSessionListener {
/* 495 */           SignUpListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 500 */           public void onGetTwitterUserComplete(String reqId, int statusCode, String reasonPhrase, TwitterUser user, VineLogin login) {
/* 501 */               if (statusCode != 200 && user != null) {
/* 502 */                   SignUpNameAvatarFragment.access$000(SignUpNameAvatarFragment.this).setText(user.name);
/* 503 */                   SignUpNameAvatarFragment.access$400(SignUpNameAvatarFragment.this).setVisibility(0);
/* 504 */                   SignUpNameAvatarFragment.access$502(SignUpNameAvatarFragment.this, null);
/* 505 */                   if (!user.defaultProfileImage) {
/* 506 */                       int w = SignUpNameAvatarFragment.this.getResources().getDimensionPixelSize(C0257R.dimen.user_image_size_large);
/* 507 */                       ImageKey key = new ImageKey(user.profileUrl, w, w, true);
/* 508 */                       Bitmap bm = SignUpNameAvatarFragment.this.mAppController.getPhotoBitmap(key);
/* 509 */                       SignUpNameAvatarFragment.access$600(SignUpNameAvatarFragment.this, bm);
/* 511 */                       if (SignUpNameAvatarFragment.access$700(SignUpNameAvatarFragment.this)) {
/* 512 */                           SignUpNameAvatarFragment.access$800(SignUpNameAvatarFragment.this).setTag(null);
/* 513 */                           SignUpNameAvatarFragment.access$900(SignUpNameAvatarFragment.this).saveProfileImage(bm);
/* 514 */                           if (SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this) != null) {
/* 515 */                               SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this).dismiss();
                                    }
                                } else {
/* 518 */                           SignUpNameAvatarFragment.access$800(SignUpNameAvatarFragment.this).setTag(key);
                                }
                            } else if (SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this) != null) {
/* 522 */                       SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this).dismiss();
                            }
/* 526 */                   SignUpNameAvatarFragment.access$1102(SignUpNameAvatarFragment.this, user);
/* 532 */                   return;
                        }
/* 528 */               if (SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this) != null) {
/* 529 */                   SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this).dismiss();
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 535 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 536 */               ImageKey key = (ImageKey) SignUpNameAvatarFragment.access$800(SignUpNameAvatarFragment.this).getTag();
/* 537 */               UrlImage image = images.get(key);
/* 538 */               if (image != null && image.isValid()) {
/* 539 */                   SignUpNameAvatarFragment.access$600(SignUpNameAvatarFragment.this, image.bitmap);
/* 540 */                   SignUpNameAvatarFragment.access$900(SignUpNameAvatarFragment.this).saveProfileImage(image.bitmap);
/* 541 */                   if (SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this) != null) {
/* 542 */                       SignUpNameAvatarFragment.access$1000(SignUpNameAvatarFragment.this).dismiss();
                            }
                        }
                    }
                }

                @Override // co.vine.android.widget.SpanClickListener
/* 550 */       public void onSpanClicked(View view, int type, Object tag) {
/* 551 */           if (getActivity() != null) {
/* 554 */               Intent i = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
                        switch (type) {
                            case 1:
/* 557 */                       i.putExtra("type", 1);
                                break;
                            case 2:
/* 561 */                       i.putExtra("type", 2);
                                break;
                            default:
/* 569 */                       return;
                        }
/* 568 */               startActivity(i);
                    }
                }
            }
