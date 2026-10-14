            package co.vine.android.widget;

            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Button;
            import android.widget.CompoundButton;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.ProgressBar;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            import co.vine.android.api.VinePost;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.player.SensitiveImageClickListener;
            import co.vine.android.util.LoopManager;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 63 */    public abstract class GenericFeedViewHolder {
                public ViewGroup avatarFollowContainer;
                public ImageKey avatarKey;
                public ImageView bylineIcon;
                public ImageKey bylineIconImageKey;
                public ColorizedCircleButton comment;
                public LinearLayout commentList;
                public int commentType;
                public TextView comments1;
                public TextView comments2;
                public TextView comments3;
                public TextView compactLikesCommentsRevinesCounts;
                public View counterPlus;
                public CounterView counterView;
                public TextView description;
                public View divider;
                public CompoundButton followButton;
                public View grayPostSpacer;
                public boolean hasVideoImage;
                public SensitiveImageClickListener imageListener;
                public boolean isVideoImageLoaded;
                public ColorizedCircleButton liked;
                public TextView location;
                public ViewGroup loopsContainer;
                public Button loopsHintButton;
                public TextView loopsLabel;
                public ImageView more;
                public View onFireView;
                public int position;
                public VinePost post;
                public View postCommentsDivider;
                public ViewGroup revineAndBylineContainer;
                public TextView revineAndBylineText;
                public LinearLayout sensitiveTextContainer;
                public ColorizedCircleButton share;
                public TextView timestamp;
                public ViewGroup topContentContainer;
                public ImageView userImage;
                public TextView username;
                public ImageKey videoImageKey;
                public ProgressBar videoLoadImage;
                public TextView viewAllComments;

                public abstract View getViewForVideoImage();

/* 64 */        public GenericFeedViewHolder(View view) {
/* 66 */            this.description = (TextView) view.findViewById(C0257R.id.text);
/* 67 */            this.username = (TextView) view.findViewById(C0257R.id.username);
/* 68 */            this.location = (TextView) view.findViewById(C0257R.id.location);
/* 69 */            this.timestamp = (TextView) view.findViewById(C0257R.id.timestamp);
/* 70 */            this.userImage = (ImageView) view.findViewById(C0257R.id.user_image);
/* 71 */            this.commentList = (LinearLayout) view.findViewById(C0257R.id.comment_list);
/* 72 */            this.videoLoadImage = (ProgressBar) view.findViewById(C0257R.id.video_load_image);
/* 73 */            this.sensitiveTextContainer = (LinearLayout) view.findViewById(C0257R.id.sensitive_text);
/* 75 */            this.liked = (ColorizedCircleButton) view.findViewById(C0257R.id.like);
/* 76 */            this.share = (ColorizedCircleButton) view.findViewById(C0257R.id.share_post);
/* 77 */            this.comment = (ColorizedCircleButton) view.findViewById(C0257R.id.comment);
/* 78 */            this.more = (ImageView) view.findViewById(C0257R.id.more_options);
/* 80 */            this.comments1 = (TextView) view.findViewById(C0257R.id.comments_1);
/* 81 */            this.comments2 = (TextView) view.findViewById(C0257R.id.comments_2);
/* 82 */            this.comments3 = (TextView) view.findViewById(C0257R.id.comments_3);
/* 83 */            this.compactLikesCommentsRevinesCounts = (TextView) view.findViewById(C0257R.id.likes_comments_revines_count);
/* 85 */            this.revineAndBylineText = (TextView) view.findViewById(C0257R.id.revine_line_text);
/* 86 */            this.loopsLabel = (TextView) view.findViewById(C0257R.id.loops_label);
/* 87 */            this.loopsHintButton = (Button) view.findViewById(C0257R.id.loops_hint_button);
/* 88 */            this.loopsContainer = (ViewGroup) view.findViewById(C0257R.id.loops_container);
/* 89 */            this.counterView = (CounterView) view.findViewById(C0257R.id.loopCount);
/* 90 */            this.counterView.setMinAnimationSeparation(500L);
/* 91 */            this.counterView.setMaxAnimationSeparation(ModalView.DURATION_DEFAULT);
/* 92 */            this.counterView.setTypeFace(Typefaces.get(view.getContext()).getContentTypeface(0, 2));
/* 93 */            this.counterView.setTextSize(view.getContext().getResources().getDimensionPixelSize(C0257R.dimen.font_size_middle));
/* 94 */            this.onFireView = view.findViewById(C0257R.id.fire);
/* 95 */            this.divider = view.findViewById(C0257R.id.post_viewer_actions_divider);
/* 96 */            this.counterPlus = view.findViewById(C0257R.id.loops_plus);
/* 97 */            this.postCommentsDivider = view.findViewById(C0257R.id.post_comments_divider);
/* 98 */            this.viewAllComments = (TextView) view.findViewById(C0257R.id.view_all_comments);
/* 99 */            this.followButton = (CompoundButton) view.findViewById(C0257R.id.follow_button);
/* 100 */           this.bylineIcon = (ImageView) view.findViewById(C0257R.id.byline_icon);
/* 101 */           this.revineAndBylineContainer = (ViewGroup) view.findViewById(C0257R.id.revine_and_byline_container);
/* 102 */           this.avatarFollowContainer = (ViewGroup) view.findViewById(C0257R.id.avatar_follow_container);
/* 103 */           this.topContentContainer = (ViewGroup) view.findViewById(C0257R.id.top_content_container);
/* 104 */           this.grayPostSpacer = view.findViewById(C0257R.id.gray_post_spacer);
                }

/* 107 */       public void updateCount(VinePost post) {
/* 108 */           this.counterView.reset();
/* 109 */           this.counterView.adjustExtraCount(LoopManager.getLocalLoopCount(post.postId));
/* 110 */           this.counterView.setKnownCount(post.loops, post.velocity / 1000.0d, post.lastRefresh);
/* 111 */           this.onFireView.setVisibility(post.onFire ? 0 : 8);
/* 113 */           this.counterPlus.setVisibility(post.created >= 1398196181000L ? 8 : 0);
                }
            }
