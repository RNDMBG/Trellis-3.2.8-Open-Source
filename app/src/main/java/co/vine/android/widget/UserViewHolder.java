            package co.vine.android.widget;

            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageButton;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            import co.vine.android.cache.image.ImageKey;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class UserViewHolder {
                public ImageKey avatarUrl;
                public View divider;
                public ImageButton followButton;
                public ImageView image;
                public TextView location;
                public ViewGroup root;
                public long userId;
                public TextView username;
                public ImageView verified;

/* 25 */        public UserViewHolder(View view) {
/* 27 */            this.root = (ViewGroup) view.findViewById(C0257R.id.root_layout);
/* 28 */            this.username = (TextView) view.findViewById(C0257R.id.username);
/* 29 */            this.followButton = (ImageButton) view.findViewById(C0257R.id.btn_follow);
/* 30 */            this.image = (ImageView) view.findViewById(C0257R.id.user_image);
/* 31 */            this.verified = (ImageView) view.findViewById(C0257R.id.user_verified);
/* 32 */            this.divider = view.findViewById(C0257R.id.divider);
                }
            }
