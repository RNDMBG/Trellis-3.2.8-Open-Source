            package co.vine.android.widget;

            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageButton;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            import co.vine.android.cache.image.ImageKey;
            
            
    public class UserViewHolder {
                public ImageKey avatarUrl;
                public View divider;
                public ImageButton followButton;
                public ImageView image;
                public TextView location;
                public ViewGroup root;
                public long userId;
                public TextView username;
                public ImageView verified;

        public UserViewHolder(View view) {
            this.root = (ViewGroup) view.findViewById(C0257R.id.root_layout);
            this.username = (TextView) view.findViewById(C0257R.id.username);
            this.followButton = (ImageButton) view.findViewById(C0257R.id.btn_follow);
            this.image = (ImageView) view.findViewById(C0257R.id.user_image);
            this.verified = (ImageView) view.findViewById(C0257R.id.user_verified);
            this.divider = view.findViewById(C0257R.id.divider);
                }
            }
