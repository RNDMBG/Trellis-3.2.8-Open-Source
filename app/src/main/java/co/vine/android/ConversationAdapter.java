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
            
            
    public class ConversationAdapter extends CursorAdapter implements HasVideoPlayerAdapter, View.OnClickListener {
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

        static  ConversationActivity access$000(ConversationAdapter x0) {
            return x0.mActivity;
                }

        static  ListView access$100(ConversationAdapter x0) {
            return x0.mListView;
                }

        static  Context access$1000(ConversationAdapter x0) {
            return x0.mContext;
                }

        static  Context access$1100(ConversationAdapter x0) {
            return x0.mContext;
                }

        static  Bitmap access$1200(ConversationAdapter x0) {
            return x0.mBlurTempBitmap;
                }

        static  Bitmap access$1202(ConversationAdapter x0, Bitmap x1) {
            x0.mBlurTempBitmap = x1;
                    return x1;
                }

        static  RsBlur access$1300(ConversationAdapter x0) {
            return x0.mRsBlur;
                }

        static  RsBlur access$1302(ConversationAdapter x0, RsBlur x1) {
            x0.mRsBlur = x1;
                    return x1;
                }

        static  Handler access$1400(ConversationAdapter x0) {
            return x0.mHandler;
                }

        static  SparseArray access$200(ConversationAdapter x0) {
            return x0.mMessages;
                }

        static  View access$300(ConversationAdapter x0, int x1) {
            return x0.getViewAt(x1);
                }

        static  int access$400(ConversationAdapter x0) {
            return x0.mCurrentPlaying;
                }

        static  SeekInfoManager.SeekInfo access$500(ConversationAdapter x0, VideoViewInterface x1, int x2) {
            return x0.getSeekInfoMarkAsRead(x1, x2);
                }

        static  boolean access$600(ConversationAdapter x0, VideoViewInterface x1, int x2) {
            return x0.onLoopFinished(x1, x2);
                }

        static  void access$700(ConversationAdapter x0, SdkVideoView x1) {
            x0.releaseOtherPlayers(x1);
                }

        static  ImageView access$800(ConversationAdapter x0) {
            return x0.mCurrentBlurImageView;
                }

        static  Bitmap access$900(ConversationAdapter x0) {
            return x0.mCurrentBlurBitmap;
                }

       public ConversationAdapter(ConversationActivity activity, AppController appController, ListView listView, long myUserId, int flags) {
           super(activity, (Cursor) null, flags);
            this.mHandler = new Handler();
            this.mPaths = new SparseArray<>();
            this.mUrlReverse = new HashMap<>();
            this.mViewHolders = new ArrayList<>();
            this.mMessages = new SparseArray<>();
            this.mThumbnails = new SparseArray<>();
            this.mAvatars = new SparseArray<>();
            this.mPlayers = new SparseArray<>();
            this.mVanishedIdDateMap = new HashMap<>();
           this.mIdToPositionMap = new HashMap<>();
           this.mViewedPorterDuffFilterSet = new SparseArray<>();
           this.mCurrentPlaying = Integer.MIN_VALUE;
           this.mShouldBePlaying = Integer.MIN_VALUE;
           this.mWaitingBg = new ColorDrawable(-1);
           this.mPlayCurrentPositionRunnable = new RunnableC01832();
          this.mBlurQueueRunnable = new RunnableC01876();
           this.mMyUserId = myUserId;
           this.mActivity = activity;
           this.mSpanClicker = new VineSpanClicker(activity);
           this.mSpanClicker.setFlurryEventSource("ConversationAdapter");
           this.mAppController = appController;
           this.mSeekInfoManager = new SeekInfoManager(this.mAppController);
           this.mListView = listView;
           this.mScreenWidth = SystemUtil.getDisplaySize((Activity) activity).x;
           SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
           this.mMyColor = prefs.getInt("profile_background", Settings.DEFAULT_PROFILE_COLOR);
           Resources res = activity.getResources();
           this.mSharedDescSize = res.getDimension(R.dimen.font_size_default);
           this.mSidePadding = res.getDimension(R.dimen.conversation_side_padding);
           this.mAvatarMargin = res.getDimension(R.dimen.conversation_avatar_size) + res.getDimension(R.dimen.spacing_small);
           this.mBoldSpan = new TypefacesSpan(null, Typefaces.get(this.mContext).getContentTypeface(1, 3));
           this.mDarkGreySpan = new ColoredSpan(res.getColor(R.color.solid_dark_gray));
           this.mVineDateFormatter = new VineDateFormatter(res);
           this.mVineDateFormatter.refreshDates();
           this.mVineDateFormatter.addSpanForDateSection(this.mBoldSpan);
                }

                @Override 
       public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    View v;
           LayoutInflater layoutInflater = LayoutInflater.from(context);
                    boolean isCurrentUser = ((long) cursor.getInt(3)) == this.mMyUserId;
           if (isCurrentUser) {
               v = layoutInflater.inflate(R.layout.message_row_view_me, parent, false);
                    } else {
               v = layoutInflater.inflate(R.layout.message_row_view, parent, false);
                    }
           ConversationViewHolder holder = new ConversationViewHolder(v);
           holder.videoContainer.setVisibility(0);
           holder.timerOval = new ColoredOvalDrawable();
           ViewUtil.setBackground(holder.vanishTimerText, holder.timerOval);
           holder.videoListener = new ConversationVideoClickedListener();
           ViewGroup.LayoutParams params = holder.videoContainer.getLayoutParams();
           params.height = (int) (this.mScreenWidth - (this.mSidePadding * 2.0f));
           params.width = (int) (this.mScreenWidth - (this.mSidePadding * 2.0f));
           holder.videoContainer.setLayoutParams(params);
           ViewGroup.LayoutParams params2 = holder.videoImage.getLayoutParams();
           params2.width = (int) (this.mScreenWidth - (this.mSidePadding * 2.0f));
           params2.height = (int) (this.mScreenWidth - (this.mSidePadding * 2.0f));
           holder.vanishPlayButtonListener = new VanishPlayButtonListener(holder);
           holder.vanishPlayButton.setOnClickListener(holder.vanishPlayButtonListener);
           holder.videoImage.setLayoutParams(params2);
           holder.messageContainerUserImage.bringToFront();
           v.setTag(holder);
           this.mViewHolders.add(new WeakReference<>(holder));
           return v;
                }

       private boolean shouldShowThumbnail(int position) {
                    return (this.mCurrentPlaying == position && hasPlayerPlaying()) ? false : true;
                }

       private void vanishMessage(VinePrivateMessage vpm) {
           vpm.vanished = true;
           vpm.vanishedDate = System.currentTimeMillis();
           this.mVanishedIdDateMap.put(Long.valueOf(vpm.messageId), Long.valueOf(vpm.vanishedDate));
                }

       private boolean isMessageAlreadyVanished(VinePrivateMessage vpm) {
           if (vpm.ephemeral) {
               if (vpm.vanished) {
                   return true;
                        }
               SeekInfoManager.SeekInfo startTime = this.mSeekInfoManager.get(vpm.messageId);
               if (startTime != null) {
                            if (vpm.localStartTime != 0) {
                       startTime.startTime = vpm.localStartTime;
                            }
                   boolean alreadyVanished = startTime.isExpired();
                   if (alreadyVanished) {
                       vanishMessage(vpm);
                       return alreadyVanished;
                            }
                   return alreadyVanished;
                        }
               return false;
                    }
           return false;
                }

       private void setupUserImageProperties(int messagePosition, ImageView userImage, boolean isCurrentUser) {
           userImage.setVisibility(0);
           userImage.setTag(Integer.valueOf(messagePosition));
           userImage.setOnClickListener(this);
           RelativeLayout.LayoutParams imageParams = (RelativeLayout.LayoutParams) userImage.getLayoutParams();
           if (!isCurrentUser) {
               imageParams.addRule(11, 0);
               imageParams.addRule(9);
                    } else {
               imageParams.addRule(9, 0);
               imageParams.addRule(11);
                    }
           userImage.setLayoutParams(imageParams);
                }

       private void setupUserImageImage(VinePrivateMessage vpm, ConversationViewHolder holder, ImageView userImage, boolean isError) {
           if (isError) {
               userImage.setColorFilter((ColorFilter) null);
               userImage.setImageResource(R.drawable.ic_failed_delivery_light);
               return;
                    }
           if (Util.isDefaultAvatarUrl(vpm.avatarUrl)) {
               Util.safeSetDefaultAvatar(userImage, Util.ProfileImageSize.MEDIUM, (-16777216) | holder.color);
               return;
                    }
           userImage.setColorFilter((ColorFilter) null);
           ImageKey avatarKey = new ImageKey(vpm.avatarUrl, true);
           holder.avatarKey = avatarKey;
           Bitmap avatar = this.mAvatars.get(holder.position);
           if (avatar != null) {
               setImage(userImage, avatar, holder);
                    } else {
               setImage(userImage, this.mAppController.getPhotoBitmap(avatarKey), holder);
                    }
                }

                @Override 
       public int getItemViewType(int position) {
           Cursor c = (Cursor) getItem(position);
                    return this.mMyUserId == c.getLong(3) ? 1 : 0;
                }

       private VinePrivateMessage initMessage(Cursor cursor) {
           int position = cursor.getPosition();
           VinePrivateMessage vpm = new VinePrivateMessage(cursor);
           this.mMessages.put(position, vpm);
           return vpm;
                }

                @Override 
       public int getViewTypeCount() {
           return 2;
                }

                @Override 
       public void bindView(View view, Context context, Cursor cursor) {
                    int color;
           int position = cursor.getPosition();
           VinePrivateMessage vpm = initMessage(cursor);
           Resources res = context.getResources();
           View.OnLongClickListener longClickListener = new ViewOnLongClickListenerC01821(vpm);
           ConversationViewHolder holder = (ConversationViewHolder) view.getTag();
           view.setOnLongClickListener(longClickListener);
           holder.videoContainer.setOnLongClickListener(longClickListener);
           holder.avatarKey = null;
           holder.videoImageKey = null;
           holder.position = position;
           if (holder.userImageProgressNormal != null && holder.userImageProgressTop != null) {
               holder.userImageProgressTop.setVisibility(8);
               holder.userImageProgressNormal.setVisibility(8);
                    }
           boolean isCurrentUser = this.mMyUserId == vpm.userId;
           holder.isCurrentUser = isCurrentUser;
           holder.videoListener.setPosition(position);
           invalidateTimestamp(holder, position, cursor, vpm);
           if (isCurrentUser) {
               color = this.mMyColor;
                    } else {
               color = cursor.getInt(25);
                    }
           if (color <= 0) {
               color = Settings.DEFAULT_PROFILE_COLOR;
                    }
           if (color == Settings.DEFAULT_PROFILE_COLOR || color <= 0) {
               color = 16777215 & this.mContext.getResources().getColor(R.color.vine_green);
                    }
           holder.color = color;
           boolean isSharedPost = vpm.postId > 0;
           holder.isSharedPost = isSharedPost;
           boolean vanishedEphemeral = isMessageAlreadyVanished(vpm);
           boolean deletedSharedPost = isSharedPost && (vpm.videoUrl == null || vpm.thumbnailUrl == null || vpm.post.username == null);
           boolean showVideoContainer = vpm.hasVideo || (vpm.ephemeral && !vanishedEphemeral) || deletedSharedPost;
           String message = TextUtils.isEmpty(vpm.message) ? "" : vpm.message;
           boolean showSecondaryMessageContainer = vpm.ephemeral && vanishedEphemeral;
           if (showVideoContainer) {
               if (vpm.ephemeral) {
                   holder.vanishThumbnailOverlay.setVisibility(0);
                        } else {
                   holder.vanishThumbnailOverlay.setVisibility(8);
                        }
                    }
           holder.vanishTimerText.setVisibility(8);
           if (deletedSharedPost) {
               holder.videoImage.setScaleType(ImageView.ScaleType.CENTER);
               holder.videoImage.setBackgroundColor(res.getColor(R.color.solid_light_gray));
                    } else {
               holder.videoImage.setScaleType(ImageView.ScaleType.FIT_XY);
               holder.videoImage.setBackgroundColor(res.getColor(android.R.color.transparent));
                    }
           if (vpm.ephemeral) {
               holder.videoContainer.setOnClickListener(null);
               holder.messageContainer.setVisibility(8);
               if (getSeekInfo(holder.position) == null) {
                   holder.vanishMessageContainer.setVisibility(0);
                   holder.vanishMessageOverlay.setText(message);
                   if (isCurrentUser) {
                       holder.vanishPlayButton.setVisibility(8);
                            } else {
                       holder.vanishPlayButton.setVisibility(0);
                            }
                        }
               setupUserImageImage(vpm, holder, holder.vanishUserImage, vpm.errorCode != 0);
               holder.topMessageContainer.setVisibility(8);
                    } else {
               if (deletedSharedPost) {
                   holder.videoContainer.setOnClickListener(null);
                   holder.videoImage.setImageResource(R.drawable.background_sadface);
                        } else {
                   holder.videoContainer.setOnClickListener(holder.videoListener);
                   holder.videoImage.setImageBitmap(null);
                        }
               holder.vanishMessageContainer.setVisibility(8);
               holder.messageContainer.setVisibility(0);
               holder.postMessageContainer.setVisibility(8);
               TextView messageContainerMessage = holder.messageContainerMessage;
               if (isSharedPost) {
                   holder.messageContainer.setVisibility(8);
                   holder.postMessageContainer.setVisibility(0);
                   holder.topMessageContainer.setVisibility(0);
                   setupUserImageProperties(position, holder.topMessageContainerUserImage, isCurrentUser);
                   setupUserImageImage(vpm, holder, holder.topMessageContainerUserImage, vpm.errorCode != 0);
                   holder.topMessageContainerMessage.setGravity((isCurrentUser ? 5 : 3) | 16);
                   RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.topMessageContainerMessage.getLayoutParams();
                   params.rightMargin = isCurrentUser ? (int) this.mAvatarMargin : 0;
                   params.leftMargin = isCurrentUser ? 0 : (int) this.mAvatarMargin;
                   styleTopSharedDescription(vpm.post, isCurrentUser, color, holder.topMessageContainerMessage, deletedSharedPost);
                   styleDescription(holder.postMessageContainerMessage, vpm.post, color, deletedSharedPost);
                   holder.postMessageContainerMessage.setTextColor(-16777216);
                   holder.postMessageContainerMessage.setTextSize(0, this.mSharedDescSize);
                   ViewUtil.setBackground(holder.postMessageContainerMessage, null);
                        } else {
                   messageContainerMessage.setText(message);
                   setupUserImageProperties(position, holder.messageContainerUserImage, isCurrentUser);
                   setupUserImageImage(vpm, holder, holder.messageContainerUserImage, vpm.errorCode != 0);
                   holder.topMessageContainer.setVisibility(8);
                   if (TextUtils.isEmpty(vpm.errorReason)) {
                       if (holder.errorMessage != null) {
                           holder.errorMessage.setVisibility(8);
                                }
                       messageContainerMessage.setVisibility(0);
                       messageContainerMessage.setTextColor((-16777216) | color);
                       if (!TextUtils.isEmpty(message)) {
                           if (isCurrentUser) {
                               ViewUtil.fillColor(res, 855638016 | color, R.drawable.bubble, messageContainerMessage);
                               ViewUtil.fillColor(res, 855638016 | color, R.drawable.vm_nib_me, holder.nibsChatNib);
                                    } else if (this.mMyColor != color) {
                               ViewUtil.fillColor(res, 855638016 | color, R.drawable.bubble, messageContainerMessage);
                               ViewUtil.fillColor(res, 855638016 | color, R.drawable.vm_nib_you, holder.nibsChatNib);
                                    } else {
                               ViewUtil.fillColor(res, (-16777216) | color, R.drawable.bubble, messageContainerMessage);
                               ViewUtil.fillColor(res, (-16777216) | color, R.drawable.vm_nib_you, holder.nibsChatNib);
                               messageContainerMessage.setTextColor(-1);
                                    }
                                } else {
                           messageContainerMessage.setVisibility(8);
                                }
                            } else {
                       if (TextUtils.isEmpty(message)) {
                           messageContainerMessage.setVisibility(8);
                                } else {
                           int grayTextColor = res.getColor(R.color.solid_light_gray_vm);
                           int grayBubbleColor = res.getColor(R.color.solid_light_gray_bubble_vm);
                           messageContainerMessage.setVisibility(0);
                           messageContainerMessage.setTextColor(grayTextColor);
                           ViewUtil.fillColor(res, grayBubbleColor, R.drawable.bubble, messageContainerMessage);
                           ViewUtil.fillColor(res, grayBubbleColor, R.drawable.vm_nib_me, holder.nibsChatNib);
                                }
                       if (holder.errorMessage != null) {
                           holder.errorMessage.setVisibility(0);
                           holder.errorMessage.setText(vpm.errorReason);
                                }
                            }
                        }
                    }
           String thumbnailUrl = deletedSharedPost ? null : vpm.thumbnailUrl;
           holder.hasVideoImage = thumbnailUrl != null;
           if (holder.hasVideoImage) {
               ImageKey videoImageKey = new ImageKey(thumbnailUrl);
               holder.videoImageKey = videoImageKey;
               Bitmap videoThumbnail = this.mThumbnails.get(position);
               if (videoThumbnail != null) {
                   SLog.m447d("setting thumbnail for {}", Integer.valueOf(position));
                   holder.isVideoImageLoaded = setImage(holder.videoImage, videoThumbnail, holder);
                        } else {
                   SLog.m447d("fetching thumbnail for {}", Integer.valueOf(position));
                   Bitmap bitmap = this.mAppController.getPhotoBitmap(videoImageKey);
                   if (bitmap != null) {
                       this.mThumbnails.put(position, bitmap);
                            }
                   holder.isVideoImageLoaded = setImage(holder.videoImage, bitmap, holder);
                        }
               holder.loadImage.setVisibility(8);
                    } else if (vpm.ephemeral || deletedSharedPost) {
               holder.loadImage.setVisibility(8);
                    } else if (!isCurrentUser) {
               holder.loadImage.setVisibility(0);
                    }
           boolean vanishedAlreadyInBindView = false;
           if (showVideoContainer) {
               holder.videoContainer.setVisibility(0);
               holder.nibsChatNib.setVisibility(4);
               if (shouldShowThumbnail(position) && holder.videoView.getPlayingPosition() != position) {
                   holder.videoView.setVisibility(4);
                        }
               initVideoView(position, holder);
               holder.nibs.setVisibility(isSharedPost ? 8 : 0);
               holder.postNibs.setVisibility(isSharedPost ? 0 : 8);
                    } else {
               holder.videoContainer.setVisibility(8);
               holder.nibsChatNib.setVisibility(0);
               holder.videoView.setTag(holder);
               this.mPlayers.remove(position);
               if (vanishedEphemeral) {
                   vanishView(vpm, holder, holder.videoView);
                   vanishedAlreadyInBindView = true;
                        }
                    }
           if (showSecondaryMessageContainer) {
               if (!vanishedAlreadyInBindView) {
                   vanishView(vpm, holder, holder.videoView);
                        }
                    } else {
               holder.secondaryMessageContainer.setVisibility(8);
                    }
           holder.vanishThumbnailOverlay.setBackgroundColor((-771751936) | holder.color);
           if (cursor.getPosition() == cursor.getCount() - 1) {
               holder.bottomPaddingView.setVisibility(0);
                    } else {
               holder.bottomPaddingView.setVisibility(8);
                    }
           holder.videoView.setMute(this.mMuted);
                }

                
       class ViewOnLongClickListenerC01821 implements View.OnLongClickListener {
                    final  VinePrivateMessage val$vpm;

           ViewOnLongClickListenerC01821(VinePrivateMessage vinePrivateMessage) {
                        this.val$vpm = vinePrivateMessage;
                    }

                    @Override 
           public boolean onLongClick(View view) {
               ConversationAdapter.access$000(ConversationAdapter.this).showDeleteMessageDialog(this.val$vpm.messageId);
               return true;
                    }
                }

       private void invalidateTimestamp(ConversationViewHolder holder, int position, Cursor cursor, VinePrivateMessage vpm) {
           boolean shouldShow = false;
           if (position == 0) {
               shouldShow = true;
                    } else {
               VinePrivateMessage previous = this.mMessages.get(position - 1);
               if (previous == null) {
                   if (cursor.moveToPrevious()) {
                       previous = initMessage(cursor);
                       cursor.moveToNext();
                            } else {
                       shouldShow = true;
                            }
                        }
               if (!shouldShow) {
                            shouldShow = vpm.created <= 0 || previous.created <= 0 || Math.abs(vpm.created - previous.created) >= CommonUtil.HOUR_MILLIS;
                        }
                    }
           if (shouldShow) {
               holder.timestamp.setText(this.mVineDateFormatter.format(vpm.created));
               holder.timestamp.setVisibility(0);
                    } else {
               holder.timestamp.setVisibility(8);
                    }
                }

       private void styleTopSharedDescription(VinePost post, boolean isCurrentUser, int color, TextView view, boolean deleted) {
                    String replacer;
           int color2 = color | (-16777216);
           if (deleted || post == null || post.username == null) {
               SpannableStringBuilder sb = new SpannableStringBuilder();
                        sb.append((CharSequence) this.mContext.getString(isCurrentUser ? R.string.share_vm_sender_deleted : R.string.share_vm_receiver_deleted));
               Util.safeSetSpan(sb, this.mDarkGreySpan, 0, sb.length(), 33);
               view.setText(sb);
               if (post != null && post.username == null) {
                   CrashUtil.logException(new IllegalArgumentException("Username is null: " + post.postId));
                   return;
                        }
               return;
                    }
           if (post.sharedVmSb == null) {
               String string = this.mContext.getString(isCurrentUser ? R.string.share_vm_sender_text : R.string.share_vm_receiver_text);
               post.sharedVmSb = new SpannableStringBuilder();
               SpannableStringBuilder sharedVmSb = post.sharedVmSb;
               int start = string.indexOf("{");
               if (start > 0) {
                   sharedVmSb.append((CharSequence) string.substring(0, start));
                        }
               int index = string.indexOf("}");
               if (index < 0) {
                   string = string + "}";
                        }
                        try {
                   replacer = string.substring(start + 1, string.indexOf("}"));
                        } catch (Exception e) {
                   CrashUtil.logException(e, "Failed top post span thing. locale: {}", Util.getLocale());
                   replacer = "%s";
                        }
               sharedVmSb.append((CharSequence) replacer.replace("%s", post.username));
               int end = sharedVmSb.length();
               int endOfReplacer = string.indexOf("}");
               if (endOfReplacer < string.length() - 1) {
                   sharedVmSb.append((CharSequence) string.substring(endOfReplacer + 1));
                        }
               StyledClickableSpan span = new StyledClickableSpan(9, Long.valueOf(post.postId), this.mSpanClicker);
               span.setColor(color2);
               Util.safeSetSpan(sharedVmSb, span, start, end, 33);
               Util.safeSetSpan(sharedVmSb, this.mBoldSpan, start, end, 33);
               Util.safeSetSpan(sharedVmSb, this.mDarkGreySpan, 0, start, 33);
                    }
           view.setMovementMethod(LinkMovementMethod.getInstance());
           view.setText(post.sharedVmSb);
                }

       private void styleDescription(TextView textView, VinePost post, int color, boolean deletedSharedPost) {
           int color2 = color | (-16777216);
           if (post != null && post.transientEntities == null && post.entities != null) {
               post.transientEntities = new ArrayList<>();
               Iterator<VineEntity> it = post.entities.iterator();
                        while (it.hasNext()) {
                   post.transientEntities.add(it.next().duplicate());
                        }
                    }
           ArrayList<VineEntity> postEntities = post != null ? post.transientEntities : null;
           if (postEntities != null) {
               textView.setMovementMethod(LinkMovementMethod.getInstance());
               if (post.descriptionSb == null && post.description != null) {
                   post.descriptionSb = new SpannableStringBuilder(post.description);
                   SpannableStringBuilder descriptionSb = post.descriptionSb;
                            try {
                       Util.adjustEntities(postEntities, descriptionSb, 0, false);
                                for (VineEntity entity : postEntities) {
                           if (entity.isUserType()) {
                               StyledClickableSpan usernameSpan = new StyledClickableSpan(1, Long.valueOf(entity.f96id), this.mSpanClicker);
                               usernameSpan.setColor(color2);
                               Util.safeSetSpan(descriptionSb, usernameSpan, entity.start, entity.end, 33);
                                    } else if (entity.isTagType()) {
                               StyledClickableSpan usernameSpan2 = new StyledClickableSpan(4, entity.title, this.mSpanClicker);
                               usernameSpan2.setColor(color2);
                               Util.safeSetSpan(descriptionSb, usernameSpan2, entity.start, entity.end, 33);
                                    }
                                }
                            } catch (Exception e) {
                       CrashUtil.logException(e);
                            }
                        }
               textView.setText(post.descriptionSb);
               return;
                    }
           if (deletedSharedPost || post == null) {
               SpannableStringBuilder sb = new SpannableStringBuilder(this.mActivity.getString(R.string.share_vm_deleted));
               Util.safeSetSpan(sb, this.mDarkGreySpan, 0, sb.length(), 33);
               textView.setText(sb);
               return;
                    }
           textView.setText(post.description);
                }

       public long getIdAtLastPosition() {
           Cursor cursor = getCursor();
           if (cursor == null || !cursor.moveToLast()) {
                        return -1L;
                    }
           return cursor.getLong(3);
                }

                @Override 
       public synchronized void getVideoPathAndPlayForPosition(int requestedPosition, boolean forceLowKey) {
                    ConversationViewHolder tag;
                    try {
                        SLog.m447d("Play {} requested.", Integer.valueOf(requestedPosition));
               if (this.mPaths.get(requestedPosition) != null) {
                   playFile(requestedPosition, true);
                        } else if (this.mMessages.get(requestedPosition) != null) {
                   VinePrivateMessage vpm = this.mMessages.get(requestedPosition);
                   VideoKey videoKey = new VideoKey(vpm.videoUrl);
                   String path = this.mAppController.getVideoFilePath(videoKey);
                   if (path != null) {
                       this.mPaths.put(requestedPosition, path);
                       SLog.m447d("playing file at position {}", Integer.valueOf(requestedPosition));
                       playFile(requestedPosition, true);
                            } else {
                       View currentView = getViewAt(requestedPosition);
                       if (currentView != null && (tag = (ConversationViewHolder) currentView.getTag()) != null) {
                           if (tag.vanishMessageContainer.getVisibility() != 0) {
                               tag.loadImage.setVisibility(0);
                               this.mShouldBePlaying = requestedPosition;
                               this.mUrlReverse.put(videoKey, Integer.valueOf(requestedPosition));
                                    }
                                } else {
                           this.mShouldBePlaying = requestedPosition;
                           this.mUrlReverse.put(videoKey, Integer.valueOf(requestedPosition));
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public void playFile(int position, boolean isVideoLoaded) {
                    SdkVideoView currentVideoView;
           if (position >= 0) {
               View currentView = getViewAt(position);
               if (currentView == null) {
                   SLog.m465i("Item is not visible: {}", Integer.valueOf(position));
                   return;
                        }
               ConversationViewHolder holder = (ConversationViewHolder) currentView.getTag();
               if (holder != null) {
                   if (holder.vanishMessageContainer.getVisibility() == 0) {
                       SLog.m446d("No AutoPlay for Ephemeral.");
                       return;
                            }
                   String newPath = this.mPaths.get(position);
                   String oldPath = holder.videoView.getPath();
                   if (newPath != null) {
                       pauseCurrentPlayer();
                       if (!newPath.equals(oldPath)) {
                           refreshVideoView(position, holder);
                           currentVideoView = holder.videoView;
                           currentVideoView.setVideoPath(newPath);
                           currentVideoView.setPlayingPosition(position);
                           currentVideoView.setTag(holder);
                           holder.videoListener.setPosition(position);
                                } else {
                           currentVideoView = holder.videoView;
                           VinePrivateMessage message = this.mMessages.get(position);
                           if (message != null && !message.ephemeral && currentVideoView.getVisibility() != 0) {
                               currentVideoView.setVisibility(0);
                                    }
                           currentVideoView.setPlayingPosition(position);
                           if (currentVideoView.isInPlaybackState() && currentVideoView.isPathPlaying(newPath)) {
                               currentVideoView.start();
                                    } else {
                               currentVideoView.setVideoPath(newPath);
                                    }
                                }
                       this.mCurrentPlaying = position;
                       this.mLastPlayer = currentVideoView;
                       currentVideoView.setMute(this.mMuted);
                            }
                        }
                    }
                }

                @Override 
       public void pausePlayer(SdkVideoView player) {
           player.pause();
                }

                @Override 
       public void resumePlayer(SdkVideoView player) {
           player.resume();
                }

       private void refreshVideoView(int position, ConversationViewHolder tag) {
           ViewGroup group = (ViewGroup) tag.videoView.getParent();
           if (this.mVideoAttr == null) {
               this.mVideoAttr = tag.videoView.getAttributes();
                    }
           tag.videoView.suspend();
                    try {
               group.removeView(tag.videoView);
                    } catch (RuntimeException e) {
                        try {
                   group.removeView(tag.videoView);
                        } catch (Exception e2) {
                   CrashUtil.logException(e, "Weird things are happening.", new Object[0]);
                        }
                    }
           tag.videoView = new SdkVideoView(this.mContext, this.mVideoAttr);
           SeekInfoManager.SeekInfo seekInfo = getSeekInfo(position);
           if (!tag.isCurrentUser && seekInfo != null && seekInfo.isExpired()) {
               tag.videoView.setVisibility(4);
                    }
           group.addView(tag.videoView);
           initVideoView(position, tag);
                }

       public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
                    for (VideoKey key : videos.keySet()) {
               UrlVideo video = videos.get(key);
               if (video.isValid() && this.mUrlReverse.get(key) != null) {
                   this.mPaths.put(this.mUrlReverse.get(key).intValue(), video.getAbsolutePath());
                   if (this.mUrlReverse.get(key).intValue() == this.mShouldBePlaying) {
                       playCurrentPosition();
                            }
                        }
                    }
                }

                @Override 
       public synchronized void playCurrentPosition() {
                    try {
                        this.mHandler.removeCallbacks(this.mPlayCurrentPositionRunnable);
               this.mHandler.postDelayed(this.mPlayCurrentPositionRunnable, 50L);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public SdkVideoView getPlayer(int position, boolean b) {
           return this.mPlayers.get(position);
                }

                @Override 
       public void pauseCurrentPlayer() {
           if (hasPlayerPlaying()) {
               this.mLastPlayer.pause();
                    }
                }

       private boolean hasPlayerPlaying() {
                    return (this.mLastPlayer == null || this.mLastPlayer.isPaused()) ? false : true;
                }

       public void stopCurrentPlayer() {
           if (this.mLastPlayer != null) {
               this.mLastPlayer.suspend();
               this.mLastPlayer = null;
               this.mCurrentPlaying = -1;
                    }
                }

       private View getViewAt(int position) {
           ListView listView = this.mListView;
           int firstPosition = (listView.getFirstVisiblePosition() - listView.getHeaderViewsCount()) - 0;
           int wantedChild = position - firstPosition;
           if (wantedChild >= 0 && wantedChild < listView.getChildCount()) {
                        return listView.getChildAt(wantedChild);
                    }
           SLog.m481w("Unable to get view for desired position, because it's not being displayed on screen: {} {} {}.", new Object[]{Integer.valueOf(wantedChild), Integer.valueOf(firstPosition), Integer.valueOf(listView.getChildCount())});
           return null;
                }

                
       class RunnableC01832 implements Runnable {
           RunnableC01832() {
                    }

                    @Override 
           public synchronized void run() {
                        VinePrivateMessage next;
                        try {
                            ListView listView = ConversationAdapter.access$100(ConversationAdapter.this);
                   int currentFirst = listView.getFirstVisiblePosition();
                   VinePrivateMessage msg = (VinePrivateMessage) ConversationAdapter.access$200(ConversationAdapter.this).get(currentFirst);
                   boolean currentHasVideo = msg != null && msg.hasVideo;
                   View currentView = ConversationAdapter.access$300(ConversationAdapter.this, currentFirst);
                   float ratio = -1.0f;
                   if (currentView != null) {
                       int currentlyPlayingOffset = 0;
                       if (ConversationAdapter.this.getLastPlayer() != null) {
                           currentlyPlayingOffset = ConversationAdapter.this.getLastPlayer().getHeight() / 2;
                                }
                       float bottom = currentView.getBottom() + currentlyPlayingOffset;
                       float height = currentView.getHeight() + currentlyPlayingOffset;
                       ratio = bottom / height;
                            }
                   SLog.m448d("Current First: {}, ratio: {}", Integer.valueOf(currentFirst), Float.valueOf(ratio));
                   int currentNext = -1;
                            if (ratio < 0.5d || currentFirst == -1 || !currentHasVideo) {
                       for (int nextPlayer = currentFirst + 1; nextPlayer < ConversationAdapter.this.getCount() && (next = (VinePrivateMessage) ConversationAdapter.access$200(ConversationAdapter.this).get(nextPlayer)) != null; nextPlayer++) {
                           if (next.hasVideo) {
                               currentNext = nextPlayer;
                                        break;
                                    }
                                }
                       SLog.m447d("Current Next: {}", Integer.valueOf(currentNext));
                       if (currentNext != -1 && currentNext != ConversationAdapter.access$400(ConversationAdapter.this)) {
                           ConversationAdapter.this.getVideoPathAndPlayForPosition(currentNext, false);
                                }
                            } else if (currentFirst != ConversationAdapter.access$400(ConversationAdapter.this)) {
                       ConversationAdapter.this.getVideoPathAndPlayForPosition(currentFirst, false);
                            } else {
                       ConversationAdapter.this.playFile(currentFirst, false);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void onResume() {
           this.mBlurHandlerThread = new HandlerThread("blur-thread");
           this.mBlurHandlerThread.start();
           this.mBlurHandler = new Handler(this.mBlurHandlerThread.getLooper());
           this.mMuted = MuteUtil.isMuted(this.mContext);
           playCurrentPosition();
           this.mVineDateFormatter.refreshDates();
                }

       public void onPause() {
                    VinePrivateMessage message;
           if (this.mBlurHandlerThread != null) {
               Looper looper = this.mBlurHandlerThread.getLooper();
               if (looper != null) {
                   looper.quit();
                        }
               this.mBlurHandlerThread = null;
                    }
           if (this.mLastPlayer != null && this.mLastPlayer.hasStarted() && this.mCurrentPlaying > 0 && (message = this.mMessages.get(this.mCurrentPlaying)) != null && message.userId != this.mAppController.getActiveId() && message.ephemeral && this.mSeekInfoManager.get(message.messageId) != null) {
               SLog.m465i("Vanishing onPause for {}.", Long.valueOf(message.messageId));
               vanishMessage(message);
                    }
           stopCurrentPlayer();
           releaseOtherPlayers(null);
                }

       private void initVideoView(int position, ConversationViewHolder holder) {
           SdkVideoView view = holder.videoView;
           view.setAutoShow(true);
           view.setOnPreparedListener(new C01843(holder, position));
           view.setOnErrorListener(new C01854(view));
           view.setOnCompletionListener(new C01865(view, position));
           this.mPlayers.put(position, view);
                }

                
       class C01843 implements VideoViewInterface.OnPreparedListener {
                    final  ConversationViewHolder val$holder;
                    final  int val$position;

           C01843(ConversationViewHolder conversationViewHolder, int i) {
                        this.val$holder = conversationViewHolder;
                        this.val$position = i;
                    }

                    @Override 
           public void onPrepared(VideoViewInterface view) {
               if (this.val$holder.isCurrentUser) {
                   view.start();
                   ConversationAdapter.this.onLoadFinish(this.val$holder);
                        } else {
                   SeekInfoManager.SeekInfo seek = ConversationAdapter.access$500(ConversationAdapter.this, view, this.val$position);
                   if (seek == null) {
                       CrashUtil.log("Invalid state onPrepared.");
                            } else if (!seek.isExpired()) {
                       if (seek.hasLoopLimit() && !seek.isExpired()) {
                           view.seekTo(seek.getSeekTime());
                           this.val$holder.vanishTimerText.setVisibility(0);
                           this.val$holder.vanishTimerText.setText(seek.getLoopsLeftForDisplay());
                                }
                       if (!seek.isExpired()) {
                           view.start();
                           ConversationAdapter.this.onLoadFinish(this.val$holder);
                                }
                            } else {
                       ConversationAdapter.access$600(ConversationAdapter.this, view, this.val$position);
                            }
                        }
               this.val$holder.nibs.bringToFront();
               this.val$holder.postNibs.bringToFront();
                    }
                }

                
       class C01854 implements VideoViewInterface.OnErrorListener {
                    final  SdkVideoView val$view;

           C01854(SdkVideoView sdkVideoView) {
                        this.val$view = sdkVideoView;
                    }

                    @Override 
           public boolean onError(VideoViewInterface videoView, int what, int extra) {
               SLog.m448d("Got error, try recycling it more aggressively: {}, {}", Integer.valueOf(what), Integer.valueOf(extra));
               ConversationAdapter.access$700(ConversationAdapter.this, this.val$view);
               this.val$view.retryOpenVideo(false);
               return true;
                    }
                }

                
       class C01865 implements VideoViewInterface.OnCompletionListener {
                    final  int val$position;
                    final  SdkVideoView val$view;

           C01865(SdkVideoView sdkVideoView, int i) {
                        this.val$view = sdkVideoView;
                        this.val$position = i;
                    }

                    @Override 
           public void onCompletion(VideoViewInterface videoView) {
               if (ConversationAdapter.access$600(ConversationAdapter.this, this.val$view, this.val$position)) {
                   videoView.seekTo(0);
                   videoView.start();
                        }
                    }
                }

      private SeekInfoManager.SeekInfo getSeekInfo(int position) {
          VinePrivateMessage message = this.mMessages.get(position);
          if (message == null) {
                        return null;
                    }
          if (!message.ephemeral) {
              return SeekInfoManager.NO_LOOP_LIMIT;
                    }
          return this.mSeekInfoManager.getSeekInfo(null, message);
                }

      private SeekInfoManager.SeekInfo getSeekInfoMarkAsRead(VideoViewInterface view, int position) {
          VinePrivateMessage message = this.mMessages.get(position);
          if (message == null) {
                        return null;
                    }
          if (!message.ephemeral) {
              return SeekInfoManager.NO_LOOP_LIMIT;
                    }
          SeekInfoManager.SeekInfo seekInfo = this.mSeekInfoManager.getSeekInfo(null, message);
          if (seekInfo == null) {
              return this.mSeekInfoManager.markAsRead(view.getDuration(), message);
                    }
          return seekInfo;
                }

      private boolean onLoopFinished(VideoViewInterface view, int position) {
                    boolean haveLoopsLeft = true;
          VinePrivateMessage message = this.mMessages.get(position);
          if (message != null && message.userId != this.mAppController.getActiveId() && message.ephemeral) {
              SeekInfoManager.SeekInfo seekInfo = this.mSeekInfoManager.getSeekInfo(view, message);
              SLog.m448d("{} just looped, seekInfo {}", Long.valueOf(message.messageId), seekInfo);
              if (seekInfo != null && seekInfo.isExpired()) {
                  haveLoopsLeft = false;
                        }
              if (haveLoopsLeft) {
                  if (seekInfo != null) {
                      ((ConversationViewHolder) view.getTag()).vanishTimerText.setText(seekInfo.getLoopsLeftForDisplay());
                            }
                        } else {
                  vanishMessage(message);
                  this.mAppController.removeFromVideoCache(new VideoKey(message.videoUrl));
                  vanishView(message, (ConversationViewHolder) view.getTag(), view);
                        }
                    } else if (message == null) {
              CrashUtil.log("Message was null, limited loop function not working.");
                    }
          return haveLoopsLeft;
                }

      private void vanishView(VinePrivateMessage vpm, ConversationViewHolder holder, VideoViewInterface view) {
          if (view.isInPlaybackState()) {
              view.stopPlayback();
              view.setVideoPath(null);
                    }
          if (holder.videoContainer.getVisibility() != 8) {
              holder.videoContainer.setVisibility(8);
                    }
          if (holder.vanishTimerText.getVisibility() != 8) {
              holder.vanishTimerText.setVisibility(8);
                    }
          int backgroundColor = 1610612736 | holder.color;
          PorterDuffColorFilter filter = this.mViewedPorterDuffFilterSet.get(backgroundColor);
          if (filter == null) {
              filter = new PorterDuffColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN);
              this.mViewedPorterDuffFilterSet.put(backgroundColor, filter);
                    }
          holder.secondaryMessageContainer.getBackground().setColorFilter(filter);
          holder.secondaryMessageContainer.setVisibility(0);
          if (vpm.vanishedViewedSb == null) {
              vpm.vanishedViewedSb = new SpannableStringBuilder();
              Long memVanishedDate = this.mVanishedIdDateMap.get(Long.valueOf(vpm.messageId));
              Date vanishedDate = new Date(memVanishedDate != null ? memVanishedDate.longValue() : vpm.vanishedDate);
              String viewedString = this.mActivity.getString(R.string.viewed);
              String dateString = DateFormat.format("hh:mm aa", vanishedDate).toString();
              int start = viewedString.indexOf("%s");
              int end = start + dateString.length();
              vpm.vanishedViewedSb.append((CharSequence) viewedString.replace("%s", dateString));
              Util.safeSetSpan(vpm.vanishedViewedSb, new ColoredSpan((-16777216) | holder.color), 0, end, 33);
              Util.safeSetSpan(vpm.vanishedViewedSb, this.mBoldSpan, start, end, 33);
                    }
          holder.secondaryMessageContainerMessage.setText(vpm.vanishedViewedSb);
                }

      private void releaseOtherPlayers(SdkVideoView view) {
          int viewKey = -1;
                    for (int i = 0; i < this.mPlayers.size(); i++) {
              int key = this.mPlayers.keyAt(i);
              SdkVideoView v = this.mPlayers.get(key);
              if (v != view) {
                  v.suspend();
                        } else {
                  viewKey = key;
                        }
                    }
          this.mPlayers.clear();
          if (view != null) {
              if (viewKey >= 0) {
                  this.mPlayers.put(viewKey, view);
                        } else {
                  CrashUtil.logException(new RuntimeException("Invalid state in conversation adapter. Was seeing this a lot coming back from full record. Commented out the runtime and am just logging"));
                        }
                    }
                }

      void onLoadFinish(ConversationViewHolder holder) {
          VinePrivateMessage message = this.mMessages.get(holder.position);
          if (message != null && holder.videoView.getVisibility() != 0) {
              holder.videoView.setVisibility(0);
                    }
                }

                @Override 
      public SdkVideoView getLastPlayer() {
          return this.mLastPlayer;
                }

      private boolean setImage(ImageView imageView, Bitmap bitmap, ConversationViewHolder holder) {
                    VinePrivateMessage message;
          int position = holder.position;
          if (bitmap == null) {
              imageView.setBackgroundColor(this.mContext.getResources().getColor(R.color.solid_light_gray));
              imageView.setImageBitmap(null);
              return false;
                    }
          boolean setBlurredImage = false;
          if (imageView == holder.videoImage && bitmap.getWidth() == 480 && bitmap.getHeight() == 480 && (message = this.mMessages.get(position)) != null && message.ephemeral) {
              SeekInfoManager.SeekInfo seek = getSeekInfo(position);
              if (seek == null) {
                  setBlurredImage = true;
                        }
                    }
          if (setBlurredImage) {
              imageView.setBackgroundColor(this.mContext.getResources().getColor(R.color.solid_white));
              imageView.setImageDrawable(this.mWaitingBg);
              this.mCurrentBlurBitmap = bitmap;
              this.mCurrentBlurImageView = imageView;
              this.mBlurHandler.removeCallbacks(this.mBlurQueueRunnable);
              this.mBlurHandler.post(this.mBlurQueueRunnable);
                    } else {
              imageView.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bitmap));
              imageView.setBackgroundColor(this.mContext.getResources().getColor(android.R.color.transparent));
                    }
          return true;
                }

      public void setImages(HashMap<ImageKey, UrlImage> images) {
                    UrlImage image;
                    UrlImage image2;
          ArrayList<WeakReference<ConversationViewHolder>> toRemove = new ArrayList<>();
                    for (WeakReference<ConversationViewHolder> ref : this.mViewHolders) {
              ConversationViewHolder holder = ref.get();
              if (holder == null) {
                  toRemove.add(ref);
                        } else {
                  if (holder.avatarKey != null && (image2 = images.get(holder.avatarKey)) != null && image2.isValid()) {
                      if (holder.isSharedPost) {
                          setImage(holder.topMessageContainerUserImage, image2.bitmap, holder);
                                } else {
                          setImage(holder.messageContainerUserImage, image2.bitmap, holder);
                          setImage(holder.vanishUserImage, image2.bitmap, holder);
                                }
                            }
                  if (holder.videoImageKey != null && (image = images.get(holder.videoImageKey)) != null && image.isValid()) {
                      holder.isVideoImageLoaded = setImage(holder.videoImage, image.bitmap, holder);
                      this.mThumbnails.put(holder.videoListener.getPosition(), image.bitmap);
                      SLog.m447d("Image found: {}.", Integer.valueOf(holder.videoListener.getPosition()));
                      playCurrentPosition();
                            }
                        }
                    }
                    for (WeakReference<ConversationViewHolder> h : toRemove) {
              this.mViewHolders.remove(h);
                    }
                }

                @Override 
      public Cursor swapCursor(Cursor c) {
          Cursor oldCursor = super.swapCursor(c);
          this.mIdToPositionMap.clear();
          this.mThumbnails.clear();
          this.mPaths.clear();
          if (c != null && c.moveToFirst()) {
              do {
                  this.mIdToPositionMap.put(Long.valueOf(c.getLong(0)), Integer.valueOf(c.getPosition()));
              } while (c.moveToNext());
                    }
          return oldCursor;
                }

                @Override 
      public long getItemId(int pos) {
          if (this.mCursor == null || !this.mCursor.moveToPosition(pos)) {
                        return -1L;
                    }
          return this.mCursor.getLong(0);
                }

      public boolean atLastPage() {
                    return this.mCursor != null && this.mCursor.moveToFirst() && this.mCursor.getInt(8) == 1;
                }

      public int getPositionForId(long id) {
          if (this.mIdToPositionMap.containsKey(Long.valueOf(id))) {
              return this.mIdToPositionMap.get(Long.valueOf(id)).intValue();
                    }
                    return -1;
                }

                
      class RunnableC01876 implements Runnable {
          RunnableC01876() {
                    }

                    @Override 
          public void run() {
              if (ConversationAdapter.access$800(ConversationAdapter.this) != null && ConversationAdapter.access$900(ConversationAdapter.this) != null) {
                  ConversationAdapter.this.new BlurRunnable(ConversationAdapter.access$900(ConversationAdapter.this), ConversationAdapter.access$800(ConversationAdapter.this)).run();
                        }
                    }
                }

                @Override 
      public void onClick(View v) {
          Object tag = v.getTag();
          if (tag instanceof Integer) {
              VinePrivateMessage vpm = this.mMessages.get(((Integer) tag).intValue());
              Runnable retryRunnable = new RunnableC01887(vpm, v);
              if (vpm.errorCode == 616) {
                  this.mActivity.startPhoneConfirmation(retryRunnable);
                        } else if (vpm.errorCode != 0) {
                  retryRunnable.run();
                        }
                    }
                }

                
      class RunnableC01887 implements Runnable {
                    final  View val$v;
                    final  VinePrivateMessage val$vpm;

          RunnableC01887(VinePrivateMessage vinePrivateMessage, View view) {
                        this.val$vpm = vinePrivateMessage;
                        this.val$v = view;
                    }

                    @Override 
          public void run() {
              ConversationAdapter.access$1100(ConversationAdapter.this).startService(VineUploadService.getVMPostIntent(ConversationAdapter.access$1000(ConversationAdapter.this), this.val$vpm.uploadPath, true, this.val$vpm.messageRowId, this.val$vpm.conversationRowId, null, this.val$vpm.message, this.val$vpm.postId, this.val$vpm.videoUrl, this.val$vpm.thumbnailUrl, this.val$vpm.maxLoops));
              ViewGroup parent = (ViewGroup) this.val$v.getParent();
              View progress = parent.findViewById(R.id.retry_progress_normal);
              if (progress == null) {
                  progress = parent.findViewById(R.id.retry_progress_top);
                        }
              if (progress != null) {
                  progress.setVisibility(0);
                  this.val$v.setVisibility(4);
                        }
                    }
                }

      public class BlurRunnable implements Runnable {
                    private final Bitmap bitmap;
                    private final ImageView imageView;
                    private boolean setImage;

          public BlurRunnable(Bitmap bitmap, ImageView imageView) {
              this.bitmap = bitmap;
              this.imageView = imageView;
                    }

                    @Override 
          public void run() {
              if (ConversationAdapter.access$1200(ConversationAdapter.this) == null) {
                  ConversationAdapter.access$1202(ConversationAdapter.this, RecordConfigUtils.createDefaultSizeBitmap());
                        }
              if (ConversationAdapter.access$1300(ConversationAdapter.this) == null) {
                  ConversationAdapter.access$1302(ConversationAdapter.this, new RsBlur(RenderScript.create(ConversationAdapter.access$000(ConversationAdapter.this)), ConversationAdapter.access$1200(ConversationAdapter.this), 25.0f));
                        }
              if (this.setImage) {
                  this.imageView.setImageBitmap(ConversationAdapter.access$1200(ConversationAdapter.this));
                  return;
                        }
              ConversationAdapter.access$1300(ConversationAdapter.this).execute(this.bitmap, ConversationAdapter.access$1200(ConversationAdapter.this), 6);
              this.setImage = true;
              ConversationAdapter.access$1400(ConversationAdapter.this).post(this);
                    }
                }

      public class VanishPlayButtonListener implements View.OnClickListener {
                    private final ConversationViewHolder holder;

          public VanishPlayButtonListener(ConversationViewHolder holder) {
              this.holder = holder;
                    }

                    @Override 
          public void onClick(View v) {
              this.holder.vanishMessageContainer.setVisibility(8);
              this.holder.vanishThumbnailOverlay.setVisibility(8);
              this.holder.loadImage.setVisibility(0);
              this.holder.timerOval.setColor((-16777216) | this.holder.color);
              this.holder.vanishTimerText.setVisibility(0);
              this.holder.vanishTimerText.invalidate();
              ConversationAdapter.this.getVideoPathAndPlayForPosition(this.holder.position, false);
                    }
                }

      public class ConversationVideoClickedListener extends OnListVideoClickListener {
          public ConversationVideoClickedListener() {
              super(ConversationAdapter.this);
                    }

                    @Override 
          public void onClick(View v) {
              super.onClick(v);
                    }
                }

      public static class ConversationViewHolder {
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

          public ConversationViewHolder(View v) {
              this.messageContainer = (ViewGroup) v.findViewById(R.id.message_container);
              this.messageContainerUserImage = (ImageView) v.findViewById(R.id.message_container_user_image);
              this.messageContainerMessage = (TextView) v.findViewById(R.id.message_container_message);
              this.topMessageContainer = (ViewGroup) v.findViewById(R.id.top_message_container);
              this.topMessageContainerUserImage = (ImageView) v.findViewById(R.id.top_message_container_user_image);
              this.topMessageContainerMessage = (TextView) v.findViewById(R.id.top_message_container_message);
              this.vanishThumbnailOverlay = v.findViewById(R.id.vanish_thumbnail_overlay);
              this.vanishMessageOverlay = (TextView) v.findViewById(R.id.vanish_message_overlay);
              this.vanishMessageContainer = v.findViewById(R.id.vanish_message_container);
              this.vanishUserImage = (ImageView) v.findViewById(R.id.vanish_container_user_image);
              this.vanishPlayButton = v.findViewById(R.id.vanish_play_button);
              this.vanishTimerText = (TextView) v.findViewById(R.id.vanish_timer);
              this.postMessageContainer = (ViewGroup) v.findViewById(R.id.post_message_container);
              this.postMessageContainerMessage = (TextView) v.findViewById(R.id.post_message_container_message);
              this.userImageProgressNormal = v.findViewById(R.id.retry_progress_normal);
              this.userImageProgressTop = v.findViewById(R.id.retry_progress_top);
              this.secondaryMessageContainer = v.findViewById(R.id.secondary_message_container);
              this.secondaryMessageContainerMessage = (TextView) v.findViewById(R.id.secondary_message_container_message);
              this.videoContainer = v.findViewById(R.id.video_container);
              this.videoImage = (ImageView) v.findViewById(R.id.video_image);
              this.videoView = (SdkVideoView) v.findViewById(R.id.video_view);
              this.loadImage = v.findViewById(R.id.video_load_image);
              this.errorMessage = (TextView) v.findViewById(R.id.error_message);
              this.bottomPaddingView = v.findViewById(R.id.message_bottom_padding_for_last_item);
              this.nibs = v.findViewById(R.id.nibs);
              this.postNibs = v.findViewById(R.id.shared_post_nibs);
              this.nibsChatNib = v.findViewById(R.id.nibs_chat_nib);
              this.timestamp = (TextView) v.findViewById(R.id.timestampText);
                    }
                }

      public void toggleMute(boolean mute) {
          this.mMuted = mute;
          SLog.m447d("Mute state changed to muted? {}.", Boolean.valueOf(mute));
          SdkVideoView lastPlayer = getLastPlayer();
          if (lastPlayer != null) {
              lastPlayer.setMute(this.mMuted);
                    }
                }
            }
