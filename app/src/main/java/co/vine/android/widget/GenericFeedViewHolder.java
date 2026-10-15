            package co.vine.android.widget;

            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Button;
            import android.widget.CompoundButton;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.ProgressBar;
            import android.widget.TextView;
            import co.vine.android.R;
            import co.vine.android.api.VinePost;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.player.SensitiveImageClickListener;
            import co.vine.android.util.LoopManager;
            
            
    public abstract class GenericFeedViewHolder {
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

        public GenericFeedViewHolder(View view) {
            this.description = (TextView) view.findViewById(R.id.text);
            this.username = (TextView) view.findViewById(R.id.username);
            this.location = (TextView) view.findViewById(R.id.location);
            this.timestamp = (TextView) view.findViewById(R.id.timestamp);
            this.userImage = (ImageView) view.findViewById(R.id.user_image);
            this.commentList = (LinearLayout) view.findViewById(R.id.comment_list);
            this.videoLoadImage = (ProgressBar) view.findViewById(R.id.video_load_image);
            this.sensitiveTextContainer = (LinearLayout) view.findViewById(R.id.sensitive_text);
            this.liked = (ColorizedCircleButton) view.findViewById(R.id.like);
            this.share = (ColorizedCircleButton) view.findViewById(R.id.share_post);
            this.comment = (ColorizedCircleButton) view.findViewById(R.id.comment);
            this.more = (ImageView) view.findViewById(R.id.more_options);
            this.comments1 = (TextView) view.findViewById(R.id.comments_1);
            this.comments2 = (TextView) view.findViewById(R.id.comments_2);
            this.comments3 = (TextView) view.findViewById(R.id.comments_3);
            this.compactLikesCommentsRevinesCounts = (TextView) view.findViewById(R.id.likes_comments_revines_count);
            this.revineAndBylineText = (TextView) view.findViewById(R.id.revine_line_text);
            this.loopsLabel = (TextView) view.findViewById(R.id.loops_label);
            this.loopsHintButton = (Button) view.findViewById(R.id.loops_hint_button);
            this.loopsContainer = (ViewGroup) view.findViewById(R.id.loops_container);
            this.counterView = (CounterView) view.findViewById(R.id.loopCount);
            this.counterView.setMinAnimationSeparation(500L);
            this.counterView.setMaxAnimationSeparation(ModalView.DURATION_DEFAULT);
            this.counterView.setTypeFace(Typefaces.get(view.getContext()).getContentTypeface(0, 2));
            this.counterView.setTextSize(view.getContext().getResources().getDimensionPixelSize(R.dimen.font_size_middle));
            this.onFireView = view.findViewById(R.id.fire);
            this.divider = view.findViewById(R.id.post_viewer_actions_divider);
            this.counterPlus = view.findViewById(R.id.loops_plus);
            this.postCommentsDivider = view.findViewById(R.id.post_comments_divider);
            this.viewAllComments = (TextView) view.findViewById(R.id.view_all_comments);
            this.followButton = (CompoundButton) view.findViewById(R.id.follow_button);
           this.bylineIcon = (ImageView) view.findViewById(R.id.byline_icon);
           this.revineAndBylineContainer = (ViewGroup) view.findViewById(R.id.revine_and_byline_container);
           this.avatarFollowContainer = (ViewGroup) view.findViewById(R.id.avatar_follow_container);
           this.topContentContainer = (ViewGroup) view.findViewById(R.id.top_content_container);
           this.grayPostSpacer = view.findViewById(R.id.gray_post_spacer);
                }

       public void updateCount(VinePost post) {
           this.counterView.reset();
           this.counterView.adjustExtraCount(LoopManager.getLocalLoopCount(post.postId));
           this.counterView.setKnownCount(post.loops, post.velocity / 1000.0d, post.lastRefresh);
           this.onFireView.setVisibility(post.onFire ? 0 : 8);
           this.counterPlus.setVisibility(post.created >= 1398196181000L ? 8 : 0);
                }
            }
