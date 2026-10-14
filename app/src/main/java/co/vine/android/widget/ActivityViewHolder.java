            package co.vine.android.widget;

            import android.view.View;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            import co.vine.android.cache.image.ImageKey;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public class ActivityViewHolder {
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

/* 51 */        public ActivityViewHolder(View v) {
/* 53 */            this.typeIcon = (ImageView) v.findViewById(C0257R.id.activity_type_icon);
/* 54 */            this.avatar = (ImageView) v.findViewById(C0257R.id.user_image);
/* 55 */            this.thumbnail = (ImageView) v.findViewById(C0257R.id.thumbnail);
/* 56 */            this.followButton = v.findViewById(C0257R.id.follow);
/* 57 */            this.contentLine = (TextView) v.findViewById(C0257R.id.content_line);
/* 58 */            this.dateLine = (TextView) v.findViewById(C0257R.id.date_line);
/* 61 */            this.milestoneBackground = (ImageView) v.findViewById(C0257R.id.milestone_background);
/* 62 */            this.milestoneOverlay = v.findViewById(C0257R.id.milestone_color_overlay);
/* 63 */            this.milestoneImage = (ImageView) v.findViewById(C0257R.id.milestone_image);
/* 64 */            this.milestoneImageFrame = (ImageView) v.findViewById(C0257R.id.milestone_image_frame);
/* 65 */            this.milestoneIcon = (ImageView) v.findViewById(C0257R.id.milestone_icon);
/* 66 */            this.milestoneTitle = (TextView) v.findViewById(C0257R.id.milestone_title);
/* 67 */            this.milestoneDescription = (TextView) v.findViewById(C0257R.id.milestone_secondary);
/* 70 */            this.headerView = v.findViewById(C0257R.id.header);
/* 71 */            this.headerText = (TextView) v.findViewById(C0257R.id.header_text);
/* 73 */            this.divider = v.findViewById(C0257R.id.divider);
                }
            }
