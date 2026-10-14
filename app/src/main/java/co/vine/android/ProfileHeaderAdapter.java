            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.support.v4.view.PagerAdapter;
            import android.support.v4.view.ViewPager;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.Button;
            import android.widget.ImageView;
            import android.widget.ProgressBar;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.DotIndicators;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.TypefacesSpan;
            import co.vine.android.widget.TypefacesTextView;
            import java.util.HashMap;
            
            
    public class ProfileHeaderAdapter extends BaseAdapter implements ViewPager.OnPageChangeListener {
                private static final int FOLLOWING = 2;
                private static final int FOLLOW_REQUESTED = 3;
                private static final int NOT_FOLLOWING = 1;
                private static final int SPAN_FLAGS = 33;
                public static final int TAB_LIKES = 1;
                public static final int TAB_POST = 0;
                private AppController mAppController;
                private boolean mBlocked;
                private final TypefacesSpan mBold;
                private Context mContext;
                private int mCurrentTab;
                private int mDetailPageHeight;
                private String mFollowEventSource;
                private int mFollowState = 0;
                private final ProfileFragment mFragment;
                private ProfileViewHolder mHolder;
                private PagerDetailsViewHolder mHolderPagerDetails;
                private PagerMainViewHolder mHolderPagerMain;
                private View.OnClickListener mListener;
                private boolean mLocked;
                private int mMainPageHeight;
                private final int mNumberUnSelectedColor;
                private final TypefacesSpan mRegular;
                private boolean mShowActionsContainer;
                private int mTabSelectedColor;
                private VineUser mUser;

        static  PagerMainViewHolder access$102(ProfileHeaderAdapter x0, PagerMainViewHolder x1) {
            x0.mHolderPagerMain = x1;
                    return x1;
                }

        static  VineUser access$200(ProfileHeaderAdapter x0) {
            return x0.mUser;
                }

        static  PagerDetailsViewHolder access$302(ProfileHeaderAdapter x0, PagerDetailsViewHolder x1) {
            x0.mHolderPagerDetails = x1;
                    return x1;
                }

        static  void access$400(ProfileHeaderAdapter x0) {
            x0.invalidateDetailHeight();
                }

        public ProfileHeaderAdapter(Context context, AppController appController, ProfileFragment fragment, boolean locked, boolean blocked, String followEventSource, boolean hideProfileReposts, boolean showActionsContainer) {
            this.mContext = context;
            this.mListener = fragment;
            this.mAppController = appController;
            this.mFragment = fragment;
            this.mLocked = locked;
            this.mBlocked = blocked;
            this.mFollowEventSource = followEventSource;
            Resources res = context.getResources();
            this.mNumberUnSelectedColor = res.getColor(C0257R.color.black_thirty_five_percent);
            this.mBold = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(1, 4));
            this.mRegular = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(0, 2));
            this.mShowActionsContainer = showActionsContainer;
            this.mCurrentTab = 0;
                }

                @Override 
       public int getCount() {
           return 1;
                }

                @Override 
       public Object getItem(int i) {
           return null;
                }

                @Override 
       public long getItemId(int i) {
           return 0L;
                }

                @Override 
       public View getView(int i, View convertView, ViewGroup root) {
           if (convertView == null) {
               convertView = LayoutInflater.from(this.mContext).inflate(C0257R.layout.profile_header, root, false);
               this.mHolder = new ProfileViewHolder(convertView);
               this.mHolder.pager.setAdapter(new ProfileViewPagerAdapter(this, null));
               this.mHolder.pager.setOnPageChangeListener(this);
               this.mHolder.progress.setVisibility(0);
               this.mHolder.dots.setFinalIcon(false);
                               this.mHolder.dots.setNumberOfDots(2);
                this.mHolder.dots.setDotPadding(this.mContext.getResources().getDimensionPixelOffset(C0257R.dimen.profile_dot_padding));
               this.mHolder.dropDownHolder.revineParent.setOnClickListener(this.mFragment);
               this.mHolder.profileActions.setVisibility(this.mShowActionsContainer ? 0 : 8);
               if (this.mUser != null) {
                   bindUser(this.mUser);
                        }
                    }
           return convertView;
                }

       public void bindUser(VineUser profileUser) {
           if (this.mHolder != null && this.mHolderPagerMain != null && this.mHolderPagerDetails != null) {
               this.mUser = profileUser;
               ProfileViewHolder holder = this.mHolder;
               PagerMainViewHolder holderMain = this.mHolderPagerMain;
               PagerDetailsViewHolder holderDetails = this.mHolderPagerDetails;
               Resources res = this.mContext.getResources();
               if (TextUtils.isEmpty(profileUser.avatarUrl) || Util.isDefaultAvatarUrl(profileUser.avatarUrl)) {
                   setImage(null);
                        } else {
                   holderMain.userImageKey = new ImageKey(profileUser.avatarUrl, true);
                   setImage(this.mAppController.getPhotoBitmap(holderMain.userImageKey));
                        }
               holderMain.imageView.setOnClickListener(this.mListener);
               holderMain.imageView.setOnLongClickListener((View.OnLongClickListener) this.mListener);
               holderMain.username.setText(profileUser.username);
               if (profileUser.profileBackground > 0) {
                   holder.profileBackground.setBackgroundColor(profileUser.profileBackground | (-16777216));
                        }
               int tabSelectedColor = profileUser.profileBackground;
               if (tabSelectedColor == Settings.DEFAULT_PROFILE_COLOR || tabSelectedColor <= 0) {
                   tabSelectedColor = this.mContext.getResources().getColor(C0257R.color.vine_green);
                        }
               this.mTabSelectedColor = tabSelectedColor | (-16777216);
               holder.profileTabArrowImage.setColorFilter(new PorterDuffColorFilter(1275068416, PorterDuff.Mode.SRC_IN));
               if (!TextUtils.isEmpty(profileUser.description)) {
                   holderDetails.description.setText(profileUser.description);
                   holderDetails.description.setVisibility(0);
                        } else {
                   holderDetails.description.setVisibility(8);
                        }
               if (!TextUtils.isEmpty(profileUser.location)) {
                   holderDetails.location.setText(profileUser.location);
                   holderDetails.location.setVisibility(0);
                        } else {
                   holderDetails.location.setVisibility(8);
                        }
               holder.userId = profileUser.userId;
               if (profileUser.isVerified()) {
                   holderMain.verified.setVisibility(0);
                        } else {
                   holderMain.verified.setVisibility(8);
                        }
               if (this.mBlocked || this.mAppController.getActiveId() == profileUser.userId) {
                   holder.messageButton.setVisibility(8);
                        } else {
                   holder.messageButton.setVisibility(0);
                   holder.messageButton.setOnClickListener(this.mListener);
                   setupMessageButton(holder.messageButton, profileUser);
                        }
               Button button = holder.follow;
               if (this.mBlocked || this.mAppController.getActiveId() == profileUser.userId) {
                   button.setVisibility(8);
                        } else {
                   button.setVisibility(0);
                   setupFollowButton(button, profileUser);
                   button.setOnClickListener(this.mListener);
                        }
               Button settings = holder.settings;
                        if (this.mAppController.getActiveId() == profileUser.userId) {
                   settings.setVisibility(0);
                   settings.setOnClickListener(this.mListener);
                        } else {
                   settings.setVisibility(8);
                        }
               if (this.mLocked) {
                   holder.postsParent.setVisibility(8);
                   holder.likesParent.setVisibility(8);
                   holderDetails.userLoopCount.setVisibility(8);
                   holder.profileTabArrow.setVisibility(8);
                        } else {
                   holder.postsParent.setVisibility(0);
                   holder.likesParent.setVisibility(0);
                   holderDetails.userLoopCount.setVisibility(0);
                   holder.profileTabArrow.setVisibility(this.mCurrentTab == 0 ? 0 : 8);
                   holder.likesParent.setOnClickListener(this.mListener);
                   holder.postsParent.setOnClickListener(this.mListener);
                            if (profileUser.loopCount > 2147483647L) {
                       profileUser.loopCount = 2147483647L;
                            }
                   holderDetails.userLoopCount.setText(res.getQuantityString(C0257R.plurals.profile_loops, (int) profileUser.loopCount, Util.numberFormat(res, profileUser.loopCount, false)));
                   invalidateCountAndTabColor(1, profileUser.likeCount, res, C0257R.plurals.profile_likes, holder.likesLabel);
                   invalidateCountAndTabColor(0, profileUser.postCount, res, C0257R.plurals.profile_posts, holder.postsLabel);
                        }
               holderMain.imageFrame.setVisibility(0);
               holder.progress.setVisibility(8);
               holder.profileTabArrow.setOnClickListener(this.mListener);
               invalidateCount(profileUser.followerCount, res, C0257R.plurals.profile_followers, holderMain.followers);
               invalidateCount(profileUser.followingCount, res, C0257R.plurals.profile_following, holderMain.following);
               holderMain.followers.setOnClickListener(this.mListener);
               holderMain.following.setOnClickListener(this.mListener);
                    }
           invalidateDetailHeight();
           this.mUser = profileUser;
                }

       private void invalidateDetailHeight() {
                    View parent;
           if (this.mHolderPagerDetails != null && (parent = this.mHolderPagerDetails.parent) != null) {
               int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
               int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(SystemUtil.getDisplaySize(this.mContext).x, 1073741824);
               parent.measure(widthMeasureSpec, heightMeasureSpec);
               this.mDetailPageHeight = parent.getMeasuredHeight();
               if (this.mMainPageHeight <= 0) {
                   this.mMainPageHeight = this.mHolder.pager.getHeight();
                        }
                    }
                }

       private void invalidateCountAndTabColor(int tab, int count, Resources res, int resId, TypefacesTextView labelView) {
           invalidateCount(count, res, resId, labelView);
           invalidateTabColor(tab, labelView);
                }

       private void invalidateCount(int count, Resources res, int resId, TypefacesTextView labelView) {
           String text = res.getQuantityString(resId, count, Util.numberFormat(res, count));
           SpannableStringBuilder builder = new SpannableStringBuilder(text);
           int start = text.indexOf(34);
           int end = text.indexOf(34, start + 1);
           Util.safeSetSpan(builder, this.mBold, start, end, 33);
           Util.safeSetSpan(builder, this.mRegular, end, text.length(), 33);
           builder.delete(start, start + 1);
           builder.delete(end - 1, end);
           labelView.setText(builder);
                }

       private void invalidateTabColor(int tab, TextView numberView) {
           if (this.mCurrentTab == tab) {
               numberView.setTextColor(this.mTabSelectedColor);
                    } else {
               numberView.setTextColor(this.mNumberUnSelectedColor);
                    }
                }

       public void updatePostCount(int count, int tabToUpdate) {
           ProfileViewHolder holder = this.mHolder;
           Resources res = this.mContext.getResources();
           if (res != null && holder != null) {
               if (tabToUpdate == 0) {
                   invalidateCountAndTabColor(0, count, res, C0257R.plurals.profile_posts, holder.postsLabel);
                   invalidateTabColor(1, holder.likesLabel);
                        } else {
                   invalidateCountAndTabColor(1, count, res, C0257R.plurals.profile_likes, holder.likesLabel);
                   invalidateTabColor(0, holder.postsLabel);
                        }
                    }
                }

       private void setupMessageButton(Button messageButton, VineUser profileUser) {
           int color = profileUser.profileBackground;
           if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
               color = 16777215 & this.mContext.getResources().getColor(C0257R.color.vine_green);
                    }
           messageButton.setTextColor((-16777216) | color);
           Resources res = this.mContext.getResources();
           ViewUtil.fillColor(res, 1023410176 | color, C0257R.drawable.btn_profile, messageButton);
                }

       private void setupFollowButton(Button followButton, VineUser user) {
           int color = user.profileBackground;
           if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
               color = this.mContext.getResources().getColor(C0257R.color.vine_green);
                    }
           int color2 = color | (-16777216);
           if (user.hasFollowRequested()) {
               this.mFollowState = 3;
                    } else if (user.isFollowing()) {
               this.mFollowState = 2;
                    } else if (this.mAppController.getActiveId() != user.userId) {
               this.mFollowState = 1;
                    }
           followButton.setActivated(this.mFollowState == 2);
           Resources res = this.mContext.getResources();
           if (this.mFollowState == 2) {
               followButton.setTextColor(res.getColor(C0257R.color.solid_white));
               followButton.setText(res.getString(C0257R.string.following));
                    } else if (this.mFollowState == 1) {
               followButton.setTextColor(color2 | (-16777216));
               followButton.setText(res.getString(C0257R.string.follow));
                    }
           if (this.mFollowState != 3) {
               ViewUtil.fillColor(res, color2, C0257R.drawable.follow_button, followButton);
               followButton.setPadding(0, 0, 0, 0);
               return;
                    }
           int gray = res.getColor(C0257R.color.solid_dark_gray);
           ViewUtil.fillColor(res, gray, C0257R.drawable.follow_button, followButton);
           followButton.setTextColor(gray);
           followButton.setText(res.getString(C0257R.string.follow_requested));
           followButton.setPadding(res.getDimensionPixelSize(C0257R.dimen.standard_spacing), 0, 0, 0);
                }

       public void onProfileHeaderLongClick(View view) {
           if (view.getId() == C0257R.id.user_image && this.mUser != null && this.mUser.userId == this.mAppController.getActiveId()) {
               FlurryUtils.trackProfileImageClick(true);
               view.performHapticFeedback(0);
               ImageActivity.start(this.mContext, this.mUser.avatarUrl, C0257R.string.sign_up_profile_photo);
                    }
                }

       public void onProfileHeaderClick(View view, ProfileFragment fragment, VineUser user) {
           Context context = this.mContext;
           int id = view.getId();
           if (id == C0257R.id.settings) {
               FlurryUtils.trackVisitFindFriends("Profile");
               Intent intent = new Intent(context, (Class<?>) SettingsActivity.class);
               context.startActivity(intent);
               return;
                    }
           if (id == C0257R.id.profile_follow) {
               if (this.mFollowState == 2) {
                   fragment.addRequest(this.mAppController.unfollowUser(this.mAppController.getActiveSession(), this.mHolder.userId, true));
                   user.following = 0;
                   setupFollowButton(this.mHolder.follow, user);
                   FlurryUtils.trackFollow(this.mFollowEventSource);
                   return;
                        }
               if (this.mFollowState == 1) {
                   if (user.isPrivate()) {
                       user.followStatus |= 2;
                            } else {
                       user.following = 1;
                       user.repostsEnabled = 1;
                            }
                   setupFollowButton(this.mHolder.follow, user);
                   fragment.addRequest(this.mAppController.followUser(this.mAppController.getActiveSession(), this.mHolder.userId, true));
                   return;
                        }
               if (this.mFollowState == 3) {
                        }
               return;
                    }
           if (id == C0257R.id.likesParent || id == C0257R.id.postsParent) {
               if (this.mFragment.getActivity() instanceof HomeTabActivity) {
                   ((HomeTabActivity) this.mFragment.getActivity()).resetNav();
                        }
               changeSelectedTab(view.getId());
                    } else {
               if (id != C0257R.id.user_image || this.mUser == null) {
                   return;
                        }
                        if (this.mUser.userId == this.mAppController.getActiveId()) {
                   FlurryUtils.trackVisitSettings("Profile");
                   context.startActivity(new Intent(context, (Class<?>) SettingsActivity.class));
                        } else {
                   FlurryUtils.trackProfileImageClick(false);
                   ImageActivity.start(context, this.mUser.avatarUrl, C0257R.string.sign_up_profile_photo);
                        }
                    }
                }

       public void changeSelectedTab(int tabId) {
           if (this.mHolder != null) {
               if (tabId == C0257R.id.postsParent) {
                   this.mCurrentTab = 0;
                   this.mFragment.changeMode(1);
                        } else if (tabId == C0257R.id.likesParent) {
                   this.mCurrentTab = 1;
                   this.mFragment.changeMode(2);
                        }
               invalidateTabColor(0, this.mHolder.postsLabel);
               invalidateTabColor(1, this.mHolder.likesLabel);
               this.mHolder.profileTabArrow.setVisibility(this.mCurrentTab != 0 ? 8 : 0);
                    }
                }

       public void onImageLoaded(HashMap<ImageKey, UrlImage> images) {
                    UrlImage urlImage;
           if (this.mHolderPagerMain != null && this.mHolderPagerMain.userImageKey != null && (urlImage = images.get(this.mHolderPagerMain.userImageKey)) != null) {
               setImage(urlImage.bitmap);
                    }
                }

       private void setImage(Bitmap bmp) {
           if (bmp == null) {
               this.mHolderPagerMain.imageView.setImageResource(C0257R.drawable.avatar_large);
               if (this.mUser != null && this.mUser.userId == this.mAppController.getActiveId()) {
                   this.mHolderPagerMain.imageAction.setVisibility(0);
                   return;
                        } else {
                   this.mHolderPagerMain.imageAction.setVisibility(8);
                   return;
                        }
                    }
           this.mHolderPagerMain.imageAction.setVisibility(8);
           this.mHolderPagerMain.imageView.setImageDrawable(new RecyclableBitmapDrawable(this.mHolderPagerMain.imageView.getResources(), bmp));
                }

                @Override 
       public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (positionOffset > 0.0f && this.mMainPageHeight > 0 && this.mDetailPageHeight > this.mMainPageHeight) {
               int delta = this.mDetailPageHeight - this.mMainPageHeight;
               ViewGroup.LayoutParams param = this.mHolder.pager.getLayoutParams();
               param.height = (int) (this.mMainPageHeight + (delta * positionOffset));
               this.mHolder.pager.setLayoutParams(param);
                    }
                }

                @Override 
       public void onPageSelected(int i) {
           if (this.mHolder != null && this.mHolder.dots != null) {
               this.mHolder.dots.setActiveDot(i);
                    }
                }

                @Override 
       public void onPageScrollStateChanged(int i) {
                }

       private class ProfileViewPagerAdapter extends PagerAdapter {
                    public static final int PAGER_COUNT = 2;
                    public static final int PAGER_INDEX_DETAILS = 1;
                    public static final int PAGER_INDEX_MAIN = 0;

           private ProfileViewPagerAdapter() {
                    }

            ProfileViewPagerAdapter(ProfileHeaderAdapter x0, Object x1) {
               this();
                    }

                    @Override 
           public int getCount() {
               return 2;
                    }

                    @Override 
           public Object instantiateItem(ViewGroup container, int position) {
               LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService("layout_inflater");
                        switch (position) {
                            case 0:
                       View v = inflater.inflate(C0257R.layout.profile_header_pager_main, container, false);
                       ProfileHeaderAdapter.access$102(ProfileHeaderAdapter.this, new PagerMainViewHolder(v));
                       container.addView(v);
                       if (ProfileHeaderAdapter.access$200(ProfileHeaderAdapter.this) == null) {
                                    return v;
                                }
                       ProfileHeaderAdapter.this.bindUser(ProfileHeaderAdapter.access$200(ProfileHeaderAdapter.this));
                                return v;
                            case 1:
                       View v2 = inflater.inflate(C0257R.layout.profile_header_pager_details, container, false);
                       ProfileHeaderAdapter.access$302(ProfileHeaderAdapter.this, new PagerDetailsViewHolder(v2));
                       container.addView(v2);
                       if (ProfileHeaderAdapter.access$200(ProfileHeaderAdapter.this) != null) {
                           ProfileHeaderAdapter.this.bindUser(ProfileHeaderAdapter.access$200(ProfileHeaderAdapter.this));
                                }
                       ProfileHeaderAdapter.access$400(ProfileHeaderAdapter.this);
                                return v2;
                            default:
                       return null;
                        }
                    }

                    @Override 
           public boolean isViewFromObject(View view, Object o) {
                        return view == o;
                    }
                }

       static class PagerMainViewHolder {
                    public final TypefacesTextView followers;
                    public final TypefacesTextView following;
                    public ImageView imageAction;
                    public ImageView imageFrame;
                    public ImageView imageView;
                    public ImageKey userImageKey;
                    public TextView username;
                    public ImageView verified;

           public PagerMainViewHolder(View v) {
               this.imageFrame = (ImageView) v.findViewById(C0257R.id.user_image_frame);
               this.username = (TextView) v.findViewById(C0257R.id.username);
               this.verified = (ImageView) v.findViewById(C0257R.id.profile_verified);
               this.imageView = (ImageView) v.findViewById(C0257R.id.user_image);
               this.imageAction = (ImageView) v.findViewById(C0257R.id.user_image_action);
               this.followers = (TypefacesTextView) v.findViewById(C0257R.id.followers);
               this.following = (TypefacesTextView) v.findViewById(C0257R.id.following);
                    }
                }

       static class PagerDetailsViewHolder {
                    public TextView description;
                    public TextView location;
                    public View parent;
                    public TextView userLoopCount;

           public PagerDetailsViewHolder(View v) {
               this.parent = v.findViewById(C0257R.id.measurableParent);
               this.location = (TextView) v.findViewById(C0257R.id.location);
               this.description = (TextView) v.findViewById(C0257R.id.description);
               this.userLoopCount = (TextView) v.findViewById(C0257R.id.userLoopCount);
                    }
                }

       static class ProfileViewHolder {
                    public final DotIndicators dots;
                    public final DropdownHolder dropDownHolder;
                    public final Button follow;
                    public final TypefacesTextView likesLabel;
                    public final View likesParent;
                    public final Button messageButton;
                    public final ViewPager pager;
                    public final TypefacesTextView postsLabel;
                    public final View postsParent;
                    public final ViewGroup profileActions;
                    public final RelativeLayout profileBackground;
                    public final ViewGroup profileTabArrow;
                    public final ImageView profileTabArrowImage;
                    public final ProgressBar progress;
                    public final Button settings;
                    public long userId;

           public ProfileViewHolder(View v) {
               this.pager = (ViewPager) v.findViewById(C0257R.id.pager);
               this.profileBackground = (RelativeLayout) v.findViewById(C0257R.id.profile_content_outer);
               this.follow = (Button) v.findViewById(C0257R.id.profile_follow);
               this.settings = (Button) v.findViewById(C0257R.id.settings);
               this.messageButton = (Button) v.findViewById(C0257R.id.message_button);
               this.postsParent = v.findViewById(C0257R.id.postsParent);
               this.postsLabel = (TypefacesTextView) v.findViewById(C0257R.id.postsLabel);
               this.likesParent = v.findViewById(C0257R.id.likesParent);
               this.likesLabel = (TypefacesTextView) v.findViewById(C0257R.id.likesLabel);
               this.progress = (ProgressBar) v.findViewById(C0257R.id.profile_progress);
               this.profileTabArrow = (ViewGroup) v.findViewById(C0257R.id.posts_dropdown);
               this.profileTabArrowImage = (ImageView) v.findViewById(C0257R.id.profile_arrow_image);
               this.dots = (DotIndicators) v.findViewById(C0257R.id.dots);
               this.profileActions = (ViewGroup) v.findViewById(C0257R.id.profile_actions_container);
               View dropDown = v.findViewById(C0257R.id.postsLikeDropDown);
               this.dropDownHolder = new DropdownHolder(dropDown);
               dropDown.setTag(this.dropDownHolder);
                    }
                }

       public static class DropdownHolder {
                    public final ImageView revineIcon;
                    public final TextView revineLabel;
                    public final View revineParent;

           public DropdownHolder(View v) {
               this.revineParent = v.findViewById(C0257R.id.postsDropDownHideRevine);
               this.revineLabel = (TextView) v.findViewById(C0257R.id.postsDropDownHideRevineLabel);
               this.revineIcon = (ImageView) v.findViewById(C0257R.id.postsDropDownHideRevineIcon);
                    }
                }

       public void updateFollowButtonState(VineUser user) {
           if (this.mHolder != null) {
               setupFollowButton(this.mHolder.follow, user);
                    }
                }
            }
