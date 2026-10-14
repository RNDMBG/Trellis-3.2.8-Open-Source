            package co.vine.android.widget;

            import android.view.View;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            import co.vine.android.cache.image.ImageKey;
            
            
    public class ActivityViewHolder {
                public static final int TYPE_COMMENT_LIST = 3;
                public static final int TYPE_POST = 1;
                public static final int TYPE_USER = 0;
                public static final int TYPE_USER_LIST = 2;
                public long anchor;
                public ImageView avatar;
                public ImageKey avatarImageKey;
                public boolean clickable = true;
                public TextView contentLine;
                public TextView dateLine;
                public View divider;
                public View followButton;
                public TextView headerText;
                public View headerView;
                public int listItemClickType;
                public ImageView milestoneBackground;
                public ImageKey milestoneBackgroundImageKey;
                public TextView milestoneDescription;
                public ImageView milestoneIcon;
                public ImageKey milestoneIconImageKey;
                public ImageView milestoneImage;
                public ImageView milestoneImageFrame;
                public ImageKey milestoneImageImageKey;
                public View milestoneOverlay;
                public TextView milestoneTitle;
                public long notificationId;
                public long postId;
                public ImageView thumbnail;
                public ImageKey thumbnailImageKey;
                public ImageView typeIcon;
                public long userId;

        public ActivityViewHolder(View v) {
            this.typeIcon = (ImageView) v.findViewById(C0257R.id.activity_type_icon);
            this.avatar = (ImageView) v.findViewById(C0257R.id.user_image);
            this.thumbnail = (ImageView) v.findViewById(C0257R.id.thumbnail);
            this.followButton = v.findViewById(C0257R.id.follow);
            this.contentLine = (TextView) v.findViewById(C0257R.id.content_line);
            this.dateLine = (TextView) v.findViewById(C0257R.id.date_line);
            this.milestoneBackground = (ImageView) v.findViewById(C0257R.id.milestone_background);
            this.milestoneOverlay = v.findViewById(C0257R.id.milestone_color_overlay);
            this.milestoneImage = (ImageView) v.findViewById(C0257R.id.milestone_image);
            this.milestoneImageFrame = (ImageView) v.findViewById(C0257R.id.milestone_image_frame);
            this.milestoneIcon = (ImageView) v.findViewById(C0257R.id.milestone_icon);
            this.milestoneTitle = (TextView) v.findViewById(C0257R.id.milestone_title);
            this.milestoneDescription = (TextView) v.findViewById(C0257R.id.milestone_secondary);
            this.headerView = v.findViewById(C0257R.id.header);
            this.headerText = (TextView) v.findViewById(C0257R.id.header_text);
            this.divider = v.findViewById(C0257R.id.divider);
                }
            }
