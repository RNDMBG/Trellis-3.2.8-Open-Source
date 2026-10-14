            package co.vine.android;

            import android.app.Activity;
            import android.content.Intent;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.Color;
            import android.graphics.PorterDuff;
            import android.graphics.drawable.BitmapDrawable;
            import android.graphics.drawable.ColorDrawable;
            import android.graphics.drawable.Drawable;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.Handler;
            import android.support.v4.app.Fragment;
            import android.support.v4.view.ViewPager;
            import android.support.v7.app.ActionBar;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.TabHost;
            import android.widget.TextView;
            import co.vine.android.api.VineChannel;
            import co.vine.android.api.VinePost;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.network.UrlCachePolicy;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.Util;
            import co.vine.android.widget.ModalView;
            import co.vine.android.widget.OnboardHelper;
            import co.vine.android.widget.TooltipOverlayView;
            import co.vine.android.widget.tabs.IconTabHost;
            import co.vine.android.widget.tabs.ScrollAwayTabWidget;
            import co.vine.android.widget.tabs.TabIndicator;
            import co.vine.android.widget.tabs.TabsAdapter;
            import co.vine.android.widget.tabs.ViewPagerScrollBar;
            import com.twitter.android.widget.TopScrollable;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public class ExploreChannelsActivity extends BaseTimelineActivity implements TabHost.OnTabChangeListener, IconTabHost.OnTabClickedListener {
                public static final String PARAM_ICON_PATH = "icon";
                public static final String PARAM_NAME = "name";
                public static final String PARAM_NAV_RGB = "navRGB";
                public static final String PARAM_SHOW_RECENT = "showRecent";
                public static final String PARAM_TITLE_RGB = "titleRGB";
                public static final int PATH_SEGMENT_CHANNEL_ID = 0;
                public static final String SORT_POPULAR = "popular";
                public static final String SORT_RECENT = "recent";
                public static final String TAG_POPULAR = "popular";
                public static final String TAG_RECENT = "recent";
                private VineChannel mChannel;
                private long mChannelId;
                private String mChannelTitle;
                private MenuItem mFollowMenuItem;
                private boolean mFollowing;
                private Handler mHandler;
                private ImageKey mIconImageKey;
                private ImageKey mLargeIconImageKey;
                private ModalView mModal;
                private ViewGroup mRootView;
                private ViewPagerScrollBar mScrollBar;
                private IconTabHost mTabHost;
                private TabsAdapter mTabsAdapter;
                private TooltipOverlayView mTooltipOverlay;
                private ViewPager mViewPager;

/* 54 */        static /* synthetic */ ImageKey access$000(ExploreChannelsActivity x0) {
/* 55 */            return x0.mIconImageKey;
                }

/* 54 */        static /* synthetic */ void access$100(ExploreChannelsActivity x0, Bitmap x1) {
/* 55 */            x0.setIconImage(x1);
                }

/* 54 */        static /* synthetic */ ImageKey access$200(ExploreChannelsActivity x0) {
/* 55 */            return x0.mLargeIconImageKey;
                }

/* 54 */        static /* synthetic */ ImageKey access$202(ExploreChannelsActivity x0, ImageKey x1) {
/* 55 */            x0.mLargeIconImageKey = x1;
                    return x1;
                }

/* 54 */        static /* synthetic */ void access$300(ExploreChannelsActivity x0, Bitmap x1) {
/* 55 */            x0.setModalIconImage(x1);
                }

/* 54 */        static /* synthetic */ void access$400(ExploreChannelsActivity x0, boolean x1) {
/* 55 */            x0.setFollowIcon(x1);
                }

/* 54 */        static /* synthetic */ boolean access$500(ExploreChannelsActivity x0) {
/* 55 */            return x0.mFollowing;
                }

/* 54 */        static /* synthetic */ boolean access$502(ExploreChannelsActivity x0, boolean x1) {
/* 55 */            x0.mFollowing = x1;
                    return x1;
                }

/* 54 */        static /* synthetic */ VineChannel access$602(ExploreChannelsActivity x0, VineChannel x1) {
/* 55 */            x0.mChannel = x1;
                    return x1;
                }

/* 54 */        static /* synthetic */ String access$700(ExploreChannelsActivity x0) {
/* 55 */            return x0.mChannelTitle;
                }

/* 54 */        static /* synthetic */ ViewGroup access$800(ExploreChannelsActivity x0) {
/* 55 */            return x0.mRootView;
                }

/* 54 */        static /* synthetic */ TooltipOverlayView access$902(ExploreChannelsActivity x0, TooltipOverlayView x1) {
/* 55 */            x0.mTooltipOverlay = x1;
                    return x1;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 89 */        public void onCreate(Bundle savedInstanceState) {
                    int navRGB;
                    int titleRGB;
                    TextView title;
/* 90 */            super.onCreate(savedInstanceState, C0257R.layout.main, true);
                    // main.xml provides the channel-specific toolbar (colour, back arrow and title).
                    // The support action bar is also installed by the activity base class, which
                    // otherwise leaves two stacked toolbars on channel screens.
                    hideOuterSupportActionBar();
/* 91 */            if (BuildUtil.isExplore()) {
/* 92 */                setupTabs();
                    }
/* 95 */            this.mAppSessionListener = new ExploreChannelsAppSessionListener();
/* 98 */            Intent intent = getIntent();
/* 99 */            if (intent == null || intent.getData() == null) {
/* 100 */               throw new IllegalAccessError("Intent data is null. You must provide a URI in order to view a video list.");
                    }
/* 102 */           Uri data = intent.getData();
/* 103 */           String channelId = data.getPathSegments().get(0);
/* 104 */           String iconPath = data.getQueryParameter(PARAM_ICON_PATH);
/* 105 */           String navRGBParam = data.getQueryParameter(PARAM_NAV_RGB);
/* 106 */           if (navRGBParam != null && !navRGBParam.startsWith("#")) {
/* 107 */               navRGBParam = "#" + navRGBParam;
                    }
/* 109 */           String titleRGBParam = data.getQueryParameter(PARAM_TITLE_RGB);
/* 110 */           boolean showRecent = data.getBooleanQueryParameter(PARAM_SHOW_RECENT, false);
/* 112 */           String channel = data.getQueryParameter("name");
/* 113 */           if (channel != null) {
/* 114 */               channel = channel.replace('+', ' ');
                    }
/* 116 */           this.mChannelTitle = channel;
/* 120 */           if (TextUtils.isEmpty(navRGBParam)) {
/* 121 */               navRGB = getResources().getColor(C0257R.color.vine_green);
                    } else {
                        try {
/* 124 */                   navRGB = Color.parseColor(navRGBParam);
                        } catch (IllegalArgumentException e) {
/* 126 */                   navRGB = getResources().getColor(C0257R.color.vine_green);
                        }
                    }
/* 130 */           if (TextUtils.isEmpty(titleRGBParam)) {
/* 131 */               titleRGB = getResources().getColor(C0257R.color.solid_white);
                    } else {
                        try {
/* 134 */                   titleRGB = Color.parseColor(titleRGBParam);
                        } catch (IllegalArgumentException e2) {
/* 136 */                   titleRGB = getResources().getColor(C0257R.color.solid_white);
                        }
                    }
/* 140 */           setupActionBar((Boolean) true, (Boolean) true, channel, (Boolean) true, (Boolean) true);
/* 142 */           ActionBar ab = getSupportActionBar();
/* 143 */           if (ab != null) {
/* 144 */               ab.setBackgroundDrawable(new ColorDrawable(navRGB));
                    }
/* 149 */           int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
/* 150 */           if (actionBarTitleId > 0 && (title = (TextView) findViewById(actionBarTitleId)) != null) {
/* 153 */               title.setTextColor(titleRGB);
                    }
/* 158 */           this.mScrollBar = (ViewPagerScrollBar) findViewById(C0257R.id.scrollbar);
/* 159 */           this.mScrollBar.setLineColor(navRGB);
/* 160 */           this.mScrollBar.setRange(2);
/* 161 */           this.mScrollBar.setVisibility(0);
/* 163 */           this.mViewPager = (ViewPager) findViewById(C0257R.id.pager);
/* 164 */           this.mViewPager.setPageMargin(getResources().getDimensionPixelSize(C0257R.dimen.home_pager_margin));
/* 165 */           this.mViewPager.setPageMarginDrawable(C0257R.color.bg_grouped_list);
/* 166 */           this.mViewPager.setOffscreenPageLimit(1);
/* 168 */           IconTabHost tabHost = this.mTabHost;
/* 169 */           this.mTabsAdapter = new TabsAdapter(this, tabHost, this.mViewPager, this.mScrollBar);
/* 170 */           this.mTabsAdapter.setSetActionBarColorOnPageSelectedEnabled(false);
/* 171 */           LayoutInflater inflater = LayoutInflater.from(this);
/* 172 */           tabHost.setOnTabChangedListener(this);
/* 173 */           tabHost.setOnTabClickedListener(this);
/* 176 */           Bundle popularBundle = ExploreChannelsFragment.prepareArguments(intent, true, channelId, "popular", "popular", navRGB);
/* 178 */           popularBundle.putInt("empty_desc", C0257R.string.failed_to_load_posts);
/* 180 */           this.mTabsAdapter.addTab(tabHost.newTabSpec("popular").setIndicator(TabIndicator.newTextIndicator(inflater, C0257R.layout.explore_tab_indicator, tabHost, C0257R.string.tab_title_popular, true)), ExploreChannelsFragment.class, popularBundle);
/* 187 */           if (showRecent) {
/* 188 */               Bundle recentBundle = ExploreChannelsFragment.prepareArguments(intent, true, channelId, "recent", "recent", navRGB);
/* 190 */               recentBundle.putInt("empty_desc", C0257R.string.failed_to_load_posts);
/* 192 */               this.mTabsAdapter.addTab(tabHost.newTabSpec("recent").setIndicator(TabIndicator.newTextIndicator(inflater, C0257R.layout.explore_tab_indicator, tabHost, C0257R.string.tab_title_recent, true)), ExploreChannelsFragment.class, recentBundle);
                    } else {
/* 199 */               this.mTabHost.getTabWidget().setVisibility(8);
/* 200 */               this.mScrollBar.setVisibility(8);
                    }
/* 203 */           setIconImage(iconPath);
/* 204 */           this.mChannelId = Long.valueOf(channelId).longValue();
/* 205 */           this.mRootView = (ViewGroup) findViewById(C0257R.id.home_root);
/* 206 */           this.mHandler = new Handler();
                }

                private void hideOuterSupportActionBar() {
                    int id = getResources().getIdentifier("action_bar_container", "id", "android");
                    if (id != 0) {
                        View outer = super.findViewById(id);
                        if (outer != null) {
                            outer.setVisibility(View.GONE);
                        }
                    }
                }

/* 209 */       public boolean hasTabs() {
                    return this.mTabHost.getTabWidget().getVisibility() == 0;
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.ScrollListener
/* 213 */       public void onScroll(int delta) {
/* 214 */           if (hasTabs()) {
/* 215 */               ScrollAwayTabWidget tabWidget = (ScrollAwayTabWidget) this.mTabHost.getTabWidget();
/* 216 */               tabWidget.onScroll(delta);
/* 217 */               Fragment fragment = this.mTabsAdapter.getCurrentFragment();
/* 218 */               if (fragment instanceof BaseArrayListFragment) {
/* 219 */                   int navBottom = tabWidget.getNavBottom();
/* 220 */                   ((BaseArrayListFragment) fragment).setNavBottom(navBottom);
/* 221 */                   this.mScrollBar.setNavOffset(navBottom);
                        }
                    }
                }

                @Override // co.vine.android.BaseTimelineActivity, co.vine.android.BaseActionBarActivity, android.app.Activity
/* 227 */       public boolean onCreateOptionsMenu(Menu menu) {
/* 228 */           getMenuInflater().inflate(C0257R.menu.explore_channels, menu);
/* 229 */           this.mFollowMenuItem = menu.findItem(C0257R.id.follow_channel);
/* 230 */           this.mShowCaptureIcon = false;
/* 231 */           return super.onCreateOptionsMenu(menu);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 235 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 236 */           int id = item.getItemId();
/* 237 */           if (id != 2131427940) {
/* 246 */               return super.onOptionsItemSelected(item);
                    }
                    if (this.mChannelId <= 0) {
                        return true;
                    }
/* 239 */           boolean newState = !this.mFollowing;
/* 240 */           this.mAppController.followChannel(this.mChannelId, newState);
/* 241 */           setFollowIcon(newState);
/* 242 */           this.mFollowing = newState;
/* 243 */           if (this.mTooltipOverlay == null) {
                        return true;
                    }
                    this.mTooltipOverlay.dismiss();
                    return true;
                }

                @Override // co.vine.android.BaseTimelineActivity
/* 252 */       protected BaseTimelineFragment getCurrentTimeLineFragment() {
/* 253 */           Fragment fragment = this.mTabsAdapter.getCurrentFragment();
/* 254 */           if (fragment instanceof BaseTimelineFragment) {
/* 255 */               return (BaseTimelineFragment) fragment;
                    }
                    return null;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 261 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 262 */           super.onActivityResult(requestCode, resultCode, data);
/* 263 */           BaseTimelineFragment fragment = getCurrentTimeLineFragment();
/* 264 */           if (fragment != null) {
/* 265 */               fragment.onActivityResult(requestCode, resultCode, data);
                    }
                }

                @Override // android.widget.TabHost.OnTabChangeListener
/* 270 */       public void onTabChanged(String s) {
/* 271 */           this.mViewPager.setCurrentItem(this.mTabHost.getCurrentTab());
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 275 */       protected void onResume() {
/* 276 */           super.onResume();
/* 277 */           this.mTabsAdapter.previousTab = this.mTabHost.getCurrentTab();
                }

                @Override // android.support.v7.app.ActionBarActivity
/* 281 */       public void onSupportContentChanged() {
/* 282 */           super.onSupportContentChanged();
/* 283 */           if (!BuildUtil.isExplore()) {
/* 284 */               setupTabs();
                    }
                }

/* 288 */       private void setupTabs() {
/* 289 */           this.mTabHost = (IconTabHost) findViewById(android.R.id.tabhost);
/* 290 */           if (this.mTabHost == null) {
/* 291 */               throw new RuntimeException("Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'");
                    }
/* 296 */           this.mTabHost.setup();
/* 297 */           this.mViewPager = (ViewPager) findViewById(C0257R.id.pager);
                }

/* 300 */       public void resetNav() {
                    ScrollAwayTabWidget widget;
                    Fragment fragment;
/* 301 */           if (this.mTabHost != null && (widget = (ScrollAwayTabWidget) this.mTabHost.getTabWidget()) != null) {
/* 304 */               widget.resetScroll();
/* 305 */               widget.invalidate();
/* 307 */               int navBottom = widget.getNavBottom();
/* 308 */               if (this.mTabsAdapter != null && (fragment = this.mTabsAdapter.getCurrentFragment()) != null) {
/* 311 */                   ((BaseArrayListFragment) fragment).setNavBottom(navBottom);
/* 312 */                   this.mScrollBar.setNavOffset(navBottom);
                        }
                    }
                }

                @Override // co.vine.android.widget.tabs.IconTabHost.OnTabClickedListener
/* 320 */       public void onCurrentTabClicked() {
/* 321 */           scrollTop();
                }

/* 324 */       public void scrollTop() {
                    View view;
                    TopScrollable list;
/* 325 */           Fragment fragment = this.mTabsAdapter.getCurrentFragment();
/* 327 */           if ((fragment instanceof BaseArrayListFragment) && fragment != null && (view = fragment.getView()) != null && (list = (TopScrollable) view.findViewById(android.R.id.list)) != null && !list.scrollTop()) {
/* 342 */               ((BaseArrayListFragment) fragment).invokeScrollFirstItem();
                    }
                }

/* 346 */       public boolean isShowing(String tag) {
                    return tag != null && tag.equals(this.mTabHost.getCurrentTabTag());
                }

/* 350 */       private void setIconImage(String url) {
/* 351 */           if (!TextUtils.isEmpty(url)) {
/* 352 */               Uri uri = Uri.parse(url);
/* 353 */               String fullUrl = url;
/* 354 */               if (uri.getHost() == null) {
/* 355 */                   fullUrl = BuildUtil.getWebsiteUrl(this) + url;
                        }
/* 357 */               this.mIconImageKey = new ImageKey(fullUrl);
/* 358 */               Bitmap iconImage = this.mAppController.getPhotoBitmap(this.mIconImageKey);
/* 359 */               if (iconImage != null) {
/* 360 */                   setIconImage(iconImage);
                        }
                    }
                }

/* 365 */       private void setIconImage(Bitmap iconImage) {
/* 366 */           ActionBar ab = getSupportActionBar();
/* 367 */           if (ab != null) {
/* 368 */               int l = getResources().getDimensionPixelSize(C0257R.dimen.action_bar_icon);
/* 369 */               Bitmap scaledIconImage = Bitmap.createScaledBitmap(iconImage, l, l, true);
/* 370 */               ab.setIcon(new BitmapDrawable(getResources(), scaledIconImage));
                    }
                }

/* 374 */       private void setFollowIcon(boolean following) {
/* 375 */           if (this.mFollowMenuItem != null) {
/* 376 */               this.mFollowMenuItem.setVisible(true);
/* 377 */               this.mFollowMenuItem.setIcon(following ? C0257R.drawable.channel_following_ic : C0257R.drawable.channel_follow_ic);
                    }
                }

/* 379 */       class ExploreChannelsAppSessionListener extends AppSessionListener {
/* 380 */           ExploreChannelsAppSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 383 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
                        UrlImage image;
                        UrlImage image2;
/* 384 */               if (!images.isEmpty()) {
/* 385 */                   if (ExploreChannelsActivity.access$000(ExploreChannelsActivity.this) != null && (image2 = images.get(ExploreChannelsActivity.access$000(ExploreChannelsActivity.this))) != null && image2.isValid() && image2.bitmap != null) {
/* 388 */                       ExploreChannelsActivity.access$100(ExploreChannelsActivity.this, image2.bitmap);
                            }
/* 391 */                   if (ExploreChannelsActivity.access$200(ExploreChannelsActivity.this) != null && (image = images.get(ExploreChannelsActivity.access$200(ExploreChannelsActivity.this))) != null && image.isValid() && image.bitmap != null) {
/* 394 */                       ExploreChannelsActivity.access$300(ExploreChannelsActivity.this, image.bitmap);
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 404 */           public void onGetTimeLineComplete(String reqId, int statusCode, String reasonPhrase, int type, int count, boolean memory, ArrayList<VinePost> posts, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated, int size, String title, UrlCachePolicy cachePolicy, Bundle bundle) {
/* 405 */               if (statusCode == 200 && type == 8 && bundle != null) {
/* 406 */                   VineChannel channel = (VineChannel) bundle.getParcelable("channels");
/* 407 */                   if (channel != null) {
/* 408 */                       ExploreChannelsActivity.access$400(ExploreChannelsActivity.this, channel.following);
/* 409 */                       ExploreChannelsActivity.access$502(ExploreChannelsActivity.this, channel.following);
/* 412 */                       ExploreChannelsActivity.access$202(ExploreChannelsActivity.this, new ImageKey(channel.exploreRetinaIconFullUrl));
/* 413 */                       ExploreChannelsActivity.this.mAppController.getPhotoBitmap(ExploreChannelsActivity.access$200(ExploreChannelsActivity.this));
/* 415 */                       if (!channel.following) {
/* 416 */                           ExploreChannelsActivity.this.showChannelFollowTooltip();
                                }
                            }
/* 420 */                   ExploreChannelsActivity.access$602(ExploreChannelsActivity.this, channel);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 425 */           public void onFollowChannelComplete(String reqId, int statusCode, String reasonPhrase) {
/* 426 */               if (statusCode != 200) {
/* 427 */                   ExploreChannelsActivity.access$502(ExploreChannelsActivity.this, !ExploreChannelsActivity.access$500(ExploreChannelsActivity.this));
/* 428 */                   Util.showCenteredToast(ExploreChannelsActivity.this, C0257R.string.generic_error);
                        } else if (ExploreChannelsActivity.access$500(ExploreChannelsActivity.this)) {
/* 430 */                   ExploreChannelsActivity.this.showChannelFollowedModal();
                        }
                    }
                }

/* 435 */       public void showChannelFollowedModal() {
/* 436 */           ModalView modal = new ModalView(this, this.mRootView, C0257R.layout.channel_followed_modal);
/* 437 */           ImageView followingIcon = (ImageView) modal.findViewById(C0257R.id.following_ic);
/* 438 */           TextView textView = (TextView) modal.findViewById(C0257R.id.description);
/* 439 */           this.mModal = modal;
/* 441 */           if (this.mLargeIconImageKey != null) {
/* 442 */               Bitmap iconImage = this.mAppController.getPhotoBitmap(this.mLargeIconImageKey);
/* 443 */               if (iconImage != null) {
/* 444 */                   setModalIconImage(iconImage);
                        }
                        try {
/* 449 */                   Drawable bg = getResources().getDrawable(C0257R.drawable.circle);
/* 450 */                   bg.setColorFilter(Color.parseColor(this.mChannel.backgroundColor), PorterDuff.Mode.SRC_IN);
/* 451 */                   followingIcon.setBackground(bg);
                        } catch (RuntimeException e) {
                        }
                    }
/* 457 */           if (!TextUtils.isEmpty(this.mChannelTitle)) {
/* 458 */               textView.setText(getString(C0257R.string.follow_channel_confirmation, new Object[]{this.mChannelTitle}));
                    }
                }

/* 462 */       private void setModalIconImage(Bitmap bitmap) {
/* 463 */           if (this.mModal != null) {
/* 464 */               ImageView imageView = (ImageView) this.mModal.findViewById(2131427383);
/* 465 */               Drawable bg = imageView.getBackground();
/* 466 */               bg.setColorFilter(Color.parseColor(this.mChannel.backgroundColor), PorterDuff.Mode.SRC_IN);
/* 467 */               imageView.setImageBitmap(bitmap);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ExploreChannelsActivity$1 */
/* 470 */       class RunnableC02041 implements Runnable {
/* 471 */           RunnableC02041() {
                    }

                    @Override // java.lang.Runnable
/* 474 */           public void run() {
/* 475 */               Activity context = ExploreChannelsActivity.this;
/* 476 */               if (!OnboardHelper.getChannelFollowHintShown(context)) {
/* 479 */                   View anchor = context.findViewById(C0257R.id.follow_channel);
/* 480 */                   if (anchor != null) {
/* 481 */                       String title = TextUtils.isEmpty(ExploreChannelsActivity.access$700(ExploreChannelsActivity.this)) ? "" : ExploreChannelsActivity.access$700(ExploreChannelsActivity.this);
/* 482 */                       TooltipOverlayView overlay = new TooltipOverlayView(context, ExploreChannelsActivity.this.getString(C0257R.string.follow_channel_tooltip, new Object[]{title}));
/* 484 */                       ExploreChannelsActivity.access$800(ExploreChannelsActivity.this).addView(overlay);
/* 485 */                       overlay.showTooltip(anchor);
/* 486 */                       ExploreChannelsActivity.access$902(ExploreChannelsActivity.this, overlay);
                            }
/* 488 */                   OnboardHelper.setChannelFollowHintShown(context);
                        }
                    }
                }

/* 471 */       public void showChannelFollowTooltip() {
/* 472 */           this.mHandler.postDelayed(new RunnableC02041(), 800L);
                }
            }
