            package co.vine.android;

            import android.app.Activity;
            import android.content.Context;
            import android.content.SharedPreferences;
            import android.content.res.Resources;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.ColorFilter;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.drawable.ColorDrawable;
            import android.os.Handler;
            import android.os.HandlerThread;
            import android.os.Looper;
            import android.preference.PreferenceManager;
            import android.support.v8.renderscript.RenderScript;
            import android.support.v4.widget.CursorAdapter;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import android.text.format.DateFormat;
            import android.text.method.LinkMovementMethod;
            import android.util.AttributeSet;
            import android.util.SparseArray;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.ListView;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VinePost;
            import co.vine.android.api.VinePrivateMessage;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.cache.video.UrlVideo;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.client.AppController;
            import co.vine.android.drawable.ColoredOvalDrawable;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.player.HasVideoPlayerAdapter;
            import co.vine.android.player.OnListVideoClickListener;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.player.VideoViewInterface;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RsBlur;
            import co.vine.android.service.VineUploadService;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.MuteUtil;
            import co.vine.android.util.SeekInfoManager;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.util.VineDateFormatter;
            import co.vine.android.widget.ColoredSpan;
            import co.vine.android.widget.StyledClickableSpan;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.TypefacesSpan;
            import com.edisonwang.android.slog.SLog;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.Date;
            import java.util.HashMap;
            import java.util.Iterator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 78 */    public class ConversationAdapter extends CursorAdapter implements HasVideoPlayerAdapter, View.OnClickListener {
                private static final int VIEW_TYPE_COUNT = 2;
                private static final int VIEW_TYPE_ME = 1;
                private static final int VIEW_TYPE_YOU = 0;
                private final ConversationActivity mActivity;
                private final AppController mAppController;
                private final float mAvatarMargin;
                private final SparseArray<Bitmap> mAvatars;
                private Handler mBlurHandler;
                private HandlerThread mBlurHandlerThread;
                private final Runnable mBlurQueueRunnable;
                private Bitmap mBlurTempBitmap;
                private final TypefacesSpan mBoldSpan;
                private Bitmap mCurrentBlurBitmap;
                private ImageView mCurrentBlurImageView;
                private int mCurrentPlaying;
                private final ColoredSpan mDarkGreySpan;
                private final Handler mHandler;
                private final HashMap<Long, Integer> mIdToPositionMap;
                private SdkVideoView mLastPlayer;
                private final ListView mListView;
                private final SparseArray<VinePrivateMessage> mMessages;
                private boolean mMuted;
                private final int mMyColor;
                private long mMyUserId;
                private final SparseArray<String> mPaths;
                private final Runnable mPlayCurrentPositionRunnable;
                private final SparseArray<SdkVideoView> mPlayers;
                private RsBlur mRsBlur;
                private final int mScreenWidth;
                private final SeekInfoManager mSeekInfoManager;
                private final float mSharedDescSize;
                private int mShouldBePlaying;
                private final float mSidePadding;
                private final VineSpanClicker mSpanClicker;
                private final SparseArray<Bitmap> mThumbnails;
                private final HashMap<VideoKey, Integer> mUrlReverse;
                private final HashMap<Long, Long> mVanishedIdDateMap;
                private AttributeSet mVideoAttr;
                private final ArrayList<WeakReference<ConversationViewHolder>> mViewHolders;
                private final SparseArray<PorterDuffColorFilter> mViewedPorterDuffFilterSet;
                private final VineDateFormatter mVineDateFormatter;
                private final ColorDrawable mWaitingBg;

/* 79 */        static /* synthetic */ ConversationActivity access$000(ConversationAdapter x0) {
/* 80 */            return x0.mActivity;
                }

/* 79 */        static /* synthetic */ ListView access$100(ConversationAdapter x0) {
/* 80 */            return x0.mListView;
                }

/* 79 */        static /* synthetic */ Context access$1000(ConversationAdapter x0) {
/* 80 */            return x0.mContext;
                }

/* 79 */        static /* synthetic */ Context access$1100(ConversationAdapter x0) {
/* 80 */            return x0.mContext;
                }

/* 79 */        static /* synthetic */ Bitmap access$1200(ConversationAdapter x0) {
/* 80 */            return x0.mBlurTempBitmap;
                }

/* 79 */        static /* synthetic */ Bitmap access$1202(ConversationAdapter x0, Bitmap x1) {
/* 80 */            x0.mBlurTempBitmap = x1;
                    return x1;
                }

/* 79 */        static /* synthetic */ RsBlur access$1300(ConversationAdapter x0) {
/* 80 */            return x0.mRsBlur;
                }

/* 79 */        static /* synthetic */ RsBlur access$1302(ConversationAdapter x0, RsBlur x1) {
/* 80 */            x0.mRsBlur = x1;
                    return x1;
                }

/* 79 */        static /* synthetic */ Handler access$1400(ConversationAdapter x0) {
/* 80 */            return x0.mHandler;
                }

/* 79 */        static /* synthetic */ SparseArray access$200(ConversationAdapter x0) {
/* 80 */            return x0.mMessages;
                }

/* 79 */        static /* synthetic */ View access$300(ConversationAdapter x0, int x1) {
/* 80 */            return x0.getViewAt(x1);
                }

/* 79 */        static /* synthetic */ int access$400(ConversationAdapter x0) {
/* 80 */            return x0.mCurrentPlaying;
                }

/* 79 */        static /* synthetic */ SeekInfoManager.SeekInfo access$500(ConversationAdapter x0, VideoViewInterface x1, int x2) {
/* 80 */            return x0.getSeekInfoMarkAsRead(x1, x2);
                }

/* 79 */        static /* synthetic */ boolean access$600(ConversationAdapter x0, VideoViewInterface x1, int x2) {
/* 80 */            return x0.onLoopFinished(x1, x2);
                }

/* 79 */        static /* synthetic */ void access$700(ConversationAdapter x0, SdkVideoView x1) {
/* 80 */            x0.releaseOtherPlayers(x1);
                }

/* 79 */        static /* synthetic */ ImageView access$800(ConversationAdapter x0) {
/* 80 */            return x0.mCurrentBlurImageView;
                }

/* 79 */        static /* synthetic */ Bitmap access$900(ConversationAdapter x0) {
/* 80 */            return x0.mCurrentBlurBitmap;
                }

/* 127 */       public ConversationAdapter(ConversationActivity activity, AppController appController, ListView listView, long myUserId, int flags) {
/* 128 */           super(activity, (Cursor) null, flags);
/* 91 */            this.mHandler = new Handler();
/* 92 */            this.mPaths = new SparseArray<>();
/* 93 */            this.mUrlReverse = new HashMap<>();
/* 94 */            this.mViewHolders = new ArrayList<>();
/* 95 */            this.mMessages = new SparseArray<>();
/* 96 */            this.mThumbnails = new SparseArray<>();
/* 97 */            this.mAvatars = new SparseArray<>();
/* 98 */            this.mPlayers = new SparseArray<>();
/* 99 */            this.mVanishedIdDateMap = new HashMap<>();
/* 100 */           this.mIdToPositionMap = new HashMap<>();
/* 101 */           this.mViewedPorterDuffFilterSet = new SparseArray<>();
/* 116 */           this.mCurrentPlaying = Integer.MIN_VALUE;
/* 117 */           this.mShouldBePlaying = Integer.MIN_VALUE;
/* 120 */           this.mWaitingBg = new ColorDrawable(-1);
/* 866 */           this.mPlayCurrentPositionRunnable = new RunnableC01832();
/* 1244 */          this.mBlurQueueRunnable = new RunnableC01876();
/* 129 */           this.mMyUserId = myUserId;
/* 130 */           this.mActivity = activity;
/* 131 */           this.mSpanClicker = new VineSpanClicker(activity);
/* 132 */           this.mSpanClicker.setFlurryEventSource("ConversationAdapter");
/* 133 */           this.mAppController = appController;
/* 134 */           this.mSeekInfoManager = new SeekInfoManager(this.mAppController);
/* 135 */           this.mListView = listView;
/* 136 */           this.mScreenWidth = SystemUtil.getDisplaySize((Activity) activity).x;
/* 137 */           SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
/* 138 */           this.mMyColor = prefs.getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
/* 140 */           Resources res = activity.getResources();
/* 142 */           this.mSharedDescSize = res.getDimension(C0257R.dimen.font_size_default);
/* 144 */           this.mSidePadding = res.getDimension(C0257R.dimen.conversation_side_padding);
/* 146 */           this.mAvatarMargin = res.getDimension(C0257R.dimen.conversation_avatar_size) + res.getDimension(C0257R.dimen.spacing_small);
/* 148 */           this.mBoldSpan = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(1, 3));
/* 151 */           this.mDarkGreySpan = new ColoredSpan(res.getColor(C0257R.color.solid_dark_gray));
/* 153 */           this.mVineDateFormatter = new VineDateFormatter(res);
/* 154 */           this.mVineDateFormatter.refreshDates();
/* 155 */           this.mVineDateFormatter.addSpanForDateSection(this.mBoldSpan);
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 159 */       public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    View v;
/* 160 */           LayoutInflater layoutInflater = LayoutInflater.from(context);
                    boolean isCurrentUser = ((long) cursor.getInt(3)) == this.mMyUserId;
/* 165 */           if (isCurrentUser) {
/* 166 */               v = layoutInflater.inflate(C0257R.layout.message_row_view_me, parent, false);
                    } else {
/* 168 */               v = layoutInflater.inflate(C0257R.layout.message_row_view, parent, false);
                    }
/* 171 */           ConversationViewHolder holder = new ConversationViewHolder(v);
/* 172 */           holder.videoContainer.setVisibility(0);
/* 173 */           holder.timerOval = new ColoredOvalDrawable();
/* 174 */           ViewUtil.setBackground(holder.vanishTimerText, holder.timerOval);
/* 175 */           holder.videoListener = new ConversationVideoClickedListener();
/* 176 */           ViewGroup.LayoutParams params = holder.videoContainer.getLayoutParams();
/* 177 */           params.height = (int) (this.mScreenWidth - (this.mSidePadding * 2.0f));
/* 178 */           params.width = (int) (this.mScreenWidth - (this.mSidePadding * 2.0f));
/* 179 */           holder.videoContainer.setLayoutParams(params);
/* 180 */           ViewGroup.LayoutParams params2 = holder.videoImage.getLayoutParams();
/* 181 */           params2.width = (int) (this.mScreenWidth - (this.mSidePadding * 2.0f));
/* 182 */           params2.height = (int) (this.mScreenWidth - (this.mSidePadding * 2.0f));
/* 183 */           holder.vanishPlayButtonListener = new VanishPlayButtonListener(holder);
/* 184 */           holder.vanishPlayButton.setOnClickListener(holder.vanishPlayButtonListener);
/* 185 */           holder.videoImage.setLayoutParams(params2);
/* 186 */           holder.messageContainerUserImage.bringToFront();
/* 187 */           v.setTag(holder);
/* 188 */           this.mViewHolders.add(new WeakReference<>(holder));
/* 189 */           return v;
                }

/* 192 */       private boolean shouldShowThumbnail(int position) {
                    return (this.mCurrentPlaying == position && hasPlayerPlaying()) ? false : true;
                }

/* 196 */       private void vanishMessage(VinePrivateMessage vpm) {
/* 197 */           vpm.vanished = true;
/* 198 */           vpm.vanishedDate = System.currentTimeMillis();
/* 199 */           this.mVanishedIdDateMap.put(Long.valueOf(vpm.messageId), Long.valueOf(vpm.vanishedDate));
                }

/* 203 */       private boolean isMessageAlreadyVanished(VinePrivateMessage vpm) {
/* 204 */           if (vpm.ephemeral) {
/* 205 */               if (vpm.vanished) {
/* 206 */                   return true;
                        }
/* 208 */               SeekInfoManager.SeekInfo startTime = this.mSeekInfoManager.get(vpm.messageId);
/* 209 */               if (startTime != null) {
                            if (vpm.localStartTime != 0) {
/* 211 */                       startTime.startTime = vpm.localStartTime;
                            }
/* 213 */                   boolean alreadyVanished = startTime.isExpired();
/* 214 */                   if (alreadyVanished) {
/* 215 */                       vanishMessage(vpm);
/* 224 */                       return alreadyVanished;
                            }
/* 224 */                   return alreadyVanished;
                        }
/* 218 */               return false;
                    }
/* 222 */           return false;
                }

/* 227 */       private void setupUserImageProperties(int messagePosition, ImageView userImage, boolean isCurrentUser) {
/* 228 */           userImage.setVisibility(0);
/* 229 */           userImage.setTag(Integer.valueOf(messagePosition));
/* 230 */           userImage.setOnClickListener(this);
/* 231 */           RelativeLayout.LayoutParams imageParams = (RelativeLayout.LayoutParams) userImage.getLayoutParams();
/* 233 */           if (!isCurrentUser) {
/* 234 */               imageParams.addRule(11, 0);
/* 235 */               imageParams.addRule(9);
                    } else {
/* 237 */               imageParams.addRule(9, 0);
/* 238 */               imageParams.addRule(11);
                    }
/* 240 */           userImage.setLayoutParams(imageParams);
                }

/* 244 */       private void setupUserImageImage(VinePrivateMessage vpm, ConversationViewHolder holder, ImageView userImage, boolean isError) {
/* 245 */           if (isError) {
/* 246 */               userImage.setColorFilter((ColorFilter) null);
/* 247 */               userImage.setImageResource(C0257R.drawable.ic_failed_delivery_light);
/* 261 */               return;
                    }
/* 248 */           if (Util.isDefaultAvatarUrl(vpm.avatarUrl)) {
/* 249 */               Util.safeSetDefaultAvatar(userImage, Util.ProfileImageSize.MEDIUM, (-16777216) | holder.color);
/* 261 */               return;
                    }
/* 251 */           userImage.setColorFilter((ColorFilter) null);
/* 252 */           ImageKey avatarKey = new ImageKey(vpm.avatarUrl, true);
/* 253 */           holder.avatarKey = avatarKey;
/* 254 */           Bitmap avatar = this.mAvatars.get(holder.position);
/* 255 */           if (avatar != null) {
/* 256 */               setImage(userImage, avatar, holder);
                    } else {
/* 258 */               setImage(userImage, this.mAppController.getPhotoBitmap(avatarKey), holder);
                    }
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 264 */       public int getItemViewType(int position) {
/* 265 */           Cursor c = (Cursor) getItem(position);
                    return this.mMyUserId == c.getLong(3) ? 1 : 0;
                }

/* 273 */       private VinePrivateMessage initMessage(Cursor cursor) {
/* 274 */           int position = cursor.getPosition();
/* 275 */           VinePrivateMessage vpm = new VinePrivateMessage(cursor);
/* 276 */           this.mMessages.put(position, vpm);
/* 277 */           return vpm;
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 281 */       public int getViewTypeCount() {
/* 282 */           return 2;
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 286 */       public void bindView(View view, Context context, Cursor cursor) {
                    int color;
/* 287 */           int position = cursor.getPosition();
/* 288 */           VinePrivateMessage vpm = initMessage(cursor);
/* 290 */           Resources res = context.getResources();
/* 292 */           View.OnLongClickListener longClickListener = new ViewOnLongClickListenerC01821(vpm);
/* 300 */           ConversationViewHolder holder = (ConversationViewHolder) view.getTag();
/* 302 */           view.setOnLongClickListener(longClickListener);
/* 304 */           holder.videoContainer.setOnLongClickListener(longClickListener);
/* 305 */           holder.avatarKey = null;
/* 306 */           holder.videoImageKey = null;
/* 307 */           holder.position = position;
/* 309 */           if (holder.userImageProgressNormal != null && holder.userImageProgressTop != null) {
/* 310 */               holder.userImageProgressTop.setVisibility(8);
/* 311 */               holder.userImageProgressNormal.setVisibility(8);
                    }
/* 314 */           boolean isCurrentUser = this.mMyUserId == vpm.userId;
/* 316 */           holder.isCurrentUser = isCurrentUser;
/* 318 */           holder.videoListener.setPosition(position);
/* 320 */           invalidateTimestamp(holder, position, cursor, vpm);
/* 323 */           if (isCurrentUser) {
/* 324 */               color = this.mMyColor;
                    } else {
/* 326 */               color = cursor.getInt(25);
                    }
/* 329 */           if (color <= 0) {
/* 330 */               color = Settings.DEFAULT_PROFILE_COLOR;
                    }
/* 333 */           if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
/* 334 */               color = 16777215 & this.mContext.getResources().getColor(C0257R.color.vine_green);
                    }
/* 337 */           holder.color = color;
/* 339 */           boolean isSharedPost = vpm.postId > 0;
/* 341 */           holder.isSharedPost = isSharedPost;
/* 343 */           boolean vanishedEphemeral = isMessageAlreadyVanished(vpm);
/* 346 */           boolean deletedSharedPost = isSharedPost && (vpm.videoUrl == null || vpm.thumbnailUrl == null || vpm.post.username == null);
/* 349 */           boolean showVideoContainer = vpm.hasVideo || (vpm.ephemeral && !vanishedEphemeral) || deletedSharedPost;
/* 352 */           String message = TextUtils.isEmpty(vpm.message) ? "" : vpm.message;
/* 353 */           boolean showSecondaryMessageContainer = vpm.ephemeral && vanishedEphemeral;
/* 355 */           if (showVideoContainer) {
/* 356 */               if (vpm.ephemeral) {
/* 357 */                   holder.vanishThumbnailOverlay.setVisibility(0);
                        } else {
/* 359 */                   holder.vanishThumbnailOverlay.setVisibility(8);
                        }
                    }
/* 363 */           holder.vanishTimerText.setVisibility(8);
/* 365 */           if (deletedSharedPost) {
/* 366 */               holder.videoImage.setScaleType(ImageView.ScaleType.CENTER);
/* 367 */               holder.videoImage.setBackgroundColor(res.getColor(C0257R.color.solid_light_gray));
                    } else {
/* 369 */               holder.videoImage.setScaleType(ImageView.ScaleType.FIT_XY);
/* 370 */               holder.videoImage.setBackgroundColor(res.getColor(android.R.color.transparent));
                    }
/* 373 */           if (vpm.ephemeral) {
/* 374 */               holder.videoContainer.setOnClickListener(null);
/* 375 */               holder.messageContainer.setVisibility(8);
/* 376 */               if (getSeekInfo(holder.position) == null) {
/* 377 */                   holder.vanishMessageContainer.setVisibility(0);
/* 378 */                   holder.vanishMessageOverlay.setText(message);
/* 379 */                   if (isCurrentUser) {
/* 380 */                       holder.vanishPlayButton.setVisibility(8);
                            } else {
/* 382 */                       holder.vanishPlayButton.setVisibility(0);
                            }
                        }
/* 385 */               setupUserImageImage(vpm, holder, holder.vanishUserImage, vpm.errorCode != 0);
/* 386 */               holder.topMessageContainer.setVisibility(8);
                    } else {
/* 388 */               if (deletedSharedPost) {
/* 389 */                   holder.videoContainer.setOnClickListener(null);
/* 390 */                   holder.videoImage.setImageResource(C0257R.drawable.background_sadface);
                        } else {
/* 392 */                   holder.videoContainer.setOnClickListener(holder.videoListener);
/* 393 */                   holder.videoImage.setImageBitmap(null);
                        }
/* 395 */               holder.vanishMessageContainer.setVisibility(8);
/* 396 */               holder.messageContainer.setVisibility(0);
/* 397 */               holder.postMessageContainer.setVisibility(8);
/* 398 */               TextView messageContainerMessage = holder.messageContainerMessage;
/* 399 */               if (isSharedPost) {
/* 400 */                   holder.messageContainer.setVisibility(8);
/* 401 */                   holder.postMessageContainer.setVisibility(0);
/* 402 */                   holder.topMessageContainer.setVisibility(0);
/* 403 */                   setupUserImageProperties(position, holder.topMessageContainerUserImage, isCurrentUser);
/* 404 */                   setupUserImageImage(vpm, holder, holder.topMessageContainerUserImage, vpm.errorCode != 0);
/* 405 */                   holder.topMessageContainerMessage.setGravity((isCurrentUser ? 5 : 3) | 16);
/* 406 */                   RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.topMessageContainerMessage.getLayoutParams();
/* 407 */                   params.rightMargin = isCurrentUser ? (int) this.mAvatarMargin : 0;
/* 408 */                   params.leftMargin = isCurrentUser ? 0 : (int) this.mAvatarMargin;
/* 409 */                   styleTopSharedDescription(vpm.post, isCurrentUser, color, holder.topMessageContainerMessage, deletedSharedPost);
/* 410 */                   styleDescription(holder.postMessageContainerMessage, vpm.post, color, deletedSharedPost);
/* 411 */                   holder.postMessageContainerMessage.setTextColor(-16777216);
/* 412 */                   holder.postMessageContainerMessage.setTextSize(0, this.mSharedDescSize);
/* 413 */                   ViewUtil.setBackground(holder.postMessageContainerMessage, null);
                        } else {
/* 415 */                   messageContainerMessage.setText(message);
/* 416 */                   setupUserImageProperties(position, holder.messageContainerUserImage, isCurrentUser);
/* 417 */                   setupUserImageImage(vpm, holder, holder.messageContainerUserImage, vpm.errorCode != 0);
/* 418 */                   holder.topMessageContainer.setVisibility(8);
/* 419 */                   if (TextUtils.isEmpty(vpm.errorReason)) {
/* 420 */                       if (holder.errorMessage != null) {
/* 421 */                           holder.errorMessage.setVisibility(8);
                                }
/* 423 */                       messageContainerMessage.setVisibility(0);
/* 424 */                       messageContainerMessage.setTextColor((-16777216) | color);
/* 426 */                       if (!TextUtils.isEmpty(message)) {
/* 427 */                           if (isCurrentUser) {
/* 428 */                               ViewUtil.fillColor(res, 855638016 | color, C0257R.drawable.bubble, messageContainerMessage);
/* 430 */                               ViewUtil.fillColor(res, 855638016 | color, C0257R.drawable.vm_nib_me, holder.nibsChatNib);
                                    } else if (this.mMyColor != color) {
/* 434 */                               ViewUtil.fillColor(res, 855638016 | color, C0257R.drawable.bubble, messageContainerMessage);
/* 436 */                               ViewUtil.fillColor(res, 855638016 | color, C0257R.drawable.vm_nib_you, holder.nibsChatNib);
                                    } else {
/* 440 */                               ViewUtil.fillColor(res, (-16777216) | color, C0257R.drawable.bubble, messageContainerMessage);
/* 442 */                               ViewUtil.fillColor(res, (-16777216) | color, C0257R.drawable.vm_nib_you, holder.nibsChatNib);
/* 444 */                               messageContainerMessage.setTextColor(-1);
                                    }
                                } else {
/* 448 */                           messageContainerMessage.setVisibility(8);
                                }
                            } else {
/* 451 */                       if (TextUtils.isEmpty(message)) {
/* 452 */                           messageContainerMessage.setVisibility(8);
                                } else {
/* 454 */                           int grayTextColor = res.getColor(C0257R.color.solid_light_gray_vm);
/* 455 */                           int grayBubbleColor = res.getColor(C0257R.color.solid_light_gray_bubble_vm);
/* 456 */                           messageContainerMessage.setVisibility(0);
/* 457 */                           messageContainerMessage.setTextColor(grayTextColor);
/* 458 */                           ViewUtil.fillColor(res, grayBubbleColor, C0257R.drawable.bubble, messageContainerMessage);
/* 460 */                           ViewUtil.fillColor(res, grayBubbleColor, C0257R.drawable.vm_nib_me, holder.nibsChatNib);
                                }
/* 463 */                       if (holder.errorMessage != null) {
/* 464 */                           holder.errorMessage.setVisibility(0);
/* 465 */                           holder.errorMessage.setText(vpm.errorReason);
                                }
                            }
                        }
                    }
/* 471 */           String thumbnailUrl = deletedSharedPost ? null : vpm.thumbnailUrl;
/* 472 */           holder.hasVideoImage = thumbnailUrl != null;
/* 473 */           if (holder.hasVideoImage) {
/* 474 */               ImageKey videoImageKey = new ImageKey(thumbnailUrl);
/* 475 */               holder.videoImageKey = videoImageKey;
/* 476 */               Bitmap videoThumbnail = this.mThumbnails.get(position);
/* 477 */               if (videoThumbnail != null) {
/* 478 */                   SLog.m447d("setting thumbnail for {}", Integer.valueOf(position));
/* 479 */                   holder.isVideoImageLoaded = setImage(holder.videoImage, videoThumbnail, holder);
                        } else {
/* 481 */                   SLog.m447d("fetching thumbnail for {}", Integer.valueOf(position));
/* 482 */                   Bitmap bitmap = this.mAppController.getPhotoBitmap(videoImageKey);
/* 483 */                   if (bitmap != null) {
/* 484 */                       this.mThumbnails.put(position, bitmap);
                            }
/* 486 */                   holder.isVideoImageLoaded = setImage(holder.videoImage, bitmap, holder);
                        }
/* 488 */               holder.loadImage.setVisibility(8);
                    } else if (vpm.ephemeral || deletedSharedPost) {
/* 491 */               holder.loadImage.setVisibility(8);
                    } else if (!isCurrentUser) {
/* 494 */               holder.loadImage.setVisibility(0);
                    }
/* 499 */           boolean vanishedAlreadyInBindView = false;
/* 501 */           if (showVideoContainer) {
/* 502 */               holder.videoContainer.setVisibility(0);
/* 503 */               holder.nibsChatNib.setVisibility(4);
/* 504 */               if (shouldShowThumbnail(position) && holder.videoView.getPlayingPosition() != position) {
/* 505 */                   holder.videoView.setVisibility(4);
                        }
/* 507 */               initVideoView(position, holder);
/* 508 */               holder.nibs.setVisibility(isSharedPost ? 8 : 0);
/* 509 */               holder.postNibs.setVisibility(isSharedPost ? 0 : 8);
                    } else {
/* 511 */               holder.videoContainer.setVisibility(8);
/* 512 */               holder.nibsChatNib.setVisibility(0);
/* 513 */               holder.videoView.setTag(holder);
/* 514 */               this.mPlayers.remove(position);
/* 515 */               if (vanishedEphemeral) {
/* 516 */                   vanishView(vpm, holder, holder.videoView);
/* 517 */                   vanishedAlreadyInBindView = true;
                        }
                    }
/* 521 */           if (showSecondaryMessageContainer) {
/* 522 */               if (!vanishedAlreadyInBindView) {
/* 523 */                   vanishView(vpm, holder, holder.videoView);
                        }
                    } else {
/* 526 */               holder.secondaryMessageContainer.setVisibility(8);
                    }
/* 529 */           holder.vanishThumbnailOverlay.setBackgroundColor((-771751936) | holder.color);
/* 531 */           if (cursor.getPosition() == cursor.getCount() - 1) {
/* 532 */               holder.bottomPaddingView.setVisibility(0);
                    } else {
/* 534 */               holder.bottomPaddingView.setVisibility(8);
                    }
/* 536 */           holder.videoView.setMute(this.mMuted);
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationAdapter$1 */
/* 290 */       class ViewOnLongClickListenerC01821 implements View.OnLongClickListener {
                    final /* synthetic */ VinePrivateMessage val$vpm;

/* 291 */           ViewOnLongClickListenerC01821(VinePrivateMessage vinePrivateMessage) {
                        this.val$vpm = vinePrivateMessage;
                    }

                    @Override // android.view.View.OnLongClickListener
/* 294 */           public boolean onLongClick(View view) {
/* 295 */               ConversationAdapter.access$000(ConversationAdapter.this).showDeleteMessageDialog(this.val$vpm.messageId);
/* 296 */               return true;
                    }
                }

/* 539 */       private void invalidateTimestamp(ConversationViewHolder holder, int position, Cursor cursor, VinePrivateMessage vpm) {
/* 540 */           boolean shouldShow = false;
/* 542 */           if (position == 0) {
/* 543 */               shouldShow = true;
                    } else {
/* 545 */               VinePrivateMessage previous = this.mMessages.get(position - 1);
/* 546 */               if (previous == null) {
/* 547 */                   if (cursor.moveToPrevious()) {
/* 548 */                       previous = initMessage(cursor);
/* 549 */                       cursor.moveToNext();
                            } else {
/* 551 */                       shouldShow = true;
                            }
                        }
/* 554 */               if (!shouldShow) {
                            shouldShow = vpm.created <= 0 || previous.created <= 0 || Math.abs(vpm.created - previous.created) >= CommonUtil.HOUR_MILLIS;
                        }
                    }
/* 558 */           if (shouldShow) {
/* 559 */               holder.timestamp.setText(this.mVineDateFormatter.format(vpm.created));
/* 560 */               holder.timestamp.setVisibility(0);
                    } else {
/* 562 */               holder.timestamp.setVisibility(8);
                    }
                }

/* 566 */       private void styleTopSharedDescription(VinePost post, boolean isCurrentUser, int color, TextView view, boolean deleted) {
                    String replacer;
/* 567 */           int color2 = color | (-16777216);
/* 569 */           if (deleted || post == null || post.username == null) {
/* 570 */               SpannableStringBuilder sb = new SpannableStringBuilder();
                        sb.append((CharSequence) this.mContext.getString(isCurrentUser ? C0257R.string.share_vm_sender_deleted : C0257R.string.share_vm_receiver_deleted));
/* 573 */               Util.safeSetSpan(sb, this.mDarkGreySpan, 0, sb.length(), 33);
/* 574 */               view.setText(sb);
/* 575 */               if (post != null && post.username == null) {
/* 576 */                   CrashUtil.logException(new IllegalArgumentException("Username is null: " + post.postId));
/* 627 */                   return;
                        }
/* 627 */               return;
                    }
/* 579 */           if (post.sharedVmSb == null) {
/* 580 */               String string = this.mContext.getString(isCurrentUser ? C0257R.string.share_vm_sender_text : C0257R.string.share_vm_receiver_text);
/* 583 */               post.sharedVmSb = new SpannableStringBuilder();
/* 584 */               SpannableStringBuilder sharedVmSb = post.sharedVmSb;
/* 586 */               int start = string.indexOf("{");
/* 588 */               if (start > 0) {
/* 589 */                   sharedVmSb.append((CharSequence) string.substring(0, start));
                        }
/* 594 */               int index = string.indexOf("}");
/* 596 */               if (index < 0) {
/* 597 */                   string = string + "}";
                        }
                        try {
/* 601 */                   replacer = string.substring(start + 1, string.indexOf("}"));
                        } catch (Exception e) {
/* 603 */                   CrashUtil.logException(e, "Failed top post span thing. locale: {}", Util.getLocale());
/* 604 */                   replacer = "%s";
                        }
/* 607 */               sharedVmSb.append((CharSequence) replacer.replace("%s", post.username));
/* 609 */               int end = sharedVmSb.length();
/* 611 */               int endOfReplacer = string.indexOf("}");
/* 613 */               if (endOfReplacer < string.length() - 1) {
/* 614 */                   sharedVmSb.append((CharSequence) string.substring(endOfReplacer + 1));
                        }
/* 617 */               StyledClickableSpan span = new StyledClickableSpan(9, Long.valueOf(post.postId), this.mSpanClicker);
/* 619 */               span.setColor(color2);
/* 620 */               Util.safeSetSpan(sharedVmSb, span, start, end, 33);
/* 621 */               Util.safeSetSpan(sharedVmSb, this.mBoldSpan, start, end, 33);
/* 622 */               Util.safeSetSpan(sharedVmSb, this.mDarkGreySpan, 0, start, 33);
                    }
/* 624 */           view.setMovementMethod(LinkMovementMethod.getInstance());
/* 625 */           view.setText(post.sharedVmSb);
                }

/* 629 */       private void styleDescription(TextView textView, VinePost post, int color, boolean deletedSharedPost) {
/* 630 */           int color2 = color | (-16777216);
/* 631 */           if (post != null && post.transientEntities == null && post.entities != null) {
/* 632 */               post.transientEntities = new ArrayList<>();
/* 633 */               Iterator<VineEntity> it = post.entities.iterator();
                        while (it.hasNext()) {
/* 634 */                   post.transientEntities.add(it.next().duplicate());
                        }
                    }
/* 638 */           ArrayList<VineEntity> postEntities = post != null ? post.transientEntities : null;
/* 639 */           if (postEntities != null) {
/* 640 */               textView.setMovementMethod(LinkMovementMethod.getInstance());
/* 641 */               if (post.descriptionSb == null && post.description != null) {
/* 642 */                   post.descriptionSb = new SpannableStringBuilder(post.description);
/* 643 */                   SpannableStringBuilder descriptionSb = post.descriptionSb;
                            try {
/* 645 */                       Util.adjustEntities(postEntities, descriptionSb, 0, false);
                                for (VineEntity entity : postEntities) {
/* 648 */                           if (entity.isUserType()) {
/* 649 */                               StyledClickableSpan usernameSpan = new StyledClickableSpan(1, Long.valueOf(entity.f96id), this.mSpanClicker);
/* 652 */                               usernameSpan.setColor(color2);
/* 653 */                               Util.safeSetSpan(descriptionSb, usernameSpan, entity.start, entity.end, 33);
                                    } else if (entity.isTagType()) {
/* 656 */                               StyledClickableSpan usernameSpan2 = new StyledClickableSpan(4, entity.title, this.mSpanClicker);
/* 659 */                               usernameSpan2.setColor(color2);
/* 660 */                               Util.safeSetSpan(descriptionSb, usernameSpan2, entity.start, entity.end, 33);
                                    }
                                }
                            } catch (Exception e) {
/* 665 */                       CrashUtil.logException(e);
                            }
                        }
/* 668 */               textView.setText(post.descriptionSb);
/* 678 */               return;
                    }
/* 670 */           if (deletedSharedPost || post == null) {
/* 671 */               SpannableStringBuilder sb = new SpannableStringBuilder(this.mActivity.getString(C0257R.string.share_vm_deleted));
/* 672 */               Util.safeSetSpan(sb, this.mDarkGreySpan, 0, sb.length(), 33);
/* 673 */               textView.setText(sb);
/* 678 */               return;
                    }
/* 675 */           textView.setText(post.description);
                }

/* 680 */       public long getIdAtLastPosition() {
/* 681 */           Cursor cursor = getCursor();
/* 682 */           if (cursor == null || !cursor.moveToLast()) {
                        return -1L;
                    }
/* 684 */           return cursor.getLong(3);
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 691 */       public synchronized void getVideoPathAndPlayForPosition(int requestedPosition, boolean forceLowKey) {
                    ConversationViewHolder tag;
                    try {
                        SLog.m447d("Play {} requested.", Integer.valueOf(requestedPosition));
/* 693 */               if (this.mPaths.get(requestedPosition) != null) {
/* 694 */                   playFile(requestedPosition, true);
                        } else if (this.mMessages.get(requestedPosition) != null) {
/* 696 */                   VinePrivateMessage vpm = this.mMessages.get(requestedPosition);
/* 697 */                   VideoKey videoKey = new VideoKey(vpm.videoUrl);
/* 698 */                   String path = this.mAppController.getVideoFilePath(videoKey);
/* 699 */                   if (path != null) {
/* 700 */                       this.mPaths.put(requestedPosition, path);
/* 701 */                       SLog.m447d("playing file at position {}", Integer.valueOf(requestedPosition));
/* 702 */                       playFile(requestedPosition, true);
                            } else {
/* 704 */                       View currentView = getViewAt(requestedPosition);
/* 705 */                       if (currentView != null && (tag = (ConversationViewHolder) currentView.getTag()) != null) {
/* 708 */                           if (tag.vanishMessageContainer.getVisibility() != 0) {
/* 711 */                               tag.loadImage.setVisibility(0);
/* 715 */                               this.mShouldBePlaying = requestedPosition;
/* 716 */                               this.mUrlReverse.put(videoKey, Integer.valueOf(requestedPosition));
                                    }
                                } else {
/* 715 */                           this.mShouldBePlaying = requestedPosition;
/* 716 */                           this.mUrlReverse.put(videoKey, Integer.valueOf(requestedPosition));
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 722 */       public void playFile(int position, boolean isVideoLoaded) {
                    SdkVideoView currentVideoView;
/* 723 */           if (position >= 0) {
/* 726 */               View currentView = getViewAt(position);
/* 727 */               if (currentView == null) {
/* 728 */                   SLog.m465i("Item is not visible: {}", Integer.valueOf(position));
/* 775 */                   return;
                        }
/* 731 */               ConversationViewHolder holder = (ConversationViewHolder) currentView.getTag();
/* 732 */               if (holder != null) {
/* 736 */                   if (holder.vanishMessageContainer.getVisibility() == 0) {
/* 737 */                       SLog.m446d("No AutoPlay for Ephemeral.");
/* 775 */                       return;
                            }
/* 741 */                   String newPath = this.mPaths.get(position);
/* 742 */                   String oldPath = holder.videoView.getPath();
/* 743 */                   if (newPath != null) {
/* 744 */                       pauseCurrentPlayer();
/* 746 */                       if (!newPath.equals(oldPath)) {
/* 747 */                           refreshVideoView(position, holder);
/* 748 */                           currentVideoView = holder.videoView;
/* 749 */                           currentVideoView.setVideoPath(newPath);
/* 750 */                           currentVideoView.setPlayingPosition(position);
/* 751 */                           currentVideoView.setTag(holder);
/* 752 */                           holder.videoListener.setPosition(position);
                                } else {
/* 754 */                           currentVideoView = holder.videoView;
/* 756 */                           VinePrivateMessage message = this.mMessages.get(position);
/* 757 */                           if (message != null && !message.ephemeral && currentVideoView.getVisibility() != 0) {
/* 760 */                               currentVideoView.setVisibility(0);
                                    }
/* 764 */                           currentVideoView.setPlayingPosition(position);
/* 765 */                           if (currentVideoView.isInPlaybackState() && currentVideoView.isPathPlaying(newPath)) {
/* 766 */                               currentVideoView.start();
                                    } else {
/* 768 */                               currentVideoView.setVideoPath(newPath);
                                    }
                                }
/* 771 */                       this.mCurrentPlaying = position;
/* 772 */                       this.mLastPlayer = currentVideoView;
/* 773 */                       currentVideoView.setMute(this.mMuted);
                            }
                        }
                    }
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 778 */       public void pausePlayer(SdkVideoView player) {
/* 779 */           player.pause();
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 783 */       public void resumePlayer(SdkVideoView player) {
/* 784 */           player.resume();
                }

/* 787 */       private void refreshVideoView(int position, ConversationViewHolder tag) {
/* 788 */           ViewGroup group = (ViewGroup) tag.videoView.getParent();
/* 789 */           if (this.mVideoAttr == null) {
/* 790 */               this.mVideoAttr = tag.videoView.getAttributes();
                    }
/* 792 */           tag.videoView.suspend();
                    try {
/* 794 */               group.removeView(tag.videoView);
                    } catch (RuntimeException e) {
                        try {
/* 797 */                   group.removeView(tag.videoView);
                        } catch (Exception e2) {
/* 799 */                   CrashUtil.logException(e, "Weird things are happening.", new Object[0]);
                        }
                    }
/* 802 */           tag.videoView = new SdkVideoView(this.mContext, this.mVideoAttr);
/* 803 */           SeekInfoManager.SeekInfo seekInfo = getSeekInfo(position);
/* 804 */           if (!tag.isCurrentUser && seekInfo != null && seekInfo.isExpired()) {
/* 805 */               tag.videoView.setVisibility(4);
                    }
/* 807 */           group.addView(tag.videoView);
/* 808 */           initVideoView(position, tag);
                }

/* 812 */       public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
                    for (VideoKey key : videos.keySet()) {
/* 814 */               UrlVideo video = videos.get(key);
/* 815 */               if (video.isValid() && this.mUrlReverse.get(key) != null) {
/* 816 */                   this.mPaths.put(this.mUrlReverse.get(key).intValue(), video.getAbsolutePath());
/* 817 */                   if (this.mUrlReverse.get(key).intValue() == this.mShouldBePlaying) {
/* 818 */                       playCurrentPosition();
                            }
                        }
                    }
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 824 */       public synchronized void playCurrentPosition() {
                    try {
                        this.mHandler.removeCallbacks(this.mPlayCurrentPositionRunnable);
/* 826 */               this.mHandler.postDelayed(this.mPlayCurrentPositionRunnable, 50L);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 830 */       public SdkVideoView getPlayer(int position, boolean b) {
/* 831 */           return this.mPlayers.get(position);
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 835 */       public void pauseCurrentPlayer() {
/* 836 */           if (hasPlayerPlaying()) {
/* 837 */               this.mLastPlayer.pause();
                    }
                }

/* 841 */       private boolean hasPlayerPlaying() {
                    return (this.mLastPlayer == null || this.mLastPlayer.isPaused()) ? false : true;
                }

/* 845 */       public void stopCurrentPlayer() {
/* 846 */           if (this.mLastPlayer != null) {
/* 847 */               this.mLastPlayer.suspend();
/* 848 */               this.mLastPlayer = null;
/* 849 */               this.mCurrentPlaying = -1;
                    }
                }

/* 853 */       private View getViewAt(int position) {
/* 854 */           ListView listView = this.mListView;
/* 855 */           int firstPosition = (listView.getFirstVisiblePosition() - listView.getHeaderViewsCount()) - 0;
/* 857 */           int wantedChild = position - firstPosition;
/* 858 */           if (wantedChild >= 0 && wantedChild < listView.getChildCount()) {
                        return listView.getChildAt(wantedChild);
                    }
/* 859 */           SLog.m481w("Unable to get view for desired position, because it's not being displayed on screen: {} {} {}.", new Object[]{Integer.valueOf(wantedChild), Integer.valueOf(firstPosition), Integer.valueOf(listView.getChildCount())});
/* 860 */           return null;
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationAdapter$2 */
/* 864 */       class RunnableC01832 implements Runnable {
/* 865 */           RunnableC01832() {
                    }

                    @Override // java.lang.Runnable
/* 868 */           public synchronized void run() {
                        VinePrivateMessage next;
                        try {
                            ListView listView = ConversationAdapter.access$100(ConversationAdapter.this);
/* 870 */                   int currentFirst = listView.getFirstVisiblePosition();
/* 871 */                   VinePrivateMessage msg = (VinePrivateMessage) ConversationAdapter.access$200(ConversationAdapter.this).get(currentFirst);
/* 872 */                   boolean currentHasVideo = msg != null && msg.hasVideo;
/* 873 */                   View currentView = ConversationAdapter.access$300(ConversationAdapter.this, currentFirst);
/* 874 */                   float ratio = -1.0f;
/* 875 */                   if (currentView != null) {
/* 876 */                       int currentlyPlayingOffset = 0;
/* 877 */                       if (ConversationAdapter.this.getLastPlayer() != null) {
/* 878 */                           currentlyPlayingOffset = ConversationAdapter.this.getLastPlayer().getHeight() / 2;
                                }
/* 880 */                       float bottom = currentView.getBottom() + currentlyPlayingOffset;
/* 881 */                       float height = currentView.getHeight() + currentlyPlayingOffset;
/* 882 */                       ratio = bottom / height;
                            }
/* 884 */                   SLog.m448d("Current First: {}, ratio: {}", Integer.valueOf(currentFirst), Float.valueOf(ratio));
/* 885 */                   int currentNext = -1;
                            if (ratio < 0.5d || currentFirst == -1 || !currentHasVideo) {
/* 889 */                       for (int nextPlayer = currentFirst + 1; nextPlayer < ConversationAdapter.this.getCount() && (next = (VinePrivateMessage) ConversationAdapter.access$200(ConversationAdapter.this).get(nextPlayer)) != null; nextPlayer++) {
/* 890 */                           if (next.hasVideo) {
/* 891 */                               currentNext = nextPlayer;
                                        break;
                                    }
                                }
/* 899 */                       SLog.m447d("Current Next: {}", Integer.valueOf(currentNext));
/* 900 */                       if (currentNext != -1 && currentNext != ConversationAdapter.access$400(ConversationAdapter.this)) {
/* 901 */                           ConversationAdapter.this.getVideoPathAndPlayForPosition(currentNext, false);
                                }
                            } else if (currentFirst != ConversationAdapter.access$400(ConversationAdapter.this)) {
/* 904 */                       ConversationAdapter.this.getVideoPathAndPlayForPosition(currentFirst, false);
                            } else {
/* 906 */                       ConversationAdapter.this.playFile(currentFirst, false);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 911 */       public void onResume() {
/* 912 */           this.mBlurHandlerThread = new HandlerThread("blur-thread");
/* 913 */           this.mBlurHandlerThread.start();
/* 914 */           this.mBlurHandler = new Handler(this.mBlurHandlerThread.getLooper());
/* 915 */           this.mMuted = MuteUtil.isMuted(this.mContext);
/* 916 */           playCurrentPosition();
/* 917 */           this.mVineDateFormatter.refreshDates();
                }

/* 920 */       public void onPause() {
                    VinePrivateMessage message;
/* 921 */           if (this.mBlurHandlerThread != null) {
/* 922 */               Looper looper = this.mBlurHandlerThread.getLooper();
/* 923 */               if (looper != null) {
/* 924 */                   looper.quit();
                        }
/* 926 */               this.mBlurHandlerThread = null;
                    }
/* 928 */           if (this.mLastPlayer != null && this.mLastPlayer.hasStarted() && this.mCurrentPlaying > 0 && (message = this.mMessages.get(this.mCurrentPlaying)) != null && message.userId != this.mAppController.getActiveId() && message.ephemeral && this.mSeekInfoManager.get(message.messageId) != null) {
/* 932 */               SLog.m465i("Vanishing onPause for {}.", Long.valueOf(message.messageId));
/* 933 */               vanishMessage(message);
                    }
/* 937 */           stopCurrentPlayer();
/* 938 */           releaseOtherPlayers(null);
                }

/* 941 */       private void initVideoView(int position, ConversationViewHolder holder) {
/* 942 */           SdkVideoView view = holder.videoView;
/* 943 */           view.setAutoShow(true);
/* 944 */           view.setOnPreparedListener(new C01843(holder, position));
/* 975 */           view.setOnErrorListener(new C01854(view));
/* 984 */           view.setOnCompletionListener(new C01865(view, position));
/* 994 */           this.mPlayers.put(position, view);
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationAdapter$3 */
/* 942 */       class C01843 implements VideoViewInterface.OnPreparedListener {
                    final /* synthetic */ ConversationViewHolder val$holder;
                    final /* synthetic */ int val$position;

/* 943 */           C01843(ConversationViewHolder conversationViewHolder, int i) {
                        this.val$holder = conversationViewHolder;
                        this.val$position = i;
                    }

                    @Override // co.vine.android.player.VideoViewInterface.OnPreparedListener
/* 946 */           public void onPrepared(VideoViewInterface view) {
/* 947 */               if (this.val$holder.isCurrentUser) {
/* 948 */                   view.start();
/* 949 */                   ConversationAdapter.this.onLoadFinish(this.val$holder);
                        } else {
/* 951 */                   SeekInfoManager.SeekInfo seek = ConversationAdapter.access$500(ConversationAdapter.this, view, this.val$position);
/* 952 */                   if (seek == null) {
/* 953 */                       CrashUtil.log("Invalid state onPrepared.");
                            } else if (!seek.isExpired()) {
/* 956 */                       if (seek.hasLoopLimit() && !seek.isExpired()) {
/* 957 */                           view.seekTo(seek.getSeekTime());
/* 958 */                           this.val$holder.vanishTimerText.setVisibility(0);
/* 959 */                           this.val$holder.vanishTimerText.setText(seek.getLoopsLeftForDisplay());
                                }
/* 961 */                       if (!seek.isExpired()) {
/* 962 */                           view.start();
/* 963 */                           ConversationAdapter.this.onLoadFinish(this.val$holder);
                                }
                            } else {
/* 966 */                       ConversationAdapter.access$600(ConversationAdapter.this, view, this.val$position);
                            }
                        }
/* 971 */               this.val$holder.nibs.bringToFront();
/* 972 */               this.val$holder.postNibs.bringToFront();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationAdapter$4 */
/* 973 */       class C01854 implements VideoViewInterface.OnErrorListener {
                    final /* synthetic */ SdkVideoView val$view;

/* 974 */           C01854(SdkVideoView sdkVideoView) {
                        this.val$view = sdkVideoView;
                    }

                    @Override // co.vine.android.player.VideoViewInterface.OnErrorListener
/* 977 */           public boolean onError(VideoViewInterface videoView, int what, int extra) {
/* 978 */               SLog.m448d("Got error, try recycling it more aggressively: {}, {}", Integer.valueOf(what), Integer.valueOf(extra));
/* 979 */               ConversationAdapter.access$700(ConversationAdapter.this, this.val$view);
/* 980 */               this.val$view.retryOpenVideo(false);
/* 981 */               return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationAdapter$5 */
/* 982 */       class C01865 implements VideoViewInterface.OnCompletionListener {
                    final /* synthetic */ int val$position;
                    final /* synthetic */ SdkVideoView val$view;

/* 983 */           C01865(SdkVideoView sdkVideoView, int i) {
                        this.val$view = sdkVideoView;
                        this.val$position = i;
                    }

                    @Override // co.vine.android.player.VideoViewInterface.OnCompletionListener
/* 986 */           public void onCompletion(VideoViewInterface videoView) {
/* 987 */               if (ConversationAdapter.access$600(ConversationAdapter.this, this.val$view, this.val$position)) {
/* 988 */                   videoView.seekTo(0);
/* 989 */                   videoView.start();
                        }
                    }
                }

/* 1001 */      private SeekInfoManager.SeekInfo getSeekInfo(int position) {
/* 1002 */          VinePrivateMessage message = this.mMessages.get(position);
/* 1003 */          if (message == null) {
                        return null;
                    }
/* 1004 */          if (!message.ephemeral) {
/* 1005 */              return SeekInfoManager.NO_LOOP_LIMIT;
                    }
/* 1007 */          return this.mSeekInfoManager.getSeekInfo(null, message);
                }

/* 1014 */      private SeekInfoManager.SeekInfo getSeekInfoMarkAsRead(VideoViewInterface view, int position) {
/* 1015 */          VinePrivateMessage message = this.mMessages.get(position);
/* 1016 */          if (message == null) {
                        return null;
                    }
/* 1017 */          if (!message.ephemeral) {
/* 1018 */              return SeekInfoManager.NO_LOOP_LIMIT;
                    }
/* 1020 */          SeekInfoManager.SeekInfo seekInfo = this.mSeekInfoManager.getSeekInfo(null, message);
/* 1021 */          if (seekInfo == null) {
/* 1022 */              return this.mSeekInfoManager.markAsRead(view.getDuration(), message);
                    }
/* 1028 */          return seekInfo;
                }

/* 1032 */      private boolean onLoopFinished(VideoViewInterface view, int position) {
                    boolean haveLoopsLeft = true;
/* 1033 */          VinePrivateMessage message = this.mMessages.get(position);
/* 1034 */          if (message != null && message.userId != this.mAppController.getActiveId() && message.ephemeral) {
/* 1035 */              SeekInfoManager.SeekInfo seekInfo = this.mSeekInfoManager.getSeekInfo(view, message);
/* 1036 */              SLog.m448d("{} just looped, seekInfo {}", Long.valueOf(message.messageId), seekInfo);
/* 1037 */              if (seekInfo != null && seekInfo.isExpired()) {
/* 1037 */                  haveLoopsLeft = false;
                        }
/* 1038 */              if (haveLoopsLeft) {
/* 1039 */                  if (seekInfo != null) {
/* 1040 */                      ((ConversationViewHolder) view.getTag()).vanishTimerText.setText(seekInfo.getLoopsLeftForDisplay());
                            }
                        } else {
/* 1043 */                  vanishMessage(message);
/* 1044 */                  this.mAppController.removeFromVideoCache(new VideoKey(message.videoUrl));
/* 1045 */                  vanishView(message, (ConversationViewHolder) view.getTag(), view);
                        }
                    } else if (message == null) {
/* 1050 */              CrashUtil.log("Message was null, limited loop function not working.");
                    }
/* 1052 */          return haveLoopsLeft;
                }

/* 1056 */      private void vanishView(VinePrivateMessage vpm, ConversationViewHolder holder, VideoViewInterface view) {
/* 1057 */          if (view.isInPlaybackState()) {
/* 1058 */              view.stopPlayback();
/* 1059 */              view.setVideoPath(null);
                    }
/* 1061 */          if (holder.videoContainer.getVisibility() != 8) {
/* 1062 */              holder.videoContainer.setVisibility(8);
                    }
/* 1065 */          if (holder.vanishTimerText.getVisibility() != 8) {
/* 1066 */              holder.vanishTimerText.setVisibility(8);
                    }
/* 1069 */          int backgroundColor = 1610612736 | holder.color;
/* 1071 */          PorterDuffColorFilter filter = this.mViewedPorterDuffFilterSet.get(backgroundColor);
/* 1072 */          if (filter == null) {
/* 1073 */              filter = new PorterDuffColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN);
/* 1074 */              this.mViewedPorterDuffFilterSet.put(backgroundColor, filter);
                    }
/* 1076 */          holder.secondaryMessageContainer.getBackground().setColorFilter(filter);
/* 1077 */          holder.secondaryMessageContainer.setVisibility(0);
/* 1079 */          if (vpm.vanishedViewedSb == null) {
/* 1080 */              vpm.vanishedViewedSb = new SpannableStringBuilder();
/* 1082 */              Long memVanishedDate = this.mVanishedIdDateMap.get(Long.valueOf(vpm.messageId));
/* 1084 */              Date vanishedDate = new Date(memVanishedDate != null ? memVanishedDate.longValue() : vpm.vanishedDate);
/* 1086 */              String viewedString = this.mActivity.getString(C0257R.string.viewed);
/* 1087 */              String dateString = DateFormat.format("hh:mm aa", vanishedDate).toString();
/* 1089 */              int start = viewedString.indexOf("%s");
/* 1090 */              int end = start + dateString.length();
/* 1091 */              vpm.vanishedViewedSb.append((CharSequence) viewedString.replace("%s", dateString));
/* 1092 */              Util.safeSetSpan(vpm.vanishedViewedSb, new ColoredSpan((-16777216) | holder.color), 0, end, 33);
/* 1093 */              Util.safeSetSpan(vpm.vanishedViewedSb, this.mBoldSpan, start, end, 33);
                    }
/* 1095 */          holder.secondaryMessageContainerMessage.setText(vpm.vanishedViewedSb);
                }

/* 1099 */      private void releaseOtherPlayers(SdkVideoView view) {
/* 1100 */          int viewKey = -1;
                    for (int i = 0; i < this.mPlayers.size(); i++) {
/* 1102 */              int key = this.mPlayers.keyAt(i);
/* 1103 */              SdkVideoView v = this.mPlayers.get(key);
/* 1104 */              if (v != view) {
/* 1105 */                  v.suspend();
                        } else {
/* 1107 */                  viewKey = key;
                        }
                    }
/* 1110 */          this.mPlayers.clear();
/* 1111 */          if (view != null) {
/* 1112 */              if (viewKey >= 0) {
/* 1113 */                  this.mPlayers.put(viewKey, view);
                        } else {
/* 1117 */                  CrashUtil.logException(new RuntimeException("Invalid state in conversation adapter. Was seeing this a lot coming back from full record. Commented out the runtime and am just logging"));
                        }
                    }
                }

/* 1124 */      void onLoadFinish(ConversationViewHolder holder) {
/* 1125 */          VinePrivateMessage message = this.mMessages.get(holder.position);
/* 1126 */          if (message != null && holder.videoView.getVisibility() != 0) {
/* 1128 */              holder.videoView.setVisibility(0);
                    }
                }

                @Override // co.vine.android.player.HasVideoPlayerAdapter
/* 1134 */      public SdkVideoView getLastPlayer() {
/* 1135 */          return this.mLastPlayer;
                }

/* 1138 */      private boolean setImage(ImageView imageView, Bitmap bitmap, ConversationViewHolder holder) {
                    VinePrivateMessage message;
/* 1139 */          int position = holder.position;
/* 1140 */          if (bitmap == null) {
/* 1141 */              imageView.setBackgroundColor(this.mContext.getResources().getColor(C0257R.color.solid_light_gray));
/* 1142 */              imageView.setImageBitmap(null);
/* 1143 */              return false;
                    }
/* 1145 */          boolean setBlurredImage = false;
/* 1146 */          if (imageView == holder.videoImage && bitmap.getWidth() == 480 && bitmap.getHeight() == 480 && (message = this.mMessages.get(position)) != null && message.ephemeral) {
/* 1151 */              SeekInfoManager.SeekInfo seek = getSeekInfo(position);
/* 1152 */              if (seek == null) {
/* 1153 */                  setBlurredImage = true;
                        }
                    }
/* 1158 */          if (setBlurredImage) {
/* 1159 */              imageView.setBackgroundColor(this.mContext.getResources().getColor(C0257R.color.solid_white));
/* 1160 */              imageView.setImageDrawable(this.mWaitingBg);
/* 1161 */              this.mCurrentBlurBitmap = bitmap;
/* 1162 */              this.mCurrentBlurImageView = imageView;
/* 1163 */              this.mBlurHandler.removeCallbacks(this.mBlurQueueRunnable);
/* 1164 */              this.mBlurHandler.post(this.mBlurQueueRunnable);
                    } else {
/* 1166 */              imageView.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bitmap));
/* 1167 */              imageView.setBackgroundColor(this.mContext.getResources().getColor(android.R.color.transparent));
                    }
/* 1170 */          return true;
                }

/* 1174 */      public void setImages(HashMap<ImageKey, UrlImage> images) {
                    UrlImage image;
                    UrlImage image2;
/* 1175 */          ArrayList<WeakReference<ConversationViewHolder>> toRemove = new ArrayList<>();
                    for (WeakReference<ConversationViewHolder> ref : this.mViewHolders) {
/* 1178 */              ConversationViewHolder holder = ref.get();
/* 1179 */              if (holder == null) {
/* 1180 */                  toRemove.add(ref);
                        } else {
/* 1182 */                  if (holder.avatarKey != null && (image2 = images.get(holder.avatarKey)) != null && image2.isValid()) {
/* 1185 */                      if (holder.isSharedPost) {
/* 1186 */                          setImage(holder.topMessageContainerUserImage, image2.bitmap, holder);
                                } else {
/* 1188 */                          setImage(holder.messageContainerUserImage, image2.bitmap, holder);
/* 1189 */                          setImage(holder.vanishUserImage, image2.bitmap, holder);
                                }
                            }
/* 1193 */                  if (holder.videoImageKey != null && (image = images.get(holder.videoImageKey)) != null && image.isValid()) {
/* 1196 */                      holder.isVideoImageLoaded = setImage(holder.videoImage, image.bitmap, holder);
/* 1197 */                      this.mThumbnails.put(holder.videoListener.getPosition(), image.bitmap);
/* 1198 */                      SLog.m447d("Image found: {}.", Integer.valueOf(holder.videoListener.getPosition()));
/* 1199 */                      playCurrentPosition();
                            }
                        }
                    }
                    for (WeakReference<ConversationViewHolder> h : toRemove) {
/* 1205 */              this.mViewHolders.remove(h);
                    }
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 1210 */      public Cursor swapCursor(Cursor c) {
/* 1211 */          Cursor oldCursor = super.swapCursor(c);
/* 1212 */          this.mIdToPositionMap.clear();
/* 1213 */          this.mThumbnails.clear();
/* 1214 */          this.mPaths.clear();
/* 1215 */          if (c != null && c.moveToFirst()) {
/* 1219 */              do {
/* 1217 */                  this.mIdToPositionMap.put(Long.valueOf(c.getLong(0)), Integer.valueOf(c.getPosition()));
/* 1219 */              } while (c.moveToNext());
                    }
/* 1221 */          return oldCursor;
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 1225 */      public long getItemId(int pos) {
/* 1226 */          if (this.mCursor == null || !this.mCursor.moveToPosition(pos)) {
                        return -1L;
                    }
/* 1227 */          return this.mCursor.getLong(0);
                }

/* 1232 */      public boolean atLastPage() {
                    return this.mCursor != null && this.mCursor.moveToFirst() && this.mCursor.getInt(8) == 1;
                }

/* 1237 */      public int getPositionForId(long id) {
/* 1238 */          if (this.mIdToPositionMap.containsKey(Long.valueOf(id))) {
/* 1239 */              return this.mIdToPositionMap.get(Long.valueOf(id)).intValue();
                    }
                    return -1;
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationAdapter$6 */
/* 1242 */      class RunnableC01876 implements Runnable {
/* 1243 */          RunnableC01876() {
                    }

                    @Override // java.lang.Runnable
/* 1247 */          public void run() {
/* 1248 */              if (ConversationAdapter.access$800(ConversationAdapter.this) != null && ConversationAdapter.access$900(ConversationAdapter.this) != null) {
/* 1249 */                  ConversationAdapter.this.new BlurRunnable(ConversationAdapter.access$900(ConversationAdapter.this), ConversationAdapter.access$800(ConversationAdapter.this)).run();
                        }
                    }
                }

                @Override // android.view.View.OnClickListener
/* 1255 */      public void onClick(View v) {
/* 1256 */          Object tag = v.getTag();
/* 1257 */          if (tag instanceof Integer) {
/* 1258 */              VinePrivateMessage vpm = this.mMessages.get(((Integer) tag).intValue());
/* 1259 */              Runnable retryRunnable = new RunnableC01887(vpm, v);
/* 1280 */              if (vpm.errorCode == 616) {
/* 1281 */                  this.mActivity.startPhoneConfirmation(retryRunnable);
                        } else if (vpm.errorCode != 0) {
/* 1284 */                  retryRunnable.run();
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ConversationAdapter$7 */
/* 1257 */      class RunnableC01887 implements Runnable {
                    final /* synthetic */ View val$v;
                    final /* synthetic */ VinePrivateMessage val$vpm;

/* 1258 */          RunnableC01887(VinePrivateMessage vinePrivateMessage, View view) {
                        this.val$vpm = vinePrivateMessage;
                        this.val$v = view;
                    }

                    @Override // java.lang.Runnable
/* 1262 */          public void run() {
/* 1263 */              ConversationAdapter.access$1100(ConversationAdapter.this).startService(VineUploadService.getVMPostIntent(ConversationAdapter.access$1000(ConversationAdapter.this), this.val$vpm.uploadPath, true, this.val$vpm.messageRowId, this.val$vpm.conversationRowId, null, this.val$vpm.message, this.val$vpm.postId, this.val$vpm.videoUrl, this.val$vpm.thumbnailUrl, this.val$vpm.maxLoops));
/* 1266 */              ViewGroup parent = (ViewGroup) this.val$v.getParent();
/* 1267 */              View progress = parent.findViewById(C0257R.id.retry_progress_normal);
/* 1269 */              if (progress == null) {
/* 1270 */                  progress = parent.findViewById(C0257R.id.retry_progress_top);
                        }
/* 1273 */              if (progress != null) {
/* 1274 */                  progress.setVisibility(0);
/* 1275 */                  this.val$v.setVisibility(4);
                        }
                    }
                }

/* 1294 */      public class BlurRunnable implements Runnable {
                    private final Bitmap bitmap;
                    private final ImageView imageView;
                    private boolean setImage;

/* 1295 */          public BlurRunnable(Bitmap bitmap, ImageView imageView) {
/* 1297 */              this.bitmap = bitmap;
/* 1298 */              this.imageView = imageView;
                    }

                    @Override // java.lang.Runnable
/* 1302 */          public void run() {
/* 1303 */              if (ConversationAdapter.access$1200(ConversationAdapter.this) == null) {
/* 1304 */                  ConversationAdapter.access$1202(ConversationAdapter.this, RecordConfigUtils.createDefaultSizeBitmap());
                        }
/* 1306 */              if (ConversationAdapter.access$1300(ConversationAdapter.this) == null) {
/* 1307 */                  ConversationAdapter.access$1302(ConversationAdapter.this, new RsBlur(RenderScript.create(ConversationAdapter.access$000(ConversationAdapter.this)), ConversationAdapter.access$1200(ConversationAdapter.this), 25.0f));
                        }
/* 1309 */              if (this.setImage) {
/* 1310 */                  this.imageView.setImageBitmap(ConversationAdapter.access$1200(ConversationAdapter.this));
/* 1316 */                  return;
                        }
/* 1312 */              ConversationAdapter.access$1300(ConversationAdapter.this).execute(this.bitmap, ConversationAdapter.access$1200(ConversationAdapter.this), 6);
/* 1313 */              this.setImage = true;
/* 1314 */              ConversationAdapter.access$1400(ConversationAdapter.this).post(this);
                    }
                }

/* 1321 */      public class VanishPlayButtonListener implements View.OnClickListener {
                    private final ConversationViewHolder holder;

/* 1322 */          public VanishPlayButtonListener(ConversationViewHolder holder) {
/* 1324 */              this.holder = holder;
                    }

                    @Override // android.view.View.OnClickListener
/* 1328 */          public void onClick(View v) {
/* 1329 */              this.holder.vanishMessageContainer.setVisibility(8);
/* 1330 */              this.holder.vanishThumbnailOverlay.setVisibility(8);
/* 1331 */              this.holder.loadImage.setVisibility(0);
/* 1332 */              this.holder.timerOval.setColor((-16777216) | this.holder.color);
/* 1333 */              this.holder.vanishTimerText.setVisibility(0);
/* 1334 */              this.holder.vanishTimerText.invalidate();
/* 1335 */              ConversationAdapter.this.getVideoPathAndPlayForPosition(this.holder.position, false);
                    }
                }

/* 1340 */      public class ConversationVideoClickedListener extends OnListVideoClickListener {
/* 1341 */          public ConversationVideoClickedListener() {
/* 1343 */              super(ConversationAdapter.this);
                    }

                    @Override // co.vine.android.player.OnListVideoClickListener, android.view.View.OnClickListener
/* 1347 */          public void onClick(View v) {
/* 1348 */              super.onClick(v);
                    }
                }

/* 1392 */      public static class ConversationViewHolder {
                    public ImageKey avatarKey;
                    public final View bottomPaddingView;
                    public int color;
                    public final TextView errorMessage;
                    public boolean hasVideoImage;
                    public boolean isCurrentUser;
                    public boolean isSharedPost;
                    public boolean isVideoImageLoaded;
                    public final View loadImage;
                    public VinePrivateMessage message;
                    public final ViewGroup messageContainer;
                    public final TextView messageContainerMessage;
                    public final ImageView messageContainerUserImage;
                    public final View nibs;
                    public final View nibsChatNib;
                    public int position;
                    public final ViewGroup postMessageContainer;
                    public final TextView postMessageContainerMessage;
                    public View postNibs;
                    public final View secondaryMessageContainer;
                    public final TextView secondaryMessageContainerMessage;
                    public ColoredOvalDrawable timerOval;
                    public final TextView timestamp;
                    public final ViewGroup topMessageContainer;
                    public final TextView topMessageContainerMessage;
                    public final ImageView topMessageContainerUserImage;
                    public View userImageProgressNormal;
                    public View userImageProgressTop;
                    public final View vanishMessageContainer;
                    public final TextView vanishMessageOverlay;
                    public final View vanishPlayButton;
                    public VanishPlayButtonListener vanishPlayButtonListener;
                    public final View vanishThumbnailOverlay;
                    public final TextView vanishTimerText;
                    public final ImageView vanishUserImage;
                    public final View videoContainer;
                    public final ImageView videoImage;
                    public ImageKey videoImageKey;
                    public OnListVideoClickListener videoListener;
                    public SdkVideoView videoView;

/* 1393 */          public ConversationViewHolder(View v) {
/* 1395 */              this.messageContainer = (ViewGroup) v.findViewById(C0257R.id.message_container);
/* 1396 */              this.messageContainerUserImage = (ImageView) v.findViewById(C0257R.id.message_container_user_image);
/* 1397 */              this.messageContainerMessage = (TextView) v.findViewById(C0257R.id.message_container_message);
/* 1399 */              this.topMessageContainer = (ViewGroup) v.findViewById(C0257R.id.top_message_container);
/* 1400 */              this.topMessageContainerUserImage = (ImageView) v.findViewById(C0257R.id.top_message_container_user_image);
/* 1401 */              this.topMessageContainerMessage = (TextView) v.findViewById(C0257R.id.top_message_container_message);
/* 1403 */              this.vanishThumbnailOverlay = v.findViewById(C0257R.id.vanish_thumbnail_overlay);
/* 1404 */              this.vanishMessageOverlay = (TextView) v.findViewById(C0257R.id.vanish_message_overlay);
/* 1405 */              this.vanishMessageContainer = v.findViewById(C0257R.id.vanish_message_container);
/* 1406 */              this.vanishUserImage = (ImageView) v.findViewById(C0257R.id.vanish_container_user_image);
/* 1407 */              this.vanishPlayButton = v.findViewById(C0257R.id.vanish_play_button);
/* 1408 */              this.vanishTimerText = (TextView) v.findViewById(C0257R.id.vanish_timer);
/* 1410 */              this.postMessageContainer = (ViewGroup) v.findViewById(C0257R.id.post_message_container);
/* 1411 */              this.postMessageContainerMessage = (TextView) v.findViewById(C0257R.id.post_message_container_message);
/* 1413 */              this.userImageProgressNormal = v.findViewById(C0257R.id.retry_progress_normal);
/* 1414 */              this.userImageProgressTop = v.findViewById(C0257R.id.retry_progress_top);
/* 1416 */              this.secondaryMessageContainer = v.findViewById(C0257R.id.secondary_message_container);
/* 1417 */              this.secondaryMessageContainerMessage = (TextView) v.findViewById(C0257R.id.secondary_message_container_message);
/* 1419 */              this.videoContainer = v.findViewById(C0257R.id.video_container);
/* 1420 */              this.videoImage = (ImageView) v.findViewById(C0257R.id.video_image);
/* 1421 */              this.videoView = (SdkVideoView) v.findViewById(C0257R.id.video_view);
/* 1422 */              this.loadImage = v.findViewById(C0257R.id.video_load_image);
/* 1423 */              this.errorMessage = (TextView) v.findViewById(C0257R.id.error_message);
/* 1424 */              this.bottomPaddingView = v.findViewById(C0257R.id.message_bottom_padding_for_last_item);
/* 1425 */              this.nibs = v.findViewById(C0257R.id.nibs);
/* 1426 */              this.postNibs = v.findViewById(C0257R.id.shared_post_nibs);
/* 1427 */              this.nibsChatNib = v.findViewById(C0257R.id.nibs_chat_nib);
/* 1429 */              this.timestamp = (TextView) v.findViewById(C0257R.id.timestampText);
                    }
                }

/* 1433 */      public void toggleMute(boolean mute) {
/* 1434 */          this.mMuted = mute;
/* 1435 */          SLog.m447d("Mute state changed to muted? {}.", Boolean.valueOf(mute));
/* 1436 */          SdkVideoView lastPlayer = getLastPlayer();
/* 1437 */          if (lastPlayer != null) {
/* 1438 */              lastPlayer.setMute(this.mMuted);
                    }
                }
            }
