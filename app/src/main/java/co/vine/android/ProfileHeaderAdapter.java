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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 43 */    public class ProfileHeaderAdapter extends BaseAdapter implements ViewPager.OnPageChangeListener {
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

/* 44 */        static /* synthetic */ PagerMainViewHolder access$102(ProfileHeaderAdapter x0, PagerMainViewHolder x1) {
/* 45 */            x0.mHolderPagerMain = x1;
                    return x1;
                }

/* 44 */        static /* synthetic */ VineUser access$200(ProfileHeaderAdapter x0) {
/* 45 */            return x0.mUser;
                }

/* 44 */        static /* synthetic */ PagerDetailsViewHolder access$302(ProfileHeaderAdapter x0, PagerDetailsViewHolder x1) {
/* 45 */            x0.mHolderPagerDetails = x1;
                    return x1;
                }

/* 44 */        static /* synthetic */ void access$400(ProfileHeaderAdapter x0) {
/* 45 */            x0.invalidateDetailHeight();
                }

/* 79 */        public ProfileHeaderAdapter(Context context, AppController appController, ProfileFragment fragment, boolean locked, boolean blocked, String followEventSource, boolean hideProfileReposts, boolean showActionsContainer) {
/* 81 */            this.mContext = context;
/* 82 */            this.mListener = fragment;
/* 83 */            this.mAppController = appController;
/* 84 */            this.mFragment = fragment;
/* 85 */            this.mLocked = locked;
/* 86 */            this.mBlocked = blocked;
/* 87 */            this.mFollowEventSource = followEventSource;
/* 89 */            Resources res = context.getResources();
/* 90 */            this.mNumberUnSelectedColor = res.getColor(C0257R.color.black_thirty_five_percent);
/* 92 */            this.mBold = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(1, 4));
/* 94 */            this.mRegular = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(0, 2));
/* 97 */            this.mShowActionsContainer = showActionsContainer;
/* 98 */            this.mCurrentTab = 0;
                }

                @Override // android.widget.Adapter
/* 102 */       public int getCount() {
/* 103 */           return 1;
                }

                @Override // android.widget.Adapter
/* 107 */       public Object getItem(int i) {
/* 108 */           return null;
                }

                @Override // android.widget.Adapter
/* 112 */       public long getItemId(int i) {
/* 113 */           return 0L;
                }

                @Override // android.widget.Adapter
/* 117 */       public View getView(int i, View convertView, ViewGroup root) {
/* 118 */           if (convertView == null) {
/* 119 */               convertView = LayoutInflater.from(this.mContext).inflate(C0257R.layout.profile_header, root, false);
/* 121 */               this.mHolder = new ProfileViewHolder(convertView);
/* 122 */               this.mHolder.pager.setAdapter(new ProfileViewPagerAdapter(this, null));
/* 123 */               this.mHolder.pager.setOnPageChangeListener(this);
/* 126 */               this.mHolder.progress.setVisibility(0);
/* 127 */               this.mHolder.dots.setFinalIcon(false);
/* 128 */                               this.mHolder.dots.setNumberOfDots(2);
                this.mHolder.dots.setDotPadding(this.mContext.getResources().getDimensionPixelOffset(C0257R.dimen.profile_dot_padding));
/* 130 */               this.mHolder.dropDownHolder.revineParent.setOnClickListener(this.mFragment);
/* 132 */               this.mHolder.profileActions.setVisibility(this.mShowActionsContainer ? 0 : 8);
/* 134 */               if (this.mUser != null) {
/* 135 */                   bindUser(this.mUser);
                        }
                    }
/* 138 */           return convertView;
                }

/* 141 */       public void bindUser(VineUser profileUser) {
/* 142 */           if (this.mHolder != null && this.mHolderPagerMain != null && this.mHolderPagerDetails != null) {
/* 143 */               this.mUser = profileUser;
/* 144 */               ProfileViewHolder holder = this.mHolder;
/* 145 */               PagerMainViewHolder holderMain = this.mHolderPagerMain;
/* 146 */               PagerDetailsViewHolder holderDetails = this.mHolderPagerDetails;
/* 147 */               Resources res = this.mContext.getResources();
/* 148 */               if (TextUtils.isEmpty(profileUser.avatarUrl) || Util.isDefaultAvatarUrl(profileUser.avatarUrl)) {
/* 150 */                   setImage(null);
                        } else {
/* 152 */                   holderMain.userImageKey = new ImageKey(profileUser.avatarUrl, true);
/* 153 */                   setImage(this.mAppController.getPhotoBitmap(holderMain.userImageKey));
                        }
/* 155 */               holderMain.imageView.setOnClickListener(this.mListener);
/* 156 */               holderMain.imageView.setOnLongClickListener((View.OnLongClickListener) this.mListener);
/* 157 */               holderMain.username.setText(profileUser.username);
/* 159 */               if (profileUser.profileBackground > 0) {
/* 160 */                   holder.profileBackground.setBackgroundColor(profileUser.profileBackground | (-16777216));
                        }
/* 164 */               int tabSelectedColor = profileUser.profileBackground;
/* 165 */               if (tabSelectedColor == Settings.DEFAULT_PROFILE_COLOR || tabSelectedColor <= 0) {
/* 166 */                   tabSelectedColor = this.mContext.getResources().getColor(C0257R.color.vine_green);
                        }
/* 170 */               this.mTabSelectedColor = tabSelectedColor | (-16777216);
/* 172 */               holder.profileTabArrowImage.setColorFilter(new PorterDuffColorFilter(1275068416, PorterDuff.Mode.SRC_IN));
/* 174 */               if (!TextUtils.isEmpty(profileUser.description)) {
/* 175 */                   holderDetails.description.setText(profileUser.description);
/* 176 */                   holderDetails.description.setVisibility(0);
                        } else {
/* 178 */                   holderDetails.description.setVisibility(8);
                        }
/* 181 */               if (!TextUtils.isEmpty(profileUser.location)) {
/* 182 */                   holderDetails.location.setText(profileUser.location);
/* 183 */                   holderDetails.location.setVisibility(0);
                        } else {
/* 185 */                   holderDetails.location.setVisibility(8);
                        }
/* 187 */               holder.userId = profileUser.userId;
/* 189 */               if (profileUser.isVerified()) {
/* 190 */                   holderMain.verified.setVisibility(0);
                        } else {
/* 192 */                   holderMain.verified.setVisibility(8);
                        }
/* 195 */               if (this.mBlocked || this.mAppController.getActiveId() == profileUser.userId) {
/* 196 */                   holder.messageButton.setVisibility(8);
                        } else {
/* 198 */                   holder.messageButton.setVisibility(0);
/* 199 */                   holder.messageButton.setOnClickListener(this.mListener);
/* 200 */                   setupMessageButton(holder.messageButton, profileUser);
                        }
/* 203 */               Button button = holder.follow;
/* 204 */               if (this.mBlocked || this.mAppController.getActiveId() == profileUser.userId) {
/* 205 */                   button.setVisibility(8);
                        } else {
/* 207 */                   button.setVisibility(0);
/* 208 */                   setupFollowButton(button, profileUser);
/* 209 */                   button.setOnClickListener(this.mListener);
                        }
/* 212 */               Button settings = holder.settings;
                        if (this.mAppController.getActiveId() == profileUser.userId) {
/* 214 */                   settings.setVisibility(0);
/* 215 */                   settings.setOnClickListener(this.mListener);
                        } else {
/* 217 */                   settings.setVisibility(8);
                        }
/* 220 */               if (this.mLocked) {
/* 221 */                   holder.postsParent.setVisibility(8);
/* 222 */                   holder.likesParent.setVisibility(8);
/* 223 */                   holderDetails.userLoopCount.setVisibility(8);
/* 224 */                   holder.profileTabArrow.setVisibility(8);
                        } else {
/* 226 */                   holder.postsParent.setVisibility(0);
/* 227 */                   holder.likesParent.setVisibility(0);
/* 228 */                   holderDetails.userLoopCount.setVisibility(0);
/* 229 */                   holder.profileTabArrow.setVisibility(this.mCurrentTab == 0 ? 0 : 8);
/* 230 */                   holder.likesParent.setOnClickListener(this.mListener);
/* 231 */                   holder.postsParent.setOnClickListener(this.mListener);
                            if (profileUser.loopCount > 2147483647L) {
/* 235 */                       profileUser.loopCount = 2147483647L;
                            }
/* 238 */                   holderDetails.userLoopCount.setText(res.getQuantityString(C0257R.plurals.profile_loops, (int) profileUser.loopCount, Util.numberFormat(res, profileUser.loopCount, false)));
/* 241 */                   invalidateCountAndTabColor(1, profileUser.likeCount, res, C0257R.plurals.profile_likes, holder.likesLabel);
/* 242 */                   invalidateCountAndTabColor(0, profileUser.postCount, res, C0257R.plurals.profile_posts, holder.postsLabel);
                        }
/* 244 */               holderMain.imageFrame.setVisibility(0);
/* 245 */               holder.progress.setVisibility(8);
/* 246 */               holder.profileTabArrow.setOnClickListener(this.mListener);
/* 248 */               invalidateCount(profileUser.followerCount, res, C0257R.plurals.profile_followers, holderMain.followers);
/* 249 */               invalidateCount(profileUser.followingCount, res, C0257R.plurals.profile_following, holderMain.following);
/* 251 */               holderMain.followers.setOnClickListener(this.mListener);
/* 252 */               holderMain.following.setOnClickListener(this.mListener);
                    }
/* 255 */           invalidateDetailHeight();
/* 257 */           this.mUser = profileUser;
                }

/* 260 */       private void invalidateDetailHeight() {
                    View parent;
/* 261 */           if (this.mHolderPagerDetails != null && (parent = this.mHolderPagerDetails.parent) != null) {
/* 264 */               int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
/* 265 */               int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(SystemUtil.getDisplaySize(this.mContext).x, 1073741824);
/* 266 */               parent.measure(widthMeasureSpec, heightMeasureSpec);
/* 267 */               this.mDetailPageHeight = parent.getMeasuredHeight();
/* 268 */               if (this.mMainPageHeight <= 0) {
/* 269 */                   this.mMainPageHeight = this.mHolder.pager.getHeight();
                        }
                    }
                }

/* 275 */       private void invalidateCountAndTabColor(int tab, int count, Resources res, int resId, TypefacesTextView labelView) {
/* 276 */           invalidateCount(count, res, resId, labelView);
/* 277 */           invalidateTabColor(tab, labelView);
                }

/* 280 */       private void invalidateCount(int count, Resources res, int resId, TypefacesTextView labelView) {
/* 281 */           String text = res.getQuantityString(resId, count, Util.numberFormat(res, count));
/* 282 */           SpannableStringBuilder builder = new SpannableStringBuilder(text);
/* 284 */           int start = text.indexOf(34);
/* 285 */           int end = text.indexOf(34, start + 1);
/* 287 */           Util.safeSetSpan(builder, this.mBold, start, end, 33);
/* 288 */           Util.safeSetSpan(builder, this.mRegular, end, text.length(), 33);
/* 290 */           builder.delete(start, start + 1);
/* 291 */           builder.delete(end - 1, end);
/* 293 */           labelView.setText(builder);
                }

/* 296 */       private void invalidateTabColor(int tab, TextView numberView) {
/* 297 */           if (this.mCurrentTab == tab) {
/* 298 */               numberView.setTextColor(this.mTabSelectedColor);
                    } else {
/* 300 */               numberView.setTextColor(this.mNumberUnSelectedColor);
                    }
                }

/* 304 */       public void updatePostCount(int count, int tabToUpdate) {
/* 305 */           ProfileViewHolder holder = this.mHolder;
/* 306 */           Resources res = this.mContext.getResources();
/* 307 */           if (res != null && holder != null) {
/* 308 */               if (tabToUpdate == 0) {
/* 309 */                   invalidateCountAndTabColor(0, count, res, C0257R.plurals.profile_posts, holder.postsLabel);
/* 311 */                   invalidateTabColor(1, holder.likesLabel);
                        } else {
/* 313 */                   invalidateCountAndTabColor(1, count, res, C0257R.plurals.profile_likes, holder.likesLabel);
/* 315 */                   invalidateTabColor(0, holder.postsLabel);
                        }
                    }
                }

/* 320 */       private void setupMessageButton(Button messageButton, VineUser profileUser) {
/* 321 */           int color = profileUser.profileBackground;
/* 322 */           if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
/* 323 */               color = 16777215 & this.mContext.getResources().getColor(C0257R.color.vine_green);
                    }
/* 325 */           messageButton.setTextColor((-16777216) | color);
/* 326 */           Resources res = this.mContext.getResources();
/* 327 */           ViewUtil.fillColor(res, 1023410176 | color, C0257R.drawable.btn_profile, messageButton);
                }

/* 330 */       private void setupFollowButton(Button followButton, VineUser user) {
/* 331 */           int color = user.profileBackground;
/* 332 */           if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
/* 333 */               color = this.mContext.getResources().getColor(C0257R.color.vine_green);
                    }
/* 335 */           int color2 = color | (-16777216);
/* 337 */           if (user.hasFollowRequested()) {
/* 338 */               this.mFollowState = 3;
                    } else if (user.isFollowing()) {
/* 340 */               this.mFollowState = 2;
                    } else if (this.mAppController.getActiveId() != user.userId) {
/* 342 */               this.mFollowState = 1;
                    }
/* 345 */           followButton.setActivated(this.mFollowState == 2);
/* 347 */           Resources res = this.mContext.getResources();
/* 348 */           if (this.mFollowState == 2) {
/* 349 */               followButton.setTextColor(res.getColor(C0257R.color.solid_white));
/* 350 */               followButton.setText(res.getString(C0257R.string.following));
                    } else if (this.mFollowState == 1) {
/* 352 */               followButton.setTextColor(color2 | (-16777216));
/* 353 */               followButton.setText(res.getString(C0257R.string.follow));
                    }
/* 356 */           if (this.mFollowState != 3) {
/* 357 */               ViewUtil.fillColor(res, color2, C0257R.drawable.follow_button, followButton);
/* 358 */               followButton.setPadding(0, 0, 0, 0);
/* 366 */               return;
                    }
/* 360 */           int gray = res.getColor(C0257R.color.solid_dark_gray);
/* 361 */           ViewUtil.fillColor(res, gray, C0257R.drawable.follow_button, followButton);
/* 362 */           followButton.setTextColor(gray);
/* 363 */           followButton.setText(res.getString(C0257R.string.follow_requested));
/* 364 */           followButton.setPadding(res.getDimensionPixelSize(C0257R.dimen.standard_spacing), 0, 0, 0);
                }

/* 368 */       public void onProfileHeaderLongClick(View view) {
/* 369 */           if (view.getId() == C0257R.id.user_image && this.mUser != null && this.mUser.userId == this.mAppController.getActiveId()) {
/* 371 */               FlurryUtils.trackProfileImageClick(true);
/* 372 */               view.performHapticFeedback(0);
/* 373 */               ImageActivity.start(this.mContext, this.mUser.avatarUrl, C0257R.string.sign_up_profile_photo);
                    }
                }

/* 378 */       public void onProfileHeaderClick(View view, ProfileFragment fragment, VineUser user) {
/* 379 */           Context context = this.mContext;
/* 380 */           int id = view.getId();
/* 381 */           if (id == C0257R.id.settings) {
/* 382 */               FlurryUtils.trackVisitFindFriends("Profile");
/* 383 */               Intent intent = new Intent(context, (Class<?>) SettingsActivity.class);
/* 384 */               context.startActivity(intent);
/* 421 */               return;
                    }
/* 385 */           if (id == C0257R.id.profile_follow) {
/* 386 */               if (this.mFollowState == 2) {
/* 387 */                   fragment.addRequest(this.mAppController.unfollowUser(this.mAppController.getActiveSession(), this.mHolder.userId, true));
/* 389 */                   user.following = 0;
/* 390 */                   setupFollowButton(this.mHolder.follow, user);
/* 391 */                   FlurryUtils.trackFollow(this.mFollowEventSource);
/* 421 */                   return;
                        }
/* 392 */               if (this.mFollowState == 1) {
/* 393 */                   if (user.isPrivate()) {
/* 394 */                       user.followStatus |= 2;
                            } else {
/* 396 */                       user.following = 1;
/* 397 */                       user.repostsEnabled = 1;
                            }
/* 399 */                   setupFollowButton(this.mHolder.follow, user);
/* 400 */                   fragment.addRequest(this.mAppController.followUser(this.mAppController.getActiveSession(), this.mHolder.userId, true));
/* 421 */                   return;
                        }
/* 402 */               if (this.mFollowState == 3) {
                        }
/* 421 */               return;
                    }
/* 405 */           if (id == C0257R.id.likesParent || id == C0257R.id.postsParent) {
/* 406 */               if (this.mFragment.getActivity() instanceof HomeTabActivity) {
/* 407 */                   ((HomeTabActivity) this.mFragment.getActivity()).resetNav();
                        }
/* 409 */               changeSelectedTab(view.getId());
                    } else {
/* 410 */               if (id != C0257R.id.user_image || this.mUser == null) {
/* 421 */                   return;
                        }
                        if (this.mUser.userId == this.mAppController.getActiveId()) {
/* 413 */                   FlurryUtils.trackVisitSettings("Profile");
/* 414 */                   context.startActivity(new Intent(context, (Class<?>) SettingsActivity.class));
                        } else {
/* 416 */                   FlurryUtils.trackProfileImageClick(false);
/* 417 */                   ImageActivity.start(context, this.mUser.avatarUrl, C0257R.string.sign_up_profile_photo);
                        }
                    }
                }

/* 423 */       public void changeSelectedTab(int tabId) {
/* 424 */           if (this.mHolder != null) {
/* 427 */               if (tabId == C0257R.id.postsParent) {
/* 428 */                   this.mCurrentTab = 0;
/* 429 */                   this.mFragment.changeMode(1);
                        } else if (tabId == C0257R.id.likesParent) {
/* 431 */                   this.mCurrentTab = 1;
/* 432 */                   this.mFragment.changeMode(2);
                        }
/* 434 */               invalidateTabColor(0, this.mHolder.postsLabel);
/* 435 */               invalidateTabColor(1, this.mHolder.likesLabel);
/* 436 */               this.mHolder.profileTabArrow.setVisibility(this.mCurrentTab != 0 ? 8 : 0);
                    }
                }

/* 439 */       public void onImageLoaded(HashMap<ImageKey, UrlImage> images) {
                    UrlImage urlImage;
/* 440 */           if (this.mHolderPagerMain != null && this.mHolderPagerMain.userImageKey != null && (urlImage = images.get(this.mHolderPagerMain.userImageKey)) != null) {
/* 444 */               setImage(urlImage.bitmap);
                    }
                }

/* 450 */       private void setImage(Bitmap bmp) {
/* 451 */           if (bmp == null) {
/* 452 */               this.mHolderPagerMain.imageView.setImageResource(C0257R.drawable.avatar_large);
/* 453 */               if (this.mUser != null && this.mUser.userId == this.mAppController.getActiveId()) {
/* 454 */                   this.mHolderPagerMain.imageAction.setVisibility(0);
/* 462 */                   return;
                        } else {
/* 456 */                   this.mHolderPagerMain.imageAction.setVisibility(8);
/* 462 */                   return;
                        }
                    }
/* 459 */           this.mHolderPagerMain.imageAction.setVisibility(8);
/* 460 */           this.mHolderPagerMain.imageView.setImageDrawable(new RecyclableBitmapDrawable(this.mHolderPagerMain.imageView.getResources(), bmp));
                }

                @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 465 */       public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (positionOffset > 0.0f && this.mMainPageHeight > 0 && this.mDetailPageHeight > this.mMainPageHeight) {
/* 467 */               int delta = this.mDetailPageHeight - this.mMainPageHeight;
/* 468 */               ViewGroup.LayoutParams param = this.mHolder.pager.getLayoutParams();
/* 469 */               param.height = (int) (this.mMainPageHeight + (delta * positionOffset));
/* 470 */               this.mHolder.pager.setLayoutParams(param);
                    }
                }

                @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 475 */       public void onPageSelected(int i) {
/* 476 */           if (this.mHolder != null && this.mHolder.dots != null) {
/* 477 */               this.mHolder.dots.setActiveDot(i);
                    }
                }

                @Override // android.support.v4.view.ViewPager.OnPageChangeListener
/* 483 */       public void onPageScrollStateChanged(int i) {
                }

/* 484 */       private class ProfileViewPagerAdapter extends PagerAdapter {
                    public static final int PAGER_COUNT = 2;
                    public static final int PAGER_INDEX_DETAILS = 1;
                    public static final int PAGER_INDEX_MAIN = 0;

/* 485 */           private ProfileViewPagerAdapter() {
                    }

/* 485 */           /* synthetic */ ProfileViewPagerAdapter(ProfileHeaderAdapter x0, Object x1) {
/* 486 */               this();
                    }

                    @Override // android.support.v4.view.PagerAdapter
/* 493 */           public int getCount() {
/* 494 */               return 2;
                    }

                    @Override // android.support.v4.view.PagerAdapter
/* 498 */           public Object instantiateItem(ViewGroup container, int position) {
/* 499 */               LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService("layout_inflater");
                        switch (position) {
                            case 0:
/* 503 */                       View v = inflater.inflate(C0257R.layout.profile_header_pager_main, container, false);
/* 504 */                       ProfileHeaderAdapter.access$102(ProfileHeaderAdapter.this, new PagerMainViewHolder(v));
/* 505 */                       container.addView(v);
/* 506 */                       if (ProfileHeaderAdapter.access$200(ProfileHeaderAdapter.this) == null) {
                                    return v;
                                }
/* 507 */                       ProfileHeaderAdapter.this.bindUser(ProfileHeaderAdapter.access$200(ProfileHeaderAdapter.this));
                                return v;
                            case 1:
/* 512 */                       View v2 = inflater.inflate(C0257R.layout.profile_header_pager_details, container, false);
/* 513 */                       ProfileHeaderAdapter.access$302(ProfileHeaderAdapter.this, new PagerDetailsViewHolder(v2));
/* 514 */                       container.addView(v2);
/* 515 */                       if (ProfileHeaderAdapter.access$200(ProfileHeaderAdapter.this) != null) {
/* 516 */                           ProfileHeaderAdapter.this.bindUser(ProfileHeaderAdapter.access$200(ProfileHeaderAdapter.this));
                                }
/* 519 */                       ProfileHeaderAdapter.access$400(ProfileHeaderAdapter.this);
                                return v2;
                            default:
/* 523 */                       return null;
                        }
                    }

                    @Override // android.support.v4.view.PagerAdapter
/* 527 */           public boolean isViewFromObject(View view, Object o) {
                        return view == o;
                    }
                }

/* 543 */       static class PagerMainViewHolder {
                    public final TypefacesTextView followers;
                    public final TypefacesTextView following;
                    public ImageView imageAction;
                    public ImageView imageFrame;
                    public ImageView imageView;
                    public ImageKey userImageKey;
                    public TextView username;
                    public ImageView verified;

/* 544 */           public PagerMainViewHolder(View v) {
/* 546 */               this.imageFrame = (ImageView) v.findViewById(C0257R.id.user_image_frame);
/* 547 */               this.username = (TextView) v.findViewById(C0257R.id.username);
/* 548 */               this.verified = (ImageView) v.findViewById(C0257R.id.profile_verified);
/* 549 */               this.imageView = (ImageView) v.findViewById(C0257R.id.user_image);
/* 550 */               this.imageAction = (ImageView) v.findViewById(C0257R.id.user_image_action);
/* 551 */               this.followers = (TypefacesTextView) v.findViewById(C0257R.id.followers);
/* 552 */               this.following = (TypefacesTextView) v.findViewById(C0257R.id.following);
                    }
                }

/* 563 */       static class PagerDetailsViewHolder {
                    public TextView description;
                    public TextView location;
                    public View parent;
                    public TextView userLoopCount;

/* 564 */           public PagerDetailsViewHolder(View v) {
/* 566 */               this.parent = v.findViewById(C0257R.id.measurableParent);
/* 567 */               this.location = (TextView) v.findViewById(C0257R.id.location);
/* 568 */               this.description = (TextView) v.findViewById(C0257R.id.description);
/* 569 */               this.userLoopCount = (TextView) v.findViewById(C0257R.id.userLoopCount);
                    }
                }

/* 590 */       static class ProfileViewHolder {
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

/* 591 */           public ProfileViewHolder(View v) {
/* 593 */               this.pager = (ViewPager) v.findViewById(C0257R.id.pager);
/* 594 */               this.profileBackground = (RelativeLayout) v.findViewById(C0257R.id.profile_content_outer);
/* 595 */               this.follow = (Button) v.findViewById(C0257R.id.profile_follow);
/* 596 */               this.settings = (Button) v.findViewById(C0257R.id.settings);
/* 597 */               this.messageButton = (Button) v.findViewById(C0257R.id.message_button);
/* 598 */               this.postsParent = v.findViewById(C0257R.id.postsParent);
/* 599 */               this.postsLabel = (TypefacesTextView) v.findViewById(C0257R.id.postsLabel);
/* 600 */               this.likesParent = v.findViewById(C0257R.id.likesParent);
/* 601 */               this.likesLabel = (TypefacesTextView) v.findViewById(C0257R.id.likesLabel);
/* 602 */               this.progress = (ProgressBar) v.findViewById(C0257R.id.profile_progress);
/* 603 */               this.profileTabArrow = (ViewGroup) v.findViewById(C0257R.id.posts_dropdown);
/* 604 */               this.profileTabArrowImage = (ImageView) v.findViewById(C0257R.id.profile_arrow_image);
/* 605 */               this.dots = (DotIndicators) v.findViewById(C0257R.id.dots);
/* 606 */               this.profileActions = (ViewGroup) v.findViewById(C0257R.id.profile_actions_container);
/* 607 */               View dropDown = v.findViewById(C0257R.id.postsLikeDropDown);
/* 608 */               this.dropDownHolder = new DropdownHolder(dropDown);
/* 609 */               dropDown.setTag(this.dropDownHolder);
                    }
                }

/* 616 */       public static class DropdownHolder {
                    public final ImageView revineIcon;
                    public final TextView revineLabel;
                    public final View revineParent;

/* 617 */           public DropdownHolder(View v) {
/* 619 */               this.revineParent = v.findViewById(C0257R.id.postsDropDownHideRevine);
/* 620 */               this.revineLabel = (TextView) v.findViewById(C0257R.id.postsDropDownHideRevineLabel);
/* 621 */               this.revineIcon = (ImageView) v.findViewById(C0257R.id.postsDropDownHideRevineIcon);
                    }
                }

/* 625 */       public void updateFollowButtonState(VineUser user) {
/* 626 */           if (this.mHolder != null) {
/* 627 */               setupFollowButton(this.mHolder.follow, user);
                    }
                }
            }
