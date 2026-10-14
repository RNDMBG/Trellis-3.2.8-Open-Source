            package co.vine.android;

            import android.app.Activity;
            import android.content.Context;
            import android.content.Intent;
            import android.content.res.ColorStateList;
            import android.content.res.Resources;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.drawable.Drawable;
            import android.graphics.drawable.GradientDrawable;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.support.v4.view.ViewPager;
            import android.text.Editable;
            import android.text.TextUtils;
            import android.text.TextWatcher;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.EditText;
            import android.widget.ImageView;
            import android.widget.RelativeLayout;
            import android.widget.TabHost;
            import android.widget.TextView;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.service.VineUploadService;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.views.FlowLayout;
            import co.vine.android.widget.FakeActionBar;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.TypefacesEditText;
            import co.vine.android.widget.TypefacesTextView;
            import co.vine.android.widget.tabs.IconTabHost;
            import co.vine.android.widget.tabs.TabIndicator;
            import co.vine.android.widget.tabs.TabInfo;
            import co.vine.android.widget.tabs.TabsAdapter;
            import co.vine.android.widget.tabs.ViewPagerScrollBar;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacv.cpp.avformat;
            import com.twitter.android.widget.TopScrollable;
            import java.util.ArrayList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 51 */    public class RecipientPickerActivity extends BaseControllerActionBarActivity implements TabHost.OnTabChangeListener, IconTabHost.OnTabClickedListener, TextWatcher, TypefacesEditText.KeyboardListener {
                public static final String ARG_TAG = "tag";
                public static final String EXTRA_MAX_LOOPS = "max_loops";
                public static final String EXTRA_MESSAGE = "message";
                public static final String EXTRA_POST_ID = "post_id";
                public static final String EXTRA_PREVIEW_PATH = "preview_path";
                public static final String EXTRA_SHARED_THUMB_URL = "thumb_url";
                public static final String EXTRA_SHARED_VIDEO_URL = "vid_url";
                public static final String EXTRA_THUMBNAIL_PATH = "thumbnail_path";
                public static final String EXTRA_VIDEO_PATH = "video_path";
                private static final int OVERFLOW_INDEX = 3;
                public static final String TAG_ADDRESS_BOOK = "address_book";
                public static final String TAG_FRIENDS = "friends";
                private boolean isForcingFullList;
                private String mCaptionMessage;
                private float mDensity;
                private FlowLayout mFlowLayout;
                private int mMaxLoops;
                private View mOverFlowView;
                private long mPostId;
                private int mProfileColor;
                private EditText mQuery;
                private CharSequence mQueryHint;
                private ImageView mSendButton;
                private String mSharedThumbnailUrl;
                private String mSharedVideoUrl;
                private IconTabHost mTabHost;
                private TabsAdapter mTabsAdapter;
                private String mVideoPath;
                private ViewPager mViewPager;
                private int mVineGreen;
                private final VineRecipient mLastExtra = VineRecipient.fromEmail(null, -1, null, 0);
                private final ArrayList<VineRecipient> mRecipients = new ArrayList<>();
                private final ArrayList<View> mRecycledViews = new ArrayList<>();
                private int mSelectedForRemoval = -1;

/* 52 */        static /* synthetic */ int access$000(RecipientPickerActivity x0) {
/* 53 */            return x0.mProfileColor;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 101 */       public void onCreate(Bundle savedInstanceState) {
/* 102 */           super.onCreate(savedInstanceState, C0257R.layout.recipient_picker_activity, true);
/* 103 */           Intent intent = getIntent();
/* 104 */           this.mVineGreen = getResources().getColor(C0257R.color.vine_green);
/* 105 */           this.mVideoPath = intent.getStringExtra("video_path");
/* 107 */           setupActionBar((Boolean) false, (Boolean) true, C0257R.string.send_to, (Boolean) true, (Boolean) true);
/* 109 */           this.mProfileColor = Util.getDefaultSharedPrefs(this).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
/* 112 */           if (this.mProfileColor == Settings.DEFAULT_PROFILE_COLOR || this.mProfileColor <= 0) {
/* 113 */               this.mProfileColor = 16777215 & getResources().getColor(C0257R.color.vine_green);
                    }
/* 116 */           this.mProfileColor = (-16777216) | this.mProfileColor;
/* 118 */           ViewPagerScrollBar scrollBar = (ViewPagerScrollBar) findViewById(C0257R.id.scrollbar);
/* 119 */           scrollBar.setLineColor(this.mProfileColor);
/* 120 */           scrollBar.setRange(2);
/* 122 */           ColorStateList indicatorStateList = TabIndicator.createTextColorList(this.mProfileColor, getResources().getColor(C0257R.color.light_light_gray));
/* 124 */           ViewPager viewPager = (ViewPager) findViewById(C0257R.id.pager);
/* 125 */           viewPager.setPageMargin(getResources().getDimensionPixelSize(C0257R.dimen.home_pager_margin));
/* 126 */           viewPager.setPageMarginDrawable(C0257R.color.bg_grouped_list);
/* 127 */           viewPager.setOffscreenPageLimit(1);
/* 129 */           IconTabHost tabHost = this.mTabHost;
/* 130 */           TabsAdapter tabsAdapter = new TabsAdapter(this, tabHost, viewPager, scrollBar);
/* 131 */           tabsAdapter.setSetActionBarColorOnPageSelectedEnabled(false);
/* 132 */           LayoutInflater inflater = LayoutInflater.from(this);
/* 133 */           tabHost.setOnTabChangedListener(this);
/* 134 */           tabHost.setOnTabClickedListener(this);
/* 137 */           Bundle friendsBundle = new Bundle();
/* 138 */           friendsBundle.putString("tag", "friends");
/* 139 */           friendsBundle.putBoolean("refresh", true);
/* 140 */           friendsBundle.putInt("empty_desc", C0257R.string.no_friends_found);
/* 142 */           TabIndicator indicator = TabIndicator.newTextIndicator(inflater, C0257R.layout.normal_tab_indicator, tabHost, C0257R.string.tab_title_vine_friends, false);
/* 146 */           TextView indicatorText = indicator.getIndicatorText();
/* 147 */           indicatorText.setTextColor(indicatorStateList);
/* 148 */           indicatorText.setTypeface(Typefaces.get(this).boldContent);
/* 150 */           tabsAdapter.addTab(tabHost.newTabSpec("friends").setIndicator(indicator), FriendsRecipientPickerFragment.class, friendsBundle);
/* 155 */           Bundle contactsBundle = new Bundle();
/* 156 */           contactsBundle.putString("tag", TAG_ADDRESS_BOOK);
/* 157 */           contactsBundle.putBoolean("refresh", false);
/* 158 */           contactsBundle.putInt("empty_desc", C0257R.string.no_contacts_found);
/* 160 */           TabIndicator indicator2 = TabIndicator.newTextIndicator(inflater, C0257R.layout.normal_tab_indicator, tabHost, C0257R.string.tab_title_address_book, false);
/* 164 */           TextView indicatorText2 = indicator2.getIndicatorText();
/* 165 */           indicatorText2.setTextColor(indicatorStateList);
/* 166 */           indicatorText2.setTypeface(Typefaces.get(this).regularContentBold);
/* 168 */           tabsAdapter.addTab(tabHost.newTabSpec(TAG_ADDRESS_BOOK).setIndicator(indicator2), ContactsRecipientPickerFragment.class, contactsBundle);
/* 172 */           this.mCaptionMessage = getIntent().getStringExtra("message");
/* 173 */           this.mMaxLoops = getIntent().getIntExtra("max_loops", 0);
/* 174 */           this.mPostId = getIntent().getLongExtra("post_id", 0L);
/* 175 */           this.mSharedVideoUrl = getIntent().getStringExtra(EXTRA_SHARED_VIDEO_URL);
/* 176 */           this.mSharedThumbnailUrl = getIntent().getStringExtra(EXTRA_SHARED_THUMB_URL);
/* 178 */           ViewGroup background = (ViewGroup) findViewById(android.R.id.tabs);
/* 179 */           background.setBackgroundColor(getResources().getColor(C0257R.color.solid_white));
/* 181 */           this.mFlowLayout = (FlowLayout) findViewById(C0257R.id.picked_recipient_flow);
/* 183 */           TypefacesEditText query = (TypefacesEditText) inflater.inflate(C0257R.layout.recipient_picker_edit_text, (ViewGroup) this.mFlowLayout, false);
/* 184 */           query.setKeyboardListener(this);
/* 185 */           query.addTextChangedListener(this);
/* 186 */           query.setInputType(524288);
/* 187 */           query.setTypeface(Typefaces.get(this).boldContent);
/* 189 */           this.mFlowLayout.addView(query);
/* 191 */           this.mQuery = query;
/* 192 */           this.mQuery.setOnClickListener(new ViewOnClickListenerC02581());
/* 198 */           this.mTabsAdapter = tabsAdapter;
/* 199 */           this.mViewPager = viewPager;
/* 201 */           this.mDensity = getResources().getDisplayMetrics().density;
                }

                /* JADX INFO: renamed from: co.vine.android.RecipientPickerActivity$1 */
/* 190 */       class ViewOnClickListenerC02581 implements View.OnClickListener {
/* 191 */           ViewOnClickListenerC02581() {
                    }

                    @Override // android.view.View.OnClickListener
/* 194 */           public void onClick(View v) {
/* 195 */               RecipientPickerActivity.this.setForceFullList(false);
                    }
                }

/* 204 */       public void showPage(String tag) {
/* 205 */           ArrayList<TabInfo> tabs = this.mTabsAdapter.getTabs();
                    for (int i = 0; i < tabs.size(); i++) {
/* 207 */               TabInfo tab = tabs.get(i);
/* 208 */               if (tag.equals(tab.getTag())) {
/* 209 */                   this.mViewPager.setCurrentItem(i, true);
                        }
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 215 */       public void onPostCreate(Bundle savedInstanceState) {
/* 216 */           super.onPostCreate(savedInstanceState);
/* 217 */           Resources res = getResources();
/* 218 */           FakeActionBar fakeActionBar = getFakeActionBar();
/* 219 */           Drawable backIcon = res.getDrawable(C0257R.drawable.ic_arrow_back_large);
/* 220 */           backIcon.setColorFilter(new PorterDuffColorFilter(this.mProfileColor, PorterDuff.Mode.SRC_IN));
/* 221 */           fakeActionBar.getBackIcon().setImageDrawable(backIcon);
/* 222 */           fakeActionBar.getTitleView().setTypeface(Typefaces.get(this).mediumContent);
/* 223 */           fakeActionBar.getTitleView().setTextSize(2, 18.0f);
/* 224 */           addSendIcon();
                }

/* 227 */       private void addSendIcon() {
/* 228 */           Resources res = getResources();
/* 229 */           FakeActionBar fakeActionBar = getFakeActionBar();
/* 230 */           int gray = res.getColor(C0257R.color.light_light_gray);
/* 231 */           ImageView sendButton = new C02592(this, gray);
/* 238 */           ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (((double) this.mDensity) * 36.5d), (int) (((double) this.mDensity) * 36.5d));
/* 239 */           sendButton.setImageResource(C0257R.drawable.ic_action_send);
/* 240 */           sendButton.setOnClickListener(new ViewOnClickListenerC02603());
/* 246 */           sendButton.setLayoutParams(params);
/* 247 */           sendButton.setScaleType(ImageView.ScaleType.CENTER);
/* 248 */           fakeActionBar.getActionBarRight().addView(sendButton);
/* 249 */           this.mSendButton = sendButton;
/* 250 */           this.mSendButton.setEnabled(isSendButtonEnabled());
                }

                /* JADX INFO: renamed from: co.vine.android.RecipientPickerActivity$2 */
/* 229 */       class C02592 extends ImageView {
                    final /* synthetic */ int val$gray;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
/* 230 */           C02592(Context x0, int i) {
                        super(x0);
                        this.val$gray = i;
                    }

                    @Override // android.view.View
/* 233 */           public void setEnabled(boolean enabled) {
/* 234 */               super.setEnabled(enabled);
/* 235 */               setColorFilter(new PorterDuffColorFilter(enabled ? RecipientPickerActivity.access$000(RecipientPickerActivity.this) : this.val$gray, PorterDuff.Mode.SRC_IN));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.RecipientPickerActivity$3 */
/* 238 */       class ViewOnClickListenerC02603 implements View.OnClickListener {
/* 239 */           ViewOnClickListenerC02603() {
                    }

                    @Override // android.view.View.OnClickListener
/* 242 */           public void onClick(View v) {
/* 243 */               RecipientPickerActivity.this.onSendButtonClicked();
                    }
                }

/* 253 */       public boolean isSendButtonEnabled() {
                    return this.mRecipients.size() > 0;
                }

/* 257 */       public void addRecipient(VineRecipient recipient) {
/* 258 */           boolean wasInOverFlowMode = isInOverflowMode();
/* 259 */           this.mRecipients.add(recipient);
/* 260 */           SLog.m449d("Recipient added: {} {} {}", recipient.key, Long.valueOf(recipient.userId), recipient.value);
/* 261 */           if (!TextUtils.isEmpty(this.mQuery.getHint())) {
/* 262 */               this.mQueryHint = this.mQuery.getHint();
/* 263 */               this.mQuery.setHint("");
                    }
/* 265 */           if (!TextUtils.isEmpty(this.mQuery.getText())) {
/* 266 */               this.mQuery.setText("");
                    }
/* 268 */           invalidateEditTextUI(this.mRecipients.size() - 1, recipient, true, wasInOverFlowMode);
/* 269 */           if (this.mSendButton != null) {
/* 270 */               this.mSendButton.setEnabled(isSendButtonEnabled());
                    }
                }

/* 274 */       public void removeRecipient(VineRecipient recipient) {
/* 275 */           boolean wasInOverFlowMode = isInOverflowMode();
/* 276 */           int originalIndex = this.mRecipients.indexOf(recipient);
/* 277 */           this.mRecipients.remove(recipient);
/* 278 */           SLog.m449d("Recipient removed: {} {} {}", recipient.key, Long.valueOf(recipient.userId), recipient.value);
/* 279 */           if (this.mRecipients.size() == 0) {
/* 280 */               this.mQuery.setHint(this.mQueryHint);
                    }
/* 282 */           invalidateEditTextUI(originalIndex, recipient, false, wasInOverFlowMode);
/* 283 */           this.mSelectedForRemoval = -1;
/* 284 */           if (this.mSendButton != null) {
/* 285 */               this.mSendButton.setEnabled(isSendButtonEnabled());
                    }
                }

/* 289 */       public boolean isRecipientSelected(VineRecipient recipient) {
/* 290 */           return this.mRecipients.contains(recipient);
                }

/* 293 */       public void onSendButtonClicked() {
/* 294 */           if (!this.mRecipients.isEmpty()) {
                        if (this.mPostId > 0) {
/* 296 */                   startService(VineUploadService.getVMSharePostIntent(this, false, -1L, new ArrayList(this.mRecipients), this.mPostId, this.mSharedVideoUrl, this.mSharedThumbnailUrl));
                        } else {
/* 299 */                   startService(VineUploadService.getVMPostIntent(this, this.mVideoPath, false, -1L, new ArrayList(this.mRecipients), this.mCaptionMessage, this.mMaxLoops));
                        }
/* 303 */               startService(VineUploadService.getShowProgressIntent(this));
                        if (this.mPostId <= 0) {
/* 306 */                   Intent i = new Intent(getApplicationContext(), (Class<?>) HomeTabActivity.class);
/* 307 */                   i.setAction(HomeTabActivity.ACTION_VM_SENT);
/* 308 */                   i.setFlags(avformat.AVFMT_SEEK_TO_PTS);
/* 309 */                   startActivity(i);
                        }
/* 311 */               finish();
                    }
                }

/* 315 */       private void invalidateDisplayForExtra(int n) {
/* 316 */           if (n == 1) {
/* 317 */               this.mLastExtra.display = getString(C0257R.string.and_x_others_single);
                    } else {
/* 319 */               this.mLastExtra.display = getString(C0257R.string.and_x_others_plural, new Object[]{Integer.valueOf(n)});
                    }
                }

/* 323 */       private void invalidateEditTextUI(int index, VineRecipient recipient, boolean isAdding, boolean wasInOverflowMode) {
                    View viewToAdd;
                    View viewToAdd2;
/* 324 */           SLog.m449d("Invalidate UI: adding: {}, original index: {}, wasOverflow: {}.", Boolean.valueOf(isAdding), Integer.valueOf(index), Boolean.valueOf(wasInOverflowMode));
/* 325 */           SLog.m447d("Before child count: {}.", Integer.valueOf(this.mFlowLayout.getChildCount()));
/* 326 */           if (isAdding) {
/* 327 */               if (isInOverflowMode()) {
/* 328 */                   invalidateDisplayForExtra(this.mRecipients.size() - 3);
/* 329 */                   this.mOverFlowView = bindOrCreateViewForRecipient(index, this.mOverFlowView, this.mLastExtra, false);
/* 330 */                   if (this.mFlowLayout.getChildCount() <= 4) {
/* 331 */                       this.mFlowLayout.addView(this.mOverFlowView, 3);
                            }
                        } else {
/* 335 */                   if (this.mRecycledViews.isEmpty()) {
/* 336 */                       viewToAdd2 = bindOrCreateViewForRecipient(index, null, recipient, false);
                            } else {
/* 338 */                       viewToAdd2 = bindOrCreateViewForRecipient(index, this.mRecycledViews.remove(this.mRecycledViews.size() - 1), recipient, false);
                            }
/* 340 */                   this.mFlowLayout.addView(viewToAdd2, this.mFlowLayout.getChildCount() - 1);
                        }
                    } else if (wasInOverflowMode) {
/* 344 */               if (index < 3) {
/* 345 */                   VineRecipient recipientToAdd = this.mRecipients.get(2);
/* 346 */                   this.mRecycledViews.add(this.mFlowLayout.getChildAt(index));
/* 347 */                   this.mFlowLayout.removeViewAt(index);
/* 349 */                   if (this.mRecycledViews.isEmpty()) {
/* 350 */                       viewToAdd = bindOrCreateViewForRecipient(index, null, recipientToAdd, false);
                            } else {
/* 352 */                       viewToAdd = bindOrCreateViewForRecipient(index, this.mRecycledViews.remove(this.mRecycledViews.size() - 1), recipientToAdd, false);
                            }
/* 354 */                   this.mFlowLayout.addView(viewToAdd, 2);
/* 355 */                   if (isInOverflowMode()) {
/* 356 */                       invalidateDisplayForExtra(this.mRecipients.size() - 3);
/* 357 */                       bindOrCreateViewForRecipient(index, this.mOverFlowView, this.mLastExtra, false);
                            }
                        } else if (isInOverflowMode() && index != 3) {
/* 362 */                   invalidateDisplayForExtra(this.mRecipients.size() - 3);
/* 363 */                   bindOrCreateViewForRecipient(index, this.mOverFlowView, this.mLastExtra, false);
                        }
/* 367 */               if (!isInOverflowMode()) {
/* 368 */                   this.mFlowLayout.removeView(this.mOverFlowView);
                        }
                    } else {
/* 371 */               this.mRecycledViews.add(this.mFlowLayout.getChildAt(index));
/* 372 */               this.mFlowLayout.removeViewAt(index);
                    }
/* 375 */           SLog.m447d("After child count: {}.", Integer.valueOf(this.mFlowLayout.getChildCount()));
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 379 */       protected void onResume() {
/* 380 */           super.onResume();
/* 381 */           this.mTabsAdapter.previousTab = this.mTabHost.getCurrentTab();
                }

                @Override // android.widget.TabHost.OnTabChangeListener
/* 385 */       public void onTabChanged(String s) {
/* 386 */           this.mViewPager.setCurrentItem(this.mTabHost.getCurrentTab());
                }

                @Override // android.support.v7.app.ActionBarActivity
/* 390 */       public void onSupportContentChanged() {
/* 391 */           super.onSupportContentChanged();
/* 392 */           this.mTabHost = (IconTabHost) findViewById(android.R.id.tabhost);
/* 393 */           if (this.mTabHost == null) {
/* 394 */               throw new RuntimeException("Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'");
                    }
/* 399 */           this.mTabHost.setup();
/* 400 */           this.mViewPager = (ViewPager) findViewById(C0257R.id.pager);
                }

                @Override // co.vine.android.widget.tabs.IconTabHost.OnTabClickedListener
/* 404 */       public void onCurrentTabClicked() {
/* 405 */           scrollTop();
                }

/* 408 */       public void scrollTop() {
                    View view;
                    TopScrollable list;
/* 409 */           if (this.mTabsAdapter != null && this.mViewPager != null) {
/* 413 */               Fragment fragment = this.mTabsAdapter.getCurrentFragment();
/* 415 */               if ((fragment instanceof BaseCursorListFragment) && (view = fragment.getView()) != null && (list = (TopScrollable) view.findViewById(android.R.id.list)) != null && !list.scrollTop()) {
/* 425 */                   ((BaseCursorListFragment) fragment).invokeScrollFirstItem();
                        }
                    }
                }

/* 430 */       public boolean isShowing(String tag) {
                    return tag != null && tag.equals(this.mTabHost.getCurrentTabTag());
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 435 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 436 */           super.onActivityResult(requestCode, resultCode, data);
/* 437 */           Fragment fragment = this.mTabsAdapter.getCurrentFragment();
/* 438 */           if (fragment instanceof BaseRecipientPickerFragment) {
/* 439 */               fragment.onActivityResult(requestCode, resultCode, data);
                    }
                }

/* 443 */       public static Intent getIntent(Activity activity, long postId, String vidUrl, String thumbUrl) {
/* 444 */           Intent intent = new Intent(activity, (Class<?>) RecipientPickerActivity.class);
/* 445 */           intent.putExtra("post_id", postId);
/* 446 */           intent.putExtra(EXTRA_SHARED_VIDEO_URL, vidUrl);
/* 447 */           intent.putExtra(EXTRA_SHARED_THUMB_URL, thumbUrl);
/* 448 */           return intent;
                }

/* 452 */       public static Intent getIntent(Activity activity, String message, String videoPath, String thumbnailPath, String previewPath, int maxLoops) {
/* 453 */           Intent intent = new Intent(activity, (Class<?>) RecipientPickerActivity.class);
/* 454 */           intent.putExtra("message", message);
/* 455 */           intent.putExtra("video_path", videoPath);
/* 456 */           intent.putExtra("thumbnail_path", thumbnailPath);
/* 457 */           intent.putExtra("preview_path", previewPath);
/* 458 */           intent.putExtra("max_loops", maxLoops);
/* 459 */           return intent;
                }

                @Override // android.text.TextWatcher
/* 463 */       public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 464 */           Fragment currentFragment = this.mTabsAdapter.getCurrentFragment();
/* 465 */           if (currentFragment instanceof BaseRecipientPickerFragment) {
/* 466 */               ((BaseRecipientPickerFragment) currentFragment).onTextChanged(s, start, before, count);
                    }
/* 468 */           if (count > 0) {
/* 469 */               setItemForRemoval(-1);
                    }
                }

                @Override // android.text.TextWatcher
/* 474 */       public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
/* 478 */       public void afterTextChanged(Editable editable) {
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 483 */       public void onBackPressed() {
/* 484 */           if (!TextUtils.isEmpty(this.mQuery.getText())) {
/* 485 */               this.mQuery.setText("");
                    } else {
/* 487 */               super.onBackPressed();
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 492 */       protected void onDestroy() {
/* 493 */           super.onDestroy();
/* 494 */           this.mQuery.removeTextChangedListener(this);
                }

/* 497 */       public void setForceFullList(boolean forceFullList) {
/* 498 */           boolean wasForceFullList = this.isForcingFullList;
/* 499 */           if (wasForceFullList != forceFullList) {
/* 502 */               this.isForcingFullList = forceFullList;
/* 503 */               if (forceFullList) {
/* 507 */                   this.mFlowLayout.removeViewAt(3);
                            for (int i = 3; i < this.mRecipients.size(); i++) {
/* 509 */                       invalidateEditTextUI(i, this.mRecipients.get(i), true, true);
                            }
/* 522 */                   return;
                        }
/* 512 */               if (isInOverflowMode()) {
/* 513 */                   if (this.mSelectedForRemoval >= 3) {
/* 514 */                       setItemForRemoval(-1);
                            }
/* 516 */                   while (this.mFlowLayout.getChildCount() > 4) {
/* 517 */                       this.mFlowLayout.removeViewAt(3);
                            }
/* 519 */                   invalidateEditTextUI(3, this.mLastExtra, true, false);
                        }
                    }
                }

/* 527 */       public class RecipientItemClickListener implements View.OnClickListener {
                    private final int position;
                    private final VineRecipient recipient;

/* 528 */           public RecipientItemClickListener(VineRecipient recipient, int position) {
/* 530 */               this.recipient = recipient;
/* 531 */               this.position = position;
                    }

                    @Override // android.view.View.OnClickListener
/* 535 */           public void onClick(View v) {
/* 536 */               RecipientPickerActivity.this.onItemClick(this.recipient);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity
/* 541 */       public boolean isFakeActionBarEnabled() {
/* 542 */           return true;
                }

/* 545 */       private View bindOrCreateViewForRecipient(int position, View convertView, VineRecipient recipient, boolean isSelected) {
                    GradientDrawable gbg;
/* 546 */           if (convertView == null) {
/* 547 */               convertView = getLayoutInflater().inflate(C0257R.layout.recipient_picker_item, (ViewGroup) this.mFlowLayout, false);
                    }
/* 550 */           TypefacesTextView displayTextView = (TypefacesTextView) ((RelativeLayout) convertView).getChildAt(0);
/* 552 */           displayTextView.setOnClickListener(new RecipientItemClickListener(recipient, position));
/* 554 */           if (recipient.color <= 0 || recipient.color == Settings.DEFAULT_PROFILE_COLOR) {
/* 555 */               recipient.color = this.mVineGreen;
                    }
/* 558 */           Drawable bg = displayTextView.getBackground();
/* 562 */           if (!(bg instanceof GradientDrawable)) {
/* 563 */               gbg = new GradientDrawable();
/* 564 */               gbg.setShape(0);
/* 565 */               gbg.setCornerRadius(this.mDensity * 2.0f);
                    } else {
/* 567 */               gbg = (GradientDrawable) bg;
                    }
/* 570 */           if (isSelected) {
/* 571 */               gbg.setStroke((int) (this.mDensity * 2.0f), recipient.color | (-16777216));
/* 572 */               gbg.setColor(0);
/* 573 */               displayTextView.setTextColor(recipient.color | (-16777216));
                    } else {
/* 575 */               gbg.setStroke(0, 0);
/* 576 */               gbg.setColor(recipient.color | (-16777216));
/* 578 */               displayTextView.setTextColor(getResources().getColor(C0257R.color.solid_white));
                    }
/* 580 */           ViewUtil.setBackground(displayTextView, gbg);
/* 581 */           displayTextView.setText(recipient.display);
/* 583 */           return convertView;
                }

/* 586 */       public void onItemClick(VineRecipient item) {
/* 587 */           if (this.mLastExtra == item) {
/* 588 */               setForceFullList(true);
/* 597 */               return;
                    }
/* 590 */           int index = this.mRecipients.indexOf(item);
/* 591 */           if (this.mSelectedForRemoval == index) {
/* 592 */               setItemForRemoval(-1);
                    } else {
/* 594 */               setItemForRemoval(index);
                    }
                }

/* 599 */       private void setItemForRemoval(int index) {
/* 600 */           if (this.mSelectedForRemoval >= 0) {
/* 601 */               bindOrCreateViewForRecipient(this.mSelectedForRemoval, this.mFlowLayout.getChildAt(this.mSelectedForRemoval), this.mRecipients.get(this.mSelectedForRemoval), false);
                    }
/* 605 */           if (index >= 0) {
/* 606 */               bindOrCreateViewForRecipient(index, this.mFlowLayout.getChildAt(index), this.mRecipients.get(index), true);
                    }
/* 610 */           this.mSelectedForRemoval = index;
                }

/* 613 */       private void notifyItemRemoval(VineRecipient recipient) {
/* 614 */           List<Fragment> fragments = getSupportFragmentManager().getFragments();
                    for (Fragment f : fragments) {
/* 616 */               if (f instanceof BaseRecipientPickerFragment) {
/* 617 */                   ((BaseRecipientPickerFragment) f).onRecipientItemRemoved(recipient);
                        }
                    }
                }

/* 622 */       public int getCount() {
                    return this.isForcingFullList ? this.mRecipients.size() : Math.min(this.mRecipients.size(), 3);
                }

/* 630 */       public boolean isInOverflowMode() {
                    return !this.isForcingFullList && this.mRecipients.size() > getCount();
                }

                @Override // co.vine.android.widget.TypefacesEditText.KeyboardListener
/* 635 */       public void onKeyboardDismissed() {
/* 636 */           setForceFullList(false);
                }

                @Override // co.vine.android.widget.TypefacesEditText.KeyboardListener
/* 640 */       public boolean sendKeyEvent(KeyEvent event) {
/* 641 */           if (event.getAction() == 1 && event.getKeyCode() == 67) {
/* 642 */               if (this.mSelectedForRemoval < 0) {
/* 643 */                   Editable txt = this.mQuery.getText();
/* 644 */                   if (txt != null && txt.length() == 0) {
/* 645 */                       if (isInOverflowMode()) {
/* 646 */                           setForceFullList(true);
                                    return true;
                                }
/* 648 */                       if (this.mRecipients.size() <= 0) {
                                    return true;
                                }
/* 649 */                       int indexToRemove = this.mRecipients.size() - 1;
/* 650 */                       if (this.mSelectedForRemoval >= 0) {
                                    return true;
                                }
/* 651 */                       setItemForRemoval(indexToRemove);
                                return true;
                            }
                        } else {
/* 658 */                   VineRecipient toRemove = this.mRecipients.get(this.mSelectedForRemoval);
/* 659 */                   removeRecipient(toRemove);
/* 660 */                   notifyItemRemoval(toRemove);
                        }
                    }
/* 663 */           return false;
                }
            }
