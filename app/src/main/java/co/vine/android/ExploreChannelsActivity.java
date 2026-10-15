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
            
            
    public class ExploreChannelsActivity extends BaseTimelineActivity implements TabHost.OnTabChangeListener, IconTabHost.OnTabClickedListener {
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

        static  ImageKey access$000(ExploreChannelsActivity x0) {
            return x0.mIconImageKey;
                }

        static  void access$100(ExploreChannelsActivity x0, Bitmap x1) {
            x0.setIconImage(x1);
                }

        static  ImageKey access$200(ExploreChannelsActivity x0) {
            return x0.mLargeIconImageKey;
                }

        static  ImageKey access$202(ExploreChannelsActivity x0, ImageKey x1) {
            x0.mLargeIconImageKey = x1;
                    return x1;
                }

        static  void access$300(ExploreChannelsActivity x0, Bitmap x1) {
            x0.setModalIconImage(x1);
                }

        static  void access$400(ExploreChannelsActivity x0, boolean x1) {
            x0.setFollowIcon(x1);
                }

        static  boolean access$500(ExploreChannelsActivity x0) {
            return x0.mFollowing;
                }

        static  boolean access$502(ExploreChannelsActivity x0, boolean x1) {
            x0.mFollowing = x1;
                    return x1;
                }

        static  VineChannel access$602(ExploreChannelsActivity x0, VineChannel x1) {
            x0.mChannel = x1;
                    return x1;
                }

        static  String access$700(ExploreChannelsActivity x0) {
            return x0.mChannelTitle;
                }

        static  ViewGroup access$800(ExploreChannelsActivity x0) {
            return x0.mRootView;
                }

        static  TooltipOverlayView access$902(ExploreChannelsActivity x0, TooltipOverlayView x1) {
            x0.mTooltipOverlay = x1;
                    return x1;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
                    int navRGB;
                    int titleRGB;
                    TextView title;
            super.onCreate(savedInstanceState, R.layout.main, true);
                    
                    
                    
                    hideOuterSupportActionBar();
            if (BuildUtil.isExplore()) {
                setupTabs();
                    }
            this.mAppSessionListener = new ExploreChannelsAppSessionListener();
            Intent intent = getIntent();
            if (intent == null || intent.getData() == null) {
               throw new IllegalAccessError("Intent data is null. You must provide a URI in order to view a video list.");
                    }
           Uri data = intent.getData();
           String channelId = data.getPathSegments().get(0);
           String iconPath = data.getQueryParameter(PARAM_ICON_PATH);
           String navRGBParam = data.getQueryParameter(PARAM_NAV_RGB);
           if (navRGBParam != null && !navRGBParam.startsWith("#")) {
               navRGBParam = "#" + navRGBParam;
                    }
           String titleRGBParam = data.getQueryParameter(PARAM_TITLE_RGB);
           boolean showRecent = data.getBooleanQueryParameter(PARAM_SHOW_RECENT, false);
           String channel = data.getQueryParameter("name");
           if (channel != null) {
               channel = channel.replace('+', ' ');
                    }
           this.mChannelTitle = channel;
           if (TextUtils.isEmpty(navRGBParam)) {
               navRGB = getResources().getColor(R.color.vine_green);
                    } else {
                        try {
                   navRGB = Color.parseColor(navRGBParam);
                        } catch (IllegalArgumentException e) {
                   navRGB = getResources().getColor(R.color.vine_green);
                        }
                    }
           if (TextUtils.isEmpty(titleRGBParam)) {
               titleRGB = getResources().getColor(R.color.solid_white);
                    } else {
                        try {
                   titleRGB = Color.parseColor(titleRGBParam);
                        } catch (IllegalArgumentException e2) {
                   titleRGB = getResources().getColor(R.color.solid_white);
                        }
                    }
           setupActionBar((Boolean) true, (Boolean) true, channel, (Boolean) true, (Boolean) true);
           ActionBar ab = getSupportActionBar();
           if (ab != null) {
               ab.setBackgroundDrawable(new ColorDrawable(navRGB));
                    }
           int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
           if (actionBarTitleId > 0 && (title = (TextView) findViewById(actionBarTitleId)) != null) {
               title.setTextColor(titleRGB);
                    }
           this.mScrollBar = (ViewPagerScrollBar) findViewById(R.id.scrollbar);
           this.mScrollBar.setLineColor(navRGB);
           this.mScrollBar.setRange(2);
           this.mScrollBar.setVisibility(0);
           this.mViewPager = (ViewPager) findViewById(R.id.pager);
           this.mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.home_pager_margin));
           this.mViewPager.setPageMarginDrawable(R.color.bg_grouped_list);
           this.mViewPager.setOffscreenPageLimit(1);
           IconTabHost tabHost = this.mTabHost;
           this.mTabsAdapter = new TabsAdapter(this, tabHost, this.mViewPager, this.mScrollBar);
           this.mTabsAdapter.setSetActionBarColorOnPageSelectedEnabled(false);
           LayoutInflater inflater = LayoutInflater.from(this);
           tabHost.setOnTabChangedListener(this);
           tabHost.setOnTabClickedListener(this);
           Bundle popularBundle = ExploreChannelsFragment.prepareArguments(intent, true, channelId, "popular", "popular", navRGB);
           popularBundle.putInt("empty_desc", R.string.failed_to_load_posts);
           this.mTabsAdapter.addTab(tabHost.newTabSpec("popular").setIndicator(TabIndicator.newTextIndicator(inflater, R.layout.explore_tab_indicator, tabHost, R.string.tab_title_popular, true)), ExploreChannelsFragment.class, popularBundle);
           if (showRecent) {
               Bundle recentBundle = ExploreChannelsFragment.prepareArguments(intent, true, channelId, "recent", "recent", navRGB);
               recentBundle.putInt("empty_desc", R.string.failed_to_load_posts);
               this.mTabsAdapter.addTab(tabHost.newTabSpec("recent").setIndicator(TabIndicator.newTextIndicator(inflater, R.layout.explore_tab_indicator, tabHost, R.string.tab_title_recent, true)), ExploreChannelsFragment.class, recentBundle);
                    } else {
               this.mTabHost.getTabWidget().setVisibility(8);
               this.mScrollBar.setVisibility(8);
                    }
           setIconImage(iconPath);
           this.mChannelId = Long.valueOf(channelId).longValue();
           this.mRootView = (ViewGroup) findViewById(R.id.home_root);
           this.mHandler = new Handler();
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

       public boolean hasTabs() {
                    return this.mTabHost.getTabWidget().getVisibility() == 0;
                }

                @Override 
       public void onScroll(int delta) {
           if (hasTabs()) {
               ScrollAwayTabWidget tabWidget = (ScrollAwayTabWidget) this.mTabHost.getTabWidget();
               tabWidget.onScroll(delta);
               Fragment fragment = this.mTabsAdapter.getCurrentFragment();
               if (fragment instanceof BaseArrayListFragment) {
                   int navBottom = tabWidget.getNavBottom();
                   ((BaseArrayListFragment) fragment).setNavBottom(navBottom);
                   this.mScrollBar.setNavOffset(navBottom);
                        }
                    }
                }

                @Override 
       public boolean onCreateOptionsMenu(Menu menu) {
           getMenuInflater().inflate(R.menu.explore_channels, menu);
           this.mFollowMenuItem = menu.findItem(R.id.follow_channel);
           this.mShowCaptureIcon = false;
           return super.onCreateOptionsMenu(menu);
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           int id = item.getItemId();
           if (id != 2131427940) {
               return super.onOptionsItemSelected(item);
                    }
                    if (this.mChannelId <= 0) {
                        return true;
                    }
           boolean newState = !this.mFollowing;
           this.mAppController.followChannel(this.mChannelId, newState);
           setFollowIcon(newState);
           this.mFollowing = newState;
           if (this.mTooltipOverlay == null) {
                        return true;
                    }
                    this.mTooltipOverlay.dismiss();
                    return true;
                }

                @Override 
       protected BaseTimelineFragment getCurrentTimeLineFragment() {
           Fragment fragment = this.mTabsAdapter.getCurrentFragment();
           if (fragment instanceof BaseTimelineFragment) {
               return (BaseTimelineFragment) fragment;
                    }
                    return null;
                }

                @Override 
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
           BaseTimelineFragment fragment = getCurrentTimeLineFragment();
           if (fragment != null) {
               fragment.onActivityResult(requestCode, resultCode, data);
                    }
                }

                @Override 
       public void onTabChanged(String s) {
           this.mViewPager.setCurrentItem(this.mTabHost.getCurrentTab());
                }

                @Override 
       protected void onResume() {
           super.onResume();
           this.mTabsAdapter.previousTab = this.mTabHost.getCurrentTab();
                }

                @Override 
       public void onSupportContentChanged() {
           super.onSupportContentChanged();
           if (!BuildUtil.isExplore()) {
               setupTabs();
                    }
                }

       private void setupTabs() {
           this.mTabHost = (IconTabHost) findViewById(android.R.id.tabhost);
           if (this.mTabHost == null) {
               throw new RuntimeException("Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'");
                    }
           this.mTabHost.setup();
           this.mViewPager = (ViewPager) findViewById(R.id.pager);
                }

       public void resetNav() {
                    ScrollAwayTabWidget widget;
                    Fragment fragment;
           if (this.mTabHost != null && (widget = (ScrollAwayTabWidget) this.mTabHost.getTabWidget()) != null) {
               widget.resetScroll();
               widget.invalidate();
               int navBottom = widget.getNavBottom();
               if (this.mTabsAdapter != null && (fragment = this.mTabsAdapter.getCurrentFragment()) != null) {
                   ((BaseArrayListFragment) fragment).setNavBottom(navBottom);
                   this.mScrollBar.setNavOffset(navBottom);
                        }
                    }
                }

                @Override 
       public void onCurrentTabClicked() {
           scrollTop();
                }

       public void scrollTop() {
                    View view;
                    TopScrollable list;
           Fragment fragment = this.mTabsAdapter.getCurrentFragment();
           if ((fragment instanceof BaseArrayListFragment) && fragment != null && (view = fragment.getView()) != null && (list = (TopScrollable) view.findViewById(android.R.id.list)) != null && !list.scrollTop()) {
               ((BaseArrayListFragment) fragment).invokeScrollFirstItem();
                    }
                }

       public boolean isShowing(String tag) {
                    return tag != null && tag.equals(this.mTabHost.getCurrentTabTag());
                }

       private void setIconImage(String url) {
           if (!TextUtils.isEmpty(url)) {
               Uri uri = Uri.parse(url);
               String fullUrl = url;
               if (uri.getHost() == null) {
                   fullUrl = BuildUtil.getWebsiteUrl(this) + url;
                        }
               this.mIconImageKey = new ImageKey(fullUrl);
               Bitmap iconImage = this.mAppController.getPhotoBitmap(this.mIconImageKey);
               if (iconImage != null) {
                   setIconImage(iconImage);
                        }
                    }
                }

       private void setIconImage(Bitmap iconImage) {
           ActionBar ab = getSupportActionBar();
           if (ab != null) {
               int l = getResources().getDimensionPixelSize(R.dimen.action_bar_icon);
               Bitmap scaledIconImage = Bitmap.createScaledBitmap(iconImage, l, l, true);
               ab.setIcon(new BitmapDrawable(getResources(), scaledIconImage));
                    }
                }

       private void setFollowIcon(boolean following) {
           if (this.mFollowMenuItem != null) {
               this.mFollowMenuItem.setVisible(true);
               this.mFollowMenuItem.setIcon(following ? R.drawable.channel_following_ic : R.drawable.channel_follow_ic);
                    }
                }

       class ExploreChannelsAppSessionListener extends AppSessionListener {
           ExploreChannelsAppSessionListener() {
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
                        UrlImage image;
                        UrlImage image2;
               if (!images.isEmpty()) {
                   if (ExploreChannelsActivity.access$000(ExploreChannelsActivity.this) != null && (image2 = images.get(ExploreChannelsActivity.access$000(ExploreChannelsActivity.this))) != null && image2.isValid() && image2.bitmap != null) {
                       ExploreChannelsActivity.access$100(ExploreChannelsActivity.this, image2.bitmap);
                            }
                   if (ExploreChannelsActivity.access$200(ExploreChannelsActivity.this) != null && (image = images.get(ExploreChannelsActivity.access$200(ExploreChannelsActivity.this))) != null && image.isValid() && image.bitmap != null) {
                       ExploreChannelsActivity.access$300(ExploreChannelsActivity.this, image.bitmap);
                            }
                        }
                    }

                    @Override 
           public void onGetTimeLineComplete(String reqId, int statusCode, String reasonPhrase, int type, int count, boolean memory, ArrayList<VinePost> posts, String tag, int pageType, int next, int previous, long anchor, boolean userInitiated, int size, String title, UrlCachePolicy cachePolicy, Bundle bundle) {
               if (statusCode == 200 && type == 8 && bundle != null) {
                   VineChannel channel = (VineChannel) bundle.getParcelable("channels");
                   if (channel != null) {
                       ExploreChannelsActivity.access$400(ExploreChannelsActivity.this, channel.following);
                       ExploreChannelsActivity.access$502(ExploreChannelsActivity.this, channel.following);
                       ExploreChannelsActivity.access$202(ExploreChannelsActivity.this, new ImageKey(channel.exploreRetinaIconFullUrl));
                       ExploreChannelsActivity.this.mAppController.getPhotoBitmap(ExploreChannelsActivity.access$200(ExploreChannelsActivity.this));
                       if (!channel.following) {
                           ExploreChannelsActivity.this.showChannelFollowTooltip();
                                }
                            }
                   ExploreChannelsActivity.access$602(ExploreChannelsActivity.this, channel);
                        }
                    }

                    @Override 
           public void onFollowChannelComplete(String reqId, int statusCode, String reasonPhrase) {
               if (statusCode != 200) {
                   ExploreChannelsActivity.access$502(ExploreChannelsActivity.this, !ExploreChannelsActivity.access$500(ExploreChannelsActivity.this));
                   Util.showCenteredToast(ExploreChannelsActivity.this, R.string.generic_error);
                        } else if (ExploreChannelsActivity.access$500(ExploreChannelsActivity.this)) {
                   ExploreChannelsActivity.this.showChannelFollowedModal();
                        }
                    }
                }

       public void showChannelFollowedModal() {
           ModalView modal = new ModalView(this, this.mRootView, R.layout.channel_followed_modal);
           ImageView followingIcon = (ImageView) modal.findViewById(R.id.following_ic);
           TextView textView = (TextView) modal.findViewById(R.id.description);
           this.mModal = modal;
           if (this.mLargeIconImageKey != null) {
               Bitmap iconImage = this.mAppController.getPhotoBitmap(this.mLargeIconImageKey);
               if (iconImage != null) {
                   setModalIconImage(iconImage);
                        }
                        try {
                   Drawable bg = getResources().getDrawable(R.drawable.circle);
                   bg.setColorFilter(Color.parseColor(this.mChannel.backgroundColor), PorterDuff.Mode.SRC_IN);
                   followingIcon.setBackground(bg);
                        } catch (RuntimeException e) {
                        }
                    }
           if (!TextUtils.isEmpty(this.mChannelTitle)) {
               textView.setText(getString(R.string.follow_channel_confirmation, new Object[]{this.mChannelTitle}));
                    }
                }

       private void setModalIconImage(Bitmap bitmap) {
           if (this.mModal != null) {
               ImageView imageView = (ImageView) this.mModal.findViewById(2131427383);
               Drawable bg = imageView.getBackground();
               bg.setColorFilter(Color.parseColor(this.mChannel.backgroundColor), PorterDuff.Mode.SRC_IN);
               imageView.setImageBitmap(bitmap);
                    }
                }

                
       class RunnableC02041 implements Runnable {
           RunnableC02041() {
                    }

                    @Override 
           public void run() {
               Activity context = ExploreChannelsActivity.this;
               if (!OnboardHelper.getChannelFollowHintShown(context)) {
                   View anchor = context.findViewById(R.id.follow_channel);
                   if (anchor != null) {
                       String title = TextUtils.isEmpty(ExploreChannelsActivity.access$700(ExploreChannelsActivity.this)) ? "" : ExploreChannelsActivity.access$700(ExploreChannelsActivity.this);
                       TooltipOverlayView overlay = new TooltipOverlayView(context, ExploreChannelsActivity.this.getString(R.string.follow_channel_tooltip, new Object[]{title}));
                       ExploreChannelsActivity.access$800(ExploreChannelsActivity.this).addView(overlay);
                       overlay.showTooltip(anchor);
                       ExploreChannelsActivity.access$902(ExploreChannelsActivity.this, overlay);
                            }
                   OnboardHelper.setChannelFollowHintShown(context);
                        }
                    }
                }

       public void showChannelFollowTooltip() {
           this.mHandler.postDelayed(new RunnableC02041(), 800L);
                }
            }
