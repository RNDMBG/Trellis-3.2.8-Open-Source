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
            
            
    public class RecipientPickerActivity extends BaseControllerActionBarActivity implements TabHost.OnTabChangeListener, IconTabHost.OnTabClickedListener, TextWatcher, TypefacesEditText.KeyboardListener {
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

        static  int access$000(RecipientPickerActivity x0) {
            return x0.mProfileColor;
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState, C0257R.layout.recipient_picker_activity, true);
           Intent intent = getIntent();
           this.mVineGreen = getResources().getColor(C0257R.color.vine_green);
           this.mVideoPath = intent.getStringExtra("video_path");
           setupActionBar((Boolean) false, (Boolean) true, C0257R.string.send_to, (Boolean) true, (Boolean) true);
           this.mProfileColor = Util.getDefaultSharedPrefs(this).getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
           if (this.mProfileColor == Settings.DEFAULT_PROFILE_COLOR || this.mProfileColor <= 0) {
               this.mProfileColor = 16777215 & getResources().getColor(C0257R.color.vine_green);
                    }
           this.mProfileColor = (-16777216) | this.mProfileColor;
           ViewPagerScrollBar scrollBar = (ViewPagerScrollBar) findViewById(C0257R.id.scrollbar);
           scrollBar.setLineColor(this.mProfileColor);
           scrollBar.setRange(2);
           ColorStateList indicatorStateList = TabIndicator.createTextColorList(this.mProfileColor, getResources().getColor(C0257R.color.light_light_gray));
           ViewPager viewPager = (ViewPager) findViewById(C0257R.id.pager);
           viewPager.setPageMargin(getResources().getDimensionPixelSize(C0257R.dimen.home_pager_margin));
           viewPager.setPageMarginDrawable(C0257R.color.bg_grouped_list);
           viewPager.setOffscreenPageLimit(1);
           IconTabHost tabHost = this.mTabHost;
           TabsAdapter tabsAdapter = new TabsAdapter(this, tabHost, viewPager, scrollBar);
           tabsAdapter.setSetActionBarColorOnPageSelectedEnabled(false);
           LayoutInflater inflater = LayoutInflater.from(this);
           tabHost.setOnTabChangedListener(this);
           tabHost.setOnTabClickedListener(this);
           Bundle friendsBundle = new Bundle();
           friendsBundle.putString("tag", "friends");
           friendsBundle.putBoolean("refresh", true);
           friendsBundle.putInt("empty_desc", C0257R.string.no_friends_found);
           TabIndicator indicator = TabIndicator.newTextIndicator(inflater, C0257R.layout.normal_tab_indicator, tabHost, C0257R.string.tab_title_vine_friends, false);
           TextView indicatorText = indicator.getIndicatorText();
           indicatorText.setTextColor(indicatorStateList);
           indicatorText.setTypeface(Typefaces.get(this).boldContent);
           tabsAdapter.addTab(tabHost.newTabSpec("friends").setIndicator(indicator), FriendsRecipientPickerFragment.class, friendsBundle);
           Bundle contactsBundle = new Bundle();
           contactsBundle.putString("tag", TAG_ADDRESS_BOOK);
           contactsBundle.putBoolean("refresh", false);
           contactsBundle.putInt("empty_desc", C0257R.string.no_contacts_found);
           TabIndicator indicator2 = TabIndicator.newTextIndicator(inflater, C0257R.layout.normal_tab_indicator, tabHost, C0257R.string.tab_title_address_book, false);
           TextView indicatorText2 = indicator2.getIndicatorText();
           indicatorText2.setTextColor(indicatorStateList);
           indicatorText2.setTypeface(Typefaces.get(this).regularContentBold);
           tabsAdapter.addTab(tabHost.newTabSpec(TAG_ADDRESS_BOOK).setIndicator(indicator2), ContactsRecipientPickerFragment.class, contactsBundle);
           this.mCaptionMessage = getIntent().getStringExtra("message");
           this.mMaxLoops = getIntent().getIntExtra("max_loops", 0);
           this.mPostId = getIntent().getLongExtra("post_id", 0L);
           this.mSharedVideoUrl = getIntent().getStringExtra(EXTRA_SHARED_VIDEO_URL);
           this.mSharedThumbnailUrl = getIntent().getStringExtra(EXTRA_SHARED_THUMB_URL);
           ViewGroup background = (ViewGroup) findViewById(android.R.id.tabs);
           background.setBackgroundColor(getResources().getColor(C0257R.color.solid_white));
           this.mFlowLayout = (FlowLayout) findViewById(C0257R.id.picked_recipient_flow);
           TypefacesEditText query = (TypefacesEditText) inflater.inflate(C0257R.layout.recipient_picker_edit_text, (ViewGroup) this.mFlowLayout, false);
           query.setKeyboardListener(this);
           query.addTextChangedListener(this);
           query.setInputType(524288);
           query.setTypeface(Typefaces.get(this).boldContent);
           this.mFlowLayout.addView(query);
           this.mQuery = query;
           this.mQuery.setOnClickListener(new ViewOnClickListenerC02581());
           this.mTabsAdapter = tabsAdapter;
           this.mViewPager = viewPager;
           this.mDensity = getResources().getDisplayMetrics().density;
                }

                
       class ViewOnClickListenerC02581 implements View.OnClickListener {
           ViewOnClickListenerC02581() {
                    }

                    @Override 
           public void onClick(View v) {
               RecipientPickerActivity.this.setForceFullList(false);
                    }
                }

       public void showPage(String tag) {
           ArrayList<TabInfo> tabs = this.mTabsAdapter.getTabs();
                    for (int i = 0; i < tabs.size(); i++) {
               TabInfo tab = tabs.get(i);
               if (tag.equals(tab.getTag())) {
                   this.mViewPager.setCurrentItem(i, true);
                        }
                    }
                }

                @Override 
       public void onPostCreate(Bundle savedInstanceState) {
           super.onPostCreate(savedInstanceState);
           Resources res = getResources();
           FakeActionBar fakeActionBar = getFakeActionBar();
           Drawable backIcon = res.getDrawable(C0257R.drawable.ic_arrow_back_large);
           backIcon.setColorFilter(new PorterDuffColorFilter(this.mProfileColor, PorterDuff.Mode.SRC_IN));
           fakeActionBar.getBackIcon().setImageDrawable(backIcon);
           fakeActionBar.getTitleView().setTypeface(Typefaces.get(this).mediumContent);
           fakeActionBar.getTitleView().setTextSize(2, 18.0f);
           addSendIcon();
                }

       private void addSendIcon() {
           Resources res = getResources();
           FakeActionBar fakeActionBar = getFakeActionBar();
           int gray = res.getColor(C0257R.color.light_light_gray);
           ImageView sendButton = new C02592(this, gray);
           ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (((double) this.mDensity) * 36.5d), (int) (((double) this.mDensity) * 36.5d));
           sendButton.setImageResource(C0257R.drawable.ic_action_send);
           sendButton.setOnClickListener(new ViewOnClickListenerC02603());
           sendButton.setLayoutParams(params);
           sendButton.setScaleType(ImageView.ScaleType.CENTER);
           fakeActionBar.getActionBarRight().addView(sendButton);
           this.mSendButton = sendButton;
           this.mSendButton.setEnabled(isSendButtonEnabled());
                }

                
       class C02592 extends ImageView {
                    final  int val$gray;

                    
           C02592(Context x0, int i) {
                        super(x0);
                        this.val$gray = i;
                    }

                    @Override 
           public void setEnabled(boolean enabled) {
               super.setEnabled(enabled);
               setColorFilter(new PorterDuffColorFilter(enabled ? RecipientPickerActivity.access$000(RecipientPickerActivity.this) : this.val$gray, PorterDuff.Mode.SRC_IN));
                    }
                }

                
       class ViewOnClickListenerC02603 implements View.OnClickListener {
           ViewOnClickListenerC02603() {
                    }

                    @Override 
           public void onClick(View v) {
               RecipientPickerActivity.this.onSendButtonClicked();
                    }
                }

       public boolean isSendButtonEnabled() {
                    return this.mRecipients.size() > 0;
                }

       public void addRecipient(VineRecipient recipient) {
           boolean wasInOverFlowMode = isInOverflowMode();
           this.mRecipients.add(recipient);
           SLog.m449d("Recipient added: {} {} {}", recipient.key, Long.valueOf(recipient.userId), recipient.value);
           if (!TextUtils.isEmpty(this.mQuery.getHint())) {
               this.mQueryHint = this.mQuery.getHint();
               this.mQuery.setHint("");
                    }
           if (!TextUtils.isEmpty(this.mQuery.getText())) {
               this.mQuery.setText("");
                    }
           invalidateEditTextUI(this.mRecipients.size() - 1, recipient, true, wasInOverFlowMode);
           if (this.mSendButton != null) {
               this.mSendButton.setEnabled(isSendButtonEnabled());
                    }
                }

       public void removeRecipient(VineRecipient recipient) {
           boolean wasInOverFlowMode = isInOverflowMode();
           int originalIndex = this.mRecipients.indexOf(recipient);
           this.mRecipients.remove(recipient);
           SLog.m449d("Recipient removed: {} {} {}", recipient.key, Long.valueOf(recipient.userId), recipient.value);
           if (this.mRecipients.size() == 0) {
               this.mQuery.setHint(this.mQueryHint);
                    }
           invalidateEditTextUI(originalIndex, recipient, false, wasInOverFlowMode);
           this.mSelectedForRemoval = -1;
           if (this.mSendButton != null) {
               this.mSendButton.setEnabled(isSendButtonEnabled());
                    }
                }

       public boolean isRecipientSelected(VineRecipient recipient) {
           return this.mRecipients.contains(recipient);
                }

       public void onSendButtonClicked() {
           if (!this.mRecipients.isEmpty()) {
                        if (this.mPostId > 0) {
                   startService(VineUploadService.getVMSharePostIntent(this, false, -1L, new ArrayList(this.mRecipients), this.mPostId, this.mSharedVideoUrl, this.mSharedThumbnailUrl));
                        } else {
                   startService(VineUploadService.getVMPostIntent(this, this.mVideoPath, false, -1L, new ArrayList(this.mRecipients), this.mCaptionMessage, this.mMaxLoops));
                        }
               startService(VineUploadService.getShowProgressIntent(this));
                        if (this.mPostId <= 0) {
                   Intent i = new Intent(getApplicationContext(), (Class<?>) HomeTabActivity.class);
                   i.setAction(HomeTabActivity.ACTION_VM_SENT);
                   i.setFlags(avformat.AVFMT_SEEK_TO_PTS);
                   startActivity(i);
                        }
               finish();
                    }
                }

       private void invalidateDisplayForExtra(int n) {
           if (n == 1) {
               this.mLastExtra.display = getString(C0257R.string.and_x_others_single);
                    } else {
               this.mLastExtra.display = getString(C0257R.string.and_x_others_plural, new Object[]{Integer.valueOf(n)});
                    }
                }

       private void invalidateEditTextUI(int index, VineRecipient recipient, boolean isAdding, boolean wasInOverflowMode) {
                    View viewToAdd;
                    View viewToAdd2;
           SLog.m449d("Invalidate UI: adding: {}, original index: {}, wasOverflow: {}.", Boolean.valueOf(isAdding), Integer.valueOf(index), Boolean.valueOf(wasInOverflowMode));
           SLog.m447d("Before child count: {}.", Integer.valueOf(this.mFlowLayout.getChildCount()));
           if (isAdding) {
               if (isInOverflowMode()) {
                   invalidateDisplayForExtra(this.mRecipients.size() - 3);
                   this.mOverFlowView = bindOrCreateViewForRecipient(index, this.mOverFlowView, this.mLastExtra, false);
                   if (this.mFlowLayout.getChildCount() <= 4) {
                       this.mFlowLayout.addView(this.mOverFlowView, 3);
                            }
                        } else {
                   if (this.mRecycledViews.isEmpty()) {
                       viewToAdd2 = bindOrCreateViewForRecipient(index, null, recipient, false);
                            } else {
                       viewToAdd2 = bindOrCreateViewForRecipient(index, this.mRecycledViews.remove(this.mRecycledViews.size() - 1), recipient, false);
                            }
                   this.mFlowLayout.addView(viewToAdd2, this.mFlowLayout.getChildCount() - 1);
                        }
                    } else if (wasInOverflowMode) {
               if (index < 3) {
                   VineRecipient recipientToAdd = this.mRecipients.get(2);
                   this.mRecycledViews.add(this.mFlowLayout.getChildAt(index));
                   this.mFlowLayout.removeViewAt(index);
                   if (this.mRecycledViews.isEmpty()) {
                       viewToAdd = bindOrCreateViewForRecipient(index, null, recipientToAdd, false);
                            } else {
                       viewToAdd = bindOrCreateViewForRecipient(index, this.mRecycledViews.remove(this.mRecycledViews.size() - 1), recipientToAdd, false);
                            }
                   this.mFlowLayout.addView(viewToAdd, 2);
                   if (isInOverflowMode()) {
                       invalidateDisplayForExtra(this.mRecipients.size() - 3);
                       bindOrCreateViewForRecipient(index, this.mOverFlowView, this.mLastExtra, false);
                            }
                        } else if (isInOverflowMode() && index != 3) {
                   invalidateDisplayForExtra(this.mRecipients.size() - 3);
                   bindOrCreateViewForRecipient(index, this.mOverFlowView, this.mLastExtra, false);
                        }
               if (!isInOverflowMode()) {
                   this.mFlowLayout.removeView(this.mOverFlowView);
                        }
                    } else {
               this.mRecycledViews.add(this.mFlowLayout.getChildAt(index));
               this.mFlowLayout.removeViewAt(index);
                    }
           SLog.m447d("After child count: {}.", Integer.valueOf(this.mFlowLayout.getChildCount()));
                }

                @Override 
       protected void onResume() {
           super.onResume();
           this.mTabsAdapter.previousTab = this.mTabHost.getCurrentTab();
                }

                @Override 
       public void onTabChanged(String s) {
           this.mViewPager.setCurrentItem(this.mTabHost.getCurrentTab());
                }

                @Override 
       public void onSupportContentChanged() {
           super.onSupportContentChanged();
           this.mTabHost = (IconTabHost) findViewById(android.R.id.tabhost);
           if (this.mTabHost == null) {
               throw new RuntimeException("Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'");
                    }
           this.mTabHost.setup();
           this.mViewPager = (ViewPager) findViewById(C0257R.id.pager);
                }

                @Override 
       public void onCurrentTabClicked() {
           scrollTop();
                }

       public void scrollTop() {
                    View view;
                    TopScrollable list;
           if (this.mTabsAdapter != null && this.mViewPager != null) {
               Fragment fragment = this.mTabsAdapter.getCurrentFragment();
               if ((fragment instanceof BaseCursorListFragment) && (view = fragment.getView()) != null && (list = (TopScrollable) view.findViewById(android.R.id.list)) != null && !list.scrollTop()) {
                   ((BaseCursorListFragment) fragment).invokeScrollFirstItem();
                        }
                    }
                }

       public boolean isShowing(String tag) {
                    return tag != null && tag.equals(this.mTabHost.getCurrentTabTag());
                }

                @Override 
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
           Fragment fragment = this.mTabsAdapter.getCurrentFragment();
           if (fragment instanceof BaseRecipientPickerFragment) {
               fragment.onActivityResult(requestCode, resultCode, data);
                    }
                }

       public static Intent getIntent(Activity activity, long postId, String vidUrl, String thumbUrl) {
           Intent intent = new Intent(activity, (Class<?>) RecipientPickerActivity.class);
           intent.putExtra("post_id", postId);
           intent.putExtra(EXTRA_SHARED_VIDEO_URL, vidUrl);
           intent.putExtra(EXTRA_SHARED_THUMB_URL, thumbUrl);
           return intent;
                }

       public static Intent getIntent(Activity activity, String message, String videoPath, String thumbnailPath, String previewPath, int maxLoops) {
           Intent intent = new Intent(activity, (Class<?>) RecipientPickerActivity.class);
           intent.putExtra("message", message);
           intent.putExtra("video_path", videoPath);
           intent.putExtra("thumbnail_path", thumbnailPath);
           intent.putExtra("preview_path", previewPath);
           intent.putExtra("max_loops", maxLoops);
           return intent;
                }

                @Override 
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           Fragment currentFragment = this.mTabsAdapter.getCurrentFragment();
           if (currentFragment instanceof BaseRecipientPickerFragment) {
               ((BaseRecipientPickerFragment) currentFragment).onTextChanged(s, start, before, count);
                    }
           if (count > 0) {
               setItemForRemoval(-1);
                    }
                }

                @Override 
       public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override 
       public void afterTextChanged(Editable editable) {
                }

                @Override 
       public void onBackPressed() {
           if (!TextUtils.isEmpty(this.mQuery.getText())) {
               this.mQuery.setText("");
                    } else {
               super.onBackPressed();
                    }
                }

                @Override 
       protected void onDestroy() {
           super.onDestroy();
           this.mQuery.removeTextChangedListener(this);
                }

       public void setForceFullList(boolean forceFullList) {
           boolean wasForceFullList = this.isForcingFullList;
           if (wasForceFullList != forceFullList) {
               this.isForcingFullList = forceFullList;
               if (forceFullList) {
                   this.mFlowLayout.removeViewAt(3);
                            for (int i = 3; i < this.mRecipients.size(); i++) {
                       invalidateEditTextUI(i, this.mRecipients.get(i), true, true);
                            }
                   return;
                        }
               if (isInOverflowMode()) {
                   if (this.mSelectedForRemoval >= 3) {
                       setItemForRemoval(-1);
                            }
                   while (this.mFlowLayout.getChildCount() > 4) {
                       this.mFlowLayout.removeViewAt(3);
                            }
                   invalidateEditTextUI(3, this.mLastExtra, true, false);
                        }
                    }
                }

       public class RecipientItemClickListener implements View.OnClickListener {
                    private final int position;
                    private final VineRecipient recipient;

           public RecipientItemClickListener(VineRecipient recipient, int position) {
               this.recipient = recipient;
               this.position = position;
                    }

                    @Override 
           public void onClick(View v) {
               RecipientPickerActivity.this.onItemClick(this.recipient);
                    }
                }

                @Override 
       public boolean isFakeActionBarEnabled() {
           return true;
                }

       private View bindOrCreateViewForRecipient(int position, View convertView, VineRecipient recipient, boolean isSelected) {
                    GradientDrawable gbg;
           if (convertView == null) {
               convertView = getLayoutInflater().inflate(C0257R.layout.recipient_picker_item, (ViewGroup) this.mFlowLayout, false);
                    }
           TypefacesTextView displayTextView = (TypefacesTextView) ((RelativeLayout) convertView).getChildAt(0);
           displayTextView.setOnClickListener(new RecipientItemClickListener(recipient, position));
           if (recipient.color <= 0 || recipient.color == Settings.DEFAULT_PROFILE_COLOR) {
               recipient.color = this.mVineGreen;
                    }
           Drawable bg = displayTextView.getBackground();
           if (!(bg instanceof GradientDrawable)) {
               gbg = new GradientDrawable();
               gbg.setShape(0);
               gbg.setCornerRadius(this.mDensity * 2.0f);
                    } else {
               gbg = (GradientDrawable) bg;
                    }
           if (isSelected) {
               gbg.setStroke((int) (this.mDensity * 2.0f), recipient.color | (-16777216));
               gbg.setColor(0);
               displayTextView.setTextColor(recipient.color | (-16777216));
                    } else {
               gbg.setStroke(0, 0);
               gbg.setColor(recipient.color | (-16777216));
               displayTextView.setTextColor(getResources().getColor(C0257R.color.solid_white));
                    }
           ViewUtil.setBackground(displayTextView, gbg);
           displayTextView.setText(recipient.display);
           return convertView;
                }

       public void onItemClick(VineRecipient item) {
           if (this.mLastExtra == item) {
               setForceFullList(true);
               return;
                    }
           int index = this.mRecipients.indexOf(item);
           if (this.mSelectedForRemoval == index) {
               setItemForRemoval(-1);
                    } else {
               setItemForRemoval(index);
                    }
                }

       private void setItemForRemoval(int index) {
           if (this.mSelectedForRemoval >= 0) {
               bindOrCreateViewForRecipient(this.mSelectedForRemoval, this.mFlowLayout.getChildAt(this.mSelectedForRemoval), this.mRecipients.get(this.mSelectedForRemoval), false);
                    }
           if (index >= 0) {
               bindOrCreateViewForRecipient(index, this.mFlowLayout.getChildAt(index), this.mRecipients.get(index), true);
                    }
           this.mSelectedForRemoval = index;
                }

       private void notifyItemRemoval(VineRecipient recipient) {
           List<Fragment> fragments = getSupportFragmentManager().getFragments();
                    for (Fragment f : fragments) {
               if (f instanceof BaseRecipientPickerFragment) {
                   ((BaseRecipientPickerFragment) f).onRecipientItemRemoved(recipient);
                        }
                    }
                }

       public int getCount() {
                    return this.isForcingFullList ? this.mRecipients.size() : Math.min(this.mRecipients.size(), 3);
                }

       public boolean isInOverflowMode() {
                    return !this.isForcingFullList && this.mRecipients.size() > getCount();
                }

                @Override 
       public void onKeyboardDismissed() {
           setForceFullList(false);
                }

                @Override 
       public boolean sendKeyEvent(KeyEvent event) {
           if (event.getAction() == 1 && event.getKeyCode() == 67) {
               if (this.mSelectedForRemoval < 0) {
                   Editable txt = this.mQuery.getText();
                   if (txt != null && txt.length() == 0) {
                       if (isInOverflowMode()) {
                           setForceFullList(true);
                                    return true;
                                }
                       if (this.mRecipients.size() <= 0) {
                                    return true;
                                }
                       int indexToRemove = this.mRecipients.size() - 1;
                       if (this.mSelectedForRemoval >= 0) {
                                    return true;
                                }
                       setItemForRemoval(indexToRemove);
                                return true;
                            }
                        } else {
                   VineRecipient toRemove = this.mRecipients.get(this.mSelectedForRemoval);
                   removeRecipient(toRemove);
                   notifyItemRemoval(toRemove);
                        }
                    }
           return false;
                }
            }
